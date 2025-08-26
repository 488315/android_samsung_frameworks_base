package com.android.compose.animation.scene;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.DecayAnimationSpec;
import androidx.compose.foundation.ClickableKt;
import androidx.compose.foundation.OverscrollFactory;
import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.foundation.interaction.InteractionSourceKt;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScope;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotMutableFloatStateImpl;
import androidx.compose.runtime.SnapshotMutableLongStateImpl;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.runtime.snapshots.SnapshotStateMap;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.ZIndexModifierKt;
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection;
import androidx.compose.ui.input.nestedscroll.NestedScrollDispatcher;
import androidx.compose.ui.input.nestedscroll.NestedScrollModifierKt;
import androidx.compose.ui.layout.LookaheadScope;
import androidx.compose.ui.layout.LookaheadScopeKt;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.IntSize;
import androidx.compose.ui.unit.LayoutDirection;
import com.android.compose.animation.scene.Back;
import com.android.compose.animation.scene.UserAction;
import com.android.compose.animation.scene.UserActionResult;
import com.android.compose.animation.scene.content.Content;
import com.android.compose.animation.scene.content.ContentEffects;
import com.android.compose.animation.scene.content.Overlay;
import com.android.compose.animation.scene.content.Scene;
import com.android.compose.animation.scene.content.state.TransitionState;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.collections.builders.ListBuilder;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlin.jvm.internal.Ref$IntRef;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes.dex */
public final class SceneTransitionLayoutImpl {
    public LookaheadScope _lookaheadScope;
    public SnapshotStateMap _overlays;
    public UserActionDistanceScopeImpl _userActionDistanceScope;
    public final List ancestors;
    public final CoroutineScope animationScope;
    public DecayAnimationSpec decayAnimationSpec;
    public Density density;
    public final float directionChangeSlop;
    public final ElementStateScopeImpl elementStateScope;
    public final Map elements;
    public final DraggableHandler horizontalDraggableHandler;
    public final boolean implicitTestTags;
    public long lastSize;
    public LayoutDirection layoutDirection;
    public final SceneTransitionLayoutImpl$nestedScrollConnection$1 nestedScrollConnection;
    public final NestedScrollDispatcher nestedScrollDispatcher;
    public final PropertyTransformationScopeImpl propertyTransformationScope;
    public final SnapshotStateMap scenes;
    public final List scenesToAlwaysCompose;
    public final MutableSceneTransitionLayoutStateImpl state;
    public SwipeDetector swipeDetector;
    public SwipeSourceDetector swipeSourceDetector;
    public final DraggableHandler verticalDraggableHandler;

    public final class SceneToCompose {
        public final boolean isInvisible;
        public final Scene scene;

