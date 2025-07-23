package com.android.systemui.statusbar.pipeline.mobile.ui;

import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.KairosActivatable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.kairos.BuildScope;
import com.android.systemui.kairos.BuildScopeKt;
import com.android.systemui.kairos.CombineKt;
import com.android.systemui.kairos.internal.BuildScopeImpl;
import com.android.systemui.statusbar.phone.ui.StatusBarIconController;
import com.android.systemui.statusbar.phone.ui.StatusBarIconControllerImpl;
import com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorKairosImpl;
import com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconsViewModelKairos;
import java.io.PrintWriter;
import java.util.List;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class MobileUiAdapterKairos implements KairosActivatable, Dumpable {
    public final StatusBarIconController iconController;
    public boolean isCollecting;
    public List lastValue;
    public final MobileViewLogger logger;
    public final MobileIconsViewModelKairos mobileIconsViewModel;

    public MobileUiAdapterKairos(StatusBarIconController statusBarIconController, MobileIconsViewModelKairos mobileIconsViewModelKairos, MobileViewLogger mobileViewLogger, DumpManager dumpManager) {
        this.iconController = statusBarIconController;
        this.mobileIconsViewModel = mobileIconsViewModelKairos;
        this.logger = mobileViewLogger;
        dumpManager.registerNormalDumpable(this);
    }

    @Override // com.android.systemui.KairosActivatable
    public final void activate(BuildScope buildScope) {
        BuildScopeKt.launchEffect(buildScope, new MobileUiAdapterKairos$activate$1(this, null));
        MobileIconsViewModelKairos mobileIconsViewModelKairos = this.mobileIconsViewModel;
        ((BuildScopeImpl) buildScope).observe(CombineKt.combine(mobileIconsViewModelKairos.subscriptionIds, ((MobileIconsInteractorKairosImpl) mobileIconsViewModelKairos.interactor).isStackable, new MobileUiAdapterKairos$$ExternalSyntheticLambda0()), new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.MobileUiAdapterKairos$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Pair pair = (Pair) obj2;
                List list = (List) pair.component1();
                boolean booleanValue = ((Boolean) pair.component2()).booleanValue();
                MobileUiAdapterKairos mobileUiAdapterKairos = MobileUiAdapterKairos.this;
                mobileUiAdapterKairos.logger.logUiAdapterSubIdsSentToIconController(list, booleanValue);
                mobileUiAdapterKairos.lastValue = list;
                StatusBarIconController statusBarIconController = mobileUiAdapterKairos.iconController;
                if (booleanValue) {
                    ((StatusBarIconControllerImpl) statusBarIconController).setNewMobileIconSubIds(EmptyList.INSTANCE);
                } else {
                    ((StatusBarIconControllerImpl) statusBarIconController).setNewMobileIconSubIds(list);
                }
                return Unit.INSTANCE;
            }
        });
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "isCollecting=", this.isCollecting);
        printWriter.println("Last values sent to icon controller: " + this.lastValue);
    }
}
