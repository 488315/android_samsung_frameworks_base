package com.android.server.devicepolicy;

import android.content.Context;
import android.content.pm.VerifierDeviceIdentity;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.security.identity.Util;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.android.internal.util.Preconditions;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import java.nio.ByteBuffer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class EnterpriseSpecificIdCalculator {
    public final String mImei;
    public final String mMacAddress;
    public final String mMeid;
    public final String mSerialNumber;

    public EnterpriseSpecificIdCalculator(Context context) {
        String str;
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(TelephonyManager.class);
        Preconditions.checkState(telephonyManager != null, "Unable to access telephony service");
        String str2 = null;
        try {
            str = telephonyManager.getImei(0);
        } catch (UnsupportedOperationException unused) {
            str = null;
        }
        this.mImei = str;
        try {
            str2 = telephonyManager.getMeid(0);
        } catch (UnsupportedOperationException unused2) {
        }
        this.mMeid = str2;
        this.mSerialNumber = Build.getSerial();
        WifiManager wifiManager = (WifiManager) context.getSystemService(WifiManager.class);
        Preconditions.checkState(wifiManager != null, "Unable to access WiFi service");
        String[] factoryMacAddresses = wifiManager.getFactoryMacAddresses();
        if (factoryMacAddresses == null || factoryMacAddresses.length == 0) {
            this.mMacAddress = "";
        } else {
            this.mMacAddress = factoryMacAddresses[0];
        }
    }

    public EnterpriseSpecificIdCalculator(String str, String str2, String str3, String str4) {
        this.mImei = str;
        this.mMeid = str2;
        this.mSerialNumber = str3;
        this.mMacAddress = str4;
    }

    public static String getPaddedTruncatedString(int i, String str) {
        return String.format(BinaryTransparencyService$$ExternalSyntheticOutline0.m(i, "%", "s"), str).substring(0, i);
    }

    public final String calculateEnterpriseId(String str, String str2) {
        boolean z = true;
        Preconditions.checkArgument(!TextUtils.isEmpty(str), "owner package must be specified.");
        if (str2 != null && str2.isEmpty()) {
            z = false;
        }
        Preconditions.checkArgument(z, "enterprise ID must either be null or non-empty.");
        if (str2 == null) {
            str2 = "";
        }
        String str3 = this.mSerialNumber;
        if (str3 == null) {
            str3 = "";
        }
        byte[] bytes = getPaddedTruncatedString(16, str3).getBytes();
        String str4 = this.mImei;
        if (str4 == null) {
            str4 = "";
        }
        byte[] bytes2 = getPaddedTruncatedString(16, str4).getBytes();
        String str5 = this.mMeid;
        byte[] bytes3 = getPaddedTruncatedString(16, str5 != null ? str5 : "").getBytes();
        byte[] bytes4 = this.mMacAddress.getBytes();
        ByteBuffer allocate = ByteBuffer.allocate(bytes.length + bytes2.length + bytes3.length + bytes4.length);
        allocate.put(bytes);
        allocate.put(bytes2);
        allocate.put(bytes3);
        allocate.put(bytes4);
        byte[] bytes5 = getPaddedTruncatedString(64, str).getBytes();
        byte[] bytes6 = getPaddedTruncatedString(64, str2).getBytes();
        ByteBuffer allocate2 = ByteBuffer.allocate(bytes5.length + bytes6.length);
        allocate2.put(bytes5);
        allocate2.put(bytes6);
        ByteBuffer wrap = ByteBuffer.wrap(Util.computeHkdf("HMACSHA256", allocate.array(), (byte[]) null, allocate2.array(), 16));
        return new VerifierDeviceIdentity(wrap.getLong()).toString() + new VerifierDeviceIdentity(wrap.getLong()).toString();
    }
}
