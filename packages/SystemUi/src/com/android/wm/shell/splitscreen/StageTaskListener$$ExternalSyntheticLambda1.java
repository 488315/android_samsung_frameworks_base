package com.android.wm.shell.splitscreen;

import android.app.ActivityManager;
import android.os.IBinder;
import android.window.WindowContainerToken;
import java.util.function.Predicate;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class StageTaskListener$$ExternalSyntheticLambda1 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ StageTaskListener$$ExternalSyntheticLambda1(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return ((ActivityManager.RunningTaskInfo) obj).token.equals((WindowContainerToken) this.f$0);
            default:
                return ((ActivityManager.RunningTaskInfo) obj).token.asBinder() == ((IBinder) this.f$0);
        }
    }
}
