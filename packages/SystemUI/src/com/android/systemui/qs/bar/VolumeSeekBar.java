package com.android.systemui.qs.bar;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Message;
import android.os.Trace;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Toast;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.audio.soundcraft.interfaces.connectivity.BluetoothDeviceManager;
import com.android.systemui.audio.soundcraft.interfaces.volume.VolumeManager;
import com.android.systemui.audio.soundcraft.model.common.VolumeModel;
import com.android.systemui.plugins.VolumeDialogController;
import com.android.systemui.plugins.qs.DetailAdapter;
import com.android.systemui.qs.SecQSDetailController;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.shade.domain.interactor.SecQSExpansionStateChangeEvent;
import com.android.systemui.shade.domain.interactor.SecQSExpansionStateInteractor;
import com.android.systemui.shade.domain.interactor.SecQSExpansionStateListener;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.volume.VolumeDependency;
import com.android.systemui.volume.util.AudioManagerWrapper;
import com.android.systemui.volume.view.icon.QPVolumeIcon;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import dagger.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class VolumeSeekBar implements SecQSExpansionStateListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AudioManagerWrapper audioManagerWrapper;
    public final BluetoothDeviceManager bluetoothDeviceManager;
    public final Context context;
    public int device;
    public final PointF downPoint;
    public boolean iconAnimationType;
    public boolean isEarShockWarned;
    public boolean isLongPressed;
    public boolean isShowing;
    public boolean isSmartViewEnabled;
    public boolean isZenModeDisabled;
    public final VolumeSeekBar$onUnhandledKeyEventListener$1 onUnhandledKeyEventListener;
    public int progressMin;
    public final Lazy qsDetailControllerLazy;
    public final SecQSExpansionStateInteractor qsExpansionStateInteractor;
    public VolumeToggleSeekBar slider;
    public final Lazy soundCraftQpDetailAdapterLazy;
    public int stream;
    public Toast toast;
    public QPVolumeIcon volumeIcon;
    public final VolumeManager volumeManager;
    public int progress = 70;
    public int progressMax = 150;
    public boolean seekBarEnabled = true;
    public final kotlin.Lazy resourcePicker$delegate = LazyKt__LazyJVMKt.lazy(new VolumeSeekBar$$ExternalSyntheticLambda0());
    public final VolumeSeekBar$handler$1 handler = new Handler() { // from class: com.android.systemui.qs.bar.VolumeSeekBar$handler$1
        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            VolumeSeekBar volumeSeekBar = this.this$0;
            VolumeToggleSeekBar volumeToggleSeekBar = volumeSeekBar.slider;
            if (volumeToggleSeekBar != null) {
                volumeToggleSeekBar.setMin(volumeSeekBar.progressMin);
                volumeToggleSeekBar.setMax(volumeSeekBar.progressMax);
                volumeToggleSeekBar.springFinalPosition = message.arg1;
                volumeToggleSeekBar.progressBarSpring.setStartValue(volumeToggleSeekBar.getProgress());
                volumeToggleSeekBar.progressBarSpring.animateToFinalPosition(volumeToggleSeekBar.springFinalPosition);
            }
            QPVolumeIcon qPVolumeIcon = volumeSeekBar.volumeIcon;
            if (qPVolumeIcon != null) {
                qPVolumeIcon.initialize(volumeSeekBar.stream, volumeSeekBar.device, volumeSeekBar.progress, volumeSeekBar.seekBarEnabled, volumeSeekBar.iconAnimationType, false, volumeSeekBar.bluetoothDeviceManager, volumeSeekBar.volumeManager);
            }
            volumeSeekBar.animateProgressDrawable();
            VolumeToggleSeekBar volumeToggleSeekBar2 = volumeSeekBar.slider;
            if (volumeToggleSeekBar2 != null) {
                boolean z = false;
                boolean z2 = volumeSeekBar.seekBarEnabled && !volumeSeekBar.isZenModeDisabled;
                if (volumeSeekBar.device == 32768 && volumeSeekBar.isSmartViewEnabled) {
                    z = true;
                }
                volumeToggleSeekBar2.setEnabled(z2);
                volumeToggleSeekBar2.isSmartViewPlaying = z;
            }
        }
    };

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

    /* JADX WARN: Type inference failed for: r1v11, types: [com.android.systemui.qs.bar.VolumeSeekBar$onUnhandledKeyEventListener$1] */
    /* JADX WARN: Type inference failed for: r1v9, types: [com.android.systemui.qs.bar.VolumeSeekBar$handler$1] */
    public VolumeSeekBar(Context context, VolumeManager volumeManager, BluetoothDeviceManager bluetoothDeviceManager, SecQSExpansionStateInteractor secQSExpansionStateInteractor, VolumeDependency volumeDependency, Lazy lazy, Lazy lazy2) {
        this.context = context;
        this.volumeManager = volumeManager;
        this.bluetoothDeviceManager = bluetoothDeviceManager;
        this.qsExpansionStateInteractor = secQSExpansionStateInteractor;
        this.soundCraftQpDetailAdapterLazy = lazy;
        this.qsDetailControllerLazy = lazy2;
        this.audioManagerWrapper = (AudioManagerWrapper) volumeDependency.get(AudioManagerWrapper.class);
        Function2 function2 = new Function2() { // from class: com.android.systemui.qs.bar.VolumeSeekBar$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                VolumeModel volumeModel = (VolumeModel) obj;
                boolean zBooleanValue = ((Boolean) obj2).booleanValue();
                int i = VolumeSeekBar.$r8$clinit;
                Log.d("VolumeSeekBar", "[QP volume] updateVolumeModel model: " + volumeModel + " isTracking : " + zBooleanValue);
                int i2 = volumeModel.volume;
                VolumeSeekBar volumeSeekBar = this.f$0;
                volumeSeekBar.progress = i2;
                volumeSeekBar.progressMin = volumeModel.minVolume;
                volumeSeekBar.progressMax = volumeModel.maxVolume;
                volumeSeekBar.seekBarEnabled = volumeModel.enable;
                volumeSeekBar.isZenModeDisabled = volumeModel.isZenModeDisabled;
                int i3 = volumeModel.device;
                volumeSeekBar.iconAnimationType = i3 == 0;
                volumeSeekBar.device = i3;
                volumeSeekBar.isSmartViewEnabled = volumeModel.isSmartViewEnabled;
                if (!zBooleanValue) {
                    VolumeSeekBar$handler$1 volumeSeekBar$handler$1 = volumeSeekBar.handler;
                    volumeSeekBar$handler$1.sendMessage(volumeSeekBar$handler$1.obtainMessage(0, i2, 0));
                }
                return Unit.INSTANCE;
            }
        };
        volumeManager.getClass();
        if (Trace.isTagEnabled(4096L)) {
            Trace.traceBegin(4096L, "#soundCraft.VolumeManager_updateQPVolumeModel");
            try {
                if (volumeManager.qpVolumeModelCallbacks.size() == 2) {
                    volumeManager.qpVolumeModelCallbacks.clear();
                }
                volumeManager.qpVolumeModelCallbacks.add(function2);
                function2.invoke(volumeManager.getVolumeModel(), Boolean.valueOf(volumeManager.isTracking));
                Unit unit = Unit.INSTANCE;
            } finally {
                Trace.traceEnd(4096L);
            }
        } else {
            if (volumeManager.qpVolumeModelCallbacks.size() == 2) {
                volumeManager.qpVolumeModelCallbacks.clear();
            }
            volumeManager.qpVolumeModelCallbacks.add(function2);
            function2.invoke(volumeManager.getVolumeModel(), Boolean.valueOf(volumeManager.isTracking));
        }
        this.onUnhandledKeyEventListener = new View.OnUnhandledKeyEventListener() { // from class: com.android.systemui.qs.bar.VolumeSeekBar$onUnhandledKeyEventListener$1
            /* JADX WARN: Removed duplicated region for block: B:28:0x004e  */
            @Override // android.view.View.OnUnhandledKeyEventListener
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final boolean onUnhandledKeyEvent(View view, KeyEvent keyEvent) {
                Integer num = null;
                if (keyEvent.getAction() != 0) {
                    keyEvent = null;
                }
                if (keyEvent != null) {
                    int keyCode = keyEvent.getKeyCode();
                    Integer num2 = keyCode != 24 ? keyCode != 25 ? null : -1 : 1;
                    if (num2 != null) {
                        VolumeSeekBar volumeSeekBar = this.this$0;
                        if (volumeSeekBar.isShowing && volumeSeekBar.audioManagerWrapper.am.getModeInternal() == 0) {
                            if (AudioManager.semGetActiveStreamType() != 3) {
                                VolumeManager volumeManager2 = volumeSeekBar.volumeManager;
                                if (volumeManager2.volumeModel.isSmartViewEnabled || (volumeManager2.remoteStreamEnabled && volumeManager2.isRemoteStreamPlaying)) {
                                    num = num2;
                                }
                            }
                        }
                        if (num != null) {
                            VolumeSeekBar volumeSeekBar2 = this.this$0;
                            int iIntValue = num.intValue();
                            ListPopupWindow$$ExternalSyntheticOutline0.m(iIntValue, "[QP Volume] adjustVolume direction = ", "VolumeSeekBar");
                            volumeSeekBar2.audioManagerWrapper.am.adjustVolume(iIntValue, 0);
                            return true;
                        }
                    }
                }
                return false;
            }
        };
        this.downPoint = new PointF();
        this.stream = -1;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x002b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void animateProgressDrawable() {
        int i;
        boolean z;
        Drawable thumb;
        QPVolumeIcon qPVolumeIcon = this.volumeIcon;
        if ((qPVolumeIcon == null || !qPVolumeIcon.isShocked) && (i = this.progress) != this.progressMax) {
            z = false;
            if (i < getThumbThreshold()) {
                VolumeToggleSeekBar volumeToggleSeekBar = this.slider;
                if (((volumeToggleSeekBar == null || (thumb = volumeToggleSeekBar.getThumb()) == null) ? 0 : thumb.getAlpha()) == 255) {
                    z = true;
                }
            }
        }
        if (z == this.isEarShockWarned) {
            return;
        }
        this.isEarShockWarned = z;
        VolumeToggleSeekBar volumeToggleSeekBar2 = this.slider;
        Drawable progressDrawable = volumeToggleSeekBar2 != null ? volumeToggleSeekBar2.getProgressDrawable() : null;
        LayerDrawable layerDrawable = progressDrawable instanceof LayerDrawable ? (LayerDrawable) progressDrawable : null;
        Object drawable = layerDrawable != null ? layerDrawable.getDrawable(1) : null;
        TransitionDrawable transitionDrawable = drawable instanceof TransitionDrawable ? (TransitionDrawable) drawable : null;
        if (transitionDrawable != null) {
            if (z) {
                transitionDrawable.startTransition(IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend);
            } else {
                transitionDrawable.reverseTransition(IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend);
            }
        }
    }

    public final int getThumbThreshold() {
        VolumeToggleSeekBar volumeToggleSeekBar = this.slider;
        if (volumeToggleSeekBar != null) {
            volumeToggleSeekBar.getClass();
            if (volumeToggleSeekBar.getWidth() != 0) {
                int brightnessBarExpandedHeight = ((SecQSPanelResourcePicker) this.resourcePicker$delegate.getValue()).getBrightnessBarExpandedHeight(this.context);
                VolumeToggleSeekBar volumeToggleSeekBar2 = this.slider;
                volumeToggleSeekBar2.getClass();
                int max = volumeToggleSeekBar2.getMax() * brightnessBarExpandedHeight;
                VolumeToggleSeekBar volumeToggleSeekBar3 = this.slider;
                volumeToggleSeekBar3.getClass();
                int width = max / volumeToggleSeekBar3.getWidth();
                if (width == 0) {
                    return 26;
                }
                return width;
            }
        }
        return 26;
    }

    public final void initialize(VolumeToggleSeekBar volumeToggleSeekBar, QPVolumeIcon qPVolumeIcon) {
        this.slider = volumeToggleSeekBar;
        this.stream = 3;
        this.volumeIcon = qPVolumeIcon;
        if (qPVolumeIcon != null) {
            qPVolumeIcon.initialize(3, this.device, this.progress, this.seekBarEnabled, this.iconAnimationType, true, this.bluetoothDeviceManager, this.volumeManager);
        }
        final VolumeToggleSeekBar volumeToggleSeekBar2 = this.slider;
        if (volumeToggleSeekBar2 != null) {
            VolumeManager volumeManager = this.volumeManager;
            VolumeModel volumeModel = volumeManager.getVolumeModel();
            volumeToggleSeekBar2.getThumb().setAlpha(0);
            final ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(0, 255);
            valueAnimatorOfInt.setDuration(200L);
            valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.qs.bar.VolumeSeekBar$initialize$1$thumbAnimator$1$1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    volumeToggleSeekBar2.getThumb().setAlpha(((Integer) valueAnimator.getAnimatedValue()).intValue());
                }
            });
            volumeToggleSeekBar2.setMin(volumeModel.minVolume);
            volumeToggleSeekBar2.setMax(volumeModel.maxVolume);
            volumeToggleSeekBar2.setProgress(volumeModel.volume);
            volumeToggleSeekBar2.volumeManager = volumeManager;
            volumeToggleSeekBar2.volumeSeekBar = this;
            volumeToggleSeekBar2.isTracking = false;
            volumeToggleSeekBar2.volumeController = (VolumeDialogController) Dependency.sDependency.getDependencyInner(VolumeDialogController.class);
            volumeToggleSeekBar2.scaledTouchSlop = ViewConfiguration.get(volumeToggleSeekBar2.getContext()).getScaledTouchSlop();
            volumeToggleSeekBar2.setOnSeekBarChangeListener(volumeToggleSeekBar2.seekbarChangeListener);
            volumeToggleSeekBar2.setContentDescription(volumeToggleSeekBar2.getContext().getString(R.string.volumepanel_media));
            Context context = volumeToggleSeekBar2.getContext();
            final Integer numValueOf = context != null ? Integer.valueOf(ViewConfiguration.get(context).getScaledTouchSlop()) : null;
            final Handler handler = new Handler();
            volumeToggleSeekBar2.addOnUnhandledKeyEventListener(this.onUnhandledKeyEventListener);
            volumeToggleSeekBar2.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.qs.bar.VolumeSeekBar$initialize$1$1
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(final View view, final MotionEvent motionEvent) {
                    if (motionEvent.getAction() == 0) {
                        VolumeSeekBar volumeSeekBar = this.this$0;
                        volumeSeekBar.isLongPressed = false;
                        volumeSeekBar.downPoint.set(motionEvent.getX(), motionEvent.getY());
                        valueAnimatorOfInt.start();
                        Handler handler2 = handler;
                        final VolumeToggleSeekBar volumeToggleSeekBar3 = volumeToggleSeekBar2;
                        final VolumeSeekBar volumeSeekBar2 = this.this$0;
                        handler2.postDelayed(new Runnable() { // from class: com.android.systemui.qs.bar.VolumeSeekBar$initialize$1$1.1
                            @Override // java.lang.Runnable
                            public final void run() throws Resources.NotFoundException {
                                volumeToggleSeekBar3.performHapticFeedback(0);
                                view.setPressed(false);
                                VolumeSeekBar volumeSeekBar3 = volumeSeekBar2;
                                volumeSeekBar3.isLongPressed = true;
                                if (volumeSeekBar3.audioManagerWrapper.am.getModeInternal() == 0) {
                                    ((SecQSDetailController) volumeSeekBar3.qsDetailControllerLazy.get()).showTargetDetail((DetailAdapter) volumeSeekBar3.soundCraftQpDetailAdapterLazy.get());
                                } else {
                                    Toast toast = volumeSeekBar3.toast;
                                    if (toast != null) {
                                        toast.cancel();
                                        Unit unit = Unit.INSTANCE;
                                    }
                                    Context context2 = volumeSeekBar3.context;
                                    Toast toastMakeText = Toast.makeText(context2, context2.getString(R.string.sound_craft_not_change_during_call), 0);
                                    volumeSeekBar3.toast = toastMakeText;
                                    if (toastMakeText != null) {
                                        toastMakeText.show();
                                    }
                                }
                                VolumeSeekBar volumeSeekBar4 = volumeSeekBar2;
                                MotionEvent motionEvent2 = motionEvent;
                                volumeSeekBar4.getClass();
                                motionEvent2.setAction(3);
                                VolumeToggleSeekBar volumeToggleSeekBar4 = volumeSeekBar4.slider;
                                if (volumeToggleSeekBar4 != null) {
                                    volumeToggleSeekBar4.onTouchEvent(motionEvent2);
                                }
                                SystemUIAnalytics.sendRunestoneEventCDLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_MEDIA_VOLUME_EXPAND, "location", "quick panel", SystemUIAnalytics.RUNESTONE_LABEL_QP_LAYOUT);
                            }
                        }, 500L);
                    } else if (motionEvent.getAction() == 2) {
                        float fHypot = (float) Math.hypot(motionEvent.getX() - this.this$0.downPoint.x, motionEvent.getY() - this.this$0.downPoint.y);
                        numValueOf.getClass();
                        if (fHypot > r6.intValue() && !this.this$0.isLongPressed) {
                            handler.removeCallbacksAndMessages(null);
                        }
                    } else {
                        if (!this.this$0.isLongPressed) {
                            handler.removeCallbacksAndMessages(null);
                        }
                        valueAnimatorOfInt.reverse();
                    }
                    return this.this$0.isLongPressed;
                }
            });
            volumeToggleSeekBar2.setOnHoverListener(new View.OnHoverListener() { // from class: com.android.systemui.qs.bar.VolumeSeekBar$initialize$1$2
                @Override // android.view.View.OnHoverListener
                public final boolean onHover(View view, MotionEvent motionEvent) {
                    int action = motionEvent.getAction();
                    if (action == 9) {
                        view.setHovered(true);
                        view.sendAccessibilityEvent(128);
                        return true;
                    }
                    if (action != 10) {
                        return false;
                    }
                    view.setHovered(false);
                    view.sendAccessibilityEvent(256);
                    return true;
                }
            });
        }
    }

    @Override // com.android.systemui.shade.domain.interactor.SecQSExpansionStateListener
    public final void onQSExpansionStateChanged(SecQSExpansionStateChangeEvent secQSExpansionStateChangeEvent) {
        boolean z = secQSExpansionStateChangeEvent.expanded;
        this.isShowing = z;
        EmergencyButtonController$$ExternalSyntheticOutline0.m("[QP volume] onQSExpansionStateChanged isShowing = ", "VolumeSeekBar", z);
        this.volumeManager.updateCurrentVolume();
    }

    public final void setProgressChanged(int i) {
        this.progress = i;
        QPVolumeIcon qPVolumeIcon = this.volumeIcon;
        if (qPVolumeIcon != null) {
            qPVolumeIcon.updateLayout(i);
        }
        if (i < getThumbThreshold()) {
            VolumeToggleSeekBar volumeToggleSeekBar = this.slider;
            ((ScaleDrawable) (volumeToggleSeekBar != null ? volumeToggleSeekBar.getThumb() : null)).setLevel((i * 10000) / getThumbThreshold());
        } else {
            VolumeToggleSeekBar volumeToggleSeekBar2 = this.slider;
            ((ScaleDrawable) (volumeToggleSeekBar2 != null ? volumeToggleSeekBar2.getThumb() : null)).setLevel(10000);
        }
        animateProgressDrawable();
    }
}
