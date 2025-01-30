package com.android.systemui.p016qs;

import com.android.systemui.p016qs.buttons.QSButtonsContainerController;
import java.util.function.BooleanSupplier;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class QuickPanel {
    public QSButtonsContainerController qsButtonsContainerController;
    public final BooleanSupplier qsExpandedSupplier;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public QuickPanel(BooleanSupplier booleanSupplier) {
        this.qsExpandedSupplier = booleanSupplier;
    }
}
