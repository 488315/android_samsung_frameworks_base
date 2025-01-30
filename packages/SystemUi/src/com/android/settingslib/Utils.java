package com.android.settingslib;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.pm.UserInfo;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.UserHandle;
import android.os.UserManager;
import android.telephony.ServiceState;
import com.android.launcher3.icons.BaseIconFactory;
import com.android.launcher3.icons.FastBitmapDrawable;
import com.android.launcher3.icons.IconFactory;
import com.android.systemui.R;
import java.util.function.Predicate;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class Utils {
    static final String INCOMPATIBLE_CHARGER_WARNING_DISABLED = "incompatible_charger_warning_disabled";
    static final String STORAGE_MANAGER_ENABLED_PROPERTY = "ro.storage_manager.enabled";
    public static final int[] WIFI_PIE = {R.drawable.sec_ic_wifi_signal_0, R.drawable.sec_ic_wifi_signal_1, R.drawable.sec_ic_wifi_signal_2, R.drawable.sec_ic_wifi_signal_3, R.drawable.sec_ic_wifi_signal_4};
    public static String sPermissionControllerPackageName;
    public static String sServicesSystemSharedLibPackageName;
    public static String sSharedSystemSharedLibPackageName;
    public static Signature[] sSystemSignature;

    public static FastBitmapDrawable getBadgedIcon(Context context, ApplicationInfo applicationInfo) {
        Drawable loadUnbadgedIcon = applicationInfo.loadUnbadgedIcon(context.getPackageManager());
        final UserHandle userHandleForUid = UserHandle.getUserHandleForUid(applicationInfo.uid);
        boolean anyMatch = ((UserManager) context.getSystemService(UserManager.class)).getProfiles(userHandleForUid.getIdentifier()).stream().anyMatch(new Predicate() { // from class: com.android.settingslib.Utils$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                UserInfo userInfo = (UserInfo) obj;
                return userInfo.isCloneProfile() && userInfo.id == userHandleForUid.getIdentifier();
            }
        });
        IconFactory obtain = IconFactory.obtain(context);
        try {
            BaseIconFactory.IconOptions iconOptions = new BaseIconFactory.IconOptions();
            iconOptions.mUserHandle = userHandleForUid;
            iconOptions.mIsCloneProfile = anyMatch;
            FastBitmapDrawable newIcon = obtain.createBadgedIconBitmap(loadUnbadgedIcon, iconOptions).newIcon(context);
            obtain.close();
            return newIcon;
        } catch (Throwable th) {
            try {
                obtain.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public static ColorStateList getColorAttr(int i, Context context) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(new int[]{i});
        try {
            return obtainStyledAttributes.getColorStateList(0);
        } finally {
            obtainStyledAttributes.recycle();
        }
    }

    public static int getColorAttrDefaultColor(int i, Context context, int i2) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(new int[]{i});
        int color = obtainStyledAttributes.getColor(0, i2);
        obtainStyledAttributes.recycle();
        return color;
    }

    public static int getColorStateListDefaultColor(int i, Context context) {
        return context.getResources().getColorStateList(i, context.getTheme()).getDefaultColor();
    }

    public static int getThemeAttr(int i, Context context) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(new int[]{i});
        int resourceId = obtainStyledAttributes.getResourceId(0, 0);
        obtainStyledAttributes.recycle();
        return resourceId;
    }

    public static boolean isInService(ServiceState serviceState) {
        if (serviceState == null) {
            return false;
        }
        int state = serviceState.getState();
        serviceState.getDataRegistrationState();
        if ((state == 1 || state == 2) && serviceState.getMobileDataRegState() == 0) {
            state = 0;
        }
        return (state == 3 || state == 1 || state == 2) ? false : true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0035, code lost:
    
        if (r0.equals(r1) == false) goto L23;
     */
    /* JADX WARN: Removed duplicated region for block: B:40:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean isSystemPackage(Resources resources, PackageManager packageManager, PackageInfo packageInfo) {
        Signature signature;
        PackageInfo packageInfo2;
        Signature[] signatureArr;
        boolean z;
        Signature signature2 = null;
        if (sSystemSignature == null) {
            try {
                packageInfo2 = packageManager.getPackageInfo("android", 64);
            } catch (PackageManager.NameNotFoundException unused) {
            }
            if (packageInfo2 != null && (signatureArr = packageInfo2.signatures) != null && signatureArr.length > 0) {
                signature = signatureArr[0];
                sSystemSignature = new Signature[]{signature};
            }
            signature = null;
            sSystemSignature = new Signature[]{signature};
        }
        Signature signature3 = sSystemSignature[0];
        if (signature3 != null) {
            Signature[] signatureArr2 = packageInfo.signatures;
            if (signatureArr2 != null && signatureArr2.length > 0) {
                signature2 = signatureArr2[0];
            }
        }
        String str = packageInfo.packageName;
        if (sPermissionControllerPackageName == null) {
            sPermissionControllerPackageName = packageManager.getPermissionControllerPackageName();
        }
        if (sServicesSystemSharedLibPackageName == null) {
            sServicesSystemSharedLibPackageName = packageManager.getServicesSystemSharedLibraryPackageName();
        }
        if (sSharedSystemSharedLibPackageName == null) {
            sSharedSystemSharedLibPackageName = packageManager.getSharedSystemSharedLibraryPackageName();
        }
        if (!str.equals(sPermissionControllerPackageName) && !str.equals(sServicesSystemSharedLibPackageName) && !str.equals(sSharedSystemSharedLibPackageName) && !str.equals("com.android.printspooler")) {
            String string = resources.getString(android.R.string.default_notification_channel_label);
            if (!(string != null && string.equals(str))) {
                z = false;
                if (!z) {
                    return false;
                }
                return true;
            }
        }
        z = true;
        if (!z) {
        }
        return true;
    }
}
