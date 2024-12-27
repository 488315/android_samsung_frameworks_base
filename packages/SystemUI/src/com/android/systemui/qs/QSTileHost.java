package com.android.systemui.qs;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.content.res.Resources;
import android.hardware.display.DisplayManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.os.SemSystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;
import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.compose.ui.text.input.EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.keyguard.CarrierTextController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.settingslib.SecNotificationBlockManager$$ExternalSyntheticOutline0;
import com.android.systemui.BootAnimationFinishedCache;
import com.android.systemui.BootAnimationFinishedCacheImpl;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.Flags;
import com.android.systemui.Operator;
import com.android.systemui.Prefs;
import com.android.systemui.ProtoDumpable;
import com.android.systemui.R;
import com.android.systemui.ScRune;
import com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0;
import com.android.systemui.dump.nano.SystemUIProtoDump;
import com.android.systemui.knox.CustomSdkMonitor;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorCallback;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.pluginlock.component.PluginLockShortcutTask;
import com.android.systemui.plugins.Plugin;
import com.android.systemui.plugins.PluginListener;
import com.android.systemui.plugins.PluginManager;
import com.android.systemui.plugins.qs.QSFactory;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.QSBackupRestoreManager;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.external.CustomTile;
import com.android.systemui.qs.external.CustomTileStatePersister;
import com.android.systemui.qs.external.CustomTileStatePersisterImpl;
import com.android.systemui.qs.external.TileLifecycleManager;
import com.android.systemui.qs.external.TileLifecycleManager$$ExternalSyntheticLambda0;
import com.android.systemui.qs.external.TileServiceKey;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.nano.QsTileState;
import com.android.systemui.qs.pipeline.data.repository.CustomTileAddedRepository;
import com.android.systemui.qs.pipeline.domain.interactor.PanelInteractor;
import com.android.systemui.qs.pipeline.shared.QSPipelineFlagsRepository;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.settings.UserFileManager;
import com.android.systemui.settings.UserFileManagerImpl;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.shade.ShadeController;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.QsResetSettingsManager;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.settings.SecureSettings;
import com.samsung.android.feature.SemCarrierFeature;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.SemPersonaManager;
import com.sec.ims.settings.ImsProfile;
import dagger.Lazy;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.inject.Provider;

public final class QSTileHost implements QSHost, TunerService.Tunable, PluginListener, ProtoDumpable, PanelInteractor, CustomTileAddedRepository {
    public static final boolean DEBUG = Log.isLoggable("QSTileHost", 3);
    public static final boolean LOGGING_DEBUG = Log.isLoggable(SystemUIAnalytics.TAG_QUICK_SETTINGS, 3);
    static final String TILES = "tiles_prefs";
    public SecAutoTileManager mAutoTiles;
    public String mBnRRemovedTileList;
    public String mBnRTileList;
    public String mBottomBarTileList;
    public String mBrightnessVolumeBarTileList;
    public HashMap mComponentNameTable;
    public final Context mContext;
    public int mCurrentUser;
    public final CustomTileStatePersister mCustomTileStatePersister;
    public final SharedPreferences.Editor mEditor;
    public final QSPipelineFlagsRepository mFeatureFlags;
    public final Handler mHandler;
    public final ArrayList mHiddenTilesByKnoxInTopBottomBar;
    public boolean mIsQQSosUpdating;
    public boolean mIsRestoring;
    public List mKnoxBlockedQsTileList;
    public final AnonymousClass3 mKnoxStateCallback;
    public KnoxStateMonitor mKnoxStateMonitor;
    public List mKnoxUnavailableQsTileList;
    public final SecLockscreenTileHost mLockscreentTileHost;
    public final Executor mMainExecutor;
    public final PluginManager mPluginManager;
    public final SecQQSTileHost mQQSTileHost;
    public final QSLogger mQSLogger;
    public final SecQSTileInstanceManager mQSTileInstanceManager;
    public final ArrayList mQsFactories;
    public final AnonymousClass1 mResetSettingsApplier;
    public final SecQSPanelResourcePicker mResourcePicker;
    public ArrayList mSearchAllowTileList;
    public final ArrayList mSearchables;
    public final SecureSettings mSecureSettings;
    public final Lazy mShadeControllerProvider;
    public String mSmartViewBarTileList;
    public final SecSubScreenQSTileHost mSubScreenTileHost;
    public boolean mTileIsRemovedByApi;
    public HashMap mTileNameTable;
    public final Object mTileUsingByBar;
    public final Object mTileUsingByPanel;
    public boolean mTilesListDirty;
    public final TilesMap mTilesMap;
    public String mTopBarTileList;
    public final TunerService mTunerService;
    public Context mUserContext;
    public final UserFileManager mUserFileManager;
    public final UserManager mUserManager;
    public final UserTracker mUserTracker;
    public int mSEPVersionOfBnRData = 0;
    public final LinkedHashMap mTiles = new LinkedHashMap();
    public final ArrayList mTileSpecs = new ArrayList();
    public final List mCallbacks = new ArrayList();

    /* renamed from: com.android.systemui.qs.QSTileHost$3, reason: invalid class name */
    public final class AnonymousClass3 extends KnoxStateMonitorCallback {
        public AnonymousClass3() {
        }

        @Override // com.android.systemui.knox.KnoxStateMonitorCallback
        public final void onUpdateQuickPanelButtons() {
            QSTileHost qSTileHost = QSTileHost.this;
            CustomSdkMonitor customSdkMonitor = ((KnoxStateMonitorImpl) qSTileHost.mKnoxStateMonitor).mCustomSdkMonitor;
            if (!(customSdkMonitor != null && (customSdkMonitor.mKnoxCustomQuickPanelButtons & 4) == 4)) {
                if (qSTileHost.mBrightnessVolumeBarTileList.isEmpty()) {
                    return;
                }
                String str = qSTileHost.mBrightnessVolumeBarTileList;
                qSTileHost.mBrightnessVolumeBarTileList = qSTileHost.mContext.getResources().getString(R.string.sec_volume_bar_tiles_default);
                for (String str2 : str.split(",")) {
                    if (qSTileHost.isSystemTile(str2)) {
                        qSTileHost.addTile(-1, str2);
                    } else {
                        String customTileSpecFromTileName = qSTileHost.getCustomTileSpecFromTileName(str2);
                        if (customTileSpecFromTileName != null) {
                            qSTileHost.addTile(-1, customTileSpecFromTileName);
                        }
                    }
                }
                qSTileHost.refreshTileList();
                return;
            }
            String string = qSTileHost.mContext.getResources().getString(R.string.sec_brightness_volume_bar_tiles_default);
            qSTileHost.mBrightnessVolumeBarTileList = string;
            for (String str3 : string.split(",")) {
                if (qSTileHost.isSystemTile(str3)) {
                    if (str3.startsWith("custom(")) {
                        qSTileHost.setTileAdded(CustomTile.getComponentFromSpec(str3), false, qSTileHost.mCurrentUser);
                    }
                    qSTileHost.mMainExecutor.execute(new QSTileHost$$ExternalSyntheticLambda4(qSTileHost, str3, 2));
                } else {
                    String customTileSpecFromTileName2 = qSTileHost.getCustomTileSpecFromTileName(str3);
                    if (customTileSpecFromTileName2 != null) {
                        if (customTileSpecFromTileName2.startsWith("custom(")) {
                            qSTileHost.setTileAdded(CustomTile.getComponentFromSpec(customTileSpecFromTileName2), false, qSTileHost.mCurrentUser);
                        }
                        qSTileHost.mMainExecutor.execute(new QSTileHost$$ExternalSyntheticLambda4(qSTileHost, customTileSpecFromTileName2, 2));
                    }
                }
            }
        }

        @Override // com.android.systemui.knox.KnoxStateMonitorCallback
        public final void onUpdateQuickPanelItems() {
            QSTileHost qSTileHost = QSTileHost.this;
            qSTileHost.mKnoxBlockedQsTileList = ((KnoxStateMonitorImpl) qSTileHost.mKnoxStateMonitor).getQuickPanelItems();
            Log.d("QSTileHost", "onUpdateQuickPanelItems : " + qSTileHost.mKnoxBlockedQsTileList);
            if (qSTileHost.mComponentNameTable == null) {
                qSTileHost.makeCustomTileComponentNameTable();
            }
            qSTileHost.updateHiddenBarTilesListByKnox();
            qSTileHost.refreshTileList();
        }

        @Override // com.android.systemui.knox.KnoxStateMonitorCallback
        public final void onUpdateQuickPanelUnavailableButtons() {
            QSTileHost qSTileHost = QSTileHost.this;
            qSTileHost.mKnoxUnavailableQsTileList = ((KnoxStateMonitorImpl) qSTileHost.mKnoxStateMonitor).getQuickPanelUnavailableButtons();
            Log.d("QSTileHost", "onUpdateQuickPanelItems : " + qSTileHost.mKnoxUnavailableQsTileList);
            if (qSTileHost.mComponentNameTable == null) {
                qSTileHost.makeCustomTileComponentNameTable();
            }
        }
    }

