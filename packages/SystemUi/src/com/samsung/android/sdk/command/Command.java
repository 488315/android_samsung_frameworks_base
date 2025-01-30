package com.samsung.android.sdk.command;

import android.app.PendingIntent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import com.samsung.android.sdk.command.template.CommandTemplate;
import com.samsung.android.sdk.command.template.MediaControlTemplate;
import com.samsung.android.sdk.command.template.SingleChoiceTemplate;
import com.samsung.android.sdk.command.template.SliderTemplate;
import com.samsung.android.sdk.command.template.ToggleTemplate;
import com.samsung.android.sdk.command.template.UnformattedTemplate;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class Command {
    public final String mCategory;
    public final String mClassification;
    public final String mCommandId;
    public final String mCustomConfigComponent;
    public final String mForTarget;
    public final int mIconResId;
    public final PendingIntent mLaunchIntent;
    public String mPackageName;
    public final int mStatus;
    public final String mStatusCode;
    public final String mStatusText;
    public final String mSubCategory;
    public final String mSubTitle;
    public final CommandTemplate mTemplate;
    public final String mTitle;

    public final Bundle getDataBundle() {
        Bundle bundle = new Bundle();
        String str = this.mCommandId;
        if (!TextUtils.isEmpty(str)) {
            bundle.putString("key_command_id", str);
        }
        String str2 = this.mTitle;
        if (!TextUtils.isEmpty(str2)) {
            bundle.putString("key_title", str2);
        }
        String str3 = this.mSubTitle;
        if (!TextUtils.isEmpty(str3)) {
            bundle.putString("key_subtitle", str3);
        }
        if (!TextUtils.isEmpty(this.mPackageName)) {
            bundle.putString("key_pacakge_name", this.mPackageName);
        }
        String str4 = this.mForTarget;
        if (!TextUtils.isEmpty(str4)) {
            bundle.putString("key_for_target", str4);
        }
        String str5 = this.mClassification;
        if (!TextUtils.isEmpty(str5)) {
            bundle.putString("key_classification", str5);
        }
        String str6 = this.mCategory;
        if (!TextUtils.isEmpty(str6)) {
            bundle.putString("key_category", str6);
        }
        String str7 = this.mSubCategory;
        if (!TextUtils.isEmpty(str7)) {
            bundle.putString("key_subcategory", str7);
        }
        PendingIntent pendingIntent = this.mLaunchIntent;
        if (pendingIntent != null) {
            bundle.putParcelable("key_launch_intent", pendingIntent);
        }
        String str8 = this.mCustomConfigComponent;
        if (!TextUtils.isEmpty(str8)) {
            bundle.putString("key_custom_config_component", str8);
        }
        String str9 = this.mStatusText;
        if (!TextUtils.isEmpty(str9)) {
            bundle.putString("key_status_text", str9);
        }
        String str10 = this.mStatusCode;
        if (!TextUtils.isEmpty(str10)) {
            bundle.putString("key_status_code", str10);
        }
        CommandTemplate commandTemplate = this.mTemplate;
        if (commandTemplate != null) {
            bundle.putBundle("key_template", commandTemplate.getDataBundle());
        }
        bundle.putInt("key_status", this.mStatus);
        bundle.putInt("key_icon_res_id", this.mIconResId);
        return bundle;
    }

    public final String getPackageName() {
        if (TextUtils.isEmpty(this.mPackageName)) {
            String str = this.mCommandId;
            if (!TextUtils.isEmpty(str)) {
                String authority = Uri.parse(str).getAuthority();
                if (!TextUtils.isEmpty(authority)) {
                    this.mPackageName = authority.replace(".command", "");
                }
            }
        }
        return this.mPackageName;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class StatefulBuilder {
        public final String mCategory;
        public final String mClassification;
        public final String mCommandId;
        public final String mCustomConfigComponent;
        public final String mForTarget;
        public final int mIconResId;
        public PendingIntent mLaunchIntent;
        public final String mPackageName;
        public int mStatus;
        public String mStatusCode;
        public final String mStatusText;
        public final String mSubCategory;
        public final String mSubTitle;
        public CommandTemplate mTemplate;
        public final String mTitle;

        public StatefulBuilder(String str) {
            this.mCommandId = str;
        }

        public final Command build() {
            return new Command(this.mCommandId, this.mTitle, this.mSubTitle, this.mPackageName, this.mForTarget, this.mClassification, this.mCategory, this.mSubCategory, this.mLaunchIntent, this.mCustomConfigComponent, this.mTemplate, this.mStatus, this.mStatusText, this.mStatusCode, this.mIconResId);
        }

        public StatefulBuilder(Command command) {
            this.mCommandId = command.mCommandId;
            this.mTitle = command.mTitle;
            this.mSubTitle = command.mSubTitle;
            this.mPackageName = command.getPackageName();
            this.mForTarget = command.mForTarget;
            this.mClassification = command.mClassification;
            this.mCategory = command.mCategory;
            this.mSubCategory = command.mSubCategory;
            this.mLaunchIntent = command.mLaunchIntent;
            this.mCustomConfigComponent = command.mCustomConfigComponent;
            this.mTemplate = command.mTemplate;
            this.mStatus = command.mStatus;
            this.mStatusText = command.mStatusText;
            this.mStatusCode = command.mStatusCode;
            this.mIconResId = command.mIconResId;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class StatelessBuilder {
        public final String mCategory;
        public final String mClassification;
        public final String mCommandId;
        public final String mCustomConfigComponent;
        public final String mForTarget;
        public final int mIconResId;
        public final PendingIntent mLaunchIntent;
        public final String mPackageName;
        public int mStatus;
        public final String mStatusCode;
        public final String mStatusText;
        public final String mSubCategory;
        public final String mSubTitle;
        public String mTitle;

        public StatelessBuilder(String str) {
            this.mCommandId = str;
        }

        public final Command build() {
            return new Command(this.mCommandId, this.mTitle, this.mSubTitle, this.mPackageName, this.mForTarget, this.mClassification, this.mCategory, this.mSubCategory, this.mLaunchIntent, this.mCustomConfigComponent, CommandTemplate.NO_TEMPLATE, this.mStatus, this.mStatusText, this.mStatusCode, this.mIconResId);
        }

        public StatelessBuilder(Command command) {
            this.mCommandId = command.mCommandId;
            this.mTitle = command.mTitle;
            this.mSubTitle = command.mSubTitle;
            this.mPackageName = command.getPackageName();
            this.mForTarget = command.mForTarget;
            this.mClassification = command.mClassification;
            this.mCategory = command.mCategory;
            this.mSubCategory = command.mSubCategory;
            this.mLaunchIntent = command.mLaunchIntent;
            this.mCustomConfigComponent = command.mCustomConfigComponent;
            this.mStatus = command.mStatus;
            this.mStatusText = command.mStatusText;
            this.mStatusCode = command.mStatusCode;
            this.mIconResId = command.mIconResId;
        }
    }

    public Command() {
    }

    private Command(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, PendingIntent pendingIntent, String str9, CommandTemplate commandTemplate, int i, String str10, String str11, int i2) {
        this.mCommandId = str;
        this.mTitle = str2;
        this.mSubTitle = str3;
        this.mPackageName = str4;
        this.mForTarget = str5;
        this.mClassification = str6;
        this.mCategory = str7;
        this.mSubCategory = str8;
        this.mLaunchIntent = pendingIntent;
        this.mCustomConfigComponent = str9;
        this.mTemplate = commandTemplate;
        this.mStatus = i;
        this.mStatusText = str10;
        this.mStatusCode = str11;
        this.mIconResId = i2;
    }

    public Command(Bundle bundle) {
        CommandTemplate toggleTemplate;
        if (bundle.containsKey("key_command_id")) {
            this.mCommandId = bundle.getString("key_command_id");
        }
        if (bundle.containsKey("key_title")) {
            this.mTitle = bundle.getString("key_title");
        }
        if (bundle.containsKey("key_subtitle")) {
            this.mSubTitle = bundle.getString("key_subtitle");
        }
        if (bundle.containsKey("key_pacakge_name")) {
            this.mPackageName = bundle.getString("key_pacakge_name");
        }
        if (bundle.containsKey("key_for_target")) {
            this.mForTarget = bundle.getString("key_for_target");
        }
        if (bundle.containsKey("key_classification")) {
            this.mClassification = bundle.getString("key_classification");
        }
        if (bundle.containsKey("key_category")) {
            this.mCategory = bundle.getString("key_category");
        }
        if (bundle.containsKey("key_subcategory")) {
            this.mSubCategory = bundle.getString("key_subcategory");
        }
        if (bundle.containsKey("key_launch_intent")) {
            this.mLaunchIntent = (PendingIntent) bundle.getParcelable("key_launch_intent");
        }
        if (bundle.containsKey("key_custom_config_component")) {
            this.mCustomConfigComponent = bundle.getString("key_custom_config_component");
        }
        if (bundle.containsKey("key_status_text")) {
            this.mStatusText = bundle.getString("key_status_text");
        }
        if (bundle.containsKey("key_status_code")) {
            this.mStatusCode = bundle.getString("key_status_code");
        }
        if (bundle.containsKey("key_template")) {
            Bundle bundle2 = bundle.getBundle("key_template");
            CommandTemplate commandTemplate = CommandTemplate.ERROR_TEMPLATE;
            if (bundle2 != null) {
                int i = bundle2.getInt("key_template_type", 0);
                try {
                    if (i != 1) {
                        if (i == 2) {
                            toggleTemplate = new ToggleTemplate(bundle2);
                        } else if (i == 3) {
                            toggleTemplate = new SliderTemplate(bundle2);
                        } else if (i == 5) {
                            toggleTemplate = new SingleChoiceTemplate(bundle2);
                        } else if (i == 6) {
                            toggleTemplate = new UnformattedTemplate(bundle2);
                        } else if (i == 7) {
                            toggleTemplate = new MediaControlTemplate(bundle2);
                        }
                        commandTemplate = toggleTemplate;
                    } else {
                        commandTemplate = CommandTemplate.NO_TEMPLATE;
                    }
                } catch (Exception unused) {
                }
            }
            this.mTemplate = commandTemplate;
        }
        if (bundle.containsKey("key_status")) {
            this.mStatus = bundle.getInt("key_status");
        }
        if (bundle.containsKey("key_icon_res_id")) {
            this.mIconResId = bundle.getInt("key_icon_res_id");
        }
    }
}
