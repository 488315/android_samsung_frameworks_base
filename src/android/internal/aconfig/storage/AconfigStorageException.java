package android.internal.aconfig.storage;

/* loaded from: classes2.dex */
public class AconfigStorageException extends RuntimeException {
    public static final int ERROR_CANNOT_READ_STORAGE_FILE = 4;
    public static final int ERROR_CONTAINER_NOT_FOUND = 3;
    public static final int ERROR_FILE_FINGERPRINT_MISMATCH = 5;
    public static final int ERROR_GENERIC = 0;
    public static final int ERROR_PACKAGE_NOT_FOUND = 2;
    public static final int ERROR_STORAGE_SYSTEM_NOT_FOUND = 1;
    private final int mErrorCode;

    public AconfigStorageException(String str) {
        super(str);
        this.mErrorCode = 0;
    }

    public AconfigStorageException(String str, Throwable th) {
        super(str, th);
        this.mErrorCode = 0;
    }

    public AconfigStorageException(int i, String str) {
        super(str);
        this.mErrorCode = i;
    }

    public AconfigStorageException(int i, String str, Throwable th) {
        super(str, th);
        this.mErrorCode = i;
    }

    public int getErrorCode() {
        return this.mErrorCode;
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        return errorString() + ": " + super.getMessage();
    }

    private String errorString() {
        int i = this.mErrorCode;
        if (i == 0) {
            return "ERROR_GENERIC";
        }
        if (i == 1) {
            return "ERROR_STORAGE_SYSTEM_NOT_FOUND";
        }
        if (i == 2) {
            return "ERROR_PACKAGE_NOT_FOUND";
        }
        if (i == 3) {
            return "ERROR_CONTAINER_NOT_FOUND";
        }
        if (i == 4) {
            return "ERROR_CANNOT_READ_STORAGE_FILE";
        }
        if (i == 5) {
            return "ERROR_FILE_FINGERPRINT_MISMATCH";
        }
        return "<Unknown error code " + this.mErrorCode + ">";
    }
}
