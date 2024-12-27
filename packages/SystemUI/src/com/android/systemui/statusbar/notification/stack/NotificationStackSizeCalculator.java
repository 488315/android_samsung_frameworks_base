package com.android.systemui.statusbar.notification.stack;

import android.content.res.Resources;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;
import androidx.compose.ui.semantics.SemanticsPropertiesKt$$ExternalSyntheticOutline0;
import com.android.systemui.Flags;
import com.android.systemui.R;
import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.stack.NotificationStackSizeCalculator;
import com.android.systemui.statusbar.policy.SplitShadeStateController;
import com.android.systemui.statusbar.policy.SplitShadeStateControllerImpl;
import com.android.systemui.util.ConvenienceExtensionsKt;
import java.util.Iterator;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.ReflectionFactory;
import kotlin.properties.Delegates;
import kotlin.properties.NotNullVar;
import kotlin.reflect.KProperty;
import kotlin.sequences.SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlin.sequences.TakeWhileSequence;
import kotlin.sequences.TakeWhileSequence$iterator$1;
import kotlin.sequences.TransformingSequence;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
            return Integer.hashCode(this.other) + KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.important, Integer.hashCode(this.ongoing) * 31, 31);
        }

        public final String toString() {
            return Anchor$$ExternalSyntheticOutline0.m(this.other, ")", RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(this.ongoing, this.important, "BucketTypeCounter(ongoing=", ", important=", ", other="));
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
    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
            return "SpaceNeeded(whenEnoughSpace=" + this.whenEnoughSpace + ", whenSavingSpace=" + this.whenSavingSpace + ")";
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
            return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(sb, this.shouldForceIntoShelf, ")");
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

    public NotificationStackSizeCalculator(SysuiStatusBarStateController sysuiStatusBarStateController, LockscreenShadeTransitionController lockscreenShadeTransitionController, MediaDataManager mediaDataManager, Resources resources, SplitShadeStateController splitShadeStateController) {
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

    public static final FitResult access$canStackFitInSpace(NotificationStackSizeCalculator notificationStackSizeCalculator, StackHeight stackHeight, float f, float f2) {
        notificationStackSizeCalculator.getClass();
        float f3 = stackHeight.notifsHeight;
        float f4 = stackHeight.shelfHeightWithSpaceBefore;
        float f5 = stackHeight.notifsHeightSavingSpace;
        if (f4 == 0.0f) {
            return f3 <= f ? FitResult.FIT : f5 <= f ? FitResult.FIT_IF_SAVE_SPACE : FitResult.NO_FIT;
        }
        float f6 = f + f2;
        return f3 + f4 <= f6 ? FitResult.FIT : f5 + f4 <= f6 ? FitResult.FIT_IF_SAVE_SPACE : FitResult.NO_FIT;
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

    public final float calculateGapAndDividerHeight(NotificationStackScrollLayout notificationStackScrollLayout, ExpandableView expandableView, ExpandableView expandableView2, int i) {
        float f = 0.0f;
        if (i == 0) {
            return 0.0f;
        }
        float floatValue = ((Number) this.dividerHeight$delegate.getValue(this, $$delegatedProperties[1])).floatValue() + notificationStackScrollLayout.calculateGapHeight(expandableView, expandableView2, i);
        if (i == 1) {
            if (expandableView != null ? expandableView.isGroupExpanded() : false) {
                f = 0.0f + getMaxGroupExpandedBottomGap();
            }
        }
        if (expandableView != null ? expandableView.isGroupExpanded() : false) {
            f += getMaxGroupExpandedBottomGap();
        }
        if ((expandableView == null || expandableView.isGroupExpanded()) ? false : true) {
            if (expandableView2 != null ? expandableView2.isGroupExpanded() : false) {
                f += getMaxGroupExpandedBottomGap();
            }
        }
        return floatValue + f;
    }

    public final int computeMaxKeyguardNotifications(NotificationStackScrollLayout notificationStackScrollLayout, final float f, final float f2, float f3) {
        boolean z;
        if (f + f2 <= 0.0f) {
            return 0;
        }
        SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1 sequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1 = new SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1(new NotificationStackSizeCalculator$computeHeightPerNotificationLimit$1(this, notificationStackScrollLayout, f3, null));
        if (this.mediaDataManager.hasActiveMediaOrRecommendation()) {
            ((SplitShadeStateControllerImpl) this.splitShadeStateController).shouldUseSplitNotificationShade();
            z = true;
        } else {
            z = false;
        }
        Iterator it = new TakeWhileSequence(sequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1, new Function1() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackSizeCalculator$computeMaxKeyguardNotifications$maxNotifWithoutSavingSpace$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                NotificationStackSizeCalculator.StackHeight stackHeight = (NotificationStackSizeCalculator.StackHeight) obj;
                NotificationStackSizeCalculator notificationStackSizeCalculator = NotificationStackSizeCalculator.this;
                KProperty[] kPropertyArr = NotificationStackSizeCalculator.$$delegatedProperties;
                notificationStackSizeCalculator.getClass();
                return Boolean.valueOf((stackHeight.shouldForceIntoShelf ^ true) && NotificationStackSizeCalculator.access$canStackFitInSpace(NotificationStackSizeCalculator.this, stackHeight, f, f2) == NotificationStackSizeCalculator.FitResult.FIT);
            }
        }).iterator();
        int i = 0;
        do {
            TakeWhileSequence$iterator$1 takeWhileSequence$iterator$1 = (TakeWhileSequence$iterator$1) it;
            if (!takeWhileSequence$iterator$1.hasNext()) {
                int i2 = i - 1;
                if (i2 < (z ? 2 : 1)) {
                    this.saveSpaceOnLockscreen = true;
                    Iterator it2 = new TakeWhileSequence(sequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1, new Function1() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackSizeCalculator$computeMaxKeyguardNotifications$10
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj) {
                            NotificationStackSizeCalculator.StackHeight stackHeight = (NotificationStackSizeCalculator.StackHeight) obj;
                            NotificationStackSizeCalculator notificationStackSizeCalculator = NotificationStackSizeCalculator.this;
                            KProperty[] kPropertyArr = NotificationStackSizeCalculator.$$delegatedProperties;
                            notificationStackSizeCalculator.getClass();
                            return Boolean.valueOf((stackHeight.shouldForceIntoShelf ^ true) && NotificationStackSizeCalculator.access$canStackFitInSpace(NotificationStackSizeCalculator.this, stackHeight, f, f2) != NotificationStackSizeCalculator.FitResult.NO_FIT);
                        }
                    }).iterator();
                    int i3 = 0;
                    do {
                        TakeWhileSequence$iterator$1 takeWhileSequence$iterator$12 = (TakeWhileSequence$iterator$1) it2;
                        if (takeWhileSequence$iterator$12.hasNext()) {
                            takeWhileSequence$iterator$12.next();
                            i3++;
                        } else {
                            i2 = i3 - 1;
                        }
                    } while (i3 >= 0);
                    CollectionsKt__CollectionsKt.throwCountOverflow();
                    throw null;
                }
                this.saveSpaceOnLockscreen = false;
                for (ExpandableView expandableView : SequencesKt___SequencesKt.toList(SequencesKt___SequencesKt.filter(new TransformingSequence(ConvenienceExtensionsKt.getChildren(notificationStackScrollLayout), NotificationStackSizeCalculator$childrenSequence$1.INSTANCE), new NotificationStackSizeCalculator$showableChildren$1(this)))) {
                    if (expandableView instanceof ExpandableNotificationRow) {
                        ((ExpandableNotificationRow) expandableView).mSaveSpaceOnLockscreen = this.saveSpaceOnLockscreen;
                    }
                }
                if (onLockscreen()) {
                    i2 = Math.min(((Number) this.maxKeyguardNotifications$delegate.getValue(this, $$delegatedProperties[0])).intValue(), i2);
                }
                return Math.max(0, i2);
            }
            takeWhileSequence$iterator$1.next();
            i++;
        } while (i >= 0);
        CollectionsKt__CollectionsKt.throwCountOverflow();
        throw null;
    }

    public final float getMaxGroupExpandedBottomGap() {
        return ((Number) this.maxGroupExpandedBottomGap$delegate.getValue(this, $$delegatedProperties[2])).floatValue();
    }

    public final SpaceNeeded getSpaceNeeded(ExpandableView expandableView, int i, ExpandableView expandableView2, NotificationStackScrollLayout notificationStackScrollLayout, boolean z) {
        isShowable(expandableView, z);
        float heightWithoutLockscreenConstraints = expandableView.getHeightWithoutLockscreenConstraints();
        float calculateGapAndDividerHeight = calculateGapAndDividerHeight(notificationStackScrollLayout, expandableView2, expandableView, i);
        float minHeight = ((!z || ((expandableView instanceof ExpandableNotificationRow) && ((ExpandableNotificationRow) expandableView).mEntry.isStickyAndNotDemoted())) ? heightWithoutLockscreenConstraints : expandableView.getMinHeight(true)) + calculateGapAndDividerHeight;
        if (z) {
            heightWithoutLockscreenConstraints = expandableView.getMinHeight(true);
        }
        return new SpaceNeeded(minHeight, heightWithoutLockscreenConstraints + calculateGapAndDividerHeight);
    }

    public final boolean onLockscreen() {
        return ((StatusBarStateControllerImpl) this.statusBarStateController).mState == 1 && this.lockscreenShadeTransitionController.getFractionToShade() == 0.0f;
    }

    public final void updateResources() {
        Flags.notificationMinimalismPrototype();
        int integer = this.resources.getInteger(R.integer.keyguard_max_notification_count);
        if (integer < 0) {
            integer = Integer.MAX_VALUE;
        }
        KProperty[] kPropertyArr = $$delegatedProperties;
        KProperty kProperty = kPropertyArr[0];
        this.maxKeyguardNotifications$delegate.value = Integer.valueOf(integer);
        Flags.notificationMinimalismPrototype();
        Flags.notificationMinimalismPrototype();
        float max = Math.max(1.0f, this.resources.getDimensionPixelSize(R.dimen.notification_divider_height));
        KProperty kProperty2 = kPropertyArr[1];
        this.dividerHeight$delegate.value = Float.valueOf(max);
        float dimension = this.resources.getDimension(R.dimen.notification_group_expanded_max_bottom_gap);
        KProperty kProperty3 = kPropertyArr[2];
        this.maxGroupExpandedBottomGap$delegate.value = Float.valueOf(dimension);
    }
}
