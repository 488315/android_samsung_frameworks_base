package com.android.internal.widget.remotecompose.core.operations;

import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.VariableSupport;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import com.android.internal.widget.remotecompose.core.serialize.Serializable;
import java.util.List;

/* loaded from: classes6.dex */
public class TextLookupInt extends Operation implements VariableSupport, Serializable {
    private static final String CLASS_NAME = "TextFromINT";
    public static final int MAX_STRING_SIZE = 4000;
    private static final int OP_CODE = 153;
    public int mDataSetId;
    public int mIndex;
    public int mOutIndex;
    public int mTextId;

    public static int id() {
        return 153;
    }

    public TextLookupInt(int i, int i2, int i3) {
        this.mTextId = i;
        this.mDataSetId = i2;
        this.mIndex = i3;
        this.mOutIndex = i3;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer, this.mTextId, this.mDataSetId, this.mIndex);
    }

    public String toString() {
        return "TextLookupInt[" + Utils.idString(this.mTextId) + "] = " + Utils.idString(this.mDataSetId) + " " + this.mIndex;
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void updateVariables(RemoteContext remoteContext) {
        this.mOutIndex = remoteContext.getInteger(this.mIndex);
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void registerListening(RemoteContext remoteContext) {
        remoteContext.listensTo(this.mIndex, this);
    }

    public static String name() {
        return CLASS_NAME;
    }

    public static void apply(WireBuffer wireBuffer, int i, int i2, int i3) {
        wireBuffer.start(153);
        wireBuffer.writeInt(i);
        wireBuffer.writeInt(i2);
        wireBuffer.writeInt(i3);
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        list.add(new TextLookupInt(wireBuffer.readInt(), wireBuffer.readInt(), wireBuffer.readInt()));
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Expressions Operations", 153, CLASS_NAME).description("Look up an array and turn into a text object").field(0, "textId", "id of the text generated").field(0, "dataSetId", "id to the array/list to turn int a string").field(0, "index", "index of the element to return");
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void apply(RemoteContext remoteContext) {
        remoteContext.loadText(this.mTextId, remoteContext.getText(remoteContext.getCollectionsAccess().getId(this.mDataSetId, this.mOutIndex)));
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public String deepToString(String str) {
        return str + toString();
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addType(CLASS_NAME).add("textId", Integer.valueOf(this.mTextId)).add("dataSetId", Integer.valueOf(this.mDataSetId)).add("indexId", this.mIndex, this.mOutIndex);
    }
}
