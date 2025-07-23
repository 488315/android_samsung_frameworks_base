package com.android.internal.widget.remotecompose.core.operations.layout.modifiers;

import com.android.internal.widget.remotecompose.core.CoreDocument;
import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.PaintOperation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.SerializableToString;
import com.android.internal.widget.remotecompose.core.VariableSupport;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.operations.MatrixRestore;
import com.android.internal.widget.remotecompose.core.operations.MatrixSave;
import com.android.internal.widget.remotecompose.core.operations.layout.ClickHandler;
import com.android.internal.widget.remotecompose.core.operations.layout.ClickModifierOperation;
import com.android.internal.widget.remotecompose.core.operations.layout.Component;
import com.android.internal.widget.remotecompose.core.operations.layout.DecoratorComponent;
import com.android.internal.widget.remotecompose.core.operations.layout.TouchHandler;
import com.android.internal.widget.remotecompose.core.operations.utilities.StringSerializer;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import com.android.internal.widget.remotecompose.core.serialize.Serializable;
import com.android.internal.widget.remotecompose.core.serialize.SerializeTags;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes6.dex */
public class ComponentModifiers extends PaintOperation implements DecoratorComponent, ClickHandler, TouchHandler, SerializableToString, Serializable {
    ArrayList<ModifierOperation> mList = new ArrayList<>();

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
    }

    public ArrayList<ModifierOperation> getList() {
        return this.mList;
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation, com.android.internal.widget.remotecompose.core.Operation
    public void apply(RemoteContext remoteContext) {
        super.apply(remoteContext);
        Iterator<ModifierOperation> it = this.mList.iterator();
        while (it.hasNext()) {
            it.next().apply(remoteContext);
            remoteContext.incrementOpCount();
        }
    }

    public String toString() {
        Iterator<ModifierOperation> it = this.mList.iterator();
        String str = "ComponentModifiers \n";
        while (it.hasNext()) {
            str = str + "    " + it.next().toString() + ShaderAssembler.NEWLINE;
        }
        return str;
    }

    @Override // com.android.internal.widget.remotecompose.core.SerializableToString
    public void serializeToString(int i, StringSerializer stringSerializer) {
        stringSerializer.append(i, "MODIFIERS");
        Iterator<ModifierOperation> it = this.mList.iterator();
        while (it.hasNext()) {
            it.next().serializeToString(i + 1, stringSerializer);
        }
    }

    public void add(ModifierOperation modifierOperation) {
        this.mList.add(modifierOperation);
    }

    public int size() {
        return this.mList.size();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.internal.widget.remotecompose.core.PaintOperation
    public void paint(PaintContext paintContext) {
        Iterator<ModifierOperation> it = this.mList.iterator();
        float f = 0.0f;
        float f2 = 0.0f;
        while (it.hasNext()) {
            ModifierOperation next = it.next();
            if (next.isDirty() && (next instanceof VariableSupport)) {
                ((VariableSupport) next).updateVariables(paintContext.getContext());
                next.markNotDirty();
            }
            if (next instanceof PaddingModifierOperation) {
                PaddingModifierOperation paddingModifierOperation = (PaddingModifierOperation) next;
                paintContext.translate(paddingModifierOperation.getLeft(), paddingModifierOperation.getTop());
                f += paddingModifierOperation.getLeft();
                f2 += paddingModifierOperation.getTop();
            }
            if (!(next instanceof MatrixSave) && !(next instanceof MatrixRestore)) {
                if (next instanceof ClickModifierOperation) {
                    paintContext.translate(-f, -f2);
                    ((ClickModifierOperation) next).paint(paintContext);
                    paintContext.translate(f, f2);
                } else if (next instanceof PaintOperation) {
                    ((PaintOperation) next).paint(paintContext);
                }
            }
        }
        paintContext.translate(-f, -f2);
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.DecoratorComponent
    public void layout(RemoteContext remoteContext, Component component, float f, float f2) {
        Iterator<ModifierOperation> it = this.mList.iterator();
        float f3 = f;
        float f4 = f2;
        while (it.hasNext()) {
            ModifierOperation next = it.next();
            if (next instanceof PaddingModifierOperation) {
                PaddingModifierOperation paddingModifierOperation = (PaddingModifierOperation) next;
                f3 -= paddingModifierOperation.getLeft() + paddingModifierOperation.getRight();
                f4 -= paddingModifierOperation.getTop() + paddingModifierOperation.getBottom();
            }
            if (next instanceof ClickModifierOperation) {
                ((DecoratorComponent) next).layout(remoteContext, component, f, f2);
            } else if (next instanceof DecoratorComponent) {
                ((DecoratorComponent) next).layout(remoteContext, component, f3, f4);
            }
        }
    }

    public void addAll(ArrayList<ModifierOperation> arrayList) {
        this.mList.addAll(arrayList);
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.ClickHandler
    public void onClick(RemoteContext remoteContext, CoreDocument coreDocument, Component component, float f, float f2) {
        RemoteContext remoteContext2;
        CoreDocument coreDocument2;
        Component component2;
        float f3;
        float f4;
        Iterator<ModifierOperation> it = this.mList.iterator();
        while (it.hasNext()) {
            ModifierOperation next = it.next();
            if (next instanceof ClickHandler) {
                remoteContext2 = remoteContext;
                coreDocument2 = coreDocument;
                component2 = component;
                f3 = f;
                f4 = f2;
                ((ClickHandler) next).onClick(remoteContext2, coreDocument2, component2, f3, f4);
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
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.TouchHandler
    public void onTouchDown(RemoteContext remoteContext, CoreDocument coreDocument, Component component, float f, float f2) {
        RemoteContext remoteContext2;
        CoreDocument coreDocument2;
        Component component2;
        float f3;
        float f4;
        Iterator<ModifierOperation> it = this.mList.iterator();
        while (it.hasNext()) {
            ModifierOperation next = it.next();
            if (next instanceof TouchHandler) {
                remoteContext2 = remoteContext;
                coreDocument2 = coreDocument;
                component2 = component;
                f3 = f;
                f4 = f2;
                ((TouchHandler) next).onTouchDown(remoteContext2, coreDocument2, component2, f3, f4);
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
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.TouchHandler
    public void onTouchUp(RemoteContext remoteContext, CoreDocument coreDocument, Component component, float f, float f2, float f3, float f4) {
        Iterator<ModifierOperation> it = this.mList.iterator();
        while (it.hasNext()) {
            ModifierOperation next = it.next();
            if (next instanceof TouchHandler) {
                ((TouchHandler) next).onTouchUp(remoteContext, coreDocument, component, f, f2, f3, f4);
            }
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.TouchHandler
    public void onTouchCancel(RemoteContext remoteContext, CoreDocument coreDocument, Component component, float f, float f2) {
        RemoteContext remoteContext2;
        CoreDocument coreDocument2;
        Component component2;
        float f3;
        float f4;
        Iterator<ModifierOperation> it = this.mList.iterator();
        while (it.hasNext()) {
            ModifierOperation next = it.next();
            if (next instanceof TouchHandler) {
                remoteContext2 = remoteContext;
                coreDocument2 = coreDocument;
                component2 = component;
                f3 = f;
                f4 = f2;
                ((TouchHandler) next).onTouchCancel(remoteContext2, coreDocument2, component2, f3, f4);
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
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.TouchHandler
    public void onTouchDrag(RemoteContext remoteContext, CoreDocument coreDocument, Component component, float f, float f2) {
        RemoteContext remoteContext2;
        CoreDocument coreDocument2;
        Component component2;
        float f3;
        float f4;
        Iterator<ModifierOperation> it = this.mList.iterator();
        while (it.hasNext()) {
            ModifierOperation next = it.next();
            if (next instanceof TouchHandler) {
                remoteContext2 = remoteContext;
                coreDocument2 = coreDocument;
                component2 = component;
                f3 = f;
                f4 = f2;
                ((TouchHandler) next).onTouchDrag(remoteContext2, coreDocument2, component2, f3, f4);
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
    }

    public boolean hasHorizontalScroll() {
        Iterator<ModifierOperation> it = this.mList.iterator();
        while (it.hasNext()) {
            ModifierOperation next = it.next();
            if ((next instanceof ScrollModifierOperation) && ((ScrollModifierOperation) next).isHorizontalScroll()) {
                return true;
            }
        }
        return false;
    }

    public boolean hasVerticalScroll() {
        Iterator<ModifierOperation> it = this.mList.iterator();
        while (it.hasNext()) {
            ModifierOperation next = it.next();
            if ((next instanceof ScrollModifierOperation) && ((ScrollModifierOperation) next).isVerticalScroll()) {
                return true;
            }
        }
        return false;
    }

    public void setHorizontalScrollDimension(float f, float f2) {
        Iterator<ModifierOperation> it = this.mList.iterator();
        while (it.hasNext()) {
            ModifierOperation next = it.next();
            if (next instanceof ScrollModifierOperation) {
                ScrollModifierOperation scrollModifierOperation = (ScrollModifierOperation) next;
                if (scrollModifierOperation.isHorizontalScroll()) {
                    scrollModifierOperation.setHorizontalScrollDimension(f, f2);
                }
            }
        }
    }

    public void setVerticalScrollDimension(float f, float f2) {
        Iterator<ModifierOperation> it = this.mList.iterator();
        while (it.hasNext()) {
            ModifierOperation next = it.next();
            if (next instanceof ScrollModifierOperation) {
                ScrollModifierOperation scrollModifierOperation = (ScrollModifierOperation) next;
                if (scrollModifierOperation.isVerticalScroll()) {
                    scrollModifierOperation.setVerticalScrollDimension(f, f2);
                }
            }
        }
    }

    public float getHorizontalScrollDimension() {
        Iterator<ModifierOperation> it = this.mList.iterator();
        while (it.hasNext()) {
            ModifierOperation next = it.next();
            if (next instanceof ScrollModifierOperation) {
                ScrollModifierOperation scrollModifierOperation = (ScrollModifierOperation) next;
                if (scrollModifierOperation.isHorizontalScroll()) {
                    return scrollModifierOperation.getContentDimension();
                }
            }
        }
        return 0.0f;
    }

    public float getVerticalScrollDimension() {
        Iterator<ModifierOperation> it = this.mList.iterator();
        while (it.hasNext()) {
            ModifierOperation next = it.next();
            if (next instanceof ScrollModifierOperation) {
                ScrollModifierOperation scrollModifierOperation = (ScrollModifierOperation) next;
                if (scrollModifierOperation.isVerticalScroll()) {
                    return scrollModifierOperation.getContentDimension();
                }
            }
        }
        return 0.0f;
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addTags(SerializeTags.MODIFIER).addType("ComponentModifiers").add("modifiers", this.mList);
    }
}
