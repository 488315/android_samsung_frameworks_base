package com.android.systemui.audio.soundcraft.view.audioeffect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.SwitchCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import com.android.systemui.R;
import com.android.systemui.audio.soundcraft.viewbinding.SoundCraftViewBindingFactory;
import com.android.systemui.audio.soundcraft.viewbinding.audioeffect.AudioEffectToggleItemViewBinding;
import com.android.systemui.audio.soundcraft.viewmodel.common.base.BaseToggleViewModel;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class AudioEffectToggleItemView extends BaseAudioEffectItemView {
    public final AudioEffectToggleItemViewBinding binding;
    public final LifecycleOwner lifecycleOwner;
    public final ViewGroup parent;
    public final BaseToggleViewModel viewModel;

    public AudioEffectToggleItemView(Context context, LifecycleOwner lifecycleOwner, BaseToggleViewModel baseToggleViewModel, ViewGroup viewGroup) {
        this.lifecycleOwner = lifecycleOwner;
        this.viewModel = baseToggleViewModel;
        this.parent = viewGroup;
        int i = SoundCraftViewBindingFactory.$r8$clinit;
        AudioEffectToggleItemViewBinding audioEffectToggleItemViewBinding = new AudioEffectToggleItemViewBinding(LayoutInflater.from(context).inflate(R.layout.soundcraft_audio_effect_toggle_item, viewGroup, false));
        this.binding = audioEffectToggleItemViewBinding;
        audioEffectToggleItemViewBinding.root.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectToggleItemView.1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AudioEffectToggleItemView.this.viewModel.onClick();
            }
        });
        audioEffectToggleItemViewBinding.f23switch.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectToggleItemView.2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AudioEffectToggleItemView.this.viewModel.onClick();
            }
        });
        baseToggleViewModel.name.observe(lifecycleOwner, new Observer() { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectToggleItemView.3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                AudioEffectToggleItemView.this.binding.name.setText((String) obj);
            }
        });
        baseToggleViewModel.isSelected.observe(lifecycleOwner, new Observer() { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectToggleItemView.4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                Boolean bool = (Boolean) obj;
                AudioEffectToggleItemView audioEffectToggleItemView = AudioEffectToggleItemView.this;
                if (Intrinsics.areEqual(bool, Boolean.valueOf(audioEffectToggleItemView.binding.f23switch.isChecked()))) {
                    return;
                }
                SwitchCompat switchCompat = audioEffectToggleItemView.binding.f23switch;
                Intrinsics.checkNotNull(bool);
                switchCompat.setChecked(bool.booleanValue());
            }
        });
        baseToggleViewModel.notifyChange();
    }

    @Override // com.android.systemui.audio.soundcraft.view.audioeffect.BaseAudioEffectItemView
    public final void enable(boolean z) {
        AudioEffectToggleItemViewBinding audioEffectToggleItemViewBinding = this.binding;
        audioEffectToggleItemViewBinding.root.setClickable(z);
        audioEffectToggleItemViewBinding.root.setEnabled(z);
        SwitchCompat switchCompat = audioEffectToggleItemViewBinding.f23switch;
        switchCompat.setClickable(z);
        switchCompat.setEnabled(z);
        audioEffectToggleItemViewBinding.root.setAlpha(z ? 1.0f : 0.5f);
    }

    @Override // com.android.systemui.audio.soundcraft.view.audioeffect.BaseAudioEffectItemView
    public final ViewGroup getRootView() {
        return this.binding.root;
    }

    @Override // com.android.systemui.audio.soundcraft.view.audioeffect.BaseAudioEffectItemView
    public final void update() {
        this.viewModel.notifyChange();
    }
}
