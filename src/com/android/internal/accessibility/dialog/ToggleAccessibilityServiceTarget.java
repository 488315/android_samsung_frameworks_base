package com.android.internal.accessibility.dialog;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import com.android.internal.R;
import com.android.internal.accessibility.dialog.TargetAdapter;
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

    ToggleAccessibilityServiceTarget(Context context, int i, AccessibilityServiceInfo accessibilityServiceInfo) {
        super(context, i, 2, accessibilityServiceInfo);
        setStateDescription(getContext().getString(AccessibilityUtils.isAccessibilityServiceEnabled(getContext(), getId()) ? R.string.accessibility_shortcut_menu_item_status_on : R.string.accessibility_shortcut_menu_item_status_off));
        this.mContext = context;
    }

    @Override // com.android.internal.accessibility.dialog.AccessibilityServiceTarget, com.android.internal.accessibility.dialog.AccessibilityTarget, com.android.internal.accessibility.dialog.TargetOperations
    public void updateActionItem(TargetAdapter.ViewHolder viewHolder, int i) {
        super.updateActionItem(viewHolder, i);
        boolean isAccessibilityTargetAllowed = AccessibilityTargetHelper.isAccessibilityTargetAllowed(getContext(), getComponentName().getPackageName(), getUid());
        viewHolder.mStatusView.setVisibility(i == 1 ? 8 : 0);
        viewHolder.mStatusView.lambda$setTextAsync$0(getStateDescription());
        viewHolder.mStatusView.setAlpha(isAccessibilityTargetAllowed ? 1.0f : 0.5f);
        if (AccessibilityUtils.isAccessibilityServiceEnabled(getContext(), getId())) {
            viewHolder.mStatusView.setTextColor(ShortcutUtils.getPrimaryDarkColorId(this.mContext));
        } else {
            viewHolder.mStatusView.setTextColor(ShortcutUtils.getSummaryColor(this.mContext));
        }
    }

    @Override // com.android.internal.accessibility.dialog.AccessibilityTarget, com.android.internal.accessibility.dialog.OnTargetSelectedListener
    public void onSelected() {
        setStateDescription(getContext().getString(AccessibilityUtils.isAccessibilityServiceEnabled(getContext(), getId()) ? R.string.accessibility_shortcut_menu_item_status_off : R.string.accessibility_shortcut_menu_item_status_on));
        super.onSelected();
    }
}
