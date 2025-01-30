package com.android.systemui.p016qs.bar.soundcraft.view.audioeffect;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import com.android.systemui.R;
import com.android.systemui.p016qs.bar.soundcraft.p017di.p018vm.SoundCraftViewModelProvider;
import com.android.systemui.p016qs.bar.soundcraft.viewbinding.AudioEffectBoxViewBinding;
import com.android.systemui.p016qs.bar.soundcraft.viewmodel.base.BaseSingleChoiceViewModel;
import com.android.systemui.p016qs.bar.soundcraft.viewmodel.base.BaseToggleViewModel;
import com.android.systemui.p016qs.bar.soundcraft.viewmodel.base.BaseViewModel;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class AudioEffectBoxView extends LinearLayout implements LifecycleOwner {
    public BaseAudioEffectItemView equalizerView;
    public final LifecycleRegistry registry;
    public BaseAudioEffectItemView spatialAudioView;
    public AudioEffectBoxViewBinding viewBinding;
    public SoundCraftViewModelProvider vmProvider;
    public BaseAudioEffectItemView voiceBoostView;
    public BaseAudioEffectItemView volumeNormalizationView;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    public AudioEffectBoxView(Context context) {
        super(context);
        LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
        this.registry = lifecycleRegistry;
        lifecycleRegistry.setCurrentState(Lifecycle.State.CREATED);
    }

    public final void addDivider() {
        LinearLayout linearLayout = getViewBinding().effectItemList;
        linearLayout.addView(LayoutInflater.from(getContext()).inflate(R.layout.soundcraft_effect_divider, (ViewGroup) linearLayout, false));
    }

    public final BaseAudioEffectItemView createItemView(BaseViewModel baseViewModel) {
        if (baseViewModel instanceof BaseSingleChoiceViewModel) {
            return new AudioEffectSingleChoiceItemView(getContext(), this, (BaseSingleChoiceViewModel) baseViewModel, getViewBinding().effectItemList);
        }
        if (baseViewModel instanceof BaseToggleViewModel) {
            return new AudioEffectToggleItemView(getContext(), this, (BaseToggleViewModel) baseViewModel, getViewBinding().effectItemList);
        }
        throw new RuntimeException();
    }

    @Override // androidx.lifecycle.LifecycleOwner
    public final LifecycleRegistry getLifecycle() {
        return this.registry;
    }

    public final AudioEffectBoxViewBinding getViewBinding() {
        AudioEffectBoxViewBinding audioEffectBoxViewBinding = this.viewBinding;
        if (audioEffectBoxViewBinding != null) {
            return audioEffectBoxViewBinding;
        }
        return null;
    }

    public final SoundCraftViewModelProvider getVmProvider() {
        SoundCraftViewModelProvider soundCraftViewModelProvider = this.vmProvider;
        if (soundCraftViewModelProvider != null) {
            return soundCraftViewModelProvider;
        }
        return null;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.registry.setCurrentState(Lifecycle.State.RESUMED);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.registry.setCurrentState(Lifecycle.State.DESTROYED);
    }

    public AudioEffectBoxView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
        this.registry = lifecycleRegistry;
        lifecycleRegistry.setCurrentState(Lifecycle.State.CREATED);
    }

    public AudioEffectBoxView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
        this.registry = lifecycleRegistry;
        lifecycleRegistry.setCurrentState(Lifecycle.State.CREATED);
    }
}
