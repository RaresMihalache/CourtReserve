package com.example.demo.controller;

import com.example.demo.dtos.CourtDTO;
import com.example.demo.dtos.SubscriptionDTO;
import com.example.demo.model.Court;
import com.example.demo.model.Subscription;
import com.example.demo.service.SubscriptionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("/subscription")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @RequestMapping(value="/save",method= RequestMethod.POST)
    public ResponseEntity<SubscriptionDTO> save(@RequestBody Subscription subscription){
        SubscriptionDTO subscriptionDTO = subscriptionService.saveSubscription(subscription);
        return new ResponseEntity<>(subscriptionDTO, HttpStatus.OK);
    }

    @RequestMapping(value="/getSubscription/{id}",method= RequestMethod.GET)
    public ResponseEntity<SubscriptionDTO> getSubscription(@PathVariable Long id){
        SubscriptionDTO subscriptionDTO = subscriptionService.getSubscription(id);
        return new ResponseEntity<>(subscriptionDTO, HttpStatus.OK);
    }

    @RequestMapping(value="/findAll",method = RequestMethod.GET)
    public ResponseEntity<List<SubscriptionDTO>> findAllSubscriptions(){
        List<SubscriptionDTO> subscriptionsDTO = subscriptionService.findAll();
        return new ResponseEntity<>(subscriptionsDTO,HttpStatus.OK);
    }

    @Transactional
    @RequestMapping(value="/update",method = RequestMethod.PUT)
    public ResponseEntity updateSubscription(@RequestBody Subscription subscription){
        subscriptionService.updateSubscription(subscription);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    @RequestMapping(value="/delete/{id}",method = {RequestMethod.DELETE})
    public ResponseEntity deleteCourt(@PathVariable Long id){
        subscriptionService.deleteSubscription(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value="/computeTotalPrice/{id}",method= RequestMethod.GET)
    public Float computeSubscriptionPrice(@PathVariable Long id){
        return subscriptionService.computeSubscriptionPrice(id);
    }

    @GetMapping (value = "/getSubscriptions/{userId}")
    public List<Subscription> getSubscriptionByUserId(@PathVariable String userId){
        return subscriptionService.getSubscriptionByUserId(userId);
    }

}
