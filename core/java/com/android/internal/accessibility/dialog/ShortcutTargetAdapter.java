package com.android.internal.accessibility.dialog;

import android.accessibilityservice.AccessibilityShortcutInfo;
import android.content.Context;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.internal.R;
import com.android.internal.accessibility.util.AccessibilityUtils;

import java.util.List;

class ShortcutTargetAdapter extends TargetAdapter {
    private int mShortcutMenuMode = 0;
    private final List<AccessibilityTarget> mTargets;

    ShortcutTargetAdapter(List<AccessibilityTarget> targets) {
        this.mTargets = targets;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.mTargets.size();
    }

    @Override // android.widget.Adapter
    public Object getItem(int position) {
        return this.mTargets.get(position);
    }

    @Override // android.widget.Adapter
    public long getItemId(int position) {
        return position;
    }

    @Override // android.widget.Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        TargetAdapter.ViewHolder holder;
        View convertView2;
        char c;
        Context context = parent.getContext();
        boolean turnedOn = false;
        if (convertView == null) {
            convertView2 =
                    LayoutInflater.from(context)
                            .inflate(
                                    R.layout.accessibility_shortcut_chooser_item_samsung,
                                    parent,
                                    false);
            holder = new TargetAdapter.ViewHolder();
            holder.mIconView =
                    (ImageView) convertView2.findViewById(R.id.accessibility_shortcut_target_icon);
            holder.mLabelView =
                    (TextView) convertView2.findViewById(R.id.accessibility_shortcut_target_label);
            holder.mStatusView =
                    (TextView) convertView2.findViewById(R.id.accessibility_shortcut_target_status);
            convertView2.setTag(holder);
        } else {
            holder = (TargetAdapter.ViewHolder) convertView.getTag();
            convertView2 = convertView;
        }
        AccessibilityTarget target = this.mTargets.get(position);
        List<AccessibilityShortcutInfo> shortcutInfo =
                AccessibilityManager.getInstance(context)
                        .getInstalledAccessibilityShortcutListAsUser(context, 0);
        if (shortcutInfo == null) {
            return convertView2;
        }
        PackageManager pm = context.getPackageManager();
        String summary = "";
        for (int i = 0; i < shortcutInfo.size(); i++) {
            String shortcutName = shortcutInfo.get(i).getComponentName().toString();
            String targetName = target.getId();
            if (shortcutName.contains(targetName)) {
                summary = shortcutInfo.get(i).loadSummary(pm);
            }
        }
        if (summary == null || TextUtils.isEmpty(summary) || !summary.contains(";;;")) {
            this.mShortcutMenuMode = 2;
        } else {
            String[] summaryInfo = summary.split(";;;");
            String key = summaryInfo[0];
            String dbType = summaryInfo[1];
            switch (dbType.hashCode()) {
                case -1243020381:
                    if (dbType.equals("global")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case -906273929:
                    if (dbType.equals("secure")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case -887328209:
                    if (dbType.equals("system")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    if (Settings.System.getIntForUser(context.getContentResolver(), key, 0, -2)
                            == 1) {
                        turnedOn = true;
                        break;
                    }
                    break;
                case 1:
                    if (Settings.Secure.getIntForUser(context.getContentResolver(), key, 0, -2)
                            == 1) {
                        turnedOn = true;
                        break;
                    }
                    break;
                case 2:
                    if (Settings.Global.getInt(context.getContentResolver(), key, 0) == 1) {
                        turnedOn = true;
                        break;
                    }
                    break;
                default:
                    turnedOn = false;
                    break;
            }
            if (turnedOn) {
                this.mShortcutMenuMode = 3;
            } else {
                this.mShortcutMenuMode = 4;
            }
            if ("sip_speak_keyboard_input_aloud".equals(key)
                    && AccessibilityManager.getInstance(context).semIsScreenReaderEnabled()) {
                this.mShortcutMenuMode = 5;
            }
        }
        convertView2.setMinimumWidth(parent.getMeasuredWidth());
        if (AccessibilityUtils.isFoldedLargeCoverScreen()) {
            if (AccessibilityUtils.disallowPerformInCoverScreen(target.getId())) {
                convertView2.setContentDescription(
                        ((Object) target.getLabel())
                                + " "
                                + context.getString(
                                        R.string.accessibility_shortcut_menu_item_status_disabled));
                convertView2.setAlpha(0.4f);
            } else {
                String targetSummary = "";
                if (target.getStateDescription() == null) {
                    if (this.mShortcutMenuMode == 3) {
                        targetSummary =
                                context.getString(
                                        R.string.accessibility_shortcut_menu_item_status_on);
                    } else if (this.mShortcutMenuMode == 4) {
                        targetSummary =
                                context.getString(
                                        R.string.accessibility_shortcut_menu_item_status_off);
                    } else if (this.mShortcutMenuMode == 5) {
                        targetSummary =
                                context.getString(
                                        R.string.accessibility_shortcut_menu_item_status_disabled);
                    }
                } else {
                    targetSummary = (String) target.getStateDescription();
                }
                convertView2.setContentDescription(
                        ((Object) target.getLabel()) + " " + targetSummary);
                convertView2.setAlpha(1.0f);
            }
        }
        target.updateActionItem(holder, this.mShortcutMenuMode);
        return convertView2;
    }

    void setShortcutMenuMode(int shortcutMenuMode) {
        this.mShortcutMenuMode = shortcutMenuMode;
    }

    int getShortcutMenuMode() {
        return this.mShortcutMenuMode;
    }
}
