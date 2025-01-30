package com.android.systemui.statusbar.notification.stack;

import android.content.res.Resources;
import com.android.settingslib.udfps.UdfpsOverlayParams$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.media.controls.pipeline.MediaDataManager;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.stack.NotificationStackSizeCalculator;
import com.android.systemui.util.ConvenienceExtensionsKt;
import com.android.systemui.util.LargeScreenUtils;
import java.util.Iterator;
import kotlin.jvm.functions.Function1;
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

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NotificationStackSizeCalculator {
    public static final /* synthetic */ KProperty[] $$delegatedProperties;
    public final NotNullVar dividerHeight$delegate;
    public final LockscreenShadeTransitionController lockscreenShadeTransitionController;
    public final NotNullVar maxKeyguardNotifications$delegate;
    public final MediaDataManager mediaDataManager;
    public final Resources resources;
    public boolean saveSpaceOnLockscreen;
    public final SysuiStatusBarStateController statusBarStateController;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    enum FitResult {
        FIT,
        FIT_IF_SAVE_SPACE,
        NO_FIT
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class StackHeight {
        public final float notifsHeight;
        public final float notifsHeightSavingSpace;
        public final float shelfHeightWithSpaceBefore;

        public StackHeight(float f, float f2, float f3) {
            this.notifsHeight = f;
            this.notifsHeightSavingSpace = f2;
            this.shelfHeightWithSpaceBefore = f3;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof StackHeight)) {
                return false;
            }
            StackHeight stackHeight = (StackHeight) obj;
            return Float.compare(this.notifsHeight, stackHeight.notifsHeight) == 0 && Float.compare(this.notifsHeightSavingSpace, stackHeight.notifsHeightSavingSpace) == 0 && Float.compare(this.shelfHeightWithSpaceBefore, stackHeight.shelfHeightWithSpaceBefore) == 0;
        }

        public final int hashCode() {
            return Float.hashCode(this.shelfHeightWithSpaceBefore) + UdfpsOverlayParams$$ExternalSyntheticOutline0.m90m(this.notifsHeightSavingSpace, Float.hashCode(this.notifsHeight) * 31, 31);
        }

        public final String toString() {
            return "StackHeight(notifsHeight=" + this.notifsHeight + ", notifsHeightSavingSpace=" + this.notifsHeightSavingSpace + ", shelfHeightWithSpaceBefore=" + this.shelfHeightWithSpaceBefore + ")";
        }
    }

    static {
        MutablePropertyReference1Impl mutablePropertyReference1Impl = new MutablePropertyReference1Impl(NotificationStackSizeCalculator.class, "maxKeyguardNotifications", "getMaxKeyguardNotifications()I", 0);
        ReflectionFactory reflectionFactory = Reflection.factory;
        reflectionFactory.getClass();
        MutablePropertyReference1Impl mutablePropertyReference1Impl2 = new MutablePropertyReference1Impl(NotificationStackSizeCalculator.class, "dividerHeight", "getDividerHeight()F", 0);
        reflectionFactory.getClass();
        $$delegatedProperties = new KProperty[]{mutablePropertyReference1Impl, mutablePropertyReference1Impl2};
    }

    public NotificationStackSizeCalculator(SysuiStatusBarStateController sysuiStatusBarStateController, LockscreenShadeTransitionController lockscreenShadeTransitionController, MediaDataManager mediaDataManager, Resources resources) {
        this.statusBarStateController = sysuiStatusBarStateController;
        this.lockscreenShadeTransitionController = lockscreenShadeTransitionController;
        this.mediaDataManager = mediaDataManager;
        this.resources = resources;
        Delegates.INSTANCE.getClass();
        this.maxKeyguardNotifications$delegate = new NotNullVar();
        this.dividerHeight$delegate = new NotNullVar();
        updateResources();
    }

    public static final FitResult access$canStackFitInSpace(NotificationStackSizeCalculator notificationStackSizeCalculator, StackHeight stackHeight, float f, float f2) {
        notificationStackSizeCalculator.getClass();
        float f3 = stackHeight.notifsHeight;
        float f4 = stackHeight.shelfHeightWithSpaceBefore;
        boolean z = f4 == 0.0f;
        float f5 = stackHeight.notifsHeightSavingSpace;
        if (z) {
            return f3 <= f ? FitResult.FIT : f5 <= f ? FitResult.FIT_IF_SAVE_SPACE : FitResult.NO_FIT;
        }
        float f6 = f + f2;
        return f3 + f4 <= f6 ? FitResult.FIT : f5 + f4 <= f6 ? FitResult.FIT_IF_SAVE_SPACE : FitResult.NO_FIT;
    }

    public static boolean isShowable(ExpandableView expandableView, boolean z) {
        if (expandableView.getVisibility() == 8 || expandableView.hasNoContentHeight()) {
            return false;
        }
        if (z) {
            if (expandableView instanceof ExpandableNotificationRow) {
                if (!((expandableView.hasNoContentHeight() || expandableView.getVisibility() == 8) ? false : true)) {
                    return false;
                }
            } else if (!(expandableView instanceof MediaContainerView) || ((MediaContainerView) expandableView).getHeight() == 0) {
                return false;
            }
        }
        return true;
    }

    public final float calculateGapAndDividerHeight(NotificationStackScrollLayout notificationStackScrollLayout, ExpandableView expandableView, ExpandableView expandableView2, int i) {
        if (i == 0) {
            return 0.0f;
        }
        return ((Number) this.dividerHeight$delegate.getValue($$delegatedProperties[1])).floatValue() + notificationStackScrollLayout.calculateGapHeight(expandableView, expandableView2, i);
    }

    public final int computeMaxKeyguardNotifications(NotificationStackScrollLayout notificationStackScrollLayout, final float f, final float f2, float f3) {
        if (f + f2 <= 0.0f) {
            return 0;
        }
        SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1 sequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1 = new SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1(new C2969xf807607a(this, notificationStackScrollLayout, f3, null));
        boolean hasActiveMediaOrRecommendation = this.mediaDataManager.hasActiveMediaOrRecommendation();
        Iterator it = new TakeWhileSequence(sequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1, new Function1() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackSizeCalculator$computeMaxKeyguardNotifications$maxNotifWithoutSavingSpace$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Boolean.valueOf(NotificationStackSizeCalculator.access$canStackFitInSpace(NotificationStackSizeCalculator.this, (NotificationStackSizeCalculator.StackHeight) obj, f, f2) == NotificationStackSizeCalculator.FitResult.FIT);
            }
        }).iterator();
        int i = 0;
        do {
            TakeWhileSequence$iterator$1 takeWhileSequence$iterator$1 = (TakeWhileSequence$iterator$1) it;
            if (!takeWhileSequence$iterator$1.hasNext()) {
                int i2 = i - 1;
                if (i2 < ((!hasActiveMediaOrRecommendation || LargeScreenUtils.shouldUseSplitNotificationShade(this.resources)) ? 1 : 2)) {
                    this.saveSpaceOnLockscreen = true;
                    Iterator it2 = new TakeWhileSequence(sequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1, new Function1() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackSizeCalculator$computeMaxKeyguardNotifications$10
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj) {
                            return Boolean.valueOf(NotificationStackSizeCalculator.access$canStackFitInSpace(NotificationStackSizeCalculator.this, (NotificationStackSizeCalculator.StackHeight) obj, f, f2) != NotificationStackSizeCalculator.FitResult.NO_FIT);
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
                    throw new ArithmeticException("Count overflow has happened.");
                }
                this.saveSpaceOnLockscreen = false;
                for (ExpandableView expandableView : SequencesKt___SequencesKt.toList(SequencesKt___SequencesKt.filter(new TransformingSequence(ConvenienceExtensionsKt.getChildren(notificationStackScrollLayout), NotificationStackSizeCalculator$childrenSequence$1.INSTANCE), new NotificationStackSizeCalculator$showableChildren$1(this)))) {
                    if (expandableView instanceof ExpandableNotificationRow) {
                        ((ExpandableNotificationRow) expandableView).mSaveSpaceOnLockscreen = this.saveSpaceOnLockscreen;
                    }
                }
                if (onLockscreen()) {
                    i2 = Math.min(((Number) this.maxKeyguardNotifications$delegate.getValue($$delegatedProperties[0])).intValue(), i2);
                }
                return Math.max(0, i2);
            }
            takeWhileSequence$iterator$1.next();
            i++;
        } while (i >= 0);
        throw new ArithmeticException("Count overflow has happened.");
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
        if (((StatusBarStateControllerImpl) this.statusBarStateController).mState == 1) {
            return (this.lockscreenShadeTransitionController.getFractionToShade() > 0.0f ? 1 : (this.lockscreenShadeTransitionController.getFractionToShade() == 0.0f ? 0 : -1)) == 0;
        }
        return false;
    }

    public final void updateResources() {
        int integer = this.resources.getInteger(R.integer.keyguard_max_notification_count);
        if (integer < 0) {
            integer = Integer.MAX_VALUE;
        }
        KProperty[] kPropertyArr = $$delegatedProperties;
        KProperty kProperty = kPropertyArr[0];
        this.maxKeyguardNotifications$delegate.value = Integer.valueOf(integer);
        float max = Math.max(1.0f, r0.getDimensionPixelSize(R.dimen.notification_divider_height));
        KProperty kProperty2 = kPropertyArr[1];
        this.dividerHeight$delegate.value = Float.valueOf(max);
    }
}
