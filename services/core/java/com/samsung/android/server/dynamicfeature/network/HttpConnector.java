package com.samsung.android.server.dynamicfeature.network;

import android.content.Context;
import android.net.shared.InitialConfiguration$$ExternalSyntheticOutline0;
import android.os.SystemProperties;
import android.telephony.TelephonyManager;

import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;

import com.samsung.android.server.dynamicfeature.InfoBoard;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

public final class HttpConnector {
    public static int isStopped = 0;
    public String mBinaryType;
    public Context mContext;
    public ExecutorService mExecutorService;
    public ArrayList mFeatures;
    public int mLastResultCode;
    public String mModelName;
    public String mOneUiVersion;
    public int mSdkVersion;

    public static HttpURLConnection getConnection(String str) {
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Content-type", "application/json; utf-8");
        httpURLConnection.setRequestProperty("Accept", "application/json");
        httpURLConnection.setDoOutput(true);
        return httpURLConnection;
    }

    public int getLastReultCode() {
        return this.mLastResultCode;
    }

    public final String getParams(boolean z) {
        String str;
        String str2;
        TelephonyManager telephonyManager =
                (TelephonyManager) this.mContext.getSystemService("phone");
        if (telephonyManager.getNetworkOperator().length() > 4) {
            str2 = telephonyManager.getNetworkOperator().substring(0, 3);
            str = telephonyManager.getNetworkOperator().substring(3);
        } else {
            str = "";
            str2 = str;
        }
        String str3 = SystemProperties.get("ro.csc.sales_code", "");
        InfoBoard.setParams(
                str2, str, str3, this.mOneUiVersion, this.mBinaryType, this.mSdkVersion);
        StringBuilder m =
                InitialConfiguration$$ExternalSyntheticOutline0.m(
                        "?mcc=", str2, "&mnc=", str, "&modelName=");
        DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                m, this.mModelName, "&csc=", str3, "&sdkVersion=");
        m.append(this.mSdkVersion);
        m.append("&oneUiVersion=");
        m.append(this.mOneUiVersion);
        m.append("&binaryType=");
        m.append(this.mBinaryType);
        String sb = m.toString();
        if (!z) {
            return sb;
        }
        StringBuilder m2 = Preconditions$$ExternalSyntheticOutline0.m(sb, "&virtualId=");
        m2.append(InfoBoard.basicInfo.vid);
        return m2.toString();
    }
}
