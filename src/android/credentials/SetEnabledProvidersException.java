package android.credentials;

import com.android.internal.util.Preconditions;

/* loaded from: classes.dex */
public class SetEnabledProvidersException extends Exception {
    private final String mType;

    public String getType() {
        return this.mType;
    }

    public SetEnabledProvidersException(String str, String str2) {
        this(str, str2, null);
    }

    public SetEnabledProvidersException(String str, String str2, Throwable th) {
        super(str2, th);
        this.mType = (String) Preconditions.checkStringNotEmpty(str, "type must not be empty");
    }

    public SetEnabledProvidersException(String str, Throwable th) {
        this(str, null, th);
    }

    public SetEnabledProvidersException(String str) {
        this(str, null, null);
    }
}
