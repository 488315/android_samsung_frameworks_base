package android.os;

import android.hardware.scontext.SContextConstants;

import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class UserBatteryConsumer extends BatteryConsumer {
    static final int COLUMN_COUNT = 2;
    private static final int COLUMN_INDEX_USER_ID = 1;
    static final int CONSUMER_TYPE_USER = 2;

    UserBatteryConsumer(BatteryConsumer.BatteryConsumerData data) {
        super(data);
    }

    private UserBatteryConsumer(Builder builder) {
        super(builder.mData, builder.mPowerComponentsBuilder.build());
    }

    public int getUserId() {
        return this.mData.getInt(1);
    }

    @Override // android.os.BatteryConsumer
    public void dump(PrintWriter pw, boolean skipEmptyComponents) {
        double consumedPower = getConsumedPower();
        pw.print("User ");
        pw.print(getUserId());
        pw.print(": ");
        pw.println(BatteryStats.formatCharge(consumedPower));
        pw.print("      ");
        this.mPowerComponents.dump(pw, 0, 0, skipEmptyComponents);
    }

    void writeToXml(TypedXmlSerializer serializer) throws IOException {
        if (getConsumedPower() == SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
            return;
        }
        serializer.startTag(null, "user");
        serializer.attributeInt(null, "user_id", getUserId());
        this.mPowerComponents.writeToXml(serializer);
        serializer.endTag(null, "user");
    }

    static void createFromXml(TypedXmlPullParser parser, BatteryUsageStats.Builder builder)
            throws XmlPullParserException, IOException {
        int userId = parser.getAttributeInt(null, "user_id");
        Builder consumerBuilder = builder.getOrCreateUserBatteryConsumerBuilder(userId);
        int eventType = parser.getEventType();
        if (eventType != 2 || !parser.getName().equals("user")) {
            throw new XmlPullParserException("Invalid XML parser state");
        }
        while (true) {
            if ((eventType != 3 || !parser.getName().equals("user")) && eventType != 1) {
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
        private List<UidBatteryConsumer.Builder> mUidBatteryConsumers;

        @Override // android.os.BatteryConsumer.BaseBuilder
        public /* bridge */ /* synthetic */ Builder addConsumedPower(int i, double d, int i2) {
            return super.addConsumedPower(i, d, i2);
        }

        @Override // android.os.BatteryConsumer.BaseBuilder
        public /* bridge */ /* synthetic */ Builder addConsumedPower(
                BatteryConsumer.Key key, double d, int i) {
            return super.addConsumedPower(key, d, i);
        }

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

        @Override // android.os.BatteryConsumer.BaseBuilder
        public /* bridge */ /* synthetic */ Builder setConsumedPower(int i, double d) {
            return super.setConsumedPower(i, d);
        }

        @Override // android.os.BatteryConsumer.BaseBuilder
        public /* bridge */ /* synthetic */ Builder setConsumedPower(int i, double d, int i2) {
            return super.setConsumedPower(i, d, i2);
        }

        @Override // android.os.BatteryConsumer.BaseBuilder
        public /* bridge */ /* synthetic */ Builder setConsumedPower(
                BatteryConsumer.Key key, double d, int i) {
            return super.setConsumedPower(key, d, i);
        }

        @Override // android.os.BatteryConsumer.BaseBuilder
        public /* bridge */ /* synthetic */ Builder setConsumedPowerForCustomComponent(
                int i, double d) {
            return super.setConsumedPowerForCustomComponent(i, d);
        }

        @Override // android.os.BatteryConsumer.BaseBuilder
        public /* bridge */ /* synthetic */ Builder setUsageDurationForCustomComponentMillis(
                int i, long j) {
            return super.setUsageDurationForCustomComponentMillis(i, j);
        }

        @Override // android.os.BatteryConsumer.BaseBuilder
        public /* bridge */ /* synthetic */ Builder setUsageDurationMillis(int i, long j) {
            return super.setUsageDurationMillis(i, j);
        }

        @Override // android.os.BatteryConsumer.BaseBuilder
        public /* bridge */ /* synthetic */ Builder setUsageDurationMillis(
                BatteryConsumer.Key key, long j) {
            return super.setUsageDurationMillis(key, j);
        }

        Builder(
                BatteryConsumer.BatteryConsumerData data,
                int userId,
                double minConsumedPowerThreshold) {
            super(data, 2, minConsumedPowerThreshold);
            data.putLong(1, userId);
        }

        public void addUidBatteryConsumer(UidBatteryConsumer.Builder uidBatteryConsumerBuilder) {
            if (this.mUidBatteryConsumers == null) {
                this.mUidBatteryConsumers = new ArrayList();
            }
            this.mUidBatteryConsumers.add(uidBatteryConsumerBuilder);
        }

        public UserBatteryConsumer build() {
            if (this.mUidBatteryConsumers != null) {
                for (int i = this.mUidBatteryConsumers.size() - 1; i >= 0; i--) {
                    UidBatteryConsumer.Builder uidBatteryConsumer =
                            this.mUidBatteryConsumers.get(i);
                    this.mPowerComponentsBuilder.addPowerAndDuration(
                            uidBatteryConsumer.mPowerComponentsBuilder);
                }
            }
            return new UserBatteryConsumer(this);
        }
    }
}
