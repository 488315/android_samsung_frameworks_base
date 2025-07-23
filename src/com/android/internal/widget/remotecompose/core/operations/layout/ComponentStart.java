package com.android.internal.widget.remotecompose.core.operations.layout;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.android.internal.accessibility.dialog.AccessibilityButtonChooserActivity;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public class ComponentStart extends Operation implements Container {
    public static final int BUTTON = 5;
    public static final int CHECKBOX = 6;
    public static final int CURVED_TEXT = 8;
    public static final int CUSTOM = 10;
    public static final int DEFAULT = 0;
    public static final int IMAGE = 12;
    public static final int LAYOUT = 2;
    public static final int LAYOUT_BOX = 14;
    public static final int LAYOUT_COLUMN = 16;
    public static final int LAYOUT_CONTENT = 3;
    public static final int LAYOUT_ROW = 15;
    public static final int LOTTIE = 11;
    public static final int ROOT_LAYOUT = 1;
    public static final int SCROLL_CONTENT = 4;
    public static final int STATE_BOX_CONTENT = 13;
    public static final int STATE_HOST = 9;
    public static final int TEXT = 7;
    public static final int UNKNOWN = -1;
    int mComponentId;
    float mHeight;
    int mType;
    float mWidth;
    public ArrayList<Operation> mList = new ArrayList<>();
    float mX = 0.0f;
    float mY = 0.0f;

    public static int id() {
        return 2;
    }

    public static int size() {
        return 13;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void apply(RemoteContext remoteContext) {
    }

    public int getType() {
        return this.mType;
    }

    public float getX() {
        return this.mX;
    }

    public float getY() {
        return this.mY;
    }

    public float getWidth() {
        return this.mWidth;
    }

    public float getHeight() {
        return this.mHeight;
    }

    public int getComponentId() {
        return this.mComponentId;
    }

    public ComponentStart(int i, int i2, float f, float f2) {
        this.mType = 0;
        this.mType = i;
        this.mComponentId = i2;
        this.mWidth = f;
        this.mHeight = f2;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer, this.mType, this.mComponentId, this.mWidth, this.mHeight);
    }

    public String toString() {
        return "COMPONENT_START (type " + this.mType + " " + typeDescription(this.mType) + ") - (" + this.mX + ", " + this.mY + " - " + this.mWidth + " x " + this.mHeight + NavigationBarInflaterView.KEY_CODE_END;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public String deepToString(String str) {
        StringBuilder sb = new StringBuilder();
        if (str == null) {
            str = "";
        }
        sb.append(str);
        sb.append(toString());
        return sb.toString();
    }

    public static String typeDescription(int i) {
        switch (i) {
            case 0:
                return "DEFAULT";
            case 1:
                return "ROOT_LAYOUT";
            case 2:
                return "LAYOUT";
            case 3:
                return "CONTENT";
            case 4:
                return "SCROLL_CONTENT";
            case 5:
                return "BUTTON";
            case 6:
                return "CHECKBOX";
            case 7:
                return "TEXT";
            case 8:
                return "CURVED_TEXT";
            case 9:
                return "STATE_HOST";
            case 10:
                return "CUSTOM";
            case 11:
                return "LOTTIE";
            case 12:
                return "IMAGE";
            default:
                return "UNKNOWN";
        }
    }

    public static String name() {
        return "ComponentStart";
    }

    public static void apply(WireBuffer wireBuffer, int i, int i2, float f, float f2) {
        wireBuffer.start(2);
        wireBuffer.writeInt(i);
        wireBuffer.writeInt(i2);
        wireBuffer.writeFloat(f);
        wireBuffer.writeFloat(f2);
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        list.add(new ComponentStart(wireBuffer.readInt(), wireBuffer.readInt(), wireBuffer.readFloat(), wireBuffer.readFloat()));
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Layout Operations", id(), name()).description("Basic component encapsulating draw commands.This is not resizable.").field(0, AccessibilityButtonChooserActivity.EXTRA_TYPE_TO_CHOOSE, "Type of components").field(0, "COMPONENT_ID", "unique id for this component").field(1, "WIDTH", "width of the component").field(1, "HEIGHT", "height of the component");
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.Container
    public ArrayList<Operation> getList() {
        return this.mList;
    }
}
