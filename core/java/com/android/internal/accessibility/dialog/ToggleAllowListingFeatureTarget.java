package com.android.internal.accessibility.dialog;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.provider.Settings;
import com.android.internal.C4337R;
import com.android.internal.accessibility.util.ShortcutUtils;

/* loaded from: classes4.dex */
class ToggleAllowListingFeatureTarget extends AccessibilityTarget {
  private Context mContext;

  ToggleAllowListingFeatureTarget(
      Context context,
      int shortcutType,
      boolean isShortcutSwitched,
      String id,
      int uid,
      CharSequence label,
      Drawable icon,
      String key) {
    super(
        context,
        shortcutType,
        2,
        isShortcutSwitched,
        id,
        uid,
        label,
        context.getPackageManager().semGetDrawableForIconTray(icon, 1),
        key);
    int statusResId;
    if (isFeatureEnabled()) {
      statusResId = C4337R.string.accessibility_shortcut_menu_item_status_on;
    } else {
      statusResId = C4337R.string.accessibility_shortcut_menu_item_status_off;
    }
    setStateDescription(getContext().getString(statusResId));
    this.mContext = context;
  }

  @Override // com.android.internal.accessibility.dialog.AccessibilityTarget,
            // com.android.internal.accessibility.dialog.TargetOperations
  public void updateActionItem(TargetAdapter.ViewHolder holder, int shortcutMenuMode) {
    super.updateActionItem(holder, shortcutMenuMode);
    boolean isEditMenuMode = shortcutMenuMode == 1;
    holder.mStatusView.setVisibility(isEditMenuMode ? 8 : 0);
    holder.mStatusView.setText(getStateDescription());
    if (isFeatureEnabled()) {
      holder.mStatusView.setTextColor(ShortcutUtils.getPrimaryDarkColorId(this.mContext));
    } else {
      holder.mStatusView.setTextColor(ShortcutUtils.getSummaryColor(this.mContext));
    }
  }

  private boolean isFeatureEnabled() {
    return Settings.Secure.getInt(getContext().getContentResolver(), getKey(), 0) == 1;
  }

  @Override // com.android.internal.accessibility.dialog.AccessibilityTarget,
            // com.android.internal.accessibility.dialog.OnTargetSelectedListener
  public void onSelected() {
    int statusResId;
    if (isFeatureEnabled()) {
      statusResId = C4337R.string.accessibility_shortcut_menu_item_status_off;
    } else {
      statusResId = C4337R.string.accessibility_shortcut_menu_item_status_on;
    }
    setStateDescription(getContext().getString(statusResId));
    super.onSelected();
  }
}
