package com.android.systemui.audio.soundcraft.view.volume;

import android.content.Context;
import android.os.Trace;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.lifecycle.ViewModelStoreOwner;
import com.android.keyguard.KeyguardSecPatternView$$ExternalSyntheticOutline0;
import com.android.systemui.audio.soundcraft.di.vm.component.SoundCraftLocalViewModelStoreOwner;
import com.android.systemui.audio.soundcraft.di.vm.component.SoundCraftVMComponent;
import com.android.systemui.audio.soundcraft.di.vm.component.SoundCraftViewModelExt;
import com.android.systemui.audio.soundcraft.interfaces.volume.VolumeManager;
import com.android.systemui.audio.soundcraft.view.SoundCraftDetailPageView$bind$2$$ExternalSyntheticOutline0;
import com.android.systemui.audio.soundcraft.view.SoundCraftViewComponent;
import com.android.systemui.audio.soundcraft.viewbinding.volume.VolumeBarViewBinding;
import com.android.systemui.audio.soundcraft.viewmodel.common.volume.VolumeBarViewModel;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class VolumeBarView extends LinearLayout implements SoundCraftVMComponent {
    public static final /* synthetic */ int $r8$clinit = 0;
    public SoundCraftVolumeMotion soundCraftVolumeMotion;
    public SpringAnimation touchDownAnimation;
    public SpringAnimation touchUpAnimation;
    public VolumeBarViewBinding viewBinding;
    public final Lazy viewModel$delegate;
    public final VolumeBarView$volumeKeyHandler$1 volumeKeyHandler;

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

    public VolumeBarView(Context context) {
        super(context);
        SoundCraftViewModelExt soundCraftViewModelExt = SoundCraftViewModelExt.INSTANCE;
        this.viewModel$delegate = LazyKt__LazyJVMKt.lazy(LazyThreadSafetyMode.SYNCHRONIZED, new Function0() { // from class: com.android.systemui.audio.soundcraft.view.volume.VolumeBarView$special$$inlined$lazyViewModel$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                SoundCraftViewModelExt soundCraftViewModelExt2 = SoundCraftViewModelExt.INSTANCE;
                SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
                ViewModelStoreOwner viewModelStoreOwner = SoundCraftLocalViewModelStoreOwner.current;
                if (viewModelStoreOwner == null) {
                    throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner".toString());
                }
                SoundCraftViewComponent soundCraftViewComponent = (SoundCraftViewComponent) viewModelStoreOwner;
                return SoundCraftDetailPageView$bind$2$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, VolumeBarViewModel.class);
            }
        });
        this.volumeKeyHandler = new View.OnUnhandledKeyEventListener() { // from class: com.android.systemui.audio.soundcraft.view.volume.VolumeBarView$volumeKeyHandler$1
            @Override // android.view.View.OnUnhandledKeyEventListener
            public final boolean onUnhandledKeyEvent(View view, KeyEvent keyEvent) {
                int modeInternal;
                VolumeBarView volumeBarView = VolumeBarView.this;
                int i = VolumeBarView.$r8$clinit;
                VolumeBarViewModel viewModel$2 = volumeBarView.getViewModel$2();
                int action = keyEvent.getAction();
                int keyCode = keyEvent.getKeyCode();
                viewModel$2.getClass();
                if (action != 0) {
                    return false;
                }
                KeyguardSecPatternView$$ExternalSyntheticOutline0.m(keyCode, "onUnhandledKeyEvent() - keyCode = ", "SoundCraft.VolumeBarViewModel");
                Integer num = keyCode != 24 ? keyCode != 25 ? null : -1 : 1;
                if (num == null) {
                    return false;
                }
                VolumeManager volumeManager = viewModel$2.volumeManager;
                volumeManager.getClass();
                if (Trace.isTagEnabled(4096L)) {
                    Trace.traceBegin(4096L, "#soundCraft.VolumeManager_getModeInternal");
                    try {
                        modeInternal = volumeManager.audioManager.getModeInternal();
                    } finally {
                    }
                } else {
                    modeInternal = volumeManager.audioManager.getModeInternal();
                }
                Integer num2 = modeInternal == 0 ? num : null;
                if (num2 == null) {
                    return false;
                }
                int intValue = num2.intValue();
                if (Trace.isTagEnabled(4096L)) {
                    Trace.traceBegin(4096L, "#soundCraft.VolumeManager_adjustVolume");
                    try {
                        volumeManager.audioManager.adjustVolume(intValue, 0);
                        Unit unit = Unit.INSTANCE;
                    } finally {
                    }
                } else {
                    volumeManager.audioManager.adjustVolume(intValue, 0);
                }
                return true;
            }
        };
    }

    public final VolumeBarViewModel getViewModel$2() {
        return (VolumeBarViewModel) this.viewModel$delegate.getValue();
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.d("SoundCraft.VolumeBarView", "addOnUnhandledKeyEventListener");
        getViewModel$2().forceVolumeControlStream(3);
        addOnUnhandledKeyEventListener(this.volumeKeyHandler);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.d("SoundCraft.VolumeBarView", "removeOnUnhandledKeyEventListener");
        getViewModel$2().forceVolumeControlStream(-1);
        removeOnUnhandledKeyEventListener(this.volumeKeyHandler);
    }

    public VolumeBarView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        SoundCraftViewModelExt soundCraftViewModelExt = SoundCraftViewModelExt.INSTANCE;
        this.viewModel$delegate = LazyKt__LazyJVMKt.lazy(LazyThreadSafetyMode.SYNCHRONIZED, new Function0() { // from class: com.android.systemui.audio.soundcraft.view.volume.VolumeBarView$special$$inlined$lazyViewModel$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                SoundCraftViewModelExt soundCraftViewModelExt2 = SoundCraftViewModelExt.INSTANCE;
                SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
                ViewModelStoreOwner viewModelStoreOwner = SoundCraftLocalViewModelStoreOwner.current;
                if (viewModelStoreOwner == null) {
                    throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner".toString());
                }
                SoundCraftViewComponent soundCraftViewComponent = (SoundCraftViewComponent) viewModelStoreOwner;
                return SoundCraftDetailPageView$bind$2$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, VolumeBarViewModel.class);
            }
        });
        this.volumeKeyHandler = new View.OnUnhandledKeyEventListener() { // from class: com.android.systemui.audio.soundcraft.view.volume.VolumeBarView$volumeKeyHandler$1
            @Override // android.view.View.OnUnhandledKeyEventListener
            public final boolean onUnhandledKeyEvent(View view, KeyEvent keyEvent) {
                int modeInternal;
                VolumeBarView volumeBarView = VolumeBarView.this;
                int i = VolumeBarView.$r8$clinit;
                VolumeBarViewModel viewModel$2 = volumeBarView.getViewModel$2();
                int action = keyEvent.getAction();
                int keyCode = keyEvent.getKeyCode();
                viewModel$2.getClass();
                if (action != 0) {
                    return false;
                }
                KeyguardSecPatternView$$ExternalSyntheticOutline0.m(keyCode, "onUnhandledKeyEvent() - keyCode = ", "SoundCraft.VolumeBarViewModel");
                Integer num = keyCode != 24 ? keyCode != 25 ? null : -1 : 1;
                if (num == null) {
                    return false;
                }
                VolumeManager volumeManager = viewModel$2.volumeManager;
                volumeManager.getClass();
                if (Trace.isTagEnabled(4096L)) {
                    Trace.traceBegin(4096L, "#soundCraft.VolumeManager_getModeInternal");
                    try {
                        modeInternal = volumeManager.audioManager.getModeInternal();
                    } finally {
                    }
                } else {
                    modeInternal = volumeManager.audioManager.getModeInternal();
                }
                Integer num2 = modeInternal == 0 ? num : null;
                if (num2 == null) {
                    return false;
                }
                int intValue = num2.intValue();
                if (Trace.isTagEnabled(4096L)) {
                    Trace.traceBegin(4096L, "#soundCraft.VolumeManager_adjustVolume");
                    try {
                        volumeManager.audioManager.adjustVolume(intValue, 0);
                        Unit unit = Unit.INSTANCE;
                    } finally {
                    }
                } else {
                    volumeManager.audioManager.adjustVolume(intValue, 0);
                }
                return true;
            }
        };
    }

    public VolumeBarView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        SoundCraftViewModelExt soundCraftViewModelExt = SoundCraftViewModelExt.INSTANCE;
        this.viewModel$delegate = LazyKt__LazyJVMKt.lazy(LazyThreadSafetyMode.SYNCHRONIZED, new Function0() { // from class: com.android.systemui.audio.soundcraft.view.volume.VolumeBarView$special$$inlined$lazyViewModel$3
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                SoundCraftViewModelExt soundCraftViewModelExt2 = SoundCraftViewModelExt.INSTANCE;
                SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
                ViewModelStoreOwner viewModelStoreOwner = SoundCraftLocalViewModelStoreOwner.current;
                if (viewModelStoreOwner == null) {
                    throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner".toString());
                }
                SoundCraftViewComponent soundCraftViewComponent = (SoundCraftViewComponent) viewModelStoreOwner;
                return SoundCraftDetailPageView$bind$2$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, VolumeBarViewModel.class);
            }
        });
        this.volumeKeyHandler = new View.OnUnhandledKeyEventListener() { // from class: com.android.systemui.audio.soundcraft.view.volume.VolumeBarView$volumeKeyHandler$1
            @Override // android.view.View.OnUnhandledKeyEventListener
            public final boolean onUnhandledKeyEvent(View view, KeyEvent keyEvent) {
                int modeInternal;
                VolumeBarView volumeBarView = VolumeBarView.this;
                int i2 = VolumeBarView.$r8$clinit;
                VolumeBarViewModel viewModel$2 = volumeBarView.getViewModel$2();
                int action = keyEvent.getAction();
                int keyCode = keyEvent.getKeyCode();
                viewModel$2.getClass();
                if (action != 0) {
                    return false;
                }
                KeyguardSecPatternView$$ExternalSyntheticOutline0.m(keyCode, "onUnhandledKeyEvent() - keyCode = ", "SoundCraft.VolumeBarViewModel");
                Integer num = keyCode != 24 ? keyCode != 25 ? null : -1 : 1;
                if (num == null) {
                    return false;
                }
                VolumeManager volumeManager = viewModel$2.volumeManager;
                volumeManager.getClass();
                if (Trace.isTagEnabled(4096L)) {
                    Trace.traceBegin(4096L, "#soundCraft.VolumeManager_getModeInternal");
                    try {
                        modeInternal = volumeManager.audioManager.getModeInternal();
                    } finally {
                    }
                } else {
                    modeInternal = volumeManager.audioManager.getModeInternal();
                }
                Integer num2 = modeInternal == 0 ? num : null;
                if (num2 == null) {
                    return false;
                }
                int intValue = num2.intValue();
                if (Trace.isTagEnabled(4096L)) {
                    Trace.traceBegin(4096L, "#soundCraft.VolumeManager_adjustVolume");
                    try {
                        volumeManager.audioManager.adjustVolume(intValue, 0);
                        Unit unit = Unit.INSTANCE;
                    } finally {
                    }
                } else {
                    volumeManager.audioManager.adjustVolume(intValue, 0);
                }
                return true;
            }
        };
    }
}
