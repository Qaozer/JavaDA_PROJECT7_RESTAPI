package com.nnk.springboot.Services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BidListService {

    @Autowired
    private BidListRepository bidListRepository;

    @Transactional(readOnly=true)
    public List<BidList> findAll(){
        return bidListRepository.findAll();
    }

    @Transactional(readOnly=true)
    public Optional<BidList> findById(int id){
        return bidListRepository.findById(id);
    }

    public BidList saveBid(BidList bid){
        return bidListRepository.save(bid);
    }

    public BidList updateBid(BidList bid, BidList update){
        bid.setAccount(update.getAccount());
        bid.setType(update.getType());
        bid.setBidQuantity(update.getBidQuantity());
        return bidListRepository.save(bid);
    }

    public void deleteById(int id){
        bidListRepository.deleteById(id);
    }
}
