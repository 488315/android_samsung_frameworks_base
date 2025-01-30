package com.samsung.context.sdk.samsunganalytics.internal.policy;

import android.text.TextUtils;
import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class Validation {
    public static Map checkSizeLimit(Map map) {
        HashMap hashMap = new HashMap();
        for (Map.Entry entry : map.entrySet()) {
            String str = (String) entry.getKey();
            String str2 = (String) entry.getValue();
            if (TextUtils.isEmpty(str)) {
                Debug.LogENG("cd key is empty");
            } else {
                if (str.length() > 40) {
                    Debug.LogENG("cd key length over:".concat(str));
                    str = str.substring(0, 40);
                }
                if (str2 != null && str2.length() > 1024) {
                    Debug.LogENG("cd value length over:".concat(str2));
                    str2 = str2.substring(0, 1024);
                }
                hashMap.put(str, str2);
            }
        }
        return hashMap;
    }

    public static String sha256(String str) {
        if (str == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes("UTF-8"));
            return String.format(Locale.US, "%064x", new BigInteger(1, messageDigest.digest()));
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            Debug.LogException(Validation.class, e);
            return null;
        }
    }
}
