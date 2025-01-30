package android.net.shared;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Function;

/* loaded from: classes.dex */
public final class ParcelableUtil {
    public static Object[] toParcelableArray(Collection collection, Function function, Class cls) {
        Object[] objArr = (Object[]) Array.newInstance((Class<?>) cls, collection.size());
        Iterator it = collection.iterator();
        int i = 0;
        while (it.hasNext()) {
            objArr[i] = function.apply(it.next());
            i++;
        }
        return objArr;
    }

    public static ArrayList fromParcelableArray(Object[] objArr, Function function) {
        ArrayList arrayList = new ArrayList(objArr.length);
        for (Object obj : objArr) {
            arrayList.add(function.apply(obj));
        }
        return arrayList;
    }
}
