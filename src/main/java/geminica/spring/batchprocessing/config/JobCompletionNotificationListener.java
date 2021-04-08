package geminica.spring.batchprocessing.config;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {
  private static final Logger log =
      LoggerFactory.getLogger(JobCompletionNotificationListener.class);

  private final JdbcTemplate jdbcTemplate;
  private long startTime;

  @Override
  public void beforeJob(JobExecution jobExecution) {
    startTime = System.currentTimeMillis();
  }

  @Override
  public void afterJob(JobExecution jobExecution) {
    if (BatchStatus.COMPLETED == jobExecution.getStatus()) {
      long endTime = System.currentTimeMillis();
      log.info(
          "Found {} records in the database. Import done in {} ms",
          jdbcTemplate.queryForObject("SELECT count(*) FROM people", Long.class),
          endTime - startTime);
    }
  }
}
