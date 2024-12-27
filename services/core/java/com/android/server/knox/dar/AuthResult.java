package com.android.server.knox.dar;

import com.samsung.android.knoxguard.service.utils.Constants;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class AuthResult {
    public int mCallerAuthResult;
    public byte[] mCallingPackage;
    public int mCallingPackageAuthResult;
    public byte[] mCallingPackageSigs;

    public final String toString() {
        StringBuilder sb = new StringBuilder("        Caller Auth Result : ");
        int i = this.mCallerAuthResult;
        String str = Constants.RLC_STATE_NORMAL;
        sb.append(
                i != 0
                        ? i != 1 ? i != 2 ? Integer.toHexString(i) : "Not support" : "Abnormal"
                        : Constants.RLC_STATE_NORMAL);
        sb.append("\n        Calling Package : ");
        Charset charset = StandardCharsets.UTF_8;
        sb.append(new String(this.mCallingPackage, charset));
        sb.append("\n        Calling Package Signatures : ");
        sb.append(new String(this.mCallingPackageSigs, charset));
        sb.append("\n        Calling Package Auth Result : ");
        int i2 = this.mCallingPackageAuthResult;
        if (i2 != 0) {
            str = i2 != 1 ? "Not support" : "Abnormal";
        }
        sb.append(str);
        return sb.toString();
    }
}
