package de.digitalcollections.core.model.jackson;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import de.digitalcollections.core.model.api.paging.Order;
import de.digitalcollections.core.model.api.paging.PageRequest;
import de.digitalcollections.core.model.api.paging.PageResponse;
import de.digitalcollections.core.model.api.paging.Sorting;
import de.digitalcollections.core.model.jackson.mixin.OrderMixIn;
import de.digitalcollections.core.model.jackson.mixin.PageRequestMixIn;
import de.digitalcollections.core.model.jackson.mixin.PageResponseMixIn;
import de.digitalcollections.core.model.jackson.mixin.SortingMixIn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DcCoreModelModule extends Module {

  private static final Logger LOGGER = LoggerFactory.getLogger(DcCoreModelModule.class);

  @Override
  public String getModuleName() {
    return "DigitalCollections Core Model jackson module";
  }

  @Override
  public void setupModule(SetupContext context) {
    LOGGER.info("Using DcCoreModelModule");
    context.setMixInAnnotations(Order.class, OrderMixIn.class);
    context.setMixInAnnotations(PageRequest.class, PageRequestMixIn.class);
    context.setMixInAnnotations(PageResponse.class, PageResponseMixIn.class);
    context.setMixInAnnotations(Sorting.class, SortingMixIn.class);
  }

  @Override
  public Version version() {
    return new Version(2, 0, 0, "SNAPSHOT", "de.digitalcollections.core", "dc-core-model-jackson");
  }

}
