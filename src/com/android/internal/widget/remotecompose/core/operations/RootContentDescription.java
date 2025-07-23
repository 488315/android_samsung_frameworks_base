package com.android.internal.widget.remotecompose.core.operations;

import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.RemoteComposeOperation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.semantics.AccessibleComponent;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import com.android.internal.widget.remotecompose.core.serialize.Serializable;
import java.util.List;

/* loaded from: classes6.dex */
public class RootContentDescription extends Operation implements RemoteComposeOperation, AccessibleComponent, Serializable {
    private static final String CLASS_NAME = "RootContentDescription";
    private static final int OP_CODE = 103;
    int mContentDescription;

    public static int id() {
        return 103;
    }

    public RootContentDescription(int i) {
        this.mContentDescription = i;
    }

    @Override // com.android.internal.widget.remotecompose.core.semantics.AccessibilitySemantics
    public boolean isInterestingForSemantics() {
        return this.mContentDescription != 0;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer, this.mContentDescription);
    }

    public String toString() {
        return "RootContentDescription " + this.mContentDescription;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void apply(RemoteContext remoteContext) {
        remoteContext.setDocumentContentDescription(this.mContentDescription);
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public String deepToString(String str) {
        return toString();
    }

    @Override // com.android.internal.widget.remotecompose.core.semantics.AccessibleComponent
    public Integer getContentDescriptionId() {
        return Integer.valueOf(this.mContentDescription);
    }

    public static String name() {
        return CLASS_NAME;
    }

    public static void apply(WireBuffer wireBuffer, int i) {
        wireBuffer.start(103);
        wireBuffer.writeInt(i);
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        list.add(new RootContentDescription(wireBuffer.readInt()));
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Protocol Operations", 103, CLASS_NAME).description("Content description of root").field(0, "id", "id of Int");
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addType(CLASS_NAME).add("contentDescriptionId", Integer.valueOf(this.mContentDescription));
    }
}
