package com.android.internal.widget.remotecompose.core.operations;

import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.PaintOperation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.VariableSupport;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import java.util.List;

/* loaded from: classes6.dex */
public class DrawText extends PaintOperation implements VariableSupport {
    private static final String CLASS_NAME = "DrawText";
    private static final int OP_CODE = 43;
    int mContextEnd;
    int mContextStart;
    int mEnd;
    float mOutX;
    float mOutY;
    boolean mRtl;
    int mStart;
    int mTextID;
    float mX;
    float mY;

    public static int id() {
        return 43;
    }

    public DrawText(int i, int i2, int i3, int i4, int i5, float f, float f2, boolean z) {
        this.mTextID = i;
        this.mStart = i2;
        this.mEnd = i3;
        this.mContextStart = i4;
        this.mContextEnd = i5;
        this.mX = f;
        this.mOutX = f;
        this.mY = f2;
        this.mOutY = f2;
        this.mRtl = z;
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void updateVariables(RemoteContext remoteContext) {
        this.mOutX = Float.isNaN(this.mX) ? remoteContext.getFloat(Utils.idFromNan(this.mX)) : this.mX;
        this.mOutY = Float.isNaN(this.mY) ? remoteContext.getFloat(Utils.idFromNan(this.mY)) : this.mY;
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void registerListening(RemoteContext remoteContext) {
        if (Float.isNaN(this.mX)) {
            remoteContext.listensTo(Utils.idFromNan(this.mX), this);
        }
        if (Float.isNaN(this.mY)) {
            remoteContext.listensTo(Utils.idFromNan(this.mY), this);
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer, this.mTextID, this.mStart, this.mEnd, this.mContextStart, this.mContextEnd, this.mX, this.mY, this.mRtl);
    }

    public String toString() {
        return "DrawTextRun [" + this.mTextID + "] " + this.mStart + ", " + this.mEnd + ", " + Utils.floatToString(this.mX, this.mOutX) + ", " + Utils.floatToString(this.mY, this.mOutY);
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        list.add(new DrawText(wireBuffer.readInt(), wireBuffer.readInt(), wireBuffer.readInt(), wireBuffer.readInt(), wireBuffer.readInt(), wireBuffer.readFloat(), wireBuffer.readFloat(), wireBuffer.readBoolean()));
    }

    public static String name() {
        return CLASS_NAME;
    }

    public static void apply(WireBuffer wireBuffer, int i, int i2, int i3, int i4, int i5, float f, float f2, boolean z) {
        wireBuffer.start(43);
        wireBuffer.writeInt(i);
        wireBuffer.writeInt(i2);
        wireBuffer.writeInt(i3);
        wireBuffer.writeInt(i4);
        wireBuffer.writeInt(i5);
        wireBuffer.writeFloat(f);
        wireBuffer.writeFloat(f2);
        wireBuffer.writeBoolean(z);
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Draw Operations", id(), CLASS_NAME).description("Draw a run of text, all in a single direction").field(0, "textId", "id of bitmap").field(0, "start", "The start of the text to render. -1=end of string").field(0, "end", "The end of the text to render").field(0, "contextStart", "the index of the start of the shaping context").field(0, "contextEnd", "the index of the end of the shaping context").field(1, "x", "The x position at which to draw the text").field(1, "y", "The y position at which to draw the text").field(2, "RTL", "Whether the run is in RTL direction");
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation
    public void paint(PaintContext paintContext) {
        paintContext.drawTextRun(this.mTextID, this.mStart, this.mEnd, this.mContextStart, this.mContextEnd, this.mOutX, this.mOutY, this.mRtl);
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addType(CLASS_NAME).add("textId", Integer.valueOf(this.mTextID)).add("start", Integer.valueOf(this.mStart)).add("end", Integer.valueOf(this.mEnd)).add("contextStart", Integer.valueOf(this.mContextStart)).add("contextEnd", Integer.valueOf(this.mContextEnd)).add("x", this.mX, this.mOutX).add("y", this.mY, this.mOutY).add("rtl", Boolean.valueOf(this.mRtl));
    }
}
