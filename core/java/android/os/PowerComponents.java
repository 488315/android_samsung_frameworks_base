package android.os;

import android.hardware.scontext.SContextConstants;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.util.proto.ProtoOutputStream;

import com.android.internal.accessibility.common.ShortcutConstants;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.PrintWriter;

class PowerComponents {
    private final BatteryConsumer.BatteryConsumerData mData;

    PowerComponents(Builder builder) {
        this.mData = builder.mData;
    }

    PowerComponents(BatteryConsumer.BatteryConsumerData data) {
        this.mData = data;
    }

    public double getConsumedPower(BatteryConsumer.Dimensions dimensions) {
        return getConsumedPower(
                dimensions.powerComponent,
                dimensions.processState,
                dimensions.screenState,
                dimensions.powerState);
    }

    public double getConsumedPower(
            int powerComponent, int processState, int screenState, int powerState) {
        if (powerComponent != -1 || processState != 0 || screenState != 0 || powerState != 0) {
            if (powerComponent != -1
                    && ((this.mData.layout.screenStateDataIncluded && screenState != 0)
                            || (this.mData.layout.powerStateDataIncluded && powerState != 0))) {
                BatteryConsumer.Key key =
                        this.mData.layout.getKey(
                                powerComponent, processState, screenState, powerState);
                if (key == null) {
                    return SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
                }
                return this.mData.getDouble(key.mPowerColumnIndex);
            }
            if (this.mData.layout.processStateDataIncluded
                    || this.mData.layout.screenStateDataIncluded
                    || this.mData.layout.powerStateDataIncluded) {
                double total = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
                for (BatteryConsumer.Key key2 : this.mData.layout.keys) {
                    if (key2.processState != 0
                            && key2.matches(
                                    powerComponent, processState, screenState, powerState)) {
                        total += this.mData.getDouble(key2.mPowerColumnIndex);
                    }
                }
                if (total != SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
                    return total;
                }
            }
            BatteryConsumer.Key key3 = this.mData.layout.getKey(powerComponent, processState, 0, 0);
            if (key3 == null) {
                return SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
            }
            return this.mData.getDouble(key3.mPowerColumnIndex);
        }
        return this.mData.getDouble(this.mData.layout.totalConsumedPowerColumnIndex);
    }

    public long getUsageDurationMillis(BatteryConsumer.Dimensions dimensions) {
        return getUsageDurationMillis(
                dimensions.powerComponent,
                dimensions.processState,
                dimensions.screenState,
                dimensions.powerState);
    }

    public long getUsageDurationMillis(
            int powerComponent, int processState, int screenState, int powerState) {
        if ((this.mData.layout.screenStateDataIncluded && screenState != 0)
                || (this.mData.layout.powerStateDataIncluded && powerState != 0)) {
            BatteryConsumer.Key key =
                    this.mData.layout.getKey(powerComponent, processState, screenState, powerState);
            if (key != null) {
                return this.mData.getLong(key.mDurationColumnIndex);
            }
            return 0L;
        }
        if (this.mData.layout.screenStateDataIncluded || this.mData.layout.powerStateDataIncluded) {
            long total = 0;
            for (BatteryConsumer.Key key2 : this.mData.layout.keys) {
                if (key2.processState != 0
                        && key2.matches(powerComponent, processState, screenState, powerState)) {
                    total += this.mData.getLong(key2.mDurationColumnIndex);
                }
            }
            if (total != 0) {
                return total;
            }
        }
        BatteryConsumer.Key key3 = this.mData.layout.getKey(powerComponent, processState, 0, 0);
        if (key3 != null) {
            return this.mData.getLong(key3.mDurationColumnIndex);
        }
        return 0L;
    }

    public double getConsumedPower(BatteryConsumer.Key key) {
        if (this.mData.hasValue(key.mPowerColumnIndex)) {
            return this.mData.getDouble(key.mPowerColumnIndex);
        }
        return getConsumedPower(
                key.powerComponent, key.processState, key.screenState, key.powerState);
    }

