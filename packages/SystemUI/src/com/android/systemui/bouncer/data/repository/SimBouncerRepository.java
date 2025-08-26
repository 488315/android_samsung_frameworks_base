package com.android.systemui.bouncer.data.repository;

import com.android.systemui.bouncer.data.model.SimPukInputModel;

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
