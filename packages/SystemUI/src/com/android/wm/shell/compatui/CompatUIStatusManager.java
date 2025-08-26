package com.android.wm.shell.compatui;

import java.util.function.IntConsumer;
import java.util.function.IntSupplier;

/* loaded from: classes3.dex */
public class CompatUIStatusManager {
    public int mCurrentValue;
    public final IntConsumer mWriter;

    public CompatUIStatusManager(IntConsumer intConsumer, IntSupplier intSupplier) {
        this.mCurrentValue = -1;
        this.mWriter = intConsumer;
    }

    public CompatUIStatusManager() {
        this(new CompatUIStatusManager$$ExternalSyntheticLambda0(), new CompatUIStatusManager$$ExternalSyntheticLambda1());
    }
}
