package com.android.internal.widget.remotecompose.core.operations;

import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.PaintOperation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import com.android.internal.widget.remotecompose.core.types.LongConstant;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* loaded from: classes6.dex */
public class TimeAttribute extends PaintOperation {
    private static final String CLASS_NAME = "TimeAttribute";
    private static final int OP_CODE = 172;
    public static final short TIME_DAY_OF_MONTH = 9;
    public static final short TIME_DAY_OF_WEEK = 11;
    public static final short TIME_FROM_ARG_HR = 5;
    public static final short TIME_FROM_ARG_MIN = 4;
    public static final short TIME_FROM_ARG_SEC = 3;
    public static final short TIME_FROM_LOAD_SEC = 14;
    public static final short TIME_FROM_NOW_HR = 2;
    public static final short TIME_FROM_NOW_MIN = 1;
    public static final short TIME_FROM_NOW_SEC = 0;
    public static final short TIME_IN_HR = 8;
    public static final short TIME_IN_MIN = 7;
    public static final short TIME_IN_SEC = 6;
    public static final short TIME_MONTH_VALUE = 10;
    public static final short TIME_YEAR = 12;
    private final int[] mArgs;
    float[] mBounds = new float[4];
    public int mId;
    public int mTimeId;
    public short mType;

    public static int id() {
        return 172;
    }

    public TimeAttribute(int i, int i2, short s, int[] iArr) {
        this.mId = i;
        this.mTimeId = i2;
        this.mType = s;
        this.mArgs = iArr;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer, this.mId, this.mTimeId, this.mType);
    }

    public String toString() {
        if (this.mArgs == null) {
            return "TimeAttribute[" + this.mId + "] = " + this.mTimeId + " " + ((int) this.mType);
        }
        return "TimeAttribute[" + this.mId + "] = " + this.mTimeId + " " + ((int) this.mType) + " " + Arrays.toString(this.mArgs);
    }

    public static String name() {
        return CLASS_NAME;
    }

    public static void apply(WireBuffer wireBuffer, int i, int i2, short s) {
        apply(wireBuffer, i, i2, s, null);
    }

    public static void apply(WireBuffer wireBuffer, int i, int i2, short s, int[] iArr) {
        wireBuffer.start(172);
        wireBuffer.writeInt(i);
        wireBuffer.writeInt(i2);
        wireBuffer.writeShort(s);
        if (iArr == null) {
            wireBuffer.writeShort(0);
            return;
        }
        wireBuffer.writeShort(iArr.length);
        for (int i3 : iArr) {
            wireBuffer.writeInt(i3);
        }
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        int[] iArr;
        int readInt = wireBuffer.readInt();
        int readInt2 = wireBuffer.readInt();
        short readShort = (short) wireBuffer.readShort();
        int readShort2 = (short) wireBuffer.readShort();
        if (readShort2 != 0) {
            iArr = new int[readShort2];
            for (int i = 0; i < readShort2; i++) {
                iArr[i] = wireBuffer.readInt();
            }
        } else {
            iArr = null;
        }
        list.add(new TimeAttribute(readInt, readInt2, readShort, iArr));
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Time Operations", 172, CLASS_NAME).description("Calculate Information about time").field(0, "id", "id to output").field(0, "longId", "id of time to calculate on").field(9, "type", "the type of calculation").field(9, "argsLength", "The number of additional args").field(0, "args", "argsLength", "The number of additional args");
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation, com.android.internal.widget.remotecompose.core.Operation
    public String deepToString(String str) {
        return str + toString();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r7v4, types: [java.time.LocalDateTime] */
    @Override // com.android.internal.widget.remotecompose.core.PaintOperation
    public void paint(PaintContext paintContext) {
        long currentTimeMillis;
        int i = this.mType & 255;
        RemoteContext context = paintContext.getContext();
        long docLoadTime = context.getDocLoadTime();
        long value = ((LongConstant) context.getObject(this.mTimeId)).getValue();
        long j = 0;
        LocalDateTime localDateTime = null;
        localDateTime = null;
        switch (i == true ? 1 : 0) {
            case false:
            case true:
            case true:
                currentTimeMillis = System.currentTimeMillis();
                j = value - currentTimeMillis;
                break;
            case true:
            case true:
            case true:
                currentTimeMillis = ((LongConstant) context.getObject(this.mArgs[0])).getValue();
                j = value - currentTimeMillis;
                break;
            case true:
            case true:
            case true:
            case true:
            case true:
            case true:
            case true:
                localDateTime = Instant.ofEpochMilli(value).atZone(ZoneOffset.systemDefault()).toLocalDateTime();
                break;
        }
        switch (i == true ? 1 : 0) {
            case false:
            case true:
                context.loadFloat(this.mId, j * 0.001f);
                context.needsRepaint();
                break;
            case true:
            case true:
                context.loadFloat(this.mId, (float) ((j * 0.001d) / 60.0d));
                context.needsRepaint();
                break;
            case true:
            case true:
                context.loadFloat(this.mId, (float) ((j * 0.001d) / 3600.0d));
                break;
            case true:
                context.loadFloat(this.mId, localDateTime.getSecond());
                break;
            case true:
                context.loadFloat(this.mId, localDateTime.getMinute());
                break;
            case true:
                context.loadFloat(this.mId, localDateTime.getHour());
                break;
            case true:
                context.loadFloat(this.mId, localDateTime.getDayOfMonth());
                break;
            case true:
                context.loadFloat(this.mId, localDateTime.getMonthValue() - 1);
                break;
            case true:
                context.loadFloat(this.mId, localDateTime.getDayOfWeek().ordinal());
                break;
            case true:
                context.loadFloat(this.mId, localDateTime.getYear());
                break;
            case true:
                context.loadFloat(this.mId, (value - docLoadTime) * 0.001f);
                context.needsRepaint();
                break;
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addType(CLASS_NAME).add("id", Integer.valueOf(this.mId)).add("timeId", Integer.valueOf(this.mTimeId)).addType(getTypeString()).add("args", Collections.singletonList(this.mArgs));
    }

    private String getTypeString() {
        switch (this.mType & 255) {
            case 0:
                return "TIME_FROM_NOW_SEC";
            case 1:
                return "TIME_FROM_NOW_MIN";
            case 2:
                return "TIME_FROM_NOW_HR";
            case 3:
                return "TIME_FROM_ARG_SEC";
            case 4:
                return "TIME_FROM_ARG_MIN";
            case 5:
                return "TIME_FROM_ARG_HR";
            case 6:
                return "TIME_IN_SEC";
            case 7:
                return "TIME_IN_MIN";
            case 8:
                return "TIME_IN_HR";
            case 9:
                return "TIME_DAY_OF_MONTH";
            case 10:
                return "TIME_MONTH_VALUE";
            case 11:
                return "TIME_DAY_OF_WEEK";
            case 12:
                return "TIME_YEAR";
            case 13:
            default:
                return "INVALID_TIME_TYPE";
            case 14:
                return "TIME_FROM_LOAD_SEC";
        }
    }
}
