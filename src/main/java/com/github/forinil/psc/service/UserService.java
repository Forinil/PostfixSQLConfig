package com.github.forinil.psc.service;

import com.github.forinil.psc.model.create.UserCreateModel;
import com.github.forinil.psc.model.edit.UserEditModel;
import com.github.forinil.psc.model.view.UserViewModel;

public interface UserService extends Service<String, UserCreateModel, UserViewModel, UserEditModel> {
}
