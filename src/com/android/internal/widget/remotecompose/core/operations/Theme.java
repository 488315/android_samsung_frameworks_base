package com.android.internal.widget.remotecompose.core.operations;

import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.RemoteComposeOperation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import java.util.List;

/* loaded from: classes6.dex */
public class Theme extends Operation implements RemoteComposeOperation {
    private static final String CLASS_NAME = "Theme";
    public static final int DARK = -2;
    public static final int LIGHT = -3;
    private static final int OP_CODE = 63;
    public static final int UNSPECIFIED = -1;
    int mTheme;

    public static int id() {
        return 63;
    }

    public Theme(int i) {
        this.mTheme = i;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer, this.mTheme);
    }

    public String toString() {
        return "SET_THEME " + this.mTheme;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void apply(RemoteContext remoteContext) {
        remoteContext.setTheme(this.mTheme);
        markDirty();
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public String deepToString(String str) {
        return str + toString();
    }

    public static String name() {
        return CLASS_NAME;
    }

    public static void apply(WireBuffer wireBuffer, int i) {
        wireBuffer.start(63);
        wireBuffer.writeInt(i);
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        list.add(new Theme(wireBuffer.readInt()));
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Protocol Operations", 63, CLASS_NAME).description("Set a theme").field(0, "THEME", "theme id").possibleValues("UNSPECIFIED", -1).possibleValues("DARK", -2).possibleValues("LIGHT", -3);
    }
}
