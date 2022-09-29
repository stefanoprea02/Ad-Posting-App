package app.olxclone.repositories;

import app.olxclone.domain.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
class CategoryRepositoryTest {

    @Autowired
    CategoryRepository categoryRepository;

    @BeforeEach
    public void setUp() throws Exception{
        categoryRepository.deleteAll().block();
    }

    @Test
    public void testSave() throws Exception{
        Category category = new Category();
        category.setDescription("category");

        categoryRepository.save(category).block();

        Long count = categoryRepository.count().block();

        assertEquals(Long.valueOf(1L), count);
    }

    @Test
    void findByDescription() {
        Category category = new Category();
        category.setDescription("category");

        categoryRepository.save(category).block();

        assertEquals("category", categoryRepository.findByDescription("category").block().getDescription());
    }
}