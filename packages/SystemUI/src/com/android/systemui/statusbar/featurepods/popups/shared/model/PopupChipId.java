package com.android.systemui.statusbar.featurepods.popups.shared.model;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public abstract class PopupChipId {

    public final class MediaControl extends PopupChipId {
        public static final MediaControl INSTANCE = new MediaControl();

        private MediaControl() {
            super("MediaControl", null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof MediaControl);
        }

        public final int hashCode() {
            return 459916401;
        }

        public final String toString() {
            return "MediaControl";
        }
    }

    public /* synthetic */ PopupChipId(String str, DefaultConstructorMarker defaultConstructorMarker) {
        this(str);
    }

    private PopupChipId(String str) {
    }
}
