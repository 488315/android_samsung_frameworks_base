package com.android.systemui.communal.ui.viewmodel;

import kotlin.jvm.internal.DefaultConstructorMarker;

public abstract class PopupType {

    public final class CtaTile extends PopupType {
        public static final CtaTile INSTANCE = new CtaTile();

        private CtaTile() {
            super(null);
        }
    }

    public final class CustomizeWidgetButton extends PopupType {
        public static final CustomizeWidgetButton INSTANCE = new CustomizeWidgetButton();

        private CustomizeWidgetButton() {
            super(null);
        }
    }

    private PopupType() {
    }

    public /* synthetic */ PopupType(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }
}
