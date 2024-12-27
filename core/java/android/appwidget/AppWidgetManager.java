package android.appwidget;

import android.app.IServiceConnection;
import android.app.PendingIntent;
import android.appwidget.flags.Flags;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ParceledListSlice;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.RemoteViews;

import com.android.internal.appwidget.IAppWidgetService;
import com.android.internal.os.BackgroundThread;
import com.android.internal.util.FunctionalUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;

public class AppWidgetManager {
    public static final String ACTION_APPWIDGET_BIND = "android.appwidget.action.APPWIDGET_BIND";
    public static final String ACTION_APPWIDGET_CONFIGURE =
            "android.appwidget.action.APPWIDGET_CONFIGURE";
    public static final String ACTION_APPWIDGET_DELETED =
            "android.appwidget.action.APPWIDGET_DELETED";
    public static final String ACTION_APPWIDGET_DISABLED =
            "android.appwidget.action.APPWIDGET_DISABLED";
    public static final String ACTION_APPWIDGET_ENABLED =
            "android.appwidget.action.APPWIDGET_ENABLED";
    public static final String ACTION_APPWIDGET_ENABLE_AND_UPDATE =
            "android.appwidget.action.APPWIDGET_ENABLE_AND_UPDATE";
    public static final String ACTION_APPWIDGET_HOST_RESTORED =
            "android.appwidget.action.APPWIDGET_HOST_RESTORED";
    public static final String ACTION_APPWIDGET_OPTIONS_CHANGED =
            "android.appwidget.action.APPWIDGET_UPDATE_OPTIONS";
    public static final String ACTION_APPWIDGET_PICK = "android.appwidget.action.APPWIDGET_PICK";
    public static final String ACTION_APPWIDGET_RESTORED =
            "android.appwidget.action.APPWIDGET_RESTORED";
    public static final String ACTION_APPWIDGET_UPDATE =
            "android.appwidget.action.APPWIDGET_UPDATE";
    public static final String ACTION_KEYGUARD_APPWIDGET_PICK =
            "android.appwidget.action.KEYGUARD_APPWIDGET_PICK";
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
    public static final String SEM_ACTION_APPWIDGET_CONFIGURE =
            "android.appwidget.action.SEM_APPWIDGET_CONFIGURE";
    public static final String SEM_ACTION_APPWIDGET_UNBIND =
            "com.samsung.android.appwidget.action.APPWIDGET_UNBIND";
    public static int SEM_APPWIDGET_LOCATION_LEFT = 0;
    public static int SEM_APPWIDGET_LOCATION_RIGHT = 1;
    public static String SEM_APPWIDGET_STYLE_COMPLICATION = "complication";
    public static final String SEM_EXTRA_APPWIDGET_PACKAGENAME = "appWidgetPackageName";
    public static final String SEM_META_DATA_CONFIGURE_ACTIVITY =
            "android.appwidget.provider.semConfigureActivity";
    public static final String SEM_OPTION_APPWIDGET_COLUMN_SPAN = "semAppWidgetColumnSpan";
    public static final String SEM_OPTION_APPWIDGET_LOCATION = "semAppWidgetLocation";
    public static final String SEM_OPTION_APPWIDGET_ROW_SPAN = "semAppWidgetRowSpan";
    public static final String SEM_OPTION_APPWIDGET_STYLE = "widgetStyle";
    private static final String TAG = "AppWidgetManager";
    private static Executor sUpdateExecutor;
    private final Context mContext;
    private final DisplayMetrics mDisplayMetrics;
    private boolean mHasPostedLegacyLists = false;
    private final String mPackageName;
    private final IAppWidgetService mService;

    public static AppWidgetManager getInstance(Context context) {
        return (AppWidgetManager) context.getSystemService(Context.APPWIDGET_SERVICE);
    }

    public AppWidgetManager(Context context, IAppWidgetService service) {
        this.mContext = context;
        this.mPackageName = context.getOpPackageName();
        this.mService = service;
        this.mDisplayMetrics = context.getResources().getDisplayMetrics();
        if (this.mService == null) {
            return;
        }
        BackgroundThread.getExecutor()
                .execute(
                        new Runnable() { // from class:
                            // android.appwidget.AppWidgetManager$$ExternalSyntheticLambda7
                            @Override // java.lang.Runnable
                            public final void run() {
                                AppWidgetManager.this.lambda$new$3();
                            }
                        });
    }

