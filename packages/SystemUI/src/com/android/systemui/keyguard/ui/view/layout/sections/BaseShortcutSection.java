package com.android.systemui.keyguard.ui.view.layout.sections;

import android.view.View;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.android.systemui.R;
import com.android.systemui.keyguard.shared.model.KeyguardSection;
import com.android.systemui.keyguard.ui.binder.KeyguardQuickAffordanceViewBinder$bind$1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class BaseShortcutSection extends KeyguardSection {
    public KeyguardQuickAffordanceViewBinder$bind$1 leftShortcutHandle;
    public KeyguardQuickAffordanceViewBinder$bind$1 rightShortcutHandle;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
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
        KeyguardQuickAffordanceViewBinder$bind$1 keyguardQuickAffordanceViewBinder$bind$1 = this.leftShortcutHandle;
        if (keyguardQuickAffordanceViewBinder$bind$1 != null) {
            keyguardQuickAffordanceViewBinder$bind$1.destroy();
        }
        this.leftShortcutHandle = null;
        KeyguardQuickAffordanceViewBinder$bind$1 keyguardQuickAffordanceViewBinder$bind$12 = this.rightShortcutHandle;
        if (keyguardQuickAffordanceViewBinder$bind$12 != null) {
            keyguardQuickAffordanceViewBinder$bind$12.destroy();
        }
        this.rightShortcutHandle = null;
        ExtensionsKt.removeView(constraintLayout, R.id.start_button);
        View findViewById = constraintLayout.findViewById(R.id.end_button);
        if (findViewById != null) {
            constraintLayout.removeView(findViewById);
        }
    }
}
