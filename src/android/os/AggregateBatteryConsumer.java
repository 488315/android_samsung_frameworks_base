package android.os;

import android.os.BatteryConsumer;
import android.os.BatteryUsageStats;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import java.io.IOException;
import java.io.PrintWriter;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes3.dex */
public final class AggregateBatteryConsumer extends BatteryConsumer {
    static final int COLUMN_COUNT = 3;
    static final int COLUMN_INDEX_CONSUMED_POWER = 2;
    static final int COLUMN_INDEX_SCOPE = 1;
    static final int CONSUMER_TYPE_AGGREGATE = 0;

    AggregateBatteryConsumer(BatteryConsumer.BatteryConsumerData batteryConsumerData) {
        super(batteryConsumerData);
    }

    private AggregateBatteryConsumer(Builder builder) {
        super(builder.mData, builder.mPowerComponentsBuilder.build());
    }

    int getScope() {
        return this.mData.getInt(1);
    }

    @Override // android.os.BatteryConsumer
    public void dump(PrintWriter printWriter, boolean z) {
        this.mPowerComponents.dump(printWriter, 0, 0, z);
    }

    @Override // android.os.BatteryConsumer
    public double getConsumedPower() {
        return this.mData.getDouble(2);
    }

    void writeToXml(TypedXmlSerializer typedXmlSerializer, int i) throws IOException {
        typedXmlSerializer.startTag(null, "aggregate");
        typedXmlSerializer.attributeInt(null, "scope", i);
        typedXmlSerializer.attributeDouble(null, "power", getConsumedPower());
        this.mPowerComponents.writeToXml(typedXmlSerializer);
        typedXmlSerializer.endTag(null, "aggregate");
    }

    static void parseXml(TypedXmlPullParser typedXmlPullParser, BatteryUsageStats.Builder builder) throws XmlPullParserException, IOException {
        Builder aggregateBatteryConsumerBuilder = builder.getAggregateBatteryConsumerBuilder(typedXmlPullParser.getAttributeInt(null, "scope"));
        int eventType = typedXmlPullParser.getEventType();
        if (eventType != 2 || !typedXmlPullParser.getName().equals("aggregate")) {
            throw new XmlPullParserException("Invalid XML parser state");
        }
        aggregateBatteryConsumerBuilder.addConsumedPower(typedXmlPullParser.getAttributeDouble(null, "power"));
        while (true) {
            if ((eventType == 3 && typedXmlPullParser.getName().equals("aggregate")) || eventType == 1) {
                return;
            }
            if (eventType == 2 && typedXmlPullParser.getName().equals("power_components")) {
                PowerComponents.parseXml(typedXmlPullParser, aggregateBatteryConsumerBuilder.mPowerComponentsBuilder);
            }
            eventType = typedXmlPullParser.next();
        }
    }

    public static final class Builder extends BatteryConsumer.BaseBuilder<Builder> {
        @Override // android.os.BatteryConsumer.BaseBuilder
        public /* bridge */ /* synthetic */ BatteryConsumer.BaseBuilder addConsumedPower(int i, double d) {
            return super.addConsumedPower(i, d);
        }

        @Override // android.os.BatteryConsumer.BaseBuilder
        public /* bridge */ /* synthetic */ BatteryConsumer.BaseBuilder addConsumedPower(int i, double d, int i2) {
            return super.addConsumedPower(i, d, i2);
        }

        @Override // android.os.BatteryConsumer.BaseBuilder
        public /* bridge */ /* synthetic */ BatteryConsumer.BaseBuilder addConsumedPower(BatteryConsumer.Key key, double d) {
            return super.addConsumedPower(key, d);
        }

        @Override // android.os.BatteryConsumer.BaseBuilder
        public /* bridge */ /* synthetic */ BatteryConsumer.BaseBuilder addUsageDurationMillis(int i, long j) {
            return super.addUsageDurationMillis(i, j);
        }

        @Override // android.os.BatteryConsumer.BaseBuilder
        public /* bridge */ /* synthetic */ BatteryConsumer.BaseBuilder addUsageDurationMillis(BatteryConsumer.Key key, long j) {
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

        @Override // android.os.BatteryConsumer.BaseBuilder
        @Deprecated
        public /* bridge */ /* synthetic */ BatteryConsumer.BaseBuilder setConsumedPower(int i, double d) {
            return super.setConsumedPower(i, d);
        }

        @Override // android.os.BatteryConsumer.BaseBuilder
        @Deprecated
        public /* bridge */ /* synthetic */ BatteryConsumer.BaseBuilder setConsumedPower(int i, double d, int i2) {
            return super.setConsumedPower(i, d, i2);
        }

        @Override // android.os.BatteryConsumer.BaseBuilder
        public /* bridge */ /* synthetic */ BatteryConsumer.BaseBuilder setConsumedPower(BatteryConsumer.Key key, double d, int i) {
            return super.setConsumedPower(key, d, i);
        }

        @Override // android.os.BatteryConsumer.BaseBuilder
        @Deprecated
        public /* bridge */ /* synthetic */ BatteryConsumer.BaseBuilder setUsageDurationMillis(int i, long j) {
            return super.setUsageDurationMillis(i, j);
        }

        @Override // android.os.BatteryConsumer.BaseBuilder
        @Deprecated
        public /* bridge */ /* synthetic */ BatteryConsumer.BaseBuilder setUsageDurationMillis(BatteryConsumer.Key key, long j) {
            return super.setUsageDurationMillis(key, j);
        }

        public Builder(BatteryConsumer.BatteryConsumerData batteryConsumerData, int i, double d) {
            super(batteryConsumerData, 0, d);
            batteryConsumerData.putInt(1, i);
        }

        public Builder setConsumedPower(double d) {
            this.mData.putDouble(2, d);
            return this;
        }

        public Builder addConsumedPower(double d) {
            this.mData.putDouble(2, this.mData.getDouble(2) + d);
            return this;
        }

        public void add(AggregateBatteryConsumer aggregateBatteryConsumer) {
            addConsumedPower(aggregateBatteryConsumer.getConsumedPower());
            this.mPowerComponentsBuilder.addPowerAndDuration(aggregateBatteryConsumer.mPowerComponents);
        }

        public AggregateBatteryConsumer build() {
            return new AggregateBatteryConsumer(this);
        }
    }
}
