package com.android.systemui.education.ui.view;

import android.R;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import android.view.accessibility.AccessibilityManager;
import androidx.core.app.NotificationCompat$Builder;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.CoreStartable;
import com.android.systemui.contextualeducation.GestureType;
import com.android.systemui.education.ui.viewmodel.ContextualEduContentViewModel;
import com.android.systemui.education.ui.viewmodel.ContextualEduNotificationViewModel;
import com.android.systemui.education.ui.viewmodel.ContextualEduToastViewModel;
import com.android.systemui.education.ui.viewmodel.ContextualEduViewModel;
import com.android.systemui.inputdevice.tutorial.ui.view.KeyboardTouchpadTutorialActivity;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* loaded from: classes2.dex */
public final class ContextualEduUiCoordinator implements CoreStartable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final CoroutineScope applicationScope;
    public final Context context;
    public final Function1 createDialog;
    public Dialog dialog;
    public final NotificationManager notificationManager;
    public final ContextualEduViewModel viewModel;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[GestureType.values().length];
            try {
                iArr[GestureType.BACK.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[GestureType.HOME.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[GestureType.ALL_APPS.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[GestureType.OVERVIEW.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* renamed from: com.android.systemui.education.ui.view.ContextualEduUiCoordinator$start$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return ContextualEduUiCoordinator.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final ContextualEduUiCoordinator contextualEduUiCoordinator = ContextualEduUiCoordinator.this;
                ChannelFlowTransformLatest channelFlowTransformLatest = contextualEduUiCoordinator.viewModel.eduContent;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.education.ui.view.ContextualEduUiCoordinator.start.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        Intent intentCreateKeyboardTouchpadTutorialIntent;
                        ContextualEduContentViewModel contextualEduContentViewModel = (ContextualEduContentViewModel) obj2;
                        ContextualEduUiCoordinator contextualEduUiCoordinator2 = contextualEduUiCoordinator;
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
                            Dialog dialog3 = (Dialog) contextualEduUiCoordinator2.createDialog.mo781invoke(contextualEduToastViewModel);
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
                            int i3 = WhenMappings.$EnumSwitchMapping$0[contextualEduNotificationViewModel.gestureType.ordinal()];
                            if (i3 == 1) {
                                intentCreateKeyboardTouchpadTutorialIntent = contextualEduUiCoordinator2.createKeyboardTouchpadTutorialIntent("touchpad_back");
                            } else if (i3 == 2) {
                                intentCreateKeyboardTouchpadTutorialIntent = contextualEduUiCoordinator2.createKeyboardTouchpadTutorialIntent("touchpad_home");
                            } else if (i3 == 3) {
                                intentCreateKeyboardTouchpadTutorialIntent = contextualEduUiCoordinator2.createKeyboardTouchpadTutorialIntent("keyboard");
                            } else {
                                if (i3 != 4) {
                                    throw new NoWhenBranchMatchedException();
                                }
                                intentCreateKeyboardTouchpadTutorialIntent = new Intent("com.android.systemui.action.TOUCHPAD_TUTORIAL");
                                intentCreateKeyboardTouchpadTutorialIntent.setFlags(268435456);
                                intentCreateKeyboardTouchpadTutorialIntent.setPackage("com.android.systemui");
                            }
                            notificationCompat$Builder.mContentIntent = PendingIntent.getActivity(contextualEduUiCoordinator2.context, 0, intentCreateKeyboardTouchpadTutorialIntent, 201326592);
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

    static {
        new Companion(null);
    }

    public ContextualEduUiCoordinator(CoroutineScope coroutineScope, ContextualEduViewModel contextualEduViewModel, Context context, NotificationManager notificationManager, Function1 function1) {
        this.applicationScope = coroutineScope;
        this.viewModel = contextualEduViewModel;
        this.context = context;
        this.notificationManager = notificationManager;
        this.createDialog = function1;
    }

    public final Intent createKeyboardTouchpadTutorialIntent(String str) {
        Intent intent = new Intent(this.context, (Class<?>) KeyboardTouchpadTutorialActivity.class);
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setFlags(268435456);
        intent.putExtra("tutorial_scope", str);
        intent.putExtra("entry_point", "contextual_edu");
        return intent;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        this.notificationManager.createNotificationChannel(new NotificationChannel("ContextualEduNotificationChannel", this.context.getString(R.string.chooseUsbActivity), 2));
        CoroutineTracingKt.launchTraced$default(this.applicationScope, null, null, new AnonymousClass1(null), 7);
    }

    public ContextualEduUiCoordinator(CoroutineScope coroutineScope, final Context context, ContextualEduViewModel contextualEduViewModel, NotificationManager notificationManager, final AccessibilityManager accessibilityManager) {
        this(coroutineScope, contextualEduViewModel, context, notificationManager, new Function1() { // from class: com.android.systemui.education.ui.view.ContextualEduUiCoordinator$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                Context context2 = context;
                AccessibilityManager accessibilityManager2 = accessibilityManager;
                int i = ContextualEduUiCoordinator.$r8$clinit;
                return new ContextualEduDialog(context2, (ContextualEduToastViewModel) obj, accessibilityManager2);
            }
        });
    }
}
