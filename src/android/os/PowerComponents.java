package android.os;

import android.hardware.scontext.SContextConstants;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.BatteryConsumer;
import android.util.proto.ProtoOutputStream;
import com.android.internal.accessibility.common.ShortcutConstants;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import java.io.IOException;
import java.io.PrintWriter;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes3.dex */
class PowerComponents {
    private static final String XML_TAG_CUSTOM_COMPONENT_COMPAT = "custom_component";
    private final BatteryConsumer.BatteryConsumerData mData;

    PowerComponents(Builder builder) {
        this.mData = builder.mData;
    }

    PowerComponents(BatteryConsumer.BatteryConsumerData batteryConsumerData) {
        this.mData = batteryConsumerData;
    }

    public double getConsumedPower(BatteryConsumer.Dimensions dimensions) {
        return getConsumedPower(dimensions.powerComponentId, dimensions.processState, dimensions.screenState, dimensions.powerState);
    }

    public double getConsumedPower(int i, int i2, int i3, int i4) {
        if (i == -1 && i2 == -1 && i3 == 0 && i4 == 0) {
            BatteryConsumer.BatteryConsumerData batteryConsumerData = this.mData;
            return batteryConsumerData.getDouble(batteryConsumerData.layout.totalConsumedPowerColumnIndex);
        }
        boolean z = this.mData.layout.processStateDataIncluded;
        double d = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        if (!z && i2 != 0 && i2 != -1) {
            return SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        }
        BatteryConsumer.Key key = this.mData.layout.getKey(i, (!this.mData.layout.processStateDataIncluded || i2 == -1) ? 0 : i2, (!this.mData.layout.screenStateDataIncluded || i3 == 0) ? 0 : i3, (!this.mData.layout.powerStateDataIncluded || i4 == 0) ? 0 : i4);
        if (key != null && this.mData.hasValue(key.mPowerColumnIndex)) {
            return this.mData.getDouble(key.mPowerColumnIndex);
        }
        for (BatteryConsumer.Key key2 : this.mData.layout.keys) {
            if (key2.matches(i, i2, i3, i4)) {
                d += this.mData.getDouble(key2.mPowerColumnIndex);
            }
        }
        return d;
    }

    public long getUsageDurationMillis(BatteryConsumer.Dimensions dimensions) {
        return getUsageDurationMillis(dimensions.powerComponentId, dimensions.processState, dimensions.screenState, dimensions.powerState);
    }

    public long getUsageDurationMillis(int i, int i2, int i3, int i4) {
        BatteryConsumer.Key key = this.mData.layout.getKey(i, (!this.mData.layout.processStateDataIncluded || i2 == -1) ? 0 : i2, (!this.mData.layout.screenStateDataIncluded || i3 == 0) ? 0 : i3, (!this.mData.layout.powerStateDataIncluded || i4 == 0) ? 0 : i4);
        if (key != null && this.mData.hasValue(key.mDurationColumnIndex)) {
            return this.mData.getLong(key.mDurationColumnIndex);
        }
        long j = 0;
        for (BatteryConsumer.Key key2 : this.mData.layout.keys) {
            if (key2.matches(i, i2, i3, i4)) {
                j += this.mData.getLong(key2.mDurationColumnIndex);
            }
        }
        return j;
    }

    public double getConsumedPower(BatteryConsumer.Key key) {
        if (this.mData.hasValue(key.mPowerColumnIndex)) {
            return this.mData.getDouble(key.mPowerColumnIndex);
        }
        return getConsumedPower(key.powerComponentId, key.processState, key.screenState, key.powerState);
    }

    public String getCustomPowerComponentName(int i) {
        return this.mData.layout.getPowerComponentName(i);
    }

    public long getUsageDurationMillis(BatteryConsumer.Key key) {
        if (this.mData.hasValue(key.mDurationColumnIndex)) {
            return this.mData.getLong(key.mDurationColumnIndex);
        }
        return getUsageDurationMillis(key.powerComponentId, key.processState, key.screenState, key.powerState);
    }

