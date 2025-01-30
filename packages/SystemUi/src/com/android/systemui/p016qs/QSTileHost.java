package com.android.systemui.p016qs;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.content.res.Resources;
import android.graphics.Point;
import android.hardware.display.DisplayManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.os.SemSystemProperties;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.ArrayLinkedVariables$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import androidx.core.graphics.PathParser$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierViewController$$ExternalSyntheticOutline0;
import com.android.systemui.BootAnimationFinishedCache;
import com.android.systemui.BootAnimationFinishedCacheImpl;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.Operator;
import com.android.systemui.Prefs;
import com.android.systemui.ProtoDumpable;
import com.android.systemui.R;
import com.android.systemui.ScRune;
import com.android.systemui.dump.nano.SystemUIProtoDump;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.indexsearch.Searchable;
import com.android.systemui.indexsearch.SystemUIIndexMediator;
import com.android.systemui.keyboard.KeyboardUI$$ExternalSyntheticOutline0;
import com.android.systemui.knox.CustomSdkMonitor;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorCallback;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.p016qs.QSBackupRestoreManager;
import com.android.systemui.p016qs.QSHost;
import com.android.systemui.p016qs.external.CustomTile;
import com.android.systemui.p016qs.external.CustomTileStatePersister;
import com.android.systemui.p016qs.external.TileLifecycleManager;
import com.android.systemui.p016qs.external.TileLifecycleManager$$ExternalSyntheticLambda1;
import com.android.systemui.p016qs.external.TileServiceKey;
import com.android.systemui.p016qs.logging.QSLogger;
import com.android.systemui.p016qs.nano.QsTileState;
import com.android.systemui.p016qs.pipeline.data.repository.CustomTileAddedRepository;
import com.android.systemui.p016qs.pipeline.domain.interactor.PanelInteractor;
import com.android.systemui.plugins.Plugin;
import com.android.systemui.plugins.PluginListener;
import com.android.systemui.plugins.PluginManager;
import com.android.systemui.plugins.p013qs.QSFactory;
import com.android.systemui.plugins.p013qs.QSTile;
import com.android.systemui.plugins.p013qs.QSTileView;
import com.android.systemui.settings.UserFileManager;
import com.android.systemui.settings.UserFileManagerImpl;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.QsResetSettingsManager;
import com.android.systemui.util.concurrency.ExecutorImpl;
import com.android.systemui.util.settings.SecureSettings;
import com.android.systemui.util.settings.SecureSettingsImpl;
import com.samsung.android.feature.SemCarrierFeature;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.SemPersonaManager;
import com.sec.ims.configuration.DATA;
import com.sec.ims.settings.ImsProfile;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class QSTileHost implements QSHost, TunerService.Tunable, PluginListener, ProtoDumpable, PanelInteractor, CustomTileAddedRepository {
    public static final boolean DEBUG = Log.isLoggable("QSTileHost", 3);
    public static final boolean LOGGING_DEBUG = Log.isLoggable("SA_QUICK_SETTINGS", 3);
    static final String TILES = "tiles_prefs";
    public SecAutoTileManager mAutoTiles;
    public String mBnRRemovedTileList;
    public String mBnRTileList;
    public String mBottomBarTileList;
    public String mBrightnessBarTileList;
    public final Optional mCentralSurfacesOptional;
    public HashMap mComponentNameTable;
    public final Context mContext;
    public int mCurrentUser;
    public final CustomTileStatePersister mCustomTileStatePersister;
    public final C20572 mDemoResetSettingsApplier;
    public final SharedPreferences.Editor mEditor;
    public final FeatureFlags mFeatureFlags;
    public final Handler mHandler;
    public final ArrayList mHiddenTilesByKnoxInTopBottomBar;
    public boolean mIsQQSosUpdating;
    public boolean mIsRestoring;
    public List mKnoxBlockedQsTileList;
    public final C20594 mKnoxStateCallback;
    public KnoxStateMonitor mKnoxStateMonitor;
    public List mKnoxUnavailableQsTileList;
    public final Executor mMainExecutor;
    public final PluginManager mPluginManager;
    public final SecQQSTileHost mQQSTileHost;
    public final QSLogger mQSLogger;
    public final SecQSTileInstanceManager mQSTileInstanceManager;
    public final ArrayList mQsFactories;
    public final C20561 mResetSettingsApplier;
    public final SecQSPanelResourcePicker mResourcePicker;
    public ArrayList mSearchAllowTileList;
    public final ArrayList mSearchables;
    public final SecureSettings mSecureSettings;
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.qs.QSTileHost$4 */
    public final class C20594 extends KnoxStateMonitorCallback {
        public C20594() {
        }

        /* JADX WARN: Removed duplicated region for block: B:10:0x001e  */
        /* JADX WARN: Removed duplicated region for block: B:20:0x0047  */
        @Override // com.android.systemui.knox.KnoxStateMonitorCallback
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void onUpdateQuickPanelButtons() {
            boolean z;
            QSTileHost qSTileHost = QSTileHost.this;
            CustomSdkMonitor customSdkMonitor = ((KnoxStateMonitorImpl) qSTileHost.mKnoxStateMonitor).mCustomSdkMonitor;
            int i = 0;
            if (customSdkMonitor != null) {
                if ((customSdkMonitor.mKnoxCustomQuickPanelButtons & 4) == 4) {
                    z = true;
                    if (!z) {
                        if (qSTileHost.mBrightnessBarTileList.isEmpty()) {
                            String string = qSTileHost.mContext.getResources().getString(R.string.sec_brightness_bar_tiles_default);
                            qSTileHost.mBrightnessBarTileList = string;
                            String[] split = string.split(",");
                            int length = split.length;
                            while (i < length) {
                                qSTileHost.removeTile(split[i]);
                                i++;
                            }
                            return;
                        }
                        return;
                    }
                    if (qSTileHost.mBrightnessBarTileList.isEmpty()) {
                        return;
                    }
                    String str = qSTileHost.mBrightnessBarTileList;
                    qSTileHost.mBrightnessBarTileList = "";
                    String[] split2 = str.split(",");
                    int length2 = split2.length;
                    while (i < length2) {
                        qSTileHost.mMainExecutor.execute(new QSTileHost$$ExternalSyntheticLambda4(qSTileHost, split2[i], -1, 1));
                        i++;
                    }
                    qSTileHost.refreshTileList();
                    return;
                }
            }
            z = false;
            if (!z) {
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class TilesMap {
        public static final HashMap mTilesMap = new HashMap();
        public static TilesMap sInstance = null;
        public static final int SID_TILE_STATE = 1;

        public TilesMap(Context context) {
            Log.d("SystemUIAnalytics", "TilesMap");
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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r12v0, types: [com.android.systemui.qs.QSTileHost$1, com.android.systemui.util.QsResetSettingsManager$ResetSettingsApplier] */
    /* JADX WARN: Type inference failed for: r13v0, types: [com.android.systemui.qs.QSTileHost$2, com.android.systemui.util.QsResetSettingsManager$DemoResetSettingsApplier] */
    public QSTileHost(Context context, QSFactory qSFactory, final Executor executor, PluginManager pluginManager, TunerService tunerService, final Provider provider, Optional<CentralSurfaces> optional, QSLogger qSLogger, UserTracker userTracker, SecureSettings secureSettings, CustomTileStatePersister customTileStatePersister, TileLifecycleManager.Factory factory, UserFileManager userFileManager, FeatureFlags featureFlags, BootAnimationFinishedCache bootAnimationFinishedCache) {
        ArrayList arrayList = new ArrayList();
        this.mQsFactories = arrayList;
        this.mHandler = new Handler();
        this.mSearchables = new ArrayList();
        this.mIsQQSosUpdating = false;
        this.mHiddenTilesByKnoxInTopBottomBar = new ArrayList();
        this.mTilesListDirty = true;
        this.mTileUsingByBar = new Object();
        this.mTileUsingByPanel = new Object();
        ?? r12 = new QsResetSettingsManager.ResetSettingsApplier() { // from class: com.android.systemui.qs.QSTileHost.1
            @Override // com.android.systemui.util.QsResetSettingsManager.ResetSettingsApplier
            public final void applyResetSetting() {
                QSTileHost qSTileHost = QSTileHost.this;
                qSTileHost.resetTileList();
                Settings.Global.putInt(qSTileHost.mContext.getContentResolver(), "swipe_directly_to_quick_setting", 0);
            }
        };
        this.mResetSettingsApplier = r12;
        ?? r13 = new QsResetSettingsManager.DemoResetSettingsApplier() { // from class: com.android.systemui.qs.QSTileHost.2
            @Override // com.android.systemui.util.QsResetSettingsManager.DemoResetSettingsApplier
            public final void applyDemoResetSetting() {
                Settings.Global.putInt(QSTileHost.this.mContext.getContentResolver(), "swipe_directly_to_quick_setting", 0);
            }
        };
        this.mDemoResetSettingsApplier = r13;
        this.mKnoxStateCallback = new C20594();
        this.mContext = context;
        this.mUserContext = context;
        this.mTunerService = tunerService;
        this.mPluginManager = pluginManager;
        this.mQSLogger = qSLogger;
        this.mMainExecutor = executor;
        this.mUserFileManager = userFileManager;
        this.mFeatureFlags = featureFlags;
        this.mCentralSurfacesOptional = optional;
        arrayList.add(qSFactory);
        pluginManager.addPluginListener((PluginListener) this, QSFactory.class, true);
        this.mUserTracker = userTracker;
        this.mSecureSettings = secureSettings;
        this.mCustomTileStatePersister = customTileStatePersister;
        SecQSPanelResourcePicker secQSPanelResourcePicker = (SecQSPanelResourcePicker) Dependency.get(SecQSPanelResourcePicker.class);
        this.mResourcePicker = secQSPanelResourcePicker;
        this.mUserManager = (UserManager) context.getSystemService("user");
        if (!Process.myUserHandle().equals(UserHandle.SYSTEM)) {
            Log.e("QSTileHost", "OPS not initialized for non-primary user, just return");
            return;
        }
        secQSPanelResourcePicker.getClass();
        this.mTopBarTileList = SecQSPanelResourcePicker.getTopBarTileList(context);
        this.mBrightnessBarTileList = context.getResources().getString(R.string.sec_brightness_bar_tiles_default);
        this.mBottomBarTileList = SecQSPanelResourcePicker.getBottomBarTileList(context);
        this.mQSTileInstanceManager = new SecQSTileInstanceManager(context, this, userTracker, qSLogger);
        this.mQQSTileHost = new SecQQSTileHost(context, this, userTracker, bootAnimationFinishedCache, qSLogger);
        if (ScRune.QUICK_MANAGE_SUBSCREEN_TILE_LIST) {
            this.mSubScreenTileHost = new SecSubScreenQSTileHost(context, this, userTracker, bootAnimationFinishedCache, qSLogger);
        }
        if (TilesMap.sInstance == null) {
            TilesMap.sInstance = new TilesMap(context);
        }
        this.mTilesMap = TilesMap.sInstance;
        this.mEditor = context.getSharedPreferences("quick_pref", 0).edit();
        ((BootAnimationFinishedCacheImpl) bootAnimationFinishedCache).addListener(new BootAnimationFinishedCache.BootAnimationFinishedListener() { // from class: com.android.systemui.qs.QSTileHost$$ExternalSyntheticLambda1
            @Override // com.android.systemui.BootAnimationFinishedCache.BootAnimationFinishedListener
            public final void onBootAnimationFinished() {
                QSTileHost qSTileHost = QSTileHost.this;
                qSTileHost.getClass();
                executor.execute(new QSTileHost$$ExternalSyntheticLambda7(qSTileHost, provider, 0));
            }
        });
        ((QsResetSettingsManager) Dependency.get(QsResetSettingsManager.class)).registerApplier(r12);
        ((QsResetSettingsManager) Dependency.get(QsResetSettingsManager.class)).registerDemoApplier(r13);
        ((QSBackupRestoreManager) Dependency.get(QSBackupRestoreManager.class)).addCallback("TileList", new QSBackupRestoreManager.Callback() { // from class: com.android.systemui.qs.QSTileHost.3
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
                    Context context2 = qSTileHost.mContext;
                    changeOldOSTileListToNewOsTileList = Settings.Secure.getString(context2.getContentResolver(), "sysui_qs_tiles");
                    if (changeOldOSTileListToNewOsTileList.isEmpty()) {
                        changeOldOSTileListToNewOsTileList = " ";
                    }
                    str = Settings.Secure.getString(context2.getContentResolver(), "sysui_removed_qs_tiles");
                    z2 = Prefs.getBoolean(context2, "QsHasEditedQuickTileList", false);
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
                Context context3 = qSTileHost.mQQSTileHost.mContext;
                String string = Settings.Secure.getString(context3.getContentResolver(), "sysui_quick_qs_tiles");
                sb2.append(Prefs.getBoolean(context3, "QQsHasEditedQuickTileList", false));
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
                int i = 1;
                String str3 = "";
                if (split.length <= 1) {
                    if (split[0].equals("removed_tile_list")) {
                        qSTileHost.mBnRRemovedTileList = "";
                        return;
                    }
                    return;
                }
                boolean equals = split[0].equals("tile_list");
                Context context2 = qSTileHost.mContext;
                if (!equals) {
                    if (split[0].equals("removed_tile_list")) {
                        qSTileHost.mBnRRemovedTileList = split[1];
                        return;
                    }
                    boolean equals2 = split[0].equals("qqs_tile_list");
                    SecQQSTileHost secQQSTileHost = qSTileHost.mQQSTileHost;
                    if (equals2) {
                        secQQSTileHost.setRestoreData(split[0], split[1]);
                        return;
                    }
                    if (split[0].equals("sep_version")) {
                        String str4 = split[1];
                        MotionLayout$$ExternalSyntheticOutline0.m23m("setRestoreData : sepVersion = ", str4, "QSTileHost");
                        if (str4 != null) {
                            qSTileHost.mSEPVersionOfBnRData = Integer.valueOf(str4).intValue();
                            return;
                        }
                        return;
                    }
                    if (!split[0].equals("has_edited")) {
                        if (split[0].equals("qqs_has_edited")) {
                            secQQSTileHost.setRestoreData(split[0], split[1]);
                            return;
                        }
                        return;
                    } else {
                        boolean booleanValue = Boolean.valueOf(split[1]).booleanValue();
                        Log.d("QSTileHost", "setRestoreData : hasEdited = " + booleanValue);
                        Prefs.putBoolean(context2, "QsHasEditedQuickTileList", booleanValue);
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
                        str6 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m15m(str6, str7, ",");
                    } else if (qSTileHost.isComponentAvailable(CustomTile.getComponentFromSpec(str7))) {
                        str6 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m15m(str6, str7, ",");
                    }
                }
                String str8 = qSTileHost.mBnRRemovedTileList;
                if (str8 == null || str8.isEmpty() || "null".equals(qSTileHost.mBnRRemovedTileList)) {
                    str2 = str6;
                } else {
                    str3 = qSTileHost.changeOldOSTileListToNewOsTileList(qSTileHost.mBnRRemovedTileList);
                    str2 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m15m(str6, ",", str3);
                }
                String stringForUser = Settings.Secure.getStringForUser(context2.getContentResolver(), "sysui_qs_tiles", ActivityManager.getCurrentUser());
                ArrayList arrayList2 = new ArrayList();
                for (String str9 : stringForUser.split(",")) {
                    arrayList2.add(str9);
                }
                Iterator it = arrayList2.iterator();
                while (it.hasNext()) {
                    String str10 = (String) it.next();
                    if (!str2.contains(str10)) {
                        str6 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m15m(str6, str10, ",");
                    }
                }
                String stringForUser2 = Settings.Secure.getStringForUser(context2.getContentResolver(), "sysui_removed_qs_tiles", ActivityManager.getCurrentUser());
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
                            str3 = str3.isEmpty() ? str12 : AbstractResolvableFuture$$ExternalSyntheticOutline0.m15m(str3, ",", str12);
                        }
                    }
                }
                int i2 = qSTileHost.mSEPVersionOfBnRData;
                if (i2 < Build.VERSION.SEM_PLATFORM_INT && i2 < 150000) {
                    qSTileHost.mMainExecutor.execute(new QSTileHost$$ExternalSyntheticLambda6(qSTileHost, str6, i));
                }
                qSTileHost.mSEPVersionOfBnRData = 0;
                qSTileHost.mIsRestoring = true;
                Log.d("QSTileHost", "bnrRemovedTileList  " + str3);
                Log.d("QSTileHost", "bnrTileList  " + str6);
                ContentResolver contentResolver = context2.getContentResolver();
                UserTrackerImpl userTrackerImpl = (UserTrackerImpl) qSTileHost.mUserTracker;
                Settings.Secure.putStringForUser(contentResolver, "sysui_removed_qs_tiles", str3, userTrackerImpl.getUserId());
                Settings.Secure.putStringForUser(context2.getContentResolver(), "sysui_qs_tiles", str6, userTrackerImpl.getUserId());
            }
        });
    }

    @Override // com.android.systemui.p016qs.QSHost
    public final void addCallback(QSHost.Callback callback) {
        this.mCallbacks.add(callback);
    }

    @Override // com.android.systemui.p016qs.QSHost
    public final void addTile(ComponentName componentName) {
        if (componentName != null) {
            Context context = this.mContext;
            List loadTileSpecs = loadTileSpecs(context, Settings.Secure.getStringForUser(context.getContentResolver(), "sysui_qs_tiles", ((UserTrackerImpl) this.mUserTracker).getUserId()));
            ArrayList arrayList = new ArrayList(loadTileSpecs);
            arrayList.add(0, CustomTile.toSpec(componentName));
            changeTilesByUser(loadTileSpecs, arrayList);
        }
    }

    public final String changeOldOSTileListToNewOsTileList(String str) {
        if (str == null) {
            return null;
        }
        String str2 = "";
        for (String str3 : str.split(",")) {
            String trim = str3.trim();
            if (!trim.isEmpty()) {
                str2 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m15m(str2, changeOldOSTileNameToNewName(trim), ",");
            }
        }
        return str2;
    }

    public final String changeOldOSTileNameToNewName(String str) {
        Locale locale = Locale.US;
        return "WIFIHOTSPOT".equals(str.toUpperCase(locale)) ? "Hotspot" : "AUTOROTATE".equals(str.toUpperCase(locale)) ? "RotationLock" : "TORCHLIGHT".equals(str.toUpperCase(locale)) ? "Flashlight" : ("SILENTMODE".equals(str.toUpperCase(locale)) || "SOUNDMODE".equals(str.toUpperCase(locale))) ? "SoundMode" : ("DND".equals(str.toUpperCase(locale)) || "DORMANTMODE".equals(str.toUpperCase(locale))) ? "Dnd" : "WORK".equals(str.toUpperCase(locale)) ? "WorkMode" : ("NIGHTMODE".equals(str.toUpperCase(locale)) || str.equals(getCustomTileSpecFromTileName("NightMode"))) ? "UiModeNight" : str.contains("com.samsung.accessibility/.vision.viewclear.extradim.ReduceBrightnessTileService") ? "ReduceBrightColors" : str.contains("com.samsung.accessibility/.vision.viewclear.HighContrastFontTileService") ? "HighContrastFont" : str.contains("com.samsung.accessibility/.vision.viewclear.ColorInversionTileService") ? "ColorInversion" : str.contains("com.samsung.accessibility/.vision.color.ColorLensTileService") ? "ColorLens" : str.contains("com.samsung.accessibility/.vision.color.ColorAdjustmentTileService") ? "ColorAdjustment" : str.contains("com.samsung.accessibility/.vision.color.AccessibilityColorCorrectionTileService") ? "ColorCorrection" : str.contains("com.samsung.android.bixby.interpreter/.interpretation.view.InterpreterQuickTileService") ? "custom(com.samsung.android.app.interpreter/.interpretation.view.InterpreterQuickTileService)" : str;
    }

    public final void changeTileSpecs(Predicate predicate) {
        List loadTileSpecs;
        if (this.mTilesListDirty) {
            loadTileSpecs = loadTileSpecs(this.mContext, ((SecureSettingsImpl) this.mSecureSettings).getStringForUser(((UserTrackerImpl) this.mUserTracker).getUserId(), "sysui_qs_tiles"));
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

    @Override // com.android.systemui.p016qs.QSHost
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
                        ((ExecutorImpl) tileLifecycleManager.mExecutor).execute(new TileLifecycleManager$$ExternalSyntheticLambda1(tileLifecycleManager, 2));
                        ComponentName componentFromSpec = CustomTile.getComponentFromSpec(str);
                        this.mCustomTileStatePersister.sharedPreferences.edit().remove(new TileServiceKey(componentFromSpec, this.mCurrentUser).string).apply();
                        setTileAdded(componentFromSpec, false, this.mCurrentUser);
                    }
                }
            }
        }
        if (list2.isEmpty()) {
            list2.add("empty");
        }
        if (DEBUG) {
            Log.d("QSTileHost", "saveCurrentTiles " + list2);
        }
        this.mTilesListDirty = true;
        saveTilesToSettings(list2);
    }

    @Override // com.android.systemui.p016qs.pipeline.domain.interactor.PanelInteractor
    public final void collapsePanels() {
        this.mCentralSurfacesOptional.ifPresent(new QSTileHost$$ExternalSyntheticLambda0(1));
    }

    public final QSTile createTile(String str) {
        int i = 0;
        while (true) {
            ArrayList arrayList = this.mQsFactories;
            if (i >= arrayList.size()) {
                return null;
            }
            QSTile createTile = ((QSFactory) arrayList.get(i)).createTile(str);
            if (createTile != null) {
                return createTile;
            }
            i++;
        }
    }

    @Override // com.android.systemui.p016qs.QSHost
    public final QSTileView createTileView(Context context, QSTile qSTile, boolean z) {
        int i = 0;
        while (true) {
            ArrayList arrayList = this.mQsFactories;
            if (i >= arrayList.size()) {
                throw new RuntimeException("Default factory didn't create view for " + qSTile.getTileSpec());
            }
            QSTileView createTileView = ((QSFactory) arrayList.get(i)).createTileView(context, qSTile, z);
            if (createTileView != null) {
                return createTileView;
            }
            i++;
        }
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(final PrintWriter printWriter, final String[] strArr) {
        printWriter.println("QSTileHost:");
        this.mTiles.values().stream().filter(new QSTileHost$$ExternalSyntheticLambda10(1)).forEach(new Consumer() { // from class: com.android.systemui.qs.QSTileHost$$ExternalSyntheticLambda11
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
            printWriter.print(((Searchable) it.next()).getSearchTitle() + ", ");
        }
        printWriter.println();
        if (ScRune.QUICK_MANAGE_SUBSCREEN_TILE_LIST) {
            SecSubScreenQSTileHost secSubScreenQSTileHost = this.mSubScreenTileHost;
            secSubScreenQSTileHost.getClass();
            printWriter.println("SecSubScreenQSTileHost:");
            secSubScreenQSTileHost.mTiles.values().stream().filter(new SecSubScreenQSTileHost$$ExternalSyntheticLambda4()).forEach(new Consumer() { // from class: com.android.systemui.qs.SecSubScreenQSTileHost$$ExternalSyntheticLambda5
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((Dumpable) ((QSTile) obj)).dump(printWriter, strArr);
                }
            });
            printWriter.println("  mTileUsingBySubScreen : " + secSubScreenQSTileHost.mTileUsingBySubScreen);
        }
        SecQQSTileHost secQQSTileHost = this.mQQSTileHost;
        secQQSTileHost.getClass();
        printWriter.println("SecQQSTileHost:");
        KeyboardUI$$ExternalSyntheticOutline0.m134m(new StringBuilder("  mBnRQQSTileList : "), secQQSTileHost.mBnRQQSTileList, printWriter);
        secQQSTileHost.mTiles.values().stream().filter(new SecQQSTileHost$$ExternalSyntheticLambda4()).forEach(new Consumer() { // from class: com.android.systemui.qs.SecQQSTileHost$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((Dumpable) ((QSTile) obj)).dump(printWriter, strArr);
            }
        });
        printWriter.println("  mTileUsingByQQS : " + secQQSTileHost.mTileUsingByQQS);
        printWriter.println("  mTileUsingByBar : " + this.mTileUsingByBar);
        printWriter.println("  mTileUsingByPanel : " + this.mTileUsingByPanel);
        SecQSTileInstanceManager secQSTileInstanceManager = this.mQSTileInstanceManager;
        secQSTileInstanceManager.getClass();
        printWriter.println("SecQSTileInstanceManager:");
        printWriter.println("  mTileInstances : " + secQSTileInstanceManager.mTileInstances);
        printWriter.println("  mTileUsingHosts : " + secQSTileInstanceManager.mTileUsingHosts);
    }

    @Override // com.android.systemui.ProtoDumpable
    public final void dumpProto(SystemUIProtoDump systemUIProtoDump) {
        final int i = 0;
        final int i2 = 1;
        systemUIProtoDump.tiles = (QsTileState[]) ((List) this.mTiles.values().stream().map(new Function() { // from class: com.android.systemui.qs.QSTileHost$$ExternalSyntheticLambda9
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                switch (i) {
                    case 0:
                        return ((QSTile) obj).getState();
                    default:
                        return TileStateToProtoKt.toProto((QSTile.State) obj);
                }
            }
        }).map(new Function() { // from class: com.android.systemui.qs.QSTileHost$$ExternalSyntheticLambda9
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                switch (i2) {
                    case 0:
                        return ((QSTile) obj).getState();
                    default:
                        return TileStateToProtoKt.toProto((QSTile.State) obj);
                }
            }
        }).filter(new QSTileHost$$ExternalSyntheticLambda10(0)).collect(Collectors.toList())).toArray(new QsTileState[0]);
    }

    @Override // com.android.systemui.p016qs.pipeline.domain.interactor.PanelInteractor
    public final void forceCollapsePanels() {
        this.mCentralSurfacesOptional.ifPresent(new QSTileHost$$ExternalSyntheticLambda0(0));
    }

    /* JADX WARN: Removed duplicated region for block: B:64:0x00e0  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x00e2  */
    @Override // com.android.systemui.p016qs.QSHost
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final ArrayList getBarTilesByType(int i, Context context) {
        String topBarTileList;
        int i2;
        String str;
        ArrayList arrayList = new ArrayList();
        boolean isEmpty = this.mHiddenTilesByKnoxInTopBottomBar.isEmpty();
        SecQSPanelResourcePicker secQSPanelResourcePicker = this.mResourcePicker;
        if (isEmpty) {
            if (i == 0) {
                secQSPanelResourcePicker.getClass();
                topBarTileList = SecQSPanelResourcePicker.getTopBarTileList(context);
                this.mTopBarTileList = topBarTileList;
            } else if (i == 1) {
                topBarTileList = this.mBrightnessBarTileList;
            } else {
                if (i != 2) {
                    return null;
                }
                secQSPanelResourcePicker.getClass();
                topBarTileList = SecQSPanelResourcePicker.getBottomBarTileList(context);
                this.mBottomBarTileList = topBarTileList;
            }
            String str2 = topBarTileList;
            i2 = 0;
            str = str2;
        } else {
            str = "";
            if (i == 0 || i == 2) {
                secQSPanelResourcePicker.getClass();
                this.mTopBarTileList = SecQSPanelResourcePicker.getTopBarTileList(context);
                this.mBottomBarTileList = SecQSPanelResourcePicker.getBottomBarTileList(context);
                int i3 = 0;
                String str3 = "";
                for (String str4 : this.mTopBarTileList.split(",")) {
                    if (!this.mKnoxBlockedQsTileList.contains(str4)) {
                        str3 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m15m(str3, str4, ",");
                        i3++;
                    }
                }
                int i4 = 0;
                String str5 = "";
                for (String str6 : this.mBottomBarTileList.split(",")) {
                    if (!this.mKnoxBlockedQsTileList.contains(str6)) {
                        str5 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m15m(str5, str6, ",");
                        i4++;
                    }
                }
                if (i3 == 1 && i4 > 0) {
                    String[] split = str5.split(",");
                    if (split.length > 0) {
                        String str7 = split[0];
                        Log.d("QSTileHost", "getBarTilesByType tileName=" + str7 + " bottomBarList=" + str5);
                        StringBuilder sb = new StringBuilder();
                        sb.append(str3);
                        sb.append(str7);
                        str3 = sb.toString();
                        str5 = str5.replace(str7 + ",", "");
                        AbstractC0000x2c234b15.m3m("getBarTilesByType after bottomBarList=", str5, "QSTileHost");
                        i2 = 0;
                        if (i != 0) {
                            str = str3;
                        } else if (i == 2) {
                            str = str5;
                        }
                    }
                }
                i2 = 0;
                if (i != 0) {
                }
            } else {
                if (i == 1) {
                    for (String str8 : this.mBrightnessBarTileList.split(",")) {
                        if (!this.mKnoxBlockedQsTileList.contains(str8)) {
                            str = AbstractResolvableFuture$$ExternalSyntheticOutline0.m15m(str, str8, ",");
                        }
                    }
                }
                i2 = 0;
            }
        }
        String[] split2 = str.split(",");
        int length = split2.length;
        while (i2 < length) {
            String trim = split2[i2].trim();
            if (!trim.isEmpty()) {
                if (!isSystemTile(trim)) {
                    if (isAvailableCustomTile(trim)) {
                        trim = getCustomTileSpecFromTileName(trim);
                    }
                }
                QSTile qSTile = (QSTile) this.mTiles.get(trim);
                if (qSTile == null) {
                    qSTile = this.mQSTileInstanceManager.requestTileUsing(this.mTileUsingByBar, trim);
                }
                if (qSTile != null) {
                    arrayList.add(qSTile);
                }
            }
            i2++;
        }
        Log.d("QSTileHost", "getBarTilesByType type=" + i + " tiles=" + arrayList);
        return arrayList;
    }

    @Override // com.android.systemui.p016qs.QSHost
    public final Context getContext() {
        return this.mContext;
    }

    @Override // com.android.systemui.p016qs.QSHost
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
            return PathParser$$ExternalSyntheticOutline0.m29m("custom(", str2, ")");
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

    @Override // com.android.systemui.p016qs.QSHost
    public final SecQQSTileHost getQQSTileHost() {
        return this.mQQSTileHost;
    }

    public final String getSupportedAllTileList() {
        Resources resources = this.mContext.getResources();
        String string = resources.getString(R.string.sec_quick_settings_tiles_default);
        boolean z = Operator.QUICK_IS_VZW_BRANDING;
        String changeOldOSTileListToNewOsTileList = changeOldOSTileListToNewOsTileList(("true".equals(SemSystemProperties.get("mdc.singlesku")) && "true".equals(SemSystemProperties.get("mdc.unified"))) ? SemCarrierFeature.getInstance().getString(0, "CarrierFeature_SystemUI_ConfigDefQuickSettingItem", string, false) : SemCscFeature.getInstance().getString("CscFeature_SystemUI_ConfigDefQuickSettingItem", string));
        String string2 = resources.getString(R.string.quick_settings_auto_adding_tiles);
        String string3 = SemCscFeature.getInstance().getString("CscFeature_SystemUI_ConfigRemoveQuickSettingItem", "");
        ArrayList arrayList = new ArrayList();
        for (String str : string3.split(",")) {
            String trim = str.trim();
            if (!trim.isEmpty()) {
                arrayList.add(trim);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        for (String str2 : changeOldOSTileListToNewOsTileList.split(",")) {
            String trim2 = str2.trim();
            if (!trim2.isEmpty() && !arrayList.contains(trim2)) {
                if ("Bluetooth".equals(trim2) && arrayList2.contains("SoundMode")) {
                    arrayList2.add(arrayList2.indexOf("SoundMode"), trim2);
                } else {
                    arrayList2.add(trim2);
                }
                if ("Dnd".equals(trim2) && DeviceType.isTablet()) {
                    for (String str3 : resources.getString(R.string.quick_settings_additional_default_tiles_tablet).split(",")) {
                        String trim3 = str3.trim();
                        if (!trim3.isEmpty() && !arrayList.contains(trim3)) {
                            arrayList2.add(trim3);
                        }
                    }
                }
            }
        }
        String[] split = string2.split(",");
        for (String str4 : split) {
            if (!str4.isEmpty()) {
                int indexOf = str4.indexOf(":");
                String substring = str4.substring(0, indexOf);
                int intValue = Integer.valueOf(str4.substring(indexOf + 1, str4.length())).intValue();
                if (!arrayList2.contains(substring) && !arrayList.contains(substring) && (!"CameraSharing".equals(substring) || DeviceType.isTablet())) {
                    if (intValue < 0 || intValue > arrayList2.size()) {
                        arrayList2.add(substring);
                    } else {
                        arrayList2.add(intValue, substring);
                    }
                    Log.d("QSTileHost", "getSupportedAllTileList : tileName = " + substring + ", tileIndex = " + intValue);
                }
            }
        }
        Log.d("QSTileHost", "getSupportedAllTileList result : " + arrayList2);
        return TextUtils.join(",", arrayList2);
    }

    @Override // com.android.systemui.p016qs.QSHost
    public final Collection getTiles() {
        return this.mTiles.values();
    }

    @Override // com.android.systemui.p016qs.QSHost
    public final Context getUserContext() {
        return this.mUserContext;
    }

    @Override // com.android.systemui.p016qs.QSHost
    public final int getUserId() {
        return this.mCurrentUser;
    }

    @Override // com.android.systemui.p016qs.QSHost
    public final int indexOf(String str) {
        return this.mTileSpecs.indexOf(str);
    }

    public final void initTunerServiceAndAutoTile(Provider provider) {
        this.mTunerService.addTunable(this, "sysui_qs_tiles");
        this.mAutoTiles = (SecAutoTileManager) provider.get();
        KnoxStateMonitor knoxStateMonitor = (KnoxStateMonitor) Dependency.get(KnoxStateMonitor.class);
        this.mKnoxStateMonitor = knoxStateMonitor;
        C20594 c20594 = this.mKnoxStateCallback;
        ((KnoxStateMonitorImpl) knoxStateMonitor).registerCallback(c20594);
        c20594.onUpdateQuickPanelButtons();
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

    /* JADX WARN: Removed duplicated region for block: B:165:? A[RETURN, SYNTHETIC] */
    @Override // com.android.systemui.p016qs.QSHost
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean isAvailableCustomTile(String str) {
        boolean z;
        boolean z2;
        boolean equals = "Dolby".equals(str);
        UserTracker userTracker = this.mUserTracker;
        if (equals) {
            if (SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_MMFW_SUPPORT_DOLBY_AUDIO", false) && ((UserTrackerImpl) userTracker).getUserId() == 0) {
                return true;
            }
            Log.d("QSTileHost", "isAvailableCustomTile : DolbyTile is removed ");
            return false;
        }
        if ("Aod".equals(str)) {
            if (SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_AOD_ITEM", "").contains("aodversion")) {
                return true;
            }
            Log.d("QSTileHost", "isAvailableCustomTile : AodTile is removed ");
            return false;
        }
        boolean equals2 = "AllShareCast".equals(str);
        Context context = this.mContext;
        if (equals2) {
            int semCheckScreenSharingSupported = ((DisplayManager) context.getSystemService("display")).semCheckScreenSharingSupported();
            if (semCheckScreenSharingSupported == 1 || semCheckScreenSharingSupported == 0) {
                return true;
            }
            Log.d("QSTileHost", "isAvailableCustomTile : AllShareCastTile is removed ");
            return false;
        }
        if ("Nfc".equals(str)) {
            if (context.getPackageManager().hasSystemFeature("android.hardware.nfc")) {
                return true;
            }
            Log.d("QSTileHost", "isAvailableCustomTile : NfcTile is removed ");
            return false;
        }
        if ("SecureFolder".equals(str)) {
            SemPersonaManager semPersonaManager = (SemPersonaManager) context.getSystemService("persona");
            if (semPersonaManager != null) {
                return semPersonaManager.isUserManaged() && ((UserTrackerImpl) userTracker).getUserId() == 0;
            }
            return false;
        }
        if ("UDS".equals(str)) {
            String string = SemCscFeature.getInstance().getString("CscFeature_SmartManager_ConfigSubFeatures");
            return string != null && string.contains("UDS");
        }
        if ("Sync".equals(str)) {
            if (!this.mUserManager.getUserInfo(((UserTrackerImpl) userTracker).getUserId()).isRestricted()) {
                return true;
            }
            Log.d("QSTileHost", "isAvailableCustomTile : Sync is removed ");
            return false;
        }
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
            if (SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_BLE_SPEN")) {
                return true;
            }
            int i = DeviceType.supportTablet;
            String string2 = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_SPEN_GARAGE_SPEC");
            if (!TextUtils.isEmpty(string2)) {
                for (String str2 : string2.toLowerCase().replaceAll(" ", "").split(",")) {
                    String[] split = str2.split("=");
                    if (split.length == 2) {
                        String str3 = split[0];
                        String str4 = split[1];
                        if ("unbundled_spec".equals(str3) && str4.contains("remote")) {
                            z2 = true;
                            break;
                        }
                    }
                }
            }
            z2 = false;
            return z2;
        }
        if ("PowerShare".equals(str)) {
            return SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_BATTERY_SUPPORT_WIRELESS_TX");
        }
        if ("PowerKeySetting".equals(str)) {
            String string3 = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_BIXBY_CONFIG_HWKEY");
            if (TextUtils.isEmpty(string3)) {
                return false;
            }
            return string3.contains("pwrkey");
        }
        if ("QRScanner".equals(str)) {
            return SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_CAMERA_SUPPORT_QRCODE") && isPackageAvailable("com.sec.android.app.camera");
        }
        if ("ScreenRecorder".equals(str)) {
            return SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_SCREEN_RECORDER");
        }
        if ("InstantSession".equals(str)) {
            return true;
        }
        if ("Routines".equals(str)) {
            return isPackageAvailable("com.samsung.android.app.routines");
        }
        if ("KidsHome".equals(str)) {
            return isPackageAvailable("com.samsung.android.kidsinstaller");
        }
        if ("SecondScreen".equals(str)) {
            return true;
        }
        if (!"TurnOn5g".equals(str)) {
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
            return true;
        }
        if (!DeviceType.mIsSupport5GChecked) {
            try {
                int parseInt = Integer.parseInt(SystemProperties.get("ro.telephony.default_network", DATA.DM_FIELD_INDEX.PCSCF_DOMAIN));
                if (parseInt >= 23) {
                    DeviceType.mIsSupport5G = true;
                }
                Log.i("DeviceType", "default network mode : " + parseInt);
            } catch (NumberFormatException unused) {
                Log.i("DeviceType", "NumberFormatException in isSupport5GConcept");
            }
            ActionBarContextView$$ExternalSyntheticOutline0.m9m(new StringBuilder("isSupport5GConcept : "), DeviceType.mIsSupport5G, "DeviceType");
            DeviceType.mIsSupport5GChecked = true;
        }
        if (DeviceType.mIsSupport5G) {
            if (!Operator.isChinaQsTileBranding()) {
                if (!(Operator.QUICK_IS_BRI_BRANDING || Operator.QUICK_IS_TGY_BRANDING)) {
                    z = true;
                    if (z) {
                        return true;
                    }
                }
            }
            z = false;
            if (z) {
            }
        }
        return false;
    }

    @Override // com.android.systemui.p016qs.QSHost
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

    @Override // com.android.systemui.p016qs.QSHost
    public final boolean isBarTile(String str) {
        if (str == null || (str.startsWith("custom(") && (str = getCustomTileNameFromSpec(str)) == null)) {
            return false;
        }
        return this.mTopBarTileList.contains(str) || this.mBrightnessBarTileList.contains(str) || this.mBottomBarTileList.contains(str);
    }

    @Override // com.android.systemui.p016qs.QSHost
    public final boolean isBrightnessBarTile(String str) {
        if (str.startsWith("custom(")) {
            str = getCustomTileNameFromSpec(str);
        }
        return str != null && this.mBrightnessBarTileList.contains(str);
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

    @Override // com.android.systemui.p016qs.QSHost
    public final boolean isLargeBarTile(String str) {
        if (str.startsWith("custom(")) {
            str = getCustomTileNameFromSpec(str);
        }
        if (str != null) {
            return this.mTopBarTileList.contains(str) || this.mBottomBarTileList.contains(str);
        }
        return false;
    }

    public final boolean isPackageAvailable(String str) {
        try {
            this.mContext.getPackageManager().getPackageInfoAsUser(str, 0, ((UserTrackerImpl) this.mUserTracker).getUserId());
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            if (DEBUG) {
                Log.d("QSTileHost", "Package not available: " + str, e);
            } else {
                AbstractC0000x2c234b15.m3m("Package not available: ", str, "QSTileHost");
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
        int i = 0;
        while (true) {
            ArrayList arrayList = this.mQsFactories;
            if (i >= arrayList.size()) {
                return false;
            }
            if (((QSFactory) arrayList.get(i)).isSystemTile(changeOldOSTileNameToNewName)) {
                return true;
            }
            i++;
        }
    }

    @Override // com.android.systemui.p016qs.pipeline.data.repository.CustomTileAddedRepository
    public final boolean isTileAdded(int i, ComponentName componentName) {
        return ((UserFileManagerImpl) this.mUserFileManager).getSharedPreferences(i, TILES).getBoolean(componentName.flattenToString(), false);
    }

    @Override // com.android.systemui.p016qs.QSHost
    public final boolean isUnsupportedTile(String str) {
        for (String str2 : this.mContext.getResources().getString(R.string.quick_settings_unsupported_tiles).split(",")) {
            if (str2.equals(str)) {
                AbstractC0000x2c234b15.m3m("isUnsupportedTile ", str, "QSTileHost");
                return true;
            }
        }
        return false;
    }

    public final List loadTileSpecs(Context context, String str) {
        boolean z;
        SecAutoTileManager secAutoTileManager;
        context.getResources();
        Point point = DeviceState.sDisplaySize;
        String string = Prefs.getString(context, "FingerprintVersion", "unknown");
        String string2 = Prefs.getString(context, "CSCVersion", "unknown");
        String string3 = Prefs.getString(context, "SalesCode", "unknown");
        String str2 = SystemProperties.get("ro.build.fingerprint", "unknown");
        String str3 = SystemProperties.get("ril.official_cscver", "unknown");
        String str4 = SystemProperties.get("ro.csc.sales_code", "unknown");
        if (string.equals(str2) && string2.equals(str3) && string3.equals(str4)) {
            z = false;
        } else {
            Log.d("DeviceState", "isFotaUpdate!!");
            Prefs.putString(context, "FingerprintVersion", str2);
            Prefs.putString(context, "CSCVersion", str3);
            Prefs.putString(context, "SalesCode", str4);
            z = true;
        }
        boolean z2 = DEBUG;
        if (str != null && !TextUtils.isEmpty(str) && !z && !this.mIsRestoring) {
            if (z2) {
                Log.d("QSTileHost", "Loaded tile specs from setting: ".concat(str));
            }
            ArrayList arrayList = new ArrayList();
            ArraySet arraySet = new ArraySet();
            boolean z3 = false;
            for (String str5 : str.split(",")) {
                String trim = str5.trim();
                if (!trim.isEmpty() && !isBarTile(trim)) {
                    if (trim.equals("default")) {
                        if (!z3) {
                            Iterator it = ((ArrayList) QSHost.getDefaultSpecs(context.getResources())).iterator();
                            while (it.hasNext()) {
                                String str6 = (String) it.next();
                                if (!arraySet.contains(str6)) {
                                    arrayList.add(str6);
                                    arraySet.add(str6);
                                }
                            }
                            z3 = true;
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
        boolean z4 = z || this.mIsRestoring;
        ArrayList arrayList2 = new ArrayList();
        String defaultTileList = getDefaultTileList();
        Context context2 = this.mContext;
        ContentResolver contentResolver = context2.getContentResolver();
        UserTrackerImpl userTrackerImpl = (UserTrackerImpl) this.mUserTracker;
        String stringForUser = Settings.Secure.getStringForUser(contentResolver, "sysui_removed_qs_tiles", userTrackerImpl.getUserId());
        String changeOldOSTileListToNewOsTileList = changeOldOSTileListToNewOsTileList(str);
        Settings.Secure.putStringForUser(context2.getContentResolver(), "sysui_removed_qs_tiles", changeOldOSTileListToNewOsTileList(stringForUser), userTrackerImpl.getUserId());
        if (z2) {
            AbstractC0000x2c234b15.m3m("Loaded tile specs from csc: ", defaultTileList, "QSTileHost");
        }
        if ("".equals(defaultTileList)) {
            arrayList2 = null;
        } else {
            if (z4) {
                if (changeOldOSTileListToNewOsTileList != null) {
                    ArrayList arrayList3 = new ArrayList();
                    makeCustomTileNameTable();
                    String[] split = changeOldOSTileListToNewOsTileList.split(",");
                    int length = split.length;
                    int i = 0;
                    while (i < length) {
                        String str7 = split[i];
                        String[] strArr = split;
                        if (str7.startsWith("custom(") && isDefaultCustomTile(CustomTile.getComponentFromSpec(str7))) {
                            str7 = getCustomTileNameFromSpec(str7);
                        }
                        if (str7 != null) {
                            arrayList3.add(str7);
                        }
                        i++;
                        split = strArr;
                    }
                    changeOldOSTileListToNewOsTileList = TextUtils.join(",", arrayList3);
                }
                Log.d("QSTileHost", "getRecalculatedTileListForFota ");
                if (changeOldOSTileListToNewOsTileList != null) {
                    ArrayList arrayList4 = new ArrayList();
                    ArrayList arrayList5 = new ArrayList();
                    String[] split2 = changeOldOSTileListToNewOsTileList.split(",");
                    int length2 = split2.length;
                    int i2 = 0;
                    while (i2 < length2) {
                        String[] strArr2 = split2;
                        String trim2 = split2[i2].trim();
                        if (!trim2.isEmpty() && !isUnsupportedTile(trim2) && !isBarTile(trim2)) {
                            arrayList4.add(trim2);
                        }
                        i2++;
                        split2 = strArr2;
                    }
                    Log.d("QSTileHost", "oldLists : " + arrayList4);
                    for (String str8 : defaultTileList.split(",")) {
                        String trim3 = str8.trim();
                        if (!trim3.isEmpty()) {
                            arrayList5.add(trim3);
                        }
                    }
                    Log.d("QSTileHost", "newLists : " + arrayList5);
                    for (int i3 = 0; i3 < arrayList5.size(); i3++) {
                        if (!arrayList4.contains(arrayList5.get(i3))) {
                            int indexOf = arrayList5.indexOf(arrayList5.get(i3));
                            if (arrayList4.size() < indexOf) {
                                arrayList4.add((String) arrayList5.get(i3));
                            } else {
                                arrayList4.add(indexOf, (String) arrayList5.get(i3));
                            }
                            StringBuilder sb = new StringBuilder();
                            sb.append(i3);
                            sb.append(" add : ");
                            ExifInterface$$ExternalSyntheticOutline0.m35m(sb, (String) arrayList5.get(i3), "QSTileHost");
                        }
                    }
                    defaultTileList = "";
                    for (int i4 = 0; i4 < arrayList4.size(); i4++) {
                        defaultTileList = AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(defaultTileList), (String) arrayList4.get(i4), ",");
                    }
                }
            }
            Log.d("QSTileHost", "loadTileSpecsFromCscFeature : loadedTileList = " + defaultTileList);
            makeCustomTileComponentNameTable();
            String[] split3 = defaultTileList.split(",");
            for (String str9 : split3) {
                if (!isBarTile(str9)) {
                    if (isSystemTile(str9)) {
                        if (!isRemovedTile(str9)) {
                            arrayList2.add(str9);
                        }
                    } else if (isAvailableCustomTile(str9)) {
                        String customTileSpecFromTileName = getCustomTileSpecFromTileName(str9);
                        if (customTileSpecFromTileName != null) {
                            if ((!SemPersonaManager.isDoEnabled(this.mCurrentUser) || isComponentAvailable(CustomTile.getComponentFromSpec(customTileSpecFromTileName))) && !isRemovedTile(customTileSpecFromTileName) && ((secAutoTileManager = this.mAutoTiles) == null || !secAutoTileManager.isRemovedTileByAppIntent(customTileSpecFromTileName))) {
                                arrayList2.add(customTileSpecFromTileName);
                            }
                        } else if (z4) {
                            if (str9.startsWith("custom(") && !isDefaultCustomTile(CustomTile.getComponentFromSpec(str9))) {
                                arrayList2.add(str9);
                            }
                        }
                    }
                }
            }
            Log.d("QSTileHost", "loadTileSpecsFromCscFeature : tiles = " + arrayList2);
            Settings.Secure.putStringForUser(context2.getContentResolver(), "sysui_qs_tiles", TextUtils.join(",", arrayList2), userTrackerImpl.getUserId());
        }
        this.mIsRestoring = false;
        if (z) {
            boolean z5 = this.mIsQQSosUpdating ? Prefs.getBoolean(context2, "QsHasEditedQuickTileList", false) : Prefs.getBoolean(context2, "QQsHasEditedQuickTileList", false);
            KeyguardCarrierViewController$$ExternalSyntheticOutline0.m63m(new StringBuilder("isQQSosUpdating="), this.mIsQQSosUpdating, " hasEdited=", z5, "QSTileHost");
            boolean z6 = !z5;
            SecQQSTileHost secQQSTileHost = this.mQQSTileHost;
            secQQSTileHost.getClass();
            ArrayList arrayList6 = new ArrayList();
            Iterator it2 = secQQSTileHost.mTileSpecs.iterator();
            while (it2.hasNext()) {
                String str10 = (String) it2.next();
                QSTileHost qSTileHost = secQQSTileHost.mQSTileHost;
                if (qSTileHost.isSystemTile(str10)) {
                    if (z6 && "Bluetooth".equals(str10) && arrayList6.contains("SoundMode")) {
                        arrayList6.add(arrayList6.indexOf("SoundMode"), str10);
                    } else {
                        arrayList6.add(str10);
                    }
                } else if (str10.startsWith("custom(")) {
                    String customTileNameFromSpec = qSTileHost.getCustomTileNameFromSpec(str10);
                    if (customTileNameFromSpec == null) {
                        arrayList6.add(str10);
                    } else if (qSTileHost.isAvailableCustomTile(customTileNameFromSpec)) {
                        arrayList6.add(str10);
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

    /* JADX WARN: Can't wrap try/catch for region: R(9:57|(2:59|(3:77|(6:79|(1:81)|82|(1:86)|87|88)(2:89|90)|71))(1:91)|(1:64)|65|66|67|(2:69|70)(1:72)|71|55) */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x0244, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x0245, code lost:
    
        android.util.Log.w("QSTileHost", "Error creating tile for spec: " + r15, r0);
     */
    @Override // com.android.systemui.tuner.TunerService.Tunable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onTuningChanged(String str, String str2) {
        boolean z;
        SecSubScreenQSTileHost secSubScreenQSTileHost;
        String str3;
        String str4;
        if ("sysui_qs_tiles".equals(str)) {
            AbstractC0000x2c234b15.m3m("TILES_SETTING changed  ", str2, "QSTileHost");
            Flags flags = Flags.INSTANCE;
            this.mFeatureFlags.getClass();
            boolean z2 = str2 != null && str2.equals("empty");
            String str5 = ",";
            String str6 = "";
            Context context = this.mContext;
            if (str2 != null && !str2.equals("empty") && !str2.equals("")) {
                String string = context.getResources().getString(R.string.quick_settings_tiles_stock);
                for (String str7 : str2.split(",")) {
                    if (string.contains(str7)) {
                        MotionLayout$$ExternalSyntheticOutline0.m23m("include aospTiles ", str7, "QSTileHost");
                        z = true;
                        break;
                    }
                }
            }
            z = false;
            ArrayList arrayList = this.mTileSpecs;
            if (z) {
                changeTilesByUser(arrayList, loadTileSpecs(context, ""));
                return;
            }
            List loadTileSpecs = loadTileSpecs(context, str2);
            UserTracker userTracker = this.mUserTracker;
            final int userId = ((UserTrackerImpl) userTracker).getUserId();
            int i = this.mCurrentUser;
            SecSubScreenQSTileHost secSubScreenQSTileHost2 = this.mSubScreenTileHost;
            SecQQSTileHost secQQSTileHost = this.mQQSTileHost;
            final SecQSTileInstanceManager secQSTileInstanceManager = this.mQSTileInstanceManager;
            if (userId != i) {
                this.mUserContext = ((UserTrackerImpl) userTracker).getUserContext();
                if (secQSTileInstanceManager.mUserId != userId) {
                    secQSTileInstanceManager.mUserId = userId;
                    Log.i("SecQSTileInstanceManager", "onUserChanged to " + userId);
                    final ArrayMap arrayMap = new ArrayMap();
                    secQSTileInstanceManager.mTileInstances.keySet().stream().forEach(new Consumer() { // from class: com.android.systemui.qs.SecQSTileInstanceManager$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            SecQSTileInstanceManager secQSTileInstanceManager2 = SecQSTileInstanceManager.this;
                            ArrayMap arrayMap2 = arrayMap;
                            int i2 = userId;
                            String str8 = (String) obj;
                            LinkedHashMap linkedHashMap = secQSTileInstanceManager2.mTileInstances;
                            if (linkedHashMap.get(str8) instanceof CustomTile) {
                                arrayMap2.put(str8, new ArraySet((ArraySet) secQSTileInstanceManager2.mTileUsingHosts.get(str8)));
                            } else if (secQSTileInstanceManager2.mQSTileHost.isBarTile(str8)) {
                                ((QSTile) linkedHashMap.get(str8)).userSwitch(i2);
                            }
                        }
                    });
                    int i2 = 0;
                    while (i2 < arrayMap.size()) {
                        final String str8 = (String) arrayMap.keyAt(i2);
                        ((ArraySet) arrayMap.get(str8)).stream().forEach(new Consumer() { // from class: com.android.systemui.qs.SecQSTileInstanceManager$$ExternalSyntheticLambda1
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                SecQSTileInstanceManager.this.releaseTileUsing(obj, str8);
                            }
                        });
                        i2++;
                        arrayMap = arrayMap;
                    }
                }
                secQQSTileHost.mQSUserChanged = true;
                secQQSTileHost.onTuningChanged("sysui_quick_qs_tiles", Settings.Secure.getStringForUser(secQQSTileHost.mContext.getContentResolver(), "sysui_quick_qs_tiles", ((UserTrackerImpl) secQQSTileHost.mUserTracker).getUserId()));
                if (ScRune.QUICK_MANAGE_SUBSCREEN_TILE_LIST) {
                    secSubScreenQSTileHost2.mQSUserChanged = true;
                    secSubScreenQSTileHost2.onTuningChanged("sysui_sub_qs_tiles", Settings.Secure.getStringForUser(secSubScreenQSTileHost2.mContext.getContentResolver(), "sysui_sub_qs_tiles", ((UserTrackerImpl) secSubScreenQSTileHost2.mUserTracker).getUserId()));
                }
                SecAutoTileManager secAutoTileManager = this.mAutoTiles;
                if (secAutoTileManager != null) {
                    UserHandle.of(userId);
                    secAutoTileManager.getClass();
                }
                SystemUIIndexMediator systemUIIndexMediator = (SystemUIIndexMediator) Dependency.get(SystemUIIndexMediator.class);
                Context context2 = this.mUserContext;
                boolean z3 = userId == 0;
                systemUIIndexMediator.getClass();
                secSubScreenQSTileHost = secSubScreenQSTileHost2;
                context2.getPackageManager().setComponentEnabledSetting(new ComponentName("com.android.systemui", "com.android.systemui.indexsearch.SystemUIIndexProvider"), z3 ? 1 : 2, 1);
            } else {
                secSubScreenQSTileHost = secSubScreenQSTileHost2;
            }
            if (loadTileSpecs.equals(arrayList) && userId == this.mCurrentUser) {
                return;
            }
            Log.d("QSTileHost", "Recreating tiles: " + loadTileSpecs);
            LinkedHashMap linkedHashMap = this.mTiles;
            linkedHashMap.entrySet().stream().filter(new QSTileHost$$ExternalSyntheticLambda2(loadTileSpecs, 0)).forEach(new QSTileHost$$ExternalSyntheticLambda3(this, 0));
            LinkedHashMap linkedHashMap2 = new LinkedHashMap();
            Iterator it = loadTileSpecs.iterator();
            while (it.hasNext()) {
                String str9 = (String) it.next();
                QSTile qSTile = (QSTile) linkedHashMap.get(str9);
                Iterator it2 = it;
                QSLogger qSLogger = this.mQSLogger;
                SecQQSTileHost secQQSTileHost2 = secQQSTileHost;
                Object obj = this.mTileUsingByPanel;
                if (qSTile != null) {
                    str3 = str5;
                    boolean z4 = qSTile instanceof CustomTile;
                    str4 = str6;
                    if (!z4 || ((CustomTile) qSTile).mUser == userId) {
                        if (qSTile.isAvailable()) {
                            if (DEBUG) {
                                Log.d("QSTileHost", "Adding " + qSTile);
                            }
                            qSTile.removeCallbacks();
                            if (!z4 && this.mCurrentUser != userId) {
                                qSTile.userSwitch(userId);
                            }
                            linkedHashMap2.put(str9, qSTile);
                            qSLogger.logTileAdded(str9);
                        } else {
                            secQSTileInstanceManager.releaseTileUsing(obj, qSTile.getTileSpec());
                            Log.d("QSTileHost", "Destroying not available tile: " + str9);
                            qSLogger.logTileDestroyed(str9, "Tile not available");
                        }
                        it = it2;
                        secQQSTileHost = secQQSTileHost2;
                        str5 = str3;
                        str6 = str4;
                    }
                } else {
                    str3 = str5;
                    str4 = str6;
                }
                if (qSTile != null) {
                    secQSTileInstanceManager.releaseTileUsing(obj, qSTile.getTileSpec());
                    StringBuilder sb = new StringBuilder("Destroying tile for wrong user: ");
                    sb.append(userId);
                    ExifInterface$$ExternalSyntheticOutline0.m36m(sb, " ", str9, "QSTileHost");
                    qSLogger.logTileDestroyed(str9, "Tile for wrong user");
                }
                AbstractC0000x2c234b15.m3m("Creating tile: ", str9, "QSTileHost");
                QSTile requestTileUsing = secQSTileInstanceManager.requestTileUsing(obj, str9);
                if (requestTileUsing != null) {
                    linkedHashMap2.put(str9, requestTileUsing);
                }
                it = it2;
                secQQSTileHost = secQQSTileHost2;
                str5 = str3;
                str6 = str4;
            }
            String str10 = str5;
            String str11 = str6;
            SecQQSTileHost secQQSTileHost3 = secQQSTileHost;
            this.mCurrentUser = userId;
            List arrayList2 = new ArrayList(arrayList);
            arrayList.clear();
            arrayList.addAll(linkedHashMap2.keySet());
            linkedHashMap.clear();
            linkedHashMap.putAll(linkedHashMap2);
            if (!linkedHashMap2.isEmpty() || loadTileSpecs.isEmpty() || z2) {
                if (!z2 && !TextUtils.join(str10, arrayList).equals(str2)) {
                    saveTilesToSettings(arrayList);
                }
                int i3 = 0;
                this.mTilesListDirty = false;
                while (true) {
                    List list = this.mCallbacks;
                    if (i3 >= list.size()) {
                        break;
                    }
                    ((QSHost.Callback) list.get(i3)).onTilesChanged();
                    i3++;
                }
                secQQSTileHost3.onTilesChanged();
                if (ScRune.QUICK_MANAGE_SUBSCREEN_TILE_LIST) {
                    secSubScreenQSTileHost.onTilesChanged();
                }
                new Handler((Looper) Dependency.get(Dependency.BG_LOOPER)).post(new QSTileHost$$ExternalSyntheticLambda5(this, 0));
            } else {
                Log.d("QSTileHost", "No valid tiles on tuning changed. Setting to default.");
                changeTilesByUser(arrayList2, loadTileSpecs(context, str11));
            }
            updateSearchableTiles();
        }
    }

    public final void refreshTileList() {
        Log.d("QSTileHost", "refreshTileList");
        if (this.mAutoTiles == null) {
            return;
        }
        Context context = this.mContext;
        ContentResolver contentResolver = context.getContentResolver();
        UserTracker userTracker = this.mUserTracker;
        List<String> loadTileSpecs = loadTileSpecs(context, Settings.Secure.getStringForUser(contentResolver, "sysui_qs_tiles", ((UserTrackerImpl) userTracker).getUserId()));
        int userId = ((UserTrackerImpl) userTracker).getUserId();
        LinkedHashMap linkedHashMap = this.mTiles;
        linkedHashMap.entrySet().stream().filter(new QSTileHost$$ExternalSyntheticLambda2(loadTileSpecs, 1)).forEach(new QSTileHost$$ExternalSyntheticLambda3(this, 1));
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        for (String str : loadTileSpecs) {
            QSTile qSTile = (QSTile) linkedHashMap.get(str);
            boolean z = DEBUG;
            Object obj = this.mTileUsingByPanel;
            SecQSTileInstanceManager secQSTileInstanceManager = this.mQSTileInstanceManager;
            if (qSTile == null || ((qSTile instanceof CustomTile) && ((CustomTile) qSTile).mUser != userId)) {
                if (z) {
                    AbstractC0000x2c234b15.m3m("Creating tile: ", str, "QSTileHost");
                }
                try {
                    QSTile requestTileUsing = secQSTileInstanceManager.requestTileUsing(obj, str);
                    if (requestTileUsing != null) {
                        requestTileUsing.setTileSpec(str);
                        if (requestTileUsing.isAvailable()) {
                            linkedHashMap2.put(str, requestTileUsing);
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
                linkedHashMap2.put(str, qSTile);
            } else {
                secQSTileInstanceManager.releaseTileUsing(obj, qSTile.getTileSpec());
            }
        }
        ArrayList arrayList = this.mTileSpecs;
        arrayList.clear();
        arrayList.addAll(loadTileSpecs);
        linkedHashMap.clear();
        linkedHashMap.putAll(linkedHashMap2);
        this.mQQSTileHost.refreshTileList();
        int i = 0;
        while (true) {
            List list = this.mCallbacks;
            if (i >= list.size()) {
                updateSearchableTiles();
                return;
            } else {
                ((QSHost.Callback) list.get(i)).onTilesChanged();
                i++;
            }
        }
    }

    @Override // com.android.systemui.p016qs.QSHost
    public final void removeCallback(QSHost.Callback callback) {
        ((ArrayList) this.mCallbacks).remove(callback);
    }

    @Override // com.android.systemui.p016qs.QSHost
    public final void removeTile(String str) {
        int i = 0;
        if (str.startsWith("custom(")) {
            setTileAdded(CustomTile.getComponentFromSpec(str), false, this.mCurrentUser);
        }
        this.mMainExecutor.execute(new QSTileHost$$ExternalSyntheticLambda6(this, str, i));
    }

    @Override // com.android.systemui.p016qs.QSHost
    public final void removeTileByUser(ComponentName componentName) {
        this.mMainExecutor.execute(new QSTileHost$$ExternalSyntheticLambda7(this, componentName, 2));
    }

    @Override // com.android.systemui.p016qs.QSHost
    public final void removeTiles(Collection collection) {
        this.mMainExecutor.execute(new QSTileHost$$ExternalSyntheticLambda7(this, collection, 1));
    }

    public final void resetTileList() {
        Context context = this.mContext;
        ContentResolver contentResolver = context.getContentResolver();
        UserTracker userTracker = this.mUserTracker;
        List loadTileSpecs = loadTileSpecs(context, Settings.Secure.getStringForUser(contentResolver, "sysui_qs_tiles", ((UserTrackerImpl) userTracker).getUserId()));
        Settings.Secure.putStringForUser(context.getContentResolver(), "sysui_removed_qs_tiles", "", ((UserTrackerImpl) userTracker).getUserId());
        changeTilesByUser(loadTileSpecs, loadTileSpecs(context, ""));
        SecQQSTileHost secQQSTileHost = this.mQQSTileHost;
        Settings.Secure.putStringForUser(secQQSTileHost.mContext.getContentResolver(), "sysui_quick_qs_tiles", "", ((UserTrackerImpl) secQQSTileHost.mUserTracker).getUserId());
        if (ScRune.QUICK_MANAGE_SUBSCREEN_TILE_LIST) {
            SecSubScreenQSTileHost secSubScreenQSTileHost = this.mSubScreenTileHost;
            Settings.Secure.putStringForUser(secSubScreenQSTileHost.mContext.getContentResolver(), "sysui_sub_qs_tiles", "", ((UserTrackerImpl) secSubScreenQSTileHost.mUserTracker).getUserId());
        }
    }

    public final void saveTilesToSettings(List list) {
        String join = TextUtils.join(",", list);
        int userId = ((UserTrackerImpl) this.mUserTracker).getUserId();
        SecureSettingsImpl secureSettingsImpl = (SecureSettingsImpl) this.mSecureSettings;
        Settings.Secure.putStringForUser(secureSettingsImpl.mContentResolver, "sysui_qs_tiles", join, null, false, secureSettingsImpl.getRealUserHandle(userId), true);
    }

    @Override // com.android.systemui.p016qs.QSHost
    public final void sendTileStatusLog(int i, String str) {
        new Handler((Looper) Dependency.get(Dependency.BG_LOOPER)).post(new QSTileHost$$ExternalSyntheticLambda4(this, str, i, 0));
    }

    @Override // com.android.systemui.p016qs.pipeline.data.repository.CustomTileAddedRepository
    public final void setTileAdded(ComponentName componentName, boolean z, int i) {
        ((UserFileManagerImpl) this.mUserFileManager).getSharedPreferences(i, TILES).edit().putBoolean(componentName.flattenToString(), z).apply();
    }

    @Override // com.android.systemui.p016qs.QSHost
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

    @Override // com.android.systemui.p016qs.QSHost
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
        ArrayList arrayList = this.mHiddenTilesByKnoxInTopBottomBar;
        arrayList.clear();
        for (String str : this.mTopBarTileList.split(",")) {
            if (this.mKnoxBlockedQsTileList.contains(str)) {
                arrayList.add(str);
            }
        }
        for (String str2 : this.mBottomBarTileList.split(",")) {
            if (this.mKnoxBlockedQsTileList.contains(str2)) {
                arrayList.add(str2);
            }
        }
        for (String str3 : this.mBrightnessBarTileList.split(",")) {
            if (this.mKnoxBlockedQsTileList.contains(str3)) {
                arrayList.add(str3);
            }
        }
    }

    public final void updateSearchableTiles() {
        Settings.Secure.getStringForUser(this.mContext.getContentResolver(), "sysui_removed_qs_tiles", ActivityManager.getCurrentUser());
        final ArrayList arrayList = new ArrayList(this.mTileSpecs);
        Handler handler = new Handler((Looper) Dependency.get(Dependency.BG_LOOPER));
        final Handler handler2 = new Handler((Looper) Dependency.get(Dependency.MAIN_LOOPER));
        final HashMap hashMap = new HashMap();
        handler.post(new Runnable() { // from class: com.android.systemui.qs.QSTileHost$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                Searchable searchable;
                QSTileHost qSTileHost = QSTileHost.this;
                List<String> list = arrayList;
                HashMap hashMap2 = hashMap;
                Handler handler3 = handler2;
                qSTileHost.getClass();
                for (String str : list) {
                    QSTile qSTile = (QSTile) qSTileHost.mTiles.get(str);
                    if (qSTileHost.isAvailableForSearch(str, (qSTile == null || !(qSTile instanceof CustomTile)) ? false : ((CustomTile) qSTile).mIsSecCustomTile) && (searchable = (Searchable) qSTile) != null && hashMap2.get(str) == null) {
                        hashMap2.put(str, searchable);
                    }
                }
                ArrayList arrayList2 = qSTileHost.mSearchables;
                arrayList2.clear();
                arrayList2.addAll(hashMap2.values());
                handler3.post(new QSTileHost$$ExternalSyntheticLambda5(qSTileHost, 1));
            }
        });
    }

    @Override // com.android.systemui.p016qs.QSHost
    public final void addTile(ComponentName componentName, boolean z) {
        this.mMainExecutor.execute(new QSTileHost$$ExternalSyntheticLambda4(this, CustomTile.toSpec(componentName), z ? -1 : 0, 1));
    }
}
