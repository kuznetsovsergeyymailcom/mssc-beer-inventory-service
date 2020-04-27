package kss.springframework.beerinventoryservice.services;

import kss.springframework.beerinventoryservice.config.JMSConfig;
import kss.springframework.beerinventoryservice.domain.BeerInventory;
import kss.springframework.beerinventoryservice.repositories.BeerInventoryRepository;
import kss.brewery.model.events.NewInventoryEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class InventoryListener {
    private final BeerInventoryRepository beerInventoryRepository;


    @JmsListener(destination = JMSConfig.INVENTORY_REQUEST_QUEUE)
    public void listener(NewInventoryEvent event){

        System.out.println("Received event: "+event.toString());
        beerInventoryRepository.save(BeerInventory.builder()
                .beerId(event.getBeerDto().getId())
                .upc(event.getBeerDto().getUpc())
                .quantityOnHand(event.getBeerDto().getQuantityOnHand())
                .build()
        );
    }
}
