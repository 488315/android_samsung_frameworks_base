package com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel;

import android.content.Context;
import android.util.Log;
import com.android.settingslib.volume.shared.model.AudioStream;
import com.android.settingslib.volume.shared.model.AudioStreamModel;
import com.android.settingslib.volume.shared.model.RingerMode;
import com.android.systemui.R;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.AudioStreamSliderViewModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;
import kotlin.ranges.ClosedFloatRange;
import kotlin.ranges.IntRange;

final class AudioStreamSliderViewModel$slider$1 extends SuspendLambda implements Function4 {
    /* synthetic */ int I$0;
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ AudioStreamSliderViewModel this$0;

    public AudioStreamSliderViewModel$slider$1(AudioStreamSliderViewModel audioStreamSliderViewModel, Continuation continuation) {
        super(4, continuation);
        this.this$0 = audioStreamSliderViewModel;
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        boolean booleanValue = ((Boolean) obj2).booleanValue();
        int i = ((RingerMode) obj3).value;
        AudioStreamSliderViewModel$slider$1 audioStreamSliderViewModel$slider$1 = new AudioStreamSliderViewModel$slider$1(this.this$0, (Continuation) obj4);
        audioStreamSliderViewModel$slider$1.L$0 = (AudioStreamModel) obj;
        audioStreamSliderViewModel$slider$1.Z$0 = booleanValue;
        audioStreamSliderViewModel$slider$1.I$0 = i;
        return audioStreamSliderViewModel$slider$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        String string;
        int i;
        String str;
        String str2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        AudioStreamModel audioStreamModel = (AudioStreamModel) this.L$0;
        boolean z = this.Z$0;
        int i2 = this.I$0;
        AudioStreamSliderViewModel audioStreamSliderViewModel = this.this$0;
        Integer num = (Integer) audioStreamSliderViewModel.labelsByStream.get(AudioStream.m870boximpl(audioStreamModel.audioStream));
        int i3 = audioStreamModel.audioStream;
        if (num == null || (string = audioStreamSliderViewModel.context.getString(num.intValue())) == null) {
            throw new IllegalStateException(("No label for the stream: " + AudioStream.m872toStringimpl(i3)).toString());
        }
        int i4 = audioStreamModel.volume;
        float f = i4;
        int i5 = audioStreamModel.minVolume;
        int i6 = audioStreamModel.maxVolume;
        ClosedFloatRange closedFloatRange = new ClosedFloatRange(new IntRange(i5, i6).first, new IntRange(i5, i6).last);
        boolean z2 = audioStreamModel.isMuted;
        boolean z3 = audioStreamModel.isAffectedByMute;
        if (z3 && z2) {
            boolean contains = audioStreamSliderViewModel.streamsAffectedByRing.contains(Integer.valueOf(i3));
            i = R.drawable.ic_volume_off;
            if (contains && i2 == 1) {
                i = R.drawable.ic_volume_ringer_vibrate;
            }
        } else {
            Integer num2 = (Integer) audioStreamSliderViewModel.iconsByStream.get(AudioStream.m870boximpl(i3));
            if (num2 != null) {
                i = num2.intValue();
            } else {
                Log.wtf("AudioStreamSliderViewModel", "No icon for the stream: " + AudioStream.m872toStringimpl(i3));
                i = R.drawable.ic_music_note;
            }
        }
        Icon.Resource resource = new Icon.Resource(i, null);
        String string2 = audioStreamSliderViewModel.context.getString(((Number) audioStreamSliderViewModel.disabledTextByStream.getOrDefault(AudioStream.m870boximpl(i3), Integer.valueOf(R.string.stream_alarm_unavailable))).intValue());
        int i7 = new IntRange(i5, i6).step;
        if (z3) {
            str = audioStreamSliderViewModel.context.getString(z2 ? R.string.volume_panel_hint_unmute : R.string.volume_panel_hint_mute, string);
        } else {
            str = null;
        }
        if (i4 == new IntRange(i5, i6).first) {
            Context context = audioStreamSliderViewModel.context;
            boolean contains2 = audioStreamSliderViewModel.streamsAffectedByRing.contains(Integer.valueOf(i3));
            int i8 = R.string.volume_panel_hint_muted;
            if (contains2 && i2 == 1) {
                i8 = R.string.volume_panel_hint_vibrate;
            }
            str2 = context.getString(i8);
        } else {
            str2 = null;
        }
        return new AudioStreamSliderViewModel.State(f, closedFloatRange, resource, string, string2, z, i7, str, str2, audioStreamModel.isAffectedByMute, audioStreamModel);
    }
}
