package ac.dnd.dodal.application.user.repository;


import ac.dnd.dodal.domain.user.enums.E_user_role;
import ac.dnd.dodal.domain.user.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    @Query("select u.id as id, u.role as role from Users u where u.id = :id and u.refreshToken is not null")
    Optional<UserSecurityForm> findFormById(@Param("id") Long id);

    @Query("select u.id as id, u.role as role from Users u where u.id = :id and u.refreshToken = :refreshToken")
    Optional<UserSecurityForm> findFormByIdAndRefreshToken(@Param("id") Long id, @Param("refreshToken") String refreshToken);

    @Modifying(clearAutomatically = true)
    @Query("update Users u set u.refreshToken = :refreshToken where u.id = :id")
    void updateRefreshToken(Long id, String refreshToken);


    interface UserSecurityForm {
        Long getId();
        E_user_role getRole();

        // User To UserSecurityForm
        static UserSecurityForm of(Users user) {
            return new UserSecurityForm() {
                @Override
                public Long getId() {
                    return user.getId();
                }

                @Override
                public E_user_role getRole() {
                    return user.getRole();
                }
            };
        }
    }
}