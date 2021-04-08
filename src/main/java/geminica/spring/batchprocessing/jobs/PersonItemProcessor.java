package geminica.spring.batchprocessing.jobs;

import geminica.spring.batchprocessing.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class PersonItemProcessor implements ItemProcessor<Person, Person> {
  private static final Logger log = LoggerFactory.getLogger(PersonItemProcessor.class);

  @Override
  public Person process(Person input) throws Exception {
    Person processed =
        new Person(input.getFirstName().toUpperCase(), input.getLastName().toUpperCase());
    log.info("Converting {} into {}", input, processed);
    return processed;
  }
}
