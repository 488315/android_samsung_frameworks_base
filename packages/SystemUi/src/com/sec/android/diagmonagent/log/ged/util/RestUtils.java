package com.sec.android.diagmonagent.log.ged.util;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.ArrayLinkedVariables$$ExternalSyntheticOutline0;
import com.android.settingslib.datetime.ZoneGetter$ZoneGetterData$$ExternalSyntheticOutline0;
import com.android.systemui.appops.AppOpItem$$ExternalSyntheticOutline0;
import com.sec.android.diagmonagent.common.NativeHelper;
import com.sec.android.diagmonagent.common.logger.AppLog;
import com.sec.android.diagmonagent.log.ged.servreinterface.model.response.Response;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class RestUtils {
    public static final String DEVICE_KEY = String.copyValueOf(NativeHelper.getRandomId());

    public static String calculateKey() {
        StringBuilder sb = new StringBuilder("");
        String str = DEVICE_KEY;
        return ZoneGetter$ZoneGetterData$$ExternalSyntheticOutline0.m89m(str, 7, ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(ZoneGetter$ZoneGetterData$$ExternalSyntheticOutline0.m89m(str, 11, ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(ZoneGetter$ZoneGetterData$$ExternalSyntheticOutline0.m89m(str, 43, ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(ZoneGetter$ZoneGetterData$$ExternalSyntheticOutline0.m89m(str, 17, ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(ZoneGetter$ZoneGetterData$$ExternalSyntheticOutline0.m89m(str, 5, ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(ZoneGetter$ZoneGetterData$$ExternalSyntheticOutline0.m89m(str, 8, ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(ZoneGetter$ZoneGetterData$$ExternalSyntheticOutline0.m89m(str, 12, ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(ZoneGetter$ZoneGetterData$$ExternalSyntheticOutline0.m89m(str, 18, ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(ZoneGetter$ZoneGetterData$$ExternalSyntheticOutline0.m89m(str, 42, ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(ZoneGetter$ZoneGetterData$$ExternalSyntheticOutline0.m89m(str, 38, ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(ZoneGetter$ZoneGetterData$$ExternalSyntheticOutline0.m89m(str, 37, ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(ZoneGetter$ZoneGetterData$$ExternalSyntheticOutline0.m89m(str, 32, ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(ZoneGetter$ZoneGetterData$$ExternalSyntheticOutline0.m89m(str, 6, ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(ZoneGetter$ZoneGetterData$$ExternalSyntheticOutline0.m89m(str, 24, ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(ZoneGetter$ZoneGetterData$$ExternalSyntheticOutline0.m89m(str, 28, ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(ZoneGetter$ZoneGetterData$$ExternalSyntheticOutline0.m89m(str, 35, ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(ZoneGetter$ZoneGetterData$$ExternalSyntheticOutline0.m89m(str, 41, ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(ZoneGetter$ZoneGetterData$$ExternalSyntheticOutline0.m89m(str, 1, ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(ZoneGetter$ZoneGetterData$$ExternalSyntheticOutline0.m89m(str, 2, ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(ZoneGetter$ZoneGetterData$$ExternalSyntheticOutline0.m89m(str, 25, ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(ZoneGetter$ZoneGetterData$$ExternalSyntheticOutline0.m89m(str, 30, ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(ZoneGetter$ZoneGetterData$$ExternalSyntheticOutline0.m89m(str, 34, ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(ZoneGetter$ZoneGetterData$$ExternalSyntheticOutline0.m89m(str, 36, ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(ZoneGetter$ZoneGetterData$$ExternalSyntheticOutline0.m89m(str, 26, ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(ZoneGetter$ZoneGetterData$$ExternalSyntheticOutline0.m89m(str, 31, ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(ZoneGetter$ZoneGetterData$$ExternalSyntheticOutline0.m89m(str, 19, ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(ZoneGetter$ZoneGetterData$$ExternalSyntheticOutline0.m89m(str, 27, ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(ZoneGetter$ZoneGetterData$$ExternalSyntheticOutline0.m89m(str, 0, ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(ZoneGetter$ZoneGetterData$$ExternalSyntheticOutline0.m89m(str, 33, ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(ZoneGetter$ZoneGetterData$$ExternalSyntheticOutline0.m89m(str, 13, ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(ZoneGetter$ZoneGetterData$$ExternalSyntheticOutline0.m89m(str, 14, ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(ZoneGetter$ZoneGetterData$$ExternalSyntheticOutline0.m89m(str, 4, sb)))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))));
    }

    public static String getIdentifier(Context context) {
        String str = "";
        String diagmonPreference = PreferenceUtils.getDiagmonPreference(context, "REST_IDENTIFIER", "");
        if (TextUtils.isEmpty(diagmonPreference)) {
            SecureRandom secureRandom = new SecureRandom();
            byte[] bArr = new byte[16];
            StringBuilder sb = new StringBuilder(32);
            for (int i = 0; i < 32; i++) {
                secureRandom.nextBytes(bArr);
                try {
                    sb.append("0123456789abcdefghijklmjopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".charAt((int) (Math.abs(new BigInteger(bArr).longValue()) % 62)));
                } catch (Exception e) {
                    AppLog.m270e("failed to generate RandomDeviceId : " + e.getMessage());
                }
            }
            str = sb.toString();
            AppLog.m269d("Generated randomId : " + str);
            context.getSharedPreferences("DIAGMON_PREFERENCE", 0).edit().remove("REST_IDENTIFIER").apply();
            PreferenceUtils.setDiagmonPreference(context, "REST_IDENTIFIER", str);
            diagmonPreference = str;
        }
        AppLog.m269d("Stored randomId : " + diagmonPreference);
        return diagmonPreference;
    }

    public static byte[] getSHADigest(String str) {
        try {
            byte[] bytes = str.getBytes(Charset.defaultCharset());
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.reset();
            return messageDigest.digest(bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x0057, code lost:
    
        if (r5.body.contains("4412") != false) goto L17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:6:0x0036, code lost:
    
        if (r5.code == 401) goto L17;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean isTokenNeedToBeUpdated(Context context, Response response) {
        boolean z;
        if (!PreferenceUtils.getDiagmonPreference(context, "JWT_TOKEN", "").isEmpty()) {
            AppLog.m269d("Check token expired : " + response.code + response.body);
            String str = response.body;
            if (str == null) {
                AppLog.m272w("Response body is null");
            } else {
                if (response.code == 401) {
                    if (!str.contains("4410")) {
                        if (!response.body.contains("4411")) {
                        }
                    }
                    z = true;
                }
                z = false;
            }
            if (!z) {
                return false;
            }
        }
        AppLog.m269d("Token need to be updated.");
        return true;
    }

    public static String makeAuth(Context context, String str, String str2, String str3, String str4) {
        StringBuilder sb = new StringBuilder();
        String str5 = "s7gna8vjt1:com.sec.android.diagmonagent:" + getIdentifier(context) + ":" + calculateKey();
        if (!str3.isEmpty()) {
            str = AbstractResolvableFuture$$ExternalSyntheticOutline0.m15m(str, ":", str3);
        }
        AppLog.m269d("part1 = " + str5);
        AppLog.m269d("part2 = " + str);
        String str6 = new String(Base64.encode(getSHADigest(str5), 2));
        String str7 = new String(Base64.encode(getSHADigest(str), 2));
        AppOpItem$$ExternalSyntheticOutline0.m97m(sb, str6, ":", str2, ":");
        sb.append(str4);
        sb.append(":");
        sb.append(str7);
        AppLog.m269d("signature = " + ((Object) sb));
        String str8 = new String(Base64.encode(getSHADigest(sb.toString()), 2));
        AppLog.m269d("hash = ".concat(str8));
        return "Bearer=\"" + str4 + "\",auth_identifier=\"ALLNEWDIAGMON-AUTH\",server_id=\"s7gna8vjt1\",service_id=\"" + str2 + "\",identifier=\"" + getIdentifier(context) + "\",signature=\"" + str8 + "\"";
    }

    public static String makeAuth(Context context, String str) {
        StringBuilder sb = new StringBuilder();
        String str2 = "s7gna8vjt1:com.sec.android.diagmonagent:" + getIdentifier(context) + ":" + calculateKey();
        AppLog.m269d("part1 = " + str2);
        AppLog.m269d("part2 = " + str);
        String str3 = new String(Base64.encode(getSHADigest(str2), 2));
        String str4 = new String(Base64.encode(getSHADigest(str), 2));
        sb.append(str3);
        sb.append(":x6g1q14r77:");
        sb.append(str4);
        AppLog.m269d("signature = " + ((Object) sb));
        String str5 = new String(Base64.encode(getSHADigest(sb.toString()), 2));
        AppLog.m269d("hash = ".concat(str5));
        return "auth_identifier=\"ALLNEWDIAGMON-AUTH\",server_id=\"s7gna8vjt1\",service_id=\"x6g1q14r77\",identifier=\"" + getIdentifier(context) + "\",signature=\"" + str5 + "\"";
    }
}
