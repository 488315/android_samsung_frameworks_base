package com.android.wm.shell.windowdecor;

import android.app.ActivityManager;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final /* synthetic */ class DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda6 implements Function1 {
    public final /* synthetic */ DesktopModeWindowDecorViewModel f$0;

    public /* synthetic */ DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda6(DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel) {
        this.f$0 = desktopModeWindowDecorViewModel;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        this.f$0.onTaskInfoChanged((ActivityManager.RunningTaskInfo) obj);
        return Unit.INSTANCE;
    }
}
