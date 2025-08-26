package com.samsung.systemui.splugins.navigationbar;

import android.graphics.drawable.Drawable;
import android.view.View;

/* loaded from: classes4.dex */
public interface ButtonDispatcherProxyBase {
    void addButton(int i);

    View getButtonView(int i);

    void setButtonImage(int i, Drawable drawable, Drawable drawable2);

    void setButtonOnClickListener(int i, View.OnClickListener onClickListener);

    void setButtonOnLongClickListener(int i, View.OnLongClickListener onLongClickListener);

    void setButtonVisibility(int i, int i2);
}
