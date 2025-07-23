package android.content;

import android.annotation.SystemApi;
import android.app.BroadcastOptions;
import android.app.IApplicationThread;
import android.app.IServiceConnection;
import android.app.compat.CompatChanges;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.UserHandle;
import android.util.Log;
import android.view.Display;
import android.view.DisplayAdjustments;
import android.view.autofill.AutofillManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.IntConsumer;

/* loaded from: classes.dex */
public class ContextWrapper extends Context {
    Context mBase;
    public List<ComponentCallbacks> mCallbacksRegisteredToSuper;
    private final Object mLock = new Object();

    public ContextWrapper(Context context) {
        this.mBase = context;
    }

    protected void attachBaseContext(Context context) {
        if (this.mBase != null) {
            throw new IllegalStateException("Base context already set");
        }
        this.mBase = context;
    }

    public Context getBaseContext() {
        return this.mBase;
    }

    @Override // android.content.Context
    public AssetManager getAssets() {
        return this.mBase.getAssets();
    }

    @Override // android.content.Context
    public Resources getResources() {
        return this.mBase.getResources();
    }

    @Override // android.content.Context
    public PackageManager getPackageManager() {
        return this.mBase.getPackageManager();
    }

    @Override // android.content.Context
    public ContentResolver getContentResolver() {
        return this.mBase.getContentResolver();
    }

    @Override // android.content.Context
    public Looper getMainLooper() {
        return this.mBase.getMainLooper();
    }

    @Override // android.content.Context
    public Executor getMainExecutor() {
        return this.mBase.getMainExecutor();
    }

    @Override // android.content.Context
    public Context getApplicationContext() {
        return this.mBase.getApplicationContext();
    }

    @Override // android.content.Context
    public void setTheme(int i) {
        this.mBase.setTheme(i);
    }

    @Override // android.content.Context
    public int getThemeResId() {
        return this.mBase.getThemeResId();
    }

    @Override // android.content.Context
    public Resources.Theme getTheme() {
        return this.mBase.getTheme();
    }

    @Override // android.content.Context
    public ClassLoader getClassLoader() {
        return this.mBase.getClassLoader();
    }

    @Override // android.content.Context
    public String getPackageName() {
        Context context = this.mBase;
        if (context == null) {
            return "";
        }
        return context.getPackageName();
    }

    @Override // android.content.Context
    public String getBasePackageName() {
        return this.mBase.getBasePackageName();
    }

    @Override // android.content.Context
    public String getOpPackageName() {
        return this.mBase.getOpPackageName();
    }

    @Override // android.content.Context
    public String getAttributionTag() {
        return this.mBase.getAttributionTag();
    }

    @Override // android.content.Context
    public ContextParams getParams() {
        return this.mBase.getParams();
    }

    @Override // android.content.Context
    public ApplicationInfo getApplicationInfo() {
        return this.mBase.getApplicationInfo();
    }

    @Override // android.content.Context
    public String getPackageResourcePath() {
        return this.mBase.getPackageResourcePath();
    }

    @Override // android.content.Context
    public String getPackageCodePath() {
        return this.mBase.getPackageCodePath();
    }

    @Override // android.content.Context
    public SharedPreferences getSharedPreferences(String str, int i) {
        return this.mBase.getSharedPreferences(str, i);
    }

    @Override // android.content.Context
    public SharedPreferences getSharedPreferences(File file, int i) {
        return this.mBase.getSharedPreferences(file, i);
    }

    @Override // android.content.Context
    public void reloadSharedPreferences() {
        this.mBase.reloadSharedPreferences();
    }

    @Override // android.content.Context
    public boolean moveSharedPreferencesFrom(Context context, String str) {
        return this.mBase.moveSharedPreferencesFrom(context, str);
    }

    @Override // android.content.Context
    public boolean deleteSharedPreferences(String str) {
        return this.mBase.deleteSharedPreferences(str);
    }

    @Override // android.content.Context
    public FileInputStream openFileInput(String str) throws FileNotFoundException {
        return this.mBase.openFileInput(str);
    }

    @Override // android.content.Context
    public FileOutputStream openFileOutput(String str, int i) throws FileNotFoundException {
        return this.mBase.openFileOutput(str, i);
    }

    @Override // android.content.Context
    public boolean deleteFile(String str) {
        return this.mBase.deleteFile(str);
    }

    @Override // android.content.Context
    public File getFileStreamPath(String str) {
        return this.mBase.getFileStreamPath(str);
    }

    @Override // android.content.Context
    public File getSharedPreferencesPath(String str) {
        return this.mBase.getSharedPreferencesPath(str);
    }

    @Override // android.content.Context
    public String[] fileList() {
        return this.mBase.fileList();
    }

    @Override // android.content.Context
    public File getDataDir() {
        return this.mBase.getDataDir();
    }

    @Override // android.content.Context
    public File getFilesDir() {
        return this.mBase.getFilesDir();
    }

    @Override // android.content.Context
    public File getCrateDir(String str) {
        return this.mBase.getCrateDir(str);
    }

    @Override // android.content.Context
    public File getNoBackupFilesDir() {
        return this.mBase.getNoBackupFilesDir();
    }

