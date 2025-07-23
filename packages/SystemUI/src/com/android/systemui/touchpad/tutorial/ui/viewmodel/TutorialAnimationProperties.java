package com.android.systemui.touchpad.tutorial.ui.viewmodel;

import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class TutorialAnimationProperties {
    public final String progressEndMarker;
    public final String progressStartMarker;
    public final int successAnimation;

    public TutorialAnimationProperties(String str, String str2, int i) {
        this.progressStartMarker = str;
        this.progressEndMarker = str2;
        this.successAnimation = i;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TutorialAnimationProperties)) {
            return false;
        }
        TutorialAnimationProperties tutorialAnimationProperties = (TutorialAnimationProperties) obj;
        return Intrinsics.areEqual(this.progressStartMarker, tutorialAnimationProperties.progressStartMarker) && Intrinsics.areEqual(this.progressEndMarker, tutorialAnimationProperties.progressEndMarker) && this.successAnimation == tutorialAnimationProperties.successAnimation;
    }

    public final int hashCode() {
        return Integer.hashCode(this.successAnimation) + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.progressStartMarker.hashCode() * 31, 31, this.progressEndMarker);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("TutorialAnimationProperties(progressStartMarker=");
        sb.append(this.progressStartMarker);
        sb.append(", progressEndMarker=");
        sb.append(this.progressEndMarker);
        sb.append(", successAnimation=");
        return ReorderTile$$ExternalSyntheticOutline0.m(this.successAnimation, ")", sb);
    }
}
