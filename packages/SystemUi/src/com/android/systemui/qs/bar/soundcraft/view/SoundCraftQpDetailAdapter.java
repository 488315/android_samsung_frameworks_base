package com.android.systemui.qs.bar.soundcraft.view;

import android.app.ActivityThread;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothUuid;
import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import com.android.settingslib.bluetooth.A2dpProfile;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.bluetooth.LocalBluetoothProfileManager;
import com.android.systemui.R;
import com.android.systemui.qs.FullScreenDetailAdapter;
import com.android.systemui.qs.SecQSPanelController;
import com.android.systemui.qs.SecQSPanelControllerBase;
import com.android.systemui.qs.bar.soundcraft.interfaces.routine.manager.RoutineManager;
import com.android.systemui.qs.bar.soundcraft.di.vm.SoundCraftViewModelProvider;
import com.android.systemui.qs.bar.soundcraft.view.actionbar.SoundCraftActionBarView;
import com.android.systemui.qs.bar.soundcraft.view.audioeffect.AudioEffectBoxView;
import com.android.systemui.qs.bar.soundcraft.view.audioeffect.AudioEffectHeaderView;
import com.android.systemui.qs.bar.soundcraft.view.audioeffect.BaseAudioEffectItemView;
import com.android.systemui.qs.bar.soundcraft.view.noisecontrol.NoiseControlBoxView;
import com.android.systemui.qs.bar.soundcraft.view.routine.RoutineTestView;
import com.android.systemui.qs.bar.soundcraft.view.wearable.WearableLinkBoxView;
import com.android.systemui.qs.bar.soundcraft.viewbinding.AudioEffectBoxViewBinding;
import com.android.systemui.qs.bar.soundcraft.viewbinding.AudioEffectHeaderViewBinding;
import com.android.systemui.qs.bar.soundcraft.viewbinding.NoiseControlBoxViewBinding;
import com.android.systemui.qs.bar.soundcraft.viewbinding.RoutineTestViewBinding;
import com.android.systemui.qs.bar.soundcraft.viewbinding.SoundCraftActionBarBinding;
import com.android.systemui.qs.bar.soundcraft.viewbinding.SoundCraftViewBinding;
import com.android.systemui.qs.bar.soundcraft.viewbinding.SoundCraftViewBindingFactory;
import com.android.systemui.qs.bar.soundcraft.viewbinding.WearableLinkBoxViewBinding;
import com.android.systemui.qs.bar.soundcraft.viewmodel.SoundCraftViewModel;
import com.android.systemui.qs.bar.soundcraft.viewmodel.actionbar.SoundCraftActionBarViewModel;
import com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectBoxViewModel;
import com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectHeaderViewModel;
import com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.EqualizerViewModel;
import com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.SpatialAudioViewModel;
import com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.VoiceBoostViewModel;
import com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.VolumeNormalizationViewModel;
import com.android.systemui.qs.bar.soundcraft.viewmodel.base.BaseViewModel;
import com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.ActiveNoiseCancelingViewModel;
import com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.AdaptiveViewModel;
import com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.AmbientSoundViewModel;
import com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseCancelingLevelViewModel;
import com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseCancelingSwitchBarViewModel;
import com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseControlBoxViewModel;
import com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseControlEffectBoxViewModel;
import com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseControlOffViewModel;
import com.android.systemui.qs.bar.soundcraft.viewmodel.routine.RoutineTestViewModel;
import com.android.systemui.qs.bar.soundcraft.viewmodel.wearable.WearableLinkBoxViewModel;
import com.android.systemui.plugins.qs.DetailAdapter;
import com.android.systemui.volume.util.ViewVisibilityUtil;
import com.samsung.android.knox.restriction.PhoneRestrictionPolicy;
import com.samsung.android.settingslib.bluetooth.BluetoothRestoredDevice;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.jvm.internal.ClassReference;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SoundCraftQpDetailAdapter extends FullScreenDetailAdapter {
    public SoundCraftViewBinding viewBinding;
    public SoundCraftViewModelProvider viewModelProvider;

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

    public SoundCraftQpDetailAdapter(Context context) {
        Log.d("SoundCraft.QpDetailAdapter", "init");
    }

    public final void bindViewModel(SoundCraftViewBinding soundCraftViewBinding) {
        SoundCraftViewModel soundCraftViewModel;
        SoundCraftActionBarViewModel soundCraftActionBarViewModel;
        AudioEffectBoxViewModel audioEffectBoxViewModel;
        AudioEffectHeaderViewModel audioEffectHeaderViewModel;
        WearableLinkBoxViewModel wearableLinkBoxViewModel;
        RoutineTestViewModel routineTestViewModel;
        Log.d("SoundCraft.QpDetailAdapter", "bindViewModel");
        final SoundCraftDetailPageView soundCraftDetailPageView = soundCraftViewBinding.root;
        soundCraftDetailPageView.viewBinding = soundCraftViewBinding;
        SoundCraftViewModelProvider viewModelProvider = getViewModelProvider();
        ClassReference orCreateKotlinClass = Reflection.getOrCreateKotlinClass(SoundCraftViewModel.class);
        if (Intrinsics.areEqual(orCreateKotlinClass, Reflection.getOrCreateKotlinClass(SoundCraftViewModel.class))) {
            soundCraftViewModel = viewModelProvider.craftViewModel;
            if (soundCraftViewModel == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.SoundCraftViewModel");
            }
        } else if (Intrinsics.areEqual(orCreateKotlinClass, Reflection.getOrCreateKotlinClass(SoundCraftActionBarViewModel.class))) {
            BaseViewModel baseViewModel = viewModelProvider.actionBarViewModel;
            if (baseViewModel == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.SoundCraftViewModel");
            }
            soundCraftViewModel = (SoundCraftViewModel) baseViewModel;
        } else if (Intrinsics.areEqual(orCreateKotlinClass, Reflection.getOrCreateKotlinClass(NoiseControlBoxViewModel.class))) {
            BaseViewModel baseViewModel2 = viewModelProvider.noiseControlBoxViewModel;
            if (baseViewModel2 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.SoundCraftViewModel");
            }
            soundCraftViewModel = (SoundCraftViewModel) baseViewModel2;
        } else if (Intrinsics.areEqual(orCreateKotlinClass, Reflection.getOrCreateKotlinClass(AudioEffectBoxViewModel.class))) {
            BaseViewModel baseViewModel3 = viewModelProvider.audioEffectBoxViewModel;
            if (baseViewModel3 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.SoundCraftViewModel");
            }
            soundCraftViewModel = (SoundCraftViewModel) baseViewModel3;
        } else if (Intrinsics.areEqual(orCreateKotlinClass, Reflection.getOrCreateKotlinClass(AudioEffectHeaderViewModel.class))) {
            BaseViewModel baseViewModel4 = viewModelProvider.audioEffectHeaderViewModel;
            if (baseViewModel4 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.SoundCraftViewModel");
            }
            soundCraftViewModel = (SoundCraftViewModel) baseViewModel4;
        } else if (Intrinsics.areEqual(orCreateKotlinClass, Reflection.getOrCreateKotlinClass(WearableLinkBoxViewModel.class))) {
            BaseViewModel baseViewModel5 = viewModelProvider.wearableLinkBoxViewModel;
            if (baseViewModel5 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.SoundCraftViewModel");
            }
            soundCraftViewModel = (SoundCraftViewModel) baseViewModel5;
        } else if (Intrinsics.areEqual(orCreateKotlinClass, Reflection.getOrCreateKotlinClass(SpatialAudioViewModel.class))) {
            BaseViewModel baseViewModel6 = viewModelProvider.spatialAudioViewModel;
            if (baseViewModel6 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.SoundCraftViewModel");
            }
            soundCraftViewModel = (SoundCraftViewModel) baseViewModel6;
        } else if (Intrinsics.areEqual(orCreateKotlinClass, Reflection.getOrCreateKotlinClass(EqualizerViewModel.class))) {
            BaseViewModel baseViewModel7 = viewModelProvider.equalizerViewModel;
            if (baseViewModel7 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.SoundCraftViewModel");
            }
            soundCraftViewModel = (SoundCraftViewModel) baseViewModel7;
        } else if (Intrinsics.areEqual(orCreateKotlinClass, Reflection.getOrCreateKotlinClass(VoiceBoostViewModel.class))) {
            BaseViewModel baseViewModel8 = viewModelProvider.voiceBoostViewModel;
            if (baseViewModel8 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.SoundCraftViewModel");
            }
            soundCraftViewModel = (SoundCraftViewModel) baseViewModel8;
        } else if (Intrinsics.areEqual(orCreateKotlinClass, Reflection.getOrCreateKotlinClass(VolumeNormalizationViewModel.class))) {
            BaseViewModel baseViewModel9 = viewModelProvider.volumeNormalizationViewModel;
            if (baseViewModel9 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.SoundCraftViewModel");
            }
            soundCraftViewModel = (SoundCraftViewModel) baseViewModel9;
        } else if (Intrinsics.areEqual(orCreateKotlinClass, Reflection.getOrCreateKotlinClass(ActiveNoiseCancelingViewModel.class))) {
            BaseViewModel baseViewModel10 = viewModelProvider.activeNoiseCancelingViewModel;
            if (baseViewModel10 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.SoundCraftViewModel");
            }
            soundCraftViewModel = (SoundCraftViewModel) baseViewModel10;
        } else if (Intrinsics.areEqual(orCreateKotlinClass, Reflection.getOrCreateKotlinClass(AdaptiveViewModel.class))) {
            BaseViewModel baseViewModel11 = viewModelProvider.adaptiveViewModel;
            if (baseViewModel11 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.SoundCraftViewModel");
            }
            soundCraftViewModel = (SoundCraftViewModel) baseViewModel11;
        } else if (Intrinsics.areEqual(orCreateKotlinClass, Reflection.getOrCreateKotlinClass(AmbientSoundViewModel.class))) {
            BaseViewModel baseViewModel12 = viewModelProvider.ambientSoundViewModel;
            if (baseViewModel12 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.SoundCraftViewModel");
            }
            soundCraftViewModel = (SoundCraftViewModel) baseViewModel12;
        } else if (Intrinsics.areEqual(orCreateKotlinClass, Reflection.getOrCreateKotlinClass(NoiseCancelingLevelViewModel.class))) {
            BaseViewModel baseViewModel13 = viewModelProvider.noiseCancelingViewModel;
            if (baseViewModel13 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.SoundCraftViewModel");
            }
            soundCraftViewModel = (SoundCraftViewModel) baseViewModel13;
        } else if (Intrinsics.areEqual(orCreateKotlinClass, Reflection.getOrCreateKotlinClass(NoiseControlEffectBoxViewModel.class))) {
            BaseViewModel baseViewModel14 = viewModelProvider.noiseControlEffectBoxViewModel;
            if (baseViewModel14 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.SoundCraftViewModel");
            }
            soundCraftViewModel = (SoundCraftViewModel) baseViewModel14;
        } else if (Intrinsics.areEqual(orCreateKotlinClass, Reflection.getOrCreateKotlinClass(NoiseControlOffViewModel.class))) {
            BaseViewModel baseViewModel15 = viewModelProvider.noiseControlOffViewModel;
            if (baseViewModel15 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.SoundCraftViewModel");
            }
            soundCraftViewModel = (SoundCraftViewModel) baseViewModel15;
        } else if (Intrinsics.areEqual(orCreateKotlinClass, Reflection.getOrCreateKotlinClass(NoiseCancelingSwitchBarViewModel.class))) {
            BaseViewModel baseViewModel16 = viewModelProvider.noiseCancelingSwitchBarViewModel;
            if (baseViewModel16 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.SoundCraftViewModel");
            }
            soundCraftViewModel = (SoundCraftViewModel) baseViewModel16;
        } else {
            if (!Intrinsics.areEqual(orCreateKotlinClass, Reflection.getOrCreateKotlinClass(RoutineTestViewModel.class))) {
                throw new RuntimeException();
            }
            BaseViewModel baseViewModel17 = viewModelProvider.routineTestViewModel;
            if (baseViewModel17 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.SoundCraftViewModel");
            }
            soundCraftViewModel = (SoundCraftViewModel) baseViewModel17;
        }
        Log.d("SoundCraft.DetailPageView", "bindViewModel : viewModel=" + soundCraftViewModel);
        soundCraftViewModel.showNoiseControlBox.observe(soundCraftDetailPageView, new Observer() { // from class: com.android.systemui.qs.bar.soundcraft.view.SoundCraftDetailPageView$bindViewModel$1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                Log.d("SoundCraft.DetailPageView", "showNoiseControlBox=" + ((Boolean) obj));
                SoundCraftViewBinding soundCraftViewBinding2 = SoundCraftDetailPageView.this.viewBinding;
                if (soundCraftViewBinding2 == null) {
                    soundCraftViewBinding2 = null;
                }
                NoiseControlBoxView noiseControlBoxView = soundCraftViewBinding2.noiseControlBox.root;
                NoiseControlBoxViewModel noiseControlBoxViewModel = noiseControlBoxView.noiseControlBoxViewModel;
                if (noiseControlBoxViewModel == null) {
                    noiseControlBoxViewModel = null;
                }
                noiseControlBoxViewModel.notifyChange();
                NoiseControlEffectBoxViewModel noiseControlEffectBoxViewModel = noiseControlBoxView.noiseControlEffectBoxViewModel;
                if (noiseControlEffectBoxViewModel == null) {
                    noiseControlEffectBoxViewModel = null;
                }
                noiseControlEffectBoxViewModel.notifyChange();
                NoiseCancelingLevelViewModel noiseCancelingLevelViewModel = noiseControlBoxView.noiseCancelingLevelViewModel;
                (noiseCancelingLevelViewModel != null ? noiseCancelingLevelViewModel : null).notifyChange();
                noiseControlBoxView.updateLayout();
            }
        });
        soundCraftViewModel.showAudioEffectBox.observe(soundCraftDetailPageView, new Observer() { // from class: com.android.systemui.qs.bar.soundcraft.view.SoundCraftDetailPageView$bindViewModel$2
            /* JADX WARN: Removed duplicated region for block: B:176:0x0988  */
            /* JADX WARN: Removed duplicated region for block: B:188:0x0b72  */
            /* JADX WARN: Removed duplicated region for block: B:191:0x0b74  */
            /* JADX WARN: Removed duplicated region for block: B:29:0x05b0  */
            /* JADX WARN: Removed duplicated region for block: B:41:0x079d  */
            @Override // androidx.lifecycle.Observer
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void onChanged(Object obj) {
                AudioEffectBoxViewModel audioEffectBoxViewModel2;
                AudioEffectBoxViewModel audioEffectBoxViewModel3;
                AudioEffectBoxView audioEffectBoxView;
                Class cls;
                boolean z;
                Boolean bool;
                String str;
                Boolean bool2;
                Boolean bool3;
                boolean z2;
                VolumeNormalizationViewModel volumeNormalizationViewModel;
                VoiceBoostViewModel voiceBoostViewModel;
                EqualizerViewModel equalizerViewModel;
                SpatialAudioViewModel spatialAudioViewModel;
                Log.d("SoundCraft.DetailPageView", "showAudioEffectBox=" + ((Boolean) obj));
                SoundCraftViewBinding soundCraftViewBinding2 = SoundCraftDetailPageView.this.viewBinding;
                if (soundCraftViewBinding2 == null) {
                    soundCraftViewBinding2 = null;
                }
                AudioEffectBoxView audioEffectBoxView2 = soundCraftViewBinding2.audioEffectBox.root;
                SoundCraftViewModelProvider vmProvider = audioEffectBoxView2.getVmProvider();
                ClassReference orCreateKotlinClass2 = Reflection.getOrCreateKotlinClass(AudioEffectBoxViewModel.class);
                if (Intrinsics.areEqual(orCreateKotlinClass2, Reflection.getOrCreateKotlinClass(SoundCraftViewModel.class))) {
                    BaseViewModel baseViewModel18 = vmProvider.craftViewModel;
                    if (baseViewModel18 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectBoxViewModel");
                    }
                    audioEffectBoxViewModel2 = (AudioEffectBoxViewModel) baseViewModel18;
                } else if (Intrinsics.areEqual(orCreateKotlinClass2, Reflection.getOrCreateKotlinClass(SoundCraftActionBarViewModel.class))) {
                    BaseViewModel baseViewModel19 = vmProvider.actionBarViewModel;
                    if (baseViewModel19 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectBoxViewModel");
                    }
                    audioEffectBoxViewModel2 = (AudioEffectBoxViewModel) baseViewModel19;
                } else if (Intrinsics.areEqual(orCreateKotlinClass2, Reflection.getOrCreateKotlinClass(NoiseControlBoxViewModel.class))) {
                    BaseViewModel baseViewModel20 = vmProvider.noiseControlBoxViewModel;
                    if (baseViewModel20 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectBoxViewModel");
                    }
                    audioEffectBoxViewModel2 = (AudioEffectBoxViewModel) baseViewModel20;
                } else if (Intrinsics.areEqual(orCreateKotlinClass2, Reflection.getOrCreateKotlinClass(AudioEffectBoxViewModel.class))) {
                    audioEffectBoxViewModel2 = vmProvider.audioEffectBoxViewModel;
                    if (audioEffectBoxViewModel2 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectBoxViewModel");
                    }
                } else if (Intrinsics.areEqual(orCreateKotlinClass2, Reflection.getOrCreateKotlinClass(AudioEffectHeaderViewModel.class))) {
                    BaseViewModel baseViewModel21 = vmProvider.audioEffectHeaderViewModel;
                    if (baseViewModel21 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectBoxViewModel");
                    }
                    audioEffectBoxViewModel2 = (AudioEffectBoxViewModel) baseViewModel21;
                } else if (Intrinsics.areEqual(orCreateKotlinClass2, Reflection.getOrCreateKotlinClass(WearableLinkBoxViewModel.class))) {
                    BaseViewModel baseViewModel22 = vmProvider.wearableLinkBoxViewModel;
                    if (baseViewModel22 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectBoxViewModel");
                    }
                    audioEffectBoxViewModel2 = (AudioEffectBoxViewModel) baseViewModel22;
                } else if (Intrinsics.areEqual(orCreateKotlinClass2, Reflection.getOrCreateKotlinClass(SpatialAudioViewModel.class))) {
                    BaseViewModel baseViewModel23 = vmProvider.spatialAudioViewModel;
                    if (baseViewModel23 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectBoxViewModel");
                    }
                    audioEffectBoxViewModel2 = (AudioEffectBoxViewModel) baseViewModel23;
                } else if (Intrinsics.areEqual(orCreateKotlinClass2, Reflection.getOrCreateKotlinClass(EqualizerViewModel.class))) {
                    BaseViewModel baseViewModel24 = vmProvider.equalizerViewModel;
                    if (baseViewModel24 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectBoxViewModel");
                    }
                    audioEffectBoxViewModel2 = (AudioEffectBoxViewModel) baseViewModel24;
                } else if (Intrinsics.areEqual(orCreateKotlinClass2, Reflection.getOrCreateKotlinClass(VoiceBoostViewModel.class))) {
                    BaseViewModel baseViewModel25 = vmProvider.voiceBoostViewModel;
                    if (baseViewModel25 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectBoxViewModel");
                    }
                    audioEffectBoxViewModel2 = (AudioEffectBoxViewModel) baseViewModel25;
                } else if (Intrinsics.areEqual(orCreateKotlinClass2, Reflection.getOrCreateKotlinClass(VolumeNormalizationViewModel.class))) {
                    BaseViewModel baseViewModel26 = vmProvider.volumeNormalizationViewModel;
                    if (baseViewModel26 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectBoxViewModel");
                    }
                    audioEffectBoxViewModel2 = (AudioEffectBoxViewModel) baseViewModel26;
                } else if (Intrinsics.areEqual(orCreateKotlinClass2, Reflection.getOrCreateKotlinClass(ActiveNoiseCancelingViewModel.class))) {
                    BaseViewModel baseViewModel27 = vmProvider.activeNoiseCancelingViewModel;
                    if (baseViewModel27 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectBoxViewModel");
                    }
                    audioEffectBoxViewModel2 = (AudioEffectBoxViewModel) baseViewModel27;
                } else if (Intrinsics.areEqual(orCreateKotlinClass2, Reflection.getOrCreateKotlinClass(AdaptiveViewModel.class))) {
                    BaseViewModel baseViewModel28 = vmProvider.adaptiveViewModel;
                    if (baseViewModel28 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectBoxViewModel");
                    }
                    audioEffectBoxViewModel2 = (AudioEffectBoxViewModel) baseViewModel28;
                } else if (Intrinsics.areEqual(orCreateKotlinClass2, Reflection.getOrCreateKotlinClass(AmbientSoundViewModel.class))) {
                    BaseViewModel baseViewModel29 = vmProvider.ambientSoundViewModel;
                    if (baseViewModel29 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectBoxViewModel");
                    }
                    audioEffectBoxViewModel2 = (AudioEffectBoxViewModel) baseViewModel29;
                } else if (Intrinsics.areEqual(orCreateKotlinClass2, Reflection.getOrCreateKotlinClass(NoiseCancelingLevelViewModel.class))) {
                    BaseViewModel baseViewModel30 = vmProvider.noiseCancelingViewModel;
                    if (baseViewModel30 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectBoxViewModel");
                    }
                    audioEffectBoxViewModel2 = (AudioEffectBoxViewModel) baseViewModel30;
                } else if (Intrinsics.areEqual(orCreateKotlinClass2, Reflection.getOrCreateKotlinClass(NoiseControlEffectBoxViewModel.class))) {
                    BaseViewModel baseViewModel31 = vmProvider.noiseControlEffectBoxViewModel;
                    if (baseViewModel31 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectBoxViewModel");
                    }
                    audioEffectBoxViewModel2 = (AudioEffectBoxViewModel) baseViewModel31;
                } else if (Intrinsics.areEqual(orCreateKotlinClass2, Reflection.getOrCreateKotlinClass(NoiseControlOffViewModel.class))) {
                    BaseViewModel baseViewModel32 = vmProvider.noiseControlOffViewModel;
                    if (baseViewModel32 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectBoxViewModel");
                    }
                    audioEffectBoxViewModel2 = (AudioEffectBoxViewModel) baseViewModel32;
                } else if (Intrinsics.areEqual(orCreateKotlinClass2, Reflection.getOrCreateKotlinClass(NoiseCancelingSwitchBarViewModel.class))) {
                    BaseViewModel baseViewModel33 = vmProvider.noiseCancelingSwitchBarViewModel;
                    if (baseViewModel33 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectBoxViewModel");
                    }
                    audioEffectBoxViewModel2 = (AudioEffectBoxViewModel) baseViewModel33;
                } else {
                    if (!Intrinsics.areEqual(orCreateKotlinClass2, Reflection.getOrCreateKotlinClass(RoutineTestViewModel.class))) {
                        throw new RuntimeException();
                    }
                    BaseViewModel baseViewModel34 = vmProvider.routineTestViewModel;
                    if (baseViewModel34 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectBoxViewModel");
                    }
                    audioEffectBoxViewModel2 = (AudioEffectBoxViewModel) baseViewModel34;
                }
                audioEffectBoxViewModel2.notifyChange();
                Log.d("SoundCraft.AudioEffectBoxView", "updateLayout");
                audioEffectBoxView2.getViewBinding().effectItemList.removeAllViews();
                SoundCraftViewModelProvider vmProvider2 = audioEffectBoxView2.getVmProvider();
                ClassReference orCreateKotlinClass3 = Reflection.getOrCreateKotlinClass(AudioEffectBoxViewModel.class);
                if (Intrinsics.areEqual(orCreateKotlinClass3, Reflection.getOrCreateKotlinClass(SoundCraftViewModel.class))) {
                    BaseViewModel baseViewModel35 = vmProvider2.craftViewModel;
                    if (baseViewModel35 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectBoxViewModel");
                    }
                    audioEffectBoxViewModel3 = (AudioEffectBoxViewModel) baseViewModel35;
                } else if (Intrinsics.areEqual(orCreateKotlinClass3, Reflection.getOrCreateKotlinClass(SoundCraftActionBarViewModel.class))) {
                    BaseViewModel baseViewModel36 = vmProvider2.actionBarViewModel;
                    if (baseViewModel36 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectBoxViewModel");
                    }
                    audioEffectBoxViewModel3 = (AudioEffectBoxViewModel) baseViewModel36;
                } else if (Intrinsics.areEqual(orCreateKotlinClass3, Reflection.getOrCreateKotlinClass(NoiseControlBoxViewModel.class))) {
                    BaseViewModel baseViewModel37 = vmProvider2.noiseControlBoxViewModel;
                    if (baseViewModel37 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectBoxViewModel");
                    }
                    audioEffectBoxViewModel3 = (AudioEffectBoxViewModel) baseViewModel37;
                } else if (Intrinsics.areEqual(orCreateKotlinClass3, Reflection.getOrCreateKotlinClass(AudioEffectBoxViewModel.class))) {
                    audioEffectBoxViewModel3 = vmProvider2.audioEffectBoxViewModel;
                    if (audioEffectBoxViewModel3 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectBoxViewModel");
                    }
                } else if (Intrinsics.areEqual(orCreateKotlinClass3, Reflection.getOrCreateKotlinClass(AudioEffectHeaderViewModel.class))) {
                    BaseViewModel baseViewModel38 = vmProvider2.audioEffectHeaderViewModel;
                    if (baseViewModel38 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectBoxViewModel");
                    }
                    audioEffectBoxViewModel3 = (AudioEffectBoxViewModel) baseViewModel38;
                } else if (Intrinsics.areEqual(orCreateKotlinClass3, Reflection.getOrCreateKotlinClass(WearableLinkBoxViewModel.class))) {
                    BaseViewModel baseViewModel39 = vmProvider2.wearableLinkBoxViewModel;
                    if (baseViewModel39 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectBoxViewModel");
                    }
                    audioEffectBoxViewModel3 = (AudioEffectBoxViewModel) baseViewModel39;
                } else if (Intrinsics.areEqual(orCreateKotlinClass3, Reflection.getOrCreateKotlinClass(SpatialAudioViewModel.class))) {
                    BaseViewModel baseViewModel40 = vmProvider2.spatialAudioViewModel;
                    if (baseViewModel40 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectBoxViewModel");
                    }
                    audioEffectBoxViewModel3 = (AudioEffectBoxViewModel) baseViewModel40;
                } else if (Intrinsics.areEqual(orCreateKotlinClass3, Reflection.getOrCreateKotlinClass(EqualizerViewModel.class))) {
                    BaseViewModel baseViewModel41 = vmProvider2.equalizerViewModel;
                    if (baseViewModel41 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectBoxViewModel");
                    }
                    audioEffectBoxViewModel3 = (AudioEffectBoxViewModel) baseViewModel41;
                } else if (Intrinsics.areEqual(orCreateKotlinClass3, Reflection.getOrCreateKotlinClass(VoiceBoostViewModel.class))) {
                    BaseViewModel baseViewModel42 = vmProvider2.voiceBoostViewModel;
                    if (baseViewModel42 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectBoxViewModel");
                    }
                    audioEffectBoxViewModel3 = (AudioEffectBoxViewModel) baseViewModel42;
                } else if (Intrinsics.areEqual(orCreateKotlinClass3, Reflection.getOrCreateKotlinClass(VolumeNormalizationViewModel.class))) {
                    BaseViewModel baseViewModel43 = vmProvider2.volumeNormalizationViewModel;
                    if (baseViewModel43 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectBoxViewModel");
                    }
                    audioEffectBoxViewModel3 = (AudioEffectBoxViewModel) baseViewModel43;
                } else if (Intrinsics.areEqual(orCreateKotlinClass3, Reflection.getOrCreateKotlinClass(ActiveNoiseCancelingViewModel.class))) {
                    BaseViewModel baseViewModel44 = vmProvider2.activeNoiseCancelingViewModel;
                    if (baseViewModel44 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectBoxViewModel");
                    }
                    audioEffectBoxViewModel3 = (AudioEffectBoxViewModel) baseViewModel44;
                } else if (Intrinsics.areEqual(orCreateKotlinClass3, Reflection.getOrCreateKotlinClass(AdaptiveViewModel.class))) {
                    BaseViewModel baseViewModel45 = vmProvider2.adaptiveViewModel;
                    if (baseViewModel45 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectBoxViewModel");
                    }
                    audioEffectBoxViewModel3 = (AudioEffectBoxViewModel) baseViewModel45;
                } else if (Intrinsics.areEqual(orCreateKotlinClass3, Reflection.getOrCreateKotlinClass(AmbientSoundViewModel.class))) {
                    BaseViewModel baseViewModel46 = vmProvider2.ambientSoundViewModel;
                    if (baseViewModel46 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectBoxViewModel");
                    }
                    audioEffectBoxViewModel3 = (AudioEffectBoxViewModel) baseViewModel46;
                } else if (Intrinsics.areEqual(orCreateKotlinClass3, Reflection.getOrCreateKotlinClass(NoiseCancelingLevelViewModel.class))) {
                    BaseViewModel baseViewModel47 = vmProvider2.noiseCancelingViewModel;
                    if (baseViewModel47 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectBoxViewModel");
                    }
                    audioEffectBoxViewModel3 = (AudioEffectBoxViewModel) baseViewModel47;
                } else if (Intrinsics.areEqual(orCreateKotlinClass3, Reflection.getOrCreateKotlinClass(NoiseControlEffectBoxViewModel.class))) {
                    BaseViewModel baseViewModel48 = vmProvider2.noiseControlEffectBoxViewModel;
                    if (baseViewModel48 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectBoxViewModel");
                    }
                    audioEffectBoxViewModel3 = (AudioEffectBoxViewModel) baseViewModel48;
                } else if (Intrinsics.areEqual(orCreateKotlinClass3, Reflection.getOrCreateKotlinClass(NoiseControlOffViewModel.class))) {
                    BaseViewModel baseViewModel49 = vmProvider2.noiseControlOffViewModel;
                    if (baseViewModel49 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectBoxViewModel");
                    }
                    audioEffectBoxViewModel3 = (AudioEffectBoxViewModel) baseViewModel49;
                } else if (Intrinsics.areEqual(orCreateKotlinClass3, Reflection.getOrCreateKotlinClass(NoiseCancelingSwitchBarViewModel.class))) {
                    BaseViewModel baseViewModel50 = vmProvider2.noiseCancelingSwitchBarViewModel;
                    if (baseViewModel50 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectBoxViewModel");
                    }
                    audioEffectBoxViewModel3 = (AudioEffectBoxViewModel) baseViewModel50;
                } else {
                    if (!Intrinsics.areEqual(orCreateKotlinClass3, Reflection.getOrCreateKotlinClass(RoutineTestViewModel.class))) {
                        throw new RuntimeException();
                    }
                    BaseViewModel baseViewModel51 = vmProvider2.routineTestViewModel;
                    if (baseViewModel51 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectBoxViewModel");
                    }
                    audioEffectBoxViewModel3 = (AudioEffectBoxViewModel) baseViewModel51;
                }
                Boolean bool4 = (Boolean) audioEffectBoxViewModel3.showSpatialAudio.getValue();
                if (bool4 != null) {
                    if (!bool4.booleanValue()) {
                        bool4 = null;
                    }
                    if (bool4 != null) {
                        Log.d("SoundCraft.AudioEffectBoxView", "addSpatialAudioView");
                        SoundCraftViewModelProvider vmProvider3 = audioEffectBoxView2.getVmProvider();
                        ClassReference orCreateKotlinClass4 = Reflection.getOrCreateKotlinClass(SpatialAudioViewModel.class);
                        cls = SoundCraftViewModel.class;
                        if (Intrinsics.areEqual(orCreateKotlinClass4, Reflection.getOrCreateKotlinClass(SoundCraftViewModel.class))) {
                            BaseViewModel baseViewModel52 = vmProvider3.craftViewModel;
                            if (baseViewModel52 == null) {
                                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.SpatialAudioViewModel");
                            }
                            spatialAudioViewModel = (SpatialAudioViewModel) baseViewModel52;
                        } else if (Intrinsics.areEqual(orCreateKotlinClass4, Reflection.getOrCreateKotlinClass(SoundCraftActionBarViewModel.class))) {
                            BaseViewModel baseViewModel53 = vmProvider3.actionBarViewModel;
                            if (baseViewModel53 == null) {
                                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.SpatialAudioViewModel");
                            }
                            spatialAudioViewModel = (SpatialAudioViewModel) baseViewModel53;
                        } else if (Intrinsics.areEqual(orCreateKotlinClass4, Reflection.getOrCreateKotlinClass(NoiseControlBoxViewModel.class))) {
                            BaseViewModel baseViewModel54 = vmProvider3.noiseControlBoxViewModel;
                            if (baseViewModel54 == null) {
                                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.SpatialAudioViewModel");
                            }
                            spatialAudioViewModel = (SpatialAudioViewModel) baseViewModel54;
                        } else if (Intrinsics.areEqual(orCreateKotlinClass4, Reflection.getOrCreateKotlinClass(AudioEffectBoxViewModel.class))) {
                            BaseViewModel baseViewModel55 = vmProvider3.audioEffectBoxViewModel;
                            if (baseViewModel55 == null) {
                                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.SpatialAudioViewModel");
                            }
                            spatialAudioViewModel = (SpatialAudioViewModel) baseViewModel55;
                        } else if (Intrinsics.areEqual(orCreateKotlinClass4, Reflection.getOrCreateKotlinClass(AudioEffectHeaderViewModel.class))) {
                            BaseViewModel baseViewModel56 = vmProvider3.audioEffectHeaderViewModel;
                            if (baseViewModel56 == null) {
                                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.SpatialAudioViewModel");
                            }
                            spatialAudioViewModel = (SpatialAudioViewModel) baseViewModel56;
                        } else if (Intrinsics.areEqual(orCreateKotlinClass4, Reflection.getOrCreateKotlinClass(WearableLinkBoxViewModel.class))) {
                            BaseViewModel baseViewModel57 = vmProvider3.wearableLinkBoxViewModel;
                            if (baseViewModel57 == null) {
                                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.SpatialAudioViewModel");
                            }
                            spatialAudioViewModel = (SpatialAudioViewModel) baseViewModel57;
                        } else if (Intrinsics.areEqual(orCreateKotlinClass4, Reflection.getOrCreateKotlinClass(SpatialAudioViewModel.class))) {
                            spatialAudioViewModel = vmProvider3.spatialAudioViewModel;
                            if (spatialAudioViewModel == null) {
                                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.SpatialAudioViewModel");
                            }
                        } else if (Intrinsics.areEqual(orCreateKotlinClass4, Reflection.getOrCreateKotlinClass(EqualizerViewModel.class))) {
                            BaseViewModel baseViewModel58 = vmProvider3.equalizerViewModel;
                            if (baseViewModel58 == null) {
                                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.SpatialAudioViewModel");
                            }
                            spatialAudioViewModel = (SpatialAudioViewModel) baseViewModel58;
                        } else if (Intrinsics.areEqual(orCreateKotlinClass4, Reflection.getOrCreateKotlinClass(VoiceBoostViewModel.class))) {
                            BaseViewModel baseViewModel59 = vmProvider3.voiceBoostViewModel;
                            if (baseViewModel59 == null) {
                                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.SpatialAudioViewModel");
                            }
                            spatialAudioViewModel = (SpatialAudioViewModel) baseViewModel59;
                        } else if (Intrinsics.areEqual(orCreateKotlinClass4, Reflection.getOrCreateKotlinClass(VolumeNormalizationViewModel.class))) {
                            BaseViewModel baseViewModel60 = vmProvider3.volumeNormalizationViewModel;
                            if (baseViewModel60 == null) {
                                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.SpatialAudioViewModel");
                            }
                            spatialAudioViewModel = (SpatialAudioViewModel) baseViewModel60;
                        } else if (Intrinsics.areEqual(orCreateKotlinClass4, Reflection.getOrCreateKotlinClass(ActiveNoiseCancelingViewModel.class))) {
                            BaseViewModel baseViewModel61 = vmProvider3.activeNoiseCancelingViewModel;
                            if (baseViewModel61 == null) {
                                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.SpatialAudioViewModel");
                            }
                            spatialAudioViewModel = (SpatialAudioViewModel) baseViewModel61;
                        } else if (Intrinsics.areEqual(orCreateKotlinClass4, Reflection.getOrCreateKotlinClass(AdaptiveViewModel.class))) {
                            BaseViewModel baseViewModel62 = vmProvider3.adaptiveViewModel;
                            if (baseViewModel62 == null) {
                                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.SpatialAudioViewModel");
                            }
                            spatialAudioViewModel = (SpatialAudioViewModel) baseViewModel62;
                        } else if (Intrinsics.areEqual(orCreateKotlinClass4, Reflection.getOrCreateKotlinClass(AmbientSoundViewModel.class))) {
                            BaseViewModel baseViewModel63 = vmProvider3.ambientSoundViewModel;
                            if (baseViewModel63 == null) {
                                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.SpatialAudioViewModel");
                            }
                            spatialAudioViewModel = (SpatialAudioViewModel) baseViewModel63;
                        } else if (Intrinsics.areEqual(orCreateKotlinClass4, Reflection.getOrCreateKotlinClass(NoiseCancelingLevelViewModel.class))) {
                            BaseViewModel baseViewModel64 = vmProvider3.noiseCancelingViewModel;
                            if (baseViewModel64 == null) {
                                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.SpatialAudioViewModel");
                            }
                            spatialAudioViewModel = (SpatialAudioViewModel) baseViewModel64;
                        } else if (Intrinsics.areEqual(orCreateKotlinClass4, Reflection.getOrCreateKotlinClass(NoiseControlEffectBoxViewModel.class))) {
                            BaseViewModel baseViewModel65 = vmProvider3.noiseControlEffectBoxViewModel;
                            if (baseViewModel65 == null) {
                                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.SpatialAudioViewModel");
                            }
                            spatialAudioViewModel = (SpatialAudioViewModel) baseViewModel65;
                        } else if (Intrinsics.areEqual(orCreateKotlinClass4, Reflection.getOrCreateKotlinClass(NoiseControlOffViewModel.class))) {
                            BaseViewModel baseViewModel66 = vmProvider3.noiseControlOffViewModel;
                            if (baseViewModel66 == null) {
                                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.SpatialAudioViewModel");
                            }
                            spatialAudioViewModel = (SpatialAudioViewModel) baseViewModel66;
                        } else if (Intrinsics.areEqual(orCreateKotlinClass4, Reflection.getOrCreateKotlinClass(NoiseCancelingSwitchBarViewModel.class))) {
                            BaseViewModel baseViewModel67 = vmProvider3.noiseCancelingSwitchBarViewModel;
                            if (baseViewModel67 == null) {
                                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.SpatialAudioViewModel");
                            }
                            spatialAudioViewModel = (SpatialAudioViewModel) baseViewModel67;
                        } else {
                            if (!Intrinsics.areEqual(orCreateKotlinClass4, Reflection.getOrCreateKotlinClass(RoutineTestViewModel.class))) {
                                throw new RuntimeException();
                            }
                            BaseViewModel baseViewModel68 = vmProvider3.routineTestViewModel;
                            if (baseViewModel68 == null) {
                                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.SpatialAudioViewModel");
                            }
                            spatialAudioViewModel = (SpatialAudioViewModel) baseViewModel68;
                        }
                        audioEffectBoxView = audioEffectBoxView2;
                        BaseAudioEffectItemView createItemView = audioEffectBoxView.createItemView(spatialAudioViewModel);
                        audioEffectBoxView.getViewBinding().effectItemList.addView(createItemView.getRootView());
                        audioEffectBoxView.spatialAudioView = createItemView;
                        z = true;
                        bool = (Boolean) audioEffectBoxViewModel3.showEqualizer.getValue();
                        if (bool != null) {
                            if (!bool.booleanValue()) {
                                bool = null;
                            }
                            if (bool != null) {
                                audioEffectBoxView.addDivider();
                                Log.d("SoundCraft.AudioEffectBoxView", "addEqualizerView");
                                SoundCraftViewModelProvider vmProvider4 = audioEffectBoxView.getVmProvider();
                                ClassReference orCreateKotlinClass5 = Reflection.getOrCreateKotlinClass(EqualizerViewModel.class);
                                str = "SoundCraft.AudioEffectBoxView";
                                if (Intrinsics.areEqual(orCreateKotlinClass5, Reflection.getOrCreateKotlinClass(cls))) {
                                    BaseViewModel baseViewModel69 = vmProvider4.craftViewModel;
                                    if (baseViewModel69 == null) {
                                        throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.EqualizerViewModel");
                                    }
                                    equalizerViewModel = (EqualizerViewModel) baseViewModel69;
                                } else if (Intrinsics.areEqual(orCreateKotlinClass5, Reflection.getOrCreateKotlinClass(SoundCraftActionBarViewModel.class))) {
                                    BaseViewModel baseViewModel70 = vmProvider4.actionBarViewModel;
                                    if (baseViewModel70 == null) {
                                        throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.EqualizerViewModel");
                                    }
                                    equalizerViewModel = (EqualizerViewModel) baseViewModel70;
                                } else if (Intrinsics.areEqual(orCreateKotlinClass5, Reflection.getOrCreateKotlinClass(NoiseControlBoxViewModel.class))) {
                                    BaseViewModel baseViewModel71 = vmProvider4.noiseControlBoxViewModel;
                                    if (baseViewModel71 == null) {
                                        throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.EqualizerViewModel");
                                    }
                                    equalizerViewModel = (EqualizerViewModel) baseViewModel71;
                                } else if (Intrinsics.areEqual(orCreateKotlinClass5, Reflection.getOrCreateKotlinClass(AudioEffectBoxViewModel.class))) {
                                    BaseViewModel baseViewModel72 = vmProvider4.audioEffectBoxViewModel;
                                    if (baseViewModel72 == null) {
                                        throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.EqualizerViewModel");
                                    }
                                    equalizerViewModel = (EqualizerViewModel) baseViewModel72;
                                } else if (Intrinsics.areEqual(orCreateKotlinClass5, Reflection.getOrCreateKotlinClass(AudioEffectHeaderViewModel.class))) {
                                    BaseViewModel baseViewModel73 = vmProvider4.audioEffectHeaderViewModel;
                                    if (baseViewModel73 == null) {
                                        throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.EqualizerViewModel");
                                    }
                                    equalizerViewModel = (EqualizerViewModel) baseViewModel73;
                                } else if (Intrinsics.areEqual(orCreateKotlinClass5, Reflection.getOrCreateKotlinClass(WearableLinkBoxViewModel.class))) {
                                    BaseViewModel baseViewModel74 = vmProvider4.wearableLinkBoxViewModel;
                                    if (baseViewModel74 == null) {
                                        throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.EqualizerViewModel");
                                    }
                                    equalizerViewModel = (EqualizerViewModel) baseViewModel74;
                                } else if (Intrinsics.areEqual(orCreateKotlinClass5, Reflection.getOrCreateKotlinClass(SpatialAudioViewModel.class))) {
                                    BaseViewModel baseViewModel75 = vmProvider4.spatialAudioViewModel;
                                    if (baseViewModel75 == null) {
                                        throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.EqualizerViewModel");
                                    }
                                    equalizerViewModel = (EqualizerViewModel) baseViewModel75;
                                } else if (Intrinsics.areEqual(orCreateKotlinClass5, Reflection.getOrCreateKotlinClass(EqualizerViewModel.class))) {
                                    equalizerViewModel = vmProvider4.equalizerViewModel;
                                    if (equalizerViewModel == null) {
                                        throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.EqualizerViewModel");
                                    }
                                } else if (Intrinsics.areEqual(orCreateKotlinClass5, Reflection.getOrCreateKotlinClass(VoiceBoostViewModel.class))) {
                                    BaseViewModel baseViewModel76 = vmProvider4.voiceBoostViewModel;
                                    if (baseViewModel76 == null) {
                                        throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.EqualizerViewModel");
                                    }
                                    equalizerViewModel = (EqualizerViewModel) baseViewModel76;
                                } else if (Intrinsics.areEqual(orCreateKotlinClass5, Reflection.getOrCreateKotlinClass(VolumeNormalizationViewModel.class))) {
                                    BaseViewModel baseViewModel77 = vmProvider4.volumeNormalizationViewModel;
                                    if (baseViewModel77 == null) {
                                        throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.EqualizerViewModel");
                                    }
                                    equalizerViewModel = (EqualizerViewModel) baseViewModel77;
                                } else if (Intrinsics.areEqual(orCreateKotlinClass5, Reflection.getOrCreateKotlinClass(ActiveNoiseCancelingViewModel.class))) {
                                    BaseViewModel baseViewModel78 = vmProvider4.activeNoiseCancelingViewModel;
                                    if (baseViewModel78 == null) {
                                        throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.EqualizerViewModel");
                                    }
                                    equalizerViewModel = (EqualizerViewModel) baseViewModel78;
                                } else if (Intrinsics.areEqual(orCreateKotlinClass5, Reflection.getOrCreateKotlinClass(AdaptiveViewModel.class))) {
                                    BaseViewModel baseViewModel79 = vmProvider4.adaptiveViewModel;
                                    if (baseViewModel79 == null) {
                                        throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.EqualizerViewModel");
                                    }
                                    equalizerViewModel = (EqualizerViewModel) baseViewModel79;
                                } else if (Intrinsics.areEqual(orCreateKotlinClass5, Reflection.getOrCreateKotlinClass(AmbientSoundViewModel.class))) {
                                    BaseViewModel baseViewModel80 = vmProvider4.ambientSoundViewModel;
                                    if (baseViewModel80 == null) {
                                        throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.EqualizerViewModel");
                                    }
                                    equalizerViewModel = (EqualizerViewModel) baseViewModel80;
                                } else if (Intrinsics.areEqual(orCreateKotlinClass5, Reflection.getOrCreateKotlinClass(NoiseCancelingLevelViewModel.class))) {
                                    BaseViewModel baseViewModel81 = vmProvider4.noiseCancelingViewModel;
                                    if (baseViewModel81 == null) {
                                        throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.EqualizerViewModel");
                                    }
                                    equalizerViewModel = (EqualizerViewModel) baseViewModel81;
                                } else if (Intrinsics.areEqual(orCreateKotlinClass5, Reflection.getOrCreateKotlinClass(NoiseControlEffectBoxViewModel.class))) {
                                    BaseViewModel baseViewModel82 = vmProvider4.noiseControlEffectBoxViewModel;
                                    if (baseViewModel82 == null) {
                                        throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.EqualizerViewModel");
                                    }
                                    equalizerViewModel = (EqualizerViewModel) baseViewModel82;
                                } else if (Intrinsics.areEqual(orCreateKotlinClass5, Reflection.getOrCreateKotlinClass(NoiseControlOffViewModel.class))) {
                                    BaseViewModel baseViewModel83 = vmProvider4.noiseControlOffViewModel;
                                    if (baseViewModel83 == null) {
                                        throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.EqualizerViewModel");
                                    }
                                    equalizerViewModel = (EqualizerViewModel) baseViewModel83;
                                } else if (Intrinsics.areEqual(orCreateKotlinClass5, Reflection.getOrCreateKotlinClass(NoiseCancelingSwitchBarViewModel.class))) {
                                    BaseViewModel baseViewModel84 = vmProvider4.noiseCancelingSwitchBarViewModel;
                                    if (baseViewModel84 == null) {
                                        throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.EqualizerViewModel");
                                    }
                                    equalizerViewModel = (EqualizerViewModel) baseViewModel84;
                                } else {
                                    if (!Intrinsics.areEqual(orCreateKotlinClass5, Reflection.getOrCreateKotlinClass(RoutineTestViewModel.class))) {
                                        throw new RuntimeException();
                                    }
                                    BaseViewModel baseViewModel85 = vmProvider4.routineTestViewModel;
                                    if (baseViewModel85 == null) {
                                        throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.EqualizerViewModel");
                                    }
                                    equalizerViewModel = (EqualizerViewModel) baseViewModel85;
                                }
                                BaseAudioEffectItemView createItemView2 = audioEffectBoxView.createItemView(equalizerViewModel);
                                audioEffectBoxView.getViewBinding().effectItemList.addView(createItemView2.getRootView());
                                audioEffectBoxView.equalizerView = createItemView2;
                                z = true;
                                bool2 = (Boolean) audioEffectBoxViewModel3.showVoiceBoost.getValue();
                                if (bool2 != null) {
                                    if (!bool2.booleanValue()) {
                                        bool2 = null;
                                    }
                                    if (bool2 != null) {
                                        audioEffectBoxView.addDivider();
                                        Log.d(str, "addVoiceBoostView");
                                        SoundCraftViewModelProvider vmProvider5 = audioEffectBoxView.getVmProvider();
                                        ClassReference orCreateKotlinClass6 = Reflection.getOrCreateKotlinClass(VoiceBoostViewModel.class);
                                        if (Intrinsics.areEqual(orCreateKotlinClass6, Reflection.getOrCreateKotlinClass(cls))) {
                                            BaseViewModel baseViewModel86 = vmProvider5.craftViewModel;
                                            if (baseViewModel86 == null) {
                                                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.VoiceBoostViewModel");
                                            }
                                            voiceBoostViewModel = (VoiceBoostViewModel) baseViewModel86;
                                        } else if (Intrinsics.areEqual(orCreateKotlinClass6, Reflection.getOrCreateKotlinClass(SoundCraftActionBarViewModel.class))) {
                                            BaseViewModel baseViewModel87 = vmProvider5.actionBarViewModel;
                                            if (baseViewModel87 == null) {
                                                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.VoiceBoostViewModel");
                                            }
                                            voiceBoostViewModel = (VoiceBoostViewModel) baseViewModel87;
                                        } else if (Intrinsics.areEqual(orCreateKotlinClass6, Reflection.getOrCreateKotlinClass(NoiseControlBoxViewModel.class))) {
                                            BaseViewModel baseViewModel88 = vmProvider5.noiseControlBoxViewModel;
                                            if (baseViewModel88 == null) {
                                                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.VoiceBoostViewModel");
                                            }
                                            voiceBoostViewModel = (VoiceBoostViewModel) baseViewModel88;
                                        } else if (Intrinsics.areEqual(orCreateKotlinClass6, Reflection.getOrCreateKotlinClass(AudioEffectBoxViewModel.class))) {
                                            BaseViewModel baseViewModel89 = vmProvider5.audioEffectBoxViewModel;
                                            if (baseViewModel89 == null) {
                                                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.VoiceBoostViewModel");
                                            }
                                            voiceBoostViewModel = (VoiceBoostViewModel) baseViewModel89;
                                        } else if (Intrinsics.areEqual(orCreateKotlinClass6, Reflection.getOrCreateKotlinClass(AudioEffectHeaderViewModel.class))) {
                                            BaseViewModel baseViewModel90 = vmProvider5.audioEffectHeaderViewModel;
                                            if (baseViewModel90 == null) {
                                                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.VoiceBoostViewModel");
                                            }
                                            voiceBoostViewModel = (VoiceBoostViewModel) baseViewModel90;
                                        } else if (Intrinsics.areEqual(orCreateKotlinClass6, Reflection.getOrCreateKotlinClass(WearableLinkBoxViewModel.class))) {
                                            BaseViewModel baseViewModel91 = vmProvider5.wearableLinkBoxViewModel;
                                            if (baseViewModel91 == null) {
                                                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.VoiceBoostViewModel");
                                            }
                                            voiceBoostViewModel = (VoiceBoostViewModel) baseViewModel91;
                                        } else if (Intrinsics.areEqual(orCreateKotlinClass6, Reflection.getOrCreateKotlinClass(SpatialAudioViewModel.class))) {
                                            BaseViewModel baseViewModel92 = vmProvider5.spatialAudioViewModel;
                                            if (baseViewModel92 == null) {
                                                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.VoiceBoostViewModel");
                                            }
                                            voiceBoostViewModel = (VoiceBoostViewModel) baseViewModel92;
                                        } else if (Intrinsics.areEqual(orCreateKotlinClass6, Reflection.getOrCreateKotlinClass(EqualizerViewModel.class))) {
                                            BaseViewModel baseViewModel93 = vmProvider5.equalizerViewModel;
                                            if (baseViewModel93 == null) {
                                                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.VoiceBoostViewModel");
                                            }
                                            voiceBoostViewModel = (VoiceBoostViewModel) baseViewModel93;
                                        } else if (Intrinsics.areEqual(orCreateKotlinClass6, Reflection.getOrCreateKotlinClass(VoiceBoostViewModel.class))) {
                                            voiceBoostViewModel = vmProvider5.voiceBoostViewModel;
                                            if (voiceBoostViewModel == null) {
                                                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.VoiceBoostViewModel");
                                            }
                                        } else if (Intrinsics.areEqual(orCreateKotlinClass6, Reflection.getOrCreateKotlinClass(VolumeNormalizationViewModel.class))) {
                                            BaseViewModel baseViewModel94 = vmProvider5.volumeNormalizationViewModel;
                                            if (baseViewModel94 == null) {
                                                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.VoiceBoostViewModel");
                                            }
                                            voiceBoostViewModel = (VoiceBoostViewModel) baseViewModel94;
                                        } else if (Intrinsics.areEqual(orCreateKotlinClass6, Reflection.getOrCreateKotlinClass(ActiveNoiseCancelingViewModel.class))) {
                                            BaseViewModel baseViewModel95 = vmProvider5.activeNoiseCancelingViewModel;
                                            if (baseViewModel95 == null) {
                                                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.VoiceBoostViewModel");
                                            }
                                            voiceBoostViewModel = (VoiceBoostViewModel) baseViewModel95;
                                        } else if (Intrinsics.areEqual(orCreateKotlinClass6, Reflection.getOrCreateKotlinClass(AdaptiveViewModel.class))) {
                                            BaseViewModel baseViewModel96 = vmProvider5.adaptiveViewModel;
                                            if (baseViewModel96 == null) {
                                                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.VoiceBoostViewModel");
                                            }
                                            voiceBoostViewModel = (VoiceBoostViewModel) baseViewModel96;
                                        } else if (Intrinsics.areEqual(orCreateKotlinClass6, Reflection.getOrCreateKotlinClass(AmbientSoundViewModel.class))) {
                                            BaseViewModel baseViewModel97 = vmProvider5.ambientSoundViewModel;
                                            if (baseViewModel97 == null) {
                                                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.VoiceBoostViewModel");
                                            }
                                            voiceBoostViewModel = (VoiceBoostViewModel) baseViewModel97;
                                        } else if (Intrinsics.areEqual(orCreateKotlinClass6, Reflection.getOrCreateKotlinClass(NoiseCancelingLevelViewModel.class))) {
                                            BaseViewModel baseViewModel98 = vmProvider5.noiseCancelingViewModel;
                                            if (baseViewModel98 == null) {
                                                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.VoiceBoostViewModel");
                                            }
                                            voiceBoostViewModel = (VoiceBoostViewModel) baseViewModel98;
                                        } else if (Intrinsics.areEqual(orCreateKotlinClass6, Reflection.getOrCreateKotlinClass(NoiseControlEffectBoxViewModel.class))) {
                                            BaseViewModel baseViewModel99 = vmProvider5.noiseControlEffectBoxViewModel;
                                            if (baseViewModel99 == null) {
                                                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.VoiceBoostViewModel");
                                            }
                                            voiceBoostViewModel = (VoiceBoostViewModel) baseViewModel99;
                                        } else if (Intrinsics.areEqual(orCreateKotlinClass6, Reflection.getOrCreateKotlinClass(NoiseControlOffViewModel.class))) {
                                            BaseViewModel baseViewModel100 = vmProvider5.noiseControlOffViewModel;
                                            if (baseViewModel100 == null) {
                                                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.VoiceBoostViewModel");
                                            }
                                            voiceBoostViewModel = (VoiceBoostViewModel) baseViewModel100;
                                        } else if (Intrinsics.areEqual(orCreateKotlinClass6, Reflection.getOrCreateKotlinClass(NoiseCancelingSwitchBarViewModel.class))) {
                                            BaseViewModel baseViewModel101 = vmProvider5.noiseCancelingSwitchBarViewModel;
                                            if (baseViewModel101 == null) {
                                                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.VoiceBoostViewModel");
                                            }
                                            voiceBoostViewModel = (VoiceBoostViewModel) baseViewModel101;
                                        } else {
                                            if (!Intrinsics.areEqual(orCreateKotlinClass6, Reflection.getOrCreateKotlinClass(RoutineTestViewModel.class))) {
                                                throw new RuntimeException();
                                            }
                                            BaseViewModel baseViewModel102 = vmProvider5.routineTestViewModel;
                                            if (baseViewModel102 == null) {
                                                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.VoiceBoostViewModel");
                                            }
                                            voiceBoostViewModel = (VoiceBoostViewModel) baseViewModel102;
                                        }
                                        BaseAudioEffectItemView createItemView3 = audioEffectBoxView.createItemView(voiceBoostViewModel);
                                        audioEffectBoxView.getViewBinding().effectItemList.addView(createItemView3.getRootView());
                                        audioEffectBoxView.voiceBoostView = createItemView3;
                                        z = true;
                                    }
                                }
                                bool3 = (Boolean) audioEffectBoxViewModel3.showVolumeNormalization.getValue();
                                if (bool3 != null) {
                                    if (!bool3.booleanValue()) {
                                        bool3 = null;
                                    }
                                    if (bool3 != null) {
                                        audioEffectBoxView.addDivider();
                                        Log.d(str, "addVolumeNormalizationView");
                                        SoundCraftViewModelProvider vmProvider6 = audioEffectBoxView.getVmProvider();
                                        ClassReference orCreateKotlinClass7 = Reflection.getOrCreateKotlinClass(VolumeNormalizationViewModel.class);
                                        if (Intrinsics.areEqual(orCreateKotlinClass7, Reflection.getOrCreateKotlinClass(cls))) {
                                            BaseViewModel baseViewModel103 = vmProvider6.craftViewModel;
                                            if (baseViewModel103 == null) {
                                                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.VolumeNormalizationViewModel");
                                            }
                                            volumeNormalizationViewModel = (VolumeNormalizationViewModel) baseViewModel103;
                                        } else if (Intrinsics.areEqual(orCreateKotlinClass7, Reflection.getOrCreateKotlinClass(SoundCraftActionBarViewModel.class))) {
                                            BaseViewModel baseViewModel104 = vmProvider6.actionBarViewModel;
                                            if (baseViewModel104 == null) {
                                                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.VolumeNormalizationViewModel");
                                            }
                                            volumeNormalizationViewModel = (VolumeNormalizationViewModel) baseViewModel104;
                                        } else if (Intrinsics.areEqual(orCreateKotlinClass7, Reflection.getOrCreateKotlinClass(NoiseControlBoxViewModel.class))) {
                                            BaseViewModel baseViewModel105 = vmProvider6.noiseControlBoxViewModel;
                                            if (baseViewModel105 == null) {
                                                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.VolumeNormalizationViewModel");
                                            }
                                            volumeNormalizationViewModel = (VolumeNormalizationViewModel) baseViewModel105;
                                        } else if (Intrinsics.areEqual(orCreateKotlinClass7, Reflection.getOrCreateKotlinClass(AudioEffectBoxViewModel.class))) {
                                            BaseViewModel baseViewModel106 = vmProvider6.audioEffectBoxViewModel;
                                            if (baseViewModel106 == null) {
                                                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.VolumeNormalizationViewModel");
                                            }
                                            volumeNormalizationViewModel = (VolumeNormalizationViewModel) baseViewModel106;
                                        } else if (Intrinsics.areEqual(orCreateKotlinClass7, Reflection.getOrCreateKotlinClass(AudioEffectHeaderViewModel.class))) {
                                            BaseViewModel baseViewModel107 = vmProvider6.audioEffectHeaderViewModel;
                                            if (baseViewModel107 == null) {
                                                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.VolumeNormalizationViewModel");
                                            }
                                            volumeNormalizationViewModel = (VolumeNormalizationViewModel) baseViewModel107;
                                        } else if (Intrinsics.areEqual(orCreateKotlinClass7, Reflection.getOrCreateKotlinClass(WearableLinkBoxViewModel.class))) {
                                            BaseViewModel baseViewModel108 = vmProvider6.wearableLinkBoxViewModel;
                                            if (baseViewModel108 == null) {
                                                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.VolumeNormalizationViewModel");
                                            }
                                            volumeNormalizationViewModel = (VolumeNormalizationViewModel) baseViewModel108;
                                        } else if (Intrinsics.areEqual(orCreateKotlinClass7, Reflection.getOrCreateKotlinClass(SpatialAudioViewModel.class))) {
                                            BaseViewModel baseViewModel109 = vmProvider6.spatialAudioViewModel;
                                            if (baseViewModel109 == null) {
                                                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.VolumeNormalizationViewModel");
                                            }
                                            volumeNormalizationViewModel = (VolumeNormalizationViewModel) baseViewModel109;
                                        } else if (Intrinsics.areEqual(orCreateKotlinClass7, Reflection.getOrCreateKotlinClass(EqualizerViewModel.class))) {
                                            BaseViewModel baseViewModel110 = vmProvider6.equalizerViewModel;
                                            if (baseViewModel110 == null) {
                                                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.VolumeNormalizationViewModel");
                                            }
                                            volumeNormalizationViewModel = (VolumeNormalizationViewModel) baseViewModel110;
                                        } else if (Intrinsics.areEqual(orCreateKotlinClass7, Reflection.getOrCreateKotlinClass(VoiceBoostViewModel.class))) {
                                            BaseViewModel baseViewModel111 = vmProvider6.voiceBoostViewModel;
                                            if (baseViewModel111 == null) {
                                                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.VolumeNormalizationViewModel");
                                            }
                                            volumeNormalizationViewModel = (VolumeNormalizationViewModel) baseViewModel111;
                                        } else if (Intrinsics.areEqual(orCreateKotlinClass7, Reflection.getOrCreateKotlinClass(VolumeNormalizationViewModel.class))) {
                                            volumeNormalizationViewModel = vmProvider6.volumeNormalizationViewModel;
                                            if (volumeNormalizationViewModel == null) {
                                                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.VolumeNormalizationViewModel");
                                            }
                                        } else if (Intrinsics.areEqual(orCreateKotlinClass7, Reflection.getOrCreateKotlinClass(ActiveNoiseCancelingViewModel.class))) {
                                            BaseViewModel baseViewModel112 = vmProvider6.activeNoiseCancelingViewModel;
                                            if (baseViewModel112 == null) {
                                                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.VolumeNormalizationViewModel");
                                            }
                                            volumeNormalizationViewModel = (VolumeNormalizationViewModel) baseViewModel112;
                                        } else if (Intrinsics.areEqual(orCreateKotlinClass7, Reflection.getOrCreateKotlinClass(AdaptiveViewModel.class))) {
                                            BaseViewModel baseViewModel113 = vmProvider6.adaptiveViewModel;
                                            if (baseViewModel113 == null) {
                                                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.VolumeNormalizationViewModel");
                                            }
                                            volumeNormalizationViewModel = (VolumeNormalizationViewModel) baseViewModel113;
                                        } else if (Intrinsics.areEqual(orCreateKotlinClass7, Reflection.getOrCreateKotlinClass(AmbientSoundViewModel.class))) {
                                            BaseViewModel baseViewModel114 = vmProvider6.ambientSoundViewModel;
                                            if (baseViewModel114 == null) {
                                                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.VolumeNormalizationViewModel");
                                            }
                                            volumeNormalizationViewModel = (VolumeNormalizationViewModel) baseViewModel114;
                                        } else if (Intrinsics.areEqual(orCreateKotlinClass7, Reflection.getOrCreateKotlinClass(NoiseCancelingLevelViewModel.class))) {
                                            BaseViewModel baseViewModel115 = vmProvider6.noiseCancelingViewModel;
                                            if (baseViewModel115 == null) {
                                                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.VolumeNormalizationViewModel");
                                            }
                                            volumeNormalizationViewModel = (VolumeNormalizationViewModel) baseViewModel115;
                                        } else if (Intrinsics.areEqual(orCreateKotlinClass7, Reflection.getOrCreateKotlinClass(NoiseControlEffectBoxViewModel.class))) {
                                            BaseViewModel baseViewModel116 = vmProvider6.noiseControlEffectBoxViewModel;
                                            if (baseViewModel116 == null) {
                                                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.VolumeNormalizationViewModel");
                                            }
                                            volumeNormalizationViewModel = (VolumeNormalizationViewModel) baseViewModel116;
                                        } else if (Intrinsics.areEqual(orCreateKotlinClass7, Reflection.getOrCreateKotlinClass(NoiseControlOffViewModel.class))) {
                                            BaseViewModel baseViewModel117 = vmProvider6.noiseControlOffViewModel;
                                            if (baseViewModel117 == null) {
                                                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.VolumeNormalizationViewModel");
                                            }
                                            volumeNormalizationViewModel = (VolumeNormalizationViewModel) baseViewModel117;
                                        } else if (Intrinsics.areEqual(orCreateKotlinClass7, Reflection.getOrCreateKotlinClass(NoiseCancelingSwitchBarViewModel.class))) {
                                            BaseViewModel baseViewModel118 = vmProvider6.noiseCancelingSwitchBarViewModel;
                                            if (baseViewModel118 == null) {
                                                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.VolumeNormalizationViewModel");
                                            }
                                            volumeNormalizationViewModel = (VolumeNormalizationViewModel) baseViewModel118;
                                        } else {
                                            if (!Intrinsics.areEqual(orCreateKotlinClass7, Reflection.getOrCreateKotlinClass(RoutineTestViewModel.class))) {
                                                throw new RuntimeException();
                                            }
                                            BaseViewModel baseViewModel119 = vmProvider6.routineTestViewModel;
                                            if (baseViewModel119 == null) {
                                                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.VolumeNormalizationViewModel");
                                            }
                                            volumeNormalizationViewModel = (VolumeNormalizationViewModel) baseViewModel119;
                                        }
                                        BaseAudioEffectItemView createItemView4 = audioEffectBoxView.createItemView(volumeNormalizationViewModel);
                                        audioEffectBoxView.getViewBinding().effectItemList.addView(createItemView4.getRootView());
                                        audioEffectBoxView.volumeNormalizationView = createItemView4;
                                        z2 = true;
                                        audioEffectBoxView.getViewBinding().root.setVisibility(!z2 ? 0 : 8);
                                    }
                                }
                                z2 = z;
                                audioEffectBoxView.getViewBinding().root.setVisibility(!z2 ? 0 : 8);
                            }
                        }
                        str = "SoundCraft.AudioEffectBoxView";
                        bool2 = (Boolean) audioEffectBoxViewModel3.showVoiceBoost.getValue();
                        if (bool2 != null) {
                        }
                        bool3 = (Boolean) audioEffectBoxViewModel3.showVolumeNormalization.getValue();
                        if (bool3 != null) {
                        }
                        z2 = z;
                        audioEffectBoxView.getViewBinding().root.setVisibility(!z2 ? 0 : 8);
                    }
                }
                audioEffectBoxView = audioEffectBoxView2;
                cls = SoundCraftViewModel.class;
                z = false;
                bool = (Boolean) audioEffectBoxViewModel3.showEqualizer.getValue();
                if (bool != null) {
                }
                str = "SoundCraft.AudioEffectBoxView";
                bool2 = (Boolean) audioEffectBoxViewModel3.showVoiceBoost.getValue();
                if (bool2 != null) {
                }
                bool3 = (Boolean) audioEffectBoxViewModel3.showVolumeNormalization.getValue();
                if (bool3 != null) {
                }
                z2 = z;
                audioEffectBoxView.getViewBinding().root.setVisibility(!z2 ? 0 : 8);
            }
        });
        soundCraftViewModel.showDownloadGuideView.observe(soundCraftDetailPageView, new Observer() { // from class: com.android.systemui.qs.bar.soundcraft.view.SoundCraftDetailPageView$bindViewModel$3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                Log.d("SoundCraft.DetailPageView", "showDownloadView=" + ((Boolean) obj));
            }
        });
        soundCraftViewModel.showLoadingView.observe(soundCraftDetailPageView, new Observer() { // from class: com.android.systemui.qs.bar.soundcraft.view.SoundCraftDetailPageView$bindViewModel$4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                Log.d("SoundCraft.DetailPageView", "showLoadingView=" + ((Boolean) obj));
            }
        });
        soundCraftViewModel.notifyChange();
        SoundCraftActionBarBinding soundCraftActionBarBinding = soundCraftViewBinding.actionBar;
        final SoundCraftActionBarView soundCraftActionBarView = soundCraftActionBarBinding.root;
        soundCraftActionBarView.viewBinding = soundCraftActionBarBinding;
        SoundCraftViewModelProvider viewModelProvider2 = getViewModelProvider();
        ClassReference orCreateKotlinClass2 = Reflection.getOrCreateKotlinClass(SoundCraftActionBarViewModel.class);
        if (Intrinsics.areEqual(orCreateKotlinClass2, Reflection.getOrCreateKotlinClass(SoundCraftViewModel.class))) {
            BaseViewModel baseViewModel18 = viewModelProvider2.craftViewModel;
            if (baseViewModel18 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.actionbar.SoundCraftActionBarViewModel");
            }
            soundCraftActionBarViewModel = (SoundCraftActionBarViewModel) baseViewModel18;
        } else if (Intrinsics.areEqual(orCreateKotlinClass2, Reflection.getOrCreateKotlinClass(SoundCraftActionBarViewModel.class))) {
            soundCraftActionBarViewModel = viewModelProvider2.actionBarViewModel;
            if (soundCraftActionBarViewModel == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.actionbar.SoundCraftActionBarViewModel");
            }
        } else if (Intrinsics.areEqual(orCreateKotlinClass2, Reflection.getOrCreateKotlinClass(NoiseControlBoxViewModel.class))) {
            BaseViewModel baseViewModel19 = viewModelProvider2.noiseControlBoxViewModel;
            if (baseViewModel19 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.actionbar.SoundCraftActionBarViewModel");
            }
            soundCraftActionBarViewModel = (SoundCraftActionBarViewModel) baseViewModel19;
        } else if (Intrinsics.areEqual(orCreateKotlinClass2, Reflection.getOrCreateKotlinClass(AudioEffectBoxViewModel.class))) {
            BaseViewModel baseViewModel20 = viewModelProvider2.audioEffectBoxViewModel;
            if (baseViewModel20 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.actionbar.SoundCraftActionBarViewModel");
            }
            soundCraftActionBarViewModel = (SoundCraftActionBarViewModel) baseViewModel20;
        } else if (Intrinsics.areEqual(orCreateKotlinClass2, Reflection.getOrCreateKotlinClass(AudioEffectHeaderViewModel.class))) {
            BaseViewModel baseViewModel21 = viewModelProvider2.audioEffectHeaderViewModel;
            if (baseViewModel21 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.actionbar.SoundCraftActionBarViewModel");
            }
            soundCraftActionBarViewModel = (SoundCraftActionBarViewModel) baseViewModel21;
        } else if (Intrinsics.areEqual(orCreateKotlinClass2, Reflection.getOrCreateKotlinClass(WearableLinkBoxViewModel.class))) {
            BaseViewModel baseViewModel22 = viewModelProvider2.wearableLinkBoxViewModel;
            if (baseViewModel22 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.actionbar.SoundCraftActionBarViewModel");
            }
            soundCraftActionBarViewModel = (SoundCraftActionBarViewModel) baseViewModel22;
        } else if (Intrinsics.areEqual(orCreateKotlinClass2, Reflection.getOrCreateKotlinClass(SpatialAudioViewModel.class))) {
            BaseViewModel baseViewModel23 = viewModelProvider2.spatialAudioViewModel;
            if (baseViewModel23 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.actionbar.SoundCraftActionBarViewModel");
            }
            soundCraftActionBarViewModel = (SoundCraftActionBarViewModel) baseViewModel23;
        } else if (Intrinsics.areEqual(orCreateKotlinClass2, Reflection.getOrCreateKotlinClass(EqualizerViewModel.class))) {
            BaseViewModel baseViewModel24 = viewModelProvider2.equalizerViewModel;
            if (baseViewModel24 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.actionbar.SoundCraftActionBarViewModel");
            }
            soundCraftActionBarViewModel = (SoundCraftActionBarViewModel) baseViewModel24;
        } else if (Intrinsics.areEqual(orCreateKotlinClass2, Reflection.getOrCreateKotlinClass(VoiceBoostViewModel.class))) {
            BaseViewModel baseViewModel25 = viewModelProvider2.voiceBoostViewModel;
            if (baseViewModel25 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.actionbar.SoundCraftActionBarViewModel");
            }
            soundCraftActionBarViewModel = (SoundCraftActionBarViewModel) baseViewModel25;
        } else if (Intrinsics.areEqual(orCreateKotlinClass2, Reflection.getOrCreateKotlinClass(VolumeNormalizationViewModel.class))) {
            BaseViewModel baseViewModel26 = viewModelProvider2.volumeNormalizationViewModel;
            if (baseViewModel26 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.actionbar.SoundCraftActionBarViewModel");
            }
            soundCraftActionBarViewModel = (SoundCraftActionBarViewModel) baseViewModel26;
        } else if (Intrinsics.areEqual(orCreateKotlinClass2, Reflection.getOrCreateKotlinClass(ActiveNoiseCancelingViewModel.class))) {
            BaseViewModel baseViewModel27 = viewModelProvider2.activeNoiseCancelingViewModel;
            if (baseViewModel27 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.actionbar.SoundCraftActionBarViewModel");
            }
            soundCraftActionBarViewModel = (SoundCraftActionBarViewModel) baseViewModel27;
        } else if (Intrinsics.areEqual(orCreateKotlinClass2, Reflection.getOrCreateKotlinClass(AdaptiveViewModel.class))) {
            BaseViewModel baseViewModel28 = viewModelProvider2.adaptiveViewModel;
            if (baseViewModel28 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.actionbar.SoundCraftActionBarViewModel");
            }
            soundCraftActionBarViewModel = (SoundCraftActionBarViewModel) baseViewModel28;
        } else if (Intrinsics.areEqual(orCreateKotlinClass2, Reflection.getOrCreateKotlinClass(AmbientSoundViewModel.class))) {
            BaseViewModel baseViewModel29 = viewModelProvider2.ambientSoundViewModel;
            if (baseViewModel29 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.actionbar.SoundCraftActionBarViewModel");
            }
            soundCraftActionBarViewModel = (SoundCraftActionBarViewModel) baseViewModel29;
        } else if (Intrinsics.areEqual(orCreateKotlinClass2, Reflection.getOrCreateKotlinClass(NoiseCancelingLevelViewModel.class))) {
            BaseViewModel baseViewModel30 = viewModelProvider2.noiseCancelingViewModel;
            if (baseViewModel30 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.actionbar.SoundCraftActionBarViewModel");
            }
            soundCraftActionBarViewModel = (SoundCraftActionBarViewModel) baseViewModel30;
        } else if (Intrinsics.areEqual(orCreateKotlinClass2, Reflection.getOrCreateKotlinClass(NoiseControlEffectBoxViewModel.class))) {
            BaseViewModel baseViewModel31 = viewModelProvider2.noiseControlEffectBoxViewModel;
            if (baseViewModel31 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.actionbar.SoundCraftActionBarViewModel");
            }
            soundCraftActionBarViewModel = (SoundCraftActionBarViewModel) baseViewModel31;
        } else if (Intrinsics.areEqual(orCreateKotlinClass2, Reflection.getOrCreateKotlinClass(NoiseControlOffViewModel.class))) {
            BaseViewModel baseViewModel32 = viewModelProvider2.noiseControlOffViewModel;
            if (baseViewModel32 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.actionbar.SoundCraftActionBarViewModel");
            }
            soundCraftActionBarViewModel = (SoundCraftActionBarViewModel) baseViewModel32;
        } else if (Intrinsics.areEqual(orCreateKotlinClass2, Reflection.getOrCreateKotlinClass(NoiseCancelingSwitchBarViewModel.class))) {
            BaseViewModel baseViewModel33 = viewModelProvider2.noiseCancelingSwitchBarViewModel;
            if (baseViewModel33 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.actionbar.SoundCraftActionBarViewModel");
            }
            soundCraftActionBarViewModel = (SoundCraftActionBarViewModel) baseViewModel33;
        } else {
            if (!Intrinsics.areEqual(orCreateKotlinClass2, Reflection.getOrCreateKotlinClass(RoutineTestViewModel.class))) {
                throw new RuntimeException();
            }
            BaseViewModel baseViewModel34 = viewModelProvider2.routineTestViewModel;
            if (baseViewModel34 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.actionbar.SoundCraftActionBarViewModel");
            }
            soundCraftActionBarViewModel = (SoundCraftActionBarViewModel) baseViewModel34;
        }
        soundCraftActionBarView.viewModel = soundCraftActionBarViewModel;
        ((MutableLiveData) soundCraftActionBarViewModel.title$delegate.getValue()).observe(soundCraftActionBarView, new Observer() { // from class: com.android.systemui.qs.bar.soundcraft.view.actionbar.SoundCraftActionBarView$bindViewModel$1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                String str = (String) obj;
                SoundCraftActionBarBinding soundCraftActionBarBinding2 = SoundCraftActionBarView.this.viewBinding;
                if (soundCraftActionBarBinding2 == null) {
                    soundCraftActionBarBinding2 = null;
                }
                soundCraftActionBarBinding2.title.setText(str);
            }
        });
        SoundCraftActionBarBinding soundCraftActionBarBinding2 = soundCraftActionBarView.viewBinding;
        if (soundCraftActionBarBinding2 == null) {
            soundCraftActionBarBinding2 = null;
        }
        soundCraftActionBarBinding2.backButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.bar.soundcraft.view.actionbar.SoundCraftActionBarView$bindViewModel$2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SoundCraftActionBarViewModel soundCraftActionBarViewModel2 = SoundCraftActionBarView.this.viewModel;
                if (soundCraftActionBarViewModel2 == null) {
                    soundCraftActionBarViewModel2 = null;
                }
                soundCraftActionBarViewModel2.getClass();
                Log.d("SoundCraft.SoundCraftActionBarViewModel", "onBackButtonClick");
                SecQSPanelController secQSPanelController = (SecQSPanelController) soundCraftActionBarViewModel2.qsPanelControllerLazy.get();
                DetailAdapter detailAdapter = (DetailAdapter) soundCraftActionBarViewModel2.soundCraftAdapter.get();
                SecQSPanelControllerBase.Record record = secQSPanelController.mDetailRecord;
                if (record == null || record.mDetailAdapter != detailAdapter) {
                    return;
                }
                secQSPanelController.showDetail(record, false);
            }
        });
        SoundCraftActionBarViewModel soundCraftActionBarViewModel2 = soundCraftActionBarView.viewModel;
        if (soundCraftActionBarViewModel2 == null) {
            soundCraftActionBarViewModel2 = null;
        }
        soundCraftActionBarViewModel2.notifyChange();
        NoiseControlBoxViewBinding noiseControlBoxViewBinding = soundCraftViewBinding.noiseControlBox;
        NoiseControlBoxView noiseControlBoxView = noiseControlBoxViewBinding.root;
        noiseControlBoxView.viewBinding = noiseControlBoxViewBinding;
        noiseControlBoxView.bindViewModel(getViewModelProvider());
        AudioEffectBoxViewBinding audioEffectBoxViewBinding = soundCraftViewBinding.audioEffectBox;
        final AudioEffectBoxView audioEffectBoxView = audioEffectBoxViewBinding.root;
        audioEffectBoxView.viewBinding = audioEffectBoxViewBinding;
        final SoundCraftViewModelProvider viewModelProvider3 = getViewModelProvider();
        Log.d("SoundCraft.AudioEffectBoxView", "bindViewModel");
        audioEffectBoxView.vmProvider = viewModelProvider3;
        ClassReference orCreateKotlinClass3 = Reflection.getOrCreateKotlinClass(AudioEffectBoxViewModel.class);
        if (Intrinsics.areEqual(orCreateKotlinClass3, Reflection.getOrCreateKotlinClass(SoundCraftViewModel.class))) {
            BaseViewModel baseViewModel35 = viewModelProvider3.craftViewModel;
            if (baseViewModel35 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectBoxViewModel");
            }
            audioEffectBoxViewModel = (AudioEffectBoxViewModel) baseViewModel35;
        } else if (Intrinsics.areEqual(orCreateKotlinClass3, Reflection.getOrCreateKotlinClass(SoundCraftActionBarViewModel.class))) {
            BaseViewModel baseViewModel36 = viewModelProvider3.actionBarViewModel;
            if (baseViewModel36 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectBoxViewModel");
            }
            audioEffectBoxViewModel = (AudioEffectBoxViewModel) baseViewModel36;
        } else if (Intrinsics.areEqual(orCreateKotlinClass3, Reflection.getOrCreateKotlinClass(NoiseControlBoxViewModel.class))) {
            BaseViewModel baseViewModel37 = viewModelProvider3.noiseControlBoxViewModel;
            if (baseViewModel37 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectBoxViewModel");
            }
            audioEffectBoxViewModel = (AudioEffectBoxViewModel) baseViewModel37;
        } else if (Intrinsics.areEqual(orCreateKotlinClass3, Reflection.getOrCreateKotlinClass(AudioEffectBoxViewModel.class))) {
            audioEffectBoxViewModel = viewModelProvider3.audioEffectBoxViewModel;
            if (audioEffectBoxViewModel == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectBoxViewModel");
            }
        } else if (Intrinsics.areEqual(orCreateKotlinClass3, Reflection.getOrCreateKotlinClass(AudioEffectHeaderViewModel.class))) {
            BaseViewModel baseViewModel38 = viewModelProvider3.audioEffectHeaderViewModel;
            if (baseViewModel38 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectBoxViewModel");
            }
            audioEffectBoxViewModel = (AudioEffectBoxViewModel) baseViewModel38;
        } else if (Intrinsics.areEqual(orCreateKotlinClass3, Reflection.getOrCreateKotlinClass(WearableLinkBoxViewModel.class))) {
            BaseViewModel baseViewModel39 = viewModelProvider3.wearableLinkBoxViewModel;
            if (baseViewModel39 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectBoxViewModel");
            }
            audioEffectBoxViewModel = (AudioEffectBoxViewModel) baseViewModel39;
        } else if (Intrinsics.areEqual(orCreateKotlinClass3, Reflection.getOrCreateKotlinClass(SpatialAudioViewModel.class))) {
            BaseViewModel baseViewModel40 = viewModelProvider3.spatialAudioViewModel;
            if (baseViewModel40 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectBoxViewModel");
            }
            audioEffectBoxViewModel = (AudioEffectBoxViewModel) baseViewModel40;
        } else if (Intrinsics.areEqual(orCreateKotlinClass3, Reflection.getOrCreateKotlinClass(EqualizerViewModel.class))) {
            BaseViewModel baseViewModel41 = viewModelProvider3.equalizerViewModel;
            if (baseViewModel41 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectBoxViewModel");
            }
            audioEffectBoxViewModel = (AudioEffectBoxViewModel) baseViewModel41;
        } else if (Intrinsics.areEqual(orCreateKotlinClass3, Reflection.getOrCreateKotlinClass(VoiceBoostViewModel.class))) {
            BaseViewModel baseViewModel42 = viewModelProvider3.voiceBoostViewModel;
            if (baseViewModel42 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectBoxViewModel");
            }
            audioEffectBoxViewModel = (AudioEffectBoxViewModel) baseViewModel42;
        } else if (Intrinsics.areEqual(orCreateKotlinClass3, Reflection.getOrCreateKotlinClass(VolumeNormalizationViewModel.class))) {
            BaseViewModel baseViewModel43 = viewModelProvider3.volumeNormalizationViewModel;
            if (baseViewModel43 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectBoxViewModel");
            }
            audioEffectBoxViewModel = (AudioEffectBoxViewModel) baseViewModel43;
        } else if (Intrinsics.areEqual(orCreateKotlinClass3, Reflection.getOrCreateKotlinClass(ActiveNoiseCancelingViewModel.class))) {
            BaseViewModel baseViewModel44 = viewModelProvider3.activeNoiseCancelingViewModel;
            if (baseViewModel44 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectBoxViewModel");
            }
            audioEffectBoxViewModel = (AudioEffectBoxViewModel) baseViewModel44;
        } else if (Intrinsics.areEqual(orCreateKotlinClass3, Reflection.getOrCreateKotlinClass(AdaptiveViewModel.class))) {
            BaseViewModel baseViewModel45 = viewModelProvider3.adaptiveViewModel;
            if (baseViewModel45 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectBoxViewModel");
            }
            audioEffectBoxViewModel = (AudioEffectBoxViewModel) baseViewModel45;
        } else if (Intrinsics.areEqual(orCreateKotlinClass3, Reflection.getOrCreateKotlinClass(AmbientSoundViewModel.class))) {
            BaseViewModel baseViewModel46 = viewModelProvider3.ambientSoundViewModel;
            if (baseViewModel46 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectBoxViewModel");
            }
            audioEffectBoxViewModel = (AudioEffectBoxViewModel) baseViewModel46;
        } else if (Intrinsics.areEqual(orCreateKotlinClass3, Reflection.getOrCreateKotlinClass(NoiseCancelingLevelViewModel.class))) {
            BaseViewModel baseViewModel47 = viewModelProvider3.noiseCancelingViewModel;
            if (baseViewModel47 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectBoxViewModel");
            }
            audioEffectBoxViewModel = (AudioEffectBoxViewModel) baseViewModel47;
        } else if (Intrinsics.areEqual(orCreateKotlinClass3, Reflection.getOrCreateKotlinClass(NoiseControlEffectBoxViewModel.class))) {
            BaseViewModel baseViewModel48 = viewModelProvider3.noiseControlEffectBoxViewModel;
            if (baseViewModel48 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectBoxViewModel");
            }
            audioEffectBoxViewModel = (AudioEffectBoxViewModel) baseViewModel48;
        } else if (Intrinsics.areEqual(orCreateKotlinClass3, Reflection.getOrCreateKotlinClass(NoiseControlOffViewModel.class))) {
            BaseViewModel baseViewModel49 = viewModelProvider3.noiseControlOffViewModel;
            if (baseViewModel49 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectBoxViewModel");
            }
            audioEffectBoxViewModel = (AudioEffectBoxViewModel) baseViewModel49;
        } else if (Intrinsics.areEqual(orCreateKotlinClass3, Reflection.getOrCreateKotlinClass(NoiseCancelingSwitchBarViewModel.class))) {
            BaseViewModel baseViewModel50 = viewModelProvider3.noiseCancelingSwitchBarViewModel;
            if (baseViewModel50 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectBoxViewModel");
            }
            audioEffectBoxViewModel = (AudioEffectBoxViewModel) baseViewModel50;
        } else {
            if (!Intrinsics.areEqual(orCreateKotlinClass3, Reflection.getOrCreateKotlinClass(RoutineTestViewModel.class))) {
                throw new RuntimeException();
            }
            BaseViewModel baseViewModel51 = viewModelProvider3.routineTestViewModel;
            if (baseViewModel51 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectBoxViewModel");
            }
            audioEffectBoxViewModel = (AudioEffectBoxViewModel) baseViewModel51;
        }
        audioEffectBoxViewModel.showSpatialAudio.observe(audioEffectBoxView, new Observer() { // from class: com.android.systemui.qs.bar.soundcraft.view.audioeffect.AudioEffectBoxView$bindViewModel$1$1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                AudioEffectHeaderViewModel audioEffectHeaderViewModel2;
                ClassReference orCreateKotlinClass4 = Reflection.getOrCreateKotlinClass(AudioEffectHeaderViewModel.class);
                boolean areEqual = Intrinsics.areEqual(orCreateKotlinClass4, Reflection.getOrCreateKotlinClass(SoundCraftViewModel.class));
                SoundCraftViewModelProvider soundCraftViewModelProvider = SoundCraftViewModelProvider.this;
                if (areEqual) {
                    BaseViewModel baseViewModel52 = soundCraftViewModelProvider.craftViewModel;
                    if (baseViewModel52 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectHeaderViewModel");
                    }
                    audioEffectHeaderViewModel2 = (AudioEffectHeaderViewModel) baseViewModel52;
                } else if (Intrinsics.areEqual(orCreateKotlinClass4, Reflection.getOrCreateKotlinClass(SoundCraftActionBarViewModel.class))) {
                    BaseViewModel baseViewModel53 = soundCraftViewModelProvider.actionBarViewModel;
                    if (baseViewModel53 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectHeaderViewModel");
                    }
                    audioEffectHeaderViewModel2 = (AudioEffectHeaderViewModel) baseViewModel53;
                } else if (Intrinsics.areEqual(orCreateKotlinClass4, Reflection.getOrCreateKotlinClass(NoiseControlBoxViewModel.class))) {
                    BaseViewModel baseViewModel54 = soundCraftViewModelProvider.noiseControlBoxViewModel;
                    if (baseViewModel54 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectHeaderViewModel");
                    }
                    audioEffectHeaderViewModel2 = (AudioEffectHeaderViewModel) baseViewModel54;
                } else if (Intrinsics.areEqual(orCreateKotlinClass4, Reflection.getOrCreateKotlinClass(AudioEffectBoxViewModel.class))) {
                    BaseViewModel baseViewModel55 = soundCraftViewModelProvider.audioEffectBoxViewModel;
                    if (baseViewModel55 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectHeaderViewModel");
                    }
                    audioEffectHeaderViewModel2 = (AudioEffectHeaderViewModel) baseViewModel55;
                } else if (Intrinsics.areEqual(orCreateKotlinClass4, Reflection.getOrCreateKotlinClass(AudioEffectHeaderViewModel.class))) {
                    audioEffectHeaderViewModel2 = soundCraftViewModelProvider.audioEffectHeaderViewModel;
                    if (audioEffectHeaderViewModel2 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectHeaderViewModel");
                    }
                } else if (Intrinsics.areEqual(orCreateKotlinClass4, Reflection.getOrCreateKotlinClass(WearableLinkBoxViewModel.class))) {
                    BaseViewModel baseViewModel56 = soundCraftViewModelProvider.wearableLinkBoxViewModel;
                    if (baseViewModel56 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectHeaderViewModel");
                    }
                    audioEffectHeaderViewModel2 = (AudioEffectHeaderViewModel) baseViewModel56;
                } else if (Intrinsics.areEqual(orCreateKotlinClass4, Reflection.getOrCreateKotlinClass(SpatialAudioViewModel.class))) {
                    BaseViewModel baseViewModel57 = soundCraftViewModelProvider.spatialAudioViewModel;
                    if (baseViewModel57 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectHeaderViewModel");
                    }
                    audioEffectHeaderViewModel2 = (AudioEffectHeaderViewModel) baseViewModel57;
                } else if (Intrinsics.areEqual(orCreateKotlinClass4, Reflection.getOrCreateKotlinClass(EqualizerViewModel.class))) {
                    BaseViewModel baseViewModel58 = soundCraftViewModelProvider.equalizerViewModel;
                    if (baseViewModel58 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectHeaderViewModel");
                    }
                    audioEffectHeaderViewModel2 = (AudioEffectHeaderViewModel) baseViewModel58;
                } else if (Intrinsics.areEqual(orCreateKotlinClass4, Reflection.getOrCreateKotlinClass(VoiceBoostViewModel.class))) {
                    BaseViewModel baseViewModel59 = soundCraftViewModelProvider.voiceBoostViewModel;
                    if (baseViewModel59 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectHeaderViewModel");
                    }
                    audioEffectHeaderViewModel2 = (AudioEffectHeaderViewModel) baseViewModel59;
                } else if (Intrinsics.areEqual(orCreateKotlinClass4, Reflection.getOrCreateKotlinClass(VolumeNormalizationViewModel.class))) {
                    BaseViewModel baseViewModel60 = soundCraftViewModelProvider.volumeNormalizationViewModel;
                    if (baseViewModel60 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectHeaderViewModel");
                    }
                    audioEffectHeaderViewModel2 = (AudioEffectHeaderViewModel) baseViewModel60;
                } else if (Intrinsics.areEqual(orCreateKotlinClass4, Reflection.getOrCreateKotlinClass(ActiveNoiseCancelingViewModel.class))) {
                    BaseViewModel baseViewModel61 = soundCraftViewModelProvider.activeNoiseCancelingViewModel;
                    if (baseViewModel61 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectHeaderViewModel");
                    }
                    audioEffectHeaderViewModel2 = (AudioEffectHeaderViewModel) baseViewModel61;
                } else if (Intrinsics.areEqual(orCreateKotlinClass4, Reflection.getOrCreateKotlinClass(AdaptiveViewModel.class))) {
                    BaseViewModel baseViewModel62 = soundCraftViewModelProvider.adaptiveViewModel;
                    if (baseViewModel62 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectHeaderViewModel");
                    }
                    audioEffectHeaderViewModel2 = (AudioEffectHeaderViewModel) baseViewModel62;
                } else if (Intrinsics.areEqual(orCreateKotlinClass4, Reflection.getOrCreateKotlinClass(AmbientSoundViewModel.class))) {
                    BaseViewModel baseViewModel63 = soundCraftViewModelProvider.ambientSoundViewModel;
                    if (baseViewModel63 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectHeaderViewModel");
                    }
                    audioEffectHeaderViewModel2 = (AudioEffectHeaderViewModel) baseViewModel63;
                } else if (Intrinsics.areEqual(orCreateKotlinClass4, Reflection.getOrCreateKotlinClass(NoiseCancelingLevelViewModel.class))) {
                    BaseViewModel baseViewModel64 = soundCraftViewModelProvider.noiseCancelingViewModel;
                    if (baseViewModel64 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectHeaderViewModel");
                    }
                    audioEffectHeaderViewModel2 = (AudioEffectHeaderViewModel) baseViewModel64;
                } else if (Intrinsics.areEqual(orCreateKotlinClass4, Reflection.getOrCreateKotlinClass(NoiseControlEffectBoxViewModel.class))) {
                    BaseViewModel baseViewModel65 = soundCraftViewModelProvider.noiseControlEffectBoxViewModel;
                    if (baseViewModel65 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectHeaderViewModel");
                    }
                    audioEffectHeaderViewModel2 = (AudioEffectHeaderViewModel) baseViewModel65;
                } else if (Intrinsics.areEqual(orCreateKotlinClass4, Reflection.getOrCreateKotlinClass(NoiseControlOffViewModel.class))) {
                    BaseViewModel baseViewModel66 = soundCraftViewModelProvider.noiseControlOffViewModel;
                    if (baseViewModel66 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectHeaderViewModel");
                    }
                    audioEffectHeaderViewModel2 = (AudioEffectHeaderViewModel) baseViewModel66;
                } else if (Intrinsics.areEqual(orCreateKotlinClass4, Reflection.getOrCreateKotlinClass(NoiseCancelingSwitchBarViewModel.class))) {
                    BaseViewModel baseViewModel67 = soundCraftViewModelProvider.noiseCancelingSwitchBarViewModel;
                    if (baseViewModel67 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectHeaderViewModel");
                    }
                    audioEffectHeaderViewModel2 = (AudioEffectHeaderViewModel) baseViewModel67;
                } else {
                    if (!Intrinsics.areEqual(orCreateKotlinClass4, Reflection.getOrCreateKotlinClass(RoutineTestViewModel.class))) {
                        throw new RuntimeException();
                    }
                    BaseViewModel baseViewModel68 = soundCraftViewModelProvider.routineTestViewModel;
                    if (baseViewModel68 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectHeaderViewModel");
                    }
                    audioEffectHeaderViewModel2 = (AudioEffectHeaderViewModel) baseViewModel68;
                }
                audioEffectHeaderViewModel2.notifyChange();
                BaseAudioEffectItemView baseAudioEffectItemView = audioEffectBoxView.spatialAudioView;
                if (baseAudioEffectItemView != null) {
                    baseAudioEffectItemView.update();
                }
            }
        });
        audioEffectBoxViewModel.showEqualizer.observe(audioEffectBoxView, new Observer() { // from class: com.android.systemui.qs.bar.soundcraft.view.audioeffect.AudioEffectBoxView$bindViewModel$1$2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BaseAudioEffectItemView baseAudioEffectItemView = AudioEffectBoxView.this.equalizerView;
                if (baseAudioEffectItemView != null) {
                    baseAudioEffectItemView.update();
                }
            }
        });
        audioEffectBoxViewModel.showVoiceBoost.observe(audioEffectBoxView, new Observer() { // from class: com.android.systemui.qs.bar.soundcraft.view.audioeffect.AudioEffectBoxView$bindViewModel$1$3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BaseAudioEffectItemView baseAudioEffectItemView = AudioEffectBoxView.this.voiceBoostView;
                if (baseAudioEffectItemView != null) {
                    baseAudioEffectItemView.update();
                }
            }
        });
        audioEffectBoxViewModel.showVolumeNormalization.observe(audioEffectBoxView, new Observer() { // from class: com.android.systemui.qs.bar.soundcraft.view.audioeffect.AudioEffectBoxView$bindViewModel$1$4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BaseAudioEffectItemView baseAudioEffectItemView = AudioEffectBoxView.this.volumeNormalizationView;
                if (baseAudioEffectItemView != null) {
                    baseAudioEffectItemView.update();
                }
            }
        });
        AudioEffectHeaderViewBinding audioEffectHeaderViewBinding = audioEffectBoxViewBinding.header;
        final AudioEffectHeaderView audioEffectHeaderView = audioEffectHeaderViewBinding.root;
        audioEffectHeaderView.viewBinding = audioEffectHeaderViewBinding;
        SoundCraftViewModelProvider viewModelProvider4 = getViewModelProvider();
        ClassReference orCreateKotlinClass4 = Reflection.getOrCreateKotlinClass(AudioEffectHeaderViewModel.class);
        if (Intrinsics.areEqual(orCreateKotlinClass4, Reflection.getOrCreateKotlinClass(SoundCraftViewModel.class))) {
            BaseViewModel baseViewModel52 = viewModelProvider4.craftViewModel;
            if (baseViewModel52 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectHeaderViewModel");
            }
            audioEffectHeaderViewModel = (AudioEffectHeaderViewModel) baseViewModel52;
        } else if (Intrinsics.areEqual(orCreateKotlinClass4, Reflection.getOrCreateKotlinClass(SoundCraftActionBarViewModel.class))) {
            BaseViewModel baseViewModel53 = viewModelProvider4.actionBarViewModel;
            if (baseViewModel53 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectHeaderViewModel");
            }
            audioEffectHeaderViewModel = (AudioEffectHeaderViewModel) baseViewModel53;
        } else if (Intrinsics.areEqual(orCreateKotlinClass4, Reflection.getOrCreateKotlinClass(NoiseControlBoxViewModel.class))) {
            BaseViewModel baseViewModel54 = viewModelProvider4.noiseControlBoxViewModel;
            if (baseViewModel54 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectHeaderViewModel");
            }
            audioEffectHeaderViewModel = (AudioEffectHeaderViewModel) baseViewModel54;
        } else if (Intrinsics.areEqual(orCreateKotlinClass4, Reflection.getOrCreateKotlinClass(AudioEffectBoxViewModel.class))) {
            BaseViewModel baseViewModel55 = viewModelProvider4.audioEffectBoxViewModel;
            if (baseViewModel55 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectHeaderViewModel");
            }
            audioEffectHeaderViewModel = (AudioEffectHeaderViewModel) baseViewModel55;
        } else if (Intrinsics.areEqual(orCreateKotlinClass4, Reflection.getOrCreateKotlinClass(AudioEffectHeaderViewModel.class))) {
            audioEffectHeaderViewModel = viewModelProvider4.audioEffectHeaderViewModel;
            if (audioEffectHeaderViewModel == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectHeaderViewModel");
            }
        } else if (Intrinsics.areEqual(orCreateKotlinClass4, Reflection.getOrCreateKotlinClass(WearableLinkBoxViewModel.class))) {
            BaseViewModel baseViewModel56 = viewModelProvider4.wearableLinkBoxViewModel;
            if (baseViewModel56 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectHeaderViewModel");
            }
            audioEffectHeaderViewModel = (AudioEffectHeaderViewModel) baseViewModel56;
        } else if (Intrinsics.areEqual(orCreateKotlinClass4, Reflection.getOrCreateKotlinClass(SpatialAudioViewModel.class))) {
            BaseViewModel baseViewModel57 = viewModelProvider4.spatialAudioViewModel;
            if (baseViewModel57 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectHeaderViewModel");
            }
            audioEffectHeaderViewModel = (AudioEffectHeaderViewModel) baseViewModel57;
        } else if (Intrinsics.areEqual(orCreateKotlinClass4, Reflection.getOrCreateKotlinClass(EqualizerViewModel.class))) {
            BaseViewModel baseViewModel58 = viewModelProvider4.equalizerViewModel;
            if (baseViewModel58 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectHeaderViewModel");
            }
            audioEffectHeaderViewModel = (AudioEffectHeaderViewModel) baseViewModel58;
        } else if (Intrinsics.areEqual(orCreateKotlinClass4, Reflection.getOrCreateKotlinClass(VoiceBoostViewModel.class))) {
            BaseViewModel baseViewModel59 = viewModelProvider4.voiceBoostViewModel;
            if (baseViewModel59 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectHeaderViewModel");
            }
            audioEffectHeaderViewModel = (AudioEffectHeaderViewModel) baseViewModel59;
        } else if (Intrinsics.areEqual(orCreateKotlinClass4, Reflection.getOrCreateKotlinClass(VolumeNormalizationViewModel.class))) {
            BaseViewModel baseViewModel60 = viewModelProvider4.volumeNormalizationViewModel;
            if (baseViewModel60 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectHeaderViewModel");
            }
            audioEffectHeaderViewModel = (AudioEffectHeaderViewModel) baseViewModel60;
        } else if (Intrinsics.areEqual(orCreateKotlinClass4, Reflection.getOrCreateKotlinClass(ActiveNoiseCancelingViewModel.class))) {
            BaseViewModel baseViewModel61 = viewModelProvider4.activeNoiseCancelingViewModel;
            if (baseViewModel61 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectHeaderViewModel");
            }
            audioEffectHeaderViewModel = (AudioEffectHeaderViewModel) baseViewModel61;
        } else if (Intrinsics.areEqual(orCreateKotlinClass4, Reflection.getOrCreateKotlinClass(AdaptiveViewModel.class))) {
            BaseViewModel baseViewModel62 = viewModelProvider4.adaptiveViewModel;
            if (baseViewModel62 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectHeaderViewModel");
            }
            audioEffectHeaderViewModel = (AudioEffectHeaderViewModel) baseViewModel62;
        } else if (Intrinsics.areEqual(orCreateKotlinClass4, Reflection.getOrCreateKotlinClass(AmbientSoundViewModel.class))) {
            BaseViewModel baseViewModel63 = viewModelProvider4.ambientSoundViewModel;
            if (baseViewModel63 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectHeaderViewModel");
            }
            audioEffectHeaderViewModel = (AudioEffectHeaderViewModel) baseViewModel63;
        } else if (Intrinsics.areEqual(orCreateKotlinClass4, Reflection.getOrCreateKotlinClass(NoiseCancelingLevelViewModel.class))) {
            BaseViewModel baseViewModel64 = viewModelProvider4.noiseCancelingViewModel;
            if (baseViewModel64 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectHeaderViewModel");
            }
            audioEffectHeaderViewModel = (AudioEffectHeaderViewModel) baseViewModel64;
        } else if (Intrinsics.areEqual(orCreateKotlinClass4, Reflection.getOrCreateKotlinClass(NoiseControlEffectBoxViewModel.class))) {
            BaseViewModel baseViewModel65 = viewModelProvider4.noiseControlEffectBoxViewModel;
            if (baseViewModel65 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectHeaderViewModel");
            }
            audioEffectHeaderViewModel = (AudioEffectHeaderViewModel) baseViewModel65;
        } else if (Intrinsics.areEqual(orCreateKotlinClass4, Reflection.getOrCreateKotlinClass(NoiseControlOffViewModel.class))) {
            BaseViewModel baseViewModel66 = viewModelProvider4.noiseControlOffViewModel;
            if (baseViewModel66 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectHeaderViewModel");
            }
            audioEffectHeaderViewModel = (AudioEffectHeaderViewModel) baseViewModel66;
        } else if (Intrinsics.areEqual(orCreateKotlinClass4, Reflection.getOrCreateKotlinClass(NoiseCancelingSwitchBarViewModel.class))) {
            BaseViewModel baseViewModel67 = viewModelProvider4.noiseCancelingSwitchBarViewModel;
            if (baseViewModel67 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectHeaderViewModel");
            }
            audioEffectHeaderViewModel = (AudioEffectHeaderViewModel) baseViewModel67;
        } else {
            if (!Intrinsics.areEqual(orCreateKotlinClass4, Reflection.getOrCreateKotlinClass(RoutineTestViewModel.class))) {
                throw new RuntimeException();
            }
            BaseViewModel baseViewModel68 = viewModelProvider4.routineTestViewModel;
            if (baseViewModel68 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectHeaderViewModel");
            }
            audioEffectHeaderViewModel = (AudioEffectHeaderViewModel) baseViewModel68;
        }
        audioEffectHeaderView.viewModel = audioEffectHeaderViewModel;
        audioEffectHeaderViewModel.title.observe(audioEffectHeaderView, new Observer() { // from class: com.android.systemui.qs.bar.soundcraft.view.audioeffect.AudioEffectHeaderView$bindViewModel$1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                String str = (String) obj;
                AbstractC0000x2c234b15.m3m("update title : ", str, "SoundCraft.AudioEffectHeaderView");
                AudioEffectHeaderViewBinding audioEffectHeaderViewBinding2 = AudioEffectHeaderView.this.viewBinding;
                if (audioEffectHeaderViewBinding2 == null) {
                    audioEffectHeaderViewBinding2 = null;
                }
                audioEffectHeaderViewBinding2.title.setText(str);
            }
        });
        AudioEffectHeaderViewModel audioEffectHeaderViewModel2 = audioEffectHeaderView.viewModel;
        if (audioEffectHeaderViewModel2 == null) {
            audioEffectHeaderViewModel2 = null;
        }
        audioEffectHeaderViewModel2.icon.observe(audioEffectHeaderView, new Observer() { // from class: com.android.systemui.qs.bar.soundcraft.view.audioeffect.AudioEffectHeaderView$bindViewModel$2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                Drawable drawable = (Drawable) obj;
                Log.d("SoundCraft.AudioEffectHeaderView", "update icon : " + drawable);
                AudioEffectHeaderView audioEffectHeaderView2 = AudioEffectHeaderView.this;
                AudioEffectHeaderViewBinding audioEffectHeaderViewBinding2 = audioEffectHeaderView2.viewBinding;
                if (audioEffectHeaderViewBinding2 == null) {
                    audioEffectHeaderViewBinding2 = null;
                }
                audioEffectHeaderViewBinding2.icon.setImageDrawable(drawable);
                if (drawable != null) {
                    ViewVisibilityUtil viewVisibilityUtil = ViewVisibilityUtil.INSTANCE;
                    AudioEffectHeaderViewBinding audioEffectHeaderViewBinding3 = audioEffectHeaderView2.viewBinding;
                    ImageView imageView = (audioEffectHeaderViewBinding3 != null ? audioEffectHeaderViewBinding3 : null).icon;
                    viewVisibilityUtil.getClass();
                    imageView.setVisibility(0);
                    return;
                }
                ViewVisibilityUtil viewVisibilityUtil2 = ViewVisibilityUtil.INSTANCE;
                AudioEffectHeaderViewBinding audioEffectHeaderViewBinding4 = audioEffectHeaderView2.viewBinding;
                ImageView imageView2 = (audioEffectHeaderViewBinding4 != null ? audioEffectHeaderViewBinding4 : null).icon;
                viewVisibilityUtil2.getClass();
                ViewVisibilityUtil.setGone(imageView2);
            }
        });
        AudioEffectHeaderViewModel audioEffectHeaderViewModel3 = audioEffectHeaderView.viewModel;
        if (audioEffectHeaderViewModel3 == null) {
            audioEffectHeaderViewModel3 = null;
        }
        audioEffectHeaderViewModel3.notifyChange();
        WearableLinkBoxViewBinding wearableLinkBoxViewBinding = soundCraftViewBinding.wearableLinkBox;
        final WearableLinkBoxView wearableLinkBoxView = wearableLinkBoxViewBinding.root;
        wearableLinkBoxView.viewBinding = wearableLinkBoxViewBinding;
        SoundCraftViewModelProvider viewModelProvider5 = getViewModelProvider();
        Log.d("SoundCraft.WearableLinkBoxView", "bindViewModel binding : viewModel=" + viewModelProvider5);
        ClassReference orCreateKotlinClass5 = Reflection.getOrCreateKotlinClass(WearableLinkBoxViewModel.class);
        if (Intrinsics.areEqual(orCreateKotlinClass5, Reflection.getOrCreateKotlinClass(SoundCraftViewModel.class))) {
            BaseViewModel baseViewModel69 = viewModelProvider5.craftViewModel;
            if (baseViewModel69 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.wearable.WearableLinkBoxViewModel");
            }
            wearableLinkBoxViewModel = (WearableLinkBoxViewModel) baseViewModel69;
        } else if (Intrinsics.areEqual(orCreateKotlinClass5, Reflection.getOrCreateKotlinClass(SoundCraftActionBarViewModel.class))) {
            BaseViewModel baseViewModel70 = viewModelProvider5.actionBarViewModel;
            if (baseViewModel70 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.wearable.WearableLinkBoxViewModel");
            }
            wearableLinkBoxViewModel = (WearableLinkBoxViewModel) baseViewModel70;
        } else if (Intrinsics.areEqual(orCreateKotlinClass5, Reflection.getOrCreateKotlinClass(NoiseControlBoxViewModel.class))) {
            BaseViewModel baseViewModel71 = viewModelProvider5.noiseControlBoxViewModel;
            if (baseViewModel71 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.wearable.WearableLinkBoxViewModel");
            }
            wearableLinkBoxViewModel = (WearableLinkBoxViewModel) baseViewModel71;
        } else if (Intrinsics.areEqual(orCreateKotlinClass5, Reflection.getOrCreateKotlinClass(AudioEffectBoxViewModel.class))) {
            BaseViewModel baseViewModel72 = viewModelProvider5.audioEffectBoxViewModel;
            if (baseViewModel72 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.wearable.WearableLinkBoxViewModel");
            }
            wearableLinkBoxViewModel = (WearableLinkBoxViewModel) baseViewModel72;
        } else if (Intrinsics.areEqual(orCreateKotlinClass5, Reflection.getOrCreateKotlinClass(AudioEffectHeaderViewModel.class))) {
            BaseViewModel baseViewModel73 = viewModelProvider5.audioEffectHeaderViewModel;
            if (baseViewModel73 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.wearable.WearableLinkBoxViewModel");
            }
            wearableLinkBoxViewModel = (WearableLinkBoxViewModel) baseViewModel73;
        } else if (Intrinsics.areEqual(orCreateKotlinClass5, Reflection.getOrCreateKotlinClass(WearableLinkBoxViewModel.class))) {
            wearableLinkBoxViewModel = viewModelProvider5.wearableLinkBoxViewModel;
            if (wearableLinkBoxViewModel == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.wearable.WearableLinkBoxViewModel");
            }
        } else if (Intrinsics.areEqual(orCreateKotlinClass5, Reflection.getOrCreateKotlinClass(SpatialAudioViewModel.class))) {
            BaseViewModel baseViewModel74 = viewModelProvider5.spatialAudioViewModel;
            if (baseViewModel74 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.wearable.WearableLinkBoxViewModel");
            }
            wearableLinkBoxViewModel = (WearableLinkBoxViewModel) baseViewModel74;
        } else if (Intrinsics.areEqual(orCreateKotlinClass5, Reflection.getOrCreateKotlinClass(EqualizerViewModel.class))) {
            BaseViewModel baseViewModel75 = viewModelProvider5.equalizerViewModel;
            if (baseViewModel75 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.wearable.WearableLinkBoxViewModel");
            }
            wearableLinkBoxViewModel = (WearableLinkBoxViewModel) baseViewModel75;
        } else if (Intrinsics.areEqual(orCreateKotlinClass5, Reflection.getOrCreateKotlinClass(VoiceBoostViewModel.class))) {
            BaseViewModel baseViewModel76 = viewModelProvider5.voiceBoostViewModel;
            if (baseViewModel76 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.wearable.WearableLinkBoxViewModel");
            }
            wearableLinkBoxViewModel = (WearableLinkBoxViewModel) baseViewModel76;
        } else if (Intrinsics.areEqual(orCreateKotlinClass5, Reflection.getOrCreateKotlinClass(VolumeNormalizationViewModel.class))) {
            BaseViewModel baseViewModel77 = viewModelProvider5.volumeNormalizationViewModel;
            if (baseViewModel77 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.wearable.WearableLinkBoxViewModel");
            }
            wearableLinkBoxViewModel = (WearableLinkBoxViewModel) baseViewModel77;
        } else if (Intrinsics.areEqual(orCreateKotlinClass5, Reflection.getOrCreateKotlinClass(ActiveNoiseCancelingViewModel.class))) {
            BaseViewModel baseViewModel78 = viewModelProvider5.activeNoiseCancelingViewModel;
            if (baseViewModel78 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.wearable.WearableLinkBoxViewModel");
            }
            wearableLinkBoxViewModel = (WearableLinkBoxViewModel) baseViewModel78;
        } else if (Intrinsics.areEqual(orCreateKotlinClass5, Reflection.getOrCreateKotlinClass(AdaptiveViewModel.class))) {
            BaseViewModel baseViewModel79 = viewModelProvider5.adaptiveViewModel;
            if (baseViewModel79 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.wearable.WearableLinkBoxViewModel");
            }
            wearableLinkBoxViewModel = (WearableLinkBoxViewModel) baseViewModel79;
        } else if (Intrinsics.areEqual(orCreateKotlinClass5, Reflection.getOrCreateKotlinClass(AmbientSoundViewModel.class))) {
            BaseViewModel baseViewModel80 = viewModelProvider5.ambientSoundViewModel;
            if (baseViewModel80 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.wearable.WearableLinkBoxViewModel");
            }
            wearableLinkBoxViewModel = (WearableLinkBoxViewModel) baseViewModel80;
        } else if (Intrinsics.areEqual(orCreateKotlinClass5, Reflection.getOrCreateKotlinClass(NoiseCancelingLevelViewModel.class))) {
            BaseViewModel baseViewModel81 = viewModelProvider5.noiseCancelingViewModel;
            if (baseViewModel81 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.wearable.WearableLinkBoxViewModel");
            }
            wearableLinkBoxViewModel = (WearableLinkBoxViewModel) baseViewModel81;
        } else if (Intrinsics.areEqual(orCreateKotlinClass5, Reflection.getOrCreateKotlinClass(NoiseControlEffectBoxViewModel.class))) {
            BaseViewModel baseViewModel82 = viewModelProvider5.noiseControlEffectBoxViewModel;
            if (baseViewModel82 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.wearable.WearableLinkBoxViewModel");
            }
            wearableLinkBoxViewModel = (WearableLinkBoxViewModel) baseViewModel82;
        } else if (Intrinsics.areEqual(orCreateKotlinClass5, Reflection.getOrCreateKotlinClass(NoiseControlOffViewModel.class))) {
            BaseViewModel baseViewModel83 = viewModelProvider5.noiseControlOffViewModel;
            if (baseViewModel83 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.wearable.WearableLinkBoxViewModel");
            }
            wearableLinkBoxViewModel = (WearableLinkBoxViewModel) baseViewModel83;
        } else if (Intrinsics.areEqual(orCreateKotlinClass5, Reflection.getOrCreateKotlinClass(NoiseCancelingSwitchBarViewModel.class))) {
            BaseViewModel baseViewModel84 = viewModelProvider5.noiseCancelingSwitchBarViewModel;
            if (baseViewModel84 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.wearable.WearableLinkBoxViewModel");
            }
            wearableLinkBoxViewModel = (WearableLinkBoxViewModel) baseViewModel84;
        } else {
            if (!Intrinsics.areEqual(orCreateKotlinClass5, Reflection.getOrCreateKotlinClass(RoutineTestViewModel.class))) {
                throw new RuntimeException();
            }
            BaseViewModel baseViewModel85 = viewModelProvider5.routineTestViewModel;
            if (baseViewModel85 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.wearable.WearableLinkBoxViewModel");
            }
            wearableLinkBoxViewModel = (WearableLinkBoxViewModel) baseViewModel85;
        }
        wearableLinkBoxView.viewModel = wearableLinkBoxViewModel;
        WearableLinkBoxViewBinding wearableLinkBoxViewBinding2 = wearableLinkBoxView.viewBinding;
        if (wearableLinkBoxViewBinding2 == null) {
            wearableLinkBoxViewBinding2 = null;
        }
        wearableLinkBoxViewBinding2.root.setVisibility(0);
        WearableLinkBoxViewBinding wearableLinkBoxViewBinding3 = wearableLinkBoxView.viewBinding;
        if (wearableLinkBoxViewBinding3 == null) {
            wearableLinkBoxViewBinding3 = null;
        }
        wearableLinkBoxViewBinding3.root.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.bar.soundcraft.view.wearable.WearableLinkBoxView$bindViewModel$1
            /* JADX WARN: Code restructure failed: missing block: B:57:0x0169, code lost:
            
                if (r6 != null) goto L48;
             */
            /* JADX WARN: Removed duplicated region for block: B:27:0x0178  */
            /* JADX WARN: Removed duplicated region for block: B:33:0x0194  */
            /* JADX WARN: Removed duplicated region for block: B:36:? A[RETURN, SYNTHETIC] */
            /* JADX WARN: Removed duplicated region for block: B:37:0x018f A[SYNTHETIC] */
            /* JADX WARN: Removed duplicated region for block: B:63:0x01a2  */
            @Override // android.view.View.OnClickListener
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void onClick(View view) {
                String str;
                Cursor cursor;
                Cursor cursor2;
                Iterator it;
                Object obj;
                CachedBluetoothDevice cachedBluetoothDevice;
                String str2;
                WearableLinkBoxViewModel wearableLinkBoxViewModel2 = WearableLinkBoxView.this.viewModel;
                if (wearableLinkBoxViewModel2 == null) {
                    wearableLinkBoxViewModel2 = null;
                }
                BluetoothDevice activeDevice = wearableLinkBoxViewModel2.bluetoothDeviceManager.getActiveDevice();
                String address = activeDevice != null ? activeDevice.getAddress() : null;
                if (address == null) {
                    address = "";
                }
                LocalBluetoothManager localBluetoothManager = wearableLinkBoxViewModel2.localBtManager;
                if (localBluetoothManager == null) {
                    return;
                }
                Context context = wearableLinkBoxViewModel2.context;
                LocalBluetoothProfileManager localBluetoothProfileManager = localBluetoothManager.mProfileManager;
                boolean z = BluetoothUtils.DEBUG;
                ArrayList arrayList = new ArrayList();
                try {
                    try {
                        str = address;
                        try {
                            cursor2 = context.getContentResolver().query(Uri.parse("content://com.samsung.bt.btservice.btsettingsprovider/bonddevice"), null, "bond_state== 2", null, "timestamp DESC");
                        } catch (IllegalStateException e) {
                            e = e;
                            cursor2 = null;
                            try {
                                Log.e("BluetoothUtils", "getA2dpBondDevices :: Occurs IllegalStateException");
                                e.printStackTrace();
                            } catch (Throwable th) {
                                th = th;
                                cursor = cursor2;
                                cursor2 = cursor;
                                if (cursor2 != null) {
                                }
                                throw th;
                            }
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        cursor = null;
                        cursor2 = cursor;
                        if (cursor2 != null) {
                            cursor2.close();
                        }
                        throw th;
                    }
                } catch (IllegalStateException e2) {
                    e = e2;
                    str = address;
                }
                try {
                } catch (IllegalStateException e3) {
                    e = e3;
                    Log.e("BluetoothUtils", "getA2dpBondDevices :: Occurs IllegalStateException");
                    e.printStackTrace();
                } catch (Throwable th3) {
                    th = th3;
                    if (cursor2 != null) {
                    }
                    throw th;
                }
                if (cursor2 == null) {
                    Log.e("BluetoothUtils", "getA2dpBondDevices() :: query return empty list");
                    if (cursor2 != null) {
                    }
                    it = arrayList.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            obj = null;
                            break;
                        }
                        obj = it.next();
                        str2 = str;
                        if (Intrinsics.areEqual(str2, ((CachedBluetoothDevice) obj).getAddress())) {
                            break;
                        } else {
                            str = str2;
                        }
                    }
                    cachedBluetoothDevice = (CachedBluetoothDevice) obj;
                    if (cachedBluetoothDevice == null) {
                        cachedBluetoothDevice.shouldLaunchGM(ActivityThread.currentOpPackageName(), true);
                        return;
                    }
                    return;
                }
                Log.e("BluetoothUtils", "getA2dpBondDevices() :: cursor count: " + cursor2.getCount() + ", Columns : " + cursor2.getColumnCount());
                cursor2.moveToFirst();
                int columnIndex = cursor2.getColumnIndex("address");
                int columnIndex2 = cursor2.getColumnIndex("name");
                int columnIndex3 = cursor2.getColumnIndex("cod");
                int columnIndex4 = cursor2.getColumnIndex("bond_state");
                int columnIndex5 = cursor2.getColumnIndex("appearance");
                int columnIndex6 = cursor2.getColumnIndex("manufacturerdata");
                int columnIndex7 = cursor2.getColumnIndex(PhoneRestrictionPolicy.TIMESTAMP);
                int columnIndex8 = cursor2.getColumnIndex("linktype");
                int columnIndex9 = cursor2.getColumnIndex("uuids");
                while (!cursor2.isAfterLast()) {
                    BluetoothRestoredDevice bluetoothRestoredDevice = new BluetoothRestoredDevice(context, cursor2.getString(columnIndex));
                    bluetoothRestoredDevice.mName = cursor2.getString(columnIndex2);
                    bluetoothRestoredDevice.mCod = cursor2.getInt(columnIndex3);
                    bluetoothRestoredDevice.mBondState = cursor2.getInt(columnIndex4);
                    bluetoothRestoredDevice.mAppearance = cursor2.getInt(columnIndex5);
                    bluetoothRestoredDevice.mManufacturerData = BluetoothUtils.stringToByte(cursor2.getString(columnIndex6));
                    int i = columnIndex3;
                    int i2 = columnIndex4;
                    bluetoothRestoredDevice.mTimeStamp = cursor2.getLong(columnIndex7);
                    bluetoothRestoredDevice.mLinkType = cursor2.getInt(columnIndex8);
                    String string = cursor2.getString(columnIndex9);
                    String[] stringToken = BluetoothUtils.getStringToken(string);
                    if (stringToken != null) {
                        bluetoothRestoredDevice.mUuids = BluetoothUtils.makeParcelUuids(stringToken);
                    }
                    String[] stringToken2 = BluetoothUtils.getStringToken(string);
                    if (stringToken2 != null && BluetoothUuid.containsAnyUuid(BluetoothUtils.makeParcelUuids(stringToken2), A2dpProfile.SINK_UUIDS)) {
                        arrayList.add(new CachedBluetoothDevice(context, localBluetoothProfileManager, bluetoothRestoredDevice));
                        Log.d("BluetoothUtils", "getA2dpBondDevices - " + cursor2.getString(columnIndex2));
                    }
                    cursor2.moveToNext();
                    columnIndex3 = i;
                    columnIndex4 = i2;
                }
                cursor2.close();
                it = arrayList.iterator();
                while (true) {
                    if (it.hasNext()) {
                    }
                    str = str2;
                }
                cachedBluetoothDevice = (CachedBluetoothDevice) obj;
                if (cachedBluetoothDevice == null) {
                }
            }
        });
        RoutineTestViewBinding routineTestViewBinding = soundCraftViewBinding.routineTest;
        if (routineTestViewBinding != null) {
            final RoutineTestView routineTestView = routineTestViewBinding.root;
            routineTestView.viewBinding = routineTestViewBinding;
            SoundCraftViewModelProvider viewModelProvider6 = getViewModelProvider();
            ClassReference orCreateKotlinClass6 = Reflection.getOrCreateKotlinClass(RoutineTestViewModel.class);
            if (Intrinsics.areEqual(orCreateKotlinClass6, Reflection.getOrCreateKotlinClass(SoundCraftViewModel.class))) {
                BaseViewModel baseViewModel86 = viewModelProvider6.craftViewModel;
                if (baseViewModel86 == null) {
                    throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.routine.RoutineTestViewModel");
                }
                routineTestViewModel = (RoutineTestViewModel) baseViewModel86;
            } else if (Intrinsics.areEqual(orCreateKotlinClass6, Reflection.getOrCreateKotlinClass(SoundCraftActionBarViewModel.class))) {
                BaseViewModel baseViewModel87 = viewModelProvider6.actionBarViewModel;
                if (baseViewModel87 == null) {
                    throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.routine.RoutineTestViewModel");
                }
                routineTestViewModel = (RoutineTestViewModel) baseViewModel87;
            } else if (Intrinsics.areEqual(orCreateKotlinClass6, Reflection.getOrCreateKotlinClass(NoiseControlBoxViewModel.class))) {
                BaseViewModel baseViewModel88 = viewModelProvider6.noiseControlBoxViewModel;
                if (baseViewModel88 == null) {
                    throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.routine.RoutineTestViewModel");
                }
                routineTestViewModel = (RoutineTestViewModel) baseViewModel88;
            } else if (Intrinsics.areEqual(orCreateKotlinClass6, Reflection.getOrCreateKotlinClass(AudioEffectBoxViewModel.class))) {
                BaseViewModel baseViewModel89 = viewModelProvider6.audioEffectBoxViewModel;
                if (baseViewModel89 == null) {
                    throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.routine.RoutineTestViewModel");
                }
                routineTestViewModel = (RoutineTestViewModel) baseViewModel89;
            } else if (Intrinsics.areEqual(orCreateKotlinClass6, Reflection.getOrCreateKotlinClass(AudioEffectHeaderViewModel.class))) {
                BaseViewModel baseViewModel90 = viewModelProvider6.audioEffectHeaderViewModel;
                if (baseViewModel90 == null) {
                    throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.routine.RoutineTestViewModel");
                }
                routineTestViewModel = (RoutineTestViewModel) baseViewModel90;
            } else if (Intrinsics.areEqual(orCreateKotlinClass6, Reflection.getOrCreateKotlinClass(WearableLinkBoxViewModel.class))) {
                BaseViewModel baseViewModel91 = viewModelProvider6.wearableLinkBoxViewModel;
                if (baseViewModel91 == null) {
                    throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.routine.RoutineTestViewModel");
                }
                routineTestViewModel = (RoutineTestViewModel) baseViewModel91;
            } else if (Intrinsics.areEqual(orCreateKotlinClass6, Reflection.getOrCreateKotlinClass(SpatialAudioViewModel.class))) {
                BaseViewModel baseViewModel92 = viewModelProvider6.spatialAudioViewModel;
                if (baseViewModel92 == null) {
                    throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.routine.RoutineTestViewModel");
                }
                routineTestViewModel = (RoutineTestViewModel) baseViewModel92;
            } else if (Intrinsics.areEqual(orCreateKotlinClass6, Reflection.getOrCreateKotlinClass(EqualizerViewModel.class))) {
                BaseViewModel baseViewModel93 = viewModelProvider6.equalizerViewModel;
                if (baseViewModel93 == null) {
                    throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.routine.RoutineTestViewModel");
                }
                routineTestViewModel = (RoutineTestViewModel) baseViewModel93;
            } else if (Intrinsics.areEqual(orCreateKotlinClass6, Reflection.getOrCreateKotlinClass(VoiceBoostViewModel.class))) {
                BaseViewModel baseViewModel94 = viewModelProvider6.voiceBoostViewModel;
                if (baseViewModel94 == null) {
                    throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.routine.RoutineTestViewModel");
                }
                routineTestViewModel = (RoutineTestViewModel) baseViewModel94;
            } else if (Intrinsics.areEqual(orCreateKotlinClass6, Reflection.getOrCreateKotlinClass(VolumeNormalizationViewModel.class))) {
                BaseViewModel baseViewModel95 = viewModelProvider6.volumeNormalizationViewModel;
                if (baseViewModel95 == null) {
                    throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.routine.RoutineTestViewModel");
                }
                routineTestViewModel = (RoutineTestViewModel) baseViewModel95;
            } else if (Intrinsics.areEqual(orCreateKotlinClass6, Reflection.getOrCreateKotlinClass(ActiveNoiseCancelingViewModel.class))) {
                BaseViewModel baseViewModel96 = viewModelProvider6.activeNoiseCancelingViewModel;
                if (baseViewModel96 == null) {
                    throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.routine.RoutineTestViewModel");
                }
                routineTestViewModel = (RoutineTestViewModel) baseViewModel96;
            } else if (Intrinsics.areEqual(orCreateKotlinClass6, Reflection.getOrCreateKotlinClass(AdaptiveViewModel.class))) {
                BaseViewModel baseViewModel97 = viewModelProvider6.adaptiveViewModel;
                if (baseViewModel97 == null) {
                    throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.routine.RoutineTestViewModel");
                }
                routineTestViewModel = (RoutineTestViewModel) baseViewModel97;
            } else if (Intrinsics.areEqual(orCreateKotlinClass6, Reflection.getOrCreateKotlinClass(AmbientSoundViewModel.class))) {
                BaseViewModel baseViewModel98 = viewModelProvider6.ambientSoundViewModel;
                if (baseViewModel98 == null) {
                    throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.routine.RoutineTestViewModel");
                }
                routineTestViewModel = (RoutineTestViewModel) baseViewModel98;
            } else if (Intrinsics.areEqual(orCreateKotlinClass6, Reflection.getOrCreateKotlinClass(NoiseCancelingLevelViewModel.class))) {
                BaseViewModel baseViewModel99 = viewModelProvider6.noiseCancelingViewModel;
                if (baseViewModel99 == null) {
                    throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.routine.RoutineTestViewModel");
                }
                routineTestViewModel = (RoutineTestViewModel) baseViewModel99;
            } else if (Intrinsics.areEqual(orCreateKotlinClass6, Reflection.getOrCreateKotlinClass(NoiseControlEffectBoxViewModel.class))) {
                BaseViewModel baseViewModel100 = viewModelProvider6.noiseControlEffectBoxViewModel;
                if (baseViewModel100 == null) {
                    throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.routine.RoutineTestViewModel");
                }
                routineTestViewModel = (RoutineTestViewModel) baseViewModel100;
            } else if (Intrinsics.areEqual(orCreateKotlinClass6, Reflection.getOrCreateKotlinClass(NoiseControlOffViewModel.class))) {
                BaseViewModel baseViewModel101 = viewModelProvider6.noiseControlOffViewModel;
                if (baseViewModel101 == null) {
                    throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.routine.RoutineTestViewModel");
                }
                routineTestViewModel = (RoutineTestViewModel) baseViewModel101;
            } else if (Intrinsics.areEqual(orCreateKotlinClass6, Reflection.getOrCreateKotlinClass(NoiseCancelingSwitchBarViewModel.class))) {
                BaseViewModel baseViewModel102 = viewModelProvider6.noiseCancelingSwitchBarViewModel;
                if (baseViewModel102 == null) {
                    throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.routine.RoutineTestViewModel");
                }
                routineTestViewModel = (RoutineTestViewModel) baseViewModel102;
            } else {
                if (!Intrinsics.areEqual(orCreateKotlinClass6, Reflection.getOrCreateKotlinClass(RoutineTestViewModel.class))) {
                    throw new RuntimeException();
                }
                routineTestViewModel = viewModelProvider6.routineTestViewModel;
                if (routineTestViewModel == null) {
                    throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.routine.RoutineTestViewModel");
                }
            }
            routineTestView.viewModel = routineTestViewModel;
            routineTestViewModel.routineCount.observe(routineTestView, new Observer() { // from class: com.android.systemui.qs.bar.soundcraft.view.routine.RoutineTestView$bindViewModel$1
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    Integer num = (Integer) obj;
                    RoutineTestViewBinding routineTestViewBinding2 = RoutineTestView.this.viewBinding;
                    if (routineTestViewBinding2 == null) {
                        routineTestViewBinding2 = null;
                    }
                    routineTestViewBinding2.routineCountText.setText(String.valueOf(num));
                }
            });
            RoutineTestViewBinding routineTestViewBinding2 = routineTestView.viewBinding;
            if (routineTestViewBinding2 == null) {
                routineTestViewBinding2 = null;
            }
            routineTestViewBinding2.startButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.bar.soundcraft.view.routine.RoutineTestView$bindViewModel$2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    final RoutineTestViewModel routineTestViewModel2 = RoutineTestView.this.viewModel;
                    if (routineTestViewModel2 == null) {
                        routineTestViewModel2 = null;
                    }
                    Log.d("SoundCraft.RoutineTestViewModel", "onStartButtonClick : playingAppPackage=" + routineTestViewModel2.audioPlaybackManager.getPlayingAppPackage());
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.android.systemui.qs.bar.soundcraft.viewmodel.routine.RoutineTestViewModel$onStartButtonClick$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            RoutineTestViewModel.this.getClass();
                        }
                    }, 3000L);
                }
            });
            RoutineTestViewBinding routineTestViewBinding3 = routineTestView.viewBinding;
            if (routineTestViewBinding3 == null) {
                routineTestViewBinding3 = null;
            }
            routineTestViewBinding3.updateButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.bar.soundcraft.view.routine.RoutineTestView$bindViewModel$3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    RoutineTestViewModel routineTestViewModel2 = RoutineTestView.this.viewModel;
                    if (routineTestViewModel2 == null) {
                        routineTestViewModel2 = null;
                    }
                    String playingAppPackage = routineTestViewModel2.audioPlaybackManager.getPlayingAppPackage();
                    if (playingAppPackage == null) {
                        return;
                    }
                    Log.d("SoundCraft.RoutineTestViewModel", "onUpdateButtonClick : playingAppPackage=".concat(playingAppPackage));
                    RoutineManager routineManager = routineTestViewModel2.routineManager;
                    String routineId = routineManager.getRoutineId(playingAppPackage);
                    if (routineId != null) {
                        Log.d("SoundCraft.RoutineTestViewModel", "onUpdateButtonClick : routineId=".concat(routineId));
                        routineManager.updateRoutine(playingAppPackage, routineId, routineTestViewModel2.modelProvider.budsInfo);
                    }
                }
            });
            RoutineTestViewBinding routineTestViewBinding4 = routineTestView.viewBinding;
            if (routineTestViewBinding4 == null) {
                routineTestViewBinding4 = null;
            }
            routineTestViewBinding4.stopButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.bar.soundcraft.view.routine.RoutineTestView$bindViewModel$4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    RoutineTestViewModel routineTestViewModel2 = RoutineTestView.this.viewModel;
                    if (routineTestViewModel2 == null) {
                        routineTestViewModel2 = null;
                    }
                    routineTestViewModel2.getClass();
                    Log.d("SoundCraft.RoutineTestViewModel", "onStopButtonClick");
                }
            });
            RoutineTestViewModel routineTestViewModel2 = routineTestView.viewModel;
            (routineTestViewModel2 != null ? routineTestViewModel2 : null).getClass();
        }
    }

    @Override // com.android.systemui.plugins.qs.DetailAdapter
    public final View createDetailView(Context context, View view, ViewGroup viewGroup) {
        SoundCraftViewModel soundCraftViewModel;
        Log.d("SoundCraft.QpDetailAdapter", "createDetailView :convertView=" + view + ", parent=" + viewGroup + ", viewModelProvider=" + getViewModelProvider());
        if (context == null) {
            return new View(context);
        }
        if (view != null) {
            ViewParent parent = view.getParent();
            ViewGroup viewGroup2 = parent instanceof ViewGroup ? (ViewGroup) parent : null;
            if (viewGroup2 != null) {
                viewGroup2.removeAllViews();
            }
        }
        SoundCraftViewBinding soundCraftViewBinding = this.viewBinding;
        if (soundCraftViewBinding != null) {
            ViewParent parent2 = soundCraftViewBinding.root.getParent();
            ViewGroup viewGroup3 = parent2 instanceof ViewGroup ? (ViewGroup) parent2 : null;
            if (viewGroup3 != null) {
                viewGroup3.removeAllViews();
            }
        }
        int i = SoundCraftViewBindingFactory.$r8$clinit;
        SoundCraftViewBinding soundCraftViewBinding2 = new SoundCraftViewBinding(LayoutInflater.from(context).inflate(R.layout.soundcraft_layout, viewGroup, false));
        this.viewBinding = soundCraftViewBinding2;
        bindViewModel(soundCraftViewBinding2);
        SoundCraftViewModelProvider viewModelProvider = getViewModelProvider();
        ClassReference orCreateKotlinClass = Reflection.getOrCreateKotlinClass(SoundCraftViewModel.class);
        if (Intrinsics.areEqual(orCreateKotlinClass, Reflection.getOrCreateKotlinClass(SoundCraftViewModel.class))) {
            soundCraftViewModel = viewModelProvider.craftViewModel;
            if (soundCraftViewModel == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.SoundCraftViewModel");
            }
        } else if (Intrinsics.areEqual(orCreateKotlinClass, Reflection.getOrCreateKotlinClass(SoundCraftActionBarViewModel.class))) {
            BaseViewModel baseViewModel = viewModelProvider.actionBarViewModel;
            if (baseViewModel == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.SoundCraftViewModel");
            }
            soundCraftViewModel = (SoundCraftViewModel) baseViewModel;
        } else if (Intrinsics.areEqual(orCreateKotlinClass, Reflection.getOrCreateKotlinClass(NoiseControlBoxViewModel.class))) {
            BaseViewModel baseViewModel2 = viewModelProvider.noiseControlBoxViewModel;
            if (baseViewModel2 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.SoundCraftViewModel");
            }
            soundCraftViewModel = (SoundCraftViewModel) baseViewModel2;
        } else if (Intrinsics.areEqual(orCreateKotlinClass, Reflection.getOrCreateKotlinClass(AudioEffectBoxViewModel.class))) {
            BaseViewModel baseViewModel3 = viewModelProvider.audioEffectBoxViewModel;
            if (baseViewModel3 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.SoundCraftViewModel");
            }
            soundCraftViewModel = (SoundCraftViewModel) baseViewModel3;
        } else if (Intrinsics.areEqual(orCreateKotlinClass, Reflection.getOrCreateKotlinClass(AudioEffectHeaderViewModel.class))) {
            BaseViewModel baseViewModel4 = viewModelProvider.audioEffectHeaderViewModel;
            if (baseViewModel4 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.SoundCraftViewModel");
            }
            soundCraftViewModel = (SoundCraftViewModel) baseViewModel4;
        } else if (Intrinsics.areEqual(orCreateKotlinClass, Reflection.getOrCreateKotlinClass(WearableLinkBoxViewModel.class))) {
            BaseViewModel baseViewModel5 = viewModelProvider.wearableLinkBoxViewModel;
            if (baseViewModel5 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.SoundCraftViewModel");
            }
            soundCraftViewModel = (SoundCraftViewModel) baseViewModel5;
        } else if (Intrinsics.areEqual(orCreateKotlinClass, Reflection.getOrCreateKotlinClass(SpatialAudioViewModel.class))) {
            BaseViewModel baseViewModel6 = viewModelProvider.spatialAudioViewModel;
            if (baseViewModel6 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.SoundCraftViewModel");
            }
            soundCraftViewModel = (SoundCraftViewModel) baseViewModel6;
        } else if (Intrinsics.areEqual(orCreateKotlinClass, Reflection.getOrCreateKotlinClass(EqualizerViewModel.class))) {
            BaseViewModel baseViewModel7 = viewModelProvider.equalizerViewModel;
            if (baseViewModel7 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.SoundCraftViewModel");
            }
            soundCraftViewModel = (SoundCraftViewModel) baseViewModel7;
        } else if (Intrinsics.areEqual(orCreateKotlinClass, Reflection.getOrCreateKotlinClass(VoiceBoostViewModel.class))) {
            BaseViewModel baseViewModel8 = viewModelProvider.voiceBoostViewModel;
            if (baseViewModel8 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.SoundCraftViewModel");
            }
            soundCraftViewModel = (SoundCraftViewModel) baseViewModel8;
        } else if (Intrinsics.areEqual(orCreateKotlinClass, Reflection.getOrCreateKotlinClass(VolumeNormalizationViewModel.class))) {
            BaseViewModel baseViewModel9 = viewModelProvider.volumeNormalizationViewModel;
            if (baseViewModel9 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.SoundCraftViewModel");
            }
            soundCraftViewModel = (SoundCraftViewModel) baseViewModel9;
        } else if (Intrinsics.areEqual(orCreateKotlinClass, Reflection.getOrCreateKotlinClass(ActiveNoiseCancelingViewModel.class))) {
            BaseViewModel baseViewModel10 = viewModelProvider.activeNoiseCancelingViewModel;
            if (baseViewModel10 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.SoundCraftViewModel");
            }
            soundCraftViewModel = (SoundCraftViewModel) baseViewModel10;
        } else if (Intrinsics.areEqual(orCreateKotlinClass, Reflection.getOrCreateKotlinClass(AdaptiveViewModel.class))) {
            BaseViewModel baseViewModel11 = viewModelProvider.adaptiveViewModel;
            if (baseViewModel11 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.SoundCraftViewModel");
            }
            soundCraftViewModel = (SoundCraftViewModel) baseViewModel11;
        } else if (Intrinsics.areEqual(orCreateKotlinClass, Reflection.getOrCreateKotlinClass(AmbientSoundViewModel.class))) {
            BaseViewModel baseViewModel12 = viewModelProvider.ambientSoundViewModel;
            if (baseViewModel12 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.SoundCraftViewModel");
            }
            soundCraftViewModel = (SoundCraftViewModel) baseViewModel12;
        } else if (Intrinsics.areEqual(orCreateKotlinClass, Reflection.getOrCreateKotlinClass(NoiseCancelingLevelViewModel.class))) {
            BaseViewModel baseViewModel13 = viewModelProvider.noiseCancelingViewModel;
            if (baseViewModel13 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.SoundCraftViewModel");
            }
            soundCraftViewModel = (SoundCraftViewModel) baseViewModel13;
        } else if (Intrinsics.areEqual(orCreateKotlinClass, Reflection.getOrCreateKotlinClass(NoiseControlEffectBoxViewModel.class))) {
            BaseViewModel baseViewModel14 = viewModelProvider.noiseControlEffectBoxViewModel;
            if (baseViewModel14 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.SoundCraftViewModel");
            }
            soundCraftViewModel = (SoundCraftViewModel) baseViewModel14;
        } else if (Intrinsics.areEqual(orCreateKotlinClass, Reflection.getOrCreateKotlinClass(NoiseControlOffViewModel.class))) {
            BaseViewModel baseViewModel15 = viewModelProvider.noiseControlOffViewModel;
            if (baseViewModel15 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.SoundCraftViewModel");
            }
            soundCraftViewModel = (SoundCraftViewModel) baseViewModel15;
        } else if (Intrinsics.areEqual(orCreateKotlinClass, Reflection.getOrCreateKotlinClass(NoiseCancelingSwitchBarViewModel.class))) {
            BaseViewModel baseViewModel16 = viewModelProvider.noiseCancelingSwitchBarViewModel;
            if (baseViewModel16 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.SoundCraftViewModel");
            }
            soundCraftViewModel = (SoundCraftViewModel) baseViewModel16;
        } else {
            if (!Intrinsics.areEqual(orCreateKotlinClass, Reflection.getOrCreateKotlinClass(RoutineTestViewModel.class))) {
                throw new RuntimeException();
            }
            BaseViewModel baseViewModel17 = viewModelProvider.routineTestViewModel;
            if (baseViewModel17 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.SoundCraftViewModel");
            }
            soundCraftViewModel = (SoundCraftViewModel) baseViewModel17;
        }
        soundCraftViewModel.onCreateView();
        return soundCraftViewBinding2.root;
    }

    @Override // com.android.systemui.plugins.qs.DetailAdapter
    public final int getMetricsCategory() {
        return 5030;
    }

    public final SoundCraftViewModelProvider getViewModelProvider() {
        SoundCraftViewModelProvider soundCraftViewModelProvider = this.viewModelProvider;
        if (soundCraftViewModelProvider != null) {
            return soundCraftViewModelProvider;
        }
        return null;
    }
}
