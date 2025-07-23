package com.android.internal.widget.remotecompose.core.operations.layout.managers;

import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.VariableSupport;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.operations.BitmapData;
import com.android.internal.widget.remotecompose.core.operations.layout.Component;
import com.android.internal.widget.remotecompose.core.operations.layout.measure.ComponentMeasure;
import com.android.internal.widget.remotecompose.core.operations.layout.measure.MeasurePass;
import com.android.internal.widget.remotecompose.core.operations.layout.measure.Size;
import com.android.internal.widget.remotecompose.core.operations.paint.PaintBundle;
import com.android.internal.widget.remotecompose.core.operations.utilities.ImageScaling;
import com.android.internal.widget.remotecompose.core.operations.utilities.StringSerializer;
import java.util.List;

/* loaded from: classes6.dex */
public class ImageLayout extends LayoutManager implements VariableSupport {
    private static final boolean DEBUG = false;
    private float mAlpha;
    private int mBitmapId;
    PaintBundle mPaint;
    private int mScaleType;
    ImageScaling mScaling;

    public static int id() {
        return 234;
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void registerListening(RemoteContext remoteContext) {
        int i = this.mBitmapId;
        if (i != -1) {
            remoteContext.listensTo(i, this);
        }
    }

    public ImageLayout(Component component, int i, int i2, int i3, float f, float f2, float f3, float f4, int i4, float f5) {
        super(component, i, i2, f, f2, f3, f4);
        this.mBitmapId = -1;
        this.mAlpha = 1.0f;
        this.mScaling = new ImageScaling();
        this.mPaint = new PaintBundle();
        this.mBitmapId = i3;
        this.mScaleType = i4 & 255;
        this.mAlpha = f5;
    }

    public ImageLayout(Component component, int i, int i2, int i3, int i4, float f) {
        this(component, i, i2, i3, 0.0f, 0.0f, 0.0f, 0.0f, i4, f);
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.managers.LayoutManager
    public void computeWrapSize(PaintContext paintContext, float f, float f2, boolean z, boolean z2, MeasurePass measurePass, Size size) {
        if (((BitmapData) paintContext.getContext().getObject(this.mBitmapId)) != null) {
            size.setWidth(r0.getWidth());
            size.setHeight(r0.getHeight());
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.managers.LayoutManager
    public void computeSize(PaintContext paintContext, float f, float f2, float f3, float f4, MeasurePass measurePass) {
        float computeModifierDefinedWidth = computeModifierDefinedWidth(paintContext.getContext());
        float computeModifierDefinedHeight = computeModifierDefinedHeight(paintContext.getContext());
        ComponentMeasure componentMeasure = measurePass.get(this);
        componentMeasure.setW(computeModifierDefinedWidth);
        componentMeasure.setH(computeModifierDefinedHeight);
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.LayoutComponent, com.android.internal.widget.remotecompose.core.operations.layout.Component
    public void paintingComponent(PaintContext paintContext) {
        PaintContext paintContext2;
        paintContext.save();
        paintContext.translate(this.mX, this.mY);
        this.mComponentModifiers.paint(paintContext);
        float f = this.mPaddingLeft;
        float f2 = this.mPaddingTop;
        paintContext.translate(f, f2);
        float f3 = (this.mWidth - this.mPaddingLeft) - this.mPaddingRight;
        float f4 = (this.mHeight - this.mPaddingTop) - this.mPaddingBottom;
        paintContext.clipRect(0.0f, 0.0f, f3, f4);
        BitmapData bitmapData = (BitmapData) paintContext.getContext().getObject(this.mBitmapId);
        if (bitmapData != null) {
            this.mScaling.setup(0.0f, 0.0f, bitmapData.getWidth(), bitmapData.getHeight(), 0.0f, 0.0f, f3, f4, this.mScaleType, 1.0f);
            paintContext.savePaint();
            if (this.mAlpha == 1.0f) {
                paintContext2 = paintContext;
                paintContext2.drawBitmap(this.mBitmapId, 0, 0, bitmapData.getWidth(), bitmapData.getHeight(), (int) this.mScaling.mFinalDstLeft, (int) this.mScaling.mFinalDstTop, (int) this.mScaling.mFinalDstRight, (int) this.mScaling.mFinalDstBottom, -1);
            } else {
                paintContext.savePaint();
                this.mPaint.reset();
                this.mPaint.setColor(0.0f, 0.0f, 0.0f, this.mAlpha);
                paintContext.applyPaint(this.mPaint);
                paintContext2 = paintContext;
                paintContext2.drawBitmap(this.mBitmapId, 0, 0, bitmapData.getWidth(), bitmapData.getHeight(), (int) this.mScaling.mFinalDstLeft, (int) this.mScaling.mFinalDstTop, (int) this.mScaling.mFinalDstRight, (int) this.mScaling.mFinalDstBottom, -1);
                paintContext2.restorePaint();
            }
            paintContext2.restorePaint();
        } else {
            paintContext2 = paintContext;
        }
        paintContext2.translate(-f, -f2);
        paintContext2.restore();
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.LayoutComponent, com.android.internal.widget.remotecompose.core.operations.layout.Component
    public String toString() {
        return "IMAGE_LAYOUT [" + this.mComponentId + ":" + this.mAnimationId + "] (" + this.mX + ", " + this.mY + " - " + this.mWidth + " x " + this.mHeight + ") " + this.mVisibility;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.Component
    protected String getSerializedName() {
        return "IMAGE_LAYOUT";
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.Component, com.android.internal.widget.remotecompose.core.SerializableToString
    public void serializeToString(int i, StringSerializer stringSerializer) {
        stringSerializer.append(i, getSerializedName() + " [" + this.mComponentId + ":" + this.mAnimationId + "] = [" + this.mX + ", " + this.mY + ", " + this.mWidth + ", " + this.mHeight + "] " + this.mVisibility + " (" + this.mBitmapId + "\")");
    }

    public static String name() {
        return "ImageLayout";
    }

    public static void apply(WireBuffer wireBuffer, int i, int i2, int i3, int i4, float f) {
        wireBuffer.start(id());
        wireBuffer.writeInt(i);
        wireBuffer.writeInt(i2);
        wireBuffer.writeInt(i3);
        wireBuffer.writeInt(i4);
        wireBuffer.writeFloat(f);
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        list.add(new ImageLayout(null, wireBuffer.readInt(), wireBuffer.readInt(), wireBuffer.readInt(), wireBuffer.readInt(), wireBuffer.readFloat()));
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Layout Operations", id(), name()).description("Image layout implementation.\n\n").field(0, "COMPONENT_ID", "unique id for this component").field(0, "ANIMATION_ID", "id used to match components, for animation purposes").field(0, "BITMAP_ID", "bitmap id").field(0, "SCALE_TYPE", "scale type").field(1, "ALPHA", "alpha");
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.Component, com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer, this.mComponentId, this.mAnimationId, this.mBitmapId, this.mScaleType, this.mAlpha);
    }
}
