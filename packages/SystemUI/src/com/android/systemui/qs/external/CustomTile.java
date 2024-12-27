package com.android.systemui.qs.external;

import android.app.IUriGrantsManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.metrics.LogMaker;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.service.quicksettings.IQSTileService;
import android.service.quicksettings.Tile;
import android.util.Log;
import android.view.IWindowManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RemoteViews;
import android.widget.Switch;
import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
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
import com.android.systemui.plugins.qs.DetailAdapter;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qp.flashlight.SubscreenFlashLightController;
import com.android.systemui.qp.util.SubscreenUtil;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.external.CustomTile;
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
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;
import java.util.function.Supplier;

public final class CustomTile extends SQSTileImpl implements CustomTileInterface {
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
    public final UserTracker mUserTracker;
    public final IWindowManager mWindowManager;

    public final class CustomDetailAdapter implements DetailAdapter {
        public final AnonymousClass1 mInteractionHandler = new RemoteViews.InteractionHandler() { // from class: com.android.systemui.qs.external.CustomTile.CustomDetailAdapter.1
            public final boolean onInteraction(View view, PendingIntent pendingIntent, RemoteViews.RemoteResponse remoteResponse) {
                boolean isActivity = pendingIntent.isActivity();
                CustomTile customTile = CustomTile.this;
                String str = customTile.TAG;
                if (!isActivity) {
                    return RemoteViews.startPendingIntent(view, pendingIntent, remoteResponse.getLaunchOptions(view));
                }
                customTile.showDetail(false);
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
            RemoteViews semGetDetailView;
            if (this.mService == null) {
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
                    this.mService.onStartListening();
                    semGetDetailView = customTile.mDetailView;
                } else {
                    semGetDetailView = this.mService.semGetDetailView();
                    if (customTile.mIsSecActiveTile) {
                        customTile.mDetailView = semGetDetailView;
                        tileServiceManager.setBindRequested(true);
                        this.mService.onStartListening();
                    }
                }
                Log.d(customTile.TAG, "getDetailView remoteViews = " + semGetDetailView);
                if (semGetDetailView == null) {
                    return null;
                }
                FrameLayout frameLayout = new FrameLayout(context);
                frameLayout.addView(semGetDetailView.apply(context, frameLayout, this.mInteractionHandler, null));
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
            if (this.mService == null) {
                return null;
            }
            CustomTile customTile = CustomTile.this;
            if (customTile.shouldUseArchivedDetailInfo()) {
                return customTile.mSettingsIntent;
            }
            try {
                Intent semGetSettingsIntent = this.mService.semGetSettingsIntent();
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
            if (this.mService == null) {
                return null;
            }
            CustomTile customTile = CustomTile.this;
            if (customTile.shouldUseArchivedDetailInfo()) {
                return customTile.mDetailViewTitle;
            }
            try {
                CharSequence semGetDetailViewTitle = this.mService.semGetDetailViewTitle();
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
            if (this.mService == null) {
                return null;
            }
            CustomTile customTile = CustomTile.this;
            if (!customTile.shouldUseArchivedDetailInfo()) {
                try {
                    boolean semIsToggleButtonExists = this.mService.semIsToggleButtonExists();
                    if (customTile.mIsSecActiveTile) {
                        customTile.mIsToggleButtonExist = semIsToggleButtonExists;
                    }
                    if (semIsToggleButtonExists) {
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
            if (edmMonitor != null && (!edmMonitor.mSettingsChangesAllowed)) {
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
                if (tileServiceManager.isActiveTile()) {
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

    /* JADX WARN: Can't wrap try/catch for region: R(19:0|1|2|3|(14:5|6|7|8|9|(1:11)|12|(1:14)(1:32)|15|(1:17)|18|(1:20)|21|(2:23|(1:29)(2:26|27))(1:31))|36|6|7|8|9|(0)|12|(0)(0)|15|(0)|18|(0)|21|(0)(0)) */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x00d0, code lost:
    
        r2 = null;
     */
    /* JADX WARN: Removed duplicated region for block: B:11:0x00dd  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0105  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0117  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0123  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x012f  */
    /* JADX WARN: Removed duplicated region for block: B:31:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:32:0x010d  */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.systemui.qs.external.CustomTile$2] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public CustomTile(dagger.Lazy r15, com.android.systemui.qs.QsEventLogger r16, android.os.Looper r17, android.os.Handler r18, com.android.systemui.plugins.FalsingManager r19, com.android.internal.logging.MetricsLogger r20, com.android.systemui.plugins.statusbar.StatusBarStateController r21, com.android.systemui.plugins.ActivityStarter r22, com.android.systemui.qs.logging.QSLogger r23, java.lang.String r24, android.content.Context r25, com.android.systemui.qs.external.CustomTileStatePersister r26, com.android.systemui.qs.external.TileServices r27, com.android.systemui.settings.DisplayTracker r28, android.app.IUriGrantsManager r29, com.android.systemui.settings.UserTracker r30, com.android.systemui.broadcast.BroadcastDispatcher r31, com.android.systemui.keyguard.DisplayLifecycle r32) {
        /*
            Method dump skipped, instructions count: 370
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.external.CustomTile.<init>(dagger.Lazy, com.android.systemui.qs.QsEventLogger, android.os.Looper, android.os.Handler, com.android.systemui.plugins.FalsingManager, com.android.internal.logging.MetricsLogger, com.android.systemui.plugins.statusbar.StatusBarStateController, com.android.systemui.plugins.ActivityStarter, com.android.systemui.qs.logging.QSLogger, java.lang.String, android.content.Context, com.android.systemui.qs.external.CustomTileStatePersister, com.android.systemui.qs.external.TileServices, com.android.systemui.settings.DisplayTracker, android.app.IUriGrantsManager, com.android.systemui.settings.UserTracker, com.android.systemui.broadcast.BroadcastDispatcher, com.android.systemui.keyguard.DisplayLifecycle):void");
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

    public final boolean allowNonActive() {
        Bundle bundle = this.mMetaData;
        return (bundle != null ? bundle.getBoolean("android.service.quicksettings.SEM_PERMISSION_NON_ACTIVE", false) : false) || Arrays.asList(this.mUserContext.getString(R.string.allow_non_active_tiles).split(",")).contains(this.mTileClassName);
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
        boolean isSecCustomTile = isSecCustomTile();
        String str = this.TAG;
        if (!isSecCustomTile || isSecActiveTile()) {
            this.mTile.setState(tile.getState());
        } else {
            Log.i(str, "NonActiveTile " + ((Object) tile.getLabel()) + "  allow=" + allowNonActive() + "  " + this.mTileClassName);
            if (allowNonActive()) {
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
            if (this.mTileState != this.mTile.getState()) {
                this.mTileState = this.mTile.getState();
            }
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("CustomTile:");
        printWriter.print("    ");
        printWriter.println("SecTile=" + isSecCustomTile() + " SecActiveTile=" + isSecActiveTile() + " supportDetail=" + this.mIsSupportDetailView);
        printWriter.print("    ");
        StringBuilder sb = new StringBuilder("listeners size : ");
        sb.append(this.mListeners.size());
        sb.append("  ");
        sb.append(this.mListeners.toString());
        printWriter.println(sb.toString());
        printWriter.print("    ");
        printWriter.println(this.mState.toString());
    }

    @Override // com.android.systemui.qs.external.CustomTileInterface
    public final ComponentName getComponent() {
        return this.mComponent;
    }

    @Override // com.android.systemui.qs.tileimpl.SQSTileImpl, com.android.systemui.plugins.qs.QSTile
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
        intent3.setPackage(this.mComponent.getPackageName());
        ResolveInfo resolveActivityAsUser = this.mContext.getPackageManager().resolveActivityAsUser(intent3, 0, this.mUser);
        if (resolveActivityAsUser != null) {
            Intent intent4 = new Intent("android.service.quicksettings.action.QS_TILE_PREFERENCES");
            ActivityInfo activityInfo = resolveActivityAsUser.activityInfo;
            intent2 = intent4.setClassName(activityInfo.packageName, activityInfo.name);
        } else {
            intent2 = null;
        }
        if (intent2 == null) {
            return new Intent("android.settings.APPLICATION_DETAILS_SETTINGS").setData(Uri.fromParts("package", this.mComponent.getPackageName(), null));
        }
        intent2.putExtra("android.intent.extra.COMPONENT_NAME", this.mComponent);
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

    @Override // com.android.systemui.qs.external.CustomTileInterface
    public final Tile getQsTile() {
        updateDefaultTileAndIcon();
        return this.mTile;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
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
        return this.mState.label;
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
            Log.w(this.TAG, ComponentActivity$1$$ExternalSyntheticOutline0.m(new StringBuilder("handleClick : "), this.mTileClassName, " hasPendingBind"));
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
            boolean isActiveTile = tileServiceManager.isActiveTile();
            TileLifecycleManager tileLifecycleManager = this.mService;
            if (isActiveTile) {
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

    /* JADX WARN: Removed duplicated region for block: B:15:0x007e  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x008a  */
    /* JADX WARN: Removed duplicated region for block: B:21:? A[RETURN, SYNTHETIC] */
    @Override // com.android.systemui.qs.tileimpl.SQSTileImpl, com.android.systemui.qs.tileimpl.QSTileImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void handleInitialize() {
        /*
            r7 = this;
            r7.updateDefaultTileAndIcon()
            java.util.concurrent.atomic.AtomicBoolean r0 = r7.mInitialDefaultIconFetched
            r1 = 0
            r2 = 1
            boolean r0 = r0.compareAndSet(r1, r2)
            com.android.systemui.qs.tileimpl.SQSTileImpl$SHandler r2 = r7.mHandler
            if (r0 == 0) goto L31
            android.graphics.drawable.Icon r0 = r7.mDefaultIcon
            if (r0 != 0) goto L31
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r3 = "No default icon for "
            r0.<init>(r3)
            java.lang.String r3 = r7.mTileSpec
            java.lang.String r4 = ", destroying tile"
            java.lang.String r0 = androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0.m(r0, r3, r4)
            java.lang.String r3 = r7.TAG
            android.util.Log.w(r3, r0)
            com.android.systemui.qs.external.CustomTile$1 r0 = new com.android.systemui.qs.external.CustomTile$1
            r0.<init>()
            r3 = 1000(0x3e8, double:4.94E-321)
            r2.postDelayed(r0, r3)
        L31:
            com.android.systemui.qs.external.TileServiceManager r0 = r7.mServiceManager
            boolean r3 = r0.isToggleableTile()
            if (r3 == 0) goto L4d
            com.android.systemui.plugins.qs.QSTile$State r3 = r7.newTileState()
            r7.mState = r3
            com.android.systemui.plugins.qs.QSTile$State r3 = r7.newTileState()
            r7.mTmpState = r3
            com.android.systemui.plugins.qs.QSTile$State r4 = r7.mState
            java.lang.String r5 = r7.mTileSpec
            r4.spec = r5
            r3.spec = r5
        L4d:
            com.android.systemui.qs.external.TileLifecycleManager r3 = r0.mStateManager
            r3.mChangeListener = r7
            boolean r3 = r0.isActiveTile()
            if (r3 == 0) goto L93
            com.android.systemui.qs.external.CustomTileStatePersister r3 = r7.mCustomTileStatePersister
            com.android.systemui.qs.external.CustomTileStatePersisterImpl r3 = (com.android.systemui.qs.external.CustomTileStatePersisterImpl) r3
            android.content.SharedPreferences r3 = r3.sharedPreferences
            com.android.systemui.qs.external.TileServiceKey r4 = r7.mKey
            java.lang.String r4 = r4.string
            r5 = 0
            java.lang.String r3 = r3.getString(r4, r5)
            if (r3 != 0) goto L6a
        L68:
            r3 = r5
            goto L7c
        L6a:
            android.service.quicksettings.Tile r3 = com.android.systemui.qs.external.CustomTileStatePersisterKt.readTileFromString(r3)     // Catch: org.json.JSONException -> L6f
            goto L7c
        L6f:
            r4 = move-exception
            java.lang.String r6 = "Bad saved state: "
            java.lang.String r3 = r6.concat(r3)
            java.lang.String r6 = "TileServicePersistence"
            android.util.Log.e(r6, r3, r4)
            goto L68
        L7c:
            if (r3 == 0) goto L86
            r7.applyTileState(r3, r1)
            r0.mPendingBind = r1
            r7.refreshState(r5)
        L86:
            boolean r0 = r7.mIsSecActiveTile
            if (r0 == 0) goto L93
            com.android.systemui.qs.external.CustomTile$$ExternalSyntheticLambda1 r0 = new com.android.systemui.qs.external.CustomTile$$ExternalSyntheticLambda1
            r1 = 3
            r0.<init>(r7, r1)
            r2.post(r0)
        L93:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.external.CustomTile.handleInitialize():void");
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleSetListening(boolean z) {
        super.handleSetListening(z);
        if (this.mListening == z) {
            return;
        }
        StringBuilder m = RowView$$ExternalSyntheticOutline0.m("handleSetListening  ", "  initialized=", z);
        m.append(this.mInitialized);
        m.append("  isTileReady=");
        m.append(isTileReady());
        m.append("  getTileSpec() = ");
        m.append(this.mTileSpec);
        Log.d(this.TAG, m.toString());
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
        final Drawable drawable;
        int state2 = this.mTile.getState();
        boolean hasPendingBind = this.mServiceManager.hasPendingBind();
        String str = this.TAG;
        if (hasPendingBind) {
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
            drawable = this.mTile.getIcon().loadDrawableCheckingUriGrant(this.mUserContext, this.mIUriGrantsManager, this.mServiceUid, this.mComponent.getPackageName());
        } catch (Exception unused) {
            Log.w(str, "Invalid icon, forcing into unavailable state");
            state.state = 0;
            drawable = null;
        }
        if (drawable == null) {
            Icon icon = this.mDefaultIcon;
            drawable = icon != null ? icon.loadDrawable(this.mUserContext) : null;
        }
        state.iconSupplier = new Supplier() { // from class: com.android.systemui.qs.external.CustomTile$$ExternalSyntheticLambda3
            @Override // java.util.function.Supplier
            public final Object get() {
                Drawable.ConstantState constantState;
                CustomTile customTile = CustomTile.this;
                Drawable drawable2 = drawable;
                customTile.getClass();
                if (drawable2 == null || (constantState = drawable2.getConstantState()) == null) {
                    return null;
                }
                if (customTile.mIsSecCustomTile) {
                    return new QSTileImpl.DrawableIcon(constantState.newDrawable());
                }
                ScalingDrawableWrapper scalingDrawableWrapper = new ScalingDrawableWrapper(drawable2, SecurityUtils$$ExternalSyntheticOutline0.m(customTile.mContext, R.dimen.qs_non_sec_customtile_icon_resize_ratio, customTile.mResourcePicker.getTileIconSize(customTile.mContext) / drawable2.getIntrinsicWidth()));
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

    @Override // com.android.systemui.qs.tileimpl.SQSTileImpl, com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final boolean isAvailable() {
        if (!"OWNER".equals(this.mUserPolicy) || ((UserTrackerImpl) this.mUserTracker).getUserId() == 0) {
            if (!this.mHost.shouldBeHiddenByKnox(this.mTileSpec)) {
                return (this.mInitialDefaultIconFetched.get() && this.mDefaultIcon == null) ? false : true;
            }
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
        if (this.mInitialized) {
            return;
        }
        Objects.toString(this.mComponent);
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

    @Override // com.android.systemui.qs.external.CustomTileInterface
    public final void setToggleEnabledState(boolean z) {
        this.mToggleEnabled = z;
    }

    public final boolean shouldUseArchivedDetailInfo() {
        return this.mIsSecActiveTile && !this.mServiceManager.mStateManager.mOptionalWrapper.isPresent();
    }

    @Override // com.android.systemui.qs.external.CustomTileInterface
    public final void startActivityAndCollapse(PendingIntent pendingIntent) {
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

    /* JADX WARN: Code restructure failed: missing block: B:29:0x0062, code lost:
    
        if (java.util.Objects.equals(r4.getResPackage(), r5.getResPackage()) == false) goto L32;
     */
    /* JADX WARN: Removed duplicated region for block: B:11:0x0020  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x002d A[Catch: NameNotFoundException -> 0x009a, TryCatch #0 {NameNotFoundException -> 0x009a, blocks: (B:3:0x0001, B:5:0x000b, B:9:0x0016, B:12:0x0025, B:14:0x002d, B:20:0x003d, B:22:0x0044, B:25:0x004b, B:28:0x0056, B:32:0x0069, B:33:0x0075, B:35:0x0079, B:36:0x007e, B:38:0x0091, B:45:0x0021), top: B:2:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0069 A[Catch: NameNotFoundException -> 0x009a, TryCatch #0 {NameNotFoundException -> 0x009a, blocks: (B:3:0x0001, B:5:0x000b, B:9:0x0016, B:12:0x0025, B:14:0x002d, B:20:0x003d, B:22:0x0044, B:25:0x004b, B:28:0x0056, B:32:0x0069, B:33:0x0075, B:35:0x0079, B:36:0x007e, B:38:0x0091, B:45:0x0021), top: B:2:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0079 A[Catch: NameNotFoundException -> 0x009a, TryCatch #0 {NameNotFoundException -> 0x009a, blocks: (B:3:0x0001, B:5:0x000b, B:9:0x0016, B:12:0x0025, B:14:0x002d, B:20:0x003d, B:22:0x0044, B:25:0x004b, B:28:0x0056, B:32:0x0069, B:33:0x0075, B:35:0x0079, B:36:0x007e, B:38:0x0091, B:45:0x0021), top: B:2:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0091 A[Catch: NameNotFoundException -> 0x009a, TRY_LEAVE, TryCatch #0 {NameNotFoundException -> 0x009a, blocks: (B:3:0x0001, B:5:0x000b, B:9:0x0016, B:12:0x0025, B:14:0x002d, B:20:0x003d, B:22:0x0044, B:25:0x004b, B:28:0x0056, B:32:0x0069, B:33:0x0075, B:35:0x0079, B:36:0x007e, B:38:0x0091, B:45:0x0021), top: B:2:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:42:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0074  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0021 A[Catch: NameNotFoundException -> 0x009a, TryCatch #0 {NameNotFoundException -> 0x009a, blocks: (B:3:0x0001, B:5:0x000b, B:9:0x0016, B:12:0x0025, B:14:0x002d, B:20:0x003d, B:22:0x0044, B:25:0x004b, B:28:0x0056, B:32:0x0069, B:33:0x0075, B:35:0x0079, B:36:0x007e, B:38:0x0091, B:45:0x0021), top: B:2:0x0001 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateDefaultTileAndIcon() {
        /*
            r8 = this;
            r0 = 0
            android.content.Context r1 = r8.mUserContext     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L9a
            android.content.pm.PackageManager r1 = r1.getPackageManager()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L9a
            boolean r2 = r8.mIsSystemApp     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L9a
            if (r2 != 0) goto L13
            boolean r2 = r8.mIsSecCustomTile     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L9a
            if (r2 == 0) goto L10
            goto L13
        L10:
            r2 = 4980736(0x4c0000, float:6.979498E-39)
            goto L16
        L13:
            r2 = 4981248(0x4c0200, float:6.980215E-39)
        L16:
            android.content.ComponentName r3 = r8.mComponent     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L9a
            android.content.pm.ServiceInfo r2 = r1.getServiceInfo(r3, r2)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L9a
            int r3 = r2.icon     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L9a
            if (r3 == 0) goto L21
            goto L25
        L21:
            android.content.pm.ApplicationInfo r3 = r2.applicationInfo     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L9a
            int r3 = r3.icon     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L9a
        L25:
            android.service.quicksettings.Tile r4 = r8.mTile     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L9a
            android.graphics.drawable.Icon r4 = r4.getIcon()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L9a
            if (r4 == 0) goto L66
            android.service.quicksettings.Tile r4 = r8.mTile     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L9a
            android.graphics.drawable.Icon r4 = r4.getIcon()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L9a
            android.graphics.drawable.Icon r5 = r8.mDefaultIcon     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L9a
            if (r4 != r5) goto L38
            goto L66
        L38:
            if (r4 == 0) goto L64
            if (r5 != 0) goto L3d
            goto L64
        L3d:
            int r6 = r4.getType()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L9a
            r7 = 2
            if (r6 != r7) goto L64
            int r6 = r5.getType()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L9a
            if (r6 == r7) goto L4b
            goto L64
        L4b:
            int r6 = r4.getResId()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L9a
            int r7 = r5.getResId()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L9a
            if (r6 == r7) goto L56
            goto L64
        L56:
            java.lang.String r4 = r4.getResPackage()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L9a
            java.lang.String r5 = r5.getResPackage()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L9a
            boolean r4 = java.util.Objects.equals(r4, r5)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L9a
            if (r4 != 0) goto L66
        L64:
            r4 = 0
            goto L67
        L66:
            r4 = 1
        L67:
            if (r3 == 0) goto L74
            android.content.ComponentName r5 = r8.mComponent     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L9a
            java.lang.String r5 = r5.getPackageName()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L9a
            android.graphics.drawable.Icon r3 = android.graphics.drawable.Icon.createWithResource(r5, r3)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L9a
            goto L75
        L74:
            r3 = r0
        L75:
            r8.mDefaultIcon = r3     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L9a
            if (r4 == 0) goto L7e
            android.service.quicksettings.Tile r4 = r8.mTile     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L9a
            r4.setIcon(r3)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L9a
        L7e:
            java.lang.CharSequence r1 = r2.loadLabel(r1)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L9a
            r8.mDefaultLabel = r1     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L9a
            android.service.quicksettings.Tile r2 = r8.mTile     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L9a
            r2.setDefaultLabel(r1)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L9a
            android.service.quicksettings.Tile r1 = r8.mTile     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L9a
            java.lang.CharSequence r1 = r1.getLabel()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L9a
            if (r1 == 0) goto L9e
            java.lang.CharSequence r1 = r8.mDefaultLabel     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L9a
            java.lang.String r1 = r1.toString()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L9a
            r8.mSearchTitle = r1     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L9a
            goto L9e
        L9a:
            r8.mDefaultIcon = r0
            r8.mDefaultLabel = r0
        L9e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.external.CustomTile.updateDefaultTileAndIcon():void");
    }

    @Override // com.android.systemui.qs.external.CustomTileInterface
    public final void updateTileState(Tile tile, int i) {
        this.mServiceUid = i;
        ((SQSTileImpl) this).mHandler.post(new CustomTile$$ExternalSyntheticLambda0(0, this, tile));
    }
}
