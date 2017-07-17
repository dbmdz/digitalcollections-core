package de.digitalcollections.core.model.api;

/**
 * Information on sorting of a list of result items (sort field, sort order)
 */
public interface Sorting {

  // Enum for sort order
  public enum SortOrder {
    ASC, DESC;
  }

  // Enum for sort type
  public enum SortType {
    ALPHANUMERICAL, NUMERICAL;
  }

  public String getSortField();

  public void setSortField(String sortField);

  public SortOrder getSortOrder();

  public void setSortOrder(SortOrder sortOrder);

  public SortType getSortType();

  public void setSortType(SortType sortType);
}
