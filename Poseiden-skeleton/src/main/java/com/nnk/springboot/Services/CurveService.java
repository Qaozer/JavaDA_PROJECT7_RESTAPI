package com.nnk.springboot.Services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurveService {
    @Autowired
    private CurvePointRepository curvePointRepository;

    public List<CurvePoint> findAll(){
        return curvePointRepository.findAll();
    }

    public CurvePoint save(CurvePoint curvePoint){
        return curvePointRepository.save(curvePoint);
    }

    public CurvePoint findById(int id){
        return curvePointRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid curvePoint Id:" + id));
    }

    public CurvePoint update(CurvePoint old, CurvePoint updated){
        old.setTerm(updated.getTerm());
        old.setValue(updated.getValue());
        return curvePointRepository.save(old);
    }

    public void delete(CurvePoint curvePoint){
        curvePointRepository.delete(curvePoint);
    }
}
