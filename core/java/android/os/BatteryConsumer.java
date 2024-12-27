package android.os;

import android.content.Context;
import android.database.CursorWindow;
import android.hardware.scontext.SContextConstants;
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

public abstract class BatteryConsumer {
    static final int COLUMN_COUNT = 1;
    static final int COLUMN_INDEX_BATTERY_CONSUMER_TYPE = 0;
    public static final int FIRST_CUSTOM_POWER_COMPONENT_ID = 1000;
    public static final int LAST_CUSTOM_POWER_COMPONENT_ID = 9999;
    public static final int POWER_COMPONENT_AMBIENT_DISPLAY = 15;
    public static final int POWER_COMPONENT_ANY = -1;
    public static final int POWER_COMPONENT_AUDIO = 4;
    public static final int POWER_COMPONENT_BLUETOOTH = 2;
    public static final int POWER_COMPONENT_CAMERA = 3;
    public static final int POWER_COMPONENT_COUNT = 19;
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
    public static final int POWER_COMPONENT_WIRELESS_POWER_SHARING = 18;
    public static final int POWER_MODEL_ENERGY_CONSUMPTION = 2;
    public static final int POWER_MODEL_POWER_PROFILE = 1;
    public static final int POWER_MODEL_UNDEFINED = 0;
    public static final int POWER_STATE_ANY = 0;
    public static final int POWER_STATE_BATTERY = 1;
    public static final int POWER_STATE_COUNT = 3;
    public static final int POWER_STATE_OTHER = 2;
    public static final int POWER_STATE_UNSPECIFIED = 0;
    public static final int PROCESS_STATE_ANY = 0;
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
    private static final String[] sPowerComponentNames = new String[19];
    private static final String[] sPowerStateNames;
    private static final String[] sProcessStateNames;
    private static final String[] sScreenStateNames;
    protected final BatteryConsumerData mData;
    protected final PowerComponents mPowerComponents;

    @Retention(RetentionPolicy.SOURCE)
    public @interface PowerComponent {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface PowerModel {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface PowerState {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface ProcessState {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface ScreenState {}

    public abstract void dump(PrintWriter printWriter, boolean z);

    static {
        sPowerComponentNames[0] = "screen";
        sPowerComponentNames[1] = "cpu";
        sPowerComponentNames[2] = "bluetooth";
        sPowerComponentNames[3] = Context.CAMERA_SERVICE;
        sPowerComponentNames[4] = "audio";
        sPowerComponentNames[5] = "video";
        sPowerComponentNames[6] = "flashlight";
        sPowerComponentNames[7] = "system_services";
        sPowerComponentNames[8] = "mobile_radio";
        sPowerComponentNames[9] = "sensors";
        sPowerComponentNames[10] = "gnss";
        sPowerComponentNames[11] = "wifi";
        sPowerComponentNames[12] = "wakelock";
        sPowerComponentNames[13] = "memory";
        sPowerComponentNames[14] = "phone";
        sPowerComponentNames[15] = "ambient_display";
        sPowerComponentNames[16] = "idle";
        sPowerComponentNames[17] = "reattributed";
        sPowerComponentNames[18] = BatteryManager.EXTRA_POWER_SHARING;
        sProcessStateNames = new String[5];
        sProcessStateNames[0] = "unspecified";
        sProcessStateNames[1] = "fg";
        sProcessStateNames[2] = "bg";
        sProcessStateNames[3] = "fgs";
        sProcessStateNames[4] = "cached";
        int[] supportedPowerComponents = {1, 8, 11, 2, 4, 5, 6, 3, 10};
        Arrays.sort(supportedPowerComponents);
        SUPPORTED_POWER_COMPONENTS_PER_PROCESS_STATE = IntArray.wrap(supportedPowerComponents);
        sScreenStateNames = new String[3];
        sScreenStateNames[0] = "unspecified";
        sScreenStateNames[1] = "on";
        sScreenStateNames[2] = "off/doze";
        sPowerStateNames = new String[3];
        sPowerStateNames[0] = "unspecified";
        sPowerStateNames[1] = "on battery";
        sPowerStateNames[2] = "not on battery";
        UNSPECIFIED_DIMENSIONS = new Dimensions(-1, 0, 0, 0);
    }

