package com.android.systemui.keyguard.ui.view.layout.sections;

import android.view.View;
import androidx.constraintlayout.widget.ConstraintSet;
import java.util.Iterator;

/* loaded from: classes2.dex */
public abstract class ClockSectionKt {
    public static final void setVisibility(ConstraintSet constraintSet, Iterable iterable, int i) {
        Iterator it = iterable.iterator();
        while (it.hasNext()) {
            constraintSet.setVisibility(((View) it.next()).getId(), i);
        }
    }
}
