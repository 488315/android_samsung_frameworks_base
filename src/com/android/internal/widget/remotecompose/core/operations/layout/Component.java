package com.android.internal.widget.remotecompose.core.operations.layout;

import android.app.slice.Slice;
import com.android.internal.widget.remotecompose.core.CoreDocument;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.PaintOperation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.SerializableToString;
import com.android.internal.widget.remotecompose.core.TouchListener;
import com.android.internal.widget.remotecompose.core.VariableSupport;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.operations.BitmapData;
import com.android.internal.widget.remotecompose.core.operations.ComponentValue;
import com.android.internal.widget.remotecompose.core.operations.TextData;
import com.android.internal.widget.remotecompose.core.operations.TouchExpression;
import com.android.internal.widget.remotecompose.core.operations.layout.animation.AnimateMeasure;
import com.android.internal.widget.remotecompose.core.operations.layout.animation.AnimationSpec;
import com.android.internal.widget.remotecompose.core.operations.layout.measure.ComponentMeasure;
import com.android.internal.widget.remotecompose.core.operations.layout.measure.Measurable;
import com.android.internal.widget.remotecompose.core.operations.layout.measure.MeasurePass;
import com.android.internal.widget.remotecompose.core.operations.paint.PaintBundle;
import com.android.internal.widget.remotecompose.core.operations.utilities.StringSerializer;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import com.android.internal.widget.remotecompose.core.serialize.Serializable;
import com.android.internal.widget.remotecompose.core.serialize.SerializeTags;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/* loaded from: classes6.dex */
public class Component extends PaintOperation implements Container, Measurable, SerializableToString, Serializable {
    private static final boolean DEBUG = false;
    public float[] locationInWindow;
    public AnimateMeasure mAnimateMeasure;
    protected int mAnimationId;
    public AnimationSpec mAnimationSpec;
    protected int mComponentId;
    protected HashSet<ComponentValue> mComponentValues;
    public boolean mFirstLayout;
    protected float mHeight;
    public ArrayList<Operation> mList;
    private boolean mNeedsBoundsAnimation;
    public boolean mNeedsMeasure;
    public boolean mNeedsRepaint;
    PaintBundle mPaint;
    protected Component mParent;
    public PaintOperation mPreTranslate;
    public int mScheduledVisibility;
    public int mVisibility;
    protected float mWidth;
    protected float mX;
    protected float mY;
    protected float mZIndex;

    public float getScrollX() {
        return 0.0f;
    }

    public float getScrollY() {
        return 0.0f;
    }

    public void registerVariables(RemoteContext remoteContext) {
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
    }

    public void markNeedsBoundsAnimation() {
        this.mNeedsBoundsAnimation = true;
        Component component = this.mParent;
        if (component == null || component.mNeedsBoundsAnimation) {
            return;
        }
        component.markNeedsBoundsAnimation();
    }

    public void clearNeedsBoundsAnimation() {
        this.mNeedsBoundsAnimation = false;
    }

    public boolean needsBoundsAnimation() {
        return this.mNeedsBoundsAnimation;
    }

