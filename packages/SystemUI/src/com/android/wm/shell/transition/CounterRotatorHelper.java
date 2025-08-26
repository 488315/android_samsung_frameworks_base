package com.android.wm.shell.transition;

import android.graphics.Rect;
import android.util.ArrayMap;
import android.util.RotationUtils;
import android.view.SurfaceControl;
import android.window.TransitionInfo;
import android.window.WindowContainerToken;
import com.android.wm.shell.shared.CounterRotator;
import com.android.wm.shell.shared.TransitionUtil;
import java.util.List;

/* loaded from: classes3.dex */
public class CounterRotatorHelper {
    public int mLastRotationDelta;
    public final ArrayMap mRotatorMap = new ArrayMap();
    public final Rect mLastDisplayBounds = new Rect();

    public final void cleanUp(SurfaceControl.Transaction transaction) {
        for (int size = this.mRotatorMap.size() - 1; size >= 0; size--) {
            SurfaceControl surfaceControl = ((CounterRotator) this.mRotatorMap.valueAt(size)).mSurface;
            if (surfaceControl != null) {
                transaction.remove(surfaceControl);
            }
        }
        this.mRotatorMap.clear();
        this.mLastRotationDelta = 0;
    }

    public final void handleClosingChanges(TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, TransitionInfo.Change change) {
        SurfaceControl.Transaction transaction2;
        int iDeltaRotation = RotationUtils.deltaRotation(change.getStartRotation(), change.getEndRotation());
        Rect endAbsBounds = change.getEndAbsBounds();
        int iWidth = endAbsBounds.width();
        int iHeight = endAbsBounds.height();
        this.mLastRotationDelta = iDeltaRotation;
        this.mLastDisplayBounds.set(endAbsBounds);
        List changes = transitionInfo.getChanges();
        int size = changes.size();
        int i = size - 1;
        while (i >= 0) {
            TransitionInfo.Change change2 = (TransitionInfo.Change) changes.get(i);
            WindowContainerToken parent = change2.getParent();
            if (TransitionUtil.isClosingType(change2.getMode()) && TransitionInfo.isIndependent(change2, transitionInfo) && parent != null) {
                CounterRotator counterRotator = (CounterRotator) this.mRotatorMap.get(parent);
                if (counterRotator == null) {
                    counterRotator = new CounterRotator();
                    transaction2 = transaction;
                    counterRotator.setup(transaction2, transitionInfo.getChange(parent).getLeash(), iDeltaRotation, iWidth, iHeight);
                    SurfaceControl surfaceControl = counterRotator.mSurface;
                    if (surfaceControl != null) {
                        transaction2.setLayer(surfaceControl, (change2.getFlags() & 2) == 0 ? size - i : -1);
                    }
                    this.mRotatorMap.put(parent, counterRotator);
                } else {
                    transaction2 = transaction;
                }
                SurfaceControl leash = change2.getLeash();
                SurfaceControl surfaceControl2 = counterRotator.mSurface;
                if (surfaceControl2 != null) {
                    transaction2.reparent(leash, surfaceControl2);
                }
            } else {
                transaction2 = transaction;
            }
            i--;
            transaction = transaction2;
        }
    }
}
