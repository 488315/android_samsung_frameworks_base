package com.android.systemui.statusbar.notification.icon.ui.viewmodel;

import android.content.res.Resources;
import android.graphics.drawable.Icon;
import com.android.systemui.R;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$isFinishedInStateWhere$$inlined$map$1;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.statusbar.notification.icon.domain.interactor.AlwaysOnDisplayNotificationIconsInteractor;
import com.android.systemui.statusbar.notification.shared.ActiveNotificationModel;
import java.util.ArrayList;
import java.util.Set;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

/* loaded from: classes3.dex */
public final class NotificationIconContainerAlwaysOnDisplayViewModel {
    public final Flow areContainerChangesAnimated;
    public final Flow areIconAnimationsEnabled;
    public final Flow icons;
    public final int maxIcons;
    public final Flow tintAlpha;

    public NotificationIconContainerAlwaysOnDisplayViewModel(CoroutineContext coroutineContext, AlwaysOnDisplayNotificationIconsInteractor alwaysOnDisplayNotificationIconsInteractor, KeyguardInteractor keyguardInteractor, KeyguardTransitionInteractor keyguardTransitionInteractor, Resources resources, ShadeInteractor shadeInteractor) {
        this.maxIcons = resources.getInteger(R.integer.max_notif_icons_on_aod);
        this.areContainerChangesAnimated = FlowKt.distinctUntilChanged(FlowKt.buffer$default(FlowKt.flowOn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(((ShadeInteractorImpl) shadeInteractor).isShadeTouchable, keyguardInteractor.isKeyguardVisible, new NotificationIconContainerAlwaysOnDisplayViewModel$areContainerChangesAnimated$1(null)), coroutineContext), -1, 2));
        this.tintAlpha = FlowKt.distinctUntilChanged(FlowKt.buffer$default(FlowKt.flowOn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new NotificationIconContainerAlwaysOnDisplayViewModel$tintAlpha$1(null), keyguardTransitionInteractor.getTransitionValueFlow(KeyguardState.AOD)), new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new NotificationIconContainerAlwaysOnDisplayViewModel$tintAlpha$2(null), keyguardTransitionInteractor.getTransitionValueFlow(KeyguardState.DOZING)), new NotificationIconContainerAlwaysOnDisplayViewModel$tintAlpha$3(null)), coroutineContext), -1, 2));
        this.areIconAnimationsEnabled = FlowKt.distinctUntilChanged(FlowKt.buffer$default(FlowKt.flowOn(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new NotificationIconContainerAlwaysOnDisplayViewModel$areIconAnimationsEnabled$2(null), FlowKt.distinctUntilChanged(new KeyguardTransitionInteractor$isFinishedInStateWhere$$inlined$map$1(keyguardTransitionInteractor.finishedKeyguardState, new NotificationIconContainerAlwaysOnDisplayViewModel$$ExternalSyntheticLambda0()))), coroutineContext), -1, 2));
        final Flow flow = alwaysOnDisplayNotificationIconsInteractor.aodNotifs;
        this.icons = FlowKt.distinctUntilChanged(FlowKt.buffer$default(FlowKt.flowOn(new Flow() { // from class: com.android.systemui.statusbar.notification.icon.ui.viewmodel.NotificationIconContainerAlwaysOnDisplayViewModel$special$$inlined$map$1

            /* renamed from: com.android.systemui.statusbar.notification.icon.ui.viewmodel.NotificationIconContainerAlwaysOnDisplayViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ NotificationIconContainerAlwaysOnDisplayViewModel this$0;

                /* renamed from: com.android.systemui.statusbar.notification.icon.ui.viewmodel.NotificationIconContainerAlwaysOnDisplayViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector, NotificationIconContainerAlwaysOnDisplayViewModel notificationIconContainerAlwaysOnDisplayViewModel) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = notificationIconContainerAlwaysOnDisplayViewModel;
                }

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    String str;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        ArrayList arrayList = new ArrayList();
                        for (ActiveNotificationModel activeNotificationModel : (Set) obj) {
                            Icon icon = activeNotificationModel.aodIcon;
                            NotificationIconInfo notificationIconInfo = (icon == null || (str = activeNotificationModel.groupKey) == null) ? null : new NotificationIconInfo(icon, activeNotificationModel.key, str);
                            if (notificationIconInfo != null) {
                                arrayList.add(notificationIconInfo);
                            }
                        }
                        NotificationIconsViewData notificationIconsViewData = new NotificationIconsViewData(arrayList, this.this$0.maxIcons, null, 4, null);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(notificationIconsViewData, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = flow.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, coroutineContext), -1, 2));
    }
}
