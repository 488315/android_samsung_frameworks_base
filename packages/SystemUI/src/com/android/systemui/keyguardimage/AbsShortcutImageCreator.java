package com.android.systemui.keyguardimage;

import android.content.Context;
import android.view.ViewGroup;
import com.android.systemui.Dependency;
import com.android.systemui.statusbar.KeyguardSecAffordanceView;
import com.android.systemui.statusbar.KeyguardShortcutManager;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    public final void updateCustomShortcutIcon(KeyguardSecAffordanceView keyguardSecAffordanceView, int i, boolean z) {
        ViewGroup.LayoutParams layoutParams = keyguardSecAffordanceView.getLayoutParams();
        KeyguardShortcutManager keyguardShortcutManager = this.mShortcutManager;
        int shortcutIconSizeValue = keyguardShortcutManager.getShortcutIconSizeValue(keyguardShortcutManager.isNowBarVisible);
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
            keyguardSecAffordanceView.setIsDrawBackgroundCircle(true);
        } else {
            keyguardSecAffordanceView.setIsDrawBackgroundCircle(false);
        }
        keyguardSecAffordanceView.setContentDescription(this.mShortcutManager.getShortcutContentDescription(i));
    }
}
