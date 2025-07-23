package com.android.internal.widget.remotecompose.core.operations;

import android.media.TtmlUtils;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.RemoteComposeOperation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.VariableSupport;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.semantics.AccessibleComponent;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import com.android.internal.widget.remotecompose.core.serialize.Serializable;
import com.samsung.android.wallpaperbackup.GenerateXML;
import java.util.List;

/* loaded from: classes6.dex */
public class ClickArea extends Operation implements RemoteComposeOperation, AccessibleComponent, VariableSupport, Serializable {
    private static final String CLASS_NAME = "ClickArea";
    private static final int OP_CODE = 64;
    float mBottom;
    int mContentDescription;
    int mId;
    float mLeft;
    int mMetadata;
    float mOutBottom;
    float mOutLeft;
    float mOutRight;
    float mOutTop;
    float mRight;
    float mTop;

    public static int id() {
        return 64;
    }

    public ClickArea(int i, int i2, float f, float f2, float f3, float f4, int i3) {
        this.mId = i;
        this.mContentDescription = i2;
        this.mLeft = f;
        this.mOutLeft = f;
        this.mTop = f2;
        this.mOutTop = f2;
        this.mRight = f3;
        this.mOutRight = f3;
        this.mBottom = f4;
        this.mOutBottom = f4;
        this.mMetadata = i3;
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void registerListening(RemoteContext remoteContext) {
        if (Float.isNaN(this.mLeft)) {
            remoteContext.listensTo(Utils.idFromNan(this.mLeft), this);
        }
        if (Float.isNaN(this.mTop)) {
            remoteContext.listensTo(Utils.idFromNan(this.mTop), this);
        }
        if (Float.isNaN(this.mRight)) {
            remoteContext.listensTo(Utils.idFromNan(this.mRight), this);
        }
        if (Float.isNaN(this.mBottom)) {
            remoteContext.listensTo(Utils.idFromNan(this.mBottom), this);
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void updateVariables(RemoteContext remoteContext) {
        this.mOutLeft = Float.isNaN(this.mLeft) ? remoteContext.getFloat(Utils.idFromNan(this.mLeft)) : this.mLeft;
        this.mOutTop = Float.isNaN(this.mTop) ? remoteContext.getFloat(Utils.idFromNan(this.mTop)) : this.mTop;
        this.mRight = Float.isNaN(this.mRight) ? remoteContext.getFloat(Utils.idFromNan(this.mRight)) : this.mRight;
        this.mOutBottom = Float.isNaN(this.mBottom) ? remoteContext.getFloat(Utils.idFromNan(this.mBottom)) : this.mBottom;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer, this.mId, this.mContentDescription, this.mLeft, this.mTop, this.mRight, this.mBottom, this.mMetadata);
    }

    public String toString() {
        return "CLICK_AREA <" + this.mId + " <" + this.mContentDescription + "> <" + this.mMetadata + ">+" + this.mLeft + " " + this.mTop + " " + this.mRight + " " + this.mBottom + "+ (" + (this.mRight - this.mLeft) + " x " + (this.mBottom - this.mTop) + " }";
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void apply(RemoteContext remoteContext) {
        remoteContext.addClickArea(this.mId, this.mContentDescription, this.mOutLeft, this.mOutTop, this.mOutRight, this.mOutBottom, this.mMetadata);
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public String deepToString(String str) {
        return str + toString();
    }

    public static String name() {
        return CLASS_NAME;
    }

    @Override // com.android.internal.widget.remotecompose.core.semantics.AccessibleComponent
    public Integer getContentDescriptionId() {
        return Integer.valueOf(this.mContentDescription);
    }

    public static void apply(WireBuffer wireBuffer, int i, int i2, float f, float f2, float f3, float f4, int i3) {
        wireBuffer.start(64);
        wireBuffer.writeInt(i);
        wireBuffer.writeInt(i2);
        wireBuffer.writeFloat(f);
        wireBuffer.writeFloat(f2);
        wireBuffer.writeFloat(f3);
        wireBuffer.writeFloat(f4);
        wireBuffer.writeInt(i3);
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        list.add(new ClickArea(wireBuffer.readInt(), wireBuffer.readInt(), wireBuffer.readFloat(), wireBuffer.readFloat(), wireBuffer.readFloat(), wireBuffer.readFloat(), wireBuffer.readInt()));
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Canvas Operations", 64, CLASS_NAME).description("Define a region you can click on").field(1, "left", "The left side of the region").field(1, GenerateXML.TOP, "The top of the region").field(1, "right", "The right side of the region").field(1, GenerateXML.BOTTOM, "The bottom of the region").field(1, TtmlUtils.TAG_METADATA, "user defined string accessible in callback");
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addType(CLASS_NAME).add("id", Integer.valueOf(this.mId)).add("contentDescriptionId", Integer.valueOf(this.mContentDescription)).add("left", this.mLeft, this.mOutLeft).add(GenerateXML.TOP, this.mTop, this.mOutTop).add("right", this.mRight, this.mOutRight).add(GenerateXML.BOTTOM, this.mBottom, this.mOutBottom).add(TtmlUtils.TAG_METADATA, Integer.valueOf(this.mMetadata));
    }
}
