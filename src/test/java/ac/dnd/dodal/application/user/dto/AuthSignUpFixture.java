package ac.dnd.dodal.application.user.dto;

import ac.dnd.dodal.domain.user.UserFixture;
import ac.dnd.dodal.domain.user.model.User;

public class AuthSignUpFixture {

  public static User signUpUser() {
    return UserFixture.createUser();
  }

}
