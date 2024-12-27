package com.android.systemui.audio.soundcraft.view.noisecontrol;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.SwitchCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import com.android.systemui.R;
import com.android.systemui.audio.soundcraft.viewbinding.SoundCraftViewBindingFactory;
import com.android.systemui.audio.soundcraft.viewbinding.noisecontrol.NoiseCancelingSwitchBarViewBinding;
import com.android.systemui.audio.soundcraft.viewmodel.buds.noisecontrol.NoiseCancelingSwitchBarViewModel;
import kotlin.jvm.internal.Intrinsics;

public final class NoiseCancelingSwitchBar {
    public final NoiseCancelingSwitchBarViewBinding binding;
    public final LifecycleOwner lifecycleOwner;
    public final NoiseCancelingSwitchBarViewModel viewModel;

    public NoiseCancelingSwitchBar(Context context, LifecycleOwner lifecycleOwner, NoiseCancelingSwitchBarViewModel noiseCancelingSwitchBarViewModel) {
        this.lifecycleOwner = lifecycleOwner;
        this.viewModel = noiseCancelingSwitchBarViewModel;
        int i = SoundCraftViewBindingFactory.$r8$clinit;
        NoiseCancelingSwitchBarViewBinding noiseCancelingSwitchBarViewBinding = new NoiseCancelingSwitchBarViewBinding(LayoutInflater.from(context).inflate(R.layout.soundcraft_noise_control_switch_bar_view, (ViewGroup) null, false));
        this.binding = noiseCancelingSwitchBarViewBinding;
        noiseCancelingSwitchBarViewBinding.root.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseCancelingSwitchBar.1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                if (NoiseCancelingSwitchBar.this.viewModel.checkWearingOn()) {
                    NoiseCancelingSwitchBar.this.viewModel.onClick();
                }
            }
        });
        noiseCancelingSwitchBarViewBinding.f24switch.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseCancelingSwitchBar.2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                if (NoiseCancelingSwitchBar.this.viewModel.checkWearingOn()) {
                    NoiseCancelingSwitchBar.this.viewModel.onClick();
                } else {
                    NoiseCancelingSwitchBar.this.binding.f24switch.setChecked(false);
                }
            }
        });
        noiseCancelingSwitchBarViewBinding.icon.setColorFilter(context.getResources().getColor(R.color.soundcraft_selected_icon_color, null));
        noiseCancelingSwitchBarViewModel.isSelected.observe(lifecycleOwner, new Observer() { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseCancelingSwitchBar.3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                Boolean bool = (Boolean) obj;
                NoiseCancelingSwitchBar noiseCancelingSwitchBar = NoiseCancelingSwitchBar.this;
                if (Intrinsics.areEqual(bool, Boolean.valueOf(noiseCancelingSwitchBar.binding.f24switch.isChecked()))) {
                    return;
                }
                SwitchCompat switchCompat = noiseCancelingSwitchBar.binding.f24switch;
                Intrinsics.checkNotNull(bool);
                switchCompat.setChecked(bool.booleanValue());
            }
        });
        noiseCancelingSwitchBarViewModel.notifyChange();
    }
}
