package android.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.pm.ComponentInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.provider.Settings;
import android.text.Layout;
import android.text.MultiSelection;
import android.text.Spannable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.PathInterpolator;
import com.android.internal.R;
import com.android.internal.view.FloatingActionMode;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes5.dex */
public class MultiSelectPopupWindow {
    private static final String TAG = "MultiSelectPopupWindow";
    private static final float[] TEMP_POSITION = new float[2];
    private static final int TW_MENU_ITEM_ORDER_CLOSE = 0;
    private static final int TW_MENU_ITEM_ORDER_COPY = 2;
    private static final int TW_MENU_ITEM_ORDER_SELECT_ALL = 1;
    private static final int TW_MENU_ITEM_ORDER_SHARE = 3;
    private static final int TW_MENU_ITEM_ORDER_TRANSLATE = 5;
    private static MultiSelectPopupWindow sInstance;
    private static ActionMode sTextActionMode;
    private static TextView sTextView;
    private PositionListener mPositionListener;
    private Drawable mSelectHandleLeft;
    private Drawable mSelectHandleRight;
    private SelectionController mSelectionController;
    private final Runnable mShowFloatingToolbar = new Runnable(this) { // from class: android.widget.MultiSelectPopupWindow.1
        @Override // java.lang.Runnable
        public void run() {
            if (MultiSelectPopupWindow.sTextActionMode != null) {
                MultiSelectPopupWindow.sTextActionMode.hide(0L);
            }
        }
    };

    private interface CursorController extends ViewTreeObserver.OnTouchModeChangeListener {
        void hide();

        void onDetached();

        void show();
    }

    private interface TextViewPositionListener {
        void updatePosition(int i, int i2, boolean z, boolean z2);
    }

    public static MultiSelectPopupWindow getInstance() {
        if (sInstance == null) {
            sInstance = new MultiSelectPopupWindow();
        }
        return sInstance;
    }

    private MultiSelectPopupWindow() {
        sTextView = null;
        sTextActionMode = null;
    }

    public void showMultiSelectPopupWindow() {
        if (getSelectionController() != null) {
            getSelectionController().hide();
            getSelectionController().show();
        }
        ActionMode actionMode = sTextActionMode;
        if (actionMode != null) {
            actionMode.invalidate();
        } else {
            sTextActionMode = sTextView.startActionMode(new TextActionModeCallback(true), 1);
        }
    }

    public void hideMultiSelectPopupWindow() {
        if (getSelectionController() != null) {
            getSelectionController().hide();
        }
        ActionMode actionMode = sTextActionMode;
        if (actionMode != null) {
            actionMode.finish();
        }
        sTextView = null;
    }

    public void changeCurrentSelectedView(TextView textView) {
        if (sTextView == textView) {
            return;
        }
        sTextView = textView;
    }

    void onScrollChanged() {
        PositionListener positionListener = this.mPositionListener;
        if (positionListener != null) {
            positionListener.onScrollChanged();
        }
    }

    private void hideFloatingToolbar() {
        if (sTextActionMode != null) {
            sTextView.removeCallbacks(this.mShowFloatingToolbar);
            sTextActionMode.hide(-1L);
        }
    }

    private void showFloatingToolbar() {
        if (sTextActionMode != null) {
            sTextView.postDelayed(this.mShowFloatingToolbar, ViewConfiguration.getDoubleTapTimeout());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateFloatingToolbarVisibility(MotionEvent motionEvent) {
        if (sTextActionMode != null) {
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked != 1) {
                if (actionMasked == 2) {
                    hideFloatingToolbar();
                    return;
                } else if (actionMasked != 3) {
                    return;
                }
            }
            showFloatingToolbar();
        }
    }

    private class TextActionModeCallback extends ActionMode.Callback2 {
        private int mHandleHeight;
        private final Path mSelectionPath = new Path();
        private final RectF mSelectionBounds = new RectF();

        public TextActionModeCallback(boolean z) {
            if (z) {
                SelectionController selectionController = MultiSelectPopupWindow.this.getSelectionController();
                if (selectionController != null && selectionController.mStartHandle == null) {
                    selectionController.initDrawables();
                    selectionController.initHandles();
                    selectionController.hide();
                }
                this.mHandleHeight = Math.max(MultiSelectPopupWindow.this.mSelectHandleLeft.getMinimumHeight(), MultiSelectPopupWindow.this.mSelectHandleRight.getMinimumHeight());
            }
        }

        @Override // android.view.ActionMode.Callback
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            actionMode.setTitle((CharSequence) null);
            actionMode.setSubtitle((CharSequence) null);
            actionMode.setTitleOptionalHint(true);
            populateMenuWithItems(menu);
            return menu.size() > 1;
        }

        private void populateMenuWithItems(Menu menu) {
            updateSelectAllItem(menu);
            menu.add(0, R.id.floatingToolbarClose, 0, R.string.close).setIcon(MultiSelectPopupWindow.sTextView.getContext().getResources().getDrawable(R.drawable.tw_floating_popup_button_ic_close));
            if (!MultiSelectPopupWindow.sTextView.isClipboardDisallowedByKnox()) {
                menu.add(0, R.id.multiSelectCopy, 2, 17039361).setIcon(MultiSelectPopupWindow.sTextView.getContext().getResources().getDrawable(R.drawable.tw_floating_popup_button_ic_copy)).setShowAsAction(2);
            }
            if (MultiSelectPopupWindow.this.isShareViaEnable()) {
                menu.add(0, R.id.multiSelectShare, 3, R.string.share).setIcon(MultiSelectPopupWindow.sTextView.getContext().getResources().getDrawable(R.drawable.tw_floating_popup_button_ic_share)).setShowAsAction(1);
            }
            PackageManager packageManager = MultiSelectPopupWindow.sTextView.getContext().getPackageManager();
            List<ResolveInfo> listQueryIntentActivities = packageManager.queryIntentActivities(new Intent().setAction(Intent.ACTION_PROCESS_TEXT).setType("text/plain"), 0);
            if (MultiSelectPopupWindow.this.isEmergencyMode()) {
                return;
            }
            int i = 0;
            for (ResolveInfo resolveInfo : listQueryIntentActivities) {
                ComponentInfo componentInfo = resolveInfo.getComponentInfo();
                if (componentInfo.packageName.contains("com.sec.android.app.translator") || componentInfo.packageName.contains("com.google.android.apps.translate")) {
                    menu.add(0, R.id.multiSelectTranslate, i + 5, resolveInfo.loadLabel(packageManager)).setIcon(MultiSelectPopupWindow.sTextView.getContext().getResources().getDrawable(R.drawable.tw_floating_popup_button_ic_translate)).setIntent(new Intent().setAction(Intent.ACTION_PROCESS_TEXT).setType("text/plain").putExtra(Intent.EXTRA_PROCESS_TEXT_READONLY, true).setClassName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name)).setShowAsAction(1);
                    i++;
                }
            }
        }

