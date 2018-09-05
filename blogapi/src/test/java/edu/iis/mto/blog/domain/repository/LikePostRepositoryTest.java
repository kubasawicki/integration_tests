package edu.iis.mto.blog.domain.repository;

 import edu.iis.mto.blog.UserBuilder;
import edu.iis.mto.blog.domain.model.AccountStatus;
 import edu.iis.mto.blog.domain.model.BlogPost;
 import edu.iis.mto.blog.domain.model.LikePost;
 import edu.iis.mto.blog.domain.model.User;
 import org.hamcrest.Matchers;
 import org.junit.Assert;
 import org.junit.Before;
 import org.junit.Test;
 import org.junit.runner.RunWith;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
 import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
 import org.springframework.test.context.junit4.SpringRunner;

 import java.util.List;
 import java.util.Optional;

 @RunWith(SpringRunner.class)
 @DataJpaTest
 public class LikePostRepositoryTest {

     @Autowired
     private TestEntityManager entityManager;

     @Autowired
     private LikePostRepository likePostRepository;

     @Autowired
     private UserRepository userRepository;

     @Autowired
     private BlogPostRepository blogPostRepository;

     private LikePost likePost;
     private User user1;
     private User user2;
     private BlogPost blogPost;

     @Before
     public void setUp(){
         user1 = new UserBuilder().build();
         user2 = new UserBuilder().withFirstName("firstname").withLastName("lastname").withEmail("firstname@domain.com").build();

         userRepository.save(user1);
         userRepository.save(user2);

         blogPost = new BlogPost();
         blogPost.setUser(user1);
         blogPost.setEntry("test entry");
         blogPostRepository.save(blogPost);

         likePost = new LikePost();
         likePost.setUser(user2);
         likePost.setPost(blogPost);

         likePostRepository.save(likePost);
     }

     @Test
     public void shouldFindOnePostIfRepositoryContainsOnePostEntity() {
         List<LikePost> posts = likePostRepository.findAll();

         Assert.assertThat(posts, Matchers.hasSize(1));
     }
     
     @Test
     public void shouldStoreNewLikePost() {
         BlogPost blogPost = new BlogPost();
         blogPost.setUser(user1);
         blogPost.setEntry("test entry 2");
         blogPostRepository.save(blogPost);

         likePost = new LikePost();
         likePost.setUser(user2);
         likePost.setPost(blogPost);

         Assert.assertThat(likePost.getId(), Matchers.nullValue());

         LikePost savedPost = likePostRepository.save(likePost);
         Assert.assertThat(savedPost.getId(), Matchers.notNullValue());
     }

     @Test
     public void findByUserAndPostsReturnsRelevantPost(){
         Optional<LikePost> post = likePostRepository.findByUserAndPost(user2, blogPost);
         Assert.assertThat(post.isPresent(), Matchers.is(true));
     }

     @Test
     public void findByUserAndPostsDoesNotReturnPostWhenThereIsNoMatching(){
         Optional<LikePost> post = likePostRepository.findByUserAndPost(user1, blogPost);
         Assert.assertThat(post.isPresent(), Matchers.is(false));
     }
     
 }