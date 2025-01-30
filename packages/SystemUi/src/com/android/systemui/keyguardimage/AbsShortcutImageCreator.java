package com.android.systemui.keyguardimage;

import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.view.ViewGroup;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.keyguardimage.ImageOptionCreator;
import com.android.systemui.statusbar.KeyguardSecAffordanceView;
import com.android.systemui.statusbar.KeyguardShortcutManager;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class AbsShortcutImageCreator implements ImageCreator {
    public final Context mContext;
    public KeyguardShortcutManager mShortcutManager;

    public AbsShortcutImageCreator(Context context) {
        this.mContext = context;
    }

    public final int getBottomMargin(ImageOptionCreator.ImageOption imageOption) {
        Point point = DeviceState.sDisplaySize;
        return (int) (r2.getResources().getDimensionPixelSize((DeviceType.isTablet() || (LsRune.LOCKUI_SUB_DISPLAY_LOCK && !DeviceState.isSubDisplay(this.mContext))) ? imageOption.width > imageOption.height ? R.dimen.shortcut_icon_vertical_margin_land_tablet : R.dimen.shortcut_icon_vertical_margin_port_tablet : imageOption.width > imageOption.height ? R.dimen.shortcut_icon_vertical_margin_land : R.dimen.shortcut_icon_vertical_margin_port) * imageOption.scale);
    }

    public final KeyguardShortcutManager getShortcutManager() {
        if (this.mShortcutManager == null) {
            this.mShortcutManager = (KeyguardShortcutManager) Dependency.get(KeyguardShortcutManager.class);
        }
        return this.mShortcutManager;
    }

    public final int getSideMargin(ImageOptionCreator.ImageOption imageOption) {
        Point point = DeviceState.sDisplaySize;
        return (int) (r2.getResources().getDimensionPixelSize((DeviceType.isTablet() || (LsRune.LOCKUI_SUB_DISPLAY_LOCK && !DeviceState.isSubDisplay(this.mContext))) ? imageOption.width > imageOption.height ? R.dimen.keyguard_shrotcut_dls_default_side_margin_land_tablet : R.dimen.keyguard_shrotcut_dls_default_side_margin_tablet : imageOption.width > imageOption.height ? R.dimen.keyguard_shrotcut_dls_default_side_margin_land : R.dimen.keyguard_shrotcut_dls_default_side_margin) * imageOption.scale);
    }

    public final void updateCustomShortcutIcon(KeyguardSecAffordanceView keyguardSecAffordanceView, int i, boolean z) {
        Point point = DeviceState.sDisplaySize;
        if (DeviceType.isTablet()) {
            ViewGroup.LayoutParams layoutParams = keyguardSecAffordanceView.getLayoutParams();
            int dimension = (int) this.mContext.getResources().getDimension(R.dimen.keyguard_shrotcut_default_size_tablet);
            layoutParams.height = dimension;
            layoutParams.width = dimension;
            keyguardSecAffordanceView.setLayoutParams(layoutParams);
        }
        if (!z) {
            keyguardSecAffordanceView.setVisibility(8);
            return;
        }
        keyguardSecAffordanceView.setVisibility(0);
        keyguardSecAffordanceView.setImageBitmap(((BitmapDrawable) this.mShortcutManager.getShortcutDrawable(i)).getBitmap());
        keyguardSecAffordanceView.setContentDescription(this.mShortcutManager.getShortcutContentDescription(i));
    }
}
