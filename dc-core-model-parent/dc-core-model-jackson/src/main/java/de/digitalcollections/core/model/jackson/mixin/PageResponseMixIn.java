package de.digitalcollections.core.model.jackson.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.digitalcollections.core.model.api.paging.Sorting;
import de.digitalcollections.core.model.impl.paging.PageResponseImpl;
import java.util.List;

@JsonDeserialize(as = PageResponseImpl.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class PageResponseMixIn<T> extends PageResponseImpl<T> {

//  @JsonCreator
//  public PageResponseMixIn(
//          @JsonProperty("content") List<T> content,
//          @JsonProperty("pageRequest") PageRequest pageRequest,
//          @JsonProperty("totalElements") long totalElements) {
//    super(content, pageRequest, totalElements);
//  }
  @JsonTypeInfo(
          use = JsonTypeInfo.Id.CLASS,
          include = JsonTypeInfo.As.WRAPPER_OBJECT,
          visible = true
  )
  @Override
  public abstract List<T> getContent();

  @JsonIgnore
  @Override
  public abstract int getNumber();

  @JsonIgnore
  @Override
  public abstract int getNumberOfElements();

  @JsonIgnore
  @Override
  public abstract int getSize();

  @JsonIgnore
  @Override
  public abstract Sorting getSorting();

//  @JsonIgnore
//  @Override
//  public abstract long getTotalElements();
  @JsonIgnore
  @Override
  public abstract int getTotalPages();

  @JsonIgnore
  @Override
  public abstract boolean isFirst();

  @JsonIgnore
  @Override
  public abstract boolean isLast();
}
