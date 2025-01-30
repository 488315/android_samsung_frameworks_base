package com.android.wm.shell.pip.phone;

import android.content.Context;
import android.graphics.Rect;
import android.os.SystemProperties;
import android.util.ArraySet;
import com.android.wm.shell.pip.PipBoundsAlgorithm;
import com.android.wm.shell.pip.PipBoundsState;
import com.android.wm.shell.pip.PipKeepClearAlgorithmInterface;
import com.android.systemui.R;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PhonePipKeepClearAlgorithm implements PipKeepClearAlgorithmInterface {
    public final boolean mKeepClearAreaGravityEnabled = SystemProperties.getBoolean("persist.wm.debug.enable_pip_keep_clear_algorithm_gravity", false);
    public int mKeepClearAreasPadding;

    public PhonePipKeepClearAlgorithm(Context context) {
        this.mKeepClearAreasPadding = context.getResources().getDimensionPixelSize(R.dimen.pip_keep_clear_areas_padding);
    }

    public static boolean tryOffset(int i, int i2, Rect rect, Rect rect2, Rect rect3) {
        Rect rect4 = new Rect(rect);
        rect4.offset(i, i2);
        if (Rect.intersects(rect2, rect4) || !rect3.contains(rect4)) {
            return false;
        }
        rect.offsetTo(rect4.left, rect4.top);
        return true;
    }

    @Override // com.android.wm.shell.pip.PipKeepClearAlgorithmInterface
    public final Rect adjust(PipBoundsState pipBoundsState, PipBoundsAlgorithm pipBoundsAlgorithm) {
        Rect entryDestinationBoundsIgnoringKeepClearAreas = pipBoundsState.getBounds().isEmpty() ? pipBoundsAlgorithm.getEntryDestinationBoundsIgnoringKeepClearAreas() : pipBoundsState.getBounds();
        Rect rect = new Rect();
        pipBoundsAlgorithm.getInsetBounds(rect);
        if (pipBoundsState.mIsImeShowing) {
            rect.bottom -= pipBoundsState.mImeHeight;
        }
        if (pipBoundsState.isStashed()) {
            int i = entryDestinationBoundsIgnoringKeepClearAreas.bottom;
            int i2 = rect.bottom;
            if (i > i2 || entryDestinationBoundsIgnoringKeepClearAreas.top < rect.top) {
                entryDestinationBoundsIgnoringKeepClearAreas.offset(0, i2 - i);
            }
            return entryDestinationBoundsIgnoringKeepClearAreas;
        }
        Rect rect2 = new Rect(entryDestinationBoundsIgnoringKeepClearAreas);
        boolean z = !rect.contains(rect2);
        if (!pipBoundsState.mHasUserMovedPip && !pipBoundsState.mHasUserResizedPip) {
            z = true;
        }
        if (this.mKeepClearAreaGravityEnabled || z) {
            float snapFraction = pipBoundsAlgorithm.mSnapAlgorithm.getSnapFraction(0, entryDestinationBoundsIgnoringKeepClearAreas, pipBoundsAlgorithm.getMovementBounds(entryDestinationBoundsIgnoringKeepClearAreas, true));
            char c = (snapFraction < 0.5f || snapFraction >= 2.5f) ? (char) 3 : (char) 5;
            rect2.offsetTo(rect2.left, rect.bottom - rect2.height());
            if (c == 5) {
                rect2.offsetTo(rect.right - rect2.width(), rect2.top);
            } else {
                rect2.offsetTo(rect.left, rect2.top);
            }
        }
        return findUnoccludedPosition(rect2, pipBoundsState.mRestrictedKeepClearAreas, pipBoundsState.getUnrestrictedKeepClearAreas(), rect);
    }

    @Override // com.android.wm.shell.pip.PipKeepClearAlgorithmInterface
    public final Rect findUnoccludedPosition(Rect rect, Set set, Set set2, Rect rect2) {
        ArraySet arraySet = (ArraySet) set;
        if (arraySet.isEmpty() && ((ArraySet) set2).isEmpty()) {
            return rect;
        }
        ArraySet arraySet2 = new ArraySet();
        if (!arraySet.isEmpty()) {
            arraySet2.addAll((Collection) arraySet);
        }
        ArraySet arraySet3 = (ArraySet) set2;
        if (!arraySet3.isEmpty()) {
            arraySet2.addAll((Collection) arraySet3);
        }
        Rect rect3 = new Rect(rect);
        Iterator it = arraySet2.iterator();
        while (it.hasNext()) {
            Rect rect4 = (Rect) it.next();
            Rect rect5 = new Rect(rect4);
            int i = -this.mKeepClearAreasPadding;
            rect5.inset(i, i);
            if (Rect.intersects(rect4, rect3) && !tryOffset(0, rect5.top - rect3.bottom, rect3, rect5, rect2) && !tryOffset(rect5.left - rect3.right, 0, rect3, rect5, rect2) && !tryOffset(0, rect5.bottom - rect3.top, rect3, rect5, rect2)) {
                tryOffset(rect5.right - rect3.left, 0, rect3, rect5, rect2);
            }
        }
        return rect3;
    }
}
