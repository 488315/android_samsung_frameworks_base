package com.android.systemui.qs.bar.soundcraft.view.noisecontrol;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.SeekBar;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import com.android.systemui.R;
import com.android.systemui.qs.bar.soundcraft.model.ModelProvider;
import com.android.systemui.qs.bar.soundcraft.viewbinding.NoiseCancelingLevelViewBinding;
import com.android.systemui.qs.bar.soundcraft.viewbinding.SoundCraftViewBindingFactory;
import com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseCancelingLevelViewModel;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NoiseCancelingLevelView {
    public final NoiseCancelingLevelViewBinding binding;
    public final LifecycleOwner lifecycleOwner;
    public final NoiseCancelingLevelViewModel viewModel;

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

    public NoiseCancelingLevelView(Context context, LifecycleOwner lifecycleOwner, NoiseCancelingLevelViewModel noiseCancelingLevelViewModel) {
        this.lifecycleOwner = lifecycleOwner;
        this.viewModel = noiseCancelingLevelViewModel;
        int i = SoundCraftViewBindingFactory.$r8$clinit;
        NoiseCancelingLevelViewBinding noiseCancelingLevelViewBinding = new NoiseCancelingLevelViewBinding(LayoutInflater.from(context).inflate(R.layout.soundcraft_noise_control_level_view, (ViewGroup) null, false));
        this.binding = noiseCancelingLevelViewBinding;
        SeekBar seekBar = noiseCancelingLevelViewBinding.noiseCancelingSeekbar;
        seekBar.setMax(5);
        seekBar.semSetMin(1);
        MutableLiveData mutableLiveData = noiseCancelingLevelViewModel.level;
        Integer num = (Integer) mutableLiveData.getValue();
        seekBar.setProgress(num != null ? num.intValue() : 1);
        seekBar.setOnSeekBarChangeListener(new SeekbarChangeListener());
        mutableLiveData.observe(lifecycleOwner, new Observer() { // from class: com.android.systemui.qs.bar.soundcraft.view.noisecontrol.NoiseCancelingLevelView.1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                Integer num2 = (Integer) obj;
                NoiseCancelingLevelView noiseCancelingLevelView = NoiseCancelingLevelView.this;
                int progress = noiseCancelingLevelView.binding.noiseCancelingSeekbar.getProgress();
                if (num2 != null && progress == num2.intValue()) {
                    return;
                }
                noiseCancelingLevelView.binding.noiseCancelingSeekbar.setProgress(num2.intValue());
            }
        });
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SeekbarChangeListener implements SeekBar.OnSeekBarChangeListener {
        public SeekbarChangeListener() {
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public final void onProgressChanged(SeekBar seekBar, int i, boolean z) {
            if (z) {
                NoiseCancelingLevelViewModel noiseCancelingLevelViewModel = NoiseCancelingLevelView.this.viewModel;
                ModelProvider modelProvider = noiseCancelingLevelViewModel.modelProvider;
                Integer noiseCancelingLevel = modelProvider.budsInfo.getNoiseCancelingLevel();
                if (noiseCancelingLevel != null) {
                    noiseCancelingLevel.intValue();
                    modelProvider.budsInfo.setNoiseCancelingLevel(Integer.valueOf(i));
                    noiseCancelingLevelViewModel.wearableManager.updateBudsInfo(modelProvider.budsInfo);
                    noiseCancelingLevelViewModel.level.setValue(Integer.valueOf(i));
                }
            }
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public final void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public final void onStopTrackingTouch(SeekBar seekBar) {
        }
    }
}
