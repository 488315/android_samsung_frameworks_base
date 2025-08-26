package com.android.compose.animation.scene.content;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.OverscrollFactory;
import androidx.compose.foundation.OverscrollKt;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.MutableFloatState;
import androidx.compose.runtime.MutableLongState;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.PrimitiveSnapshotStateKt;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotLongStateKt;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.TestTagKt;
import androidx.compose.ui.unit.IntSize;
import com.android.compose.animation.scene.ContentKey;
import com.android.compose.animation.scene.Element;
import com.android.compose.animation.scene.SceneTransitionLayoutImpl;
import com.android.compose.animation.scene.UserAction;
import com.android.compose.animation.scene.UserActionResult;
import com.android.compose.animation.scene.content.Content;
import com.android.compose.gesture.NestedScrollControlState;
import com.android.compose.ui.graphics.ContainerState;
import java.util.Map;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public abstract class Content {
    public static final Companion Companion = new Companion(null);
    public final ContainerState containerState;
    public final MutableState content$delegate;
    public final MutableLongState globalZIndex$delegate;
    public final MutableState horizontalEffects$delegate;
    public final ContentKey key;
    public final MutableState lastFactory$delegate;
    public final SceneTransitionLayoutImpl layoutImpl;
    public final NestedScrollControlState nestedScrollControlState;
    public final ContentScopeImpl scope;
    public final MutableState targetSize$delegate;
    public final MutableState userActions$delegate;
    public final MutableState verticalEffects$delegate;
    public final MutableFloatState zIndex$delegate;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public /* synthetic */ Content(ContentKey contentKey, SceneTransitionLayoutImpl sceneTransitionLayoutImpl, Function3 function3, Map map, float f, long j, OverscrollFactory overscrollFactory, DefaultConstructorMarker defaultConstructorMarker) {
        this(contentKey, sceneTransitionLayoutImpl, function3, map, f, j, overscrollFactory);
    }

    public final void Content(Modifier modifier, boolean z, Composer composer, final int i, final int i2) {
        int i3;
        int i4;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-46060985);
        int i5 = i2 & 1;
        if (i5 != 0) {
            i3 = i | 6;
        } else {
            i3 = (composerImpl.changed(modifier) ? 4 : 2) | i;
        }
        int i6 = 2 & i2;
        if (i6 != 0) {
            i4 = i3 | 48;
        } else {
            i4 = i3 | (composerImpl.changed(z) ? 32 : 16);
        }
        if (((i4 | (composerImpl.changed(this) ? 256 : 128)) & 147) == 146 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (i5 != 0) {
                modifier = Modifier.Companion;
            }
            if (i6 != 0) {
                z = false;
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.compose.animation.scene.content.Content.Content (Content.kt:163)");
            }
            SceneTransitionLayoutImpl sceneTransitionLayoutImpl = this.layoutImpl;
            Modifier modifierThen = modifier.then(new ContentElement(this, sceneTransitionLayoutImpl.state.isElevationPossible$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout(getKey(), null), z));
            if (sceneTransitionLayoutImpl.implicitTestTags) {
                modifierThen = modifierThen.then(TestTagKt.testTag(Modifier.Companion, getKey().getTestTag()));
            }
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
            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
            CompositionLocalKt.CompositionLocalProvider(OverscrollKt.LocalOverscrollFactory.defaultProvidedValue$runtime_release((OverscrollFactory) ((SnapshotMutableStateImpl) this.lastFactory$delegate).getValue()), ComposableLambdaKt.rememberComposableLambda(-2101144691, new Function2() { // from class: com.android.compose.animation.scene.content.Content$Content$5$1
                /* JADX WARN: Removed duplicated region for block: B:8:0x001b  */
                @Override // kotlin.jvm.functions.Function2
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invoke(Object obj, Object obj2) {
                    Composer composer2 = (Composer) obj;
                    if ((((Number) obj2).intValue() & 3) == 2) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                        } else {
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("com.android.compose.animation.scene.content.Content.Content.<anonymous>.<anonymous> (Content.kt:176)");
                            }
                            Content content = this.this$0;
                            ((Function3) ((SnapshotMutableStateImpl) content.content$delegate).getValue()).invoke(content.scope, composer2, 0);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, 56);
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        final Modifier modifier2 = modifier;
        final boolean z2 = z;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(modifier2, z2, i, i2) { // from class: com.android.compose.animation.scene.content.Content$$ExternalSyntheticLambda0
                public final /* synthetic */ Modifier f$1;
                public final /* synthetic */ boolean f$2;
                public final /* synthetic */ int f$4;

                {
                    this.f$4 = i2;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    Composer composer2 = (Composer) obj;
                    ((Integer) obj2).getClass();
                    Content.Companion companion = Content.Companion;
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    this.f$0.Content(this.f$1, this.f$2, composer2, iUpdateChangedFlags, this.f$4);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public ContentKey getKey() {
        return this.key;
    }

    private Content(ContentKey contentKey, SceneTransitionLayoutImpl sceneTransitionLayoutImpl, Function3 function3, Map<UserAction.Resolved, ? extends UserActionResult> map, float f, long j, OverscrollFactory overscrollFactory) {
        this.key = contentKey;
        this.layoutImpl = sceneTransitionLayoutImpl;
        NestedScrollControlState nestedScrollControlState = new NestedScrollControlState();
        this.nestedScrollControlState = nestedScrollControlState;
        this.scope = new ContentScopeImpl(sceneTransitionLayoutImpl, this, nestedScrollControlState);
        this.containerState = new ContainerState();
        this.content$delegate = SnapshotStateKt.mutableStateOf$default(function3);
        Element.Companion.getClass();
        this.targetSize$delegate = SnapshotStateKt.mutableStateOf$default(IntSize.m861boximpl(Element.SizeUnspecified));
        this.userActions$delegate = SnapshotStateKt.mutableStateOf$default(map);
        this.zIndex$delegate = PrimitiveSnapshotStateKt.mutableFloatStateOf(f);
        this.globalZIndex$delegate = SnapshotLongStateKt.mutableLongStateOf(j);
        this.lastFactory$delegate = SnapshotStateKt.mutableStateOf$default(overscrollFactory);
        this.verticalEffects$delegate = SnapshotStateKt.mutableStateOf$default(new ContentEffects(overscrollFactory));
        this.horizontalEffects$delegate = SnapshotStateKt.mutableStateOf$default(new ContentEffects(overscrollFactory));
    }
}
