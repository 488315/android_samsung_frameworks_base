package com.android.compose.animation.scene;

import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.snapshots.SnapshotStateMap;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.unit.IntSize;
import com.android.compose.animation.scene.Element;

/* loaded from: classes.dex */
public final class ElementStateScopeImpl {
    public final SceneTransitionLayoutImpl layoutImpl;

    public ElementStateScopeImpl(SceneTransitionLayoutImpl sceneTransitionLayoutImpl) {
        this.layoutImpl = sceneTransitionLayoutImpl;
    }

    /* renamed from: targetOffset-GcwITfU, reason: not valid java name */
    public final Offset m924targetOffsetGcwITfU(ContentKey contentKey, ElementKey elementKey) {
        SnapshotStateMap snapshotStateMap;
        Element.State state;
        Element element = (Element) this.layoutImpl.elements.get(elementKey);
        Offset offsetM395boximpl = (element == null || (snapshotStateMap = element.stateByContent) == null || (state = (Element.State) snapshotStateMap.get(contentKey)) == null) ? null : Offset.m395boximpl(state.m922getTargetOffsetF1C5BW0());
        Offset.Companion.getClass();
        if (offsetM395boximpl == null ? false : Offset.m398equalsimpl0(offsetM395boximpl.packedValue, Offset.Unspecified)) {
            return null;
        }
        return offsetM395boximpl;
    }

    /* renamed from: targetSize-GG5KONw, reason: not valid java name */
    public final IntSize m925targetSizeGG5KONw(ContentKey contentKey) {
        IntSize intSizeM861boximpl = IntSize.m861boximpl(((IntSize) ((SnapshotMutableStateImpl) this.layoutImpl.content$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout(contentKey).targetSize$delegate).getValue()).packedValue);
        Element.Companion.getClass();
        if (IntSize.m863equalsimpl0(intSizeM861boximpl.packedValue, Element.SizeUnspecified)) {
            return null;
        }
        return intSizeM861boximpl;
    }
}
