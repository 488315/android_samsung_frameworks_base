package com.android.server.broadcastradio.aidl;

import android.app.compat.CompatChanges;
import android.hardware.broadcastradio.Metadata;
import android.hardware.broadcastradio.ProgramFilter;
import android.hardware.broadcastradio.ProgramIdentifier;
import android.hardware.broadcastradio.ProgramInfo;
import android.hardware.broadcastradio.VendorKeyValue;
import android.hardware.radio.ProgramList;
import android.hardware.radio.ProgramSelector;
import android.hardware.radio.RadioManager;
import android.hardware.radio.RadioMetadata;
import android.hardware.radio.UniqueProgramIdentifier;
import android.os.ParcelableException;
import android.os.ServiceSpecificException;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.IntArray;

import com.android.internal.hidden_from_bootclasspath.android.hardware.radio.Flags;
import com.android.server.utils.Slogf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

public abstract class ConversionUtils {
    public static ProgramList.Chunk convertChunkToTargetSdkVersion(ProgramList.Chunk chunk, int i) {
        ArraySet arraySet = new ArraySet();
        for (RadioManager.ProgramInfo programInfo : chunk.getModified()) {
            if (programInfoMeetsSdkVersionRequirement(programInfo, i)) {
                arraySet.add(programInfo);
            }
        }
        ArraySet arraySet2 = new ArraySet();
        for (UniqueProgramIdentifier uniqueProgramIdentifier : chunk.getRemoved()) {
            if (identifierMeetsSdkVersionRequirement(uniqueProgramIdentifier.getPrimaryId(), i)) {
                arraySet2.add(uniqueProgramIdentifier);
            }
        }
        return new ProgramList.Chunk(chunk.isPurge(), chunk.isComplete(), arraySet, arraySet2);
    }

    public static ProgramFilter filterToHalProgramFilter(ProgramList.Filter filter) {
        ProgramFilter programFilter = new ProgramFilter();
        IntArray intArray = new IntArray(filter.getIdentifierTypes().size());
        ArrayList arrayList = new ArrayList();
        Iterator it = filter.getIdentifierTypes().iterator();
        while (it.hasNext()) {
            intArray.add(((Integer) it.next()).intValue());
        }
        for (ProgramSelector.Identifier identifier : filter.getIdentifiers()) {
            ProgramIdentifier identifierToHalProgramIdentifier =
                    identifierToHalProgramIdentifier(identifier);
            if (identifierToHalProgramIdentifier.type != 0) {
                arrayList.add(identifierToHalProgramIdentifier);
            } else {
                Slogf.w("BcRadioAidlSrv.convert", "Invalid identifiers: %s", identifier);
            }
        }
        programFilter.identifierTypes = intArray.toArray();
        programFilter.identifiers =
                (ProgramIdentifier[])
                        arrayList.toArray(new ConversionUtils$$ExternalSyntheticLambda0(0));
        programFilter.includeCategories = filter.areCategoriesIncluded();
        programFilter.excludeModifications = filter.areModificationsExcluded();
        return programFilter;
    }

    public static ProgramSelector.Identifier identifierFromHalProgramIdentifier(
            ProgramIdentifier programIdentifier) {
        int i = programIdentifier.type;
        if (i == 0) {
            return null;
        }
        if (i == 5) {
            i = 14;
        } else if (i == 14) {
            if (!Flags.hdRadioImproved()) {
                return null;
            }
            i = 15;
        }
        return new ProgramSelector.Identifier(i, programIdentifier.value);
    }

    public static boolean identifierMeetsSdkVersionRequirement(
            ProgramSelector.Identifier identifier, int i) {
        if (Flags.hdRadioImproved()
                && !CompatChanges.isChangeEnabled(302589903L, i)
                && identifier.getType() == 15) {
            return false;
        }
        return CompatChanges.isChangeEnabled(261770108L, i) || identifier.getType() != 14;
    }

