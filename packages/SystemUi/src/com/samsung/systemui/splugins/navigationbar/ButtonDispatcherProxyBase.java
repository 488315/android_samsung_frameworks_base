package com.samsung.systemui.splugins.navigationbar;

import android.graphics.drawable.Drawable;
import android.view.View;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface ButtonDispatcherProxyBase {
    void addButton(int i);

    View getButtonView(int i);

    void setButtonImage(int i, Drawable drawable, Drawable drawable2);

    void setButtonOnClickListener(int i, View.OnClickListener onClickListener);

    void setButtonOnLongClickListener(int i, View.OnLongClickListener onLongClickListener);

    void setButtonVisibility(int i, int i2);
}
