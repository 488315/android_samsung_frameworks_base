package com.android.systemui.qs.footer.ui.viewmodel;

import android.content.Context;
import android.view.ContextThemeWrapper;
import com.android.settingslib.Utils;
import com.android.systemui.R;
import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.globalactions.GlobalActionsDialogLite;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.qs.footer.domain.interactor.FooterActionsInteractor;
import com.android.systemui.qs.footer.domain.interactor.FooterActionsInteractorImpl;
import com.android.systemui.qs.footer.domain.interactor.FooterActionsInteractorImpl$special$$inlined$map$1;
import com.android.systemui.qs.footer.domain.model.SecurityButtonConfig;
import com.android.systemui.shade.shared.model.ShadeMode;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes2.dex */
public abstract class FooterActionsViewModelKt {

    /* renamed from: com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModelKt$createFooterActionsViewModel$3, reason: invalid class name */
    final /* synthetic */ class AnonymousClass3 extends FunctionReferenceImpl implements Function2 {
        final /* synthetic */ FooterActionsInteractor $footerActionsInteractor;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass3(FooterActionsInteractor footerActionsInteractor) {
            super(2, Intrinsics.Kotlin.class, "observeDeviceMonitoringDialogRequests", "createFooterActionsViewModel$observeDeviceMonitoringDialogRequests(Lcom/android/systemui/qs/footer/domain/interactor/FooterActionsInteractor;Landroid/content/Context;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", 0);
            this.$footerActionsInteractor = footerActionsInteractor;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            final Context context = (Context) obj;
            final FooterActionsInteractor footerActionsInteractor = this.$footerActionsInteractor;
            Object objCollect = ((FooterActionsInteractorImpl) footerActionsInteractor).deviceMonitoringDialogRequests.collect(new FlowCollector() { // from class: com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModelKt$createFooterActionsViewModel$observeDeviceMonitoringDialogRequests$2
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj3, Continuation continuation) {
                    ((FooterActionsInteractorImpl) footerActionsInteractor).showDeviceMonitoringDialog(context, null);
                    return Unit.INSTANCE;
                }
            }, (Continuation) obj2);
            return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
        }
    }

    public static final FooterActionsViewModel createFooterActionsViewModel(Context context, final FooterActionsInteractor footerActionsInteractor, final ReadonlyStateFlow readonlyStateFlow, final FalsingManager falsingManager, GlobalActionsDialogLite globalActionsDialogLite, ActivityStarter activityStarter, boolean z) {
        final GlobalActionsDialogLite globalActionsDialogLite2;
        Flow flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
        final ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, R.style.Theme_SystemUI_QuickSettings);
        FooterActionsInteractorImpl footerActionsInteractorImpl = (FooterActionsInteractorImpl) footerActionsInteractor;
        final FooterActionsInteractorImpl$special$$inlined$map$1 footerActionsInteractorImpl$special$$inlined$map$1 = footerActionsInteractorImpl.securityButtonConfig;
        Flow flowDistinctUntilChanged = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModelKt$createFooterActionsViewModel$$inlined$map$1

            /* renamed from: com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModelKt$createFooterActionsViewModel$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FalsingManager $falsingManager$inlined;
                public final /* synthetic */ FooterActionsInteractor $footerActionsInteractor$inlined;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModelKt$createFooterActionsViewModel$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, FalsingManager falsingManager, FooterActionsInteractor footerActionsInteractor) {
                    this.$this_unsafeFlow = flowCollector;
                    this.$falsingManager$inlined = falsingManager;
                    this.$footerActionsInteractor$inlined = footerActionsInteractor;
                }

                /* JADX WARN: Multi-variable type inference failed */
                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                /* JADX WARN: Type inference failed for: r4v1, types: [com.android.systemui.qs.footer.ui.viewmodel.FooterActionsSecurityButtonViewModel] */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
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
                        SecurityButtonConfig securityButtonConfig = (SecurityButtonConfig) obj;
                        if (securityButtonConfig != null) {
                            footerActionsSecurityButtonViewModel = new FooterActionsSecurityButtonViewModel(securityButtonConfig.icon, securityButtonConfig.text, securityButtonConfig.isClickable ? new FooterActionsViewModelKt$createFooterActionsViewModel$security$1$1$1(this.$falsingManager$inlined, this.$footerActionsInteractor$inlined) : null);
                        }
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(footerActionsSecurityButtonViewModel, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = footerActionsInteractorImpl$special$$inlined$map$1.collect(new AnonymousClass2(flowCollector, falsingManager, footerActionsInteractor), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        });
        Flow flowDistinctUntilChanged2 = FlowKt.distinctUntilChanged(FlowKt.combine(footerActionsInteractorImpl.foregroundServicesCount, footerActionsInteractorImpl.hasNewForegroundServices, flowDistinctUntilChanged, new FooterActionsViewModelKt$createFooterActionsViewModel$foregroundServices$1(contextThemeWrapper, falsingManager, activityStarter, footerActionsInteractor, null)));
        Flow flowDistinctUntilChanged3 = FlowKt.distinctUntilChanged(new FooterActionsViewModelKt$userSwitcherViewModel$$inlined$map$1(footerActionsInteractorImpl.userSwitcherStatus, contextThemeWrapper, new FooterActionsViewModelKt$createFooterActionsViewModel$userSwitcher$1(falsingManager, footerActionsInteractor)));
        FooterActionsButtonViewModel footerActionsButtonViewModel = settingsButtonViewModel(contextThemeWrapper, new FooterActionsViewModelKt$createFooterActionsViewModel$settings$1(falsingManager, footerActionsInteractor));
        if (z) {
            globalActionsDialogLite2 = globalActionsDialogLite;
            flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new FooterActionsViewModelKt$powerButtonViewModel$$inlined$map$1(readonlyStateFlow, contextThemeWrapper, new FooterActionsViewModelKt$createFooterActionsViewModel$power$1(falsingManager, footerActionsInteractor, globalActionsDialogLite2));
        } else {
            globalActionsDialogLite2 = globalActionsDialogLite;
            flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(null);
        }
        return new FooterActionsViewModel(flowDistinctUntilChanged, flowDistinctUntilChanged2, flowDistinctUntilChanged3, footerActionsButtonViewModel, flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2, z ? new Function0() { // from class: com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModelKt$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return FooterActionsViewModelKt.powerButtonViewModel(contextThemeWrapper, new FooterActionsViewModelKt$createFooterActionsViewModel$1$1(falsingManager, footerActionsInteractor, globalActionsDialogLite2), (ShadeMode) readonlyStateFlow.$$delegate_0.getValue());
            }
        } : new FooterActionsViewModelKt$$ExternalSyntheticLambda1(), new AnonymousClass3(footerActionsInteractor));
    }

    public static final FooterActionsButtonViewModel powerButtonViewModel(Context context, Function1 function1, ShadeMode shadeMode) {
        boolean z = shadeMode instanceof ShadeMode.Dual;
        return new FooterActionsButtonViewModel(R.id.pm_lite, new Icon.Resource(android.R.drawable.ic_lock_power_off, new ContentDescription.Resource(R.string.accessibility_quick_settings_power_menu)), Integer.valueOf(Utils.getColorAttrDefaultColor(context, z ? R.attr.onShadeInactiveVariant : R.attr.onShadeActive, 0)), z ? R.attr.shadeInactive : R.attr.shadeActive, function1);
    }

    public static final FooterActionsButtonViewModel settingsButtonViewModel(Context context, Function1 function1) {
        return new FooterActionsButtonViewModel(R.id.settings_button_container, new Icon.Resource(R.drawable.ic_settings, new ContentDescription.Resource(R.string.accessibility_quick_settings_settings)), Integer.valueOf(Utils.getColorAttrDefaultColor(context, R.attr.onShadeInactiveVariant, 0)), R.attr.shadeInactive, function1);
    }
}
