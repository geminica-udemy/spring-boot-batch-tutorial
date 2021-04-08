package geminica.spring.batchprocessing.config;

import geminica.spring.batchprocessing.model.Person;
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

  @Override
  public void afterJob(JobExecution jobExecution) {
    if (BatchStatus.COMPLETED == jobExecution.getStatus()) {
      jdbcTemplate
          .query(
              "SELECT first_name, last_name FROM people",
              (rs, row) -> new Person(rs.getString(1), rs.getString(2)))
          .forEach(p -> log.info("Found {} in the database", p));
    }
  }
}
