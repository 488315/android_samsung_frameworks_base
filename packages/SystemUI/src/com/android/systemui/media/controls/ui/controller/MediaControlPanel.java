package com.android.systemui.media.controls.ui.controller;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.app.PendingIntent;
import android.app.WallpaperColors;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.Icon;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.os.Trace;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintSet;
import com.android.internal.logging.InstanceId;
import com.android.settingslib.widget.AdaptiveIcon;
import com.android.systemui.ActivityIntentHelper;
import com.android.systemui.R;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.animation.GhostedViewTransitionAnimatorController;
import com.android.systemui.bluetooth.BroadcastDialogController;
import com.android.systemui.broadcast.BroadcastSender;
import com.android.systemui.communal.domain.interactor.CommunalSceneInteractor;
import com.android.systemui.communal.widgets.CommunalTransitionAnimatorController;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.media.controls.domain.pipeline.MediaActionsKt;
import com.android.systemui.media.controls.shared.model.MediaAction;
import com.android.systemui.media.controls.shared.model.MediaButton;
import com.android.systemui.media.controls.shared.model.MediaData;
import com.android.systemui.media.controls.shared.model.MediaDeviceData;
import com.android.systemui.media.controls.ui.animation.AnimatingColorTransition;
import com.android.systemui.media.controls.ui.animation.AnimationBindHandler;
import com.android.systemui.media.controls.ui.animation.ColorSchemeTransition;
import com.android.systemui.media.controls.ui.animation.MetadataAnimationHandler;
import com.android.systemui.media.controls.ui.binder.SeekBarObserver;
import com.android.systemui.media.controls.ui.view.GutsViewHolder;
import com.android.systemui.media.controls.ui.view.MediaViewHolder;
import com.android.systemui.media.controls.ui.viewmodel.SeekBarViewModel;
import com.android.systemui.media.controls.util.MediaUiEvent;
import com.android.systemui.media.controls.util.MediaUiEventLogger;
import com.android.systemui.media.dialog.MediaOutputDialogManager;
import com.android.systemui.monet.ColorScheme;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.surfaceeffects.PaintDrawCallback;
import com.android.systemui.surfaceeffects.loadingeffect.LoadingEffect;
import com.android.systemui.surfaceeffects.loadingeffect.LoadingEffectView;
import com.android.systemui.surfaceeffects.ripple.MultiRippleController;
import com.android.systemui.surfaceeffects.ripple.MultiRippleView;
import com.android.systemui.surfaceeffects.ripple.RippleAnimation;
import com.android.systemui.surfaceeffects.ripple.RippleAnimationConfig;
import com.android.systemui.surfaceeffects.ripple.RippleShader;
import com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseAnimationConfig;
import com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseController;
import com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseShader;
import com.android.systemui.util.ColorUtilKt;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.animation.TransitionLayout;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.settings.GlobalSettings;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.Executor;
import kotlin.Triple;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public class MediaControlPanel {
    public static final List SEMANTIC_ACTIONS_ALL;
    public static final List SEMANTIC_ACTIONS_COMPACT;
    public static final List SEMANTIC_ACTIONS_HIDE_WHEN_SCRUBBING;
    public static final Intent SETTINGS_INTENT = new Intent("android.settings.ACTION_MEDIA_CONTROLS_SETTINGS");
    static final long TURBULENCE_NOISE_PLAY_DURATION = 7500;
    public final ActivityIntentHelper mActivityIntentHelper;
    public final ActivityStarter mActivityStarter;
    public final Executor mBackgroundExecutor;
    public final BroadcastDialogController mBroadcastDialogController;
    public ColorSchemeTransition mColorSchemeTransition;
    public final CommunalSceneInteractor mCommunalSceneInteractor;
    public final Context mContext;
    public MediaController mController;
    public final FalsingManager mFalsingManager;
    public final GlobalSettings mGlobalSettings;
    public InstanceId mInstanceId;
    public String mKey;
    public final KeyguardStateController mKeyguardStateController;
    public LoadingEffect mLoadingEffect;
    public final NotificationLockscreenUserManager mLockscreenUserManager;
    public final MediaUiEventLogger mLogger;
    public final DelayableExecutor mMainExecutor;
    public final MediaCarouselController mMediaCarouselController;
    public MediaData mMediaData;
    public final Lazy mMediaDataManagerLazy;
    public final MediaOutputDialogManager mMediaOutputDialogManager;
    public final MediaViewController mMediaViewController;
    public MediaViewHolder mMediaViewHolder;
    public MetadataAnimationHandler mMetadataAnimationHandler;
    public MultiRippleController mMultiRippleController;
    public String mPackageName;
    public SeekBarObserver mSeekBarObserver;
    public final SeekBarViewModel mSeekBarViewModel;
    public MediaSession.Token mToken;
    public TurbulenceNoiseAnimationConfig mTurbulenceNoiseAnimationConfig;
    public int mUid = -1;
    public Drawable mPrevArtwork = null;
    public boolean mIsArtworkBound = false;
    public int mArtworkBoundId = 0;
    public int mArtworkNextBindRequestId = 0;
    public boolean mIsScrubbing = false;
    public boolean mIsSeekBarEnabled = false;
    public final MediaControlPanel$$ExternalSyntheticLambda0 mScrubbingChangeListener = new MediaControlPanel$$ExternalSyntheticLambda0(this);
    public final MediaControlPanel$$ExternalSyntheticLambda0 mEnabledChangeListener = new MediaControlPanel$$ExternalSyntheticLambda0(this);
    public final MediaControlPanel$$ExternalSyntheticLambda0 mContentDescriptionListener = new MediaControlPanel$$ExternalSyntheticLambda0(this);
    public boolean mWasPlaying = false;
    public boolean mButtonClicked = false;
    public final AnonymousClass1 mNoiseDrawCallback = new PaintDrawCallback() { // from class: com.android.systemui.media.controls.ui.controller.MediaControlPanel.1
        @Override // com.android.systemui.surfaceeffects.PaintDrawCallback
        public final void onDraw(Paint paint) {
            LoadingEffectView loadingEffectView = MediaControlPanel.this.mMediaViewHolder.loadingEffectView;
            loadingEffectView.paint = paint;
            paint.getClass();
            paint.setBlendMode(loadingEffectView.blendMode);
            loadingEffectView.invalidate();
        }
    };
    public final AnonymousClass2 mStateChangedCallback = new LoadingEffect.AnimationStateChangedCallback() { // from class: com.android.systemui.media.controls.ui.controller.MediaControlPanel.2
        @Override // com.android.systemui.surfaceeffects.loadingeffect.LoadingEffect.AnimationStateChangedCallback
        public final void onStateChanged(LoadingEffect.AnimationState animationState) {
            LoadingEffectView loadingEffectView = MediaControlPanel.this.mMediaViewHolder.loadingEffectView;
            if (animationState == LoadingEffect.AnimationState.NOT_PLAYING) {
                loadingEffectView.setVisibility(4);
            } else {
                loadingEffectView.setVisibility(0);
            }
        }
    };

    static {
        Integer numValueOf = Integer.valueOf(R.id.actionPlayPause);
        Integer numValueOf2 = Integer.valueOf(R.id.actionPrev);
        Integer numValueOf3 = Integer.valueOf(R.id.actionNext);
        SEMANTIC_ACTIONS_COMPACT = List.of(numValueOf, numValueOf2, numValueOf3);
        SEMANTIC_ACTIONS_HIDE_WHEN_SCRUBBING = List.of(numValueOf2, numValueOf3);
        SEMANTIC_ACTIONS_ALL = List.of(numValueOf, numValueOf2, numValueOf3, Integer.valueOf(R.id.action0), Integer.valueOf(R.id.action1));
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.media.controls.ui.controller.MediaControlPanel$1] */
    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.systemui.media.controls.ui.controller.MediaControlPanel$2] */
    public MediaControlPanel(Context context, Executor executor, DelayableExecutor delayableExecutor, ActivityStarter activityStarter, BroadcastSender broadcastSender, MediaViewController mediaViewController, SeekBarViewModel seekBarViewModel, Lazy lazy, MediaOutputDialogManager mediaOutputDialogManager, MediaCarouselController mediaCarouselController, FalsingManager falsingManager, MediaUiEventLogger mediaUiEventLogger, KeyguardStateController keyguardStateController, ActivityIntentHelper activityIntentHelper, CommunalSceneInteractor communalSceneInteractor, NotificationLockscreenUserManager notificationLockscreenUserManager, BroadcastDialogController broadcastDialogController, GlobalSettings globalSettings) {
        this.mContext = context;
        this.mBackgroundExecutor = executor;
        this.mMainExecutor = delayableExecutor;
        this.mActivityStarter = activityStarter;
        this.mSeekBarViewModel = seekBarViewModel;
        this.mMediaViewController = mediaViewController;
        this.mMediaDataManagerLazy = lazy;
        this.mMediaOutputDialogManager = mediaOutputDialogManager;
        this.mMediaCarouselController = mediaCarouselController;
        this.mFalsingManager = falsingManager;
        this.mLogger = mediaUiEventLogger;
        this.mKeyguardStateController = keyguardStateController;
        this.mActivityIntentHelper = activityIntentHelper;
        this.mLockscreenUserManager = notificationLockscreenUserManager;
        this.mBroadcastDialogController = broadcastDialogController;
        this.mCommunalSceneInteractor = communalSceneInteractor;
        seekBarViewModel.logSeek = new MediaControlPanel$$ExternalSyntheticLambda1(this, 0);
        this.mGlobalSettings = globalSettings;
        SeekBarObserver seekBarObserver = this.mSeekBarObserver;
        if (seekBarObserver != null) {
            seekBarObserver.animationEnabled = globalSettings.getFloat(SettingsHelper.INDEX_GLOBAL_ANIMATOR_DURATION_SCALE, 1.0f) > 0.0f;
        }
    }

    public static void scaleTransitionDrawableLayer(TransitionDrawable transitionDrawable, int i, int i2, int i3) {
        Drawable drawable = transitionDrawable.getDrawable(i);
        if (drawable == null) {
            return;
        }
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        Pair pair = new Pair(Integer.valueOf(intrinsicWidth), Integer.valueOf(intrinsicHeight));
        Pair pair2 = new Pair(Integer.valueOf(i2), Integer.valueOf(i3));
        float fIntValue = ((Integer) pair.first).intValue();
        float fIntValue2 = ((Integer) pair.second).intValue();
        float fIntValue3 = ((Integer) pair2.first).intValue();
        float fIntValue4 = ((Integer) pair2.second).intValue();
        float f = 0.0f;
        if (fIntValue != 0.0f && fIntValue2 != 0.0f && fIntValue3 != 0.0f && fIntValue4 != 0.0f) {
            f = fIntValue / fIntValue2 > fIntValue3 / fIntValue4 ? fIntValue4 / fIntValue2 : fIntValue3 / fIntValue;
        }
        if (f == 0.0f) {
            return;
        }
        transitionDrawable.setLayerSize(i, (int) (intrinsicWidth * f), (int) (f * intrinsicHeight));
    }

    public static void setVisibleAndAlpha(ConstraintSet constraintSet, int i, boolean z) {
        setVisibleAndAlpha(constraintSet, i, z, 8);
    }

    public LayerDrawable addGradientToPlayerAlbum(Icon icon, ColorScheme colorScheme, int i, int i2) {
        Drawable drawableLoadDrawable;
        if (icon == null) {
            drawableLoadDrawable = null;
        } else {
            drawableLoadDrawable = icon.loadDrawable(this.mContext);
            Rect rect = new Rect(0, 0, i, i2);
            if (rect.width() > i || rect.height() > i2) {
                rect.offset((int) (-((rect.width() - i) / 2.0f)), (int) (-((rect.height() - i2) / 2.0f)));
            }
            drawableLoadDrawable.setBounds(rect);
        }
        GradientDrawable gradientDrawable = (GradientDrawable) this.mContext.getDrawable(R.drawable.qs_media_scrim).mutate();
        int onSurface = colorScheme.mMaterialScheme.getOnSurface();
        gradientDrawable.setColors(new int[]{ColorUtilKt.getColorWithAlpha(onSurface, 0.65f), ColorUtilKt.getColorWithAlpha(onSurface, 0.75f)});
        return new LayerDrawable(new Drawable[]{drawableLoadDrawable, gradientDrawable});
    }

    public final void bindButtonCommon(final ImageButton imageButton, MediaAction mediaAction) {
        if (mediaAction == null) {
            imageButton.setImageDrawable(null);
            imageButton.setContentDescription(null);
            imageButton.setEnabled(false);
            imageButton.setBackground(null);
            return;
        }
        final Drawable drawable = mediaAction.icon;
        imageButton.setImageDrawable(drawable);
        imageButton.setContentDescription(mediaAction.contentDescription);
        final Drawable drawable2 = mediaAction.background;
        imageButton.setBackground(drawable2);
        final Runnable runnable = mediaAction.action;
        if (runnable == null) {
            imageButton.setEnabled(false);
        } else {
            imageButton.setEnabled(true);
            imageButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.media.controls.ui.controller.MediaControlPanel$$ExternalSyntheticLambda15
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    MediaControlPanel mediaControlPanel = this.f$0;
                    ImageButton imageButton2 = imageButton;
                    Runnable runnable2 = runnable;
                    Object obj = drawable;
                    Object obj2 = drawable2;
                    if (mediaControlPanel.mFalsingManager.isFalseTap(2)) {
                        return;
                    }
                    mediaControlPanel.mLogger.logTapAction(imageButton2.getId(), mediaControlPanel.mUid, mediaControlPanel.mInstanceId, mediaControlPanel.mPackageName);
                    mediaControlPanel.mWasPlaying = mediaControlPanel.isPlaying();
                    mediaControlPanel.mButtonClicked = true;
                    runnable2.run();
                    final MultiRippleController multiRippleController = mediaControlPanel.mMultiRippleController;
                    float width = mediaControlPanel.mMediaViewHolder.multiRippleView.getWidth() * 2;
                    final RippleAnimation rippleAnimation = new RippleAnimation(new RippleAnimationConfig(RippleShader.RippleShape.CIRCLE, 1500L, (imageButton2.getWidth() * 0.5f) + imageButton2.getX(), (imageButton2.getHeight() * 0.5f) + imageButton2.getY(), width, width, mediaControlPanel.mContext.getResources().getDisplayMetrics().density, mediaControlPanel.mColorSchemeTransition.getPrimaryColor().targetColor, 100, 0.0f, null, null, null, false));
                    MultiRippleView multiRippleView = multiRippleController.multipleRippleView;
                    if (multiRippleView.ripples.size() < 10) {
                        multiRippleView.ripples.add(rippleAnimation);
                        final Runnable runnable3 = new Runnable() { // from class: com.android.systemui.surfaceeffects.ripple.MultiRippleController$play$1
                            @Override // java.lang.Runnable
                            public final void run() {
                                multiRippleController.multipleRippleView.ripples.remove(rippleAnimation);
                            }
                        };
                        if (!rippleAnimation.animator.isRunning()) {
                            rippleAnimation.animator.setDuration(rippleAnimation.config.duration);
                            rippleAnimation.animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.surfaceeffects.ripple.RippleAnimation$play$1
                                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                                    long currentPlayTime = valueAnimator.getCurrentPlayTime();
                                    float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                                    rippleAnimation.rippleShader.setRawProgress(fFloatValue);
                                    RippleAnimation rippleAnimation2 = rippleAnimation;
                                    rippleAnimation2.rippleShader.setDistortionStrength(rippleAnimation2.config.shouldDistort ? 1 - fFloatValue : 0.0f);
                                    rippleAnimation.rippleShader.setFloatUniform("in_time", currentPlayTime);
                                }
                            });
                            rippleAnimation.animator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.surfaceeffects.ripple.RippleAnimation$play$2
                                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                public final void onAnimationEnd(Animator animator) {
                                    Runnable runnable4 = runnable3;
                                    if (runnable4 != null) {
                                        runnable4.run();
                                    }
                                }
                            });
                            rippleAnimation.animator.start();
                        }
                        multiRippleView.invalidate();
                    }
                    if (obj instanceof Animatable) {
                        ((Animatable) obj).start();
                    }
                    if (obj2 instanceof Animatable) {
                        ((Animatable) obj2).start();
                    }
                }
            });
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void bindPlayer(final MediaData mediaData, String str) {
        int i;
        final boolean z;
        AnimationBindHandler animationBindHandler;
        Double d;
        int i2 = 2;
        final int i3 = 0;
        final int i4 = 1;
        int i5 = SceneContainerFlag.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        if (this.mMediaViewHolder == null) {
            return;
        }
        if (Trace.isEnabled()) {
            Trace.traceBegin(4096L, "MediaControlPanel#bindPlayer<" + str + ">");
        }
        this.mKey = str;
        this.mMediaData = mediaData;
        MediaSession.Token token = mediaData.token;
        this.mPackageName = mediaData.packageName;
        this.mUid = mediaData.appUid;
        this.mInstanceId = mediaData.instanceId;
        MediaSession.Token token2 = this.mToken;
        if (token2 == null || !token2.equals(token)) {
            this.mToken = token;
        }
        if (this.mToken != null) {
            this.mController = new MediaController(this.mContext, this.mToken);
        } else {
            this.mController = null;
        }
        PendingIntent pendingIntent = mediaData.clickIntent;
        if (pendingIntent != null) {
            this.mMediaViewHolder.player.setOnClickListener(new MediaControlPanel$$ExternalSyntheticLambda6(this, pendingIntent, str));
        }
        boolean z2 = mediaData.resumption;
        if (!z2 || (d = mediaData.resumeProgress) == null) {
            this.mBackgroundExecutor.execute(new MediaControlPanel$$ExternalSyntheticLambda7(this, this.mController, i3));
        } else {
            double dDoubleValue = d.doubleValue();
            SeekBarViewModel seekBarViewModel = this.mSeekBarViewModel;
            seekBarViewModel.getClass();
            seekBarViewModel.set_data(new SeekBarViewModel.Progress(true, false, false, false, Integer.valueOf((int) (dDoubleValue * 100)), 100, false));
        }
        ViewGroup viewGroup = this.mMediaViewHolder.seamless;
        viewGroup.setVisibility(0);
        MediaViewHolder mediaViewHolder = this.mMediaViewHolder;
        ImageView imageView = mediaViewHolder.seamlessIcon;
        TextView textView = mediaViewHolder.seamlessText;
        MediaDeviceData mediaDeviceData = mediaData.device;
        boolean z3 = !(mediaDeviceData == null || mediaDeviceData.enabled) || z2;
        boolean z4 = !z3;
        CharSequence string = this.mContext.getString(R.string.media_seamless_other_device);
        this.mMediaViewHolder.seamlessButton.setAlpha(z3 ? 0.38f : 1.0f);
        viewGroup.setEnabled(z4);
        if (mediaDeviceData != null) {
            Drawable drawable = mediaDeviceData.icon;
            if (drawable instanceof AdaptiveIcon) {
                AdaptiveIcon adaptiveIcon = (AdaptiveIcon) drawable;
                adaptiveIcon.setBackgroundColor(((AnimatingColorTransition) this.mColorSchemeTransition.onPrimaryColor$delegate.getValue()).targetColor);
                imageView.setImageDrawable(adaptiveIcon);
            } else {
                imageView.setImageDrawable(drawable);
            }
            CharSequence charSequence = mediaDeviceData.name;
            if (charSequence != null) {
                string = charSequence;
            }
        } else {
            imageView.setImageResource(R.drawable.ic_media_home_devices);
        }
        textView.setText(string);
        viewGroup.setContentDescription(string);
        viewGroup.setOnClickListener(new MediaControlPanel$$ExternalSyntheticLambda6(this, mediaDeviceData, i4));
        MediaControlPanel$$ExternalSyntheticLambda7 mediaControlPanel$$ExternalSyntheticLambda7 = new MediaControlPanel$$ExternalSyntheticLambda7(this, mediaData, i4);
        GutsViewHolder gutsViewHolder = this.mMediaViewHolder.gutsViewHolder;
        boolean z5 = mediaData.isClearable;
        gutsViewHolder.gutsText.setText(z5 ? this.mContext.getString(R.string.controls_media_close_session, mediaData.app) : this.mContext.getString(R.string.controls_media_active_session));
        gutsViewHolder.dismissText.setVisibility(z5 ? 0 : 8);
        gutsViewHolder.dismiss.setEnabled(z5);
        gutsViewHolder.dismiss.setOnClickListener(new MediaControlPanel$$ExternalSyntheticLambda6(this, mediaControlPanel$$ExternalSyntheticLambda7, i2));
        TextView textView2 = gutsViewHolder.cancelText;
        if (z5) {
            textView2.setBackground(this.mContext.getDrawable(R.drawable.qs_media_outline_button));
        } else {
            textView2.setBackground(this.mContext.getDrawable(R.drawable.qs_media_solid_button));
        }
        gutsViewHolder.cancel.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.systemui.media.controls.ui.controller.MediaControlPanel$$ExternalSyntheticLambda20
            public final /* synthetic */ MediaControlPanel f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                int i6 = i3;
                MediaControlPanel mediaControlPanel = this.f$0;
                switch (i6) {
                    case 0:
                        if (!mediaControlPanel.mFalsingManager.isFalseTap(1)) {
                            mediaControlPanel.closeGuts(false);
                            break;
                        }
                        break;
                    default:
                        if (!mediaControlPanel.mFalsingManager.isFalseTap(1)) {
                            mediaControlPanel.mLogger.logger.logWithInstanceId(MediaUiEvent.OPEN_SETTINGS_LONG_PRESS, mediaControlPanel.mUid, mediaControlPanel.mPackageName, mediaControlPanel.mInstanceId);
                            mediaControlPanel.mActivityStarter.startActivity(MediaControlPanel.SETTINGS_INTENT, true);
                            break;
                        }
                        break;
                }
            }
        });
        if (gutsViewHolder.isDismissible != z5) {
            gutsViewHolder.isDismissible = z5;
            ColorScheme colorScheme = gutsViewHolder.colorScheme;
            if (colorScheme != null) {
                gutsViewHolder.setColors(colorScheme);
            }
        }
        gutsViewHolder.settings.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.systemui.media.controls.ui.controller.MediaControlPanel$$ExternalSyntheticLambda20
            public final /* synthetic */ MediaControlPanel f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                int i6 = i4;
                MediaControlPanel mediaControlPanel = this.f$0;
                switch (i6) {
                    case 0:
                        if (!mediaControlPanel.mFalsingManager.isFalseTap(1)) {
                            mediaControlPanel.closeGuts(false);
                            break;
                        }
                        break;
                    default:
                        if (!mediaControlPanel.mFalsingManager.isFalseTap(1)) {
                            mediaControlPanel.mLogger.logger.logWithInstanceId(MediaUiEvent.OPEN_SETTINGS_LONG_PRESS, mediaControlPanel.mUid, mediaControlPanel.mPackageName, mediaControlPanel.mInstanceId);
                            mediaControlPanel.mActivityStarter.startActivity(MediaControlPanel.SETTINGS_INTENT, true);
                            break;
                        }
                        break;
                }
            }
        });
        bindPlayerContentDescription(mediaData);
        bindScrubbingTime(mediaData);
        ArrayList arrayList = new ArrayList();
        MediaViewHolder.Companion.getClass();
        Iterator it = MediaViewHolder.genericButtonIds.iterator();
        while (it.hasNext()) {
            arrayList.add(this.mMediaViewHolder.getAction(((Integer) it.next()).intValue()));
        }
        MediaViewController mediaViewController = this.mMediaViewController;
        ConstraintSet constraintSet = mediaViewController.expandedLayout;
        ConstraintSet constraintSet2 = mediaViewController.collapsedLayout;
        final MediaButton mediaButton = mediaData.semanticActions;
        if (mediaButton != null) {
            int size = arrayList.size();
            int i6 = 0;
            while (i6 < size) {
                Object obj = arrayList.get(i6);
                i6++;
                ImageButton imageButton = (ImageButton) obj;
                setVisibleAndAlpha(constraintSet2, imageButton.getId(), false);
                setVisibleAndAlpha(constraintSet, imageButton.getId(), false);
            }
            Iterator it2 = SEMANTIC_ACTIONS_ALL.iterator();
            while (it2.hasNext()) {
                int iIntValue = ((Integer) it2.next()).intValue();
                final ImageButton action = this.mMediaViewHolder.getAction(iIntValue);
                final MediaAction actionById = mediaButton.getActionById(iIntValue);
                if (action.getTag() == null) {
                    animationBindHandler = new AnimationBindHandler();
                    action.setTag(animationBindHandler);
                } else {
                    animationBindHandler = (AnimationBindHandler) action.getTag();
                }
                final AnimationBindHandler animationBindHandler2 = animationBindHandler;
                Function0 function0 = new Function0() { // from class: com.android.systemui.media.controls.ui.controller.MediaControlPanel$$ExternalSyntheticLambda17
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        ImageButton imageButton2 = action;
                        Intent intent = MediaControlPanel.SETTINGS_INTENT;
                        MediaControlPanel mediaControlPanel = this.f$0;
                        mediaControlPanel.getClass();
                        MediaAction mediaAction = actionById;
                        AnimationBindHandler animationBindHandler3 = animationBindHandler2;
                        if (mediaAction != null) {
                            Integer num = animationBindHandler3.rebindId;
                            Integer num2 = mediaAction.rebindId;
                            if (num == null || num2 == null || !Intrinsics.areEqual(num, num2)) {
                                animationBindHandler3.rebindId = num2;
                                animationBindHandler3.unregisterAll();
                                animationBindHandler3.tryRegister(mediaAction.icon);
                                animationBindHandler3.tryRegister(mediaAction.background);
                                mediaControlPanel.bindButtonCommon(imageButton2, mediaAction);
                            }
                        } else {
                            animationBindHandler3.unregisterAll();
                            imageButton2.setImageDrawable(null);
                            imageButton2.setContentDescription(null);
                            imageButton2.setEnabled(false);
                            imageButton2.setBackground(null);
                        }
                        mediaControlPanel.setSemanticButtonVisibleAndAlpha(imageButton2.getId(), mediaAction, mediaButton);
                        return Unit.INSTANCE;
                    }
                };
                if (animationBindHandler2.isAnimationRunning()) {
                    ((ArrayList) animationBindHandler2.onAnimationsComplete).add(function0);
                } else {
                    function0.invoke();
                }
            }
            i = 1;
        } else {
            Iterator it3 = SEMANTIC_ACTIONS_COMPACT.iterator();
            while (it3.hasNext()) {
                int iIntValue2 = ((Integer) it3.next()).intValue();
                setVisibleAndAlpha(constraintSet2, iIntValue2, false);
                setVisibleAndAlpha(constraintSet, iIntValue2, false);
            }
            List list = mediaData.actionsToShowInCompact;
            List notificationActions = MediaActionsKt.getNotificationActions(mediaData.actions, this.mActivityStarter);
            int i7 = 0;
            while (true) {
                ArrayList arrayList2 = (ArrayList) notificationActions;
                if (i7 >= arrayList2.size() || i7 >= arrayList.size()) {
                    break;
                }
                boolean zContains = list.contains(Integer.valueOf(i7));
                int i8 = i4;
                ImageButton imageButton2 = (ImageButton) arrayList.get(i7);
                MediaAction mediaAction = (MediaAction) arrayList2.get(i7);
                bindButtonCommon(imageButton2, mediaAction);
                boolean z6 = mediaAction != null ? i8 : i3;
                setVisibleAndAlpha(constraintSet, imageButton2.getId(), z6);
                setVisibleAndAlpha(constraintSet2, imageButton2.getId(), (z6 == 0 || !zContains) ? 0 : i8);
                i7++;
                i4 = i8;
                i3 = 0;
            }
            i = i4;
            while (i7 < arrayList.size()) {
                ImageButton imageButton3 = (ImageButton) arrayList.get(i7);
                bindButtonCommon(imageButton3, null);
                setVisibleAndAlpha(constraintSet, imageButton3.getId(), false);
                setVisibleAndAlpha(constraintSet2, imageButton3.getId(), false);
                i7++;
            }
        }
        updateSeekBarVisibility();
        MediaViewHolder mediaViewHolder2 = this.mMediaViewHolder;
        final TextView textView3 = mediaViewHolder2.titleText;
        final TextView textView4 = mediaViewHolder2.artistText;
        final ConstraintSet constraintSet3 = mediaViewController.expandedLayout;
        final ConstraintSet constraintSet4 = mediaViewController.collapsedLayout;
        MetadataAnimationHandler metadataAnimationHandler = this.mMetadataAnimationHandler;
        Triple triple = new Triple(mediaData.song, mediaData.artist, Boolean.valueOf(mediaData.isExplicit));
        Function0 function02 = new Function0() { // from class: com.android.systemui.media.controls.ui.controller.MediaControlPanel$$ExternalSyntheticLambda9
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                TextView textView5 = textView3;
                TextView textView6 = textView4;
                Intent intent = MediaControlPanel.SETTINGS_INTENT;
                MediaControlPanel mediaControlPanel = this.f$0;
                mediaControlPanel.getClass();
                MediaData mediaData2 = mediaData;
                textView5.setText(mediaData2.song);
                textView6.setText(mediaData2.artist);
                ConstraintSet constraintSet5 = constraintSet3;
                boolean z7 = mediaData2.isExplicit;
                MediaControlPanel.setVisibleAndAlpha(constraintSet5, R.id.media_explicit_indicator, z7);
                MediaControlPanel.setVisibleAndAlpha(constraintSet4, R.id.media_explicit_indicator, z7);
                mediaControlPanel.mMediaViewController.refreshState();
                return Unit.INSTANCE;
            }
        };
        MediaControlPanel$$ExternalSyntheticLambda1 mediaControlPanel$$ExternalSyntheticLambda1 = new MediaControlPanel$$ExternalSyntheticLambda1(this, i);
        if (triple.equals(metadataAnimationHandler.targetData)) {
            z = false;
        } else {
            metadataAnimationHandler.targetData = triple;
            metadataAnimationHandler.postExitUpdate = function02;
            metadataAnimationHandler.postEnterUpdate = mediaControlPanel$$ExternalSyntheticLambda1;
            if (!metadataAnimationHandler.isRunning()) {
                metadataAnimationHandler.exitAnimator.start();
            }
            z = true;
        }
        final int iHashCode = mediaData.hashCode();
        final String str2 = "MediaControlPanel#bindArtworkAndColors<" + str + ">";
        Trace.beginAsyncSection(str2, iHashCode);
        final int i9 = this.mArtworkNextBindRequestId;
        this.mArtworkNextBindRequestId = i9 + 1;
        if (z) {
            this.mIsArtworkBound = false;
        }
        final int measuredWidth = this.mMediaViewHolder.albumView.getMeasuredWidth();
        final int measuredHeight = this.mMediaViewHolder.albumView.getMeasuredHeight();
        this.mBackgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.ui.controller.MediaControlPanel$$ExternalSyntheticLambda16
            @Override // java.lang.Runnable
            public final void run() {
                Drawable colorDrawable;
                final ColorScheme colorScheme2;
                final boolean z7;
                final MediaControlPanel mediaControlPanel = this.f$0;
                final MediaData mediaData2 = mediaData;
                final int i10 = measuredWidth;
                final int i11 = measuredHeight;
                final int i12 = i9;
                final String str3 = str2;
                final int i13 = iHashCode;
                final boolean z8 = z;
                Intent intent = MediaControlPanel.SETTINGS_INTENT;
                mediaControlPanel.getClass();
                Icon icon = mediaData2.artwork;
                String str4 = mediaData2.packageName;
                WallpaperColors wallpaperColor = mediaControlPanel.getWallpaperColor(icon);
                boolean z9 = false;
                if (wallpaperColor == null) {
                    colorDrawable = new ColorDrawable(0);
                    try {
                        z7 = false;
                        colorScheme2 = new ColorScheme(WallpaperColors.fromDrawable(mediaControlPanel.mContext.getPackageManager().getApplicationIcon(str4)), false, 6);
                    } catch (PackageManager.NameNotFoundException e) {
                        Log.w("MediaControlPanel", "Cannot find icon for package " + str4, e);
                        colorScheme2 = null;
                    }
                    final Drawable drawable2 = colorDrawable;
                    mediaControlPanel.mMainExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.ui.controller.MediaControlPanel$$ExternalSyntheticLambda18
                        @Override // java.lang.Runnable
                        public final void run() {
                            TurbulenceNoiseShader turbulenceNoiseShader;
                            boolean z10;
                            int iIntValue3;
                            boolean z11;
                            MediaControlPanel mediaControlPanel2 = mediaControlPanel;
                            int i14 = i12;
                            String str5 = str3;
                            int i15 = i13;
                            ColorScheme colorScheme3 = colorScheme2;
                            boolean z12 = z8;
                            boolean z13 = z7;
                            Drawable drawable3 = drawable2;
                            int i16 = i10;
                            int i17 = i11;
                            MediaData mediaData3 = mediaData2;
                            if (i14 < mediaControlPanel2.mArtworkBoundId) {
                                Trace.endAsyncSection(str5, i15);
                                return;
                            }
                            mediaControlPanel2.mArtworkBoundId = i14;
                            ColorSchemeTransition colorSchemeTransition = mediaControlPanel2.mColorSchemeTransition;
                            AnimatingColorTransition[] animatingColorTransitionArr = {(AnimatingColorTransition) colorSchemeTransition.backgroundColor$delegate.getValue(), colorSchemeTransition.getPrimaryColor(), (AnimatingColorTransition) colorSchemeTransition.onPrimaryColor$delegate.getValue()};
                            int length = animatingColorTransitionArr.length;
                            int i18 = 0;
                            boolean z14 = false;
                            while (i18 < length) {
                                AnimatingColorTransition animatingColorTransition = animatingColorTransitionArr[i18];
                                if (colorScheme3 == null) {
                                    z10 = z12;
                                    iIntValue3 = animatingColorTransition.defaultColor;
                                } else {
                                    z10 = z12;
                                    iIntValue3 = ((Number) animatingColorTransition.extractColor.mo781invoke(colorScheme3)).intValue();
                                }
                                AnimatingColorTransition[] animatingColorTransitionArr2 = animatingColorTransitionArr;
                                if (iIntValue3 != animatingColorTransition.targetColor) {
                                    animatingColorTransition.sourceColor = animatingColorTransition.currentColor;
                                    animatingColorTransition.targetColor = iIntValue3;
                                    animatingColorTransition.valueAnimator.cancel();
                                    animatingColorTransition.valueAnimator.start();
                                    z11 = true;
                                } else {
                                    z11 = false;
                                }
                                if (!animatingColorTransition.equals((AnimatingColorTransition) colorSchemeTransition.colorSeamless$delegate.getValue())) {
                                    z14 = z11 || z14;
                                }
                                i18++;
                                z12 = z10;
                                animatingColorTransitionArr = animatingColorTransitionArr2;
                            }
                            boolean z15 = z12;
                            int i19 = colorSchemeTransition.getPrimaryColor().targetColor;
                            ArrayList arrayList3 = colorSchemeTransition.multiRippleController.multipleRippleView.ripples;
                            int size2 = arrayList3.size();
                            int i20 = 0;
                            while (i20 < size2) {
                                Object obj2 = arrayList3.get(i20);
                                i20++;
                                RippleAnimation rippleAnimation = (RippleAnimation) obj2;
                                rippleAnimation.config.color = i19;
                                rippleAnimation.applyConfigToShader();
                                arrayList3 = arrayList3;
                            }
                            TurbulenceNoiseController turbulenceNoiseController = colorSchemeTransition.turbulenceNoiseController;
                            if (turbulenceNoiseController.state != TurbulenceNoiseController.Companion.AnimationState.NOT_PLAYING && (turbulenceNoiseShader = turbulenceNoiseController.turbulenceNoiseView.turbulenceNoiseShader) != null) {
                                turbulenceNoiseShader.setColorUniform("in_color", i19);
                            }
                            LoadingEffect loadingEffect = colorSchemeTransition.loadingEffect;
                            if (loadingEffect != null) {
                                loadingEffect.turbulenceNoiseShader.setColorUniform("in_color", i19);
                            }
                            MediaViewHolder mediaViewHolder3 = colorSchemeTransition.mediaViewHolder;
                            mediaViewHolder3.gutsViewHolder.setTextColor(colorSchemeTransition.context.getColor(R.color.media_on_background));
                            if (colorScheme3 != null) {
                                mediaViewHolder3.gutsViewHolder.setColors(colorScheme3);
                            }
                            ImageView imageView2 = mediaControlPanel2.mMediaViewHolder.albumView;
                            imageView2.setPadding(0, 0, 0, 0);
                            if (z15 || z14 || (!mediaControlPanel2.mIsArtworkBound && z13)) {
                                if (mediaControlPanel2.mPrevArtwork == null) {
                                    imageView2.setImageDrawable(drawable3);
                                } else {
                                    TransitionDrawable transitionDrawable = new TransitionDrawable(new Drawable[]{mediaControlPanel2.mPrevArtwork, drawable3});
                                    MediaControlPanel.scaleTransitionDrawableLayer(transitionDrawable, 0, i16, i17);
                                    MediaControlPanel.scaleTransitionDrawableLayer(transitionDrawable, 1, i16, i17);
                                    transitionDrawable.setLayerGravity(0, 17);
                                    transitionDrawable.setLayerGravity(1, 17);
                                    transitionDrawable.setCrossFadeEnabled(true);
                                    imageView2.setImageDrawable(transitionDrawable);
                                    transitionDrawable.startTransition(z13 ? 333 : 80);
                                }
                                mediaControlPanel2.mPrevArtwork = drawable3;
                                mediaControlPanel2.mIsArtworkBound = z13;
                            }
                            ImageView imageView3 = mediaControlPanel2.mMediaViewHolder.appIcon;
                            imageView3.clearColorFilter();
                            Icon icon2 = mediaData3.appIcon;
                            String str6 = mediaData3.packageName;
                            if (icon2 == null || mediaData3.resumption) {
                                ColorMatrix colorMatrix = new ColorMatrix();
                                colorMatrix.setSaturation(0.0f);
                                imageView3.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
                                try {
                                    imageView3.setImageDrawable(mediaControlPanel2.mContext.getPackageManager().getApplicationIcon(str6));
                                } catch (PackageManager.NameNotFoundException e2) {
                                    Log.w("MediaControlPanel", "Cannot find icon for package " + str6, e2);
                                    imageView3.setImageResource(R.drawable.ic_music_note);
                                }
                            } else {
                                imageView3.setImageIcon(icon2);
                                imageView3.setColorFilter(mediaControlPanel2.mColorSchemeTransition.getPrimaryColor().targetColor);
                            }
                            Trace.endAsyncSection(str5, i15);
                        }
                    });
                }
                colorScheme2 = new ColorScheme(wallpaperColor, false, 6);
                colorDrawable = mediaControlPanel.addGradientToPlayerAlbum(icon, colorScheme2, i10, i11);
                z9 = true;
                z7 = z9;
                final Drawable drawable22 = colorDrawable;
                mediaControlPanel.mMainExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.ui.controller.MediaControlPanel$$ExternalSyntheticLambda18
                    @Override // java.lang.Runnable
                    public final void run() {
                        TurbulenceNoiseShader turbulenceNoiseShader;
                        boolean z10;
                        int iIntValue3;
                        boolean z11;
                        MediaControlPanel mediaControlPanel2 = mediaControlPanel;
                        int i14 = i12;
                        String str5 = str3;
                        int i15 = i13;
                        ColorScheme colorScheme3 = colorScheme2;
                        boolean z12 = z8;
                        boolean z13 = z7;
                        Drawable drawable3 = drawable22;
                        int i16 = i10;
                        int i17 = i11;
                        MediaData mediaData3 = mediaData2;
                        if (i14 < mediaControlPanel2.mArtworkBoundId) {
                            Trace.endAsyncSection(str5, i15);
                            return;
                        }
                        mediaControlPanel2.mArtworkBoundId = i14;
                        ColorSchemeTransition colorSchemeTransition = mediaControlPanel2.mColorSchemeTransition;
                        AnimatingColorTransition[] animatingColorTransitionArr = {(AnimatingColorTransition) colorSchemeTransition.backgroundColor$delegate.getValue(), colorSchemeTransition.getPrimaryColor(), (AnimatingColorTransition) colorSchemeTransition.onPrimaryColor$delegate.getValue()};
                        int length = animatingColorTransitionArr.length;
                        int i18 = 0;
                        boolean z14 = false;
                        while (i18 < length) {
                            AnimatingColorTransition animatingColorTransition = animatingColorTransitionArr[i18];
                            if (colorScheme3 == null) {
                                z10 = z12;
                                iIntValue3 = animatingColorTransition.defaultColor;
                            } else {
                                z10 = z12;
                                iIntValue3 = ((Number) animatingColorTransition.extractColor.mo781invoke(colorScheme3)).intValue();
                            }
                            AnimatingColorTransition[] animatingColorTransitionArr2 = animatingColorTransitionArr;
                            if (iIntValue3 != animatingColorTransition.targetColor) {
                                animatingColorTransition.sourceColor = animatingColorTransition.currentColor;
                                animatingColorTransition.targetColor = iIntValue3;
                                animatingColorTransition.valueAnimator.cancel();
                                animatingColorTransition.valueAnimator.start();
                                z11 = true;
                            } else {
                                z11 = false;
                            }
                            if (!animatingColorTransition.equals((AnimatingColorTransition) colorSchemeTransition.colorSeamless$delegate.getValue())) {
                                z14 = z11 || z14;
                            }
                            i18++;
                            z12 = z10;
                            animatingColorTransitionArr = animatingColorTransitionArr2;
                        }
                        boolean z15 = z12;
                        int i19 = colorSchemeTransition.getPrimaryColor().targetColor;
                        ArrayList arrayList3 = colorSchemeTransition.multiRippleController.multipleRippleView.ripples;
                        int size2 = arrayList3.size();
                        int i20 = 0;
                        while (i20 < size2) {
                            Object obj2 = arrayList3.get(i20);
                            i20++;
                            RippleAnimation rippleAnimation = (RippleAnimation) obj2;
                            rippleAnimation.config.color = i19;
                            rippleAnimation.applyConfigToShader();
                            arrayList3 = arrayList3;
                        }
                        TurbulenceNoiseController turbulenceNoiseController = colorSchemeTransition.turbulenceNoiseController;
                        if (turbulenceNoiseController.state != TurbulenceNoiseController.Companion.AnimationState.NOT_PLAYING && (turbulenceNoiseShader = turbulenceNoiseController.turbulenceNoiseView.turbulenceNoiseShader) != null) {
                            turbulenceNoiseShader.setColorUniform("in_color", i19);
                        }
                        LoadingEffect loadingEffect = colorSchemeTransition.loadingEffect;
                        if (loadingEffect != null) {
                            loadingEffect.turbulenceNoiseShader.setColorUniform("in_color", i19);
                        }
                        MediaViewHolder mediaViewHolder3 = colorSchemeTransition.mediaViewHolder;
                        mediaViewHolder3.gutsViewHolder.setTextColor(colorSchemeTransition.context.getColor(R.color.media_on_background));
                        if (colorScheme3 != null) {
                            mediaViewHolder3.gutsViewHolder.setColors(colorScheme3);
                        }
                        ImageView imageView2 = mediaControlPanel2.mMediaViewHolder.albumView;
                        imageView2.setPadding(0, 0, 0, 0);
                        if (z15 || z14 || (!mediaControlPanel2.mIsArtworkBound && z13)) {
                            if (mediaControlPanel2.mPrevArtwork == null) {
                                imageView2.setImageDrawable(drawable3);
                            } else {
                                TransitionDrawable transitionDrawable = new TransitionDrawable(new Drawable[]{mediaControlPanel2.mPrevArtwork, drawable3});
                                MediaControlPanel.scaleTransitionDrawableLayer(transitionDrawable, 0, i16, i17);
                                MediaControlPanel.scaleTransitionDrawableLayer(transitionDrawable, 1, i16, i17);
                                transitionDrawable.setLayerGravity(0, 17);
                                transitionDrawable.setLayerGravity(1, 17);
                                transitionDrawable.setCrossFadeEnabled(true);
                                imageView2.setImageDrawable(transitionDrawable);
                                transitionDrawable.startTransition(z13 ? 333 : 80);
                            }
                            mediaControlPanel2.mPrevArtwork = drawable3;
                            mediaControlPanel2.mIsArtworkBound = z13;
                        }
                        ImageView imageView3 = mediaControlPanel2.mMediaViewHolder.appIcon;
                        imageView3.clearColorFilter();
                        Icon icon2 = mediaData3.appIcon;
                        String str6 = mediaData3.packageName;
                        if (icon2 == null || mediaData3.resumption) {
                            ColorMatrix colorMatrix = new ColorMatrix();
                            colorMatrix.setSaturation(0.0f);
                            imageView3.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
                            try {
                                imageView3.setImageDrawable(mediaControlPanel2.mContext.getPackageManager().getApplicationIcon(str6));
                            } catch (PackageManager.NameNotFoundException e2) {
                                Log.w("MediaControlPanel", "Cannot find icon for package " + str6, e2);
                                imageView3.setImageResource(R.drawable.ic_music_note);
                            }
                        } else {
                            imageView3.setImageIcon(icon2);
                            imageView3.setColorFilter(mediaControlPanel2.mColorSchemeTransition.getPrimaryColor().targetColor);
                        }
                        Trace.endAsyncSection(str5, i15);
                    }
                });
            }
        });
        if (!this.mMetadataAnimationHandler.isRunning()) {
            mediaViewController.refreshState();
        }
        if (this.mButtonClicked && !this.mWasPlaying && isPlaying()) {
            if (this.mTurbulenceNoiseAnimationConfig == null) {
                LoadingEffectView loadingEffectView = this.mMediaViewHolder.loadingEffectView;
                int width = loadingEffectView.getWidth();
                int height = loadingEffectView.getHeight();
                Random random = new Random();
                this.mTurbulenceNoiseAnimationConfig = new TurbulenceNoiseAnimationConfig(2.14f, 0.6f, random.nextFloat(), random.nextFloat(), random.nextFloat(), 0.42f, 0.0f, 0.3f, this.mColorSchemeTransition.getPrimaryColor().targetColor, -16777216, width, height, 30000.0f, 1350.0f, 1350.0f, this.mContext.getResources().getDisplayMetrics().density, 0.26f, 0.09f, false);
            }
            if (this.mLoadingEffect == null) {
                LoadingEffect loadingEffect = new LoadingEffect(TurbulenceNoiseShader.Companion.Type.SIMPLEX_NOISE, this.mTurbulenceNoiseAnimationConfig, this.mNoiseDrawCallback, this.mStateChangedCallback);
                this.mLoadingEffect = loadingEffect;
                this.mColorSchemeTransition.loadingEffect = loadingEffect;
            }
            final LoadingEffect loadingEffect2 = this.mLoadingEffect;
            LoadingEffect.AnimationState animationState = loadingEffect2.state;
            LoadingEffect.AnimationState animationState2 = LoadingEffect.AnimationState.NOT_PLAYING;
            if (animationState == animationState2 && animationState == animationState2) {
                loadingEffect2.setState(LoadingEffect.AnimationState.EASE_IN);
                ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
                valueAnimatorOfFloat.setDuration((long) loadingEffect2.config.easeInDuration);
                TurbulenceNoiseShader turbulenceNoiseShader = loadingEffect2.turbulenceNoiseShader;
                final float f = turbulenceNoiseShader.noiseOffsetX;
                final float f2 = turbulenceNoiseShader.noiseOffsetY;
                final float f3 = turbulenceNoiseShader.noiseOffsetZ;
                valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.surfaceeffects.loadingeffect.LoadingEffect$playEaseIn$1
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        float currentPlayTime = valueAnimator.getCurrentPlayTime() * 0.001f;
                        float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                        LoadingEffect loadingEffect3 = loadingEffect2;
                        TurbulenceNoiseShader turbulenceNoiseShader2 = loadingEffect3.turbulenceNoiseShader;
                        float f4 = f;
                        TurbulenceNoiseAnimationConfig turbulenceNoiseAnimationConfig = loadingEffect3.config;
                        turbulenceNoiseShader2.setNoiseMove((turbulenceNoiseAnimationConfig.noiseMoveSpeedX * currentPlayTime) + f4, (turbulenceNoiseAnimationConfig.noiseMoveSpeedY * currentPlayTime) + f2, (currentPlayTime * turbulenceNoiseAnimationConfig.noiseMoveSpeedZ) + f3);
                        LoadingEffect loadingEffect4 = loadingEffect2;
                        loadingEffect4.turbulenceNoiseShader.setOpacity(fFloatValue * loadingEffect4.config.luminosityMultiplier);
                        LoadingEffect loadingEffect5 = loadingEffect2;
                        PaintDrawCallback paintDrawCallback = loadingEffect5.paintCallback;
                        if (paintDrawCallback != null) {
                            Paint paint = loadingEffect5.paint;
                            paint.getClass();
                            paintDrawCallback.onDraw(paint);
                        }
                    }
                });
                valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.surfaceeffects.loadingeffect.LoadingEffect$playEaseIn$2
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        final LoadingEffect loadingEffect3 = loadingEffect2;
                        loadingEffect3.currentAnimator = null;
                        if (loadingEffect3.state != LoadingEffect.AnimationState.EASE_IN) {
                            return;
                        }
                        loadingEffect3.setState(LoadingEffect.AnimationState.MAIN);
                        ValueAnimator valueAnimatorOfFloat2 = ValueAnimator.ofFloat(0.0f, 1.0f);
                        TurbulenceNoiseAnimationConfig turbulenceNoiseAnimationConfig = loadingEffect3.config;
                        valueAnimatorOfFloat2.setDuration((long) turbulenceNoiseAnimationConfig.maxDuration);
                        TurbulenceNoiseShader turbulenceNoiseShader2 = loadingEffect3.turbulenceNoiseShader;
                        final float f4 = turbulenceNoiseShader2.noiseOffsetX;
                        final float f5 = turbulenceNoiseShader2.noiseOffsetY;
                        final float f6 = turbulenceNoiseShader2.noiseOffsetZ;
                        turbulenceNoiseShader2.setOpacity(turbulenceNoiseAnimationConfig.luminosityMultiplier);
                        valueAnimatorOfFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.surfaceeffects.loadingeffect.LoadingEffect$playMain$1
                            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                                float currentPlayTime = valueAnimator.getCurrentPlayTime() * 0.001f;
                                LoadingEffect loadingEffect4 = loadingEffect3;
                                TurbulenceNoiseShader turbulenceNoiseShader3 = loadingEffect4.turbulenceNoiseShader;
                                float f7 = f4;
                                TurbulenceNoiseAnimationConfig turbulenceNoiseAnimationConfig2 = loadingEffect4.config;
                                turbulenceNoiseShader3.setNoiseMove((turbulenceNoiseAnimationConfig2.noiseMoveSpeedX * currentPlayTime) + f7, (turbulenceNoiseAnimationConfig2.noiseMoveSpeedY * currentPlayTime) + f5, (currentPlayTime * turbulenceNoiseAnimationConfig2.noiseMoveSpeedZ) + f6);
                                LoadingEffect loadingEffect5 = loadingEffect3;
                                PaintDrawCallback paintDrawCallback = loadingEffect5.paintCallback;
                                if (paintDrawCallback != null) {
                                    Paint paint = loadingEffect5.paint;
                                    paint.getClass();
                                    paintDrawCallback.onDraw(paint);
                                }
                            }
                        });
                        valueAnimatorOfFloat2.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.surfaceeffects.loadingeffect.LoadingEffect$playMain$2
                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                            public final void onAnimationEnd(Animator animator2) {
                                LoadingEffect loadingEffect4 = loadingEffect3;
                                loadingEffect4.currentAnimator = null;
                                loadingEffect4.playEaseOut();
                            }
                        });
                        valueAnimatorOfFloat2.start();
                        loadingEffect3.currentAnimator = valueAnimatorOfFloat2;
                    }
                });
                valueAnimatorOfFloat.start();
                loadingEffect2.currentAnimator = valueAnimatorOfFloat;
            }
            LoadingEffect loadingEffect3 = this.mLoadingEffect;
            Objects.requireNonNull(loadingEffect3);
            this.mMainExecutor.executeDelayed(new MediaControlPanel$$ExternalSyntheticLambda3(loadingEffect3, 2), TURBULENCE_NOISE_PLAY_DURATION);
        }
        this.mButtonClicked = false;
        this.mWasPlaying = isPlaying();
        Trace.endSection();
    }

    public final void bindPlayerContentDescription(MediaData mediaData) {
        MediaViewHolder mediaViewHolder = this.mMediaViewHolder;
        if (mediaViewHolder == null) {
            return;
        }
        this.mMediaViewHolder.player.setContentDescription(this.mMediaViewController.isGutsVisible ? mediaViewHolder.gutsViewHolder.gutsText.getText() : mediaData != null ? this.mContext.getString(R.string.controls_media_playing_item_description, mediaData.song, mediaData.artist, mediaData.app) : null);
    }

    public final void bindScrubbingTime(MediaData mediaData) {
        ConstraintSet constraintSet = this.mMediaViewController.expandedLayout;
        int id = this.mMediaViewHolder.scrubbingElapsedTimeView.getId();
        int id2 = this.mMediaViewHolder.scrubbingTotalTimeView.getId();
        MediaButton mediaButton = mediaData.semanticActions;
        boolean z = mediaButton != null && SEMANTIC_ACTIONS_HIDE_WHEN_SCRUBBING.stream().allMatch(new MediaControlPanel$$ExternalSyntheticLambda14(mediaButton)) && this.mIsScrubbing;
        setVisibleAndAlpha(constraintSet, id, z);
        setVisibleAndAlpha(constraintSet, id2, z, 8);
    }

    public final ActivityTransitionAnimator.Controller buildLaunchAnimatorController(TransitionLayout transitionLayout) {
        if (!(transitionLayout.getParent() instanceof ViewGroup)) {
            Log.wtf("MediaControlPanel", "Skipping player animation as it is not attached to a ViewGroup", new Exception());
            return null;
        }
        GhostedViewTransitionAnimatorController ghostedViewTransitionAnimatorController = new GhostedViewTransitionAnimatorController(transitionLayout, 31) { // from class: com.android.systemui.media.controls.ui.controller.MediaControlPanel.3
            @Override // com.android.systemui.animation.GhostedViewTransitionAnimatorController
            public final float getCurrentBottomCornerRadius() {
                return getCurrentTopCornerRadius();
            }

            @Override // com.android.systemui.animation.GhostedViewTransitionAnimatorController
            public final float getCurrentTopCornerRadius() {
                return MediaControlPanel.this.mContext.getResources().getDimension(R.dimen.notification_corner_radius);
            }
        };
        if (this.mMediaViewController.currentEndLocation != 4) {
            return ghostedViewTransitionAnimatorController;
        }
        Boolean bool = Boolean.TRUE;
        CommunalSceneInteractor communalSceneInteractor = this.mCommunalSceneInteractor;
        communalSceneInteractor._isLaunchingWidget.updateState(null, bool);
        return new CommunalTransitionAnimatorController(ghostedViewTransitionAnimatorController, communalSceneInteractor);
    }

    public final void closeGuts(boolean z) {
        MediaViewHolder mediaViewHolder = this.mMediaViewHolder;
        if (mediaViewHolder != null) {
            mediaViewHolder.marquee(MediaViewController.GUTS_ANIMATION_DURATION, false);
        }
        MediaViewController mediaViewController = this.mMediaViewController;
        if (mediaViewController.isGutsVisible) {
            mediaViewController.isGutsVisible = false;
            if (!z) {
                mediaViewController.animateNextStateChange = true;
                mediaViewController.animationDuration = MediaViewController.GUTS_ANIMATION_DURATION;
                mediaViewController.animationDelay = 0L;
            }
            mediaViewController.setCurrentState(mediaViewController.currentStartLocation, mediaViewController.currentEndLocation, mediaViewController.currentTransitionProgress, z, true);
        }
        if (this.mMediaViewHolder != null) {
            bindPlayerContentDescription(this.mMediaData);
        }
    }

    public boolean getListening() {
        return this.mSeekBarViewModel.listening;
    }

    public WallpaperColors getWallpaperColor(Icon icon) {
        if (icon != null) {
            if (icon.getType() == 1 || icon.getType() == 5) {
                Bitmap bitmap = icon.getBitmap();
                if (!bitmap.isRecycled()) {
                    return WallpaperColors.fromBitmap(bitmap);
                }
                Log.d("MediaControlPanel", "Cannot load wallpaper color from a recycled bitmap");
                return null;
            }
            Drawable drawableLoadDrawable = icon.loadDrawable(this.mContext);
            if (drawableLoadDrawable != null) {
                return WallpaperColors.fromDrawable(drawableLoadDrawable);
            }
        }
        return null;
    }

    public final boolean isPlaying() {
        PlaybackState playbackState;
        MediaController mediaController = this.mController;
        return (mediaController == null || (playbackState = mediaController.getPlaybackState()) == null || playbackState.getState() != 3) ? false : true;
    }

    public AnimatorSet loadAnimator(int i, Interpolator interpolator, View... viewArr) {
        ArrayList arrayList = new ArrayList();
        for (View view : viewArr) {
            AnimatorSet animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(this.mContext, i);
            animatorSet.getChildAnimations().get(0).setInterpolator(interpolator);
            animatorSet.setTarget(view);
            arrayList.add(animatorSet);
        }
        AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.playTogether(arrayList);
        return animatorSet2;
    }

    public final void onDestroy() {
        SeekBarObserver seekBarObserver = this.mSeekBarObserver;
        final SeekBarViewModel seekBarViewModel = this.mSeekBarViewModel;
        if (seekBarObserver != null) {
            seekBarViewModel._progress.removeObserver(seekBarObserver);
        }
        if (this.mScrubbingChangeListener.equals(seekBarViewModel.scrubbingChangeListener)) {
            seekBarViewModel.scrubbingChangeListener = null;
        }
        if (this.mEnabledChangeListener.equals(seekBarViewModel.enabledChangeListener)) {
            seekBarViewModel.enabledChangeListener = null;
        }
        if (this.mContentDescriptionListener.equals(seekBarViewModel.contentDescriptionListener)) {
            seekBarViewModel.contentDescriptionListener = null;
        }
        seekBarViewModel.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.ui.viewmodel.SeekBarViewModel$onDestroy$1
            @Override // java.lang.Runnable
            public final void run() {
                seekBarViewModel.setController(null);
                SeekBarViewModel seekBarViewModel2 = seekBarViewModel;
                seekBarViewModel2.playbackState = null;
                SeekBarViewModel.AnonymousClass1 anonymousClass1 = seekBarViewModel2.cancel;
                if (anonymousClass1 != null) {
                    anonymousClass1.run();
                }
                SeekBarViewModel seekBarViewModel3 = seekBarViewModel;
                seekBarViewModel3.cancel = null;
                seekBarViewModel3.scrubbingChangeListener = null;
                seekBarViewModel3.enabledChangeListener = null;
            }
        });
        MediaViewController mediaViewController = this.mMediaViewController;
        mediaViewController.mediaHostStatesManager.controllers.remove(mediaViewController);
        ((ConfigurationControllerImpl) mediaViewController.configurationController).removeCallback(mediaViewController.configurationListener);
    }

    public final void setSemanticButtonVisibleAndAlpha(int i, MediaAction mediaAction, MediaButton mediaButton) {
        int i2;
        MediaViewController mediaViewController = this.mMediaViewController;
        ConstraintSet constraintSet = mediaViewController.collapsedLayout;
        ConstraintSet constraintSet2 = mediaViewController.expandedLayout;
        boolean zContains = SEMANTIC_ACTIONS_COMPACT.contains(Integer.valueOf(i));
        List list = SEMANTIC_ACTIONS_HIDE_WHEN_SCRUBBING;
        boolean z = false;
        boolean z2 = (mediaButton != null && list.stream().allMatch(new MediaControlPanel$$ExternalSyntheticLambda14(mediaButton))) && list.contains(Integer.valueOf(i)) && this.mIsScrubbing;
        boolean z3 = (mediaAction == null || z2) ? false : true;
        if (z2 || !((i == R.id.actionPrev && mediaButton.reservePrev) || (i == R.id.actionNext && mediaButton.reserveNext))) {
            i2 = 8;
        } else {
            this.mMediaViewHolder.getAction(i).setFocusable(z3);
            this.mMediaViewHolder.getAction(i).setClickable(z3);
            i2 = 4;
        }
        setVisibleAndAlpha(constraintSet2, i, z3, i2);
        if (z3 && zContains) {
            z = true;
        }
        setVisibleAndAlpha(constraintSet, i, z);
    }

    public final void updateSeekBarVisibility() {
        ConstraintSet constraintSet = this.mMediaViewController.expandedLayout;
        constraintSet.setVisibility(R.id.media_progress_bar, this.mIsSeekBarEnabled ? 0 : 4);
        constraintSet.setAlpha(R.id.media_progress_bar, this.mIsSeekBarEnabled ? 1.0f : 0.0f);
    }

    public static void setVisibleAndAlpha(ConstraintSet constraintSet, int i, boolean z, int i2) {
        if (z) {
            i2 = 0;
        }
        constraintSet.setVisibility(i, i2);
        constraintSet.setAlpha(i, z ? 1.0f : 0.0f);
    }
}
