package android.hardware.camera2.utils;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class ListUtils {
    public static <T> boolean listContains(List<T> list, T t) {
        if (list == null) {
            return false;
        }
        return list.contains(t);
    }

    public static <T> boolean listElementsEqualTo(List<T> list, T t) {
        return list != null && list.size() == 1 && list.contains(t);
    }

    public static <T> String listToString(List<T> list) {
        if (list == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder(NavigationBarInflaterView.SIZE_MOD_START);
        int size = list.size();
        Iterator<T> it = list.iterator();
        int i = 0;
        while (it.hasNext()) {
            sb.append(it.next());
            if (i != size - 1) {
                sb.append(',');
            }
            i++;
        }
        sb.append(']');
        return sb.toString();
    }

    public static <T> T listSelectFirstFrom(List<T> list, T[] tArr) {
        if (list == null) {
            return null;
        }
        for (T t : tArr) {
            if (list.contains(t)) {
                return t;
            }
        }
        return null;
    }

    private ListUtils() {
        throw new AssertionError();
    }
}
