package com.android.systemui.statusbar.phone.nio;

import android.content.Context;
import android.content.res.Resources;
import androidx.appcompat.widget.MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.statusbar.phone.nio.KeyguardStatusBarNioLayoutRepository;
import com.android.systemui.util.SettingsHelper;

/* loaded from: classes3.dex */
public final class KeyguardStatusBarNioLayoutModel {
    public int bottomMargin;
    public int containerEndX;
    public int containerStartX;
    public final Context context;
    public float iconScaleRatio;
    public int iconSize;
    public boolean isNotificationIconsOnlyOn;
    public boolean isRtl;
    public boolean isShowNotificationOnKeyguard;
    public boolean isUpdatedModel;
    public float keyguardStatusBarViewAlpha;
    public int keyguardStatusBarViewVisibility;
    public int numberOfNio;
    public int paddingLeft;
    public int paddingRight;
    private final SettingsHelper settingsHelper;
    public int topMargin;
    public int totalHeight;
    public KeyguardStatusBarNioLayoutRepository.AnonymousClass1 updateRing;
    public boolean visibleCallChip;

    public KeyguardStatusBarNioLayoutModel(Context context, SettingsHelper settingsHelper) {
        this.context = context;
        this.settingsHelper = settingsHelper;
        this.isRtl = MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0.m(context) == 1;
        this.isShowNotificationOnKeyguard = settingsHelper.isShowNotificationOnKeyguard();
        this.isNotificationIconsOnlyOn = settingsHelper.isNotificationIconsOnlyOn();
    }

    public final int getEndMargin() throws Resources.NotFoundException {
        if (!this.isNotificationIconsOnlyOn || !this.isShowNotificationOnKeyguard || this.visibleCallChip || this.numberOfNio <= 0) {
            return 0;
        }
        int dimensionPixelSize = this.context.getResources().getDimensionPixelSize(R.dimen.keyguard_carrier_text_nio_container_space);
        float f = this.iconScaleRatio;
        return f > 1.0f ? ((int) (dimensionPixelSize * f)) + 1 : dimensionPixelSize;
    }

    public final void updateValues(Runnable runnable) {
        runnable.run();
        if (this.isUpdatedModel) {
            this.isUpdatedModel = false;
            KeyguardStatusBarNioLayoutRepository.AnonymousClass1 anonymousClass1 = this.updateRing;
            if (anonymousClass1 == null) {
                anonymousClass1 = null;
            }
            anonymousClass1.run();
        }
    }
}
