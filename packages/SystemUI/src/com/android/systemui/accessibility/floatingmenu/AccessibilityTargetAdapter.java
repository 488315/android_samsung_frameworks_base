package com.android.systemui.accessibility.floatingmenu;

import android.content.Context;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.text.TextUtils;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.mediarouter.app.MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView;
import com.android.internal.accessibility.dialog.AccessibilityTarget;
import com.android.internal.accessibility.util.AccessibilityUtils;
import com.android.systemui.R;
import com.android.systemui.accessibility.floatingmenu.AccessibilityTargetAdapter;
import com.samsung.android.settings.accessibility.advanced.shortcut.WrappedDrawable;
import java.util.List;

public final class AccessibilityTargetAdapter extends RecyclerView.Adapter {
    public int mIconWidthHeight;
    public int mItemPadding;
    public final List mTargets;

    public final class BottomViewHolder extends ViewHolder {
        public BottomViewHolder(View view) {
            super(view);
        }
    }

    public final class TopViewHolder extends ViewHolder {
        public TopViewHolder(View view) {
            super(view);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView mIconView;

        public ViewHolder(View view) {
            super(view);
            this.mIconView = (ImageView) view.findViewById(R.id.icon_view);
        }
    }

    public AccessibilityTargetAdapter(List<AccessibilityTarget> list) {
        this.mTargets = list;
    }

