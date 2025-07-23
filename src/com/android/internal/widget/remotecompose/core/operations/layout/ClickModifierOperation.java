package com.android.internal.widget.remotecompose.core.operations.layout;

import com.android.internal.widget.remotecompose.core.CoreDocument;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.PaintOperation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.operations.TextData;
import com.android.internal.widget.remotecompose.core.operations.Utils;
import com.android.internal.widget.remotecompose.core.operations.layout.modifiers.ModifierOperation;
import com.android.internal.widget.remotecompose.core.operations.paint.PaintBundle;
import com.android.internal.widget.remotecompose.core.operations.utilities.ColorUtils;
import com.android.internal.widget.remotecompose.core.operations.utilities.StringSerializer;
import com.android.internal.widget.remotecompose.core.operations.utilities.easing.FloatAnimation;
import com.android.internal.widget.remotecompose.core.semantics.AccessibleComponent;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import com.android.internal.widget.remotecompose.core.serialize.SerializeTags;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes6.dex */
public class ClickModifierOperation extends PaintOperation implements Container, ModifierOperation, DecoratorComponent, ClickHandler, AccessibleComponent {
    private static final int OP_CODE = 59;
    long mAnimateRippleStart = 0;
    float mAnimateRippleX = 0.0f;
    float mAnimateRippleY = 0.0f;
    int mAnimateRippleDuration = 1000;
    float mWidth = 0.0f;
    float mHeight = 0.0f;
    public float[] locationInWindow = new float[2];
    PaintBundle mPaint = new PaintBundle();
    public ArrayList<Operation> mList = new ArrayList<>();

    @Override // com.android.internal.widget.remotecompose.core.semantics.AccessibleComponent
    public boolean isClickable() {
        return true;
    }

    @Override // com.android.internal.widget.remotecompose.core.semantics.AccessibleComponent
    public AccessibleComponent.Role getRole() {
        return AccessibleComponent.Role.BUTTON;
    }

    @Override // com.android.internal.widget.remotecompose.core.semantics.AccessibleComponent
    public AccessibleComponent.Mode getMode() {
        return AccessibleComponent.Mode.MERGE;
    }

    public void animateRipple(float f, float f2) {
        this.mAnimateRippleStart = System.currentTimeMillis();
        this.mAnimateRippleX = f;
        this.mAnimateRippleY = f2;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.Container
    public ArrayList<Operation> getList() {
        return this.mList;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer);
    }

    public String toString() {
        return "ClickModifier";
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation, com.android.internal.widget.remotecompose.core.Operation
    public void apply(RemoteContext remoteContext) {
        RootLayoutComponent rootLayoutComponent = remoteContext.getDocument().getRootLayoutComponent();
        if (rootLayoutComponent != null) {
            rootLayoutComponent.setHasTouchListeners(true);
        }
        Iterator<Operation> it = this.mList.iterator();
        while (it.hasNext()) {
            Operation next = it.next();
            if (next instanceof TextData) {
                next.apply(remoteContext);
                remoteContext.incrementOpCount();
            }
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
        float currentTimeMillis = (System.currentTimeMillis() - this.mAnimateRippleStart) / this.mAnimateRippleDuration;
        if (currentTimeMillis > 1.0f) {
            this.mAnimateRippleStart = 0L;
        }
        float min = Math.min(1.0f, currentTimeMillis);
        paintContext.save();
        paintContext.savePaint();
        this.mPaint.reset();
        FloatAnimation floatAnimation = new FloatAnimation(1, 1.0f, null, Float.NaN, Float.NaN);
        floatAnimation.setInitialValue(0.0f);
        floatAnimation.setTargetValue(1.0f);
        float f = floatAnimation.get(min);
        FloatAnimation floatAnimation2 = new FloatAnimation(1, 0.5f, null, Float.NaN, Float.NaN);
        floatAnimation2.setInitialValue(0.0f);
        floatAnimation2.setTargetValue(1.0f);
        float f2 = floatAnimation2.get(min);
        int interpolateColor = Utils.interpolateColor(ColorUtils.createColor(250, 250, 250, 180), ColorUtils.createColor(200, 200, 200, 0), f);
        float max = Math.max(this.mWidth, this.mHeight) * f2;
        this.mPaint.setColor(interpolateColor);
        paintContext.applyPaint(this.mPaint);
        paintContext.clipRect(0.0f, 0.0f, this.mWidth, this.mHeight);
        paintContext.drawCircle(this.mAnimateRippleX, this.mAnimateRippleY, max);
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
        stringSerializer.append(i, "CLICK_MODIFIER");
        Iterator<Operation> it = this.mList.iterator();
        while (it.hasNext()) {
            Object obj = (Operation) it.next();
            if (obj instanceof ActionOperation) {
                ((ActionOperation) obj).serializeToString(i + 1, stringSerializer);
            }
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.ClickHandler
    public void onClick(RemoteContext remoteContext, CoreDocument coreDocument, Component component, float f, float f2) {
        RemoteContext remoteContext2;
        CoreDocument coreDocument2;
        Component component2;
        float f3;
        float f4;
        if (component.isVisible()) {
            float[] fArr = this.locationInWindow;
            fArr[0] = 0.0f;
            fArr[1] = 0.0f;
            component.getLocationInWindow(fArr);
            float[] fArr2 = this.locationInWindow;
            animateRipple(f - fArr2[0], f2 - fArr2[1]);
            Iterator<Operation> it = this.mList.iterator();
            while (it.hasNext()) {
                Object obj = (Operation) it.next();
                if (obj instanceof ActionOperation) {
                    remoteContext2 = remoteContext;
                    coreDocument2 = coreDocument;
                    component2 = component;
                    f3 = f;
                    f4 = f2;
                    ((ActionOperation) obj).runAction(remoteContext2, coreDocument2, component2, f3, f4);
                } else {
                    remoteContext2 = remoteContext;
                    coreDocument2 = coreDocument;
                    component2 = component;
                    f3 = f;
                    f4 = f2;
                }
                remoteContext = remoteContext2;
                coreDocument = coreDocument2;
                component = component2;
                f = f3;
                f2 = f4;
            }
            remoteContext.hapticEffect(3);
        }
    }

    public static String name() {
        return "ClickModifier";
    }

    public static void apply(WireBuffer wireBuffer) {
        wireBuffer.start(59);
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        list.add(new ClickModifierOperation());
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Layout Operations", 59, name()).description("Click modifier. This operation contains a list of action executed on click");
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addTags(SerializeTags.MODIFIER).addType("ClickModifierOperation");
    }
}
