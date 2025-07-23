package com.android.wm.shell.common.pip;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.SystemProperties;
import android.util.ArraySet;
import com.android.systemui.R;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class PhonePipKeepClearAlgorithm implements PipKeepClearAlgorithmInterface {
    public final int mImeOffset;
    public final boolean mKeepClearAreaGravityEnabled = SystemProperties.getBoolean("persist.wm.debug.enable_pip_keep_clear_algorithm_gravity", false);
    public final int mKeepClearAreasPadding;

    public PhonePipKeepClearAlgorithm(Context context) {
        Resources resources = context.getResources();
        this.mKeepClearAreasPadding = resources.getDimensionPixelSize(R.dimen.pip_keep_clear_areas_padding);
        this.mImeOffset = resources.getDimensionPixelSize(R.dimen.pip_ime_offset);
    }

    public static boolean tryOffset(Rect rect, Rect rect2, Rect rect3, int i, int i2) {
        Rect rect4 = new Rect(rect);
        rect4.offset(i, i2);
        if (Rect.intersects(rect2, rect4) || !rect3.contains(rect4)) {
            return false;
        }
        rect.offsetTo(rect4.left, rect4.top);
        return true;
    }

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
            if (Rect.intersects(rect4, rect3) && !tryOffset(rect3, rect5, rect2, 0, rect5.top - rect3.bottom) && !tryOffset(rect3, rect5, rect2, rect5.left - rect3.right, 0) && !tryOffset(rect3, rect5, rect2, 0, rect5.bottom - rect3.top)) {
                tryOffset(rect3, rect5, rect2, rect5.right - rect3.left, 0);
            }
        }
        return rect3;
    }
}
