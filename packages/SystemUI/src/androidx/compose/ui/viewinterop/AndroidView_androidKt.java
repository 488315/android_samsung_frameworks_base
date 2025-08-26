package androidx.compose.ui.viewinterop;

import android.content.Context;
import android.view.View;
import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.runtime.Applier;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.saveable.SaveableStateRegistry;
import androidx.compose.runtime.saveable.SaveableStateRegistryKt;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.autofill.AndroidAutofill$$ExternalSyntheticOutline0;
import androidx.compose.ui.focus.FocusTargetNode;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.node.LayoutNode;
import androidx.compose.ui.node.Owner;
import androidx.compose.ui.node.UiApplier;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.LayoutDirection;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.compose.LocalLifecycleOwnerKt;
import androidx.savedstate.SavedStateRegistryOwner;
import com.android.systemui.R;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public abstract class AndroidView_androidKt {
    public static final Function1 NoOpUpdate = new Function1() { // from class: androidx.compose.ui.viewinterop.AndroidView_androidKt$NoOpUpdate$1
        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final /* bridge */ /* synthetic */ Object mo781invoke(Object obj) {
            return Unit.INSTANCE;
        }
    };

    public static final void AndroidView(final Function1 function1, Modifier modifier, Function1 function12, Composer composer, final int i, final int i2) {
        int i3;
        final Modifier modifier2;
        final Function1 function13;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1783766393);
        if ((i2 & 1) != 0) {
            i3 = i | 6;
        } else if ((i & 6) == 0) {
            i3 = (composerImpl.changedInstance(function1) ? 4 : 2) | i;
        } else {
            i3 = i;
        }
        int i4 = i2 & 2;
        if (i4 != 0) {
            i3 |= 48;
        } else if ((i & 48) == 0) {
            i3 |= composerImpl.changed(modifier) ? 32 : 16;
        }
        int i5 = i2 & 4;
        if (i5 != 0) {
            i3 |= 384;
        } else if ((i & 384) == 0) {
            i3 |= composerImpl.changedInstance(function12) ? 256 : 128;
        }
        if (composerImpl.shouldExecute(i3 & 1, (i3 & 147) != 146)) {
            if (i4 != 0) {
                modifier = Modifier.Companion;
            }
            Modifier modifier3 = modifier;
            Function1 function14 = NoOpUpdate;
            Function1 function15 = i5 != 0 ? function14 : function12;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("androidx.compose.ui.viewinterop.AndroidView (AndroidView.android.kt:104)");
            }
            AndroidView(function1, modifier3, null, function14, function15, composerImpl, (i3 & 14) | 3072 | (i3 & 112) | (57344 & (i3 << 6)), 4);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            modifier2 = modifier3;
            function13 = function15;
        } else {
            composerImpl.skipToGroupEnd();
            modifier2 = modifier;
            function13 = function12;
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.ui.viewinterop.AndroidView_androidKt.AndroidView.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    AndroidView_androidKt.AndroidView(function1, modifier2, function13, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final ViewFactoryHolder access$requireViewFactoryHolder(LayoutNode layoutNode) {
        AndroidViewHolder androidViewHolder = layoutNode.interopViewFactoryHolder;
        if (androidViewHolder != null) {
            return (ViewFactoryHolder) androidViewHolder;
        }
        throw AndroidAutofill$$ExternalSyntheticOutline0.m("Required value was null.");
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x006c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Function0 createAndroidViewNodeFactory(final Function1 function1, Composer composer, int i) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.ui.viewinterop.createAndroidViewNodeFactory (AndroidView.android.kt:252)");
        }
        final int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer);
        ComposerImpl composerImpl = (ComposerImpl) composer;
        final Context context = (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
        final ComposerImpl.CompositionContextImpl compositionContextImplRememberCompositionContext = ComposablesKt.rememberCompositionContext(composerImpl);
        final SaveableStateRegistry saveableStateRegistry = (SaveableStateRegistry) composerImpl.consume(SaveableStateRegistryKt.LocalSaveableStateRegistry);
        final View view = (View) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalView);
        boolean zChangedInstance = ((((i & 14) ^ 6) > 4 && composerImpl.changed(function1)) || (i & 6) == 4) | composerImpl.changedInstance(context) | composerImpl.changedInstance(compositionContextImplRememberCompositionContext) | composerImpl.changedInstance(saveableStateRegistry) | composerImpl.changed(currentCompositeKeyHash) | composerImpl.changedInstance(view);
        Object objRememberedValue = composerImpl.rememberedValue();
        if (!zChangedInstance) {
            Composer.Companion.getClass();
            if (objRememberedValue == Composer.Companion.Empty) {
                Function0 function0 = new Function0() { // from class: androidx.compose.ui.viewinterop.AndroidView_androidKt$createAndroidViewNodeFactory$1$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return new ViewFactoryHolder(context, function1, compositionContextImplRememberCompositionContext, saveableStateRegistry, currentCompositeKeyHash, (Owner) view).layoutNode;
                    }
                };
                composerImpl.updateRememberedValue(function0);
                objRememberedValue = function0;
            }
        }
        Function0 function02 = (Function0) objRememberedValue;
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return function02;
    }

    /* renamed from: updateViewHolderParams-6NefGtU, reason: not valid java name */
    public static final void m886updateViewHolderParams6NefGtU(Composer composer, Modifier modifier, int i, Density density, LifecycleOwner lifecycleOwner, SavedStateRegistryOwner savedStateRegistryOwner, LayoutDirection layoutDirection, PersistentCompositionLocalMap persistentCompositionLocalMap) {
        ComposeUiNode.Companion.getClass();
        Updater.m337setimpl(composer, persistentCompositionLocalMap, ComposeUiNode.Companion.SetResolvedCompositionLocals);
        Updater.m337setimpl(composer, modifier, new Function2() { // from class: androidx.compose.ui.viewinterop.AndroidView_androidKt$updateViewHolderParams$1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Modifier modifier2 = (Modifier) obj2;
                ViewFactoryHolder viewFactoryHolderAccess$requireViewFactoryHolder = AndroidView_androidKt.access$requireViewFactoryHolder((LayoutNode) obj);
                if (modifier2 != viewFactoryHolderAccess$requireViewFactoryHolder.modifier) {
                    viewFactoryHolderAccess$requireViewFactoryHolder.modifier = modifier2;
                    Function1 function1 = viewFactoryHolderAccess$requireViewFactoryHolder.onModifierChanged;
                    if (function1 != null) {
                        ((AndroidViewHolder$layoutNode$1$1) function1).mo781invoke(modifier2);
                    }
                }
                return Unit.INSTANCE;
            }
        });
        Updater.m337setimpl(composer, density, new Function2() { // from class: androidx.compose.ui.viewinterop.AndroidView_androidKt$updateViewHolderParams$2
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Density density2 = (Density) obj2;
                ViewFactoryHolder viewFactoryHolderAccess$requireViewFactoryHolder = AndroidView_androidKt.access$requireViewFactoryHolder((LayoutNode) obj);
                if (density2 != viewFactoryHolderAccess$requireViewFactoryHolder.density) {
                    viewFactoryHolderAccess$requireViewFactoryHolder.density = density2;
                    Function1 function1 = viewFactoryHolderAccess$requireViewFactoryHolder.onDensityChanged;
                    if (function1 != null) {
                        ((AndroidViewHolder$layoutNode$1$2) function1).mo781invoke(density2);
                    }
                }
                return Unit.INSTANCE;
            }
        });
        Updater.m337setimpl(composer, lifecycleOwner, new Function2() { // from class: androidx.compose.ui.viewinterop.AndroidView_androidKt$updateViewHolderParams$3
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                LifecycleOwner lifecycleOwner2 = (LifecycleOwner) obj2;
                ViewFactoryHolder viewFactoryHolderAccess$requireViewFactoryHolder = AndroidView_androidKt.access$requireViewFactoryHolder((LayoutNode) obj);
                if (lifecycleOwner2 != viewFactoryHolderAccess$requireViewFactoryHolder.lifecycleOwner) {
                    viewFactoryHolderAccess$requireViewFactoryHolder.lifecycleOwner = lifecycleOwner2;
                    viewFactoryHolderAccess$requireViewFactoryHolder.setTag(R.id.view_tree_lifecycle_owner, lifecycleOwner2);
                }
                return Unit.INSTANCE;
            }
        });
        Updater.m337setimpl(composer, savedStateRegistryOwner, new Function2() { // from class: androidx.compose.ui.viewinterop.AndroidView_androidKt$updateViewHolderParams$4
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                SavedStateRegistryOwner savedStateRegistryOwner2 = (SavedStateRegistryOwner) obj2;
                ViewFactoryHolder viewFactoryHolderAccess$requireViewFactoryHolder = AndroidView_androidKt.access$requireViewFactoryHolder((LayoutNode) obj);
                if (savedStateRegistryOwner2 != viewFactoryHolderAccess$requireViewFactoryHolder.savedStateRegistryOwner) {
                    viewFactoryHolderAccess$requireViewFactoryHolder.savedStateRegistryOwner = savedStateRegistryOwner2;
                    viewFactoryHolderAccess$requireViewFactoryHolder.setTag(R.id.view_tree_saved_state_registry_owner, savedStateRegistryOwner2);
                }
                return Unit.INSTANCE;
            }
        });
        Updater.m337setimpl(composer, layoutDirection, new Function2() { // from class: androidx.compose.ui.viewinterop.AndroidView_androidKt$updateViewHolderParams$5

            public abstract /* synthetic */ class WhenMappings {
                public static final /* synthetic */ int[] $EnumSwitchMapping$0;

                static {
                    int[] iArr = new int[LayoutDirection.values().length];
                    try {
                        iArr[LayoutDirection.Ltr.ordinal()] = 1;
                    } catch (NoSuchFieldError unused) {
                    }
                    try {
                        iArr[LayoutDirection.Rtl.ordinal()] = 2;
                    } catch (NoSuchFieldError unused2) {
                    }
                    $EnumSwitchMapping$0 = iArr;
                }
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                ViewFactoryHolder viewFactoryHolderAccess$requireViewFactoryHolder = AndroidView_androidKt.access$requireViewFactoryHolder((LayoutNode) obj);
                int i2 = WhenMappings.$EnumSwitchMapping$0[((LayoutDirection) obj2).ordinal()];
                int i3 = 1;
                if (i2 == 1) {
                    i3 = 0;
                } else if (i2 != 2) {
                    throw new NoWhenBranchMatchedException();
                }
                viewFactoryHolderAccess$requireViewFactoryHolder.setLayoutDirection(i3);
                return Unit.INSTANCE;
            }
        });
        Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(i))) {
            AnimatedContentKt$$ExternalSyntheticOutline0.m(i, composerImpl, i, function2);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:102:0x01a0  */
    /* JADX WARN: Removed duplicated region for block: B:105:0x01ad  */
    /* JADX WARN: Removed duplicated region for block: B:107:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0044  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0049  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x005f  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x007a  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0098  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x009a  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x00a3  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void AndroidView(final Function1 function1, Modifier modifier, Function1 function12, Function1 function13, Function1 function14, Composer composer, final int i, final int i2) {
        int i3;
        Modifier modifier2;
        int i4;
        Function1 function15;
        int i5;
        Function1 function16;
        int i6;
        Function1 function17;
        final Modifier modifier3;
        final Function1 function18;
        final Function1 function19;
        final Function1 function110;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        Function1 function111;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-180024211);
        if ((i2 & 1) != 0) {
            i3 = i | 6;
        } else if ((i & 6) == 0) {
            i3 = (composerImpl.changedInstance(function1) ? 4 : 2) | i;
        } else {
            i3 = i;
        }
        int i7 = i2 & 2;
        if (i7 != 0) {
            i3 |= 48;
        } else {
            if ((i & 48) == 0) {
                modifier2 = modifier;
                i3 |= composerImpl.changed(modifier2) ? 32 : 16;
            }
            i4 = i2 & 4;
            if (i4 == 0) {
                i3 |= 384;
            } else {
                if ((i & 384) == 0) {
                    function15 = function12;
                    i3 |= composerImpl.changedInstance(function15) ? 256 : 128;
                }
                i5 = i2 & 8;
                if (i5 != 0) {
                    i3 |= 3072;
                } else {
                    if ((i & 3072) == 0) {
                        function16 = function13;
                        i3 |= composerImpl.changedInstance(function16) ? 2048 : 1024;
                    }
                    i6 = i2 & 16;
                    if (i6 != 0) {
                        if ((i & 24576) == 0) {
                            function17 = function14;
                            i3 |= composerImpl.changedInstance(function17) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
                        }
                        if (composerImpl.shouldExecute(i3 & 1, (i3 & 9363) != 9362)) {
                            modifier3 = i7 != 0 ? Modifier.Companion : modifier2;
                            if (i4 != 0) {
                                function15 = null;
                            }
                            Function1 function112 = NoOpUpdate;
                            if (i5 != 0) {
                                function16 = function112;
                            }
                            if (i6 == 0) {
                                function112 = function17;
                            }
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("androidx.compose.ui.viewinterop.AndroidView (AndroidView.android.kt:199)");
                            }
                            Function1 function113 = function16;
                            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                            Modifier modifierThen = modifier3.then(FocusGroupPropertiesElement.INSTANCE);
                            FocusTargetNode.FocusTargetElement focusTargetElement = FocusTargetNode.FocusTargetElement.INSTANCE;
                            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierThen.then(focusTargetElement).then(FocusTargetPropertiesElement.INSTANCE).then(focusTargetElement));
                            Density density = (Density) composerImpl.consume(CompositionLocalsKt.LocalDensity);
                            LayoutDirection layoutDirection = (LayoutDirection) composerImpl.consume(CompositionLocalsKt.LocalLayoutDirection);
                            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
                            LifecycleOwner lifecycleOwner = (LifecycleOwner) composerImpl.consume(LocalLifecycleOwnerKt.LocalLifecycleOwner);
                            SavedStateRegistryOwner savedStateRegistryOwner = (SavedStateRegistryOwner) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalSavedStateRegistryOwner);
                            Applier applier = composerImpl.applier;
                            if (function15 != null) {
                                composerImpl.startReplaceGroup(607780130);
                                Function0 function0CreateAndroidViewNodeFactory = createAndroidViewNodeFactory(function1, composerImpl, i3 & 14);
                                if (applier instanceof UiApplier) {
                                    composerImpl.startReusableNode();
                                    if (composerImpl.inserting) {
                                        composerImpl.createNode(function0CreateAndroidViewNodeFactory);
                                    } else {
                                        composerImpl.useNode();
                                    }
                                    function111 = function113;
                                    m886updateViewHolderParams6NefGtU(composerImpl, modifierMaterializeModifier, currentCompositeKeyHash, density, lifecycleOwner, savedStateRegistryOwner, layoutDirection, persistentCompositionLocalMapCurrentCompositionLocalScope);
                                    Updater.m337setimpl(composerImpl, function15, new Function2() { // from class: androidx.compose.ui.viewinterop.AndroidView_androidKt$AndroidView$2$1
                                        /* JADX WARN: Type inference failed for: r1v2, types: [androidx.compose.ui.viewinterop.ViewFactoryHolder$resetBlock$1, kotlin.jvm.internal.Lambda] */
                                        @Override // kotlin.jvm.functions.Function2
                                        public final Object invoke(Object obj, Object obj2) {
                                            final ViewFactoryHolder viewFactoryHolderAccess$requireViewFactoryHolder = AndroidView_androidKt.access$requireViewFactoryHolder((LayoutNode) obj);
                                            viewFactoryHolderAccess$requireViewFactoryHolder.resetBlock = (Function1) obj2;
                                            viewFactoryHolderAccess$requireViewFactoryHolder.reset = new Function0() { // from class: androidx.compose.ui.viewinterop.ViewFactoryHolder$resetBlock$1
                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                {
                                                    super(0);
                                                }

                                                @Override // kotlin.jvm.functions.Function0
                                                public final Object invoke() {
                                                    ViewFactoryHolder<View> viewFactoryHolder = viewFactoryHolderAccess$requireViewFactoryHolder;
                                                    viewFactoryHolder.resetBlock.mo781invoke(viewFactoryHolder.typedView);
                                                    return Unit.INSTANCE;
                                                }
                                            };
                                            return Unit.INSTANCE;
                                        }
                                    });
                                    Updater.m337setimpl(composerImpl, function112, new Function2() { // from class: androidx.compose.ui.viewinterop.AndroidView_androidKt$AndroidView$2$2
                                        @Override // kotlin.jvm.functions.Function2
                                        public final Object invoke(Object obj, Object obj2) {
                                            ViewFactoryHolder viewFactoryHolderAccess$requireViewFactoryHolder = AndroidView_androidKt.access$requireViewFactoryHolder((LayoutNode) obj);
                                            viewFactoryHolderAccess$requireViewFactoryHolder.updateBlock = (Function1) obj2;
                                            viewFactoryHolderAccess$requireViewFactoryHolder.update = new ViewFactoryHolder$updateBlock$1(viewFactoryHolderAccess$requireViewFactoryHolder);
                                            viewFactoryHolderAccess$requireViewFactoryHolder.hasUpdateBlock = true;
                                            ((AndroidViewHolder$runUpdate$1) viewFactoryHolderAccess$requireViewFactoryHolder.runUpdate).invoke();
                                            return Unit.INSTANCE;
                                        }
                                    });
                                    Updater.m337setimpl(composerImpl, function111, new Function2() { // from class: androidx.compose.ui.viewinterop.AndroidView_androidKt$AndroidView$2$3
                                        @Override // kotlin.jvm.functions.Function2
                                        public final Object invoke(Object obj, Object obj2) {
                                            ViewFactoryHolder viewFactoryHolderAccess$requireViewFactoryHolder = AndroidView_androidKt.access$requireViewFactoryHolder((LayoutNode) obj);
                                            viewFactoryHolderAccess$requireViewFactoryHolder.releaseBlock = (Function1) obj2;
                                            viewFactoryHolderAccess$requireViewFactoryHolder.release = new ViewFactoryHolder$releaseBlock$1(viewFactoryHolderAccess$requireViewFactoryHolder);
                                            return Unit.INSTANCE;
                                        }
                                    });
                                    composerImpl.end(true);
                                    composerImpl.end(false);
                                } else {
                                    ComposablesKt.invalidApplier();
                                    throw null;
                                }
                            } else {
                                function111 = function113;
                                composerImpl.startReplaceGroup(608635513);
                                Function0 function0CreateAndroidViewNodeFactory2 = createAndroidViewNodeFactory(function1, composerImpl, i3 & 14);
                                if (applier instanceof UiApplier) {
                                    composerImpl.startNode();
                                    if (composerImpl.inserting) {
                                        composerImpl.createNode(function0CreateAndroidViewNodeFactory2);
                                    } else {
                                        composerImpl.useNode();
                                    }
                                    m886updateViewHolderParams6NefGtU(composerImpl, modifierMaterializeModifier, currentCompositeKeyHash, density, lifecycleOwner, savedStateRegistryOwner, layoutDirection, persistentCompositionLocalMapCurrentCompositionLocalScope);
                                    Updater.m337setimpl(composerImpl, function112, new Function2() { // from class: androidx.compose.ui.viewinterop.AndroidView_androidKt$AndroidView$3$1
                                        @Override // kotlin.jvm.functions.Function2
                                        public final Object invoke(Object obj, Object obj2) {
                                            ViewFactoryHolder viewFactoryHolderAccess$requireViewFactoryHolder = AndroidView_androidKt.access$requireViewFactoryHolder((LayoutNode) obj);
                                            viewFactoryHolderAccess$requireViewFactoryHolder.updateBlock = (Function1) obj2;
                                            viewFactoryHolderAccess$requireViewFactoryHolder.update = new ViewFactoryHolder$updateBlock$1(viewFactoryHolderAccess$requireViewFactoryHolder);
                                            viewFactoryHolderAccess$requireViewFactoryHolder.hasUpdateBlock = true;
                                            ((AndroidViewHolder$runUpdate$1) viewFactoryHolderAccess$requireViewFactoryHolder.runUpdate).invoke();
                                            return Unit.INSTANCE;
                                        }
                                    });
                                    Updater.m337setimpl(composerImpl, function111, new Function2() { // from class: androidx.compose.ui.viewinterop.AndroidView_androidKt$AndroidView$3$2
                                        @Override // kotlin.jvm.functions.Function2
                                        public final Object invoke(Object obj, Object obj2) {
                                            ViewFactoryHolder viewFactoryHolderAccess$requireViewFactoryHolder = AndroidView_androidKt.access$requireViewFactoryHolder((LayoutNode) obj);
                                            viewFactoryHolderAccess$requireViewFactoryHolder.releaseBlock = (Function1) obj2;
                                            viewFactoryHolderAccess$requireViewFactoryHolder.release = new ViewFactoryHolder$releaseBlock$1(viewFactoryHolderAccess$requireViewFactoryHolder);
                                            return Unit.INSTANCE;
                                        }
                                    });
                                    composerImpl.end(true);
                                    composerImpl.end(false);
                                } else {
                                    ComposablesKt.invalidApplier();
                                    throw null;
                                }
                            }
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                            Function1 function114 = function112;
                            function19 = function111;
                            function18 = function15;
                            function110 = function114;
                        } else {
                            composerImpl.skipToGroupEnd();
                            modifier3 = modifier2;
                            function18 = function15;
                            function19 = function16;
                            function110 = function17;
                        }
                        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                        if (recomposeScopeImplEndRestartGroup != null) {
                            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.ui.viewinterop.AndroidView_androidKt.AndroidView.4
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(2);
                                }

                                @Override // kotlin.jvm.functions.Function2
                                public final Object invoke(Object obj, Object obj2) {
                                    ((Number) obj2).intValue();
                                    AndroidView_androidKt.AndroidView(function1, modifier3, function18, function19, function110, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                                    return Unit.INSTANCE;
                                }
                            };
                            return;
                        }
                        return;
                    }
                    i3 |= 24576;
                    function17 = function14;
                    if (composerImpl.shouldExecute(i3 & 1, (i3 & 9363) != 9362)) {
                    }
                    recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                    if (recomposeScopeImplEndRestartGroup != null) {
                    }
                }
                function16 = function13;
                i6 = i2 & 16;
                if (i6 != 0) {
                }
                function17 = function14;
                if (composerImpl.shouldExecute(i3 & 1, (i3 & 9363) != 9362)) {
                }
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup != null) {
                }
            }
            function15 = function12;
            i5 = i2 & 8;
            if (i5 != 0) {
            }
            function16 = function13;
            i6 = i2 & 16;
            if (i6 != 0) {
            }
            function17 = function14;
            if (composerImpl.shouldExecute(i3 & 1, (i3 & 9363) != 9362)) {
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup != null) {
            }
        }
        modifier2 = modifier;
        i4 = i2 & 4;
        if (i4 == 0) {
        }
        function15 = function12;
        i5 = i2 & 8;
        if (i5 != 0) {
        }
        function16 = function13;
        i6 = i2 & 16;
        if (i6 != 0) {
        }
        function17 = function14;
        if (composerImpl.shouldExecute(i3 & 1, (i3 & 9363) != 9362)) {
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
        }
    }
}
