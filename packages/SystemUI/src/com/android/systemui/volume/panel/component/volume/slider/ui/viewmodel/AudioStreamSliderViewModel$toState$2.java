package com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.AudioSystem;
import android.util.Log;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.volume.shared.model.AudioStream;
import com.android.settingslib.volume.shared.model.AudioStreamModel;
import com.android.systemui.R;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.haptics.slider.SliderHapticFeedbackFilter;
import com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.AudioStreamSliderViewModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.ranges.ClosedFloatRange;
import kotlin.ranges.IntRange;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes3.dex */
final class AudioStreamSliderViewModel$toState$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ String $disabledMessage;
    final /* synthetic */ boolean $inAudioSharing;
    final /* synthetic */ boolean $isEnabled;
    final /* synthetic */ CachedBluetoothDevice $primaryDevice;
    final /* synthetic */ int $ringerMode;
    final /* synthetic */ AudioStreamModel $this_toState;
    int label;
    final /* synthetic */ AudioStreamSliderViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AudioStreamSliderViewModel$toState$2(AudioStreamSliderViewModel audioStreamSliderViewModel, AudioStreamModel audioStreamModel, boolean z, CachedBluetoothDevice cachedBluetoothDevice, int i, boolean z2, String str, Continuation continuation) {
        super(2, continuation);
        this.this$0 = audioStreamSliderViewModel;
        this.$this_toState = audioStreamModel;
        this.$inAudioSharing = z;
        this.$primaryDevice = cachedBluetoothDevice;
        this.$ringerMode = i;
        this.$isEnabled = z2;
        this.$disabledMessage = str;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new AudioStreamSliderViewModel$toState$2(this.this$0, this.$this_toState, this.$inAudioSharing, this.$primaryDevice, this.$ringerMode, this.$isEnabled, this.$disabledMessage, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AudioStreamSliderViewModel$toState$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        String string;
        SliderHapticFeedbackFilter sliderHapticFeedbackFilter;
        int i;
        int i2;
        String string2;
        String str;
        String string3;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        AudioStreamSliderViewModel audioStreamSliderViewModel = this.this$0;
        AudioStreamModel audioStreamModel = this.$this_toState;
        Integer num = (Integer) audioStreamSliderViewModel.labelsByStream.get(AudioStream.m990boximpl(audioStreamModel.audioStream));
        if (num == null || (string = audioStreamSliderViewModel.context.getString(num.intValue())) == null) {
            throw new IllegalStateException(("No label for the stream: " + AudioSystem.streamToString(audioStreamModel.audioStream)).toString());
        }
        AudioStreamModel audioStreamModel2 = this.$this_toState;
        float f = audioStreamModel2.volume;
        this.this$0.getClass();
        float f2 = new IntRange(audioStreamModel2.minVolume, audioStreamModel2.maxVolume).first;
        AudioStreamSliderViewModel audioStreamSliderViewModel2 = this.this$0;
        AudioStreamModel audioStreamModel3 = this.$this_toState;
        audioStreamSliderViewModel2.getClass();
        ClosedFloatRange closedFloatRange = new ClosedFloatRange(f2, new IntRange(audioStreamModel3.minVolume, audioStreamModel3.maxVolume).last);
        AudioStreamSliderViewModel audioStreamSliderViewModel3 = this.this$0;
        AudioStreamModel audioStreamModel4 = this.$this_toState;
        int i3 = this.$ringerMode;
        audioStreamSliderViewModel3.getClass();
        int i4 = audioStreamModel4.audioStream;
        String string4 = null;
        if (i4 == 2) {
            sliderHapticFeedbackFilter = new SliderHapticFeedbackFilter(false, false, 1, null);
        } else if (i4 != 5) {
            sliderHapticFeedbackFilter = new SliderHapticFeedbackFilter(false, false, 3, null);
        } else {
            sliderHapticFeedbackFilter = new SliderHapticFeedbackFilter(false, i3 != 1, 1, null);
        }
        AudioStreamSliderViewModel audioStreamSliderViewModel4 = this.this$0;
        AudioStreamModel audioStreamModel5 = this.$this_toState;
        int i5 = this.$ringerMode;
        audioStreamSliderViewModel4.getClass();
        if (audioStreamModel5.isMuted) {
            boolean z = audioStreamModel5.isAffectedByRingerMode;
            i2 = R.drawable.ic_volume_off;
            if (z && i5 == 1) {
                i2 = R.drawable.ic_volume_ringer_vibrate;
            }
        } else {
            int i6 = audioStreamModel5.audioStream;
            if (i6 == 0) {
                i = R.drawable.ic_call;
            } else if (i6 != 2) {
                if (i6 != 3) {
                    if (i6 == 4) {
                        i = R.drawable.ic_volume_alarm;
                    } else if (i6 != 5) {
                        Log.wtf("AudioStreamSliderViewModel", "No icon for the stream: " + AudioSystem.streamToString(i6));
                    } else {
                        i = R.drawable.ic_volume_ringer;
                    }
                }
                i2 = R.drawable.ic_music_note;
            } else {
                i = R.drawable.ic_ring_volume;
            }
            i2 = i;
        }
        Drawable drawable = audioStreamSliderViewModel4.context.getDrawable(i2);
        drawable.getClass();
        Icon.Loaded loaded = new Icon.Loaded(drawable, null, Integer.valueOf(i2));
        AudioStreamSliderViewModel audioStreamSliderViewModel5 = this.this$0;
        AudioStreamModel audioStreamModel6 = this.$this_toState;
        audioStreamSliderViewModel5.getClass();
        float f3 = new IntRange(audioStreamModel6.minVolume, audioStreamModel6.maxVolume).step;
        String str2 = (this.$isEnabled || (str = this.$disabledMessage) == null || (string3 = this.this$0.context.getString(R.string.volume_slider_disabled_message_template, string, str)) == null) ? string : string3;
        AudioStreamModel audioStreamModel7 = this.$this_toState;
        if (audioStreamModel7.isAffectedByMute) {
            string2 = this.this$0.context.getString(audioStreamModel7.isMuted ? R.string.volume_panel_hint_unmute : R.string.volume_panel_hint_mute, string);
        } else {
            string2 = null;
        }
        AudioStreamModel audioStreamModel8 = this.$this_toState;
        if (audioStreamModel8.isMuted) {
            Context context = this.this$0.context;
            boolean z2 = audioStreamModel8.isAffectedByRingerMode;
            int i7 = R.string.volume_panel_hint_muted;
            if (z2 && this.$ringerMode == 1) {
                i7 = R.string.volume_panel_hint_vibrate;
            }
            string4 = context.getString(i7);
        }
        AudioStreamModel audioStreamModel9 = this.$this_toState;
        return new AudioStreamSliderViewModel.State(f, closedFloatRange, f3, sliderHapticFeedbackFilter, loaded, string, this.$disabledMessage, this.$isEnabled, string2, string4, str2, audioStreamModel9.isAffectedByMute, audioStreamModel9);
    }
}
