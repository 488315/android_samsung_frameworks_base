package com.android.systemui.keyguard.ui.view.layout.sections;

import android.view.View;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.android.systemui.R;
import com.android.systemui.keyguard.shared.model.KeyguardSection;
import kotlin.jvm.internal.DefaultConstructorMarker;

public abstract class BaseShortcutSection extends KeyguardSection {

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final boolean equals(Object obj) {
        return obj instanceof BaseShortcutSection;
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final int hashCode() {
        return -2027574035;
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void removeViews(ConstraintLayout constraintLayout) {
        ExtensionsKt.removeView(constraintLayout, R.id.start_button);
        View findViewById = constraintLayout.findViewById(R.id.end_button);
        if (findViewById != null) {
            constraintLayout.removeView(findViewById);
        }
    }
}
