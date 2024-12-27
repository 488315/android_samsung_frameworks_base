package com.android.systemui.media.mediaoutput.compose;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;
import androidx.compose.foundation.gestures.TapGestureDetectorKt;
import androidx.compose.foundation.layout.FillElement;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.PaddingValues;
import androidx.compose.foundation.layout.PaddingValuesImpl;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.SpacerKt;
import androidx.compose.foundation.lazy.LazyDslKt;
import androidx.compose.foundation.lazy.LazyListIntervalContent;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.StructuralEqualityPolicy;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.input.pointer.PointerInputScope;
import androidx.compose.ui.input.pointer.SuspendingPointerInputFilterKt;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.LayoutDirection;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner;
import com.android.systemui.R;
import com.android.systemui.media.mediaoutput.common.DeviceUtils;
import com.android.systemui.media.mediaoutput.compose.ext.CompositionExtKt;
import com.android.systemui.media.mediaoutput.compose.widget.ActionBarKt;
import com.android.systemui.media.mediaoutput.compose.widget.ListsKt;
import com.android.systemui.media.mediaoutput.viewmodel.LabsViewModel;
import com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel;
import com.android.systemui.media.mediaoutput.viewmodel.ViewModelKt;
import com.samsung.android.bluetooth.SemBluetoothCastAdapter;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public abstract class SettingHomeKt {
    /* JADX WARN: Type inference failed for: r2v7, types: [com.android.systemui.media.mediaoutput.compose.SettingHomeKt$SettingHome$1, kotlin.jvm.internal.Lambda] */
    public static final void SettingHome(final Function0 function0, final Function1 function1, SettingViewModel settingViewModel, LabsViewModel labsViewModel, Composer composer, final int i, final int i2) {
        int i3;
        final SettingViewModel settingViewModel2;
        Object failure;
        final LabsViewModel labsViewModel2;
        Object failure2;
        final LabsViewModel labsViewModel3;
        final SettingViewModel settingViewModel3;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(2079907393);
        if ((i2 & 1) != 0) {
            i3 = i | 6;
        } else if ((i & 14) == 0) {
            i3 = i | (composerImpl.changedInstance(function0) ? 4 : 2);
        } else {
            i3 = i;
        }
        if ((i2 & 2) != 0) {
            i3 |= 48;
        } else if ((i & 112) == 0) {
            i3 |= composerImpl.changedInstance(function1) ? 32 : 16;
        }
        int i4 = i2 & 4;
        if (i4 != 0) {
            i3 |= 128;
        }
        int i5 = i2 & 8;
        if (i5 != 0) {
            i3 |= 1024;
        }
        if ((i2 & 12) == 12 && (i3 & 5851) == 1170 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            settingViewModel3 = settingViewModel;
            labsViewModel3 = labsViewModel;
        } else {
            composerImpl.startDefaults();
            if ((i & 1) == 0 || composerImpl.getDefaultsInvalid()) {
                if (i4 != 0) {
                    composerImpl.startReplaceGroup(1487631618);
                    LocalViewModelStoreOwner.INSTANCE.getClass();
                    ViewModelStoreOwner current = LocalViewModelStoreOwner.getCurrent(composerImpl);
                    if (current == null) {
                        throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner".toString());
                    }
                    CreationExtras defaultViewModelCreationExtras = current instanceof HasDefaultViewModelProviderFactory ? ((HasDefaultViewModelProviderFactory) current).getDefaultViewModelCreationExtras() : CreationExtras.Empty.INSTANCE;
                    try {
                        int i6 = Result.$r8$clinit;
                        failure2 = (ViewModelProvider.Factory) composerImpl.consume(CompositionExtKt.LocalViewModelProviderFactory);
                    } catch (Throwable th) {
                        int i7 = Result.$r8$clinit;
                        failure2 = new Result.Failure(th);
                    }
                    Throwable m2527exceptionOrNullimpl = Result.m2527exceptionOrNullimpl(failure2);
                    if (m2527exceptionOrNullimpl != null) {
                        m2527exceptionOrNullimpl.printStackTrace();
                    }
                    if (failure2 instanceof Result.Failure) {
                        failure2 = null;
                    }
                    ViewModelProvider.Factory factory = (ViewModelProvider.Factory) failure2;
                    if (factory == null) {
                        factory = ViewModelKt.createDaggerViewModelFactory(current);
                    }
                    ViewModel viewModel = ViewModelKt.get(current, SettingViewModel.class, factory, defaultViewModelCreationExtras);
                    composerImpl.end(false);
                    i3 &= -897;
                    settingViewModel2 = (SettingViewModel) viewModel;
                } else {
                    settingViewModel2 = settingViewModel;
                }
                if (i5 != 0) {
                    composerImpl.startReplaceGroup(1487631618);
                    LocalViewModelStoreOwner.INSTANCE.getClass();
                    ViewModelStoreOwner current2 = LocalViewModelStoreOwner.getCurrent(composerImpl);
                    if (current2 == null) {
                        throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner".toString());
                    }
                    CreationExtras defaultViewModelCreationExtras2 = current2 instanceof HasDefaultViewModelProviderFactory ? ((HasDefaultViewModelProviderFactory) current2).getDefaultViewModelCreationExtras() : CreationExtras.Empty.INSTANCE;
                    try {
                        int i8 = Result.$r8$clinit;
                        failure = (ViewModelProvider.Factory) composerImpl.consume(CompositionExtKt.LocalViewModelProviderFactory);
                    } catch (Throwable th2) {
                        int i9 = Result.$r8$clinit;
                        failure = new Result.Failure(th2);
                    }
                    Throwable m2527exceptionOrNullimpl2 = Result.m2527exceptionOrNullimpl(failure);
                    if (m2527exceptionOrNullimpl2 != null) {
                        m2527exceptionOrNullimpl2.printStackTrace();
                    }
                    ViewModelProvider.Factory factory2 = (ViewModelProvider.Factory) (failure instanceof Result.Failure ? null : failure);
                    if (factory2 == null) {
                        factory2 = ViewModelKt.createDaggerViewModelFactory(current2);
                    }
                    ViewModel viewModel2 = ViewModelKt.get(current2, LabsViewModel.class, factory2, defaultViewModelCreationExtras2);
                    composerImpl.end(false);
                    labsViewModel2 = (LabsViewModel) viewModel2;
                    i3 &= -7169;
                    composerImpl.endDefaults();
                    OpaqueKey opaqueKey = ComposerKt.invocation;
                    ActionBarKt.SecTitle(function0, StringResources_androidKt.stringResource(R.string.settings, new Object[]{StringResources_androidKt.stringResource(R.string.media_output, composerImpl)}, composerImpl), ComposableLambdaKt.rememberComposableLambda(-370486008, composerImpl, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.SettingHomeKt$SettingHome$1

                        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                        /* renamed from: com.android.systemui.media.mediaoutput.compose.SettingHomeKt$SettingHome$1$1, reason: invalid class name */
                        final class AnonymousClass1 extends SuspendLambda implements Function2 {
                            final /* synthetic */ LabsViewModel $labsViewModel;
                            final /* synthetic */ MutableState $tapCount$delegate;
                            private /* synthetic */ Object L$0;
                            int label;

                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            public AnonymousClass1(LabsViewModel labsViewModel, MutableState mutableState, Continuation continuation) {
                                super(2, continuation);
                                this.$labsViewModel = labsViewModel;
                                this.$tapCount$delegate = mutableState;
                            }

                            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                            public final Continuation create(Object obj, Continuation continuation) {
                                AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$labsViewModel, this.$tapCount$delegate, continuation);
                                anonymousClass1.L$0 = obj;
                                return anonymousClass1;
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj, Object obj2) {
                                return ((AnonymousClass1) create((PointerInputScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                            }

                            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                            public final Object invokeSuspend(Object obj) {
                                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                                int i = this.label;
                                if (i == 0) {
                                    ResultKt.throwOnFailure(obj);
                                    final PointerInputScope pointerInputScope = (PointerInputScope) this.L$0;
                                    final LabsViewModel labsViewModel = this.$labsViewModel;
                                    final MutableState mutableState = this.$tapCount$delegate;
                                    Function1 function1 = new Function1() { // from class: com.android.systemui.media.mediaoutput.compose.SettingHomeKt.SettingHome.1.1.1
                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        {
                                            super(1);
                                        }

                                        /* JADX WARN: Code restructure failed: missing block: B:9:0x0093, code lost:
                                        
                                            if (r6 != null) goto L17;
                                         */
                                        @Override // kotlin.jvm.functions.Function1
                                        /*
                                            Code decompiled incorrectly, please refer to instructions dump.
                                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                                        */
                                        public final java.lang.Object invoke(java.lang.Object r18) {
                                            /*
                                                r17 = this;
                                                r0 = r17
                                                r1 = r18
                                                androidx.compose.ui.geometry.Offset r1 = (androidx.compose.ui.geometry.Offset) r1
                                                long r1 = r1.packedValue
                                                androidx.compose.runtime.MutableState r3 = r2
                                                java.lang.Object r4 = r3.getValue()
                                                kotlin.Pair r4 = (kotlin.Pair) r4
                                                androidx.compose.ui.input.pointer.PointerInputScope r5 = r3
                                                float r6 = androidx.compose.ui.geometry.Offset.m326getYimpl(r1)
                                                double r6 = (double) r6
                                                androidx.compose.ui.input.pointer.SuspendingPointerInputModifierNodeImpl r5 = (androidx.compose.ui.input.pointer.SuspendingPointerInputModifierNodeImpl) r5
                                                long r8 = r5.boundsSize
                                                androidx.compose.ui.unit.IntSize$Companion r5 = androidx.compose.ui.unit.IntSize.Companion
                                                r10 = 4294967295(0xffffffff, double:2.1219957905E-314)
                                                long r8 = r8 & r10
                                                int r5 = (int) r8
                                                double r8 = (double) r5
                                                r10 = 4604930618986332160(0x3fe8000000000000, double:0.75)
                                                double r8 = r8 * r10
                                                int r5 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
                                                r6 = 0
                                                if (r5 <= 0) goto L2e
                                                goto L2f
                                            L2e:
                                                r4 = r6
                                            L2f:
                                                r5 = 0
                                                if (r4 == 0) goto L96
                                                androidx.compose.ui.input.pointer.PointerInputScope r7 = r3
                                                java.lang.Object r8 = r4.component1()
                                                java.lang.Number r8 = (java.lang.Number) r8
                                                int r8 = r8.intValue()
                                                java.lang.Object r4 = r4.component2()
                                                java.lang.Number r4 = (java.lang.Number) r4
                                                int r4 = r4.intValue()
                                                float r9 = androidx.compose.ui.geometry.Offset.m325getXimpl(r1)
                                                double r9 = (double) r9
                                                androidx.compose.ui.input.pointer.SuspendingPointerInputModifierNodeImpl r7 = (androidx.compose.ui.input.pointer.SuspendingPointerInputModifierNodeImpl) r7
                                                long r11 = r7.boundsSize
                                                r7 = 32
                                                long r13 = r11 >> r7
                                                int r13 = (int) r13
                                                double r13 = (double) r13
                                                r15 = 4600877379321698714(0x3fd999999999999a, double:0.4)
                                                double r13 = r13 * r15
                                                int r9 = (r9 > r13 ? 1 : (r9 == r13 ? 0 : -1))
                                                if (r9 >= 0) goto L71
                                                int r8 = r8 + 1
                                                java.lang.Integer r1 = java.lang.Integer.valueOf(r8)
                                                java.lang.Integer r2 = java.lang.Integer.valueOf(r4)
                                                kotlin.Pair r6 = new kotlin.Pair
                                                r6.<init>(r1, r2)
                                                goto L93
                                            L71:
                                                long r9 = r11 >> r7
                                                int r7 = (int) r9
                                                double r9 = (double) r7
                                                r11 = 4603579539098121011(0x3fe3333333333333, double:0.6)
                                                double r9 = r9 * r11
                                                float r1 = androidx.compose.ui.geometry.Offset.m325getXimpl(r1)
                                                double r1 = (double) r1
                                                int r1 = (r9 > r1 ? 1 : (r9 == r1 ? 0 : -1))
                                                if (r1 >= 0) goto L93
                                                java.lang.Integer r1 = java.lang.Integer.valueOf(r8)
                                                int r4 = r4 + 1
                                                java.lang.Integer r2 = java.lang.Integer.valueOf(r4)
                                                kotlin.Pair r6 = new kotlin.Pair
                                                r6.<init>(r1, r2)
                                            L93:
                                                if (r6 == 0) goto L96
                                                goto La3
                                            L96:
                                                java.lang.Integer r1 = java.lang.Integer.valueOf(r5)
                                                java.lang.Integer r2 = java.lang.Integer.valueOf(r5)
                                                kotlin.Pair r6 = new kotlin.Pair
                                                r6.<init>(r1, r2)
                                            La3:
                                                r3.setValue(r6)
                                                androidx.compose.runtime.MutableState r1 = r2
                                                java.lang.Object r1 = r1.getValue()
                                                kotlin.Pair r1 = (kotlin.Pair) r1
                                                java.lang.Object r1 = r1.getFirst()
                                                java.lang.Number r1 = (java.lang.Number) r1
                                                int r1 = r1.intValue()
                                                r2 = 10
                                                if (r1 < r2) goto Le7
                                                androidx.compose.runtime.MutableState r1 = r2
                                                java.lang.Object r1 = r1.getValue()
                                                kotlin.Pair r1 = (kotlin.Pair) r1
                                                java.lang.Object r1 = r1.getSecond()
                                                java.lang.Number r1 = (java.lang.Number) r1
                                                int r1 = r1.intValue()
                                                if (r1 < r2) goto Le7
                                                androidx.compose.runtime.MutableState r1 = r2
                                                java.lang.Integer r2 = java.lang.Integer.valueOf(r5)
                                                java.lang.Integer r3 = java.lang.Integer.valueOf(r5)
                                                kotlin.Pair r4 = new kotlin.Pair
                                                r4.<init>(r2, r3)
                                                r1.setValue(r4)
                                                com.android.systemui.media.mediaoutput.viewmodel.LabsViewModel r0 = com.android.systemui.media.mediaoutput.viewmodel.LabsViewModel.this
                                                r0.setShowLabsMenu()
                                            Le7:
                                                kotlin.Unit r0 = kotlin.Unit.INSTANCE
                                                return r0
                                            */
                                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.SettingHomeKt$SettingHome$1.AnonymousClass1.C01381.invoke(java.lang.Object):java.lang.Object");
                                        }
                                    };
                                    this.label = 1;
                                    if (TapGestureDetectorKt.detectTapGestures$default(pointerInputScope, null, null, null, function1, this, 7) == coroutineSingletons) {
                                        return coroutineSingletons;
                                    }
                                } else {
                                    if (i != 1) {
                                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                    }
                                    ResultKt.throwOnFailure(obj);
                                }
                                return Unit.INSTANCE;
                            }
                        }

                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(3);
                        }

                        @Override // kotlin.jvm.functions.Function3
                        public final Object invoke(Object obj, Object obj2, Object obj3) {
                            PaddingValues paddingValues = (PaddingValues) obj;
                            Composer composer2 = (Composer) obj2;
                            int intValue = ((Number) obj3).intValue();
                            if ((intValue & 14) == 0) {
                                intValue |= ((ComposerImpl) composer2).changed(paddingValues) ? 4 : 2;
                            }
                            if ((intValue & 91) == 18) {
                                ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                                if (composerImpl2.getSkipping()) {
                                    composerImpl2.skipToGroupEnd();
                                    return Unit.INSTANCE;
                                }
                            }
                            OpaqueKey opaqueKey2 = ComposerKt.invocation;
                            LayoutDirection layoutDirection = LayoutDirection.Ltr;
                            PaddingValuesImpl m101PaddingValuesa9UjIt4$default = PaddingKt.m101PaddingValuesa9UjIt4$default(PaddingKt.calculateStartPadding(paddingValues, layoutDirection), 0.0f, PaddingKt.calculateEndPadding(paddingValues, layoutDirection), paddingValues.mo89calculateBottomPaddingD9Ej5fM(), 2);
                            final MutableState collectAsState = SnapshotStateKt.collectAsState(LabsViewModel.this.isShowLabsMenu, Boolean.FALSE, null, composer2, 56, 2);
                            ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                            composerImpl3.startReplaceGroup(1663996483);
                            Object rememberedValue = composerImpl3.rememberedValue();
                            Composer.Companion.getClass();
                            if (rememberedValue == Composer.Companion.Empty) {
                                rememberedValue = SnapshotStateKt.mutableStateOf(new Pair(0, 0), StructuralEqualityPolicy.INSTANCE);
                                composerImpl3.updateRememberedValue(rememberedValue);
                            }
                            composerImpl3.end(false);
                            Modifier.Companion companion = Modifier.Companion;
                            FillElement fillElement = SizeKt.FillWholeMaxSize;
                            companion.then(fillElement);
                            Modifier pointerInput = SuspendingPointerInputFilterKt.pointerInput(PaddingKt.m106paddingqDBjuR0$default(fillElement, 0.0f, paddingValues.mo92calculateTopPaddingD9Ej5fM(), 0.0f, 0.0f, 13), Unit.INSTANCE, new AnonymousClass1(LabsViewModel.this, (MutableState) rememberedValue, null));
                            final SettingViewModel settingViewModel4 = settingViewModel2;
                            final Function1 function12 = function1;
                            LazyDslKt.LazyColumn(pointerInput, null, m101PaddingValuesa9UjIt4$default, false, null, null, null, false, new Function1() { // from class: com.android.systemui.media.mediaoutput.compose.SettingHomeKt$SettingHome$1.2
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(1);
                                }

                                @Override // kotlin.jvm.functions.Function1
                                public final Object invoke(Object obj4) {
                                    LazyListIntervalContent lazyListIntervalContent = (LazyListIntervalContent) obj4;
                                    final SettingViewModel settingViewModel5 = SettingViewModel.this;
                                    final Function1 function13 = function12;
                                    LazyListIntervalContent.item$default(lazyListIntervalContent, new ComposableLambdaImpl(-2072891492, true, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.SettingHomeKt.SettingHome.1.2.1
                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        {
                                            super(3);
                                        }

                                        /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.media.mediaoutput.compose.SettingHomeKt$SettingHome$1$2$1$1, kotlin.jvm.internal.Lambda] */
                                        @Override // kotlin.jvm.functions.Function3
                                        public final Object invoke(Object obj5, Object obj6, Object obj7) {
                                            final boolean z;
                                            Object failure3;
                                            Composer composer3 = (Composer) obj6;
                                            if ((((Number) obj7).intValue() & 81) == 16) {
                                                ComposerImpl composerImpl4 = (ComposerImpl) composer3;
                                                if (composerImpl4.getSkipping()) {
                                                    composerImpl4.skipToGroupEnd();
                                                    return Unit.INSTANCE;
                                                }
                                            }
                                            OpaqueKey opaqueKey3 = ComposerKt.invocation;
                                            ComposerImpl composerImpl5 = (ComposerImpl) composer3;
                                            Context context = (Context) composerImpl5.consume(AndroidCompositionLocals_androidKt.LocalContext);
                                            DeviceUtils.INSTANCE.getClass();
                                            if (!((Boolean) DeviceUtils.isChinaSalesCode$delegate.getValue()).booleanValue()) {
                                                PackageManager packageManager = context.getPackageManager();
                                                try {
                                                    int i10 = Result.$r8$clinit;
                                                    packageManager.getApplicationInfo("com.samsung.android.audiomirroring", 128);
                                                    failure3 = Boolean.TRUE;
                                                } catch (Throwable th3) {
                                                    int i11 = Result.$r8$clinit;
                                                    failure3 = new Result.Failure(th3);
                                                }
                                                Throwable m2527exceptionOrNullimpl3 = Result.m2527exceptionOrNullimpl(failure3);
                                                if (m2527exceptionOrNullimpl3 != null) {
                                                    Log.e("PackageManagerExt", "isPackageInstalled() failed: " + m2527exceptionOrNullimpl3);
                                                }
                                                Boolean bool = Boolean.FALSE;
                                                if (failure3 instanceof Result.Failure) {
                                                    failure3 = bool;
                                                }
                                                if (((Boolean) failure3).booleanValue()) {
                                                    z = true;
                                                    final boolean isBluetoothCastSupported = SemBluetoothCastAdapter.isBluetoothCastSupported();
                                                    final SettingViewModel settingViewModel6 = SettingViewModel.this;
                                                    final Function1 function14 = function13;
                                                    ListsKt.ListItemContainer(ComposableLambdaKt.rememberComposableLambda(-1750842351, composerImpl5, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.SettingHomeKt.SettingHome.1.2.1.1
                                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                        {
                                                            super(3);
                                                        }

                                                        /* JADX WARN: Code restructure failed: missing block: B:14:0x0054, code lost:
                                                        
                                                            if (r2 == androidx.compose.runtime.Composer.Companion.Empty) goto L14;
                                                         */
                                                        @Override // kotlin.jvm.functions.Function3
                                                        /*
                                                            Code decompiled incorrectly, please refer to instructions dump.
                                                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                                                        */
                                                        public final java.lang.Object invoke(java.lang.Object r9, java.lang.Object r10, java.lang.Object r11) {
                                                            /*
                                                                r8 = this;
                                                                androidx.compose.foundation.layout.ColumnScope r9 = (androidx.compose.foundation.layout.ColumnScope) r9
                                                                androidx.compose.runtime.Composer r10 = (androidx.compose.runtime.Composer) r10
                                                                java.lang.Number r11 = (java.lang.Number) r11
                                                                int r9 = r11.intValue()
                                                                r9 = r9 & 81
                                                                r11 = 16
                                                                if (r9 != r11) goto L1f
                                                                r9 = r10
                                                                androidx.compose.runtime.ComposerImpl r9 = (androidx.compose.runtime.ComposerImpl) r9
                                                                boolean r0 = r9.getSkipping()
                                                                if (r0 != 0) goto L1a
                                                                goto L1f
                                                            L1a:
                                                                r9.skipToGroupEnd()
                                                                goto Le3
                                                            L1f:
                                                                androidx.compose.runtime.OpaqueKey r9 = androidx.compose.runtime.ComposerKt.invocation
                                                                r9 = r10
                                                                androidx.compose.runtime.ComposerImpl r9 = (androidx.compose.runtime.ComposerImpl) r9
                                                                r10 = 1709965881(0x65ec0239, float:1.3931494E23)
                                                                r9.startReplaceGroup(r10)
                                                                boolean r10 = r1
                                                                r7 = 0
                                                                if (r10 == 0) goto L88
                                                                com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel r10 = r2
                                                                androidx.lifecycle.CoroutineLiveData r10 = r10.isCastingPriority
                                                                java.lang.Boolean r0 = java.lang.Boolean.FALSE
                                                                androidx.compose.runtime.MutableState r10 = androidx.compose.runtime.livedata.LiveDataAdapterKt.observeAsState(r10, r0, r9)
                                                                r0 = 1709966091(0x65ec030b, float:1.3931683E23)
                                                                r9.startReplaceGroup(r0)
                                                                kotlin.jvm.functions.Function1 r0 = r3
                                                                boolean r0 = r9.changed(r0)
                                                                kotlin.jvm.functions.Function1 r1 = r3
                                                                java.lang.Object r2 = r9.rememberedValue()
                                                                if (r0 != 0) goto L56
                                                                androidx.compose.runtime.Composer$Companion r0 = androidx.compose.runtime.Composer.Companion
                                                                r0.getClass()
                                                                androidx.compose.runtime.Composer$Companion$Empty$1 r0 = androidx.compose.runtime.Composer.Companion.Empty
                                                                if (r2 != r0) goto L5e
                                                            L56:
                                                                com.android.systemui.media.mediaoutput.compose.SettingHomeKt$SettingHome$1$2$1$1$1$1 r2 = new com.android.systemui.media.mediaoutput.compose.SettingHomeKt$SettingHome$1$2$1$1$1$1
                                                                r2.<init>()
                                                                r9.updateRememberedValue(r2)
                                                            L5e:
                                                                r0 = r2
                                                                kotlin.jvm.functions.Function0 r0 = (kotlin.jvm.functions.Function0) r0
                                                                r9.end(r7)
                                                                r1 = 2131952477(0x7f13035d, float:1.9541398E38)
                                                                java.lang.String r1 = androidx.compose.ui.res.StringResources_androidKt.stringResource(r1, r9)
                                                                java.lang.Object r10 = r10.getValue()
                                                                java.lang.Boolean r10 = (java.lang.Boolean) r10
                                                                boolean r10 = r10.booleanValue()
                                                                if (r10 == 0) goto L7b
                                                                r10 = 2131952485(0x7f130365, float:1.9541414E38)
                                                                goto L7e
                                                            L7b:
                                                                r10 = 2131952071(0x7f1301c7, float:1.9540574E38)
                                                            L7e:
                                                                java.lang.String r2 = androidx.compose.ui.res.StringResources_androidKt.stringResource(r10, r9)
                                                                r4 = 0
                                                                r5 = 0
                                                                r3 = r9
                                                                com.android.systemui.media.mediaoutput.compose.widget.ListsKt.SecListItem(r0, r1, r2, r3, r4, r5)
                                                            L88:
                                                                r9.end(r7)
                                                                r10 = 1709966408(0x65ec0448, float:1.3931968E23)
                                                                r9.startReplaceGroup(r10)
                                                                boolean r10 = r1
                                                                if (r10 == 0) goto Lb1
                                                                boolean r10 = r4
                                                                if (r10 == 0) goto Lb1
                                                                androidx.compose.ui.Modifier$Companion r10 = androidx.compose.ui.Modifier.Companion
                                                                float r11 = (float) r11
                                                                androidx.compose.ui.unit.Dp$Companion r0 = androidx.compose.ui.unit.Dp.Companion
                                                                r0 = 0
                                                                r1 = 2
                                                                androidx.compose.ui.Modifier r0 = androidx.compose.foundation.layout.PaddingKt.m104paddingVpY3zN4$default(r10, r11, r0, r1)
                                                                r10 = 1
                                                                float r1 = (float) r10
                                                                long r2 = com.android.systemui.media.mediaoutput.compose.theme.ColorKt.dividerColor(r9)
                                                                r5 = 54
                                                                r6 = 0
                                                                r4 = r9
                                                                androidx.compose.material3.DividerKt.m218HorizontalDivider9IZ8Weo(r0, r1, r2, r4, r5, r6)
                                                            Lb1:
                                                                r9.end(r7)
                                                                boolean r10 = r4
                                                                if (r10 == 0) goto Le3
                                                                com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel r10 = r2
                                                                androidx.lifecycle.CoroutineLiveData r10 = r10.isShowMusicShareEnabled
                                                                r11 = 0
                                                                androidx.compose.runtime.MutableState r10 = androidx.compose.runtime.livedata.LiveDataAdapterKt.observeAsState(r10, r11, r9)
                                                                com.android.systemui.media.mediaoutput.compose.SettingHomeKt$SettingHome$1$2$1$1$2 r0 = new com.android.systemui.media.mediaoutput.compose.SettingHomeKt$SettingHome$1$2$1$1$2
                                                                com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel r8 = r2
                                                                r0.<init>()
                                                                r8 = 2131956267(0x7f13122b, float:1.9549085E38)
                                                                java.lang.String r1 = androidx.compose.ui.res.StringResources_androidKt.stringResource(r8, r9)
                                                                r8 = 2131956268(0x7f13122c, float:1.9549087E38)
                                                                java.lang.String r2 = androidx.compose.ui.res.StringResources_androidKt.stringResource(r8, r9)
                                                                java.lang.Object r8 = r10.getValue()
                                                                r3 = r8
                                                                java.lang.Boolean r3 = (java.lang.Boolean) r3
                                                                r5 = 0
                                                                r6 = 0
                                                                r4 = r9
                                                                com.android.systemui.media.mediaoutput.compose.widget.ListsKt.SecSwitchListItem(r0, r1, r2, r3, r4, r5, r6)
                                                            Le3:
                                                                kotlin.Unit r8 = kotlin.Unit.INSTANCE
                                                                return r8
                                                            */
                                                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.SettingHomeKt$SettingHome$1.AnonymousClass2.AnonymousClass1.C01391.invoke(java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
                                                        }
                                                    }), composerImpl5, 6);
                                                    OpaqueKey opaqueKey4 = ComposerKt.invocation;
                                                    return Unit.INSTANCE;
                                                }
                                            }
                                            z = false;
                                            final boolean isBluetoothCastSupported2 = SemBluetoothCastAdapter.isBluetoothCastSupported();
                                            final SettingViewModel settingViewModel62 = SettingViewModel.this;
                                            final Function1 function142 = function13;
                                            ListsKt.ListItemContainer(ComposableLambdaKt.rememberComposableLambda(-1750842351, composerImpl5, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.SettingHomeKt.SettingHome.1.2.1.1
                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                {
                                                    super(3);
                                                }

                                                @Override // kotlin.jvm.functions.Function3
                                                public final Object invoke(Object obj8, Object obj9, Object obj10) {
                                                    /*
                                                        this = this;
                                                        androidx.compose.foundation.layout.ColumnScope r9 = (androidx.compose.foundation.layout.ColumnScope) r9
                                                        androidx.compose.runtime.Composer r10 = (androidx.compose.runtime.Composer) r10
                                                        java.lang.Number r11 = (java.lang.Number) r11
                                                        int r9 = r11.intValue()
                                                        r9 = r9 & 81
                                                        r11 = 16
                                                        if (r9 != r11) goto L1f
                                                        r9 = r10
                                                        androidx.compose.runtime.ComposerImpl r9 = (androidx.compose.runtime.ComposerImpl) r9
                                                        boolean r0 = r9.getSkipping()
                                                        if (r0 != 0) goto L1a
                                                        goto L1f
                                                    L1a:
                                                        r9.skipToGroupEnd()
                                                        goto Le3
                                                    L1f:
                                                        androidx.compose.runtime.OpaqueKey r9 = androidx.compose.runtime.ComposerKt.invocation
                                                        r9 = r10
                                                        androidx.compose.runtime.ComposerImpl r9 = (androidx.compose.runtime.ComposerImpl) r9
                                                        r10 = 1709965881(0x65ec0239, float:1.3931494E23)
                                                        r9.startReplaceGroup(r10)
                                                        boolean r10 = r1
                                                        r7 = 0
                                                        if (r10 == 0) goto L88
                                                        com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel r10 = r2
                                                        androidx.lifecycle.CoroutineLiveData r10 = r10.isCastingPriority
                                                        java.lang.Boolean r0 = java.lang.Boolean.FALSE
                                                        androidx.compose.runtime.MutableState r10 = androidx.compose.runtime.livedata.LiveDataAdapterKt.observeAsState(r10, r0, r9)
                                                        r0 = 1709966091(0x65ec030b, float:1.3931683E23)
                                                        r9.startReplaceGroup(r0)
                                                        kotlin.jvm.functions.Function1 r0 = r3
                                                        boolean r0 = r9.changed(r0)
                                                        kotlin.jvm.functions.Function1 r1 = r3
                                                        java.lang.Object r2 = r9.rememberedValue()
                                                        if (r0 != 0) goto L56
                                                        androidx.compose.runtime.Composer$Companion r0 = androidx.compose.runtime.Composer.Companion
                                                        r0.getClass()
                                                        androidx.compose.runtime.Composer$Companion$Empty$1 r0 = androidx.compose.runtime.Composer.Companion.Empty
                                                        if (r2 != r0) goto L5e
                                                    L56:
                                                        com.android.systemui.media.mediaoutput.compose.SettingHomeKt$SettingHome$1$2$1$1$1$1 r2 = new com.android.systemui.media.mediaoutput.compose.SettingHomeKt$SettingHome$1$2$1$1$1$1
                                                        r2.<init>()
                                                        r9.updateRememberedValue(r2)
                                                    L5e:
                                                        r0 = r2
                                                        kotlin.jvm.functions.Function0 r0 = (kotlin.jvm.functions.Function0) r0
                                                        r9.end(r7)
                                                        r1 = 2131952477(0x7f13035d, float:1.9541398E38)
                                                        java.lang.String r1 = androidx.compose.ui.res.StringResources_androidKt.stringResource(r1, r9)
                                                        java.lang.Object r10 = r10.getValue()
                                                        java.lang.Boolean r10 = (java.lang.Boolean) r10
                                                        boolean r10 = r10.booleanValue()
                                                        if (r10 == 0) goto L7b
                                                        r10 = 2131952485(0x7f130365, float:1.9541414E38)
                                                        goto L7e
                                                    L7b:
                                                        r10 = 2131952071(0x7f1301c7, float:1.9540574E38)
                                                    L7e:
                                                        java.lang.String r2 = androidx.compose.ui.res.StringResources_androidKt.stringResource(r10, r9)
                                                        r4 = 0
                                                        r5 = 0
                                                        r3 = r9
                                                        com.android.systemui.media.mediaoutput.compose.widget.ListsKt.SecListItem(r0, r1, r2, r3, r4, r5)
                                                    L88:
                                                        r9.end(r7)
                                                        r10 = 1709966408(0x65ec0448, float:1.3931968E23)
                                                        r9.startReplaceGroup(r10)
                                                        boolean r10 = r1
                                                        if (r10 == 0) goto Lb1
                                                        boolean r10 = r4
                                                        if (r10 == 0) goto Lb1
                                                        androidx.compose.ui.Modifier$Companion r10 = androidx.compose.ui.Modifier.Companion
                                                        float r11 = (float) r11
                                                        androidx.compose.ui.unit.Dp$Companion r0 = androidx.compose.ui.unit.Dp.Companion
                                                        r0 = 0
                                                        r1 = 2
                                                        androidx.compose.ui.Modifier r0 = androidx.compose.foundation.layout.PaddingKt.m104paddingVpY3zN4$default(r10, r11, r0, r1)
                                                        r10 = 1
                                                        float r1 = (float) r10
                                                        long r2 = com.android.systemui.media.mediaoutput.compose.theme.ColorKt.dividerColor(r9)
                                                        r5 = 54
                                                        r6 = 0
                                                        r4 = r9
                                                        androidx.compose.material3.DividerKt.m218HorizontalDivider9IZ8Weo(r0, r1, r2, r4, r5, r6)
                                                    Lb1:
                                                        r9.end(r7)
                                                        boolean r10 = r4
                                                        if (r10 == 0) goto Le3
                                                        com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel r10 = r2
                                                        androidx.lifecycle.CoroutineLiveData r10 = r10.isShowMusicShareEnabled
                                                        r11 = 0
                                                        androidx.compose.runtime.MutableState r10 = androidx.compose.runtime.livedata.LiveDataAdapterKt.observeAsState(r10, r11, r9)
                                                        com.android.systemui.media.mediaoutput.compose.SettingHomeKt$SettingHome$1$2$1$1$2 r0 = new com.android.systemui.media.mediaoutput.compose.SettingHomeKt$SettingHome$1$2$1$1$2
                                                        com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel r8 = r2
                                                        r0.<init>()
                                                        r8 = 2131956267(0x7f13122b, float:1.9549085E38)
                                                        java.lang.String r1 = androidx.compose.ui.res.StringResources_androidKt.stringResource(r8, r9)
                                                        r8 = 2131956268(0x7f13122c, float:1.9549087E38)
                                                        java.lang.String r2 = androidx.compose.ui.res.StringResources_androidKt.stringResource(r8, r9)
                                                        java.lang.Object r8 = r10.getValue()
                                                        r3 = r8
                                                        java.lang.Boolean r3 = (java.lang.Boolean) r3
                                                        r5 = 0
                                                        r6 = 0
                                                        r4 = r9
                                                        com.android.systemui.media.mediaoutput.compose.widget.ListsKt.SecSwitchListItem(r0, r1, r2, r3, r4, r5, r6)
                                                    Le3:
                                                        kotlin.Unit r8 = kotlin.Unit.INSTANCE
                                                        return r8
                                                    */
                                                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.SettingHomeKt$SettingHome$1.AnonymousClass2.AnonymousClass1.C01391.invoke(java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
                                                }
                                            }), composerImpl5, 6);
                                            OpaqueKey opaqueKey42 = ComposerKt.invocation;
                                            return Unit.INSTANCE;
                                        }
                                    }));
                                    if (((Boolean) collectAsState.getValue()).booleanValue()) {
                                        final Function1 function14 = function12;
                                        LazyListIntervalContent.item$default(lazyListIntervalContent, new ComposableLambdaImpl(40539383, true, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.SettingHomeKt.SettingHome.1.2.2
                                            {
                                                super(3);
                                            }

                                            /* JADX WARN: Type inference failed for: r2v7, types: [com.android.systemui.media.mediaoutput.compose.SettingHomeKt$SettingHome$1$2$2$1, kotlin.jvm.internal.Lambda] */
                                            @Override // kotlin.jvm.functions.Function3
                                            public final Object invoke(Object obj5, Object obj6, Object obj7) {
                                                Composer composer3 = (Composer) obj6;
                                                if ((((Number) obj7).intValue() & 81) == 16) {
                                                    ComposerImpl composerImpl4 = (ComposerImpl) composer3;
                                                    if (composerImpl4.getSkipping()) {
                                                        composerImpl4.skipToGroupEnd();
                                                        return Unit.INSTANCE;
                                                    }
                                                }
                                                OpaqueKey opaqueKey3 = ComposerKt.invocation;
                                                Dp.Companion companion2 = Dp.Companion;
                                                SpacerKt.Spacer(composer3, SizeKt.m108height3ABfNKs(Modifier.Companion, 12));
                                                final Function1 function15 = Function1.this;
                                                ListsKt.ListItemContainer(ComposableLambdaKt.rememberComposableLambda(697916204, composer3, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.SettingHomeKt.SettingHome.1.2.2.1
                                                    {
                                                        super(3);
                                                    }

                                                    /* JADX WARN: Code restructure failed: missing block: B:12:0x003e, code lost:
                                                    
                                                        if (r8 == androidx.compose.runtime.Composer.Companion.Empty) goto L12;
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
                                                            r7 = r7 & 81
                                                            r9 = 16
                                                            if (r7 != r9) goto L1e
                                                            r7 = r8
                                                            androidx.compose.runtime.ComposerImpl r7 = (androidx.compose.runtime.ComposerImpl) r7
                                                            boolean r9 = r7.getSkipping()
                                                            if (r9 != 0) goto L1a
                                                            goto L1e
                                                        L1a:
                                                            r7.skipToGroupEnd()
                                                            goto L58
                                                        L1e:
                                                            androidx.compose.runtime.OpaqueKey r7 = androidx.compose.runtime.ComposerKt.invocation
                                                            r3 = r8
                                                            androidx.compose.runtime.ComposerImpl r3 = (androidx.compose.runtime.ComposerImpl) r3
                                                            r7 = 1709967444(0x65ec0854, float:1.39329015E23)
                                                            r3.startReplaceGroup(r7)
                                                            kotlin.jvm.functions.Function1 r7 = kotlin.jvm.functions.Function1.this
                                                            boolean r7 = r3.changed(r7)
                                                            kotlin.jvm.functions.Function1 r6 = kotlin.jvm.functions.Function1.this
                                                            java.lang.Object r8 = r3.rememberedValue()
                                                            if (r7 != 0) goto L40
                                                            androidx.compose.runtime.Composer$Companion r7 = androidx.compose.runtime.Composer.Companion
                                                            r7.getClass()
                                                            androidx.compose.runtime.Composer$Companion$Empty$1 r7 = androidx.compose.runtime.Composer.Companion.Empty
                                                            if (r8 != r7) goto L48
                                                        L40:
                                                            com.android.systemui.media.mediaoutput.compose.SettingHomeKt$SettingHome$1$2$2$1$1$1 r8 = new com.android.systemui.media.mediaoutput.compose.SettingHomeKt$SettingHome$1$2$2$1$1$1
                                                            r8.<init>()
                                                            r3.updateRememberedValue(r8)
                                                        L48:
                                                            r0 = r8
                                                            kotlin.jvm.functions.Function0 r0 = (kotlin.jvm.functions.Function0) r0
                                                            r6 = 0
                                                            r3.end(r6)
                                                            r4 = 48
                                                            r5 = 4
                                                            java.lang.String r1 = "Labs"
                                                            r2 = 0
                                                            com.android.systemui.media.mediaoutput.compose.widget.ListsKt.SecListItem(r0, r1, r2, r3, r4, r5)
                                                        L58:
                                                            kotlin.Unit r6 = kotlin.Unit.INSTANCE
                                                            return r6
                                                        */
                                                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.SettingHomeKt$SettingHome$1.AnonymousClass2.C01412.AnonymousClass1.invoke(java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
                                                    }
                                                }), composer3, 6);
                                                return Unit.INSTANCE;
                                            }
                                        }));
                                    }
                                    return Unit.INSTANCE;
                                }
                            }, composerImpl3, 0, IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend);
                            return Unit.INSTANCE;
                        }
                    }), composerImpl, (i3 & 14) | 384, 0);
                    labsViewModel3 = labsViewModel2;
                    settingViewModel3 = settingViewModel2;
                }
            } else {
                composerImpl.skipToGroupEnd();
                if (i4 != 0) {
                    i3 &= -897;
                }
                if (i5 != 0) {
                    i3 &= -7169;
                }
                settingViewModel2 = settingViewModel;
            }
            labsViewModel2 = labsViewModel;
            composerImpl.endDefaults();
            OpaqueKey opaqueKey2 = ComposerKt.invocation;
            ActionBarKt.SecTitle(function0, StringResources_androidKt.stringResource(R.string.settings, new Object[]{StringResources_androidKt.stringResource(R.string.media_output, composerImpl)}, composerImpl), ComposableLambdaKt.rememberComposableLambda(-370486008, composerImpl, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.SettingHomeKt$SettingHome$1

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.media.mediaoutput.compose.SettingHomeKt$SettingHome$1$1, reason: invalid class name */
                final class AnonymousClass1 extends SuspendLambda implements Function2 {
                    final /* synthetic */ LabsViewModel $labsViewModel;
                    final /* synthetic */ MutableState $tapCount$delegate;
                    private /* synthetic */ Object L$0;
                    int label;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    public AnonymousClass1(LabsViewModel labsViewModel, MutableState mutableState, Continuation continuation) {
                        super(2, continuation);
                        this.$labsViewModel = labsViewModel;
                        this.$tapCount$delegate = mutableState;
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Continuation create(Object obj, Continuation continuation) {
                        AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$labsViewModel, this.$tapCount$delegate, continuation);
                        anonymousClass1.L$0 = obj;
                        return anonymousClass1;
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        return ((AnonymousClass1) create((PointerInputScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                        int i = this.label;
                        if (i == 0) {
                            ResultKt.throwOnFailure(obj);
                            final PointerInputScope pointerInputScope = (PointerInputScope) this.L$0;
                            final LabsViewModel labsViewModel = this.$labsViewModel;
                            final MutableState mutableState = this.$tapCount$delegate;
                            Function1 function1 = new Function1() { // from class: com.android.systemui.media.mediaoutput.compose.SettingHomeKt.SettingHome.1.1.1
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(1);
                                }

                                @Override // kotlin.jvm.functions.Function1
                                public final Object invoke(Object obj2) {
                                    /*
                                        this = this;
                                        r0 = r17
                                        r1 = r18
                                        androidx.compose.ui.geometry.Offset r1 = (androidx.compose.ui.geometry.Offset) r1
                                        long r1 = r1.packedValue
                                        androidx.compose.runtime.MutableState r3 = r2
                                        java.lang.Object r4 = r3.getValue()
                                        kotlin.Pair r4 = (kotlin.Pair) r4
                                        androidx.compose.ui.input.pointer.PointerInputScope r5 = r3
                                        float r6 = androidx.compose.ui.geometry.Offset.m326getYimpl(r1)
                                        double r6 = (double) r6
                                        androidx.compose.ui.input.pointer.SuspendingPointerInputModifierNodeImpl r5 = (androidx.compose.ui.input.pointer.SuspendingPointerInputModifierNodeImpl) r5
                                        long r8 = r5.boundsSize
                                        androidx.compose.ui.unit.IntSize$Companion r5 = androidx.compose.ui.unit.IntSize.Companion
                                        r10 = 4294967295(0xffffffff, double:2.1219957905E-314)
                                        long r8 = r8 & r10
                                        int r5 = (int) r8
                                        double r8 = (double) r5
                                        r10 = 4604930618986332160(0x3fe8000000000000, double:0.75)
                                        double r8 = r8 * r10
                                        int r5 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
                                        r6 = 0
                                        if (r5 <= 0) goto L2e
                                        goto L2f
                                    L2e:
                                        r4 = r6
                                    L2f:
                                        r5 = 0
                                        if (r4 == 0) goto L96
                                        androidx.compose.ui.input.pointer.PointerInputScope r7 = r3
                                        java.lang.Object r8 = r4.component1()
                                        java.lang.Number r8 = (java.lang.Number) r8
                                        int r8 = r8.intValue()
                                        java.lang.Object r4 = r4.component2()
                                        java.lang.Number r4 = (java.lang.Number) r4
                                        int r4 = r4.intValue()
                                        float r9 = androidx.compose.ui.geometry.Offset.m325getXimpl(r1)
                                        double r9 = (double) r9
                                        androidx.compose.ui.input.pointer.SuspendingPointerInputModifierNodeImpl r7 = (androidx.compose.ui.input.pointer.SuspendingPointerInputModifierNodeImpl) r7
                                        long r11 = r7.boundsSize
                                        r7 = 32
                                        long r13 = r11 >> r7
                                        int r13 = (int) r13
                                        double r13 = (double) r13
                                        r15 = 4600877379321698714(0x3fd999999999999a, double:0.4)
                                        double r13 = r13 * r15
                                        int r9 = (r9 > r13 ? 1 : (r9 == r13 ? 0 : -1))
                                        if (r9 >= 0) goto L71
                                        int r8 = r8 + 1
                                        java.lang.Integer r1 = java.lang.Integer.valueOf(r8)
                                        java.lang.Integer r2 = java.lang.Integer.valueOf(r4)
                                        kotlin.Pair r6 = new kotlin.Pair
                                        r6.<init>(r1, r2)
                                        goto L93
                                    L71:
                                        long r9 = r11 >> r7
                                        int r7 = (int) r9
                                        double r9 = (double) r7
                                        r11 = 4603579539098121011(0x3fe3333333333333, double:0.6)
                                        double r9 = r9 * r11
                                        float r1 = androidx.compose.ui.geometry.Offset.m325getXimpl(r1)
                                        double r1 = (double) r1
                                        int r1 = (r9 > r1 ? 1 : (r9 == r1 ? 0 : -1))
                                        if (r1 >= 0) goto L93
                                        java.lang.Integer r1 = java.lang.Integer.valueOf(r8)
                                        int r4 = r4 + 1
                                        java.lang.Integer r2 = java.lang.Integer.valueOf(r4)
                                        kotlin.Pair r6 = new kotlin.Pair
                                        r6.<init>(r1, r2)
                                    L93:
                                        if (r6 == 0) goto L96
                                        goto La3
                                    L96:
                                        java.lang.Integer r1 = java.lang.Integer.valueOf(r5)
                                        java.lang.Integer r2 = java.lang.Integer.valueOf(r5)
                                        kotlin.Pair r6 = new kotlin.Pair
                                        r6.<init>(r1, r2)
                                    La3:
                                        r3.setValue(r6)
                                        androidx.compose.runtime.MutableState r1 = r2
                                        java.lang.Object r1 = r1.getValue()
                                        kotlin.Pair r1 = (kotlin.Pair) r1
                                        java.lang.Object r1 = r1.getFirst()
                                        java.lang.Number r1 = (java.lang.Number) r1
                                        int r1 = r1.intValue()
                                        r2 = 10
                                        if (r1 < r2) goto Le7
                                        androidx.compose.runtime.MutableState r1 = r2
                                        java.lang.Object r1 = r1.getValue()
                                        kotlin.Pair r1 = (kotlin.Pair) r1
                                        java.lang.Object r1 = r1.getSecond()
                                        java.lang.Number r1 = (java.lang.Number) r1
                                        int r1 = r1.intValue()
                                        if (r1 < r2) goto Le7
                                        androidx.compose.runtime.MutableState r1 = r2
                                        java.lang.Integer r2 = java.lang.Integer.valueOf(r5)
                                        java.lang.Integer r3 = java.lang.Integer.valueOf(r5)
                                        kotlin.Pair r4 = new kotlin.Pair
                                        r4.<init>(r2, r3)
                                        r1.setValue(r4)
                                        com.android.systemui.media.mediaoutput.viewmodel.LabsViewModel r0 = com.android.systemui.media.mediaoutput.viewmodel.LabsViewModel.this
                                        r0.setShowLabsMenu()
                                    Le7:
                                        kotlin.Unit r0 = kotlin.Unit.INSTANCE
                                        return r0
                                    */
                                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.SettingHomeKt$SettingHome$1.AnonymousClass1.C01381.invoke(java.lang.Object):java.lang.Object");
                                }
                            };
                            this.label = 1;
                            if (TapGestureDetectorKt.detectTapGestures$default(pointerInputScope, null, null, null, function1, this, 7) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                        } else {
                            if (i != 1) {
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            }
                            ResultKt.throwOnFailure(obj);
                        }
                        return Unit.INSTANCE;
                    }
                }

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(3);
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    PaddingValues paddingValues = (PaddingValues) obj;
                    Composer composer2 = (Composer) obj2;
                    int intValue = ((Number) obj3).intValue();
                    if ((intValue & 14) == 0) {
                        intValue |= ((ComposerImpl) composer2).changed(paddingValues) ? 4 : 2;
                    }
                    if ((intValue & 91) == 18) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                            return Unit.INSTANCE;
                        }
                    }
                    OpaqueKey opaqueKey22 = ComposerKt.invocation;
                    LayoutDirection layoutDirection = LayoutDirection.Ltr;
                    PaddingValuesImpl m101PaddingValuesa9UjIt4$default = PaddingKt.m101PaddingValuesa9UjIt4$default(PaddingKt.calculateStartPadding(paddingValues, layoutDirection), 0.0f, PaddingKt.calculateEndPadding(paddingValues, layoutDirection), paddingValues.mo89calculateBottomPaddingD9Ej5fM(), 2);
                    final State collectAsState = SnapshotStateKt.collectAsState(LabsViewModel.this.isShowLabsMenu, Boolean.FALSE, null, composer2, 56, 2);
                    ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                    composerImpl3.startReplaceGroup(1663996483);
                    Object rememberedValue = composerImpl3.rememberedValue();
                    Composer.Companion.getClass();
                    if (rememberedValue == Composer.Companion.Empty) {
                        rememberedValue = SnapshotStateKt.mutableStateOf(new Pair(0, 0), StructuralEqualityPolicy.INSTANCE);
                        composerImpl3.updateRememberedValue(rememberedValue);
                    }
                    composerImpl3.end(false);
                    Modifier.Companion companion = Modifier.Companion;
                    FillElement fillElement = SizeKt.FillWholeMaxSize;
                    companion.then(fillElement);
                    Modifier pointerInput = SuspendingPointerInputFilterKt.pointerInput(PaddingKt.m106paddingqDBjuR0$default(fillElement, 0.0f, paddingValues.mo92calculateTopPaddingD9Ej5fM(), 0.0f, 0.0f, 13), Unit.INSTANCE, new AnonymousClass1(LabsViewModel.this, (MutableState) rememberedValue, null));
                    final SettingViewModel settingViewModel4 = settingViewModel2;
                    final Function1 function12 = function1;
                    LazyDslKt.LazyColumn(pointerInput, null, m101PaddingValuesa9UjIt4$default, false, null, null, null, false, new Function1() { // from class: com.android.systemui.media.mediaoutput.compose.SettingHomeKt$SettingHome$1.2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj4) {
                            LazyListIntervalContent lazyListIntervalContent = (LazyListIntervalContent) obj4;
                            final SettingViewModel settingViewModel5 = SettingViewModel.this;
                            final Function1 function13 = function12;
                            LazyListIntervalContent.item$default(lazyListIntervalContent, new ComposableLambdaImpl(-2072891492, true, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.SettingHomeKt.SettingHome.1.2.1
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(3);
                                }

                                /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.media.mediaoutput.compose.SettingHomeKt$SettingHome$1$2$1$1, kotlin.jvm.internal.Lambda] */
                                @Override // kotlin.jvm.functions.Function3
                                public final Object invoke(Object obj5, Object obj6, Object obj7) {
                                    final boolean z;
                                    Object failure3;
                                    Composer composer3 = (Composer) obj6;
                                    if ((((Number) obj7).intValue() & 81) == 16) {
                                        ComposerImpl composerImpl4 = (ComposerImpl) composer3;
                                        if (composerImpl4.getSkipping()) {
                                            composerImpl4.skipToGroupEnd();
                                            return Unit.INSTANCE;
                                        }
                                    }
                                    OpaqueKey opaqueKey3 = ComposerKt.invocation;
                                    ComposerImpl composerImpl5 = (ComposerImpl) composer3;
                                    Context context = (Context) composerImpl5.consume(AndroidCompositionLocals_androidKt.LocalContext);
                                    DeviceUtils.INSTANCE.getClass();
                                    if (!((Boolean) DeviceUtils.isChinaSalesCode$delegate.getValue()).booleanValue()) {
                                        PackageManager packageManager = context.getPackageManager();
                                        try {
                                            int i10 = Result.$r8$clinit;
                                            packageManager.getApplicationInfo("com.samsung.android.audiomirroring", 128);
                                            failure3 = Boolean.TRUE;
                                        } catch (Throwable th3) {
                                            int i11 = Result.$r8$clinit;
                                            failure3 = new Result.Failure(th3);
                                        }
                                        Throwable m2527exceptionOrNullimpl3 = Result.m2527exceptionOrNullimpl(failure3);
                                        if (m2527exceptionOrNullimpl3 != null) {
                                            Log.e("PackageManagerExt", "isPackageInstalled() failed: " + m2527exceptionOrNullimpl3);
                                        }
                                        Boolean bool = Boolean.FALSE;
                                        if (failure3 instanceof Result.Failure) {
                                            failure3 = bool;
                                        }
                                        if (((Boolean) failure3).booleanValue()) {
                                            z = true;
                                            final boolean isBluetoothCastSupported2 = SemBluetoothCastAdapter.isBluetoothCastSupported();
                                            final SettingViewModel settingViewModel62 = SettingViewModel.this;
                                            final Function1 function142 = function13;
                                            ListsKt.ListItemContainer(ComposableLambdaKt.rememberComposableLambda(-1750842351, composerImpl5, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.SettingHomeKt.SettingHome.1.2.1.1
                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                {
                                                    super(3);
                                                }

                                                @Override // kotlin.jvm.functions.Function3
                                                public final Object invoke(Object obj8, Object obj9, Object obj10) {
                                                    /*
                                                        this = this;
                                                        androidx.compose.foundation.layout.ColumnScope r9 = (androidx.compose.foundation.layout.ColumnScope) r9
                                                        androidx.compose.runtime.Composer r10 = (androidx.compose.runtime.Composer) r10
                                                        java.lang.Number r11 = (java.lang.Number) r11
                                                        int r9 = r11.intValue()
                                                        r9 = r9 & 81
                                                        r11 = 16
                                                        if (r9 != r11) goto L1f
                                                        r9 = r10
                                                        androidx.compose.runtime.ComposerImpl r9 = (androidx.compose.runtime.ComposerImpl) r9
                                                        boolean r0 = r9.getSkipping()
                                                        if (r0 != 0) goto L1a
                                                        goto L1f
                                                    L1a:
                                                        r9.skipToGroupEnd()
                                                        goto Le3
                                                    L1f:
                                                        androidx.compose.runtime.OpaqueKey r9 = androidx.compose.runtime.ComposerKt.invocation
                                                        r9 = r10
                                                        androidx.compose.runtime.ComposerImpl r9 = (androidx.compose.runtime.ComposerImpl) r9
                                                        r10 = 1709965881(0x65ec0239, float:1.3931494E23)
                                                        r9.startReplaceGroup(r10)
                                                        boolean r10 = r1
                                                        r7 = 0
                                                        if (r10 == 0) goto L88
                                                        com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel r10 = r2
                                                        androidx.lifecycle.CoroutineLiveData r10 = r10.isCastingPriority
                                                        java.lang.Boolean r0 = java.lang.Boolean.FALSE
                                                        androidx.compose.runtime.MutableState r10 = androidx.compose.runtime.livedata.LiveDataAdapterKt.observeAsState(r10, r0, r9)
                                                        r0 = 1709966091(0x65ec030b, float:1.3931683E23)
                                                        r9.startReplaceGroup(r0)
                                                        kotlin.jvm.functions.Function1 r0 = r3
                                                        boolean r0 = r9.changed(r0)
                                                        kotlin.jvm.functions.Function1 r1 = r3
                                                        java.lang.Object r2 = r9.rememberedValue()
                                                        if (r0 != 0) goto L56
                                                        androidx.compose.runtime.Composer$Companion r0 = androidx.compose.runtime.Composer.Companion
                                                        r0.getClass()
                                                        androidx.compose.runtime.Composer$Companion$Empty$1 r0 = androidx.compose.runtime.Composer.Companion.Empty
                                                        if (r2 != r0) goto L5e
                                                    L56:
                                                        com.android.systemui.media.mediaoutput.compose.SettingHomeKt$SettingHome$1$2$1$1$1$1 r2 = new com.android.systemui.media.mediaoutput.compose.SettingHomeKt$SettingHome$1$2$1$1$1$1
                                                        r2.<init>()
                                                        r9.updateRememberedValue(r2)
                                                    L5e:
                                                        r0 = r2
                                                        kotlin.jvm.functions.Function0 r0 = (kotlin.jvm.functions.Function0) r0
                                                        r9.end(r7)
                                                        r1 = 2131952477(0x7f13035d, float:1.9541398E38)
                                                        java.lang.String r1 = androidx.compose.ui.res.StringResources_androidKt.stringResource(r1, r9)
                                                        java.lang.Object r10 = r10.getValue()
                                                        java.lang.Boolean r10 = (java.lang.Boolean) r10
                                                        boolean r10 = r10.booleanValue()
                                                        if (r10 == 0) goto L7b
                                                        r10 = 2131952485(0x7f130365, float:1.9541414E38)
                                                        goto L7e
                                                    L7b:
                                                        r10 = 2131952071(0x7f1301c7, float:1.9540574E38)
                                                    L7e:
                                                        java.lang.String r2 = androidx.compose.ui.res.StringResources_androidKt.stringResource(r10, r9)
                                                        r4 = 0
                                                        r5 = 0
                                                        r3 = r9
                                                        com.android.systemui.media.mediaoutput.compose.widget.ListsKt.SecListItem(r0, r1, r2, r3, r4, r5)
                                                    L88:
                                                        r9.end(r7)
                                                        r10 = 1709966408(0x65ec0448, float:1.3931968E23)
                                                        r9.startReplaceGroup(r10)
                                                        boolean r10 = r1
                                                        if (r10 == 0) goto Lb1
                                                        boolean r10 = r4
                                                        if (r10 == 0) goto Lb1
                                                        androidx.compose.ui.Modifier$Companion r10 = androidx.compose.ui.Modifier.Companion
                                                        float r11 = (float) r11
                                                        androidx.compose.ui.unit.Dp$Companion r0 = androidx.compose.ui.unit.Dp.Companion
                                                        r0 = 0
                                                        r1 = 2
                                                        androidx.compose.ui.Modifier r0 = androidx.compose.foundation.layout.PaddingKt.m104paddingVpY3zN4$default(r10, r11, r0, r1)
                                                        r10 = 1
                                                        float r1 = (float) r10
                                                        long r2 = com.android.systemui.media.mediaoutput.compose.theme.ColorKt.dividerColor(r9)
                                                        r5 = 54
                                                        r6 = 0
                                                        r4 = r9
                                                        androidx.compose.material3.DividerKt.m218HorizontalDivider9IZ8Weo(r0, r1, r2, r4, r5, r6)
                                                    Lb1:
                                                        r9.end(r7)
                                                        boolean r10 = r4
                                                        if (r10 == 0) goto Le3
                                                        com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel r10 = r2
                                                        androidx.lifecycle.CoroutineLiveData r10 = r10.isShowMusicShareEnabled
                                                        r11 = 0
                                                        androidx.compose.runtime.MutableState r10 = androidx.compose.runtime.livedata.LiveDataAdapterKt.observeAsState(r10, r11, r9)
                                                        com.android.systemui.media.mediaoutput.compose.SettingHomeKt$SettingHome$1$2$1$1$2 r0 = new com.android.systemui.media.mediaoutput.compose.SettingHomeKt$SettingHome$1$2$1$1$2
                                                        com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel r8 = r2
                                                        r0.<init>()
                                                        r8 = 2131956267(0x7f13122b, float:1.9549085E38)
                                                        java.lang.String r1 = androidx.compose.ui.res.StringResources_androidKt.stringResource(r8, r9)
                                                        r8 = 2131956268(0x7f13122c, float:1.9549087E38)
                                                        java.lang.String r2 = androidx.compose.ui.res.StringResources_androidKt.stringResource(r8, r9)
                                                        java.lang.Object r8 = r10.getValue()
                                                        r3 = r8
                                                        java.lang.Boolean r3 = (java.lang.Boolean) r3
                                                        r5 = 0
                                                        r6 = 0
                                                        r4 = r9
                                                        com.android.systemui.media.mediaoutput.compose.widget.ListsKt.SecSwitchListItem(r0, r1, r2, r3, r4, r5, r6)
                                                    Le3:
                                                        kotlin.Unit r8 = kotlin.Unit.INSTANCE
                                                        return r8
                                                    */
                                                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.SettingHomeKt$SettingHome$1.AnonymousClass2.AnonymousClass1.C01391.invoke(java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
                                                }
                                            }), composerImpl5, 6);
                                            OpaqueKey opaqueKey42 = ComposerKt.invocation;
                                            return Unit.INSTANCE;
                                        }
                                    }
                                    z = false;
                                    final boolean isBluetoothCastSupported22 = SemBluetoothCastAdapter.isBluetoothCastSupported();
                                    final SettingViewModel settingViewModel622 = SettingViewModel.this;
                                    final Function1 function1422 = function13;
                                    ListsKt.ListItemContainer(ComposableLambdaKt.rememberComposableLambda(-1750842351, composerImpl5, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.SettingHomeKt.SettingHome.1.2.1.1
                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        {
                                            super(3);
                                        }

                                        @Override // kotlin.jvm.functions.Function3
                                        public final Object invoke(Object obj8, Object obj9, Object obj10) {
                                            /*
                                                this = this;
                                                androidx.compose.foundation.layout.ColumnScope r9 = (androidx.compose.foundation.layout.ColumnScope) r9
                                                androidx.compose.runtime.Composer r10 = (androidx.compose.runtime.Composer) r10
                                                java.lang.Number r11 = (java.lang.Number) r11
                                                int r9 = r11.intValue()
                                                r9 = r9 & 81
                                                r11 = 16
                                                if (r9 != r11) goto L1f
                                                r9 = r10
                                                androidx.compose.runtime.ComposerImpl r9 = (androidx.compose.runtime.ComposerImpl) r9
                                                boolean r0 = r9.getSkipping()
                                                if (r0 != 0) goto L1a
                                                goto L1f
                                            L1a:
                                                r9.skipToGroupEnd()
                                                goto Le3
                                            L1f:
                                                androidx.compose.runtime.OpaqueKey r9 = androidx.compose.runtime.ComposerKt.invocation
                                                r9 = r10
                                                androidx.compose.runtime.ComposerImpl r9 = (androidx.compose.runtime.ComposerImpl) r9
                                                r10 = 1709965881(0x65ec0239, float:1.3931494E23)
                                                r9.startReplaceGroup(r10)
                                                boolean r10 = r1
                                                r7 = 0
                                                if (r10 == 0) goto L88
                                                com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel r10 = r2
                                                androidx.lifecycle.CoroutineLiveData r10 = r10.isCastingPriority
                                                java.lang.Boolean r0 = java.lang.Boolean.FALSE
                                                androidx.compose.runtime.MutableState r10 = androidx.compose.runtime.livedata.LiveDataAdapterKt.observeAsState(r10, r0, r9)
                                                r0 = 1709966091(0x65ec030b, float:1.3931683E23)
                                                r9.startReplaceGroup(r0)
                                                kotlin.jvm.functions.Function1 r0 = r3
                                                boolean r0 = r9.changed(r0)
                                                kotlin.jvm.functions.Function1 r1 = r3
                                                java.lang.Object r2 = r9.rememberedValue()
                                                if (r0 != 0) goto L56
                                                androidx.compose.runtime.Composer$Companion r0 = androidx.compose.runtime.Composer.Companion
                                                r0.getClass()
                                                androidx.compose.runtime.Composer$Companion$Empty$1 r0 = androidx.compose.runtime.Composer.Companion.Empty
                                                if (r2 != r0) goto L5e
                                            L56:
                                                com.android.systemui.media.mediaoutput.compose.SettingHomeKt$SettingHome$1$2$1$1$1$1 r2 = new com.android.systemui.media.mediaoutput.compose.SettingHomeKt$SettingHome$1$2$1$1$1$1
                                                r2.<init>()
                                                r9.updateRememberedValue(r2)
                                            L5e:
                                                r0 = r2
                                                kotlin.jvm.functions.Function0 r0 = (kotlin.jvm.functions.Function0) r0
                                                r9.end(r7)
                                                r1 = 2131952477(0x7f13035d, float:1.9541398E38)
                                                java.lang.String r1 = androidx.compose.ui.res.StringResources_androidKt.stringResource(r1, r9)
                                                java.lang.Object r10 = r10.getValue()
                                                java.lang.Boolean r10 = (java.lang.Boolean) r10
                                                boolean r10 = r10.booleanValue()
                                                if (r10 == 0) goto L7b
                                                r10 = 2131952485(0x7f130365, float:1.9541414E38)
                                                goto L7e
                                            L7b:
                                                r10 = 2131952071(0x7f1301c7, float:1.9540574E38)
                                            L7e:
                                                java.lang.String r2 = androidx.compose.ui.res.StringResources_androidKt.stringResource(r10, r9)
                                                r4 = 0
                                                r5 = 0
                                                r3 = r9
                                                com.android.systemui.media.mediaoutput.compose.widget.ListsKt.SecListItem(r0, r1, r2, r3, r4, r5)
                                            L88:
                                                r9.end(r7)
                                                r10 = 1709966408(0x65ec0448, float:1.3931968E23)
                                                r9.startReplaceGroup(r10)
                                                boolean r10 = r1
                                                if (r10 == 0) goto Lb1
                                                boolean r10 = r4
                                                if (r10 == 0) goto Lb1
                                                androidx.compose.ui.Modifier$Companion r10 = androidx.compose.ui.Modifier.Companion
                                                float r11 = (float) r11
                                                androidx.compose.ui.unit.Dp$Companion r0 = androidx.compose.ui.unit.Dp.Companion
                                                r0 = 0
                                                r1 = 2
                                                androidx.compose.ui.Modifier r0 = androidx.compose.foundation.layout.PaddingKt.m104paddingVpY3zN4$default(r10, r11, r0, r1)
                                                r10 = 1
                                                float r1 = (float) r10
                                                long r2 = com.android.systemui.media.mediaoutput.compose.theme.ColorKt.dividerColor(r9)
                                                r5 = 54
                                                r6 = 0
                                                r4 = r9
                                                androidx.compose.material3.DividerKt.m218HorizontalDivider9IZ8Weo(r0, r1, r2, r4, r5, r6)
                                            Lb1:
                                                r9.end(r7)
                                                boolean r10 = r4
                                                if (r10 == 0) goto Le3
                                                com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel r10 = r2
                                                androidx.lifecycle.CoroutineLiveData r10 = r10.isShowMusicShareEnabled
                                                r11 = 0
                                                androidx.compose.runtime.MutableState r10 = androidx.compose.runtime.livedata.LiveDataAdapterKt.observeAsState(r10, r11, r9)
                                                com.android.systemui.media.mediaoutput.compose.SettingHomeKt$SettingHome$1$2$1$1$2 r0 = new com.android.systemui.media.mediaoutput.compose.SettingHomeKt$SettingHome$1$2$1$1$2
                                                com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel r8 = r2
                                                r0.<init>()
                                                r8 = 2131956267(0x7f13122b, float:1.9549085E38)
                                                java.lang.String r1 = androidx.compose.ui.res.StringResources_androidKt.stringResource(r8, r9)
                                                r8 = 2131956268(0x7f13122c, float:1.9549087E38)
                                                java.lang.String r2 = androidx.compose.ui.res.StringResources_androidKt.stringResource(r8, r9)
                                                java.lang.Object r8 = r10.getValue()
                                                r3 = r8
                                                java.lang.Boolean r3 = (java.lang.Boolean) r3
                                                r5 = 0
                                                r6 = 0
                                                r4 = r9
                                                com.android.systemui.media.mediaoutput.compose.widget.ListsKt.SecSwitchListItem(r0, r1, r2, r3, r4, r5, r6)
                                            Le3:
                                                kotlin.Unit r8 = kotlin.Unit.INSTANCE
                                                return r8
                                            */
                                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.SettingHomeKt$SettingHome$1.AnonymousClass2.AnonymousClass1.C01391.invoke(java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
                                        }
                                    }), composerImpl5, 6);
                                    OpaqueKey opaqueKey422 = ComposerKt.invocation;
                                    return Unit.INSTANCE;
                                }
                            }));
                            if (((Boolean) collectAsState.getValue()).booleanValue()) {
                                final Function1 function14 = function12;
                                LazyListIntervalContent.item$default(lazyListIntervalContent, new ComposableLambdaImpl(40539383, true, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.SettingHomeKt.SettingHome.1.2.2
                                    {
                                        super(3);
                                    }

                                    /* JADX WARN: Type inference failed for: r2v7, types: [com.android.systemui.media.mediaoutput.compose.SettingHomeKt$SettingHome$1$2$2$1, kotlin.jvm.internal.Lambda] */
                                    @Override // kotlin.jvm.functions.Function3
                                    public final Object invoke(Object obj5, Object obj6, Object obj7) {
                                        Composer composer3 = (Composer) obj6;
                                        if ((((Number) obj7).intValue() & 81) == 16) {
                                            ComposerImpl composerImpl4 = (ComposerImpl) composer3;
                                            if (composerImpl4.getSkipping()) {
                                                composerImpl4.skipToGroupEnd();
                                                return Unit.INSTANCE;
                                            }
                                        }
                                        OpaqueKey opaqueKey3 = ComposerKt.invocation;
                                        Dp.Companion companion2 = Dp.Companion;
                                        SpacerKt.Spacer(composer3, SizeKt.m108height3ABfNKs(Modifier.Companion, 12));
                                        final Function1 function15 = Function1.this;
                                        ListsKt.ListItemContainer(ComposableLambdaKt.rememberComposableLambda(697916204, composer3, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.SettingHomeKt.SettingHome.1.2.2.1
                                            {
                                                super(3);
                                            }

                                            @Override // kotlin.jvm.functions.Function3
                                            public final Object invoke(Object obj8, Object obj9, Object obj10) {
                                                /*
                                                    this = this;
                                                    androidx.compose.foundation.layout.ColumnScope r7 = (androidx.compose.foundation.layout.ColumnScope) r7
                                                    androidx.compose.runtime.Composer r8 = (androidx.compose.runtime.Composer) r8
                                                    java.lang.Number r9 = (java.lang.Number) r9
                                                    int r7 = r9.intValue()
                                                    r7 = r7 & 81
                                                    r9 = 16
                                                    if (r7 != r9) goto L1e
                                                    r7 = r8
                                                    androidx.compose.runtime.ComposerImpl r7 = (androidx.compose.runtime.ComposerImpl) r7
                                                    boolean r9 = r7.getSkipping()
                                                    if (r9 != 0) goto L1a
                                                    goto L1e
                                                L1a:
                                                    r7.skipToGroupEnd()
                                                    goto L58
                                                L1e:
                                                    androidx.compose.runtime.OpaqueKey r7 = androidx.compose.runtime.ComposerKt.invocation
                                                    r3 = r8
                                                    androidx.compose.runtime.ComposerImpl r3 = (androidx.compose.runtime.ComposerImpl) r3
                                                    r7 = 1709967444(0x65ec0854, float:1.39329015E23)
                                                    r3.startReplaceGroup(r7)
                                                    kotlin.jvm.functions.Function1 r7 = kotlin.jvm.functions.Function1.this
                                                    boolean r7 = r3.changed(r7)
                                                    kotlin.jvm.functions.Function1 r6 = kotlin.jvm.functions.Function1.this
                                                    java.lang.Object r8 = r3.rememberedValue()
                                                    if (r7 != 0) goto L40
                                                    androidx.compose.runtime.Composer$Companion r7 = androidx.compose.runtime.Composer.Companion
                                                    r7.getClass()
                                                    androidx.compose.runtime.Composer$Companion$Empty$1 r7 = androidx.compose.runtime.Composer.Companion.Empty
                                                    if (r8 != r7) goto L48
                                                L40:
                                                    com.android.systemui.media.mediaoutput.compose.SettingHomeKt$SettingHome$1$2$2$1$1$1 r8 = new com.android.systemui.media.mediaoutput.compose.SettingHomeKt$SettingHome$1$2$2$1$1$1
                                                    r8.<init>()
                                                    r3.updateRememberedValue(r8)
                                                L48:
                                                    r0 = r8
                                                    kotlin.jvm.functions.Function0 r0 = (kotlin.jvm.functions.Function0) r0
                                                    r6 = 0
                                                    r3.end(r6)
                                                    r4 = 48
                                                    r5 = 4
                                                    java.lang.String r1 = "Labs"
                                                    r2 = 0
                                                    com.android.systemui.media.mediaoutput.compose.widget.ListsKt.SecListItem(r0, r1, r2, r3, r4, r5)
                                                L58:
                                                    kotlin.Unit r6 = kotlin.Unit.INSTANCE
                                                    return r6
                                                */
                                                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.SettingHomeKt$SettingHome$1.AnonymousClass2.C01412.AnonymousClass1.invoke(java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
                                            }
                                        }), composer3, 6);
                                        return Unit.INSTANCE;
                                    }
                                }));
                            }
                            return Unit.INSTANCE;
                        }
                    }, composerImpl3, 0, IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend);
                    return Unit.INSTANCE;
                }
            }), composerImpl, (i3 & 14) | 384, 0);
            labsViewModel3 = labsViewModel2;
            settingViewModel3 = settingViewModel2;
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.SettingHomeKt$SettingHome$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    SettingHomeKt.SettingHome(Function0.this, function1, settingViewModel3, labsViewModel3, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
