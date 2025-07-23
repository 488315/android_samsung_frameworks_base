package android.os;

import android.hardware.scontext.SContextConstants;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.BatteryConsumer;
import android.os.BatteryStats;
import android.os.BatteryUsageStats;
import android.text.TextUtils;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes3.dex */
public final class UidBatteryConsumer extends BatteryConsumer {
    static final int COLUMN_COUNT = 3;
    static final int COLUMN_INDEX_PACKAGE_WITH_HIGHEST_DRAIN = 2;
    static final int COLUMN_INDEX_UID = 1;
    static final int CONSUMER_TYPE_UID = 1;
    public static final int STATE_BACKGROUND = 1;
    public static final int STATE_FOREGROUND = 0;

    @Retention(RetentionPolicy.SOURCE)
    public @interface State {
    }

    UidBatteryConsumer(BatteryConsumer.BatteryConsumerData batteryConsumerData) {
        super(batteryConsumerData);
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
    public long getTimeInStateMs(int i) {
        if (i == 0) {
            return getTimeInProcessStateMs(1);
        }
        if (i != 1) {
            return 0L;
        }
        return getTimeInProcessStateMs(2) + getTimeInProcessStateMs(3);
    }

    public long getTimeInProcessStateMs(int i) {
        BatteryConsumer.Key key;
        if (i == 0 || (key = getKey(18, i)) == null) {
            return 0L;
        }
        return getUsageDurationMillis(key);
    }

    @Override // android.os.BatteryConsumer
    public void dump(PrintWriter printWriter, boolean z) {
        printWriter.print("UID ");
        UserHandle.formatUid(printWriter, getUid());
        printWriter.print(": ");
        printWriter.print(BatteryStats.formatCharge(getConsumedPower()));
        StringBuilder sb = new StringBuilder();
        appendProcessStateData(sb, 1, z);
        appendProcessStateData(sb, 2, z);
        appendProcessStateData(sb, 3, z);
        appendProcessStateData(sb, 4, z);
        printWriter.println(sb);
        printWriter.print("      ");
        this.mPowerComponents.dump(printWriter, 0, 0, z);
        if (this.mData.layout.powerStateDataIncluded || this.mData.layout.screenStateDataIncluded) {
            for (int i = 0; i < 3; i++) {
                if (!this.mData.layout.powerStateDataIncluded || i != 0) {
                    for (int i2 = 0; i2 < 3; i2++) {
                        if ((!this.mData.layout.screenStateDataIncluded || i2 != 0) && this.mPowerComponents.getConsumedPower(-1, -1, i2, i) != SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
                            printWriter.print("      (");
                            if (i != 0) {
                                printWriter.print(BatteryConsumer.powerStateToString(i));
                            }
                            if (i2 != 0) {
                                if (i != 0) {
                                    printWriter.print(", ");
                                }
                                printWriter.print("screen ");
                                printWriter.print(BatteryConsumer.screenStateToString(i2));
                            }
                            printWriter.print(") ");
                            this.mPowerComponents.dump(printWriter, i2, i, z);
                        }
                    }
                }
            }
        }
    }

    private void appendProcessStateData(StringBuilder sb, int i, boolean z) {
        double consumedPower = this.mPowerComponents.getConsumedPower(new BatteryConsumer.Dimensions(-1, i));
        BatteryConsumer.Key key = getKey(18, i);
        long usageDurationMillis = key != null ? this.mPowerComponents.getUsageDurationMillis(key) : 0L;
        if (consumedPower == SContextConstants.ENVIRONMENT_VALUE_UNKNOWN && usageDurationMillis == 0 && z) {
            return;
        }
        sb.append(" ");
        sb.append(processStateToString(i));
        sb.append(": ");
        sb.append(BatteryStats.formatCharge(consumedPower));
        if (usageDurationMillis != 0) {
            sb.append(" (");
            BatteryStats.formatTimeMsNoSpace(sb, usageDurationMillis);
            sb.append(NavigationBarInflaterView.KEY_CODE_END);
        }
    }

    void writeToXml(TypedXmlSerializer typedXmlSerializer) throws IOException {
        if (getConsumedPower() == SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
            return;
        }
        typedXmlSerializer.startTag(null, "uid");
        typedXmlSerializer.attributeInt(null, "uid", getUid());
        String packageWithHighestDrain = getPackageWithHighestDrain();
        if (!TextUtils.isEmpty(packageWithHighestDrain)) {
            typedXmlSerializer.attribute(null, "highest_drain_package", packageWithHighestDrain);
        }
        this.mPowerComponents.writeToXml(typedXmlSerializer);
        typedXmlSerializer.endTag(null, "uid");
    }

    static void createFromXml(TypedXmlPullParser typedXmlPullParser, BatteryUsageStats.Builder builder) throws XmlPullParserException, IOException {
        Builder orCreateUidBatteryConsumerBuilder = builder.getOrCreateUidBatteryConsumerBuilder(typedXmlPullParser.getAttributeInt(null, "uid"));
        int eventType = typedXmlPullParser.getEventType();
        if (eventType != 2 || !typedXmlPullParser.getName().equals("uid")) {
            throw new XmlPullParserException("Invalid XML parser state");
        }
        orCreateUidBatteryConsumerBuilder.setPackageWithHighestDrain(typedXmlPullParser.getAttributeValue(null, "highest_drain_package"));
        while (true) {
            if ((eventType == 3 && typedXmlPullParser.getName().equals("uid")) || eventType == 1) {
                return;
            }
            if (eventType == 2 && typedXmlPullParser.getName().equals("power_components")) {
                PowerComponents.parseXml(typedXmlPullParser, orCreateUidBatteryConsumerBuilder.mPowerComponentsBuilder);
            }
            eventType = typedXmlPullParser.next();
        }
    }

    public static final class Builder extends BatteryConsumer.BaseBuilder<Builder> {
        private static final String PACKAGE_NAME_UNINITIALIZED = "";
        private final BatteryStats.Uid mBatteryStatsUid;
        private boolean mExcludeFromBatteryUsageStats;
        private final boolean mIsVirtualUid;
        private String mPackageWithHighestDrain;
        private final int mUid;

        /* JADX WARN: Type inference failed for: r0v1, types: [android.os.BatteryConsumer$BaseBuilder, android.os.UidBatteryConsumer$Builder] */
        @Override // android.os.BatteryConsumer.BaseBuilder
        public /* bridge */ /* synthetic */ Builder addConsumedPower(int i, double d) {
            return super.addConsumedPower(i, d);
        }

        /* JADX WARN: Type inference failed for: r0v1, types: [android.os.BatteryConsumer$BaseBuilder, android.os.UidBatteryConsumer$Builder] */
        @Override // android.os.BatteryConsumer.BaseBuilder
        public /* bridge */ /* synthetic */ Builder addConsumedPower(int i, double d, int i2) {
            return super.addConsumedPower(i, d, i2);
        }

        /* JADX WARN: Type inference failed for: r0v1, types: [android.os.BatteryConsumer$BaseBuilder, android.os.UidBatteryConsumer$Builder] */
        @Override // android.os.BatteryConsumer.BaseBuilder
        public /* bridge */ /* synthetic */ Builder addConsumedPower(BatteryConsumer.Key key, double d) {
            return super.addConsumedPower(key, d);
        }

        /* JADX WARN: Type inference failed for: r0v1, types: [android.os.BatteryConsumer$BaseBuilder, android.os.UidBatteryConsumer$Builder] */
        @Override // android.os.BatteryConsumer.BaseBuilder
        public /* bridge */ /* synthetic */ Builder addUsageDurationMillis(int i, long j) {
            return super.addUsageDurationMillis(i, j);
        }

        /* JADX WARN: Type inference failed for: r0v1, types: [android.os.BatteryConsumer$BaseBuilder, android.os.UidBatteryConsumer$Builder] */
        @Override // android.os.BatteryConsumer.BaseBuilder
        public /* bridge */ /* synthetic */ Builder addUsageDurationMillis(BatteryConsumer.Key key, long j) {
            return super.addUsageDurationMillis(key, j);
        }

        @Override // android.os.BatteryConsumer.BaseBuilder
        public /* bridge */ /* synthetic */ BatteryConsumer.Key getKey(int i, int i2) {
            return super.getKey(i, i2);
        }

        @Override // android.os.BatteryConsumer.BaseBuilder
        public /* bridge */ /* synthetic */ BatteryConsumer.Key getKey(int i, int i2, int i3, int i4) {
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

        /* JADX WARN: Type inference failed for: r0v1, types: [android.os.BatteryConsumer$BaseBuilder, android.os.UidBatteryConsumer$Builder] */
        @Override // android.os.BatteryConsumer.BaseBuilder
        @Deprecated
        public /* bridge */ /* synthetic */ Builder setConsumedPower(int i, double d) {
            return super.setConsumedPower(i, d);
        }

        /* JADX WARN: Type inference failed for: r0v1, types: [android.os.BatteryConsumer$BaseBuilder, android.os.UidBatteryConsumer$Builder] */
        @Override // android.os.BatteryConsumer.BaseBuilder
        @Deprecated
        public /* bridge */ /* synthetic */ Builder setConsumedPower(int i, double d, int i2) {
            return super.setConsumedPower(i, d, i2);
        }

        /* JADX WARN: Type inference failed for: r0v1, types: [android.os.BatteryConsumer$BaseBuilder, android.os.UidBatteryConsumer$Builder] */
        @Override // android.os.BatteryConsumer.BaseBuilder
        public /* bridge */ /* synthetic */ Builder setConsumedPower(BatteryConsumer.Key key, double d, int i) {
            return super.setConsumedPower(key, d, i);
        }

        /* JADX WARN: Type inference failed for: r0v1, types: [android.os.BatteryConsumer$BaseBuilder, android.os.UidBatteryConsumer$Builder] */
        @Override // android.os.BatteryConsumer.BaseBuilder
        @Deprecated
        public /* bridge */ /* synthetic */ Builder setUsageDurationMillis(int i, long j) {
            return super.setUsageDurationMillis(i, j);
        }

        /* JADX WARN: Type inference failed for: r0v1, types: [android.os.BatteryConsumer$BaseBuilder, android.os.UidBatteryConsumer$Builder] */
        @Override // android.os.BatteryConsumer.BaseBuilder
        @Deprecated
        public /* bridge */ /* synthetic */ Builder setUsageDurationMillis(BatteryConsumer.Key key, long j) {
            return super.setUsageDurationMillis(key, j);
        }

        public Builder(BatteryConsumer.BatteryConsumerData batteryConsumerData, BatteryStats.Uid uid, double d) {
            this(batteryConsumerData, uid, uid.getUid(), d);
        }

        public Builder(BatteryConsumer.BatteryConsumerData batteryConsumerData, int i, double d) {
            this(batteryConsumerData, null, i, d);
        }

        private Builder(BatteryConsumer.BatteryConsumerData batteryConsumerData, BatteryStats.Uid uid, int i, double d) {
            super(batteryConsumerData, 1, d);
            this.mPackageWithHighestDrain = "";
            this.mBatteryStatsUid = uid;
            this.mUid = i;
            this.mIsVirtualUid = i == 1090;
            batteryConsumerData.putLong(1, i);
        }

        public BatteryStats.Uid getBatteryStatsUid() {
            BatteryStats.Uid uid = this.mBatteryStatsUid;
            if (uid != null) {
                return uid;
            }
            throw new IllegalStateException("UidBatteryConsumer.Builder was initialized without a BatteryStats.Uid");
        }

        public int getUid() {
            return this.mUid;
        }

        public boolean isVirtualUid() {
            return this.mIsVirtualUid;
        }

        public Builder setPackageWithHighestDrain(String str) {
            this.mPackageWithHighestDrain = TextUtils.nullIfEmpty(str);
            return this;
        }

        @Deprecated
        public Builder setTimeInStateMs(int i, long j) {
            if (i == 0) {
                setTimeInProcessStateMs(1, j);
                return this;
            }
            if (i == 1) {
                setTimeInProcessStateMs(2, j);
                return this;
            }
            throw new IllegalArgumentException("Unsupported state: " + i);
        }

        @Deprecated
        public Builder setTimeInProcessStateMs(int i, long j) {
            BatteryConsumer.Key key = getKey(18, i);
            if (key != null) {
                this.mData.putLong(key.mDurationColumnIndex, j);
            }
            return this;
        }

        public Builder excludeFromBatteryUsageStats() {
            this.mExcludeFromBatteryUsageStats = true;
            return this;
        }

        public Builder add(UidBatteryConsumer uidBatteryConsumer) {
            this.mPowerComponentsBuilder.addPowerAndDuration(uidBatteryConsumer.mPowerComponents);
            String str = this.mPackageWithHighestDrain;
            if (str == "") {
                this.mPackageWithHighestDrain = uidBatteryConsumer.getPackageWithHighestDrain();
                return this;
            }
            if (!TextUtils.equals(str, uidBatteryConsumer.getPackageWithHighestDrain())) {
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
