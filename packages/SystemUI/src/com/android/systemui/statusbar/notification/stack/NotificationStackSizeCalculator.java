package com.android.systemui.statusbar.notification.stack;

import android.content.res.Resources;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.foundation.shape.DpCornerSize$$ExternalSyntheticOutline0;
import androidx.compose.ui.semantics.SemanticsPropertiesKt$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.domain.interactor.SeenNotificationsInteractor;
import com.android.systemui.statusbar.notification.promoted.PromotedNotificationUi;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.policy.SplitShadeStateController;
import com.android.systemui.util.ConvenienceExtensionsKt;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.util.Iterator;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.Ref$FloatRef;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.ReflectionFactory;
import kotlin.properties.Delegates;
import kotlin.properties.NotNullVar;
import kotlin.reflect.KProperty;
import kotlin.sequences.SequenceScope;
import kotlin.sequences.SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlin.sequences.TransformingSequence;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes3.dex */
public final class NotificationStackSizeCalculator {
    public static final /* synthetic */ KProperty[] $$delegatedProperties;
    public final NotNullVar dividerHeight$delegate;
    public final LockscreenShadeTransitionController lockscreenShadeTransitionController;
    public final NotNullVar maxGroupExpandedBottomGap$delegate;
    public final NotNullVar maxKeyguardNotifications$delegate;
    public final MediaDataManager mediaDataManager;
    public final Resources resources;
    public boolean saveSpaceOnLockscreen;
    public final SplitShadeStateController splitShadeStateController;
    public final SysuiStatusBarStateController statusBarStateController;

    public final class BucketTypeCounter {
        public int important;
        public int ongoing;
        public int other;

