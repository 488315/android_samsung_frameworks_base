package com.android.server.webkit;

import android.R;
import android.app.ActivityManager;
import android.app.AppGlobals;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.UserInfo;
import android.content.res.XmlResourceParser;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.os.UserManager;
import android.provider.Settings;
import android.util.AndroidRuntimeException;
import android.util.Log;
import android.util.Slog;
import android.webkit.WebViewProviderInfo;

import com.android.internal.util.XmlUtils;
import com.android.server.LocalServices;
import com.android.server.PinnerService;
import com.android.server.flags.Flags;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public final class SystemImpl {
    public final WebViewProviderInfo[] mWebViewProviderPackages;

    public abstract class LazyHolder {
        public static final SystemImpl INSTANCE = new SystemImpl();
    }

    public SystemImpl() {
        XmlResourceParser xml;
        ArrayList arrayList = new ArrayList();
        XmlResourceParser xmlResourceParser = null;
        try {
            try {
                xml =
                        AppGlobals.getInitialApplication()
                                .getResources()
                                .getXml(R.xml.irq_device_map);
            } catch (IOException | XmlPullParserException e) {
                e = e;
            }
            try {
                try {
                    XmlUtils.beginDocument(xml, "webviewproviders");
                    int i = 0;
                    int i2 = 0;
                    while (true) {
                        XmlUtils.nextElement(xml);
                        String name = xml.getName();
                        if (name == null) {
                            xml.close();
                            if (i == 0) {
                                throw new AndroidRuntimeException(
                                        "There must be at least one WebView package that is"
                                            + " available by default");
                            }
                            this.mWebViewProviderPackages =
                                    (WebViewProviderInfo[])
                                            arrayList.toArray(
                                                    new WebViewProviderInfo[arrayList.size()]);
                            return;
                        }
                        if (name.equals("webviewprovider")) {
                            String attributeValue = xml.getAttributeValue(null, "packageName");
                            if (attributeValue == null) {
                                throw new AndroidRuntimeException(
                                        "WebView provider in framework resources missing package"
                                            + " name");
                            }
                            String attributeValue2 = xml.getAttributeValue(null, "description");
                            if (attributeValue2 == null) {
                                throw new AndroidRuntimeException(
                                        "WebView provider in framework resources missing"
                                            + " description");
                            }
                            WebViewProviderInfo webViewProviderInfo =
                                    new WebViewProviderInfo(
                                            attributeValue,
                                            attributeValue2,
                                            "true"
                                                    .equals(
                                                            xml.getAttributeValue(
                                                                    null, "availableByDefault")),
                                            "true"
                                                    .equals(
                                                            xml.getAttributeValue(
                                                                    null, "isFallback")),
                                            readSignatures(xml));
                            if (webViewProviderInfo.isFallback) {
                                i2++;
                                if (!webViewProviderInfo.availableByDefault) {
                                    throw new AndroidRuntimeException(
                                            "Each WebView fallback package must be available by"
                                                + " default.");
                                }
                                if (i2 > 1) {
                                    throw new AndroidRuntimeException(
                                            "There can be at most one WebView fallback package.");
                                }
                            }
                            i = webViewProviderInfo.availableByDefault ? i + 1 : i;
                            arrayList.add(webViewProviderInfo);
                        } else {
                            Log.e("SystemImpl", "Found an element that is not a WebView provider");
                        }
                    }
                } catch (Throwable th) {
                    th = th;
                    xmlResourceParser = xml;
                    if (xmlResourceParser != null) {
                        xmlResourceParser.close();
                    }
                    throw th;
                }
            } catch (IOException | XmlPullParserException e2) {
                e = e2;
                xmlResourceParser = xml;
                throw new AndroidRuntimeException("Error when parsing WebView config " + e);
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static String[] readSignatures(XmlResourceParser xmlResourceParser) {
        ArrayList arrayList = new ArrayList();
        int depth = xmlResourceParser.getDepth();
        while (XmlUtils.nextElementWithin(xmlResourceParser, depth)) {
            if (xmlResourceParser.getName().equals("signature")) {
                arrayList.add(xmlResourceParser.nextText());
            } else {
                Log.e(
                        "SystemImpl",
                        "Found an element in a webview provider that is not a signature");
            }
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    public final void enablePackageForAllUsers(Context context, String str) {
        Iterator it = ((UserManager) context.getSystemService("user")).getUsers().iterator();
        while (it.hasNext()) {
            int i = ((UserInfo) it.next()).id;
            try {
                AppGlobals.getPackageManager()
                        .setApplicationEnabledSetting(str, 0, 0, i, (String) null);
            } catch (RemoteException | IllegalArgumentException e) {
                Log.w("SystemImpl", "Tried to enable " + str + " for user " + i + ": " + e);
            }
        }
    }

    public final PackageInfo getPackageInfoForProvider(WebViewProviderInfo webViewProviderInfo) {
        return AppGlobals.getInitialApplication()
                .getPackageManager()
                .getPackageInfo(webViewProviderInfo.packageName, 272630976);
    }

    public final void killPackageDependents(String str) {
        try {
            ActivityManager.getService().killPackageDependents(str, -1);
        } catch (RemoteException e) {
            Slog.wtf("SystemImpl", "failed to call killPackageDependents for " + str, e);
        }
    }

    public final void pinWebviewIfRequired(ApplicationInfo applicationInfo) {
        int i;
        PinnerService.PinnedFile pinnedFile;
        int i2;
        PinnerService pinnerService = (PinnerService) LocalServices.getService(PinnerService.class);
        pinnerService.getClass();
        if (Flags.pinWebview()) {
            i = SystemProperties.getInt("pinner.pin_webview_size", -1);
            if (i == -1) {
                i = pinnerService.mConfiguredWebviewPinBytes;
            }
        } else {
            i = 0;
        }
        if (i <= 0) {
            return;
        }
        Iterator it = pinnerService.getAllPinsForGroup("webview").iterator();
        while (it.hasNext()) {
            pinnerService.unpinFile(((PinnerService.PinnedFile) it.next()).fileName);
        }
        ArrayList arrayList = new ArrayList();
        boolean z = applicationInfo.metaData.getBoolean("PIN_SHARED_LIBS_FIRST", true);
        String[] strArr = applicationInfo.sharedLibraryFiles;
        if (strArr != null) {
            for (String str : strArr) {
                arrayList.add(str);
            }
        }
        arrayList.add(applicationInfo.sourceDir);
        if (!z) {
            Collections.reverse(arrayList);
        }
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            String str2 = (String) it2.next();
            if (i <= 0) {
                return;
            }
            synchronized (pinnerService) {
                pinnedFile = (PinnerService.PinnedFile) pinnerService.mPinnedFiles.get(str2);
            }
            if (pinnedFile != null) {
                if (pinnedFile.bytesPinned != i) {
                    pinnerService.unpinFile(str2);
                }
                i2 = 0;
                i -= i2;
            }
            boolean endsWith = str2.endsWith(".apk");
            pinnerService.mInjector.getClass();
            PinnerService.PinnedFile pinFileInternal =
                    PinnerService.pinFileInternal(i, str2, endsWith);
            if (pinFileInternal == null) {
                Slog.e("PinnerService", "Failed to pin file = ".concat(str2));
                i2 = 0;
                i -= i2;
            } else {
                pinFileInternal.groupName = "webview";
                i2 = pinFileInternal.bytesPinned;
                int i3 = i - i2;
                synchronized (pinnerService) {
                    pinnerService.mPinnedFiles.put(pinFileInternal.fileName, pinFileInternal);
                }
                if (i3 > 0) {
                    pinnerService.pinOptimizedDexDependencies(pinFileInternal, i3, applicationInfo);
                }
                i -= i2;
            }
        }
    }

    public final void updateUserSetting(Context context, String str) {
        ContentResolver contentResolver = context.getContentResolver();
        if (str == null) {
            str = "";
        }
        Settings.Global.putString(contentResolver, "webview_provider", str);
    }
}