    public double getConsumedPowerForCustomComponent(int componentId) {
        int index = componentId - 1000;
        if (index >= 0 && index < this.mData.layout.customPowerComponentCount) {
            return this.mData.getDouble(this.mData.layout.firstCustomConsumedPowerColumn + index);
        }
        throw new IllegalArgumentException("Unsupported custom power component ID: " + componentId);
    }

    public String getCustomPowerComponentName(int componentId) {
        int index = componentId - 1000;
        if (index >= 0 && index < this.mData.layout.customPowerComponentCount) {
            try {
                return this.mData.layout.customPowerComponentNames[index];
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new IllegalArgumentException(
                        "Unsupported custom power component ID: " + componentId);
            }
        }
        throw new IllegalArgumentException("Unsupported custom power component ID: " + componentId);
    }

    int getPowerModel(BatteryConsumer.Key key) {
        if (key.mPowerModelColumnIndex == -1) {
            throw new IllegalStateException(
                    "Power model IDs were not requested in the BatteryUsageStatsQuery");
        }
        return this.mData.getInt(key.mPowerModelColumnIndex);
    }

    public long getUsageDurationMillis(BatteryConsumer.Key key) {
        if (this.mData.hasValue(key.mDurationColumnIndex)) {
            return this.mData.getLong(key.mDurationColumnIndex);
        }
        return getUsageDurationMillis(
                key.powerComponent, key.processState, key.screenState, key.powerState);
    }

    public long getUsageDurationForCustomComponentMillis(int componentId) {
        int index = componentId - 1000;
        if (index >= 0 && index < this.mData.layout.customPowerComponentCount) {
            return this.mData.getLong(this.mData.layout.firstCustomUsageDurationColumn + index);
        }
        throw new IllegalArgumentException("Unsupported custom power component ID: " + componentId);
    }

    void dump(PrintWriter pw, int screenState, int powerState, boolean skipEmptyComponents) {
        StringBuilder sb = new StringBuilder();
        for (int componentId = 0; componentId < 19; componentId++) {
            dump(sb, componentId, 0, screenState, powerState, skipEmptyComponents);
            if (this.mData.layout.processStateDataIncluded) {
                for (int processState = 0; processState < 5; processState++) {
                    if (processState != 0) {
                        dump(
                                sb,
                                componentId,
                                processState,
                                screenState,
                                powerState,
                                skipEmptyComponents);
                    }
                }
            }
        }
        if (screenState == 0 && powerState == 0) {
            int customComponentCount = this.mData.layout.customPowerComponentCount;
            for (int customComponentId = 1000;
                    customComponentId < customComponentCount + 1000;
                    customComponentId++) {
                double customComponentPower = getConsumedPowerForCustomComponent(customComponentId);
                if (!skipEmptyComponents
                        || customComponentPower != SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
                    sb.append(getCustomPowerComponentName(customComponentId));
                    sb.append("=");
                    sb.append(BatteryStats.formatCharge(customComponentPower));
                    sb.append(" ");
                }
            }
        }
        while (!sb.isEmpty() && Character.isWhitespace(sb.charAt(sb.length() - 1))) {
            sb.setLength(sb.length() - 1);
        }
        pw.println(sb);
    }

    private void dump(
            StringBuilder sb,
            int powerComponent,
            int processState,
            int screenState,
            int powerState,
            boolean skipEmptyComponents) {
        double componentPower =
                getConsumedPower(powerComponent, processState, screenState, powerState);
        long durationMs =
                getUsageDurationMillis(powerComponent, processState, screenState, powerState);
        if (skipEmptyComponents
                && componentPower == SContextConstants.ENVIRONMENT_VALUE_UNKNOWN
                && durationMs == 0) {
            return;
        }
        sb.append(BatteryConsumer.powerComponentIdToString(powerComponent));
        if (processState != 0) {
            sb.append(ShortcutConstants.SERVICES_SEPARATOR);
            sb.append(BatteryConsumer.processStateToString(processState));
        }
        sb.append("=");
        sb.append(BatteryStats.formatCharge(componentPower));
        if (durationMs != 0) {
            sb.append(" (");
            BatteryStats.formatTimeMsNoSpace(sb, durationMs);
            sb.append(NavigationBarInflaterView.KEY_CODE_END);
        }
        sb.append(' ');
    }

