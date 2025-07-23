package com.android.systemui.media.audiovisseekbar.config;

import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        StringBuilder m = MutableObjectList$$ExternalSyntheticOutline0.m(this.primaryColor, this.secondaryColor, "AudioVisSeekBarConfig(primaryColor=", ", secondaryColor=", ", progressColor=");
        m.append(this.progressColor);
        m.append(", remainTrackColor=");
        m.append(this.remainTrackColor);
        m.append(", remainTrackBorderColor=");
        return ReorderTile$$ExternalSyntheticOutline0.m(this.remainTrackBorderColor, ")", m);
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
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public AudioVisSeekBarConfig(int r1, int r2, int r3, int r4, int r5, int r6, kotlin.jvm.internal.DefaultConstructorMarker r7) {
        /*
            r0 = this;
            r7 = r6 & 1
            if (r7 == 0) goto Lb
            com.android.systemui.media.audiovisseekbar.utils.color.ColorPresetProvider r1 = com.android.systemui.media.audiovisseekbar.utils.color.ColorPresetProvider.INSTANCE
            r1.getClass()
            int r1 = com.android.systemui.media.audiovisseekbar.utils.color.ColorPresetProvider.uxPrimaryColor
        Lb:
            r7 = r6 & 2
            if (r7 == 0) goto L16
            com.android.systemui.media.audiovisseekbar.utils.color.ColorPresetProvider r2 = com.android.systemui.media.audiovisseekbar.utils.color.ColorPresetProvider.INSTANCE
            r2.getClass()
            int r2 = com.android.systemui.media.audiovisseekbar.utils.color.ColorPresetProvider.uxSecondaryColor
        L16:
            r7 = r6 & 4
            if (r7 == 0) goto L21
            com.android.systemui.media.audiovisseekbar.utils.color.ColorPresetProvider r3 = com.android.systemui.media.audiovisseekbar.utils.color.ColorPresetProvider.INSTANCE
            r3.getClass()
            int r3 = com.android.systemui.media.audiovisseekbar.utils.color.ColorPresetProvider.progressTrackColor
        L21:
            r7 = r6 & 8
            if (r7 == 0) goto L2c
            com.android.systemui.media.audiovisseekbar.utils.color.ColorPresetProvider r4 = com.android.systemui.media.audiovisseekbar.utils.color.ColorPresetProvider.INSTANCE
            r4.getClass()
            int r4 = com.android.systemui.media.audiovisseekbar.utils.color.ColorPresetProvider.remainTrackColor
        L2c:
            r6 = r6 & 16
            if (r6 == 0) goto L37
            com.android.systemui.media.audiovisseekbar.utils.color.ColorPresetProvider r5 = com.android.systemui.media.audiovisseekbar.utils.color.ColorPresetProvider.INSTANCE
            r5.getClass()
            int r5 = com.android.systemui.media.audiovisseekbar.utils.color.ColorPresetProvider.remainTrackBorderColor
        L37:
            r6 = r4
            r7 = r5
            r4 = r2
            r5 = r3
            r2 = r0
            r3 = r1
            r2.<init>(r3, r4, r5, r6, r7)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.audiovisseekbar.config.AudioVisSeekBarConfig.<init>(int, int, int, int, int, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }
}
