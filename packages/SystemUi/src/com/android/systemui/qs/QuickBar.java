package com.android.systemui.qs;

import com.android.systemui.qs.bar.BarController;
import java.util.function.BooleanSupplier;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class QuickBar {
    public BarController barController;
    public final BooleanSupplier qsExpandedSupplier;

    public QuickBar(BooleanSupplier booleanSupplier) {
        this.qsExpandedSupplier = booleanSupplier;
    }
}
