package com.android.systemui.audio.soundcraft.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.lifecycle.ViewTreeLifecycleOwner;
import com.android.systemui.Dependency;
import com.android.systemui.FontSizeUtils;
import com.android.systemui.R;
import com.android.systemui.audio.soundcraft.di.vm.SoundCraftViewModelFactory;
import com.android.systemui.audio.soundcraft.di.vm.SoundCraftViewModelStore;
import com.android.systemui.audio.soundcraft.di.vm.component.SoundCraftLocalViewModelStoreOwner;
import com.android.systemui.audio.soundcraft.di.vm.component.SoundCraftVMComponent;
import com.android.systemui.audio.soundcraft.di.vm.component.SoundCraftViewModelExt;
import com.android.systemui.audio.soundcraft.interfaces.routine.manager.RoutineManager;
import com.android.systemui.audio.soundcraft.model.ModelProvider;
import com.android.systemui.audio.soundcraft.utils.ViewUtils;
import com.android.systemui.audio.soundcraft.view.SoundCraftDetailPageView$bind$2$$ExternalSyntheticOutline0;
import com.android.systemui.audio.soundcraft.view.SoundCraftViewComponent;
import com.android.systemui.audio.soundcraft.view.actionbar.SoundCraftActionBarView;
import com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectBoxView;
import com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectHeaderView;
import com.android.systemui.audio.soundcraft.view.audioeffect.BaseAudioEffectItemView;
import com.android.systemui.audio.soundcraft.view.battery.BatteryInfoBoxView;
import com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlBoxView;
import com.android.systemui.audio.soundcraft.view.routine.RoutineTestView;
import com.android.systemui.audio.soundcraft.view.volume.SoundCraftRoundedCornerSeekBarDrawable;
import com.android.systemui.audio.soundcraft.view.volume.SoundCraftVolumeIcon;
import com.android.systemui.audio.soundcraft.view.volume.SoundCraftVolumeMotion;
import com.android.systemui.audio.soundcraft.view.volume.SoundCraftVolumeSeekBar;
import com.android.systemui.audio.soundcraft.view.volume.VolumeBarView;
import com.android.systemui.audio.soundcraft.viewbinding.SoundCraftViewBinding;
import com.android.systemui.audio.soundcraft.viewbinding.SoundCraftViewBindingFactory;
import com.android.systemui.audio.soundcraft.viewbinding.actionbar.SoundCraftActionBarBinding;
import com.android.systemui.audio.soundcraft.viewbinding.audioeffect.AudioEffectBoxLayoutBinding;
import com.android.systemui.audio.soundcraft.viewbinding.audioeffect.AudioEffectHeaderViewBinding;
import com.android.systemui.audio.soundcraft.viewbinding.battery.BatteryInfoBoxViewBinding;
import com.android.systemui.audio.soundcraft.viewbinding.noisecontrol.NoiseControlBoxViewBinding;
import com.android.systemui.audio.soundcraft.viewbinding.routine.RoutineTestViewBinding;
import com.android.systemui.audio.soundcraft.viewbinding.volume.VolumeBarViewBinding;
import com.android.systemui.audio.soundcraft.viewmodel.SoundCraftViewModel;
import com.android.systemui.audio.soundcraft.viewmodel.buds.audioeffect.SpatialAudioViewModel;
import com.android.systemui.audio.soundcraft.viewmodel.buds.noisecontrol.AmbientVolumeViewModel;
import com.android.systemui.audio.soundcraft.viewmodel.buds.noisecontrol.NoiseCancelingLevelViewModel;
import com.android.systemui.audio.soundcraft.viewmodel.buds.noisecontrol.NoiseCancelingSwitchBarViewModel;
import com.android.systemui.audio.soundcraft.viewmodel.common.actionbar.SoundCraftActionBarViewModel;
import com.android.systemui.audio.soundcraft.viewmodel.common.audioeffect.AudioEffectBoxViewModel;
import com.android.systemui.audio.soundcraft.viewmodel.common.audioeffect.AudioEffectHeaderViewModel;
import com.android.systemui.audio.soundcraft.viewmodel.common.audioeffect.EqualizerViewModel;
import com.android.systemui.audio.soundcraft.viewmodel.common.routine.RoutineTestViewModel;
import com.android.systemui.audio.soundcraft.viewmodel.common.volume.VolumeBarViewModel;
import com.android.systemui.audio.soundcraft.viewmodel.phone.audioeffect.DolbyViewModel;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.qs.SecQSDetailController;
import com.android.systemui.qs.bar.ColoredBGHelper;
import kotlin.Result;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class SoundCraftViewComponent implements LifecycleOwner, ViewModelStoreOwner, HasDefaultViewModelProviderFactory, SoundCraftVMComponent {
    public SoundCraftViewBinding binding;
    public final SoundCraftViewModelFactory defaultViewModelProviderFactory;
    public Lifecycle lifecycle;
    public final SoundCraftViewComponent$lifecycleObserver$1 lifecycleObserver;
    public final ModelProvider modelProvider;
    public LifecycleRegistry registry;
    public final SoundCraftViewModelStore viewModelStore;

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

    public SoundCraftViewComponent(SoundCraftViewModelFactory soundCraftViewModelFactory, ModelProvider modelProvider) {
        this.modelProvider = modelProvider;
        LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
        this.registry = lifecycleRegistry;
        this.lifecycle = lifecycleRegistry;
        this.viewModelStore = new SoundCraftViewModelStore();
        this.defaultViewModelProviderFactory = soundCraftViewModelFactory;
        this.lifecycleObserver = SoundCraftViewComponent$lifecycleObserver$1.INSTANCE;
        Log.d("SoundCraft.SoundCraftViewComponent", "initialized");
    }

    @Override // androidx.lifecycle.HasDefaultViewModelProviderFactory
    public final ViewModelProvider.Factory getDefaultViewModelProviderFactory() {
        return this.defaultViewModelProviderFactory;
    }

    @Override // androidx.lifecycle.LifecycleOwner
    public final Lifecycle getLifecycle() {
        return this.lifecycle;
    }

    @Override // androidx.lifecycle.ViewModelStoreOwner
    public final ViewModelStore getViewModelStore() {
        return this.viewModelStore;
    }

    public final void onCreate(Context context, ViewGroup viewGroup) {
        Log.d("SoundCraft.SoundCraftViewComponent", "onCreate");
        LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
        this.registry = lifecycleRegistry;
        this.lifecycle = lifecycleRegistry;
        lifecycleRegistry.addObserver(this.lifecycleObserver);
        this.registry.setCurrentState(Lifecycle.State.CREATED);
        int i = SoundCraftViewBindingFactory.$r8$clinit;
        final SoundCraftViewBinding soundCraftViewBinding = new SoundCraftViewBinding(LayoutInflater.from(context).inflate(R.layout.soundcraft_layout, viewGroup, false));
        final SoundCraftDetailPageView soundCraftDetailPageView = soundCraftViewBinding.detailPageContent;
        ViewTreeLifecycleOwner.set(soundCraftDetailPageView, this);
        soundCraftDetailPageView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.android.systemui.audio.soundcraft.view.SoundCraftViewComponent$createView$1$1
            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewAttachedToWindow(View view) {
                Log.d("SoundCraft.SoundCraftViewComponent", "onViewAttachedToWindow");
                SoundCraftViewComponent.this.registry.setCurrentState(Lifecycle.State.RESUMED);
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewDetachedFromWindow(View view) {
                Log.d("SoundCraft.SoundCraftViewComponent", "onViewDetachedFromWindow");
            }
        });
        Log.d("SoundCraft.SoundCraftViewBinding", "bindRoots");
        soundCraftDetailPageView.viewBinding = soundCraftViewBinding;
        LifecycleOwner lifecycleOwner = ViewTreeLifecycleOwner.get(soundCraftDetailPageView);
        Intrinsics.checkNotNull(lifecycleOwner);
        soundCraftDetailPageView.getViewModel().isBatteryInfoBoxVisible.observe(lifecycleOwner, new Observer() { // from class: com.android.systemui.audio.soundcraft.view.SoundCraftDetailPageView$bind$1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                Boolean bool = (Boolean) obj;
                SoundCraftViewBinding soundCraftViewBinding2 = SoundCraftViewBinding.this;
                BatteryInfoBoxView batteryInfoBoxView = soundCraftViewBinding2.batteryInfoBox.root;
                Intrinsics.checkNotNull(bool);
                batteryInfoBoxView.setVisibility(bool.booleanValue() ? 0 : 8);
                if (!bool.booleanValue()) {
                    soundCraftViewBinding2.volumeContainer.setBackground(null);
                    soundCraftViewBinding2.volumeContainer.setPadding(0, 0, 0, 0);
                    return;
                }
                int i2 = SoundCraftDetailPageView.$r8$clinit;
                SoundCraftViewModel viewModel = soundCraftDetailPageView.getViewModel();
                LinearLayout linearLayout = soundCraftViewBinding2.volumeContainer;
                ColoredBGHelper coloredBGHelper = viewModel.coloredBGHelper;
                coloredBGHelper.setBackGroundDrawable(linearLayout, coloredBGHelper.getBGColor());
            }
        });
        soundCraftDetailPageView.getViewModel().updateBatteryInfoBox.observe(lifecycleOwner, new Observer() { // from class: com.android.systemui.audio.soundcraft.view.SoundCraftDetailPageView$bind$2
            /* JADX WARN: Code restructure failed: missing block: B:33:0x0213, code lost:
            
                if (r2 != null) goto L112;
             */
            /* JADX WARN: Code restructure failed: missing block: B:73:0x0161, code lost:
            
                if (r6 != null) goto L67;
             */
            /* JADX WARN: Code restructure failed: missing block: B:88:0x01ac, code lost:
            
                if (r5 != null) goto L85;
             */
            @Override // androidx.lifecycle.Observer
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final void onChanged(java.lang.Object r14) {
                /*
                    Method dump skipped, instructions count: 596
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.audio.soundcraft.view.SoundCraftDetailPageView$bind$2.onChanged(java.lang.Object):void");
            }
        });
        soundCraftDetailPageView.getViewModel().isNoiseControlBoxVisible.observe(lifecycleOwner, new Observer() { // from class: com.android.systemui.audio.soundcraft.view.SoundCraftDetailPageView$bind$3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                Boolean bool = (Boolean) obj;
                SoundCraftViewBinding soundCraftViewBinding2 = SoundCraftViewBinding.this;
                NoiseControlBoxView noiseControlBoxView = soundCraftViewBinding2.noiseControlBox.root;
                Intrinsics.checkNotNull(bool);
                noiseControlBoxView.setVisibility(bool.booleanValue() ? 0 : 8);
                NoiseControlBoxViewBinding noiseControlBoxViewBinding = soundCraftViewBinding2.noiseControlBox;
                NoiseControlBoxView noiseControlBoxView2 = noiseControlBoxViewBinding.root;
                noiseControlBoxView2.getNoiseControlBoxViewModel().notifyChange();
                noiseControlBoxView2.getNoiseControlEffectBoxViewModel().notifyChange();
                noiseControlBoxView2.updateLayout();
                if (bool.booleanValue()) {
                    int i2 = SoundCraftDetailPageView.$r8$clinit;
                    ColoredBGHelper coloredBGHelper = soundCraftDetailPageView.getViewModel().coloredBGHelper;
                    coloredBGHelper.setBackGroundDrawable(noiseControlBoxViewBinding.root, coloredBGHelper.getBGColor());
                }
            }
        });
        soundCraftDetailPageView.getViewModel().updateNoiseControlBox.observe(lifecycleOwner, new Observer() { // from class: com.android.systemui.audio.soundcraft.view.SoundCraftDetailPageView$bind$4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                NoiseControlBoxView noiseControlBoxView = SoundCraftViewBinding.this.noiseControlBox.root;
                noiseControlBoxView.getNoiseControlEffectBoxViewModel().notifyChange();
                ((NoiseCancelingLevelViewModel) noiseControlBoxView.noiseCancelingLevelViewModel$delegate.getValue()).notifyChange();
                ((NoiseCancelingSwitchBarViewModel) noiseControlBoxView.noiseCancelingSwitchBarViewModel$delegate.getValue()).notifyChange();
                ((AmbientVolumeViewModel) noiseControlBoxView.ambientVolumeViewModel$delegate.getValue()).notifyChange();
                noiseControlBoxView.updateSeekBarView();
            }
        });
        soundCraftDetailPageView.getViewModel().updateEffectBox.observe(lifecycleOwner, new Observer() { // from class: com.android.systemui.audio.soundcraft.view.SoundCraftDetailPageView$bind$5
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                AudioEffectBoxView audioEffectBoxView = SoundCraftViewBinding.this.audioEffectBox.box;
                audioEffectBoxView.getClass();
                SoundCraftViewModelExt soundCraftViewModelExt = SoundCraftViewModelExt.INSTANCE;
                SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
                ViewModelStoreOwner viewModelStoreOwner = SoundCraftLocalViewModelStoreOwner.current;
                if (viewModelStoreOwner == null) {
                    throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner".toString());
                }
                SoundCraftViewComponent soundCraftViewComponent = (SoundCraftViewComponent) viewModelStoreOwner;
                ((AudioEffectBoxViewModel) SoundCraftDetailPageView$bind$2$$ExternalSyntheticOutline0.m(soundCraftViewModelExt, soundCraftViewComponent, soundCraftViewComponent, AudioEffectBoxViewModel.class)).notifyChange();
                Log.d("SoundCraft.AudioEffectBoxView", "updateLayout");
                AudioEffectBoxLayoutBinding audioEffectBoxLayoutBinding = audioEffectBoxView.viewBinding;
                if (audioEffectBoxLayoutBinding == null) {
                    audioEffectBoxLayoutBinding = null;
                }
                audioEffectBoxLayoutBinding.effectItemList.removeAllViews();
                ViewModelStoreOwner viewModelStoreOwner2 = SoundCraftLocalViewModelStoreOwner.current;
                if (viewModelStoreOwner2 == null) {
                    throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner".toString());
                }
                SoundCraftViewComponent soundCraftViewComponent2 = (SoundCraftViewComponent) viewModelStoreOwner2;
                AudioEffectBoxViewModel audioEffectBoxViewModel = (AudioEffectBoxViewModel) SoundCraftViewModelExt.get(soundCraftViewComponent2, AudioEffectBoxViewModel.class, SoundCraftViewModelExt.createDaggerViewModelFactory(soundCraftViewComponent2));
                Object value = audioEffectBoxViewModel.isDolbyVisible.getValue();
                Boolean bool = Boolean.TRUE;
                if (Intrinsics.areEqual(value, bool)) {
                    ViewUtils viewUtils = ViewUtils.INSTANCE;
                    AudioEffectBoxLayoutBinding audioEffectBoxLayoutBinding2 = audioEffectBoxView.viewBinding;
                    if (audioEffectBoxLayoutBinding2 == null) {
                        audioEffectBoxLayoutBinding2 = null;
                    }
                    LinearLayout linearLayout = audioEffectBoxLayoutBinding2.effectItemList;
                    ViewGroup rootView = ((BaseAudioEffectItemView) audioEffectBoxView.dolbyView$delegate.getValue()).getRootView();
                    viewUtils.getClass();
                    ViewUtils.addViewIfNotAttached(rootView, linearLayout);
                }
                if (Intrinsics.areEqual(audioEffectBoxViewModel.isSpatialAudioVisible.getValue(), bool)) {
                    ViewUtils viewUtils2 = ViewUtils.INSTANCE;
                    AudioEffectBoxLayoutBinding audioEffectBoxLayoutBinding3 = audioEffectBoxView.viewBinding;
                    if (audioEffectBoxLayoutBinding3 == null) {
                        audioEffectBoxLayoutBinding3 = null;
                    }
                    LinearLayout linearLayout2 = audioEffectBoxLayoutBinding3.effectItemList;
                    ViewGroup rootView2 = ((BaseAudioEffectItemView) audioEffectBoxView.spatialAudioView$delegate.getValue()).getRootView();
                    viewUtils2.getClass();
                    ViewUtils.addViewIfNotAttached(rootView2, linearLayout2);
                }
                if (Intrinsics.areEqual(audioEffectBoxViewModel.isEqualizerVisible.getValue(), bool)) {
                    audioEffectBoxView.addDivider();
                    ViewUtils viewUtils3 = ViewUtils.INSTANCE;
                    AudioEffectBoxLayoutBinding audioEffectBoxLayoutBinding4 = audioEffectBoxView.viewBinding;
                    if (audioEffectBoxLayoutBinding4 == null) {
                        audioEffectBoxLayoutBinding4 = null;
                    }
                    LinearLayout linearLayout3 = audioEffectBoxLayoutBinding4.effectItemList;
                    ViewGroup rootView3 = ((BaseAudioEffectItemView) audioEffectBoxView.equalizerView$delegate.getValue()).getRootView();
                    viewUtils3.getClass();
                    ViewUtils.addViewIfNotAttached(rootView3, linearLayout3);
                }
                if (Intrinsics.areEqual(audioEffectBoxViewModel.isVoiceBoostVisible.getValue(), bool)) {
                    audioEffectBoxView.addDivider();
                    ViewUtils viewUtils4 = ViewUtils.INSTANCE;
                    AudioEffectBoxLayoutBinding audioEffectBoxLayoutBinding5 = audioEffectBoxView.viewBinding;
                    if (audioEffectBoxLayoutBinding5 == null) {
                        audioEffectBoxLayoutBinding5 = null;
                    }
                    LinearLayout linearLayout4 = audioEffectBoxLayoutBinding5.effectItemList;
                    ViewGroup rootView4 = ((BaseAudioEffectItemView) audioEffectBoxView.voiceBoostView$delegate.getValue()).getRootView();
                    viewUtils4.getClass();
                    ViewUtils.addViewIfNotAttached(rootView4, linearLayout4);
                }
                if (Intrinsics.areEqual(audioEffectBoxViewModel.isVolumeNormalizationVisible.getValue(), bool)) {
                    audioEffectBoxView.addDivider();
                    ViewUtils viewUtils5 = ViewUtils.INSTANCE;
                    AudioEffectBoxLayoutBinding audioEffectBoxLayoutBinding6 = audioEffectBoxView.viewBinding;
                    LinearLayout linearLayout5 = (audioEffectBoxLayoutBinding6 != null ? audioEffectBoxLayoutBinding6 : null).effectItemList;
                    ViewGroup rootView5 = ((BaseAudioEffectItemView) audioEffectBoxView.volumeNormalizationView$delegate.getValue()).getRootView();
                    viewUtils5.getClass();
                    ViewUtils.addViewIfNotAttached(rootView5, linearLayout5);
                }
            }
        });
        soundCraftDetailPageView.getViewModel().isActionBarVisible.observe(lifecycleOwner, new Observer() { // from class: com.android.systemui.audio.soundcraft.view.SoundCraftDetailPageView$bind$6
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                Boolean bool = (Boolean) obj;
                SoundCraftActionBarView soundCraftActionBarView = SoundCraftViewBinding.this.actionBar.root;
                Intrinsics.checkNotNull(bool);
                soundCraftActionBarView.setVisibility(bool.booleanValue() ? 0 : 8);
                int i2 = SoundCraftDetailPageView.$r8$clinit;
                soundCraftDetailPageView.updatePadding$1();
            }
        });
        soundCraftDetailPageView.getViewModel().updateVolumeBar.observe(lifecycleOwner, new Observer() { // from class: com.android.systemui.audio.soundcraft.view.SoundCraftDetailPageView$bind$7
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                VolumeBarView volumeBarView = SoundCraftViewBinding.this.volumeBar.root;
                VolumeBarViewBinding volumeBarViewBinding = volumeBarView.viewBinding;
                if (volumeBarViewBinding == null) {
                    volumeBarViewBinding = null;
                }
                if (volumeBarViewBinding.volumeBar.isTracking) {
                    return;
                }
                volumeBarView.getViewModel$2().notifyChange();
            }
        });
        final SoundCraftActionBarBinding soundCraftActionBarBinding = soundCraftViewBinding.actionBar;
        final SoundCraftActionBarView soundCraftActionBarView = soundCraftActionBarBinding.root;
        MutableLiveData mutableLiveData = ((SoundCraftActionBarViewModel) soundCraftActionBarView.viewModel$delegate.getValue()).title;
        LifecycleOwner lifecycleOwner2 = ViewTreeLifecycleOwner.get(soundCraftActionBarView);
        Intrinsics.checkNotNull(lifecycleOwner2);
        mutableLiveData.observe(lifecycleOwner2, new Observer() { // from class: com.android.systemui.audio.soundcraft.view.actionbar.SoundCraftActionBarView$bind$1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SoundCraftActionBarBinding.this.title.setText((String) obj);
            }
        });
        soundCraftActionBarBinding.backButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.audio.soundcraft.view.actionbar.SoundCraftActionBarView$bind$2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SoundCraftActionBarView soundCraftActionBarView2 = SoundCraftActionBarView.this;
                int i2 = SoundCraftActionBarView.$r8$clinit;
                SoundCraftActionBarViewModel soundCraftActionBarViewModel = (SoundCraftActionBarViewModel) soundCraftActionBarView2.viewModel$delegate.getValue();
                soundCraftActionBarViewModel.getClass();
                Log.d("SoundCraft.SoundCraftActionBarViewModel", "onBackButtonClick");
                ((SecQSDetailController) soundCraftActionBarViewModel.qsDetailControllerLazy.get()).closeTargetDetail(soundCraftActionBarViewModel.soundCraftQpDetailAdapter);
                soundCraftActionBarViewModel.soundCraftController.dismiss();
            }
        });
        ((SoundCraftActionBarViewModel) soundCraftActionBarView.viewModel$delegate.getValue()).notifyChange();
        final VolumeBarViewBinding volumeBarViewBinding = soundCraftViewBinding.volumeBar;
        final VolumeBarView volumeBarView = volumeBarViewBinding.root;
        volumeBarView.viewBinding = volumeBarViewBinding;
        volumeBarView.soundCraftVolumeMotion = new SoundCraftVolumeMotion();
        DynamicAnimation.AnonymousClass4 anonymousClass4 = DynamicAnimation.SCALE_X;
        SpringAnimation springAnimation = new SpringAnimation(volumeBarView, anonymousClass4);
        springAnimation.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() { // from class: com.android.systemui.audio.soundcraft.view.volume.SoundCraftVolumeMotion$getSeekBarTouchDownAnimation$1$1
            @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationUpdateListener
            public final void onAnimationUpdate(float f, float f2) {
                volumeBarView.setScaleY(f);
            }
        });
        SpringForce springForce = new SpringForce();
        springForce.setStiffness(300.0f);
        springForce.setDampingRatio(1.0f);
        springAnimation.mSpring = springForce;
        volumeBarView.touchDownAnimation = springAnimation;
        SoundCraftVolumeMotion soundCraftVolumeMotion = volumeBarView.soundCraftVolumeMotion;
        if (soundCraftVolumeMotion == null) {
            soundCraftVolumeMotion = null;
        }
        soundCraftVolumeMotion.getClass();
        SpringAnimation springAnimation2 = new SpringAnimation(volumeBarView, anonymousClass4);
        springAnimation2.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() { // from class: com.android.systemui.audio.soundcraft.view.volume.SoundCraftVolumeMotion$getSeekBarTouchUpAnimation$1$1
            @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationUpdateListener
            public final void onAnimationUpdate(float f, float f2) {
                volumeBarView.setScaleY(f);
            }
        });
        SpringForce springForce2 = new SpringForce();
        springForce2.setStiffness(200.0f);
        springForce2.setDampingRatio(1.0f);
        springAnimation2.mSpring = springForce2;
        volumeBarView.touchUpAnimation = springAnimation2;
        LifecycleOwner lifecycleOwner3 = ViewTreeLifecycleOwner.get(volumeBarView);
        Intrinsics.checkNotNull(lifecycleOwner3);
        VolumeBarViewModel viewModel$2 = volumeBarView.getViewModel$2();
        viewModel$2.seekBarEnabled.observe(lifecycleOwner3, new Observer() { // from class: com.android.systemui.audio.soundcraft.view.volume.VolumeBarView$bind$1$1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                int i2;
                ImageView imageView = VolumeBarViewBinding.this.volumeStatusIcon;
                int i3 = VolumeBarView.$r8$clinit;
                VolumeBarViewModel viewModel$22 = volumeBarView.getViewModel$2();
                Object value = viewModel$22.isAuraCasting.getValue();
                Intrinsics.checkNotNull(value);
                if (!((Boolean) value).booleanValue()) {
                    Object value2 = viewModel$22.zenModeEnabled.getValue();
                    Intrinsics.checkNotNull(value2);
                    if (((Number) value2).intValue() != 1) {
                        Object value3 = viewModel$22.allSoundMuteEnabled.getValue();
                        Intrinsics.checkNotNull(value3);
                        if (!((Boolean) value3).booleanValue()) {
                            i2 = 8;
                            imageView.setVisibility(i2);
                        }
                    }
                }
                i2 = 0;
                imageView.setVisibility(i2);
            }
        });
        viewModel$2.isAuraCasting.observe(lifecycleOwner3, new Observer() { // from class: com.android.systemui.audio.soundcraft.view.volume.VolumeBarView$bind$1$2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                int i2;
                ImageView imageView = VolumeBarViewBinding.this.volumeStatusIcon;
                VolumeBarView volumeBarView2 = volumeBarView;
                Context context2 = volumeBarView2.getContext();
                VolumeBarViewModel viewModel$22 = volumeBarView2.getViewModel$2();
                Object value = viewModel$22.allSoundMuteEnabled.getValue();
                Intrinsics.checkNotNull(value);
                if (!((Boolean) value).booleanValue()) {
                    Object value2 = viewModel$22.zenModeEnabled.getValue();
                    Intrinsics.checkNotNull(value2);
                    if (((Number) value2).intValue() != 1) {
                        i2 = R.drawable.ic_auracast;
                        imageView.setImageDrawable(context2.getDrawable(i2));
                    }
                }
                i2 = R.drawable.ic_qp_volume_control_dnd;
                imageView.setImageDrawable(context2.getDrawable(i2));
            }
        });
        viewModel$2.isTouching.observe(lifecycleOwner3, new Observer() { // from class: com.android.systemui.audio.soundcraft.view.volume.VolumeBarView$bind$1$3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SpringAnimation springAnimation3;
                Boolean bool = (Boolean) obj;
                Intrinsics.checkNotNull(bool);
                boolean booleanValue = bool.booleanValue();
                VolumeBarView volumeBarView2 = VolumeBarView.this;
                if (booleanValue) {
                    SoundCraftVolumeMotion soundCraftVolumeMotion2 = volumeBarView2.soundCraftVolumeMotion;
                    if (soundCraftVolumeMotion2 == null) {
                        soundCraftVolumeMotion2 = null;
                    }
                    SpringAnimation springAnimation4 = volumeBarView2.touchDownAnimation;
                    if (springAnimation4 == null) {
                        springAnimation4 = null;
                    }
                    SpringAnimation springAnimation5 = volumeBarView2.touchUpAnimation;
                    springAnimation3 = springAnimation5 != null ? springAnimation5 : null;
                    soundCraftVolumeMotion2.getClass();
                    if (springAnimation3 != null && springAnimation3.mRunning && springAnimation3.canSkipToEnd()) {
                        springAnimation3.skipToEnd();
                    }
                    springAnimation4.animateToFinalPosition(1.04f);
                    return;
                }
                SoundCraftVolumeMotion soundCraftVolumeMotion3 = volumeBarView2.soundCraftVolumeMotion;
                if (soundCraftVolumeMotion3 == null) {
                    soundCraftVolumeMotion3 = null;
                }
                SpringAnimation springAnimation6 = volumeBarView2.touchUpAnimation;
                if (springAnimation6 == null) {
                    springAnimation6 = null;
                }
                SpringAnimation springAnimation7 = volumeBarView2.touchDownAnimation;
                springAnimation3 = springAnimation7 != null ? springAnimation7 : null;
                soundCraftVolumeMotion3.getClass();
                if (springAnimation3 != null && springAnimation3.mRunning && springAnimation3.canSkipToEnd()) {
                    springAnimation3.skipToEnd();
                }
                springAnimation6.animateToFinalPosition(1.0f);
            }
        });
        final SoundCraftVolumeSeekBar soundCraftVolumeSeekBar = volumeBarViewBinding.volumeBar;
        soundCraftVolumeSeekBar.setThumb(null);
        soundCraftVolumeSeekBar.setProgressDrawable(soundCraftVolumeSeekBar.getContext().getDrawable(R.drawable.sec_soundcraft_seekbar_drawable));
        ((SoundCraftRoundedCornerSeekBarDrawable) ((LayerDrawable) soundCraftVolumeSeekBar.mProgressDrawable).findDrawableByLayerId(android.R.id.progress)).setContext(soundCraftVolumeSeekBar.getContext());
        soundCraftVolumeSeekBar.setMin(0);
        soundCraftVolumeSeekBar.setMax(150);
        VolumeBarViewModel viewModel$1 = soundCraftVolumeSeekBar.getViewModel$1();
        viewModel$1.progress.observe(lifecycleOwner3, new Observer() { // from class: com.android.systemui.audio.soundcraft.view.volume.SoundCraftVolumeSeekBar$initialize$1$1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                Integer num = (Integer) obj;
                SoundCraftVolumeSeekBar soundCraftVolumeSeekBar2 = SoundCraftVolumeSeekBar.this;
                if (soundCraftVolumeSeekBar2.isTracking) {
                    return;
                }
                if (soundCraftVolumeSeekBar2.init) {
                    Intrinsics.checkNotNull(num);
                    soundCraftVolumeSeekBar2.setProgress(num.intValue());
                    soundCraftVolumeSeekBar2.init = false;
                } else {
                    Intrinsics.checkNotNull(num);
                    soundCraftVolumeSeekBar2.springFinalPosition = num.intValue();
                    soundCraftVolumeSeekBar2.progressBarSpring.setStartValue(soundCraftVolumeSeekBar2.getProgress());
                    soundCraftVolumeSeekBar2.progressBarSpring.animateToFinalPosition(soundCraftVolumeSeekBar2.springFinalPosition);
                }
            }
        });
        viewModel$1.progressMin.observe(lifecycleOwner3, new Observer() { // from class: com.android.systemui.audio.soundcraft.view.volume.SoundCraftVolumeSeekBar$initialize$1$2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                Integer num = (Integer) obj;
                Intrinsics.checkNotNull(num);
                SoundCraftVolumeSeekBar.this.setMin(num.intValue());
            }
        });
        viewModel$1.progressMax.observe(lifecycleOwner3, new Observer() { // from class: com.android.systemui.audio.soundcraft.view.volume.SoundCraftVolumeSeekBar$initialize$1$3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                Integer num = (Integer) obj;
                Intrinsics.checkNotNull(num);
                SoundCraftVolumeSeekBar.this.setMax(num.intValue());
            }
        });
        viewModel$1.seekBarEnabled.observe(lifecycleOwner3, new Observer() { // from class: com.android.systemui.audio.soundcraft.view.volume.SoundCraftVolumeSeekBar$initialize$1$4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                Boolean bool = (Boolean) obj;
                Intrinsics.checkNotNull(bool);
                SoundCraftVolumeSeekBar.this.setEnabled(bool.booleanValue());
            }
        });
        soundCraftVolumeSeekBar.setContentDescription(soundCraftVolumeSeekBar.getContext().getString(R.string.volumepanel_media));
        final SoundCraftVolumeIcon soundCraftVolumeIcon = volumeBarViewBinding.volumeIcon;
        soundCraftVolumeIcon.stream = 3;
        LifecycleOwner lifecycleOwner4 = ViewTreeLifecycleOwner.get(soundCraftVolumeIcon);
        Intrinsics.checkNotNull(lifecycleOwner4);
        final VolumeBarViewModel viewModel = soundCraftVolumeIcon.getViewModel();
        viewModel.progress.observe(lifecycleOwner4, new Observer() { // from class: com.android.systemui.audio.soundcraft.view.volume.SoundCraftVolumeIcon$initialize$1$1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                Integer num = (Integer) obj;
                SoundCraftVolumeIcon soundCraftVolumeIcon2 = SoundCraftVolumeIcon.this;
                soundCraftVolumeIcon2.shouldUpdateIcon = soundCraftVolumeIcon2.init;
                Intrinsics.checkNotNull(num);
                soundCraftVolumeIcon2.updateLayout$2(num.intValue());
            }
        });
        viewModel.seekBarEnabled.observe(lifecycleOwner4, new Observer() { // from class: com.android.systemui.audio.soundcraft.view.volume.SoundCraftVolumeIcon$initialize$1$2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                Boolean bool = (Boolean) obj;
                Intrinsics.checkNotNull(bool);
                boolean booleanValue = bool.booleanValue();
                SoundCraftVolumeIcon soundCraftVolumeIcon2 = SoundCraftVolumeIcon.this;
                soundCraftVolumeIcon2.setEnabled(booleanValue);
                soundCraftVolumeIcon2.setAlpha(bool.booleanValue() ? 0.85f : 0.4f);
            }
        });
        viewModel.iconAnimationType.observe(lifecycleOwner4, new Observer() { // from class: com.android.systemui.audio.soundcraft.view.volume.SoundCraftVolumeIcon$initialize$1$3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                Boolean bool = (Boolean) obj;
                SoundCraftVolumeIcon soundCraftVolumeIcon2 = SoundCraftVolumeIcon.this;
                soundCraftVolumeIcon2.shouldUpdateIcon = soundCraftVolumeIcon2.init || !Boolean.valueOf(soundCraftVolumeIcon2.isAnimateType).equals(bool);
                Intrinsics.checkNotNull(bool);
                soundCraftVolumeIcon2.isAnimateType = bool.booleanValue();
                Integer num = (Integer) viewModel.progress.getValue();
                if (num != null) {
                    soundCraftVolumeIcon2.updateLayout$2(num.intValue());
                }
            }
        });
        BatteryInfoBoxViewBinding batteryInfoBoxViewBinding = soundCraftViewBinding.batteryInfoBox;
        batteryInfoBoxViewBinding.root.viewBinding = batteryInfoBoxViewBinding;
        NoiseControlBoxViewBinding noiseControlBoxViewBinding = soundCraftViewBinding.noiseControlBox;
        noiseControlBoxViewBinding.root.viewBinding = noiseControlBoxViewBinding;
        final AudioEffectBoxLayoutBinding audioEffectBoxLayoutBinding = soundCraftViewBinding.audioEffectBox;
        final AudioEffectBoxView audioEffectBoxView = audioEffectBoxLayoutBinding.box;
        audioEffectBoxView.viewBinding = audioEffectBoxLayoutBinding;
        LifecycleOwner lifecycleOwner5 = ViewTreeLifecycleOwner.get(audioEffectBoxView);
        Intrinsics.checkNotNull(lifecycleOwner5);
        audioEffectBoxView.getViewModel().isDolbyVisible.observe(lifecycleOwner5, new Observer() { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectBoxView$bind$1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                Boolean bool = (Boolean) obj;
                int i2 = AudioEffectBoxView.$r8$clinit;
                AudioEffectBoxView audioEffectBoxView2 = AudioEffectBoxView.this;
                ViewGroup rootView = ((BaseAudioEffectItemView) audioEffectBoxView2.dolbyView$delegate.getValue()).getRootView();
                Intrinsics.checkNotNull(bool);
                rootView.setVisibility(bool.booleanValue() ? 0 : 8);
                if (bool.booleanValue()) {
                    ((BaseAudioEffectItemView) audioEffectBoxView2.dolbyView$delegate.getValue()).update();
                }
            }
        });
        audioEffectBoxView.getViewModel().isDolbyEnable.observe(lifecycleOwner5, new Observer() { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectBoxView$bind$2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                Boolean bool = (Boolean) obj;
                int i2 = AudioEffectBoxView.$r8$clinit;
                BaseAudioEffectItemView baseAudioEffectItemView = (BaseAudioEffectItemView) AudioEffectBoxView.this.dolbyView$delegate.getValue();
                Intrinsics.checkNotNull(bool);
                baseAudioEffectItemView.enable(bool.booleanValue());
            }
        });
        audioEffectBoxView.getViewModel().isSpatialAudioVisible.observe(lifecycleOwner5, new Observer() { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectBoxView$bind$3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                Boolean bool = (Boolean) obj;
                int i2 = AudioEffectBoxView.$r8$clinit;
                AudioEffectBoxView audioEffectBoxView2 = AudioEffectBoxView.this;
                ViewGroup rootView = ((BaseAudioEffectItemView) audioEffectBoxView2.spatialAudioView$delegate.getValue()).getRootView();
                Intrinsics.checkNotNull(bool);
                rootView.setVisibility(bool.booleanValue() ? 0 : 8);
                if (bool.booleanValue()) {
                    ((BaseAudioEffectItemView) audioEffectBoxView2.spatialAudioView$delegate.getValue()).update();
                }
            }
        });
        audioEffectBoxView.getViewModel().isEqualizerVisible.observe(lifecycleOwner5, new Observer() { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectBoxView$bind$4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                Boolean bool = (Boolean) obj;
                SoundCraftViewModelExt soundCraftViewModelExt = SoundCraftViewModelExt.INSTANCE;
                SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
                ViewModelStoreOwner viewModelStoreOwner = SoundCraftLocalViewModelStoreOwner.current;
                if (viewModelStoreOwner == null) {
                    throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner".toString());
                }
                SoundCraftViewComponent soundCraftViewComponent = (SoundCraftViewComponent) viewModelStoreOwner;
                ((AudioEffectHeaderViewModel) SoundCraftDetailPageView$bind$2$$ExternalSyntheticOutline0.m(soundCraftViewModelExt, soundCraftViewComponent, soundCraftViewComponent, AudioEffectHeaderViewModel.class)).notifyChange();
                int i2 = AudioEffectBoxView.$r8$clinit;
                AudioEffectBoxView audioEffectBoxView2 = AudioEffectBoxView.this;
                ViewGroup rootView = ((BaseAudioEffectItemView) audioEffectBoxView2.equalizerView$delegate.getValue()).getRootView();
                Intrinsics.checkNotNull(bool);
                rootView.setVisibility(bool.booleanValue() ? 0 : 8);
                if (bool.booleanValue()) {
                    ((BaseAudioEffectItemView) audioEffectBoxView2.equalizerView$delegate.getValue()).update();
                }
            }
        });
        audioEffectBoxView.getViewModel().isVoiceBoostVisible.observe(lifecycleOwner5, new Observer() { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectBoxView$bind$5
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                Boolean bool = (Boolean) obj;
                int i2 = AudioEffectBoxView.$r8$clinit;
                AudioEffectBoxView audioEffectBoxView2 = AudioEffectBoxView.this;
                ViewGroup rootView = ((BaseAudioEffectItemView) audioEffectBoxView2.voiceBoostView$delegate.getValue()).getRootView();
                Intrinsics.checkNotNull(bool);
                rootView.setVisibility(bool.booleanValue() ? 0 : 8);
                if (bool.booleanValue()) {
                    ((BaseAudioEffectItemView) audioEffectBoxView2.voiceBoostView$delegate.getValue()).update();
                }
            }
        });
        audioEffectBoxView.getViewModel().isVoiceBoostEnable.observe(lifecycleOwner5, new Observer() { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectBoxView$bind$6
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                Boolean bool = (Boolean) obj;
                int i2 = AudioEffectBoxView.$r8$clinit;
                BaseAudioEffectItemView baseAudioEffectItemView = (BaseAudioEffectItemView) AudioEffectBoxView.this.voiceBoostView$delegate.getValue();
                Intrinsics.checkNotNull(bool);
                baseAudioEffectItemView.enable(bool.booleanValue());
            }
        });
        audioEffectBoxView.getViewModel().isVolumeNormalizationVisible.observe(lifecycleOwner5, new Observer() { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectBoxView$bind$7
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                Boolean bool = (Boolean) obj;
                int i2 = AudioEffectBoxView.$r8$clinit;
                AudioEffectBoxView audioEffectBoxView2 = AudioEffectBoxView.this;
                ViewGroup rootView = ((BaseAudioEffectItemView) audioEffectBoxView2.volumeNormalizationView$delegate.getValue()).getRootView();
                Intrinsics.checkNotNull(bool);
                rootView.setVisibility(bool.booleanValue() ? 0 : 8);
                if (bool.booleanValue()) {
                    ((BaseAudioEffectItemView) audioEffectBoxView2.volumeNormalizationView$delegate.getValue()).update();
                }
            }
        });
        audioEffectBoxView.getViewModel().isFallbackTextVisible.observe(lifecycleOwner5, new Observer() { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectBoxView$bind$8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                Boolean bool = (Boolean) obj;
                AudioEffectBoxLayoutBinding audioEffectBoxLayoutBinding2 = AudioEffectBoxLayoutBinding.this;
                TextView textView = audioEffectBoxLayoutBinding2.fallbackText;
                Intrinsics.checkNotNull(bool);
                textView.setVisibility(bool.booleanValue() ? 0 : 8);
                audioEffectBoxLayoutBinding2.effectItemList.setVisibility(bool.booleanValue() ^ true ? 0 : 8);
            }
        });
        audioEffectBoxView.getViewModel().fallbackMessage.observe(lifecycleOwner5, new Observer() { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectBoxView$bind$9
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                String str = (String) obj;
                int i2 = AudioEffectBoxView.$r8$clinit;
                if (Intrinsics.areEqual(AudioEffectBoxView.this.getViewModel().isFallbackTextVisible.getValue(), Boolean.TRUE)) {
                    audioEffectBoxLayoutBinding.fallbackText.setText(str);
                }
            }
        });
        audioEffectBoxView.getViewModel().isShowBoxBg.observe(lifecycleOwner5, new Observer() { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectBoxView$bind$10
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                Boolean bool = (Boolean) obj;
                AudioEffectBoxLayoutBinding audioEffectBoxLayoutBinding2 = AudioEffectBoxLayoutBinding.this;
                AudioEffectBoxView audioEffectBoxView2 = audioEffectBoxLayoutBinding2.box;
                Intrinsics.checkNotNull(bool);
                boolean booleanValue = bool.booleanValue();
                AudioEffectBoxView audioEffectBoxView3 = audioEffectBoxView;
                audioEffectBoxView2.setBackground(booleanValue ? audioEffectBoxView3.getContext().getDrawable(R.drawable.soundcraft_buds_effect_container_background) : null);
                if (bool.booleanValue()) {
                    ColoredBGHelper coloredBGHelper = audioEffectBoxView3.getViewModel().coloredBGHelper;
                    coloredBGHelper.setBackGroundDrawable(audioEffectBoxLayoutBinding2.box, coloredBGHelper.getBGColor());
                }
            }
        });
        audioEffectBoxView.getViewModel().isDetailJumpButtonVisible.observe(lifecycleOwner5, new Observer() { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectBoxView$bind$11
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                Boolean bool = (Boolean) obj;
                TextView textView = AudioEffectBoxLayoutBinding.this.detailJumpButton;
                Intrinsics.checkNotNull(bool);
                textView.setVisibility(bool.booleanValue() ? 0 : 8);
            }
        });
        TextView textView = audioEffectBoxLayoutBinding.detailJumpButton;
        FontSizeUtils.updateFontSize(textView, R.dimen.sec_qs_detail_button_text_size);
        textView.getTypeface().isLikeDefault = true;
        textView.setBackground(textView.getContext().getDrawable(R.drawable.sec_qs_btn_borderless_rect));
        textView.semSetButtonShapeEnabled(true);
        audioEffectBoxLayoutBinding.detailJumpButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectBoxView$bind$13
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Object failure;
                AudioEffectBoxView audioEffectBoxView2 = AudioEffectBoxView.this;
                int i2 = AudioEffectBoxView.$r8$clinit;
                AudioEffectBoxViewModel viewModel2 = audioEffectBoxView2.getViewModel();
                viewModel2.getClass();
                try {
                    int i3 = Result.$r8$clinit;
                    ((ActivityStarter) Dependency.sDependency.getDependencyInner(ActivityStarter.class)).startActivity(viewModel2.intentFactory.createIntent(), true);
                    failure = Unit.INSTANCE;
                } catch (Throwable th) {
                    int i4 = Result.$r8$clinit;
                    failure = new Result.Failure(th);
                }
                Throwable m2527exceptionOrNullimpl = Result.m2527exceptionOrNullimpl(failure);
                if (m2527exceptionOrNullimpl != null) {
                    Log.d("SoundCraft.AudioEffectBoxViewModel", "jumpToDetailPage : onFailure, e=" + m2527exceptionOrNullimpl);
                }
            }
        });
        final AudioEffectHeaderViewBinding audioEffectHeaderViewBinding = audioEffectBoxLayoutBinding.header;
        AudioEffectHeaderView audioEffectHeaderView = audioEffectHeaderViewBinding.root;
        audioEffectHeaderView.getClass();
        LifecycleOwner lifecycleOwner6 = ViewTreeLifecycleOwner.get(audioEffectHeaderView);
        Intrinsics.checkNotNull(lifecycleOwner6);
        ((AudioEffectHeaderViewModel) audioEffectHeaderView.viewModel$delegate.getValue()).title.observe(lifecycleOwner6, new Observer() { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectHeaderView$bind$1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                AudioEffectHeaderViewBinding.this.title.setText((String) obj);
            }
        });
        ((AudioEffectHeaderViewModel) audioEffectHeaderView.viewModel$delegate.getValue()).icon.observe(lifecycleOwner6, new Observer() { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectHeaderView$bind$2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                Drawable drawable = (Drawable) obj;
                AudioEffectHeaderViewBinding audioEffectHeaderViewBinding2 = AudioEffectHeaderViewBinding.this;
                audioEffectHeaderViewBinding2.icon.setImageDrawable(drawable);
                audioEffectHeaderViewBinding2.icon.setVisibility(drawable != null ? 0 : 8);
            }
        });
        ((AudioEffectHeaderViewModel) audioEffectHeaderView.viewModel$delegate.getValue()).isVisible.observe(lifecycleOwner6, new Observer() { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectHeaderView$bind$3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                Boolean bool = (Boolean) obj;
                AudioEffectHeaderView audioEffectHeaderView2 = AudioEffectHeaderViewBinding.this.root;
                Intrinsics.checkNotNull(bool);
                audioEffectHeaderView2.setVisibility(bool.booleanValue() ? 0 : 8);
            }
        });
        ((AudioEffectHeaderViewModel) audioEffectHeaderView.viewModel$delegate.getValue()).marginTop.observe(lifecycleOwner6, new Observer() { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectHeaderView$bind$4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                Integer num = (Integer) obj;
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) AudioEffectHeaderViewBinding.this.root.getLayoutParams();
                Intrinsics.checkNotNull(num);
                marginLayoutParams.topMargin = num.intValue();
            }
        });
        ((AudioEffectHeaderViewModel) audioEffectHeaderView.viewModel$delegate.getValue()).notifyChange();
        final RoutineTestViewBinding routineTestViewBinding = soundCraftViewBinding.routineTestView;
        if (routineTestViewBinding != null) {
            final RoutineTestView routineTestView = routineTestViewBinding.root;
            MutableLiveData mutableLiveData2 = routineTestView.getViewModel().routineCount;
            LifecycleOwner lifecycleOwner7 = ViewTreeLifecycleOwner.get(routineTestView);
            Intrinsics.checkNotNull(lifecycleOwner7);
            mutableLiveData2.observe(lifecycleOwner7, new Observer() { // from class: com.android.systemui.audio.soundcraft.view.routine.RoutineTestView$bind$1
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    RoutineTestViewBinding.this.routineCountText.setText(String.valueOf((Integer) obj));
                }
            });
            routineTestViewBinding.startButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.audio.soundcraft.view.routine.RoutineTestView$bind$2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    RoutineTestView routineTestView2 = RoutineTestView.this;
                    int i2 = RoutineTestView.$r8$clinit;
                    final RoutineTestViewModel viewModel2 = routineTestView2.getViewModel();
                    Log.d("SoundCraft.RoutineTestViewModel", "onStartButtonClick : playingAppPackage=" + viewModel2.audioPlaybackManager.getPlayingAppPackage());
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.android.systemui.audio.soundcraft.viewmodel.common.routine.RoutineTestViewModel$onStartButtonClick$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            RoutineTestViewModel.this.getClass();
                        }
                    }, 3000L);
                }
            });
            routineTestViewBinding.updateButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.audio.soundcraft.view.routine.RoutineTestView$bind$3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    RoutineTestView routineTestView2 = RoutineTestView.this;
                    int i2 = RoutineTestView.$r8$clinit;
                    RoutineTestViewModel viewModel2 = routineTestView2.getViewModel();
                    String playingAppPackage = viewModel2.audioPlaybackManager.getPlayingAppPackage();
                    if (playingAppPackage == null) {
                        return;
                    }
                    Log.d("SoundCraft.RoutineTestViewModel", "onUpdateButtonClick : playingAppPackage=".concat(playingAppPackage));
                    RoutineManager routineManager = viewModel2.routineManager;
                    String routineId = routineManager.getRoutineId(playingAppPackage);
                    if (routineId != null) {
                        Log.d("SoundCraft.RoutineTestViewModel", "onUpdateButtonClick : routineId=".concat(routineId));
                        routineManager.updateRoutine(playingAppPackage, routineId, viewModel2.modelProvider.effectModel);
                    }
                }
            });
            routineTestViewBinding.stopButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.audio.soundcraft.view.routine.RoutineTestView$bind$4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    RoutineTestView routineTestView2 = RoutineTestView.this;
                    int i2 = RoutineTestView.$r8$clinit;
                    routineTestView2.getViewModel().getClass();
                    Log.d("SoundCraft.RoutineTestViewModel", "onStopButtonClick");
                }
            });
            routineTestView.getViewModel().getClass();
        }
        this.binding = soundCraftViewBinding;
        SoundCraftViewModelExt soundCraftViewModelExt = SoundCraftViewModelExt.INSTANCE;
        SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
        ViewModelStoreOwner viewModelStoreOwner = SoundCraftLocalViewModelStoreOwner.current;
        if (viewModelStoreOwner == null) {
            throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner".toString());
        }
        SoundCraftViewComponent soundCraftViewComponent = (SoundCraftViewComponent) viewModelStoreOwner;
        ((SoundCraftViewModel) SoundCraftDetailPageView$bind$2$$ExternalSyntheticOutline0.m(soundCraftViewModelExt, soundCraftViewComponent, soundCraftViewComponent, SoundCraftViewModel.class)).notifyChange();
    }

    public final void onDestroy() {
        SoundCraftDetailPageView soundCraftDetailPageView;
        ViewGroup viewGroup;
        ViewParent parent;
        Log.d("SoundCraft.SoundCraftViewComponent", "onDestroy");
        SoundCraftViewModelExt soundCraftViewModelExt = SoundCraftViewModelExt.INSTANCE;
        SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
        ViewModelStoreOwner viewModelStoreOwner = SoundCraftLocalViewModelStoreOwner.current;
        if (viewModelStoreOwner == null) {
            throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner".toString());
        }
        SoundCraftViewComponent soundCraftViewComponent = (SoundCraftViewComponent) viewModelStoreOwner;
        ((EqualizerViewModel) SoundCraftDetailPageView$bind$2$$ExternalSyntheticOutline0.m(soundCraftViewModelExt, soundCraftViewComponent, soundCraftViewComponent, EqualizerViewModel.class)).dismiss();
        ViewModelStoreOwner viewModelStoreOwner2 = SoundCraftLocalViewModelStoreOwner.current;
        if (viewModelStoreOwner2 == null) {
            throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner".toString());
        }
        SoundCraftViewComponent soundCraftViewComponent2 = (SoundCraftViewComponent) viewModelStoreOwner2;
        ((SpatialAudioViewModel) SoundCraftViewModelExt.get(soundCraftViewComponent2, SpatialAudioViewModel.class, SoundCraftViewModelExt.createDaggerViewModelFactory(soundCraftViewComponent2))).dismiss();
        ViewModelStoreOwner viewModelStoreOwner3 = SoundCraftLocalViewModelStoreOwner.current;
        if (viewModelStoreOwner3 == null) {
            throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner".toString());
        }
        SoundCraftViewComponent soundCraftViewComponent3 = (SoundCraftViewComponent) viewModelStoreOwner3;
        ((DolbyViewModel) SoundCraftViewModelExt.get(soundCraftViewComponent3, DolbyViewModel.class, SoundCraftViewModelExt.createDaggerViewModelFactory(soundCraftViewComponent3))).dismiss();
        this.viewModelStore.clear();
        this.registry.setCurrentState(Lifecycle.State.DESTROYED);
        this.registry.removeObserver(this.lifecycleObserver);
        SoundCraftViewBinding soundCraftViewBinding = this.binding;
        if (soundCraftViewBinding != null && (viewGroup = soundCraftViewBinding.root) != null && (parent = viewGroup.getParent()) != null) {
            ViewGroup viewGroup2 = (ViewGroup) parent;
            SoundCraftViewBinding soundCraftViewBinding2 = this.binding;
            viewGroup2.removeView(soundCraftViewBinding2 != null ? soundCraftViewBinding2.root : null);
        }
        SoundCraftViewBinding soundCraftViewBinding3 = this.binding;
        if (soundCraftViewBinding3 != null && (soundCraftDetailPageView = soundCraftViewBinding3.detailPageContent) != null) {
            ViewTreeLifecycleOwner.set(soundCraftDetailPageView, null);
        }
        this.binding = null;
    }

    public final void updateModel(boolean z) {
        SoundCraftDetailPageView soundCraftDetailPageView;
        Log.d("SoundCraft.SoundCraftViewComponent", "updateModel");
        SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
        SoundCraftLocalViewModelStoreOwner.current = this;
        this.modelProvider.isFromNowBar = z;
        SoundCraftViewModelExt soundCraftViewModelExt = SoundCraftViewModelExt.INSTANCE;
        ViewModelStoreOwner viewModelStoreOwner = SoundCraftLocalViewModelStoreOwner.current;
        if (viewModelStoreOwner == null) {
            throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner".toString());
        }
        SoundCraftViewComponent soundCraftViewComponent = (SoundCraftViewComponent) viewModelStoreOwner;
        ((SoundCraftViewModel) SoundCraftDetailPageView$bind$2$$ExternalSyntheticOutline0.m(soundCraftViewModelExt, soundCraftViewComponent, soundCraftViewComponent, SoundCraftViewModel.class)).updateModel();
        SoundCraftViewBinding soundCraftViewBinding = this.binding;
        if (soundCraftViewBinding == null || (soundCraftDetailPageView = soundCraftViewBinding.detailPageContent) == null) {
            return;
        }
        soundCraftDetailPageView.requestLayout();
    }
}
