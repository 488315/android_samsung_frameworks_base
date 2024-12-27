package com.android.systemui.statusbar.notification.row;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.service.notification.StatusBarNotification;
import android.util.ArrayMap;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.android.app.animation.Interpolators;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin;
import com.android.systemui.statusbar.AlphaOptimizedImageView;
import com.android.systemui.statusbar.notification.people.PeopleNotificationIdentifier;
import com.android.systemui.statusbar.notification.people.PeopleNotificationIdentifierImpl;
import com.android.systemui.statusbar.notification.row.NotificationGuts;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.util.SettingsHelper;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class NotificationMenuRow implements NotificationMenuRowPlugin, View.OnClickListener {
    public boolean mAnimating;
    public CheckForDrag mCheckForDrag;
    public final Context mContext;
    public boolean mDismissing;
    public ValueAnimator mFadeAnimator;
    public NotificationMenuItem mFeedbackItem;
    public boolean mHasPopped;
    public boolean mIconsPlaced;
    public NotificationMenuItem mInfoItem;
    public boolean mIsUserTouching;
    public FrameLayout mMenuContainer;
    public boolean mMenuFadedIn;
    public NotificationMenuRowPlugin.OnMenuEventListener mMenuListener;
    public boolean mMenuSnapped;
    public boolean mMenuSnappedOnLeft;
    public boolean mOnLeft;
    public ExpandableNotificationRow mParent;
    public final PeopleNotificationIdentifier mPeopleNotificationIdentifier;
    public final boolean mShouldShowMenu;
    public boolean mSnapping;
    public boolean mSnappingToDismiss;
    public NotificationMenuItem mSnoozeItem;
    public float mTranslation;
    public final Map mMenuItemsByView = new ArrayMap();
    public final int[] mIconLocation = new int[2];
    public final int[] mParentLocation = new int[2];
    public int mHorizSpaceForIcon = -1;
    public int mVertSpaceForIcons = -1;
    public final int mIconPadding = -1;
    public float mAlpha = 0.0f;
    public final Handler mHandler = new Handler(Looper.getMainLooper());
    public final ArrayList mLeftMenuItems = new ArrayList();
    public final ArrayList mRightMenuItems = new ArrayList();

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class CheckForDrag implements Runnable {
        public /* synthetic */ CheckForDrag(NotificationMenuRow notificationMenuRow, int i) {
            this();
        }

        @Override // java.lang.Runnable
        public final void run() {
            float abs = Math.abs(NotificationMenuRow.this.mTranslation);
            float spaceForMenu = NotificationMenuRow.this.getSpaceForMenu();
            float width = NotificationMenuRow.this.mParent.getWidth() * 0.4f;
            if ((!NotificationMenuRow.this.isMenuVisible() || NotificationMenuRow.this.isMenuLocationChange()) && abs >= spaceForMenu * 0.4d && abs < width) {
                NotificationMenuRow.this.fadeInMenu(width);
            }
        }

        private CheckForDrag() {
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class NotificationMenuItem implements NotificationMenuRowPlugin.MenuItem, GutContentInitializer {
        public final String mContentDescription;
        public final GutContentInitializer mGutContentInitializer;
        public final NotificationGuts.GutsContent mGutsContent;
        public final AlphaOptimizedImageView mMenuView;

        public NotificationMenuItem(Context context, String str, NotificationGuts.GutsContent gutsContent, int i) {
            Resources resources = context.getResources();
            int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.notification_menu_icon_padding);
            int color = resources.getColor(R.color.notification_gear_color);
            if (i >= 0) {
                AlphaOptimizedImageView alphaOptimizedImageView = new AlphaOptimizedImageView(context);
                alphaOptimizedImageView.setPadding(dimensionPixelSize, dimensionPixelSize, dimensionPixelSize, dimensionPixelSize);
                alphaOptimizedImageView.setImageDrawable(context.getResources().getDrawable(i));
                alphaOptimizedImageView.setColorFilter(color);
                alphaOptimizedImageView.setAlpha(1.0f);
                this.mMenuView = alphaOptimizedImageView;
            }
            this.mContentDescription = str;
            this.mGutsContent = gutsContent;
            if (gutsContent instanceof GutContentInitializer) {
                this.mGutContentInitializer = (GutContentInitializer) gutsContent;
            }
        }

        @Override // com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin.MenuItem
        public final String getContentDescription() {
            return this.mContentDescription;
        }

        @Override // com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin.MenuItem
        public final View getGutsView() {
            return this.mGutsContent.getContentView();
        }

        @Override // com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin.MenuItem
        public final View getMenuView() {
            return this.mMenuView;
        }

        @Override // com.android.systemui.statusbar.notification.row.GutContentInitializer
        public final boolean initializeGutContentView(final ExpandableNotificationRow expandableNotificationRow) {
            final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
            Optional.ofNullable(this.mGutContentInitializer).ifPresent(new Consumer() { // from class: com.android.systemui.statusbar.notification.row.NotificationMenuRow$NotificationMenuItem$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    try {
                        atomicBoolean.set(((GutContentInitializer) obj).initializeGutContentView(expandableNotificationRow));
                    } catch (Exception e) {
                        Log.e("NotificationMenuRow", "error binding guts", e);
                    }
                }
            });
            return atomicBoolean.get();
        }
    }

    public NotificationMenuRow(Context context, PeopleNotificationIdentifier peopleNotificationIdentifier) {
        this.mContext = context;
        this.mShouldShowMenu = context.getResources().getBoolean(R.bool.config_showNotificationGear);
        this.mPeopleNotificationIdentifier = peopleNotificationIdentifier;
    }

    public void beginDrag() {
        this.mSnapping = false;
        ValueAnimator valueAnimator = this.mFadeAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        this.mHandler.removeCallbacks(this.mCheckForDrag);
        this.mCheckForDrag = null;
        this.mIsUserTouching = true;
    }

    @Override // com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin
    public final boolean canBeDismissed() {
        return getParent().canViewBeDismissed$1();
    }

    public void cancelDrag() {
        ValueAnimator valueAnimator = this.mFadeAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        this.mHandler.removeCallbacks(this.mCheckForDrag);
    }

    @Override // com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin
    public final void createMenu(ViewGroup viewGroup, StatusBarNotification statusBarNotification) {
        this.mParent = (ExpandableNotificationRow) viewGroup;
        createMenuViews(true);
    }

    public final void createMenuViews(boolean z) {
        Resources resources = this.mContext.getResources();
        this.mHorizSpaceForIcon = resources.getDimensionPixelSize(R.dimen.notification_menu_icon_size);
        this.mVertSpaceForIcons = resources.getDimensionPixelSize(R.dimen.notification_min_height);
        this.mLeftMenuItems.clear();
        this.mRightMenuItems.clear();
        this.mParent.getClass();
        boolean isEnableSnooze = ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isEnableSnooze();
        if (isEnableSnooze) {
            this.mSnoozeItem = SecGutInflater.createNotificationMenuItem(this.mContext, R.string.notification_menu_snooze_description, R.layout.sec_notification_snooze);
        }
        Context context = this.mContext;
        this.mFeedbackItem = new NotificationMenuItem(context, null, (FeedbackInfo) LayoutInflater.from(context).inflate(R.layout.feedback_info, (ViewGroup) null, false), -1);
        ((PeopleNotificationIdentifierImpl) this.mPeopleNotificationIdentifier).getPeopleNotificationType(this.mParent.mEntry);
        this.mInfoItem = SecGutInflater.createNotificationMenuItem(this.mContext, R.string.notification_menu_gear_description, R.layout.sec_notification_app_info);
        if (isEnableSnooze) {
            this.mRightMenuItems.add(this.mSnoozeItem);
        }
        this.mRightMenuItems.add(this.mInfoItem);
        this.mRightMenuItems.add(this.mFeedbackItem);
        this.mLeftMenuItems.addAll(this.mRightMenuItems);
        populateMenuViews();
        if (z) {
            resetState(false);
            return;
        }
        this.mIconsPlaced = false;
        setMenuLocation();
        if (this.mIsUserTouching) {
            return;
        }
        onSnapOpen();
    }

    public final void fadeInMenu(final float f) {
        if (this.mDismissing || this.mAnimating) {
            return;
        }
        if (isMenuLocationChange()) {
            setMenuAlpha(0.0f);
        }
        final float f2 = this.mTranslation;
        final boolean z = f2 > 0.0f;
        setMenuLocation();
        ValueAnimator ofFloat = ValueAnimator.ofFloat(this.mAlpha, 1.0f);
        this.mFadeAnimator = ofFloat;
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.notification.row.NotificationMenuRow.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                float abs = Math.abs(f2);
                boolean z2 = z;
                if ((!z2 || f2 > f) && (z2 || abs > f)) {
                    return;
                }
                NotificationMenuRow notificationMenuRow = NotificationMenuRow.this;
                if (notificationMenuRow.mMenuFadedIn) {
                    return;
                }
                notificationMenuRow.setMenuAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
            }
        });
        this.mFadeAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.notification.row.NotificationMenuRow.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
                NotificationMenuRow.this.setMenuAlpha(0.0f);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                NotificationMenuRow notificationMenuRow = NotificationMenuRow.this;
                notificationMenuRow.mAnimating = false;
                notificationMenuRow.mMenuFadedIn = notificationMenuRow.mAlpha == 1.0f;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
                NotificationMenuRow.this.mAnimating = true;
            }
        });
        this.mFadeAnimator.setInterpolator(Interpolators.ALPHA_IN);
        this.mFadeAnimator.setDuration(200L);
        this.mFadeAnimator.start();
    }

    public float getDismissThreshold() {
        return getParent().getWidth() * 0.6f;
    }

    @Override // com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin
    public final NotificationMenuRowPlugin.MenuItem getFeedbackMenuItem(Context context) {
        return this.mFeedbackItem;
    }

    @Override // com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin
    public final boolean getHasPopped() {
        return this.mHasPopped;
    }

    @Override // com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin
    public final NotificationMenuRowPlugin.MenuItem getLongpressMenuItem(Context context) {
        return this.mInfoItem;
    }

    public float getMaximumSwipeDistance() {
        return this.mHorizSpaceForIcon * 0.2f;
    }

    @Override // com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin
    public final ArrayList getMenuItems(Context context) {
        return this.mOnLeft ? this.mLeftMenuItems : this.mRightMenuItems;
    }

    @Override // com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin
    public final int getMenuSnapTarget() {
        boolean isMenuOnLeft = isMenuOnLeft();
        int spaceForMenu = getSpaceForMenu();
        return isMenuOnLeft ? spaceForMenu : -spaceForMenu;
    }

    @Override // com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin
    public final View getMenuView() {
        return this.mMenuContainer;
    }

    public float getMinimumSwipeDistance() {
        return this.mHorizSpaceForIcon * (getParent().canViewBeDismissed$1() ? 0.25f : 0.15f);
    }

    public ExpandableNotificationRow getParent() {
        return this.mParent;
    }

    @Override // com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin
    public final Point getRevealAnimationOrigin() {
        AlphaOptimizedImageView alphaOptimizedImageView = this.mInfoItem.mMenuView;
        int width = (alphaOptimizedImageView.getWidth() / 2) + alphaOptimizedImageView.getPaddingLeft() + alphaOptimizedImageView.getLeft();
        int height = (alphaOptimizedImageView.getHeight() / 2) + alphaOptimizedImageView.getPaddingTop() + alphaOptimizedImageView.getTop();
        return isMenuOnLeft() ? new Point(width, height) : new Point(this.mParent.getRight() - width, height);
    }

    public float getSnapBackThreshold() {
        return getSpaceForMenu() - getMaximumSwipeDistance();
    }

    @Override // com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin
    public final NotificationMenuRowPlugin.MenuItem getSnoozeMenuItem(Context context) {
        return this.mSnoozeItem;
    }

    public int getSpaceForMenu() {
        return this.mMenuContainer.getChildCount() * this.mHorizSpaceForIcon;
    }

    public float getTranslation() {
        return this.mTranslation;
    }

    public boolean isDismissing() {
        return this.mDismissing;
    }

    public final boolean isMenuLocationChange() {
        float f = this.mTranslation;
        int i = this.mIconPadding;
        return (isMenuOnLeft() && ((f > ((float) (-i)) ? 1 : (f == ((float) (-i)) ? 0 : -1)) < 0)) || (!isMenuOnLeft() && ((f > ((float) i) ? 1 : (f == ((float) i) ? 0 : -1)) > 0));
    }

    public boolean isMenuOnLeft() {
        return this.mOnLeft;
    }

    public boolean isMenuSnapped() {
        return this.mMenuSnapped;
    }

    public boolean isMenuSnappedOnLeft() {
        return this.mMenuSnappedOnLeft;
    }

    @Override // com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin
    public final boolean isMenuVisible() {
        return this.mAlpha > 0.0f;
    }

    @Override // com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin
    public final boolean isSnappedAndOnSameSide() {
        return isMenuSnapped() && isMenuVisible() && isMenuSnappedOnLeft() == isMenuOnLeft();
    }

    public boolean isSnapping() {
        return this.mSnapping;
    }

    public boolean isSnappingToDismiss() {
        return this.mSnappingToDismiss;
    }

    @Override // com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin
    public final boolean isSwipedEnoughToShowMenu() {
        float minimumSwipeDistance = getMinimumSwipeDistance();
        float translation = getTranslation();
        return isMenuVisible() && (!isMenuOnLeft() ? translation >= (-minimumSwipeDistance) : translation <= minimumSwipeDistance);
    }

    @Override // com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin
    public final boolean isTowardsMenu(float f) {
        return isMenuVisible() && ((isMenuOnLeft() && f <= 0.0f) || (!isMenuOnLeft() && f >= 0.0f));
    }

    public boolean isUserTouching() {
        return this.mIsUserTouching;
    }

    @Override // com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin
    public final boolean isWithinSnapMenuThreshold() {
        float translation = getTranslation();
        float snapBackThreshold = getSnapBackThreshold();
        float dismissThreshold = getDismissThreshold();
        if (isMenuOnLeft()) {
            if (translation <= snapBackThreshold || translation >= dismissThreshold) {
                return false;
            }
        } else if (translation >= (-snapBackThreshold) || translation <= (-dismissThreshold)) {
            return false;
        }
        return true;
    }

    @Override // com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin
    public final NotificationMenuRowPlugin.MenuItem menuItemToExposeOnSnap() {
        return null;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        if (this.mMenuListener == null) {
            return;
        }
        view.getLocationOnScreen(this.mIconLocation);
        this.mParent.getLocationOnScreen(this.mParentLocation);
        int i = this.mHorizSpaceForIcon / 2;
        int height = view.getHeight() / 2;
        int[] iArr = this.mIconLocation;
        int i2 = iArr[0];
        int[] iArr2 = this.mParentLocation;
        int i3 = (i2 - iArr2[0]) + i;
        int i4 = (iArr[1] - iArr2[1]) + height;
        if (((ArrayMap) this.mMenuItemsByView).containsKey(view)) {
            this.mMenuListener.onMenuClicked(this.mParent, i3, i4, (NotificationMenuRowPlugin.MenuItem) ((ArrayMap) this.mMenuItemsByView).get(view));
        }
    }

    @Override // com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin
    public final void onConfigurationChanged() {
        this.mParent.mLayoutListener = this;
    }

    @Override // com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin
    public final void onDismiss() {
        cancelDrag();
        this.mMenuSnapped = false;
        this.mDismissing = true;
    }

    @Override // com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin
    public final void onNotificationUpdated(StatusBarNotification statusBarNotification) {
        if (this.mMenuContainer == null) {
            return;
        }
        createMenuViews(!isMenuVisible());
    }

    @Override // com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin
    public final void onParentHeightUpdate() {
        FrameLayout frameLayout;
        if (this.mParent != null) {
            if ((this.mLeftMenuItems.isEmpty() && this.mRightMenuItems.isEmpty()) || (frameLayout = this.mMenuContainer) == null) {
                return;
            }
            frameLayout.setTranslationY(this.mParent.mActualHeight < this.mVertSpaceForIcons ? (r1 / 2) - (this.mHorizSpaceForIcon / 2) : (r2 - this.mHorizSpaceForIcon) / 2);
        }
    }

    @Override // com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin
    public final void onParentTranslationUpdate(float f) {
        this.mTranslation = f;
        if (this.mAnimating || !this.mMenuFadedIn) {
            return;
        }
        float width = this.mParent.getWidth() * 0.3f;
        float abs = Math.abs(f);
        setMenuAlpha(abs != 0.0f ? abs <= width ? 1.0f : 1.0f - ((abs - width) / (this.mParent.getWidth() - width)) : 0.0f);
    }

    @Override // com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin
    public final void onSnapClosed() {
        cancelDrag();
        this.mMenuSnapped = false;
        this.mSnapping = true;
    }

    @Override // com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin
    public final void onSnapOpen() {
        ExpandableNotificationRow expandableNotificationRow;
        this.mMenuSnapped = true;
        this.mMenuSnappedOnLeft = isMenuOnLeft();
        if (this.mAlpha == 0.0f && (expandableNotificationRow = this.mParent) != null) {
            fadeInMenu(expandableNotificationRow.getWidth());
        }
        NotificationMenuRowPlugin.OnMenuEventListener onMenuEventListener = this.mMenuListener;
        if (onMenuEventListener != null) {
            onMenuEventListener.onMenuShown(getParent());
        }
    }

    @Override // com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin
    public final void onTouchEnd() {
        this.mIsUserTouching = false;
    }

    @Override // com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin
    public final void onTouchMove(float f) {
        CheckForDrag checkForDrag;
        byte b = 0;
        this.mSnapping = false;
        if (!isTowardsMenu(f) && isMenuLocationChange()) {
            this.mMenuSnapped = false;
            if (this.mHandler.hasCallbacks(this.mCheckForDrag)) {
                setMenuAlpha(0.0f);
                setMenuLocation();
            } else {
                this.mCheckForDrag = null;
            }
        }
        if (this.mShouldShowMenu && !NotificationStackScrollLayout.isPinnedHeadsUp(getParent()) && !this.mParent.areGutsExposed() && !this.mParent.showingPulsing() && ((checkForDrag = this.mCheckForDrag) == null || !this.mHandler.hasCallbacks(checkForDrag))) {
            CheckForDrag checkForDrag2 = new CheckForDrag(this, b == true ? 1 : 0);
            this.mCheckForDrag = checkForDrag2;
            this.mHandler.postDelayed(checkForDrag2, 60L);
        }
        if (canBeDismissed()) {
            float dismissThreshold = getDismissThreshold();
            boolean z = f < (-dismissThreshold) || f > dismissThreshold;
            if (this.mSnappingToDismiss != z) {
                this.mMenuContainer.performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(41));
                this.mHasPopped = true;
            }
            this.mSnappingToDismiss = z;
        }
    }

    @Override // com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin
    public final void onTouchStart() {
        beginDrag();
        this.mHasPopped = false;
        this.mSnappingToDismiss = false;
    }

    public final void populateMenuViews() {
        FrameLayout frameLayout = this.mMenuContainer;
        if (frameLayout != null) {
            frameLayout.removeAllViews();
            ((ArrayMap) this.mMenuItemsByView).clear();
        } else {
            this.mMenuContainer = new FrameLayout(this.mContext);
        }
        if (Settings.Global.getInt(this.mContext.getContentResolver(), "show_new_notif_dismiss", 1) == 1) {
            return;
        }
        ArrayList arrayList = this.mOnLeft ? this.mLeftMenuItems : this.mRightMenuItems;
        for (int i = 0; i < arrayList.size(); i++) {
            NotificationMenuRowPlugin.MenuItem menuItem = (NotificationMenuRowPlugin.MenuItem) arrayList.get(i);
            FrameLayout frameLayout2 = this.mMenuContainer;
            View menuView = menuItem.getMenuView();
            if (menuView != null) {
                menuView.setAlpha(this.mAlpha);
                frameLayout2.addView(menuView);
                menuView.setOnClickListener(this);
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) menuView.getLayoutParams();
                int i2 = this.mHorizSpaceForIcon;
                layoutParams.width = i2;
                layoutParams.height = i2;
                menuView.setLayoutParams(layoutParams);
            }
            ((ArrayMap) this.mMenuItemsByView).put(menuView, menuItem);
        }
    }

    @Override // com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin
    public final void resetMenu() {
        resetState(true);
    }

    public final void resetState(boolean z) {
        setMenuAlpha(0.0f);
        this.mIconsPlaced = false;
        this.mMenuFadedIn = false;
        this.mAnimating = false;
        this.mSnapping = false;
        this.mDismissing = false;
        this.mMenuSnapped = false;
        setMenuLocation();
        NotificationMenuRowPlugin.OnMenuEventListener onMenuEventListener = this.mMenuListener;
        if (onMenuEventListener == null || !z) {
            return;
        }
        onMenuEventListener.onMenuReset(this.mParent);
    }

    @Override // com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin
    public final void setAppName(String str) {
        if (str == null) {
            return;
        }
        setAppName(str, this.mLeftMenuItems);
        setAppName(str, this.mRightMenuItems);
    }

    public void setMenuAlpha(float f) {
        this.mAlpha = f;
        FrameLayout frameLayout = this.mMenuContainer;
        if (frameLayout == null) {
            return;
        }
        if (f == 0.0f) {
            this.mMenuFadedIn = false;
            frameLayout.setVisibility(4);
        } else {
            frameLayout.setVisibility(0);
        }
        int childCount = this.mMenuContainer.getChildCount();
        for (int i = 0; i < childCount; i++) {
            this.mMenuContainer.getChildAt(i).setAlpha(this.mAlpha);
        }
    }

    @Override // com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin
    public final void setMenuClickListener(NotificationMenuRowPlugin.OnMenuEventListener onMenuEventListener) {
        this.mMenuListener = onMenuEventListener;
    }

    public final void setMenuLocation() {
        FrameLayout frameLayout;
        int i = 0;
        boolean z = this.mTranslation > 0.0f;
        if ((this.mIconsPlaced && z == isMenuOnLeft()) || isSnapping() || (frameLayout = this.mMenuContainer) == null || !frameLayout.isAttachedToWindow()) {
            return;
        }
        boolean z2 = this.mOnLeft;
        this.mOnLeft = z;
        if (z2 != z) {
            populateMenuViews();
        }
        int childCount = this.mMenuContainer.getChildCount();
        while (i < childCount) {
            View childAt = this.mMenuContainer.getChildAt(i);
            float f = this.mHorizSpaceForIcon * i;
            i++;
            float width = this.mParent.getWidth() - (this.mHorizSpaceForIcon * i);
            if (!z) {
                f = width;
            }
            childAt.setX(f);
        }
        this.mIconsPlaced = true;
    }

    @Override // com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin
    public final boolean shouldShowGutsOnSnapOpen() {
        return false;
    }

    @Override // com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin
    public final boolean shouldShowMenu() {
        return this.mShouldShowMenu;
    }

    @Override // com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin
    public final boolean shouldSnapBack() {
        float translation = getTranslation();
        float snapBackThreshold = getSnapBackThreshold();
        if (isMenuOnLeft()) {
            if (translation >= snapBackThreshold) {
                return false;
            }
        } else if (translation <= (-snapBackThreshold)) {
            return false;
        }
        return true;
    }

    public final void setAppName(String str, ArrayList arrayList) {
        Resources resources = this.mContext.getResources();
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            NotificationMenuRowPlugin.MenuItem menuItem = (NotificationMenuRowPlugin.MenuItem) arrayList.get(i);
            String format = String.format(resources.getString(R.string.notification_menu_accessibility), str, menuItem.getContentDescription());
            View menuView = menuItem.getMenuView();
            if (menuView != null) {
                menuView.setContentDescription(format);
            }
        }
    }

    @Override // com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin
    public final void setMenuItems(ArrayList arrayList) {
    }
}
