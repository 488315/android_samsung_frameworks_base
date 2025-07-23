package com.samsung.context.sdk.samsunganalytics.internal.util;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class AuthUtil {
    public static String sha256(String str) {
        if (str == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes(StandardCharsets.UTF_8));
            return String.format(Locale.US, "%064x", new BigInteger(1, messageDigest.digest()));
        } catch (NoSuchAlgorithmException e) {
            Debug.logwingE("failed to hash : " + e.getMessage());
            return null;
        }
    }
}
