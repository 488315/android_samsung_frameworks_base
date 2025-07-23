package com.android.systemui.screenshot.policy;

import android.app.ActivityTaskManager;
import com.android.systemui.screenshot.data.model.ChildTaskModel;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class RootTaskInfoExtKt$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ ActivityTaskManager.RootTaskInfo f$0;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        ActivityTaskManager.RootTaskInfo rootTaskInfo = this.f$0;
        int intValue = ((Integer) obj).intValue();
        return new ChildTaskModel(rootTaskInfo.childTaskIds[intValue], rootTaskInfo.childTaskNames[intValue], rootTaskInfo.childTaskBounds[intValue], rootTaskInfo.childTaskUserIds[intValue]);
    }
}
