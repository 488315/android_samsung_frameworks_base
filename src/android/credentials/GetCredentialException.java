package android.credentials;

import com.android.internal.util.Preconditions;

/* loaded from: classes.dex */
public class GetCredentialException extends Exception {
    public static final String TYPE_INTERRUPTED = "android.credentials.GetCredentialException.TYPE_INTERRUPTED";
    public static final String TYPE_NO_CREDENTIAL = "android.credentials.GetCredentialException.TYPE_NO_CREDENTIAL";
    public static final String TYPE_UNKNOWN = "android.credentials.GetCredentialException.TYPE_UNKNOWN";
    public static final String TYPE_USER_CANCELED = "android.credentials.GetCredentialException.TYPE_USER_CANCELED";
    private final String mType;

    public String getType() {
        return this.mType;
    }

    public GetCredentialException(String str, String str2) {
        this(str, str2, null);
    }

    public GetCredentialException(String str, String str2, Throwable th) {
        super(str2, th);
        this.mType = (String) Preconditions.checkStringNotEmpty(str, "type must not be empty");
    }

    public GetCredentialException(String str, Throwable th) {
        this(str, null, th);
    }

    public GetCredentialException(String str) {
        this(str, null, null);
    }
}
