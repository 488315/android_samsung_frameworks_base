package com.android.internal.widget.remotecompose.core.operations;

import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.PaintOperation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.VariableSupport;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import com.android.internal.widget.remotecompose.core.serialize.Serializable;
import java.util.List;

/* loaded from: classes6.dex */
public class PathTween extends PaintOperation implements VariableSupport, Serializable {
    private static final String CLASS_NAME = "PathTween";
    private static final int OP_CODE = 158;
    public int mOutId;
    public int mPathId1;
    public int mPathId2;
    public float mTween;
    public float mTweenOut;

    public static int id() {
        return 158;
    }

    public PathTween(int i, int i2, int i3, float f) {
        this.mOutId = i;
        this.mPathId1 = i2;
        this.mPathId2 = i3;
        this.mTween = f;
        this.mTweenOut = f;
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void updateVariables(RemoteContext remoteContext) {
        this.mTweenOut = Float.isNaN(this.mTween) ? remoteContext.getFloat(Utils.idFromNan(this.mTween)) : this.mTween;
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void registerListening(RemoteContext remoteContext) {
        if (Float.isNaN(this.mTween)) {
            remoteContext.listensTo(Utils.idFromNan(this.mTween), this);
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer, this.mOutId, this.mPathId1, this.mPathId2, this.mTween);
    }

    public String toString() {
        return "PathTween[" + this.mOutId + "] = [" + this.mPathId1 + " ] + [ " + this.mPathId2 + "], " + Utils.floatToString(this.mTween, this.mTweenOut);
    }

    public static String name() {
        return CLASS_NAME;
    }

    public static void apply(WireBuffer wireBuffer, int i, int i2, int i3, float f) {
        wireBuffer.start(158);
        wireBuffer.writeInt(i);
        wireBuffer.writeInt(i2);
        wireBuffer.writeInt(i3);
        wireBuffer.writeFloat(f);
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        list.add(new PathTween(wireBuffer.readInt(), wireBuffer.readInt(), wireBuffer.readInt(), wireBuffer.readFloat()));
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Data Operations", 158, CLASS_NAME).description("Merge two string into one").field(0, "pathId", "id of the path").field(0, "srcPathId1", "id of the path").field(0, "srcPathId1", "x Shift of the path");
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation, com.android.internal.widget.remotecompose.core.Operation
    public String deepToString(String str) {
        return str + toString();
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation
    public void paint(PaintContext paintContext) {
        paintContext.tweenPath(this.mOutId, this.mPathId1, this.mPathId2, this.mTweenOut);
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addType(CLASS_NAME).add("outId", Integer.valueOf(this.mOutId)).add("pathId1", Integer.valueOf(this.mPathId1)).add("pathId2", Integer.valueOf(this.mPathId2)).add("tween", this.mTween, this.mTweenOut);
    }
}
