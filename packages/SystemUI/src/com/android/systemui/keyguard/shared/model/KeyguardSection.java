package com.android.systemui.keyguard.shared.model;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import kotlin.jvm.internal.Reflection;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
