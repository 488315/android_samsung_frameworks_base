package com.android.systemui.media.controls.ui.view;

import android.content.res.Resources;
import android.graphics.Outline;
import android.util.MathUtils;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import androidx.core.view.GestureDetectorCompat;
import androidx.dynamicanimation.animation.FloatPropertyCompat;
import com.android.app.tracing.TraceStateLogger;
import com.android.systemui.R;
import com.android.systemui.media.controls.util.MediaUiEvent;
import com.android.systemui.media.controls.util.MediaUiEventLogger;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.qs.PageIndicator;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.wm.shell.shared.animation.PhysicsAnimator;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class MediaCarouselScrollHandler {
    public static final MediaCarouselScrollHandler$Companion$CONTENT_TRANSLATION$1 CONTENT_TRANSLATION;
    public int carouselHeight;
    public int carouselWidth;
    public final Function1 closeGuts;
    public float contentTranslation;
    public int cornerRadius;
    public final Function0 dismissCallback;
    public final FalsingManager falsingManager;
    public boolean falsingProtectionNeeded;
    public final GestureDetectorCompat gestureDetector;
    public final MediaUiEventLogger logger;
    public final DelayableExecutor mainExecutor;
    public final ViewGroup mediaContent;
    public final PageIndicator pageIndicator;
    public int playerWidthPlusPadding;
    public int scrollIntoCurrentMedia;
    public final MediaScrollView scrollView;
    public boolean scrollingDisabled;
    public final Function1 seekBarUpdateListener;
    public View settingsButton;
    public boolean showsSettingsButton;
    public final MediaCarouselScrollHandler$touchListener$1 touchListener;
    public final Function0 translationChangedListener;
    public int visibleMediaIndex;
    public final TraceStateLogger visibleStateLogger = new TraceStateLogger("MediaCarouselScrollHandler#visibleToUser", false, false, false, 14, null);
    public boolean visibleToUser;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.media.controls.ui.view.MediaCarouselScrollHandler$Companion$CONTENT_TRANSLATION$1] */
    static {
        new Companion(null);
        CONTENT_TRANSLATION = new FloatPropertyCompat() { // from class: com.android.systemui.media.controls.ui.view.MediaCarouselScrollHandler$Companion$CONTENT_TRANSLATION$1
            @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
            public final float getValue(Object obj) {
                return ((MediaCarouselScrollHandler) obj).contentTranslation;
            }

            @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
            public final void setValue(Object obj, float f) {
                MediaCarouselScrollHandler$Companion$CONTENT_TRANSLATION$1 mediaCarouselScrollHandler$Companion$CONTENT_TRANSLATION$1 = MediaCarouselScrollHandler.CONTENT_TRANSLATION;
                ((MediaCarouselScrollHandler) obj).setContentTranslation(f);
            }
        };
    }

    public MediaCarouselScrollHandler(MediaScrollView mediaScrollView, PageIndicator pageIndicator, DelayableExecutor delayableExecutor, Function0 function0, Function0 function02, Function1 function1, Function1 function12, FalsingManager falsingManager, MediaUiEventLogger mediaUiEventLogger) {
        this.scrollView = mediaScrollView;
        this.pageIndicator = pageIndicator;
        this.mainExecutor = delayableExecutor;
        this.dismissCallback = function0;
        this.translationChangedListener = function02;
        this.seekBarUpdateListener = function1;
        this.closeGuts = function12;
        this.falsingManager = falsingManager;
        this.logger = mediaUiEventLogger;
        GestureDetector.SimpleOnGestureListener simpleOnGestureListener = new GestureDetector.SimpleOnGestureListener() { // from class: com.android.systemui.media.controls.ui.view.MediaCarouselScrollHandler$gestureListener$1
            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public final boolean onDown(MotionEvent motionEvent) {
                return false;
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public final boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
                final MediaCarouselScrollHandler mediaCarouselScrollHandler = this.this$0;
                if (!mediaCarouselScrollHandler.scrollingDisabled) {
                    float f3 = f * f;
                    double d = f2;
                    if (f3 >= 0.5d * d * d && f3 >= 1000000.0f) {
                        MediaScrollView mediaScrollView2 = mediaCarouselScrollHandler.scrollView;
                        float contentTranslation = mediaScrollView2.getContentTranslation();
                        float fSignum = 0.0f;
                        DelayableExecutor delayableExecutor2 = mediaCarouselScrollHandler.mainExecutor;
                        if (contentTranslation != 0.0f) {
                            if (Math.signum(f) == Math.signum(contentTranslation) && (!mediaCarouselScrollHandler.falsingProtectionNeeded || !mediaCarouselScrollHandler.falsingManager.isFalseTouch(1))) {
                                fSignum = Math.signum(contentTranslation) * mediaCarouselScrollHandler.getMaxTranslation();
                                if (!mediaCarouselScrollHandler.showsSettingsButton) {
                                    delayableExecutor2.executeDelayed(new Runnable() { // from class: com.android.systemui.media.controls.ui.view.MediaCarouselScrollHandler$onFling$1
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            mediaCarouselScrollHandler.dismissCallback.invoke();
                                        }
                                    }, 100L);
                                }
                            }
                            PhysicsAnimator.Companion.getClass();
                            PhysicsAnimator companion = PhysicsAnimator.Companion.getInstance(mediaCarouselScrollHandler);
                            companion.spring(MediaCarouselScrollHandler.CONTENT_TRANSLATION, fSignum, f, MediaCarouselScrollHandlerKt.translationConfig);
                            companion.start();
                            mediaScrollView2.animationTargetX = fSignum;
                            return true;
                        }
                        int scrollX = mediaScrollView2.getScrollX();
                        if (mediaScrollView2.isLayoutRtl()) {
                            ViewGroup viewGroup = mediaScrollView2.contentContainer;
                            if (viewGroup == null) {
                                viewGroup = null;
                            }
                            scrollX = (viewGroup.getWidth() - mediaScrollView2.getWidth()) - scrollX;
                        }
                        int i = mediaCarouselScrollHandler.playerWidthPlusPadding;
                        int i2 = i > 0 ? scrollX / i : 0;
                        if (!mediaScrollView2.isLayoutRtl() ? f < 0.0f : f > 0.0f) {
                            i2++;
                        }
                        final View childAt = mediaCarouselScrollHandler.mediaContent.getChildAt(Math.min(mediaCarouselScrollHandler.mediaContent.getChildCount() - 1, Math.max(0, i2)));
                        delayableExecutor2.execute(new Runnable() { // from class: com.android.systemui.media.controls.ui.view.MediaCarouselScrollHandler$onFling$2
                            @Override // java.lang.Runnable
                            public final void run() {
                                mediaCarouselScrollHandler.scrollView.smoothScrollTo(childAt.getLeft(), mediaCarouselScrollHandler.scrollView.getScrollY());
                            }
                        });
                        return true;
                    }
                }
                return false;
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public final boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
                MediaCarouselScrollHandler mediaCarouselScrollHandler = this.this$0;
                motionEvent.getClass();
                if (mediaCarouselScrollHandler.scrollingDisabled) {
                    return false;
                }
                float x = motionEvent2.getX() - motionEvent.getX();
                MediaScrollView mediaScrollView2 = mediaCarouselScrollHandler.scrollView;
                float contentTranslation = mediaScrollView2.getContentTranslation();
                if (contentTranslation == 0.0f && mediaScrollView2.canScrollHorizontally((int) (-x))) {
                    return false;
                }
                float fSignum = contentTranslation - f;
                float fAbs = Math.abs(fSignum);
                if (fAbs > mediaCarouselScrollHandler.getMaxTranslation() && Math.signum(f) != Math.signum(contentTranslation)) {
                    if (Math.abs(contentTranslation) > mediaCarouselScrollHandler.getMaxTranslation()) {
                        fSignum = contentTranslation - (f * 0.2f);
                    } else {
                        fSignum = Math.signum(fSignum) * (((fAbs - mediaCarouselScrollHandler.getMaxTranslation()) * 0.2f) + mediaCarouselScrollHandler.getMaxTranslation());
                    }
                }
                if (Math.signum(fSignum) != Math.signum(contentTranslation) && contentTranslation != 0.0f && mediaScrollView2.canScrollHorizontally(-((int) fSignum))) {
                    fSignum = 0.0f;
                }
                PhysicsAnimator.Companion.getClass();
                PhysicsAnimator companion = PhysicsAnimator.Companion.getInstance(mediaCarouselScrollHandler);
                if (companion.isRunning()) {
                    companion.spring(MediaCarouselScrollHandler.CONTENT_TRANSLATION, fSignum, 0.0f, MediaCarouselScrollHandlerKt.translationConfig);
                    companion.start();
                } else {
                    mediaCarouselScrollHandler.setContentTranslation(fSignum);
                }
                mediaScrollView2.animationTargetX = fSignum;
                return true;
            }
        };
        MediaCarouselScrollHandler$touchListener$1 mediaCarouselScrollHandler$touchListener$1 = new MediaCarouselScrollHandler$touchListener$1(this);
        this.touchListener = mediaCarouselScrollHandler$touchListener$1;
        View.OnScrollChangeListener onScrollChangeListener = new View.OnScrollChangeListener() { // from class: com.android.systemui.media.controls.ui.view.MediaCarouselScrollHandler$scrollChangedListener$1
            @Override // android.view.View.OnScrollChangeListener
            public final void onScrollChange(View view, int i, int i2, int i3, int i4) {
                MediaCarouselScrollHandler mediaCarouselScrollHandler = this.this$0;
                if (mediaCarouselScrollHandler.playerWidthPlusPadding == 0) {
                    return;
                }
                MediaScrollView mediaScrollView2 = mediaCarouselScrollHandler.scrollView;
                int scrollX = mediaScrollView2.getScrollX();
                if (mediaScrollView2.isLayoutRtl()) {
                    ViewGroup viewGroup = mediaScrollView2.contentContainer;
                    if (viewGroup == null) {
                        viewGroup = null;
                    }
                    scrollX = (viewGroup.getWidth() - mediaScrollView2.getWidth()) - scrollX;
                }
                MediaCarouselScrollHandler mediaCarouselScrollHandler2 = this.this$0;
                int i5 = mediaCarouselScrollHandler2.playerWidthPlusPadding;
                int i6 = scrollX / i5;
                int i7 = scrollX % i5;
                boolean z = true;
                boolean z2 = mediaCarouselScrollHandler2.scrollIntoCurrentMedia != 0;
                mediaCarouselScrollHandler2.scrollIntoCurrentMedia = i7;
                boolean z3 = i7 != 0;
                int i8 = mediaCarouselScrollHandler2.visibleMediaIndex;
                if (i6 != i8 || z2 != z3) {
                    mediaCarouselScrollHandler2.visibleMediaIndex = i6;
                    if (i8 != i6 && mediaCarouselScrollHandler2.visibleToUser) {
                        mediaCarouselScrollHandler2.logger.logger.logWithPosition(MediaUiEvent.CAROUSEL_PAGE, 0, (String) null, i6);
                    }
                    mediaCarouselScrollHandler2.closeGuts.mo781invoke(Boolean.FALSE);
                    mediaCarouselScrollHandler2.updatePlayerVisibilities();
                }
                float f = mediaCarouselScrollHandler2.visibleMediaIndex;
                int i9 = mediaCarouselScrollHandler2.playerWidthPlusPadding;
                float childCount = f + (i9 > 0 ? i7 / i9 : 0.0f);
                MediaScrollView mediaScrollView3 = mediaCarouselScrollHandler2.scrollView;
                if (mediaScrollView3.isLayoutRtl()) {
                    childCount = (mediaCarouselScrollHandler2.mediaContent.getChildCount() - childCount) - 1;
                }
                mediaCarouselScrollHandler2.pageIndicator.setLocation(childCount);
                if (mediaCarouselScrollHandler2.contentTranslation == 0.0f && mediaCarouselScrollHandler2.scrollIntoCurrentMedia == 0) {
                    z = false;
                }
                mediaScrollView3.setClipToOutline(z);
            }
        };
        this.gestureDetector = new GestureDetectorCompat(mediaScrollView.getContext(), simpleOnGestureListener);
        mediaScrollView.touchListener = mediaCarouselScrollHandler$touchListener$1;
        mediaScrollView.setOverScrollMode(2);
        ViewGroup viewGroup = mediaScrollView.contentContainer;
        this.mediaContent = viewGroup == null ? null : viewGroup;
        mediaScrollView.setOnScrollChangeListener(onScrollChangeListener);
        mediaScrollView.setOutlineProvider(new ViewOutlineProvider() { // from class: com.android.systemui.media.controls.ui.view.MediaCarouselScrollHandler.1
            @Override // android.view.ViewOutlineProvider
            public final void getOutline(View view, Outline outline) {
                if (outline != null) {
                    MediaCarouselScrollHandler mediaCarouselScrollHandler = MediaCarouselScrollHandler.this;
                    outline.setRoundRect(0, 0, mediaCarouselScrollHandler.carouselWidth, mediaCarouselScrollHandler.carouselHeight, mediaCarouselScrollHandler.cornerRadius);
                }
            }
        });
    }

    public static void scrollToPlayer$default(final MediaCarouselScrollHandler mediaCarouselScrollHandler, int i) {
        if (mediaCarouselScrollHandler.scrollingDisabled) {
            return;
        }
        final View childAt = mediaCarouselScrollHandler.mediaContent.getChildAt(Math.min(mediaCarouselScrollHandler.mediaContent.getChildCount() - 1, i));
        mediaCarouselScrollHandler.mainExecutor.executeDelayed(new Runnable() { // from class: com.android.systemui.media.controls.ui.view.MediaCarouselScrollHandler$scrollToPlayer$1
            @Override // java.lang.Runnable
            public final void run() {
                this.this$0.scrollView.smoothScrollTo(childAt.getLeft(), this.this$0.scrollView.getScrollY());
            }
        }, 100L);
    }

    public final int getMaxTranslation() {
        if (!this.showsSettingsButton) {
            return this.playerWidthPlusPadding;
        }
        View view = this.settingsButton;
        if (view == null) {
            view = null;
        }
        return view.getWidth();
    }

    public final void onPlayersChanged() throws Resources.NotFoundException {
        updatePlayerVisibilities();
        int dimensionPixelSize = this.scrollView.getContext().getResources().getDimensionPixelSize(R.dimen.qs_media_padding);
        int childCount = this.mediaContent.getChildCount();
        int i = 0;
        while (i < childCount) {
            View childAt = this.mediaContent.getChildAt(i);
            int i2 = i == childCount + (-1) ? 0 : dimensionPixelSize;
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) childAt.getLayoutParams();
            if (marginLayoutParams.getMarginEnd() != i2) {
                marginLayoutParams.setMarginEnd(i2);
                childAt.setLayoutParams(marginLayoutParams);
            }
            i++;
        }
    }

    public final void resetTranslation(boolean z) {
        MediaScrollView mediaScrollView = this.scrollView;
        if (mediaScrollView.getContentTranslation() == 0.0f) {
            return;
        }
        if (!z) {
            PhysicsAnimator.Companion.getClass();
            PhysicsAnimator.Companion.getInstance(this).cancel();
            setContentTranslation(0.0f);
        } else {
            PhysicsAnimator.Companion.getClass();
            PhysicsAnimator companion = PhysicsAnimator.Companion.getInstance(this);
            companion.spring(CONTENT_TRANSLATION, 0.0f, 0.0f, MediaCarouselScrollHandlerKt.translationConfig);
            companion.start();
            mediaScrollView.animationTargetX = 0.0f;
        }
    }

    public final void scrollByStep(int i) {
        if (this.scrollingDisabled) {
            return;
        }
        int i2 = this.visibleMediaIndex + i;
        int childCount = this.mediaContent.getChildCount();
        MediaScrollView mediaScrollView = this.scrollView;
        if (i2 < childCount && i2 >= 0) {
            if (mediaScrollView.getContentTranslation() == 0.0f) {
                scrollToPlayer$default(this, i2);
                return;
            } else {
                resetTranslation(true);
                return;
            }
        }
        if (this.showsSettingsButton) {
            float fSignum = Math.signum(-i) * getMaxTranslation();
            if (mediaScrollView.isLayoutRtl()) {
                fSignum = -fSignum;
            }
            PhysicsAnimator.Companion.getClass();
            PhysicsAnimator companion = PhysicsAnimator.Companion.getInstance(this);
            companion.spring(CONTENT_TRANSLATION, fSignum, 0.0f, MediaCarouselScrollHandlerKt.translationConfig);
            companion.start();
            mediaScrollView.animationTargetX = fSignum;
        }
    }

    public final void setContentTranslation(float f) {
        this.contentTranslation = f;
        this.mediaContent.setTranslationX(f);
        updateSettingsPresentation();
        this.translationChangedListener.invoke();
        this.scrollView.setClipToOutline((this.contentTranslation == 0.0f && this.scrollIntoCurrentMedia == 0) ? false : true);
    }

    public final void setVisibleMediaIndex(int i) {
        this.visibleMediaIndex = i;
    }

    public final void updatePlayerVisibilities() {
        boolean z = this.scrollIntoCurrentMedia != 0;
        int childCount = this.mediaContent.getChildCount();
        int i = 0;
        while (i < childCount) {
            View childAt = this.mediaContent.getChildAt(i);
            int i2 = this.visibleMediaIndex;
            childAt.setVisibility((i == i2 || (i == i2 + 1 && z)) ? 0 : 4);
            i++;
        }
    }

    public final void updateSettingsPresentation() {
        if (this.showsSettingsButton) {
            View view = this.settingsButton;
            if (view == null) {
                view = null;
            }
            if (view.getWidth() > 0) {
                float map = MathUtils.map(0.0f, getMaxTranslation(), 0.0f, 1.0f, Math.abs(this.contentTranslation));
                float f = 1.0f - map;
                View view2 = this.settingsButton;
                if (view2 == null) {
                    view2 = null;
                }
                float width = (-view2.getWidth()) * f * 0.3f;
                MediaScrollView mediaScrollView = this.scrollView;
                if (mediaScrollView.isLayoutRtl()) {
                    if (this.contentTranslation > 0.0f) {
                        float width2 = mediaScrollView.getWidth() - width;
                        View view3 = this.settingsButton;
                        if (view3 == null) {
                            view3 = null;
                        }
                        width = -(width2 - view3.getWidth());
                    } else {
                        width = -width;
                    }
                } else if (this.contentTranslation <= 0.0f) {
                    float width3 = mediaScrollView.getWidth() - width;
                    View view4 = this.settingsButton;
                    if (view4 == null) {
                        view4 = null;
                    }
                    width = width3 - view4.getWidth();
                }
                float f2 = f * 50;
                View view5 = this.settingsButton;
                if (view5 == null) {
                    view5 = null;
                }
                view5.setRotation(f2 * (-Math.signum(this.contentTranslation)));
                float fSaturate = MathUtils.saturate(MathUtils.map(0.5f, 1.0f, 0.0f, 1.0f, map));
                View view6 = this.settingsButton;
                if (view6 == null) {
                    view6 = null;
                }
                view6.setAlpha(fSaturate);
                View view7 = this.settingsButton;
                if (view7 == null) {
                    view7 = null;
                }
                view7.setVisibility(fSaturate != 0.0f ? 0 : 4);
                View view8 = this.settingsButton;
                if (view8 == null) {
                    view8 = null;
                }
                view8.setTranslationX(width);
                View view9 = this.settingsButton;
                if (view9 == null) {
                    view9 = null;
                }
                int height = mediaScrollView.getHeight();
                view9.setTranslationY((height - (this.settingsButton != null ? r10 : null).getHeight()) / 2.0f);
                return;
            }
        }
        View view10 = this.settingsButton;
        (view10 != null ? view10 : null).setVisibility(4);
    }

    public static /* synthetic */ void getTouchListener$annotations() {
    }
}
