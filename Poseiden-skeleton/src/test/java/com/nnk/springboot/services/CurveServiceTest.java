package com.nnk.springboot.services;

import com.nnk.springboot.Services.CurveService;
import com.nnk.springboot.domain.CurvePoint;
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
public class CurveServiceTest {
    @Autowired
    private CurveService curveService;

    @Test
    public void saveCurveTest(){
        CurvePoint curvePoint = new CurvePoint(10, 10d, 30d);

        curvePoint = curveService.save(curvePoint);
        assertNotNull(curvePoint.getId());
        CurvePoint inDB = curveService.findById(curvePoint.getId());
        assertEquals(curvePoint.getTerm(), inDB.getTerm(), 1d);
        assertEquals(curvePoint.getValue(), inDB.getValue(), 1d);
        assertEquals(curvePoint.getCurveId(), inDB.getCurveId());
    }

    @Test
    public void findByIdTest(){
        CurvePoint  curvePoint = new CurvePoint(10, 10d, 30d);

        curvePoint = curveService.save(curvePoint);
        assertNotNull(curveService.findById(curvePoint.getId()));
    }

    @Test
    public void findAllTest(){
        int count = curveService.findAll().size();

        CurvePoint  curvePoint = new CurvePoint(10, 10d, 30d);

        curvePoint = curveService.save(curvePoint);
        assertEquals( count + 1, curveService.findAll().size());
    }

    @Test
    public void updateCurveTest(){
        CurvePoint  curvePoint = new CurvePoint(10, 10d, 30d);
        curvePoint = curveService.save(curvePoint);

        int id = curvePoint.getId();

        CurvePoint nuCurve = new CurvePoint(10, 10d, 40d);
        curvePoint = curveService.update(nuCurve, id);

        assertEquals(id, curvePoint.getId());
        assertEquals(10d, curvePoint.getTerm(), 1d);
        assertEquals(40d, curvePoint.getValue(), 1d);
        assertEquals(10, curvePoint.getCurveId());
    }

    @Test
    public void deleteCurveTest(){
        CurvePoint  curvePoint = new CurvePoint(10, 10d, 30d);
        int count = curveService.findAll().size();
        curvePoint = curveService.save(curvePoint);

        assertEquals( count + 1, curveService.findAll().size());

        curveService.delete(curvePoint.getId());

        assertEquals( count, curveService.findAll().size());
    }
}
