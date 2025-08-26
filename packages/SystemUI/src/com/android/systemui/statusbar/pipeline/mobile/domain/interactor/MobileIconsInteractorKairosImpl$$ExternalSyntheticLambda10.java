package com.android.systemui.statusbar.pipeline.mobile.domain.interactor;

import com.android.systemui.flags.FeatureFlagsClassicRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.kairos.StateInit;
import com.android.systemui.kairos.StateKt;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.jvm.functions.Function2;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt___SequencesKt;

/* loaded from: classes3.dex */
public final /* synthetic */ class MobileIconsInteractorKairosImpl$$ExternalSyntheticLambda10 implements Function2 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ MobileIconsInteractorKairosImpl$$ExternalSyntheticLambda10(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        int i = 1;
        Object obj3 = this.f$0;
        switch (this.$r8$classId) {
            case 0:
                int i2 = MobileIconsInteractorKairosImpl.$r8$clinit;
                Sequence collectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1 = new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1((Collection) obj2);
                Flags.INSTANCE.getClass();
                if (((FeatureFlagsClassicRelease) ((MobileIconsInteractorKairosImpl) obj3).featureFlagsClassic).isEnabled(Flags.FILTER_PROVISIONING_NETWORK_SUBSCRIPTIONS)) {
                    collectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1 = SequencesKt___SequencesKt.filter(collectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1, new MobileIconsInteractorKairosImpl$$ExternalSyntheticLambda17(0));
                }
                return SequencesKt___SequencesKt.toList(SequencesKt___SequencesKt.filter(collectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1, new MobileIconsInteractorKairosImpl$$ExternalSyntheticLambda17(i)));
            case 1:
                Map.Entry entry = (Map.Entry) obj2;
                int i3 = MobileIconsInteractorKairosImpl.$r8$clinit;
                ((Number) entry.getKey()).intValue();
                return new MobileIconsInteractorKairosImpl$$ExternalSyntheticLambda11((MobileIconsInteractorKairosImpl) obj3, (MobileConnectionRepositoryKairos) entry.getValue(), i);
            default:
                int i4 = MobileIconsInteractorKairosImpl.$r8$clinit;
                final int iIntValue = ((Number) ((Map.Entry) obj2).getKey()).intValue();
                return StateKt.map((StateInit) obj3, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorKairosImpl$$ExternalSyntheticLambda25
                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj4, Object obj5) {
                        int i5 = MobileIconsInteractorKairosImpl.$r8$clinit;
                        return Boolean.valueOf(((Set) obj5).contains(Integer.valueOf(iIntValue)));
                    }
                });
        }
    }
}
