package de.digitalcollections.core.model.api;

/**
 * Information on paging of a list of result items (offset, number of records to display)
 */
public interface Paging {

  int getNumRows();

  int getOffset();

  void setNumRows(int numRows);

  void setOffset(int offset);

}
