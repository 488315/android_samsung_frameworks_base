package com.android.systemui.animation;

import android.util.ArrayMap;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;
import android.window.TransitionInfo;
import com.android.wm.shell.shared.TransitionUtil;
import java.util.ArrayList;
import java.util.function.Predicate;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class RemoteAnimationTargetCompat {
    public static RemoteAnimationTarget[] wrap(TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, ArrayMap arrayMap, Predicate predicate) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < transitionInfo.getChanges().size(); i++) {
            TransitionInfo.Change change = (TransitionInfo.Change) transitionInfo.getChanges().get(i);
            if (!TransitionUtil.isOrderOnly(change) && predicate.test(change)) {
                arrayList.add(TransitionUtil.newTarget(change, RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfo, i), false, transitionInfo, transaction, arrayMap));
            }
        }
        return (RemoteAnimationTarget[]) arrayList.toArray(new RemoteAnimationTarget[arrayList.size()]);
    }
}
