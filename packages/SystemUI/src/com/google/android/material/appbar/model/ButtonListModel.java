package com.google.android.material.appbar.model;

import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class ButtonListModel {
    public final List buttonModels;
    public final ButtonStyle buttonStyle;

    public ButtonListModel(ButtonStyle buttonStyle, List<? extends ButtonModel> list) {
        this.buttonStyle = buttonStyle;
        this.buttonModels = list;
    }
}
