package android.credentials;

import com.android.internal.util.Preconditions;

/* loaded from: classes.dex */
public class ListEnabledProvidersException extends Exception {
    private final String mType;

    public String getType() {
        return this.mType;
    }

    public ListEnabledProvidersException(String str, String str2) {
        this(str, str2, null);
    }

    public ListEnabledProvidersException(String str, String str2, Throwable th) {
        super(str2, th);
        this.mType = (String) Preconditions.checkStringNotEmpty(str, "type must not be empty");
    }

    public ListEnabledProvidersException(String str, Throwable th) {
        this(str, null, th);
    }

    public ListEnabledProvidersException(String str) {
        this(str, null, null);
    }
}