    public final class TilesMap {
        public static final HashMap mTilesMap = new HashMap();
        public static TilesMap sInstance = null;
        public static final int SID_TILE_STATE = 1;

        public TilesMap(Context context) {
            Log.d(SystemUIAnalytics.TAG, "TilesMap");
            String[] stringArray = context.getResources().getStringArray(R.array.tile_ids);
            ArrayList arrayList = new ArrayList();
            for (String str : stringArray) {
                arrayList.add(str);
                if (arrayList.size() == 3) {
                    ArrayList arrayList2 = new ArrayList();
                    arrayList2.add((String) arrayList.get(1));
                    arrayList2.add((String) arrayList.get(2));
                    mTilesMap.put((String) arrayList.get(0), arrayList2);
                    arrayList.clear();
                }
            }
        }

        public static String getId(int i, String str) {
            if (SID_TILE_STATE < i || i < 0) {
                return null;
            }
            List list = sInstance == null ? null : (List) mTilesMap.get(str);
            if (list == null) {
                return null;
            }
            return (String) list.get(i);
        }
    }

    public QSTileHost(Context context, Lazy lazy, QSFactory qSFactory, final Executor executor, PluginManager pluginManager, TunerService tunerService, final Provider provider, Lazy lazy2, QSLogger qSLogger, UserTracker userTracker, SecureSettings secureSettings, CustomTileStatePersister customTileStatePersister, TileLifecycleManager.Factory factory, UserFileManager userFileManager, QSPipelineFlagsRepository qSPipelineFlagsRepository, BootAnimationFinishedCache bootAnimationFinishedCache, Executor executor2) {
        ArrayList arrayList = new ArrayList();
        this.mQsFactories = arrayList;
        this.mHandler = new Handler();
        this.mIsQQSosUpdating = false;
        this.mHiddenTilesByKnoxInTopBottomBar = new ArrayList();
        this.mSearchables = new ArrayList();
        this.mTilesListDirty = true;
        this.mTileUsingByBar = new Object();
        this.mTileUsingByPanel = new Object();
        QsResetSettingsManager.ResetSettingsApplier resetSettingsApplier = new QsResetSettingsManager.ResetSettingsApplier() { // from class: com.android.systemui.qs.QSTileHost.1
            @Override // com.android.systemui.util.QsResetSettingsManager.ResetSettingsApplier
            public final void applyResetSetting() {
                QSTileHost.this.resetTileList();
            }
        };
        this.mKnoxStateCallback = new AnonymousClass3();
        this.mContext = context;
        this.mUserContext = context;
        this.mTunerService = tunerService;
        this.mPluginManager = pluginManager;
        this.mQSLogger = qSLogger;
        this.mMainExecutor = executor;
        this.mUserFileManager = userFileManager;
        this.mFeatureFlags = qSPipelineFlagsRepository;
        this.mShadeControllerProvider = lazy2;
        qSPipelineFlagsRepository.getClass();
        Flags.qsNewTiles();
        arrayList.add(qSFactory);
        pluginManager.addPluginListener((PluginListener) this, QSFactory.class, true);
        this.mUserTracker = userTracker;
        UserTrackerImpl userTrackerImpl = (UserTrackerImpl) userTracker;
        this.mCurrentUser = userTrackerImpl.getUserId();
        this.mSecureSettings = secureSettings;
        this.mCustomTileStatePersister = customTileStatePersister;
        SecQSPanelResourcePicker secQSPanelResourcePicker = (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);
        this.mResourcePicker = secQSPanelResourcePicker;
        this.mUserManager = (UserManager) context.getSystemService("user");
        if (!Process.myUserHandle().equals(UserHandle.SYSTEM)) {
            Log.e("QSTileHost", "OPS not initialized for non-primary user, just return");
            return;
        }
        int i = context.getResources().getConfiguration().orientation;
        this.mTopBarTileList = secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getTopBarTileList(i, context);
        this.mBrightnessVolumeBarTileList = context.getResources().getString(R.string.sec_brightness_volume_bar_tiles_default);
        this.mBottomBarTileList = secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getBottomBarTileList(i, context);
        this.mSmartViewBarTileList = secQSPanelResourcePicker.getSmartViewBarTileList(context);
        this.mQSTileInstanceManager = new SecQSTileInstanceManager(context, this, userTracker, qSLogger);
        this.mQQSTileHost = new SecQQSTileHost(context, this, userTracker, bootAnimationFinishedCache, executor, qSLogger);
        if (ScRune.QUICK_MANAGE_SUBSCREEN_TILE_LIST) {
            this.mSubScreenTileHost = new SecSubScreenQSTileHost(context, this, userTracker, bootAnimationFinishedCache, executor, qSLogger);
        }
        this.mLockscreentTileHost = new SecLockscreenTileHost(context, this, userTrackerImpl, executor, executor2);
        if (TilesMap.sInstance == null) {
            TilesMap.sInstance = new TilesMap(context);
        }
        this.mTilesMap = TilesMap.sInstance;
        this.mEditor = context.getSharedPreferences(SystemUIAnalytics.QUICK_PREF_NAME, 0).edit();
        ((BootAnimationFinishedCacheImpl) bootAnimationFinishedCache).addListener(new BootAnimationFinishedCache.BootAnimationFinishedListener() { // from class: com.android.systemui.qs.QSTileHost$$ExternalSyntheticLambda2
            @Override // com.android.systemui.BootAnimationFinishedCache.BootAnimationFinishedListener
            public final void onBootAnimationFinished() {
                Executor executor3 = executor;
                QSTileHost qSTileHost = QSTileHost.this;
                qSTileHost.getClass();
                executor3.execute(new QSTileHost$$ExternalSyntheticLambda12(qSTileHost, provider, 0));
            }
        });
        ((QsResetSettingsManager) Dependency.sDependency.getDependencyInner(QsResetSettingsManager.class)).registerApplier(resetSettingsApplier);
        ((QSBackupRestoreManager) Dependency.sDependency.getDependencyInner(QSBackupRestoreManager.class)).addCallback("TileList", new QSBackupRestoreManager.Callback() { // from class: com.android.systemui.qs.QSTileHost.2
            @Override // com.android.systemui.qs.QSBackupRestoreManager.Callback
            public final boolean isValidDB() {
                return true;
            }

            @Override // com.android.systemui.qs.QSBackupRestoreManager.Callback
            public final String onBackup(boolean z) {
                String changeOldOSTileListToNewOsTileList;
                String str;
                boolean z2;
                StringBuilder sb = new StringBuilder("TAG::sep_version::");
                QSTileHost qSTileHost = QSTileHost.this;
                if (z) {
                    changeOldOSTileListToNewOsTileList = Settings.Secure.getString(qSTileHost.mContext.getContentResolver(), "sysui_qs_tiles");
                    if (changeOldOSTileListToNewOsTileList.isEmpty()) {
                        changeOldOSTileListToNewOsTileList = " ";
                    }
                    str = Settings.Secure.getString(qSTileHost.mContext.getContentResolver(), "sysui_removed_qs_tiles");
                    z2 = Prefs.getBoolean(qSTileHost.mContext, "QsHasEditedQuickTileList", false);
                } else {
                    changeOldOSTileListToNewOsTileList = qSTileHost.changeOldOSTileListToNewOsTileList(qSTileHost.getDefaultTileList());
                    str = "";
                    z2 = false;
                }
                sb.append(Build.VERSION.SEM_PLATFORM_INT);
                sb.append("::TAG::has_edited::");
                sb.append(z2);
                sb.append("::TAG::removed_tile_list::");
                sb.append(str);
                sb.append("::TAG::tile_list::");
                sb.append(changeOldOSTileListToNewOsTileList);
                StringBuilder sb2 = new StringBuilder("::TAG::qqs_has_edited::");
                SecQQSTileHost secQQSTileHost = qSTileHost.mQQSTileHost;
                String string = Settings.Secure.getString(secQQSTileHost.mContext.getContentResolver(), "sysui_quick_qs_tiles");
                sb2.append(Prefs.getBoolean(secQQSTileHost.mContext, "QQsHasEditedQuickTileList", false));
                sb2.append("::TAG::qqs_tile_list::");
                sb2.append(string);
                sb.append(sb2.toString());
                return sb.toString();
            }

            @Override // com.android.systemui.qs.QSBackupRestoreManager.Callback
            public final void onRestore(String str) {
                String str2;
                QSTileHost qSTileHost = QSTileHost.this;
                qSTileHost.getClass();
                String[] split = str.split("::");
                String str3 = "";
                if (split.length <= 1) {
                    if (split[0].equals("removed_tile_list")) {
                        qSTileHost.mBnRRemovedTileList = "";
                        return;
                    }
                    return;
                }
                if (!split[0].equals("tile_list")) {
                    if (split[0].equals("removed_tile_list")) {
                        qSTileHost.mBnRRemovedTileList = split[1];
                        return;
                    }
                    boolean equals = split[0].equals("qqs_tile_list");
                    SecQQSTileHost secQQSTileHost = qSTileHost.mQQSTileHost;
                    if (equals) {
                        secQQSTileHost.setRestoreData(split[0], split[1]);
                        return;
                    }
                    if (split[0].equals("sep_version")) {
                        String str4 = split[1];
                        MotionLayout$$ExternalSyntheticOutline0.m("setRestoreData : sepVersion = ", str4, "QSTileHost");
                        if (str4 != null) {
                            qSTileHost.mSEPVersionOfBnRData = Integer.valueOf(str4).intValue();
                            return;
                        }
                        return;
                    }
                    if (split[0].equals("has_edited")) {
                        boolean booleanValue = Boolean.valueOf(split[1]).booleanValue();
                        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("setRestoreData : hasEdited = ", "QSTileHost", booleanValue);
                        Prefs.putBoolean(qSTileHost.mContext, "QsHasEditedQuickTileList", booleanValue);
                        return;
                    } else {
                        if (split[0].equals("qqs_has_edited")) {
                            secQQSTileHost.setRestoreData(split[0], split[1]);
                            return;
                        }
                        return;
                    }
                }
                String str5 = split[1];
                if (str5 == null) {
                    Log.w("QSTileHost", "restoredTileList is null");
                    return;
                }
                qSTileHost.mBnRTileList = str5;
                String str6 = "";
                for (String str7 : qSTileHost.changeOldOSTileListToNewOsTileList(str5).split(",")) {
                    if (!str7.startsWith("custom(")) {
                        str6 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str6, str7, ",");
                    } else if (qSTileHost.isComponentAvailable(CustomTile.getComponentFromSpec(str7))) {
                        str6 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str6, str7, ",");
                    }
                }
                String str8 = qSTileHost.mBnRRemovedTileList;
                if (str8 == null || str8.isEmpty() || "null".equals(qSTileHost.mBnRRemovedTileList)) {
                    str2 = str6;
                } else {
                    str3 = qSTileHost.changeOldOSTileListToNewOsTileList(qSTileHost.mBnRRemovedTileList);
                    str2 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str6, ",", str3);
                }
                String stringForUser = Settings.Secure.getStringForUser(qSTileHost.mContext.getContentResolver(), "sysui_qs_tiles", ActivityManager.getCurrentUser());
                ArrayList arrayList2 = new ArrayList();
                for (String str9 : stringForUser.split(",")) {
                    arrayList2.add(str9);
                }
                Iterator it = arrayList2.iterator();
                while (it.hasNext()) {
                    String str10 = (String) it.next();
                    if (!str2.contains(str10)) {
                        str6 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str6, str10, ",");
                    }
                }
                String stringForUser2 = Settings.Secure.getStringForUser(qSTileHost.mContext.getContentResolver(), "sysui_removed_qs_tiles", ActivityManager.getCurrentUser());
                if (stringForUser2 != null) {
                    ArrayList arrayList3 = new ArrayList();
                    for (String str11 : stringForUser2.split(",")) {
                        if (!str11.trim().isEmpty()) {
                            arrayList3.add(str11);
                        }
                    }
                    Iterator it2 = arrayList3.iterator();
                    while (it2.hasNext()) {
                        String str12 = (String) it2.next();
                        if (!str2.contains(str12)) {
                            str3 = str3.isEmpty() ? str12 : AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str3, ",", str12);
                        }
                    }
                }
                int i2 = qSTileHost.mSEPVersionOfBnRData;
                if (i2 < Build.VERSION.SEM_PLATFORM_INT && i2 < 150000) {
                    qSTileHost.mMainExecutor.execute(new QSTileHost$$ExternalSyntheticLambda4(qSTileHost, str6, 1));
                }
                qSTileHost.mSEPVersionOfBnRData = 0;
                qSTileHost.mIsRestoring = true;
                Log.d("QSTileHost", "bnrRemovedTileList  " + str3);
                Log.d("QSTileHost", "bnrTileList  " + str6);
                ContentResolver contentResolver = qSTileHost.mContext.getContentResolver();
                UserTrackerImpl userTrackerImpl2 = (UserTrackerImpl) qSTileHost.mUserTracker;
                Settings.Secure.putStringForUser(contentResolver, "sysui_removed_qs_tiles", str3, userTrackerImpl2.getUserId());
                Settings.Secure.putStringForUser(qSTileHost.mContext.getContentResolver(), "sysui_qs_tiles", str6, userTrackerImpl2.getUserId());
            }
        });
    }

    @Override // com.android.systemui.qs.QSHost
    public final void addCallback(QSHost.Callback callback) {
        this.mCallbacks.add(callback);
    }

    public final void addTile(int i, String str) {
        this.mMainExecutor.execute(new QSTileHost$$ExternalSyntheticLambda3(this, str, i, 1));
    }

    public final String changeOldOSTileListToNewOsTileList(String str) {
        if (str == null) {
            return null;
        }
        String str2 = "";
        for (String str3 : str.split(",")) {
            String trim = str3.trim();
            if (!trim.isEmpty()) {
                str2 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str2, changeOldOSTileNameToNewName(trim), ",");
            }
        }
        return str2;
    }

    public final String changeOldOSTileNameToNewName(String str) {
        Locale locale = Locale.US;
        return "WIFIHOTSPOT".equals(str.toUpperCase(locale)) ? "Hotspot" : "AUTOROTATE".equals(str.toUpperCase(locale)) ? "RotationLock" : "TORCHLIGHT".equals(str.toUpperCase(locale)) ? PluginLockShortcutTask.FLASH_LIGHT_TASK : ("SILENTMODE".equals(str.toUpperCase(locale)) || "SOUNDMODE".equals(str.toUpperCase(locale))) ? "SoundMode" : ("DND".equals(str.toUpperCase(locale)) || "DORMANTMODE".equals(str.toUpperCase(locale))) ? PluginLockShortcutTask.DO_NOT_DISTURB_TASK : "WORK".equals(str.toUpperCase(locale)) ? "WorkMode" : ("NIGHTMODE".equals(str.toUpperCase(locale)) || str.equals(getCustomTileSpecFromTileName("NightMode"))) ? "UiModeNight" : str.contains("com.samsung.accessibility/.vision.viewclear.extradim.ReduceBrightnessTileService") ? "ReduceBrightColors" : str.contains("com.samsung.accessibility/.vision.viewclear.HighContrastFontTileService") ? "HighContrastFont" : str.contains("com.samsung.accessibility/.vision.viewclear.ColorInversionTileService") ? "ColorInversion" : str.contains("com.samsung.accessibility/.vision.color.ColorLensTileService") ? "ColorLens" : str.contains("com.samsung.accessibility/.vision.color.ColorAdjustmentTileService") ? "ColorAdjustment" : str.contains("com.samsung.accessibility/.vision.color.AccessibilityColorCorrectionTileService") ? "ColorCorrection" : str.contains("com.samsung.android.bixby.interpreter/.interpretation.view.InterpreterQuickTileService") ? "custom(com.samsung.android.app.interpreter/.interpretation.view.InterpreterQuickTileService)" : str;
    }

    public final void changeTileSpecs(Predicate predicate) {
        List loadTileSpecs;
        if (this.mTilesListDirty) {
            loadTileSpecs = loadTileSpecs(this.mContext, this.mSecureSettings.getStringForUser("sysui_qs_tiles", ((UserTrackerImpl) this.mUserTracker).getUserId()));
        } else {
            loadTileSpecs = new ArrayList(this.mTileSpecs);
        }
        if (predicate.test(loadTileSpecs)) {
            this.mTilesListDirty = true;
            if (loadTileSpecs.isEmpty()) {
                loadTileSpecs.add("empty");
            }
            saveTilesToSettings(loadTileSpecs);
        }
    }

    @Override // com.android.systemui.qs.QSHost
    public final void changeTilesByUser(List list, List list2) {
        ArrayList arrayList = new ArrayList(list);
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            String str = (String) arrayList.get(i);
            if (str.startsWith("custom(")) {
                if (this.mTileIsRemovedByApi && "WifiCalling".equals(getCustomTileNameFromSpec(str))) {
                    this.mTileIsRemovedByApi = false;
                } else if (!list2.contains(str)) {
                    QSTile requestTileUsing = this.mQSTileInstanceManager.requestTileUsing(this.mTileUsingByPanel, str);
                    if (requestTileUsing != null) {
                        TileLifecycleManager tileLifecycleManager = ((CustomTile) requestTileUsing).mServiceManager.mStateManager;
                        tileLifecycleManager.onStopListening();
                        tileLifecycleManager.onTileRemoved();
                        tileLifecycleManager.mExecutor.execute(new TileLifecycleManager$$ExternalSyntheticLambda0(tileLifecycleManager, 3));
                        ComponentName componentFromSpec = CustomTile.getComponentFromSpec(str);
                        ((CustomTileStatePersisterImpl) this.mCustomTileStatePersister).sharedPreferences.edit().remove(new TileServiceKey(componentFromSpec, this.mCurrentUser).string).apply();
                        setTileAdded(componentFromSpec, false, this.mCurrentUser);
                    }
                }
            }
        }
        if (list2.isEmpty()) {
            list2.add("empty");
        }
        Log.d("QSTileHost", "saveCurrentTiles " + list2);
        this.mTilesListDirty = true;
        saveTilesToSettings(list2);
    }

    @Override // com.android.systemui.qs.pipeline.domain.interactor.PanelInteractor
    public final void collapsePanels() {
        ((ShadeController) this.mShadeControllerProvider.get()).postAnimateCollapseShade();
    }

    public final QSTile createTile(String str) {
        for (int i = 0; i < this.mQsFactories.size(); i++) {
            QSTile createTile = ((QSFactory) this.mQsFactories.get(i)).createTile(str);
            if (createTile != null) {
                return createTile;
            }
        }
        return null;
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(final PrintWriter printWriter, final String[] strArr) {
        StringBuilder m = CarrierTextController$$ExternalSyntheticOutline0.m(printWriter, "QSTileHost:", "tile specs: ");
        m.append(this.mTileSpecs);
        printWriter.println(m.toString());
        StringBuilder m2 = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("current user: "), this.mCurrentUser, printWriter, "is dirty: ");
        m2.append(this.mTilesListDirty);
        printWriter.println(m2.toString());
        printWriter.println("tiles:");
        this.mTiles.values().stream().filter(new QSTileHost$$ExternalSyntheticLambda0(0)).forEach(new Consumer() { // from class: com.android.systemui.qs.QSTileHost$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((Dumpable) ((QSTile) obj)).dump(printWriter, strArr);
            }
        });
        if (this.mAutoTiles != null) {
            printWriter.println("  mRemovedTileListByAppIntent : " + this.mAutoTiles.mRemovedTileListByAppIntent);
        }
        printWriter.println("  mBnRTileList : " + this.mBnRTileList);
        printWriter.println("  mBnRRemovedTileList : " + this.mBnRRemovedTileList);
        printWriter.println("  mSearchables : ");
        Iterator it = this.mSearchables.iterator();
        while (it.hasNext()) {
            printWriter.print(((QSTileImpl) it.next()).getSearchTitle() + ", ");
        }
        printWriter.println();
        SecQQSTileHost secQQSTileHost = this.mQQSTileHost;
        secQQSTileHost.getClass();
        printWriter.println("SecQQSTileHost:");
        CarrierTextController$$ExternalSyntheticOutline0.m(new StringBuilder("  mBnRQQSTileList : "), secQQSTileHost.mBnRQQSTileList, printWriter);
        secQQSTileHost.mTiles.values().stream().filter(new SecQQSTileHost$$ExternalSyntheticLambda2()).forEach(new Consumer() { // from class: com.android.systemui.qs.SecQQSTileHost$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((Dumpable) ((QSTile) obj)).dump(printWriter, strArr);
            }
        });
        printWriter.println("  mTileUsingByQQS : " + secQQSTileHost.mTileUsingByQQS);
        printWriter.println("  mTileUsingByBar : " + this.mTileUsingByBar);
        printWriter.println("  mTileUsingByPanel : " + this.mTileUsingByPanel);
        SecLockscreenTileHost secLockscreenTileHost = this.mLockscreentTileHost;
        secLockscreenTileHost.getClass();
        printWriter.println("LockscreenTileHost:");
        DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("  mTileUsingByLock : ", secLockscreenTileHost.obj, printWriter);
        SecQSTileInstanceManager secQSTileInstanceManager = this.mQSTileInstanceManager;
        secQSTileInstanceManager.getClass();
        printWriter.println("SecQSTileInstanceManager:");
        printWriter.println("  mTileInstances : " + secQSTileInstanceManager.mTileInstances);
        printWriter.println("  mTileUsingHosts : " + secQSTileInstanceManager.mTileUsingHosts);
    }

    @Override // com.android.systemui.ProtoDumpable
    public final void dumpProto(SystemUIProtoDump systemUIProtoDump) {
        final int i = 0;
        Stream map = this.mTiles.values().stream().map(new Function() { // from class: com.android.systemui.qs.QSTileHost$$ExternalSyntheticLambda8
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                switch (i) {
                    case 0:
                        return ((QSTile) obj).getState();
                    default:
                        return TileStateToProtoKt.toProto((QSTile.State) obj);
                }
            }
        });
        final int i2 = 1;
        systemUIProtoDump.tiles = (QsTileState[]) ((List) map.map(new Function() { // from class: com.android.systemui.qs.QSTileHost$$ExternalSyntheticLambda8
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                switch (i2) {
                    case 0:
                        return ((QSTile) obj).getState();
                    default:
                        return TileStateToProtoKt.toProto((QSTile.State) obj);
                }
            }
        }).filter(new QSTileHost$$ExternalSyntheticLambda0(1)).collect(Collectors.toList())).toArray(new QsTileState[0]);
    }

    @Override // com.android.systemui.qs.pipeline.domain.interactor.PanelInteractor
    public final void forceCollapsePanels() {
        ((ShadeController) this.mShadeControllerProvider.get()).postAnimateForceCollapseShade();
    }

    @Override // com.android.systemui.qs.QSHost
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.ArrayList getBarTilesByType(int r18, int r19) {
        /*
            Method dump skipped, instructions count: 626
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.QSTileHost.getBarTilesByType(int, int):java.util.ArrayList");
    }

    @Override // com.android.systemui.qs.QSHost
    public final Context getContext() {
        return this.mContext;
    }

    @Override // com.android.systemui.qs.QSHost
    public final String getCustomTileNameFromSpec(String str) {
        if (this.mTileNameTable == null) {
            makeCustomTileNameTable();
        }
        if (str.contains("com.sec.android.app.wfdbroker/.AllShareCastTile")) {
            return "AllShareCast";
        }
        String str2 = (String) this.mTileNameTable.get(CustomTile.getComponentFromSpec(str).flattenToShortString());
        return "com.samsung.android.sm_cn".equals(Operator.smartManagerPackageName) ? "BatteryModeCHN".equals(str2) ? "BatteryMode" : "PowerShareCHN".equals(str2) ? "PowerShare" : str2 : str2;
    }

    public final String getCustomTileSpecFromTileName(String str) {
        if (this.mComponentNameTable == null) {
            makeCustomTileComponentNameTable();
        }
        if ("com.samsung.android.sm_cn".equals(Operator.smartManagerPackageName)) {
            if ("BatteryMode".equals(str)) {
                str = "BatteryModeCHN";
            } else if ("PowerShare".equals(str)) {
                str = "PowerShareCHN";
            }
        }
        String str2 = (String) this.mComponentNameTable.get(str);
        if (str2 != null) {
            return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("custom(", str2, ")");
        }
        return null;
    }

    public final String getDefaultTileList() {
        String supportedAllTileList = getSupportedAllTileList();
        ArrayList arrayList = new ArrayList();
        for (String str : supportedAllTileList.split(",")) {
            if (!isBarTile(str)) {
                arrayList.add(str);
            }
        }
        Log.d("QSTileHost", "getDefaultTileList result : " + arrayList);
        return TextUtils.join(",", arrayList);
    }

    @Override // com.android.systemui.qs.QSHost
    public final SecQQSTileHost getQQSTileHost() {
        return this.mQQSTileHost;
    }

    @Override // com.android.systemui.qs.QSHost
    public final List getSpecs() {
        return this.mTileSpecs;
    }

    public final String getSupportedAllTileList() {
        Resources resources = this.mContext.getResources();
        String string = resources.getString(R.string.sec_quick_settings_tiles_default);
        boolean z = Operator.QUICK_IS_VZW_BRANDING;
        String changeOldOSTileListToNewOsTileList = changeOldOSTileListToNewOsTileList(("true".equals(SemSystemProperties.get("mdc.singlesku")) && "true".equals(SemSystemProperties.get("mdc.unified"))) ? SemCarrierFeature.getInstance().getString(0, "CarrierFeature_SystemUI_ConfigDefQuickSettingItem", string, false) : SemCscFeature.getInstance().getString("CscFeature_SystemUI_ConfigDefQuickSettingItem", string));
        String string2 = resources.getString(R.string.quick_settings_auto_adding_tiles);
        ArrayList arrayList = new ArrayList();
        for (String str : changeOldOSTileListToNewOsTileList.split(",")) {
            String trim = str.trim();
            if (!trim.isEmpty()) {
                if ("Bluetooth".equals(trim) && arrayList.contains("SoundMode")) {
                    arrayList.add(arrayList.indexOf("SoundMode"), trim);
                } else {
                    arrayList.add(trim);
                }
                if (PluginLockShortcutTask.DO_NOT_DISTURB_TASK.equals(trim) && DeviceType.isTablet()) {
                    for (String str2 : resources.getString(R.string.quick_settings_additional_default_tiles_tablet).split(",")) {
                        String trim2 = str2.trim();
                        if (!trim2.isEmpty()) {
                            arrayList.add(trim2);
                        }
                    }
                }
            }
        }
        String[] split = string2.split(",");
        for (String str3 : split) {
            if (!str3.isEmpty()) {
                int indexOf = str3.indexOf(":");
                String substring = str3.substring(0, indexOf);
                int intValue = Integer.valueOf(str3.substring(indexOf + 1, str3.length())).intValue();
                if (!arrayList.contains(substring) && (!"CameraSharing".equals(substring) || DeviceType.isTablet())) {
                    if (intValue < 0 || intValue > arrayList.size()) {
                        arrayList.add(substring);
                    } else {
                        arrayList.add(intValue, substring);
                    }
                    SecNotificationBlockManager$$ExternalSyntheticOutline0.m(intValue, "getSupportedAllTileList : tileName = ", substring, ", tileIndex = ", "QSTileHost");
                }
            }
        }
        Log.d("QSTileHost", "getSupportedAllTileList result : " + arrayList);
        return TextUtils.join(",", arrayList);
    }

    @Override // com.android.systemui.qs.QSHost
    public final Collection getTiles() {
        return this.mTiles.values();
    }

    @Override // com.android.systemui.qs.QSHost
    public final Context getUserContext() {
        return this.mUserContext;
    }

    @Override // com.android.systemui.qs.QSHost
    public final int getUserId() {
        return this.mCurrentUser;
    }

    @Override // com.android.systemui.qs.QSHost
    public final int indexOf(String str) {
        return this.mTileSpecs.indexOf(str);
    }

    public final void initTunerServiceAndAutoTile(Provider provider) {
        this.mTunerService.addTunable(this, "sysui_qs_tiles");
        this.mAutoTiles = (SecAutoTileManager) provider.get();
        KnoxStateMonitor knoxStateMonitor = (KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class);
        this.mKnoxStateMonitor = knoxStateMonitor;
        AnonymousClass3 anonymousClass3 = this.mKnoxStateCallback;
        ((KnoxStateMonitorImpl) knoxStateMonitor).registerCallback(anonymousClass3);
        anonymousClass3.onUpdateQuickPanelButtons();
        this.mKnoxBlockedQsTileList = ((KnoxStateMonitorImpl) this.mKnoxStateMonitor).getQuickPanelItems();
        this.mKnoxUnavailableQsTileList = ((KnoxStateMonitorImpl) this.mKnoxStateMonitor).getQuickPanelUnavailableButtons();
        Log.d("QSTileHost", "QSTileHost : mKnoxBlockedQsTileList = " + this.mKnoxBlockedQsTileList + ", mKnoxUnavailableQsTileList = " + this.mKnoxUnavailableQsTileList);
        List list = this.mKnoxBlockedQsTileList;
        if (list == null || list.isEmpty()) {
            return;
        }
        if (this.mComponentNameTable == null) {
            makeCustomTileComponentNameTable();
        }
        updateHiddenBarTilesListByKnox();
        refreshTileList();
    }

    @Override // com.android.systemui.qs.QSHost
    public final boolean isAvailableCustomTile(String str) {
        boolean equals = "Dolby".equals(str);
        UserTracker userTracker = this.mUserTracker;
        if (equals) {
            if (!SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_MMFW_SUPPORT_DOLBY_AUDIO", false) || ((UserTrackerImpl) userTracker).getUserId() != 0) {
                Log.d("QSTileHost", "isAvailableCustomTile : DolbyTile is removed ");
                return false;
            }
        } else if ("Aod".equals(str)) {
            if (!SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_AOD_ITEM", "").contains("aodversion")) {
                Log.d("QSTileHost", "isAvailableCustomTile : AodTile is removed ");
                return false;
            }
        } else if ("AllShareCast".equals(str)) {
            int semCheckScreenSharingSupported = ((DisplayManager) this.mContext.getSystemService("display")).semCheckScreenSharingSupported();
            if (semCheckScreenSharingSupported != 1 && semCheckScreenSharingSupported != 0) {
                Log.d("QSTileHost", "isAvailableCustomTile : AllShareCastTile is removed ");
                return false;
            }
        } else if ("Nfc".equals(str)) {
            if (!this.mContext.getPackageManager().hasSystemFeature("android.hardware.nfc")) {
                Log.d("QSTileHost", "isAvailableCustomTile : NfcTile is removed ");
                return false;
            }
        } else {
            if ("SecureFolder".equals(str)) {
                SemPersonaManager semPersonaManager = (SemPersonaManager) this.mContext.getSystemService("persona");
                if (semPersonaManager != null) {
                    return semPersonaManager.isUserManaged() && ((UserTrackerImpl) userTracker).getUserId() == 0;
                }
                return false;
            }
            if ("Sync".equals(str)) {
                if (this.mUserManager.getUserInfo(((UserTrackerImpl) userTracker).getUserId()).isRestricted()) {
                    Log.d("QSTileHost", "isAvailableCustomTile : Sync is removed ");
                    return false;
                }
            } else {
                if ("BikeMode".equals(str)) {
                    return false;
                }
                if ("DailyBoard".equals(str)) {
                    return !"".equals(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_COMMON_CONFIG_DAILYBOARD", "")) && (((UserTrackerImpl) userTracker).getUserId() == 0 || isPackageAvailable("com.samsung.android.homemode"));
                }
                if ("BatteryMode".equals(str)) {
                    return isPackageAvailable(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_SMARTMANAGER_CONFIG_PACKAGE_NAME", "com.samsung.android.lool"));
                }
                if ("WifiCalling".equals(str)) {
                    return isComponentAvailable(CustomTile.getComponentFromSpec(getCustomTileSpecFromTileName(str)));
                }
                if ("SpenRemote".equals(str)) {
                    return SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_BLE_SPEN") || DeviceType.isSupportUnbundledBleSPen();
                }
                if ("PowerShare".equals(str)) {
                    return SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_BATTERY_SUPPORT_WIRELESS_TX");
                }
                if ("PowerKeySetting".equals(str)) {
                    String string = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_BIXBY_CONFIG_HWKEY");
                    if (TextUtils.isEmpty(string)) {
                        return false;
                    }
                    return string.contains("pwrkey");
                }
                if ("QRScanner".equals(str)) {
                    return SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_CAMERA_SUPPORT_QRCODE") && isPackageAvailable("com.sec.android.app.camera");
                }
                if ("ScreenRecorder".equals(str)) {
                    return SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_SCREEN_RECORDER");
                }
                if (!"InstantSession".equals(str)) {
                    if ("Routines".equals(str)) {
                        return isPackageAvailable("com.samsung.android.app.routines");
                    }
                    if ("KidsHome".equals(str)) {
                        return isPackageAvailable("com.samsung.android.kidsinstaller");
                    }
                    if ("SecondScreen".equals(str)) {
                        return false;
                    }
                    if ("TurnOn5g".equals(str)) {
                        return (!DeviceType.isSupport5G() || Operator.isChinaQsTileBranding() || Operator.QUICK_IS_BRI_BRANDING || Operator.QUICK_IS_TGY_BRANDING) ? false : true;
                    }
                    if ("VoLte".equals(str)) {
                        return getDefaultTileList().contains("VoLte");
                    }
                    if ("HomeHub".equals(str)) {
                        return !TextUtils.isEmpty(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_COMMON_CONFIG_HOMEHUB"));
                    }
                    if ("NearbyShare".equals(str)) {
                        return Build.VERSION.SEM_PLATFORM_INT < 150100;
                    }
                    if ("Translator".equals(str)) {
                        return !SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_COMMON_DISABLE_NATIVE_AI") && isPackageAvailable("com.samsung.android.app.interpreter");
                    }
                    if ("NearByDevice".equals(str)) {
                        return isPackageAvailable("com.samsung.android.mydevice");
                    }
                }
            }
        }
        return true;
    }

    @Override // com.android.systemui.qs.QSHost
    public final boolean isAvailableForSearch(String str, boolean z) {
        if (this.mSearchAllowTileList == null) {
            this.mSearchAllowTileList = new ArrayList();
            for (String str2 : this.mContext.getResources().getString(R.string.quick_settings_search_allow_list).split(",")) {
                String trim = str2.trim();
                if (!trim.isEmpty()) {
                    this.mSearchAllowTileList.add(trim);
                }
            }
        }
        if (this.mSearchAllowTileList.contains(str.startsWith("custom(") ? getCustomTileNameFromSpec(str) : str) && !shouldBeHiddenByKnox(str)) {
            return !str.startsWith("custom(") || isDefaultCustomTile(CustomTile.getComponentFromSpec(str)) || z;
        }
        return false;
    }

    @Override // com.android.systemui.qs.QSHost
    public final boolean isBarTile(String str) {
        if (str == null || (str.startsWith("custom(") && (str = getCustomTileNameFromSpec(str)) == null)) {
            return false;
        }
        return this.mTopBarTileList.contains(str) || this.mBrightnessVolumeBarTileList.contains(str) || this.mBottomBarTileList.contains(str) || this.mSmartViewBarTileList.contains(str);
    }

    @Override // com.android.systemui.qs.QSHost
    public final boolean isBrightnessVolumeBarTile(String str) {
        if (str.startsWith("custom(")) {
            str = getCustomTileNameFromSpec(str);
        }
        return str != null && this.mBrightnessVolumeBarTileList.contains(str);
    }

    public final boolean isComponentAvailable(ComponentName componentName) {
        try {
            ServiceInfo serviceInfo = this.mContext.getPackageManager().getServiceInfo(componentName, 0);
            if (DEBUG && serviceInfo == null) {
                Log.d("QSTileHost", "Can't find component " + componentName);
            }
            return serviceInfo != null;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    public final boolean isDefaultCustomTile(ComponentName componentName) {
        return this.mContext.getResources().getString(R.string.quick_settings_custom_tile_component_names).contains(componentName.flattenToShortString());
    }

    @Override // com.android.systemui.qs.QSHost
    public final boolean isLargeBarTile(String str) {
        if (str.startsWith("custom(")) {
            str = getCustomTileNameFromSpec(str);
        }
        if (str != null) {
            return this.mTopBarTileList.contains(str) || this.mBottomBarTileList.contains(str) || this.mSmartViewBarTileList.contains(str);
        }
        return false;
    }

    @Override // com.android.systemui.qs.QSHost
    public final boolean isNoBgLargeTile(String str) {
        if (str.startsWith("custom(")) {
            str = getCustomTileNameFromSpec(str);
        }
        return str != null && this.mContext.getString(R.string.sec_no_bg_tiles).contains(str);
    }

    public final boolean isPackageAvailable(String str) {
        try {
            this.mContext.getPackageManager().getPackageInfoAsUser(str, 0, ((UserTrackerImpl) this.mUserTracker).getUserId());
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            if (DEBUG) {
                Log.d("QSTileHost", "Package not available: " + str, e);
            } else {
                MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("Package not available: ", str, "QSTileHost");
            }
            return false;
        }
    }

    public final boolean isRemovedTile(String str) {
        String stringForUser = Settings.Secure.getStringForUser(this.mContext.getContentResolver(), "sysui_removed_qs_tiles", ((UserTrackerImpl) this.mUserTracker).getUserId());
        if (stringForUser != null) {
            for (String str2 : stringForUser.split(",")) {
                if (str2.equals(str)) {
                    return true;
                }
            }
        }
        return false;
    }

    public final boolean isSystemTile(String str) {
        String changeOldOSTileNameToNewName = changeOldOSTileNameToNewName(str);
        for (int i = 0; i < this.mQsFactories.size(); i++) {
            if (((QSFactory) this.mQsFactories.get(i)).isSystemTile(changeOldOSTileNameToNewName)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.systemui.qs.pipeline.data.repository.CustomTileAddedRepository
    public final boolean isTileAdded(int i, ComponentName componentName) {
        return ((UserFileManagerImpl) this.mUserFileManager).getSharedPreferences$1(i, TILES).getBoolean(componentName.flattenToString(), false);
    }

    @Override // com.android.systemui.qs.QSHost
    public final boolean isUnsupportedTile(String str) {
        for (String str2 : this.mContext.getResources().getString(R.string.quick_settings_unsupported_tiles).split(",")) {
            if (str2.equals(str)) {
                MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("isUnsupportedTile ", str, "QSTileHost");
                return true;
            }
        }
        return false;
    }

    public final List loadTileSpecs(Context context, String str) {
        SecAutoTileManager secAutoTileManager;
        context.getResources();
        boolean isFotaUpdate = DeviceState.isFotaUpdate(context);
        boolean z = DEBUG;
        if (str != null && !TextUtils.isEmpty(str) && !isFotaUpdate && !this.mIsRestoring) {
            if (z) {
                Log.d("QSTileHost", "Loaded tile specs from setting: ".concat(str));
            }
            ArrayList arrayList = new ArrayList();
            ArraySet arraySet = new ArraySet();
            boolean z2 = false;
            for (String str2 : str.split(",")) {
                String trim = str2.trim();
                if (!trim.isEmpty() && !isBarTile(trim)) {
                    if (trim.equals("default")) {
                        if (!z2) {
                            Iterator it = ((ArrayList) QSHost.getDefaultSpecs(context.getResources())).iterator();
                            while (it.hasNext()) {
                                String str3 = (String) it.next();
                                if (!arraySet.contains(str3)) {
                                    arrayList.add(str3);
                                    arraySet.add(str3);
                                }
                            }
                            z2 = true;
                        }
                    } else if (!arraySet.contains(trim)) {
                        arrayList.add(trim);
                        arraySet.add(trim);
                    }
                }
            }
            if (arrayList.contains(ImsProfile.PDN_INTERNET)) {
                arrayList.remove(ImsProfile.PDN_WIFI);
                arrayList.remove("cell");
            } else if (arrayList.contains(ImsProfile.PDN_WIFI)) {
                arrayList.set(arrayList.indexOf(ImsProfile.PDN_WIFI), ImsProfile.PDN_INTERNET);
                arrayList.remove("cell");
            } else if (arrayList.contains("cell")) {
                arrayList.set(arrayList.indexOf("cell"), ImsProfile.PDN_INTERNET);
            }
            return arrayList;
        }
        boolean z3 = isFotaUpdate || this.mIsRestoring;
        ArrayList arrayList2 = new ArrayList();
        String defaultTileList = getDefaultTileList();
        ContentResolver contentResolver = this.mContext.getContentResolver();
        UserTrackerImpl userTrackerImpl = (UserTrackerImpl) this.mUserTracker;
        String stringForUser = Settings.Secure.getStringForUser(contentResolver, "sysui_removed_qs_tiles", userTrackerImpl.getUserId());
        String changeOldOSTileListToNewOsTileList = changeOldOSTileListToNewOsTileList(str);
        Settings.Secure.putStringForUser(this.mContext.getContentResolver(), "sysui_removed_qs_tiles", changeOldOSTileListToNewOsTileList(stringForUser), userTrackerImpl.getUserId());
        if (z) {
            MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("Loaded tile specs from csc: ", defaultTileList, "QSTileHost");
        }
        if ("".equals(defaultTileList)) {
            arrayList2 = null;
        } else {
            if (z3) {
                if (changeOldOSTileListToNewOsTileList != null) {
                    ArrayList arrayList3 = new ArrayList();
                    makeCustomTileNameTable();
                    for (String str4 : changeOldOSTileListToNewOsTileList.split(",")) {
                        if (str4.startsWith("custom(") && isDefaultCustomTile(CustomTile.getComponentFromSpec(str4))) {
                            str4 = getCustomTileNameFromSpec(str4);
                        }
                        if (str4 != null) {
                            arrayList3.add(str4);
                        }
                    }
                    changeOldOSTileListToNewOsTileList = TextUtils.join(",", arrayList3);
                }
                Log.d("QSTileHost", "getRecalculatedTileListForFota ");
                if (changeOldOSTileListToNewOsTileList != null) {
                    ArrayList arrayList4 = new ArrayList();
                    ArrayList arrayList5 = new ArrayList();
                    for (String str5 : changeOldOSTileListToNewOsTileList.split(",")) {
                        String trim2 = str5.trim();
                        if (!trim2.isEmpty() && !isUnsupportedTile(trim2) && !isBarTile(trim2)) {
                            arrayList4.add(trim2);
                        }
                    }
                    Log.d("QSTileHost", "oldLists : " + arrayList4);
                    for (String str6 : defaultTileList.split(",")) {
                        String trim3 = str6.trim();
                        if (!trim3.isEmpty()) {
                            arrayList5.add(trim3);
                        }
                    }
                    Log.d("QSTileHost", "newLists : " + arrayList5);
                    for (int i = 0; i < arrayList5.size(); i++) {
                        if (!arrayList4.contains(arrayList5.get(i))) {
                            int indexOf = arrayList5.indexOf(arrayList5.get(i));
                            if (arrayList4.size() < indexOf) {
                                arrayList4.add((String) arrayList5.get(i));
                            } else {
                                arrayList4.add(indexOf, (String) arrayList5.get(i));
                            }
                            StringBuilder sb = new StringBuilder();
                            sb.append(i);
                            sb.append(" add : ");
                            ExifInterface$$ExternalSyntheticOutline0.m(sb, (String) arrayList5.get(i), "QSTileHost");
                        }
                    }
                    defaultTileList = "";
                    for (int i2 = 0; i2 < arrayList4.size(); i2++) {
                        defaultTileList = ComponentActivity$1$$ExternalSyntheticOutline0.m(EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0.m(defaultTileList), (String) arrayList4.get(i2), ",");
                    }
                }
            }
            Log.d("QSTileHost", "loadTileSpecsFromCscFeature : loadedTileList = " + defaultTileList);
            makeCustomTileComponentNameTable();
            for (String str7 : defaultTileList.split(",")) {
                if (!isBarTile(str7)) {
                    if (isSystemTile(str7)) {
                        if (!isRemovedTile(str7)) {
                            arrayList2.add(str7);
                        }
                    } else if (isAvailableCustomTile(str7)) {
                        String customTileSpecFromTileName = getCustomTileSpecFromTileName(str7);
                        if (customTileSpecFromTileName != null) {
                            if ((!SemPersonaManager.isDoEnabled(this.mCurrentUser) || isComponentAvailable(CustomTile.getComponentFromSpec(customTileSpecFromTileName))) && !isRemovedTile(customTileSpecFromTileName) && ((secAutoTileManager = this.mAutoTiles) == null || !secAutoTileManager.isRemovedTileByAppIntent(customTileSpecFromTileName))) {
                                arrayList2.add(customTileSpecFromTileName);
                            }
                        } else if (z3) {
                            if (str7.startsWith("custom(") ? !isDefaultCustomTile(CustomTile.getComponentFromSpec(str7)) : false) {
                                arrayList2.add(str7);
                            }
                        }
                    }
                }
            }
            Log.d("QSTileHost", "loadTileSpecsFromCscFeature : tiles = " + arrayList2);
            Settings.Secure.putStringForUser(this.mContext.getContentResolver(), "sysui_qs_tiles", TextUtils.join(",", arrayList2), userTrackerImpl.getUserId());
        }
        this.mIsRestoring = false;
        if (isFotaUpdate) {
            boolean z4 = this.mIsQQSosUpdating ? Prefs.getBoolean(this.mContext, "QsHasEditedQuickTileList", false) : Prefs.getBoolean(this.mContext, "QQsHasEditedQuickTileList", false);
            KeyguardCarrierViewController$$ExternalSyntheticOutline0.m(new StringBuilder("isQQSosUpdating="), this.mIsQQSosUpdating, " hasEdited=", z4, "QSTileHost");
            boolean z5 = true ^ z4;
            SecQQSTileHost secQQSTileHost = this.mQQSTileHost;
            secQQSTileHost.getClass();
            ArrayList arrayList6 = new ArrayList();
            Iterator it2 = secQQSTileHost.mTileSpecs.iterator();
            while (it2.hasNext()) {
                String str8 = (String) it2.next();
                QSTileHost qSTileHost = secQQSTileHost.mQSTileHost;
                if (qSTileHost.isSystemTile(str8)) {
                    if (z5 && "Bluetooth".equals(str8) && arrayList6.contains("SoundMode")) {
                        arrayList6.add(arrayList6.indexOf("SoundMode"), str8);
                    } else {
                        arrayList6.add(str8);
                    }
                } else if (str8.startsWith("custom(")) {
                    String customTileNameFromSpec = qSTileHost.getCustomTileNameFromSpec(str8);
                    if (customTileNameFromSpec == null) {
                        arrayList6.add(str8);
                    } else if (qSTileHost.isAvailableCustomTile(customTileNameFromSpec)) {
                        arrayList6.add(str8);
                    }
                }
            }
            secQQSTileHost.changeTiles(arrayList6);
            this.mIsQQSosUpdating = false;
        }
        return arrayList2;
    }

    public final void makeCustomTileComponentNameTable() {
        String string = this.mContext.getResources().getString(R.string.quick_settings_custom_tile_component_names);
        this.mComponentNameTable = new HashMap();
        for (String str : string.split(",")) {
            int indexOf = str.indexOf(":");
            String substring = str.substring(0, indexOf);
            String substring2 = str.substring(indexOf + 1, str.length());
            this.mComponentNameTable.put(substring, substring2);
            Log.d("QSTileHost", "make table : customTileName = " + substring + ", componentName = " + substring2);
        }
    }

    public final void makeCustomTileNameTable() {
        String string = this.mContext.getResources().getString(R.string.quick_settings_custom_tile_component_names);
        this.mTileNameTable = new HashMap();
        for (String str : string.split(",")) {
            int indexOf = str.indexOf(":");
            this.mTileNameTable.put(str.substring(indexOf + 1, str.length()), str.substring(0, indexOf));
        }
    }

    @Override // com.android.systemui.plugins.PluginListener
    public final void onPluginConnected(Plugin plugin, Context context) {
        this.mQsFactories.add(0, (QSFactory) plugin);
        String value = this.mTunerService.getValue("sysui_qs_tiles");
        onTuningChanged("sysui_qs_tiles", "");
        onTuningChanged("sysui_qs_tiles", value);
    }

    @Override // com.android.systemui.plugins.PluginListener
    public final void onPluginDisconnected(Plugin plugin) {
        this.mQsFactories.remove((QSFactory) plugin);
        String value = this.mTunerService.getValue("sysui_qs_tiles");
        onTuningChanged("sysui_qs_tiles", "");
        onTuningChanged("sysui_qs_tiles", value);
    }

    /* JADX WARN: Code restructure failed: missing block: B:74:0x0236, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x0237, code lost:
    
        android.util.Log.w("QSTileHost", "Error creating tile for spec: " + r15, r0);
     */
    @Override // com.android.systemui.tuner.TunerService.Tunable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onTuningChanged(java.lang.String r19, java.lang.String r20) {
        /*
            Method dump skipped, instructions count: 746
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.QSTileHost.onTuningChanged(java.lang.String, java.lang.String):void");
    }

    public final void refreshTileList() {
        boolean z = DEBUG;
        if (z) {
            Log.d("QSTileHost", "refreshTileList");
        }
        if (this.mAutoTiles == null) {
            return;
        }
        ContentResolver contentResolver = this.mContext.getContentResolver();
        UserTracker userTracker = this.mUserTracker;
        List<String> loadTileSpecs = loadTileSpecs(this.mContext, Settings.Secure.getStringForUser(contentResolver, "sysui_qs_tiles", ((UserTrackerImpl) userTracker).getUserId()));
        int userId = ((UserTrackerImpl) userTracker).getUserId();
        this.mTiles.entrySet().stream().filter(new QSTileHost$$ExternalSyntheticLambda5(loadTileSpecs, 1)).forEach(new QSTileHost$$ExternalSyntheticLambda6(this, 1));
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (String str : loadTileSpecs) {
            QSTile qSTile = (QSTile) this.mTiles.get(str);
            Object obj = this.mTileUsingByPanel;
            SecQSTileInstanceManager secQSTileInstanceManager = this.mQSTileInstanceManager;
            if (qSTile == null || ((qSTile instanceof CustomTile) && ((CustomTile) qSTile).mUser != userId)) {
                if (z) {
                    MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("Creating tile: ", str, "QSTileHost");
                }
                try {
                    QSTile requestTileUsing = secQSTileInstanceManager.requestTileUsing(obj, str);
                    if (requestTileUsing != null) {
                        requestTileUsing.setTileSpec(str);
                        if (requestTileUsing.isAvailable()) {
                            linkedHashMap.put(str, requestTileUsing);
                        } else {
                            secQSTileInstanceManager.releaseTileUsing(obj, requestTileUsing.getTileSpec());
                        }
                    }
                } catch (Throwable th) {
                    Log.w("QSTileHost", "Error creating tile for spec: " + str, th);
                }
            } else if (qSTile.isAvailable()) {
                if (z) {
                    Log.d("QSTileHost", "Adding " + qSTile);
                }
                qSTile.removeCallbacks();
                linkedHashMap.put(str, qSTile);
            } else {
                secQSTileInstanceManager.releaseTileUsing(obj, qSTile.getTileSpec());
            }
        }
        this.mTileSpecs.clear();
        this.mTileSpecs.addAll(loadTileSpecs);
        this.mTiles.clear();
        this.mTiles.putAll(linkedHashMap);
        boolean z2 = SecQQSTileHost.DEBUG;
        SecQQSTileHost secQQSTileHost = this.mQQSTileHost;
        if (z2) {
            secQQSTileHost.getClass();
            Log.d("SecQQSTileHost", "refreshTileList");
        }
        ContentResolver contentResolver2 = secQQSTileHost.mContext.getContentResolver();
        UserTrackerImpl userTrackerImpl = (UserTrackerImpl) secQQSTileHost.mUserTracker;
        List<String> loadTileSpecs2 = secQQSTileHost.loadTileSpecs(secQQSTileHost.mContext, Settings.Secure.getStringForUser(contentResolver2, "sysui_quick_qs_tiles", userTrackerImpl.getUserId()));
        Log.d("SecQQSTileHost", "loaded tiles :" + loadTileSpecs2);
        int userId2 = userTrackerImpl.getUserId();
        secQQSTileHost.mTiles.entrySet().stream().filter(new SecQQSTileHost$$ExternalSyntheticLambda4(loadTileSpecs2, 1)).forEach(new SecQQSTileHost$$ExternalSyntheticLambda5(secQQSTileHost, 1));
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        for (String str2 : loadTileSpecs2) {
            QSTile qSTile2 = (QSTile) secQQSTileHost.mTiles.get(str2);
            Object obj2 = secQQSTileHost.mTileUsingByQQS;
            SecQSTileInstanceManager secQSTileInstanceManager2 = secQQSTileHost.mQSTileInstanceManager;
            if (qSTile2 == null || ((qSTile2 instanceof CustomTile) && ((CustomTile) qSTile2).mUser != userId2)) {
                MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("Creating tile: ", str2, "SecQQSTileHost");
                try {
                    QSTile requestTileUsing2 = secQSTileInstanceManager2.requestTileUsing(obj2, str2);
                    if (requestTileUsing2 != null) {
                        requestTileUsing2.setTileSpec(str2);
                        if (requestTileUsing2.isAvailable()) {
                            linkedHashMap2.put(str2, requestTileUsing2);
                        } else {
                            secQSTileInstanceManager2.releaseTileUsing(obj2, requestTileUsing2.getTileSpec());
                        }
                    }
                } catch (Throwable th2) {
                    Log.w("SecQQSTileHost", "Error creating tile for spec: " + str2, th2);
                }
            } else if (qSTile2.isAvailable()) {
                if (z2) {
                    Log.d("SecQQSTileHost", "Adding " + qSTile2);
                }
                qSTile2.removeCallbacks();
                linkedHashMap2.put(str2, qSTile2);
            } else {
                secQSTileInstanceManager2.releaseTileUsing(obj2, qSTile2.getTileSpec());
                secQQSTileHost.mQSLogger.logTileDestroyed(str2, "Tile not available at QQS");
                Log.d("SecQQSTileHost", "Destroying not available tile: ".concat(str2));
            }
        }
        secQQSTileHost.mCurrentUser = userId2;
        secQQSTileHost.mTileSpecs.clear();
        secQQSTileHost.mTileSpecs.addAll(loadTileSpecs2);
        secQQSTileHost.mTiles.clear();
        secQQSTileHost.mTiles.putAll(linkedHashMap2);
        secQQSTileHost.onTilesChanged();
        for (int i = 0; i < ((ArrayList) this.mCallbacks).size(); i++) {
            ((QSHost.Callback) ((ArrayList) this.mCallbacks).get(i)).onTilesChanged();
        }
        updateSearchableTiles();
    }

    @Override // com.android.systemui.qs.QSHost
    public final void removeCallback(QSHost.Callback callback) {
        ((ArrayList) this.mCallbacks).remove(callback);
    }

    @Override // com.android.systemui.qs.QSHost
    public final void removeTile(String str) {
        if (str.startsWith("custom(")) {
            setTileAdded(CustomTile.getComponentFromSpec(str), false, this.mCurrentUser);
        }
        this.mMainExecutor.execute(new QSTileHost$$ExternalSyntheticLambda4(this, str, 0));
    }

    @Override // com.android.systemui.qs.QSHost
    public final void removeTileByUser(ComponentName componentName) {
        this.mMainExecutor.execute(new QSTileHost$$ExternalSyntheticLambda12(this, componentName, 2));
    }

    @Override // com.android.systemui.qs.QSHost
    public final void removeTiles(Collection collection) {
        this.mMainExecutor.execute(new QSTileHost$$ExternalSyntheticLambda12(this, collection, 1));
    }

    public final void resetTileList() {
        List loadTileSpecs = loadTileSpecs(this.mContext, Settings.Secure.getStringForUser(this.mContext.getContentResolver(), "sysui_qs_tiles", ((UserTrackerImpl) this.mUserTracker).getUserId()));
        Settings.Secure.putStringForUser(this.mContext.getContentResolver(), "sysui_removed_qs_tiles", "", ((UserTrackerImpl) this.mUserTracker).getUserId());
        changeTilesByUser(loadTileSpecs, loadTileSpecs(this.mContext, ""));
        SecQQSTileHost secQQSTileHost = this.mQQSTileHost;
        Settings.Secure.putStringForUser(secQQSTileHost.mContext.getContentResolver(), "sysui_quick_qs_tiles", "", ((UserTrackerImpl) secQQSTileHost.mUserTracker).getUserId());
        if (ScRune.QUICK_MANAGE_SUBSCREEN_TILE_LIST) {
            SecSubScreenQSTileHost secSubScreenQSTileHost = this.mSubScreenTileHost;
            Settings.Secure.putStringForUser(secSubScreenQSTileHost.mContext.getContentResolver(), "sysui_sub_qs_tiles", "", ((UserTrackerImpl) secSubScreenQSTileHost.mUserTracker).getUserId());
        }
    }

    public final void saveTilesToSettings(List list) {
        Log.d("QSTileHost", "Saving tiles: " + list + " for user: " + this.mCurrentUser);
        this.mSecureSettings.putStringForUser("sysui_qs_tiles", TextUtils.join(",", list), null, false, ((UserTrackerImpl) this.mUserTracker).getUserId(), true);
    }

    @Override // com.android.systemui.qs.QSHost
    public final void sendTileStatusLog(int i, String str) {
        new Handler((Looper) Dependency.sDependency.getDependencyInner(Dependency.BG_LOOPER)).post(new QSTileHost$$ExternalSyntheticLambda3(this, str, i, 0));
    }

    @Override // com.android.systemui.qs.pipeline.data.repository.CustomTileAddedRepository
    public final void setTileAdded(ComponentName componentName, boolean z, int i) {
        ((UserFileManagerImpl) this.mUserFileManager).getSharedPreferences$1(i, TILES).edit().putBoolean(componentName.flattenToString(), z).apply();
    }

    @Override // com.android.systemui.qs.QSHost
    public final boolean shouldBeHiddenByKnox(String str) {
        List<String> list = this.mKnoxBlockedQsTileList;
        if (list == null) {
            return false;
        }
        for (String str2 : list) {
            String customTileSpecFromTileName = !isSystemTile(str2) ? getCustomTileSpecFromTileName(str2) : changeOldOSTileNameToNewName(str2);
            if (customTileSpecFromTileName != null && customTileSpecFromTileName.equals(str)) {
                Log.d("QSTileHost", "shouldBeHiddenByKnox : tileName = ".concat(customTileSpecFromTileName));
                return true;
            }
        }
        return false;
    }

    @Override // com.android.systemui.qs.QSHost
    public final boolean shouldUnavailableByKnox(String str) {
        List<String> list = this.mKnoxUnavailableQsTileList;
        if (list == null) {
            return false;
        }
        for (String str2 : list) {
            String customTileSpecFromTileName = !isSystemTile(str2) ? getCustomTileSpecFromTileName(str2) : changeOldOSTileNameToNewName(str2);
            if (customTileSpecFromTileName != null && customTileSpecFromTileName.equals(str)) {
                Log.d("QSTileHost", "shouldUnavailableByKnox : tileName = ".concat(customTileSpecFromTileName));
                return true;
            }
        }
        return false;
    }

    public final void updateHiddenBarTilesListByKnox() {
        this.mHiddenTilesByKnoxInTopBottomBar.clear();
        for (String str : this.mTopBarTileList.split(",")) {
            if (this.mKnoxBlockedQsTileList.contains(str)) {
                this.mHiddenTilesByKnoxInTopBottomBar.add(str);
            }
        }
        for (String str2 : this.mBottomBarTileList.split(",")) {
            if (this.mKnoxBlockedQsTileList.contains(str2)) {
                this.mHiddenTilesByKnoxInTopBottomBar.add(str2);
            }
        }
        for (String str3 : this.mBrightnessVolumeBarTileList.split(",")) {
            if (this.mKnoxBlockedQsTileList.contains(str3)) {
                this.mHiddenTilesByKnoxInTopBottomBar.add(str3);
            }
        }
        if (this.mKnoxBlockedQsTileList.contains(this.mSmartViewBarTileList)) {
            this.mHiddenTilesByKnoxInTopBottomBar.add(this.mSmartViewBarTileList);
        }
    }

    public final void updateSearchableTiles() {
        final ArrayList arrayList = new ArrayList(this.mTileSpecs);
        Handler handler = new Handler((Looper) Dependency.sDependency.getDependencyInner(Dependency.BG_LOOPER));
        final HashMap hashMap = new HashMap();
        handler.post(new Runnable() { // from class: com.android.systemui.qs.QSTileHost$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                QSTileImpl qSTileImpl;
                QSTileHost qSTileHost = QSTileHost.this;
                List<String> list = arrayList;
                HashMap hashMap2 = hashMap;
                qSTileHost.getClass();
                for (String str : list) {
                    QSTile qSTile = (QSTile) qSTileHost.mTiles.get(str);
                    if (qSTileHost.isAvailableForSearch(str, (qSTile == null || !(qSTile instanceof CustomTile)) ? false : ((CustomTile) qSTile).mIsSecCustomTile) && (qSTileImpl = (QSTileImpl) qSTile) != null && hashMap2.get(str) == null) {
                        hashMap2.put(str, qSTileImpl);
                    }
                }
                qSTileHost.mSearchables.clear();
                qSTileHost.mSearchables.addAll(hashMap2.values());
                qSTileHost.mMainExecutor.execute(new QSTileHost$$ExternalSyntheticLambda13(qSTileHost, 1));
            }
        });
    }

    @Override // com.android.systemui.qs.QSHost
    public final void addTile(ComponentName componentName) {
        if (componentName != null) {
            List loadTileSpecs = loadTileSpecs(this.mContext, Settings.Secure.getStringForUser(this.mContext.getContentResolver(), "sysui_qs_tiles", ((UserTrackerImpl) this.mUserTracker).getUserId()));
            ArrayList arrayList = new ArrayList(loadTileSpecs);
            arrayList.add(0, CustomTile.toSpec(componentName));
            changeTilesByUser(loadTileSpecs, arrayList);
        }
    }

    @Override // com.android.systemui.qs.QSHost
    public final void addTile(ComponentName componentName, boolean z) {
        addTile(z ? -1 : 0, CustomTile.toSpec(componentName));
    }
}
