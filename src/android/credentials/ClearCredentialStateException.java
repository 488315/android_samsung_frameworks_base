package android.credentials;

import com.android.internal.util.Preconditions;

/* loaded from: classes.dex */
public class ClearCredentialStateException extends Exception {
    public static final String TYPE_UNKNOWN = "android.credentials.ClearCredentialStateException.TYPE_UNKNOWN";
    private final String mType;

    public String getType() {
        return this.mType;
    }

    public ClearCredentialStateException(String str, String str2) {
        this(str, str2, null);
    }

    public ClearCredentialStateException(String str, String str2, Throwable th) {
        super(str2, th);
        this.mType = (String) Preconditions.checkStringNotEmpty(str, "type must not be empty");
    }

    public ClearCredentialStateException(String str, Throwable th) {
        this(str, null, th);
    }

    public ClearCredentialStateException(String str) {
        this(str, null, null);
    }
}
