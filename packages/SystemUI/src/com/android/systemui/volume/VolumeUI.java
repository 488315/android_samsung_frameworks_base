package com.android.systemui.volume;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.VolumePolicy;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.text.TextUtils;
import android.util.Log;
import androidx.constraintlayout.widget.ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0;
import com.android.app.tracing.coroutines.TraceData$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.CoreStartable;
import com.android.systemui.Prefs;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.qs.tiles.DndTile;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.volume.util.BluetoothIconServerUtils;
import com.android.systemui.volume.util.BroadcastReceiverManager;
import com.android.systemui.volume.util.BroadcastReceiverType;
import com.android.systemui.volume.util.DisplayManagerWrapper;
import com.android.systemui.volume.util.DisplayManagerWrapper$registerDisplayVolumeListener$1;
import com.android.systemui.volume.util.SystemServiceExtension;
import com.samsung.android.settingslib.bluetooth.scsp.ScspUtils;
import com.samsung.android.settingslib.bluetooth.scsp.ScspUtils$$ExternalSyntheticLambda2;
import com.sec.ims.IMSParameter;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class VolumeUI implements CoreStartable, ConfigurationController.ConfigurationListener {
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

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onConfigChanged(Configuration configuration) {
        if (this.mEnabled) {
            VolumeDialogComponent volumeDialogComponent = this.mVolumeComponent;
            if (volumeDialogComponent.mConfigChanges.applyNewConfig(volumeDialogComponent.mContext.getResources())) {
                volumeDialogComponent.mController.mCallbacks.onConfigurationChanged();
            }
        }
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        final int i = 3;
        final int i2 = 2;
        final int i3 = 0;
        boolean z = this.mContext.getResources().getBoolean(R.bool.enable_volume_ui);
        boolean z2 = this.mContext.getResources().getBoolean(R.bool.enable_safety_warning);
        final int i4 = 1;
        boolean z3 = z || z2;
        this.mEnabled = z3;
        if (z3) {
            VolumeDialogComponent volumeDialogComponent = this.mVolumeComponent;
            VolumeDialogControllerImpl volumeDialogControllerImpl = volumeDialogComponent.mController;
            volumeDialogControllerImpl.mShowVolumeDialog = z;
            volumeDialogControllerImpl.mShowSafetyWarning = z2;
            Context context = this.mContext;
            Intent intent = DndTile.DND_SETTINGS;
            Prefs.putBoolean(context, "DndTileVisible", true);
            if (LOGD) {
                Log.d("VolumeUI", "Registering default volume controller");
            }
            final VolumeDialogControllerImpl volumeDialogControllerImpl2 = volumeDialogComponent.mController;
            volumeDialogControllerImpl2.getClass();
            String str = VolumeDialogControllerImpl.TAG;
            try {
                volumeDialogControllerImpl2.mAudio.setVolumeController(volumeDialogControllerImpl2.mVolumeController);
            } catch (SecurityException e) {
                Log.w(str, "Unable to set the volume controller", e);
            }
            VolumePolicy volumePolicy = volumeDialogControllerImpl2.mVolumePolicy;
            volumeDialogControllerImpl2.mVolumePolicy = volumePolicy;
            if (volumePolicy != null) {
                try {
                    volumeDialogControllerImpl2.mAudio.setVolumePolicy(volumePolicy);
                } catch (NoSuchMethodError unused) {
                    Log.w(str, "No volume policy api");
                }
            }
            if (D.BUG) {
                Log.d(VolumeDialogControllerImpl.TAG, "showDndTile");
            }
            Context context2 = volumeDialogControllerImpl2.mContext;
            Intent intent2 = DndTile.DND_SETTINGS;
            Prefs.putBoolean(context2, "DndTileVisible", true);
            try {
                volumeDialogControllerImpl2.mMediaSessions.init();
            } catch (SecurityException e2) {
                Log.w(str, "No access to media sessions", e2);
            }
            final Consumer consumer = new Consumer() { // from class: com.android.systemui.volume.VolumeDialogControllerImpl$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    int i5 = i3;
                    VolumeDialogControllerImpl volumeDialogControllerImpl3 = volumeDialogControllerImpl2;
                    switch (i5) {
                        case 0:
                            volumeDialogControllerImpl3.mIsSupportTvVolumeControl = (Boolean) obj;
                            break;
                        case 1:
                            Context context3 = volumeDialogControllerImpl3.mContext;
                            BluetoothIconServerUtils bluetoothIconServerUtils = BluetoothIconServerUtils.INSTANCE;
                            String str2 = ScspUtils.FILE_PATH_ROOT;
                            Executors.newSingleThreadExecutor().execute(new ScspUtils$$ExternalSyntheticLambda2((ArrayList) obj, context3, null, new Handler(Looper.getMainLooper())));
                            volumeDialogControllerImpl3.mCallbacks.onStateChanged(volumeDialogControllerImpl3.mState);
                            break;
                        case 2:
                            volumeDialogControllerImpl3.mIsDLNAEnabled = (Boolean) obj;
                            break;
                        case 3:
                            Boolean bool = (Boolean) obj;
                            if (volumeDialogControllerImpl3.streamStateW(20).muted != bool.booleanValue()) {
                                volumeDialogControllerImpl3.updateStreamMuteW(20, bool.booleanValue());
                            }
                            if (volumeDialogControllerImpl3.mIsVolumeDialogShowing) {
                                int i6 = volumeDialogControllerImpl3.mSmartViewFlag;
                                volumeDialogControllerImpl3.onVolumeChangedW(20, i6 != VolumeDialogControllerImpl.FLAG_SMART_VIEW_NONE ? i6 : 1);
                                if (volumeDialogControllerImpl3.isSmartViewEnabled()) {
                                    volumeDialogControllerImpl3.mCallbacks.onStateChanged(volumeDialogControllerImpl3.mState);
                                    break;
                                }
                            }
                            break;
                        case 4:
                            volumeDialogControllerImpl3.getClass();
                            volumeDialogControllerImpl3.mIsBudsTogetherEnabled = ((Boolean) obj).booleanValue();
                            break;
                        case 5:
                            volumeDialogControllerImpl3.getClass();
                            volumeDialogControllerImpl3.mIsMusicShareEnabled = ((Boolean) obj).booleanValue();
                            break;
                        default:
                            int i7 = volumeDialogControllerImpl3.mWakefulnessLifecycle.mWakefulness;
                            boolean z4 = i7 == 0 || i7 == 3 || !volumeDialogControllerImpl3.mDeviceInteractive;
                            volumeDialogControllerImpl3.mState.aodEnabled = z4;
                            if (z4) {
                                volumeDialogControllerImpl3.onVolumeChangedW(3, 1);
                                break;
                            }
                            break;
                    }
                }
            };
            final Consumer consumer2 = new Consumer() { // from class: com.android.systemui.volume.VolumeDialogControllerImpl$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    int i5 = i2;
                    VolumeDialogControllerImpl volumeDialogControllerImpl3 = volumeDialogControllerImpl2;
                    switch (i5) {
                        case 0:
                            volumeDialogControllerImpl3.mIsSupportTvVolumeControl = (Boolean) obj;
                            break;
                        case 1:
                            Context context3 = volumeDialogControllerImpl3.mContext;
                            BluetoothIconServerUtils bluetoothIconServerUtils = BluetoothIconServerUtils.INSTANCE;
                            String str2 = ScspUtils.FILE_PATH_ROOT;
                            Executors.newSingleThreadExecutor().execute(new ScspUtils$$ExternalSyntheticLambda2((ArrayList) obj, context3, null, new Handler(Looper.getMainLooper())));
                            volumeDialogControllerImpl3.mCallbacks.onStateChanged(volumeDialogControllerImpl3.mState);
                            break;
                        case 2:
                            volumeDialogControllerImpl3.mIsDLNAEnabled = (Boolean) obj;
                            break;
                        case 3:
                            Boolean bool = (Boolean) obj;
                            if (volumeDialogControllerImpl3.streamStateW(20).muted != bool.booleanValue()) {
                                volumeDialogControllerImpl3.updateStreamMuteW(20, bool.booleanValue());
                            }
                            if (volumeDialogControllerImpl3.mIsVolumeDialogShowing) {
                                int i6 = volumeDialogControllerImpl3.mSmartViewFlag;
                                volumeDialogControllerImpl3.onVolumeChangedW(20, i6 != VolumeDialogControllerImpl.FLAG_SMART_VIEW_NONE ? i6 : 1);
                                if (volumeDialogControllerImpl3.isSmartViewEnabled()) {
                                    volumeDialogControllerImpl3.mCallbacks.onStateChanged(volumeDialogControllerImpl3.mState);
                                    break;
                                }
                            }
                            break;
                        case 4:
                            volumeDialogControllerImpl3.getClass();
                            volumeDialogControllerImpl3.mIsBudsTogetherEnabled = ((Boolean) obj).booleanValue();
                            break;
                        case 5:
                            volumeDialogControllerImpl3.getClass();
                            volumeDialogControllerImpl3.mIsMusicShareEnabled = ((Boolean) obj).booleanValue();
                            break;
                        default:
                            int i7 = volumeDialogControllerImpl3.mWakefulnessLifecycle.mWakefulness;
                            boolean z4 = i7 == 0 || i7 == 3 || !volumeDialogControllerImpl3.mDeviceInteractive;
                            volumeDialogControllerImpl3.mState.aodEnabled = z4;
                            if (z4) {
                                volumeDialogControllerImpl3.onVolumeChangedW(3, 1);
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
                    public final void onReceive(Context context3, Intent intent3) {
                        String action = intent3.getAction();
                        if (action != null) {
                            int hashCode = action.hashCode();
                            if (hashCode != -1061859923) {
                                if (hashCode != 1735215423) {
                                    if (hashCode == 1886075268 && action.equals("com.samsung.intent.action.DLNA_STATUS_CHANGED")) {
                                        boolean z4 = intent3.getIntExtra(IMSParameter.CALL.STATUS, 0) == 1;
                                        consumer2.accept(Boolean.valueOf(z4));
                                        broadcastReceiverManager.logWrapper.d("vol.BroadcastManager", "onReceive : SmartView action=" + intent3.getAction() + ", dlnaEnabled=" + z4);
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
                            broadcastReceiverManager.logWrapper.d("vol.BroadcastManager", KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m(intExtra, "onReceive : SmartView action=", intent3.getAction(), ", state=", ", isSupportDisplayVolumeControl="), booleanExtra, ", ret=", intExtra == 1 && booleanExtra));
                        }
                    }
                };
                BroadcastDispatcher.registerReceiver$default(broadcastReceiverManager.broadcastDispatcher, broadcastReceiver, broadcastReceiverItem.intentFilter, null, null, 0, null, 60);
                broadcastReceiverItem.receiver = broadcastReceiver;
                Unit unit = Unit.INSTANCE;
            }
            Consumer consumer3 = new Consumer() { // from class: com.android.systemui.volume.VolumeDialogControllerImpl$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    int i5 = i;
                    VolumeDialogControllerImpl volumeDialogControllerImpl3 = volumeDialogControllerImpl2;
                    switch (i5) {
                        case 0:
                            volumeDialogControllerImpl3.mIsSupportTvVolumeControl = (Boolean) obj;
                            break;
                        case 1:
                            Context context3 = volumeDialogControllerImpl3.mContext;
                            BluetoothIconServerUtils bluetoothIconServerUtils = BluetoothIconServerUtils.INSTANCE;
                            String str2 = ScspUtils.FILE_PATH_ROOT;
                            Executors.newSingleThreadExecutor().execute(new ScspUtils$$ExternalSyntheticLambda2((ArrayList) obj, context3, null, new Handler(Looper.getMainLooper())));
                            volumeDialogControllerImpl3.mCallbacks.onStateChanged(volumeDialogControllerImpl3.mState);
                            break;
                        case 2:
                            volumeDialogControllerImpl3.mIsDLNAEnabled = (Boolean) obj;
                            break;
                        case 3:
                            Boolean bool = (Boolean) obj;
                            if (volumeDialogControllerImpl3.streamStateW(20).muted != bool.booleanValue()) {
                                volumeDialogControllerImpl3.updateStreamMuteW(20, bool.booleanValue());
                            }
                            if (volumeDialogControllerImpl3.mIsVolumeDialogShowing) {
                                int i6 = volumeDialogControllerImpl3.mSmartViewFlag;
                                volumeDialogControllerImpl3.onVolumeChangedW(20, i6 != VolumeDialogControllerImpl.FLAG_SMART_VIEW_NONE ? i6 : 1);
                                if (volumeDialogControllerImpl3.isSmartViewEnabled()) {
                                    volumeDialogControllerImpl3.mCallbacks.onStateChanged(volumeDialogControllerImpl3.mState);
                                    break;
                                }
                            }
                            break;
                        case 4:
                            volumeDialogControllerImpl3.getClass();
                            volumeDialogControllerImpl3.mIsBudsTogetherEnabled = ((Boolean) obj).booleanValue();
                            break;
                        case 5:
                            volumeDialogControllerImpl3.getClass();
                            volumeDialogControllerImpl3.mIsMusicShareEnabled = ((Boolean) obj).booleanValue();
                            break;
                        default:
                            int i7 = volumeDialogControllerImpl3.mWakefulnessLifecycle.mWakefulness;
                            boolean z4 = i7 == 0 || i7 == 3 || !volumeDialogControllerImpl3.mDeviceInteractive;
                            volumeDialogControllerImpl3.mState.aodEnabled = z4;
                            if (z4) {
                                volumeDialogControllerImpl3.onVolumeChangedW(3, 1);
                                break;
                            }
                            break;
                    }
                }
            };
            DisplayManagerWrapper displayManagerWrapper = volumeDialogControllerImpl2.mDisplayManagerWrapper;
            displayManagerWrapper.getClass();
            DisplayManagerWrapper$registerDisplayVolumeListener$1 displayManagerWrapper$registerDisplayVolumeListener$1 = new DisplayManagerWrapper$registerDisplayVolumeListener$1(displayManagerWrapper, consumer3);
            SystemServiceExtension systemServiceExtension = SystemServiceExtension.INSTANCE;
            Context context3 = displayManagerWrapper.context;
            systemServiceExtension.getClass();
            SystemServiceExtension.getDisplayManager(context3).semRegisterDisplayVolumeListener(displayManagerWrapper$registerDisplayVolumeListener$1, new Handler());
            displayManagerWrapper.displayVolumeListener = displayManagerWrapper$registerDisplayVolumeListener$1;
            final int i5 = 4;
            final Consumer consumer4 = new Consumer() { // from class: com.android.systemui.volume.VolumeDialogControllerImpl$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    int i52 = i5;
                    VolumeDialogControllerImpl volumeDialogControllerImpl3 = volumeDialogControllerImpl2;
                    switch (i52) {
                        case 0:
                            volumeDialogControllerImpl3.mIsSupportTvVolumeControl = (Boolean) obj;
                            break;
                        case 1:
                            Context context32 = volumeDialogControllerImpl3.mContext;
                            BluetoothIconServerUtils bluetoothIconServerUtils = BluetoothIconServerUtils.INSTANCE;
                            String str2 = ScspUtils.FILE_PATH_ROOT;
                            Executors.newSingleThreadExecutor().execute(new ScspUtils$$ExternalSyntheticLambda2((ArrayList) obj, context32, null, new Handler(Looper.getMainLooper())));
                            volumeDialogControllerImpl3.mCallbacks.onStateChanged(volumeDialogControllerImpl3.mState);
                            break;
                        case 2:
                            volumeDialogControllerImpl3.mIsDLNAEnabled = (Boolean) obj;
                            break;
                        case 3:
                            Boolean bool = (Boolean) obj;
                            if (volumeDialogControllerImpl3.streamStateW(20).muted != bool.booleanValue()) {
                                volumeDialogControllerImpl3.updateStreamMuteW(20, bool.booleanValue());
                            }
                            if (volumeDialogControllerImpl3.mIsVolumeDialogShowing) {
                                int i6 = volumeDialogControllerImpl3.mSmartViewFlag;
                                volumeDialogControllerImpl3.onVolumeChangedW(20, i6 != VolumeDialogControllerImpl.FLAG_SMART_VIEW_NONE ? i6 : 1);
                                if (volumeDialogControllerImpl3.isSmartViewEnabled()) {
                                    volumeDialogControllerImpl3.mCallbacks.onStateChanged(volumeDialogControllerImpl3.mState);
                                    break;
                                }
                            }
                            break;
                        case 4:
                            volumeDialogControllerImpl3.getClass();
                            volumeDialogControllerImpl3.mIsBudsTogetherEnabled = ((Boolean) obj).booleanValue();
                            break;
                        case 5:
                            volumeDialogControllerImpl3.getClass();
                            volumeDialogControllerImpl3.mIsMusicShareEnabled = ((Boolean) obj).booleanValue();
                            break;
                        default:
                            int i7 = volumeDialogControllerImpl3.mWakefulnessLifecycle.mWakefulness;
                            boolean z4 = i7 == 0 || i7 == 3 || !volumeDialogControllerImpl3.mDeviceInteractive;
                            volumeDialogControllerImpl3.mState.aodEnabled = z4;
                            if (z4) {
                                volumeDialogControllerImpl3.onVolumeChangedW(3, 1);
                                break;
                            }
                            break;
                    }
                }
            };
            final VolumeDialogControllerImpl$$ExternalSyntheticLambda1 volumeDialogControllerImpl$$ExternalSyntheticLambda1 = new VolumeDialogControllerImpl$$ExternalSyntheticLambda1(volumeDialogControllerImpl2, 2);
            BroadcastReceiverManager.BroadcastReceiverItem broadcastReceiverItem2 = (BroadcastReceiverManager.BroadcastReceiverItem) broadcastReceiverManager.broadcastReceiverItemMap.get(BroadcastReceiverType.BUDS_TOGETHER);
            if (broadcastReceiverItem2 != null) {
                BroadcastReceiver broadcastReceiver2 = new BroadcastReceiver() { // from class: com.android.systemui.volume.util.BroadcastReceiverManager$registerAudioSharingStateAction$1$1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context4, Intent intent3) {
                        String action = intent3.getAction();
                        if (!Intrinsics.areEqual(action, "com.samsung.android.bluetooth.audiocast.action.device.AUDIO_SHARING_MODE_CHANGED")) {
                            if (Intrinsics.areEqual(action, "com.samsung.android.bluetooth.audiocast.action.device.AUDIO_SHARING_DEVICE_VOLUME_CHANGED")) {
                                int intExtra = intent3.getIntExtra("com.samsung.android.bluetooth.cast.extra.AUDIO_SHARING_DEVICE_VOLUME", 0);
                                volumeDialogControllerImpl$$ExternalSyntheticLambda1.run();
                                broadcastReceiverManager.logWrapper.d("vol.BroadcastManager", TraceData$$ExternalSyntheticOutline0.m(intExtra, "onReceive : ", action, " "));
                                return;
                            }
                            return;
                        }
                        boolean z4 = intent3.getIntExtra("com.samsung.android.bluetooth.cast.extra.AUDIO_SHARING_MODE", 0) == 1;
                        consumer4.accept(Boolean.valueOf(z4));
                        volumeDialogControllerImpl$$ExternalSyntheticLambda1.run();
                        broadcastReceiverManager.logWrapper.d("vol.BroadcastManager", "onReceive : " + action + " " + z4);
                    }
                };
                BroadcastDispatcher.registerReceiver$default(broadcastReceiverManager.broadcastDispatcher, broadcastReceiver2, broadcastReceiverItem2.intentFilter, null, null, 0, null, 60);
                broadcastReceiverItem2.receiver = broadcastReceiver2;
                Unit unit2 = Unit.INSTANCE;
            }
            final int i6 = 5;
            final Consumer consumer5 = new Consumer() { // from class: com.android.systemui.volume.VolumeDialogControllerImpl$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    int i52 = i6;
                    VolumeDialogControllerImpl volumeDialogControllerImpl3 = volumeDialogControllerImpl2;
                    switch (i52) {
                        case 0:
                            volumeDialogControllerImpl3.mIsSupportTvVolumeControl = (Boolean) obj;
                            break;
                        case 1:
                            Context context32 = volumeDialogControllerImpl3.mContext;
                            BluetoothIconServerUtils bluetoothIconServerUtils = BluetoothIconServerUtils.INSTANCE;
                            String str2 = ScspUtils.FILE_PATH_ROOT;
                            Executors.newSingleThreadExecutor().execute(new ScspUtils$$ExternalSyntheticLambda2((ArrayList) obj, context32, null, new Handler(Looper.getMainLooper())));
                            volumeDialogControllerImpl3.mCallbacks.onStateChanged(volumeDialogControllerImpl3.mState);
                            break;
                        case 2:
                            volumeDialogControllerImpl3.mIsDLNAEnabled = (Boolean) obj;
                            break;
                        case 3:
                            Boolean bool = (Boolean) obj;
                            if (volumeDialogControllerImpl3.streamStateW(20).muted != bool.booleanValue()) {
                                volumeDialogControllerImpl3.updateStreamMuteW(20, bool.booleanValue());
                            }
                            if (volumeDialogControllerImpl3.mIsVolumeDialogShowing) {
                                int i62 = volumeDialogControllerImpl3.mSmartViewFlag;
                                volumeDialogControllerImpl3.onVolumeChangedW(20, i62 != VolumeDialogControllerImpl.FLAG_SMART_VIEW_NONE ? i62 : 1);
                                if (volumeDialogControllerImpl3.isSmartViewEnabled()) {
                                    volumeDialogControllerImpl3.mCallbacks.onStateChanged(volumeDialogControllerImpl3.mState);
                                    break;
                                }
                            }
                            break;
                        case 4:
                            volumeDialogControllerImpl3.getClass();
                            volumeDialogControllerImpl3.mIsBudsTogetherEnabled = ((Boolean) obj).booleanValue();
                            break;
                        case 5:
                            volumeDialogControllerImpl3.getClass();
                            volumeDialogControllerImpl3.mIsMusicShareEnabled = ((Boolean) obj).booleanValue();
                            break;
                        default:
                            int i7 = volumeDialogControllerImpl3.mWakefulnessLifecycle.mWakefulness;
                            boolean z4 = i7 == 0 || i7 == 3 || !volumeDialogControllerImpl3.mDeviceInteractive;
                            volumeDialogControllerImpl3.mState.aodEnabled = z4;
                            if (z4) {
                                volumeDialogControllerImpl3.onVolumeChangedW(3, 1);
                                break;
                            }
                            break;
                    }
                }
            };
            final VolumeDialogControllerImpl$$ExternalSyntheticLambda1 volumeDialogControllerImpl$$ExternalSyntheticLambda12 = new VolumeDialogControllerImpl$$ExternalSyntheticLambda1(volumeDialogControllerImpl2, 3);
            BroadcastReceiverManager.BroadcastReceiverItem broadcastReceiverItem3 = (BroadcastReceiverManager.BroadcastReceiverItem) broadcastReceiverManager.broadcastReceiverItemMap.get(BroadcastReceiverType.MUSIC_SHARE);
            if (broadcastReceiverItem3 != null) {
                BroadcastReceiver broadcastReceiver3 = new BroadcastReceiver() { // from class: com.android.systemui.volume.util.BroadcastReceiverManager$registerMusicShareStateAction$1$1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context4, Intent intent3) {
                        String action = intent3.getAction();
                        if (Intrinsics.areEqual(action, "com.samsung.android.bluetooth.audiocast.action.device.CONNECTION_STATE_CHANGED")) {
                            boolean z4 = intent3.getIntExtra("com.samsung.android.bluetooth.cast.extra.STATE", 0) == 2;
                            consumer5.accept(Boolean.valueOf(z4));
                            volumeDialogControllerImpl$$ExternalSyntheticLambda12.run();
                            broadcastReceiverManager.logWrapper.d("vol.BroadcastManager", "onReceive : " + action + " " + z4);
                        }
                    }
                };
                BroadcastDispatcher.registerReceiver$default(broadcastReceiverManager.broadcastDispatcher, broadcastReceiver3, broadcastReceiverItem3.intentFilter, null, null, 0, null, 60);
                broadcastReceiverItem3.receiver = broadcastReceiver3;
                Unit unit3 = Unit.INSTANCE;
            }
            final int i7 = 6;
            final Consumer consumer6 = new Consumer() { // from class: com.android.systemui.volume.VolumeDialogControllerImpl$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    int i52 = i7;
                    VolumeDialogControllerImpl volumeDialogControllerImpl3 = volumeDialogControllerImpl2;
                    switch (i52) {
                        case 0:
                            volumeDialogControllerImpl3.mIsSupportTvVolumeControl = (Boolean) obj;
                            break;
                        case 1:
                            Context context32 = volumeDialogControllerImpl3.mContext;
                            BluetoothIconServerUtils bluetoothIconServerUtils = BluetoothIconServerUtils.INSTANCE;
                            String str2 = ScspUtils.FILE_PATH_ROOT;
                            Executors.newSingleThreadExecutor().execute(new ScspUtils$$ExternalSyntheticLambda2((ArrayList) obj, context32, null, new Handler(Looper.getMainLooper())));
                            volumeDialogControllerImpl3.mCallbacks.onStateChanged(volumeDialogControllerImpl3.mState);
                            break;
                        case 2:
                            volumeDialogControllerImpl3.mIsDLNAEnabled = (Boolean) obj;
                            break;
                        case 3:
                            Boolean bool = (Boolean) obj;
                            if (volumeDialogControllerImpl3.streamStateW(20).muted != bool.booleanValue()) {
                                volumeDialogControllerImpl3.updateStreamMuteW(20, bool.booleanValue());
                            }
                            if (volumeDialogControllerImpl3.mIsVolumeDialogShowing) {
                                int i62 = volumeDialogControllerImpl3.mSmartViewFlag;
                                volumeDialogControllerImpl3.onVolumeChangedW(20, i62 != VolumeDialogControllerImpl.FLAG_SMART_VIEW_NONE ? i62 : 1);
                                if (volumeDialogControllerImpl3.isSmartViewEnabled()) {
                                    volumeDialogControllerImpl3.mCallbacks.onStateChanged(volumeDialogControllerImpl3.mState);
                                    break;
                                }
                            }
                            break;
                        case 4:
                            volumeDialogControllerImpl3.getClass();
                            volumeDialogControllerImpl3.mIsBudsTogetherEnabled = ((Boolean) obj).booleanValue();
                            break;
                        case 5:
                            volumeDialogControllerImpl3.getClass();
                            volumeDialogControllerImpl3.mIsMusicShareEnabled = ((Boolean) obj).booleanValue();
                            break;
                        default:
                            int i72 = volumeDialogControllerImpl3.mWakefulnessLifecycle.mWakefulness;
                            boolean z4 = i72 == 0 || i72 == 3 || !volumeDialogControllerImpl3.mDeviceInteractive;
                            volumeDialogControllerImpl3.mState.aodEnabled = z4;
                            if (z4) {
                                volumeDialogControllerImpl3.onVolumeChangedW(3, 1);
                                break;
                            }
                            break;
                    }
                }
            };
            BroadcastReceiverManager.BroadcastReceiverItem broadcastReceiverItem4 = (BroadcastReceiverManager.BroadcastReceiverItem) broadcastReceiverManager.broadcastReceiverItemMap.get(BroadcastReceiverType.AOD);
            if (broadcastReceiverItem4 != null) {
                BroadcastReceiver broadcastReceiver4 = new BroadcastReceiver() { // from class: com.android.systemui.volume.util.BroadcastReceiverManager$registerAODShowAction$1$1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context4, Intent intent3) {
                        int intExtra;
                        String action = intent3.getAction();
                        if (action != null && action.hashCode() == -1119174317 && action.equals("com.samsung.android.app.aodservice.intent.action.CHANGE_AOD_MODE") && (intExtra = intent3.getIntExtra("info", -1)) == 18) {
                            consumer6.accept(Boolean.TRUE);
                            broadcastReceiverManager.logWrapper.d("vol.BroadcastManager", "onReceive : " + intExtra + ", long press on AOD state true");
                        }
                    }
                };
                BroadcastDispatcher.registerReceiver$default(broadcastReceiverManager.broadcastDispatcher, broadcastReceiver4, broadcastReceiverItem4.intentFilter, null, null, 0, null, 60);
                broadcastReceiverItem4.receiver = broadcastReceiver4;
                Unit unit4 = Unit.INSTANCE;
            }
            final Consumer consumer7 = new Consumer() { // from class: com.android.systemui.volume.VolumeDialogControllerImpl$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    int i52 = i4;
                    VolumeDialogControllerImpl volumeDialogControllerImpl3 = volumeDialogControllerImpl2;
                    switch (i52) {
                        case 0:
                            volumeDialogControllerImpl3.mIsSupportTvVolumeControl = (Boolean) obj;
                            break;
                        case 1:
                            Context context32 = volumeDialogControllerImpl3.mContext;
                            BluetoothIconServerUtils bluetoothIconServerUtils = BluetoothIconServerUtils.INSTANCE;
                            String str2 = ScspUtils.FILE_PATH_ROOT;
                            Executors.newSingleThreadExecutor().execute(new ScspUtils$$ExternalSyntheticLambda2((ArrayList) obj, context32, null, new Handler(Looper.getMainLooper())));
                            volumeDialogControllerImpl3.mCallbacks.onStateChanged(volumeDialogControllerImpl3.mState);
                            break;
                        case 2:
                            volumeDialogControllerImpl3.mIsDLNAEnabled = (Boolean) obj;
                            break;
                        case 3:
                            Boolean bool = (Boolean) obj;
                            if (volumeDialogControllerImpl3.streamStateW(20).muted != bool.booleanValue()) {
                                volumeDialogControllerImpl3.updateStreamMuteW(20, bool.booleanValue());
                            }
                            if (volumeDialogControllerImpl3.mIsVolumeDialogShowing) {
                                int i62 = volumeDialogControllerImpl3.mSmartViewFlag;
                                volumeDialogControllerImpl3.onVolumeChangedW(20, i62 != VolumeDialogControllerImpl.FLAG_SMART_VIEW_NONE ? i62 : 1);
                                if (volumeDialogControllerImpl3.isSmartViewEnabled()) {
                                    volumeDialogControllerImpl3.mCallbacks.onStateChanged(volumeDialogControllerImpl3.mState);
                                    break;
                                }
                            }
                            break;
                        case 4:
                            volumeDialogControllerImpl3.getClass();
                            volumeDialogControllerImpl3.mIsBudsTogetherEnabled = ((Boolean) obj).booleanValue();
                            break;
                        case 5:
                            volumeDialogControllerImpl3.getClass();
                            volumeDialogControllerImpl3.mIsMusicShareEnabled = ((Boolean) obj).booleanValue();
                            break;
                        default:
                            int i72 = volumeDialogControllerImpl3.mWakefulnessLifecycle.mWakefulness;
                            boolean z4 = i72 == 0 || i72 == 3 || !volumeDialogControllerImpl3.mDeviceInteractive;
                            volumeDialogControllerImpl3.mState.aodEnabled = z4;
                            if (z4) {
                                volumeDialogControllerImpl3.onVolumeChangedW(3, 1);
                                break;
                            }
                            break;
                    }
                }
            };
            BroadcastReceiverManager.BroadcastReceiverItem broadcastReceiverItem5 = (BroadcastReceiverManager.BroadcastReceiverItem) broadcastReceiverManager.broadcastReceiverItemMap.get(BroadcastReceiverType.BUDS_ICON_SERVER_CHANGE);
            if (broadcastReceiverItem5 != null) {
                BroadcastReceiver broadcastReceiver5 = new BroadcastReceiver() { // from class: com.android.systemui.volume.util.BroadcastReceiverManager$registerBudsIconServerChange$1$1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context4, Intent intent3) {
                        ArrayList parcelableArrayListExtra;
                        Log.d("vol.BroadcastManager", "action=" + intent3.getAction());
                        String action = intent3.getAction();
                        if (action == null || action.hashCode() != 1308295972 || !action.equals("com.samsung.bluetooth.adapter.action.RESOURCE_UPDATE_ALL") || (parcelableArrayListExtra = intent3.getParcelableArrayListExtra("com.samsung.bluetooth.adapter.extra.RESOURCE_ALL_URI")) == null) {
                            return;
                        }
                        consumer7.accept(parcelableArrayListExtra);
                    }
                };
                BroadcastDispatcher.registerReceiver$default(broadcastReceiverManager.broadcastDispatcher, broadcastReceiver5, broadcastReceiverItem5.intentFilter, null, null, 0, null, 60);
                broadcastReceiverItem5.receiver = broadcastReceiver5;
                Unit unit5 = Unit.INSTANCE;
            }
            final Context context4 = volumeDialogControllerImpl2.mContext;
            final VolumeDialogControllerImpl$$ExternalSyntheticLambda1 volumeDialogControllerImpl$$ExternalSyntheticLambda13 = new VolumeDialogControllerImpl$$ExternalSyntheticLambda1(volumeDialogControllerImpl2, 1);
            BluetoothIconServerUtils bluetoothIconServerUtils = BluetoothIconServerUtils.INSTANCE;
            Handler handler = new Handler(Looper.getMainLooper());
            Log.d("vol.BluetoothIconServerUtils", "init");
            handler.postDelayed(new Runnable() { // from class: com.android.systemui.volume.util.BluetoothIconServerUtils$init$1
                @Override // java.lang.Runnable
                public final void run() {
                    BluetoothIconServerUtils bluetoothIconServerUtils2 = BluetoothIconServerUtils.INSTANCE;
                    final Context context5 = context4;
                    final Runnable runnable = volumeDialogControllerImpl$$ExternalSyntheticLambda13;
                    bluetoothIconServerUtils2.getClass();
                    Executors.newSingleThreadExecutor().execute(new Runnable() { // from class: com.android.systemui.volume.util.BluetoothIconServerUtils$executeServerSyncInit$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            ScspUtils.removeOldDir(context5);
                            BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
                            if (defaultAdapter != null) {
                                Context context6 = context5;
                                Runnable runnable2 = runnable;
                                String semGetEtag = defaultAdapter.semGetEtag(context6.getPackageName(), null);
                                if (TextUtils.isEmpty(semGetEtag)) {
                                    Log.d("vol.BluetoothIconServerUtils", "init: etag is empty");
                                    return;
                                }
                                MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("init: etag = ", semGetEtag, "vol.BluetoothIconServerUtils");
                                boolean z4 = false;
                                SharedPreferences sharedPreferences = context6.getSharedPreferences("bluetooth_scsp_manager", 0);
                                String string = sharedPreferences.getString("etag", "");
                                Log.d("vol.BluetoothIconServerUtils", "init: sharedEtag = " + string);
                                if (Intrinsics.areEqual(semGetEtag, string)) {
                                    File file = new File(ScspUtils.getFileRootPath(context6));
                                    if (file.exists()) {
                                        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("init: etag is not updated rootDir: ", file.getPath(), "vol.BluetoothIconServerUtils");
                                        return;
                                    }
                                    Log.d("vol.BluetoothIconServerUtils", "init: dir is not exist. need update");
                                } else {
                                    SharedPreferences.Editor edit = sharedPreferences.edit();
                                    edit.putString("etag", semGetEtag);
                                    edit.apply();
                                }
                                List<Uri> semGetAllIconResourceUri = defaultAdapter.semGetAllIconResourceUri(context6.getPackageName());
                                List list = semGetAllIconResourceUri;
                                if (list == null || list.isEmpty()) {
                                    Log.d("vol.BluetoothIconServerUtils", "init: uriList is empty");
                                    return;
                                }
                                for (Uri uri : semGetAllIconResourceUri) {
                                    Log.d("vol.BluetoothIconServerUtils", "saveAllResources: uri = " + uri);
                                    z4 = ScspUtils.makeAllResourceData(context6, uri);
                                }
                                if (z4) {
                                    runnable2.run();
                                }
                            }
                        }
                    });
                }
            }, 5000L);
            Prefs.putBoolean(volumeDialogComponent.mContext, "DndTileCombinedIcon", true);
        }
    }
}
