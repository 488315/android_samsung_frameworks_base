package com.android.server.signedconfig;

import android.os.Build;
import android.util.Slog;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class SignatureVerifier {
    public final PublicKey mDebugKey;
    public final SignedConfigEvent mEvent;
    public final PublicKey mProdKey;

    public SignatureVerifier(SignedConfigEvent signedConfigEvent) {
        this.mEvent = signedConfigEvent;
        this.mDebugKey =
                Build.IS_DEBUGGABLE
                        ? createKey(
                                "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEmJKs4lSn+XRhMQmMid+Zbhbu13YrU1haIhVC5296InRu1x7A8PV1ejQyisBODGgRY6pqkAHRncBCYcgg5wIIJg==")
                        : null;
        this.mProdKey =
                createKey(
                        "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAE+lky6wKyGL6lE1VrD0YTMHwb0Xwc+tzC8MvnrzVxodvTpVY/jV7V+Zktcx+pry43XPABFRXtbhTo+qykhyBA1g==");
    }

    public static PublicKey createKey(String str) {
        try {
            try {
                return KeyFactory.getInstance("EC")
                        .generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(str)));
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                Slog.e("SignedConfig", "Failed to construct public key", e);
                return null;
            }
        } catch (IllegalArgumentException e2) {
            Slog.e("SignedConfig", "Failed to base64 decode public key", e2);
            return null;
        }
    }

    public final boolean verifySignature(String str, String str2) {
        SignedConfigEvent signedConfigEvent = this.mEvent;
        try {
            byte[] decode = Base64.getDecoder().decode(str2);
            byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
            if (Build.IS_DEBUGGABLE) {
                PublicKey publicKey = this.mDebugKey;
                if (publicKey != null) {
                    Signature signature = Signature.getInstance("SHA256withECDSA");
                    signature.initVerify(publicKey);
                    signature.update(bytes);
                    if (signature.verify(decode)) {
                        Slog.i("SignedConfig", "Verified config using debug key");
                        signedConfigEvent.verifiedWith = 1;
                        return true;
                    }
                } else {
                    Slog.w("SignedConfig", "Debuggable build, but have no debug key");
                }
            }
            PublicKey publicKey2 = this.mProdKey;
            if (publicKey2 == null) {
                Slog.e("SignedConfig", "No prod key; construction failed?");
                signedConfigEvent.status = 9;
                return false;
            }
            Signature signature2 = Signature.getInstance("SHA256withECDSA");
            signature2.initVerify(publicKey2);
            signature2.update(bytes);
            if (!signature2.verify(decode)) {
                signedConfigEvent.status = 7;
                return false;
            }
            Slog.i("SignedConfig", "Verified config using production key");
            signedConfigEvent.verifiedWith = 2;
            return true;
        } catch (IllegalArgumentException unused) {
            signedConfigEvent.status = 3;
            Slog.e("SignedConfig", "Failed to base64 decode signature");
            return false;
        }
    }
}
