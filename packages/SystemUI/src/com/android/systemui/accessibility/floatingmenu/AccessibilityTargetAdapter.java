package com.android.systemui.accessibility.floatingmenu;

import android.accessibilityservice.AccessibilityShortcutInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.text.TextUtils;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.ImageView;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.android.internal.accessibility.AccessibilityShortcutController;
import com.android.internal.accessibility.dialog.AccessibilityTarget;
import com.android.internal.accessibility.util.AccessibilityUtils;
import com.android.keyguard.KeyguardSecurityContainer$UserSwitcherViewMode$2$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.accessibility.floatingmenu.AccessibilityTargetAdapter;
import com.android.systemui.accessibility.hearingaid.HearingDeviceStatusDrawableInfo;
import com.samsung.android.settings.accessibility.advanced.shortcut.WrappedDrawable;
import java.util.List;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public class AccessibilityTargetAdapter extends RecyclerView.Adapter {
    static final int PAYLOAD_HEARING_STATUS_DRAWABLE = 1;
    public int mHearingDeviceStatus;
    public int mIconWidthHeight;
    public int mItemPadding;
    public final List mTargets;

    public class BottomViewHolder extends ViewHolder {
        public BottomViewHolder(View view) {
            super(view);
        }
    }

    public class TopViewHolder extends ViewHolder {
        public TopViewHolder(View view) {
            super(view);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView mIconView;
        public final View mLeftBadgeView;
        public final View mRightBadgeView;

        public ViewHolder(View view) {
            super(view);
            this.mIconView = (ImageView) view.findViewById(R.id.icon_view);
            this.mRightBadgeView = view.findViewById(R.id.right_badge_view);
            this.mLeftBadgeView = view.findViewById(R.id.left_badge_view);
        }
    }

    public AccessibilityTargetAdapter(List<AccessibilityTarget> list) {
        this.mTargets = list;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x009b, code lost:
    
        if (android.provider.Settings.System.getIntForUser(r10.getContentResolver(), r2, 0, -2) == 1) goto L42;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x00a6, code lost:
    
        if (android.provider.Settings.Secure.getIntForUser(r10.getContentResolver(), r2, 0, -2) == 1) goto L42;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x00b1, code lost:
    
        if (android.provider.Settings.Global.getInt(r10.getContentResolver(), r2, 0) == 1) goto L42;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00ba, code lost:
    
        return r10.getString(com.android.systemui.R.string.switch_bar_on);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static CharSequence getStateDescription(AccessibilityTarget accessibilityTarget, Context context) {
        if (!TextUtils.isEmpty(accessibilityTarget.getStateDescription())) {
            return accessibilityTarget.getStateDescription();
        }
        PackageManager packageManager = context.getPackageManager();
        List installedAccessibilityShortcutListAsUser = AccessibilityManager.getInstance(context).getInstalledAccessibilityShortcutListAsUser(context, 0);
        String strLoadSummary = "";
        for (int i = 0; i < installedAccessibilityShortcutListAsUser.size(); i++) {
            if (((AccessibilityShortcutInfo) installedAccessibilityShortcutListAsUser.get(i)).getComponentName().toString().contains(accessibilityTarget.getId())) {
                strLoadSummary = ((AccessibilityShortcutInfo) installedAccessibilityShortcutListAsUser.get(i)).loadSummary(packageManager);
            }
        }
        if (TextUtils.isEmpty(strLoadSummary) || !strLoadSummary.contains(";;;")) {
            return "";
        }
        String[] strArrSplit = strLoadSummary.split(";;;");
        String str = strArrSplit[0];
        String str2 = strArrSplit[1];
        str2.getClass();
        switch (str2) {
            case "global":
                break;
            case "secure":
                break;
            case "system":
                break;
            default:
                return context.getString(R.string.switch_bar_off);
        }
    }

    public static void updateHearingDeviceStatusDrawable(ViewHolder viewHolder, int i) {
        Context context = viewHolder.itemView.getContext();
        HearingDeviceStatusDrawableInfo.StatusDrawableInfo statusDrawableInfo = HearingDeviceStatusDrawableInfo.DRAWABLE_DEFAULT_INFO;
        if (i != -1) {
            if (i == 0) {
                statusDrawableInfo = HearingDeviceStatusDrawableInfo.DRAWABLE_DISCONNECTED_INFO;
            } else if (i == 1) {
                statusDrawableInfo = HearingDeviceStatusDrawableInfo.DRAWABLE_CONNECTED_INFO;
            } else if (i != 2 && i == 3) {
                statusDrawableInfo = HearingDeviceStatusDrawableInfo.DRAWABLE_ACTIVE_INFO;
            }
        }
        int i2 = statusDrawableInfo.baseDrawableId;
        int i3 = statusDrawableInfo.stateDescriptionId;
        int i4 = statusDrawableInfo.indicatorDrawableId;
        viewHolder.mIconView.setBackground(i2 != 0 ? context.getDrawable(i2) : null);
        viewHolder.mRightBadgeView.setBackground(i4 != 0 ? context.getDrawable(i4) : null);
        viewHolder.mLeftBadgeView.setBackground(i4 != 0 ? context.getDrawable(i4) : null);
        viewHolder.itemView.setStateDescription(i3 != 0 ? context.getString(i3) : null);
        ViewGroup.LayoutParams layoutParams = viewHolder.mRightBadgeView.getLayoutParams();
        if (layoutParams.width != 0) {
            layoutParams.width = 0;
            layoutParams.height = 0;
            ViewGroup.LayoutParams layoutParams2 = viewHolder.mLeftBadgeView.getLayoutParams();
            if (layoutParams2.width != 0) {
                layoutParams2.width = 0;
                layoutParams2.height = 0;
                viewHolder.mRightBadgeView.setLayoutParams(layoutParams);
                viewHolder.mLeftBadgeView.setLayoutParams(layoutParams2);
            }
        }
        viewHolder.mRightBadgeView.setVisibility(0);
        viewHolder.mLeftBadgeView.setVisibility(4);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        return this.mTargets.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemViewType(int i) {
        if (i == this.mTargets.size() - 1) {
            return 2;
        }
        return i == 0 ? 0 : 1;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View viewM = KeyguardSecurityContainer$UserSwitcherViewMode$2$$ExternalSyntheticOutline0.m(viewGroup, R.layout.accessibility_floating_menu_item, viewGroup, false);
        return i == 0 ? new TopViewHolder(viewM) : i == 2 ? new BottomViewHolder(viewM) : new ViewHolder(viewM);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i, List list) {
        final ViewHolder viewHolder2 = (ViewHolder) viewHolder;
        if (list.isEmpty()) {
            onBindViewHolder(viewHolder2, i);
        } else {
            list.forEach(new Consumer() { // from class: com.android.systemui.accessibility.floatingmenu.AccessibilityTargetAdapter$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    AccessibilityTargetAdapter accessibilityTargetAdapter = this.f$0;
                    AccessibilityTargetAdapter.ViewHolder viewHolder3 = viewHolder2;
                    accessibilityTargetAdapter.getClass();
                    if ((obj instanceof Integer) && ((Integer) obj).intValue() == 1) {
                        AccessibilityTargetAdapter.updateHearingDeviceStatusDrawable(viewHolder3, accessibilityTargetAdapter.mHearingDeviceStatus);
                    }
                }
            });
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(final ViewHolder viewHolder, int i) {
        final AccessibilityTarget accessibilityTarget = (AccessibilityTarget) this.mTargets.get(i);
        Drawable icon = accessibilityTarget.getIcon();
        if (accessibilityTarget.getIcon() instanceof AdaptiveIconDrawable) {
            float f = accessibilityTarget.getContext().getResources().getDisplayMetrics().density;
            int i2 = (int) (90.0f * f);
            int i3 = (int) (f * 80.0f);
            LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{((AdaptiveIconDrawable) icon).getBackground(), new WrappedDrawable(icon)});
            layerDrawable.setLayerSize(0, i2, i2);
            layerDrawable.setLayerSize(1, i3, i3);
            layerDrawable.setLayerGravity(1, 17);
            icon = layerDrawable;
        }
        viewHolder.mIconView.setImageDrawable(icon);
        int i4 = this.mIconWidthHeight;
        ViewGroup.LayoutParams layoutParams = viewHolder.mIconView.getLayoutParams();
        if (layoutParams.width != i4) {
            layoutParams.width = i4;
            layoutParams.height = i4;
            viewHolder.mIconView.setLayoutParams(layoutParams);
        }
        int i5 = this.mItemPadding;
        this.mTargets.size();
        viewHolder.itemView.setPaddingRelative(i5, i5, i5, i5);
        viewHolder.itemView.setStateDescription(accessibilityTarget.getStateDescription());
        viewHolder.itemView.setContentDescription(accessibilityTarget.getLabel());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.accessibility.floatingmenu.AccessibilityTargetAdapter$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AccessibilityTargetAdapter.ViewHolder viewHolder2 = viewHolder;
                AccessibilityTarget accessibilityTarget2 = accessibilityTarget;
                viewHolder2.itemView.performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(41));
                accessibilityTarget2.onSelected();
            }
        });
        viewHolder.itemView.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.systemui.accessibility.floatingmenu.AccessibilityTargetAdapter.1
            @Override // android.view.View.AccessibilityDelegate
            public final void sendAccessibilityEvent(View view, int i6) {
                if (i6 == 32768) {
                    AccessibilityTargetAdapter accessibilityTargetAdapter = AccessibilityTargetAdapter.this;
                    AccessibilityTarget accessibilityTarget2 = accessibilityTarget;
                    Context context = accessibilityTarget2.getContext();
                    accessibilityTargetAdapter.getClass();
                    view.setStateDescription(AccessibilityTargetAdapter.getStateDescription(accessibilityTarget2, context));
                }
                super.sendAccessibilityEvent(view, i6);
            }
        });
        if (AccessibilityUtils.isFoldedLargeCoverScreen()) {
            if (AccessibilityUtils.disallowPerformInCoverScreen(accessibilityTarget.getId())) {
                viewHolder.itemView.setAlpha(0.5f);
                viewHolder.itemView.setContentDescription(((Object) accessibilityTarget.getLabel()) + " " + accessibilityTarget.getContext().getString(R.string.kg_keycode_ok_disabled));
            } else {
                viewHolder.itemView.setAlpha(1.0f);
                viewHolder.itemView.setContentDescription(accessibilityTarget.getLabel());
            }
        }
        if (!AccessibilityUtils.isDefaultTheme(accessibilityTarget.getContext()) && !AccessibilityUtils.isHighContrastTheme(accessibilityTarget.getContext())) {
            viewHolder.mIconView.setBackground(null);
        }
        viewHolder.mRightBadgeView.setVisibility(8);
        viewHolder.mLeftBadgeView.setVisibility(8);
        ViewCompat.replaceAccessibilityAction(viewHolder.itemView, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK, (accessibilityTarget.getFragmentType() == 2 || !TextUtils.isEmpty(getStateDescription(accessibilityTarget, accessibilityTarget.getContext()))) ? viewHolder.itemView.getResources().getString(R.string.accessibility_floating_button_action_double_tap_to_toggle) : null, null);
        if (AccessibilityShortcutController.ACCESSIBILITY_HEARING_AIDS_COMPONENT_NAME.equals(ComponentName.unflattenFromString(accessibilityTarget.getId()))) {
            updateHearingDeviceStatusDrawable(viewHolder, this.mHearingDeviceStatus);
        }
    }
}
