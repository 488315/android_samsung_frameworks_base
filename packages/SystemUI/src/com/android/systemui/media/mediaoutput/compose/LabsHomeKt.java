package com.android.systemui.media.mediaoutput.compose;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner;
import com.android.systemui.media.mediaoutput.compose.ext.CompositionExtKt;
import com.android.systemui.media.mediaoutput.compose.widget.ActionBarKt;
import com.android.systemui.media.mediaoutput.compose.widget.ListsKt;
import com.android.systemui.media.mediaoutput.viewmodel.LabsViewModel;
import com.android.systemui.media.mediaoutput.viewmodel.ViewModelKt;
import com.samsung.sesl.compose.template.SeslTopAppBarTemplate$ActionScope;
import kotlin.Result;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class LabsHomeKt {
    public static final void ActionLabs(final LabsViewModel labsViewModel, Composer composer, int i) {
        Object failure;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(506053709);
        if (((i | 2) & 3) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            composerImpl.startDefaults();
            if ((i & 1) == 0 || composerImpl.getDefaultsInvalid()) {
                composerImpl.startReplaceGroup(1487631618);
                LocalViewModelStoreOwner.INSTANCE.getClass();
                ViewModelStoreOwner current = LocalViewModelStoreOwner.getCurrent(composerImpl);
                if (current == null) {
                    throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
                }
                CreationExtras defaultViewModelCreationExtras = current instanceof HasDefaultViewModelProviderFactory ? ((HasDefaultViewModelProviderFactory) current).getDefaultViewModelCreationExtras() : CreationExtras.Empty.INSTANCE;
                try {
                    int i2 = Result.$r8$clinit;
                    failure = (ViewModelProvider.Factory) composerImpl.consume(CompositionExtKt.LocalViewModelProviderFactory);
                } catch (Throwable th) {
                    int i3 = Result.$r8$clinit;
                    failure = new Result.Failure(th);
                }
                Throwable m3422exceptionOrNullimpl = Result.m3422exceptionOrNullimpl(failure);
                if (m3422exceptionOrNullimpl != null) {
                    m3422exceptionOrNullimpl.printStackTrace();
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
            } else {
                composerImpl.skipToGroupEnd();
            }
            composerImpl.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.ActionLabs (LabsHome.kt:191)");
            }
            ListsKt.SecSubHeader("Actions", composerImpl, 6);
            ListsKt.ListItemContainer(ComposableLambdaKt.rememberComposableLambda(1062474114, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.LabsHomeKt$ActionLabs$1
                /* JADX WARN: Code restructure failed: missing block: B:15:0x005d, code lost:
                
                    if (r0 == androidx.compose.runtime.Composer.Companion.Empty) goto L15;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:19:0x008d, code lost:
                
                    if (r2 == androidx.compose.runtime.Composer.Companion.Empty) goto L20;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:25:0x00d0, code lost:
                
                    if (r0 == androidx.compose.runtime.Composer.Companion.Empty) goto L27;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:31:0x0102, code lost:
                
                    if (r0 == androidx.compose.runtime.Composer.Companion.Empty) goto L33;
                 */
                @Override // kotlin.jvm.functions.Function3
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object invoke(java.lang.Object r12, java.lang.Object r13, java.lang.Object r14) {
                    /*
                        Method dump skipped, instructions count: 304
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.LabsHomeKt$ActionLabs$1.invoke(java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
                }
            }, composerImpl), composerImpl, 6);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new LabsHomeKt$$ExternalSyntheticLambda1(labsViewModel, i, 4);
        }
    }

    public static final void ChromecastLabs(final LabsViewModel labsViewModel, Composer composer, int i) {
        Object failure;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1172440810);
        if (((i | 2) & 3) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            composerImpl.startDefaults();
            if ((i & 1) == 0 || composerImpl.getDefaultsInvalid()) {
                composerImpl.startReplaceGroup(1487631618);
                LocalViewModelStoreOwner.INSTANCE.getClass();
                ViewModelStoreOwner current = LocalViewModelStoreOwner.getCurrent(composerImpl);
                if (current == null) {
                    throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
                }
                CreationExtras defaultViewModelCreationExtras = current instanceof HasDefaultViewModelProviderFactory ? ((HasDefaultViewModelProviderFactory) current).getDefaultViewModelCreationExtras() : CreationExtras.Empty.INSTANCE;
                try {
                    int i2 = Result.$r8$clinit;
                    failure = (ViewModelProvider.Factory) composerImpl.consume(CompositionExtKt.LocalViewModelProviderFactory);
                } catch (Throwable th) {
                    int i3 = Result.$r8$clinit;
                    failure = new Result.Failure(th);
                }
                Throwable m3422exceptionOrNullimpl = Result.m3422exceptionOrNullimpl(failure);
                if (m3422exceptionOrNullimpl != null) {
                    m3422exceptionOrNullimpl.printStackTrace();
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
            } else {
                composerImpl.skipToGroupEnd();
            }
            composerImpl.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.ChromecastLabs (LabsHome.kt:147)");
            }
            ListsKt.SecSubHeader("Chromecast Labs", composerImpl, 6);
            ListsKt.ListItemContainer(ComposableLambdaKt.rememberComposableLambda(34124191, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.LabsHomeKt$ChromecastLabs$1
                /* JADX WARN: Code restructure failed: missing block: B:15:0x0068, code lost:
                
                    if (r1 == androidx.compose.runtime.Composer.Companion.Empty) goto L15;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:19:0x009f, code lost:
                
                    if (r1 == androidx.compose.runtime.Composer.Companion.Empty) goto L20;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:23:0x00d5, code lost:
                
                    if (r13 == androidx.compose.runtime.Composer.Companion.Empty) goto L25;
                 */
                @Override // kotlin.jvm.functions.Function3
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object invoke(java.lang.Object r12, java.lang.Object r13, java.lang.Object r14) {
                    /*
                        Method dump skipped, instructions count: 258
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.LabsHomeKt$ChromecastLabs$1.invoke(java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
                }
            }, composerImpl), composerImpl, 6);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new LabsHomeKt$$ExternalSyntheticLambda1(labsViewModel, i, 0);
        }
    }

    public static final void DebugLabs(final LabsViewModel labsViewModel, Composer composer, int i) {
        Object failure;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1324084820);
        if (((i | 2) & 3) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            composerImpl.startDefaults();
            if ((i & 1) == 0 || composerImpl.getDefaultsInvalid()) {
                composerImpl.startReplaceGroup(1487631618);
                LocalViewModelStoreOwner.INSTANCE.getClass();
                ViewModelStoreOwner current = LocalViewModelStoreOwner.getCurrent(composerImpl);
                if (current == null) {
                    throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
                }
                CreationExtras defaultViewModelCreationExtras = current instanceof HasDefaultViewModelProviderFactory ? ((HasDefaultViewModelProviderFactory) current).getDefaultViewModelCreationExtras() : CreationExtras.Empty.INSTANCE;
                try {
                    int i2 = Result.$r8$clinit;
                    failure = (ViewModelProvider.Factory) composerImpl.consume(CompositionExtKt.LocalViewModelProviderFactory);
                } catch (Throwable th) {
                    int i3 = Result.$r8$clinit;
                    failure = new Result.Failure(th);
                }
                Throwable m3422exceptionOrNullimpl = Result.m3422exceptionOrNullimpl(failure);
                if (m3422exceptionOrNullimpl != null) {
                    m3422exceptionOrNullimpl.printStackTrace();
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
            } else {
                composerImpl.skipToGroupEnd();
            }
            composerImpl.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.DebugLabs (LabsHome.kt:111)");
            }
            ListsKt.SecSubHeader("Debug Labs", composerImpl, 6);
            ListsKt.ListItemContainer(ComposableLambdaKt.rememberComposableLambda(494979543, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.LabsHomeKt$DebugLabs$1
                /* JADX WARN: Code restructure failed: missing block: B:15:0x0073, code lost:
                
                    if (r2 == androidx.compose.runtime.Composer.Companion.Empty) goto L15;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:19:0x00aa, code lost:
                
                    if (r2 == androidx.compose.runtime.Composer.Companion.Empty) goto L20;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:23:0x00e0, code lost:
                
                    if (r13 == androidx.compose.runtime.Composer.Companion.Empty) goto L25;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:27:0x0116, code lost:
                
                    if (r13 == androidx.compose.runtime.Composer.Companion.Empty) goto L30;
                 */
                @Override // kotlin.jvm.functions.Function3
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object invoke(java.lang.Object r12, java.lang.Object r13, java.lang.Object r14) {
                    /*
                        Method dump skipped, instructions count: 324
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.LabsHomeKt$DebugLabs$1.invoke(java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
                }
            }, composerImpl), composerImpl, 6);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new LabsHomeKt$$ExternalSyntheticLambda1(labsViewModel, i, 1);
        }
    }

    public static final void Labs(final LabsViewModel labsViewModel, Composer composer, int i) {
        Object failure;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-16641565);
        if (((i | 2) & 3) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            composerImpl.startDefaults();
            if ((i & 1) == 0 || composerImpl.getDefaultsInvalid()) {
                composerImpl.startReplaceGroup(1487631618);
                LocalViewModelStoreOwner.INSTANCE.getClass();
                ViewModelStoreOwner current = LocalViewModelStoreOwner.getCurrent(composerImpl);
                if (current == null) {
                    throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
                }
                CreationExtras defaultViewModelCreationExtras = current instanceof HasDefaultViewModelProviderFactory ? ((HasDefaultViewModelProviderFactory) current).getDefaultViewModelCreationExtras() : CreationExtras.Empty.INSTANCE;
                try {
                    int i2 = Result.$r8$clinit;
                    failure = (ViewModelProvider.Factory) composerImpl.consume(CompositionExtKt.LocalViewModelProviderFactory);
                } catch (Throwable th) {
                    int i3 = Result.$r8$clinit;
                    failure = new Result.Failure(th);
                }
                Throwable m3422exceptionOrNullimpl = Result.m3422exceptionOrNullimpl(failure);
                if (m3422exceptionOrNullimpl != null) {
                    m3422exceptionOrNullimpl.printStackTrace();
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
            } else {
                composerImpl.skipToGroupEnd();
            }
            composerImpl.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.Labs (LabsHome.kt:75)");
            }
            ListsKt.SecSubHeader("Labs", composerImpl, 6);
            ListsKt.ListItemContainer(ComposableLambdaKt.rememberComposableLambda(744991448, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.LabsHomeKt$Labs$1
                /* JADX WARN: Code restructure failed: missing block: B:15:0x0073, code lost:
                
                    if (r2 == androidx.compose.runtime.Composer.Companion.Empty) goto L15;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:19:0x00aa, code lost:
                
                    if (r2 == androidx.compose.runtime.Composer.Companion.Empty) goto L20;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:23:0x00e1, code lost:
                
                    if (r13 == androidx.compose.runtime.Composer.Companion.Empty) goto L25;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:27:0x0118, code lost:
                
                    if (r13 == androidx.compose.runtime.Composer.Companion.Empty) goto L30;
                 */
                @Override // kotlin.jvm.functions.Function3
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object invoke(java.lang.Object r12, java.lang.Object r13, java.lang.Object r14) {
                    /*
                        Method dump skipped, instructions count: 326
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.LabsHomeKt$Labs$1.invoke(java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
                }
            }, composerImpl), composerImpl, 6);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new LabsHomeKt$$ExternalSyntheticLambda1(labsViewModel, i, 3);
        }
    }

    public static final void LabsHome(Function0 function0, final LabsViewModel labsViewModel, Composer composer, final int i) {
        Object failure;
        final Function0 function02;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1253005640);
        int i2 = (composerImpl.changedInstance(function0) ? 4 : 2) | i | 16;
        if ((i2 & 19) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            function02 = function0;
        } else {
            composerImpl.startDefaults();
            if ((i & 1) == 0 || composerImpl.getDefaultsInvalid()) {
                composerImpl.startReplaceGroup(1487631618);
                LocalViewModelStoreOwner.INSTANCE.getClass();
                ViewModelStoreOwner current = LocalViewModelStoreOwner.getCurrent(composerImpl);
                if (current == null) {
                    throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
                }
                CreationExtras defaultViewModelCreationExtras = current instanceof HasDefaultViewModelProviderFactory ? ((HasDefaultViewModelProviderFactory) current).getDefaultViewModelCreationExtras() : CreationExtras.Empty.INSTANCE;
                try {
                    int i3 = Result.$r8$clinit;
                    failure = (ViewModelProvider.Factory) composerImpl.consume(CompositionExtKt.LocalViewModelProviderFactory);
                } catch (Throwable th) {
                    int i4 = Result.$r8$clinit;
                    failure = new Result.Failure(th);
                }
                Throwable m3422exceptionOrNullimpl = Result.m3422exceptionOrNullimpl(failure);
                if (m3422exceptionOrNullimpl != null) {
                    m3422exceptionOrNullimpl.printStackTrace();
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
            } else {
                composerImpl.skipToGroupEnd();
            }
            int i5 = i2 & (-113);
            composerImpl.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.LabsHome (LabsHome.kt:28)");
            }
            ComposableLambdaImpl rememberComposableLambda = ComposableLambdaKt.rememberComposableLambda(-1132156546, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.LabsHomeKt$LabsHome$1
                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    SeslTopAppBarTemplate$ActionScope seslTopAppBarTemplate$ActionScope = (SeslTopAppBarTemplate$ActionScope) obj;
                    Composer composer2 = (Composer) obj2;
                    int intValue = ((Number) obj3).intValue();
                    if ((intValue & 6) == 0) {
                        intValue |= (intValue & 8) == 0 ? ((ComposerImpl) composer2).changed(seslTopAppBarTemplate$ActionScope) : ((ComposerImpl) composer2).changedInstance(seslTopAppBarTemplate$ActionScope) ? 4 : 2;
                    }
                    if ((intValue & 19) == 18) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                            return Unit.INSTANCE;
                        }
                    }
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.LabsHome.<anonymous> (LabsHome.kt:33)");
                    }
                    final LabsViewModel labsViewModel2 = LabsViewModel.this;
                    ComposableLambdaImpl rememberComposableLambda2 = ComposableLambdaKt.rememberComposableLambda(2013360012, new Function4() { // from class: com.android.systemui.media.mediaoutput.compose.LabsHomeKt$LabsHome$1.1
                        /* JADX WARN: Code restructure failed: missing block: B:20:0x00b7, code lost:
                        
                            if (r2 == androidx.compose.runtime.Composer.Companion.Empty) goto L26;
                         */
                        /* JADX WARN: Code restructure failed: missing block: B:29:0x0072, code lost:
                        
                            if (r3 == androidx.compose.runtime.Composer.Companion.Empty) goto L18;
                         */
                        @Override // kotlin.jvm.functions.Function4
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public final java.lang.Object invoke(java.lang.Object r14, java.lang.Object r15, java.lang.Object r16, java.lang.Object r17) {
                            /*
                                r13 = this;
                                androidx.compose.foundation.layout.RowScope r14 = (androidx.compose.foundation.layout.RowScope) r14
                                r14 = r15
                                com.samsung.sesl.compose.template.SeslTopAppBarTemplate$ActionItemScope r14 = (com.samsung.sesl.compose.template.SeslTopAppBarTemplate$ActionItemScope) r14
                                r3 = r16
                                androidx.compose.runtime.Composer r3 = (androidx.compose.runtime.Composer) r3
                                r14 = r17
                                java.lang.Number r14 = (java.lang.Number) r14
                                int r14 = r14.intValue()
                                r14 = r14 & 129(0x81, float:1.81E-43)
                                r0 = 128(0x80, float:1.8E-43)
                                if (r14 != r0) goto L26
                                r14 = r3
                                androidx.compose.runtime.ComposerImpl r14 = (androidx.compose.runtime.ComposerImpl) r14
                                boolean r0 = r14.getSkipping()
                                if (r0 != 0) goto L21
                                goto L26
                            L21:
                                r14.skipToGroupEnd()
                                goto Ldd
                            L26:
                                boolean r14 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                                if (r14 == 0) goto L31
                                java.lang.String r14 = "com.android.systemui.media.mediaoutput.compose.LabsHome.<anonymous>.<anonymous> (LabsHome.kt:35)"
                                androidx.compose.runtime.ComposerKt.traceEventStart(r14)
                            L31:
                                com.android.systemui.media.mediaoutput.viewmodel.LabsViewModel r13 = com.android.systemui.media.mediaoutput.viewmodel.LabsViewModel.this
                                com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$1 r0 = r13.isShowLabsMenu
                                r4 = 48
                                r5 = 2
                                r1 = 0
                                r2 = 0
                                androidx.compose.runtime.MutableState r14 = androidx.compose.runtime.SnapshotStateKt.collectAsState(r0, r1, r2, r3, r4, r5)
                                java.lang.Object r14 = r14.getValue()
                                java.lang.Boolean r14 = (java.lang.Boolean) r14
                                r10 = r3
                                androidx.compose.runtime.ComposerImpl r10 = (androidx.compose.runtime.ComposerImpl) r10
                                r0 = 1868165050(0x6f59efba, float:6.744806E28)
                                r10.startReplaceGroup(r0)
                                androidx.compose.runtime.Composer$Companion r0 = androidx.compose.runtime.Composer.Companion
                                r1 = 0
                                if (r14 != 0) goto L54
                                r14 = 0
                                goto L9d
                            L54:
                                boolean r4 = r14.booleanValue()
                                r2 = 475927255(0x1c5e12d7, float:7.347799E-22)
                                r10.startReplaceGroup(r2)
                                boolean r2 = r10.changedInstance(r13)
                                boolean r3 = r10.changed(r4)
                                r2 = r2 | r3
                                java.lang.Object r3 = r10.rememberedValue()
                                if (r2 != 0) goto L74
                                r0.getClass()
                                androidx.compose.runtime.Composer$Companion$Empty$1 r2 = androidx.compose.runtime.Composer.Companion.Empty
                                if (r3 != r2) goto L7c
                            L74:
                                com.android.systemui.media.mediaoutput.compose.LabsHomeKt$LabsHome$1$1$$ExternalSyntheticLambda0 r3 = new com.android.systemui.media.mediaoutput.compose.LabsHomeKt$LabsHome$1$1$$ExternalSyntheticLambda0
                                r3.<init>()
                                r10.updateRememberedValue(r3)
                            L7c:
                                r5 = r3
                                kotlin.jvm.functions.Function1 r5 = (kotlin.jvm.functions.Function1) r5
                                r10.end(r1)
                                com.samsung.sesl.compose.component.SeslSwitchDefaults r2 = com.samsung.sesl.compose.component.SeslSwitchDefaults.INSTANCE
                                r6 = 4281891583(0xff387aff, double:2.1155355304E-314)
                                long r6 = androidx.compose.ui.graphics.ColorKt.Color(r6)
                                r2.getClass()
                                com.samsung.sesl.compose.component.SeslSwitchColors r8 = com.samsung.sesl.compose.component.SeslSwitchDefaults.m3326colorsoq7We08(r6, r10)
                                r11 = 0
                                r12 = 44
                                r6 = 0
                                r7 = 0
                                r9 = 0
                                com.samsung.sesl.compose.component.SwitchKt.SeslSwitch(r4, r5, r6, r7, r8, r9, r10, r11, r12)
                            L9d:
                                r10.end(r1)
                                if (r14 != 0) goto Ld4
                                r14 = 1868178644(0x6f5a24d4, float:6.7512255E28)
                                r10.startReplaceGroup(r14)
                                boolean r14 = r10.changedInstance(r13)
                                java.lang.Object r2 = r10.rememberedValue()
                                if (r14 != 0) goto Lb9
                                r0.getClass()
                                androidx.compose.runtime.Composer$Companion$Empty$1 r14 = androidx.compose.runtime.Composer.Companion.Empty
                                if (r2 != r14) goto Lc3
                            Lb9:
                                com.android.systemui.media.mediaoutput.compose.LabsHomeKt$Labs$1$$ExternalSyntheticLambda0 r2 = new com.android.systemui.media.mediaoutput.compose.LabsHomeKt$Labs$1$$ExternalSyntheticLambda0
                                r14 = 12
                                r2.<init>(r13, r14)
                                r10.updateRememberedValue(r2)
                            Lc3:
                                r5 = r2
                                kotlin.jvm.functions.Function1 r5 = (kotlin.jvm.functions.Function1) r5
                                r10.end(r1)
                                r11 = 6
                                r12 = 60
                                r4 = 0
                                r6 = 0
                                r7 = 0
                                r8 = 0
                                r9 = 0
                                com.samsung.sesl.compose.component.SwitchKt.SeslSwitch(r4, r5, r6, r7, r8, r9, r10, r11, r12)
                            Ld4:
                                boolean r13 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                                if (r13 == 0) goto Ldd
                                androidx.compose.runtime.ComposerKt.traceEventEnd()
                            Ldd:
                                kotlin.Unit r13 = kotlin.Unit.INSTANCE
                                return r13
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.LabsHomeKt$LabsHome$1.AnonymousClass1.invoke(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
                        }
                    }, composer2);
                    ComposableSingletons$LabsHomeKt.INSTANCE.getClass();
                    ComposableLambdaImpl composableLambdaImpl = ComposableSingletons$LabsHomeKt.f57lambda1;
                    ComposableLambdaImpl composableLambdaImpl2 = ComposableSingletons$LabsHomeKt.f58lambda2;
                    SeslTopAppBarTemplate$ActionScope.Companion companion = SeslTopAppBarTemplate$ActionScope.Companion;
                    seslTopAppBarTemplate$ActionScope.SeslTopAppBarActionLayout(rememberComposableLambda2, composableLambdaImpl, composableLambdaImpl2, null, composer2, 438 | ((intValue << 12) & 57344));
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl);
            ComposableSingletons$LabsHomeKt.INSTANCE.getClass();
            function02 = function0;
            ActionBarKt.SecTitle(function02, "Labs", rememberComposableLambda, ComposableSingletons$LabsHomeKt.f65lambda9, composerImpl, (i5 & 14) | 3504, 0);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2(labsViewModel, i) { // from class: com.android.systemui.media.mediaoutput.compose.LabsHomeKt$$ExternalSyntheticLambda0
                public final /* synthetic */ LabsViewModel f$1;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    LabsHomeKt.LabsHome(Function0.this, this.f$1, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void ResetItem(final LabsViewModel labsViewModel, Composer composer, int i) {
        Object failure;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1618650109);
        if (((i | 2) & 3) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            composerImpl.startDefaults();
            if ((i & 1) == 0 || composerImpl.getDefaultsInvalid()) {
                composerImpl.startReplaceGroup(1487631618);
                LocalViewModelStoreOwner.INSTANCE.getClass();
                ViewModelStoreOwner current = LocalViewModelStoreOwner.getCurrent(composerImpl);
                if (current == null) {
                    throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
                }
                CreationExtras defaultViewModelCreationExtras = current instanceof HasDefaultViewModelProviderFactory ? ((HasDefaultViewModelProviderFactory) current).getDefaultViewModelCreationExtras() : CreationExtras.Empty.INSTANCE;
                try {
                    int i2 = Result.$r8$clinit;
                    failure = (ViewModelProvider.Factory) composerImpl.consume(CompositionExtKt.LocalViewModelProviderFactory);
                } catch (Throwable th) {
                    int i3 = Result.$r8$clinit;
                    failure = new Result.Failure(th);
                }
                Throwable m3422exceptionOrNullimpl = Result.m3422exceptionOrNullimpl(failure);
                if (m3422exceptionOrNullimpl != null) {
                    m3422exceptionOrNullimpl.printStackTrace();
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
            } else {
                composerImpl.skipToGroupEnd();
            }
            composerImpl.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.ResetItem (LabsHome.kt:224)");
            }
            ListsKt.SecSubHeader("Reset", composerImpl, 6);
            ListsKt.ListItemContainer(ComposableLambdaKt.rememberComposableLambda(200414254, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.LabsHomeKt$ResetItem$1
                /* JADX WARN: Code restructure failed: missing block: B:15:0x0045, code lost:
                
                    if (r8 == androidx.compose.runtime.Composer.Companion.Empty) goto L15;
                 */
                @Override // kotlin.jvm.functions.Function3
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object invoke(java.lang.Object r7, java.lang.Object r8, java.lang.Object r9) {
                    /*
                        r6 = this;
                        androidx.compose.foundation.layout.ColumnScope r7 = (androidx.compose.foundation.layout.ColumnScope) r7
                        androidx.compose.runtime.Composer r8 = (androidx.compose.runtime.Composer) r8
                        java.lang.Number r9 = (java.lang.Number) r9
                        int r7 = r9.intValue()
                        r7 = r7 & 17
                        r9 = 16
                        if (r7 != r9) goto L1e
                        r7 = r8
                        androidx.compose.runtime.ComposerImpl r7 = (androidx.compose.runtime.ComposerImpl) r7
                        boolean r9 = r7.getSkipping()
                        if (r9 != 0) goto L1a
                        goto L1e
                    L1a:
                        r7.skipToGroupEnd()
                        goto L69
                    L1e:
                        boolean r7 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                        if (r7 == 0) goto L29
                        java.lang.String r7 = "com.android.systemui.media.mediaoutput.compose.ResetItem.<anonymous> (LabsHome.kt:228)"
                        androidx.compose.runtime.ComposerKt.traceEventStart(r7)
                    L29:
                        r3 = r8
                        androidx.compose.runtime.ComposerImpl r3 = (androidx.compose.runtime.ComposerImpl) r3
                        r7 = -1111946904(0xffffffffbdb90968, float:-0.09034997)
                        r3.startReplaceGroup(r7)
                        com.android.systemui.media.mediaoutput.viewmodel.LabsViewModel r6 = com.android.systemui.media.mediaoutput.viewmodel.LabsViewModel.this
                        boolean r7 = r3.changedInstance(r6)
                        java.lang.Object r8 = r3.rememberedValue()
                        if (r7 != 0) goto L47
                        androidx.compose.runtime.Composer$Companion r7 = androidx.compose.runtime.Composer.Companion
                        r7.getClass()
                        androidx.compose.runtime.Composer$Companion$Empty$1 r7 = androidx.compose.runtime.Composer.Companion.Empty
                        if (r8 != r7) goto L50
                    L47:
                        com.android.systemui.media.mediaoutput.compose.LabsHomeKt$ResetItem$1$$ExternalSyntheticLambda0 r8 = new com.android.systemui.media.mediaoutput.compose.LabsHomeKt$ResetItem$1$$ExternalSyntheticLambda0
                        r7 = 0
                        r8.<init>(r6, r7)
                        r3.updateRememberedValue(r8)
                    L50:
                        r0 = r8
                        kotlin.jvm.functions.Function0 r0 = (kotlin.jvm.functions.Function0) r0
                        r6 = 0
                        r3.end(r6)
                        r4 = 48
                        r5 = 4
                        java.lang.String r1 = "Reset Labs Settings"
                        r2 = 0
                        com.android.systemui.media.mediaoutput.compose.widget.ListsKt.SecListItem(r0, r1, r2, r3, r4, r5)
                        boolean r6 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                        if (r6 == 0) goto L69
                        androidx.compose.runtime.ComposerKt.traceEventEnd()
                    L69:
                        kotlin.Unit r6 = kotlin.Unit.INSTANCE
                        return r6
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.LabsHomeKt$ResetItem$1.invoke(java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
                }
            }, composerImpl), composerImpl, 6);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new LabsHomeKt$$ExternalSyntheticLambda1(labsViewModel, i, 2);
        }
    }

    public static final void SmartThingsLabs(final LabsViewModel labsViewModel, Composer composer, int i) {
        Object failure;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1156387791);
        if (((i | 2) & 3) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            composerImpl.startDefaults();
            if ((i & 1) == 0 || composerImpl.getDefaultsInvalid()) {
                composerImpl.startReplaceGroup(1487631618);
                LocalViewModelStoreOwner.INSTANCE.getClass();
                ViewModelStoreOwner current = LocalViewModelStoreOwner.getCurrent(composerImpl);
                if (current == null) {
                    throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
                }
                CreationExtras defaultViewModelCreationExtras = current instanceof HasDefaultViewModelProviderFactory ? ((HasDefaultViewModelProviderFactory) current).getDefaultViewModelCreationExtras() : CreationExtras.Empty.INSTANCE;
                try {
                    int i2 = Result.$r8$clinit;
                    failure = (ViewModelProvider.Factory) composerImpl.consume(CompositionExtKt.LocalViewModelProviderFactory);
                } catch (Throwable th) {
                    int i3 = Result.$r8$clinit;
                    failure = new Result.Failure(th);
                }
                Throwable m3422exceptionOrNullimpl = Result.m3422exceptionOrNullimpl(failure);
                if (m3422exceptionOrNullimpl != null) {
                    m3422exceptionOrNullimpl.printStackTrace();
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
            } else {
                composerImpl.skipToGroupEnd();
            }
            composerImpl.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.SmartThingsLabs (LabsHome.kt:176)");
            }
            ListsKt.SecSubHeader("SmartThings Labs", composerImpl, 6);
            ListsKt.ListItemContainer(ComposableLambdaKt.rememberComposableLambda(-2084464612, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.LabsHomeKt$SmartThingsLabs$1
                /* JADX WARN: Code restructure failed: missing block: B:15:0x0051, code lost:
                
                    if (r14 == androidx.compose.runtime.Composer.Companion.Empty) goto L15;
                 */
                @Override // kotlin.jvm.functions.Function3
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object invoke(java.lang.Object r12, java.lang.Object r13, java.lang.Object r14) {
                    /*
                        r11 = this;
                        androidx.compose.foundation.layout.ColumnScope r12 = (androidx.compose.foundation.layout.ColumnScope) r12
                        r3 = r13
                        androidx.compose.runtime.Composer r3 = (androidx.compose.runtime.Composer) r3
                        java.lang.Number r14 = (java.lang.Number) r14
                        int r12 = r14.intValue()
                        r12 = r12 & 17
                        r13 = 16
                        if (r12 != r13) goto L1f
                        r12 = r3
                        androidx.compose.runtime.ComposerImpl r12 = (androidx.compose.runtime.ComposerImpl) r12
                        boolean r13 = r12.getSkipping()
                        if (r13 != 0) goto L1b
                        goto L1f
                    L1b:
                        r12.skipToGroupEnd()
                        goto L7d
                    L1f:
                        boolean r12 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                        if (r12 == 0) goto L2a
                        java.lang.String r12 = "com.android.systemui.media.mediaoutput.compose.SmartThingsLabs.<anonymous> (LabsHome.kt:180)"
                        androidx.compose.runtime.ComposerKt.traceEventStart(r12)
                    L2a:
                        com.android.systemui.media.mediaoutput.viewmodel.LabsViewModel r11 = com.android.systemui.media.mediaoutput.viewmodel.LabsViewModel.this
                        com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$9 r0 = r11.isSupportForUnsupportedTV
                        r4 = 48
                        r5 = 2
                        r1 = 0
                        r2 = 0
                        androidx.compose.runtime.MutableState r12 = androidx.compose.runtime.SnapshotStateKt.collectAsState(r0, r1, r2, r3, r4, r5)
                        r8 = r3
                        androidx.compose.runtime.ComposerImpl r8 = (androidx.compose.runtime.ComposerImpl) r8
                        r13 = 910588785(0x36467b71, float:2.9576152E-6)
                        r8.startReplaceGroup(r13)
                        boolean r13 = r8.changedInstance(r11)
                        java.lang.Object r14 = r8.rememberedValue()
                        if (r13 != 0) goto L53
                        androidx.compose.runtime.Composer$Companion r13 = androidx.compose.runtime.Composer.Companion
                        r13.getClass()
                        androidx.compose.runtime.Composer$Companion$Empty$1 r13 = androidx.compose.runtime.Composer.Companion.Empty
                        if (r14 != r13) goto L5d
                    L53:
                        com.android.systemui.media.mediaoutput.compose.LabsHomeKt$Labs$1$$ExternalSyntheticLambda0 r14 = new com.android.systemui.media.mediaoutput.compose.LabsHomeKt$Labs$1$$ExternalSyntheticLambda0
                        r13 = 13
                        r14.<init>(r11, r13)
                        r8.updateRememberedValue(r14)
                    L5d:
                        r4 = r14
                        kotlin.jvm.functions.Function1 r4 = (kotlin.jvm.functions.Function1) r4
                        r11 = 0
                        r8.end(r11)
                        java.lang.Object r11 = r12.getValue()
                        r7 = r11
                        java.lang.Boolean r7 = (java.lang.Boolean) r7
                        r9 = 48
                        r10 = 4
                        java.lang.String r5 = "Support for unsupported TV"
                        r6 = 0
                        com.android.systemui.media.mediaoutput.compose.widget.ListsKt.SecSwitchListItem(r4, r5, r6, r7, r8, r9, r10)
                        boolean r11 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                        if (r11 == 0) goto L7d
                        androidx.compose.runtime.ComposerKt.traceEventEnd()
                    L7d:
                        kotlin.Unit r11 = kotlin.Unit.INSTANCE
                        return r11
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.LabsHomeKt$SmartThingsLabs$1.invoke(java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
                }
            }, composerImpl), composerImpl, 6);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new LabsHomeKt$$ExternalSyntheticLambda1(labsViewModel, i, 5);
        }
    }
}
