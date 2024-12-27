package com.android.systemui.audio.soundcraft.viewmodel;

import android.content.Context;
import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import com.android.systemui.audio.soundcraft.interfaces.audio.AudioPlaybackManager;
import com.android.systemui.audio.soundcraft.interfaces.connectivity.BluetoothDeviceManager;
import com.android.systemui.audio.soundcraft.interfaces.connectivity.SoundCraftManager;
import com.android.systemui.audio.soundcraft.interfaces.routine.manager.RoutineManager;
import com.android.systemui.audio.soundcraft.interfaces.settings.SoundCraftSettings;
import com.android.systemui.audio.soundcraft.interfaces.soundalive.SoundAliveManager;
import com.android.systemui.audio.soundcraft.interfaces.volume.VolumeManager;
import com.android.systemui.audio.soundcraft.interfaces.wearable.WearableManager;
import com.android.systemui.audio.soundcraft.model.EffectOutDeviceType;
import com.android.systemui.audio.soundcraft.model.ModelProvider;
import com.android.systemui.audio.soundcraft.model.buds.NoiseControl;
import com.android.systemui.audio.soundcraft.viewmodel.common.base.BaseViewModel;
import com.android.systemui.qs.bar.ColoredBGHelper;
import com.samsung.android.sdk.routines.automationservice.interfaces.AutomationService;
import com.samsung.android.sdk.routines.automationservice.internal.AutomationServiceImpl;
import com.samsung.android.sdk.routines.automationservice.internal.ContentHandlerImpl;
import com.samsung.android.sdk.routines.automationservice.internal.ContentHandlerImpl$register$1;
import java.util.Iterator;
import java.util.Set;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class SoundCraftViewModel extends BaseViewModel {
    public final AudioPlaybackManager audioPlaybackManager;
    public final BluetoothDeviceManager bluetoothDeviceManager;
    public final ColoredBGHelper coloredBGHelper;
    public final Context context;
    public final ModelProvider modelProvider;
    public final RoutineManager routineManager;
    public final SoundCraftSettings settings;
    public final SoundAliveManager soundAliveManager;
    public final SoundCraftManager soundCraftManager;
    public final VolumeManager volumeManager;
    public final WearableManager wearableManager;
    public final MutableLiveData isBatteryInfoBoxVisible = new MutableLiveData();
    public final MutableLiveData isNoiseControlBoxVisible = new MutableLiveData();
    public final MutableLiveData isActionBarVisible = new MutableLiveData();
    public final MutableLiveData isFromNowBar = new MutableLiveData();
    public final MutableLiveData updateBatteryInfoBox = new MutableLiveData();
    public final MutableLiveData updateEffectBox = new MutableLiveData();
    public final MutableLiveData updateNoiseControlBox = new MutableLiveData();
    public final MutableLiveData updateVolumeBar = new MutableLiveData();

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

    public SoundCraftViewModel(Context context, WearableManager wearableManager, RoutineManager routineManager, BluetoothDeviceManager bluetoothDeviceManager, ModelProvider modelProvider, SoundCraftSettings soundCraftSettings, AudioPlaybackManager audioPlaybackManager, SoundAliveManager soundAliveManager, VolumeManager volumeManager, SoundCraftManager soundCraftManager, ColoredBGHelper coloredBGHelper) {
        this.context = context;
        this.wearableManager = wearableManager;
        this.routineManager = routineManager;
        this.bluetoothDeviceManager = bluetoothDeviceManager;
        this.modelProvider = modelProvider;
        this.settings = soundCraftSettings;
        this.audioPlaybackManager = audioPlaybackManager;
        this.soundAliveManager = soundAliveManager;
        this.volumeManager = volumeManager;
        this.soundCraftManager = soundCraftManager;
        this.coloredBGHelper = coloredBGHelper;
    }

    /* JADX WARN: Code restructure failed: missing block: B:147:0x070f, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:148:0x0714, code lost:
    
        r2 = kotlin.Result.$r8$clinit;
        r0 = kotlin.Result.m2527exceptionOrNullimpl(new kotlin.Result.Failure(r0));
     */
    /* JADX WARN: Code restructure failed: missing block: B:149:0x071f, code lost:
    
        if (r0 != null) goto L322;
     */
    /* JADX WARN: Code restructure failed: missing block: B:150:0x0721, code lost:
    
        r0.printStackTrace();
     */
    /* JADX WARN: Code restructure failed: missing block: B:151:0x06ce, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:152:0x06d3, code lost:
    
        r2 = kotlin.Result.$r8$clinit;
        r0 = kotlin.Result.m2527exceptionOrNullimpl(new kotlin.Result.Failure(r0));
     */
    /* JADX WARN: Code restructure failed: missing block: B:153:0x06de, code lost:
    
        if (r0 != null) goto L311;
     */
    /* JADX WARN: Code restructure failed: missing block: B:154:0x06e0, code lost:
    
        r0.printStackTrace();
     */
    /* JADX WARN: Code restructure failed: missing block: B:156:0x0645, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:158:0x0650, code lost:
    
        r5 = kotlin.Result.$r8$clinit;
        r0 = kotlin.Result.m2527exceptionOrNullimpl(new kotlin.Result.Failure(r0));
     */
    /* JADX WARN: Code restructure failed: missing block: B:159:0x065b, code lost:
    
        if (r0 != null) goto L288;
     */
    /* JADX WARN: Code restructure failed: missing block: B:160:0x065d, code lost:
    
        r0.printStackTrace();
     */
    /* JADX WARN: Code restructure failed: missing block: B:165:0x05f0, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:167:0x05fb, code lost:
    
        r5 = kotlin.Result.$r8$clinit;
        r0 = kotlin.Result.m2527exceptionOrNullimpl(new kotlin.Result.Failure(r0));
     */
    /* JADX WARN: Code restructure failed: missing block: B:168:0x0606, code lost:
    
        if (r0 != null) goto L269;
     */
    /* JADX WARN: Code restructure failed: missing block: B:169:0x0608, code lost:
    
        r0.printStackTrace();
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x03d7, code lost:
    
        if (r1 != false) goto L196;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.systemui.audio.soundcraft.model.common.EffectModel getAppRoutineModel() {
        /*
            Method dump skipped, instructions count: 1905
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.audio.soundcraft.viewmodel.SoundCraftViewModel.getAppRoutineModel():com.android.systemui.audio.soundcraft.model.common.EffectModel");
    }

    public final boolean hasNoiseControl() {
        Set noiseControlsList = this.modelProvider.budsModel.getNoiseControlsList();
        boolean z = false;
        if (noiseControlsList != null) {
            Iterator it = noiseControlsList.iterator();
            while (it.hasNext()) {
                String name = ((NoiseControl) it.next()).getName();
                BluetoothDeviceManager bluetoothDeviceManager = this.bluetoothDeviceManager;
                if (Intrinsics.areEqual(name, bluetoothDeviceManager.getActiveNoiseControlTitle()) || Intrinsics.areEqual(name, bluetoothDeviceManager.getAmbientSoundTitle()) || Intrinsics.areEqual(name, bluetoothDeviceManager.getAdaptiveTitle())) {
                    z = true;
                }
            }
        }
        return z;
    }

    @Override // com.android.systemui.audio.soundcraft.viewmodel.common.base.BaseViewModel
    public final void notifyChange() {
        ModelProvider modelProvider = this.modelProvider;
        boolean z = false;
        boolean z2 = modelProvider.effectOutDeviceType == EffectOutDeviceType.BUDS;
        this.isActionBarVisible.setValue(Boolean.valueOf(z2));
        this.isBatteryInfoBoxVisible.setValue(Boolean.valueOf(z2));
        MutableLiveData mutableLiveData = this.isNoiseControlBoxVisible;
        if (z2 && hasNoiseControl()) {
            z = true;
        }
        mutableLiveData.setValue(Boolean.valueOf(z));
        this.isFromNowBar.setValue(Boolean.valueOf(modelProvider.isFromNowBar));
        this.updateEffectBox.setValue(Boolean.TRUE);
    }

    @Override // androidx.lifecycle.ViewModel
    public final void onCleared() {
        Log.d("SoundCraft.SoundCraftViewModel", "onCleared");
        RoutineManager routineManager = this.routineManager;
        AutomationService service = routineManager.getService();
        Context context = routineManager.context;
        routineManager.getCurrentSystemRoutineType();
        ContentHandlerImpl$register$1 contentHandlerImpl$register$1 = ((ContentHandlerImpl) ((AutomationServiceImpl) service).contentHandler).contentObserver;
        if (contentHandlerImpl$register$1 != null) {
            context.getContentResolver().unregisterContentObserver(contentHandlerImpl$register$1);
        }
        BluetoothDeviceManager bluetoothDeviceManager = this.bluetoothDeviceManager;
        if (bluetoothDeviceManager.isRegister) {
            try {
                bluetoothDeviceManager.context.unregisterReceiver(bluetoothDeviceManager.bluetoothMetadataBroadcastReceiver);
            } catch (IllegalArgumentException unused) {
            }
            bluetoothDeviceManager.isRegister = false;
        }
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateModel() {
        /*
            Method dump skipped, instructions count: 866
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.audio.soundcraft.viewmodel.SoundCraftViewModel.updateModel():void");
    }
}
