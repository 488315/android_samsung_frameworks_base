package com.android.systemui.controls.util;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public enum ControlsBackUpRestore$BNRSecurityLevel {
    /* JADX INFO: Fake field, exist only in values array */
    LOW(0),
    /* JADX INFO: Fake field, exist only in values array */
    HIGH(1);

    public static final Companion Companion = new Companion(null);
    private final int value;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    ControlsBackUpRestore$BNRSecurityLevel(int i) {
        this.value = i;
    }

    public final int getValue() {
        return this.value;
    }
}
