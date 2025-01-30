package com.android.systemui.p016qs.tiles;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.provider.Settings;
import android.service.notification.ZenModeConfig;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.ArrayMap;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.internal.logging.MetricsLogger;
import com.android.keyguard.AbstractC0731x5bb8a836;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardFMMViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardFaceListenModel$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.settingslib.notification.EnableZenModeDialog;
import com.android.systemui.R;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.p016qs.DNDDetailItems;
import com.android.systemui.p016qs.QSHost;
import com.android.systemui.p016qs.QsEventLogger;
import com.android.systemui.p016qs.SecQSDetail;
import com.android.systemui.p016qs.SettingObserver;
import com.android.systemui.p016qs.logging.QSLogger;
import com.android.systemui.p016qs.pipeline.domain.interactor.PanelInteractor;
import com.android.systemui.p016qs.tileimpl.QSTileImpl;
import com.android.systemui.p016qs.tileimpl.SQSTileImpl;
import com.android.systemui.p016qs.tiles.dialog.QSZenModeDialogMetricsLogger;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.p013qs.DetailAdapter;
import com.android.systemui.plugins.p013qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.ZenModeController;
import com.android.systemui.statusbar.policy.ZenModeControllerImpl;
import com.android.systemui.util.settings.GlobalSettings;
import com.android.systemui.util.settings.GlobalSettingsImpl;
import com.android.systemui.util.settings.SecureSettings;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.sec.ims.configuration.DATA;
import com.sec.ims.volte2.data.VolteConstants;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DndTile extends SQSTileImpl {
    public static final Intent DND_SETTINGS;
    public static final int mZenOneHourSession;
    public final ZenModeController mController;
    public final DndDetailAdapter mDetailAdapter;
    public int mDndMenuSelectedItem;
    public String mDndMenuSummary;
    public final GlobalSettings mGlobalSettings;
    public boolean mIsSettingsUpdated;
    public int mLastDndDurationSelected;
    public boolean mListening;
    public final PanelInteractor mPanelInteractor;
    public final SharedPreferencesOnSharedPreferenceChangeListenerC22383 mPrefListener;
    public int mPreviousSetZenDuration;
    public SecQSDetail mSecQSDetail;
    public final C22361 mSettingZenDuration;
    public final C22372 mSettingsObserver;
    public final SharedPreferences mSharedPreferences;
    public final C22394 mZenCallback;

    static {
        new Intent("android.settings.ZEN_MODE_SETTINGS");
        new Intent("android.settings.ZEN_MODE_PRIORITY_SETTINGS");
        DND_SETTINGS = new Intent().setComponent(new ComponentName(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "com.android.settings.Settings$ZenModeSettingsActivity")).setAction("android.intent.action.MAIN");
        mZenOneHourSession = 60;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v3, types: [android.database.ContentObserver, com.android.systemui.qs.tiles.DndTile$2] */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.android.systemui.qs.tiles.DndTile$3] */
    /* JADX WARN: Type inference failed for: r3v3, types: [com.android.systemui.qs.tiles.DndTile$4, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r3v4, types: [com.android.systemui.qs.tiles.DndTile$1] */
    public DndTile(QSHost qSHost, QsEventLogger qsEventLogger, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, ZenModeController zenModeController, SharedPreferences sharedPreferences, SecureSettings secureSettings, DialogLaunchAnimator dialogLaunchAnimator, PanelInteractor panelInteractor, GlobalSettings globalSettings) {
        super(qSHost, qsEventLogger, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.mDndMenuSelectedItem = 0;
        this.mLastDndDurationSelected = -1;
        this.mIsSettingsUpdated = false;
        this.mPreviousSetZenDuration = -2;
        ?? r2 = new ContentObserver(this.mUiHandler) { // from class: com.android.systemui.qs.tiles.DndTile.2
            @Override // android.database.ContentObserver
            public final void onChange(boolean z, Uri uri) {
                boolean z2;
                super.onChange(z, uri);
                int value = getValue();
                RecyclerView$$ExternalSyntheticOutline0.m46m(KeyguardFMMViewController$$ExternalSyntheticOutline0.m66m("ZEN_MODE: onChange = ", z, ",currentZen = ", value, ",previousZen = "), DndTile.this.mPreviousSetZenDuration, "DndTile");
                DndTile dndTile = DndTile.this;
                int i = dndTile.mPreviousSetZenDuration;
                if (i == -2 || (z2 = dndTile.mIsSettingsUpdated)) {
                    dndTile.mPreviousSetZenDuration = value;
                } else if (!z2 && i != value) {
                    Settings.Secure.putInt(dndTile.mContext.getContentResolver(), "zen_duration", DndTile.this.mPreviousSetZenDuration);
                }
                DndTile.this.mIsSettingsUpdated = true;
            }
        };
        this.mSettingsObserver = r2;
        this.mPrefListener = new SharedPreferences.OnSharedPreferenceChangeListener() { // from class: com.android.systemui.qs.tiles.DndTile.3
            @Override // android.content.SharedPreferences.OnSharedPreferenceChangeListener
            public final void onSharedPreferenceChanged(SharedPreferences sharedPreferences2, String str) {
                if ("DndTileCombinedIcon".equals(str) || "DndTileVisible".equals(str)) {
                    DndTile.this.refreshState(null);
                }
            }
        };
        ?? r3 = new ZenModeController.Callback() { // from class: com.android.systemui.qs.tiles.DndTile.4
            @Override // com.android.systemui.statusbar.policy.ZenModeController.Callback
            public final void onZenChanged(int i) {
                Integer valueOf = Integer.valueOf(i);
                Intent intent = DndTile.DND_SETTINGS;
                DndTile.this.refreshState(valueOf);
            }
        };
        this.mZenCallback = r3;
        this.mController = zenModeController;
        this.mSharedPreferences = sharedPreferences;
        this.mDetailAdapter = new DndDetailAdapter();
        zenModeController.observe(((QSTileImpl) this).mLifecycle, r3);
        this.mSettingZenDuration = new SettingObserver(secureSettings, this.mUiHandler, "zen_duration", this.mHost.getUserId()) { // from class: com.android.systemui.qs.tiles.DndTile.1
            @Override // com.android.systemui.p016qs.SettingObserver
            public final void handleValueChanged(int i, boolean z) {
                AbstractC0731x5bb8a836.m72m("handleValueChanged: value = ", i, ",observedChange = ", z, "DndTile");
                DndTile.this.refreshState(null);
            }
        };
        this.mGlobalSettings = globalSettings;
        GlobalSettingsImpl globalSettingsImpl = (GlobalSettingsImpl) globalSettings;
        globalSettingsImpl.registerContentObserverForUser(globalSettingsImpl.getUriFor("zen_mode"), false, (ContentObserver) r2, globalSettingsImpl.getUserId());
        new QSZenModeDialogMetricsLogger(this.mContext);
        this.mPanelInteractor = panelInteractor;
    }

    public static String getStringFromMillis(Context context, long j) {
        if (j < 0 || j >= 1440) {
            return DateFormat.getTimeFormat(context).format(Long.valueOf(j));
        }
        Calendar calendar = Calendar.getInstance();
        int i = (int) j;
        calendar.set(11, i / 60);
        calendar.set(12, i % 60);
        return DateFormat.getTimeFormat(context).format(calendar.getTime());
    }

    public final String getApplicationNameFromPackage(String str) {
        PackageManager packageManager = ((ZenModeControllerImpl) this.mController).mContext.getPackageManager();
        try {
            return packageManager.getApplicationLabel(packageManager.getApplicationInfo(str, 0)).toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override // com.android.systemui.p016qs.tileimpl.SQSTileImpl, com.android.systemui.p016qs.tileimpl.QSTileImpl, com.android.systemui.plugins.p013qs.QSTile
    public final DetailAdapter getDetailAdapter() {
        return this.mDetailAdapter;
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl
    public final Intent getLongClickIntent() {
        return null;
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl, com.android.systemui.plugins.p013qs.QSTile
    public final int getMetricsCategory() {
        return 118;
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl, com.android.systemui.plugins.p013qs.QSTile
    public final CharSequence getTileLabel() {
        return this.mContext.getString(R.string.quick_settings_sec_dnd_label);
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl
    public final void handleClick(View view) {
        ZenModeControllerImpl zenModeControllerImpl = (ZenModeControllerImpl) this.mController;
        boolean hasUserRestriction = zenModeControllerImpl.mUserManager.hasUserRestriction("no_adjust_volume", UserHandle.of(zenModeControllerImpl.mUserId));
        Context context = this.mContext;
        if (hasUserRestriction) {
            this.mPanelInteractor.collapsePanels();
            Toast.makeText(context, context.getString(android.R.string.indeterminate_progress_12), 1).show();
            return;
        }
        QSTile.BooleanState booleanState = (QSTile.BooleanState) this.mState;
        if (!booleanState.value && booleanState.state == 2) {
            booleanState.value = zenModeControllerImpl.mZenMode != 0;
            Log.d("DndTile", "handleClick refresh value ");
        }
        ActionBarContextView$$ExternalSyntheticOutline0.m9m(new StringBuilder("handleClick ="), ((QSTile.BooleanState) this.mState).value, "DndTile");
        boolean z = !((QSTile.BooleanState) this.mState).value;
        if (z) {
            int i = Settings.Global.getInt(this.mHost.getUserContext().getContentResolver(), "zen_duration", 0);
            if (i == -1) {
                final ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, android.R.style.Theme.DeviceDefault.Settings);
                this.mUiHandler.post(new Runnable() { // from class: com.android.systemui.qs.tiles.DndTile$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        DndTile dndTile = DndTile.this;
                        ContextThemeWrapper contextThemeWrapper2 = contextThemeWrapper;
                        dndTile.getClass();
                        AlertDialog createDialog = new EnableZenModeDialog(contextThemeWrapper2).createDialog();
                        createDialog.getWindow().setType(2009);
                        SystemUIDialog.setShowForAllUsers(createDialog);
                        SystemUIDialog.registerDismissListener(createDialog, null);
                        SystemUIDialog.setWindowOnTop(createDialog, true);
                        SystemUIDialog.setDialogSize(createDialog);
                        dndTile.mUiHandler.post(new DndTile$$ExternalSyntheticLambda1(createDialog, 1));
                        dndTile.mPanelInteractor.collapsePanels();
                    }
                });
            } else if (i != 0) {
                zenModeControllerImpl.setZen(1, ZenModeConfig.toTimeCondition(context, i, ActivityManager.getCurrentUser(), true).id, "DndTile");
            } else {
                zenModeControllerImpl.setZen(1, null, "DndTile");
            }
        } else {
            zenModeControllerImpl.setZen(0, null, "DndTile");
        }
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("setZen state: ", z, "DndTile");
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl
    public final void handleDestroy() {
        super.handleDestroy();
        setListening(false);
        this.mGlobalSettings.unregisterContentObserver(this.mSettingsObserver);
    }

    @Override // com.android.systemui.p016qs.tileimpl.SQSTileImpl, com.android.systemui.p016qs.tileimpl.QSTileImpl
    public final void handleSecondaryClick(View view) {
        showDetail(true);
    }

    @Override // com.android.systemui.p016qs.tileimpl.SQSTileImpl, com.android.systemui.p016qs.tileimpl.QSTileImpl
    public final void handleSetListening(boolean z) {
        super.handleSetListening(z);
        if (this.mListening == z) {
            return;
        }
        this.mListening = z;
        SharedPreferencesOnSharedPreferenceChangeListenerC22383 sharedPreferencesOnSharedPreferenceChangeListenerC22383 = this.mPrefListener;
        Context context = this.mContext;
        if (z) {
            context.getSharedPreferences(context.getPackageName(), 0).registerOnSharedPreferenceChangeListener(sharedPreferencesOnSharedPreferenceChangeListenerC22383);
        } else {
            context.getSharedPreferences(context.getPackageName(), 0).unregisterOnSharedPreferenceChangeListener(sharedPreferencesOnSharedPreferenceChangeListenerC22383);
        }
        setListening(z);
        C22372 c22372 = this.mSettingsObserver;
        GlobalSettings globalSettings = this.mGlobalSettings;
        if (z) {
            globalSettings.unregisterContentObserver(c22372);
        } else {
            GlobalSettingsImpl globalSettingsImpl = (GlobalSettingsImpl) globalSettings;
            globalSettingsImpl.registerContentObserverForUser(globalSettingsImpl.getUriFor("zen_mode"), false, (ContentObserver) c22372, globalSettingsImpl.getUserId());
        }
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl
    public final void handleUpdateState(QSTile.State state, Object obj) {
        QSTile.BooleanState booleanState = (QSTile.BooleanState) state;
        ZenModeController zenModeController = this.mController;
        if (zenModeController == null) {
            return;
        }
        int intValue = obj instanceof Integer ? ((Integer) obj).intValue() : ((ZenModeControllerImpl) zenModeController).mZenMode;
        Log.d("DndTile", "handleUpdateState zen " + intValue + "  state = " + booleanState);
        boolean z = intValue != 0;
        boolean z2 = booleanState.value != z;
        booleanState.dualTarget = true;
        booleanState.value = z;
        booleanState.state = z ? 2 : 1;
        booleanState.label = this.mContext.getString(R.string.quick_settings_sec_dnd_label);
        booleanState.icon = QSTileImpl.ResourceIcon.get(R.drawable.quick_panel_icon_donot_disturb);
        if (z2) {
            fireToggleStateChanged(booleanState.value);
        }
        this.mUiHandler.post(new DndTile$$ExternalSyntheticLambda1(this, 0));
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl
    public final void handleUserSwitch(int i) {
        handleRefreshState(null);
        setUserId(i);
    }

    @Override // com.android.systemui.p016qs.tileimpl.SQSTileImpl, com.android.systemui.p016qs.tileimpl.QSTileImpl, com.android.systemui.plugins.p013qs.QSTile
    public final boolean isAvailable() {
        if (this.mSharedPreferences.getBoolean("DndTileVisible", false)) {
            return !this.mHost.shouldBeHiddenByKnox(this.mTileSpec);
        }
        return false;
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl
    public final QSTile.State newTileState() {
        return new QSTile.BooleanState();
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class DndDetailAdapter implements DetailAdapter, View.OnAttachStateChangeListener, DNDDetailItems.Callback {
        public static final /* synthetic */ int $r8$clinit = 0;
        public DNDDetailItems mDNDActivationItems;
        public ViewGroup mMenuOptions;
        public TextView mSummary;
        public final ArrayList mItemsList = new ArrayList();
        public final String[] mDndMenuOptions = new String[6];

        public DndDetailAdapter() {
        }

        /* JADX WARN: Removed duplicated region for block: B:104:0x0237 A[Catch: Exception -> 0x0261, TryCatch #2 {Exception -> 0x0261, blocks: (B:77:0x0131, B:81:0x01ad, B:83:0x01c1, B:85:0x01ca, B:88:0x01d0, B:89:0x01d2, B:94:0x01df, B:96:0x0201, B:97:0x0214, B:102:0x022c, B:104:0x0237, B:115:0x0245, B:116:0x0210, B:119:0x0152, B:121:0x015a, B:125:0x0165, B:128:0x016a, B:129:0x016f, B:152:0x017a, B:133:0x0184, B:148:0x018f, B:144:0x01a7, B:155:0x016d), top: B:76:0x0131 }] */
        /* JADX WARN: Removed duplicated region for block: B:107:0x0256  */
        /* JADX WARN: Removed duplicated region for block: B:115:0x0245 A[Catch: Exception -> 0x0261, TRY_LEAVE, TryCatch #2 {Exception -> 0x0261, blocks: (B:77:0x0131, B:81:0x01ad, B:83:0x01c1, B:85:0x01ca, B:88:0x01d0, B:89:0x01d2, B:94:0x01df, B:96:0x0201, B:97:0x0214, B:102:0x022c, B:104:0x0237, B:115:0x0245, B:116:0x0210, B:119:0x0152, B:121:0x015a, B:125:0x0165, B:128:0x016a, B:129:0x016f, B:152:0x017a, B:133:0x0184, B:148:0x018f, B:144:0x01a7, B:155:0x016d), top: B:76:0x0131 }] */
        /* JADX WARN: Removed duplicated region for block: B:118:0x0234  */
        /* JADX WARN: Removed duplicated region for block: B:136:0x0195  */
        /* JADX WARN: Removed duplicated region for block: B:140:0x019e  */
        /* JADX WARN: Removed duplicated region for block: B:147:0x018f A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:151:0x017a A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:34:0x00d1  */
        /* JADX WARN: Removed duplicated region for block: B:40:0x0357  */
        /* JADX WARN: Removed duplicated region for block: B:44:0x0383  */
        /* JADX WARN: Removed duplicated region for block: B:65:0x00f4  */
        /* JADX WARN: Removed duplicated region for block: B:83:0x01c1 A[Catch: Exception -> 0x0261, TryCatch #2 {Exception -> 0x0261, blocks: (B:77:0x0131, B:81:0x01ad, B:83:0x01c1, B:85:0x01ca, B:88:0x01d0, B:89:0x01d2, B:94:0x01df, B:96:0x0201, B:97:0x0214, B:102:0x022c, B:104:0x0237, B:115:0x0245, B:116:0x0210, B:119:0x0152, B:121:0x015a, B:125:0x0165, B:128:0x016a, B:129:0x016f, B:152:0x017a, B:133:0x0184, B:148:0x018f, B:144:0x01a7, B:155:0x016d), top: B:76:0x0131 }] */
        @Override // com.android.systemui.plugins.p013qs.DetailAdapter
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final View createDetailView(Context context, View view, ViewGroup viewGroup) {
            boolean z;
            String str;
            String str2;
            int i;
            boolean is24HourFormat;
            boolean contains;
            boolean is24HourFormat2;
            int indexOf;
            int i2;
            String substring;
            int parseInt;
            String substring2;
            int[] iArr;
            int i3;
            TextView textView;
            int i4;
            String string;
            ArrayMap arrayMap;
            boolean z2;
            Uri uri;
            DndTile dndTile = DndTile.this;
            Intent intent = DndTile.DND_SETTINGS;
            this.mSummary = (TextView) LayoutInflater.from(dndTile.mContext).inflate(R.layout.sec_qs_detail_text, viewGroup, false).findViewById(R.id.message);
            View inflate = LayoutInflater.from(DndTile.this.mContext).inflate(R.layout.sec_qs_detail_dnd, viewGroup, false);
            this.mSummary = (TextView) inflate.findViewById(R.id.dnd_summary);
            DndTile dndTile2 = DndTile.this;
            dndTile2.mGlobalSettings.unregisterContentObserver(dndTile2.mSettingsObserver);
            DndTile dndTile3 = DndTile.this;
            ZenModeConfig zenModeConfig = ((ZenModeControllerImpl) dndTile3.mController).mConfig;
            int i5 = 1;
            boolean z3 = (zenModeConfig == null || zenModeConfig.manualRule == null) ? false : true;
            boolean z4 = z3 && zenModeConfig.manualRule.conditionId == null;
            boolean z5 = z3 && (uri = zenModeConfig.manualRule.conditionId) != null && ZenModeConfig.isValidCountdownConditionId(uri);
            if (zenModeConfig != null && zenModeConfig.manualRule == null && (arrayMap = zenModeConfig.automaticRules) != null && !arrayMap.isEmpty()) {
                Iterator it = zenModeConfig.automaticRules.values().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        z2 = false;
                        break;
                    }
                    if (((ZenModeConfig.ZenRule) it.next()).isAutomaticActive()) {
                        z2 = true;
                        break;
                    }
                }
                if (z2) {
                    z = true;
                    StringBuilder m69m = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m69m("updateZenModeConfigState,isTurnOnAsManualRule: ", z3, ",isDurationForever: ", z4, ",isDurationTime: ");
                    KeyguardFaceListenModel$$ExternalSyntheticOutline0.m67m(m69m, z5, ",isAutomaticRule:", z, ",mIsSettingsUpdated:");
                    m69m.append(dndTile3.mIsSettingsUpdated);
                    Log.d("DndTile", m69m.toString());
                    StringBuilder sb = new StringBuilder();
                    str = "";
                    Context context2 = dndTile3.mContext;
                    if (z4) {
                        int i6 = -1;
                        if (z5) {
                            long tryParseCountdownConditionId = ZenModeConfig.tryParseCountdownConditionId(zenModeConfig.manualRule.conditionId);
                            boolean isToday = ZenModeConfig.isToday(tryParseCountdownConditionId);
                            CharSequence formattedTime = ZenModeConfig.getFormattedTime(context2, tryParseCountdownConditionId, isToday, context2.getUserId());
                            String string2 = isToday ? context2.getString(R.string.sec_dnd_detail_on_until_time_today, formattedTime) : context2.getString(R.string.sec_dnd_detail_on_until_time_tomorrow, formattedTime);
                            if (dndTile3.mIsSettingsUpdated) {
                                str = string2;
                            } else {
                                int i7 = dndTile3.mLastDndDurationSelected;
                                if (i7 == -1) {
                                    String str3 = (String) formattedTime;
                                    try {
                                        is24HourFormat = DateFormat.is24HourFormat(context2, context2.getUserId());
                                        contains = str3.contains("pm");
                                        is24HourFormat2 = DateFormat.is24HourFormat(context2, context2.getUserId());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    if (!TextUtils.isEmpty(str3) && (indexOf = str3.indexOf(58)) >= 1 && indexOf < str3.length() - 1) {
                                        if (!is24HourFormat2 && indexOf - 2 < 0) {
                                            i2 = indexOf - 1;
                                            substring = str3.substring(i2, indexOf);
                                            if (!TextUtils.isEmpty(substring)) {
                                                try {
                                                    parseInt = Integer.parseInt(substring);
                                                } catch (NumberFormatException unused) {
                                                }
                                                substring2 = str3.substring(indexOf + 1, indexOf + 3);
                                                if (!TextUtils.isEmpty(substring2)) {
                                                    try {
                                                        i6 = Integer.parseInt(substring2);
                                                    } catch (NumberFormatException unused2) {
                                                    }
                                                }
                                                if (parseInt < 0 && parseInt < 24) {
                                                    if (i6 >= 0 && i6 < 60) {
                                                        iArr = new int[]{parseInt, i6};
                                                        Calendar calendar = Calendar.getInstance();
                                                        int i8 = (calendar.get(11) * 60) + calendar.get(12);
                                                        if (iArr == null) {
                                                            int i9 = iArr[0];
                                                            int i10 = (i9 * 60) + iArr[1];
                                                            if (!isToday) {
                                                                i10 += 1440;
                                                            }
                                                            if (i10 < i8 && !is24HourFormat) {
                                                                i10 += 720;
                                                            }
                                                            i3 = (i10 - i8) / 60;
                                                            String str4 = ((i9 >= 10 || !is24HourFormat) ? "" : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN) + iArr[0] + ":";
                                                            StringBuilder sb2 = new StringBuilder();
                                                            sb2.append(str4);
                                                            int i11 = iArr[1];
                                                            sb2.append(i11 < 10 ? DATA.DM_FIELD_INDEX.PCSCF_DOMAIN + iArr[1] : Integer.valueOf(i11));
                                                            String sb3 = sb2.toString();
                                                            StringBuilder sb4 = new StringBuilder();
                                                            sb4.append(sb3);
                                                            sb4.append(is24HourFormat ? "" : contains ? " pm" : " am");
                                                            str3 = sb4.toString();
                                                        } else {
                                                            i3 = 4;
                                                        }
                                                        if (isToday) {
                                                            dndTile3.mDndMenuSummary = context2.getString(R.string.sec_dnd_detail_on_until_time_tomorrow, str3);
                                                        } else {
                                                            dndTile3.mDndMenuSummary = context2.getString(R.string.sec_dnd_detail_on_until_time_today, str3);
                                                        }
                                                        if (i3 >= 2) {
                                                            if (i3 < 2 || i3 >= 4) {
                                                                if (i3 == 4) {
                                                                    i5 = 3;
                                                                }
                                                                i5 = 5;
                                                            } else {
                                                                i5 = 2;
                                                            }
                                                        }
                                                        str = dndTile3.mDndMenuSummary;
                                                        i = i5;
                                                    }
                                                }
                                            }
                                            parseInt = -1;
                                            substring2 = str3.substring(indexOf + 1, indexOf + 3);
                                            if (!TextUtils.isEmpty(substring2)) {
                                            }
                                            if (parseInt < 0 && parseInt < 24) {
                                            }
                                        }
                                        i2 = indexOf - 2;
                                        substring = str3.substring(i2, indexOf);
                                        if (!TextUtils.isEmpty(substring)) {
                                        }
                                        parseInt = -1;
                                        substring2 = str3.substring(indexOf + 1, indexOf + 3);
                                        if (!TextUtils.isEmpty(substring2)) {
                                        }
                                        if (parseInt < 0 && parseInt < 24) {
                                        }
                                    }
                                    iArr = null;
                                    Calendar calendar2 = Calendar.getInstance();
                                    int i82 = (calendar2.get(11) * 60) + calendar2.get(12);
                                    if (iArr == null) {
                                    }
                                    if (isToday) {
                                    }
                                    if (i3 >= 2) {
                                    }
                                    str = dndTile3.mDndMenuSummary;
                                    i = i5;
                                } else {
                                    str = string2;
                                    i = i7;
                                }
                            }
                        } else {
                            if (z) {
                                dndTile3.mLastDndDurationSelected = -1;
                                String description = ZenModeConfig.getDescription(context2, true, zenModeConfig, false);
                                for (ZenModeConfig.ZenRule zenRule : zenModeConfig.automaticRules.values()) {
                                    if (zenRule.isAutomaticActive() && description != null && !description.isEmpty() && description.equals(zenRule.name)) {
                                        if (ZenModeConfig.isValidScheduleConditionId(zenRule.conditionId)) {
                                            long nextChangeTime = ZenModeConfig.toScheduleCalendar(zenRule.conditionId).getNextChangeTime(System.currentTimeMillis());
                                            CharSequence formattedTime2 = ZenModeConfig.getFormattedTime(context2, nextChangeTime, true, context2.getUserId());
                                            if (ZenModeConfig.isToday(nextChangeTime)) {
                                                sb.append(context2.getResources().getString(R.string.sec_dnd_detail_on_until_time_today, formattedTime2));
                                            } else {
                                                sb.append(context2.getResources().getString(R.string.sec_dnd_detail_on_until_time_tomorrow, formattedTime2));
                                            }
                                            str2 = sb.toString() + "\n" + context2.getResources().getString(R.string.sec_dnd_detail_turned_by_app_name, description);
                                        } else {
                                            str2 = context2.getResources().getString(R.string.sec_dnd_detail_turned_by_app_name, dndTile3.getApplicationNameFromPackage(zenRule.pkg)) + "\n" + ("(" + description + ')');
                                        }
                                        str = str2;
                                    }
                                }
                            }
                            i = 0;
                        }
                        i = 5;
                    } else {
                        String str5 = zenModeConfig.manualRule.enabler;
                        if (str5 != null) {
                            sb.append(dndTile3.getApplicationNameFromPackage(str5));
                            str = context2.getString(R.string.sec_zen_mode_footer_by_app_name, sb);
                            i = 0;
                        } else {
                            if (!dndTile3.mIsSettingsUpdated) {
                                i = 4;
                            }
                            i = 5;
                        }
                    }
                    dndTile3.mDndMenuSummary = str;
                    dndTile3.mDndMenuSelectedItem = i;
                    textView = this.mSummary;
                    if (textView != null) {
                        textView.setText(DndTile.this.mDndMenuSummary);
                    }
                    ViewGroup viewGroup2 = (ViewGroup) inflate.findViewById(R.id.dnd_menu_layout);
                    this.mMenuOptions = viewGroup2;
                    int i12 = DNDDetailItems.$r8$clinit;
                    DNDDetailItems dNDDetailItems = (DNDDetailItems) LayoutInflater.from(context).inflate(R.layout.sec_qs_detail_dnd_items, viewGroup2, false);
                    this.mDNDActivationItems = dNDDetailItems;
                    this.mMenuOptions.addView(dNDDetailItems);
                    for (i4 = 0; i4 < 6; i4++) {
                        if (i4 == 0) {
                            DndTile dndTile4 = DndTile.this;
                            Intent intent2 = DndTile.DND_SETTINGS;
                            string = dndTile4.mContext.getResources().getString(R.string.sec_qs_dnd_detail_off_option);
                        } else if (i4 < 4) {
                            int pow = (int) Math.pow(2.0d, i4 - 1);
                            DndTile dndTile5 = DndTile.this;
                            Intent intent3 = DndTile.DND_SETTINGS;
                            string = dndTile5.mContext.getResources().getQuantityString(R.plurals.sec_qs_dnd_detail_fhl_hours_option, pow, Integer.valueOf(pow));
                        } else if (i4 == 4) {
                            DndTile dndTile6 = DndTile.this;
                            Intent intent4 = DndTile.DND_SETTINGS;
                            string = dndTile6.mContext.getResources().getString(R.string.sec_qs_dnd_detail_until_i_turn_off_option);
                        } else {
                            DndTile dndTile7 = DndTile.this;
                            Intent intent5 = DndTile.DND_SETTINGS;
                            string = dndTile7.mContext.getResources().getString(R.string.sec_qs_dnd_detail_on_option);
                        }
                        this.mDndMenuOptions[i4] = string;
                    }
                    updateDndActivationItems(false);
                    DNDDetailItems dNDDetailItems2 = this.mDNDActivationItems;
                    dNDDetailItems2.getClass();
                    dNDDetailItems2.mTag = "DNDDetailItems.Do not disturb";
                    DNDDetailItems dNDDetailItems3 = this.mDNDActivationItems;
                    dNDDetailItems3.mHandler.removeMessages(2);
                    dNDDetailItems3.mHandler.obtainMessage(2, this).sendToTarget();
                    return inflate;
                }
            }
            z = false;
            StringBuilder m69m2 = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m69m("updateZenModeConfigState,isTurnOnAsManualRule: ", z3, ",isDurationForever: ", z4, ",isDurationTime: ");
            KeyguardFaceListenModel$$ExternalSyntheticOutline0.m67m(m69m2, z5, ",isAutomaticRule:", z, ",mIsSettingsUpdated:");
            m69m2.append(dndTile3.mIsSettingsUpdated);
            Log.d("DndTile", m69m2.toString());
            StringBuilder sb5 = new StringBuilder();
            str = "";
            Context context22 = dndTile3.mContext;
            if (z4) {
            }
            dndTile3.mDndMenuSummary = str;
            dndTile3.mDndMenuSelectedItem = i;
            textView = this.mSummary;
            if (textView != null) {
            }
            ViewGroup viewGroup22 = (ViewGroup) inflate.findViewById(R.id.dnd_menu_layout);
            this.mMenuOptions = viewGroup22;
            int i122 = DNDDetailItems.$r8$clinit;
            DNDDetailItems dNDDetailItems4 = (DNDDetailItems) LayoutInflater.from(context).inflate(R.layout.sec_qs_detail_dnd_items, viewGroup22, false);
            this.mDNDActivationItems = dNDDetailItems4;
            this.mMenuOptions.addView(dNDDetailItems4);
            while (i4 < 6) {
            }
            updateDndActivationItems(false);
            DNDDetailItems dNDDetailItems22 = this.mDNDActivationItems;
            dNDDetailItems22.getClass();
            dNDDetailItems22.mTag = "DNDDetailItems.Do not disturb";
            DNDDetailItems dNDDetailItems32 = this.mDNDActivationItems;
            dNDDetailItems32.mHandler.removeMessages(2);
            dNDDetailItems32.mHandler.obtainMessage(2, this).sendToTarget();
            return inflate;
        }

        @Override // com.android.systemui.plugins.p013qs.DetailAdapter
        public final String getDetailAdapterSummary() {
            return DndTile.this.mDndMenuSummary;
        }

        @Override // com.android.systemui.plugins.p013qs.DetailAdapter
        public final int getMetricsCategory() {
            return 149;
        }

        @Override // com.android.systemui.plugins.p013qs.DetailAdapter
        public final Intent getSettingsIntent() {
            return DndTile.DND_SETTINGS;
        }

        @Override // com.android.systemui.plugins.p013qs.DetailAdapter
        public final CharSequence getTitle() {
            DndTile dndTile = DndTile.this;
            Intent intent = DndTile.DND_SETTINGS;
            return dndTile.mContext.getString(R.string.quick_settings_dnd_detail_title);
        }

        @Override // com.android.systemui.plugins.p013qs.DetailAdapter
        public final Boolean getToggleState() {
            return null;
        }

        public final void updateDetailItem(DNDDetailItems.Item item, boolean z) {
            item.getClass();
            DndTile dndTile = DndTile.this;
            Intent intent = DndTile.DND_SETTINGS;
            Resources resources = dndTile.mContext.getResources();
            Typeface create = Typeface.create(Typeface.create("sec", 1), VolteConstants.ErrorCode.BUSY_EVERYWHERE, false);
            Typeface create2 = Typeface.create(Typeface.create("sec", 0), 400, false);
            int color = resources.getColor(R.color.dnd_detail_selected_text_color);
            int color2 = resources.getColor(R.color.dnd_detail_unselected_text_color);
            int color3 = resources.getColor(R.color.dnd_detail_unselected_text_summary_color);
            CheckedTextView checkedTextView = item.ctv;
            TextView textView = item.stv;
            if (textView.getText().toString() == "") {
                textView.setVisibility(8);
            }
            if (checkedTextView != null) {
                boolean z2 = resources.getConfiguration().getLayoutDirection() == 1;
                checkedTextView.setChecked(z);
                if (z) {
                    color3 = color;
                }
                textView.setTextColor(color3);
                if (!z) {
                    color = color2;
                }
                checkedTextView.setTextColor(color);
                Drawable drawable = DndTile.this.mContext.getResources().getDrawable(R.drawable.dnd_detail_option_ic_check);
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                Drawable drawable2 = (z && z2) ? drawable : null;
                if (!z || z2) {
                    drawable = null;
                }
                checkedTextView.setCompoundDrawables(drawable2, null, drawable, null);
                if (!z) {
                    create = create2;
                }
                checkedTextView.setTypeface(create);
            }
            DNDDetailItems dNDDetailItems = this.mDNDActivationItems;
            int i = dNDDetailItems.mAdapter.getCount() <= 0 ? 0 : 1;
            dNDDetailItems.mHandler.removeMessages(3);
            dNDDetailItems.mHandler.obtainMessage(3, i, 0).sendToTarget();
        }

        public final void updateDndActivationItems(boolean z) {
            DNDDetailItems dNDDetailItems = this.mDNDActivationItems;
            int i = DndTile.this.mDndMenuSelectedItem;
            String str = this.mDndMenuOptions[i];
            dNDDetailItems.getClass();
            int i2 = DNDDetailItems.$r8$clinit;
            if (i == 0) {
                dNDDetailItems.updateQSPanelOptions(1);
            } else if (i == 1 || i == 2 || i == 3) {
                dNDDetailItems.updateQSPanelOptions(1);
            } else if (i == 4) {
                dNDDetailItems.updateQSPanelOptions(1);
            } else if (i == 5) {
                dNDDetailItems.updateQSPanelOptions(0);
            }
            dNDDetailItems.mSelectedMenu = str;
            DNDDetailItems dNDDetailItems2 = this.mDNDActivationItems;
            int i3 = dNDDetailItems2.mAdapter.getCount() > 0 ? 1 : 0;
            dNDDetailItems2.mHandler.removeMessages(3);
            dNDDetailItems2.mHandler.obtainMessage(3, i3, 0).sendToTarget();
            if (!z) {
                ((SQSTileImpl) DndTile.this).mHandler.post(new Runnable() { // from class: com.android.systemui.qs.tiles.DndTile.DndDetailAdapter.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        final DndDetailAdapter dndDetailAdapter = DndTile.this.mDetailAdapter;
                        if (dndDetailAdapter.mDNDActivationItems == null) {
                            return;
                        }
                        Log.d("DndTile", "setItems");
                        ArrayList arrayList = new ArrayList();
                        for (String str2 : dndDetailAdapter.mDndMenuOptions) {
                            DNDDetailItems.Item item = new DNDDetailItems.Item();
                            item.line1 = str2;
                            arrayList.add(item);
                        }
                        DNDDetailItems dNDDetailItems3 = dndDetailAdapter.mDNDActivationItems;
                        DNDDetailItems.Item[] itemArr = (DNDDetailItems.Item[]) arrayList.toArray(new DNDDetailItems.Item[arrayList.size()]);
                        dNDDetailItems3.mHandler.removeMessages(1);
                        dNDDetailItems3.mHandler.obtainMessage(1, itemArr).sendToTarget();
                        dndDetailAdapter.mDNDActivationItems.post(new Runnable() { // from class: com.android.systemui.qs.tiles.DndTile.DndDetailAdapter.2
                            @Override // java.lang.Runnable
                            public final void run() {
                                DndDetailAdapter dndDetailAdapter2 = DndDetailAdapter.this;
                                dndDetailAdapter2.mMenuOptions.setVisibility(dndDetailAdapter2.mDNDActivationItems.mAdapter.getCount() > 0 ? 0 : 8);
                            }
                        });
                        dndDetailAdapter.mItemsList.clear();
                        dndDetailAdapter.mItemsList.addAll(arrayList);
                    }
                });
                return;
            }
            DNDDetailItems dNDDetailItems3 = this.mDNDActivationItems;
            ArrayList arrayList = this.mItemsList;
            DNDDetailItems.Item[] itemArr = (DNDDetailItems.Item[]) arrayList.toArray(new DNDDetailItems.Item[arrayList.size()]);
            dNDDetailItems3.mHandler.removeMessages(1);
            dNDDetailItems3.mHandler.obtainMessage(1, itemArr).sendToTarget();
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public final void onViewAttachedToWindow(View view) {
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public final void onViewDetachedFromWindow(View view) {
        }

        @Override // com.android.systemui.plugins.p013qs.DetailAdapter
        public final void setToggleState(boolean z) {
        }
    }
}
