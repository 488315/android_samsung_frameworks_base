package com.android.systemui.media.mediaoutput.compose;

import android.content.Context;
import android.content.res.Configuration;
import androidx.activity.compose.PredictiveBackHandlerKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.AnimatedVisibilityKt;
import androidx.compose.animation.EnterExitTransitionKt;
import androidx.compose.foundation.BackgroundKt;
import androidx.compose.foundation.ClickableKt;
import androidx.compose.foundation.ImageKt;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.Arrangement$Top$1;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnMeasurePolicy;
import androidx.compose.foundation.layout.ColumnScopeInstance;
import androidx.compose.foundation.layout.FillElement;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.lazy.LazyDslKt;
import androidx.compose.foundation.lazy.LazyItemScopeImpl;
import androidx.compose.foundation.lazy.LazyListInterval;
import androidx.compose.foundation.lazy.LazyListIntervalContent;
import androidx.compose.material3.ContentColorKt;
import androidx.compose.material3.IconButtonKt;
import androidx.compose.material3.ProgressIndicatorKt;
import androidx.compose.material3.SnackbarHostState;
import androidx.compose.runtime.Applier;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.CompositionScopedCoroutineScopeCanceller;
import androidx.compose.runtime.DynamicProvidableCompositionLocal;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.ProvidedValue;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.StaticProvidableCompositionLocal;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ImageBitmap;
import androidx.compose.ui.graphics.RectangleShapeKt;
import androidx.compose.ui.layout.ContentScale;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.DensityImpl;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.DpKt;
import androidx.compose.ui.unit.DpSize;
import com.android.systemui.media.mediaoutput.compose.common.Feature;
import com.android.systemui.media.mediaoutput.compose.ext.CharSequenceExtKt;
import com.android.systemui.media.mediaoutput.compose.ext.CompositionExtKt;
import com.android.systemui.media.mediaoutput.compose.theme.ColorKt;
import com.android.systemui.media.mediaoutput.compose.widget.IconExtKt;
import com.android.systemui.media.mediaoutput.entity.MediaAction;
import com.android.systemui.media.mediaoutput.entity.MediaInfo;
import com.android.systemui.media.mediaoutput.viewmodel.MediaInteraction;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

