package com.android.systemui.keyguardimage;

import android.content.Context;
import android.view.ViewGroup;
import com.android.systemui.Dependency;
import com.android.systemui.keyguardimage.ImageOptionCreator;
import com.android.systemui.statusbar.KeyguardSecAffordanceView;
import com.android.systemui.statusbar.KeyguardShortcutManager;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public abstract class AbsShortcutImageCreator implements ImageCreator {
    public final Context mContext;
    public KeyguardShortcutManager mShortcutManager;

    public AbsShortcutImageCreator(Context context) {
        this.mContext = context;
    }

    public final KeyguardShortcutManager getShortcutManager() {
        if (this.mShortcutManager == null) {
            this.mShortcutManager = (KeyguardShortcutManager) Dependency.sDependency.getDependencyInner(KeyguardShortcutManager.class);
        }
        return this.mShortcutManager;
    }

    public final void updateCustomShortcutIcon(KeyguardSecAffordanceView keyguardSecAffordanceView, int i, boolean z, ImageOptionCreator.ImageOption imageOption) {
        ViewGroup.LayoutParams layoutParams = keyguardSecAffordanceView.getLayoutParams();
        int i2 = imageOption.realWidth;
        int i3 = imageOption.realHeight;
        int i4 = i2 > i3 ? 2 : 1;
        KeyguardShortcutManager keyguardShortcutManager = this.mShortcutManager;
        int shortcutIconSizeValue = keyguardShortcutManager.getShortcutIconSizeValue(i2, i3, i4, keyguardShortcutManager.isNowBarVisible);
        layoutParams.height = shortcutIconSizeValue;
        layoutParams.width = shortcutIconSizeValue;
        keyguardSecAffordanceView.setLayoutParams(layoutParams);
        if (!z) {
            keyguardSecAffordanceView.setVisibility(8);
            return;
        }
        keyguardSecAffordanceView.setVisibility(0);
        keyguardSecAffordanceView.setImageDrawable(this.mShortcutManager.getShortcutDrawable(i));
        if (this.mShortcutManager.isMonotoneIcon(i)) {
            keyguardSecAffordanceView.mIsDrawBackgroundCircle = true;
            keyguardSecAffordanceView.setBackgroundCircleColor();
            keyguardSecAffordanceView.setForegroundCircleColor();
        } else {
            keyguardSecAffordanceView.mIsDrawBackgroundCircle = false;
            keyguardSecAffordanceView.setBackgroundCircleColor();
            keyguardSecAffordanceView.setForegroundCircleColor();
        }
        keyguardSecAffordanceView.setContentDescription(this.mShortcutManager.getShortcutContentDescription(i));
    }
}
