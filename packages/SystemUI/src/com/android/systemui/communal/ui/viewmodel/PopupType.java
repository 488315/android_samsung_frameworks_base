package com.android.systemui.communal.ui.viewmodel;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class PopupType {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
