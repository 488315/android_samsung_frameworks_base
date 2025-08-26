package com.android.wm.shell.splitscreen;

import android.app.ActivityManager;
import android.os.IBinder;
import android.window.WindowContainerToken;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public final /* synthetic */ class StageTaskListener$$ExternalSyntheticLambda0 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ StageTaskListener$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                return ((ActivityManager.RunningTaskInfo) obj).token.equals((WindowContainerToken) obj2);
            default:
                return ((ActivityManager.RunningTaskInfo) obj).token.asBinder() == ((IBinder) obj2);
        }
    }
}
