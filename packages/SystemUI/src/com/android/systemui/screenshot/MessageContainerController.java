package com.android.systemui.screenshot;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.UserHandle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.Guideline;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.R;
import com.android.systemui.screenshot.message.LabeledIcon;
import com.android.systemui.screenshot.message.ProfileFirstRunSettingsImpl;
import com.android.systemui.screenshot.message.ProfileMessageController;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
public final class MessageContainerController {
    public Animator animateOut;
    public ViewGroup container;
    public ViewGroup detectionNoticeView;
    public Guideline guideline;
    public final CoroutineScope mainScope;
    public final ProfileMessageController profileMessageController;
    public ViewGroup workProfileFirstRunView;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.screenshot.MessageContainerController$onScreenshotTaken$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ ScreenshotData $screenshot;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(ScreenshotData screenshotData, Continuation continuation) {
            super(2, continuation);
            this.$screenshot = screenshotData;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return MessageContainerController.this.new AnonymousClass1(this.$screenshot, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            int i;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                ProfileMessageController profileMessageController = MessageContainerController.this.profileMessageController;
                UserHandle userHandle = this.$screenshot.userHandle;
                this.label = 1;
                obj = profileMessageController.onScreenshotTaken(userHandle, this);
                if (obj == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            final ProfileMessageController.ProfileFirstRunData profileFirstRunData = (ProfileMessageController.ProfileFirstRunData) obj;
            EmptyList emptyList = EmptyList.INSTANCE;
            if (profileFirstRunData != null) {
                ViewGroup viewGroup = MessageContainerController.this.workProfileFirstRunView;
                if (viewGroup == null) {
                    viewGroup = null;
                }
                viewGroup.setVisibility(0);
                ViewGroup viewGroup2 = MessageContainerController.this.detectionNoticeView;
                if (viewGroup2 == null) {
                    viewGroup2 = null;
                }
                viewGroup2.setVisibility(8);
                final MessageContainerController messageContainerController = MessageContainerController.this;
                final ProfileMessageController profileMessageController2 = messageContainerController.profileMessageController;
                ViewGroup viewGroup3 = messageContainerController.workProfileFirstRunView;
                if (viewGroup3 == null) {
                    viewGroup3 = null;
                }
                final Function0 function0 = new Function0() { // from class: com.android.systemui.screenshot.MessageContainerController$onScreenshotTaken$1$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        final MessageContainerController messageContainerController2 = messageContainerController;
                        if (messageContainerController2.animateOut == null) {
                            Animator animator = messageContainerController2.getAnimator(false);
                            animator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.screenshot.MessageContainerController$animateOutMessageContainer$1$1
                                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                public final void onAnimationEnd(Animator animator2) {
                                    super.onAnimationEnd(animator2);
                                    ViewGroup viewGroup4 = messageContainerController2.container;
                                    if (viewGroup4 == null) {
                                        viewGroup4 = null;
                                    }
                                    viewGroup4.setVisibility(8);
                                    messageContainerController2.animateOut = null;
                                }
                            });
                            animator.start();
                            messageContainerController2.animateOut = animator;
                        }
                        return Unit.INSTANCE;
                    }
                };
                profileMessageController2.getClass();
                LabeledIcon labeledIcon = profileFirstRunData.labeledIcon;
                if (labeledIcon.badgedIcon != null) {
                    ((ImageView) viewGroup3.requireViewById(R.id.screenshot_message_icon)).setImageDrawable(labeledIcon.badgedIcon);
                }
                TextView textView = (TextView) viewGroup3.requireViewById(R.id.screenshot_message_content);
                Context context = viewGroup3.getContext();
                int i3 = ProfileMessageController.WhenMappings.$EnumSwitchMapping$1[profileFirstRunData.profileType.ordinal()];
                if (i3 == 1) {
                    i = R.string.screenshot_work_profile_notification;
                } else {
                    if (i3 != 2) {
                        throw new NoWhenBranchMatchedException();
                    }
                    i = R.string.screenshot_private_profile_notification;
                }
                textView.setText(context.getString(i, labeledIcon.label));
                viewGroup3.requireViewById(R.id.message_dismiss_button).setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.screenshot.message.ProfileMessageController$bindView$1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        String str;
                        function0.invoke();
                        ProfileFirstRunSettings profileFirstRunSettings = profileMessageController2.firstRunSettings;
                        ProfileMessageController.FirstRunProfile firstRunProfile = profileFirstRunData.profileType;
                        ProfileFirstRunSettingsImpl profileFirstRunSettingsImpl = (ProfileFirstRunSettingsImpl) profileFirstRunSettings;
                        profileFirstRunSettingsImpl.getClass();
                        int i4 = ProfileFirstRunSettingsImpl.WhenMappings.$EnumSwitchMapping$0[firstRunProfile.ordinal()];
                        if (i4 == 1) {
                            str = "work_profile_first_run";
                        } else {
                            if (i4 != 2) {
                                throw new NoWhenBranchMatchedException();
                            }
                            str = "private_profile_first_run";
                        }
                        SharedPreferences.Editor editorEdit = profileFirstRunSettingsImpl.context.getSharedPreferences("com.android.systemui.screenshot", 0).edit();
                        editorEdit.putBoolean(str, true);
                        editorEdit.apply();
                    }
                });
                final MessageContainerController messageContainerController2 = MessageContainerController.this;
                ViewGroup viewGroup4 = messageContainerController2.container;
                if (viewGroup4 == null) {
                    viewGroup4 = null;
                }
                if (viewGroup4.getVisibility() != 0) {
                    ViewGroup viewGroup5 = messageContainerController2.container;
                    if (viewGroup5 == null) {
                        viewGroup5 = null;
                    }
                    viewGroup5.setVisibility(0);
                    ViewGroup viewGroup6 = messageContainerController2.container;
                    (viewGroup6 != null ? viewGroup6 : null).getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { // from class: com.android.systemui.screenshot.MessageContainerController$animateInMessageContainer$1
                        @Override // android.view.ViewTreeObserver.OnPreDrawListener
                        public final boolean onPreDraw() {
                            ViewGroup viewGroup7 = messageContainerController2.container;
                            if (viewGroup7 == null) {
                                viewGroup7 = null;
                            }
                            viewGroup7.getViewTreeObserver().removeOnPreDrawListener(this);
                            messageContainerController2.getAnimator(true).start();
                            return false;
                        }
                    });
                }
            } else {
                emptyList.getClass();
            }
            return Unit.INSTANCE;
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
        ValueAnimator valueAnimatorOfFloat = z ? ValueAnimator.ofFloat(0.0f, 1.0f) : ValueAnimator.ofFloat(1.0f, 0.0f);
        valueAnimatorOfFloat.setDuration(400L);
        valueAnimatorOfFloat.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.screenshot.MessageContainerController$getAnimator$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                Guideline guideline = this.this$0.guideline;
                if (guideline == null) {
                    guideline = null;
                }
                guideline.setGuidelineEnd((int) (height * fFloatValue));
                ViewGroup viewGroup3 = this.this$0.container;
                (viewGroup3 != null ? viewGroup3 : null).setAlpha(fFloatValue);
            }
        });
        return valueAnimatorOfFloat;
    }

    public final void onScreenshotTaken(ScreenshotData screenshotData) {
        CoroutineTracingKt.launchTraced$default(this.mainScope, null, null, new AnonymousClass1(screenshotData, null), 7);
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
