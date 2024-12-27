package com.android.systemui.communal.ui.viewmodel;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public abstract class PopupType {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class CtaTile extends PopupType {
        public static final CtaTile INSTANCE = new CtaTile();

        private CtaTile() {
            super(null);
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