    boolean hasStatsProtoData() {
        return writeStatsProtoImpl(null);
    }

    void writeStatsProto(ProtoOutputStream proto) {
        writeStatsProtoImpl(proto);
    }

    private boolean writeStatsProtoImpl(ProtoOutputStream proto) {
        int i;
        boolean interestingData = false;
        int componentId = 0;
        while (true) {
            boolean z = true;
            if (componentId < 19) {
                BatteryConsumer.Key[] keys = this.mData.layout.getKeys(componentId);
                int length = keys.length;
                int i2 = 0;
                while (i2 < length) {
                    BatteryConsumer.Key key = keys[i2];
                    long powerDeciCoulombs =
                            BatteryConsumer.convertMahToDeciCoulombs(
                                    getConsumedPower(
                                            key.powerComponent,
                                            key.processState,
                                            key.screenState,
                                            key.powerState));
                    long durationMs =
                            getUsageDurationMillis(
                                    key.powerComponent,
                                    key.processState,
                                    key.screenState,
                                    key.powerState);
                    if (powerDeciCoulombs == 0 && durationMs == 0) {
                        i = i2;
                    } else {
                        if (proto == null) {
                            return z;
                        }
                        if (key.processState == 0) {
                            i = i2;
                            writePowerComponentUsage(
                                    proto,
                                    2246267895810L,
                                    componentId,
                                    powerDeciCoulombs,
                                    durationMs);
                        } else {
                            i = i2;
                            writePowerUsageSlice(
                                    proto,
                                    componentId,
                                    powerDeciCoulombs,
                                    durationMs,
                                    key.processState);
                        }
                        interestingData = true;
                    }
                    i2 = i + 1;
                    z = true;
                }
                componentId++;
            } else {
                for (int idx = 0; idx < this.mData.layout.customPowerComponentCount; idx++) {
                    int componentId2 = idx + 1000;
                    long powerDeciCoulombs2 =
                            BatteryConsumer.convertMahToDeciCoulombs(
                                    getConsumedPowerForCustomComponent(componentId2));
                    long durationMs2 = getUsageDurationForCustomComponentMillis(componentId2);
                    if (powerDeciCoulombs2 != 0 || durationMs2 != 0) {
                        if (proto != null) {
                            writePowerComponentUsage(
                                    proto,
                                    2246267895810L,
                                    componentId2,
                                    powerDeciCoulombs2,
                                    durationMs2);
                            interestingData = true;
                        } else {
                            return true;
                        }
                    }
                }
                return interestingData;
            }
        }
    }

    private void writePowerUsageSlice(
            ProtoOutputStream proto,
            int componentId,
            long powerDeciCoulombs,
            long durationMs,
            int processState) {
        int procState;
        long slicesToken = proto.start(2246267895811L);
        writePowerComponentUsage(proto, 1146756268033L, componentId, powerDeciCoulombs, durationMs);
        switch (processState) {
            case 1:
                procState = 1;
                break;
            case 2:
                procState = 2;
                break;
            case 3:
                procState = 3;
                break;
            case 4:
                procState = 4;
                break;
            default:
                throw new IllegalArgumentException("Unknown process state: " + processState);
        }
        proto.write(1159641169922L, procState);
        proto.end(slicesToken);
    }

    private void writePowerComponentUsage(
            ProtoOutputStream proto,
            long tag,
            int componentId,
            long powerDeciCoulombs,
            long durationMs) {
        long token = proto.start(tag);
        proto.write(1120986464257L, componentId);
        proto.write(1112396529666L, powerDeciCoulombs);
        proto.write(1112396529667L, durationMs);
        proto.end(token);
    }

