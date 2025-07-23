package com.android.systemui.education.ui.view;

import android.R;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import androidx.core.app.NotificationCompat$Builder;
import com.android.systemui.education.ui.view.ContextualEduUiCoordinator;
import com.android.systemui.education.ui.viewmodel.ContextualEduContentViewModel;
import com.android.systemui.education.ui.viewmodel.ContextualEduNotificationViewModel;
import com.android.systemui.education.ui.viewmodel.ContextualEduToastViewModel;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class ContextualEduUiCoordinator$start$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ ContextualEduUiCoordinator this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ContextualEduUiCoordinator$start$1(ContextualEduUiCoordinator contextualEduUiCoordinator, Continuation continuation) {
        super(2, continuation);
        this.this$0 = contextualEduUiCoordinator;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ContextualEduUiCoordinator$start$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ContextualEduUiCoordinator$start$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ContextualEduUiCoordinator contextualEduUiCoordinator = this.this$0;
            ChannelFlowTransformLatest channelFlowTransformLatest = contextualEduUiCoordinator.viewModel.eduContent;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.education.ui.view.ContextualEduUiCoordinator$start$1.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    Intent createKeyboardTouchpadTutorialIntent;
                    ContextualEduContentViewModel contextualEduContentViewModel = (ContextualEduContentViewModel) obj2;
                    ContextualEduUiCoordinator contextualEduUiCoordinator2 = ContextualEduUiCoordinator.this;
                    if (contextualEduContentViewModel == null) {
                        Dialog dialog = contextualEduUiCoordinator2.dialog;
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                        contextualEduUiCoordinator2.dialog = null;
                    } else if (contextualEduContentViewModel instanceof ContextualEduToastViewModel) {
                        ContextualEduToastViewModel contextualEduToastViewModel = (ContextualEduToastViewModel) contextualEduContentViewModel;
                        Dialog dialog2 = contextualEduUiCoordinator2.dialog;
                        if (dialog2 != null) {
                            dialog2.dismiss();
                        }
                        Dialog dialog3 = (Dialog) contextualEduUiCoordinator2.createDialog.mo779invoke(contextualEduToastViewModel);
                        contextualEduUiCoordinator2.dialog = dialog3;
                        if (dialog3 != null) {
                            dialog3.show();
                        }
                    } else {
                        if (!(contextualEduContentViewModel instanceof ContextualEduNotificationViewModel)) {
                            throw new NoWhenBranchMatchedException();
                        }
                        ContextualEduNotificationViewModel contextualEduNotificationViewModel = (ContextualEduNotificationViewModel) contextualEduContentViewModel;
                        int i2 = ContextualEduUiCoordinator.$r8$clinit;
                        contextualEduUiCoordinator2.getClass();
                        Bundle bundle = new Bundle();
                        bundle.putString("android.substName", contextualEduUiCoordinator2.context.getString(R.string.chooseUsbActivity));
                        NotificationCompat$Builder notificationCompat$Builder = new NotificationCompat$Builder(contextualEduUiCoordinator2.context, "ContextualEduNotificationChannel");
                        notificationCompat$Builder.mNotification.icon = com.android.systemui.R.drawable.ic_settings;
                        notificationCompat$Builder.mContentTitle = NotificationCompat$Builder.limitCharSequenceLength(contextualEduNotificationViewModel.title);
                        notificationCompat$Builder.mContentText = NotificationCompat$Builder.limitCharSequenceLength(contextualEduNotificationViewModel.message);
                        int i3 = ContextualEduUiCoordinator.WhenMappings.$EnumSwitchMapping$0[contextualEduNotificationViewModel.gestureType.ordinal()];
                        if (i3 == 1) {
                            createKeyboardTouchpadTutorialIntent = contextualEduUiCoordinator2.createKeyboardTouchpadTutorialIntent("touchpad_back");
                        } else if (i3 == 2) {
                            createKeyboardTouchpadTutorialIntent = contextualEduUiCoordinator2.createKeyboardTouchpadTutorialIntent("touchpad_home");
                        } else if (i3 == 3) {
                            createKeyboardTouchpadTutorialIntent = contextualEduUiCoordinator2.createKeyboardTouchpadTutorialIntent("keyboard");
                        } else {
                            if (i3 != 4) {
                                throw new NoWhenBranchMatchedException();
                            }
                            createKeyboardTouchpadTutorialIntent = new Intent("com.android.systemui.action.TOUCHPAD_TUTORIAL");
                            createKeyboardTouchpadTutorialIntent.setFlags(268435456);
                            createKeyboardTouchpadTutorialIntent.setPackage("com.android.systemui");
                        }
                        notificationCompat$Builder.mContentIntent = PendingIntent.getActivity(contextualEduUiCoordinator2.context, 0, createKeyboardTouchpadTutorialIntent, 201326592);
                        notificationCompat$Builder.mPriority = 0;
                        notificationCompat$Builder.setFlag(16, true);
                        notificationCompat$Builder.addExtras(bundle);
                        contextualEduUiCoordinator2.notificationManager.notifyAsUser("ContextualEduUiCoordinator", 1000, notificationCompat$Builder.build(), UserHandle.of(contextualEduNotificationViewModel.userId));
                    }
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (channelFlowTransformLatest.collect(flowCollector, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
