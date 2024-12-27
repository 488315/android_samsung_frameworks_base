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

public class MultiUserSupportsHelper {
    public static final boolean DEFAULT_ENABLE_STATUS;
    public static final int DEFAULT_MAX_USERS;
    public static final boolean IS_TABLET =
            SystemProperties.get("ro.build.characteristics", "")
                    .contains(BnRConstants.DEVICETYPE_TABLET);

    static {
        DEFAULT_MAX_USERS = IS_TABLET ? 8 : 1;
        DEFAULT_ENABLE_STATUS = IS_TABLET;
    }

    public static boolean supportsMultipleUsers() {
        if (isLduSkuBinary()) {
            return false;
        }
        boolean mumEnabled = getConfigStatusMultiUser();
        boolean config = SystemProperties.getBoolean("persist.sys.show_multiuserui", mumEnabled);
        return getMaxSupportedUsers() > 1
                && SystemProperties.getBoolean("fw.show_multiuserui", config);
    }

    public static int getMaxSupportedUsers() {
        if (Build.ID.startsWith("JVP") || isLduSkuBinary()) {
            return 1;
        }
        int mumMaxUsers = getConfigMaxMultiUsers();
        int config = SystemProperties.getInt("persist.sys.max_users", mumMaxUsers);
        return SystemProperties.getInt("fw.max_users", config);
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

    public static Bitmap getBmodeIconIfValidUser(List<UserInfo> users, int userId, Context ctx) {
        if (users == null) {
            return null;
        }
        boolean isBmodeInstalled = false;
        UserInfo curUser = null;
        for (UserInfo user : users) {
            if (user.isBMode()) {
                isBmodeInstalled = true;
            }
            if (user.id == userId) {
                curUser = user;
            }
        }
        if (!isBmodeInstalled || curUser == null || (!curUser.isPrimary() && !curUser.isBMode())) {
            return null;
        }
        Drawable icon = getBmodeUserIcon(ctx.getResources(), curUser.isPrimary());
        return UserIcons.convertToBitmap(icon);
    }

    private static Drawable getBmodeUserIcon(Resources resources, boolean isPrimary) {
        Drawable icon;
        int colorResId;
        if (isPrimary) {
            icon = resources.getDrawable(R.drawable.mum_bmode_1, null).mutate();
            colorResId = R.color.user_icon_bmode_1;
        } else {
            icon = resources.getDrawable(R.drawable.mum_bmode_2, null).mutate();
            colorResId = R.color.user_icon_bmode_2;
        }
        icon.setColorFilter(resources.getColor(colorResId), PorterDuff.Mode.SCREEN);
        icon.setBounds(0, 0, icon.getIntrinsicWidth(), icon.getIntrinsicHeight());
        return icon;
    }

    private MultiUserSupportsHelper() {}
}
