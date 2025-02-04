package ac.dnd.dodal.config.security.usecase;

import ac.dnd.dodal.annotation.UseCase;
import org.springframework.security.core.userdetails.UserDetails;

@UseCase
public interface LoadUserPrincipalByIdUseCase {

    UserDetails execute(Long userId, String redirectURI);
}
