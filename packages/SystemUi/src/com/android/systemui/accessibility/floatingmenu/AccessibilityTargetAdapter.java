package com.android.systemui.accessibility.floatingmenu;

import android.accessibilityservice.AccessibilityShortcutInfo;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.android.internal.accessibility.dialog.AccessibilityTarget;
import com.android.internal.accessibility.util.AccessibilityUtils;
import com.android.systemui.R;
import com.android.systemui.accessibility.floatingmenu.AccessibilityTargetAdapter;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class AccessibilityTargetAdapter extends RecyclerView.Adapter {
    public int mIconWidthHeight;
    public int mItemPadding;
    public final List mTargets;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class BottomViewHolder extends ViewHolder {
        public BottomViewHolder(View view) {
            super(view);
        }

        @Override // com.android.systemui.accessibility.floatingmenu.AccessibilityTargetAdapter.ViewHolder
        public final void updateItemPadding(int i, int i2) {
            super.updateItemPadding(i, i2);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class TopViewHolder extends ViewHolder {
        public TopViewHolder(View view) {
            super(view);
        }

        @Override // com.android.systemui.accessibility.floatingmenu.AccessibilityTargetAdapter.ViewHolder
        public final void updateItemPadding(int i, int i2) {
            super.updateItemPadding(i, i2);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mIconView;

        public ViewHolder(View view) {
            super(view);
            this.mIconView = view.findViewById(R.id.icon_view);
        }

        public void updateItemPadding(int i, int i2) {
            this.itemView.setPaddingRelative(i, i, i, i);
        }
    }

    public AccessibilityTargetAdapter(List<AccessibilityTarget> list) {
        this.mTargets = list;
    }

    public static CharSequence getStateDescription(AccessibilityTarget accessibilityTarget, Context context) {
        char c;
        if (!TextUtils.isEmpty(accessibilityTarget.getStateDescription())) {
            return accessibilityTarget.getStateDescription();
        }
        PackageManager packageManager = context.getPackageManager();
        boolean z = false;
        List installedAccessibilityShortcutListAsUser = AccessibilityManager.getInstance(context).getInstalledAccessibilityShortcutListAsUser(context, 0);
        String str = "";
        for (int i = 0; i < installedAccessibilityShortcutListAsUser.size(); i++) {
            if (((AccessibilityShortcutInfo) installedAccessibilityShortcutListAsUser.get(i)).getComponentName().toString().contains(accessibilityTarget.getId())) {
                str = ((AccessibilityShortcutInfo) installedAccessibilityShortcutListAsUser.get(i)).loadSummary(packageManager);
            }
        }
        if (TextUtils.isEmpty(str) || !str.contains(";;;")) {
            return "";
        }
        String[] split = str.split(";;;");
        String str2 = split[0];
        String str3 = split[1];
        str3.getClass();
        int hashCode = str3.hashCode();
        if (hashCode == -1243020381) {
            if (str3.equals("global")) {
                c = 0;
            }
            c = 65535;
        } else if (hashCode != -906273929) {
            if (hashCode == -887328209 && str3.equals("system")) {
                c = 2;
            }
            c = 65535;
        } else {
            if (str3.equals("secure")) {
                c = 1;
            }
            c = 65535;
        }
        if (c == 0 ? Settings.Global.getInt(context.getContentResolver(), str2, 0) == 1 : !(c == 1 ? Settings.Secure.getIntForUser(context.getContentResolver(), str2, 0, -2) != 1 : c != 2 || Settings.System.getIntForUser(context.getContentResolver(), str2, 0, -2) != 1)) {
            z = true;
        }
        return z ? context.getString(R.string.switch_bar_on) : context.getString(R.string.switch_bar_off);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        return this.mTargets.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemViewType(int i) {
        if (i == getItemCount() - 1) {
            return 2;
        }
        return i == 0 ? 0 : 1;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        final ViewHolder viewHolder2 = (ViewHolder) viewHolder;
        final AccessibilityTarget accessibilityTarget = (AccessibilityTarget) this.mTargets.get(i);
        Drawable icon = accessibilityTarget.getIcon();
        View view = viewHolder2.mIconView;
        view.setBackground(icon);
        int i2 = this.mIconWidthHeight;
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams.width != i2) {
            layoutParams.width = i2;
            layoutParams.height = i2;
            view.setLayoutParams(layoutParams);
        }
        viewHolder2.updateItemPadding(this.mItemPadding, getItemCount());
        CharSequence stateDescription = accessibilityTarget.getStateDescription();
        View view2 = viewHolder2.itemView;
        view2.setStateDescription(stateDescription);
        view2.setContentDescription(accessibilityTarget.getLabel());
        view2.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.accessibility.floatingmenu.AccessibilityTargetAdapter$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view3) {
                AccessibilityTargetAdapter.ViewHolder viewHolder3 = AccessibilityTargetAdapter.ViewHolder.this;
                AccessibilityTarget accessibilityTarget2 = accessibilityTarget;
                viewHolder3.itemView.performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(41));
                accessibilityTarget2.onSelected();
            }
        });
        view2.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.systemui.accessibility.floatingmenu.AccessibilityTargetAdapter.1
            @Override // android.view.View.AccessibilityDelegate
            public final void sendAccessibilityEvent(View view3, int i3) {
                if (i3 == 32768) {
                    AccessibilityTargetAdapter accessibilityTargetAdapter = AccessibilityTargetAdapter.this;
                    AccessibilityTarget accessibilityTarget2 = accessibilityTarget;
                    Context context = accessibilityTarget2.getContext();
                    accessibilityTargetAdapter.getClass();
                    view3.setStateDescription(AccessibilityTargetAdapter.getStateDescription(accessibilityTarget2, context));
                }
                super.sendAccessibilityEvent(view3, i3);
            }
        });
        if (AccessibilityUtils.isFoldedLargeCoverScreen()) {
            if (AccessibilityUtils.disallowPerformInCoverScreen(accessibilityTarget.getId())) {
                view2.setAlpha(0.5f);
                view2.setContentDescription(((Object) accessibilityTarget.getLabel()) + " " + accessibilityTarget.getContext().getString(R.string.kg_keycode_ok_disabled));
            } else {
                view2.setAlpha(1.0f);
                view2.setContentDescription(accessibilityTarget.getLabel());
            }
        }
        ViewCompat.replaceAccessibilityAction(view2, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK, (accessibilityTarget.getFragmentType() == 2 || !TextUtils.isEmpty(getStateDescription(accessibilityTarget, accessibilityTarget.getContext()))) ? view2.getResources().getString(R.string.accessibility_floating_button_action_double_tap_to_toggle) : null, null);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(RecyclerView recyclerView, int i) {
        View inflate = LayoutInflater.from(recyclerView.getContext()).inflate(R.layout.accessibility_floating_menu_item, (ViewGroup) recyclerView, false);
        return i == 0 ? new TopViewHolder(inflate) : i == 2 ? new BottomViewHolder(inflate) : new ViewHolder(inflate);
    }
}
