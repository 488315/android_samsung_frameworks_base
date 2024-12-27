package android.util;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes4.dex */
public class DataUnit {
    public static final DataUnit KILOBYTES = new AnonymousClass1("KILOBYTES", 0);
    public static final DataUnit MEGABYTES = new AnonymousClass2("MEGABYTES", 1);
    public static final DataUnit GIGABYTES = new AnonymousClass3("GIGABYTES", 2);
    public static final DataUnit TERABYTES = new AnonymousClass4("TERABYTES", 3);
    public static final DataUnit KIBIBYTES = new AnonymousClass5("KIBIBYTES", 4);
    public static final DataUnit MEBIBYTES = new AnonymousClass6("MEBIBYTES", 5);
    public static final DataUnit GIBIBYTES = new AnonymousClass7("GIBIBYTES", 6);
    public static final DataUnit TEBIBYTES = new AnonymousClass8("TEBIBYTES", 7);
    private static final /* synthetic */ DataUnit[] $VALUES = $values();

    private static /* synthetic */ DataUnit[] $values() {
        return new DataUnit[]{KILOBYTES, MEGABYTES, GIGABYTES, TERABYTES, KIBIBYTES, MEBIBYTES, GIBIBYTES, TEBIBYTES};
    }

    public static DataUnit valueOf(String name) {
        return (DataUnit) Enum.valueOf(DataUnit.class, name);
    }

    public static DataUnit[] values() {
        return (DataUnit[]) $VALUES.clone();
    }

    /* renamed from: android.util.DataUnit$1, reason: invalid class name */
    enum AnonymousClass1 extends DataUnit {
        private AnonymousClass1(String str, int i) {
            super(str, i);
        }

        @Override // android.util.DataUnit
        public long toBytes(long v) {
            return 1000 * v;
        }
    }

    private DataUnit(String str, int i) {
    }

    /* renamed from: android.util.DataUnit$2, reason: invalid class name */
    enum AnonymousClass2 extends DataUnit {
        private AnonymousClass2(String str, int i) {
            super(str, i);
        }

        @Override // android.util.DataUnit
        public long toBytes(long v) {
            return 1000000 * v;
        }
    }

    /* renamed from: android.util.DataUnit$3, reason: invalid class name */
    enum AnonymousClass3 extends DataUnit {
        private AnonymousClass3(String str, int i) {
            super(str, i);
        }

        @Override // android.util.DataUnit
        public long toBytes(long v) {
            return 1000000000 * v;
        }
    }

    /* renamed from: android.util.DataUnit$4, reason: invalid class name */
    enum AnonymousClass4 extends DataUnit {
        private AnonymousClass4(String str, int i) {
            super(str, i);
        }

        @Override // android.util.DataUnit
        public long toBytes(long v) {
            return 1000000000000L * v;
        }
    }

    /* renamed from: android.util.DataUnit$5, reason: invalid class name */
    enum AnonymousClass5 extends DataUnit {
        private AnonymousClass5(String str, int i) {
            super(str, i);
        }

        @Override // android.util.DataUnit
        public long toBytes(long v) {
            return 1024 * v;
        }
    }

    /* renamed from: android.util.DataUnit$6, reason: invalid class name */
    enum AnonymousClass6 extends DataUnit {
        private AnonymousClass6(String str, int i) {
            super(str, i);
        }

        @Override // android.util.DataUnit
        public long toBytes(long v) {
            return 1048576 * v;
        }
    }

    /* renamed from: android.util.DataUnit$7, reason: invalid class name */
    enum AnonymousClass7 extends DataUnit {
        private AnonymousClass7(String str, int i) {
            super(str, i);
        }

        @Override // android.util.DataUnit
        public long toBytes(long v) {
            return 1073741824 * v;
        }
    }

    /* renamed from: android.util.DataUnit$8, reason: invalid class name */
    enum AnonymousClass8 extends DataUnit {
        private AnonymousClass8(String str, int i) {
            super(str, i);
        }

        @Override // android.util.DataUnit
        public long toBytes(long v) {
            return 1099511627776L * v;
        }
    }

    public long toBytes(long v) {
        throw new AbstractMethodError();
    }
}
