package com.android.systemui.volume;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.text.TextUtils;
import android.util.Log;
import androidx.constraintlayout.widget.ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.CarrierTextManagerLogger$$ExternalSyntheticOutline0;
import com.android.settingslib.volume.data.repository.AudioRepository;
import com.android.settingslib.volume.data.repository.AudioRepositoryImpl;
import com.android.systemui.CoreStartable;
import com.android.systemui.Prefs;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.globalactions.presentation.features.FakeFeatures$$ExternalSyntheticOutline0;
import com.android.systemui.qs.tiles.DndTile;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.kotlin.JavaAdapter;
import com.android.systemui.volume.domain.interactor.AudioSharingInteractor;
import com.android.systemui.volume.shared.VolumeLogger;
import com.android.systemui.volume.util.BluetoothIconServerUtils;
import com.android.systemui.volume.util.BroadcastReceiverManager;
import com.android.systemui.volume.util.BroadcastReceiverType;
import com.android.systemui.volume.util.DisplayManagerWrapper;
import com.android.systemui.volume.util.DisplayManagerWrapper$registerDisplayVolumeListener$1;
import com.android.systemui.volume.util.SystemServiceExtension;
import com.samsung.android.settingslib.bluetooth.scsp.ScspUtils;
import com.sec.ims.IMSParameter;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class VolumeUI implements CoreStartable, ConfigurationController.ConfigurationListener {
    public static final boolean LOGD = Log.isLoggable("VolumeUI", 3);
    public final AudioRepository mAudioRepository;
    public final AudioSharingInteractor mAudioSharingInteractor;
    public final Context mContext;
    public boolean mEnabled;
    public final JavaAdapter mJavaAdapter;
    public final VolumeDialogComponent mVolumeComponent;
    public final VolumeLogger mVolumeLogger;

    public VolumeUI(Context context, VolumeDialogComponent volumeDialogComponent, AudioRepository audioRepository, AudioSharingInteractor audioSharingInteractor, JavaAdapter javaAdapter, VolumeLogger volumeLogger) {
        this.mContext = context;
        this.mVolumeComponent = volumeDialogComponent;
        this.mAudioRepository = audioRepository;
        this.mAudioSharingInteractor = audioSharingInteractor;
        this.mJavaAdapter = javaAdapter;
        this.mVolumeLogger = volumeLogger;
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
        final int i = 0;
        final int i2 = 1;
        AudioRepositoryImpl audioRepositoryImpl = (AudioRepositoryImpl) this.mAudioRepository;
        audioRepositoryImpl.getClass();
        try {
            audioRepositoryImpl.audioManager.setVolumeController(audioRepositoryImpl.volumeController);
        } catch (SecurityException e) {
            Log.wtf("AudioManager", "Unable to set the volume controller", e);
        }
        boolean z = this.mContext.getResources().getBoolean(R.bool.enable_volume_ui);
        boolean z2 = this.mContext.getResources().getBoolean(R.bool.enable_safety_warning);
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
            VolumeDialogControllerImpl volumeDialogControllerImpl2 = volumeDialogComponent.mController;
            volumeDialogControllerImpl2.setVolumeController();
            String str = VolumeDialogControllerImpl.TAG;
            if (D.BUG) {
                Log.d(str, "showDndTile");
            }
            Prefs.putBoolean(volumeDialogControllerImpl2.mContext, "DndTileVisible", true);
            try {
                volumeDialogControllerImpl2.mMediaSessions.init();
            } catch (SecurityException e2) {
                Log.w(str, "No access to media sessions", e2);
            }
            VolumeDialogControllerImpl$$ExternalSyntheticLambda3 volumeDialogControllerImpl$$ExternalSyntheticLambda3 = new VolumeDialogControllerImpl$$ExternalSyntheticLambda3(volumeDialogControllerImpl2, 2);
            AudioSharingInteractor audioSharingInteractor = volumeDialogControllerImpl2.mAudioSharingInteractor;
            Objects.requireNonNull(audioSharingInteractor);
            volumeDialogControllerImpl2.mJavaAdapter.callSuspend(new VolumeDialogControllerImpl$$ExternalSyntheticLambda1(audioSharingInteractor), volumeDialogControllerImpl2.mContext, new VolumeDialogControllerImpl$$ExternalSyntheticLambda3(volumeDialogControllerImpl2, 1), volumeDialogControllerImpl$$ExternalSyntheticLambda3, volumeDialogControllerImpl$$ExternalSyntheticLambda3);
            final VolumeDialogControllerImpl$$ExternalSyntheticLambda9 volumeDialogControllerImpl$$ExternalSyntheticLambda9 = new VolumeDialogControllerImpl$$ExternalSyntheticLambda9(volumeDialogControllerImpl2, 2);
            final VolumeDialogControllerImpl$$ExternalSyntheticLambda9 volumeDialogControllerImpl$$ExternalSyntheticLambda92 = new VolumeDialogControllerImpl$$ExternalSyntheticLambda9(volumeDialogControllerImpl2, 3);
            final BroadcastReceiverManager broadcastReceiverManager = volumeDialogControllerImpl2.mBroadcastReceiverManager;
            BroadcastReceiverManager.BroadcastReceiverItem broadcastReceiverItem = (BroadcastReceiverManager.BroadcastReceiverItem) broadcastReceiverManager.broadcastReceiverItemMap.get(BroadcastReceiverType.DISPLAY_MANAGER);
            if (broadcastReceiverItem != null) {
                BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.volume.util.BroadcastReceiverManager$registerDisplayManagerStateAction$1$1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent2) {
                        String action = intent2.getAction();
                        if (action != null) {
                            int hashCode = action.hashCode();
                            if (hashCode != -1061859923) {
                                if (hashCode != 1735215423) {
                                    if (hashCode == 1886075268 && action.equals("com.samsung.intent.action.DLNA_STATUS_CHANGED")) {
                                        boolean z4 = intent2.getIntExtra(IMSParameter.CALL.STATUS, 0) == 1;
                                        volumeDialogControllerImpl$$ExternalSyntheticLambda92.accept(Boolean.valueOf(z4));
                                        broadcastReceiverManager.logWrapper.d("vol.BroadcastManager", FakeFeatures$$ExternalSyntheticOutline0.m("onReceive : SmartView action=", intent2.getAction(), ", dlnaEnabled=", z4));
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
                            int intExtra = intent2.getIntExtra("state", 0);
                            boolean booleanExtra = intent2.getBooleanExtra("isSupportDisplayVolumeControl", false);
                            volumeDialogControllerImpl$$ExternalSyntheticLambda9.accept(Boolean.valueOf(intExtra == 1 && booleanExtra));
                            broadcastReceiverManager.logWrapper.d("vol.BroadcastManager", KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m888m(intExtra, "onReceive : SmartView action=", intent2.getAction(), ", state=", ", isSupportDisplayVolumeControl="), booleanExtra, ", ret=", intExtra == 1 && booleanExtra));
                        }
                    }
                };
                BroadcastDispatcher.registerReceiver$default(broadcastReceiverManager.broadcastDispatcher, broadcastReceiver, broadcastReceiverItem.intentFilter, null, null, 0, null, 60);
                broadcastReceiverItem.receiver = broadcastReceiver;
                Unit unit = Unit.INSTANCE;
            }
            VolumeDialogControllerImpl$$ExternalSyntheticLambda9 volumeDialogControllerImpl$$ExternalSyntheticLambda93 = new VolumeDialogControllerImpl$$ExternalSyntheticLambda9(volumeDialogControllerImpl2, 4);
            DisplayManagerWrapper displayManagerWrapper = volumeDialogControllerImpl2.mDisplayManagerWrapper;
            displayManagerWrapper.getClass();
            DisplayManagerWrapper$registerDisplayVolumeListener$1 displayManagerWrapper$registerDisplayVolumeListener$1 = new DisplayManagerWrapper$registerDisplayVolumeListener$1(displayManagerWrapper, volumeDialogControllerImpl$$ExternalSyntheticLambda93);
            SystemServiceExtension systemServiceExtension = SystemServiceExtension.INSTANCE;
            Context context2 = displayManagerWrapper.context;
            systemServiceExtension.getClass();
            SystemServiceExtension.getDisplayManager(context2).semRegisterDisplayVolumeListener(displayManagerWrapper$registerDisplayVolumeListener$1, new Handler());
            displayManagerWrapper.displayVolumeListener = displayManagerWrapper$registerDisplayVolumeListener$1;
            final VolumeDialogControllerImpl$$ExternalSyntheticLambda9 volumeDialogControllerImpl$$ExternalSyntheticLambda94 = new VolumeDialogControllerImpl$$ExternalSyntheticLambda9(volumeDialogControllerImpl2, 5);
            final VolumeDialogControllerImpl$$ExternalSyntheticLambda6 volumeDialogControllerImpl$$ExternalSyntheticLambda6 = new VolumeDialogControllerImpl$$ExternalSyntheticLambda6(volumeDialogControllerImpl2, 2);
            BroadcastReceiverManager.BroadcastReceiverItem broadcastReceiverItem2 = (BroadcastReceiverManager.BroadcastReceiverItem) broadcastReceiverManager.broadcastReceiverItemMap.get(BroadcastReceiverType.BUDS_TOGETHER);
            if (broadcastReceiverItem2 != null) {
                BroadcastReceiver broadcastReceiver2 = new BroadcastReceiver() { // from class: com.android.systemui.volume.util.BroadcastReceiverManager$registerAudioSharingStateAction$1$1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context3, Intent intent2) {
                        String action = intent2.getAction();
                        if (Intrinsics.areEqual(action, "com.samsung.android.bluetooth.audiocast.action.device.AUDIO_SHARING_MODE_CHANGED")) {
                            boolean z4 = intent2.getIntExtra("com.samsung.android.bluetooth.cast.extra.AUDIO_SHARING_MODE", 0) == 1;
                            volumeDialogControllerImpl$$ExternalSyntheticLambda94.accept(Boolean.valueOf(z4));
                            volumeDialogControllerImpl$$ExternalSyntheticLambda6.run();
                            broadcastReceiverManager.logWrapper.d("vol.BroadcastManager", FakeFeatures$$ExternalSyntheticOutline0.m("onReceive : ", action, " ", z4));
                            return;
                        }
                        if (Intrinsics.areEqual(action, "com.samsung.android.bluetooth.audiocast.action.device.AUDIO_SHARING_DEVICE_VOLUME_CHANGED")) {
                            int intExtra = intent2.getIntExtra("com.samsung.android.bluetooth.cast.extra.AUDIO_SHARING_DEVICE_VOLUME", 0);
                            volumeDialogControllerImpl$$ExternalSyntheticLambda6.run();
                            broadcastReceiverManager.logWrapper.d("vol.BroadcastManager", CarrierTextManagerLogger$$ExternalSyntheticOutline0.m(intExtra, "onReceive : ", action, " "));
                        }
                    }
                };
                BroadcastDispatcher.registerReceiver$default(broadcastReceiverManager.broadcastDispatcher, broadcastReceiver2, broadcastReceiverItem2.intentFilter, null, null, 0, null, 60);
                broadcastReceiverItem2.receiver = broadcastReceiver2;
                Unit unit2 = Unit.INSTANCE;
            }
            final VolumeDialogControllerImpl$$ExternalSyntheticLambda9 volumeDialogControllerImpl$$ExternalSyntheticLambda95 = new VolumeDialogControllerImpl$$ExternalSyntheticLambda9(volumeDialogControllerImpl2, 6);
            final VolumeDialogControllerImpl$$ExternalSyntheticLambda6 volumeDialogControllerImpl$$ExternalSyntheticLambda62 = new VolumeDialogControllerImpl$$ExternalSyntheticLambda6(volumeDialogControllerImpl2, 3);
            BroadcastReceiverManager.BroadcastReceiverItem broadcastReceiverItem3 = (BroadcastReceiverManager.BroadcastReceiverItem) broadcastReceiverManager.broadcastReceiverItemMap.get(BroadcastReceiverType.MUSIC_SHARE);
            if (broadcastReceiverItem3 != null) {
                BroadcastReceiver broadcastReceiver3 = new BroadcastReceiver() { // from class: com.android.systemui.volume.util.BroadcastReceiverManager$registerMusicShareStateAction$1$1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context3, Intent intent2) {
                        String action = intent2.getAction();
                        if (Intrinsics.areEqual(action, "com.samsung.android.bluetooth.audiocast.action.device.CONNECTION_STATE_CHANGED")) {
                            boolean z4 = intent2.getIntExtra("com.samsung.android.bluetooth.cast.extra.STATE", 0) == 2;
                            volumeDialogControllerImpl$$ExternalSyntheticLambda95.accept(Boolean.valueOf(z4));
                            volumeDialogControllerImpl$$ExternalSyntheticLambda62.run();
                            broadcastReceiverManager.logWrapper.d("vol.BroadcastManager", FakeFeatures$$ExternalSyntheticOutline0.m("onReceive : ", action, " ", z4));
                        }
                    }
                };
                BroadcastDispatcher.registerReceiver$default(broadcastReceiverManager.broadcastDispatcher, broadcastReceiver3, broadcastReceiverItem3.intentFilter, null, null, 0, null, 60);
                broadcastReceiverItem3.receiver = broadcastReceiver3;
                Unit unit3 = Unit.INSTANCE;
            }
            final VolumeDialogControllerImpl$$ExternalSyntheticLambda9 volumeDialogControllerImpl$$ExternalSyntheticLambda96 = new VolumeDialogControllerImpl$$ExternalSyntheticLambda9(volumeDialogControllerImpl2, 0);
            BroadcastReceiverManager.BroadcastReceiverItem broadcastReceiverItem4 = (BroadcastReceiverManager.BroadcastReceiverItem) broadcastReceiverManager.broadcastReceiverItemMap.get(BroadcastReceiverType.AOD);
            if (broadcastReceiverItem4 != null) {
                BroadcastReceiver broadcastReceiver4 = new BroadcastReceiver() { // from class: com.android.systemui.volume.util.BroadcastReceiverManager$registerAODShowAction$1$1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context3, Intent intent2) {
                        int intExtra;
                        String action = intent2.getAction();
                        if (action != null && action.hashCode() == -1119174317 && action.equals("com.samsung.android.app.aodservice.intent.action.CHANGE_AOD_MODE") && (intExtra = intent2.getIntExtra("info", -1)) == 18) {
                            volumeDialogControllerImpl$$ExternalSyntheticLambda96.accept(Boolean.TRUE);
                            broadcastReceiverManager.logWrapper.d("vol.BroadcastManager", "onReceive : " + intExtra + ", long press on AOD state true");
                        }
                    }
                };
                BroadcastDispatcher.registerReceiver$default(broadcastReceiverManager.broadcastDispatcher, broadcastReceiver4, broadcastReceiverItem4.intentFilter, null, null, 0, null, 60);
                broadcastReceiverItem4.receiver = broadcastReceiver4;
                Unit unit4 = Unit.INSTANCE;
            }
            final VolumeDialogControllerImpl$$ExternalSyntheticLambda9 volumeDialogControllerImpl$$ExternalSyntheticLambda97 = new VolumeDialogControllerImpl$$ExternalSyntheticLambda9(volumeDialogControllerImpl2, 1);
            BroadcastReceiverManager.BroadcastReceiverItem broadcastReceiverItem5 = (BroadcastReceiverManager.BroadcastReceiverItem) broadcastReceiverManager.broadcastReceiverItemMap.get(BroadcastReceiverType.BUDS_ICON_SERVER_CHANGE);
            if (broadcastReceiverItem5 != null) {
                BroadcastReceiver broadcastReceiver5 = new BroadcastReceiver() { // from class: com.android.systemui.volume.util.BroadcastReceiverManager$registerBudsIconServerChange$1$1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context3, Intent intent2) {
                        ArrayList parcelableArrayListExtra;
                        Log.d("vol.BroadcastManager", "action=" + intent2.getAction());
                        String action = intent2.getAction();
                        if (action == null || action.hashCode() != 1308295972 || !action.equals("com.samsung.bluetooth.adapter.action.RESOURCE_UPDATE_ALL") || (parcelableArrayListExtra = intent2.getParcelableArrayListExtra("com.samsung.bluetooth.adapter.extra.RESOURCE_ALL_URI")) == null) {
                            return;
                        }
                        volumeDialogControllerImpl$$ExternalSyntheticLambda97.accept(parcelableArrayListExtra);
                    }
                };
                BroadcastDispatcher.registerReceiver$default(broadcastReceiverManager.broadcastDispatcher, broadcastReceiver5, broadcastReceiverItem5.intentFilter, null, null, 0, null, 60);
                broadcastReceiverItem5.receiver = broadcastReceiver5;
                Unit unit5 = Unit.INSTANCE;
            }
            final Context context3 = volumeDialogControllerImpl2.mContext;
            final VolumeDialogControllerImpl$$ExternalSyntheticLambda6 volumeDialogControllerImpl$$ExternalSyntheticLambda63 = new VolumeDialogControllerImpl$$ExternalSyntheticLambda6(volumeDialogControllerImpl2, 1);
            BluetoothIconServerUtils bluetoothIconServerUtils = BluetoothIconServerUtils.INSTANCE;
            Handler handler = new Handler(Looper.getMainLooper());
            Log.d("vol.BluetoothIconServerUtils", "init");
            handler.postDelayed(new Runnable() { // from class: com.android.systemui.volume.util.BluetoothIconServerUtils$init$1
                @Override // java.lang.Runnable
                public final void run() {
                    BluetoothIconServerUtils bluetoothIconServerUtils2 = BluetoothIconServerUtils.INSTANCE;
                    final Context context4 = context3;
                    final Runnable runnable = volumeDialogControllerImpl$$ExternalSyntheticLambda63;
                    bluetoothIconServerUtils2.getClass();
                    Executors.newSingleThreadExecutor().execute(new Runnable() { // from class: com.android.systemui.volume.util.BluetoothIconServerUtils$executeServerSyncInit$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            ScspUtils.removeOldDir(context4);
                            BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
                            if (defaultAdapter != null) {
                                Context context5 = context4;
                                Runnable runnable2 = runnable;
                                String semGetEtag = defaultAdapter.semGetEtag(context5.getPackageName(), null);
                                if (TextUtils.isEmpty(semGetEtag)) {
                                    Log.d("vol.BluetoothIconServerUtils", "init: etag is empty");
                                    return;
                                }
                                MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("init: etag = ", semGetEtag, "vol.BluetoothIconServerUtils");
                                boolean z4 = false;
                                SharedPreferences sharedPreferences = context5.getSharedPreferences("bluetooth_scsp_manager", 0);
                                String string = sharedPreferences.getString("etag", "");
                                Log.d("vol.BluetoothIconServerUtils", "init: sharedEtag = " + string);
                                if (Intrinsics.areEqual(semGetEtag, string)) {
                                    File file = new File(ScspUtils.getFileRootPath(context5));
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
                                List<Uri> semGetAllIconResourceUri = defaultAdapter.semGetAllIconResourceUri(context5.getPackageName());
                                List list = semGetAllIconResourceUri;
                                if (list == null || list.isEmpty()) {
                                    Log.d("vol.BluetoothIconServerUtils", "init: uriList is empty");
                                    return;
                                }
                                for (Uri uri : semGetAllIconResourceUri) {
                                    Log.d("vol.BluetoothIconServerUtils", "saveAllResources: uri = " + uri);
                                    z4 = ScspUtils.makeAllResourceData(context5, uri);
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
            Function1 function1 = new Function1(this) { // from class: com.android.systemui.volume.VolumeUI$$ExternalSyntheticLambda0
                public final /* synthetic */ VolumeUI f$0;

                {
                    this.f$0 = this;
                }

                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj) {
                    VolumeUI volumeUI = this.f$0;
                    switch (i) {
                        case 0:
                            boolean z4 = VolumeUI.LOGD;
                            volumeUI.getClass();
                            volumeUI.mVolumeLogger.onAudioSharingAvailabilityRequestedError("start()", ((Throwable) obj).getMessage());
                            break;
                        default:
                            boolean z5 = VolumeUI.LOGD;
                            volumeUI.getClass();
                            if (((Boolean) obj).booleanValue()) {
                                volumeUI.mAudioSharingInteractor.handlePrimaryGroupChange();
                                break;
                            }
                            break;
                    }
                    return null;
                }
            };
            AudioSharingInteractor audioSharingInteractor2 = this.mAudioSharingInteractor;
            Objects.requireNonNull(audioSharingInteractor2);
            this.mJavaAdapter.callSuspend(new VolumeDialogControllerImpl$$ExternalSyntheticLambda1(audioSharingInteractor2), this.mContext, new Function1(this) { // from class: com.android.systemui.volume.VolumeUI$$ExternalSyntheticLambda0
                public final /* synthetic */ VolumeUI f$0;

                {
                    this.f$0 = this;
                }

                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj) {
                    VolumeUI volumeUI = this.f$0;
                    switch (i2) {
                        case 0:
                            boolean z4 = VolumeUI.LOGD;
                            volumeUI.getClass();
                            volumeUI.mVolumeLogger.onAudioSharingAvailabilityRequestedError("start()", ((Throwable) obj).getMessage());
                            break;
                        default:
                            boolean z5 = VolumeUI.LOGD;
                            volumeUI.getClass();
                            if (((Boolean) obj).booleanValue()) {
                                volumeUI.mAudioSharingInteractor.handlePrimaryGroupChange();
                                break;
                            }
                            break;
                    }
                    return null;
                }
            }, function1, function1);
        }
    }
}
