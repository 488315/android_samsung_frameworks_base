package com.android.internal.widget.remotecompose.core.operations;

import android.app.admin.DevicePolicyResources;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.PaintOperation;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import com.android.internal.widget.remotecompose.core.serialize.Serializable;
import java.util.List;

/* loaded from: classes6.dex */
public class ClipPath extends PaintOperation implements Serializable {
    private static final String CLASS_NAME = "ClipPath";
    public static final int DIFFERENCE = 1;
    public static final int INTERSECT = 2;
    private static final int OP_CODE = 38;
    public static final int PATH_CLIP_DIFFERENCE = 1;
    public static final int PATH_CLIP_INTERSECT = 2;
    public static final int PATH_CLIP_REPLACE = 0;
    public static final int PATH_CLIP_REVERSE_DIFFERENCE = 5;
    public static final int PATH_CLIP_UNDEFINED = 6;
    public static final int PATH_CLIP_UNION = 3;
    public static final int PATH_CLIP_XOR = 4;
    public static final int REPLACE = 0;
    public static final int REVERSE_DIFFERENCE = 5;
    public static final int UNDEFINED = 6;
    public static final int UNION = 3;
    public static final int XOR = 4;
    int mId;
    int mRegionOp;

    public static int id() {
        return 38;
    }

    public ClipPath(int i, int i2) {
        this.mId = i;
        this.mRegionOp = i2;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer, this.mId);
    }

    public String toString() {
        return "ClipPath " + this.mId + NavigationBarInflaterView.GRAVITY_SEPARATOR;
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        int i = wireBuffer.readInt();
        list.add(new ClipPath(1048575 & i, i >> 24));
    }

    public static String name() {
        return CLASS_NAME;
    }

    public static void apply(WireBuffer wireBuffer, int i) {
        wireBuffer.start(38);
        wireBuffer.writeInt(i);
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Canvas Operations", 38, CLASS_NAME).description("Intersect the current clip with the path").field(0, "id", "id of the path");
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation
    public void paint(PaintContext paintContext) {
        paintContext.clipPath(this.mId, this.mRegionOp);
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addType(CLASS_NAME).add("id", Integer.valueOf(this.mId)).add("regionOp", regionOpToString());
    }

    String regionOpToString() {
        int i = this.mRegionOp;
        if (i == 0) {
            return "REPLACE";
        }
        if (i == 1) {
            return "DIFFERENCE";
        }
        if (i == 2) {
            return "INTERSECT";
        }
        if (i == 4) {
            return "XOR";
        }
        if (i == 5) {
            return "REVERSE_DIFFERENCE";
        }
        return DevicePolicyResources.UNDEFINED;
    }
}
