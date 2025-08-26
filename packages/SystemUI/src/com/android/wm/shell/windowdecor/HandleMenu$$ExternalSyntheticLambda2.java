package com.android.wm.shell.windowdecor;

import android.content.Intent;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* loaded from: classes3.dex */
public final /* synthetic */ class HandleMenu$$ExternalSyntheticLambda2 implements Function0 {
    public final /* synthetic */ DesktopModeWindowDecoration$$ExternalSyntheticLambda12 f$0;
    public final /* synthetic */ HandleMenu f$1;

    public /* synthetic */ HandleMenu$$ExternalSyntheticLambda2(DesktopModeWindowDecoration$$ExternalSyntheticLambda12 desktopModeWindowDecoration$$ExternalSyntheticLambda12, HandleMenu handleMenu) {
        this.f$0 = desktopModeWindowDecoration$$ExternalSyntheticLambda12;
        this.f$1 = handleMenu;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        Intent intent = this.f$1.openInAppOrBrowserIntent;
        intent.getClass();
        this.f$0.mo781invoke(intent);
        return Unit.INSTANCE;
    }
}
