package com.android.systemui.audio.soundcraft.view.noisecontrol;

import android.content.Context;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import com.android.systemui.R;
import com.android.systemui.audio.soundcraft.viewbinding.SoundCraftViewBindingFactory;
import com.android.systemui.audio.soundcraft.viewbinding.noisecontrol.LevelSeekBarViewBinding;
import com.android.systemui.audio.soundcraft.viewmodel.buds.noisecontrol.NoiseControlLevelViewModel;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class NoiseControlLevelView {
    public final LevelSeekBarViewBinding binding;
    public final LifecycleOwner lifecycleOwner;
    public final NoiseControlLevelViewModel viewModel;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    public NoiseControlLevelView(Context context, LifecycleOwner lifecycleOwner, NoiseControlLevelViewModel noiseControlLevelViewModel) {
        this.lifecycleOwner = lifecycleOwner;
        this.viewModel = noiseControlLevelViewModel;
        int i = SoundCraftViewBindingFactory.$r8$clinit;
        LevelSeekBarViewBinding levelSeekBarViewBinding = new LevelSeekBarViewBinding(LayoutInflater.from(context).inflate(R.layout.soundcraft_level_view, (ViewGroup) null, false));
        this.binding = levelSeekBarViewBinding;
        levelSeekBarViewBinding.title.setText(noiseControlLevelViewModel.getTitle());
        levelSeekBarViewBinding.seekbar.setMax(4);
        levelSeekBarViewBinding.seekbar.semSetMin(0);
        levelSeekBarViewBinding.seekbar.semSetMode(5);
        SeekBar seekBar = levelSeekBarViewBinding.seekbar;
        MutableLiveData mutableLiveData = noiseControlLevelViewModel.level;
        Integer num = (Integer) mutableLiveData.getValue();
        seekBar.setProgress(num == null ? 1 : num.intValue());
        levelSeekBarViewBinding.seekbar.setOnSeekBarChangeListener(new SeekbarChangeListener());
        levelSeekBarViewBinding.plusButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlLevelView.1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                NoiseControlLevelView noiseControlLevelView = NoiseControlLevelView.this;
                noiseControlLevelView.viewModel.progressChange(noiseControlLevelView.binding.seekbar.getProgress() + 1);
            }
        });
        levelSeekBarViewBinding.minusButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlLevelView.2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                NoiseControlLevelView.this.viewModel.progressChange(r0.binding.seekbar.getProgress() - 1);
            }
        });
        mutableLiveData.observe(lifecycleOwner, new Observer() { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlLevelView.3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                Integer num2 = (Integer) obj;
                NoiseControlLevelView noiseControlLevelView = NoiseControlLevelView.this;
                int progress = noiseControlLevelView.binding.seekbar.getProgress();
                if (num2 != null && progress == num2.intValue()) {
                    return;
                }
                SeekBar seekBar2 = noiseControlLevelView.binding.seekbar;
                Intrinsics.checkNotNull(num2);
                seekBar2.setProgress(num2.intValue());
            }
        });
        noiseControlLevelViewModel.levelMax.observe(lifecycleOwner, new Observer() { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlLevelView.4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                Integer num2 = (Integer) obj;
                NoiseControlLevelView noiseControlLevelView = NoiseControlLevelView.this;
                int max = noiseControlLevelView.binding.seekbar.getMax();
                if (num2 != null && max == num2.intValue()) {
                    return;
                }
                SeekBar seekBar2 = noiseControlLevelView.binding.seekbar;
                Intrinsics.checkNotNull(num2);
                seekBar2.setMax(num2.intValue());
            }
        });
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class SeekbarChangeListener implements SeekBar.OnSeekBarChangeListener {
        public SeekbarChangeListener() {
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public final void onProgressChanged(SeekBar seekBar, int i, boolean z) {
            if (z) {
                NoiseControlLevelView.this.viewModel.progressChange(i);
            }
            NoiseControlLevelView.this.binding.seekbar.performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(41));
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public final void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public final void onStopTrackingTouch(SeekBar seekBar) {
        }
    }
}
