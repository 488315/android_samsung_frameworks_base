package com.android.systemui.media.audiovisseekbar.config;

import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import com.android.systemui.media.audiovisseekbar.utils.color.ColorPresetProvider;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class AudioVisSeekBarConfig {
    public int primaryColor;
    public final int progressColor;
    public final int remainTrackBorderColor;
    public final int remainTrackColor;
    public int secondaryColor;

    public AudioVisSeekBarConfig() {
        this(0, 0, 0, 0, 0, 31, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AudioVisSeekBarConfig)) {
            return false;
        }
        AudioVisSeekBarConfig audioVisSeekBarConfig = (AudioVisSeekBarConfig) obj;
        return this.primaryColor == audioVisSeekBarConfig.primaryColor && this.secondaryColor == audioVisSeekBarConfig.secondaryColor && this.progressColor == audioVisSeekBarConfig.progressColor && this.remainTrackColor == audioVisSeekBarConfig.remainTrackColor && this.remainTrackBorderColor == audioVisSeekBarConfig.remainTrackBorderColor;
    }

    public final int hashCode() {
        return Integer.hashCode(this.remainTrackBorderColor) + ReorderTile$$ExternalSyntheticOutline0.m(this.remainTrackColor, ReorderTile$$ExternalSyntheticOutline0.m(this.progressColor, ReorderTile$$ExternalSyntheticOutline0.m(this.secondaryColor, Integer.hashCode(this.primaryColor) * 31, 31), 31), 31);
    }

    public final String toString() {
        StringBuilder sbM = MutableObjectList$$ExternalSyntheticOutline0.m(this.primaryColor, this.secondaryColor, "AudioVisSeekBarConfig(primaryColor=", ", secondaryColor=", ", progressColor=");
        sbM.append(this.progressColor);
        sbM.append(", remainTrackColor=");
        sbM.append(this.remainTrackColor);
        sbM.append(", remainTrackBorderColor=");
        return ReorderTile$$ExternalSyntheticOutline0.m(this.remainTrackBorderColor, ")", sbM);
    }

    public AudioVisSeekBarConfig(int i, int i2, int i3, int i4, int i5) {
        this.primaryColor = i;
        this.secondaryColor = i2;
        this.progressColor = i3;
        this.remainTrackColor = i4;
        this.remainTrackBorderColor = i5;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public AudioVisSeekBarConfig(int i, int i2, int i3, int i4, int i5, int i6, DefaultConstructorMarker defaultConstructorMarker) {
        if ((i6 & 1) != 0) {
            ColorPresetProvider.INSTANCE.getClass();
            i = ColorPresetProvider.uxPrimaryColor;
        }
        if ((i6 & 2) != 0) {
            ColorPresetProvider.INSTANCE.getClass();
            i2 = ColorPresetProvider.uxSecondaryColor;
        }
        if ((i6 & 4) != 0) {
            ColorPresetProvider.INSTANCE.getClass();
            i3 = ColorPresetProvider.progressTrackColor;
        }
        if ((i6 & 8) != 0) {
            ColorPresetProvider.INSTANCE.getClass();
            i4 = ColorPresetProvider.remainTrackColor;
        }
        if ((i6 & 16) != 0) {
            ColorPresetProvider.INSTANCE.getClass();
            i5 = ColorPresetProvider.remainTrackBorderColor;
        }
        int i7 = i5;
        int i8 = i3;
        int i9 = i;
        this(i9, i2, i8, i4, i7);
    }
}
