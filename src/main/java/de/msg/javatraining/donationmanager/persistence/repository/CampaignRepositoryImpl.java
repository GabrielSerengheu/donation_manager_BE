package de.msg.javatraining.donationmanager.persistence.repository;

import de.msg.javatraining.donationmanager.persistence.model.Campaign;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
@Transactional
public class CampaignRepositoryImpl implements CampaignRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Campaign create(Campaign campaign) {
        String name = campaign.getName();
        if (findByName(name) != null) {
           throw new IllegalArgumentException("Name already exists.");
        }
        else {
            entityManager.persist(campaign);
            return campaign;
        }
    }

    @Override
    public Campaign update(Long id, Campaign campaign) {
        Campaign existingCampaign = entityManager.find(Campaign.class, id);
        if (existingCampaign != null) {
            if (!campaign.getName().isEmpty()) {
                if (!campaign.getName().equals(existingCampaign.getName())) {
                    Campaign campaignWithSameName = findByName(campaign.getName());
                    if (campaignWithSameName == null || campaignWithSameName.getId().equals(id)) {
                        existingCampaign.setName(campaign.getName());
                    } else {
                       throw new IllegalArgumentException("Name already exists.");
                    }
                }
            }
            if (!campaign.getPurpose().isEmpty()) {
                existingCampaign.setPurpose(campaign.getPurpose());
            }
            entityManager.merge(existingCampaign);
        }
        return existingCampaign;
    }

    @Override
    public void delete(Campaign campaign) {

        entityManager.remove(campaign);
    }

    @Override
    public List<Campaign> findAll() {
        return entityManager.createQuery("SELECT c FROM Campaign c", Campaign.class).getResultList();
    }

    @Override
    public Campaign findById(long id) {
        return entityManager.find(Campaign.class, id);
    }

    @Override
    public Campaign findByName(String name) {
        TypedQuery<Campaign> query = entityManager.createQuery(
                "SELECT c FROM Campaign c WHERE c.name = :name", Campaign.class);
        query.setParameter("name", name); // Bind the parameter

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null; // Return null if no matching campaign is found
        }
    }
}
