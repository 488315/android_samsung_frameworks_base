package android.appwidget;

import android.app.IServiceConnection;
import android.app.PendingIntent;
import android.app.usage.UsageStatsManager;
import android.appwidget.AppWidgetManager;
import android.appwidget.flags.Flags;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ParceledListSlice;
import android.graphics.Rect;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Pair;
import android.widget.RemoteViews;
import com.android.internal.appwidget.IAppWidgetService;
import com.android.internal.os.BackgroundThread;
import com.android.internal.util.FunctionalUtils;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;

/* loaded from: classes.dex */
public class AppWidgetManager {
    public static final String ACTION_APPWIDGET_BIND = "android.appwidget.action.APPWIDGET_BIND";
    public static final String ACTION_APPWIDGET_CONFIGURE = "android.appwidget.action.APPWIDGET_CONFIGURE";
    public static final String ACTION_APPWIDGET_DELETED = "android.appwidget.action.APPWIDGET_DELETED";
    public static final String ACTION_APPWIDGET_DISABLED = "android.appwidget.action.APPWIDGET_DISABLED";
    public static final String ACTION_APPWIDGET_ENABLED = "android.appwidget.action.APPWIDGET_ENABLED";
    public static final String ACTION_APPWIDGET_ENABLE_AND_UPDATE = "android.appwidget.action.APPWIDGET_ENABLE_AND_UPDATE";
    public static final String ACTION_APPWIDGET_HOST_RESTORED = "android.appwidget.action.APPWIDGET_HOST_RESTORED";
    public static final String ACTION_APPWIDGET_OPTIONS_CHANGED = "android.appwidget.action.APPWIDGET_UPDATE_OPTIONS";
    public static final String ACTION_APPWIDGET_PICK = "android.appwidget.action.APPWIDGET_PICK";
    public static final String ACTION_APPWIDGET_RESTORED = "android.appwidget.action.APPWIDGET_RESTORED";
    public static final String ACTION_APPWIDGET_UPDATE = "android.appwidget.action.APPWIDGET_UPDATE";
    public static final String ACTION_KEYGUARD_APPWIDGET_PICK = "android.appwidget.action.KEYGUARD_APPWIDGET_PICK";
    public static final String EVENT_CATEGORY_APPWIDGET = "android.appwidget";
    public static final String EVENT_TYPE_WIDGET_INTERACTION = "widget_interaction";
    public static final String EXTRA_APPWIDGET_ID = "appWidgetId";
    public static final String EXTRA_APPWIDGET_IDS = "appWidgetIds";
    public static final String EXTRA_APPWIDGET_OLD_IDS = "appWidgetOldIds";
    public static final String EXTRA_APPWIDGET_OPTIONS = "appWidgetOptions";
    public static final String EXTRA_APPWIDGET_PREVIEW = "appWidgetPreview";
    public static final String EXTRA_APPWIDGET_PROVIDER = "appWidgetProvider";
    public static final String EXTRA_APPWIDGET_PROVIDER_PROFILE = "appWidgetProviderProfile";
    public static final String EXTRA_CATEGORY_FILTER = "categoryFilter";
    public static final String EXTRA_CUSTOM_EXTRAS = "customExtras";
    public static final String EXTRA_CUSTOM_INFO = "customInfo";
    public static final String EXTRA_CUSTOM_SORT = "customSort";
    public static final String EXTRA_EVENT_CLICKED_VIEWS = "android.appwidget.extra.EVENT_CLICKED_VIEWS";
    public static final String EXTRA_EVENT_DURATION_MS = "android.appwidget.extra.EVENT_DURATION_MS";
    public static final String EXTRA_EVENT_POSITION_RECT = "android.appwidget.extra.EVENT_POSITION_RECT";
    public static final String EXTRA_EVENT_SCROLLED_VIEWS = "android.appwidget.extra.EVENT_SCROLLED_VIEWS";
    public static final String EXTRA_HOST_ID = "hostId";
    public static final int INVALID_APPWIDGET_ID = 0;
    public static final String META_DATA_APPWIDGET_PROVIDER = "android.appwidget.provider";
    public static final String OPTION_APPWIDGET_HOST_CATEGORY = "appWidgetCategory";
    public static final String OPTION_APPWIDGET_MAX_HEIGHT = "appWidgetMaxHeight";
    public static final String OPTION_APPWIDGET_MAX_WIDTH = "appWidgetMaxWidth";
    public static final String OPTION_APPWIDGET_MIN_HEIGHT = "appWidgetMinHeight";
    public static final String OPTION_APPWIDGET_MIN_WIDTH = "appWidgetMinWidth";
    public static final String OPTION_APPWIDGET_RESTORE_COMPLETED = "appWidgetRestoreCompleted";
    public static final String OPTION_APPWIDGET_SIZES = "appWidgetSizes";
    public static final String SEM_ACTION_APPWIDGET_CONFIGURE = "android.appwidget.action.SEM_APPWIDGET_CONFIGURE";
    public static final String SEM_ACTION_APPWIDGET_UNBIND = "com.samsung.android.appwidget.action.APPWIDGET_UNBIND";
    public static int SEM_APPWIDGET_LOCATION_LEFT = 0;
    public static int SEM_APPWIDGET_LOCATION_RIGHT = 1;
    public static String SEM_APPWIDGET_STYLE_COMPLICATION = "complication";
    public static final String SEM_EXTRA_APPWIDGET_PACKAGENAME = "appWidgetPackageName";
    public static final String SEM_META_DATA_CONFIGURE_ACTIVITY = "android.appwidget.provider.semConfigureActivity";
    public static final String SEM_OPTION_APPWIDGET_COLUMN_SPAN = "semAppWidgetColumnSpan";
    public static final String SEM_OPTION_APPWIDGET_LOCATION = "semAppWidgetLocation";
    public static final String SEM_OPTION_APPWIDGET_ROW_SPAN = "semAppWidgetRowSpan";
    public static final String SEM_OPTION_APPWIDGET_STYLE = "widgetStyle";
    private static final String TAG = "AppWidgetManager";
    private static Executor sUpdateExecutor;
    private final Context mContext;
    private final DisplayMetrics mDisplayMetrics;
    private boolean mHasPostedLegacyLists = false;
    private int mMaxBitmapMemory;
    private final String mPackageName;
    private final IAppWidgetService mService;
    private ServiceCollectionCache mServiceCollectionCache;

