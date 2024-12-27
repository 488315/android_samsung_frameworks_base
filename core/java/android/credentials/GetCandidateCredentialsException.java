package android.credentials;

import com.android.internal.util.Preconditions;

public class GetCandidateCredentialsException extends Exception {
    public static final String TYPE_INTERRUPTED =
            "android.credentials.GetCredentialException.TYPE_INTERRUPTED";
    public static final String TYPE_NO_CREDENTIAL =
            "android.credentials.GetCandidateCredentialsException.TYPE_NO_CREDENTIAL";
    public static final String TYPE_UNKNOWN =
            "android.credentials.GetCandidateCredentialsException.TYPE_UNKNOWN";
    public static final String TYPE_USER_CANCELED =
            "android.credentials.GetCredentialException.TYPE_USER_CANCELED";
    private final String mType;

    public String getType() {
        return this.mType;
    }

    public GetCandidateCredentialsException(String type, String message) {
        this(type, message, null);
    }

    public GetCandidateCredentialsException(String type, String message, Throwable cause) {
        super(message, cause);
        this.mType = (String) Preconditions.checkStringNotEmpty(type, "type must not be empty");
    }

    public GetCandidateCredentialsException(String type, Throwable cause) {
        this(type, null, cause);
    }

    public GetCandidateCredentialsException(String type) {
        this(type, null, null);
    }
}
