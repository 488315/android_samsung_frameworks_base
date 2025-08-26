package com.android.systemui.qs.footer.domain.interactor;

import android.app.admin.DevicePolicyEventLogger;
import android.content.Context;
import android.content.Intent;
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
import com.android.systemui.security.data.model.SecurityModel;
import com.android.systemui.security.data.repository.SecurityRepository;
import com.android.systemui.security.data.repository.SecurityRepositoryImpl;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import com.android.systemui.statusbar.policy.SecurityControllerImpl;
import com.android.systemui.user.data.repository.UserSwitcherRepository;
import com.android.systemui.user.data.repository.UserSwitcherRepositoryImpl;
import com.android.systemui.user.domain.interactor.UserSwitcherInteractor;
import com.sec.ims.volte2.data.VolteConstants;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;

/* loaded from: classes2.dex */
public final class FooterActionsInteractorImpl implements FooterActionsInteractor {
    public final ActivityStarter activityStarter;
    public final Context context;
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

    /* JADX WARN: Type inference failed for: r2v1, types: [com.android.systemui.qs.footer.domain.interactor.FooterActionsInteractorImpl$special$$inlined$map$1] */
    public FooterActionsInteractorImpl(ActivityStarter activityStarter, MetricsLogger metricsLogger, UiEventLogger uiEventLogger, DeviceProvisionedController deviceProvisionedController, QSSecurityFooterUtils qSSecurityFooterUtils, FgsManagerController fgsManagerController, UserSwitcherInteractor userSwitcherInteractor, SecurityRepository securityRepository, ForegroundServicesRepository foregroundServicesRepository, UserSwitcherRepository userSwitcherRepository, BroadcastDispatcher broadcastDispatcher, final CoroutineDispatcher coroutineDispatcher, Context context) {
        this.activityStarter = activityStarter;
        this.metricsLogger = metricsLogger;
        this.uiEventLogger = uiEventLogger;
        this.deviceProvisionedController = deviceProvisionedController;
        this.qsSecurityFooterUtils = qSSecurityFooterUtils;
        this.userSwitcherInteractor = userSwitcherInteractor;
        this.context = context;
        final Flow flow = ((SecurityRepositoryImpl) securityRepository).security;
        this.securityButtonConfig = new Flow() { // from class: com.android.systemui.qs.footer.domain.interactor.FooterActionsInteractorImpl$special$$inlined$map$1

            /* renamed from: com.android.systemui.qs.footer.domain.interactor.FooterActionsInteractorImpl$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ CoroutineDispatcher $bgDispatcher$inlined;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ FooterActionsInteractorImpl this$0;

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

                /* JADX WARN: Code restructure failed: missing block: B:21:0x005f, code lost:
                
                    if (r6.emit(r8, r0) == r1) goto L22;
                 */
                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) throws Throwable {
                    AnonymousClass1 anonymousClass1;
                    FlowCollector flowCollector;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object objWithContext = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(objWithContext);
                        FooterActionsInteractorImpl$securityButtonConfig$1$1 footerActionsInteractorImpl$securityButtonConfig$1$1 = new FooterActionsInteractorImpl$securityButtonConfig$1$1(this.this$0, (SecurityModel) obj, null);
                        FlowCollector flowCollector2 = this.$this_unsafeFlow;
                        anonymousClass1.L$0 = flowCollector2;
                        anonymousClass1.label = 1;
                        objWithContext = BuildersKt.withContext(this.$bgDispatcher$inlined, footerActionsInteractorImpl$securityButtonConfig$1$1, anonymousClass1);
                        if (objWithContext != coroutineSingletons) {
                            flowCollector = flowCollector2;
                        }
                        return coroutineSingletons;
                    }
                    if (i2 != 1) {
                        if (i2 != 2) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(objWithContext);
                        return Unit.INSTANCE;
                    }
                    flowCollector = (FlowCollector) anonymousClass1.L$0;
                    ResultKt.throwOnFailure(objWithContext);
                    anonymousClass1.L$0 = null;
                    anonymousClass1.label = 2;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = flow.collect(new AnonymousClass2(flowCollector, coroutineDispatcher, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        this.foregroundServicesCount = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(0);
        this.hasNewForegroundServices = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE);
        this.userSwitcherStatus = ((UserSwitcherRepositoryImpl) userSwitcherRepository).userSwitcherStatus;
        this.deviceMonitoringDialogRequests = broadcastDispatcher.broadcastFlow(new IntentFilter("android.app.action.SHOW_DEVICE_MONITORING_DIALOG"), UserHandle.ALL, 2, null);
    }

    public final void showDeviceMonitoringDialog(final Context context, final Expandable expandable) {
        final QSSecurityFooterUtils qSSecurityFooterUtils = this.qsSecurityFooterUtils;
        qSSecurityFooterUtils.mShouldUseSettingsButton.set(false);
        qSSecurityFooterUtils.mBgHandler.post(new Runnable() { // from class: com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda29
            @Override // java.lang.Runnable
            public final void run() {
                final QSSecurityFooterUtils qSSecurityFooterUtils2 = qSSecurityFooterUtils;
                final Context context2 = context;
                final Expandable expandable2 = expandable;
                final String settingsButton = qSSecurityFooterUtils2.getSettingsButton();
                final View viewCreateDialogView = qSSecurityFooterUtils2.createDialogView(context2);
                qSSecurityFooterUtils2.mMainHandler.post(new Runnable() { // from class: com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda30
                    @Override // java.lang.Runnable
                    public final void run() {
                        QSSecurityFooterUtils qSSecurityFooterUtils3 = qSSecurityFooterUtils2;
                        Context context3 = context2;
                        String string = settingsButton;
                        View view = viewCreateDialogView;
                        Expandable expandable3 = expandable2;
                        qSSecurityFooterUtils3.getClass();
                        SystemUIDialog systemUIDialog = new SystemUIDialog(context3, 0);
                        qSSecurityFooterUtils3.mDialog = systemUIDialog;
                        systemUIDialog.requestWindowFeature(1);
                        qSSecurityFooterUtils3.mDialog.setButton(-1, qSSecurityFooterUtils3.mContext.getString(R.string.ok), qSSecurityFooterUtils3);
                        SystemUIDialog systemUIDialog2 = qSSecurityFooterUtils3.mDialog;
                        if (!qSSecurityFooterUtils3.mShouldUseSettingsButton.get()) {
                            string = ((SecurityControllerImpl) qSSecurityFooterUtils3.mSecurityController).isParentalControlsEnabled() ? qSSecurityFooterUtils3.mContext.getString(R.string.monitoring_button_view_controls) : null;
                        }
                        systemUIDialog2.setButton(-2, string, qSSecurityFooterUtils3);
                        qSSecurityFooterUtils3.mDialog.setView(view);
                        DialogTransitionAnimator.Controller controllerDialogTransitionController = expandable3 != null ? expandable3.dialogTransitionController(new DialogCuj(58, "managed_device_info")) : null;
                        if (controllerDialogTransitionController != null) {
                            qSSecurityFooterUtils3.mDialogTransitionAnimator.show(qSSecurityFooterUtils3.mDialog, controllerDialogTransitionController, false);
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

    public final void showSettings(Expandable expandable) {
        boolean zIsCurrentUserSetup = ((DeviceProvisionedControllerImpl) this.deviceProvisionedController).isCurrentUserSetup();
        ActivityStarter activityStarter = this.activityStarter;
        if (!zIsCurrentUserSetup) {
            activityStarter.postQSRunnableDismissingKeyguard(new Runnable() { // from class: com.android.systemui.qs.footer.domain.interactor.FooterActionsInteractorImpl.showSettings.1
                @Override // java.lang.Runnable
                public final void run() {
                }
            });
        } else {
            this.metricsLogger.action(VolteConstants.ErrorCode.NOT_ACCEPTABLE);
            activityStarter.startActivity(new Intent("android.settings.SETTINGS"), true, expandable.activityTransitionController(33));
        }
    }
}
