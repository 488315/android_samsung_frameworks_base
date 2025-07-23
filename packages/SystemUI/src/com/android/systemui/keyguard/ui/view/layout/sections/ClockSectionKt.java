package com.android.systemui.keyguard.ui.view.layout.sections;

import android.view.View;
import androidx.constraintlayout.widget.ConstraintSet;
import java.util.Iterator;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class ClockSectionKt {
    public static final void setVisibility(ConstraintSet constraintSet, Iterable iterable, int i) {
        Iterator it = iterable.iterator();
        while (it.hasNext()) {
            constraintSet.setVisibility(((View) it.next()).getId(), i);
        }
    }
}
