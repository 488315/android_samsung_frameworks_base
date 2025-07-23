package com.android.internal.widget.remotecompose.core.operations;

import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.PaintOperation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import java.util.List;

/* loaded from: classes6.dex */
public class TextMeasure extends PaintOperation {
    private static final String CLASS_NAME = "TextMeasure";
    public static final int MEASURE_BOTTOM = 5;
    public static final int MEASURE_HEIGHT = 1;
    public static final int MEASURE_LEFT = 2;
    public static final int MEASURE_MAX_HEIGHT_FLAG = 512;
    public static final int MEASURE_MONOSPACE_FLAG = 256;
    public static final int MEASURE_RIGHT = 3;
    public static final int MEASURE_TOP = 4;
    public static final int MEASURE_WIDTH = 0;
    private static final int OP_CODE = 155;
    float[] mBounds = new float[4];
    public int mId;
    public int mTextId;
    public int mType;

    public static int id() {
        return 155;
    }

    public TextMeasure(int i, int i2, int i3) {
        this.mId = i;
        this.mTextId = i2;
        this.mType = i3;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer, this.mId, this.mTextId, this.mType);
    }

    public String toString() {
        return "FloatConstant[" + this.mId + "] = " + this.mTextId + " " + this.mType;
    }

    public static String name() {
        return CLASS_NAME;
    }

    public static void apply(WireBuffer wireBuffer, int i, int i2, int i3) {
        wireBuffer.start(155);
        wireBuffer.writeInt(i);
        wireBuffer.writeInt(i2);
        wireBuffer.writeInt(i3);
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        list.add(new TextMeasure(wireBuffer.readInt(), wireBuffer.readInt(), wireBuffer.readInt()));
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Expressions Operations", 155, CLASS_NAME).description("Measure text").field(0, "id", "id of float result of the measure").field(0, "textId", "id of text").field(0, "type", "type: measure 0=width,1=height");
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation, com.android.internal.widget.remotecompose.core.Operation
    public String deepToString(String str) {
        return str + toString();
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation
    public void paint(PaintContext paintContext) {
        int i = this.mType;
        int i2 = i & 255;
        paintContext.getTextBounds(this.mTextId, 0, -1, i >> 8, this.mBounds);
        if (i2 == 0) {
            RemoteContext context = paintContext.getContext();
            int i3 = this.mId;
            float[] fArr = this.mBounds;
            context.loadFloat(i3, fArr[2] - fArr[0]);
            return;
        }
        if (i2 == 1) {
            RemoteContext context2 = paintContext.getContext();
            int i4 = this.mId;
            float[] fArr2 = this.mBounds;
            context2.loadFloat(i4, fArr2[3] - fArr2[1]);
            return;
        }
        if (i2 == 2) {
            paintContext.getContext().loadFloat(this.mId, this.mBounds[0]);
            return;
        }
        if (i2 == 3) {
            paintContext.getContext().loadFloat(this.mId, this.mBounds[2]);
        } else if (i2 == 4) {
            paintContext.getContext().loadFloat(this.mId, this.mBounds[1]);
        } else {
            if (i2 != 5) {
                return;
            }
            paintContext.getContext().loadFloat(this.mId, this.mBounds[3]);
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addType(CLASS_NAME).add("id", Integer.valueOf(this.mId)).add("textId", Integer.valueOf(this.mTextId)).add("measureType", typeToString());
    }

    private String typeToString() {
        int i = this.mType & 255;
        if (i == 0) {
            return "MEASURE_WIDTH";
        }
        if (i == 1) {
            return "MEASURE_HEIGHT";
        }
        if (i == 2) {
            return "MEASURE_LEFT";
        }
        if (i == 3) {
            return "MEASURE_RIGHT";
        }
        if (i == 4) {
            return "MEASURE_TOP";
        }
        if (i == 5) {
            return "MEASURE_BOTTOM";
        }
        return "INVALID_TYPE";
    }
}
