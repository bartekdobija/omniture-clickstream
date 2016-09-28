package com.github.bartekdobija.omniture.loader;

import java.io.Closeable;
import java.io.InputStream;

/**
 * Data loader interface provided for metadata and log loading operation.
 * @since 0.1
 */
public interface DataLoader extends Closeable {

  /**
   * Get stream for the selected source.
   *
   * @return arbitrary data stream
   * @throws DataLoaderException data loader exception
   * @since 0.1
   */
  InputStream stream() throws DataLoaderException;

  /**
   * Get string representation of the source URL/Path.
   *
   * @return source URL/Path or other medium
   * @since 0.1
   */
  String getSource();

  /**
   * Close {@link DataLoader}`s resources.
   * @since 0.1
   */
  @Override void close();
}
