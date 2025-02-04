package ac.dnd.dodal.config.security.service;

import ac.dnd.dodal.application.user.repository.UserRepository;
import ac.dnd.dodal.config.security.info.UserPrincipal;
import ac.dnd.dodal.config.security.usecase.LoadUserPrincipalByIdUseCase;
import ac.dnd.dodal.domain.user.enums.E_user_code;
import ac.dnd.dodal.domain.user.exception.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailServiceById implements LoadUserPrincipalByIdUseCase {
    private final UserRepository userRepository;

    @Override
    public UserDetails execute(Long userId, String redirectURI) {
        UserRepository.UserSecurityForm userSecurityForm = userRepository.findFormById(userId)
                .orElseThrow(() -> new UserException(E_user_code.NOT_FOUND_USER));

        return UserPrincipal.create(userSecurityForm);
    }
}
