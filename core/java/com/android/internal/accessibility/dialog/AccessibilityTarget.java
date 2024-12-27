package com.android.internal.accessibility.dialog;

import android.content.ComponentName;
import android.content.Context;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.UserHandle;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.Flags;
import com.android.internal.R;
import com.android.internal.accessibility.dialog.TargetAdapter;
import com.android.internal.accessibility.util.AccessibilityUtils;
import com.android.internal.accessibility.util.ShortcutUtils;
import java.util.Set;

/* loaded from: classes5.dex */
public abstract class AccessibilityTarget implements TargetOperations, OnTargetSelectedListener, OnTargetCheckedChangeListener {
    private ComponentName mComponentName;
    private Context mContext;
    private int mFragmentType;
    private Drawable mIcon;
    private String mId;
    private String mKey;
    private CharSequence mLabel;
    private boolean mShortcutEnabled;
    private int mShortcutType;
    private CharSequence mStateDescription;
    private int mUid;

    public AccessibilityTarget(Context context, int shortcutType, int fragmentType, boolean isShortcutSwitched, String id, int uid, CharSequence label, Drawable icon, String key) {
        this.mContext = context;
        this.mShortcutType = shortcutType;
        this.mFragmentType = fragmentType;
        this.mShortcutEnabled = isShortcutSwitched;
        this.mId = id;
        this.mUid = uid;
        this.mComponentName = ComponentName.unflattenFromString(id);
        this.mLabel = label;
        this.mIcon = icon;
        this.mKey = key;
    }

    @Override // com.android.internal.accessibility.dialog.TargetOperations
    public void updateActionItem(TargetAdapter.ViewHolder holder, int shortcutMenuMode) {
        if (getIcon() instanceof AdaptiveIconDrawable) {
            LayerDrawable layerDrawable = getLayerDrawable();
            holder.mIconView.setImageDrawable(layerDrawable);
        } else {
            holder.mIconView.setImageDrawable(getIcon());
        }
        if (!AccessibilityUtils.isDefaultTheme(this.mContext)) {
            holder.mIconView.setBackground(null);
        }
        holder.mLabelView.lambda$setTextAsync$0(getLabel());
        if (shortcutMenuMode == 2) {
            holder.mStatusView.setVisibility(8);
            return;
        }
        holder.mStatusView.setVisibility(0);
        if (shortcutMenuMode == 3) {
            holder.mStatusView.setText(R.string.accessibility_shortcut_menu_item_status_on);
            holder.mStatusView.setTextColor(ShortcutUtils.getPrimaryDarkColorId(this.mContext));
        } else if (shortcutMenuMode == 4) {
            holder.mStatusView.setText(R.string.accessibility_shortcut_menu_item_status_off);
            holder.mStatusView.setTextColor(ShortcutUtils.getSummaryColor(this.mContext));
        } else if (shortcutMenuMode == 5) {
            holder.mStatusView.setText(R.string.accessibility_shortcut_menu_item_status_disabled);
            holder.mStatusView.setTextColor(ShortcutUtils.getSummaryColor(this.mContext));
        }
    }

    private LayerDrawable getLayerDrawable() {
        float density = this.mContext.getResources().getDisplayMetrics().density;
        int backgroundSize = (int) (90.0f * density);
        int iconSize = (int) (80.0f * density);
        WrappedDrawable wrappedDrawable = new WrappedDrawable(getIcon());
        LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{((AdaptiveIconDrawable) getIcon()).getBackground(), wrappedDrawable});
        layerDrawable.setLayerSize(0, backgroundSize, backgroundSize);
        layerDrawable.setLayerSize(1, iconSize, iconSize);
        layerDrawable.setLayerGravity(1, 17);
        return layerDrawable;
    }

    @Override // com.android.internal.accessibility.dialog.OnTargetSelectedListener
    public void onSelected() {
        AccessibilityManager am = (AccessibilityManager) getContext().getSystemService(AccessibilityManager.class);
        switch (getShortcutType()) {
            case 1:
                am.notifyAccessibilityButtonClicked(getContext().getDisplayId(), getId());
                return;
            case 2:
                am.performAccessibilityShortcut(getId());
                return;
            case 512:
                am.performAccessibilityDirectAccess(getId());
                return;
            default:
                throw new IllegalStateException("Unexpected shortcut type");
        }
    }

    @Override // com.android.internal.accessibility.dialog.OnTargetCheckedChangeListener
    public void onCheckedChanged(boolean isChecked) {
        setShortcutEnabled(isChecked);
        if (Flags.migrateEnableShortcuts()) {
            AccessibilityManager am = (AccessibilityManager) getContext().getSystemService(AccessibilityManager.class);
            am.enableShortcutsForTargets(isChecked, getShortcutType(), Set.of(this.mId), UserHandle.myUserId());
        } else if (isChecked) {
            ShortcutUtils.optInValueToSettings(getContext(), getShortcutType(), getId());
        } else {
            ShortcutUtils.optOutValueFromSettings(getContext(), getShortcutType(), getId());
        }
    }

    public void setStateDescription(CharSequence stateDescription) {
        this.mStateDescription = stateDescription;
    }

    public CharSequence getStateDescription() {
        return this.mStateDescription;
    }

    public void setShortcutEnabled(boolean enabled) {
        this.mShortcutEnabled = enabled;
    }

    public Context getContext() {
        return this.mContext;
    }

    public int getShortcutType() {
        return this.mShortcutType;
    }

    public int getFragmentType() {
        return this.mFragmentType;
    }

    public boolean isShortcutEnabled() {
        return this.mShortcutEnabled;
    }

    public String getId() {
        return this.mId;
    }

    public int getUid() {
        return this.mUid;
    }

    public ComponentName getComponentName() {
        return this.mComponentName;
    }

    public CharSequence getLabel() {
        return this.mLabel;
    }

    public Drawable getIcon() {
        return this.mIcon;
    }

    public String getKey() {
        return this.mKey;
    }
}
