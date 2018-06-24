package com.github.forinil.psc.service;

import com.github.forinil.psc.model.create.DomainCreateModel;
import com.github.forinil.psc.model.edit.DomainEditModel;
import com.github.forinil.psc.model.view.DomainViewModel;

public interface DomainService extends Service<String, DomainCreateModel, DomainViewModel, DomainEditModel> {
}
