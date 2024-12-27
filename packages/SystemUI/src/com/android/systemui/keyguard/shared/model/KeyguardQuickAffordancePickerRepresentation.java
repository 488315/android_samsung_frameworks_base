package com.android.systemui.keyguard.shared.model;

import android.content.Intent;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class KeyguardQuickAffordancePickerRepresentation {
    public final Intent actionIntent;
    public final String actionText;
    public final Intent configureIntent;
    public final String explanation;
    public final int iconResourceId;
    public final String id;
    public final boolean isEnabled;
    public final String name;

    public KeyguardQuickAffordancePickerRepresentation(String str, String str2, int i, boolean z, String str3, String str4, Intent intent, Intent intent2) {
        this.id = str;
        this.name = str2;
        this.iconResourceId = i;
        this.isEnabled = z;
        this.explanation = str3;
        this.actionText = str4;
        this.actionIntent = intent;
        this.configureIntent = intent2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof KeyguardQuickAffordancePickerRepresentation)) {
            return false;
        }
        KeyguardQuickAffordancePickerRepresentation keyguardQuickAffordancePickerRepresentation = (KeyguardQuickAffordancePickerRepresentation) obj;
        return Intrinsics.areEqual(this.id, keyguardQuickAffordancePickerRepresentation.id) && Intrinsics.areEqual(this.name, keyguardQuickAffordancePickerRepresentation.name) && this.iconResourceId == keyguardQuickAffordancePickerRepresentation.iconResourceId && this.isEnabled == keyguardQuickAffordancePickerRepresentation.isEnabled && Intrinsics.areEqual(this.explanation, keyguardQuickAffordancePickerRepresentation.explanation) && Intrinsics.areEqual(this.actionText, keyguardQuickAffordancePickerRepresentation.actionText) && Intrinsics.areEqual(this.actionIntent, keyguardQuickAffordancePickerRepresentation.actionIntent) && Intrinsics.areEqual(this.configureIntent, keyguardQuickAffordancePickerRepresentation.configureIntent);
    }

    public final int hashCode() {
        int m = TransitionData$$ExternalSyntheticOutline0.m(KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.iconResourceId, PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.id.hashCode() * 31, 31, this.name), 31), 31, this.isEnabled);
        String str = this.explanation;
        int hashCode = (m + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.actionText;
        int hashCode2 = (hashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        Intent intent = this.actionIntent;
        int hashCode3 = (hashCode2 + (intent == null ? 0 : intent.hashCode())) * 31;
        Intent intent2 = this.configureIntent;
        return hashCode3 + (intent2 != null ? intent2.hashCode() : 0);
    }

    public final String toString() {
        return "KeyguardQuickAffordancePickerRepresentation(id=" + this.id + ", name=" + this.name + ", iconResourceId=" + this.iconResourceId + ", isEnabled=" + this.isEnabled + ", explanation=" + this.explanation + ", actionText=" + this.actionText + ", actionIntent=" + this.actionIntent + ", configureIntent=" + this.configureIntent + ")";
    }

    public /* synthetic */ KeyguardQuickAffordancePickerRepresentation(String str, String str2, int i, boolean z, String str3, String str4, Intent intent, Intent intent2, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, i, (i2 & 8) != 0 ? true : z, (i2 & 16) != 0 ? null : str3, (i2 & 32) != 0 ? null : str4, (i2 & 64) != 0 ? null : intent, (i2 & 128) != 0 ? null : intent2);
    }
}
