package com.android.systemui.statusbar;

import android.R;
import android.app.Notification;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.internal.widget.CachingIconView;
import com.android.internal.widget.ConversationLayout;
import com.android.systemui.Flags;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.NotificationContentView;
import com.android.systemui.statusbar.notification.row.shared.AsyncGroupHeaderViewInflation;
import com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class NotificationGroupingUtil {
    public static final AppNameApplicator APP_NAME_APPLICATOR;
    public static final AppNameComparator APP_NAME_COMPARATOR;
    public static final TextViewComparator TEXT_VIEW_COMPARATOR;
    public static final VisibilityApplicator VISIBILITY_APPLICATOR;
    public final HashSet mDividers;
    public final ArrayList mProcessors;
    public final ExpandableNotificationRow mRow;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class AppNameApplicator extends VisibilityApplicator {
        public /* synthetic */ AppNameApplicator(int i) {
            this();
        }

        @Override // com.android.systemui.statusbar.NotificationGroupingUtil.VisibilityApplicator, com.android.systemui.statusbar.NotificationGroupingUtil.ResultApplicator
        public final void apply(View view, View view2, boolean z, boolean z2) {
            if (z2 && (view instanceof ConversationLayout)) {
                z = ((ConversationLayout) view).shouldHideAppName();
            }
            super.apply(view, view2, z, z2);
        }

        private AppNameApplicator() {
            super(0);
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class AppNameComparator extends TextViewComparator {
        public /* synthetic */ AppNameComparator(int i) {
            this();
        }

        @Override // com.android.systemui.statusbar.NotificationGroupingUtil.TextViewComparator, com.android.systemui.statusbar.NotificationGroupingUtil.ViewComparator
        public final boolean compare(View view, View view2, Object obj, Object obj2) {
            if (isEmpty(view2)) {
                return true;
            }
            return super.compare(view, view2, obj, obj2);
        }

        private AppNameComparator() {
            super(0);
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class BadgeComparator implements ViewComparator {
        private BadgeComparator() {
        }

        @Override // com.android.systemui.statusbar.NotificationGroupingUtil.ViewComparator
        public final boolean compare(View view, View view2, Object obj, Object obj2) {
            return view.getVisibility() != 8;
        }

        @Override // com.android.systemui.statusbar.NotificationGroupingUtil.ViewComparator
        public final boolean isEmpty(View view) {
            int i = AsyncGroupHeaderViewInflation.$r8$clinit;
            Flags.notificationAsyncGroupHeaderInflation();
            return (view instanceof ImageView) && ((ImageView) view).getDrawable() == null;
        }

        public /* synthetic */ BadgeComparator(int i) {
            this();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface DataExtractor {
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public abstract class IconComparator implements ViewComparator {
        private IconComparator() {
        }

        @Override // com.android.systemui.statusbar.NotificationGroupingUtil.ViewComparator
        public boolean compare(View view, View view2, Object obj, Object obj2) {
            return false;
        }

        @Override // com.android.systemui.statusbar.NotificationGroupingUtil.ViewComparator
        public final boolean isEmpty(View view) {
            return false;
        }

        public /* synthetic */ IconComparator(int i) {
            this();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class LeftIconApplicator implements ResultApplicator {
        public static final int[] MARGIN_ADJUSTED_VIEWS = {16909869, R.id.button5, R.id.title, R.id.popup_submenu_presenter, R.id.placeholder};

        private LeftIconApplicator() {
        }

        /* JADX WARN: Removed duplicated region for block: B:12:0x003e  */
        /* JADX WARN: Removed duplicated region for block: B:22:0x0053  */
        /* JADX WARN: Removed duplicated region for block: B:25:0x005b  */
        /* JADX WARN: Removed duplicated region for block: B:60:0x00c5 A[ORIG_RETURN, RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:61:0x0055  */
        @Override // com.android.systemui.statusbar.NotificationGroupingUtil.ResultApplicator
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void apply(android.view.View r5, android.view.View r6, boolean r7, boolean r8) {
            /*
                r4 = this;
                r4 = 16909243(0x10203bb, float:2.3879905E-38)
                android.view.View r4 = r6.findViewById(r4)
                android.widget.ImageView r4 = (android.widget.ImageView) r4
                if (r4 != 0) goto Lc
                return
            Lc:
                r5 = 16909576(0x1020508, float:2.388084E-38)
                android.view.View r5 = r6.findViewById(r5)
                android.widget.ImageView r5 = (android.widget.ImageView) r5
                r8 = 1
                r0 = 0
                if (r5 == 0) goto L2c
                java.lang.Integer r1 = java.lang.Integer.valueOf(r8)
                r2 = 16909850(0x102061a, float:2.3881607E-38)
                java.lang.Object r2 = r5.getTag(r2)
                boolean r1 = r1.equals(r2)
                if (r1 == 0) goto L2c
                r1 = r8
                goto L2d
            L2c:
                r1 = r0
            L2d:
                java.lang.Integer r2 = java.lang.Integer.valueOf(r8)
                r3 = 16909856(0x1020620, float:2.3881623E-38)
                java.lang.Object r3 = r4.getTag(r3)
                boolean r2 = r2.equals(r3)
                if (r2 == 0) goto L4f
                r2 = 0
                if (r5 != 0) goto L43
                r3 = r2
                goto L47
            L43:
                android.graphics.drawable.Drawable r3 = r5.getDrawable()
            L47:
                if (r7 == 0) goto L4c
                if (r1 != 0) goto L4c
                r2 = r3
            L4c:
                r4.setImageDrawable(r2)
            L4f:
                r2 = 8
                if (r7 == 0) goto L55
                r3 = r0
                goto L56
            L55:
                r3 = r2
            L56:
                r4.setVisibility(r3)
                if (r5 == 0) goto Lc5
                if (r1 != 0) goto L5f
                if (r7 != 0) goto L66
            L5f:
                android.graphics.drawable.Drawable r4 = r5.getDrawable()
                if (r4 == 0) goto L66
                goto L67
            L66:
                r8 = r0
            L67:
                if (r8 == 0) goto L6a
                r2 = r0
            L6a:
                r5.setVisibility(r2)
                int[] r4 = com.android.systemui.statusbar.NotificationGroupingUtil.LeftIconApplicator.MARGIN_ADJUSTED_VIEWS
            L6f:
                r5 = 5
                if (r0 >= r5) goto Lc5
                r5 = r4[r0]
                android.view.View r5 = r6.findViewById(r5)
                if (r5 != 0) goto L7b
                goto Lc2
            L7b:
                boolean r7 = r5 instanceof com.android.internal.widget.ImageFloatingTextView
                if (r7 == 0) goto L85
                com.android.internal.widget.ImageFloatingTextView r5 = (com.android.internal.widget.ImageFloatingTextView) r5
                r5.setHasImage(r8)
                goto Lc2
            L85:
                if (r8 == 0) goto L8b
                r7 = 16909853(0x102061d, float:2.3881615E-38)
                goto L8e
            L8b:
                r7 = 16909852(0x102061c, float:2.3881612E-38)
            L8e:
                java.lang.Object r7 = r5.getTag(r7)
                java.lang.Integer r7 = (java.lang.Integer) r7
                if (r7 != 0) goto L97
                goto Lc2
            L97:
                android.content.res.Resources r1 = r5.getResources()
                android.util.DisplayMetrics r1 = r1.getDisplayMetrics()
                int r7 = r7.intValue()
                int r7 = android.util.TypedValue.complexToDimensionPixelOffset(r7, r1)
                boolean r1 = r5 instanceof android.view.NotificationHeaderView
                if (r1 == 0) goto Lb1
                android.view.NotificationHeaderView r5 = (android.view.NotificationHeaderView) r5
                r5.setTopLineExtraMarginEnd(r7)
                goto Lc2
            Lb1:
                android.view.ViewGroup$LayoutParams r1 = r5.getLayoutParams()
                boolean r2 = r1 instanceof android.view.ViewGroup.MarginLayoutParams
                if (r2 == 0) goto Lc2
                r2 = r1
                android.view.ViewGroup$MarginLayoutParams r2 = (android.view.ViewGroup.MarginLayoutParams) r2
                r2.setMarginEnd(r7)
                r5.setLayoutParams(r1)
            Lc2:
                int r0 = r0 + 1
                goto L6f
            Lc5:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.NotificationGroupingUtil.LeftIconApplicator.apply(android.view.View, android.view.View, boolean, boolean):void");
        }

        public /* synthetic */ LeftIconApplicator(int i) {
            this();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Processor {
        public final ResultApplicator mApplicator;
        public boolean mApply;
        public final ViewComparator mComparator;
        public final DataExtractor mExtractor;
        public final int mId;
        public Object mParentData;
        public final ExpandableNotificationRow mParentRow;
        public View mParentView;

        public Processor(ExpandableNotificationRow expandableNotificationRow, int i, DataExtractor dataExtractor, ViewComparator viewComparator, ResultApplicator resultApplicator) {
            this.mId = i;
            this.mExtractor = dataExtractor;
            this.mApplicator = resultApplicator;
            this.mComparator = viewComparator;
            this.mParentRow = expandableNotificationRow;
        }

        public final void apply(ExpandableNotificationRow expandableNotificationRow, boolean z) {
            NotificationViewWrapper notificationViewWrapper;
            NotificationViewWrapper notificationViewWrapper2;
            boolean z2 = this.mApply && !z;
            boolean z3 = expandableNotificationRow.mIsSummaryWithChildren;
            if (!z3) {
                applyToView(expandableNotificationRow.mPrivateLayout.mContractedChild, z2, z);
                applyToView(expandableNotificationRow.mPrivateLayout.mHeadsUpChild, z2, z);
                applyToView(expandableNotificationRow.mPrivateLayout.mExpandedChild, z2, z);
                return;
            }
            if (z3) {
                notificationViewWrapper = expandableNotificationRow.mChildrenContainer.mGroupHeaderWrapper;
            } else {
                NotificationContentView notificationContentView = expandableNotificationRow.mPrivateLayout;
                if ((notificationContentView.mContractedChild != null && (notificationViewWrapper2 = notificationContentView.mContractedWrapper) != null) || (notificationContentView.mExpandedChild != null && (notificationViewWrapper2 = notificationContentView.mExpandedWrapper) != null)) {
                    notificationViewWrapper = notificationViewWrapper2;
                } else if (notificationContentView.mHeadsUpChild == null || (notificationViewWrapper = notificationContentView.mHeadsUpWrapper) == null) {
                    notificationViewWrapper = null;
                }
            }
            applyToView(notificationViewWrapper.getNotificationHeader(), z2, z);
        }

        public final void applyToView(View view, boolean z, boolean z2) {
            View findViewById;
            if (view == null || (findViewById = view.findViewById(this.mId)) == null || this.mComparator.isEmpty(findViewById)) {
                return;
            }
            this.mApplicator.apply(view, findViewById, z, z2);
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface ResultApplicator {
        void apply(View view, View view2, boolean z, boolean z2);
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public class TextViewComparator implements ViewComparator {
        private TextViewComparator() {
        }

        @Override // com.android.systemui.statusbar.NotificationGroupingUtil.ViewComparator
        public boolean compare(View view, View view2, Object obj, Object obj2) {
            TextView textView = (TextView) view;
            return Objects.equals(textView == null ? "" : textView.getText(), ((TextView) view2).getText());
        }

        @Override // com.android.systemui.statusbar.NotificationGroupingUtil.ViewComparator
        public final boolean isEmpty(View view) {
            return view == null || TextUtils.isEmpty(((TextView) view).getText());
        }

        public /* synthetic */ TextViewComparator(int i) {
            this();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface ViewComparator {
        boolean compare(View view, View view2, Object obj, Object obj2);

        boolean isEmpty(View view);
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public class VisibilityApplicator implements ResultApplicator {
        private VisibilityApplicator() {
        }

        @Override // com.android.systemui.statusbar.NotificationGroupingUtil.ResultApplicator
        public void apply(View view, View view2, boolean z, boolean z2) {
            view2.setVisibility(z ? 8 : 0);
        }

        public /* synthetic */ VisibilityApplicator(int i) {
            this();
        }
    }

    static {
        int i = 0;
        TEXT_VIEW_COMPARATOR = new TextViewComparator(i);
        APP_NAME_COMPARATOR = new AppNameComparator(i);
        new BadgeComparator(i);
        VISIBILITY_APPLICATOR = new VisibilityApplicator(i);
        APP_NAME_APPLICATOR = new AppNameApplicator(i);
        new LeftIconApplicator(i);
        new DataExtractor() { // from class: com.android.systemui.statusbar.NotificationGroupingUtil.1
        };
        new IconComparator() { // from class: com.android.systemui.statusbar.NotificationGroupingUtil.2
            @Override // com.android.systemui.statusbar.NotificationGroupingUtil.IconComparator, com.android.systemui.statusbar.NotificationGroupingUtil.ViewComparator
            public final boolean compare(View view, View view2, Object obj, Object obj2) {
                Notification notification2 = (Notification) obj;
                Notification notification3 = (Notification) obj2;
                if ((notification2.shouldUseAppIcon() ? notification2.getAppIcon() : notification2.getSmallIcon()).sameAs(notification3.shouldUseAppIcon() ? notification3.getAppIcon() : notification3.getSmallIcon())) {
                    return (notification2.shouldUseAppIcon() ? 0 : notification2.color) == (notification3.shouldUseAppIcon() ? 0 : notification3.color);
                }
                return false;
            }
        };
        new IconComparator() { // from class: com.android.systemui.statusbar.NotificationGroupingUtil.3
            @Override // com.android.systemui.statusbar.NotificationGroupingUtil.IconComparator, com.android.systemui.statusbar.NotificationGroupingUtil.ViewComparator
            public final boolean compare(View view, View view2, Object obj, Object obj2) {
                Notification notification2 = (Notification) obj;
                Notification notification3 = (Notification) obj2;
                if ((notification2.shouldUseAppIcon() ? notification2.getAppIcon() : notification2.getSmallIcon()).sameAs(notification3.shouldUseAppIcon() ? notification3.getAppIcon() : notification3.getSmallIcon())) {
                    if ((notification2.shouldUseAppIcon() ? 0 : notification2.color) != (notification3.shouldUseAppIcon() ? 0 : notification3.color)) {
                        return false;
                    }
                }
                return true;
            }
        };
        new ResultApplicator() { // from class: com.android.systemui.statusbar.NotificationGroupingUtil.4
            @Override // com.android.systemui.statusbar.NotificationGroupingUtil.ResultApplicator
            public final void apply(View view, View view2, boolean z, boolean z2) {
                CachingIconView findViewById = view2.findViewById(R.id.icon);
                if (findViewById != null) {
                    findViewById.setGrayedOut(z);
                }
            }
        };
    }

    public NotificationGroupingUtil(ExpandableNotificationRow expandableNotificationRow) {
        ArrayList arrayList = new ArrayList();
        this.mProcessors = arrayList;
        this.mDividers = new HashSet();
        this.mRow = expandableNotificationRow;
        arrayList.add(new Processor(expandableNotificationRow, R.id.authtoken_type, null, APP_NAME_COMPARATOR, APP_NAME_APPLICATOR));
        if (!expandableNotificationRow.mIsGroupHeaderContainAtMark) {
            NotificationContentView[] notificationContentViewArr = expandableNotificationRow.mLayouts;
            NotificationContentView[] notificationContentViewArr2 = (NotificationContentView[]) Arrays.copyOf(notificationContentViewArr, notificationContentViewArr.length);
            int length = notificationContentViewArr2.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    this.mProcessors.add(new Processor(this.mRow, R.id.inbox_text0, null, TEXT_VIEW_COMPARATOR, VISIBILITY_APPLICATOR));
                    break;
                }
                NotificationContentView notificationContentView = notificationContentViewArr2[i];
                if (notificationContentView.mIsContractedHeaderContainAtMark || notificationContentView.mIsExpandedHeaderContainAtMark) {
                    break;
                } else {
                    i++;
                }
            }
        }
        this.mDividers.add(Integer.valueOf(R.id.inbox_text1));
        this.mDividers.add(Integer.valueOf(R.id.inbox_text3));
        this.mDividers.add(16909909);
    }

    public final void sanitizeTopLine(ViewGroup viewGroup, ExpandableNotificationRow expandableNotificationRow) {
        if (viewGroup == null) {
            return;
        }
        int childCount = viewGroup.getChildCount();
        View findViewById = viewGroup.findViewById(16909905);
        int i = 0;
        int i2 = 0;
        while (true) {
            if (i2 >= childCount) {
                break;
            }
            View childAt = viewGroup.getChildAt(i2);
            if (!(childAt instanceof TextView) || childAt.getVisibility() == 8 || this.mDividers.contains(Integer.valueOf(childAt.getId())) || childAt == findViewById) {
                i2++;
            } else if (!expandableNotificationRow.mEntry.mSbn.getNotification().showsTime()) {
                i = 8;
            }
        }
        findViewById.setVisibility(i);
    }

    public final void sanitizeTopLineViews(ExpandableNotificationRow expandableNotificationRow) {
        NotificationViewWrapper notificationViewWrapper;
        NotificationViewWrapper notificationViewWrapper2;
        boolean z = expandableNotificationRow.mIsSummaryWithChildren;
        if (!z) {
            NotificationContentView notificationContentView = expandableNotificationRow.mPrivateLayout;
            View view = notificationContentView.mContractedChild;
            if (view != null) {
                sanitizeTopLine((ViewGroup) view.findViewById(R.id.progressContainer), expandableNotificationRow);
            }
            View view2 = notificationContentView.mHeadsUpChild;
            if (view2 != null) {
                sanitizeTopLine((ViewGroup) view2.findViewById(R.id.progressContainer), expandableNotificationRow);
            }
            View view3 = notificationContentView.mExpandedChild;
            if (view3 != null) {
                sanitizeTopLine((ViewGroup) view3.findViewById(R.id.progressContainer), expandableNotificationRow);
                return;
            }
            return;
        }
        if (z) {
            notificationViewWrapper = expandableNotificationRow.mChildrenContainer.mGroupHeaderWrapper;
        } else {
            NotificationContentView notificationContentView2 = expandableNotificationRow.mPrivateLayout;
            if ((notificationContentView2.mContractedChild != null && (notificationViewWrapper2 = notificationContentView2.mContractedWrapper) != null) || (notificationContentView2.mExpandedChild != null && (notificationViewWrapper2 = notificationContentView2.mExpandedWrapper) != null)) {
                notificationViewWrapper = notificationViewWrapper2;
            } else if (notificationContentView2.mHeadsUpChild == null || (notificationViewWrapper = notificationContentView2.mHeadsUpWrapper) == null) {
                notificationViewWrapper = null;
            }
        }
        sanitizeTopLine(notificationViewWrapper.getNotificationHeader(), expandableNotificationRow);
    }
}
