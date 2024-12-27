package com.samsung.android.server.audio.utils;

import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.AudioSystem;
import android.os.Binder;
import android.os.RemoteException;
import android.provider.Settings;
import android.util.Log;
import com.samsung.android.multiwindow.MultiWindowManager;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class AudioUtils {
    public static final Set DEVICE_OUT_WIRED_DEVICE_SET;
    public static final Set SKIP_RESTORE_DEVICE_SET;

    static {
        HashSet hashSet = new HashSet();
        DEVICE_OUT_WIRED_DEVICE_SET = hashSet;
        hashSet.add(4);
        hashSet.add(8);
        hashSet.addAll(AudioSystem.DEVICE_OUT_ALL_USB_SET);
        HashSet hashSet2 = new HashSet();
        SKIP_RESTORE_DEVICE_SET = hashSet2;
        hashSet2.add(2);
        hashSet2.add(32768);
    }

    public static boolean checkRunningBackground(String str) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            List visibleTasks = new MultiWindowManager().getVisibleTasks();
            Binder.restoreCallingIdentity(clearCallingIdentity);
            int size = visibleTasks.size();
            for (int i = 0; i < size; i++) {
                if (str.equals(((ActivityManager.RunningTaskInfo) visibleTasks.get(i)).topActivity.getPackageName())) {
                    Log.d("AS.AudioUtils", "checkRunningBackground : visible Tasks =".concat(str));
                    return false;
                }
            }
            return true;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public static int getCurrentUserId() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            int i = ActivityManager.getService().getCurrentUser().id;
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return i;
        } catch (RemoteException unused) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return 0;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public static int getUidForPackage(Context context, String str) {
        try {
            return context.getPackageManager().getApplicationInfoAsUser(str, 0, getCurrentUserId()).uid;
        } catch (PackageManager.NameNotFoundException unused) {
            return 0;
        }
    }

    public static boolean isUsingAudioForUid(int i) {
        return "true".equalsIgnoreCase(AudioSystem.getParameters("l_multi_sound_active_track_uid=" + i));
    }

    public static boolean isWiredDeviceType(int i) {
        return ((HashSet) DEVICE_OUT_WIRED_DEVICE_SET).contains(Integer.valueOf(i));
    }

    public static void setSettingsInt(ContentResolver contentResolver, String str, int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Settings.System.putInt(contentResolver, str, i);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0072 A[Catch: Exception -> 0x0076, TRY_ENTER, TRY_LEAVE, TryCatch #2 {Exception -> 0x0076, blocks: (B:23:0x004c, B:27:0x0072, B:32:0x0082, B:37:0x007f, B:39:0x0052, B:42:0x0059, B:25:0x006a, B:34:0x007a), top: B:22:0x004c, inners: #0, #3 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void wakeUpDeviceByWiredHeadset(android.content.Context r10, int r11) {
        /*
            boolean r0 = com.samsung.android.audio.Rune.SEC_AUDIO_SCREEN_OFF_MUSIC
            if (r0 == 0) goto L90
            java.lang.String r0 = "content://"
            android.content.ContentResolver r1 = r10.getContentResolver()
            android.content.pm.PackageManager r2 = r10.getPackageManager()
            java.lang.String r3 = "com.sec.android.app.music.shared"
            r7 = 0
            android.content.pm.ProviderInfo r2 = r2.resolveContentProvider(r3, r7)
            r8 = 1
            if (r2 == 0) goto L1a
            r2 = r8
            goto L1b
        L1a:
            r2 = r7
        L1b:
            android.content.pm.PackageManager r4 = r10.getPackageManager()
            java.lang.String r5 = "com.samsung.android.app.music.chn.setting"
            android.content.pm.ProviderInfo r4 = r4.resolveContentProvider(r5, r7)
            if (r4 == 0) goto L29
            r4 = r8
            goto L2a
        L29:
            r4 = r7
        L2a:
            java.lang.String r9 = "AS.SamsungMusicHelper"
            if (r2 != 0) goto L36
            if (r4 != 0) goto L36
            java.lang.String r0 = "ScreenOffMusicProvider does not exist"
            android.util.Log.i(r9, r0)
            goto L86
        L36:
            if (r4 == 0) goto L39
            r3 = r5
        L39:
            java.lang.String r0 = r0.concat(r3)     // Catch: java.lang.NullPointerException -> L86
            android.net.Uri r0 = android.net.Uri.parse(r0)     // Catch: java.lang.NullPointerException -> L86
            java.lang.String r2 = "setting/ready_screen_off_music"
            android.net.Uri r2 = android.net.Uri.withAppendedPath(r0, r2)     // Catch: java.lang.NullPointerException -> L86
            r3 = 0
            r4 = 0
            r5 = 0
            r6 = 0
            android.database.Cursor r0 = r1.query(r2, r3, r4, r5, r6)     // Catch: java.lang.Exception -> L76
            if (r0 == 0) goto L6a
            int r1 = r0.getCount()     // Catch: java.lang.Throwable -> L68
            if (r1 > 0) goto L59
            goto L6a
        L59:
            r0.moveToFirst()     // Catch: java.lang.Throwable -> L68
            java.lang.String r1 = r0.getString(r8)     // Catch: java.lang.Throwable -> L68
            java.lang.String r2 = "true"
            boolean r7 = r2.equals(r1)     // Catch: java.lang.Throwable -> L68
            goto L70
        L68:
            r1 = move-exception
            goto L78
        L6a:
            java.lang.String r1 = "screen off music query failed"
            android.util.Log.e(r9, r1)     // Catch: java.lang.Throwable -> L68
        L70:
            if (r0 == 0) goto L86
            r0.close()     // Catch: java.lang.Exception -> L76
            goto L86
        L76:
            r0 = move-exception
            goto L83
        L78:
            if (r0 == 0) goto L82
            r0.close()     // Catch: java.lang.Throwable -> L7e
            goto L82
        L7e:
            r0 = move-exception
            r1.addSuppressed(r0)     // Catch: java.lang.Exception -> L76
        L82:
            throw r1     // Catch: java.lang.Exception -> L76
        L83:
            r0.printStackTrace()
        L86:
            if (r7 == 0) goto L90
            java.lang.String r10 = "AS.AudioUtils"
            java.lang.String r11 = "Screen on is handled by samsung music"
            android.util.Log.i(r10, r11)
            return
        L90:
            boolean r11 = isWiredDeviceType(r11)
            if (r11 == 0) goto La9
            java.lang.String r11 = "power"
            java.lang.Object r10 = r10.getSystemService(r11)
            android.os.PowerManager r10 = (android.os.PowerManager) r10
            long r0 = android.os.SystemClock.uptimeMillis()
            r11 = 3
            java.lang.String r2 = "WAKE_REASON_PLUGGED_IN"
            r10.semWakeUp(r0, r11, r2)
        La9:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.server.audio.utils.AudioUtils.wakeUpDeviceByWiredHeadset(android.content.Context, int):void");
    }
}
