package de.digitalcollections.core.backend.impl.file.repository.resource.resolver;

import de.digitalcollections.core.model.api.resource.exceptions.ResourceIOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

@Component
public class MultiPatternsFileNameResolverImpl implements FileNameResolver, InitializingBean {

  private static final Logger LOGGER = LoggerFactory.getLogger(MultiPatternsFileNameResolverImpl.class);

  @Value(value = "${multiPatternResolvingFile:}")
  private String patternFileClasspath;

  private List<PatternFileNameResolverImpl> patternFileNameResolvers = new ArrayList<>();

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
    String filepath = getPatternFileClasspath();
    if (StringUtils.isEmpty(filepath)) {
      return;
    }
    Resource patRes = new ClassPathResource(filepath);
    if (patRes.exists() && patRes.isReadable()) {
      Constructor constructor = new Constructor(PatternFileNameResolverImpl[].class);
      Yaml yaml = new Yaml(constructor);
      this.patternFileNameResolvers = Arrays.asList((PatternFileNameResolverImpl[]) yaml.load(patRes.getInputStream()));
    }
  }

  public String getPatternFileClasspath() {
    return patternFileClasspath;
  }

  public void setPatternFileClasspath(String patternFileClasspath) throws Exception {
    this.patternFileClasspath = patternFileClasspath;
    afterPropertiesSet();
  }

  @Override
  public List<String> getStrings(String identifier) throws ResourceIOException {
    return patternFileNameResolvers.stream()
            .filter(r -> r.isResolvable(identifier))
            .map(r -> r.getStrings(identifier))
            .flatMap(Collection::stream)
            .collect(Collectors.toList());
  }

  @Override
  public Boolean isResolvable(String identifier) {
    return patternFileNameResolvers.stream()
            .filter(r -> r.isResolvable(identifier))
            .findFirst().isPresent();
  }
}
