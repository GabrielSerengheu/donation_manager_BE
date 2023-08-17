package de.msg.javatraining.donationmanager.service;

import de.msg.javatraining.donationmanager.persistence.model.Campaign;
import de.msg.javatraining.donationmanager.persistence.repository.CampaignRepositoryImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CampaignService {

    @Autowired
    CampaignRepositoryImpl campaignRepository;

    public List<Campaign> findAll() {
        List<Campaign> campaigns = campaignRepository.findAll();
        return campaigns;
    }

    public Campaign findById(Long id) {
        return campaignRepository.findById(id);
    }

    public Campaign create(Campaign campaign) {
        campaignRepository.create(campaign);
        return campaign;
    }

    public Campaign update(Long id, Campaign updateCampaign) {
        campaignRepository.update(id, updateCampaign);
        return updateCampaign;
    }

    public void delete(Long id, Campaign campaign) {
        campaignRepository.delete(campaign);
    }

}
