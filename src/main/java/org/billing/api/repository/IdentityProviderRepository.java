package org.billing.api.repository;
import com.sumitchouksey.book.repository.HibernateRepository;
import org.billing.api.entities.ClientEntity;
import org.billing.api.entities.RolesEntity;
import org.billing.api.entities.UserEntity;
import org.billing.api.entities.UserProductEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.List;
import org.billing.api.entities.*;

@Repository
@Transactional
public class IdentityProviderRepository extends HibernateRepository
{
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Fetch client details on the basis of client name
     * @param clientName
     *          - clientName whose details to be fetched
     * @return ClientEntity
     *          - Return ClientEntity from database table for given clientName
     * @author Sumit Chouksey  "sumitchouksey2315@gmail.com"
     */
    public ClientEntity getClientEntity(String clientName){
        CriteriaBuilder criteriaBuilder  = entityManager.getCriteriaBuilder();
        CriteriaQuery<ClientEntity> clientEntityCriteriaQuery  = criteriaBuilder.createQuery(ClientEntity.class);
        Root<ClientEntity> clientEntityRoot = clientEntityCriteriaQuery.from(ClientEntity.class);
        clientEntityCriteriaQuery.select(clientEntityRoot).distinct(true);
        clientEntityCriteriaQuery.where(
            criteriaBuilder.equal(clientEntityRoot.get(ClientEntity_.clientName),clientName),
            criteriaBuilder.equal(clientEntityRoot.get(ClientEntity_.isActive),true)
        );
        List<ClientEntity> clientEntityList = entityManager.createQuery(clientEntityCriteriaQuery).getResultList();
        if(clientEntityList!=null){
            if(!clientEntityList.isEmpty())
                return clientEntityList.get(0);
        }
        return null;
    }

    /**
     * Fetch client details on the basis of client id
     * @param clientId
     *          - clientId whose details to be fetched
     * @return ClientEntity
     *          - Return ClientEntity from database table for given clientName
     * @author Sumit Chouksey  "sumitchouksey2315@gmail.com"
     */
    public ClientEntity getClientEntity(Long clientId){
        CriteriaBuilder criteriaBuilder  = entityManager.getCriteriaBuilder();
        CriteriaQuery<ClientEntity> clientEntityCriteriaQuery  = criteriaBuilder.createQuery(ClientEntity.class);
        Root<ClientEntity> clientEntityRoot = clientEntityCriteriaQuery.from(ClientEntity.class);
        clientEntityCriteriaQuery.select(clientEntityRoot).distinct(true);
        clientEntityCriteriaQuery.where(
                criteriaBuilder.equal(clientEntityRoot.get(ClientEntity_.clientName),clientId),
                criteriaBuilder.equal(clientEntityRoot.get(ClientEntity_.isActive),true)
        );
        List<ClientEntity> clientEntityList = entityManager.createQuery(clientEntityCriteriaQuery).getResultList();
        if(clientEntityList!=null){
            if(!clientEntityList.isEmpty())
                return clientEntityList.get(0);
        }
        return null;
    }


    /**
     * Fetch user details on the basis of user email
     * @param email
     *          - user email whose details to be fetched
     * @return UserEntity
     *          - Return UserEntity from database table for given user email
     * @author Sumit Chouksey  "sumitchouksey2315@gmail.com"
     */
    public UserEntity getUserEntity(String email){
        CriteriaBuilder criteriaBuilder  = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserEntity> userEntityCriteriaQuery= criteriaBuilder.createQuery(UserEntity.class);
        Root<UserEntity> userEntityRoot= userEntityCriteriaQuery.from(UserEntity.class);
        Join<UserEntity, RolesEntity> roleEntityFetch =(Join<UserEntity, RolesEntity>)userEntityRoot.fetch(UserEntity_.rolesEntity);
        Join<RolesEntity,ClientEntity> organizationEntityJoin=(Join<RolesEntity,ClientEntity>)roleEntityFetch.fetch(RolesEntity_.clientEntity);
        Join<UserEntity, UserProductEntity> userEntityUserProductEntityJoin=(Join<UserEntity,UserProductEntity>)userEntityRoot.fetch(UserEntity_.userProductEntities);
        userEntityCriteriaQuery.select(userEntityRoot).distinct(true);
        userEntityCriteriaQuery
                .where(
                        criteriaBuilder.equal(userEntityRoot.get(UserEntity_.email),email),
                        criteriaBuilder.equal(userEntityRoot.get(UserEntity_.isActive),true)
                );
        List<UserEntity> userEntities= entityManager.createQuery( userEntityCriteriaQuery )
                .getResultList();
        if(userEntities!=null){
            if(!userEntities.isEmpty())
                return userEntities.get(0);
        }
        return null;
    }

    /**
     * Save new UserEntity or Update existing UserEntity
     * @param userEntity
     *          - userEntity - user details to be saved or updated
     * @author Sumit Chouksey  "sumitchouksey2315@gmail.com"
     */
    public void saveOrUpdateUserEntity(UserEntity userEntity){
        saveOrUpdateEntity(userEntity);
    }
}
