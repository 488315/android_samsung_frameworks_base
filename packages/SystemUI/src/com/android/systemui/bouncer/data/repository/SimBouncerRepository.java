package com.android.systemui.bouncer.data.repository;

import com.android.systemui.bouncer.data.model.SimPukInputModel;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface SimBouncerRepository {
    static void setSimPukUserInput$default(SimBouncerRepository simBouncerRepository, String str, int i) {
        if ((i & 1) != 0) {
            str = null;
        }
        SimBouncerRepositoryImpl simBouncerRepositoryImpl = (SimBouncerRepositoryImpl) simBouncerRepository;
        simBouncerRepositoryImpl.getClass();
        simBouncerRepositoryImpl._simPukInputModel = new SimPukInputModel(str, null);
    }
}
