package com.android.systemui.audio.soundcraft.view;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.lifecycle.ViewTreeLifecycleOwner;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.FontSizeUtils;
import com.android.systemui.R;
import com.android.systemui.audio.soundcraft.activity.SoundCraftActivity;
import com.android.systemui.audio.soundcraft.di.vm.SoundCraftViewModelFactory;
import com.android.systemui.audio.soundcraft.di.vm.SoundCraftViewModelStore;
import com.android.systemui.audio.soundcraft.di.vm.component.SoundCraftLocalViewModelStoreOwner;
import com.android.systemui.audio.soundcraft.di.vm.component.SoundCraftVMComponent;
import com.android.systemui.audio.soundcraft.di.vm.component.SoundCraftViewModelExt;
import com.android.systemui.audio.soundcraft.interfaces.connectivity.BluetoothDeviceManager;
import com.android.systemui.audio.soundcraft.interfaces.routine.manager.RoutineManager;
import com.android.systemui.audio.soundcraft.interfaces.routine.manager.RoutineManager$changeObserver$1;
import com.android.systemui.audio.soundcraft.interfaces.settings.SoundCraftSettingConstants;
import com.android.systemui.audio.soundcraft.interfaces.settings.SoundCraftSettings;
import com.android.systemui.audio.soundcraft.interfaces.soundalive.SoundAliveEffectEnum;
import com.android.systemui.audio.soundcraft.interfaces.soundalive.SoundAliveEqEnum;
import com.android.systemui.audio.soundcraft.interfaces.soundalive.SoundAliveManager;
import com.android.systemui.audio.soundcraft.interfaces.wearable.WearableManager;
import com.android.systemui.audio.soundcraft.interfaces.wearable.requester.GetInfoRequester;
import com.android.systemui.audio.soundcraft.model.EffectOutDeviceType;
import com.android.systemui.audio.soundcraft.model.ModelProvider;
import com.android.systemui.audio.soundcraft.model.buds.BatteryInfo;
import com.android.systemui.audio.soundcraft.model.common.EffectModel;
import com.android.systemui.audio.soundcraft.model.common.Equalizer;
import com.android.systemui.audio.soundcraft.model.common.VolumeModel;
import com.android.systemui.audio.soundcraft.model.phone.Dolby;
import com.android.systemui.audio.soundcraft.model.phone.DolbyEnum;
import com.android.systemui.audio.soundcraft.model.phone.PhoneEffectModel;
import com.android.systemui.audio.soundcraft.utils.SoundCraftSALogging;
import com.android.systemui.audio.soundcraft.utils.ViewUtils;
import com.android.systemui.audio.soundcraft.view.SoundCraftDetailPageView$$ExternalSyntheticOutline0;
import com.android.systemui.audio.soundcraft.view.SoundCraftViewComponent;
import com.android.systemui.audio.soundcraft.view.actionbar.SoundCraftActionBarView;
import com.android.systemui.audio.soundcraft.view.actionbar.SoundCraftActionBarView$sam$androidx_lifecycle_Observer$0;
import com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectBoxView;
import com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectBoxView$$ExternalSyntheticLambda15;
import com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectBoxView$sam$androidx_lifecycle_Observer$0;
import com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectHeaderView;
import com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectHeaderView$sam$androidx_lifecycle_Observer$0;
import com.android.systemui.audio.soundcraft.view.audioeffect.BaseAudioEffectItemView;
import com.android.systemui.audio.soundcraft.view.battery.BatteryInfoBoxView;
import com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlBoxView;
import com.android.systemui.audio.soundcraft.view.routine.RoutineTestView;
import com.android.systemui.audio.soundcraft.view.routine.RoutineTestView$sam$androidx_lifecycle_Observer$0;
import com.android.systemui.audio.soundcraft.view.volume.SoundCraftRoundedCornerSeekBarDrawable;
import com.android.systemui.audio.soundcraft.view.volume.SoundCraftVolumeIcon;
import com.android.systemui.audio.soundcraft.view.volume.SoundCraftVolumeIcon$sam$androidx_lifecycle_Observer$0;
import com.android.systemui.audio.soundcraft.view.volume.SoundCraftVolumeMotion;
import com.android.systemui.audio.soundcraft.view.volume.SoundCraftVolumeSeekBar;
import com.android.systemui.audio.soundcraft.view.volume.SoundCraftVolumeSeekBar$sam$androidx_lifecycle_Observer$0;
import com.android.systemui.audio.soundcraft.view.volume.VolumeBarView;
import com.android.systemui.audio.soundcraft.view.volume.VolumeBarView$sam$androidx_lifecycle_Observer$0;
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
import com.android.systemui.audio.soundcraft.viewmodel.SoundCraftViewModel$$ExternalSyntheticLambda0;
import com.android.systemui.audio.soundcraft.viewmodel.buds.audioeffect.SpatialAudioViewModel;
import com.android.systemui.audio.soundcraft.viewmodel.buds.battery.BatteryInfoBoxViewModel;
import com.android.systemui.audio.soundcraft.viewmodel.buds.noisecontrol.AmbientVolumeViewModel;
import com.android.systemui.audio.soundcraft.viewmodel.buds.noisecontrol.NoiseCancelingLevelViewModel;
import com.android.systemui.audio.soundcraft.viewmodel.common.actionbar.SoundCraftActionBarViewModel;
import com.android.systemui.audio.soundcraft.viewmodel.common.audioeffect.AudioEffectBoxViewModel;
import com.android.systemui.audio.soundcraft.viewmodel.common.audioeffect.AudioEffectHeaderViewModel;
import com.android.systemui.audio.soundcraft.viewmodel.common.audioeffect.EqualizerViewModel;
import com.android.systemui.audio.soundcraft.viewmodel.common.routine.RoutineTestViewModel;
import com.android.systemui.audio.soundcraft.viewmodel.common.volume.VolumeBarViewModel;
import com.android.systemui.audio.soundcraft.viewmodel.phone.audioeffect.DolbyViewModel;
import com.android.systemui.facewidget.plugin.PluginFaceWidgetManager;
import com.android.systemui.facewidget.plugin.PluginFaceWidgetManager$$ExternalSyntheticLambda4;
import com.android.systemui.media.audiovisseekbar.utils.DimensionUtilsKt;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.qs.SecQSDetailController;
import com.android.systemui.qs.bar.ColoredBGHelper;
import com.samsung.android.knox.license.KnoxEnterpriseLicenseManager;
import com.samsung.android.sdk.routines.automationservice.interfaces.AutomationService;
import com.samsung.android.sdk.routines.automationservice.internal.AutomationServiceImpl;
import com.samsung.android.sdk.routines.automationservice.internal.ContentHandlerImpl;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Result;
import kotlin.Unit;
import kotlin.collections.AbstractList;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

