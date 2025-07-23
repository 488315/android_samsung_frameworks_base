package com.android.internal.widget.remotecompose.core.operations;

import android.media.MediaMetrics;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.VariableSupport;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.operations.utilities.StringUtils;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import com.android.internal.widget.remotecompose.core.serialize.Serializable;
import java.util.List;

/* loaded from: classes6.dex */
public class TextFromFloat extends Operation implements VariableSupport, Serializable {
    private static final String CLASS_NAME = "TextFromFloat";
    public static final int MAX_STRING_SIZE = 4000;
    private static final int OP_CODE = 135;
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

    public static int id() {
        return 135;
    }

    public TextFromFloat(int i, float f, short s, short s2, int i2) {
        this.mPre = ' ';
        this.mAfter = ' ';
        this.mTextId = i;
        this.mValue = f;
        this.mDigitsAfter = s2;
        this.mDigitsBefore = s;
        this.mFlags = i2;
        this.mOutValue = f;
        int i3 = i2 & 3;
        if (i3 == 0) {
            this.mAfter = ' ';
        } else if (i3 == 1) {
            this.mAfter = (char) 0;
        } else if (i3 == 3) {
            this.mAfter = '0';
        }
        int i4 = i2 & 12;
        if (i4 == 0) {
            this.mPre = ' ';
        } else if (i4 == 4) {
            this.mPre = (char) 0;
        } else {
            if (i4 != 12) {
                return;
            }
            this.mPre = '0';
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer, this.mTextId, this.mValue, this.mDigitsBefore, this.mDigitsAfter, this.mFlags);
    }

    public String toString() {
        return "TextFromFloat[" + this.mTextId + "] = " + Utils.floatToString(this.mValue) + " " + ((int) this.mDigitsBefore) + MediaMetrics.SEPARATOR + ((int) this.mDigitsAfter) + " " + this.mFlags;
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void updateVariables(RemoteContext remoteContext) {
        if (Float.isNaN(this.mValue)) {
            this.mOutValue = remoteContext.getFloat(Utils.idFromNan(this.mValue));
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void registerListening(RemoteContext remoteContext) {
        if (Float.isNaN(this.mValue)) {
            remoteContext.listensTo(Utils.idFromNan(this.mValue), this);
        }
    }

    public static String name() {
        return CLASS_NAME;
    }

    public static void apply(WireBuffer wireBuffer, int i, float f, short s, short s2, int i2) {
        wireBuffer.start(135);
        wireBuffer.writeInt(i);
        wireBuffer.writeFloat(f);
        wireBuffer.writeInt((s << 16) | s2);
        wireBuffer.writeInt(i2);
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        int readInt = wireBuffer.readInt();
        float readFloat = wireBuffer.readFloat();
        int readInt2 = wireBuffer.readInt();
        list.add(new TextFromFloat(readInt, readFloat, (short) ((readInt2 >> 16) & 65535), (short) (readInt2 & 65535), wireBuffer.readInt()));
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Expressions Operations", 135, CLASS_NAME).description("Draw text along path object").field(0, "textId", "id of the text generated").field(0, "value", "Value to add").field(9, "prePoint", "digits before the decimal point").field(9, "pstPoint", "digit after the decimal point").field(0, "flags", "options on padding");
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void apply(RemoteContext remoteContext) {
        remoteContext.loadText(this.mTextId, StringUtils.floatToString(this.mOutValue, this.mDigitsBefore, this.mDigitsAfter, this.mPre, this.mAfter));
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public String deepToString(String str) {
        return str + toString();
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addType(CLASS_NAME).add("textId", Integer.valueOf(this.mTextId)).add("value", this.mValue, this.mOutValue).add("digitsBefore", Short.valueOf(this.mDigitsBefore)).add("digitsAfter", Short.valueOf(this.mDigitsAfter)).add("flags", Integer.valueOf(this.mFlags));
    }
}
