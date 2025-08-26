package com.android.systemui.screenshot.policy;

import android.app.ActivityTaskManager;
import com.android.systemui.screenshot.data.model.ChildTaskModel;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public final /* synthetic */ class RootTaskInfoExtKt$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ ActivityTaskManager.RootTaskInfo f$0;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        ActivityTaskManager.RootTaskInfo rootTaskInfo = this.f$0;
        int iIntValue = ((Integer) obj).intValue();
        return new ChildTaskModel(rootTaskInfo.childTaskIds[iIntValue], rootTaskInfo.childTaskNames[iIntValue], rootTaskInfo.childTaskBounds[iIntValue], rootTaskInfo.childTaskUserIds[iIntValue]);
    }
}
