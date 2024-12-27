package com.android.internal.widget.remotecompose.core.operations;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;

import com.android.internal.widget.remotecompose.core.CompanionOperation;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.WireBuffer;

import java.util.List;

public class TextMerge implements Operation {
    public static final Companion COMPANION = new Companion();
    public static final int MAX_STRING_SIZE = 4000;
    public int mSrcId1;
    public int mSrcId2;
    public int mTextId;

    public TextMerge(int textId, int srcId1, int srcId2) {
        this.mTextId = textId;
        this.mSrcId1 = srcId1;
        this.mSrcId2 = srcId2;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer buffer) {
        COMPANION.apply(buffer, this.mTextId, this.mSrcId1, this.mSrcId2);
    }

    public String toString() {
        return "TextMerge["
                + this.mTextId
                + "] = ["
                + this.mSrcId1
                + " ] + [ "
                + this.mSrcId2
                + NavigationBarInflaterView.SIZE_MOD_END;
    }

    public static class Companion implements CompanionOperation {
        private Companion() {}

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public String name() {
            return "TextData";
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public int id() {
            return 136;
        }

        public void apply(WireBuffer buffer, int textId, int srcId1, int srcId2) {
            buffer.start(136);
            buffer.writeInt(textId);
            buffer.writeInt(srcId1);
            buffer.writeInt(srcId2);
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public void read(WireBuffer buffer, List<Operation> operations) {
            int textId = buffer.readInt();
            int srcId1 = buffer.readInt();
            int srcId2 = buffer.readInt();
            operations.add(new TextMerge(textId, srcId1, srcId2));
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void apply(RemoteContext context) {
        String str1 = context.getText(this.mSrcId1);
        String str2 = context.getText(this.mSrcId2);
        context.loadText(this.mTextId, str1 + str2);
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public String deepToString(String indent) {
        return indent + toString();
    }
}
