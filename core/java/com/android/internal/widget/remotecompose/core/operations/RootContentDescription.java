package com.android.internal.widget.remotecompose.core.operations;

import com.android.internal.widget.remotecompose.core.CompanionOperation;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.RemoteComposeOperation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import java.util.List;

/* loaded from: classes5.dex */
public class RootContentDescription implements RemoteComposeOperation {
    public static final Companion COMPANION = new Companion();
    int mContentDescription;

    public RootContentDescription(int contentDescription) {
        this.mContentDescription = contentDescription;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer buffer) {
        COMPANION.apply(buffer, this.mContentDescription);
    }

    public String toString() {
        return "RootContentDescription " + this.mContentDescription;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void apply(RemoteContext context) {
        context.setDocumentContentDescription(this.mContentDescription);
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public String deepToString(String indent) {
        return toString();
    }

    public static class Companion implements CompanionOperation {
        private Companion() {
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public String name() {
            return "RootContentDescription";
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public int id() {
            return 103;
        }

        public void apply(WireBuffer buffer, int contentDescription) {
            buffer.start(103);
            buffer.writeInt(contentDescription);
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public void read(WireBuffer buffer, List<Operation> operations) {
            int contentDescription = buffer.readInt();
            RootContentDescription header = new RootContentDescription(contentDescription);
            operations.add(header);
        }
    }
}