    public /* synthetic */ void lambda$new$3() {
        try {
            this.mService.notifyProviderInheritance(
                    (ComponentName[])
                            getInstalledProvidersForPackage(this.mPackageName, null).stream()
                                    .filter(
                                            new Predicate() { // from class:
                                                // android.appwidget.AppWidgetManager$$ExternalSyntheticLambda2
                                                @Override // java.util.function.Predicate
                                                public final boolean test(Object obj) {
                                                    return Objects.nonNull(
                                                            (AppWidgetProviderInfo) obj);
                                                }
                                            })
                                    .map(
                                            new Function() { // from class:
                                                // android.appwidget.AppWidgetManager$$ExternalSyntheticLambda3
                                                @Override // java.util.function.Function
                                                public final Object apply(Object obj) {
                                                    ComponentName componentName;
                                                    componentName =
                                                            ((AppWidgetProviderInfo) obj).provider;
                                                    return componentName;
                                                }
                                            })
                                    .filter(
                                            new Predicate() { // from class:
                                                // android.appwidget.AppWidgetManager$$ExternalSyntheticLambda4
                                                @Override // java.util.function.Predicate
                                                public final boolean test(Object obj) {
                                                    return AppWidgetManager.lambda$new$1(
                                                            (ComponentName) obj);
                                                }
                                            })
                                    .toArray(
                                            new IntFunction() { // from class:
                                                // android.appwidget.AppWidgetManager$$ExternalSyntheticLambda5
                                                @Override // java.util.function.IntFunction
                                                public final Object apply(int i) {
                                                    return AppWidgetManager.lambda$new$2(i);
                                                }
                                            }));
        } catch (Exception e) {
            Log.e(TAG, "Notify service of inheritance info", e);
        }
    }

    static /* synthetic */ boolean lambda$new$1(ComponentName p) {
        try {
            Class clazz = Class.forName(p.getClassName());
            return AppWidgetProvider.class.isAssignableFrom(clazz);
        } catch (Exception e) {
            return false;
        }
    }

    static /* synthetic */ ComponentName[] lambda$new$2(int x$0) {
        return new ComponentName[x$0];
    }

    private void tryAdapterConversion(
            final FunctionalUtils.RemoteExceptionIgnoringConsumer<RemoteViews> action,
            RemoteViews original,
            final String failureMsg) {
        if (Flags.remoteAdapterConversion()) {
            boolean z =
                    this.mHasPostedLegacyLists || (original != null && original.hasLegacyLists());
            this.mHasPostedLegacyLists = z;
            if (z) {
                final RemoteViews viewsCopy = new RemoteViews(original);
                Runnable updateWidgetWithTask = new Runnable() { // from class:
                            // android.appwidget.AppWidgetManager$$ExternalSyntheticLambda8
                            @Override // java.lang.Runnable
                            public final void run() {
                                AppWidgetManager.lambda$tryAdapterConversion$4(
                                        RemoteViews.this, action, failureMsg);
                            }
                        };
                if (Looper.getMainLooper() == Looper.myLooper()) {
                    createUpdateExecutorIfNull().execute(updateWidgetWithTask);
                    return;
                } else {
                    updateWidgetWithTask.run();
                    return;
                }
            }
        }
        try {
            action.acceptOrThrow(original);
        } catch (RemoteException re) {
            throw re.rethrowFromSystemServer();
        }
    }

    static /* synthetic */ void lambda$tryAdapterConversion$4(
            RemoteViews viewsCopy,
            FunctionalUtils.RemoteExceptionIgnoringConsumer action,
            String failureMsg) {
        try {
            viewsCopy.collectAllIntents().get();
            action.acceptOrThrow(viewsCopy);
        } catch (Exception e) {
            Log.e(TAG, failureMsg, e);
        }
    }

    public void updateAppWidget(final int[] appWidgetIds, RemoteViews views) {
        if (this.mService == null) {
            return;
        }
        Log.i(TAG, "updateAppWidget() appWidgetIds = " + Arrays.toString(appWidgetIds));
        tryAdapterConversion(
                new FunctionalUtils.RemoteExceptionIgnoringConsumer() { // from class:
                    // android.appwidget.AppWidgetManager$$ExternalSyntheticLambda11
                    @Override // com.android.internal.util.FunctionalUtils.RemoteExceptionIgnoringConsumer
                    public final void acceptOrThrow(Object obj) {
                        AppWidgetManager.this.lambda$updateAppWidget$5(
                                appWidgetIds, (RemoteViews) obj);
                    }
                },
                views,
                "Error updating app widget views in background");
    }

