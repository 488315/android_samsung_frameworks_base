package com.samsung.android.core.p036pm.multiuser;

import android.content.Context;
import android.content.p002pm.UserInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.p009os.Build;
import android.p009os.SystemProperties;
import com.android.internal.C4337R;
import com.android.internal.util.UserIcons;
import com.samsung.android.core.p036pm.PmUtils;
import com.samsung.android.wallpaperbackup.BnRConstants;
import java.util.List;

/* loaded from: classes5.dex */
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
        boolean mumEnabled = getConfigStatusMultiUser();
        boolean config = SystemProperties.getBoolean("persist.sys.show_multiuserui", mumEnabled);
        return getMaxSupportedUsers() > 1 && SystemProperties.getBoolean("fw.show_multiuserui", config);
    }

    public static int getMaxSupportedUsers() {
        if (Build.f317ID.startsWith("JVP") || isLduSkuBinary()) {
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
            if (user.f54id == userId) {
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
            icon = resources.getDrawable(C4337R.drawable.mum_bmode_1, null).mutate();
            colorResId = C4337R.color.user_icon_bmode_1;
        } else {
            icon = resources.getDrawable(C4337R.drawable.mum_bmode_2, null).mutate();
            colorResId = C4337R.color.user_icon_bmode_2;
        }
        icon.setColorFilter(resources.getColor(colorResId), PorterDuff.Mode.SCREEN);
        icon.setBounds(0, 0, icon.getIntrinsicWidth(), icon.getIntrinsicHeight());
        return icon;
    }

    private MultiUserSupportsHelper() {
    }
}
