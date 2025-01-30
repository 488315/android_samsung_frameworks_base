package com.android.systemui.qs.tiles;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.core.os.LocaleListCompatWrapper$$ExternalSyntheticOutline0;
import com.android.internal.logging.MetricsLogger;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.QSTileHost;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.qs.tileimpl.SQSTileImpl;
import com.android.systemui.qs.tiles.SoundModeTile;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.phone.SecStatusBarAudioManagerHelper;
import com.android.systemui.statusbar.policy.ZenModeController;
import com.android.systemui.statusbar.policy.ZenModeControllerImpl;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.sec.ims.configuration.DATA;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SoundModeTile extends SQSTileImpl {
    public static final Intent SOUNDMODE_SETTINGS;
    public static final String[] SOUND_MODE_LOGGING_VALUE;
    public static final int[] SOUND_MODE_MUTE_ALL_SOUNDS_TEXT;
    public static final int[] SOUND_MODE_TEXT;
    public final QSTileImpl.AnimationIcon[] SOUND_MODE_ICON;
    public final DisplayLifecycle mDisplayLifecycle;
    public boolean mListening;
    public MetricsLogger mMetricsLogger;
    public final QSTile.Icon mMuteAllSound;
    public final C22951 mReceiver;
    public final SharedPreferences.Editor mSoundModeTilePrefEditor;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.qs.tiles.SoundModeTile$1 */
    public final class C22951 extends BroadcastReceiver {
        public C22951() {
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if ("android.media.INTERNAL_RINGER_MODE_CHANGED_ACTION".equals(intent.getAction())) {
                SoundModeTile soundModeTile = SoundModeTile.this;
                Intent intent2 = SoundModeTile.SOUNDMODE_SETTINGS;
                final int i = 0;
                soundModeTile.mUiHandler.post(new Runnable(this) { // from class: com.android.systemui.qs.tiles.SoundModeTile$1$$ExternalSyntheticLambda0
                    public final /* synthetic */ SoundModeTile.C22951 f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        switch (i) {
                            case 0:
                                SoundModeTile.this.refreshState(null);
                                break;
                            default:
                                SoundModeTile.this.refreshState(null);
                                break;
                        }
                    }
                });
                return;
            }
            if ("android.settings.ALL_SOUND_MUTE".equals(intent.getAction())) {
                SoundModeTile soundModeTile2 = SoundModeTile.this;
                Intent intent3 = SoundModeTile.SOUNDMODE_SETTINGS;
                final int i2 = 1;
                soundModeTile2.mUiHandler.post(new Runnable(this) { // from class: com.android.systemui.qs.tiles.SoundModeTile$1$$ExternalSyntheticLambda0
                    public final /* synthetic */ SoundModeTile.C22951 f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        switch (i2) {
                            case 0:
                                SoundModeTile.this.refreshState(null);
                                break;
                            default:
                                SoundModeTile.this.refreshState(null);
                                break;
                        }
                    }
                });
            }
        }
    }

    static {
        DeviceType.isEngOrUTBinary();
        SOUNDMODE_SETTINGS = new Intent("android.settings.SOUND_SETTINGS");
        SOUND_MODE_TEXT = new int[]{R.string.quick_settings_sound_mode_mute_label, R.string.quick_settings_sound_mode_vibrate_label, R.string.quick_settings_sound_mode_sound_label};
        SOUND_MODE_MUTE_ALL_SOUNDS_TEXT = new int[]{R.string.quick_settings_sound_mode_mute_label, R.string.quick_settings_sound_mode_vibrate_label, R.string.quick_settings_sound_mode_mute_all_sound_label};
        SOUND_MODE_LOGGING_VALUE = new String[]{"2", DATA.DM_FIELD_INDEX.PCSCF_DOMAIN, "1"};
    }

    public SoundModeTile(QSHost qSHost, QsEventLogger qsEventLogger, Looper looper, Handler handler, SettingsHelper settingsHelper, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, DisplayLifecycle displayLifecycle) {
        super(qSHost, qsEventLogger, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        QSTileImpl.AnimationIcon animationIcon = new QSTileImpl.AnimationIcon(R.drawable.quick_panel_icon_sound, R.drawable.quick_panel_icon_sound_011);
        QSTileImpl.AnimationIcon animationIcon2 = new QSTileImpl.AnimationIcon(R.drawable.quick_panel_icon_sound_vibrate, R.drawable.quick_panel_icon_sound_vibrate_015);
        QSTileImpl.AnimationIcon animationIcon3 = new QSTileImpl.AnimationIcon(R.drawable.quick_panel_icon_sound_mute, R.drawable.quick_panel_icon_sound_mute_008);
        this.mMuteAllSound = QSTileImpl.ResourceIcon.get(R.drawable.quick_panel_icon_mute_all_sound);
        this.SOUND_MODE_ICON = new QSTileImpl.AnimationIcon[]{animationIcon3, animationIcon2, animationIcon};
        this.mReceiver = new C22951();
        this.mMetricsLogger = (MetricsLogger) Dependency.get(MetricsLogger.class);
        SharedPreferences sharedPreferences = this.mContext.getSharedPreferences("quick_pref", 0);
        if (sharedPreferences != null) {
            this.mSoundModeTilePrefEditor = sharedPreferences.edit();
        }
        if (QpRune.QUICK_PANEL_SUBSCREEN) {
            this.mDisplayLifecycle = displayLifecycle;
        }
    }

    @Override // com.android.systemui.qs.tileimpl.SQSTileImpl
    public final String composeChangeAnnouncement(QSTile.State state) {
        StringBuilder sb = new StringBuilder();
        sb.append(((QSTile.BooleanState) this.mState).label);
        sb.append(", ");
        return KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m73m(this.mContext, R.string.switch_bar_on, sb);
    }

    public final SecStatusBarAudioManagerHelper getAudioHelper() {
        if (SecStatusBarAudioManagerHelper.sInstance == null) {
            SecStatusBarAudioManagerHelper.sInstance = new SecStatusBarAudioManagerHelper(this.mContext);
        }
        return SecStatusBarAudioManagerHelper.sInstance;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final Intent getLongClickIntent() {
        return SOUNDMODE_SETTINGS;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final int getMetricsCategory() {
        return 5002;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.indexsearch.Searchable
    public final String getSearchTitle() {
        int ringerMode = getAudioHelper().getRingerMode(false);
        return this.mContext.getString(isSystemSettingAllSoundOff() ? SOUND_MODE_MUTE_ALL_SOUNDS_TEXT[ringerMode] : SOUND_MODE_TEXT[ringerMode]).replaceAll(System.getProperty("line.separator"), " ");
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.indexsearch.Searchable
    public final ArrayList getSearchWords() {
        ArrayList arrayList = new ArrayList();
        for (int i : (isSystemSettingAllSoundOff() && getAudioHelper().getRingerMode(false) == 2) ? SOUND_MODE_MUTE_ALL_SOUNDS_TEXT : SOUND_MODE_TEXT) {
            arrayList.add(this.mContext.getString(i).trim().toLowerCase());
        }
        return arrayList;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final CharSequence getTileLabel() {
        int i;
        if (isSystemSettingAllSoundOff()) {
            i = SOUND_MODE_MUTE_ALL_SOUNDS_TEXT[getAudioHelper().getRingerMode(false)];
        } else {
            i = SOUND_MODE_TEXT[getAudioHelper().getRingerMode(false)];
        }
        return this.mContext.getString(i);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final int getTileMapValue() {
        return getAudioHelper().getRingerMode(false);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleClick(View view) {
        DisplayLifecycle displayLifecycle;
        if (((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.get(KnoxStateMonitor.class))).isSoundModeTileBlocked()) {
            if (QpRune.QUICK_PANEL_SUBSCREEN) {
                showItPolicyToastOnSubScreen(getSubScreenContext());
                return;
            } else {
                showItPolicyToast();
                return;
            }
        }
        ZenModeControllerImpl zenModeControllerImpl = (ZenModeControllerImpl) ((ZenModeController) Dependency.get(ZenModeController.class));
        boolean hasUserRestriction = zenModeControllerImpl.mUserManager.hasUserRestriction("no_adjust_volume", UserHandle.of(zenModeControllerImpl.mUserId));
        Context context = this.mContext;
        if (hasUserRestriction) {
            Toast.makeText(context, context.getString(android.R.string.indeterminate_progress_12), 1).show();
            return;
        }
        MetricsLogger metricsLogger = this.mMetricsLogger;
        if (metricsLogger != null) {
            metricsLogger.action(5002, !((QSTile.BooleanState) this.mState).value);
        }
        int i = 0;
        int ringerMode = getAudioHelper().getRingerMode(false);
        if (ringerMode != 1) {
            i = 2;
            if (ringerMode == 2) {
                i = DeviceType.isVibratorSupported(context);
            } else if (isSystemSettingAllSoundOff()) {
                i = DeviceType.isVibratorSupported(context);
            }
        }
        Log.d(this.TAG, LocaleListCompatWrapper$$ExternalSyntheticOutline0.m31m("setSoundProfile(soundProfile:", i, ", detailSet:false)"));
        if (((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.get(KnoxStateMonitor.class))).isSoundModeTileBlocked()) {
            showItPolicyToast();
        } else {
            getAudioHelper().setRingerModeInternal(i);
        }
        if (!QpRune.QUICK_PANEL_SUBSCREEN || (displayLifecycle = this.mDisplayLifecycle) == null || displayLifecycle.mIsFolderOpened) {
            return;
        }
        SystemUIAnalytics.sendEventLog(SystemUIAnalytics.sCurrentScreenID, "QPBE2016");
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleDestroy() {
        super.handleDestroy();
        this.mMetricsLogger = null;
    }

    @Override // com.android.systemui.qs.tileimpl.SQSTileImpl, com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleSecondaryClick(View view) {
        ZenModeControllerImpl zenModeControllerImpl = (ZenModeControllerImpl) ((ZenModeController) Dependency.get(ZenModeController.class));
        if (!zenModeControllerImpl.mUserManager.hasUserRestriction("no_adjust_volume", UserHandle.of(zenModeControllerImpl.mUserId))) {
            super.handleSecondaryClick(view);
        } else {
            Context context = this.mContext;
            Toast.makeText(context, context.getString(android.R.string.indeterminate_progress_12), 1).show();
        }
    }

    @Override // com.android.systemui.qs.tileimpl.SQSTileImpl, com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleSetListening(boolean z) {
        super.handleSetListening(z);
        if (this.mListening == z) {
            return;
        }
        this.mListening = z;
        C22951 c22951 = this.mReceiver;
        if (!z) {
            ((BroadcastDispatcher) Dependency.get(BroadcastDispatcher.class)).unregisterReceiver(c22951);
            return;
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.media.INTERNAL_RINGER_MODE_CHANGED_ACTION");
        intentFilter.addAction("android.settings.ALL_SOUND_MUTE");
        ((BroadcastDispatcher) Dependency.get(BroadcastDispatcher.class)).registerReceiver(intentFilter, c22951);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleUpdateState(QSTile.State state, Object obj) {
        QSTile.BooleanState booleanState = (QSTile.BooleanState) state;
        int ringerMode = getAudioHelper().getRingerMode(true);
        boolean z = false;
        boolean z2 = ringerMode == 2 && isSystemSettingAllSoundOff();
        if (ringerMode != 0 && !z2) {
            z = true;
        }
        booleanState.value = z;
        int i = z2 ? SOUND_MODE_MUTE_ALL_SOUNDS_TEXT[ringerMode] : SOUND_MODE_TEXT[ringerMode];
        Context context = this.mContext;
        booleanState.label = context.getString(i);
        booleanState.icon = z2 ? this.mMuteAllSound : this.SOUND_MODE_ICON[ringerMode];
        booleanState.dualTarget = true;
        String str = ((Object) booleanState.label) + " " + context.getString(R.string.switch_bar_on);
        booleanState.contentDescription = str;
        booleanState.state = booleanState.value ? 2 : 1;
        booleanState.stateDescription = str;
    }

    public final boolean isSystemSettingAllSoundOff() {
        return Settings.System.getInt(this.mContext.getContentResolver(), "all_sound_off", 0) == 1;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final QSTile.State newTileState() {
        return new QSTile.BooleanState();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.SQSTile
    public final void sendTileStatusLog() {
        int ringerMode = getAudioHelper().getRingerMode(false);
        String str = ringerMode == 0 ? "mute" : ringerMode == 1 ? "vibrate" : ringerMode == 2 ? "sound" : null;
        String tileMapKey = getTileMapKey();
        int i = QSTileHost.TilesMap.SID_TILE_STATE;
        this.mTilesMap.getClass();
        String id = QSTileHost.TilesMap.getId(i, tileMapKey);
        if (id == null || str == null) {
            return;
        }
        SharedPreferences.Editor editor = this.mSoundModeTilePrefEditor;
        editor.putString(id, str);
        editor.commit();
    }
}
