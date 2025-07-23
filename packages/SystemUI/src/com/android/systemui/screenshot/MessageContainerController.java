package com.android.systemui.screenshot;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import androidx.constraintlayout.widget.Guideline;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.R;
import com.android.systemui.screenshot.message.ProfileMessageController;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class MessageContainerController {
    public Animator animateOut;
    public ViewGroup container;
    public ViewGroup detectionNoticeView;
    public Guideline guideline;
    public final CoroutineScope mainScope;
    public final ProfileMessageController profileMessageController;
    public ViewGroup workProfileFirstRunView;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    public MessageContainerController(WorkProfileMessageController workProfileMessageController, ProfileMessageController profileMessageController, ScreenshotDetectionController screenshotDetectionController, CoroutineScope coroutineScope) {
        this.profileMessageController = profileMessageController;
        this.mainScope = coroutineScope;
    }

    public final Animator getAnimator(boolean z) {
        ViewGroup viewGroup = this.container;
        if (viewGroup == null) {
            viewGroup = null;
        }
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) viewGroup.getLayoutParams();
        ViewGroup viewGroup2 = this.container;
        final int height = (viewGroup2 != null ? viewGroup2 : null).getHeight() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin;
        ValueAnimator ofFloat = z ? ValueAnimator.ofFloat(0.0f, 1.0f) : ValueAnimator.ofFloat(1.0f, 0.0f);
        ofFloat.setDuration(400L);
        ofFloat.setInterpolator(new AccelerateDecelerateInterpolator());
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.screenshot.MessageContainerController$getAnimator$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                Guideline guideline = MessageContainerController.this.guideline;
                if (guideline == null) {
                    guideline = null;
                }
                guideline.setGuidelineEnd((int) (height * floatValue));
                ViewGroup viewGroup3 = MessageContainerController.this.container;
                (viewGroup3 != null ? viewGroup3 : null).setAlpha(floatValue);
            }
        });
        return ofFloat;
    }

    public final void onScreenshotTaken(ScreenshotData screenshotData) {
        CoroutineTracingKt.launchTraced$default(this.mainScope, null, null, new MessageContainerController$onScreenshotTaken$1(this, screenshotData, null), 7);
    }

    public final void setView(ViewGroup viewGroup) {
        this.container = (ViewGroup) viewGroup.requireViewById(R.id.screenshot_message_container);
        this.guideline = (Guideline) viewGroup.requireViewById(R.id.guideline);
        ViewGroup viewGroup2 = this.container;
        if (viewGroup2 == null) {
            viewGroup2 = null;
        }
        this.workProfileFirstRunView = (ViewGroup) viewGroup2.requireViewById(R.id.work_profile_first_run);
        ViewGroup viewGroup3 = this.container;
        if (viewGroup3 == null) {
            viewGroup3 = null;
        }
        this.detectionNoticeView = (ViewGroup) viewGroup3.requireViewById(R.id.screenshot_detection_notice);
        ViewGroup viewGroup4 = this.container;
        if (viewGroup4 == null) {
            viewGroup4 = null;
        }
        viewGroup4.setVisibility(8);
        Guideline guideline = this.guideline;
        if (guideline == null) {
            guideline = null;
        }
        guideline.setGuidelineEnd(0);
        ViewGroup viewGroup5 = this.workProfileFirstRunView;
        if (viewGroup5 == null) {
            viewGroup5 = null;
        }
        viewGroup5.setVisibility(8);
        ViewGroup viewGroup6 = this.detectionNoticeView;
        (viewGroup6 != null ? viewGroup6 : null).setVisibility(8);
    }
}
