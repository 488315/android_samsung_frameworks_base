package com.android.internal.view;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.PopupWindow;
import com.android.internal.R;
import com.android.internal.view.menu.MenuBuilder;
import com.android.internal.widget.floatingtoolbar.FloatingToolbar;
import java.util.Arrays;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class FloatingActionMode extends ActionMode {
    private static final int MAX_HIDE_DURATION = 3000;
    private static final int MOVING_HIDE_DELAY = 50;
    private final int mBottomAllowance;
    private final ActionMode.Callback2 mCallback;
    private final Rect mContentRect;
    private final Rect mContentRectOnScreen;
    private final Context mContext;
    private final Point mDisplaySize;
    private FloatingToolbar mFloatingToolbar;
    private FloatingToolbarVisibilityHelper mFloatingToolbarVisibilityHelper;
    private final MenuBuilder mMenu;
    private final View mOriginatingView;
    private final Rect mPreviousContentRectOnScreen;
    private final int[] mPreviousViewPositionOnScreen;
    private final Rect mPreviousViewRectOnScreen;
    private final int[] mRootViewPositionOnScreen;
    private final Rect mScreenRect;
    private final int[] mViewPositionOnScreen;
    private final Rect mViewRectOnScreen;
    private final Runnable mMovingOff = new Runnable() { // from class: com.android.internal.view.FloatingActionMode.1
        @Override // java.lang.Runnable
        public void run() {
            if (FloatingActionMode.this.isViewStillActive()) {
                FloatingActionMode.this.mFloatingToolbarVisibilityHelper.setMoving(false);
                FloatingActionMode.this.mFloatingToolbarVisibilityHelper.updateToolbarVisibility();
            }
        }
    };
    private final Runnable mHideOff = new Runnable() { // from class: com.android.internal.view.FloatingActionMode.2
        @Override // java.lang.Runnable
        public void run() {
            if (FloatingActionMode.this.isViewStillActive()) {
                FloatingActionMode.this.mFloatingToolbarVisibilityHelper.setHideRequested(false);
                FloatingActionMode.this.mFloatingToolbarVisibilityHelper.updateToolbarVisibility();
            }
        }
    };

    @Override // android.view.ActionMode
    public View getCustomView() {
        return null;
    }

    @Override // android.view.ActionMode
    public CharSequence getSubtitle() {
        return null;
    }

    @Override // android.view.ActionMode
    public CharSequence getTitle() {
        return null;
    }

    @Override // android.view.ActionMode
    public void setCustomView(View view) {
    }

    @Override // android.view.ActionMode
    public void setSubtitle(int i) {
    }

    @Override // android.view.ActionMode
    public void setSubtitle(CharSequence charSequence) {
    }

    @Override // android.view.ActionMode
    public void setTitle(int i) {
    }

    @Override // android.view.ActionMode
    public void setTitle(CharSequence charSequence) {
    }

    public FloatingActionMode(Context context, ActionMode.Callback2 callback2, View view, FloatingToolbar floatingToolbar) {
        this.mContext = (Context) Objects.requireNonNull(context);
        this.mCallback = (ActionMode.Callback2) Objects.requireNonNull(callback2);
        MenuBuilder defaultShowAsAction = new MenuBuilder(context).setDefaultShowAsAction(1);
        this.mMenu = defaultShowAsAction;
        setType(1);
        defaultShowAsAction.setCallback(new MenuBuilder.Callback() { // from class: com.android.internal.view.FloatingActionMode.3
            @Override // com.android.internal.view.menu.MenuBuilder.Callback
            public void onMenuModeChange(MenuBuilder menuBuilder) {
            }

            @Override // com.android.internal.view.menu.MenuBuilder.Callback
            public boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
                return FloatingActionMode.this.mCallback.onActionItemClicked(FloatingActionMode.this, menuItem);
            }
        });
        this.mContentRect = new Rect();
        this.mContentRectOnScreen = new Rect();
        this.mPreviousContentRectOnScreen = new Rect();
        int[] iArr = new int[2];
        this.mViewPositionOnScreen = iArr;
        this.mPreviousViewPositionOnScreen = new int[2];
        this.mRootViewPositionOnScreen = new int[2];
        this.mViewRectOnScreen = new Rect();
        this.mPreviousViewRectOnScreen = new Rect();
        this.mScreenRect = new Rect();
        View view2 = (View) Objects.requireNonNull(view);
        this.mOriginatingView = view2;
        view2.getLocationOnScreen(iArr);
        this.mBottomAllowance = Math.max(context.getResources().getDimensionPixelSize(R.dimen.content_rect_bottom_clip_allowance), context.getResources().getDimensionPixelSize(R.dimen.tw_edittext_field_handler_min_width));
        this.mDisplaySize = new Point();
        setFloatingToolbar((FloatingToolbar) Objects.requireNonNull(floatingToolbar));
    }

    public void setFloatingToolbar(FloatingToolbar floatingToolbar) {
        this.mFloatingToolbar = floatingToolbar.setMenu(this.mMenu).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() { // from class: com.android.internal.view.FloatingActionMode$$ExternalSyntheticLambda0
            @Override // android.view.MenuItem.OnMenuItemClickListener
            public final boolean onMenuItemClick(MenuItem menuItem) {
                boolean lambda$setFloatingToolbar$0;
                lambda$setFloatingToolbar$0 = FloatingActionMode.this.lambda$setFloatingToolbar$0(menuItem);
                return lambda$setFloatingToolbar$0;
            }
        });
        FloatingToolbarVisibilityHelper floatingToolbarVisibilityHelper = new FloatingToolbarVisibilityHelper(this.mFloatingToolbar);
        this.mFloatingToolbarVisibilityHelper = floatingToolbarVisibilityHelper;
        floatingToolbarVisibilityHelper.activate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$setFloatingToolbar$0(MenuItem menuItem) {
        return this.mMenu.performItemAction(menuItem, 0);
    }

    @Override // android.view.ActionMode
    public void invalidate() {
        int size = this.mMenu.size();
        this.mCallback.onPrepareActionMode(this, this.mMenu);
        if (this.mFloatingToolbar.isMovingStarted() && size == this.mMenu.size()) {
            return;
        }
        invalidateContentRect();
    }

    @Override // android.view.ActionMode
    public void invalidateContentRect() {
        this.mCallback.onGetContentRect(this, this.mOriginatingView, this.mContentRect);
        updateViewLocationInWindow(true);
    }

    public void updateViewLocationInWindow() {
        updateViewLocationInWindow(false);
    }

    private void updateViewLocationInWindow(boolean z) {
        this.mOriginatingView.getLocationOnScreen(this.mViewPositionOnScreen);
        this.mOriginatingView.getRootView().getLocationOnScreen(this.mRootViewPositionOnScreen);
        this.mOriginatingView.getGlobalVisibleRect(this.mViewRectOnScreen);
        Rect rect = this.mViewRectOnScreen;
        int[] iArr = this.mRootViewPositionOnScreen;
        rect.offset(iArr[0], iArr[1]);
        if (!z && Arrays.equals(this.mViewPositionOnScreen, this.mPreviousViewPositionOnScreen) && this.mViewRectOnScreen.equals(this.mPreviousViewRectOnScreen)) {
            return;
        }
        repositionToolbar();
        int[] iArr2 = this.mPreviousViewPositionOnScreen;
        int[] iArr3 = this.mViewPositionOnScreen;
        iArr2[0] = iArr3[0];
        iArr2[1] = iArr3[1];
        this.mPreviousViewRectOnScreen.set(this.mViewRectOnScreen);
    }

    private void repositionToolbar() {
        this.mContentRectOnScreen.set(this.mContentRect);
        Rect rect = this.mContentRectOnScreen;
        int[] iArr = this.mViewPositionOnScreen;
        rect.offset(iArr[0], iArr[1]);
        if (isContentRectWithinBounds()) {
            this.mFloatingToolbarVisibilityHelper.setOutOfBounds(false);
            Rect rect2 = this.mContentRectOnScreen;
            rect2.set(Math.max(rect2.left, this.mViewRectOnScreen.left), Math.max(this.mContentRectOnScreen.top, this.mViewRectOnScreen.top), Math.min(this.mContentRectOnScreen.right, this.mViewRectOnScreen.right), Math.min(this.mContentRectOnScreen.bottom, this.mViewRectOnScreen.bottom + this.mBottomAllowance));
            if (!this.mContentRectOnScreen.equals(this.mPreviousContentRectOnScreen)) {
                if (!this.mPreviousContentRectOnScreen.isEmpty() || this.mPreviousContentRectOnScreen.left != 0) {
                    this.mOriginatingView.removeCallbacks(this.mMovingOff);
                    this.mFloatingToolbarVisibilityHelper.setMoving(true);
                    this.mOriginatingView.postDelayed(this.mMovingOff, 50L);
                }
                this.mFloatingToolbar.setContentRect(this.mContentRectOnScreen);
                this.mFloatingToolbar.updateLayout();
            }
        } else {
            this.mFloatingToolbarVisibilityHelper.setOutOfBounds(true);
            this.mContentRectOnScreen.setEmpty();
        }
        this.mFloatingToolbarVisibilityHelper.updateToolbarVisibility();
        this.mPreviousContentRectOnScreen.set(this.mContentRectOnScreen);
    }

    private boolean isContentRectWithinBounds() {
        this.mContext.getDisplayNoVerify().getRealSize(this.mDisplaySize);
        this.mScreenRect.set(0, 0, this.mDisplaySize.x, this.mDisplaySize.y);
        Rect rect = this.mScreenRect;
        int[] iArr = this.mRootViewPositionOnScreen;
        rect.offset(iArr[0], iArr[1]);
        return intersectsClosed(this.mContentRectOnScreen, this.mScreenRect) && intersectsClosed(this.mContentRectOnScreen, this.mViewRectOnScreen);
    }

    private static boolean intersectsClosed(Rect rect, Rect rect2) {
        return rect.left <= rect2.right && rect2.left <= rect.right && rect.top <= rect2.bottom && rect2.top <= rect.bottom;
    }

    @Override // android.view.ActionMode
    public void hide(long j) {
        if (j == -1) {
            j = ViewConfiguration.getDefaultActionModeHideDuration();
        }
        long min = Math.min(3000L, j);
        this.mOriginatingView.removeCallbacks(this.mHideOff);
        if (min <= 0) {
            this.mHideOff.run();
            return;
        }
        this.mFloatingToolbarVisibilityHelper.setHideRequested(true);
        this.mFloatingToolbarVisibilityHelper.updateToolbarVisibility();
        this.mOriginatingView.postDelayed(this.mHideOff, min);
    }

    public void setOutsideTouchable(boolean z, PopupWindow.OnDismissListener onDismissListener) {
        this.mFloatingToolbar.setOutsideTouchable(z, onDismissListener);
    }

    @Override // android.view.ActionMode
    public void onWindowFocusChanged(boolean z) {
        this.mFloatingToolbarVisibilityHelper.setWindowFocused(z);
        this.mFloatingToolbarVisibilityHelper.updateToolbarVisibility();
    }

    @Override // android.view.ActionMode
    public void finish() {
        reset();
        this.mCallback.onDestroyActionMode(this);
    }

    @Override // android.view.ActionMode
    public Menu getMenu() {
        return this.mMenu;
    }

    @Override // android.view.ActionMode
    public MenuInflater getMenuInflater() {
        return new MenuInflater(this.mContext);
    }

    private void reset() {
        this.mFloatingToolbar.dismiss();
        this.mFloatingToolbarVisibilityHelper.deactivate();
        this.mOriginatingView.removeCallbacks(this.mMovingOff);
        this.mOriginatingView.removeCallbacks(this.mHideOff);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isViewStillActive() {
        return this.mOriginatingView.getWindowVisibility() == 0 && this.mOriginatingView.isShown();
    }

    private static final class FloatingToolbarVisibilityHelper {
        private static final long MIN_SHOW_DURATION_FOR_MOVE_HIDE = 500;
        private boolean mActive;
        private boolean mHideRequested;
        private long mLastShowTime;
        private boolean mMoving;
        private boolean mOutOfBounds;
        private final FloatingToolbar mToolbar;
        private boolean mWindowFocused = true;

        public FloatingToolbarVisibilityHelper(FloatingToolbar floatingToolbar) {
            this.mToolbar = (FloatingToolbar) Objects.requireNonNull(floatingToolbar);
        }

        public void activate() {
            this.mHideRequested = false;
            this.mMoving = false;
            this.mOutOfBounds = false;
            this.mWindowFocused = true;
            this.mActive = true;
        }

        public void deactivate() {
            this.mActive = false;
            this.mToolbar.dismiss();
        }

        public void setHideRequested(boolean z) {
            this.mHideRequested = z;
        }

        public void setMoving(boolean z) {
            boolean z2 = System.currentTimeMillis() - this.mLastShowTime > MIN_SHOW_DURATION_FOR_MOVE_HIDE;
            if (!z || z2) {
                this.mMoving = z;
            }
        }

        public void setOutOfBounds(boolean z) {
            this.mOutOfBounds = z;
        }

        public void setWindowFocused(boolean z) {
            this.mWindowFocused = z;
        }

        public void updateToolbarVisibility() {
            if (this.mActive) {
                if (this.mHideRequested || this.mMoving || this.mOutOfBounds || !this.mWindowFocused) {
                    this.mToolbar.hide();
                } else {
                    this.mToolbar.show();
                    this.mLastShowTime = System.currentTimeMillis();
                }
            }
        }
    }

    public Point getContentRectOnScreen() {
        Point movedPos = this.mFloatingToolbar.getMovedPos();
        return new Point(this.mContentRectOnScreen.centerX() - movedPos.x, this.mContentRectOnScreen.centerY() - movedPos.y);
    }
}
