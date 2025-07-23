package com.android.internal.widget.remotecompose.core.operations.layout.modifiers;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.android.internal.widget.remotecompose.core.CoreDocument;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.VariableSupport;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.operations.TouchExpression;
import com.android.internal.widget.remotecompose.core.operations.Utils;
import com.android.internal.widget.remotecompose.core.operations.layout.Component;
import com.android.internal.widget.remotecompose.core.operations.layout.DecoratorComponent;
import com.android.internal.widget.remotecompose.core.operations.layout.LayoutComponent;
import com.android.internal.widget.remotecompose.core.operations.layout.ListActionsOperation;
import com.android.internal.widget.remotecompose.core.operations.layout.RootLayoutComponent;
import com.android.internal.widget.remotecompose.core.operations.layout.ScrollDelegate;
import com.android.internal.widget.remotecompose.core.operations.layout.TouchHandler;
import com.android.internal.widget.remotecompose.core.operations.utilities.StringSerializer;
import com.android.internal.widget.remotecompose.core.semantics.ScrollableComponent;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import com.android.internal.widget.remotecompose.core.serialize.SerializeTags;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes6.dex */
public class ScrollModifierOperation extends ListActionsOperation implements TouchHandler, DecoratorComponent, ScrollDelegate, VariableSupport, ScrollableComponent {
    public static final String CLASS_NAME = "ScrollModifierOperation";
    private static final int OP_CODE = 226;
    float mContentDimension;
    int mDirection;
    float mHostDimension;
    float mInitialScrollX;
    float mInitialScrollY;
    private final float mMax;
    float mMaxScrollX;
    float mMaxScrollY;
    private final float mNotchMax;
    private final float mPositionExpression;
    float mScrollX;
    float mScrollY;
    float mTouchDownX;
    float mTouchDownY;
    private TouchExpression mTouchExpression;

