package com.android.internal.widget.remotecompose.core.operations.layout.modifiers;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.TtmlUtils;
import com.android.internal.widget.remotecompose.core.CoreDocument;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.SerializableToString;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.operations.layout.ActionOperation;
import com.android.internal.widget.remotecompose.core.operations.layout.Component;
import com.android.internal.widget.remotecompose.core.operations.utilities.StringSerializer;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import com.android.internal.widget.remotecompose.core.serialize.Serializable;
import com.android.internal.widget.remotecompose.core.serialize.SerializeTags;
import java.util.List;

/* loaded from: classes6.dex */
public class HostActionMetadataOperation extends Operation implements ActionOperation, SerializableToString, Serializable {
    private static final int OP_CODE = 216;
    int mActionId;
    int mMetadataId;

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void apply(RemoteContext remoteContext) {
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
    }

    public HostActionMetadataOperation(int i, int i2) {
        this.mActionId = i;
        this.mMetadataId = i2;
    }

    public String toString() {
        return "HostMetadataActionOperation(" + this.mActionId + ":" + this.mMetadataId + NavigationBarInflaterView.KEY_CODE_END;
    }

    public int getActionId() {
        return this.mActionId;
    }

    public String serializedName() {
        return "HOST_METADATA_ACTION";
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.ActionOperation, com.android.internal.widget.remotecompose.core.SerializableToString
    public void serializeToString(int i, StringSerializer stringSerializer) {
        stringSerializer.append(i, serializedName() + " = " + this.mActionId + ", " + this.mMetadataId);
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

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.ActionOperation
    public void runAction(RemoteContext remoteContext, CoreDocument coreDocument, Component component, float f, float f2) {
        String text = remoteContext.getText(this.mMetadataId);
        if (text == null) {
            text = "";
        }
        remoteContext.runAction(this.mActionId, text);
    }

    public static void apply(WireBuffer wireBuffer, int i, int i2) {
        wireBuffer.start(216);
        wireBuffer.writeInt(i);
        wireBuffer.writeInt(i2);
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        list.add(new HostActionMetadataOperation(wireBuffer.readInt(), wireBuffer.readInt()));
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Layout Operations", 216, "HostAction").description("Host action. This operation represents a host action").field(0, "ACTION_ID", "Host Action ID").field(0, "METADATA", "Host Action Text Metadata ID");
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addTags(SerializeTags.MODIFIER).addType("HostActionOperation").add("id", Integer.valueOf(this.mActionId)).add(TtmlUtils.TAG_METADATA, Integer.valueOf(this.mMetadataId));
    }
}
