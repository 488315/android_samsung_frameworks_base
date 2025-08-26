package com.samsung.android.app;

import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ShortcutInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import com.android.internal.util.Preconditions;
import com.samsung.android.app.ISemExecuteManager;
import com.samsung.android.sepunion.SemUnionManager;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

/* loaded from: classes6.dex */
public class SemExecutableManager {
    public static final String EXTRA_EXECUTABLE_ICON = "com.samsung.android.execute.extra.ICON";
    public static final String EXTRA_EXECUTABLE_INTENT = "com.samsung.android.execute.extra.INTENT";
    public static final String EXTRA_EXECUTABLE_NAME = "com.samsung.android.execute.extra.NAME";
    public static final String EXTRA_EXECUTABLE_SMALL_ICON = "com.samsung.android.execute.extra.SMALLICON";
    public static final String EXTRA_SHORTCUT_PACKAGE_NAME = "com.samsung.android.shortcut.PACKAGE_NAME";
    public static final String EXTRA_SHORTCUT_USER_ID = "com.samsung.android.shortcut.USER_ID";
    private static final String TAG = "SemExecutableManager";
    private static ISemExecuteManager mService;
    private final Context mContext;
    private final UserManager mUserManager;

    public SemExecutableManager(Context context) {
        this.mContext = context;
        this.mUserManager = (UserManager) context.getSystemService(UserManager.class);
    }

    public SemExecutableInfo getExecutableInfo(String str) {
        if (getService() == null) {
            Log.d(TAG, "getExecutableInfo: can not get service impl ");
            return null;
        }
        try {
            return mService.getExecutableInfo(str);
        } catch (Exception e) {
            Log.e(TAG, "getExecutableInfo() failed: " + e);
            return null;
        }
    }

    public List<SemExecutableInfo> getExecutableInfos() {
        if (getService() == null) {
            Log.d(TAG, "getExecutableInfos: can not get service impl ");
            return null;
        }
        try {
            return mService.getExecutableInfos();
        } catch (Exception e) {
            Log.e(TAG, "getExecutableInfo() failed: " + e);
            return null;
        }
    }

    private ISemExecuteManager getService() {
        if (mService == null) {
            IBinder semSystemService = ((SemUnionManager) this.mContext.getSystemService(Context.SEP_UNION_SERVICE)).getSemSystemService("execute");
            mService = ISemExecuteManager.Stub.asInterface(semSystemService);
            Log.i(TAG, "getService: retry to get service impl " + mService + semSystemService);
        }
        return mService;
    }

