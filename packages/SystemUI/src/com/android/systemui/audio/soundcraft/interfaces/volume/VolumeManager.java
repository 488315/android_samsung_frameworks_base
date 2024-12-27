package com.android.systemui.audio.soundcraft.interfaces.volume;

import android.content.Context;
import android.media.AudioManager;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.os.Trace;
import android.util.Log;
import com.android.keyguard.KeyguardFMMViewController$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.audio.soundcraft.model.common.VolumeModel;
import com.android.systemui.audio.soundcraft.utils.SystemServiceExtension;
import com.android.systemui.plugins.VolumeDialogController;
import com.android.systemui.volume.util.BluetoothAdapterWrapper;
import com.android.systemui.volume.util.DisplayManagerWrapper;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class VolumeManager {
    public final AudioManager audioManager;
    public final BluetoothAdapterWrapper bluetoothAdapterWrapper;
    public final List bluetoothDeviceList;
    public final VolumeManager$callback$1 callback;
    public final Context context;
    public final DisplayManagerWrapper displayManagerWrapper;
    public boolean isRemoteStreamPlaying;
    public boolean isTracking;
    public MediaController mediaController;
    public final ArrayList qpVolumeModelCallbacks;
    public int remoteMaxVolume;
    public int remoteMinVolume;
    public boolean remoteSpeakerConnected;
    public int remoteStream;
    public boolean remoteStreamEnabled;
    public int remoteVolumeLevel;
    public final Lazy volumeController$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.audio.soundcraft.interfaces.volume.VolumeManager$volumeController$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return (VolumeDialogController) Dependency.sDependency.getDependencyInner(VolumeDialogController.class);
        }
    });
    public VolumeModel volumeModel;
    public Function2 volumeModelCallback;
    public final List wiredEarphoneDeviceList;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r1v12, types: [com.android.systemui.audio.soundcraft.interfaces.volume.VolumeManager$callback$1] */
    public VolumeManager(Context context, BluetoothAdapterWrapper bluetoothAdapterWrapper, DisplayManagerWrapper displayManagerWrapper) {
        this.context = context;
        this.bluetoothAdapterWrapper = bluetoothAdapterWrapper;
        this.displayManagerWrapper = displayManagerWrapper;
        SystemServiceExtension.INSTANCE.getClass();
        this.audioManager = SystemServiceExtension.getAudioManager(context);
        this.volumeModel = new VolumeModel(0, 0, 0, 0, false, false, false, 0, false, false, false, false, 4095, null);
        this.qpVolumeModelCallbacks = new ArrayList(2);
        this.wiredEarphoneDeviceList = CollectionsKt__CollectionsKt.listOf(3, 4, 22);
        this.bluetoothDeviceList = CollectionsKt__CollectionsKt.listOf(8, 30, 26, 27);
        this.callback = new MediaController.Callback() { // from class: com.android.systemui.audio.soundcraft.interfaces.volume.VolumeManager$callback$1
            @Override // android.media.session.MediaController.Callback
            public final void onPlaybackStateChanged(PlaybackState playbackState) {
                Log.i("SoundCraft.VolumeManager", "onPlaybackStateChanged state = " + (playbackState != null ? Integer.valueOf(playbackState.getState()) : null));
                Integer valueOf = playbackState != null ? Integer.valueOf(playbackState.getState()) : null;
                if (VolumeManager.this.isRemoteStreamPlaying && valueOf != null && valueOf.intValue() == 3) {
                    return;
                }
                if (valueOf != null && valueOf.intValue() == 3) {
                    VolumeManager.this.isRemoteStreamPlaying = true;
                } else {
                    VolumeManager.this.isRemoteStreamPlaying = false;
                }
                VolumeManager.this.updateCurrentVolume();
            }
        };
    }

    public final int getDevice() {
        int i = this.volumeModel.device;
        if (i != 23) {
            BluetoothAdapterWrapper bluetoothAdapterWrapper = this.bluetoothAdapterWrapper;
            if (i != 26 || !(!bluetoothAdapterWrapper.getConnectedLeHearingAidDevice().isEmpty())) {
                if (this.bluetoothDeviceList.contains(Integer.valueOf(i)) || this.volumeModel.isMusicShareEnabled || (i == 30 && isAurCastEnabled() && (!bluetoothAdapterWrapper.getConnectedLeDevices().isEmpty()))) {
                    return 128;
                }
                return this.wiredEarphoneDeviceList.contains(Integer.valueOf(i)) ? QuickStepContract.SYSUI_STATE_REQUESTED_RECENT_KEY : ((i == 25 && this.volumeModel.isSmartViewEnabled) || (this.remoteStreamEnabled && this.isRemoteStreamPlaying)) ? 32768 : 0;
            }
        }
        return 134217728;
    }

    public final int getMaxVolume() {
        if (this.remoteStreamEnabled && this.isRemoteStreamPlaying) {
            return this.remoteMaxVolume;
        }
        VolumeModel volumeModel = this.volumeModel;
        return (volumeModel.device == 25 && volumeModel.isSmartViewEnabled) ? this.displayManagerWrapper.getDisplayMaxVolume() : volumeModel.maxVolume;
    }

    public final int getMinVolume() {
        if (this.remoteStreamEnabled && this.isRemoteStreamPlaying) {
            return this.remoteMinVolume;
        }
        VolumeModel volumeModel = this.volumeModel;
        if (volumeModel.device != 25 || !volumeModel.isSmartViewEnabled) {
            return volumeModel.minVolume;
        }
        DisplayManagerWrapper displayManagerWrapper = this.displayManagerWrapper;
        if (displayManagerWrapper.minSmartViewVol == -1) {
            com.android.systemui.volume.util.SystemServiceExtension systemServiceExtension = com.android.systemui.volume.util.SystemServiceExtension.INSTANCE;
            Context context = displayManagerWrapper.context;
            systemServiceExtension.getClass();
            displayManagerWrapper.minSmartViewVol = ((Integer) com.android.systemui.volume.util.SystemServiceExtension.getDisplayManager(context).semGetWifiDisplayConfiguration("mavo")).intValue();
        }
        return displayManagerWrapper.minSmartViewVol;
    }

    public final int getStreamVolume() {
        if (this.remoteStreamEnabled && this.isRemoteStreamPlaying) {
            return this.remoteVolumeLevel;
        }
        if (!isStreamEnabled() && !isAurCastEnabled()) {
            return 0;
        }
        VolumeModel volumeModel = this.volumeModel;
        return (volumeModel.device == 25 && volumeModel.isSmartViewEnabled) ? this.displayManagerWrapper.displayCurrentVolume : volumeModel.volume;
    }

    public final VolumeModel getVolumeModel() {
        if (!Trace.isTagEnabled(4096L)) {
            int streamVolume = getStreamVolume();
            int minVolume = getMinVolume();
            int maxVolume = getMaxVolume();
            int device = getDevice();
            boolean isStreamEnabled = isStreamEnabled();
            boolean isAurCastEnabled = isAurCastEnabled();
            VolumeModel volumeModel = this.volumeModel;
            return new VolumeModel(streamVolume, minVolume, maxVolume, device, isStreamEnabled, isAurCastEnabled, volumeModel.isAllSoundMute, volumeModel.isZenMode, volumeModel.isZenModeDisabled, volumeModel.isSmartViewEnabled, volumeModel.isMusicShareEnabled, volumeModel.isDisallowAdjustVolume);
        }
        Trace.traceBegin(4096L, "#soundCraft.VolumeManager_getVolumeModel");
        try {
            int streamVolume2 = getStreamVolume();
            int minVolume2 = getMinVolume();
            int maxVolume2 = getMaxVolume();
            int device2 = getDevice();
            boolean isStreamEnabled2 = isStreamEnabled();
            boolean isAurCastEnabled2 = isAurCastEnabled();
            VolumeModel volumeModel2 = this.volumeModel;
            return new VolumeModel(streamVolume2, minVolume2, maxVolume2, device2, isStreamEnabled2, isAurCastEnabled2, volumeModel2.isAllSoundMute, volumeModel2.isZenMode, volumeModel2.isZenModeDisabled, volumeModel2.isSmartViewEnabled, volumeModel2.isMusicShareEnabled, volumeModel2.isDisallowAdjustVolume);
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    public final boolean isAurCastEnabled() {
        VolumeModel volumeModel = this.volumeModel;
        return (volumeModel.isZenModeDisabled || volumeModel.isAllSoundMute || !volumeModel.isBroadcast) ? false : true;
    }

    public final boolean isStreamEnabled() {
        VolumeModel volumeModel = this.volumeModel;
        if (volumeModel.isAllSoundMute || volumeModel.isZenModeDisabled || volumeModel.isDisallowAdjustVolume) {
            return false;
        }
        return !isAurCastEnabled() || (this.bluetoothAdapterWrapper.getConnectedLeDevices().isEmpty() ^ true);
    }

    public final void updateCurrentVolume() {
        if (!Trace.isTagEnabled(4096L)) {
            if (this.isTracking) {
                return;
            }
            VolumeModel volumeModel = getVolumeModel();
            Function2 function2 = this.volumeModelCallback;
            if (function2 != null) {
                function2.invoke(volumeModel, Boolean.valueOf(this.isTracking));
            }
            ArrayList arrayList = this.qpVolumeModelCallbacks;
            if (arrayList != null) {
                ListIterator listIterator = arrayList.listIterator();
                while (listIterator.hasNext()) {
                    ((Function2) listIterator.next()).invoke(volumeModel, Boolean.valueOf(this.isTracking));
                }
                return;
            }
            return;
        }
        Trace.traceBegin(4096L, "#soundCraft.VolumeManager_updateCurrentVolume");
        try {
            if (!this.isTracking) {
                VolumeModel volumeModel2 = getVolumeModel();
                Function2 function22 = this.volumeModelCallback;
                if (function22 != null) {
                    function22.invoke(volumeModel2, Boolean.valueOf(this.isTracking));
                }
                ArrayList arrayList2 = this.qpVolumeModelCallbacks;
                if (arrayList2 != null) {
                    ListIterator listIterator2 = arrayList2.listIterator();
                    while (listIterator2.hasNext()) {
                        ((Function2) listIterator2.next()).invoke(volumeModel2, Boolean.valueOf(this.isTracking));
                    }
                }
            }
            Unit unit = Unit.INSTANCE;
            Trace.traceEnd(4096L);
        } catch (Throwable th) {
            Trace.traceEnd(4096L);
            throw th;
        }
    }

    public final void updateRemoteVolume(boolean z, int i, int i2, int i3, int i4, MediaSession.Token token, boolean z2) {
        StringBuilder m = KeyguardFMMViewController$$ExternalSyntheticOutline0.m("updateRemoteVolume enabled = ", i, " stream = ", z, " volume = ");
        m.append(i4);
        m.append(" remoteSpeaker = ");
        m.append(z2);
        Log.i("SoundCraft.VolumeManager", m.toString());
        if (this.remoteStreamEnabled != z) {
            VolumeManager$callback$1 volumeManager$callback$1 = this.callback;
            if (z) {
                MediaController mediaController = new MediaController(this.context, token);
                this.mediaController = mediaController;
                mediaController.registerCallback(volumeManager$callback$1);
            } else {
                MediaController mediaController2 = this.mediaController;
                if (mediaController2 != null) {
                    mediaController2.unregisterCallback(volumeManager$callback$1);
                }
                this.mediaController = null;
            }
        }
        this.remoteSpeakerConnected = z2;
        this.remoteStreamEnabled = z;
        this.remoteMinVolume = i2;
        this.remoteMaxVolume = i3;
        this.remoteVolumeLevel = i4;
        this.remoteStream = i;
        updateCurrentVolume();
    }
}
