package com.android.systemui.statusbar.pipeline.shared.ui.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.android.systemui.R;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.statusbar.BaseStatusBarFrameLayout;
import com.android.systemui.statusbar.StatusBarIconView;
import com.android.systemui.statusbar.pipeline.shared.ui.binder.ModernStatusBarViewBinding;
import java.util.ArrayList;
import kotlin.jvm.functions.Function0;

public class ModernStatusBarView extends BaseStatusBarFrameLayout {
    public ModernStatusBarViewBinding binding;
    public int iconVisibleState;
    public String slot;

    public ModernStatusBarView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0, 4, null);
        this.iconVisibleState = 2;
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
        int dimensionPixelSize = ((FrameLayout) this).mContext.getResources().getDimensionPixelSize(R.dimen.status_bar_icon_size_sp);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(dimensionPixelSize, dimensionPixelSize);
        layoutParams.gravity = 8388627;
        addView(statusBarIconView, layoutParams);
        this.binding = (ModernStatusBarViewBinding) function0.invoke();
    }

    @Override // com.android.systemui.statusbar.StatusIconDisplayable
    public final boolean isIconVisible() {
        ModernStatusBarViewBinding modernStatusBarViewBinding = this.binding;
        if (modernStatusBarViewBinding == null) {
            modernStatusBarViewBinding = null;
        }
        return modernStatusBarViewBinding.getShouldIconBeVisible();
    }

    @Override // com.android.systemui.plugins.DarkIconDispatcher.DarkReceiver
    public final void onDarkChangedWithContrast(ArrayList arrayList, int i, int i2) {
        int tint = DarkIconDispatcher.getTint(arrayList, this, i);
        int inverseTint = DarkIconDispatcher.getInverseTint(arrayList, this, i2);
        ModernStatusBarViewBinding modernStatusBarViewBinding = this.binding;
        if (modernStatusBarViewBinding == null) {
            modernStatusBarViewBinding = null;
        }
        modernStatusBarViewBinding.onIconTintChanged(tint, inverseTint);
        ModernStatusBarViewBinding modernStatusBarViewBinding2 = this.binding;
        (modernStatusBarViewBinding2 != null ? modernStatusBarViewBinding2 : null).onDecorTintChanged(tint);
    }

    @Override // com.android.systemui.statusbar.StatusIconDisplayable
    public final void setDecorColor(int i) {
        ModernStatusBarViewBinding modernStatusBarViewBinding = this.binding;
        if (modernStatusBarViewBinding == null) {
            modernStatusBarViewBinding = null;
        }
        modernStatusBarViewBinding.onDecorTintChanged(i);
    }

    @Override // com.android.systemui.statusbar.StatusIconDisplayable
    public final void setStaticDrawableColor(int i) {
    }

    @Override // com.android.systemui.statusbar.StatusIconDisplayable
    public final void setVisibleState(int i, boolean z) {
        if (this.iconVisibleState == i) {
            return;
        }
        this.iconVisibleState = i;
        ModernStatusBarViewBinding modernStatusBarViewBinding = this.binding;
        if (modernStatusBarViewBinding == null) {
            modernStatusBarViewBinding = null;
        }
        modernStatusBarViewBinding.onVisibilityStateChanged(i);
    }

    @Override // com.android.systemui.statusbar.StatusIconDisplayable
    public final void setStaticDrawableColor(int i, int i2) {
        ModernStatusBarViewBinding modernStatusBarViewBinding = this.binding;
        if (modernStatusBarViewBinding == null) {
            modernStatusBarViewBinding = null;
        }
        modernStatusBarViewBinding.onIconTintChanged(i, i2);
    }

    @Override // com.android.systemui.plugins.DarkIconDispatcher.DarkReceiver
    public final void onDarkChanged(ArrayList arrayList, float f, int i) {
    }
}
