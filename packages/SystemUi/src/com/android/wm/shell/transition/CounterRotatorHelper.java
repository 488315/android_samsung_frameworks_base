package com.android.wm.shell.transition;

import android.graphics.Rect;
import android.util.ArrayMap;
import android.util.RotationUtils;
import android.view.SurfaceControl;
import android.window.TransitionInfo;
import android.window.WindowContainerToken;
import com.android.wm.shell.util.CounterRotator;
import com.android.wm.shell.util.TransitionUtil;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class CounterRotatorHelper {
    public int mLastRotationDelta;
    public final ArrayMap mRotatorMap = new ArrayMap();
    public final Rect mLastDisplayBounds = new Rect();

    public final void cleanUp(SurfaceControl.Transaction transaction) {
        ArrayMap arrayMap = this.mRotatorMap;
        int size = arrayMap.size();
        while (true) {
            size--;
            if (size < 0) {
                arrayMap.clear();
                this.mLastRotationDelta = 0;
                return;
            } else {
                SurfaceControl surfaceControl = ((CounterRotator) arrayMap.valueAt(size)).mSurface;
                if (surfaceControl != null) {
                    transaction.remove(surfaceControl);
                }
            }
        }
    }

    public final void handleClosingChanges(SurfaceControl.Transaction transaction, TransitionInfo.Change change, TransitionInfo transitionInfo) {
        int i;
        CounterRotatorHelper counterRotatorHelper = this;
        TransitionInfo transitionInfo2 = transitionInfo;
        int deltaRotation = RotationUtils.deltaRotation(change.getStartRotation(), change.getEndRotation());
        Rect endAbsBounds = change.getEndAbsBounds();
        int width = endAbsBounds.width();
        int height = endAbsBounds.height();
        counterRotatorHelper.mLastRotationDelta = deltaRotation;
        counterRotatorHelper.mLastDisplayBounds.set(endAbsBounds);
        List changes = transitionInfo.getChanges();
        int size = changes.size();
        int i2 = size - 1;
        while (i2 >= 0) {
            TransitionInfo.Change change2 = (TransitionInfo.Change) changes.get(i2);
            WindowContainerToken parent = change2.getParent();
            if (TransitionUtil.isClosingType(change2.getMode()) && TransitionInfo.isIndependent(change2, transitionInfo2) && parent != null) {
                ArrayMap arrayMap = counterRotatorHelper.mRotatorMap;
                CounterRotator counterRotator = (CounterRotator) arrayMap.get(parent);
                if (counterRotator == null) {
                    CounterRotator counterRotator2 = new CounterRotator();
                    i = deltaRotation;
                    counterRotator2.setup(width, height, deltaRotation, transaction, transitionInfo2.getChange(parent).getLeash());
                    SurfaceControl surfaceControl = counterRotator2.mSurface;
                    if (surfaceControl != null) {
                        transaction.setLayer(surfaceControl, (change2.getFlags() & 2) == 0 ? size - i2 : -1);
                    }
                    arrayMap.put(parent, counterRotator2);
                    counterRotator = counterRotator2;
                } else {
                    i = deltaRotation;
                }
                SurfaceControl leash = change2.getLeash();
                SurfaceControl surfaceControl2 = counterRotator.mSurface;
                if (surfaceControl2 != null) {
                    transaction.reparent(leash, surfaceControl2);
                }
            } else {
                i = deltaRotation;
            }
            i2--;
            counterRotatorHelper = this;
            transitionInfo2 = transitionInfo;
            deltaRotation = i;
        }
    }
}