public abstract class MediaCardKt {
    public static final void ControlArea(MediaInteraction mediaInteraction, Composer composer, final int i, final int i2) {
        MediaInteraction mediaInteraction2;
        int i3;
        final MediaInteraction mediaInteraction3;
        int i4;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1778593250);
        if ((i & 14) == 0) {
            if ((i2 & 1) == 0) {
                mediaInteraction2 = mediaInteraction;
                if (composerImpl.changed(mediaInteraction2)) {
                    i4 = 4;
                    i3 = i4 | i;
                }
            } else {
                mediaInteraction2 = mediaInteraction;
            }
            i4 = 2;
            i3 = i4 | i;
        } else {
            mediaInteraction2 = mediaInteraction;
            i3 = i;
        }
        if ((i3 & 11) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            mediaInteraction3 = mediaInteraction2;
        } else {
            composerImpl.startDefaults();
            if ((i & 1) != 0 && !composerImpl.getDefaultsInvalid()) {
                composerImpl.skipToGroupEnd();
                int i5 = i2 & 1;
            } else if ((i2 & 1) != 0) {
                mediaInteraction2 = (MediaInteraction) composerImpl.consume(CompositionExtKt.LocalMediaInteraction);
            }
            mediaInteraction3 = mediaInteraction2;
            composerImpl.endDefaults();
            OpaqueKey opaqueKey = ComposerKt.invocation;
            final MutableState collectAsState = SnapshotStateKt.collectAsState(mediaInteraction3.getMediaInfo(), mediaInteraction3.getEmpty(), null, composerImpl, 8, 2);
            final Flow mediaInfo = mediaInteraction3.getMediaInfo();
            final MutableState collectAsState2 = SnapshotStateKt.collectAsState(new Flow() { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$ControlArea$$inlined$map$1

                /* renamed from: com.android.systemui.media.mediaoutput.compose.MediaCardKt$ControlArea$$inlined$map$1$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                    /* renamed from: com.android.systemui.media.mediaoutput.compose.MediaCardKt$ControlArea$$inlined$map$1$2$1, reason: invalid class name */
                    public final class AnonymousClass1 extends ContinuationImpl {
                        Object L$0;
                        int label;
                        /* synthetic */ Object result;

                        public AnonymousClass1(Continuation continuation) {
                            super(continuation);
                        }

                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        public final Object invokeSuspend(Object obj) {
                            this.result = obj;
                            this.label |= Integer.MIN_VALUE;
                            return AnonymousClass2.this.emit(null, this);
                        }
                    }

                    public AnonymousClass2(FlowCollector flowCollector) {
                        this.$this_unsafeFlow = flowCollector;
                    }

                    @Override // kotlinx.coroutines.flow.FlowCollector
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                        /*
                            r4 = this;
                            boolean r0 = r6 instanceof com.android.systemui.media.mediaoutput.compose.MediaCardKt$ControlArea$$inlined$map$1.AnonymousClass2.AnonymousClass1
                            if (r0 == 0) goto L13
                            r0 = r6
                            com.android.systemui.media.mediaoutput.compose.MediaCardKt$ControlArea$$inlined$map$1$2$1 r0 = (com.android.systemui.media.mediaoutput.compose.MediaCardKt$ControlArea$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                            int r1 = r0.label
                            r2 = -2147483648(0xffffffff80000000, float:-0.0)
                            r3 = r1 & r2
                            if (r3 == 0) goto L13
                            int r1 = r1 - r2
                            r0.label = r1
                            goto L18
                        L13:
                            com.android.systemui.media.mediaoutput.compose.MediaCardKt$ControlArea$$inlined$map$1$2$1 r0 = new com.android.systemui.media.mediaoutput.compose.MediaCardKt$ControlArea$$inlined$map$1$2$1
                            r0.<init>(r6)
                        L18:
                            java.lang.Object r6 = r0.result
                            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                            int r2 = r0.label
                            r3 = 1
                            if (r2 == 0) goto L2f
                            if (r2 != r3) goto L27
                            kotlin.ResultKt.throwOnFailure(r6)
                            goto L43
                        L27:
                            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                            r4.<init>(r5)
                            throw r4
                        L2f:
                            kotlin.ResultKt.throwOnFailure(r6)
                            com.android.systemui.media.mediaoutput.entity.MediaInfo r5 = (com.android.systemui.media.mediaoutput.entity.MediaInfo) r5
                            java.util.List r5 = r5.getMediaActions()
                            r0.label = r3
                            kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                            java.lang.Object r4 = r4.emit(r5, r0)
                            if (r4 != r1) goto L43
                            return r1
                        L43:
                            kotlin.Unit r4 = kotlin.Unit.INSTANCE
                            return r4
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.MediaCardKt$ControlArea$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                    Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                    return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                }
            }, EmptyList.INSTANCE, null, composerImpl, 56, 2);
            final Flow mediaInfo2 = mediaInteraction3.getMediaInfo();
            MutableState collectAsState3 = SnapshotStateKt.collectAsState(new Flow() { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$ControlArea$$inlined$map$2

                /* renamed from: com.android.systemui.media.mediaoutput.compose.MediaCardKt$ControlArea$$inlined$map$2$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                    /* renamed from: com.android.systemui.media.mediaoutput.compose.MediaCardKt$ControlArea$$inlined$map$2$2$1, reason: invalid class name */
                    public final class AnonymousClass1 extends ContinuationImpl {
                        Object L$0;
                        int label;
                        /* synthetic */ Object result;

                        public AnonymousClass1(Continuation continuation) {
                            super(continuation);
                        }

                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        public final Object invokeSuspend(Object obj) {
                            this.result = obj;
                            this.label |= Integer.MIN_VALUE;
                            return AnonymousClass2.this.emit(null, this);
                        }
                    }

                    public AnonymousClass2(FlowCollector flowCollector) {
                        this.$this_unsafeFlow = flowCollector;
                    }

                    @Override // kotlinx.coroutines.flow.FlowCollector
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final java.lang.Object emit(java.lang.Object r9, kotlin.coroutines.Continuation r10) {
                        /*
                            r8 = this;
                            boolean r0 = r10 instanceof com.android.systemui.media.mediaoutput.compose.MediaCardKt$ControlArea$$inlined$map$2.AnonymousClass2.AnonymousClass1
                            if (r0 == 0) goto L13
                            r0 = r10
                            com.android.systemui.media.mediaoutput.compose.MediaCardKt$ControlArea$$inlined$map$2$2$1 r0 = (com.android.systemui.media.mediaoutput.compose.MediaCardKt$ControlArea$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                            int r1 = r0.label
                            r2 = -2147483648(0xffffffff80000000, float:-0.0)
                            r3 = r1 & r2
                            if (r3 == 0) goto L13
                            int r1 = r1 - r2
                            r0.label = r1
                            goto L18
                        L13:
                            com.android.systemui.media.mediaoutput.compose.MediaCardKt$ControlArea$$inlined$map$2$2$1 r0 = new com.android.systemui.media.mediaoutput.compose.MediaCardKt$ControlArea$$inlined$map$2$2$1
                            r0.<init>(r10)
                        L18:
                            java.lang.Object r10 = r0.result
                            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                            int r2 = r0.label
                            r3 = 1
                            if (r2 == 0) goto L2f
                            if (r2 != r3) goto L27
                            kotlin.ResultKt.throwOnFailure(r10)
                            goto L71
                        L27:
                            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
                            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
                            r8.<init>(r9)
                            throw r8
                        L2f:
                            kotlin.ResultKt.throwOnFailure(r10)
                            com.android.systemui.media.mediaoutput.entity.MediaInfo r9 = (com.android.systemui.media.mediaoutput.entity.MediaInfo) r9
                            java.util.List r9 = r9.getMediaActions()
                            java.lang.Iterable r9 = (java.lang.Iterable) r9
                            boolean r10 = r9 instanceof java.util.Collection
                            r2 = 0
                            if (r10 == 0) goto L49
                            r10 = r9
                            java.util.Collection r10 = (java.util.Collection) r10
                            boolean r10 = r10.isEmpty()
                            if (r10 == 0) goto L49
                            goto L62
                        L49:
                            java.util.Iterator r9 = r9.iterator()
                        L4d:
                            boolean r10 = r9.hasNext()
                            if (r10 == 0) goto L62
                            java.lang.Object r10 = r9.next()
                            com.android.systemui.media.mediaoutput.entity.MediaAction r10 = (com.android.systemui.media.mediaoutput.entity.MediaAction) r10
                            long r4 = r10.id
                            r6 = -4
                            int r10 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
                            if (r10 != 0) goto L4d
                            r2 = r3
                        L62:
                            java.lang.Boolean r9 = java.lang.Boolean.valueOf(r2)
                            r0.label = r3
                            kotlinx.coroutines.flow.FlowCollector r8 = r8.$this_unsafeFlow
                            java.lang.Object r8 = r8.emit(r9, r0)
                            if (r8 != r1) goto L71
                            return r1
                        L71:
                            kotlin.Unit r8 = kotlin.Unit.INSTANCE
                            return r8
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.MediaCardKt$ControlArea$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                    Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                    return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                }
            }, Boolean.FALSE, null, composerImpl, 56, 2);
            Arrangement arrangement = Arrangement.INSTANCE;
            Dp.Companion companion = Dp.Companion;
            arrangement.getClass();
            LazyDslKt.LazyRow(null, null, null, false, Arrangement.m75spacedBy0680j_4(15), null, null, false, new Function1() { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$ControlArea$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    LazyListIntervalContent lazyListIntervalContent = (LazyListIntervalContent) obj;
                    final List list = (List) State.this.getValue();
                    final AnonymousClass1 anonymousClass1 = new Function1() { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$ControlArea$1.1
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj2) {
                            return Long.valueOf(((MediaAction) obj2).id);
                        }
                    };
                    final MediaInteraction mediaInteraction4 = mediaInteraction3;
                    final State state = collectAsState;
                    final MediaCardKt$ControlArea$1$invoke$$inlined$items$default$1 mediaCardKt$ControlArea$1$invoke$$inlined$items$default$1 = new Function1() { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$ControlArea$1$invoke$$inlined$items$default$1
                        @Override // kotlin.jvm.functions.Function1
                        public final /* bridge */ /* synthetic */ Object invoke(Object obj2) {
                            return null;
                        }
                    };
                    int size = list.size();
                    Function1 function1 = anonymousClass1 != null ? new Function1() { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$ControlArea$1$invoke$$inlined$items$default$2
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj2) {
                            return Function1.this.invoke(list.get(((Number) obj2).intValue()));
                        }
                    } : null;
                    Function1 function12 = new Function1() { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$ControlArea$1$invoke$$inlined$items$default$3
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj2) {
                            return Function1.this.invoke(list.get(((Number) obj2).intValue()));
                        }
                    };
                    ComposableLambdaImpl composableLambdaImpl = new ComposableLambdaImpl(-632812321, true, new Function4() { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$ControlArea$1$invoke$$inlined$items$default$4
                        {
                            super(4);
                        }

                        @Override // kotlin.jvm.functions.Function4
                        public final Object invoke(Object obj2, Object obj3, Object obj4, Object obj5) {
                            int i6;
                            LazyItemScopeImpl lazyItemScopeImpl = (LazyItemScopeImpl) obj2;
                            int intValue = ((Number) obj3).intValue();
                            Composer composer2 = (Composer) obj4;
                            int intValue2 = ((Number) obj5).intValue();
                            if ((intValue2 & 6) == 0) {
                                i6 = (((ComposerImpl) composer2).changed(lazyItemScopeImpl) ? 4 : 2) | intValue2;
                            } else {
                                i6 = intValue2;
                            }
                            if ((intValue2 & 48) == 0) {
                                i6 |= ((ComposerImpl) composer2).changed(intValue) ? 32 : 16;
                            }
                            if ((i6 & 147) == 146) {
                                ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                                if (composerImpl2.getSkipping()) {
                                    composerImpl2.skipToGroupEnd();
                                    return Unit.INSTANCE;
                                }
                            }
                            OpaqueKey opaqueKey2 = ComposerKt.invocation;
                            final MediaAction mediaAction = (MediaAction) list.get(intValue);
                            ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                            composerImpl3.startReplaceGroup(1374122396);
                            if (mediaAction.id == -1) {
                                composerImpl3.startReplaceGroup(1374122461);
                                Dp.Companion companion2 = Dp.Companion;
                                ProgressIndicatorKt.m229CircularProgressIndicatorLxG7B9w(SizeKt.m115size3ABfNKs(Modifier.Companion, 32), 0L, 0.0f, 0L, 0, composerImpl3, 6, 30);
                                composerImpl3.end(false);
                            } else {
                                composerImpl3.startReplaceGroup(1374122557);
                                final MediaInteraction mediaInteraction5 = mediaInteraction4;
                                final State state2 = state;
                                Function0 function0 = new Function0() { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$ControlArea$1$2$1
                                    {
                                        super(0);
                                    }

                                    @Override // kotlin.jvm.functions.Function0
                                    public final Object invoke() {
                                        MediaInteraction.this.execute((MediaInfo) state2.getValue(), mediaAction.id, 0L);
                                        return Unit.INSTANCE;
                                    }
                                };
                                Dp.Companion companion3 = Dp.Companion;
                                IconButtonKt.IconButton(function0, SizeKt.m115size3ABfNKs(Modifier.Companion, 32), mediaAction.enabled, null, null, ComposableLambdaKt.rememberComposableLambda(992645849, composerImpl3, new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$ControlArea$1$2$2
                                    {
                                        super(2);
                                    }

                                    @Override // kotlin.jvm.functions.Function2
                                    public final Object invoke(Object obj6, Object obj7) {
                                        Composer composer3 = (Composer) obj6;
                                        if ((((Number) obj7).intValue() & 11) == 2) {
                                            ComposerImpl composerImpl4 = (ComposerImpl) composer3;
                                            if (composerImpl4.getSkipping()) {
                                                composerImpl4.skipToGroupEnd();
                                                return Unit.INSTANCE;
                                            }
                                        }
                                        OpaqueKey opaqueKey3 = ComposerKt.invocation;
                                        MediaAction mediaAction2 = MediaAction.this;
                                        IconExtKt.m1978IconExtww6aTOc(mediaAction2.icon, CharSequenceExtKt.text(mediaAction2.description, composer3), null, ColorKt.mediaPrimaryColor(composer3), composer3, 8, 4);
                                        return Unit.INSTANCE;
                                    }
                                }), composerImpl3, 196656, 24);
                                composerImpl3.end(false);
                            }
                            composerImpl3.end(false);
                            return Unit.INSTANCE;
                        }
                    });
                    lazyListIntervalContent.getClass();
                    lazyListIntervalContent.intervals.addInterval(size, new LazyListInterval(function1, function12, composableLambdaImpl));
                    return Unit.INSTANCE;
                }
            }, composerImpl, 24576, IKnoxCustomManager.Stub.TRANSACTION_getFavoriteAppsMaxCount);
            SnackbarHostState snackbarHostState = (SnackbarHostState) composerImpl.consume(CompositionExtKt.LocalSnackbarHostState);
            Context context = (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
            Boolean bool = (Boolean) collectAsState3.getValue();
            bool.getClass();
            EffectsKt.LaunchedEffect(composerImpl, bool, new MediaCardKt$ControlArea$2(collectAsState2, collectAsState, context, snackbarHostState, mediaInteraction3, null));
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$ControlArea$3
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    MediaCardKt.ControlArea(MediaInteraction.this, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void MediaCard(final int i, Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-260820118);
        if (i == 0 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            final Feature feature = (Feature) composerImpl.consume(CompositionExtKt.LocalFeature);
            DynamicProvidableCompositionLocal dynamicProvidableCompositionLocal = ContentColorKt.LocalContentColor;
            Color.Companion.getClass();
            CompositionLocalKt.CompositionLocalProvider(dynamicProvidableCompositionLocal.defaultProvidedValue$runtime_release(Color.m383boximpl(Color.White)), ComposableLambdaKt.rememberComposableLambda(-1416714582, composerImpl, new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$MediaCard$1
                {
                    super(2);
                }

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
                    Modifier.Companion companion = Modifier.Companion;
                    Modifier m24backgroundbw27NRU = BackgroundKt.m24backgroundbw27NRU(ClickableKt.m30clickableO2vRcR0$default(SizeKt.fillMaxWidth(companion, 1.0f), null, null, false, null, new Function0() { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$MediaCard$1.1
                        @Override // kotlin.jvm.functions.Function0
                        public final /* bridge */ /* synthetic */ Object invoke() {
                            return Unit.INSTANCE;
                        }
                    }, 28), Feature.this.backgroundColor, RectangleShapeKt.RectangleShape);
                    Feature feature2 = Feature.this;
                    Arrangement.INSTANCE.getClass();
                    Arrangement$Top$1 arrangement$Top$1 = Arrangement.Top;
                    Alignment.Companion.getClass();
                    ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(arrangement$Top$1, Alignment.Companion.Start, composer2, 0);
                    int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                    ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                    PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl3.currentCompositionLocalScope();
                    Modifier materializeModifier = ComposedModifierKt.materializeModifier(composer2, m24backgroundbw27NRU);
                    ComposeUiNode.Companion.getClass();
                    Function0 function0 = ComposeUiNode.Companion.Constructor;
                    if (!(composerImpl3.applier instanceof Applier)) {
                        ComposablesKt.invalidApplier();
                        throw null;
                    }
                    composerImpl3.startReusableNode();
                    if (composerImpl3.inserting) {
                        composerImpl3.createNode(function0);
                    } else {
                        composerImpl3.useNode();
                    }
                    Updater.m276setimpl(composer2, columnMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                    Updater.m276setimpl(composer2, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                    Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                    if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl3, currentCompositeKeyHash, function2);
                    }
                    Updater.m276setimpl(composer2, materializeModifier, ComposeUiNode.Companion.SetModifier);
                    ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
                    composerImpl3.startReplaceGroup(-1621463834);
                    if (feature2.showMediaController) {
                        StaticProvidableCompositionLocal staticProvidableCompositionLocal = CompositionLocalsKt.LocalDensity;
                        ProvidedValue defaultProvidedValue$runtime_release = staticProvidableCompositionLocal.defaultProvidedValue$runtime_release(new DensityImpl(((Density) composerImpl3.consume(staticProvidableCompositionLocal)).getDensity(), 1.0f));
                        ComposableSingletons$MediaCardKt.INSTANCE.getClass();
                        CompositionLocalKt.CompositionLocalProvider(defaultProvidedValue$runtime_release, ComposableSingletons$MediaCardKt.f56lambda1, composer2, 56);
                    }
                    composerImpl3.end(false);
                    MediaCardKt.access$AudioPathSection(columnScopeInstance.weight(companion, 1.0f, true), null, null, composer2, 0, 6);
                    composerImpl3.end(true);
                    return Unit.INSTANCE;
                }
            }), composerImpl, 56);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$MediaCard$2
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    MediaCardKt.MediaCard(RecomposeScopeImplKt.updateChangedFlags(i | 1), (Composer) obj);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:34:0x00dc, code lost:
    
        if (kotlin.jvm.internal.Intrinsics.areEqual(r14.rememberedValue(), java.lang.Integer.valueOf(r5)) == false) goto L41;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void MediaDeviceControlArea(com.android.systemui.media.mediaoutput.viewmodel.MediaInteraction r49, androidx.compose.runtime.Composer r50, final int r51, final int r52) {
        /*
            Method dump skipped, instructions count: 764
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.MediaCardKt.MediaDeviceControlArea(com.android.systemui.media.mediaoutput.viewmodel.MediaInteraction, androidx.compose.runtime.Composer, int, int):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:40:0x018d, code lost:
    
        if (kotlin.jvm.internal.Intrinsics.areEqual(r15.rememberedValue(), java.lang.Integer.valueOf(r7)) == false) goto L47;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void ProgressArea(com.android.systemui.media.mediaoutput.viewmodel.MediaInteraction r43, androidx.compose.runtime.Composer r44, final int r45, final int r46) {
        /*
            Method dump skipped, instructions count: 998
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.MediaCardKt.ProgressArea(com.android.systemui.media.mediaoutput.viewmodel.MediaInteraction, androidx.compose.runtime.Composer, int, int):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:34:0x0167, code lost:
    
        if (kotlin.jvm.internal.Intrinsics.areEqual(r13.rememberedValue(), java.lang.Integer.valueOf(r6)) == false) goto L41;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void TitleArea(com.android.systemui.media.mediaoutput.viewmodel.MediaInteraction r31, androidx.compose.runtime.Composer r32, final int r33, final int r34) {
        /*
            Method dump skipped, instructions count: 720
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.MediaCardKt.TitleArea(com.android.systemui.media.mediaoutput.viewmodel.MediaInteraction, androidx.compose.runtime.Composer, int, int):void");
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void access$AudioPathSection(androidx.compose.ui.Modifier r20, com.android.systemui.media.mediaoutput.viewmodel.AudioPathInteraction r21, com.android.systemui.media.mediaoutput.viewmodel.LabsViewModel r22, androidx.compose.runtime.Composer r23, final int r24, final int r25) {
        /*
            Method dump skipped, instructions count: 582
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.MediaCardKt.access$AudioPathSection(androidx.compose.ui.Modifier, com.android.systemui.media.mediaoutput.viewmodel.AudioPathInteraction, com.android.systemui.media.mediaoutput.viewmodel.LabsViewModel, androidx.compose.runtime.Composer, int, int):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:66:0x0208, code lost:
    
        if (r0 != null) goto L119;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void access$DeviceListItem(final com.android.systemui.media.mediaoutput.entity.AudioDevice r51, androidx.compose.ui.Modifier r52, final androidx.compose.runtime.MutableState r53, boolean r54, boolean r55, com.android.systemui.media.mediaoutput.viewmodel.AudioPathInteraction r56, androidx.compose.runtime.Composer r57, final int r58, final int r59) {
        /*
            Method dump skipped, instructions count: 1822
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.MediaCardKt.access$DeviceListItem(com.android.systemui.media.mediaoutput.entity.AudioDevice, androidx.compose.ui.Modifier, androidx.compose.runtime.MutableState, boolean, boolean, com.android.systemui.media.mediaoutput.viewmodel.AudioPathInteraction, androidx.compose.runtime.Composer, int, int):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:37:0x010b, code lost:
    
        if (r5 != false) goto L54;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void access$MediaControlSection(androidx.compose.ui.Modifier r21, com.android.systemui.media.mediaoutput.viewmodel.MediaInteraction r22, androidx.compose.runtime.Composer r23, final int r24, final int r25) {
        /*
            Method dump skipped, instructions count: 937
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.MediaCardKt.access$MediaControlSection(androidx.compose.ui.Modifier, com.android.systemui.media.mediaoutput.viewmodel.MediaInteraction, androidx.compose.runtime.Composer, int, int):void");
    }

    public static final void access$ThumbnailSection(final Modifier modifier, final MediaInteraction mediaInteraction, Composer composer, final int i, final int i2) {
        int i3;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1119005814);
        int i4 = i2 & 1;
        if (i4 != 0) {
            i3 = i | 6;
        } else if ((i & 14) == 0) {
            i3 = (composerImpl.changed(modifier) ? 4 : 2) | i;
        } else {
            i3 = i;
        }
        if ((i & 112) == 0) {
            i3 |= ((i2 & 2) == 0 && composerImpl.changed(mediaInteraction)) ? 32 : 16;
        }
        if ((i3 & 91) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            composerImpl.startDefaults();
            if ((i & 1) == 0 || composerImpl.getDefaultsInvalid()) {
                if (i4 != 0) {
                    modifier = Modifier.Companion;
                }
                if ((2 & i2) != 0) {
                    mediaInteraction = (MediaInteraction) composerImpl.consume(CompositionExtKt.LocalMediaInteraction);
                }
            } else {
                composerImpl.skipToGroupEnd();
            }
            composerImpl.endDefaults();
            OpaqueKey opaqueKey = ComposerKt.invocation;
            final Flow mediaInfo = mediaInteraction.getMediaInfo();
            final MutableState collectAsState = SnapshotStateKt.collectAsState(new Flow() { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$ThumbnailSection$$inlined$map$1

                /* renamed from: com.android.systemui.media.mediaoutput.compose.MediaCardKt$ThumbnailSection$$inlined$map$1$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                    /* renamed from: com.android.systemui.media.mediaoutput.compose.MediaCardKt$ThumbnailSection$$inlined$map$1$2$1, reason: invalid class name */
                    public final class AnonymousClass1 extends ContinuationImpl {
                        Object L$0;
                        int label;
                        /* synthetic */ Object result;

                        public AnonymousClass1(Continuation continuation) {
                            super(continuation);
                        }

                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        public final Object invokeSuspend(Object obj) {
                            this.result = obj;
                            this.label |= Integer.MIN_VALUE;
                            return AnonymousClass2.this.emit(null, this);
                        }
                    }

                    public AnonymousClass2(FlowCollector flowCollector) {
                        this.$this_unsafeFlow = flowCollector;
                    }

                    @Override // kotlinx.coroutines.flow.FlowCollector
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                        /*
                            r4 = this;
                            boolean r0 = r6 instanceof com.android.systemui.media.mediaoutput.compose.MediaCardKt$ThumbnailSection$$inlined$map$1.AnonymousClass2.AnonymousClass1
                            if (r0 == 0) goto L13
                            r0 = r6
                            com.android.systemui.media.mediaoutput.compose.MediaCardKt$ThumbnailSection$$inlined$map$1$2$1 r0 = (com.android.systemui.media.mediaoutput.compose.MediaCardKt$ThumbnailSection$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                            int r1 = r0.label
                            r2 = -2147483648(0xffffffff80000000, float:-0.0)
                            r3 = r1 & r2
                            if (r3 == 0) goto L13
                            int r1 = r1 - r2
                            r0.label = r1
                            goto L18
                        L13:
                            com.android.systemui.media.mediaoutput.compose.MediaCardKt$ThumbnailSection$$inlined$map$1$2$1 r0 = new com.android.systemui.media.mediaoutput.compose.MediaCardKt$ThumbnailSection$$inlined$map$1$2$1
                            r0.<init>(r6)
                        L18:
                            java.lang.Object r6 = r0.result
                            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                            int r2 = r0.label
                            r3 = 1
                            if (r2 == 0) goto L2f
                            if (r2 != r3) goto L27
                            kotlin.ResultKt.throwOnFailure(r6)
                            goto L43
                        L27:
                            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                            r4.<init>(r5)
                            throw r4
                        L2f:
                            kotlin.ResultKt.throwOnFailure(r6)
                            com.android.systemui.media.mediaoutput.entity.MediaInfo r5 = (com.android.systemui.media.mediaoutput.entity.MediaInfo) r5
                            androidx.compose.ui.graphics.ImageBitmap r5 = r5.getThumbnail()
                            r0.label = r3
                            kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                            java.lang.Object r4 = r4.emit(r5, r0)
                            if (r4 != r1) goto L43
                            return r1
                        L43:
                            kotlin.Unit r4 = kotlin.Unit.INSTANCE
                            return r4
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.MediaCardKt$ThumbnailSection$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                    Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                    return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                }
            }, null, null, composerImpl, 56, 2);
            Alignment.Companion.getClass();
            MeasurePolicy maybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
            int i5 = composerImpl.compoundKeyHash;
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifier);
            ComposeUiNode.Companion.getClass();
            Function0 function0 = ComposeUiNode.Companion.Constructor;
            if (!(composerImpl.applier instanceof Applier)) {
                ComposablesKt.invalidApplier();
                throw null;
            }
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function0);
            } else {
                composerImpl.useNode();
            }
            Updater.m276setimpl(composerImpl, maybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m276setimpl(composerImpl, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(i5))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(i5, composerImpl, i5, function2);
            }
            Updater.m276setimpl(composerImpl, materializeModifier, ComposeUiNode.Companion.SetModifier);
            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
            AnimatedVisibilityKt.AnimatedVisibility(((ImageBitmap) collectAsState.getValue()) != null, (Modifier) null, EnterExitTransitionKt.fadeIn$default(null, 3), EnterExitTransitionKt.fadeOut$default(null, 3), (String) null, ComposableLambdaKt.rememberComposableLambda(-924960788, composerImpl, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$ThumbnailSection$1$1
                {
                    super(3);
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    Composer composer2 = (Composer) obj2;
                    ((Number) obj3).intValue();
                    OpaqueKey opaqueKey2 = ComposerKt.invocation;
                    ImageBitmap imageBitmap = (ImageBitmap) State.this.getValue();
                    if (imageBitmap != null) {
                        Modifier.Companion companion = Modifier.Companion;
                        FillElement fillElement = SizeKt.FillWholeMaxSize;
                        companion.then(fillElement);
                        ContentScale.Companion.getClass();
                        ImageKt.m34Image5hnEew(imageBitmap, "", fillElement, ContentScale.Companion.Crop, composer2, 25016, IKnoxCustomManager.Stub.TRANSACTION_addWidget);
                    }
                    return Unit.INSTANCE;
                }
            }), composerImpl, 200064, 18);
            composerImpl.end(true);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$ThumbnailSection$2
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    MediaCardKt.access$ThumbnailSection(Modifier.this, mediaInteraction, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final boolean isProgressVisible(Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(1486251067);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        composerImpl.startReplaceGroup(-575072100);
        float f = ((Configuration) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalConfiguration)).screenWidthDp;
        Dp.Companion companion = Dp.Companion;
        long m742DpSizeYgX7TsA = DpKt.m742DpSizeYgX7TsA(f, r0.screenHeightDp);
        composerImpl.end(false);
        boolean z = Float.compare(DpSize.m747getHeightD9Ej5fM(m742DpSizeYgX7TsA), (float) 411) > 0;
        composerImpl.end(false);
        return z;
    }

    public static final void showInAppCastingPopup(final Function0 function0, final MediaInteraction mediaInteraction, Composer composer, final int i, final int i2) {
        int i3;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(883040504);
        if ((i2 & 1) != 0) {
            i3 = i | 6;
        } else if ((i & 14) == 0) {
            i3 = (composerImpl.changedInstance(function0) ? 4 : 2) | i;
        } else {
            i3 = i;
        }
        if ((i & 112) == 0) {
            i3 |= ((i2 & 2) == 0 && composerImpl.changed(mediaInteraction)) ? 32 : 16;
        }
        if ((i3 & 91) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            composerImpl.startDefaults();
            if ((i & 1) != 0 && !composerImpl.getDefaultsInvalid()) {
                composerImpl.skipToGroupEnd();
                int i4 = 2 & i2;
            } else if ((2 & i2) != 0) {
                mediaInteraction = (MediaInteraction) composerImpl.consume(CompositionExtKt.LocalMediaInteraction);
            }
            composerImpl.endDefaults();
            OpaqueKey opaqueKey = ComposerKt.invocation;
            MutableState collectAsState = SnapshotStateKt.collectAsState(mediaInteraction.getMediaInfo(), mediaInteraction.getEmpty(), null, composerImpl, 8, 2);
            SnackbarHostState snackbarHostState = (SnackbarHostState) composerImpl.consume(CompositionExtKt.LocalSnackbarHostState);
            Context context = (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
            Object rememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            if (rememberedValue == Composer.Companion.Empty) {
                rememberedValue = PredictiveBackHandlerKt$$ExternalSyntheticOutline0.m(EffectsKt.createCompositionCoroutineScope(EmptyCoroutineContext.INSTANCE, composerImpl), composerImpl);
            }
            BuildersKt.launch$default(((CompositionScopedCoroutineScopeCanceller) rememberedValue).coroutineScope, null, null, new MediaCardKt$showInAppCastingPopup$1(context, snackbarHostState, collectAsState, mediaInteraction, function0, null), 3);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$showInAppCastingPopup$2
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    MediaCardKt.showInAppCastingPopup(Function0.this, mediaInteraction, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
