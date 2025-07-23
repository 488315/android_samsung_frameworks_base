package android.internal.aconfig.storage;

/* loaded from: classes2.dex */
public enum FileType {
    PACKAGE_MAP(0),
    FLAG_MAP(1),
    FLAG_VAL(2),
    FLAG_INFO(3);

    public final int type;

    FileType(int i) {
        this.type = i;
    }

    public static FileType fromInt(int i) {
        if (i == 0) {
            return PACKAGE_MAP;
        }
        if (i == 1) {
            return FLAG_MAP;
        }
        if (i == 2) {
            return FLAG_VAL;
        }
        if (i != 3) {
            return null;
        }
        return FLAG_INFO;
    }

    @Override // java.lang.Enum
    public String toString() {
        int i = this.type;
        if (i == 0) {
            return "PACKAGE_MAP";
        }
        if (i == 1) {
            return "FLAG_MAP";
        }
        if (i == 2) {
            return "FLAG_VAL";
        }
        if (i == 3) {
            return "FLAG_INFO";
        }
        return "unrecognized type";
    }
}
