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
public class BackgroundModifierOperation extends DecoratorModifierOperation {
    private static final String CLASS_NAME = "BackgroundModifierOperation";
    private static final int OP_CODE = 55;
    float mA;
    float mB;
    float mG;
    float mHeight;
    public PaintBundle mPaint = new PaintBundle();
    float mR;
    int mShapeType;
    float mWidth;
    float mX;
    float mY;

    public static int id() {
        return 55;
    }

    public BackgroundModifierOperation(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, int i) {
        this.mShapeType = 0;
        this.mX = f;
        this.mY = f2;
        this.mWidth = f3;
        this.mHeight = f4;
        this.mR = f5;
        this.mG = f6;
        this.mB = f7;
        this.mA = f8;
        this.mShapeType = i;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer, this.mX, this.mY, this.mWidth, this.mHeight, this.mR, this.mG, this.mB, this.mA, this.mShapeType);
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.modifiers.ModifierOperation
    public void serializeToString(int i, StringSerializer stringSerializer) {
        stringSerializer.append(i, "BACKGROUND = [" + this.mX + ", " + this.mY + ", " + this.mWidth + ", " + this.mHeight + "] color [" + this.mR + ", " + this.mG + ", " + this.mB + ", " + this.mA + "] shape [" + this.mShapeType + NavigationBarInflaterView.SIZE_MOD_END);
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.DecoratorComponent
    public void layout(RemoteContext remoteContext, Component component, float f, float f2) {
        this.mWidth = f;
        this.mHeight = f2;
    }

    public String toString() {
        return "BackgroundModifierOperation(" + this.mWidth + " x " + this.mHeight + NavigationBarInflaterView.KEY_CODE_END;
    }

    public static String name() {
        return CLASS_NAME;
    }

    public static void apply(WireBuffer wireBuffer, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, int i) {
        wireBuffer.start(55);
        wireBuffer.writeFloat(f);
        wireBuffer.writeFloat(f2);
        wireBuffer.writeFloat(f3);
        wireBuffer.writeFloat(f4);
        wireBuffer.writeFloat(f5);
        wireBuffer.writeFloat(f6);
        wireBuffer.writeFloat(f7);
        wireBuffer.writeFloat(f8);
        wireBuffer.writeInt(i);
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        list.add(new BackgroundModifierOperation(wireBuffer.readFloat(), wireBuffer.readFloat(), wireBuffer.readFloat(), wireBuffer.readFloat(), wireBuffer.readFloat(), wireBuffer.readFloat(), wireBuffer.readFloat(), wireBuffer.readFloat(), wireBuffer.readInt()));
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation
    public void paint(PaintContext paintContext) {
        paintContext.savePaint();
        this.mPaint.reset();
        this.mPaint.setStyle(0);
        this.mPaint.setColor(this.mR, this.mG, this.mB, this.mA);
        paintContext.replacePaint(this.mPaint);
        int i = this.mShapeType;
        if (i == 0) {
            paintContext.drawRect(0.0f, 0.0f, this.mWidth, this.mHeight);
        } else if (i == 1) {
            float f = this.mWidth;
            float f2 = this.mHeight;
            paintContext.drawCircle(f / 2.0f, f2 / 2.0f, Math.min(f, f2) / 2.0f);
        }
        paintContext.restorePaint();
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Modifier Operations", 55, CLASS_NAME).description("define the Background Modifier").field(1, "x", "").field(1, "y", "").field(1, "width", "").field(1, "height", "").field(1, "r", "").field(1, "g", "").field(1, XmlTags.TAG_BLOB, "").field(1, FullBackup.APK_TREE_TOKEN, "").field(1, "shapeType", "0 for RECTANGLE, 1 for CIRCLE");
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addTags(SerializeTags.MODIFIER).addType(CLASS_NAME).add("x", Float.valueOf(this.mX)).add("y", Float.valueOf(this.mY)).add("width", Float.valueOf(this.mWidth)).add("height", Float.valueOf(this.mHeight)).add("color", this.mA, this.mR, this.mG, this.mB).add("shapeType", ShapeType.getString(this.mShapeType));
    }
}
