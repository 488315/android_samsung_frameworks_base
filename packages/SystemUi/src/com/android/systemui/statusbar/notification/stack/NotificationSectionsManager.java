package com.android.systemui.statusbar.notification.stack;

import android.util.SparseArray;
import android.view.View;
import com.android.systemui.media.controls.p010ui.KeyguardMediaController;
import com.android.systemui.statusbar.notification.NotificationSectionsFeatureManager;
import com.android.systemui.statusbar.notification.SourceType;
import com.android.systemui.statusbar.notification.SourceType$Companion$from$1;
import com.android.systemui.statusbar.notification.collection.render.MediaContainerController;
import com.android.systemui.statusbar.notification.collection.render.SectionHeaderController;
import com.android.systemui.statusbar.notification.collection.render.SectionHeaderNodeControllerImpl;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.stack.NotificationRoundnessManager;
import com.android.systemui.statusbar.notification.stack.StackScrollAlgorithm;
import com.android.systemui.statusbar.policy.ConfigurationController;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.collections.Grouping;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NotificationSectionsManager implements StackScrollAlgorithm.SectionProvider {
    public static final SourceType$Companion$from$1 SECTION;
    public final SectionHeaderController alertingHeaderController;
    public final ConfigurationController configurationController;
    public final NotificationSectionsManager$configurationListener$1 configurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.statusbar.notification.stack.NotificationSectionsManager$configurationListener$1
        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public final void onLocaleListChanged() {
            NotificationSectionsManager.this.reinflateViews();
        }
    };
    public final SectionHeaderController incomingHeaderController;
    public boolean initialized;
    public final NotificationRoundnessManager notificationRoundnessManager;
    public NotificationStackScrollLayout parent;
    public final SectionHeaderController peopleHeaderController;
    public NotificationRoundnessManager.SectionStateProvider sectionStateProvider;
    public final NotificationSectionsFeatureManager sectionsFeatureManager;
    public final SectionHeaderController silentHeaderController;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class SectionBounds {

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class Many extends SectionBounds {
            public final ExpandableView first;
            public final ExpandableView last;

            public Many(ExpandableView expandableView, ExpandableView expandableView2) {
                super(null);
                this.first = expandableView;
                this.last = expandableView2;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof Many)) {
                    return false;
                }
                Many many = (Many) obj;
                return Intrinsics.areEqual(this.first, many.first) && Intrinsics.areEqual(this.last, many.last);
            }

            public final int hashCode() {
                return this.last.hashCode() + (this.first.hashCode() * 31);
            }

            public final String toString() {
                return "Many(first=" + this.first + ", last=" + this.last + ")";
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class None extends SectionBounds {
            public static final None INSTANCE = new None();

            private None() {
                super(null);
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class One extends SectionBounds {
            public final ExpandableView lone;

            public One(ExpandableView expandableView) {
                super(null);
                this.lone = expandableView;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                return (obj instanceof One) && Intrinsics.areEqual(this.lone, ((One) obj).lone);
            }

            public final int hashCode() {
                return this.lone.hashCode();
            }

            public final String toString() {
                return "One(lone=" + this.lone + ")";
            }
        }

        private SectionBounds() {
        }

        public /* synthetic */ SectionBounds(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static boolean setFirstAndLastVisibleChildren(NotificationSection notificationSection, ExpandableView expandableView, ExpandableView expandableView2) {
            boolean z = notificationSection.mFirstVisibleChild != expandableView;
            notificationSection.mFirstVisibleChild = expandableView;
            boolean z2 = notificationSection.mLastVisibleChild != expandableView2;
            notificationSection.mLastVisibleChild = expandableView2;
            return z || z2;
        }
    }

    static {
        new Companion(null);
        SourceType.Companion.getClass();
        SECTION = new SourceType$Companion$from$1("Section");
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.statusbar.notification.stack.NotificationSectionsManager$configurationListener$1] */
    public NotificationSectionsManager(ConfigurationController configurationController, KeyguardMediaController keyguardMediaController, NotificationSectionsFeatureManager notificationSectionsFeatureManager, MediaContainerController mediaContainerController, NotificationRoundnessManager notificationRoundnessManager, SectionHeaderController sectionHeaderController, SectionHeaderController sectionHeaderController2, SectionHeaderController sectionHeaderController3, SectionHeaderController sectionHeaderController4) {
        this.configurationController = configurationController;
        this.sectionsFeatureManager = notificationSectionsFeatureManager;
        this.notificationRoundnessManager = notificationRoundnessManager;
        this.incomingHeaderController = sectionHeaderController;
        this.peopleHeaderController = sectionHeaderController2;
        this.alertingHeaderController = sectionHeaderController3;
        this.silentHeaderController = sectionHeaderController4;
    }

    public final boolean beginsSection(View view, View view2) {
        return view == ((SectionHeaderNodeControllerImpl) this.peopleHeaderController)._view || view == ((SectionHeaderNodeControllerImpl) this.alertingHeaderController)._view || view == ((SectionHeaderNodeControllerImpl) this.incomingHeaderController)._view || !(Intrinsics.areEqual(getBucket(view), getBucket(view2)) || Intrinsics.areEqual(view, ((SectionHeaderNodeControllerImpl) this.silentHeaderController)._view));
    }

    public final Integer getBucket(View view) {
        if (view == ((SectionHeaderNodeControllerImpl) this.silentHeaderController)._view) {
            return 9;
        }
        if (view == ((SectionHeaderNodeControllerImpl) this.incomingHeaderController)._view) {
            return 4;
        }
        if (view == ((SectionHeaderNodeControllerImpl) this.peopleHeaderController)._view) {
            return 7;
        }
        if (view == ((SectionHeaderNodeControllerImpl) this.alertingHeaderController)._view) {
            return 8;
        }
        if (view instanceof ExpandableNotificationRow) {
            return Integer.valueOf(((ExpandableNotificationRow) view).mEntry.mBucket);
        }
        return null;
    }

    public final NotificationRoundnessManager.SectionStateProvider getSectionStateProvider() {
        NotificationRoundnessManager.SectionStateProvider sectionStateProvider = this.sectionStateProvider;
        if (sectionStateProvider != null) {
            return sectionStateProvider;
        }
        return null;
    }

    public final void reinflateViews() {
        NotificationStackScrollLayout notificationStackScrollLayout = this.parent;
        if (notificationStackScrollLayout == null) {
            notificationStackScrollLayout = null;
        }
        SectionHeaderController sectionHeaderController = this.silentHeaderController;
        ((SectionHeaderNodeControllerImpl) sectionHeaderController).reinflateView(notificationStackScrollLayout);
        NotificationStackScrollLayout notificationStackScrollLayout2 = this.parent;
        if (notificationStackScrollLayout2 == null) {
            notificationStackScrollLayout2 = null;
        }
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = notificationStackScrollLayout2.mController;
        boolean hasNotifications = notificationStackScrollLayoutController == null ? false : notificationStackScrollLayoutController.hasNotifications(2, true);
        SectionHeaderView sectionHeaderView = ((SectionHeaderNodeControllerImpl) sectionHeaderController)._view;
        Intrinsics.checkNotNull(sectionHeaderView);
        sectionHeaderView.mClearAllButton.setVisibility(hasNotifications ? 0 : 8);
        NotificationStackScrollLayout notificationStackScrollLayout3 = this.parent;
        if (notificationStackScrollLayout3 == null) {
            notificationStackScrollLayout3 = null;
        }
        ((SectionHeaderNodeControllerImpl) this.alertingHeaderController).reinflateView(notificationStackScrollLayout3);
        NotificationStackScrollLayout notificationStackScrollLayout4 = this.parent;
        if (notificationStackScrollLayout4 == null) {
            notificationStackScrollLayout4 = null;
        }
        ((SectionHeaderNodeControllerImpl) this.peopleHeaderController).reinflateView(notificationStackScrollLayout4);
        NotificationStackScrollLayout notificationStackScrollLayout5 = this.parent;
        ((SectionHeaderNodeControllerImpl) this.incomingHeaderController).reinflateView(notificationStackScrollLayout5 != null ? notificationStackScrollLayout5 : null);
    }

    /* JADX WARN: Code restructure failed: missing block: B:110:0x0185, code lost:
    
        r2.requestTopRoundness(1.0f, r10, false);
     */
    /* JADX WARN: Code restructure failed: missing block: B:142:0x01e3, code lost:
    
        r2.requestBottomRoundness(1.0f, r10, false);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean updateFirstAndLastViewsForAllSections(NotificationSection[] notificationSectionArr, List list) {
        NotificationRoundnessManager notificationRoundnessManager;
        SourceType$Companion$from$1 sourceType$Companion$from$1;
        boolean firstAndLastVisibleChildren;
        Object many;
        Object obj;
        final CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1 collectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1 = new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(list);
        Grouping grouping = new Grouping() { // from class: com.android.systemui.statusbar.notification.stack.NotificationSectionsManager$updateFirstAndLastViewsForAllSections$$inlined$groupingBy$1
            @Override // kotlin.collections.Grouping
            public final Object keyOf(Object obj2) {
                SourceType$Companion$from$1 sourceType$Companion$from$12 = NotificationSectionsManager.SECTION;
                Integer bucket = this.getBucket((ExpandableView) obj2);
                if (bucket != null) {
                    return Integer.valueOf(bucket.intValue());
                }
                throw new IllegalArgumentException("Cannot find section bucket for view");
            }

            @Override // kotlin.collections.Grouping
            public final Iterator sourceIterator() {
                return Sequence.this.iterator();
            }
        };
        SectionBounds.None none = SectionBounds.None.INSTANCE;
        int length = notificationSectionArr.length;
        SparseArray sparseArray = length < 0 ? new SparseArray() : new SparseArray(length);
        Iterator sourceIterator = grouping.sourceIterator();
        while (sourceIterator.hasNext()) {
            Object next = sourceIterator.next();
            int intValue = ((Number) grouping.keyOf(next)).intValue();
            Object obj2 = sparseArray.get(intValue);
            if (obj2 == null) {
                obj2 = none;
            }
            ExpandableView expandableView = (ExpandableView) next;
            SectionBounds sectionBounds = (SectionBounds) obj2;
            sectionBounds.getClass();
            if (sectionBounds instanceof SectionBounds.None) {
                obj = new SectionBounds.One(expandableView);
            } else {
                if (sectionBounds instanceof SectionBounds.One) {
                    many = new SectionBounds.Many(((SectionBounds.One) sectionBounds).lone, expandableView);
                } else {
                    if (!(sectionBounds instanceof SectionBounds.Many)) {
                        throw new NoWhenBranchMatchedException();
                    }
                    many = new SectionBounds.Many(((SectionBounds.Many) sectionBounds).first, expandableView);
                }
                obj = many;
            }
            sparseArray.put(intValue, obj);
        }
        ArrayList arrayList = new ArrayList();
        for (NotificationSection notificationSection : notificationSectionArr) {
            ExpandableView expandableView2 = notificationSection.mFirstVisibleChild;
            if (expandableView2 != null) {
                arrayList.add(expandableView2);
            }
        }
        Set<ExpandableView> mutableSet = CollectionsKt___CollectionsKt.toMutableSet(CollectionsKt___CollectionsKt.toSet(arrayList));
        ArrayList arrayList2 = new ArrayList();
        for (NotificationSection notificationSection2 : notificationSectionArr) {
            ExpandableView expandableView3 = notificationSection2.mLastVisibleChild;
            if (expandableView3 != null) {
                arrayList2.add(expandableView3);
            }
        }
        Set<ExpandableView> mutableSet2 = CollectionsKt___CollectionsKt.toMutableSet(CollectionsKt___CollectionsKt.toSet(arrayList2));
        boolean z = false;
        for (NotificationSection notificationSection3 : notificationSectionArr) {
            Object obj3 = (SectionBounds) sparseArray.get(notificationSection3.mBucket);
            if (obj3 == null) {
                obj3 = SectionBounds.None.INSTANCE;
            }
            obj3.getClass();
            if (obj3 instanceof SectionBounds.None) {
                firstAndLastVisibleChildren = SectionBounds.setFirstAndLastVisibleChildren(notificationSection3, null, null);
            } else if (obj3 instanceof SectionBounds.One) {
                ExpandableView expandableView4 = ((SectionBounds.One) obj3).lone;
                firstAndLastVisibleChildren = SectionBounds.setFirstAndLastVisibleChildren(notificationSection3, expandableView4, expandableView4);
            } else {
                if (!(obj3 instanceof SectionBounds.Many)) {
                    throw new NoWhenBranchMatchedException();
                }
                SectionBounds.Many many2 = (SectionBounds.Many) obj3;
                firstAndLastVisibleChildren = SectionBounds.setFirstAndLastVisibleChildren(notificationSection3, many2.first, many2.last);
            }
            z = firstAndLastVisibleChildren || z;
        }
        ArrayList arrayList3 = new ArrayList();
        for (NotificationSection notificationSection4 : notificationSectionArr) {
            ExpandableView expandableView5 = notificationSection4.mFirstVisibleChild;
            if (expandableView5 != null) {
                arrayList3.add(expandableView5);
            }
        }
        ArrayList arrayList4 = new ArrayList();
        for (NotificationSection notificationSection5 : notificationSectionArr) {
            ExpandableView expandableView6 = notificationSection5.mLastVisibleChild;
            if (expandableView6 != null) {
                arrayList4.add(expandableView6);
            }
        }
        Iterator it = arrayList3.iterator();
        while (true) {
            boolean hasNext = it.hasNext();
            notificationRoundnessManager = this.notificationRoundnessManager;
            sourceType$Companion$from$1 = SECTION;
            if (!hasNext) {
                break;
            }
            ExpandableView expandableView7 = (ExpandableView) it.next();
            boolean remove = mutableSet.remove(expandableView7);
            if (!remove) {
                notificationRoundnessManager.mAnimatedChildren.contains(expandableView7);
                expandableView7.isShown();
                expandableView7.requestTopRoundness(1.0f, sourceType$Companion$from$1, false);
            }
            if (getSectionStateProvider() != null) {
                if ((((NotificationStackScrollLayoutController) getSectionStateProvider()).mBarState == 1) && ((NotificationStackScrollLayoutController) getSectionStateProvider()).mView.getFirstVisibleSection() != null && ((NotificationStackScrollLayoutController) getSectionStateProvider()).mView.getFirstVisibleSection().mFirstVisibleChild != expandableView7) {
                    expandableView7.requestTopRoundness(0.0f, sourceType$Companion$from$1, false);
                }
            }
        }
        Iterator it2 = arrayList4.iterator();
        while (it2.hasNext()) {
            ExpandableView expandableView8 = (ExpandableView) it2.next();
            boolean remove2 = mutableSet2.remove(expandableView8);
            if (!remove2) {
                notificationRoundnessManager.mAnimatedChildren.contains(expandableView8);
                expandableView8.isShown();
                expandableView8.requestBottomRoundness(1.0f, sourceType$Companion$from$1, false);
            }
            if (getSectionStateProvider() != null) {
                if ((((NotificationStackScrollLayoutController) getSectionStateProvider()).mBarState == 1) && ((NotificationStackScrollLayoutController) getSectionStateProvider()).mView.getLastVisibleSection() != null && ((NotificationStackScrollLayoutController) getSectionStateProvider()).mView.getLastVisibleSection().mLastVisibleChild != expandableView8) {
                    expandableView8.requestBottomRoundness(0.0f, sourceType$Companion$from$1, false);
                }
            }
        }
        for (ExpandableView expandableView9 : mutableSet) {
            expandableView9.requestTopRoundness(0.0f, sourceType$Companion$from$1, expandableView9.getRoundableState().targetView.isShown());
        }
        for (ExpandableView expandableView10 : mutableSet2) {
            expandableView10.requestBottomRoundness(0.0f, sourceType$Companion$from$1, expandableView10.getRoundableState().targetView.isShown());
        }
        list.stream().forEach(new Consumer() { // from class: com.android.systemui.statusbar.notification.stack.NotificationSectionsManager$updateFirstAndLastViewsForAllSections$5
            @Override // java.util.function.Consumer
            public final void accept(Object obj4) {
                ((ExpandableView) obj4).requestRoundness(1.0f, 1.0f, NotificationSectionsManager.SECTION, false);
            }
        });
        return z;
    }

    public static /* synthetic */ void getAlertingHeaderView$annotations() {
    }

    public static /* synthetic */ void getIncomingHeaderView$annotations() {
    }

    public static /* synthetic */ void getMediaControlsView$annotations() {
    }

    public static /* synthetic */ void getPeopleHeaderView$annotations() {
    }

    public static /* synthetic */ void getSilentHeaderView$annotations() {
    }
}
