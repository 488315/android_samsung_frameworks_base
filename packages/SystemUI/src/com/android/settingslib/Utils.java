package com.android.settingslib;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.content.pm.UserInfo;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.UserManager;
import android.telephony.NetworkRegistrationInfo;
import android.telephony.ServiceState;
import android.util.Log;
import android.webkit.IWebViewUpdateService;
import android.webkit.WebViewFactory;
import android.webkit.WebViewProviderInfo;
import com.android.launcher3.icons.BaseIconFactory;
import com.android.launcher3.icons.FastBitmapDrawable;
import com.android.launcher3.icons.IconFactory;
import com.android.launcher3.util.UserIconInfo;
import com.android.systemui.R;
import java.util.List;

/* loaded from: classes.dex */
public class Utils {
    static final String STORAGE_MANAGER_ENABLED_PROPERTY = "ro.storage_manager.enabled";
    public static final int[] WIFI_PIE = {R.drawable.sec_ic_wifi_details_signal_0, R.drawable.sec_ic_wifi_details_signal_1, R.drawable.sec_ic_wifi_details_signal_2, R.drawable.sec_ic_wifi_details_signal_3, R.drawable.sec_ic_wifi_details_signal_4};
    public static String sDefaultWebViewPackageName;
    public static String sPackageInstallerPackageName;
    public static String sPermissionControllerPackageName;
    public static String sServicesSystemSharedLibPackageName;
    public static String sSharedSystemSharedLibPackageName;
    public static Signature[] sSystemSignature;

    /* JADX WARN: Removed duplicated region for block: B:14:0x0039  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static FastBitmapDrawable getBadgedIcon(Context context, ApplicationInfo applicationInfo) {
        int i;
        UserInfo userInfo;
        Drawable drawableLoadUnbadgedIcon = applicationInfo.loadUnbadgedIcon(context.getPackageManager());
        UserHandle userHandleForUid = UserHandle.getUserHandleForUid(applicationInfo.uid);
        try {
            userInfo = ((UserManager) context.getSystemService(UserManager.class)).getUserInfo(userHandleForUid.getIdentifier());
        } catch (Exception unused) {
        }
        if (userInfo == null) {
            i = 0;
        } else if (userInfo.isCloneProfile()) {
            i = 2;
        } else if (userInfo.isManagedProfile()) {
            i = 1;
        } else if (userInfo.isPrivateProfile()) {
            i = 3;
        }
        IconFactory iconFactoryObtain = IconFactory.obtain(context);
        try {
            BaseIconFactory.IconOptions iconOptions = new BaseIconFactory.IconOptions();
            iconOptions.mUserIconInfo = new UserIconInfo(userHandleForUid, i);
            FastBitmapDrawable fastBitmapDrawableNewIcon$1 = iconFactoryObtain.createBadgedIconBitmap(drawableLoadUnbadgedIcon, iconOptions).newIcon$1(0, context);
            iconFactoryObtain.close();
            return fastBitmapDrawableNewIcon$1;
        } catch (Throwable th) {
            try {
                iconFactoryObtain.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public static ColorStateList getColorAttr(int i, Context context) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(new int[]{i});
        try {
            return typedArrayObtainStyledAttributes.getColorStateList(0);
        } finally {
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    public static int getColorAttrDefaultColor(Context context, int i, int i2) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(new int[]{i});
        int color = typedArrayObtainStyledAttributes.getColor(0, i2);
        typedArrayObtainStyledAttributes.recycle();
        return color;
    }

    public static int getColorStateListDefaultColor(int i, Context context) {
        return context.getResources().getColorStateList(i, context.getTheme()).getDefaultColor();
    }

    public static int getThemeAttr(int i, Context context) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(new int[]{i});
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(0, 0);
        typedArrayObtainStyledAttributes.recycle();
        return resourceId;
    }

    public static boolean isInService(ServiceState serviceState) {
        if (serviceState == null) {
            return false;
        }
        int voiceRegState = serviceState.getVoiceRegState();
        if (voiceRegState == 1 || voiceRegState == 2) {
            NetworkRegistrationInfo networkRegistrationInfo = serviceState.getNetworkRegistrationInfo(2, 1);
            if (networkRegistrationInfo == null ? false : networkRegistrationInfo.isInService()) {
                voiceRegState = 0;
            }
        }
        return (voiceRegState == 3 || voiceRegState == 1 || voiceRegState == 2) ? false : true;
    }

    public static boolean isSystemPackage(Resources resources, PackageManager packageManager, PackageInfo packageInfo) throws Resources.NotFoundException, PackageManager.NameNotFoundException {
        PackageInfo packageInfo2;
        Signature[] signatureArr;
        WebViewProviderInfo defaultWebViewPackage = null;
        if (sSystemSignature == null) {
            Signature[] signatureArr2 = new Signature[1];
            try {
                packageInfo2 = packageManager.getPackageInfo("android", 64);
            } catch (PackageManager.NameNotFoundException unused) {
            }
            Signature signature = (packageInfo2 == null || (signatureArr = packageInfo2.signatures) == null || signatureArr.length <= 0) ? null : signatureArr[0];
            signatureArr2[0] = signature;
            sSystemSignature = signatureArr2;
        }
        Signature signature2 = sSystemSignature[0];
        if (signature2 != null) {
            Signature[] signatureArr3 = packageInfo.signatures;
            if (signature2.equals((signatureArr3 == null || signatureArr3.length <= 0) ? null : signatureArr3[0])) {
                return true;
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
        if (str.equals(sPermissionControllerPackageName) || str.equals(sServicesSystemSharedLibPackageName) || str.equals(sSharedSystemSharedLibPackageName) || str.equals("com.android.printspooler")) {
            return true;
        }
        String str2 = sDefaultWebViewPackageName;
        if (str2 == null) {
            try {
                IWebViewUpdateService updateService = WebViewFactory.getUpdateService();
                if (updateService != null) {
                    defaultWebViewPackage = updateService.getDefaultWebViewPackage();
                }
            } catch (RemoteException e) {
                Log.e("Utils", "RemoteException when trying to fetch default WebView package Name", e);
            }
            if (defaultWebViewPackage != null) {
                sDefaultWebViewPackageName = defaultWebViewPackage.packageName;
            }
            str2 = sDefaultWebViewPackageName;
        }
        if (str.equals(str2)) {
            return true;
        }
        String str3 = sPackageInstallerPackageName;
        if (str3 == null) {
            Intent intent = new Intent("android.intent.action.INSTALL_PACKAGE");
            intent.addCategory("android.intent.category.DEFAULT");
            intent.setDataAndType(Uri.parse("content://com.example/foo.apk"), "application/vnd.android.package-archive");
            List<ResolveInfo> listQueryIntentActivities = packageManager.queryIntentActivities(intent, 128);
            if (listQueryIntentActivities.size() == 1) {
                ResolveInfo resolveInfo = listQueryIntentActivities.get(0);
                if (resolveInfo.activityInfo.applicationInfo.isPrivilegedApp()) {
                    sPackageInstallerPackageName = resolveInfo.getComponentInfo().packageName;
                }
            }
            str3 = sPackageInstallerPackageName;
        }
        if (str.equals(str3)) {
            return true;
        }
        String string = resources.getString(android.R.string.duration_days_relative_future);
        return string != null && string.equals(str);
    }
}
