package com.android.internal.accessibility.dialog;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.provider.Settings;
import com.android.internal.R;
import com.android.internal.accessibility.dialog.TargetAdapter;
import com.android.internal.accessibility.util.AccessibilityUtils;
import com.android.internal.accessibility.util.ShortcutUtils;

/* loaded from: classes5.dex */
class ToggleAllowListingFeatureTarget extends AccessibilityTarget {
    private Context mContext;

    ToggleAllowListingFeatureTarget(Context context, int i, boolean z, String str, int i2, CharSequence charSequence, Drawable drawable, String str2) {
        super(context, i, 2, z, str, i2, charSequence, AccessibilityUtils.isDefaultTheme(context) ? drawable : context.getPackageManager().semGetDrawableForIconTray(drawable, 1), str2);
        setStateDescription(getContext().getString(isFeatureEnabled() ? R.string.accessibility_shortcut_menu_item_status_on : R.string.accessibility_shortcut_menu_item_status_off));
        this.mContext = context;
    }

    @Override // com.android.internal.accessibility.dialog.AccessibilityTarget, com.android.internal.accessibility.dialog.TargetOperations
    public void updateActionItem(TargetAdapter.ViewHolder viewHolder, int i) {
        super.updateActionItem(viewHolder, i);
        viewHolder.mStatusView.setVisibility(i == 1 ? 8 : 0);
        viewHolder.mStatusView.lambda$setTextAsync$0(getStateDescription());
        if (isFeatureEnabled()) {
            viewHolder.mStatusView.setTextColor(ShortcutUtils.getPrimaryDarkColorId(this.mContext));
        } else {
            viewHolder.mStatusView.setTextColor(ShortcutUtils.getSummaryColor(this.mContext));
        }
    }

    private boolean isFeatureEnabled() {
        return Settings.Secure.getInt(getContext().getContentResolver(), getKey(), 0) == 1;
    }

    @Override // com.android.internal.accessibility.dialog.AccessibilityTarget, com.android.internal.accessibility.dialog.OnTargetSelectedListener
    public void onSelected() {
        setStateDescription(getContext().getString(isFeatureEnabled() ? R.string.accessibility_shortcut_menu_item_status_off : R.string.accessibility_shortcut_menu_item_status_on));
        super.onSelected();
    }
}
