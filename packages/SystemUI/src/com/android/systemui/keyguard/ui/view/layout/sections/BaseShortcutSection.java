package com.android.systemui.keyguard.ui.view.layout.sections;

import android.view.View;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.android.systemui.R;
import com.android.systemui.keyguard.shared.model.KeyguardSection;
import com.android.systemui.keyguard.ui.binder.KeyguardQuickAffordanceViewBinder;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public abstract class BaseShortcutSection extends KeyguardSection {
    public KeyguardQuickAffordanceViewBinder.AnonymousClass1 leftShortcutHandle;
    public KeyguardQuickAffordanceViewBinder.AnonymousClass1 rightShortcutHandle;

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
        KeyguardQuickAffordanceViewBinder.AnonymousClass1 anonymousClass1 = this.leftShortcutHandle;
        if (anonymousClass1 != null) {
            anonymousClass1.destroy();
        }
        this.leftShortcutHandle = null;
        KeyguardQuickAffordanceViewBinder.AnonymousClass1 anonymousClass12 = this.rightShortcutHandle;
        if (anonymousClass12 != null) {
            anonymousClass12.destroy();
        }
        this.rightShortcutHandle = null;
        ExtensionsKt.removeView(constraintLayout, R.id.start_button);
        View viewFindViewById = constraintLayout.findViewById(R.id.end_button);
        if (viewFindViewById != null) {
            constraintLayout.removeView(viewFindViewById);
        }
    }
}
