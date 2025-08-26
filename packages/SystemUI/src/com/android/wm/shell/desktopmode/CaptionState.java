package com.android.wm.shell.desktopmode;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public abstract class CaptionState {

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
