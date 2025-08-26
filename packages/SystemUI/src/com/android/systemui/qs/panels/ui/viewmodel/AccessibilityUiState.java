package com.android.systemui.qs.panels.ui.viewmodel;

import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import androidx.compose.ui.semantics.Role;
import androidx.compose.ui.state.ToggleableState;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class AccessibilityUiState {
    public final int accessibilityRole;
    public final String clickLabel;
    public final String contentDescription;
    public final String stateDescription;
    public final ToggleableState toggleableState;

    public /* synthetic */ AccessibilityUiState(String str, String str2, int i, ToggleableState toggleableState, String str3, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, i, toggleableState, str3);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AccessibilityUiState)) {
            return false;
        }
        AccessibilityUiState accessibilityUiState = (AccessibilityUiState) obj;
        if (!Intrinsics.areEqual(this.contentDescription, accessibilityUiState.contentDescription) || !Intrinsics.areEqual(this.stateDescription, accessibilityUiState.stateDescription)) {
            return false;
        }
        Role.Companion companion = Role.Companion;
        return this.accessibilityRole == accessibilityUiState.accessibilityRole && this.toggleableState == accessibilityUiState.toggleableState && Intrinsics.areEqual(this.clickLabel, accessibilityUiState.clickLabel);
    }

    public final int hashCode() {
        int iM = PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.contentDescription.hashCode() * 31, 31, this.stateDescription);
        Role.Companion companion = Role.Companion;
        int iM2 = ReorderTile$$ExternalSyntheticOutline0.m(this.accessibilityRole, iM, 31);
        ToggleableState toggleableState = this.toggleableState;
        int iHashCode = (iM2 + (toggleableState == null ? 0 : toggleableState.hashCode())) * 31;
        String str = this.clickLabel;
        return iHashCode + (str != null ? str.hashCode() : 0);
    }

    public final String toString() {
        String strM716toStringimpl = Role.m716toStringimpl(this.accessibilityRole);
        StringBuilder sb = new StringBuilder("AccessibilityUiState(contentDescription=");
        sb.append(this.contentDescription);
        sb.append(", stateDescription=");
        MoveResult$$ExternalSyntheticOutline0.m(sb, this.stateDescription, ", accessibilityRole=", strM716toStringimpl, ", toggleableState=");
        sb.append(this.toggleableState);
        sb.append(", clickLabel=");
        return TransitionKt$$ExternalSyntheticOutline0.m(sb, this.clickLabel, ")");
    }

    private AccessibilityUiState(String str, String str2, int i, ToggleableState toggleableState, String str3) {
        this.contentDescription = str;
        this.stateDescription = str2;
        this.accessibilityRole = i;
        this.toggleableState = toggleableState;
        this.clickLabel = str3;
    }

    public /* synthetic */ AccessibilityUiState(String str, String str2, int i, ToggleableState toggleableState, String str3, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, i, (i2 & 8) != 0 ? null : toggleableState, (i2 & 16) != 0 ? null : str3, null);
    }
}
