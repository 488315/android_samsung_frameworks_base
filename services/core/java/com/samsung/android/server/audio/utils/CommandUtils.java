package com.samsung.android.server.audio.utils;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.samsung.android.knox.custom.KnoxCustomManagerService;

public abstract class CommandUtils {
    public static void executePanel(Context context, String str) {
        Intent intent = new Intent();
        intent.setFlags(268435456);
        intent.setComponent(
                new ComponentName(
                        KnoxCustomManagerService.SETTING_PKG_NAME,
                        "com.android.settings.panel.SettingsPanelActivity"));
        if ("mediapanel".equals(str)) {
            intent.setAction("com.android.settings.panel.action.MEDIA_OUTPUT");
        } else if ("volumepanel".equals(str)) {
            intent.setAction("android.settings.panel.action.VOLUME");
        }
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }
}