    public static ProgramIdentifier identifierToHalProgramIdentifier(
            ProgramSelector.Identifier identifier) {
        ProgramIdentifier programIdentifier = new ProgramIdentifier();
        if (identifier.getType() == 14) {
            programIdentifier.type = 5;
        } else if (!Flags.hdRadioImproved()) {
            programIdentifier.type = identifier.getType();
        } else if (identifier.getType() == 15) {
            programIdentifier.type = 14;
        } else {
            programIdentifier.type = identifier.getType();
        }
        long value = identifier.getValue();
        if (identifier.getType() == 5) {
            programIdentifier.value = (65535 & value) | ((value >>> 16) << 32);
        } else {
            programIdentifier.value = value;
        }
        return programIdentifier;
    }

    public static int identifierTypeToProgramType(int i) {
        int i2 = 2;
        if (i != 1 && i != 2) {
            i2 = 4;
            if (i != 3 && i != 10004) {
                switch (i) {
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                        return 5;
                    case 9:
                    case 10:
                        return 6;
                    default:
                        switch (i) {
                            case 12:
                            case 13:
                                return 7;
                            case 14:
                                return 5;
                            default:
                                if (Flags.hdRadioImproved() && i == 15) {
                                    return 4;
                                }
                                if (i < 1000 || i > 1999) {
                                    return 0;
                                }
                                return i;
                        }
                }
            }
        }
        return i2;
    }

    public static boolean isValidHalProgramSelector(
            android.hardware.broadcastradio.ProgramSelector programSelector) {
        int i = programSelector.primaryId.type;
        if (i == 1 || i == 2 || i == 3 || i == 5 || i == 9 || i == 12) {
            return true;
        }
        return i >= 1000 && i <= 1999;
    }

    public static RadioManager.ProgramInfo programInfoFromHalProgramInfo(ProgramInfo programInfo) {
        if (!isValidHalProgramSelector(programInfo.selector)) {
            return null;
        }
        int i = programInfo.logicallyTunedTo.type;
        if (i != 1 && i != 2 && i != 3 && i != 5 && i != 9 && i != 12 && (i < 1000 || i > 1999)) {
            return null;
        }
        int i2 = programInfo.physicallyTunedTo.type;
        if (i2 != 1 && i2 != 8 && i2 != 10 && i2 != 13 && (i2 < 1000 || i2 > 1999)) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        if (programInfo.relatedContent != null) {
            int i3 = 0;
            while (true) {
                ProgramIdentifier[] programIdentifierArr = programInfo.relatedContent;
                if (i3 >= programIdentifierArr.length) {
                    break;
                }
                ProgramSelector.Identifier identifierFromHalProgramIdentifier =
                        identifierFromHalProgramIdentifier(programIdentifierArr[i3]);
                if (identifierFromHalProgramIdentifier != null) {
                    arrayList.add(identifierFromHalProgramIdentifier);
                }
                i3++;
            }
        }
        ProgramSelector programSelectorFromHalProgramSelector =
                programSelectorFromHalProgramSelector(programInfo.selector);
        Objects.requireNonNull(programSelectorFromHalProgramSelector);
        return new RadioManager.ProgramInfo(
                programSelectorFromHalProgramSelector,
                identifierFromHalProgramIdentifier(programInfo.logicallyTunedTo),
                identifierFromHalProgramIdentifier(programInfo.physicallyTunedTo),
                arrayList,
                programInfo.infoFlags,
                programInfo.signalQuality,
                radioMetadataFromHalMetadata(programInfo.metadata),
                vendorInfoFromHalVendorKeyValues(programInfo.vendorInfo));
    }

    public static boolean programInfoMeetsSdkVersionRequirement(
            RadioManager.ProgramInfo programInfo, int i) {
        if (!programSelectorMeetsSdkVersionRequirement(i, programInfo.getSelector())
                || !identifierMeetsSdkVersionRequirement(programInfo.getLogicallyTunedTo(), i)
                || !identifierMeetsSdkVersionRequirement(programInfo.getPhysicallyTunedTo(), i)) {
            return false;
        }
        Iterator it = programInfo.getRelatedContent().iterator();
        while (it.hasNext()) {
            if (!identifierMeetsSdkVersionRequirement((ProgramSelector.Identifier) it.next(), i)) {
                return false;
            }
        }
        return true;
    }

