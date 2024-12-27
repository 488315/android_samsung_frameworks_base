package com.android.systemui.media.controls.ui.viewmodel;

import android.graphics.drawable.Icon;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import com.android.systemui.controls.controller.ControlInfo$$ExternalSyntheticOutline0;
import com.android.systemui.monet.ColorScheme;
import java.util.List;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class MediaPlayerViewModel {
    public final List actionButtons;
    public final Icon appIcon;
    public final CharSequence artistName;
    public final Icon backgroundCover;
    public final boolean canShowTime;
    public final ColorScheme colorScheme;
    public final Function1 contentDescription;
    public final GutsViewModel gutsMenu;
    public final boolean isExplicitVisible;
    public final com.android.systemui.common.shared.model.Icon launcherIcon;
    public final Function1 onBindSeekbar;
    public final Function1 onClicked;
    public final Function0 onLongClicked;
    public final Function0 onSeek;
    public final MediaOutputSwitcherViewModel outputSwitcher;
    public final boolean playTurbulenceNoise;
    public final boolean shouldAddGradient;
    public final CharSequence titleName;
    public final boolean useGrayColorFilter;
    public final boolean useSemanticActions;

    public MediaPlayerViewModel(Function1 function1, Icon icon, Icon icon2, com.android.systemui.common.shared.model.Icon icon3, boolean z, CharSequence charSequence, CharSequence charSequence2, boolean z2, boolean z3, ColorScheme colorScheme, boolean z4, boolean z5, boolean z6, List<MediaActionViewModel> list, MediaOutputSwitcherViewModel mediaOutputSwitcherViewModel, GutsViewModel gutsViewModel, Function1 function12, Function0 function0, Function0 function02, Function1 function13) {
        this.contentDescription = function1;
        this.backgroundCover = icon;
        this.appIcon = icon2;
        this.launcherIcon = icon3;
        this.useGrayColorFilter = z;
        this.artistName = charSequence;
        this.titleName = charSequence2;
        this.isExplicitVisible = z2;
        this.shouldAddGradient = z3;
        this.colorScheme = colorScheme;
        this.canShowTime = z4;
        this.playTurbulenceNoise = z5;
        this.useSemanticActions = z6;
        this.actionButtons = list;
        this.outputSwitcher = mediaOutputSwitcherViewModel;
        this.gutsMenu = gutsViewModel;
        this.onClicked = function12;
        this.onLongClicked = function0;
        this.onSeek = function02;
        this.onBindSeekbar = function13;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MediaPlayerViewModel)) {
            return false;
        }
        MediaPlayerViewModel mediaPlayerViewModel = (MediaPlayerViewModel) obj;
        return Intrinsics.areEqual(this.contentDescription, mediaPlayerViewModel.contentDescription) && Intrinsics.areEqual(this.backgroundCover, mediaPlayerViewModel.backgroundCover) && Intrinsics.areEqual(this.appIcon, mediaPlayerViewModel.appIcon) && Intrinsics.areEqual(this.launcherIcon, mediaPlayerViewModel.launcherIcon) && this.useGrayColorFilter == mediaPlayerViewModel.useGrayColorFilter && Intrinsics.areEqual(this.artistName, mediaPlayerViewModel.artistName) && Intrinsics.areEqual(this.titleName, mediaPlayerViewModel.titleName) && this.isExplicitVisible == mediaPlayerViewModel.isExplicitVisible && this.shouldAddGradient == mediaPlayerViewModel.shouldAddGradient && Intrinsics.areEqual(this.colorScheme, mediaPlayerViewModel.colorScheme) && this.canShowTime == mediaPlayerViewModel.canShowTime && this.playTurbulenceNoise == mediaPlayerViewModel.playTurbulenceNoise && this.useSemanticActions == mediaPlayerViewModel.useSemanticActions && Intrinsics.areEqual(this.actionButtons, mediaPlayerViewModel.actionButtons) && Intrinsics.areEqual(this.outputSwitcher, mediaPlayerViewModel.outputSwitcher) && Intrinsics.areEqual(this.gutsMenu, mediaPlayerViewModel.gutsMenu) && Intrinsics.areEqual(this.onClicked, mediaPlayerViewModel.onClicked) && Intrinsics.areEqual(this.onLongClicked, mediaPlayerViewModel.onLongClicked) && Intrinsics.areEqual(this.onSeek, mediaPlayerViewModel.onSeek) && Intrinsics.areEqual(this.onBindSeekbar, mediaPlayerViewModel.onBindSeekbar);
    }

    public final int hashCode() {
        int hashCode = this.contentDescription.hashCode() * 31;
        Icon icon = this.backgroundCover;
        int hashCode2 = (hashCode + (icon == null ? 0 : icon.hashCode())) * 31;
        Icon icon2 = this.appIcon;
        return this.onBindSeekbar.hashCode() + ((this.onSeek.hashCode() + ((this.onLongClicked.hashCode() + ((this.onClicked.hashCode() + ((this.gutsMenu.hashCode() + ((this.outputSwitcher.hashCode() + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.actionButtons, TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m((this.colorScheme.hashCode() + TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(ControlInfo$$ExternalSyntheticOutline0.m(ControlInfo$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m((this.launcherIcon.hashCode() + ((hashCode2 + (icon2 != null ? icon2.hashCode() : 0)) * 31)) * 31, 31, this.useGrayColorFilter), 31, this.artistName), 31, this.titleName), 31, this.isExplicitVisible), 31, this.shouldAddGradient)) * 31, 31, this.canShowTime), 31, this.playTurbulenceNoise), 31, this.useSemanticActions), 31)) * 31)) * 31)) * 31)) * 31)) * 31);
    }

    public final String toString() {
        Icon icon = this.backgroundCover;
        Icon icon2 = this.appIcon;
        CharSequence charSequence = this.artistName;
        CharSequence charSequence2 = this.titleName;
        return "MediaPlayerViewModel(contentDescription=" + this.contentDescription + ", backgroundCover=" + icon + ", appIcon=" + icon2 + ", launcherIcon=" + this.launcherIcon + ", useGrayColorFilter=" + this.useGrayColorFilter + ", artistName=" + ((Object) charSequence) + ", titleName=" + ((Object) charSequence2) + ", isExplicitVisible=" + this.isExplicitVisible + ", shouldAddGradient=" + this.shouldAddGradient + ", colorScheme=" + this.colorScheme + ", canShowTime=" + this.canShowTime + ", playTurbulenceNoise=" + this.playTurbulenceNoise + ", useSemanticActions=" + this.useSemanticActions + ", actionButtons=" + this.actionButtons + ", outputSwitcher=" + this.outputSwitcher + ", gutsMenu=" + this.gutsMenu + ", onClicked=" + this.onClicked + ", onLongClicked=" + this.onLongClicked + ", onSeek=" + this.onSeek + ", onBindSeekbar=" + this.onBindSeekbar + ")";
    }
}
