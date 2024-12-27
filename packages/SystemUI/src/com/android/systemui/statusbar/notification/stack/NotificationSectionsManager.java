package com.android.systemui.statusbar.notification.stack;

import android.util.SparseArray;
import android.view.View;
import com.android.systemui.media.controls.ui.controller.KeyguardMediaController;
import com.android.systemui.statusbar.notification.NotificationSectionsFeatureManager;
import com.android.systemui.statusbar.notification.SourceType;
import com.android.systemui.statusbar.notification.SourceType$Companion$from$1;
import com.android.systemui.statusbar.notification.collection.render.MediaContainerController;
import com.android.systemui.statusbar.notification.collection.render.SectionHeaderController;
import com.android.systemui.statusbar.notification.collection.render.SectionHeaderNodeControllerImpl;
import com.android.systemui.statusbar.notification.footer.shared.FooterViewRefactor;
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
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

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
    public final SectionHeaderController favoriteHeaderController;
    public final SectionHeaderController incomingHeaderController;
    public boolean initialized;
    public final NotificationRoundnessManager notificationRoundnessManager;
    public final SectionHeaderController ongoingActivityHeaderController;
    public NotificationStackScrollLayout parent;
    public final SectionHeaderController peopleHeaderController;
    public NotificationRoundnessManager.SectionStateProvider sectionStateProvider;
    public final NotificationSectionsFeatureManager sectionsFeatureManager;
    public final SectionHeaderController silentHeaderController;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public abstract class SectionBounds {

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

        public final class None extends SectionBounds {
            public static final None INSTANCE = new None();

            private None() {
                super(null);
            }
        }

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

        public static boolean setFirstAndLastVisibleChildren(NotificationSection notificationSection, ExpandableView expandableView, ExpandableView expandableView2) {
            boolean z = notificationSection.mFirstVisibleChild != expandableView;
            notificationSection.mFirstVisibleChild = expandableView;
            boolean z2 = notificationSection.mLastVisibleChild != expandableView2;
            notificationSection.mLastVisibleChild = expandableView2;
            return z || z2;
        }

        public /* synthetic */ SectionBounds(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
        SourceType.Companion.getClass();
        SECTION = new SourceType$Companion$from$1("Section");
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.statusbar.notification.stack.NotificationSectionsManager$configurationListener$1] */
    public NotificationSectionsManager(ConfigurationController configurationController, KeyguardMediaController keyguardMediaController, NotificationSectionsFeatureManager notificationSectionsFeatureManager, MediaContainerController mediaContainerController, NotificationRoundnessManager notificationRoundnessManager, SectionHeaderController sectionHeaderController, SectionHeaderController sectionHeaderController2, SectionHeaderController sectionHeaderController3, SectionHeaderController sectionHeaderController4, SectionHeaderController sectionHeaderController5, SectionHeaderController sectionHeaderController6, SectionHeaderController sectionHeaderController7) {
        this.configurationController = configurationController;
        this.sectionsFeatureManager = notificationSectionsFeatureManager;
        this.notificationRoundnessManager = notificationRoundnessManager;
        this.incomingHeaderController = sectionHeaderController;
        this.peopleHeaderController = sectionHeaderController2;
        this.alertingHeaderController = sectionHeaderController3;
        this.silentHeaderController = sectionHeaderController4;
        this.favoriteHeaderController = sectionHeaderController5;
        this.ongoingActivityHeaderController = sectionHeaderController6;
    }

    public final boolean beginsSection(View view, View view2) {
        return view == ((SectionHeaderNodeControllerImpl) this.peopleHeaderController)._view || view == ((SectionHeaderNodeControllerImpl) this.alertingHeaderController)._view || view == ((SectionHeaderNodeControllerImpl) this.incomingHeaderController)._view || !(Intrinsics.areEqual(getBucket(view), getBucket(view2)) || view.equals(((SectionHeaderNodeControllerImpl) this.silentHeaderController)._view));
    }

    public final Integer getBucket(View view) {
        if (view == ((SectionHeaderNodeControllerImpl) this.silentHeaderController)._view) {
            return 13;
        }
        if (view == ((SectionHeaderNodeControllerImpl) this.incomingHeaderController)._view) {
            return 7;
        }
        if (view == ((SectionHeaderNodeControllerImpl) this.peopleHeaderController)._view) {
            return 11;
        }
        if (view == ((SectionHeaderNodeControllerImpl) this.alertingHeaderController)._view) {
            return 12;
        }
        if (view instanceof ExpandableNotificationRow) {
            return Integer.valueOf(((ExpandableNotificationRow) view).mEntry.mBucket);
        }
        return null;
    }

    public final void reinflateViews() {
        boolean hasNotifications;
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
        if (notificationStackScrollLayoutController == null) {
            hasNotifications = false;
        } else {
            FooterViewRefactor.assertInLegacyMode();
            hasNotifications = notificationStackScrollLayoutController.hasNotifications(2, true);
        }
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
        if (notificationStackScrollLayout5 == null) {
            notificationStackScrollLayout5 = null;
        }
        ((SectionHeaderNodeControllerImpl) this.incomingHeaderController).reinflateView(notificationStackScrollLayout5);
        NotificationStackScrollLayout notificationStackScrollLayout6 = this.parent;
        if (notificationStackScrollLayout6 == null) {
            notificationStackScrollLayout6 = null;
        }
        ((SectionHeaderNodeControllerImpl) this.favoriteHeaderController).reinflateView(notificationStackScrollLayout6);
        NotificationStackScrollLayout notificationStackScrollLayout7 = this.parent;
        ((SectionHeaderNodeControllerImpl) this.ongoingActivityHeaderController).reinflateView(notificationStackScrollLayout7 != null ? notificationStackScrollLayout7 : null);
    }

    public final void updateFirstAndLastViewsForAllSections(NotificationSection[] notificationSectionArr, List list) {
        NotificationRoundnessManager notificationRoundnessManager;
        SourceType$Companion$from$1 sourceType$Companion$from$1;
        boolean firstAndLastVisibleChildren;
        Object many;
        Object obj;
        NotificationSectionsManager$updateFirstAndLastViewsForAllSections$$inlined$groupingBy$1 notificationSectionsManager$updateFirstAndLastViewsForAllSections$$inlined$groupingBy$1 = new NotificationSectionsManager$updateFirstAndLastViewsForAllSections$$inlined$groupingBy$1(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(list), this);
        SectionBounds.None none = SectionBounds.None.INSTANCE;
        int length = notificationSectionArr.length;
        SparseArray sparseArray = length < 0 ? new SparseArray() : new SparseArray(length);
        for (Object obj2 : notificationSectionsManager$updateFirstAndLastViewsForAllSections$$inlined$groupingBy$1.$this_groupingBy) {
            int intValue = ((Number) notificationSectionsManager$updateFirstAndLastViewsForAllSections$$inlined$groupingBy$1.keyOf(obj2)).intValue();
            Object obj3 = sparseArray.get(intValue);
            if (obj3 == null) {
                obj3 = none;
            }
            ExpandableView expandableView = (ExpandableView) obj2;
            SectionBounds sectionBounds = (SectionBounds) obj3;
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
            Object obj4 = (SectionBounds) sparseArray.get(notificationSection3.mBucket);
            if (obj4 == null) {
                obj4 = SectionBounds.None.INSTANCE;
            }
            obj4.getClass();
            if (obj4 instanceof SectionBounds.None) {
                firstAndLastVisibleChildren = SectionBounds.setFirstAndLastVisibleChildren(notificationSection3, null, null);
            } else if (obj4 instanceof SectionBounds.One) {
                ExpandableView expandableView4 = ((SectionBounds.One) obj4).lone;
                firstAndLastVisibleChildren = SectionBounds.setFirstAndLastVisibleChildren(notificationSection3, expandableView4, expandableView4);
            } else {
                if (!(obj4 instanceof SectionBounds.Many)) {
                    throw new NoWhenBranchMatchedException();
                }
                SectionBounds.Many many2 = (SectionBounds.Many) obj4;
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
                expandableView7.requestTopRoundness(1.0f, sourceType$Companion$from$1, expandableView7.isShown() && (notificationRoundnessManager.mAnimatedChildren.contains(expandableView7) ^ true));
            }
            NotificationRoundnessManager.SectionStateProvider sectionStateProvider = this.sectionStateProvider;
            if ((sectionStateProvider != null ? sectionStateProvider : null) != null) {
                if (((NotificationStackScrollLayoutController) (sectionStateProvider != null ? sectionStateProvider : null)).mBarState == 1) {
                    if (sectionStateProvider == null) {
                        sectionStateProvider = null;
                    }
                    if (((NotificationStackScrollLayoutController) sectionStateProvider).mView.getFirstVisibleSection() != null) {
                        NotificationRoundnessManager.SectionStateProvider sectionStateProvider2 = this.sectionStateProvider;
                        if (sectionStateProvider2 == null) {
                            sectionStateProvider2 = null;
                        }
                        if (((NotificationStackScrollLayoutController) sectionStateProvider2).mView.getFirstVisibleSection().mFirstVisibleChild != expandableView7) {
                            expandableView7.requestTopRoundness(0.0f, sourceType$Companion$from$1, false);
                        }
                    }
                }
                if (remove) {
                    expandableView7.requestTopRoundness(1.0f, sourceType$Companion$from$1, false);
                }
            }
        }
        Iterator it2 = arrayList4.iterator();
        while (it2.hasNext()) {
            ExpandableView expandableView8 = (ExpandableView) it2.next();
            boolean remove2 = mutableSet2.remove(expandableView8);
            if (!remove2) {
                expandableView8.requestBottomRoundness(1.0f, sourceType$Companion$from$1, expandableView8.isShown() && (notificationRoundnessManager.mAnimatedChildren.contains(expandableView8) ^ true));
            }
            NotificationRoundnessManager.SectionStateProvider sectionStateProvider3 = this.sectionStateProvider;
            if ((sectionStateProvider3 != null ? sectionStateProvider3 : null) != null) {
                if (((NotificationStackScrollLayoutController) (sectionStateProvider3 != null ? sectionStateProvider3 : null)).mBarState == 1) {
                    if (sectionStateProvider3 == null) {
                        sectionStateProvider3 = null;
                    }
                    if (((NotificationStackScrollLayoutController) sectionStateProvider3).mView.getLastVisibleSection() != null) {
                        NotificationRoundnessManager.SectionStateProvider sectionStateProvider4 = this.sectionStateProvider;
                        if (sectionStateProvider4 == null) {
                            sectionStateProvider4 = null;
                        }
                        if (((NotificationStackScrollLayoutController) sectionStateProvider4).mView.getLastVisibleSection().mLastVisibleChild != expandableView8) {
                            expandableView8.requestBottomRoundness(0.0f, sourceType$Companion$from$1, false);
                        }
                    }
                }
                if (remove2) {
                    expandableView8.requestBottomRoundness(1.0f, sourceType$Companion$from$1, false);
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
            public final void accept(Object obj5) {
                ((ExpandableView) obj5).requestRoundness(1.0f, 1.0f, NotificationSectionsManager.SECTION, false);
            }
        });
    }

    public static /* synthetic */ void getAlertingHeaderView$annotations() {
    }

    public static /* synthetic */ void getFavoriteHeaderView$annotations() {
    }

    public static /* synthetic */ void getIncomingHeaderView$annotations() {
    }

    public static /* synthetic */ void getMediaControlsView$annotations() {
    }

    public static /* synthetic */ void getOngoingActivityHeaderView$annotations() {
    }

    public static /* synthetic */ void getPeopleHeaderView$annotations() {
    }

    public static /* synthetic */ void getSilentHeaderView$annotations() {
    }
}
