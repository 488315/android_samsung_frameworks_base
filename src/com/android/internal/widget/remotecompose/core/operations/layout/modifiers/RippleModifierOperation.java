package com.android.internal.widget.remotecompose.core.operations.layout.modifiers;

import com.android.internal.widget.remotecompose.core.CoreDocument;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.operations.Utils;
import com.android.internal.widget.remotecompose.core.operations.layout.Component;
import com.android.internal.widget.remotecompose.core.operations.layout.RootLayoutComponent;
import com.android.internal.widget.remotecompose.core.operations.layout.TouchHandler;
import com.android.internal.widget.remotecompose.core.operations.paint.PaintBundle;
import com.android.internal.widget.remotecompose.core.operations.utilities.ColorUtils;
import com.android.internal.widget.remotecompose.core.operations.utilities.StringSerializer;
import com.android.internal.widget.remotecompose.core.operations.utilities.easing.FloatAnimation;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import com.android.internal.widget.remotecompose.core.serialize.SerializeTags;
import java.util.List;

/* loaded from: classes6.dex */
public class RippleModifierOperation extends DecoratorModifierOperation implements TouchHandler {
    private static final int OP_CODE = 229;
    long mAnimateRippleStart = 0;
    float mAnimateRippleX = 0.0f;
    float mAnimateRippleY = 0.0f;
    int mAnimateRippleDuration = 1000;
    float mWidth = 0.0f;
    float mHeight = 0.0f;
    public float[] locationInWindow = new float[2];
    PaintBundle mPaint = new PaintBundle();

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.TouchHandler
    public void onTouchCancel(RemoteContext remoteContext, CoreDocument coreDocument, Component component, float f, float f2) {
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.TouchHandler
    public void onTouchDrag(RemoteContext remoteContext, CoreDocument coreDocument, Component component, float f, float f2) {
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.TouchHandler
    public void onTouchUp(RemoteContext remoteContext, CoreDocument coreDocument, Component component, float f, float f2, float f3, float f4) {
    }

    public void animateRipple(float f, float f2) {
        this.mAnimateRippleStart = System.currentTimeMillis();
        this.mAnimateRippleX = f;
        this.mAnimateRippleY = f2;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer);
    }

    public String toString() {
        return "RippleModifier";
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation, com.android.internal.widget.remotecompose.core.Operation
    public void apply(RemoteContext remoteContext) {
        RootLayoutComponent rootLayoutComponent = remoteContext.getDocument().getRootLayoutComponent();
        if (rootLayoutComponent != null) {
            rootLayoutComponent.setHasTouchListeners(true);
        }
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
        if (this.mAnimateRippleStart == 0) {
            return;
        }
        paintContext.needsRepaint();
        float fCurrentTimeMillis = (System.currentTimeMillis() - this.mAnimateRippleStart) / this.mAnimateRippleDuration;
        if (fCurrentTimeMillis > 1.0f) {
            this.mAnimateRippleStart = 0L;
        }
        float fMin = Math.min(1.0f, fCurrentTimeMillis);
        paintContext.save();
        paintContext.savePaint();
        this.mPaint.reset();
        FloatAnimation floatAnimation = new FloatAnimation(1, 1.0f, null, Float.NaN, Float.NaN);
        floatAnimation.setInitialValue(0.0f);
        floatAnimation.setTargetValue(1.0f);
        float f = floatAnimation.get(fMin);
        FloatAnimation floatAnimation2 = new FloatAnimation(1, 0.5f, null, Float.NaN, Float.NaN);
        floatAnimation2.setInitialValue(0.0f);
        floatAnimation2.setTargetValue(1.0f);
        float f2 = floatAnimation2.get(fMin);
        int iInterpolateColor = Utils.interpolateColor(ColorUtils.createColor(250, 250, 250, 180), ColorUtils.createColor(200, 200, 200, 0), f);
        float fMax = Math.max(this.mWidth, this.mHeight) * f2;
        this.mPaint.setColor(iInterpolateColor);
        paintContext.replacePaint(this.mPaint);
        paintContext.clipRect(0.0f, 0.0f, this.mWidth, this.mHeight);
        paintContext.drawCircle(this.mAnimateRippleX, this.mAnimateRippleY, fMax);
        paintContext.restorePaint();
        paintContext.restore();
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.DecoratorComponent
    public void layout(RemoteContext remoteContext, Component component, float f, float f2) {
        this.mWidth = f;
        this.mHeight = f2;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.modifiers.ModifierOperation
    public void serializeToString(int i, StringSerializer stringSerializer) {
        stringSerializer.append(i, "RIPPLE_MODIFIER");
    }

    public static String name() {
        return "RippleModifier";
    }

    public static void apply(WireBuffer wireBuffer) {
        wireBuffer.start(229);
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        list.add(new RippleModifierOperation());
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Layout Operations", 229, name()).description("Ripple modifier. This modifier will do a ripple animation on touch down");
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.TouchHandler
    public void onTouchDown(RemoteContext remoteContext, CoreDocument coreDocument, Component component, float f, float f2) {
        float[] fArr = this.locationInWindow;
        fArr[0] = 0.0f;
        fArr[1] = 0.0f;
        component.getLocationInWindow(fArr);
        float[] fArr2 = this.locationInWindow;
        animateRipple(f - fArr2[0], f2 - fArr2[1]);
        remoteContext.hapticEffect(3);
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addTags(SerializeTags.MODIFIER).addType("RippleModifierOperation").add("animateRippleStart", Long.valueOf(this.mAnimateRippleStart)).add("animateRippleX", Float.valueOf(this.mAnimateRippleX)).add("animateRippleY", Float.valueOf(this.mAnimateRippleY)).add("animateRippleDuration", Integer.valueOf(this.mAnimateRippleDuration)).add("width", Float.valueOf(this.mWidth)).add("height", Float.valueOf(this.mHeight));
    }
}