        public BucketTypeCounter() {
            this(0, 0, 0, 7, null);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof BucketTypeCounter)) {
                return false;
            }
            BucketTypeCounter bucketTypeCounter = (BucketTypeCounter) obj;
            return this.ongoing == bucketTypeCounter.ongoing && this.important == bucketTypeCounter.important && this.other == bucketTypeCounter.other;
        }

        public final int hashCode() {
            return Integer.hashCode(this.other) + ReorderTile$$ExternalSyntheticOutline0.m(this.important, Integer.hashCode(this.ongoing) * 31, 31);
        }

        public final String toString() {
            return ReorderTile$$ExternalSyntheticOutline0.m(this.other, ")", MutableObjectList$$ExternalSyntheticOutline0.m(this.ongoing, this.important, "BucketTypeCounter(ongoing=", ", important=", ", other="));
        }

        public BucketTypeCounter(int i, int i2, int i3) {
            this.ongoing = i;
            this.important = i2;
            this.other = i3;
        }

        public /* synthetic */ BucketTypeCounter(int i, int i2, int i3, int i4, DefaultConstructorMarker defaultConstructorMarker) {
            this((i4 & 1) != 0 ? 0 : i, (i4 & 2) != 0 ? 0 : i2, (i4 & 4) != 0 ? 0 : i3);
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    final class FitResult {
        public static final /* synthetic */ FitResult[] $VALUES;
        public static final FitResult FIT;
        public static final FitResult FIT_IF_SAVE_SPACE;
        public static final FitResult NO_FIT;

        static {
            FitResult fitResult = new FitResult("FIT", 0);
            FIT = fitResult;
            FitResult fitResult2 = new FitResult("FIT_IF_SAVE_SPACE", 1);
            FIT_IF_SAVE_SPACE = fitResult2;
            FitResult fitResult3 = new FitResult("NO_FIT", 2);
            NO_FIT = fitResult3;
            FitResult[] fitResultArr = {fitResult, fitResult2, fitResult3};
            $VALUES = fitResultArr;
            EnumEntriesKt.enumEntries(fitResultArr);
        }

        private FitResult(String str, int i) {
        }

        public static FitResult valueOf(String str) {
            return (FitResult) Enum.valueOf(FitResult.class, str);
        }

        public static FitResult[] values() {
            return (FitResult[]) $VALUES.clone();
        }
    }

    public final class SpaceNeeded {
        public final float whenEnoughSpace;
        public final float whenSavingSpace;

        public SpaceNeeded(float f, float f2) {
            this.whenEnoughSpace = f;
            this.whenSavingSpace = f2;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof SpaceNeeded)) {
                return false;
            }
            SpaceNeeded spaceNeeded = (SpaceNeeded) obj;
            return Float.compare(this.whenEnoughSpace, spaceNeeded.whenEnoughSpace) == 0 && Float.compare(this.whenSavingSpace, spaceNeeded.whenSavingSpace) == 0;
        }

        public final int hashCode() {
            return Float.hashCode(this.whenSavingSpace) + (Float.hashCode(this.whenEnoughSpace) * 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("SpaceNeeded(whenEnoughSpace=");
            sb.append(this.whenEnoughSpace);
            sb.append(", whenSavingSpace=");
            return DpCornerSize$$ExternalSyntheticOutline0.m(this.whenSavingSpace, ")", sb);
        }
    }

    public final class StackHeight {
        public final float notifsHeight;
        public final float notifsHeightSavingSpace;
        public final float shelfHeightWithSpaceBefore;
        public final boolean shouldForceIntoShelf;

        public StackHeight(float f, float f2, float f3, boolean z) {
            this.notifsHeight = f;
            this.notifsHeightSavingSpace = f2;
            this.shelfHeightWithSpaceBefore = f3;
            this.shouldForceIntoShelf = z;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof StackHeight)) {
                return false;
            }
            StackHeight stackHeight = (StackHeight) obj;
            return Float.compare(this.notifsHeight, stackHeight.notifsHeight) == 0 && Float.compare(this.notifsHeightSavingSpace, stackHeight.notifsHeightSavingSpace) == 0 && Float.compare(this.shelfHeightWithSpaceBefore, stackHeight.shelfHeightWithSpaceBefore) == 0 && this.shouldForceIntoShelf == stackHeight.shouldForceIntoShelf;
        }

        public final int hashCode() {
            return Boolean.hashCode(this.shouldForceIntoShelf) + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.shelfHeightWithSpaceBefore, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.notifsHeightSavingSpace, Float.hashCode(this.notifsHeight) * 31, 31), 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("StackHeight(notifsHeight=");
            sb.append(this.notifsHeight);
            sb.append(", notifsHeightSavingSpace=");
            sb.append(this.notifsHeightSavingSpace);
            sb.append(", shelfHeightWithSpaceBefore=");
            sb.append(this.shelfHeightWithSpaceBefore);
            sb.append(", shouldForceIntoShelf=");
            return MoveResult$$ExternalSyntheticOutline0.m(sb, this.shouldForceIntoShelf, ")");
        }
    }

    /* renamed from: com.android.systemui.statusbar.notification.stack.NotificationStackSizeCalculator$computeHeightPerNotificationLimit$1, reason: invalid class name */
    final class AnonymousClass1 extends RestrictedSuspendLambda implements Function2 {
        final /* synthetic */ float $shelfHeight;
        final /* synthetic */ NotificationStackScrollLayout $stack;
        float F$0;
        float F$1;
        int I$0;
        private /* synthetic */ Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        Object L$5;
        Object L$6;
        Object L$7;
        Object L$8;
        boolean Z$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(NotificationStackScrollLayout notificationStackScrollLayout, float f, Continuation continuation) {
            super(2, continuation);
            this.$stack = notificationStackScrollLayout;
            this.$shelfHeight = f;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = NotificationStackSizeCalculator.this.new AnonymousClass1(this.$stack, this.$shelfHeight, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((SequenceScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:17:0x0115  */
        /* JADX WARN: Removed duplicated region for block: B:71:0x022f  */
        /* JADX WARN: Removed duplicated region for block: B:76:0x024d  */
        /* JADX WARN: Removed duplicated region for block: B:78:? A[RETURN, SYNTHETIC] */
        /* JADX WARN: Type inference failed for: r19v1, types: [T, com.android.systemui.statusbar.notification.row.ExpandableView] */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:24:0x015d -> B:25:0x0167). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:71:0x022f -> B:72:0x0235). Please report as a decompilation issue!!! */
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
            boolean zOnLockscreen;
            BucketTypeCounter bucketTypeCounter;
            Iterator it;
            Ref$ObjectRef ref$ObjectRef2;
            List list2;
            float f;
            boolean z;
            Ref$FloatRef ref$FloatRef3;
            int i;
            SequenceScope sequenceScope2;
            float f2;
            NotificationStackSizeCalculator notificationStackSizeCalculator;
            NotificationStackScrollLayout notificationStackScrollLayout;
            BucketTypeCounter bucketTypeCounter2;
            StackHeight stackHeight;
            boolean z2;
            boolean z3;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                sequenceScope = (SequenceScope) this.L$0;
                NotificationStackSizeCalculator notificationStackSizeCalculator2 = NotificationStackSizeCalculator.this;
                NotificationStackScrollLayout notificationStackScrollLayout2 = this.$stack;
                KProperty[] kPropertyArr = NotificationStackSizeCalculator.$$delegatedProperties;
                notificationStackSizeCalculator2.getClass();
                list = SequencesKt___SequencesKt.toList(SequencesKt___SequencesKt.filter(new TransformingSequence(ConvenienceExtensionsKt.getChildren(notificationStackScrollLayout2), new NotificationStackSizeCalculator$$ExternalSyntheticLambda3()), new NotificationStackSizeCalculator$$ExternalSyntheticLambda2(notificationStackSizeCalculator2)));
                ref$FloatRef = new Ref$FloatRef();
                ref$FloatRef2 = new Ref$FloatRef();
                ref$ObjectRef = new Ref$ObjectRef();
                zOnLockscreen = NotificationStackSizeCalculator.this.onLockscreen();
                NotificationStackSizeCalculator.this.getClass();
                StackHeight stackHeight2 = new StackHeight(0.0f, 0.0f, this.$shelfHeight, false);
                this.L$0 = sequenceScope;
                this.L$1 = list;
                this.L$2 = ref$FloatRef;
                this.L$3 = ref$FloatRef2;
                this.L$4 = ref$ObjectRef;
                this.L$5 = null;
                this.Z$0 = zOnLockscreen;
                this.label = 1;
                if (sequenceScope.yield(stackHeight2, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                bucketTypeCounter = null;
            } else if (i2 == 1) {
                zOnLockscreen = this.Z$0;
                bucketTypeCounter = (BucketTypeCounter) this.L$5;
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
                f = this.F$1;
                f2 = this.F$0;
                boolean z4 = this.Z$0;
                Iterator it2 = (Iterator) this.L$8;
                NotificationStackScrollLayout notificationStackScrollLayout3 = (NotificationStackScrollLayout) this.L$7;
                NotificationStackSizeCalculator notificationStackSizeCalculator3 = (NotificationStackSizeCalculator) this.L$6;
                bucketTypeCounter2 = (BucketTypeCounter) this.L$5;
                ref$ObjectRef2 = (Ref$ObjectRef) this.L$4;
                ref$FloatRef2 = (Ref$FloatRef) this.L$3;
                ref$FloatRef3 = (Ref$FloatRef) this.L$2;
                list2 = (List) this.L$1;
                sequenceScope2 = (SequenceScope) this.L$0;
                ResultKt.throwOnFailure(obj);
                NotificationStackScrollLayout notificationStackScrollLayout4 = notificationStackScrollLayout3;
                CoroutineSingletons coroutineSingletons2 = coroutineSingletons;
                NotificationStackSizeCalculator notificationStackSizeCalculator4 = notificationStackSizeCalculator3;
                it = it2;
                NotificationStackSizeCalculator notificationStackSizeCalculator5 = notificationStackSizeCalculator4;
                i = i3;
                z = z4;
                notificationStackScrollLayout = notificationStackScrollLayout4;
                notificationStackSizeCalculator = notificationStackSizeCalculator5;
                coroutineSingletons = coroutineSingletons2;
                if (!it.hasNext()) {
                    Object next = it.next();
                    int i4 = i + 1;
                    if (i < 0) {
                        CollectionsKt__CollectionsKt.throwIndexOverflow();
                        throw null;
                    }
                    ?? r19 = (ExpandableView) next;
                    SpaceNeeded spaceNeeded = notificationStackSizeCalculator.getSpaceNeeded(r19, i, (ExpandableView) ref$ObjectRef2.element, notificationStackScrollLayout, z);
                    int i5 = i;
                    CoroutineSingletons coroutineSingletons3 = coroutineSingletons;
                    float f3 = f;
                    float f4 = f2;
                    NotificationStackSizeCalculator notificationStackSizeCalculator6 = notificationStackSizeCalculator;
                    notificationStackScrollLayout4 = notificationStackScrollLayout;
                    boolean z5 = z;
                    ref$FloatRef3.element += spaceNeeded.whenEnoughSpace;
                    ref$FloatRef2.element += spaceNeeded.whenSavingSpace;
                    ref$ObjectRef2.element = r19;
                    if (!z5 || f4 - f3 >= ref$FloatRef3.element) {
                        float fCalculateGapAndDividerHeight = i5 == CollectionsKt__CollectionsKt.getLastIndex(list2) ? 0.0f : notificationStackSizeCalculator6.calculateGapAndDividerHeight(notificationStackScrollLayout4, r19, (ExpandableView) list2.get(i4), i4) + f3;
                        if (bucketTypeCounter2 != null) {
                            ExpandableNotificationRow expandableNotificationRow = r19 instanceof ExpandableNotificationRow ? (ExpandableNotificationRow) r19 : null;
                            NotificationEntry entryLegacy = expandableNotificationRow != null ? expandableNotificationRow.getEntryLegacy() : null;
                            Integer num = entryLegacy != null ? new Integer(entryLegacy.mBucket) : null;
                            if (num == null) {
                                z3 = true;
                            } else {
                                z3 = true;
                                if (num.intValue() != 1) {
                                }
                            }
                            if (num != null) {
                                if (num.intValue() == 13) {
                                    bucketTypeCounter2.ongoing++;
                                } else if (num.intValue() == 7 || num.intValue() == 14) {
                                    bucketTypeCounter2.important++;
                                } else {
                                    bucketTypeCounter2.other++;
                                }
                            }
                        }
                        float f5 = ref$FloatRef3.element;
                        float f6 = ref$FloatRef2.element;
                        if (bucketTypeCounter2 != null) {
                            z2 = z5;
                            boolean z6 = bucketTypeCounter2.ongoing > 1 || bucketTypeCounter2.important > 1 || bucketTypeCounter2.other > 0;
                            stackHeight = new StackHeight(f5, f6, fCalculateGapAndDividerHeight, z6);
                            this.L$0 = sequenceScope2;
                            this.L$1 = list2;
                            this.L$2 = ref$FloatRef3;
                            this.L$3 = ref$FloatRef2;
                            this.L$4 = ref$ObjectRef2;
                            this.L$5 = bucketTypeCounter2;
                            this.L$6 = notificationStackSizeCalculator6;
                            this.L$7 = notificationStackScrollLayout4;
                            this.L$8 = it;
                            boolean z7 = z2;
                            this.Z$0 = z7;
                            this.F$0 = f4;
                            f = f3;
                            this.F$1 = f;
                            this.I$0 = i4;
                            this.label = 2;
                            coroutineSingletons2 = coroutineSingletons3;
                            if (sequenceScope2.yield(stackHeight, this) != coroutineSingletons2) {
                                return coroutineSingletons2;
                            }
                            Iterator it3 = it;
                            notificationStackSizeCalculator3 = notificationStackSizeCalculator6;
                            it2 = it3;
                            i3 = i4;
                            f2 = f4;
                            z4 = z7;
                            NotificationStackSizeCalculator notificationStackSizeCalculator42 = notificationStackSizeCalculator3;
                            it = it2;
                            NotificationStackSizeCalculator notificationStackSizeCalculator52 = notificationStackSizeCalculator42;
                            i = i3;
                            z = z4;
                            notificationStackScrollLayout = notificationStackScrollLayout4;
                            notificationStackSizeCalculator = notificationStackSizeCalculator52;
                            coroutineSingletons = coroutineSingletons2;
                            if (!it.hasNext()) {
                                return Unit.INSTANCE;
                            }
                        } else {
                            z2 = z5;
                        }
                        stackHeight = new StackHeight(f5, f6, fCalculateGapAndDividerHeight, z6);
                        this.L$0 = sequenceScope2;
                        this.L$1 = list2;
                        this.L$2 = ref$FloatRef3;
                        this.L$3 = ref$FloatRef2;
                        this.L$4 = ref$ObjectRef2;
                        this.L$5 = bucketTypeCounter2;
                        this.L$6 = notificationStackSizeCalculator6;
                        this.L$7 = notificationStackScrollLayout4;
                        this.L$8 = it;
                        boolean z72 = z2;
                        this.Z$0 = z72;
                        this.F$0 = f4;
                        f = f3;
                        this.F$1 = f;
                        this.I$0 = i4;
                        this.label = 2;
                        coroutineSingletons2 = coroutineSingletons3;
                        if (sequenceScope2.yield(stackHeight, this) != coroutineSingletons2) {
                        }
                    } else {
                        f = f3;
                        z = z5;
                        coroutineSingletons2 = coroutineSingletons3;
                        f2 = f4;
                        i = i4;
                        notificationStackSizeCalculator52 = notificationStackSizeCalculator6;
                        notificationStackScrollLayout = notificationStackScrollLayout4;
                        notificationStackSizeCalculator = notificationStackSizeCalculator52;
                        coroutineSingletons = coroutineSingletons2;
                        if (!it.hasNext()) {
                        }
                    }
                }
            }
            float topPadding = (((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).getRealSize().y - this.$stack.getTopPadding()) - this.$stack.mKeyguardBottomPadding;
            NotificationStackSizeCalculator notificationStackSizeCalculator7 = NotificationStackSizeCalculator.this;
            NotificationStackScrollLayout notificationStackScrollLayout5 = this.$stack;
            float f7 = this.$shelfHeight;
            it = list.iterator();
            ref$ObjectRef2 = ref$ObjectRef;
            list2 = list;
            f = f7;
            z = zOnLockscreen;
            ref$FloatRef3 = ref$FloatRef;
            i = 0;
            sequenceScope2 = sequenceScope;
            f2 = topPadding;
            notificationStackSizeCalculator = notificationStackSizeCalculator7;
            notificationStackScrollLayout = notificationStackScrollLayout5;
            bucketTypeCounter2 = bucketTypeCounter;
            if (!it.hasNext()) {
            }
        }
    }

    static {
        MutablePropertyReference1Impl mutablePropertyReference1Impl = new MutablePropertyReference1Impl(NotificationStackSizeCalculator.class, "maxKeyguardNotifications", "getMaxKeyguardNotifications()I", 0);
        ReflectionFactory reflectionFactory = Reflection.factory;
        reflectionFactory.getClass();
        MutablePropertyReference1Impl mutablePropertyReference1Impl2 = new MutablePropertyReference1Impl(NotificationStackSizeCalculator.class, "dividerHeight", "getDividerHeight()F", 0);
        reflectionFactory.getClass();
        $$delegatedProperties = new KProperty[]{mutablePropertyReference1Impl, mutablePropertyReference1Impl2, SemanticsPropertiesKt$$ExternalSyntheticOutline0.m(NotificationStackSizeCalculator.class, "maxGroupExpandedBottomGap", "getMaxGroupExpandedBottomGap()F", 0, reflectionFactory)};
    }

    public NotificationStackSizeCalculator(SysuiStatusBarStateController sysuiStatusBarStateController, LockscreenShadeTransitionController lockscreenShadeTransitionController, MediaDataManager mediaDataManager, Resources resources, SplitShadeStateController splitShadeStateController, SeenNotificationsInteractor seenNotificationsInteractor, CoroutineScope coroutineScope) {
        this.statusBarStateController = sysuiStatusBarStateController;
        this.lockscreenShadeTransitionController = lockscreenShadeTransitionController;
        this.mediaDataManager = mediaDataManager;
        this.resources = resources;
        this.splitShadeStateController = splitShadeStateController;
        Delegates.INSTANCE.getClass();
        this.maxKeyguardNotifications$delegate = new NotNullVar();
        this.dividerHeight$delegate = new NotNullVar();
        this.maxGroupExpandedBottomGap$delegate = new NotNullVar();
        updateResources();
    }

    public static FitResult canStackFitInSpace(StackHeight stackHeight, float f, float f2) {
        float f3 = stackHeight.notifsHeight;
        float f4 = stackHeight.shelfHeightWithSpaceBefore;
        float f5 = stackHeight.notifsHeightSavingSpace;
        if (f4 == 0.0f) {
            return f3 <= f ? FitResult.FIT : f5 <= f ? FitResult.FIT_IF_SAVE_SPACE : FitResult.NO_FIT;
        }
        float f6 = f + f2;
        return f3 + f4 <= f6 ? FitResult.FIT : f5 + f4 <= f6 ? FitResult.FIT_IF_SAVE_SPACE : FitResult.NO_FIT;
    }

    public static float getPreviousGroupExpandFraction(ExpandableView expandableView) {
        ExpandableNotificationRow expandableNotificationRow;
        NotificationChildrenContainer notificationChildrenContainer;
        if (!(expandableView instanceof ExpandableNotificationRow) || (notificationChildrenContainer = (expandableNotificationRow = (ExpandableNotificationRow) expandableView).mChildrenContainer) == null) {
            return 0.0f;
        }
        return expandableNotificationRow.mUserLocked ? notificationChildrenContainer.getGroupExpandFraction() : expandableNotificationRow.isGroupExpanded$1() ? 1.0f : 0.0f;
    }

    public static boolean isShowable(ExpandableView expandableView, boolean z) {
        if (expandableView.getVisibility() == 8 || expandableView.hasNoContentHeight()) {
            return false;
        }
        if (!z) {
            return true;
        }
        if (!(expandableView instanceof ExpandableNotificationRow)) {
            return (expandableView instanceof MediaContainerView) && ((MediaContainerView) expandableView).getHeight() != 0;
        }
        if (expandableView.hasNoContentHeight() || expandableView.getVisibility() == 8) {
            return false;
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0035  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0060  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0072  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final float calculateGapAndDividerHeight(NotificationStackScrollLayout notificationStackScrollLayout, ExpandableView expandableView, ExpandableView expandableView2, int i) {
        float fInterpolate;
        if (i == 0) {
            return 0.0f;
        }
        float fCalculateGapHeight = notificationStackScrollLayout.calculateGapHeight(expandableView, expandableView2);
        KProperty[] kPropertyArr = $$delegatedProperties;
        float fFloatValue = ((Number) this.dividerHeight$delegate.getValue(this, kPropertyArr[1])).floatValue() + fCalculateGapHeight;
        boolean zIsGroupExpanded$1 = expandableView != null ? expandableView.isGroupExpanded$1() : false;
        NotNullVar notNullVar = this.maxGroupExpandedBottomGap$delegate;
        if (zIsGroupExpanded$1) {
            fInterpolate = NotificationUtils.interpolate(0.0f, ((Number) notNullVar.getValue(this, kPropertyArr[2])).floatValue(), getPreviousGroupExpandFraction(expandableView)) + 0.0f;
        } else {
            if (!(expandableView != null ? expandableView.isUserGroupExpanded() : false)) {
                fInterpolate = 0.0f;
            }
        }
        if ((expandableView == null || expandableView.isGroupExpanded$1()) ? false : true) {
            if (expandableView2 != null ? expandableView2.isGroupExpanded$1() : false) {
                fInterpolate += NotificationUtils.interpolate(0.0f, ((Number) notNullVar.getValue(this, kPropertyArr[2])).floatValue(), getPreviousGroupExpandFraction(expandableView2));
            } else {
                if (expandableView2 != null ? expandableView2.isUserGroupExpanded() : false) {
                }
            }
        } else {
            if (expandableView != null ? expandableView.isUserGroupExpanded() : false) {
            }
        }
        return fFloatValue + fInterpolate;
    }

    public final SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1 computeHeightPerNotificationLimit(NotificationStackScrollLayout notificationStackScrollLayout, float f) {
        return new SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1(new AnonymousClass1(notificationStackScrollLayout, f, null));
    }

    public final SpaceNeeded getSpaceNeeded(ExpandableView expandableView, int i, ExpandableView expandableView2, NotificationStackScrollLayout notificationStackScrollLayout, boolean z) {
        float minHeight;
        isShowable(expandableView, z);
        float heightWithoutLockscreenConstraints = expandableView.getHeightWithoutLockscreenConstraints();
        float fCalculateGapAndDividerHeight = calculateGapAndDividerHeight(notificationStackScrollLayout, expandableView2, expandableView, i);
        boolean z2 = expandableView instanceof ExpandableNotificationRow;
        boolean z3 = z2 && ((ExpandableNotificationRow) expandableView).getEntryLegacy().isStickyAndNotDemoted();
        if (z) {
            if (z2) {
                if (!z3) {
                    int i2 = PromotedNotificationUi.$r8$clinit;
                }
                minHeight = heightWithoutLockscreenConstraints;
            }
            minHeight = expandableView.getMinHeight(true);
        } else {
            minHeight = heightWithoutLockscreenConstraints;
        }
        float f = minHeight + fCalculateGapAndDividerHeight;
        if (z) {
            heightWithoutLockscreenConstraints = expandableView.getMinHeight(true);
        }
        return new SpaceNeeded(f, heightWithoutLockscreenConstraints + fCalculateGapAndDividerHeight);
    }

    public final boolean onLockscreen() {
        return this.statusBarStateController.getState() == 1 && this.lockscreenShadeTransitionController.getFractionToShade() == 0.0f;
    }

    public final void updateResources() {
        int integer = this.resources.getInteger(R.integer.keyguard_max_notification_count);
        if (integer < 0) {
            integer = Integer.MAX_VALUE;
        }
        KProperty[] kPropertyArr = $$delegatedProperties;
        KProperty kProperty = kPropertyArr[0];
        this.maxKeyguardNotifications$delegate.value = Integer.valueOf(integer);
        float fMax = Math.max(1.0f, this.resources.getDimensionPixelSize(R.dimen.notification_divider_height));
        KProperty kProperty2 = kPropertyArr[1];
        this.dividerHeight$delegate.value = Float.valueOf(fMax);
        float dimension = this.resources.getDimension(R.dimen.notification_group_expanded_max_bottom_gap);
        KProperty kProperty3 = kPropertyArr[2];
        this.maxGroupExpandedBottomGap$delegate.value = Float.valueOf(dimension);
    }
}
