package com.android.server.asks;

import android.content.pm.Signature;
import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.util.Slog;

import java.security.MessageDigest;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class AuthorizedToken {
    public String createData;
    public String expireDate;
    public String installAuthority;
    public String limit;
    public String limitCondition;
    public String packageName;
    public String policy;
    public String signature;

    public static String getSigHash(Signature signature) {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(signature.toByteArray());
        byte[] digest = messageDigest.digest();
        StringBuilder sb = new StringBuilder();
        if (digest == null) {
            return "null";
        }
        for (byte b : digest) {
            int i = (b >>> 4) & 15;
            int i2 = 0;
            while (true) {
                sb.append((char) ((i < 0 || i > 9) ? i + 87 : i + 48));
                i = b & 15;
                int i3 = i2 + 1;
                if (i2 >= 1) {
                    break;
                }
                i2 = i3;
            }
        }
        return sb.toString();
    }

    public final boolean checkExpiredDate(String str) {
        try {
            if (this.expireDate == null
                    || Integer.parseInt(this.createData) > Integer.parseInt(this.expireDate)) {
                return false;
            }
            return Integer.parseInt(str) <= Integer.parseInt(this.expireDate);
        } catch (NumberFormatException e) {
            Slog.e(
                    "PackageInformation_Token",
                    "[Token] checkExpiredDate error : " + e.getMessage());
            return false;
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("AuthorizedToken{packageName='");
        sb.append(this.packageName);
        sb.append("', signature='");
        sb.append(this.signature);
        sb.append("', policy='");
        sb.append(this.policy);
        sb.append("', installAuthority='");
        sb.append(this.installAuthority);
        sb.append("', limit=");
        sb.append(this.limit);
        sb.append(", limitCondition='");
        sb.append(this.limitCondition);
        sb.append("', createData=");
        sb.append(this.createData);
        sb.append(", expireDate='");
        return AudioOffloadInfo$$ExternalSyntheticOutline0.m(sb, this.expireDate, "'}");
    }
}
