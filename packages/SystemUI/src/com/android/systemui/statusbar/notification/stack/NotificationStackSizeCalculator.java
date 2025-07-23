package com.android.systemui.statusbar.notification.stack;

import android.content.res.Resources;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.foundation.shape.DpCornerSize$$ExternalSyntheticOutline0;
import androidx.compose.ui.semantics.SemanticsPropertiesKt$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.notification.domain.interactor.SeenNotificationsInteractor;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.policy.SplitShadeStateController;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.ReflectionFactory;
import kotlin.properties.Delegates;
import kotlin.properties.NotNullVar;
import kotlin.reflect.KProperty;
import kotlin.sequences.SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        float floatValue = ((Number) this.dividerHeight$delegate.getValue(this, $$delegatedProperties[1])).floatValue() + notificationStackScrollLayout.calculateGapHeight(expandableView, expandableView2);
        if (i == 1) {
            if (expandableView != null ? expandableView.isGroupExpanded$1() : false) {
                f = 0.0f + getMaxGroupExpandedBottomGap();
            }
        }
        if (expandableView != null ? expandableView.isGroupExpanded$1() : false) {
            f += getMaxGroupExpandedBottomGap();
        }
        if ((expandableView == null || expandableView.isGroupExpanded$1()) ? false : true) {
            if (expandableView2 != null ? expandableView2.isGroupExpanded$1() : false) {
                f += getMaxGroupExpandedBottomGap();
            }
        }
        return floatValue + f;
    }

    public final SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1 computeHeightPerNotificationLimit(NotificationStackScrollLayout notificationStackScrollLayout, float f) {
        return new SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1(new NotificationStackSizeCalculator$computeHeightPerNotificationLimit$1(this, notificationStackScrollLayout, f, null));
    }

    public final float getMaxGroupExpandedBottomGap() {
        return ((Number) this.maxGroupExpandedBottomGap$delegate.getValue(this, $$delegatedProperties[2])).floatValue();
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0036  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.systemui.statusbar.notification.stack.NotificationStackSizeCalculator.SpaceNeeded getSpaceNeeded(com.android.systemui.statusbar.notification.row.ExpandableView r2, int r3, com.android.systemui.statusbar.notification.row.ExpandableView r4, com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r5, boolean r6) {
        /*
            r1 = this;
            isShowable(r2, r6)
            int r0 = r2.getHeightWithoutLockscreenConstraints()
            float r0 = (float) r0
            float r1 = r1.calculateGapAndDividerHeight(r5, r4, r2, r3)
            boolean r3 = r2 instanceof com.android.systemui.statusbar.notification.row.ExpandableNotificationRow
            r4 = 1
            if (r3 == 0) goto L20
            r5 = r2
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r5 = (com.android.systemui.statusbar.notification.row.ExpandableNotificationRow) r5
            com.android.systemui.statusbar.notification.collection.NotificationEntry r5 = r5.getEntryLegacy()
            boolean r5 = r5.isStickyAndNotDemoted()
            if (r5 == 0) goto L20
            r5 = r4
            goto L21
        L20:
            r5 = 0
        L21:
            if (r6 == 0) goto L32
            if (r3 == 0) goto L2c
            if (r5 != 0) goto L32
            r3 = r2
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r3 = (com.android.systemui.statusbar.notification.row.ExpandableNotificationRow) r3
            int r3 = com.android.systemui.statusbar.notification.promoted.PromotedNotificationUi.$r8$clinit
        L2c:
            int r3 = r2.getMinHeight(r4)
            float r3 = (float) r3
            goto L33
        L32:
            r3 = r0
        L33:
            float r3 = r3 + r1
            if (r6 == 0) goto L3b
            int r2 = r2.getMinHeight(r4)
            float r0 = (float) r2
        L3b:
            float r0 = r0 + r1
            com.android.systemui.statusbar.notification.stack.NotificationStackSizeCalculator$SpaceNeeded r1 = new com.android.systemui.statusbar.notification.stack.NotificationStackSizeCalculator$SpaceNeeded
            r1.<init>(r3, r0)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.NotificationStackSizeCalculator.getSpaceNeeded(com.android.systemui.statusbar.notification.row.ExpandableView, int, com.android.systemui.statusbar.notification.row.ExpandableView, com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout, boolean):com.android.systemui.statusbar.notification.stack.NotificationStackSizeCalculator$SpaceNeeded");
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
        float max = Math.max(1.0f, this.resources.getDimensionPixelSize(R.dimen.notification_divider_height));
        KProperty kProperty2 = kPropertyArr[1];
        this.dividerHeight$delegate.value = Float.valueOf(max);
        float dimension = this.resources.getDimension(R.dimen.notification_group_expanded_max_bottom_gap);
        KProperty kProperty3 = kPropertyArr[2];
        this.maxGroupExpandedBottomGap$delegate.value = Float.valueOf(dimension);
    }
}
