package com.android.systemui.keyguard.shared.model;

import androidx.constraintlayout.widget.ConstraintSet;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface KeyguardBlueprint {
    default void applyConstraints(ConstraintSet constraintSet) {
        Iterator it = getSections().iterator();
        while (it.hasNext()) {
            ((KeyguardSection) it.next()).applyConstraints(constraintSet);
        }
    }

    String getId();

    List getSections();
}