    void writeToXml(TypedXmlSerializer serializer) throws IOException {
        int i;
        BatteryConsumer.Key[] keyArr;
        String str;
        serializer.startTag(null, "power_components");
        BatteryConsumer.Key[] keyArr2 = this.mData.layout.keys;
        int length = keyArr2.length;
        int i2 = 0;
        while (i2 < length) {
            BatteryConsumer.Key key = keyArr2[i2];
            if (!this.mData.hasValue(key.mPowerColumnIndex)
                    && !this.mData.hasValue(key.mDurationColumnIndex)) {
                keyArr = keyArr2;
                i = length;
            } else {
                double powerMah = getConsumedPower(key);
                BatteryConsumer.Key[] keyArr3 = keyArr2;
                i = length;
                long durationMs = getUsageDurationMillis(key);
                if (powerMah == SContextConstants.ENVIRONMENT_VALUE_UNKNOWN && durationMs == 0) {
                    keyArr = keyArr3;
                } else {
                    serializer.startTag(null, "component");
                    keyArr = keyArr3;
                    serializer.attributeInt(null, "id", key.powerComponent);
                    if (key.processState != 0) {
                        serializer.attributeInt(null, "process_state", key.processState);
                    }
                    if (key.screenState != 0) {
                        serializer.attributeInt(null, "screen_state", key.screenState);
                    }
                    if (key.powerState != 0) {
                        serializer.attributeInt(null, "power_state", key.powerState);
                    }
                    if (powerMah != SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
                        serializer.attributeDouble(null, "power", powerMah);
                    }
                    if (durationMs != 0) {
                        serializer.attributeLong(null, "duration", durationMs);
                    }
                    if (!this.mData.layout.powerModelsIncluded) {
                        str = null;
                    } else {
                        str = null;
                        serializer.attributeInt(null, "model", getPowerModel(key));
                    }
                    serializer.endTag(str, "component");
                }
            }
            i2++;
            length = i;
            keyArr2 = keyArr;
        }
        int customComponentEnd = this.mData.layout.customPowerComponentCount + 1000;
        for (int componentId = 1000; componentId < customComponentEnd; componentId++) {
            double powerMah2 = getConsumedPowerForCustomComponent(componentId);
            long durationMs2 = getUsageDurationForCustomComponentMillis(componentId);
            if (powerMah2 != SContextConstants.ENVIRONMENT_VALUE_UNKNOWN || durationMs2 != 0) {
                serializer.startTag(null, "custom_component");
                serializer.attributeInt(null, "id", componentId);
                if (powerMah2 != SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
                    serializer.attributeDouble(null, "power", powerMah2);
                }
                if (durationMs2 != 0) {
                    serializer.attributeLong(null, "duration", durationMs2);
                }
                serializer.endTag(null, "custom_component");
            }
        }
        serializer.endTag(null, "power_components");
    }

