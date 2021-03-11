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

    /**
     * Get the list of bidLists
     * @return a list of bidLists
     */
    @Transactional(readOnly=true)
    public List<BidList> findAll(){
        return bidListRepository.findAll();
    }

    /**
     * Get a bidList by its id
     * @param id the bidListId in database
     * @return an optional of a bidList
     */
    @Transactional(readOnly=true)
    public Optional<BidList> findById(int id){
        return bidListRepository.findById(id);
    }

    /**
     * Saves a bidList in database
     * @param bid the bidList to be saved
     * @return the bidList
     */
    public BidList save(BidList bid){
        return bidListRepository.save(bid);
    }

    /**
     * Updates a bidList in database
     * @param update the updated bidlist information
     * @param id the bidListId in database
     * @return the bidList saved in database
     */
    public BidList update(BidList update, Integer id){
        update.setBidListId(id);
        return bidListRepository.save(update);
    }

    /**
     * Deletes a bidList
     * @param id the bidListId
     */
    public void deleteById(int id){
        bidListRepository.deleteById(id);
    }
}
