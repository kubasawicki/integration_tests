package edu.iis.mto.blog.domain.repository;

import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import edu.iis.mto.blog.UserBuilder;
import edu.iis.mto.blog.domain.model.AccountStatus;
import edu.iis.mto.blog.domain.model.User;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository repository;

    private User user1;
    private User user2;

    @Before
    public void setUp() {
    	
        user1 = new UserBuilder().build();
        user2 = new UserBuilder().withFirstName("firstname").withLastName("lastname").withEmail("firstname@domain.com").build();
    }

    @Test
    public void shouldFindOneUserIfRepositoryContainsOneUserEntity() {
        User persistedUser = entityManager.persist(user1);
        List<User> users = repository.findAll();

        Assert.assertThat(users, Matchers.hasSize(1));
        Assert.assertThat(users.get(0).getEmail(), Matchers.equalTo(persistedUser.getEmail()));
    }

    @Test
    public void shouldStoreANewUser() {
        User persistedUser = repository.save(user1);

        Assert.assertThat(persistedUser.getId(), Matchers.notNullValue());
    }

    @Test
    public void findOneUserByEmailWhenOneMatchingInRepo() {
        repository.save(user1);
        repository.save(user2);

        List<User> users = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase
                ("null", "null", user1.getEmail());

        Assert.assertThat(users, Matchers.hasSize(1));
        Assert.assertThat(users.get(0).getEmail(), Matchers.equalTo(user1.getEmail()));
    }

    @Test
    public void findTwoUsersByEmailWhenTwoMatchingInRepo() {
        repository.save(user1);
        repository.save(user2);

        List<User> users = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase
                ("null", "null", "domain");

        Assert.assertThat(users, Matchers.hasSize(2));
        Assert.assertThat(users.get(0).getEmail(), Matchers.equalTo(user1.getEmail()));
        Assert.assertThat(users.get(1).getEmail(), Matchers.equalTo(user2.getEmail()));
    }


    @Test
    public void findNoUsersByEmailWhenNoMatchingInRepo() {
        repository.save(user1);
        repository.save(user2);

        List<User> users = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase
                ("null", "null", "p.lodz.pl");

        Assert.assertThat(users, Matchers.hasSize(0));
    }


    @Test
    public void findOneUserByFirstNameWhenOneMatchingInRepo() {
        repository.save(user1);
        repository.save(user2);

        List<User> users = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase
                ("j", "null", "null");

        Assert.assertThat(users, Matchers.hasSize(1));
        Assert.assertThat(users.get(0).getEmail(), Matchers.equalTo(user1.getEmail()));
    }

    @Test
    public void findTwoUsersByFirstNameWhenTwoMatchingInRepo() {
        repository.save(user1);
        repository.save(user2);

        List<User> users = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase
                ("a", "null", "null");

        Assert.assertThat(users, Matchers.hasSize(2));
        Assert.assertThat(users.get(0).getEmail(), Matchers.equalTo(user1.getEmail()));
        Assert.assertThat(users.get(1).getEmail(), Matchers.equalTo(user2.getEmail()));
    }
    
    @Test
    public void findNoUsersByFirstNameWhenNoMatchingInRepo() {
        repository.save(user1);
        repository.save(user2);

        List<User> users = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase
                ("s", "null", "null");

        Assert.assertThat(users, Matchers.hasSize(0));
    }

    @Test
    public void findOneUserByLastNameWhenOneMatchingInRepo() {
        repository.save(user1);
        repository.save(user2);

        List<User> users = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase
                ("null", "tt", "null");

        Assert.assertThat(users, Matchers.hasSize(1));
        Assert.assertThat(users.get(0).getEmail(), Matchers.equalTo(user1.getEmail()));
    }

    @Test
    public void findTwoUsersByLastNameWhenTwoMatchingInRepo() {
        repository.save(user1);
        repository.save(user2);

        List<User> users = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase
                ("null", "be", "null");

        Assert.assertThat(users, Matchers.hasSize(2));
        Assert.assertThat(users.get(0).getEmail(), Matchers.equalTo(user1.getEmail()));
        Assert.assertThat(users.get(1).getEmail(), Matchers.equalTo(user2.getEmail()));
    }

    @Test
    public void findNoUsersByLastNameWhenNoMatchingInRepo() {
        repository.save(user1);
        repository.save(user2);

        List<User> users = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase
                ("null", "worse", "null");

        Assert.assertThat(users, Matchers.hasSize(0));
    }
    
    @Test
    public void findUserIgnoresCaseForFirstName() {
        repository.save(user1);
        repository.save(user2);

        List<User> users = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase
                ("A", "null", "null");

        Assert.assertThat(users, Matchers.hasSize(2));
    }
    
    @Test
    public void findUserIgnoresCaseForLastName() {
        repository.save(user1);
        repository.save(user2);

        List<User> users = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase
                ("null", "bE", "null");

        Assert.assertThat(users, Matchers.hasSize(2));
    }
    
    @Test
    public void findUserIgnoresCaseForEmail() {
        repository.save(user1);
        repository.save(user2);

        List<User> users = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase
                ("null", "null", "dOMAin");

        Assert.assertThat(users, Matchers.hasSize(2));
    }
    
}