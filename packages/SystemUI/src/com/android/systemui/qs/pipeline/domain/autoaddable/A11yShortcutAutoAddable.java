package com.android.systemui.qs.pipeline.domain.autoaddable;

import android.content.ComponentName;
import com.android.systemui.accessibility.data.repository.AccessibilityQsShortcutsRepository;
import com.android.systemui.accessibility.data.repository.AccessibilityQsShortcutsRepositoryImpl;
import com.android.systemui.accessibility.data.repository.UserA11yQsShortcutsRepository;
import com.android.systemui.qs.pipeline.domain.model.AutoAddSignal;
import com.android.systemui.qs.pipeline.domain.model.AutoAddTracking;
import com.android.systemui.qs.pipeline.domain.model.AutoAddable;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import java.util.Objects;
import java.util.Set;
import kotlin.ResultKt;
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
        final Flow flowDistinctUntilChanged = FlowKt.distinctUntilChanged(new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(new Flow() { // from class: com.android.systemui.qs.pipeline.domain.autoaddable.A11yShortcutAutoAddable$autoAddSignal$$inlined$map$1

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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        Boolean boolValueOf = Boolean.valueOf(((Set) obj).contains(this.this$0.componentName.flattenToString()));
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(boolValueOf, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = readonlySharedFlow.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }));
        return FlowKt.flowOn(new Flow() { // from class: com.android.systemui.qs.pipeline.domain.autoaddable.A11yShortcutAutoAddable$autoAddSignal$$inlined$map$2

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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        boolean zBooleanValue = ((Boolean) obj).booleanValue();
                        A11yShortcutAutoAddable a11yShortcutAutoAddable = this.this$0;
                        Object add = zBooleanValue ? new AutoAddSignal.Add(a11yShortcutAutoAddable.spec, 0, 2, null) : new AutoAddSignal.Remove(a11yShortcutAutoAddable.spec);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(add, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = flowDistinctUntilChanged.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
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
