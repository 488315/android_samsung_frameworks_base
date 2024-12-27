package com.android.server.broadcastradio.hal2;

import android.hardware.audio.common.V2_0.AudioChannelMask$$ExternalSyntheticOutline0;
import android.hardware.broadcastradio.V2_0.Metadata;
import android.hardware.broadcastradio.V2_0.ProgramFilter;
import android.hardware.broadcastradio.V2_0.ProgramIdentifier;
import android.hardware.broadcastradio.V2_0.ProgramInfo;
import android.hardware.broadcastradio.V2_0.VendorKeyValue;
import android.hardware.radio.ProgramList;
import android.hardware.radio.ProgramSelector;
import android.hardware.radio.RadioManager;
import android.hardware.radio.RadioMetadata;
import android.os.ParcelableException;
import android.util.ArrayMap;
import android.util.SparseArray;

import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;
import com.android.server.utils.Slogf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class Convert {
    public static final SparseArray METADATA_KEYS;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class MetadataDef {
        public final String key;
        public final MetadataType type;

        public MetadataDef(MetadataType metadataType, String str) {
            this.type = metadataType;
            this.key = str;
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    final class MetadataType {
        public static final /* synthetic */ MetadataType[] $VALUES;
        public static final MetadataType INT;
        public static final MetadataType STRING;

        static {
            MetadataType metadataType = new MetadataType("INT", 0);
            INT = metadataType;
            MetadataType metadataType2 = new MetadataType("STRING", 1);
            STRING = metadataType2;
            $VALUES = new MetadataType[] {metadataType, metadataType2};
        }

        public static MetadataType valueOf(String str) {
            return (MetadataType) Enum.valueOf(MetadataType.class, str);
        }

        public static MetadataType[] values() {
            return (MetadataType[]) $VALUES.clone();
        }
    }

    static {
        SparseArray sparseArray = new SparseArray();
        METADATA_KEYS = sparseArray;
        MetadataType metadataType = MetadataType.STRING;
        sparseArray.put(1, new MetadataDef(metadataType, "android.hardware.radio.metadata.RDS_PS"));
        MetadataType metadataType2 = MetadataType.INT;
        sparseArray.put(
                2, new MetadataDef(metadataType2, "android.hardware.radio.metadata.RDS_PTY"));
        sparseArray.put(
                3, new MetadataDef(metadataType2, "android.hardware.radio.metadata.RBDS_PTY"));
        sparseArray.put(4, new MetadataDef(metadataType, "android.hardware.radio.metadata.RDS_RT"));
        sparseArray.put(5, new MetadataDef(metadataType, "android.hardware.radio.metadata.TITLE"));
        sparseArray.put(6, new MetadataDef(metadataType, "android.hardware.radio.metadata.ARTIST"));
        sparseArray.put(7, new MetadataDef(metadataType, "android.hardware.radio.metadata.ALBUM"));
        sparseArray.put(8, new MetadataDef(metadataType2, "android.hardware.radio.metadata.ICON"));
        sparseArray.put(9, new MetadataDef(metadataType2, "android.hardware.radio.metadata.ART"));
        sparseArray.put(
                10, new MetadataDef(metadataType, "android.hardware.radio.metadata.PROGRAM_NAME"));
        sparseArray.put(
                11,
                new MetadataDef(metadataType, "android.hardware.radio.metadata.DAB_ENSEMBLE_NAME"));
        sparseArray.put(
                12,
                new MetadataDef(
                        metadataType, "android.hardware.radio.metadata.DAB_ENSEMBLE_NAME_SHORT"));
        sparseArray.put(
                13,
                new MetadataDef(metadataType, "android.hardware.radio.metadata.DAB_SERVICE_NAME"));
        sparseArray.put(
                14,
                new MetadataDef(
                        metadataType, "android.hardware.radio.metadata.DAB_SERVICE_NAME_SHORT"));
        sparseArray.put(
                15,
                new MetadataDef(
                        metadataType, "android.hardware.radio.metadata.DAB_COMPONENT_NAME"));
        sparseArray.put(
                16,
                new MetadataDef(
                        metadataType, "android.hardware.radio.metadata.DAB_COMPONENT_NAME_SHORT"));
    }

    public static ProgramFilter programFilterToHal(ProgramList.Filter filter) {
        ProgramFilter programFilter = new ProgramFilter();
        ArrayList arrayList = new ArrayList();
        programFilter.identifierTypes = arrayList;
        programFilter.identifiers = new ArrayList();
        programFilter.includeCategories = false;
        programFilter.excludeModifications = false;
        filter.getIdentifierTypes().stream()
                .forEachOrdered(new Convert$$ExternalSyntheticLambda5(1, arrayList));
        filter.getIdentifiers().stream()
                .forEachOrdered(new Convert$$ExternalSyntheticLambda5(2, programFilter));
        programFilter.includeCategories = filter.areCategoriesIncluded();
        programFilter.excludeModifications = filter.areModificationsExcluded();
        return programFilter;
    }

    public static ProgramSelector.Identifier programIdentifierFromHal(
            ProgramIdentifier programIdentifier) {
        if (programIdentifier.type == 0) {
            return null;
        }
        return new ProgramSelector.Identifier(programIdentifier.type, programIdentifier.value);
    }

    public static RadioManager.ProgramInfo programInfoFromHal(ProgramInfo programInfo) {
        Collection collection =
                (Collection)
                        programInfo.relatedContent.stream()
                                .map(new Convert$$ExternalSyntheticLambda0(0))
                                .collect(Collectors.toList());
        ProgramSelector programSelectorFromHal = programSelectorFromHal(programInfo.selector);
        Objects.requireNonNull(programSelectorFromHal);
        ProgramSelector.Identifier programIdentifierFromHal =
                programIdentifierFromHal(programInfo.logicallyTunedTo);
        ProgramSelector.Identifier programIdentifierFromHal2 =
                programIdentifierFromHal(programInfo.physicallyTunedTo);
        int i = programInfo.infoFlags;
        int i2 = programInfo.signalQuality;
        ArrayList arrayList = programInfo.metadata;
        RadioMetadata.Builder builder = new RadioMetadata.Builder();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            Metadata metadata = (Metadata) it.next();
            MetadataDef metadataDef = (MetadataDef) METADATA_KEYS.get(metadata.key);
            if (metadataDef == null) {
                StringBuilder sb = new StringBuilder("Ignored unknown metadata entry: ");
                int i3 = metadata.key;
                sb.append(
                        i3 == 1
                                ? "RDS_PS"
                                : i3 == 2
                                        ? "RDS_PTY"
                                        : i3 == 3
                                                ? "RBDS_PTY"
                                                : i3 == 4
                                                        ? "RDS_RT"
                                                        : i3 == 5
                                                                ? "SONG_TITLE"
                                                                : i3 == 6
                                                                        ? "SONG_ARTIST"
                                                                        : i3 == 7
                                                                                ? "SONG_ALBUM"
                                                                                : i3 == 8
                                                                                        ? "STATION_ICON"
                                                                                        : i3 == 9
                                                                                                ? "ALBUM_ART"
                                                                                                : i3
                                                                                                                == 10
                                                                                                        ? "PROGRAM_NAME"
                                                                                                        : i3
                                                                                                                        == 11
                                                                                                                ? "DAB_ENSEMBLE_NAME"
                                                                                                                : i3
                                                                                                                                == 12
                                                                                                                        ? "DAB_ENSEMBLE_NAME_SHORT"
                                                                                                                        : i3
                                                                                                                                        == 13
                                                                                                                                ? "DAB_SERVICE_NAME"
                                                                                                                                : i3
                                                                                                                                                == 14
                                                                                                                                        ? "DAB_SERVICE_NAME_SHORT"
                                                                                                                                        : i3
                                                                                                                                                        == 15
                                                                                                                                                ? "DAB_COMPONENT_NAME"
                                                                                                                                                : i3
                                                                                                                                                                == 16
                                                                                                                                                        ? "DAB_COMPONENT_NAME_SHORT"
                                                                                                                                                        : AudioChannelMask$$ExternalSyntheticOutline0
                                                                                                                                                                .m(
                                                                                                                                                                        new StringBuilder(
                                                                                                                                                                                "0x"),
                                                                                                                                                                        i3));
                Slogf.i("BcRadio2Srv.convert", sb.toString());
            } else {
                MetadataType metadataType = MetadataType.STRING;
                MetadataType metadataType2 = metadataDef.type;
                String str = metadataDef.key;
                if (metadataType2 == metadataType) {
                    builder.putString(str, metadata.stringValue);
                } else {
                    builder.putInt(str, (int) metadata.intValue);
                }
            }
        }
        return new RadioManager.ProgramInfo(
                programSelectorFromHal,
                programIdentifierFromHal,
                programIdentifierFromHal2,
                collection,
                i,
                i2,
                builder.build(),
                vendorInfoFromHal(programInfo.vendorInfo));
    }

    public static ProgramSelector programSelectorFromHal(
            android.hardware.broadcastradio.V2_0.ProgramSelector programSelector) {
        ProgramIdentifier programIdentifier = programSelector.primaryId;
        if (programIdentifier.type == 0
                && programIdentifier.value == 0
                && programSelector.secondaryIds.isEmpty()) {
            return null;
        }
        ProgramSelector.Identifier[] identifierArr =
                (ProgramSelector.Identifier[])
                        programSelector.secondaryIds.stream()
                                .map(new Convert$$ExternalSyntheticLambda0(1))
                                .map(new Convert$$ExternalSyntheticLambda0(2))
                                .toArray(new Convert$$ExternalSyntheticLambda3());
        ProgramIdentifier programIdentifier2 = programSelector.primaryId;
        int i = programIdentifier2.type;
        switch (i) {
            case 1:
            case 2:
                i = 2;
                break;
            case 3:
                i = 4;
                break;
            case 4:
            case 11:
            default:
                if (i < 1000 || i > 1999) {
                    i = 0;
                    break;
                }
                break;
            case 5:
            case 6:
            case 7:
            case 8:
            case 14:
                i = 5;
                break;
            case 9:
            case 10:
                i = 6;
                break;
            case 12:
            case 13:
                i = 7;
                break;
        }
        ProgramSelector.Identifier programIdentifierFromHal =
                programIdentifierFromHal(programIdentifier2);
        Objects.requireNonNull(programIdentifierFromHal);
        return new ProgramSelector(i, programIdentifierFromHal, identifierArr, (long[]) null);
    }

    public static android.hardware.broadcastradio.V2_0.ProgramSelector programSelectorToHal(
            ProgramSelector programSelector) {
        android.hardware.broadcastradio.V2_0.ProgramSelector programSelector2 =
                new android.hardware.broadcastradio.V2_0.ProgramSelector();
        ProgramIdentifier programIdentifier = programSelector2.primaryId;
        ProgramSelector.Identifier primaryId = programSelector.getPrimaryId();
        programIdentifier.type = primaryId.getType();
        programIdentifier.value = primaryId.getValue();
        Stream map =
                Arrays.stream(programSelector.getSecondaryIds())
                        .map(new Convert$$ExternalSyntheticLambda0(3));
        ArrayList arrayList = programSelector2.secondaryIds;
        Objects.requireNonNull(arrayList);
        map.forEachOrdered(new Convert$$ExternalSyntheticLambda5(0, arrayList));
        return programSelector2;
    }

    /* JADX WARN: Removed duplicated region for block: B:44:0x00ec A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00d0 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.hardware.radio.RadioManager.ModuleProperties propertiesFromHal(
            int r33,
            java.lang.String r34,
            android.hardware.broadcastradio.V2_0.Properties r35,
            android.hardware.broadcastradio.V2_0.AmFmRegionConfig r36,
            java.util.List r37) {
        /*
            Method dump skipped, instructions count: 408
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.broadcastradio.hal2.Convert.propertiesFromHal(int,"
                    + " java.lang.String, android.hardware.broadcastradio.V2_0.Properties,"
                    + " android.hardware.broadcastradio.V2_0.AmFmRegionConfig,"
                    + " java.util.List):android.hardware.radio.RadioManager$ModuleProperties");
    }

    public static void throwOnError(int i, String str) {
        StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(str, ": ");
        m.append(
                i == 0
                        ? "OK"
                        : i == 1
                                ? "UNKNOWN_ERROR"
                                : i == 2
                                        ? "INTERNAL_ERROR"
                                        : i == 3
                                                ? "INVALID_ARGUMENTS"
                                                : i == 4
                                                        ? "INVALID_STATE"
                                                        : i == 5
                                                                ? "NOT_SUPPORTED"
                                                                : i == 6
                                                                        ? "TIMEOUT"
                                                                        : AudioChannelMask$$ExternalSyntheticOutline0
                                                                                .m(
                                                                                        new StringBuilder(
                                                                                                "0x"),
                                                                                        i));
        String sb = m.toString();
        switch (i) {
            case 0:
                return;
            case 1:
            case 2:
            case 6:
                throw new ParcelableException(new RuntimeException(sb));
            case 3:
                throw new IllegalArgumentException(sb);
            case 4:
                throw new IllegalStateException(sb);
            case 5:
                throw new UnsupportedOperationException(sb);
            default:
                throw new ParcelableException(
                        new RuntimeException(str + ": unknown error (" + i + ")"));
        }
    }

    public static Map vendorInfoFromHal(List list) {
        String str;
        ArrayMap arrayMap = new ArrayMap();
        if (list == null) {
            return arrayMap;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            VendorKeyValue vendorKeyValue = (VendorKeyValue) it.next();
            String str2 = vendorKeyValue.key;
            if (str2 == null || (str = vendorKeyValue.value) == null) {
                Slogf.w("BcRadio2Srv.convert", "VendorKeyValue contains null pointers");
            } else {
                arrayMap.put(str2, str);
            }
        }
        return arrayMap;
    }
}
