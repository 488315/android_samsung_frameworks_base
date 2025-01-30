package com.android.systemui.statusbar;

import android.R;
import android.app.Notification;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NotificationGroupingUtil {
    public static final AppNameApplicator APP_NAME_APPLICATOR;
    public static final AppNameComparator APP_NAME_COMPARATOR;
    public static final BadgeComparator BADGE_COMPARATOR;
    public static final TextViewComparator TEXT_VIEW_COMPARATOR;
    public static final VisibilityApplicator VISIBILITY_APPLICATOR;
    public final HashSet mDividers;
    public final ArrayList mProcessors;
    public final ExpandableNotificationRow mRow;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class BadgeComparator implements ViewComparator {
        private BadgeComparator() {
        }

        public /* synthetic */ BadgeComparator(int i) {
            this();
        }

        @Override // com.android.systemui.statusbar.NotificationGroupingUtil.ViewComparator
        public final boolean compare(View view, View view2, Object obj, Object obj2) {
            return view.getVisibility() != 8;
        }

        @Override // com.android.systemui.statusbar.NotificationGroupingUtil.ViewComparator
        public final boolean isEmpty(View view) {
            return (view instanceof ImageView) && ((ImageView) view).getDrawable() == null;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface DataExtractor {
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class IconComparator implements ViewComparator {
        private IconComparator() {
        }

        public /* synthetic */ IconComparator(int i) {
            this();
        }

        @Override // com.android.systemui.statusbar.NotificationGroupingUtil.ViewComparator
        public boolean compare(View view, View view2, Object obj, Object obj2) {
            return false;
        }

        @Override // com.android.systemui.statusbar.NotificationGroupingUtil.ViewComparator
        public final boolean isEmpty(View view) {
            return false;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class LeftIconApplicator implements ResultApplicator {
        public static final int[] MARGIN_ADJUSTED_VIEWS = {16909857, R.id.bubble_button, R.id.title, R.id.pin_ok_button, R.id.pin_error_message};

        private LeftIconApplicator() {
        }

        public /* synthetic */ LeftIconApplicator(int i) {
            this();
        }

        /* JADX WARN: Removed duplicated region for block: B:12:0x003e  */
        /* JADX WARN: Removed duplicated region for block: B:22:0x0053  */
        /* JADX WARN: Removed duplicated region for block: B:25:0x005b  */
        /* JADX WARN: Removed duplicated region for block: B:60:0x00c5 A[ORIG_RETURN, RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:61:0x0055  */
        @Override // com.android.systemui.statusbar.NotificationGroupingUtil.ResultApplicator
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void apply(View view, View view2, boolean z, boolean z2) {
            boolean z3;
            Integer num;
            ImageView imageView = (ImageView) view2.findViewById(R.id.mediaProjection);
            if (imageView == null) {
                return;
            }
            ImageView imageView2 = (ImageView) view2.findViewById(R.id.snooze_button);
            if (imageView2 != null) {
                Integer num2 = 1;
                if (num2.equals(imageView2.getTag(16909838))) {
                    z3 = true;
                    num = 1;
                    if (num.equals(imageView.getTag(16909844))) {
                        Drawable drawable = null;
                        Drawable drawable2 = imageView2 == null ? null : imageView2.getDrawable();
                        if (z && !z3) {
                            drawable = drawable2;
                        }
                        imageView.setImageDrawable(drawable);
                    }
                    imageView.setVisibility(!z ? 0 : 8);
                    if (imageView2 == null) {
                        boolean z4 = (z3 || !z) && imageView2.getDrawable() != null;
                        imageView2.setVisibility(z4 ? 0 : 8);
                        int[] iArr = MARGIN_ADJUSTED_VIEWS;
                        for (int i = 0; i < 5; i++) {
                            ImageFloatingTextView findViewById = view2.findViewById(iArr[i]);
                            if (findViewById != null) {
                                if (findViewById instanceof ImageFloatingTextView) {
                                    findViewById.setHasImage(z4);
                                } else {
                                    Integer num3 = (Integer) findViewById.getTag(z4 ? 16909841 : 16909840);
                                    if (num3 != null) {
                                        int complexToDimensionPixelOffset = TypedValue.complexToDimensionPixelOffset(num3.intValue(), findViewById.getResources().getDisplayMetrics());
                                        if (findViewById instanceof NotificationHeaderView) {
                                            ((NotificationHeaderView) findViewById).setTopLineExtraMarginEnd(complexToDimensionPixelOffset);
                                        } else {
                                            ViewGroup.LayoutParams layoutParams = findViewById.getLayoutParams();
                                            if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                                                ((ViewGroup.MarginLayoutParams) layoutParams).setMarginEnd(complexToDimensionPixelOffset);
                                                findViewById.setLayoutParams(layoutParams);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        return;
                    }
                    return;
                }
            }
            z3 = false;
            num = 1;
            if (num.equals(imageView.getTag(16909844))) {
            }
            imageView.setVisibility(!z ? 0 : 8);
            if (imageView2 == null) {
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
            boolean z2 = this.mApply && !z;
            if (expandableNotificationRow.mIsSummaryWithChildren) {
                applyToView(expandableNotificationRow.getNotificationViewWrapper().getNotificationHeader(), z2, z);
                return;
            }
            applyToView(expandableNotificationRow.mPrivateLayout.mContractedChild, z2, z);
            applyToView(expandableNotificationRow.mPrivateLayout.mHeadsUpChild, z2, z);
            applyToView(expandableNotificationRow.mPrivateLayout.mExpandedChild, z2, z);
        }

        public final void applyToView(View view, boolean z, boolean z2) {
            View findViewById;
            if (view == null || (findViewById = view.findViewById(this.mId)) == null || this.mComparator.isEmpty(findViewById)) {
                return;
            }
            this.mApplicator.apply(view, findViewById, z, z2);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface ResultApplicator {
        void apply(View view, View view2, boolean z, boolean z2);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class TextViewComparator implements ViewComparator {
        private TextViewComparator() {
        }

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
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface ViewComparator {
        boolean compare(View view, View view2, Object obj, Object obj2);

        boolean isEmpty(View view);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class VisibilityApplicator implements ResultApplicator {
        private VisibilityApplicator() {
        }

        public /* synthetic */ VisibilityApplicator(int i) {
            this();
        }

        @Override // com.android.systemui.statusbar.NotificationGroupingUtil.ResultApplicator
        public void apply(View view, View view2, boolean z, boolean z2) {
            view2.setVisibility(z ? 8 : 0);
        }
    }

    static {
        int i = 0;
        TEXT_VIEW_COMPARATOR = new TextViewComparator(i);
        APP_NAME_COMPARATOR = new AppNameComparator(i);
        BADGE_COMPARATOR = new BadgeComparator(i);
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
                if (notification2.getSmallIcon().sameAs(notification3.getSmallIcon())) {
                    return notification2.color == notification3.color;
                }
                return false;
            }
        };
        new IconComparator() { // from class: com.android.systemui.statusbar.NotificationGroupingUtil.3
            @Override // com.android.systemui.statusbar.NotificationGroupingUtil.IconComparator, com.android.systemui.statusbar.NotificationGroupingUtil.ViewComparator
            public final boolean compare(View view, View view2, Object obj, Object obj2) {
                Notification notification2 = (Notification) obj;
                Notification notification3 = (Notification) obj2;
                if (notification2.getSmallIcon().sameAs(notification3.getSmallIcon())) {
                    return notification2.color == notification3.color;
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

    /* JADX WARN: Removed duplicated region for block: B:14:0x0063  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public NotificationGroupingUtil(ExpandableNotificationRow expandableNotificationRow) {
        boolean z;
        ArrayList arrayList = new ArrayList();
        this.mProcessors = arrayList;
        this.mDividers = new HashSet();
        this.mRow = expandableNotificationRow;
        BadgeComparator badgeComparator = BADGE_COMPARATOR;
        VisibilityApplicator visibilityApplicator = VISIBILITY_APPLICATOR;
        arrayList.add(new Processor(expandableNotificationRow, R.id.search_go_btn, null, badgeComparator, visibilityApplicator));
        arrayList.add(new Processor(expandableNotificationRow, R.id.audio, null, APP_NAME_COMPARATOR, APP_NAME_APPLICATOR));
        if (!expandableNotificationRow.mIsGroupHeaderContainAtMark) {
            NotificationContentView[] notificationContentViewArr = expandableNotificationRow.mLayouts;
            z = false;
            for (NotificationContentView notificationContentView : (NotificationContentView[]) Arrays.copyOf(notificationContentViewArr, notificationContentViewArr.length)) {
                if (!notificationContentView.mIsContractedHeaderContainAtMark && !notificationContentView.mIsExpandedHeaderContainAtMark) {
                }
            }
            if (!z) {
                this.mProcessors.add(new Processor(this.mRow, R.id.image, null, TEXT_VIEW_COMPARATOR, visibilityApplicator));
            }
            this.mDividers.add(Integer.valueOf(R.id.imageView));
            this.mDividers.add(Integer.valueOf(R.id.immersive_cling_back_bg_light));
            this.mDividers.add(16909895);
        }
        z = true;
        if (!z) {
        }
        this.mDividers.add(Integer.valueOf(R.id.imageView));
        this.mDividers.add(Integer.valueOf(R.id.immersive_cling_back_bg_light));
        this.mDividers.add(16909895);
    }

    public final void sanitizeTopLine(ViewGroup viewGroup, ExpandableNotificationRow expandableNotificationRow) {
        boolean z;
        if (viewGroup == null) {
            return;
        }
        int childCount = viewGroup.getChildCount();
        View findViewById = viewGroup.findViewById(16909891);
        int i = 0;
        int i2 = 0;
        while (true) {
            if (i2 >= childCount) {
                z = false;
                break;
            }
            View childAt = viewGroup.getChildAt(i2);
            if ((childAt instanceof TextView) && childAt.getVisibility() != 8 && !this.mDividers.contains(Integer.valueOf(childAt.getId())) && childAt != findViewById) {
                z = true;
                break;
            }
            i2++;
        }
        if (z && !expandableNotificationRow.mEntry.mSbn.getNotification().showsTime()) {
            i = 8;
        }
        findViewById.setVisibility(i);
    }

    public final void sanitizeTopLineViews(ExpandableNotificationRow expandableNotificationRow) {
        if (expandableNotificationRow.mIsSummaryWithChildren) {
            sanitizeTopLine(expandableNotificationRow.getNotificationViewWrapper().getNotificationHeader(), expandableNotificationRow);
            return;
        }
        NotificationContentView notificationContentView = expandableNotificationRow.mPrivateLayout;
        View view = notificationContentView.mContractedChild;
        if (view != null) {
            sanitizeTopLine((ViewGroup) view.findViewById(R.id.productivity), expandableNotificationRow);
        }
        View view2 = notificationContentView.mHeadsUpChild;
        if (view2 != null) {
            sanitizeTopLine((ViewGroup) view2.findViewById(R.id.productivity), expandableNotificationRow);
        }
        View view3 = notificationContentView.mExpandedChild;
        if (view3 != null) {
            sanitizeTopLine((ViewGroup) view3.findViewById(R.id.productivity), expandableNotificationRow);
        }
    }
}
