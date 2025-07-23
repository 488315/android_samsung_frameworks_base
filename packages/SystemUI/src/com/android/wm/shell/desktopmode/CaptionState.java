package com.android.wm.shell.desktopmode;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class CaptionState {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class NoCaption extends CaptionState {
        public static final NoCaption INSTANCE = new NoCaption();

        private NoCaption() {
            super(null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof NoCaption);
        }

        public final int hashCode() {
            return 1919328187;
        }

        public final String toString() {
            return "NoCaption";
        }
    }

    public /* synthetic */ CaptionState(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    private CaptionState() {
    }
}
