package com.github.bartekdobija.omniture.row;

/**
 * Omniture log row parser.
 */
public interface RowParser {

  /**
   * Parse a line of Omniture log.
   *
   * @param line string log representation.
   * @return initialised {@link Row} instance.
   */
  Row parse(String line);

  /**
   * Parse a list of Omniture log lines.
   *
   * @param lines string logs representation.
   * @return initialised {@link Row} instance.
   */
  Row parse(char[] lines);

  /**
   * Fetch parser usage statistics.
   *
   * @return parser stats
   */
  RowParserStats getRowParserStats();

}
