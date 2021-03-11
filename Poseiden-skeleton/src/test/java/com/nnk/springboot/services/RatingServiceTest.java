package com.nnk.springboot.services;

import com.nnk.springboot.Services.RatingService;
import com.nnk.springboot.domain.Rating;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class RatingServiceTest {
    @Autowired
    private RatingService ratingService;

    @Test
    public void saveTest(){
        Rating rating = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);

        rating = ratingService.save(rating);
        int id = rating.getId();
        assertNotNull(rating.getId());
        Rating inDb = ratingService.findById(id);
        Assert.assertEquals(rating.getMoodysRating(), inDb.getMoodysRating());
        Assert.assertEquals(rating.getSandPRating(), inDb.getSandPRating());
        Assert.assertEquals(rating.getFitchRating(), inDb.getFitchRating());
        Assert.assertEquals(rating.getOrderNumber(), inDb.getOrderNumber());
    }

    @Test
    public void findByIdTest(){
        Rating  rating = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);

        rating = ratingService.save(rating);
        assertNotNull(ratingService.findById(rating.getId()));
    }

    @Test
    public void findAllTest(){
        int count = ratingService.findAll().size();

        Rating  rating = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);

        rating = ratingService.save(rating);
        assertEquals(count + 1, ratingService.findAll().size());
    }

    @Test
    public void updateRatingTest(){
        Rating  rating = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);
        rating = ratingService.save(rating);

        int id = rating.getId();

        Rating nuRating = new Rating("uMoodys Rating", "uSand PRating", "uFitch Rating", 20);
        rating = ratingService.update(nuRating, id);

        assertEquals(id, rating.getId());
        assertEquals(nuRating.getMoodysRating(), rating.getMoodysRating());
        assertEquals(nuRating.getSandPRating(), rating.getSandPRating());
        assertEquals(nuRating.getFitchRating(), rating.getFitchRating());
        assertEquals(nuRating.getOrderNumber(), rating.getOrderNumber());
    }

    @Test
    public void deleteRatingTest(){
        int count = ratingService.findAll().size();
        Rating  rating = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);
        rating = ratingService.save(rating);

        assertEquals(count + 1, ratingService.findAll().size());
        ratingService.delete(rating.getId());

        assertEquals(count, ratingService.findAll().size());
    }
}
