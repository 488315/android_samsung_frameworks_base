package com.android.systemui.audio.soundcraft.view.noisecontrol;

import android.content.Context;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import com.android.systemui.R;
import com.android.systemui.audio.soundcraft.utils.SoundCraftSALogging;
import com.android.systemui.audio.soundcraft.viewbinding.SoundCraftViewBindingFactory;
import com.android.systemui.audio.soundcraft.viewbinding.noisecontrol.LevelSeekBarViewBinding;
import com.android.systemui.audio.soundcraft.viewmodel.buds.noisecontrol.NoiseControlLevelViewModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class NoiseControlLevelView {
    public final LevelSeekBarViewBinding binding;
    public final LifecycleOwner lifecycleOwner;
    public final NoiseControlLevelViewModel viewModel;

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

    public NoiseControlLevelView(Context context, LifecycleOwner lifecycleOwner, NoiseControlLevelViewModel noiseControlLevelViewModel) {
        final int i = 1;
        final int i2 = 0;
        this.lifecycleOwner = lifecycleOwner;
        this.viewModel = noiseControlLevelViewModel;
        int i3 = SoundCraftViewBindingFactory.$r8$clinit;
        LevelSeekBarViewBinding levelSeekBarViewBinding = new LevelSeekBarViewBinding(LayoutInflater.from(context).inflate(R.layout.soundcraft_level_view, (ViewGroup) null, false));
        this.binding = levelSeekBarViewBinding;
        levelSeekBarViewBinding.title.setText(noiseControlLevelViewModel.getTitle());
        levelSeekBarViewBinding.seekbar.setMax(4);
        levelSeekBarViewBinding.seekbar.semSetMin(0);
        levelSeekBarViewBinding.seekbar.semSetMode(5);
        SeekBar seekBar = levelSeekBarViewBinding.seekbar;
        MutableLiveData mutableLiveData = noiseControlLevelViewModel.level;
        Integer num = (Integer) mutableLiveData.getValue();
        seekBar.setProgress(num != null ? num.intValue() : 1);
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
        mutableLiveData.observe(lifecycleOwner, new NoiseControlLevelView$sam$androidx_lifecycle_Observer$0(new Function1(this) { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlLevelView$$ExternalSyntheticLambda0
            public final /* synthetic */ NoiseControlLevelView f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                Integer num2 = (Integer) obj;
                switch (i2) {
                    case 0:
                        NoiseControlLevelView noiseControlLevelView = this.f$0;
                        int progress = noiseControlLevelView.binding.seekbar.getProgress();
                        if (num2 == null || progress != num2.intValue()) {
                            noiseControlLevelView.binding.seekbar.setProgress(num2.intValue());
                        }
                        break;
                    default:
                        NoiseControlLevelView noiseControlLevelView2 = this.f$0;
                        int max = noiseControlLevelView2.binding.seekbar.getMax();
                        if (num2 == null || max != num2.intValue()) {
                            noiseControlLevelView2.binding.seekbar.setMax(num2.intValue());
                        }
                        break;
                }
                return Unit.INSTANCE;
            }
        }));
        noiseControlLevelViewModel.levelMax.observe(lifecycleOwner, new NoiseControlLevelView$sam$androidx_lifecycle_Observer$0(new Function1(this) { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlLevelView$$ExternalSyntheticLambda0
            public final /* synthetic */ NoiseControlLevelView f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                Integer num2 = (Integer) obj;
                switch (i) {
                    case 0:
                        NoiseControlLevelView noiseControlLevelView = this.f$0;
                        int progress = noiseControlLevelView.binding.seekbar.getProgress();
                        if (num2 == null || progress != num2.intValue()) {
                            noiseControlLevelView.binding.seekbar.setProgress(num2.intValue());
                        }
                        break;
                    default:
                        NoiseControlLevelView noiseControlLevelView2 = this.f$0;
                        int max = noiseControlLevelView2.binding.seekbar.getMax();
                        if (num2 == null || max != num2.intValue()) {
                            noiseControlLevelView2.binding.seekbar.setMax(num2.intValue());
                        }
                        break;
                }
                return Unit.INSTANCE;
            }
        }));
    }

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
        public final void onStopTrackingTouch(SeekBar seekBar) {
            SoundCraftSALogging.sendEventLog$default(SoundCraftSALogging.INSTANCE, SoundCraftSALogging.ScreenId.EID_BUDS_DETAIL_SETTING, NoiseControlLevelView.this.viewModel.getSALoggingEvent(), String.valueOf(seekBar.getProgress()), 8);
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public final void onStartTrackingTouch(SeekBar seekBar) {
        }
    }
}
