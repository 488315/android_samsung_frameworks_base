package com.samsung.context.sdk.samsunganalytics.internal.policy;

import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

public abstract class Validation {
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
