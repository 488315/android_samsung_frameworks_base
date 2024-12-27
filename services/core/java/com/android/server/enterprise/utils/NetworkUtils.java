package com.android.server.enterprise.utils;

import android.net.ProxyInfo;
import android.net.Uri;
import android.text.TextUtils;

import com.samsung.android.knox.net.ProxyProperties;

import java.util.Collections;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class NetworkUtils {
    public static ProxyInfo convertToProxyInfo(ProxyProperties proxyProperties) {
        if (proxyProperties == null) {
            return null;
        }
        if (!TextUtils.isEmpty(proxyProperties.getPacFileUrl())) {
            return ProxyInfo.buildPacProxy(Uri.parse(proxyProperties.getPacFileUrl()));
        }
        return ProxyInfo.buildDirectProxy(
                proxyProperties.getHostname(),
                proxyProperties.getPortNumber(),
                proxyProperties.getExclusionList() == null
                        ? Collections.EMPTY_LIST
                        : proxyProperties.getExclusionList());
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
