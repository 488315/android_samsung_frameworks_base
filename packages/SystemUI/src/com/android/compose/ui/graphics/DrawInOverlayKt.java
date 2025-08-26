package com.android.compose.ui.graphics;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroupOverlay;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.DisposableEffectResult;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.platform.ComposeView;
import androidx.compose.ui.unit.IntSize;
import androidx.lifecycle.ViewTreeLifecycleOwner;
import androidx.lifecycle.ViewTreeViewModelStoreOwner;
import androidx.savedstate.ViewTreeSavedStateRegistryOwner;
import com.android.systemui.R;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* loaded from: classes.dex */
public abstract class DrawInOverlayKt {
    /* JADX WARN: Removed duplicated region for block: B:38:0x00d4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void FullScreenComposeViewInOverlay(ViewGroupOverlay viewGroupOverlay, final Function1 function1, Composer composer, final int i, final int i2) {
        final ViewGroupOverlay viewGroupOverlay2;
        int i3;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1143562558);
        int i4 = i2 & 1;
        if (i4 != 0) {
            i3 = i | 6;
            viewGroupOverlay2 = viewGroupOverlay;
        } else {
            viewGroupOverlay2 = viewGroupOverlay;
            i3 = (composerImpl.changedInstance(viewGroupOverlay2) ? 4 : 2) | i;
        }
        int i5 = i3 | (composerImpl.changedInstance(function1) ? 32 : 16);
        if ((i5 & 19) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            ViewGroupOverlay viewGroupOverlay3 = i4 != 0 ? null : viewGroupOverlay2;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.compose.ui.graphics.FullScreenComposeViewInOverlay (DrawInOverlay.kt:52)");
            }
            final Context context = (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
            final View view = (View) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalView);
            final ComposerImpl.CompositionContextImpl compositionContextImplRememberCompositionContext = ComposablesKt.rememberCompositionContext(composerImpl);
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            final long j = (displayMetrics.widthPixels << 32) | (displayMetrics.heightPixels & 4294967295L);
            IntSize.Companion companion = IntSize.Companion;
            ViewGroupOverlay viewGroupOverlay4 = viewGroupOverlay3 == null ? (ViewGroupOverlay) view.getRootView().getOverlay() : viewGroupOverlay3;
            Object[] objArr = {context, view, viewGroupOverlay4, compositionContextImplRememberCompositionContext, IntSize.m861boximpl(j)};
            composerImpl.startReplaceGroup(-224292197);
            ViewGroupOverlay viewGroupOverlay5 = viewGroupOverlay3;
            boolean zChangedInstance = ((i5 & 112) == 32) | composerImpl.changedInstance(context) | composerImpl.changedInstance(compositionContextImplRememberCompositionContext) | composerImpl.changedInstance(view) | composerImpl.changedInstance(viewGroupOverlay4) | composerImpl.changed(j);
            Object objRememberedValue = composerImpl.rememberedValue();
            if (!zChangedInstance) {
                Composer.Companion.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    final ViewGroupOverlay viewGroupOverlay6 = viewGroupOverlay4;
                    Function1 function12 = new Function1() { // from class: com.android.compose.ui.graphics.DrawInOverlayKt$$ExternalSyntheticLambda1
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj) {
                            Context context2 = context;
                            final ViewGroupOverlay viewGroupOverlay7 = viewGroupOverlay6;
                            View view2 = view;
                            final ComposeView composeView = new ComposeView(context2, null, 0, 6, null);
                            composeView.setParentCompositionContext(compositionContextImplRememberCompositionContext);
                            composeView.setTag(R.id.view_tree_lifecycle_owner, ViewTreeLifecycleOwner.get(view2));
                            composeView.setTag(R.id.view_tree_view_model_store_owner, ViewTreeViewModelStoreOwner.get(view2));
                            composeView.setTag(R.id.view_tree_saved_state_registry_owner, ViewTreeSavedStateRegistryOwner.get(view2));
                            final Function1 function13 = function1;
                            composeView.setContent(new ComposableLambdaImpl(-1585226444, true, new Function2() { // from class: com.android.compose.ui.graphics.DrawInOverlayKt$FullScreenComposeViewInOverlay$2$1$view$1$1
                                /* JADX WARN: Removed duplicated region for block: B:8:0x001b  */
                                @Override // kotlin.jvm.functions.Function2
                                /*
                                    Code decompiled incorrectly, please refer to instructions dump.
                                */
                                public final Object invoke(Object obj2, Object obj3) {
                                    Composer composer2 = (Composer) obj2;
                                    if ((((Number) obj3).intValue() & 3) == 2) {
                                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                                        if (composerImpl2.getSkipping()) {
                                            composerImpl2.skipToGroupEnd();
                                        } else {
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventStart("com.android.compose.ui.graphics.FullScreenComposeViewInOverlay.<anonymous>.<anonymous>.<anonymous>.<anonymous> (DrawInOverlay.kt:71)");
                                            }
                                            BoxKt.Box(SizeKt.fillMaxSize((Modifier) function13.mo781invoke(composeView), 1.0f), composer2, 0);
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventEnd();
                                            }
                                        }
                                    }
                                    return Unit.INSTANCE;
                                }
                            }));
                            viewGroupOverlay7.add(composeView);
                            long j2 = j;
                            int i6 = (int) (j2 >> 32);
                            int i7 = (int) (j2 & 4294967295L);
                            composeView.measure(View.MeasureSpec.makeSafeMeasureSpec(i6, 1073741824), View.MeasureSpec.makeSafeMeasureSpec(i7, 1073741824));
                            composeView.layout(0, 0, i6, i7);
                            return new DisposableEffectResult() { // from class: com.android.compose.ui.graphics.DrawInOverlayKt$FullScreenComposeViewInOverlay$lambda$10$lambda$9$$inlined$onDispose$1
                                @Override // androidx.compose.runtime.DisposableEffectResult
                                public final void dispose() {
                                    viewGroupOverlay7.remove(composeView);
                                }
                            };
                        }
                    };
                    composerImpl.updateRememberedValue(function12);
                    objRememberedValue = function12;
                }
                composerImpl.end(false);
                EffectsKt.DisposableEffect(objArr, (Function1) objRememberedValue, (Composer) composerImpl);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                viewGroupOverlay2 = viewGroupOverlay5;
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(viewGroupOverlay2, function1, i, i2) { // from class: com.android.compose.ui.graphics.DrawInOverlayKt$$ExternalSyntheticLambda2
                public final /* synthetic */ ViewGroupOverlay f$0;
                public final /* synthetic */ Function1 f$1;
                public final /* synthetic */ int f$3;

                {
                    this.f$3 = i2;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    DrawInOverlayKt.FullScreenComposeViewInOverlay(this.f$0, this.f$1, (Composer) obj, iUpdateChangedFlags, this.f$3);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
