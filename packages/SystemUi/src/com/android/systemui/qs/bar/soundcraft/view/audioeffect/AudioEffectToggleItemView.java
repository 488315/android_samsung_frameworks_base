package com.android.systemui.qs.bar.soundcraft.view.audioeffect;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import com.android.systemui.R;
import com.android.systemui.qs.bar.soundcraft.viewbinding.AudioEffectToggleItemViewBinding;
import com.android.systemui.qs.bar.soundcraft.viewbinding.SoundCraftViewBindingFactory;
import com.android.systemui.qs.bar.soundcraft.viewmodel.base.BaseToggleViewModel;
import kotlin.Result;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class AudioEffectToggleItemView extends BaseAudioEffectItemView {
    public final AudioEffectToggleItemViewBinding binding;
    public final Context context;
    public final LifecycleOwner lifecycleOwner;
    public final ViewGroup parent;
    public final BaseToggleViewModel viewModel;

    public AudioEffectToggleItemView(Context context, LifecycleOwner lifecycleOwner, BaseToggleViewModel baseToggleViewModel, ViewGroup viewGroup) {
        this.context = context;
        this.lifecycleOwner = lifecycleOwner;
        this.viewModel = baseToggleViewModel;
        this.parent = viewGroup;
        int i = SoundCraftViewBindingFactory.$r8$clinit;
        AudioEffectToggleItemViewBinding audioEffectToggleItemViewBinding = new AudioEffectToggleItemViewBinding(LayoutInflater.from(context).inflate(R.layout.soundcraft_audio_effect_toggle_item, viewGroup, false));
        this.binding = audioEffectToggleItemViewBinding;
        audioEffectToggleItemViewBinding.root.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.bar.soundcraft.view.audioeffect.AudioEffectToggleItemView.1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AudioEffectToggleItemView.this.viewModel.onClick();
            }
        });
        audioEffectToggleItemViewBinding.f832switch.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.bar.soundcraft.view.audioeffect.AudioEffectToggleItemView.2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AudioEffectToggleItemView.this.viewModel.onClick();
            }
        });
        baseToggleViewModel.getIcon().observe(lifecycleOwner, new Observer() { // from class: com.android.systemui.qs.bar.soundcraft.view.audioeffect.AudioEffectToggleItemView.3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                AudioEffectToggleItemView audioEffectToggleItemView = AudioEffectToggleItemView.this;
                audioEffectToggleItemView.binding.icon.setBackground(audioEffectToggleItemView.context.getDrawable(((Integer) obj).intValue()));
            }
        });
        baseToggleViewModel.name.observe(lifecycleOwner, new Observer() { // from class: com.android.systemui.qs.bar.soundcraft.view.audioeffect.AudioEffectToggleItemView.4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                AudioEffectToggleItemView.this.binding.name.setText((String) obj);
            }
        });
        baseToggleViewModel.isSelected.observe(lifecycleOwner, new Observer() { // from class: com.android.systemui.qs.bar.soundcraft.view.audioeffect.AudioEffectToggleItemView.5
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                Boolean bool = (Boolean) obj;
                AudioEffectToggleItemView audioEffectToggleItemView = AudioEffectToggleItemView.this;
                if (Intrinsics.areEqual(bool, Boolean.valueOf(audioEffectToggleItemView.binding.f832switch.isChecked()))) {
                    return;
                }
                audioEffectToggleItemView.binding.f832switch.setChecked(bool.booleanValue());
            }
        });
        baseToggleViewModel.getIcon().observe(lifecycleOwner, new Observer() { // from class: com.android.systemui.qs.bar.soundcraft.view.audioeffect.AudioEffectToggleItemView.6
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                Object failure;
                Integer num = (Integer) obj;
                AudioEffectToggleItemView audioEffectToggleItemView = AudioEffectToggleItemView.this;
                ImageView imageView = audioEffectToggleItemView.binding.icon;
                try {
                    int i2 = Result.$r8$clinit;
                    failure = audioEffectToggleItemView.context.getDrawable(num.intValue());
                } catch (Throwable th) {
                    int i3 = Result.$r8$clinit;
                    failure = new Result.Failure(th);
                }
                if (failure instanceof Result.Failure) {
                    failure = null;
                }
                imageView.setImageDrawable((Drawable) failure);
            }
        });
        baseToggleViewModel.notifyChange();
    }

    @Override // com.android.systemui.qs.bar.soundcraft.view.audioeffect.BaseAudioEffectItemView
    public final ViewGroup getRootView() {
        return this.binding.root;
    }

    @Override // com.android.systemui.qs.bar.soundcraft.view.audioeffect.BaseAudioEffectItemView
    public final void update() {
        this.viewModel.notifyChange();
    }
}