    void dump(PrintWriter printWriter, int i, int i2, boolean z) {
        PowerComponents powerComponents;
        int i3;
        int i4;
        boolean z2;
        StringBuilder sb = new StringBuilder();
        int[] iArr = this.mData.layout.powerComponentIds;
        int length = iArr.length;
        int i5 = 0;
        while (i5 < length) {
            int i6 = iArr[i5];
            if (i6 == 18) {
                powerComponents = this;
                i3 = i;
                i4 = i2;
                z2 = z;
            } else {
                powerComponents = this;
                i3 = i;
                i4 = i2;
                z2 = z;
                powerComponents.dump(sb, i6, -1, i3, i4, z2);
                if (powerComponents.mData.layout.processStateDataIncluded) {
                    for (int i7 = 0; i7 < 5; i7++) {
                        if (i7 != 0) {
                            powerComponents.dump(sb, i6, i7, i3, i4, z2);
                        }
                    }
                }
            }
            i5++;
            this = powerComponents;
            i = i3;
            i2 = i4;
            z = z2;
        }
        while (!sb.isEmpty() && Character.isWhitespace(sb.charAt(sb.length() - 1))) {
            sb.setLength(sb.length() - 1);
        }
        printWriter.println(sb);
    }

    private void dump(StringBuilder sb, int i, int i2, int i3, int i4, boolean z) {
        double consumedPower = getConsumedPower(i, i2, i3, i4);
        long usageDurationMillis = getUsageDurationMillis(i, i2, i3, i4);
        if (z && consumedPower == SContextConstants.ENVIRONMENT_VALUE_UNKNOWN && usageDurationMillis == 0) {
            return;
        }
        sb.append(this.mData.layout.getPowerComponentName(i));
        if (i2 != -1) {
            sb.append(ShortcutConstants.SERVICES_SEPARATOR);
            sb.append(BatteryConsumer.processStateToString(i2));
        }
        sb.append("=");
        sb.append(BatteryStats.formatCharge(consumedPower));
        if (usageDurationMillis != 0) {
            sb.append(" (");
            BatteryStats.formatTimeMsNoSpace(sb, usageDurationMillis);
            sb.append(NavigationBarInflaterView.KEY_CODE_END);
        }
        sb.append(' ');
    }

    boolean hasStatsProtoData() {
        return writeStatsProtoImpl(null);
    }

    void writeStatsProto(ProtoOutputStream protoOutputStream) {
        writeStatsProtoImpl(protoOutputStream);
    }

    private boolean writeStatsProtoImpl(ProtoOutputStream protoOutputStream) {
        int i;
        PowerComponents powerComponents = this;
        int[] iArr = powerComponents.mData.layout.powerComponentIds;
        int length = iArr.length;
        boolean z = false;
        int i2 = 0;
        while (i2 < length) {
            int i3 = iArr[i2];
            BatteryConsumer.Key[] keys = powerComponents.mData.layout.getKeys(i3);
            int length2 = keys.length;
            int i4 = 0;
            while (i4 < length2) {
                BatteryConsumer.Key key = keys[i4];
                long convertMahToDeciCoulombs = BatteryConsumer.convertMahToDeciCoulombs(powerComponents.getConsumedPower(key.powerComponentId, key.processState, key.screenState, key.powerState));
                long usageDurationMillis = powerComponents.getUsageDurationMillis(key.powerComponentId, key.processState, key.screenState, key.powerState);
                if (convertMahToDeciCoulombs == 0 && usageDurationMillis == 0) {
                    i = i3;
                } else {
                    if (protoOutputStream == null) {
                        return true;
                    }
                    if (key.processState == 0) {
                        i = i3;
                        powerComponents.writePowerComponentUsage(protoOutputStream, 2246267895810L, i, convertMahToDeciCoulombs, usageDurationMillis);
                    } else {
                        writePowerUsageSlice(protoOutputStream, i3, convertMahToDeciCoulombs, usageDurationMillis, key.processState);
                        i = i3;
                    }
                    z = true;
                }
                i4++;
                powerComponents = this;
                i3 = i;
            }
            i2++;
            powerComponents = this;
        }
        return z;
    }

    private void writePowerUsageSlice(ProtoOutputStream protoOutputStream, int i, long j, long j2, int i2) {
        long start = protoOutputStream.start(2246267895811L);
        writePowerComponentUsage(protoOutputStream, 1146756268033L, i, j, j2);
        int i3 = 1;
        if (i2 != 1) {
            i3 = 2;
            if (i2 != 2) {
                i3 = 3;
                if (i2 != 3) {
                    i3 = 4;
                    if (i2 != 4) {
                        throw new IllegalArgumentException("Unknown process state: " + i2);
                    }
                }
            }
        }
        protoOutputStream.write(1159641169922L, i3);
        protoOutputStream.end(start);
    }

