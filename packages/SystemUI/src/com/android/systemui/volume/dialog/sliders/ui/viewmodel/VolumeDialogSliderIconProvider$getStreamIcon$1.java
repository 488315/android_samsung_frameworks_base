package com.android.systemui.volume.dialog.sliders.ui.viewmodel;

import android.graphics.drawable.Drawable;
import com.android.settingslib.volume.shared.model.RingerMode;
import com.android.systemui.R;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.statusbar.policy.domain.model.ActiveZenModes;
import com.android.systemui.statusbar.policy.domain.model.ZenModeInfo;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.BuildersKt;

/* loaded from: classes3.dex */
final class VolumeDialogSliderIconProvider$getStreamIcon$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ boolean $isMuted;
    final /* synthetic */ boolean $isRoutedToBluetooth;
    final /* synthetic */ int $level;
    final /* synthetic */ int $levelMax;
    final /* synthetic */ int $levelMin;
    final /* synthetic */ int $stream;
    int I$0;
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ VolumeDialogSliderIconProvider this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VolumeDialogSliderIconProvider$getStreamIcon$1(VolumeDialogSliderIconProvider volumeDialogSliderIconProvider, int i, int i2, int i3, int i4, boolean z, boolean z2, Continuation continuation) {
        super(3, continuation);
        this.this$0 = volumeDialogSliderIconProvider;
        this.$stream = i;
        this.$level = i2;
        this.$levelMin = i3;
        this.$levelMax = i4;
        this.$isMuted = z;
        this.$isRoutedToBluetooth = z2;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        VolumeDialogSliderIconProvider$getStreamIcon$1 volumeDialogSliderIconProvider$getStreamIcon$1 = new VolumeDialogSliderIconProvider$getStreamIcon$1(this.this$0, this.$stream, this.$level, this.$levelMin, this.$levelMax, this.$isMuted, this.$isRoutedToBluetooth, (Continuation) obj3);
        volumeDialogSliderIconProvider$getStreamIcon$1.L$0 = (ActiveZenModes) obj;
        volumeDialogSliderIconProvider$getStreamIcon$1.L$1 = (RingerMode) obj2;
        return volumeDialogSliderIconProvider$getStreamIcon$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:53:0x00ab  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00d3  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00d8  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) throws Throwable {
        Integer numValueOf;
        int iIntValue;
        int i;
        ZenModeInfo zenModeInfo;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = this.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            ActiveZenModes activeZenModes = (ActiveZenModes) this.L$0;
            RingerMode ringerMode = (RingerMode) this.L$1;
            if (((activeZenModes == null || (zenModeInfo = activeZenModes.mainMode) == null) ? null : zenModeInfo.icon) != null) {
                return new Icon.Loaded(activeZenModes.mainMode.icon.drawable, null, null, 4, null);
            }
            VolumeDialogSliderIconProvider volumeDialogSliderIconProvider = this.this$0;
            int i3 = this.$stream;
            int i4 = this.$level;
            int i5 = this.$levelMin;
            int i6 = this.$levelMax;
            boolean z = this.$isMuted;
            boolean z2 = this.$isRoutedToBluetooth;
            volumeDialogSliderIconProvider.getClass();
            boolean z3 = i4 == 0 || z;
            if (z2) {
                iIntValue = i3 == 0 ? R.drawable.ic_volume_bt_sco : z3 ? R.drawable.ic_volume_media_bt_mute : R.drawable.ic_volume_media_bt;
            } else {
                boolean z4 = i4 < (i6 + i5) / 2;
                if (z3) {
                    Integer numValueOf2 = ringerMode != null ? Integer.valueOf(ringerMode.value) : null;
                    if (numValueOf2 != null && numValueOf2.intValue() == 1) {
                        iIntValue = R.drawable.ic_volume_ringer_vibrate;
                    } else if (numValueOf2 != null && numValueOf2.intValue() == 0) {
                        iIntValue = R.drawable.ic_ring_volume_off;
                    } else {
                        if (i3 == 1) {
                            numValueOf = Integer.valueOf(R.drawable.ic_volume_system_mute);
                        } else if (i3 == 2) {
                            numValueOf = Integer.valueOf(R.drawable.ic_volume_ringer_vibrate);
                        } else if (i3 == 3) {
                            numValueOf = Integer.valueOf(R.drawable.ic_volume_media_mute);
                        } else if (i3 == 4) {
                            numValueOf = Integer.valueOf(R.drawable.ic_volume_alarm_mute);
                        } else if (i3 == 5) {
                            numValueOf = Integer.valueOf(R.drawable.ic_volume_ringer_mute);
                        }
                        if (numValueOf == null) {
                        }
                    }
                } else {
                    numValueOf = null;
                    if (numValueOf == null) {
                        iIntValue = numValueOf.intValue();
                    } else if (i3 == 0) {
                        iIntValue = android.R.drawable.input_method_fullscreen_background;
                    } else if (i3 == 1) {
                        iIntValue = R.drawable.ic_volume_system;
                    } else if (i3 == 2) {
                        iIntValue = R.drawable.ic_ring_volume;
                    } else if (i3 == 3) {
                        iIntValue = z4 ? R.drawable.ic_volume_media_low : R.drawable.ic_volume_media;
                    } else if (i3 == 4) {
                        iIntValue = R.drawable.ic_alarm;
                    } else if (i3 == 5) {
                        iIntValue = R.drawable.ic_volume_ringer;
                    } else {
                        if (i3 != 10) {
                            throw new IllegalStateException(("Unsupported stream: " + i3).toString());
                        }
                        iIntValue = R.drawable.ic_volume_accessibility;
                    }
                }
            }
            VolumeDialogSliderIconProvider volumeDialogSliderIconProvider2 = this.this$0;
            CoroutineContext coroutineContext = volumeDialogSliderIconProvider2.uiBackgroundContext;
            VolumeDialogSliderIconProvider$getStreamIcon$1$drawable$1 volumeDialogSliderIconProvider$getStreamIcon$1$drawable$1 = new VolumeDialogSliderIconProvider$getStreamIcon$1$drawable$1(volumeDialogSliderIconProvider2, iIntValue, null);
            this.L$0 = null;
            this.I$0 = iIntValue;
            this.label = 1;
            Object objWithContext = BuildersKt.withContext(coroutineContext, volumeDialogSliderIconProvider$getStreamIcon$1$drawable$1, this);
            if (objWithContext == coroutineSingletons) {
                return coroutineSingletons;
            }
            int i7 = iIntValue;
            obj = objWithContext;
            i = i7;
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            i = this.I$0;
            ResultKt.throwOnFailure(obj);
        }
        return new Icon.Loaded((Drawable) obj, null, new Integer(i));
    }
}
