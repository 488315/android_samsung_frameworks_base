package com.android.systemui.media.dialog;

import com.android.internal.graphics.ColorUtils;
import com.android.systemui.monet.ColorScheme;
import com.android.systemui.monet.TonalPalette;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class MediaOutputColorSchemeLegacyDynamic extends MediaOutputColorSchemeLegacy {
    public final int mColorButtonBackground;
    public final int mColorConnectedItemBackground;
    public final int mColorDialogBackground;
    public final int mColorItemBackground;
    public final int mColorItemContent;
    public final int mColorPositiveButtonText;
    public final int mColorSeekbarProgress;

    public MediaOutputColorSchemeLegacyDynamic(ColorScheme colorScheme, boolean z) {
        if (z) {
            this.mColorItemContent = colorScheme.mAccent1.getS100();
            TonalPalette tonalPalette = colorScheme.mAccent2;
            this.mColorSeekbarProgress = ((Integer) tonalPalette.allShades.get(8)).intValue();
            this.mColorButtonBackground = ((Integer) colorScheme.mAccent1.allShades.get(5)).intValue();
            this.mColorItemBackground = colorScheme.mNeutral2.getS800();
            this.mColorConnectedItemBackground = tonalPalette.getS800();
            this.mColorPositiveButtonText = tonalPalette.getS800();
            this.mColorDialogBackground = ((Integer) colorScheme.mNeutral1.allShades.get(11)).intValue();
            return;
        }
        this.mColorItemContent = colorScheme.mAccent1.getS800();
        TonalPalette tonalPalette2 = colorScheme.mAccent1;
        this.mColorSeekbarProgress = ((Integer) tonalPalette2.allShades.get(5)).intValue();
        this.mColorButtonBackground = ((Integer) tonalPalette2.allShades.get(8)).intValue();
        this.mColorItemBackground = ((Integer) colorScheme.mAccent2.allShades.get(2)).intValue();
        this.mColorConnectedItemBackground = tonalPalette2.getS100();
        TonalPalette tonalPalette3 = colorScheme.mNeutral1;
        this.mColorPositiveButtonText = ((Integer) tonalPalette3.allShades.get(2)).intValue();
        this.mColorDialogBackground = ColorUtils.setAlphaComponent(colorScheme.mIsDark ? tonalPalette3.getS700() : ((Integer) tonalPalette3.allShades.get(1)).intValue(), 255);
    }

    @Override // com.android.systemui.media.dialog.MediaOutputColorSchemeLegacy
    public final int getColorButtonBackground() {
        return this.mColorButtonBackground;
    }

    @Override // com.android.systemui.media.dialog.MediaOutputColorSchemeLegacy
    public final int getColorConnectedItemBackground() {
        return this.mColorConnectedItemBackground;
    }

    @Override // com.android.systemui.media.dialog.MediaOutputColorSchemeLegacy
    public final int getColorDialogBackground() {
        return this.mColorDialogBackground;
    }

    @Override // com.android.systemui.media.dialog.MediaOutputColorSchemeLegacy
    public final int getColorItemBackground() {
        return this.mColorItemBackground;
    }

    @Override // com.android.systemui.media.dialog.MediaOutputColorSchemeLegacy
    public final int getColorItemContent() {
        return this.mColorItemContent;
    }

    @Override // com.android.systemui.media.dialog.MediaOutputColorSchemeLegacy
    public final int getColorPositiveButtonText() {
        return this.mColorPositiveButtonText;
    }

    @Override // com.android.systemui.media.dialog.MediaOutputColorSchemeLegacy
    public final int getColorSeekbarProgress() {
        return this.mColorSeekbarProgress;
    }
}