    public static AppWidgetManager getInstance(Context context) {
        return (AppWidgetManager) context.getSystemService(Context.APPWIDGET_SERVICE);
    }

    public AppWidgetManager(Context context, IAppWidgetService iAppWidgetService) {
        this.mMaxBitmapMemory = 0;
        this.mContext = context;
        this.mPackageName = context.getOpPackageName();
        this.mService = iAppWidgetService;
        this.mDisplayMetrics = context.getResources().getDisplayMetrics();
        this.mServiceCollectionCache = new ServiceCollectionCache(context, 5000L);
        if (iAppWidgetService == null) {
            return;
        }
        try {
            this.mMaxBitmapMemory = (int) (iAppWidgetService.getMaxBitmapMemory() * 0.9d);
        } catch (Exception e) {
            Log.e(TAG, "Error setting the maximum bitmap memory", e);
        }
        BackgroundThread.getExecutor().execute(new Runnable() { // from class: android.appwidget.AppWidgetManager$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                AppWidgetManager.this.lambda$new$3();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$3() {
        try {
            this.mService.notifyProviderInheritance((ComponentName[]) getInstalledProvidersForPackage(this.mPackageName, null).stream().filter(new Predicate() { // from class: android.appwidget.AppWidgetManager$$ExternalSyntheticLambda2
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return Objects.nonNull((AppWidgetProviderInfo) obj);
                }
            }).map(new Function() { // from class: android.appwidget.AppWidgetManager$$ExternalSyntheticLambda3
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    ComponentName componentName;
                    componentName = ((AppWidgetProviderInfo) obj).provider;
                    return componentName;
                }
            }).filter(new Predicate() { // from class: android.appwidget.AppWidgetManager$$ExternalSyntheticLambda4
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return AppWidgetManager.lambda$new$1((ComponentName) obj);
                }
            }).toArray(new IntFunction() { // from class: android.appwidget.AppWidgetManager$$ExternalSyntheticLambda5
                @Override // java.util.function.IntFunction
                public final Object apply(int i) {
                    return AppWidgetManager.lambda$new$2(i);
                }
            }));
        } catch (Exception e) {
            Log.e(TAG, "Notify service of inheritance info", e);
        }
    }

    static /* synthetic */ boolean lambda$new$1(ComponentName componentName) {
        try {
            return AppWidgetProvider.class.isAssignableFrom(Class.forName(componentName.getClassName()));
        } catch (Exception unused) {
            return false;
        }
    }

    static /* synthetic */ ComponentName[] lambda$new$2(int i) {
        return new ComponentName[i];
    }

    private void tryAdapterConversion(final FunctionalUtils.RemoteExceptionIgnoringConsumer<RemoteViews> remoteExceptionIgnoringConsumer, RemoteViews remoteViews, final String str) {
        if (remoteViews == null) {
            Log.w(TAG, "Original RemoteViews is null");
        }
        if (remoteViews != null && Flags.remoteAdapterConversion()) {
            boolean z = this.mHasPostedLegacyLists || (remoteViews != null && remoteViews.hasLegacyLists());
            this.mHasPostedLegacyLists = z;
            if (z) {
                final RemoteViews remoteViews2 = new RemoteViews(remoteViews);
                Runnable runnable = new Runnable() { // from class: android.appwidget.AppWidgetManager$$ExternalSyntheticLambda8
                    @Override // java.lang.Runnable
                    public final void run() {
                        AppWidgetManager.this.lambda$tryAdapterConversion$4(remoteViews2, remoteExceptionIgnoringConsumer, str);
                    }
                };
                if (Looper.getMainLooper() == Looper.myLooper()) {
                    createUpdateExecutorIfNull().execute(runnable);
                    return;
                } else {
                    runnable.run();
                    return;
                }
            }
        }
        try {
            remoteExceptionIgnoringConsumer.acceptOrThrow(remoteViews);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$tryAdapterConversion$4(RemoteViews remoteViews, FunctionalUtils.RemoteExceptionIgnoringConsumer remoteExceptionIgnoringConsumer, String str) {
        try {
            remoteViews.collectAllIntents(this.mMaxBitmapMemory, this.mServiceCollectionCache).get();
            remoteExceptionIgnoringConsumer.acceptOrThrow(remoteViews);
        } catch (Exception e) {
            Log.e(TAG, str, e);
        }
    }

    public void updateAppWidget(final int[] iArr, RemoteViews remoteViews) {
        if (this.mService == null) {
            return;
        }
        Log.i(TAG, "updateAppWidget() appWidgetIds = " + Arrays.toString(iArr));
        tryAdapterConversion(new FunctionalUtils.RemoteExceptionIgnoringConsumer() { // from class: android.appwidget.AppWidgetManager$$ExternalSyntheticLambda11
            @Override // com.android.internal.util.FunctionalUtils.RemoteExceptionIgnoringConsumer
            public final void acceptOrThrow(Object obj) {
                AppWidgetManager.this.lambda$updateAppWidget$5(iArr, (RemoteViews) obj);
            }
        }, remoteViews, "Error updating app widget views in background");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateAppWidget$5(int[] iArr, RemoteViews remoteViews) throws RemoteException {
        this.mService.updateAppWidgetIds(this.mPackageName, iArr, remoteViews);
    }

    public void semSetSkipPackageChanged(String str) {
        Context context;
        if (this.mService == null || (context = this.mContext) == null || !str.equals(context.getPackageName())) {
            return;
        }
        try {
            this.mService.semSetSkipPackageChanged(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void semChangeHostIds(int[] iArr, int i) {
        Log.i(TAG, "semChangeHostIds() appWidgetIds = " + Arrays.toString(iArr) + " hostId = " + i);
        IAppWidgetService iAppWidgetService = this.mService;
        if (iAppWidgetService == null) {
            return;
        }
        try {
            iAppWidgetService.changeHostIds(this.mPackageName, iArr, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void updateAppWidgetOptions(int i, Bundle bundle) {
        if (this.mService == null) {
            return;
        }
        try {
            Log.d(TAG, "updateAppWidgetOptions() appWidgetId = " + i + ", options = " + bundle);
            this.mService.updateAppWidgetOptions(this.mPackageName, i, bundle);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Bundle getAppWidgetOptions(int i) {
        IAppWidgetService iAppWidgetService = this.mService;
        if (iAppWidgetService == null) {
            return Bundle.EMPTY;
        }
        try {
            return iAppWidgetService.getAppWidgetOptions(this.mPackageName, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void updateAppWidget(int i, RemoteViews remoteViews) {
        if (this.mService == null) {
            return;
        }
        updateAppWidget(new int[]{i}, remoteViews);
    }

    public void partiallyUpdateAppWidget(final int[] iArr, RemoteViews remoteViews) {
        if (this.mService == null) {
            return;
        }
        Log.i(TAG, "partiallyUpdateAppWidget() appWidgetIds = " + Arrays.toString(iArr));
        tryAdapterConversion(new FunctionalUtils.RemoteExceptionIgnoringConsumer() { // from class: android.appwidget.AppWidgetManager$$ExternalSyntheticLambda10
            @Override // com.android.internal.util.FunctionalUtils.RemoteExceptionIgnoringConsumer
            public final void acceptOrThrow(Object obj) {
                AppWidgetManager.this.lambda$partiallyUpdateAppWidget$6(iArr, (RemoteViews) obj);
            }
        }, remoteViews, "Error partially updating app widget views in background");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$partiallyUpdateAppWidget$6(int[] iArr, RemoteViews remoteViews) throws RemoteException {
        this.mService.partiallyUpdateAppWidgetIds(this.mPackageName, iArr, remoteViews);
    }

    public void partiallyUpdateAppWidget(int i, RemoteViews remoteViews) {
        if (this.mService == null) {
            return;
        }
        partiallyUpdateAppWidget(new int[]{i}, remoteViews);
    }

    public void updateAppWidget(final ComponentName componentName, RemoteViews remoteViews) {
        if (this.mService == null) {
            return;
        }
        tryAdapterConversion(new FunctionalUtils.RemoteExceptionIgnoringConsumer() { // from class: android.appwidget.AppWidgetManager$$ExternalSyntheticLambda6
            @Override // com.android.internal.util.FunctionalUtils.RemoteExceptionIgnoringConsumer
            public final void acceptOrThrow(Object obj) {
                AppWidgetManager.this.lambda$updateAppWidget$7(componentName, (RemoteViews) obj);
            }
        }, remoteViews, "Error updating app widget view using provider in background");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateAppWidget$7(ComponentName componentName, RemoteViews remoteViews) throws RemoteException {
        this.mService.updateAppWidgetProvider(componentName, remoteViews);
    }

    public void updateAppWidgetProviderInfo(ComponentName componentName, String str) {
        IAppWidgetService iAppWidgetService = this.mService;
        if (iAppWidgetService == null) {
            return;
        }
        try {
            iAppWidgetService.updateAppWidgetProviderInfo(componentName, str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public void notifyAppWidgetViewDataChanged(final int[] iArr, final int i) {
        if (this.mService == null) {
            return;
        }
        if (Flags.remoteAdapterConversion()) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                this.mHasPostedLegacyLists = true;
                createUpdateExecutorIfNull().execute(new Runnable() { // from class: android.appwidget.AppWidgetManager$$ExternalSyntheticLambda9
                    @Override // java.lang.Runnable
                    public final void run() {
                        AppWidgetManager.this.lambda$notifyAppWidgetViewDataChanged$8(iArr, i);
                    }
                });
                return;
            } else {
                lambda$notifyAppWidgetViewDataChanged$8(iArr, i);
                return;
            }
        }
        try {
            Log.i(TAG, "notifyAppWidgetViewDataChanged() appWidgetIds = " + Arrays.toString(iArr) + ", viewId = " + i);
            this.mService.notifyAppWidgetViewDataChanged(this.mPackageName, iArr, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: notifyCollectionWidgetChange, reason: merged with bridge method [inline-methods] */
    public void lambda$notifyAppWidgetViewDataChanged$8(int[] iArr, final int i) {
        try {
            ArrayList arrayList = new ArrayList();
            for (final int i2 : iArr) {
                arrayList.add(CompletableFuture.runAsync(new Runnable() { // from class: android.appwidget.AppWidgetManager$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        AppWidgetManager.this.lambda$notifyCollectionWidgetChange$9(i2, i);
                    }
                }));
            }
            CompletableFuture.allOf((CompletableFuture[]) arrayList.toArray(new IntFunction() { // from class: android.appwidget.AppWidgetManager$$ExternalSyntheticLambda1
                @Override // java.util.function.IntFunction
                public final Object apply(int i3) {
                    return AppWidgetManager.lambda$notifyCollectionWidgetChange$10(i3);
                }
            })).join();
        } catch (Exception e) {
            Log.e(TAG, "Error notifying changes for all widgets", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyCollectionWidgetChange$9(int i, int i2) {
        try {
            RemoteViews appWidgetViews = this.mService.getAppWidgetViews(this.mPackageName, i);
            if (appWidgetViews != null && appWidgetViews.replaceRemoteCollections(i2)) {
                updateAppWidget(i, appWidgetViews);
                return;
            }
            if (appWidgetViews == null) {
                Log.e(TAG, "Error notifying changes in RemoteViews because of its null pkg:" + this.mPackageName + " widgetId:" + i);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error notifying changes in RemoteViews", e);
        }
    }

    static /* synthetic */ CompletableFuture[] lambda$notifyCollectionWidgetChange$10(int i) {
        return new CompletableFuture[i];
    }

    @Deprecated
    public void notifyAppWidgetViewDataChanged(int i, int i2) {
        if (this.mService == null) {
            return;
        }
        notifyAppWidgetViewDataChanged(new int[]{i}, i2);
    }

    public List<AppWidgetProviderInfo> getInstalledProvidersForProfile(UserHandle userHandle) {
        if (this.mService == null) {
            return Collections.EMPTY_LIST;
        }
        return getInstalledProvidersForProfile(1, userHandle, null);
    }

    public List<AppWidgetProviderInfo> getInstalledProvidersForProfile(int i, UserHandle userHandle) {
        if (this.mService == null) {
            return Collections.EMPTY_LIST;
        }
        return getInstalledProvidersForProfile(i, userHandle, null);
    }

    public List<AppWidgetProviderInfo> getInstalledProvidersForPackage(String str, UserHandle userHandle) {
        if (str == null) {
            throw new NullPointerException("A non-null package must be passed to this method. If you want all widgets regardless of package, see getInstalledProvidersForProfile(UserHandle)");
        }
        if (this.mService == null) {
            return Collections.EMPTY_LIST;
        }
        return getInstalledProvidersForProfile(1, userHandle, str);
    }

    public List<AppWidgetProviderInfo> getInstalledProviders() {
        if (this.mService == null) {
            return Collections.EMPTY_LIST;
        }
        return getInstalledProvidersForProfile(1, null, null);
    }

    public List<AppWidgetProviderInfo> getInstalledProviders(int i) {
        if (this.mService == null) {
            return Collections.EMPTY_LIST;
        }
        return getInstalledProvidersForProfile(i, null, null);
    }

    public List<AppWidgetProviderInfo> getInstalledProvidersForProfile(int i, UserHandle userHandle, String str) {
        if (this.mService == null) {
            return Collections.EMPTY_LIST;
        }
        if (userHandle == null) {
            userHandle = this.mContext.getUser();
        }
        try {
            ParceledListSlice installedProvidersForProfile = this.mService.getInstalledProvidersForProfile(i, userHandle.getIdentifier(), str);
            if (installedProvidersForProfile == null) {
                return Collections.EMPTY_LIST;
            }
            Iterator it = installedProvidersForProfile.getList().iterator();
            while (it.hasNext()) {
                ((AppWidgetProviderInfo) it.next()).updateDimensions(this.mDisplayMetrics);
            }
            return installedProvidersForProfile.getList();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public AppWidgetProviderInfo getAppWidgetInfo(int i) {
        IAppWidgetService iAppWidgetService = this.mService;
        if (iAppWidgetService == null) {
            Log.e(TAG, "Service wasn't initialized, appWidgetId=" + i);
            return null;
        }
        try {
            AppWidgetProviderInfo appWidgetInfo = iAppWidgetService.getAppWidgetInfo(this.mPackageName, i);
            if (appWidgetInfo != null) {
                appWidgetInfo.updateDimensions(this.mDisplayMetrics);
                return appWidgetInfo;
            }
            Log.e(TAG, "App widget provider info is null. PackageName=" + this.mPackageName + " appWidgetId-" + i);
            return appWidgetInfo;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void bindAppWidgetId(int i, ComponentName componentName) {
        if (this.mService == null) {
            return;
        }
        bindAppWidgetId(i, componentName, null);
    }

    public void bindAppWidgetId(int i, ComponentName componentName, Bundle bundle) {
        if (this.mService == null) {
            return;
        }
        bindAppWidgetIdIfAllowed(i, this.mContext.getUser(), componentName, bundle);
    }

    public boolean bindAppWidgetIdIfAllowed(int i, ComponentName componentName) {
        if (this.mService == null) {
            return false;
        }
        return bindAppWidgetIdIfAllowed(i, this.mContext.getUserId(), componentName, (Bundle) null);
    }

    public boolean bindAppWidgetIdIfAllowed(int i, ComponentName componentName, Bundle bundle) {
        if (this.mService == null) {
            return false;
        }
        return bindAppWidgetIdIfAllowed(i, this.mContext.getUserId(), componentName, bundle);
    }

    public boolean bindAppWidgetIdIfAllowed(int i, UserHandle userHandle, ComponentName componentName, Bundle bundle) {
        if (this.mService == null) {
            return false;
        }
        return bindAppWidgetIdIfAllowed(i, userHandle.getIdentifier(), componentName, bundle);
    }

    public boolean hasBindAppWidgetPermission(String str, int i) {
        IAppWidgetService iAppWidgetService = this.mService;
        if (iAppWidgetService == null) {
            return false;
        }
        try {
            return iAppWidgetService.hasBindAppWidgetPermission(str, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean hasBindAppWidgetPermission(String str) {
        IAppWidgetService iAppWidgetService = this.mService;
        if (iAppWidgetService == null) {
            return false;
        }
        try {
            return iAppWidgetService.hasBindAppWidgetPermission(str, this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setBindAppWidgetPermission(String str, boolean z) {
        if (this.mService == null) {
            return;
        }
        setBindAppWidgetPermission(str, this.mContext.getUserId(), z);
    }

    public void setBindAppWidgetPermission(String str, int i, boolean z) {
        IAppWidgetService iAppWidgetService = this.mService;
        if (iAppWidgetService == null) {
            return;
        }
        try {
            iAppWidgetService.setBindAppWidgetPermission(str, i, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean bindRemoteViewsService(Context context, int i, Intent intent, IServiceConnection iServiceConnection, int i2) {
        IAppWidgetService iAppWidgetService = this.mService;
        if (iAppWidgetService == null) {
            return false;
        }
        try {
            return iAppWidgetService.bindRemoteViewsService(context.getOpPackageName(), i, intent, context.getIApplicationThread(), context.getActivityToken(), iServiceConnection, Integer.toUnsignedLong(i2));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int[] getAppWidgetIds(ComponentName componentName) {
        IAppWidgetService iAppWidgetService = this.mService;
        if (iAppWidgetService == null) {
            return new int[0];
        }
        try {
            return iAppWidgetService.getAppWidgetIds(componentName);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isBoundWidgetPackage(String str, int i) {
        IAppWidgetService iAppWidgetService = this.mService;
        if (iAppWidgetService == null) {
            return false;
        }
        try {
            return iAppWidgetService.isBoundWidgetPackage(str, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private boolean bindAppWidgetIdIfAllowed(int i, int i2, ComponentName componentName, Bundle bundle) {
        if (this.mService == null) {
            return false;
        }
        try {
            Log.d(TAG, "bindAppWidgetIdIfAllowed() appWidgetIds = " + i + ", provider = " + componentName);
            return this.mService.bindAppWidgetId(this.mPackageName, i, i2, componentName, bundle);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isRequestPinAppWidgetSupported() {
        try {
            return this.mService.isRequestPinAppWidgetSupported();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean requestPinAppWidget(ComponentName componentName, PendingIntent pendingIntent) {
        return requestPinAppWidget(componentName, null, pendingIntent);
    }

    public boolean requestPinAppWidget(ComponentName componentName, Bundle bundle, PendingIntent pendingIntent) {
        try {
            return this.mService.requestPinAppWidget(this.mPackageName, componentName, bundle, pendingIntent == null ? null : pendingIntent.getIntentSender());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void noteAppWidgetTapped(int i) {
        try {
            this.mService.noteAppWidgetTapped(this.mPackageName, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setWidgetPreview(ComponentName componentName, int i, RemoteViews remoteViews) {
        try {
            return this.mService.setWidgetPreview(componentName, i, remoteViews);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public RemoteViews getWidgetPreview(ComponentName componentName, UserHandle userHandle, int i) {
        if (userHandle == null) {
            try {
                userHandle = this.mContext.getUser();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return this.mService.getWidgetPreview(this.mPackageName, componentName, userHandle.getIdentifier(), i);
    }

    public void removeWidgetPreview(ComponentName componentName, int i) {
        try {
            this.mService.removeWidgetPreview(componentName, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static PersistableBundle createWidgetInteractionEvent(int i, long j, Rect rect, int[] iArr, int[] iArr2) {
        PersistableBundle persistableBundle = new PersistableBundle();
        persistableBundle.putString(UsageStatsManager.EXTRA_EVENT_ACTION, EVENT_TYPE_WIDGET_INTERACTION);
        persistableBundle.putString(UsageStatsManager.EXTRA_EVENT_CATEGORY, EVENT_CATEGORY_APPWIDGET);
        persistableBundle.putInt(EXTRA_APPWIDGET_ID, i);
        persistableBundle.putLong(EXTRA_EVENT_DURATION_MS, j);
        if (rect != null) {
            persistableBundle.putIntArray(EXTRA_EVENT_POSITION_RECT, new int[]{rect.left, rect.top, rect.right, rect.bottom});
        }
        if (iArr != null && iArr.length > 0) {
            persistableBundle.putIntArray(EXTRA_EVENT_CLICKED_VIEWS, iArr);
        }
        if (iArr2 != null && iArr2.length > 0) {
            persistableBundle.putIntArray(EXTRA_EVENT_SCROLLED_VIEWS, iArr2);
        }
        return persistableBundle;
    }

    private static Executor createUpdateExecutorIfNull() {
        if (sUpdateExecutor == null) {
            sUpdateExecutor = new HandlerExecutor(createAndStartNewHandler("widget_manager_update_helper_thread", -2));
        }
        return sUpdateExecutor;
    }

    private static Handler createAndStartNewHandler(String str, int i) {
        HandlerThread handlerThread = new HandlerThread(str, i);
        handlerThread.start();
        return handlerThread.getThreadHandler();
    }

    public static class ServiceCollectionCache {
        private final Context mContext;
        private final long mTimeOut;
        private final Map<Intent.FilterComparison, ConnectionTask> mActiveConnections = new ArrayMap();
        private final Handler mHandler = new Handler(BackgroundThread.getHandler().getLooper());

        public ServiceCollectionCache(Context context, long j) {
            this.mContext = context;
            this.mTimeOut = j;
        }

        public void connectAndConsume(final Intent intent, final Consumer<IBinder> consumer, final Executor executor) {
            this.mHandler.post(new Runnable() { // from class: android.appwidget.AppWidgetManager$ServiceCollectionCache$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    AppWidgetManager.ServiceCollectionCache.this.lambda$connectAndConsume$0(intent, consumer, executor);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* renamed from: connectAndConsumeInner, reason: merged with bridge method [inline-methods] */
        public void lambda$connectAndConsume$0(Intent intent, Consumer<IBinder> consumer, Executor executor) {
            this.mActiveConnections.computeIfAbsent(new Intent.FilterComparison(intent), new Function() { // from class: android.appwidget.AppWidgetManager$ServiceCollectionCache$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    AppWidgetManager.ServiceCollectionCache.ConnectionTask lambda$connectAndConsumeInner$1;
                    lambda$connectAndConsumeInner$1 = AppWidgetManager.ServiceCollectionCache.this.lambda$connectAndConsumeInner$1((Intent.FilterComparison) obj);
                    return lambda$connectAndConsumeInner$1;
                }
            }).add(consumer, executor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ ConnectionTask lambda$connectAndConsumeInner$1(Intent.FilterComparison filterComparison) {
            return new ConnectionTask(filterComparison);
        }

        /* JADX INFO: Access modifiers changed from: private */
        class ConnectionTask implements ServiceConnection {
            private IBinder mIBinder;
            private final Runnable mDestroyAfterTimeout = new Runnable() { // from class: android.appwidget.AppWidgetManager$ServiceCollectionCache$ConnectionTask$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    AppWidgetManager.ServiceCollectionCache.ConnectionTask.this.onDestroyTimeout();
                }
            };
            private final ArrayDeque<Pair<Consumer<IBinder>, Executor>> mTaskQueue = new ArrayDeque<>();
            private boolean mOnDestroyTimeout = false;

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName componentName) {
            }

            ConnectionTask(Intent.FilterComparison filterComparison) {
                try {
                    Context context = ServiceCollectionCache.this.mContext;
                    Intent intent = filterComparison.getIntent();
                    Context.BindServiceFlags of = Context.BindServiceFlags.of(1L);
                    Handler handler = ServiceCollectionCache.this.mHandler;
                    Objects.requireNonNull(handler);
                    context.bindService(intent, of, new AppWidgetManager$ServiceCollectionCache$ConnectionTask$$ExternalSyntheticLambda1(handler), this);
                } catch (Exception e) {
                    Log.e(AppWidgetManager.TAG, "Error connecting to service in connection cache", e);
                }
            }

            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                this.mIBinder = iBinder;
                ServiceCollectionCache.this.mHandler.post(new AppWidgetManager$ServiceCollectionCache$ConnectionTask$$ExternalSyntheticLambda2(this));
            }

            @Override // android.content.ServiceConnection
            public void onNullBinding(ComponentName componentName) {
                onServiceConnected(componentName, new Binder());
            }

            void add(Consumer<IBinder> consumer, Executor executor) {
                this.mTaskQueue.add(Pair.create(consumer, executor));
                if (this.mOnDestroyTimeout) {
                    handleNext();
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public void handleNext() {
                ServiceCollectionCache.this.mHandler.removeCallbacks(this.mDestroyAfterTimeout);
                final Pair<Consumer<IBinder>, Executor> pollFirst = this.mTaskQueue.pollFirst();
                if (pollFirst != null) {
                    this.mOnDestroyTimeout = false;
                    pollFirst.second.execute(new Runnable() { // from class: android.appwidget.AppWidgetManager$ServiceCollectionCache$ConnectionTask$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            AppWidgetManager.ServiceCollectionCache.ConnectionTask.this.lambda$handleNext$0(pollFirst);
                        }
                    });
                } else {
                    this.mOnDestroyTimeout = true;
                    ServiceCollectionCache.this.mHandler.postDelayed(this.mDestroyAfterTimeout, ServiceCollectionCache.this.mTimeOut);
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$handleNext$0(Pair pair) {
                ((Consumer) pair.first).accept(this.mIBinder);
                ServiceCollectionCache.this.mHandler.post(new AppWidgetManager$ServiceCollectionCache$ConnectionTask$$ExternalSyntheticLambda2(this));
            }

            /* JADX INFO: Access modifiers changed from: private */
            public void onDestroyTimeout() {
                if (!this.mTaskQueue.isEmpty()) {
                    handleNext();
                    return;
                }
                try {
                    ServiceCollectionCache.this.mContext.unbindService(this);
                } catch (Exception e) {
                    Log.e(AppWidgetManager.TAG, "Error unbinding the cached connection", e);
                }
                ServiceCollectionCache.this.mActiveConnections.values().remove(this);
            }
        }
    }

    private boolean hidden_semSetTemplateWidgetPreview(ComponentName componentName, int i, int i2, RemoteViews[] remoteViewsArr) {
        try {
            Log.d(TAG, "setTemplateWidgetPreview : " + componentName + " " + i + " " + i2 + " " + remoteViewsArr);
            if (remoteViewsArr.length != 0) {
                return this.mService.setTemplateWidgetPreview(componentName, i, i2, remoteViewsArr);
            }
            Log.d(TAG, "The preview data array is empty.");
            return false;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private Bundle hidden_semGetTemplateWidgetPreview(ComponentName componentName, UserHandle userHandle, int i, int i2) {
        if (userHandle == null) {
            try {
                userHandle = this.mContext.getUser();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        Log.d(TAG, "getTemplateWidgetPreview : " + componentName + " " + i + " " + i2);
        return this.mService.getTemplateWidgetPreview(this.mPackageName, componentName, userHandle.getIdentifier(), i, i2);
    }

    private void hidden_semRemoveTemplateWidgetPreview(ComponentName componentName, int i, int i2) {
        try {
            Log.d(TAG, "removeTemplateWidgetPreview : " + componentName + " " + i + " " + i2);
            this.mService.removeTemplateWidgetPreview(componentName, i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private boolean hidden_semIsPreviewUpdateAvailable(ComponentName componentName) {
        try {
            Log.d(TAG, "isTemplatePreviewUpdateAvailable : " + componentName);
            return this.mService.isTemplatePreviewUpdateAvailable(componentName);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<AppWidgetProviderInfo> hidden_semGetInstalledProvidersForPackage(int i, String str) {
        if (this.mService == null) {
            return Collections.EMPTY_LIST;
        }
        return getInstalledProvidersForProfile(i, null, str);
    }
}