    public /* synthetic */ void lambda$updateAppWidget$5(int[] appWidgetIds, RemoteViews view)
            throws RemoteException {
        this.mService.updateAppWidgetIds(this.mPackageName, appWidgetIds, view);
    }

    public void semSetSkipPackageChanged(String packageName) {
        if (this.mService == null
                || this.mContext == null
                || !packageName.equals(this.mContext.getPackageName())) {
            return;
        }
        try {
            this.mService.semSetSkipPackageChanged(packageName);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void semChangeHostIds(int[] appWidgetIds, int hostId) {
        Log.i(
                TAG,
                "semChangeHostIds() appWidgetIds = "
                        + Arrays.toString(appWidgetIds)
                        + " hostId = "
                        + hostId);
        if (this.mService == null) {
            return;
        }
        try {
            this.mService.changeHostIds(this.mPackageName, appWidgetIds, hostId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void updateAppWidgetOptions(int appWidgetId, Bundle options) {
        if (this.mService == null) {
            return;
        }
        try {
            Log.d(
                    TAG,
                    "updateAppWidgetOptions() appWidgetId = "
                            + appWidgetId
                            + ", options = "
                            + options);
            this.mService.updateAppWidgetOptions(this.mPackageName, appWidgetId, options);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Bundle getAppWidgetOptions(int appWidgetId) {
        if (this.mService == null) {
            return Bundle.EMPTY;
        }
        try {
            return this.mService.getAppWidgetOptions(this.mPackageName, appWidgetId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void updateAppWidget(int appWidgetId, RemoteViews views) {
        if (this.mService == null) {
            return;
        }
        updateAppWidget(new int[] {appWidgetId}, views);
    }

    public void partiallyUpdateAppWidget(final int[] appWidgetIds, RemoteViews views) {
        if (this.mService == null) {
            return;
        }
        Log.i(TAG, "partiallyUpdateAppWidget() appWidgetIds = " + Arrays.toString(appWidgetIds));
        tryAdapterConversion(
                new FunctionalUtils.RemoteExceptionIgnoringConsumer() { // from class:
                    // android.appwidget.AppWidgetManager$$ExternalSyntheticLambda10
                    @Override // com.android.internal.util.FunctionalUtils.RemoteExceptionIgnoringConsumer
                    public final void acceptOrThrow(Object obj) {
                        AppWidgetManager.this.lambda$partiallyUpdateAppWidget$6(
                                appWidgetIds, (RemoteViews) obj);
                    }
                },
                views,
                "Error partially updating app widget views in background");
    }

    public /* synthetic */ void lambda$partiallyUpdateAppWidget$6(
            int[] appWidgetIds, RemoteViews view) throws RemoteException {
        this.mService.partiallyUpdateAppWidgetIds(this.mPackageName, appWidgetIds, view);
    }

    public void partiallyUpdateAppWidget(int appWidgetId, RemoteViews views) {
        if (this.mService == null) {
            return;
        }
        partiallyUpdateAppWidget(new int[] {appWidgetId}, views);
    }

    public void updateAppWidget(final ComponentName provider, RemoteViews views) {
        if (this.mService == null) {
            return;
        }
        tryAdapterConversion(
                new FunctionalUtils.RemoteExceptionIgnoringConsumer() { // from class:
                    // android.appwidget.AppWidgetManager$$ExternalSyntheticLambda6
                    @Override // com.android.internal.util.FunctionalUtils.RemoteExceptionIgnoringConsumer
                    public final void acceptOrThrow(Object obj) {
                        AppWidgetManager.this.lambda$updateAppWidget$7(provider, (RemoteViews) obj);
                    }
                },
                views,
                "Error updating app widget view using provider in background");
    }

    public /* synthetic */ void lambda$updateAppWidget$7(ComponentName provider, RemoteViews view)
            throws RemoteException {
        this.mService.updateAppWidgetProvider(provider, view);
    }

    public void updateAppWidgetProviderInfo(ComponentName provider, String metaDataKey) {
        if (this.mService == null) {
            return;
        }
        try {
            this.mService.updateAppWidgetProviderInfo(provider, metaDataKey);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public void notifyAppWidgetViewDataChanged(final int[] appWidgetIds, final int viewId) {
        if (this.mService == null) {
            return;
        }
        if (Flags.remoteAdapterConversion()) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                this.mHasPostedLegacyLists = true;
                createUpdateExecutorIfNull()
                        .execute(
                                new Runnable() { // from class:
                                    // android.appwidget.AppWidgetManager$$ExternalSyntheticLambda9
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        AppWidgetManager.this
                                                .lambda$notifyAppWidgetViewDataChanged$8(
                                                        appWidgetIds, viewId);
                                    }
                                });
                return;
            } else {
                lambda$notifyAppWidgetViewDataChanged$8(appWidgetIds, viewId);
                return;
            }
        }
        try {
            Log.i(
                    TAG,
                    "notifyAppWidgetViewDataChanged() appWidgetIds = "
                            + Arrays.toString(appWidgetIds)
                            + ", viewId = "
                            + viewId);
            this.mService.notifyAppWidgetViewDataChanged(this.mPackageName, appWidgetIds, viewId);
        } catch (RemoteException re) {
            throw re.rethrowFromSystemServer();
        }
    }

    /* renamed from: notifyCollectionWidgetChange, reason: merged with bridge method [inline-methods] */
    public void lambda$notifyAppWidgetViewDataChanged$8(int[] appWidgetIds, final int viewId) {
        try {
            List<CompletableFuture<Void>> updateFutures = new ArrayList<>();
            for (final int widgetId : appWidgetIds) {
                updateFutures.add(
                        CompletableFuture.runAsync(
                                new Runnable() { // from class:
                                    // android.appwidget.AppWidgetManager$$ExternalSyntheticLambda0
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        AppWidgetManager.this.lambda$notifyCollectionWidgetChange$9(
                                                widgetId, viewId);
                                    }
                                }));
            }
            CompletableFuture.allOf(
                            (CompletableFuture[])
                                    updateFutures.toArray(
                                            new IntFunction() { // from class:
                                                // android.appwidget.AppWidgetManager$$ExternalSyntheticLambda1
                                                @Override // java.util.function.IntFunction
                                                public final Object apply(int i) {
                                                    return AppWidgetManager
                                                            .lambda$notifyCollectionWidgetChange$10(
                                                                    i);
                                                }
                                            }))
                    .join();
        } catch (Exception e) {
            Log.e(TAG, "Error notifying changes for all widgets", e);
        }
    }

    public /* synthetic */ void lambda$notifyCollectionWidgetChange$9(int widgetId, int viewId) {
        try {
            RemoteViews views = this.mService.getAppWidgetViews(this.mPackageName, widgetId);
            if (views.replaceRemoteCollections(viewId)) {
                updateAppWidget(widgetId, views);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error notifying changes in RemoteViews", e);
        }
    }

    static /* synthetic */ CompletableFuture[] lambda$notifyCollectionWidgetChange$10(int x$0) {
        return new CompletableFuture[x$0];
    }

    @Deprecated
    public void notifyAppWidgetViewDataChanged(int appWidgetId, int viewId) {
        if (this.mService == null) {
            return;
        }
        notifyAppWidgetViewDataChanged(new int[] {appWidgetId}, viewId);
    }

    public List<AppWidgetProviderInfo> getInstalledProvidersForProfile(UserHandle profile) {
        if (this.mService == null) {
            return Collections.emptyList();
        }
        return getInstalledProvidersForProfile(1, profile, null);
    }

    public List<AppWidgetProviderInfo> getInstalledProvidersForProfile(
            int categoryFilter, UserHandle profile) {
        if (this.mService == null) {
            return Collections.emptyList();
        }
        return getInstalledProvidersForProfile(categoryFilter, profile, null);
    }

    public List<AppWidgetProviderInfo> getInstalledProvidersForPackage(
            String packageName, UserHandle profile) {
        if (packageName == null) {
            throw new NullPointerException(
                    "A non-null package must be passed to this method. If you want all widgets"
                            + " regardless of package, see"
                            + " getInstalledProvidersForProfile(UserHandle)");
        }
        if (this.mService == null) {
            return Collections.emptyList();
        }
        return getInstalledProvidersForProfile(1, profile, packageName);
    }

    public List<AppWidgetProviderInfo> getInstalledProviders() {
        if (this.mService == null) {
            return Collections.emptyList();
        }
        return getInstalledProvidersForProfile(1, null, null);
    }

    public List<AppWidgetProviderInfo> getInstalledProviders(int categoryFilter) {
        if (this.mService == null) {
            return Collections.emptyList();
        }
        return getInstalledProvidersForProfile(categoryFilter, null, null);
    }

    public List<AppWidgetProviderInfo> getInstalledProvidersForProfile(
            int categoryFilter, UserHandle profile, String packageName) {
        if (this.mService == null) {
            return Collections.emptyList();
        }
        if (profile == null) {
            profile = this.mContext.getUser();
        }
        try {
            ParceledListSlice<AppWidgetProviderInfo> providers =
                    this.mService.getInstalledProvidersForProfile(
                            categoryFilter, profile.getIdentifier(), packageName);
            if (providers == null) {
                return Collections.emptyList();
            }
            for (AppWidgetProviderInfo info : providers.getList()) {
                info.updateDimensions(this.mDisplayMetrics);
            }
            return providers.getList();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public AppWidgetProviderInfo getAppWidgetInfo(int appWidgetId) {
        if (this.mService == null) {
            Log.e(TAG, "Service wasn't initialized, appWidgetId=" + appWidgetId);
            return null;
        }
        try {
            AppWidgetProviderInfo info =
                    this.mService.getAppWidgetInfo(this.mPackageName, appWidgetId);
            if (info != null) {
                info.updateDimensions(this.mDisplayMetrics);
            } else {
                Log.e(
                        TAG,
                        "App widget provider info is null. PackageName="
                                + this.mPackageName
                                + " appWidgetId-"
                                + appWidgetId);
            }
            return info;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void bindAppWidgetId(int appWidgetId, ComponentName provider) {
        if (this.mService == null) {
            return;
        }
        bindAppWidgetId(appWidgetId, provider, null);
    }

    public void bindAppWidgetId(int appWidgetId, ComponentName provider, Bundle options) {
        if (this.mService == null) {
            return;
        }
        bindAppWidgetIdIfAllowed(appWidgetId, this.mContext.getUser(), provider, options);
    }

    public boolean bindAppWidgetIdIfAllowed(int appWidgetId, ComponentName provider) {
        if (this.mService == null) {
            return false;
        }
        return bindAppWidgetIdIfAllowed(
                appWidgetId, this.mContext.getUserId(), provider, (Bundle) null);
    }

    public boolean bindAppWidgetIdIfAllowed(
            int appWidgetId, ComponentName provider, Bundle options) {
        if (this.mService == null) {
            return false;
        }
        return bindAppWidgetIdIfAllowed(appWidgetId, this.mContext.getUserId(), provider, options);
    }

    public boolean bindAppWidgetIdIfAllowed(
            int appWidgetId, UserHandle user, ComponentName provider, Bundle options) {
        if (this.mService == null) {
            return false;
        }
        return bindAppWidgetIdIfAllowed(appWidgetId, user.getIdentifier(), provider, options);
    }

    public boolean hasBindAppWidgetPermission(String packageName, int userId) {
        if (this.mService == null) {
            return false;
        }
        try {
            return this.mService.hasBindAppWidgetPermission(packageName, userId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean hasBindAppWidgetPermission(String packageName) {
        if (this.mService == null) {
            return false;
        }
        try {
            return this.mService.hasBindAppWidgetPermission(packageName, this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setBindAppWidgetPermission(String packageName, boolean permission) {
        if (this.mService == null) {
            return;
        }
        setBindAppWidgetPermission(packageName, this.mContext.getUserId(), permission);
    }

    public void setBindAppWidgetPermission(String packageName, int userId, boolean permission) {
        if (this.mService == null) {
            return;
        }
        try {
            this.mService.setBindAppWidgetPermission(packageName, userId, permission);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean bindRemoteViewsService(
            Context context,
            int appWidgetId,
            Intent intent,
            IServiceConnection connection,
            int flags) {
        if (this.mService == null) {
            return false;
        }
        try {
            return this.mService.bindRemoteViewsService(
                    context.getOpPackageName(),
                    appWidgetId,
                    intent,
                    context.getIApplicationThread(),
                    context.getActivityToken(),
                    connection,
                    Integer.toUnsignedLong(flags));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int[] getAppWidgetIds(ComponentName provider) {
        if (this.mService == null) {
            return new int[0];
        }
        try {
            return this.mService.getAppWidgetIds(provider);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isBoundWidgetPackage(String packageName, int userId) {
        if (this.mService == null) {
            return false;
        }
        try {
            return this.mService.isBoundWidgetPackage(packageName, userId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private boolean bindAppWidgetIdIfAllowed(
            int appWidgetId, int profileId, ComponentName provider, Bundle options) {
        if (this.mService != null) {
            try {
                Log.d(
                        TAG,
                        "bindAppWidgetIdIfAllowed() appWidgetIds = "
                                + appWidgetId
                                + ", provider = "
                                + provider);
                Log.d(TAG, "Stack:", new Throwable("stack dump"));
                return this.mService.bindAppWidgetId(
                        this.mPackageName, appWidgetId, profileId, provider, options);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public boolean isRequestPinAppWidgetSupported() {
        try {
            return this.mService.isRequestPinAppWidgetSupported();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean requestPinAppWidget(ComponentName provider, PendingIntent successCallback) {
        return requestPinAppWidget(provider, null, successCallback);
    }

    public boolean requestPinAppWidget(
            ComponentName provider, Bundle extras, PendingIntent successCallback) {
        try {
            return this.mService.requestPinAppWidget(
                    this.mPackageName,
                    provider,
                    extras,
                    successCallback == null ? null : successCallback.getIntentSender());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void noteAppWidgetTapped(int appWidgetId) {
        try {
            this.mService.noteAppWidgetTapped(this.mPackageName, appWidgetId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setWidgetPreview(
            ComponentName provider, int widgetCategories, RemoteViews preview) {
        try {
            return this.mService.setWidgetPreview(provider, widgetCategories, preview);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public RemoteViews getWidgetPreview(
            ComponentName provider, UserHandle profile, int widgetCategory) {
        if (profile == null) {
            try {
                profile = this.mContext.getUser();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return this.mService.getWidgetPreview(
                this.mPackageName, provider, profile.getIdentifier(), widgetCategory);
    }

    public void removeWidgetPreview(ComponentName provider, int widgetCategories) {
        try {
            this.mService.removeWidgetPreview(provider, widgetCategories);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private static Executor createUpdateExecutorIfNull() {
        if (sUpdateExecutor == null) {
            sUpdateExecutor =
                    new HandlerExecutor(
                            createAndStartNewHandler("widget_manager_update_helper_thread", -2));
        }
        return sUpdateExecutor;
    }

    private static Handler createAndStartNewHandler(String name, int priority) {
        HandlerThread thread = new HandlerThread(name, priority);
        thread.start();
        return thread.getThreadHandler();
    }

    private boolean hidden_semSetTemplateWidgetPreview(
            ComponentName provider, int templateSize, int templateStyle, RemoteViews[] preview) {
        try {
            Log.d(
                    TAG,
                    "setTemplateWidgetPreview : "
                            + provider
                            + " "
                            + templateSize
                            + " "
                            + templateStyle
                            + " "
                            + preview);
            if (preview.length == 0) {
                Log.d(TAG, "The preview data array is empty.");
                return false;
            }
            return this.mService.setTemplateWidgetPreview(
                    provider, templateSize, templateStyle, preview);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private Bundle hidden_semGetTemplateWidgetPreview(
            ComponentName provider, UserHandle profile, int templateSize, int templateStyle) {
        if (profile == null) {
            try {
                profile = this.mContext.getUser();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        Log.d(
                TAG,
                "getTemplateWidgetPreview : "
                        + provider
                        + " "
                        + templateSize
                        + " "
                        + templateStyle);
        return this.mService.getTemplateWidgetPreview(
                this.mPackageName, provider, profile.getIdentifier(), templateSize, templateStyle);
    }

    private void hidden_semRemoveTemplateWidgetPreview(
            ComponentName provider, int templateSize, int templateStyle) {
        try {
            Log.d(
                    TAG,
                    "removeTemplateWidgetPreview : "
                            + provider
                            + " "
                            + templateSize
                            + " "
                            + templateStyle);
            this.mService.removeTemplateWidgetPreview(provider, templateSize, templateStyle);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private boolean hidden_semIsPreviewUpdateAvailable(ComponentName provider) {
        try {
            Log.d(TAG, "isTemplatePreviewUpdateAvailable : " + provider);
            return this.mService.isTemplatePreviewUpdateAvailable(provider);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
