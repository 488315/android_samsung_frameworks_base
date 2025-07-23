package com.android.systemui.statusbar.phone;

import android.view.KeyEvent;
import com.android.systemui.doze.DozeReceiver;
import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class DozeServiceHost$$ExternalSyntheticLambda2 implements Function0 {
    public final /* synthetic */ DozeServiceHost f$0;

    public /* synthetic */ DozeServiceHost$$ExternalSyntheticLambda2(DozeServiceHost dozeServiceHost) {
        this.f$0 = dozeServiceHost;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        DozeServiceHost dozeServiceHost = this.f$0;
        KeyguardRepositoryImpl keyguardRepositoryImpl = (KeyguardRepositoryImpl) dozeServiceHost.mDozeInteractor.keyguardRepository;
        keyguardRepositoryImpl._dozeTimeTick.updateState(null, Long.valueOf(keyguardRepositoryImpl.systemClock.uptimeMillis()));
        dozeServiceHost.mAuthController.dozeTimeTick();
        KeyEvent.Callback callback = dozeServiceHost.mAmbientIndicationContainer;
        if (callback instanceof DozeReceiver) {
            ((DozeReceiver) callback).dozeTimeTick();
        }
        return Unit.INSTANCE;
    }
}
