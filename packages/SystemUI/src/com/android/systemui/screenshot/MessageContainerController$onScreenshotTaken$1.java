package com.android.systemui.screenshot;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.UserHandle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;
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
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class MessageContainerController$onScreenshotTaken$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ ScreenshotData $screenshot;
    int label;
    final /* synthetic */ MessageContainerController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MessageContainerController$onScreenshotTaken$1(MessageContainerController messageContainerController, ScreenshotData screenshotData, Continuation continuation) {
        super(2, continuation);
        this.this$0 = messageContainerController;
        this.$screenshot = screenshotData;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new MessageContainerController$onScreenshotTaken$1(this.this$0, this.$screenshot, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MessageContainerController$onScreenshotTaken$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        int i;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = this.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            ProfileMessageController profileMessageController = this.this$0.profileMessageController;
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
            ViewGroup viewGroup = this.this$0.workProfileFirstRunView;
            if (viewGroup == null) {
                viewGroup = null;
            }
            viewGroup.setVisibility(0);
            ViewGroup viewGroup2 = this.this$0.detectionNoticeView;
            if (viewGroup2 == null) {
                viewGroup2 = null;
            }
            viewGroup2.setVisibility(8);
            final MessageContainerController messageContainerController = this.this$0;
            final ProfileMessageController profileMessageController2 = messageContainerController.profileMessageController;
            ViewGroup viewGroup3 = messageContainerController.workProfileFirstRunView;
            if (viewGroup3 == null) {
                viewGroup3 = null;
            }
            final Function0 function0 = new Function0() { // from class: com.android.systemui.screenshot.MessageContainerController$onScreenshotTaken$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    final MessageContainerController messageContainerController2 = MessageContainerController.this;
                    if (messageContainerController2.animateOut == null) {
                        Animator animator = messageContainerController2.getAnimator(false);
                        animator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.screenshot.MessageContainerController$animateOutMessageContainer$1$1
                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                            public final void onAnimationEnd(Animator animator2) {
                                super.onAnimationEnd(animator2);
                                ViewGroup viewGroup4 = MessageContainerController.this.container;
                                if (viewGroup4 == null) {
                                    viewGroup4 = null;
                                }
                                viewGroup4.setVisibility(8);
                                MessageContainerController.this.animateOut = null;
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
                    Function0.this.invoke();
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
                    SharedPreferences.Editor edit = profileFirstRunSettingsImpl.context.getSharedPreferences("com.android.systemui.screenshot", 0).edit();
                    edit.putBoolean(str, true);
                    edit.apply();
                }
            });
            final MessageContainerController messageContainerController2 = this.this$0;
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
                        ViewGroup viewGroup7 = MessageContainerController.this.container;
                        if (viewGroup7 == null) {
                            viewGroup7 = null;
                        }
                        viewGroup7.getViewTreeObserver().removeOnPreDrawListener(this);
                        MessageContainerController.this.getAnimator(true).start();
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