    /* JADX WARN: Code restructure failed: missing block: B:33:0x009b, code lost:
    
        if (android.provider.Settings.System.getIntForUser(r10.getContentResolver(), r2, 0, -2) == 1) goto L42;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x00ba, code lost:
    
        return r10.getString(com.android.systemui.R.string.switch_bar_on);
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x00a6, code lost:
    
        if (android.provider.Settings.Secure.getIntForUser(r10.getContentResolver(), r2, 0, -2) == 1) goto L42;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x00b1, code lost:
    
        if (android.provider.Settings.Global.getInt(r10.getContentResolver(), r2, 0) == 1) goto L42;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.CharSequence getStateDescription(com.android.internal.accessibility.dialog.AccessibilityTarget r9, android.content.Context r10) {
        /*
            r0 = 0
            r1 = 1
            java.lang.CharSequence r2 = r9.getStateDescription()
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 != 0) goto L11
            java.lang.CharSequence r9 = r9.getStateDescription()
            return r9
        L11:
            android.content.pm.PackageManager r2 = r10.getPackageManager()
            android.view.accessibility.AccessibilityManager r3 = android.view.accessibility.AccessibilityManager.getInstance(r10)
            java.util.List r3 = r3.getInstalledAccessibilityShortcutListAsUser(r10, r0)
            java.lang.String r4 = ""
            r5 = r0
            r6 = r4
        L21:
            int r7 = r3.size()
            if (r5 >= r7) goto L4b
            java.lang.Object r7 = r3.get(r5)
            android.accessibilityservice.AccessibilityShortcutInfo r7 = (android.accessibilityservice.AccessibilityShortcutInfo) r7
            android.content.ComponentName r7 = r7.getComponentName()
            java.lang.String r7 = r7.toString()
            java.lang.String r8 = r9.getId()
            boolean r7 = r7.contains(r8)
            if (r7 == 0) goto L49
            java.lang.Object r6 = r3.get(r5)
            android.accessibilityservice.AccessibilityShortcutInfo r6 = (android.accessibilityservice.AccessibilityShortcutInfo) r6
            java.lang.String r6 = r6.loadSummary(r2)
        L49:
            int r5 = r5 + r1
            goto L21
        L4b:
            boolean r9 = android.text.TextUtils.isEmpty(r6)
            if (r9 != 0) goto Lc3
            java.lang.String r9 = ";;;"
            boolean r2 = r6.contains(r9)
            if (r2 == 0) goto Lc3
            java.lang.String[] r9 = r6.split(r9)
            r2 = r9[r0]
            r9 = r9[r1]
            r9.getClass()
            r3 = -2
            r4 = -1
            int r5 = r9.hashCode()
            switch(r5) {
                case -1243020381: goto L85;
                case -906273929: goto L7a;
                case -887328209: goto L6e;
                default: goto L6d;
            }
        L6d:
            goto L8f
        L6e:
            java.lang.String r5 = "system"
            boolean r9 = r9.equals(r5)
            if (r9 != 0) goto L78
            goto L8f
        L78:
            r4 = 2
            goto L8f
        L7a:
            java.lang.String r5 = "secure"
            boolean r9 = r9.equals(r5)
            if (r9 != 0) goto L83
            goto L8f
        L83:
            r4 = r1
            goto L8f
        L85:
            java.lang.String r5 = "global"
            boolean r9 = r9.equals(r5)
            if (r9 != 0) goto L8e
            goto L8f
        L8e:
            r4 = r0
        L8f:
            switch(r4) {
                case 0: goto La9;
                case 1: goto L9e;
                case 2: goto L93;
                default: goto L92;
            }
        L92:
            goto Lbb
        L93:
            android.content.ContentResolver r9 = r10.getContentResolver()
            int r9 = android.provider.Settings.System.getIntForUser(r9, r2, r0, r3)
            if (r9 != r1) goto Lbb
            goto Lb3
        L9e:
            android.content.ContentResolver r9 = r10.getContentResolver()
            int r9 = android.provider.Settings.Secure.getIntForUser(r9, r2, r0, r3)
            if (r9 != r1) goto Lbb
            goto Lb3
        La9:
            android.content.ContentResolver r9 = r10.getContentResolver()
            int r9 = android.provider.Settings.Global.getInt(r9, r2, r0)
            if (r9 != r1) goto Lbb
        Lb3:
            r9 = 2131956490(0x7f13130a, float:1.9549537E38)
            java.lang.String r9 = r10.getString(r9)
            return r9
        Lbb:
            r9 = 2131956489(0x7f131309, float:1.9549535E38)
            java.lang.String r9 = r10.getString(r9)
            return r9
        Lc3:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.accessibility.floatingmenu.AccessibilityTargetAdapter.getStateDescription(com.android.internal.accessibility.dialog.AccessibilityTarget, android.content.Context):java.lang.CharSequence");
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
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        final ViewHolder viewHolder2 = (ViewHolder) viewHolder;
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
        viewHolder2.mIconView.setImageDrawable(icon);
        int i4 = this.mIconWidthHeight;
        ViewGroup.LayoutParams layoutParams = viewHolder2.mIconView.getLayoutParams();
        if (layoutParams.width != i4) {
            layoutParams.width = i4;
            layoutParams.height = i4;
            viewHolder2.mIconView.setLayoutParams(layoutParams);
        }
        int i5 = this.mItemPadding;
        this.mTargets.size();
        viewHolder2.itemView.setPaddingRelative(i5, i5, i5, i5);
        viewHolder2.itemView.setStateDescription(accessibilityTarget.getStateDescription());
        viewHolder2.itemView.setContentDescription(accessibilityTarget.getLabel());
        viewHolder2.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.accessibility.floatingmenu.AccessibilityTargetAdapter$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AccessibilityTargetAdapter.ViewHolder viewHolder3 = AccessibilityTargetAdapter.ViewHolder.this;
                AccessibilityTarget accessibilityTarget2 = accessibilityTarget;
                viewHolder3.itemView.performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(41));
                accessibilityTarget2.onSelected();
            }
        });
        viewHolder2.itemView.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.systemui.accessibility.floatingmenu.AccessibilityTargetAdapter.1
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
                viewHolder2.itemView.setAlpha(0.5f);
                viewHolder2.itemView.setContentDescription(((Object) accessibilityTarget.getLabel()) + " " + accessibilityTarget.getContext().getString(R.string.kg_keycode_ok_disabled));
            } else {
                viewHolder2.itemView.setAlpha(1.0f);
                viewHolder2.itemView.setContentDescription(accessibilityTarget.getLabel());
            }
        }
        if (!AccessibilityUtils.isDefaultTheme(accessibilityTarget.getContext())) {
            viewHolder2.mIconView.setBackground(null);
        }
        ViewCompat.replaceAccessibilityAction(viewHolder2.itemView, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK, (accessibilityTarget.getFragmentType() == 2 || !TextUtils.isEmpty(getStateDescription(accessibilityTarget, accessibilityTarget.getContext()))) ? viewHolder2.itemView.getResources().getString(R.string.accessibility_floating_button_action_double_tap_to_toggle) : null, null);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(int i, ViewGroup viewGroup) {
        View m = MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0.m(viewGroup, R.layout.accessibility_floating_menu_item, viewGroup, false);
        return i == 0 ? new TopViewHolder(m) : i == 2 ? new BottomViewHolder(m) : new ViewHolder(m);
    }
}
