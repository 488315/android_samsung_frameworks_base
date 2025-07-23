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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SoundCraftViewModel extends BaseViewModel {
    public static final /* synthetic */ int $r8$clinit = 0;
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
    public final MutableLiveData isVolumeBarVisible = new MutableLiveData();
    public final MutableLiveData isFromNowBar = new MutableLiveData();
    public final MutableLiveData isFromCover = new MutableLiveData();
    public final MutableLiveData updateBatteryInfoBox = new MutableLiveData();
    public final MutableLiveData updateEffectBox = new MutableLiveData();
    public final MutableLiveData updateNoiseControlBox = new MutableLiveData();
    public final MutableLiveData updateVolumeBar = new MutableLiveData();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
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

    /* JADX WARN: Can't wrap try/catch for region: R(27:80|81|82|(23:84|85|(1:87)(8:136|137|138|(4:140|141|(1:143)(1:145)|144)|147|141|(0)(0)|144)|88|89|90|(1:92)|94|(1:96)(1:131)|97|(4:100|(2:102|103)(2:105|106)|104|98)|107|108|109|110|(1:112)|114|115|116|117|(1:119)|121|122)|153|85|(0)(0)|88|89|90|(0)|94|(0)(0)|97|(1:98)|107|108|109|110|(0)|114|115|116|117|(0)|121|122) */
    /* JADX WARN: Code restructure failed: missing block: B:123:0x06a1, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:124:0x06a6, code lost:
    
        r3 = kotlin.Result.$r8$clinit;
        r0 = kotlin.Result.m3422exceptionOrNullimpl(new kotlin.Result.Failure(r0));
     */
    /* JADX WARN: Code restructure failed: missing block: B:125:0x06b1, code lost:
    
        if (r0 != null) goto L285;
     */
    /* JADX WARN: Code restructure failed: missing block: B:126:0x06b3, code lost:
    
        r0.printStackTrace();
     */
    /* JADX WARN: Code restructure failed: missing block: B:127:0x0660, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:128:0x0665, code lost:
    
        r3 = kotlin.Result.$r8$clinit;
        r0 = kotlin.Result.m3422exceptionOrNullimpl(new kotlin.Result.Failure(r0));
     */
    /* JADX WARN: Code restructure failed: missing block: B:129:0x0670, code lost:
    
        if (r0 != null) goto L274;
     */
    /* JADX WARN: Code restructure failed: missing block: B:130:0x0672, code lost:
    
        r0.printStackTrace();
     */
    /* JADX WARN: Code restructure failed: missing block: B:132:0x05e1, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:133:0x05e2, code lost:
    
        r4 = kotlin.Result.$r8$clinit;
        r0 = kotlin.Result.m3422exceptionOrNullimpl(new kotlin.Result.Failure(r0));
     */
    /* JADX WARN: Code restructure failed: missing block: B:134:0x05ed, code lost:
    
        if (r0 != null) goto L251;
     */
    /* JADX WARN: Code restructure failed: missing block: B:135:0x05ef, code lost:
    
        r0.printStackTrace();
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x03d3, code lost:
    
        if (r1 == false) goto L179;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:100:0x0613  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x0646 A[Catch: all -> 0x0660, TRY_LEAVE, TryCatch #0 {all -> 0x0660, blocks: (B:110:0x0639, B:112:0x0646), top: B:109:0x0639 }] */
    /* JADX WARN: Removed duplicated region for block: B:119:0x0687 A[Catch: all -> 0x06a1, TRY_LEAVE, TryCatch #4 {all -> 0x06a1, blocks: (B:117:0x067c, B:119:0x0687), top: B:116:0x067c }] */
    /* JADX WARN: Removed duplicated region for block: B:131:0x05fb  */
    /* JADX WARN: Removed duplicated region for block: B:136:0x056e  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x05af  */
    /* JADX WARN: Removed duplicated region for block: B:145:0x05b1  */
    /* JADX WARN: Removed duplicated region for block: B:159:0x03b9  */
    /* JADX WARN: Removed duplicated region for block: B:171:0x01cd  */
    /* JADX WARN: Removed duplicated region for block: B:185:0x0334  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0378  */
    /* JADX WARN: Removed duplicated region for block: B:256:0x0363  */
    /* JADX WARN: Removed duplicated region for block: B:258:0x0366  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x056b  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x05c7 A[Catch: all -> 0x05e1, TRY_LEAVE, TryCatch #5 {all -> 0x05e1, blocks: (B:90:0x05bc, B:92:0x05c7), top: B:89:0x05bc }] */
    /* JADX WARN: Removed duplicated region for block: B:96:0x05f9  */
    /* JADX WARN: Type inference failed for: r14v10 */
    /* JADX WARN: Type inference failed for: r14v11 */
    /* JADX WARN: Type inference failed for: r14v12 */
    /* JADX WARN: Type inference failed for: r14v13 */
    /* JADX WARN: Type inference failed for: r14v14 */
    /* JADX WARN: Type inference failed for: r14v15 */
    /* JADX WARN: Type inference failed for: r14v16 */
    /* JADX WARN: Type inference failed for: r14v20, types: [com.samsung.android.sdk.routines.automationservice.data.ConditionStatus$Companion, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r14v24 */
    /* JADX WARN: Type inference failed for: r14v33 */
    /* JADX WARN: Type inference failed for: r14v34 */
    /* JADX WARN: Type inference failed for: r14v35 */
    /* JADX WARN: Type inference failed for: r14v6 */
    /* JADX WARN: Type inference failed for: r14v7 */
    /* JADX WARN: Type inference failed for: r14v8 */
    /* JADX WARN: Type inference failed for: r19v0 */
    /* JADX WARN: Type inference failed for: r19v1 */
    /* JADX WARN: Type inference failed for: r19v12 */
    /* JADX WARN: Type inference failed for: r19v13 */
    /* JADX WARN: Type inference failed for: r19v22 */
    /* JADX WARN: Type inference failed for: r19v23 */
    /* JADX WARN: Type inference failed for: r19v4, types: [android.content.ContentResolver] */
    /* JADX WARN: Type inference failed for: r19v5 */
    /* JADX WARN: Type inference failed for: r19v6 */
    /* JADX WARN: Type inference failed for: r19v7 */
    /* JADX WARN: Type inference failed for: r19v8 */
    /* JADX WARN: Type inference failed for: r20v10 */
    /* JADX WARN: Type inference failed for: r20v11 */
    /* JADX WARN: Type inference failed for: r20v12 */
    /* JADX WARN: Type inference failed for: r20v13 */
    /* JADX WARN: Type inference failed for: r20v17 */
    /* JADX WARN: Type inference failed for: r20v18 */
    /* JADX WARN: Type inference failed for: r20v19 */
    /* JADX WARN: Type inference failed for: r20v21 */
    /* JADX WARN: Type inference failed for: r20v23 */
    /* JADX WARN: Type inference failed for: r20v24 */
    /* JADX WARN: Type inference failed for: r20v5 */
    /* JADX WARN: Type inference failed for: r20v6 */
    /* JADX WARN: Type inference failed for: r20v9, types: [android.net.Uri] */
    /* JADX WARN: Type inference failed for: r4v28, types: [java.lang.Object] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.systemui.audio.soundcraft.model.common.EffectModel getAppRoutineModel() {
        /*
            Method dump skipped, instructions count: 1794
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
        this.isVolumeBarVisible.setValue(Boolean.valueOf(!modelProvider.isFromCover));
        this.isBatteryInfoBoxVisible.setValue(Boolean.valueOf(z2));
        MutableLiveData mutableLiveData = this.isNoiseControlBoxVisible;
        if (z2 && hasNoiseControl()) {
            z = true;
        }
        mutableLiveData.setValue(Boolean.valueOf(z));
        this.isFromNowBar.setValue(Boolean.valueOf(modelProvider.isFromNowBar));
        this.isFromCover.setValue(Boolean.valueOf(modelProvider.isFromCover));
        this.updateEffectBox.setValue(Boolean.TRUE);
    }

    @Override // androidx.lifecycle.ViewModel
    public final void onCleared() {
        Log.d("SoundCraft.SoundCraftViewModel", "onCleared");
        RoutineManager routineManager = this.routineManager;
        AutomationService service = routineManager.getService();
        Context context = routineManager.context;
        AutomationService.SystemRoutineType currentSystemRoutineType = routineManager.getCurrentSystemRoutineType();
        AutomationServiceImpl automationServiceImpl = (AutomationServiceImpl) service;
        automationServiceImpl.getClass();
        currentSystemRoutineType.getClass();
        ContentHandlerImpl$register$1 contentHandlerImpl$register$1 = ((ContentHandlerImpl) automationServiceImpl.contentHandler).contentObserver;
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
}
