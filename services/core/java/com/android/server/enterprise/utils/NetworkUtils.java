package com.android.server.enterprise.utils;

import android.net.ProxyInfo;
import android.net.Uri;
import android.text.TextUtils;
import com.samsung.android.knox.net.ProxyProperties;
import java.util.Collections;

/* loaded from: classes2.dex */
public abstract class NetworkUtils {
    public static String getExclusionListAsString(String[] strArr) {
        return (strArr == null || strArr.length <= 0) ? "" : String.join(",", strArr);
    }

    public static ProxyInfo convertToProxyInfo(ProxyProperties proxyProperties) {
        if (proxyProperties == null) {
            return null;
        }
        if (!(!TextUtils.isEmpty(proxyProperties.getPacFileUrl()))) {
            return ProxyInfo.buildDirectProxy(proxyProperties.getHostname(), proxyProperties.getPortNumber(), proxyProperties.getExclusionList() == null ? Collections.EMPTY_LIST : proxyProperties.getExclusionList());
        }
        return ProxyInfo.buildPacProxy(Uri.parse(proxyProperties.getPacFileUrl()));
    }

    public static String removeDoubleQuotes(String str) {
        if (str == null) {
            return null;
        }
        int length = str.length();
        if (length <= 1 || str.charAt(0) != '\"') {
            return str;
        }
        int i = length - 1;
        return str.charAt(i) == '\"' ? str.substring(1, i) : str;
    }
}
