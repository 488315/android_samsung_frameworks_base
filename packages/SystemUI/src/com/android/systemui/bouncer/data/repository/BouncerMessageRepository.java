package com.android.systemui.bouncer.data.repository;

import com.android.systemui.bouncer.shared.model.BouncerMessageModel;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface BouncerMessageRepository {
    static void setMessage$default(BouncerMessageRepository bouncerMessageRepository, BouncerMessageModel bouncerMessageModel) {
        BouncerMessageRepositoryImpl bouncerMessageRepositoryImpl = (BouncerMessageRepositoryImpl) bouncerMessageRepository;
        bouncerMessageRepositoryImpl._bouncerMessage.setValue(bouncerMessageModel);
        bouncerMessageRepositoryImpl.messageSource = null;
    }
}
