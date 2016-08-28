package com.github.bartekdobija.omniture.row;

public interface RowParser {

  Row parse(String line);
  Row parse(char[] line);
  RowParserStats getRowParserStats();

}
