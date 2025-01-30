package com.android.systemui.statusbar.notification.stack;

import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.stack.NotificationStackSizeCalculator;
import com.android.systemui.util.ConvenienceExtensionsKt;
import java.util.Iterator;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$FloatRef;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlin.reflect.KProperty;
import kotlin.sequences.SequenceScope;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlin.sequences.TransformingSequence;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.statusbar.notification.stack.NotificationStackSizeCalculator$computeHeightPerNotificationLimit$1", m277f = "NotificationStackSizeCalculator.kt", m278l = {322, 357}, m279m = "invokeSuspend")
/* renamed from: com.android.systemui.statusbar.notification.stack.NotificationStackSizeCalculator$computeHeightPerNotificationLimit$1 */
/* loaded from: classes2.dex */
final class C2969xf807607a extends RestrictedSuspendLambda implements Function2 {
    final /* synthetic */ float $shelfHeight;
    final /* synthetic */ NotificationStackScrollLayout $stack;
    float F$0;
    int I$0;
    private /* synthetic */ Object L$0;
    Object L$1;
    Object L$2;
    Object L$3;
    Object L$4;
    Object L$5;
    Object L$6;
    Object L$7;
    boolean Z$0;
    int label;
    final /* synthetic */ NotificationStackSizeCalculator this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C2969xf807607a(NotificationStackSizeCalculator notificationStackSizeCalculator, NotificationStackScrollLayout notificationStackScrollLayout, float f, Continuation<? super C2969xf807607a> continuation) {
        super(2, continuation);
        this.this$0 = notificationStackSizeCalculator;
        this.$stack = notificationStackScrollLayout;
        this.$shelfHeight = f;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        C2969xf807607a c2969xf807607a = new C2969xf807607a(this.this$0, this.$stack, this.$shelfHeight, continuation);
        c2969xf807607a.L$0 = obj;
        return c2969xf807607a;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((C2969xf807607a) create((SequenceScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:22:0x016c  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x00de  */
    /* JADX WARN: Type inference failed for: r6v7, types: [T, com.android.systemui.statusbar.notification.row.ExpandableView] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:18:0x015b -> B:6:0x0161). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        SequenceScope sequenceScope;
        List list;
        Ref$FloatRef ref$FloatRef;
        Ref$FloatRef ref$FloatRef2;
        Ref$ObjectRef ref$ObjectRef;
        boolean z;
        Iterator it;
        Ref$ObjectRef ref$ObjectRef2;
        float f;
        NotificationStackScrollLayout notificationStackScrollLayout;
        Ref$FloatRef ref$FloatRef3;
        Ref$FloatRef ref$FloatRef4;
        SequenceScope sequenceScope2;
        List list2;
        int i;
        NotificationStackSizeCalculator notificationStackSizeCalculator;
        List list3;
        float calculateGapAndDividerHeight;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = this.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            sequenceScope = (SequenceScope) this.L$0;
            NotificationStackSizeCalculator notificationStackSizeCalculator2 = this.this$0;
            NotificationStackScrollLayout notificationStackScrollLayout2 = this.$stack;
            KProperty[] kPropertyArr = NotificationStackSizeCalculator.$$delegatedProperties;
            notificationStackSizeCalculator2.getClass();
            list = SequencesKt___SequencesKt.toList(SequencesKt___SequencesKt.filter(new TransformingSequence(ConvenienceExtensionsKt.getChildren(notificationStackScrollLayout2), NotificationStackSizeCalculator$childrenSequence$1.INSTANCE), new NotificationStackSizeCalculator$showableChildren$1(notificationStackSizeCalculator2)));
            ref$FloatRef = new Ref$FloatRef();
            ref$FloatRef2 = new Ref$FloatRef();
            Ref$ObjectRef ref$ObjectRef3 = new Ref$ObjectRef();
            boolean onLockscreen = this.this$0.onLockscreen();
            NotificationStackSizeCalculator.StackHeight stackHeight = new NotificationStackSizeCalculator.StackHeight(0.0f, 0.0f, this.$shelfHeight);
            this.L$0 = sequenceScope;
            this.L$1 = list;
            this.L$2 = ref$FloatRef;
            this.L$3 = ref$FloatRef2;
            this.L$4 = ref$ObjectRef3;
            this.Z$0 = onLockscreen;
            this.label = 1;
            if (sequenceScope.yield(stackHeight, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
            ref$ObjectRef = ref$ObjectRef3;
            z = onLockscreen;
        } else if (i2 == 1) {
            z = this.Z$0;
            ref$ObjectRef = (Ref$ObjectRef) this.L$4;
            ref$FloatRef2 = (Ref$FloatRef) this.L$3;
            ref$FloatRef = (Ref$FloatRef) this.L$2;
            list = (List) this.L$1;
            sequenceScope = (SequenceScope) this.L$0;
            ResultKt.throwOnFailure(obj);
        } else {
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            int i3 = this.I$0;
            f = this.F$0;
            boolean z2 = this.Z$0;
            Iterator it2 = (Iterator) this.L$7;
            NotificationStackScrollLayout notificationStackScrollLayout3 = (NotificationStackScrollLayout) this.L$6;
            NotificationStackSizeCalculator notificationStackSizeCalculator3 = (NotificationStackSizeCalculator) this.L$5;
            Ref$ObjectRef ref$ObjectRef4 = (Ref$ObjectRef) this.L$4;
            ref$FloatRef3 = (Ref$FloatRef) this.L$3;
            Ref$FloatRef ref$FloatRef5 = (Ref$FloatRef) this.L$2;
            List list4 = (List) this.L$1;
            SequenceScope sequenceScope3 = (SequenceScope) this.L$0;
            ResultKt.throwOnFailure(obj);
            ref$ObjectRef2 = ref$ObjectRef4;
            ref$FloatRef4 = ref$FloatRef5;
            notificationStackScrollLayout = notificationStackScrollLayout3;
            i = i3;
            z = z2;
            Iterator it3 = it2;
            sequenceScope2 = sequenceScope3;
            NotificationStackSizeCalculator notificationStackSizeCalculator4 = notificationStackSizeCalculator3;
            char c = 2;
            list2 = list4;
            it = it3;
            notificationStackSizeCalculator = notificationStackSizeCalculator4;
            if (it.hasNext()) {
                Object next = it.next();
                int i4 = i + 1;
                if (i < 0) {
                    CollectionsKt__CollectionsKt.throwIndexOverflow();
                    throw null;
                }
                ?? r6 = (ExpandableView) next;
                CoroutineSingletons coroutineSingletons2 = coroutineSingletons;
                SequenceScope sequenceScope4 = sequenceScope2;
                int i5 = i;
                List list5 = list2;
                Ref$FloatRef ref$FloatRef6 = ref$FloatRef4;
                Iterator it4 = it;
                Ref$FloatRef ref$FloatRef7 = ref$FloatRef3;
                NotificationStackSizeCalculator.SpaceNeeded spaceNeeded = notificationStackSizeCalculator.getSpaceNeeded(r6, i, (ExpandableView) ref$ObjectRef2.element, notificationStackScrollLayout, z);
                ref$FloatRef6.element += spaceNeeded.whenEnoughSpace;
                ref$FloatRef7.element += spaceNeeded.whenSavingSpace;
                ref$ObjectRef2.element = r6;
                if (i5 == CollectionsKt__CollectionsKt.getLastIndex(list5)) {
                    list3 = list5;
                    calculateGapAndDividerHeight = 0.0f;
                } else {
                    list3 = list5;
                    calculateGapAndDividerHeight = notificationStackSizeCalculator.calculateGapAndDividerHeight(notificationStackScrollLayout, r6, (ExpandableView) list3.get(i4), i4) + f;
                }
                NotificationStackSizeCalculator.StackHeight stackHeight2 = new NotificationStackSizeCalculator.StackHeight(ref$FloatRef6.element, ref$FloatRef7.element, calculateGapAndDividerHeight);
                this.L$0 = sequenceScope4;
                this.L$1 = list3;
                this.L$2 = ref$FloatRef6;
                this.L$3 = ref$FloatRef7;
                this.L$4 = ref$ObjectRef2;
                this.L$5 = notificationStackSizeCalculator;
                this.L$6 = notificationStackScrollLayout;
                it3 = it4;
                this.L$7 = it3;
                this.Z$0 = z;
                this.F$0 = f;
                this.I$0 = i4;
                c = 2;
                this.label = 2;
                if (sequenceScope4.yield(stackHeight2, this) == coroutineSingletons2) {
                    return coroutineSingletons2;
                }
                sequenceScope2 = sequenceScope4;
                coroutineSingletons = coroutineSingletons2;
                ref$FloatRef3 = ref$FloatRef7;
                ref$FloatRef4 = ref$FloatRef6;
                list4 = list3;
                i = i4;
                notificationStackSizeCalculator4 = notificationStackSizeCalculator;
                list2 = list4;
                it = it3;
                notificationStackSizeCalculator = notificationStackSizeCalculator4;
                if (it.hasNext()) {
                    return Unit.INSTANCE;
                }
            }
        }
        NotificationStackSizeCalculator notificationStackSizeCalculator5 = this.this$0;
        NotificationStackScrollLayout notificationStackScrollLayout4 = this.$stack;
        float f2 = this.$shelfHeight;
        it = list.iterator();
        ref$ObjectRef2 = ref$ObjectRef;
        f = f2;
        notificationStackScrollLayout = notificationStackScrollLayout4;
        ref$FloatRef3 = ref$FloatRef2;
        ref$FloatRef4 = ref$FloatRef;
        sequenceScope2 = sequenceScope;
        list2 = list;
        i = 0;
        notificationStackSizeCalculator = notificationStackSizeCalculator5;
        if (it.hasNext()) {
        }
    }
}
