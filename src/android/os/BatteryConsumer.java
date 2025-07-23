package android.os;

import android.content.Context;
import android.database.CursorWindow;
import android.hardware.scontext.SContextConstants;
import android.os.PowerComponents;
import android.util.IntArray;
import android.util.Slog;
import android.util.SparseArray;
import android.util.proto.ProtoOutputStream;
import com.android.internal.accessibility.common.ShortcutConstants;
import java.io.PrintWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class BatteryConsumer {
    static final int COLUMN_COUNT = 1;
    static final int COLUMN_INDEX_BATTERY_CONSUMER_TYPE = 0;
    public static final int FIRST_CUSTOM_POWER_COMPONENT_ID = 1000;
    public static final int LAST_CUSTOM_POWER_COMPONENT_ID = 9999;
    public static final int POWER_COMPONENT_AMBIENT_DISPLAY = 15;
    public static final int POWER_COMPONENT_ANY = -1;
    public static final int POWER_COMPONENT_AUDIO = 4;
    public static final int POWER_COMPONENT_BASE = 18;
    public static final int POWER_COMPONENT_BLUETOOTH = 2;
    public static final int POWER_COMPONENT_CAMERA = 3;
    public static final int POWER_COMPONENT_COUNT = 20;
    public static final int POWER_COMPONENT_CPU = 1;
    public static final int POWER_COMPONENT_FLASHLIGHT = 6;
    public static final int POWER_COMPONENT_GNSS = 10;
    public static final int POWER_COMPONENT_IDLE = 16;
    public static final int POWER_COMPONENT_MEMORY = 13;
    public static final int POWER_COMPONENT_MOBILE_RADIO = 8;
    public static final int POWER_COMPONENT_PHONE = 14;
    public static final int POWER_COMPONENT_REATTRIBUTED_TO_OTHER_CONSUMERS = 17;
    public static final int POWER_COMPONENT_SCREEN = 0;
    public static final int POWER_COMPONENT_SENSORS = 9;
    public static final int POWER_COMPONENT_SYSTEM_SERVICES = 7;
    public static final int POWER_COMPONENT_VIDEO = 5;
    public static final int POWER_COMPONENT_WAKELOCK = 12;
    public static final int POWER_COMPONENT_WIFI = 11;
    public static final int POWER_COMPONENT_WIRELESS_POWER_SHARING = 19;

    @Deprecated
    public static final int POWER_MODEL_ENERGY_CONSUMPTION = 2;

    @Deprecated
    public static final int POWER_MODEL_POWER_PROFILE = 1;

    @Deprecated
    public static final int POWER_MODEL_UNDEFINED = 0;
    public static final int POWER_STATE_ANY = 0;
    public static final int POWER_STATE_BATTERY = 1;
    public static final int POWER_STATE_COUNT = 3;
    public static final int POWER_STATE_OTHER = 2;
    public static final int POWER_STATE_UNSPECIFIED = 0;
    public static final int PROCESS_STATE_ANY = -1;
    public static final int PROCESS_STATE_BACKGROUND = 2;
    public static final int PROCESS_STATE_CACHED = 4;
    public static final int PROCESS_STATE_COUNT = 5;
    public static final int PROCESS_STATE_FOREGROUND = 1;
    public static final int PROCESS_STATE_FOREGROUND_SERVICE = 3;
    public static final int PROCESS_STATE_UNSPECIFIED = 0;
    public static final int SCREEN_STATE_ANY = 0;
    public static final int SCREEN_STATE_COUNT = 3;
    public static final int SCREEN_STATE_ON = 1;
    public static final int SCREEN_STATE_OTHER = 2;
    public static final int SCREEN_STATE_UNSPECIFIED = 0;
    private static final IntArray SUPPORTED_POWER_COMPONENTS_PER_PROCESS_STATE;
    private static final String TAG = "BatteryConsumer";
    public static final Dimensions UNSPECIFIED_DIMENSIONS;
    private static final String[] sPowerStateNames;
    private static final String[] sScreenStateNames;
    protected final BatteryConsumerData mData;
    protected final PowerComponents mPowerComponents;
    private static final String[] sPowerComponentNames = {"screen", "cpu", "bluetooth", Context.CAMERA_SERVICE, "audio", "video", "flashlight", "system_services", "mobile_radio", "sensors", "gnss", "wifi", "wakelock", "memory", "phone", "ambient_display", "idle", "reattributed", "", BatteryManager.EXTRA_POWER_SHARING};
    private static final String[] sProcessStateNames = {"unspecified", "fg", "bg", "fgs", "cached"};

    @Retention(RetentionPolicy.SOURCE)
    public @interface PowerComponent {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface PowerComponentId {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface PowerModel {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface PowerState {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ProcessState {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ScreenState {
    }

    static long convertMahToDeciCoulombs(double d) {
        return (long) ((d * 36.0d) + 0.5d);
    }

    public static int powerModelToProtoEnum(int i) {
        int i2 = 1;
        if (i != 1) {
            i2 = 2;
            if (i != 2) {
                return 0;
            }
        }
        return i2;
    }

    public abstract void dump(PrintWriter printWriter, boolean z);

    @Deprecated
    public int getPowerModel(int i) {
        return 0;
    }

    @Deprecated
    public int getPowerModel(Key key) {
        return 0;
    }

    static {
        int[] iArr = {18, 1, 8, 11, 2, 4, 5, 6, 3, 10, 9, 12};
        Arrays.sort(iArr);
        SUPPORTED_POWER_COMPONENTS_PER_PROCESS_STATE = IntArray.wrap(iArr);
        sScreenStateNames = new String[]{"unspecified", "on", "off/doze"};
        sPowerStateNames = new String[]{"unspecified", "on battery", "not on battery"};
        UNSPECIFIED_DIMENSIONS = new Dimensions(-1, -1, 0, 0);
    }

    public static final class Dimensions {
        public final int powerComponentId;
        public final int powerState;
        public final int processState;
        public final int screenState;

        public Dimensions(int i, int i2) {
            this(i, i2, 0, 0);
        }

        public Dimensions(int i, int i2, int i3, int i4) {
            this.powerComponentId = i;
            this.processState = i2;
            this.screenState = i3;
            this.powerState = i4;
        }

        public String toString() {
            boolean z;
            StringBuilder sb = new StringBuilder();
            boolean z2 = true;
            if (this.powerComponentId != -1) {
                sb.append("powerComponent=");
                if (this.powerComponentId < 20) {
                    sb.append(BatteryConsumer.sPowerComponentNames[this.powerComponentId]);
                } else {
                    sb.append("CUSTOM/");
                    sb.append(this.powerComponentId);
                }
                z = true;
            } else {
                z = false;
            }
            if (this.processState != -1) {
                if (z) {
                    sb.append(", ");
                }
                sb.append("processState=");
                sb.append(BatteryConsumer.sProcessStateNames[this.processState]);
                z = true;
            }
            if (this.screenState != 0) {
                if (z) {
                    sb.append(", ");
                }
                sb.append("screenState=");
                sb.append(BatteryConsumer.screenStateToString(this.screenState));
                z = true;
            }
            if (this.powerState != 0) {
                if (z) {
                    sb.append(", ");
                }
                sb.append("powerState=");
                sb.append(BatteryConsumer.powerStateToString(this.powerState));
            } else {
                z2 = z;
            }
            if (!z2) {
                sb.append("any components and process states");
            }
            return sb.toString();
        }
    }

    public static final class Key {
        final int mDurationColumnIndex;
        final int mPowerColumnIndex;
        public final int powerComponentId;
        public final int powerState;
        public final int processState;
        public final int screenState;

        private Key(int i, int i2, int i3, int i4, int i5, int i6) {
            this.powerComponentId = i;
            this.processState = i2;
            this.screenState = i3;
            this.powerState = i4;
            this.mPowerColumnIndex = i5;
            this.mDurationColumnIndex = i6;
        }

        boolean matches(int i, int i2, int i3, int i4) {
            int i5;
            if ((i != -1 && this.powerComponentId != i) || (i5 = this.processState) == 0) {
                return false;
            }
            if (i2 != -1 && i5 != i2) {
                return false;
            }
            if (i3 == 0 || this.screenState == i3) {
                return i4 == 0 || this.powerState == i4;
            }
            return false;
        }

        public boolean equals(Object obj) {
            Key key = (Key) obj;
            return this.powerComponentId == key.powerComponentId && this.processState == key.processState && this.screenState == key.screenState && this.powerState == key.powerState;
        }

        public int hashCode() {
            return (((((this.powerComponentId * 31) + this.processState) * 31) + this.screenState) * 31) + this.powerState;
        }

        public static String toString(int i, int i2, int i3, int i4) {
            StringBuilder sb = new StringBuilder();
            if (i < 20) {
                sb.append(BatteryConsumer.powerComponentIdToString(i));
            } else {
                sb.append("CUSTOM/");
                sb.append(i);
            }
            if (i2 != 0) {
                sb.append(ShortcutConstants.SERVICES_SEPARATOR);
                sb.append(BatteryConsumer.processStateToString(i2));
            }
            if (i3 != 0) {
                sb.append(":scr-");
                sb.append(BatteryConsumer.sScreenStateNames[i3]);
            }
            if (i4 != 0) {
                sb.append(":pwr-");
                sb.append(BatteryConsumer.sPowerStateNames[i4]);
            }
            return sb.toString();
        }

        public String toString() {
            return toString(this.powerComponentId, this.processState, this.screenState, this.powerState);
        }
    }

    protected BatteryConsumer(BatteryConsumerData batteryConsumerData, PowerComponents powerComponents) {
        this.mData = batteryConsumerData;
        this.mPowerComponents = powerComponents;
    }

    public BatteryConsumer(BatteryConsumerData batteryConsumerData) {
        this.mData = batteryConsumerData;
        this.mPowerComponents = new PowerComponents(batteryConsumerData);
    }

    public String getPowerComponentName(int i) {
        return this.mData.layout.getPowerComponentName(i);
    }

    public double getConsumedPower() {
        return this.mPowerComponents.getConsumedPower(UNSPECIFIED_DIMENSIONS);
    }

    public double getConsumedPower(Dimensions dimensions) {
        return this.mPowerComponents.getConsumedPower(dimensions);
    }

    public long getUsageDurationMillis(Dimensions dimensions) {
        return this.mPowerComponents.getUsageDurationMillis(dimensions);
    }

    public int[] getPowerComponentIds() {
        return this.mData.layout.powerComponentIds;
    }

    public Key[] getKeys(int i) {
        return this.mData.layout.getKeys(i);
    }

    public Key getKey(int i) {
        return this.mData.layout.getKey(i, 0, 0, 0);
    }

    public Key getKey(int i, int i2) {
        return this.mData.layout.getKey(i, i2, 0, 0);
    }

    public double getConsumedPower(int i) {
        return this.mPowerComponents.getConsumedPower(i, -1, 0, 0);
    }

    public double getConsumedPower(Key key) {
        return this.mPowerComponents.getConsumedPower(key);
    }

    @Deprecated
    public double getConsumedPowerForCustomComponent(int i) {
        return getConsumedPower(i);
    }

    public int getCustomPowerComponentCount() {
        return this.mData.layout.customPowerComponentCount;
    }

    public String getCustomPowerComponentName(int i) {
        return this.mPowerComponents.getCustomPowerComponentName(i);
    }

    public long getUsageDurationMillis(int i) {
        return this.mPowerComponents.getUsageDurationMillis(i, -1, 0, 0);
    }

    public long getUsageDurationMillis(Key key) {
        return this.mPowerComponents.getUsageDurationMillis(key);
    }

    public static String powerComponentIdToString(int i) {
        if (i == -1) {
            return "all";
        }
        return sPowerComponentNames[i];
    }

    public static String processStateToString(int i) {
        return sProcessStateNames[i];
    }

    public static String powerStateToString(int i) {
        return sPowerStateNames[i];
    }

    public static String screenStateToString(int i) {
        return sScreenStateNames[i];
    }

    public void dump(PrintWriter printWriter) {
        dump(printWriter, true);
    }

    boolean hasStatsProtoData() {
        return writeStatsProtoImpl(null, 0L);
    }

    void writeStatsProto(ProtoOutputStream protoOutputStream, long j) {
        writeStatsProtoImpl(protoOutputStream, j);
    }

    private boolean writeStatsProtoImpl(ProtoOutputStream protoOutputStream, long j) {
        long convertMahToDeciCoulombs = convertMahToDeciCoulombs(getConsumedPower());
        if (convertMahToDeciCoulombs == 0) {
            return false;
        }
        if (protoOutputStream == null) {
            return true;
        }
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1112396529665L, convertMahToDeciCoulombs);
        this.mPowerComponents.writeStatsProto(protoOutputStream);
        protoOutputStream.end(start);
        return true;
    }

    static class BatteryConsumerData {
        public final BatteryConsumerDataLayout layout;
        private final int mCursorRow;
        private final CursorWindow mCursorWindow;

        BatteryConsumerData(CursorWindow cursorWindow, int i, BatteryConsumerDataLayout batteryConsumerDataLayout) {
            this.mCursorWindow = cursorWindow;
            this.mCursorRow = i;
            this.layout = batteryConsumerDataLayout;
        }

        static BatteryConsumerData create(CursorWindow cursorWindow, BatteryConsumerDataLayout batteryConsumerDataLayout) {
            int numRows = cursorWindow.getNumRows();
            if (!cursorWindow.allocRow()) {
                Slog.e(BatteryConsumer.TAG, "Cannot allocate BatteryConsumerData: too many UIDs: " + numRows);
                numRows = -1;
            }
            return new BatteryConsumerData(cursorWindow, numRows, batteryConsumerDataLayout);
        }

        boolean hasValue(int i) {
            int i2 = this.mCursorRow;
            return (i2 == -1 || this.mCursorWindow.getType(i2, i) == 0) ? false : true;
        }

        void putInt(int i, int i2) {
            int i3 = this.mCursorRow;
            if (i3 == -1) {
                return;
            }
            this.mCursorWindow.putLong(i2, i3, i);
        }

        int getInt(int i) {
            int i2 = this.mCursorRow;
            if (i2 == -1) {
                return 0;
            }
            return this.mCursorWindow.getInt(i2, i);
        }

        void putDouble(int i, double d) {
            int i2 = this.mCursorRow;
            if (i2 == -1) {
                return;
            }
            this.mCursorWindow.putDouble(d, i2, i);
        }

        double getDouble(int i) {
            int i2 = this.mCursorRow;
            return i2 == -1 ? SContextConstants.ENVIRONMENT_VALUE_UNKNOWN : this.mCursorWindow.getDouble(i2, i);
        }

        void putLong(int i, long j) {
            int i2 = this.mCursorRow;
            if (i2 == -1) {
                return;
            }
            this.mCursorWindow.putLong(j, i2, i);
        }

        long getLong(int i) {
            int i2 = this.mCursorRow;
            if (i2 == -1) {
                return 0L;
            }
            return this.mCursorWindow.getLong(i2, i);
        }

        void putString(int i, String str) {
            int i2 = this.mCursorRow;
            if (i2 == -1) {
                return;
            }
            this.mCursorWindow.putString(str, i2, i);
        }

        String getString(int i) {
            int i2 = this.mCursorRow;
            if (i2 == -1) {
                return null;
            }
            return this.mCursorWindow.getString(i2, i);
        }
    }

    static class BatteryConsumerDataLayout {
        private static final Key[] KEY_ARRAY = new Key[0];
        public final int columnCount;
        public final int customPowerComponentCount;
        public final String[] customPowerComponentNames;
        public final SparseArray<Key> indexedKeys;
        public final Key[] keys;
        private SparseArray<Key[]> mPerComponentKeys;
        public final int[] powerComponentIds;
        public final boolean powerStateDataIncluded;
        public final boolean processStateDataIncluded;
        public final boolean screenStateDataIncluded;
        public final int totalConsumedPowerColumnIndex;

        private int keyIndex(int i, int i2, int i3, int i4) {
            return (i << 7) | (i2 << 4) | (i3 << 2) | i4;
        }

        private BatteryConsumerDataLayout(int i, String[] strArr, boolean z, boolean z2, boolean z3) {
            this.customPowerComponentNames = strArr;
            int length = strArr.length;
            this.customPowerComponentCount = length;
            this.processStateDataIncluded = z;
            this.screenStateDataIncluded = z2;
            this.powerStateDataIncluded = z3;
            this.powerComponentIds = new int[length + 20];
            int i2 = 0;
            int i3 = 0;
            int i4 = 0;
            while (i3 < 20) {
                this.powerComponentIds[i4] = i3;
                i3++;
                i4++;
            }
            int i5 = 0;
            while (i5 < this.customPowerComponentCount) {
                this.powerComponentIds[i4] = i5 + 1000;
                i5++;
                i4++;
            }
            int i6 = i + 1;
            this.totalConsumedPowerColumnIndex = i;
            ArrayList arrayList = new ArrayList();
            int i7 = 0;
            while (i7 < 3) {
                if (z2 || i7 == 0) {
                    int i8 = 0;
                    while (i8 < 3) {
                        if (z3 || i8 == 0) {
                            int i9 = i6;
                            int i10 = 0;
                            while (true) {
                                int[] iArr = this.powerComponentIds;
                                if (i10 >= iArr.length) {
                                    break;
                                }
                                i9 = this.addKeys(arrayList, z, iArr[i10], i7, i8, i9);
                                i10++;
                            }
                            i6 = i9;
                        }
                        i8++;
                        this = this;
                        z = z;
                    }
                }
                i7++;
                this = this;
                z = z;
            }
            BatteryConsumerDataLayout batteryConsumerDataLayout = this;
            batteryConsumerDataLayout.columnCount = i6;
            Key[] keyArr = (Key[]) arrayList.toArray(KEY_ARRAY);
            batteryConsumerDataLayout.keys = keyArr;
            batteryConsumerDataLayout.indexedKeys = new SparseArray<>(keyArr.length);
            while (true) {
                Key[] keyArr2 = batteryConsumerDataLayout.keys;
                if (i2 >= keyArr2.length) {
                    return;
                }
                Key key = keyArr2[i2];
                batteryConsumerDataLayout.indexedKeys.put(batteryConsumerDataLayout.keyIndex(key.powerComponentId, key.processState, key.screenState, key.powerState), key);
                i2++;
            }
        }

        public String getPowerComponentName(int i) {
            if (i < 20) {
                return BatteryConsumer.powerComponentIdToString(i);
            }
            if (i >= 1000 && i < this.customPowerComponentCount + 1000) {
                return this.customPowerComponentNames[i - 1000];
            }
            throw new IllegalArgumentException("Unsupported power component " + i);
        }

        private int addKeys(List<Key> list, boolean z, int i, int i2, int i3, int i4) {
            int i5 = i4 + 2;
            list.add(new Key(i, 0, i2, i3, i4, i4 + 1));
            if (!z && i != 18) {
                return i5;
            }
            if (BatteryConsumer.SUPPORTED_POWER_COMPONENTS_PER_PROCESS_STATE.binarySearch(i) < 0 && i < 1000) {
                return i5;
            }
            int i6 = i5;
            for (int i7 = 0; i7 < 5; i7++) {
                if (i7 != 0) {
                    list.add(new Key(i, i7, i2, i3, i6, i6 + 1));
                    i6 += 2;
                }
            }
            return i6;
        }

        Key getKey(int i, int i2, int i3, int i4) {
            return this.indexedKeys.get(keyIndex(i, i2, i3, i4));
        }

        Key getKeyOrThrow(int i, int i2, int i3, int i4) {
            Key key = getKey(i, i2, i3, i4);
            if (key != null) {
                return key;
            }
            throw new IllegalArgumentException("Unsupported power component ID: " + Key.toString(i, i2, i3, i4));
        }

        public Key[] getKeys(int i) {
            Key[] keyArr;
            synchronized (this) {
                if (this.mPerComponentKeys == null) {
                    this.mPerComponentKeys = new SparseArray<>(this.powerComponentIds.length);
                }
                keyArr = this.mPerComponentKeys.get(i);
                if (keyArr == null) {
                    ArrayList arrayList = new ArrayList();
                    for (Key key : this.keys) {
                        if (key.powerComponentId == i) {
                            arrayList.add(key);
                        }
                    }
                    keyArr = (Key[]) arrayList.toArray(new Key[arrayList.size()]);
                    this.mPerComponentKeys.put(i, keyArr);
                }
            }
            return keyArr;
        }
    }

    static BatteryConsumerDataLayout createBatteryConsumerDataLayout(String[] strArr, boolean z, boolean z2, boolean z3) {
        return new BatteryConsumerDataLayout(Math.max(Math.max(Math.max(1, 3), 3), 2), strArr, z, z2, z3);
    }

    protected static abstract class BaseBuilder<T extends BaseBuilder<?>> {
        protected final BatteryConsumerData mData;
        protected final PowerComponents.Builder mPowerComponentsBuilder;

        public BaseBuilder(BatteryConsumerData batteryConsumerData, int i, double d) {
            this.mData = batteryConsumerData;
            batteryConsumerData.putLong(0, i);
            this.mPowerComponentsBuilder = new PowerComponents.Builder(batteryConsumerData, d);
        }

        public Key[] getKeys(int i) {
            return this.mData.layout.getKeys(i);
        }

        public Key getKey(int i, int i2) {
            return this.mData.layout.getKey(i, i2, 0, 0);
        }

        public Key getKey(int i, int i2, int i3, int i4) {
            return this.mData.layout.getKey(i, i2, i3, i4);
        }

        @Deprecated
        public T setConsumedPower(int i, double d) {
            return setConsumedPower(i, d, 1);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Deprecated
        public T setConsumedPower(int i, double d, int i2) {
            this.mPowerComponentsBuilder.setConsumedPower(getKey(i, 0), d);
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public T addConsumedPower(int i, double d, int i2) {
            this.mPowerComponentsBuilder.addConsumedPower(getKey(i, 0), d);
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public T setConsumedPower(Key key, double d, int i) {
            this.mPowerComponentsBuilder.setConsumedPower(key, d);
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public T addConsumedPower(int i, double d) {
            this.mPowerComponentsBuilder.addConsumedPower(getKey(i, 0), d);
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public T addConsumedPower(Key key, double d) {
            this.mPowerComponentsBuilder.addConsumedPower(key, d);
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Deprecated
        public T setUsageDurationMillis(int i, long j) {
            this.mPowerComponentsBuilder.setUsageDurationMillis(getKey(i, 0), j);
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Deprecated
        public T setUsageDurationMillis(Key key, long j) {
            this.mPowerComponentsBuilder.setUsageDurationMillis(key, j);
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public T addUsageDurationMillis(int i, long j) {
            this.mPowerComponentsBuilder.addUsageDurationMillis(getKey(i, 0), j);
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public T addUsageDurationMillis(Key key, long j) {
            this.mPowerComponentsBuilder.addUsageDurationMillis(key, j);
            return this;
        }

        public double getTotalPower() {
            return this.mPowerComponentsBuilder.getTotalPower();
        }
    }
}
