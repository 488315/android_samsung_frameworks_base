package com.android.server.webkit;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.webkit.WebViewProviderInfo;
import java.util.List;

/* loaded from: classes3.dex */
public interface SystemInterface {
  void enablePackageForAllUsers(Context context, String str, boolean z);

  void ensureZygoteStarted();

  long getFactoryPackageVersion(String str);

  int getMultiProcessSetting(Context context);

  PackageInfo getPackageInfoForProvider(WebViewProviderInfo webViewProviderInfo);

  List getPackageInfoForProviderAllUsers(Context context, WebViewProviderInfo webViewProviderInfo);

  String getUserChosenWebViewProvider(Context context);

  WebViewProviderInfo[] getWebViewPackages();

  boolean isMultiProcessDefaultEnabled();

  void killPackageDependents(String str);

  void notifyZygote(boolean z);

  int onWebViewProviderChanged(PackageInfo packageInfo);

  void setMultiProcessSetting(Context context, int i);

  boolean systemIsDebuggable();

  void updateUserSetting(Context context, String str);
}