    public static ProgramSelector programSelectorFromHalProgramSelector(
            android.hardware.broadcastradio.ProgramSelector programSelector) {
        ProgramIdentifier programIdentifier = programSelector.primaryId;
        if ((programIdentifier.type == 0
                        && programIdentifier.value == 0
                        && programSelector.secondaryIds.length == 0)
                || !isValidHalProgramSelector(programSelector)) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        int i = 0;
        while (true) {
            ProgramIdentifier[] programIdentifierArr = programSelector.secondaryIds;
            if (i >= programIdentifierArr.length) {
                int identifierTypeToProgramType =
                        identifierTypeToProgramType(programSelector.primaryId.type);
                ProgramSelector.Identifier identifierFromHalProgramIdentifier =
                        identifierFromHalProgramIdentifier(programSelector.primaryId);
                Objects.requireNonNull(identifierFromHalProgramIdentifier);
                return new ProgramSelector(
                        identifierTypeToProgramType,
                        identifierFromHalProgramIdentifier,
                        (ProgramSelector.Identifier[])
                                arrayList.toArray(new ProgramSelector.Identifier[0]),
                        (long[]) null);
            }
            ProgramIdentifier programIdentifier2 = programIdentifierArr[i];
            if (programIdentifier2 != null) {
                ProgramSelector.Identifier identifierFromHalProgramIdentifier2 =
                        identifierFromHalProgramIdentifier(programIdentifier2);
                if (identifierFromHalProgramIdentifier2 == null) {
                    Slogf.e(
                            "BcRadioAidlSrv.convert",
                            "invalid secondary id: %s",
                            programSelector.secondaryIds[i]);
                } else {
                    arrayList.add(identifierFromHalProgramIdentifier2);
                }
            }
            i++;
        }
    }

