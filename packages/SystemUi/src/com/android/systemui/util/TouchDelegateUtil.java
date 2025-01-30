package com.android.systemui.util;

import android.graphics.Rect;
import android.view.TouchDelegate;
import android.view.View;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class TouchDelegateUtil {
    public static final TouchDelegateUtil INSTANCE = new TouchDelegateUtil();

    private TouchDelegateUtil() {
    }

    public static void expandTouchAreaAsParent(final View view, final View view2) {
        view.post(new Runnable() { // from class: com.android.systemui.util.TouchDelegateUtil$expandTouchAreaAsParent$1
            @Override // java.lang.Runnable
            public final void run() {
                Rect rect = new Rect();
                view2.getHitRect(rect);
                View view3 = view;
                rect.left = 0;
                rect.top = 0;
                rect.right = view3.getWidth();
                rect.bottom = view3.getHeight();
                view.setTouchDelegate(new TouchDelegate(rect, view2));
            }
        });
    }
}
