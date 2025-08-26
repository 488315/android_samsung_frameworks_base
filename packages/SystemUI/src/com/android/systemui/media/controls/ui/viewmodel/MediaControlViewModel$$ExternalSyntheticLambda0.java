package com.android.systemui.media.controls.ui.viewmodel;

import com.android.systemui.media.controls.ui.viewmodel.MediaControlViewModel;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final /* synthetic */ class MediaControlViewModel$$ExternalSyntheticLambda0 implements Function2 {
    /* JADX WARN: Removed duplicated region for block: B:67:0x00f2 A[EDGE_INSN: B:48:0x00b4->B:67:0x00f2 BREAK  A[LOOP:0: B:27:0x0064->B:47:0x00b2], EDGE_INSN: B:73:0x00f2->B:67:0x00f2 BREAK  A[LOOP:0: B:27:0x0064->B:47:0x00b2], EDGE_INSN: B:74:0x00f2->B:67:0x00f2 BREAK  A[LOOP:0: B:27:0x0064->B:47:0x00b2], EDGE_INSN: B:75:0x00f2->B:67:0x00f2 BREAK  A[LOOP:0: B:27:0x0064->B:47:0x00b2], EDGE_INSN: B:76:0x00f2->B:67:0x00f2 BREAK  A[LOOP:0: B:27:0x0064->B:47:0x00b2], EDGE_INSN: B:77:0x00f2->B:67:0x00f2 BREAK  A[LOOP:0: B:27:0x0064->B:47:0x00b2], EDGE_INSN: B:78:0x00f2->B:67:0x00f2 BREAK  A[LOOP:0: B:27:0x0064->B:47:0x00b2], EDGE_INSN: B:79:0x00f2->B:67:0x00f2 BREAK  A[LOOP:0: B:27:0x0064->B:47:0x00b2]] */
    /* JADX WARN: Removed duplicated region for block: B:69:0x00f5  */
    @Override // kotlin.jvm.functions.Function2
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invoke(Object obj, Object obj2) {
        boolean z;
        boolean z2;
        MediaPlayerViewModel mediaPlayerViewModel = (MediaPlayerViewModel) obj;
        MediaPlayerViewModel mediaPlayerViewModel2 = (MediaPlayerViewModel) obj2;
        MediaControlViewModel.Companion companion = MediaControlViewModel.Companion;
        if (mediaPlayerViewModel2 != null || mediaPlayerViewModel != null) {
            if (mediaPlayerViewModel2 != null) {
                mediaPlayerViewModel2.getClass();
                if (mediaPlayerViewModel != null && Intrinsics.areEqual(mediaPlayerViewModel.backgroundCover, mediaPlayerViewModel2.backgroundCover) && Intrinsics.areEqual(mediaPlayerViewModel2.appIcon, mediaPlayerViewModel.appIcon) && mediaPlayerViewModel2.useGrayColorFilter == mediaPlayerViewModel.useGrayColorFilter && Intrinsics.areEqual(mediaPlayerViewModel2.artistName, mediaPlayerViewModel.artistName) && Intrinsics.areEqual(mediaPlayerViewModel2.titleName, mediaPlayerViewModel.titleName) && mediaPlayerViewModel2.isExplicitVisible == mediaPlayerViewModel.isExplicitVisible && mediaPlayerViewModel2.canShowTime == mediaPlayerViewModel.canShowTime && mediaPlayerViewModel2.playTurbulenceNoise == mediaPlayerViewModel.playTurbulenceNoise && mediaPlayerViewModel2.useSemanticActions == mediaPlayerViewModel.useSemanticActions) {
                    List list = mediaPlayerViewModel.actionButtons;
                    Iterator it = mediaPlayerViewModel2.actionButtons.iterator();
                    int i = 0;
                    while (true) {
                        if (it.hasNext()) {
                            Object next = it.next();
                            int i2 = i + 1;
                            if (i < 0) {
                                CollectionsKt__CollectionsKt.throwIndexOverflow();
                                throw null;
                            }
                            MediaActionViewModel mediaActionViewModel = (MediaActionViewModel) next;
                            MediaActionViewModel mediaActionViewModel2 = (MediaActionViewModel) list.get(i);
                            if (mediaActionViewModel2 == null) {
                                mediaActionViewModel.getClass();
                                break;
                            }
                            if (!Intrinsics.areEqual(mediaActionViewModel.contentDescription, mediaActionViewModel2.contentDescription) || mediaActionViewModel.isVisibleWhenScrubbing != mediaActionViewModel2.isVisibleWhenScrubbing || mediaActionViewModel.notVisibleValue != mediaActionViewModel2.notVisibleValue || mediaActionViewModel.showInCollapsed != mediaActionViewModel2.showInCollapsed || !Intrinsics.areEqual(mediaActionViewModel.rebindId, mediaActionViewModel2.rebindId) || !Intrinsics.areEqual(mediaActionViewModel.buttonId, mediaActionViewModel2.buttonId) || mediaActionViewModel.isEnabled != mediaActionViewModel2.isEnabled) {
                                break;
                            }
                            i = i2;
                        } else {
                            MediaOutputSwitcherViewModel mediaOutputSwitcherViewModel = mediaPlayerViewModel2.outputSwitcher;
                            MediaOutputSwitcherViewModel mediaOutputSwitcherViewModel2 = mediaPlayerViewModel.outputSwitcher;
                            if (mediaOutputSwitcherViewModel2 == null) {
                                mediaOutputSwitcherViewModel.getClass();
                            } else {
                                if (mediaOutputSwitcherViewModel.isTapEnabled != mediaOutputSwitcherViewModel2.isTapEnabled || !Intrinsics.areEqual(mediaOutputSwitcherViewModel.deviceString, mediaOutputSwitcherViewModel2.deviceString) || mediaOutputSwitcherViewModel.isCurrentBroadcastApp != mediaOutputSwitcherViewModel2.isCurrentBroadcastApp || mediaOutputSwitcherViewModel.isIntentValid != mediaOutputSwitcherViewModel2.isIntentValid || mediaOutputSwitcherViewModel.alpha != mediaOutputSwitcherViewModel2.alpha || mediaOutputSwitcherViewModel.isVisible != mediaOutputSwitcherViewModel2.isVisible) {
                                    break;
                                }
                                z = true;
                            }
                        }
                    }
                } else {
                    z = false;
                    z2 = z;
                }
            }
        }
        return Boolean.valueOf(z2);
    }
}
