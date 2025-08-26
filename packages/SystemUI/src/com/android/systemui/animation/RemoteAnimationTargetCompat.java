package com.android.systemui.animation;

import android.util.ArrayMap;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;
import android.window.TransitionInfo;
import com.android.wm.shell.shared.TransitionUtil;
import java.util.ArrayList;
import java.util.function.Predicate;

/* loaded from: classes.dex */
public class RemoteAnimationTargetCompat {
    public static RemoteAnimationTarget[] wrap(TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, ArrayMap arrayMap, Predicate predicate) {
        TransitionInfo transitionInfo2;
        SurfaceControl.Transaction transaction2;
        ArrayMap arrayMap2;
        ArrayList arrayList = new ArrayList();
        int i = 0;
        while (i < transitionInfo.getChanges().size()) {
            TransitionInfo.Change change = (TransitionInfo.Change) transitionInfo.getChanges().get(i);
            if (!TransitionUtil.isOrderOnly(change) && predicate.test(change)) {
                transitionInfo2 = transitionInfo;
                transaction2 = transaction;
                arrayMap2 = arrayMap;
                arrayList.add(TransitionUtil.newTarget(change, RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfo, i), false, transitionInfo2, transaction2, arrayMap2));
            } else {
                transitionInfo2 = transitionInfo;
                transaction2 = transaction;
                arrayMap2 = arrayMap;
            }
            i++;
            transitionInfo = transitionInfo2;
            transaction = transaction2;
            arrayMap = arrayMap2;
        }
        return (RemoteAnimationTarget[]) arrayList.toArray(new RemoteAnimationTarget[arrayList.size()]);
    }
}
