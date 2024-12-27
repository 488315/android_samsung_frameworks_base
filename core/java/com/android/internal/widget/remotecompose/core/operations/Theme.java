package com.android.internal.widget.remotecompose.core.operations;

import com.android.internal.widget.remotecompose.core.CompanionOperation;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.RemoteComposeOperation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.WireBuffer;

import java.util.List;

public class Theme implements RemoteComposeOperation {
    public static final Companion COMPANION = new Companion();
    public static final int DARK = -2;
    public static final int LIGHT = -3;
    public static final int UNSPECIFIED = -1;
    int mTheme;

    public Theme(int theme) {
        this.mTheme = theme;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer buffer) {
        COMPANION.apply(buffer, this.mTheme);
    }

    public String toString() {
        return "SET_THEME " + this.mTheme;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void apply(RemoteContext context) {
        context.setTheme(this.mTheme);
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public String deepToString(String indent) {
        return indent + toString();
    }

    public static class Companion implements CompanionOperation {
        private Companion() {}

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public String name() {
            return "SetTheme";
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public int id() {
            return 63;
        }

        public void apply(WireBuffer buffer, int theme) {
            buffer.start(63);
            buffer.writeInt(theme);
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public void read(WireBuffer buffer, List<Operation> operations) {
            int theme = buffer.readInt();
            operations.add(new Theme(theme));
        }
    }
}
