package com.android.server.hdmi;

import android.util.Slog;
import android.util.SparseArray;

import com.android.internal.util.IndentingPrintWriter;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;

import com.google.android.collect.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class HdmiUtils {
    public static final Map ADDRESS_TO_TYPE =
            Map.ofEntries(
                    Map.entry(0, Lists.newArrayList(new Integer[] {0})),
                    Map.entry(1, Lists.newArrayList(new Integer[] {1})),
                    Map.entry(2, Lists.newArrayList(new Integer[] {1})),
                    Map.entry(3, Lists.newArrayList(new Integer[] {3})),
                    Map.entry(4, Lists.newArrayList(new Integer[] {4})),
                    Map.entry(5, Lists.newArrayList(new Integer[] {5})),
                    Map.entry(6, Lists.newArrayList(new Integer[] {3})),
                    Map.entry(7, Lists.newArrayList(new Integer[] {3})),
                    Map.entry(8, Lists.newArrayList(new Integer[] {4})),
                    Map.entry(9, Lists.newArrayList(new Integer[] {1})),
                    Map.entry(10, Lists.newArrayList(new Integer[] {3})),
                    Map.entry(11, Lists.newArrayList(new Integer[] {4})),
                    Map.entry(12, Lists.newArrayList(new Integer[] {4, 1, 3, 7})),
                    Map.entry(13, Lists.newArrayList(new Integer[] {4, 1, 3, 7})),
                    Map.entry(14, Lists.newArrayList(new Integer[] {0})),
                    Map.entry(15, Collections.emptyList()));
    public static final String[] DEFAULT_NAMES = {
        "TV",
        "Recorder_1",
        "Recorder_2",
        "Tuner_1",
        "Playback_1",
        "AudioSystem",
        "Tuner_2",
        "Tuner_3",
        "Playback_2",
        "Recorder_3",
        "Tuner_4",
        "Playback_3",
        "Backup_1",
        "Backup_2",
        "Secondary_TV"
    };

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class CodecSad {
        public final int audioCodec;
        public final byte[] sad;

        public CodecSad(int i, byte[] bArr) {
            this.audioCodec = i;
            this.sad = bArr;
        }

        public final boolean equals(Object obj) {
            if (!(obj instanceof CodecSad)) {
                return false;
            }
            CodecSad codecSad = (CodecSad) obj;
            return codecSad.audioCodec == this.audioCodec && Arrays.equals(codecSad.sad, this.sad);
        }

        public final int hashCode() {
            return Objects.hash(
                    Integer.valueOf(this.audioCodec), Integer.valueOf(Arrays.hashCode(this.sad)));
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DeviceConfig {
        public final String name;
        public final List supportedCodecs;

        public DeviceConfig(String str, List list) {
            this.name = str;
            this.supportedCodecs = list;
        }

        public final boolean equals(Object obj) {
            if (!(obj instanceof DeviceConfig)) {
                return false;
            }
            DeviceConfig deviceConfig = (DeviceConfig) obj;
            return deviceConfig.name.equals(this.name)
                    && deviceConfig.supportedCodecs.equals(this.supportedCodecs);
        }

        public final int hashCode() {
            return Objects.hash(this.name, Integer.valueOf(this.supportedCodecs.hashCode()));
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class ShortAudioDescriptorXmlParser {
        /* JADX WARN: Removed duplicated region for block: B:113:0x01af A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:116:0x0019 A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:37:0x0178  */
        /* JADX WARN: Removed duplicated region for block: B:39:0x0188 A[ADDED_TO_REGION] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public static java.util.List parse(java.io.InputStream r18) {
            /*
                Method dump skipped, instructions count: 538
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.hdmi.HdmiUtils.ShortAudioDescriptorXmlParser.parse(java.io.InputStream):java.util.List");
        }

        public static void skip(TypedXmlPullParser typedXmlPullParser) {
            if (typedXmlPullParser.getEventType() != 2) {
                throw new IllegalStateException();
            }
            int i = 1;
            while (i != 0) {
                int next = typedXmlPullParser.next();
                if (next == 2) {
                    i++;
                } else if (next == 3) {
                    i--;
                }
            }
        }
    }

    public static boolean checkCommandSource(HdmiCecMessage hdmiCecMessage, int i, String str) {
        int i2 = hdmiCecMessage.mSource;
        if (i2 == i) {
            return true;
        }
        Slog.w(
                str,
                DualAppManagerService$$ExternalSyntheticOutline0.m(
                        i, i2, "Invalid source [Expected:", ", Actual:", "]"));
        return false;
    }

    public static void dumpMap(IndentingPrintWriter indentingPrintWriter, String str, Map map) {
        if (!str.endsWith(":")) {
            str = str.concat(":");
        }
        indentingPrintWriter.println(str);
        indentingPrintWriter.increaseIndent();
        for (Map.Entry entry : ((HashMap) map).entrySet()) {
            indentingPrintWriter.printPair(entry.getKey().toString(), entry.getValue());
            indentingPrintWriter.println();
        }
        indentingPrintWriter.decreaseIndent();
    }

    public static int getAudioStatusVolume(HdmiCecMessage hdmiCecMessage) {
        int i = hdmiCecMessage.mParams[0] & Byte.MAX_VALUE;
        if (i < 0 || 100 < i) {
            return -1;
        }
        return i;
    }

    public static int getEndOfSequence(int i, byte[] bArr) {
        if (i < 0) {
            return -1;
        }
        while (i < bArr.length && ((bArr[i] >> 7) & 1) == 1) {
            i++;
        }
        if (i >= bArr.length) {
            return -1;
        }
        return i;
    }

    public static int getLocalPortFromPhysicalAddress(int i, int i2) {
        if (i2 == i) {
            return 0;
        }
        int i3 = 61440;
        int i4 = i2;
        int i5 = 61440;
        while (i4 != 0) {
            i4 = i2 & i5;
            i3 |= i5;
            i5 >>= 4;
        }
        int i6 = i & i3;
        if (((i3 << 4) & i6) != i2) {
            return -1;
        }
        int i7 = i6 & (i5 << 4);
        while (true) {
            int i8 = i7 >> 4;
            if (i8 == 0) {
                return i7;
            }
            i7 = i8;
        }
    }

    public static boolean isEligibleAddressForDevice(int i, int i2) {
        return isValidAddress(i2)
                && ((List) ADDRESS_TO_TYPE.get(Integer.valueOf(i2))).contains(Integer.valueOf(i));
    }

    public static boolean isInActiveRoutingPath(int i, int i2) {
        int pathRelationship = pathRelationship(i2, i);
        return pathRelationship == 2 || pathRelationship == 3 || pathRelationship == 5;
    }

    public static boolean isValidAddress(int i) {
        return i >= 0 && i <= 14;
    }

    public static boolean parseCommandParamSystemAudioStatus(HdmiCecMessage hdmiCecMessage) {
        return hdmiCecMessage.mParams[0] == 1;
    }

    public static int pathRelationship(int i, int i2) {
        if (i == 65535 || i2 == 65535) {
            return 0;
        }
        for (int i3 = 0; i3 <= 3; i3++) {
            int i4 = i3 * 4;
            int i5 = 12 - i4;
            int i6 = (i >> i5) & 15;
            int i7 = (i2 >> i5) & 15;
            if (i6 != i7) {
                int i8 = 8 - i4;
                int i9 = (i >> i8) & 15;
                int i10 = (i2 >> i8) & 15;
                if (i6 == 0) {
                    return 2;
                }
                if (i7 == 0) {
                    return 3;
                }
                if (i3 != 3) {
                    return (i9 == 0 && i10 == 0) ? 4 : 1;
                }
                return 4;
            }
        }
        return 5;
    }

    public static List sparseArrayToList(SparseArray sparseArray) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < sparseArray.size(); i++) {
            arrayList.add(sparseArray.valueAt(i));
        }
        return arrayList;
    }

    public static int threeBytesToInt(byte[] bArr) {
        return (bArr[2] & 255) | ((bArr[0] & 255) << 16) | ((bArr[1] & 255) << 8);
    }

    public static int twoBytesToInt(int i, byte[] bArr) {
        return (bArr[i + 1] & 255) | ((bArr[i] & 255) << 8);
    }

    public static int twoBytesToInt(byte[] bArr) {
        return (bArr[1] & 255) | ((bArr[0] & 255) << 8);
    }

    public static boolean verifyAddressType(int i, int i2) {
        List newArrayList =
                isValidAddress(i)
                        ? (List) ADDRESS_TO_TYPE.get(Integer.valueOf(i))
                        : Lists.newArrayList(new Integer[] {-1});
        if (newArrayList.contains(Integer.valueOf(i2))) {
            return true;
        }
        Slog.w(
                "HdmiUtils",
                "Device type mismatch:[Expected:" + i2 + ", Actual:" + newArrayList + "]");
        return false;
    }
}