    public static int id() {
        return 226;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.TouchHandler
    public void onTouchCancel(RemoteContext remoteContext, CoreDocument coreDocument, Component component, float f, float f2) {
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.ScrollDelegate
    public void reset() {
    }

    public ScrollModifierOperation(int i, float f, float f2, float f3) {
        super("SCROLL_MODIFIER");
        this.mDirection = i;
        this.mPositionExpression = f;
        this.mMax = f2;
        this.mNotchMax = f3;
    }

    public void inflate(Component component) {
        Iterator<Operation> it = this.mList.iterator();
        while (it.hasNext()) {
            Operation next = it.next();
            if (next instanceof TouchExpression) {
                TouchExpression touchExpression = (TouchExpression) next;
                this.mTouchExpression = touchExpression;
                touchExpression.setComponent(component);
            }
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void registerListening(RemoteContext remoteContext) {
        TouchExpression touchExpression = this.mTouchExpression;
        if (touchExpression != null) {
            touchExpression.registerListening(remoteContext);
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void updateVariables(RemoteContext remoteContext) {
        TouchExpression touchExpression = this.mTouchExpression;
        if (touchExpression != null) {
            touchExpression.updateVariables(remoteContext);
        }
    }

    public boolean isVerticalScroll() {
        return this.mDirection == 0;
    }

    public boolean isHorizontalScroll() {
        return this.mDirection != 0;
    }

    public float getScrollX() {
        return this.mScrollX;
    }

    public float getScrollY() {
        return this.mScrollY;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.ListActionsOperation, com.android.internal.widget.remotecompose.core.PaintOperation, com.android.internal.widget.remotecompose.core.Operation
    public void apply(RemoteContext remoteContext) {
        RootLayoutComponent rootLayoutComponent = remoteContext.getDocument().getRootLayoutComponent();
        if (rootLayoutComponent != null) {
            rootLayoutComponent.setHasTouchListeners(true);
        }
        super.apply(remoteContext);
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer, this.mDirection, this.mPositionExpression, this.mMax, this.mNotchMax);
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.ListActionsOperation, com.android.internal.widget.remotecompose.core.operations.layout.modifiers.ModifierOperation
    public void serializeToString(int i, StringSerializer stringSerializer) {
        stringSerializer.append(i, "SCROLL = [" + this.mDirection + NavigationBarInflaterView.SIZE_MOD_END);
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.ListActionsOperation, com.android.internal.widget.remotecompose.core.PaintOperation, com.android.internal.widget.remotecompose.core.Operation
    public String deepToString(String str) {
        StringBuilder sb = new StringBuilder();
        if (str == null) {
            str = "";
        }
        sb.append(str);
        sb.append(toString());
        return sb.toString();
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.ListActionsOperation, com.android.internal.widget.remotecompose.core.PaintOperation
    public void paint(PaintContext paintContext) {
        Iterator<Operation> it = this.mList.iterator();
        while (it.hasNext()) {
            it.next().apply(paintContext.getContext());
        }
        if (this.mTouchExpression == null) {
            return;
        }
        float f = paintContext.getContext().mRemoteComposeState.getFloat(Utils.idFromNan(this.mPositionExpression));
        if (this.mDirection == 0) {
            this.mScrollY = -f;
        } else {
            this.mScrollX = -f;
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.ListActionsOperation
    public String toString() {
        return "ScrollModifierOperation(" + this.mDirection + NavigationBarInflaterView.KEY_CODE_END;
    }

    public static String name() {
        return CLASS_NAME;
    }

    public static void apply(WireBuffer wireBuffer, int i, float f, float f2, float f3) {
        wireBuffer.start(226);
        wireBuffer.writeInt(i);
        wireBuffer.writeFloat(f);
        wireBuffer.writeFloat(f2);
        wireBuffer.writeFloat(f3);
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        list.add(new ScrollModifierOperation(wireBuffer.readInt(), wireBuffer.readFloat(), wireBuffer.readFloat(), wireBuffer.readFloat()));
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Modifier Operations", 226, CLASS_NAME).description("define a Scroll Modifier").field(0, "direction", "");
    }

    private float getMaxScrollPosition(Component component, int i) {
        if (!(component instanceof LayoutComponent)) {
            return 0.0f;
        }
        LayoutComponent layoutComponent = (LayoutComponent) component;
        int size = layoutComponent.getChildrenComponents().size();
        if (size <= 0) {
            return 0.0f;
        }
        Component component2 = layoutComponent.getChildrenComponents().get(size - 1);
        if (i == 0) {
            return component2.getY();
        }
        return component2.getX();
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.ListActionsOperation, com.android.internal.widget.remotecompose.core.operations.layout.DecoratorComponent
    public void layout(RemoteContext remoteContext, Component component, float f, float f2) {
        this.mWidth = f;
        this.mHeight = f2;
        float f3 = this.mMaxScrollY;
        int i = this.mDirection;
        if (i != 0) {
            f3 = this.mMaxScrollX;
        }
        if (this.mTouchExpression != null) {
            float maxScrollPosition = getMaxScrollPosition(component, i);
            if (maxScrollPosition > 0.0f) {
                f3 = maxScrollPosition;
            }
        }
        remoteContext.loadFloat(Utils.idFromNan(this.mMax), f3);
        remoteContext.loadFloat(Utils.idFromNan(this.mNotchMax), this.mContentDimension);
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.TouchHandler
    public void onTouchDown(RemoteContext remoteContext, CoreDocument coreDocument, Component component, float f, float f2) {
        this.mTouchDownX = f;
        this.mTouchDownY = f2;
        this.mInitialScrollX = this.mScrollX;
        this.mInitialScrollY = this.mScrollY;
        TouchExpression touchExpression = this.mTouchExpression;
        if (touchExpression != null) {
            touchExpression.updateVariables(remoteContext);
            this.mTouchExpression.touchDown(remoteContext, f + this.mScrollX, f2 + this.mScrollY);
        }
        coreDocument.appliedTouchOperation(component);
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.TouchHandler
    public void onTouchUp(RemoteContext remoteContext, CoreDocument coreDocument, Component component, float f, float f2, float f3, float f4) {
        TouchExpression touchExpression = this.mTouchExpression;
        if (touchExpression != null) {
            touchExpression.updateVariables(remoteContext);
            this.mTouchExpression.touchUp(remoteContext, f + this.mScrollX, f2 + this.mScrollY, f3, f4);
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.TouchHandler
    public void onTouchDrag(RemoteContext remoteContext, CoreDocument coreDocument, Component component, float f, float f2) {
        TouchExpression touchExpression = this.mTouchExpression;
        if (touchExpression != null) {
            touchExpression.updateVariables(remoteContext);
            this.mTouchExpression.touchDrag(remoteContext, this.mScrollX + f, this.mScrollY + f2);
        }
        float f3 = f - this.mTouchDownX;
        float f4 = f2 - this.mTouchDownY;
        if (Utils.isVariable(this.mPositionExpression)) {
            return;
        }
        if (this.mDirection == 0) {
            this.mScrollY = Math.max(-this.mMaxScrollY, Math.min(0.0f, this.mInitialScrollY + f4));
        } else {
            this.mScrollX = Math.max(-this.mMaxScrollX, Math.min(0.0f, this.mInitialScrollX + f3));
        }
    }

    public void setHorizontalScrollDimension(float f, float f2) {
        this.mHostDimension = f;
        this.mContentDimension = f2;
        this.mMaxScrollX = f2 - f;
    }

    public void setVerticalScrollDimension(float f, float f2) {
        this.mHostDimension = f;
        this.mContentDimension = f2;
        this.mMaxScrollY = f2 - f;
    }

    public float getContentDimension() {
        return this.mContentDimension;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.ScrollDelegate
    public float getScrollX(float f) {
        if (this.mDirection == 1) {
            return this.mScrollX;
        }
        return 0.0f;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.ScrollDelegate
    public float getScrollY(float f) {
        if (this.mDirection == 0) {
            return this.mScrollY;
        }
        return 0.0f;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.ScrollDelegate
    public boolean handlesHorizontalScroll() {
        return this.mDirection == 1;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.ScrollDelegate
    public boolean handlesVerticalScroll() {
        return this.mDirection == 0;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.ListActionsOperation, com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addTags(SerializeTags.MODIFIER).addType(CLASS_NAME).add("direction", Integer.valueOf(this.mDirection)).add("max", Float.valueOf(this.mMax)).add("notchMax", Float.valueOf(this.mNotchMax)).add("scrollValue", Float.valueOf(isVerticalScroll() ? this.mScrollY : this.mScrollX)).add("maxScrollValue", Float.valueOf(isVerticalScroll() ? this.mMaxScrollY : this.mMaxScrollX)).add("contentDimension", Float.valueOf(this.mContentDimension)).add("hostDimension", Float.valueOf(this.mHostDimension));
    }

    @Override // com.android.internal.widget.remotecompose.core.semantics.ScrollableComponent
    public int scrollDirection() {
        return handlesVerticalScroll() ? 2 : 1;
    }

    @Override // com.android.internal.widget.remotecompose.core.semantics.ScrollableComponent
    public int scrollByOffset(RemoteContext remoteContext, int i) {
        this.mTouchExpression = null;
        if (handlesVerticalScroll()) {
            this.mScrollY = Math.max(-this.mMaxScrollY, Math.min(0.0f, this.mScrollY + i));
            return i;
        }
        this.mScrollX = Math.max(-this.mMaxScrollX, Math.min(0.0f, this.mScrollX + i));
        return i;
    }

    @Override // com.android.internal.widget.remotecompose.core.semantics.ScrollableComponent
    public boolean scrollDirection(RemoteContext remoteContext, ScrollableComponent.ScrollDirection scrollDirection) {
        float f = this.mHostDimension * 0.7f;
        if (scrollDirection == ScrollableComponent.ScrollDirection.FORWARD || scrollDirection == ScrollableComponent.ScrollDirection.DOWN || scrollDirection == ScrollableComponent.ScrollDirection.RIGHT) {
            f *= -1.0f;
        }
        return scrollByOffset(remoteContext, (int) f) != 0;
    }

    @Override // com.android.internal.widget.remotecompose.core.semantics.ScrollableComponent
    public boolean showOnScreen(RemoteContext remoteContext, Component component) {
        float f;
        float[] fArr = new float[2];
        component.getLocationInWindow(fArr);
        if (handlesVerticalScroll()) {
            f = fArr[1];
        } else {
            f = fArr[0];
        }
        int i = (int) (-f);
        return i == 0 || scrollByOffset(remoteContext, i) != 0;
    }

    @Override // com.android.internal.widget.remotecompose.core.semantics.ScrollableComponent
    public ScrollableComponent.ScrollAxisRange getScrollAxisRange() {
        if (handlesVerticalScroll()) {
            return new ScrollableComponent.ScrollAxisRange(this.mScrollY, this.mMaxScrollY, true, true);
        }
        return new ScrollableComponent.ScrollAxisRange(this.mScrollX, this.mMaxScrollX, true, true);
    }
}
