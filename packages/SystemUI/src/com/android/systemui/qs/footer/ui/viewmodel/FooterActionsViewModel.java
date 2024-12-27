package com.android.systemui.qs.footer.ui.viewmodel;

import android.content.Context;
import android.view.ContextThemeWrapper;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
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
import javax.inject.Provider;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

public final class FooterActionsViewModel {
    public final StateFlowImpl _alpha;
    public final StateFlowImpl _backgroundAlpha;
    public final ReadonlyStateFlow alpha;
    public final ReadonlyStateFlow backgroundAlpha;
    public final Flow foregroundServices;
    public final Function2 observeDeviceMonitoringDialogRequests;
    public final FooterActionsButtonViewModel power;
    public final Flow security;
    public final FooterActionsButtonViewModel settings;
    public final Flow userSwitcher;

    public final class Factory {
        public final ActivityStarter activityStarter;
        public final Context context;
        public final FalsingManager falsingManager;
        public final FooterActionsInteractor footerActionsInteractor;
        public final Provider globalActionsDialogLiteProvider;
        public final boolean showPowerButton;

        public Factory(Context context, FalsingManager falsingManager, FooterActionsInteractor footerActionsInteractor, Provider provider, ActivityStarter activityStarter, boolean z) {
            this.context = context;
            this.falsingManager = falsingManager;
            this.footerActionsInteractor = footerActionsInteractor;
            this.globalActionsDialogLiteProvider = provider;
            this.activityStarter = activityStarter;
            this.showPowerButton = z;
        }

        public final FooterActionsViewModel create(LifecycleOwner lifecycleOwner) {
            FooterActionsButtonViewModel footerActionsButtonViewModel;
            final GlobalActionsDialogLite globalActionsDialogLite = (GlobalActionsDialogLite) this.globalActionsDialogLiteProvider.get();
            if (lifecycleOwner.getLifecycle().getCurrentState() == Lifecycle.State.DESTROYED) {
                globalActionsDialogLite.destroy();
            } else {
                lifecycleOwner.getLifecycle().addObserver(new DefaultLifecycleObserver() { // from class: com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModel$Factory$create$1
                    @Override // androidx.lifecycle.DefaultLifecycleObserver
                    public final void onDestroy$1() {
                        GlobalActionsDialogLite.this.destroy();
                    }
                });
            }
            Context context = this.context;
            Intrinsics.checkNotNull(globalActionsDialogLite);
            final ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, R.style.Theme_SystemUI_QuickSettings);
            final FooterActionsInteractor footerActionsInteractor = this.footerActionsInteractor;
            FooterActionsInteractorImpl footerActionsInteractorImpl = (FooterActionsInteractorImpl) footerActionsInteractor;
            final FooterActionsInteractorImpl$special$$inlined$map$1 footerActionsInteractorImpl$special$$inlined$map$1 = footerActionsInteractorImpl.securityButtonConfig;
            final FalsingManager falsingManager = this.falsingManager;
            Flow distinctUntilChanged = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModelKt$FooterActionsViewModel$$inlined$map$1

