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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    /* JADX WARN: Can't wrap try/catch for region: R(31:91|(2:92|93)|(29:95|(1:97)(1:170)|98|100|101|(24:103|(1:105)(1:163)|106|(1:108)(1:161)|109|110|111|(3:113|(1:115)(1:117)|116)|118|(1:120)(1:155)|121|(4:124|(2:126|127)(2:129|130)|128|122)|131|132|133|134|(1:136)|138|139|140|141|(1:143)|145|146)|164|106|(0)(0)|109|110|111|(0)|118|(0)(0)|121|(1:122)|131|132|133|134|(0)|138|139|140|141|(0)|145|146)|171|98|100|101|(0)|164|106|(0)(0)|109|110|111|(0)|118|(0)(0)|121|(1:122)|131|132|133|134|(0)|138|139|140|141|(0)|145|146) */
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
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:103:0x05d0 A[Catch: all -> 0x05f0, TryCatch #12 {all -> 0x05f0, blocks: (B:101:0x05c5, B:103:0x05d0, B:105:0x05e5, B:163:0x05f2), top: B:100:0x05c5 }] */
    /* JADX WARN: Removed duplicated region for block: B:108:0x0612  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x0624 A[Catch: all -> 0x0645, TryCatch #5 {all -> 0x0645, blocks: (B:111:0x0619, B:113:0x0624, B:115:0x0639, B:117:0x0647), top: B:110:0x0619 }] */
    /* JADX WARN: Removed duplicated region for block: B:120:0x0667  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x0681  */
    /* JADX WARN: Removed duplicated region for block: B:136:0x06b4 A[Catch: all -> 0x06ce, TRY_LEAVE, TryCatch #10 {all -> 0x06ce, blocks: (B:134:0x06a7, B:136:0x06b4), top: B:133:0x06a7 }] */
    /* JADX WARN: Removed duplicated region for block: B:143:0x06f5 A[Catch: all -> 0x070f, TRY_LEAVE, TryCatch #11 {all -> 0x070f, blocks: (B:141:0x06ea, B:143:0x06f5), top: B:140:0x06ea }] */
    /* JADX WARN: Removed duplicated region for block: B:155:0x0669  */
    /* JADX WARN: Removed duplicated region for block: B:161:0x0614  */
    /* JADX WARN: Removed duplicated region for block: B:178:0x03b9  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0373  */
    /* JADX WARN: Removed duplicated region for block: B:191:0x01da  */
    /* JADX WARN: Removed duplicated region for block: B:206:0x032f  */
    /* JADX WARN: Removed duplicated region for block: B:281:0x035f  */
    /* JADX WARN: Removed duplicated region for block: B:283:0x0362  */
    /* JADX WARN: Type inference failed for: r11v0, types: [com.android.systemui.audio.soundcraft.model.ModelProvider] */
    /* JADX WARN: Type inference failed for: r11v23, types: [com.samsung.android.sdk.routines.automationservice.data.ConditionStatus$Companion, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r11v25 */
    /* JADX WARN: Type inference failed for: r11v26 */
    /* JADX WARN: Type inference failed for: r11v28 */
    /* JADX WARN: Type inference failed for: r11v30, types: [java.lang.Object, kotlin.collections.AbstractList] */
    /* JADX WARN: Type inference failed for: r11v32 */
    /* JADX WARN: Type inference failed for: r11v33, types: [com.samsung.android.sdk.routines.automationservice.interfaces.AutomationService$SystemRoutineType] */
    /* JADX WARN: Type inference failed for: r11v4 */
    /* JADX WARN: Type inference failed for: r11v62 */
    /* JADX WARN: Type inference failed for: r16v10, types: [android.content.ContentResolver] */
    /* JADX WARN: Type inference failed for: r16v11 */
    /* JADX WARN: Type inference failed for: r16v12 */
    /* JADX WARN: Type inference failed for: r16v13 */
    /* JADX WARN: Type inference failed for: r16v14 */
    /* JADX WARN: Type inference failed for: r16v15 */
    /* JADX WARN: Type inference failed for: r16v16 */
    /* JADX WARN: Type inference failed for: r16v20 */
    /* JADX WARN: Type inference failed for: r16v5 */
    /* JADX WARN: Type inference failed for: r16v6 */
    /* JADX WARN: Type inference failed for: r16v7 */
    /* JADX WARN: Type inference failed for: r17v10 */
    /* JADX WARN: Type inference failed for: r17v11 */
    /* JADX WARN: Type inference failed for: r17v12 */
    /* JADX WARN: Type inference failed for: r17v13 */
    /* JADX WARN: Type inference failed for: r17v14 */
    /* JADX WARN: Type inference failed for: r17v18 */
    /* JADX WARN: Type inference failed for: r17v19 */
    /* JADX WARN: Type inference failed for: r17v20 */
    /* JADX WARN: Type inference failed for: r17v3 */
    /* JADX WARN: Type inference failed for: r17v4 */
    /* JADX WARN: Type inference failed for: r17v5 */
    /* JADX WARN: Type inference failed for: r17v8, types: [android.net.Uri] */
    /* JADX WARN: Type inference failed for: r17v9 */
    /* JADX WARN: Type inference failed for: r33v0, types: [com.android.systemui.audio.soundcraft.viewmodel.SoundCraftViewModel] */
    /* JADX WARN: Type inference failed for: r33v1 */
    /* JADX WARN: Type inference failed for: r33v10 */
    /* JADX WARN: Type inference failed for: r33v11 */
    /* JADX WARN: Type inference failed for: r33v2 */
    /* JADX WARN: Type inference failed for: r33v5 */
    /* JADX WARN: Type inference failed for: r33v6 */
    /* JADX WARN: Type inference failed for: r33v7 */
    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:146:0x060b -> B:91:0x060c). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:153:0x05bc -> B:83:0x05bd). Please report as a decompilation issue!!! */
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

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to find 'out' block for switch in B:84:0x0201. Please report as an issue. */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0273  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x027b  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0278  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x0248  */
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
