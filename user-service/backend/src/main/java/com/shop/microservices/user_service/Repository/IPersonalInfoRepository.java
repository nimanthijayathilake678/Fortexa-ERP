package com.shop.microservices.user_service.Repository;

import com.shop.microservices.user_service.Model.PersonalInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for {@link PersonalInfo} entities.
 * <p>
 * This interface extends {@link JpaRepository} to provide CRUD operations and additional query methods
 * for the {@link PersonalInfo} entity.
 * </p>
 * <p>
 * The {@link Repository} annotation indicates that this interface is a Spring Data repository.
 * </p>
 *
 * @see PersonalInfo The {@link PersonalInfo} entity.
 * @see JpaRepository The {@link JpaRepository} interface providing CRUD operations.
 */
@Repository
public interface IPersonalInfoRepository extends JpaRepository<PersonalInfo, String> {
    // Additional query methods can be defined here
}