        @Override // android.view.ActionMode.Callback
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            updateSelectAllItem(menu);
            return true;
        }

        private void updateSelectAllItem(Menu menu) {
            boolean z = menu.findItem(R.id.multiSelectAll) != null;
            boolean zIsSelectAllEnable = MultiSelectPopupWindow.this.isSelectAllEnable();
            if (zIsSelectAllEnable && !z) {
                menu.add(0, R.id.multiSelectAll, 1, 17039373).setIcon(MultiSelectPopupWindow.sTextView.getContext().getResources().getDrawable(R.drawable.tw_floating_popup_button_ic_selectall)).setShowAsAction(1);
            } else {
                if (zIsSelectAllEnable || !z) {
                    return;
                }
                menu.removeItem(R.id.multiSelectAll);
            }
        }

        @Override // android.view.ActionMode.Callback
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            if (menuItem.getItemId() == 16909394 && (actionMode instanceof FloatingActionMode)) {
                MultiSelectPopupWindow.sTextView.startChooserPopupActivity(((FloatingActionMode) actionMode).getContentRectOnScreen(), true);
                return true;
            }
            return MultiSelectPopupWindow.sTextView.onMultiSelectMenuItem(menuItem);
        }

        @Override // android.view.ActionMode.Callback
        public void onDestroyActionMode(ActionMode actionMode) {
            MultiSelectPopupWindow.sTextActionMode = null;
            if (MultiSelectPopupWindow.this.mSelectionController != null) {
                MultiSelectPopupWindow.this.mSelectionController.hide();
                MultiSelectPopupWindow.this.mSelectionController = null;
            }
        }

        @Override // android.view.ActionMode.Callback2
        public void onGetContentRect(ActionMode actionMode, View view, Rect rect) throws Resources.NotFoundException {
            if (!view.equals(MultiSelectPopupWindow.sTextView) || MultiSelectPopupWindow.sTextView.getLayout() == null) {
                super.onGetContentRect(actionMode, view, rect);
                return;
            }
            int dimensionPixelSize = MultiSelectPopupWindow.sTextView.getResources().getDimensionPixelSize(R.dimen.tw_floating_popup_top_margin);
            int dimensionPixelSize2 = MultiSelectPopupWindow.sTextView.getResources().getDimensionPixelSize(R.dimen.tw_floating_popup_bottom_margin);
            CharSequence textForMultiSelection = MultiSelectPopupWindow.sTextView.getTextForMultiSelection();
            if (textForMultiSelection == null) {
                Log.e(MultiSelectPopupWindow.TAG, "getTextFormultiSelection() text is null");
                return;
            }
            if (MultiSelection.getSelectionStart(textForMultiSelection) != MultiSelection.getSelectionEnd(textForMultiSelection)) {
                this.mSelectionPath.reset();
                MultiSelectPopupWindow.sTextView.getLayout().getSelectionPath(MultiSelection.getSelectionStart(textForMultiSelection), MultiSelection.getSelectionEnd(textForMultiSelection), this.mSelectionPath);
                this.mSelectionPath.computeBounds(this.mSelectionBounds, true);
                this.mSelectionBounds.top -= dimensionPixelSize2;
                this.mSelectionBounds.bottom += this.mHandleHeight + dimensionPixelSize;
            }
            float fViewportToContentHorizontalOffset = MultiSelectPopupWindow.sTextView.viewportToContentHorizontalOffset();
            float fViewportToContentVerticalOffset = MultiSelectPopupWindow.sTextView.viewportToContentVerticalOffset();
            rect.set((int) Math.floor(this.mSelectionBounds.left + fViewportToContentHorizontalOffset), (int) Math.floor(this.mSelectionBounds.top + fViewportToContentVerticalOffset), (int) Math.ceil(this.mSelectionBounds.right + fViewportToContentHorizontalOffset), (int) Math.ceil(this.mSelectionBounds.bottom + fViewportToContentVerticalOffset));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isSelectAllEnable() {
        CharSequence textForMultiSelection = sTextView.getTextForMultiSelection();
        if (textForMultiSelection != null) {
            return (MultiSelection.getSelectionStart(textForMultiSelection) == 0 && MultiSelection.getSelectionEnd(textForMultiSelection) == textForMultiSelection.length()) ? false : true;
        }
        Log.e(TAG, "getTextFormultiSelection() text is null");
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isShareViaEnable() {
        return !isEmergencyMode();
    }

    private boolean isDictionaryEnable() {
        return (sTextView.getContext().getPackageManager().queryIntentActivities(new Intent("com.sec.android.app.dictionary.SEARCH"), 0).size() == 0 || isEmergencyMode()) ? false : true;
    }

    private boolean isTranslatorEnable() {
        List<ResolveInfo> listQueryIntentActivities = sTextView.getContext().getPackageManager().queryIntentActivities(new Intent().setAction(Intent.ACTION_PROCESS_TEXT).setType("text/plain"), 0);
        if (listQueryIntentActivities.size() != 0 && !isEmergencyMode()) {
            Iterator<ResolveInfo> it = listQueryIntentActivities.iterator();
            while (it.hasNext()) {
                String string = it.next().toString();
                if (string.contains("com.sec.android.app.translator") || string.contains("com.google.android.apps.translate")) {
                    return true;
                }
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isEmergencyMode() {
        boolean z = Settings.System.getInt(sTextView.getContext().getContentResolver(), Settings.System.SEM_EMERGENCY_MODE, 0) == 1;
        boolean z2 = Settings.System.getInt(sTextView.getContext().getContentResolver(), Settings.System.SEM_ULTRA_POWERSAVING_MODE, 0) == 1;
        if (!z && !z2) {
            return false;
        }
        Log.d(TAG, "isEmergencyMode = " + z + ", isUPSMode = " + z2);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public PositionListener getPositionListener() {
        if (this.mPositionListener == null) {
            this.mPositionListener = new PositionListener();
        }
        return this.mPositionListener;
    }

    public void initSelectionControllerPosition() {
        if (getSelectionController() != null) {
            getSelectionController().initPreviousOffset();
        }
    }

    SelectionController getSelectionController() {
        if (sTextView == null) {
            return null;
        }
        if (this.mSelectionController == null) {
            this.mSelectionController = new SelectionController();
            sTextView.getViewTreeObserver().addOnTouchModeChangeListener(this.mSelectionController);
        }
        return this.mSelectionController;
    }

    private class PositionListener implements ViewTreeObserver.OnPreDrawListener {
        private final int MAXIMUM_NUMBER_OF_LISTENERS;
        private int[] mNewRect;
        private int mNumberOfListeners;
        private boolean mPositionHasChanged;
        private TextViewPositionListener[] mPositionListeners;
        private int mPositionX;
        private int mPositionY;
        private int[] mRect;
        private boolean mScrollHasChanged;
        final int[] mTempCoords;

        private PositionListener(MultiSelectPopupWindow multiSelectPopupWindow) {
            this.MAXIMUM_NUMBER_OF_LISTENERS = 2;
            this.mPositionListeners = new TextViewPositionListener[2];
            this.mPositionHasChanged = true;
            this.mRect = new int[2];
            this.mNewRect = new int[2];
            this.mTempCoords = new int[2];
        }

        public void addSubscriber(TextViewPositionListener textViewPositionListener) {
            if (this.mNumberOfListeners == 0) {
                updatePosition();
                MultiSelectPopupWindow.sTextView.getViewTreeObserver().addOnPreDrawListener(this);
            }
            int i = -1;
            for (int i2 = 0; i2 < 2; i2++) {
                TextViewPositionListener textViewPositionListener2 = this.mPositionListeners[i2];
                if (textViewPositionListener2 == textViewPositionListener) {
                    return;
                }
                if (i < 0 && textViewPositionListener2 == null) {
                    i = i2;
                }
            }
            this.mPositionListeners[i] = textViewPositionListener;
            this.mNumberOfListeners++;
        }

        public void removeSubscriber(TextViewPositionListener textViewPositionListener) {
            int i = 0;
            while (true) {
                if (i >= 2) {
                    break;
                }
                TextViewPositionListener[] textViewPositionListenerArr = this.mPositionListeners;
                if (textViewPositionListenerArr[i] == textViewPositionListener) {
                    textViewPositionListenerArr[i] = null;
                    this.mNumberOfListeners--;
                    break;
                }
                i++;
            }
            if (this.mNumberOfListeners == 0) {
                MultiSelectPopupWindow.sTextView.getViewTreeObserver().removeOnPreDrawListener(this);
            }
        }

        public int getPositionX() {
            return this.mPositionX;
        }

        public int getPositionY() {
            return this.mPositionY;
        }

        @Override // android.view.ViewTreeObserver.OnPreDrawListener
        public boolean onPreDraw() {
            if (MultiSelectPopupWindow.sTextView == null) {
                this.mPositionListeners[0] = null;
                this.mNumberOfListeners = 0;
                return true;
            }
            updatePosition();
            if (!MultiSelectPopupWindow.sTextView.checkValidMultiSelectionForPreDraw()) {
                MultiSelectPopupWindow.sTextView.clearMultiSelection();
                return true;
            }
            for (int i = 0; i < 2; i++) {
                TextViewPositionListener textViewPositionListener = this.mPositionListeners[i];
                if (textViewPositionListener != null) {
                    textViewPositionListener.updatePosition(this.mPositionX, this.mPositionY, this.mPositionHasChanged, this.mScrollHasChanged);
                }
            }
            this.mScrollHasChanged = false;
            return true;
        }

        /* JADX WARN: Removed duplicated region for block: B:12:0x0044  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private void updatePosition() {
            boolean z;
            MultiSelectPopupWindow.sTextView.getLocationInWindow(this.mTempCoords);
            this.mNewRect[0] = MultiSelectPopupWindow.sTextView.getWidth();
            this.mNewRect[1] = MultiSelectPopupWindow.sTextView.getHeight();
            int[] iArr = this.mTempCoords;
            int i = iArr[0];
            if (i == this.mPositionX && iArr[1] == this.mPositionY) {
                int[] iArr2 = this.mRect;
                int i2 = iArr2[0];
                int[] iArr3 = this.mNewRect;
                if (i2 == iArr3[0] && iArr2[1] == iArr3[1]) {
                    z = false;
                }
            } else {
                z = true;
            }
            this.mPositionHasChanged = z;
            this.mPositionX = i;
            this.mPositionY = iArr[1];
            int[] iArr4 = this.mRect;
            int[] iArr5 = this.mNewRect;
            iArr4[0] = iArr5[0];
            iArr4[1] = iArr5[1];
        }

        public void onScrollChanged() {
            this.mScrollHasChanged = true;
        }
    }

    private class SelectionController implements CursorController {
        private SelectionEndHandleView mEndHandle;
        private SelectionStartHandleView mStartHandle;

        private SelectionController() {
        }

        @Override // android.widget.MultiSelectPopupWindow.CursorController
        public void show() {
            initDrawables();
            initHandles();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void initDrawables() {
            if (MultiSelectPopupWindow.this.mSelectHandleLeft == null) {
                MultiSelectPopupWindow.this.mSelectHandleLeft = MultiSelectPopupWindow.sTextView.getContext().getResources().getDrawable(MultiSelectPopupWindow.sTextView.mTextSelectHandleLeftRes);
            }
            if (MultiSelectPopupWindow.this.mSelectHandleRight == null) {
                MultiSelectPopupWindow.this.mSelectHandleRight = MultiSelectPopupWindow.sTextView.getContext().getResources().getDrawable(MultiSelectPopupWindow.sTextView.mTextSelectHandleRightRes);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void initHandles() {
            if (this.mStartHandle == null) {
                MultiSelectPopupWindow multiSelectPopupWindow = MultiSelectPopupWindow.this;
                this.mStartHandle = multiSelectPopupWindow.new SelectionStartHandleView(multiSelectPopupWindow.mSelectHandleLeft, MultiSelectPopupWindow.this.mSelectHandleRight);
            }
            if (this.mEndHandle == null) {
                MultiSelectPopupWindow multiSelectPopupWindow2 = MultiSelectPopupWindow.this;
                this.mEndHandle = multiSelectPopupWindow2.new SelectionEndHandleView(multiSelectPopupWindow2.mSelectHandleRight, MultiSelectPopupWindow.this.mSelectHandleLeft);
            }
            this.mStartHandle.show();
            this.mEndHandle.show();
        }

        @Override // android.widget.MultiSelectPopupWindow.CursorController
        public void hide() {
            SelectionStartHandleView selectionStartHandleView = this.mStartHandle;
            if (selectionStartHandleView != null) {
                selectionStartHandleView.hide();
            }
            SelectionEndHandleView selectionEndHandleView = this.mEndHandle;
            if (selectionEndHandleView != null) {
                selectionEndHandleView.hide();
            }
        }

        public boolean isSelectionStartDragged() {
            SelectionStartHandleView selectionStartHandleView = this.mStartHandle;
            return selectionStartHandleView != null && selectionStartHandleView.isDragging();
        }

        public boolean isSelectionEndDragged() {
            SelectionEndHandleView selectionEndHandleView = this.mEndHandle;
            return selectionEndHandleView != null && selectionEndHandleView.isDragging();
        }

        @Override // android.view.ViewTreeObserver.OnTouchModeChangeListener
        public void onTouchModeChanged(boolean z) {
            if (z) {
                return;
            }
            hide();
        }

        @Override // android.widget.MultiSelectPopupWindow.CursorController
        public void onDetached() {
            MultiSelectPopupWindow.sTextView.getViewTreeObserver().removeOnTouchModeChangeListener(this);
            SelectionStartHandleView selectionStartHandleView = this.mStartHandle;
            if (selectionStartHandleView != null) {
                selectionStartHandleView.onDetached();
            }
            SelectionEndHandleView selectionEndHandleView = this.mEndHandle;
            if (selectionEndHandleView != null) {
                selectionEndHandleView.onDetached();
            }
        }

        public void initPreviousOffset() {
            SelectionStartHandleView selectionStartHandleView = this.mStartHandle;
            if (selectionStartHandleView != null) {
                selectionStartHandleView.initPreviousOffset();
            }
            SelectionEndHandleView selectionEndHandleView = this.mEndHandle;
            if (selectionEndHandleView != null) {
                selectionEndHandleView.initPreviousOffset();
            }
        }
    }

    private abstract class HandleView extends View implements TextViewPositionListener {
        static final int HANDLE_TYPE_END = 2;
        static final int HANDLE_TYPE_NONE = 0;
        static final int HANDLE_TYPE_START = 1;
        static final String HEIGHT = "height";
        static final float MAGNIFYING_FACTOR = 1.5f;
        static final String WIDTH = "width";
        protected int mBaselineY;
        private final PopupWindow mContainer;
        protected Drawable mDrawable;
        protected Drawable mDrawableLtr;
        protected Drawable mDrawableRtl;
        protected int mEndRange;
        public int mHandleType;
        protected int mHorizontalGravity;
        protected int mHotspotX;
        private float mIdealVerticalOffset;
        protected boolean mIsDragging;
        private boolean mIsResetAnimating;
        private int mLastParentX;
        private int mLastParentY;
        private ValueAnimator mMagnifySizeAnimator;
        private int mMinSize;
        protected boolean mPositionHasChanged;
        protected int mPositionX;
        protected int mPositionY;
        private int mPreviousOffset;
        private ValueAnimator mResetAnimator;
        protected int mStartRange;
        private float mTouchOffsetY;
        private float mTouchToWindowOffsetX;
        private float mTouchToWindowOffsetY;
        protected boolean mbSwitchCursor;

        protected boolean calculateForSwitchingCursor() {
            return true;
        }

        public abstract int getCurrentCursorOffset();

        protected abstract int getHotspotX(Drawable drawable, boolean z);

        public void onDetached() {
        }

        void onHandleMoved() {
        }

        public boolean refreshForSwitchingCursor() {
            return true;
        }

        public abstract void updatePosition(float f, float f2);

        protected abstract void updateSelection(int i);

        public HandleView(Drawable drawable, Drawable drawable2) {
            super(MultiSelectPopupWindow.sTextView.getContext());
            this.mPreviousOffset = -1;
            this.mPositionHasChanged = true;
            this.mResetAnimator = null;
            this.mMagnifySizeAnimator = null;
            this.mHandleType = 0;
            LinearLayout linearLayout = new LinearLayout(MultiSelectPopupWindow.sTextView.getContext());
            linearLayout.setGravity(3);
            PopupWindow popupWindow = new PopupWindow(MultiSelectPopupWindow.sTextView.getContext(), (AttributeSet) null, 16843464);
            this.mContainer = popupWindow;
            popupWindow.setSplitTouchEnabled(true);
            popupWindow.setClippingEnabled(false);
            popupWindow.setWindowLayoutType(1002);
            popupWindow.setContentView(linearLayout);
            linearLayout.addView(this);
            this.mDrawableLtr = drawable;
            this.mDrawableRtl = drawable2;
            updateDrawable();
            recalHandleView();
            this.mMinSize = MultiSelectPopupWindow.sTextView.getContext().getResources().getDimensionPixelSize(R.dimen.text_handle_min_size);
            popupWindow.setWidth(Math.max((int) (this.mDrawable.getIntrinsicWidth() * 1.5f), this.mMinSize));
            popupWindow.setHeight(Math.max((int) (this.mDrawable.getIntrinsicHeight() * 1.5f), this.mMinSize));
        }

        protected void updateDrawable() {
            boolean zIsRtlCharAt = MultiSelectPopupWindow.sTextView.getLayout().isRtlCharAt(getCurrentCursorOffset());
            Drawable drawable = zIsRtlCharAt ? this.mDrawableRtl : this.mDrawableLtr;
            this.mDrawable = drawable;
            this.mHotspotX = getHotspotX(drawable, zIsRtlCharAt);
            this.mHorizontalGravity = getHorizontalGravity(zIsRtlCharAt);
        }

        protected int getHorizontalGravity(boolean z) {
            return z == (this.mHandleType == 1) ? 3 : 5;
        }

        protected int getHorizontalOffset() {
            int preferredWidth = getPreferredWidth();
            int intrinsicWidth = this.mDrawable.getIntrinsicWidth();
            int i = this.mHorizontalGravity;
            if (i != 3) {
                return i != 5 ? (preferredWidth - intrinsicWidth) / 2 : preferredWidth - intrinsicWidth;
            }
            return 0;
        }

        public Rect getDrawableBounds(int i, int i2) {
            int i3;
            int horizontalOffset = getHorizontalOffset();
            Drawable drawable = this.mDrawable;
            int hotspotX = getHotspotX(drawable, drawable == this.mDrawableRtl);
            int i4 = this.mHorizontalGravity;
            if (i4 == 1) {
                i3 = i / 2;
            } else if (i4 == 3) {
                i3 = i / 4;
            } else {
                i3 = i4 != 5 ? 0 : (i * 3) / 4;
            }
            int i5 = horizontalOffset - (i3 - hotspotX);
            return new Rect(i5, 0, i + i5, i2);
        }

        @Override // android.view.View
        protected void onMeasure(int i, int i2) {
            if (this.mIsDragging || this.mIsResetAnimating) {
                setMeasuredDimension((int) Math.ceil(getPreferredWidth() * 1.5f), (int) Math.ceil(getPreferredHeight() * 1.5f));
            } else {
                setMeasuredDimension(getPreferredWidth(), getPreferredHeight());
            }
        }

        private int getPreferredWidth() {
            return Math.max(this.mDrawable.getIntrinsicWidth(), this.mMinSize);
        }

        private int getPreferredHeight() {
            return Math.max(this.mDrawable.getIntrinsicHeight(), this.mMinSize);
        }

        public void show() {
            if (isShowing()) {
                return;
            }
            MultiSelectPopupWindow.this.getPositionListener().addSubscriber(this);
            this.mPreviousOffset = -1;
            positionAtCursorOffset(getCurrentCursorOffset(), false, false);
            int[] iArr = new int[2];
            int[] iArr2 = new int[2];
            MultiSelectPopupWindow.sTextView.getLocationInWindow(iArr);
            MultiSelectPopupWindow.sTextView.getLocationOnScreen(iArr2);
            int i = this.mPositionX;
            int i2 = iArr[0];
            int i3 = i + i2;
            int i4 = this.mPositionY;
            int i5 = iArr[1];
            int i6 = i4 + i5;
            int i7 = iArr2[0] - i2;
            int i8 = iArr2[1] - i5;
            if (isShowing()) {
                if (MultiSelectPopupWindow.sTextView.getApplicationWindowToken() != null && MultiSelectPopupWindow.sTextView.getApplicationWindowToken() != MultiSelectPopupWindow.sTextView.getWindowToken()) {
                    i3 += i7;
                    i6 += i8;
                }
                this.mContainer.update(i3, i6, -1, -1);
                return;
            }
            if (MultiSelectPopupWindow.sTextView.getApplicationWindowToken() != null && MultiSelectPopupWindow.sTextView.getApplicationWindowToken() != MultiSelectPopupWindow.sTextView.getWindowToken()) {
                this.mContainer.setLayoutInScreenEnabled(true);
                this.mContainer.showAtLocation(MultiSelectPopupWindow.sTextView.getApplicationWindowToken(), 0, i3 + i7, i6 + i8);
                return;
            }
            this.mContainer.setLayoutInScreenEnabled(false);
            try {
                this.mContainer.showAtLocation(MultiSelectPopupWindow.sTextView, 0, i3, i6);
            } catch (WindowManager.BadTokenException unused) {
                MultiSelectPopupWindow.sTextView.clearAllMultiSelection();
                Log.e(MultiSelectPopupWindow.TAG, "showAtLocation occur BadTokenException");
            }
        }

        protected void dismiss() {
            this.mIsDragging = false;
            this.mIsResetAnimating = false;
            this.mContainer.dismiss();
            onDetached();
            this.mbSwitchCursor = false;
        }

        public void hide() {
            dismiss();
            MultiSelectPopupWindow.this.getPositionListener().removeSubscriber(this);
        }

        public boolean isShowing() {
            return this.mContainer.isShowing();
        }

        private boolean isVisible() {
            if (this.mIsDragging) {
                return true;
            }
            return isPositionVisible(this.mPositionX, this.mBaselineY);
        }

        private boolean isPositionVisible(int i, int i2) {
            synchronized (MultiSelectPopupWindow.TEMP_POSITION) {
                float[] fArr = MultiSelectPopupWindow.TEMP_POSITION;
                fArr[0] = i;
                fArr[1] = i2;
                View view = MultiSelectPopupWindow.sTextView;
                while (view != null) {
                    if (view != MultiSelectPopupWindow.sTextView) {
                        fArr[0] = fArr[0] - view.getScrollX();
                        fArr[1] = fArr[1] - view.getScrollY();
                    }
                    if (fArr[0] + this.mContainer.getWidth() >= 0.0f && fArr[1] >= 0.0f && fArr[0] <= view.getWidth() && fArr[1] <= view.getHeight()) {
                        if (!view.getMatrix().isIdentity()) {
                            view.getMatrix().mapPoints(fArr);
                        }
                        fArr[0] = fArr[0] + view.getLeft();
                        fArr[1] = fArr[1] + view.getTop();
                        Object parent = view.getParent();
                        view = parent instanceof View ? (View) parent : null;
                    }
                    return false;
                }
                return true;
            }
        }

        protected void positionAtCursorOffset(int i, boolean z, boolean z2) {
            Layout layout = MultiSelectPopupWindow.sTextView.getLayout();
            if (layout == null) {
                return;
            }
            boolean z3 = i != this.mPreviousOffset;
            if (z3 || z || z2) {
                if (z3) {
                    updateSelection(i);
                }
                int lineForOffset = layout.getLineForOffset(i);
                this.mPositionX = (int) (((layout.getPrimaryHorizontal(i) + (layout.getParagraphDirection(lineForOffset) == -1 ? 0.5f : -0.5f)) - this.mHotspotX) - getHorizontalOffset());
                this.mPositionY = layout.getLineBottom(lineForOffset);
                this.mBaselineY = layout.getLineBaseline(lineForOffset);
                this.mPositionX += MultiSelectPopupWindow.sTextView.viewportToContentHorizontalOffset();
                this.mPositionY += MultiSelectPopupWindow.sTextView.viewportToContentVerticalOffset();
                this.mBaselineY += MultiSelectPopupWindow.sTextView.viewportToContentVerticalOffset();
                this.mPreviousOffset = i;
                this.mPositionHasChanged = true;
            }
        }

        @Override // android.widget.MultiSelectPopupWindow.TextViewPositionListener
        public void updatePosition(int i, int i2, boolean z, boolean z2) {
            positionAtCursorOffset(getCurrentCursorOffset(), z, z2);
            if (z || this.mPositionHasChanged) {
                if (this.mIsDragging) {
                    if (i != this.mLastParentX || i2 != this.mLastParentY) {
                        this.mTouchToWindowOffsetX += i - r8;
                        this.mTouchToWindowOffsetY += i2 - this.mLastParentY;
                        this.mLastParentX = i;
                        this.mLastParentY = i2;
                    }
                    onHandleMoved();
                }
                if (isVisible() && !z) {
                    int[] iArr = new int[2];
                    int[] iArr2 = new int[2];
                    MultiSelectPopupWindow.sTextView.getLocationInWindow(iArr);
                    MultiSelectPopupWindow.sTextView.getLocationOnScreen(iArr2);
                    int i3 = iArr2[0] - iArr[0];
                    int i4 = iArr2[1] - iArr[1];
                    int i5 = i + this.mPositionX;
                    int i6 = i2 + this.mPositionY;
                    if (isShowing()) {
                        if (MultiSelectPopupWindow.sTextView.getApplicationWindowToken() != null && MultiSelectPopupWindow.sTextView.getApplicationWindowToken() != MultiSelectPopupWindow.sTextView.getWindowToken()) {
                            i5 += i3;
                            i6 += i4;
                        }
                        this.mContainer.update(i5, i6, -1, -1);
                    } else if (MultiSelectPopupWindow.sTextView.getApplicationWindowToken() != null && MultiSelectPopupWindow.sTextView.getApplicationWindowToken() != MultiSelectPopupWindow.sTextView.getWindowToken()) {
                        this.mContainer.setLayoutInScreenEnabled(true);
                        this.mContainer.showAtLocation(MultiSelectPopupWindow.sTextView.getApplicationWindowToken(), 0, i5 + i3, i6 + i4);
                    } else {
                        this.mContainer.setLayoutInScreenEnabled(false);
                        try {
                            this.mContainer.showAtLocation(MultiSelectPopupWindow.sTextView, 0, i5, i6);
                        } catch (WindowManager.BadTokenException unused) {
                            MultiSelectPopupWindow.sTextView.clearAllMultiSelection();
                            Log.e(MultiSelectPopupWindow.TAG, "showAtLocation occur BadTokenException");
                        }
                    }
                } else if (isShowing()) {
                    dismiss();
                }
                this.mPositionHasChanged = false;
            }
        }

        @Override // android.view.View
        protected void onDraw(Canvas canvas) {
            int intrinsicWidth = this.mDrawable.getIntrinsicWidth();
            int horizontalOffset = getHorizontalOffset();
            if (!this.mIsDragging && !this.mIsResetAnimating) {
                Drawable drawable = this.mDrawable;
                drawable.setBounds(horizontalOffset, 0, intrinsicWidth + horizontalOffset, drawable.getIntrinsicHeight());
            }
            this.mDrawable.draw(canvas);
        }

        @Override // android.view.View
        public boolean onTouchEvent(MotionEvent motionEvent) {
            float fMin;
            CharSequence textForMultiSelection = MultiSelectPopupWindow.sTextView.getTextForMultiSelection();
            if (textForMultiSelection == null) {
                Log.e(MultiSelectPopupWindow.TAG, "getTextFormultiSelection() text is null");
                return true;
            }
            MultiSelectPopupWindow.this.updateFloatingToolbarVisibility(motionEvent);
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked == 0) {
                this.mTouchToWindowOffsetX = motionEvent.getRawXForScaledWindow() - this.mPositionX;
                this.mTouchToWindowOffsetY = motionEvent.getRawYForScaledWindow() - this.mPositionY;
                int[] iArr = new int[2];
                if (MultiSelectPopupWindow.sTextView.getVisibleTextRange(iArr)) {
                    this.mStartRange = iArr[0];
                    this.mEndRange = iArr[1];
                } else {
                    this.mStartRange = 0;
                    this.mEndRange = textForMultiSelection.length();
                }
                PositionListener positionListener = MultiSelectPopupWindow.this.getPositionListener();
                this.mLastParentX = positionListener.getPositionX();
                this.mLastParentY = positionListener.getPositionY();
                this.mIsDragging = true;
                magnifyHandleView();
                MultiSelectPopupWindow.sTextView.mIsTouchDown = true;
            } else if (actionMasked == 1) {
                this.mIsDragging = false;
                this.mIsResetAnimating = true;
                resetHandleView();
                MultiSelectPopupWindow.sTextView.mIsTouchDown = false;
                refreshForSwitchingCursor();
                int selectionStart = MultiSelection.getSelectionStart(textForMultiSelection);
                int selectionEnd = MultiSelection.getSelectionEnd(textForMultiSelection);
                if (selectionStart > selectionEnd) {
                    MultiSelection.setSelection((Spannable) textForMultiSelection, selectionEnd, selectionStart);
                }
            } else if (actionMasked == 2) {
                float rawXForScaledWindow = motionEvent.getRawXForScaledWindow();
                float rawYForScaledWindow = motionEvent.getRawYForScaledWindow();
                float f = this.mTouchToWindowOffsetY;
                int i = this.mLastParentY;
                float f2 = f - i;
                float f3 = (rawYForScaledWindow - this.mPositionY) - i;
                float f4 = this.mIdealVerticalOffset;
                if (f2 < f4) {
                    fMin = Math.max(Math.min(f3, f4), f2);
                } else if (f3 < f2) {
                    fMin = Math.max(Math.max(f3, f4), f2);
                } else {
                    fMin = Math.min(Math.max(f3, f4), f2);
                }
                this.mTouchToWindowOffsetY = fMin + this.mLastParentY;
                updatePosition((rawXForScaledWindow - this.mTouchToWindowOffsetX) + this.mHotspotX + getHorizontalOffset(), (rawYForScaledWindow - this.mTouchToWindowOffsetY) + this.mTouchOffsetY);
            } else if (actionMasked == 3) {
                this.mIsDragging = false;
                this.mIsResetAnimating = true;
                resetHandleView();
            }
            return true;
        }

        public boolean isDragging() {
            return this.mIsDragging;
        }

        public void initPreviousOffset() {
            this.mPreviousOffset = -1;
        }

        public void recalHandleView() {
            float intrinsicHeight = this.mDrawable.getIntrinsicHeight();
            this.mTouchOffsetY = (-0.3f) * intrinsicHeight;
            this.mIdealVerticalOffset = intrinsicHeight * 0.7f;
        }

        private void magnifyHandleView() {
            requestLayout();
            int intrinsicWidth = this.mDrawable.getIntrinsicWidth();
            int intrinsicHeight = this.mDrawable.getIntrinsicHeight();
            final int i = (int) (intrinsicWidth * 1.5f);
            final int i2 = (int) (intrinsicHeight * 1.5f);
            ValueAnimator valueAnimatorOfPropertyValuesHolder = ValueAnimator.ofPropertyValuesHolder(PropertyValuesHolder.ofInt("width", intrinsicWidth, i), PropertyValuesHolder.ofInt("height", intrinsicHeight, i2));
            this.mMagnifySizeAnimator = valueAnimatorOfPropertyValuesHolder;
            valueAnimatorOfPropertyValuesHolder.setDuration(250L);
            this.mMagnifySizeAnimator.setInterpolator(new PathInterpolator(0.25f, 0.46f, 0.45f, 1.0f));
            this.mMagnifySizeAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: android.widget.MultiSelectPopupWindow.HandleView.1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    HandleView.this.mDrawable.setBounds(HandleView.this.getDrawableBounds(((Integer) valueAnimator.getAnimatedValue("width")).intValue(), ((Integer) valueAnimator.getAnimatedValue("height")).intValue()));
                    HandleView.this.invalidate();
                }
            });
            this.mMagnifySizeAnimator.addListener(new AnimatorListenerAdapter() { // from class: android.widget.MultiSelectPopupWindow.HandleView.2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    HandleView.this.mDrawable.setBounds(HandleView.this.getDrawableBounds(i, i2));
                    HandleView.this.invalidate();
                }
            });
            this.mMagnifySizeAnimator.start();
        }

        private void resetHandleView() {
            if (this.mMagnifySizeAnimator.isStarted()) {
                this.mMagnifySizeAnimator.pause();
            }
            Rect bounds = this.mDrawable.getBounds();
            ValueAnimator valueAnimatorOfPropertyValuesHolder = ValueAnimator.ofPropertyValuesHolder(PropertyValuesHolder.ofInt("width", bounds.right - bounds.left, this.mDrawable.getIntrinsicWidth()), PropertyValuesHolder.ofInt("height", bounds.bottom - bounds.top, this.mDrawable.getIntrinsicHeight()));
            this.mResetAnimator = valueAnimatorOfPropertyValuesHolder;
            valueAnimatorOfPropertyValuesHolder.setDuration(250L);
            this.mResetAnimator.setInterpolator(new PathInterpolator(0.25f, 0.46f, 0.45f, 1.0f));
            this.mResetAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: android.widget.MultiSelectPopupWindow.HandleView.3
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    if (HandleView.this.mIsResetAnimating) {
                        HandleView.this.mDrawable.setBounds(HandleView.this.getDrawableBounds(((Integer) valueAnimator.getAnimatedValue("width")).intValue(), ((Integer) valueAnimator.getAnimatedValue("height")).intValue()));
                        HandleView.this.invalidate();
                    }
                }
            });
            this.mResetAnimator.addListener(new AnimatorListenerAdapter() { // from class: android.widget.MultiSelectPopupWindow.HandleView.4
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    if (HandleView.this.mIsResetAnimating) {
                        HandleView.this.mIsResetAnimating = false;
                        HandleView.this.requestLayout();
                        HandleView.this.invalidate();
                    }
                }
            });
            this.mResetAnimator.start();
        }
    }

    private class SelectionStartHandleView extends HandleView {
        public SelectionStartHandleView(Drawable drawable, Drawable drawable2) {
            super(drawable, drawable2);
            this.mHandleType = 1;
        }

        @Override // android.widget.MultiSelectPopupWindow.HandleView
        protected int getHotspotX(Drawable drawable, boolean z) {
            if (z) {
                return drawable.getIntrinsicWidth() / 4;
            }
            return (drawable.getIntrinsicWidth() * 3) / 4;
        }

        @Override // android.widget.MultiSelectPopupWindow.HandleView
        public int getCurrentCursorOffset() {
            CharSequence textForMultiSelection = MultiSelectPopupWindow.sTextView.getTextForMultiSelection();
            if (textForMultiSelection == null) {
                Log.e(MultiSelectPopupWindow.TAG, "getTextFormultiSelection() text is null");
            }
            return MultiSelection.getSelectionStart(textForMultiSelection);
        }

        @Override // android.widget.MultiSelectPopupWindow.HandleView
        public void updateSelection(int i) {
            CharSequence textForMultiSelection = MultiSelectPopupWindow.sTextView.getTextForMultiSelection();
            if (textForMultiSelection == null) {
                Log.e(MultiSelectPopupWindow.TAG, "getTextFormultiSelection() text is null");
                return;
            }
            MultiSelection.setSelection((Spannable) textForMultiSelection, i, MultiSelection.getSelectionEnd(textForMultiSelection));
            updateDrawable();
            if (MultiSelectPopupWindow.sTextActionMode != null) {
                MultiSelectPopupWindow.sTextActionMode.invalidate();
            }
        }

        @Override // android.widget.MultiSelectPopupWindow.HandleView
        public void updatePosition(float f, float f2) {
            int offsetForPosition = MultiSelectPopupWindow.sTextView.getOffsetForPosition(f, f2);
            if (offsetForPosition == MultiSelection.getSelectionEnd(MultiSelectPopupWindow.sTextView.getTextForMultiSelection())) {
                return;
            }
            if (offsetForPosition < this.mStartRange) {
                offsetForPosition = this.mStartRange;
            }
            positionAtCursorOffset(offsetForPosition, false, false);
        }

        @Override // android.widget.MultiSelectPopupWindow.HandleView
        protected void positionAtCursorOffset(int i, boolean z, boolean z2) {
            super.positionAtCursorOffset(i, z, z2);
            calculateForSwitchingCursor();
            this.mPositionHasChanged = true;
            invalidate();
        }

        @Override // android.widget.MultiSelectPopupWindow.HandleView
        public boolean refreshForSwitchingCursor() {
            if (!isHandleViewScreenOut() || this.mbSwitchCursor) {
                return false;
            }
            MultiSelectPopupWindow.sTextView.invalidate();
            return true;
        }

        @Override // android.widget.MultiSelectPopupWindow.HandleView
        protected boolean calculateForSwitchingCursor() {
            boolean z = this.mbSwitchCursor;
            this.mbSwitchCursor = false;
            if (isHandleViewScreenOut()) {
                this.mbSwitchCursor = true;
            }
            if (z == this.mbSwitchCursor) {
                return false;
            }
            updateDrawable();
            this.mPositionX = (int) ((MultiSelectPopupWindow.sTextView.getLayout().getPrimaryHorizontal(getCurrentCursorOffset()) - 0.5f) - this.mHotspotX);
            this.mPositionX += MultiSelectPopupWindow.sTextView.viewportToContentHorizontalOffset();
            return true;
        }

        @Override // android.widget.MultiSelectPopupWindow.HandleView
        protected void updateDrawable() {
            int currentCursorOffset = getCurrentCursorOffset();
            Drawable drawable = this.mDrawable;
            boolean zIsRtlCharAt = MultiSelectPopupWindow.sTextView.getLayout().isRtlCharAt(currentCursorOffset);
            if (this.mbSwitchCursor) {
                zIsRtlCharAt = !zIsRtlCharAt;
            }
            this.mDrawable = zIsRtlCharAt ? this.mDrawableRtl : this.mDrawableLtr;
            this.mHotspotX = getHotspotX(this.mDrawable, zIsRtlCharAt);
            this.mHorizontalGravity = getHorizontalGravity(zIsRtlCharAt);
            if (drawable != this.mDrawable) {
                recalHandleView();
                invalidate();
            }
        }

        private boolean isHandleViewScreenOut() {
            return (((this.mPositionX + MultiSelectPopupWindow.this.getPositionListener().getPositionX()) + this.mHotspotX) + getHorizontalOffset()) - (this.mDrawableRtl.getIntrinsicWidth() / 2) < 0;
        }
    }

    private class SelectionEndHandleView extends HandleView {
        public SelectionEndHandleView(Drawable drawable, Drawable drawable2) {
            super(drawable, drawable2);
            this.mHandleType = 2;
        }

        @Override // android.widget.MultiSelectPopupWindow.HandleView
        protected int getHotspotX(Drawable drawable, boolean z) {
            if (z) {
                return (drawable.getIntrinsicWidth() * 3) / 4;
            }
            return drawable.getIntrinsicWidth() / 4;
        }

        @Override // android.widget.MultiSelectPopupWindow.HandleView
        public int getCurrentCursorOffset() {
            CharSequence textForMultiSelection = MultiSelectPopupWindow.sTextView.getTextForMultiSelection();
            if (textForMultiSelection == null) {
                Log.e(MultiSelectPopupWindow.TAG, "getTextFormultiSelection() text is null");
            }
            return MultiSelection.getSelectionEnd(textForMultiSelection);
        }

        @Override // android.widget.MultiSelectPopupWindow.HandleView
        public void updateSelection(int i) {
            CharSequence textForMultiSelection = MultiSelectPopupWindow.sTextView.getTextForMultiSelection();
            if (textForMultiSelection == null) {
                Log.e(MultiSelectPopupWindow.TAG, "getTextFormultiSelection() text is null");
                return;
            }
            MultiSelection.setSelection((Spannable) textForMultiSelection, MultiSelection.getSelectionStart(textForMultiSelection), i);
            updateDrawable();
            if (MultiSelectPopupWindow.sTextActionMode != null) {
                MultiSelectPopupWindow.sTextActionMode.invalidate();
            }
        }

        @Override // android.widget.MultiSelectPopupWindow.HandleView
        public void updatePosition(float f, float f2) {
            int offsetForPosition = MultiSelectPopupWindow.sTextView.getOffsetForPosition(f, f2);
            if (offsetForPosition == MultiSelection.getSelectionStart(MultiSelectPopupWindow.sTextView.getTextForMultiSelection())) {
                return;
            }
            if (offsetForPosition > this.mEndRange) {
                offsetForPosition = this.mEndRange;
            }
            positionAtCursorOffset(offsetForPosition, false, false);
        }

        @Override // android.widget.MultiSelectPopupWindow.HandleView
        protected void positionAtCursorOffset(int i, boolean z, boolean z2) {
            super.positionAtCursorOffset(i, z, z2);
            if (this.mIsDragging) {
                return;
            }
            calculateForSwitchingCursor();
            this.mPositionHasChanged = true;
            invalidate();
        }

        @Override // android.widget.MultiSelectPopupWindow.HandleView
        public boolean refreshForSwitchingCursor() {
            if (!this.mbSwitchCursor && (!isHandleViewScreenOut() || this.mbSwitchCursor)) {
                return false;
            }
            MultiSelectPopupWindow.sTextView.invalidate();
            return true;
        }

        @Override // android.widget.MultiSelectPopupWindow.HandleView
        protected boolean calculateForSwitchingCursor() {
            boolean z = this.mbSwitchCursor;
            this.mbSwitchCursor = false;
            if (isHandleViewScreenOut()) {
                this.mbSwitchCursor = true;
            }
            if (z == this.mbSwitchCursor) {
                return false;
            }
            updateDrawable();
            this.mPositionX = (int) ((MultiSelectPopupWindow.sTextView.getLayout().getPrimaryHorizontal(getCurrentCursorOffset()) - 0.5f) - this.mHotspotX);
            this.mPositionX += MultiSelectPopupWindow.sTextView.viewportToContentHorizontalOffset();
            return true;
        }

        @Override // android.widget.MultiSelectPopupWindow.HandleView
        protected void updateDrawable() {
            int currentCursorOffset = getCurrentCursorOffset();
            Drawable drawable = this.mDrawable;
            boolean zIsRtlCharAt = MultiSelectPopupWindow.sTextView.getLayout().isRtlCharAt(currentCursorOffset);
            if (this.mbSwitchCursor) {
                zIsRtlCharAt = !zIsRtlCharAt;
            }
            this.mDrawable = zIsRtlCharAt ? this.mDrawableRtl : this.mDrawableLtr;
            this.mHotspotX = getHotspotX(this.mDrawable, zIsRtlCharAt);
            this.mHorizontalGravity = getHorizontalGravity(zIsRtlCharAt);
            if (drawable != this.mDrawable) {
                recalHandleView();
                invalidate();
            }
        }

        private boolean isHandleViewScreenOut() {
            return (((this.mPositionX + MultiSelectPopupWindow.this.getPositionListener().getPositionX()) + this.mHotspotX) + getHorizontalOffset()) + (this.mDrawableRtl.getIntrinsicWidth() / 2) > this.mContext.getResources().getDisplayMetrics().widthPixels;
        }
    }
}