    @Override // android.content.Context
    public File getExternalFilesDir(String str) {
        return this.mBase.getExternalFilesDir(str);
    }

    @Override // android.content.Context
    public File[] getExternalFilesDirs(String str) {
        return this.mBase.getExternalFilesDirs(str);
    }

    @Override // android.content.Context
    public File getObbDir() {
        return this.mBase.getObbDir();
    }

    @Override // android.content.Context
    public File[] getObbDirs() {
        return this.mBase.getObbDirs();
    }

    @Override // android.content.Context
    public File getCacheDir() {
        return this.mBase.getCacheDir();
    }

    @Override // android.content.Context
    public File getCodeCacheDir() {
        return this.mBase.getCodeCacheDir();
    }

    @Override // android.content.Context
    public File getExternalCacheDir() {
        return this.mBase.getExternalCacheDir();
    }

    @Override // android.content.Context
    public File[] getExternalCacheDirs() {
        return this.mBase.getExternalCacheDirs();
    }

    @Override // android.content.Context
    public File[] getExternalMediaDirs() {
        return this.mBase.getExternalMediaDirs();
    }

    @Override // android.content.Context
    public File getDir(String str, int i) {
        return this.mBase.getDir(str, i);
    }

    @Override // android.content.Context
    public File getPreloadsFileCache() {
        return this.mBase.getPreloadsFileCache();
    }

    @Override // android.content.Context
    public SQLiteDatabase openOrCreateDatabase(String str, int i, SQLiteDatabase.CursorFactory cursorFactory) {
        return this.mBase.openOrCreateDatabase(str, i, cursorFactory);
    }

    @Override // android.content.Context
    public SQLiteDatabase openOrCreateDatabase(String str, int i, SQLiteDatabase.CursorFactory cursorFactory, DatabaseErrorHandler databaseErrorHandler) {
        return this.mBase.openOrCreateDatabase(str, i, cursorFactory, databaseErrorHandler);
    }

    @Override // android.content.Context
    public boolean moveDatabaseFrom(Context context, String str) {
        return this.mBase.moveDatabaseFrom(context, str);
    }

    @Override // android.content.Context
    public boolean deleteDatabase(String str) {
        return this.mBase.deleteDatabase(str);
    }

    @Override // android.content.Context
    public File getDatabasePath(String str) {
        return this.mBase.getDatabasePath(str);
    }

    @Override // android.content.Context
    public String[] databaseList() {
        return this.mBase.databaseList();
    }

    @Override // android.content.Context
    @Deprecated
    public Drawable getWallpaper() {
        return this.mBase.getWallpaper();
    }

    @Override // android.content.Context
    @Deprecated
    public Drawable peekWallpaper() {
        return this.mBase.peekWallpaper();
    }

    @Override // android.content.Context
    @Deprecated
    public int getWallpaperDesiredMinimumWidth() {
        return this.mBase.getWallpaperDesiredMinimumWidth();
    }

    @Override // android.content.Context
    @Deprecated
    public int getWallpaperDesiredMinimumHeight() {
        return this.mBase.getWallpaperDesiredMinimumHeight();
    }

    @Override // android.content.Context
    @Deprecated
    public void setWallpaper(Bitmap bitmap) throws IOException {
        this.mBase.setWallpaper(bitmap);
    }

    @Override // android.content.Context
    @Deprecated
    public void setWallpaper(InputStream inputStream) throws IOException {
        this.mBase.setWallpaper(inputStream);
    }

    @Override // android.content.Context
    @Deprecated
    public void clearWallpaper() throws IOException {
        this.mBase.clearWallpaper();
    }

    @Override // android.content.Context
    public void startActivity(Intent intent) {
        this.mBase.startActivity(intent);
    }

    @Override // android.content.Context
    public void startActivityAsUser(Intent intent, UserHandle userHandle) {
        this.mBase.startActivityAsUser(intent, userHandle);
    }

    @Override // android.content.Context
    public void startActivityForResult(String str, Intent intent, int i, Bundle bundle) {
        this.mBase.startActivityForResult(str, intent, i, bundle);
    }

    @Override // android.content.Context
    public boolean canStartActivityForResult() {
        return this.mBase.canStartActivityForResult();
    }

    @Override // android.content.Context
    public void startActivity(Intent intent, Bundle bundle) {
        this.mBase.startActivity(intent, bundle);
    }

    @Override // android.content.Context
    public void startActivityAsUser(Intent intent, Bundle bundle, UserHandle userHandle) {
        this.mBase.startActivityAsUser(intent, bundle, userHandle);
    }

    @Override // android.content.Context
    public void startActivities(Intent[] intentArr) {
        this.mBase.startActivities(intentArr);
    }

    @Override // android.content.Context
    public void startActivities(Intent[] intentArr, Bundle bundle) {
        this.mBase.startActivities(intentArr, bundle);
    }

    @Override // android.content.Context
    public int startActivitiesAsUser(Intent[] intentArr, Bundle bundle, UserHandle userHandle) {
        return this.mBase.startActivitiesAsUser(intentArr, bundle, userHandle);
    }

