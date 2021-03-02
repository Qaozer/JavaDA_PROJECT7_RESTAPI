package com.nnk.springboot.Services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {
    @Autowired
    private RatingRepository ratingRepository;

    public List<Rating> findAll(){ return ratingRepository.findAll();}

    public Rating save(Rating rating){
        return ratingRepository.save(rating);
    }

    public Rating findById(int id){
        return ratingRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid rating Id:" + id));
    }

    public Rating update(Rating rating, int id){
        rating.setId(id);
        return ratingRepository.save(rating);
    }

    public void delete(int id){
        Rating rating = this.findById(id);
        ratingRepository.delete(rating);
    }
}
