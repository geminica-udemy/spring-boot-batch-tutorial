package geminica.spring.batchprocessing.jobs;

import geminica.spring.batchprocessing.model.Person;
import org.springframework.batch.item.ItemProcessor;

public class PersonItemProcessor implements ItemProcessor<Person, Person> {
  @Override
  public Person process(Person input) throws Exception {
    Person processed =
        new Person(input.getFirstName().toUpperCase(), input.getLastName().toUpperCase());
    return processed;
  }
}
