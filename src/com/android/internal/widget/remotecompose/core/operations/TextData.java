package com.android.internal.widget.remotecompose.core.operations;

import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.SerializableToString;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.operations.utilities.StringSerializer;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import com.android.internal.widget.remotecompose.core.serialize.Serializable;
import java.util.List;

/* loaded from: classes6.dex */
public class TextData extends Operation implements SerializableToString, Serializable {
    private static final String CLASS_NAME = "TextData";
    public static final int MAX_STRING_SIZE = 4000;
    private static final int OP_CODE = 102;
    public String mText;
    public final int mTextId;

    public static int id() {
        return 102;
    }

    public TextData(int i, String str) {
        this.mTextId = i;
        this.mText = str;
    }

    public void update(TextData textData) {
        this.mText = textData.mText;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer, this.mTextId, this.mText);
    }

    public String toString() {
        return "TextData[" + this.mTextId + "] = \"" + Utils.trimString(this.mText, 10) + "\"";
    }

    public static String name() {
        return CLASS_NAME;
    }

    public static void apply(WireBuffer wireBuffer, int i, String str) {
        wireBuffer.start(102);
        wireBuffer.writeInt(i);
        wireBuffer.writeUTF8(str);
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        list.add(new TextData(wireBuffer.readInt(), wireBuffer.readUTF8(4000)));
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Data Operations", 102, CLASS_NAME).description("Encode a string ").field(0, "id", "id string").field(5, "text", "encode text as a string");
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void apply(RemoteContext remoteContext) {
        remoteContext.loadText(this.mTextId, this.mText);
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public String deepToString(String str) {
        return str + toString();
    }

    @Override // com.android.internal.widget.remotecompose.core.SerializableToString
    public void serializeToString(int i, StringSerializer stringSerializer) {
        stringSerializer.append(i, getSerializedName() + "<" + this.mTextId + "> = \"" + this.mText + "\"");
    }

    private String getSerializedName() {
        return "DATA_TEXT";
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addType(CLASS_NAME).add("textId", Integer.valueOf(this.mTextId)).add("text", this.mText);
    }
}
