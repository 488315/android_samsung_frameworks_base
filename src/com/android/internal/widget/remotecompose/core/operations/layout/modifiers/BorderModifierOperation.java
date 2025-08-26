package com.android.internal.widget.remotecompose.core.operations.layout.modifiers;

import android.app.backup.FullBackup;
import android.app.blob.XmlTags;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.operations.layout.Component;
import com.android.internal.widget.remotecompose.core.operations.paint.PaintBundle;
import com.android.internal.widget.remotecompose.core.operations.utilities.StringSerializer;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import com.android.internal.widget.remotecompose.core.serialize.SerializeTags;
import java.util.List;

/* loaded from: classes6.dex */
public class BorderModifierOperation extends DecoratorModifierOperation {
    public static final String CLASS_NAME = "BorderModifierOperation";
    private static final int OP_CODE = 107;
    float mA;
    float mB;
    float mBorderWidth;
    float mG;
    float mHeight;
    float mR;
    float mRoundedCorner;
    int mShapeType;
    float mWidth;
    float mX;
    float mY;
    public PaintBundle paint = new PaintBundle();

    public static int id() {
        return 107;
    }

    public BorderModifierOperation(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, int i) {
        this.mShapeType = 0;
        this.mX = f;
        this.mY = f2;
        this.mWidth = f3;
        this.mHeight = f4;
        this.mBorderWidth = f5;
        this.mRoundedCorner = f6;
        this.mR = f7;
        this.mG = f8;
        this.mB = f9;
        this.mA = f10;
        this.mShapeType = i;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.modifiers.ModifierOperation
    public void serializeToString(int i, StringSerializer stringSerializer) {
        stringSerializer.append(i, "BORDER = [" + this.mX + ", " + this.mY + ", " + this.mWidth + ", " + this.mHeight + "] color [" + this.mR + ", " + this.mG + ", " + this.mB + ", " + this.mA + "] border [" + this.mBorderWidth + ", " + this.mRoundedCorner + "] shape [" + this.mShapeType + NavigationBarInflaterView.SIZE_MOD_END);
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer, this.mX, this.mY, this.mWidth, this.mHeight, this.mBorderWidth, this.mRoundedCorner, this.mR, this.mG, this.mB, this.mA, this.mShapeType);
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.DecoratorComponent
    public void layout(RemoteContext remoteContext, Component component, float f, float f2) {
        this.mWidth = f;
        this.mHeight = f2;
    }

    public String toString() {
        return "BorderModifierOperation(" + this.mX + "," + this.mY + " - " + this.mWidth + " x " + this.mHeight + ") borderWidth(" + this.mBorderWidth + ") color(" + this.mR + "," + this.mG + "," + this.mB + "," + this.mA + NavigationBarInflaterView.KEY_CODE_END;
    }

    public static String name() {
        return CLASS_NAME;
    }

    public static void apply(WireBuffer wireBuffer, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, int i) {
        wireBuffer.start(107);
        wireBuffer.writeFloat(f);
        wireBuffer.writeFloat(f2);
        wireBuffer.writeFloat(f3);
        wireBuffer.writeFloat(f4);
        wireBuffer.writeFloat(f5);
        wireBuffer.writeFloat(f6);
        wireBuffer.writeFloat(f7);
        wireBuffer.writeFloat(f8);
        wireBuffer.writeFloat(f9);
        wireBuffer.writeFloat(f10);
        wireBuffer.writeInt(i);
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        list.add(new BorderModifierOperation(wireBuffer.readFloat(), wireBuffer.readFloat(), wireBuffer.readFloat(), wireBuffer.readFloat(), wireBuffer.readFloat(), wireBuffer.readFloat(), wireBuffer.readFloat(), wireBuffer.readFloat(), wireBuffer.readFloat(), wireBuffer.readFloat(), wireBuffer.readInt()));
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation
    public void paint(PaintContext paintContext) {
        PaintContext paintContext2;
        paintContext.savePaint();
        this.paint.reset();
        this.paint.setColor(this.mR, this.mG, this.mB, this.mA);
        this.paint.setStrokeWidth(this.mBorderWidth * paintContext.getContext().getDensity());
        this.paint.setStyle(1);
        paintContext.replacePaint(this.paint);
        int i = this.mShapeType;
        if (i == 0) {
            paintContext.drawRect(0.0f, 0.0f, this.mWidth, this.mHeight);
            paintContext2 = paintContext;
        } else {
            float fMin = this.mRoundedCorner;
            if (i == 1) {
                fMin = Math.min(this.mWidth, this.mHeight) / 2.0f;
            }
            float f = fMin;
            paintContext2 = paintContext;
            paintContext2.drawRoundRect(0.0f, 0.0f, this.mWidth, this.mHeight, f, f);
        }
        paintContext2.restorePaint();
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Modifier Operations", 107, CLASS_NAME).description("define the Border Modifier").field(1, "x", "").field(1, "y", "").field(1, "width", "").field(1, "height", "").field(1, "borderWidth", "").field(1, "roundedCorner", "").field(1, "r", "").field(1, "g", "").field(1, XmlTags.TAG_BLOB, "").field(1, FullBackup.APK_TREE_TOKEN, "").field(1, "shapeType", "");
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addTags(SerializeTags.MODIFIER).addType(CLASS_NAME).add("x", Float.valueOf(this.mX)).add("y", Float.valueOf(this.mY)).add("width", Float.valueOf(this.mWidth)).add("height", Float.valueOf(this.mHeight)).add("borderWidth", Float.valueOf(this.mBorderWidth)).add("roundedCornerRadius", Float.valueOf(this.mRoundedCorner)).add("color", this.mA, this.mR, this.mG, this.mB).add("shapeType", ShapeType.getString(this.mShapeType));
    }
}
