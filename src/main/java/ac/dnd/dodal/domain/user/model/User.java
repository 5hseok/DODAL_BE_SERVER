package ac.dnd.dodal.domain.user.model;

import ac.dnd.dodal.common.model.BaseEntity;
import ac.dnd.dodal.domain.user.enums.E_user_role;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Entity
@Getter
@DynamicUpdate
@NoArgsConstructor
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @Column(name ="device_token", nullable = false, unique = true)
    private String deviceToken;

    @Column(name="email", nullable = false, unique = true)
    private String email;

    @Column(name="role", nullable = false)
    private E_user_role role;

    @Column(name="refresh_token")
    private String refreshToken;

    @Builder
    public User(String nickname, String profileImageUrl, String deviceToken, String email, E_user_role role) {
        super(LocalDateTime.now(), LocalDateTime.now(), null);

        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.deviceToken = deviceToken;
        this.email = email;
        this.refreshToken = null;
        this.role = role;
    }


    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void updateUserNickname(String nickname) {
        if (nickname != null && !nickname.isEmpty()) {
            this.nickname = nickname;
        }
    }

    public void updateUserProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public void deleteUserProfileImageUrl() {
        this.profileImageUrl = null;
    }
}
