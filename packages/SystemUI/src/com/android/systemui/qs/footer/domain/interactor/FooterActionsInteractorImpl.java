package com.android.systemui.qs.footer.domain.interactor;

import android.app.admin.DevicePolicyEventLogger;
import android.content.Context;
import android.content.IntentFilter;
import android.os.UserHandle;
import android.view.View;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.R;
import com.android.systemui.animation.DialogCuj;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.animation.Expandable;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.qs.FgsManagerController;
import com.android.systemui.qs.QSSecurityFooterUtils;
import com.android.systemui.qs.footer.data.repository.ForegroundServicesRepository;
import com.android.systemui.security.data.repository.SecurityRepository;
import com.android.systemui.security.data.repository.SecurityRepositoryImpl;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.SecurityControllerImpl;
import com.android.systemui.user.data.repository.UserSwitcherRepository;
import com.android.systemui.user.data.repository.UserSwitcherRepositoryImpl;
import com.android.systemui.user.domain.interactor.UserSwitcherInteractor;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class FooterActionsInteractorImpl implements FooterActionsInteractor {
    public final ActivityStarter activityStarter;
    public final Flow deviceMonitoringDialogRequests;
    public final DeviceProvisionedController deviceProvisionedController;
    public final FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 foregroundServicesCount;
    public final FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 hasNewForegroundServices;
    public final MetricsLogger metricsLogger;
    public final QSSecurityFooterUtils qsSecurityFooterUtils;
    public final FooterActionsInteractorImpl$special$$inlined$map$1 securityButtonConfig;
    public final UiEventLogger uiEventLogger;
    public final UserSwitcherInteractor userSwitcherInteractor;
    public final Flow userSwitcherStatus;

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.qs.footer.domain.interactor.FooterActionsInteractorImpl$special$$inlined$map$1] */
    public FooterActionsInteractorImpl(ActivityStarter activityStarter, MetricsLogger metricsLogger, UiEventLogger uiEventLogger, DeviceProvisionedController deviceProvisionedController, QSSecurityFooterUtils qSSecurityFooterUtils, FgsManagerController fgsManagerController, UserSwitcherInteractor userSwitcherInteractor, SecurityRepository securityRepository, ForegroundServicesRepository foregroundServicesRepository, UserSwitcherRepository userSwitcherRepository, BroadcastDispatcher broadcastDispatcher, final CoroutineDispatcher coroutineDispatcher) {
        this.activityStarter = activityStarter;
        this.metricsLogger = metricsLogger;
        this.uiEventLogger = uiEventLogger;
        this.deviceProvisionedController = deviceProvisionedController;
        this.qsSecurityFooterUtils = qSSecurityFooterUtils;
        this.userSwitcherInteractor = userSwitcherInteractor;
        final Flow flow = ((SecurityRepositoryImpl) securityRepository).security;
        this.securityButtonConfig = new Flow() { // from class: com.android.systemui.qs.footer.domain.interactor.FooterActionsInteractorImpl$special$$inlined$map$1

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.qs.footer.domain.interactor.FooterActionsInteractorImpl$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ CoroutineDispatcher $bgDispatcher$inlined;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ FooterActionsInteractorImpl this$0;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.qs.footer.domain.interactor.FooterActionsInteractorImpl$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, CoroutineDispatcher coroutineDispatcher, FooterActionsInteractorImpl footerActionsInteractorImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.$bgDispatcher$inlined = coroutineDispatcher;
                    this.this$0 = footerActionsInteractorImpl;
                }

                /* JADX WARN: Removed duplicated region for block: B:19:0x0061 A[RETURN] */
                /* JADX WARN: Removed duplicated region for block: B:20:0x003b  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0023  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r7, kotlin.coroutines.Continuation r8) {
                    /*
                        r6 = this;
                        boolean r0 = r8 instanceof com.android.systemui.qs.footer.domain.interactor.FooterActionsInteractorImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r8
                        com.android.systemui.qs.footer.domain.interactor.FooterActionsInteractorImpl$special$$inlined$map$1$2$1 r0 = (com.android.systemui.qs.footer.domain.interactor.FooterActionsInteractorImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.qs.footer.domain.interactor.FooterActionsInteractorImpl$special$$inlined$map$1$2$1 r0 = new com.android.systemui.qs.footer.domain.interactor.FooterActionsInteractorImpl$special$$inlined$map$1$2$1
                        r0.<init>(r8)
                    L18:
                        java.lang.Object r8 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 0
                        r4 = 2
                        r5 = 1
                        if (r2 == 0) goto L3b
                        if (r2 == r5) goto L33
                        if (r2 != r4) goto L2b
                        kotlin.ResultKt.throwOnFailure(r8)
                        goto L62
                    L2b:
                        java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                        java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                        r6.<init>(r7)
                        throw r6
                    L33:
                        java.lang.Object r6 = r0.L$0
                        kotlinx.coroutines.flow.FlowCollector r6 = (kotlinx.coroutines.flow.FlowCollector) r6
                        kotlin.ResultKt.throwOnFailure(r8)
                        goto L57
                    L3b:
                        kotlin.ResultKt.throwOnFailure(r8)
                        com.android.systemui.security.data.model.SecurityModel r7 = (com.android.systemui.security.data.model.SecurityModel) r7
                        com.android.systemui.qs.footer.domain.interactor.FooterActionsInteractorImpl$securityButtonConfig$1$1 r8 = new com.android.systemui.qs.footer.domain.interactor.FooterActionsInteractorImpl$securityButtonConfig$1$1
                        com.android.systemui.qs.footer.domain.interactor.FooterActionsInteractorImpl r2 = r6.this$0
                        r8.<init>(r2, r7, r3)
                        kotlinx.coroutines.flow.FlowCollector r7 = r6.$this_unsafeFlow
                        r0.L$0 = r7
                        r0.label = r5
                        kotlinx.coroutines.CoroutineDispatcher r6 = r6.$bgDispatcher$inlined
                        java.lang.Object r8 = kotlinx.coroutines.BuildersKt.withContext(r6, r8, r0)
                        if (r8 != r1) goto L56
                        return r1
                    L56:
                        r6 = r7
                    L57:
                        r0.L$0 = r3
                        r0.label = r4
                        java.lang.Object r6 = r6.emit(r8, r0)
                        if (r6 != r1) goto L62
                        return r1
                    L62:
                        kotlin.Unit r6 = kotlin.Unit.INSTANCE
                        return r6
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.footer.domain.interactor.FooterActionsInteractorImpl$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, coroutineDispatcher, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        this.foregroundServicesCount = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(0);
        this.hasNewForegroundServices = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE);
        this.userSwitcherStatus = ((UserSwitcherRepositoryImpl) userSwitcherRepository).userSwitcherStatus;
        this.deviceMonitoringDialogRequests = broadcastDispatcher.broadcastFlow(new IntentFilter("android.app.action.SHOW_DEVICE_MONITORING_DIALOG"), UserHandle.ALL);
    }

    public final void showDeviceMonitoringDialog(final Context context, final Expandable expandable) {
        final QSSecurityFooterUtils qSSecurityFooterUtils = this.qsSecurityFooterUtils;
        qSSecurityFooterUtils.mShouldUseSettingsButton.set(false);
        qSSecurityFooterUtils.mBgHandler.post(new Runnable() { // from class: com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda28
            @Override // java.lang.Runnable
            public final void run() {
                final QSSecurityFooterUtils qSSecurityFooterUtils2 = QSSecurityFooterUtils.this;
                final Context context2 = context;
                final Expandable expandable2 = expandable;
                final String settingsButton = qSSecurityFooterUtils2.getSettingsButton();
                final View createDialogView = qSSecurityFooterUtils2.createDialogView(context2);
                qSSecurityFooterUtils2.mMainHandler.post(new Runnable() { // from class: com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda30
                    @Override // java.lang.Runnable
                    public final void run() {
                        QSSecurityFooterUtils qSSecurityFooterUtils3 = QSSecurityFooterUtils.this;
                        Context context3 = context2;
                        String str = settingsButton;
                        View view = createDialogView;
                        Expandable expandable3 = expandable2;
                        qSSecurityFooterUtils3.getClass();
                        SystemUIDialog systemUIDialog = new SystemUIDialog(context3, 0);
                        qSSecurityFooterUtils3.mDialog = systemUIDialog;
                        systemUIDialog.requestWindowFeature(1);
                        qSSecurityFooterUtils3.mDialog.setButton(-1, qSSecurityFooterUtils3.mContext.getString(R.string.ok), qSSecurityFooterUtils3);
                        SystemUIDialog systemUIDialog2 = qSSecurityFooterUtils3.mDialog;
                        if (!qSSecurityFooterUtils3.mShouldUseSettingsButton.get()) {
                            str = ((SecurityControllerImpl) qSSecurityFooterUtils3.mSecurityController).isParentalControlsEnabled() ? qSSecurityFooterUtils3.mContext.getString(R.string.monitoring_button_view_controls) : null;
                        }
                        systemUIDialog2.setButton(-2, str, qSSecurityFooterUtils3);
                        qSSecurityFooterUtils3.mDialog.setView(view);
                        DialogTransitionAnimator.Controller dialogTransitionController = expandable3 != null ? expandable3.dialogTransitionController(new DialogCuj(58, "managed_device_info")) : null;
                        if (dialogTransitionController != null) {
                            qSSecurityFooterUtils3.mDialogTransitionAnimator.show(qSSecurityFooterUtils3.mDialog, dialogTransitionController, false);
                        } else {
                            qSSecurityFooterUtils3.mDialog.show();
                        }
                    }
                });
            }
        });
        if (expandable != null) {
            DevicePolicyEventLogger.createEvent(57).write();
        }
    }
}
