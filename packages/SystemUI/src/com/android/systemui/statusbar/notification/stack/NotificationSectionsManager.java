package com.android.systemui.statusbar.notification.stack;

import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.R;
import com.android.systemui.media.controls.ui.controller.KeyguardMediaController;
import com.android.systemui.statusbar.notification.SourceType;
import com.android.systemui.statusbar.notification.SourceType$Companion$from$1;
import com.android.systemui.statusbar.notification.collection.render.MediaContainerController;
import com.android.systemui.statusbar.notification.collection.render.SectionHeaderController;
import com.android.systemui.statusbar.notification.collection.render.SectionHeaderNodeControllerImpl;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.stack.StackScrollAlgorithm;
import com.android.systemui.statusbar.policy.ConfigurationController;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Stream;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public final class NotificationSectionsManager implements StackScrollAlgorithm.SectionProvider {
    public static final SourceType$Companion$from$1 SECTION;
    public final SectionHeaderController alertingHeaderController;
    public final ConfigurationController configurationController;
    public final NotificationSectionsManager$configurationListener$1 configurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.statusbar.notification.stack.NotificationSectionsManager$configurationListener$1
        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public final void onLocaleListChanged() {
            this.this$0.reinflateViews();
        }
    };
    public final SectionHeaderController favoriteHeaderController;
    public final SectionHeaderController incomingHeaderController;
    public boolean initialized;
    public final KeyguardMediaController keyguardMediaController;
    public final MediaContainerController mediaContainerController;
    public final SectionHeaderController newsHeaderController;
    public final NotificationRoundnessManager notificationRoundnessManager;
    public final SectionHeaderController ongoingActivityHeaderController;
    public NotificationStackScrollLayout parent;
    public final SectionHeaderController peopleHeaderController;
    public final SectionHeaderController promoHeaderController;
    public final SectionHeaderController recsHeaderController;
    public NotificationStackScrollLayoutController sectionStateProvider;
    public final SectionHeaderController silentHeaderController;
    public final SectionHeaderController socialHeaderController;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
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

        private SectionBounds() {
        }
    }

    static {
        new Companion(null);
        SourceType.Companion.getClass();
        SECTION = new SourceType$Companion$from$1("Section");
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.statusbar.notification.stack.NotificationSectionsManager$configurationListener$1] */
    public NotificationSectionsManager(ConfigurationController configurationController, KeyguardMediaController keyguardMediaController, MediaContainerController mediaContainerController, NotificationRoundnessManager notificationRoundnessManager, SectionHeaderController sectionHeaderController, SectionHeaderController sectionHeaderController2, SectionHeaderController sectionHeaderController3, SectionHeaderController sectionHeaderController4, SectionHeaderController sectionHeaderController5, SectionHeaderController sectionHeaderController6, SectionHeaderController sectionHeaderController7, SectionHeaderController sectionHeaderController8, SectionHeaderController sectionHeaderController9, SectionHeaderController sectionHeaderController10) {
        this.configurationController = configurationController;
        this.keyguardMediaController = keyguardMediaController;
        this.mediaContainerController = mediaContainerController;
        this.notificationRoundnessManager = notificationRoundnessManager;
        this.incomingHeaderController = sectionHeaderController;
        this.peopleHeaderController = sectionHeaderController2;
        this.alertingHeaderController = sectionHeaderController3;
        this.silentHeaderController = sectionHeaderController4;
        this.newsHeaderController = sectionHeaderController5;
        this.socialHeaderController = sectionHeaderController6;
        this.recsHeaderController = sectionHeaderController7;
        this.promoHeaderController = sectionHeaderController8;
        this.favoriteHeaderController = sectionHeaderController9;
        this.ongoingActivityHeaderController = sectionHeaderController10;
    }

    public final boolean beginsSection(ExpandableView expandableView, ExpandableView expandableView2) {
        SectionHeaderController sectionHeaderController = this.silentHeaderController;
        if (expandableView == ((SectionHeaderNodeControllerImpl) sectionHeaderController)._view || expandableView == this.mediaContainerController.mediaContainerView || expandableView == ((SectionHeaderNodeControllerImpl) this.peopleHeaderController)._view || expandableView == ((SectionHeaderNodeControllerImpl) this.alertingHeaderController)._view || expandableView == ((SectionHeaderNodeControllerImpl) this.incomingHeaderController)._view || expandableView == ((SectionHeaderNodeControllerImpl) this.newsHeaderController)._view || expandableView == ((SectionHeaderNodeControllerImpl) this.socialHeaderController)._view || expandableView == ((SectionHeaderNodeControllerImpl) this.recsHeaderController)._view || expandableView == ((SectionHeaderNodeControllerImpl) this.promoHeaderController)._view) {
            return true;
        }
        return (Intrinsics.areEqual(getBucket(expandableView), getBucket(expandableView2)) || Intrinsics.areEqual(expandableView, ((SectionHeaderNodeControllerImpl) sectionHeaderController)._view)) ? false : true;
    }

    public final Integer getBucket(View view) {
        if (view == ((SectionHeaderNodeControllerImpl) this.silentHeaderController)._view) {
            return 20;
        }
        if (view == ((SectionHeaderNodeControllerImpl) this.incomingHeaderController)._view) {
            return 7;
        }
        SectionHeaderController sectionHeaderController = this.peopleHeaderController;
        if (view != ((SectionHeaderNodeControllerImpl) sectionHeaderController)._view && view != ((SectionHeaderNodeControllerImpl) sectionHeaderController)._view) {
            if (view == ((SectionHeaderNodeControllerImpl) this.alertingHeaderController)._view) {
                return 15;
            }
            if (view == ((SectionHeaderNodeControllerImpl) this.newsHeaderController)._view) {
                return 16;
            }
            if (view == ((SectionHeaderNodeControllerImpl) this.socialHeaderController)._view) {
                return 17;
            }
            if (view == ((SectionHeaderNodeControllerImpl) this.recsHeaderController)._view) {
                return 18;
            }
            if (view == ((SectionHeaderNodeControllerImpl) this.promoHeaderController)._view) {
                return 19;
            }
            if (view instanceof ExpandableNotificationRow) {
                return Integer.valueOf(((ExpandableNotificationRow) view).getEntryLegacy().mBucket);
            }
            return null;
        }
        return 11;
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x004e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void reinflateViews() {
        int iIndexOfChild;
        NotificationStackScrollLayout notificationStackScrollLayout = this.parent;
        if (notificationStackScrollLayout == null) {
            notificationStackScrollLayout = null;
        }
        ((SectionHeaderNodeControllerImpl) this.silentHeaderController).reinflateView(notificationStackScrollLayout);
        NotificationStackScrollLayout notificationStackScrollLayout2 = this.parent;
        if (notificationStackScrollLayout2 == null) {
            notificationStackScrollLayout2 = null;
        }
        ((SectionHeaderNodeControllerImpl) this.alertingHeaderController).reinflateView(notificationStackScrollLayout2);
        NotificationStackScrollLayout notificationStackScrollLayout3 = this.parent;
        if (notificationStackScrollLayout3 == null) {
            notificationStackScrollLayout3 = null;
        }
        ((SectionHeaderNodeControllerImpl) this.peopleHeaderController).reinflateView(notificationStackScrollLayout3);
        NotificationStackScrollLayout notificationStackScrollLayout4 = this.parent;
        if (notificationStackScrollLayout4 == null) {
            notificationStackScrollLayout4 = null;
        }
        ((SectionHeaderNodeControllerImpl) this.incomingHeaderController).reinflateView(notificationStackScrollLayout4);
        NotificationStackScrollLayout notificationStackScrollLayout5 = this.parent;
        if (notificationStackScrollLayout5 == null) {
            notificationStackScrollLayout5 = null;
        }
        MediaContainerController mediaContainerController = this.mediaContainerController;
        MediaContainerView mediaContainerView = mediaContainerController.mediaContainerView;
        if (mediaContainerView != null) {
            mediaContainerView.removeFromTransientContainer();
            if (mediaContainerView.getParent() == notificationStackScrollLayout5) {
                iIndexOfChild = notificationStackScrollLayout5.indexOfChild(mediaContainerView);
                notificationStackScrollLayout5.removeView(mediaContainerView);
            } else {
                iIndexOfChild = -1;
            }
        }
        MediaContainerView mediaContainerView2 = (MediaContainerView) mediaContainerController.layoutInflater.inflate(R.layout.keyguard_media_container, (ViewGroup) notificationStackScrollLayout5, false);
        if (iIndexOfChild != -1) {
            notificationStackScrollLayout5.addView(mediaContainerView2, iIndexOfChild);
        }
        mediaContainerController.mediaContainerView = mediaContainerView2;
        this.keyguardMediaController.attachSinglePaneContainer(mediaContainerView2);
        NotificationStackScrollLayout notificationStackScrollLayout6 = this.parent;
        if (notificationStackScrollLayout6 == null) {
            notificationStackScrollLayout6 = null;
        }
        ((SectionHeaderNodeControllerImpl) this.newsHeaderController).reinflateView(notificationStackScrollLayout6);
        NotificationStackScrollLayout notificationStackScrollLayout7 = this.parent;
        if (notificationStackScrollLayout7 == null) {
            notificationStackScrollLayout7 = null;
        }
        ((SectionHeaderNodeControllerImpl) this.socialHeaderController).reinflateView(notificationStackScrollLayout7);
        NotificationStackScrollLayout notificationStackScrollLayout8 = this.parent;
        if (notificationStackScrollLayout8 == null) {
            notificationStackScrollLayout8 = null;
        }
        ((SectionHeaderNodeControllerImpl) this.recsHeaderController).reinflateView(notificationStackScrollLayout8);
        NotificationStackScrollLayout notificationStackScrollLayout9 = this.parent;
        if (notificationStackScrollLayout9 == null) {
            notificationStackScrollLayout9 = null;
        }
        ((SectionHeaderNodeControllerImpl) this.promoHeaderController).reinflateView(notificationStackScrollLayout9);
        NotificationStackScrollLayout notificationStackScrollLayout10 = this.parent;
        if (notificationStackScrollLayout10 == null) {
            notificationStackScrollLayout10 = null;
        }
        ((SectionHeaderNodeControllerImpl) this.favoriteHeaderController).reinflateView(notificationStackScrollLayout10);
        NotificationStackScrollLayout notificationStackScrollLayout11 = this.parent;
        ((SectionHeaderNodeControllerImpl) this.ongoingActivityHeaderController).reinflateView(notificationStackScrollLayout11 != null ? notificationStackScrollLayout11 : null);
    }

    public final void setHeaderForegroundColors(int i, int i2) {
        SectionHeaderView sectionHeaderView = ((SectionHeaderNodeControllerImpl) this.peopleHeaderController)._view;
        if (sectionHeaderView != null) {
            sectionHeaderView.setForegroundColors(i, i2);
        }
        SectionHeaderView sectionHeaderView2 = ((SectionHeaderNodeControllerImpl) this.silentHeaderController)._view;
        if (sectionHeaderView2 != null) {
            sectionHeaderView2.setForegroundColors(i, i2);
        }
        SectionHeaderView sectionHeaderView3 = ((SectionHeaderNodeControllerImpl) this.alertingHeaderController)._view;
        if (sectionHeaderView3 != null) {
            sectionHeaderView3.setForegroundColors(i, i2);
        }
        SectionHeaderView sectionHeaderView4 = ((SectionHeaderNodeControllerImpl) this.newsHeaderController)._view;
        if (sectionHeaderView4 != null) {
            sectionHeaderView4.setForegroundColors(i, i2);
        }
        SectionHeaderView sectionHeaderView5 = ((SectionHeaderNodeControllerImpl) this.socialHeaderController)._view;
        if (sectionHeaderView5 != null) {
            sectionHeaderView5.setForegroundColors(i, i2);
        }
        SectionHeaderView sectionHeaderView6 = ((SectionHeaderNodeControllerImpl) this.recsHeaderController)._view;
        if (sectionHeaderView6 != null) {
            sectionHeaderView6.setForegroundColors(i, i2);
        }
        SectionHeaderView sectionHeaderView7 = ((SectionHeaderNodeControllerImpl) this.promoHeaderController)._view;
        if (sectionHeaderView7 != null) {
            sectionHeaderView7.setForegroundColors(i, i2);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:107:0x0197  */
    /* JADX WARN: Removed duplicated region for block: B:146:0x0202  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateFirstAndLastViewsForAllSections(NotificationSection[] notificationSectionArr, List list) {
        NotificationRoundnessManager notificationRoundnessManager;
        float f;
        SourceType$Companion$from$1 sourceType$Companion$from$1;
        float f2;
        boolean firstAndLastVisibleChildren;
        Object many;
        Object one;
        NotificationSectionsManager$updateFirstAndLastViewsForAllSections$$inlined$groupingBy$1 notificationSectionsManager$updateFirstAndLastViewsForAllSections$$inlined$groupingBy$1 = new NotificationSectionsManager$updateFirstAndLastViewsForAllSections$$inlined$groupingBy$1(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(list), this);
        SectionBounds.None none = SectionBounds.None.INSTANCE;
        int length = notificationSectionArr.length;
        SparseArray sparseArray = length < 0 ? new SparseArray() : new SparseArray(length);
        for (Object obj : notificationSectionsManager$updateFirstAndLastViewsForAllSections$$inlined$groupingBy$1.$this_groupingBy) {
            int iIntValue = ((Number) notificationSectionsManager$updateFirstAndLastViewsForAllSections$$inlined$groupingBy$1.keyOf(obj)).intValue();
            Object obj2 = sparseArray.get(iIntValue);
            if (obj2 == null) {
                obj2 = none;
            }
            ExpandableView expandableView = (ExpandableView) obj;
            SectionBounds sectionBounds = (SectionBounds) obj2;
            sectionBounds.getClass();
            if (sectionBounds instanceof SectionBounds.None) {
                one = new SectionBounds.One(expandableView);
            } else {
                if (sectionBounds instanceof SectionBounds.One) {
                    many = new SectionBounds.Many(((SectionBounds.One) sectionBounds).lone, expandableView);
                } else {
                    if (!(sectionBounds instanceof SectionBounds.Many)) {
                        throw new NoWhenBranchMatchedException();
                    }
                    many = new SectionBounds.Many(((SectionBounds.Many) sectionBounds).first, expandableView);
                }
                one = many;
            }
            sparseArray.put(iIntValue, one);
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
        int length2 = notificationSectionArr.length;
        int i = 0;
        boolean z = false;
        while (true) {
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = null;
            if (i >= length2) {
                ArrayList arrayList3 = new ArrayList();
                for (NotificationSection notificationSection3 : notificationSectionArr) {
                    ExpandableView expandableView4 = notificationSection3.mFirstVisibleChild;
                    if (expandableView4 != null) {
                        arrayList3.add(expandableView4);
                    }
                }
                ArrayList arrayList4 = new ArrayList();
                for (NotificationSection notificationSection4 : notificationSectionArr) {
                    ExpandableView expandableView5 = notificationSection4.mLastVisibleChild;
                    if (expandableView5 != null) {
                        arrayList4.add(expandableView5);
                    }
                }
                int size = arrayList3.size();
                int i2 = 0;
                while (true) {
                    notificationRoundnessManager = this.notificationRoundnessManager;
                    f = 1.0f;
                    sourceType$Companion$from$1 = SECTION;
                    if (i2 >= size) {
                        break;
                    }
                    Object obj3 = arrayList3.get(i2);
                    i2++;
                    ExpandableView expandableView6 = (ExpandableView) obj3;
                    boolean zRemove = mutableSet.remove(expandableView6);
                    if (!zRemove) {
                        expandableView6.requestTopRoundness(1.0f, sourceType$Companion$from$1, expandableView6.isShown() && !notificationRoundnessManager.mAnimatedChildren.contains(expandableView6));
                    }
                    NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = this.sectionStateProvider;
                    if ((notificationStackScrollLayoutController2 != null ? notificationStackScrollLayoutController2 : notificationStackScrollLayoutController) != null) {
                        if (notificationStackScrollLayoutController2 != null) {
                            notificationStackScrollLayoutController = notificationStackScrollLayoutController2;
                        }
                        if (notificationStackScrollLayoutController.mBarState == 1) {
                            if (notificationStackScrollLayoutController2 == null) {
                                notificationStackScrollLayoutController2 = null;
                            }
                            if (notificationStackScrollLayoutController2.mView.getFirstVisibleSection() != null) {
                                NotificationStackScrollLayoutController notificationStackScrollLayoutController3 = this.sectionStateProvider;
                                if (notificationStackScrollLayoutController3 == null) {
                                    notificationStackScrollLayoutController3 = null;
                                }
                                if (notificationStackScrollLayoutController3.mView.getFirstVisibleSection().mFirstVisibleChild != expandableView6) {
                                    expandableView6.requestTopRoundness(0.0f, sourceType$Companion$from$1, false);
                                }
                            }
                        } else if (zRemove) {
                            expandableView6.requestTopRoundness(1.0f, sourceType$Companion$from$1, false);
                        }
                    }
                    notificationStackScrollLayoutController = null;
                }
                int size2 = arrayList4.size();
                int i3 = 0;
                while (i3 < size2) {
                    Object obj4 = arrayList4.get(i3);
                    i3++;
                    ExpandableView expandableView7 = (ExpandableView) obj4;
                    boolean zRemove2 = mutableSet2.remove(expandableView7);
                    if (!zRemove2) {
                        expandableView7.requestBottomRoundness(f, sourceType$Companion$from$1, expandableView7.isShown() && !notificationRoundnessManager.mAnimatedChildren.contains(expandableView7));
                    }
                    NotificationStackScrollLayoutController notificationStackScrollLayoutController4 = this.sectionStateProvider;
                    if ((notificationStackScrollLayoutController4 != null ? notificationStackScrollLayoutController4 : null) == null) {
                        f2 = f;
                    } else if ((notificationStackScrollLayoutController4 != null ? notificationStackScrollLayoutController4 : null).mBarState == 1) {
                        if (notificationStackScrollLayoutController4 == null) {
                            notificationStackScrollLayoutController4 = null;
                        }
                        if (notificationStackScrollLayoutController4.mView.getLastVisibleSection() != null) {
                            NotificationStackScrollLayoutController notificationStackScrollLayoutController5 = this.sectionStateProvider;
                            if (notificationStackScrollLayoutController5 == null) {
                                notificationStackScrollLayoutController5 = null;
                            }
                            if (notificationStackScrollLayoutController5.mView.getLastVisibleSection().mLastVisibleChild != expandableView7) {
                                expandableView7.requestBottomRoundness(0.0f, sourceType$Companion$from$1, false);
                            }
                            f2 = 1.0f;
                        }
                    } else if (zRemove2) {
                        f2 = 1.0f;
                        expandableView7.requestBottomRoundness(1.0f, sourceType$Companion$from$1, false);
                    } else {
                        f2 = 1.0f;
                    }
                    f = f2;
                }
                for (ExpandableView expandableView8 : mutableSet) {
                    expandableView8.requestTopRoundness(0.0f, sourceType$Companion$from$1, expandableView8.getRoundableState().targetView.isShown());
                }
                for (ExpandableView expandableView9 : mutableSet2) {
                    expandableView9.requestBottomRoundness(0.0f, sourceType$Companion$from$1, expandableView9.getRoundableState().targetView.isShown());
                }
                Stream stream = list.stream();
                final NotificationSectionsManager$$ExternalSyntheticLambda0 notificationSectionsManager$$ExternalSyntheticLambda0 = new NotificationSectionsManager$$ExternalSyntheticLambda0();
                stream.forEach(new Consumer() { // from class: com.android.systemui.statusbar.notification.stack.NotificationSectionsManager$sam$java_util_function_Consumer$0
                    @Override // java.util.function.Consumer
                    public final /* synthetic */ void accept(Object obj5) {
                        notificationSectionsManager$$ExternalSyntheticLambda0.mo781invoke(obj5);
                    }
                });
                return;
            }
            NotificationSection notificationSection5 = notificationSectionArr[i];
            Object obj5 = (SectionBounds) sparseArray.get(notificationSection5.mBucket);
            if (obj5 == null) {
                obj5 = SectionBounds.None.INSTANCE;
            }
            obj5.getClass();
            if (obj5 instanceof SectionBounds.None) {
                firstAndLastVisibleChildren = SectionBounds.setFirstAndLastVisibleChildren(notificationSection5, null, null);
            } else if (obj5 instanceof SectionBounds.One) {
                ExpandableView expandableView10 = ((SectionBounds.One) obj5).lone;
                firstAndLastVisibleChildren = SectionBounds.setFirstAndLastVisibleChildren(notificationSection5, expandableView10, expandableView10);
            } else {
                if (!(obj5 instanceof SectionBounds.Many)) {
                    throw new NoWhenBranchMatchedException();
                }
                SectionBounds.Many many2 = (SectionBounds.Many) obj5;
                firstAndLastVisibleChildren = SectionBounds.setFirstAndLastVisibleChildren(notificationSection5, many2.first, many2.last);
            }
            z = firstAndLastVisibleChildren || z;
            i++;
        }
    }

    public static /* synthetic */ void getAlertingHeaderView$annotations() {
    }

    public static /* synthetic */ void getFavoriteHeaderView$annotations() {
    }

    public static /* synthetic */ void getIncomingHeaderView$annotations() {
    }

    public static /* synthetic */ void getMediaControlsView$annotations() {
    }

    public static /* synthetic */ void getNewsHeaderView$annotations() {
    }

    public static /* synthetic */ void getOngoingActivityHeaderView$annotations() {
    }

    public static /* synthetic */ void getPeopleHeaderView$annotations() {
    }

    public static /* synthetic */ void getPromoHeaderView$annotations() {
    }

    public static /* synthetic */ void getRecsHeaderView$annotations() {
    }

    public static /* synthetic */ void getSilentHeaderView$annotations() {
    }

    public static /* synthetic */ void getSocialHeaderView$annotations() {
    }
}
