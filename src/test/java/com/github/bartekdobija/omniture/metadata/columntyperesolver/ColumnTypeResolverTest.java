package com.github.bartekdobija.omniture.metadata.columntyperesolver;

import com.github.bartekdobija.omniture.metadata.Column;
import static org.junit.Assert.*;
import static com.github.bartekdobija.omniture.metadata.columntyperesolver.SimpleColumnTypeResolver.*;

import com.github.bartekdobija.omniture.metadata.ColumnType;
import org.junit.Test;

public class ColumnTypeResolverTest {

  @Test
  public void simpleTypeResolver() {

    ColumnTypeResolver resolver = new SimpleColumnTypeResolver();

    assertEquals(resolver.parseName(null), null);
    assertEquals(resolver.parseName(""), null);

    Column tested = resolver.parseName("sample_list");

    assertTrue(tested.getName().equals("sample_list"));
    assertTrue(tested.getLookupGroup() == null);
    assertTrue(tested.getType() == ColumnType.STRING_ARRAY);

    tested = resolver.parseName("event_list");

    assertTrue(tested.getName().equals("event_list"));
    assertTrue(tested.getLookupGroup().equals(EVENT_LIST_GROUP));
    assertTrue(tested.getType() == ColumnType.STRING_ARRAY);

    tested = resolver.parseName("test");

    assertTrue(tested.getName().equals("test"));
    assertTrue(tested.getLookupGroup() == null);
    assertTrue(tested.getType() == ColumnType.STRING);

    tested = resolver.parseName("os");

    assertTrue(tested.getName().equals("os"));
    assertTrue(tested.getLookupGroup().equals(OS_GROUP));
    assertTrue(tested.getType() == ColumnType.STRING);

    tested = resolver.parseName("timestamp_gmt");

    assertTrue(tested.getName().equals("timestamp_gmt"));
    assertTrue(tested.getLookupGroup() == null);
    assertTrue(tested.getType() == ColumnType.TIMESTAMP);

    tested = resolver.parseName("hit_time_gmt");

    assertTrue(tested.getName().equals("hit_time_gmt"));
    assertTrue(tested.getLookupGroup() == null);
    assertTrue(tested.getType() == ColumnType.TIMESTAMP);

    tested = resolver.parseName("cust_hit_time_gmt");

    assertTrue(tested.getName().equals("cust_hit_time_gmt"));
    assertTrue(tested.getLookupGroup() == null);
    assertTrue(tested.getType() == ColumnType.TIMESTAMP);

    tested = resolver.parseName("first_hit_time_gmt");

    assertTrue(tested.getName().equals("first_hit_time_gmt"));
    assertTrue(tested.getLookupGroup() == null);
    assertTrue(tested.getType() == ColumnType.TIMESTAMP);

    tested = resolver.parseName("last_hit_time_gmt");

    assertTrue(tested.getName().equals("last_hit_time_gmt"));
    assertTrue(tested.getLookupGroup() == null);
    assertTrue(tested.getType() == ColumnType.TIMESTAMP);

    tested = resolver.parseName("post_cust_hit_time_gmt");

    assertTrue(tested.getName().equals("post_cust_hit_time_gmt"));
    assertTrue(tested.getLookupGroup() == null);
    assertTrue(tested.getType() == ColumnType.TIMESTAMP);

    tested = resolver.parseName("visit_start_time_gmt");

    assertTrue(tested.getName().equals("visit_start_time_gmt"));
    assertTrue(tested.getLookupGroup() == null);
    assertTrue(tested.getType() == ColumnType.TIMESTAMP);

    tested = resolver.parseName("j_script");

    assertTrue(tested.getName().equals("j_script"));
    assertTrue(tested.getLookupGroup() == null);
    assertTrue(tested.getType() == ColumnType.STRING);

    tested = resolver.parseName("javascript");

    assertTrue(tested.getName().equals("javascript"));
    assertTrue(tested.getLookupGroup() == null);
    assertTrue(tested.getType() == ColumnType.STRING);

    tested = resolver.parseName("post_visid_high");

    assertTrue(tested.getName().equals("post_visid_high"));
    assertTrue(tested.getLookupGroup() == null);
    assertTrue(tested.getType() == ColumnType.LONG);

    tested = resolver.parseName("post_visid_low");

    assertTrue(tested.getName().equals("post_visid_low"));
    assertTrue(tested.getLookupGroup() == null);
    assertTrue(tested.getType() == ColumnType.LONG);

    tested = resolver.parseName("hitid_high");

    assertTrue(tested.getName().equals("hitid_high"));
    assertTrue(tested.getLookupGroup() == null);
    assertTrue(tested.getType() == ColumnType.LONG);

    tested = resolver.parseName("hitid_low");

    assertTrue(tested.getName().equals("hitid_low"));
    assertTrue(tested.getLookupGroup() == null);
    assertTrue(tested.getType() == ColumnType.LONG);

    tested = resolver.parseName("post_visid_type");

    assertTrue(tested.getName().equals("post_visid_type"));
    assertTrue(tested.getLookupGroup() == null);
    assertTrue(tested.getType() == ColumnType.INT);

    tested = resolver.parseName("evar10");

    assertTrue(tested.getName().equals("evar10"));
    assertTrue(tested.getLookupGroup() == null);
    assertTrue(tested.getType() == ColumnType.STRING);

    tested = resolver.parseName("user_hash");

    assertTrue(tested.getName().equals("user_hash"));
    assertTrue(tested.getLookupGroup() == null);
    assertTrue(tested.getType() == ColumnType.LONG);

    tested = resolver.parseName("userid");

    assertTrue(tested.getName().equals("userid"));
    assertTrue(tested.getLookupGroup() == null);
    assertTrue(tested.getType() == ColumnType.LONG);

  }

}
