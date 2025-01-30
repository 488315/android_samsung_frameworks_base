package com.samsung.server.wallpaper;

import android.content.ComponentName;
import android.content.Context;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.os.SystemClock;
import android.os.UserHandle;
import android.text.TextUtils;

/* compiled from: PreloadedLiveWallpaperHelper.java */
/* loaded from: classes2.dex */
public class ProviderRequester {
    public Context mContext;
    public final Object mLock = new Object();

    public ProviderRequester(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public Bundle requestWallpaperPrepare(ComponentName componentName, int i, int i2, Bundle bundle) {
        Bundle invokeProviderCall;
        if (!isValidComponentName(componentName)) {
            Log.m130e("ProviderRequester", "requestWallpaperPrepare : service component is invalid - " + componentName);
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("requestWallpaperPrepare : ");
        sb.append(componentName);
        sb.append(", which=");
        sb.append(i);
        sb.append(", hasExtras=");
        sb.append(bundle != null);
        Log.m129d("ProviderRequester", sb.toString());
        Bundle bundle2 = new Bundle();
        bundle2.putInt("which", i);
        bundle2.putString("wallpaper_service_class_name", componentName.getClassName());
        bundle2.putBundle("external_params", bundle);
        synchronized (this.mLock) {
            invokeProviderCall = invokeProviderCall(componentName.getPackageName(), i2, "prepare_wallpaper", bundle2);
        }
        return invokeProviderCall;
    }

    public ParcelFileDescriptor requestThumbnail(ComponentName componentName, int i, int i2, int i3, Bundle bundle) {
        Bundle invokeProviderCall;
        if (!isValidComponentName(componentName)) {
            Log.m130e("ProviderRequester", "requestThumbnail : service component is invalid - " + componentName);
            return null;
        }
        String packageName = componentName.getPackageName();
        String className = componentName.getClassName();
        Log.m132i("ProviderRequester", "requestThumbnail : " + componentName + ", which=" + i + ", user=" + i2 + ", orientation=" + i3);
        Bundle bundle2 = new Bundle();
        bundle2.putInt("which", i);
        bundle2.putInt("orientation", i3);
        bundle2.putString("wallpaper_service_class_name", className);
        if (bundle != null) {
            bundle2.putBundle("service_settings", bundle);
        }
        synchronized (this.mLock) {
            invokeProviderCall = invokeProviderCall(packageName, i2, "get_thumbnail", bundle2);
        }
        ParcelFileDescriptor parcelFileDescriptor = invokeProviderCall != null ? (ParcelFileDescriptor) invokeProviderCall.getParcelable("thumbnail_file_descriptor", ParcelFileDescriptor.class) : null;
        if (parcelFileDescriptor == null) {
            Log.m134w("ProviderRequester", "requestThumbnail : fd is null");
        }
        return parcelFileDescriptor;
    }

    public final Bundle invokeProviderCall(String str, int i, String str2, Bundle bundle) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        Bundle bundle2 = null;
        try {
            try {
                bundle2 = this.mContext.createContextAsUser(UserHandle.of(i), 0).getContentResolver().call(Uri.parse("content://" + str + ".provider.sepwallpaper"), str2, (String) null, bundle);
            } catch (Exception e) {
                Log.m130e("ProviderRequester", "invokeProviderCall : e = " + e);
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            Log.m132i("ProviderRequester", "invokeProviderCall : " + str2 + ", elapsed=" + (SystemClock.elapsedRealtime() - elapsedRealtime));
            return bundle2;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final boolean isValidComponentName(ComponentName componentName) {
        return (componentName == null || TextUtils.isEmpty(componentName.getPackageName()) || TextUtils.isEmpty(componentName.getClassName())) ? false : true;
    }
}
