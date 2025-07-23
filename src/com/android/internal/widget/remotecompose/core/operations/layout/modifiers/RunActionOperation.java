package com.android.internal.widget.remotecompose.core.operations.layout.modifiers;

import android.app.slice.Slice;
import com.android.internal.widget.remotecompose.core.CoreDocument;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.PaintOperation;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.operations.layout.ActionOperation;
import com.android.internal.widget.remotecompose.core.operations.layout.Component;
import com.android.internal.widget.remotecompose.core.operations.layout.Container;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes6.dex */
public class RunActionOperation extends PaintOperation implements Container {
    private static final String CLASS_NAME = "RunActionOperation";
    private static final int OP_CODE = 236;
    public ArrayList<Operation> mList = new ArrayList<>();

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public boolean isDirty() {
        return true;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void markDirty() {
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void markNotDirty() {
    }

    public String toString() {
        return "RunActionOperation()";
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.Container
    public ArrayList<Operation> getList() {
        return this.mList;
    }

    public String serializedName() {
        return "RUN_ACTION";
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addType(CLASS_NAME).add(Slice.HINT_LIST, this.mList);
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation
    public void paint(PaintContext paintContext) {
        CoreDocument document = paintContext.getContext().getDocument();
        Component component = paintContext.getContext().mLastComponent;
        if (document == null || component == null) {
            return;
        }
        Iterator<Operation> it = getList().iterator();
        while (it.hasNext()) {
            Object obj = (Operation) it.next();
            if (obj instanceof ActionOperation) {
                ((ActionOperation) obj).runAction(paintContext.getContext(), document, component, 0.0f, 0.0f);
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

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer);
    }

    public static void apply(WireBuffer wireBuffer) {
        wireBuffer.start(236);
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        list.add(new RunActionOperation());
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Operations", 236, "RunAction").description("This operation runs child actions");
    }
}
