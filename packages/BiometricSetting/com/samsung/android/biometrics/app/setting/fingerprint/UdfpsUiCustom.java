package com.samsung.android.biometrics.app.setting.fingerprint;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.samsung.android.biometrics.app.setting.Utils;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/* loaded from: classes.dex */
public final class UdfpsUiCustom {

    public static final class ThemeReceiver extends BroadcastReceiver {
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (Utils.Config.FP_FEATURE_SENSOR_IS_IN_DISPLAY_TYPE) {
                Log.i("BSS_InDisplayCustom", "onReceive: " + intent.getAction());
                if (!"com.samsung.android.theme.themecenter.THEME_APPLY".equals(intent.getAction()) || context == null) {
                    return;
                }
                try {
                    File file = new File(context.getFilesDir(), "user_fingerprint_touch_effect.json");
                    if (file.exists() && file.delete()) {
                        Log.d("BSS_InDisplayCustom", "resetThemeTouchEffect done");
                    }
                } catch (Exception e) {
                    Log.e("BSS_InDisplayCustom", "resetThemeTouchEffect error : " + e);
                }
            }
        }
    }

    private static void applyCustomResource(Context context, String str, FileDescriptor fileDescriptor, String str2, String str3) {
        if (context == null) {
            Log.i("BSS_InDisplayCustom", "Context or fd is invalid.");
            return;
        }
        Log.i("BSS_InDisplayCustom", "Remove caches:" + (new File(context.getFilesDir(), str2).delete() | new File(context.getFilesDir(), str3).delete()));
        boolean equals = "animation".equals(str);
        boolean equals2 = "png".equals(str);
        if ((!equals && !equals2) || fileDescriptor == null) {
            Log.i("BSS_InDisplayCustom", "Custom effect cleared.");
            return;
        }
        if (!equals) {
            str2 = str3;
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(fileDescriptor);
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(new File(context.getFilesDir(), str2));
                try {
                    FileChannel channel = fileInputStream.getChannel();
                    try {
                        FileChannel channel2 = fileOutputStream.getChannel();
                        try {
                            Log.i("BSS_InDisplayCustom", "completed, val = " + channel.transferTo(0L, channel.size(), channel2));
                            if (channel2 != null) {
                                channel2.close();
                            }
                            channel.close();
                            fileOutputStream.close();
                            fileInputStream.close();
                        } finally {
                        }
                    } finally {
                    }
                } finally {
                }
            } finally {
            }
        } catch (IOException e) {
            Log.e("BSS_InDisplayCustom", "Error in opening: " + e);
        }
    }

    public static void setCustomFingerIcon(Context context, String str, FileDescriptor fileDescriptor) {
        StringBuilder sb = new StringBuilder("custom Finger Icon (");
        sb.append(str);
        sb.append(", ");
        sb.append(fileDescriptor != null ? fileDescriptor.toString() : "null");
        sb.append(")");
        Log.i("BSS_InDisplayCustom", sb.toString());
        applyCustomResource(context, str, fileDescriptor, "user_fingerprint_icon.json", "user_fingerprint_icon.png");
    }

    public static void setCustomTouchEffect(Context context, String str, FileDescriptor fileDescriptor) {
        StringBuilder sb = new StringBuilder("custom Touch Effect (");
        sb.append(str);
        sb.append(", ");
        sb.append(fileDescriptor != null ? fileDescriptor.toString() : "null");
        sb.append(")");
        Log.i("BSS_InDisplayCustom", sb.toString());
        applyCustomResource(context, str, fileDescriptor, "user_fingerprint_touch_effect.json", "user_fingerprint_touch_effect.png");
    }
}
