package com.android.internal.widget.remotecompose.core.operations;

import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.VariableSupport;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import java.util.List;

/* loaded from: classes6.dex */
public class DebugMessage extends Operation implements VariableSupport {
    private static final String CLASS_NAME = "DebugMessage";
    private static final int OP_CODE = 179;
    int mFlags;
    float mFloatValue;
    float mOutFloatValue;
    int mTextID;

    public static int id() {
        return 179;
    }

    public DebugMessage(int i, float f, int i2) {
        this.mTextID = i;
        this.mFloatValue = f;
        this.mFlags = i2;
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void updateVariables(RemoteContext remoteContext) {
        float f;
        System.out.println("Debug message : updateVariables ");
        if (Float.isNaN(this.mFloatValue)) {
            f = remoteContext.getFloat(Utils.idFromNan(this.mFloatValue));
        } else {
            f = this.mFloatValue;
        }
        this.mOutFloatValue = f;
        System.out.println("Debug message : updateVariables " + Utils.floatToString(this.mFloatValue, this.mOutFloatValue));
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void registerListening(RemoteContext remoteContext) {
        System.out.println("Debug message : registerListening ");
        if (Float.isNaN(this.mFloatValue)) {
            System.out.println("Debug message : registerListening " + this.mFloatValue);
            remoteContext.listensTo(Utils.idFromNan(this.mFloatValue), this);
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer, this.mTextID, this.mFloatValue, this.mFlags);
    }

    public String toString() {
        return "DebugMessage " + this.mTextID + ", " + Utils.floatToString(this.mFloatValue, this.mOutFloatValue) + ", " + this.mFlags;
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        list.add(new DebugMessage(wireBuffer.readInt(), wireBuffer.readFloat(), wireBuffer.readInt()));
    }

    public static String name() {
        return CLASS_NAME;
    }

    public static void apply(WireBuffer wireBuffer, int i, float f, int i2) {
        wireBuffer.start(179);
        wireBuffer.writeInt(i);
        wireBuffer.writeFloat(f);
        wireBuffer.writeInt(i2);
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("DebugMessage Operations", id(), CLASS_NAME).description("Print debugging messages").field(0, "textId", "test to print").field(1, "value", "value of a float to print").field(0, "flags", "print additional information");
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void apply(RemoteContext remoteContext) {
        String text = remoteContext.getText(this.mTextID);
        System.out.println("Debug message : " + text + " " + this.mOutFloatValue + " " + this.mFlags);
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public String deepToString(String str) {
        return str + toString();
    }
}
