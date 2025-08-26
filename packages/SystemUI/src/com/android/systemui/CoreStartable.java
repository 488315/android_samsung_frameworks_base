package com.android.systemui;

import java.io.PrintWriter;

/* loaded from: classes.dex */
public interface CoreStartable extends Dumpable {
    public static final Nop NOP = new Nop();

    default boolean isDumpCritical() {
        return true;
    }

    void start();

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
