package com.android.internal.widget.remotecompose.core.operations.layout.modifiers;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;

/* loaded from: classes6.dex */
public class ShapeType {
    public static final int CIRCLE = 1;
    public static final int RECTANGLE = 0;
    public static final int ROUNDED_RECTANGLE = 2;

    public static String getString(int i) {
        if (i == 0) {
            return "RECTANGLE";
        }
        if (i == 1) {
            return "CIRCLE";
        }
        if (i == 2) {
            return "ROUNDED_RECTANGLE";
        }
        return "INVALID_SHAPE_TYPE[" + i + NavigationBarInflaterView.SIZE_MOD_END;
    }
}
