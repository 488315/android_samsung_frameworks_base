package com.android.systemui.media.controls.ui.view;

import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.wm.shell.shared.animation.PhysicsAnimator;
import com.android.wm.shell.shared.animation.PhysicsAnimatorKt;
import com.samsung.android.knox.ex.peripheral.PeripheralConstants;
import java.util.WeakHashMap;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class MediaScrollView extends HorizontalScrollView {
    public float animationTargetX;
    public ViewGroup contentContainer;
    public MediaCarouselScrollHandler$touchListener$1 touchListener;

    public MediaScrollView(Context context) {
        this(context, null, 0, 6, null);
    }

    public final void cancelCurrentScroll() {
        long jUptimeMillis = SystemClock.uptimeMillis();
        MotionEvent motionEventObtain = MotionEvent.obtain(jUptimeMillis, jUptimeMillis, 3, 0.0f, 0.0f, 0);
        motionEventObtain.setSource(PeripheralConstants.ErrorCode.ERROR_PERIPHERAL_CONNECTION_FAIL);
        super.onTouchEvent(motionEventObtain);
        motionEventObtain.recycle();
    }

    public final float getContentTranslation() {
        ViewGroup viewGroup = this.contentContainer;
        if (viewGroup == null) {
            viewGroup = null;
        }
        WeakHashMap weakHashMap = PhysicsAnimatorKt.animators;
        PhysicsAnimator.Companion.getClass();
        if (PhysicsAnimator.Companion.getInstance(viewGroup).isRunning()) {
            return this.animationTargetX;
        }
        ViewGroup viewGroup2 = this.contentContainer;
        return (viewGroup2 != null ? viewGroup2 : null).getTranslationX();
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.contentContainer = (ViewGroup) getChildAt(0);
    }

    @Override // android.widget.HorizontalScrollView, android.view.ViewGroup
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        MediaCarouselScrollHandler$touchListener$1 mediaCarouselScrollHandler$touchListener$1 = this.touchListener;
        return super.onInterceptTouchEvent(motionEvent) || (mediaCarouselScrollHandler$touchListener$1 != null ? mediaCarouselScrollHandler$touchListener$1.onInterceptTouchEvent(motionEvent) : false);
    }

    @Override // android.widget.HorizontalScrollView, android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (!isLaidOut() && isLayoutRtl()) {
            int scrollX = getScrollX();
            if (isLayoutRtl()) {
                ViewGroup viewGroup = this.contentContainer;
                if (viewGroup == null) {
                    viewGroup = null;
                }
                scrollX = (viewGroup.getWidth() - getWidth()) - scrollX;
            }
            ((HorizontalScrollView) this).mScrollX = scrollX;
        }
        super.onLayout(z, i, i2, i3, i4);
    }

    @Override // android.widget.HorizontalScrollView, android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        boolean z;
        float fSignum;
        MediaCarouselScrollHandler$touchListener$1 mediaCarouselScrollHandler$touchListener$1 = this.touchListener;
        if (mediaCarouselScrollHandler$touchListener$1 != null) {
            mediaCarouselScrollHandler$touchListener$1.getClass();
            motionEvent.getClass();
            final MediaCarouselScrollHandler mediaCarouselScrollHandler = mediaCarouselScrollHandler$touchListener$1.this$0;
            z = false;
            if (!mediaCarouselScrollHandler.scrollingDisabled) {
                boolean z2 = motionEvent.getAction() == 1;
                boolean zOnTouchEvent = mediaCarouselScrollHandler.gestureDetector.mDetector.onTouchEvent(motionEvent);
                MediaScrollView mediaScrollView = mediaCarouselScrollHandler.scrollView;
                if (zOnTouchEvent) {
                    if (z2) {
                        mediaScrollView.cancelCurrentScroll();
                        z = true;
                    }
                } else if (motionEvent.getAction() == 2) {
                    PhysicsAnimator.Companion.getClass();
                    PhysicsAnimator.Companion.getInstance(mediaCarouselScrollHandler).cancel();
                } else if (z2 || motionEvent.getAction() == 3) {
                    int scrollX = mediaScrollView.getScrollX();
                    if (mediaScrollView.isLayoutRtl()) {
                        ViewGroup viewGroup = mediaScrollView.contentContainer;
                        if (viewGroup == null) {
                            viewGroup = null;
                        }
                        scrollX = (viewGroup.getWidth() - mediaScrollView.getWidth()) - scrollX;
                    }
                    int i = mediaCarouselScrollHandler.playerWidthPlusPadding;
                    int i2 = scrollX % i;
                    int i3 = i2 > i / 2 ? i - i2 : i2 * (-1);
                    DelayableExecutor delayableExecutor = mediaCarouselScrollHandler.mainExecutor;
                    if (i3 != 0) {
                        if (mediaScrollView.isLayoutRtl()) {
                            i3 = -i3;
                        }
                        final int scrollX2 = mediaScrollView.getScrollX() + i3;
                        delayableExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.ui.view.MediaCarouselScrollHandler$onTouch$1
                            @Override // java.lang.Runnable
                            public final void run() {
                                MediaScrollView mediaScrollView2 = mediaCarouselScrollHandler.scrollView;
                                mediaScrollView2.smoothScrollTo(scrollX2, mediaScrollView2.getScrollY());
                            }
                        });
                    }
                    float contentTranslation = mediaScrollView.getContentTranslation();
                    if (contentTranslation != 0.0f) {
                        if (Math.abs(contentTranslation) < mediaCarouselScrollHandler.getMaxTranslation() / 2 || (mediaCarouselScrollHandler.falsingProtectionNeeded && mediaCarouselScrollHandler.falsingManager.isFalseTouch(1))) {
                            fSignum = 0.0f;
                        } else {
                            fSignum = Math.signum(contentTranslation) * mediaCarouselScrollHandler.getMaxTranslation();
                            if (!mediaCarouselScrollHandler.showsSettingsButton) {
                                delayableExecutor.executeDelayed(new Runnable() { // from class: com.android.systemui.media.controls.ui.view.MediaCarouselScrollHandler$onTouch$2
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        mediaCarouselScrollHandler.dismissCallback.invoke();
                                    }
                                }, 100L);
                            }
                        }
                        PhysicsAnimator.Companion.getClass();
                        PhysicsAnimator companion = PhysicsAnimator.Companion.getInstance(mediaCarouselScrollHandler);
                        companion.spring(MediaCarouselScrollHandler.CONTENT_TRANSLATION, fSignum, 0.0f, MediaCarouselScrollHandlerKt.translationConfig);
                        companion.start();
                        mediaScrollView.animationTargetX = fSignum;
                    }
                }
            }
        } else {
            z = false;
        }
        return super.onTouchEvent(motionEvent) || z;
    }

    @Override // android.view.View
    public final boolean overScrollBy(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, boolean z) {
        if (getContentTranslation() == 0.0f) {
            return super.overScrollBy(i, i2, i3, i4, i5, i6, i7, i8, z);
        }
        return false;
    }

    @Override // android.widget.HorizontalScrollView, android.view.View
    public final void scrollTo(int i, int i2) {
        int i3 = ((HorizontalScrollView) this).mScrollX;
        if (i3 == i && ((HorizontalScrollView) this).mScrollY == i2) {
            return;
        }
        int i4 = ((HorizontalScrollView) this).mScrollY;
        ((HorizontalScrollView) this).mScrollX = i;
        ((HorizontalScrollView) this).mScrollY = i2;
        invalidateParentCaches();
        onScrollChanged(((HorizontalScrollView) this).mScrollX, ((HorizontalScrollView) this).mScrollY, i3, i4);
        if (awakenScrollBars()) {
            return;
        }
        postInvalidateOnAnimation();
    }

    public MediaScrollView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
    }

    public /* synthetic */ MediaScrollView(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 0 : i);
    }

    public MediaScrollView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }
}