    @Override // android.content.Context
    public void startIntentSender(IntentSender intentSender, Intent intent, int i, int i2, int i3) throws IntentSender.SendIntentException {
        this.mBase.startIntentSender(intentSender, intent, i, i2, i3);
    }

    @Override // android.content.Context
    public void startIntentSender(IntentSender intentSender, Intent intent, int i, int i2, int i3, Bundle bundle) throws IntentSender.SendIntentException {
        this.mBase.startIntentSender(intentSender, intent, i, i2, i3, bundle);
    }

    @Override // android.content.Context
    public void sendBroadcast(Intent intent) {
        this.mBase.sendBroadcast(intent);
    }

    @Override // android.content.Context
    public void sendBroadcast(Intent intent, String str) {
        this.mBase.sendBroadcast(intent, str);
    }

    @Override // android.content.Context
    public void sendBroadcastMultiplePermissions(Intent intent, String[] strArr) {
        this.mBase.sendBroadcastMultiplePermissions(intent, strArr);
    }

    @Override // android.content.Context
    public void sendBroadcastMultiplePermissions(Intent intent, String[] strArr, String[] strArr2, String[] strArr3, BroadcastOptions broadcastOptions) {
        this.mBase.sendBroadcastMultiplePermissions(intent, strArr, strArr2, strArr3, broadcastOptions);
    }

    @Override // android.content.Context
    public void sendBroadcastMultiplePermissions(Intent intent, String[] strArr, Bundle bundle) {
        this.mBase.sendBroadcastMultiplePermissions(intent, strArr, bundle);
    }

    @Override // android.content.Context
    public void sendBroadcastMultiplePermissionsAsUser(Intent intent, String[] strArr, UserHandle userHandle) {
        this.mBase.sendBroadcastMultiplePermissionsAsUser(intent, strArr, userHandle);
    }

    @Override // android.content.Context
    public void sendBroadcastAsUserMultiplePermissions(Intent intent, UserHandle userHandle, String[] strArr) {
        this.mBase.sendBroadcastAsUserMultiplePermissions(intent, userHandle, strArr);
    }

    @Override // android.content.Context
    public void sendBroadcast(Intent intent, String str, Bundle bundle) {
        this.mBase.sendBroadcast(intent, str, bundle);
    }

    @Override // android.content.Context
    public void sendBroadcast(Intent intent, String str, int i) {
        this.mBase.sendBroadcast(intent, str, i);
    }

    @Override // android.content.Context
    public void sendOrderedBroadcast(Intent intent, String str) {
        this.mBase.sendOrderedBroadcast(intent, str);
    }

    @Override // android.content.Context
    public void sendOrderedBroadcast(Intent intent, String str, Bundle bundle) {
        this.mBase.sendOrderedBroadcast(intent, str, bundle);
    }

    @Override // android.content.Context
    public void sendOrderedBroadcast(Intent intent, String str, BroadcastReceiver broadcastReceiver, Handler handler, int i, String str2, Bundle bundle) {
        this.mBase.sendOrderedBroadcast(intent, str, broadcastReceiver, handler, i, str2, bundle);
    }

    @Override // android.content.Context
    public void sendOrderedBroadcast(Intent intent, String str, Bundle bundle, BroadcastReceiver broadcastReceiver, Handler handler, int i, String str2, Bundle bundle2) {
        this.mBase.sendOrderedBroadcast(intent, str, bundle, broadcastReceiver, handler, i, str2, bundle2);
    }

    @Override // android.content.Context
    public void sendOrderedBroadcast(Intent intent, String str, int i, BroadcastReceiver broadcastReceiver, Handler handler, int i2, String str2, Bundle bundle) {
        this.mBase.sendOrderedBroadcast(intent, str, i, broadcastReceiver, handler, i2, str2, bundle);
    }

    @Override // android.content.Context
    public void sendBroadcastAsUser(Intent intent, UserHandle userHandle) {
        this.mBase.sendBroadcastAsUser(intent, userHandle);
    }

    @Override // android.content.Context
    public void sendBroadcastAsUser(Intent intent, UserHandle userHandle, String str) {
        this.mBase.sendBroadcastAsUser(intent, userHandle, str);
    }

    @Override // android.content.Context
    public void sendBroadcastAsUser(Intent intent, UserHandle userHandle, String str, Bundle bundle) {
        this.mBase.sendBroadcastAsUser(intent, userHandle, str, bundle);
    }

    @Override // android.content.Context
    public void sendBroadcastAsUser(Intent intent, UserHandle userHandle, String str, int i) {
        this.mBase.sendBroadcastAsUser(intent, userHandle, str, i);
    }

    @Override // android.content.Context
    public void sendOrderedBroadcastAsUser(Intent intent, UserHandle userHandle, String str, BroadcastReceiver broadcastReceiver, Handler handler, int i, String str2, Bundle bundle) {
        this.mBase.sendOrderedBroadcastAsUser(intent, userHandle, str, broadcastReceiver, handler, i, str2, bundle);
    }

    @Override // android.content.Context
    public void sendOrderedBroadcastAsUser(Intent intent, UserHandle userHandle, String str, int i, BroadcastReceiver broadcastReceiver, Handler handler, int i2, String str2, Bundle bundle) {
        this.mBase.sendOrderedBroadcastAsUser(intent, userHandle, str, i, broadcastReceiver, handler, i2, str2, bundle);
    }

