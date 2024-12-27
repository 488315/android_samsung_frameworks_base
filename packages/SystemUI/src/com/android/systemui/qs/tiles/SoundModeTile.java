package com.android.systemui.qs.tiles;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;
import androidx.compose.foundation.lazy.LazyListMeasuredItem$$ExternalSyntheticOutline0;
import com.android.internal.logging.MetricsLogger;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.animation.Expandable;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.QSTileHost;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.qs.tileimpl.SQSTileImpl;
import com.android.systemui.qs.tiles.SoundModeTile;
import com.android.systemui.statusbar.phone.SecStatusBarAudioManagerHelper;
import com.android.systemui.statusbar.policy.ZenModeController;
import com.android.systemui.statusbar.policy.ZenModeControllerImpl;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import java.util.ArrayList;

public final class SoundModeTile extends SQSTileImpl {
    public static final Intent SOUNDMODE_SETTINGS;
    public static final String[] SOUND_MODE_LOGGING_VALUE = null;
    public static final int[] SOUND_MODE_MUTE_ALL_SOUNDS_TEXT;
    public static final int[] SOUND_MODE_TEXT;
    public final QSTileImpl.AnimationIcon[] SOUND_MODE_ICON;
    public boolean mListening;
    public MetricsLogger mMetricsLogger;
    public final QSTile.Icon mMuteAllSound;
    public final AnonymousClass1 mReceiver;
    public final SharedPreferences.Editor mSoundModeTilePrefEditor;
    public final ZenModeController mZenController;

