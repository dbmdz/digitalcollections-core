package de.digitalcollections.core.model.impl;

import de.digitalcollections.core.model.api.Paging;

public class PagingImpl implements Paging {

  private int offset;

  private int numRows;

  protected PagingImpl() {
  }

  public PagingImpl(int offset, int numRows) {
    this.offset = offset;
    this.numRows = numRows;
  }

  @Override
  public int getOffset() {
    return offset;
  }

  @Override
  public void setOffset(int offset) {
    this.offset = offset;
  }

  @Override
  public int getNumRows() {
    return numRows;
  }

  @Override
  public void setNumRows(int numRows) {
    this.numRows = numRows;
  }

  @Override
  public int hashCode() {
    int hash = 5;
    hash = 11 * hash + this.offset;
    hash = 11 * hash + this.numRows;
    return hash;
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
    final PagingImpl other = (PagingImpl) obj;
    if (this.offset != other.offset) {
      return false;
    }
    if (this.numRows != other.numRows) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "PagingImpl{" + "offset=" + offset + ", numRows=" + numRows + '}';
  }

}
