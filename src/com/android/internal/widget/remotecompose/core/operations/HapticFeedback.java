package com.android.internal.widget.remotecompose.core.operations;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.SerializableToString;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.operations.utilities.StringSerializer;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import com.android.internal.widget.remotecompose.core.serialize.Serializable;
import java.util.List;

/* loaded from: classes6.dex */
public class HapticFeedback extends Operation implements SerializableToString, Serializable {
    private static final String CLASS_NAME = "HapticFeedback";
    private static final int OP_CODE = 177;
    private int mHapticFeedbackType;

    public static int id() {
        return 177;
    }

    public HapticFeedback(int i) {
        this.mHapticFeedbackType = i;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer, this.mHapticFeedbackType);
    }

    public String toString() {
        return "HapticFeedback(" + this.mHapticFeedbackType + NavigationBarInflaterView.KEY_CODE_END;
    }

    public static String name() {
        return CLASS_NAME;
    }

    public static void apply(WireBuffer wireBuffer, int i) {
        wireBuffer.start(177);
        wireBuffer.writeInt(i);
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        list.add(new HapticFeedback(wireBuffer.readInt()));
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Data Operations", 177, CLASS_NAME).description("Generate an haptic feedback").field(0, "HapticFeedbackType", "Type of haptic feedback");
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void apply(RemoteContext remoteContext) {
        remoteContext.hapticEffect(this.mHapticFeedbackType);
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public String deepToString(String str) {
        return str + toString();
    }

    @Override // com.android.internal.widget.remotecompose.core.SerializableToString
    public void serializeToString(int i, StringSerializer stringSerializer) {
        stringSerializer.append(i, getSerializedName() + "<" + this.mHapticFeedbackType + ">");
    }

    private String getSerializedName() {
        return "HAPTIC_FEEDBACK";
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addType(CLASS_NAME).add("hapticFeedbackType", Integer.valueOf(this.mHapticFeedbackType));
    }
}