        public SceneToCompose(Scene scene, boolean z) {
            this.scene = scene;
            this.isInvisible = z;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof SceneToCompose)) {
                return false;
            }
            SceneToCompose sceneToCompose = (SceneToCompose) obj;
            return Intrinsics.areEqual(this.scene, sceneToCompose.scene) && this.isInvisible == sceneToCompose.isInvisible;
        }

        public final int hashCode() {
            return Boolean.hashCode(this.isInvisible) + (this.scene.hashCode() * 31);
        }

        public final String toString() {
            return "SceneToCompose(scene=" + this.scene + ", isInvisible=" + this.isInvisible + ")";
        }
    }

    /* JADX WARN: Type inference failed for: r2v7, types: [com.android.compose.animation.scene.SceneTransitionLayoutImpl$nestedScrollConnection$1] */
    public SceneTransitionLayoutImpl(MutableSceneTransitionLayoutStateImpl mutableSceneTransitionLayoutStateImpl, Density density, LayoutDirection layoutDirection, SwipeSourceDetector swipeSourceDetector, SwipeDetector swipeDetector, float f, DecayAnimationSpec<Float> decayAnimationSpec, Function1 function1, CoroutineScope coroutineScope, float f2, Map<ElementKey, Element> map, List<Ancestor> list, boolean z, LookaheadScope lookaheadScope, OverscrollFactory overscrollFactory) {
        this.state = mutableSceneTransitionLayoutStateImpl;
        this.density = density;
        this.layoutDirection = layoutDirection;
        this.swipeSourceDetector = swipeSourceDetector;
        this.swipeDetector = swipeDetector;
        this.decayAnimationSpec = decayAnimationSpec;
        this.animationScope = coroutineScope;
        this.directionChangeSlop = f2;
        this.elements = map;
        this.ancestors = list;
        this.implicitTestTags = z;
        this._lookaheadScope = lookaheadScope;
        this.scenes = new SnapshotStateMap();
        this.elementStateScope = new ElementStateScopeImpl(this);
        this.propertyTransformationScope = new PropertyTransformationScopeImpl(this);
        IntSize.Companion.getClass();
        this.lastSize = 0L;
        this.nestedScrollDispatcher = new NestedScrollDispatcher();
        this.nestedScrollConnection = new NestedScrollConnection() { // from class: com.android.compose.animation.scene.SceneTransitionLayoutImpl$nestedScrollConnection$1
        };
        updateContents$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout(function1, this.layoutDirection, overscrollFactory);
        final int i = 0;
        this.horizontalDraggableHandler = new DraggableHandler(this, Orientation.Horizontal, new Function1(this) { // from class: com.android.compose.animation.scene.SceneTransitionLayoutImpl$$ExternalSyntheticLambda1
            public final /* synthetic */ SceneTransitionLayoutImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                ContentKey contentKey = (ContentKey) obj;
                switch (i) {
                    case 0:
                        return ((ContentEffects) ((SnapshotMutableStateImpl) this.f$0.content$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout(contentKey).horizontalEffects$delegate).getValue()).gestureEffect;
                    default:
                        return ((ContentEffects) ((SnapshotMutableStateImpl) this.f$0.content$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout(contentKey).verticalEffects$delegate).getValue()).gestureEffect;
                }
            }
        });
        final int i2 = 1;
        this.verticalDraggableHandler = new DraggableHandler(this, Orientation.Vertical, new Function1(this) { // from class: com.android.compose.animation.scene.SceneTransitionLayoutImpl$$ExternalSyntheticLambda1
            public final /* synthetic */ SceneTransitionLayoutImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                ContentKey contentKey = (ContentKey) obj;
                switch (i2) {
                    case 0:
                        return ((ContentEffects) ((SnapshotMutableStateImpl) this.f$0.content$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout(contentKey).horizontalEffects$delegate).getValue()).gestureEffect;
                    default:
                        return ((ContentEffects) ((SnapshotMutableStateImpl) this.f$0.content$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout(contentKey).verticalEffects$delegate).getValue()).gestureEffect;
                }
            }
        });
        mutableSceneTransitionLayoutStateImpl.checkThread$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout();
    }

    public static final String checkUserActions$lambda$17$details(ContentKey contentKey, UserAction.Resolved resolved, UserActionResult userActionResult) {
        return "Content " + contentKey + ", action " + resolved + ", result " + userActionResult + ".";
    }

    public static final void overlaysToComposeOrderedByZIndex$lambda$37$maybeAdd$34(Set set, ListBuilder listBuilder, SceneTransitionLayoutImpl sceneTransitionLayoutImpl, OverlayKey overlayKey) {
        if (set.add(overlayKey)) {
            listBuilder.add(sceneTransitionLayoutImpl.overlay$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout(overlayKey));
        }
    }

    public static void scenesToCompose$lambda$25$maybeAdd$default(Set set, ListBuilder listBuilder, SceneTransitionLayoutImpl sceneTransitionLayoutImpl, SceneKey sceneKey) {
        if (set.add(sceneKey)) {
            listBuilder.add(new SceneToCompose(sceneTransitionLayoutImpl.scene$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout(sceneKey), false));
        }
    }

    public final void BackHandler(int i, Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-770610215);
        int i2 = (composerImpl.changed(this) ? 4 : 2) | i;
        if ((i2 & 3) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.compose.animation.scene.SceneTransitionLayoutImpl.BackHandler (SceneTransitionLayoutImpl.kt:484)");
            }
            PredictiveBackHandlerKt.PredictiveBackHandler(this, (UserActionResult) ((Map) ((SnapshotMutableStateImpl) contentForUserActions$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout().userActions$delegate).getValue()).get(Back.Resolved.INSTANCE), composerImpl, i2 & 14);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new SceneTransitionLayoutImpl$$ExternalSyntheticLambda3(this, i, 1);
        }
    }

    public final void Content$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout(final Modifier modifier, Composer composer, final int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1154610664);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changed(modifier) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changed(this) ? 32 : 16;
        }
        if ((i2 & 19) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.compose.animation.scene.SceneTransitionLayoutImpl.Content (SceneTransitionLayoutImpl.kt:455)");
            }
            Modifier modifierThen = SwipeToSceneKt.swipeToScene(SwipeToSceneKt.swipeToScene(NestedScrollModifierKt.nestedScroll(modifier, this.nestedScrollConnection, this.nestedScrollDispatcher), this.horizontalDraggableHandler), this.verticalDraggableHandler).then(new LayoutElement(this, this.state.getTransitionState()));
            Alignment.Companion.getClass();
            MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierThen);
            ComposeUiNode.Companion.getClass();
            Function0 function0 = ComposeUiNode.Companion.Constructor;
            if (composerImpl.applier == null) {
                ComposablesKt.invalidApplier();
                throw null;
            }
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function0);
            } else {
                composerImpl.useNode();
            }
            Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
            }
            Updater.m337setimpl(composerImpl, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
            final BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
            LookaheadScopeKt.LookaheadScope(ComposableLambdaKt.rememberComposableLambda(1261608669, new Function3() { // from class: com.android.compose.animation.scene.SceneTransitionLayoutImpl$Content$1$1
                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    LookaheadScope lookaheadScope = (LookaheadScope) obj;
                    Composer composer2 = (Composer) obj2;
                    ((Number) obj3).intValue();
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.compose.animation.scene.SceneTransitionLayoutImpl.Content.<anonymous>.<anonymous> (SceneTransitionLayoutImpl.kt:469)");
                    }
                    SceneTransitionLayoutImpl sceneTransitionLayoutImpl = this.this$0;
                    if (sceneTransitionLayoutImpl._lookaheadScope == null) {
                        sceneTransitionLayoutImpl._lookaheadScope = lookaheadScope;
                    }
                    sceneTransitionLayoutImpl.BackHandler(0, composer2);
                    sceneTransitionLayoutImpl.Scenes(0, composer2);
                    sceneTransitionLayoutImpl.Overlays(boxScopeInstance, composer2, 0);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, 6);
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.compose.animation.scene.SceneTransitionLayoutImpl$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).intValue();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    this.f$0.Content$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout(modifier, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v22, types: [com.android.compose.animation.scene.content.Content, com.android.compose.animation.scene.content.Overlay] */
    /* JADX WARN: Type inference failed for: r4v10, types: [kotlin.collections.builders.ListBuilder] */
    /* JADX WARN: Type inference failed for: r4v11, types: [java.lang.Iterable] */
    /* JADX WARN: Type inference failed for: r4v12, types: [java.util.ArrayList] */
    /* JADX WARN: Type inference failed for: r6v1, types: [androidx.compose.runtime.Composer, androidx.compose.runtime.ComposerImpl] */
    public final void Overlays(final BoxScope boxScope, Composer composer, final int i) {
        ?? Build;
        List listSortedWith;
        boolean z;
        ?? r6 = (ComposerImpl) composer;
        r6.startRestartGroup(118058814);
        int i2 = (r6.changed(boxScope) ? 4 : 2) | i | (r6.changed(this) ? 32 : 16);
        if ((i2 & 19) == 18 && r6.getSkipping()) {
            r6.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.compose.animation.scene.SceneTransitionLayoutImpl.Overlays (SceneTransitionLayoutImpl.kt:535)");
            }
            boolean z2 = false;
            if (this._overlays == null) {
                listSortedWith = EmptyList.INSTANCE;
            } else {
                MutableSceneTransitionLayoutStateImpl mutableSceneTransitionLayoutStateImpl = this.state;
                List currentTransitions = mutableSceneTransitionLayoutStateImpl.getCurrentTransitions();
                if (currentTransitions.isEmpty()) {
                    Set currentOverlays = mutableSceneTransitionLayoutStateImpl.getTransitionState().getCurrentOverlays();
                    Build = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(currentOverlays, 10));
                    Iterator it = currentOverlays.iterator();
                    while (it.hasNext()) {
                        Build.add(overlay$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout((OverlayKey) it.next()));
                    }
                } else {
                    ListBuilder listBuilderCreateListBuilder = CollectionsKt__CollectionsJVMKt.createListBuilder();
                    LinkedHashSet linkedHashSet = new LinkedHashSet();
                    int size = currentTransitions.size();
                    for (int i3 = 0; i3 < size; i3++) {
                        TransitionState.Transition transition = (TransitionState.Transition) currentTransitions.get(i3);
                        if (!(transition instanceof TransitionState.Transition.ChangeScene)) {
                            if (transition instanceof TransitionState.Transition.ShowOrHideOverlay) {
                                overlaysToComposeOrderedByZIndex$lambda$37$maybeAdd$34(linkedHashSet, listBuilderCreateListBuilder, this, ((TransitionState.Transition.ShowOrHideOverlay) transition).overlay);
                            } else {
                                if (!(transition instanceof TransitionState.Transition.ReplaceOverlay)) {
                                    throw new NoWhenBranchMatchedException();
                                }
                                TransitionState.Transition.ReplaceOverlay replaceOverlay = (TransitionState.Transition.ReplaceOverlay) transition;
                                overlaysToComposeOrderedByZIndex$lambda$37$maybeAdd$34(linkedHashSet, listBuilderCreateListBuilder, this, replaceOverlay.fromOverlay);
                                overlaysToComposeOrderedByZIndex$lambda$37$maybeAdd$34(linkedHashSet, listBuilderCreateListBuilder, this, replaceOverlay.toOverlay);
                            }
                        }
                    }
                    Iterator it2 = ((TransitionState.Transition) CollectionsKt___CollectionsKt.last(currentTransitions)).getCurrentOverlays().iterator();
                    while (it2.hasNext()) {
                        overlaysToComposeOrderedByZIndex$lambda$37$maybeAdd$34(linkedHashSet, listBuilderCreateListBuilder, this, (OverlayKey) it2.next());
                    }
                    Build = listBuilderCreateListBuilder.build();
                }
                listSortedWith = CollectionsKt___CollectionsKt.sortedWith(Build, new Comparator() { // from class: com.android.compose.animation.scene.SceneTransitionLayoutImpl$overlaysToComposeOrderedByZIndex$$inlined$sortedBy$1
                    @Override // java.util.Comparator
                    public final int compare(Object obj, Object obj2) {
                        return ComparisonsKt__ComparisonsKt.compareValues(Float.valueOf(((SnapshotMutableFloatStateImpl) ((Overlay) obj).zIndex$delegate).getFloatValue()), Float.valueOf(((SnapshotMutableFloatStateImpl) ((Overlay) obj2).zIndex$delegate).getFloatValue()));
                    }
                });
            }
            List list = listSortedWith;
            if (list.isEmpty()) {
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                RecomposeScopeImpl recomposeScopeImplEndRestartGroup = r6.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup != null) {
                    final int i4 = 0;
                    recomposeScopeImplEndRestartGroup.block = new Function2(this, boxScope, i, i4) { // from class: com.android.compose.animation.scene.SceneTransitionLayoutImpl$$ExternalSyntheticLambda5
                        public final /* synthetic */ int $r8$classId;
                        public final /* synthetic */ SceneTransitionLayoutImpl f$0;
                        public final /* synthetic */ BoxScope f$1;

                        {
                            this.$r8$classId = i4;
                            this.f$0 = this;
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            int i5 = this.$r8$classId;
                            Composer composer2 = (Composer) obj;
                            ((Integer) obj2).getClass();
                            switch (i5) {
                                case 0:
                                    this.f$0.Overlays(this.f$1, composer2, RecomposeScopeImplKt.updateChangedFlags(1));
                                    break;
                                default:
                                    this.f$0.Overlays(this.f$1, composer2, RecomposeScopeImplKt.updateChangedFlags(1));
                                    break;
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    return;
                }
                return;
            }
            int size2 = list.size();
            int i5 = 0;
            while (i5 < size2) {
                ?? r3 = (Overlay) list.get(i5);
                final OverlayKey overlayKey = r3.key;
                r6.startMovableGroup(-644694998, overlayKey);
                Modifier.Companion companion = Modifier.Companion;
                Modifier modifierZIndex = ZIndexModifierKt.zIndex(boxScope.matchParentSize(companion), ((SnapshotMutableFloatStateImpl) r3.zIndex$delegate).getFloatValue());
                Alignment.Companion.getClass();
                MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, z2);
                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(r6);
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = r6.currentCompositionLocalScope();
                Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(r6, modifierZIndex);
                ComposeUiNode.Companion.getClass();
                Function0 function0 = ComposeUiNode.Companion.Constructor;
                int i6 = i2;
                if (r6.applier == null) {
                    ComposablesKt.invalidApplier();
                    throw null;
                }
                r6.startReusableNode();
                if (r6.inserting) {
                    r6.createNode(function0);
                } else {
                    r6.useNode();
                }
                Updater.m337setimpl(r6, measurePolicyMaybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                Updater.m337setimpl(r6, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                if (r6.inserting || !Intrinsics.areEqual(r6.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, r6, currentCompositeKeyHash, function2);
                }
                Updater.m337setimpl(r6, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                r6.startReplaceGroup(-793278550);
                if (((Boolean) ((SnapshotMutableStateImpl) r3.isModal$delegate).getValue()).booleanValue()) {
                    Modifier modifierFillMaxSize = SizeKt.fillMaxSize(companion, 1.0f);
                    r6.startReplaceGroup(-793267650);
                    Object objRememberedValue = r6.rememberedValue();
                    Composer.Companion.getClass();
                    Object obj = Composer.Companion.Empty;
                    if (objRememberedValue == obj) {
                        objRememberedValue = InteractionSourceKt.MutableInteractionSource();
                        r6.updateRememberedValue(objRememberedValue);
                    }
                    MutableInteractionSource mutableInteractionSource = (MutableInteractionSource) objRememberedValue;
                    r6.end(false);
                    r6.startReplaceGroup(-793263563);
                    boolean zChanged = ((i6 & 112) == 32) | r6.changed(overlayKey);
                    Object objRememberedValue2 = r6.rememberedValue();
                    if (zChanged || objRememberedValue2 == obj) {
                        objRememberedValue2 = new Function0() { // from class: com.android.compose.animation.scene.SceneTransitionLayoutImpl$$ExternalSyntheticLambda6
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                SceneTransitionLayoutImpl sceneTransitionLayoutImpl = this.f$0;
                                Function1 function1 = sceneTransitionLayoutImpl.state.canHideOverlay;
                                OverlayKey overlayKey2 = overlayKey;
                                if (((Boolean) function1.mo781invoke(overlayKey2)).booleanValue()) {
                                    sceneTransitionLayoutImpl.state.hideOverlay(overlayKey2, sceneTransitionLayoutImpl.animationScope, null);
                                }
                                return Unit.INSTANCE;
                            }
                        };
                        r6.updateRememberedValue(objRememberedValue2);
                    }
                    z = false;
                    r6.end(false);
                    BoxKt.Box(ClickableKt.m34clickableO2vRcR0$default(modifierFillMaxSize, mutableInteractionSource, null, false, null, null, (Function0) objRememberedValue2, 28), r6, 0);
                } else {
                    z = false;
                }
                r6.end(z);
                r3.Content(boxScopeInstance.align(companion, (Alignment) ((SnapshotMutableStateImpl) r3.alignment$delegate).getValue()), false, r6, 0, 2);
                r6.end(true);
                r6.end(false);
                i5++;
                z2 = false;
                i2 = i6;
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup2 = r6.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup2 != null) {
            final int i7 = 1;
            recomposeScopeImplEndRestartGroup2.block = new Function2(this, boxScope, i, i7) { // from class: com.android.compose.animation.scene.SceneTransitionLayoutImpl$$ExternalSyntheticLambda5
                public final /* synthetic */ int $r8$classId;
                public final /* synthetic */ SceneTransitionLayoutImpl f$0;
                public final /* synthetic */ BoxScope f$1;

                {
                    this.$r8$classId = i7;
                    this.f$0 = this;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj2, Object obj22) {
                    int i52 = this.$r8$classId;
                    Composer composer2 = (Composer) obj2;
                    ((Integer) obj22).getClass();
                    switch (i52) {
                        case 0:
                            this.f$0.Overlays(this.f$1, composer2, RecomposeScopeImplKt.updateChangedFlags(1));
                            break;
                        default:
                            this.f$0.Overlays(this.f$1, composer2, RecomposeScopeImplKt.updateChangedFlags(1));
                            break;
                    }
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public final void Scenes(int i, Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(407513209);
        if ((((composerImpl.changed(this) ? 4 : 2) | i) & 3) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.compose.animation.scene.SceneTransitionLayoutImpl.Scenes (SceneTransitionLayoutImpl.kt:490)");
            }
            MutableSceneTransitionLayoutStateImpl mutableSceneTransitionLayoutStateImpl = this.state;
            List currentTransitions = mutableSceneTransitionLayoutStateImpl.getCurrentTransitions();
            ListBuilder listBuilderCreateListBuilder = CollectionsKt__CollectionsJVMKt.createListBuilder();
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            if (currentTransitions.isEmpty()) {
                scenesToCompose$lambda$25$maybeAdd$default(linkedHashSet, listBuilderCreateListBuilder, this, mutableSceneTransitionLayoutStateImpl.getTransitionState().getCurrentScene());
            } else {
                int size = currentTransitions.size() - 1;
                if (size >= 0) {
                    while (true) {
                        int i2 = size - 1;
                        TransitionState.Transition transition = (TransitionState.Transition) currentTransitions.get(size);
                        if (transition instanceof TransitionState.Transition.ChangeScene) {
                            TransitionState.Transition.ChangeScene changeScene = (TransitionState.Transition.ChangeScene) transition;
                            scenesToCompose$lambda$25$maybeAdd$default(linkedHashSet, listBuilderCreateListBuilder, this, changeScene.toScene);
                            scenesToCompose$lambda$25$maybeAdd$default(linkedHashSet, listBuilderCreateListBuilder, this, changeScene.fromScene);
                        } else if (transition instanceof TransitionState.Transition.ShowOrHideOverlay) {
                            scenesToCompose$lambda$25$maybeAdd$default(linkedHashSet, listBuilderCreateListBuilder, this, ((TransitionState.Transition.ShowOrHideOverlay) transition).fromOrToScene);
                        } else if (!(transition instanceof TransitionState.Transition.ReplaceOverlay)) {
                            throw new NoWhenBranchMatchedException();
                        }
                        if (i2 < 0) {
                            break;
                        } else {
                            size = i2;
                        }
                    }
                }
                scenesToCompose$lambda$25$maybeAdd$default(linkedHashSet, listBuilderCreateListBuilder, this, ((TransitionState.Transition) CollectionsKt___CollectionsKt.last(currentTransitions)).getCurrentScene());
            }
            List list = this.scenesToAlwaysCompose;
            if (list != null) {
                int size2 = list.size();
                for (int i3 = 0; i3 < size2; i3++) {
                    SceneKey sceneKey = ((Scene) ((ArrayList) list).get(i3)).key;
                    if (linkedHashSet.add(sceneKey)) {
                        listBuilderCreateListBuilder.add(new SceneToCompose(scene$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout(sceneKey), true));
                    }
                }
            }
            ListBuilder listBuilderBuild = listBuilderCreateListBuilder.build();
            int size3 = listBuilderBuild.getSize();
            for (int i4 = 0; i4 < size3; i4++) {
                SceneToCompose sceneToCompose = (SceneToCompose) listBuilderBuild.get(i4);
                Scene scene = sceneToCompose.scene;
                composerImpl.startMovableGroup(-1495293608, scene.key);
                scene.Content(null, sceneToCompose.isInvisible, composerImpl, 0, 1);
                composerImpl.end(false);
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new SceneTransitionLayoutImpl$$ExternalSyntheticLambda3(this, i, 0);
        }
    }

    public final Content content$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout(ContentKey contentKey) {
        if (contentKey instanceof SceneKey) {
            return scene$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout((SceneKey) contentKey);
        }
        if (contentKey instanceof OverlayKey) {
            return overlay$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout((OverlayKey) contentKey);
        }
        throw new NoWhenBranchMatchedException();
    }

    public final Content contentForUserActions$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout() {
        MutableSceneTransitionLayoutStateImpl mutableSceneTransitionLayoutStateImpl = this.state;
        Set<OverlayKey> currentOverlays = mutableSceneTransitionLayoutStateImpl.getTransitionState().getCurrentOverlays();
        Overlay overlay = null;
        if (!currentOverlays.isEmpty()) {
            Overlay overlay2 = null;
            for (OverlayKey overlayKey : currentOverlays) {
                Float fValueOf = overlay2 != null ? Float.valueOf(((SnapshotMutableFloatStateImpl) overlay2.zIndex$delegate).getFloatValue()) : null;
                Overlay overlayOverlay$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout = overlay$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout(overlayKey);
                if (fValueOf == null || ((SnapshotMutableFloatStateImpl) overlayOverlay$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout.zIndex$delegate).getFloatValue() > fValueOf.floatValue()) {
                    overlay2 = overlayOverlay$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout;
                }
            }
            overlay = overlay2;
        }
        return overlay != null ? overlay : scene$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout(mutableSceneTransitionLayoutStateImpl.getTransitionState().getCurrentScene());
    }

    public final Map getOverlays() {
        SnapshotStateMap snapshotStateMap = this._overlays;
        if (snapshotStateMap != null) {
            return snapshotStateMap;
        }
        SnapshotStateMap snapshotStateMap2 = new SnapshotStateMap();
        this._overlays = snapshotStateMap2;
        return snapshotStateMap2;
    }

    public final void hideOverlays$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout(UserActionResult.ShowOverlay.HideCurrentOverlays hideCurrentOverlays) {
        if (Intrinsics.areEqual(hideCurrentOverlays, UserActionResult.ShowOverlay.HideCurrentOverlays.None.INSTANCE)) {
            return;
        }
        boolean zAreEqual = Intrinsics.areEqual(hideCurrentOverlays, UserActionResult.ShowOverlay.HideCurrentOverlays.All.INSTANCE);
        CoroutineScope coroutineScope = this.animationScope;
        MutableSceneTransitionLayoutStateImpl mutableSceneTransitionLayoutStateImpl = this.state;
        if (zAreEqual) {
            Iterator it = new HashSet(mutableSceneTransitionLayoutStateImpl.getTransitionState().getCurrentOverlays()).iterator();
            while (it.hasNext()) {
                OverlayKey overlayKey = (OverlayKey) it.next();
                overlayKey.getClass();
                if (((Boolean) mutableSceneTransitionLayoutStateImpl.canHideOverlay.mo781invoke(overlayKey)).booleanValue()) {
                    mutableSceneTransitionLayoutStateImpl.hideOverlay(overlayKey, coroutineScope, null);
                }
            }
            return;
        }
        if (!(hideCurrentOverlays instanceof UserActionResult.ShowOverlay.HideCurrentOverlays.Some)) {
            throw new NoWhenBranchMatchedException();
        }
        for (OverlayKey overlayKey2 : ((UserActionResult.ShowOverlay.HideCurrentOverlays.Some) hideCurrentOverlays).overlays) {
            if (((Boolean) mutableSceneTransitionLayoutStateImpl.canHideOverlay.mo781invoke(overlayKey2)).booleanValue()) {
                mutableSceneTransitionLayoutStateImpl.hideOverlay(overlayKey2, coroutineScope, null);
            }
        }
    }

    public final Overlay overlay$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout(OverlayKey overlayKey) {
        Object obj;
        SceneTransitionLayoutImpl sceneTransitionLayoutImpl;
        Overlay overlay = (Overlay) ((SnapshotStateMap) getOverlays()).get(overlayKey);
        if (overlay == null) {
            List list = this.ancestors;
            int size = list.size();
            int i = 0;
            while (true) {
                if (i >= size) {
                    obj = null;
                    break;
                }
                obj = list.get(i);
                if (((SnapshotStateMap) ((Ancestor) obj).layoutImpl.getOverlays()).get(overlayKey) != null) {
                    break;
                }
                i++;
            }
            Ancestor ancestor = (Ancestor) obj;
            overlay = (ancestor == null || (sceneTransitionLayoutImpl = ancestor.layoutImpl) == null) ? null : (Overlay) ((SnapshotStateMap) sceneTransitionLayoutImpl.getOverlays()).get(overlayKey);
        }
        if (overlay != null) {
            return overlay;
        }
        throw new IllegalStateException(("Overlay " + overlayKey + " is not configured").toString());
    }

    public final Map<OverlayKey, Overlay> overlaysOrNullForTest$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout() {
        return this._overlays;
    }

    public final Scene scene$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout(SceneKey sceneKey) {
        Object obj;
        SceneTransitionLayoutImpl sceneTransitionLayoutImpl;
        SnapshotStateMap snapshotStateMap;
        Scene scene = (Scene) this.scenes.get(sceneKey);
        if (scene == null) {
            List list = this.ancestors;
            int size = list.size();
            int i = 0;
            while (true) {
                if (i >= size) {
                    obj = null;
                    break;
                }
                obj = list.get(i);
                if (((Ancestor) obj).layoutImpl.scenes.get(sceneKey) != null) {
                    break;
                }
                i++;
            }
            Ancestor ancestor = (Ancestor) obj;
            scene = (ancestor == null || (sceneTransitionLayoutImpl = ancestor.layoutImpl) == null || (snapshotStateMap = sceneTransitionLayoutImpl.scenes) == null) ? null : (Scene) snapshotStateMap.get(sceneKey);
        }
        if (scene != null) {
            return scene;
        }
        throw new IllegalStateException(("Scene " + sceneKey + " is not configured").toString());
    }

    /* renamed from: setContentsAndLayoutTargetSizeForTest-ozmzZPI$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout, reason: not valid java name */
    public final void m927x98b17969(long j) {
        this.lastSize = j;
        ArrayList arrayList = (ArrayList) CollectionsKt___CollectionsKt.plus((Iterable) ((SnapshotStateMap) getOverlays()).values, this.scenes.values);
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((SnapshotMutableStateImpl) ((Content) obj).targetSize$delegate).setValue(IntSize.m861boximpl(j));
        }
    }

    public final void updateContents$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout(Function1 function1, LayoutDirection layoutDirection, OverscrollFactory overscrollFactory) {
        SnapshotStateMap snapshotStateMap = this.scenes;
        Set mutableSet = CollectionsKt___CollectionsKt.toMutableSet(snapshotStateMap.keys);
        Set linkedHashSet = this._overlays == null ? new LinkedHashSet() : CollectionsKt___CollectionsKt.toMutableSet(((SnapshotStateMap) getOverlays()).keys);
        function1.mo781invoke(new SceneTransitionLayoutImpl$updateContents$1(new Ref$BooleanRef(), mutableSet, this, layoutDirection, this.ancestors.isEmpty() ? 0L : ((SnapshotMutableLongStateImpl) content$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout(((Ancestor) CollectionsKt___CollectionsKt.last(this.ancestors)).inContent).globalZIndex$delegate).getLongValue(), new Ref$IntRef(), overscrollFactory, linkedHashSet));
        Iterator it = mutableSet.iterator();
        while (it.hasNext()) {
            snapshotStateMap.remove((SceneKey) it.next());
        }
        Iterator it2 = linkedHashSet.iterator();
        while (it2.hasNext()) {
            ((SnapshotStateMap) getOverlays()).remove((OverlayKey) it2.next());
        }
    }

    public SceneTransitionLayoutImpl(MutableSceneTransitionLayoutStateImpl mutableSceneTransitionLayoutStateImpl, Density density, LayoutDirection layoutDirection, SwipeSourceDetector swipeSourceDetector, SwipeDetector swipeDetector, float f, DecayAnimationSpec decayAnimationSpec, Function1 function1, CoroutineScope coroutineScope, float f2, Map map, List list, boolean z, LookaheadScope lookaheadScope, OverscrollFactory overscrollFactory, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(mutableSceneTransitionLayoutStateImpl, density, layoutDirection, swipeSourceDetector, swipeDetector, f, decayAnimationSpec, function1, coroutineScope, f2, (i & 1024) != 0 ? new LinkedHashMap() : map, (i & 2048) != 0 ? EmptyList.INSTANCE : list, (i & 4096) != 0 ? false : z, (i & 8192) != 0 ? null : lookaheadScope, overscrollFactory);
    }
}
