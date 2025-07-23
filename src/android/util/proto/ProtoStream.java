package android.util.proto;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes4.dex */
public class ProtoStream {
    public static final long FIELD_COUNT_MASK = 16492674416640L;
    public static final long FIELD_COUNT_PACKED = 5497558138880L;
    public static final long FIELD_COUNT_REPEATED = 2199023255552L;
    public static final int FIELD_COUNT_SHIFT = 40;
    public static final long FIELD_COUNT_SINGLE = 1099511627776L;
    public static final long FIELD_COUNT_UNKNOWN = 0;
    public static final int FIELD_ID_MASK = -8;
    public static final int FIELD_ID_SHIFT = 3;
    public static final long FIELD_TYPE_BOOL = 34359738368L;
    public static final long FIELD_TYPE_BYTES = 51539607552L;
    public static final long FIELD_TYPE_DOUBLE = 4294967296L;
    public static final long FIELD_TYPE_ENUM = 60129542144L;
    public static final long FIELD_TYPE_FIXED32 = 30064771072L;
    public static final long FIELD_TYPE_FIXED64 = 25769803776L;
    public static final long FIELD_TYPE_FLOAT = 8589934592L;
    public static final long FIELD_TYPE_INT32 = 21474836480L;
    public static final long FIELD_TYPE_INT64 = 12884901888L;
    public static final long FIELD_TYPE_MASK = 1095216660480L;
    public static final long FIELD_TYPE_MESSAGE = 47244640256L;
    private static final String[] FIELD_TYPE_NAMES = {"Double", "Float", "Int64", "UInt64", "Int32", "Fixed64", "Fixed32", "Bool", "String", "Group", "Message", "Bytes", "UInt32", "Enum", "SFixed32", "SFixed64", "SInt32", "SInt64"};
    public static final long FIELD_TYPE_SFIXED32 = 64424509440L;
    public static final long FIELD_TYPE_SFIXED64 = 68719476736L;
    public static final int FIELD_TYPE_SHIFT = 32;
    public static final long FIELD_TYPE_SINT32 = 73014444032L;
    public static final long FIELD_TYPE_SINT64 = 77309411328L;
    public static final long FIELD_TYPE_STRING = 38654705664L;
    public static final long FIELD_TYPE_UINT32 = 55834574848L;
    public static final long FIELD_TYPE_UINT64 = 17179869184L;
    public static final long FIELD_TYPE_UNKNOWN = 0;
    public static final int WIRE_TYPE_END_GROUP = 4;
    public static final int WIRE_TYPE_FIXED32 = 5;
    public static final int WIRE_TYPE_FIXED64 = 1;
    public static final int WIRE_TYPE_LENGTH_DELIMITED = 2;
    public static final int WIRE_TYPE_MASK = 7;
    public static final int WIRE_TYPE_START_GROUP = 3;
    public static final int WIRE_TYPE_VARINT = 0;

    @Retention(RetentionPolicy.SOURCE)
    public @interface FieldCount {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface FieldType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface WireType {
    }

    public static int convertObjectIdToOrdinal(int i) {
        return 524287 - i;
    }

    public static int getDepthFromToken(long j) {
        return (int) ((j >> 51) & 511);
    }

    public static int getObjectIdFromToken(long j) {
        return (int) ((j >> 32) & 524287);
    }

    public static int getOffsetFromToken(long j) {
        return (int) j;
    }

    public static boolean getRepeatedFromToken(long j) {
        return ((j >> 60) & 1) != 0;
    }

    public static int getTagSizeFromToken(long j) {
        return (int) ((j >> 61) & 7);
    }

    public static long makeFieldId(int i, long j) {
        return j | (i & 4294967295L);
    }

    public static long makeToken(int i, boolean z, int i2, int i3, int i4) {
        return (z ? 1152921504606846976L : 0L) | ((7 & i) << 61) | ((511 & i2) << 51) | ((i3 & 524287) << 32) | (4294967295L & i4);
    }

    public static String getFieldTypeString(long j) {
        int i = ((int) ((j & FIELD_TYPE_MASK) >>> 32)) - 1;
        if (i < 0) {
            return null;
        }
        String[] strArr = FIELD_TYPE_NAMES;
        if (i < strArr.length) {
            return strArr[i];
        }
        return null;
    }

    public static String getFieldCountString(long j) {
        if (j == 1099511627776L) {
            return "";
        }
        if (j == 2199023255552L) {
            return "Repeated";
        }
        if (j == FIELD_COUNT_PACKED) {
            return "Packed";
        }
        return null;
    }

    public static String getWireTypeString(int i) {
        if (i == 0) {
            return "Varint";
        }
        if (i == 1) {
            return "Fixed64";
        }
        if (i == 2) {
            return "Length Delimited";
        }
        if (i == 3) {
            return "Start Group";
        }
        if (i == 4) {
            return "End Group";
        }
        if (i != 5) {
            return null;
        }
        return "Fixed32";
    }

    public static String getFieldIdString(long j) {
        long j2 = FIELD_COUNT_MASK & j;
        String fieldCountString = getFieldCountString(j2);
        if (fieldCountString == null) {
            fieldCountString = "fieldCount=" + j2;
        }
        if (fieldCountString.length() > 0) {
            fieldCountString = fieldCountString + " ";
        }
        long j3 = FIELD_TYPE_MASK & j;
        String fieldTypeString = getFieldTypeString(j3);
        if (fieldTypeString == null) {
            fieldTypeString = "fieldType=" + j3;
        }
        return fieldCountString + fieldTypeString + " tag=" + ((int) j) + " fieldId=0x" + Long.toHexString(j);
    }

    public static String token2String(long j) {
        if (j == 0) {
            return "Token(0)";
        }
        return "Token(val=0x" + Long.toHexString(j) + " depth=" + getDepthFromToken(j) + " object=" + convertObjectIdToOrdinal(getObjectIdFromToken(j)) + " tagSize=" + getTagSizeFromToken(j) + " offset=" + getOffsetFromToken(j) + ')';
    }

    protected ProtoStream() {
    }
}
