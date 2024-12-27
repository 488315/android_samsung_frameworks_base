package com.android.server.pm.dex;

import android.content.pm.SharedLibraryInfo;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.util.Slog;
import android.util.SparseArray;
import com.android.internal.os.ClassLoaderFactory;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class DexoptUtils {
    public static final String SHARED_LIBRARY_LOADER_TYPE = ClassLoaderFactory.getPathClassLoaderName();

    public static String encodeClassLoader(String str, String str2) {
        str.getClass();
        if (ClassLoaderFactory.isPathClassLoaderName(str2)) {
            str2 = "PCL";
        } else if (ClassLoaderFactory.isDelegateLastClassLoaderName(str2)) {
            str2 = "DLC";
        } else {
            Slog.wtf("DexoptUtils", "Unsupported classLoaderName: " + str2);
        }
        return str2 + "[" + str + "]";
    }

    public static String encodeClassLoaderChain(String str, String str2) {
        return str.isEmpty() ? str2 : str2.isEmpty() ? str : AnyMotionDetector$$ExternalSyntheticOutline0.m(str, ";", str2);
    }

    public static String encodeSharedLibraries(List list) {
        String str;
        Iterator it = list.iterator();
        String str2 = "{";
        boolean z = true;
        while (it.hasNext()) {
            SharedLibraryInfo sharedLibraryInfo = (SharedLibraryInfo) it.next();
            if (!z) {
                str2 = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str2, "#");
            }
            StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m(str2);
            List allCodePaths = sharedLibraryInfo.getAllCodePaths();
            String[] strArr = (String[]) allCodePaths.toArray(new String[allCodePaths.size()]);
            if (strArr == null || strArr.length == 0) {
                str = "";
            } else {
                StringBuilder sb = new StringBuilder();
                for (String str3 : strArr) {
                    if (sb.length() != 0) {
                        sb.append(":");
                    }
                    sb.append(str3);
                }
                str = sb.toString();
            }
            String encodeClassLoader = encodeClassLoader(str, SHARED_LIBRARY_LOADER_TYPE);
            if (sharedLibraryInfo.getDependencies() != null) {
                StringBuilder m2 = BootReceiver$$ExternalSyntheticOutline0.m(encodeClassLoader);
                m2.append(encodeSharedLibraries(sharedLibraryInfo.getDependencies()));
                encodeClassLoader = m2.toString();
            }
            m.append(encodeClassLoader);
            str2 = m.toString();
            z = false;
        }
        return ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str2, "}");
    }

    public static String getParentDependencies(int i, String[] strArr, SparseArray sparseArray, String[] strArr2, String str) {
        if (i == 0) {
            return str;
        }
        String str2 = strArr2[i];
        if (str2 != null) {
            return str2;
        }
        int i2 = ((int[]) sparseArray.get(i))[0];
        String parentDependencies = getParentDependencies(i2, strArr, sparseArray, strArr2, str);
        if (i2 != 0) {
            parentDependencies = encodeClassLoaderChain(strArr[i2 - 1], parentDependencies);
        }
        strArr2[i] = parentDependencies;
        return parentDependencies;
    }
}
