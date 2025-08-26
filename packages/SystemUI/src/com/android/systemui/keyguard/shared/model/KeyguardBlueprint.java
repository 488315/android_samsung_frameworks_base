package com.android.systemui.keyguard.shared.model;

import androidx.constraintlayout.widget.ConstraintSet;
import java.util.Iterator;
import java.util.List;

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
