package com.github.bartekdobija.omniture.row;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.Serializable;
import java.util.Iterator;

public class Row implements Iterable<Object>, Serializable, Cloneable {

  private final Object[] columns;
  private int index;

  public Row(int size) {
    columns = new Object[size];
    index = 0;
  }

  public void add(Object o) {
    columns[index++] = o;
  }

  public Object get(int pos) {
    return columns[pos];
  }

  @Override
  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }

  public int size() {
    return index;
  }

  @Override
  public Iterator<Object> iterator() {
    return new Iterator<Object>() {

      private int pos = 0;

      @Override
      public boolean hasNext() {
        return pos < index;
      }

      @Override
      public Object next() {
        return hasNext() ? columns[pos++] : null;
      }

      @Override
      public void remove() {
        throw new NotImplementedException();
      }
    };
  }
}
