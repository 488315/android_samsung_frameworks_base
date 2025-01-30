package com.android.wm.shell.draganddrop;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DragAndDropUtil {
    public static float calculateFontSizeWithScale(float f, float f2) {
        return (float) (f2 > 1.3f ? Math.floor(Math.ceil(f / f2) * 1.2999999523162842d) : Math.ceil(f));
    }
}
