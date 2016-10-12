package de.digitalcollections.core.backend.impl.file.repository.resource.resolver;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import de.digitalcollections.core.model.api.resource.exceptions.ResourceIOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class MultiPatternsFileNameResolverImpl implements FileNameResolver, InitializingBean {

  private static final Logger LOGGER = LoggerFactory.getLogger(MultiPatternsFileNameResolverImpl.class);

  @Value(value = "${multiPatternResolvingFile:}")
  private String patternFileClasspath;

  private List<PatternFileNameResolverImpl> patternFileNameResolvers = new LinkedList<>();

  public MultiPatternsFileNameResolverImpl(List<PatternFileNameResolverImpl> patternFileNameResolvers) {
    this.patternFileNameResolvers = patternFileNameResolvers;
  }

  public MultiPatternsFileNameResolverImpl() {
  }

  public void addPattern(String regex, String replacement) {
    PatternFileNameResolverImpl resolver = new PatternFileNameResolverImpl(regex, replacement);
    patternFileNameResolvers.add(resolver);
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    if (StringUtils.isEmpty(patternFileClasspath)) {
      return;
    }
    Resource patRes = new ClassPathResource(patternFileClasspath);
    if (patRes.exists()) {
      Properties props = new Properties();
      try {
        props.load(patRes.getInputStream());
      } catch (IOException e) {
        LOGGER.error("Could not read pattern file.", e);
        return;
      }
      for (Map.Entry<Object, Object> entry : props.entrySet()) {
        String pattern = (String) entry.getKey();
        String replacement = (String) entry.getValue();
        replacement = replacement.replaceFirst("~", System.getProperty("user.home"));
        this.addPattern(pattern, replacement);
      }
    }
  }

  @Override
  public Path getPath(String fileName) throws ResourceIOException {
    return Paths.get(this.getUri(fileName));
  }

  public void setPatternFileClasspath(String patternFileClasspath) throws Exception {
    this.patternFileClasspath = patternFileClasspath;
    afterPropertiesSet();
  }

  @Override
  public String getString(String fileName) throws ResourceIOException {
    for (PatternFileNameResolverImpl filenameResolver : patternFileNameResolvers) {
      if (filenameResolver.isResolvable(fileName)) {
        String result = filenameResolver.getString(fileName);
        return result;
      }
    }
    throw new ResourceIOException("No resolver matched.");
  }

  @Override
  public URI getUri(String identifier) throws ResourceIOException {
    URI uri;
    try {
      String resolvedPath = getString(identifier);
      uri = new URI(resolvedPath);
      if (!uri.isAbsolute()) {
        uri = new URI("file:" + resolvedPath);
      }
      return uri;
    } catch (URISyntaxException e) {
      LOGGER.error("Invalid URI", e);
      throw new ResourceIOException("URI syntax exception.", e);
    }
  }

  @Override
  public Boolean isResolvable(String fileName) {
    for (PatternFileNameResolverImpl filenameResolver : patternFileNameResolvers) {
      if (filenameResolver.isResolvable(fileName)) {
        return Boolean.TRUE;
      }
    }
    return Boolean.FALSE;
  }
}