/* loaded from: classes.dex */
public final class SoundCraftViewComponent implements LifecycleOwner, ViewModelStoreOwner, HasDefaultViewModelProviderFactory, SoundCraftVMComponent {
    public SoundCraftViewBinding binding;
    public final SoundCraftViewModelFactory defaultViewModelProviderFactory;
    public LifecycleRegistry lifecycle;
    public final SoundCraftViewComponent$lifecycleObserver$1 lifecycleObserver;
    public final ModelProvider modelProvider;
    public LifecycleRegistry registry;
    public final SoundCraftViewModelStore viewModelStore;

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
        final int i = 4;
        final int i2 = 3;
        final int i3 = 2;
        final int i4 = 1;
        final int i5 = 0;
        Log.d("SoundCraft.SoundCraftViewComponent", "onCreate");
        Lifecycle.State state = this.registry.state;
        if (state != Lifecycle.State.DESTROYED && state != Lifecycle.State.INITIALIZED) {
            onDestroy();
        }
        LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
        this.registry = lifecycleRegistry;
        this.lifecycle = lifecycleRegistry;
        lifecycleRegistry.addObserver(this.lifecycleObserver);
        this.registry.setCurrentState(Lifecycle.State.CREATED);
        int i6 = SoundCraftViewBindingFactory.$r8$clinit;
        final SoundCraftViewBinding soundCraftViewBinding = new SoundCraftViewBinding(LayoutInflater.from(context).inflate(R.layout.soundcraft_layout, viewGroup, false));
        final SoundCraftDetailPageView soundCraftDetailPageView = soundCraftViewBinding.detailPageContent;
        ViewTreeLifecycleOwner.set(this, soundCraftDetailPageView);
        soundCraftDetailPageView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.android.systemui.audio.soundcraft.view.SoundCraftViewComponent$createView$1$1
            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewAttachedToWindow(View view) {
                Log.d("SoundCraft.SoundCraftViewComponent", "onViewAttachedToWindow");
                this.this$0.registry.setCurrentState(Lifecycle.State.RESUMED);
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewDetachedFromWindow(View view) {
                Log.d("SoundCraft.SoundCraftViewComponent", "onViewDetachedFromWindow");
            }
        });
        Log.d("SoundCraft.SoundCraftViewBinding", "bindRoots");
        soundCraftDetailPageView.viewBinding = soundCraftViewBinding;
        LifecycleOwner lifecycleOwner = ViewTreeLifecycleOwner.get(soundCraftDetailPageView);
        lifecycleOwner.getClass();
        soundCraftDetailPageView.getViewModel().isBatteryInfoBoxVisible.observe(lifecycleOwner, new SoundCraftDetailPageView$sam$androidx_lifecycle_Observer$0(new Function1() { // from class: com.android.systemui.audio.soundcraft.view.SoundCraftDetailPageView$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) throws Resources.NotFoundException {
                SoundCraftDetailPageView soundCraftDetailPageView2 = soundCraftDetailPageView;
                SoundCraftViewBinding soundCraftViewBinding2 = soundCraftViewBinding;
                Boolean bool = (Boolean) obj;
                switch (i5) {
                    case 0:
                        int i7 = SoundCraftDetailPageView.$r8$clinit;
                        soundCraftViewBinding2.batteryInfoBox.root.setVisibility(bool.booleanValue() ? 0 : 8);
                        if (!bool.booleanValue() || Intrinsics.areEqual(soundCraftDetailPageView2.getViewModel().isFromCover.getValue(), Boolean.TRUE)) {
                            soundCraftViewBinding2.volumeContainer.setBackground(null);
                            soundCraftViewBinding2.volumeContainer.setPadding(0, 0, 0, 0);
                        } else {
                            SoundCraftViewModel viewModel = soundCraftDetailPageView2.getViewModel();
                            LinearLayout linearLayout = soundCraftViewBinding2.volumeContainer;
                            ColoredBGHelper coloredBGHelper = viewModel.coloredBGHelper;
                            coloredBGHelper.setBackGroundDrawable(linearLayout, coloredBGHelper.getBGColor());
                        }
                        break;
                    case 1:
                        int i8 = SoundCraftDetailPageView.$r8$clinit;
                        NoiseControlBoxViewBinding noiseControlBoxViewBinding = soundCraftViewBinding2.noiseControlBox;
                        noiseControlBoxViewBinding.root.setVisibility(bool.booleanValue() ? 0 : 8);
                        NoiseControlBoxView noiseControlBoxView = noiseControlBoxViewBinding.root;
                        noiseControlBoxView.getNoiseControlBoxViewModel().notifyChange();
                        noiseControlBoxView.getNoiseControlEffectBoxViewModel().notifyChange();
                        noiseControlBoxView.updateLayout$1();
                        if (bool.booleanValue() && Intrinsics.areEqual(soundCraftDetailPageView2.getViewModel().isFromCover.getValue(), Boolean.FALSE)) {
                            ColoredBGHelper coloredBGHelper2 = soundCraftDetailPageView2.getViewModel().coloredBGHelper;
                            coloredBGHelper2.setBackGroundDrawable(noiseControlBoxViewBinding.root, coloredBGHelper2.getBGColor());
                        }
                        break;
                    default:
                        int i9 = SoundCraftDetailPageView.$r8$clinit;
                        soundCraftViewBinding2.actionBar.root.setVisibility(bool.booleanValue() ? 0 : 8);
                        soundCraftDetailPageView2.updatePadding$1();
                        break;
                }
                return Unit.INSTANCE;
            }
        }));
        soundCraftDetailPageView.getViewModel().updateBatteryInfoBox.observe(lifecycleOwner, new SoundCraftDetailPageView$sam$androidx_lifecycle_Observer$0(new Function1() { // from class: com.android.systemui.audio.soundcraft.view.SoundCraftDetailPageView$$ExternalSyntheticLambda3
            /* JADX WARN: Removed duplicated region for block: B:131:0x031b  */
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object mo781invoke(Object obj) throws Resources.NotFoundException {
                String strM;
                String str;
                SoundCraftViewBinding soundCraftViewBinding2 = soundCraftViewBinding;
                Boolean bool = (Boolean) obj;
                switch (i5) {
                    case 0:
                        int i7 = SoundCraftDetailPageView.$r8$clinit;
                        BatteryInfoBoxView batteryInfoBoxView = soundCraftViewBinding2.batteryInfoBox.root;
                        batteryInfoBoxView.getClass();
                        SoundCraftViewModelExt soundCraftViewModelExt = SoundCraftViewModelExt.INSTANCE;
                        SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
                        SoundCraftViewComponent soundCraftViewComponent = SoundCraftLocalViewModelStoreOwner.current;
                        if (soundCraftViewComponent == null) {
                            throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
                        }
                        ((BatteryInfoBoxViewModel) SoundCraftDetailPageView$$ExternalSyntheticOutline0.m(soundCraftViewModelExt, soundCraftViewComponent, soundCraftViewComponent, BatteryInfoBoxViewModel.class)).notifyChange();
                        Log.d("SoundCraft.BatteryInfoBoxView", "updateLayout " + ((BatteryInfoBoxViewModel) batteryInfoBoxView.viewModel$delegate.getValue()));
                        BatteryInfoBoxViewBinding batteryInfoBoxViewBinding = batteryInfoBoxView.viewBinding;
                        if (batteryInfoBoxViewBinding == null) {
                            batteryInfoBoxViewBinding = null;
                        }
                        batteryInfoBoxViewBinding.budsBatteryView.setVisibility(8);
                        BatteryInfoBoxViewBinding batteryInfoBoxViewBinding2 = batteryInfoBoxView.viewBinding;
                        if (batteryInfoBoxViewBinding2 == null) {
                            batteryInfoBoxViewBinding2 = null;
                        }
                        batteryInfoBoxViewBinding2.cradleBatteryView.setVisibility(8);
                        BatteryInfoBoxViewModel batteryInfoBoxViewModel = (BatteryInfoBoxViewModel) batteryInfoBoxView.viewModel$delegate.getValue();
                        if (Intrinsics.areEqual(batteryInfoBoxViewModel.isCoverScreen.getValue(), Boolean.TRUE)) {
                            BatteryInfoBoxViewBinding batteryInfoBoxViewBinding3 = batteryInfoBoxView.viewBinding;
                            if (batteryInfoBoxViewBinding3 == null) {
                                batteryInfoBoxViewBinding3 = null;
                            }
                            batteryInfoBoxViewBinding3.root.setPadding(0, 0, 0, 0);
                        }
                        BatteryInfo batteryInfo = new BatteryInfo("-1", null, null, 6, null);
                        String str2 = (String) batteryInfoBoxViewModel.batteryLevelLeft.getValue();
                        if (str2 != null) {
                            batteryInfo.setBatteryLeft(str2);
                        }
                        String str3 = (String) batteryInfoBoxViewModel.batteryLevelRight.getValue();
                        if (str3 != null) {
                            batteryInfo.setBatteryRight(str3);
                        }
                        if (BatteryInfoBoxViewModel.isValid(batteryInfo.getBatteryLeft()) || BatteryInfoBoxViewModel.isValid(batteryInfo.getBatteryRight())) {
                            BatteryInfoBoxViewBinding batteryInfoBoxViewBinding4 = batteryInfoBoxView.viewBinding;
                            if (batteryInfoBoxViewBinding4 == null) {
                                batteryInfoBoxViewBinding4 = null;
                            }
                            batteryInfoBoxViewBinding4.budsBatteryView.setVisibility(0);
                            boolean z = batteryInfoBoxView.getContext().getResources().getConfiguration().getLayoutDirection() == 1;
                            BatteryInfoBoxViewBinding batteryInfoBoxViewBinding5 = batteryInfoBoxView.viewBinding;
                            if (batteryInfoBoxViewBinding5 == null) {
                                batteryInfoBoxViewBinding5 = null;
                            }
                            TextView textView = batteryInfoBoxViewBinding5.budsBatteryInfoText;
                            String batteryLeft = batteryInfo.getBatteryLeft();
                            String batteryRight = batteryInfo.getBatteryRight();
                            if (!BatteryInfoBoxViewModel.isValid(batteryLeft)) {
                                strM = ContentInViewNode$Request$$ExternalSyntheticOutline0.m("R ", batteryRight, "%");
                            } else if (BatteryInfoBoxViewModel.isValid(batteryRight)) {
                                batteryRight.getClass();
                                batteryLeft.getClass();
                                if (Math.abs(Integer.parseInt(batteryRight) - Integer.parseInt(batteryLeft)) < 15) {
                                    if (z) {
                                        if (Integer.parseInt(batteryRight) <= Integer.parseInt(batteryLeft)) {
                                            batteryLeft = batteryRight;
                                        }
                                        str = "R • L ";
                                    } else {
                                        if (Integer.parseInt(batteryRight) <= Integer.parseInt(batteryLeft)) {
                                            batteryLeft = batteryRight;
                                        }
                                        str = "L • R ";
                                    }
                                    strM = ContentInViewNode$Request$$ExternalSyntheticOutline0.m(str, batteryLeft, "%");
                                } else {
                                    strM = BatteryInfoBoxViewModel.isValid(batteryRight) ? z ? MotionLayout$$ExternalSyntheticOutline0.m("R ", batteryRight, "% • L ", batteryLeft, "%") : MotionLayout$$ExternalSyntheticOutline0.m("L ", batteryLeft, "% • R ", batteryRight, "%") : ContentInViewNode$Request$$ExternalSyntheticOutline0.m("L ", batteryLeft, "%");
                                }
                            }
                            textView.setText(strM);
                        }
                        MutableLiveData mutableLiveData = batteryInfoBoxViewModel.batteryLevelCradle;
                        String str4 = (String) mutableLiveData.getValue();
                        if (str4 != null) {
                            BatteryInfoBoxViewBinding batteryInfoBoxViewBinding6 = batteryInfoBoxView.viewBinding;
                            if (batteryInfoBoxViewBinding6 == null) {
                                batteryInfoBoxViewBinding6 = null;
                            }
                            batteryInfoBoxViewBinding6.cradleBatteryView.setVisibility(BatteryInfoBoxViewModel.isValid(str4) ? 0 : 8);
                            batteryInfo.setBatteryCradle((String) mutableLiveData.getValue());
                            BatteryInfoBoxViewBinding batteryInfoBoxViewBinding7 = batteryInfoBoxView.viewBinding;
                            (batteryInfoBoxViewBinding7 != null ? batteryInfoBoxViewBinding7 : null).cradleBatteryInfoText.setText(batteryInfoBoxView.getContext().getString(R.string.battery_case) + " " + batteryInfo.getBatteryCradle() + "%");
                        }
                        return Unit.INSTANCE;
                    case 1:
                        int i8 = SoundCraftDetailPageView.$r8$clinit;
                        NoiseControlBoxView noiseControlBoxView = soundCraftViewBinding2.noiseControlBox.root;
                        noiseControlBoxView.getNoiseControlEffectBoxViewModel().notifyChange();
                        ((NoiseCancelingLevelViewModel) noiseControlBoxView.noiseCancelingLevelViewModel$delegate.getValue()).notifyChange();
                        ((AmbientVolumeViewModel) noiseControlBoxView.ambientVolumeViewModel$delegate.getValue()).notifyChange();
                        noiseControlBoxView.updateSeekBarView$1();
                        return Unit.INSTANCE;
                    case 2:
                        int i9 = SoundCraftDetailPageView.$r8$clinit;
                        AudioEffectBoxView audioEffectBoxView = soundCraftViewBinding2.audioEffectBox.box;
                        audioEffectBoxView.getClass();
                        SoundCraftViewModelExt soundCraftViewModelExt2 = SoundCraftViewModelExt.INSTANCE;
                        SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
                        SoundCraftViewComponent soundCraftViewComponent2 = SoundCraftLocalViewModelStoreOwner.current;
                        if (soundCraftViewComponent2 == null) {
                            throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
                        }
                        ((AudioEffectBoxViewModel) SoundCraftDetailPageView$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent2, soundCraftViewComponent2, AudioEffectBoxViewModel.class)).notifyChange();
                        Log.d("SoundCraft.AudioEffectBoxView", "updateLayout");
                        AudioEffectBoxLayoutBinding audioEffectBoxLayoutBinding = audioEffectBoxView.viewBinding;
                        if (audioEffectBoxLayoutBinding == null) {
                            audioEffectBoxLayoutBinding = null;
                        }
                        audioEffectBoxLayoutBinding.effectItemList.removeAllViews();
                        SoundCraftViewComponent soundCraftViewComponent3 = SoundCraftLocalViewModelStoreOwner.current;
                        if (soundCraftViewComponent3 == null) {
                            throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
                        }
                        AudioEffectBoxViewModel audioEffectBoxViewModel = (AudioEffectBoxViewModel) SoundCraftViewModelExt.get(soundCraftViewComponent3, AudioEffectBoxViewModel.class, soundCraftViewComponent3.getDefaultViewModelProviderFactory());
                        Object value = audioEffectBoxViewModel.isDolbyVisible.getValue();
                        Boolean bool2 = Boolean.TRUE;
                        if (Intrinsics.areEqual(value, bool2)) {
                            ViewUtils viewUtils = ViewUtils.INSTANCE;
                            AudioEffectBoxLayoutBinding audioEffectBoxLayoutBinding2 = audioEffectBoxView.viewBinding;
                            if (audioEffectBoxLayoutBinding2 == null) {
                                audioEffectBoxLayoutBinding2 = null;
                            }
                            LinearLayout linearLayout = audioEffectBoxLayoutBinding2.effectItemList;
                            ViewGroup rootView = ((BaseAudioEffectItemView) audioEffectBoxView.dolbyView$delegate.getValue()).getRootView();
                            viewUtils.getClass();
                            ViewUtils.addViewIfNotAttached(rootView, linearLayout);
                            audioEffectBoxView.addDivider();
                        }
                        if (Intrinsics.areEqual(audioEffectBoxViewModel.isSpatialAudioVisible.getValue(), bool2)) {
                            if (Intrinsics.areEqual(audioEffectBoxView.getViewModel().isHeadTrackingVisible.getValue(), bool2)) {
                                ViewUtils viewUtils2 = ViewUtils.INSTANCE;
                                AudioEffectBoxLayoutBinding audioEffectBoxLayoutBinding3 = audioEffectBoxView.viewBinding;
                                if (audioEffectBoxLayoutBinding3 == null) {
                                    audioEffectBoxLayoutBinding3 = null;
                                }
                                LinearLayout linearLayout2 = audioEffectBoxLayoutBinding3.effectItemList;
                                ViewGroup rootView2 = ((BaseAudioEffectItemView) audioEffectBoxView.spatialAudioView$delegate.getValue()).getRootView();
                                viewUtils2.getClass();
                                ViewUtils.addViewIfNotAttached(rootView2, linearLayout2);
                            } else {
                                int dimensionPixelSize = audioEffectBoxView.getResources().getDimensionPixelSize(R.dimen.soundcraft_spatial_audio_switch_padding);
                                ((BaseAudioEffectItemView) audioEffectBoxView.spatialAudioSwitchView$delegate.getValue()).getRootView().setPadding(dimensionPixelSize, dimensionPixelSize, dimensionPixelSize, audioEffectBoxView.getResources().getDimensionPixelSize(R.dimen.soundcraft_spatial_audio_switch_bottom_padding));
                                ViewUtils viewUtils3 = ViewUtils.INSTANCE;
                                AudioEffectBoxLayoutBinding audioEffectBoxLayoutBinding4 = audioEffectBoxView.viewBinding;
                                if (audioEffectBoxLayoutBinding4 == null) {
                                    audioEffectBoxLayoutBinding4 = null;
                                }
                                LinearLayout linearLayout3 = audioEffectBoxLayoutBinding4.effectItemList;
                                ViewGroup rootView3 = ((BaseAudioEffectItemView) audioEffectBoxView.spatialAudioSwitchView$delegate.getValue()).getRootView();
                                viewUtils3.getClass();
                                ViewUtils.addViewIfNotAttached(rootView3, linearLayout3);
                            }
                            audioEffectBoxView.addDivider();
                        }
                        boolean zAreEqual = Intrinsics.areEqual(audioEffectBoxViewModel.isEqualizerVisible.getValue(), bool2);
                        MutableLiveData mutableLiveData2 = audioEffectBoxViewModel.isVolumeNormalizationVisible;
                        if (zAreEqual) {
                            ViewUtils viewUtils4 = ViewUtils.INSTANCE;
                            AudioEffectBoxLayoutBinding audioEffectBoxLayoutBinding5 = audioEffectBoxView.viewBinding;
                            if (audioEffectBoxLayoutBinding5 == null) {
                                audioEffectBoxLayoutBinding5 = null;
                            }
                            LinearLayout linearLayout4 = audioEffectBoxLayoutBinding5.effectItemList;
                            ViewGroup rootView4 = ((BaseAudioEffectItemView) audioEffectBoxView.equalizerView$delegate.getValue()).getRootView();
                            viewUtils4.getClass();
                            ViewUtils.addViewIfNotAttached(rootView4, linearLayout4);
                            if (Intrinsics.areEqual(mutableLiveData2.getValue(), bool2) || Intrinsics.areEqual(mutableLiveData2.getValue(), bool2)) {
                                audioEffectBoxView.addDivider();
                            }
                        }
                        if (Intrinsics.areEqual(audioEffectBoxViewModel.isVoiceBoostVisible.getValue(), bool2)) {
                            ViewUtils viewUtils5 = ViewUtils.INSTANCE;
                            AudioEffectBoxLayoutBinding audioEffectBoxLayoutBinding6 = audioEffectBoxView.viewBinding;
                            if (audioEffectBoxLayoutBinding6 == null) {
                                audioEffectBoxLayoutBinding6 = null;
                            }
                            LinearLayout linearLayout5 = audioEffectBoxLayoutBinding6.effectItemList;
                            ViewGroup rootView5 = ((BaseAudioEffectItemView) audioEffectBoxView.voiceBoostView$delegate.getValue()).getRootView();
                            viewUtils5.getClass();
                            ViewUtils.addViewIfNotAttached(rootView5, linearLayout5);
                            if (Intrinsics.areEqual(mutableLiveData2.getValue(), bool2)) {
                                audioEffectBoxView.addDivider();
                            }
                        }
                        if (Intrinsics.areEqual(mutableLiveData2.getValue(), bool2)) {
                            ViewUtils viewUtils6 = ViewUtils.INSTANCE;
                            AudioEffectBoxLayoutBinding audioEffectBoxLayoutBinding7 = audioEffectBoxView.viewBinding;
                            LinearLayout linearLayout6 = (audioEffectBoxLayoutBinding7 != null ? audioEffectBoxLayoutBinding7 : null).effectItemList;
                            ViewGroup rootView6 = ((BaseAudioEffectItemView) audioEffectBoxView.volumeNormalizationView$delegate.getValue()).getRootView();
                            viewUtils6.getClass();
                            ViewUtils.addViewIfNotAttached(rootView6, linearLayout6);
                        }
                        return Unit.INSTANCE;
                    case 3:
                        int i10 = SoundCraftDetailPageView.$r8$clinit;
                        soundCraftViewBinding2.volumeBar.root.setVisibility(bool.booleanValue() ? 0 : 8);
                        return Unit.INSTANCE;
                    default:
                        int i11 = SoundCraftDetailPageView.$r8$clinit;
                        VolumeBarView volumeBarView = soundCraftViewBinding2.volumeBar.root;
                        VolumeBarViewBinding volumeBarViewBinding = volumeBarView.viewBinding;
                        if (!(volumeBarViewBinding != null ? volumeBarViewBinding : null).volumeBar.isTracking) {
                            ((VolumeBarViewModel) volumeBarView.viewModel$delegate.getValue()).notifyChange();
                        }
                        return Unit.INSTANCE;
                }
            }
        }));
        soundCraftDetailPageView.getViewModel().isNoiseControlBoxVisible.observe(lifecycleOwner, new SoundCraftDetailPageView$sam$androidx_lifecycle_Observer$0(new Function1() { // from class: com.android.systemui.audio.soundcraft.view.SoundCraftDetailPageView$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) throws Resources.NotFoundException {
                SoundCraftDetailPageView soundCraftDetailPageView2 = soundCraftDetailPageView;
                SoundCraftViewBinding soundCraftViewBinding2 = soundCraftViewBinding;
                Boolean bool = (Boolean) obj;
                switch (i4) {
                    case 0:
                        int i7 = SoundCraftDetailPageView.$r8$clinit;
                        soundCraftViewBinding2.batteryInfoBox.root.setVisibility(bool.booleanValue() ? 0 : 8);
                        if (!bool.booleanValue() || Intrinsics.areEqual(soundCraftDetailPageView2.getViewModel().isFromCover.getValue(), Boolean.TRUE)) {
                            soundCraftViewBinding2.volumeContainer.setBackground(null);
                            soundCraftViewBinding2.volumeContainer.setPadding(0, 0, 0, 0);
                        } else {
                            SoundCraftViewModel viewModel = soundCraftDetailPageView2.getViewModel();
                            LinearLayout linearLayout = soundCraftViewBinding2.volumeContainer;
                            ColoredBGHelper coloredBGHelper = viewModel.coloredBGHelper;
                            coloredBGHelper.setBackGroundDrawable(linearLayout, coloredBGHelper.getBGColor());
                        }
                        break;
                    case 1:
                        int i8 = SoundCraftDetailPageView.$r8$clinit;
                        NoiseControlBoxViewBinding noiseControlBoxViewBinding = soundCraftViewBinding2.noiseControlBox;
                        noiseControlBoxViewBinding.root.setVisibility(bool.booleanValue() ? 0 : 8);
                        NoiseControlBoxView noiseControlBoxView = noiseControlBoxViewBinding.root;
                        noiseControlBoxView.getNoiseControlBoxViewModel().notifyChange();
                        noiseControlBoxView.getNoiseControlEffectBoxViewModel().notifyChange();
                        noiseControlBoxView.updateLayout$1();
                        if (bool.booleanValue() && Intrinsics.areEqual(soundCraftDetailPageView2.getViewModel().isFromCover.getValue(), Boolean.FALSE)) {
                            ColoredBGHelper coloredBGHelper2 = soundCraftDetailPageView2.getViewModel().coloredBGHelper;
                            coloredBGHelper2.setBackGroundDrawable(noiseControlBoxViewBinding.root, coloredBGHelper2.getBGColor());
                        }
                        break;
                    default:
                        int i9 = SoundCraftDetailPageView.$r8$clinit;
                        soundCraftViewBinding2.actionBar.root.setVisibility(bool.booleanValue() ? 0 : 8);
                        soundCraftDetailPageView2.updatePadding$1();
                        break;
                }
                return Unit.INSTANCE;
            }
        }));
        soundCraftDetailPageView.getViewModel().updateNoiseControlBox.observe(lifecycleOwner, new SoundCraftDetailPageView$sam$androidx_lifecycle_Observer$0(new Function1() { // from class: com.android.systemui.audio.soundcraft.view.SoundCraftDetailPageView$$ExternalSyntheticLambda3
            /* JADX WARN: Removed duplicated region for block: B:131:0x031b  */
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object mo781invoke(Object obj) throws Resources.NotFoundException {
                String strM;
                String str;
                SoundCraftViewBinding soundCraftViewBinding2 = soundCraftViewBinding;
                Boolean bool = (Boolean) obj;
                switch (i4) {
                    case 0:
                        int i7 = SoundCraftDetailPageView.$r8$clinit;
                        BatteryInfoBoxView batteryInfoBoxView = soundCraftViewBinding2.batteryInfoBox.root;
                        batteryInfoBoxView.getClass();
                        SoundCraftViewModelExt soundCraftViewModelExt = SoundCraftViewModelExt.INSTANCE;
                        SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
                        SoundCraftViewComponent soundCraftViewComponent = SoundCraftLocalViewModelStoreOwner.current;
                        if (soundCraftViewComponent == null) {
                            throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
                        }
                        ((BatteryInfoBoxViewModel) SoundCraftDetailPageView$$ExternalSyntheticOutline0.m(soundCraftViewModelExt, soundCraftViewComponent, soundCraftViewComponent, BatteryInfoBoxViewModel.class)).notifyChange();
                        Log.d("SoundCraft.BatteryInfoBoxView", "updateLayout " + ((BatteryInfoBoxViewModel) batteryInfoBoxView.viewModel$delegate.getValue()));
                        BatteryInfoBoxViewBinding batteryInfoBoxViewBinding = batteryInfoBoxView.viewBinding;
                        if (batteryInfoBoxViewBinding == null) {
                            batteryInfoBoxViewBinding = null;
                        }
                        batteryInfoBoxViewBinding.budsBatteryView.setVisibility(8);
                        BatteryInfoBoxViewBinding batteryInfoBoxViewBinding2 = batteryInfoBoxView.viewBinding;
                        if (batteryInfoBoxViewBinding2 == null) {
                            batteryInfoBoxViewBinding2 = null;
                        }
                        batteryInfoBoxViewBinding2.cradleBatteryView.setVisibility(8);
                        BatteryInfoBoxViewModel batteryInfoBoxViewModel = (BatteryInfoBoxViewModel) batteryInfoBoxView.viewModel$delegate.getValue();
                        if (Intrinsics.areEqual(batteryInfoBoxViewModel.isCoverScreen.getValue(), Boolean.TRUE)) {
                            BatteryInfoBoxViewBinding batteryInfoBoxViewBinding3 = batteryInfoBoxView.viewBinding;
                            if (batteryInfoBoxViewBinding3 == null) {
                                batteryInfoBoxViewBinding3 = null;
                            }
                            batteryInfoBoxViewBinding3.root.setPadding(0, 0, 0, 0);
                        }
                        BatteryInfo batteryInfo = new BatteryInfo("-1", null, null, 6, null);
                        String str2 = (String) batteryInfoBoxViewModel.batteryLevelLeft.getValue();
                        if (str2 != null) {
                            batteryInfo.setBatteryLeft(str2);
                        }
                        String str3 = (String) batteryInfoBoxViewModel.batteryLevelRight.getValue();
                        if (str3 != null) {
                            batteryInfo.setBatteryRight(str3);
                        }
                        if (BatteryInfoBoxViewModel.isValid(batteryInfo.getBatteryLeft()) || BatteryInfoBoxViewModel.isValid(batteryInfo.getBatteryRight())) {
                            BatteryInfoBoxViewBinding batteryInfoBoxViewBinding4 = batteryInfoBoxView.viewBinding;
                            if (batteryInfoBoxViewBinding4 == null) {
                                batteryInfoBoxViewBinding4 = null;
                            }
                            batteryInfoBoxViewBinding4.budsBatteryView.setVisibility(0);
                            boolean z = batteryInfoBoxView.getContext().getResources().getConfiguration().getLayoutDirection() == 1;
                            BatteryInfoBoxViewBinding batteryInfoBoxViewBinding5 = batteryInfoBoxView.viewBinding;
                            if (batteryInfoBoxViewBinding5 == null) {
                                batteryInfoBoxViewBinding5 = null;
                            }
                            TextView textView = batteryInfoBoxViewBinding5.budsBatteryInfoText;
                            String batteryLeft = batteryInfo.getBatteryLeft();
                            String batteryRight = batteryInfo.getBatteryRight();
                            if (!BatteryInfoBoxViewModel.isValid(batteryLeft)) {
                                strM = ContentInViewNode$Request$$ExternalSyntheticOutline0.m("R ", batteryRight, "%");
                            } else if (BatteryInfoBoxViewModel.isValid(batteryRight)) {
                                batteryRight.getClass();
                                batteryLeft.getClass();
                                if (Math.abs(Integer.parseInt(batteryRight) - Integer.parseInt(batteryLeft)) < 15) {
                                    if (z) {
                                        if (Integer.parseInt(batteryRight) <= Integer.parseInt(batteryLeft)) {
                                            batteryLeft = batteryRight;
                                        }
                                        str = "R • L ";
                                    } else {
                                        if (Integer.parseInt(batteryRight) <= Integer.parseInt(batteryLeft)) {
                                            batteryLeft = batteryRight;
                                        }
                                        str = "L • R ";
                                    }
                                    strM = ContentInViewNode$Request$$ExternalSyntheticOutline0.m(str, batteryLeft, "%");
                                } else {
                                    strM = BatteryInfoBoxViewModel.isValid(batteryRight) ? z ? MotionLayout$$ExternalSyntheticOutline0.m("R ", batteryRight, "% • L ", batteryLeft, "%") : MotionLayout$$ExternalSyntheticOutline0.m("L ", batteryLeft, "% • R ", batteryRight, "%") : ContentInViewNode$Request$$ExternalSyntheticOutline0.m("L ", batteryLeft, "%");
                                }
                            }
                            textView.setText(strM);
                        }
                        MutableLiveData mutableLiveData = batteryInfoBoxViewModel.batteryLevelCradle;
                        String str4 = (String) mutableLiveData.getValue();
                        if (str4 != null) {
                            BatteryInfoBoxViewBinding batteryInfoBoxViewBinding6 = batteryInfoBoxView.viewBinding;
                            if (batteryInfoBoxViewBinding6 == null) {
                                batteryInfoBoxViewBinding6 = null;
                            }
                            batteryInfoBoxViewBinding6.cradleBatteryView.setVisibility(BatteryInfoBoxViewModel.isValid(str4) ? 0 : 8);
                            batteryInfo.setBatteryCradle((String) mutableLiveData.getValue());
                            BatteryInfoBoxViewBinding batteryInfoBoxViewBinding7 = batteryInfoBoxView.viewBinding;
                            (batteryInfoBoxViewBinding7 != null ? batteryInfoBoxViewBinding7 : null).cradleBatteryInfoText.setText(batteryInfoBoxView.getContext().getString(R.string.battery_case) + " " + batteryInfo.getBatteryCradle() + "%");
                        }
                        return Unit.INSTANCE;
                    case 1:
                        int i8 = SoundCraftDetailPageView.$r8$clinit;
                        NoiseControlBoxView noiseControlBoxView = soundCraftViewBinding2.noiseControlBox.root;
                        noiseControlBoxView.getNoiseControlEffectBoxViewModel().notifyChange();
                        ((NoiseCancelingLevelViewModel) noiseControlBoxView.noiseCancelingLevelViewModel$delegate.getValue()).notifyChange();
                        ((AmbientVolumeViewModel) noiseControlBoxView.ambientVolumeViewModel$delegate.getValue()).notifyChange();
                        noiseControlBoxView.updateSeekBarView$1();
                        return Unit.INSTANCE;
                    case 2:
                        int i9 = SoundCraftDetailPageView.$r8$clinit;
                        AudioEffectBoxView audioEffectBoxView = soundCraftViewBinding2.audioEffectBox.box;
                        audioEffectBoxView.getClass();
                        SoundCraftViewModelExt soundCraftViewModelExt2 = SoundCraftViewModelExt.INSTANCE;
                        SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
                        SoundCraftViewComponent soundCraftViewComponent2 = SoundCraftLocalViewModelStoreOwner.current;
                        if (soundCraftViewComponent2 == null) {
                            throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
                        }
                        ((AudioEffectBoxViewModel) SoundCraftDetailPageView$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent2, soundCraftViewComponent2, AudioEffectBoxViewModel.class)).notifyChange();
                        Log.d("SoundCraft.AudioEffectBoxView", "updateLayout");
                        AudioEffectBoxLayoutBinding audioEffectBoxLayoutBinding = audioEffectBoxView.viewBinding;
                        if (audioEffectBoxLayoutBinding == null) {
                            audioEffectBoxLayoutBinding = null;
                        }
                        audioEffectBoxLayoutBinding.effectItemList.removeAllViews();
                        SoundCraftViewComponent soundCraftViewComponent3 = SoundCraftLocalViewModelStoreOwner.current;
                        if (soundCraftViewComponent3 == null) {
                            throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
                        }
                        AudioEffectBoxViewModel audioEffectBoxViewModel = (AudioEffectBoxViewModel) SoundCraftViewModelExt.get(soundCraftViewComponent3, AudioEffectBoxViewModel.class, soundCraftViewComponent3.getDefaultViewModelProviderFactory());
                        Object value = audioEffectBoxViewModel.isDolbyVisible.getValue();
                        Boolean bool2 = Boolean.TRUE;
                        if (Intrinsics.areEqual(value, bool2)) {
                            ViewUtils viewUtils = ViewUtils.INSTANCE;
                            AudioEffectBoxLayoutBinding audioEffectBoxLayoutBinding2 = audioEffectBoxView.viewBinding;
                            if (audioEffectBoxLayoutBinding2 == null) {
                                audioEffectBoxLayoutBinding2 = null;
                            }
                            LinearLayout linearLayout = audioEffectBoxLayoutBinding2.effectItemList;
                            ViewGroup rootView = ((BaseAudioEffectItemView) audioEffectBoxView.dolbyView$delegate.getValue()).getRootView();
                            viewUtils.getClass();
                            ViewUtils.addViewIfNotAttached(rootView, linearLayout);
                            audioEffectBoxView.addDivider();
                        }
                        if (Intrinsics.areEqual(audioEffectBoxViewModel.isSpatialAudioVisible.getValue(), bool2)) {
                            if (Intrinsics.areEqual(audioEffectBoxView.getViewModel().isHeadTrackingVisible.getValue(), bool2)) {
                                ViewUtils viewUtils2 = ViewUtils.INSTANCE;
                                AudioEffectBoxLayoutBinding audioEffectBoxLayoutBinding3 = audioEffectBoxView.viewBinding;
                                if (audioEffectBoxLayoutBinding3 == null) {
                                    audioEffectBoxLayoutBinding3 = null;
                                }
                                LinearLayout linearLayout2 = audioEffectBoxLayoutBinding3.effectItemList;
                                ViewGroup rootView2 = ((BaseAudioEffectItemView) audioEffectBoxView.spatialAudioView$delegate.getValue()).getRootView();
                                viewUtils2.getClass();
                                ViewUtils.addViewIfNotAttached(rootView2, linearLayout2);
                            } else {
                                int dimensionPixelSize = audioEffectBoxView.getResources().getDimensionPixelSize(R.dimen.soundcraft_spatial_audio_switch_padding);
                                ((BaseAudioEffectItemView) audioEffectBoxView.spatialAudioSwitchView$delegate.getValue()).getRootView().setPadding(dimensionPixelSize, dimensionPixelSize, dimensionPixelSize, audioEffectBoxView.getResources().getDimensionPixelSize(R.dimen.soundcraft_spatial_audio_switch_bottom_padding));
                                ViewUtils viewUtils3 = ViewUtils.INSTANCE;
                                AudioEffectBoxLayoutBinding audioEffectBoxLayoutBinding4 = audioEffectBoxView.viewBinding;
                                if (audioEffectBoxLayoutBinding4 == null) {
                                    audioEffectBoxLayoutBinding4 = null;
                                }
                                LinearLayout linearLayout3 = audioEffectBoxLayoutBinding4.effectItemList;
                                ViewGroup rootView3 = ((BaseAudioEffectItemView) audioEffectBoxView.spatialAudioSwitchView$delegate.getValue()).getRootView();
                                viewUtils3.getClass();
                                ViewUtils.addViewIfNotAttached(rootView3, linearLayout3);
                            }
                            audioEffectBoxView.addDivider();
                        }
                        boolean zAreEqual = Intrinsics.areEqual(audioEffectBoxViewModel.isEqualizerVisible.getValue(), bool2);
                        MutableLiveData mutableLiveData2 = audioEffectBoxViewModel.isVolumeNormalizationVisible;
                        if (zAreEqual) {
                            ViewUtils viewUtils4 = ViewUtils.INSTANCE;
                            AudioEffectBoxLayoutBinding audioEffectBoxLayoutBinding5 = audioEffectBoxView.viewBinding;
                            if (audioEffectBoxLayoutBinding5 == null) {
                                audioEffectBoxLayoutBinding5 = null;
                            }
                            LinearLayout linearLayout4 = audioEffectBoxLayoutBinding5.effectItemList;
                            ViewGroup rootView4 = ((BaseAudioEffectItemView) audioEffectBoxView.equalizerView$delegate.getValue()).getRootView();
                            viewUtils4.getClass();
                            ViewUtils.addViewIfNotAttached(rootView4, linearLayout4);
                            if (Intrinsics.areEqual(mutableLiveData2.getValue(), bool2) || Intrinsics.areEqual(mutableLiveData2.getValue(), bool2)) {
                                audioEffectBoxView.addDivider();
                            }
                        }
                        if (Intrinsics.areEqual(audioEffectBoxViewModel.isVoiceBoostVisible.getValue(), bool2)) {
                            ViewUtils viewUtils5 = ViewUtils.INSTANCE;
                            AudioEffectBoxLayoutBinding audioEffectBoxLayoutBinding6 = audioEffectBoxView.viewBinding;
                            if (audioEffectBoxLayoutBinding6 == null) {
                                audioEffectBoxLayoutBinding6 = null;
                            }
                            LinearLayout linearLayout5 = audioEffectBoxLayoutBinding6.effectItemList;
                            ViewGroup rootView5 = ((BaseAudioEffectItemView) audioEffectBoxView.voiceBoostView$delegate.getValue()).getRootView();
                            viewUtils5.getClass();
                            ViewUtils.addViewIfNotAttached(rootView5, linearLayout5);
                            if (Intrinsics.areEqual(mutableLiveData2.getValue(), bool2)) {
                                audioEffectBoxView.addDivider();
                            }
                        }
                        if (Intrinsics.areEqual(mutableLiveData2.getValue(), bool2)) {
                            ViewUtils viewUtils6 = ViewUtils.INSTANCE;
                            AudioEffectBoxLayoutBinding audioEffectBoxLayoutBinding7 = audioEffectBoxView.viewBinding;
                            LinearLayout linearLayout6 = (audioEffectBoxLayoutBinding7 != null ? audioEffectBoxLayoutBinding7 : null).effectItemList;
                            ViewGroup rootView6 = ((BaseAudioEffectItemView) audioEffectBoxView.volumeNormalizationView$delegate.getValue()).getRootView();
                            viewUtils6.getClass();
                            ViewUtils.addViewIfNotAttached(rootView6, linearLayout6);
                        }
                        return Unit.INSTANCE;
                    case 3:
                        int i10 = SoundCraftDetailPageView.$r8$clinit;
                        soundCraftViewBinding2.volumeBar.root.setVisibility(bool.booleanValue() ? 0 : 8);
                        return Unit.INSTANCE;
                    default:
                        int i11 = SoundCraftDetailPageView.$r8$clinit;
                        VolumeBarView volumeBarView = soundCraftViewBinding2.volumeBar.root;
                        VolumeBarViewBinding volumeBarViewBinding = volumeBarView.viewBinding;
                        if (!(volumeBarViewBinding != null ? volumeBarViewBinding : null).volumeBar.isTracking) {
                            ((VolumeBarViewModel) volumeBarView.viewModel$delegate.getValue()).notifyChange();
                        }
                        return Unit.INSTANCE;
                }
            }
        }));
        soundCraftDetailPageView.getViewModel().updateEffectBox.observe(lifecycleOwner, new SoundCraftDetailPageView$sam$androidx_lifecycle_Observer$0(new Function1() { // from class: com.android.systemui.audio.soundcraft.view.SoundCraftDetailPageView$$ExternalSyntheticLambda3
            /* JADX WARN: Removed duplicated region for block: B:131:0x031b  */
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object mo781invoke(Object obj) throws Resources.NotFoundException {
                String strM;
                String str;
                SoundCraftViewBinding soundCraftViewBinding2 = soundCraftViewBinding;
                Boolean bool = (Boolean) obj;
                switch (i3) {
                    case 0:
                        int i7 = SoundCraftDetailPageView.$r8$clinit;
                        BatteryInfoBoxView batteryInfoBoxView = soundCraftViewBinding2.batteryInfoBox.root;
                        batteryInfoBoxView.getClass();
                        SoundCraftViewModelExt soundCraftViewModelExt = SoundCraftViewModelExt.INSTANCE;
                        SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
                        SoundCraftViewComponent soundCraftViewComponent = SoundCraftLocalViewModelStoreOwner.current;
                        if (soundCraftViewComponent == null) {
                            throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
                        }
                        ((BatteryInfoBoxViewModel) SoundCraftDetailPageView$$ExternalSyntheticOutline0.m(soundCraftViewModelExt, soundCraftViewComponent, soundCraftViewComponent, BatteryInfoBoxViewModel.class)).notifyChange();
                        Log.d("SoundCraft.BatteryInfoBoxView", "updateLayout " + ((BatteryInfoBoxViewModel) batteryInfoBoxView.viewModel$delegate.getValue()));
                        BatteryInfoBoxViewBinding batteryInfoBoxViewBinding = batteryInfoBoxView.viewBinding;
                        if (batteryInfoBoxViewBinding == null) {
                            batteryInfoBoxViewBinding = null;
                        }
                        batteryInfoBoxViewBinding.budsBatteryView.setVisibility(8);
                        BatteryInfoBoxViewBinding batteryInfoBoxViewBinding2 = batteryInfoBoxView.viewBinding;
                        if (batteryInfoBoxViewBinding2 == null) {
                            batteryInfoBoxViewBinding2 = null;
                        }
                        batteryInfoBoxViewBinding2.cradleBatteryView.setVisibility(8);
                        BatteryInfoBoxViewModel batteryInfoBoxViewModel = (BatteryInfoBoxViewModel) batteryInfoBoxView.viewModel$delegate.getValue();
                        if (Intrinsics.areEqual(batteryInfoBoxViewModel.isCoverScreen.getValue(), Boolean.TRUE)) {
                            BatteryInfoBoxViewBinding batteryInfoBoxViewBinding3 = batteryInfoBoxView.viewBinding;
                            if (batteryInfoBoxViewBinding3 == null) {
                                batteryInfoBoxViewBinding3 = null;
                            }
                            batteryInfoBoxViewBinding3.root.setPadding(0, 0, 0, 0);
                        }
                        BatteryInfo batteryInfo = new BatteryInfo("-1", null, null, 6, null);
                        String str2 = (String) batteryInfoBoxViewModel.batteryLevelLeft.getValue();
                        if (str2 != null) {
                            batteryInfo.setBatteryLeft(str2);
                        }
                        String str3 = (String) batteryInfoBoxViewModel.batteryLevelRight.getValue();
                        if (str3 != null) {
                            batteryInfo.setBatteryRight(str3);
                        }
                        if (BatteryInfoBoxViewModel.isValid(batteryInfo.getBatteryLeft()) || BatteryInfoBoxViewModel.isValid(batteryInfo.getBatteryRight())) {
                            BatteryInfoBoxViewBinding batteryInfoBoxViewBinding4 = batteryInfoBoxView.viewBinding;
                            if (batteryInfoBoxViewBinding4 == null) {
                                batteryInfoBoxViewBinding4 = null;
                            }
                            batteryInfoBoxViewBinding4.budsBatteryView.setVisibility(0);
                            boolean z = batteryInfoBoxView.getContext().getResources().getConfiguration().getLayoutDirection() == 1;
                            BatteryInfoBoxViewBinding batteryInfoBoxViewBinding5 = batteryInfoBoxView.viewBinding;
                            if (batteryInfoBoxViewBinding5 == null) {
                                batteryInfoBoxViewBinding5 = null;
                            }
                            TextView textView = batteryInfoBoxViewBinding5.budsBatteryInfoText;
                            String batteryLeft = batteryInfo.getBatteryLeft();
                            String batteryRight = batteryInfo.getBatteryRight();
                            if (!BatteryInfoBoxViewModel.isValid(batteryLeft)) {
                                strM = ContentInViewNode$Request$$ExternalSyntheticOutline0.m("R ", batteryRight, "%");
                            } else if (BatteryInfoBoxViewModel.isValid(batteryRight)) {
                                batteryRight.getClass();
                                batteryLeft.getClass();
                                if (Math.abs(Integer.parseInt(batteryRight) - Integer.parseInt(batteryLeft)) < 15) {
                                    if (z) {
                                        if (Integer.parseInt(batteryRight) <= Integer.parseInt(batteryLeft)) {
                                            batteryLeft = batteryRight;
                                        }
                                        str = "R • L ";
                                    } else {
                                        if (Integer.parseInt(batteryRight) <= Integer.parseInt(batteryLeft)) {
                                            batteryLeft = batteryRight;
                                        }
                                        str = "L • R ";
                                    }
                                    strM = ContentInViewNode$Request$$ExternalSyntheticOutline0.m(str, batteryLeft, "%");
                                } else {
                                    strM = BatteryInfoBoxViewModel.isValid(batteryRight) ? z ? MotionLayout$$ExternalSyntheticOutline0.m("R ", batteryRight, "% • L ", batteryLeft, "%") : MotionLayout$$ExternalSyntheticOutline0.m("L ", batteryLeft, "% • R ", batteryRight, "%") : ContentInViewNode$Request$$ExternalSyntheticOutline0.m("L ", batteryLeft, "%");
                                }
                            }
                            textView.setText(strM);
                        }
                        MutableLiveData mutableLiveData = batteryInfoBoxViewModel.batteryLevelCradle;
                        String str4 = (String) mutableLiveData.getValue();
                        if (str4 != null) {
                            BatteryInfoBoxViewBinding batteryInfoBoxViewBinding6 = batteryInfoBoxView.viewBinding;
                            if (batteryInfoBoxViewBinding6 == null) {
                                batteryInfoBoxViewBinding6 = null;
                            }
                            batteryInfoBoxViewBinding6.cradleBatteryView.setVisibility(BatteryInfoBoxViewModel.isValid(str4) ? 0 : 8);
                            batteryInfo.setBatteryCradle((String) mutableLiveData.getValue());
                            BatteryInfoBoxViewBinding batteryInfoBoxViewBinding7 = batteryInfoBoxView.viewBinding;
                            (batteryInfoBoxViewBinding7 != null ? batteryInfoBoxViewBinding7 : null).cradleBatteryInfoText.setText(batteryInfoBoxView.getContext().getString(R.string.battery_case) + " " + batteryInfo.getBatteryCradle() + "%");
                        }
                        return Unit.INSTANCE;
                    case 1:
                        int i8 = SoundCraftDetailPageView.$r8$clinit;
                        NoiseControlBoxView noiseControlBoxView = soundCraftViewBinding2.noiseControlBox.root;
                        noiseControlBoxView.getNoiseControlEffectBoxViewModel().notifyChange();
                        ((NoiseCancelingLevelViewModel) noiseControlBoxView.noiseCancelingLevelViewModel$delegate.getValue()).notifyChange();
                        ((AmbientVolumeViewModel) noiseControlBoxView.ambientVolumeViewModel$delegate.getValue()).notifyChange();
                        noiseControlBoxView.updateSeekBarView$1();
                        return Unit.INSTANCE;
                    case 2:
                        int i9 = SoundCraftDetailPageView.$r8$clinit;
                        AudioEffectBoxView audioEffectBoxView = soundCraftViewBinding2.audioEffectBox.box;
                        audioEffectBoxView.getClass();
                        SoundCraftViewModelExt soundCraftViewModelExt2 = SoundCraftViewModelExt.INSTANCE;
                        SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
                        SoundCraftViewComponent soundCraftViewComponent2 = SoundCraftLocalViewModelStoreOwner.current;
                        if (soundCraftViewComponent2 == null) {
                            throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
                        }
                        ((AudioEffectBoxViewModel) SoundCraftDetailPageView$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent2, soundCraftViewComponent2, AudioEffectBoxViewModel.class)).notifyChange();
                        Log.d("SoundCraft.AudioEffectBoxView", "updateLayout");
                        AudioEffectBoxLayoutBinding audioEffectBoxLayoutBinding = audioEffectBoxView.viewBinding;
                        if (audioEffectBoxLayoutBinding == null) {
                            audioEffectBoxLayoutBinding = null;
                        }
                        audioEffectBoxLayoutBinding.effectItemList.removeAllViews();
                        SoundCraftViewComponent soundCraftViewComponent3 = SoundCraftLocalViewModelStoreOwner.current;
                        if (soundCraftViewComponent3 == null) {
                            throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
                        }
                        AudioEffectBoxViewModel audioEffectBoxViewModel = (AudioEffectBoxViewModel) SoundCraftViewModelExt.get(soundCraftViewComponent3, AudioEffectBoxViewModel.class, soundCraftViewComponent3.getDefaultViewModelProviderFactory());
                        Object value = audioEffectBoxViewModel.isDolbyVisible.getValue();
                        Boolean bool2 = Boolean.TRUE;
                        if (Intrinsics.areEqual(value, bool2)) {
                            ViewUtils viewUtils = ViewUtils.INSTANCE;
                            AudioEffectBoxLayoutBinding audioEffectBoxLayoutBinding2 = audioEffectBoxView.viewBinding;
                            if (audioEffectBoxLayoutBinding2 == null) {
                                audioEffectBoxLayoutBinding2 = null;
                            }
                            LinearLayout linearLayout = audioEffectBoxLayoutBinding2.effectItemList;
                            ViewGroup rootView = ((BaseAudioEffectItemView) audioEffectBoxView.dolbyView$delegate.getValue()).getRootView();
                            viewUtils.getClass();
                            ViewUtils.addViewIfNotAttached(rootView, linearLayout);
                            audioEffectBoxView.addDivider();
                        }
                        if (Intrinsics.areEqual(audioEffectBoxViewModel.isSpatialAudioVisible.getValue(), bool2)) {
                            if (Intrinsics.areEqual(audioEffectBoxView.getViewModel().isHeadTrackingVisible.getValue(), bool2)) {
                                ViewUtils viewUtils2 = ViewUtils.INSTANCE;
                                AudioEffectBoxLayoutBinding audioEffectBoxLayoutBinding3 = audioEffectBoxView.viewBinding;
                                if (audioEffectBoxLayoutBinding3 == null) {
                                    audioEffectBoxLayoutBinding3 = null;
                                }
                                LinearLayout linearLayout2 = audioEffectBoxLayoutBinding3.effectItemList;
                                ViewGroup rootView2 = ((BaseAudioEffectItemView) audioEffectBoxView.spatialAudioView$delegate.getValue()).getRootView();
                                viewUtils2.getClass();
                                ViewUtils.addViewIfNotAttached(rootView2, linearLayout2);
                            } else {
                                int dimensionPixelSize = audioEffectBoxView.getResources().getDimensionPixelSize(R.dimen.soundcraft_spatial_audio_switch_padding);
                                ((BaseAudioEffectItemView) audioEffectBoxView.spatialAudioSwitchView$delegate.getValue()).getRootView().setPadding(dimensionPixelSize, dimensionPixelSize, dimensionPixelSize, audioEffectBoxView.getResources().getDimensionPixelSize(R.dimen.soundcraft_spatial_audio_switch_bottom_padding));
                                ViewUtils viewUtils3 = ViewUtils.INSTANCE;
                                AudioEffectBoxLayoutBinding audioEffectBoxLayoutBinding4 = audioEffectBoxView.viewBinding;
                                if (audioEffectBoxLayoutBinding4 == null) {
                                    audioEffectBoxLayoutBinding4 = null;
                                }
                                LinearLayout linearLayout3 = audioEffectBoxLayoutBinding4.effectItemList;
                                ViewGroup rootView3 = ((BaseAudioEffectItemView) audioEffectBoxView.spatialAudioSwitchView$delegate.getValue()).getRootView();
                                viewUtils3.getClass();
                                ViewUtils.addViewIfNotAttached(rootView3, linearLayout3);
                            }
                            audioEffectBoxView.addDivider();
                        }
                        boolean zAreEqual = Intrinsics.areEqual(audioEffectBoxViewModel.isEqualizerVisible.getValue(), bool2);
                        MutableLiveData mutableLiveData2 = audioEffectBoxViewModel.isVolumeNormalizationVisible;
                        if (zAreEqual) {
                            ViewUtils viewUtils4 = ViewUtils.INSTANCE;
                            AudioEffectBoxLayoutBinding audioEffectBoxLayoutBinding5 = audioEffectBoxView.viewBinding;
                            if (audioEffectBoxLayoutBinding5 == null) {
                                audioEffectBoxLayoutBinding5 = null;
                            }
                            LinearLayout linearLayout4 = audioEffectBoxLayoutBinding5.effectItemList;
                            ViewGroup rootView4 = ((BaseAudioEffectItemView) audioEffectBoxView.equalizerView$delegate.getValue()).getRootView();
                            viewUtils4.getClass();
                            ViewUtils.addViewIfNotAttached(rootView4, linearLayout4);
                            if (Intrinsics.areEqual(mutableLiveData2.getValue(), bool2) || Intrinsics.areEqual(mutableLiveData2.getValue(), bool2)) {
                                audioEffectBoxView.addDivider();
                            }
                        }
                        if (Intrinsics.areEqual(audioEffectBoxViewModel.isVoiceBoostVisible.getValue(), bool2)) {
                            ViewUtils viewUtils5 = ViewUtils.INSTANCE;
                            AudioEffectBoxLayoutBinding audioEffectBoxLayoutBinding6 = audioEffectBoxView.viewBinding;
                            if (audioEffectBoxLayoutBinding6 == null) {
                                audioEffectBoxLayoutBinding6 = null;
                            }
                            LinearLayout linearLayout5 = audioEffectBoxLayoutBinding6.effectItemList;
                            ViewGroup rootView5 = ((BaseAudioEffectItemView) audioEffectBoxView.voiceBoostView$delegate.getValue()).getRootView();
                            viewUtils5.getClass();
                            ViewUtils.addViewIfNotAttached(rootView5, linearLayout5);
                            if (Intrinsics.areEqual(mutableLiveData2.getValue(), bool2)) {
                                audioEffectBoxView.addDivider();
                            }
                        }
                        if (Intrinsics.areEqual(mutableLiveData2.getValue(), bool2)) {
                            ViewUtils viewUtils6 = ViewUtils.INSTANCE;
                            AudioEffectBoxLayoutBinding audioEffectBoxLayoutBinding7 = audioEffectBoxView.viewBinding;
                            LinearLayout linearLayout6 = (audioEffectBoxLayoutBinding7 != null ? audioEffectBoxLayoutBinding7 : null).effectItemList;
                            ViewGroup rootView6 = ((BaseAudioEffectItemView) audioEffectBoxView.volumeNormalizationView$delegate.getValue()).getRootView();
                            viewUtils6.getClass();
                            ViewUtils.addViewIfNotAttached(rootView6, linearLayout6);
                        }
                        return Unit.INSTANCE;
                    case 3:
                        int i10 = SoundCraftDetailPageView.$r8$clinit;
                        soundCraftViewBinding2.volumeBar.root.setVisibility(bool.booleanValue() ? 0 : 8);
                        return Unit.INSTANCE;
                    default:
                        int i11 = SoundCraftDetailPageView.$r8$clinit;
                        VolumeBarView volumeBarView = soundCraftViewBinding2.volumeBar.root;
                        VolumeBarViewBinding volumeBarViewBinding = volumeBarView.viewBinding;
                        if (!(volumeBarViewBinding != null ? volumeBarViewBinding : null).volumeBar.isTracking) {
                            ((VolumeBarViewModel) volumeBarView.viewModel$delegate.getValue()).notifyChange();
                        }
                        return Unit.INSTANCE;
                }
            }
        }));
        soundCraftViewBinding.root.setBackgroundColor(Intrinsics.areEqual(soundCraftDetailPageView.getViewModel().isFromCover.getValue(), Boolean.TRUE) ? soundCraftDetailPageView.getContext().getColor(R.color.qs_tile_round_background_off) : 0);
        soundCraftDetailPageView.getViewModel().isActionBarVisible.observe(lifecycleOwner, new SoundCraftDetailPageView$sam$androidx_lifecycle_Observer$0(new Function1() { // from class: com.android.systemui.audio.soundcraft.view.SoundCraftDetailPageView$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) throws Resources.NotFoundException {
                SoundCraftDetailPageView soundCraftDetailPageView2 = soundCraftDetailPageView;
                SoundCraftViewBinding soundCraftViewBinding2 = soundCraftViewBinding;
                Boolean bool = (Boolean) obj;
                switch (i3) {
                    case 0:
                        int i7 = SoundCraftDetailPageView.$r8$clinit;
                        soundCraftViewBinding2.batteryInfoBox.root.setVisibility(bool.booleanValue() ? 0 : 8);
                        if (!bool.booleanValue() || Intrinsics.areEqual(soundCraftDetailPageView2.getViewModel().isFromCover.getValue(), Boolean.TRUE)) {
                            soundCraftViewBinding2.volumeContainer.setBackground(null);
                            soundCraftViewBinding2.volumeContainer.setPadding(0, 0, 0, 0);
                        } else {
                            SoundCraftViewModel viewModel = soundCraftDetailPageView2.getViewModel();
                            LinearLayout linearLayout = soundCraftViewBinding2.volumeContainer;
                            ColoredBGHelper coloredBGHelper = viewModel.coloredBGHelper;
                            coloredBGHelper.setBackGroundDrawable(linearLayout, coloredBGHelper.getBGColor());
                        }
                        break;
                    case 1:
                        int i8 = SoundCraftDetailPageView.$r8$clinit;
                        NoiseControlBoxViewBinding noiseControlBoxViewBinding = soundCraftViewBinding2.noiseControlBox;
                        noiseControlBoxViewBinding.root.setVisibility(bool.booleanValue() ? 0 : 8);
                        NoiseControlBoxView noiseControlBoxView = noiseControlBoxViewBinding.root;
                        noiseControlBoxView.getNoiseControlBoxViewModel().notifyChange();
                        noiseControlBoxView.getNoiseControlEffectBoxViewModel().notifyChange();
                        noiseControlBoxView.updateLayout$1();
                        if (bool.booleanValue() && Intrinsics.areEqual(soundCraftDetailPageView2.getViewModel().isFromCover.getValue(), Boolean.FALSE)) {
                            ColoredBGHelper coloredBGHelper2 = soundCraftDetailPageView2.getViewModel().coloredBGHelper;
                            coloredBGHelper2.setBackGroundDrawable(noiseControlBoxViewBinding.root, coloredBGHelper2.getBGColor());
                        }
                        break;
                    default:
                        int i9 = SoundCraftDetailPageView.$r8$clinit;
                        soundCraftViewBinding2.actionBar.root.setVisibility(bool.booleanValue() ? 0 : 8);
                        soundCraftDetailPageView2.updatePadding$1();
                        break;
                }
                return Unit.INSTANCE;
            }
        }));
        soundCraftDetailPageView.getViewModel().isVolumeBarVisible.observe(lifecycleOwner, new SoundCraftDetailPageView$sam$androidx_lifecycle_Observer$0(new Function1() { // from class: com.android.systemui.audio.soundcraft.view.SoundCraftDetailPageView$$ExternalSyntheticLambda3
            /* JADX WARN: Removed duplicated region for block: B:131:0x031b  */
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object mo781invoke(Object obj) throws Resources.NotFoundException {
                String strM;
                String str;
                SoundCraftViewBinding soundCraftViewBinding2 = soundCraftViewBinding;
                Boolean bool = (Boolean) obj;
                switch (i2) {
                    case 0:
                        int i7 = SoundCraftDetailPageView.$r8$clinit;
                        BatteryInfoBoxView batteryInfoBoxView = soundCraftViewBinding2.batteryInfoBox.root;
                        batteryInfoBoxView.getClass();
                        SoundCraftViewModelExt soundCraftViewModelExt = SoundCraftViewModelExt.INSTANCE;
                        SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
                        SoundCraftViewComponent soundCraftViewComponent = SoundCraftLocalViewModelStoreOwner.current;
                        if (soundCraftViewComponent == null) {
                            throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
                        }
                        ((BatteryInfoBoxViewModel) SoundCraftDetailPageView$$ExternalSyntheticOutline0.m(soundCraftViewModelExt, soundCraftViewComponent, soundCraftViewComponent, BatteryInfoBoxViewModel.class)).notifyChange();
                        Log.d("SoundCraft.BatteryInfoBoxView", "updateLayout " + ((BatteryInfoBoxViewModel) batteryInfoBoxView.viewModel$delegate.getValue()));
                        BatteryInfoBoxViewBinding batteryInfoBoxViewBinding = batteryInfoBoxView.viewBinding;
                        if (batteryInfoBoxViewBinding == null) {
                            batteryInfoBoxViewBinding = null;
                        }
                        batteryInfoBoxViewBinding.budsBatteryView.setVisibility(8);
                        BatteryInfoBoxViewBinding batteryInfoBoxViewBinding2 = batteryInfoBoxView.viewBinding;
                        if (batteryInfoBoxViewBinding2 == null) {
                            batteryInfoBoxViewBinding2 = null;
                        }
                        batteryInfoBoxViewBinding2.cradleBatteryView.setVisibility(8);
                        BatteryInfoBoxViewModel batteryInfoBoxViewModel = (BatteryInfoBoxViewModel) batteryInfoBoxView.viewModel$delegate.getValue();
                        if (Intrinsics.areEqual(batteryInfoBoxViewModel.isCoverScreen.getValue(), Boolean.TRUE)) {
                            BatteryInfoBoxViewBinding batteryInfoBoxViewBinding3 = batteryInfoBoxView.viewBinding;
                            if (batteryInfoBoxViewBinding3 == null) {
                                batteryInfoBoxViewBinding3 = null;
                            }
                            batteryInfoBoxViewBinding3.root.setPadding(0, 0, 0, 0);
                        }
                        BatteryInfo batteryInfo = new BatteryInfo("-1", null, null, 6, null);
                        String str2 = (String) batteryInfoBoxViewModel.batteryLevelLeft.getValue();
                        if (str2 != null) {
                            batteryInfo.setBatteryLeft(str2);
                        }
                        String str3 = (String) batteryInfoBoxViewModel.batteryLevelRight.getValue();
                        if (str3 != null) {
                            batteryInfo.setBatteryRight(str3);
                        }
                        if (BatteryInfoBoxViewModel.isValid(batteryInfo.getBatteryLeft()) || BatteryInfoBoxViewModel.isValid(batteryInfo.getBatteryRight())) {
                            BatteryInfoBoxViewBinding batteryInfoBoxViewBinding4 = batteryInfoBoxView.viewBinding;
                            if (batteryInfoBoxViewBinding4 == null) {
                                batteryInfoBoxViewBinding4 = null;
                            }
                            batteryInfoBoxViewBinding4.budsBatteryView.setVisibility(0);
                            boolean z = batteryInfoBoxView.getContext().getResources().getConfiguration().getLayoutDirection() == 1;
                            BatteryInfoBoxViewBinding batteryInfoBoxViewBinding5 = batteryInfoBoxView.viewBinding;
                            if (batteryInfoBoxViewBinding5 == null) {
                                batteryInfoBoxViewBinding5 = null;
                            }
                            TextView textView = batteryInfoBoxViewBinding5.budsBatteryInfoText;
                            String batteryLeft = batteryInfo.getBatteryLeft();
                            String batteryRight = batteryInfo.getBatteryRight();
                            if (!BatteryInfoBoxViewModel.isValid(batteryLeft)) {
                                strM = ContentInViewNode$Request$$ExternalSyntheticOutline0.m("R ", batteryRight, "%");
                            } else if (BatteryInfoBoxViewModel.isValid(batteryRight)) {
                                batteryRight.getClass();
                                batteryLeft.getClass();
                                if (Math.abs(Integer.parseInt(batteryRight) - Integer.parseInt(batteryLeft)) < 15) {
                                    if (z) {
                                        if (Integer.parseInt(batteryRight) <= Integer.parseInt(batteryLeft)) {
                                            batteryLeft = batteryRight;
                                        }
                                        str = "R • L ";
                                    } else {
                                        if (Integer.parseInt(batteryRight) <= Integer.parseInt(batteryLeft)) {
                                            batteryLeft = batteryRight;
                                        }
                                        str = "L • R ";
                                    }
                                    strM = ContentInViewNode$Request$$ExternalSyntheticOutline0.m(str, batteryLeft, "%");
                                } else {
                                    strM = BatteryInfoBoxViewModel.isValid(batteryRight) ? z ? MotionLayout$$ExternalSyntheticOutline0.m("R ", batteryRight, "% • L ", batteryLeft, "%") : MotionLayout$$ExternalSyntheticOutline0.m("L ", batteryLeft, "% • R ", batteryRight, "%") : ContentInViewNode$Request$$ExternalSyntheticOutline0.m("L ", batteryLeft, "%");
                                }
                            }
                            textView.setText(strM);
                        }
                        MutableLiveData mutableLiveData = batteryInfoBoxViewModel.batteryLevelCradle;
                        String str4 = (String) mutableLiveData.getValue();
                        if (str4 != null) {
                            BatteryInfoBoxViewBinding batteryInfoBoxViewBinding6 = batteryInfoBoxView.viewBinding;
                            if (batteryInfoBoxViewBinding6 == null) {
                                batteryInfoBoxViewBinding6 = null;
                            }
                            batteryInfoBoxViewBinding6.cradleBatteryView.setVisibility(BatteryInfoBoxViewModel.isValid(str4) ? 0 : 8);
                            batteryInfo.setBatteryCradle((String) mutableLiveData.getValue());
                            BatteryInfoBoxViewBinding batteryInfoBoxViewBinding7 = batteryInfoBoxView.viewBinding;
                            (batteryInfoBoxViewBinding7 != null ? batteryInfoBoxViewBinding7 : null).cradleBatteryInfoText.setText(batteryInfoBoxView.getContext().getString(R.string.battery_case) + " " + batteryInfo.getBatteryCradle() + "%");
                        }
                        return Unit.INSTANCE;
                    case 1:
                        int i8 = SoundCraftDetailPageView.$r8$clinit;
                        NoiseControlBoxView noiseControlBoxView = soundCraftViewBinding2.noiseControlBox.root;
                        noiseControlBoxView.getNoiseControlEffectBoxViewModel().notifyChange();
                        ((NoiseCancelingLevelViewModel) noiseControlBoxView.noiseCancelingLevelViewModel$delegate.getValue()).notifyChange();
                        ((AmbientVolumeViewModel) noiseControlBoxView.ambientVolumeViewModel$delegate.getValue()).notifyChange();
                        noiseControlBoxView.updateSeekBarView$1();
                        return Unit.INSTANCE;
                    case 2:
                        int i9 = SoundCraftDetailPageView.$r8$clinit;
                        AudioEffectBoxView audioEffectBoxView = soundCraftViewBinding2.audioEffectBox.box;
                        audioEffectBoxView.getClass();
                        SoundCraftViewModelExt soundCraftViewModelExt2 = SoundCraftViewModelExt.INSTANCE;
                        SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
                        SoundCraftViewComponent soundCraftViewComponent2 = SoundCraftLocalViewModelStoreOwner.current;
                        if (soundCraftViewComponent2 == null) {
                            throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
                        }
                        ((AudioEffectBoxViewModel) SoundCraftDetailPageView$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent2, soundCraftViewComponent2, AudioEffectBoxViewModel.class)).notifyChange();
                        Log.d("SoundCraft.AudioEffectBoxView", "updateLayout");
                        AudioEffectBoxLayoutBinding audioEffectBoxLayoutBinding = audioEffectBoxView.viewBinding;
                        if (audioEffectBoxLayoutBinding == null) {
                            audioEffectBoxLayoutBinding = null;
                        }
                        audioEffectBoxLayoutBinding.effectItemList.removeAllViews();
                        SoundCraftViewComponent soundCraftViewComponent3 = SoundCraftLocalViewModelStoreOwner.current;
                        if (soundCraftViewComponent3 == null) {
                            throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
                        }
                        AudioEffectBoxViewModel audioEffectBoxViewModel = (AudioEffectBoxViewModel) SoundCraftViewModelExt.get(soundCraftViewComponent3, AudioEffectBoxViewModel.class, soundCraftViewComponent3.getDefaultViewModelProviderFactory());
                        Object value = audioEffectBoxViewModel.isDolbyVisible.getValue();
                        Boolean bool2 = Boolean.TRUE;
                        if (Intrinsics.areEqual(value, bool2)) {
                            ViewUtils viewUtils = ViewUtils.INSTANCE;
                            AudioEffectBoxLayoutBinding audioEffectBoxLayoutBinding2 = audioEffectBoxView.viewBinding;
                            if (audioEffectBoxLayoutBinding2 == null) {
                                audioEffectBoxLayoutBinding2 = null;
                            }
                            LinearLayout linearLayout = audioEffectBoxLayoutBinding2.effectItemList;
                            ViewGroup rootView = ((BaseAudioEffectItemView) audioEffectBoxView.dolbyView$delegate.getValue()).getRootView();
                            viewUtils.getClass();
                            ViewUtils.addViewIfNotAttached(rootView, linearLayout);
                            audioEffectBoxView.addDivider();
                        }
                        if (Intrinsics.areEqual(audioEffectBoxViewModel.isSpatialAudioVisible.getValue(), bool2)) {
                            if (Intrinsics.areEqual(audioEffectBoxView.getViewModel().isHeadTrackingVisible.getValue(), bool2)) {
                                ViewUtils viewUtils2 = ViewUtils.INSTANCE;
                                AudioEffectBoxLayoutBinding audioEffectBoxLayoutBinding3 = audioEffectBoxView.viewBinding;
                                if (audioEffectBoxLayoutBinding3 == null) {
                                    audioEffectBoxLayoutBinding3 = null;
                                }
                                LinearLayout linearLayout2 = audioEffectBoxLayoutBinding3.effectItemList;
                                ViewGroup rootView2 = ((BaseAudioEffectItemView) audioEffectBoxView.spatialAudioView$delegate.getValue()).getRootView();
                                viewUtils2.getClass();
                                ViewUtils.addViewIfNotAttached(rootView2, linearLayout2);
                            } else {
                                int dimensionPixelSize = audioEffectBoxView.getResources().getDimensionPixelSize(R.dimen.soundcraft_spatial_audio_switch_padding);
                                ((BaseAudioEffectItemView) audioEffectBoxView.spatialAudioSwitchView$delegate.getValue()).getRootView().setPadding(dimensionPixelSize, dimensionPixelSize, dimensionPixelSize, audioEffectBoxView.getResources().getDimensionPixelSize(R.dimen.soundcraft_spatial_audio_switch_bottom_padding));
                                ViewUtils viewUtils3 = ViewUtils.INSTANCE;
                                AudioEffectBoxLayoutBinding audioEffectBoxLayoutBinding4 = audioEffectBoxView.viewBinding;
                                if (audioEffectBoxLayoutBinding4 == null) {
                                    audioEffectBoxLayoutBinding4 = null;
                                }
                                LinearLayout linearLayout3 = audioEffectBoxLayoutBinding4.effectItemList;
                                ViewGroup rootView3 = ((BaseAudioEffectItemView) audioEffectBoxView.spatialAudioSwitchView$delegate.getValue()).getRootView();
                                viewUtils3.getClass();
                                ViewUtils.addViewIfNotAttached(rootView3, linearLayout3);
                            }
                            audioEffectBoxView.addDivider();
                        }
                        boolean zAreEqual = Intrinsics.areEqual(audioEffectBoxViewModel.isEqualizerVisible.getValue(), bool2);
                        MutableLiveData mutableLiveData2 = audioEffectBoxViewModel.isVolumeNormalizationVisible;
                        if (zAreEqual) {
                            ViewUtils viewUtils4 = ViewUtils.INSTANCE;
                            AudioEffectBoxLayoutBinding audioEffectBoxLayoutBinding5 = audioEffectBoxView.viewBinding;
                            if (audioEffectBoxLayoutBinding5 == null) {
                                audioEffectBoxLayoutBinding5 = null;
                            }
                            LinearLayout linearLayout4 = audioEffectBoxLayoutBinding5.effectItemList;
                            ViewGroup rootView4 = ((BaseAudioEffectItemView) audioEffectBoxView.equalizerView$delegate.getValue()).getRootView();
                            viewUtils4.getClass();
                            ViewUtils.addViewIfNotAttached(rootView4, linearLayout4);
                            if (Intrinsics.areEqual(mutableLiveData2.getValue(), bool2) || Intrinsics.areEqual(mutableLiveData2.getValue(), bool2)) {
                                audioEffectBoxView.addDivider();
                            }
                        }
                        if (Intrinsics.areEqual(audioEffectBoxViewModel.isVoiceBoostVisible.getValue(), bool2)) {
                            ViewUtils viewUtils5 = ViewUtils.INSTANCE;
                            AudioEffectBoxLayoutBinding audioEffectBoxLayoutBinding6 = audioEffectBoxView.viewBinding;
                            if (audioEffectBoxLayoutBinding6 == null) {
                                audioEffectBoxLayoutBinding6 = null;
                            }
                            LinearLayout linearLayout5 = audioEffectBoxLayoutBinding6.effectItemList;
                            ViewGroup rootView5 = ((BaseAudioEffectItemView) audioEffectBoxView.voiceBoostView$delegate.getValue()).getRootView();
                            viewUtils5.getClass();
                            ViewUtils.addViewIfNotAttached(rootView5, linearLayout5);
                            if (Intrinsics.areEqual(mutableLiveData2.getValue(), bool2)) {
                                audioEffectBoxView.addDivider();
                            }
                        }
                        if (Intrinsics.areEqual(mutableLiveData2.getValue(), bool2)) {
                            ViewUtils viewUtils6 = ViewUtils.INSTANCE;
                            AudioEffectBoxLayoutBinding audioEffectBoxLayoutBinding7 = audioEffectBoxView.viewBinding;
                            LinearLayout linearLayout6 = (audioEffectBoxLayoutBinding7 != null ? audioEffectBoxLayoutBinding7 : null).effectItemList;
                            ViewGroup rootView6 = ((BaseAudioEffectItemView) audioEffectBoxView.volumeNormalizationView$delegate.getValue()).getRootView();
                            viewUtils6.getClass();
                            ViewUtils.addViewIfNotAttached(rootView6, linearLayout6);
                        }
                        return Unit.INSTANCE;
                    case 3:
                        int i10 = SoundCraftDetailPageView.$r8$clinit;
                        soundCraftViewBinding2.volumeBar.root.setVisibility(bool.booleanValue() ? 0 : 8);
                        return Unit.INSTANCE;
                    default:
                        int i11 = SoundCraftDetailPageView.$r8$clinit;
                        VolumeBarView volumeBarView = soundCraftViewBinding2.volumeBar.root;
                        VolumeBarViewBinding volumeBarViewBinding = volumeBarView.viewBinding;
                        if (!(volumeBarViewBinding != null ? volumeBarViewBinding : null).volumeBar.isTracking) {
                            ((VolumeBarViewModel) volumeBarView.viewModel$delegate.getValue()).notifyChange();
                        }
                        return Unit.INSTANCE;
                }
            }
        }));
        soundCraftDetailPageView.getViewModel().updateVolumeBar.observe(lifecycleOwner, new SoundCraftDetailPageView$sam$androidx_lifecycle_Observer$0(new Function1() { // from class: com.android.systemui.audio.soundcraft.view.SoundCraftDetailPageView$$ExternalSyntheticLambda3
            /* JADX WARN: Removed duplicated region for block: B:131:0x031b  */
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object mo781invoke(Object obj) throws Resources.NotFoundException {
                String strM;
                String str;
                SoundCraftViewBinding soundCraftViewBinding2 = soundCraftViewBinding;
                Boolean bool = (Boolean) obj;
                switch (i) {
                    case 0:
                        int i7 = SoundCraftDetailPageView.$r8$clinit;
                        BatteryInfoBoxView batteryInfoBoxView = soundCraftViewBinding2.batteryInfoBox.root;
                        batteryInfoBoxView.getClass();
                        SoundCraftViewModelExt soundCraftViewModelExt = SoundCraftViewModelExt.INSTANCE;
                        SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
                        SoundCraftViewComponent soundCraftViewComponent = SoundCraftLocalViewModelStoreOwner.current;
                        if (soundCraftViewComponent == null) {
                            throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
                        }
                        ((BatteryInfoBoxViewModel) SoundCraftDetailPageView$$ExternalSyntheticOutline0.m(soundCraftViewModelExt, soundCraftViewComponent, soundCraftViewComponent, BatteryInfoBoxViewModel.class)).notifyChange();
                        Log.d("SoundCraft.BatteryInfoBoxView", "updateLayout " + ((BatteryInfoBoxViewModel) batteryInfoBoxView.viewModel$delegate.getValue()));
                        BatteryInfoBoxViewBinding batteryInfoBoxViewBinding = batteryInfoBoxView.viewBinding;
                        if (batteryInfoBoxViewBinding == null) {
                            batteryInfoBoxViewBinding = null;
                        }
                        batteryInfoBoxViewBinding.budsBatteryView.setVisibility(8);
                        BatteryInfoBoxViewBinding batteryInfoBoxViewBinding2 = batteryInfoBoxView.viewBinding;
                        if (batteryInfoBoxViewBinding2 == null) {
                            batteryInfoBoxViewBinding2 = null;
                        }
                        batteryInfoBoxViewBinding2.cradleBatteryView.setVisibility(8);
                        BatteryInfoBoxViewModel batteryInfoBoxViewModel = (BatteryInfoBoxViewModel) batteryInfoBoxView.viewModel$delegate.getValue();
                        if (Intrinsics.areEqual(batteryInfoBoxViewModel.isCoverScreen.getValue(), Boolean.TRUE)) {
                            BatteryInfoBoxViewBinding batteryInfoBoxViewBinding3 = batteryInfoBoxView.viewBinding;
                            if (batteryInfoBoxViewBinding3 == null) {
                                batteryInfoBoxViewBinding3 = null;
                            }
                            batteryInfoBoxViewBinding3.root.setPadding(0, 0, 0, 0);
                        }
                        BatteryInfo batteryInfo = new BatteryInfo("-1", null, null, 6, null);
                        String str2 = (String) batteryInfoBoxViewModel.batteryLevelLeft.getValue();
                        if (str2 != null) {
                            batteryInfo.setBatteryLeft(str2);
                        }
                        String str3 = (String) batteryInfoBoxViewModel.batteryLevelRight.getValue();
                        if (str3 != null) {
                            batteryInfo.setBatteryRight(str3);
                        }
                        if (BatteryInfoBoxViewModel.isValid(batteryInfo.getBatteryLeft()) || BatteryInfoBoxViewModel.isValid(batteryInfo.getBatteryRight())) {
                            BatteryInfoBoxViewBinding batteryInfoBoxViewBinding4 = batteryInfoBoxView.viewBinding;
                            if (batteryInfoBoxViewBinding4 == null) {
                                batteryInfoBoxViewBinding4 = null;
                            }
                            batteryInfoBoxViewBinding4.budsBatteryView.setVisibility(0);
                            boolean z = batteryInfoBoxView.getContext().getResources().getConfiguration().getLayoutDirection() == 1;
                            BatteryInfoBoxViewBinding batteryInfoBoxViewBinding5 = batteryInfoBoxView.viewBinding;
                            if (batteryInfoBoxViewBinding5 == null) {
                                batteryInfoBoxViewBinding5 = null;
                            }
                            TextView textView = batteryInfoBoxViewBinding5.budsBatteryInfoText;
                            String batteryLeft = batteryInfo.getBatteryLeft();
                            String batteryRight = batteryInfo.getBatteryRight();
                            if (!BatteryInfoBoxViewModel.isValid(batteryLeft)) {
                                strM = ContentInViewNode$Request$$ExternalSyntheticOutline0.m("R ", batteryRight, "%");
                            } else if (BatteryInfoBoxViewModel.isValid(batteryRight)) {
                                batteryRight.getClass();
                                batteryLeft.getClass();
                                if (Math.abs(Integer.parseInt(batteryRight) - Integer.parseInt(batteryLeft)) < 15) {
                                    if (z) {
                                        if (Integer.parseInt(batteryRight) <= Integer.parseInt(batteryLeft)) {
                                            batteryLeft = batteryRight;
                                        }
                                        str = "R • L ";
                                    } else {
                                        if (Integer.parseInt(batteryRight) <= Integer.parseInt(batteryLeft)) {
                                            batteryLeft = batteryRight;
                                        }
                                        str = "L • R ";
                                    }
                                    strM = ContentInViewNode$Request$$ExternalSyntheticOutline0.m(str, batteryLeft, "%");
                                } else {
                                    strM = BatteryInfoBoxViewModel.isValid(batteryRight) ? z ? MotionLayout$$ExternalSyntheticOutline0.m("R ", batteryRight, "% • L ", batteryLeft, "%") : MotionLayout$$ExternalSyntheticOutline0.m("L ", batteryLeft, "% • R ", batteryRight, "%") : ContentInViewNode$Request$$ExternalSyntheticOutline0.m("L ", batteryLeft, "%");
                                }
                            }
                            textView.setText(strM);
                        }
                        MutableLiveData mutableLiveData = batteryInfoBoxViewModel.batteryLevelCradle;
                        String str4 = (String) mutableLiveData.getValue();
                        if (str4 != null) {
                            BatteryInfoBoxViewBinding batteryInfoBoxViewBinding6 = batteryInfoBoxView.viewBinding;
                            if (batteryInfoBoxViewBinding6 == null) {
                                batteryInfoBoxViewBinding6 = null;
                            }
                            batteryInfoBoxViewBinding6.cradleBatteryView.setVisibility(BatteryInfoBoxViewModel.isValid(str4) ? 0 : 8);
                            batteryInfo.setBatteryCradle((String) mutableLiveData.getValue());
                            BatteryInfoBoxViewBinding batteryInfoBoxViewBinding7 = batteryInfoBoxView.viewBinding;
                            (batteryInfoBoxViewBinding7 != null ? batteryInfoBoxViewBinding7 : null).cradleBatteryInfoText.setText(batteryInfoBoxView.getContext().getString(R.string.battery_case) + " " + batteryInfo.getBatteryCradle() + "%");
                        }
                        return Unit.INSTANCE;
                    case 1:
                        int i8 = SoundCraftDetailPageView.$r8$clinit;
                        NoiseControlBoxView noiseControlBoxView = soundCraftViewBinding2.noiseControlBox.root;
                        noiseControlBoxView.getNoiseControlEffectBoxViewModel().notifyChange();
                        ((NoiseCancelingLevelViewModel) noiseControlBoxView.noiseCancelingLevelViewModel$delegate.getValue()).notifyChange();
                        ((AmbientVolumeViewModel) noiseControlBoxView.ambientVolumeViewModel$delegate.getValue()).notifyChange();
                        noiseControlBoxView.updateSeekBarView$1();
                        return Unit.INSTANCE;
                    case 2:
                        int i9 = SoundCraftDetailPageView.$r8$clinit;
                        AudioEffectBoxView audioEffectBoxView = soundCraftViewBinding2.audioEffectBox.box;
                        audioEffectBoxView.getClass();
                        SoundCraftViewModelExt soundCraftViewModelExt2 = SoundCraftViewModelExt.INSTANCE;
                        SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
                        SoundCraftViewComponent soundCraftViewComponent2 = SoundCraftLocalViewModelStoreOwner.current;
                        if (soundCraftViewComponent2 == null) {
                            throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
                        }
                        ((AudioEffectBoxViewModel) SoundCraftDetailPageView$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent2, soundCraftViewComponent2, AudioEffectBoxViewModel.class)).notifyChange();
                        Log.d("SoundCraft.AudioEffectBoxView", "updateLayout");
                        AudioEffectBoxLayoutBinding audioEffectBoxLayoutBinding = audioEffectBoxView.viewBinding;
                        if (audioEffectBoxLayoutBinding == null) {
                            audioEffectBoxLayoutBinding = null;
                        }
                        audioEffectBoxLayoutBinding.effectItemList.removeAllViews();
                        SoundCraftViewComponent soundCraftViewComponent3 = SoundCraftLocalViewModelStoreOwner.current;
                        if (soundCraftViewComponent3 == null) {
                            throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
                        }
                        AudioEffectBoxViewModel audioEffectBoxViewModel = (AudioEffectBoxViewModel) SoundCraftViewModelExt.get(soundCraftViewComponent3, AudioEffectBoxViewModel.class, soundCraftViewComponent3.getDefaultViewModelProviderFactory());
                        Object value = audioEffectBoxViewModel.isDolbyVisible.getValue();
                        Boolean bool2 = Boolean.TRUE;
                        if (Intrinsics.areEqual(value, bool2)) {
                            ViewUtils viewUtils = ViewUtils.INSTANCE;
                            AudioEffectBoxLayoutBinding audioEffectBoxLayoutBinding2 = audioEffectBoxView.viewBinding;
                            if (audioEffectBoxLayoutBinding2 == null) {
                                audioEffectBoxLayoutBinding2 = null;
                            }
                            LinearLayout linearLayout = audioEffectBoxLayoutBinding2.effectItemList;
                            ViewGroup rootView = ((BaseAudioEffectItemView) audioEffectBoxView.dolbyView$delegate.getValue()).getRootView();
                            viewUtils.getClass();
                            ViewUtils.addViewIfNotAttached(rootView, linearLayout);
                            audioEffectBoxView.addDivider();
                        }
                        if (Intrinsics.areEqual(audioEffectBoxViewModel.isSpatialAudioVisible.getValue(), bool2)) {
                            if (Intrinsics.areEqual(audioEffectBoxView.getViewModel().isHeadTrackingVisible.getValue(), bool2)) {
                                ViewUtils viewUtils2 = ViewUtils.INSTANCE;
                                AudioEffectBoxLayoutBinding audioEffectBoxLayoutBinding3 = audioEffectBoxView.viewBinding;
                                if (audioEffectBoxLayoutBinding3 == null) {
                                    audioEffectBoxLayoutBinding3 = null;
                                }
                                LinearLayout linearLayout2 = audioEffectBoxLayoutBinding3.effectItemList;
                                ViewGroup rootView2 = ((BaseAudioEffectItemView) audioEffectBoxView.spatialAudioView$delegate.getValue()).getRootView();
                                viewUtils2.getClass();
                                ViewUtils.addViewIfNotAttached(rootView2, linearLayout2);
                            } else {
                                int dimensionPixelSize = audioEffectBoxView.getResources().getDimensionPixelSize(R.dimen.soundcraft_spatial_audio_switch_padding);
                                ((BaseAudioEffectItemView) audioEffectBoxView.spatialAudioSwitchView$delegate.getValue()).getRootView().setPadding(dimensionPixelSize, dimensionPixelSize, dimensionPixelSize, audioEffectBoxView.getResources().getDimensionPixelSize(R.dimen.soundcraft_spatial_audio_switch_bottom_padding));
                                ViewUtils viewUtils3 = ViewUtils.INSTANCE;
                                AudioEffectBoxLayoutBinding audioEffectBoxLayoutBinding4 = audioEffectBoxView.viewBinding;
                                if (audioEffectBoxLayoutBinding4 == null) {
                                    audioEffectBoxLayoutBinding4 = null;
                                }
                                LinearLayout linearLayout3 = audioEffectBoxLayoutBinding4.effectItemList;
                                ViewGroup rootView3 = ((BaseAudioEffectItemView) audioEffectBoxView.spatialAudioSwitchView$delegate.getValue()).getRootView();
                                viewUtils3.getClass();
                                ViewUtils.addViewIfNotAttached(rootView3, linearLayout3);
                            }
                            audioEffectBoxView.addDivider();
                        }
                        boolean zAreEqual = Intrinsics.areEqual(audioEffectBoxViewModel.isEqualizerVisible.getValue(), bool2);
                        MutableLiveData mutableLiveData2 = audioEffectBoxViewModel.isVolumeNormalizationVisible;
                        if (zAreEqual) {
                            ViewUtils viewUtils4 = ViewUtils.INSTANCE;
                            AudioEffectBoxLayoutBinding audioEffectBoxLayoutBinding5 = audioEffectBoxView.viewBinding;
                            if (audioEffectBoxLayoutBinding5 == null) {
                                audioEffectBoxLayoutBinding5 = null;
                            }
                            LinearLayout linearLayout4 = audioEffectBoxLayoutBinding5.effectItemList;
                            ViewGroup rootView4 = ((BaseAudioEffectItemView) audioEffectBoxView.equalizerView$delegate.getValue()).getRootView();
                            viewUtils4.getClass();
                            ViewUtils.addViewIfNotAttached(rootView4, linearLayout4);
                            if (Intrinsics.areEqual(mutableLiveData2.getValue(), bool2) || Intrinsics.areEqual(mutableLiveData2.getValue(), bool2)) {
                                audioEffectBoxView.addDivider();
                            }
                        }
                        if (Intrinsics.areEqual(audioEffectBoxViewModel.isVoiceBoostVisible.getValue(), bool2)) {
                            ViewUtils viewUtils5 = ViewUtils.INSTANCE;
                            AudioEffectBoxLayoutBinding audioEffectBoxLayoutBinding6 = audioEffectBoxView.viewBinding;
                            if (audioEffectBoxLayoutBinding6 == null) {
                                audioEffectBoxLayoutBinding6 = null;
                            }
                            LinearLayout linearLayout5 = audioEffectBoxLayoutBinding6.effectItemList;
                            ViewGroup rootView5 = ((BaseAudioEffectItemView) audioEffectBoxView.voiceBoostView$delegate.getValue()).getRootView();
                            viewUtils5.getClass();
                            ViewUtils.addViewIfNotAttached(rootView5, linearLayout5);
                            if (Intrinsics.areEqual(mutableLiveData2.getValue(), bool2)) {
                                audioEffectBoxView.addDivider();
                            }
                        }
                        if (Intrinsics.areEqual(mutableLiveData2.getValue(), bool2)) {
                            ViewUtils viewUtils6 = ViewUtils.INSTANCE;
                            AudioEffectBoxLayoutBinding audioEffectBoxLayoutBinding7 = audioEffectBoxView.viewBinding;
                            LinearLayout linearLayout6 = (audioEffectBoxLayoutBinding7 != null ? audioEffectBoxLayoutBinding7 : null).effectItemList;
                            ViewGroup rootView6 = ((BaseAudioEffectItemView) audioEffectBoxView.volumeNormalizationView$delegate.getValue()).getRootView();
                            viewUtils6.getClass();
                            ViewUtils.addViewIfNotAttached(rootView6, linearLayout6);
                        }
                        return Unit.INSTANCE;
                    case 3:
                        int i10 = SoundCraftDetailPageView.$r8$clinit;
                        soundCraftViewBinding2.volumeBar.root.setVisibility(bool.booleanValue() ? 0 : 8);
                        return Unit.INSTANCE;
                    default:
                        int i11 = SoundCraftDetailPageView.$r8$clinit;
                        VolumeBarView volumeBarView = soundCraftViewBinding2.volumeBar.root;
                        VolumeBarViewBinding volumeBarViewBinding = volumeBarView.viewBinding;
                        if (!(volumeBarViewBinding != null ? volumeBarViewBinding : null).volumeBar.isTracking) {
                            ((VolumeBarViewModel) volumeBarView.viewModel$delegate.getValue()).notifyChange();
                        }
                        return Unit.INSTANCE;
                }
            }
        }));
        final SoundCraftActionBarBinding soundCraftActionBarBinding = soundCraftViewBinding.actionBar;
        final SoundCraftActionBarView soundCraftActionBarView = soundCraftActionBarBinding.root;
        MutableLiveData mutableLiveData = ((SoundCraftActionBarViewModel) soundCraftActionBarView.viewModel$delegate.getValue()).title;
        LifecycleOwner lifecycleOwner2 = ViewTreeLifecycleOwner.get(soundCraftActionBarView);
        lifecycleOwner2.getClass();
        mutableLiveData.observe(lifecycleOwner2, new SoundCraftActionBarView$sam$androidx_lifecycle_Observer$0(new Function1() { // from class: com.android.systemui.audio.soundcraft.view.actionbar.SoundCraftActionBarView$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                SoundCraftActionBarBinding soundCraftActionBarBinding2 = soundCraftActionBarBinding;
                switch (i5) {
                    case 0:
                        int i7 = SoundCraftActionBarView.$r8$clinit;
                        soundCraftActionBarBinding2.title.setText((String) obj);
                        break;
                    default:
                        int i8 = SoundCraftActionBarView.$r8$clinit;
                        if (((Boolean) obj).booleanValue()) {
                            TextView textView = soundCraftActionBarBinding2.title;
                            textView.setTypeface(Typeface.create(Typeface.create("sec", 0), KnoxEnterpriseLicenseManager.ERROR_LICENSE_DEACTIVATED, false));
                            textView.setGravity(17);
                            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
                            layoutParams.setMarginStart(0);
                            textView.setLayoutParams(layoutParams);
                        }
                        break;
                }
                return Unit.INSTANCE;
            }
        }));
        soundCraftActionBarBinding.backButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.audio.soundcraft.view.actionbar.SoundCraftActionBarView$bind$2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) throws Resources.NotFoundException {
                SoundCraftActionBarView soundCraftActionBarView2 = soundCraftActionBarView;
                int i7 = SoundCraftActionBarView.$r8$clinit;
                SoundCraftActionBarViewModel soundCraftActionBarViewModel = (SoundCraftActionBarViewModel) soundCraftActionBarView2.viewModel$delegate.getValue();
                soundCraftActionBarViewModel.getClass();
                Log.d("SoundCraft.SoundCraftActionBarViewModel", "onBackButtonClick");
                ((SecQSDetailController) soundCraftActionBarViewModel.qsDetailControllerLazy.get()).closeTargetDetail(soundCraftActionBarViewModel.soundCraftQpDetailAdapter);
                PluginFaceWidgetManager$$ExternalSyntheticLambda4 pluginFaceWidgetManager$$ExternalSyntheticLambda4 = soundCraftActionBarViewModel.soundCraftNowBarController.listener;
                if (pluginFaceWidgetManager$$ExternalSyntheticLambda4 != null) {
                    StringBuilder sb = new StringBuilder("showBudsInfo mFaceWidgetPlugin");
                    PluginFaceWidgetManager pluginFaceWidgetManager = pluginFaceWidgetManager$$ExternalSyntheticLambda4.f$0;
                    sb.append(pluginFaceWidgetManager.mFaceWidgetPlugin);
                    Log.i("PluginFaceWidgetManager", sb.toString());
                    if (pluginFaceWidgetManager.mFaceWidgetPlugin != null) {
                        Log.i("PluginFaceWidgetManager", "destroyFullNowBar");
                        pluginFaceWidgetManager.mFaceWidgetPlugin.destroyFullNowBar();
                    }
                }
                SoundCraftActivity.AnonymousClass5 anonymousClass5 = soundCraftActionBarViewModel.soundCraftCoverController.listener;
                if (anonymousClass5 != null) {
                    int i8 = SoundCraftActivity.$r8$clinit;
                    SoundCraftActivity soundCraftActivity = SoundCraftActivity.this;
                    SoundCraftActivity.AnonymousClass3 anonymousClass3 = soundCraftActivity.actionScreenReceiver;
                    if (anonymousClass3 != null) {
                        soundCraftActivity.unregisterReceiver(anonymousClass3);
                        soundCraftActivity.actionScreenReceiver = null;
                    }
                    soundCraftActivity.finish();
                }
            }
        });
        MutableLiveData mutableLiveData2 = ((SoundCraftActionBarViewModel) soundCraftActionBarView.viewModel$delegate.getValue()).isCoverScreen;
        LifecycleOwner lifecycleOwner3 = ViewTreeLifecycleOwner.get(soundCraftActionBarView);
        lifecycleOwner3.getClass();
        mutableLiveData2.observe(lifecycleOwner3, new SoundCraftActionBarView$sam$androidx_lifecycle_Observer$0(new Function1() { // from class: com.android.systemui.audio.soundcraft.view.actionbar.SoundCraftActionBarView$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                SoundCraftActionBarBinding soundCraftActionBarBinding2 = soundCraftActionBarBinding;
                switch (i4) {
                    case 0:
                        int i7 = SoundCraftActionBarView.$r8$clinit;
                        soundCraftActionBarBinding2.title.setText((String) obj);
                        break;
                    default:
                        int i8 = SoundCraftActionBarView.$r8$clinit;
                        if (((Boolean) obj).booleanValue()) {
                            TextView textView = soundCraftActionBarBinding2.title;
                            textView.setTypeface(Typeface.create(Typeface.create("sec", 0), KnoxEnterpriseLicenseManager.ERROR_LICENSE_DEACTIVATED, false));
                            textView.setGravity(17);
                            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
                            layoutParams.setMarginStart(0);
                            textView.setLayoutParams(layoutParams);
                        }
                        break;
                }
                return Unit.INSTANCE;
            }
        }));
        ((SoundCraftActionBarViewModel) soundCraftActionBarView.viewModel$delegate.getValue()).notifyChange();
        VolumeBarViewBinding volumeBarViewBinding = soundCraftViewBinding.volumeBar;
        final VolumeBarView volumeBarView = volumeBarViewBinding.root;
        volumeBarView.viewBinding = volumeBarViewBinding;
        volumeBarView.soundCraftVolumeMotion = new SoundCraftVolumeMotion();
        DynamicAnimation.AnonymousClass4 anonymousClass4 = DynamicAnimation.SCALE_X;
        SpringAnimation springAnimation = new SpringAnimation(volumeBarView, anonymousClass4);
        springAnimation.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() { // from class: com.android.systemui.audio.soundcraft.view.volume.SoundCraftVolumeMotion$getSeekBarTouchDownAnimation$1$1
            @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationUpdateListener
            public final void onAnimationUpdate(DynamicAnimation dynamicAnimation, float f, float f2) {
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
            public final void onAnimationUpdate(DynamicAnimation dynamicAnimation, float f, float f2) {
                volumeBarView.setScaleY(f);
            }
        });
        SpringForce springForce2 = new SpringForce();
        springForce2.setStiffness(200.0f);
        springForce2.setDampingRatio(1.0f);
        springAnimation2.mSpring = springForce2;
        volumeBarView.touchUpAnimation = springAnimation2;
        LifecycleOwner lifecycleOwner4 = ViewTreeLifecycleOwner.get(volumeBarView);
        lifecycleOwner4.getClass();
        ((VolumeBarViewModel) volumeBarView.viewModel$delegate.getValue()).isTouching.observe(lifecycleOwner4, new VolumeBarView$sam$androidx_lifecycle_Observer$0(new Function1() { // from class: com.android.systemui.audio.soundcraft.view.volume.VolumeBarView$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                SpringAnimation springAnimation3;
                int i7 = VolumeBarView.$r8$clinit;
                boolean zBooleanValue = ((Boolean) obj).booleanValue();
                VolumeBarView volumeBarView2 = volumeBarView;
                if (zBooleanValue) {
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
                } else {
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
                return Unit.INSTANCE;
            }
        }));
        final SoundCraftVolumeSeekBar soundCraftVolumeSeekBar = volumeBarViewBinding.volumeBar;
        soundCraftVolumeSeekBar.setThumb(null);
        soundCraftVolumeSeekBar.setProgressDrawable(soundCraftVolumeSeekBar.getContext().getDrawable(R.drawable.sec_soundcraft_seekbar_drawable));
        ((SoundCraftRoundedCornerSeekBarDrawable) ((LayerDrawable) soundCraftVolumeSeekBar.mProgressDrawable).findDrawableByLayerId(android.R.id.progress)).setContext(soundCraftVolumeSeekBar.getContext());
        soundCraftVolumeSeekBar.setMin(0);
        soundCraftVolumeSeekBar.setMax(150);
        VolumeBarViewModel viewModel$1 = soundCraftVolumeSeekBar.getViewModel$1();
        viewModel$1.progress.observe(lifecycleOwner4, new SoundCraftVolumeSeekBar$sam$androidx_lifecycle_Observer$0(new Function1() { // from class: com.android.systemui.audio.soundcraft.view.volume.SoundCraftVolumeSeekBar$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                SoundCraftVolumeSeekBar soundCraftVolumeSeekBar2 = soundCraftVolumeSeekBar;
                switch (i5) {
                    case 0:
                        Integer num = (Integer) obj;
                        if (!soundCraftVolumeSeekBar2.isTracking) {
                            if (soundCraftVolumeSeekBar2.init) {
                                num.getClass();
                                soundCraftVolumeSeekBar2.setProgress(num.intValue());
                                soundCraftVolumeSeekBar2.init = false;
                            } else {
                                num.getClass();
                                soundCraftVolumeSeekBar2.springFinalPosition = num.intValue();
                                soundCraftVolumeSeekBar2.progressBarSpring.setStartValue(soundCraftVolumeSeekBar2.getProgress());
                                soundCraftVolumeSeekBar2.progressBarSpring.animateToFinalPosition(soundCraftVolumeSeekBar2.springFinalPosition);
                            }
                        }
                        break;
                    case 1:
                        int i7 = SoundCraftVolumeSeekBar.$r8$clinit;
                        soundCraftVolumeSeekBar2.setMin(((Integer) obj).intValue());
                        break;
                    case 2:
                        int i8 = SoundCraftVolumeSeekBar.$r8$clinit;
                        soundCraftVolumeSeekBar2.setMax(((Integer) obj).intValue());
                        break;
                    default:
                        int i9 = SoundCraftVolumeSeekBar.$r8$clinit;
                        soundCraftVolumeSeekBar2.setEnabled(((Boolean) obj).booleanValue());
                        break;
                }
                return Unit.INSTANCE;
            }
        }));
        viewModel$1.progressMin.observe(lifecycleOwner4, new SoundCraftVolumeSeekBar$sam$androidx_lifecycle_Observer$0(new Function1() { // from class: com.android.systemui.audio.soundcraft.view.volume.SoundCraftVolumeSeekBar$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                SoundCraftVolumeSeekBar soundCraftVolumeSeekBar2 = soundCraftVolumeSeekBar;
                switch (i4) {
                    case 0:
                        Integer num = (Integer) obj;
                        if (!soundCraftVolumeSeekBar2.isTracking) {
                            if (soundCraftVolumeSeekBar2.init) {
                                num.getClass();
                                soundCraftVolumeSeekBar2.setProgress(num.intValue());
                                soundCraftVolumeSeekBar2.init = false;
                            } else {
                                num.getClass();
                                soundCraftVolumeSeekBar2.springFinalPosition = num.intValue();
                                soundCraftVolumeSeekBar2.progressBarSpring.setStartValue(soundCraftVolumeSeekBar2.getProgress());
                                soundCraftVolumeSeekBar2.progressBarSpring.animateToFinalPosition(soundCraftVolumeSeekBar2.springFinalPosition);
                            }
                        }
                        break;
                    case 1:
                        int i7 = SoundCraftVolumeSeekBar.$r8$clinit;
                        soundCraftVolumeSeekBar2.setMin(((Integer) obj).intValue());
                        break;
                    case 2:
                        int i8 = SoundCraftVolumeSeekBar.$r8$clinit;
                        soundCraftVolumeSeekBar2.setMax(((Integer) obj).intValue());
                        break;
                    default:
                        int i9 = SoundCraftVolumeSeekBar.$r8$clinit;
                        soundCraftVolumeSeekBar2.setEnabled(((Boolean) obj).booleanValue());
                        break;
                }
                return Unit.INSTANCE;
            }
        }));
        viewModel$1.progressMax.observe(lifecycleOwner4, new SoundCraftVolumeSeekBar$sam$androidx_lifecycle_Observer$0(new Function1() { // from class: com.android.systemui.audio.soundcraft.view.volume.SoundCraftVolumeSeekBar$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                SoundCraftVolumeSeekBar soundCraftVolumeSeekBar2 = soundCraftVolumeSeekBar;
                switch (i3) {
                    case 0:
                        Integer num = (Integer) obj;
                        if (!soundCraftVolumeSeekBar2.isTracking) {
                            if (soundCraftVolumeSeekBar2.init) {
                                num.getClass();
                                soundCraftVolumeSeekBar2.setProgress(num.intValue());
                                soundCraftVolumeSeekBar2.init = false;
                            } else {
                                num.getClass();
                                soundCraftVolumeSeekBar2.springFinalPosition = num.intValue();
                                soundCraftVolumeSeekBar2.progressBarSpring.setStartValue(soundCraftVolumeSeekBar2.getProgress());
                                soundCraftVolumeSeekBar2.progressBarSpring.animateToFinalPosition(soundCraftVolumeSeekBar2.springFinalPosition);
                            }
                        }
                        break;
                    case 1:
                        int i7 = SoundCraftVolumeSeekBar.$r8$clinit;
                        soundCraftVolumeSeekBar2.setMin(((Integer) obj).intValue());
                        break;
                    case 2:
                        int i8 = SoundCraftVolumeSeekBar.$r8$clinit;
                        soundCraftVolumeSeekBar2.setMax(((Integer) obj).intValue());
                        break;
                    default:
                        int i9 = SoundCraftVolumeSeekBar.$r8$clinit;
                        soundCraftVolumeSeekBar2.setEnabled(((Boolean) obj).booleanValue());
                        break;
                }
                return Unit.INSTANCE;
            }
        }));
        viewModel$1.seekBarEnabled.observe(lifecycleOwner4, new SoundCraftVolumeSeekBar$sam$androidx_lifecycle_Observer$0(new Function1() { // from class: com.android.systemui.audio.soundcraft.view.volume.SoundCraftVolumeSeekBar$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                SoundCraftVolumeSeekBar soundCraftVolumeSeekBar2 = soundCraftVolumeSeekBar;
                switch (i2) {
                    case 0:
                        Integer num = (Integer) obj;
                        if (!soundCraftVolumeSeekBar2.isTracking) {
                            if (soundCraftVolumeSeekBar2.init) {
                                num.getClass();
                                soundCraftVolumeSeekBar2.setProgress(num.intValue());
                                soundCraftVolumeSeekBar2.init = false;
                            } else {
                                num.getClass();
                                soundCraftVolumeSeekBar2.springFinalPosition = num.intValue();
                                soundCraftVolumeSeekBar2.progressBarSpring.setStartValue(soundCraftVolumeSeekBar2.getProgress());
                                soundCraftVolumeSeekBar2.progressBarSpring.animateToFinalPosition(soundCraftVolumeSeekBar2.springFinalPosition);
                            }
                        }
                        break;
                    case 1:
                        int i7 = SoundCraftVolumeSeekBar.$r8$clinit;
                        soundCraftVolumeSeekBar2.setMin(((Integer) obj).intValue());
                        break;
                    case 2:
                        int i8 = SoundCraftVolumeSeekBar.$r8$clinit;
                        soundCraftVolumeSeekBar2.setMax(((Integer) obj).intValue());
                        break;
                    default:
                        int i9 = SoundCraftVolumeSeekBar.$r8$clinit;
                        soundCraftVolumeSeekBar2.setEnabled(((Boolean) obj).booleanValue());
                        break;
                }
                return Unit.INSTANCE;
            }
        }));
        soundCraftVolumeSeekBar.setContentDescription(soundCraftVolumeSeekBar.getContext().getString(R.string.volumepanel_media));
        final SoundCraftVolumeIcon soundCraftVolumeIcon = volumeBarViewBinding.volumeIcon;
        soundCraftVolumeIcon.stream = 3;
        LifecycleOwner lifecycleOwner5 = ViewTreeLifecycleOwner.get(soundCraftVolumeIcon);
        lifecycleOwner5.getClass();
        final VolumeBarViewModel viewModel = soundCraftVolumeIcon.getViewModel();
        viewModel.progress.observe(lifecycleOwner5, new SoundCraftVolumeIcon$sam$androidx_lifecycle_Observer$0(new Function1() { // from class: com.android.systemui.audio.soundcraft.view.volume.SoundCraftVolumeIcon$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) throws Resources.NotFoundException {
                SoundCraftVolumeIcon soundCraftVolumeIcon2 = soundCraftVolumeIcon;
                switch (i5) {
                    case 0:
                        Integer num = (Integer) obj;
                        soundCraftVolumeIcon2.shouldUpdateIcon = soundCraftVolumeIcon2.init;
                        num.getClass();
                        soundCraftVolumeIcon2.updateLayout$2(num.intValue());
                        break;
                    default:
                        Boolean bool = (Boolean) obj;
                        int i7 = SoundCraftVolumeIcon.$r8$clinit;
                        soundCraftVolumeIcon2.setEnabled(bool.booleanValue());
                        soundCraftVolumeIcon2.setAlpha(bool.booleanValue() ? 0.85f : 0.4f);
                        break;
                }
                return Unit.INSTANCE;
            }
        }));
        viewModel.seekBarEnabled.observe(lifecycleOwner5, new SoundCraftVolumeIcon$sam$androidx_lifecycle_Observer$0(new Function1() { // from class: com.android.systemui.audio.soundcraft.view.volume.SoundCraftVolumeIcon$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) throws Resources.NotFoundException {
                SoundCraftVolumeIcon soundCraftVolumeIcon2 = soundCraftVolumeIcon;
                switch (i4) {
                    case 0:
                        Integer num = (Integer) obj;
                        soundCraftVolumeIcon2.shouldUpdateIcon = soundCraftVolumeIcon2.init;
                        num.getClass();
                        soundCraftVolumeIcon2.updateLayout$2(num.intValue());
                        break;
                    default:
                        Boolean bool = (Boolean) obj;
                        int i7 = SoundCraftVolumeIcon.$r8$clinit;
                        soundCraftVolumeIcon2.setEnabled(bool.booleanValue());
                        soundCraftVolumeIcon2.setAlpha(bool.booleanValue() ? 0.85f : 0.4f);
                        break;
                }
                return Unit.INSTANCE;
            }
        }));
        viewModel.iconAnimationType.observe(lifecycleOwner5, new SoundCraftVolumeIcon$sam$androidx_lifecycle_Observer$0(new Function1() { // from class: com.android.systemui.audio.soundcraft.view.volume.SoundCraftVolumeIcon$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) throws Resources.NotFoundException {
                Boolean bool = (Boolean) obj;
                SoundCraftVolumeIcon soundCraftVolumeIcon2 = soundCraftVolumeIcon;
                soundCraftVolumeIcon2.shouldUpdateIcon = soundCraftVolumeIcon2.init || !Boolean.valueOf(soundCraftVolumeIcon2.isAnimateType).equals(bool);
                soundCraftVolumeIcon2.isAnimateType = bool.booleanValue();
                Integer num = (Integer) viewModel.progress.getValue();
                if (num != null) {
                    soundCraftVolumeIcon2.updateLayout$2(num.intValue());
                }
                return Unit.INSTANCE;
            }
        }));
        BatteryInfoBoxViewBinding batteryInfoBoxViewBinding = soundCraftViewBinding.batteryInfoBox;
        batteryInfoBoxViewBinding.root.viewBinding = batteryInfoBoxViewBinding;
        NoiseControlBoxViewBinding noiseControlBoxViewBinding = soundCraftViewBinding.noiseControlBox;
        noiseControlBoxViewBinding.root.viewBinding = noiseControlBoxViewBinding;
        final AudioEffectBoxLayoutBinding audioEffectBoxLayoutBinding = soundCraftViewBinding.audioEffectBox;
        final AudioEffectBoxView audioEffectBoxView = audioEffectBoxLayoutBinding.box;
        audioEffectBoxView.viewBinding = audioEffectBoxLayoutBinding;
        LifecycleOwner lifecycleOwner6 = ViewTreeLifecycleOwner.get(audioEffectBoxView);
        lifecycleOwner6.getClass();
        audioEffectBoxView.getViewModel().isDolbyVisible.observe(lifecycleOwner6, new AudioEffectBoxView$sam$androidx_lifecycle_Observer$0(new Function1() { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectBoxView$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                AudioEffectBoxView audioEffectBoxView2 = audioEffectBoxView;
                Boolean bool = (Boolean) obj;
                switch (i5) {
                    case 0:
                        int i7 = AudioEffectBoxView.$r8$clinit;
                        ((BaseAudioEffectItemView) audioEffectBoxView2.dolbyView$delegate.getValue()).getRootView().setVisibility(bool.booleanValue() ? 0 : 8);
                        if (bool.booleanValue()) {
                            ((BaseAudioEffectItemView) audioEffectBoxView2.dolbyView$delegate.getValue()).update();
                        }
                        return Unit.INSTANCE;
                    case 1:
                        int i8 = AudioEffectBoxView.$r8$clinit;
                        SoundCraftViewModelExt soundCraftViewModelExt = SoundCraftViewModelExt.INSTANCE;
                        SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
                        SoundCraftViewComponent soundCraftViewComponent = SoundCraftLocalViewModelStoreOwner.current;
                        if (soundCraftViewComponent == null) {
                            throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
                        }
                        ((AudioEffectHeaderViewModel) SoundCraftDetailPageView$$ExternalSyntheticOutline0.m(soundCraftViewModelExt, soundCraftViewComponent, soundCraftViewComponent, AudioEffectHeaderViewModel.class)).notifyChange();
                        ((BaseAudioEffectItemView) audioEffectBoxView2.equalizerView$delegate.getValue()).getRootView().setVisibility(bool.booleanValue() ? 0 : 8);
                        if (bool.booleanValue()) {
                            ((BaseAudioEffectItemView) audioEffectBoxView2.equalizerView$delegate.getValue()).update();
                        }
                        return Unit.INSTANCE;
                    case 2:
                        int i9 = AudioEffectBoxView.$r8$clinit;
                        ((BaseAudioEffectItemView) audioEffectBoxView2.voiceBoostView$delegate.getValue()).getRootView().setVisibility(bool.booleanValue() ? 0 : 8);
                        if (bool.booleanValue()) {
                            ((BaseAudioEffectItemView) audioEffectBoxView2.voiceBoostView$delegate.getValue()).update();
                        }
                        return Unit.INSTANCE;
                    case 3:
                        int i10 = AudioEffectBoxView.$r8$clinit;
                        BaseAudioEffectItemView baseAudioEffectItemView = (BaseAudioEffectItemView) audioEffectBoxView2.voiceBoostView$delegate.getValue();
                        bool.getClass();
                        baseAudioEffectItemView.enable(bool.booleanValue());
                        return Unit.INSTANCE;
                    case 4:
                        int i11 = AudioEffectBoxView.$r8$clinit;
                        ((BaseAudioEffectItemView) audioEffectBoxView2.volumeNormalizationView$delegate.getValue()).getRootView().setVisibility(bool.booleanValue() ? 0 : 8);
                        if (bool.booleanValue()) {
                            ((BaseAudioEffectItemView) audioEffectBoxView2.volumeNormalizationView$delegate.getValue()).update();
                        }
                        return Unit.INSTANCE;
                    case 5:
                        int i12 = AudioEffectBoxView.$r8$clinit;
                        BaseAudioEffectItemView baseAudioEffectItemView2 = (BaseAudioEffectItemView) audioEffectBoxView2.dolbyView$delegate.getValue();
                        bool.getClass();
                        baseAudioEffectItemView2.enable(bool.booleanValue());
                        return Unit.INSTANCE;
                    default:
                        int i13 = AudioEffectBoxView.$r8$clinit;
                        if (Intrinsics.areEqual(audioEffectBoxView2.getViewModel().isHeadTrackingVisible.getValue(), Boolean.TRUE)) {
                            ((BaseAudioEffectItemView) audioEffectBoxView2.spatialAudioView$delegate.getValue()).getRootView().setVisibility(bool.booleanValue() ? 0 : 8);
                            if (bool.booleanValue()) {
                                ((BaseAudioEffectItemView) audioEffectBoxView2.spatialAudioView$delegate.getValue()).update();
                            }
                        } else {
                            ((BaseAudioEffectItemView) audioEffectBoxView2.spatialAudioSwitchView$delegate.getValue()).getRootView().setVisibility(0);
                            ((BaseAudioEffectItemView) audioEffectBoxView2.spatialAudioSwitchView$delegate.getValue()).update();
                        }
                        return Unit.INSTANCE;
                }
            }
        }));
        final int i7 = 5;
        audioEffectBoxView.getViewModel().isDolbyEnable.observe(lifecycleOwner6, new AudioEffectBoxView$sam$androidx_lifecycle_Observer$0(new Function1() { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectBoxView$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                AudioEffectBoxView audioEffectBoxView2 = audioEffectBoxView;
                Boolean bool = (Boolean) obj;
                switch (i7) {
                    case 0:
                        int i72 = AudioEffectBoxView.$r8$clinit;
                        ((BaseAudioEffectItemView) audioEffectBoxView2.dolbyView$delegate.getValue()).getRootView().setVisibility(bool.booleanValue() ? 0 : 8);
                        if (bool.booleanValue()) {
                            ((BaseAudioEffectItemView) audioEffectBoxView2.dolbyView$delegate.getValue()).update();
                        }
                        return Unit.INSTANCE;
                    case 1:
                        int i8 = AudioEffectBoxView.$r8$clinit;
                        SoundCraftViewModelExt soundCraftViewModelExt = SoundCraftViewModelExt.INSTANCE;
                        SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
                        SoundCraftViewComponent soundCraftViewComponent = SoundCraftLocalViewModelStoreOwner.current;
                        if (soundCraftViewComponent == null) {
                            throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
                        }
                        ((AudioEffectHeaderViewModel) SoundCraftDetailPageView$$ExternalSyntheticOutline0.m(soundCraftViewModelExt, soundCraftViewComponent, soundCraftViewComponent, AudioEffectHeaderViewModel.class)).notifyChange();
                        ((BaseAudioEffectItemView) audioEffectBoxView2.equalizerView$delegate.getValue()).getRootView().setVisibility(bool.booleanValue() ? 0 : 8);
                        if (bool.booleanValue()) {
                            ((BaseAudioEffectItemView) audioEffectBoxView2.equalizerView$delegate.getValue()).update();
                        }
                        return Unit.INSTANCE;
                    case 2:
                        int i9 = AudioEffectBoxView.$r8$clinit;
                        ((BaseAudioEffectItemView) audioEffectBoxView2.voiceBoostView$delegate.getValue()).getRootView().setVisibility(bool.booleanValue() ? 0 : 8);
                        if (bool.booleanValue()) {
                            ((BaseAudioEffectItemView) audioEffectBoxView2.voiceBoostView$delegate.getValue()).update();
                        }
                        return Unit.INSTANCE;
                    case 3:
                        int i10 = AudioEffectBoxView.$r8$clinit;
                        BaseAudioEffectItemView baseAudioEffectItemView = (BaseAudioEffectItemView) audioEffectBoxView2.voiceBoostView$delegate.getValue();
                        bool.getClass();
                        baseAudioEffectItemView.enable(bool.booleanValue());
                        return Unit.INSTANCE;
                    case 4:
                        int i11 = AudioEffectBoxView.$r8$clinit;
                        ((BaseAudioEffectItemView) audioEffectBoxView2.volumeNormalizationView$delegate.getValue()).getRootView().setVisibility(bool.booleanValue() ? 0 : 8);
                        if (bool.booleanValue()) {
                            ((BaseAudioEffectItemView) audioEffectBoxView2.volumeNormalizationView$delegate.getValue()).update();
                        }
                        return Unit.INSTANCE;
                    case 5:
                        int i12 = AudioEffectBoxView.$r8$clinit;
                        BaseAudioEffectItemView baseAudioEffectItemView2 = (BaseAudioEffectItemView) audioEffectBoxView2.dolbyView$delegate.getValue();
                        bool.getClass();
                        baseAudioEffectItemView2.enable(bool.booleanValue());
                        return Unit.INSTANCE;
                    default:
                        int i13 = AudioEffectBoxView.$r8$clinit;
                        if (Intrinsics.areEqual(audioEffectBoxView2.getViewModel().isHeadTrackingVisible.getValue(), Boolean.TRUE)) {
                            ((BaseAudioEffectItemView) audioEffectBoxView2.spatialAudioView$delegate.getValue()).getRootView().setVisibility(bool.booleanValue() ? 0 : 8);
                            if (bool.booleanValue()) {
                                ((BaseAudioEffectItemView) audioEffectBoxView2.spatialAudioView$delegate.getValue()).update();
                            }
                        } else {
                            ((BaseAudioEffectItemView) audioEffectBoxView2.spatialAudioSwitchView$delegate.getValue()).getRootView().setVisibility(0);
                            ((BaseAudioEffectItemView) audioEffectBoxView2.spatialAudioSwitchView$delegate.getValue()).update();
                        }
                        return Unit.INSTANCE;
                }
            }
        }));
        final int i8 = 6;
        audioEffectBoxView.getViewModel().isSpatialAudioVisible.observe(lifecycleOwner6, new AudioEffectBoxView$sam$androidx_lifecycle_Observer$0(new Function1() { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectBoxView$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                AudioEffectBoxView audioEffectBoxView2 = audioEffectBoxView;
                Boolean bool = (Boolean) obj;
                switch (i8) {
                    case 0:
                        int i72 = AudioEffectBoxView.$r8$clinit;
                        ((BaseAudioEffectItemView) audioEffectBoxView2.dolbyView$delegate.getValue()).getRootView().setVisibility(bool.booleanValue() ? 0 : 8);
                        if (bool.booleanValue()) {
                            ((BaseAudioEffectItemView) audioEffectBoxView2.dolbyView$delegate.getValue()).update();
                        }
                        return Unit.INSTANCE;
                    case 1:
                        int i82 = AudioEffectBoxView.$r8$clinit;
                        SoundCraftViewModelExt soundCraftViewModelExt = SoundCraftViewModelExt.INSTANCE;
                        SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
                        SoundCraftViewComponent soundCraftViewComponent = SoundCraftLocalViewModelStoreOwner.current;
                        if (soundCraftViewComponent == null) {
                            throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
                        }
                        ((AudioEffectHeaderViewModel) SoundCraftDetailPageView$$ExternalSyntheticOutline0.m(soundCraftViewModelExt, soundCraftViewComponent, soundCraftViewComponent, AudioEffectHeaderViewModel.class)).notifyChange();
                        ((BaseAudioEffectItemView) audioEffectBoxView2.equalizerView$delegate.getValue()).getRootView().setVisibility(bool.booleanValue() ? 0 : 8);
                        if (bool.booleanValue()) {
                            ((BaseAudioEffectItemView) audioEffectBoxView2.equalizerView$delegate.getValue()).update();
                        }
                        return Unit.INSTANCE;
                    case 2:
                        int i9 = AudioEffectBoxView.$r8$clinit;
                        ((BaseAudioEffectItemView) audioEffectBoxView2.voiceBoostView$delegate.getValue()).getRootView().setVisibility(bool.booleanValue() ? 0 : 8);
                        if (bool.booleanValue()) {
                            ((BaseAudioEffectItemView) audioEffectBoxView2.voiceBoostView$delegate.getValue()).update();
                        }
                        return Unit.INSTANCE;
                    case 3:
                        int i10 = AudioEffectBoxView.$r8$clinit;
                        BaseAudioEffectItemView baseAudioEffectItemView = (BaseAudioEffectItemView) audioEffectBoxView2.voiceBoostView$delegate.getValue();
                        bool.getClass();
                        baseAudioEffectItemView.enable(bool.booleanValue());
                        return Unit.INSTANCE;
                    case 4:
                        int i11 = AudioEffectBoxView.$r8$clinit;
                        ((BaseAudioEffectItemView) audioEffectBoxView2.volumeNormalizationView$delegate.getValue()).getRootView().setVisibility(bool.booleanValue() ? 0 : 8);
                        if (bool.booleanValue()) {
                            ((BaseAudioEffectItemView) audioEffectBoxView2.volumeNormalizationView$delegate.getValue()).update();
                        }
                        return Unit.INSTANCE;
                    case 5:
                        int i12 = AudioEffectBoxView.$r8$clinit;
                        BaseAudioEffectItemView baseAudioEffectItemView2 = (BaseAudioEffectItemView) audioEffectBoxView2.dolbyView$delegate.getValue();
                        bool.getClass();
                        baseAudioEffectItemView2.enable(bool.booleanValue());
                        return Unit.INSTANCE;
                    default:
                        int i13 = AudioEffectBoxView.$r8$clinit;
                        if (Intrinsics.areEqual(audioEffectBoxView2.getViewModel().isHeadTrackingVisible.getValue(), Boolean.TRUE)) {
                            ((BaseAudioEffectItemView) audioEffectBoxView2.spatialAudioView$delegate.getValue()).getRootView().setVisibility(bool.booleanValue() ? 0 : 8);
                            if (bool.booleanValue()) {
                                ((BaseAudioEffectItemView) audioEffectBoxView2.spatialAudioView$delegate.getValue()).update();
                            }
                        } else {
                            ((BaseAudioEffectItemView) audioEffectBoxView2.spatialAudioSwitchView$delegate.getValue()).getRootView().setVisibility(0);
                            ((BaseAudioEffectItemView) audioEffectBoxView2.spatialAudioSwitchView$delegate.getValue()).update();
                        }
                        return Unit.INSTANCE;
                }
            }
        }));
        audioEffectBoxView.getViewModel().isEqualizerVisible.observe(lifecycleOwner6, new AudioEffectBoxView$sam$androidx_lifecycle_Observer$0(new Function1() { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectBoxView$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                AudioEffectBoxView audioEffectBoxView2 = audioEffectBoxView;
                Boolean bool = (Boolean) obj;
                switch (i4) {
                    case 0:
                        int i72 = AudioEffectBoxView.$r8$clinit;
                        ((BaseAudioEffectItemView) audioEffectBoxView2.dolbyView$delegate.getValue()).getRootView().setVisibility(bool.booleanValue() ? 0 : 8);
                        if (bool.booleanValue()) {
                            ((BaseAudioEffectItemView) audioEffectBoxView2.dolbyView$delegate.getValue()).update();
                        }
                        return Unit.INSTANCE;
                    case 1:
                        int i82 = AudioEffectBoxView.$r8$clinit;
                        SoundCraftViewModelExt soundCraftViewModelExt = SoundCraftViewModelExt.INSTANCE;
                        SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
                        SoundCraftViewComponent soundCraftViewComponent = SoundCraftLocalViewModelStoreOwner.current;
                        if (soundCraftViewComponent == null) {
                            throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
                        }
                        ((AudioEffectHeaderViewModel) SoundCraftDetailPageView$$ExternalSyntheticOutline0.m(soundCraftViewModelExt, soundCraftViewComponent, soundCraftViewComponent, AudioEffectHeaderViewModel.class)).notifyChange();
                        ((BaseAudioEffectItemView) audioEffectBoxView2.equalizerView$delegate.getValue()).getRootView().setVisibility(bool.booleanValue() ? 0 : 8);
                        if (bool.booleanValue()) {
                            ((BaseAudioEffectItemView) audioEffectBoxView2.equalizerView$delegate.getValue()).update();
                        }
                        return Unit.INSTANCE;
                    case 2:
                        int i9 = AudioEffectBoxView.$r8$clinit;
                        ((BaseAudioEffectItemView) audioEffectBoxView2.voiceBoostView$delegate.getValue()).getRootView().setVisibility(bool.booleanValue() ? 0 : 8);
                        if (bool.booleanValue()) {
                            ((BaseAudioEffectItemView) audioEffectBoxView2.voiceBoostView$delegate.getValue()).update();
                        }
                        return Unit.INSTANCE;
                    case 3:
                        int i10 = AudioEffectBoxView.$r8$clinit;
                        BaseAudioEffectItemView baseAudioEffectItemView = (BaseAudioEffectItemView) audioEffectBoxView2.voiceBoostView$delegate.getValue();
                        bool.getClass();
                        baseAudioEffectItemView.enable(bool.booleanValue());
                        return Unit.INSTANCE;
                    case 4:
                        int i11 = AudioEffectBoxView.$r8$clinit;
                        ((BaseAudioEffectItemView) audioEffectBoxView2.volumeNormalizationView$delegate.getValue()).getRootView().setVisibility(bool.booleanValue() ? 0 : 8);
                        if (bool.booleanValue()) {
                            ((BaseAudioEffectItemView) audioEffectBoxView2.volumeNormalizationView$delegate.getValue()).update();
                        }
                        return Unit.INSTANCE;
                    case 5:
                        int i12 = AudioEffectBoxView.$r8$clinit;
                        BaseAudioEffectItemView baseAudioEffectItemView2 = (BaseAudioEffectItemView) audioEffectBoxView2.dolbyView$delegate.getValue();
                        bool.getClass();
                        baseAudioEffectItemView2.enable(bool.booleanValue());
                        return Unit.INSTANCE;
                    default:
                        int i13 = AudioEffectBoxView.$r8$clinit;
                        if (Intrinsics.areEqual(audioEffectBoxView2.getViewModel().isHeadTrackingVisible.getValue(), Boolean.TRUE)) {
                            ((BaseAudioEffectItemView) audioEffectBoxView2.spatialAudioView$delegate.getValue()).getRootView().setVisibility(bool.booleanValue() ? 0 : 8);
                            if (bool.booleanValue()) {
                                ((BaseAudioEffectItemView) audioEffectBoxView2.spatialAudioView$delegate.getValue()).update();
                            }
                        } else {
                            ((BaseAudioEffectItemView) audioEffectBoxView2.spatialAudioSwitchView$delegate.getValue()).getRootView().setVisibility(0);
                            ((BaseAudioEffectItemView) audioEffectBoxView2.spatialAudioSwitchView$delegate.getValue()).update();
                        }
                        return Unit.INSTANCE;
                }
            }
        }));
        audioEffectBoxView.getViewModel().isVoiceBoostVisible.observe(lifecycleOwner6, new AudioEffectBoxView$sam$androidx_lifecycle_Observer$0(new Function1() { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectBoxView$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                AudioEffectBoxView audioEffectBoxView2 = audioEffectBoxView;
                Boolean bool = (Boolean) obj;
                switch (i3) {
                    case 0:
                        int i72 = AudioEffectBoxView.$r8$clinit;
                        ((BaseAudioEffectItemView) audioEffectBoxView2.dolbyView$delegate.getValue()).getRootView().setVisibility(bool.booleanValue() ? 0 : 8);
                        if (bool.booleanValue()) {
                            ((BaseAudioEffectItemView) audioEffectBoxView2.dolbyView$delegate.getValue()).update();
                        }
                        return Unit.INSTANCE;
                    case 1:
                        int i82 = AudioEffectBoxView.$r8$clinit;
                        SoundCraftViewModelExt soundCraftViewModelExt = SoundCraftViewModelExt.INSTANCE;
                        SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
                        SoundCraftViewComponent soundCraftViewComponent = SoundCraftLocalViewModelStoreOwner.current;
                        if (soundCraftViewComponent == null) {
                            throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
                        }
                        ((AudioEffectHeaderViewModel) SoundCraftDetailPageView$$ExternalSyntheticOutline0.m(soundCraftViewModelExt, soundCraftViewComponent, soundCraftViewComponent, AudioEffectHeaderViewModel.class)).notifyChange();
                        ((BaseAudioEffectItemView) audioEffectBoxView2.equalizerView$delegate.getValue()).getRootView().setVisibility(bool.booleanValue() ? 0 : 8);
                        if (bool.booleanValue()) {
                            ((BaseAudioEffectItemView) audioEffectBoxView2.equalizerView$delegate.getValue()).update();
                        }
                        return Unit.INSTANCE;
                    case 2:
                        int i9 = AudioEffectBoxView.$r8$clinit;
                        ((BaseAudioEffectItemView) audioEffectBoxView2.voiceBoostView$delegate.getValue()).getRootView().setVisibility(bool.booleanValue() ? 0 : 8);
                        if (bool.booleanValue()) {
                            ((BaseAudioEffectItemView) audioEffectBoxView2.voiceBoostView$delegate.getValue()).update();
                        }
                        return Unit.INSTANCE;
                    case 3:
                        int i10 = AudioEffectBoxView.$r8$clinit;
                        BaseAudioEffectItemView baseAudioEffectItemView = (BaseAudioEffectItemView) audioEffectBoxView2.voiceBoostView$delegate.getValue();
                        bool.getClass();
                        baseAudioEffectItemView.enable(bool.booleanValue());
                        return Unit.INSTANCE;
                    case 4:
                        int i11 = AudioEffectBoxView.$r8$clinit;
                        ((BaseAudioEffectItemView) audioEffectBoxView2.volumeNormalizationView$delegate.getValue()).getRootView().setVisibility(bool.booleanValue() ? 0 : 8);
                        if (bool.booleanValue()) {
                            ((BaseAudioEffectItemView) audioEffectBoxView2.volumeNormalizationView$delegate.getValue()).update();
                        }
                        return Unit.INSTANCE;
                    case 5:
                        int i12 = AudioEffectBoxView.$r8$clinit;
                        BaseAudioEffectItemView baseAudioEffectItemView2 = (BaseAudioEffectItemView) audioEffectBoxView2.dolbyView$delegate.getValue();
                        bool.getClass();
                        baseAudioEffectItemView2.enable(bool.booleanValue());
                        return Unit.INSTANCE;
                    default:
                        int i13 = AudioEffectBoxView.$r8$clinit;
                        if (Intrinsics.areEqual(audioEffectBoxView2.getViewModel().isHeadTrackingVisible.getValue(), Boolean.TRUE)) {
                            ((BaseAudioEffectItemView) audioEffectBoxView2.spatialAudioView$delegate.getValue()).getRootView().setVisibility(bool.booleanValue() ? 0 : 8);
                            if (bool.booleanValue()) {
                                ((BaseAudioEffectItemView) audioEffectBoxView2.spatialAudioView$delegate.getValue()).update();
                            }
                        } else {
                            ((BaseAudioEffectItemView) audioEffectBoxView2.spatialAudioSwitchView$delegate.getValue()).getRootView().setVisibility(0);
                            ((BaseAudioEffectItemView) audioEffectBoxView2.spatialAudioSwitchView$delegate.getValue()).update();
                        }
                        return Unit.INSTANCE;
                }
            }
        }));
        audioEffectBoxView.getViewModel().isVoiceBoostEnable.observe(lifecycleOwner6, new AudioEffectBoxView$sam$androidx_lifecycle_Observer$0(new Function1() { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectBoxView$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                AudioEffectBoxView audioEffectBoxView2 = audioEffectBoxView;
                Boolean bool = (Boolean) obj;
                switch (i2) {
                    case 0:
                        int i72 = AudioEffectBoxView.$r8$clinit;
                        ((BaseAudioEffectItemView) audioEffectBoxView2.dolbyView$delegate.getValue()).getRootView().setVisibility(bool.booleanValue() ? 0 : 8);
                        if (bool.booleanValue()) {
                            ((BaseAudioEffectItemView) audioEffectBoxView2.dolbyView$delegate.getValue()).update();
                        }
                        return Unit.INSTANCE;
                    case 1:
                        int i82 = AudioEffectBoxView.$r8$clinit;
                        SoundCraftViewModelExt soundCraftViewModelExt = SoundCraftViewModelExt.INSTANCE;
                        SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
                        SoundCraftViewComponent soundCraftViewComponent = SoundCraftLocalViewModelStoreOwner.current;
                        if (soundCraftViewComponent == null) {
                            throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
                        }
                        ((AudioEffectHeaderViewModel) SoundCraftDetailPageView$$ExternalSyntheticOutline0.m(soundCraftViewModelExt, soundCraftViewComponent, soundCraftViewComponent, AudioEffectHeaderViewModel.class)).notifyChange();
                        ((BaseAudioEffectItemView) audioEffectBoxView2.equalizerView$delegate.getValue()).getRootView().setVisibility(bool.booleanValue() ? 0 : 8);
                        if (bool.booleanValue()) {
                            ((BaseAudioEffectItemView) audioEffectBoxView2.equalizerView$delegate.getValue()).update();
                        }
                        return Unit.INSTANCE;
                    case 2:
                        int i9 = AudioEffectBoxView.$r8$clinit;
                        ((BaseAudioEffectItemView) audioEffectBoxView2.voiceBoostView$delegate.getValue()).getRootView().setVisibility(bool.booleanValue() ? 0 : 8);
                        if (bool.booleanValue()) {
                            ((BaseAudioEffectItemView) audioEffectBoxView2.voiceBoostView$delegate.getValue()).update();
                        }
                        return Unit.INSTANCE;
                    case 3:
                        int i10 = AudioEffectBoxView.$r8$clinit;
                        BaseAudioEffectItemView baseAudioEffectItemView = (BaseAudioEffectItemView) audioEffectBoxView2.voiceBoostView$delegate.getValue();
                        bool.getClass();
                        baseAudioEffectItemView.enable(bool.booleanValue());
                        return Unit.INSTANCE;
                    case 4:
                        int i11 = AudioEffectBoxView.$r8$clinit;
                        ((BaseAudioEffectItemView) audioEffectBoxView2.volumeNormalizationView$delegate.getValue()).getRootView().setVisibility(bool.booleanValue() ? 0 : 8);
                        if (bool.booleanValue()) {
                            ((BaseAudioEffectItemView) audioEffectBoxView2.volumeNormalizationView$delegate.getValue()).update();
                        }
                        return Unit.INSTANCE;
                    case 5:
                        int i12 = AudioEffectBoxView.$r8$clinit;
                        BaseAudioEffectItemView baseAudioEffectItemView2 = (BaseAudioEffectItemView) audioEffectBoxView2.dolbyView$delegate.getValue();
                        bool.getClass();
                        baseAudioEffectItemView2.enable(bool.booleanValue());
                        return Unit.INSTANCE;
                    default:
                        int i13 = AudioEffectBoxView.$r8$clinit;
                        if (Intrinsics.areEqual(audioEffectBoxView2.getViewModel().isHeadTrackingVisible.getValue(), Boolean.TRUE)) {
                            ((BaseAudioEffectItemView) audioEffectBoxView2.spatialAudioView$delegate.getValue()).getRootView().setVisibility(bool.booleanValue() ? 0 : 8);
                            if (bool.booleanValue()) {
                                ((BaseAudioEffectItemView) audioEffectBoxView2.spatialAudioView$delegate.getValue()).update();
                            }
                        } else {
                            ((BaseAudioEffectItemView) audioEffectBoxView2.spatialAudioSwitchView$delegate.getValue()).getRootView().setVisibility(0);
                            ((BaseAudioEffectItemView) audioEffectBoxView2.spatialAudioSwitchView$delegate.getValue()).update();
                        }
                        return Unit.INSTANCE;
                }
            }
        }));
        audioEffectBoxView.getViewModel().isVolumeNormalizationVisible.observe(lifecycleOwner6, new AudioEffectBoxView$sam$androidx_lifecycle_Observer$0(new Function1() { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectBoxView$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                AudioEffectBoxView audioEffectBoxView2 = audioEffectBoxView;
                Boolean bool = (Boolean) obj;
                switch (i) {
                    case 0:
                        int i72 = AudioEffectBoxView.$r8$clinit;
                        ((BaseAudioEffectItemView) audioEffectBoxView2.dolbyView$delegate.getValue()).getRootView().setVisibility(bool.booleanValue() ? 0 : 8);
                        if (bool.booleanValue()) {
                            ((BaseAudioEffectItemView) audioEffectBoxView2.dolbyView$delegate.getValue()).update();
                        }
                        return Unit.INSTANCE;
                    case 1:
                        int i82 = AudioEffectBoxView.$r8$clinit;
                        SoundCraftViewModelExt soundCraftViewModelExt = SoundCraftViewModelExt.INSTANCE;
                        SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
                        SoundCraftViewComponent soundCraftViewComponent = SoundCraftLocalViewModelStoreOwner.current;
                        if (soundCraftViewComponent == null) {
                            throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
                        }
                        ((AudioEffectHeaderViewModel) SoundCraftDetailPageView$$ExternalSyntheticOutline0.m(soundCraftViewModelExt, soundCraftViewComponent, soundCraftViewComponent, AudioEffectHeaderViewModel.class)).notifyChange();
                        ((BaseAudioEffectItemView) audioEffectBoxView2.equalizerView$delegate.getValue()).getRootView().setVisibility(bool.booleanValue() ? 0 : 8);
                        if (bool.booleanValue()) {
                            ((BaseAudioEffectItemView) audioEffectBoxView2.equalizerView$delegate.getValue()).update();
                        }
                        return Unit.INSTANCE;
                    case 2:
                        int i9 = AudioEffectBoxView.$r8$clinit;
                        ((BaseAudioEffectItemView) audioEffectBoxView2.voiceBoostView$delegate.getValue()).getRootView().setVisibility(bool.booleanValue() ? 0 : 8);
                        if (bool.booleanValue()) {
                            ((BaseAudioEffectItemView) audioEffectBoxView2.voiceBoostView$delegate.getValue()).update();
                        }
                        return Unit.INSTANCE;
                    case 3:
                        int i10 = AudioEffectBoxView.$r8$clinit;
                        BaseAudioEffectItemView baseAudioEffectItemView = (BaseAudioEffectItemView) audioEffectBoxView2.voiceBoostView$delegate.getValue();
                        bool.getClass();
                        baseAudioEffectItemView.enable(bool.booleanValue());
                        return Unit.INSTANCE;
                    case 4:
                        int i11 = AudioEffectBoxView.$r8$clinit;
                        ((BaseAudioEffectItemView) audioEffectBoxView2.volumeNormalizationView$delegate.getValue()).getRootView().setVisibility(bool.booleanValue() ? 0 : 8);
                        if (bool.booleanValue()) {
                            ((BaseAudioEffectItemView) audioEffectBoxView2.volumeNormalizationView$delegate.getValue()).update();
                        }
                        return Unit.INSTANCE;
                    case 5:
                        int i12 = AudioEffectBoxView.$r8$clinit;
                        BaseAudioEffectItemView baseAudioEffectItemView2 = (BaseAudioEffectItemView) audioEffectBoxView2.dolbyView$delegate.getValue();
                        bool.getClass();
                        baseAudioEffectItemView2.enable(bool.booleanValue());
                        return Unit.INSTANCE;
                    default:
                        int i13 = AudioEffectBoxView.$r8$clinit;
                        if (Intrinsics.areEqual(audioEffectBoxView2.getViewModel().isHeadTrackingVisible.getValue(), Boolean.TRUE)) {
                            ((BaseAudioEffectItemView) audioEffectBoxView2.spatialAudioView$delegate.getValue()).getRootView().setVisibility(bool.booleanValue() ? 0 : 8);
                            if (bool.booleanValue()) {
                                ((BaseAudioEffectItemView) audioEffectBoxView2.spatialAudioView$delegate.getValue()).update();
                            }
                        } else {
                            ((BaseAudioEffectItemView) audioEffectBoxView2.spatialAudioSwitchView$delegate.getValue()).getRootView().setVisibility(0);
                            ((BaseAudioEffectItemView) audioEffectBoxView2.spatialAudioSwitchView$delegate.getValue()).update();
                        }
                        return Unit.INSTANCE;
                }
            }
        }));
        audioEffectBoxView.getViewModel().isFallbackTextVisible.observe(lifecycleOwner6, new AudioEffectBoxView$sam$androidx_lifecycle_Observer$0(new Function1() { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectBoxView$$ExternalSyntheticLambda7
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                AudioEffectBoxLayoutBinding audioEffectBoxLayoutBinding2 = audioEffectBoxLayoutBinding;
                Boolean bool = (Boolean) obj;
                switch (i4) {
                    case 0:
                        int i9 = AudioEffectBoxView.$r8$clinit;
                        audioEffectBoxLayoutBinding2.detailJumpButton.setVisibility(bool.booleanValue() ? 0 : 8);
                        if (!bool.booleanValue()) {
                            LinearLayout linearLayout = audioEffectBoxLayoutBinding2.effectItemList;
                            linearLayout.setPadding(linearLayout.getPaddingStart(), audioEffectBoxLayoutBinding2.effectItemList.getPaddingTop(), audioEffectBoxLayoutBinding2.effectItemList.getPaddingEnd(), DimensionUtilsKt.dpToPx(3));
                        }
                        break;
                    default:
                        int i10 = AudioEffectBoxView.$r8$clinit;
                        audioEffectBoxLayoutBinding2.fallbackText.setVisibility(bool.booleanValue() ? 0 : 8);
                        audioEffectBoxLayoutBinding2.effectItemList.setVisibility(bool.booleanValue() ? 8 : 0);
                        break;
                }
                return Unit.INSTANCE;
            }
        }));
        audioEffectBoxView.getViewModel().fallbackMessage.observe(lifecycleOwner6, new AudioEffectBoxView$sam$androidx_lifecycle_Observer$0(new AudioEffectBoxView$$ExternalSyntheticLambda15(audioEffectBoxView, audioEffectBoxLayoutBinding)));
        audioEffectBoxView.getViewModel().isShowBoxBg.observe(lifecycleOwner6, new AudioEffectBoxView$sam$androidx_lifecycle_Observer$0(new AudioEffectBoxView$$ExternalSyntheticLambda15(audioEffectBoxLayoutBinding, audioEffectBoxView)));
        audioEffectBoxView.getViewModel().isDetailJumpButtonVisible.observe(lifecycleOwner6, new AudioEffectBoxView$sam$androidx_lifecycle_Observer$0(new Function1() { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectBoxView$$ExternalSyntheticLambda7
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                AudioEffectBoxLayoutBinding audioEffectBoxLayoutBinding2 = audioEffectBoxLayoutBinding;
                Boolean bool = (Boolean) obj;
                switch (i5) {
                    case 0:
                        int i9 = AudioEffectBoxView.$r8$clinit;
                        audioEffectBoxLayoutBinding2.detailJumpButton.setVisibility(bool.booleanValue() ? 0 : 8);
                        if (!bool.booleanValue()) {
                            LinearLayout linearLayout = audioEffectBoxLayoutBinding2.effectItemList;
                            linearLayout.setPadding(linearLayout.getPaddingStart(), audioEffectBoxLayoutBinding2.effectItemList.getPaddingTop(), audioEffectBoxLayoutBinding2.effectItemList.getPaddingEnd(), DimensionUtilsKt.dpToPx(3));
                        }
                        break;
                    default:
                        int i10 = AudioEffectBoxView.$r8$clinit;
                        audioEffectBoxLayoutBinding2.fallbackText.setVisibility(bool.booleanValue() ? 0 : 8);
                        audioEffectBoxLayoutBinding2.effectItemList.setVisibility(bool.booleanValue() ? 8 : 0);
                        break;
                }
                return Unit.INSTANCE;
            }
        }));
        TextView textView = audioEffectBoxLayoutBinding.detailJumpButton;
        FontSizeUtils.updateFontSize(textView, R.dimen.sec_qs_detail_button_text_size);
        textView.getTypeface().isLikeDefault = true;
        textView.setBackground(textView.getContext().getDrawable(R.drawable.sec_qs_btn_borderless_rect));
        textView.semSetButtonShapeEnabled(true);
        audioEffectBoxLayoutBinding.detailJumpButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectBoxView$bind$13
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Object failure;
                SoundCraftActivity.AnonymousClass5 anonymousClass5;
                SoundCraftSALogging.sendEventLog$default(SoundCraftSALogging.INSTANCE, SoundCraftSALogging.ScreenId.EID_BUDS_DETAIL_SETTING, SoundCraftSALogging.Event.DETAIL, null, 12);
                AudioEffectBoxView audioEffectBoxView2 = audioEffectBoxView;
                int i9 = AudioEffectBoxView.$r8$clinit;
                AudioEffectBoxViewModel viewModel2 = audioEffectBoxView2.getViewModel();
                viewModel2.getClass();
                try {
                    int i10 = Result.$r8$clinit;
                    ((ActivityStarter) Dependency.sDependency.getDependencyInner(ActivityStarter.class)).startActivity(viewModel2.intentFactory.createIntent(), true);
                    if (viewModel2.modelProvider.isFromCover && (anonymousClass5 = viewModel2.soundCraftCoverController.listener) != null) {
                        int i11 = SoundCraftActivity.$r8$clinit;
                        SoundCraftActivity soundCraftActivity = SoundCraftActivity.this;
                        SoundCraftActivity.AnonymousClass3 anonymousClass3 = soundCraftActivity.actionScreenReceiver;
                        if (anonymousClass3 != null) {
                            soundCraftActivity.unregisterReceiver(anonymousClass3);
                            soundCraftActivity.actionScreenReceiver = null;
                        }
                        soundCraftActivity.finish();
                    }
                    failure = Unit.INSTANCE;
                } catch (Throwable th) {
                    int i12 = Result.$r8$clinit;
                    failure = new Result.Failure(th);
                }
                Throwable thM3442exceptionOrNullimpl = Result.m3442exceptionOrNullimpl(failure);
                if (thM3442exceptionOrNullimpl != null) {
                    Log.d("SoundCraft.AudioEffectBoxViewModel", "jumpToDetailPage : onFailure, e=" + thM3442exceptionOrNullimpl);
                }
            }
        });
        final AudioEffectHeaderViewBinding audioEffectHeaderViewBinding = audioEffectBoxLayoutBinding.header;
        AudioEffectHeaderView audioEffectHeaderView = audioEffectHeaderViewBinding.root;
        audioEffectHeaderView.getClass();
        LifecycleOwner lifecycleOwner7 = ViewTreeLifecycleOwner.get(audioEffectHeaderView);
        lifecycleOwner7.getClass();
        ((AudioEffectHeaderViewModel) audioEffectHeaderView.viewModel$delegate.getValue()).title.observe(lifecycleOwner7, new AudioEffectHeaderView$sam$androidx_lifecycle_Observer$0(new Function1() { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectHeaderView$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                AudioEffectHeaderViewBinding audioEffectHeaderViewBinding2 = audioEffectHeaderViewBinding;
                switch (i5) {
                    case 0:
                        int i9 = AudioEffectHeaderView.$r8$clinit;
                        audioEffectHeaderViewBinding2.title.setText((String) obj);
                        break;
                    case 1:
                        Drawable drawable = (Drawable) obj;
                        int i10 = AudioEffectHeaderView.$r8$clinit;
                        audioEffectHeaderViewBinding2.icon.setImageDrawable(drawable);
                        audioEffectHeaderViewBinding2.icon.setVisibility(drawable != null ? 0 : 8);
                        break;
                    case 2:
                        int i11 = AudioEffectHeaderView.$r8$clinit;
                        audioEffectHeaderViewBinding2.root.setVisibility(((Boolean) obj).booleanValue() ? 0 : 8);
                        break;
                    default:
                        int i12 = AudioEffectHeaderView.$r8$clinit;
                        ((ViewGroup.MarginLayoutParams) audioEffectHeaderViewBinding2.root.getLayoutParams()).topMargin = ((Integer) obj).intValue();
                        break;
                }
                return Unit.INSTANCE;
            }
        }));
        ((AudioEffectHeaderViewModel) audioEffectHeaderView.viewModel$delegate.getValue()).icon.observe(lifecycleOwner7, new AudioEffectHeaderView$sam$androidx_lifecycle_Observer$0(new Function1() { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectHeaderView$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                AudioEffectHeaderViewBinding audioEffectHeaderViewBinding2 = audioEffectHeaderViewBinding;
                switch (i4) {
                    case 0:
                        int i9 = AudioEffectHeaderView.$r8$clinit;
                        audioEffectHeaderViewBinding2.title.setText((String) obj);
                        break;
                    case 1:
                        Drawable drawable = (Drawable) obj;
                        int i10 = AudioEffectHeaderView.$r8$clinit;
                        audioEffectHeaderViewBinding2.icon.setImageDrawable(drawable);
                        audioEffectHeaderViewBinding2.icon.setVisibility(drawable != null ? 0 : 8);
                        break;
                    case 2:
                        int i11 = AudioEffectHeaderView.$r8$clinit;
                        audioEffectHeaderViewBinding2.root.setVisibility(((Boolean) obj).booleanValue() ? 0 : 8);
                        break;
                    default:
                        int i12 = AudioEffectHeaderView.$r8$clinit;
                        ((ViewGroup.MarginLayoutParams) audioEffectHeaderViewBinding2.root.getLayoutParams()).topMargin = ((Integer) obj).intValue();
                        break;
                }
                return Unit.INSTANCE;
            }
        }));
        ((AudioEffectHeaderViewModel) audioEffectHeaderView.viewModel$delegate.getValue()).isVisible.observe(lifecycleOwner7, new AudioEffectHeaderView$sam$androidx_lifecycle_Observer$0(new Function1() { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectHeaderView$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                AudioEffectHeaderViewBinding audioEffectHeaderViewBinding2 = audioEffectHeaderViewBinding;
                switch (i3) {
                    case 0:
                        int i9 = AudioEffectHeaderView.$r8$clinit;
                        audioEffectHeaderViewBinding2.title.setText((String) obj);
                        break;
                    case 1:
                        Drawable drawable = (Drawable) obj;
                        int i10 = AudioEffectHeaderView.$r8$clinit;
                        audioEffectHeaderViewBinding2.icon.setImageDrawable(drawable);
                        audioEffectHeaderViewBinding2.icon.setVisibility(drawable != null ? 0 : 8);
                        break;
                    case 2:
                        int i11 = AudioEffectHeaderView.$r8$clinit;
                        audioEffectHeaderViewBinding2.root.setVisibility(((Boolean) obj).booleanValue() ? 0 : 8);
                        break;
                    default:
                        int i12 = AudioEffectHeaderView.$r8$clinit;
                        ((ViewGroup.MarginLayoutParams) audioEffectHeaderViewBinding2.root.getLayoutParams()).topMargin = ((Integer) obj).intValue();
                        break;
                }
                return Unit.INSTANCE;
            }
        }));
        ((AudioEffectHeaderViewModel) audioEffectHeaderView.viewModel$delegate.getValue()).marginTop.observe(lifecycleOwner7, new AudioEffectHeaderView$sam$androidx_lifecycle_Observer$0(new Function1() { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectHeaderView$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                AudioEffectHeaderViewBinding audioEffectHeaderViewBinding2 = audioEffectHeaderViewBinding;
                switch (i2) {
                    case 0:
                        int i9 = AudioEffectHeaderView.$r8$clinit;
                        audioEffectHeaderViewBinding2.title.setText((String) obj);
                        break;
                    case 1:
                        Drawable drawable = (Drawable) obj;
                        int i10 = AudioEffectHeaderView.$r8$clinit;
                        audioEffectHeaderViewBinding2.icon.setImageDrawable(drawable);
                        audioEffectHeaderViewBinding2.icon.setVisibility(drawable != null ? 0 : 8);
                        break;
                    case 2:
                        int i11 = AudioEffectHeaderView.$r8$clinit;
                        audioEffectHeaderViewBinding2.root.setVisibility(((Boolean) obj).booleanValue() ? 0 : 8);
                        break;
                    default:
                        int i12 = AudioEffectHeaderView.$r8$clinit;
                        ((ViewGroup.MarginLayoutParams) audioEffectHeaderViewBinding2.root.getLayoutParams()).topMargin = ((Integer) obj).intValue();
                        break;
                }
                return Unit.INSTANCE;
            }
        }));
        ((AudioEffectHeaderViewModel) audioEffectHeaderView.viewModel$delegate.getValue()).notifyChange();
        final RoutineTestViewBinding routineTestViewBinding = soundCraftViewBinding.routineTestView;
        if (routineTestViewBinding != null) {
            final RoutineTestView routineTestView = routineTestViewBinding.root;
            MutableLiveData mutableLiveData3 = routineTestView.getViewModel().routineCount;
            LifecycleOwner lifecycleOwner8 = ViewTreeLifecycleOwner.get(routineTestView);
            lifecycleOwner8.getClass();
            mutableLiveData3.observe(lifecycleOwner8, new RoutineTestView$sam$androidx_lifecycle_Observer$0(new Function1() { // from class: com.android.systemui.audio.soundcraft.view.routine.RoutineTestView$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj) {
                    int i9 = RoutineTestView.$r8$clinit;
                    routineTestViewBinding.routineCountText.setText(((Integer) obj).toString());
                    return Unit.INSTANCE;
                }
            }));
            routineTestViewBinding.startButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.audio.soundcraft.view.routine.RoutineTestView$bind$2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    RoutineTestView routineTestView2 = routineTestView;
                    int i9 = RoutineTestView.$r8$clinit;
                    final RoutineTestViewModel viewModel2 = routineTestView2.getViewModel();
                    Log.d("SoundCraft.RoutineTestViewModel", "onStartButtonClick : playingAppPackage=" + viewModel2.audioPlaybackManager.getPlayingAppPackage());
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.android.systemui.audio.soundcraft.viewmodel.common.routine.RoutineTestViewModel$onStartButtonClick$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            viewModel2.getClass();
                        }
                    }, 3000L);
                }
            });
            routineTestViewBinding.updateButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.audio.soundcraft.view.routine.RoutineTestView$bind$3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    RoutineTestView routineTestView2 = routineTestView;
                    int i9 = RoutineTestView.$r8$clinit;
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
                    RoutineTestView routineTestView2 = routineTestView;
                    int i9 = RoutineTestView.$r8$clinit;
                    routineTestView2.getViewModel().getClass();
                    Log.d("SoundCraft.RoutineTestViewModel", "onStopButtonClick");
                }
            });
            routineTestView.getViewModel().getClass();
        }
        this.binding = soundCraftViewBinding;
        SoundCraftViewModelExt soundCraftViewModelExt = SoundCraftViewModelExt.INSTANCE;
        SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
        SoundCraftViewComponent soundCraftViewComponent = SoundCraftLocalViewModelStoreOwner.current;
        if (soundCraftViewComponent == null) {
            throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
        }
        ((SoundCraftViewModel) SoundCraftDetailPageView$$ExternalSyntheticOutline0.m(soundCraftViewModelExt, soundCraftViewComponent, soundCraftViewComponent, SoundCraftViewModel.class)).notifyChange();
    }

    public final void onDestroy() {
        Object failure;
        SoundCraftDetailPageView soundCraftDetailPageView;
        ViewGroup viewGroup;
        ViewParent parent;
        Log.d("SoundCraft.SoundCraftViewComponent", "onDestroy");
        SoundCraftViewModelExt soundCraftViewModelExt = SoundCraftViewModelExt.INSTANCE;
        SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
        SoundCraftViewComponent soundCraftViewComponent = SoundCraftLocalViewModelStoreOwner.current;
        if (soundCraftViewComponent == null) {
            throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
        }
        ((EqualizerViewModel) SoundCraftDetailPageView$$ExternalSyntheticOutline0.m(soundCraftViewModelExt, soundCraftViewComponent, soundCraftViewComponent, EqualizerViewModel.class)).dismiss();
        SoundCraftViewComponent soundCraftViewComponent2 = SoundCraftLocalViewModelStoreOwner.current;
        if (soundCraftViewComponent2 == null) {
            throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
        }
        ((SpatialAudioViewModel) SoundCraftViewModelExt.get(soundCraftViewComponent2, SpatialAudioViewModel.class, soundCraftViewComponent2.getDefaultViewModelProviderFactory())).dismiss();
        SoundCraftViewComponent soundCraftViewComponent3 = SoundCraftLocalViewModelStoreOwner.current;
        if (soundCraftViewComponent3 == null) {
            throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
        }
        ((DolbyViewModel) SoundCraftViewModelExt.get(soundCraftViewComponent3, DolbyViewModel.class, soundCraftViewComponent3.getDefaultViewModelProviderFactory())).dismiss();
        this.viewModelStore.clear();
        try {
            int i = Result.$r8$clinit;
            this.registry.setCurrentState(Lifecycle.State.DESTROYED);
            failure = Unit.INSTANCE;
        } catch (Throwable th) {
            int i2 = Result.$r8$clinit;
            failure = new Result.Failure(th);
        }
        Throwable thM3442exceptionOrNullimpl = Result.m3442exceptionOrNullimpl(failure);
        if (thM3442exceptionOrNullimpl != null) {
            thM3442exceptionOrNullimpl.printStackTrace();
        }
        this.registry.removeObserver(this.lifecycleObserver);
        SoundCraftViewBinding soundCraftViewBinding = this.binding;
        if (soundCraftViewBinding != null && (viewGroup = soundCraftViewBinding.root) != null && (parent = viewGroup.getParent()) != null) {
            ViewGroup viewGroup2 = (ViewGroup) parent;
            SoundCraftViewBinding soundCraftViewBinding2 = this.binding;
            viewGroup2.removeView(soundCraftViewBinding2 != null ? soundCraftViewBinding2.root : null);
        }
        SoundCraftViewBinding soundCraftViewBinding3 = this.binding;
        if (soundCraftViewBinding3 != null && (soundCraftDetailPageView = soundCraftViewBinding3.detailPageContent) != null) {
            soundCraftDetailPageView.setTag(R.id.view_tree_lifecycle_owner, null);
        }
        this.binding = null;
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x02ac  */
    /* JADX WARN: Removed duplicated region for block: B:101:0x02b0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateModel(int i) {
        Object failure;
        EffectModel appRoutineModel;
        List list;
        Cursor cursorQuery;
        SoundCraftDetailPageView soundCraftDetailPageView;
        int i2 = 2;
        int i3 = 0;
        int i4 = 1;
        ListPopupWindow$$ExternalSyntheticOutline0.m(i, "updateModel from=", "SoundCraft.SoundCraftViewComponent");
        SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
        SoundCraftLocalViewModelStoreOwner.current = this;
        boolean z = i == 1;
        ModelProvider modelProvider = this.modelProvider;
        modelProvider.isFromNowBar = z;
        modelProvider.isFromCover = i == 2;
        SoundCraftViewModelExt soundCraftViewModelExt = SoundCraftViewModelExt.INSTANCE;
        SoundCraftViewComponent soundCraftViewComponent = SoundCraftLocalViewModelStoreOwner.current;
        if (soundCraftViewComponent == null) {
            throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
        }
        SoundCraftViewModel soundCraftViewModel = (SoundCraftViewModel) SoundCraftDetailPageView$$ExternalSyntheticOutline0.m(soundCraftViewModelExt, soundCraftViewComponent, soundCraftViewComponent, SoundCraftViewModel.class);
        VolumeModel volumeModel = soundCraftViewModel.volumeManager.getVolumeModel();
        ModelProvider modelProvider2 = soundCraftViewModel.modelProvider;
        modelProvider2.volumeModel = volumeModel;
        soundCraftViewModel.updateVolumeBar.setValue(Boolean.TRUE);
        SoundCraftSettings soundCraftSettings = soundCraftViewModel.settings;
        soundCraftSettings.update();
        RoutineManager routineManager = soundCraftViewModel.routineManager;
        AutomationService service = routineManager.getService();
        Context context = routineManager.context;
        AutomationService.SystemRoutineType currentSystemRoutineType = routineManager.getCurrentSystemRoutineType();
        RoutineManager$changeObserver$1 routineManager$changeObserver$1 = routineManager.changeObserver;
        AutomationServiceImpl automationServiceImpl = (AutomationServiceImpl) service;
        automationServiceImpl.getClass();
        if (AutomationServiceImpl.Companion.access$isValidRequest(AutomationServiceImpl.Companion, context, currentSystemRoutineType)) {
            try {
                ((ContentHandlerImpl) automationServiceImpl.contentHandler).register(context, currentSystemRoutineType.getValue(), routineManager$changeObserver$1);
            } catch (Exception e) {
                com.samsung.android.sdk.routines.automationservice.internal.Log log = com.samsung.android.sdk.routines.automationservice.internal.Log.INSTANCE;
                String str = "registerObserver: " + e.getMessage();
                log.getClass();
                com.samsung.android.sdk.routines.automationservice.internal.Log.e("AutomationServiceImpl@SDK", str);
            }
        }
        Log.d("SoundCraft.SoundCraftViewModel", "SoundCraftViewModel.updateModel : settings=" + soundCraftSettings);
        boolean z2 = soundCraftSettings.isBudsActive;
        modelProvider2.effectOutDeviceType = z2 ? EffectOutDeviceType.BUDS : EffectOutDeviceType.PHONE;
        EmergencyButtonController$$ExternalSyntheticOutline0.m("isShowBudsSetting=", "SoundCraft.SoundCraftViewModel", z2);
        if (z2) {
            Log.d("SoundCraft.SoundCraftViewModel", "updateBudsModels");
            BluetoothDeviceManager bluetoothDeviceManager = soundCraftViewModel.bluetoothDeviceManager;
            BluetoothDevice activeDevice = bluetoothDeviceManager.getActiveDevice();
            if (activeDevice != null && BluetoothDeviceManager.isSupportedSoundCraft(activeDevice)) {
                SoundCraftSettingConstants soundCraftSettingConstants = SoundCraftSettingConstants.INSTANCE;
                Context context2 = soundCraftViewModel.context;
                soundCraftSettingConstants.getClass();
                String string = Settings.System.getString(context2.getContentResolver(), "buds_plugin_package_name");
                if (string == null) {
                    string = "";
                }
                if (string.length() == 0) {
                    soundCraftViewModel.soundCraftManager.requestGWPluginModel(activeDevice);
                }
            }
            SoundCraftViewModel$$ExternalSyntheticLambda0 soundCraftViewModel$$ExternalSyntheticLambda0 = new SoundCraftViewModel$$ExternalSyntheticLambda0(soundCraftViewModel, i2);
            WearableManager wearableManager = soundCraftViewModel.wearableManager;
            wearableManager.getClass();
            if (!new GetInfoRequester(wearableManager.context, wearableManager.settings.budsPluginPackageName, soundCraftViewModel$$ExternalSyntheticLambda0).bindService()) {
                Log.d("SoundCraft.SoundCraftViewModel", "connection failed");
                modelProvider2.budsModel.setConnectionState();
                modelProvider2.appSettingModel.readyToUpdateRoutine = false;
                SoundCraftSettingConstants soundCraftSettingConstants2 = SoundCraftSettingConstants.INSTANCE;
                Context context3 = soundCraftViewModel.context;
                soundCraftSettingConstants2.getClass();
                SoundCraftSettingConstants.isBudsPluginConnected(context3, false);
                soundCraftViewModel.notifyChange();
            }
            Log.d("SoundCraft.BluetoothDeviceManager", "init()");
            BluetoothDevice activeDevice2 = bluetoothDeviceManager.getActiveDevice();
            SoundCraftViewModel$$ExternalSyntheticLambda0 soundCraftViewModel$$ExternalSyntheticLambda02 = bluetoothDeviceManager.noiseControlCallback;
            if (soundCraftViewModel$$ExternalSyntheticLambda02 != null) {
                BluetoothDevice activeDevice3 = bluetoothDeviceManager.getActiveDevice();
                soundCraftViewModel$$ExternalSyntheticLambda02.mo781invoke(activeDevice3 != null ? bluetoothDeviceManager.getNoiseControlList(activeDevice3) : new LinkedHashSet());
            }
            SoundCraftViewModel$$ExternalSyntheticLambda0 soundCraftViewModel$$ExternalSyntheticLambda03 = bluetoothDeviceManager.batteryInfoCallback;
            if (soundCraftViewModel$$ExternalSyntheticLambda03 != null) {
                soundCraftViewModel$$ExternalSyntheticLambda03.mo781invoke(BluetoothDeviceManager.getBatteryInfo(activeDevice2));
            }
            if (activeDevice2 == null) {
                Log.e("SoundCraft.BluetoothDeviceManager", "connected device is empty");
            }
            bluetoothDeviceManager.isRegister = true;
            bluetoothDeviceManager.context.registerReceiver(bluetoothDeviceManager.bluetoothMetadataBroadcastReceiver, new IntentFilter("com.samsung.bluetooth.device.action.META_DATA_CHANGED"));
            SoundCraftViewModel$$ExternalSyntheticLambda0 soundCraftViewModel$$ExternalSyntheticLambda04 = new SoundCraftViewModel$$ExternalSyntheticLambda0(soundCraftViewModel, i3);
            bluetoothDeviceManager.noiseControlCallback = soundCraftViewModel$$ExternalSyntheticLambda04;
            BluetoothDevice activeDevice4 = bluetoothDeviceManager.getActiveDevice();
            Set noiseControlList = activeDevice4 != null ? bluetoothDeviceManager.getNoiseControlList(activeDevice4) : new LinkedHashSet();
            bluetoothDeviceManager.currentNoiseControlList = noiseControlList;
            soundCraftViewModel$$ExternalSyntheticLambda04.mo781invoke(noiseControlList);
            SoundCraftViewModel$$ExternalSyntheticLambda0 soundCraftViewModel$$ExternalSyntheticLambda05 = new SoundCraftViewModel$$ExternalSyntheticLambda0(soundCraftViewModel, i4);
            bluetoothDeviceManager.batteryInfoCallback = soundCraftViewModel$$ExternalSyntheticLambda05;
            soundCraftViewModel$$ExternalSyntheticLambda05.mo781invoke(BluetoothDeviceManager.getBatteryInfo(bluetoothDeviceManager.getActiveDevice()));
        } else {
            Log.d("SoundCraft.SoundCraftViewModel", "updatePhoneEffectModel");
            SoundAliveManager soundAliveManager = soundCraftViewModel.soundAliveManager;
            soundAliveManager.getClass();
            String str2 = SoundAliveManager.TAG;
            PhoneEffectModel phoneEffectModel = new PhoneEffectModel(0, 0, false, false, false, false, 63, null);
            try {
                int i5 = Result.$r8$clinit;
                list = SoundAliveEffectEnum.$ENTRIES;
                ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
                Iterator it = ((AbstractList) list).iterator();
                while (it.hasNext()) {
                    arrayList.add(((SoundAliveEffectEnum) it.next()).getSettingName());
                }
                cursorQuery = soundAliveManager.context.getContentResolver().query(soundAliveManager.uri, (String[]) arrayList.toArray(new String[0]), null, null, null);
            } catch (Throwable th) {
                int i6 = Result.$r8$clinit;
                failure = new Result.Failure(th);
            }
            if (cursorQuery == null) {
                Log.d(str2, "query was failed.. soundAliveEffectModel=" + phoneEffectModel);
                modelProvider2.phoneModel = phoneEffectModel;
                appRoutineModel = soundCraftViewModel.getAppRoutineModel();
                if (appRoutineModel == null) {
                    modelProvider2.effectModel = appRoutineModel;
                } else {
                    List list2 = DolbyEnum.$ENTRIES;
                    ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
                    Iterator it2 = ((AbstractList) list2).iterator();
                    while (it2.hasNext()) {
                        DolbyEnum dolbyEnum = (DolbyEnum) it2.next();
                        arrayList2.add(new Dolby(soundCraftViewModel.context.getString(dolbyEnum.getNameResId()), dolbyEnum.getRealIndex() == phoneEffectModel.dolbyIndex));
                    }
                    EffectModel effectModel = new EffectModel(arrayList2, null, null, null, null, Boolean.valueOf(phoneEffectModel.voiceBoost), Boolean.valueOf(phoneEffectModel.volumeNormalization), 30, null);
                    List list3 = SoundAliveEqEnum.$ENTRIES;
                    ArrayList arrayList3 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list3, 10));
                    Iterator it3 = ((AbstractList) list3).iterator();
                    while (it3.hasNext()) {
                        SoundAliveEqEnum soundAliveEqEnum = (SoundAliveEqEnum) it3.next();
                        arrayList3.add(new Equalizer(soundCraftViewModel.context.getString(soundAliveEqEnum.getNameResId()), soundAliveEqEnum.getRealIndex() == phoneEffectModel.eqIndex));
                    }
                    effectModel.equalizerList = arrayList3;
                    modelProvider2.effectModel = effectModel;
                }
                soundCraftViewModel.notifyChange();
            } else {
                int i7 = 0;
                while (cursorQuery.moveToNext()) {
                    int i8 = Integer.parseInt((String) CollectionsKt___CollectionsKt.first(StringsKt__StringsKt.split$default(cursorQuery.getString(0), new String[]{","}, 0, 6)));
                    switch (SoundAliveManager.WhenMappings.$EnumSwitchMapping$0[((SoundAliveEffectEnum) list.get(i7)).ordinal()]) {
                        case 1:
                            phoneEffectModel.dolbyIndex = i8;
                            break;
                        case 2:
                            phoneEffectModel.eqIndex = i8;
                            break;
                        case 3:
                            phoneEffectModel.voiceBoost = i8 == 1;
                            break;
                        case 4:
                            phoneEffectModel.volumeNormalization = i8 == 1;
                            break;
                        case 5:
                            phoneEffectModel.uhqUpscaler = i8 == 1;
                            break;
                        case 6:
                            phoneEffectModel.spatialAudio = i8 == 1;
                            break;
                        default:
                            throw new NoWhenBranchMatchedException();
                    }
                    i7++;
                }
                cursorQuery.close();
                failure = Unit.INSTANCE;
                Throwable thM3442exceptionOrNullimpl = Result.m3442exceptionOrNullimpl(failure);
                if (thM3442exceptionOrNullimpl != null) {
                    Log.e(str2, "Error, SoundAlive query : e=" + thM3442exceptionOrNullimpl);
                }
                Log.d(str2, "soundAliveEffectModel=" + phoneEffectModel);
                modelProvider2.phoneModel = phoneEffectModel;
                appRoutineModel = soundCraftViewModel.getAppRoutineModel();
                if (appRoutineModel == null) {
                }
                soundCraftViewModel.notifyChange();
            }
        }
        Log.d("SoundCraft.SoundCraftViewModel", "effectModel=" + modelProvider2.effectModel + ", [phoneModel=" + modelProvider2.phoneModel + "], [budsModel=" + modelProvider2.budsModel + "]");
        soundCraftViewModel.notifyChange();
        SoundCraftViewBinding soundCraftViewBinding = this.binding;
        if (soundCraftViewBinding == null || (soundCraftDetailPageView = soundCraftViewBinding.detailPageContent) == null) {
            return;
        }
        soundCraftDetailPageView.requestLayout();
    }
}
