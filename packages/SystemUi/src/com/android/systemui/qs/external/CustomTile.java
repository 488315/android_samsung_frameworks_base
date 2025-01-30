package com.android.systemui.qs.external;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.metrics.LogMaker;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.service.quicksettings.IQSTileService;
import android.service.quicksettings.Tile;
import android.text.TextUtils;
import android.util.Log;
import android.view.IWindowManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManagerGlobal;
import android.widget.FrameLayout;
import android.widget.RemoteViews;
import android.widget.Switch;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.KeyAttributes$$ExternalSyntheticOutline0;
import androidx.core.app.AbstractC0147x487e7be7;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.animation.GhostedViewLaunchAnimatorController;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.qp.flashlight.SubscreenFlashLightController;
import com.android.systemui.qp.util.SubscreenUtil;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.external.CustomTile;
import com.android.systemui.qs.external.TileLifecycleManager;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.pipeline.data.repository.CustomTileAddedRepository;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.qs.tileimpl.SQSTileImpl;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.DetailAdapter;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.ScalingDrawableWrapper;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.concurrency.ExecutorImpl;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;
import java.util.function.Supplier;
import org.json.JSONException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class CustomTile extends SQSTileImpl implements TileLifecycleManager.TileChangeListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final ComponentName mComponent;
    public final CustomTileStatePersister mCustomTileStatePersister;
    public Icon mDefaultIcon;
    public CharSequence mDefaultLabel;
    public final CustomDetailAdapter mDetailAdapter;
    public RemoteViews mDetailView;
    public CharSequence mDetailViewTitle;
    public final DisplayLifecycle mDisplayLifecycle;
    public final DisplayTracker mDisplayTracker;
    public final AtomicBoolean mInitialDefaultIconFetched;
    public boolean mInitialized;
    public final String mIntentAction;
    public boolean mIsSecActiveTile;
    public boolean mIsSecCustomTile;
    public boolean mIsShowingDialog;
    public boolean mIsSupportDetailView;
    public final boolean mIsSystemApp;
    public boolean mIsTileStateActive;
    public boolean mIsToggleButtonExist;
    public boolean mIsTokenGranted;
    public boolean mIsUnlockAndRun;
    public final TileServiceKey mKey;
    public boolean mListening;
    public Bundle mMetaData;
    public final SecQSPanelResourcePicker mResourcePicker;
    public String mSearchTitle;
    public final TileLifecycleManager mService;
    public final TileServiceManager mServiceManager;
    public Intent mSettingsIntent;
    public final RunnableC21582 mStopUnlockAndRun;
    public SubscreenCustomTileReceiver mSubscreenCustomTileReceiver;
    public final Tile mTile;
    public String mTileClassName;
    public String mTileClassNameFromMetaData;
    public final TileServices mTileServices;
    public int mTileState;
    public boolean mToggleEnabled;
    public final IBinder mToken;
    public String mUnlockPolicy;
    public final int mUser;
    public final Context mUserContext;
    public String mUserPolicy;
    public final UserTracker mUserTracker;
    public View mViewClicked;
    public final IWindowManager mWindowManager;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Builder {
        public final ActivityStarter mActivityStarter;
        public final Looper mBackgroundLooper;
        public final BroadcastDispatcher mBroadcastDispatcher;
        public final CustomTileStatePersister mCustomTileStatePersister;
        public final DisplayLifecycle mDisplayLifecycle;
        public final DisplayTracker mDisplayTracker;
        public final FalsingManager mFalsingManager;
        public final Handler mMainHandler;
        public final MetricsLogger mMetricsLogger;
        public final Lazy mQSHostLazy;
        public final QSLogger mQSLogger;
        public String mSpec = "";
        public final StatusBarStateController mStatusBarStateController;
        public final TileServices mTileServices;
        public final QsEventLogger mUiEventLogger;
        public Context mUserContext;
        public final UserTracker mUserTracker;

        public Builder(Lazy lazy, QsEventLogger qsEventLogger, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, CustomTileStatePersister customTileStatePersister, TileServices tileServices, DisplayTracker displayTracker, UserTracker userTracker, BroadcastDispatcher broadcastDispatcher, DisplayLifecycle displayLifecycle) {
            this.mQSHostLazy = lazy;
            this.mUiEventLogger = qsEventLogger;
            this.mBackgroundLooper = looper;
            this.mMainHandler = handler;
            this.mFalsingManager = falsingManager;
            this.mMetricsLogger = metricsLogger;
            this.mStatusBarStateController = statusBarStateController;
            this.mActivityStarter = activityStarter;
            this.mQSLogger = qSLogger;
            this.mCustomTileStatePersister = customTileStatePersister;
            this.mTileServices = tileServices;
            this.mDisplayTracker = displayTracker;
            this.mUserTracker = userTracker;
            if (QpRune.QUICK_PANEL_SUBSCREEN) {
                this.mDisplayLifecycle = displayLifecycle;
                this.mBroadcastDispatcher = broadcastDispatcher;
            }
        }

        public CustomTile build() {
            if (this.mUserContext == null) {
                throw new NullPointerException("UserContext cannot be null");
            }
            String str = this.mSpec;
            int i = CustomTile.$r8$clinit;
            if (str == null || !str.startsWith("custom(") || !str.endsWith(")")) {
                throw new IllegalArgumentException(KeyAttributes$$ExternalSyntheticOutline0.m21m("Bad custom tile spec: ", str));
            }
            String substring = str.substring(7, str.length() - 1);
            if (substring.isEmpty()) {
                throw new IllegalArgumentException("Empty custom tile spec action");
            }
            return new CustomTile((QSHost) this.mQSHostLazy.get(), this.mUiEventLogger, this.mBackgroundLooper, this.mMainHandler, this.mFalsingManager, this.mMetricsLogger, this.mStatusBarStateController, this.mActivityStarter, this.mQSLogger, substring, this.mUserContext, this.mCustomTileStatePersister, this.mTileServices, this.mDisplayTracker, this.mUserTracker, this.mBroadcastDispatcher, this.mDisplayLifecycle, 0);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class CustomDetailAdapter implements DetailAdapter {
        public static final /* synthetic */ int $r8$clinit = 0;
        public final C21591 mInteractionHandler = new RemoteViews.InteractionHandler() { // from class: com.android.systemui.qs.external.CustomTile.CustomDetailAdapter.1
            public final boolean onInteraction(View view, PendingIntent pendingIntent, RemoteViews.RemoteResponse remoteResponse) {
                boolean isActivity = pendingIntent.isActivity();
                CustomTile customTile = CustomTile.this;
                int i = CustomTile.$r8$clinit;
                String str = customTile.TAG;
                if (!isActivity) {
                    return RemoteViews.startPendingIntent(view, pendingIntent, remoteResponse.getLaunchOptions(view));
                }
                customTile.showDetail(false);
                ((ActivityStarter) Dependency.get(ActivityStarter.class)).postStartActivityDismissingKeyguard(pendingIntent);
                return true;
            }
        };
        public final IQSTileService mService;

        /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.qs.external.CustomTile$CustomDetailAdapter$1] */
        public CustomDetailAdapter(IQSTileService iQSTileService) {
            this.mService = iQSTileService;
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public final View createDetailView(Context context, View view, ViewGroup viewGroup) {
            RemoteViews remoteViews;
            IQSTileService iQSTileService = this.mService;
            if (iQSTileService == null) {
                return null;
            }
            CustomTile customTile = CustomTile.this;
            if (!customTile.mIsSupportDetailView) {
                return null;
            }
            try {
                boolean shouldUseArchivedDetailInfo = customTile.shouldUseArchivedDetailInfo();
                TileServiceManager tileServiceManager = customTile.mServiceManager;
                if (shouldUseArchivedDetailInfo) {
                    tileServiceManager.setBindRequested(true);
                    iQSTileService.onStartListening();
                    remoteViews = customTile.mDetailView;
                } else {
                    RemoteViews semGetDetailView = iQSTileService.semGetDetailView();
                    if (customTile.mIsSecActiveTile) {
                        customTile.mDetailView = semGetDetailView;
                        tileServiceManager.setBindRequested(true);
                        iQSTileService.onStartListening();
                    }
                    remoteViews = semGetDetailView;
                }
                Log.d(customTile.TAG, "getDetailView remoteViews = " + remoteViews);
                if (remoteViews == null) {
                    return null;
                }
                FrameLayout frameLayout = new FrameLayout(context);
                frameLayout.addView(remoteViews.apply(context, frameLayout, this.mInteractionHandler, null));
                return frameLayout;
            } catch (RemoteException unused) {
                return null;
            }
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public final int getMetricsCategory() {
            return 268;
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public final Intent getSettingsIntent() {
            IQSTileService iQSTileService = this.mService;
            if (iQSTileService == null) {
                return null;
            }
            int i = CustomTile.$r8$clinit;
            CustomTile customTile = CustomTile.this;
            if (customTile.shouldUseArchivedDetailInfo()) {
                return customTile.mSettingsIntent;
            }
            try {
                Intent semGetSettingsIntent = iQSTileService.semGetSettingsIntent();
                if (customTile.mIsSecActiveTile) {
                    customTile.mSettingsIntent = semGetSettingsIntent;
                }
                return semGetSettingsIntent;
            } catch (RemoteException unused) {
                return null;
            }
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public final CharSequence getTitle() {
            IQSTileService iQSTileService = this.mService;
            if (iQSTileService == null) {
                return null;
            }
            int i = CustomTile.$r8$clinit;
            CustomTile customTile = CustomTile.this;
            if (customTile.shouldUseArchivedDetailInfo()) {
                return customTile.mDetailViewTitle;
            }
            try {
                CharSequence semGetDetailViewTitle = iQSTileService.semGetDetailViewTitle();
                if (customTile.mIsSecActiveTile) {
                    customTile.mDetailViewTitle = semGetDetailViewTitle;
                }
                return semGetDetailViewTitle;
            } catch (RemoteException unused) {
                return null;
            }
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public final boolean getToggleEnabled() {
            return CustomTile.this.mToggleEnabled;
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public final Boolean getToggleState() {
            IQSTileService iQSTileService = this.mService;
            if (iQSTileService == null) {
                return null;
            }
            int i = CustomTile.$r8$clinit;
            CustomTile customTile = CustomTile.this;
            if (!customTile.shouldUseArchivedDetailInfo()) {
                try {
                    boolean semIsToggleButtonExists = iQSTileService.semIsToggleButtonExists();
                    if (customTile.mIsSecActiveTile) {
                        customTile.mIsToggleButtonExist = semIsToggleButtonExists;
                    }
                    if (semIsToggleButtonExists) {
                        return Boolean.valueOf(iQSTileService.semIsToggleButtonChecked());
                    }
                } catch (RemoteException unused) {
                }
            } else if (customTile.mIsToggleButtonExist) {
                return Boolean.valueOf(customTile.mState.state == 2);
            }
            return null;
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public final void setToggleState(boolean z) {
            Boolean toggleState = getToggleState();
            int i = CustomTile.$r8$clinit;
            CustomTile customTile = CustomTile.this;
            String str = customTile.TAG;
            TileServiceManager tileServiceManager = customTile.mServiceManager;
            ExifInterface$$ExternalSyntheticOutline0.m35m(RowView$$ExternalSyntheticOutline0.m49m("setToggleState  ", z, "getTileSpec() = "), customTile.mTileSpec, str);
            IQSTileService iQSTileService = this.mService;
            if (iQSTileService == null || toggleState == null) {
                return;
            }
            boolean isBlockedEdmSettingsChange$1 = ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.get(KnoxStateMonitor.class))).isBlockedEdmSettingsChange$1();
            String str2 = customTile.TAG;
            if (isBlockedEdmSettingsChange$1) {
                customTile.showItPolicyToast();
                Log.d(str2, "setToggleState blocked");
                customTile.fireToggleStateChanged(toggleState.booleanValue());
                return;
            }
            int i2 = 1;
            if ((customTile.mUnlockPolicy.equals("ALL") || (customTile.mUnlockPolicy.equals("ON") && z) || (customTile.mUnlockPolicy.equals("OFF") && !z)) && ((KeyguardStateControllerImpl) ((KeyguardStateController) Dependency.get(KeyguardStateController.class))).mShowing && ((KeyguardStateControllerImpl) ((KeyguardStateController) Dependency.get(KeyguardStateController.class))).mSecure && !((KeyguardStateControllerImpl) ((KeyguardStateController) Dependency.get(KeyguardStateController.class))).mCanDismissLockScreen && ((SettingsHelper) Dependency.get(SettingsHelper.class)).isLockFunctionsEnabled()) {
                ((ActivityStarter) Dependency.get(ActivityStarter.class)).postQSRunnableDismissingKeyguard(new CustomTile$$ExternalSyntheticLambda1(i2, this, toggleState));
                customTile.fireToggleStateChanged(toggleState.booleanValue());
                return;
            }
            try {
                if (tileServiceManager.isActiveTile()) {
                    tileServiceManager.setBindRequested(true);
                    iQSTileService.onStartListening();
                }
                Log.d(str2, "setToggleState state = " + z);
                iQSTileService.semSetToggleButtonChecked(z);
            } catch (RemoteException unused) {
            }
            customTile.fireToggleStateChanged(z);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SubscreenCustomTileReceiver extends BroadcastReceiver {
        public SubscreenCustomTileReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(CustomTile.this.mIntentAction)) {
                try {
                    CustomTile.this.mService.onUnlockComplete();
                    CustomTile.this.mServiceManager.setWaitingUnlockState(false);
                    CustomTile customTile = CustomTile.this;
                    ((SQSTileImpl) customTile).mHandler.postDelayed(customTile.mStopUnlockAndRun, 1000L);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    enum SubscreenSALog {
        /* JADX INFO: Fake field, exist only in values array */
        SUBSCREEN_SCREENRECORDER_TILE("com.samsung.android.app.smartcapture", "QPBE2021"),
        /* JADX INFO: Fake field, exist only in values array */
        SUBSCREEN_MODES_TILE("com.samsung.android.app.routines", "QPBE2022");

        private final String mLogId;
        private final String mPackageName;

        SubscreenSALog(String str, String str2) {
            this.mPackageName = str;
            this.mLogId = str2;
        }

        public final String getLogId() {
            return this.mLogId;
        }

        public final boolean hasSamePackageName(String str) {
            return this.mPackageName.equals(str);
        }
    }

    public /* synthetic */ CustomTile(QSHost qSHost, QsEventLogger qsEventLogger, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, String str, Context context, CustomTileStatePersister customTileStatePersister, TileServices tileServices, DisplayTracker displayTracker, UserTracker userTracker, BroadcastDispatcher broadcastDispatcher, DisplayLifecycle displayLifecycle, int i) {
        this(qSHost, qsEventLogger, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger, str, context, customTileStatePersister, tileServices, displayTracker, userTracker, broadcastDispatcher, displayLifecycle);
    }

    public static ComponentName getComponentFromSpec(String str) {
        String substring = str.substring(7, str.length() - 1);
        if (substring.isEmpty()) {
            throw new IllegalArgumentException("Empty custom tile spec action");
        }
        return ComponentName.unflattenFromString(substring);
    }

    public static String toSpec(ComponentName componentName) {
        return "custom(" + componentName.flattenToShortString() + ")";
    }

    public final void applyTileState(Tile tile, boolean z) {
        Icon icon = tile.getIcon();
        Tile tile2 = this.mTile;
        if (icon != null || z) {
            tile2.setIcon(tile.getIcon());
        }
        if (tile.getLabel() != null || z) {
            tile2.setLabel(tile.getLabel());
        }
        if (tile.getSubtitle() != null || z) {
            tile2.setSubtitle(tile.getSubtitle());
        }
        if (tile.getContentDescription() != null || z) {
            tile2.setContentDescription(tile.getContentDescription());
        }
        if (tile.getStateDescription() != null || z) {
            tile2.setStateDescription(tile.getStateDescription());
        }
        tile2.setActivityLaunchForClick(tile.getActivityLaunchForClick());
        tile2.setState(tile.getState());
        Log.d(this.TAG, "updateState : Label = " + ((Object) tile.getLabel()) + ", State = " + tile.getState() + ", Icon = " + tile.getIcon());
        if (this.mIsSecCustomTile) {
            if (!this.mListening) {
                try {
                    this.mListening = false;
                    TileLifecycleManager tileLifecycleManager = this.mService;
                    if (tileLifecycleManager != null) {
                        tileLifecycleManager.onStopListening();
                    }
                    TileServiceManager tileServiceManager = this.mServiceManager;
                    if (tileServiceManager != null) {
                        tileServiceManager.setBindRequested(false);
                    }
                } catch (RemoteException unused) {
                }
            }
            if (this.mTileState != tile2.getState()) {
                this.mTileState = tile2.getState();
            }
        }
    }

    @Override // com.android.systemui.qs.tileimpl.SQSTileImpl, com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final DetailAdapter getDetailAdapter() {
        CustomDetailAdapter customDetailAdapter;
        if (this.mIsSupportDetailView && (customDetailAdapter = this.mDetailAdapter) != null) {
            if (!shouldUseArchivedDetailInfo()) {
                try {
                    TileLifecycleManager tileLifecycleManager = this.mService;
                    if (tileLifecycleManager != null) {
                        if (tileLifecycleManager.semGetDetailView() != null) {
                            return customDetailAdapter;
                        }
                    }
                } catch (RemoteException unused) {
                }
            } else if (this.mDetailView != null) {
                return customDetailAdapter;
            }
        }
        return null;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final Intent getLongClickIntent() {
        Intent intent;
        Intent intent2;
        if (this.mIsSupportDetailView) {
            return null;
        }
        TileLifecycleManager tileLifecycleManager = this.mService;
        if (tileLifecycleManager != null) {
            try {
                if (shouldUseArchivedDetailInfo()) {
                    intent = this.mSettingsIntent;
                } else {
                    intent = tileLifecycleManager.semGetSettingsIntent();
                    try {
                        if (this.mIsSecActiveTile) {
                            this.mSettingsIntent = intent;
                        }
                    } catch (RemoteException unused) {
                    }
                }
            } catch (RemoteException unused2) {
                intent = null;
            }
            if (intent != null) {
                return intent;
            }
        }
        if (this.mIsSecCustomTile) {
            return null;
        }
        Intent intent3 = new Intent("android.service.quicksettings.action.QS_TILE_PREFERENCES");
        ComponentName componentName = this.mComponent;
        intent3.setPackage(componentName.getPackageName());
        ResolveInfo resolveActivityAsUser = this.mContext.getPackageManager().resolveActivityAsUser(intent3, 0, this.mUser);
        if (resolveActivityAsUser != null) {
            Intent intent4 = new Intent("android.service.quicksettings.action.QS_TILE_PREFERENCES");
            ActivityInfo activityInfo = resolveActivityAsUser.activityInfo;
            intent2 = intent4.setClassName(activityInfo.packageName, activityInfo.name);
        } else {
            intent2 = null;
        }
        if (intent2 == null) {
            return new Intent("android.settings.APPLICATION_DETAILS_SETTINGS").setData(Uri.fromParts("package", componentName.getPackageName(), null));
        }
        intent2.putExtra("android.intent.extra.COMPONENT_NAME", componentName);
        intent2.putExtra("state", this.mTile.getState());
        return intent2;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final int getMetricsCategory() {
        return 268;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final String getMetricsSpec() {
        return this.mComponent.getPackageName();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.indexsearch.Searchable
    public final String getSearchTitle() {
        CharSequence charSequence = this.mState.label;
        if (charSequence != null) {
            return charSequence.toString().replaceAll(System.getProperty("line.separator"), " ").trim();
        }
        String str = this.mSearchTitle;
        if (str != null) {
            return str.replaceAll(System.getProperty("line.separator"), " ").trim();
        }
        return null;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.indexsearch.Searchable
    public final ArrayList getSearchWords() {
        Context context = this.mContext;
        ComponentName componentName = this.mComponent;
        try {
            Bundle bundle = context.getPackageManager().getServiceInfo(componentName, 787072).metaData;
            if (bundle != null) {
                String string = bundle.getString("android.service.quicksettings.SEM_DEFAULT_TILE_SEARCH_KEYWORDS", "");
                if (!"".equals(string)) {
                    ArrayList arrayList = new ArrayList();
                    for (String str : string.split(";")) {
                        Resources resources = context.createPackageContext(componentName.getPackageName(), 0).getResources();
                        int identifier = resources.getIdentifier(str, "string", componentName.getPackageName());
                        if (identifier == 0) {
                            Objects.toString(super.getSearchWords());
                            return super.getSearchWords();
                        }
                        arrayList.add(resources.getString(identifier).trim().toLowerCase());
                    }
                    String lowerCase = getSearchTitle().toLowerCase();
                    if (lowerCase != null && !arrayList.contains(lowerCase)) {
                        arrayList.add(lowerCase);
                    }
                    return arrayList;
                }
            }
            return super.getSearchWords();
        } catch (PackageManager.NameNotFoundException unused) {
            return super.getSearchWords();
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final long getStaleTimeout() {
        return (this.mHost.indexOf(this.mTileSpec) * 60000) + 3600000;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final Drawable getTileIconDrawable() {
        try {
            Supplier<QSTile.Icon> supplier = this.mState.iconSupplier;
            if (supplier != null) {
                return supplier.get().getDrawable(this.mContext);
            }
            Icon icon = this.mDefaultIcon;
            if (icon != null) {
                return icon.loadDrawable(this.mUserContext);
            }
            return null;
        } catch (Exception unused) {
            return null;
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final CharSequence getTileLabel() {
        return this.mState.label;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleClick(View view) {
        IBinder iBinder = this.mToken;
        Tile tile = this.mTile;
        if (tile.getState() == 0) {
            return;
        }
        TileServiceManager tileServiceManager = this.mServiceManager;
        if (tileServiceManager.mPendingBind && tileServiceManager.mStateManager.mHasPendingBind) {
            Log.w(this.TAG, AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(new StringBuilder("handleClick : "), this.mTileClassName, " hasPendingBind"));
            return;
        }
        this.mViewClicked = view;
        try {
            IWindowManager iWindowManager = this.mWindowManager;
            this.mDisplayTracker.getClass();
            iWindowManager.addWindowToken(iBinder, 2035, 0, (Bundle) null);
            this.mIsTokenGranted = true;
        } catch (RemoteException unused) {
        }
        try {
            boolean isActiveTile = tileServiceManager.isActiveTile();
            TileLifecycleManager tileLifecycleManager = this.mService;
            if (isActiveTile) {
                tileServiceManager.setBindRequested(true);
                tileLifecycleManager.onStartListening();
            }
            boolean z = QpRune.QUICK_PANEL_SUBSCREEN;
            DisplayLifecycle displayLifecycle = this.mDisplayLifecycle;
            if (z) {
                if (!(displayLifecycle != null ? displayLifecycle.mIsFolderOpened : false)) {
                    SubscreenUtil subscreenUtil = (SubscreenUtil) Dependency.get(SubscreenUtil.class);
                    Context context = this.mContext;
                    subscreenUtil.getClass();
                    SubscreenFlashLightController.getInstance(context).finishFlashLightActivity();
                    ((SubscreenUtil) Dependency.get(SubscreenUtil.class)).closeSubscreenPanel();
                    this.mUiHandler.post(new CustomTile$$ExternalSyntheticLambda3());
                }
            }
            if (tile.getActivityLaunchForClick() != null) {
                startActivityAndCollapse(tile.getActivityLaunchForClick());
            } else {
                tileLifecycleManager.onClick(iBinder);
            }
            if (z) {
                if (displayLifecycle != null ? displayLifecycle.mIsFolderOpened : false) {
                    return;
                }
                final String resPackage = tile.getIcon().getResPackage();
                Arrays.stream(SubscreenSALog.values()).filter(new Predicate() { // from class: com.android.systemui.qs.external.CustomTile$$ExternalSyntheticLambda5
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((CustomTile.SubscreenSALog) obj).hasSamePackageName(resPackage);
                    }
                }).findFirst().ifPresent(new CustomTile$$ExternalSyntheticLambda6());
            }
        } catch (RemoteException unused2) {
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleDestroy() {
        SubscreenCustomTileReceiver subscreenCustomTileReceiver;
        BroadcastDispatcher broadcastDispatcher;
        super.handleDestroy();
        int i = 0;
        if (this.mIsTokenGranted) {
            try {
                IWindowManager iWindowManager = this.mWindowManager;
                IBinder iBinder = this.mToken;
                this.mDisplayTracker.getClass();
                iWindowManager.removeWindowToken(iBinder, 0);
            } catch (RemoteException unused) {
            }
        }
        TileServices tileServices = this.mTileServices;
        TileServiceManager tileServiceManager = this.mServiceManager;
        synchronized (tileServices.mServices) {
            if (TileServices.DEBUG) {
                Log.d("TileServices", "freeService" + this);
            }
            tileServiceManager.setBindAllowed(false);
            tileServiceManager.handleDestroy();
            tileServices.mServices.remove(this);
            tileServices.mTokenMap.remove(tileServiceManager.mStateManager.mToken);
            tileServices.mTiles.delete(this.mUser, this.mComponent);
            tileServices.mMainHandler.post(new TileServices$$ExternalSyntheticLambda1(tileServices, this.mComponent.getClassName(), i));
            if (tileServices.mServices.size() == 0 && tileServices.mUninstallReceiverRegistered) {
                tileServices.mBroadcastDispatcher.unregisterReceiver(tileServices.mUninstallReceiver);
                tileServices.mUninstallReceiverRegistered = false;
            }
        }
        if (!QpRune.QUICK_PANEL_SUBSCREEN || (subscreenCustomTileReceiver = this.mSubscreenCustomTileReceiver) == null || (broadcastDispatcher = this.mBroadcastDispatcher) == null) {
            return;
        }
        broadcastDispatcher.unregisterReceiver(subscreenCustomTileReceiver);
        this.mSubscreenCustomTileReceiver = null;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x007b  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0087  */
    /* JADX WARN: Removed duplicated region for block: B:21:? A[RETURN, SYNTHETIC] */
    @Override // com.android.systemui.qs.tileimpl.SQSTileImpl, com.android.systemui.qs.tileimpl.QSTileImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void handleInitialize() {
        Tile readTileFromString;
        updateDefaultTileAndIcon();
        int i = 1;
        boolean compareAndSet = this.mInitialDefaultIconFetched.compareAndSet(false, true);
        SQSTileImpl.SHandler sHandler = ((SQSTileImpl) this).mHandler;
        if (compareAndSet && this.mDefaultIcon == null) {
            Log.w(this.TAG, AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(new StringBuilder("No default icon for "), this.mTileSpec, ", destroying tile"));
            sHandler.postDelayed(new Runnable() { // from class: com.android.systemui.qs.external.CustomTile.1
                @Override // java.lang.Runnable
                public final void run() {
                    CustomTile customTile = CustomTile.this;
                    int i2 = CustomTile.$r8$clinit;
                    customTile.mHost.removeTile(customTile.mTileSpec);
                }
            }, 1000L);
        }
        TileServiceManager tileServiceManager = this.mServiceManager;
        if (tileServiceManager.isToggleableTile()) {
            this.mState = newTileState();
            QSTile.State newTileState = newTileState();
            this.mTmpState = newTileState;
            QSTile.State state = this.mState;
            String str = this.mTileSpec;
            state.spec = str;
            newTileState.spec = str;
        }
        tileServiceManager.mStateManager.mChangeListener = this;
        if (!tileServiceManager.isActiveTile()) {
            return;
        }
        String string = this.mCustomTileStatePersister.sharedPreferences.getString(this.mKey.string, null);
        if (string != null) {
            try {
                readTileFromString = CustomTileStatePersisterKt.readTileFromString(string);
            } catch (JSONException e) {
                Log.e("TileServicePersistence", "Bad saved state: ".concat(string), e);
            }
            if (readTileFromString != null) {
                applyTileState(readTileFromString, false);
                tileServiceManager.mPendingBind = false;
                refreshState(null);
            }
            if (this.mIsSecActiveTile) {
                return;
            }
            sHandler.post(new CustomTile$$ExternalSyntheticLambda2(this, i));
            return;
        }
        readTileFromString = null;
        if (readTileFromString != null) {
        }
        if (this.mIsSecActiveTile) {
        }
    }

    @Override // com.android.systemui.qs.tileimpl.SQSTileImpl, com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleSetListening(boolean z) {
        super.handleSetListening(z);
        if (this.mListening == z) {
            return;
        }
        StringBuilder m49m = RowView$$ExternalSyntheticOutline0.m49m("handleSetListening  ", z, "  initialized=");
        m49m.append(this.mInitialized);
        m49m.append("  isTileReady=");
        m49m.append(isTileReady());
        m49m.append("  getTileSpec() = ");
        m49m.append(this.mTileSpec);
        Log.d(this.TAG, m49m.toString());
        this.mListening = z;
        boolean z2 = this.mIsSecActiveTile;
        TileServiceManager tileServiceManager = this.mServiceManager;
        if (z2) {
            tileServiceManager.mIsTileListening = z;
        }
        TileLifecycleManager tileLifecycleManager = this.mService;
        try {
            if (!z) {
                this.mViewClicked = null;
                tileLifecycleManager.onStopListening();
                if (this.mIsTokenGranted && !this.mIsShowingDialog) {
                    try {
                        IWindowManager iWindowManager = this.mWindowManager;
                        IBinder iBinder = this.mToken;
                        this.mDisplayTracker.getClass();
                        iWindowManager.removeWindowToken(iBinder, 0);
                    } catch (RemoteException unused) {
                    }
                    this.mIsTokenGranted = false;
                }
                this.mIsShowingDialog = false;
                tileServiceManager.setBindRequested(false);
                return;
            }
            updateDefaultTileAndIcon();
            refreshState(null);
            if (tileServiceManager.isActiveTile() && ((isTileReady() || this.mIsSecActiveTile) && this.mInitialized)) {
                return;
            }
            tileServiceManager.setBindRequested(true);
            tileLifecycleManager.onStartListening();
            this.mInitialized = true;
            if (this.mIsSecActiveTile) {
                tileLifecycleManager.refreshDetailInfo();
            }
        } catch (RemoteException unused2) {
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleUpdateState(QSTile.State state, Object obj) {
        final Drawable loadDrawable;
        Context context = this.mUserContext;
        Tile tile = this.mTile;
        int state2 = tile.getState();
        TileServiceManager tileServiceManager = this.mServiceManager;
        boolean z = tileServiceManager.mPendingBind && tileServiceManager.mStateManager.mHasPendingBind;
        String str = this.TAG;
        if (z) {
            Log.w(str, "handleUpdateState : hasPendingBind " + ((Object) state.label));
        }
        state.state = state2;
        state.dualTarget = true;
        String str2 = this.mTileSpec;
        if (this.mTileClassName == null && str2 != null) {
            String customTileNameFromSpec = this.mHost.getCustomTileNameFromSpec(str2);
            this.mTileClassName = customTileNameFromSpec;
            if (customTileNameFromSpec == null) {
                this.mTileClassName = this.mTileClassNameFromMetaData;
            }
        }
        if (this.mTileClassName == null && str2 != null) {
            this.mTileClassName = getComponentFromSpec(str2).getClassName();
        }
        state.tileClassName = this.mTileClassName;
        state.isCustomTile = true;
        try {
            loadDrawable = tile.getIcon().loadDrawable(context);
        } catch (Exception unused) {
            Log.w(str, "Invalid icon, forcing into unavailable state");
            state.state = 0;
            loadDrawable = this.mDefaultIcon.loadDrawable(context);
        }
        state.iconSupplier = new Supplier() { // from class: com.android.systemui.qs.external.CustomTile$$ExternalSyntheticLambda4
            @Override // java.util.function.Supplier
            public final Object get() {
                Drawable.ConstantState constantState;
                CustomTile customTile = CustomTile.this;
                Drawable drawable = loadDrawable;
                customTile.getClass();
                if (drawable == null || (constantState = drawable.getConstantState()) == null) {
                    return null;
                }
                if (customTile.mIsSecCustomTile) {
                    return new QSTileImpl.DrawableIcon(constantState.newDrawable());
                }
                customTile.mResourcePicker.getClass();
                Context context2 = customTile.mContext;
                ScalingDrawableWrapper scalingDrawableWrapper = new ScalingDrawableWrapper(drawable, context2.getResources().getFloat(R.dimen.qs_non_sec_customtile_icon_resize_ratio) * (SecQSPanelResourcePicker.getTileIconSize(context2) / drawable.getIntrinsicWidth()));
                scalingDrawableWrapper.mCloneDrawable = constantState.newDrawable();
                return new QSTileImpl.DrawableIcon(scalingDrawableWrapper, context2);
            }
        };
        state.label = tile.getLabel();
        CharSequence subtitle = tile.getSubtitle();
        if (subtitle == null || subtitle.length() <= 0) {
            state.secondaryLabel = null;
        } else {
            state.secondaryLabel = subtitle;
        }
        this.mIsTileStateActive = state.state == 2;
        if (tile.getContentDescription() != null) {
            state.contentDescription = tile.getContentDescription();
        } else {
            StringBuffer stringBuffer = new StringBuffer();
            String string = this.mContext.getString(this.mIsTileStateActive ? R.string.accessibility_desc_on : R.string.accessibility_desc_off);
            stringBuffer.append(state.label);
            stringBuffer.append(",");
            stringBuffer.append(string);
            stringBuffer.append(",");
            state.contentDescription = stringBuffer.toString();
        }
        if (tile.getStateDescription() != null) {
            state.stateDescription = tile.getStateDescription();
        } else {
            state.stateDescription = null;
        }
        if (state instanceof QSTile.BooleanState) {
            state.expandedAccessibilityClassName = Switch.class.getName();
            ((QSTile.BooleanState) state).value = state.state == 2;
        }
    }

    @Override // com.android.systemui.qs.tileimpl.SQSTileImpl, com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final boolean isAvailable() {
        if (!"OWNER".equals(this.mUserPolicy) || ((UserTrackerImpl) this.mUserTracker).getUserId() == 0) {
            if (!this.mHost.shouldBeHiddenByKnox(this.mTileSpec)) {
                return (this.mInitialDefaultIconFetched.get() && this.mDefaultIcon == null) ? false : true;
            }
        }
        Log.d(this.TAG, "isAvailable : return false , mComponent = " + this.mComponent + ", mUserPolicy = " + this.mUserPolicy);
        return false;
    }

    public final boolean isSecActiveTile() {
        int i;
        Bundle bundle = this.mMetaData;
        return (bundle == null || (i = bundle.getInt("android.service.quicksettings.SEM_ACTIVE_TILE_SUPPORT_SEM_PLATFORM_VER", 0)) == 0 || i > Build.VERSION.SEM_PLATFORM_INT) ? false : true;
    }

    public final boolean isSecCustomTile() {
        String str = "isSecCustomTile : mComponent =" + this.mComponent;
        String str2 = this.TAG;
        Log.d(str2, str);
        Bundle bundle = this.mMetaData;
        if (bundle == null) {
            return false;
        }
        String string = bundle.getString("android.service.quicksettings.SEM_DEFAULT_TILE_NAME", "");
        Log.d(str2, "isSecCustomTile : tileName =" + string);
        if ("".equals(string)) {
            return false;
        }
        this.mTileClassNameFromMetaData = string;
        return true;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final QSTile.State newTileState() {
        TileServiceManager tileServiceManager = this.mServiceManager;
        return (tileServiceManager == null || !tileServiceManager.isToggleableTile()) ? new QSTile.State() : new QSTile.BooleanState();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final LogMaker populate(LogMaker logMaker) {
        return super.populate(logMaker).setComponentName(this.mComponent);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void postStale() {
        if (this.mIsSecActiveTile) {
            return;
        }
        super.postStale();
    }

    public final boolean shouldUseArchivedDetailInfo() {
        if (this.mIsSecActiveTile) {
            return !(this.mServiceManager.mStateManager.mWrapper != null);
        }
        return false;
    }

    public final void startActivityAndCollapse(final PendingIntent pendingIntent) {
        boolean isActivity = pendingIntent.isActivity();
        String str = this.TAG;
        if (!isActivity) {
            Log.i(str, "Intent not for activity.");
            return;
        }
        if (!this.mIsTokenGranted && !this.mIsUnlockAndRun) {
            Log.i(str, "Launching activity before click");
            return;
        }
        Log.i(str, "The activity is starting");
        ((SQSTileImpl) this).mHandler.removeCallbacks(this.mStopUnlockAndRun);
        this.mIsUnlockAndRun = false;
        View view = this.mViewClicked;
        final GhostedViewLaunchAnimatorController fromView = view == null ? null : ActivityLaunchAnimator.Controller.fromView(view, 0);
        this.mUiHandler.post(new Runnable() { // from class: com.android.systemui.qs.external.CustomTile$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                CustomTile customTile = CustomTile.this;
                customTile.mActivityStarter.startPendingIntentDismissingKeyguard(pendingIntent, (Runnable) null, fromView);
            }
        });
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0022  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x002f A[Catch: NameNotFoundException -> 0x00ac, TryCatch #0 {NameNotFoundException -> 0x00ac, blocks: (B:3:0x0005, B:5:0x000f, B:9:0x001a, B:12:0x0027, B:14:0x002f, B:21:0x006e, B:22:0x0078, B:24:0x007c, B:25:0x007f, B:27:0x0085, B:29:0x0092, B:31:0x009a, B:32:0x009d, B:34:0x00a3, B:44:0x003e, B:46:0x0045, B:49:0x004c, B:52:0x0057, B:56:0x0023), top: B:2:0x0005 }] */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0069  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x006e A[Catch: NameNotFoundException -> 0x00ac, TryCatch #0 {NameNotFoundException -> 0x00ac, blocks: (B:3:0x0005, B:5:0x000f, B:9:0x001a, B:12:0x0027, B:14:0x002f, B:21:0x006e, B:22:0x0078, B:24:0x007c, B:25:0x007f, B:27:0x0085, B:29:0x0092, B:31:0x009a, B:32:0x009d, B:34:0x00a3, B:44:0x003e, B:46:0x0045, B:49:0x004c, B:52:0x0057, B:56:0x0023), top: B:2:0x0005 }] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x007c A[Catch: NameNotFoundException -> 0x00ac, TryCatch #0 {NameNotFoundException -> 0x00ac, blocks: (B:3:0x0005, B:5:0x000f, B:9:0x001a, B:12:0x0027, B:14:0x002f, B:21:0x006e, B:22:0x0078, B:24:0x007c, B:25:0x007f, B:27:0x0085, B:29:0x0092, B:31:0x009a, B:32:0x009d, B:34:0x00a3, B:44:0x003e, B:46:0x0045, B:49:0x004c, B:52:0x0057, B:56:0x0023), top: B:2:0x0005 }] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0085 A[Catch: NameNotFoundException -> 0x00ac, TryCatch #0 {NameNotFoundException -> 0x00ac, blocks: (B:3:0x0005, B:5:0x000f, B:9:0x001a, B:12:0x0027, B:14:0x002f, B:21:0x006e, B:22:0x0078, B:24:0x007c, B:25:0x007f, B:27:0x0085, B:29:0x0092, B:31:0x009a, B:32:0x009d, B:34:0x00a3, B:44:0x003e, B:46:0x0045, B:49:0x004c, B:52:0x0057, B:56:0x0023), top: B:2:0x0005 }] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x009a A[Catch: NameNotFoundException -> 0x00ac, TryCatch #0 {NameNotFoundException -> 0x00ac, blocks: (B:3:0x0005, B:5:0x000f, B:9:0x001a, B:12:0x0027, B:14:0x002f, B:21:0x006e, B:22:0x0078, B:24:0x007c, B:25:0x007f, B:27:0x0085, B:29:0x0092, B:31:0x009a, B:32:0x009d, B:34:0x00a3, B:44:0x003e, B:46:0x0045, B:49:0x004c, B:52:0x0057, B:56:0x0023), top: B:2:0x0005 }] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00a3 A[Catch: NameNotFoundException -> 0x00ac, TRY_LEAVE, TryCatch #0 {NameNotFoundException -> 0x00ac, blocks: (B:3:0x0005, B:5:0x000f, B:9:0x001a, B:12:0x0027, B:14:0x002f, B:21:0x006e, B:22:0x0078, B:24:0x007c, B:25:0x007f, B:27:0x0085, B:29:0x0092, B:31:0x009a, B:32:0x009d, B:34:0x00a3, B:44:0x003e, B:46:0x0045, B:49:0x004c, B:52:0x0057, B:56:0x0023), top: B:2:0x0005 }] */
    /* JADX WARN: Removed duplicated region for block: B:38:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0077  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0023 A[Catch: NameNotFoundException -> 0x00ac, TryCatch #0 {NameNotFoundException -> 0x00ac, blocks: (B:3:0x0005, B:5:0x000f, B:9:0x001a, B:12:0x0027, B:14:0x002f, B:21:0x006e, B:22:0x0078, B:24:0x007c, B:25:0x007f, B:27:0x0085, B:29:0x0092, B:31:0x009a, B:32:0x009d, B:34:0x00a3, B:44:0x003e, B:46:0x0045, B:49:0x004c, B:52:0x0057, B:56:0x0023), top: B:2:0x0005 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateDefaultTileAndIcon() {
        int i;
        int i2;
        boolean z;
        boolean z2;
        boolean z3;
        ComponentName componentName = this.mComponent;
        Tile tile = this.mTile;
        try {
            PackageManager packageManager = this.mUserContext.getPackageManager();
            if (!this.mIsSystemApp && !this.mIsSecCustomTile) {
                i = 4980736;
                ServiceInfo serviceInfo = packageManager.getServiceInfo(componentName, i);
                i2 = serviceInfo.icon;
                if (i2 != 0) {
                    i2 = serviceInfo.applicationInfo.icon;
                }
                if (tile.getIcon() != null) {
                    Icon icon = tile.getIcon();
                    Icon icon2 = this.mDefaultIcon;
                    if (icon == icon2 || (icon != null && icon2 != null && icon.getType() == 2 && icon2.getType() == 2 && icon.getResId() == icon2.getResId() && Objects.equals(icon.getResPackage(), icon2.getResPackage()))) {
                        z3 = true;
                        if (z3) {
                            z = false;
                            Icon createWithResource = i2 != 0 ? Icon.createWithResource(componentName.getPackageName(), i2) : null;
                            this.mDefaultIcon = createWithResource;
                            if (z) {
                                tile.setIcon(createWithResource);
                            }
                            z2 = tile.getLabel() != null || TextUtils.equals(tile.getLabel(), this.mDefaultLabel);
                            CharSequence loadLabel = serviceInfo.loadLabel(packageManager);
                            this.mDefaultLabel = loadLabel;
                            if (z2) {
                                tile.setLabel(loadLabel);
                            }
                            if (tile.getLabel() != null) {
                                this.mSearchTitle = this.mDefaultLabel.toString();
                                return;
                            }
                            return;
                        }
                    }
                    z3 = false;
                    if (z3) {
                    }
                }
                z = true;
                if (i2 != 0) {
                }
                this.mDefaultIcon = createWithResource;
                if (z) {
                }
                if (tile.getLabel() != null) {
                }
                CharSequence loadLabel2 = serviceInfo.loadLabel(packageManager);
                this.mDefaultLabel = loadLabel2;
                if (z2) {
                }
                if (tile.getLabel() != null) {
                }
            }
            i = 4981248;
            ServiceInfo serviceInfo2 = packageManager.getServiceInfo(componentName, i);
            i2 = serviceInfo2.icon;
            if (i2 != 0) {
            }
            if (tile.getIcon() != null) {
            }
            z = true;
            if (i2 != 0) {
            }
            this.mDefaultIcon = createWithResource;
            if (z) {
            }
            if (tile.getLabel() != null) {
            }
            CharSequence loadLabel22 = serviceInfo2.loadLabel(packageManager);
            this.mDefaultLabel = loadLabel22;
            if (z2) {
            }
            if (tile.getLabel() != null) {
            }
        } catch (PackageManager.NameNotFoundException unused) {
            this.mDefaultIcon = null;
            this.mDefaultLabel = null;
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(14:0|1|(2:2|3)|(18:5|6|7|8|9|(1:11)(1:51)|12|(1:14)|15|107|27|(1:29)|30|(1:32)|33|(1:35)|36|(2:38|(1:44)(2:41|42))(1:46))|55|6|7|8|9|(0)(0)|12|(0)|15|107) */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x00b2, code lost:
    
        r6 = null;
     */
    /* JADX WARN: Removed duplicated region for block: B:11:0x00bf  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x00fe  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0108 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00c6  */
    /* JADX WARN: Type inference failed for: r0v5, types: [com.android.systemui.qs.external.CustomTile$2] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private CustomTile(QSHost qSHost, QsEventLogger qsEventLogger, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, String str, Context context, CustomTileStatePersister customTileStatePersister, TileServices tileServices, DisplayTracker displayTracker, UserTracker userTracker, BroadcastDispatcher broadcastDispatcher, DisplayLifecycle displayLifecycle) {
        super(qSHost, qsEventLogger, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        boolean z;
        TileServiceManager tileServiceManager;
        ApplicationInfo applicationInfo;
        this.mToken = new Binder();
        this.mTileState = -1;
        this.mUnlockPolicy = "";
        this.mUserPolicy = "";
        this.mToggleEnabled = true;
        this.mInitialDefaultIconFetched = new AtomicBoolean(false);
        this.mStopUnlockAndRun = new Runnable() { // from class: com.android.systemui.qs.external.CustomTile.2
            @Override // java.lang.Runnable
            public final void run() {
                CustomTile customTile = CustomTile.this;
                customTile.mIsUnlockAndRun = false;
                Log.d(customTile.TAG, "mStopUnlockAndRun");
            }
        };
        this.mTileServices = tileServices;
        this.mWindowManager = WindowManagerGlobal.getWindowManagerService();
        ComponentName unflattenFromString = ComponentName.unflattenFromString(str);
        this.mComponent = unflattenFromString;
        this.mTile = new Tile();
        this.mUserContext = context;
        int userId = context.getUserId();
        this.mUser = userId;
        this.mKey = new TileServiceKey(unflattenFromString, userId);
        this.mResourcePicker = (SecQSPanelResourcePicker) Dependency.get(SecQSPanelResourcePicker.class);
        this.mUserTracker = userTracker;
        PackageManager packageManager = this.mContext.getPackageManager();
        String str2 = this.TAG;
        try {
            applicationInfo = packageManager.getApplicationInfo(unflattenFromString.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            Log.d(str2, "isSystemApp NameNotFoundException : " + e);
        } catch (RuntimeException e2) {
            Log.d(str2, "isSystemApp RuntimeException : " + e2);
        }
        if (applicationInfo != null) {
            z = applicationInfo.isSystemApp();
            this.mIsSystemApp = z;
            Bundle bundle = this.mContext.getPackageManager().getServiceInfo(this.mComponent, 787072).metaData;
            this.mMetaData = bundle;
            this.mIsSecCustomTile = isSecCustomTile();
            Bundle bundle2 = this.mMetaData;
            this.mIsSupportDetailView = bundle2 == null ? bundle2.getBoolean("android.service.quicksettings.SEM_SUPPORT_DETAIL_VIEW", false) : false;
            this.mIsSecActiveTile = isSecActiveTile();
            tileServices.getClass();
            ComponentName componentName = this.mComponent;
            int i = this.mUser;
            TileServiceManager tileServiceManager2 = new TileServiceManager(tileServices, (Handler) tileServices.mHandlerProvider.get(), componentName, tileServices.mBroadcastDispatcher, tileServices.mUserTracker, tileServices.mCustomTileAddedRepository, tileServices.mBackgroundExecutor);
            if (TileServices.DEBUG) {
                AbstractC0147x487e7be7.m27m("getTileWrapper ", componentName, "TileServices");
            }
            synchronized (tileServices.mServices) {
                CustomTile customTile = (CustomTile) tileServices.mTiles.get(i, componentName);
                if (customTile != null && (tileServiceManager = (TileServiceManager) tileServices.mServices.get(customTile)) != null) {
                    tileServiceManager.setBindAllowed(false);
                    tileServiceManager.handleDestroy();
                    tileServices.mServices.remove(customTile);
                    tileServices.mTokenMap.remove(tileServiceManager.mStateManager.mToken);
                }
                tileServices.mServices.put(this, tileServiceManager2);
                tileServices.mTiles.add(i, componentName, this);
                tileServices.mTokenMap.put(tileServiceManager2.mStateManager.mToken, this);
                if (!tileServices.mUninstallReceiverRegistered) {
                    IntentFilter intentFilter = new IntentFilter();
                    intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
                    intentFilter.addDataScheme("package");
                    tileServices.mBroadcastDispatcher.registerReceiver(tileServices.mUninstallReceiver, intentFilter, null, ((UserTrackerImpl) tileServices.mUserTracker).getUserHandle());
                    tileServices.mUninstallReceiverRegistered = true;
                }
            }
            tileServiceManager2.mStarted = true;
            TileLifecycleManager tileLifecycleManager = tileServiceManager2.mStateManager;
            ComponentName component = tileLifecycleManager.getComponent();
            int identifier = tileLifecycleManager.mUser.getIdentifier();
            CustomTileAddedRepository customTileAddedRepository = tileServiceManager2.mCustomTileAddedRepository;
            if (!customTileAddedRepository.isTileAdded(identifier, component)) {
                customTileAddedRepository.setTileAdded(component, true, identifier);
                tileLifecycleManager.onTileAdded();
                ((ExecutorImpl) tileLifecycleManager.mExecutor).execute(new TileLifecycleManager$$ExternalSyntheticLambda1(tileLifecycleManager, 2));
            }
            this.mServiceManager = tileServiceManager2;
            TileLifecycleManager tileLifecycleManager2 = tileServiceManager2.mStateManager;
            this.mService = tileLifecycleManager2;
            this.mCustomTileStatePersister = customTileStatePersister;
            this.mDisplayTracker = displayTracker;
            this.mDetailAdapter = new CustomDetailAdapter(tileLifecycleManager2);
            tileServiceManager2.mIsSecCustomTile = this.mIsSecCustomTile;
            Bundle bundle3 = this.mMetaData;
            if (bundle3 != null) {
                this.mUnlockPolicy = bundle3.getString("android.service.quicksettings.SEM_DEFAULT_TILE_UNLOCK_POLICY", "");
            }
            Bundle bundle4 = this.mMetaData;
            if (bundle4 != null) {
                this.mUserPolicy = bundle4.getString("android.service.quicksettings.SEM_DEFAULT_TILE_USER_POLICY", "");
            }
            if (QpRune.QUICK_PANEL_SUBSCREEN) {
                this.mDisplayLifecycle = displayLifecycle;
                this.mBroadcastDispatcher = broadcastDispatcher;
                if (this.mSubscreenCustomTileReceiver != null || broadcastDispatcher == null) {
                    return;
                }
                String str3 = "com.android.systemui.qs.external.customTile.unlock." + this.mComponent.getShortClassName();
                this.mIntentAction = str3;
                SubscreenCustomTileReceiver subscreenCustomTileReceiver = new SubscreenCustomTileReceiver();
                this.mSubscreenCustomTileReceiver = subscreenCustomTileReceiver;
                broadcastDispatcher.registerReceiver(subscreenCustomTileReceiver, new IntentFilter(str3), null, null, 2, null);
                return;
            }
            return;
        }
        z = false;
        this.mIsSystemApp = z;
        Bundle bundle5 = this.mContext.getPackageManager().getServiceInfo(this.mComponent, 787072).metaData;
        this.mMetaData = bundle5;
        this.mIsSecCustomTile = isSecCustomTile();
        Bundle bundle22 = this.mMetaData;
        this.mIsSupportDetailView = bundle22 == null ? bundle22.getBoolean("android.service.quicksettings.SEM_SUPPORT_DETAIL_VIEW", false) : false;
        this.mIsSecActiveTile = isSecActiveTile();
        tileServices.getClass();
        ComponentName componentName2 = this.mComponent;
        int i2 = this.mUser;
        TileServiceManager tileServiceManager22 = new TileServiceManager(tileServices, (Handler) tileServices.mHandlerProvider.get(), componentName2, tileServices.mBroadcastDispatcher, tileServices.mUserTracker, tileServices.mCustomTileAddedRepository, tileServices.mBackgroundExecutor);
        if (TileServices.DEBUG) {
        }
        synchronized (tileServices.mServices) {
        }
    }
}
