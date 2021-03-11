package com.nnk.springboot.Services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CurveService {
    @Autowired
    private CurvePointRepository curvePointRepository;

    /**
     * Gets a list of curvePoints from the database
     * @return a list of curvePoints
     */
    @Transactional(readOnly=true)
    public List<CurvePoint> findAll(){
        return curvePointRepository.findAll();
    }

    /**
     * Saves a curvePoint in database
     * @param curvePoint the curvePoint
     * @return the curvePoint in database
     */
    public CurvePoint save(CurvePoint curvePoint){
        return curvePointRepository.save(curvePoint);
    }

    /**
     * Finds a curvePoint by its id
     * @param id the curvePoint id
     * @return the curvePoint or an exception if it was not found
     */
    @Transactional(readOnly=true)
    public CurvePoint findById(int id){
        return curvePointRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid curvePoint Id:" + id));
    }

    /**
     * Updates a curvePoint
     * @param old the curvePoint in database
     * @param updated the curvePoint with updated infos
     * @return the curvePoint in database
     */
    public CurvePoint update(CurvePoint old, CurvePoint updated){
        old.setTerm(updated.getTerm());
        old.setValue(updated.getValue());
        return curvePointRepository.save(old);
    }

    /**
     * Deletes a curvePoint
     * @param curvePoint the curvePoint
     */
    public void delete(CurvePoint curvePoint){
        curvePointRepository.delete(curvePoint);
    }
}
