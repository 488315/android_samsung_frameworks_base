package com.android.server.pm;

import android.content.pm.PackageManager;
import android.util.ArrayMap;
import com.android.internal.pm.pkg.component.ParsedComponent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class PackageProperty {
    public ArrayMap mActivityProperties;
    public ArrayMap mApplicationProperties;
    public ArrayMap mProviderProperties;
    public ArrayMap mReceiverProperties;
    public ArrayMap mServiceProperties;

    public static ArrayMap addComponentProperties(List list, ArrayMap arrayMap) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            Map properties = ((ParsedComponent) list.get(i)).getProperties();
            if (properties.size() != 0) {
                arrayMap = addProperties(properties, arrayMap);
            }
        }
        return arrayMap;
    }

    public static ArrayMap addProperties(Map map, ArrayMap arrayMap) {
        if (map.size() == 0) {
            return arrayMap;
        }
        if (arrayMap == null) {
            arrayMap = new ArrayMap(10);
        }
        for (PackageManager.Property property : map.values()) {
            String name = property.getName();
            String packageName = property.getPackageName();
            ArrayMap arrayMap2 = (ArrayMap) arrayMap.get(name);
            if (arrayMap2 == null) {
                arrayMap2 = new ArrayMap();
                arrayMap.put(name, arrayMap2);
            }
            ArrayList arrayList = (ArrayList) arrayMap2.get(packageName);
            if (arrayList == null) {
                arrayList = new ArrayList(map.size());
                arrayMap2.put(packageName, arrayList);
            }
            arrayList.add(property);
        }
        return arrayMap;
    }

    public static PackageManager.Property getProperty(String str, String str2, String str3, ArrayMap arrayMap) {
        List list;
        ArrayMap arrayMap2 = (ArrayMap) arrayMap.get(str);
        if (arrayMap2 == null || (list = (List) arrayMap2.get(str2)) == null) {
            return null;
        }
        for (int size = list.size() - 1; size >= 0; size--) {
            PackageManager.Property property = (PackageManager.Property) list.get(size);
            if (Objects.equals(str3, property.getClassName())) {
                return property;
            }
        }
        return null;
    }

    public static ArrayMap removeComponentProperties(List list, ArrayMap arrayMap) {
        int size = list.size();
        for (int i = 0; arrayMap != null && i < size; i++) {
            Map properties = ((ParsedComponent) list.get(i)).getProperties();
            if (properties.size() != 0) {
                arrayMap = removeProperties(properties, arrayMap);
            }
        }
        return arrayMap;
    }

    public static ArrayMap removeProperties(Map map, ArrayMap arrayMap) {
        ArrayList arrayList;
        if (arrayMap == null) {
            return null;
        }
        for (PackageManager.Property property : map.values()) {
            String name = property.getName();
            String packageName = property.getPackageName();
            ArrayMap arrayMap2 = (ArrayMap) arrayMap.get(name);
            if (arrayMap2 != null && (arrayList = (ArrayList) arrayMap2.get(packageName)) != null) {
                arrayList.remove(property);
                if (arrayList.size() == 0) {
                    arrayMap2.remove(packageName);
                }
                if (arrayMap2.size() == 0) {
                    arrayMap.remove(name);
                }
            }
        }
        if (arrayMap.size() == 0) {
            return null;
        }
        return arrayMap;
    }
}
