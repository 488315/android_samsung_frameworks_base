package com.android.systemui.plugins;

import android.graphics.Rect;
import android.view.View;
import android.widget.ImageView;
import com.android.systemui.plugins.annotations.DependsOn;
import com.android.systemui.plugins.annotations.ProvidesInterface;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@ProvidesInterface(version = 2)
@DependsOn(target = DarkReceiver.class)
/* loaded from: classes2.dex */
public interface DarkIconDispatcher {
    public static final int DEFAULT_DARK_ICON_TINT = -1291845632;
    public static final int DEFAULT_ICON_TINT = -301989889;
    public static final int DEFAULT_WHITE_ICON_TINT = -301989889;
    public static final int VERSION = 2;
    public static final Rect sTmpRect = new Rect();
    public static final int[] sTmpInt2 = new int[2];

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    @ProvidesInterface(version = 2)
    public interface DarkReceiver {
        public static final int VERSION = 2;

        void onDarkChanged(ArrayList<Rect> arrayList, float f, int i);
    }

    static int getTint(ArrayList<Rect> arrayList, View view, int i) {
        if (isInAreas(arrayList, view)) {
            return i;
        }
        return -301989889;
    }

    static boolean isInArea(Rect rect, View view) {
        if (rect.isEmpty()) {
            return true;
        }
        sTmpRect.set(rect);
        int[] iArr = sTmpInt2;
        view.getLocationOnScreen(iArr);
        int i = iArr[0];
        return (Math.max(0, Math.min(view.getWidth() + i, rect.right) - Math.max(i, rect.left)) * 2 > view.getWidth()) && (rect.top <= 0);
    }

    static boolean isInAreas(ArrayList<Rect> arrayList, View view) {
        if (arrayList.isEmpty()) {
            return true;
        }
        Iterator<Rect> it = arrayList.iterator();
        while (it.hasNext()) {
            if (isInArea(it.next(), view)) {
                return true;
            }
        }
        return false;
    }

    void addDarkReceiver(ImageView imageView);

    void addDarkReceiver(DarkReceiver darkReceiver);

    void applyDark(DarkReceiver darkReceiver);

    void removeDarkReceiver(ImageView imageView);

    void removeDarkReceiver(DarkReceiver darkReceiver);

    void setIconsDarkArea(ArrayList<Rect> arrayList);
}
