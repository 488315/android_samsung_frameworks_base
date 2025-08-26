package com.android.systemui.statusbar;

import android.R;
import android.app.Notification;
import android.graphics.drawable.Drawable;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.NotificationHeaderView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.internal.widget.CachingIconView;
import com.android.internal.widget.ConversationLayout;
import com.android.internal.widget.ImageFloatingTextView;
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

    interface DataExtractor {
        Object extractData(ExpandableNotificationRow expandableNotificationRow);
    }

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

    public class LeftIconApplicator implements ResultApplicator {
        public static final int[] MARGIN_ADJUSTED_VIEWS = {16909932, R.id.choice, R.id.title, R.id.remote_input_tag, R.id.remote_input};

        public /* synthetic */ LeftIconApplicator(int i) {
            this();
        }

        /* JADX WARN: Removed duplicated region for block: B:10:0x002d  */
        @Override // com.android.systemui.statusbar.NotificationGroupingUtil.ResultApplicator
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void apply(View view, View view2, boolean z, boolean z2) {
            boolean z3;
            ImageView imageView = (ImageView) view2.findViewById(R.id.multipleChoice);
            if (imageView == null) {
                return;
            }
            ImageView imageView2 = (ImageView) view2.findViewById(R.id.tag_top_override);
            if (imageView2 != null) {
                Integer num = 1;
                z3 = num.equals(imageView2.getTag(16909913));
            }
            Integer num2 = 1;
            if (num2.equals(imageView.getTag(16909919))) {
                Drawable drawable = null;
                Drawable drawable2 = imageView2 == null ? null : imageView2.getDrawable();
                if (z && !z3) {
                    drawable = drawable2;
                }
                imageView.setImageDrawable(drawable);
            }
            imageView.setVisibility(z ? 0 : 8);
            if (imageView2 != null) {
                boolean z4 = (z3 || !z) && imageView2.getDrawable() != null;
                imageView2.setVisibility(z4 ? 0 : 8);
                int[] iArr = MARGIN_ADJUSTED_VIEWS;
                for (int i = 0; i < 5; i++) {
                    ImageFloatingTextView imageFloatingTextViewFindViewById = view2.findViewById(iArr[i]);
                    if (imageFloatingTextViewFindViewById != null) {
                        if (imageFloatingTextViewFindViewById instanceof ImageFloatingTextView) {
                            imageFloatingTextViewFindViewById.setHasImage(z4);
                        } else {
                            Integer num3 = (Integer) imageFloatingTextViewFindViewById.getTag(z4 ? 16909916 : 16909915);
                            if (num3 != null) {
                                int iComplexToDimensionPixelOffset = TypedValue.complexToDimensionPixelOffset(num3.intValue(), imageFloatingTextViewFindViewById.getResources().getDisplayMetrics());
                                if (imageFloatingTextViewFindViewById instanceof NotificationHeaderView) {
                                    ((NotificationHeaderView) imageFloatingTextViewFindViewById).setTopLineExtraMarginEnd(iComplexToDimensionPixelOffset);
                                } else {
                                    ViewGroup.LayoutParams layoutParams = imageFloatingTextViewFindViewById.getLayoutParams();
                                    if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                                        ((ViewGroup.MarginLayoutParams) layoutParams).setMarginEnd(iComplexToDimensionPixelOffset);
                                        imageFloatingTextViewFindViewById.setLayoutParams(layoutParams);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        private LeftIconApplicator() {
        }
    }

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
            View viewFindViewById;
            if (view == null || (viewFindViewById = view.findViewById(this.mId)) == null || this.mComparator.isEmpty(viewFindViewById)) {
                return;
            }
            this.mApplicator.apply(view, viewFindViewById, z, z2);
        }
    }

    public interface ResultApplicator {
        void apply(View view, View view2, boolean z, boolean z2);
    }

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

    public interface ViewComparator {
        boolean compare(View view, View view2, Object obj, Object obj2);

        boolean isEmpty(View view);
    }

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
            /* JADX WARN: Removed duplicated region for block: B:13:0x0022  */
            @Override // com.android.systemui.statusbar.NotificationGroupingUtil.IconComparator, com.android.systemui.statusbar.NotificationGroupingUtil.ViewComparator
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final boolean compare(View view, View view2, Object obj, Object obj2) {
                if ((obj == null || obj2 == null) ? false : ((Notification) obj).getSmallIcon().sameAs(((Notification) obj2).getSmallIcon())) {
                    if ((obj != null || obj2 == null) && (obj == null || obj2 != null)) {
                        boolean z = (obj != null ? ((Notification) obj).color : 0) == (obj2 != null ? ((Notification) obj2).color : 0);
                        if (z) {
                            return true;
                        }
                    }
                }
                return false;
            }
        };
        new IconComparator(this) { // from class: com.android.systemui.statusbar.NotificationGroupingUtil.3
            /* JADX WARN: Removed duplicated region for block: B:13:0x0022  */
            @Override // com.android.systemui.statusbar.NotificationGroupingUtil.IconComparator, com.android.systemui.statusbar.NotificationGroupingUtil.ViewComparator
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final boolean compare(View view, View view2, Object obj, Object obj2) {
                if ((obj == null || obj2 == null) ? false : ((Notification) obj).getSmallIcon().sameAs(((Notification) obj2).getSmallIcon())) {
                    if ((obj != null || obj2 == null) && (obj == null || obj2 != null)) {
                        boolean z = (obj != null ? ((Notification) obj).color : 0) == (obj2 != null ? ((Notification) obj2).color : 0);
                        if (!z) {
                            return false;
                        }
                    }
                }
                return true;
            }
        };
        new ResultApplicator(this) { // from class: com.android.systemui.statusbar.NotificationGroupingUtil.4
            @Override // com.android.systemui.statusbar.NotificationGroupingUtil.ResultApplicator
            public final void apply(View view, View view2, boolean z, boolean z2) {
                CachingIconView cachingIconViewFindViewById = view2.findViewById(R.id.icon);
                if (cachingIconViewFindViewById != null) {
                    cachingIconViewFindViewById.setGrayedOut(z);
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
        this.mDividers.add(16909972);
    }

    public final void sanitizeTopLine(ViewGroup viewGroup, ExpandableNotificationRow expandableNotificationRow) {
        if (viewGroup == null) {
            return;
        }
        int childCount = viewGroup.getChildCount();
        View viewFindViewById = viewGroup.findViewById(16909968);
        int i = 0;
        int i2 = 0;
        while (true) {
            if (i2 < childCount) {
                View childAt = viewGroup.getChildAt(i2);
                if (!(childAt instanceof TextView) || childAt.getVisibility() == 8 || this.mDividers.contains(Integer.valueOf(childAt.getId())) || childAt == viewFindViewById) {
                    i2++;
                } else if (!showsTime(expandableNotificationRow)) {
                    i = 8;
                }
            }
        }
        try {
            viewFindViewById.setVisibility(i);
        } catch (Exception e) {
            Log.d("NotificationGroupingUtil", "Exception! " + e.toString());
        }
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
        View viewFindViewById;
        NotificationViewWrapper notificationViewWrapper;
        NotificationViewWrapper notificationViewWrapper2;
        ExpandableNotificationRow expandableNotificationRow = this.mRow;
        List attachedChildren = expandableNotificationRow.getAttachedChildren();
        if (attachedChildren == null || !expandableNotificationRow.mIsSummaryWithChildren) {
            return;
        }
        int i = 0;
        while (true) {
            Object objExtractData = null;
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
                objExtractData = dataExtractor.extractData(expandableNotificationRow2);
            }
            processor.mParentData = objExtractData;
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
                if (processor2.mApply && (view = expandableNotificationRow3.mPrivateLayout.mContractedChild) != null && (viewFindViewById = view.findViewById(processor2.mId)) != null) {
                    DataExtractor dataExtractor2 = processor2.mExtractor;
                    processor2.mApply = processor2.mComparator.compare(processor2.mParentView, viewFindViewById, processor2.mParentData, dataExtractor2 == null ? null : dataExtractor2.extractData(expandableNotificationRow3));
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
