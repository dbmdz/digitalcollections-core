package de.digitalcollections.core.model.jackson.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.digitalcollections.core.model.impl.paging.PageRequestImpl;

@JsonDeserialize(as = PageRequestImpl.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class PageRequestMixIn {
//public abstract class PageRequestMixIn extends PageRequestImpl {

  // TODO does not work, never called. workaround: had to introduce default constructor in PageRequestImpl
//  @JsonCreator
//  public PageRequestMixIn(
//          @JsonProperty("pageSize") int pageSize,
//          @JsonProperty("pageNumber") int pageNumber,
//          @JsonProperty("sorting") Sorting sorting) {
//    super(pageSize, pageNumber, sorting);
//  }
  @JsonIgnore
  //  @Override
  public abstract int getOffset();
}
