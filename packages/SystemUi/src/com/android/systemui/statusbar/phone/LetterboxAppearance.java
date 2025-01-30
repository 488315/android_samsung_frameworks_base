package com.android.systemui.statusbar.phone;

import android.view.InsetsFlags;
import android.view.ViewDebug;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import com.android.internal.view.AppearanceRegion;
import java.util.Arrays;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class LetterboxAppearance {
    public final int appearance;
    public final AppearanceRegion[] appearanceRegions;

    public LetterboxAppearance(int i, AppearanceRegion[] appearanceRegionArr) {
        this.appearance = i;
        this.appearanceRegions = appearanceRegionArr;
    }

    public final String toString() {
        return MotionLayout$$ExternalSyntheticOutline0.m22m("LetterboxAppearance{", ViewDebug.flagsToString(InsetsFlags.class, "appearance", this.appearance), ", ", Arrays.toString(this.appearanceRegions), "}");
    }
}
