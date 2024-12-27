package com.android.server.webkit;

import android.content.pm.PackageInfo;
import android.webkit.WebViewProviderInfo;
import android.webkit.WebViewProviderResponse;

import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public interface WebViewUpdateServiceInterface {
    String changeProviderAndSetting(String str);

    void dumpState(PrintWriter printWriter);

    void enableMultiProcess(boolean z);

    PackageInfo getCurrentWebViewPackage();

    WebViewProviderInfo getDefaultWebViewPackage();

    WebViewProviderInfo[] getValidWebViewPackages();

    WebViewProviderInfo[] getWebViewPackages();

    void handleNewUser(int i);

    void handleUserRemoved();

    boolean isMultiProcessEnabled();

    void notifyRelroCreationCompleted();

    void packageStateChanged(String str);

    void prepareWebViewInSystemServer();

    WebViewProviderResponse waitForAndGetProvider();
}
