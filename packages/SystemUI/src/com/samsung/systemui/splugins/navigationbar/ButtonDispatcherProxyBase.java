package com.samsung.systemui.splugins.navigationbar;

import android.graphics.drawable.Drawable;
import android.view.View;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface ButtonDispatcherProxyBase {
    void addButton(int i);

    View getButtonView(int i);

    void setButtonImage(int i, Drawable drawable, Drawable drawable2);

    void setButtonOnClickListener(int i, View.OnClickListener onClickListener);

    void setButtonOnLongClickListener(int i, View.OnLongClickListener onLongClickListener);

    void setButtonVisibility(int i, int i2);
}
