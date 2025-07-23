package com.android.systemui.statusbar;

import android.R;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.internal.widget.CachingIconView;
import com.android.internal.widget.ConversationLayout;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.NotificationContentView;
import com.android.systemui.statusbar.notification.row.shared.AsyncGroupHeaderViewInflation;
import com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper;
import com.android.systemui.statusbar.notification.shared.NotificationBundleUi;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class NotificationGroupingUtil {
    public static final AppNameApplicator APP_NAME_APPLICATOR;
    public static final AppNameComparator APP_NAME_COMPARATOR;
    static final DataExtractor ICON_EXTRACTOR;
    public static final TextViewComparator TEXT_VIEW_COMPARATOR;
    public static final VisibilityApplicator VISIBILITY_APPLICATOR;
    public final HashSet mDividers;
    public final ArrayList mProcessors;
    public final ExpandableNotificationRow mRow;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class AppNameApplicator extends VisibilityApplicator {
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class AppNameComparator extends TextViewComparator {
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class BadgeComparator implements ViewComparator {
        public /* synthetic */ BadgeComparator(int i) {
            this();
        }

        @Override // com.android.systemui.statusbar.NotificationGroupingUtil.ViewComparator
        public final boolean compare(View view, View view2, Object obj, Object obj2) {
            return view.getVisibility() != 8;
        }

        @Override // com.android.systemui.statusbar.NotificationGroupingUtil.ViewComparator
        public final boolean isEmpty(View view) {
            int i = AsyncGroupHeaderViewInflation.$r8$clinit;
            return (view instanceof ImageView) && ((ImageView) view).getDrawable() == null;
        }

        private BadgeComparator() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    interface DataExtractor {
        Object extractData(ExpandableNotificationRow expandableNotificationRow);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    class IconComparator implements ViewComparator {
        @Override // com.android.systemui.statusbar.NotificationGroupingUtil.ViewComparator
        public boolean compare(View view, View view2, Object obj, Object obj2) {
            return false;
        }

        @Override // com.android.systemui.statusbar.NotificationGroupingUtil.ViewComparator
        public final boolean isEmpty(View view) {
            return false;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class LeftIconApplicator implements ResultApplicator {
        public static final int[] MARGIN_ADJUSTED_VIEWS = {16909931, R.id.choice, R.id.title, R.id.remote_input_tag, R.id.remote_input};

        public /* synthetic */ LeftIconApplicator(int i) {
            this();
        }

        /* JADX WARN: Removed duplicated region for block: B:10:0x003f  */
        /* JADX WARN: Removed duplicated region for block: B:20:0x0054  */
        /* JADX WARN: Removed duplicated region for block: B:23:0x005c  */
        /* JADX WARN: Removed duplicated region for block: B:59:0x00c6 A[ORIG_RETURN, RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:60:0x0056  */
        @Override // com.android.systemui.statusbar.NotificationGroupingUtil.ResultApplicator
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void apply(android.view.View r5, android.view.View r6, boolean r7, boolean r8) {
            /*
                r4 = this;
                r4 = 16909283(0x10203e3, float:2.3880018E-38)
                android.view.View r4 = r6.findViewById(r4)
                android.widget.ImageView r4 = (android.widget.ImageView) r4
                if (r4 != 0) goto Ld
                goto Lc6
            Ld:
                r5 = 16909624(0x1020538, float:2.3880973E-38)
                android.view.View r5 = r6.findViewById(r5)
                android.widget.ImageView r5 = (android.widget.ImageView) r5
                r8 = 1
                r0 = 0
                if (r5 == 0) goto L2d
                java.lang.Integer r1 = java.lang.Integer.valueOf(r8)
                r2 = 16909912(0x1020658, float:2.388178E-38)
                java.lang.Object r2 = r5.getTag(r2)
                boolean r1 = r1.equals(r2)
                if (r1 == 0) goto L2d
                r1 = r8
                goto L2e
            L2d:
                r1 = r0
            L2e:
                java.lang.Integer r2 = java.lang.Integer.valueOf(r8)
                r3 = 16909918(0x102065e, float:2.3881797E-38)
                java.lang.Object r3 = r4.getTag(r3)
                boolean r2 = r2.equals(r3)
                if (r2 == 0) goto L50
                r2 = 0
                if (r5 != 0) goto L44
                r3 = r2
                goto L48
            L44:
                android.graphics.drawable.Drawable r3 = r5.getDrawable()
            L48:
                if (r7 == 0) goto L4d
                if (r1 != 0) goto L4d
                r2 = r3
            L4d:
                r4.setImageDrawable(r2)
            L50:
                r2 = 8
                if (r7 == 0) goto L56
                r3 = r0
                goto L57
            L56:
                r3 = r2
            L57:
                r4.setVisibility(r3)
                if (r5 == 0) goto Lc6
                if (r1 != 0) goto L60
                if (r7 != 0) goto L67
            L60:
                android.graphics.drawable.Drawable r4 = r5.getDrawable()
                if (r4 == 0) goto L67
                goto L68
            L67:
                r8 = r0
            L68:
                if (r8 == 0) goto L6b
                r2 = r0
            L6b:
                r5.setVisibility(r2)
                int[] r4 = com.android.systemui.statusbar.NotificationGroupingUtil.LeftIconApplicator.MARGIN_ADJUSTED_VIEWS
            L70:
                r5 = 5
                if (r0 >= r5) goto Lc6
                r5 = r4[r0]
                android.view.View r5 = r6.findViewById(r5)
                if (r5 != 0) goto L7c
                goto Lc3
            L7c:
                boolean r7 = r5 instanceof com.android.internal.widget.ImageFloatingTextView
                if (r7 == 0) goto L86
                com.android.internal.widget.ImageFloatingTextView r5 = (com.android.internal.widget.ImageFloatingTextView) r5
                r5.setHasImage(r8)
                goto Lc3
            L86:
                if (r8 == 0) goto L8c
                r7 = 16909915(0x102065b, float:2.388179E-38)
                goto L8f
            L8c:
                r7 = 16909914(0x102065a, float:2.3881786E-38)
            L8f:
                java.lang.Object r7 = r5.getTag(r7)
                java.lang.Integer r7 = (java.lang.Integer) r7
                if (r7 != 0) goto L98
                goto Lc3
            L98:
                android.content.res.Resources r1 = r5.getResources()
                android.util.DisplayMetrics r1 = r1.getDisplayMetrics()
                int r7 = r7.intValue()
                int r7 = android.util.TypedValue.complexToDimensionPixelOffset(r7, r1)
                boolean r1 = r5 instanceof android.view.NotificationHeaderView
                if (r1 == 0) goto Lb2
                android.view.NotificationHeaderView r5 = (android.view.NotificationHeaderView) r5
                r5.setTopLineExtraMarginEnd(r7)
                goto Lc3
            Lb2:
                android.view.ViewGroup$LayoutParams r1 = r5.getLayoutParams()
                boolean r2 = r1 instanceof android.view.ViewGroup.MarginLayoutParams
                if (r2 == 0) goto Lc3
                r2 = r1
                android.view.ViewGroup$MarginLayoutParams r2 = (android.view.ViewGroup.MarginLayoutParams) r2
                r2.setMarginEnd(r7)
                r5.setLayoutParams(r1)
            Lc3:
                int r0 = r0 + 1
                goto L70
            Lc6:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.NotificationGroupingUtil.LeftIconApplicator.apply(android.view.View, android.view.View, boolean, boolean):void");
        }

        private LeftIconApplicator() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Processor {
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface ResultApplicator {
        void apply(View view, View view2, boolean z, boolean z2);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class TextViewComparator implements ViewComparator {
        public /* synthetic */ TextViewComparator(int i) {
            this();
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

        private TextViewComparator() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface ViewComparator {
        boolean compare(View view, View view2, Object obj, Object obj2);

        boolean isEmpty(View view);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class VisibilityApplicator implements ResultApplicator {
        public /* synthetic */ VisibilityApplicator(int i) {
            this();
        }

        @Override // com.android.systemui.statusbar.NotificationGroupingUtil.ResultApplicator
        public void apply(View view, View view2, boolean z, boolean z2) {
            view2.setVisibility(z ? 8 : 0);
        }

        private VisibilityApplicator() {
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
        ICON_EXTRACTOR = new DataExtractor() { // from class: com.android.systemui.statusbar.NotificationGroupingUtil.1
            @Override // com.android.systemui.statusbar.NotificationGroupingUtil.DataExtractor
            public final Object extractData(ExpandableNotificationRow expandableNotificationRow) {
                int i2 = NotificationBundleUi.$r8$clinit;
                return expandableNotificationRow.getEntryLegacy().mSbn.getNotification();
            }
        };
    }

    public NotificationGroupingUtil(ExpandableNotificationRow expandableNotificationRow) {
        ArrayList arrayList = new ArrayList();
        this.mProcessors = arrayList;
        this.mDividers = new HashSet();
        this.mRow = expandableNotificationRow;
        new IconComparator(this) { // from class: com.android.systemui.statusbar.NotificationGroupingUtil.2
            /* JADX WARN: Removed duplicated region for block: B:11:0x003e A[RETURN] */
            @Override // com.android.systemui.statusbar.NotificationGroupingUtil.IconComparator, com.android.systemui.statusbar.NotificationGroupingUtil.ViewComparator
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final boolean compare(android.view.View r1, android.view.View r2, java.lang.Object r3, java.lang.Object r4) {
                /*
                    r0 = this;
                    r0 = 0
                    if (r3 == 0) goto L19
                    if (r4 != 0) goto L6
                    goto L19
                L6:
                    r1 = r3
                    android.app.Notification r1 = (android.app.Notification) r1
                    android.graphics.drawable.Icon r1 = r1.getSmallIcon()
                    r2 = r4
                    android.app.Notification r2 = (android.app.Notification) r2
                    android.graphics.drawable.Icon r2 = r2.getSmallIcon()
                    boolean r1 = r1.sameAs(r2)
                    goto L1a
                L19:
                    r1 = r0
                L1a:
                    if (r1 == 0) goto L3f
                    r1 = 1
                    if (r3 != 0) goto L24
                    if (r4 != 0) goto L22
                    goto L24
                L22:
                    r2 = r0
                    goto L3c
                L24:
                    if (r3 == 0) goto L29
                    if (r4 != 0) goto L29
                    goto L22
                L29:
                    if (r3 == 0) goto L30
                    android.app.Notification r3 = (android.app.Notification) r3
                    int r2 = r3.color
                    goto L31
                L30:
                    r2 = r0
                L31:
                    if (r4 == 0) goto L38
                    android.app.Notification r4 = (android.app.Notification) r4
                    int r3 = r4.color
                    goto L39
                L38:
                    r3 = r0
                L39:
                    if (r2 != r3) goto L22
                    r2 = r1
                L3c:
                    if (r2 == 0) goto L3f
                    return r1
                L3f:
                    return r0
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.NotificationGroupingUtil.AnonymousClass2.compare(android.view.View, android.view.View, java.lang.Object, java.lang.Object):boolean");
            }
        };
        new IconComparator(this) { // from class: com.android.systemui.statusbar.NotificationGroupingUtil.3
            /* JADX WARN: Removed duplicated region for block: B:11:0x003f A[RETURN] */
            @Override // com.android.systemui.statusbar.NotificationGroupingUtil.IconComparator, com.android.systemui.statusbar.NotificationGroupingUtil.ViewComparator
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final boolean compare(android.view.View r1, android.view.View r2, java.lang.Object r3, java.lang.Object r4) {
                /*
                    r0 = this;
                    r0 = 0
                    if (r3 == 0) goto L19
                    if (r4 != 0) goto L6
                    goto L19
                L6:
                    r1 = r3
                    android.app.Notification r1 = (android.app.Notification) r1
                    android.graphics.drawable.Icon r1 = r1.getSmallIcon()
                    r2 = r4
                    android.app.Notification r2 = (android.app.Notification) r2
                    android.graphics.drawable.Icon r2 = r2.getSmallIcon()
                    boolean r1 = r1.sameAs(r2)
                    goto L1a
                L19:
                    r1 = r0
                L1a:
                    r2 = 1
                    if (r1 == 0) goto L40
                    if (r3 != 0) goto L24
                    if (r4 != 0) goto L22
                    goto L24
                L22:
                    r1 = r0
                    goto L3c
                L24:
                    if (r3 == 0) goto L29
                    if (r4 != 0) goto L29
                    goto L22
                L29:
                    if (r3 == 0) goto L30
                    android.app.Notification r3 = (android.app.Notification) r3
                    int r1 = r3.color
                    goto L31
                L30:
                    r1 = r0
                L31:
                    if (r4 == 0) goto L38
                    android.app.Notification r4 = (android.app.Notification) r4
                    int r3 = r4.color
                    goto L39
                L38:
                    r3 = r0
                L39:
                    if (r1 != r3) goto L22
                    r1 = r2
                L3c:
                    if (r1 == 0) goto L3f
                    goto L40
                L3f:
                    return r0
                L40:
                    return r2
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.NotificationGroupingUtil.AnonymousClass3.compare(android.view.View, android.view.View, java.lang.Object, java.lang.Object):boolean");
            }
        };
        new ResultApplicator(this) { // from class: com.android.systemui.statusbar.NotificationGroupingUtil.4
            @Override // com.android.systemui.statusbar.NotificationGroupingUtil.ResultApplicator
            public final void apply(View view, View view2, boolean z, boolean z2) {
                CachingIconView findViewById = view2.findViewById(R.id.icon);
                if (findViewById != null) {
                    findViewById.setGrayedOut(z);
                }
            }
        };
        arrayList.add(new Processor(expandableNotificationRow, R.id.beforeDescendants, null, APP_NAME_COMPARATOR, APP_NAME_APPLICATOR));
        expandableNotificationRow.getClass();
        NotificationContentView[] notificationContentViewArr = expandableNotificationRow.mLayouts;
        NotificationContentView[] notificationContentViewArr2 = (NotificationContentView[]) Arrays.copyOf(notificationContentViewArr, notificationContentViewArr.length);
        int length = notificationContentViewArr2.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                this.mProcessors.add(new Processor(this.mRow, R.id.inter_word, null, TEXT_VIEW_COMPARATOR, VISIBILITY_APPLICATOR));
                break;
            }
            NotificationContentView notificationContentView = notificationContentViewArr2[i];
            if (notificationContentView.mIsContractedHeaderContainAtMark || notificationContentView.mIsExpandedHeaderContainAtMark) {
                break;
            } else {
                i++;
            }
        }
        this.mDividers.add(Integer.valueOf(R.id.internal));
        this.mDividers.add(Integer.valueOf(R.id.internalOnly));
        this.mDividers.add(16909971);
    }

    public final void sanitizeTopLine(ViewGroup viewGroup, ExpandableNotificationRow expandableNotificationRow) {
        if (viewGroup == null) {
            return;
        }
        int childCount = viewGroup.getChildCount();
        View findViewById = viewGroup.findViewById(16909967);
        int i = 0;
        int i2 = 0;
        while (true) {
            if (i2 >= childCount) {
                break;
            }
            View childAt = viewGroup.getChildAt(i2);
            if (!(childAt instanceof TextView) || childAt.getVisibility() == 8 || this.mDividers.contains(Integer.valueOf(childAt.getId())) || childAt == findViewById) {
                i2++;
            } else if (!showsTime(expandableNotificationRow)) {
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
                sanitizeTopLine((ViewGroup) view.findViewById(R.id.resolver_list), expandableNotificationRow);
            }
            View view2 = notificationContentView.mHeadsUpChild;
            if (view2 != null) {
                sanitizeTopLine((ViewGroup) view2.findViewById(R.id.resolver_list), expandableNotificationRow);
            }
            View view3 = notificationContentView.mExpandedChild;
            if (view3 != null) {
                sanitizeTopLine((ViewGroup) view3.findViewById(R.id.resolver_list), expandableNotificationRow);
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

    public boolean showsTime(ExpandableNotificationRow expandableNotificationRow) {
        int i = NotificationBundleUi.$r8$clinit;
        StatusBarNotification statusBarNotification = expandableNotificationRow.getEntryLegacy().mSbn;
        return statusBarNotification != null && statusBarNotification.getNotification().showsTime();
    }

    public final void updateChildrenAppearance() {
        ArrayList arrayList;
        View view;
        View findViewById;
        NotificationViewWrapper notificationViewWrapper;
        NotificationViewWrapper notificationViewWrapper2;
        ExpandableNotificationRow expandableNotificationRow = this.mRow;
        List attachedChildren = expandableNotificationRow.getAttachedChildren();
        if (attachedChildren == null || !expandableNotificationRow.mIsSummaryWithChildren) {
            return;
        }
        int i = 0;
        while (true) {
            Object obj = null;
            if (i >= this.mProcessors.size()) {
                break;
            }
            Processor processor = (Processor) this.mProcessors.get(i);
            ExpandableNotificationRow expandableNotificationRow2 = processor.mParentRow;
            if (expandableNotificationRow2.mIsSummaryWithChildren) {
                notificationViewWrapper = expandableNotificationRow2.mChildrenContainer.mGroupHeaderWrapper;
            } else {
                NotificationContentView notificationContentView = expandableNotificationRow2.mPrivateLayout;
                if ((notificationContentView.mContractedChild != null && (notificationViewWrapper2 = notificationContentView.mContractedWrapper) != null) || (notificationContentView.mExpandedChild != null && (notificationViewWrapper2 = notificationContentView.mExpandedWrapper) != null)) {
                    notificationViewWrapper = notificationViewWrapper2;
                } else if (notificationContentView.mHeadsUpChild == null || (notificationViewWrapper = notificationContentView.mHeadsUpWrapper) == null) {
                    notificationViewWrapper = null;
                }
            }
            View notificationHeader = notificationViewWrapper == null ? null : notificationViewWrapper.getNotificationHeader();
            processor.mParentView = notificationHeader == null ? null : notificationHeader.findViewById(processor.mId);
            DataExtractor dataExtractor = processor.mExtractor;
            if (dataExtractor != null) {
                obj = dataExtractor.extractData(expandableNotificationRow2);
            }
            processor.mParentData = obj;
            processor.mApply = !processor.mComparator.isEmpty(processor.mParentView);
            i++;
        }
        int i2 = 0;
        while (true) {
            arrayList = (ArrayList) attachedChildren;
            if (i2 >= arrayList.size()) {
                break;
            }
            ExpandableNotificationRow expandableNotificationRow3 = (ExpandableNotificationRow) arrayList.get(i2);
            for (int i3 = 0; i3 < this.mProcessors.size(); i3++) {
                Processor processor2 = (Processor) this.mProcessors.get(i3);
                if (processor2.mApply && (view = expandableNotificationRow3.mPrivateLayout.mContractedChild) != null && (findViewById = view.findViewById(processor2.mId)) != null) {
                    DataExtractor dataExtractor2 = processor2.mExtractor;
                    processor2.mApply = processor2.mComparator.compare(processor2.mParentView, findViewById, processor2.mParentData, dataExtractor2 == null ? null : dataExtractor2.extractData(expandableNotificationRow3));
                }
            }
            i2++;
        }
        for (int i4 = 0; i4 < arrayList.size(); i4++) {
            ExpandableNotificationRow expandableNotificationRow4 = (ExpandableNotificationRow) arrayList.get(i4);
            for (int i5 = 0; i5 < this.mProcessors.size(); i5++) {
                ((Processor) this.mProcessors.get(i5)).apply(expandableNotificationRow4, false);
            }
            sanitizeTopLineViews(expandableNotificationRow4);
        }
    }
}
