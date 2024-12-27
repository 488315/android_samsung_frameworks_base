package com.android.systemui.media.controls.ui.controller;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.app.ActivityOptions;
import android.app.BroadcastOptions;
import android.app.PendingIntent;
import android.app.WallpaperColors;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BlendMode;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.Icon;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.os.Handler;
import android.os.Trace;
import android.os.UserHandle;
import android.util.Log;
import android.util.Pair;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintSet;
import com.android.app.animation.Interpolators;
import com.android.internal.logging.InstanceId;
import com.android.internal.widget.CachingIconView;
import com.android.settingslib.flags.Flags;
import com.android.settingslib.widget.AdaptiveIcon;
import com.android.systemui.ActivityIntentHelper;
import com.android.systemui.R;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.animation.GhostedViewTransitionAnimatorController;
import com.android.systemui.animation.ViewDialogTransitionAnimatorController;
import com.android.systemui.bluetooth.BroadcastDialogController;
import com.android.systemui.broadcast.BroadcastSender;
import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import com.android.systemui.media.controls.shared.model.MediaAction;
import com.android.systemui.media.controls.shared.model.MediaButton;
import com.android.systemui.media.controls.shared.model.MediaData;
import com.android.systemui.media.controls.shared.model.MediaDeviceData;
import com.android.systemui.media.controls.shared.model.SmartspaceMediaData;
import com.android.systemui.media.controls.ui.animation.AnimatingColorTransition;
import com.android.systemui.media.controls.ui.animation.AnimationBindHandler;
import com.android.systemui.media.controls.ui.animation.ColorSchemeTransition;
import com.android.systemui.media.controls.ui.animation.MetadataAnimationHandler;
import com.android.systemui.media.controls.ui.binder.SeekBarObserver;
import com.android.systemui.media.controls.ui.controller.MediaViewController;
import com.android.systemui.media.controls.ui.view.GutsViewHolder;
import com.android.systemui.media.controls.ui.view.GutsViewHolder$marquee$1;
import com.android.systemui.media.controls.ui.view.MediaViewHolder;
import com.android.systemui.media.controls.ui.view.RecommendationViewHolder;
import com.android.systemui.media.controls.ui.viewmodel.SeekBarViewModel;
import com.android.systemui.media.controls.util.MediaDataUtils;
import com.android.systemui.media.controls.util.MediaFlags;
import com.android.systemui.media.controls.util.MediaUiEvent;
import com.android.systemui.media.controls.util.MediaUiEventLogger;
import com.android.systemui.media.dialog.MediaOutputDialogManager;
import com.android.systemui.monet.ColorScheme;
import com.android.systemui.monet.Style;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.surfaceeffects.loadingeffect.LoadingEffectView;
import com.android.systemui.surfaceeffects.ripple.MultiRippleController;
import com.android.systemui.surfaceeffects.ripple.MultiRippleView;
import com.android.systemui.surfaceeffects.ripple.RippleAnimation;
import com.android.systemui.surfaceeffects.ripple.RippleAnimationConfig;
import com.android.systemui.surfaceeffects.ripple.RippleShader;
import com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseAnimationConfig;
import com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseController;
import com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseShader;
import com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseView;
import com.android.systemui.util.ColorUtilKt;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.animation.TransitionLayout;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.settings.GlobalSettings;
import com.android.systemui.util.time.SystemClock;
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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class MediaControlPanel {
    public static final List SEMANTIC_ACTIONS_ALL;
    public static final List SEMANTIC_ACTIONS_COMPACT;
    public static final List SEMANTIC_ACTIONS_HIDE_WHEN_SCRUBBING;
    public static final Intent SETTINGS_INTENT = new Intent("android.settings.ACTION_MEDIA_CONTROLS_SETTINGS");
    static final long TURBULENCE_NOISE_PLAY_DURATION = 7500;
    public final ActivityIntentHelper mActivityIntentHelper;
    public final ActivityStarter mActivityStarter;
    public final Executor mBackgroundExecutor;
    public final BroadcastDialogController mBroadcastDialogController;
    public final BroadcastSender mBroadcastSender;
    public ColorSchemeTransition mColorSchemeTransition;
    public final Context mContext;
    public MediaController mController;
    public final FalsingManager mFalsingManager;
    public final GlobalSettings mGlobalSettings;
    public InstanceId mInstanceId;
    public String mKey;
    public final KeyguardStateController mKeyguardStateController;
    public final NotificationLockscreenUserManager mLockscreenUserManager;
    public final MediaUiEventLogger mLogger;
    public final DelayableExecutor mMainExecutor;
    public final MediaCarouselController mMediaCarouselController;
    public MediaData mMediaData;
    public final Lazy mMediaDataManagerLazy;
    public final MediaFlags mMediaFlags;
    public final MediaOutputDialogManager mMediaOutputDialogManager;
    public final MediaViewController mMediaViewController;
    public MediaViewHolder mMediaViewHolder;
    public MetadataAnimationHandler mMetadataAnimationHandler;
    public MultiRippleController mMultiRippleController;
    public final AnonymousClass1 mNoiseDrawCallback;
    public String mPackageName;
    public final SmartspaceMediaData mRecommendationData;
    public RecommendationViewHolder mRecommendationViewHolder;
    public SeekBarObserver mSeekBarObserver;
    public final SeekBarViewModel mSeekBarViewModel;
    public final AnonymousClass2 mStateChangedCallback;
    public final SystemClock mSystemClock;
    public MediaSession.Token mToken;
    public TurbulenceNoiseAnimationConfig mTurbulenceNoiseAnimationConfig;
    public TurbulenceNoiseController mTurbulenceNoiseController;
    public int mUid = -1;
    public Drawable mPrevArtwork = null;
    public boolean mIsArtworkBound = false;
    public int mArtworkBoundId = 0;
    public int mArtworkNextBindRequestId = 0;
    public boolean mIsImpressed = false;
    public int mSmartspaceId = -1;
    public boolean mIsScrubbing = false;
    public boolean mIsSeekBarEnabled = false;
    public final MediaControlPanel$$ExternalSyntheticLambda0 mScrubbingChangeListener = new MediaControlPanel$$ExternalSyntheticLambda0(this);
    public final MediaControlPanel$$ExternalSyntheticLambda0 mEnabledChangeListener = new MediaControlPanel$$ExternalSyntheticLambda0(this);
    public final boolean mIsCurrentBroadcastedApp = false;
    public boolean mWasPlaying = false;
    public boolean mButtonClicked = false;

    static {
        Integer valueOf = Integer.valueOf(R.id.actionPlayPause);
        Integer valueOf2 = Integer.valueOf(R.id.actionPrev);
        Integer valueOf3 = Integer.valueOf(R.id.actionNext);
        SEMANTIC_ACTIONS_COMPACT = List.of(valueOf, valueOf2, valueOf3);
        SEMANTIC_ACTIONS_HIDE_WHEN_SCRUBBING = List.of(valueOf2, valueOf3);
        SEMANTIC_ACTIONS_ALL = List.of(valueOf, valueOf2, valueOf3, Integer.valueOf(R.id.action0), Integer.valueOf(R.id.action1));
    }

    public MediaControlPanel(Context context, Executor executor, DelayableExecutor delayableExecutor, ActivityStarter activityStarter, BroadcastSender broadcastSender, MediaViewController mediaViewController, SeekBarViewModel seekBarViewModel, Lazy lazy, MediaOutputDialogManager mediaOutputDialogManager, MediaCarouselController mediaCarouselController, FalsingManager falsingManager, SystemClock systemClock, MediaUiEventLogger mediaUiEventLogger, KeyguardStateController keyguardStateController, ActivityIntentHelper activityIntentHelper, NotificationLockscreenUserManager notificationLockscreenUserManager, BroadcastDialogController broadcastDialogController, GlobalSettings globalSettings, MediaFlags mediaFlags) {
        new Object(this) { // from class: com.android.systemui.media.controls.ui.controller.MediaControlPanel.1
            public final /* synthetic */ MediaControlPanel this$0;
        };
        new Object(this) { // from class: com.android.systemui.media.controls.ui.controller.MediaControlPanel.2
            public final /* synthetic */ MediaControlPanel this$0;
        };
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
        this.mSystemClock = systemClock;
        this.mLogger = mediaUiEventLogger;
        this.mKeyguardStateController = keyguardStateController;
        this.mActivityIntentHelper = activityIntentHelper;
        this.mLockscreenUserManager = notificationLockscreenUserManager;
        this.mBroadcastDialogController = broadcastDialogController;
        this.mMediaFlags = mediaFlags;
        seekBarViewModel.logSeek = new MediaControlPanel$$ExternalSyntheticLambda2(this, 0);
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
        float scaleFactor = MediaDataUtils.getScaleFactor(new Pair(Integer.valueOf(intrinsicWidth), Integer.valueOf(intrinsicHeight)), new Pair(Integer.valueOf(i2), Integer.valueOf(i3)));
        if (scaleFactor == 0.0f) {
            return;
        }
        transitionDrawable.setLayerSize(i, (int) (intrinsicWidth * scaleFactor), (int) (scaleFactor * intrinsicHeight));
    }

    public static void setVisibleAndAlpha(ConstraintSet constraintSet, int i, boolean z) {
        setVisibleAndAlpha(constraintSet, i, z, 8);
    }

    public static LayerDrawable setupGradientColorOnDrawable(Drawable drawable, GradientDrawable gradientDrawable, ColorScheme colorScheme, float f) {
        gradientDrawable.setColors(new int[]{ColorUtilKt.getColorWithAlpha(colorScheme.mAccent2.getS700(), f), ColorUtilKt.getColorWithAlpha(colorScheme.mAccent1.getS700(), 1.0f)});
        return new LayerDrawable(new Drawable[]{drawable, gradientDrawable});
    }

    public LayerDrawable addGradientToPlayerAlbum(Icon icon, ColorScheme colorScheme, int i, int i2) {
        return setupGradientColorOnDrawable(getScaledBackground(icon, i, i2), (GradientDrawable) this.mContext.getDrawable(R.drawable.qs_media_scrim).mutate(), colorScheme, 0.25f);
    }

    public LayerDrawable addGradientToRecommendationAlbum(Icon icon, ColorScheme colorScheme, int i, int i2) {
        Drawable drawable = null;
        if (i != 0 && i2 != 0 && icon != null && ((icon.getType() == 1 || icon.getType() == 5) && icon.getBitmap() != null)) {
            drawable = new BitmapDrawable(this.mContext.getResources(), Bitmap.createScaledBitmap(icon.getBitmap(), i, i2, false));
        }
        if (drawable == null) {
            drawable = getScaledBackground(icon, i, i2);
        }
        return setupGradientColorOnDrawable(drawable, (GradientDrawable) this.mContext.getDrawable(R.drawable.qs_media_rec_scrim).mutate(), colorScheme, 0.15f);
    }

    public final void attachPlayer(MediaViewHolder mediaViewHolder) {
        this.mMediaViewHolder = mediaViewHolder;
        SeekBarObserver seekBarObserver = new SeekBarObserver(mediaViewHolder);
        this.mSeekBarObserver = seekBarObserver;
        SeekBarViewModel seekBarViewModel = this.mSeekBarViewModel;
        seekBarViewModel._progress.observeForever(seekBarObserver);
        SeekBar seekBar = mediaViewHolder.seekBar;
        seekBar.setOnSeekBarChangeListener(new SeekBarViewModel.SeekBarChangeListener(seekBarViewModel, seekBarViewModel.falsingManager));
        seekBar.setOnTouchListener(new SeekBarViewModel.SeekBarTouchListener(seekBarViewModel, seekBar));
        seekBarViewModel.scrubbingChangeListener = this.mScrubbingChangeListener;
        seekBarViewModel.enabledChangeListener = this.mEnabledChangeListener;
        MediaViewController mediaViewController = this.mMediaViewController;
        MediaViewController.TYPE type = MediaViewController.TYPE.PLAYER;
        TransitionLayout transitionLayout = mediaViewHolder.player;
        mediaViewController.attach(transitionLayout, type);
        transitionLayout.setOnLongClickListener(new MediaControlPanel$$ExternalSyntheticLambda3(this, 0));
        this.mMediaViewHolder.albumView.setLayerType(2, null);
        MediaViewHolder mediaViewHolder2 = this.mMediaViewHolder;
        TextView textView = mediaViewHolder2.titleText;
        TextView textView2 = mediaViewHolder2.artistText;
        CachingIconView cachingIconView = mediaViewHolder2.explicitIndicator;
        AnimatorSet loadAnimator = loadAnimator(R.anim.media_metadata_enter, Interpolators.EMPHASIZED_DECELERATE, textView, textView2, cachingIconView);
        AnimatorSet loadAnimator2 = loadAnimator(R.anim.media_metadata_exit, Interpolators.EMPHASIZED_ACCELERATE, textView, textView2, cachingIconView);
        this.mMultiRippleController = new MultiRippleController(mediaViewHolder.multiRippleView);
        BlendMode blendMode = BlendMode.SCREEN;
        TurbulenceNoiseView turbulenceNoiseView = mediaViewHolder.turbulenceNoiseView;
        turbulenceNoiseView.paint.setBlendMode(blendMode);
        LoadingEffectView loadingEffectView = mediaViewHolder.loadingEffectView;
        loadingEffectView.blendMode = blendMode;
        loadingEffectView.setVisibility(4);
        TurbulenceNoiseController turbulenceNoiseController = new TurbulenceNoiseController(turbulenceNoiseView);
        this.mTurbulenceNoiseController = turbulenceNoiseController;
        this.mColorSchemeTransition = new ColorSchemeTransition(this.mContext, this.mMediaViewHolder, this.mMultiRippleController, turbulenceNoiseController);
        this.mMetadataAnimationHandler = new MetadataAnimationHandler(loadAnimator2, loadAnimator);
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
            imageButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.media.controls.ui.controller.MediaControlPanel$$ExternalSyntheticLambda14
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    MediaControlPanel mediaControlPanel = MediaControlPanel.this;
                    ImageButton imageButton2 = imageButton;
                    Runnable runnable2 = runnable;
                    Object obj = drawable;
                    Object obj2 = drawable2;
                    if (mediaControlPanel.mFalsingManager.isFalseTap(2)) {
                        return;
                    }
                    mediaControlPanel.mLogger.logTapAction(imageButton2.getId(), mediaControlPanel.mUid, mediaControlPanel.mInstanceId, mediaControlPanel.mPackageName);
                    mediaControlPanel.logSmartspaceCardReported(760, 0, 0);
                    mediaControlPanel.mWasPlaying = mediaControlPanel.isPlaying();
                    mediaControlPanel.mButtonClicked = true;
                    runnable2.run();
                    final MultiRippleController multiRippleController = mediaControlPanel.mMultiRippleController;
                    float width = mediaControlPanel.mMediaViewHolder.multiRippleView.getWidth() * 2;
                    final RippleAnimation rippleAnimation = new RippleAnimation(new RippleAnimationConfig(RippleShader.RippleShape.CIRCLE, 1500L, (imageButton2.getWidth() * 0.5f) + imageButton2.getX(), (imageButton2.getHeight() * 0.5f) + imageButton2.getY(), width, width, mediaControlPanel.mContext.getResources().getDisplayMetrics().density, mediaControlPanel.mColorSchemeTransition.accentPrimary.currentColor, 100, 0.0f, null, null, null, false));
                    MultiRippleView multiRippleView = multiRippleController.multipleRippleView;
                    if (multiRippleView.ripples.size() < 10) {
                        multiRippleView.ripples.add(rippleAnimation);
                        final Runnable runnable3 = new Runnable() { // from class: com.android.systemui.surfaceeffects.ripple.MultiRippleController$play$1
                            @Override // java.lang.Runnable
                            public final void run() {
                                MultiRippleController.this.multipleRippleView.ripples.remove(rippleAnimation);
                            }
                        };
                        if (!rippleAnimation.animator.isRunning()) {
                            rippleAnimation.animator.setDuration(rippleAnimation.config.duration);
                            rippleAnimation.animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.surfaceeffects.ripple.RippleAnimation$play$1
                                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                                    long currentPlayTime = valueAnimator.getCurrentPlayTime();
                                    float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                                    RippleAnimation.this.rippleShader.setRawProgress(floatValue);
                                    RippleAnimation rippleAnimation2 = RippleAnimation.this;
                                    rippleAnimation2.rippleShader.setDistortionStrength(rippleAnimation2.config.shouldDistort ? 1 - floatValue : 0.0f);
                                    RippleAnimation.this.rippleShader.setFloatUniform("in_time", currentPlayTime);
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

    public final void bindPlayer(final MediaData mediaData, final String str) {
        boolean z;
        AnimationBindHandler animationBindHandler;
        Double d;
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
        int i = mediaData.appUid;
        this.mUid = i;
        if (this.mSmartspaceId == -1) {
            this.mSmartspaceId = Math.abs(Math.floorMod(i + ((int) this.mSystemClock.currentTimeMillis()), 8192));
        }
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
        final PendingIntent pendingIntent = mediaData.clickIntent;
        if (pendingIntent != null) {
            this.mMediaViewHolder.player.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.media.controls.ui.controller.MediaControlPanel$$ExternalSyntheticLambda4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    GhostedViewTransitionAnimatorController ghostedViewTransitionAnimatorController;
                    final MediaControlPanel mediaControlPanel = MediaControlPanel.this;
                    PendingIntent pendingIntent2 = pendingIntent;
                    String str2 = str;
                    if (mediaControlPanel.mFalsingManager.isFalseTap(1) || mediaControlPanel.mMediaViewController.isGutsVisible) {
                        return;
                    }
                    mediaControlPanel.mLogger.logger.logWithInstanceId(MediaUiEvent.MEDIA_TAP_CONTENT_VIEW, mediaControlPanel.mUid, mediaControlPanel.mPackageName, mediaControlPanel.mInstanceId);
                    mediaControlPanel.logSmartspaceCardReported(760, 0, 0);
                    if (((KeyguardStateControllerImpl) mediaControlPanel.mKeyguardStateController).mShowing) {
                        if (mediaControlPanel.mActivityIntentHelper.wouldPendingShowOverLockscreen(((NotificationLockscreenUserManagerImpl) mediaControlPanel.mLockscreenUserManager).mCurrentUserId, pendingIntent2)) {
                            try {
                                ActivityOptions makeBasic = ActivityOptions.makeBasic();
                                makeBasic.setPendingIntentBackgroundActivityStartMode(1);
                                pendingIntent2.send(makeBasic.toBundle());
                                return;
                            } catch (PendingIntent.CanceledException unused) {
                                Log.e("MediaControlPanel", "Pending intent for " + str2 + " was cancelled");
                                return;
                            }
                        }
                    }
                    TransitionLayout transitionLayout = mediaControlPanel.mMediaViewHolder.player;
                    mediaControlPanel.getClass();
                    if (transitionLayout.getParent() instanceof ViewGroup) {
                        ghostedViewTransitionAnimatorController = new GhostedViewTransitionAnimatorController(transitionLayout, 31) { // from class: com.android.systemui.media.controls.ui.controller.MediaControlPanel.3
                            @Override // com.android.systemui.animation.GhostedViewTransitionAnimatorController
                            public final float getCurrentBottomCornerRadius() {
                                return getCurrentTopCornerRadius();
                            }

                            @Override // com.android.systemui.animation.GhostedViewTransitionAnimatorController
                            public final float getCurrentTopCornerRadius() {
                                return MediaControlPanel.this.mContext.getResources().getDimension(R.dimen.notification_corner_radius);
                            }
                        };
                    } else {
                        Log.wtf("MediaControlPanel", "Skipping player animation as it is not attached to a ViewGroup", new Exception());
                        ghostedViewTransitionAnimatorController = null;
                    }
                    mediaControlPanel.mActivityStarter.postStartActivityDismissingKeyguard(pendingIntent2, ghostedViewTransitionAnimatorController);
                }
            });
        }
        boolean z2 = mediaData.resumption;
        if (!z2 || (d = mediaData.resumeProgress) == null) {
            final MediaController mediaController = this.mController;
            final int i2 = 0;
            this.mBackgroundExecutor.execute(new Runnable(this) { // from class: com.android.systemui.media.controls.ui.controller.MediaControlPanel$$ExternalSyntheticLambda5
                public final /* synthetic */ MediaControlPanel f$0;

                {
                    this.f$0 = this;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    switch (i2) {
                        case 0:
                            this.f$0.mSeekBarViewModel.updateController((MediaController) mediaController);
                            break;
                        default:
                            MediaControlPanel mediaControlPanel = this.f$0;
                            MediaData mediaData2 = (MediaData) mediaController;
                            if (mediaControlPanel.mKey == null) {
                                Log.w("MediaControlPanel", "Dismiss media with null notification. Token uid=" + mediaData2.token.getUid());
                                break;
                            } else {
                                mediaControlPanel.closeGuts(false);
                                if (!((MediaDataManager) mediaControlPanel.mMediaDataManagerLazy.get()).dismissMediaData(mediaControlPanel.mKey, MediaViewController.GUTS_ANIMATION_DURATION + 100, true)) {
                                    Log.w("MediaControlPanel", "Manager failed to dismiss media " + mediaControlPanel.mKey);
                                    mediaControlPanel.mMediaCarouselController.removePlayer(mediaControlPanel.mKey, false, false, true);
                                    break;
                                }
                            }
                            break;
                    }
                }
            });
        } else {
            double doubleValue = d.doubleValue();
            SeekBarViewModel seekBarViewModel = this.mSeekBarViewModel;
            seekBarViewModel.getClass();
            seekBarViewModel.set_data(new SeekBarViewModel.Progress(true, false, false, false, Integer.valueOf((int) (doubleValue * 100)), 100, false));
        }
        Flags.legacyLeAudioSharing();
        ViewGroup viewGroup = this.mMediaViewHolder.seamless;
        boolean z3 = false;
        viewGroup.setVisibility(0);
        MediaViewHolder mediaViewHolder = this.mMediaViewHolder;
        ImageView imageView = mediaViewHolder.seamlessIcon;
        TextView textView = mediaViewHolder.seamlessText;
        final MediaDeviceData mediaDeviceData = mediaData.device;
        boolean z4 = !(mediaDeviceData == null || mediaDeviceData.enabled) || z2;
        boolean z5 = !z4;
        CharSequence string = this.mContext.getString(R.string.media_seamless_other_device);
        this.mMediaViewHolder.seamlessButton.setAlpha(z4 ? 0.38f : 1.0f);
        viewGroup.setEnabled(z5);
        if (mediaDeviceData != null) {
            Drawable drawable = mediaDeviceData.icon;
            if (drawable instanceof AdaptiveIcon) {
                AdaptiveIcon adaptiveIcon = (AdaptiveIcon) drawable;
                adaptiveIcon.setBackgroundColor(this.mColorSchemeTransition.bgColor);
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
        viewGroup.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.media.controls.ui.controller.MediaControlPanel$$ExternalSyntheticLambda10
            public final /* synthetic */ boolean f$1 = false;

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MediaControlPanel mediaControlPanel = MediaControlPanel.this;
                boolean z6 = this.f$1;
                MediaDeviceData mediaDeviceData2 = mediaDeviceData;
                if (mediaControlPanel.mFalsingManager.isFalseTap(2)) {
                    return;
                }
                MediaUiEventLogger mediaUiEventLogger = mediaControlPanel.mLogger;
                boolean z7 = false;
                if (!z6) {
                    mediaUiEventLogger.logger.logWithInstanceId(MediaUiEvent.OPEN_OUTPUT_SWITCHER, mediaControlPanel.mUid, mediaControlPanel.mPackageName, mediaControlPanel.mInstanceId);
                    PendingIntent pendingIntent2 = mediaDeviceData2.intent;
                    if (pendingIntent2 == null) {
                        mediaControlPanel.mMediaOutputDialogManager.createAndShow(mediaControlPanel.mPackageName, true, mediaControlPanel.mMediaViewHolder.seamlessButton, UserHandle.getUserHandleForUid(mediaControlPanel.mUid), mediaControlPanel.mToken);
                        return;
                    }
                    if (((KeyguardStateControllerImpl) mediaControlPanel.mKeyguardStateController).mShowing) {
                        if (mediaControlPanel.mActivityIntentHelper.wouldPendingShowOverLockscreen(((NotificationLockscreenUserManagerImpl) mediaControlPanel.mLockscreenUserManager).mCurrentUserId, pendingIntent2)) {
                            z7 = true;
                        }
                    }
                    if (!pendingIntent2.isActivity()) {
                        Log.w("MediaControlPanel", "Device pending intent is not an activity.");
                        return;
                    }
                    if (!z7) {
                        mediaControlPanel.mActivityStarter.postStartActivityDismissingKeyguard(pendingIntent2);
                        return;
                    }
                    try {
                        BroadcastOptions makeBasic = BroadcastOptions.makeBasic();
                        makeBasic.setInteractive(true);
                        makeBasic.setPendingIntentBackgroundActivityStartMode(1);
                        pendingIntent2.send(makeBasic.toBundle());
                        return;
                    } catch (PendingIntent.CanceledException unused) {
                        Log.e("MediaControlPanel", "Device pending intent was canceled");
                        return;
                    }
                }
                if (mediaControlPanel.mIsCurrentBroadcastedApp) {
                    mediaUiEventLogger.logger.logWithInstanceId(MediaUiEvent.OPEN_OUTPUT_SWITCHER, mediaControlPanel.mUid, mediaControlPanel.mPackageName, mediaControlPanel.mInstanceId);
                    mediaControlPanel.mMediaOutputDialogManager.createAndShow(mediaControlPanel.mPackageName, true, mediaControlPanel.mMediaViewHolder.seamlessButton, UserHandle.getUserHandleForUid(mediaControlPanel.mUid), mediaControlPanel.mToken);
                    return;
                }
                mediaUiEventLogger.logger.logWithInstanceId(MediaUiEvent.MEDIA_OPEN_BROADCAST_DIALOG, mediaControlPanel.mUid, mediaControlPanel.mPackageName, mediaControlPanel.mInstanceId);
                String charSequence2 = mediaDeviceData2.name.toString();
                String str2 = mediaControlPanel.mPackageName;
                View view2 = mediaControlPanel.mMediaViewHolder.seamlessButton;
                BroadcastDialogController broadcastDialogController = mediaControlPanel.mBroadcastDialogController;
                SystemUIDialog createDialog = broadcastDialogController.mBroadcastDialogFactory.create(charSequence2, str2).createDialog();
                if (view2 == null) {
                    createDialog.show();
                    return;
                }
                DialogTransitionAnimator dialogTransitionAnimator = broadcastDialogController.mDialogTransitionAnimator;
                dialogTransitionAnimator.getClass();
                DialogTransitionAnimator.Controller.Companion.getClass();
                ViewDialogTransitionAnimatorController fromView = DialogTransitionAnimator.Controller.Companion.fromView(view2, null);
                if (fromView == null) {
                    createDialog.show();
                } else {
                    dialogTransitionAnimator.show(createDialog, fromView, false);
                }
            }
        });
        final int i3 = 1;
        final Runnable runnable = new Runnable(this) { // from class: com.android.systemui.media.controls.ui.controller.MediaControlPanel$$ExternalSyntheticLambda5
            public final /* synthetic */ MediaControlPanel f$0;

            {
                this.f$0 = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                switch (i3) {
                    case 0:
                        this.f$0.mSeekBarViewModel.updateController((MediaController) mediaData);
                        break;
                    default:
                        MediaControlPanel mediaControlPanel = this.f$0;
                        MediaData mediaData2 = (MediaData) mediaData;
                        if (mediaControlPanel.mKey == null) {
                            Log.w("MediaControlPanel", "Dismiss media with null notification. Token uid=" + mediaData2.token.getUid());
                            break;
                        } else {
                            mediaControlPanel.closeGuts(false);
                            if (!((MediaDataManager) mediaControlPanel.mMediaDataManagerLazy.get()).dismissMediaData(mediaControlPanel.mKey, MediaViewController.GUTS_ANIMATION_DURATION + 100, true)) {
                                Log.w("MediaControlPanel", "Manager failed to dismiss media " + mediaControlPanel.mKey);
                                mediaControlPanel.mMediaCarouselController.removePlayer(mediaControlPanel.mKey, false, false, true);
                                break;
                            }
                        }
                        break;
                }
            }
        };
        GutsViewHolder gutsViewHolder = this.mMediaViewHolder.gutsViewHolder;
        String str2 = mediaData.app;
        boolean z6 = mediaData.isClearable;
        gutsViewHolder.gutsText.setText(z6 ? this.mContext.getString(R.string.controls_media_close_session, str2) : this.mContext.getString(R.string.controls_media_active_session));
        gutsViewHolder.dismissText.setVisibility(z6 ? 0 : 8);
        gutsViewHolder.dismiss.setEnabled(z6);
        gutsViewHolder.dismiss.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.media.controls.ui.controller.MediaControlPanel$$ExternalSyntheticLambda20
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MediaControlPanel mediaControlPanel = MediaControlPanel.this;
                Runnable runnable2 = runnable;
                if (mediaControlPanel.mFalsingManager.isFalseTap(1)) {
                    return;
                }
                mediaControlPanel.logSmartspaceCardReported(761, 0, 0);
                mediaControlPanel.mLogger.logger.logWithInstanceId(MediaUiEvent.DISMISS_LONG_PRESS, mediaControlPanel.mUid, mediaControlPanel.mPackageName, mediaControlPanel.mInstanceId);
                runnable2.run();
            }
        });
        TextView textView2 = gutsViewHolder.cancelText;
        if (z6) {
            textView2.setBackground(this.mContext.getDrawable(R.drawable.qs_media_outline_button));
        } else {
            textView2.setBackground(this.mContext.getDrawable(R.drawable.qs_media_solid_button));
        }
        final int i4 = 0;
        gutsViewHolder.cancel.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.systemui.media.controls.ui.controller.MediaControlPanel$$ExternalSyntheticLambda21
            public final /* synthetic */ MediaControlPanel f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                int i5 = i4;
                MediaControlPanel mediaControlPanel = this.f$0;
                switch (i5) {
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
        if (gutsViewHolder.isDismissible != z6) {
            gutsViewHolder.isDismissible = z6;
            ColorScheme colorScheme = gutsViewHolder.colorScheme;
            if (colorScheme != null) {
                gutsViewHolder.colorScheme = colorScheme;
                gutsViewHolder.setSurfaceColor(colorScheme.mAccent2.getS800());
                gutsViewHolder.setTextPrimaryColor(colorScheme.mNeutral1.getS50());
                gutsViewHolder.setAccentPrimaryColor(colorScheme.mAccent1.getS100());
            }
        }
        final int i5 = 1;
        gutsViewHolder.settings.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.systemui.media.controls.ui.controller.MediaControlPanel$$ExternalSyntheticLambda21
            public final /* synthetic */ MediaControlPanel f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                int i52 = i5;
                MediaControlPanel mediaControlPanel = this.f$0;
                switch (i52) {
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
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                ImageButton imageButton = (ImageButton) it2.next();
                setVisibleAndAlpha(constraintSet2, imageButton.getId(), false);
                setVisibleAndAlpha(constraintSet, imageButton.getId(), false);
            }
            Iterator it3 = SEMANTIC_ACTIONS_ALL.iterator();
            while (it3.hasNext()) {
                int intValue = ((Integer) it3.next()).intValue();
                final ImageButton action = this.mMediaViewHolder.getAction(intValue);
                final MediaAction actionById = mediaButton.getActionById(intValue);
                if (action.getTag() == null) {
                    animationBindHandler = new AnimationBindHandler();
                    action.setTag(animationBindHandler);
                } else {
                    animationBindHandler = (AnimationBindHandler) action.getTag();
                }
                final AnimationBindHandler animationBindHandler2 = animationBindHandler;
                Function0 function0 = new Function0() { // from class: com.android.systemui.media.controls.ui.controller.MediaControlPanel$$ExternalSyntheticLambda16
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        ImageButton imageButton2 = action;
                        MediaControlPanel mediaControlPanel = MediaControlPanel.this;
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
        } else {
            Iterator it4 = SEMANTIC_ACTIONS_COMPACT.iterator();
            while (it4.hasNext()) {
                int intValue2 = ((Integer) it4.next()).intValue();
                setVisibleAndAlpha(constraintSet2, intValue2, false);
                setVisibleAndAlpha(constraintSet, intValue2, false);
            }
            List list = mediaData.actionsToShowInCompact;
            List list2 = mediaData.actions;
            int i6 = 0;
            while (i6 < list2.size() && i6 < arrayList.size()) {
                boolean contains = list.contains(Integer.valueOf(i6));
                ImageButton imageButton2 = (ImageButton) arrayList.get(i6);
                MediaAction mediaAction = (MediaAction) list2.get(i6);
                bindButtonCommon(imageButton2, mediaAction);
                boolean z7 = mediaAction != null ? true : z3;
                setVisibleAndAlpha(constraintSet, imageButton2.getId(), z7);
                setVisibleAndAlpha(constraintSet2, imageButton2.getId(), z7 && contains);
                i6++;
                z3 = false;
            }
            while (i6 < arrayList.size()) {
                ImageButton imageButton3 = (ImageButton) arrayList.get(i6);
                bindButtonCommon(imageButton3, null);
                setVisibleAndAlpha(constraintSet, imageButton3.getId(), false);
                setVisibleAndAlpha(constraintSet2, imageButton3.getId(), false);
                i6++;
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
        Function0 function02 = new Function0() { // from class: com.android.systemui.media.controls.ui.controller.MediaControlPanel$$ExternalSyntheticLambda7
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                TextView textView5 = textView3;
                TextView textView6 = textView4;
                MediaControlPanel mediaControlPanel = MediaControlPanel.this;
                mediaControlPanel.getClass();
                MediaData mediaData2 = mediaData;
                textView5.setText(mediaData2.song);
                textView6.setText(mediaData2.artist);
                ConstraintSet constraintSet5 = constraintSet3;
                boolean z8 = mediaData2.isExplicit;
                MediaControlPanel.setVisibleAndAlpha(constraintSet5, R.id.media_explicit_indicator, z8);
                MediaControlPanel.setVisibleAndAlpha(constraintSet4, R.id.media_explicit_indicator, z8);
                mediaControlPanel.mMediaViewController.refreshState();
                return Unit.INSTANCE;
            }
        };
        MediaControlPanel$$ExternalSyntheticLambda2 mediaControlPanel$$ExternalSyntheticLambda2 = new MediaControlPanel$$ExternalSyntheticLambda2(this, 2);
        if (triple.equals(metadataAnimationHandler.targetData)) {
            z = false;
        } else {
            metadataAnimationHandler.targetData = triple;
            metadataAnimationHandler.postExitUpdate = function02;
            metadataAnimationHandler.postEnterUpdate = mediaControlPanel$$ExternalSyntheticLambda2;
            if (!metadataAnimationHandler.isRunning()) {
                metadataAnimationHandler.exitAnimator.start();
            }
            z = true;
        }
        final int hashCode = mediaData.hashCode();
        final String str3 = "MediaControlPanel#bindArtworkAndColors<" + str + ">";
        Trace.beginAsyncSection(str3, hashCode);
        final int i7 = this.mArtworkNextBindRequestId;
        this.mArtworkNextBindRequestId = i7 + 1;
        if (z) {
            this.mIsArtworkBound = false;
        }
        final int measuredWidth = this.mMediaViewHolder.albumView.getMeasuredWidth();
        final int measuredHeight = this.mMediaViewHolder.albumView.getMeasuredHeight();
        this.mMediaFlags.getClass();
        com.android.systemui.Flags.sceneContainer();
        final boolean z8 = z;
        this.mBackgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.ui.controller.MediaControlPanel$$ExternalSyntheticLambda15
            @Override // java.lang.Runnable
            public final void run() {
                final Drawable drawable2;
                final ColorScheme colorScheme2;
                final boolean z9;
                final MediaControlPanel mediaControlPanel = MediaControlPanel.this;
                final MediaData mediaData2 = mediaData;
                final int i8 = measuredWidth;
                final int i9 = measuredHeight;
                final int i10 = i7;
                final String str4 = str3;
                final int i11 = hashCode;
                final boolean z10 = z8;
                mediaControlPanel.getClass();
                Icon icon = mediaData2.artwork;
                String str5 = mediaData2.packageName;
                WallpaperColors wallpaperColor = mediaControlPanel.getWallpaperColor(icon);
                if (wallpaperColor != null) {
                    ColorScheme colorScheme3 = new ColorScheme(wallpaperColor, true, Style.CONTENT);
                    drawable2 = mediaControlPanel.addGradientToPlayerAlbum(icon, colorScheme3, i8, i9);
                    z9 = true;
                    colorScheme2 = colorScheme3;
                } else {
                    Drawable colorDrawable = new ColorDrawable(0);
                    try {
                        z9 = false;
                        colorScheme2 = new ColorScheme(WallpaperColors.fromDrawable(mediaControlPanel.mContext.getPackageManager().getApplicationIcon(str5)), true, Style.CONTENT);
                        drawable2 = colorDrawable;
                    } catch (PackageManager.NameNotFoundException e) {
                        Log.w("MediaControlPanel", "Cannot find icon for package " + str5, e);
                        drawable2 = colorDrawable;
                        colorScheme2 = null;
                        z9 = false;
                    }
                }
                mediaControlPanel.mMainExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.ui.controller.MediaControlPanel$$ExternalSyntheticLambda19
                    @Override // java.lang.Runnable
                    public final void run() {
                        AnimatingColorTransition[] animatingColorTransitionArr;
                        int intValue3;
                        boolean z11;
                        MediaControlPanel mediaControlPanel2 = MediaControlPanel.this;
                        int i12 = i10;
                        String str6 = str4;
                        int i13 = i11;
                        ColorScheme colorScheme4 = colorScheme2;
                        boolean z12 = z10;
                        boolean z13 = z9;
                        Drawable drawable3 = drawable2;
                        int i14 = i8;
                        int i15 = i9;
                        MediaData mediaData3 = mediaData2;
                        if (i12 < mediaControlPanel2.mArtworkBoundId) {
                            Trace.endAsyncSection(str6, i13);
                            return;
                        }
                        mediaControlPanel2.mArtworkBoundId = i12;
                        ColorSchemeTransition colorSchemeTransition = mediaControlPanel2.mColorSchemeTransition;
                        AnimatingColorTransition[] animatingColorTransitionArr2 = colorSchemeTransition.colorTransitions;
                        int length = animatingColorTransitionArr2.length;
                        int i16 = 0;
                        boolean z14 = false;
                        while (i16 < length) {
                            AnimatingColorTransition animatingColorTransition = animatingColorTransitionArr2[i16];
                            if (colorScheme4 == null) {
                                animatingColorTransitionArr = animatingColorTransitionArr2;
                                intValue3 = animatingColorTransition.defaultColor;
                            } else {
                                animatingColorTransitionArr = animatingColorTransitionArr2;
                                intValue3 = ((Number) animatingColorTransition.extractColor.invoke(colorScheme4)).intValue();
                            }
                            int i17 = length;
                            if (intValue3 != animatingColorTransition.targetColor) {
                                animatingColorTransition.sourceColor = animatingColorTransition.currentColor;
                                animatingColorTransition.targetColor = intValue3;
                                animatingColorTransition.valueAnimator.cancel();
                                animatingColorTransition.valueAnimator.start();
                                z11 = true;
                            } else {
                                z11 = false;
                            }
                            if (!animatingColorTransition.equals(colorSchemeTransition.colorSeamless)) {
                                z14 = z11 || z14;
                            }
                            i16++;
                            animatingColorTransitionArr2 = animatingColorTransitionArr;
                            length = i17;
                        }
                        if (colorScheme4 != null) {
                            colorSchemeTransition.mediaViewHolder.gutsViewHolder.colorScheme = colorScheme4;
                        }
                        ImageView imageView2 = mediaControlPanel2.mMediaViewHolder.albumView;
                        imageView2.setPadding(0, 0, 0, 0);
                        if (z12 || z14 || (!mediaControlPanel2.mIsArtworkBound && z13)) {
                            if (mediaControlPanel2.mPrevArtwork == null) {
                                imageView2.setImageDrawable(drawable3);
                            } else {
                                TransitionDrawable transitionDrawable = new TransitionDrawable(new Drawable[]{mediaControlPanel2.mPrevArtwork, drawable3});
                                MediaControlPanel.scaleTransitionDrawableLayer(transitionDrawable, 0, i14, i15);
                                MediaControlPanel.scaleTransitionDrawableLayer(transitionDrawable, 1, i14, i15);
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
                        String str7 = mediaData3.packageName;
                        if (icon2 == null || mediaData3.resumption) {
                            ColorMatrix colorMatrix = new ColorMatrix();
                            colorMatrix.setSaturation(0.0f);
                            imageView3.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
                            try {
                                imageView3.setImageDrawable(mediaControlPanel2.mContext.getPackageManager().getApplicationIcon(str7));
                            } catch (PackageManager.NameNotFoundException e2) {
                                Log.w("MediaControlPanel", "Cannot find icon for package " + str7, e2);
                                imageView3.setImageResource(R.drawable.ic_music_note);
                            }
                        } else {
                            imageView3.setImageIcon(icon2);
                            imageView3.setColorFilter(mediaControlPanel2.mColorSchemeTransition.accentPrimary.targetColor);
                        }
                        Trace.endAsyncSection(str6, i13);
                    }
                });
            }
        });
        if (!this.mMetadataAnimationHandler.isRunning()) {
            com.android.systemui.Flags.sceneContainer();
            mediaViewController.refreshState();
        }
        if (this.mButtonClicked && !this.mWasPlaying && isPlaying()) {
            if (this.mTurbulenceNoiseAnimationConfig == null) {
                com.android.systemui.Flags.FEATURE_FLAGS.getClass();
                TurbulenceNoiseView turbulenceNoiseView = this.mMediaViewHolder.turbulenceNoiseView;
                int width = turbulenceNoiseView.getWidth();
                int height = turbulenceNoiseView.getHeight();
                Random random = new Random();
                this.mTurbulenceNoiseAnimationConfig = new TurbulenceNoiseAnimationConfig(2.14f, 1.0f, random.nextFloat(), random.nextFloat(), random.nextFloat(), 0.42f, 0.0f, 0.3f, this.mColorSchemeTransition.accentPrimary.currentColor, -16777216, width, height, 30000.0f, 1350.0f, 1350.0f, this.mContext.getResources().getDisplayMetrics().density, 0.26f, 0.09f, false);
            }
            com.android.systemui.Flags.FEATURE_FLAGS.getClass();
            final TurbulenceNoiseController turbulenceNoiseController = this.mTurbulenceNoiseController;
            TurbulenceNoiseShader.Companion.Type type = TurbulenceNoiseShader.Companion.Type.SIMPLEX_NOISE;
            TurbulenceNoiseAnimationConfig turbulenceNoiseAnimationConfig = this.mTurbulenceNoiseAnimationConfig;
            TurbulenceNoiseController.Companion.AnimationState animationState = turbulenceNoiseController.state;
            TurbulenceNoiseController.Companion.AnimationState animationState2 = TurbulenceNoiseController.Companion.AnimationState.NOT_PLAYING;
            if (animationState == animationState2) {
                TurbulenceNoiseView turbulenceNoiseView2 = turbulenceNoiseController.turbulenceNoiseView;
                turbulenceNoiseView2.initShader(type, turbulenceNoiseAnimationConfig);
                if (turbulenceNoiseController.state == animationState2) {
                    turbulenceNoiseController.setState(TurbulenceNoiseController.Companion.AnimationState.EASE_IN);
                    turbulenceNoiseView2.playEaseIn(new Runnable() { // from class: com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseController$playEaseInAnimation$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            final TurbulenceNoiseController turbulenceNoiseController2 = TurbulenceNoiseController.this;
                            if (turbulenceNoiseController2.state != TurbulenceNoiseController.Companion.AnimationState.EASE_IN) {
                                return;
                            }
                            turbulenceNoiseController2.setState(TurbulenceNoiseController.Companion.AnimationState.MAIN);
                            turbulenceNoiseController2.turbulenceNoiseView.play(new Runnable() { // from class: com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseController$playMainAnimation$1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    TurbulenceNoiseController turbulenceNoiseController3 = TurbulenceNoiseController.this;
                                    if (turbulenceNoiseController3.state != TurbulenceNoiseController.Companion.AnimationState.MAIN) {
                                        return;
                                    }
                                    turbulenceNoiseController3.setState(TurbulenceNoiseController.Companion.AnimationState.EASE_OUT);
                                    turbulenceNoiseController3.turbulenceNoiseView.playEaseOut(new TurbulenceNoiseController$playEaseOutAnimation$1(turbulenceNoiseController3));
                                }
                            });
                        }
                    });
                }
            }
            TurbulenceNoiseController turbulenceNoiseController2 = this.mTurbulenceNoiseController;
            Objects.requireNonNull(turbulenceNoiseController2);
            this.mMainExecutor.executeDelayed(new MediaControlPanel$$ExternalSyntheticLambda6(turbulenceNoiseController2, 0), TURBULENCE_NOISE_PLAY_DURATION);
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

    public final void bindRecommendationContentDescription(SmartspaceMediaData smartspaceMediaData) {
        RecommendationViewHolder recommendationViewHolder = this.mRecommendationViewHolder;
        if (recommendationViewHolder == null) {
            return;
        }
        this.mRecommendationViewHolder.recommendations.setContentDescription(this.mMediaViewController.isGutsVisible ? recommendationViewHolder.gutsViewHolder.gutsText.getText() : smartspaceMediaData != null ? this.mContext.getString(R.string.controls_media_smartspace_rec_header) : null);
    }

    public final void bindScrubbingTime(MediaData mediaData) {
        ConstraintSet constraintSet = this.mMediaViewController.expandedLayout;
        int id = this.mMediaViewHolder.scrubbingElapsedTimeView.getId();
        int id2 = this.mMediaViewHolder.scrubbingTotalTimeView.getId();
        MediaButton mediaButton = mediaData.semanticActions;
        boolean z = mediaButton != null && SEMANTIC_ACTIONS_HIDE_WHEN_SCRUBBING.stream().allMatch(new MediaControlPanel$$ExternalSyntheticLambda11(mediaButton)) && this.mIsScrubbing;
        setVisibleAndAlpha(constraintSet, id, z);
        setVisibleAndAlpha(constraintSet, id2, z, 8);
    }

    public final void closeGuts(boolean z) {
        MediaViewHolder mediaViewHolder = this.mMediaViewHolder;
        if (mediaViewHolder != null) {
            long j = MediaViewController.GUTS_ANIMATION_DURATION;
            GutsViewHolder gutsViewHolder = mediaViewHolder.gutsViewHolder;
            Handler handler = gutsViewHolder.gutsText.getHandler();
            if (handler == null) {
                Log.d("MediaViewHolder", "marquee while longPressText.getHandler() is null", new Exception());
            } else {
                handler.postDelayed(new GutsViewHolder$marquee$1(gutsViewHolder, false), j);
            }
        } else {
            RecommendationViewHolder recommendationViewHolder = this.mRecommendationViewHolder;
            if (recommendationViewHolder != null) {
                long j2 = MediaViewController.GUTS_ANIMATION_DURATION;
                GutsViewHolder gutsViewHolder2 = recommendationViewHolder.gutsViewHolder;
                Handler handler2 = gutsViewHolder2.gutsText.getHandler();
                if (handler2 == null) {
                    Log.d("RecommendationViewHolder", "marquee while longPressText.getHandler() is null", new Exception());
                } else {
                    handler2.postDelayed(new GutsViewHolder$marquee$1(gutsViewHolder2, false), j2);
                }
            }
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
        } else if (this.mRecommendationViewHolder != null) {
            bindRecommendationContentDescription(this.mRecommendationData);
        }
    }

    public boolean getListening() {
        return this.mSeekBarViewModel.listening;
    }

    public int getNumberOfFittedRecommendations() {
        Resources resources = this.mContext.getResources();
        Configuration configuration = resources.getConfiguration();
        int integer = resources.getInteger(R.integer.default_qs_media_rec_width_dp);
        int dimensionPixelSize = (resources.getDimensionPixelSize(R.dimen.qs_media_info_spacing) * 2) + resources.getDimensionPixelSize(R.dimen.qs_media_rec_album_width);
        int i = configuration.screenWidthDp;
        if (configuration.orientation == 2) {
            i /= 2;
        }
        return Math.min(i > integer ? resources.getDimensionPixelSize(R.dimen.qs_media_rec_default_width) / dimensionPixelSize : ((int) TypedValue.applyDimension(1, i, resources.getDisplayMetrics())) / dimensionPixelSize, 3);
    }

    public final Drawable getScaledBackground(Icon icon, int i, int i2) {
        if (icon == null) {
            return null;
        }
        Drawable loadDrawable = icon.loadDrawable(this.mContext);
        Rect rect = new Rect(0, 0, i, i2);
        if (rect.width() > i || rect.height() > i2) {
            rect.offset((int) (-((rect.width() - i) / 2.0f)), (int) (-((rect.height() - i2) / 2.0f)));
        }
        loadDrawable.setBounds(rect);
        return loadDrawable;
    }

    public final int getSurfaceForSmartspaceLogging() {
        int i = this.mMediaViewController.currentEndLocation;
        if (i == 1 || i == 0) {
            return 4;
        }
        if (i == 2) {
            return 2;
        }
        return i == 3 ? 5 : 0;
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
            Drawable loadDrawable = icon.loadDrawable(this.mContext);
            if (loadDrawable != null) {
                return WallpaperColors.fromDrawable(loadDrawable);
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

    public final void logSmartspaceCardReported(int i, int i2, int i3) {
        int i4 = this.mSmartspaceId;
        int i5 = this.mUid;
        int[] iArr = {getSurfaceForSmartspaceLogging()};
        MediaCarouselController mediaCarouselController = this.mMediaCarouselController;
        mediaCarouselController.getClass();
        MediaCarouselController.logSmartspaceCardReported$default(mediaCarouselController, i, i4, i5, iArr, i2, i3, 0, false, 448);
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
        seekBarViewModel.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.ui.viewmodel.SeekBarViewModel$onDestroy$1
            @Override // java.lang.Runnable
            public final void run() {
                SeekBarViewModel.this.setController(null);
                SeekBarViewModel seekBarViewModel2 = SeekBarViewModel.this;
                seekBarViewModel2.playbackState = null;
                Runnable runnable = seekBarViewModel2.cancel;
                if (runnable != null) {
                    runnable.run();
                }
                SeekBarViewModel seekBarViewModel3 = SeekBarViewModel.this;
                seekBarViewModel3.cancel = null;
                seekBarViewModel3.scrubbingChangeListener = null;
                seekBarViewModel3.enabledChangeListener = null;
            }
        });
        MediaViewController mediaViewController = this.mMediaViewController;
        mediaViewController.mediaFlags.getClass();
        com.android.systemui.Flags.sceneContainer();
        mediaViewController.mediaHostStatesManager.controllers.remove(mediaViewController);
        ((ConfigurationControllerImpl) mediaViewController.configurationController).removeCallback(mediaViewController.configurationListener);
    }

    public final void openGuts() {
        MediaViewHolder mediaViewHolder = this.mMediaViewHolder;
        if (mediaViewHolder != null) {
            long j = MediaViewController.GUTS_ANIMATION_DURATION;
            GutsViewHolder gutsViewHolder = mediaViewHolder.gutsViewHolder;
            Handler handler = gutsViewHolder.gutsText.getHandler();
            if (handler == null) {
                Log.d("MediaViewHolder", "marquee while longPressText.getHandler() is null", new Exception());
            } else {
                handler.postDelayed(new GutsViewHolder$marquee$1(gutsViewHolder, true), j);
            }
        } else {
            RecommendationViewHolder recommendationViewHolder = this.mRecommendationViewHolder;
            if (recommendationViewHolder != null) {
                long j2 = MediaViewController.GUTS_ANIMATION_DURATION;
                GutsViewHolder gutsViewHolder2 = recommendationViewHolder.gutsViewHolder;
                Handler handler2 = gutsViewHolder2.gutsText.getHandler();
                if (handler2 == null) {
                    Log.d("RecommendationViewHolder", "marquee while longPressText.getHandler() is null", new Exception());
                } else {
                    handler2.postDelayed(new GutsViewHolder$marquee$1(gutsViewHolder2, true), j2);
                }
            }
        }
        MediaViewController mediaViewController = this.mMediaViewController;
        if (!mediaViewController.isGutsVisible) {
            mediaViewController.isGutsVisible = true;
            mediaViewController.animateNextStateChange = true;
            mediaViewController.animationDuration = MediaViewController.GUTS_ANIMATION_DURATION;
            mediaViewController.animationDelay = 0L;
            mediaViewController.setCurrentState(mediaViewController.currentStartLocation, mediaViewController.currentEndLocation, mediaViewController.currentTransitionProgress, false, true);
        }
        if (this.mMediaViewHolder != null) {
            bindPlayerContentDescription(this.mMediaData);
        } else if (this.mRecommendationViewHolder != null) {
            bindRecommendationContentDescription(this.mRecommendationData);
        }
        this.mLogger.logger.logWithInstanceId(MediaUiEvent.OPEN_LONG_PRESS, this.mUid, this.mPackageName, this.mInstanceId);
    }

    public final void setSemanticButtonVisibleAndAlpha(int i, MediaAction mediaAction, MediaButton mediaButton) {
        int i2;
        MediaViewController mediaViewController = this.mMediaViewController;
        ConstraintSet constraintSet = mediaViewController.collapsedLayout;
        ConstraintSet constraintSet2 = mediaViewController.expandedLayout;
        boolean contains = SEMANTIC_ACTIONS_COMPACT.contains(Integer.valueOf(i));
        List list = SEMANTIC_ACTIONS_HIDE_WHEN_SCRUBBING;
        boolean z = false;
        boolean z2 = (mediaAction == null || ((mediaButton != null && list.stream().allMatch(new MediaControlPanel$$ExternalSyntheticLambda11(mediaButton))) && list.contains(Integer.valueOf(i)) && this.mIsScrubbing)) ? false : true;
        if ((i == R.id.actionPrev && mediaButton.reservePrev) || (i == R.id.actionNext && mediaButton.reserveNext)) {
            this.mMediaViewHolder.getAction(i).setFocusable(z2);
            this.mMediaViewHolder.getAction(i).setClickable(z2);
            i2 = 4;
        } else {
            i2 = 8;
        }
        setVisibleAndAlpha(constraintSet2, i, z2, i2);
        if (z2 && contains) {
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
