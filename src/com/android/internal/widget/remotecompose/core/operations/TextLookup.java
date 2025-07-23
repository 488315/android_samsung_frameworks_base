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
public class TextLookup extends Operation implements VariableSupport, Serializable {
    private static final String CLASS_NAME = "TextFromFloat";
    public static final int MAX_STRING_SIZE = 4000;
    private static final int OP_CODE = 151;
    public int mDataSetId;
    public float mIndex;
    public float mOutIndex;
    public int mTextId;

    public static int id() {
        return 151;
    }

    public TextLookup(int i, int i2, float f) {
        this.mTextId = i;
        this.mDataSetId = i2;
        this.mIndex = f;
        this.mOutIndex = f;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer, this.mTextId, this.mDataSetId, this.mIndex);
    }

    public String toString() {
        return "TextLookup[" + Utils.idString(this.mTextId) + "] = " + Utils.idString(this.mDataSetId) + " " + Utils.floatToString(this.mIndex);
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void updateVariables(RemoteContext remoteContext) {
        if (Float.isNaN(this.mIndex)) {
            this.mOutIndex = remoteContext.getFloat(Utils.idFromNan(this.mIndex));
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void registerListening(RemoteContext remoteContext) {
        if (Float.isNaN(this.mIndex)) {
            remoteContext.listensTo(Utils.idFromNan(this.mIndex), this);
        }
    }

    public static String name() {
        return CLASS_NAME;
    }

    public static void apply(WireBuffer wireBuffer, int i, int i2, float f) {
        wireBuffer.start(151);
        wireBuffer.writeInt(i);
        wireBuffer.writeInt(i2);
        wireBuffer.writeFloat(f);
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        list.add(new TextLookup(wireBuffer.readInt(), wireBuffer.readInt(), wireBuffer.readFloat()));
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Expressions Operations", 151, CLASS_NAME).description("Look an array and turn into a text object").field(0, "textId", "id of the text generated").field(1, "dataSet", "float pointer to the array/list to turn int a string").field(1, "index", "index of element to return");
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void apply(RemoteContext remoteContext) {
        remoteContext.loadText(this.mTextId, remoteContext.getText(remoteContext.getCollectionsAccess().getId(this.mDataSetId, (int) this.mOutIndex)));
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
