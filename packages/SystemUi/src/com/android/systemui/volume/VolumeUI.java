package com.android.systemui.volume;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.hardware.display.SemDisplayVolumeListener;
import android.media.AudioManager;
import android.media.VolumePolicy;
import android.media.session.MediaSessionManager;
import android.os.Handler;
import android.util.Log;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.settingslib.volume.C0934D;
import com.android.settingslib.volume.MediaSessions;
import com.android.systemui.AbstractC0950x8906c950;
import com.android.systemui.CoreStartable;
import com.android.systemui.Prefs;
import com.android.systemui.R;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.qs.tiles.DndTile;
import com.android.systemui.volume.util.BroadcastReceiverManager;
import com.android.systemui.volume.util.BroadcastReceiverType;
import com.android.systemui.volume.util.DisplayManagerWrapper;
import com.android.systemui.volume.util.SystemServiceExtension;
import com.sec.ims.IMSParameter;
import java.io.PrintWriter;
import java.util.Map;
import java.util.function.Consumer;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class VolumeUI implements CoreStartable {
    public static final boolean LOGD = Log.isLoggable("VolumeUI", 3);
    public final Context mContext;
    public boolean mEnabled;
    public final VolumeDialogComponent mVolumeComponent;

    public VolumeUI(Context context, VolumeDialogComponent volumeDialogComponent) {
        new Handler();
        this.mContext = context;
        this.mVolumeComponent = volumeDialogComponent;
    }

    @Override // com.android.systemui.CoreStartable, com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.print("mEnabled=");
        printWriter.println(this.mEnabled);
        if (this.mEnabled) {
            this.mVolumeComponent.getClass();
        }
    }

    @Override // com.android.systemui.CoreStartable
    public final void onConfigurationChanged(Configuration configuration) {
        if (this.mEnabled) {
            VolumeDialogComponent volumeDialogComponent = this.mVolumeComponent;
            if (volumeDialogComponent.mConfigChanges.applyNewConfig(volumeDialogComponent.mContext.getResources())) {
                volumeDialogComponent.mController.mCallbacks.onConfigurationChanged();
            }
        }
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        Context context = this.mContext;
        boolean z = context.getResources().getBoolean(R.bool.enable_volume_ui);
        boolean z2 = context.getResources().getBoolean(R.bool.enable_safety_warning);
        final int i = 0;
        final int i2 = 1;
        boolean z3 = z || z2;
        this.mEnabled = z3;
        if (z3) {
            VolumeDialogComponent volumeDialogComponent = this.mVolumeComponent;
            VolumeDialogControllerImpl volumeDialogControllerImpl = volumeDialogComponent.mController;
            volumeDialogControllerImpl.mShowVolumeDialog = z;
            volumeDialogControllerImpl.mShowSafetyWarning = z2;
            Intent intent = DndTile.DND_SETTINGS;
            Prefs.putBoolean(context, "DndTileVisible", true);
            if (LOGD) {
                Log.d("VolumeUI", "Registering default volume controller");
            }
            final VolumeDialogControllerImpl volumeDialogControllerImpl2 = volumeDialogComponent.mController;
            AudioManager audioManager = volumeDialogControllerImpl2.mAudio;
            String str = VolumeDialogControllerImpl.TAG;
            try {
                audioManager.setVolumeController(volumeDialogControllerImpl2.mVolumeController);
            } catch (SecurityException e) {
                Log.w(str, "Unable to set the volume controller", e);
            }
            VolumePolicy volumePolicy = volumeDialogControllerImpl2.mVolumePolicy;
            volumeDialogControllerImpl2.mVolumePolicy = volumePolicy;
            if (volumePolicy != null) {
                try {
                    audioManager.setVolumePolicy(volumePolicy);
                } catch (NoSuchMethodError unused) {
                    Log.w(str, "No volume policy api");
                }
            }
            if (C3599D.BUG) {
                Log.d(VolumeDialogControllerImpl.TAG, "showDndTile");
            }
            Intent intent2 = DndTile.DND_SETTINGS;
            Prefs.putBoolean(volumeDialogControllerImpl2.mContext, "DndTileVisible", true);
            DumpManager dumpManager = volumeDialogControllerImpl2.mDumpManager;
            dumpManager.getClass();
            DumpManager.registerDumpable$default(dumpManager, "VolumeDialogControllerImpl", volumeDialogControllerImpl2);
            try {
                MediaSessions mediaSessions = volumeDialogControllerImpl2.mMediaSessions;
                mediaSessions.getClass();
                MediaSessions.HandlerC0937H handlerC0937H = mediaSessions.mHandler;
                if (C0934D.BUG) {
                    Log.d(MediaSessions.TAG, "init");
                }
                MediaSessions.C09351 c09351 = mediaSessions.mSessionsListener;
                MediaSessionManager mediaSessionManager = mediaSessions.mMgr;
                mediaSessionManager.addOnActiveSessionsChangedListener(c09351, null, handlerC0937H);
                mediaSessions.mInit = true;
                handlerC0937H.sendEmptyMessage(1);
                mediaSessionManager.registerRemoteSessionCallback(mediaSessions.mHandlerExecutor, mediaSessions.mRemoteSessionCallback);
            } catch (SecurityException e2) {
                Log.w(str, "No access to media sessions", e2);
            }
            final Consumer consumer = new Consumer() { // from class: com.android.systemui.volume.VolumeDialogControllerImpl$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    switch (i) {
                        case 0:
                            volumeDialogControllerImpl2.mIsSupportTvVolumeControl = (Boolean) obj;
                            break;
                        case 1:
                            volumeDialogControllerImpl2.mIsDLNAEnabled = (Boolean) obj;
                            break;
                        case 2:
                            VolumeDialogControllerImpl volumeDialogControllerImpl3 = volumeDialogControllerImpl2;
                            Boolean bool = (Boolean) obj;
                            if (volumeDialogControllerImpl3.streamStateW(20).muted != bool.booleanValue()) {
                                volumeDialogControllerImpl3.updateStreamMuteW(20, bool.booleanValue());
                            }
                            if (volumeDialogControllerImpl3.mIsVolumeDialogShowing) {
                                int i3 = volumeDialogControllerImpl3.mSmartViewFlag;
                                volumeDialogControllerImpl3.onVolumeChangedW(20, i3 != VolumeDialogControllerImpl.FLAG_SMART_VIEW_NONE ? i3 : 1);
                                if (volumeDialogControllerImpl3.isSmartViewEnabled()) {
                                    volumeDialogControllerImpl3.mCallbacks.onStateChanged(volumeDialogControllerImpl3.mState);
                                    break;
                                }
                            }
                            break;
                        case 3:
                            VolumeDialogControllerImpl volumeDialogControllerImpl4 = volumeDialogControllerImpl2;
                            volumeDialogControllerImpl4.getClass();
                            volumeDialogControllerImpl4.mIsBudsTogetherEnabled = ((Boolean) obj).booleanValue();
                            break;
                        case 4:
                            VolumeDialogControllerImpl volumeDialogControllerImpl5 = volumeDialogControllerImpl2;
                            volumeDialogControllerImpl5.getClass();
                            volumeDialogControllerImpl5.mIsMusicShareEnabled = ((Boolean) obj).booleanValue();
                            break;
                        default:
                            VolumeDialogControllerImpl volumeDialogControllerImpl6 = volumeDialogControllerImpl2;
                            int i4 = volumeDialogControllerImpl6.mWakefulnessLifecycle.mWakefulness;
                            boolean z4 = i4 == 0 || i4 == 3 || !volumeDialogControllerImpl6.mDeviceInteractive;
                            volumeDialogControllerImpl6.mState.aodEnabled = z4;
                            if (z4) {
                                volumeDialogControllerImpl6.onVolumeChangedW(3, 1);
                                break;
                            }
                            break;
                    }
                }
            };
            final Consumer consumer2 = new Consumer() { // from class: com.android.systemui.volume.VolumeDialogControllerImpl$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    switch (i2) {
                        case 0:
                            volumeDialogControllerImpl2.mIsSupportTvVolumeControl = (Boolean) obj;
                            break;
                        case 1:
                            volumeDialogControllerImpl2.mIsDLNAEnabled = (Boolean) obj;
                            break;
                        case 2:
                            VolumeDialogControllerImpl volumeDialogControllerImpl3 = volumeDialogControllerImpl2;
                            Boolean bool = (Boolean) obj;
                            if (volumeDialogControllerImpl3.streamStateW(20).muted != bool.booleanValue()) {
                                volumeDialogControllerImpl3.updateStreamMuteW(20, bool.booleanValue());
                            }
                            if (volumeDialogControllerImpl3.mIsVolumeDialogShowing) {
                                int i3 = volumeDialogControllerImpl3.mSmartViewFlag;
                                volumeDialogControllerImpl3.onVolumeChangedW(20, i3 != VolumeDialogControllerImpl.FLAG_SMART_VIEW_NONE ? i3 : 1);
                                if (volumeDialogControllerImpl3.isSmartViewEnabled()) {
                                    volumeDialogControllerImpl3.mCallbacks.onStateChanged(volumeDialogControllerImpl3.mState);
                                    break;
                                }
                            }
                            break;
                        case 3:
                            VolumeDialogControllerImpl volumeDialogControllerImpl4 = volumeDialogControllerImpl2;
                            volumeDialogControllerImpl4.getClass();
                            volumeDialogControllerImpl4.mIsBudsTogetherEnabled = ((Boolean) obj).booleanValue();
                            break;
                        case 4:
                            VolumeDialogControllerImpl volumeDialogControllerImpl5 = volumeDialogControllerImpl2;
                            volumeDialogControllerImpl5.getClass();
                            volumeDialogControllerImpl5.mIsMusicShareEnabled = ((Boolean) obj).booleanValue();
                            break;
                        default:
                            VolumeDialogControllerImpl volumeDialogControllerImpl6 = volumeDialogControllerImpl2;
                            int i4 = volumeDialogControllerImpl6.mWakefulnessLifecycle.mWakefulness;
                            boolean z4 = i4 == 0 || i4 == 3 || !volumeDialogControllerImpl6.mDeviceInteractive;
                            volumeDialogControllerImpl6.mState.aodEnabled = z4;
                            if (z4) {
                                volumeDialogControllerImpl6.onVolumeChangedW(3, 1);
                                break;
                            }
                            break;
                    }
                }
            };
            final BroadcastReceiverManager broadcastReceiverManager = volumeDialogControllerImpl2.mBroadcastReceiverManager;
            BroadcastReceiverManager.BroadcastReceiverItem broadcastReceiverItem = (BroadcastReceiverManager.BroadcastReceiverItem) broadcastReceiverManager.broadcastReceiverItemMap.get(BroadcastReceiverType.DISPLAY_MANAGER);
            if (broadcastReceiverItem != null) {
                BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.volume.util.BroadcastReceiverManager$registerDisplayManagerStateAction$1$1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent3) {
                        String action = intent3.getAction();
                        if (action != null) {
                            int hashCode = action.hashCode();
                            if (hashCode != -1061859923) {
                                if (hashCode != 1735215423) {
                                    if (hashCode == 1886075268 && action.equals("com.samsung.intent.action.DLNA_STATUS_CHANGED")) {
                                        boolean z4 = intent3.getIntExtra(IMSParameter.CALL.STATUS, 0) == 1;
                                        consumer2.accept(Boolean.valueOf(z4));
                                        broadcastReceiverManager.logWrapper.m98d("BroadcastManager", "onReceive : SmartView action=" + intent3.getAction() + ", dlnaEnabled=" + z4);
                                        return;
                                    }
                                    return;
                                }
                                if (!action.equals("com.samsung.intent.action.WIFI_DISPLAY_VOLUME_SUPPORT_CHANGED")) {
                                    return;
                                }
                            } else if (!action.equals("com.samsung.intent.action.WIFI_DISPLAY_SOURCE_STATE")) {
                                return;
                            }
                            int intExtra = intent3.getIntExtra("state", 0);
                            boolean booleanExtra = intent3.getBooleanExtra("isSupportDisplayVolumeControl", false);
                            consumer.accept(Boolean.valueOf(intExtra == 1 && booleanExtra));
                            broadcastReceiverManager.logWrapper.m98d("BroadcastManager", KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m74m(AbstractC0950x8906c950.m92m("onReceive : SmartView action=", intent3.getAction(), ", state=", intExtra, ", isSupportDisplayVolumeControl="), booleanExtra, ", ret=", intExtra == 1 && booleanExtra));
                        }
                    }
                };
                BroadcastDispatcher.registerReceiver$default(broadcastReceiverManager.broadcastDispatcher, broadcastReceiver, broadcastReceiverItem.intentFilter, null, null, 0, null, 60);
                broadcastReceiverItem.receiver = broadcastReceiver;
                Unit unit = Unit.INSTANCE;
            }
            final int i3 = 2;
            final Consumer consumer3 = new Consumer() { // from class: com.android.systemui.volume.VolumeDialogControllerImpl$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    switch (i3) {
                        case 0:
                            volumeDialogControllerImpl2.mIsSupportTvVolumeControl = (Boolean) obj;
                            break;
                        case 1:
                            volumeDialogControllerImpl2.mIsDLNAEnabled = (Boolean) obj;
                            break;
                        case 2:
                            VolumeDialogControllerImpl volumeDialogControllerImpl3 = volumeDialogControllerImpl2;
                            Boolean bool = (Boolean) obj;
                            if (volumeDialogControllerImpl3.streamStateW(20).muted != bool.booleanValue()) {
                                volumeDialogControllerImpl3.updateStreamMuteW(20, bool.booleanValue());
                            }
                            if (volumeDialogControllerImpl3.mIsVolumeDialogShowing) {
                                int i32 = volumeDialogControllerImpl3.mSmartViewFlag;
                                volumeDialogControllerImpl3.onVolumeChangedW(20, i32 != VolumeDialogControllerImpl.FLAG_SMART_VIEW_NONE ? i32 : 1);
                                if (volumeDialogControllerImpl3.isSmartViewEnabled()) {
                                    volumeDialogControllerImpl3.mCallbacks.onStateChanged(volumeDialogControllerImpl3.mState);
                                    break;
                                }
                            }
                            break;
                        case 3:
                            VolumeDialogControllerImpl volumeDialogControllerImpl4 = volumeDialogControllerImpl2;
                            volumeDialogControllerImpl4.getClass();
                            volumeDialogControllerImpl4.mIsBudsTogetherEnabled = ((Boolean) obj).booleanValue();
                            break;
                        case 4:
                            VolumeDialogControllerImpl volumeDialogControllerImpl5 = volumeDialogControllerImpl2;
                            volumeDialogControllerImpl5.getClass();
                            volumeDialogControllerImpl5.mIsMusicShareEnabled = ((Boolean) obj).booleanValue();
                            break;
                        default:
                            VolumeDialogControllerImpl volumeDialogControllerImpl6 = volumeDialogControllerImpl2;
                            int i4 = volumeDialogControllerImpl6.mWakefulnessLifecycle.mWakefulness;
                            boolean z4 = i4 == 0 || i4 == 3 || !volumeDialogControllerImpl6.mDeviceInteractive;
                            volumeDialogControllerImpl6.mState.aodEnabled = z4;
                            if (z4) {
                                volumeDialogControllerImpl6.onVolumeChangedW(3, 1);
                                break;
                            }
                            break;
                    }
                }
            };
            final DisplayManagerWrapper displayManagerWrapper = volumeDialogControllerImpl2.mDisplayManagerWrapper;
            displayManagerWrapper.getClass();
            SemDisplayVolumeListener semDisplayVolumeListener = new SemDisplayVolumeListener() { // from class: com.android.systemui.volume.util.DisplayManagerWrapper$registerDisplayVolumeListener$1
                public final void onVolumeChanged(int i4, int i5, int i6, boolean z4) {
                    DisplayManagerWrapper displayManagerWrapper2 = DisplayManagerWrapper.this;
                    displayManagerWrapper2.displayCurrentVolume = i6;
                    displayManagerWrapper2.minSmartViewVol = i4;
                    displayManagerWrapper2.maxSmartViewVol = i5;
                    LogWrapper logWrapper = displayManagerWrapper2.logWrapper;
                    StringBuilder m45m = GridLayoutManager$$ExternalSyntheticOutline0.m45m("onDisplayVolumeChanged : curVol = ", i6, ", minVol = ", i4, ", maxVol = ");
                    m45m.append(i5);
                    m45m.append(", mute=");
                    m45m.append(z4);
                    logWrapper.m98d("DisplayManagerWrapper", m45m.toString());
                    consumer3.accept(Boolean.valueOf(z4));
                }
            };
            SystemServiceExtension.INSTANCE.getClass();
            SystemServiceExtension.getDisplayManager(displayManagerWrapper.context).semRegisterDisplayVolumeListener(semDisplayVolumeListener, new Handler());
            final int i4 = 3;
            final Consumer consumer4 = new Consumer() { // from class: com.android.systemui.volume.VolumeDialogControllerImpl$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    switch (i4) {
                        case 0:
                            volumeDialogControllerImpl2.mIsSupportTvVolumeControl = (Boolean) obj;
                            break;
                        case 1:
                            volumeDialogControllerImpl2.mIsDLNAEnabled = (Boolean) obj;
                            break;
                        case 2:
                            VolumeDialogControllerImpl volumeDialogControllerImpl3 = volumeDialogControllerImpl2;
                            Boolean bool = (Boolean) obj;
                            if (volumeDialogControllerImpl3.streamStateW(20).muted != bool.booleanValue()) {
                                volumeDialogControllerImpl3.updateStreamMuteW(20, bool.booleanValue());
                            }
                            if (volumeDialogControllerImpl3.mIsVolumeDialogShowing) {
                                int i32 = volumeDialogControllerImpl3.mSmartViewFlag;
                                volumeDialogControllerImpl3.onVolumeChangedW(20, i32 != VolumeDialogControllerImpl.FLAG_SMART_VIEW_NONE ? i32 : 1);
                                if (volumeDialogControllerImpl3.isSmartViewEnabled()) {
                                    volumeDialogControllerImpl3.mCallbacks.onStateChanged(volumeDialogControllerImpl3.mState);
                                    break;
                                }
                            }
                            break;
                        case 3:
                            VolumeDialogControllerImpl volumeDialogControllerImpl4 = volumeDialogControllerImpl2;
                            volumeDialogControllerImpl4.getClass();
                            volumeDialogControllerImpl4.mIsBudsTogetherEnabled = ((Boolean) obj).booleanValue();
                            break;
                        case 4:
                            VolumeDialogControllerImpl volumeDialogControllerImpl5 = volumeDialogControllerImpl2;
                            volumeDialogControllerImpl5.getClass();
                            volumeDialogControllerImpl5.mIsMusicShareEnabled = ((Boolean) obj).booleanValue();
                            break;
                        default:
                            VolumeDialogControllerImpl volumeDialogControllerImpl6 = volumeDialogControllerImpl2;
                            int i42 = volumeDialogControllerImpl6.mWakefulnessLifecycle.mWakefulness;
                            boolean z4 = i42 == 0 || i42 == 3 || !volumeDialogControllerImpl6.mDeviceInteractive;
                            volumeDialogControllerImpl6.mState.aodEnabled = z4;
                            if (z4) {
                                volumeDialogControllerImpl6.onVolumeChangedW(3, 1);
                                break;
                            }
                            break;
                    }
                }
            };
            final VolumeDialogControllerImpl$$ExternalSyntheticLambda1 volumeDialogControllerImpl$$ExternalSyntheticLambda1 = new VolumeDialogControllerImpl$$ExternalSyntheticLambda1(volumeDialogControllerImpl2, 1);
            Map map = broadcastReceiverManager.broadcastReceiverItemMap;
            BroadcastReceiverManager.BroadcastReceiverItem broadcastReceiverItem2 = (BroadcastReceiverManager.BroadcastReceiverItem) map.get(BroadcastReceiverType.BUDS_TOGETHER);
            if (broadcastReceiverItem2 != null) {
                BroadcastReceiver broadcastReceiver2 = new BroadcastReceiver() { // from class: com.android.systemui.volume.util.BroadcastReceiverManager$registerAudioSharingStateAction$1$1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent3) {
                        String action = intent3.getAction();
                        if (Intrinsics.areEqual(action, "com.samsung.android.bluetooth.audiocast.action.device.AUDIO_SHARING_MODE_CHANGED")) {
                            boolean z4 = intent3.getIntExtra("com.samsung.android.bluetooth.cast.extra.AUDIO_SHARING_MODE", 0) == 1;
                            consumer4.accept(Boolean.valueOf(z4));
                            volumeDialogControllerImpl$$ExternalSyntheticLambda1.run();
                            broadcastReceiverManager.logWrapper.m98d("BroadcastManager", "onReceive : " + action + " " + z4);
                            return;
                        }
                        if (Intrinsics.areEqual(action, "com.samsung.android.bluetooth.audiocast.action.device.AUDIO_SHARING_DEVICE_VOLUME_CHANGED")) {
                            int intExtra = intent3.getIntExtra("com.samsung.android.bluetooth.cast.extra.AUDIO_SHARING_DEVICE_VOLUME", 0);
                            volumeDialogControllerImpl$$ExternalSyntheticLambda1.run();
                            broadcastReceiverManager.logWrapper.m98d("BroadcastManager", "onReceive : " + action + " " + intExtra);
                        }
                    }
                };
                BroadcastDispatcher.registerReceiver$default(broadcastReceiverManager.broadcastDispatcher, broadcastReceiver2, broadcastReceiverItem2.intentFilter, null, null, 0, null, 60);
                broadcastReceiverItem2.receiver = broadcastReceiver2;
                Unit unit2 = Unit.INSTANCE;
            }
            final int i5 = 4;
            final Consumer consumer5 = new Consumer() { // from class: com.android.systemui.volume.VolumeDialogControllerImpl$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    switch (i5) {
                        case 0:
                            volumeDialogControllerImpl2.mIsSupportTvVolumeControl = (Boolean) obj;
                            break;
                        case 1:
                            volumeDialogControllerImpl2.mIsDLNAEnabled = (Boolean) obj;
                            break;
                        case 2:
                            VolumeDialogControllerImpl volumeDialogControllerImpl3 = volumeDialogControllerImpl2;
                            Boolean bool = (Boolean) obj;
                            if (volumeDialogControllerImpl3.streamStateW(20).muted != bool.booleanValue()) {
                                volumeDialogControllerImpl3.updateStreamMuteW(20, bool.booleanValue());
                            }
                            if (volumeDialogControllerImpl3.mIsVolumeDialogShowing) {
                                int i32 = volumeDialogControllerImpl3.mSmartViewFlag;
                                volumeDialogControllerImpl3.onVolumeChangedW(20, i32 != VolumeDialogControllerImpl.FLAG_SMART_VIEW_NONE ? i32 : 1);
                                if (volumeDialogControllerImpl3.isSmartViewEnabled()) {
                                    volumeDialogControllerImpl3.mCallbacks.onStateChanged(volumeDialogControllerImpl3.mState);
                                    break;
                                }
                            }
                            break;
                        case 3:
                            VolumeDialogControllerImpl volumeDialogControllerImpl4 = volumeDialogControllerImpl2;
                            volumeDialogControllerImpl4.getClass();
                            volumeDialogControllerImpl4.mIsBudsTogetherEnabled = ((Boolean) obj).booleanValue();
                            break;
                        case 4:
                            VolumeDialogControllerImpl volumeDialogControllerImpl5 = volumeDialogControllerImpl2;
                            volumeDialogControllerImpl5.getClass();
                            volumeDialogControllerImpl5.mIsMusicShareEnabled = ((Boolean) obj).booleanValue();
                            break;
                        default:
                            VolumeDialogControllerImpl volumeDialogControllerImpl6 = volumeDialogControllerImpl2;
                            int i42 = volumeDialogControllerImpl6.mWakefulnessLifecycle.mWakefulness;
                            boolean z4 = i42 == 0 || i42 == 3 || !volumeDialogControllerImpl6.mDeviceInteractive;
                            volumeDialogControllerImpl6.mState.aodEnabled = z4;
                            if (z4) {
                                volumeDialogControllerImpl6.onVolumeChangedW(3, 1);
                                break;
                            }
                            break;
                    }
                }
            };
            final VolumeDialogControllerImpl$$ExternalSyntheticLambda1 volumeDialogControllerImpl$$ExternalSyntheticLambda12 = new VolumeDialogControllerImpl$$ExternalSyntheticLambda1(volumeDialogControllerImpl2, 2);
            BroadcastReceiverManager.BroadcastReceiverItem broadcastReceiverItem3 = (BroadcastReceiverManager.BroadcastReceiverItem) map.get(BroadcastReceiverType.MUSIC_SHARE);
            if (broadcastReceiverItem3 != null) {
                BroadcastReceiver broadcastReceiver3 = new BroadcastReceiver() { // from class: com.android.systemui.volume.util.BroadcastReceiverManager$registerMusicShareStateAction$1$1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent3) {
                        String action = intent3.getAction();
                        if (Intrinsics.areEqual(action, "com.samsung.android.bluetooth.audiocast.action.device.CONNECTION_STATE_CHANGED")) {
                            boolean z4 = intent3.getIntExtra("com.samsung.android.bluetooth.cast.extra.STATE", 0) == 2;
                            consumer5.accept(Boolean.valueOf(z4));
                            volumeDialogControllerImpl$$ExternalSyntheticLambda12.run();
                            broadcastReceiverManager.logWrapper.m98d("BroadcastManager", "onReceive : " + action + " " + z4);
                        }
                    }
                };
                BroadcastDispatcher.registerReceiver$default(broadcastReceiverManager.broadcastDispatcher, broadcastReceiver3, broadcastReceiverItem3.intentFilter, null, null, 0, null, 60);
                broadcastReceiverItem3.receiver = broadcastReceiver3;
                Unit unit3 = Unit.INSTANCE;
            }
            final int i6 = 5;
            final Consumer consumer6 = new Consumer() { // from class: com.android.systemui.volume.VolumeDialogControllerImpl$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    switch (i6) {
                        case 0:
                            volumeDialogControllerImpl2.mIsSupportTvVolumeControl = (Boolean) obj;
                            break;
                        case 1:
                            volumeDialogControllerImpl2.mIsDLNAEnabled = (Boolean) obj;
                            break;
                        case 2:
                            VolumeDialogControllerImpl volumeDialogControllerImpl3 = volumeDialogControllerImpl2;
                            Boolean bool = (Boolean) obj;
                            if (volumeDialogControllerImpl3.streamStateW(20).muted != bool.booleanValue()) {
                                volumeDialogControllerImpl3.updateStreamMuteW(20, bool.booleanValue());
                            }
                            if (volumeDialogControllerImpl3.mIsVolumeDialogShowing) {
                                int i32 = volumeDialogControllerImpl3.mSmartViewFlag;
                                volumeDialogControllerImpl3.onVolumeChangedW(20, i32 != VolumeDialogControllerImpl.FLAG_SMART_VIEW_NONE ? i32 : 1);
                                if (volumeDialogControllerImpl3.isSmartViewEnabled()) {
                                    volumeDialogControllerImpl3.mCallbacks.onStateChanged(volumeDialogControllerImpl3.mState);
                                    break;
                                }
                            }
                            break;
                        case 3:
                            VolumeDialogControllerImpl volumeDialogControllerImpl4 = volumeDialogControllerImpl2;
                            volumeDialogControllerImpl4.getClass();
                            volumeDialogControllerImpl4.mIsBudsTogetherEnabled = ((Boolean) obj).booleanValue();
                            break;
                        case 4:
                            VolumeDialogControllerImpl volumeDialogControllerImpl5 = volumeDialogControllerImpl2;
                            volumeDialogControllerImpl5.getClass();
                            volumeDialogControllerImpl5.mIsMusicShareEnabled = ((Boolean) obj).booleanValue();
                            break;
                        default:
                            VolumeDialogControllerImpl volumeDialogControllerImpl6 = volumeDialogControllerImpl2;
                            int i42 = volumeDialogControllerImpl6.mWakefulnessLifecycle.mWakefulness;
                            boolean z4 = i42 == 0 || i42 == 3 || !volumeDialogControllerImpl6.mDeviceInteractive;
                            volumeDialogControllerImpl6.mState.aodEnabled = z4;
                            if (z4) {
                                volumeDialogControllerImpl6.onVolumeChangedW(3, 1);
                                break;
                            }
                            break;
                    }
                }
            };
            BroadcastReceiverManager.BroadcastReceiverItem broadcastReceiverItem4 = (BroadcastReceiverManager.BroadcastReceiverItem) map.get(BroadcastReceiverType.AOD);
            if (broadcastReceiverItem4 != null) {
                BroadcastReceiver broadcastReceiver4 = new BroadcastReceiver() { // from class: com.android.systemui.volume.util.BroadcastReceiverManager$registerAODShowAction$1$1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent3) {
                        int intExtra;
                        if (Intrinsics.areEqual(intent3.getAction(), "com.samsung.android.app.aodservice.intent.action.CHANGE_AOD_MODE") && (intExtra = intent3.getIntExtra("info", -1)) == 18) {
                            consumer6.accept(Boolean.TRUE);
                            broadcastReceiverManager.logWrapper.m98d("BroadcastManager", "onReceive : " + intExtra + ", long press on AOD state true");
                        }
                    }
                };
                BroadcastDispatcher.registerReceiver$default(broadcastReceiverManager.broadcastDispatcher, broadcastReceiver4, broadcastReceiverItem4.intentFilter, null, null, 0, null, 60);
                broadcastReceiverItem4.receiver = broadcastReceiver4;
                Unit unit4 = Unit.INSTANCE;
            }
            Prefs.putBoolean(volumeDialogComponent.mContext, "DndTileCombinedIcon", true);
        }
    }
}
