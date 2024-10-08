package no.hvl.dat250.jpa.tutorial.creditcards.driver;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import no.hvl.dat250.jpa.tutorial.creditcards.*;

public class CreditCardsMain {

  static final String PERSISTENCE_UNIT_NAME = "jpa-tutorial";

  public static void main(String[] args) {
    try (EntityManagerFactory factory = Persistence.createEntityManagerFactory(
        PERSISTENCE_UNIT_NAME); EntityManager em = factory.createEntityManager()) {
      em.getTransaction().begin();
      createObjects(em);
      em.getTransaction().commit();
    }

  }

  private static void createObjects(EntityManager em) {
    Customer customer = new Customer("Max Mustermann");
    Address address = new Address("Inndalsveien", 28);
    Pincode pincode = new Pincode("123", 1);
    CreditCard card1 = new CreditCard(12345, -10000, -5000);
    CreditCard card2 = new CreditCard(123, 2000, 1);
    Bank bank = new Bank("Pengebank");

    customer.getAddresses().add(address);

    card1.setPincode(pincode);
    card1.setOwningBank(bank);

    card2.setPincode(pincode);
    card2.setOwningBank(bank);

    customer.getCreditCards().add(card1);
    customer.getCreditCards().add(card2);

    em.persist(pincode);
    em.persist(bank);
    em.persist(card1);
    em.persist(card2);
    em.persist(address);
    em.persist(customer);
  }
}
