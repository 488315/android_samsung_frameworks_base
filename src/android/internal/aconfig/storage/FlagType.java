package android.internal.aconfig.storage;

/* loaded from: classes2.dex */
public enum FlagType {
    ReadWriteBoolean(0),
    ReadOnlyBoolean(1),
    FixedReadOnlyBoolean(2);

    public final int type;

    FlagType(int i) {
        this.type = i;
    }

    public static FlagType fromInt(int i) {
        if (i == 0) {
            return ReadWriteBoolean;
        }
        if (i == 1) {
            return ReadOnlyBoolean;
        }
        if (i != 2) {
            return null;
        }
        return FixedReadOnlyBoolean;
    }
}
