package com.android.systemui.audio.soundcraft.view.audioeffect;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.android.systemui.audio.soundcraft.di.vm.component.SoundCraftLocalViewModelStoreOwner;
import com.android.systemui.audio.soundcraft.di.vm.component.SoundCraftVMComponent;
import com.android.systemui.audio.soundcraft.di.vm.component.SoundCraftViewModelExt;
import com.android.systemui.audio.soundcraft.view.SoundCraftDetailPageView$$ExternalSyntheticOutline0;
import com.android.systemui.audio.soundcraft.view.SoundCraftViewComponent;
import com.android.systemui.audio.soundcraft.viewmodel.common.audioeffect.AudioEffectHeaderViewModel;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class AudioEffectHeaderView extends LinearLayout implements SoundCraftVMComponent {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Lazy viewModel$delegate;

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

    public AudioEffectHeaderView(Context context) {
        super(context);
        SoundCraftViewModelExt soundCraftViewModelExt = SoundCraftViewModelExt.INSTANCE;
        this.viewModel$delegate = LazyKt__LazyJVMKt.lazy(LazyThreadSafetyMode.SYNCHRONIZED, new Function0(this) { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectHeaderView$special$$inlined$lazyViewModel$1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                SoundCraftViewModelExt soundCraftViewModelExt2 = SoundCraftViewModelExt.INSTANCE;
                SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
                SoundCraftViewComponent soundCraftViewComponent = SoundCraftLocalViewModelStoreOwner.current;
                if (soundCraftViewComponent != null) {
                    return SoundCraftDetailPageView$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, AudioEffectHeaderViewModel.class);
                }
                throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
            }
        });
    }

    public AudioEffectHeaderView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        SoundCraftViewModelExt soundCraftViewModelExt = SoundCraftViewModelExt.INSTANCE;
        this.viewModel$delegate = LazyKt__LazyJVMKt.lazy(LazyThreadSafetyMode.SYNCHRONIZED, new Function0(this) { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectHeaderView$special$$inlined$lazyViewModel$2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                SoundCraftViewModelExt soundCraftViewModelExt2 = SoundCraftViewModelExt.INSTANCE;
                SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
                SoundCraftViewComponent soundCraftViewComponent = SoundCraftLocalViewModelStoreOwner.current;
                if (soundCraftViewComponent != null) {
                    return SoundCraftDetailPageView$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, AudioEffectHeaderViewModel.class);
                }
                throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
            }
        });
    }

    public AudioEffectHeaderView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        SoundCraftViewModelExt soundCraftViewModelExt = SoundCraftViewModelExt.INSTANCE;
        this.viewModel$delegate = LazyKt__LazyJVMKt.lazy(LazyThreadSafetyMode.SYNCHRONIZED, new Function0(this) { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectHeaderView$special$$inlined$lazyViewModel$3
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                SoundCraftViewModelExt soundCraftViewModelExt2 = SoundCraftViewModelExt.INSTANCE;
                SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
                SoundCraftViewComponent soundCraftViewComponent = SoundCraftLocalViewModelStoreOwner.current;
                if (soundCraftViewComponent != null) {
                    return SoundCraftDetailPageView$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, AudioEffectHeaderViewModel.class);
                }
                throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
            }
        });
    }
}
