package com.android.compose.animation.scene;

import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.snapshots.SnapshotStateMap;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.unit.IntSize;
import com.android.compose.animation.scene.Element;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ElementStateScopeImpl {
    public final SceneTransitionLayoutImpl layoutImpl;

    public ElementStateScopeImpl(SceneTransitionLayoutImpl sceneTransitionLayoutImpl) {
        this.layoutImpl = sceneTransitionLayoutImpl;
    }

    /* renamed from: targetOffset-GcwITfU, reason: not valid java name */
    public final Offset m922targetOffsetGcwITfU(ContentKey contentKey, ElementKey elementKey) {
        SnapshotStateMap snapshotStateMap;
        Element.State state;
        Element element = (Element) this.layoutImpl.elements.get(elementKey);
        Offset m393boximpl = (element == null || (snapshotStateMap = element.stateByContent) == null || (state = (Element.State) snapshotStateMap.get(contentKey)) == null) ? null : Offset.m393boximpl(state.m920getTargetOffsetF1C5BW0());
        Offset.Companion.getClass();
        if (m393boximpl == null ? false : Offset.m396equalsimpl0(m393boximpl.packedValue, Offset.Unspecified)) {
            return null;
        }
        return m393boximpl;
    }

    /* renamed from: targetSize-GG5KONw, reason: not valid java name */
    public final IntSize m923targetSizeGG5KONw(ContentKey contentKey) {
        IntSize m859boximpl = IntSize.m859boximpl(((IntSize) ((SnapshotMutableStateImpl) this.layoutImpl.content$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout(contentKey).targetSize$delegate).getValue()).packedValue);
        Element.Companion.getClass();
        if (IntSize.m861equalsimpl0(m859boximpl.packedValue, Element.SizeUnspecified)) {
            return null;
        }
        return m859boximpl;
    }
}
