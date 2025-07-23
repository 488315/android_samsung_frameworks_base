package com.android.internal.widget.remotecompose.core.operations;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.PaintOperation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.VariableSupport;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import com.android.internal.widget.remotecompose.core.serialize.Serializable;
import java.util.List;

/* loaded from: classes6.dex */
public class DrawTextAnchored extends PaintOperation implements VariableSupport, Serializable {
    public static final int ANCHOR_MONOSPACE_MEASURE = 2;
    public static final int ANCHOR_TEXT_RTL = 1;
    private static final String CLASS_NAME = "DrawTextAnchored";
    public static final int MEASURE_EVERY_TIME = 4;
    private static final int OP_CODE = 133;
    float[] mBounds = new float[4];
    int mFlags;
    String mLastString;
    float mOutPanX;
    float mOutPanY;
    float mOutX;
    float mOutY;
    float mPanX;
    float mPanY;
    int mTextID;
    float mX;
    float mY;

    public static int id() {
        return 133;
    }

    public DrawTextAnchored(int i, float f, float f2, float f3, float f4, int i2) {
        this.mTextID = i;
        this.mX = f;
        this.mY = f2;
        this.mOutX = f;
        this.mOutY = f2;
        this.mFlags = i2;
        this.mPanX = f3;
        this.mOutPanX = f3;
        this.mPanY = f4;
        this.mOutPanY = f4;
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void updateVariables(RemoteContext remoteContext) {
        this.mOutX = Float.isNaN(this.mX) ? remoteContext.getFloat(Utils.idFromNan(this.mX)) : this.mX;
        this.mOutY = Float.isNaN(this.mY) ? remoteContext.getFloat(Utils.idFromNan(this.mY)) : this.mY;
        this.mOutPanX = Float.isNaN(this.mPanX) ? remoteContext.getFloat(Utils.idFromNan(this.mPanX)) : this.mPanX;
        this.mOutPanY = Float.isNaN(this.mPanY) ? remoteContext.getFloat(Utils.idFromNan(this.mPanY)) : this.mPanY;
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void registerListening(RemoteContext remoteContext) {
        if (Float.isNaN(this.mX)) {
            remoteContext.listensTo(Utils.idFromNan(this.mX), this);
        }
        if (Float.isNaN(this.mY)) {
            remoteContext.listensTo(Utils.idFromNan(this.mY), this);
        }
        if (Float.isNaN(this.mPanX)) {
            remoteContext.listensTo(Utils.idFromNan(this.mPanX), this);
        }
        if (!Float.isNaN(this.mPanY) || Utils.idFromNan(this.mPanY) <= 0) {
            return;
        }
        remoteContext.listensTo(Utils.idFromNan(this.mPanY), this);
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer, this.mTextID, this.mX, this.mY, this.mPanX, this.mPanY, this.mFlags);
    }

    public String toString() {
        return "DrawTextAnchored [" + this.mTextID + "] " + floatToStr(this.mX) + ", " + floatToStr(this.mY) + ", " + floatToStr(this.mPanX) + ", " + floatToStr(this.mPanY) + ", " + Integer.toBinaryString(this.mFlags);
    }

    private static String floatToStr(float f) {
        if (Float.isNaN(f)) {
            return NavigationBarInflaterView.SIZE_MOD_START + Utils.idFromNan(f) + NavigationBarInflaterView.SIZE_MOD_END;
        }
        return Float.toString(f);
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        list.add(new DrawTextAnchored(wireBuffer.readInt(), wireBuffer.readFloat(), wireBuffer.readFloat(), wireBuffer.readFloat(), wireBuffer.readFloat(), wireBuffer.readInt()));
    }

    public static String name() {
        return CLASS_NAME;
    }

    public static void apply(WireBuffer wireBuffer, int i, float f, float f2, float f3, float f4, int i2) {
        wireBuffer.start(133);
        wireBuffer.writeInt(i);
        wireBuffer.writeFloat(f);
        wireBuffer.writeFloat(f2);
        wireBuffer.writeFloat(f3);
        wireBuffer.writeFloat(f4);
        wireBuffer.writeInt(i2);
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Draw Operations", 133, CLASS_NAME).description("Draw text centered about an anchor point").field(0, "textId", "id of bitmap").field(1, "x", "The x-position of the anchor point").field(1, "y", "The y-position of the anchor point").field(1, "panX", "The pan from left(-1) to right(1) 0 being centered").field(1, "panY", "The pan from top(-1) to bottom(1) 0 being centered").field(0, "flags", "Change the behaviour");
    }

    private float getHorizontalOffset() {
        float[] fArr = this.mBounds;
        float f = fArr[2];
        float f2 = fArr[0];
        return (((0.0f - ((f - f2) * 1.0f)) * (this.mOutPanX + 1.0f)) / 2.0f) - (f2 * 1.0f);
    }

    private float getVerticalOffset() {
        float[] fArr = this.mBounds;
        float f = fArr[3];
        float f2 = fArr[1];
        return (((0.0f - ((f - f2) * 1.0f)) * (1.0f - this.mOutPanY)) / 2.0f) - (f2 * 1.0f);
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation
    public void paint(PaintContext paintContext) {
        int i = (this.mFlags & 2) != 0 ? 1 : 0;
        String text = paintContext.getText(this.mTextID);
        if (text != this.mLastString || (this.mFlags & 4) != 0) {
            this.mLastString = text;
            paintContext.getTextBounds(this.mTextID, 0, -1, i, this.mBounds);
        }
        paintContext.drawTextRun(this.mTextID, 0, -1, 0, 1, this.mOutX + getHorizontalOffset(), Float.isNaN(this.mOutPanY) ? this.mOutY : this.mOutY + getVerticalOffset(), (this.mFlags & 1) == 1);
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addType(CLASS_NAME).add("textId", Integer.valueOf(this.mTextID)).add("x", this.mX, this.mOutX).add("y", this.mY, this.mOutY).add("panX", this.mPanX, this.mOutPanX).add("panY", this.mPanY, this.mOutPanY).add("flags", Integer.valueOf(this.mFlags));
    }
}
