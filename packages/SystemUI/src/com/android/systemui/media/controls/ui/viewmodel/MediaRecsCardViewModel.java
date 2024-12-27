package com.android.systemui.media.controls.ui.viewmodel;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import java.util.List;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

public final class MediaRecsCardViewModel {
    public final boolean areSubtitlesVisible;
    public final boolean areTitlesVisible;
    public final int cardColor;
    public final int cardTitleColor;
    public final Function1 contentDescription;
    public final GutsViewModel gutsMenu;
    public final List mediaRecs;
    public final Function1 onClicked;
    public final Function0 onLongClicked;

    public MediaRecsCardViewModel(Function1 function1, int i, int i2, Function1 function12, Function0 function0, List<MediaRecViewModel> list, boolean z, boolean z2, GutsViewModel gutsViewModel) {
        this.contentDescription = function1;
        this.cardColor = i;
        this.cardTitleColor = i2;
        this.onClicked = function12;
        this.onLongClicked = function0;
        this.mediaRecs = list;
        this.areTitlesVisible = z;
        this.areSubtitlesVisible = z2;
        this.gutsMenu = gutsViewModel;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MediaRecsCardViewModel)) {
            return false;
        }
        MediaRecsCardViewModel mediaRecsCardViewModel = (MediaRecsCardViewModel) obj;
        return Intrinsics.areEqual(this.contentDescription, mediaRecsCardViewModel.contentDescription) && this.cardColor == mediaRecsCardViewModel.cardColor && this.cardTitleColor == mediaRecsCardViewModel.cardTitleColor && Intrinsics.areEqual(this.onClicked, mediaRecsCardViewModel.onClicked) && Intrinsics.areEqual(this.onLongClicked, mediaRecsCardViewModel.onLongClicked) && Intrinsics.areEqual(this.mediaRecs, mediaRecsCardViewModel.mediaRecs) && this.areTitlesVisible == mediaRecsCardViewModel.areTitlesVisible && this.areSubtitlesVisible == mediaRecsCardViewModel.areSubtitlesVisible && Intrinsics.areEqual(this.gutsMenu, mediaRecsCardViewModel.gutsMenu);
    }

    public final int hashCode() {
        return this.gutsMenu.hashCode() + TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.mediaRecs, (this.onLongClicked.hashCode() + ((this.onClicked.hashCode() + KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.cardTitleColor, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.cardColor, this.contentDescription.hashCode() * 31, 31), 31)) * 31)) * 31, 31), 31, this.areTitlesVisible), 31, this.areSubtitlesVisible);
    }

    public final String toString() {
        return "MediaRecsCardViewModel(contentDescription=" + this.contentDescription + ", cardColor=" + this.cardColor + ", cardTitleColor=" + this.cardTitleColor + ", onClicked=" + this.onClicked + ", onLongClicked=" + this.onLongClicked + ", mediaRecs=" + this.mediaRecs + ", areTitlesVisible=" + this.areTitlesVisible + ", areSubtitlesVisible=" + this.areSubtitlesVisible + ", gutsMenu=" + this.gutsMenu + ")";
    }
}
