package com.android.compose.animation.scene.content;

import androidx.compose.foundation.OverscrollFactory;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.ui.Alignment;
import com.android.compose.animation.scene.ContentKey;
import com.android.compose.animation.scene.OverlayKey;
import com.android.compose.animation.scene.SceneTransitionLayoutImpl;
import com.android.compose.animation.scene.UserAction;
import com.android.compose.animation.scene.UserActionResult;
import java.util.Map;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class Overlay extends Content {
    public final MutableState alignment$delegate;
    public final MutableState isModal$delegate;
    public final OverlayKey key;

    public Overlay(OverlayKey overlayKey, SceneTransitionLayoutImpl sceneTransitionLayoutImpl, Function3 function3, Map<UserAction.Resolved, ? extends UserActionResult> map, float f, long j, Alignment alignment, boolean z, OverscrollFactory overscrollFactory) {
        super(overlayKey, sceneTransitionLayoutImpl, function3, map, f, j, overscrollFactory, null);
        this.key = overlayKey;
        this.alignment$delegate = SnapshotStateKt.mutableStateOf$default(alignment);
        this.isModal$delegate = SnapshotStateKt.mutableStateOf$default(Boolean.valueOf(z));
    }

    @Override // com.android.compose.animation.scene.content.Content
    public final ContentKey getKey() {
        return this.key;
    }

    public final String toString() {
        return "Overlay(key=" + this.key + ")";
    }
}
