package com.android.systemui.media.mediaoutput.compose;

import androidx.compose.foundation.ClickableKt;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.material3.SurfaceKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner;
import androidx.navigation.NavHostController;
import androidx.navigation.Navigator;
import androidx.navigation.compose.NavHostControllerKt;
import com.android.systemui.media.mediaoutput.compose.common.DismissCallback;
import com.android.systemui.media.mediaoutput.compose.common.Feature;
import com.android.systemui.media.mediaoutput.compose.ext.CompositionExtKt;
import com.android.systemui.media.mediaoutput.viewmodel.LabsViewModel;
import com.android.systemui.media.mediaoutput.viewmodel.ViewModelKt;
import com.samsung.sesl.compose.foundation.theme.ThemeKt;
import kotlin.Result;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public abstract class MediaOutputHostKt {
    /* JADX WARN: Type inference failed for: r2v8, types: [com.android.systemui.media.mediaoutput.compose.MediaOutputHostKt$MediaOutputHost$1, kotlin.jvm.internal.Lambda] */
    public static final void MediaOutputHost(final LabsViewModel labsViewModel, Composer composer, final int i, final int i2) {
        Object failure;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1378427780);
        int i3 = i2 & 1;
        int i4 = i3 != 0 ? i | 2 : i;
        if (i3 == 1 && (i4 & 11) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            composerImpl.startDefaults();
            if ((i & 1) != 0 && !composerImpl.getDefaultsInvalid()) {
                composerImpl.skipToGroupEnd();
            } else if (i3 != 0) {
                composerImpl.startReplaceGroup(1487631618);
                LocalViewModelStoreOwner.INSTANCE.getClass();
                ViewModelStoreOwner current = LocalViewModelStoreOwner.getCurrent(composerImpl);
                if (current == null) {
                    throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner".toString());
                }
                CreationExtras defaultViewModelCreationExtras = current instanceof HasDefaultViewModelProviderFactory ? ((HasDefaultViewModelProviderFactory) current).getDefaultViewModelCreationExtras() : CreationExtras.Empty.INSTANCE;
                try {
                    int i5 = Result.$r8$clinit;
                    failure = (ViewModelProvider.Factory) composerImpl.consume(CompositionExtKt.LocalViewModelProviderFactory);
                } catch (Throwable th) {
                    int i6 = Result.$r8$clinit;
                    failure = new Result.Failure(th);
                }
                Throwable m2527exceptionOrNullimpl = Result.m2527exceptionOrNullimpl(failure);
                if (m2527exceptionOrNullimpl != null) {
                    m2527exceptionOrNullimpl.printStackTrace();
                }
                if (failure instanceof Result.Failure) {
                    failure = null;
                }
                ViewModelProvider.Factory factory = (ViewModelProvider.Factory) failure;
                if (factory == null) {
                    factory = ViewModelKt.createDaggerViewModelFactory(current);
                }
                ViewModel viewModel = ViewModelKt.get(current, LabsViewModel.class, factory, defaultViewModelCreationExtras);
                composerImpl.end(false);
                labsViewModel = (LabsViewModel) viewModel;
            }
            composerImpl.endDefaults();
            OpaqueKey opaqueKey = ComposerKt.invocation;
            final MutableState collectAsState = SnapshotStateKt.collectAsState(labsViewModel.isCloseOnTouchOutside, Boolean.TRUE, null, composerImpl, 56, 2);
            final MutableState collectAsState2 = SnapshotStateKt.collectAsState(labsViewModel.isSupportMultipleMediaSession, Boolean.FALSE, null, composerImpl, 56, 2);
            ThemeKt.SeslTheme(false, null, ComposableLambdaKt.rememberComposableLambda(-767531723, composerImpl, new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.MediaOutputHostKt$MediaOutputHost$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                /* JADX WARN: Type inference failed for: r7v1, types: [com.android.systemui.media.mediaoutput.compose.MediaOutputHostKt$MediaOutputHost$1$2, kotlin.jvm.internal.Lambda] */
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    Composer composer2 = (Composer) obj;
                    if ((((Number) obj2).intValue() & 11) == 2) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                            return Unit.INSTANCE;
                        }
                    }
                    OpaqueKey opaqueKey2 = ComposerKt.invocation;
                    ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                    final Feature feature = (Feature) composerImpl3.consume(CompositionExtKt.LocalFeature);
                    final DismissCallback dismissCallback = (DismissCallback) composerImpl3.consume(CompositionExtKt.LocalDismissCallback);
                    final NavHostController rememberNavController = NavHostControllerKt.rememberNavController(new Navigator[0], composerImpl3);
                    final MutableState collectAsState3 = SnapshotStateKt.collectAsState(rememberNavController.currentBackStackEntryFlow, null, null, composerImpl3, 48, 2);
                    Modifier fillMaxWidth = SizeKt.fillMaxWidth(Modifier.Companion, 1.0f);
                    final State state = State.this;
                    Modifier m30clickableO2vRcR0$default = ClickableKt.m30clickableO2vRcR0$default(fillMaxWidth, null, null, false, null, new Function0() { // from class: com.android.systemui.media.mediaoutput.compose.MediaOutputHostKt$MediaOutputHost$1.1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            if (((Boolean) state.getValue()).booleanValue() && !NavHostController.this.popBackStack()) {
                                ((Feature) dismissCallback).animateDismiss();
                            }
                            return Unit.INSTANCE;
                        }
                    }, 28);
                    Color.Companion.getClass();
                    long j = Color.Transparent;
                    MaterialTheme.INSTANCE.getClass();
                    long j2 = MaterialTheme.getColorScheme(composerImpl3).onSurfaceVariant;
                    final State state2 = collectAsState2;
                    SurfaceKt.m248SurfaceT9BRK9s(m30clickableO2vRcR0$default, null, j, j2, 0.0f, 0.0f, null, ComposableLambdaKt.rememberComposableLambda(-1098278342, composerImpl3, new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.MediaOutputHostKt$MediaOutputHost$1.2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        /* JADX WARN: Code restructure failed: missing block: B:15:0x0077, code lost:
                        
                            if (r6 == androidx.compose.runtime.Composer.Companion.Empty) goto L15;
                         */
                        /* JADX WARN: Code restructure failed: missing block: B:23:0x00a6, code lost:
                        
                            if (r1 != null) goto L32;
                         */
                        @Override // kotlin.jvm.functions.Function2
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public final java.lang.Object invoke(java.lang.Object r19, java.lang.Object r20) {
                            /*
                                Method dump skipped, instructions count: 225
                                To view this dump change 'Code comments level' option to 'DEBUG'
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.MediaOutputHostKt$MediaOutputHost$1.AnonymousClass2.invoke(java.lang.Object, java.lang.Object):java.lang.Object");
                        }
                    }), composerImpl3, 12583296, 114);
                    return Unit.INSTANCE;
                }
            }), composerImpl, 384, 3);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.MediaOutputHostKt$MediaOutputHost$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    MediaOutputHostKt.MediaOutputHost(LabsViewModel.this, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
