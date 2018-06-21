package com.github.forinil.psc.service;

import com.github.forinil.psc.model.create.TransportCreateModel;
import com.github.forinil.psc.model.edit.TransportEditModel;
import com.github.forinil.psc.model.view.TransportViewModel;

public interface TransportService extends Service<String, TransportCreateModel, TransportViewModel, TransportEditModel> {
}
