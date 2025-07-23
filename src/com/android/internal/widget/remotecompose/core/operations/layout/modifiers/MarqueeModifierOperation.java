package com.android.internal.widget.remotecompose.core.operations.layout.modifiers;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.PerformanceCollector;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.operations.layout.Component;
import com.android.internal.widget.remotecompose.core.operations.layout.LayoutComponent;
import com.android.internal.widget.remotecompose.core.operations.layout.ScrollDelegate;
import com.android.internal.widget.remotecompose.core.operations.utilities.StringSerializer;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import com.android.internal.widget.remotecompose.core.serialize.SerializeTags;
import java.util.List;

/* loaded from: classes6.dex */
public class MarqueeModifierOperation extends DecoratorModifierOperation implements ScrollDelegate {
    public static final String CLASS_NAME = "MarqueeModifierOperation";
    private static final int OP_CODE = 228;
    int mAnimationMode;
    private float mComponentHeight;
    private float mComponentWidth;
    private float mContentHeight;
    private float mContentWidth;
    float mInitialDelayMillis;
    int mIterations;
    float mRepeatDelayMillis;
    float mSpacing;
    float mVelocity;
    private long mLastTime = 0;
    private long mStartTime = 0;
    private float mScrollX = 0.0f;

    public static int id() {
        return 228;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.ScrollDelegate
    public float getScrollY(float f) {
        return 0.0f;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.ScrollDelegate
    public boolean handlesHorizontalScroll() {
        return true;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.ScrollDelegate
    public boolean handlesVerticalScroll() {
        return false;
    }

    public MarqueeModifierOperation(int i, int i2, float f, float f2, float f3, float f4) {
        this.mIterations = i;
        this.mAnimationMode = i2;
        this.mRepeatDelayMillis = f;
        this.mInitialDelayMillis = f2;
        this.mSpacing = f3;
        this.mVelocity = f4;
    }

    public void setContentWidth(float f) {
        this.mContentWidth = f;
    }

    public void setContentHeight(float f) {
        this.mContentHeight = f;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.ScrollDelegate
    public float getScrollX(float f) {
        return this.mScrollX;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.ScrollDelegate
    public void reset() {
        this.mLastTime = 0L;
        this.mScrollX = 0.0f;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer, this.mIterations, this.mAnimationMode, this.mRepeatDelayMillis, this.mInitialDelayMillis, this.mSpacing, this.mVelocity);
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.modifiers.ModifierOperation
    public void serializeToString(int i, StringSerializer stringSerializer) {
        stringSerializer.append(i, "MARQUEE = [" + this.mIterations + NavigationBarInflaterView.SIZE_MOD_END);
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation, com.android.internal.widget.remotecompose.core.Operation
    public String deepToString(String str) {
        StringBuilder sb = new StringBuilder();
        if (str == null) {
            str = "";
        }
        sb.append(str);
        sb.append(toString());
        return sb.toString();
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation
    public void paint(PaintContext paintContext) {
        long currentTimeMillis = System.currentTimeMillis();
        if (this.mLastTime == 0) {
            this.mLastTime = currentTimeMillis;
            this.mStartTime = ((long) this.mInitialDelayMillis) + currentTimeMillis;
            paintContext.needsRepaint();
        }
        if (this.mContentWidth <= this.mComponentWidth || currentTimeMillis - this.mStartTime <= this.mInitialDelayMillis) {
            return;
        }
        float density = paintContext.getContext().getDensity();
        float f = this.mContentWidth - this.mComponentWidth;
        float f2 = f / (density * this.mVelocity);
        this.mScrollX = ((((float) Math.sin(((((((System.currentTimeMillis() - this.mStartTime) / 1000.0f) % f2) / f2) * 2.0f) * 3.141592653589793d) - 1.5707963267948966d)) + 1.0f) / 2.0f) * (-f);
        paintContext.needsRepaint();
    }

    public String toString() {
        return "MarqueeModifierOperation(" + this.mIterations + NavigationBarInflaterView.KEY_CODE_END;
    }

    public static String name() {
        return CLASS_NAME;
    }

    public static void apply(WireBuffer wireBuffer, int i, int i2, float f, float f2, float f3, float f4) {
        wireBuffer.start(228);
        wireBuffer.writeInt(i);
        wireBuffer.writeInt(i2);
        wireBuffer.writeFloat(f);
        wireBuffer.writeFloat(f2);
        wireBuffer.writeFloat(f3);
        wireBuffer.writeFloat(f4);
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        list.add(new MarqueeModifierOperation(wireBuffer.readInt(), wireBuffer.readInt(), wireBuffer.readFloat(), wireBuffer.readFloat(), wireBuffer.readFloat(), wireBuffer.readFloat()));
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Modifier Operations", 228, CLASS_NAME).description("specify a Marquee Modifier").field(1, "value", "");
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.DecoratorComponent
    public void layout(RemoteContext remoteContext, Component component, float f, float f2) {
        this.mComponentWidth = f;
        this.mComponentHeight = f2;
        if (component instanceof LayoutComponent) {
            LayoutComponent layoutComponent = (LayoutComponent) component;
            setContentWidth(layoutComponent.minIntrinsicWidth(remoteContext));
            setContentHeight(layoutComponent.minIntrinsicHeight(remoteContext));
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addTags(SerializeTags.MODIFIER).addType(CLASS_NAME).add(PerformanceCollector.METRIC_KEY_ITERATIONS, Integer.valueOf(this.mIterations)).add("animationMode", Integer.valueOf(this.mAnimationMode)).add("repeatDelayMillis", Float.valueOf(this.mRepeatDelayMillis)).add("initialDelayMillis", Float.valueOf(this.mInitialDelayMillis)).add("spacing", Float.valueOf(this.mSpacing)).add("velocity", Float.valueOf(this.mVelocity));
    }
}
