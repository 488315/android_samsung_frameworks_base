package com.samsung.android.knox.browser;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.browser.IBrowserPolicy;
import com.samsung.android.knox.license.EnterpriseLicenseManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class BrowserPolicy {
    public static String TAG = "BrowserPolicy";
    public ContextInfo mContextInfo;
    public IBrowserPolicy mService = IBrowserPolicy.Stub.asInterface(ServiceManager.getService("browser_policy"));

    public BrowserPolicy(ContextInfo contextInfo) {
        this.mContextInfo = contextInfo;
    }

    public final boolean addWebBookmarkBitmap(Uri uri, String str, Bitmap bitmap) {
        EnterpriseLicenseManager.log(this.mContextInfo, "BrowserPolicy.addWebBookmarkBitmap");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.addWebBookmarkBitmap(this.mContextInfo, uri, str, bitmap);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed addWebBookmarkBitmap", e);
            return false;
        }
    }

    public final boolean addWebBookmarkByteBuffer(Uri uri, String str, byte[] bArr) {
        EnterpriseLicenseManager.log(this.mContextInfo, "BrowserPolicy.addWebBookmarkByteBuffer");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.addWebBookmarkByteBuffer(this.mContextInfo, uri, str, bArr);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed addWebBookmarkByteBuffer", e);
            return false;
        }
    }

    public final boolean clearHttpProxy() {
        EnterpriseLicenseManager.log(this.mContextInfo, "BrowserPolicy.clearHttpProxy");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.clearHttpProxy(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return false;
        }
    }

    public final boolean deleteWebBookmark(Uri uri, String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "BrowserPolicy.deleteWebBookmark");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.deleteWebBookmark(this.mContextInfo, uri, str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed deleteWebBookmark", e);
            return false;
        }
    }

    public final boolean getAutoFillSetting() {
        return getBrowserSettingStatus(4);
    }

    public final boolean getBrowserSettingStatus(int i) {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.getBrowserSettingStatus(this.mContextInfo, i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return true;
        }
    }

    public final boolean getCookiesSetting() {
        return getBrowserSettingStatus(2);
    }

    public final boolean getForceFraudWarningSetting() {
        return getBrowserSettingStatus(8);
    }

    public final String getHttpProxy() {
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getHttpProxy(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return null;
        }
    }

    public final boolean getJavaScriptSetting() {
        return getBrowserSettingStatus(16);
    }

    public final boolean getPopupsSetting() {
        return getBrowserSettingStatus(1);
    }

    public final IBrowserPolicy getService() {
        if (this.mService == null) {
            this.mService = IBrowserPolicy.Stub.asInterface(ServiceManager.getService("browser_policy"));
        }
        return this.mService;
    }

    public final boolean setAutoFillSetting(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "BrowserPolicy.setAutoFillSetting");
        return setBrowserSettingStatus(z, 4);
    }

    public final boolean setBrowserSettingStatus(boolean z, int i) {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setBrowserSettingStatus(this.mContextInfo, z, i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return false;
        }
    }

    public final boolean setCookiesSetting(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "BrowserPolicy.setCookiesSetting");
        return setBrowserSettingStatus(z, 2);
    }

    public final boolean setForceFraudWarningSetting(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "BrowserPolicy.setForceFraudWarningSetting");
        return setBrowserSettingStatus(z, 8);
    }

    public final boolean setHttpProxy(String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "BrowserPolicy.setHttpProxy");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setHttpProxy(this.mContextInfo, str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return false;
        }
    }

    public final boolean setJavaScriptSetting(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "BrowserPolicy.setJavaScriptSetting");
        return setBrowserSettingStatus(z, 16);
    }

    public final boolean setPopupsSetting(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "BrowserPolicy.setPopupsSetting");
        return setBrowserSettingStatus(z, 1);
    }
}
