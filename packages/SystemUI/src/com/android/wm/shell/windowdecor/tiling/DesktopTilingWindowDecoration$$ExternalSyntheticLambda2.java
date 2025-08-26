package com.android.wm.shell.windowdecor.tiling;

import android.app.ActivityManager;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* loaded from: classes3.dex */
public final /* synthetic */ class DesktopTilingWindowDecoration$$ExternalSyntheticLambda2 implements Function0 {
    public final /* synthetic */ DesktopTilingWindowDecoration f$0;
    public final /* synthetic */ ActivityManager.RunningTaskInfo f$1;
    public final /* synthetic */ boolean f$2;

    public /* synthetic */ DesktopTilingWindowDecoration$$ExternalSyntheticLambda2(DesktopTilingWindowDecoration desktopTilingWindowDecoration, ActivityManager.RunningTaskInfo runningTaskInfo, boolean z) {
        this.f$0 = desktopTilingWindowDecoration;
        this.f$1 = runningTaskInfo;
        this.f$2 = z;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        ActivityManager.RunningTaskInfo runningTaskInfo = this.f$1;
        String str = DesktopTilingWindowDecoration.TAG;
        this.f$0.initTilingForDisplayIfNeeded(this.f$2, runningTaskInfo.configuration);
        return Unit.INSTANCE;
    }
}
