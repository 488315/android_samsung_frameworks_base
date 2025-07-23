package com.android.internal.widget.remotecompose.core.operations;

import android.app.jank.AppJankStats;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.RemoteComposeOperation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.semantics.ScrollableComponent;
import java.util.List;

/* loaded from: classes6.dex */
public class RootContentBehavior extends Operation implements RemoteComposeOperation, ScrollableComponent {
    public static final int ALIGNMENT_BOTTOM = 4;
    public static final int ALIGNMENT_CENTER = 34;
    public static final int ALIGNMENT_END = 64;
    public static final int ALIGNMENT_HORIZONTAL_CENTER = 32;
    public static final int ALIGNMENT_START = 16;
    public static final int ALIGNMENT_TOP = 1;
    public static final int ALIGNMENT_VERTICAL_CENTER = 2;
    private static final String CLASS_NAME = "RootContentBehavior";
    public static final int LAYOUT_HORIZONTAL_FIXED = 4;
    public static final int LAYOUT_HORIZONTAL_MATCH_PARENT = 1;
    public static final int LAYOUT_HORIZONTAL_WRAP_CONTENT = 2;
    public static final int LAYOUT_MATCH_PARENT = 9;
    public static final int LAYOUT_VERTICAL_FIXED = 32;
    public static final int LAYOUT_VERTICAL_MATCH_PARENT = 8;
    public static final int LAYOUT_VERTICAL_WRAP_CONTENT = 16;
    public static final int LAYOUT_WRAP_CONTENT = 18;
    public static final int NONE = 0;
    private static final int OP_CODE = 65;
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

    public static int id() {
        return 65;
    }

    @Override // com.android.internal.widget.remotecompose.core.semantics.ScrollableComponent
    public int scrollByOffset(RemoteContext remoteContext, int i) {
        return i;
    }

    public RootContentBehavior(int i, int i2, int i3, int i4) {
        this.mScroll = 0;
        this.mSizing = 0;
        this.mAlignment = 34;
        this.mMode = 0;
        if (i == 0 || i == 1 || i == 2) {
            this.mScroll = i;
        } else {
            System.out.println("RootContentBehaviorincorrect scroll value " + i);
        }
        if (i2 == 34) {
            this.mAlignment = i2;
        } else {
            int i5 = i2 & 240;
            int i6 = i2 & 15;
            boolean z = i5 == 16 || i5 == 32 || i5 == 64;
            boolean z2 = i6 == 1 || i6 == 2 || i6 == 4;
            if (z && z2) {
                this.mAlignment = i2;
            } else {
                System.out.println("RootContentBehaviorincorrect alignment  h: " + i5 + " v: " + i6);
            }
        }
        if (i3 == 1) {
            System.out.println("RootContentBehaviorsizing_layout is not yet supported");
        } else if (i3 == 2) {
            this.mSizing = i3;
        } else {
            System.out.println("RootContentBehaviorincorrect sizing value " + i3);
        }
        int i7 = this.mSizing;
        if (i7 == 1) {
            if (i4 != 0) {
                System.out.println("RootContentBehaviormode for sizing layout is not yet supported");
            }
        } else if (i7 == 2) {
            switch (i4) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                    this.mMode = i4;
                    break;
                default:
                    System.out.println("RootContentBehaviorincorrect mode for scale sizing, mode: " + i4);
                    break;
            }
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer, this.mScroll, this.mAlignment, this.mSizing, this.mMode);
    }

    public String toString() {
        return "ROOT_CONTENT_BEHAVIOR scroll: " + this.mScroll + " sizing: " + this.mSizing + " mode: " + this.mMode;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void apply(RemoteContext remoteContext) {
        remoteContext.setRootContentBehavior(this.mScroll, this.mAlignment, this.mSizing, this.mMode);
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public String deepToString(String str) {
        return toString();
    }

    public static String name() {
        return "RootContentBehavior";
    }

    public static void apply(WireBuffer wireBuffer, int i, int i2, int i3, int i4) {
        wireBuffer.start(65);
        wireBuffer.writeInt(i);
        wireBuffer.writeInt(i2);
        wireBuffer.writeInt(i3);
        wireBuffer.writeInt(i4);
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        list.add(new RootContentBehavior(wireBuffer.readInt(), wireBuffer.readInt(), wireBuffer.readInt(), wireBuffer.readInt()));
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Protocol Operations", 65, "RootContentBehavior").description("Describes the behaviour of the root").field(0, AppJankStats.WIDGET_CATEGORY_SCROLL, AppJankStats.WIDGET_CATEGORY_SCROLL).possibleValues("SCROLL_HORIZONTAL", 1).possibleValues("SCROLL_VERTICAL", 2).field(0, "alignment", "alignment").possibleValues("ALIGNMENT_TOP", 1).possibleValues("ALIGNMENT_VERTICAL_CENTER", 2).possibleValues("ALIGNMENT_BOTTOM", 4).possibleValues("ALIGNMENT_START", 16).possibleValues("ALIGNMENT_START", 16).possibleValues("ALIGNMENT_END", 64).field(0, "sizing", "sizing").possibleValues("SCALE_INSIDE", 1).possibleValues("SCALE_FIT", 4).possibleValues("SCALE_FILL_WIDTH", 2).possibleValues("SCALE_FILL_HEIGHT", 3).possibleValues("SCALE_CROP", 5).possibleValues("SCALE_FILL_BOUNDS", 6).field(0, "mode", "mode").possibleValues("LAYOUT_HORIZONTAL_MATCH_PARENT", 1).possibleValues("LAYOUT_HORIZONTAL_WRAP_CONTENT", 2).possibleValues("LAYOUT_HORIZONTAL_FIXED", 4).possibleValues("LAYOUT_VERTICAL_MATCH_PARENT", 8).possibleValues("LAYOUT_VERTICAL_WRAP_CONTENT", 16).possibleValues("LAYOUT_VERTICAL_FIXED", 32).possibleValues("LAYOUT_MATCH_PARENT", 9).possibleValues("LAYOUT_WRAP_CONTENT", 18);
    }

    @Override // com.android.internal.widget.remotecompose.core.semantics.AccessibilitySemantics
    public boolean isInterestingForSemantics() {
        return this.mScroll != 0;
    }

    @Override // com.android.internal.widget.remotecompose.core.semantics.ScrollableComponent
    public boolean supportsScrollByOffset() {
        return this.mScroll != 0;
    }

    @Override // com.android.internal.widget.remotecompose.core.semantics.ScrollableComponent
    public int scrollDirection() {
        return this.mScroll;
    }
}
