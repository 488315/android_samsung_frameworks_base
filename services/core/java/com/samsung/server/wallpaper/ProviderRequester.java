package com.samsung.server.wallpaper;

import android.content.ComponentName;
import android.content.Context;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.UserHandle;
import android.text.TextUtils;

import com.samsung.android.knoxguard.service.utils.Constants;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class ProviderRequester {
    public Context mContext;

    public static boolean isValidComponentName(ComponentName componentName) {
        return (componentName == null
                        || TextUtils.isEmpty(componentName.getPackageName())
                        || TextUtils.isEmpty(componentName.getClassName()))
                ? false
                : true;
    }

    public final Bundle invokeProviderCall(int i, String str, String str2, Bundle bundle) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        Bundle bundle2 = null;
        try {
            try {
                Uri parse = Uri.parse("content://" + str + ".provider.sepwallpaper");
                if (!TextUtils.isEmpty(str)
                        && Constants.SYSTEMUI_PACKAGE_NAME.equals(str)
                        && i > 0) {
                    Log.d(
                            "ProviderRequester",
                            "invokeProviderCall: userId = "
                                    + i
                                    + ", Covert userId to owner to create context for systemui.");
                    i = 0;
                }
                bundle2 =
                        this.mContext
                                .createContextAsUser(UserHandle.of(i), 0)
                                .getContentResolver()
                                .call(parse, str2, (String) null, bundle);
            } catch (Exception e) {
                Log.e("ProviderRequester", "invokeProviderCall : e=" + e, e);
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            Log.i(
                    "ProviderRequester",
                    "invokeProviderCall : "
                            + str2
                            + ", elapsed="
                            + (SystemClock.elapsedRealtime() - elapsedRealtime));
            return bundle2;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }
}
