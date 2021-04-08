package geminica.spring.batchprocessing;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import geminica.spring.batchprocessing.config.SpringBatchExecutor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@Sql(scripts = "/sql/clear.sql")
class BatchProcessingApplicationTests {
  @Autowired private SpringBatchExecutor springBatchExecutor;
  @Autowired private JdbcTemplate jdbcTemplate;

  @Test
  void testExample() {
    assertThat(count(), equalTo(0L));
    springBatchExecutor.execute("sample-data.csv");
    assertThat(count(), equalTo(5L));
  }

  @Test
  void testLargeFileProcessing() {
    assertThat(count(), equalTo(0L));
    springBatchExecutor.execute("large-data.csv");
    assertThat(count(), equalTo(60000L));
  }

  private long count() {
    return jdbcTemplate.queryForObject("SELECT count(*) FROM people", Long.class);
  }
}
