package android.os;

import android.hardware.scontext.SContextConstants;
import android.os.BatteryConsumer;
import android.os.BatteryUsageStats;
import android.os.UidBatteryConsumer;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes3.dex */
public class UserBatteryConsumer extends BatteryConsumer {
    static final int COLUMN_COUNT = 2;
    private static final int COLUMN_INDEX_USER_ID = 1;
    static final int CONSUMER_TYPE_USER = 2;

    UserBatteryConsumer(BatteryConsumer.BatteryConsumerData batteryConsumerData) {
        super(batteryConsumerData);
    }

    private UserBatteryConsumer(Builder builder) {
        super(builder.mData, builder.mPowerComponentsBuilder.build());
    }

    public int getUserId() {
        return this.mData.getInt(1);
    }

    @Override // android.os.BatteryConsumer
    public void dump(PrintWriter printWriter, boolean z) {
        double consumedPower = getConsumedPower();
        printWriter.print("User ");
        printWriter.print(getUserId());
        printWriter.print(": ");
        printWriter.println(BatteryStats.formatCharge(consumedPower));
        printWriter.print("      ");
        this.mPowerComponents.dump(printWriter, 0, 0, z);
    }

    void writeToXml(TypedXmlSerializer typedXmlSerializer) throws IOException {
        if (getConsumedPower() == SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
            return;
        }
        typedXmlSerializer.startTag(null, "user");
        typedXmlSerializer.attributeInt(null, "user_id", getUserId());
        this.mPowerComponents.writeToXml(typedXmlSerializer);
        typedXmlSerializer.endTag(null, "user");
    }

    static void createFromXml(TypedXmlPullParser typedXmlPullParser, BatteryUsageStats.Builder builder) throws XmlPullParserException, IOException {
        Builder orCreateUserBatteryConsumerBuilder = builder.getOrCreateUserBatteryConsumerBuilder(typedXmlPullParser.getAttributeInt(null, "user_id"));
        int eventType = typedXmlPullParser.getEventType();
        if (eventType != 2 || !typedXmlPullParser.getName().equals("user")) {
            throw new XmlPullParserException("Invalid XML parser state");
        }
        while (true) {
            if ((eventType == 3 && typedXmlPullParser.getName().equals("user")) || eventType == 1) {
                return;
            }
            if (eventType == 2 && typedXmlPullParser.getName().equals("power_components")) {
                PowerComponents.parseXml(typedXmlPullParser, orCreateUserBatteryConsumerBuilder.mPowerComponentsBuilder);
            }
            eventType = typedXmlPullParser.next();
        }
    }

    public static final class Builder extends BatteryConsumer.BaseBuilder<Builder> {
        private List<UidBatteryConsumer.Builder> mUidBatteryConsumers;

        /* JADX WARN: Type inference failed for: r0v1, types: [android.os.BatteryConsumer$BaseBuilder, android.os.UserBatteryConsumer$Builder] */
        @Override // android.os.BatteryConsumer.BaseBuilder
        public /* bridge */ /* synthetic */ Builder addConsumedPower(int i, double d) {
            return super.addConsumedPower(i, d);
        }

        /* JADX WARN: Type inference failed for: r0v1, types: [android.os.BatteryConsumer$BaseBuilder, android.os.UserBatteryConsumer$Builder] */
        @Override // android.os.BatteryConsumer.BaseBuilder
        public /* bridge */ /* synthetic */ Builder addConsumedPower(int i, double d, int i2) {
            return super.addConsumedPower(i, d, i2);
        }

        /* JADX WARN: Type inference failed for: r0v1, types: [android.os.BatteryConsumer$BaseBuilder, android.os.UserBatteryConsumer$Builder] */
        @Override // android.os.BatteryConsumer.BaseBuilder
        public /* bridge */ /* synthetic */ Builder addConsumedPower(BatteryConsumer.Key key, double d) {
            return super.addConsumedPower(key, d);
        }

        /* JADX WARN: Type inference failed for: r0v1, types: [android.os.BatteryConsumer$BaseBuilder, android.os.UserBatteryConsumer$Builder] */
        @Override // android.os.BatteryConsumer.BaseBuilder
        public /* bridge */ /* synthetic */ Builder addUsageDurationMillis(int i, long j) {
            return super.addUsageDurationMillis(i, j);
        }

        /* JADX WARN: Type inference failed for: r0v1, types: [android.os.BatteryConsumer$BaseBuilder, android.os.UserBatteryConsumer$Builder] */
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

        /* JADX WARN: Type inference failed for: r0v1, types: [android.os.BatteryConsumer$BaseBuilder, android.os.UserBatteryConsumer$Builder] */
        @Override // android.os.BatteryConsumer.BaseBuilder
        @Deprecated
        public /* bridge */ /* synthetic */ Builder setConsumedPower(int i, double d) {
            return super.setConsumedPower(i, d);
        }

        /* JADX WARN: Type inference failed for: r0v1, types: [android.os.BatteryConsumer$BaseBuilder, android.os.UserBatteryConsumer$Builder] */
        @Override // android.os.BatteryConsumer.BaseBuilder
        @Deprecated
        public /* bridge */ /* synthetic */ Builder setConsumedPower(int i, double d, int i2) {
            return super.setConsumedPower(i, d, i2);
        }

        /* JADX WARN: Type inference failed for: r0v1, types: [android.os.BatteryConsumer$BaseBuilder, android.os.UserBatteryConsumer$Builder] */
        @Override // android.os.BatteryConsumer.BaseBuilder
        public /* bridge */ /* synthetic */ Builder setConsumedPower(BatteryConsumer.Key key, double d, int i) {
            return super.setConsumedPower(key, d, i);
        }

        /* JADX WARN: Type inference failed for: r0v1, types: [android.os.BatteryConsumer$BaseBuilder, android.os.UserBatteryConsumer$Builder] */
        @Override // android.os.BatteryConsumer.BaseBuilder
        @Deprecated
        public /* bridge */ /* synthetic */ Builder setUsageDurationMillis(int i, long j) {
            return super.setUsageDurationMillis(i, j);
        }

        /* JADX WARN: Type inference failed for: r0v1, types: [android.os.BatteryConsumer$BaseBuilder, android.os.UserBatteryConsumer$Builder] */
        @Override // android.os.BatteryConsumer.BaseBuilder
        @Deprecated
        public /* bridge */ /* synthetic */ Builder setUsageDurationMillis(BatteryConsumer.Key key, long j) {
            return super.setUsageDurationMillis(key, j);
        }

        Builder(BatteryConsumer.BatteryConsumerData batteryConsumerData, int i, double d) {
            super(batteryConsumerData, 2, d);
            batteryConsumerData.putLong(1, i);
        }

        public void addUidBatteryConsumer(UidBatteryConsumer.Builder builder) {
            if (this.mUidBatteryConsumers == null) {
                this.mUidBatteryConsumers = new ArrayList();
            }
            this.mUidBatteryConsumers.add(builder);
        }

        public UserBatteryConsumer build() {
            List<UidBatteryConsumer.Builder> list = this.mUidBatteryConsumers;
            if (list != null) {
                for (int size = list.size() - 1; size >= 0; size--) {
                    this.mPowerComponentsBuilder.addPowerAndDuration(this.mUidBatteryConsumers.get(size).mPowerComponentsBuilder);
                }
            }
            return new UserBatteryConsumer(this);
        }
    }
}
