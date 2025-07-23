package android.credentials;

import android.os.Bundle;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;

/* loaded from: classes.dex */
public final class SecCredentialUtils {
    public static final boolean DEBUG_LOG = false;
    public static final String TAG = "CRED_LOG";

    static String printBundle(Bundle bundle, int i) {
        StringBuilder sb = new StringBuilder(ShaderAssembler.NEWLINE);
        for (int i2 = 1; i2 < i; i2++) {
            sb.append("\t");
        }
        sb.append("\t[\n");
        for (String str : bundle.keySet()) {
            for (int i3 = 1; i3 < i + 1; i3++) {
                sb.append("\t");
            }
            sb.append("key:" + str + ", data=" + bundle.get(str) + ShaderAssembler.NEWLINE);
        }
        sb.append("\t]");
        return sb.toString();
    }
}
