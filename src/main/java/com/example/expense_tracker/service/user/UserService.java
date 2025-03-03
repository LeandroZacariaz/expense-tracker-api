package com.example.expense_tracker.service.user;

import com.example.expense_tracker.domain.User;
import com.example.expense_tracker.dto.user.UserRegisterDto;

public interface UserService {
    User createUser(UserRegisterDto userRegisterDto);

    User getLoggingInUser();

    User getUserById(Long id_user);
}