    public static final class Dimensions {
        public final int powerComponent;
        public final int powerState;
        public final int processState;
        public final int screenState;

        public Dimensions(int powerComponent, int processState) {
            this(powerComponent, processState, 0, 0);
        }

        public Dimensions(int powerComponent, int processState, int screenState, int powerState) {
            this.powerComponent = powerComponent;
            this.processState = processState;
            this.screenState = screenState;
            this.powerState = powerState;
        }

        public String toString() {
            boolean dimensionSpecified = false;
            StringBuilder sb = new StringBuilder();
            if (this.powerComponent != -1) {
                sb.append("powerComponent=")
                        .append(BatteryConsumer.sPowerComponentNames[this.powerComponent]);
                dimensionSpecified = true;
            }
            if (this.processState != 0) {
                if (dimensionSpecified) {
                    sb.append(", ");
                }
                sb.append("processState=")
                        .append(BatteryConsumer.sProcessStateNames[this.processState]);
                dimensionSpecified = true;
            }
            if (this.screenState != 0) {
                if (dimensionSpecified) {
                    sb.append(", ");
                }
                sb.append("screenState=")
                        .append(BatteryConsumer.screenStateToString(this.screenState));
                dimensionSpecified = true;
            }
            if (this.powerState != 0) {
                if (dimensionSpecified) {
                    sb.append(", ");
                }
                sb.append("powerState=")
                        .append(BatteryConsumer.powerStateToString(this.powerState));
                dimensionSpecified = true;
            }
            if (!dimensionSpecified) {
                sb.append("any components and process states");
            }
            return sb.toString();
        }
    }

    public static final class Key {
        final int mDurationColumnIndex;
        final int mPowerColumnIndex;
        final int mPowerModelColumnIndex;
        private String mShortString;
        public final int powerComponent;
        public final int powerState;
        public final int processState;
        public final int screenState;

        private Key(
                int powerComponent,
                int processState,
                int screenState,
                int powerState,
                int powerModelColumnIndex,
                int powerColumnIndex,
                int durationColumnIndex) {
            this.powerComponent = powerComponent;
            this.processState = processState;
            this.screenState = screenState;
            this.powerState = powerState;
            this.mPowerModelColumnIndex = powerModelColumnIndex;
            this.mPowerColumnIndex = powerColumnIndex;
            this.mDurationColumnIndex = durationColumnIndex;
        }

        boolean matches(int powerComponent, int processState, int screenState, int powerState) {
            if (powerComponent != -1 && this.powerComponent != powerComponent) {
                return false;
            }
            if (processState != 0 && this.processState != processState) {
                return false;
            }
            if (screenState == 0 || this.screenState == screenState) {
                return powerState == 0 || this.powerState == powerState;
            }
            return false;
        }

        public boolean equals(Object o) {
            Key key = (Key) o;
            return this.powerComponent == key.powerComponent
                    && this.processState == key.processState
                    && this.screenState == key.screenState
                    && this.powerState == key.powerState;
        }

        public int hashCode() {
            int result = this.powerComponent;
            return (((((result * 31) + this.processState) * 31) + this.screenState) * 31)
                    + this.powerState;
        }

        public static String toString(
                int powerComponent, int processState, int screenState, int powerState) {
            StringBuilder sb = new StringBuilder();
            sb.append(BatteryConsumer.powerComponentIdToString(powerComponent));
            if (processState != 0) {
                sb.append(ShortcutConstants.SERVICES_SEPARATOR);
                sb.append(BatteryConsumer.processStateToString(processState));
            }
            if (screenState != 0) {
                sb.append(":scr-");
                sb.append(BatteryConsumer.sScreenStateNames[screenState]);
            }
            if (powerState != 0) {
                sb.append(":pwr-");
                sb.append(BatteryConsumer.sPowerStateNames[powerState]);
            }
            return sb.toString();
        }

        public String toString() {
            return toString(
                    this.powerComponent, this.processState, this.screenState, this.powerState);
        }
    }

