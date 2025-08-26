package androidx.compose.material3;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.ui.window.SecureFlagPolicy;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class ModalBottomSheetProperties {
    public final boolean isAppearanceLightNavigationBars;
    public final boolean isAppearanceLightStatusBars;
    public final SecureFlagPolicy securePolicy;
    public final boolean shouldDismissOnBackPress;

    public ModalBottomSheetProperties() {
        this(null, false, false, false, 15, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ModalBottomSheetProperties)) {
            return false;
        }
        ModalBottomSheetProperties modalBottomSheetProperties = (ModalBottomSheetProperties) obj;
        return this.securePolicy == modalBottomSheetProperties.securePolicy && this.isAppearanceLightStatusBars == modalBottomSheetProperties.isAppearanceLightStatusBars && this.isAppearanceLightNavigationBars == modalBottomSheetProperties.isAppearanceLightNavigationBars;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.isAppearanceLightNavigationBars) + TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(this.securePolicy.hashCode() * 31, 31, this.shouldDismissOnBackPress), 31, this.isAppearanceLightStatusBars);
    }

    public ModalBottomSheetProperties(SecureFlagPolicy secureFlagPolicy, boolean z, boolean z2, boolean z3) {
        this.securePolicy = secureFlagPolicy;
        this.shouldDismissOnBackPress = z;
        this.isAppearanceLightStatusBars = z2;
        this.isAppearanceLightNavigationBars = z3;
    }

    public /* synthetic */ ModalBottomSheetProperties(SecureFlagPolicy secureFlagPolicy, boolean z, boolean z2, boolean z3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? SecureFlagPolicy.Inherit : secureFlagPolicy, (i & 2) != 0 ? true : z, (i & 4) != 0 ? true : z2, (i & 8) != 0 ? true : z3);
    }

    public /* synthetic */ ModalBottomSheetProperties(boolean z, boolean z2, boolean z3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? true : z, (i & 2) != 0 ? true : z2, (i & 4) != 0 ? true : z3);
    }

    public ModalBottomSheetProperties(boolean z, boolean z2, boolean z3) {
        this(SecureFlagPolicy.Inherit, z, z2, z3);
    }

    public ModalBottomSheetProperties(SecureFlagPolicy secureFlagPolicy, boolean z, boolean z2) {
        this(secureFlagPolicy, z2, false, false, 12, null);
    }
}
