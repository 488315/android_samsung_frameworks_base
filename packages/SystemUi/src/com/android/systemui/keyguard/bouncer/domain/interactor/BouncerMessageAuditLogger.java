package com.android.systemui.keyguard.bouncer.domain.interactor;

import android.os.Build;
import com.android.systemui.CoreStartable;
import com.android.systemui.keyguard.bouncer.data.repository.BouncerMessageRepository;
import com.android.systemui.keyguard.bouncer.data.repository.BouncerMessageRepositoryImpl;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class BouncerMessageAuditLogger implements CoreStartable {
    public final BouncerMessageInteractor interactor;
    public final BouncerMessageRepository repository;
    public final CoroutineScope scope;

    public BouncerMessageAuditLogger(CoroutineScope coroutineScope, BouncerMessageRepository bouncerMessageRepository, BouncerMessageInteractor bouncerMessageInteractor) {
        this.scope = coroutineScope;
        this.repository = bouncerMessageRepository;
        this.interactor = bouncerMessageInteractor;
    }

    public final void collectAndLog(Flow flow, String str) {
        BuildersKt.launch$default(this.scope, null, null, new BouncerMessageAuditLogger$collectAndLog$1(flow, str, null), 3);
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        if (Build.isDebuggable()) {
            BouncerMessageRepositoryImpl bouncerMessageRepositoryImpl = (BouncerMessageRepositoryImpl) this.repository;
            collectAndLog(bouncerMessageRepositoryImpl.biometricAuthMessage, "biometricMessage: ");
            collectAndLog(bouncerMessageRepositoryImpl.primaryAuthMessage, "primaryAuthMessage: ");
            collectAndLog(bouncerMessageRepositoryImpl.customMessage, "customMessage: ");
            collectAndLog(bouncerMessageRepositoryImpl.faceAcquisitionMessage, "faceAcquisitionMessage: ");
            collectAndLog(bouncerMessageRepositoryImpl.fingerprintAcquisitionMessage, "fingerprintAcquisitionMessage: ");
            collectAndLog(bouncerMessageRepositoryImpl.authFlagsMessage, "authFlagsMessage: ");
            collectAndLog(this.interactor.bouncerMessage, "interactor.bouncerMessage: ");
        }
    }
}
