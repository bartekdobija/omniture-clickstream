package com.github.bartekdobija.omniture.row;

import static org.junit.Assert.*;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;


public class RowTest {

  @Test
  public void rowCreation() throws CloneNotSupportedException {

    Row row = new Row(2);

    row.add(1);
    row.add(2);
    row.add(null);
    row.add(null);

    assertEquals(1, row.get(0));
    assertEquals(2, row.get(1));
    assertEquals(null, row.get(2));
    assertEquals(null, row.get(3));

    Row cloned = (Row) row.clone();

    assertEquals(1, cloned.get(0));
    assertEquals(2, cloned.get(1));

  }

  @Test
  public void rowIterator() {

    Row row = new Row(3);
    Object[] expected = new Object[]{ 1, "string", 1.2 };
    int i = 0;

    row.add(expected[0]);
    row.add(expected[1]);
    row.add(expected[2]);
    row.add(null);
    row.add(null);

    for(Object o : row) assertEquals(expected[i++], o);

    Iterator<Object> iter = row.iterator();

    assertEquals(expected[0], iter.next());
    assertEquals(expected[1], iter.next());
    assertEquals(expected[2], iter.next());
    assertEquals(null, iter.next());

    assertEquals(expected[0], row.iterator().next());

  }

  @Test
  public void rowSerialization() throws IOException {

    Row row = new Row(2);
    row.add("string");
    row.add(1);

    assertTrue(row instanceof Serializable);
    new ObjectOutputStream(new ByteArrayOutputStream()).writeObject(row);
    assertEquals("string\t1\t", row.toString());
  }

}
