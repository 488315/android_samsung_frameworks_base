package com.android.systemui.doze;

import com.android.app.tracing.TraceUtils;
import com.android.systemui.statusbar.phone.DozeServiceHost;
import com.android.systemui.statusbar.phone.DozeServiceHost$$ExternalSyntheticLambda2;

/* loaded from: classes2.dex */
public final /* synthetic */ class DozeUi$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ DozeHost f$0;

    public /* synthetic */ DozeUi$$ExternalSyntheticLambda0(DozeHost dozeHost) {
        this.f$0 = dozeHost;
    }

    @Override // java.lang.Runnable
    public final void run() {
        DozeServiceHost dozeServiceHost = (DozeServiceHost) this.f$0;
        dozeServiceHost.getClass();
        TraceUtils.trace("DozeServiceHost#dozeTimeTick", new DozeServiceHost$$ExternalSyntheticLambda2(dozeServiceHost));
    }
}
