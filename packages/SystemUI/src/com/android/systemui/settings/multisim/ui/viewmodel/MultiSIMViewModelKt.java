package com.android.systemui.settings.multisim.ui.viewmodel;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class MultiSIMViewModelKt {
    public static final boolean isSIMINFO(ButtonType buttonType) {
        return buttonType == ButtonType.SIMINFO1 || buttonType == ButtonType.SIMINFO2;
    }
}
