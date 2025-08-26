package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.PipelineEntry;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequenceScope;
import kotlin.sequences.SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1;
import kotlin.sequences.SequencesKt___SequencesKt;

/* loaded from: classes3.dex */
public final class SensitiveContentCoordinatorKt {

    /* renamed from: com.android.systemui.statusbar.notification.collection.coordinator.SensitiveContentCoordinatorKt$extractAllRepresentativeEntries$1, reason: invalid class name */
    final /* synthetic */ class AnonymousClass1 extends FunctionReferenceImpl implements Function1 {
        public static final AnonymousClass1 INSTANCE = new AnonymousClass1();

        public AnonymousClass1() {
            super(1, SensitiveContentCoordinatorKt.class, "extractAllRepresentativeEntries", "extractAllRepresentativeEntries(Lcom/android/systemui/statusbar/notification/collection/PipelineEntry;)Lkotlin/sequences/Sequence;", 1);
        }

        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke, reason: merged with bridge method [inline-methods] */
        public final Sequence mo781invoke(PipelineEntry pipelineEntry) {
            return SensitiveContentCoordinatorKt.extractAllRepresentativeEntries(pipelineEntry);
        }
    }

    /* renamed from: com.android.systemui.statusbar.notification.collection.coordinator.SensitiveContentCoordinatorKt$extractAllRepresentativeEntries$2, reason: invalid class name */
    final class AnonymousClass2 extends RestrictedSuspendLambda implements Function2 {
        final /* synthetic */ PipelineEntry $pipelineEntry;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(PipelineEntry pipelineEntry, Continuation continuation) {
            super(2, continuation);
            this.$pipelineEntry = pipelineEntry;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$pipelineEntry, continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        /* JADX WARN: Code restructure failed: missing block: B:21:0x005e, code lost:
        
            if (r4 == r0) goto L22;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) {
            SequenceScope sequenceScope;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                sequenceScope = (SequenceScope) this.L$0;
                NotificationEntry representativeEntry = this.$pipelineEntry.getRepresentativeEntry();
                if (representativeEntry != null) {
                    this.L$0 = sequenceScope;
                    this.label = 1;
                    if (sequenceScope.yield(representativeEntry, this) != coroutineSingletons) {
                    }
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    if (i != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                sequenceScope = (SequenceScope) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            PipelineEntry pipelineEntry = this.$pipelineEntry;
            if (pipelineEntry instanceof GroupEntry) {
                Sequence sequenceExtractAllRepresentativeEntries = SensitiveContentCoordinatorKt.extractAllRepresentativeEntries((List<? extends PipelineEntry>) ((GroupEntry) pipelineEntry).mUnmodifiableChildren);
                this.L$0 = null;
                this.label = 2;
                sequenceScope.getClass();
                Object objYieldAll = sequenceScope.yieldAll(sequenceExtractAllRepresentativeEntries.iterator(), this);
                if (objYieldAll != coroutineSingletons) {
                    objYieldAll = Unit.INSTANCE;
                }
            }
            return Unit.INSTANCE;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(SequenceScope sequenceScope, Continuation continuation) {
            return ((AnonymousClass2) create(sequenceScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Sequence extractAllRepresentativeEntries(List<? extends PipelineEntry> list) {
        return SequencesKt___SequencesKt.flatMap(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(list), AnonymousClass1.INSTANCE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Sequence extractAllRepresentativeEntries(PipelineEntry pipelineEntry) {
        return new SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1(new AnonymousClass2(pipelineEntry, null));
    }
}
