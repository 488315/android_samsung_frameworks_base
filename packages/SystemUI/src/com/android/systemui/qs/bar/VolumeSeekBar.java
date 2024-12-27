package com.android.systemui.qs.bar;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Handler;
import android.os.Message;
import android.os.Trace;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ImageView;
import android.widget.Toast;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.audio.soundcraft.interfaces.connectivity.BluetoothDeviceManager;
import com.android.systemui.audio.soundcraft.interfaces.volume.VolumeManager;
import com.android.systemui.audio.soundcraft.model.common.VolumeModel;
import com.android.systemui.plugins.VolumeDialogController;
import com.android.systemui.plugins.qs.DetailAdapter;
import com.android.systemui.qs.SecQSDetailController;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.shade.domain.interactor.SecQSExpansionStateInteractor;
import com.android.systemui.shade.domain.interactor.SecQSExpansionStateListener;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.volume.VolumeDependency;
import com.android.systemui.volume.util.AudioManagerWrapper;
import com.android.systemui.volume.util.ColorUtils;
import com.android.systemui.volume.util.ViewVisibilityUtil;
import com.android.systemui.volume.view.icon.QPVolumeIcon;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import dagger.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class VolumeSeekBar implements SecQSExpansionStateListener {
    public final AudioManagerWrapper audioManagerWrapper;
    public final BluetoothDeviceManager bluetoothDeviceManager;
    public final Context context;
    public int device;
    public final PointF downPoint;
    public boolean iconAnimationType;
    public boolean isAllSoundMute;
    public boolean isAuraCasting;
    public boolean isEarShockWarned;
    public boolean isLongPressed;
    public boolean isShowing;
    public boolean isSmartViewEnabled;
    public boolean isZenMode;
    public boolean isZenModeDisabled;
    public final VolumeSeekBar$onUnhandledKeyEventListener$1 onUnhandledKeyEventListener;
    public int progressMin;
    public final Lazy qsDetailControllerLazy;
    public final SecQSExpansionStateInteractor qsExpansionStateInteractor;
    public VolumeToggleSeekBar slider;
    public final Lazy soundCraftQpDetailAdapterLazy;
    public ImageView statusIcon;
    public int stream;
    public Toast toast;
    public QPVolumeIcon volumeIcon;
    public final VolumeManager volumeManager;
    public int progress = 70;
    public int progressMax = 150;
    public boolean seekBarEnabled = true;
    public final kotlin.Lazy resourcePicker$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.qs.bar.VolumeSeekBar$resourcePicker$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);
        }
    });
    public final VolumeSeekBar$handler$1 handler = new Handler() { // from class: com.android.systemui.qs.bar.VolumeSeekBar$handler$1
        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            VolumeSeekBar volumeSeekBar = VolumeSeekBar.this;
            VolumeToggleSeekBar volumeToggleSeekBar = volumeSeekBar.slider;
            if (volumeToggleSeekBar != null) {
                volumeToggleSeekBar.setMin(volumeSeekBar.progressMin);
                volumeToggleSeekBar.setMax(volumeSeekBar.progressMax);
                volumeToggleSeekBar.springFinalPosition = message.arg1;
                volumeToggleSeekBar.progressBarSpring.setStartValue(volumeToggleSeekBar.getProgress());
                volumeToggleSeekBar.progressBarSpring.animateToFinalPosition(volumeToggleSeekBar.springFinalPosition);
                volumeSeekBar.updateStatusIcon();
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
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
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
        Function2 function2 = new Function2() { // from class: com.android.systemui.qs.bar.VolumeSeekBar.1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                VolumeModel volumeModel = (VolumeModel) obj;
                boolean booleanValue = ((Boolean) obj2).booleanValue();
                Log.d("VolumeSeekBar", "[QP volume] updateVolumeModel model: " + volumeModel + " isTracking : " + booleanValue);
                VolumeSeekBar volumeSeekBar = VolumeSeekBar.this;
                int i = volumeModel.volume;
                volumeSeekBar.progress = i;
                volumeSeekBar.progressMin = volumeModel.minVolume;
                volumeSeekBar.progressMax = volumeModel.maxVolume;
                volumeSeekBar.seekBarEnabled = volumeModel.enable;
                volumeSeekBar.isAuraCasting = volumeModel.isBroadcast;
                volumeSeekBar.isZenMode = volumeModel.isZenMode == 1;
                volumeSeekBar.isZenModeDisabled = volumeModel.isZenModeDisabled;
                volumeSeekBar.isAllSoundMute = volumeModel.isAllSoundMute;
                int i2 = volumeModel.device;
                volumeSeekBar.iconAnimationType = i2 == 0;
                volumeSeekBar.device = i2;
                volumeSeekBar.isSmartViewEnabled = volumeModel.isSmartViewEnabled;
                if (!booleanValue) {
                    VolumeSeekBar$handler$1 volumeSeekBar$handler$1 = volumeSeekBar.handler;
                    volumeSeekBar$handler$1.sendMessage(volumeSeekBar$handler$1.obtainMessage(0, i, 0));
                }
                return Unit.INSTANCE;
            }
        };
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
            /* JADX WARN: Code restructure failed: missing block: B:23:0x004f, code lost:
            
                if (r1.isRemoteStreamPlaying == false) goto L29;
             */
            @Override // android.view.View.OnUnhandledKeyEventListener
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final boolean onUnhandledKeyEvent(android.view.View r6, android.view.KeyEvent r7) {
                /*
                    r5 = this;
                    int r6 = r7.getAction()
                    r0 = 0
                    if (r6 != 0) goto L8
                    goto L9
                L8:
                    r7 = r0
                L9:
                    r6 = 0
                    if (r7 == 0) goto L69
                    int r7 = r7.getKeyCode()
                    r1 = 24
                    r2 = 1
                    if (r7 == r1) goto L21
                    r1 = 25
                    if (r7 == r1) goto L1b
                    r7 = r0
                    goto L25
                L1b:
                    r7 = -1
                    java.lang.Integer r7 = java.lang.Integer.valueOf(r7)
                    goto L25
                L21:
                    java.lang.Integer r7 = java.lang.Integer.valueOf(r2)
                L25:
                    if (r7 == 0) goto L69
                    com.android.systemui.qs.bar.VolumeSeekBar r1 = com.android.systemui.qs.bar.VolumeSeekBar.this
                    boolean r3 = r1.isShowing
                    if (r3 == 0) goto L52
                    com.android.systemui.volume.util.AudioManagerWrapper r3 = r1.audioManagerWrapper
                    android.media.AudioManager r4 = r3.am
                    int r4 = r4.getModeInternal()
                    if (r4 != 0) goto L52
                    r3.getClass()
                    int r3 = android.media.AudioManager.semGetActiveStreamType()
                    r4 = 3
                    if (r3 == r4) goto L51
                    com.android.systemui.audio.soundcraft.interfaces.volume.VolumeManager r1 = r1.volumeManager
                    com.android.systemui.audio.soundcraft.model.common.VolumeModel r3 = r1.volumeModel
                    boolean r3 = r3.isSmartViewEnabled
                    if (r3 != 0) goto L51
                    boolean r3 = r1.remoteStreamEnabled
                    if (r3 == 0) goto L52
                    boolean r1 = r1.isRemoteStreamPlaying
                    if (r1 == 0) goto L52
                L51:
                    r0 = r7
                L52:
                    if (r0 == 0) goto L69
                    com.android.systemui.qs.bar.VolumeSeekBar r5 = com.android.systemui.qs.bar.VolumeSeekBar.this
                    int r7 = r0.intValue()
                    java.lang.String r0 = "[QP Volume] adjustVolume direction = "
                    java.lang.String r1 = "VolumeSeekBar"
                    androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0.m(r7, r0, r1)
                    com.android.systemui.volume.util.AudioManagerWrapper r5 = r5.audioManagerWrapper
                    android.media.AudioManager r5 = r5.am
                    r5.adjustVolume(r7, r6)
                    r6 = r2
                L69:
                    return r6
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.bar.VolumeSeekBar$onUnhandledKeyEventListener$1.onUnhandledKeyEvent(android.view.View, android.view.KeyEvent):boolean");
            }
        };
        this.downPoint = new PointF();
        this.stream = -1;
    }

    public final void animateProgressDrawable() {
        QPVolumeIcon qPVolumeIcon = this.volumeIcon;
        boolean z = (qPVolumeIcon != null && qPVolumeIcon.isShocked) || this.progress == this.progressMax;
        if (z == this.isEarShockWarned) {
            return;
        }
        this.isEarShockWarned = z;
        VolumeToggleSeekBar volumeToggleSeekBar = this.slider;
        Drawable progressDrawable = volumeToggleSeekBar != null ? volumeToggleSeekBar.getProgressDrawable() : null;
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
            Intrinsics.checkNotNull(volumeToggleSeekBar);
            if (volumeToggleSeekBar.getWidth() != 0) {
                int brightnessBarExpandedHeight = ((SecQSPanelResourcePicker) this.resourcePicker$delegate.getValue()).getBrightnessBarExpandedHeight(this.context);
                VolumeToggleSeekBar volumeToggleSeekBar2 = this.slider;
                Intrinsics.checkNotNull(volumeToggleSeekBar2);
                int max = volumeToggleSeekBar2.getMax() * brightnessBarExpandedHeight;
                VolumeToggleSeekBar volumeToggleSeekBar3 = this.slider;
                Intrinsics.checkNotNull(volumeToggleSeekBar3);
                return max / volumeToggleSeekBar3.getWidth();
            }
        }
        return 26;
    }

    public final void initialize(VolumeToggleSeekBar volumeToggleSeekBar, QPVolumeIcon qPVolumeIcon, ImageView imageView) {
        this.slider = volumeToggleSeekBar;
        this.stream = 3;
        this.volumeIcon = qPVolumeIcon;
        this.statusIcon = imageView;
        qPVolumeIcon.initialize(3, this.device, this.progress, this.seekBarEnabled, this.iconAnimationType, true, this.bluetoothDeviceManager, this.volumeManager);
        final VolumeToggleSeekBar volumeToggleSeekBar2 = this.slider;
        if (volumeToggleSeekBar2 != null) {
            VolumeManager volumeManager = this.volumeManager;
            VolumeModel volumeModel = volumeManager.getVolumeModel();
            volumeToggleSeekBar2.getThumb().setAlpha(0);
            final ValueAnimator ofInt = ValueAnimator.ofInt(0, 255);
            ofInt.setDuration(200L);
            ofInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.qs.bar.VolumeSeekBar$initialize$1$thumbAnimator$1$1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    VolumeToggleSeekBar.this.getThumb().setAlpha(((Integer) valueAnimator.getAnimatedValue()).intValue());
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
            updateStatusIcon();
            Context context = volumeToggleSeekBar2.getContext();
            final Integer valueOf = context != null ? Integer.valueOf(ViewConfiguration.get(context).getScaledTouchSlop()) : null;
            final Handler handler = new Handler();
            volumeToggleSeekBar2.addOnUnhandledKeyEventListener(this.onUnhandledKeyEventListener);
            volumeToggleSeekBar2.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.qs.bar.VolumeSeekBar$initialize$1$1
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(final View view, final MotionEvent motionEvent) {
                    if (motionEvent.getAction() == 0) {
                        VolumeSeekBar volumeSeekBar = VolumeSeekBar.this;
                        volumeSeekBar.isLongPressed = false;
                        volumeSeekBar.downPoint.set(motionEvent.getX(), motionEvent.getY());
                        ofInt.start();
                        Handler handler2 = handler;
                        final VolumeToggleSeekBar volumeToggleSeekBar3 = volumeToggleSeekBar2;
                        final VolumeSeekBar volumeSeekBar2 = VolumeSeekBar.this;
                        handler2.postDelayed(new Runnable() { // from class: com.android.systemui.qs.bar.VolumeSeekBar$initialize$1$1.1
                            @Override // java.lang.Runnable
                            public final void run() {
                                VolumeToggleSeekBar.this.performHapticFeedback(0);
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
                                    Toast makeText = Toast.makeText(context2, context2.getString(R.string.sound_craft_not_change_during_call), 0);
                                    volumeSeekBar3.toast = makeText;
                                    if (makeText != null) {
                                        makeText.show();
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
                        float hypot = (float) Math.hypot(motionEvent.getX() - VolumeSeekBar.this.downPoint.x, motionEvent.getY() - VolumeSeekBar.this.downPoint.y);
                        Intrinsics.checkNotNull(valueOf);
                        if (hypot > r6.intValue() && !VolumeSeekBar.this.isLongPressed) {
                            handler.removeCallbacksAndMessages(null);
                        }
                    } else {
                        if (!VolumeSeekBar.this.isLongPressed) {
                            handler.removeCallbacksAndMessages(null);
                        }
                        ofInt.reverse();
                    }
                    return VolumeSeekBar.this.isLongPressed;
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

    public final void updateStatusIcon() {
        ImageView imageView;
        ImageView imageView2 = this.statusIcon;
        if (imageView2 == null) {
            imageView2 = null;
        }
        imageView2.setImageTintList(ColorUtils.getSingleColorStateList(R.color.qs_tile_volume_icon_color, this.context));
        if (this.isAllSoundMute || this.isZenMode) {
            ImageView imageView3 = this.statusIcon;
            if (imageView3 == null) {
                imageView3 = null;
            }
            imageView3.setImageDrawable(this.context.getDrawable(R.drawable.ic_qp_volume_control_dnd));
            ViewVisibilityUtil viewVisibilityUtil = ViewVisibilityUtil.INSTANCE;
            ImageView imageView4 = this.statusIcon;
            imageView = imageView4 != null ? imageView4 : null;
            viewVisibilityUtil.getClass();
            imageView.setVisibility(0);
            return;
        }
        if (!this.isAuraCasting) {
            ViewVisibilityUtil viewVisibilityUtil2 = ViewVisibilityUtil.INSTANCE;
            ImageView imageView5 = this.statusIcon;
            imageView = imageView5 != null ? imageView5 : null;
            viewVisibilityUtil2.getClass();
            ViewVisibilityUtil.setGone(imageView);
            return;
        }
        ImageView imageView6 = this.statusIcon;
        if (imageView6 == null) {
            imageView6 = null;
        }
        imageView6.setImageDrawable(this.context.getDrawable(R.drawable.ic_auracast));
        ViewVisibilityUtil viewVisibilityUtil3 = ViewVisibilityUtil.INSTANCE;
        ImageView imageView7 = this.statusIcon;
        imageView = imageView7 != null ? imageView7 : null;
        viewVisibilityUtil3.getClass();
        imageView.setVisibility(0);
    }
}
