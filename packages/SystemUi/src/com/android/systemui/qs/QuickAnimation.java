package com.android.systemui.qs;

import android.view.View;
import com.android.systemui.qs.animator.SecQSFragmentAnimatorManager;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class QuickAnimation {
    public final BooleanSupplier qsExpandedSupplier;
    public SecQSFragmentAnimatorManager secQSFragmentAnimatorManager;

    public QuickAnimation(BooleanSupplier booleanSupplier, Supplier<View> supplier) {
        this.qsExpandedSupplier = booleanSupplier;
    }
}
