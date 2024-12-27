package android.net.shared;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ParcelableUtil {
    public static ArrayList fromParcelableArray(Object[] objArr, Function function) {
        ArrayList arrayList = new ArrayList(objArr.length);
        for (Object obj : objArr) {
            arrayList.add(function.apply(obj));
        }
        return arrayList;
    }

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
}
