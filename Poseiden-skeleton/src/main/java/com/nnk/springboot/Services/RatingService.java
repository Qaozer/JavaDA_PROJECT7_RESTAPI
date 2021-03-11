package com.nnk.springboot.Services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RatingService {
    @Autowired
    private RatingRepository ratingRepository;

    /**
     * Gets a list of all ratings from the database
     * @return a list of ratings
     */
    @Transactional(readOnly=true)
    public List<Rating> findAll(){ return ratingRepository.findAll();}

    /**
     * Saves a rating in the database
     * @param rating a rating
     * @return the rating in database
     */
    public Rating save(Rating rating){
        return ratingRepository.save(rating);
    }

    /**
     * Retrieves a rating from the database
     * @param id the rating id
     * @return the rating from the database or an exception if not found
     */
    @Transactional(readOnly=true)
    public Rating findById(int id){
        return ratingRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid rating Id:" + id));
    }

    /**
     * Updates a rating in database
     * @param updated the updated rating
     * @param id the rating id in database
     * @return the rating in database
     */
    public Rating update(Rating updated, int id){
        updated.setId(id);
        return ratingRepository.save(updated);
    }

    /**
     * Deletes a rating
     * @param id the rating id
     */
    public void delete(int id){
        ratingRepository.deleteById(id);
    }
}
