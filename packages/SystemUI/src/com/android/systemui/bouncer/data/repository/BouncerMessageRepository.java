package com.android.systemui.bouncer.data.repository;

import com.android.systemui.bouncer.shared.model.BouncerMessageModel;

/* loaded from: classes.dex */
public interface BouncerMessageRepository {
    static void setMessage$default(BouncerMessageRepository bouncerMessageRepository, BouncerMessageModel bouncerMessageModel) {
        BouncerMessageRepositoryImpl bouncerMessageRepositoryImpl = (BouncerMessageRepositoryImpl) bouncerMessageRepository;
        bouncerMessageRepositoryImpl._bouncerMessage.setValue(bouncerMessageModel);
        bouncerMessageRepositoryImpl.messageSource = null;
    }
}
