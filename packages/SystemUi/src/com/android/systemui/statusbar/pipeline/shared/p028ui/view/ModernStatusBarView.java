package com.android.systemui.statusbar.pipeline.shared.p028ui.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.android.systemui.R;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.statusbar.BaseStatusBarFrameLayout;
import com.android.systemui.statusbar.StatusBarIconView;
import com.android.systemui.statusbar.pipeline.shared.p028ui.binder.ModernStatusBarViewBinding;
import java.util.ArrayList;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class ModernStatusBarView extends BaseStatusBarFrameLayout {
    public ModernStatusBarViewBinding binding;
    public int iconVisibleState;
    public String slot;

    public ModernStatusBarView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0, 4, null);
        this.iconVisibleState = 2;
    }

    /* renamed from: getBinding$frameworks__base__packages__SystemUI__android_common__SystemUI_core */
    public final ModernStatusBarViewBinding m211xb00bd415() {
        ModernStatusBarViewBinding modernStatusBarViewBinding = this.binding;
        if (modernStatusBarViewBinding != null) {
            return modernStatusBarViewBinding;
        }
        return null;
    }

    @Override // android.view.View
    public final void getDrawingRect(Rect rect) {
        super.getDrawingRect(rect);
        int translationX = (int) getTranslationX();
        int translationY = (int) getTranslationY();
        rect.left += translationX;
        rect.right += translationX;
        rect.top += translationY;
        rect.bottom += translationY;
    }

    @Override // com.android.systemui.statusbar.StatusIconDisplayable
    public final String getSlot() {
        String str = this.slot;
        if (str == null) {
            return null;
        }
        return str;
    }

    @Override // com.android.systemui.statusbar.StatusIconDisplayable
    public final int getVisibleState() {
        return this.iconVisibleState;
    }

    public void initView(String str, Function0 function0) {
        this.slot = str;
        Context context = ((FrameLayout) this).mContext;
        String str2 = this.slot;
        if (str2 == null) {
            str2 = null;
        }
        StatusBarIconView statusBarIconView = new StatusBarIconView(context, str2, null);
        statusBarIconView.setId(R.id.status_bar_dot);
        statusBarIconView.setVisibleState(1);
        int dimensionPixelSize = ((FrameLayout) this).mContext.getResources().getDimensionPixelSize(R.dimen.status_bar_icon_size);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(dimensionPixelSize, dimensionPixelSize);
        layoutParams.gravity = 8388627;
        addView(statusBarIconView, layoutParams);
        this.binding = (ModernStatusBarViewBinding) function0.invoke();
    }

    @Override // com.android.systemui.statusbar.StatusIconDisplayable
    public final boolean isIconVisible() {
        return m211xb00bd415().getShouldIconBeVisible();
    }

    @Override // com.android.systemui.plugins.DarkIconDispatcher.DarkReceiver
    public final void onDarkChanged(ArrayList arrayList, float f, int i) {
        int tint = DarkIconDispatcher.getTint(arrayList, this, i);
        m211xb00bd415().onIconTintChanged(tint);
        m211xb00bd415().onDecorTintChanged(tint);
    }

    @Override // com.android.systemui.statusbar.StatusIconDisplayable
    public final void setDecorColor(int i) {
        m211xb00bd415().onDecorTintChanged(i);
    }

    @Override // com.android.systemui.statusbar.StatusIconDisplayable
    public final void setStaticDrawableColor(int i) {
        m211xb00bd415().onIconTintChanged(i);
    }

    @Override // com.android.systemui.statusbar.StatusIconDisplayable
    public final void setVisibleState(int i, boolean z) {
        if (this.iconVisibleState == i) {
            return;
        }
        this.iconVisibleState = i;
        m211xb00bd415().onVisibilityStateChanged(i);
    }
}
