package com.android.systemui.plugins;

import android.graphics.Rect;
import android.view.View;
import com.android.systemui.plugins.annotations.DependsOn;
import com.android.systemui.plugins.annotations.ProvidesInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
@ProvidesInterface(version = 2)
@DependsOn(target = DarkReceiver.class)
/* loaded from: classes2.dex */
public interface DarkIconDispatcher {
    public static final int DEFAULT_DARK_ICON_TINT = -1291845632;
    public static final int DEFAULT_ICON_TINT = -301989889;
    public static final int DEFAULT_INVERSE_ICON_TINT = -16777216;
    public static final int DEFAULT_WHITE_ICON_TINT = -301989889;
    public static final int VERSION = 2;
    public static final Rect sTmpRect = new Rect();
    public static final int[] sTmpInt2 = new int[2];

    static int getInverseTint(Collection<Rect> collection, View view, int i) {
        if (isInAreas(collection, view)) {
            return i;
        }
        return -16777216;
    }

    static int getTint(Collection<Rect> collection, View view, int i) {
        if (isInAreas(collection, view)) {
            return i;
        }
        return -301989889;
    }

    static boolean isInArea(Rect rect, Rect rect2) {
        if (rect.isEmpty()) {
            return true;
        }
        sTmpRect.set(rect);
        int i = rect2.left;
        int width = rect2.width();
        return Math.max(0, Math.min(i + width, rect.right) - Math.max(i, rect.left)) * 2 > width && (rect.top <= 0);
    }

    static boolean isInAreas(Collection<Rect> collection, View view) {
        if (collection.isEmpty()) {
            return true;
        }
        Iterator<Rect> it = collection.iterator();
        while (it.hasNext()) {
            if (isInArea(it.next(), view)) {
                return true;
            }
        }
        return false;
    }

    void addDarkReceiver(DarkReceiver darkReceiver);

    void applyDark(DarkReceiver darkReceiver);

    void removeDarkReceiver(DarkReceiver darkReceiver);

    void setIconsDarkArea(ArrayList<Rect> arrayList);

    static boolean isInAreas(Collection<Rect> collection, Rect rect) {
        if (collection.isEmpty()) {
            return true;
        }
        Iterator<Rect> it = collection.iterator();
        while (it.hasNext()) {
            if (isInArea(it.next(), rect)) {
                return true;
            }
        }
        return false;
    }

    static boolean isInArea(Rect rect, View view) {
        if (rect.isEmpty()) {
            return true;
        }
        sTmpRect.set(rect);
        int[] iArr = sTmpInt2;
        view.getLocationOnScreen(iArr);
        int i = iArr[0];
        return Math.max(0, Math.min(view.getWidth() + i, rect.right) - Math.max(i, rect.left)) * 2 > view.getWidth() && (rect.top <= 0);
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    @ProvidesInterface(version = 3)
    public interface DarkReceiver {
        public static final int VERSION = 3;

        void onDarkChanged(ArrayList<Rect> arrayList, float f, int i);

        default void onDarkChangedWithContrast(ArrayList<Rect> arrayList, int i, int i2) {
        }
    }
}
