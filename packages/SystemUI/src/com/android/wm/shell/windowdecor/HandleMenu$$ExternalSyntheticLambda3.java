package com.android.wm.shell.windowdecor;

import android.content.Intent;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class HandleMenu$$ExternalSyntheticLambda3 implements Function0 {
    public final /* synthetic */ DesktopModeWindowDecoration$$ExternalSyntheticLambda11 f$0;
    public final /* synthetic */ HandleMenu f$1;

    public /* synthetic */ HandleMenu$$ExternalSyntheticLambda3(DesktopModeWindowDecoration$$ExternalSyntheticLambda11 desktopModeWindowDecoration$$ExternalSyntheticLambda11, HandleMenu handleMenu) {
        this.f$0 = desktopModeWindowDecoration$$ExternalSyntheticLambda11;
        this.f$1 = handleMenu;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        Intent intent = this.f$1.openInAppOrBrowserIntent;
        intent.getClass();
        this.f$0.mo779invoke(intent);
        return Unit.INSTANCE;
    }
}
