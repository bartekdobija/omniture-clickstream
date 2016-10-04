package com.github.bartekdobija.omniture.metadata.columntyperesolver;

import com.github.bartekdobija.omniture.metadata.Column;

/**
 * Interface for "column name-to-{@link Column}" resolution implementations.
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
