package android.os;

import android.database.CursorWindow;
import android.hardware.scontext.SContextConstants;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.AggregateBatteryConsumer;
import android.os.BatteryConsumer;
import android.os.BatteryStats;
import android.os.Parcelable;
import android.os.UidBatteryConsumer;
import android.os.UserBatteryConsumer;
import android.util.Range;
import android.util.SparseArray;
import android.util.proto.ProtoOutputStream;
import com.android.internal.content.NativeLibraryHelper;
import com.android.internal.os.BatteryStatsHistory;
import com.android.internal.os.BatteryStatsHistoryIterator;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import java.io.Closeable;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.ToDoubleFunction;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes3.dex */
public final class BatteryUsageStats implements Parcelable, Closeable {
    public static final int AGGREGATE_BATTERY_CONSUMER_SCOPE_ALL_APPS = 1;
    public static final int AGGREGATE_BATTERY_CONSUMER_SCOPE_COUNT = 2;
    public static final int AGGREGATE_BATTERY_CONSUMER_SCOPE_DEVICE = 0;
    private static final long BATTERY_CONSUMER_CURSOR_WINDOW_SIZE = 20000000;
    private static final int STATSD_PULL_ATOM_MAX_BYTES = 45000;
    private static final double WEIGHT_BACKGROUND_STATE = 8.333333333333333E-5d;
    private static final double WEIGHT_CONSUMED_POWER = 1.0d;
    private static final double WEIGHT_FOREGROUND_STATE = 2.777777777777778E-5d;
    static final String XML_ATTR_BATTERY_CAPACITY = "battery_capacity";
    static final String XML_ATTR_BATTERY_REMAINING = "battery_remaining";
    static final String XML_ATTR_CHARGE_REMAINING = "charge_remaining";
    static final String XML_ATTR_DISCHARGE_DURATION = "discharge_duration";
    static final String XML_ATTR_DISCHARGE_LOWER = "discharge_lower";
    static final String XML_ATTR_DISCHARGE_PERCENT = "discharge_pct";
    static final String XML_ATTR_DISCHARGE_UPPER = "discharge_upper";
    static final String XML_ATTR_DURATION = "duration";
    static final String XML_ATTR_END_TIMESTAMP = "end_timestamp";
    static final String XML_ATTR_HIGHEST_DRAIN_PACKAGE = "highest_drain_package";
    static final String XML_ATTR_ID = "id";
    static final String XML_ATTR_POWER = "power";
    static final String XML_ATTR_POWER_STATE = "power_state";
    static final String XML_ATTR_PREFIX_CUSTOM_COMPONENT = "custom_component_";
    static final String XML_ATTR_PREFIX_INCLUDES_POWER_STATE_DATA = "includes_power_state_data";
    static final String XML_ATTR_PREFIX_INCLUDES_PROC_STATE_DATA = "includes_proc_state_data";
    static final String XML_ATTR_PREFIX_INCLUDES_SCREEN_STATE_DATA = "includes_screen_state_data";
    static final String XML_ATTR_PROCESS_STATE = "process_state";
    static final String XML_ATTR_SCOPE = "scope";
    static final String XML_ATTR_SCREEN_STATE = "screen_state";
    static final String XML_ATTR_START_TIMESTAMP = "start_timestamp";
    static final String XML_ATTR_TIME_IN_BACKGROUND = "time_in_background";
    static final String XML_ATTR_TIME_IN_FOREGROUND = "time_in_foreground";
    static final String XML_ATTR_TIME_IN_FOREGROUND_SERVICE = "time_in_foreground_service";
    static final String XML_ATTR_UID = "uid";
    static final String XML_ATTR_USER_ID = "user_id";
    static final String XML_TAG_AGGREGATE = "aggregate";
    static final String XML_TAG_BATTERY_USAGE_STATS = "battery_usage_stats";
    static final String XML_TAG_COMPONENT = "component";
    static final String XML_TAG_POWER_COMPONENTS = "power_components";
    static final String XML_TAG_UID = "uid";
    static final String XML_TAG_USER = "user";
    private static volatile boolean sInstanceLeakDetectionEnabled;
    private static Map<CursorWindow, Exception> sInstances;
    private final AggregateBatteryConsumer[] mAggregateBatteryConsumers;
    private final double mBatteryCapacityMah;
    private final BatteryConsumer.BatteryConsumerDataLayout mBatteryConsumerDataLayout;
    private CursorWindow mBatteryConsumersCursorWindow;
    private double mBatteryRatedCapacityMah;
    private final BatteryStatsHistory mBatteryStatsHistory;
    private final long mBatteryTimeRemainingMs;
    private double mBatteryTypicalCapacityMah;
    private final long mChargeTimeRemainingMs;
    private final String[] mCustomPowerComponentNames;
    private final long mDischargeDurationMs;
    private final int mDischargePercentage;
    private final double mDischargedPowerLowerBound;
    private final double mDischargedPowerUpperBound;
    private final boolean mIncludesPowerStateData;
    private final boolean mIncludesProcessStateData;
    private final boolean mIncludesScreenStateData;
    private final long mPreferredHistoryDurationMs;
    private final long mStatsDurationMs;
    private final long mStatsEndTimestampMs;
    private final long mStatsStartTimestampMs;
    private final List<UidBatteryConsumer> mUidBatteryConsumers;
    private final List<UserBatteryConsumer> mUserBatteryConsumers;
    private static final int[] UID_USAGE_TIME_PROCESS_STATES = {1, 2, 3};
    public static final Parcelable.Creator<BatteryUsageStats> CREATOR = new Parcelable.Creator<BatteryUsageStats>() { // from class: android.os.BatteryUsageStats.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BatteryUsageStats createFromParcel(Parcel parcel) {
            return new BatteryUsageStats(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BatteryUsageStats[] newArray(int i) {
            return new BatteryUsageStats[i];
        }
    };

    @Retention(RetentionPolicy.SOURCE)
    public @interface AggregateBatteryConsumerScope {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private BatteryUsageStats(Builder builder) {
        this.mStatsStartTimestampMs = builder.mStatsStartTimestampMs;
        this.mStatsEndTimestampMs = builder.mStatsEndTimestampMs;
        this.mStatsDurationMs = builder.getStatsDuration();
        this.mBatteryCapacityMah = builder.mBatteryCapacityMah;
        this.mBatteryRatedCapacityMah = builder.mBatteryRatedCapacityMah;
        this.mBatteryTypicalCapacityMah = builder.mBatteryTypicalCapacityMah;
        this.mDischargePercentage = builder.mDischargePercentage;
        this.mDischargedPowerLowerBound = builder.mDischargedPowerLowerBoundMah;
        this.mDischargedPowerUpperBound = builder.mDischargedPowerUpperBoundMah;
        this.mDischargeDurationMs = builder.mDischargeDurationMs;
        this.mBatteryStatsHistory = builder.mBatteryStatsHistory;
        this.mPreferredHistoryDurationMs = builder.mPreferredHistoryDurationMs;
        this.mBatteryTimeRemainingMs = builder.mBatteryTimeRemainingMs;
        this.mChargeTimeRemainingMs = builder.mChargeTimeRemainingMs;
        this.mCustomPowerComponentNames = builder.mCustomPowerComponentNames;
        this.mIncludesProcessStateData = builder.mIncludesProcessStateData;
        this.mIncludesScreenStateData = builder.mIncludesScreenStateData;
        this.mIncludesPowerStateData = builder.mIncludesPowerStateData;
        this.mBatteryConsumerDataLayout = builder.mBatteryConsumerDataLayout;
        this.mBatteryConsumersCursorWindow = builder.mBatteryConsumersCursorWindow;
        int size = builder.mUidBatteryConsumerBuilders.size();
        this.mUidBatteryConsumers = new ArrayList(size);
        double d = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        for (int i = 0; i < size; i++) {
            UidBatteryConsumer.Builder builder2 = (UidBatteryConsumer.Builder) builder.mUidBatteryConsumerBuilders.valueAt(i);
            if (!builder2.isExcludedFromBatteryUsageStats()) {
                UidBatteryConsumer build = builder2.build();
                d += build.getConsumedPower();
                this.mUidBatteryConsumers.add(build);
            }
        }
        int size2 = builder.mUserBatteryConsumerBuilders.size();
        this.mUserBatteryConsumers = new ArrayList(size2);
        for (int i2 = 0; i2 < size2; i2++) {
            UserBatteryConsumer build2 = ((UserBatteryConsumer.Builder) builder.mUserBatteryConsumerBuilders.valueAt(i2)).build();
            d += build2.getConsumedPower();
            this.mUserBatteryConsumers.add(build2);
        }
        builder.getAggregateBatteryConsumerBuilder(1).addConsumedPower(d);
        this.mAggregateBatteryConsumers = new AggregateBatteryConsumer[2];
        for (int i3 = 0; i3 < 2; i3++) {
            this.mAggregateBatteryConsumers[i3] = builder.mAggregateBatteryConsumersBuilders[i3].build();
        }
    }

    public long getStatsStartTimestamp() {
        return this.mStatsStartTimestampMs;
    }

    public long getStatsEndTimestamp() {
        return this.mStatsEndTimestampMs;
    }

    public long getStatsDuration() {
        return this.mStatsDurationMs;
    }

    public double getConsumedPower() {
        return this.mAggregateBatteryConsumers[0].getConsumedPower();
    }

    public double getBatteryCapacity() {
        return this.mBatteryCapacityMah;
    }

    public double getBatteryRatedCapacity() {
        return this.mBatteryRatedCapacityMah;
    }

    public double getBatteryTypicalCapacity() {
        return this.mBatteryTypicalCapacityMah;
    }

    public int getDischargePercentage() {
        return this.mDischargePercentage;
    }

    public Range<Double> getDischargedPowerRange() {
        return Range.create(Double.valueOf(this.mDischargedPowerLowerBound), Double.valueOf(this.mDischargedPowerUpperBound));
    }

    public long getDischargeDurationMs() {
        return this.mDischargeDurationMs;
    }

    public long getBatteryTimeRemainingMs() {
        return this.mBatteryTimeRemainingMs;
    }

    public long getChargeTimeRemainingMs() {
        return this.mChargeTimeRemainingMs;
    }

    public AggregateBatteryConsumer getAggregateBatteryConsumer(int i) {
        return this.mAggregateBatteryConsumers[i];
    }

    public List<UidBatteryConsumer> getUidBatteryConsumers() {
        return this.mUidBatteryConsumers;
    }

    public List<UserBatteryConsumer> getUserBatteryConsumers() {
        return this.mUserBatteryConsumers;
    }

    public String[] getCustomPowerComponentNames() {
        return this.mCustomPowerComponentNames;
    }

    public boolean isProcessStateDataIncluded() {
        return this.mIncludesProcessStateData;
    }

    public BatteryStatsHistoryIterator iterateBatteryStatsHistory() {
        if (this.mBatteryStatsHistory == null) {
            throw new IllegalStateException("Battery history was not requested in the BatteryUsageStatsQuery");
        }
        return new BatteryStatsHistoryIterator(this.mBatteryStatsHistory, 0L, -1L);
    }

    private BatteryUsageStats(Parcel parcel) {
        this.mStatsStartTimestampMs = parcel.readLong();
        this.mStatsEndTimestampMs = parcel.readLong();
        this.mStatsDurationMs = parcel.readLong();
        this.mBatteryCapacityMah = parcel.readDouble();
        this.mDischargePercentage = parcel.readInt();
        this.mDischargedPowerLowerBound = parcel.readDouble();
        this.mDischargedPowerUpperBound = parcel.readDouble();
        this.mDischargeDurationMs = parcel.readLong();
        this.mBatteryTimeRemainingMs = parcel.readLong();
        this.mChargeTimeRemainingMs = parcel.readLong();
        String[] readStringArray = parcel.readStringArray();
        this.mCustomPowerComponentNames = readStringArray;
        boolean readBoolean = parcel.readBoolean();
        this.mIncludesProcessStateData = readBoolean;
        boolean readBoolean2 = parcel.readBoolean();
        this.mIncludesScreenStateData = readBoolean2;
        boolean readBoolean3 = parcel.readBoolean();
        this.mIncludesPowerStateData = readBoolean3;
        this.mBatteryConsumersCursorWindow = CursorWindow.newFromParcel(parcel);
        this.mBatteryConsumerDataLayout = BatteryConsumer.createBatteryConsumerDataLayout(readStringArray, readBoolean, readBoolean2, readBoolean3);
        int numRows = this.mBatteryConsumersCursorWindow.getNumRows();
        this.mAggregateBatteryConsumers = new AggregateBatteryConsumer[2];
        this.mUidBatteryConsumers = new ArrayList(numRows);
        this.mUserBatteryConsumers = new ArrayList();
        for (int i = 0; i < numRows; i++) {
            BatteryConsumer.BatteryConsumerData batteryConsumerData = new BatteryConsumer.BatteryConsumerData(this.mBatteryConsumersCursorWindow, i, this.mBatteryConsumerDataLayout);
            int i2 = this.mBatteryConsumersCursorWindow.getInt(i, 0);
            if (i2 == 0) {
                AggregateBatteryConsumer aggregateBatteryConsumer = new AggregateBatteryConsumer(batteryConsumerData);
                this.mAggregateBatteryConsumers[aggregateBatteryConsumer.getScope()] = aggregateBatteryConsumer;
            } else if (i2 == 1) {
                this.mUidBatteryConsumers.add(new UidBatteryConsumer(batteryConsumerData));
            } else if (i2 == 2) {
                this.mUserBatteryConsumers.add(new UserBatteryConsumer(batteryConsumerData));
            }
        }
        if (parcel.readBoolean()) {
            this.mBatteryStatsHistory = BatteryStatsHistory.createFromBatteryUsageStatsParcel(parcel);
            this.mPreferredHistoryDurationMs = parcel.readLong();
        } else {
            this.mBatteryStatsHistory = null;
            this.mPreferredHistoryDurationMs = 0L;
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.mStatsStartTimestampMs);
        parcel.writeLong(this.mStatsEndTimestampMs);
        parcel.writeLong(this.mStatsDurationMs);
        parcel.writeDouble(this.mBatteryCapacityMah);
        parcel.writeInt(this.mDischargePercentage);
        parcel.writeDouble(this.mDischargedPowerLowerBound);
        parcel.writeDouble(this.mDischargedPowerUpperBound);
        parcel.writeLong(this.mDischargeDurationMs);
        parcel.writeLong(this.mBatteryTimeRemainingMs);
        parcel.writeLong(this.mChargeTimeRemainingMs);
        parcel.writeStringArray(this.mCustomPowerComponentNames);
        parcel.writeBoolean(this.mIncludesProcessStateData);
        parcel.writeBoolean(this.mIncludesScreenStateData);
        parcel.writeBoolean(this.mIncludesPowerStateData);
        this.mBatteryConsumersCursorWindow.writeToParcel(parcel, i);
        if (this.mBatteryStatsHistory != null) {
            parcel.writeBoolean(true);
            this.mBatteryStatsHistory.writeToBatteryUsageStatsParcel(parcel, this.mPreferredHistoryDurationMs);
        } else {
            parcel.writeBoolean(false);
        }
    }

    public byte[] getStatsProto() {
        int i = 78750;
        for (int i2 = 0; i2 < 3; i2++) {
            ProtoOutputStream protoOutputStream = new ProtoOutputStream();
            writeStatsProto(protoOutputStream, i);
            int rawSize = protoOutputStream.getRawSize();
            byte[] bytes = protoOutputStream.getBytes();
            if (bytes.length <= STATSD_PULL_ATOM_MAX_BYTES) {
                return bytes;
            }
            i = (int) (((rawSize * 45000) / bytes.length) - 1024);
        }
        ProtoOutputStream protoOutputStream2 = new ProtoOutputStream();
        writeStatsProto(protoOutputStream2, STATSD_PULL_ATOM_MAX_BYTES);
        return protoOutputStream2.getBytes();
    }

    public void dumpToProto(FileDescriptor fileDescriptor) {
        ProtoOutputStream protoOutputStream = new ProtoOutputStream(fileDescriptor);
        writeStatsProto(protoOutputStream, Integer.MAX_VALUE);
        protoOutputStream.flush();
    }

    private void writeStatsProto(ProtoOutputStream protoOutputStream, int i) {
        AggregateBatteryConsumer aggregateBatteryConsumer = getAggregateBatteryConsumer(0);
        protoOutputStream.write(1112396529665L, getStatsStartTimestamp());
        protoOutputStream.write(1112396529666L, getStatsEndTimestamp());
        protoOutputStream.write(1112396529667L, getStatsDuration());
        protoOutputStream.write(1120986464262L, getDischargePercentage());
        protoOutputStream.write(1112396529671L, getDischargeDurationMs());
        aggregateBatteryConsumer.writeStatsProto(protoOutputStream, 1146756268036L);
        writeUidBatteryConsumersProto(protoOutputStream, i);
    }

    private void writeUidBatteryConsumersProto(ProtoOutputStream protoOutputStream, int i) {
        int i2;
        List<UidBatteryConsumer> list;
        int i3;
        List<UidBatteryConsumer> list2;
        int i4;
        List<UidBatteryConsumer> uidBatteryConsumers = getUidBatteryConsumers();
        uidBatteryConsumers.sort(Comparator.comparingDouble(new ToDoubleFunction() { // from class: android.os.BatteryUsageStats$$ExternalSyntheticLambda0
            @Override // java.util.function.ToDoubleFunction
            public final double applyAsDouble(Object obj) {
                double uidBatteryConsumerWeight;
                uidBatteryConsumerWeight = BatteryUsageStats.this.getUidBatteryConsumerWeight((UidBatteryConsumer) obj);
                return uidBatteryConsumerWeight;
            }
        }).reversed());
        int size = uidBatteryConsumers.size();
        int i5 = 0;
        int i6 = 0;
        while (i6 < size) {
            UidBatteryConsumer uidBatteryConsumer = uidBatteryConsumers.get(i6);
            long timeInStateMs = uidBatteryConsumer.getTimeInStateMs(i5);
            long timeInStateMs2 = uidBatteryConsumer.getTimeInStateMs(1);
            boolean hasStatsProtoData = uidBatteryConsumer.hasStatsProtoData();
            if (timeInStateMs == 0 && timeInStateMs2 == 0 && !hasStatsProtoData) {
                list = uidBatteryConsumers;
                i3 = size;
                i2 = i6;
            } else {
                i2 = i6;
                long start = protoOutputStream.start(2246267895813L);
                protoOutputStream.write(1120986464257L, uidBatteryConsumer.getUid());
                if (hasStatsProtoData) {
                    uidBatteryConsumer.writeStatsProto(protoOutputStream, 1146756268034L);
                }
                protoOutputStream.write(1112396529667L, timeInStateMs);
                protoOutputStream.write(1112396529668L, timeInStateMs2);
                int[] iArr = UID_USAGE_TIME_PROCESS_STATES;
                int length = iArr.length;
                int i7 = 0;
                while (i7 < length) {
                    int i8 = iArr[i7];
                    long timeInProcessStateMs = uidBatteryConsumer.getTimeInProcessStateMs(i8);
                    if (timeInProcessStateMs <= 0) {
                        list2 = uidBatteryConsumers;
                        i4 = size;
                    } else {
                        list2 = uidBatteryConsumers;
                        i4 = size;
                        long start2 = protoOutputStream.start(2246267895813L);
                        protoOutputStream.write(1159641169921L, i8);
                        protoOutputStream.write(1112396529666L, timeInProcessStateMs);
                        protoOutputStream.end(start2);
                    }
                    i7++;
                    uidBatteryConsumers = list2;
                    size = i4;
                }
                list = uidBatteryConsumers;
                i3 = size;
                protoOutputStream.end(start);
                if (protoOutputStream.getRawSize() >= i) {
                    return;
                }
            }
            i6 = i2 + 1;
            uidBatteryConsumers = list;
            size = i3;
            i5 = 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public double getUidBatteryConsumerWeight(UidBatteryConsumer uidBatteryConsumer) {
        return (uidBatteryConsumer.getConsumedPower() * WEIGHT_CONSUMED_POWER) + (uidBatteryConsumer.getTimeInStateMs(0) * WEIGHT_FOREGROUND_STATE) + (uidBatteryConsumer.getTimeInStateMs(1) * WEIGHT_BACKGROUND_STATE);
    }

    public void dump(PrintWriter printWriter, String str) {
        printWriter.print(str);
        printWriter.println("  Estimated power use (mAh):");
        printWriter.print(str);
        printWriter.print("    Capacity: ");
        printWriter.print(BatteryStats.formatCharge(getBatteryCapacity()));
        printWriter.print(", Rated: ");
        printWriter.print(BatteryStats.formatCharge(getBatteryRatedCapacity()));
        printWriter.print(", Typical: ");
        printWriter.print(BatteryStats.formatCharge(getBatteryTypicalCapacity()));
        printWriter.print(", Computed drain: ");
        printWriter.print(BatteryStats.formatCharge(getConsumedPower()));
        Range<Double> dischargedPowerRange = getDischargedPowerRange();
        printWriter.print(", actual drain: ");
        printWriter.print(BatteryStats.formatCharge(dischargedPowerRange.getLower().doubleValue()));
        if (!dischargedPowerRange.getLower().equals(dischargedPowerRange.getUpper())) {
            printWriter.print(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
            printWriter.print(BatteryStats.formatCharge(dischargedPowerRange.getUpper().doubleValue()));
        }
        printWriter.println();
        printWriter.println("    Global");
        AggregateBatteryConsumer aggregateBatteryConsumer = getAggregateBatteryConsumer(0);
        AggregateBatteryConsumer aggregateBatteryConsumer2 = getAggregateBatteryConsumer(1);
        for (int i : this.mBatteryConsumerDataLayout.powerComponentIds) {
            double consumedPower = aggregateBatteryConsumer.getConsumedPower(i);
            double consumedPower2 = aggregateBatteryConsumer2.getConsumedPower(i);
            if (consumedPower != SContextConstants.ENVIRONMENT_VALUE_UNKNOWN || consumedPower2 != SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
                printPowerComponent(printWriter, str, this.mBatteryConsumerDataLayout.getPowerComponentName(i), consumedPower, consumedPower2, aggregateBatteryConsumer.getUsageDurationMillis(i));
            }
        }
        String str2 = str + "  ";
        boolean z = this.mIncludesPowerStateData;
        if (z && !this.mIncludesScreenStateData) {
            for (int i2 = 0; i2 < 3; i2++) {
                if (i2 != 0) {
                    dumpPowerComponents(printWriter, 0, i2, str2);
                }
            }
        } else if (!z && this.mIncludesScreenStateData) {
            for (int i3 = 0; i3 < 3; i3++) {
                if (i3 != 0) {
                    dumpPowerComponents(printWriter, i3, 0, str2);
                }
            }
        } else if (z && this.mIncludesScreenStateData) {
            for (int i4 = 0; i4 < 3; i4++) {
                if (i4 != 0) {
                    for (int i5 = 0; i5 < 3; i5++) {
                        if (i5 != 0) {
                            dumpPowerComponents(printWriter, i5, i4, str2);
                        }
                    }
                }
            }
        }
        dumpSortedBatteryConsumers(printWriter, str, getUidBatteryConsumers());
        dumpSortedBatteryConsumers(printWriter, str, getUserBatteryConsumers());
        printWriter.println();
    }

    private void dumpPowerComponents(PrintWriter printWriter, int i, int i2, String str) {
        PrintWriter printWriter2;
        boolean z;
        int i3;
        int[] iArr;
        int i4;
        boolean z2;
        BatteryUsageStats batteryUsageStats = this;
        AggregateBatteryConsumer aggregateBatteryConsumer = batteryUsageStats.getAggregateBatteryConsumer(0);
        AggregateBatteryConsumer aggregateBatteryConsumer2 = batteryUsageStats.getAggregateBatteryConsumer(1);
        int[] iArr2 = batteryUsageStats.mBatteryConsumerDataLayout.powerComponentIds;
        int length = iArr2.length;
        int i5 = 0;
        boolean z3 = false;
        while (i5 < length) {
            int i6 = iArr2[i5];
            BatteryConsumer.Dimensions dimensions = new BatteryConsumer.Dimensions(i6, -1, i, i2);
            double consumedPower = aggregateBatteryConsumer.getConsumedPower(dimensions);
            double consumedPower2 = aggregateBatteryConsumer2.getConsumedPower(dimensions);
            if (consumedPower == SContextConstants.ENVIRONMENT_VALUE_UNKNOWN && consumedPower2 == SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
                iArr = iArr2;
                i4 = length;
                i3 = i5;
            } else {
                if (!z3) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("      (");
                    if (i2 != 0) {
                        sb.append(BatteryConsumer.powerStateToString(i2));
                        z2 = false;
                    } else {
                        z2 = true;
                    }
                    if (i != 0) {
                        if (!z2) {
                            sb.append(", ");
                        }
                        sb.append("screen ");
                        sb.append(BatteryConsumer.screenStateToString(i));
                        z2 = false;
                    }
                    if (!z2) {
                        sb.append(NavigationBarInflaterView.KEY_CODE_END);
                        printWriter2 = printWriter;
                        printWriter2.println(sb);
                        z = true;
                        i3 = i5;
                        PrintWriter printWriter3 = printWriter2;
                        iArr = iArr2;
                        i4 = length;
                        batteryUsageStats.printPowerComponent(printWriter3, str, batteryUsageStats.mBatteryConsumerDataLayout.getPowerComponentName(i6), consumedPower, consumedPower2, aggregateBatteryConsumer.getUsageDurationMillis(dimensions));
                        z3 = z;
                    }
                }
                printWriter2 = printWriter;
                z = z3;
                i3 = i5;
                PrintWriter printWriter32 = printWriter2;
                iArr = iArr2;
                i4 = length;
                batteryUsageStats.printPowerComponent(printWriter32, str, batteryUsageStats.mBatteryConsumerDataLayout.getPowerComponentName(i6), consumedPower, consumedPower2, aggregateBatteryConsumer.getUsageDurationMillis(dimensions));
                z3 = z;
            }
            i5 = i3 + 1;
            batteryUsageStats = this;
            iArr2 = iArr;
            length = i4;
        }
    }

    private void printPowerComponent(PrintWriter printWriter, String str, String str2, double d, double d2, long j) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("    ");
        sb.append(str2);
        sb.append(": ");
        sb.append(BatteryStats.formatCharge(d));
        sb.append(" apps: ");
        sb.append(BatteryStats.formatCharge(d2));
        if (j != 0) {
            sb.append(" duration: ");
            BatteryStats.formatTimeMs(sb, j);
        }
        printWriter.println(sb);
    }

    private void dumpSortedBatteryConsumers(PrintWriter printWriter, String str, List<? extends BatteryConsumer> list) {
        list.sort(Comparator.comparingDouble(new ToDoubleFunction() { // from class: android.os.BatteryUsageStats$$ExternalSyntheticLambda1
            @Override // java.util.function.ToDoubleFunction
            public final double applyAsDouble(Object obj) {
                return ((BatteryConsumer) obj).getConsumedPower();
            }
        }).reversed());
        for (BatteryConsumer batteryConsumer : list) {
            if (batteryConsumer.getConsumedPower() != SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
                printWriter.print(str);
                printWriter.print("  ");
                batteryConsumer.dump(printWriter);
            }
        }
    }

    public void writeXml(TypedXmlSerializer typedXmlSerializer) throws IOException {
        typedXmlSerializer.startTag(null, XML_TAG_BATTERY_USAGE_STATS);
        for (int i = 0; i < this.mCustomPowerComponentNames.length; i++) {
            typedXmlSerializer.attribute(null, XML_ATTR_PREFIX_CUSTOM_COMPONENT + i, this.mCustomPowerComponentNames[i]);
        }
        typedXmlSerializer.attributeBoolean(null, XML_ATTR_PREFIX_INCLUDES_PROC_STATE_DATA, this.mIncludesProcessStateData);
        typedXmlSerializer.attributeBoolean(null, XML_ATTR_PREFIX_INCLUDES_SCREEN_STATE_DATA, this.mIncludesScreenStateData);
        typedXmlSerializer.attributeBoolean(null, XML_ATTR_PREFIX_INCLUDES_POWER_STATE_DATA, this.mIncludesPowerStateData);
        typedXmlSerializer.attributeLong(null, XML_ATTR_START_TIMESTAMP, this.mStatsStartTimestampMs);
        typedXmlSerializer.attributeLong(null, XML_ATTR_END_TIMESTAMP, this.mStatsEndTimestampMs);
        typedXmlSerializer.attributeLong(null, "duration", this.mStatsDurationMs);
        typedXmlSerializer.attributeDouble(null, XML_ATTR_BATTERY_CAPACITY, this.mBatteryCapacityMah);
        typedXmlSerializer.attributeInt(null, XML_ATTR_DISCHARGE_PERCENT, this.mDischargePercentage);
        typedXmlSerializer.attributeDouble(null, XML_ATTR_DISCHARGE_LOWER, this.mDischargedPowerLowerBound);
        typedXmlSerializer.attributeDouble(null, XML_ATTR_DISCHARGE_UPPER, this.mDischargedPowerUpperBound);
        typedXmlSerializer.attributeLong(null, XML_ATTR_DISCHARGE_DURATION, this.mDischargeDurationMs);
        typedXmlSerializer.attributeLong(null, XML_ATTR_BATTERY_REMAINING, this.mBatteryTimeRemainingMs);
        typedXmlSerializer.attributeLong(null, XML_ATTR_CHARGE_REMAINING, this.mChargeTimeRemainingMs);
        for (int i2 = 0; i2 < 2; i2++) {
            this.mAggregateBatteryConsumers[i2].writeToXml(typedXmlSerializer, i2);
        }
        Iterator<UidBatteryConsumer> it = this.mUidBatteryConsumers.iterator();
        while (it.hasNext()) {
            it.next().writeToXml(typedXmlSerializer);
        }
        Iterator<UserBatteryConsumer> it2 = this.mUserBatteryConsumers.iterator();
        while (it2.hasNext()) {
            it2.next().writeToXml(typedXmlSerializer);
        }
        typedXmlSerializer.endTag(null, XML_TAG_BATTERY_USAGE_STATS);
    }

    public static BatteryUsageStats createFromXml(TypedXmlPullParser typedXmlPullParser) throws XmlPullParserException, IOException {
        return createBuilderFromXml(typedXmlPullParser).build();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static Builder createBuilderFromXml(TypedXmlPullParser typedXmlPullParser) throws XmlPullParserException, IOException {
        Builder builder;
        boolean z;
        int eventType = typedXmlPullParser.getEventType();
        while (true) {
            builder = null;
            if (eventType == 1) {
                break;
            }
            if (eventType == 2 && typedXmlPullParser.getName().equals(XML_TAG_BATTERY_USAGE_STATS)) {
                ArrayList arrayList = new ArrayList();
                int i = 0;
                while (true) {
                    int attributeIndex = typedXmlPullParser.getAttributeIndex(null, XML_ATTR_PREFIX_CUSTOM_COMPONENT + i);
                    if (attributeIndex == -1) {
                        break;
                    }
                    arrayList.add(typedXmlPullParser.getAttributeValue(attributeIndex));
                    i++;
                }
                Builder builder2 = new Builder((String[]) arrayList.toArray(new String[0]), typedXmlPullParser.getAttributeBoolean(null, XML_ATTR_PREFIX_INCLUDES_PROC_STATE_DATA, false), typedXmlPullParser.getAttributeBoolean(null, XML_ATTR_PREFIX_INCLUDES_SCREEN_STATE_DATA, false), typedXmlPullParser.getAttributeBoolean(null, XML_ATTR_PREFIX_INCLUDES_POWER_STATE_DATA, false), SContextConstants.ENVIRONMENT_VALUE_UNKNOWN);
                builder2.setStatsStartTimestamp(typedXmlPullParser.getAttributeLong(null, XML_ATTR_START_TIMESTAMP));
                builder2.setStatsEndTimestamp(typedXmlPullParser.getAttributeLong(null, XML_ATTR_END_TIMESTAMP));
                builder2.setStatsDuration(typedXmlPullParser.getAttributeLong(null, "duration"));
                builder2.setBatteryCapacity(typedXmlPullParser.getAttributeDouble(null, XML_ATTR_BATTERY_CAPACITY));
                builder2.addDischargePercentage(typedXmlPullParser.getAttributeInt(null, XML_ATTR_DISCHARGE_PERCENT));
                builder2.addDischargedPowerRange(typedXmlPullParser.getAttributeDouble(null, XML_ATTR_DISCHARGE_LOWER), typedXmlPullParser.getAttributeDouble(null, XML_ATTR_DISCHARGE_UPPER));
                builder2.addDischargeDurationMs(typedXmlPullParser.getAttributeLong(null, XML_ATTR_DISCHARGE_DURATION));
                builder2.setBatteryTimeRemainingMs(typedXmlPullParser.getAttributeLong(null, XML_ATTR_BATTERY_REMAINING));
                builder2.setChargeTimeRemainingMs(typedXmlPullParser.getAttributeLong(null, XML_ATTR_CHARGE_REMAINING));
                eventType = typedXmlPullParser.next();
                builder = builder2;
            } else {
                eventType = typedXmlPullParser.next();
            }
        }
        if (builder == null) {
            throw new XmlPullParserException("No root element");
        }
        while (eventType != 1) {
            if (eventType == 2) {
                String name = typedXmlPullParser.getName();
                name.hashCode();
                switch (name.hashCode()) {
                    case 115792:
                        if (name.equals("uid")) {
                            z = false;
                            break;
                        }
                        z = -1;
                        break;
                    case 3599307:
                        if (name.equals("user")) {
                            z = true;
                            break;
                        }
                        z = -1;
                        break;
                    case 175177151:
                        if (name.equals(XML_TAG_AGGREGATE)) {
                            z = 2;
                            break;
                        }
                        z = -1;
                        break;
                    default:
                        z = -1;
                        break;
                }
                switch (z) {
                    case false:
                        UidBatteryConsumer.createFromXml(typedXmlPullParser, builder);
                        break;
                    case true:
                        UserBatteryConsumer.createFromXml(typedXmlPullParser, builder);
                        break;
                    case true:
                        AggregateBatteryConsumer.parseXml(typedXmlPullParser, builder);
                        break;
                }
            }
            eventType = typedXmlPullParser.next();
        }
        return builder;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        onCursorWindowReleased(this.mBatteryConsumersCursorWindow);
        this.mBatteryConsumersCursorWindow.close();
        this.mBatteryConsumersCursorWindow = null;
    }

    protected void finalize() throws Throwable {
        CursorWindow cursorWindow = this.mBatteryConsumersCursorWindow;
        if (cursorWindow != null) {
            cursorWindow.close();
        }
        super.finalize();
    }

    public String toString() {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        dump(printWriter, "");
        printWriter.flush();
        return stringWriter.toString();
    }

    public static final class Builder {
        private final AggregateBatteryConsumer.Builder[] mAggregateBatteryConsumersBuilders;
        private double mBatteryCapacityMah;
        private final BatteryConsumer.BatteryConsumerDataLayout mBatteryConsumerDataLayout;
        private final CursorWindow mBatteryConsumersCursorWindow;
        private double mBatteryRatedCapacityMah;
        private BatteryStatsHistory mBatteryStatsHistory;
        private long mBatteryTimeRemainingMs;
        private double mBatteryTypicalCapacityMah;
        private long mChargeTimeRemainingMs;
        private final String[] mCustomPowerComponentNames;
        private long mDischargeDurationMs;
        private int mDischargePercentage;
        private double mDischargedPowerLowerBoundMah;
        private double mDischargedPowerUpperBoundMah;
        private final boolean mIncludesPowerStateData;
        private final boolean mIncludesProcessStateData;
        private final boolean mIncludesScreenStateData;
        private final double mMinConsumedPowerThreshold;
        private long mPreferredHistoryDurationMs;
        private long mStatsDurationMs;
        private long mStatsEndTimestampMs;
        private long mStatsStartTimestampMs;
        private final SparseArray<UidBatteryConsumer.Builder> mUidBatteryConsumerBuilders;
        private final SparseArray<UserBatteryConsumer.Builder> mUserBatteryConsumerBuilders;

        public Builder(String[] strArr) {
            this(strArr, false, false, false, SContextConstants.ENVIRONMENT_VALUE_UNKNOWN);
        }

        public Builder(String[] strArr, boolean z, boolean z2, boolean z3, double d) {
            this.mStatsDurationMs = -1L;
            this.mBatteryTimeRemainingMs = -1L;
            this.mChargeTimeRemainingMs = -1L;
            this.mAggregateBatteryConsumersBuilders = new AggregateBatteryConsumer.Builder[2];
            this.mUidBatteryConsumerBuilders = new SparseArray<>();
            this.mUserBatteryConsumerBuilders = new SparseArray<>();
            CursorWindow cursorWindow = new CursorWindow((String) null, BatteryUsageStats.BATTERY_CONSUMER_CURSOR_WINDOW_SIZE);
            this.mBatteryConsumersCursorWindow = cursorWindow;
            BatteryUsageStats.onCursorWindowAllocated(cursorWindow);
            BatteryConsumer.BatteryConsumerDataLayout createBatteryConsumerDataLayout = BatteryConsumer.createBatteryConsumerDataLayout(strArr, z, z2, z3);
            this.mBatteryConsumerDataLayout = createBatteryConsumerDataLayout;
            cursorWindow.setNumColumns(createBatteryConsumerDataLayout.columnCount);
            this.mCustomPowerComponentNames = strArr;
            this.mIncludesProcessStateData = z;
            this.mIncludesScreenStateData = z2;
            this.mIncludesPowerStateData = z3;
            this.mMinConsumedPowerThreshold = d;
            for (int i = 0; i < 2; i++) {
                this.mAggregateBatteryConsumersBuilders[i] = new AggregateBatteryConsumer.Builder(BatteryConsumer.BatteryConsumerData.create(this.mBatteryConsumersCursorWindow, this.mBatteryConsumerDataLayout), i, this.mMinConsumedPowerThreshold);
            }
        }

        public boolean isProcessStateDataNeeded() {
            return this.mIncludesProcessStateData;
        }

        public boolean isScreenStateDataNeeded() {
            return this.mIncludesScreenStateData;
        }

        public boolean isPowerStateDataNeeded() {
            return this.mIncludesPowerStateData;
        }

        public boolean isSupportedPowerComponent(int i) {
            if (i >= 20) {
                return i >= 1000 && i < this.mBatteryConsumerDataLayout.customPowerComponentCount + 1000;
            }
            return true;
        }

        public BatteryUsageStats build() {
            if (this.mBatteryConsumersCursorWindow == null) {
                throw new IllegalStateException("Builder has been discarded");
            }
            return new BatteryUsageStats(this);
        }

        public void discard() {
            this.mBatteryConsumersCursorWindow.close();
            BatteryUsageStats.onCursorWindowReleased(this.mBatteryConsumersCursorWindow);
        }

        public Builder setBatteryCapacity(double d) {
            this.mBatteryCapacityMah = d;
            return this;
        }

        public Builder setBatteryRatedCapacity(double d) {
            this.mBatteryRatedCapacityMah = d;
            return this;
        }

        public Builder setBatteryTypicalCapacity(double d) {
            this.mBatteryTypicalCapacityMah = d;
            return this;
        }

        public Builder setStatsStartTimestamp(long j) {
            this.mStatsStartTimestampMs = j;
            return this;
        }

        public Builder setStatsEndTimestamp(long j) {
            this.mStatsEndTimestampMs = j;
            return this;
        }

        public Builder setStatsDuration(long j) {
            this.mStatsDurationMs = j;
            return this;
        }

        public long getStatsDuration() {
            long j = this.mStatsDurationMs;
            return j != -1 ? j : this.mStatsEndTimestampMs - this.mStatsStartTimestampMs;
        }

        public Builder addDischargePercentage(int i) {
            this.mDischargePercentage += i;
            return this;
        }

        public Builder addDischargedPowerRange(double d, double d2) {
            this.mDischargedPowerLowerBoundMah += d;
            this.mDischargedPowerUpperBoundMah += d2;
            return this;
        }

        public Builder addDischargeDurationMs(long j) {
            this.mDischargeDurationMs += j;
            return this;
        }

        public Builder setBatteryTimeRemainingMs(long j) {
            this.mBatteryTimeRemainingMs = j;
            return this;
        }

        public Builder setChargeTimeRemainingMs(long j) {
            this.mChargeTimeRemainingMs = j;
            return this;
        }

        public Builder setBatteryHistory(BatteryStatsHistory batteryStatsHistory, long j) {
            this.mBatteryStatsHistory = batteryStatsHistory;
            this.mPreferredHistoryDurationMs = j;
            return this;
        }

        public AggregateBatteryConsumer.Builder getAggregateBatteryConsumerBuilder(int i) {
            return this.mAggregateBatteryConsumersBuilders[i];
        }

        public UidBatteryConsumer.Builder getOrCreateUidBatteryConsumerBuilder(BatteryStats.Uid uid) {
            int uid2 = uid.getUid();
            UidBatteryConsumer.Builder builder = this.mUidBatteryConsumerBuilders.get(uid2);
            if (builder != null) {
                return builder;
            }
            UidBatteryConsumer.Builder builder2 = new UidBatteryConsumer.Builder(BatteryConsumer.BatteryConsumerData.create(this.mBatteryConsumersCursorWindow, this.mBatteryConsumerDataLayout), uid, this.mMinConsumedPowerThreshold);
            this.mUidBatteryConsumerBuilders.put(uid2, builder2);
            return builder2;
        }

        public UidBatteryConsumer.Builder getOrCreateUidBatteryConsumerBuilder(int i) {
            UidBatteryConsumer.Builder builder = this.mUidBatteryConsumerBuilders.get(i);
            if (builder != null) {
                return builder;
            }
            UidBatteryConsumer.Builder builder2 = new UidBatteryConsumer.Builder(BatteryConsumer.BatteryConsumerData.create(this.mBatteryConsumersCursorWindow, this.mBatteryConsumerDataLayout), i, this.mMinConsumedPowerThreshold);
            this.mUidBatteryConsumerBuilders.put(i, builder2);
            return builder2;
        }

        public UserBatteryConsumer.Builder getOrCreateUserBatteryConsumerBuilder(int i) {
            UserBatteryConsumer.Builder builder = this.mUserBatteryConsumerBuilders.get(i);
            if (builder != null) {
                return builder;
            }
            UserBatteryConsumer.Builder builder2 = new UserBatteryConsumer.Builder(BatteryConsumer.BatteryConsumerData.create(this.mBatteryConsumersCursorWindow, this.mBatteryConsumerDataLayout), i, this.mMinConsumedPowerThreshold);
            this.mUserBatteryConsumerBuilders.put(i, builder2);
            return builder2;
        }

        public SparseArray<UidBatteryConsumer.Builder> getUidBatteryConsumerBuilders() {
            return this.mUidBatteryConsumerBuilders;
        }

        public Builder add(BatteryUsageStats batteryUsageStats) {
            if (!Arrays.equals(this.mCustomPowerComponentNames, batteryUsageStats.mCustomPowerComponentNames)) {
                throw new IllegalArgumentException("BatteryUsageStats have different custom power components");
            }
            if (this.mIncludesProcessStateData && !batteryUsageStats.mIncludesProcessStateData) {
                throw new IllegalArgumentException("Added BatteryUsageStats does not include process state data");
            }
            if (this.mUserBatteryConsumerBuilders.size() != 0 || !batteryUsageStats.getUserBatteryConsumers().isEmpty()) {
                throw new UnsupportedOperationException("Combining UserBatteryConsumers is not supported");
            }
            this.mDischargedPowerLowerBoundMah += batteryUsageStats.mDischargedPowerLowerBound;
            this.mDischargedPowerUpperBoundMah += batteryUsageStats.mDischargedPowerUpperBound;
            this.mDischargePercentage += batteryUsageStats.mDischargePercentage;
            this.mDischargeDurationMs += batteryUsageStats.mDischargeDurationMs;
            this.mStatsDurationMs = getStatsDuration() + batteryUsageStats.getStatsDuration();
            if (this.mStatsStartTimestampMs == 0 || batteryUsageStats.mStatsStartTimestampMs < this.mStatsStartTimestampMs) {
                this.mStatsStartTimestampMs = batteryUsageStats.mStatsStartTimestampMs;
            }
            boolean z = batteryUsageStats.mStatsEndTimestampMs > this.mStatsEndTimestampMs;
            if (z) {
                this.mStatsEndTimestampMs = batteryUsageStats.mStatsEndTimestampMs;
            }
            for (int i = 0; i < 2; i++) {
                getAggregateBatteryConsumerBuilder(i).add(batteryUsageStats.mAggregateBatteryConsumers[i]);
            }
            for (UidBatteryConsumer uidBatteryConsumer : batteryUsageStats.getUidBatteryConsumers()) {
                getOrCreateUidBatteryConsumerBuilder(uidBatteryConsumer.getUid()).add(uidBatteryConsumer);
            }
            if (z) {
                this.mBatteryCapacityMah = batteryUsageStats.mBatteryCapacityMah;
                this.mBatteryTimeRemainingMs = batteryUsageStats.mBatteryTimeRemainingMs;
                this.mChargeTimeRemainingMs = batteryUsageStats.mChargeTimeRemainingMs;
            }
            return this;
        }

        void dump(PrintWriter printWriter) {
            int numRows = this.mBatteryConsumersCursorWindow.getNumRows();
            int i = this.mBatteryConsumerDataLayout.columnCount;
            for (int i2 = 0; i2 < numRows; i2++) {
                StringBuilder sb = new StringBuilder();
                for (int i3 = 0; i3 < i; i3++) {
                    int type = this.mBatteryConsumersCursorWindow.getType(i2, i3);
                    if (type == 0) {
                        sb.append("null, ");
                    } else if (type == 1) {
                        sb.append(this.mBatteryConsumersCursorWindow.getInt(i2, i3));
                        sb.append(", ");
                    } else if (type == 2) {
                        sb.append(this.mBatteryConsumersCursorWindow.getFloat(i2, i3));
                        sb.append(", ");
                    } else if (type == 3) {
                        sb.append(this.mBatteryConsumersCursorWindow.getString(i2, i3));
                        sb.append(", ");
                    } else if (type == 4) {
                        sb.append("BLOB, ");
                    }
                }
                sb.setLength(sb.length() - 2);
                printWriter.println(sb);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void onCursorWindowAllocated(CursorWindow cursorWindow) {
        if (sInstanceLeakDetectionEnabled) {
            synchronized (BatteryUsageStats.class) {
                if (sInstances == null) {
                    sInstances = new HashMap();
                }
                sInstances.put(cursorWindow, new Exception());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void onCursorWindowReleased(CursorWindow cursorWindow) {
        if (sInstanceLeakDetectionEnabled) {
            synchronized (BatteryUsageStats.class) {
                sInstances.remove(cursorWindow);
            }
        }
    }

    public static void enableInstanceLeakDetection() {
        sInstanceLeakDetectionEnabled = true;
        synchronized (BatteryUsageStats.class) {
            Map<CursorWindow, Exception> map = sInstances;
            if (map != null) {
                map.clear();
            }
        }
    }

    public static void assertAllInstancesClosed() {
        if (!sInstanceLeakDetectionEnabled) {
            throw new IllegalStateException("Instance leak detection is not enabled");
        }
        synchronized (BatteryUsageStats.class) {
            Map<CursorWindow, Exception> map = sInstances;
            if (map != null && !map.isEmpty()) {
                Exception value = sInstances.entrySet().iterator().next().getValue();
                int size = sInstances.size();
                sInstances.clear();
                throw new IllegalStateException("Instances of BatteryUsageStats not closed: " + size, value);
            }
        }
    }
}