    public float getZIndex() {
        return this.mZIndex;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.Container
    public ArrayList<Operation> getList() {
        return this.mList;
    }

    public float getX() {
        return this.mX;
    }

    public float getY() {
        return this.mY;
    }

    public float getWidth() {
        return this.mWidth;
    }

    public float getHeight() {
        return this.mHeight;
    }

    public int getComponentId() {
        return this.mComponentId;
    }

    public int getAnimationId() {
        return this.mAnimationId;
    }

    public Component getParent() {
        return this.mParent;
    }

    public void setX(float f) {
        this.mX = f;
    }

    public void setY(float f) {
        this.mY = f;
    }

    public void setWidth(float f) {
        this.mWidth = f;
    }

    public void setHeight(float f) {
        this.mHeight = f;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.internal.widget.remotecompose.core.PaintOperation, com.android.internal.widget.remotecompose.core.Operation
    public void apply(RemoteContext remoteContext) {
        Iterator<Operation> it = this.mList.iterator();
        while (it.hasNext()) {
            Operation next = it.next();
            if ((next instanceof VariableSupport) && next.isDirty()) {
                next.markNotDirty();
                ((VariableSupport) next).updateVariables(remoteContext);
            }
        }
        super.apply(remoteContext);
    }

    private void updateComponentValues(RemoteContext remoteContext) {
        Iterator<ComponentValue> it = this.mComponentValues.iterator();
        while (it.hasNext()) {
            ComponentValue next = it.next();
            if (remoteContext.getMode() == RemoteContext.ContextMode.DATA) {
                remoteContext.loadFloat(next.getValueId(), 1.0f);
            } else {
                int type = next.getType();
                if (type == 0) {
                    remoteContext.loadFloat(next.getValueId(), this.mWidth);
                } else if (type == 1) {
                    remoteContext.loadFloat(next.getValueId(), this.mHeight);
                }
            }
        }
    }

    public void setComponentId(int i) {
        this.mComponentId = i;
    }

    public void setAnimationId(int i) {
        this.mAnimationId = i;
    }

    public Component(Component component, int i, int i2, float f, float f2, float f3, float f4) {
        this.mComponentId = -1;
        this.mAnimationId = -1;
        this.mVisibility = 1;
        this.mScheduledVisibility = 1;
        this.mList = new ArrayList<>();
        this.mNeedsMeasure = true;
        this.mNeedsRepaint = false;
        this.mAnimationSpec = AnimationSpec.DEFAULT;
        this.mFirstLayout = true;
        this.mPaint = new PaintBundle();
        this.mComponentValues = new HashSet<>();
        this.mZIndex = 0.0f;
        this.mNeedsBoundsAnimation = false;
        this.locationInWindow = new float[2];
        this.mComponentId = i;
        this.mX = f;
        this.mY = f2;
        this.mWidth = f3;
        this.mHeight = f4;
        this.mParent = component;
        this.mAnimationId = i2;
    }

    public Component(int i, float f, float f2, float f3, float f4, Component component) {
        this(component, i, -1, f, f2, f3, f4);
    }

    public Component(Component component) {
        this(component.mParent, component.mComponentId, component.mAnimationId, component.mX, component.mY, component.mWidth, component.mHeight);
        this.mList.addAll(component.mList);
        finalizeCreation();
    }

    public void finalizeCreation() {
        Iterator<Operation> it = this.mList.iterator();
        while (it.hasNext()) {
            Operation next = it.next();
            if (next instanceof Component) {
                ((Component) next).mParent = this;
            }
            if (next instanceof AnimationSpec) {
                AnimationSpec animationSpec = (AnimationSpec) next;
                this.mAnimationSpec = animationSpec;
                this.mAnimationId = animationSpec.getAnimationId();
            }
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.measure.Measurable
    public boolean needsMeasure() {
        return this.mNeedsMeasure;
    }

    public void setParent(Component component) {
        this.mParent = component;
    }

    public void updateVariables(RemoteContext remoteContext) {
        Component component = remoteContext.mLastComponent;
        remoteContext.mLastComponent = this;
        if (!this.mComponentValues.isEmpty()) {
            updateComponentValues(remoteContext);
        }
        remoteContext.mLastComponent = component;
    }

    public void addComponentValue(ComponentValue componentValue) {
        this.mComponentValues.add(componentValue);
    }

    public float minIntrinsicWidth(RemoteContext remoteContext) {
        return getWidth();
    }

    public float maxIntrinsicWidth(RemoteContext remoteContext) {
        return getWidth();
    }

    public float minIntrinsicHeight(RemoteContext remoteContext) {
        return getHeight();
    }

    public float maxIntrinsicHeight(RemoteContext remoteContext) {
        return getHeight();
    }

    public void inflate() {
        Iterator<Operation> it = this.mList.iterator();
        while (it.hasNext()) {
            Object obj = (Operation) it.next();
            if (obj instanceof TouchListener) {
                ((TouchListener) obj).setComponent(this);
            }
        }
    }

    protected AnimationSpec getAnimationSpec() {
        return this.mAnimationSpec;
    }

    protected void setAnimationSpec(AnimationSpec animationSpec) {
        this.mAnimationSpec = animationSpec;
    }

    public static class Visibility {
        public static final int CLEAR_OVERRIDE = 128;
        public static final int GONE = 0;
        public static final int INVISIBLE = 2;
        public static final int OVERRIDE_GONE = 16;
        public static final int OVERRIDE_INVISIBLE = 64;
        public static final int OVERRIDE_VISIBLE = 32;
        public static final int VISIBLE = 1;

        public static int add(int i, int i2) {
            int i3 = (i & 15) + i2;
            return (i3 & 128) == 128 ? i3 & 15 : i3;
        }

        public static int clearOverride(int i) {
            return i & 15;
        }

        public static boolean hasOverride(int i) {
            return (i >> 4) > 0;
        }

        public static boolean isGone(int i) {
            return (i >> 4) > 0 ? (i & 16) == 16 : i == 0;
        }

        public static boolean isInvisible(int i) {
            return (i >> 4) > 0 ? (i & 64) == 64 : i == 2;
        }

        public static boolean isVisible(int i) {
            return (i >> 4) > 0 ? (i & 32) == 32 : i == 1;
        }

        public static String toString(int i) {
            if (i == 0) {
                return "GONE";
            }
            if (i == 1) {
                return "VISIBLE";
            }
            if (i == 2) {
                return "INVISIBLE";
            }
            if ((i >> 4) > 0) {
                if ((i & 16) == 16) {
                    return "OVERRIDE_GONE";
                }
                if ((i & 32) == 32) {
                    return "OVERRIDE_VISIBLE";
                }
                if ((i & 64) == 64) {
                    return "OVERRIDE_INVISIBLE";
                }
            }
            return "" + i;
        }
    }

    public boolean isVisible() {
        if (this.mParent == null || !Visibility.isVisible(this.mVisibility)) {
            return Visibility.isVisible(this.mVisibility);
        }
        return this.mParent.isVisible();
    }

    public boolean isGone() {
        return Visibility.isGone(this.mVisibility);
    }

    public boolean isInvisible() {
        return Visibility.isInvisible(this.mVisibility);
    }

    public void setVisibility(int i) {
        if (i == this.mVisibility && i == this.mScheduledVisibility) {
            return;
        }
        this.mScheduledVisibility = i;
        invalidateMeasure();
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation
    public boolean suitableForTransition(Operation operation) {
        if (!(operation instanceof Component)) {
            return false;
        }
        Component component = (Component) operation;
        if (this.mList.size() != component.mList.size()) {
            return false;
        }
        for (int i = 0; i < this.mList.size(); i++) {
            Operation operation2 = this.mList.get(i);
            Operation operation3 = component.mList.get(i);
            if ((operation2 instanceof Component) && (operation3 instanceof Component) && !((Component) operation2).suitableForTransition(operation3)) {
                return false;
            }
            if ((operation2 instanceof PaintOperation) && !((PaintOperation) operation2).suitableForTransition(operation3)) {
                return false;
            }
        }
        return true;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.measure.Measurable
    public void measure(PaintContext paintContext, float f, float f2, float f3, float f4, MeasurePass measurePass) {
        ComponentMeasure componentMeasure = measurePass.get(this);
        componentMeasure.setW(this.mWidth);
        componentMeasure.setH(this.mHeight);
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.measure.Measurable
    public void layout(RemoteContext remoteContext, MeasurePass measurePass) {
        Component component;
        ComponentMeasure componentMeasure = measurePass.get(this);
        if (!this.mFirstLayout && remoteContext.isAnimationEnabled() && this.mAnimationSpec.isAnimationEnabled() && !(this instanceof LayoutComponentContent)) {
            AnimateMeasure animateMeasure = this.mAnimateMeasure;
            if (animateMeasure == null) {
                ComponentMeasure componentMeasure2 = new ComponentMeasure(this.mComponentId, this.mX, this.mY, this.mWidth, this.mHeight, this.mVisibility);
                ComponentMeasure componentMeasure3 = new ComponentMeasure(this.mComponentId, componentMeasure.getX(), componentMeasure.getY(), componentMeasure.getW(), componentMeasure.getH(), componentMeasure.getVisibility());
                if (componentMeasure3.same(componentMeasure2)) {
                    component = this;
                } else {
                    component = this;
                    component.mAnimateMeasure = new AnimateMeasure(remoteContext.currentTime, component, componentMeasure2, componentMeasure3, this.mAnimationSpec.getMotionDuration(), this.mAnimationSpec.getVisibilityDuration(), this.mAnimationSpec.getEnterAnimation(), this.mAnimationSpec.getExitAnimation(), this.mAnimationSpec.getMotionEasingType(), this.mAnimationSpec.getVisibilityEasingType());
                }
            } else {
                component = this;
                animateMeasure.updateTarget(componentMeasure, remoteContext.currentTime);
            }
        } else {
            component = this;
            component.mVisibility = componentMeasure.getVisibility();
        }
        AnimateMeasure animateMeasure2 = component.mAnimateMeasure;
        if (animateMeasure2 == null) {
            component.setWidth(componentMeasure.getW());
            component.setHeight(componentMeasure.getH());
            component.setLayoutPosition(componentMeasure.getX(), componentMeasure.getY());
            component.updateComponentValues(remoteContext);
            component.clearNeedsBoundsAnimation();
        } else {
            animateMeasure2.apply(remoteContext);
            component.updateComponentValues(remoteContext);
            component.markNeedsBoundsAnimation();
        }
        component.mFirstLayout = false;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.measure.Measurable
    public void animatingBounds(RemoteContext remoteContext) {
        AnimateMeasure animateMeasure = this.mAnimateMeasure;
        if (animateMeasure != null) {
            animateMeasure.apply(remoteContext);
            updateComponentValues(remoteContext);
        } else {
            clearNeedsBoundsAnimation();
        }
        Iterator<Operation> it = this.mList.iterator();
        while (it.hasNext()) {
            Object obj = (Operation) it.next();
            if (obj instanceof Measurable) {
                ((Measurable) obj).animatingBounds(remoteContext);
            }
        }
    }

    public boolean contains(float f, float f2) {
        float[] fArr = this.locationInWindow;
        fArr[0] = 0.0f;
        fArr[1] = 0.0f;
        getLocationInWindow(fArr);
        float[] fArr2 = this.locationInWindow;
        float f3 = fArr2[0];
        float f4 = this.mWidth + f3;
        float f5 = fArr2[1];
        return f >= f3 && f < f4 && f2 >= f5 && f2 < this.mHeight + f5;
    }

    public void onClick(RemoteContext remoteContext, CoreDocument coreDocument, float f, float f2) {
        Component component;
        RemoteContext remoteContext2;
        CoreDocument coreDocument2;
        boolean z = (f == -1.0f) & (f2 == -1.0f);
        if (z || contains(f, f2)) {
            float scrollX = z ? -1.0f : f - getScrollX();
            float scrollY = z ? -1.0f : f2 - getScrollY();
            Iterator<Operation> it = this.mList.iterator();
            while (it.hasNext()) {
                Object obj = (Operation) it.next();
                if (obj instanceof Component) {
                    ((Component) obj).onClick(remoteContext, coreDocument, scrollX, scrollY);
                }
                if (obj instanceof ClickHandler) {
                    component = this;
                    remoteContext2 = remoteContext;
                    coreDocument2 = coreDocument;
                    ((ClickHandler) obj).onClick(remoteContext2, coreDocument2, component, scrollX, scrollY);
                } else {
                    component = this;
                    remoteContext2 = remoteContext;
                    coreDocument2 = coreDocument;
                }
                remoteContext = remoteContext2;
                coreDocument = coreDocument2;
                this = component;
            }
        }
    }

    public void onTouchDown(RemoteContext remoteContext, CoreDocument coreDocument, float f, float f2) {
        Component component;
        RemoteContext remoteContext2;
        CoreDocument coreDocument2;
        if (contains(f, f2)) {
            float scrollX = f - getScrollX();
            float scrollY = f2 - getScrollY();
            Iterator<Operation> it = this.mList.iterator();
            while (it.hasNext()) {
                Object obj = (Operation) it.next();
                if (obj instanceof Component) {
                    ((Component) obj).onTouchDown(remoteContext, coreDocument, scrollX, scrollY);
                }
                if (obj instanceof TouchHandler) {
                    component = this;
                    remoteContext2 = remoteContext;
                    coreDocument2 = coreDocument;
                    ((TouchHandler) obj).onTouchDown(remoteContext2, coreDocument2, component, scrollX, scrollY);
                } else {
                    component = this;
                    remoteContext2 = remoteContext;
                    coreDocument2 = coreDocument;
                }
                if (obj instanceof TouchExpression) {
                    TouchExpression touchExpression = (TouchExpression) obj;
                    touchExpression.updateVariables(remoteContext2);
                    touchExpression.touchDown(remoteContext2, scrollX, scrollY);
                    coreDocument2.appliedTouchOperation(component);
                }
                remoteContext = remoteContext2;
                coreDocument = coreDocument2;
                this = component;
            }
        }
    }

    public void onTouchUp(RemoteContext remoteContext, CoreDocument coreDocument, float f, float f2, float f3, float f4, boolean z) {
        if (z || contains(f, f2)) {
            float scrollX = f - getScrollX();
            float scrollY = f2 - getScrollY();
            Iterator<Operation> it = this.mList.iterator();
            while (it.hasNext()) {
                Object obj = (Operation) it.next();
                if (obj instanceof Component) {
                    float f5 = scrollX;
                    float f6 = scrollY;
                    ((Component) obj).onTouchUp(remoteContext, coreDocument, f5, f6, f3, f4, z);
                    scrollX = f5;
                    scrollY = f6;
                }
                if (obj instanceof TouchHandler) {
                    float f7 = scrollX;
                    ((TouchHandler) obj).onTouchUp(remoteContext, coreDocument, this, f7, scrollY, f3, f4);
                    scrollX = f7;
                }
                if (obj instanceof TouchExpression) {
                    TouchExpression touchExpression = (TouchExpression) obj;
                    touchExpression.updateVariables(remoteContext);
                    float f8 = scrollY;
                    touchExpression.touchUp(remoteContext, scrollX, f8, f3, f4);
                    scrollY = f8;
                }
            }
        }
    }

    public void onTouchCancel(RemoteContext remoteContext, CoreDocument coreDocument, float f, float f2, boolean z) {
        boolean z2;
        Component component;
        if (z || contains(f, f2)) {
            float scrollX = f - getScrollX();
            float scrollY = f2 - getScrollY();
            Iterator<Operation> it = this.mList.iterator();
            while (it.hasNext()) {
                Object obj = (Operation) it.next();
                if (obj instanceof Component) {
                    z2 = z;
                    ((Component) obj).onTouchCancel(remoteContext, coreDocument, scrollX, scrollY, z2);
                } else {
                    z2 = z;
                }
                float f3 = scrollX;
                if (obj instanceof TouchHandler) {
                    float f4 = scrollY;
                    Component component2 = this;
                    ((TouchHandler) obj).onTouchCancel(remoteContext, coreDocument, component2, f3, f4);
                    component = component2;
                    scrollY = f4;
                } else {
                    component = this;
                }
                CoreDocument coreDocument2 = coreDocument;
                if (obj instanceof TouchExpression) {
                    TouchExpression touchExpression = (TouchExpression) obj;
                    touchExpression.updateVariables(remoteContext);
                    touchExpression.touchUp(remoteContext, f3, scrollY, 0.0f, 0.0f);
                    f3 = f3;
                }
                scrollX = f3;
                this = component;
                coreDocument = coreDocument2;
                z = z2;
            }
        }
    }

    public void onTouchDrag(RemoteContext remoteContext, CoreDocument coreDocument, float f, float f2, boolean z) {
        RemoteContext remoteContext2;
        CoreDocument coreDocument2;
        boolean z2;
        float f3;
        float f4;
        Component component;
        if (z || contains(f, f2)) {
            float scrollX = f - getScrollX();
            float scrollY = f2 - getScrollY();
            Iterator<Operation> it = this.mList.iterator();
            while (it.hasNext()) {
                Object obj = (Operation) it.next();
                if (obj instanceof Component) {
                    remoteContext2 = remoteContext;
                    coreDocument2 = coreDocument;
                    boolean z3 = z;
                    ((Component) obj).onTouchDrag(remoteContext2, coreDocument2, scrollX, scrollY, z3);
                    z2 = z3;
                } else {
                    remoteContext2 = remoteContext;
                    coreDocument2 = coreDocument;
                    z2 = z;
                }
                if (obj instanceof TouchHandler) {
                    f3 = scrollY;
                    f4 = scrollX;
                    component = this;
                    ((TouchHandler) obj).onTouchDrag(remoteContext2, coreDocument2, component, f4, f3);
                } else {
                    f3 = scrollY;
                    f4 = scrollX;
                    component = this;
                }
                if (obj instanceof TouchExpression) {
                    TouchExpression touchExpression = (TouchExpression) obj;
                    touchExpression.updateVariables(remoteContext2);
                    touchExpression.touchDrag(remoteContext2, f, f2);
                }
                z = z2;
                remoteContext = remoteContext2;
                coreDocument = coreDocument2;
                this = component;
                scrollX = f4;
                scrollY = f3;
            }
        }
    }

    public void getLocationInWindow(float[] fArr, boolean z) {
        fArr[0] = fArr[0] + this.mX;
        fArr[1] = fArr[1] + this.mY;
        Component component = this.mParent;
        if (component != null) {
            component.getLocationInWindow(fArr, false);
        }
    }

    public void getLocationInWindow(float[] fArr) {
        getLocationInWindow(fArr, true);
    }

    public String toString() {
        return "COMPONENT(<" + this.mComponentId + "> " + getClass().getSimpleName() + ") [" + this.mX + "," + this.mY + " - " + this.mWidth + " x " + this.mHeight + "] " + textContent() + " Visibility (" + Visibility.toString(this.mVisibility) + ") ";
    }

    protected String getSerializedName() {
        return "COMPONENT";
    }

    @Override // com.android.internal.widget.remotecompose.core.SerializableToString
    public void serializeToString(int i, StringSerializer stringSerializer) {
        stringSerializer.append(i, getSerializedName() + " [" + this.mComponentId + ":" + this.mAnimationId + "] = [" + this.mX + ", " + this.mY + ", " + this.mWidth + ", " + this.mHeight + "] " + Visibility.toString(this.mVisibility));
    }

    public RootLayoutComponent getRoot() throws Exception {
        if (this instanceof RootLayoutComponent) {
            return (RootLayoutComponent) this;
        }
        Component component = this.mParent;
        while (!(component instanceof RootLayoutComponent)) {
            if (component == null) {
                throw new Exception("No RootLayoutComponent found");
            }
            component = component.mParent;
        }
        return (RootLayoutComponent) component;
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation, com.android.internal.widget.remotecompose.core.Operation
    public String deepToString(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(toString());
        sb.append(ShaderAssembler.NEWLINE);
        String str2 = "  " + str;
        Iterator<Operation> it = this.mList.iterator();
        while (it.hasNext()) {
            sb.append(it.next().deepToString(str2));
            sb.append(ShaderAssembler.NEWLINE);
        }
        return sb.toString();
    }

    public void invalidateMeasure() {
        needsRepaint();
        this.mNeedsMeasure = true;
        for (Component component = this.mParent; component != null; component = component.mParent) {
            component.mNeedsMeasure = true;
        }
    }

    public void needsRepaint() {
        try {
            getRoot().mNeedsRepaint = true;
        } catch (Exception unused) {
        }
    }

    public String content() {
        StringBuilder sb = new StringBuilder();
        Iterator<Operation> it = this.mList.iterator();
        while (it.hasNext()) {
            Operation next = it.next();
            sb.append("- ");
            sb.append(next);
            sb.append(ShaderAssembler.NEWLINE);
        }
        return sb.toString();
    }

    public String textContent() {
        StringBuilder sb = new StringBuilder();
        Iterator<Operation> it = this.mList.iterator();
        while (it.hasNext()) {
            it.next();
            sb.append("");
        }
        return sb.toString();
    }

    public void debugBox(Component component, PaintContext paintContext) {
        float f = component.mWidth;
        float f2 = component.mHeight;
        paintContext.savePaint();
        this.mPaint.reset();
        this.mPaint.setColor(0, 0, 255, 255);
        paintContext.applyPaint(this.mPaint);
        paintContext.drawLine(0.0f, 0.0f, f, 0.0f);
        paintContext.drawLine(f, 0.0f, f, f2);
        paintContext.drawLine(f, f2, 0.0f, f2);
        paintContext.drawLine(0.0f, f2, 0.0f, 0.0f);
        paintContext.restorePaint();
    }

    public void setLayoutPosition(float f, float f2) {
        this.mX = f;
        this.mY = f2;
    }

    public float getTranslateX() {
        Component component = this.mParent;
        if (component != null) {
            return this.mX - component.mX;
        }
        return 0.0f;
    }

    public float getTranslateY() {
        Component component = this.mParent;
        if (component != null) {
            return this.mY - component.mY;
        }
        return 0.0f;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void paintingComponent(PaintContext paintContext) {
        PaintOperation paintOperation = this.mPreTranslate;
        if (paintOperation != null) {
            paintOperation.paint(paintContext);
        }
        Component component = paintContext.getContext().mLastComponent;
        paintContext.getContext().mLastComponent = this;
        paintContext.save();
        paintContext.translate(this.mX, this.mY);
        if (paintContext.isVisualDebug()) {
            debugBox(this, paintContext);
        }
        Iterator<Operation> it = this.mList.iterator();
        while (it.hasNext()) {
            Operation next = it.next();
            if (next.isDirty() && (next instanceof VariableSupport)) {
                ((VariableSupport) next).updateVariables(paintContext.getContext());
                next.markNotDirty();
            }
            if (next instanceof PaintOperation) {
                ((PaintOperation) next).paint(paintContext);
                paintContext.getContext().incrementOpCount();
            } else {
                next.apply(paintContext.getContext());
                paintContext.getContext().incrementOpCount();
            }
        }
        paintContext.restore();
        paintContext.getContext().mLastComponent = component;
    }

    public boolean applyAnimationAsNeeded(PaintContext paintContext) {
        AnimateMeasure animateMeasure;
        if (!paintContext.isAnimationEnabled() || (animateMeasure = this.mAnimateMeasure) == null) {
            return false;
        }
        animateMeasure.paint(paintContext);
        if (this.mAnimateMeasure.isDone()) {
            this.mAnimateMeasure = null;
            clearNeedsBoundsAnimation();
            needsRepaint();
            return true;
        }
        markNeedsBoundsAnimation();
        return true;
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation
    public void paint(PaintContext paintContext) {
        if (paintContext.isVisualDebug()) {
            paintContext.save();
            paintContext.translate(this.mX, this.mY);
            paintContext.savePaint();
            this.mPaint.reset();
            this.mPaint.setColor(0, 255, 0, 255);
            paintContext.applyPaint(this.mPaint);
            paintContext.drawLine(0.0f, 0.0f, this.mWidth, 0.0f);
            float f = this.mWidth;
            paintContext.drawLine(f, 0.0f, f, this.mHeight);
            float f2 = this.mWidth;
            float f3 = this.mHeight;
            paintContext.drawLine(f2, f3, 0.0f, f3);
            paintContext.drawLine(0.0f, this.mHeight, 0.0f, 0.0f);
            this.mPaint.setColor(255, 0, 0, 255);
            paintContext.applyPaint(this.mPaint);
            paintContext.drawLine(0.0f, 0.0f, this.mWidth, this.mHeight);
            paintContext.drawLine(0.0f, this.mHeight, this.mWidth, 0.0f);
            paintContext.restorePaint();
            paintContext.restore();
        }
        if (applyAnimationAsNeeded(paintContext) || isGone() || isInvisible()) {
            return;
        }
        paintingComponent(paintContext);
    }

    public void getComponents(ArrayList<Component> arrayList) {
        Iterator<Operation> it = this.mList.iterator();
        while (it.hasNext()) {
            Operation next = it.next();
            if (next instanceof Component) {
                arrayList.add((Component) next);
            }
        }
    }

    public void getData(ArrayList<Operation> arrayList) {
        Iterator<Operation> it = this.mList.iterator();
        while (it.hasNext()) {
            Operation next = it.next();
            if ((next instanceof TextData) || (next instanceof BitmapData)) {
                arrayList.add(next);
            }
        }
    }

    public int getComponentCount() {
        Iterator<Operation> it = this.mList.iterator();
        int componentCount = 0;
        while (it.hasNext()) {
            Operation next = it.next();
            if (next instanceof Component) {
                componentCount += ((Component) next).getComponentCount() + 1;
            }
        }
        return componentCount;
    }

    public int getPaintId() {
        int i = this.mAnimationId;
        return i != -1 ? i : this.mComponentId;
    }

    public boolean doesNeedsRepaint() {
        return this.mNeedsRepaint;
    }

    public Component getComponent(int i) {
        Component component;
        if (this.mComponentId == i || this.mAnimationId == i) {
            return this;
        }
        Iterator<Operation> it = this.mList.iterator();
        while (it.hasNext()) {
            Operation next = it.next();
            if ((next instanceof Component) && (component = ((Component) next).getComponent(i)) != null) {
                return component;
            }
        }
        return null;
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addTags(SerializeTags.COMPONENT);
        mapSerializer.addType(getSerializedName());
        mapSerializer.add("id", Integer.valueOf(this.mComponentId));
        mapSerializer.add("x", Float.valueOf(this.mX));
        mapSerializer.add("y", Float.valueOf(this.mY));
        mapSerializer.add("width", Float.valueOf(this.mWidth));
        mapSerializer.add("height", Float.valueOf(this.mHeight));
        mapSerializer.add("visibility", Visibility.toString(this.mVisibility));
        mapSerializer.add(Slice.HINT_LIST, this.mList);
    }

    public <T> T selfOrModifier(Class<T> cls) {
        if (cls.isInstance(this)) {
            return cls.cast(this);
        }
        Iterator<Operation> it = this.mList.iterator();
        while (it.hasNext()) {
            Operation next = it.next();
            if (cls.isInstance(next)) {
                return cls.cast(next);
            }
        }
        return null;
    }
}
