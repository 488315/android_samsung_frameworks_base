package com.android.internal.accessibility.dialog;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;

import com.android.internal.R;
import com.android.internal.accessibility.util.AccessibilityUtils;
import com.android.internal.accessibility.util.ShortcutUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes5.dex */
class ToggleAccessibilityServiceTarget extends AccessibilityServiceTarget {
    private Context mContext;

    @Retention(RetentionPolicy.SOURCE)
    @interface StatusViewAlphaScale {
        public static final float DISABLED = 0.5f;
        public static final float OPAQUE = 1.0f;
    }

    ToggleAccessibilityServiceTarget(
            Context context, int shortcutType, AccessibilityServiceInfo serviceInfo) {
        super(context, shortcutType, 2, serviceInfo);
        int statusResId;
        if (AccessibilityUtils.isAccessibilityServiceEnabled(getContext(), getId())) {
            statusResId = R.string.accessibility_shortcut_menu_item_status_on;
        } else {
            statusResId = R.string.accessibility_shortcut_menu_item_status_off;
        }
        setStateDescription(getContext().getString(statusResId));
        this.mContext = context;
    }

    @Override // com.android.internal.accessibility.dialog.AccessibilityServiceTarget,
              // com.android.internal.accessibility.dialog.AccessibilityTarget,
              // com.android.internal.accessibility.dialog.TargetOperations
    public void updateActionItem(TargetAdapter.ViewHolder holder, int shortcutMenuMode) {
        super.updateActionItem(holder, shortcutMenuMode);
        boolean isAllowed =
                AccessibilityTargetHelper.isAccessibilityTargetAllowed(
                        getContext(), getComponentName().getPackageName(), getUid());
        boolean isEditMenuMode = shortcutMenuMode == 1;
        holder.mStatusView.setVisibility(isEditMenuMode ? 8 : 0);
        holder.mStatusView.lambda$setTextAsync$0(getStateDescription());
        holder.mStatusView.setAlpha(isAllowed ? 1.0f : 0.5f);
        if (AccessibilityUtils.isAccessibilityServiceEnabled(getContext(), getId())) {
            holder.mStatusView.setTextColor(ShortcutUtils.getPrimaryDarkColorId(this.mContext));
        } else {
            holder.mStatusView.setTextColor(ShortcutUtils.getSummaryColor(this.mContext));
        }
    }

    @Override // com.android.internal.accessibility.dialog.AccessibilityTarget,
              // com.android.internal.accessibility.dialog.OnTargetSelectedListener
    public void onSelected() {
        int statusResId;
        if (AccessibilityUtils.isAccessibilityServiceEnabled(getContext(), getId())) {
            statusResId = R.string.accessibility_shortcut_menu_item_status_off;
        } else {
            statusResId = R.string.accessibility_shortcut_menu_item_status_on;
        }
        setStateDescription(getContext().getString(statusResId));
        super.onSelected();
    }
}