    protected BatteryConsumer(BatteryConsumerData data, PowerComponents powerComponents) {
        this.mData = data;
        this.mPowerComponents = powerComponents;
    }

    public BatteryConsumer(BatteryConsumerData data) {
        this.mData = data;
        this.mPowerComponents = new PowerComponents(data);
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

    public Key[] getKeys(int componentId) {
        return this.mData.layout.getKeys(componentId);
    }

    public Key getKey(int componentId) {
        return this.mData.layout.getKey(componentId, 0, 0, 0);
    }

    public Key getKey(int componentId, int processState) {
        return this.mData.layout.getKey(componentId, processState, 0, 0);
    }

    public double getConsumedPower(int componentId) {
        return this.mPowerComponents.getConsumedPower(componentId, 0, 0, 0);
    }

    public double getConsumedPower(Key key) {
        return this.mPowerComponents.getConsumedPower(key);
    }

    public int getPowerModel(int componentId) {
        return this.mPowerComponents.getPowerModel(
                this.mData.layout.getKeyOrThrow(componentId, 0, 0, 0));
    }

    public int getPowerModel(Key key) {
        return this.mPowerComponents.getPowerModel(key);
    }

    public double getConsumedPowerForCustomComponent(int componentId) {
        return this.mPowerComponents.getConsumedPowerForCustomComponent(componentId);
    }

    public int getCustomPowerComponentCount() {
        return this.mData.layout.customPowerComponentCount;
    }

    public String getCustomPowerComponentName(int componentId) {
        return this.mPowerComponents.getCustomPowerComponentName(componentId);
    }

    public long getUsageDurationMillis(int componentId) {
        return this.mPowerComponents.getUsageDurationMillis(getKey(componentId));
    }

    public long getUsageDurationMillis(Key key) {
        return this.mPowerComponents.getUsageDurationMillis(key);
    }

    public long getUsageDurationForCustomComponentMillis(int componentId) {
        return this.mPowerComponents.getUsageDurationForCustomComponentMillis(componentId);
    }

    public static String powerComponentIdToString(int componentId) {
        if (componentId == -1) {
            return "all";
        }
        return sPowerComponentNames[componentId];
    }

    public static String powerModelToString(int powerModel) {
        switch (powerModel) {
            case 1:
                return "power profile";
            case 2:
                return "energy consumption";
            default:
                return "";
        }
    }

    public static int powerModelToProtoEnum(int powerModel) {
        switch (powerModel) {
            case 1:
                return 1;
            case 2:
                return 2;
            default:
                return 0;
        }
    }

    public static String processStateToString(int processState) {
        return sProcessStateNames[processState];
    }

    public static String powerStateToString(int powerState) {
        return sPowerStateNames[powerState];
    }

    public static String screenStateToString(int screenState) {
        return sScreenStateNames[screenState];
    }

    public void dump(PrintWriter pw) {
        dump(pw, true);
    }

    boolean hasStatsProtoData() {
        return writeStatsProtoImpl(null, 0L);
    }

    void writeStatsProto(ProtoOutputStream proto, long fieldId) {
        writeStatsProtoImpl(proto, fieldId);
    }

    private boolean writeStatsProtoImpl(ProtoOutputStream proto, long fieldId) {
        long totalConsumedPowerDeciCoulombs = convertMahToDeciCoulombs(getConsumedPower());
        if (totalConsumedPowerDeciCoulombs == 0) {
            return false;
        }
        if (proto == null) {
            return true;
        }
        long token = proto.start(fieldId);
        proto.write(1112396529665L, totalConsumedPowerDeciCoulombs);
        this.mPowerComponents.writeStatsProto(proto);
        proto.end(token);
        return true;
    }

    static long convertMahToDeciCoulombs(double powerMah) {
        return (long) ((36.0d * powerMah) + 0.5d);
    }

    static class BatteryConsumerData {
        public final BatteryConsumerDataLayout layout;
        private final int mCursorRow;
        private final CursorWindow mCursorWindow;

        BatteryConsumerData(
                CursorWindow cursorWindow, int cursorRow, BatteryConsumerDataLayout layout) {
            this.mCursorWindow = cursorWindow;
            this.mCursorRow = cursorRow;
            this.layout = layout;
        }

        static BatteryConsumerData create(
                CursorWindow cursorWindow, BatteryConsumerDataLayout layout) {
            int cursorRow = cursorWindow.getNumRows();
            if (!cursorWindow.allocRow()) {
                Slog.e(
                        BatteryConsumer.TAG,
                        "Cannot allocate BatteryConsumerData: too many UIDs: " + cursorRow);
                cursorRow = -1;
            }
            return new BatteryConsumerData(cursorWindow, cursorRow, layout);
        }

        boolean hasValue(int columnIndex) {
            return (this.mCursorRow == -1
                            || this.mCursorWindow.getType(this.mCursorRow, columnIndex) == 0)
                    ? false
                    : true;
        }

        void putInt(int columnIndex, int value) {
            if (this.mCursorRow == -1) {
                return;
            }
            this.mCursorWindow.putLong(value, this.mCursorRow, columnIndex);
        }

        int getInt(int columnIndex) {
            if (this.mCursorRow == -1) {
                return 0;
            }
            return this.mCursorWindow.getInt(this.mCursorRow, columnIndex);
        }

        void putDouble(int columnIndex, double value) {
            if (this.mCursorRow == -1) {
                return;
            }
            this.mCursorWindow.putDouble(value, this.mCursorRow, columnIndex);
        }

        double getDouble(int columnIndex) {
            if (this.mCursorRow == -1) {
                return SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
            }
            return this.mCursorWindow.getDouble(this.mCursorRow, columnIndex);
        }

        void putLong(int columnIndex, long value) {
            if (this.mCursorRow == -1) {
                return;
            }
            this.mCursorWindow.putLong(value, this.mCursorRow, columnIndex);
        }

        long getLong(int columnIndex) {
            if (this.mCursorRow == -1) {
                return 0L;
            }
            return this.mCursorWindow.getLong(this.mCursorRow, columnIndex);
        }

        void putString(int columnIndex, String value) {
            if (this.mCursorRow == -1) {
                return;
            }
            this.mCursorWindow.putString(value, this.mCursorRow, columnIndex);
        }

        String getString(int columnIndex) {
            if (this.mCursorRow == -1) {
                return null;
            }
            return this.mCursorWindow.getString(this.mCursorRow, columnIndex);
        }
    }

    static class BatteryConsumerDataLayout {
        private static final Key[] KEY_ARRAY = new Key[0];
        public static final int POWER_MODEL_NOT_INCLUDED = -1;
        public final int columnCount;
        public final int customPowerComponentCount;
        public final String[] customPowerComponentNames;
        public final int firstCustomConsumedPowerColumn;
        public final int firstCustomUsageDurationColumn;
        public final SparseArray<Key> indexedKeys;
        public final Key[] keys;
        private Key[][] mPerComponentKeys;
        public final boolean powerModelsIncluded;
        public final boolean powerStateDataIncluded;
        public final boolean processStateDataIncluded;
        public final boolean screenStateDataIncluded;
        public final int totalConsumedPowerColumnIndex;

        private BatteryConsumerDataLayout(
                int firstColumn,
                String[] customPowerComponentNames,
                boolean powerModelsIncluded,
                boolean includeProcessStateData,
                boolean includeScreenState,
                boolean includePowerState) {
            int powerState;
            int i;
            this.customPowerComponentNames = customPowerComponentNames;
            this.customPowerComponentCount = customPowerComponentNames.length;
            this.powerModelsIncluded = powerModelsIncluded;
            this.processStateDataIncluded = includeProcessStateData;
            this.screenStateDataIncluded = includeScreenState;
            this.powerStateDataIncluded = includePowerState;
            int columnIndex = firstColumn + 1;
            this.totalConsumedPowerColumnIndex = firstColumn;
            ArrayList<Key> keyList = new ArrayList<>();
            int screenState = 0;
            while (true) {
                int i2 = 3;
                if (screenState >= 3) {
                    break;
                }
                if (includeScreenState || screenState == 0) {
                    int powerState2 = 0;
                    while (powerState2 < i2) {
                        if (!includePowerState && powerState2 != 0) {
                            powerState = powerState2;
                            i = i2;
                        } else {
                            int componentId = 0;
                            int columnIndex2 = columnIndex;
                            while (componentId < 19) {
                                int i3 = componentId;
                                int componentId2 = componentId;
                                int componentId3 = screenState;
                                columnIndex2 =
                                        addKeys(
                                                keyList,
                                                powerModelsIncluded,
                                                includeProcessStateData,
                                                i3,
                                                componentId3,
                                                powerState2,
                                                columnIndex2);
                                componentId = componentId2 + 1;
                                i2 = i2;
                            }
                            powerState = powerState2;
                            i = i2;
                            columnIndex = columnIndex2;
                        }
                        powerState2 = powerState + 1;
                        i2 = i;
                    }
                }
                screenState++;
            }
            this.firstCustomConsumedPowerColumn = columnIndex;
            int columnIndex3 = columnIndex + this.customPowerComponentCount;
            this.firstCustomUsageDurationColumn = columnIndex3;
            this.columnCount = columnIndex3 + this.customPowerComponentCount;
            this.keys = (Key[]) keyList.toArray(KEY_ARRAY);
            this.indexedKeys = new SparseArray<>(this.keys.length);
            for (int i4 = 0; i4 < this.keys.length; i4++) {
                Key key = this.keys[i4];
                int index =
                        keyIndex(
                                key.powerComponent,
                                key.processState,
                                key.screenState,
                                key.powerState);
                this.indexedKeys.put(index, key);
            }
        }

        private int addKeys(
                List<Key> keys,
                boolean powerModelsIncluded,
                boolean includeProcessStateData,
                int componentId,
                int screenState,
                int powerState,
                int columnIndex) {
            int columnIndex2;
            int i;
            int columnIndex3;
            int i2;
            if (powerModelsIncluded) {
                i = columnIndex;
                columnIndex2 = columnIndex + 1;
            } else {
                columnIndex2 = columnIndex;
                i = -1;
            }
            int columnIndex4 = columnIndex2 + 1;
            int columnIndex5 = columnIndex4 + 1;
            keys.add(
                    new Key(
                            componentId,
                            0,
                            screenState,
                            powerState,
                            i,
                            columnIndex2,
                            columnIndex4));
            if (includeProcessStateData) {
                boolean isSupported =
                        BatteryConsumer.SUPPORTED_POWER_COMPONENTS_PER_PROCESS_STATE.binarySearch(
                                        componentId)
                                >= 0;
                if (isSupported) {
                    int columnIndex6 = columnIndex5;
                    for (int columnIndex7 = 0; columnIndex7 < 5; columnIndex7++) {
                        if (columnIndex7 != 0) {
                            if (powerModelsIncluded) {
                                i2 = columnIndex6;
                                columnIndex3 = columnIndex6 + 1;
                            } else {
                                columnIndex3 = columnIndex6;
                                i2 = -1;
                            }
                            int columnIndex8 = columnIndex3 + 1;
                            keys.add(
                                    new Key(
                                            componentId,
                                            columnIndex7,
                                            screenState,
                                            powerState,
                                            i2,
                                            columnIndex3,
                                            columnIndex8));
                            columnIndex6 = columnIndex8 + 1;
                        }
                    }
                    int processState = columnIndex6;
                    return processState;
                }
                return columnIndex5;
            }
            return columnIndex5;
        }

        Key getKey(int componentId, int processState, int screenState, int powerState) {
            return this.indexedKeys.get(
                    keyIndex(componentId, processState, screenState, powerState));
        }

        Key getKeyOrThrow(int componentId, int processState, int screenState, int powerState) {
            Key key = getKey(componentId, processState, screenState, powerState);
            if (key == null) {
                throw new IllegalArgumentException(
                        "Unsupported power component ID: "
                                + Key.toString(componentId, processState, screenState, powerState));
            }
            return key;
        }

        public Key[] getKeys(int componentId) {
            Key[] componentKeys;
            synchronized (this) {
                if (this.mPerComponentKeys == null) {
                    this.mPerComponentKeys = new Key[19][];
                }
                componentKeys = this.mPerComponentKeys[componentId];
                if (componentKeys == null) {
                    ArrayList<Key> out = new ArrayList<>();
                    for (Key key : this.keys) {
                        if (key.powerComponent == componentId) {
                            out.add(key);
                        }
                    }
                    componentKeys = (Key[]) out.toArray(new Key[out.size()]);
                    this.mPerComponentKeys[componentId] = componentKeys;
                }
            }
            return componentKeys;
        }

        private int keyIndex(int componentId, int processState, int screenState, int powerState) {
            return (componentId << 7) | (processState << 4) | (screenState << 2) | powerState;
        }
    }

    static BatteryConsumerDataLayout createBatteryConsumerDataLayout(
            String[] customPowerComponentNames,
            boolean includePowerModels,
            boolean includeProcessStateData,
            boolean includeScreenStateData,
            boolean includePowerStateData) {
        int columnCount = Math.max(1, 3);
        return new BatteryConsumerDataLayout(
                Math.max(Math.max(columnCount, 6), 2),
                customPowerComponentNames,
                includePowerModels,
                includeProcessStateData,
                includeScreenStateData,
                includePowerStateData);
    }

    protected abstract static class BaseBuilder<T extends BaseBuilder<?>> {
        protected final BatteryConsumerData mData;
        protected final PowerComponents.Builder mPowerComponentsBuilder;

        public BaseBuilder(
                BatteryConsumerData data, int consumerType, double minConsumedPowerThreshold) {
            this.mData = data;
            data.putLong(0, consumerType);
            this.mPowerComponentsBuilder =
                    new PowerComponents.Builder(data, minConsumedPowerThreshold);
        }

        public Key[] getKeys(int componentId) {
            return this.mData.layout.getKeys(componentId);
        }

        public Key getKey(int componentId, int processState) {
            return this.mData.layout.getKey(componentId, processState, 0, 0);
        }

        public Key getKey(int componentId, int processState, int screenState, int powerState) {
            return this.mData.layout.getKey(componentId, processState, screenState, powerState);
        }

        public T setConsumedPower(int componentId, double componentPower) {
            return setConsumedPower(componentId, componentPower, 1);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public T setConsumedPower(int componentId, double componentPower, int powerModel) {
            this.mPowerComponentsBuilder.setConsumedPower(
                    getKey(componentId, 0), componentPower, powerModel);
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public T addConsumedPower(int componentId, double componentPower, int powerModel) {
            this.mPowerComponentsBuilder.addConsumedPower(
                    getKey(componentId, 0), componentPower, powerModel);
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public T setConsumedPower(Key key, double componentPower, int powerModel) {
            this.mPowerComponentsBuilder.setConsumedPower(key, componentPower, powerModel);
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public T addConsumedPower(Key key, double componentPower, int powerModel) {
            this.mPowerComponentsBuilder.addConsumedPower(key, componentPower, powerModel);
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public T setConsumedPowerForCustomComponent(int componentId, double componentPower) {
            this.mPowerComponentsBuilder.setConsumedPowerForCustomComponent(
                    componentId, componentPower);
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public T addConsumedPowerForCustomComponent(int componentId, double componentPower) {
            this.mPowerComponentsBuilder.addConsumedPowerForCustomComponent(
                    componentId, componentPower);
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public T setUsageDurationMillis(int componentId, long componentUsageTimeMillis) {
            this.mPowerComponentsBuilder.setUsageDurationMillis(
                    getKey(componentId, 0), componentUsageTimeMillis);
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public T setUsageDurationMillis(Key key, long componentUsageTimeMillis) {
            this.mPowerComponentsBuilder.setUsageDurationMillis(key, componentUsageTimeMillis);
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public T setUsageDurationForCustomComponentMillis(
                int componentId, long componentUsageTimeMillis) {
            this.mPowerComponentsBuilder.setUsageDurationForCustomComponentMillis(
                    componentId, componentUsageTimeMillis);
            return this;
        }

        public double getTotalPower() {
            return this.mPowerComponentsBuilder.getTotalPower();
        }
    }
}
