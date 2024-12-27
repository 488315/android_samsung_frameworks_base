package com.samsung.android.biometrics.app.setting.fingerprint;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.util.Log;

import com.samsung.android.biometrics.app.setting.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
/* loaded from: classes.dex */
public abstract class UdfpsUiCustom {

    /* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
    public final class LockstarResourceReceiver extends BroadcastReceiver {
        public static final /* synthetic */ int $r8$clinit = 0;

        @Override // android.content.BroadcastReceiver
        public final void onReceive(final Context context, Intent intent) {
            if (Utils.Config.FP_FEATURE_SENSOR_IS_IN_DISPLAY_TYPE) {
                Log.i("BSS_InDisplayCustom", "onReceive: " + intent.getAction());
                if ("com.samsung.lockstar.fingerprint.vi.update".equals(intent.getAction())) {
                    if (context != null) {
                        try {
                            File file =
                                    new File(
                                            context.getFilesDir(),
                                            "user_fingerprint_touch_effect.json");
                            if (file.exists() && file.delete()) {
                                Log.d("BSS_InDisplayCustom", "resetThemeTouchEffect done");
                            }
                        } catch (Exception e) {
                            Log.e("BSS_InDisplayCustom", "resetThemeTouchEffect error : " + e);
                        }
                    }
                    final String stringExtra = intent.getStringExtra("FINGERPRINT_VI_RESOURCE");
                    if (stringExtra != null) {
                        new Thread(
                                        new Runnable() { // from class:
                                            // com.samsung.android.biometrics.app.setting.fingerprint.UdfpsUiCustom$LockstarResourceReceiver$$ExternalSyntheticLambda0
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                InputStream open;
                                                Context context2 = context;
                                                String str = stringExtra;
                                                int i =
                                                        UdfpsUiCustom.LockstarResourceReceiver
                                                                .$r8$clinit;
                                                try {
                                                    AssetManager assets =
                                                            context2.getPackageManager()
                                                                    .getResourcesForApplication(
                                                                            "com.samsung.systemui.lockstar")
                                                                    .getAssets();
                                                    try {
                                                        try {
                                                            open = assets.open(str, 3);
                                                        } catch (Resources.NotFoundException
                                                                | IOException e2) {
                                                            Log.e(
                                                                    "BSS_InDisplayCustom",
                                                                    "applyCustomEffectFromLockStar"
                                                                        + " error : "
                                                                            + e2);
                                                        }
                                                        try {
                                                            StringWriter stringWriter =
                                                                    new StringWriter();
                                                            char[] cArr = new char[2048];
                                                            try {
                                                                BufferedReader bufferedReader =
                                                                        new BufferedReader(
                                                                                new InputStreamReader(
                                                                                        open,
                                                                                        StandardCharsets
                                                                                                .UTF_8));
                                                                while (true) {
                                                                    try {
                                                                        int read =
                                                                                bufferedReader.read(
                                                                                        cArr);
                                                                        if (read == -1) {
                                                                            break;
                                                                        } else {
                                                                            stringWriter.write(
                                                                                    cArr, 0, read);
                                                                        }
                                                                    } catch (Throwable th) {
                                                                        try {
                                                                            bufferedReader.close();
                                                                        } catch (Throwable th2) {
                                                                            th.addSuppressed(th2);
                                                                        }
                                                                        throw th;
                                                                    }
                                                                }
                                                                bufferedReader.close();
                                                            } catch (IOException
                                                                    | IllegalArgumentException e3) {
                                                                Log.e(
                                                                        "BSS_InDisplayCustom",
                                                                        "applyCustomEffectFromLockStar"
                                                                            + " write error : "
                                                                                + e3);
                                                            }
                                                            UdfpsUiCustom.writeToFile(
                                                                    context2,
                                                                    stringWriter.toString());
                                                            if (open != null) {
                                                                open.close();
                                                            }
                                                            if (assets != null) {
                                                                assets.close();
                                                            }
                                                        } catch (Throwable th3) {
                                                            if (open != null) {
                                                                try {
                                                                    open.close();
                                                                } catch (Throwable th4) {
                                                                    th3.addSuppressed(th4);
                                                                }
                                                            }
                                                            throw th3;
                                                        }
                                                    } finally {
                                                    }
                                                } catch (PackageManager.NameNotFoundException e4) {
                                                    e4.printStackTrace();
                                                }
                                            }
                                        })
                                .start();
                    }
                }
            }
        }
    }

    public static void applyCustomResource(
            Context context, String str, FileDescriptor fileDescriptor, String str2, String str3) {
        if (context == null) {
            Log.i("BSS_InDisplayCustom", "Context or fd is invalid.");
            return;
        }
        Log.i(
                "BSS_InDisplayCustom",
                "Remove caches:"
                        + (new File(context.getFilesDir(), str2).delete()
                                | new File(context.getFilesDir(), str3).delete()));
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
                FileOutputStream fileOutputStream =
                        new FileOutputStream(new File(context.getFilesDir(), str2));
                try {
                    FileChannel channel = fileInputStream.getChannel();
                    try {
                        FileChannel channel2 = fileOutputStream.getChannel();
                        try {
                            Log.i(
                                    "BSS_InDisplayCustom",
                                    "completed, val = "
                                            + channel.transferTo(0L, channel.size(), channel2));
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

    public static void writeToFile(Context context, String str) {
        try {
            FileOutputStream fileOutputStream =
                    new FileOutputStream(
                            new File(context.getFilesDir(), "user_fingerprint_touch_effect.json"));
            try {
                fileOutputStream.write(str.getBytes(StandardCharsets.UTF_8));
                fileOutputStream.close();
            } finally {
            }
        } catch (IOException e) {
            Log.e("BSS_InDisplayCustom", "writeToFile error : " + e);
        }
    }
}
