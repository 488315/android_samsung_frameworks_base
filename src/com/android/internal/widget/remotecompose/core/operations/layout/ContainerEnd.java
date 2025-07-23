package com.android.internal.widget.remotecompose.core.operations.layout;

import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import java.util.List;

/* loaded from: classes6.dex */
public class ContainerEnd extends Operation {
    public static int id() {
        return 214;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void apply(RemoteContext remoteContext) {
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer);
    }

    public String toString() {
        return "LIST_END";
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public String deepToString(String str) {
        StringBuilder sb = new StringBuilder();
        if (str == null) {
            str = "";
        }
        sb.append(str);
        sb.append(toString());
        return sb.toString();
    }

    public static String name() {
        return "ListEnd";
    }

    public static void apply(WireBuffer wireBuffer) {
        wireBuffer.start(id());
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        list.add(new ContainerEnd());
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Layout Operations", id(), name()).description("End tag for list of operations.");
    }
}
