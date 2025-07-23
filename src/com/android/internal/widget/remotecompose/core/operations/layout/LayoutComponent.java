package com.android.internal.widget.remotecompose.core.operations.layout;

import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.TouchListener;
import com.android.internal.widget.remotecompose.core.VariableSupport;
import com.android.internal.widget.remotecompose.core.operations.BitmapData;
import com.android.internal.widget.remotecompose.core.operations.ComponentData;
import com.android.internal.widget.remotecompose.core.operations.MatrixRestore;
import com.android.internal.widget.remotecompose.core.operations.MatrixSave;
import com.android.internal.widget.remotecompose.core.operations.MatrixTranslate;
import com.android.internal.widget.remotecompose.core.operations.layout.animation.AnimationSpec;
import com.android.internal.widget.remotecompose.core.operations.layout.modifiers.ComponentModifiers;
import com.android.internal.widget.remotecompose.core.operations.layout.modifiers.ComponentVisibilityOperation;
import com.android.internal.widget.remotecompose.core.operations.layout.modifiers.DimensionModifierOperation;
import com.android.internal.widget.remotecompose.core.operations.layout.modifiers.GraphicsLayerModifierOperation;
import com.android.internal.widget.remotecompose.core.operations.layout.modifiers.HeightInModifierOperation;
import com.android.internal.widget.remotecompose.core.operations.layout.modifiers.HeightModifierOperation;
import com.android.internal.widget.remotecompose.core.operations.layout.modifiers.ModifierOperation;
import com.android.internal.widget.remotecompose.core.operations.layout.modifiers.PaddingModifierOperation;
import com.android.internal.widget.remotecompose.core.operations.layout.modifiers.ScrollModifierOperation;
import com.android.internal.widget.remotecompose.core.operations.layout.modifiers.WidthInModifierOperation;
import com.android.internal.widget.remotecompose.core.operations.layout.modifiers.WidthModifierOperation;
import com.android.internal.widget.remotecompose.core.operations.layout.modifiers.ZIndexModifierOperation;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import com.android.internal.widget.remotecompose.core.serialize.SerializeTags;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;

/* loaded from: classes6.dex */
public class LayoutComponent extends Component {
    private static final boolean USE_IMAGE_TEMP_FIX = true;
    protected final HashMap<Integer, Object> mCachedAttributes;
    protected ArrayList<Component> mChildrenComponents;
    protected boolean mChildrenHaveZIndex;
    protected ComponentModifiers mComponentModifiers;
    protected LayoutComponentContent mContent;
    private CanvasOperations mDrawContentOperations;
    protected GraphicsLayerModifierOperation mGraphicsLayerModifier;
    protected HeightModifierOperation mHeightModifier;
    protected ScrollDelegate mHorizontalScrollDelegate;
    protected float mPaddingBottom;
    protected float mPaddingLeft;
    protected float mPaddingRight;
    protected float mPaddingTop;
    float mScrollX;
    float mScrollY;
    protected ScrollDelegate mVerticalScrollDelegate;
    protected WidthModifierOperation mWidthModifier;
    protected ZIndexModifierOperation mZIndexModifier;

    public LayoutComponent(Component component, int i, int i2, float f, float f2, float f3, float f4) {
        super(component, i, i2, f, f2, f3, f4);
        this.mWidthModifier = null;
        this.mHeightModifier = null;
        this.mZIndexModifier = null;
        this.mGraphicsLayerModifier = null;
        this.mPaddingLeft = 0.0f;
        this.mPaddingRight = 0.0f;
        this.mPaddingTop = 0.0f;
        this.mPaddingBottom = 0.0f;
        this.mScrollX = 0.0f;
        this.mScrollY = 0.0f;
        this.mHorizontalScrollDelegate = null;
        this.mVerticalScrollDelegate = null;
        this.mComponentModifiers = new ComponentModifiers();
        this.mChildrenComponents = new ArrayList<>();
        this.mChildrenHaveZIndex = false;
        this.mContent = null;
        this.mCachedAttributes = new HashMap<>();
    }

    public float getPaddingLeft() {
        return this.mPaddingLeft;
    }

    public float getPaddingTop() {
        return this.mPaddingTop;
    }

    public float getPaddingRight() {
        return this.mPaddingRight;
    }

