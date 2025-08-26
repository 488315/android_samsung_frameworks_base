package com.android.systemui.audio.soundcraft.view.audioeffect;

import com.android.systemui.R;
import com.android.systemui.audio.soundcraft.viewbinding.audioeffect.AudioEffectBoxLayoutBinding;
import com.android.systemui.qs.bar.ColoredBGHelper;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final /* synthetic */ class AudioEffectBoxView$$ExternalSyntheticLambda15 implements Function1 {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ AudioEffectBoxView f$0;
    public final /* synthetic */ AudioEffectBoxLayoutBinding f$1;

    public /* synthetic */ AudioEffectBoxView$$ExternalSyntheticLambda15(AudioEffectBoxView audioEffectBoxView, AudioEffectBoxLayoutBinding audioEffectBoxLayoutBinding) {
        this.f$0 = audioEffectBoxView;
        this.f$1 = audioEffectBoxLayoutBinding;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        AudioEffectBoxView audioEffectBoxView = this.f$0;
        AudioEffectBoxLayoutBinding audioEffectBoxLayoutBinding = this.f$1;
        switch (this.$r8$classId) {
            case 0:
                String str = (String) obj;
                int i = AudioEffectBoxView.$r8$clinit;
                if (Intrinsics.areEqual(audioEffectBoxView.getViewModel().isFallbackTextVisible.getValue(), Boolean.TRUE)) {
                    audioEffectBoxLayoutBinding.fallbackText.setText(str);
                }
                break;
            default:
                Boolean bool = (Boolean) obj;
                int i2 = AudioEffectBoxView.$r8$clinit;
                audioEffectBoxLayoutBinding.box.setBackground((bool.booleanValue() && Intrinsics.areEqual(audioEffectBoxView.getViewModel().isCoverScreen.getValue(), Boolean.TRUE)) ? audioEffectBoxView.getContext().getDrawable(R.drawable.soundcraft_cover_detailed_container_background) : bool.booleanValue() ? audioEffectBoxView.getContext().getDrawable(R.drawable.soundcraft_buds_effect_container_background) : null);
                if (bool.booleanValue() && Intrinsics.areEqual(audioEffectBoxView.getViewModel().isCoverScreen.getValue(), Boolean.FALSE)) {
                    ColoredBGHelper coloredBGHelper = audioEffectBoxView.getViewModel().coloredBGHelper;
                    coloredBGHelper.setBackGroundDrawable(audioEffectBoxLayoutBinding.box, coloredBGHelper.getBGColor());
                }
                break;
        }
        return Unit.INSTANCE;
    }

    public /* synthetic */ AudioEffectBoxView$$ExternalSyntheticLambda15(AudioEffectBoxLayoutBinding audioEffectBoxLayoutBinding, AudioEffectBoxView audioEffectBoxView) {
        this.f$1 = audioEffectBoxLayoutBinding;
        this.f$0 = audioEffectBoxView;
    }
}
