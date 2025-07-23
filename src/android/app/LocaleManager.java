package android.app;

import android.annotation.SystemApi;
import android.content.Context;
import android.content.res.Configuration;
import android.os.LocaleList;
import android.os.RemoteException;

/* loaded from: classes.dex */
public class LocaleManager {
    private static final String TAG = "LocaleManager";
    private Context mContext;
    private ILocaleManager mService;

    public LocaleManager(Context context, ILocaleManager iLocaleManager) {
        this.mContext = context;
        this.mService = iLocaleManager;
    }

    public void setApplicationLocales(LocaleList localeList) {
        setApplicationLocales(this.mContext.getPackageName(), localeList, false);
    }

    @SystemApi
    public void setApplicationLocales(String str, LocaleList localeList) {
        setApplicationLocales(str, localeList, true);
    }

    private void setApplicationLocales(String str, LocaleList localeList, boolean z) {
        try {
            this.mService.setApplicationLocales(str, this.mContext.getUserId(), localeList, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public LocaleList getApplicationLocales() {
        return getApplicationLocales(this.mContext.getPackageName());
    }

    public LocaleList getApplicationLocales(String str) {
        try {
            return this.mService.getApplicationLocales(str, this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public LocaleList getSystemLocales() {
        try {
            return this.mService.getSystemLocales();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setSystemLocales(LocaleList localeList) {
        try {
            Configuration configuration = new Configuration();
            configuration.setLocales(localeList);
            ActivityManager.getService().updatePersistentConfiguration(configuration);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setOverrideLocaleConfig(LocaleConfig localeConfig) {
        try {
            this.mService.setOverrideLocaleConfig(this.mContext.getPackageName(), this.mContext.getUserId(), localeConfig);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public LocaleConfig getOverrideLocaleConfig() {
        try {
            return this.mService.getOverrideLocaleConfig(this.mContext.getPackageName(), this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
