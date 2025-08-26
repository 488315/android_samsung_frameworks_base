package com.android.systemui.statusbar.notification.icon.ui.viewmodel;

import android.content.res.Resources;
import android.graphics.drawable.Icon;
import com.android.systemui.R;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.statusbar.notification.domain.interactor.HeadsUpNotificationIconInteractor;
import com.android.systemui.statusbar.notification.icon.domain.interactor.StatusBarNotificationIconsInteractor;
import com.android.systemui.statusbar.notification.shared.ActiveNotificationModel;
import com.android.systemui.statusbar.phone.domain.interactor.DarkIconInteractor;
import com.android.systemui.util.ui.AnimatedValueKt;
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
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

/* loaded from: classes3.dex */
public final class NotificationIconContainerStatusBarViewModel {
    public final Flow animationsEnabled;
    public final CoroutineContext bgContext;
    public final DarkIconInteractor darkIconInteractor;
    public final Flow icons;
    public final Flow isolatedIcon;
    public final Flow isolatedIconLocation;
    public final int maxIcons;

    public NotificationIconContainerStatusBarViewModel(CoroutineContext coroutineContext, DarkIconInteractor darkIconInteractor, StatusBarNotificationIconsInteractor statusBarNotificationIconsInteractor, HeadsUpNotificationIconInteractor headsUpNotificationIconInteractor, KeyguardInteractor keyguardInteractor, Resources resources, ShadeInteractor shadeInteractor) {
        this.bgContext = coroutineContext;
        this.darkIconInteractor = darkIconInteractor;
        this.maxIcons = resources.getInteger(R.integer.max_notif_static_icons);
        ShadeInteractorImpl shadeInteractorImpl = (ShadeInteractorImpl) shadeInteractor;
        this.animationsEnabled = FlowKt.distinctUntilChanged(FlowKt.buffer$default(FlowKt.flowOn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(shadeInteractorImpl.isShadeTouchable, keyguardInteractor.isKeyguardShowing, new NotificationIconContainerStatusBarViewModel$animationsEnabled$1(null)), coroutineContext), -1, 2));
        final Flow flow = statusBarNotificationIconsInteractor.statusBarNotifs;
        Flow flowDistinctUntilChanged = FlowKt.distinctUntilChanged(FlowKt.buffer$default(FlowKt.flowOn(new Flow() { // from class: com.android.systemui.statusbar.notification.icon.ui.viewmodel.NotificationIconContainerStatusBarViewModel$special$$inlined$map$1

            /* renamed from: com.android.systemui.statusbar.notification.icon.ui.viewmodel.NotificationIconContainerStatusBarViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ NotificationIconContainerStatusBarViewModel this$0;

                /* renamed from: com.android.systemui.statusbar.notification.icon.ui.viewmodel.NotificationIconContainerStatusBarViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, NotificationIconContainerStatusBarViewModel notificationIconContainerStatusBarViewModel) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = notificationIconContainerStatusBarViewModel;
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
                            Icon icon = activeNotificationModel.statusBarIcon;
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
        this.icons = flowDistinctUntilChanged;
        this.isolatedIcon = AnimatedValueKt.toAnimatedValueFlow(com.android.systemui.util.kotlin.FlowKt.sample(com.android.systemui.util.kotlin.FlowKt.pairwise(FlowKt.distinctUntilChanged(FlowKt.buffer$default(FlowKt.flowOn(FlowKt.distinctUntilChanged(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(headsUpNotificationIconInteractor.isolatedNotification, flowDistinctUntilChanged, new NotificationIconContainerStatusBarViewModel$isolatedIcon$1(null))), coroutineContext), -1, 2)), null), shadeInteractorImpl.baseShadeInteractor.getShadeExpansion(), new NotificationIconContainerStatusBarViewModel$isolatedIcon$2(null)));
        this.isolatedIconLocation = FlowKt.distinctUntilChanged(FlowKt.buffer$default(new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(headsUpNotificationIconInteractor.isolatedIconLocation), -1, 2));
    }
}
