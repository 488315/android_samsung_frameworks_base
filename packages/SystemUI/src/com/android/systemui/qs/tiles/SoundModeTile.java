package com.android.systemui.qs.tiles;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.UserManager;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;
import com.android.internal.logging.MetricsLogger;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.animation.Expandable;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.qs.tileimpl.SQSTileImpl;
import com.android.systemui.qs.tiles.SoundModeTile;
import com.android.systemui.statusbar.phone.SecStatusBarAudioManagerHelper;
import com.android.systemui.statusbar.policy.ZenModeController;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class SoundModeTile extends SQSTileImpl {
    public static final Intent SOUNDMODE_SETTINGS;
    public static final String[] SOUND_MODE_LOGGING_VALUE = null;
    public static final int[] SOUND_MODE_MUTE_ALL_SOUNDS_TEXT;
    public static final int[] SOUND_MODE_TEXT;
    public final QSTileImpl.AnimationIcon[] SOUND_MODE_ICON;
    public final QSTile.Icon[] SOUND_MODE_MUTE_ALL_ICON;
    public final DisplayLifecycle mDisplayLifecycle;
    public boolean mListening;
    public MetricsLogger mMetricsLogger;
    public final QSTile.Icon mMuteAllSound;
    public final AnonymousClass1 mReceiver;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.qs.tiles.SoundModeTile$1, reason: invalid class name */
    public class AnonymousClass1 extends BroadcastReceiver {
        public AnonymousClass1() {
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            SoundModeTile soundModeTile = SoundModeTile.this;
            Intent intent2 = SoundModeTile.SOUNDMODE_SETTINGS;
            Log.d(soundModeTile.TAG, "onReceive: " + intent.getAction());
            if ("android.media.INTERNAL_RINGER_MODE_CHANGED_ACTION".equals(intent.getAction())) {
                final int i = 0;
                SoundModeTile.this.mUiHandler.post(new Runnable(this) { // from class: com.android.systemui.qs.tiles.SoundModeTile$1$$ExternalSyntheticLambda0
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
            } else if ("android.settings.ALL_SOUND_MUTE".equals(intent.getAction())) {
                final int i2 = 1;
                SoundModeTile.this.mUiHandler.post(new Runnable(this) { // from class: com.android.systemui.qs.tiles.SoundModeTile$1$$ExternalSyntheticLambda0
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
        QSTile.Icon icon = QSTileImpl.ResourceIcon.get(R.drawable.quick_panel_icon_mute_all_sounds);
        this.mMuteAllSound = icon;
        QSTile.Icon icon2 = QSTileImpl.ResourceIcon.get(R.drawable.quick_panel_icon_mute_all_sounds_vibrate);
        this.SOUND_MODE_ICON = new QSTileImpl.AnimationIcon[]{animationIcon3, animationIcon2, animationIcon};
        this.SOUND_MODE_MUTE_ALL_ICON = new QSTile.Icon[]{icon, icon2};
        this.mReceiver = new AnonymousClass1();
        this.mMetricsLogger = (MetricsLogger) Dependency.sDependency.getDependencyInner(MetricsLogger.class);
        if (QpRune.QUICK_SUBSCREEN_PANEL) {
            this.mDisplayLifecycle = displayLifecycle;
        }
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

    /* JADX WARN: Code restructure failed: missing block: B:32:0x009b, code lost:
    
        if (r6.mSettingsChangesAllowed == false) goto L37;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:14:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x001f  */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void handleClick(com.android.systemui.animation.Expandable r6) {
        /*
            r5 = this;
            com.android.systemui.Dependency r6 = com.android.systemui.Dependency.sDependency
            java.lang.Class<com.android.systemui.knox.KnoxStateMonitor> r0 = com.android.systemui.knox.KnoxStateMonitor.class
            java.lang.Object r6 = r6.getDependencyInner(r0)
            com.android.systemui.knox.KnoxStateMonitor r6 = (com.android.systemui.knox.KnoxStateMonitor) r6
            com.android.systemui.knox.KnoxStateMonitorImpl r6 = (com.android.systemui.knox.KnoxStateMonitorImpl) r6
            com.android.systemui.knox.EdmMonitor r6 = r6.mEdmMonitor
            r1 = 0
            r2 = 1
            if (r6 == 0) goto L1c
            com.android.systemui.knox.KnoxStateMonitorImpl r3 = r6.knoxStateMonitor
            android.content.Context r3 = r3.mContext
            boolean r6 = r6.mSettingsChangesAllowed
            if (r6 != 0) goto L1c
            r6 = r2
            goto L1d
        L1c:
            r6 = r1
        L1d:
            if (r6 == 0) goto L2f
            boolean r6 = com.android.systemui.QpRune.QUICK_SUBSCREEN_PANEL
            if (r6 == 0) goto L2b
            android.content.Context r6 = r5.getSubScreenContext()
            r5.showItPolicyToastOnSubScreen(r6)
            return
        L2b:
            r5.showItPolicyToast()
            return
        L2f:
            boolean r6 = r5.isVolumeRestricted$1()
            if (r6 == 0) goto L46
            android.content.Context r5 = r5.mContext
            r6 = 17040641(0x1040501, float:2.424816E-38)
            java.lang.String r6 = r5.getString(r6)
            android.widget.Toast r5 = android.widget.Toast.makeText(r5, r6, r2)
            r5.show()
            return
        L46:
            com.android.internal.logging.MetricsLogger r6 = r5.mMetricsLogger
            if (r6 == 0) goto L56
            com.android.systemui.plugins.qs.QSTile$State r3 = r5.mState
            com.android.systemui.plugins.qs.QSTile$BooleanState r3 = (com.android.systemui.plugins.qs.QSTile.BooleanState) r3
            boolean r3 = r3.value
            r3 = r3 ^ r2
            r4 = 5002(0x138a, float:7.009E-42)
            r6.action(r4, r3)
        L56:
            android.content.Context r6 = r5.mContext
            com.android.systemui.statusbar.phone.SecStatusBarAudioManagerHelper r6 = com.android.systemui.statusbar.phone.SecStatusBarAudioManagerHelper.getInstance(r6)
            int r6 = r6.getRingerMode(r1)
            if (r6 != r2) goto L63
            goto L79
        L63:
            r1 = 2
            if (r6 != r1) goto L6d
            android.content.Context r6 = r5.mContext
            boolean r1 = com.android.systemui.util.DeviceType.isVibratorSupported(r6)
            goto L79
        L6d:
            boolean r6 = r5.isSystemSettingAllSoundOff()
            if (r6 == 0) goto L79
            android.content.Context r6 = r5.mContext
            boolean r1 = com.android.systemui.util.DeviceType.isVibratorSupported(r6)
        L79:
            java.lang.String r6 = "setSoundProfile(soundProfile:"
            java.lang.String r2 = ", detailSet:false)"
            java.lang.String r6 = androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(r1, r6, r2)
            java.lang.String r2 = r5.TAG
            android.util.Log.d(r2, r6)
            com.android.systemui.Dependency r6 = com.android.systemui.Dependency.sDependency
            java.lang.Object r6 = r6.getDependencyInner(r0)
            com.android.systemui.knox.KnoxStateMonitor r6 = (com.android.systemui.knox.KnoxStateMonitor) r6
            com.android.systemui.knox.KnoxStateMonitorImpl r6 = (com.android.systemui.knox.KnoxStateMonitorImpl) r6
            com.android.systemui.knox.EdmMonitor r6 = r6.mEdmMonitor
            if (r6 == 0) goto L9e
            com.android.systemui.knox.KnoxStateMonitorImpl r0 = r6.knoxStateMonitor
            android.content.Context r0 = r0.mContext
            boolean r6 = r6.mSettingsChangesAllowed
            if (r6 != 0) goto L9e
            goto La7
        L9e:
            android.content.Context r6 = r5.mContext
            com.android.systemui.statusbar.phone.SecStatusBarAudioManagerHelper r6 = com.android.systemui.statusbar.phone.SecStatusBarAudioManagerHelper.getInstance(r6)
            r6.setRingerModeInternal(r1)
        La7:
            boolean r6 = com.android.systemui.QpRune.QUICK_SUBSCREEN_PANEL
            if (r6 != 0) goto Laf
            boolean r6 = com.android.systemui.QpRune.QUICK_SUBSCREEN_FULLSCREEN_PANEL
            if (r6 == 0) goto Lc0
        Laf:
            com.android.systemui.keyguard.DisplayLifecycle r5 = r5.mDisplayLifecycle
            if (r5 == 0) goto Lc0
            boolean r5 = r5.mIsFolderOpened
            if (r5 != 0) goto Lc0
            java.lang.String r5 = com.android.systemui.util.SystemUIAnalytics.getCurrentScreenID()
            java.lang.String r6 = "QPBE2016"
            com.android.systemui.util.SystemUIAnalytics.sendEventLog(r5, r6)
        Lc0:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.tiles.SoundModeTile.handleClick(com.android.systemui.animation.Expandable):void");
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleDestroy() {
        super.handleDestroy();
        this.mMetricsLogger = null;
    }

    @Override // com.android.systemui.qs.tileimpl.SQSTileImpl, com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleSecondaryClick(Expandable expandable) {
        if (!isVolumeRestricted$1()) {
            super.handleSecondaryClick(expandable);
        } else {
            Context context = this.mContext;
            Toast.makeText(context, context.getString(android.R.string.keyguard_accessibility_slide_unlock), 1).show();
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleSetListening(boolean z) {
        super.handleSetListening(z);
        if (this.mListening == z) {
            return;
        }
        this.mListening = z;
        Log.d(this.TAG, KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("handleSetListening: listening ", z));
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
        Log.d(this.TAG, "handleUpdateState: profile " + ringerMode + ", muteAllSound " + z);
        booleanState.value = (ringerMode == 0 || z) ? false : true;
        booleanState.label = this.mContext.getString(z ? SOUND_MODE_MUTE_ALL_SOUNDS_TEXT[ringerMode] : SOUND_MODE_TEXT[ringerMode]);
        QSTile.Icon[] iconArr = this.SOUND_MODE_ICON;
        booleanState.icon = z ? this.mMuteAllSound : isSystemSettingAllSoundOff() ? this.SOUND_MODE_MUTE_ALL_ICON[ringerMode] : iconArr[ringerMode];
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

    public final boolean isVolumeRestricted$1() {
        boolean hasUserRestriction = ((UserManager) this.mContext.getSystemService("user")).hasUserRestriction("no_adjust_volume");
        Log.i(this.TAG, KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("getDisallowAdjustVolume enabled = ", hasUserRestriction));
        return hasUserRestriction;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final QSTile.State newTileState() {
        return new QSTile.BooleanState();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.SQSTile
    public final void sendTileStatusLog() {
        int ringerMode = SecStatusBarAudioManagerHelper.getInstance(this.mContext).getRingerMode(false);
        this.mHost.sendTileStatusLog(ringerMode == 0 ? "mute" : ringerMode == 1 ? "vibrate" : ringerMode == 2 ? "sound" : null, getTileMapKey());
    }
}
