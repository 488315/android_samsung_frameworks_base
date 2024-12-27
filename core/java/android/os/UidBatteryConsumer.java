package android.os;

import android.hardware.scontext.SContextConstants;
import android.text.TextUtils;

import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class UidBatteryConsumer extends BatteryConsumer {
    static final int COLUMN_COUNT = 6;
    static final int COLUMN_INDEX_PACKAGE_WITH_HIGHEST_DRAIN = 2;
    static final int COLUMN_INDEX_TIME_IN_BACKGROUND = 4;
    static final int COLUMN_INDEX_TIME_IN_FOREGROUND = 3;
    static final int COLUMN_INDEX_TIME_IN_FOREGROUND_SERVICE = 5;
    static final int COLUMN_INDEX_UID = 1;
    static final int CONSUMER_TYPE_UID = 1;
    public static final int STATE_BACKGROUND = 1;
    public static final int STATE_FOREGROUND = 0;

    @Retention(RetentionPolicy.SOURCE)
    public @interface State {}

    UidBatteryConsumer(BatteryConsumer.BatteryConsumerData data) {
        super(data);
    }

    private UidBatteryConsumer(Builder builder) {
        super(builder.mData, builder.mPowerComponentsBuilder.build());
    }

    public int getUid() {
        return this.mData.getInt(1);
    }

    public String getPackageWithHighestDrain() {
        return this.mData.getString(2);
    }

    @Deprecated
    public long getTimeInStateMs(int state) {
        switch (state) {
            case 0:
                return this.mData.getInt(3);
            case 1:
                return this.mData.getInt(4) + this.mData.getInt(5);
            default:
                return 0L;
        }
    }

    public long getTimeInProcessStateMs(int state) {
        switch (state) {
            case 1:
                return this.mData.getInt(3);
            case 2:
                return this.mData.getInt(4);
            case 3:
                return this.mData.getInt(5);
            default:
                return 0L;
        }
    }

    @Override // android.os.BatteryConsumer
    public void dump(PrintWriter pw, boolean skipEmptyComponents) {
        pw.print("UID ");
        UserHandle.formatUid(pw, getUid());
        pw.print(": ");
        pw.print(BatteryStats.formatCharge(getConsumedPower()));
        if (this.mData.layout.processStateDataIncluded) {
            StringBuilder sb = new StringBuilder();
            appendProcessStateData(sb, 1, skipEmptyComponents);
            appendProcessStateData(sb, 2, skipEmptyComponents);
            appendProcessStateData(sb, 3, skipEmptyComponents);
            appendProcessStateData(sb, 4, skipEmptyComponents);
            pw.println(sb);
        } else {
            pw.println();
        }
        pw.print("      ");
        this.mPowerComponents.dump(pw, 0, 0, skipEmptyComponents);
        if (this.mData.layout.powerStateDataIncluded || this.mData.layout.screenStateDataIncluded) {
            for (int powerState = 0; powerState < 3; powerState++) {
                if (!this.mData.layout.powerStateDataIncluded || powerState != 0) {
                    for (int screenState = 0; screenState < 3; screenState++) {
                        if (!this.mData.layout.screenStateDataIncluded || screenState != 0) {
                            double consumedPower =
                                    this.mPowerComponents.getConsumedPower(
                                            -1, 0, screenState, powerState);
                            if (consumedPower != SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
                                pw.print("      (");
                                if (powerState != 0) {
                                    pw.print(BatteryConsumer.powerStateToString(powerState));
                                }
                                if (screenState != 0) {
                                    if (powerState != 0) {
                                        pw.print(", ");
                                    }
                                    pw.print("screen ");
                                    pw.print(BatteryConsumer.screenStateToString(screenState));
                                }
                                pw.print(") ");
                                this.mPowerComponents.dump(
                                        pw, screenState, powerState, skipEmptyComponents);
                            }
                        }
                    }
                }
            }
        }
    }

    private void appendProcessStateData(
            StringBuilder sb, int processState, boolean skipEmptyComponents) {
        BatteryConsumer.Dimensions dimensions = new BatteryConsumer.Dimensions(-1, processState);
        double power = this.mPowerComponents.getConsumedPower(dimensions);
        if (power == SContextConstants.ENVIRONMENT_VALUE_UNKNOWN && skipEmptyComponents) {
            return;
        }
        sb.append(" ")
                .append(processStateToString(processState))
                .append(": ")
                .append(BatteryStats.formatCharge(power));
    }

    void writeToXml(TypedXmlSerializer serializer) throws IOException {
        if (getConsumedPower() == SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
            return;
        }
        serializer.startTag(null, "uid");
        serializer.attributeInt(null, "uid", getUid());
        String packageWithHighestDrain = getPackageWithHighestDrain();
        if (!TextUtils.isEmpty(packageWithHighestDrain)) {
            serializer.attribute(null, "highest_drain_package", packageWithHighestDrain);
        }
        serializer.attributeLong(null, "time_in_foreground", getTimeInProcessStateMs(1));
        serializer.attributeLong(null, "time_in_background", getTimeInProcessStateMs(2));
        serializer.attributeLong(null, "time_in_foreground_service", getTimeInProcessStateMs(3));
        this.mPowerComponents.writeToXml(serializer);
        serializer.endTag(null, "uid");
    }

    static void createFromXml(TypedXmlPullParser parser, BatteryUsageStats.Builder builder)
            throws XmlPullParserException, IOException {
        int uid = parser.getAttributeInt(null, "uid");
        Builder consumerBuilder = builder.getOrCreateUidBatteryConsumerBuilder(uid);
        int eventType = parser.getEventType();
        if (eventType != 2 || !parser.getName().equals("uid")) {
            throw new XmlPullParserException("Invalid XML parser state");
        }
        consumerBuilder.setPackageWithHighestDrain(
                parser.getAttributeValue(null, "highest_drain_package"));
        consumerBuilder.setTimeInProcessStateMs(
                1, parser.getAttributeLong(null, "time_in_foreground"));
        consumerBuilder.setTimeInProcessStateMs(
                2, parser.getAttributeLong(null, "time_in_background"));
        consumerBuilder.setTimeInProcessStateMs(
                3, parser.getAttributeLong(null, "time_in_foreground_service"));
        while (true) {
            if ((eventType != 3 || !parser.getName().equals("uid")) && eventType != 1) {
                if (eventType == 2 && parser.getName().equals("power_components")) {
                    PowerComponents.parseXml(parser, consumerBuilder.mPowerComponentsBuilder);
                }
                eventType = parser.next();
            } else {
                return;
            }
        }
    }

    public static final class Builder extends BatteryConsumer.BaseBuilder<Builder> {
        private static final String PACKAGE_NAME_UNINITIALIZED = "";
        private final BatteryStats.Uid mBatteryStatsUid;
        private boolean mExcludeFromBatteryUsageStats;
        private final boolean mIsVirtualUid;
        private String mPackageWithHighestDrain;
        private final int mUid;

        /* JADX WARN: Type inference failed for: r1v1, types: [android.os.BatteryConsumer$BaseBuilder, android.os.UidBatteryConsumer$Builder] */
        @Override // android.os.BatteryConsumer.BaseBuilder
        public /* bridge */ /* synthetic */ Builder addConsumedPower(int i, double d, int i2) {
            return super.addConsumedPower(i, d, i2);
        }

        /* JADX WARN: Type inference failed for: r1v1, types: [android.os.BatteryConsumer$BaseBuilder, android.os.UidBatteryConsumer$Builder] */
        @Override // android.os.BatteryConsumer.BaseBuilder
        public /* bridge */ /* synthetic */ Builder addConsumedPower(
                BatteryConsumer.Key key, double d, int i) {
            return super.addConsumedPower(key, d, i);
        }

        /* JADX WARN: Type inference failed for: r1v1, types: [android.os.BatteryConsumer$BaseBuilder, android.os.UidBatteryConsumer$Builder] */
        @Override // android.os.BatteryConsumer.BaseBuilder
        public /* bridge */ /* synthetic */ Builder addConsumedPowerForCustomComponent(
                int i, double d) {
            return super.addConsumedPowerForCustomComponent(i, d);
        }

        @Override // android.os.BatteryConsumer.BaseBuilder
        public /* bridge */ /* synthetic */ BatteryConsumer.Key getKey(int i, int i2) {
            return super.getKey(i, i2);
        }

        @Override // android.os.BatteryConsumer.BaseBuilder
        public /* bridge */ /* synthetic */ BatteryConsumer.Key getKey(
                int i, int i2, int i3, int i4) {
            return super.getKey(i, i2, i3, i4);
        }

        @Override // android.os.BatteryConsumer.BaseBuilder
        public /* bridge */ /* synthetic */ BatteryConsumer.Key[] getKeys(int i) {
            return super.getKeys(i);
        }

        @Override // android.os.BatteryConsumer.BaseBuilder
        public /* bridge */ /* synthetic */ double getTotalPower() {
            return super.getTotalPower();
        }

        /* JADX WARN: Type inference failed for: r1v1, types: [android.os.BatteryConsumer$BaseBuilder, android.os.UidBatteryConsumer$Builder] */
        @Override // android.os.BatteryConsumer.BaseBuilder
        public /* bridge */ /* synthetic */ Builder setConsumedPower(int i, double d) {
            return super.setConsumedPower(i, d);
        }

        /* JADX WARN: Type inference failed for: r1v1, types: [android.os.BatteryConsumer$BaseBuilder, android.os.UidBatteryConsumer$Builder] */
        @Override // android.os.BatteryConsumer.BaseBuilder
        public /* bridge */ /* synthetic */ Builder setConsumedPower(int i, double d, int i2) {
            return super.setConsumedPower(i, d, i2);
        }

        /* JADX WARN: Type inference failed for: r1v1, types: [android.os.BatteryConsumer$BaseBuilder, android.os.UidBatteryConsumer$Builder] */
        @Override // android.os.BatteryConsumer.BaseBuilder
        public /* bridge */ /* synthetic */ Builder setConsumedPower(
                BatteryConsumer.Key key, double d, int i) {
            return super.setConsumedPower(key, d, i);
        }

        /* JADX WARN: Type inference failed for: r1v1, types: [android.os.BatteryConsumer$BaseBuilder, android.os.UidBatteryConsumer$Builder] */
        @Override // android.os.BatteryConsumer.BaseBuilder
        public /* bridge */ /* synthetic */ Builder setConsumedPowerForCustomComponent(
                int i, double d) {
            return super.setConsumedPowerForCustomComponent(i, d);
        }

        /* JADX WARN: Type inference failed for: r1v1, types: [android.os.BatteryConsumer$BaseBuilder, android.os.UidBatteryConsumer$Builder] */
        @Override // android.os.BatteryConsumer.BaseBuilder
        public /* bridge */ /* synthetic */ Builder setUsageDurationForCustomComponentMillis(
                int i, long j) {
            return super.setUsageDurationForCustomComponentMillis(i, j);
        }

        /* JADX WARN: Type inference failed for: r1v1, types: [android.os.BatteryConsumer$BaseBuilder, android.os.UidBatteryConsumer$Builder] */
        @Override // android.os.BatteryConsumer.BaseBuilder
        public /* bridge */ /* synthetic */ Builder setUsageDurationMillis(int i, long j) {
            return super.setUsageDurationMillis(i, j);
        }

        /* JADX WARN: Type inference failed for: r1v1, types: [android.os.BatteryConsumer$BaseBuilder, android.os.UidBatteryConsumer$Builder] */
        @Override // android.os.BatteryConsumer.BaseBuilder
        public /* bridge */ /* synthetic */ Builder setUsageDurationMillis(
                BatteryConsumer.Key key, long j) {
            return super.setUsageDurationMillis(key, j);
        }

        public Builder(
                BatteryConsumer.BatteryConsumerData data,
                BatteryStats.Uid batteryStatsUid,
                double minConsumedPowerThreshold) {
            this(data, batteryStatsUid, batteryStatsUid.getUid(), minConsumedPowerThreshold);
        }

        public Builder(
                BatteryConsumer.BatteryConsumerData data,
                int uid,
                double minConsumedPowerThreshold) {
            this(data, null, uid, minConsumedPowerThreshold);
        }

        private Builder(
                BatteryConsumer.BatteryConsumerData data,
                BatteryStats.Uid batteryStatsUid,
                int uid,
                double minConsumedPowerThreshold) {
            super(data, 1, minConsumedPowerThreshold);
            this.mPackageWithHighestDrain = "";
            this.mBatteryStatsUid = batteryStatsUid;
            this.mUid = uid;
            this.mIsVirtualUid = this.mUid == 1090;
            data.putLong(1, this.mUid);
        }

        public BatteryStats.Uid getBatteryStatsUid() {
            if (this.mBatteryStatsUid == null) {
                throw new IllegalStateException(
                        "UidBatteryConsumer.Builder was initialized without a BatteryStats.Uid");
            }
            return this.mBatteryStatsUid;
        }

        public int getUid() {
            return this.mUid;
        }

        public boolean isVirtualUid() {
            return this.mIsVirtualUid;
        }

        public Builder setPackageWithHighestDrain(String packageName) {
            this.mPackageWithHighestDrain = TextUtils.nullIfEmpty(packageName);
            return this;
        }

        @Deprecated
        public Builder setTimeInStateMs(int state, long timeInStateMs) {
            switch (state) {
                case 0:
                    this.mData.putLong(3, timeInStateMs);
                    return this;
                case 1:
                    this.mData.putLong(4, timeInStateMs);
                    return this;
                default:
                    throw new IllegalArgumentException("Unsupported state: " + state);
            }
        }

        public Builder setTimeInProcessStateMs(int state, long timeInProcessStateMs) {
            switch (state) {
                case 1:
                    this.mData.putLong(3, timeInProcessStateMs);
                    return this;
                case 2:
                    this.mData.putLong(4, timeInProcessStateMs);
                    return this;
                case 3:
                    this.mData.putLong(5, timeInProcessStateMs);
                    return this;
                default:
                    throw new IllegalArgumentException("Unsupported process state: " + state);
            }
        }

        public Builder excludeFromBatteryUsageStats() {
            this.mExcludeFromBatteryUsageStats = true;
            return this;
        }

        public Builder add(UidBatteryConsumer consumer) {
            this.mPowerComponentsBuilder.addPowerAndDuration(consumer.mPowerComponents);
            setTimeInProcessStateMs(1, this.mData.getLong(3) + consumer.getTimeInProcessStateMs(1));
            setTimeInProcessStateMs(2, this.mData.getLong(4) + consumer.getTimeInProcessStateMs(2));
            setTimeInProcessStateMs(3, this.mData.getLong(5) + consumer.getTimeInProcessStateMs(3));
            if (this.mPackageWithHighestDrain == "") {
                this.mPackageWithHighestDrain = consumer.getPackageWithHighestDrain();
            } else if (!TextUtils.equals(
                    this.mPackageWithHighestDrain, consumer.getPackageWithHighestDrain())) {
                this.mPackageWithHighestDrain = null;
            }
            return this;
        }

        public boolean isExcludedFromBatteryUsageStats() {
            return this.mExcludeFromBatteryUsageStats;
        }

        public UidBatteryConsumer build() {
            if (this.mPackageWithHighestDrain == "") {
                this.mPackageWithHighestDrain = null;
            }
            if (this.mPackageWithHighestDrain != null) {
                this.mData.putString(2, this.mPackageWithHighestDrain);
            }
            return new UidBatteryConsumer(this);
        }
    }
}
