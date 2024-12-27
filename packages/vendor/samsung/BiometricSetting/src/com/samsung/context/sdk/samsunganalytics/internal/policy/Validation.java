package com.samsung.context.sdk.samsunganalytics.internal.policy;

import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
/* loaded from: classes.dex */
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
