package com.android.systemui.volume.dialog.ringer.ui.viewmodel;

import com.android.settingslib.volume.shared.model.RingerMode;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.volume.dialog.ringer.shared.model.VolumeDialogRingerModel;
import com.android.systemui.volume.dialog.ringer.ui.viewmodel.RingerViewModelState;
import com.android.systemui.volume.dialog.shared.VolumeDialogLogger;
import com.android.systemui.volume.dialog.shared.VolumeDialogLogger$$ExternalSyntheticLambda0;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function5;

/* loaded from: classes3.dex */
final class VolumeDialogRingerDrawerViewModel$ringerViewModel$1 extends SuspendLambda implements Function5 {
    /* synthetic */ int I$0;
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ VolumeDialogRingerDrawerViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VolumeDialogRingerDrawerViewModel$ringerViewModel$1(VolumeDialogRingerDrawerViewModel volumeDialogRingerDrawerViewModel, Continuation continuation) {
        super(5, continuation);
        this.this$0 = volumeDialogRingerDrawerViewModel;
    }

    @Override // kotlin.jvm.functions.Function5
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        boolean zBooleanValue = ((Boolean) obj).booleanValue();
        int iIntValue = ((Number) obj4).intValue();
        VolumeDialogRingerDrawerViewModel$ringerViewModel$1 volumeDialogRingerDrawerViewModel$ringerViewModel$1 = new VolumeDialogRingerDrawerViewModel$ringerViewModel$1(this.this$0, (Continuation) obj5);
        volumeDialogRingerDrawerViewModel$ringerViewModel$1.Z$0 = zBooleanValue;
        volumeDialogRingerDrawerViewModel$ringerViewModel$1.L$0 = (VolumeDialogRingerModel) obj2;
        volumeDialogRingerDrawerViewModel$ringerViewModel$1.L$1 = (RingerDrawerState) obj3;
        volumeDialogRingerDrawerViewModel$ringerViewModel$1.I$0 = iIntValue;
        return volumeDialogRingerDrawerViewModel$ringerViewModel$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = this.Z$0;
        VolumeDialogRingerModel volumeDialogRingerModel = (VolumeDialogRingerModel) this.L$0;
        RingerDrawerState ringerDrawerState = (RingerDrawerState) this.L$1;
        int i = this.I$0;
        VolumeDialogRingerDrawerViewModel volumeDialogRingerDrawerViewModel = this.this$0;
        volumeDialogRingerDrawerViewModel.level = volumeDialogRingerModel.level;
        volumeDialogRingerDrawerViewModel.levelMax = volumeDialogRingerModel.levelMax;
        List list = volumeDialogRingerModel.availableModes;
        int i2 = volumeDialogRingerModel.currentRingerMode;
        int iIndexOf = list.indexOf(RingerMode.m992boximpl(i2));
        if (iIndexOf == -1) {
            VolumeDialogLogger volumeDialogLogger = volumeDialogRingerDrawerViewModel.volumeDialogLogger;
            volumeDialogLogger.getClass();
            LogLevel logLevel = LogLevel.DEBUG;
            VolumeDialogLogger$$ExternalSyntheticLambda0 volumeDialogLogger$$ExternalSyntheticLambda0 = new VolumeDialogLogger$$ExternalSyntheticLambda0(4);
            LogBuffer logBuffer = volumeDialogLogger.logBuffer;
            LogMessage logMessageObtain = logBuffer.obtain("SysUI_VolumeDialog", logLevel, volumeDialogLogger$$ExternalSyntheticLambda0, null);
            ((LogMessageImpl) logMessageObtain).int1 = i2;
            logBuffer.commit(logMessageObtain);
        }
        if (iIndexOf == -1 || volumeDialogRingerModel.isSingleVolume) {
            return RingerViewModelState.Unavailable.INSTANCE;
        }
        RingerButtonViewModel ringerButtonViewModelM3216toButtonViewModelL0tBgz0 = VolumeDialogRingerDrawerViewModel.m3216toButtonViewModelL0tBgz0(volumeDialogRingerModel, i2, z, true);
        if (ringerButtonViewModelM3216toButtonViewModelL0tBgz0 == null) {
            return RingerViewModelState.Unavailable.INSTANCE;
        }
        List list2 = volumeDialogRingerModel.availableModes;
        ArrayList arrayList = new ArrayList();
        Iterator it = list2.iterator();
        while (it.hasNext()) {
            RingerButtonViewModel ringerButtonViewModelM3216toButtonViewModelL0tBgz02 = VolumeDialogRingerDrawerViewModel.m3216toButtonViewModelL0tBgz0(volumeDialogRingerModel, ((RingerMode) it.next()).value, z, false);
            if (ringerButtonViewModelM3216toButtonViewModelL0tBgz02 != null) {
                arrayList.add(ringerButtonViewModelM3216toButtonViewModelL0tBgz02);
            }
        }
        return new RingerViewModelState.Available(new RingerViewModel(arrayList, iIndexOf, ringerButtonViewModelM3216toButtonViewModelL0tBgz0, ringerDrawerState), i);
    }
}
