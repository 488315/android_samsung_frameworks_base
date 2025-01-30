package com.samsung.android.server.pm.install;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.provider.Settings;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes2.dex */
public abstract class PackageBlockListPolicy {
    public static final AtomicBoolean sIsRduDevice = new AtomicBoolean(false);
    public static HashSet sLduBlocklist;

    public static boolean isBlocked(String str) {
        if (sLduBlocklist == null) {
            sLduBlocklist = new HashSet(new PmConfigParser().parsePackages("/system/etc/ldu_blocklist.xml"));
        }
        return sLduBlocklist.contains(str);
    }

    public static void registerContentObserverForRdu(Context context, Handler handler) {
        final ContentResolver contentResolver = context.getContentResolver();
        sIsRduDevice.set(Settings.Secure.getInt(contentResolver, "shopdemo", 0) == 1);
        contentResolver.registerContentObserver(Settings.Secure.getUriFor("shopdemo"), false, new ContentObserver(handler) { // from class: com.samsung.android.server.pm.install.PackageBlockListPolicy.1
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                PackageBlockListPolicy.sIsRduDevice.set(Settings.Secure.getInt(contentResolver, "shopdemo", 0) == 1);
            }
        }, -1);
    }

    public static boolean isRdu() {
        return sIsRduDevice.get();
    }
}
