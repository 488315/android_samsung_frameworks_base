package com.android.systemui.shade.ui.viewmodel;

import com.android.compose.animation.scene.Edge;
import com.android.compose.animation.scene.OverlayKey;
import com.android.compose.animation.scene.SceneKey;
import com.android.compose.animation.scene.Swipe;
import com.android.compose.animation.scene.UserActionResult;
import com.android.systemui.scene.shared.model.Overlays;
import com.android.systemui.scene.shared.model.Scenes;
import com.android.systemui.scene.shared.model.TransitionKeys;
import com.android.systemui.scene.ui.viewmodel.SceneContainerArea;
import kotlin.Pair;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.collections.builders.ListBuilder;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class ShadeUserActionsKt {
    public static final Pair[] dualShadeActions() {
        Swipe.Companion companion = Swipe.Companion;
        companion.getClass();
        Pair pair = new Pair(Swipe.Down, new UserActionResult.ShowOverlay(Overlays.NotificationsShade, null, false, null, 14, null));
        Swipe m926DownloWS4t8$default = Swipe.Companion.m926DownloWS4t8$default(companion, 0, SceneContainerArea.EndHalf.INSTANCE, 3);
        OverlayKey overlayKey = Overlays.QuickSettingsShade;
        return new Pair[]{pair, new Pair(m926DownloWS4t8$default, new UserActionResult.ShowOverlay(overlayKey, null, false, null, 14, null)), new Pair(Swipe.Companion.m926DownloWS4t8$default(companion, 0, SceneContainerArea.TopEdgeEndHalf.INSTANCE, 3), new UserActionResult.ShowOverlay(overlayKey, null, false, null, 14, null))};
    }

    public static Pair[] singleShadeActions$default(int i, boolean z) {
        if ((i & 1) != 0) {
            z = true;
        }
        boolean z2 = (i & 2) == 0;
        UserActionResult.Companion companion = UserActionResult.Companion;
        UserActionResult.ChangeScene invoke$default = UserActionResult.Companion.invoke$default(companion, Scenes.Shade, null, 6);
        UserActionResult.ChangeScene invoke$default2 = UserActionResult.Companion.invoke$default(companion, Scenes.QuickSettings, null, 6);
        ListBuilder createListBuilder = CollectionsKt__CollectionsJVMKt.createListBuilder();
        Swipe.Companion companion2 = Swipe.Companion;
        companion2.getClass();
        createListBuilder.add(new Pair(Swipe.Down, invoke$default));
        createListBuilder.add(new Pair(Swipe.Companion.m926DownloWS4t8$default(companion2, 2, null, 6), invoke$default));
        if (z) {
            Edge edge = Edge.Top;
            Swipe m926DownloWS4t8$default = Swipe.Companion.m926DownloWS4t8$default(companion2, 1, edge, 2);
            if (!z2) {
                invoke$default = invoke$default2;
            }
            createListBuilder.add(new Pair(m926DownloWS4t8$default, invoke$default));
            createListBuilder.add(new Pair(Swipe.Companion.m926DownloWS4t8$default(companion2, 2, edge, 2), invoke$default2));
        }
        return (Pair[]) createListBuilder.build().toArray(new Pair[0]);
    }

    public static final Pair[] splitShadeActions() {
        UserActionResult.Companion companion = UserActionResult.Companion;
        SceneKey sceneKey = Scenes.Shade;
        TransitionKeys.INSTANCE.getClass();
        UserActionResult.ChangeScene invoke$default = UserActionResult.Companion.invoke$default(companion, sceneKey, TransitionKeys.ToSplitShade, 4);
        Swipe.Companion companion2 = Swipe.Companion;
        companion2.getClass();
        Pair pair = new Pair(Swipe.Down, invoke$default);
        Pair pair2 = new Pair(Swipe.Companion.m926DownloWS4t8$default(companion2, 2, null, 6), invoke$default);
        Edge edge = Edge.Top;
        return new Pair[]{pair, pair2, new Pair(Swipe.Companion.m926DownloWS4t8$default(companion2, 1, edge, 2), invoke$default), new Pair(Swipe.Companion.m926DownloWS4t8$default(companion2, 2, edge, 2), invoke$default)};
    }
}
