package com.android.systemui.media.mediaoutput.viewmodel;

import android.content.Context;
import android.util.Log;
import androidx.datastore.core.DataStore;
import androidx.datastore.preferences.core.Preferences;
import androidx.lifecycle.ViewModel;
import com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt;
import com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$1;
import com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$10;
import com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$2;
import com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$3;
import com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$4;
import com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$5;
import com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$6;
import com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$7;
import com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$8;
import com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$9;
import com.android.systemui.media.mediaoutput.common.DataStoreLabsExt;
import com.android.systemui.media.mediaoutput.common.DataStoreLabsExt$special$$inlined$map$1;
import com.android.systemui.media.mediaoutput.common.DataStoreLabsExt$special$$inlined$map$2;
import com.android.systemui.media.mediaoutput.common.DataStoreLabsExt$special$$inlined$map$3;
import com.android.systemui.media.mediaoutput.common.DataStoreLabsExt$special$$inlined$map$4;
import com.android.systemui.qs.pipeline.domain.interactor.PanelInteractor;
import java.util.Map;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.SafeFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class LabsViewModel extends ViewModel {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Context context;
    public final DataStore dataStore;
    public final DataStoreDebugLabsExt$special$$inlined$map$10 isActionOpenOutputSwitcher;
    public final DataStoreLabsExt$special$$inlined$map$1 isCloseOnTouchOutside;
    public final DataStoreDebugLabsExt$special$$inlined$map$2 isGrayscaleThumbnail;
    public final DataStoreLabsExt$special$$inlined$map$3 isGroupSpeakerDefaultExpanded;
    public final SafeFlow isQuickboardInstalled;
    public final DataStoreDebugLabsExt$special$$inlined$map$1 isShowLabsMenu;
    public final DataStoreDebugLabsExt$special$$inlined$map$5 isSupportDisplayDeviceVolumeControl;
    public final DataStoreDebugLabsExt$special$$inlined$map$8 isSupportDisplayOnlyRemoteDevice;
    public final DataStoreDebugLabsExt$special$$inlined$map$7 isSupportForTransferDuringRouting;
    public final DataStoreDebugLabsExt$special$$inlined$map$9 isSupportForUnsupportedTV;
    public final DataStoreDebugLabsExt$special$$inlined$map$3 isSupportMultipleMediaSession;
    public final DataStoreLabsExt$special$$inlined$map$4 isSupportRecentGroupSpeaker;
    public final DataStoreDebugLabsExt$special$$inlined$map$4 isSupportSelectableBudsTogether;
    public final DataStoreDebugLabsExt$special$$inlined$map$6 isSupportTransferableRoutesWhileConnecting;
    public final DataStoreLabsExt$special$$inlined$map$2 isSupportVolumeInteraction;
    public final PanelInteractor panelInteractor;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.LabsViewModel$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return LabsViewModel.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Flow data = LabsViewModel.this.dataStore.getData();
                C02301 c02301 = new FlowCollector() { // from class: com.android.systemui.media.mediaoutput.viewmodel.LabsViewModel.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        for (Map.Entry entry : ((Preferences) obj2).asMap().entrySet()) {
                            Log.e("LabsViewModel", ((Preferences.Key) entry.getKey()) + " : " + entry.getValue());
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (data.collect(c02301, this) == coroutineSingletons) {
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r3v1, types: [com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$1] */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.android.systemui.media.mediaoutput.common.DataStoreLabsExt$special$$inlined$map$1] */
    /* JADX WARN: Type inference failed for: r3v3, types: [com.android.systemui.media.mediaoutput.common.DataStoreLabsExt$special$$inlined$map$2] */
    /* JADX WARN: Type inference failed for: r3v4, types: [com.android.systemui.media.mediaoutput.common.DataStoreLabsExt$special$$inlined$map$3] */
    /* JADX WARN: Type inference failed for: r3v5, types: [com.android.systemui.media.mediaoutput.common.DataStoreLabsExt$special$$inlined$map$4] */
    /* JADX WARN: Type inference failed for: r3v7, types: [com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$3] */
    /* JADX WARN: Type inference failed for: r4v1, types: [com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$10] */
    public LabsViewModel(Context context, PanelInteractor panelInteractor, DataStore dataStore) {
        this.context = context;
        this.panelInteractor = panelInteractor;
        this.dataStore = dataStore;
        DataStoreDebugLabsExt.INSTANCE.getClass();
        final Flow data = dataStore.getData();
        this.isShowLabsMenu = new Flow() { // from class: com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$1$2$1, reason: invalid class name */
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

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$1$2$1 r0 = (com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$1$2$1 r0 = new com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L58
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        androidx.datastore.preferences.core.Preferences r5 = (androidx.datastore.preferences.core.Preferences) r5
                        com.android.systemui.media.mediaoutput.common.PreferenceDebugLabsKeys r6 = com.android.systemui.media.mediaoutput.common.PreferenceDebugLabsKeys.INSTANCE
                        r6.getClass()
                        androidx.datastore.preferences.core.Preferences$Key r6 = com.android.systemui.media.mediaoutput.common.PreferenceDebugLabsKeys.SHOW_LABS_MENU
                        java.lang.Object r5 = r5.get(r6)
                        java.lang.Boolean r5 = (java.lang.Boolean) r5
                        if (r5 == 0) goto L48
                        boolean r5 = r5.booleanValue()
                        goto L49
                    L48:
                        r5 = 0
                    L49:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L58
                        return r1
                    L58:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        DataStoreLabsExt.INSTANCE.getClass();
        final Flow data2 = dataStore.getData();
        this.isCloseOnTouchOutside = new Flow() { // from class: com.android.systemui.media.mediaoutput.common.DataStoreLabsExt$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.media.mediaoutput.common.DataStoreLabsExt$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.media.mediaoutput.common.DataStoreLabsExt$special$$inlined$map$1$2$1, reason: invalid class name */
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

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.media.mediaoutput.common.DataStoreLabsExt$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.media.mediaoutput.common.DataStoreLabsExt$special$$inlined$map$1$2$1 r0 = (com.android.systemui.media.mediaoutput.common.DataStoreLabsExt$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.media.mediaoutput.common.DataStoreLabsExt$special$$inlined$map$1$2$1 r0 = new com.android.systemui.media.mediaoutput.common.DataStoreLabsExt$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L58
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        androidx.datastore.preferences.core.Preferences r5 = (androidx.datastore.preferences.core.Preferences) r5
                        com.android.systemui.media.mediaoutput.common.PreferenceLabsKeys r6 = com.android.systemui.media.mediaoutput.common.PreferenceLabsKeys.INSTANCE
                        r6.getClass()
                        androidx.datastore.preferences.core.Preferences$Key r6 = com.android.systemui.media.mediaoutput.common.PreferenceLabsKeys.CLOSE_ON_TOUCH_OUTSIDE
                        java.lang.Object r5 = r5.get(r6)
                        java.lang.Boolean r5 = (java.lang.Boolean) r5
                        if (r5 == 0) goto L48
                        boolean r5 = r5.booleanValue()
                        goto L49
                    L48:
                        r5 = r3
                    L49:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L58
                        return r1
                    L58:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.common.DataStoreLabsExt$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        final Flow data3 = dataStore.getData();
        this.isSupportVolumeInteraction = new Flow() { // from class: com.android.systemui.media.mediaoutput.common.DataStoreLabsExt$special$$inlined$map$2

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.media.mediaoutput.common.DataStoreLabsExt$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.media.mediaoutput.common.DataStoreLabsExt$special$$inlined$map$2$2$1, reason: invalid class name */
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

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.media.mediaoutput.common.DataStoreLabsExt$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.media.mediaoutput.common.DataStoreLabsExt$special$$inlined$map$2$2$1 r0 = (com.android.systemui.media.mediaoutput.common.DataStoreLabsExt$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.media.mediaoutput.common.DataStoreLabsExt$special$$inlined$map$2$2$1 r0 = new com.android.systemui.media.mediaoutput.common.DataStoreLabsExt$special$$inlined$map$2$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L58
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        androidx.datastore.preferences.core.Preferences r5 = (androidx.datastore.preferences.core.Preferences) r5
                        com.android.systemui.media.mediaoutput.common.PreferenceLabsKeys r6 = com.android.systemui.media.mediaoutput.common.PreferenceLabsKeys.INSTANCE
                        r6.getClass()
                        androidx.datastore.preferences.core.Preferences$Key r6 = com.android.systemui.media.mediaoutput.common.PreferenceLabsKeys.SUPPORT_VOLUME_INTERACTION
                        java.lang.Object r5 = r5.get(r6)
                        java.lang.Boolean r5 = (java.lang.Boolean) r5
                        if (r5 == 0) goto L48
                        boolean r5 = r5.booleanValue()
                        goto L49
                    L48:
                        r5 = r3
                    L49:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L58
                        return r1
                    L58:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.common.DataStoreLabsExt$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        final Flow data4 = dataStore.getData();
        this.isGroupSpeakerDefaultExpanded = new Flow() { // from class: com.android.systemui.media.mediaoutput.common.DataStoreLabsExt$special$$inlined$map$3

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.media.mediaoutput.common.DataStoreLabsExt$special$$inlined$map$3$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.media.mediaoutput.common.DataStoreLabsExt$special$$inlined$map$3$2$1, reason: invalid class name */
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

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.media.mediaoutput.common.DataStoreLabsExt$special$$inlined$map$3.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.media.mediaoutput.common.DataStoreLabsExt$special$$inlined$map$3$2$1 r0 = (com.android.systemui.media.mediaoutput.common.DataStoreLabsExt$special$$inlined$map$3.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.media.mediaoutput.common.DataStoreLabsExt$special$$inlined$map$3$2$1 r0 = new com.android.systemui.media.mediaoutput.common.DataStoreLabsExt$special$$inlined$map$3$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L58
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        androidx.datastore.preferences.core.Preferences r5 = (androidx.datastore.preferences.core.Preferences) r5
                        com.android.systemui.media.mediaoutput.common.PreferenceLabsKeys r6 = com.android.systemui.media.mediaoutput.common.PreferenceLabsKeys.INSTANCE
                        r6.getClass()
                        androidx.datastore.preferences.core.Preferences$Key r6 = com.android.systemui.media.mediaoutput.common.PreferenceLabsKeys.GROUP_SPEAKER_DEFAULT_EXPANDED
                        java.lang.Object r5 = r5.get(r6)
                        java.lang.Boolean r5 = (java.lang.Boolean) r5
                        if (r5 == 0) goto L48
                        boolean r5 = r5.booleanValue()
                        goto L49
                    L48:
                        r5 = r3
                    L49:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L58
                        return r1
                    L58:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.common.DataStoreLabsExt$special$$inlined$map$3.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        final Flow data5 = dataStore.getData();
        this.isSupportRecentGroupSpeaker = new Flow() { // from class: com.android.systemui.media.mediaoutput.common.DataStoreLabsExt$special$$inlined$map$4

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.media.mediaoutput.common.DataStoreLabsExt$special$$inlined$map$4$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.media.mediaoutput.common.DataStoreLabsExt$special$$inlined$map$4$2$1, reason: invalid class name */
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

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.media.mediaoutput.common.DataStoreLabsExt$special$$inlined$map$4.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.media.mediaoutput.common.DataStoreLabsExt$special$$inlined$map$4$2$1 r0 = (com.android.systemui.media.mediaoutput.common.DataStoreLabsExt$special$$inlined$map$4.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.media.mediaoutput.common.DataStoreLabsExt$special$$inlined$map$4$2$1 r0 = new com.android.systemui.media.mediaoutput.common.DataStoreLabsExt$special$$inlined$map$4$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L58
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        androidx.datastore.preferences.core.Preferences r5 = (androidx.datastore.preferences.core.Preferences) r5
                        com.android.systemui.media.mediaoutput.common.PreferenceLabsKeys r6 = com.android.systemui.media.mediaoutput.common.PreferenceLabsKeys.INSTANCE
                        r6.getClass()
                        androidx.datastore.preferences.core.Preferences$Key r6 = com.android.systemui.media.mediaoutput.common.PreferenceLabsKeys.SUPPORT_RECENT_GROUP_SPEAKER
                        java.lang.Object r5 = r5.get(r6)
                        java.lang.Boolean r5 = (java.lang.Boolean) r5
                        if (r5 == 0) goto L48
                        boolean r5 = r5.booleanValue()
                        goto L49
                    L48:
                        r5 = 0
                    L49:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L58
                        return r1
                    L58:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.common.DataStoreLabsExt$special$$inlined$map$4.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        this.isGrayscaleThumbnail = new DataStoreDebugLabsExt$special$$inlined$map$2(dataStore.getData());
        final Flow data6 = dataStore.getData();
        this.isSupportMultipleMediaSession = new Flow() { // from class: com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$3

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$3$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$3$2$1, reason: invalid class name */
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

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$3.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$3$2$1 r0 = (com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$3.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$3$2$1 r0 = new com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$3$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L58
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        androidx.datastore.preferences.core.Preferences r5 = (androidx.datastore.preferences.core.Preferences) r5
                        com.android.systemui.media.mediaoutput.common.PreferenceDebugLabsKeys r6 = com.android.systemui.media.mediaoutput.common.PreferenceDebugLabsKeys.INSTANCE
                        r6.getClass()
                        androidx.datastore.preferences.core.Preferences$Key r6 = com.android.systemui.media.mediaoutput.common.PreferenceDebugLabsKeys.SUPPORT_MULTIPLE_MEDIA_SESSION
                        java.lang.Object r5 = r5.get(r6)
                        java.lang.Boolean r5 = (java.lang.Boolean) r5
                        if (r5 == 0) goto L48
                        boolean r5 = r5.booleanValue()
                        goto L49
                    L48:
                        r5 = 0
                    L49:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L58
                        return r1
                    L58:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$3.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        this.isSupportSelectableBudsTogether = new DataStoreDebugLabsExt$special$$inlined$map$4(dataStore.getData());
        this.isSupportDisplayDeviceVolumeControl = new DataStoreDebugLabsExt$special$$inlined$map$5(dataStore.getData());
        this.isSupportTransferableRoutesWhileConnecting = new DataStoreDebugLabsExt$special$$inlined$map$6(dataStore.getData());
        this.isSupportForTransferDuringRouting = new DataStoreDebugLabsExt$special$$inlined$map$7(dataStore.getData());
        this.isSupportDisplayOnlyRemoteDevice = new DataStoreDebugLabsExt$special$$inlined$map$8(dataStore.getData());
        this.isSupportForUnsupportedTV = new DataStoreDebugLabsExt$special$$inlined$map$9(dataStore.getData());
        this.isQuickboardInstalled = new SafeFlow(new LabsViewModel$isQuickboardInstalled$1(this, null));
        final Flow data7 = dataStore.getData();
        this.isActionOpenOutputSwitcher = new Flow() { // from class: com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$10

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$10$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$10$2$1, reason: invalid class name */
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

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$10.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$10$2$1 r0 = (com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$10.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$10$2$1 r0 = new com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$10$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L58
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        androidx.datastore.preferences.core.Preferences r5 = (androidx.datastore.preferences.core.Preferences) r5
                        com.android.systemui.media.mediaoutput.common.PreferenceDebugLabsKeys r6 = com.android.systemui.media.mediaoutput.common.PreferenceDebugLabsKeys.INSTANCE
                        r6.getClass()
                        androidx.datastore.preferences.core.Preferences$Key r6 = com.android.systemui.media.mediaoutput.common.PreferenceDebugLabsKeys.ACTION_OPEN_OUTPUT_SWITCHER
                        java.lang.Object r5 = r5.get(r6)
                        java.lang.Boolean r5 = (java.lang.Boolean) r5
                        if (r5 == 0) goto L48
                        boolean r5 = r5.booleanValue()
                        goto L49
                    L48:
                        r5 = 0
                    L49:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L58
                        return r1
                    L58:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$10.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        Log.d("LabsViewModel", "init()");
        BuildersKt.launch$default(androidx.lifecycle.ViewModelKt.getViewModelScope(this), null, null, new AnonymousClass1(null), 3);
    }

    @Override // androidx.lifecycle.ViewModel
    public final void onCleared() {
        Log.d("LabsViewModel", "onCleared()");
    }

    public final void openActivity(boolean z) {
        BuildersKt.launch$default(androidx.lifecycle.ViewModelKt.getViewModelScope(this), null, null, new LabsViewModel$openActivity$1(this, z, null), 3);
    }

    public final void setActionOpenOutputSwitcher(boolean z) {
        Log.d("LabsViewModel", "setActionOpenOutputSwitcher() - " + z);
        BuildersKt.launch$default(androidx.lifecycle.ViewModelKt.getViewModelScope(this), null, null, new LabsViewModel$setActionOpenOutputSwitcher$1(this, z, null), 3);
    }

    public final void setCloseOnTouchOutside(boolean z) {
        Log.d("LabsViewModel", "setCloseOnTouchOutside() - " + z);
        BuildersKt.launch$default(androidx.lifecycle.ViewModelKt.getViewModelScope(this), null, null, new LabsViewModel$setCloseOnTouchOutside$1(this, z, null), 3);
    }

    public final void setGrayscaleThumbnail(boolean z) {
        Log.d("LabsViewModel", "setGrayscaleThumbnail() - " + z);
        BuildersKt.launch$default(androidx.lifecycle.ViewModelKt.getViewModelScope(this), null, null, new LabsViewModel$setGrayscaleThumbnail$1(this, z, null), 3);
    }

    public final void setGroupSpeakerDefaultExpanded(boolean z) {
        Log.d("LabsViewModel", "setGroupSpeakerDefaultExpanded() - " + z);
        BuildersKt.launch$default(androidx.lifecycle.ViewModelKt.getViewModelScope(this), null, null, new LabsViewModel$setGroupSpeakerDefaultExpanded$1(this, z, null), 3);
    }

    public final void setShowLabsMenu(boolean z) {
        Log.d("LabsViewModel", "setShowLabsMenu()");
        BuildersKt.launch$default(androidx.lifecycle.ViewModelKt.getViewModelScope(this), null, null, new LabsViewModel$setShowLabsMenu$1(this, z, null), 3);
    }

    public final void setSupportDisplayDeviceVolumeControl(boolean z) {
        Log.d("LabsViewModel", "setSupportDisplayDeviceVolumeControl() - " + z);
        BuildersKt.launch$default(androidx.lifecycle.ViewModelKt.getViewModelScope(this), null, null, new LabsViewModel$setSupportDisplayDeviceVolumeControl$1(this, z, null), 3);
    }

    public final void setSupportDisplayOnlyRemoteDevice(boolean z) {
        Log.d("LabsViewModel", "setSupportDisplayOnlyRemoteDevice() - " + z);
        BuildersKt.launch$default(androidx.lifecycle.ViewModelKt.getViewModelScope(this), null, null, new LabsViewModel$setSupportDisplayOnlyRemoteDevice$1(this, z, null), 3);
    }

    public final void setSupportForTransferDuringRouting(boolean z) {
        Log.d("LabsViewModel", "setSupportForTransferDuringRouting() - " + z);
        BuildersKt.launch$default(androidx.lifecycle.ViewModelKt.getViewModelScope(this), null, null, new LabsViewModel$setSupportForTransferDuringRouting$1(this, z, null), 3);
    }

    public final void setSupportForUnsupportedTV(boolean z) {
        Log.d("LabsViewModel", "setCastingPriority() - " + z);
        BuildersKt.launch$default(androidx.lifecycle.ViewModelKt.getViewModelScope(this), null, null, new LabsViewModel$setSupportForUnsupportedTV$1(this, z, null), 3);
    }

    public final void setSupportMultipleMediaSession(boolean z) {
        Log.d("LabsViewModel", "setSupportMultipleMediaSession() - " + z);
        BuildersKt.launch$default(androidx.lifecycle.ViewModelKt.getViewModelScope(this), null, null, new LabsViewModel$setSupportMultipleMediaSession$1(this, z, null), 3);
    }

    public final void setSupportRecentGroupSpeaker(boolean z) {
        Log.d("LabsViewModel", "setGroupSpeakerDefaultExpandedsetSupportRecentGroupSpeaker() - " + z);
        BuildersKt.launch$default(androidx.lifecycle.ViewModelKt.getViewModelScope(this), null, null, new LabsViewModel$setSupportRecentGroupSpeaker$1(this, z, null), 3);
    }

    public final void setSupportSelectableBudsTogether(boolean z) {
        Log.d("LabsViewModel", "setSupportSelectableBudsTogether() - " + z);
        BuildersKt.launch$default(androidx.lifecycle.ViewModelKt.getViewModelScope(this), null, null, new LabsViewModel$setSupportSelectableBudsTogether$1(this, z, null), 3);
    }

    public final void setSupportTransferableRoutesWhileConnecting(boolean z) {
        Log.d("LabsViewModel", "setSupportTransferableRoutesWhileConnecting() - " + z);
        BuildersKt.launch$default(androidx.lifecycle.ViewModelKt.getViewModelScope(this), null, null, new LabsViewModel$setSupportTransferableRoutesWhileConnecting$1(this, z, null), 3);
    }

    public final void setSupportVolumeInteraction(boolean z) {
        Log.d("LabsViewModel", "setSupportVolumeInteraction() - " + z);
        BuildersKt.launch$default(androidx.lifecycle.ViewModelKt.getViewModelScope(this), null, null, new LabsViewModel$setSupportVolumeInteraction$1(this, z, null), 3);
    }
}
