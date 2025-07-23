package com.android.systemui.qs.pipeline.domain.autoaddable;

import android.content.ComponentName;
import com.android.systemui.accessibility.data.repository.AccessibilityQsShortcutsRepository;
import com.android.systemui.accessibility.data.repository.AccessibilityQsShortcutsRepositoryImpl;
import com.android.systemui.accessibility.data.repository.UserA11yQsShortcutsRepository;
import com.android.systemui.qs.pipeline.domain.model.AutoAddTracking;
import com.android.systemui.qs.pipeline.domain.model.AutoAddable;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import java.util.Objects;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlySharedFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class A11yShortcutAutoAddable implements AutoAddable {
    public final AccessibilityQsShortcutsRepository a11yQsShortcutsRepository;
    public final AutoAddTracking.Always autoAddTracking;
    public final CoroutineDispatcher bgDispatcher;
    public final ComponentName componentName;
    public final String description;
    public final TileSpec spec;

    public A11yShortcutAutoAddable(AccessibilityQsShortcutsRepository accessibilityQsShortcutsRepository, CoroutineDispatcher coroutineDispatcher, TileSpec tileSpec, ComponentName componentName) {
        this.a11yQsShortcutsRepository = accessibilityQsShortcutsRepository;
        this.bgDispatcher = coroutineDispatcher;
        this.spec = tileSpec;
        this.componentName = componentName;
        AutoAddTracking.Always always = AutoAddTracking.Always.INSTANCE;
        this.autoAddTracking = always;
        this.description = "A11yShortcutAutoAddableSetting: " + tileSpec + ":" + componentName + " (" + always + ")";
    }

    @Override // com.android.systemui.qs.pipeline.domain.model.AutoAddable
    public final Flow autoAddSignal(int i) {
        final ReadonlySharedFlow readonlySharedFlow;
        AccessibilityQsShortcutsRepositoryImpl accessibilityQsShortcutsRepositoryImpl = (AccessibilityQsShortcutsRepositoryImpl) this.a11yQsShortcutsRepository;
        synchronized (accessibilityQsShortcutsRepositoryImpl.userA11yQsShortcutsRepositories) {
            try {
                if (!accessibilityQsShortcutsRepositoryImpl.userA11yQsShortcutsRepositories.contains(i)) {
                    accessibilityQsShortcutsRepositoryImpl.userA11yQsShortcutsRepositories.put(i, accessibilityQsShortcutsRepositoryImpl.userA11yQsShortcutsRepositoryFactory.create(i));
                }
                readonlySharedFlow = ((UserA11yQsShortcutsRepository) accessibilityQsShortcutsRepositoryImpl.userA11yQsShortcutsRepositories.get(i)).targets;
            } catch (Throwable th) {
                throw th;
            }
        }
        final Flow distinctUntilChanged = FlowKt.distinctUntilChanged(new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(new Flow() { // from class: com.android.systemui.qs.pipeline.domain.autoaddable.A11yShortcutAutoAddable$autoAddSignal$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.qs.pipeline.domain.autoaddable.A11yShortcutAutoAddable$autoAddSignal$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ A11yShortcutAutoAddable this$0;

                /* renamed from: com.android.systemui.qs.pipeline.domain.autoaddable.A11yShortcutAutoAddable$autoAddSignal$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, A11yShortcutAutoAddable a11yShortcutAutoAddable) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = a11yShortcutAutoAddable;
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
                        boolean r0 = r6 instanceof com.android.systemui.qs.pipeline.domain.autoaddable.A11yShortcutAutoAddable$autoAddSignal$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.qs.pipeline.domain.autoaddable.A11yShortcutAutoAddable$autoAddSignal$$inlined$map$1$2$1 r0 = (com.android.systemui.qs.pipeline.domain.autoaddable.A11yShortcutAutoAddable$autoAddSignal$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.qs.pipeline.domain.autoaddable.A11yShortcutAutoAddable$autoAddSignal$$inlined$map$1$2$1 r0 = new com.android.systemui.qs.pipeline.domain.autoaddable.A11yShortcutAutoAddable$autoAddSignal$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L4f
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.util.Set r5 = (java.util.Set) r5
                        com.android.systemui.qs.pipeline.domain.autoaddable.A11yShortcutAutoAddable r6 = r4.this$0
                        android.content.ComponentName r6 = r6.componentName
                        java.lang.String r6 = r6.flattenToString()
                        boolean r5 = r5.contains(r6)
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4f
                        return r1
                    L4f:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.pipeline.domain.autoaddable.A11yShortcutAutoAddable$autoAddSignal$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }));
        return FlowKt.flowOn(new Flow() { // from class: com.android.systemui.qs.pipeline.domain.autoaddable.A11yShortcutAutoAddable$autoAddSignal$$inlined$map$2

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.qs.pipeline.domain.autoaddable.A11yShortcutAutoAddable$autoAddSignal$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ A11yShortcutAutoAddable this$0;

                /* renamed from: com.android.systemui.qs.pipeline.domain.autoaddable.A11yShortcutAutoAddable$autoAddSignal$$inlined$map$2$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, A11yShortcutAutoAddable a11yShortcutAutoAddable) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = a11yShortcutAutoAddable;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r7, kotlin.coroutines.Continuation r8) {
                    /*
                        r6 = this;
                        boolean r0 = r8 instanceof com.android.systemui.qs.pipeline.domain.autoaddable.A11yShortcutAutoAddable$autoAddSignal$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r8
                        com.android.systemui.qs.pipeline.domain.autoaddable.A11yShortcutAutoAddable$autoAddSignal$$inlined$map$2$2$1 r0 = (com.android.systemui.qs.pipeline.domain.autoaddable.A11yShortcutAutoAddable$autoAddSignal$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.qs.pipeline.domain.autoaddable.A11yShortcutAutoAddable$autoAddSignal$$inlined$map$2$2$1 r0 = new com.android.systemui.qs.pipeline.domain.autoaddable.A11yShortcutAutoAddable$autoAddSignal$$inlined$map$2$2$1
                        r0.<init>(r8)
                    L18:
                        java.lang.Object r8 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r8)
                        goto L59
                    L27:
                        java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                        java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                        r6.<init>(r7)
                        throw r6
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r8)
                        java.lang.Boolean r7 = (java.lang.Boolean) r7
                        boolean r7 = r7.booleanValue()
                        com.android.systemui.qs.pipeline.domain.autoaddable.A11yShortcutAutoAddable r8 = r6.this$0
                        if (r7 == 0) goto L47
                        com.android.systemui.qs.pipeline.domain.model.AutoAddSignal$Add r7 = new com.android.systemui.qs.pipeline.domain.model.AutoAddSignal$Add
                        com.android.systemui.qs.pipeline.shared.TileSpec r8 = r8.spec
                        r2 = 2
                        r4 = 0
                        r5 = 0
                        r7.<init>(r8, r5, r2, r4)
                        goto L4e
                    L47:
                        com.android.systemui.qs.pipeline.domain.model.AutoAddSignal$Remove r7 = new com.android.systemui.qs.pipeline.domain.model.AutoAddSignal$Remove
                        com.android.systemui.qs.pipeline.shared.TileSpec r8 = r8.spec
                        r7.<init>(r8)
                    L4e:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r6 = r6.$this_unsafeFlow
                        java.lang.Object r6 = r6.emit(r7, r0)
                        if (r6 != r1) goto L59
                        return r1
                    L59:
                        kotlin.Unit r6 = kotlin.Unit.INSTANCE
                        return r6
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.pipeline.domain.autoaddable.A11yShortcutAutoAddable$autoAddSignal$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, this.bgDispatcher);
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof A11yShortcutAutoAddable)) {
            return false;
        }
        A11yShortcutAutoAddable a11yShortcutAutoAddable = (A11yShortcutAutoAddable) obj;
        return Intrinsics.areEqual(this.spec, a11yShortcutAutoAddable.spec) && Intrinsics.areEqual(this.componentName, a11yShortcutAutoAddable.componentName);
    }

    @Override // com.android.systemui.qs.pipeline.domain.model.AutoAddable
    public final AutoAddTracking getAutoAddTracking() {
        return this.autoAddTracking;
    }

    @Override // com.android.systemui.qs.pipeline.domain.model.AutoAddable
    public final String getDescription() {
        return this.description;
    }

    public final int hashCode() {
        return Objects.hash(this.spec, this.componentName);
    }

    public final String toString() {
        return this.description;
    }
}