    public static boolean programSelectorMeetsSdkVersionRequirement(
            int i, ProgramSelector programSelector) {
        if (!identifierMeetsSdkVersionRequirement(programSelector.getPrimaryId(), i)) {
            return false;
        }
        for (ProgramSelector.Identifier identifier : programSelector.getSecondaryIds()) {
            if (!identifierMeetsSdkVersionRequirement(identifier, i)) {
                return false;
            }
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:44:0x00b2  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00c2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.hardware.radio.RadioManager.ModuleProperties propertiesFromHalProperties(
            int r28,
            java.lang.String r29,
            android.hardware.broadcastradio.Properties r30,
            android.hardware.broadcastradio.AmFmRegionConfig r31,
            android.hardware.broadcastradio.DabTableEntry[] r32) {
        /*
            Method dump skipped, instructions count: 349
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.broadcastradio.aidl.ConversionUtils.propertiesFromHalProperties(int,"
                    + " java.lang.String, android.hardware.broadcastradio.Properties,"
                    + " android.hardware.broadcastradio.AmFmRegionConfig,"
                    + " android.hardware.broadcastradio.DabTableEntry[]):android.hardware.radio.RadioManager$ModuleProperties");
    }

    public static RadioMetadata radioMetadataFromHalMetadata(Metadata[] metadataArr) {
        RadioMetadata.Builder builder = new RadioMetadata.Builder();
        for (int i = 0; i < metadataArr.length; i++) {
            Metadata metadata = metadataArr[i];
            int i2 = metadata._tag;
            switch (i2) {
                case 0:
                    metadata._assertTag(0);
                    builder.putString(
                            "android.hardware.radio.metadata.RDS_PS", (String) metadata._value);
                    break;
                case 1:
                    metadata._assertTag(1);
                    builder.putInt(
                            "android.hardware.radio.metadata.RDS_PTY",
                            ((Integer) metadata._value).intValue());
                    break;
                case 2:
                    metadata._assertTag(2);
                    builder.putInt(
                            "android.hardware.radio.metadata.RBDS_PTY",
                            ((Integer) metadata._value).intValue());
                    break;
                case 3:
                    metadata._assertTag(3);
                    builder.putString(
                            "android.hardware.radio.metadata.RDS_RT", (String) metadata._value);
                    break;
                case 4:
                    metadata._assertTag(4);
                    builder.putString(
                            "android.hardware.radio.metadata.TITLE", (String) metadata._value);
                    break;
                case 5:
                    metadata._assertTag(5);
                    builder.putString(
                            "android.hardware.radio.metadata.ARTIST", (String) metadata._value);
                    break;
                case 6:
                    metadata._assertTag(6);
                    builder.putString(
                            "android.hardware.radio.metadata.ALBUM", (String) metadata._value);
                    break;
                case 7:
                    metadata._assertTag(7);
                    builder.putInt(
                            "android.hardware.radio.metadata.ICON",
                            ((Integer) metadata._value).intValue());
                    break;
                case 8:
                    metadata._assertTag(8);
                    builder.putInt(
                            "android.hardware.radio.metadata.ART",
                            ((Integer) metadata._value).intValue());
                    break;
                case 9:
                    metadata._assertTag(9);
                    builder.putString(
                            "android.hardware.radio.metadata.PROGRAM_NAME",
                            (String) metadata._value);
                    break;
                case 10:
                    metadata._assertTag(10);
                    builder.putString(
                            "android.hardware.radio.metadata.DAB_ENSEMBLE_NAME",
                            (String) metadata._value);
                    break;
                case 11:
                    metadata._assertTag(11);
                    builder.putString(
                            "android.hardware.radio.metadata.DAB_ENSEMBLE_NAME_SHORT",
                            (String) metadata._value);
                    break;
                case 12:
                    metadata._assertTag(12);
                    builder.putString(
                            "android.hardware.radio.metadata.DAB_SERVICE_NAME",
                            (String) metadata._value);
                    break;
                case 13:
                    metadata._assertTag(13);
                    builder.putString(
                            "android.hardware.radio.metadata.DAB_SERVICE_NAME_SHORT",
                            (String) metadata._value);
                    break;
                case 14:
                    metadata._assertTag(14);
                    builder.putString(
                            "android.hardware.radio.metadata.DAB_COMPONENT_NAME",
                            (String) metadata._value);
                    break;
                case 15:
                    metadata._assertTag(15);
                    builder.putString(
                            "android.hardware.radio.metadata.DAB_COMPONENT_NAME_SHORT",
                            (String) metadata._value);
                    break;
                default:
                    if (Flags.hdRadioImproved()) {
                        switch (i2) {
                            case 16:
                                Metadata metadata2 = metadataArr[i];
                                metadata2._assertTag(16);
                                builder.putString(
                                        "android.hardware.radio.metadata.GENRE",
                                        (String) metadata2._value);
                                break;
                            case 17:
                                Metadata metadata3 = metadataArr[i];
                                metadata3._assertTag(17);
                                builder.putString(
                                        "android.hardware.radio.metadata.COMMENT_SHORT_DESCRIPTION",
                                        (String) metadata3._value);
                                break;
                            case 18:
                                Metadata metadata4 = metadataArr[i];
                                metadata4._assertTag(18);
                                builder.putString(
                                        "android.hardware.radio.metadata.COMMENT_ACTUAL_TEXT",
                                        (String) metadata4._value);
                                break;
                            case 19:
                                Metadata metadata5 = metadataArr[i];
                                metadata5._assertTag(19);
                                builder.putString(
                                        "android.hardware.radio.metadata.COMMERCIAL",
                                        (String) metadata5._value);
                                break;
                            case 20:
                                Metadata metadata6 = metadataArr[i];
                                metadata6._assertTag(20);
                                builder.putStringArray(
                                        "android.hardware.radio.metadata.UFIDS",
                                        (String[]) metadata6._value);
                                break;
                            case 21:
                                Metadata metadata7 = metadataArr[i];
                                metadata7._assertTag(21);
                                builder.putString(
                                        "android.hardware.radio.metadata.HD_STATION_NAME_SHORT",
                                        (String) metadata7._value);
                                break;
                            case 22:
                                Metadata metadata8 = metadataArr[i];
                                metadata8._assertTag(22);
                                builder.putString(
                                        "android.hardware.radio.metadata.HD_STATION_NAME_LONG",
                                        (String) metadata8._value);
                                break;
                            case 23:
                                Metadata metadata9 = metadataArr[i];
                                metadata9._assertTag(23);
                                builder.putInt(
                                        "android.hardware.radio.metadata.HD_SUBCHANNELS_AVAILABLE",
                                        ((Integer) metadata9._value).intValue());
                                break;
                            default:
                                Slogf.w(
                                        "BcRadioAidlSrv.convert",
                                        "Ignored unknown metadata entry: %s with HD radio flag"
                                            + " enabled",
                                        metadataArr[i]);
                                break;
                        }
                    } else {
                        Slogf.w(
                                "BcRadioAidlSrv.convert",
                                "Ignored unknown metadata entry: %s with HD radio flag disabled",
                                metadataArr[i]);
                        break;
                    }
            }
        }
        return builder.build();
    }

    public static RuntimeException throwOnError(RuntimeException runtimeException, String str) {
        if (!(runtimeException instanceof ServiceSpecificException)) {
            return new ParcelableException(new RuntimeException(str.concat(": unknown error")));
        }
        int i = ((ServiceSpecificException) runtimeException).errorCode;
        switch (i) {
            case 1:
                return new ParcelableException(
                        new RuntimeException(str.concat(": INTERNAL_ERROR")));
            case 2:
                return new IllegalArgumentException(str.concat(": INVALID_ARGUMENTS"));
            case 3:
                return new IllegalStateException(str.concat(": INVALID_STATE"));
            case 4:
                return new UnsupportedOperationException(str.concat(": NOT_SUPPORTED"));
            case 5:
                return new ParcelableException(new RuntimeException(str.concat(": TIMEOUT")));
            case 6:
                return new IllegalStateException(str.concat(": CANCELED"));
            case 7:
                return new ParcelableException(new RuntimeException(str.concat(": UNKNOWN_ERROR")));
            default:
                return new ParcelableException(
                        new RuntimeException(str + ": unknown error (" + i + ")"));
        }
    }

    public static Map vendorInfoFromHalVendorKeyValues(VendorKeyValue[] vendorKeyValueArr) {
        String str;
        if (vendorKeyValueArr == null) {
            return Collections.emptyMap();
        }
        ArrayMap arrayMap = new ArrayMap();
        for (VendorKeyValue vendorKeyValue : vendorKeyValueArr) {
            String str2 = vendorKeyValue.key;
            if (str2 == null || (str = vendorKeyValue.value) == null) {
                Slogf.w(
                        "BcRadioAidlSrv.convert",
                        "VendorKeyValue contains invalid entry: key = %s, value = %s",
                        str2,
                        vendorKeyValue.value);
            } else {
                arrayMap.put(str2, str);
            }
        }
        return arrayMap;
    }

    public static VendorKeyValue[] vendorInfoToHalVendorKeyValues(Map map) {
        if (map == null) {
            return new VendorKeyValue[0];
        }
        ArrayList arrayList = new ArrayList();
        for (Map.Entry entry : map.entrySet()) {
            VendorKeyValue vendorKeyValue = new VendorKeyValue();
            vendorKeyValue.key = (String) entry.getKey();
            String str = (String) entry.getValue();
            vendorKeyValue.value = str;
            String str2 = vendorKeyValue.key;
            if (str2 == null || str == null) {
                Slogf.w(
                        "BcRadioAidlSrv.convert",
                        "VendorKeyValue contains invalid entry: key = %s, value = %s",
                        str2,
                        str);
            } else {
                arrayList.add(vendorKeyValue);
            }
        }
        return (VendorKeyValue[])
                arrayList.toArray(new ConversionUtils$$ExternalSyntheticLambda0(2));
    }
}
