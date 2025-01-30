package com.samsung.android.app;

import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.p002pm.ApplicationInfo;
import android.content.p002pm.PackageManager;
import android.content.p002pm.ResolveInfo;
import android.content.p002pm.ShortcutInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.p009os.Bundle;
import android.p009os.IBinder;
import android.p009os.ParcelFileDescriptor;
import android.p009os.RemoteException;
import android.p009os.UserHandle;
import android.p009os.UserManager;
import android.util.Log;
import com.android.internal.util.Preconditions;
import com.samsung.android.app.ISemExecuteManager;
import com.samsung.android.ims.options.SemCapabilities;
import com.samsung.android.sepunion.SemUnionManager;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

/* loaded from: classes5.dex */
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

    public SemExecutableInfo getExecutableInfo(String id) {
        if (getService() == null) {
            Log.m94d(TAG, "getExecutableInfo: can not get service impl ");
            return null;
        }
        try {
            return mService.getExecutableInfo(id);
        } catch (Exception ex) {
            Log.m96e(TAG, "getExecutableInfo() failed: " + ex);
            return null;
        }
    }

    public List<SemExecutableInfo> getExecutableInfos() {
        if (getService() == null) {
            Log.m94d(TAG, "getExecutableInfos: can not get service impl ");
            return null;
        }
        try {
            return mService.getExecutableInfos();
        } catch (Exception ex) {
            Log.m96e(TAG, "getExecutableInfo() failed: " + ex);
            return null;
        }
    }

    private ISemExecuteManager getService() {
        if (mService == null) {
            SemUnionManager um = (SemUnionManager) this.mContext.getSystemService(Context.SEP_UNION_SERVICE);
            IBinder b = um.getSemSystemService("execute");
            mService = ISemExecuteManager.Stub.asInterface(b);
            Log.m94d(TAG, "getService: retry to get service impl " + mService + b);
        }
        return mService;
    }

    public boolean hasShortcutHostPermission() {
        if (getService() == null) {
            Log.m94d(TAG, "hasShortcutHostPermission: can not get service impl ");
            return false;
        }
        try {
            return mService.hasShortcutHostPermission(this.mContext.getPackageName());
        } catch (RemoteException re) {
            throw re.rethrowFromSystemServer();
        }
    }

    public List<ShortcutInfo> getShortcuts(ShortcutQuery query, UserHandle user) {
        logErrorForInvalidProfileAccess(user);
        StringBuilder append = new StringBuilder().append("getShortcuts: ").append(mService).append(" ");
        Context context = this.mContext;
        Log.m94d(TAG, append.append(context != null ? context.getPackageName() : SemCapabilities.FEATURE_TAG_NULL).append(query).toString());
        if (getService() == null) {
            Log.m94d(TAG, "getShortcuts: can not get service impl ");
            return null;
        }
        try {
            String queryTargetPackage = getDefaultLauncherPackage();
            if (queryTargetPackage == null) {
                queryTargetPackage = this.mContext.getPackageName();
                Log.m94d(TAG, "getShortcuts: can not launcher name ");
            }
            return mService.getShortcuts(this.mContext.getPackageName(), queryTargetPackage, query.mChangedSince, query.mPackage, query.mShortcutIds, query.mActivity, query.mQueryFlags, user).getList();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<ShortcutInfo> getShortcuts(String queryTargetLauncherPackage, ShortcutQuery query, UserHandle user) {
        logErrorForInvalidProfileAccess(user);
        StringBuilder append = new StringBuilder().append("getShortcuts: ").append(mService).append(" ");
        Context context = this.mContext;
        Log.m94d(TAG, append.append(context != null ? context.getPackageName() : SemCapabilities.FEATURE_TAG_NULL).append(query).toString());
        if (getService() == null) {
            Log.m94d(TAG, "getShortcuts: can not get service impl ");
            return null;
        }
        try {
            return mService.getShortcuts(this.mContext.getPackageName(), queryTargetLauncherPackage, query.mChangedSince, query.mPackage, query.mShortcutIds, query.mActivity, query.mQueryFlags, user).getList();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private String getDefaultLauncherPackage() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        ResolveInfo resolveInfo = this.mContext.getPackageManager().resolveActivity(intent, 65536);
        if (resolveInfo != null) {
            return resolveInfo.activityInfo.packageName;
        }
        return null;
    }

    public Drawable getShortcutIconDrawable(ShortcutInfo shortcut, int density) {
        if (!shortcut.hasIconFile()) {
            if (shortcut.hasIconResource()) {
                return loadDrawableResourceFromPackage(shortcut.getPackage(), shortcut.getIconResourceId(), shortcut.getUserHandle(), density);
            }
            if (shortcut.getIcon() == null) {
                return null;
            }
            Icon icon = shortcut.getIcon();
            switch (icon.getType()) {
                case 1:
                case 5:
                    return icon.loadDrawable(this.mContext);
                case 2:
                    return loadDrawableResourceFromPackage(shortcut.getPackage(), icon.getResId(), shortcut.getUserHandle(), density);
                case 3:
                case 4:
                default:
                    return null;
            }
        }
        ParcelFileDescriptor pfd = getShortcutIconFd(shortcut);
        if (pfd == null) {
            return null;
        }
        try {
            Bitmap bmp = BitmapFactory.decodeFileDescriptor(pfd.getFileDescriptor());
            if (bmp == null) {
                try {
                    pfd.close();
                } catch (IOException e) {
                }
                return null;
            }
            BitmapDrawable dr = new BitmapDrawable(this.mContext.getResources(), bmp);
            if (shortcut.hasAdaptiveBitmap()) {
                return new AdaptiveIconDrawable((Drawable) null, dr);
            }
            try {
                pfd.close();
            } catch (IOException e2) {
            }
            return dr;
        } finally {
            try {
                pfd.close();
            } catch (IOException e3) {
            }
        }
    }

    public Drawable getShortcutBadgedIconDrawable(ShortcutInfo shortcut, int density) {
        Drawable originalIcon = getShortcutIconDrawable(shortcut, density);
        if (originalIcon == null) {
            return null;
        }
        return this.mContext.getPackageManager().getUserBadgedIcon(originalIcon, shortcut.getUserHandle());
    }

    public void startShortcut(String packageName, String shortcutId, Rect sourceBounds, Bundle startActivityOptions, UserHandle user) {
        logErrorForInvalidProfileAccess(user);
        startShortcut(packageName, shortcutId, sourceBounds, startActivityOptions, user.getIdentifier());
    }

    public void startShortcut(ShortcutInfo shortcut, Rect sourceBounds, Bundle startActivityOptions) {
        startShortcut(shortcut.getPackage(), shortcut.getId(), sourceBounds, startActivityOptions, shortcut.getUserId());
    }

    private void startShortcut(String packageName, String shortcutId, Rect sourceBounds, Bundle startActivityOptions, int userId) {
        String queryTargetPackage;
        if (getService() == null) {
            Log.m94d(TAG, "startShortcut: can not get service impl ");
            return;
        }
        String queryTargetPackage2 = getDefaultLauncherPackage();
        if (queryTargetPackage2 != null) {
            queryTargetPackage = queryTargetPackage2;
        } else {
            String queryTargetPackage3 = this.mContext.getPackageName();
            Log.m94d(TAG, "getShortcuts: can not launcher name ");
            queryTargetPackage = queryTargetPackage3;
        }
        try {
            boolean success = mService.startShortcut(this.mContext.getPackageName(), queryTargetPackage, packageName, shortcutId, sourceBounds, startActivityOptions, userId);
            if (!success) {
                throw new ActivityNotFoundException("Shortcut could not be started");
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private void logErrorForInvalidProfileAccess(UserHandle target) {
        if (UserHandle.myUserId() != target.getIdentifier() && this.mUserManager.isManagedProfile()) {
            Log.m102w(TAG, "Accessing other profiles/users from managed profile is no longer allowed.");
        }
    }

    private Drawable loadDrawableResourceFromPackage(String packageName, int resId, UserHandle user, int density) {
        if (resId == 0) {
            return null;
        }
        try {
            ApplicationInfo ai = getApplicationInfo(packageName, 0, user);
            Resources res = this.mContext.getPackageManager().getResourcesForApplication(ai);
            return res.getDrawableForDensity(resId, density);
        } catch (PackageManager.NameNotFoundException | Resources.NotFoundException e) {
            return null;
        }
    }

    public ParcelFileDescriptor getShortcutIconFd(ShortcutInfo shortcut) {
        if (getService() == null) {
            Log.m94d(TAG, "getShortcutIconFd: can not get service impl ");
            return null;
        }
        return getShortcutIconFd(shortcut.getPackage(), shortcut.getId(), shortcut.getUserId());
    }

    private ParcelFileDescriptor getShortcutIconFd(String packageName, String shortcutId, int userId) {
        String queryTargetPackage = getDefaultLauncherPackage();
        if (queryTargetPackage == null) {
            queryTargetPackage = this.mContext.getPackageName();
            Log.m94d(TAG, "getShortcuts: can not launcher name ");
        }
        try {
            return mService.getShortcutIconFd(this.mContext.getPackageName(), queryTargetPackage, packageName, shortcutId, userId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public ApplicationInfo getApplicationInfo(String packageName, int flags, UserHandle user) throws PackageManager.NameNotFoundException {
        Preconditions.checkNotNull(packageName, "packageName");
        Preconditions.checkNotNull(packageName, "user");
        logErrorForInvalidProfileAccess(user);
        String queryTargetPackage = getDefaultLauncherPackage();
        if (queryTargetPackage == null) {
            this.mContext.getPackageName();
            Log.m94d(TAG, "getShortcuts: can not launcher name ");
        }
        try {
            ApplicationInfo ai = mService.getApplicationInfo(this.mContext.getPackageName(), packageName, flags, user);
            if (ai == null) {
                throw new PackageManager.NameNotFoundException("Package " + packageName + " not found for user " + user.getIdentifier());
            }
            return ai;
        } catch (RemoteException re) {
            throw re.rethrowFromSystemServer();
        }
    }

    public void registerShortcutChangedCallback(PendingIntent pIntent, UserHandle user) {
        Preconditions.checkNotNull(pIntent, "pIntent");
        Preconditions.checkNotNull(user, "user");
        logErrorForInvalidProfileAccess(user);
        if (getService() == null) {
            Log.m94d(TAG, "registerChangedCallback: can not get service impl ");
            return;
        }
        try {
            mService.registerChangedCallback(this.mContext.getPackageName(), pIntent, user);
        } catch (RemoteException re) {
            throw re.rethrowFromSystemServer();
        }
    }

    public void unRegisterShortcutChangedCallback(PendingIntent pIntent, UserHandle user) {
        Preconditions.checkNotNull(pIntent, "pIntent");
        Preconditions.checkNotNull(user, "user");
        logErrorForInvalidProfileAccess(user);
        if (getService() == null) {
            Log.m94d(TAG, "unRegisterChangedCallback: can not get service impl ");
            return;
        }
        try {
            mService.unRegisterChangedCallback(this.mContext.getPackageName(), pIntent, user);
        } catch (RemoteException re) {
            throw re.rethrowFromSystemServer();
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

        public ShortcutQuery setChangedSince(long changedSince) {
            this.mChangedSince = changedSince;
            return this;
        }

        public ShortcutQuery setPackage(String packageName) {
            this.mPackage = packageName;
            return this;
        }

        public ShortcutQuery setShortcutIds(List<String> shortcutIds) {
            this.mShortcutIds = shortcutIds;
            return this;
        }

        public ShortcutQuery setActivity(ComponentName activity) {
            this.mActivity = activity;
            return this;
        }

        public ShortcutQuery setQueryFlags(int queryFlags) {
            this.mQueryFlags = queryFlags;
            return this;
        }
    }
}
