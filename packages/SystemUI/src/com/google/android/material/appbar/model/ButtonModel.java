package com.google.android.material.appbar.model;

import com.google.android.material.appbar.model.AppBarModel;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes4.dex */
public class ButtonModel {
    public final AppBarModel.OnClickListener clickListener;
    public final String contentDescription;
    public final String text;

    public ButtonModel() {
        this(null, null, null, 7, null);
    }

    public ButtonModel(String str) {
        this(str, null, null, 6, null);
    }

    public ButtonModel(String str, AppBarModel.OnClickListener onClickListener) {
        this(str, onClickListener, null, 4, null);
    }

    public ButtonModel(String str, AppBarModel.OnClickListener onClickListener, String str2) {
        this.text = str;
        this.clickListener = onClickListener;
        this.contentDescription = str2;
    }

    public /* synthetic */ ButtonModel(String str, AppBarModel.OnClickListener onClickListener, String str2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : str, (i & 2) != 0 ? null : onClickListener, (i & 4) != 0 ? null : str2);
    }
}
