package com.samsung.android.server.pm.user;

import android.content.pm.UserInfo;
import android.os.SystemProperties;
import android.util.Slog;
import com.samsung.android.core.pm.multiuser.MultiUserSupportsHelper;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public abstract class BmodeUtils {
    public static void enableBMode() {
        int configMaxMultiUsers = MultiUserSupportsHelper.getConfigMaxMultiUsers() + 1;
        SystemProperties.set("persist.sys.show_multiuserui", "true");
        SystemProperties.set("persist.sys.max_users", Integer.toString(configMaxMultiUsers));
        Slog.d("BmodeMigrationUtils", "Enabling multi user due to BMODE");
    }

    public static void disableBMode() {
        int configMaxMultiUsers = MultiUserSupportsHelper.getConfigMaxMultiUsers();
        SystemProperties.set("persist.sys.show_multiuserui", Boolean.toString(MultiUserSupportsHelper.getConfigStatusMultiUser()));
        SystemProperties.set("persist.sys.max_users", Integer.toString(configMaxMultiUsers));
        Slog.d("BmodeMigrationUtils", "Disabling multi user due to BMODE");
    }

    public static UserInfo getBmodeUser(List list) {
        if (list == null) {
            return null;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            UserInfo userInfo = (UserInfo) it.next();
            if (userInfo.isBMode()) {
                return userInfo;
            }
        }
        return null;
    }
}
