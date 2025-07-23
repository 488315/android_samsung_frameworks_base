package com.android.systemui.scene.domain.startable;

import com.android.systemui.CoreStartable;
import com.android.systemui.bouncer.domain.interactor.SimBouncerInteractor;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor;
import com.android.systemui.keyguard.domain.interactor.TrustInteractor;
import com.android.systemui.scene.domain.interactor.SceneInteractor;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import java.util.ArrayList;
import java.util.List;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class KeyguardStateCallbackStartable implements CoreStartable {
    public final CoroutineDispatcher backgroundDispatcher;
    public final List callbacks = new ArrayList();
    public final DeviceEntryInteractor deviceEntryInteractor;
    public final SceneInteractor sceneInteractor;
    public final SelectedUserInteractor selectedUserInteractor;
    public final SimBouncerInteractor simBouncerInteractor;
    public final TrustInteractor trustInteractor;

    public KeyguardStateCallbackStartable(CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, SceneInteractor sceneInteractor, SelectedUserInteractor selectedUserInteractor, DeviceEntryInteractor deviceEntryInteractor, SimBouncerInteractor simBouncerInteractor, TrustInteractor trustInteractor) {
        this.backgroundDispatcher = coroutineDispatcher;
        this.sceneInteractor = sceneInteractor;
        this.selectedUserInteractor = selectedUserInteractor;
        this.deviceEntryInteractor = deviceEntryInteractor;
        this.simBouncerInteractor = simBouncerInteractor;
        this.trustInteractor = trustInteractor;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
    }
}
