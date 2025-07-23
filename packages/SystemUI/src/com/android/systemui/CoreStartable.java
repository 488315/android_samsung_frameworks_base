package com.android.systemui;

import java.io.PrintWriter;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface CoreStartable extends Dumpable {
    public static final Nop NOP = new Nop();

    default boolean isDumpCritical() {
        return true;
    }

    void start();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Nop implements CoreStartable {
        @Override // com.android.systemui.CoreStartable
        public final void start() {
        }
    }

    default void onBootCompleted() {
    }

    default void onTrimMemory(int i) {
    }

    @Override // com.android.systemui.Dumpable
    default void dump(PrintWriter printWriter, String[] strArr) {
    }
}
