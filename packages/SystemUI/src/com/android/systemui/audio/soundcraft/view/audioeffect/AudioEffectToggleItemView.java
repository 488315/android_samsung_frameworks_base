package com.android.systemui.audio.soundcraft.view.audioeffect;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.SwitchCompat;
import androidx.lifecycle.LifecycleOwner;
import com.android.systemui.R;
import com.android.systemui.audio.soundcraft.viewbinding.SoundCraftViewBindingFactory;
import com.android.systemui.audio.soundcraft.viewbinding.audioeffect.AudioEffectToggleItemViewBinding;
import com.android.systemui.audio.soundcraft.viewmodel.common.base.BaseToggleViewModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class AudioEffectToggleItemView extends BaseAudioEffectItemView {
    public final AudioEffectToggleItemViewBinding binding;
    public final LifecycleOwner lifecycleOwner;
    public final ViewGroup parent;
    public final BaseToggleViewModel viewModel;

    public AudioEffectToggleItemView(Context context, LifecycleOwner lifecycleOwner, BaseToggleViewModel baseToggleViewModel, ViewGroup viewGroup) {
        final int i = 0;
        this.lifecycleOwner = lifecycleOwner;
        this.viewModel = baseToggleViewModel;
        this.parent = viewGroup;
        int i2 = SoundCraftViewBindingFactory.$r8$clinit;
        AudioEffectToggleItemViewBinding audioEffectToggleItemViewBinding = new AudioEffectToggleItemViewBinding(LayoutInflater.from(context).inflate(R.layout.soundcraft_audio_effect_toggle_item, viewGroup, false));
        this.binding = audioEffectToggleItemViewBinding;
        audioEffectToggleItemViewBinding.root.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectToggleItemView.1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AudioEffectToggleItemView.this.viewModel.onClick();
            }
        });
        audioEffectToggleItemViewBinding.f26switch.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectToggleItemView.2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AudioEffectToggleItemView.this.viewModel.onClick();
            }
        });
        baseToggleViewModel.name.observe(lifecycleOwner, new AudioEffectToggleItemView$sam$androidx_lifecycle_Observer$0(new Function1(this) { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectToggleItemView$$ExternalSyntheticLambda0
            public final /* synthetic */ AudioEffectToggleItemView f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) throws Resources.NotFoundException {
                switch (i) {
                    case 0:
                        this.f$0.binding.name.setText((String) obj);
                        break;
                    case 1:
                        Boolean bool = (Boolean) obj;
                        AudioEffectToggleItemView audioEffectToggleItemView = this.f$0;
                        if (!Intrinsics.areEqual(bool, Boolean.valueOf(audioEffectToggleItemView.binding.f26switch.isChecked()))) {
                            audioEffectToggleItemView.binding.f26switch.setChecked(bool.booleanValue());
                        }
                        break;
                    case 2:
                        Boolean bool2 = (Boolean) obj;
                        AudioEffectToggleItemView audioEffectToggleItemView2 = this.f$0;
                        if (!Intrinsics.areEqual(bool2, Boolean.valueOf(audioEffectToggleItemView2.binding.f26switch.isChecked()))) {
                            SwitchCompat switchCompat = audioEffectToggleItemView2.binding.f26switch;
                            bool2.getClass();
                            switchCompat.setCheckedWithoutAnimation(bool2.booleanValue());
                        }
                        break;
                    default:
                        this.f$0.binding.status.setText((String) obj);
                        break;
                }
                return Unit.INSTANCE;
            }
        }));
        final int i3 = 1;
        baseToggleViewModel.isSelected.observe(lifecycleOwner, new AudioEffectToggleItemView$sam$androidx_lifecycle_Observer$0(new Function1(this) { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectToggleItemView$$ExternalSyntheticLambda0
            public final /* synthetic */ AudioEffectToggleItemView f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) throws Resources.NotFoundException {
                switch (i3) {
                    case 0:
                        this.f$0.binding.name.setText((String) obj);
                        break;
                    case 1:
                        Boolean bool = (Boolean) obj;
                        AudioEffectToggleItemView audioEffectToggleItemView = this.f$0;
                        if (!Intrinsics.areEqual(bool, Boolean.valueOf(audioEffectToggleItemView.binding.f26switch.isChecked()))) {
                            audioEffectToggleItemView.binding.f26switch.setChecked(bool.booleanValue());
                        }
                        break;
                    case 2:
                        Boolean bool2 = (Boolean) obj;
                        AudioEffectToggleItemView audioEffectToggleItemView2 = this.f$0;
                        if (!Intrinsics.areEqual(bool2, Boolean.valueOf(audioEffectToggleItemView2.binding.f26switch.isChecked()))) {
                            SwitchCompat switchCompat = audioEffectToggleItemView2.binding.f26switch;
                            bool2.getClass();
                            switchCompat.setCheckedWithoutAnimation(bool2.booleanValue());
                        }
                        break;
                    default:
                        this.f$0.binding.status.setText((String) obj);
                        break;
                }
                return Unit.INSTANCE;
            }
        }));
        final int i4 = 2;
        baseToggleViewModel.isChecked.observe(lifecycleOwner, new AudioEffectToggleItemView$sam$androidx_lifecycle_Observer$0(new Function1(this) { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectToggleItemView$$ExternalSyntheticLambda0
            public final /* synthetic */ AudioEffectToggleItemView f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) throws Resources.NotFoundException {
                switch (i4) {
                    case 0:
                        this.f$0.binding.name.setText((String) obj);
                        break;
                    case 1:
                        Boolean bool = (Boolean) obj;
                        AudioEffectToggleItemView audioEffectToggleItemView = this.f$0;
                        if (!Intrinsics.areEqual(bool, Boolean.valueOf(audioEffectToggleItemView.binding.f26switch.isChecked()))) {
                            audioEffectToggleItemView.binding.f26switch.setChecked(bool.booleanValue());
                        }
                        break;
                    case 2:
                        Boolean bool2 = (Boolean) obj;
                        AudioEffectToggleItemView audioEffectToggleItemView2 = this.f$0;
                        if (!Intrinsics.areEqual(bool2, Boolean.valueOf(audioEffectToggleItemView2.binding.f26switch.isChecked()))) {
                            SwitchCompat switchCompat = audioEffectToggleItemView2.binding.f26switch;
                            bool2.getClass();
                            switchCompat.setCheckedWithoutAnimation(bool2.booleanValue());
                        }
                        break;
                    default:
                        this.f$0.binding.status.setText((String) obj);
                        break;
                }
                return Unit.INSTANCE;
            }
        }));
        final int i5 = 3;
        baseToggleViewModel.subText.observe(lifecycleOwner, new AudioEffectToggleItemView$sam$androidx_lifecycle_Observer$0(new Function1(this) { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectToggleItemView$$ExternalSyntheticLambda0
            public final /* synthetic */ AudioEffectToggleItemView f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) throws Resources.NotFoundException {
                switch (i5) {
                    case 0:
                        this.f$0.binding.name.setText((String) obj);
                        break;
                    case 1:
                        Boolean bool = (Boolean) obj;
                        AudioEffectToggleItemView audioEffectToggleItemView = this.f$0;
                        if (!Intrinsics.areEqual(bool, Boolean.valueOf(audioEffectToggleItemView.binding.f26switch.isChecked()))) {
                            audioEffectToggleItemView.binding.f26switch.setChecked(bool.booleanValue());
                        }
                        break;
                    case 2:
                        Boolean bool2 = (Boolean) obj;
                        AudioEffectToggleItemView audioEffectToggleItemView2 = this.f$0;
                        if (!Intrinsics.areEqual(bool2, Boolean.valueOf(audioEffectToggleItemView2.binding.f26switch.isChecked()))) {
                            SwitchCompat switchCompat = audioEffectToggleItemView2.binding.f26switch;
                            bool2.getClass();
                            switchCompat.setCheckedWithoutAnimation(bool2.booleanValue());
                        }
                        break;
                    default:
                        this.f$0.binding.status.setText((String) obj);
                        break;
                }
                return Unit.INSTANCE;
            }
        }));
        baseToggleViewModel.notifyChange();
    }

    @Override // com.android.systemui.audio.soundcraft.view.audioeffect.BaseAudioEffectItemView
    public final void enable(boolean z) {
        AudioEffectToggleItemViewBinding audioEffectToggleItemViewBinding = this.binding;
        audioEffectToggleItemViewBinding.root.setClickable(z);
        audioEffectToggleItemViewBinding.root.setEnabled(z);
        SwitchCompat switchCompat = audioEffectToggleItemViewBinding.f26switch;
        switchCompat.setClickable(z);
        switchCompat.setEnabled(z);
        this.viewModel.enable(z);
        if (z) {
            audioEffectToggleItemViewBinding.status.setVisibility(8);
        } else if (audioEffectToggleItemViewBinding.status.getText().length() > 0) {
            audioEffectToggleItemViewBinding.status.setVisibility(0);
        }
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
