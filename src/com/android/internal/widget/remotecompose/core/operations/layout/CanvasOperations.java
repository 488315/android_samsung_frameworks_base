package com.android.internal.widget.remotecompose.core.operations.layout;

import android.app.slice.Slice;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.PaintOperation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.VariableSupport;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.operations.ComponentValue;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import com.android.internal.widget.remotecompose.core.serialize.Serializable;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes6.dex */
public class CanvasOperations extends PaintOperation implements VariableSupport, Container, Serializable {
    private static final String CLASS_NAME = "CanvasOperations";
    private static final int OP_CODE = 173;
    LayoutComponent mComponent;
    public ArrayList<Operation> mList = new ArrayList<>();

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void registerListening(RemoteContext remoteContext) {
        Iterator<Operation> it = this.mList.iterator();
        while (it.hasNext()) {
            Object obj = (Operation) it.next();
            if (obj instanceof VariableSupport) {
                ((VariableSupport) obj).registerListening(remoteContext);
            }
            if (obj instanceof ComponentValue) {
                this.mComponent.addComponentValue((ComponentValue) obj);
            }
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void updateVariables(RemoteContext remoteContext) {
        Iterator<Operation> it = this.mList.iterator();
        while (it.hasNext()) {
            Object obj = (Operation) it.next();
            if (obj instanceof VariableSupport) {
                ((VariableSupport) obj).updateVariables(remoteContext);
            }
        }
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
        StringBuilder sb = new StringBuilder("CanvasOperations\n");
        Iterator<Operation> it = this.mList.iterator();
        while (it.hasNext()) {
            Operation next = it.next();
            sb.append("  ");
            sb.append(next);
            sb.append(ShaderAssembler.NEWLINE);
        }
        return sb.toString();
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

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.internal.widget.remotecompose.core.PaintOperation
    public void paint(PaintContext paintContext) {
        RemoteContext context = paintContext.getContext();
        Iterator<Operation> it = this.mList.iterator();
        while (it.hasNext()) {
            Operation next = it.next();
            if ((next instanceof VariableSupport) && next.isDirty()) {
                ((VariableSupport) next).updateVariables(paintContext.getContext());
            }
            context.incrementOpCount();
            next.apply(paintContext.getContext());
        }
    }

    public static String name() {
        return "Loop";
    }

    public static void apply(WireBuffer wireBuffer) {
        wireBuffer.start(173);
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        list.add(new CanvasOperations());
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Operations", 173, name()).description("Impulse Process that runs a list of operations");
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addType(CLASS_NAME).add(Slice.HINT_LIST, this.mList);
    }

    public void setComponent(LayoutComponent layoutComponent) {
        this.mComponent = layoutComponent;
        if (layoutComponent != null) {
            layoutComponent.setCanvasOperations(this);
        }
    }
}
