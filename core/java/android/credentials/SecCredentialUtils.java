package android.credentials;

import android.p009os.Bundle;

/* loaded from: classes.dex */
public final class SecCredentialUtils {
    public static final boolean DEBUG_LOG = false;
    public static final String TAG = "CRED_LOG";

    static String printBundle(Bundle bundle, int depth) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        for (int i = 1; i < depth; i++) {
            sb.append("\t");
        }
        sb.append("\t[\n");
        for (String key : bundle.keySet()) {
            for (int i2 = 1; i2 < depth + 1; i2++) {
                sb.append("\t");
            }
            sb.append("key:" + key + ", data=" + bundle.get(key) + "\n");
        }
        sb.append("\t]");
        return sb.toString();
    }
}
