package com.android.wm.shell.recents;

import android.app.ActivityManager;
import android.window.TransitionInfo;
import com.android.wm.shell.recents.RecentsTransitionHandler;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public final /* synthetic */ class RecentsTransitionHandler$RecentsController$$ExternalSyntheticLambda0 implements Predicate {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                int i = RecentsTransitionHandler.RecentsController.$r8$clinit;
                if (((ActivityManager.RunningTaskInfo) obj).getActivityType() == 2) {
                    break;
                }
                break;
            default:
                int i2 = RecentsTransitionHandler.RecentsController.$r8$clinit;
                if (((TransitionInfo.Change) obj).getMinimizeAnimState() == 1) {
                    break;
                }
                break;
        }
        return true;
    }
}
