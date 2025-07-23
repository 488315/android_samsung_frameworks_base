package com.android.internal.widget.remotecompose.core.operations.layout;

import android.app.slice.Slice;
import com.android.internal.widget.remotecompose.core.CoreDocument;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.PaintOperation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.operations.TextData;
import com.android.internal.widget.remotecompose.core.operations.layout.modifiers.ModifierOperation;
import com.android.internal.widget.remotecompose.core.operations.utilities.StringSerializer;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

/* loaded from: classes6.dex */
public abstract class ListActionsOperation extends PaintOperation implements Container, ModifierOperation, DecoratorComponent {
    String mOperationName;
    protected float mWidth = 0.0f;
    protected float mHeight = 0.0f;
    private final float[] mLocationInWindow = new float[2];
    public ArrayList<Operation> mList = new ArrayList<>();

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation
    public void paint(PaintContext paintContext) {
    }

    public ListActionsOperation(String str) {
        this.mOperationName = str;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.Container
    public ArrayList<Operation> getList() {
        return this.mList;
    }

    public String toString() {
        return this.mOperationName;
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation, com.android.internal.widget.remotecompose.core.Operation
    public void apply(RemoteContext remoteContext) {
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

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.DecoratorComponent
    public void layout(RemoteContext remoteContext, Component component, float f, float f2) {
        this.mWidth = f;
        this.mHeight = f2;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.modifiers.ModifierOperation
    public void serializeToString(int i, StringSerializer stringSerializer) {
        stringSerializer.append(i, this.mOperationName);
        Iterator<Operation> it = this.mList.iterator();
        while (it.hasNext()) {
            Object obj = (Operation) it.next();
            if (obj instanceof ActionOperation) {
                ((ActionOperation) obj).serializeToString(i + 1, stringSerializer);
            }
        }
    }

    public boolean applyActions(RemoteContext remoteContext, CoreDocument coreDocument, Component component, float f, float f2, boolean z) {
        RemoteContext remoteContext2;
        CoreDocument coreDocument2;
        Component component2;
        float f3;
        float f4;
        if (!z && !component.isVisible()) {
            return false;
        }
        if (!z && !component.contains(f, f2)) {
            return false;
        }
        float[] fArr = this.mLocationInWindow;
        fArr[0] = 0.0f;
        fArr[1] = 0.0f;
        component.getLocationInWindow(fArr);
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
        return true;
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.add(Slice.HINT_ACTIONS, new Vector());
    }
}
