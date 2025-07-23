package com.android.internal.widget.remotecompose.core.operations;

import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.PaintOperation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.VariableSupport;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.operations.paint.PaintBundle;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import com.android.internal.widget.remotecompose.core.serialize.Serializable;
import com.samsung.android.knox.analytics.database.Contract;
import java.util.List;

/* loaded from: classes6.dex */
public class PaintData extends PaintOperation implements ComponentData, VariableSupport, Serializable {
    private static final String CLASS_NAME = "PaintData";
    public static final int MAX_STRING_SIZE = 4000;
    private static final int OP_CODE = 40;
    public PaintBundle mPaintData = new PaintBundle();

    public static int id() {
        return 40;
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void updateVariables(RemoteContext remoteContext) {
        this.mPaintData.updateVariables(remoteContext);
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void registerListening(RemoteContext remoteContext) {
        this.mPaintData.registerVars(remoteContext, this);
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer, this.mPaintData);
    }

    public String toString() {
        return "PaintData \"" + this.mPaintData + "\"";
    }

    public static String name() {
        return CLASS_NAME;
    }

    public static void apply(WireBuffer wireBuffer, PaintBundle paintBundle) {
        wireBuffer.start(40);
        paintBundle.writeBundle(wireBuffer);
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        PaintData paintData = new PaintData();
        paintData.mPaintData.readBundle(wireBuffer);
        list.add(paintData);
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Data Operations", 40, CLASS_NAME).description("Encode a Paint ").field(0, Contract.CompressedEvents.Field.LENGTH, "id string").field(11, "paint", Contract.CompressedEvents.Field.LENGTH, "path encoded as floats");
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation, com.android.internal.widget.remotecompose.core.Operation
    public String deepToString(String str) {
        return str + toString();
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation
    public void paint(PaintContext paintContext) {
        paintContext.applyPaint(this.mPaintData);
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addType(CLASS_NAME).add("paintBundle", this.mPaintData);
    }
}