    @Override // android.content.Context
    public void sendOrderedBroadcastAsUser(Intent intent, UserHandle userHandle, String str, int i, Bundle bundle, BroadcastReceiver broadcastReceiver, Handler handler, int i2, String str2, Bundle bundle2) {
        this.mBase.sendOrderedBroadcastAsUser(intent, userHandle, str, i, bundle, broadcastReceiver, handler, i2, str2, bundle2);
    }

    @Override // android.content.Context
    public void sendOrderedBroadcastAsUserMultiplePermissions(Intent intent, UserHandle userHandle, String[] strArr, int i, Bundle bundle, BroadcastReceiver broadcastReceiver, Handler handler, int i2, String str, Bundle bundle2) {
        this.mBase.sendOrderedBroadcastAsUserMultiplePermissions(intent, userHandle, strArr, i, bundle, broadcastReceiver, handler, i2, str, bundle2);
    }

    @Override // android.content.Context
    public void sendOrderedBroadcast(Intent intent, String str, String str2, BroadcastReceiver broadcastReceiver, Handler handler, int i, String str3, Bundle bundle) {
        this.mBase.sendOrderedBroadcast(intent, str, str2, broadcastReceiver, handler, i, str3, bundle);
    }

    @Override // android.content.Context
    public void sendOrderedBroadcastMultiplePermissions(Intent intent, String[] strArr, String str, BroadcastReceiver broadcastReceiver, Handler handler, int i, String str2, Bundle bundle, Bundle bundle2) {
        this.mBase.sendOrderedBroadcastMultiplePermissions(intent, strArr, str, broadcastReceiver, handler, i, str2, bundle, bundle2);
    }

    @Override // android.content.Context
    public void sendOrderedBroadcast(Intent intent, int i, String str, String str2, BroadcastReceiver broadcastReceiver, Handler handler, String str3, Bundle bundle, Bundle bundle2) {
        this.mBase.sendOrderedBroadcast(intent, i, str, str2, broadcastReceiver, handler, str3, bundle, bundle2);
    }

    @Override // android.content.Context
    @Deprecated
    public void sendStickyBroadcast(Intent intent) {
        this.mBase.sendStickyBroadcast(intent);
    }

    @Override // android.content.Context
    @Deprecated
    public void sendStickyBroadcast(Intent intent, Bundle bundle) {
        this.mBase.sendStickyBroadcast(intent, bundle);
    }

    @Override // android.content.Context
    @Deprecated
    public void sendStickyOrderedBroadcast(Intent intent, BroadcastReceiver broadcastReceiver, Handler handler, int i, String str, Bundle bundle) {
        this.mBase.sendStickyOrderedBroadcast(intent, broadcastReceiver, handler, i, str, bundle);
    }

    @Override // android.content.Context
    @Deprecated
    public void removeStickyBroadcast(Intent intent) {
        this.mBase.removeStickyBroadcast(intent);
    }

    @Override // android.content.Context
    @Deprecated
    public void sendStickyBroadcastAsUser(Intent intent, UserHandle userHandle) {
        this.mBase.sendStickyBroadcastAsUser(intent, userHandle);
    }

    @Override // android.content.Context
    @Deprecated
    public void sendStickyBroadcastAsUser(Intent intent, UserHandle userHandle, Bundle bundle) {
        this.mBase.sendStickyBroadcastAsUser(intent, userHandle, bundle);
    }

    @Override // android.content.Context
    @Deprecated
    public void sendStickyOrderedBroadcastAsUser(Intent intent, UserHandle userHandle, BroadcastReceiver broadcastReceiver, Handler handler, int i, String str, Bundle bundle) {
        this.mBase.sendStickyOrderedBroadcastAsUser(intent, userHandle, broadcastReceiver, handler, i, str, bundle);
    }

    @Override // android.content.Context
    @Deprecated
    public void removeStickyBroadcastAsUser(Intent intent, UserHandle userHandle) {
        this.mBase.removeStickyBroadcastAsUser(intent, userHandle);
    }

