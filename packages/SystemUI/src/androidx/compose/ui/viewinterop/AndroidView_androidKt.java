package androidx.compose.ui.viewinterop;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.Updater;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.autofill.AndroidAutofill$$ExternalSyntheticOutline0;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.node.LayoutNode;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.LayoutDirection;
import androidx.lifecycle.LifecycleOwner;
import androidx.savedstate.SavedStateRegistryOwner;
import com.android.systemui.R;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class AndroidView_androidKt {
    public static final Function1 NoOpUpdate = new Function1() { // from class: androidx.compose.ui.viewinterop.AndroidView_androidKt$NoOpUpdate$1
        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final /* bridge */ /* synthetic */ Object mo779invoke(Object obj) {
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
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: androidx.compose.ui.viewinterop.AndroidView_androidKt$AndroidView$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    AndroidView_androidKt.AndroidView(Function1.this, modifier2, function13, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
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

    /* JADX WARN: Code restructure failed: missing block: B:12:0x006a, code lost:
    
        if (r0 == androidx.compose.runtime.Composer.Companion.Empty) goto L17;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final kotlin.jvm.functions.Function0 createAndroidViewNodeFactory(final kotlin.jvm.functions.Function1 r8, androidx.compose.runtime.Composer r9, int r10) {
        /*
            boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r0 == 0) goto Lb
            java.lang.String r0 = "androidx.compose.ui.viewinterop.createAndroidViewNodeFactory (AndroidView.android.kt:252)"
            androidx.compose.runtime.ComposerKt.traceEventStart(r0)
        Lb:
            int r6 = androidx.compose.runtime.ComposablesKt.getCurrentCompositeKeyHash(r9)
            androidx.compose.runtime.StaticProvidableCompositionLocal r0 = androidx.compose.ui.platform.AndroidCompositionLocals_androidKt.LocalContext
            androidx.compose.runtime.ComposerImpl r9 = (androidx.compose.runtime.ComposerImpl) r9
            java.lang.Object r0 = r9.consume(r0)
            r2 = r0
            android.content.Context r2 = (android.content.Context) r2
            androidx.compose.runtime.ComposerImpl$CompositionContextImpl r4 = androidx.compose.runtime.ComposablesKt.rememberCompositionContext(r9)
            androidx.compose.runtime.StaticProvidableCompositionLocal r0 = androidx.compose.runtime.saveable.SaveableStateRegistryKt.LocalSaveableStateRegistry
            java.lang.Object r0 = r9.consume(r0)
            r5 = r0
            androidx.compose.runtime.saveable.SaveableStateRegistry r5 = (androidx.compose.runtime.saveable.SaveableStateRegistry) r5
            androidx.compose.runtime.StaticProvidableCompositionLocal r0 = androidx.compose.ui.platform.AndroidCompositionLocals_androidKt.LocalView
            java.lang.Object r0 = r9.consume(r0)
            r7 = r0
            android.view.View r7 = (android.view.View) r7
            boolean r0 = r9.changedInstance(r2)
            r1 = r10 & 14
            r1 = r1 ^ 6
            r3 = 4
            if (r1 <= r3) goto L41
            boolean r1 = r9.changed(r8)
            if (r1 != 0) goto L45
        L41:
            r10 = r10 & 6
            if (r10 != r3) goto L47
        L45:
            r10 = 1
            goto L48
        L47:
            r10 = 0
        L48:
            r10 = r10 | r0
            boolean r0 = r9.changedInstance(r4)
            r10 = r10 | r0
            boolean r0 = r9.changedInstance(r5)
            r10 = r10 | r0
            boolean r0 = r9.changed(r6)
            r10 = r10 | r0
            boolean r0 = r9.changedInstance(r7)
            r10 = r10 | r0
            java.lang.Object r0 = r9.rememberedValue()
            if (r10 != 0) goto L6c
            androidx.compose.runtime.Composer$Companion r10 = androidx.compose.runtime.Composer.Companion
            r10.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r10 = androidx.compose.runtime.Composer.Companion.Empty
            if (r0 != r10) goto L76
        L6c:
            androidx.compose.ui.viewinterop.AndroidView_androidKt$createAndroidViewNodeFactory$1$1 r1 = new androidx.compose.ui.viewinterop.AndroidView_androidKt$createAndroidViewNodeFactory$1$1
            r3 = r8
            r1.<init>()
            r9.updateRememberedValue(r1)
            r0 = r1
        L76:
            kotlin.jvm.functions.Function0 r0 = (kotlin.jvm.functions.Function0) r0
            boolean r8 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r8 == 0) goto L81
            androidx.compose.runtime.ComposerKt.traceEventEnd()
        L81:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.viewinterop.AndroidView_androidKt.createAndroidViewNodeFactory(kotlin.jvm.functions.Function1, androidx.compose.runtime.Composer, int):kotlin.jvm.functions.Function0");
    }

    /* renamed from: updateViewHolderParams-6NefGtU, reason: not valid java name */
    public static final void m884updateViewHolderParams6NefGtU(Composer composer, Modifier modifier, int i, Density density, LifecycleOwner lifecycleOwner, SavedStateRegistryOwner savedStateRegistryOwner, LayoutDirection layoutDirection, PersistentCompositionLocalMap persistentCompositionLocalMap) {
        ComposeUiNode.Companion.getClass();
        Updater.m336setimpl(composer, persistentCompositionLocalMap, ComposeUiNode.Companion.SetResolvedCompositionLocals);
        Updater.m336setimpl(composer, modifier, new Function2() { // from class: androidx.compose.ui.viewinterop.AndroidView_androidKt$updateViewHolderParams$1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Modifier modifier2 = (Modifier) obj2;
                ViewFactoryHolder access$requireViewFactoryHolder = AndroidView_androidKt.access$requireViewFactoryHolder((LayoutNode) obj);
                if (modifier2 != access$requireViewFactoryHolder.modifier) {
                    access$requireViewFactoryHolder.modifier = modifier2;
                    Function1 function1 = access$requireViewFactoryHolder.onModifierChanged;
                    if (function1 != null) {
                        ((AndroidViewHolder$layoutNode$1$1) function1).mo779invoke(modifier2);
                    }
                }
                return Unit.INSTANCE;
            }
        });
        Updater.m336setimpl(composer, density, new Function2() { // from class: androidx.compose.ui.viewinterop.AndroidView_androidKt$updateViewHolderParams$2
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Density density2 = (Density) obj2;
                ViewFactoryHolder access$requireViewFactoryHolder = AndroidView_androidKt.access$requireViewFactoryHolder((LayoutNode) obj);
                if (density2 != access$requireViewFactoryHolder.density) {
                    access$requireViewFactoryHolder.density = density2;
                    Function1 function1 = access$requireViewFactoryHolder.onDensityChanged;
                    if (function1 != null) {
                        ((AndroidViewHolder$layoutNode$1$2) function1).mo779invoke(density2);
                    }
                }
                return Unit.INSTANCE;
            }
        });
        Updater.m336setimpl(composer, lifecycleOwner, new Function2() { // from class: androidx.compose.ui.viewinterop.AndroidView_androidKt$updateViewHolderParams$3
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                LifecycleOwner lifecycleOwner2 = (LifecycleOwner) obj2;
                ViewFactoryHolder access$requireViewFactoryHolder = AndroidView_androidKt.access$requireViewFactoryHolder((LayoutNode) obj);
                if (lifecycleOwner2 != access$requireViewFactoryHolder.lifecycleOwner) {
                    access$requireViewFactoryHolder.lifecycleOwner = lifecycleOwner2;
                    access$requireViewFactoryHolder.setTag(R.id.view_tree_lifecycle_owner, lifecycleOwner2);
                }
                return Unit.INSTANCE;
            }
        });
        Updater.m336setimpl(composer, savedStateRegistryOwner, new Function2() { // from class: androidx.compose.ui.viewinterop.AndroidView_androidKt$updateViewHolderParams$4
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                SavedStateRegistryOwner savedStateRegistryOwner2 = (SavedStateRegistryOwner) obj2;
                ViewFactoryHolder access$requireViewFactoryHolder = AndroidView_androidKt.access$requireViewFactoryHolder((LayoutNode) obj);
                if (savedStateRegistryOwner2 != access$requireViewFactoryHolder.savedStateRegistryOwner) {
                    access$requireViewFactoryHolder.savedStateRegistryOwner = savedStateRegistryOwner2;
                    access$requireViewFactoryHolder.setTag(R.id.view_tree_saved_state_registry_owner, savedStateRegistryOwner2);
                }
                return Unit.INSTANCE;
            }
        });
        Updater.m336setimpl(composer, layoutDirection, new Function2() { // from class: androidx.compose.ui.viewinterop.AndroidView_androidKt$updateViewHolderParams$5

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                ViewFactoryHolder access$requireViewFactoryHolder = AndroidView_androidKt.access$requireViewFactoryHolder((LayoutNode) obj);
                int i2 = WhenMappings.$EnumSwitchMapping$0[((LayoutDirection) obj2).ordinal()];
                int i3 = 1;
                if (i2 == 1) {
                    i3 = 0;
                } else if (i2 != 2) {
                    throw new NoWhenBranchMatchedException();
                }
                access$requireViewFactoryHolder.setLayoutDirection(i3);
                return Unit.INSTANCE;
            }
        });
        Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(i))) {
            AnimatedContentKt$$ExternalSyntheticOutline0.m(i, composerImpl, i, function2);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0044  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x005f  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x007a  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0098  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00a3  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x01ad  */
    /* JADX WARN: Removed duplicated region for block: B:55:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:69:0x01a0  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x009a  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x0049  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void AndroidView(final kotlin.jvm.functions.Function1 r18, androidx.compose.ui.Modifier r19, kotlin.jvm.functions.Function1 r20, kotlin.jvm.functions.Function1 r21, kotlin.jvm.functions.Function1 r22, androidx.compose.runtime.Composer r23, final int r24, final int r25) {
        /*
            Method dump skipped, instructions count: 439
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.viewinterop.AndroidView_androidKt.AndroidView(kotlin.jvm.functions.Function1, androidx.compose.ui.Modifier, kotlin.jvm.functions.Function1, kotlin.jvm.functions.Function1, kotlin.jvm.functions.Function1, androidx.compose.runtime.Composer, int, int):void");
    }
}
