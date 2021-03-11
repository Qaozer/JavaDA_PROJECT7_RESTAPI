package com.nnk.springboot.Services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TradeService {
    @Autowired
    private TradeRepository tradeRepository;

    /**
     * Retrieves a list of all Trades from database
     * @return a list of Trades
     */
    @Transactional(readOnly=true)
    public List<Trade> findAll(){ return tradeRepository.findAll();}

    /**
     * Saves a trade in database
     * @param trade the trade
     * @return the trade in database
     */
    public Trade save(Trade trade){
        return tradeRepository.save(trade);
    }

    /**
     * Retrieves a trade from the database
     * @param id the trade id
     * @return the trade or an exception if the trade was not found
     */
    @Transactional(readOnly=true)
    public Trade findById(int id){
        return tradeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid trade Id:" + id));
    }

    /**
     * Updates a trade in database
     * @param trade the updated trade
     * @param id the trade id in database
     * @return the trade in database
     */
    public Trade update(Trade trade, int id){
        trade.setTradeId(id);
        return tradeRepository.save(trade);
    }

    /**
     * Deletes a trade from the database
     * @param id the trade id
     */
    public void delete(int id){
        tradeRepository.deleteById(id);
    }
}