    /* renamed from: com.android.systemui.qs.tiles.SoundModeTile$1, reason: invalid class name */
    public final class AnonymousClass1 extends BroadcastReceiver {
        public AnonymousClass1() {
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if ("android.media.INTERNAL_RINGER_MODE_CHANGED_ACTION".equals(intent.getAction())) {
                SoundModeTile soundModeTile = SoundModeTile.this;
                Intent intent2 = SoundModeTile.SOUNDMODE_SETTINGS;
                final int i = 0;
                soundModeTile.mUiHandler.post(new Runnable(this) { // from class: com.android.systemui.qs.tiles.SoundModeTile$1$$ExternalSyntheticLambda0
                    public final /* synthetic */ SoundModeTile.AnonymousClass1 f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        int i2 = i;
                        SoundModeTile.AnonymousClass1 anonymousClass1 = this.f$0;
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
                return;
            }
            if ("android.settings.ALL_SOUND_MUTE".equals(intent.getAction())) {
                SoundModeTile soundModeTile2 = SoundModeTile.this;
                Intent intent3 = SoundModeTile.SOUNDMODE_SETTINGS;
                final int i2 = 1;
                soundModeTile2.mUiHandler.post(new Runnable(this) { // from class: com.android.systemui.qs.tiles.SoundModeTile$1$$ExternalSyntheticLambda0
                    public final /* synthetic */ SoundModeTile.AnonymousClass1 f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        int i22 = i2;
                        SoundModeTile.AnonymousClass1 anonymousClass1 = this.f$0;
                        switch (i22) {
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
    }

    public SoundModeTile(QSHost qSHost, QsEventLogger qsEventLogger, Looper looper, Handler handler, SettingsHelper settingsHelper, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, ZenModeController zenModeController, DisplayLifecycle displayLifecycle) {
        super(qSHost, qsEventLogger, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        QSTileImpl.AnimationIcon animationIcon = new QSTileImpl.AnimationIcon(R.drawable.quick_panel_icon_sound, R.drawable.quick_panel_icon_sound_011);
        QSTileImpl.AnimationIcon animationIcon2 = new QSTileImpl.AnimationIcon(R.drawable.quick_panel_icon_sound_vibrate, R.drawable.quick_panel_icon_sound_vibrate_015);
        QSTileImpl.AnimationIcon animationIcon3 = new QSTileImpl.AnimationIcon(R.drawable.quick_panel_icon_sound_mute, R.drawable.quick_panel_icon_sound_mute_008);
        this.mMuteAllSound = QSTileImpl.ResourceIcon.get(R.drawable.quick_panel_icon_mute_all_sound);
        this.SOUND_MODE_ICON = new QSTileImpl.AnimationIcon[]{animationIcon3, animationIcon2, animationIcon};
        this.mReceiver = new AnonymousClass1();
        this.mMetricsLogger = (MetricsLogger) Dependency.sDependency.getDependencyInner(MetricsLogger.class);
        this.mZenController = zenModeController;
        SharedPreferences sharedPreferences = this.mContext.getSharedPreferences(SystemUIAnalytics.QUICK_PREF_NAME, 0);
        if (sharedPreferences != null) {
            this.mSoundModeTilePrefEditor = sharedPreferences.edit();
        }
        boolean z = QpRune.QUICK_PANEL_BLUR_DEFAULT;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final Intent getLongClickIntent() {
        return SOUNDMODE_SETTINGS;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final int getMetricsCategory() {
        return 5002;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final String getSearchTitle() {
        int ringerMode = SecStatusBarAudioManagerHelper.getInstance(this.mContext).getRingerMode(false);
        return this.mContext.getString(isSystemSettingAllSoundOff() ? SOUND_MODE_MUTE_ALL_SOUNDS_TEXT[ringerMode] : SOUND_MODE_TEXT[ringerMode]).replaceAll(System.getProperty("line.separator"), " ");
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final ArrayList getSearchWords() {
        ArrayList arrayList = new ArrayList();
        for (int i : (isSystemSettingAllSoundOff() && SecStatusBarAudioManagerHelper.getInstance(this.mContext).getRingerMode(false) == 2) ? SOUND_MODE_MUTE_ALL_SOUNDS_TEXT : SOUND_MODE_TEXT) {
            arrayList.add(this.mContext.getString(i).trim().toLowerCase());
        }
        return arrayList;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final CharSequence getTileLabel() {
        int i;
        Context context = this.mContext;
        if (isSystemSettingAllSoundOff()) {
            i = SOUND_MODE_MUTE_ALL_SOUNDS_TEXT[SecStatusBarAudioManagerHelper.getInstance(this.mContext).getRingerMode(false)];
        } else {
            i = SOUND_MODE_TEXT[SecStatusBarAudioManagerHelper.getInstance(this.mContext).getRingerMode(false)];
        }
        return context.getString(i);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final int getTileMapValue() {
        return SecStatusBarAudioManagerHelper.getInstance(this.mContext).getRingerMode(false);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleClick(Expandable expandable) {
        if (((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).isSoundModeTileBlocked()) {
            if (QpRune.QUICK_SUBSCREEN_PANEL) {
                showItPolicyToastOnSubScreen(getSubScreenContext());
                return;
            } else {
                showItPolicyToast();
                return;
            }
        }
        ZenModeControllerImpl zenModeControllerImpl = (ZenModeControllerImpl) this.mZenController;
        if (zenModeControllerImpl.mUserManager.hasUserRestriction("no_adjust_volume", UserHandle.of(zenModeControllerImpl.mUserId))) {
            Context context = this.mContext;
            Toast.makeText(context, context.getString(android.R.string.indeterminate_progress_60), 1).show();
            return;
        }
        MetricsLogger metricsLogger = this.mMetricsLogger;
        if (metricsLogger != null) {
            metricsLogger.action(5002, !((QSTile.BooleanState) this.mState).value);
        }
        int i = 0;
        int ringerMode = SecStatusBarAudioManagerHelper.getInstance(this.mContext).getRingerMode(false);
        if (ringerMode != 1) {
            i = 2;
            if (ringerMode == 2) {
                i = DeviceType.isVibratorSupported(this.mContext);
            } else if (isSystemSettingAllSoundOff()) {
                i = DeviceType.isVibratorSupported(this.mContext);
            }
        }
        Log.d(this.TAG, LazyListMeasuredItem$$ExternalSyntheticOutline0.m(i, "setSoundProfile(soundProfile:", ", detailSet:false)"));
        if (((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).isSoundModeTileBlocked()) {
            return;
        }
        SecStatusBarAudioManagerHelper.getInstance(this.mContext).setRingerModeInternal(i);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleDestroy() {
        super.handleDestroy();
        this.mMetricsLogger = null;
    }

    @Override // com.android.systemui.qs.tileimpl.SQSTileImpl, com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleSecondaryClick(Expandable expandable) {
        ZenModeControllerImpl zenModeControllerImpl = (ZenModeControllerImpl) this.mZenController;
        if (!zenModeControllerImpl.mUserManager.hasUserRestriction("no_adjust_volume", UserHandle.of(zenModeControllerImpl.mUserId))) {
            super.handleSecondaryClick(expandable);
        } else {
            Context context = this.mContext;
            Toast.makeText(context, context.getString(android.R.string.indeterminate_progress_60), 1).show();
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleSetListening(boolean z) {
        super.handleSetListening(z);
        if (this.mListening == z) {
            return;
        }
        this.mListening = z;
        AnonymousClass1 anonymousClass1 = this.mReceiver;
        if (!z) {
            ((BroadcastDispatcher) Dependency.sDependency.getDependencyInner(BroadcastDispatcher.class)).unregisterReceiver(anonymousClass1);
        } else {
            ((BroadcastDispatcher) Dependency.sDependency.getDependencyInner(BroadcastDispatcher.class)).registerReceiver(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m("android.media.INTERNAL_RINGER_MODE_CHANGED_ACTION", "android.settings.ALL_SOUND_MUTE"), anonymousClass1);
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleUpdateState(QSTile.State state, Object obj) {
        QSTile.BooleanState booleanState = (QSTile.BooleanState) state;
        int ringerMode = SecStatusBarAudioManagerHelper.getInstance(this.mContext).getRingerMode(true);
        boolean z = ringerMode == 2 && isSystemSettingAllSoundOff();
        booleanState.value = (ringerMode == 0 || z) ? false : true;
        booleanState.label = this.mContext.getString(z ? SOUND_MODE_MUTE_ALL_SOUNDS_TEXT[ringerMode] : SOUND_MODE_TEXT[ringerMode]);
        QSTile.Icon[] iconArr = this.SOUND_MODE_ICON;
        booleanState.icon = z ? this.mMuteAllSound : iconArr[ringerMode];
        if (ringerMode == 0) {
            ringerMode = 2;
        } else if (ringerMode == 1) {
            ringerMode = 0;
        } else if (ringerMode == 2) {
            ringerMode = 1;
        }
        booleanState.nextIcon = z ? null : iconArr[ringerMode];
        booleanState.dualTarget = true;
        StringBuilder sb = new StringBuilder();
        sb.append((Object) booleanState.label);
        sb.append(" ");
        String m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(this.mContext, R.string.switch_bar_on, sb);
        booleanState.contentDescription = m;
        booleanState.state = booleanState.value ? 2 : 1;
        booleanState.stateDescription = m;
    }

    public final boolean isSystemSettingAllSoundOff() {
        return Settings.Global.getInt(this.mContext.getContentResolver(), "all_sound_off", 0) == 1;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final QSTile.State newTileState() {
        return new QSTile.BooleanState();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.SQSTile
    public final void sendTileStatusLog() {
        int ringerMode = SecStatusBarAudioManagerHelper.getInstance(this.mContext).getRingerMode(false);
        String str = ringerMode == 0 ? "mute" : ringerMode == 1 ? "vibrate" : ringerMode == 2 ? "sound" : null;
        String tileMapKey = getTileMapKey();
        int i = QSTileHost.TilesMap.SID_TILE_STATE;
        this.mTilesMap.getClass();
        String id = QSTileHost.TilesMap.getId(i, tileMapKey);
        if (id == null || str == null) {
            return;
        }
        this.mSoundModeTilePrefEditor.putString(id, str);
        this.mSoundModeTilePrefEditor.commit();
    }
}
