package com.android.internal.widget.remotecompose.core.operations;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.VariableSupport;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import com.android.internal.widget.remotecompose.core.serialize.Serializable;
import java.util.List;

/* loaded from: classes6.dex */
public class TextMerge extends Operation implements VariableSupport, Serializable {
    private static final String CLASS_NAME = "TextMerge";
    private static final int OP_CODE = 136;
    public int mSrcId1;
    public int mSrcId2;
    public int mTextId;

    public static int id() {
        return 136;
    }

    public TextMerge(int i, int i2, int i3) {
        this.mTextId = i;
        this.mSrcId1 = i2;
        this.mSrcId2 = i3;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer, this.mTextId, this.mSrcId1, this.mSrcId2);
    }

    public String toString() {
        return "TextMerge[" + this.mTextId + "] = [" + this.mSrcId1 + " ] + [ " + this.mSrcId2 + NavigationBarInflaterView.SIZE_MOD_END;
    }

    public static String name() {
        return CLASS_NAME;
    }

    public static void apply(WireBuffer wireBuffer, int i, int i2, int i3) {
        wireBuffer.start(136);
        wireBuffer.writeInt(i);
        wireBuffer.writeInt(i2);
        wireBuffer.writeInt(i3);
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        list.add(new TextMerge(wireBuffer.readInt(), wireBuffer.readInt(), wireBuffer.readInt()));
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Data Operations", 136, CLASS_NAME).description("Merge two string into one").field(0, "textId", "id of the text").field(0, "srcTextId1", "id of the path").field(0, "srcTextId1", "x Shift of the text");
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void apply(RemoteContext remoteContext) {
        String text = remoteContext.getText(this.mSrcId1);
        String text2 = remoteContext.getText(this.mSrcId2);
        remoteContext.loadText(this.mTextId, text + text2);
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void updateVariables(RemoteContext remoteContext) {
        apply(remoteContext);
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void registerListening(RemoteContext remoteContext) {
        remoteContext.listensTo(this.mSrcId1, this);
        remoteContext.listensTo(this.mSrcId2, this);
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public String deepToString(String str) {
        return str + this;
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addType(CLASS_NAME).add("id", Integer.valueOf(this.mTextId)).add("leftId", Integer.valueOf(this.mSrcId1)).add("rightId", Integer.valueOf(this.mSrcId2));
    }
}
