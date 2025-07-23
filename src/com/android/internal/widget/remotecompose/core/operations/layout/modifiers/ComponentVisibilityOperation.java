package com.android.internal.widget.remotecompose.core.operations.layout.modifiers;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.VariableSupport;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.operations.layout.Component;
import com.android.internal.widget.remotecompose.core.operations.layout.DecoratorComponent;
import com.android.internal.widget.remotecompose.core.operations.layout.LayoutComponent;
import com.android.internal.widget.remotecompose.core.operations.utilities.StringSerializer;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import com.android.internal.widget.remotecompose.core.serialize.SerializeTags;
import java.util.List;

/* loaded from: classes6.dex */
public class ComponentVisibilityOperation extends Operation implements ModifierOperation, VariableSupport, DecoratorComponent {
    private static final int OP_CODE = 211;
    private LayoutComponent mParent;
    int mVisibility = 1;
    int mVisibilityId;

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void apply(RemoteContext remoteContext) {
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.DecoratorComponent
    public void layout(RemoteContext remoteContext, Component component, float f, float f2) {
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
    }

    public ComponentVisibilityOperation(int i) {
        this.mVisibilityId = i;
    }

    public String toString() {
        return "ComponentVisibilityOperation(" + this.mVisibilityId + NavigationBarInflaterView.KEY_CODE_END;
    }

    public String serializedName() {
        return "COMPONENT_VISIBILITY";
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.modifiers.ModifierOperation
    public void serializeToString(int i, StringSerializer stringSerializer) {
        stringSerializer.append(i, serializedName() + " = " + this.mVisibilityId);
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

    public static void apply(WireBuffer wireBuffer, int i) {
        wireBuffer.start(211);
        wireBuffer.writeInt(i);
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        list.add(new ComponentVisibilityOperation(wireBuffer.readInt()));
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Layout Operations", 211, "ComponentVisibility").description("This operation allows setting a componentvisibility from a provided value").field(0, "VALUE_ID", "Value ID representing the visibility");
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void registerListening(RemoteContext remoteContext) {
        remoteContext.listensTo(this.mVisibilityId, this);
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void updateVariables(RemoteContext remoteContext) {
        int integer = remoteContext.getInteger(this.mVisibilityId);
        if (Component.Visibility.isVisible(integer)) {
            this.mVisibility = 1;
        } else if (Component.Visibility.isGone(integer)) {
            this.mVisibility = 0;
        } else if (Component.Visibility.isInvisible(integer)) {
            this.mVisibility = 2;
        } else {
            this.mVisibility = 0;
        }
        LayoutComponent layoutComponent = this.mParent;
        if (layoutComponent != null) {
            layoutComponent.setVisibility(this.mVisibility);
        }
    }

    public void setParent(LayoutComponent layoutComponent) {
        this.mParent = layoutComponent;
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addTags(SerializeTags.MODIFIER).addType("ComponentVisibilityOperation").add("visibilityId", Integer.valueOf(this.mVisibilityId)).add("visibility", Component.Visibility.toString(this.mVisibility));
    }
}
