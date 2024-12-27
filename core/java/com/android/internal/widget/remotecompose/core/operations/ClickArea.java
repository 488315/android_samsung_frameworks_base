package com.android.internal.widget.remotecompose.core.operations;

import com.android.internal.widget.remotecompose.core.CompanionOperation;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.RemoteComposeOperation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.WireBuffer;

import java.util.List;

/* loaded from: classes5.dex */
public class ClickArea implements RemoteComposeOperation {
    public static final Companion COMPANION = new Companion();
    float mBottom;
    int mContentDescription;
    int mId;
    float mLeft;
    int mMetadata;
    float mRight;
    float mTop;

    public ClickArea(
            int id,
            int contentDescription,
            float left,
            float top,
            float right,
            float bottom,
            int metadata) {
        this.mId = id;
        this.mContentDescription = contentDescription;
        this.mLeft = left;
        this.mTop = top;
        this.mRight = right;
        this.mBottom = bottom;
        this.mMetadata = metadata;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer buffer) {
        COMPANION.apply(
                buffer,
                this.mId,
                this.mContentDescription,
                this.mLeft,
                this.mTop,
                this.mRight,
                this.mBottom,
                this.mMetadata);
    }

    public String toString() {
        return "CLICK_AREA <"
                + this.mId
                + " <"
                + this.mContentDescription
                + "> <"
                + this.mMetadata
                + ">+"
                + this.mLeft
                + " "
                + this.mTop
                + " "
                + this.mRight
                + " "
                + this.mBottom
                + "+ ("
                + (this.mRight - this.mLeft)
                + " x "
                + (this.mBottom - this.mTop)
                + " }";
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void apply(RemoteContext context) {
        if (context.getMode() != RemoteContext.ContextMode.DATA) {
            return;
        }
        context.addClickArea(
                this.mId,
                this.mContentDescription,
                this.mLeft,
                this.mTop,
                this.mRight,
                this.mBottom,
                this.mMetadata);
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public String deepToString(String indent) {
        return indent + toString();
    }

    public static class Companion implements CompanionOperation {
        private Companion() {}

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public String name() {
            return "ClickArea";
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public int id() {
            return 64;
        }

        public void apply(
                WireBuffer buffer,
                int id,
                int contentDescription,
                float left,
                float top,
                float right,
                float bottom,
                int metadata) {
            buffer.start(64);
            buffer.writeInt(id);
            buffer.writeInt(contentDescription);
            buffer.writeFloat(left);
            buffer.writeFloat(top);
            buffer.writeFloat(right);
            buffer.writeFloat(bottom);
            buffer.writeInt(metadata);
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public void read(WireBuffer buffer, List<Operation> operations) {
            int id = buffer.readInt();
            int contentDescription = buffer.readInt();
            float left = buffer.readFloat();
            float top = buffer.readFloat();
            float right = buffer.readFloat();
            float bottom = buffer.readFloat();
            int metadata = buffer.readInt();
            ClickArea clickArea =
                    new ClickArea(id, contentDescription, left, top, right, bottom, metadata);
            operations.add(clickArea);
        }
    }
}
