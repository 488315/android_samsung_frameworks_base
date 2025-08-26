package com.android.systemui.communal.ui.viewmodel;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public abstract class PopupType {

    public final class CtaTile extends PopupType {
        public static final CtaTile INSTANCE = new CtaTile();

        private CtaTile() {
            super(null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof CtaTile);
        }

        public final int hashCode() {
            return 1618960433;
        }

        public final String toString() {
            return "CtaTile";
        }
    }

    public final class CustomizeWidgetButton extends PopupType {
        public static final CustomizeWidgetButton INSTANCE = new CustomizeWidgetButton();

        private CustomizeWidgetButton() {
            super(null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof CustomizeWidgetButton);
        }

        public final int hashCode() {
            return -1708912500;
        }

        public final String toString() {
            return "CustomizeWidgetButton";
        }
    }

    public /* synthetic */ PopupType(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    private PopupType() {
    }
}
