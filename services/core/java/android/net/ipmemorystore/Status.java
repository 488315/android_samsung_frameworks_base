package android.net.ipmemorystore;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public class Status {
    public static final int ERROR_DATABASE_CANNOT_BE_OPENED = -3;
    public static final int ERROR_GENERIC = -1;
    public static final int ERROR_ILLEGAL_ARGUMENT = -2;
    public static final int ERROR_STORAGE = -4;
    public static final int ERROR_UNKNOWN = -5;
    public static final int SUCCESS = 0;
    public final int resultCode;

    public Status(int i) {
        this.resultCode = i;
    }

    public Status(StatusParcelable statusParcelable) {
        this(statusParcelable.resultCode);
    }

    public boolean isSuccess() {
        return this.resultCode == 0;
    }

    public StatusParcelable toParcelable() {
        StatusParcelable statusParcelable = new StatusParcelable();
        statusParcelable.resultCode = this.resultCode;
        return statusParcelable;
    }

    public String toString() {
        int i = this.resultCode;
        return i != -4
                ? i != -3
                        ? i != -2
                                ? i != -1
                                        ? i != 0 ? "Unknown value ?!" : "SUCCESS"
                                        : "GENERIC ERROR"
                                : "ILLEGAL ARGUMENT"
                        : "DATABASE CANNOT BE OPENED"
                : "DATABASE STORAGE ERROR";
    }
}
