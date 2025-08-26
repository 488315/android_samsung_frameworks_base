package com.android.systemui.audio.soundcraft.view.volume;

import android.content.Context;
import android.os.Trace;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import androidx.dynamicanimation.animation.SpringAnimation;
import com.android.keyguard.ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0;
import com.android.systemui.audio.soundcraft.di.vm.component.SoundCraftLocalViewModelStoreOwner;
import com.android.systemui.audio.soundcraft.di.vm.component.SoundCraftVMComponent;
import com.android.systemui.audio.soundcraft.di.vm.component.SoundCraftViewModelExt;
import com.android.systemui.audio.soundcraft.interfaces.volume.VolumeManager;
import com.android.systemui.audio.soundcraft.view.SoundCraftDetailPageView$$ExternalSyntheticOutline0;
import com.android.systemui.audio.soundcraft.view.SoundCraftViewComponent;
import com.android.systemui.audio.soundcraft.viewbinding.volume.VolumeBarViewBinding;
import com.android.systemui.audio.soundcraft.viewmodel.common.volume.VolumeBarViewModel;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class VolumeBarView extends LinearLayout implements SoundCraftVMComponent {
    public static final /* synthetic */ int $r8$clinit = 0;
    public SoundCraftVolumeMotion soundCraftVolumeMotion;
    public SpringAnimation touchDownAnimation;
    public SpringAnimation touchUpAnimation;
    public VolumeBarViewBinding viewBinding;
    public final Lazy viewModel$delegate;
    public final VolumeBarView$volumeKeyHandler$1 volumeKeyHandler;

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

    /* JADX WARN: Type inference failed for: r2v4, types: [com.android.systemui.audio.soundcraft.view.volume.VolumeBarView$volumeKeyHandler$1] */
    public VolumeBarView(Context context) {
        super(context);
        SoundCraftViewModelExt soundCraftViewModelExt = SoundCraftViewModelExt.INSTANCE;
        this.viewModel$delegate = LazyKt__LazyJVMKt.lazy(LazyThreadSafetyMode.SYNCHRONIZED, new Function0(this) { // from class: com.android.systemui.audio.soundcraft.view.volume.VolumeBarView$special$$inlined$lazyViewModel$1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                SoundCraftViewModelExt soundCraftViewModelExt2 = SoundCraftViewModelExt.INSTANCE;
                SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
                SoundCraftViewComponent soundCraftViewComponent = SoundCraftLocalViewModelStoreOwner.current;
                if (soundCraftViewComponent != null) {
                    return SoundCraftDetailPageView$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, VolumeBarViewModel.class);
                }
                throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
            }
        });
        this.volumeKeyHandler = new View.OnUnhandledKeyEventListener() { // from class: com.android.systemui.audio.soundcraft.view.volume.VolumeBarView$volumeKeyHandler$1
            @Override // android.view.View.OnUnhandledKeyEventListener
            public final boolean onUnhandledKeyEvent(View view, KeyEvent keyEvent) {
                int modeInternal;
                VolumeBarView volumeBarView = this.this$0;
                int i = VolumeBarView.$r8$clinit;
                VolumeBarViewModel volumeBarViewModel = (VolumeBarViewModel) volumeBarView.viewModel$delegate.getValue();
                int action = keyEvent.getAction();
                int keyCode = keyEvent.getKeyCode();
                volumeBarViewModel.getClass();
                if (action == 0) {
                    ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(keyCode, "onUnhandledKeyEvent() - keyCode = ", "SoundCraft.VolumeBarViewModel");
                    Integer num = keyCode != 24 ? keyCode != 25 ? null : -1 : 1;
                    if (num != null) {
                        VolumeManager volumeManager = volumeBarViewModel.volumeManager;
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
                        if (num2 != null) {
                            int iIntValue = num2.intValue();
                            if (!Trace.isTagEnabled(4096L)) {
                                volumeManager.audioManager.adjustVolume(iIntValue, 0);
                                return true;
                            }
                            Trace.traceBegin(4096L, "#soundCraft.VolumeManager_adjustVolume");
                            try {
                                volumeManager.audioManager.adjustVolume(iIntValue, 0);
                                Unit unit = Unit.INSTANCE;
                                return true;
                            } finally {
                            }
                        }
                    }
                }
                return false;
            }
        };
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.d("SoundCraft.VolumeBarView", "addOnUnhandledKeyEventListener");
        ((VolumeBarViewModel) this.viewModel$delegate.getValue()).forceVolumeControlStream(3);
        addOnUnhandledKeyEventListener(this.volumeKeyHandler);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.d("SoundCraft.VolumeBarView", "removeOnUnhandledKeyEventListener");
        ((VolumeBarViewModel) this.viewModel$delegate.getValue()).forceVolumeControlStream(-1);
        removeOnUnhandledKeyEventListener(this.volumeKeyHandler);
    }

    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.systemui.audio.soundcraft.view.volume.VolumeBarView$volumeKeyHandler$1] */
    public VolumeBarView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        SoundCraftViewModelExt soundCraftViewModelExt = SoundCraftViewModelExt.INSTANCE;
        this.viewModel$delegate = LazyKt__LazyJVMKt.lazy(LazyThreadSafetyMode.SYNCHRONIZED, new Function0(this) { // from class: com.android.systemui.audio.soundcraft.view.volume.VolumeBarView$special$$inlined$lazyViewModel$2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                SoundCraftViewModelExt soundCraftViewModelExt2 = SoundCraftViewModelExt.INSTANCE;
                SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
                SoundCraftViewComponent soundCraftViewComponent = SoundCraftLocalViewModelStoreOwner.current;
                if (soundCraftViewComponent != null) {
                    return SoundCraftDetailPageView$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, VolumeBarViewModel.class);
                }
                throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
            }
        });
        this.volumeKeyHandler = new View.OnUnhandledKeyEventListener() { // from class: com.android.systemui.audio.soundcraft.view.volume.VolumeBarView$volumeKeyHandler$1
            @Override // android.view.View.OnUnhandledKeyEventListener
            public final boolean onUnhandledKeyEvent(View view, KeyEvent keyEvent) {
                int modeInternal;
                VolumeBarView volumeBarView = this.this$0;
                int i = VolumeBarView.$r8$clinit;
                VolumeBarViewModel volumeBarViewModel = (VolumeBarViewModel) volumeBarView.viewModel$delegate.getValue();
                int action = keyEvent.getAction();
                int keyCode = keyEvent.getKeyCode();
                volumeBarViewModel.getClass();
                if (action == 0) {
                    ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(keyCode, "onUnhandledKeyEvent() - keyCode = ", "SoundCraft.VolumeBarViewModel");
                    Integer num = keyCode != 24 ? keyCode != 25 ? null : -1 : 1;
                    if (num != null) {
                        VolumeManager volumeManager = volumeBarViewModel.volumeManager;
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
                        if (num2 != null) {
                            int iIntValue = num2.intValue();
                            if (!Trace.isTagEnabled(4096L)) {
                                volumeManager.audioManager.adjustVolume(iIntValue, 0);
                                return true;
                            }
                            Trace.traceBegin(4096L, "#soundCraft.VolumeManager_adjustVolume");
                            try {
                                volumeManager.audioManager.adjustVolume(iIntValue, 0);
                                Unit unit = Unit.INSTANCE;
                                return true;
                            } finally {
                            }
                        }
                    }
                }
                return false;
            }
        };
    }

    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.systemui.audio.soundcraft.view.volume.VolumeBarView$volumeKeyHandler$1] */
    public VolumeBarView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        SoundCraftViewModelExt soundCraftViewModelExt = SoundCraftViewModelExt.INSTANCE;
        this.viewModel$delegate = LazyKt__LazyJVMKt.lazy(LazyThreadSafetyMode.SYNCHRONIZED, new Function0(this) { // from class: com.android.systemui.audio.soundcraft.view.volume.VolumeBarView$special$$inlined$lazyViewModel$3
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                SoundCraftViewModelExt soundCraftViewModelExt2 = SoundCraftViewModelExt.INSTANCE;
                SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
                SoundCraftViewComponent soundCraftViewComponent = SoundCraftLocalViewModelStoreOwner.current;
                if (soundCraftViewComponent != null) {
                    return SoundCraftDetailPageView$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, VolumeBarViewModel.class);
                }
                throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
            }
        });
        this.volumeKeyHandler = new View.OnUnhandledKeyEventListener() { // from class: com.android.systemui.audio.soundcraft.view.volume.VolumeBarView$volumeKeyHandler$1
            @Override // android.view.View.OnUnhandledKeyEventListener
            public final boolean onUnhandledKeyEvent(View view, KeyEvent keyEvent) {
                int modeInternal;
                VolumeBarView volumeBarView = this.this$0;
                int i2 = VolumeBarView.$r8$clinit;
                VolumeBarViewModel volumeBarViewModel = (VolumeBarViewModel) volumeBarView.viewModel$delegate.getValue();
                int action = keyEvent.getAction();
                int keyCode = keyEvent.getKeyCode();
                volumeBarViewModel.getClass();
                if (action == 0) {
                    ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(keyCode, "onUnhandledKeyEvent() - keyCode = ", "SoundCraft.VolumeBarViewModel");
                    Integer num = keyCode != 24 ? keyCode != 25 ? null : -1 : 1;
                    if (num != null) {
                        VolumeManager volumeManager = volumeBarViewModel.volumeManager;
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
                        if (num2 != null) {
                            int iIntValue = num2.intValue();
                            if (!Trace.isTagEnabled(4096L)) {
                                volumeManager.audioManager.adjustVolume(iIntValue, 0);
                                return true;
                            }
                            Trace.traceBegin(4096L, "#soundCraft.VolumeManager_adjustVolume");
                            try {
                                volumeManager.audioManager.adjustVolume(iIntValue, 0);
                                Unit unit = Unit.INSTANCE;
                                return true;
                            } finally {
                            }
                        }
                    }
                }
                return false;
            }
        };
    }
}
