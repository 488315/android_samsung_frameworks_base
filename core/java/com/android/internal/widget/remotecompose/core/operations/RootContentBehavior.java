package com.android.internal.widget.remotecompose.core.operations;

import android.util.Log;

import com.android.internal.widget.remotecompose.core.CompanionOperation;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.RemoteComposeOperation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.WireBuffer;

import java.util.List;

/* loaded from: classes5.dex */
public class RootContentBehavior implements RemoteComposeOperation {
    public static final int ALIGNMENT_BOTTOM = 4;
    public static final int ALIGNMENT_CENTER = 34;
    public static final int ALIGNMENT_END = 64;
    public static final int ALIGNMENT_HORIZONTAL_CENTER = 32;
    public static final int ALIGNMENT_START = 16;
    public static final int ALIGNMENT_TOP = 1;
    public static final int ALIGNMENT_VERTICAL_CENTER = 2;
    public static final Companion COMPANION = new Companion();
    public static final int LAYOUT_HORIZONTAL_FIXED = 4;
    public static final int LAYOUT_HORIZONTAL_MATCH_PARENT = 1;
    public static final int LAYOUT_HORIZONTAL_WRAP_CONTENT = 2;
    public static final int LAYOUT_MATCH_PARENT = 9;
    public static final int LAYOUT_VERTICAL_FIXED = 32;
    public static final int LAYOUT_VERTICAL_MATCH_PARENT = 8;
    public static final int LAYOUT_VERTICAL_WRAP_CONTENT = 16;
    public static final int LAYOUT_WRAP_CONTENT = 18;
    public static final int NONE = 0;
    public static final int SCALE_CROP = 5;
    public static final int SCALE_FILL_BOUNDS = 6;
    public static final int SCALE_FILL_HEIGHT = 3;
    public static final int SCALE_FILL_WIDTH = 2;
    public static final int SCALE_FIT = 4;
    public static final int SCALE_INSIDE = 1;
    public static final int SCROLL_HORIZONTAL = 1;
    public static final int SCROLL_VERTICAL = 2;
    public static final int SIZING_LAYOUT = 1;
    public static final int SIZING_SCALE = 2;
    protected static final String TAG = "RootContentBehavior";
    int mAlignment;
    int mMode;
    int mScroll;
    int mSizing;

    public RootContentBehavior(int scroll, int alignment, int sizing, int mode) {
        this.mScroll = 0;
        this.mSizing = 0;
        this.mAlignment = 34;
        this.mMode = 0;
        switch (scroll) {
            case 0:
            case 1:
            case 2:
                this.mScroll = scroll;
                break;
            default:
                Log.e(TAG, "incorrect scroll value " + scroll);
                break;
        }
        if (alignment == 34) {
            this.mAlignment = alignment;
        } else {
            int horizontalContentAlignment = alignment & 240;
            int verticalContentAlignment = alignment & 15;
            boolean validHorizontalAlignment =
                    horizontalContentAlignment == 16
                            || horizontalContentAlignment == 32
                            || horizontalContentAlignment == 64;
            boolean validVerticalAlignment =
                    verticalContentAlignment == 1
                            || verticalContentAlignment == 2
                            || verticalContentAlignment == 4;
            if (!validHorizontalAlignment || !validVerticalAlignment) {
                Log.e(
                        TAG,
                        "incorrect alignment  h: "
                                + horizontalContentAlignment
                                + " v: "
                                + verticalContentAlignment);
            } else {
                this.mAlignment = alignment;
            }
        }
        switch (sizing) {
            case 1:
                Log.e(TAG, "sizing_layout is not yet supported");
                break;
            case 2:
                this.mSizing = sizing;
                break;
            default:
                Log.e(TAG, "incorrect sizing value " + sizing);
                break;
        }
        if (this.mSizing != 1) {
            if (this.mSizing == 2) {
                switch (mode) {
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                        this.mMode = mode;
                        break;
                    default:
                        Log.e(TAG, "incorrect mode for scale sizing, mode: " + mode);
                        break;
                }
                return;
            }
            return;
        }
        if (mode != 0) {
            Log.e(TAG, "mode for sizing layout is not yet supported");
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer buffer) {
        COMPANION.apply(buffer, this.mScroll, this.mAlignment, this.mSizing, this.mMode);
    }

    public String toString() {
        return "ROOT_CONTENT_BEHAVIOR scroll: "
                + this.mScroll
                + " sizing: "
                + this.mSizing
                + " mode: "
                + this.mMode;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void apply(RemoteContext context) {
        context.setRootContentBehavior(this.mScroll, this.mAlignment, this.mSizing, this.mMode);
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public String deepToString(String indent) {
        return toString();
    }

    public static class Companion implements CompanionOperation {
        private Companion() {}

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public String name() {
            return RootContentBehavior.TAG;
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public int id() {
            return 65;
        }

        public void apply(WireBuffer buffer, int scroll, int alignment, int sizing, int mode) {
            buffer.start(65);
            buffer.writeInt(scroll);
            buffer.writeInt(alignment);
            buffer.writeInt(sizing);
            buffer.writeInt(mode);
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public void read(WireBuffer buffer, List<Operation> operations) {
            int scroll = buffer.readInt();
            int alignment = buffer.readInt();
            int sizing = buffer.readInt();
            int mode = buffer.readInt();
            RootContentBehavior rootContentBehavior =
                    new RootContentBehavior(scroll, alignment, sizing, mode);
            operations.add(rootContentBehavior);
        }
    }
}
