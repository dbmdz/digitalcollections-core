package de.digitalcollections.core.backend.impl.file.repository.resource.resolver;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;
import de.digitalcollections.core.model.api.resource.exceptions.ResourceIOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PatternFileNameResolverImpl implements FileNameResolver {

  private static final Logger LOGGER = LoggerFactory.getLogger(PatternFileNameResolverImpl.class);

  private final Pattern pattern;

  private final String regex;

  private final String replacement;

  public PatternFileNameResolverImpl(String regex, String replacement) {
    this.regex = regex;
    this.pattern = Pattern.compile(regex);
    this.replacement = replacement;
  }

  @Override
  public Path getPath(String fileName) throws ResourceIOException {
    return Paths.get(this.getUri(fileName));
  }

  @Override
  public String getString(String fileName) {
    String result = this.pattern.matcher(fileName).replaceAll(this.replacement);
    LOGGER.debug("Result " + result);
    return result;
  }

  @Override
  public URI getUri(String fileName) throws ResourceIOException {
    try {
      return new URI(this.getString(fileName));
    } catch (URISyntaxException e) {
      throw new ResourceIOException(e);
    }
  }

  @Override
  public Boolean isResolvable(String fileName) {
    Boolean b = this.pattern.matcher(fileName).matches();
    LOGGER.debug("Matching " + fileName + " against " + this.regex + " is " + b);
    return b;
  }

  @Override
  public String toString() {
    return "PatternFileNameResolverImpl\n{"
            + "\nregex    = '" + regex + '\''
            + "\nreplacement = '" + replacement + '\''
            + "\n}";
  }
}
