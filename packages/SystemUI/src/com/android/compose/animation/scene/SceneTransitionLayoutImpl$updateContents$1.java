package com.android.compose.animation.scene;

import androidx.compose.foundation.OverscrollFactory;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableFloatStateImpl;
import androidx.compose.runtime.SnapshotMutableLongStateImpl;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.snapshots.SnapshotStateMap;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.compose.ui.unit.LayoutDirection;
import com.android.compose.animation.scene.UserAction;
import com.android.compose.animation.scene.UserActionResult;
import com.android.compose.animation.scene.content.Content;
import com.android.compose.animation.scene.content.ContentEffects;
import com.android.compose.animation.scene.content.Scene;
import com.android.compose.gesture.effect.OffsetOverscrollEffectFactory;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlin.jvm.internal.Ref$IntRef;

/* loaded from: classes.dex */
public final class SceneTransitionLayoutImpl$updateContents$1 {
    public final /* synthetic */ OverscrollFactory $defaultEffectFactory;
    public final /* synthetic */ LayoutDirection $layoutDirection;
    public final /* synthetic */ Ref$BooleanRef $overlaysDefined;
    public final /* synthetic */ Set $overlaysToRemove;
    public final /* synthetic */ long $parentZIndex;
    public final /* synthetic */ Set $scenesToRemove;
    public final /* synthetic */ Ref$IntRef $zIndex;
    public final /* synthetic */ SceneTransitionLayoutImpl this$0;

    public SceneTransitionLayoutImpl$updateContents$1(Ref$BooleanRef ref$BooleanRef, Set<SceneKey> set, SceneTransitionLayoutImpl sceneTransitionLayoutImpl, LayoutDirection layoutDirection, long j, Ref$IntRef ref$IntRef, OverscrollFactory overscrollFactory, Set<OverlayKey> set2) {
        this.$overlaysDefined = ref$BooleanRef;
        this.$scenesToRemove = set;
        this.this$0 = sceneTransitionLayoutImpl;
        this.$layoutDirection = layoutDirection;
        this.$parentZIndex = j;
        this.$zIndex = ref$IntRef;
        this.$defaultEffectFactory = overscrollFactory;
        this.$overlaysToRemove = set2;
    }

    public static void scene$default(SceneTransitionLayoutImpl$updateContents$1 sceneTransitionLayoutImpl$updateContents$1, SceneKey sceneKey, Map map, OffsetOverscrollEffectFactory offsetOverscrollEffectFactory, ComposableLambdaImpl composableLambdaImpl, int i) {
        Map mapEmptyMap = (i & 2) != 0 ? MapsKt__MapsKt.emptyMap() : map;
        OverscrollFactory overscrollFactory = (i & 4) != 0 ? null : offsetOverscrollEffectFactory;
        if (sceneTransitionLayoutImpl$updateContents$1.$overlaysDefined.element) {
            throw new IllegalArgumentException("all scenes must be defined before overlays");
        }
        sceneTransitionLayoutImpl$updateContents$1.$scenesToRemove.remove(sceneKey);
        LayoutDirection layoutDirection = sceneTransitionLayoutImpl$updateContents$1.$layoutDirection;
        SceneTransitionLayoutImpl sceneTransitionLayoutImpl = sceneTransitionLayoutImpl$updateContents$1.this$0;
        sceneTransitionLayoutImpl.getClass();
        LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt__MapsJVMKt.mapCapacity(mapEmptyMap.size()));
        for (Map.Entry entry : mapEmptyMap.entrySet()) {
            linkedHashMap.put(((UserAction) entry.getKey()).resolve$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout(layoutDirection), entry.getValue());
        }
        for (Map.Entry entry2 : linkedHashMap.entrySet()) {
            UserAction.Resolved resolved = (UserAction.Resolved) entry2.getKey();
            UserActionResult userActionResult = (UserActionResult) entry2.getValue();
            if (userActionResult instanceof UserActionResult.ChangeScene) {
                if (Intrinsics.areEqual(sceneKey, ((UserActionResult.ChangeScene) userActionResult).toScene)) {
                    throw new IllegalStateException(("Transition to the same scene is not supported. " + SceneTransitionLayoutImpl.checkUserActions$lambda$17$details(sceneKey, resolved, userActionResult)).toString());
                }
            } else {
                if (userActionResult instanceof UserActionResult.ReplaceByOverlay) {
                    throw new IllegalStateException(AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("ReplaceByOverlay() can only be used for overlays, not scenes. ", SceneTransitionLayoutImpl.checkUserActions$lambda$17$details(sceneKey, resolved, userActionResult)).toString());
                }
                if (!(userActionResult instanceof UserActionResult.ShowOverlay) && !(userActionResult instanceof UserActionResult.HideOverlay)) {
                    throw new NoWhenBranchMatchedException();
                }
            }
        }
        SnapshotStateMap snapshotStateMap = sceneTransitionLayoutImpl.scenes;
        Scene scene = (Scene) snapshotStateMap.get(sceneKey);
        Content.Companion companion = Content.Companion;
        Ref$IntRef ref$IntRef = sceneTransitionLayoutImpl$updateContents$1.$zIndex;
        int i2 = ref$IntRef.element + 1;
        ref$IntRef.element = i2;
        int size = sceneTransitionLayoutImpl.ancestors.size();
        companion.getClass();
        long j = sceneTransitionLayoutImpl$updateContents$1.$parentZIndex;
        if (size < 0 || size >= 6) {
            throw new IllegalArgumentException("NestingDepth of STLs can be at most 5.");
        }
        if (1 > i2 || i2 >= 1000) {
            throw new IllegalArgumentException("A scene can have at most 999 contents.");
        }
        long jPow = (((long) Math.pow(10.0d, (5 - size) * 3)) * i2) + j;
        if (overscrollFactory == null) {
            overscrollFactory = sceneTransitionLayoutImpl$updateContents$1.$defaultEffectFactory;
        }
        OverscrollFactory overscrollFactory2 = overscrollFactory;
        if (scene == null) {
            snapshotStateMap.put(sceneKey, new Scene(sceneKey, sceneTransitionLayoutImpl$updateContents$1.this$0, composableLambdaImpl, linkedHashMap, ref$IntRef.element, jPow, overscrollFactory2, false));
            return;
        }
        if (scene.alwaysCompose) {
            throw new IllegalStateException("scene.alwaysCompose can not change");
        }
        ((SnapshotMutableStateImpl) scene.content$delegate).setValue(composableLambdaImpl);
        ((SnapshotMutableStateImpl) scene.userActions$delegate).setValue(linkedHashMap);
        ((SnapshotMutableFloatStateImpl) scene.zIndex$delegate).setFloatValue(ref$IntRef.element);
        ((SnapshotMutableLongStateImpl) scene.globalZIndex$delegate).setLongValue(jPow);
        MutableState mutableState = scene.lastFactory$delegate;
        if (Intrinsics.areEqual(overscrollFactory2, (OverscrollFactory) ((SnapshotMutableStateImpl) mutableState).getValue())) {
            return;
        }
        ((SnapshotMutableStateImpl) mutableState).setValue(overscrollFactory2);
        ((SnapshotMutableStateImpl) scene.verticalEffects$delegate).setValue(new ContentEffects(overscrollFactory2));
        ((SnapshotMutableStateImpl) scene.horizontalEffects$delegate).setValue(new ContentEffects(overscrollFactory2));
    }
}
