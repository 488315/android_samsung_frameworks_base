package com.android.systemui.statusbar.notification.collection.coordinator;

import android.R;
import android.view.View;
import android.widget.TextView;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.PipelineEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeRenderListListener;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.NotificationContentView;
import java.util.Arrays;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.ArrayIterator;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.sequences.FilteringSequence;
import kotlin.sequences.FilteringSequence.AnonymousClass1;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequenceScope;
import kotlin.sequences.SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1;
import kotlin.sequences.SequencesKt___SequencesKt;

/* loaded from: classes3.dex */
public final class NotifHeaderCoordinator implements Coordinator {
    public static final int $stable = 8;
    private final NotificationLockscreenUserManager lockscreenUserManager;

    /* renamed from: com.android.systemui.statusbar.notification.collection.coordinator.NotifHeaderCoordinator$extractAllRepresentativeEntries$1, reason: invalid class name and case insensitive filesystem */
    final /* synthetic */ class C10721 extends FunctionReferenceImpl implements Function1 {
        public C10721(Object obj) {
            super(1, obj, NotifHeaderCoordinator.class, "extractAllRepresentativeEntries", "extractAllRepresentativeEntries(Lcom/android/systemui/statusbar/notification/collection/PipelineEntry;)Lkotlin/sequences/Sequence;", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke, reason: merged with bridge method [inline-methods] */
        public final Sequence mo781invoke(PipelineEntry pipelineEntry) {
            return ((NotifHeaderCoordinator) this.receiver).extractAllRepresentativeEntries(pipelineEntry);
        }
    }

    /* renamed from: com.android.systemui.statusbar.notification.collection.coordinator.NotifHeaderCoordinator$extractAllRepresentativeEntries$2, reason: invalid class name */
    final class AnonymousClass2 extends RestrictedSuspendLambda implements Function2 {
        final /* synthetic */ PipelineEntry $entry;
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ NotifHeaderCoordinator this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(PipelineEntry pipelineEntry, NotifHeaderCoordinator notifHeaderCoordinator, Continuation continuation) {
            super(2, continuation);
            this.$entry = pipelineEntry;
            this.this$0 = notifHeaderCoordinator;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$entry, this.this$0, continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        /* JADX WARN: Code restructure failed: missing block: B:21:0x0060, code lost:
        
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
                NotificationEntry representativeEntry = this.$entry.getRepresentativeEntry();
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
            PipelineEntry pipelineEntry = this.$entry;
            if (pipelineEntry instanceof GroupEntry) {
                Sequence sequenceExtractAllRepresentativeEntries = this.this$0.extractAllRepresentativeEntries((List<? extends PipelineEntry>) ((GroupEntry) pipelineEntry).mUnmodifiableChildren);
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

    public NotifHeaderCoordinator(NotificationLockscreenUserManager notificationLockscreenUserManager) {
        this.lockscreenUserManager = notificationLockscreenUserManager;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Sequence extractAllRepresentativeEntries(List<? extends PipelineEntry> list) {
        return SequencesKt___SequencesKt.flatMap(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(list), new C10721(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onBeforeRenderListListener(List<? extends PipelineEntry> list) {
        FilteringSequence.AnonymousClass1 anonymousClass1 = SequencesKt___SequencesKt.filter(extractAllRepresentativeEntries(list), new NotifHeaderCoordinator$$ExternalSyntheticLambda0()).new AnonymousClass1();
        while (anonymousClass1.hasNext()) {
            NotificationEntry notificationEntry = (NotificationEntry) anonymousClass1.next();
            NotificationLockscreenUserManagerImpl notificationLockscreenUserManagerImpl = (NotificationLockscreenUserManagerImpl) this.lockscreenUserManager;
            boolean zIsLockscreenPublicMode = notificationLockscreenUserManagerImpl.isLockscreenPublicMode(notificationLockscreenUserManagerImpl.mCurrentUserId);
            ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
            expandableNotificationRow.getClass();
            NotificationContentView[] notificationContentViewArr = expandableNotificationRow.mLayouts;
            ArrayIterator arrayIterator = new ArrayIterator((NotificationContentView[]) Arrays.copyOf(notificationContentViewArr, notificationContentViewArr.length));
            while (arrayIterator.hasNext()) {
                NotificationContentView notificationContentView = (NotificationContentView) arrayIterator.next();
                if (notificationContentView.mIsContractedHeaderContainAtMark) {
                    View view = notificationContentView.mContractedChild;
                    TextView textView = view != null ? (TextView) view.findViewById(R.id.inter_word) : null;
                    if (textView != null) {
                        textView.setVisibility(zIsLockscreenPublicMode ? 8 : 0);
                    }
                }
                if (notificationContentView.mIsExpandedHeaderContainAtMark) {
                    View view2 = notificationContentView.mExpandedChild;
                    TextView textView2 = view2 != null ? (TextView) view2.findViewById(R.id.inter_word) : null;
                    if (textView2 != null) {
                        textView2.setVisibility(zIsLockscreenPublicMode ? 8 : 0);
                    }
                }
            }
        }
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        notifPipeline.addOnBeforeRenderListListener(new OnBeforeRenderListListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.NotifHeaderCoordinator.attach.1
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeRenderListListener
            public final void onBeforeRenderList(List<? extends PipelineEntry> list) {
                NotifHeaderCoordinator.this.onBeforeRenderListListener(list);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Sequence extractAllRepresentativeEntries(PipelineEntry pipelineEntry) {
        return new SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1(new AnonymousClass2(pipelineEntry, this, null));
    }
}
