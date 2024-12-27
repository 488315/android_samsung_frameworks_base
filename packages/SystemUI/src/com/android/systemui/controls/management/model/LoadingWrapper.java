package com.android.systemui.controls.management.model;

import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class LoadingWrapper extends StructureElementWrapper {
    public final String subtitle;

    public LoadingWrapper(String str) {
        super(null);
        this.subtitle = str;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof LoadingWrapper) && Intrinsics.areEqual(this.subtitle, ((LoadingWrapper) obj).subtitle);
    }

    public final int hashCode() {
        return this.subtitle.hashCode();
    }

    public final String toString() {
        return ComponentActivity$1$$ExternalSyntheticOutline0.m(new StringBuilder("LoadingWrapper(subtitle="), this.subtitle, ")");
    }
}
