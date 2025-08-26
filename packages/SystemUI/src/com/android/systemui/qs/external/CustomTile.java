package com.android.systemui.qs.external;

import android.app.IUriGrantsManager;
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
import android.util.Log;
import android.view.IWindowManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManagerGlobal;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RemoteViews;
import android.widget.Switch;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.internal.logging.MetricsLogger;
import com.android.keyguard.SecurityUtils$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.animation.Expandable;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.knox.EdmMonitor;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.DetailAdapter;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qp.flashlight.SubscreenFlashLightController;
import com.android.systemui.qp.util.SubscreenUtil;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.external.CustomTile;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.pipeline.data.repository.TileNameConverter;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.qs.tileimpl.SQSTileImpl;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.ScalingDrawableWrapper;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import dagger.Lazy;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;
import java.util.function.Supplier;
import org.json.JSONException;

/* loaded from: classes2.dex */
public class CustomTile extends SQSTileImpl implements CustomTileInterface {
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
    public Expandable mExpandableClicked;
    public final IUriGrantsManager mIUriGrantsManager;
    public final AtomicBoolean mInitialDefaultIconFetched;
    public boolean mInitialized;
    public final String mIntentAction;
    public boolean mIsSecActiveTile;
    public boolean mIsSecCustomTile;
    public boolean mIsShowingDialog;
    public boolean mIsSupportDetailView;
    public final boolean mIsSystemApp;
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
    public int mServiceUid;
    public Intent mSettingsIntent;
    public final AnonymousClass2 mStopUnlockAndRun;
    public SubscreenCustomTileReceiver mSubscreenCustomTileReceiver;
    public final Tile mTile;
    public String mTileClassName;
    public String mTileClassNameFromMetaData;
    public final TileServices mTileServices;
    public int mTileState;
    public boolean mToggleEnabled;
    public final IBinder mToken;
    public final String mUnlockPolicy;
    public final int mUser;
    public final Context mUserContext;
    public final String mUserPolicy;
    public UserTracker mUserTracker;
    public final IWindowManager mWindowManager;