    public boolean hasShortcutHostPermission() {
        if (getService() == null) {
            Log.d(TAG, "hasShortcutHostPermission: can not get service impl ");
            return false;
        }
        try {
            return mService.hasShortcutHostPermission(this.mContext.getPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<ShortcutInfo> getShortcuts(ShortcutQuery shortcutQuery, UserHandle userHandle) {
        logErrorForInvalidProfileAccess(userHandle);
        StringBuilder sb = new StringBuilder("getShortcuts: ");
        sb.append(mService);
        sb.append(" ");
        Context context = this.mContext;
        sb.append(context != null ? context.getPackageName() : PerfettoProtoLogImpl.NULL_STRING);
        sb.append(shortcutQuery);
        Log.d(TAG, sb.toString());
        if (getService() == null) {
            Log.d(TAG, "getShortcuts: can not get service impl ");
            return null;
        }
        try {
            String defaultLauncherPackage = getDefaultLauncherPackage();
            if (defaultLauncherPackage == null) {
                defaultLauncherPackage = this.mContext.getPackageName();
                Log.d(TAG, "getShortcuts: can not launcher name ");
            }
            return mService.getShortcuts(this.mContext.getPackageName(), defaultLauncherPackage, shortcutQuery.mChangedSince, shortcutQuery.mPackage, shortcutQuery.mShortcutIds, shortcutQuery.mActivity, shortcutQuery.mQueryFlags, userHandle).getList();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<ShortcutInfo> getShortcuts(String str, ShortcutQuery shortcutQuery, UserHandle userHandle) {
        logErrorForInvalidProfileAccess(userHandle);
        StringBuilder sb = new StringBuilder("getShortcuts: ");
        sb.append(mService);
        sb.append(" ");
        Context context = this.mContext;
        sb.append(context != null ? context.getPackageName() : PerfettoProtoLogImpl.NULL_STRING);
        sb.append(shortcutQuery);
        Log.d(TAG, sb.toString());
        if (getService() == null) {
            Log.d(TAG, "getShortcuts: can not get service impl ");
            return null;
        }
        try {
            return mService.getShortcuts(this.mContext.getPackageName(), str, shortcutQuery.mChangedSince, shortcutQuery.mPackage, shortcutQuery.mShortcutIds, shortcutQuery.mActivity, shortcutQuery.mQueryFlags, userHandle).getList();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private String getDefaultLauncherPackage() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        ResolveInfo resolveInfoResolveActivity = this.mContext.getPackageManager().resolveActivity(intent, 65536);
        if (resolveInfoResolveActivity != null) {
            return resolveInfoResolveActivity.activityInfo.packageName;
        }
        return null;
    }

    public Drawable getShortcutIconDrawable(ShortcutInfo shortcutInfo, int i) {
        if (shortcutInfo.hasIconFile()) {
            ParcelFileDescriptor shortcutIconFd = getShortcutIconFd(shortcutInfo);
            if (shortcutIconFd == null) {
                return null;
            }
            try {
                Bitmap bitmapDecodeFileDescriptor = BitmapFactory.decodeFileDescriptor(shortcutIconFd.getFileDescriptor());
                if (bitmapDecodeFileDescriptor == null) {
                    try {
                        shortcutIconFd.close();
                    } catch (IOException unused) {
                    }
                    return null;
                }
                BitmapDrawable bitmapDrawable = new BitmapDrawable(this.mContext.getResources(), bitmapDecodeFileDescriptor);
                if (shortcutInfo.hasAdaptiveBitmap()) {
                    return new AdaptiveIconDrawable((Drawable) null, bitmapDrawable);
                }
                try {
                    shortcutIconFd.close();
                } catch (IOException unused2) {
                }
                return bitmapDrawable;
            } finally {
                try {
                    shortcutIconFd.close();
                } catch (IOException unused3) {
                }
            }
        }
        if (shortcutInfo.hasIconResource()) {
            return loadDrawableResourceFromPackage(shortcutInfo.getPackage(), shortcutInfo.getIconResourceId(), shortcutInfo.getUserHandle(), i);
        }
        if (shortcutInfo.getIcon() == null) {
            return null;
        }
        Icon icon = shortcutInfo.getIcon();
        int type = icon.getType();
        if (type != 1) {
            if (type == 2) {
                return loadDrawableResourceFromPackage(shortcutInfo.getPackage(), icon.getResId(), shortcutInfo.getUserHandle(), i);
            }
            if (type != 5) {
                return null;
            }
        }
        return icon.loadDrawable(this.mContext);
    }

    public Drawable getShortcutBadgedIconDrawable(ShortcutInfo shortcutInfo, int i) {
        Drawable shortcutIconDrawable = getShortcutIconDrawable(shortcutInfo, i);
        if (shortcutIconDrawable == null) {
            return null;
        }
        return this.mContext.getPackageManager().getUserBadgedIcon(shortcutIconDrawable, shortcutInfo.getUserHandle());
    }

    public void startShortcut(String str, String str2, Rect rect, Bundle bundle, UserHandle userHandle) {
        logErrorForInvalidProfileAccess(userHandle);
        startShortcut(str, str2, rect, bundle, userHandle.getIdentifier());
    }

    public void startShortcut(ShortcutInfo shortcutInfo, Rect rect, Bundle bundle) {
        startShortcut(shortcutInfo.getPackage(), shortcutInfo.getId(), rect, bundle, shortcutInfo.getUserId());
    }

    private void startShortcut(String str, String str2, Rect rect, Bundle bundle, int i) {
        if (getService() == null) {
            Log.d(TAG, "startShortcut: can not get service impl ");
            return;
        }
        String defaultLauncherPackage = getDefaultLauncherPackage();
        if (defaultLauncherPackage == null) {
            defaultLauncherPackage = this.mContext.getPackageName();
            Log.d(TAG, "getShortcuts: can not launcher name ");
        }
        try {
            if (mService.startShortcut(this.mContext.getPackageName(), defaultLauncherPackage, str, str2, rect, bundle, i)) {
            } else {
                throw new ActivityNotFoundException("Shortcut could not be started");
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private void logErrorForInvalidProfileAccess(UserHandle userHandle) {
        if (UserHandle.myUserId() == userHandle.getIdentifier() || !this.mUserManager.isManagedProfile()) {
            return;
        }
        Log.w(TAG, "Accessing other profiles/users from managed profile is no longer allowed.");
    }

    private Drawable loadDrawableResourceFromPackage(String str, int i, UserHandle userHandle, int i2) {
        if (i == 0) {
            return null;
        }
        try {
            return this.mContext.getPackageManager().getResourcesForApplication(getApplicationInfo(str, 0, userHandle)).getDrawableForDensity(i, i2);
        } catch (PackageManager.NameNotFoundException | Resources.NotFoundException unused) {
            return null;
        }
    }

    public ParcelFileDescriptor getShortcutIconFd(ShortcutInfo shortcutInfo) {
        if (getService() == null) {
            Log.d(TAG, "getShortcutIconFd: can not get service impl ");
            return null;
        }
        return getShortcutIconFd(shortcutInfo.getPackage(), shortcutInfo.getId(), shortcutInfo.getUserId());
    }

    private ParcelFileDescriptor getShortcutIconFd(String str, String str2, int i) {
        String defaultLauncherPackage = getDefaultLauncherPackage();
        if (defaultLauncherPackage == null) {
            defaultLauncherPackage = this.mContext.getPackageName();
            Log.d(TAG, "getShortcuts: can not launcher name ");
        }
        try {
            return mService.getShortcutIconFd(this.mContext.getPackageName(), defaultLauncherPackage, str, str2, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public ApplicationInfo getApplicationInfo(String str, int i, UserHandle userHandle) throws PackageManager.NameNotFoundException {
        Preconditions.checkNotNull(str, "packageName");
        Preconditions.checkNotNull(str, "user");
        logErrorForInvalidProfileAccess(userHandle);
        try {
            ApplicationInfo applicationInfo = mService.getApplicationInfo(this.mContext.getPackageName(), str, i, userHandle);
            if (applicationInfo != null) {
                return applicationInfo;
            }
            throw new PackageManager.NameNotFoundException("Package " + str + " not found for user " + userHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void registerShortcutChangedCallback(PendingIntent pendingIntent, UserHandle userHandle) {
        Preconditions.checkNotNull(pendingIntent, "pIntent");
        Preconditions.checkNotNull(userHandle, "user");
        logErrorForInvalidProfileAccess(userHandle);
        if (getService() == null) {
            Log.d(TAG, "registerChangedCallback: can not get service impl ");
            return;
        }
        try {
            mService.registerChangedCallback(this.mContext.getPackageName(), pendingIntent, userHandle);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void unRegisterShortcutChangedCallback(PendingIntent pendingIntent, UserHandle userHandle) {
        Preconditions.checkNotNull(pendingIntent, "pIntent");
        Preconditions.checkNotNull(userHandle, "user");
        logErrorForInvalidProfileAccess(userHandle);
        if (getService() == null) {
            Log.d(TAG, "unRegisterChangedCallback: can not get service impl ");
            return;
        }
        try {
            mService.unRegisterChangedCallback(this.mContext.getPackageName(), pendingIntent, userHandle);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static class ShortcutQuery {

        @Deprecated
        public static final int FLAG_GET_ALL_KINDS = 11;

        @Deprecated
        public static final int FLAG_GET_DYNAMIC = 1;
        public static final int FLAG_GET_KEY_FIELDS_ONLY = 4;

        @Deprecated
        public static final int FLAG_GET_MANIFEST = 8;

        @Deprecated
        public static final int FLAG_GET_PINNED = 2;
        public static final int FLAG_MATCH_ALL_KINDS = 11;
        public static final int FLAG_MATCH_DYNAMIC = 1;
        public static final int FLAG_MATCH_MANIFEST = 8;
        public static final int FLAG_MATCH_PINNED = 2;
        ComponentName mActivity;
        long mChangedSince;
        String mPackage;
        int mQueryFlags;
        List<String> mShortcutIds;

        @Retention(RetentionPolicy.SOURCE)
        public @interface QueryFlags {
        }

        public ShortcutQuery setChangedSince(long j) {
            this.mChangedSince = j;
            return this;
        }

        public ShortcutQuery setPackage(String str) {
            this.mPackage = str;
            return this;
        }

        public ShortcutQuery setShortcutIds(List<String> list) {
            this.mShortcutIds = list;
            return this;
        }

        public ShortcutQuery setActivity(ComponentName componentName) {
            this.mActivity = componentName;
            return this;
        }

        public ShortcutQuery setQueryFlags(int i) {
            this.mQueryFlags = i;
            return this;
        }
    }
}
