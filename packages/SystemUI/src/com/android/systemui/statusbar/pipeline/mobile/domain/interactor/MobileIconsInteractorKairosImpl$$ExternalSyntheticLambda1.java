package com.android.systemui.statusbar.pipeline.mobile.domain.interactor;

import com.android.settingslib.mobile.MobileMappings;
import com.android.systemui.kairos.BuildScope;
import com.android.systemui.kairos.BuildScopeKt;
import com.android.systemui.kairos.State;
import com.android.systemui.kairos.StateKt;
import com.android.systemui.kairos.internal.BuildScopeImpl;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos;
import com.android.systemui.statusbar.pipeline.shared.data.model.ConnectivitySlot;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.jvm.functions.Function2;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlin.sequences.TransformingSequence;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class MobileIconsInteractorKairosImpl$$ExternalSyntheticLambda1 implements Function2 {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ MobileIconsInteractorKairosImpl$$ExternalSyntheticLambda1(int i) {
        this.$r8$classId = i;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        State dataEnabled;
        switch (this.$r8$classId) {
            case 0:
                int i = MobileIconsInteractorKairosImpl.$r8$clinit;
                return Boolean.valueOf(((MobileMappings.Config) obj2).alwaysShowCdmaRssi);
            case 1:
                BuildScope buildScope = (BuildScope) obj;
                int i2 = MobileIconsInteractorKairosImpl.$r8$clinit;
                return ((BuildScopeImpl) buildScope).stateScope.holdState(BuildScopeKt.asyncEvent(buildScope, new MobileIconsInteractorKairosImpl$forcingCellularValidation$1$1$1(null)), Boolean.TRUE);
            case 2:
                int i3 = MobileIconsInteractorKairosImpl.$r8$clinit;
                return Boolean.valueOf(((MobileMappings.Config) obj2).alwaysShowDataRatIcon);
            case 3:
                int i4 = MobileIconsInteractorKairosImpl.$r8$clinit;
                return Boolean.valueOf(((Set) obj2).contains(ConnectivitySlot.MOBILE));
            case 4:
                int i5 = MobileIconsInteractorKairosImpl.$r8$clinit;
                return SequencesKt___SequencesKt.toSet(new TransformingSequence(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1((List) obj2), new MobileIconsInteractorKairosImpl$$ExternalSyntheticLambda17(2)));
            case 5:
                int i6 = MobileIconsInteractorKairosImpl.$r8$clinit;
                return Boolean.valueOf(((Collection) obj2).size() == 1);
            default:
                MobileConnectionRepositoryKairos mobileConnectionRepositoryKairos = (MobileConnectionRepositoryKairos) obj2;
                int i7 = MobileIconsInteractorKairosImpl.$r8$clinit;
                return (mobileConnectionRepositoryKairos == null || (dataEnabled = mobileConnectionRepositoryKairos.getDataEnabled()) == null) ? StateKt.stateOf(Boolean.FALSE) : dataEnabled;
        }
    }
}
