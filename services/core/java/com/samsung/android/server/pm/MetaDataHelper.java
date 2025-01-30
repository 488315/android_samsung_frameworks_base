package com.samsung.android.server.pm;

import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import com.android.server.pm.PackageSetting;
import com.android.server.pm.parsing.PackageInfoUtils;
import com.android.server.pm.parsing.pkg.AndroidPackageInternal;
import com.android.server.pm.pkg.component.ParsedComponent;

/* loaded from: classes2.dex */
public class MetaDataHelper {
    public Bundle getAppMetaData(PackageSetting packageSetting) {
        return getAppMetaBundle(packageSetting);
    }

    public Bundle getComponentMetaData(ParsedComponent parsedComponent) {
        return parsedComponent.getMetaData();
    }

    public static Bundle getAppMetaBundle(PackageSetting packageSetting) {
        AndroidPackageInternal pkg;
        ApplicationInfo generateApplicationInfo;
        if (packageSetting == null || (pkg = packageSetting.getPkg()) == null || (generateApplicationInfo = PackageInfoUtils.generateApplicationInfo(pkg, 128L, packageSetting.readUserState(-1), -1, packageSetting)) == null) {
            return null;
        }
        return generateApplicationInfo.metaData;
    }

    public static boolean isMetaDataInBundle(Bundle bundle, String str) {
        return bundle != null && bundle.getBoolean(str, false);
    }
}
