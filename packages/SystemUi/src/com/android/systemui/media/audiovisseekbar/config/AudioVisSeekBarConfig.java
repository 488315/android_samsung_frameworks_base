package com.android.systemui.media.audiovisseekbar.config;

import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;
import androidx.picker.model.viewdata.AppInfoViewData$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0;
import com.android.systemui.media.audiovisseekbar.utils.color.ColorPresetProvider;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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
        return Integer.hashCode(this.remainTrackBorderColor) + AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.remainTrackColor, AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.progressColor, AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.secondaryColor, Integer.hashCode(this.primaryColor) * 31, 31), 31), 31);
    }

    public final String toString() {
        StringBuilder m45m = GridLayoutManager$$ExternalSyntheticOutline0.m45m("AudioVisSeekBarConfig(primaryColor=", this.primaryColor, ", secondaryColor=", this.secondaryColor, ", progressColor=");
        m45m.append(this.progressColor);
        m45m.append(", remainTrackColor=");
        m45m.append(this.remainTrackColor);
        m45m.append(", remainTrackBorderColor=");
        return ConstraintWidget$$ExternalSyntheticOutline0.m19m(m45m, this.remainTrackBorderColor, ")");
    }

    public AudioVisSeekBarConfig(int i, int i2, int i3, int i4, int i5) {
        this.primaryColor = i;
        this.secondaryColor = i2;
        this.progressColor = i3;
        this.remainTrackColor = i4;
        this.remainTrackBorderColor = i5;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public AudioVisSeekBarConfig(int i, int i2, int i3, int i4, int i5, int i6, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, r10, r0, r1, i5);
        if ((i6 & 1) != 0) {
            ColorPresetProvider.INSTANCE.getClass();
            i = ColorPresetProvider.uxPrimaryColor;
        }
        if ((i6 & 2) != 0) {
            ColorPresetProvider.INSTANCE.getClass();
            i2 = ColorPresetProvider.uxSecondaryColor;
        }
        int i7 = i2;
        if ((i6 & 4) != 0) {
            ColorPresetProvider.INSTANCE.getClass();
            i3 = ColorPresetProvider.progressTrackColor;
        }
        int i8 = i3;
        if ((i6 & 8) != 0) {
            ColorPresetProvider.INSTANCE.getClass();
            i4 = ColorPresetProvider.remainTrackColor;
        }
        int i9 = i4;
        if ((i6 & 16) != 0) {
            ColorPresetProvider.INSTANCE.getClass();
            i5 = ColorPresetProvider.remainTrackBorderColor;
        }
    }
}
