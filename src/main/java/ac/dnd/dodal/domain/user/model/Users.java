package ac.dnd.dodal.domain.user.model;

import ac.dnd.dodal.domain.user.enums.E_user_role;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
@Table(name = "users")
public class Users {

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

//    @Column(name="password", nullable = false)     // 이메일 회원 로직을 사용할 경우에 사용
//    private final String password;

    @Column(name="role", nullable = false)
    private E_user_role role;  // enum으로 변경 예정

    @Column(name="refresh_token")
    private String refreshToken;

    @Builder
    public Users(String nickname, String deviceToken, String email, E_user_role role) {
        this.nickname = nickname;
        this.profileImageUrl = null;
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