                /* renamed from: com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModelKt$FooterActionsViewModel$$inlined$map$1$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ FalsingManager $falsingManager$inlined;
                    public final /* synthetic */ FooterActionsInteractor $footerActionsInteractor$inlined;
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                    /* renamed from: com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModelKt$FooterActionsViewModel$$inlined$map$1$2$1, reason: invalid class name */
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
                            boolean r0 = r8 instanceof com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModelKt$FooterActionsViewModel$$inlined$map$1.AnonymousClass2.AnonymousClass1
                            if (r0 == 0) goto L13
                            r0 = r8
                            com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModelKt$FooterActionsViewModel$$inlined$map$1$2$1 r0 = (com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModelKt$FooterActionsViewModel$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                            int r1 = r0.label
                            r2 = -2147483648(0xffffffff80000000, float:-0.0)
                            r3 = r1 & r2
                            if (r3 == 0) goto L13
                            int r1 = r1 - r2
                            r0.label = r1
                            goto L18
                        L13:
                            com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModelKt$FooterActionsViewModel$$inlined$map$1$2$1 r0 = new com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModelKt$FooterActionsViewModel$$inlined$map$1$2$1
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
                            com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModelKt$FooterActionsViewModel$security$1$1$1 r2 = new com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModelKt$FooterActionsViewModel$security$1$1$1
                            com.android.systemui.plugins.FalsingManager r4 = r6.$falsingManager$inlined
                            com.android.systemui.qs.footer.domain.interactor.FooterActionsInteractor r5 = r6.$footerActionsInteractor$inlined
                            r2.<init>(r4, r5)
                            com.android.systemui.qs.footer.ui.viewmodel.FooterActionsSecurityButtonViewModel r4 = new com.android.systemui.qs.footer.ui.viewmodel.FooterActionsSecurityButtonViewModel
                            boolean r5 = r7.isClickable
                            if (r5 == 0) goto L47
                            r8 = r2
                        L47:
                            com.android.systemui.common.shared.model.Icon r2 = r7.icon
                            java.lang.String r7 = r7.text
                            r4.<init>(r2, r7, r8)
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
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModelKt$FooterActionsViewModel$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                    Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, falsingManager, footerActionsInteractor), continuation);
                    return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                }
            });
            Flow distinctUntilChanged2 = FlowKt.distinctUntilChanged(FlowKt.combine(footerActionsInteractorImpl.foregroundServicesCount, footerActionsInteractorImpl.hasNewForegroundServices, distinctUntilChanged, new FooterActionsViewModelKt$FooterActionsViewModel$foregroundServices$1(contextThemeWrapper, falsingManager, this.activityStarter, footerActionsInteractor, null)));
            final Flow flow = footerActionsInteractorImpl.userSwitcherStatus;
            Flow distinctUntilChanged3 = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModelKt$FooterActionsViewModel$$inlined$map$2

                /* renamed from: com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModelKt$FooterActionsViewModel$$inlined$map$2$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ FalsingManager $falsingManager$inlined;
                    public final /* synthetic */ FooterActionsInteractor $footerActionsInteractor$inlined;
                    public final /* synthetic */ ContextThemeWrapper $qsThemedContext$inlined;
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                    /* renamed from: com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModelKt$FooterActionsViewModel$$inlined$map$2$2$1, reason: invalid class name */
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

                    public AnonymousClass2(FlowCollector flowCollector, ContextThemeWrapper contextThemeWrapper, FalsingManager falsingManager, FooterActionsInteractor footerActionsInteractor) {
                        this.$this_unsafeFlow = flowCollector;
                        this.$qsThemedContext$inlined = contextThemeWrapper;
                        this.$falsingManager$inlined = falsingManager;
                        this.$footerActionsInteractor$inlined = footerActionsInteractor;
                    }

                    /* JADX WARN: Multi-variable type inference failed */
                    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                    /* JADX WARN: Type inference failed for: r10v0, types: [com.android.systemui.qs.footer.ui.viewmodel.FooterActionsButtonViewModel] */
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final java.lang.Object emit(java.lang.Object r12, kotlin.coroutines.Continuation r13) {
                        /*
                            r11 = this;
                            boolean r0 = r13 instanceof com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModelKt$FooterActionsViewModel$$inlined$map$2.AnonymousClass2.AnonymousClass1
                            if (r0 == 0) goto L13
                            r0 = r13
                            com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModelKt$FooterActionsViewModel$$inlined$map$2$2$1 r0 = (com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModelKt$FooterActionsViewModel$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                            int r1 = r0.label
                            r2 = -2147483648(0xffffffff80000000, float:-0.0)
                            r3 = r1 & r2
                            if (r3 == 0) goto L13
                            int r1 = r1 - r2
                            r0.label = r1
                            goto L18
                        L13:
                            com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModelKt$FooterActionsViewModel$$inlined$map$2$2$1 r0 = new com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModelKt$FooterActionsViewModel$$inlined$map$2$2$1
                            r0.<init>(r13)
                        L18:
                            java.lang.Object r13 = r0.result
                            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                            int r2 = r0.label
                            r3 = 1
                            if (r2 == 0) goto L2f
                            if (r2 != r3) goto L27
                            kotlin.ResultKt.throwOnFailure(r13)
                            goto L92
                        L27:
                            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
                            java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
                            r11.<init>(r12)
                            throw r11
                        L2f:
                            kotlin.ResultKt.throwOnFailure(r13)
                            com.android.systemui.qs.footer.data.model.UserSwitcherStatusModel r12 = (com.android.systemui.qs.footer.data.model.UserSwitcherStatusModel) r12
                            com.android.systemui.qs.footer.data.model.UserSwitcherStatusModel$Disabled r13 = com.android.systemui.qs.footer.data.model.UserSwitcherStatusModel.Disabled.INSTANCE
                            boolean r13 = kotlin.jvm.internal.Intrinsics.areEqual(r12, r13)
                            r2 = 0
                            if (r13 == 0) goto L3e
                            goto L87
                        L3e:
                            boolean r13 = r12 instanceof com.android.systemui.qs.footer.data.model.UserSwitcherStatusModel.Enabled
                            if (r13 == 0) goto L95
                            com.android.systemui.qs.footer.data.model.UserSwitcherStatusModel$Enabled r12 = (com.android.systemui.qs.footer.data.model.UserSwitcherStatusModel.Enabled) r12
                            android.graphics.drawable.Drawable r13 = r12.currentUserImage
                            if (r13 != 0) goto L50
                            java.lang.String r12 = "FooterActionsViewModel"
                            java.lang.String r13 = "Skipped the addition of user switcher button because currentUserImage is missing"
                            android.util.Log.e(r12, r13)
                            goto L87
                        L50:
                            android.view.ContextThemeWrapper r13 = r11.$qsThemedContext$inlined
                            com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModelKt$FooterActionsViewModel$userSwitcher$1$1 r9 = new com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModelKt$FooterActionsViewModel$userSwitcher$1$1
                            com.android.systemui.plugins.FalsingManager r4 = r11.$falsingManager$inlined
                            com.android.systemui.qs.footer.domain.interactor.FooterActionsInteractor r5 = r11.$footerActionsInteractor$inlined
                            r9.<init>(r4, r5)
                            android.graphics.drawable.Drawable r4 = r12.currentUserImage
                            kotlin.jvm.internal.Intrinsics.checkNotNull(r4)
                            com.android.systemui.qs.footer.ui.viewmodel.FooterActionsButtonViewModel r10 = new com.android.systemui.qs.footer.ui.viewmodel.FooterActionsButtonViewModel
                            com.android.systemui.common.shared.model.Icon$Loaded r6 = new com.android.systemui.common.shared.model.Icon$Loaded
                            com.android.systemui.common.shared.model.ContentDescription$Loaded r5 = new com.android.systemui.common.shared.model.ContentDescription$Loaded
                            java.lang.String r12 = r12.currentUserName
                            if (r12 == 0) goto L75
                            r2 = 2131951902(0x7f13011e, float:1.9540232E38)
                            java.lang.Object[] r12 = new java.lang.Object[]{r12}
                            java.lang.String r2 = r13.getString(r2, r12)
                        L75:
                            r5.<init>(r2)
                            r6.<init>(r4, r5)
                            r8 = 2130970084(0x7f0405e4, float:1.7548868E38)
                            r5 = 2131363738(0x7f0a079a, float:1.8347293E38)
                            r7 = 0
                            r4 = r10
                            r4.<init>(r5, r6, r7, r8, r9)
                            r2 = r10
                        L87:
                            r0.label = r3
                            kotlinx.coroutines.flow.FlowCollector r11 = r11.$this_unsafeFlow
                            java.lang.Object r11 = r11.emit(r2, r0)
                            if (r11 != r1) goto L92
                            return r1
                        L92:
                            kotlin.Unit r11 = kotlin.Unit.INSTANCE
                            return r11
                        L95:
                            kotlin.NoWhenBranchMatchedException r11 = new kotlin.NoWhenBranchMatchedException
                            r11.<init>()
                            throw r11
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModelKt$FooterActionsViewModel$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                    Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, contextThemeWrapper, falsingManager, footerActionsInteractor), continuation);
                    return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                }
            });
            FooterActionsButtonViewModel footerActionsButtonViewModel2 = new FooterActionsButtonViewModel(R.id.settings_button_container, new Icon.Resource(R.drawable.ic_settings, new ContentDescription.Resource(R.string.accessibility_quick_settings_settings)), Integer.valueOf(Utils.getColorAttrDefaultColor(contextThemeWrapper, R.attr.onShadeInactiveVariant, 0)), R.attr.shadeInactive, new FooterActionsViewModelKt$FooterActionsViewModel$settings$1(falsingManager, footerActionsInteractor));
            if (this.showPowerButton) {
                footerActionsButtonViewModel = new FooterActionsButtonViewModel(R.id.pm_lite, new Icon.Resource(android.R.drawable.ic_lock_power_off, new ContentDescription.Resource(R.string.accessibility_quick_settings_power_menu)), Integer.valueOf(Utils.getColorAttrDefaultColor(contextThemeWrapper, R.attr.onShadeActive, 0)), R.attr.shadeActive, new FooterActionsViewModelKt$FooterActionsViewModel$power$1(falsingManager, footerActionsInteractor, globalActionsDialogLite));
            } else {
                footerActionsButtonViewModel = null;
            }
            return new FooterActionsViewModel(distinctUntilChanged, distinctUntilChanged2, distinctUntilChanged3, footerActionsButtonViewModel2, footerActionsButtonViewModel, new FooterActionsViewModelKt$FooterActionsViewModel$1(footerActionsInteractor));
        }
    }

    public FooterActionsViewModel(Flow flow, Flow flow2, Flow flow3, FooterActionsButtonViewModel footerActionsButtonViewModel, FooterActionsButtonViewModel footerActionsButtonViewModel2, Function2 function2) {
        Float valueOf = Float.valueOf(1.0f);
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(valueOf);
        this._alpha = MutableStateFlow;
        FlowKt.asStateFlow(MutableStateFlow);
        StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow(valueOf);
        this._backgroundAlpha = MutableStateFlow2;
        FlowKt.asStateFlow(MutableStateFlow2);
    }
}
