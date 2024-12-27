package com.android.server.webkit;

import android.content.pm.PackageInfo;
import android.webkit.WebViewProviderInfo;
import android.webkit.WebViewProviderResponse;

import java.io.PrintWriter;

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
