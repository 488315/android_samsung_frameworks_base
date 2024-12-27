package com.android.systemui.statusbar.phone;

import android.view.InsetsFlags;
import android.view.ViewDebug;
import com.android.internal.view.AppearanceRegion;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class LetterboxAppearance {
    public final int appearance;
    public final List appearanceRegions;

    public LetterboxAppearance(int i, List<? extends AppearanceRegion> list) {
        this.appearance = i;
        this.appearanceRegions = list;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof LetterboxAppearance)) {
            return false;
        }
        LetterboxAppearance letterboxAppearance = (LetterboxAppearance) obj;
        return this.appearance == letterboxAppearance.appearance && Intrinsics.areEqual(this.appearanceRegions, letterboxAppearance.appearanceRegions);
    }

    public final int hashCode() {
        return this.appearanceRegions.hashCode() + (Integer.hashCode(this.appearance) * 31);
    }

    public final String toString() {
        return "LetterboxAppearance{" + ViewDebug.flagsToString(InsetsFlags.class, "appearance", this.appearance) + ", " + this.appearanceRegions + "}";
    }
}