    @Override // android.content.Context
    public Intent registerReceiver(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter) {
        return this.mBase.registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override // android.content.Context
    public Intent registerReceiver(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter, int i) {
        return this.mBase.registerReceiver(broadcastReceiver, intentFilter, i);
    }

    @Override // android.content.Context
    public Intent registerReceiver(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter, String str, Handler handler) {
        return this.mBase.registerReceiver(broadcastReceiver, intentFilter, str, handler);
    }

    @Override // android.content.Context
    public Intent registerReceiver(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter, String str, Handler handler, int i) {
        return this.mBase.registerReceiver(broadcastReceiver, intentFilter, str, handler, i);
    }

    @Override // android.content.Context
    public Intent registerReceiverForAllUsers(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter, String str, Handler handler) {
        return this.mBase.registerReceiverForAllUsers(broadcastReceiver, intentFilter, str, handler);
    }

    @Override // android.content.Context
    public Intent registerReceiverForAllUsers(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter, String str, Handler handler, int i) {
        return this.mBase.registerReceiverForAllUsers(broadcastReceiver, intentFilter, str, handler, i);
    }

    @Override // android.content.Context
    public Intent registerReceiverAsUser(BroadcastReceiver broadcastReceiver, UserHandle userHandle, IntentFilter intentFilter, String str, Handler handler) {
        return this.mBase.registerReceiverAsUser(broadcastReceiver, userHandle, intentFilter, str, handler);
    }

    @Override // android.content.Context
    public Intent registerReceiverAsUser(BroadcastReceiver broadcastReceiver, UserHandle userHandle, IntentFilter intentFilter, String str, Handler handler, int i) {
        return this.mBase.registerReceiverAsUser(broadcastReceiver, userHandle, intentFilter, str, handler, i);
    }

    @Override // android.content.Context
    public void unregisterReceiver(BroadcastReceiver broadcastReceiver) {
        this.mBase.unregisterReceiver(broadcastReceiver);
    }

    @Override // android.content.Context
    public List<IntentFilter> getRegisteredIntentFilters(BroadcastReceiver broadcastReceiver) {
        return this.mBase.getRegisteredIntentFilters(broadcastReceiver);
    }

    @Override // android.content.Context
    public ComponentName startService(Intent intent) {
        return this.mBase.startService(intent);
    }

    @Override // android.content.Context
    public ComponentName startForegroundService(Intent intent) {
        return this.mBase.startForegroundService(intent);
    }

    @Override // android.content.Context
    public boolean stopService(Intent intent) {
        return this.mBase.stopService(intent);
    }

    @Override // android.content.Context
    public ComponentName startServiceAsUser(Intent intent, UserHandle userHandle) {
        return this.mBase.startServiceAsUser(intent, userHandle);
    }

    @Override // android.content.Context
    public ComponentName startForegroundServiceAsUser(Intent intent, UserHandle userHandle) {
        return this.mBase.startForegroundServiceAsUser(intent, userHandle);
    }

    @Override // android.content.Context
    public boolean stopServiceAsUser(Intent intent, UserHandle userHandle) {
        return this.mBase.stopServiceAsUser(intent, userHandle);
    }

    @Override // android.content.Context
    public boolean bindService(Intent intent, ServiceConnection serviceConnection, int i) {
        return this.mBase.bindService(intent, serviceConnection, i);
    }

    @Override // android.content.Context
    public boolean bindService(Intent intent, ServiceConnection serviceConnection, Context.BindServiceFlags bindServiceFlags) {
        return this.mBase.bindService(intent, serviceConnection, bindServiceFlags);
    }

    @Override // android.content.Context
    public boolean bindService(Intent intent, int i, Executor executor, ServiceConnection serviceConnection) {
        return this.mBase.bindService(intent, i, executor, serviceConnection);
    }

    @Override // android.content.Context
    public boolean bindService(Intent intent, Context.BindServiceFlags bindServiceFlags, Executor executor, ServiceConnection serviceConnection) {
        return this.mBase.bindService(intent, bindServiceFlags, executor, serviceConnection);
    }

    @Override // android.content.Context
    public boolean bindIsolatedService(Intent intent, int i, String str, Executor executor, ServiceConnection serviceConnection) {
        return this.mBase.bindIsolatedService(intent, i, str, executor, serviceConnection);
    }

    @Override // android.content.Context
    public boolean bindServiceAsUser(Intent intent, ServiceConnection serviceConnection, int i, UserHandle userHandle) {
        return this.mBase.bindServiceAsUser(intent, serviceConnection, i, userHandle);
    }

    @Override // android.content.Context
    public boolean bindServiceAsUser(Intent intent, ServiceConnection serviceConnection, Context.BindServiceFlags bindServiceFlags, UserHandle userHandle) {
        return this.mBase.bindServiceAsUser(intent, serviceConnection, bindServiceFlags, userHandle);
    }

    @Override // android.content.Context
    public boolean bindServiceAsUser(Intent intent, ServiceConnection serviceConnection, int i, Handler handler, UserHandle userHandle) {
        return this.mBase.bindServiceAsUser(intent, serviceConnection, i, handler, userHandle);
    }

    @Override // android.content.Context
    public boolean bindServiceAsUser(Intent intent, ServiceConnection serviceConnection, Context.BindServiceFlags bindServiceFlags, Handler handler, UserHandle userHandle) {
        return this.mBase.bindServiceAsUser(intent, serviceConnection, bindServiceFlags, handler, userHandle);
    }

    @Override // android.content.Context
    public void updateServiceGroup(ServiceConnection serviceConnection, int i, int i2) {
        this.mBase.updateServiceGroup(serviceConnection, i, i2);
    }

    @Override // android.content.Context
    public void unbindService(ServiceConnection serviceConnection) {
        this.mBase.unbindService(serviceConnection);
    }

    @Override // android.content.Context
    public boolean startInstrumentation(ComponentName componentName, String str, Bundle bundle) {
        return this.mBase.startInstrumentation(componentName, str, bundle);
    }

    @Override // android.content.Context
    public Object getSystemService(String str) {
        return this.mBase.getSystemService(str);
    }

    @Override // android.content.Context
    public String getSystemServiceName(Class<?> cls) {
        return this.mBase.getSystemServiceName(cls);
    }

    @Override // android.content.Context
    public int checkPermission(String str, int i, int i2) {
        return this.mBase.checkPermission(str, i, i2);
    }

    @Override // android.content.Context
    public int checkPermission(String str, int i, int i2, IBinder iBinder) {
        return this.mBase.checkPermission(str, i, i2, iBinder);
    }

    @Override // android.content.Context
    public int checkCallingPermission(String str) {
        return this.mBase.checkCallingPermission(str);
    }

    @Override // android.content.Context
    public int checkCallingOrSelfPermission(String str) {
        return this.mBase.checkCallingOrSelfPermission(str);
    }

    @Override // android.content.Context
    public int checkSelfPermission(String str) {
        return this.mBase.checkSelfPermission(str);
    }

    @Override // android.content.Context
    public void enforcePermission(String str, int i, int i2, String str2) {
        this.mBase.enforcePermission(str, i, i2, str2);
    }

    @Override // android.content.Context
    public void enforceCallingPermission(String str, String str2) {
        this.mBase.enforceCallingPermission(str, str2);
    }

    @Override // android.content.Context
    public void enforceCallingOrSelfPermission(String str, String str2) {
        this.mBase.enforceCallingOrSelfPermission(str, str2);
    }

    @Override // android.content.Context
    public int getPermissionRequestState(String str) {
        return this.mBase.getPermissionRequestState(str);
    }

    @Override // android.content.Context
    public void grantUriPermission(String str, Uri uri, int i) {
        this.mBase.grantUriPermission(str, uri, i);
    }

    @Override // android.content.Context
    public void revokeUriPermission(Uri uri, int i) {
        this.mBase.revokeUriPermission(uri, i);
    }

    @Override // android.content.Context
    public void revokeUriPermission(String str, Uri uri, int i) {
        this.mBase.revokeUriPermission(str, uri, i);
    }

    @Override // android.content.Context
    public int checkUriPermission(Uri uri, int i, int i2, int i3) {
        return this.mBase.checkUriPermission(uri, i, i2, i3);
    }

    @Override // android.content.Context
    public int checkContentUriPermissionFull(Uri uri, int i, int i2, int i3) {
        return this.mBase.checkContentUriPermissionFull(uri, i, i2, i3);
    }

    @Override // android.content.Context
    public int[] checkUriPermissions(List<Uri> list, int i, int i2, int i3) {
        return this.mBase.checkUriPermissions(list, i, i2, i3);
    }

    @Override // android.content.Context
    public int checkUriPermission(Uri uri, int i, int i2, int i3, IBinder iBinder) {
        return this.mBase.checkUriPermission(uri, i, i2, i3, iBinder);
    }

    @Override // android.content.Context
    public int checkCallingUriPermission(Uri uri, int i) {
        return this.mBase.checkCallingUriPermission(uri, i);
    }

    @Override // android.content.Context
    public int[] checkCallingUriPermissions(List<Uri> list, int i) {
        return this.mBase.checkCallingUriPermissions(list, i);
    }

    @Override // android.content.Context
    public int checkCallingOrSelfUriPermission(Uri uri, int i) {
        return this.mBase.checkCallingOrSelfUriPermission(uri, i);
    }

    @Override // android.content.Context
    public int[] checkCallingOrSelfUriPermissions(List<Uri> list, int i) {
        return this.mBase.checkCallingOrSelfUriPermissions(list, i);
    }

    @Override // android.content.Context
    public int checkUriPermission(Uri uri, String str, String str2, int i, int i2, int i3) {
        return this.mBase.checkUriPermission(uri, str, str2, i, i2, i3);
    }

    @Override // android.content.Context
    public void enforceUriPermission(Uri uri, int i, int i2, int i3, String str) {
        this.mBase.enforceUriPermission(uri, i, i2, i3, str);
    }

    @Override // android.content.Context
    public void enforceCallingUriPermission(Uri uri, int i, String str) {
        this.mBase.enforceCallingUriPermission(uri, i, str);
    }

    @Override // android.content.Context
    public void enforceCallingOrSelfUriPermission(Uri uri, int i, String str) {
        this.mBase.enforceCallingOrSelfUriPermission(uri, i, str);
    }

    @Override // android.content.Context
    public void enforceUriPermission(Uri uri, String str, String str2, int i, int i2, int i3, String str3) {
        this.mBase.enforceUriPermission(uri, str, str2, i, i2, i3, str3);
    }

    @Override // android.content.Context
    public void revokeSelfPermissionsOnKill(Collection<String> collection) {
        this.mBase.revokeSelfPermissionsOnKill(collection);
    }

    @Override // android.content.Context
    public Context createPackageContext(String str, int i) throws PackageManager.NameNotFoundException {
        return this.mBase.createPackageContext(str, i);
    }

    @Override // android.content.Context
    public Context createPackageContextAsUser(String str, int i, UserHandle userHandle) throws PackageManager.NameNotFoundException {
        return this.mBase.createPackageContextAsUser(str, i, userHandle);
    }

    @Override // android.content.Context
    public Context createContextAsUser(UserHandle userHandle, int i) {
        return this.mBase.createContextAsUser(userHandle, i);
    }

    @Override // android.content.Context
    public Context createApplicationContext(ApplicationInfo applicationInfo, int i) throws PackageManager.NameNotFoundException {
        return this.mBase.createApplicationContext(applicationInfo, i);
    }

    @Override // android.content.Context
    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public Context createContextForSdkInSandbox(ApplicationInfo applicationInfo, int i) throws PackageManager.NameNotFoundException {
        return this.mBase.createContextForSdkInSandbox(applicationInfo, i);
    }

    @Override // android.content.Context
    public Context createContextForSplit(String str) throws PackageManager.NameNotFoundException {
        return this.mBase.createContextForSplit(str);
    }

    @Override // android.content.Context
    public int getUserId() {
        return this.mBase.getUserId();
    }

    @Override // android.content.Context
    public UserHandle getUser() {
        return this.mBase.getUser();
    }

    @Override // android.content.Context
    public Context createConfigurationContext(Configuration configuration) {
        return this.mBase.createConfigurationContext(configuration);
    }

    @Override // android.content.Context
    public Context createDisplayContext(Display display) {
        return this.mBase.createDisplayContext(display);
    }

    @Override // android.content.Context
    public Context createDeviceContext(int i) {
        return this.mBase.createDeviceContext(i);
    }

    @Override // android.content.Context
    public Context createWindowContext(int i, Bundle bundle) {
        return this.mBase.createWindowContext(i, bundle);
    }

    @Override // android.content.Context
    public Context createWindowContext(Display display, int i, Bundle bundle) {
        return this.mBase.createWindowContext(display, i, bundle);
    }

    @Override // android.content.Context
    public Context createContext(ContextParams contextParams) {
        return this.mBase.createContext(contextParams);
    }

    @Override // android.content.Context
    public Context createAttributionContext(String str) {
        return this.mBase.createAttributionContext(str);
    }

    @Override // android.content.Context
    public AttributionSource getAttributionSource() {
        return this.mBase.getAttributionSource();
    }

    @Override // android.content.Context
    public boolean isRestricted() {
        return this.mBase.isRestricted();
    }

    @Override // android.content.Context
    public DisplayAdjustments getDisplayAdjustments(int i) {
        return this.mBase.getDisplayAdjustments(i);
    }

    @Override // android.content.Context
    public Display getDisplay() {
        return this.mBase.getDisplay();
    }

    @Override // android.content.Context
    public Display getDisplayNoVerify() {
        return this.mBase.getDisplayNoVerify();
    }

    @Override // android.content.Context
    public int getDisplayId() {
        return this.mBase.getDisplayId();
    }

    @Override // android.content.Context
    public int getAssociatedDisplayId() {
        return this.mBase.getAssociatedDisplayId();
    }

    @Override // android.content.Context
    public void updateDisplay(int i) {
        this.mBase.updateDisplay(i);
    }

    @Override // android.content.Context
    public void updateDeviceId(int i) {
        this.mBase.updateDeviceId(i);
    }

    @Override // android.content.Context
    public int getDeviceId() {
        return this.mBase.getDeviceId();
    }

    @Override // android.content.Context
    public void registerDeviceIdChangeListener(Executor executor, IntConsumer intConsumer) {
        this.mBase.registerDeviceIdChangeListener(executor, intConsumer);
    }

    @Override // android.content.Context
    public void unregisterDeviceIdChangeListener(IntConsumer intConsumer) {
        this.mBase.unregisterDeviceIdChangeListener(intConsumer);
    }

    @Override // android.content.Context
    public Context createDeviceProtectedStorageContext() {
        return this.mBase.createDeviceProtectedStorageContext();
    }

    @Override // android.content.Context
    @SystemApi
    public Context createCredentialProtectedStorageContext() {
        return this.mBase.createCredentialProtectedStorageContext();
    }

    @Override // android.content.Context
    public Context createTokenContext(IBinder iBinder, Display display) {
        return this.mBase.createTokenContext(iBinder, display);
    }

    @Override // android.content.Context
    public boolean isDeviceProtectedStorage() {
        return this.mBase.isDeviceProtectedStorage();
    }

    @Override // android.content.Context
    @SystemApi
    public boolean isCredentialProtectedStorage() {
        return this.mBase.isCredentialProtectedStorage();
    }

    @Override // android.content.Context
    public boolean canLoadUnsafeResources() {
        return this.mBase.canLoadUnsafeResources();
    }

    @Override // android.content.Context
    public IBinder getActivityToken() {
        return this.mBase.getActivityToken();
    }

    @Override // android.content.Context
    public IBinder getWindowContextToken() {
        Context context = this.mBase;
        if (context != null) {
            return context.getWindowContextToken();
        }
        return null;
    }

    @Override // android.content.Context
    public IServiceConnection getServiceDispatcher(ServiceConnection serviceConnection, Handler handler, long j) {
        return this.mBase.getServiceDispatcher(serviceConnection, handler, j);
    }

    @Override // android.content.Context
    public IApplicationThread getIApplicationThread() {
        return this.mBase.getIApplicationThread();
    }

    @Override // android.content.Context
    public IBinder getProcessToken() {
        return this.mBase.getProcessToken();
    }

    @Override // android.content.Context
    public Handler getMainThreadHandler() {
        return this.mBase.getMainThreadHandler();
    }

    @Override // android.content.Context
    public int getNextAutofillId() {
        return this.mBase.getNextAutofillId();
    }

    @Override // android.content.Context
    public AutofillManager.AutofillClient getAutofillClient() {
        return this.mBase.getAutofillClient();
    }

    @Override // android.content.Context
    public void setAutofillClient(AutofillManager.AutofillClient autofillClient) {
        this.mBase.setAutofillClient(autofillClient);
    }

    @Override // android.content.Context
    public AutofillOptions getAutofillOptions() {
        Context context = this.mBase;
        if (context == null) {
            return null;
        }
        return context.getAutofillOptions();
    }

    @Override // android.content.Context
    public void setAutofillOptions(AutofillOptions autofillOptions) {
        Context context = this.mBase;
        if (context != null) {
            context.setAutofillOptions(autofillOptions);
        }
    }

    @Override // android.content.Context
    public ContentCaptureOptions getContentCaptureOptions() {
        Context context = this.mBase;
        if (context == null) {
            return null;
        }
        return context.getContentCaptureOptions();
    }

    @Override // android.content.Context
    public void setContentCaptureOptions(ContentCaptureOptions contentCaptureOptions) {
        Context context = this.mBase;
        if (context != null) {
            context.setContentCaptureOptions(contentCaptureOptions);
        }
    }

    @Override // android.content.Context
    public boolean isUiContext() {
        Context context = this.mBase;
        if (context == null) {
            return false;
        }
        return context.isUiContext();
    }

    @Override // android.content.Context
    public boolean isConfigurationContext() {
        Context context = this.mBase;
        if (context == null) {
            return false;
        }
        return context.isConfigurationContext();
    }

    @Override // android.content.Context
    public void registerComponentCallbacks(ComponentCallbacks componentCallbacks) {
        Context context = this.mBase;
        if (context != null) {
            context.registerComponentCallbacks(componentCallbacks);
            return;
        }
        if (!CompatChanges.isChangeEnabled(Context.OVERRIDABLE_COMPONENT_CALLBACKS)) {
            super.registerComponentCallbacks(componentCallbacks);
            synchronized (this.mLock) {
                if (this.mCallbacksRegisteredToSuper == null) {
                    this.mCallbacksRegisteredToSuper = new ArrayList();
                }
                this.mCallbacksRegisteredToSuper.add(componentCallbacks);
            }
            return;
        }
        throw new IllegalStateException("ComponentCallbacks must be registered after this ContextWrapper is attached to a base Context.");
    }

    @Override // android.content.Context
    public void unregisterComponentCallbacks(ComponentCallbacks componentCallbacks) {
        synchronized (this.mLock) {
            List<ComponentCallbacks> list = this.mCallbacksRegisteredToSuper;
            if (list != null && list.contains(componentCallbacks)) {
                super.unregisterComponentCallbacks(componentCallbacks);
                this.mCallbacksRegisteredToSuper.remove(componentCallbacks);
            } else {
                Context context = this.mBase;
                if (context != null) {
                    context.unregisterComponentCallbacks(componentCallbacks);
                } else if (CompatChanges.isChangeEnabled(Context.OVERRIDABLE_COMPONENT_CALLBACKS)) {
                    throw new IllegalStateException("ComponentCallbacks must be unregistered after this ContextWrapper is attached to a base Context.");
                }
            }
        }
    }

    @Override // android.content.Context
    public void closeSystemDialogs() {
        this.mBase.closeSystemDialogs();
    }

    @Override // android.content.Context
    public Intent semRegisterReceiverAsUser(BroadcastReceiver broadcastReceiver, UserHandle userHandle, IntentFilter intentFilter, String str, Handler handler) {
        if (getApplicationInfo().targetSdkVersion >= 34) {
            Log.w(getApplicationInfo().className, "This API will be deprecated instead use semRegisterReceiverAsUser(BroadcastReceiver, IntentFilter, String, Handler, int) with flag");
        }
        return this.mBase.semRegisterReceiverAsUser(broadcastReceiver, userHandle, intentFilter, str, handler);
    }

    @Override // android.content.Context
    public Intent semRegisterReceiverAsUser(BroadcastReceiver broadcastReceiver, UserHandle userHandle, IntentFilter intentFilter, String str, Handler handler, int i) {
        return this.mBase.semRegisterReceiverAsUser(broadcastReceiver, userHandle, intentFilter, str, handler, i);
    }

    @Override // android.content.Context
    public ComponentName semStartServiceAsUser(Intent intent, UserHandle userHandle) {
        return this.mBase.semStartServiceAsUser(intent, userHandle);
    }

    public ComponentName semStartForegroundServiceAsUser(Intent intent, UserHandle userHandle) {
        return this.mBase.startForegroundServiceAsUser(intent, userHandle);
    }

    @Override // android.content.Context
    public boolean isExternalDesktopContext() {
        Context context = this.mBase;
        if (context == null) {
            return false;
        }
        return context.isExternalDesktopContext();
    }
}
