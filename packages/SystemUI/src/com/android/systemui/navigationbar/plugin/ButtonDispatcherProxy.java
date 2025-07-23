package com.android.systemui.navigationbar.plugin;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.View;
import com.android.systemui.navigationbar.views.buttons.ButtonDispatcher;
import com.android.systemui.navigationbar.views.buttons.KeyButtonDrawable;
import com.samsung.systemui.splugins.navigationbar.ButtonDispatcherProxyBase;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ButtonDispatcherProxy implements ButtonDispatcherProxyBase {
    public final SparseArray mButtonDispatchers;
    public final Context mContext;

    public ButtonDispatcherProxy(Context context, SparseArray<ButtonDispatcher> sparseArray) {
        this.mContext = context;
        this.mButtonDispatchers = sparseArray;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ButtonDispatcherProxyBase
    public final void addButton(int i) {
        if (this.mButtonDispatchers.get(i) == null) {
            this.mButtonDispatchers.put(i, new ButtonDispatcher(i));
        }
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ButtonDispatcherProxyBase
    public final View getButtonView(int i) {
        ButtonDispatcher buttonDispatcher = (ButtonDispatcher) this.mButtonDispatchers.get(i);
        if (buttonDispatcher != null) {
            return buttonDispatcher.mCurrentView;
        }
        return null;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ButtonDispatcherProxyBase
    public final void setButtonImage(int i, Drawable drawable, Drawable drawable2) {
        KeyButtonDrawable create = KeyButtonDrawable.create(this.mContext, drawable, drawable2, false);
        ButtonDispatcher buttonDispatcher = (ButtonDispatcher) this.mButtonDispatchers.get(i);
        if (buttonDispatcher != null) {
            buttonDispatcher.setImageDrawable(create);
            buttonDispatcher.setVisibility(0);
        }
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ButtonDispatcherProxyBase
    public final void setButtonOnClickListener(int i, View.OnClickListener onClickListener) {
        ButtonDispatcher buttonDispatcher = (ButtonDispatcher) this.mButtonDispatchers.get(i);
        if (buttonDispatcher != null) {
            buttonDispatcher.setOnClickListener(onClickListener);
        }
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ButtonDispatcherProxyBase
    public final void setButtonOnLongClickListener(int i, View.OnLongClickListener onLongClickListener) {
        ButtonDispatcher buttonDispatcher = (ButtonDispatcher) this.mButtonDispatchers.get(i);
        if (buttonDispatcher != null) {
            buttonDispatcher.setOnLongClickListener(onLongClickListener);
        }
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ButtonDispatcherProxyBase
    public final void setButtonVisibility(int i, int i2) {
        ButtonDispatcher buttonDispatcher = (ButtonDispatcher) this.mButtonDispatchers.get(i);
        if (buttonDispatcher != null) {
            buttonDispatcher.setVisibility(i2);
        }
    }
}
