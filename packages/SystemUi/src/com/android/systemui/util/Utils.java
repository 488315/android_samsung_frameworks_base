package com.android.systemui.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Settings;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.shared.system.QuickStepContract;
import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class Utils {
    public static Boolean sUseQsMediaPlayer;

    /* JADX WARN: Type inference failed for: r7v4 */
    /* JADX WARN: Type inference failed for: r7v5, types: [boolean] */
    /* JADX WARN: Type inference failed for: r7v7 */
    public static int getEnterprisePolicyEnabled(Context context, String str, String str2, String[] strArr) {
        Uri parse = Uri.parse(str);
        int i = -1;
        ?? r7 = -1;
        if (context == null) {
            return -1;
        }
        Cursor query = context.getContentResolver().query(parse, null, str2, strArr, null);
        if (query != null) {
            try {
                query.moveToFirst();
                r7 = query.getString(query.getColumnIndex(str2)).equals("true");
            } catch (Exception unused) {
            } catch (Throwable th) {
                query.close();
                throw th;
            }
            query.close();
            i = r7;
        }
        return i;
    }

    public static boolean isGesturalModeOnDefaultDisplay(Context context, DisplayTracker displayTracker, int i) {
        int displayId = context.getDisplayId();
        displayTracker.getClass();
        return displayId == 0 && QuickStepContract.isGesturalMode(i);
    }

    public static boolean isHeadlessRemoteDisplayProvider(PackageManager packageManager, String str) {
        if (packageManager.checkPermission("android.permission.REMOTE_DISPLAY_PROVIDER", str) != 0) {
            return false;
        }
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.LAUNCHER");
        intent.setPackage(str);
        return packageManager.queryIntentActivities(intent, 0).isEmpty();
    }

    public static void safeForeach(List list, Consumer consumer) {
        int size = list.size();
        while (true) {
            size--;
            if (size < 0) {
                return;
            }
            Object obj = list.get(size);
            if (obj != null) {
                consumer.accept(obj);
            }
        }
    }

    public static boolean useMediaResumption(Context context) {
        return useQsMediaPlayer(context) && Settings.Secure.getInt(context.getContentResolver(), "qs_media_resumption", 1) > 0;
    }

    public static boolean useQsMediaPlayer(Context context) {
        if (sUseQsMediaPlayer == null) {
            sUseQsMediaPlayer = Boolean.valueOf(Settings.Global.getInt(context.getContentResolver(), "qs_media_controls", 1) > 0);
        }
        return sUseQsMediaPlayer.booleanValue();
    }
}
