package ac.dnd.dodal.domain.user.model;

import ac.dnd.dodal.domain.user.enums.E_user_type;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "user_guides")
@Getter
@RequiredArgsConstructor
public class UserGuides {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_guide_id", nullable = false)
    private Long userGuideId;

    @Column(nullable = false)
    private E_user_type type;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @MapsId
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private Users user;
}
