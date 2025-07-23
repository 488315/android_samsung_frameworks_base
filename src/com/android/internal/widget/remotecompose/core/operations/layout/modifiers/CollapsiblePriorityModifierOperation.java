package com.android.internal.widget.remotecompose.core.operations.layout.modifiers;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.operations.utilities.StringSerializer;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import com.android.internal.widget.remotecompose.core.serialize.Serializable;
import com.android.internal.widget.remotecompose.core.serialize.SerializeTags;
import java.util.List;

/* loaded from: classes6.dex */
public class CollapsiblePriorityModifierOperation extends Operation implements ModifierOperation, Serializable {
    public static final String CLASS_NAME = "CollapsiblePriorityModifierOperation";
    private static final int OP_CODE = 235;
    private int mOrientation;
    private float mPriority;

    public static int id() {
        return 235;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void apply(RemoteContext remoteContext) {
    }

    public CollapsiblePriorityModifierOperation(int i, float f) {
        this.mOrientation = i;
        this.mPriority = f;
    }

    public float getPriority() {
        return this.mPriority;
    }

    public int getOrientation() {
        return this.mOrientation;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer, this.mOrientation, this.mPriority);
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public String deepToString(String str) {
        return "";
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        list.add(new CollapsiblePriorityModifierOperation(wireBuffer.readInt(), wireBuffer.readFloat()));
    }

    public static String name() {
        return CLASS_NAME;
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Layout Operations", 235, "CollapsiblePriorityModifier").description("Add additional priority to children of Collapsible layouts").field(0, "orientation", "Horizontal(0) or Vertical (1)").field(1, "priority", "The associated priority");
    }

    public static void apply(WireBuffer wireBuffer, int i, float f) {
        wireBuffer.start(235);
        wireBuffer.writeInt(i);
        wireBuffer.writeFloat(f);
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addTags(SerializeTags.MODIFIER).addType(name()).add("orientation", Integer.valueOf(this.mOrientation)).add("priority", Float.valueOf(this.mPriority));
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.modifiers.ModifierOperation
    public void serializeToString(int i, StringSerializer stringSerializer) {
        stringSerializer.append(i, "PRIORITY = [" + getPriority() + "] (" + this.mOrientation + NavigationBarInflaterView.KEY_CODE_END);
    }
}
