package android.util;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes4.dex */
public class DataUnit {
    public static final DataUnit KILOBYTES = new C35901("KILOBYTES", 0);
    public static final DataUnit MEGABYTES = new C35912("MEGABYTES", 1);
    public static final DataUnit GIGABYTES = new C35923("GIGABYTES", 2);
    public static final DataUnit TERABYTES = new C35934("TERABYTES", 3);
    public static final DataUnit KIBIBYTES = new C35945("KIBIBYTES", 4);
    public static final DataUnit MEBIBYTES = new C35956("MEBIBYTES", 5);
    public static final DataUnit GIBIBYTES = new C35967("GIBIBYTES", 6);
    public static final DataUnit TEBIBYTES = new C35978("TEBIBYTES", 7);
    private static final /* synthetic */ DataUnit[] $VALUES = $values();

    /* renamed from: android.util.DataUnit$1 */
    enum C35901 extends DataUnit {
        private C35901(String str, int i) {
            super(str, i);
        }

        @Override // android.util.DataUnit
        public long toBytes(long v) {
            return 1000 * v;
        }
    }

    private static /* synthetic */ DataUnit[] $values() {
        return new DataUnit[]{KILOBYTES, MEGABYTES, GIGABYTES, TERABYTES, KIBIBYTES, MEBIBYTES, GIBIBYTES, TEBIBYTES};
    }

    private DataUnit(String str, int i) {
    }

    public static DataUnit valueOf(String name) {
        return (DataUnit) Enum.valueOf(DataUnit.class, name);
    }

    public static DataUnit[] values() {
        return (DataUnit[]) $VALUES.clone();
    }

    /* renamed from: android.util.DataUnit$2 */
    enum C35912 extends DataUnit {
        private C35912(String str, int i) {
            super(str, i);
        }

        @Override // android.util.DataUnit
        public long toBytes(long v) {
            return 1000000 * v;
        }
    }

    /* renamed from: android.util.DataUnit$3 */
    enum C35923 extends DataUnit {
        private C35923(String str, int i) {
            super(str, i);
        }

        @Override // android.util.DataUnit
        public long toBytes(long v) {
            return 1000000000 * v;
        }
    }

    /* renamed from: android.util.DataUnit$4 */
    enum C35934 extends DataUnit {
        private C35934(String str, int i) {
            super(str, i);
        }

        @Override // android.util.DataUnit
        public long toBytes(long v) {
            return 1000000000000L * v;
        }
    }

    /* renamed from: android.util.DataUnit$5 */
    enum C35945 extends DataUnit {
        private C35945(String str, int i) {
            super(str, i);
        }

        @Override // android.util.DataUnit
        public long toBytes(long v) {
            return 1024 * v;
        }
    }

    /* renamed from: android.util.DataUnit$6 */
    enum C35956 extends DataUnit {
        private C35956(String str, int i) {
            super(str, i);
        }

        @Override // android.util.DataUnit
        public long toBytes(long v) {
            return 1048576 * v;
        }
    }

    /* renamed from: android.util.DataUnit$7 */
    enum C35967 extends DataUnit {
        private C35967(String str, int i) {
            super(str, i);
        }

        @Override // android.util.DataUnit
        public long toBytes(long v) {
            return 1073741824 * v;
        }
    }

    /* renamed from: android.util.DataUnit$8 */
    enum C35978 extends DataUnit {
        private C35978(String str, int i) {
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
