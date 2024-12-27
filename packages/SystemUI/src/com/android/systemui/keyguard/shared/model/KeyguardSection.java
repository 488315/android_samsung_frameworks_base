package com.android.systemui.keyguard.shared.model;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import kotlin.jvm.internal.Reflection;

public abstract class KeyguardSection {
    public abstract void addViews(ConstraintLayout constraintLayout);

    public abstract void applyConstraints(ConstraintSet constraintSet);

    public abstract void bindData(ConstraintLayout constraintLayout);

    public boolean equals(Object obj) {
        return obj != null && getClass() == obj.getClass();
    }

    public int hashCode() {
        return Reflection.getOrCreateKotlinClass(getClass()).hashCode();
    }

    public abstract void removeViews(ConstraintLayout constraintLayout);

    public void onRebuildBegin() {
    }

    public void onRebuildEnd() {
    }
}
