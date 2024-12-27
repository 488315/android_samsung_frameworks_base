package com.android.internal.widget.remotecompose.core.operations;

import android.media.MediaMetrics;

import com.android.internal.widget.remotecompose.core.CompanionOperation;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.VariableSupport;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.operations.utilities.StringUtils;

import java.util.List;

public class TextFromFloat implements Operation, VariableSupport {
    public static final Companion COMPANION = new Companion();
    public static final int MAX_STRING_SIZE = 4000;
    public static final int PAD_AFTER_NONE = 1;
    public static final int PAD_AFTER_SPACE = 0;
    public static final int PAD_AFTER_ZERO = 3;
    public static final int PAD_PRE_NONE = 4;
    public static final int PAD_PRE_SPACE = 0;
    public static final int PAD_PRE_ZERO = 12;
    char mAfter;
    public short mDigitsAfter;
    public short mDigitsBefore;
    public int mFlags;
    public float mOutValue;
    char mPre;
    public int mTextId;
    public float mValue;

    public TextFromFloat(
            int textId, float value, short digitsBefore, short digitsAfter, int flags) {
        this.mPre = ' ';
        this.mAfter = ' ';
        this.mTextId = textId;
        this.mValue = value;
        this.mDigitsAfter = digitsAfter;
        this.mDigitsBefore = digitsBefore;
        this.mFlags = flags;
        this.mOutValue = this.mValue;
        switch (this.mFlags & 3) {
            case 0:
                this.mAfter = ' ';
                break;
            case 1:
                this.mAfter = (char) 0;
                break;
            case 3:
                this.mAfter = '0';
                break;
        }
        switch (this.mFlags & 12) {
            case 0:
                this.mPre = ' ';
                break;
            case 4:
                this.mPre = (char) 0;
                break;
            case 12:
                this.mPre = '0';
                break;
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer buffer) {
        COMPANION.apply(
                buffer,
                this.mTextId,
                this.mValue,
                this.mDigitsAfter,
                this.mDigitsBefore,
                this.mFlags);
    }

    public String toString() {
        return "TextFromFloat["
                + this.mTextId
                + "] = "
                + Utils.floatToString(this.mValue)
                + " "
                + ((int) this.mDigitsBefore)
                + MediaMetrics.SEPARATOR
                + ((int) this.mDigitsAfter)
                + " "
                + this.mFlags;
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void updateVariables(RemoteContext context) {
        if (Float.isNaN(this.mValue)) {
            this.mOutValue = context.getFloat(Utils.idFromNan(this.mValue));
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void registerListening(RemoteContext context) {
        if (Float.isNaN(this.mValue)) {
            context.listensTo(Utils.idFromNan(this.mValue), this);
        }
    }

    public static class Companion implements CompanionOperation {
        private Companion() {}

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public String name() {
            return "TextData";
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public int id() {
            return 135;
        }

        public void apply(
                WireBuffer buffer,
                int textId,
                float value,
                short digitsBefore,
                short digitsAfter,
                int flags) {
            buffer.start(135);
            buffer.writeInt(textId);
            buffer.writeFloat(value);
            buffer.writeInt((digitsBefore << 16) | digitsAfter);
            buffer.writeInt(flags);
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public void read(WireBuffer buffer, List<Operation> operations) {
            int textId = buffer.readInt();
            float value = buffer.readFloat();
            int tmp = buffer.readInt();
            short post = (short) (tmp & 65535);
            short pre = (short) (65535 & (tmp >> 16));
            int flags = buffer.readInt();
            operations.add(new TextFromFloat(textId, value, pre, post, flags));
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void apply(RemoteContext context) {
        float v = this.mOutValue;
        String s =
                StringUtils.floatToString(
                        v, this.mDigitsBefore, this.mDigitsAfter, this.mPre, this.mAfter);
        context.loadText(this.mTextId, s);
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public String deepToString(String indent) {
        return indent + toString();
    }
}
