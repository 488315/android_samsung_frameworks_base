package com.android.systemui.keyguard;

import android.app.ActivityTaskManager;
import android.os.RemoteException;
import android.view.View;
import com.android.keyguard.KeyguardViewController;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.text.CharsKt__CharJVMKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class KeyguardViewMediatorHelperImpl$$ExternalSyntheticLambda25 implements Function0 {
    public final /* synthetic */ KeyguardViewMediatorHelperImpl f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ KeyguardViewMediatorHelperImpl$$ExternalSyntheticLambda25(KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl, int i) {
        this.f$0 = keyguardViewMediatorHelperImpl;
        this.f$1 = i;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        final KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.f$0;
        View view = ((KeyguardViewController) keyguardViewMediatorHelperImpl.viewControllerLazy.get()).getViewRootImpl().getView();
        final int i = this.f$1;
        view.post(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$keyguardGoingAway$1$1
            @Override // java.lang.Runnable
            public final void run() {
                try {
                    ActivityTaskManager.getService().keyguardGoingAway(i);
                    KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl2 = keyguardViewMediatorHelperImpl;
                    int i2 = i;
                    CharsKt__CharJVMKt.checkRadix(16);
                    String str = "keyguardGoingAway flags=0x" + Integer.toString(i2, 16);
                    keyguardViewMediatorHelperImpl2.getClass();
                    KeyguardViewMediatorHelperImpl.logD$1(str);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        return Unit.INSTANCE;
    }
}
