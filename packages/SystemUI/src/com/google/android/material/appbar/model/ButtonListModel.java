package com.google.android.material.appbar.model;

import java.util.List;

/* loaded from: classes4.dex */
public final class ButtonListModel {
    public final List buttonModels;
    public final ButtonStyle buttonStyle;

    public ButtonListModel(ButtonStyle buttonStyle, List<? extends ButtonModel> list) {
        this.buttonStyle = buttonStyle;
        this.buttonModels = list;
    }
}
