package com.android.internal.widget.remotecompose.core.operations.layout.modifiers;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.operations.DrawBase4;
import com.android.internal.widget.remotecompose.core.operations.layout.Component;
import com.android.internal.widget.remotecompose.core.operations.layout.DecoratorComponent;
import com.android.internal.widget.remotecompose.core.operations.utilities.StringSerializer;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import com.android.internal.widget.remotecompose.core.serialize.SerializeTags;
import java.util.List;

/* loaded from: classes6.dex */
public class RoundedClipRectModifierOperation extends DrawBase4 implements ModifierOperation, DecoratorComponent {
    public static final String CLASS_NAME = "RoundedClipRectModifierOperation";
    public static final int OP_CODE = 54;
    float mHeight;
    float mWidth;

    public static int id() {
        return 54;
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        read(new DrawBase4.Maker() { // from class: com.android.internal.widget.remotecompose.core.operations.layout.modifiers.RoundedClipRectModifierOperation$$ExternalSyntheticLambda0
            @Override // com.android.internal.widget.remotecompose.core.operations.DrawBase4.Maker
            public final DrawBase4 create(float f, float f2, float f3, float f4) {
                return new RoundedClipRectModifierOperation(f, f2, f3, f4);
            }
        }, wireBuffer, list);
    }

    public static String name() {
        return CLASS_NAME;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.DrawBase4
    protected void write(WireBuffer wireBuffer, float f, float f2, float f3, float f4) {
        apply(wireBuffer, f, f2, f3, f4);
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Modifier Operations", id(), CLASS_NAME).description("clip with rectangle").field(1, "topStart", "The topStart radius of the rectangle to intersect with the current clip").field(1, "topEnd", "The topEnd radius of the rectangle to intersect with the current clip").field(1, "bottomStart", "The bottomStart radius of the rectangle to intersect with the current clip").field(1, "bottomEnd", "The bottomEnd radius of the rectangle to intersect with the current clip");
    }

    public RoundedClipRectModifierOperation(float f, float f2, float f3, float f4) {
        super(f, f2, f3, f4);
        this.mName = CLASS_NAME;
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation
    public void paint(PaintContext paintContext) {
        paintContext.roundedClipRect(this.mWidth, this.mHeight, this.mX1, this.mY1, this.mX2, this.mY2);
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.DecoratorComponent
    public void layout(RemoteContext remoteContext, Component component, float f, float f2) {
        this.mWidth = f;
        this.mHeight = f2;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.modifiers.ModifierOperation
    public void serializeToString(int i, StringSerializer stringSerializer) {
        stringSerializer.append(i, "ROUNDED_CLIP_RECT = [" + this.mWidth + ", " + this.mHeight + ", " + this.mX1 + ", " + this.mY1 + ", " + this.mX2 + ", " + this.mY2 + NavigationBarInflaterView.SIZE_MOD_END);
    }

    public static void apply(WireBuffer wireBuffer, float f, float f2, float f3, float f4) {
        write(wireBuffer, 54, f, f2, f3, f4);
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        serialize(mapSerializer, "topStart", "topEnd", "bottomStart", "bottomEnd").addTags(SerializeTags.MODIFIER).addType(CLASS_NAME).add("width", Float.valueOf(this.mWidth)).add("height", Float.valueOf(this.mHeight));
    }
}
