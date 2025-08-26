package com.android.internal.accessibility.dialog;

import android.accessibilityservice.AccessibilityShortcutInfo;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.internal.R;
import com.android.internal.accessibility.dialog.TargetAdapter;
import com.android.internal.accessibility.util.AccessibilityUtils;
import java.util.List;

/* loaded from: classes5.dex */
class ShortcutTargetAdapter extends TargetAdapter {
    private int mShortcutMenuMode = 0;
    private final List<AccessibilityTarget> mTargets;

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    ShortcutTargetAdapter(List<AccessibilityTarget> list) {
        this.mTargets = list;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.mTargets.size();
    }

    @Override // android.widget.Adapter
    public Object getItem(int i) {
        return this.mTargets.get(i);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00bc  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0104  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0107  */
    @Override // android.widget.Adapter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public View getView(int i, View view, ViewGroup viewGroup) throws Resources.NotFoundException {
        ViewGroup viewGroup2;
        TargetAdapter.ViewHolder viewHolder;
        View viewInflate;
        Context context = viewGroup.getContext();
        if (view == null) {
            viewGroup2 = viewGroup;
            viewInflate = LayoutInflater.from(context).inflate(R.layout.accessibility_shortcut_chooser_item_samsung, viewGroup2, false);
            viewHolder = new TargetAdapter.ViewHolder();
            viewHolder.mIconView = (ImageView) viewInflate.findViewById(R.id.accessibility_shortcut_target_icon);
            viewHolder.mLabelView = (TextView) viewInflate.findViewById(R.id.accessibility_shortcut_target_label);
            viewHolder.mStatusView = (TextView) viewInflate.findViewById(R.id.accessibility_shortcut_target_status);
            viewInflate.setTag(viewHolder);
        } else {
            viewGroup2 = viewGroup;
            viewHolder = (TargetAdapter.ViewHolder) view.getTag();
            viewInflate = view;
        }
        AccessibilityTarget accessibilityTarget = this.mTargets.get(i);
        List<AccessibilityShortcutInfo> installedAccessibilityShortcutListAsUser = AccessibilityManager.getInstance(context).getInstalledAccessibilityShortcutListAsUser(context, 0);
        if (installedAccessibilityShortcutListAsUser == null) {
            return viewInflate;
        }
        PackageManager packageManager = context.getPackageManager();
        String string = "";
        String strLoadSummary = "";
        for (int i2 = 0; i2 < installedAccessibilityShortcutListAsUser.size(); i2++) {
            if (installedAccessibilityShortcutListAsUser.get(i2).getComponentName().toString().contains(accessibilityTarget.getId())) {
                strLoadSummary = installedAccessibilityShortcutListAsUser.get(i2).loadSummary(packageManager);
            }
        }
        char c = 2;
        if (strLoadSummary != null && !TextUtils.isEmpty(strLoadSummary) && strLoadSummary.contains(";;;")) {
            String[] strArrSplit = strLoadSummary.split(";;;");
            String str = strArrSplit[0];
            String str2 = strArrSplit[1];
            str2.hashCode();
            switch (str2.hashCode()) {
                case -1243020381:
                    if (str2.equals("global")) {
                        c = 0;
                        break;
                    } else {
                        c = 65535;
                        break;
                    }
                case -906273929:
                    if (str2.equals("secure")) {
                        c = 1;
                        break;
                    }
                    break;
                case -887328209:
                    if (!str2.equals("system")) {
                    }
                    break;
            }
            switch (c) {
                case 0:
                    if (Settings.Global.getInt(context.getContentResolver(), str, 0) == 1) {
                        this.mShortcutMenuMode = 3;
                        break;
                    } else {
                        this.mShortcutMenuMode = 4;
                        break;
                    }
                case 1:
                    if (Settings.Secure.getIntForUser(context.getContentResolver(), str, 0, -2) == 1) {
                    }
                    break;
                case 2:
                    if (Settings.System.getIntForUser(context.getContentResolver(), str, 0, -2) == 1) {
                    }
                    break;
            }
            if ("sip_speak_keyboard_input_aloud".equals(str) && AccessibilityManager.getInstance(context).semIsScreenReaderEnabled()) {
                this.mShortcutMenuMode = 5;
            }
        } else {
            this.mShortcutMenuMode = 2;
        }
        viewInflate.setMinimumWidth(viewGroup2.getMeasuredWidth());
        if (AccessibilityUtils.isFoldedLargeCoverScreen()) {
            if (AccessibilityUtils.disallowPerformInCoverScreen(accessibilityTarget.getId())) {
                viewInflate.setContentDescription(((Object) accessibilityTarget.getLabel()) + " " + context.getString(R.string.accessibility_shortcut_menu_item_status_disabled));
                viewInflate.setAlpha(0.4f);
            } else {
                if (accessibilityTarget.getStateDescription() == null) {
                    int i3 = this.mShortcutMenuMode;
                    if (i3 == 3) {
                        string = context.getString(R.string.accessibility_shortcut_menu_item_status_on);
                    } else if (i3 == 4) {
                        string = context.getString(R.string.accessibility_shortcut_menu_item_status_off);
                    } else if (i3 == 5) {
                        string = context.getString(R.string.accessibility_shortcut_menu_item_status_disabled);
                    }
                } else {
                    string = (String) accessibilityTarget.getStateDescription();
                }
                viewInflate.setContentDescription(((Object) accessibilityTarget.getLabel()) + " " + string);
                viewInflate.setAlpha(1.0f);
            }
        }
        accessibilityTarget.updateActionItem(viewHolder, this.mShortcutMenuMode);
        return viewInflate;
    }

    void setShortcutMenuMode(int i) {
        this.mShortcutMenuMode = i;
    }

    int getShortcutMenuMode() {
        return this.mShortcutMenuMode;
    }
}
