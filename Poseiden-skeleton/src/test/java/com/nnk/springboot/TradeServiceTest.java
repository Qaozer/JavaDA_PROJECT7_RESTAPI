package com.nnk.springboot;

import com.nnk.springboot.Services.TradeService;
import com.nnk.springboot.domain.Trade;
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
public class TradeServiceTest {
    @Autowired
    private TradeService tradeService;

    @Test
    public void saveTest(){

        Trade trade = new Trade("Trade Account", "Type", 10.0d);
        trade = tradeService.save(trade);
        int id = trade.getTradeId();
        assertNotNull(trade.getTradeId());
        Trade inDb = tradeService.findById(id);
        Assert.assertEquals(trade.getAccount(), inDb.getAccount());
        Assert.assertEquals(trade.getType(), inDb.getType());
        Assert.assertEquals(trade.getBuyQuantity(), inDb.getBuyQuantity(), 0d);
    }

    @Test
    public void findByIdTest(){
        Trade trade = new Trade("Trade Account", "Type", 10.0d);
        trade = tradeService.save(trade);
        assertNotNull(tradeService.findById(trade.getTradeId()));
    }

    @Test
    public void findAllTest(){
        int count = tradeService.findAll().size();

        Trade trade = new Trade("Trade Account", "Type", 10.0d);
        trade = tradeService.save(trade);
        assertEquals(count + 1, tradeService.findAll().size());
    }

    @Test
    public void updateRatingTest(){
        Trade trade = new Trade("Trade Account", "Type", 10.0d);
        trade = tradeService.save(trade);

        int id = trade.getTradeId();

        Trade nuRule = new Trade("uTrade Account", "uType", 12.0d);
        trade = tradeService.update(nuRule, id);
        assertEquals(id, trade.getTradeId());
        assertEquals(nuRule.getAccount(), trade.getAccount());
        assertEquals(nuRule.getType(), trade.getType());
        assertEquals(nuRule.getBuyQuantity(), trade.getBuyQuantity(), 0d);
    }

    @Test
    public void deleteTradeTest(){
        int count = tradeService.findAll().size();
        Trade trade = new Trade("Trade Account", "Type", 10.0d);

        trade = tradeService.save(trade);

        assertEquals(count + 1, tradeService.findAll().size());

        tradeService.delete(trade.getTradeId());

        assertEquals(count, tradeService.findAll().size());
    }
}