    static void parseXml(TypedXmlPullParser parser, Builder builder)
            throws XmlPullParserException, IOException {
        String str;
        char c;
        long durationMs;
        char c2;
        char c3;
        int eventType = parser.getEventType();
        int i = 2;
        if (eventType == 2) {
            String str2 = "power_components";
            if (parser.getName().equals("power_components")) {
                while (true) {
                    if ((eventType != 3 || !parser.getName().equals(str2)) && eventType != 1) {
                        if (eventType == i) {
                            String name = parser.getName();
                            switch (name.hashCode()) {
                                case -1399907075:
                                    if (name.equals("component")) {
                                        c = 0;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -437794385:
                                    if (name.equals("custom_component")) {
                                        c = 1;
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
                                    int componentId = -1;
                                    int processState = 0;
                                    int screenState = 0;
                                    int powerState = 0;
                                    double powerMah = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
                                    int model = 0;
                                    int i2 = 0;
                                    str = str2;
                                    long durationMs2 = 0;
                                    while (true) {
                                        int eventType2 = eventType;
                                        if (i2 < parser.getAttributeCount()) {
                                            String attributeName = parser.getAttributeName(i2);
                                            switch (attributeName.hashCode()) {
                                                case -1992012396:
                                                    durationMs = durationMs2;
                                                    if (attributeName.equals("duration")) {
                                                        c2 = 5;
                                                        break;
                                                    }
                                                    c2 = 65535;
                                                    break;
                                                case -1336023298:
                                                    durationMs = durationMs2;
                                                    if (attributeName.equals("screen_state")) {
                                                        c2 = 2;
                                                        break;
                                                    }
                                                    c2 = 65535;
                                                    break;
                                                case 3355:
                                                    durationMs = durationMs2;
                                                    if (attributeName.equals("id")) {
                                                        c2 = 0;
                                                        break;
                                                    }
                                                    c2 = 65535;
                                                    break;
                                                case 104069929:
                                                    durationMs = durationMs2;
                                                    if (attributeName.equals("model")) {
                                                        c2 = 6;
                                                        break;
                                                    }
                                                    c2 = 65535;
                                                    break;
                                                case 106858757:
                                                    durationMs = durationMs2;
                                                    if (attributeName.equals("power")) {
                                                        c2 = 4;
                                                        break;
                                                    }
                                                    c2 = 65535;
                                                    break;
                                                case 783947991:
                                                    durationMs = durationMs2;
                                                    if (attributeName.equals("power_state")) {
                                                        c2 = 3;
                                                        break;
                                                    }
                                                    c2 = 65535;
                                                    break;
                                                case 1664710337:
                                                    durationMs = durationMs2;
                                                    if (attributeName.equals("process_state")) {
                                                        c2 = 1;
                                                        break;
                                                    }
                                                    c2 = 65535;
                                                    break;
                                                default:
                                                    durationMs = durationMs2;
                                                    c2 = 65535;
                                                    break;
                                            }
                                            switch (c2) {
                                                case 0:
                                                    int componentId2 = parser.getAttributeInt(i2);
                                                    componentId = componentId2;
                                                    durationMs2 = durationMs;
                                                    break;
                                                case 1:
                                                    int processState2 = parser.getAttributeInt(i2);
                                                    processState = processState2;
                                                    durationMs2 = durationMs;
                                                    break;
                                                case 2:
                                                    int screenState2 = parser.getAttributeInt(i2);
                                                    screenState = screenState2;
                                                    durationMs2 = durationMs;
                                                    break;
                                                case 3:
                                                    int powerState2 = parser.getAttributeInt(i2);
                                                    powerState = powerState2;
                                                    durationMs2 = durationMs;
                                                    break;
                                                case 4:
                                                    double powerMah2 =
                                                            parser.getAttributeDouble(i2);
                                                    powerMah = powerMah2;
                                                    durationMs2 = durationMs;
                                                    break;
                                                case 5:
                                                    durationMs2 = parser.getAttributeLong(i2);
                                                    break;
                                                case 6:
                                                    model = parser.getAttributeInt(i2);
                                                    durationMs2 = durationMs;
                                                    break;
                                                default:
                                                    durationMs2 = durationMs;
                                                    break;
                                            }
                                            i2++;
                                            eventType = eventType2;
                                        } else {
                                            BatteryConsumer.Key key =
                                                    builder.mData.layout.getKey(
                                                            componentId,
                                                            processState,
                                                            screenState,
                                                            powerState);
                                            builder.setConsumedPower(key, powerMah, model);
                                            builder.setUsageDurationMillis(key, durationMs2);
                                            break;
                                        }
                                    }
                                case 1:
                                    int componentId3 = -1;
                                    double powerMah3 = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
                                    long durationMs3 = 0;
                                    for (int i3 = 0; i3 < parser.getAttributeCount(); i3++) {
                                        String attributeName2 = parser.getAttributeName(i3);
                                        switch (attributeName2.hashCode()) {
                                            case -1992012396:
                                                if (attributeName2.equals("duration")) {
                                                    c3 = 2;
                                                    break;
                                                }
                                                c3 = 65535;
                                                break;
                                            case 3355:
                                                if (attributeName2.equals("id")) {
                                                    c3 = 0;
                                                    break;
                                                }
                                                c3 = 65535;
                                                break;
                                            case 106858757:
                                                if (attributeName2.equals("power")) {
                                                    c3 = 1;
                                                    break;
                                                }
                                                c3 = 65535;
                                                break;
                                            default:
                                                c3 = 65535;
                                                break;
                                        }
                                        switch (c3) {
                                            case 0:
                                                int componentId4 = parser.getAttributeInt(i3);
                                                componentId3 = componentId4;
                                                break;
                                            case 1:
                                                powerMah3 = parser.getAttributeDouble(i3);
                                                break;
                                            case 2:
                                                durationMs3 = parser.getAttributeLong(i3);
                                                break;
                                        }
                                    }
                                    builder.setConsumedPowerForCustomComponent(
                                            componentId3, powerMah3);
                                    builder.setUsageDurationForCustomComponentMillis(
                                            componentId3, durationMs3);
                                    str = str2;
                                    break;
                                default:
                                    str = str2;
                                    break;
                            }
                        } else {
                            str = str2;
                        }
                        eventType = parser.next();
                        str2 = str;
                        i = 2;
                    }
                    return;
                }
            }
        }
        throw new XmlPullParserException("Invalid XML parser state");
    }

    static final class Builder {
        private static final byte POWER_MODEL_UNINITIALIZED = -1;
        private final BatteryConsumer.BatteryConsumerData mData;
        private final double mMinConsumedPowerThreshold;

        Builder(BatteryConsumer.BatteryConsumerData data, double minConsumedPowerThreshold) {
            this.mData = data;
            this.mMinConsumedPowerThreshold = minConsumedPowerThreshold;
            for (BatteryConsumer.Key key : this.mData.layout.keys) {
                if (key.mPowerModelColumnIndex != -1) {
                    this.mData.putInt(key.mPowerModelColumnIndex, -1);
                }
            }
        }

        public Builder setConsumedPower(
                BatteryConsumer.Key key, double componentPower, int powerModel) {
            this.mData.putDouble(key.mPowerColumnIndex, componentPower);
            if (key.mPowerModelColumnIndex != -1) {
                this.mData.putInt(key.mPowerModelColumnIndex, powerModel);
            }
            return this;
        }

        public Builder addConsumedPower(
                BatteryConsumer.Key key, double componentPower, int powerModel) {
            this.mData.putDouble(
                    key.mPowerColumnIndex,
                    this.mData.getDouble(key.mPowerColumnIndex) + componentPower);
            if (key.mPowerModelColumnIndex != -1) {
                this.mData.putInt(key.mPowerModelColumnIndex, powerModel);
            }
            return this;
        }

        public Builder setConsumedPowerForCustomComponent(int componentId, double componentPower) {
            int index = componentId - 1000;
            if (index < 0 || index >= this.mData.layout.customPowerComponentCount) {
                throw new IllegalArgumentException(
                        "Unsupported custom power component ID: " + componentId);
            }
            this.mData.putDouble(
                    this.mData.layout.firstCustomConsumedPowerColumn + index, componentPower);
            return this;
        }

        public Builder addConsumedPowerForCustomComponent(int componentId, double componentPower) {
            int index = componentId - 1000;
            if (index < 0 || index >= this.mData.layout.customPowerComponentCount) {
                throw new IllegalArgumentException(
                        "Unsupported custom power component ID: " + componentId);
            }
            this.mData.putDouble(
                    this.mData.layout.firstCustomConsumedPowerColumn + index,
                    this.mData.getDouble(this.mData.layout.firstCustomConsumedPowerColumn + index)
                            + componentPower);
            return this;
        }

        public Builder setUsageDurationMillis(
                BatteryConsumer.Key key, long componentUsageDurationMillis) {
            this.mData.putLong(key.mDurationColumnIndex, componentUsageDurationMillis);
            return this;
        }

        public Builder setUsageDurationForCustomComponentMillis(
                int componentId, long componentUsageDurationMillis) {
            int index = componentId - 1000;
            if (index < 0 || index >= this.mData.layout.customPowerComponentCount) {
                throw new IllegalArgumentException(
                        "Unsupported custom power component ID: " + componentId);
            }
            this.mData.putLong(
                    this.mData.layout.firstCustomUsageDurationColumn + index,
                    componentUsageDurationMillis);
            return this;
        }

        public void addPowerAndDuration(Builder other) {
            addPowerAndDuration(other.mData);
        }

        public void addPowerAndDuration(PowerComponents other) {
            addPowerAndDuration(other.mData);
        }

        private void addPowerAndDuration(BatteryConsumer.BatteryConsumerData otherData) {
            if (this.mData.layout.customPowerComponentCount
                    != otherData.layout.customPowerComponentCount) {
                throw new IllegalArgumentException(
                        "Number of custom power components does not match: "
                                + otherData.layout.customPowerComponentCount
                                + ", expected: "
                                + this.mData.layout.customPowerComponentCount);
            }
            for (BatteryConsumer.Key key : this.mData.layout.keys) {
                BatteryConsumer.Key otherKey =
                        otherData.layout.getKey(
                                key.powerComponent,
                                key.processState,
                                key.screenState,
                                key.powerState);
                if (otherKey != null) {
                    this.mData.putDouble(
                            key.mPowerColumnIndex,
                            this.mData.getDouble(key.mPowerColumnIndex)
                                    + otherData.getDouble(otherKey.mPowerColumnIndex));
                    this.mData.putLong(
                            key.mDurationColumnIndex,
                            this.mData.getLong(key.mDurationColumnIndex)
                                    + otherData.getLong(otherKey.mDurationColumnIndex));
                    if (key.mPowerModelColumnIndex != -1) {
                        boolean undefined = false;
                        if (otherKey.mPowerModelColumnIndex == -1) {
                            undefined = true;
                        } else {
                            int powerModel = this.mData.getInt(key.mPowerModelColumnIndex);
                            int otherPowerModel = otherData.getInt(otherKey.mPowerModelColumnIndex);
                            if (powerModel == -1) {
                                this.mData.putInt(key.mPowerModelColumnIndex, otherPowerModel);
                            } else if (powerModel != otherPowerModel && otherPowerModel != -1) {
                                undefined = true;
                            }
                        }
                        if (undefined) {
                            this.mData.putInt(key.mPowerModelColumnIndex, 0);
                        }
                    }
                }
            }
            for (int i = this.mData.layout.customPowerComponentCount - 1; i >= 0; i--) {
                int powerColumnIndex = this.mData.layout.firstCustomConsumedPowerColumn + i;
                int otherPowerColumnIndex = otherData.layout.firstCustomConsumedPowerColumn + i;
                this.mData.putDouble(
                        powerColumnIndex,
                        this.mData.getDouble(powerColumnIndex)
                                + otherData.getDouble(otherPowerColumnIndex));
                int usageColumnIndex = this.mData.layout.firstCustomUsageDurationColumn + i;
                int otherDurationColumnIndex = otherData.layout.firstCustomUsageDurationColumn + i;
                this.mData.putLong(
                        usageColumnIndex,
                        this.mData.getLong(usageColumnIndex)
                                + otherData.getLong(otherDurationColumnIndex));
            }
        }

        public double getTotalPower() {
            double totalPowerMah = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
            for (int componentId = 0; componentId < 19; componentId++) {
                totalPowerMah +=
                        this.mData.getDouble(
                                this.mData.layout.getKeyOrThrow(componentId, 0, 0, 0)
                                        .mPowerColumnIndex);
            }
            for (int i = 0; i < this.mData.layout.customPowerComponentCount; i++) {
                totalPowerMah +=
                        this.mData.getDouble(this.mData.layout.firstCustomConsumedPowerColumn + i);
            }
            return totalPowerMah;
        }

        public PowerComponents build() {
            for (BatteryConsumer.Key key : this.mData.layout.keys) {
                if (key.mPowerModelColumnIndex != -1
                        && this.mData.getInt(key.mPowerModelColumnIndex) == -1) {
                    this.mData.putInt(key.mPowerModelColumnIndex, 0);
                }
                if (this.mMinConsumedPowerThreshold != SContextConstants.ENVIRONMENT_VALUE_UNKNOWN
                        && this.mData.getDouble(key.mPowerColumnIndex)
                                < this.mMinConsumedPowerThreshold) {
                    this.mData.putDouble(
                            key.mPowerColumnIndex, SContextConstants.ENVIRONMENT_VALUE_UNKNOWN);
                }
            }
            if (this.mData.getDouble(this.mData.layout.totalConsumedPowerColumnIndex)
                    == SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
                this.mData.putDouble(
                        this.mData.layout.totalConsumedPowerColumnIndex, getTotalPower());
            }
            return new PowerComponents(this);
        }
    }
}
