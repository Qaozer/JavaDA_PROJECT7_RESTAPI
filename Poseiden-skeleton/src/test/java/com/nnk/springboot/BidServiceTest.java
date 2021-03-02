package com.nnk.springboot;

import com.nnk.springboot.Services.BidListService;
import com.nnk.springboot.domain.BidList;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class BidServiceTest {
    @Autowired
    private BidListService bidListService;

    @Test
    public void saveBidTest(){
        BidList  bid = new BidList("Account Test", "Type Test", 10d);

        bid = bidListService.saveBid(bid);
        int id = bid.getBidListId();
        assertNotNull(bid.getBidListId());
        BidList inDb = bidListService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid bid Id:" + id));
        Assert.assertEquals(10d, inDb.getBidQuantity(), 1d);
    }

    @Test
    public void findByIdTest(){
        BidList  bid = new BidList("Account Test", "Type Test", 10d);

        bid = bidListService.saveBid(bid);
        assertTrue(bidListService.findById(bid.getBidListId()).isPresent());
    }

    @Test
    public void findAllTest(){
        int count = bidListService.findAll().size();

        BidList  bid = new BidList("Account Test", "Type Test", 10d);

        bid = bidListService.saveBid(bid);
        assertTrue(bidListService.findAll().size() == count + 1);
    }

    @Test
    public void updateBidTest(){
        BidList  bid = new BidList("Account Test", "Type Test", 10d);
        bid = bidListService.saveBid(bid);

        int id = bid.getBidListId();

        BidList nuBid = new BidList("Update Account", "Update Type",20d);
        bid = bidListService.updateBid(bid, nuBid);

        assertEquals(id, bid.getBidListId());
        assertEquals("Update Account", bid.getAccount());
        assertEquals("Update Type", bid.getType());
        assertEquals(20d, bid.getBidQuantity(),1d);
    }

    @Test
    public void deleteBidTest(){
        int count = bidListService.findAll().size();
        BidList  bid = new BidList("Account Test", "Type Test", 10d);
        bid = bidListService.saveBid(bid);

        assertTrue(bidListService.findAll().size() == count + 1);

        bidListService.deleteById(bid.getBidListId());

        assertTrue(bidListService.findAll().size() == count);
    }
}
