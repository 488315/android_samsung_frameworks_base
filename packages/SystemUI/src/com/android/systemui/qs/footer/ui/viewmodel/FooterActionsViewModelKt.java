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
import com.android.systemui.shade.shared.model.ShadeMode;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class FooterActionsViewModelKt {
    public static final FooterActionsViewModel createFooterActionsViewModel(Context context, final FooterActionsInteractor footerActionsInteractor, final ReadonlyStateFlow readonlyStateFlow, final FalsingManager falsingManager, GlobalActionsDialogLite globalActionsDialogLite, ActivityStarter activityStarter, boolean z) {
        final GlobalActionsDialogLite globalActionsDialogLite2;
        Flow flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
        final ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, R.style.Theme_SystemUI_QuickSettings);
        FooterActionsInteractorImpl footerActionsInteractorImpl = (FooterActionsInteractorImpl) footerActionsInteractor;
        final FooterActionsInteractorImpl$special$$inlined$map$1 footerActionsInteractorImpl$special$$inlined$map$1 = footerActionsInteractorImpl.securityButtonConfig;
        Flow distinctUntilChanged = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModelKt$createFooterActionsViewModel$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                /* JADX WARN: Type inference failed for: r4v1, types: [com.android.systemui.qs.footer.ui.viewmodel.FooterActionsSecurityButtonViewModel] */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r7, kotlin.coroutines.Continuation r8) {
                    /*
                        r6 = this;
                        boolean r0 = r8 instanceof com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModelKt$createFooterActionsViewModel$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r8
                        com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModelKt$createFooterActionsViewModel$$inlined$map$1$2$1 r0 = (com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModelKt$createFooterActionsViewModel$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModelKt$createFooterActionsViewModel$$inlined$map$1$2$1 r0 = new com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModelKt$createFooterActionsViewModel$$inlined$map$1$2$1
                        r0.<init>(r8)
                    L18:
                        java.lang.Object r8 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r8)
                        goto L5a
                    L27:
                        java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                        java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                        r6.<init>(r7)
                        throw r6
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r8)
                        com.android.systemui.qs.footer.domain.model.SecurityButtonConfig r7 = (com.android.systemui.qs.footer.domain.model.SecurityButtonConfig) r7
                        r8 = 0
                        if (r7 == 0) goto L4f
                        com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModelKt$createFooterActionsViewModel$security$1$1$1 r2 = new com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModelKt$createFooterActionsViewModel$security$1$1$1
                        com.android.systemui.plugins.FalsingManager r4 = r6.$falsingManager$inlined
                        com.android.systemui.qs.footer.domain.interactor.FooterActionsInteractor r5 = r6.$footerActionsInteractor$inlined
                        r2.<init>(r4, r5)
                        com.android.systemui.qs.footer.ui.viewmodel.FooterActionsSecurityButtonViewModel r4 = new com.android.systemui.qs.footer.ui.viewmodel.FooterActionsSecurityButtonViewModel
                        boolean r5 = r7.isClickable
                        if (r5 == 0) goto L47
                        r8 = r2
                    L47:
                        java.lang.String r2 = r7.text
                        com.android.systemui.common.shared.model.Icon r7 = r7.icon
                        r4.<init>(r7, r2, r8)
                        r8 = r4
                    L4f:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r6 = r6.$this_unsafeFlow
                        java.lang.Object r6 = r6.emit(r8, r0)
                        if (r6 != r1) goto L5a
                        return r1
                    L5a:
                        kotlin.Unit r6 = kotlin.Unit.INSTANCE
                        return r6
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModelKt$createFooterActionsViewModel$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, falsingManager, footerActionsInteractor), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        Flow distinctUntilChanged2 = FlowKt.distinctUntilChanged(FlowKt.combine(footerActionsInteractorImpl.foregroundServicesCount, footerActionsInteractorImpl.hasNewForegroundServices, distinctUntilChanged, new FooterActionsViewModelKt$createFooterActionsViewModel$foregroundServices$1(contextThemeWrapper, falsingManager, activityStarter, footerActionsInteractor, null)));
        Flow distinctUntilChanged3 = FlowKt.distinctUntilChanged(new FooterActionsViewModelKt$userSwitcherViewModel$$inlined$map$1(footerActionsInteractorImpl.userSwitcherStatus, contextThemeWrapper, new FooterActionsViewModelKt$createFooterActionsViewModel$userSwitcher$1(falsingManager, footerActionsInteractor)));
        FooterActionsButtonViewModel footerActionsButtonViewModel = settingsButtonViewModel(contextThemeWrapper, new FooterActionsViewModelKt$createFooterActionsViewModel$settings$1(falsingManager, footerActionsInteractor));
        if (z) {
            globalActionsDialogLite2 = globalActionsDialogLite;
            flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new FooterActionsViewModelKt$powerButtonViewModel$$inlined$map$1(readonlyStateFlow, contextThemeWrapper, new FooterActionsViewModelKt$createFooterActionsViewModel$power$1(falsingManager, footerActionsInteractor, globalActionsDialogLite2));
        } else {
            globalActionsDialogLite2 = globalActionsDialogLite;
            flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(null);
        }
        return new FooterActionsViewModel(distinctUntilChanged, distinctUntilChanged2, distinctUntilChanged3, footerActionsButtonViewModel, flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2, z ? new Function0() { // from class: com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModelKt$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return FooterActionsViewModelKt.powerButtonViewModel(contextThemeWrapper, new FooterActionsViewModelKt$createFooterActionsViewModel$1$1(falsingManager, footerActionsInteractor, globalActionsDialogLite2), (ShadeMode) readonlyStateFlow.$$delegate_0.getValue());
            }
        } : new FooterActionsViewModelKt$$ExternalSyntheticLambda1(), new FooterActionsViewModelKt$createFooterActionsViewModel$3(footerActionsInteractor));
    }

    public static final FooterActionsButtonViewModel powerButtonViewModel(Context context, Function1 function1, ShadeMode shadeMode) {
        boolean z = shadeMode instanceof ShadeMode.Dual;
        return new FooterActionsButtonViewModel(R.id.pm_lite, new Icon.Resource(android.R.drawable.ic_lock_power_off, new ContentDescription.Resource(R.string.accessibility_quick_settings_power_menu)), Integer.valueOf(Utils.getColorAttrDefaultColor(context, z ? R.attr.onShadeInactiveVariant : R.attr.onShadeActive, 0)), z ? R.attr.shadeInactive : R.attr.shadeActive, function1);
    }

    public static final FooterActionsButtonViewModel settingsButtonViewModel(Context context, Function1 function1) {
        return new FooterActionsButtonViewModel(R.id.settings_button_container, new Icon.Resource(R.drawable.ic_settings, new ContentDescription.Resource(R.string.accessibility_quick_settings_settings)), Integer.valueOf(Utils.getColorAttrDefaultColor(context, R.attr.onShadeInactiveVariant, 0)), R.attr.shadeInactive, function1);
    }
}
