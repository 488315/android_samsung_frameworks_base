package com.android.server.pm;

import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.UserHandle;
import android.util.ArrayMap;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.component.ParsedComponent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public class PackageProperty {
  public ArrayMap mActivityProperties;
  public ArrayMap mApplicationProperties;
  public ArrayMap mProviderProperties;
  public ArrayMap mReceiverProperties;
  public ArrayMap mServiceProperties;

  public PackageManager.Property getProperty(String str, String str2, String str3) {
    if (str3 == null) {
      return getApplicationProperty(str, str2);
    }
    return getComponentProperty(str, str2, str3);
  }

  public List queryProperty(String str, int i, Predicate predicate) {
    ArrayMap arrayMap;
    ArrayMap arrayMap2;
    if (i == 5) {
      arrayMap = this.mApplicationProperties;
    } else if (i == 1) {
      arrayMap = this.mActivityProperties;
    } else if (i == 4) {
      arrayMap = this.mProviderProperties;
    } else if (i == 2) {
      arrayMap = this.mReceiverProperties;
    } else {
      arrayMap = i == 3 ? this.mServiceProperties : null;
    }
    if (arrayMap == null || (arrayMap2 = (ArrayMap) arrayMap.get(str)) == null) {
      return null;
    }
    Binder.getCallingUid();
    UserHandle.getCallingUserId();
    int size = arrayMap2.size();
    ArrayList arrayList = new ArrayList(size);
    for (int i2 = 0; i2 < size; i2++) {
      if (!predicate.test((String) arrayMap2.keyAt(i2))) {
        arrayList.addAll((Collection) arrayMap2.valueAt(i2));
      }
    }
    return arrayList;
  }

  public void addAllProperties(AndroidPackage androidPackage) {
    this.mApplicationProperties =
        addProperties(androidPackage.getProperties(), this.mApplicationProperties);
    this.mActivityProperties =
        addComponentProperties(androidPackage.getActivities(), this.mActivityProperties);
    this.mProviderProperties =
        addComponentProperties(androidPackage.getProviders(), this.mProviderProperties);
    this.mReceiverProperties =
        addComponentProperties(androidPackage.getReceivers(), this.mReceiverProperties);
    this.mServiceProperties =
        addComponentProperties(androidPackage.getServices(), this.mServiceProperties);
  }

  public void removeAllProperties(AndroidPackage androidPackage) {
    this.mApplicationProperties =
        removeProperties(androidPackage.getProperties(), this.mApplicationProperties);
    this.mActivityProperties =
        removeComponentProperties(androidPackage.getActivities(), this.mActivityProperties);
    this.mProviderProperties =
        removeComponentProperties(androidPackage.getProviders(), this.mProviderProperties);
    this.mReceiverProperties =
        removeComponentProperties(androidPackage.getReceivers(), this.mReceiverProperties);
    this.mServiceProperties =
        removeComponentProperties(androidPackage.getServices(), this.mServiceProperties);
  }

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

  public static PackageManager.Property getProperty(
      String str, String str2, String str3, ArrayMap arrayMap) {
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

  public final PackageManager.Property getComponentProperty(String str, String str2, String str3) {
    ArrayMap arrayMap;
    ArrayMap arrayMap2;
    ArrayMap arrayMap3;
    ArrayMap arrayMap4 = this.mActivityProperties;
    PackageManager.Property property =
        arrayMap4 != null ? getProperty(str, str2, str3, arrayMap4) : null;
    if (property == null && (arrayMap3 = this.mProviderProperties) != null) {
      property = getProperty(str, str2, str3, arrayMap3);
    }
    if (property == null && (arrayMap2 = this.mReceiverProperties) != null) {
      property = getProperty(str, str2, str3, arrayMap2);
    }
    return (property != null || (arrayMap = this.mServiceProperties) == null)
        ? property
        : getProperty(str, str2, str3, arrayMap);
  }

  public final PackageManager.Property getApplicationProperty(String str, String str2) {
    List list;
    ArrayMap arrayMap = this.mApplicationProperties;
    ArrayMap arrayMap2 = arrayMap != null ? (ArrayMap) arrayMap.get(str) : null;
    if (arrayMap2 == null || (list = (List) arrayMap2.get(str2)) == null) {
      return null;
    }
    return (PackageManager.Property) list.get(0);
  }
}
