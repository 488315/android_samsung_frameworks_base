package com.android.systemui.shared.system;

import android.util.ArrayMap;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;
import android.window.TransitionInfo;
import com.android.p038wm.shell.util.TransitionUtil;
import java.util.ArrayList;
import java.util.function.Predicate;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class RemoteAnimationTargetCompat {
    public static RemoteAnimationTarget[] wrap(TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, ArrayMap arrayMap, Predicate predicate) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < transitionInfo.getChanges().size(); i++) {
            TransitionInfo.Change change = (TransitionInfo.Change) transitionInfo.getChanges().get(i);
            if (!TransitionUtil.isOrderOnly(change) && predicate.test(change)) {
                arrayList.add(TransitionUtil.newTarget(change, transitionInfo.getChanges().size() - i, transitionInfo, transaction, arrayMap));
            }
        }
        return (RemoteAnimationTarget[]) arrayList.toArray(new RemoteAnimationTarget[arrayList.size()]);
    }
}
