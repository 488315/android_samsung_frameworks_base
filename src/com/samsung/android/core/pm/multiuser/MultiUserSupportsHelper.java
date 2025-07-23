package com.samsung.android.core.pm.multiuser;

import android.content.Context;
import android.content.pm.UserInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.SystemProperties;
import com.android.internal.R;
import com.android.internal.util.UserIcons;
import com.samsung.android.core.pm.PmUtils;
import com.samsung.android.wallpaperbackup.BnRConstants;
import java.util.List;

/* loaded from: classes6.dex */
public class MultiUserSupportsHelper {
    public static final boolean DEFAULT_ENABLE_STATUS;
    public static final int DEFAULT_MAX_USERS;
    public static final boolean IS_TABLET;

    static {
        boolean contains = SystemProperties.get("ro.build.characteristics", "").contains(BnRConstants.DEVICETYPE_TABLET);
        IS_TABLET = contains;
        DEFAULT_MAX_USERS = contains ? 8 : 1;
        DEFAULT_ENABLE_STATUS = contains;
    }

    public static boolean supportsMultipleUsers() {
        if (isLduSkuBinary()) {
            return false;
        }
        return getMaxSupportedUsers() > 1 && SystemProperties.getBoolean("fw.show_multiuserui", SystemProperties.getBoolean("persist.sys.show_multiuserui", getConfigStatusMultiUser()));
    }

    public static int getMaxSupportedUsers() {
        if (Build.ID.startsWith("JVP") || isLduSkuBinary()) {
            return 1;
        }
        return SystemProperties.getInt("fw.max_users", SystemProperties.getInt("persist.sys.max_users", getConfigMaxMultiUsers()));
    }

    private static boolean isLduSkuBinary() {
        return PmUtils.isLduSkuBinary();
    }

    public static int getConfigMaxMultiUsers() {
        return DEFAULT_MAX_USERS;
    }

    public static boolean getConfigStatusMultiUser() {
        return DEFAULT_ENABLE_STATUS;
    }

    public static Bitmap getBmodeIconIfValidUser(List<UserInfo> list, int i, Context context) {
        if (list == null) {
            return null;
        }
        boolean z = false;
        UserInfo userInfo = null;
        for (UserInfo userInfo2 : list) {
            if (userInfo2.isBMode()) {
                z = true;
            }
            if (userInfo2.id == i) {
                userInfo = userInfo2;
            }
        }
        if (z && userInfo != null && (userInfo.isPrimary() || userInfo.isBMode())) {
            return UserIcons.convertToBitmap(getBmodeUserIcon(context.getResources(), userInfo.isPrimary()));
        }
        return null;
    }

    private static Drawable getBmodeUserIcon(Resources resources, boolean z) {
        Drawable mutate;
        int i;
        if (z) {
            mutate = resources.getDrawable(R.drawable.mum_bmode_1, null).mutate();
            i = R.color.user_icon_bmode_1;
        } else {
            mutate = resources.getDrawable(R.drawable.mum_bmode_2, null).mutate();
            i = R.color.user_icon_bmode_2;
        }
        mutate.setColorFilter(resources.getColor(i), PorterDuff.Mode.SCREEN);
        mutate.setBounds(0, 0, mutate.getIntrinsicWidth(), mutate.getIntrinsicHeight());
        return mutate;
    }

    private MultiUserSupportsHelper() {
    }
}
