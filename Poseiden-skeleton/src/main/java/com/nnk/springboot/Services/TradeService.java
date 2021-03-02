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

    @Transactional(readOnly=true)
    public List<Trade> findAll(){ return tradeRepository.findAll();}

    public Trade save(Trade trade){
        return tradeRepository.save(trade);
    }

    @Transactional(readOnly=true)
    public Trade findById(int id){
        return tradeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid trade Id:" + id));
    }

    public Trade update(Trade trade, int id){
        trade.setTradeId(id);
        return tradeRepository.save(trade);
    }

    public void delete(int id){
        Trade trade = this.findById(id);
        tradeRepository.delete(trade);
    }
}
