package com.android.internal.widget.remotecompose.core.operations;

import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.PaintOperation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import com.android.internal.widget.remotecompose.core.serialize.Serializable;
import java.util.List;

/* loaded from: classes6.dex */
public class TextAttribute extends PaintOperation implements Serializable {
    private static final String CLASS_NAME = "TextMeasure";
    public static final short MEASURE_BOTTOM = 5;
    public static final short MEASURE_HEIGHT = 1;
    public static final short MEASURE_LEFT = 2;
    public static final int MEASURE_MAX_HEIGHT_FLAG = 512;
    public static final int MEASURE_MONOSPACE_FLAG = 256;
    public static final short MEASURE_RIGHT = 3;
    public static final short MEASURE_TOP = 4;
    public static final short MEASURE_WIDTH = 0;
    private static final int OP_CODE = 170;
    public static final short TEXT_LENGTH = 6;
    float[] mBounds = new float[4];
    public int mId;
    public int mTextId;
    public short mType;

    public static int id() {
        return 170;
    }

    public TextAttribute(int i, int i2, short s) {
        this.mId = i;
        this.mTextId = i2;
        this.mType = s;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer, this.mId, this.mTextId, this.mType);
    }

    public String toString() {
        return "FloatConstant[" + this.mId + "] = " + this.mTextId + " " + ((int) this.mType);
    }

    public static String name() {
        return CLASS_NAME;
    }

    public static void apply(WireBuffer wireBuffer, int i, int i2, short s) {
        wireBuffer.start(170);
        wireBuffer.writeInt(i);
        wireBuffer.writeInt(i2);
        wireBuffer.writeShort(s);
        wireBuffer.writeShort(0);
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        int readInt = wireBuffer.readInt();
        int readInt2 = wireBuffer.readInt();
        short readShort = (short) wireBuffer.readShort();
        wireBuffer.readShort();
        list.add(new TextAttribute(readInt, readInt2, readShort));
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Expressions Operations", 170, CLASS_NAME).description("Measure text").field(0, "id", "id of float result of the measure").field(0, "textId", "id of text").field(0, "type", "type: measure 0=width,1=height");
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation, com.android.internal.widget.remotecompose.core.Operation
    public String deepToString(String str) {
        return str + toString();
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation
    public void paint(PaintContext paintContext) {
        PaintContext paintContext2;
        short s = this.mType;
        int i = s & 255;
        int i2 = s >> 8;
        if (i <= 5) {
            paintContext2 = paintContext;
            paintContext2.getTextBounds(this.mTextId, 0, -1, i2, this.mBounds);
        } else {
            paintContext2 = paintContext;
        }
        switch (i) {
            case 0:
                RemoteContext context = paintContext2.getContext();
                int i3 = this.mId;
                float[] fArr = this.mBounds;
                context.loadFloat(i3, fArr[2] - fArr[0]);
                break;
            case 1:
                RemoteContext context2 = paintContext2.getContext();
                int i4 = this.mId;
                float[] fArr2 = this.mBounds;
                context2.loadFloat(i4, fArr2[3] - fArr2[1]);
                break;
            case 2:
                paintContext2.getContext().loadFloat(this.mId, this.mBounds[0]);
                break;
            case 3:
                paintContext2.getContext().loadFloat(this.mId, this.mBounds[2]);
                break;
            case 4:
                paintContext2.getContext().loadFloat(this.mId, this.mBounds[1]);
                break;
            case 5:
                paintContext2.getContext().loadFloat(this.mId, this.mBounds[3]);
                break;
            case 6:
                paintContext2.getContext().loadFloat(this.mId, paintContext2.getText(this.mTextId).length());
                break;
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addType(CLASS_NAME).add("id", Integer.valueOf(this.mId)).add("textId", Integer.valueOf(this.mTextId)).add("measureType", typeToString());
    }

    private String typeToString() {
        switch (this.mType) {
            case 0:
                return "MEASURE_WIDTH";
            case 1:
                return "MEASURE_HEIGHT";
            case 2:
                return "MEASURE_LEFT";
            case 3:
                return "MEASURE_RIGHT";
            case 4:
                return "MEASURE_TOP";
            case 5:
                return "MEASURE_BOTTOM";
            case 6:
                return "TEXT_LENGTH";
            default:
                return "INVALID_TYPE";
        }
    }
}
