package android.util;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes4.dex */
public class DataUnit {
    private static final /* synthetic */ DataUnit[] $VALUES = $values();
    public static final DataUnit GIBIBYTES;
    public static final DataUnit GIGABYTES;
    public static final DataUnit KIBIBYTES;
    public static final DataUnit KILOBYTES;
    public static final DataUnit MEBIBYTES;
    public static final DataUnit MEGABYTES;
    public static final DataUnit TEBIBYTES;
    public static final DataUnit TERABYTES;

    private static /* synthetic */ DataUnit[] $values() {
        return new DataUnit[]{KILOBYTES, MEGABYTES, GIGABYTES, TERABYTES, KIBIBYTES, MEBIBYTES, GIBIBYTES, TEBIBYTES};
    }

    public static DataUnit valueOf(String str) {
        return (DataUnit) Enum.valueOf(DataUnit.class, str);
    }

    public static DataUnit[] values() {
        return (DataUnit[]) $VALUES.clone();
    }

    /* renamed from: android.util.DataUnit$1, reason: invalid class name */
    enum AnonymousClass1 extends DataUnit {
        @Override // android.util.DataUnit
        public long toBytes(long j) {
            return j * 1000;
        }

        private AnonymousClass1(String str, int i) {
            super(str, i);
        }
    }

    private DataUnit(String str, int i) {
    }

    /* renamed from: android.util.DataUnit$2, reason: invalid class name */
    enum AnonymousClass2 extends DataUnit {
        @Override // android.util.DataUnit
        public long toBytes(long j) {
            return j * 1000000;
        }

        private AnonymousClass2(String str, int i) {
            super(str, i);
        }
    }

    static {
        KILOBYTES = new AnonymousClass1("KILOBYTES", 0);
        MEGABYTES = new AnonymousClass2("MEGABYTES", 1);
        GIGABYTES = new AnonymousClass3("GIGABYTES", 2);
        TERABYTES = new AnonymousClass4("TERABYTES", 3);
        KIBIBYTES = new AnonymousClass5("KIBIBYTES", 4);
        MEBIBYTES = new AnonymousClass6("MEBIBYTES", 5);
        GIBIBYTES = new AnonymousClass7("GIBIBYTES", 6);
        TEBIBYTES = new AnonymousClass8("TEBIBYTES", 7);
    }

    /* renamed from: android.util.DataUnit$3, reason: invalid class name */
    enum AnonymousClass3 extends DataUnit {
        @Override // android.util.DataUnit
        public long toBytes(long j) {
            return j * 1000000000;
        }

        private AnonymousClass3(String str, int i) {
            super(str, i);
        }
    }

    /* renamed from: android.util.DataUnit$4, reason: invalid class name */
    enum AnonymousClass4 extends DataUnit {
        @Override // android.util.DataUnit
        public long toBytes(long j) {
            return j * 1000000000000L;
        }

        private AnonymousClass4(String str, int i) {
            super(str, i);
        }
    }

    /* renamed from: android.util.DataUnit$5, reason: invalid class name */
    enum AnonymousClass5 extends DataUnit {
        @Override // android.util.DataUnit
        public long toBytes(long j) {
            return j * 1024;
        }

        private AnonymousClass5(String str, int i) {
            super(str, i);
        }
    }

    /* renamed from: android.util.DataUnit$6, reason: invalid class name */
    enum AnonymousClass6 extends DataUnit {
        @Override // android.util.DataUnit
        public long toBytes(long j) {
            return j * 1048576;
        }

        private AnonymousClass6(String str, int i) {
            super(str, i);
        }
    }

    /* renamed from: android.util.DataUnit$7, reason: invalid class name */
    enum AnonymousClass7 extends DataUnit {
        @Override // android.util.DataUnit
        public long toBytes(long j) {
            return j * 1073741824;
        }

        private AnonymousClass7(String str, int i) {
            super(str, i);
        }
    }

    /* renamed from: android.util.DataUnit$8, reason: invalid class name */
    enum AnonymousClass8 extends DataUnit {
        @Override // android.util.DataUnit
        public long toBytes(long j) {
            return j * 1099511627776L;
        }

        private AnonymousClass8(String str, int i) {
            super(str, i);
        }
    }

    public long toBytes(long j) {
        throw new AbstractMethodError();
    }
}
