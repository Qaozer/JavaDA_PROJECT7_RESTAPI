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

    @Transactional(readOnly=true)
    public List<Rating> findAll(){ return ratingRepository.findAll();}

    public Rating save(Rating rating){
        return ratingRepository.save(rating);
    }

    @Transactional(readOnly=true)
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
