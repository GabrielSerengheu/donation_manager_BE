package de.msg.javatraining.donationmanager.controller.app;

import de.msg.javatraining.donationmanager.persistence.model.Campaign;
import de.msg.javatraining.donationmanager.persistence.modelDTO.CampaignDto;
import de.msg.javatraining.donationmanager.service.CampaignConverter;
import de.msg.javatraining.donationmanager.service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CampaignController {

    @Autowired
    private CampaignService campaignService;
    private final CampaignConverter campaignConverter = new CampaignConverter();

    @GetMapping("/campaign")
    public List<CampaignDto> findAll() {
        List<Campaign> campaigns = campaignService.findAll();
        return campaigns.stream()
                .map(campaignConverter::campaignToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/campaign/{id}")
    public Campaign findById(@PathVariable Long id) {
        return campaignService.findById(id);
    }

    @PostMapping("/campaign/create")
    @ResponseBody
    public ResponseEntity<?> createCampaign(@RequestBody Campaign campaign) {
        try {
            Campaign createdCampaign = campaignService.create(campaign);
            return new ResponseEntity<>(createdCampaign, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Campaign name already exists.", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/campaign/update/{id}")
    @ResponseBody
    public ResponseEntity<?> updateCampaign(@PathVariable Long id, @RequestBody Campaign updateCampaign) {
        try {
            Campaign updatedCampaign = campaignService.update(id, updateCampaign);
            return new ResponseEntity<>(updatedCampaign, HttpStatus.OK);
        }
        catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Campaign name already exists.", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/campaign/delete/{id}")
    public void deleteCampaign(@PathVariable Long id, @RequestBody Campaign campaign) {
        campaignService.delete(id, campaign);
    }
}
