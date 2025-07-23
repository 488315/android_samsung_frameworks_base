package com.android.internal.accessibility.dialog;

import android.content.ComponentName;
import android.content.Context;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.UserHandle;
import android.view.accessibility.AccessibilityManager;
import com.android.internal.R;
import com.android.internal.accessibility.dialog.TargetAdapter;
import com.android.internal.accessibility.util.AccessibilityUtils;
import com.android.internal.accessibility.util.ShortcutUtils;
import com.android.internal.hidden_from_bootclasspath.android.provider.Flags;
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

    public AccessibilityTarget(Context context, int i, int i2, boolean z, String str, int i3, CharSequence charSequence, Drawable drawable, String str2) {
        if (!isRecognizedShortcutType(i)) {
            throw new IllegalArgumentException("Unexpected shortcut type " + ShortcutUtils.convertToKey(i));
        }
        this.mContext = context;
        this.mShortcutType = i;
        this.mFragmentType = i2;
        this.mShortcutEnabled = z;
        this.mId = str;
        this.mUid = i3;
        this.mComponentName = ComponentName.unflattenFromString(str);
        this.mLabel = charSequence;
        this.mIcon = drawable;
        this.mKey = str2;
    }

    @Override // com.android.internal.accessibility.dialog.TargetOperations
    public void updateActionItem(TargetAdapter.ViewHolder viewHolder, int i) {
        if (getIcon() instanceof AdaptiveIconDrawable) {
            viewHolder.mIconView.setImageDrawable(getLayerDrawable());
        } else {
            viewHolder.mIconView.setImageDrawable(getIcon());
        }
        if (!AccessibilityUtils.isDefaultTheme(this.mContext) && !AccessibilityUtils.isHighContrastTheme(this.mContext)) {
            viewHolder.mIconView.setBackground(null);
        }
        viewHolder.mLabelView.lambda$setTextAsync$0(getLabel());
        if (i == 2) {
            viewHolder.mStatusView.setVisibility(8);
            return;
        }
        viewHolder.mStatusView.setVisibility(0);
        if (i == 3) {
            viewHolder.mStatusView.setText(R.string.accessibility_shortcut_menu_item_status_on);
            viewHolder.mStatusView.setTextColor(ShortcutUtils.getPrimaryDarkColorId(this.mContext));
        } else if (i == 4) {
            viewHolder.mStatusView.setText(R.string.accessibility_shortcut_menu_item_status_off);
            viewHolder.mStatusView.setTextColor(ShortcutUtils.getSummaryColor(this.mContext));
        } else if (i == 5) {
            viewHolder.mStatusView.setText(R.string.accessibility_shortcut_menu_item_status_disabled);
            viewHolder.mStatusView.setTextColor(ShortcutUtils.getSummaryColor(this.mContext));
        }
    }

    private LayerDrawable getLayerDrawable() {
        float f = this.mContext.getResources().getDisplayMetrics().density;
        int i = (int) (90.0f * f);
        int i2 = (int) (f * 80.0f);
        LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{((AdaptiveIconDrawable) getIcon()).getBackground(), new WrappedDrawable(getIcon())});
        layerDrawable.setLayerSize(0, i, i);
        layerDrawable.setLayerSize(1, i2, i2);
        layerDrawable.setLayerGravity(1, 17);
        return layerDrawable;
    }

    @Override // com.android.internal.accessibility.dialog.OnTargetSelectedListener
    public void onSelected() {
        AccessibilityManager accessibilityManager = (AccessibilityManager) getContext().getSystemService(AccessibilityManager.class);
        if (accessibilityManager == null) {
            return;
        }
        accessibilityManager.performAccessibilityShortcut(getContext().getDisplayId(), this.mShortcutType, getId());
    }

    @Override // com.android.internal.accessibility.dialog.OnTargetCheckedChangeListener
    public void onCheckedChanged(boolean z) {
        setShortcutEnabled(z);
        ((AccessibilityManager) getContext().getSystemService(AccessibilityManager.class)).enableShortcutsForTargets(z, getShortcutType(), Set.of(this.mId), UserHandle.myUserId());
    }

    public void setStateDescription(CharSequence charSequence) {
        this.mStateDescription = charSequence;
    }

    public CharSequence getStateDescription() {
        return this.mStateDescription;
    }

    public void setShortcutEnabled(boolean z) {
        this.mShortcutEnabled = z;
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

    public static boolean isRecognizedShortcutType(int i) {
        return i != 0 && ((Flags.a11yStandaloneGestureEnabled() ? 547 : 515) & i) == i;
    }
}
