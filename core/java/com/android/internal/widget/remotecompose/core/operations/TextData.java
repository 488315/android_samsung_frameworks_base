package com.android.internal.widget.remotecompose.core.operations;

import com.android.internal.widget.remotecompose.core.CompanionOperation;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import java.util.List;

/* loaded from: classes5.dex */
public class TextData implements Operation {
    public static final Companion COMPANION = new Companion();
    public static final int MAX_STRING_SIZE = 4000;
    public String mText;
    public int mTextId;

    public TextData(int textId, String text) {
        this.mTextId = textId;
        this.mText = text;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer buffer) {
        COMPANION.apply(buffer, this.mTextId, this.mText);
    }

    public String toString() {
        return "TextData[" + this.mTextId + "] = \"" + Utils.trimString(this.mText, 10) + "\"";
    }

    public static class Companion implements CompanionOperation {
        private Companion() {
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public String name() {
            return "TextData";
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public int id() {
            return 102;
        }

        public void apply(WireBuffer buffer, int textId, String text) {
            buffer.start(102);
            buffer.writeInt(textId);
            buffer.writeUTF8(text);
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public void read(WireBuffer buffer, List<Operation> operations) {
            int textId = buffer.readInt();
            String text = buffer.readUTF8(4000);
            operations.add(new TextData(textId, text));
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void apply(RemoteContext context) {
        context.loadText(this.mTextId, this.mText);
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public String deepToString(String indent) {
        return indent + toString();
    }
}