    public class CustomDetailAdapter implements DetailAdapter {
        public static final /* synthetic */ int $r8$clinit = 0;
        public final AnonymousClass1 mInteractionHandler = new RemoteViews.InteractionHandler() { // from class: com.android.systemui.qs.external.CustomTile.CustomDetailAdapter.1
            public final boolean onInteraction(View view, PendingIntent pendingIntent, RemoteViews.RemoteResponse remoteResponse) {
                boolean zIsActivity = pendingIntent.isActivity();
                CustomTile customTile = CustomTile.this;
                String str = customTile.TAG;
                if (!zIsActivity) {
                    return RemoteViews.startPendingIntent(view, pendingIntent, remoteResponse.getLaunchOptions(view));
                }
                customTile.showDetail$1(false);
                ((ActivityStarter) Dependency.sDependency.getDependencyInner(ActivityStarter.class)).postStartActivityDismissingKeyguard(pendingIntent);
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
            RemoteViews remoteViewsSemGetDetailView;
            if (this.mService != null) {
                CustomTile customTile = CustomTile.this;
                if (customTile.mIsSupportDetailView) {
                    try {
                        boolean zShouldUseArchivedDetailInfo = customTile.shouldUseArchivedDetailInfo();
                        TileServiceManager tileServiceManager = customTile.mServiceManager;
                        if (zShouldUseArchivedDetailInfo) {
                            tileServiceManager.setBindRequested(true);
                            this.mService.onStartListening();
                            remoteViewsSemGetDetailView = customTile.mDetailView;
                        } else {
                            remoteViewsSemGetDetailView = this.mService.semGetDetailView();
                            if (customTile.mIsSecActiveTile) {
                                customTile.mDetailView = remoteViewsSemGetDetailView;
                                tileServiceManager.setBindRequested(true);
                                this.mService.onStartListening();
                            }
                        }
                        Log.d(customTile.TAG, "getDetailView remoteViews = " + remoteViewsSemGetDetailView);
                        if (remoteViewsSemGetDetailView != null) {
                            FrameLayout frameLayout = new FrameLayout(context);
                            frameLayout.addView(remoteViewsSemGetDetailView.apply(context, frameLayout, this.mInteractionHandler, null));
                            return frameLayout;
                        }
                    } catch (RemoteException unused) {
                    }
                }
            }
            return null;
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public final int getMetricsCategory() {
            return 268;
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public final Intent getSettingsIntent() {
            if (this.mService == null) {
                return null;
            }
            CustomTile customTile = CustomTile.this;
            if (customTile.shouldUseArchivedDetailInfo()) {
                return customTile.mSettingsIntent;
            }
            try {
                Intent intentSemGetSettingsIntent = this.mService.semGetSettingsIntent();
                if (customTile.mIsSecActiveTile) {
                    customTile.mSettingsIntent = intentSemGetSettingsIntent;
                }
                return intentSemGetSettingsIntent;
            } catch (RemoteException unused) {
                return null;
            }
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public final CharSequence getTitle() {
            if (this.mService == null) {
                return null;
            }
            CustomTile customTile = CustomTile.this;
            if (customTile.shouldUseArchivedDetailInfo()) {
                return customTile.mDetailViewTitle;
            }
            try {
                CharSequence charSequenceSemGetDetailViewTitle = this.mService.semGetDetailViewTitle();
                if (customTile.mIsSecActiveTile) {
                    customTile.mDetailViewTitle = charSequenceSemGetDetailViewTitle;
                }
                return charSequenceSemGetDetailViewTitle;
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
            if (this.mService == null) {
                return null;
            }
            CustomTile customTile = CustomTile.this;
            if (!customTile.shouldUseArchivedDetailInfo()) {
                try {
                    boolean zSemIsToggleButtonExists = this.mService.semIsToggleButtonExists();
                    if (customTile.mIsSecActiveTile) {
                        customTile.mIsToggleButtonExist = zSemIsToggleButtonExists;
                    }
                    if (zSemIsToggleButtonExists) {
                        return Boolean.valueOf(this.mService.semIsToggleButtonChecked());
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
            CustomTile customTile = CustomTile.this;
            String str = customTile.TAG;
            TileServiceManager tileServiceManager = customTile.mServiceManager;
            ExifInterface$$ExternalSyntheticOutline0.m(RowView$$ExternalSyntheticOutline0.m("setToggleState  ", "getTileSpec() = ", z), customTile.mTileSpec, str);
            if (this.mService == null || toggleState == null) {
                return;
            }
            EdmMonitor edmMonitor = ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).mEdmMonitor;
            String str2 = customTile.TAG;
            if (edmMonitor != null && !edmMonitor.mSettingsChangesAllowed) {
                customTile.showItPolicyToast();
                Log.d(str2, "setToggleState blocked");
                customTile.fireToggleStateChanged(toggleState.booleanValue());
                return;
            }
            if ((customTile.mUnlockPolicy.equals("ALL") || ((customTile.mUnlockPolicy.equals("ON") && z) || (customTile.mUnlockPolicy.equals("OFF") && !z))) && ((KeyguardStateControllerImpl) ((KeyguardStateController) Dependency.sDependency.getDependencyInner(KeyguardStateController.class))).mShowing && ((KeyguardStateControllerImpl) ((KeyguardStateController) Dependency.sDependency.getDependencyInner(KeyguardStateController.class))).mSecure && !((KeyguardStateControllerImpl) ((KeyguardStateController) Dependency.sDependency.getDependencyInner(KeyguardStateController.class))).mCanDismissLockScreen && ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isLockFunctionsEnabled()) {
                ((ActivityStarter) Dependency.sDependency.getDependencyInner(ActivityStarter.class)).postQSRunnableDismissingKeyguard(new CustomTile$$ExternalSyntheticLambda0(1, this, toggleState));
                customTile.fireToggleStateChanged(toggleState.booleanValue());
                return;
            }
            try {
                if (tileServiceManager.mStateManager.isActiveTile()) {
                    tileServiceManager.setBindRequested(true);
                    this.mService.onStartListening();
                }
                Log.d(str2, "setToggleState state = " + z);
                this.mService.semSetToggleButtonChecked(z);
            } catch (RemoteException unused) {
            }
            customTile.fireToggleStateChanged(z);
        }
    }

    public class SubscreenCustomTileReceiver extends BroadcastReceiver {
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

    enum SubscreenSALog {
        /* JADX INFO: Fake field, exist only in values array */
        SUBSCREEN_SCREENRECORDER_TILE("com.samsung.android.app.smartcapture", SystemUIAnalytics.EID_QP_SCREENRECORDER_COVER),
        /* JADX INFO: Fake field, exist only in values array */
        SUBSCREEN_MODES_TILE("com.samsung.android.app.routines", SystemUIAnalytics.EID_QP_MODES_COVER);

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

    /* JADX WARN: Type inference failed for: r14v4, types: [com.android.systemui.qs.external.CustomTile$2] */
    public CustomTile(Lazy lazy, QsEventLogger qsEventLogger, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, String str, Context context, CustomTileStatePersister customTileStatePersister, TileServices tileServices, DisplayTracker displayTracker, IUriGrantsManager iUriGrantsManager, UserTracker userTracker, BroadcastDispatcher broadcastDispatcher, DisplayLifecycle displayLifecycle) throws PackageManager.NameNotFoundException {
        Bundle bundle;
        ApplicationInfo applicationInfo;
        super((QSHost) lazy.get(), qsEventLogger, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.mToken = new Binder();
        this.mTileState = -1;
        this.mUnlockPolicy = "";
        this.mUserPolicy = "";
        this.mToggleEnabled = true;
        this.mInitialDefaultIconFetched = new AtomicBoolean(false);
        this.mServiceUid = -1;
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
        ComponentName componentNameUnflattenFromString = ComponentName.unflattenFromString(str);
        this.mComponent = componentNameUnflattenFromString;
        this.mTile = new Tile();
        this.mUserContext = context;
        int userId = context.getUserId();
        this.mUser = userId;
        this.mKey = new TileServiceKey(componentNameUnflattenFromString, userId);
        this.mResourcePicker = (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);
        this.mUserTracker = userTracker;
        PackageManager packageManager = this.mContext.getPackageManager();
        String str2 = this.TAG;
        try {
            applicationInfo = packageManager.getApplicationInfo(componentNameUnflattenFromString.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            Log.d(str2, "isSystemApp NameNotFoundException : " + e);
        } catch (RuntimeException e2) {
            Log.d(str2, "isSystemApp RuntimeException : " + e2);
        }
        boolean zIsSystemApp = applicationInfo != null ? applicationInfo.isSystemApp() : false;
        this.mIsSystemApp = zIsSystemApp;
        try {
            bundle = this.mContext.getPackageManager().getServiceInfo(this.mComponent, 787072).metaData;
        } catch (PackageManager.NameNotFoundException unused) {
            bundle = null;
        }
        this.mMetaData = bundle;
        this.mIsSecCustomTile = isSecCustomTile();
        Bundle bundle2 = this.mMetaData;
        this.mIsSupportDetailView = bundle2 != null ? bundle2.getBoolean("android.service.quicksettings.SEM_SUPPORT_DETAIL_VIEW", false) : false;
        this.mIsSecActiveTile = isSecActiveTile();
        TileServiceManager tileWrapper = tileServices.getTileWrapper(this);
        this.mServiceManager = tileWrapper;
        TileLifecycleManager tileLifecycleManager = tileWrapper.mStateManager;
        this.mService = tileLifecycleManager;
        this.mCustomTileStatePersister = customTileStatePersister;
        this.mDisplayTracker = displayTracker;
        this.mIUriGrantsManager = iUriGrantsManager;
        if (this.mIsSupportDetailView) {
            this.mDetailAdapter = new CustomDetailAdapter(tileLifecycleManager);
        } else {
            this.mDetailAdapter = null;
        }
        tileWrapper.mIsSecCustomTile = this.mIsSecCustomTile;
        Bundle bundle3 = this.mMetaData;
        if (bundle3 != null) {
            this.mUnlockPolicy = bundle3.getString("android.service.quicksettings.SEM_DEFAULT_TILE_UNLOCK_POLICY", "");
        }
        Bundle bundle4 = this.mMetaData;
        if (bundle4 != null) {
            this.mUserPolicy = bundle4.getString("android.service.quicksettings.SEM_DEFAULT_TILE_USER_POLICY", "");
        }
        if (QpRune.QUICK_SUBSCREEN_PANEL) {
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
        }
    }

    public static ComponentName getComponentFromSpec(String str) {
        String strSubstring = str.substring(7, str.length() - 1);
        if (strSubstring.isEmpty()) {
            throw new IllegalArgumentException("Empty custom tile spec action");
        }
        return ComponentName.unflattenFromString(strSubstring);
    }

    public static String toSpec(ComponentName componentName) {
        return "custom(" + componentName.flattenToShortString() + ")";
    }

    public final void applyTileState(Tile tile, boolean z) {
        if (tile.getIcon() != null || z) {
            this.mTile.setIcon(tile.getIcon());
        }
        if (tile.getCustomLabel() != null || z) {
            this.mTile.setLabel(tile.getCustomLabel());
        }
        if (tile.getSubtitle() != null || z) {
            this.mTile.setSubtitle(tile.getSubtitle());
        }
        if (tile.getContentDescription() != null || z) {
            this.mTile.setContentDescription(tile.getContentDescription());
        }
        if (tile.getStateDescription() != null || z) {
            this.mTile.setStateDescription(tile.getStateDescription());
        }
        this.mTile.setActivityLaunchForClick(tile.getActivityLaunchForClick());
        boolean zIsSecCustomTile = isSecCustomTile();
        TileLifecycleManager tileLifecycleManager = this.mService;
        String str = this.TAG;
        if (!zIsSecCustomTile || isSecActiveTile()) {
            this.mTile.setState(tile.getState());
            boolean zContains = this.mContext.getResources().getString(R.string.quick_settings_allow_settings_intent_updates).contains(this.mComponent.flattenToShortString());
            if (isSecActiveTile() && zContains && tileLifecycleManager != null) {
                try {
                    this.mSettingsIntent = tileLifecycleManager.semGetSettingsIntent();
                } catch (RemoteException unused) {
                }
            }
        } else {
            StringBuilder sb = new StringBuilder("NonActiveTile ");
            sb.append((Object) tile.getLabel());
            sb.append("  allow=");
            Bundle bundle = this.mMetaData;
            sb.append(bundle != null ? bundle.getBoolean("android.service.quicksettings.SEM_PERMISSION_NON_ACTIVE", false) : false);
            sb.append("  ");
            sb.append(this.mTileClassName);
            Log.i(str, sb.toString());
            Bundle bundle2 = this.mMetaData;
            if (bundle2 != null ? bundle2.getBoolean("android.service.quicksettings.SEM_PERMISSION_NON_ACTIVE", false) : false) {
                this.mTile.setState(tile.getState());
            } else {
                this.mTile.setState(0);
            }
        }
        Log.d(str, "updateState : Label = " + ((Object) tile.getLabel()) + ", State = " + tile.getState() + ", Icon = " + tile.getIcon());
        if (this.mIsSecCustomTile) {
            if (!this.mListening) {
                try {
                    this.mListening = false;
                    if (tileLifecycleManager != null) {
                        tileLifecycleManager.onStopListening();
                    }
                    TileServiceManager tileServiceManager = this.mServiceManager;
                    if (tileServiceManager != null) {
                        tileServiceManager.setBindRequested(false);
                    }
                } catch (RemoteException unused2) {
                }
            }
            if (this.mTileState != this.mTile.getState()) {
                this.mTileState = this.mTile.getState();
            }
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println(getClass().getSimpleName().concat(":"));
        printWriter.print("    ");
        printWriter.println("SecTile=" + isSecCustomTile() + " SecActiveTile=" + isSecActiveTile() + " supportDetail=" + this.mIsSupportDetailView + " user=" + this.mUser);
        printWriter.print("    ");
        StringBuilder sb = new StringBuilder("listeners size : ");
        sb.append(this.mListeners.size());
        sb.append("  ");
        sb.append(this.mListeners.toString());
        printWriter.println(sb.toString());
        printWriter.print("    ");
        printWriter.println(getState().toString());
    }

    @Override // com.android.systemui.qs.external.CustomTileInterface
    public final ComponentName getComponent() {
        return this.mComponent;
    }

    @Override // com.android.systemui.qs.tileimpl.SQSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final DetailAdapter getDetailAdapter() {
        CustomDetailAdapter customDetailAdapter;
        if (!this.mIsSupportDetailView || (customDetailAdapter = this.mDetailAdapter) == null) {
            return null;
        }
        if (!shouldUseArchivedDetailInfo()) {
            try {
                TileLifecycleManager tileLifecycleManager = this.mService;
                if (tileLifecycleManager == null) {
                    return null;
                }
                if (tileLifecycleManager.semGetDetailView() == null) {
                    return null;
                }
            } catch (RemoteException unused) {
                return null;
            }
        } else if (this.mDetailView == null) {
            return null;
        }
        return customDetailAdapter;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final Intent getLongClickIntent() {
        Intent intentSemGetSettingsIntent;
        Intent className;
        if (!this.mIsSupportDetailView) {
            TileLifecycleManager tileLifecycleManager = this.mService;
            if (tileLifecycleManager != null) {
                try {
                    if (shouldUseArchivedDetailInfo()) {
                        intentSemGetSettingsIntent = this.mSettingsIntent;
                    } else {
                        intentSemGetSettingsIntent = tileLifecycleManager.semGetSettingsIntent();
                        try {
                            if (this.mIsSecActiveTile) {
                                this.mSettingsIntent = intentSemGetSettingsIntent;
                            }
                        } catch (RemoteException unused) {
                        }
                    }
                } catch (RemoteException unused2) {
                    intentSemGetSettingsIntent = null;
                }
                if (intentSemGetSettingsIntent != null) {
                    return intentSemGetSettingsIntent;
                }
            }
            if (!this.mIsSecCustomTile) {
                Intent intent = new Intent("android.service.quicksettings.action.QS_TILE_PREFERENCES");
                intent.setPackage(this.mComponent.getPackageName());
                ResolveInfo resolveInfoResolveActivityAsUser = this.mContext.getPackageManager().resolveActivityAsUser(intent, 0, this.mUser);
                if (resolveInfoResolveActivityAsUser != null) {
                    Intent intent2 = new Intent("android.service.quicksettings.action.QS_TILE_PREFERENCES");
                    ActivityInfo activityInfo = resolveInfoResolveActivityAsUser.activityInfo;
                    className = intent2.setClassName(activityInfo.packageName, activityInfo.name);
                } else {
                    className = null;
                }
                if (className == null) {
                    return new Intent("android.settings.APPLICATION_DETAILS_SETTINGS").setData(Uri.fromParts("package", this.mComponent.getPackageName(), null));
                }
                className.putExtra("android.intent.extra.COMPONENT_NAME", this.mComponent);
                className.putExtra("state", this.mTile.getState());
                return className;
            }
        }
        return null;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final int getMetricsCategory() {
        return 268;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final String getMetricsSpec() {
        return this.mComponent.getPackageName();
    }

    @Override // com.android.systemui.qs.external.CustomTileInterface
    public final Tile getQsTile() throws PackageManager.NameNotFoundException {
        updateDefaultTileAndIcon();
        return this.mTile;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final String getSearchTitle() {
        if (getState().label != null) {
            return getState().label.toString().replaceAll(System.getProperty("line.separator"), " ").trim();
        }
        String str = this.mSearchTitle;
        if (str != null) {
            return str.replaceAll(System.getProperty("line.separator"), " ").trim();
        }
        return null;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final ArrayList getSearchWords() {
        try {
            Bundle bundle = this.mContext.getPackageManager().getServiceInfo(this.mComponent, 787072).metaData;
            if (bundle != null) {
                String string = bundle.getString("android.service.quicksettings.SEM_DEFAULT_TILE_SEARCH_KEYWORDS", "");
                if (!"".equals(string)) {
                    ArrayList arrayList = new ArrayList();
                    for (String str : string.split(";")) {
                        Resources resources = this.mContext.createPackageContext(this.mComponent.getPackageName(), 0).getResources();
                        int identifier = resources.getIdentifier(str, "string", this.mComponent.getPackageName());
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

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.LockQSTile
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

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final CharSequence getTileLabel() {
        return getState().label;
    }

    @Override // com.android.systemui.qs.external.CustomTileInterface
    public final int getUser() {
        return this.mUser;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleClick(Expandable expandable) {
        if (this.mTile.getState() == 0) {
            return;
        }
        TileServiceManager tileServiceManager = this.mServiceManager;
        if (tileServiceManager.hasPendingBind()) {
            Log.w(this.TAG, TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder("handleClick : "), this.mTileClassName, " hasPendingBind"));
            return;
        }
        this.mExpandableClicked = expandable;
        try {
            IWindowManager iWindowManager = this.mWindowManager;
            IBinder iBinder = this.mToken;
            this.mDisplayTracker.getClass();
            iWindowManager.addWindowToken(iBinder, 2035, 0, (Bundle) null);
            this.mIsTokenGranted = true;
        } catch (RemoteException unused) {
        }
        try {
            boolean zIsActiveTile = tileServiceManager.mStateManager.isActiveTile();
            TileLifecycleManager tileLifecycleManager = this.mService;
            if (zIsActiveTile) {
                tileServiceManager.setBindRequested(true);
                tileLifecycleManager.onStartListening();
            }
            boolean z = QpRune.QUICK_SUBSCREEN_PANEL;
            if (z) {
                DisplayLifecycle displayLifecycle = this.mDisplayLifecycle;
                if (!(displayLifecycle != null ? displayLifecycle.mIsFolderOpened : false)) {
                    SubscreenUtil subscreenUtil = (SubscreenUtil) Dependency.sDependency.getDependencyInner(SubscreenUtil.class);
                    Context context = this.mContext;
                    subscreenUtil.getClass();
                    SubscreenFlashLightController.getInstance(context).finishFlashLightActivity();
                    ((SubscreenUtil) Dependency.sDependency.getDependencyInner(SubscreenUtil.class)).closeSubscreenPanel();
                    this.mUiHandler.post(new CustomTile$$ExternalSyntheticLambda6());
                }
            }
            if (this.mTile.getActivityLaunchForClick() != null) {
                startActivityAndCollapse(this.mTile.getActivityLaunchForClick());
            } else {
                tileLifecycleManager.onClick(this.mToken);
            }
            if (z) {
                DisplayLifecycle displayLifecycle2 = this.mDisplayLifecycle;
                if (displayLifecycle2 != null ? displayLifecycle2.mIsFolderOpened : false) {
                    return;
                }
                final String resPackage = this.mTile.getIcon().getResPackage();
                Arrays.stream(SubscreenSALog.values()).filter(new Predicate() { // from class: com.android.systemui.qs.external.CustomTile$$ExternalSyntheticLambda7
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((CustomTile.SubscreenSALog) obj).hasSamePackageName(resPackage);
                    }
                }).findFirst().ifPresent(new CustomTile$$ExternalSyntheticLambda8());
            }
        } catch (RemoteException unused2) {
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleDestroy() {
        SubscreenCustomTileReceiver subscreenCustomTileReceiver;
        BroadcastDispatcher broadcastDispatcher;
        super.handleDestroy();
        if (this.mIsTokenGranted) {
            try {
                IWindowManager iWindowManager = this.mWindowManager;
                IBinder iBinder = this.mToken;
                this.mDisplayTracker.getClass();
                iWindowManager.removeWindowToken(iBinder, 0);
            } catch (RemoteException unused) {
            }
        }
        this.mTileServices.freeService(this, this.mServiceManager);
        if (!QpRune.QUICK_SUBSCREEN_PANEL || (subscreenCustomTileReceiver = this.mSubscreenCustomTileReceiver) == null || (broadcastDispatcher = this.mBroadcastDispatcher) == null) {
            return;
        }
        broadcastDispatcher.unregisterReceiver(subscreenCustomTileReceiver);
        this.mSubscreenCustomTileReceiver = null;
    }

    @Override // com.android.systemui.qs.tileimpl.SQSTileImpl, com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleInitialize() throws PackageManager.NameNotFoundException {
        Tile tileFromString;
        updateDefaultTileAndIcon();
        boolean zCompareAndSet = this.mInitialDefaultIconFetched.compareAndSet(false, true);
        SQSTileImpl.SHandler sHandler = ((SQSTileImpl) this).mHandler;
        if (zCompareAndSet && this.mDefaultIcon == null) {
            Log.w(this.TAG, TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder("No default icon for "), this.mTileSpec, ", destroying tile"));
            sHandler.postDelayed(new Runnable() { // from class: com.android.systemui.qs.external.CustomTile.1
                @Override // java.lang.Runnable
                public final void run() {
                    CustomTile customTile = CustomTile.this;
                    customTile.mHost.removeTile(customTile.mTileSpec);
                }
            }, 1000L);
        }
        TileServiceManager tileServiceManager = this.mServiceManager;
        if (tileServiceManager.isToggleableTile()) {
            this.mState = newTileState();
            QSTile.State stateNewTileState = newTileState();
            this.mTmpState = stateNewTileState;
            QSTile.State state = this.mState;
            String str = this.mTileSpec;
            state.spec = str;
            stateNewTileState.spec = str;
        }
        TileLifecycleManager tileLifecycleManager = tileServiceManager.mStateManager;
        tileLifecycleManager.mChangeListener = this;
        if (tileLifecycleManager.isActiveTile()) {
            String string = ((CustomTileStatePersisterImpl) this.mCustomTileStatePersister).sharedPreferences.getString(this.mKey.string, null);
            if (string == null) {
                tileFromString = null;
            } else {
                try {
                    tileFromString = CustomTileStatePersisterKt.readTileFromString(string);
                } catch (JSONException e) {
                    Log.e("TileServicePersistence", "Bad saved state: ".concat(string), e);
                }
            }
            if (tileFromString != null) {
                applyTileState(tileFromString, false);
                tileServiceManager.mPendingBind = false;
                refreshState(null);
            }
            if (this.mIsSecActiveTile) {
                sHandler.post(new CustomTile$$ExternalSyntheticLambda1(this, 3));
            }
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleSetListening(boolean z) throws PackageManager.NameNotFoundException {
        super.handleSetListening(z);
        if (this.mListening == z) {
            return;
        }
        StringBuilder sbM = RowView$$ExternalSyntheticOutline0.m("handleSetListening  ", "  initialized=", z);
        sbM.append(this.mInitialized);
        sbM.append("  isTileReady=");
        sbM.append(isTileReady());
        sbM.append("  getTileSpec() = ");
        sbM.append(this.mTileSpec);
        Log.d(this.TAG, sbM.toString());
        this.mListening = z;
        boolean z2 = this.mIsSecActiveTile;
        TileServiceManager tileServiceManager = this.mServiceManager;
        if (z2) {
            tileServiceManager.mIsTileListening = z;
        }
        TileLifecycleManager tileLifecycleManager = this.mService;
        try {
            if (!z) {
                this.mExpandableClicked = null;
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
            if (tileServiceManager.mStateManager.isActiveTile() && ((isTileReady() || this.mIsSecActiveTile) && this.mInitialized)) {
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
        final Drawable drawableLoadDrawable;
        int state2 = this.mTile.getState();
        boolean zHasPendingBind = this.mServiceManager.hasPendingBind();
        String str = this.TAG;
        if (zHasPendingBind) {
            Log.w(str, "handleUpdateState : hasPendingBind " + ((Object) state.label));
        }
        state.state = state2;
        state.dualTarget = true;
        String str2 = this.mTileSpec;
        if (this.mTileClassName == null) {
            this.mTileClassName = this.mTileClassNameFromMetaData;
        }
        if (this.mTileClassName == null && str2 != null) {
            this.mTileClassName = getComponentFromSpec(str2).getClassName();
        }
        state.tileClassName = this.mTileClassName;
        state.isCustomTile = true;
        try {
            drawableLoadDrawable = this.mTile.getIcon().loadDrawableCheckingUriGrant(this.mUserContext, this.mIUriGrantsManager, this.mServiceUid, this.mComponent.getPackageName());
        } catch (Exception unused) {
            Log.w(str, "Invalid icon, forcing into unavailable state");
            state.state = 0;
            drawableLoadDrawable = null;
        }
        if (drawableLoadDrawable == null) {
            Icon icon = this.mDefaultIcon;
            drawableLoadDrawable = icon != null ? icon.loadDrawable(this.mUserContext) : null;
        }
        state.iconSupplier = new Supplier() { // from class: com.android.systemui.qs.external.CustomTile$$ExternalSyntheticLambda3
            @Override // java.util.function.Supplier
            public final Object get() {
                Drawable.ConstantState constantState;
                CustomTile customTile = this.f$0;
                Drawable drawable = drawableLoadDrawable;
                customTile.getClass();
                if (drawable == null || (constantState = drawable.getConstantState()) == null) {
                    return null;
                }
                if (customTile.mIsSecCustomTile) {
                    return new QSTileImpl.DrawableIcon(constantState.newDrawable());
                }
                ScalingDrawableWrapper scalingDrawableWrapper = new ScalingDrawableWrapper(drawable, SecurityUtils$$ExternalSyntheticOutline0.m(customTile.mContext, R.dimen.qs_non_sec_customtile_icon_resize_ratio, customTile.mResourcePicker.getTileIconSize(customTile.mContext) / drawable.getIntrinsicWidth()));
                scalingDrawableWrapper.mCloneDrawable = constantState.newDrawable();
                return new QSTileImpl.DrawableIcon(scalingDrawableWrapper, customTile.mContext);
            }
        };
        state.label = this.mTile.getLabel();
        CharSequence subtitle = this.mTile.getSubtitle();
        if (subtitle == null || subtitle.length() <= 0) {
            state.secondaryLabel = null;
        } else {
            state.secondaryLabel = subtitle;
        }
        if (this.mTile.getContentDescription() != null) {
            state.contentDescription = this.mTile.getContentDescription();
        } else {
            state.contentDescription = state.label;
        }
        if (this.mTile.getStateDescription() != null) {
            state.stateDescription = this.mTile.getStateDescription();
        } else {
            state.stateDescription = null;
        }
        if (!(state instanceof QSTile.BooleanState)) {
            state.expandedAccessibilityClassName = Button.class.getName();
            return;
        }
        state.expandedAccessibilityClassName = Switch.class.getName();
        ((QSTile.BooleanState) state).value = state.state == 2;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final boolean isAvailable() {
        if (!"OWNER".equals(this.mUserPolicy) || ((UserTrackerImpl) this.mUserTracker).getUserId() == 0) {
            return (this.mInitialDefaultIconFetched.get() && this.mDefaultIcon == null) ? false : true;
        }
        Log.d(this.TAG, "isAvailable : return false , mComponent = " + this.mComponent + ", mUserPolicy = " + this.mUserPolicy);
        return false;
    }

    @Override // com.android.systemui.qs.external.CustomTileInterface
    public final boolean isInitialized() {
        return this.mInitialized;
    }

    @Override // com.android.systemui.qs.external.CustomTileInterface
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

    @Override // com.android.systemui.qs.external.CustomTileInterface
    public final void lazyInitialize() {
        Objects.toString(this.mComponent);
        if (this.mInitialized) {
            return;
        }
        ((SQSTileImpl) this).mHandler.post(new CustomTile$$ExternalSyntheticLambda1(this, 4));
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final QSTile.State newTileState() {
        TileServiceManager tileServiceManager = this.mServiceManager;
        return (tileServiceManager == null || !tileServiceManager.isToggleableTile()) ? new QSTile.State() : new QSTile.BooleanState();
    }

    @Override // com.android.systemui.qs.external.CustomTileInterface
    public final void onDialogHidden() {
        this.mIsShowingDialog = false;
        try {
            IWindowManager iWindowManager = this.mWindowManager;
            IBinder iBinder = this.mToken;
            this.mDisplayTracker.getClass();
            iWindowManager.removeWindowToken(iBinder, 0);
        } catch (RemoteException unused) {
        }
    }

    @Override // com.android.systemui.qs.external.CustomTileInterface
    public final void onDialogShown() {
        this.mIsShowingDialog = true;
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

    @Override // com.android.systemui.qs.external.CustomTileInterface
    public final void refreshDetailInfo() {
        TileLifecycleManager tileLifecycleManager = this.mService;
        if (tileLifecycleManager == null || !this.mIsSecActiveTile) {
            return;
        }
        Objects.toString(this.mComponent);
        try {
            this.mSettingsIntent = tileLifecycleManager.semGetSettingsIntent();
            Bundle bundle = this.mMetaData;
            if (bundle != null ? bundle.getBoolean("android.service.quicksettings.SEM_SUPPORT_DETAIL_VIEW", false) : false) {
                this.mDetailView = tileLifecycleManager.semGetDetailView();
                this.mDetailViewTitle = tileLifecycleManager.semGetDetailViewTitle();
                this.mIsToggleButtonExist = tileLifecycleManager.semIsToggleButtonExists();
            }
        } catch (RemoteException unused) {
        }
    }

    @Override // com.android.systemui.qs.external.CustomTileInterface
    public final void refreshMetaInfo() {
        Bundle bundle;
        Log.d(this.TAG, "refreshMetaInfo");
        try {
            bundle = this.mContext.getPackageManager().getServiceInfo(this.mComponent, 787072).metaData;
        } catch (PackageManager.NameNotFoundException unused) {
            bundle = null;
        }
        this.mMetaData = bundle;
        this.mIsSecCustomTile = isSecCustomTile();
        Bundle bundle2 = this.mMetaData;
        this.mIsSupportDetailView = bundle2 != null ? bundle2.getBoolean("android.service.quicksettings.SEM_SUPPORT_DETAIL_VIEW", false) : false;
        this.mIsSecActiveTile = isSecActiveTile();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final void setTileSpec(String str) {
        super.setTileSpec(str);
        TileNameConverter tileNameConverter = TileNameConverter.INSTANCE;
        Resources resources = this.mContext.getResources();
        tileNameConverter.getClass();
        String tileLoggingName = TileNameConverter.toTileLoggingName(resources, str);
        this.mTileClassName = tileLoggingName;
        this.mState.tileClassName = tileLoggingName;
    }

    @Override // com.android.systemui.qs.external.CustomTileInterface
    public final void setToggleEnabledState(boolean z) {
        this.mToggleEnabled = z;
    }

    public final boolean shouldUseArchivedDetailInfo() {
        return this.mIsSecActiveTile && !this.mServiceManager.mStateManager.mOptionalWrapper.isPresent();
    }

    @Override // com.android.systemui.qs.external.CustomTileInterface
    public final void startActivityAndCollapse(PendingIntent pendingIntent) {
        boolean zIsActivity = pendingIntent.isActivity();
        String str = this.TAG;
        if (!zIsActivity) {
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
        Expandable expandable = this.mExpandableClicked;
        this.mActivityStarter.startPendingIntentMaybeDismissingKeyguard(pendingIntent, null, expandable == null ? null : expandable.activityTransitionController(32));
    }

    @Override // com.android.systemui.qs.external.CustomTileInterface
    public final void startUnlockAndRun() {
        if (QpRune.QUICK_SUBSCREEN_PANEL) {
            DisplayLifecycle displayLifecycle = this.mDisplayLifecycle;
            if (!(displayLifecycle != null ? displayLifecycle.mIsFolderOpened : false)) {
                ((SubscreenUtil) Dependency.sDependency.getDependencyInner(SubscreenUtil.class)).showLockscreenOnCoverScreen(this.mContext, this.mIntentAction);
                return;
            }
        }
        this.mIsUnlockAndRun = true;
        this.mActivityStarter.postQSRunnableDismissingKeyguard(new CustomTile$$ExternalSyntheticLambda1(this, 0));
    }

    public final void updateDefaultTileAndIcon() throws PackageManager.NameNotFoundException {
        try {
            PackageManager packageManager = this.mUserContext.getPackageManager();
            ServiceInfo serviceInfo = packageManager.getServiceInfo(this.mComponent, (this.mIsSystemApp || this.mIsSecCustomTile) ? 4981248 : 4980736);
            int i = serviceInfo.icon;
            if (i == 0) {
                i = serviceInfo.applicationInfo.icon;
            }
            boolean z = true;
            if (this.mTile.getIcon() != null) {
                Icon icon = this.mTile.getIcon();
                Icon icon2 = this.mDefaultIcon;
                if (!(icon == icon2 || (icon != null && icon2 != null && icon.getType() == 2 && icon2.getType() == 2 && icon.getResId() == icon2.getResId() && Objects.equals(icon.getResPackage(), icon2.getResPackage())))) {
                    z = false;
                }
            }
            Icon iconCreateWithResource = i != 0 ? Icon.createWithResource(this.mComponent.getPackageName(), i) : null;
            this.mDefaultIcon = iconCreateWithResource;
            if (z) {
                this.mTile.setIcon(iconCreateWithResource);
            }
            CharSequence charSequenceLoadLabel = serviceInfo.loadLabel(packageManager);
            this.mDefaultLabel = charSequenceLoadLabel;
            this.mTile.setDefaultLabel(charSequenceLoadLabel);
            if (this.mTile.getLabel() != null) {
                this.mSearchTitle = this.mDefaultLabel.toString();
            }
        } catch (PackageManager.NameNotFoundException unused) {
            this.mDefaultIcon = null;
            this.mDefaultLabel = null;
        }
    }

    @Override // com.android.systemui.qs.external.CustomTileInterface
    public final void updateTileState(Tile tile, int i) {
        this.mServiceUid = i;
        ((SQSTileImpl) this).mHandler.post(new CustomTile$$ExternalSyntheticLambda0(0, this, tile));
    }
}