    public float getPaddingBottom() {
        return this.mPaddingBottom;
    }

    public WidthModifierOperation getWidthModifier() {
        return this.mWidthModifier;
    }

    public HeightModifierOperation getHeightModifier() {
        return this.mHeightModifier;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.Component
    public float getZIndex() {
        ZIndexModifierOperation zIndexModifierOperation = this.mZIndexModifier;
        if (zIndexModifierOperation != null) {
            return zIndexModifierOperation.getValue();
        }
        return this.mZIndex;
    }

    public void setCanvasOperations(CanvasOperations canvasOperations) {
        this.mDrawContentOperations = canvasOperations;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.Component
    public void inflate() {
        LayoutComponent layoutComponent;
        ArrayList<Operation> arrayList = new ArrayList<>();
        ArrayList arrayList2 = new ArrayList();
        Iterator<Operation> it = this.mList.iterator();
        while (it.hasNext()) {
            Object obj = (Operation) it.next();
            if (obj instanceof LayoutComponentContent) {
                LayoutComponentContent layoutComponentContent = (LayoutComponentContent) obj;
                this.mContent = layoutComponentContent;
                layoutComponentContent.mParent = this;
                this.mChildrenComponents.clear();
                layoutComponentContent.getComponents(this.mChildrenComponents);
                if (this.mChildrenComponents.isEmpty() && !this.mContent.mList.isEmpty()) {
                    layoutComponent = this;
                    CanvasContent canvasContent = new CanvasContent(-1, 0.0f, 0.0f, 0.0f, 0.0f, layoutComponent, -1);
                    Iterator<Operation> it2 = layoutComponent.mContent.mList.iterator();
                    while (it2.hasNext()) {
                        Operation next = it2.next();
                        if (next instanceof BitmapData) {
                            canvasContent.mList.add(next);
                            BitmapData bitmapData = (BitmapData) next;
                            int width = bitmapData.getWidth();
                            int height = bitmapData.getHeight();
                            canvasContent.setWidth(width);
                            canvasContent.setHeight(height);
                        } else if (!(next instanceof MatrixTranslate) && !(next instanceof MatrixSave) && !(next instanceof MatrixRestore)) {
                            canvasContent.mList.add(next);
                        }
                    }
                    if (!canvasContent.mList.isEmpty()) {
                        layoutComponent.mContent.mList.clear();
                        layoutComponent.mChildrenComponents.add(canvasContent);
                        canvasContent.inflate();
                    }
                } else {
                    layoutComponent = this;
                    layoutComponentContent.getData(arrayList);
                }
            } else {
                layoutComponent = this;
                if (obj instanceof ModifierOperation) {
                    if (obj instanceof ComponentVisibilityOperation) {
                        ((ComponentVisibilityOperation) obj).setParent(layoutComponent);
                    }
                    if (obj instanceof ScrollModifierOperation) {
                        ((ScrollModifierOperation) obj).inflate(layoutComponent);
                    }
                    layoutComponent.mComponentModifiers.add((ModifierOperation) obj);
                } else if (obj instanceof ComponentData) {
                    arrayList2.add(obj);
                    if (obj instanceof TouchListener) {
                        ((TouchListener) obj).setComponent(layoutComponent);
                    }
                }
            }
            this = layoutComponent;
        }
        LayoutComponent layoutComponent2 = this;
        layoutComponent2.mList.clear();
        layoutComponent2.mList.addAll(arrayList);
        layoutComponent2.mList.addAll(arrayList2);
        layoutComponent2.mList.add(layoutComponent2.mComponentModifiers);
        Iterator<Component> it3 = layoutComponent2.mChildrenComponents.iterator();
        while (it3.hasNext()) {
            Component next2 = it3.next();
            next2.mParent = layoutComponent2;
            layoutComponent2.mList.add(next2);
            if ((next2 instanceof LayoutComponent) && ((LayoutComponent) next2).mZIndexModifier != null) {
                layoutComponent2.mChildrenHaveZIndex = true;
            }
        }
        layoutComponent2.mX = 0.0f;
        layoutComponent2.mY = 0.0f;
        layoutComponent2.mPaddingLeft = 0.0f;
        layoutComponent2.mPaddingTop = 0.0f;
        layoutComponent2.mPaddingRight = 0.0f;
        layoutComponent2.mPaddingBottom = 0.0f;
        Iterator<ModifierOperation> it4 = layoutComponent2.mComponentModifiers.getList().iterator();
        WidthInModifierOperation widthInModifierOperation = null;
        HeightInModifierOperation heightInModifierOperation = null;
        while (it4.hasNext()) {
            ModifierOperation next3 = it4.next();
            if (next3 instanceof PaddingModifierOperation) {
                PaddingModifierOperation paddingModifierOperation = (PaddingModifierOperation) next3;
                float left = paddingModifierOperation.getLeft();
                float right = paddingModifierOperation.getRight();
                float top = paddingModifierOperation.getTop();
                float bottom = paddingModifierOperation.getBottom();
                layoutComponent2.mPaddingLeft += left;
                layoutComponent2.mPaddingTop += top;
                layoutComponent2.mPaddingRight += right;
                layoutComponent2.mPaddingBottom += bottom;
            } else if ((next3 instanceof WidthModifierOperation) && layoutComponent2.mWidthModifier == null) {
                layoutComponent2.mWidthModifier = (WidthModifierOperation) next3;
            } else if ((next3 instanceof HeightModifierOperation) && layoutComponent2.mHeightModifier == null) {
                layoutComponent2.mHeightModifier = (HeightModifierOperation) next3;
            } else if (next3 instanceof WidthInModifierOperation) {
                widthInModifierOperation = (WidthInModifierOperation) next3;
            } else if (next3 instanceof HeightInModifierOperation) {
                heightInModifierOperation = (HeightInModifierOperation) next3;
            } else if (next3 instanceof ZIndexModifierOperation) {
                layoutComponent2.mZIndexModifier = (ZIndexModifierOperation) next3;
            } else if (next3 instanceof GraphicsLayerModifierOperation) {
                layoutComponent2.mGraphicsLayerModifier = (GraphicsLayerModifierOperation) next3;
            } else if (next3 instanceof AnimationSpec) {
                layoutComponent2.mAnimationSpec = (AnimationSpec) next3;
            } else if (next3 instanceof ScrollDelegate) {
                ScrollDelegate scrollDelegate = (ScrollDelegate) next3;
                if (scrollDelegate.handlesHorizontalScroll()) {
                    layoutComponent2.mHorizontalScrollDelegate = scrollDelegate;
                }
                if (scrollDelegate.handlesVerticalScroll()) {
                    layoutComponent2.mVerticalScrollDelegate = scrollDelegate;
                }
            }
        }
        if (layoutComponent2.mWidthModifier == null) {
            layoutComponent2.mWidthModifier = new WidthModifierOperation(DimensionModifierOperation.Type.WRAP);
        }
        if (layoutComponent2.mHeightModifier == null) {
            layoutComponent2.mHeightModifier = new HeightModifierOperation(DimensionModifierOperation.Type.WRAP);
        }
        if (widthInModifierOperation != null) {
            layoutComponent2.mWidthModifier.setWidthIn(widthInModifierOperation);
        }
        if (heightInModifierOperation != null) {
            layoutComponent2.mHeightModifier.setHeightIn(heightInModifierOperation);
        }
        if (layoutComponent2.mAnimationSpec != AnimationSpec.DEFAULT) {
            for (int i = 0; i < layoutComponent2.mChildrenComponents.size(); i++) {
                Component component = layoutComponent2.mChildrenComponents.get(i);
                if (component != null && component.getAnimationSpec() == AnimationSpec.DEFAULT) {
                    component.setAnimationSpec(layoutComponent2.mAnimationSpec);
                }
            }
        }
        layoutComponent2.setWidth(layoutComponent2.computeModifierDefinedWidth(null));
        layoutComponent2.setHeight(layoutComponent2.computeModifierDefinedHeight(null));
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.Component
    public String toString() {
        return "UNKNOWN LAYOUT_COMPONENT";
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.Component
    public void getLocationInWindow(float[] fArr, boolean z) {
        fArr[0] = fArr[0] + this.mX + this.mPaddingLeft;
        fArr[1] = fArr[1] + this.mY + this.mPaddingTop;
        if (this.mParent != null) {
            this.mParent.getLocationInWindow(fArr, false);
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.Component
    public float getScrollX() {
        ScrollDelegate scrollDelegate = this.mHorizontalScrollDelegate;
        if (scrollDelegate != null) {
            return scrollDelegate.getScrollX(this.mScrollX);
        }
        return this.mScrollX;
    }

    public void setScrollX(float f) {
        this.mScrollX = f;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.Component
    public float getScrollY() {
        ScrollDelegate scrollDelegate = this.mVerticalScrollDelegate;
        if (scrollDelegate != null) {
            return scrollDelegate.getScrollY(this.mScrollY);
        }
        return this.mScrollY;
    }

    public void setScrollY(float f) {
        this.mScrollY = f;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.Component, com.android.internal.widget.remotecompose.core.PaintOperation
    public void paint(PaintContext paintContext) {
        if (this.mDrawContentOperations != null) {
            paintContext.save();
            paintContext.translate(this.mX, this.mY);
            this.mDrawContentOperations.paint(paintContext);
            paintContext.restore();
            return;
        }
        super.paint(paintContext);
    }

    public void drawContent(PaintContext paintContext) {
        paintContext.save();
        paintContext.translate(-this.mX, -this.mY);
        paintingComponent(paintContext);
        paintContext.restore();
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.Component
    public void paintingComponent(PaintContext paintContext) {
        Component component = paintContext.getContext().mLastComponent;
        RemoteContext context = paintContext.getContext();
        context.mLastComponent = this;
        paintContext.save();
        paintContext.translate(this.mX, this.mY);
        if (paintContext.isVisualDebug()) {
            debugBox(this, paintContext);
        }
        if (this.mGraphicsLayerModifier != null) {
            paintContext.startGraphicsLayer((int) getWidth(), (int) getHeight());
            this.mCachedAttributes.clear();
            this.mGraphicsLayerModifier.fillInAttributes(this.mCachedAttributes);
            paintContext.setGraphicsLayer(this.mCachedAttributes);
        }
        this.mComponentModifiers.paint(paintContext);
        float scrollX = this.mPaddingLeft + getScrollX();
        float scrollY = this.mPaddingTop + getScrollY();
        paintContext.translate(scrollX, scrollY);
        if (this.mChildrenHaveZIndex) {
            ArrayList arrayList = new ArrayList(this.mChildrenComponents);
            arrayList.sort(new Comparator() { // from class: com.android.internal.widget.remotecompose.core.operations.layout.LayoutComponent$$ExternalSyntheticLambda0
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return LayoutComponent.lambda$paintingComponent$0((Component) obj, (Component) obj2);
                }
            });
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                Component component2 = (Component) it.next();
                if (component2.isDirty() && (component2 instanceof VariableSupport)) {
                    component2.updateVariables(paintContext.getContext());
                    component2.markNotDirty();
                }
                context.incrementOpCount();
                component2.paint(paintContext);
            }
        } else {
            Iterator<Component> it2 = this.mChildrenComponents.iterator();
            while (it2.hasNext()) {
                Component next = it2.next();
                if (next.isDirty() && (next instanceof VariableSupport)) {
                    next.updateVariables(paintContext.getContext());
                    next.markNotDirty();
                }
                context.incrementOpCount();
                next.paint(paintContext);
            }
        }
        if (this.mGraphicsLayerModifier != null) {
            paintContext.endGraphicsLayer();
        }
        paintContext.translate(-scrollX, -scrollY);
        paintContext.restore();
        paintContext.getContext().mLastComponent = component;
    }

    static /* synthetic */ int lambda$paintingComponent$0(Component component, Component component2) {
        return (int) (component.getZIndex() - component2.getZIndex());
    }

    public float computeModifierDefinedWidth(RemoteContext remoteContext) {
        Iterator<ModifierOperation> it = this.mComponentModifiers.getList().iterator();
        float f = 0.0f;
        float f2 = 0.0f;
        float f3 = 0.0f;
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            ModifierOperation next = it.next();
            if (remoteContext != null && next.isDirty() && (next instanceof VariableSupport)) {
                ((VariableSupport) next).updateVariables(remoteContext);
                next.markNotDirty();
            }
            if (next instanceof WidthModifierOperation) {
                WidthModifierOperation widthModifierOperation = (WidthModifierOperation) next;
                if (widthModifierOperation.getType() == DimensionModifierOperation.Type.EXACT || widthModifierOperation.getType() == DimensionModifierOperation.Type.EXACT_DP) {
                    f = widthModifierOperation.getValue();
                }
            } else if (next instanceof PaddingModifierOperation) {
                PaddingModifierOperation paddingModifierOperation = (PaddingModifierOperation) next;
                f2 += paddingModifierOperation.getLeft();
                f3 += paddingModifierOperation.getRight();
            }
        }
        return f2 + f + f3;
    }

    public float computeModifierDefinedPaddingWidth(float[] fArr) {
        Iterator<ModifierOperation> it = this.mComponentModifiers.getList().iterator();
        float f = 0.0f;
        float f2 = 0.0f;
        while (it.hasNext()) {
            ModifierOperation next = it.next();
            if (next instanceof PaddingModifierOperation) {
                PaddingModifierOperation paddingModifierOperation = (PaddingModifierOperation) next;
                f += paddingModifierOperation.getLeft();
                f2 += paddingModifierOperation.getRight();
            }
        }
        fArr[0] = f;
        fArr[1] = f2;
        return f + f2;
    }

    public float computeModifierDefinedHeight(RemoteContext remoteContext) {
        Iterator<ModifierOperation> it = this.mComponentModifiers.getList().iterator();
        float f = 0.0f;
        float f2 = 0.0f;
        float f3 = 0.0f;
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            ModifierOperation next = it.next();
            if (remoteContext != null && next.isDirty() && (next instanceof VariableSupport)) {
                ((VariableSupport) next).updateVariables(remoteContext);
                next.markNotDirty();
            }
            if (next instanceof HeightModifierOperation) {
                HeightModifierOperation heightModifierOperation = (HeightModifierOperation) next;
                if (heightModifierOperation.getType() == DimensionModifierOperation.Type.EXACT || heightModifierOperation.getType() == DimensionModifierOperation.Type.EXACT_DP) {
                    f = heightModifierOperation.getValue();
                }
            } else if (next instanceof PaddingModifierOperation) {
                PaddingModifierOperation paddingModifierOperation = (PaddingModifierOperation) next;
                f2 += paddingModifierOperation.getTop();
                f3 += paddingModifierOperation.getBottom();
            }
        }
        return f2 + f + f3;
    }

    public float computeModifierDefinedPaddingHeight(float[] fArr) {
        Iterator<ModifierOperation> it = this.mComponentModifiers.getList().iterator();
        float f = 0.0f;
        float f2 = 0.0f;
        while (it.hasNext()) {
            ModifierOperation next = it.next();
            if (next instanceof PaddingModifierOperation) {
                PaddingModifierOperation paddingModifierOperation = (PaddingModifierOperation) next;
                f += paddingModifierOperation.getTop();
                f2 += paddingModifierOperation.getBottom();
            }
        }
        fArr[0] = f;
        fArr[1] = f2;
        return f + f2;
    }

    public ComponentModifiers getComponentModifiers() {
        return this.mComponentModifiers;
    }

    public ArrayList<Component> getChildrenComponents() {
        return this.mChildrenComponents;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.Component, com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        super.serialize(mapSerializer);
        mapSerializer.addTags(SerializeTags.LAYOUT_COMPONENT).add("paddingLeft", Float.valueOf(this.mPaddingLeft)).add("paddingRight", Float.valueOf(this.mPaddingRight)).add("paddingTop", Float.valueOf(this.mPaddingTop)).add("paddingBottom", Float.valueOf(this.mPaddingBottom));
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.Component
    public <T> T selfOrModifier(Class<T> cls) {
        if (cls.isInstance(this)) {
            return cls.cast(this);
        }
        Iterator<ModifierOperation> it = this.mComponentModifiers.getList().iterator();
        while (it.hasNext()) {
            ModifierOperation next = it.next();
            if (cls.isInstance(next)) {
                return cls.cast(next);
            }
        }
        return null;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.Component
    public void registerVariables(RemoteContext remoteContext) {
        CanvasOperations canvasOperations = this.mDrawContentOperations;
        if (canvasOperations != null) {
            canvasOperations.registerListening(remoteContext);
        }
    }
}
