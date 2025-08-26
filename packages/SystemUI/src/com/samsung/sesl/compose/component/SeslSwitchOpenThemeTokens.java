package com.samsung.sesl.compose.component;

import androidx.compose.ui.graphics.painter.Painter;
import com.android.systemui.media.mediaoutput.entity.AudioMirroringDevice$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes4.dex */
public final class SeslSwitchOpenThemeTokens {
    public final Painter thumb;
    public final Painter thumbDisabledOff;
    public final Painter thumbDisabledOn;
    public final Painter thumbOff;
    public final Painter thumbOn;
    public final Painter trackDisabledOff;
    public final Painter trackDisabledOn;
    public final Painter trackOff;
    public final Painter trackOn;

    public SeslSwitchOpenThemeTokens(Painter painter, Painter painter2, Painter painter3, Painter painter4, Painter painter5, Painter painter6, Painter painter7, Painter painter8, Painter painter9) {
        this.trackOn = painter;
        this.trackDisabledOn = painter2;
        this.trackOff = painter3;
        this.trackDisabledOff = painter4;
        this.thumbOn = painter5;
        this.thumbDisabledOn = painter6;
        this.thumbOff = painter7;
        this.thumbDisabledOff = painter8;
        this.thumb = painter9;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SeslSwitchOpenThemeTokens)) {
            return false;
        }
        SeslSwitchOpenThemeTokens seslSwitchOpenThemeTokens = (SeslSwitchOpenThemeTokens) obj;
        return Intrinsics.areEqual(this.trackOn, seslSwitchOpenThemeTokens.trackOn) && Intrinsics.areEqual(this.trackDisabledOn, seslSwitchOpenThemeTokens.trackDisabledOn) && Intrinsics.areEqual(this.trackOff, seslSwitchOpenThemeTokens.trackOff) && Intrinsics.areEqual(this.trackDisabledOff, seslSwitchOpenThemeTokens.trackDisabledOff) && Intrinsics.areEqual(this.thumbOn, seslSwitchOpenThemeTokens.thumbOn) && Intrinsics.areEqual(this.thumbDisabledOn, seslSwitchOpenThemeTokens.thumbDisabledOn) && Intrinsics.areEqual(this.thumbOff, seslSwitchOpenThemeTokens.thumbOff) && Intrinsics.areEqual(this.thumbDisabledOff, seslSwitchOpenThemeTokens.thumbDisabledOff) && Intrinsics.areEqual(this.thumb, seslSwitchOpenThemeTokens.thumb);
    }

    public final int hashCode() {
        return this.thumb.hashCode() + AudioMirroringDevice$$ExternalSyntheticOutline0.m(this.thumbDisabledOff, AudioMirroringDevice$$ExternalSyntheticOutline0.m(this.thumbOff, AudioMirroringDevice$$ExternalSyntheticOutline0.m(this.thumbDisabledOn, AudioMirroringDevice$$ExternalSyntheticOutline0.m(this.thumbOn, AudioMirroringDevice$$ExternalSyntheticOutline0.m(this.trackDisabledOff, AudioMirroringDevice$$ExternalSyntheticOutline0.m(this.trackOff, AudioMirroringDevice$$ExternalSyntheticOutline0.m(this.trackDisabledOn, this.trackOn.hashCode() * 31, 31), 31), 31), 31), 31), 31), 31);
    }

    public final String toString() {
        return "SeslSwitchOpenThemeTokens(trackOn=" + this.trackOn + ", trackDisabledOn=" + this.trackDisabledOn + ", trackOff=" + this.trackOff + ", trackDisabledOff=" + this.trackDisabledOff + ", thumbOn=" + this.thumbOn + ", thumbDisabledOn=" + this.thumbDisabledOn + ", thumbOff=" + this.thumbOff + ", thumbDisabledOff=" + this.thumbDisabledOff + ", thumb=" + this.thumb + ")";
    }
}
