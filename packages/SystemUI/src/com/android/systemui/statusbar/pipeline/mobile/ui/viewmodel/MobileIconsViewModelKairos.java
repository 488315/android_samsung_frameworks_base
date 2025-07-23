package com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel;

import com.android.systemui.KairosBuilder;
import com.android.systemui.KairosBuilderImpl;
import com.android.systemui.flags.FeatureFlagsClassic;
import com.android.systemui.kairos.BuildScope;
import com.android.systemui.kairos.CombineKt;
import com.android.systemui.kairos.Incremental;
import com.android.systemui.kairos.IncrementalKt;
import com.android.systemui.kairos.StateInit;
import com.android.systemui.kairos.StateKt;
import com.android.systemui.kairos.StateKt$$ExternalSyntheticLambda7;
import com.android.systemui.statusbar.pipeline.airplane.domain.interactor.AirplaneModeInteractor;
import com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorKairos;
import com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorKairos;
import com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorKairosImpl;
import com.android.systemui.statusbar.pipeline.mobile.ui.MobileViewLogger;
import com.android.systemui.statusbar.pipeline.mobile.ui.VerboseMobileViewLogger;
import com.android.systemui.statusbar.pipeline.shared.ConnectivityConstants;
import java.util.Map;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class MobileIconsViewModelKairos implements KairosBuilder {
    public final /* synthetic */ KairosBuilderImpl $$delegate_0;
    public final AirplaneModeInteractor airplaneModeInteractor;
    public final ConnectivityConstants constants;
    public final FeatureFlagsClassic flags;
    public final Incremental icons;
    public final MobileIconsInteractorKairos interactor;
    public final MobileViewLogger logger;
    public final StateInit subscriptionIds;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Module {
        public static final Module INSTANCE = new Module();

        private Module() {
        }
    }

    public MobileIconsViewModelKairos(MobileViewLogger mobileViewLogger, VerboseMobileViewLogger verboseMobileViewLogger, MobileIconsInteractorKairos mobileIconsInteractorKairos, AirplaneModeInteractor airplaneModeInteractor, ConnectivityConstants connectivityConstants, FeatureFlagsClassic featureFlagsClassic) {
        KairosBuilderImpl kairosBuilderImpl = new KairosBuilderImpl();
        this.$$delegate_0 = kairosBuilderImpl;
        this.logger = mobileViewLogger;
        this.interactor = mobileIconsInteractorKairos;
        this.airplaneModeInteractor = airplaneModeInteractor;
        this.constants = connectivityConstants;
        this.flags = featureFlagsClassic;
        this.subscriptionIds = StateKt.map(((MobileIconsInteractorKairosImpl) mobileIconsInteractorKairos).filteredSubscriptions, new MobileIconsViewModelKairos$$ExternalSyntheticLambda0(0));
        final int i = 0;
        this.icons = kairosBuilderImpl.buildIncremental(new Function1(this) { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconsViewModelKairos$$ExternalSyntheticLambda1
            public final /* synthetic */ MobileIconsViewModelKairos f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i) {
                    case 0:
                        final MobileIconsViewModelKairos mobileIconsViewModelKairos = this.f$0;
                        return BuildScope.DefaultImpls.applyLatestSpecForKey$default(buildScope, IncrementalKt.mapValues(((MobileIconsInteractorKairosImpl) mobileIconsViewModelKairos.interactor).icons, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconsViewModelKairos$$ExternalSyntheticLambda3
                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj2, Object obj3) {
                                Map.Entry entry = (Map.Entry) obj3;
                                final int intValue = ((Number) entry.getKey()).intValue();
                                final MobileIconInteractorKairos mobileIconInteractorKairos = (MobileIconInteractorKairos) entry.getValue();
                                final MobileIconsViewModelKairos mobileIconsViewModelKairos2 = MobileIconsViewModelKairos.this;
                                return new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconsViewModelKairos$$ExternalSyntheticLambda7
                                    @Override // kotlin.jvm.functions.Function1
                                    /* renamed from: invoke */
                                    public final Object mo779invoke(Object obj4) {
                                        MobileIconsViewModelKairos$commonViewModel$1 mobileIconsViewModelKairos$commonViewModel$1 = new MobileIconsViewModelKairos$commonViewModel$1(intValue, mobileIconInteractorKairos, mobileIconsViewModelKairos2);
                                        MobileIconsViewModelKairos mobileIconsViewModelKairos3 = mobileIconsViewModelKairos$commonViewModel$1.this$0;
                                        MobileIconViewModelKairos mobileIconViewModelKairos = new MobileIconViewModelKairos(mobileIconsViewModelKairos$commonViewModel$1.$subId, mobileIconsViewModelKairos$commonViewModel$1.$iconInteractor, mobileIconsViewModelKairos3.airplaneModeInteractor, mobileIconsViewModelKairos3.constants, mobileIconsViewModelKairos3.flags);
                                        mobileIconViewModelKairos.activate((BuildScope) obj4);
                                        return mobileIconViewModelKairos;
                                    }
                                };
                            }
                        }));
                    default:
                        MobileIconsViewModelKairos mobileIconsViewModelKairos2 = this.f$0;
                        return StateKt.flatMap(CombineKt.combine(StateKt.map(mobileIconsViewModelKairos2.subscriptionIds, new MobileIconsViewModelKairos$$ExternalSyntheticLambda0(2)), mobileIconsViewModelKairos2.icons, new MobileIconsViewModelKairos$$ExternalSyntheticLambda6()), new StateKt$$ExternalSyntheticLambda7());
                }
            }
        });
        final int i2 = 1;
        this.$$delegate_0.buildState(new Function1(this) { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconsViewModelKairos$$ExternalSyntheticLambda1
            public final /* synthetic */ MobileIconsViewModelKairos f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i2) {
                    case 0:
                        final MobileIconsViewModelKairos mobileIconsViewModelKairos = this.f$0;
                        return BuildScope.DefaultImpls.applyLatestSpecForKey$default(buildScope, IncrementalKt.mapValues(((MobileIconsInteractorKairosImpl) mobileIconsViewModelKairos.interactor).icons, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconsViewModelKairos$$ExternalSyntheticLambda3
                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj2, Object obj3) {
                                Map.Entry entry = (Map.Entry) obj3;
                                final int intValue = ((Number) entry.getKey()).intValue();
                                final MobileIconInteractorKairos mobileIconInteractorKairos = (MobileIconInteractorKairos) entry.getValue();
                                final MobileIconsViewModelKairos mobileIconsViewModelKairos2 = MobileIconsViewModelKairos.this;
                                return new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconsViewModelKairos$$ExternalSyntheticLambda7
                                    @Override // kotlin.jvm.functions.Function1
                                    /* renamed from: invoke */
                                    public final Object mo779invoke(Object obj4) {
                                        MobileIconsViewModelKairos$commonViewModel$1 mobileIconsViewModelKairos$commonViewModel$1 = new MobileIconsViewModelKairos$commonViewModel$1(intValue, mobileIconInteractorKairos, mobileIconsViewModelKairos2);
                                        MobileIconsViewModelKairos mobileIconsViewModelKairos3 = mobileIconsViewModelKairos$commonViewModel$1.this$0;
                                        MobileIconViewModelKairos mobileIconViewModelKairos = new MobileIconViewModelKairos(mobileIconsViewModelKairos$commonViewModel$1.$subId, mobileIconsViewModelKairos$commonViewModel$1.$iconInteractor, mobileIconsViewModelKairos3.airplaneModeInteractor, mobileIconsViewModelKairos3.constants, mobileIconsViewModelKairos3.flags);
                                        mobileIconViewModelKairos.activate((BuildScope) obj4);
                                        return mobileIconViewModelKairos;
                                    }
                                };
                            }
                        }));
                    default:
                        MobileIconsViewModelKairos mobileIconsViewModelKairos2 = this.f$0;
                        return StateKt.flatMap(CombineKt.combine(StateKt.map(mobileIconsViewModelKairos2.subscriptionIds, new MobileIconsViewModelKairos$$ExternalSyntheticLambda0(2)), mobileIconsViewModelKairos2.icons, new MobileIconsViewModelKairos$$ExternalSyntheticLambda6()), new StateKt$$ExternalSyntheticLambda7());
                }
            }
        });
    }

    @Override // com.android.systemui.KairosActivatable
    public final void activate(BuildScope buildScope) {
        this.$$delegate_0.activate(buildScope);
    }

    @Override // com.android.systemui.KairosBuilder
    public final void onActivated(Function1 function1) {
        this.$$delegate_0.onActivated(function1);
    }
}
