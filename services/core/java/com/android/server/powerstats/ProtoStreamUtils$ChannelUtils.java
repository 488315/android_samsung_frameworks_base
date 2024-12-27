package com.android.server.powerstats;

import android.hardware.power.stats.Channel;
import android.hardware.power.stats.EnergyConsumer;
import android.hardware.power.stats.EnergyConsumerAttribution;
import android.hardware.power.stats.EnergyConsumerResult;
import android.hardware.power.stats.EnergyMeasurement;
import android.hardware.power.stats.PowerEntity;
import android.hardware.power.stats.State;
import android.hardware.power.stats.StateResidency;
import android.hardware.power.stats.StateResidencyResult;
import android.util.Slog;
import android.util.proto.ProtoInputStream;
import android.util.proto.ProtoOutputStream;
import android.util.proto.ProtoUtils;
import android.util.proto.WireTypeMismatchException;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class ProtoStreamUtils$ChannelUtils {
    public static void packProtoMessage(Channel[] channelArr, ProtoOutputStream protoOutputStream) {
        if (channelArr == null) {
            return;
        }
        for (int i = 0; i < channelArr.length; i++) {
            long start = protoOutputStream.start(2246267895809L);
            protoOutputStream.write(1120986464257L, channelArr[i].id);
            protoOutputStream.write(1138166333442L, channelArr[i].name);
            protoOutputStream.write(1138166333443L, channelArr[i].subsystem);
            protoOutputStream.end(start);
        }
    }

    public static void packProtoMessage(EnergyConsumer[] energyConsumerArr, ProtoOutputStream protoOutputStream) {
        if (energyConsumerArr == null) {
            return;
        }
        for (int i = 0; i < energyConsumerArr.length; i++) {
            long start = protoOutputStream.start(2246267895809L);
            protoOutputStream.write(1120986464257L, energyConsumerArr[i].id);
            protoOutputStream.write(1120986464258L, energyConsumerArr[i].ordinal);
            protoOutputStream.write(1120986464259L, (int) energyConsumerArr[i].type);
            protoOutputStream.write(1138166333444L, energyConsumerArr[i].name);
            protoOutputStream.end(start);
        }
    }

    public static void packProtoMessage(EnergyConsumerResult[] energyConsumerResultArr, ProtoOutputStream protoOutputStream, boolean z) {
        if (energyConsumerResultArr == null) {
            return;
        }
        for (int i = 0; i < energyConsumerResultArr.length; i++) {
            long start = protoOutputStream.start(2246267895810L);
            long j = 1120986464257L;
            protoOutputStream.write(1120986464257L, energyConsumerResultArr[i].id);
            protoOutputStream.write(1112396529666L, energyConsumerResultArr[i].timestampMs);
            protoOutputStream.write(1112396529667L, energyConsumerResultArr[i].energyUWs);
            if (z) {
                int length = energyConsumerResultArr[i].attribution.length;
                int i2 = 0;
                while (i2 < length) {
                    EnergyConsumerAttribution energyConsumerAttribution = energyConsumerResultArr[i].attribution[i2];
                    long start2 = protoOutputStream.start(2246267895812L);
                    protoOutputStream.write(j, energyConsumerAttribution.uid);
                    protoOutputStream.write(1112396529666L, energyConsumerAttribution.energyUWs);
                    protoOutputStream.end(start2);
                    i2++;
                    j = 1120986464257L;
                }
            }
            protoOutputStream.end(start);
        }
    }

    public static void packProtoMessage(EnergyMeasurement[] energyMeasurementArr, ProtoOutputStream protoOutputStream) {
        if (energyMeasurementArr == null) {
            return;
        }
        for (int i = 0; i < energyMeasurementArr.length; i++) {
            long start = protoOutputStream.start(2246267895810L);
            protoOutputStream.write(1120986464257L, energyMeasurementArr[i].id);
            protoOutputStream.write(1112396529666L, energyMeasurementArr[i].timestampMs);
            protoOutputStream.write(1112396529668L, energyMeasurementArr[i].durationMs);
            protoOutputStream.write(1112396529667L, energyMeasurementArr[i].energyUWs);
            protoOutputStream.end(start);
        }
    }

    public static void packProtoMessage(PowerEntity[] powerEntityArr, ProtoOutputStream protoOutputStream) {
        if (powerEntityArr == null) {
            return;
        }
        for (int i = 0; i < powerEntityArr.length; i++) {
            long start = protoOutputStream.start(2246267895809L);
            protoOutputStream.write(1120986464257L, powerEntityArr[i].id);
            protoOutputStream.write(1138166333442L, powerEntityArr[i].name);
            State[] stateArr = powerEntityArr[i].states;
            if (stateArr != null) {
                int length = stateArr.length;
                for (int i2 = 0; i2 < length; i2++) {
                    State state = powerEntityArr[i].states[i2];
                    long start2 = protoOutputStream.start(2246267895811L);
                    protoOutputStream.write(1120986464257L, state.id);
                    protoOutputStream.write(1138166333442L, state.name);
                    protoOutputStream.end(start2);
                }
            }
            protoOutputStream.end(start);
        }
    }

    public static void packProtoMessage(StateResidencyResult[] stateResidencyResultArr, ProtoOutputStream protoOutputStream) {
        if (stateResidencyResultArr == null) {
            return;
        }
        for (int i = 0; i < stateResidencyResultArr.length; i++) {
            int length = stateResidencyResultArr[i].stateResidencyData.length;
            long j = 2246267895810L;
            long start = protoOutputStream.start(2246267895810L);
            long j2 = 1120986464257L;
            protoOutputStream.write(1120986464257L, stateResidencyResultArr[i].id);
            int i2 = 0;
            while (i2 < length) {
                StateResidency stateResidency = stateResidencyResultArr[i].stateResidencyData[i2];
                long start2 = protoOutputStream.start(j);
                protoOutputStream.write(j2, stateResidency.id);
                protoOutputStream.write(1112396529666L, stateResidency.totalTimeInStateMs);
                protoOutputStream.write(1112396529667L, stateResidency.totalStateEntryCount);
                protoOutputStream.write(1112396529668L, stateResidency.lastEntryTimestampMs);
                protoOutputStream.end(start2);
                i2++;
                j = 2246267895810L;
                j2 = 1120986464257L;
            }
            protoOutputStream.end(start);
        }
    }

    public static EnergyConsumerAttribution unpackEnergyConsumerAttributionProto(ProtoInputStream protoInputStream) {
        int nextField;
        EnergyConsumerAttribution energyConsumerAttribution = new EnergyConsumerAttribution();
        while (true) {
            try {
                nextField = protoInputStream.nextField();
            } catch (WireTypeMismatchException unused) {
                Slog.e("ProtoStreamUtils", "Wire Type mismatch in EnergyConsumerAttributionProto: " + ProtoUtils.currentFieldToString(protoInputStream));
            }
            if (nextField == -1) {
                return energyConsumerAttribution;
            }
            if (nextField == 1) {
                energyConsumerAttribution.uid = protoInputStream.readInt(1120986464257L);
            } else if (nextField != 2) {
                Slog.e("ProtoStreamUtils", "Unhandled field in EnergyConsumerAttributionProto: " + ProtoUtils.currentFieldToString(protoInputStream));
            } else {
                energyConsumerAttribution.energyUWs = protoInputStream.readLong(1112396529666L);
            }
        }
    }

    public static EnergyConsumerResult unpackEnergyConsumerResultProto(ProtoInputStream protoInputStream) {
        int nextField;
        EnergyConsumerResult energyConsumerResult = new EnergyConsumerResult();
        ArrayList arrayList = new ArrayList();
        while (true) {
            try {
                nextField = protoInputStream.nextField();
            } catch (WireTypeMismatchException unused) {
                Slog.e("ProtoStreamUtils", "Wire Type mismatch in EnergyConsumerResultProto: " + ProtoUtils.currentFieldToString(protoInputStream));
            }
            if (nextField == -1) {
                energyConsumerResult.attribution = (EnergyConsumerAttribution[]) arrayList.toArray(new EnergyConsumerAttribution[arrayList.size()]);
                return energyConsumerResult;
            }
            if (nextField == 1) {
                energyConsumerResult.id = protoInputStream.readInt(1120986464257L);
            } else if (nextField == 2) {
                energyConsumerResult.timestampMs = protoInputStream.readLong(1112396529666L);
            } else if (nextField == 3) {
                energyConsumerResult.energyUWs = protoInputStream.readLong(1112396529667L);
            } else if (nextField != 4) {
                Slog.e("ProtoStreamUtils", "Unhandled field in EnergyConsumerResultProto: " + ProtoUtils.currentFieldToString(protoInputStream));
            } else {
                long start = protoInputStream.start(2246267895812L);
                arrayList.add(unpackEnergyConsumerAttributionProto(protoInputStream));
                protoInputStream.end(start);
            }
        }
    }

    public static EnergyMeasurement unpackEnergyMeasurementProto(ProtoInputStream protoInputStream) {
        int nextField;
        EnergyMeasurement energyMeasurement = new EnergyMeasurement();
        while (true) {
            try {
                nextField = protoInputStream.nextField();
            } catch (WireTypeMismatchException unused) {
                Slog.e("ProtoStreamUtils", "Wire Type mismatch in EnergyMeasurementProto: " + ProtoUtils.currentFieldToString(protoInputStream));
            }
            if (nextField == -1) {
                return energyMeasurement;
            }
            if (nextField == 1) {
                energyMeasurement.id = protoInputStream.readInt(1120986464257L);
            } else if (nextField == 2) {
                energyMeasurement.timestampMs = protoInputStream.readLong(1112396529666L);
            } else if (nextField == 3) {
                energyMeasurement.energyUWs = protoInputStream.readLong(1112396529667L);
            } else if (nextField != 4) {
                Slog.e("ProtoStreamUtils", "Unhandled field in EnergyMeasurementProto: " + ProtoUtils.currentFieldToString(protoInputStream));
            } else {
                energyMeasurement.durationMs = protoInputStream.readLong(1112396529668L);
            }
        }
    }

    public static EnergyConsumerResult[] unpackProtoMessage(byte[] bArr) {
        ProtoInputStream protoInputStream = new ProtoInputStream(new ByteArrayInputStream(bArr));
        ArrayList arrayList = new ArrayList();
        while (true) {
            try {
                int nextField = protoInputStream.nextField();
                if (nextField == 2) {
                    long start = protoInputStream.start(2246267895810L);
                    arrayList.add(unpackEnergyConsumerResultProto(protoInputStream));
                    protoInputStream.end(start);
                } else {
                    if (nextField == -1) {
                        return (EnergyConsumerResult[]) arrayList.toArray(new EnergyConsumerResult[arrayList.size()]);
                    }
                    Slog.e("ProtoStreamUtils", "Unhandled field in proto: " + ProtoUtils.currentFieldToString(protoInputStream));
                }
            } catch (WireTypeMismatchException unused) {
                Slog.e("ProtoStreamUtils", "Wire Type mismatch in proto: " + ProtoUtils.currentFieldToString(protoInputStream));
            }
        }
    }

    /* renamed from: unpackProtoMessage, reason: collision with other method in class */
    public static EnergyMeasurement[] m851unpackProtoMessage(byte[] bArr) {
        ProtoInputStream protoInputStream = new ProtoInputStream(new ByteArrayInputStream(bArr));
        ArrayList arrayList = new ArrayList();
        while (true) {
            try {
                int nextField = protoInputStream.nextField();
                if (nextField == 2) {
                    long start = protoInputStream.start(2246267895810L);
                    arrayList.add(unpackEnergyMeasurementProto(protoInputStream));
                    protoInputStream.end(start);
                } else {
                    if (nextField == -1) {
                        return (EnergyMeasurement[]) arrayList.toArray(new EnergyMeasurement[arrayList.size()]);
                    }
                    Slog.e("ProtoStreamUtils", "Unhandled field in proto: " + ProtoUtils.currentFieldToString(protoInputStream));
                }
            } catch (WireTypeMismatchException unused) {
                Slog.e("ProtoStreamUtils", "Wire Type mismatch in proto: " + ProtoUtils.currentFieldToString(protoInputStream));
            }
        }
    }

    /* renamed from: unpackProtoMessage, reason: collision with other method in class */
    public static StateResidencyResult[] m852unpackProtoMessage(byte[] bArr) {
        ProtoInputStream protoInputStream = new ProtoInputStream(new ByteArrayInputStream(bArr));
        ArrayList arrayList = new ArrayList();
        while (true) {
            try {
                int nextField = protoInputStream.nextField();
                if (nextField == 2) {
                    long start = protoInputStream.start(2246267895810L);
                    arrayList.add(unpackStateResidencyResultProto(protoInputStream));
                    protoInputStream.end(start);
                } else {
                    if (nextField == -1) {
                        return (StateResidencyResult[]) arrayList.toArray(new StateResidencyResult[arrayList.size()]);
                    }
                    Slog.e("ProtoStreamUtils", "Unhandled field in PowerStatsServiceResidencyProto: " + ProtoUtils.currentFieldToString(protoInputStream));
                }
            } catch (WireTypeMismatchException unused) {
                Slog.e("ProtoStreamUtils", "Wire Type mismatch in PowerStatsServiceResidencyProto: " + ProtoUtils.currentFieldToString(protoInputStream));
            }
        }
    }

    public static StateResidency unpackStateResidencyProto(ProtoInputStream protoInputStream) {
        int nextField;
        StateResidency stateResidency = new StateResidency();
        while (true) {
            try {
                nextField = protoInputStream.nextField();
            } catch (WireTypeMismatchException unused) {
                Slog.e("ProtoStreamUtils", "Wire Type mismatch in StateResidencyProto: " + ProtoUtils.currentFieldToString(protoInputStream));
            }
            if (nextField == -1) {
                return stateResidency;
            }
            if (nextField == 1) {
                stateResidency.id = protoInputStream.readInt(1120986464257L);
            } else if (nextField == 2) {
                stateResidency.totalTimeInStateMs = protoInputStream.readLong(1112396529666L);
            } else if (nextField == 3) {
                stateResidency.totalStateEntryCount = protoInputStream.readLong(1112396529667L);
            } else if (nextField != 4) {
                Slog.e("ProtoStreamUtils", "Unhandled field in StateResidencyProto: " + ProtoUtils.currentFieldToString(protoInputStream));
            } else {
                stateResidency.lastEntryTimestampMs = protoInputStream.readLong(1112396529668L);
            }
        }
    }

    public static StateResidencyResult unpackStateResidencyResultProto(ProtoInputStream protoInputStream) {
        int nextField;
        StateResidencyResult stateResidencyResult = new StateResidencyResult();
        ArrayList arrayList = new ArrayList();
        while (true) {
            try {
                nextField = protoInputStream.nextField();
            } catch (WireTypeMismatchException unused) {
                Slog.e("ProtoStreamUtils", "Wire Type mismatch in StateResidencyResultProto: " + ProtoUtils.currentFieldToString(protoInputStream));
            }
            if (nextField == -1) {
                stateResidencyResult.stateResidencyData = (StateResidency[]) arrayList.toArray(new StateResidency[arrayList.size()]);
                return stateResidencyResult;
            }
            if (nextField == 1) {
                stateResidencyResult.id = protoInputStream.readInt(1120986464257L);
            } else if (nextField != 2) {
                Slog.e("ProtoStreamUtils", "Unhandled field in StateResidencyResultProto: " + ProtoUtils.currentFieldToString(protoInputStream));
            } else {
                long start = protoInputStream.start(2246267895810L);
                arrayList.add(unpackStateResidencyProto(protoInputStream));
                protoInputStream.end(start);
            }
        }
    }
}
