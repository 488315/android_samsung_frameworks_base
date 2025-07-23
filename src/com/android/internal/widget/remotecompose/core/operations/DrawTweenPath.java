package com.android.internal.widget.remotecompose.core.operations;

import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.PaintOperation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.VariableSupport;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import java.util.List;

/* loaded from: classes6.dex */
public class DrawTweenPath extends PaintOperation implements VariableSupport {
    private static final String CLASS_NAME = "DrawTweenPath";
    private static final int OP_CODE = 125;
    float mOutStart;
    float mOutStop;
    float mOutTween;
    int mPath1Id;
    int mPath2Id;
    float mStart;
    float mStop;
    float mTween;

    public static int id() {
        return 125;
    }

    public DrawTweenPath(int i, int i2, float f, float f2, float f3) {
        this.mTween = f;
        this.mOutTween = f;
        this.mStart = f2;
        this.mOutStart = f2;
        this.mStop = f3;
        this.mOutStop = f3;
        this.mPath1Id = i;
        this.mPath2Id = i2;
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void updateVariables(RemoteContext remoteContext) {
        this.mOutTween = Float.isNaN(this.mTween) ? remoteContext.getFloat(Utils.idFromNan(this.mTween)) : this.mTween;
        this.mOutStart = Float.isNaN(this.mStart) ? remoteContext.getFloat(Utils.idFromNan(this.mStart)) : this.mStart;
        this.mOutStop = Float.isNaN(this.mStop) ? remoteContext.getFloat(Utils.idFromNan(this.mStop)) : this.mStop;
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void registerListening(RemoteContext remoteContext) {
        if (Float.isNaN(this.mTween)) {
            remoteContext.listensTo(Utils.idFromNan(this.mTween), this);
        }
        if (Float.isNaN(this.mStart)) {
            remoteContext.listensTo(Utils.idFromNan(this.mStart), this);
        }
        if (Float.isNaN(this.mStop)) {
            remoteContext.listensTo(Utils.idFromNan(this.mStop), this);
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer, this.mPath1Id, this.mPath2Id, this.mTween, this.mStart, this.mStop);
    }

    public String toString() {
        return "DrawTweenPath " + this.mPath1Id + " " + this.mPath2Id + " " + Utils.floatToString(this.mTween, this.mOutTween) + " " + Utils.floatToString(this.mStart, this.mOutStart) + " - " + Utils.floatToString(this.mStop, this.mOutStop);
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        list.add(new DrawTweenPath(wireBuffer.readInt(), wireBuffer.readInt(), wireBuffer.readFloat(), wireBuffer.readFloat(), wireBuffer.readFloat()));
    }

    public static String name() {
        return CLASS_NAME;
    }

    public static void apply(WireBuffer wireBuffer, int i, int i2, float f, float f2, float f3) {
        wireBuffer.start(125);
        wireBuffer.writeInt(i);
        wireBuffer.writeInt(i2);
        wireBuffer.writeFloat(f);
        wireBuffer.writeFloat(f2);
        wireBuffer.writeFloat(f3);
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Draw Operations", 125, CLASS_NAME).description("Draw text along path object").field(0, "pathId1", "id of path 1").field(0, "pathId2", "id of path 2").field(1, "tween", "interpolate between the two paths").field(1, "start", "trim the start of the path").field(1, "yOffset", "trim the end of the path");
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation
    public void paint(PaintContext paintContext) {
        paintContext.drawTweenPath(this.mPath1Id, this.mPath2Id, this.mOutTween, this.mOutStart, this.mOutStop);
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addType(CLASS_NAME).add("path1Id", Integer.valueOf(this.mPath1Id)).add("path2Id", Integer.valueOf(this.mPath2Id)).add("tween", this.mTween, this.mOutTween).add("start", this.mStart, this.mOutStart).add("stop", this.mStop, this.mOutStop);
    }
}
