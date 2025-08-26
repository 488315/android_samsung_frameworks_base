package com.android.systemui.settings.multisim.ui.viewmodel;

/* loaded from: classes3.dex */
public abstract class MultiSIMViewModelKt {
    public static final boolean isSIMINFO(ButtonType buttonType) {
        return buttonType == ButtonType.SIMINFO1 || buttonType == ButtonType.SIMINFO2;
    }
}
