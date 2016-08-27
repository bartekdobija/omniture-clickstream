package com.github.bartekdobija.omniture.metadata.columntyperesolver;

import com.github.bartekdobija.omniture.metadata.Column;

public interface ColumnTypeResolver {
  Column parseName(String name);
}
