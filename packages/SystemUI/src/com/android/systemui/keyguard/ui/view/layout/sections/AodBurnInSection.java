package com.android.systemui.keyguard.ui.view.layout.sections;

import android.content.Context;
import android.view.View;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import com.android.systemui.Flags;
import com.android.systemui.R;
import com.android.systemui.keyguard.shared.model.KeyguardSection;
import com.android.systemui.keyguard.ui.view.KeyguardRootView;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardClockViewModel;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class AodBurnInSection extends KeyguardSection {
    public final Context context;

    public AodBurnInSection(Context context, KeyguardRootView keyguardRootView, KeyguardClockViewModel keyguardClockViewModel) {
        this.context = context;
        LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.ui.view.layout.sections.AodBurnInSection$emptyView$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                View view = new View(AodBurnInSection.this.context, null);
                view.setId(R.id.burn_in_layer_empty_view);
                view.setVisibility(8);
                return view;
            }
        });
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void addViews(ConstraintLayout constraintLayout) {
        Flags.migrateClocksToBlueprint();
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void applyConstraints(ConstraintSet constraintSet) {
        Flags.migrateClocksToBlueprint();
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void bindData(ConstraintLayout constraintLayout) {
        Flags.migrateClocksToBlueprint();
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void removeViews(ConstraintLayout constraintLayout) {
        throw null;
    }
}
