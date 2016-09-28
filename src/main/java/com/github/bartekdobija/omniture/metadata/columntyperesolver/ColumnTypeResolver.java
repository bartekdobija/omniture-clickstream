package com.github.bartekdobija.omniture.metadata.columntyperesolver;

import com.github.bartekdobija.omniture.metadata.Column;

/**
 * Interface for Column name-to-{@link Column} resolver.
 */
public interface ColumnTypeResolver {

  /**
   * return initialized {@link Column} based on the column name.
   *
   * @param name column name
   * @return initialized {@link Column} object with correct type.
   */
  Column parseName(String name);
}
