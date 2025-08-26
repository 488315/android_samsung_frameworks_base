package android.util.proto;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import java.io.IOException;
import java.util.Arrays;

/* loaded from: classes4.dex */
public class ProtoUtils {
    public static void toAggStatsProto(ProtoOutputStream protoOutputStream, long j, long j2, long j3, long j4, int i, int i2) {
        long jStart = protoOutputStream.start(j);
        protoOutputStream.write(1112396529665L, j2);
        protoOutputStream.write(1112396529666L, j3);
        protoOutputStream.write(1112396529667L, j4);
        protoOutputStream.write(1120986464260L, i);
        protoOutputStream.write(1120986464261L, i2);
        protoOutputStream.end(jStart);
    }

    public static void toAggStatsProto(ProtoOutputStream protoOutputStream, long j, long j2, long j3, long j4) {
        toAggStatsProto(protoOutputStream, j, j2, j3, j4, 0, 0);
    }

    public static void toDuration(ProtoOutputStream protoOutputStream, long j, long j2, long j3) {
        long jStart = protoOutputStream.start(j);
        protoOutputStream.write(1112396529665L, j2);
        protoOutputStream.write(1112396529666L, j3);
        protoOutputStream.end(jStart);
    }

    public static void writeBitWiseFlagsToProtoEnum(ProtoOutputStream protoOutputStream, long j, long j2, int[] iArr, int[] iArr2) {
        if (iArr2.length != iArr.length) {
            throw new IllegalArgumentException("The length of origEnums must match protoEnums");
        }
        int length = iArr.length;
        for (int i = 0; i < length; i++) {
            int i2 = iArr[i];
            if (i2 == 0 && j2 == 0) {
                protoOutputStream.write(j, iArr2[i]);
                return;
            } else {
                if ((i2 & j2) != 0) {
                    protoOutputStream.write(j, iArr2[i]);
                }
            }
        }
    }

    public static String currentFieldToString(ProtoInputStream protoInputStream) throws IOException {
        StringBuilder sb = new StringBuilder("Offset : 0x");
        int fieldNumber = protoInputStream.getFieldNumber();
        int wireType = protoInputStream.getWireType();
        sb.append(Integer.toHexString(protoInputStream.getOffset()));
        sb.append("\nField Number : 0x");
        sb.append(Integer.toHexString(protoInputStream.getFieldNumber()));
        sb.append("\nWire Type : ");
        if (wireType == 0) {
            long jMakeFieldId = ProtoStream.makeFieldId(fieldNumber, 1112396529664L);
            sb.append("varint\nField Value : 0x");
            sb.append(Long.toHexString(protoInputStream.readLong(jMakeFieldId)));
        } else if (wireType == 1) {
            long jMakeFieldId2 = ProtoStream.makeFieldId(fieldNumber, 1125281431552L);
            sb.append("fixed64\nField Value : 0x");
            sb.append(Long.toHexString(protoInputStream.readLong(jMakeFieldId2)));
        } else if (wireType == 2) {
            long jMakeFieldId3 = ProtoStream.makeFieldId(fieldNumber, 1151051235328L);
            sb.append("length delimited\nField Bytes : ");
            sb.append(Arrays.toString(protoInputStream.readBytes(jMakeFieldId3)));
        } else if (wireType == 3) {
            sb.append("start group");
        } else if (wireType == 4) {
            sb.append("end group");
        } else if (wireType == 5) {
            long jMakeFieldId4 = ProtoStream.makeFieldId(fieldNumber, 1129576398848L);
            sb.append("fixed32\nField Value : 0x");
            sb.append(Integer.toHexString(protoInputStream.readInt(jMakeFieldId4)));
        } else {
            sb.append("unknown(");
            sb.append(protoInputStream.getWireType());
            sb.append(NavigationBarInflaterView.KEY_CODE_END);
        }
        return sb.toString();
    }
}
