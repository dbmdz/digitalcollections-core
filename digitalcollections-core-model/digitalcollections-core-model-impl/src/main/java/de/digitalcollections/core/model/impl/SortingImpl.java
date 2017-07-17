package de.digitalcollections.core.model.impl;

import de.digitalcollections.core.model.api.Sorting;
import de.digitalcollections.core.model.api.Sorting.SortOrder;
import de.digitalcollections.core.model.api.Sorting.SortType;
import java.util.Objects;

public class SortingImpl implements Sorting {

  private String sortField;

  private SortOrder sortOrder;

  private SortType sortType;

  public SortingImpl() {
  }

  public SortingImpl(String sortField, String sortOrder, String sortType) {
    this.sortField = sortField;
    if (sortOrder != null && !sortOrder.isEmpty()) {
      this.sortOrder = SortOrder.valueOf(sortOrder);
    }
    if (sortType != null && !sortType.isEmpty()) {
      this.sortType = SortType.valueOf(sortType);
    }
  }

  public SortingImpl(String sortField, SortOrder sortOrder, SortType sortType) {
    this.sortField = sortField;
    this.sortOrder = sortOrder;
    this.sortType = sortType;
  }

  @Override
  public String getSortField() {
    return sortField;
  }

  @Override
  public void setSortField(String sortField) {
    this.sortField = sortField;
  }

  @Override
  public SortOrder getSortOrder() {
    return sortOrder;
  }

  @Override
  public void setSortOrder(SortOrder sortOrder) {
    this.sortOrder = sortOrder;
  }

  @Override
  public SortType getSortType() {
    return this.sortType;
  }

  @Override
  public void setSortType(SortType sortType) {
    this.sortType = sortType;
  }

  @Override
  public int hashCode() {
    return Objects.hash(sortField, sortOrder, sortType);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final SortingImpl other = (SortingImpl) obj;
    if (!Objects.equals(this.sortField, other.sortField)) {
      return false;
    }
    if (this.sortOrder != other.sortOrder) {
      return false;
    }
    if (this.sortType != other.sortType) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "SortingImpl{" + "sortField=" + sortField + ", sortOrder=" + sortOrder + ", sortType=" + sortType + '}';
  }
}