    private void writePowerComponentUsage(ProtoOutputStream protoOutputStream, long j, int i, long j2, long j3) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1120986464257L, i);
        protoOutputStream.write(1112396529666L, j2);
        protoOutputStream.write(1112396529667L, j3);
        protoOutputStream.end(start);
    }

    void writeToXml(TypedXmlSerializer typedXmlSerializer) throws IOException {
        typedXmlSerializer.startTag(null, "power_components");
        for (BatteryConsumer.Key key : this.mData.layout.keys) {
            if (this.mData.hasValue(key.mPowerColumnIndex) || this.mData.hasValue(key.mDurationColumnIndex)) {
                double consumedPower = getConsumedPower(key);
                long usageDurationMillis = getUsageDurationMillis(key);
                if (consumedPower != SContextConstants.ENVIRONMENT_VALUE_UNKNOWN || usageDurationMillis != 0) {
                    typedXmlSerializer.startTag(null, "component");
                    typedXmlSerializer.attributeInt(null, "id", key.powerComponentId);
                    if (key.processState != 0) {
                        typedXmlSerializer.attributeInt(null, "process_state", key.processState);
                    }
                    if (key.screenState != 0) {
                        typedXmlSerializer.attributeInt(null, "screen_state", key.screenState);
                    }
                    if (key.powerState != 0) {
                        typedXmlSerializer.attributeInt(null, "power_state", key.powerState);
                    }
                    if (consumedPower != SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
                        typedXmlSerializer.attributeDouble(null, "power", consumedPower);
                    }
                    if (usageDurationMillis != 0) {
                        typedXmlSerializer.attributeLong(null, "duration", usageDurationMillis);
                    }
                    typedXmlSerializer.endTag(null, "component");
                }
            }
        }
        typedXmlSerializer.endTag(null, "power_components");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    static void parseXml(TypedXmlPullParser typedXmlPullParser, Builder builder) throws XmlPullParserException, IOException {
        char c;
        int eventType = typedXmlPullParser.getEventType();
        int i = 2;
        if (eventType != 2 || !typedXmlPullParser.getName().equals("power_components")) {
            throw new XmlPullParserException("Invalid XML parser state");
        }
        while (true) {
            if ((eventType == 3 && typedXmlPullParser.getName().equals("power_components")) || eventType == 1) {
                return;
            }
            if (eventType == i) {
                String name = typedXmlPullParser.getName();
                name.hashCode();
                if (name.equals("component") || name.equals(XML_TAG_CUSTOM_COMPONENT_COMPAT)) {
                    double d = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
                    long j = 0;
                    int i2 = 0;
                    int i3 = 0;
                    int i4 = 0;
                    int i5 = -1;
                    for (int i6 = 0; i6 < typedXmlPullParser.getAttributeCount(); i6++) {
                        String attributeName = typedXmlPullParser.getAttributeName(i6);
                        attributeName.hashCode();
                        switch (attributeName.hashCode()) {
                            case -1992012396:
                                if (attributeName.equals("duration")) {
                                    c = 0;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -1336023298:
                                if (attributeName.equals("screen_state")) {
                                    c = 1;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 3355:
                                if (attributeName.equals("id")) {
                                    c = 2;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 106858757:
                                if (attributeName.equals("power")) {
                                    c = 3;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 783947991:
                                if (attributeName.equals("power_state")) {
                                    c = 4;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 1664710337:
                                if (attributeName.equals("process_state")) {
                                    c = 5;
                                    break;
                                }
                                c = 65535;
                                break;
                            default:
                                c = 65535;
                                break;
                        }
                        switch (c) {
                            case 0:
                                j = typedXmlPullParser.getAttributeLong(i6);
                                break;
                            case 1:
                                i4 = typedXmlPullParser.getAttributeInt(i6);
                                break;
                            case 2:
                                i5 = typedXmlPullParser.getAttributeInt(i6);
                                break;
                            case 3:
                                d = typedXmlPullParser.getAttributeDouble(i6);
                                break;
                            case 4:
                                i2 = typedXmlPullParser.getAttributeInt(i6);
                                break;
                            case 5:
                                i3 = typedXmlPullParser.getAttributeInt(i6);
                                break;
                        }
                    }
                    BatteryConsumer.Key key = builder.mData.layout.getKey(i5, i3, i4, i2);
                    builder.addConsumedPower(key, d);
                    builder.addUsageDurationMillis(key, j);
                }
            }
            eventType = typedXmlPullParser.next();
            i = 2;
        }
    }

    static final class Builder {
        private final BatteryConsumer.BatteryConsumerData mData;
        private final double mMinConsumedPowerThreshold;

        Builder(BatteryConsumer.BatteryConsumerData batteryConsumerData, double d) {
            this.mData = batteryConsumerData;
            this.mMinConsumedPowerThreshold = d;
        }

        @Deprecated
        public Builder setConsumedPower(BatteryConsumer.Key key, double d) {
            this.mData.putDouble(key.mPowerColumnIndex, d);
            return this;
        }

        public Builder addConsumedPower(BatteryConsumer.Key key, double d) {
            this.mData.putDouble(key.mPowerColumnIndex, this.mData.getDouble(key.mPowerColumnIndex) + d);
            return this;
        }

        @Deprecated
        public Builder setUsageDurationMillis(BatteryConsumer.Key key, long j) {
            this.mData.putLong(key.mDurationColumnIndex, j);
            return this;
        }

        public Builder addUsageDurationMillis(BatteryConsumer.Key key, long j) {
            this.mData.putLong(key.mDurationColumnIndex, this.mData.getLong(key.mDurationColumnIndex) + j);
            return this;
        }

        public void addPowerAndDuration(Builder builder) {
            addPowerAndDuration(builder.mData);
        }

        public void addPowerAndDuration(PowerComponents powerComponents) {
            addPowerAndDuration(powerComponents.mData);
        }

        private void addPowerAndDuration(BatteryConsumer.BatteryConsumerData batteryConsumerData) {
            if (this.mData.layout.customPowerComponentCount != batteryConsumerData.layout.customPowerComponentCount) {
                throw new IllegalArgumentException("Number of custom power components does not match: " + batteryConsumerData.layout.customPowerComponentCount + ", expected: " + this.mData.layout.customPowerComponentCount);
            }
            for (BatteryConsumer.Key key : this.mData.layout.keys) {
                BatteryConsumer.Key key2 = batteryConsumerData.layout.getKey(key.powerComponentId, key.processState, key.screenState, key.powerState);
                if (key2 != null) {
                    if (!this.mData.hasValue(key.mPowerColumnIndex)) {
                        batteryConsumerData.hasValue(key2.mPowerColumnIndex);
                    }
                    if (this.mData.hasValue(key.mDurationColumnIndex) || batteryConsumerData.hasValue(key2.mDurationColumnIndex)) {
                        this.mData.putLong(key.mDurationColumnIndex, this.mData.getLong(key.mDurationColumnIndex) + batteryConsumerData.getLong(key2.mDurationColumnIndex));
                    }
                }
            }
        }

        public double getTotalPower() {
            BatteryConsumer.Key[] keyArr = this.mData.layout.keys;
            double d = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
            for (BatteryConsumer.Key key : keyArr) {
                if (key.processState == 0 && key.screenState == 0 && key.powerState == 0) {
                    d += this.mData.getDouble(key.mPowerColumnIndex);
                }
            }
            return d;
        }

        public PowerComponents build() {
            for (BatteryConsumer.Key key : this.mData.layout.keys) {
                if (this.mMinConsumedPowerThreshold != SContextConstants.ENVIRONMENT_VALUE_UNKNOWN && this.mData.getDouble(key.mPowerColumnIndex) < this.mMinConsumedPowerThreshold) {
                    this.mData.putDouble(key.mPowerColumnIndex, SContextConstants.ENVIRONMENT_VALUE_UNKNOWN);
                }
            }
            BatteryConsumer.BatteryConsumerData batteryConsumerData = this.mData;
            batteryConsumerData.putDouble(batteryConsumerData.layout.totalConsumedPowerColumnIndex, getTotalPower());
            return new PowerComponents(this);
        }
    }
}
