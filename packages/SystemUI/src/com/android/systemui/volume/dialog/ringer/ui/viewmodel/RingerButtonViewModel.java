package com.android.systemui.volume.dialog.ringer.ui.viewmodel;

import com.android.settingslib.volume.shared.model.RingerMode;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.util.Set;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class RingerButtonViewModel {
    public final int contentDescriptionResId;
    public final int hintLabelResId;
    public final int imageResId;
    public final int ringerMode;

    public /* synthetic */ RingerButtonViewModel(int i, int i2, int i3, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, i2, i3, i4);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RingerButtonViewModel)) {
            return false;
        }
        RingerButtonViewModel ringerButtonViewModel = (RingerButtonViewModel) obj;
        if (this.imageResId != ringerButtonViewModel.imageResId || this.contentDescriptionResId != ringerButtonViewModel.contentDescriptionResId || this.hintLabelResId != ringerButtonViewModel.hintLabelResId) {
            return false;
        }
        Set set = RingerMode.supportedRingerModes;
        return this.ringerMode == ringerButtonViewModel.ringerMode;
    }

    public final int hashCode() {
        int iM = ReorderTile$$ExternalSyntheticOutline0.m(this.hintLabelResId, ReorderTile$$ExternalSyntheticOutline0.m(this.contentDescriptionResId, Integer.hashCode(this.imageResId) * 31, 31), 31);
        Set set = RingerMode.supportedRingerModes;
        return Integer.hashCode(this.ringerMode) + iM;
    }

    public final String toString() {
        return "RingerButtonViewModel(imageResId=" + this.imageResId + ", contentDescriptionResId=" + this.contentDescriptionResId + ", hintLabelResId=" + this.hintLabelResId + ", ringerMode=" + RingerMode.m994toStringimpl(this.ringerMode) + ")";
    }

    private RingerButtonViewModel(int i, int i2, int i3, int i4) {
        this.imageResId = i;
        this.contentDescriptionResId = i2;
        this.hintLabelResId = i3;
        this.ringerMode = i4;
    }
}
