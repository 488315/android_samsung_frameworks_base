package com.android.wm.shell.compatui;

import java.util.function.IntConsumer;
import java.util.function.IntSupplier;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
