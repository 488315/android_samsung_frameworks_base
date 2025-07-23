package com.android.internal.widget.remotecompose.core.operations;

import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import java.util.List;

/* loaded from: classes6.dex */
public class TextLength extends Operation {
    private static final String CLASS_NAME = "TextLength";
    private static final int OP_CODE = 156;
    public int mLengthId;
    public int mTextId;

    public static int id() {
        return 156;
    }

    public TextLength(int i, int i2) {
        this.mLengthId = i;
        this.mTextId = i2;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer, this.mLengthId, this.mTextId);
    }

    public String toString() {
        return "TextLength[" + this.mLengthId + "] = " + this.mTextId;
    }

    public static String name() {
        return CLASS_NAME;
    }

    public static void apply(WireBuffer wireBuffer, int i, int i2) {
        wireBuffer.start(156);
        wireBuffer.writeInt(i);
        wireBuffer.writeInt(i2);
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        list.add(new TextLength(wireBuffer.readInt(), wireBuffer.readInt()));
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Expressions Operations", 156, CLASS_NAME).description("get the length of the text and store in float table").field(0, "id", "id of float length").field(0, "value", "index of text");
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void apply(RemoteContext remoteContext) {
        remoteContext.loadFloat(this.mLengthId, remoteContext.getText(this.mTextId).length());
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public String deepToString(String str) {
        return str + toString();
    }
}
