package android.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.app.AppGlobals;
import android.app.PendingIntent;
import android.app.RemoteAction;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.UndoManager;
import android.content.UndoOperation;
import android.content.UndoOwner;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RecordingCanvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.RenderNode;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.LocaleList;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.ParcelableParcel;
import android.os.SemSystemProperties;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.DynamicLayout;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Layout;
import android.text.ParcelableSpan;
import android.text.Selection;
import android.text.SpanWatcher;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.StaticLayout;
import android.text.TextFlags;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.text.method.InsertModeTransformationMethod;
import android.text.method.KeyListener;
import android.text.method.MetaKeyKeyListener;
import android.text.method.MovementMethod;
import android.text.method.OffsetMapping;
import android.text.method.TransformationMethod;
import android.text.method.WordIterator;
import android.text.style.EasyEditSpan;
import android.text.style.SuggestionRangeSpan;
import android.text.style.SuggestionSpan;
import android.text.style.TextAppearanceSpan;
import android.text.style.URLSpan;
import android.util.ArraySet;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Pair;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.ContentInfo;
import android.view.ContextMenu;
import android.view.ContextThemeWrapper;
import android.view.DragAndDropPermissions;
import android.view.DragEvent;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SubMenu;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewRootImpl;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.LinearInterpolator;
import android.view.animation.PathInterpolator;
import android.view.inputmethod.CorrectionInfo;
import android.view.inputmethod.CursorAnchorInfo;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.SemInputMethodManagerUtils;
import android.view.textclassifier.TextClassification;
import android.view.textclassifier.TextClassificationManager;
import android.window.OnBackInvokedCallback;

import com.android.internal.R;
import com.android.internal.graphics.ColorUtils;
import com.android.internal.inputmethod.EditableInputConnection;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.nano.MetricsProto;
import com.android.internal.transition.EpicenterTranslateClipReveal;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.GrowingArrayUtils;
import com.android.internal.util.Preconditions;
import com.android.internal.view.FloatingActionMode;
import com.android.text.flags.Flags;

import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.desktopmode.SemDesktopModeState;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.rune.ViewRune;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/* loaded from: classes4.dex */
public class Editor {
    private static final int ACTION_MODE_MENU_ITEM_ORDER_ASSIST = 1;
    private static final int ACTION_MODE_MENU_ITEM_ORDER_AUTOFILL = 15;
    private static final int ACTION_MODE_MENU_ITEM_ORDER_COPY = 5;
    private static final int ACTION_MODE_MENU_ITEM_ORDER_CUT = 4;
    private static final int ACTION_MODE_MENU_ITEM_ORDER_MANAGE_APP = 101;
    private static final int ACTION_MODE_MENU_ITEM_ORDER_PASTE = 6;
    private static final int ACTION_MODE_MENU_ITEM_ORDER_PASTE_AS_PLAIN_TEXT = 7;
    private static final int ACTION_MODE_MENU_ITEM_ORDER_PROCESS_TEXT_INTENT_ACTIONS_START = 100;
    private static final int ACTION_MODE_MENU_ITEM_ORDER_REDO = 12;
    private static final int ACTION_MODE_MENU_ITEM_ORDER_REPLACE = 14;
    private static final int ACTION_MODE_MENU_ITEM_ORDER_SECONDARY_ASSIST_ACTIONS_START = 50;
    private static final int ACTION_MODE_MENU_ITEM_ORDER_SELECT_ALL = 9;
    private static final int ACTION_MODE_MENU_ITEM_ORDER_SHARE = 10;
    private static final int ACTION_MODE_MENU_ITEM_ORDER_SSS_TRANSLATE = 8;
    private static final int ACTION_MODE_MENU_ITEM_ORDER_TRANSLATE = 16;
    private static final int ACTION_MODE_MENU_ITEM_ORDER_UNDO = 11;
    private static final int ACTION_MODE_MENU_ITEM_ORDER_WEBSEARCH = 13;
    private static final int ACTION_MODE_MENU_ITEM_ORDER_WRITING_TOOLKIT = 0;
    static final int BLINK = 500;
    private static final int CONTEXT_MENU_GROUP_CLIPBOARD = 2;
    private static final int CONTEXT_MENU_GROUP_MISC = 3;
    private static final int CONTEXT_MENU_GROUP_UNDO_REDO = 1;
    private static final int CONTEXT_MENU_ITEM_ORDER_REPLACE = 11;
    private static final int CURSOR_START_FLOAT_DISTANCE_PX = 20;
    private static final boolean DEBUG_UNDO = false;
    private static final int DELAY_BEFORE_HANDLE_FADES_OUT = 4000;
    private static final int DRAG_SHADOW_MAX_TEXT_LENGTH = 20;
    static final int EXTRACT_NOTHING = -2;
    static final int EXTRACT_UNKNOWN = -1;
    private static final int FLAG_MISSPELLED_OR_GRAMMAR_ERROR = 10;
    private static final boolean FLAG_USE_MAGNIFIER = true;
    public static final int HANDLE_TYPE_SELECTION_END = 1;
    public static final int HANDLE_TYPE_SELECTION_START = 0;
    private static final int LINE_CHANGE_SLOP_MAX_DP = 45;
    private static final int LINE_CHANGE_SLOP_MIN_DP = 8;
    private static final int MAX_LINE_HEIGHT_FOR_MAGNIFIER = 32;
    private static final int MIN_LINE_HEIGHT_FOR_MAGNIFIER = 20;
    private static final int RECENT_CUT_COPY_DURATION_MS = 15000;
    private static final float SHADOW_VIEW_MAX_WIDTH = 0.75f;
    private static final float SHADOW_VIEW_MAX_WIDTH_TABLET = 0.55f;
    private static final int SHADOW_VIEW_WIDTH_RESTRICT_DP = 480;
    private static final String SWITCH_CONTROL_ENABLED = "universal_switch_enabled";
    private static final String TAG = "Editor";
    private static final String TAG_LAG = "PF_LAG";
    private static final int TW_MENU_ITEM_ORDER_CLIPBOARD = 19;
    private static final int TW_MENU_ITEM_ORDER_HBD_TRANSLATE = 17;
    private static final int TW_MENU_ITEM_ORDER_SCAN_TEXT = 18;
    private static final String UNDO_OWNER_TAG = "Editor";
    private static final int UNSET_LINE = -1;
    private static final int UNSET_X_VALUE = -1;
    private static final boolean mDisableDoubleTapTextSelection =
            SemCscFeature.getInstance()
                    .getBoolean("CscFeature_Framework_DisableDoubleTapTextSelection", false);
    private final AccessibilitySmartActions mA11ySmartActions;
    private boolean mBackCallbackRegistered;
    private Blink mBlink;
    private float mContextMenuAnchorX;
    private float mContextMenuAnchorY;
    private CorrectionHighlighter mCorrectionHighlighter;
    boolean mCreatedWithASelection;
    private float mCursorDragDirectionMinXYRatio;
    ActionMode.Callback mCustomInsertionActionModeCallback;
    ActionMode.Callback mCustomSelectionActionModeCallback;
    boolean mDiscardNextActionUp;
    private boolean mDrawCursorOnMagnifier;
    CharSequence mError;
    private ErrorPopup mErrorPopup;
    boolean mErrorWasChanged;
    private boolean mFlagCursorDragFromAnywhereEnabled;
    private boolean mFlagInsertionHandleGesturesEnabled;
    boolean mFrozenWithFocus;
    private final boolean mHapticTextHandleEnabled;
    boolean mIgnoreActionUpEvent;
    boolean mInBatchEditControllers;
    InputContentType mInputContentType;
    InputMethodState mInputMethodState;
    private InsertModeController mInsertModeController;
    private Runnable mInsertionActionModeRunnable;
    private boolean mInsertionControllerEnabled;
    InsertionPointCursorController mInsertionPointCursorController;
    boolean mIsBeingLongClicked;
    boolean mIsBeingLongClickedByAccessibility;
    boolean mIsSelectedByLongClick;
    private boolean mIsThemeDeviceDefault;
    KeyListener mKeyListener;
    private int mLastButtonState;
    private int mLineChangeSlopMax;
    private int mLineChangeSlopMin;
    private final float mLineSlopRatio;
    private MagnifierMotionAnimator mMagnifierAnimator;
    private int mMaxLineHeightForMagnifier;
    private int mMinLineHeightForMagnifier;
    private final boolean mNewMagnifierEnabled;
    private PositionListener mPositionListener;
    private boolean mPreserveSelection;
    final ProcessTextIntentActionsHandler mProcessTextIntentActionsHandler;
    private boolean mRenderCursorRegardlessTiming;
    private boolean mRequestingLinkActionMode;
    private boolean mRestartActionModeOnNextRefresh;
    boolean mSelectAllOnFocus;
    Drawable mSelectHandleCenter;
    Drawable mSelectHandleLeft;
    Drawable mSelectHandleRight;
    private SelectionActionModeHelper mSelectionActionModeHelper;
    private boolean mSelectionControllerEnabled;
    SelectionModifierCursorController mSelectionModifierCursorController;
    boolean mSelectionMoved;
    private long mShowCursor;
    private boolean mShowErrorAfterAttach;
    private Runnable mShowSuggestionRunnable;
    private SpanController mSpanController;
    SpellChecker mSpellChecker;
    SuggestionRangeSpan mSuggestionRangeSpan;
    private SuggestionsPopupWindow mSuggestionsPopupWindow;
    private Rect mTempRect;
    private ActionMode mTextActionMode;
    boolean mTextIsSelectable;
    private TextRenderNode[] mTextRenderNodes;
    private final TextView mTextView;
    boolean mTouchFocusSelected;
    private boolean mUpdateWordIteratorText;
    private boolean mUseNewContextMenu;
    private WordIterator mWordIterator;
    private WordIterator mWordIteratorWithText;
    private final TextViewOnReceiveContentListener mDefaultOnReceiveContentListener =
            new TextViewOnReceiveContentListener();
    private final UndoManager mUndoManager = new UndoManager();
    private UndoOwner mUndoOwner = this.mUndoManager.getOwner("Editor", this);
    final UndoInputFilter mUndoInputFilter = new UndoInputFilter(this);
    boolean mAllowUndo = true;
    private final MetricsLogger mMetricsLogger = new MetricsLogger();
    private final OnBackInvokedCallback mBackCallback =
            new OnBackInvokedCallback() { // from class:
                                          // android.widget.Editor$$ExternalSyntheticLambda0
                @Override // android.window.OnBackInvokedCallback
                public final void onBackInvoked() {
                    Editor.this.lambda$startActionModeInternal$0();
                }
            };
    private boolean mShowMagnifier = false;
    private final Runnable mUpdateMagnifierRunnable =
            new Runnable() { // from class: android.widget.Editor.1
                @Override // java.lang.Runnable
                public void run() {
                    Editor.this.mMagnifierAnimator.update();
                }
            };
    private final ViewTreeObserver.OnDrawListener mMagnifierOnDrawListener =
            new ViewTreeObserver.OnDrawListener() { // from class: android.widget.Editor.2
                @Override // android.view.ViewTreeObserver.OnDrawListener
                public void onDraw() {
                    if (Editor.this.mMagnifierAnimator != null) {
                        Editor.this.mTextView.post(Editor.this.mUpdateMagnifierRunnable);
                    }
                }
            };
    private boolean mHasPendingRestartInputForSetText = false;
    int mInputType = 0;
    boolean mCursorVisible = true;
    boolean mShowSoftInputOnFocus = true;
    Drawable mDrawableForCursor = null;
    private SemDesktopModeManager mDesktopModeManager = null;
    private boolean mUseCtxMenuInDesktopMode = true;
    private boolean mhadWindowFocus = false;
    private final EditorTouchState mTouchState = new EditorTouchState();
    private final CursorAnchorInfoNotifier mCursorAnchorInfoNotifier =
            new CursorAnchorInfoNotifier();
    private final Runnable mShowFloatingToolbar =
            new Runnable() { // from class: android.widget.Editor.3
                @Override // java.lang.Runnable
                public void run() {
                    if (Editor.this.mTextActionMode != null) {
                        Editor.this.mTextActionMode.hide(0L);
                    }
                }
            };
    boolean mIsInsertionActionModeStartPending = false;
    private final SuggestionHelper mSuggestionHelper = new SuggestionHelper();
    private float mInitialZoom = 1.0f;
    private Float SEP_VERSION = Float.valueOf(Float.parseFloat("16.0"));
    private boolean mWasBlinking = false;
    private boolean mWasSIPShowing = false;
    private boolean mToggleActionMode = false;
    private boolean mShowSoftInputOnFocusInternal = false;
    private final float mYVelocityThreshold = 0.5f;
    private boolean mIsMagnifierHideByVelocityTracker = false;
    private final MenuItem.OnMenuItemClickListener mOnContextMenuItemClickListener =
            new MenuItem.OnMenuItemClickListener() { // from class: android.widget.Editor.5
                @Override // android.view.MenuItem.OnMenuItemClickListener
                public boolean onMenuItemClick(MenuItem item) {
                    if (Editor.this.mProcessTextIntentActionsHandler.performMenuItemAction(item)) {
                        return true;
                    }
                    return Editor.this.mTextView.onTextContextMenuItem(item.getItemId());
                }
            };

    private interface CursorController extends ViewTreeObserver.OnTouchModeChangeListener {
        void hide();

        boolean isActive();

        boolean isCursorBeingModified();

        void onDetached();

        void show();
    }

    private interface EasyEditDeleteListener {
        void onDeleteClick(EasyEditSpan easyEditSpan);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface HandleType {}

    @Retention(RetentionPolicy.SOURCE)
    private @interface MagnifierHandleTrigger {
        public static final int INSERTION = 0;
        public static final int SELECTION_END = 2;
        public static final int SELECTION_START = 1;
    }

    @interface TextActionMode {
        public static final int INSERTION = 1;
        public static final int SELECTION = 0;
        public static final int TEXT_LINK = 2;
    }

    private interface TextViewPositionListener {
        void updatePosition(int i, int i2, boolean z, boolean z2);
    }

    private static class TextRenderNode {
        boolean isDirty = true;
        boolean needsToBeShifted = true;
        RenderNode renderNode;

        public TextRenderNode(String name) {
            this.renderNode = RenderNode.create(name, null);
        }

        boolean needsRecord() {
            return this.isDirty || !this.renderNode.hasDisplayList();
        }
    }

    public boolean editorShowSoftInput() {
        return this.mShowSoftInputOnFocus || this.mShowSoftInputOnFocusInternal;
    }

    private boolean softInputShown() {
        InputMethodManager imm = getInputMethodManager();
        return imm != null && imm.isInputMethodShown();
    }

    Editor(TextView textView) {
        boolean z;
        boolean z2;
        boolean z3;
        this.mIsThemeDeviceDefault = false;
        this.mTextView = textView;
        this.mTextView.setFilters(this.mTextView.getFilters());
        this.mProcessTextIntentActionsHandler = new ProcessTextIntentActionsHandler();
        this.mA11ySmartActions = new AccessibilitySmartActions(this.mTextView);
        this.mHapticTextHandleEnabled =
                this.mTextView
                        .getContext()
                        .getResources()
                        .getBoolean(R.bool.config_enableHapticTextHandle);
        if (AppGlobals.getIntCoreSetting(WidgetFlags.KEY_ENABLE_CURSOR_DRAG_FROM_ANYWHERE, 1)
                != 0) {
            z = true;
        } else {
            z = false;
        }
        this.mFlagCursorDragFromAnywhereEnabled = z;
        this.mCursorDragDirectionMinXYRatio =
                EditorTouchState.getXYRatio(
                        AppGlobals.getIntCoreSetting(
                                WidgetFlags.KEY_CURSOR_DRAG_MIN_ANGLE_FROM_VERTICAL, 45));
        if (AppGlobals.getIntCoreSetting(WidgetFlags.KEY_ENABLE_INSERTION_HANDLE_GESTURES, 0)
                != 0) {
            z2 = true;
        } else {
            z2 = false;
        }
        this.mFlagInsertionHandleGesturesEnabled = z2;
        if (AppGlobals.getIntCoreSetting(WidgetFlags.KEY_ENABLE_NEW_MAGNIFIER, 0) != 0) {
            z3 = true;
        } else {
            z3 = false;
        }
        this.mNewMagnifierEnabled = z3;
        this.mLineSlopRatio = AppGlobals.getFloatCoreSetting(WidgetFlags.KEY_LINE_SLOP_RATIO, 0.5f);
        this.mUseNewContextMenu =
                AppGlobals.getIntCoreSetting(TextFlags.KEY_ENABLE_NEW_CONTEXT_MENU, 0) != 0;
        this.mLineChangeSlopMax =
                (int)
                        TypedValue.applyDimension(
                                1,
                                45.0f,
                                this.mTextView.getContext().getResources().getDisplayMetrics());
        this.mLineChangeSlopMin =
                (int)
                        TypedValue.applyDimension(
                                1,
                                8.0f,
                                this.mTextView.getContext().getResources().getDisplayMetrics());
        this.mIsThemeDeviceDefault = this.mTextView.isThemeDeviceDefault();
    }

    public boolean getFlagCursorDragFromAnywhereEnabled() {
        return this.mFlagCursorDragFromAnywhereEnabled;
    }

    public void setFlagCursorDragFromAnywhereEnabled(boolean enabled) {
        this.mFlagCursorDragFromAnywhereEnabled = enabled;
    }

    public void setCursorDragMinAngleFromVertical(int degreesFromVertical) {
        this.mCursorDragDirectionMinXYRatio = EditorTouchState.getXYRatio(degreesFromVertical);
    }

    public boolean getFlagInsertionHandleGesturesEnabled() {
        return this.mFlagInsertionHandleGesturesEnabled;
    }

    public void setFlagInsertionHandleGesturesEnabled(boolean enabled) {
        this.mFlagInsertionHandleGesturesEnabled = enabled;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public MagnifierMotionAnimator getMagnifierAnimator() {
        Magnifier.Builder builder;
        if (this.mMagnifierAnimator == null) {
            if (this.mNewMagnifierEnabled) {
                builder = createBuilderWithInlineMagnifierDefaults();
            } else {
                builder = Magnifier.createBuilderWithOldMagnifierDefaults(this.mTextView);
            }
            this.mMagnifierAnimator = new MagnifierMotionAnimator(builder.build());
        }
        return this.mMagnifierAnimator;
    }

    private Magnifier.Builder createBuilderWithInlineMagnifierDefaults() {
        Magnifier.Builder params = new Magnifier.Builder(this.mTextView);
        float zoom = AppGlobals.getFloatCoreSetting(WidgetFlags.KEY_MAGNIFIER_ZOOM_FACTOR, 1.5f);
        float aspectRatio =
                AppGlobals.getFloatCoreSetting(WidgetFlags.KEY_MAGNIFIER_ASPECT_RATIO, 5.5f);
        if (zoom < 1.2f || zoom > 1.8f) {
            zoom = 1.5f;
        }
        if (aspectRatio < 3.0f || aspectRatio > 8.0f) {
            aspectRatio = 5.5f;
        }
        this.mInitialZoom = zoom;
        this.mMinLineHeightForMagnifier =
                (int)
                        TypedValue.applyDimension(
                                1,
                                20.0f,
                                this.mTextView.getContext().getResources().getDisplayMetrics());
        this.mMaxLineHeightForMagnifier =
                (int)
                        TypedValue.applyDimension(
                                1,
                                32.0f,
                                this.mTextView.getContext().getResources().getDisplayMetrics());
        Layout layout = this.mTextView.getLayout();
        int line = layout.getLineForOffset(this.mTextView.getSelectionStartTransformed());
        int sourceHeight = layout.getLineBottom(line, false) - layout.getLineTop(line);
        int height = (int) (sourceHeight * zoom);
        int width = (int) (Math.max(sourceHeight, this.mMinLineHeightForMagnifier) * aspectRatio);
        params.setFishEyeStyle()
                .setSize(width, height)
                .setSourceSize(width, sourceHeight)
                .setElevation(0.0f)
                .setInitialZoom(zoom)
                .setClippingEnabled(false);
        Context context = this.mTextView.getContext();
        TypedArray a =
                context.obtainStyledAttributes(
                        null, R.styleable.Magnifier, R.attr.magnifierStyle, 0);
        params.setDefaultSourceToMagnifierOffset(
                a.getDimensionPixelSize(3, 0), a.getDimensionPixelSize(4, 0));
        a.recycle();
        return params.setSourceBounds(1, 0, 1, 0);
    }

    ParcelableParcel saveInstanceState() {
        ParcelableParcel state = new ParcelableParcel(getClass().getClassLoader());
        Parcel parcel = state.getParcel();
        this.mUndoManager.saveInstanceState(parcel);
        this.mUndoInputFilter.saveInstanceState(parcel);
        return state;
    }

    void restoreInstanceState(ParcelableParcel state) {
        Parcel parcel = state.getParcel();
        this.mUndoManager.restoreInstanceState(parcel, state.getClassLoader());
        this.mUndoInputFilter.restoreInstanceState(parcel);
        this.mUndoOwner = this.mUndoManager.getOwner("Editor", this);
    }

    public TextViewOnReceiveContentListener getDefaultOnReceiveContentListener() {
        return this.mDefaultOnReceiveContentListener;
    }

    void forgetUndoRedo() {
        UndoOwner[] owners = {this.mUndoOwner};
        this.mUndoManager.forgetUndos(owners, -1);
        this.mUndoManager.forgetRedos(owners, -1);
    }

    boolean canUndo() {
        UndoOwner[] owners = {this.mUndoOwner};
        return this.mAllowUndo && this.mUndoManager.countUndos(owners) > 0;
    }

    boolean canRedo() {
        UndoOwner[] owners = {this.mUndoOwner};
        return this.mAllowUndo && this.mUndoManager.countRedos(owners) > 0;
    }

    void undo() {
        if (!this.mAllowUndo) {
            return;
        }
        UndoOwner[] owners = {this.mUndoOwner};
        this.mUndoManager.undo(owners, 1);
    }

    void redo() {
        if (!this.mAllowUndo) {
            return;
        }
        UndoOwner[] owners = {this.mUndoOwner};
        this.mUndoManager.redo(owners, 1);
    }

    void replace() {
        if (this.mSuggestionsPopupWindow == null) {
            this.mSuggestionsPopupWindow = new SuggestionsPopupWindow();
        }
        hideCursorAndSpanControllers();
        this.mSuggestionsPopupWindow.show();
        int middle = (this.mTextView.getSelectionStart() + this.mTextView.getSelectionEnd()) / 2;
        Selection.setSelection((Spannable) this.mTextView.getText(), middle);
    }

    void onAttachedToWindow() {
        if (this.mShowErrorAfterAttach) {
            showError();
            this.mShowErrorAfterAttach = false;
        }
        ViewTreeObserver observer = this.mTextView.getViewTreeObserver();
        if (observer.isAlive()) {
            if (this.mInsertionPointCursorController != null) {
                observer.addOnTouchModeChangeListener(this.mInsertionPointCursorController);
            }
            if (this.mSelectionModifierCursorController != null) {
                this.mSelectionModifierCursorController.resetTouchOffsets();
                observer.addOnTouchModeChangeListener(this.mSelectionModifierCursorController);
            }
            observer.addOnDrawListener(this.mMagnifierOnDrawListener);
        }
        updateSpellCheckSpans(0, this.mTextView.getText().length(), true);
        if (this.mTextView.hasSelection()) {
            refreshTextActionMode();
        }
        getPositionListener().addSubscriber(this.mCursorAnchorInfoNotifier, true);
        resumeBlink();
    }

    void onDetachedFromWindow() {
        getPositionListener().removeSubscriber(this.mCursorAnchorInfoNotifier);
        if (this.mError != null) {
            hideError();
        }
        suspendBlink();
        if (this.mInsertionPointCursorController != null) {
            this.mInsertionPointCursorController.onDetached();
        }
        if (this.mSelectionModifierCursorController != null) {
            this.mSelectionModifierCursorController.onDetached();
        }
        if (this.mShowSuggestionRunnable != null) {
            this.mTextView.removeCallbacks(this.mShowSuggestionRunnable);
        }
        if (this.mInsertionActionModeRunnable != null) {
            this.mTextView.removeCallbacks(this.mInsertionActionModeRunnable);
        }
        this.mTextView.removeCallbacks(this.mShowFloatingToolbar);
        discardTextDisplayLists();
        if (this.mSpellChecker != null) {
            this.mSpellChecker.closeSession();
            this.mSpellChecker = null;
        }
        ViewTreeObserver observer = this.mTextView.getViewTreeObserver();
        if (observer.isAlive()) {
            observer.removeOnDrawListener(this.mMagnifierOnDrawListener);
        }
        hideCursorAndSpanControllers();
        stopTextActionModeWithPreservingSelection();
        this.mDefaultOnReceiveContentListener.clearInputConnectionInfo();
        unregisterOnBackInvokedCallback();
    }

    private void unregisterOnBackInvokedCallback() {
        ViewRootImpl viewRootImpl;
        if (this.mBackCallbackRegistered
                && (viewRootImpl = getTextView().getViewRootImpl()) != null
                && viewRootImpl.getOnBackInvokedDispatcher().isOnBackInvokedCallbackEnabled()) {
            viewRootImpl
                    .getOnBackInvokedDispatcher()
                    .unregisterOnBackInvokedCallback(this.mBackCallback);
            this.mBackCallbackRegistered = false;
        }
    }

    private void registerOnBackInvokedCallback() {
        ViewRootImpl viewRootImpl;
        if (!this.mBackCallbackRegistered
                && (viewRootImpl = this.mTextView.getViewRootImpl()) != null
                && viewRootImpl.getOnBackInvokedDispatcher().isOnBackInvokedCallbackEnabled()) {
            viewRootImpl
                    .getOnBackInvokedDispatcher()
                    .registerOnBackInvokedCallback(0, this.mBackCallback);
            this.mBackCallbackRegistered = true;
        }
    }

    private void discardTextDisplayLists() {
        if (this.mTextRenderNodes != null) {
            for (int i = 0; i < this.mTextRenderNodes.length; i++) {
                RenderNode displayList =
                        this.mTextRenderNodes[i] != null
                                ? this.mTextRenderNodes[i].renderNode
                                : null;
                if (displayList != null && displayList.hasDisplayList()) {
                    displayList.discardDisplayList();
                }
            }
        }
    }

    private void showError() {
        if (this.mTextView.getWindowToken() == null) {
            this.mShowErrorAfterAttach = true;
            return;
        }
        if (this.mErrorPopup == null) {
            LayoutInflater inflater = LayoutInflater.from(this.mTextView.getContext());
            TextView err =
                    (TextView)
                            inflater.inflate(
                                    this.mIsThemeDeviceDefault
                                            ? R.layout.tw_textview_hint
                                            : R.layout.textview_hint,
                                    (ViewGroup) null);
            float scale = this.mTextView.getResources().getDisplayMetrics().density;
            this.mErrorPopup =
                    new ErrorPopup(
                            err, (int) ((200.0f * scale) + 0.5f), (int) ((50.0f * scale) + 0.5f));
            this.mErrorPopup.setFocusable(false);
            this.mErrorPopup.setInputMethodMode(1);
        }
        TextView tv = (TextView) this.mErrorPopup.getContentView();
        chooseSize(this.mErrorPopup, this.mError, tv);
        tv.lambda$setTextAsync$0(this.mError);
        this.mErrorPopup.showAsDropDown(this.mTextView, getErrorX(), getErrorY(), 51);
        this.mErrorPopup.fixDirection(this.mErrorPopup.isAboveAnchor());
    }

    public void setError(CharSequence error, Drawable icon) {
        this.mError = TextUtils.stringOrSpannedString(error);
        this.mErrorWasChanged = true;
        if (this.mError == null) {
            setErrorIcon(null);
            if (this.mErrorPopup != null) {
                if (this.mErrorPopup.isShowing()) {
                    this.mErrorPopup.dismiss();
                }
                this.mErrorPopup = null;
            }
            this.mShowErrorAfterAttach = false;
            return;
        }
        setErrorIcon(icon);
        if (this.mTextView.isFocused()) {
            showError();
        }
    }

    private void setErrorIcon(Drawable icon) {
        TextView.Drawables dr = this.mTextView.mDrawables;
        if (dr == null) {
            TextView textView = this.mTextView;
            TextView.Drawables drawables = new TextView.Drawables(this.mTextView.getContext());
            dr = drawables;
            textView.mDrawables = drawables;
        }
        dr.setErrorDrawable(icon, this.mTextView);
        this.mTextView.resetResolvedDrawables();
        this.mTextView.invalidate();
        this.mTextView.requestLayout();
    }

    private void hideError() {
        if (this.mErrorPopup != null && this.mErrorPopup.isShowing()) {
            this.mErrorPopup.dismiss();
        }
        this.mShowErrorAfterAttach = false;
    }

    private int getErrorX() {
        int offset;
        float scale = this.mTextView.getResources().getDisplayMetrics().density;
        TextView.Drawables dr = this.mTextView.mDrawables;
        int layoutDirection = this.mTextView.getLayoutDirection();
        switch (layoutDirection) {
            case 1:
                offset = dr != null ? dr.mDrawableSizeLeft : 0;
                int errorX =
                        this.mTextView.getPaddingLeft()
                                + ((offset / 2) - ((int) ((25.0f * scale) + 0.5f)));
                return errorX;
            default:
                offset = dr != null ? dr.mDrawableSizeRight : 0;
                int errorX2 =
                        ((this.mTextView.getWidth() - this.mErrorPopup.getWidth())
                                        - this.mTextView.getPaddingRight())
                                + ((-offset) / 2)
                                + ((int) ((25.0f * scale) + 0.5f));
                return errorX2;
        }
    }

    private int getErrorY() {
        int compoundPaddingTop = this.mTextView.getCompoundPaddingTop();
        int vspace =
                ((this.mTextView.getBottom() - this.mTextView.getTop())
                                - this.mTextView.getCompoundPaddingBottom())
                        - compoundPaddingTop;
        TextView.Drawables dr = this.mTextView.mDrawables;
        int layoutDirection = this.mTextView.getLayoutDirection();
        int height = 0;
        switch (layoutDirection) {
            case 1:
                if (dr != null) {
                    height = dr.mDrawableHeightLeft;
                    break;
                }
                break;
            default:
                if (dr != null) {
                    height = dr.mDrawableHeightRight;
                    break;
                }
                break;
        }
        int icontop = ((vspace - height) / 2) + compoundPaddingTop;
        float scale = this.mTextView.getResources().getDisplayMetrics().density;
        return ((icontop + height) - this.mTextView.getHeight()) - ((int) ((2.0f * scale) + 0.5f));
    }

    void createInputContentTypeIfNeeded() {
        if (this.mInputContentType == null) {
            this.mInputContentType = new InputContentType();
        }
    }

    void createInputMethodStateIfNeeded() {
        if (this.mInputMethodState == null) {
            this.mInputMethodState = new InputMethodState();
        }
    }

    private boolean isCursorVisible() {
        return this.mCursorVisible && this.mTextView.isTextEditable();
    }

    boolean shouldRenderCursor() {
        if (!isCursorVisible()) {
            return false;
        }
        if (this.mRenderCursorRegardlessTiming) {
            return true;
        }
        long showCursorDelta = SystemClock.uptimeMillis() - this.mShowCursor;
        return showCursorDelta % 1000 < 500;
    }

    void prepareCursorControllers() {
        boolean windowSupportsHandles = false;
        ViewGroup.LayoutParams params = this.mTextView.getRootView().getLayoutParams();
        if (params instanceof WindowManager.LayoutParams) {
            WindowManager.LayoutParams windowParams = (WindowManager.LayoutParams) params;
            windowSupportsHandles = windowParams.type < 1000 || windowParams.type > 1999;
        }
        boolean enabled = windowSupportsHandles && this.mTextView.getLayout() != null;
        this.mInsertionControllerEnabled =
                enabled && (this.mDrawCursorOnMagnifier || isCursorVisible());
        this.mSelectionControllerEnabled = enabled && this.mTextView.textCanBeSelected();
        if (!this.mInsertionControllerEnabled) {
            hideInsertionPointCursorController();
            if (this.mInsertionPointCursorController != null) {
                this.mInsertionPointCursorController.onDetached();
                this.mInsertionPointCursorController = null;
            }
        }
        if (!this.mSelectionControllerEnabled) {
            lambda$startActionModeInternal$0();
            if (this.mSelectionModifierCursorController != null) {
                this.mSelectionModifierCursorController.onDetached();
                this.mSelectionModifierCursorController = null;
            }
        }
        this.mToggleActionMode = false;
    }

    void hideInsertionPointCursorController() {
        if (this.mInsertionPointCursorController != null) {
            this.mInsertionPointCursorController.hide();
        }
    }

    void hideCursorAndSpanControllers() {
        hideCursorControllers();
        hideSpanControllers();
    }

    private void hideSpanControllers() {
        if (this.mSpanController != null) {
            this.mSpanController.hide();
        }
    }

    private void hideCursorControllers() {
        if (this.mSuggestionsPopupWindow != null
                && (this.mTextView.isInExtractedMode()
                        || !this.mSuggestionsPopupWindow.isShowingUp())) {
            this.mSuggestionsPopupWindow.hide();
        }
        hideInsertionPointCursorController();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateSpellCheckSpans(int start, int end, boolean createSpellChecker) {
        this.mTextView.removeAdjacentSuggestionSpans(start);
        this.mTextView.removeAdjacentSuggestionSpans(end);
        if (this.mTextView.isTextEditable()
                && this.mTextView.isSuggestionsEnabled()
                && !this.mTextView.isInExtractedMode()) {
            InputMethodManager imm = getInputMethodManager();
            if (imm != null && imm.isInputMethodSuppressingSpellChecker()) {
                return;
            }
            if (this.mSpellChecker == null && createSpellChecker) {
                this.mSpellChecker = new SpellChecker(this.mTextView);
            }
            if (this.mSpellChecker != null) {
                this.mSpellChecker.spellCheck(start, end);
            }
        }
    }

    void onScreenStateChanged(int screenState) {
        switch (screenState) {
            case 0:
                this.mhadWindowFocus = this.mTextView.hasWindowFocus();
                suspendBlink();
                break;
            case 1:
                if (this.mhadWindowFocus) {
                    this.mhadWindowFocus = false;
                    resumeBlink();
                    break;
                }
                break;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void suspendBlink() {
        if (this.mBlink != null) {
            this.mBlink.cancel();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resumeBlink() {
        if (this.mBlink != null) {
            this.mBlink.uncancel();
        }
        makeBlink();
    }

    void adjustInputType(
            boolean password,
            boolean passwordInputType,
            boolean webPasswordInputType,
            boolean numberPasswordInputType) {
        if ((this.mInputType & 15) == 1) {
            if (password || passwordInputType) {
                this.mInputType = (this.mInputType & (-4081)) | 128;
            }
            if (webPasswordInputType) {
                this.mInputType = (this.mInputType & (-4081)) | 224;
                return;
            }
            return;
        }
        if ((this.mInputType & 15) == 2 && numberPasswordInputType) {
            this.mInputType = (this.mInputType & (-4081)) | 16;
        }
    }

    private void chooseSize(PopupWindow pop, CharSequence text, TextView tv) {
        int wid = tv.getPaddingLeft() + tv.getPaddingRight();
        int ht = tv.getPaddingTop() + tv.getPaddingBottom();
        int defaultWidthInPixels =
                this.mTextView
                        .getResources()
                        .getDimensionPixelSize(R.dimen.textview_error_popup_default_width);
        StaticLayout l =
                StaticLayout.Builder.obtain(
                                text, 0, text.length(), tv.getPaint(), defaultWidthInPixels)
                        .setUseLineSpacingFromFallbacks(tv.isFallbackLineSpacingForStaticLayout())
                        .build();
        float max = 0.0f;
        for (int i = 0; i < l.getLineCount(); i++) {
            max = Math.max(max, l.getLineWidth(i));
        }
        pop.setWidth(((int) Math.ceil(max)) + wid);
        pop.setHeight(l.getHeight() + ht);
    }

    void setFrame() {
        if (this.mErrorPopup != null) {
            TextView tv = (TextView) this.mErrorPopup.getContentView();
            chooseSize(this.mErrorPopup, this.mError, tv);
            this.mErrorPopup.update(
                    this.mTextView,
                    getErrorX(),
                    getErrorY(),
                    this.mErrorPopup.getWidth(),
                    this.mErrorPopup.getHeight());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getWordStart(int offset) {
        int retOffset;
        int retOffset2 = getWordIteratorWithText().prevBoundary(offset);
        if (getWordIteratorWithText().isOnPunctuation(retOffset2)) {
            retOffset = getWordIteratorWithText().getPunctuationBeginning(offset);
        } else {
            retOffset = getWordIteratorWithText().getPrevWordBeginningOnTwoWordsBoundary(offset);
        }
        if (retOffset == -1) {
            return offset;
        }
        return retOffset;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getWordEnd(int offset) {
        int retOffset;
        int retOffset2 = getWordIteratorWithText().nextBoundary(offset);
        if (getWordIteratorWithText().isAfterPunctuation(retOffset2)) {
            retOffset = getWordIteratorWithText().getPunctuationEnd(offset);
        } else {
            retOffset = getWordIteratorWithText().getNextWordEndOnTwoWordBoundary(offset);
        }
        if (retOffset == -1) {
            return offset;
        }
        return retOffset;
    }

    private boolean needsToSelectAllToSelectWordOrParagraph() {
        if (this.mTextView.hasPasswordTransformationMethod()) {
            return true;
        }
        int inputType = this.mTextView.getInputType();
        int klass = inputType & 15;
        int variation = inputType & InputType.TYPE_MASK_VARIATION;
        return klass == 2
                || klass == 3
                || klass == 4
                || variation == 16
                || variation == 32
                || variation == 208
                || variation == 176;
    }

    boolean selectCurrentWord() {
        int selectionStart;
        int selectionEnd;
        if (!this.mTextView.canSelectText()) {
            return false;
        }
        if (needsToSelectAllToSelectWordOrParagraph()) {
            return this.mTextView.selectAllText();
        }
        long lastTouchOffsets = getLastTouchOffsets();
        int minOffset = TextUtils.unpackRangeStartFromLong(lastTouchOffsets);
        int maxOffset = TextUtils.unpackRangeEndFromLong(lastTouchOffsets);
        if (this.mTextView.getKeycodeDpadCenterStatus()) {
            int selectionStart2 = this.mTextView.getSelectionStart();
            maxOffset = selectionStart2;
            minOffset = selectionStart2;
        }
        if (minOffset < 0
                || minOffset > this.mTextView.getText().length()
                || maxOffset < 0
                || maxOffset > this.mTextView.getText().length()) {
            return false;
        }
        URLSpan[] urlSpans =
                (URLSpan[])
                        ((Spanned) this.mTextView.getText())
                                .getSpans(minOffset, maxOffset, URLSpan.class);
        if (urlSpans.length >= 1) {
            URLSpan urlSpan = urlSpans[0];
            selectionStart = ((Spanned) this.mTextView.getText()).getSpanStart(urlSpan);
            selectionEnd = ((Spanned) this.mTextView.getText()).getSpanEnd(urlSpan);
        } else {
            WordIterator wordIterator = getWordIterator();
            wordIterator.setCharSequence(this.mTextView.getText(), minOffset, maxOffset);
            selectionStart = wordIterator.getBeginning(minOffset);
            int selectionEnd2 = wordIterator.getEnd(maxOffset);
            if (selectionStart == -1 || selectionEnd2 == -1 || selectionStart == selectionEnd2) {
                long range = getCharClusterRange(minOffset);
                selectionStart = TextUtils.unpackRangeStartFromLong(range);
                selectionEnd = TextUtils.unpackRangeEndFromLong(range);
            } else {
                selectionEnd = selectionEnd2;
            }
        }
        Selection.setSelection((Spannable) this.mTextView.getText(), selectionStart, selectionEnd);
        return selectionEnd > selectionStart;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean selectCurrentParagraph() {
        if (!this.mTextView.canSelectText()) {
            return false;
        }
        if (needsToSelectAllToSelectWordOrParagraph()) {
            return this.mTextView.selectAllText();
        }
        long lastTouchOffsets = getLastTouchOffsets();
        int minLastTouchOffset = TextUtils.unpackRangeStartFromLong(lastTouchOffsets);
        int maxLastTouchOffset = TextUtils.unpackRangeEndFromLong(lastTouchOffsets);
        long paragraphsRange = getParagraphsRange(minLastTouchOffset, maxLastTouchOffset);
        int start = TextUtils.unpackRangeStartFromLong(paragraphsRange);
        int end = TextUtils.unpackRangeEndFromLong(paragraphsRange);
        if (start >= end) {
            return false;
        }
        Selection.setSelection((Spannable) this.mTextView.getText(), start, end);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public long getParagraphsRange(int startOffset, int endOffset) {
        int startOffsetTransformed = this.mTextView.originalToTransformed(startOffset, 1);
        int endOffsetTransformed = this.mTextView.originalToTransformed(endOffset, 1);
        Layout layout = this.mTextView.getLayout();
        if (layout == null) {
            return TextUtils.packRangeInLong(-1, -1);
        }
        CharSequence text = layout.getText();
        int minLine = layout.getLineForOffset(startOffsetTransformed);
        while (minLine > 0) {
            int prevLineEndOffset = layout.getLineEnd(minLine - 1);
            if (text.charAt(prevLineEndOffset - 1) == '\n') {
                break;
            }
            minLine--;
        }
        int maxLine = layout.getLineForOffset(endOffsetTransformed);
        while (maxLine < layout.getLineCount() - 1) {
            int lineEndOffset = layout.getLineEnd(maxLine);
            if (text.charAt(lineEndOffset - 1) == '\n') {
                break;
            }
            maxLine++;
        }
        int paragraphStart = this.mTextView.transformedToOriginal(layout.getLineStart(minLine), 1);
        int paragraphEnd = this.mTextView.transformedToOriginal(layout.getLineEnd(maxLine), 1);
        return TextUtils.packRangeInLong(paragraphStart, paragraphEnd);
    }

    void onLocaleChanged() {
        this.mWordIterator = null;
        this.mWordIteratorWithText = null;
    }

    public WordIterator getWordIterator() {
        if (this.mWordIterator == null) {
            this.mWordIterator = new WordIterator(this.mTextView.getTextServicesLocale());
        }
        return this.mWordIterator;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public WordIterator getWordIteratorWithText() {
        if (this.mWordIteratorWithText == null) {
            this.mWordIteratorWithText = new WordIterator(this.mTextView.getTextServicesLocale());
            this.mUpdateWordIteratorText = true;
        }
        if (this.mUpdateWordIteratorText) {
            CharSequence text = this.mTextView.getText();
            this.mWordIteratorWithText.setCharSequence(text, 0, text.length());
            this.mUpdateWordIteratorText = false;
        }
        return this.mWordIteratorWithText;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getNextCursorOffset(int offset, boolean findAfterGivenOffset) {
        int nextCursor;
        Layout layout = this.mTextView.getLayout();
        if (layout == null) {
            return offset;
        }
        int offsetTransformed = this.mTextView.originalToTransformed(offset, 1);
        if (findAfterGivenOffset == layout.isRtlCharAt(offsetTransformed)) {
            nextCursor = layout.getOffsetToLeftOf(offsetTransformed);
        } else {
            nextCursor = layout.getOffsetToRightOf(offsetTransformed);
        }
        return this.mTextView.transformedToOriginal(nextCursor, 1);
    }

    private long getCharClusterRange(int offset) {
        int textLength = this.mTextView.getText().length();
        if (offset < textLength) {
            int clusterEndOffset = getNextCursorOffset(offset, true);
            return TextUtils.packRangeInLong(
                    getNextCursorOffset(clusterEndOffset, false), clusterEndOffset);
        }
        if (offset - 1 >= 0) {
            int clusterStartOffset = getNextCursorOffset(offset, false);
            return TextUtils.packRangeInLong(
                    clusterStartOffset, getNextCursorOffset(clusterStartOffset, true));
        }
        return TextUtils.packRangeInLong(offset, offset);
    }

    private boolean touchPositionIsInSelection() {
        int selectionStart = this.mTextView.getSelectionStart();
        int selectionEnd = this.mTextView.getSelectionEnd();
        if (selectionStart == selectionEnd) {
            return false;
        }
        if (selectionStart > selectionEnd) {
            selectionStart = selectionEnd;
            selectionEnd = selectionStart;
            Selection.setSelection(
                    (Spannable) this.mTextView.getText(), selectionStart, selectionEnd);
        }
        SelectionModifierCursorController selectionController = getSelectionController();
        int minOffset = selectionController.getMinTouchOffset();
        int maxOffset = selectionController.getMaxTouchOffset();
        return minOffset >= selectionStart && maxOffset < selectionEnd;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public PositionListener getPositionListener() {
        if (this.mPositionListener == null) {
            this.mPositionListener = new PositionListener();
        }
        return this.mPositionListener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isOffsetVisible(int offset) {
        Layout layout = this.mTextView.getLayout();
        if (layout == null) {
            return false;
        }
        int offsetTransformed = this.mTextView.originalToTransformed(offset, 1);
        int line = layout.getLineForOffset(offsetTransformed);
        int lineBottom = layout.getLineBottom(line);
        int primaryHorizontal = (int) layout.getPrimaryHorizontal(offsetTransformed);
        return this.mTextView.isPositionVisible(
                this.mTextView.viewportToContentHorizontalOffset() + primaryHorizontal,
                this.mTextView.viewportToContentVerticalOffset() + lineBottom);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isPositionOnText(float x, float y) {
        Layout layout = this.mTextView.getLayout();
        if (layout == null) {
            return false;
        }
        if (this.mTextView.getKeycodeDpadCenterStatus()) {
            int offset = this.mTextView.getSelectionStart();
            return offset != this.mTextView.getText().length() || this.mTextView.hasSelection();
        }
        int line = this.mTextView.getLineAtCoordinate(y);
        float x2 = this.mTextView.convertToLocalHorizontalCoordinate(x);
        return x2 >= layout.getLineLeft(line) && x2 <= layout.getLineRight(line);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startDragAndDrop() {
        getSelectionActionModeHelper().onSelectionDrag();
        if (this.mTextView.isInExtractedMode()) {
            return;
        }
        int start = this.mTextView.getSelectionStart();
        int end = this.mTextView.getSelectionEnd();
        CharSequence selectedText = this.mTextView.getTransformedText(start, end);
        ClipData data = ClipData.newPlainText(null, selectedText);
        DragLocalState localState = new DragLocalState(this.mTextView, start, end);
        AudioManager audioManager =
                (AudioManager) this.mTextView.getContext().getSystemService("audio");
        if (audioManager != null) {
            audioManager.playSoundEffect(106);
        } else {
            Log.w("Editor", "performSoundEffect: Couldn't get audio manager");
        }
        this.mTextView.startDragAndDrop(data, getTextThumbnailBuilder(start, end), localState, 768);
        lambda$startActionModeInternal$0();
        if (hasSelectionController()) {
            getSelectionController().resetTouchOffsets();
        }
    }

    public boolean performLongClick(boolean handled) {
        if (this.mIsBeingLongClickedByAccessibility) {
            if (!handled) {
                toggleInsertionActionMode();
            }
            return true;
        }
        if (!handled
                && !isPositionOnText(
                        this.mTouchState.getLastDownX(), this.mTouchState.getLastDownY())
                && !this.mTouchState.isOnHandle()
                && this.mInsertionControllerEnabled) {
            int offset =
                    this.mTextView.getOffsetForPosition(
                            this.mTouchState.getLastDownX(), this.mTouchState.getLastDownY());
            if (this.mTextView.getKeycodeDpadCenterStatus()) {
                offset = this.mTextView.getSelectionStart();
                this.mToggleActionMode = true;
                startInsertionActionMode();
            }
            Selection.setSelection((Spannable) this.mTextView.getText(), offset);
            getInsertionController().show();
            this.mIsInsertionActionModeStartPending = true;
            handled = true;
            MetricsLogger.action(
                    this.mTextView.getContext(), MetricsProto.MetricsEvent.TEXT_LONGPRESS, 0);
        }
        if (!handled && this.mTextActionMode != null) {
            if (touchPositionIsInSelection()) {
                startDragAndDrop();
                MetricsLogger.action(
                        this.mTextView.getContext(), MetricsProto.MetricsEvent.TEXT_LONGPRESS, 2);
            } else {
                lambda$startActionModeInternal$0();
                selectCurrentWordAndStartDrag();
                if (!this.mTextView.isDesktopMode() && this.mTextView.hasSelection()) {
                    this.mShowMagnifier = true;
                }
                MetricsLogger.action(
                        this.mTextView.getContext(), MetricsProto.MetricsEvent.TEXT_LONGPRESS, 1);
            }
            handled = true;
        }
        if (!handled) {
            handled = selectCurrentWordAndStartDrag();
            if (this.mTextView.isInTouchMode()) {
                if (!this.mTextView.isDesktopMode() && this.mTextView.hasSelection()) {
                    this.mShowMagnifier = true;
                }
            } else if (this.mTextView.getKeycodeDpadCenterStatus()) {
                startSelectionActionModeAsync(false);
            }
            if (handled) {
                MetricsLogger.action(
                        this.mTextView.getContext(), MetricsProto.MetricsEvent.TEXT_LONGPRESS, 1);
            }
        }
        return handled;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void toggleInsertionActionMode() {
        if (this.mTextActionMode != null) {
            lambda$startActionModeInternal$0();
        } else {
            startInsertionActionMode();
        }
    }

    float getLastUpPositionX() {
        return this.mTouchState.getLastUpX();
    }

    float getLastUpPositionY() {
        return this.mTouchState.getLastUpY();
    }

    private long getLastTouchOffsets() {
        SelectionModifierCursorController selectionController = getSelectionController();
        int minOffset = selectionController.getMinTouchOffset();
        int maxOffset = selectionController.getMaxTouchOffset();
        return TextUtils.packRangeInLong(minOffset, maxOffset);
    }

    void onFocusChanged(boolean focused, int direction) {
        this.mShowCursor = SystemClock.uptimeMillis();
        ensureEndedBatchEdit();
        if (focused) {
            int selStart = this.mTextView.getSelectionStart();
            int selEnd = this.mTextView.getSelectionEnd();
            boolean isFocusHighlighted =
                    this.mSelectAllOnFocus
                            && selStart == 0
                            && selEnd == this.mTextView.getText().length();
            this.mCreatedWithASelection =
                    this.mFrozenWithFocus && this.mTextView.hasSelection() && !isFocusHighlighted;
            if (!this.mFrozenWithFocus || selStart < 0 || selEnd < 0) {
                int lastTapPosition = getLastTapPosition();
                if (lastTapPosition >= 0) {
                    Selection.setSelection((Spannable) this.mTextView.getText(), lastTapPosition);
                }
                MovementMethod mMovement = this.mTextView.getMovementMethod();
                if (mMovement != null) {
                    mMovement.onTakeFocus(
                            this.mTextView, (Spannable) this.mTextView.getText(), direction);
                }
                if ((this.mTextView.isInExtractedMode() || this.mSelectionMoved)
                        && selStart >= 0
                        && selEnd >= 0) {
                    Selection.setSelection((Spannable) this.mTextView.getText(), selStart, selEnd);
                }
                if (this.mSelectAllOnFocus) {
                    this.mTextView.selectAllText();
                }
                this.mTouchFocusSelected = true;
            }
            this.mFrozenWithFocus = false;
            this.mSelectionMoved = false;
            if (this.mError != null) {
                showError();
            }
            makeBlink();
            return;
        }
        if (this.mError != null) {
            hideError();
        }
        this.mTextView.onEndBatchEdit();
        if (this.mTextView.isInExtractedMode()) {
            hideCursorAndSpanControllers();
            stopTextActionModeWithPreservingSelection();
        } else {
            hideCursorAndSpanControllers();
            if (this.mTextView.isTemporarilyDetached()) {
                stopTextActionModeWithPreservingSelection();
            } else {
                lambda$startActionModeInternal$0();
            }
            downgradeEasyCorrectionSpans();
        }
        if (this.mSelectionModifierCursorController != null) {
            this.mSelectionModifierCursorController.resetTouchOffsets();
        }
        if (this.mInsertModeController != null) {
            this.mInsertModeController.exitInsertMode();
        }
        ensureNoSelectionIfNonSelectable();
    }

    private void ensureNoSelectionIfNonSelectable() {
        if (!this.mTextView.textCanBeSelected() && this.mTextView.hasSelection()) {
            Selection.setSelection(
                    (Spannable) this.mTextView.getText(),
                    this.mTextView.length(),
                    this.mTextView.length());
        }
    }

    private void downgradeEasyCorrectionSpans() {
        CharSequence text = this.mTextView.getText();
        if (text instanceof Spannable) {
            Spannable spannable = (Spannable) text;
            SuggestionSpan[] suggestionSpans =
                    (SuggestionSpan[])
                            spannable.getSpans(0, spannable.length(), SuggestionSpan.class);
            for (int i = 0; i < suggestionSpans.length; i++) {
                int flags = suggestionSpans[i].getFlags();
                if ((flags & 1) != 0 && (flags & 10) == 0) {
                    suggestionSpans[i].setFlags(flags & (-2));
                }
            }
        }
    }

    void sendOnTextChanged(int start, int before, int after) {
        getSelectionActionModeHelper().onTextChanged(start, start + before);
        updateSpellCheckSpans(start, start + after, false);
        this.mUpdateWordIteratorText = true;
        hideCursorControllers();
        if (this.mSelectionModifierCursorController != null) {
            this.mSelectionModifierCursorController.resetTouchOffsets();
        }
        lambda$startActionModeInternal$0();
    }

    private int getLastTapPosition() {
        int lastTapPosition;
        if (this.mSelectionModifierCursorController != null
                && (lastTapPosition = this.mSelectionModifierCursorController.getMinTouchOffset())
                        >= 0) {
            if (lastTapPosition > this.mTextView.getText().length()) {
                return this.mTextView.getText().length();
            }
            return lastTapPosition;
        }
        return -1;
    }

    void onWindowFocusChanged(boolean hasWindowFocus) {
        if (hasWindowFocus) {
            resumeBlink();
            if (this.mTextView.hasSelection() && !extractedTextModeWillBeStarted()) {
                refreshTextActionMode();
                return;
            }
            return;
        }
        suspendBlink();
        if (this.mInputContentType != null) {
            this.mInputContentType.enterDown = false;
        }
        hideCursorAndSpanControllers();
        stopTextActionModeWithPreservingSelection();
        if (this.mSuggestionsPopupWindow != null) {
            this.mSuggestionsPopupWindow.onParentLostFocus();
        }
        ensureEndedBatchEdit();
        ensureNoSelectionIfNonSelectable();
    }

    private boolean shouldFilterOutTouchEvent(MotionEvent event) {
        if (!event.isFromSource(8194) || event.getToolType(0) == 1 || event.getToolType(0) == 2) {
            return false;
        }
        boolean primaryButtonStateChanged =
                ((this.mLastButtonState ^ event.getButtonState()) & 1) != 0;
        int action = event.getActionMasked();
        if ((action == 0 || action == 1) && !primaryButtonStateChanged) {
            return true;
        }
        return action == 2 && !event.isButtonPressed(1);
    }

    public void onTouchEvent(MotionEvent event) {
        boolean filterOutEvent = shouldFilterOutTouchEvent(event);
        this.mLastButtonState = event.getButtonState();
        if (filterOutEvent) {
            if (event.getActionMasked() == 1) {
                this.mDiscardNextActionUp = true;
                return;
            }
            return;
        }
        ViewConfiguration viewConfiguration = ViewConfiguration.get(this.mTextView.getContext());
        this.mTouchState.update(event, viewConfiguration);
        updateFloatingToolbarVisibility(event);
        if (hasInsertionController()) {
            getInsertionController().onTouchEvent(event);
        }
        if (hasSelectionController()) {
            getSelectionController().onTouchEvent(event);
        }
        if (this.mShowSuggestionRunnable != null) {
            this.mTextView.removeCallbacks(this.mShowSuggestionRunnable);
            this.mShowSuggestionRunnable = null;
        }
        if (event.getActionMasked() == 1
                || event.getActionMasked() == 3
                || event.getActionMasked() == 6) {
            this.mIsSelectedByLongClick = false;
            this.mShowMagnifier = false;
            dismissMagnifierForDrag();
        }
        if (event.getActionMasked() == 0) {
            this.mTouchFocusSelected = false;
            this.mIgnoreActionUpEvent = false;
            this.mWasBlinking = shouldBlink() && this.mTextView.hasCallbacks(this.mBlink);
            this.mWasSIPShowing = softInputShown();
        }
        if (event.getActionMasked() == 0) {
            setUseCtxMenuInDesktopMode(event.isFromSource(8194));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateFloatingToolbarVisibility(MotionEvent event) {
        if (this.mTextActionMode != null) {
            switch (event.getActionMasked()) {
                case 1:
                case 3:
                    showFloatingToolbar();
                    break;
                case 2:
                    hideFloatingToolbar(-1);
                    break;
            }
        }
    }

    void hideFloatingToolbar(int duration) {
        if (this.mTextActionMode != null) {
            this.mTextView.removeCallbacks(this.mShowFloatingToolbar);
            this.mTextActionMode.hide(duration);
        }
    }

    private void showFloatingToolbar() {
        if (this.mTextActionMode != null && this.mTextView.showUIForTouchScreen()) {
            int delay = ViewConfiguration.getDoubleTapTimeout();
            this.mTextView.postDelayed(this.mShowFloatingToolbar, delay);
            invalidateActionModeAsync();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public InputMethodManager getInputMethodManager() {
        return (InputMethodManager)
                this.mTextView.getContext().getSystemService(InputMethodManager.class);
    }

    public void beginBatchEdit() {
        this.mInBatchEditControllers = true;
        InputMethodState ims = this.mInputMethodState;
        if (ims != null) {
            int nesting = ims.mBatchEditNesting + 1;
            ims.mBatchEditNesting = nesting;
            if (nesting == 1) {
                ims.mCursorChanged = false;
                ims.mChangedDelta = 0;
                if (ims.mContentChanged) {
                    ims.mChangedStart = 0;
                    ims.mChangedEnd = this.mTextView.getText().length();
                } else {
                    ims.mChangedStart = -1;
                    ims.mChangedEnd = -1;
                    ims.mContentChanged = false;
                }
                this.mUndoInputFilter.beginBatchEdit();
                this.mTextView.onBeginBatchEdit();
            }
        }
    }

    public void endBatchEdit() {
        this.mInBatchEditControllers = false;
        InputMethodState ims = this.mInputMethodState;
        if (ims != null) {
            int nesting = ims.mBatchEditNesting - 1;
            ims.mBatchEditNesting = nesting;
            if (nesting == 0) {
                finishBatchEdit(ims);
            }
        }
    }

    void ensureEndedBatchEdit() {
        InputMethodState ims = this.mInputMethodState;
        if (ims != null && ims.mBatchEditNesting != 0) {
            ims.mBatchEditNesting = 0;
            finishBatchEdit(ims);
        }
    }

    void finishBatchEdit(InputMethodState ims) {
        this.mTextView.onEndBatchEdit();
        this.mUndoInputFilter.endBatchEdit();
        if (ims.mContentChanged || ims.mSelectionModeChanged) {
            this.mTextView.updateAfterEdit();
            reportExtractedText();
        } else if (ims.mCursorChanged) {
            this.mTextView.invalidateCursor();
        }
        sendUpdateSelection();
        if (this.mTextActionMode != null) {
            CursorController cursorController =
                    this.mTextView.hasSelection()
                            ? getSelectionController()
                            : getInsertionController();
            if (cursorController != null
                    && !cursorController.isActive()
                    && !cursorController.isCursorBeingModified()
                    && this.mTextView.showUIForTouchScreen()) {
                cursorController.show();
            }
        }
    }

    void scheduleRestartInputForSetText() {
        this.mHasPendingRestartInputForSetText = true;
    }

    void maybeFireScheduledRestartInputForSetText() {
        if (this.mHasPendingRestartInputForSetText) {
            InputMethodManager imm = getInputMethodManager();
            if (imm != null) {
                imm.invalidateInput(this.mTextView);
            }
            this.mHasPendingRestartInputForSetText = false;
        }
    }

    boolean extractText(ExtractedTextRequest request, ExtractedText outText) {
        return extractTextInternal(request, -1, -1, -1, outText);
    }

    private boolean extractTextInternal(
            ExtractedTextRequest request,
            int partialStartOffset,
            int partialEndOffset,
            int delta,
            ExtractedText outText) {
        CharSequence content;
        int partialEndOffset2;
        if (request == null || outText == null || (content = this.mTextView.getText()) == null) {
            return false;
        }
        if (partialStartOffset != -2) {
            int N = content.length();
            if (partialStartOffset < 0) {
                outText.partialEndOffset = -1;
                outText.partialStartOffset = -1;
                partialStartOffset = 0;
                partialEndOffset2 = N;
            } else {
                partialEndOffset2 = partialEndOffset + delta;
                if (content instanceof Spanned) {
                    Spanned spanned = (Spanned) content;
                    Object[] spans =
                            spanned.getSpans(
                                    partialStartOffset, partialEndOffset2, ParcelableSpan.class);
                    int i = spans.length;
                    while (i > 0) {
                        i--;
                        int j = spanned.getSpanStart(spans[i]);
                        if (j < partialStartOffset) {
                            partialStartOffset = j;
                        }
                        int j2 = spanned.getSpanEnd(spans[i]);
                        if (j2 > partialEndOffset2) {
                            partialEndOffset2 = j2;
                        }
                    }
                }
                outText.partialStartOffset = partialStartOffset;
                outText.partialEndOffset = partialEndOffset2 - delta;
                if (partialStartOffset > N) {
                    partialStartOffset = N;
                } else if (partialStartOffset < 0) {
                    partialStartOffset = 0;
                }
                if (partialEndOffset2 > N) {
                    partialEndOffset2 = N;
                } else if (partialEndOffset2 < 0) {
                    partialEndOffset2 = 0;
                }
            }
            if ((request.flags & 1) != 0) {
                outText.text = content.subSequence(partialStartOffset, partialEndOffset2);
            } else {
                outText.text = TextUtils.substring(content, partialStartOffset, partialEndOffset2);
            }
        } else {
            outText.partialStartOffset = 0;
            outText.partialEndOffset = 0;
            outText.text = "";
        }
        outText.flags = 0;
        if (MetaKeyKeyListener.getMetaState(content, 2048) != 0) {
            outText.flags |= 2;
        }
        if (this.mTextView.isSingleLine()) {
            outText.flags |= 1;
        }
        outText.startOffset = 0;
        outText.selectionStart = this.mTextView.getSelectionStart();
        outText.selectionEnd = this.mTextView.getSelectionEnd();
        outText.hint = this.mTextView.getHint();
        return true;
    }

    boolean reportExtractedText() {
        InputMethodManager imm;
        InputMethodState ims = this.mInputMethodState;
        if (ims == null) {
            return false;
        }
        boolean wasContentChanged = ims.mContentChanged;
        if (!wasContentChanged && !ims.mSelectionModeChanged) {
            return false;
        }
        ims.mContentChanged = false;
        ims.mSelectionModeChanged = false;
        ExtractedTextRequest req = ims.mExtractedTextRequest;
        if (req == null || (imm = getInputMethodManager()) == null) {
            return false;
        }
        if (ims.mChangedStart < 0 && !wasContentChanged) {
            ims.mChangedStart = -2;
        }
        if (!extractTextInternal(
                req, ims.mChangedStart, ims.mChangedEnd, ims.mChangedDelta, ims.mExtractedText)) {
            return false;
        }
        imm.updateExtractedText(this.mTextView, req.token, ims.mExtractedText);
        ims.mChangedStart = -1;
        ims.mChangedEnd = -1;
        ims.mChangedDelta = 0;
        ims.mContentChanged = false;
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendUpdateSelection() {
        InputMethodManager imm;
        int candStart;
        int candEnd;
        if (this.mInputMethodState != null
                && this.mInputMethodState.mBatchEditNesting <= 0
                && !this.mHasPendingRestartInputForSetText
                && (imm = getInputMethodManager()) != null) {
            int selectionStart = this.mTextView.getSelectionStart();
            int selectionEnd = this.mTextView.getSelectionEnd();
            if (!(this.mTextView.getText() instanceof Spannable)) {
                candStart = -1;
                candEnd = -1;
            } else {
                Spannable sp = (Spannable) this.mTextView.getText();
                int candStart2 = EditableInputConnection.getComposingSpanStart(sp);
                int candEnd2 = EditableInputConnection.getComposingSpanEnd(sp);
                candStart = candStart2;
                candEnd = candEnd2;
            }
            imm.updateSelection(this.mTextView, selectionStart, selectionEnd, candStart, candEnd);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:46:0x00bc  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00c3  */
    /* JADX WARN: Removed duplicated region for block: B:51:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    void onDraw(
            android.graphics.Canvas r18,
            android.text.Layout r19,
            java.util.List<android.graphics.Path> r20,
            java.util.List<android.graphics.Paint> r21,
            android.graphics.Path r22,
            android.graphics.Paint r23,
            int r24) {
        /*
            r17 = this;
            r9 = r17
            r10 = r18
            r11 = r24
            android.widget.TextView r0 = r9.mTextView
            int r12 = r0.getSelectionStart()
            android.widget.TextView r0 = r9.mTextView
            int r13 = r0.getSelectionEnd()
            android.widget.Editor$InputMethodState r14 = r9.mInputMethodState
            if (r14 == 0) goto L33
            int r0 = r14.mBatchEditNesting
            if (r0 != 0) goto L33
            boolean r0 = r14.mContentChanged
            if (r0 != 0) goto L22
            boolean r0 = r14.mSelectionModeChanged
            if (r0 == 0) goto L33
        L22:
            android.view.inputmethod.InputMethodManager r0 = r17.getInputMethodManager()
            if (r0 == 0) goto L33
            android.widget.TextView r1 = r9.mTextView
            boolean r1 = r0.hasActiveInputConnection(r1)
            if (r1 == 0) goto L33
            r17.reportExtractedText()
        L33:
            boolean r0 = com.android.graphics.hwui.flags.Flags.highContrastTextSmallTextRect()
            if (r0 == 0) goto L41
            boolean r0 = r18.isHighContrastTextEnabled()
            if (r0 == 0) goto L41
            r0 = 1
            goto L42
        L41:
            r0 = 0
        L42:
            r15 = r0
            if (r15 == 0) goto L59
            r0 = r17
            r1 = r18
            r2 = r19
            r3 = r20
            r4 = r21
            r5 = r22
            r6 = r23
            r7 = r24
            r8 = r15
            r0.drawLayout(r1, r2, r3, r4, r5, r6, r7, r8)
        L59:
            android.widget.Editor$CorrectionHighlighter r0 = r9.mCorrectionHighlighter
            if (r0 == 0) goto L62
            android.widget.Editor$CorrectionHighlighter r0 = r9.mCorrectionHighlighter
            r0.draw(r10, r11)
        L62:
            if (r22 == 0) goto L9f
            if (r12 != r13) goto L9f
            android.graphics.drawable.Drawable r0 = r9.mDrawableForCursor
            if (r0 == 0) goto L9f
            android.widget.TextView r0 = r9.mTextView
            boolean r0 = r0.hasGesturePreviewHighlight()
            if (r0 != 0) goto L9f
            r0 = 0
            if (r12 != 0) goto L9a
            android.widget.TextView r1 = r9.mTextView
            boolean r1 = r1.isHighContrastTextEnabled()
            if (r1 == 0) goto L9a
            android.widget.TextView r1 = r9.mTextView
            android.text.TextPaint r1 = r1.getPaint()
            float r1 = r1.getHCTStrokeWidth()
            r2 = 1073741824(0x40000000, float:2.0)
            float r1 = r1 / r2
            double r1 = (double) r1
            double r1 = java.lang.Math.floor(r1)
            int r0 = (int) r1
            android.widget.TextView r1 = r9.mTextView
            int r1 = r1.getLayoutDirection()
            if (r1 != 0) goto L9a
            int r0 = r0 * (-1)
        L9a:
            r9.drawCursor(r10, r0, r11)
            r1 = 0
            goto La1
        L9f:
            r1 = r22
        La1:
            android.widget.SelectionActionModeHelper r0 = r9.mSelectionActionModeHelper
            if (r0 == 0) goto Lb6
            android.widget.SelectionActionModeHelper r0 = r9.mSelectionActionModeHelper
            r0.onDraw(r10)
            android.widget.SelectionActionModeHelper r0 = r9.mSelectionActionModeHelper
            boolean r0 = r0.isDrawingHighlight()
            if (r0 == 0) goto Lb6
            r1 = 0
            r16 = r1
            goto Lb8
        Lb6:
            r16 = r1
        Lb8:
            android.widget.Editor$InsertModeController r0 = r9.mInsertModeController
            if (r0 == 0) goto Lc1
            android.widget.Editor$InsertModeController r0 = r9.mInsertModeController
            r0.onDraw(r10)
        Lc1:
            if (r15 != 0) goto Ld7
            r0 = r17
            r1 = r18
            r2 = r19
            r3 = r20
            r4 = r21
            r5 = r16
            r6 = r23
            r7 = r24
            r8 = r15
            r0.drawLayout(r1, r2, r3, r4, r5, r6, r7, r8)
        Ld7:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: android.widget.Editor.onDraw(android.graphics.Canvas,"
                    + " android.text.Layout, java.util.List, java.util.List, android.graphics.Path,"
                    + " android.graphics.Paint, int):void");
    }

    private void drawLayout(
            Canvas canvas,
            Layout layout,
            List<Path> highlightPaths,
            List<Paint> highlightPaints,
            Path selectionHighlight,
            Paint selectionHighlightPaint,
            int cursorOffsetVertical,
            boolean shouldDrawHighlightsOnTop) {
        if (this.mTextView.canHaveDisplayList() && canvas.isHardwareAccelerated()) {
            drawHardwareAccelerated(
                    canvas,
                    layout,
                    highlightPaths,
                    highlightPaints,
                    selectionHighlight,
                    selectionHighlightPaint,
                    cursorOffsetVertical,
                    shouldDrawHighlightsOnTop);
        } else {
            layout.draw(
                    canvas,
                    highlightPaths,
                    highlightPaints,
                    selectionHighlight,
                    selectionHighlightPaint,
                    cursorOffsetVertical);
        }
    }

    private void drawHardwareAccelerated(
            Canvas canvas,
            Layout layout,
            List<Path> highlightPaths,
            List<Paint> highlightPaints,
            Path selectionHighlight,
            Paint selectionHighlightPaint,
            int cursorOffsetVertical,
            boolean shouldDrawHighlightsOnTop) {
        int lastLine;
        int firstLine;
        int numberOfBlocks;
        int[] blockEndLines;
        DynamicLayout dynamicLayout;
        int lastLine2;
        int firstLine2;
        ArraySet<Integer> blockSet;
        int lastIndex;
        int lastIndex2;
        int i;
        int lastIndex3;
        boolean z;
        long lineRange;
        int i2;
        int indexFirstChangedBlock;
        Editor editor = this;
        long lineRange2 = layout.getLineRangeForDraw(canvas);
        int firstLine3 = TextUtils.unpackRangeStartFromLong(lineRange2);
        int lastLine3 = TextUtils.unpackRangeEndFromLong(lineRange2);
        if (lastLine3 < 0) {
            return;
        }
        if (!shouldDrawHighlightsOnTop) {
            layout.drawWithoutText(
                    canvas,
                    highlightPaths,
                    highlightPaints,
                    selectionHighlight,
                    selectionHighlightPaint,
                    cursorOffsetVertical,
                    firstLine3,
                    lastLine3);
        } else {
            layout.drawBackground(canvas, firstLine3, lastLine3);
        }
        if (layout instanceof DynamicLayout) {
            if (editor.mTextRenderNodes == null) {
                editor.mTextRenderNodes =
                        (TextRenderNode[]) ArrayUtils.emptyArray(TextRenderNode.class);
            }
            DynamicLayout dynamicLayout2 = (DynamicLayout) layout;
            int[] blockEndLines2 = dynamicLayout2.getBlockEndLines();
            int[] blockIndices = dynamicLayout2.getBlockIndices();
            int numberOfBlocks2 = dynamicLayout2.getNumberOfBlocks();
            int indexFirstChangedBlock2 = dynamicLayout2.getIndexFirstChangedBlock();
            ArraySet<Integer> blockSet2 = dynamicLayout2.getBlocksAlwaysNeedToBeRedrawn();
            int i3 = -1;
            boolean z2 = true;
            if (blockSet2 != null) {
                int i4 = 0;
                while (i4 < blockSet2.size()) {
                    int blockIndex = dynamicLayout2.getBlockIndex(blockSet2.valueAt(i4).intValue());
                    if (blockIndex != i3 && editor.mTextRenderNodes[blockIndex] != null) {
                        editor.mTextRenderNodes[blockIndex].needsToBeShifted = true;
                    }
                    i4++;
                    i3 = -1;
                }
            }
            int startBlock = Arrays.binarySearch(blockEndLines2, 0, numberOfBlocks2, firstLine3);
            if (startBlock < 0) {
                startBlock = -(startBlock + 1);
            }
            int startIndexToFindAvailableRenderNode = 0;
            int i5 = Math.min(indexFirstChangedBlock2, startBlock);
            while (true) {
                if (i5 >= numberOfBlocks2) {
                    numberOfBlocks = numberOfBlocks2;
                    blockEndLines = blockEndLines2;
                    dynamicLayout = dynamicLayout2;
                    lastLine2 = lastLine3;
                    firstLine2 = firstLine3;
                    blockSet = blockSet2;
                    lastIndex = numberOfBlocks2;
                    break;
                }
                int blockIndex2 = blockIndices[i5];
                if (i5 >= indexFirstChangedBlock2
                        && blockIndex2 != -1
                        && editor.mTextRenderNodes[blockIndex2] != null) {
                    editor.mTextRenderNodes[blockIndex2].needsToBeShifted = z2;
                }
                if (blockEndLines2[i5] < firstLine3) {
                    z = z2;
                    i2 = i5;
                    numberOfBlocks = numberOfBlocks2;
                    blockEndLines = blockEndLines2;
                    dynamicLayout = dynamicLayout2;
                    lastLine2 = lastLine3;
                    firstLine2 = firstLine3;
                    lineRange = lineRange2;
                    blockSet = blockSet2;
                    indexFirstChangedBlock = indexFirstChangedBlock2;
                } else {
                    z = z2;
                    lineRange = lineRange2;
                    i2 = i5;
                    blockSet = blockSet2;
                    indexFirstChangedBlock = indexFirstChangedBlock2;
                    numberOfBlocks = numberOfBlocks2;
                    blockEndLines = blockEndLines2;
                    dynamicLayout = dynamicLayout2;
                    lastLine2 = lastLine3;
                    firstLine2 = firstLine3;
                    startIndexToFindAvailableRenderNode =
                            drawHardwareAcceleratedInner(
                                    canvas,
                                    layout,
                                    selectionHighlight,
                                    selectionHighlightPaint,
                                    cursorOffsetVertical,
                                    blockEndLines2,
                                    blockIndices,
                                    i2,
                                    numberOfBlocks,
                                    startIndexToFindAvailableRenderNode);
                    if (blockEndLines[i2] >= lastLine2) {
                        int lastIndex4 = Math.max(indexFirstChangedBlock, i2 + 1);
                        lastIndex = lastIndex4;
                        break;
                    }
                }
                i5 = i2 + 1;
                dynamicLayout2 = dynamicLayout;
                lastLine3 = lastLine2;
                indexFirstChangedBlock2 = indexFirstChangedBlock;
                blockSet2 = blockSet;
                z2 = z;
                lineRange2 = lineRange;
                numberOfBlocks2 = numberOfBlocks;
                blockEndLines2 = blockEndLines;
                firstLine3 = firstLine2;
            }
            if (blockSet == null) {
                lastIndex2 = lastIndex;
            } else {
                int i6 = 0;
                while (i6 < blockSet.size()) {
                    int block = blockSet.valueAt(i6).intValue();
                    int blockIndex3 = dynamicLayout.getBlockIndex(block);
                    if (blockIndex3 == -1
                            || editor.mTextRenderNodes[blockIndex3] == null
                            || editor.mTextRenderNodes[blockIndex3].needsToBeShifted) {
                        i = i6;
                        int i7 = numberOfBlocks;
                        lastIndex3 = lastIndex;
                        int lastIndex5 = startIndexToFindAvailableRenderNode;
                        startIndexToFindAvailableRenderNode =
                                drawHardwareAcceleratedInner(
                                        canvas,
                                        layout,
                                        selectionHighlight,
                                        selectionHighlightPaint,
                                        cursorOffsetVertical,
                                        blockEndLines,
                                        blockIndices,
                                        block,
                                        i7,
                                        lastIndex5);
                    } else {
                        i = i6;
                        lastIndex3 = lastIndex;
                    }
                    i6 = i + 1;
                    lastIndex = lastIndex3;
                    editor = this;
                }
                lastIndex2 = lastIndex;
            }
            dynamicLayout.setIndexFirstChangedBlock(lastIndex2);
            lastLine = lastLine2;
            firstLine = firstLine2;
        } else {
            lastLine = lastLine3;
            firstLine = firstLine3;
            layout.drawText(canvas, firstLine, lastLine);
        }
        if (shouldDrawHighlightsOnTop) {
            layout.drawHighlights(
                    canvas,
                    highlightPaths,
                    highlightPaints,
                    selectionHighlight,
                    selectionHighlightPaint,
                    cursorOffsetVertical,
                    firstLine,
                    lastLine);
        }
    }

    private int drawHardwareAcceleratedInner(
            Canvas canvas,
            Layout layout,
            Path highlight,
            Paint highlightPaint,
            int cursorOffsetVertical,
            int[] blockEndLines,
            int[] blockIndices,
            int blockInfoIndex,
            int numberOfBlocks,
            int startIndexToFindAvailableRenderNode) {
        int startIndexToFindAvailableRenderNode2;
        int blockIndex;
        int line;
        boolean z;
        int blockEndLine = blockEndLines[blockInfoIndex];
        int blockIndex2 = blockIndices[blockInfoIndex];
        boolean blockIsInvalid = blockIndex2 == -1;
        if (blockIsInvalid) {
            int blockIndex3 =
                    getAvailableDisplayListIndex(
                            blockIndices, numberOfBlocks, startIndexToFindAvailableRenderNode);
            blockIndices[blockInfoIndex] = blockIndex3;
            if (this.mTextRenderNodes[blockIndex3] != null) {
                this.mTextRenderNodes[blockIndex3].isDirty = true;
            }
            startIndexToFindAvailableRenderNode2 = blockIndex3 + 1;
            blockIndex = blockIndex3;
        } else {
            startIndexToFindAvailableRenderNode2 = startIndexToFindAvailableRenderNode;
            blockIndex = blockIndex2;
        }
        if (this.mTextRenderNodes[blockIndex] == null) {
            this.mTextRenderNodes[blockIndex] = new TextRenderNode("Text " + blockIndex);
        }
        boolean blockDisplayListIsInvalid = this.mTextRenderNodes[blockIndex].needsRecord();
        RenderNode blockDisplayList = this.mTextRenderNodes[blockIndex].renderNode;
        if (this.mTextRenderNodes[blockIndex].needsToBeShifted || blockDisplayListIsInvalid) {
            int blockBeginLine = blockInfoIndex == 0 ? 0 : blockEndLines[blockInfoIndex - 1] + 1;
            int top = layout.getLineTop(blockBeginLine);
            int bottom = layout.getLineBottom(blockEndLine);
            int right = this.mTextView.getWidth();
            if (!this.mTextView.getHorizontallyScrolling()) {
                line = 0;
            } else {
                float min = Float.MAX_VALUE;
                float max = Float.MIN_VALUE;
                int line2 = blockBeginLine;
                while (line2 <= blockEndLine) {
                    min = Math.min(min, layout.getLineLeft(line2));
                    max = Math.max(max, layout.getLineRight(line2));
                    line2++;
                    blockIsInvalid = blockIsInvalid;
                }
                line = (int) min;
                right = (int) (0.5f + max);
            }
            if (!blockDisplayListIsInvalid) {
                z = false;
            } else {
                RecordingCanvas recordingCanvas =
                        blockDisplayList.beginRecording(right - line, bottom - top);
                try {
                    recordingCanvas.translate(-line, -top);
                    layout.drawText(recordingCanvas, blockBeginLine, blockEndLine);
                    if (canPrintLagLog()) {
                        Log.d(TAG_LAG, "drawText");
                    }
                    this.mTextRenderNodes[blockIndex].isDirty = false;
                    blockDisplayList.endRecording();
                    blockDisplayList.setClipToBounds(false);
                    z = false;
                } catch (Throwable th) {
                    blockDisplayList.endRecording();
                    blockDisplayList.setClipToBounds(false);
                    throw th;
                }
            }
            blockDisplayList.setLeftTopRightBottom(line, top, right, bottom);
            this.mTextRenderNodes[blockIndex].needsToBeShifted = z;
        }
        ((RecordingCanvas) canvas).drawRenderNode(blockDisplayList);
        return startIndexToFindAvailableRenderNode2;
    }

    private int getAvailableDisplayListIndex(
            int[] blockIndices, int numberOfBlocks, int searchStartIndex) {
        int length = this.mTextRenderNodes.length;
        for (int i = searchStartIndex; i < length; i++) {
            boolean blockIndexFound = false;
            int j = 0;
            while (true) {
                if (j >= numberOfBlocks) {
                    break;
                }
                if (blockIndices[j] != i) {
                    j++;
                } else {
                    blockIndexFound = true;
                    break;
                }
            }
            if (!blockIndexFound) {
                return i;
            }
        }
        this.mTextRenderNodes =
                (TextRenderNode[])
                        GrowingArrayUtils.append(this.mTextRenderNodes, length, (Object) null);
        return length;
    }

    private void drawCursor(Canvas canvas, int cursorOffsetHorizontal, int cursorOffsetVertical) {
        boolean translate =
                (cursorOffsetHorizontal == 0 && cursorOffsetVertical == 0) ? false : true;
        if (translate) {
            canvas.translate(cursorOffsetHorizontal, cursorOffsetVertical);
        }
        if (this.mDrawableForCursor != null) {
            this.mDrawableForCursor.draw(canvas);
        }
        if (translate) {
            canvas.translate(-cursorOffsetHorizontal, -cursorOffsetVertical);
        }
    }

    void invalidateHandlesAndActionMode() {
        if (this.mSelectionModifierCursorController != null) {
            this.mSelectionModifierCursorController.invalidateHandles();
        }
        if (this.mInsertionPointCursorController != null) {
            this.mInsertionPointCursorController.invalidateHandle();
        }
        if (this.mTextActionMode != null) {
            invalidateActionMode();
        }
    }

    void invalidateTextDisplayList(Layout layout, int start, int end) {
        if (this.mTextRenderNodes != null && (layout instanceof DynamicLayout)) {
            if (Flags.insertModeCrashWhenDelete() && this.mTextView.isOffsetMappingAvailable()) {
                invalidateTextDisplayList();
                return;
            }
            int startTransformed = this.mTextView.originalToTransformed(start, 0);
            int endTransformed = this.mTextView.originalToTransformed(end, 0);
            int firstLine = layout.getLineForOffset(startTransformed);
            int lastLine = layout.getLineForOffset(endTransformed);
            DynamicLayout dynamicLayout = (DynamicLayout) layout;
            int[] blockEndLines = dynamicLayout.getBlockEndLines();
            int[] blockIndices = dynamicLayout.getBlockIndices();
            int numberOfBlocks = dynamicLayout.getNumberOfBlocks();
            int i = 0;
            while (i < numberOfBlocks && blockEndLines[i] < firstLine) {
                i++;
            }
            while (i < numberOfBlocks) {
                int blockIndex = blockIndices[i];
                if (blockIndex != -1) {
                    this.mTextRenderNodes[blockIndex].isDirty = true;
                }
                if (blockEndLines[i] >= lastLine) {
                    return;
                } else {
                    i++;
                }
            }
        }
    }

    void invalidateTextDisplayList() {
        if (this.mTextRenderNodes != null) {
            for (int i = 0; i < this.mTextRenderNodes.length; i++) {
                if (this.mTextRenderNodes[i] != null) {
                    this.mTextRenderNodes[i].isDirty = true;
                }
            }
        }
    }

    void updateCursorPosition() {
        loadCursorDrawable();
        if (this.mDrawableForCursor == null) {
            return;
        }
        Layout layout = getActiveLayout();
        int offset = this.mTextView.getSelectionStart();
        int transformedOffset = this.mTextView.originalToTransformed(offset, 1);
        int line = layout.getLineForOffset(transformedOffset);
        int top = layout.getLineTop(line);
        int bottom = layout.getLineBottom(line, false);
        boolean clamped = layout.shouldClampCursor(line);
        updateCursorPosition(top, bottom, layout.getPrimaryHorizontal(transformedOffset, clamped));
    }

    void refreshTextActionMode() {
        if (extractedTextModeWillBeStarted()) {
            this.mRestartActionModeOnNextRefresh = false;
            return;
        }
        boolean hasSelection = this.mTextView.hasSelection();
        SelectionModifierCursorController selectionController = getSelectionController();
        InsertionPointCursorController insertionController = getInsertionController();
        if ((selectionController != null && selectionController.isCursorBeingModified())
                || (insertionController != null && insertionController.isCursorBeingModified())) {
            this.mRestartActionModeOnNextRefresh = false;
            return;
        }
        if (hasSelection) {
            hideInsertionPointCursorController();
            if (this.mTextActionMode == null) {
                if (this.mRestartActionModeOnNextRefresh) {
                    startSelectionActionModeAsync(false);
                }
            } else if (selectionController == null || !selectionController.isActive()) {
                stopTextActionModeWithPreservingSelection();
                startSelectionActionModeAsync(false);
            } else {
                this.mTextActionMode.invalidateContentRect();
            }
        } else if (insertionController == null || !insertionController.isActive()) {
            lambda$startActionModeInternal$0();
        } else if (this.mTextActionMode != null) {
            this.mTextActionMode.invalidateContentRect();
        }
        this.mRestartActionModeOnNextRefresh = false;
    }

    void startInsertionActionMode() {
        if (this.mInsertionActionModeRunnable != null) {
            this.mTextView.removeCallbacks(this.mInsertionActionModeRunnable);
        }
        if (extractedTextModeWillBeStarted()) {
            return;
        }
        lambda$startActionModeInternal$0();
        if ((this.mUseCtxMenuInDesktopMode && this.mTextView.isDesktopMode())
                || isUniversalSwitchEnable()) {
            Log.e(
                    "Editor",
                    "Action mode didn't start because Universal Switch / Desktop mode was enabled");
            return;
        }
        ActionMode.Callback actionModeCallback = new TextActionModeCallback(1);
        this.mTextActionMode = this.mTextView.startActionMode(actionModeCallback, 1);
        registerOnBackInvokedCallback();
        if (this.mTextActionMode != null && getInsertionController() != null) {
            getInsertionController().show();
        }
    }

    TextView getTextView() {
        return this.mTextView;
    }

    ActionMode getTextActionMode() {
        return this.mTextActionMode;
    }

    void setRestartActionModeOnNextRefresh(boolean value) {
        this.mRestartActionModeOnNextRefresh = value;
    }

    void startSelectionActionModeAsync(boolean adjustSelection) {
        getSelectionActionModeHelper().startSelectionActionModeAsync(adjustSelection);
    }

    void startLinkActionModeAsync(int start, int end) {
        if (!(this.mTextView.getText() instanceof Spannable)) {
            return;
        }
        lambda$startActionModeInternal$0();
        this.mRequestingLinkActionMode = true;
        getSelectionActionModeHelper().startLinkActionModeAsync(start, end);
    }

    void invalidateActionModeAsync() {
        getSelectionActionModeHelper().invalidateActionModeAsync();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void invalidateActionMode() {
        if (this.mTextActionMode != null) {
            this.mTextActionMode.invalidate();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public SelectionActionModeHelper getSelectionActionModeHelper() {
        if (this.mSelectionActionModeHelper == null) {
            this.mSelectionActionModeHelper = new SelectionActionModeHelper(this);
        }
        return this.mSelectionActionModeHelper;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean selectCurrentWordAndStartDrag() {
        if (this.mInsertionActionModeRunnable != null) {
            this.mTextView.removeCallbacks(this.mInsertionActionModeRunnable);
        }
        if (extractedTextModeWillBeStarted() || !checkField()) {
            return false;
        }
        if (!this.mTextView.hasSelection() && !selectCurrentWord()) {
            this.mShowMagnifier = false;
            dismissMagnifierForDrag();
            return false;
        }
        stopTextActionModeWithPreservingSelection();
        SelectionModifierCursorController selectionController = getSelectionController();
        if (selectionController != null) {
            selectionController.enterDrag(2);
            return true;
        }
        return true;
    }

    boolean checkField() {
        if (!this.mTextView.canSelectText() || !this.mTextView.requestFocus()) {
            Log.w("TextView", "TextView does not support text selection. Selection cancelled.");
            return false;
        }
        return true;
    }

    boolean startActionModeInternal(int actionMode) {
        InputMethodManager imm;
        if (ViewRune.WIDGET_MULTIPLE_PEN_TEXT_SUPPORTED) {
            this.mTextView.clearAllMultiSelection();
        }
        if (extractedTextModeWillBeStarted()) {
            return false;
        }
        if (this.mTextActionMode != null) {
            invalidateActionMode();
            return false;
        }
        if ((actionMode != 2 && (!checkField() || !this.mTextView.hasSelection()))
                || !this.mTextView.showUIForTouchScreen()) {
            return false;
        }
        if ((!this.mUseCtxMenuInDesktopMode || !this.mTextView.isDesktopMode())
                && !isUniversalSwitchEnable()) {
            ActionMode.Callback actionModeCallback = new TextActionModeCallback(actionMode);
            this.mTextActionMode = this.mTextView.startActionMode(actionModeCallback, 1);
            registerOnBackInvokedCallback();
        } else {
            Log.e(
                    "Editor",
                    "Action mode didn't start because Universal Switch / Desktop mode was enabled");
        }
        boolean selectableText =
                this.mTextView.isTextEditable() || this.mTextView.isTextSelectable();
        if (actionMode == 2
                && !selectableText
                && (this.mTextActionMode instanceof FloatingActionMode)) {
            ((FloatingActionMode) this.mTextActionMode)
                    .setOutsideTouchable(
                            true,
                            new PopupWindow
                                    .OnDismissListener() { // from class:
                                                           // android.widget.Editor$$ExternalSyntheticLambda1
                                @Override // android.widget.PopupWindow.OnDismissListener
                                public final void onDismiss() {
                                    Editor.this.lambda$startActionModeInternal$0();
                                }
                            });
        }
        boolean selectionStarted = this.mTextActionMode != null;
        if (selectionStarted
                && this.mTextView.isTextEditable()
                && !this.mTextView.isTextSelectable()
                && this.mShowSoftInputOnFocus
                && (imm = getInputMethodManager()) != null) {
            imm.showSoftInput(this.mTextView, 0, null);
        }
        return selectionStarted;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean extractedTextModeWillBeStarted() {
        InputMethodManager imm;
        return (this.mTextView.isInExtractedMode()
                        || (imm = getInputMethodManager()) == null
                        || !imm.isFullscreenMode())
                ? false
                : true;
    }

    boolean shouldOfferToShowSuggestions() {
        CharSequence text = this.mTextView.getText();
        if (!(text instanceof Spannable)) {
            return false;
        }
        Spannable spannable = (Spannable) text;
        int selectionStart = this.mTextView.getSelectionStart();
        int selectionEnd = this.mTextView.getSelectionEnd();
        SuggestionSpan[] suggestionSpans =
                (SuggestionSpan[])
                        spannable.getSpans(selectionStart, selectionEnd, SuggestionSpan.class);
        if (suggestionSpans.length == 0) {
            return false;
        }
        if (selectionStart == selectionEnd) {
            for (int i = 0;
                    i < suggestionSpans.length && (suggestionSpans[i].getFlags() & 12288) == 0;
                    i++) {
                if (suggestionSpans[i].getSuggestions().length > 0) {
                    return true;
                }
            }
            return false;
        }
        int minSpanStart = this.mTextView.getText().length();
        int maxSpanEnd = 0;
        int unionOfSpansCoveringSelectionStartStart = this.mTextView.getText().length();
        int unionOfSpansCoveringSelectionStartEnd = 0;
        boolean hasValidSuggestions = false;
        for (int i2 = 0; i2 < suggestionSpans.length; i2++) {
            if ((suggestionSpans[i2].getFlags() & 12288) != 0) {
                return false;
            }
            int spanStart = spannable.getSpanStart(suggestionSpans[i2]);
            int spanEnd = spannable.getSpanEnd(suggestionSpans[i2]);
            minSpanStart = Math.min(minSpanStart, spanStart);
            maxSpanEnd = Math.max(maxSpanEnd, spanEnd);
            if (selectionStart >= spanStart && selectionStart <= spanEnd) {
                boolean hasValidSuggestions2 =
                        hasValidSuggestions || suggestionSpans[i2].getSuggestions().length > 0;
                unionOfSpansCoveringSelectionStartStart =
                        Math.min(unionOfSpansCoveringSelectionStartStart, spanStart);
                unionOfSpansCoveringSelectionStartEnd =
                        Math.max(unionOfSpansCoveringSelectionStartEnd, spanEnd);
                hasValidSuggestions = hasValidSuggestions2;
            }
        }
        return hasValidSuggestions
                && unionOfSpansCoveringSelectionStartStart < unionOfSpansCoveringSelectionStartEnd
                && minSpanStart >= unionOfSpansCoveringSelectionStartStart
                && maxSpanEnd <= unionOfSpansCoveringSelectionStartEnd;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isCursorInsideEasyCorrectionSpan() {
        Spannable spannable = (Spannable) this.mTextView.getText();
        SuggestionSpan[] suggestionSpans =
                (SuggestionSpan[])
                        spannable.getSpans(
                                this.mTextView.getSelectionStart(),
                                this.mTextView.getSelectionEnd(),
                                SuggestionSpan.class);
        for (SuggestionSpan suggestionSpan : suggestionSpans) {
            if ((suggestionSpan.getFlags() & 1) != 0) {
                return true;
            }
        }
        return false;
    }

    void onTouchUpEvent(MotionEvent event) {
        InsertionPointCursorController mInsertionController;
        if (getSelectionActionModeHelper()
                .resetSelection(getTextView().getOffsetForPosition(event.getX(), event.getY()))) {
            return;
        }
        boolean selectAllGotFocus = this.mSelectAllOnFocus && this.mTextView.didTouchFocusSelect();
        boolean insertionHandleActived = false;
        int beforeCursorOffset = -1;
        if ((getInsertionController() != null && getInsertionController().isActive())
                || (this.mTextView.getText() != null && this.mTextView.getText().length() == 0)) {
            insertionHandleActived = true;
            beforeCursorOffset = this.mTextView.getSelectionStart();
        }
        hideCursorAndSpanControllers();
        lambda$startActionModeInternal$0();
        CharSequence text = this.mTextView.getText();
        if (!selectAllGotFocus && text.length() >= 0) {
            int offset = this.mTextView.getOffsetForPosition(event.getX(), event.getY());
            boolean shouldInsertCursor = !this.mRequestingLinkActionMode;
            if (shouldInsertCursor) {
                Selection.setSelection((Spannable) text, offset);
                if (this.mSpellChecker != null) {
                    this.mSpellChecker.onSelectionChanged();
                }
            }
            if (!extractedTextModeWillBeStarted()) {
                if (isCursorInsideEasyCorrectionSpan()) {
                    if (this.mInsertionActionModeRunnable != null) {
                        this.mTextView.removeCallbacks(this.mInsertionActionModeRunnable);
                    }
                    boolean isHBDGrammarly = false;
                    Spannable spannable = (Spannable) this.mTextView.getText();
                    SuggestionSpan[] suggestionSpans =
                            (SuggestionSpan[])
                                    spannable.getSpans(
                                            this.mTextView.getSelectionStart(),
                                            this.mTextView.getSelectionEnd(),
                                            SuggestionSpan.class);
                    int i = 0;
                    while (true) {
                        if (i >= suggestionSpans.length) {
                            break;
                        }
                        if ((suggestionSpans[i].getFlags() & 12288) == 0) {
                            i++;
                        } else {
                            isHBDGrammarly = true;
                            break;
                        }
                    }
                    if (!isHBDGrammarly) {
                        this.mShowSuggestionRunnable =
                                new Runnable() { // from class:
                                                 // android.widget.Editor$$ExternalSyntheticLambda2
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        Editor.this.replace();
                                    }
                                };
                    }
                    this.mTextView.postDelayed(
                            this.mShowSuggestionRunnable, ViewConfiguration.getDoubleTapTimeout());
                    return;
                }
                if (hasInsertionController()) {
                    if (shouldInsertCursor && this.mTextView.showUIForTouchScreen()) {
                        if (!this.mShowSoftInputOnFocusInternal) {
                            this.mShowSoftInputOnFocusInternal = softInputShown();
                        }
                        if (this.mTextView.getText() != null
                                && this.mTextView.getText().length() == 0) {
                            if (this.mWasBlinking
                                    && this.mShowSoftInputOnFocus == this.mWasSIPShowing
                                    && (mInsertionController = getInsertionController()) != null
                                    && !mInsertionController.isActive()) {
                                mInsertionController.show();
                                startInsertionActionMode();
                                return;
                            }
                            return;
                        }
                        if (insertionHandleActived
                                && beforeCursorOffset == this.mTextView.getSelectionStart()
                                && !this.mToggleActionMode) {
                            startInsertionActionMode();
                            this.mToggleActionMode = true;
                        } else {
                            this.mToggleActionMode = false;
                        }
                        getInsertionController().show();
                        return;
                    }
                    getInsertionController().hide();
                }
            }
        }
    }

    final void onTextOperationUserChanged() {
        if (this.mSpellChecker != null) {
            this.mSpellChecker.resetSession();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: stopTextActionMode, reason: merged with bridge method [inline-methods] */
    public void lambda$startActionModeInternal$0() {
        if (this.mTextActionMode != null) {
            this.mTextActionMode.finish();
        }
        unregisterOnBackInvokedCallback();
    }

    void stopTextActionModeWithPreservingSelection() {
        if (this.mTextActionMode != null) {
            this.mRestartActionModeOnNextRefresh = true;
        }
        this.mPreserveSelection = true;
        lambda$startActionModeInternal$0();
        this.mPreserveSelection = false;
    }

    boolean hasInsertionController() {
        return this.mInsertionControllerEnabled;
    }

    boolean hasSelectionController() {
        return this.mSelectionControllerEnabled;
    }

    public InsertionPointCursorController getInsertionController() {
        if (!this.mInsertionControllerEnabled) {
            return null;
        }
        if (this.mInsertionPointCursorController == null) {
            this.mInsertionPointCursorController = new InsertionPointCursorController();
            ViewTreeObserver observer = this.mTextView.getViewTreeObserver();
            observer.addOnTouchModeChangeListener(this.mInsertionPointCursorController);
        }
        return this.mInsertionPointCursorController;
    }

    public SelectionModifierCursorController getSelectionController() {
        if (!this.mSelectionControllerEnabled) {
            return null;
        }
        if (this.mSelectionModifierCursorController == null) {
            this.mSelectionModifierCursorController = new SelectionModifierCursorController();
            ViewTreeObserver observer = this.mTextView.getViewTreeObserver();
            observer.addOnTouchModeChangeListener(this.mSelectionModifierCursorController);
        }
        return this.mSelectionModifierCursorController;
    }

    public Drawable getCursorDrawable() {
        return this.mDrawableForCursor;
    }

    private void updateCursorPosition(int top, int bottom, float horizontal) {
        loadCursorDrawable();
        int left = clampHorizontalPosition(this.mDrawableForCursor, horizontal);
        int width = this.mDrawableForCursor.getIntrinsicWidth();
        int scaledWidth = Math.round(width * this.mTextView.getCursorThicknessScale());
        float magnifierCursorScale = 0.0f;
        if (getInsertionController() != null
                && getInsertionController().getHandle().shouldMagnifierCursorAdjust()) {
            magnifierCursorScale = 0.2f;
        }
        int height = bottom - top;
        int offset = Math.round(height * magnifierCursorScale);
        int scaledTop = top + offset;
        int scaledBottom = bottom - offset;
        this.mDrawableForCursor.setBounds(
                left,
                scaledTop - this.mTempRect.top,
                left + scaledWidth,
                this.mTempRect.bottom + scaledBottom);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int clampHorizontalPosition(Drawable drawable, float horizontal) {
        float horizontal2 = Math.max(0.5f, horizontal - 0.5f);
        if (this.mTempRect == null) {
            this.mTempRect = new Rect();
        }
        int drawableWidth = 0;
        if (drawable != null) {
            drawable.getPadding(this.mTempRect);
            drawableWidth = drawable.getIntrinsicWidth();
        } else {
            this.mTempRect.setEmpty();
        }
        int scrollX = this.mTextView.getScrollX();
        float horizontalDiff = horizontal2 - scrollX;
        int viewClippedWidth =
                (this.mTextView.getWidth() - this.mTextView.getCompoundPaddingLeft())
                        - this.mTextView.getCompoundPaddingRight();
        if (horizontalDiff >= viewClippedWidth - 1.0f) {
            int left = (viewClippedWidth + scrollX) - (drawableWidth - this.mTempRect.right);
            return left;
        }
        if (Math.abs(horizontalDiff) <= 1.0f
                || (TextUtils.isEmpty(this.mTextView.getText())
                        && 1048576 - scrollX <= viewClippedWidth + 1.0f
                        && horizontal2 <= 1.0f)) {
            int left2 = scrollX - this.mTempRect.left;
            return left2;
        }
        int left3 = ((int) horizontal2) - this.mTempRect.left;
        return left3;
    }

    public void onCommitCorrection(CorrectionInfo info) {
        if (this.mCorrectionHighlighter == null) {
            this.mCorrectionHighlighter = new CorrectionHighlighter();
        } else {
            this.mCorrectionHighlighter.invalidate(false);
        }
        this.mCorrectionHighlighter.highlight(info);
        this.mUndoInputFilter.freezeLastEdit();
    }

    void onScrollChanged() {
        if (this.mPositionListener != null) {
            this.mPositionListener.onScrollChanged();
        }
        if (this.mTextActionMode != null) {
            this.mTextActionMode.invalidateContentRect();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean shouldBlink() {
        int start;
        int end;
        return isCursorVisible()
                && this.mTextView.isFocused()
                && this.mTextView.getWindowVisibility() == 0
                && (start = this.mTextView.getSelectionStart()) >= 0
                && (end = this.mTextView.getSelectionEnd()) >= 0
                && start == end;
    }

    void makeBlink() {
        if (shouldBlink()) {
            this.mShowCursor = SystemClock.uptimeMillis();
            if (this.mBlink == null) {
                this.mBlink = new Blink();
            }
            this.mBlink.uncancel();
            this.mTextView.removeCallbacks(this.mBlink);
            this.mTextView.postDelayed(this.mBlink, 500L);
            return;
        }
        if (this.mBlink != null) {
            this.mTextView.removeCallbacks(this.mBlink);
        }
    }

    public boolean isBlinking() {
        if (this.mBlink == null) {
            return false;
        }
        return !this.mBlink.mCancelled;
    }

    private class Blink implements Runnable {
        private boolean mCancelled;

        private Blink() {}

        @Override // java.lang.Runnable
        public void run() {
            if (this.mCancelled) {
                return;
            }
            Editor.this.mTextView.removeCallbacks(this);
            if (Editor.this.shouldBlink()) {
                if (Editor.this.mTextView.getLayout() != null) {
                    Editor.this.mTextView.invalidateCursorPath();
                }
                Editor.this.mTextView.postDelayed(this, 500L);
            }
        }

        void cancel() {
            if (!this.mCancelled) {
                Editor.this.mTextView.removeCallbacks(this);
                this.mCancelled = true;
            }
        }

        void uncancel() {
            this.mCancelled = false;
        }
    }

    private View.DragShadowBuilder getTextThumbnailBuilder(int start, int end) {
        int shadowViewMaxWidth;
        int i;
        FrameLayout shadowView =
                (FrameLayout)
                        View.inflate(
                                this.mTextView.getContext(),
                                R.layout.sem_text_drag_thumbnail,
                                null);
        TextView shadowViewContents = (TextView) shadowView.getChildAt(1);
        if (shadowView == null) {
            throw new IllegalArgumentException("Unable to inflate text drag thumbnail");
        }
        CharSequence text = this.mTextView.getTransformedText(start, end);
        shadowViewContents.lambda$setTextAsync$0(text);
        shadowView.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        Resources resources = this.mTextView.getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        float width = displayMetrics.widthPixels / displayMetrics.density;
        if (width < 480.0f) {
            shadowViewMaxWidth = Math.round(displayMetrics.widthPixels * 0.75f);
        } else {
            int shadowViewMaxWidth2 = displayMetrics.widthPixels;
            shadowViewMaxWidth = Math.round(shadowViewMaxWidth2 * SHADOW_VIEW_MAX_WIDTH_TABLET);
        }
        int shadowViewMinWidth =
                resources.getDimensionPixelSize(R.dimen.sem_text_drag_thumbnail_min_width);
        int shadowSize =
                resources.getDimensionPixelSize(
                        R.dimen.sem_text_drag_thumbnail_background_shadow_size);
        int size = View.MeasureSpec.makeMeasureSpec(0, 0);
        shadowView.measure(size, size);
        int measuredWidth = shadowView.getMeasuredWidth();
        int measuredHeight = shadowView.getMeasuredHeight();
        int contentWidth = measuredWidth - (shadowSize * 2);
        int i2 = measuredHeight - (shadowSize * 2);
        if (contentWidth > shadowViewMaxWidth) {
            int shadowWidth = shadowViewMaxWidth + (shadowSize * 2);
            int widthSpec = View.MeasureSpec.makeMeasureSpec(shadowWidth, 1073741824);
            int heightSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
            shadowView.measure(widthSpec, heightSpec);
            measuredWidth = shadowView.getMeasuredWidth();
            measuredHeight = shadowView.getMeasuredHeight();
            i = 0;
        } else if (contentWidth < shadowViewMinWidth) {
            float textSize = (shadowViewContents.getTextSize() * shadowViewMinWidth) / contentWidth;
            shadowViewContents.setTextSize(0, textSize);
            shadowViewContents.setGravity(17);
            int shadowWidth2 = (shadowSize * 2) + shadowViewMinWidth;
            int widthSpec2 = View.MeasureSpec.makeMeasureSpec(shadowWidth2, 1073741824);
            i = 0;
            int heightSpec2 = View.MeasureSpec.makeMeasureSpec(0, 0);
            shadowView.measure(widthSpec2, heightSpec2);
            measuredWidth = shadowView.getMeasuredWidth();
            measuredHeight = shadowView.getMeasuredHeight();
        } else {
            i = 0;
        }
        shadowView.layout(i, i, measuredWidth, measuredHeight);
        shadowView.invalidate();
        return new View.DragShadowBuilder(shadowView);
    }

    private static class DragLocalState {
        public int end;
        public TextView sourceTextView;
        public int start;

        public DragLocalState(TextView sourceTextView, int start, int end) {
            this.sourceTextView = sourceTextView;
            this.start = start;
            this.end = end;
        }
    }

    void onDrop(DragEvent event) {
        int offset = this.mTextView.getOffsetForPosition(event.getX(), event.getY());
        Object localState = event.getLocalState();
        DragLocalState dragLocalState = null;
        if (localState instanceof DragLocalState) {
            dragLocalState = (DragLocalState) localState;
        }
        boolean dragDropIntoItself =
                dragLocalState != null && dragLocalState.sourceTextView == this.mTextView;
        if (dragDropIntoItself && offset >= dragLocalState.start && offset < dragLocalState.end) {
            return;
        }
        DragAndDropPermissions permissions = DragAndDropPermissions.obtain(event);
        if (permissions != null) {
            permissions.takeTransient();
        }
        this.mTextView.beginBatchEdit();
        this.mUndoInputFilter.freezeLastEdit();
        try {
            int originalLength = this.mTextView.getText().length();
            Selection.setSelection((Spannable) this.mTextView.getText(), offset);
            ClipData clip = event.getClipData();
            ContentInfo payload =
                    new ContentInfo.Builder(clip, 3).setDragAndDropPermissions(permissions).build();
            this.mTextView.performReceiveContent(payload);
            if (dragDropIntoItself) {
                deleteSourceAfterLocalDrop(dragLocalState, offset, originalLength);
            }
        } finally {
            this.mTextView.endBatchEdit();
            this.mUndoInputFilter.freezeLastEdit();
        }
    }

    private void deleteSourceAfterLocalDrop(
            DragLocalState dragLocalState, int dropOffset, int lengthBeforeDrop) {
        int dragSourceStart = dragLocalState.start;
        int dragSourceEnd = dragLocalState.end;
        if (dropOffset <= dragSourceStart) {
            int shift = this.mTextView.getText().length() - lengthBeforeDrop;
            dragSourceStart += shift;
            dragSourceEnd += shift;
        }
        this.mTextView.deleteText_internal(dragSourceStart, dragSourceEnd);
        int prevCharIdx = Math.max(0, dragSourceStart - 1);
        int nextCharIdx = Math.min(this.mTextView.getText().length(), dragSourceStart + 1);
        if (nextCharIdx > prevCharIdx + 1) {
            CharSequence t = this.mTextView.getTransformedText(prevCharIdx, nextCharIdx);
            if (Character.isSpaceChar(t.charAt(0)) && Character.isSpaceChar(t.charAt(1))) {
                this.mTextView.deleteText_internal(prevCharIdx, prevCharIdx + 1);
            }
        }
    }

    public void addSpanWatchers(Spannable text) {
        int textLength = text.length();
        if (this.mKeyListener != null) {
            text.setSpan(this.mKeyListener, 0, textLength, 18);
        }
        if (this.mSpanController == null) {
            this.mSpanController = new SpanController();
        }
        text.setSpan(this.mSpanController, 0, textLength, 18);
    }

    void setContextMenuAnchor(float x, float y) {
        this.mContextMenuAnchorX = x;
        this.mContextMenuAnchorY = y;
    }

    private void setAssistContextMenuItems(Menu menu) {
        TextClassification textClassification =
                getSelectionActionModeHelper().getTextClassification();
        if (textClassification == null) {
            return;
        }
        final AssistantCallbackHelper helper =
                new AssistantCallbackHelper(getSelectionActionModeHelper());
        helper.updateAssistMenuItems(
                menu,
                new MenuItem
                        .OnMenuItemClickListener() { // from class:
                                                     // android.widget.Editor$$ExternalSyntheticLambda3
                    @Override // android.view.MenuItem.OnMenuItemClickListener
                    public final boolean onMenuItemClick(MenuItem menuItem) {
                        boolean lambda$setAssistContextMenuItems$1;
                        lambda$setAssistContextMenuItems$1 =
                                Editor.this.lambda$setAssistContextMenuItems$1(helper, menuItem);
                        return lambda$setAssistContextMenuItems$1;
                    }
                });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$setAssistContextMenuItems$1(
            AssistantCallbackHelper helper, MenuItem item) {
        getSelectionActionModeHelper()
                .onSelectionAction(item.getItemId(), item.getTitle().toString());
        if (this.mProcessTextIntentActionsHandler.performMenuItemAction(item)) {
            return true;
        }
        if (item.getGroupId() == 16908353 && helper.onAssistMenuItemClicked(item)) {
            return true;
        }
        return this.mTextView.onTextContextMenuItem(item.getItemId());
    }

    public void onCreateContextMenu(ContextMenu menu) {
        int offset;
        int menuItemOrderShare;
        int menuItemOrderSelectAll;
        int menuItemOrderPasteAsPlainText;
        int keyboard;
        int menuItemOrderTranslate;
        if (this.mIsBeingLongClicked
                || Float.isNaN(this.mContextMenuAnchorX)
                || Float.isNaN(this.mContextMenuAnchorY)
                || (offset =
                                this.mTextView.getOffsetForPosition(
                                        this.mContextMenuAnchorX, this.mContextMenuAnchorY))
                        == -1) {
            return;
        }
        stopTextActionModeWithPreservingSelection();
        if (this.mTextView.canSelectText()) {
            boolean isOnSelection =
                    this.mTextView.hasSelection()
                            && offset >= this.mTextView.getSelectionStart()
                            && offset <= this.mTextView.getSelectionEnd();
            if (!isOnSelection) {
                Selection.setSelection((Spannable) this.mTextView.getText(), offset);
                lambda$startActionModeInternal$0();
            }
        }
        boolean isOnSelection2 = shouldOfferToShowSuggestions();
        if (isOnSelection2) {
            SuggestionInfo[] suggestionInfoArray = new SuggestionInfo[5];
            int i = 0;
            while (true) {
                if (i >= suggestionInfoArray.length) {
                    break;
                }
                suggestionInfoArray[i] = new SuggestionInfo();
                i++;
            }
            SubMenu subMenu = menu.addSubMenu(0, 0, 11, R.string.replace);
            int numItems = this.mSuggestionHelper.getSuggestionInfo(suggestionInfoArray, null);
            for (int i2 = 0; i2 < numItems; i2++) {
                final SuggestionInfo info = suggestionInfoArray[i2];
                subMenu.add(0, 0, i2, info.mText)
                        .setOnMenuItemClickListener(
                                new MenuItem
                                        .OnMenuItemClickListener() { // from class:
                                                                     // android.widget.Editor.4
                                    @Override // android.view.MenuItem.OnMenuItemClickListener
                                    public boolean onMenuItemClick(MenuItem item) {
                                        Editor.this.replaceWithSuggestion(info);
                                        return true;
                                    }
                                });
            }
        }
        if (this.mUseNewContextMenu) {
            menuItemOrderPasteAsPlainText = 13;
            menuItemOrderSelectAll = 14;
            menuItemOrderShare = 16;
            menu.setOptionalIconsVisible(true);
            menu.setGroupDividerEnabled(true);
            setAssistContextMenuItems(menu);
            int keyboard2 = this.mTextView.getResources().getConfiguration().keyboard;
            menu.setQwertyMode(keyboard2 == 2);
            keyboard = 15;
            menuItemOrderTranslate = 12;
        } else {
            menuItemOrderShare = 9;
            menuItemOrderSelectAll = 7;
            menuItemOrderPasteAsPlainText = 6;
            keyboard = 8;
            menuItemOrderTranslate = 5;
        }
        TypedArray a =
                this.mTextView
                        .getContext()
                        .obtainStyledAttributes(
                                new int[] {
                                    R.attr.actionModeUndoDrawable,
                                    R.attr.actionModeRedoDrawable,
                                    16843537,
                                    16843538,
                                    16843539,
                                    16843646,
                                    16843897
                                });
        menu.add(1, 16908338, 10, R.string.undo)
                .setAlphabeticShortcut(DateFormat.TIME_ZONE)
                .setOnMenuItemClickListener(this.mOnContextMenuItemClickListener)
                .setIcon(a.getDrawable(0))
                .setEnabled(this.mTextView.canUndo());
        menu.add(1, 16908339, 11, R.string.redo)
                .setAlphabeticShortcut(DateFormat.TIME_ZONE, 4097)
                .setOnMenuItemClickListener(this.mOnContextMenuItemClickListener)
                .setIcon(a.getDrawable(1))
                .setEnabled(this.mTextView.canRedo());
        menu.add(2, 16908320, 2, 17039363)
                .setAlphabeticShortcut(EpicenterTranslateClipReveal.StateProperty.TARGET_X)
                .setOnMenuItemClickListener(this.mOnContextMenuItemClickListener)
                .setIcon(a.getDrawable(2))
                .setEnabled(this.mTextView.canCut());
        menu.add(2, 16908321, 3, 17039361)
                .setAlphabeticShortcut('c')
                .setOnMenuItemClickListener(this.mOnContextMenuItemClickListener)
                .setIcon(a.getDrawable(3))
                .setEnabled(this.mTextView.canCopy());
        menu.add(2, 16908322, 4, 17039371)
                .setAlphabeticShortcut('v')
                .setEnabled(this.mTextView.canPaste())
                .setIcon(a.getDrawable(4))
                .setOnMenuItemClickListener(this.mOnContextMenuItemClickListener);
        menu.add(2, 16908337, menuItemOrderPasteAsPlainText, 17039385)
                .setAlphabeticShortcut('v', 4097)
                .setEnabled(this.mTextView.canPasteAsPlainText())
                .setIcon(a.getDrawable(4))
                .setOnMenuItemClickListener(this.mOnContextMenuItemClickListener);
        menu.add(2, 16908319, menuItemOrderSelectAll, 17039373)
                .setAlphabeticShortcut(DateFormat.AM_PM)
                .setEnabled(this.mTextView.canSelectAllText())
                .setIcon(a.getDrawable(5))
                .setOnMenuItemClickListener(this.mOnContextMenuItemClickListener);
        menu.add(3, 16908341, menuItemOrderShare, R.string.share)
                .setEnabled(this.mTextView.canShare())
                .setIcon(a.getDrawable(6))
                .setOnMenuItemClickListener(this.mOnContextMenuItemClickListener);
        menu.add(3, 16908355, keyboard, 17039386)
                .setEnabled(this.mTextView.canRequestAutofill())
                .setOnMenuItemClickListener(this.mOnContextMenuItemClickListener);
        if (this.SEP_VERSION.floatValue() >= 15.1d
                && !this.mTextView.hasPasswordTransformationMethod()
                && ViewRune.WIDGET_SSS_TRANSLATE_SUPPORTED
                && this.mTextView.getContext().canStartActivityForResult()) {
            menu.add(2, R.id.sssTranslate, menuItemOrderTranslate, R.string.sss_translate)
                    .setEnabled(this.mTextView.hasSelection())
                    .setOnMenuItemClickListener(this.mOnContextMenuItemClickListener);
        }
        this.mPreserveSelection = true;
        a.recycle();
        adjustIconSpacing(menu);
    }

    public void adjustIconSpacing(ContextMenu menu) {
        int width = -1;
        int height = -1;
        for (int i = 0; i < menu.size(); i++) {
            Drawable d = menu.getItem(i).getIcon();
            if (d != null) {
                width = Math.max(width, d.getIntrinsicWidth());
                height = Math.max(height, d.getIntrinsicHeight());
            }
        }
        if (width < 0 || height < 0) {
            return;
        }
        GradientDrawable paddingDrawable = new GradientDrawable();
        paddingDrawable.setSize(width, height);
        for (int i2 = 0; i2 < menu.size(); i2++) {
            MenuItem item = menu.getItem(i2);
            if (item.getIcon() == null) {
                item.setIcon(paddingDrawable);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public SuggestionSpan findEquivalentSuggestionSpan(SuggestionSpanInfo suggestionSpanInfo) {
        Editable editable = (Editable) this.mTextView.getText();
        if (editable.getSpanStart(suggestionSpanInfo.mSuggestionSpan) >= 0) {
            return suggestionSpanInfo.mSuggestionSpan;
        }
        SuggestionSpan[] suggestionSpans =
                (SuggestionSpan[])
                        editable.getSpans(
                                suggestionSpanInfo.mSpanStart,
                                suggestionSpanInfo.mSpanEnd,
                                SuggestionSpan.class);
        for (SuggestionSpan suggestionSpan : suggestionSpans) {
            int start = editable.getSpanStart(suggestionSpan);
            if (start == suggestionSpanInfo.mSpanStart) {
                int end = editable.getSpanEnd(suggestionSpan);
                if (end == suggestionSpanInfo.mSpanEnd
                        && suggestionSpan.equals(suggestionSpanInfo.mSuggestionSpan)) {
                    return suggestionSpan;
                }
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void replaceWithSuggestion(SuggestionInfo suggestionInfo) {
        int spanStart;
        String originalText;
        SuggestionSpan[] suggestionSpans;
        int length;
        SuggestionSpan targetSuggestionSpan =
                findEquivalentSuggestionSpan(suggestionInfo.mSuggestionSpanInfo);
        if (targetSuggestionSpan == null) {
            return;
        }
        Editable editable = (Editable) this.mTextView.getText();
        int spanStart2 = editable.getSpanStart(targetSuggestionSpan);
        int spanEnd = editable.getSpanEnd(targetSuggestionSpan);
        if (spanStart2 >= 0 && spanEnd > spanStart2) {
            String originalText2 = TextUtils.substring(editable, spanStart2, spanEnd);
            SuggestionSpan[] suggestionSpans2 =
                    (SuggestionSpan[]) editable.getSpans(spanStart2, spanEnd, SuggestionSpan.class);
            int length2 = suggestionSpans2.length;
            int[] suggestionSpansStarts = new int[length2];
            int[] suggestionSpansEnds = new int[length2];
            int[] suggestionSpansFlags = new int[length2];
            for (int i = 0; i < length2; i++) {
                SuggestionSpan suggestionSpan = suggestionSpans2[i];
                suggestionSpansStarts[i] = editable.getSpanStart(suggestionSpan);
                suggestionSpansEnds[i] = editable.getSpanEnd(suggestionSpan);
                suggestionSpansFlags[i] = editable.getSpanFlags(suggestionSpan);
                int suggestionSpanFlags = suggestionSpan.getFlags();
                if ((suggestionSpanFlags & 10) != 0) {
                    suggestionSpan.setFlags(suggestionSpanFlags & (-3) & (-9) & (-2));
                }
            }
            int i2 = suggestionInfo.mSuggestionStart;
            int suggestionEnd = suggestionInfo.mSuggestionEnd;
            String suggestion = suggestionInfo.mText.subSequence(i2, suggestionEnd).toString();
            this.mTextView.replaceText_internal(spanStart2, spanEnd, suggestion);
            String[] suggestions = targetSuggestionSpan.getSuggestions();
            suggestions[suggestionInfo.mSuggestionIndex] = originalText2;
            int lengthDelta = suggestion.length() - (spanEnd - spanStart2);
            int i3 = 0;
            while (i3 < length2) {
                Editable editable2 = editable;
                if (suggestionSpansStarts[i3] > spanStart2 || suggestionSpansEnds[i3] < spanEnd) {
                    spanStart = spanStart2;
                    originalText = originalText2;
                    suggestionSpans = suggestionSpans2;
                    length = length2;
                } else {
                    spanStart = spanStart2;
                    if (suggestionSpansEnds[i3] + lengthDelta > this.mTextView.length()) {
                        originalText = originalText2;
                        suggestionSpans = suggestionSpans2;
                        length = length2;
                    } else {
                        originalText = originalText2;
                        suggestionSpans = suggestionSpans2;
                        length = length2;
                        this.mTextView.setSpan_internal(
                                suggestionSpans2[i3],
                                suggestionSpansStarts[i3],
                                suggestionSpansEnds[i3] + lengthDelta,
                                suggestionSpansFlags[i3]);
                    }
                }
                i3++;
                editable = editable2;
                spanStart2 = spanStart;
                originalText2 = originalText;
                length2 = length;
                suggestionSpans2 = suggestionSpans;
            }
            int cursorPos = spanEnd + lengthDelta;
            if (cursorPos > this.mTextView.length()) {
                cursorPos = this.mTextView.length();
            }
            int newCursorPosition = cursorPos;
            this.mTextView.setCursorPosition_internal(newCursorPosition, newCursorPosition);
        }
    }

    private class SpanController implements SpanWatcher {
        private static final int DISPLAY_TIMEOUT_MS = 3000;
        private Runnable mHidePopup;
        private EasyEditPopupWindow mPopupWindow;

        private SpanController() {}

        private boolean isNonIntermediateSelectionSpan(Spannable text, Object span) {
            return (Selection.SELECTION_START == span || Selection.SELECTION_END == span)
                    && (text.getSpanFlags(span) & 512) == 0;
        }

        @Override // android.text.SpanWatcher
        public void onSpanAdded(Spannable text, Object span, int start, int end) {
            if (isNonIntermediateSelectionSpan(text, span)) {
                Editor.this.sendUpdateSelection();
                return;
            }
            if (span instanceof EasyEditSpan) {
                if (this.mPopupWindow == null) {
                    this.mPopupWindow = new EasyEditPopupWindow();
                    this.mHidePopup =
                            new Runnable() { // from class: android.widget.Editor.SpanController.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    SpanController.this.hide();
                                }
                            };
                }
                if (this.mPopupWindow.mEasyEditSpan != null) {
                    this.mPopupWindow.mEasyEditSpan.setDeleteEnabled(false);
                }
                this.mPopupWindow.setEasyEditSpan((EasyEditSpan) span);
                this.mPopupWindow.setOnDeleteListener(
                        new EasyEditDeleteListener() { // from class:
                                                       // android.widget.Editor.SpanController.2
                            @Override // android.widget.Editor.EasyEditDeleteListener
                            public void onDeleteClick(EasyEditSpan span2) {
                                Editable editable = (Editable) Editor.this.mTextView.getText();
                                int start2 = editable.getSpanStart(span2);
                                int end2 = editable.getSpanEnd(span2);
                                if (start2 >= 0 && end2 >= 0) {
                                    SpanController.this.sendEasySpanNotification(1, span2);
                                    Editor.this.mTextView.deleteText_internal(start2, end2);
                                }
                                editable.removeSpan(span2);
                            }
                        });
                if (Editor.this.mTextView.getWindowVisibility() != 0
                        || Editor.this.mTextView.getLayout() == null
                        || Editor.this.extractedTextModeWillBeStarted()) {
                    return;
                }
                this.mPopupWindow.show();
                Editor.this.mTextView.removeCallbacks(this.mHidePopup);
                Editor.this.mTextView.postDelayed(this.mHidePopup, 3000L);
            }
        }

        @Override // android.text.SpanWatcher
        public void onSpanRemoved(Spannable text, Object span, int start, int end) {
            if (isNonIntermediateSelectionSpan(text, span)) {
                Editor.this.sendUpdateSelection();
            } else if (this.mPopupWindow != null && span == this.mPopupWindow.mEasyEditSpan) {
                hide();
            }
        }

        @Override // android.text.SpanWatcher
        public void onSpanChanged(
                Spannable text,
                Object span,
                int previousStart,
                int previousEnd,
                int newStart,
                int newEnd) {
            if (isNonIntermediateSelectionSpan(text, span)) {
                Editor.this.sendUpdateSelection();
            } else if (this.mPopupWindow != null && (span instanceof EasyEditSpan)) {
                EasyEditSpan easyEditSpan = (EasyEditSpan) span;
                sendEasySpanNotification(2, easyEditSpan);
                text.removeSpan(easyEditSpan);
            }
        }

        public void hide() {
            if (this.mPopupWindow != null) {
                this.mPopupWindow.hide();
                Editor.this.mTextView.removeCallbacks(this.mHidePopup);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void sendEasySpanNotification(int textChangedType, EasyEditSpan span) {
            try {
                PendingIntent pendingIntent = span.getPendingIntent();
                if (pendingIntent != null) {
                    Intent intent = new Intent();
                    intent.putExtra(EasyEditSpan.EXTRA_TEXT_CHANGED_TYPE, textChangedType);
                    pendingIntent.send(Editor.this.mTextView.getContext(), 0, intent);
                }
            } catch (PendingIntent.CanceledException e) {
                Log.w("Editor", "PendingIntent for notification cannot be sent", e);
            }
        }
    }

    private class EasyEditPopupWindow extends PinnedPopupWindow implements View.OnClickListener {
        private static final int POPUP_TEXT_LAYOUT = 17367456;
        private TextView mDeleteTextView;
        private EasyEditSpan mEasyEditSpan;
        private EasyEditDeleteListener mOnDeleteListener;

        private EasyEditPopupWindow() {
            super();
        }

        @Override // android.widget.Editor.PinnedPopupWindow
        protected void createPopupWindow() {
            this.mPopupWindow =
                    new PopupWindow(
                            Editor.this.mTextView.getContext(), (AttributeSet) null, 16843464);
            this.mPopupWindow.setInputMethodMode(2);
            this.mPopupWindow.setClippingEnabled(true);
        }

        @Override // android.widget.Editor.PinnedPopupWindow
        protected void initContentView() {
            LinearLayout linearLayout = new LinearLayout(Editor.this.mTextView.getContext());
            linearLayout.setOrientation(0);
            this.mContentView = linearLayout;
            this.mContentView.setBackgroundResource(R.drawable.text_edit_side_paste_window);
            LayoutInflater inflater =
                    (LayoutInflater)
                            Editor.this
                                    .mTextView
                                    .getContext()
                                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ViewGroup.LayoutParams wrapContent = new ViewGroup.LayoutParams(-2, -2);
            this.mDeleteTextView = (TextView) inflater.inflate(17367456, (ViewGroup) null);
            this.mDeleteTextView.setLayoutParams(wrapContent);
            this.mDeleteTextView.setText(R.string.delete);
            this.mDeleteTextView.setOnClickListener(this);
            this.mContentView.addView(this.mDeleteTextView);
        }

        public void setEasyEditSpan(EasyEditSpan easyEditSpan) {
            this.mEasyEditSpan = easyEditSpan;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setOnDeleteListener(EasyEditDeleteListener listener) {
            this.mOnDeleteListener = listener;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (view == this.mDeleteTextView
                    && this.mEasyEditSpan != null
                    && this.mEasyEditSpan.isDeleteEnabled()
                    && this.mOnDeleteListener != null) {
                this.mOnDeleteListener.onDeleteClick(this.mEasyEditSpan);
            }
        }

        @Override // android.widget.Editor.PinnedPopupWindow
        public void hide() {
            if (this.mEasyEditSpan != null) {
                this.mEasyEditSpan.setDeleteEnabled(false);
            }
            this.mOnDeleteListener = null;
            super.hide();
        }

        @Override // android.widget.Editor.PinnedPopupWindow
        protected int getTextOffset() {
            Editable editable = (Editable) Editor.this.mTextView.getText();
            return editable.getSpanEnd(this.mEasyEditSpan);
        }

        @Override // android.widget.Editor.PinnedPopupWindow
        protected int getVerticalLocalPosition(int line) {
            Layout layout = Editor.this.mTextView.getLayout();
            return layout.getLineBottom(line, false);
        }

        @Override // android.widget.Editor.PinnedPopupWindow
        protected int clipVertically(int positionY) {
            return positionY;
        }
    }

    private class PositionListener implements ViewTreeObserver.OnPreDrawListener {
        private static final int MAXIMUM_NUMBER_OF_LISTENERS = 7;
        private boolean[] mCanMove;
        private final int mDelayTime;
        private int mNumberOfListeners;
        private boolean mPositionHasChanged;
        private TextViewPositionListener[] mPositionListeners;
        private int mPositionX;
        private int mPositionXOnScreen;
        private int mPositionY;
        private int mPositionYOnScreen;
        private boolean mScrollHasChanged;
        final int[] mTempCoords;
        private final Runnable mUpdatePosition;

        private PositionListener() {
            this.mPositionListeners = new TextViewPositionListener[7];
            this.mCanMove = new boolean[7];
            this.mPositionHasChanged = true;
            this.mTempCoords = new int[2];
            this.mDelayTime = 300;
            this.mUpdatePosition =
                    new Runnable() { // from class: android.widget.Editor.PositionListener.1
                        @Override // java.lang.Runnable
                        public void run() {
                            for (int i = 0; i < 7; i++) {
                                TextViewPositionListener positionListener =
                                        PositionListener.this.mPositionListeners[i];
                                if (positionListener != null
                                        && (positionListener instanceof HandleView)) {
                                    if ((positionListener instanceof SelectionHandleView)
                                            && Editor.this.mTextActionMode == null) {
                                        return;
                                    } else {
                                        positionListener.updatePosition(
                                                PositionListener.this.mPositionX,
                                                PositionListener.this.mPositionY,
                                                true,
                                                true);
                                    }
                                }
                            }
                        }
                    };
        }

        public void addSubscriber(TextViewPositionListener positionListener, boolean canMove) {
            if (this.mNumberOfListeners == 0) {
                updatePosition();
                ViewTreeObserver vto = Editor.this.mTextView.getViewTreeObserver();
                vto.addOnPreDrawListener(this);
            }
            int emptySlotIndex = -1;
            for (int i = 0; i < 7; i++) {
                TextViewPositionListener listener = this.mPositionListeners[i];
                if (listener == positionListener) {
                    return;
                }
                if (emptySlotIndex < 0 && listener == null) {
                    emptySlotIndex = i;
                }
            }
            if (emptySlotIndex == -1) {
                for (int i2 = 0; i2 < 7; i2++) {
                    this.mPositionListeners[i2] = null;
                }
                this.mNumberOfListeners = 0;
                emptySlotIndex = 0;
            }
            this.mPositionListeners[emptySlotIndex] = positionListener;
            this.mCanMove[emptySlotIndex] = canMove;
            this.mNumberOfListeners++;
        }

        public void removeSubscriber(TextViewPositionListener positionListener) {
            if (positionListener == null) {
                return;
            }
            int i = 0;
            while (true) {
                if (i >= 7) {
                    break;
                }
                if (this.mPositionListeners[i] != positionListener) {
                    i++;
                } else {
                    this.mPositionListeners[i] = null;
                    this.mNumberOfListeners--;
                    break;
                }
            }
            int i2 = this.mNumberOfListeners;
            if (i2 == 0) {
                ViewTreeObserver vto = Editor.this.mTextView.getViewTreeObserver();
                vto.removeOnPreDrawListener(this);
            }
        }

        public int getPositionX() {
            return this.mPositionX;
        }

        public int getPositionY() {
            return this.mPositionY;
        }

        public int getPositionXOnScreen() {
            return this.mPositionXOnScreen;
        }

        public int getPositionYOnScreen() {
            return this.mPositionYOnScreen;
        }

        @Override // android.view.ViewTreeObserver.OnPreDrawListener
        public boolean onPreDraw() {
            TextViewPositionListener positionListener;
            updatePosition();
            for (int i = 0; i < 7; i++) {
                if ((this.mPositionHasChanged || this.mScrollHasChanged || this.mCanMove[i])
                        && (positionListener = this.mPositionListeners[i]) != null) {
                    boolean isNeedToDelay = false;
                    if (this.mPositionHasChanged && (positionListener instanceof HandleView)) {
                        HandleView currentHandle = (HandleView) positionListener;
                        if (!currentHandle.isDragging()) {
                            currentHandle.dismiss();
                            if ((currentHandle instanceof InsertionHandleView)
                                    && Editor.this.mTextActionMode == null) {
                                ((InsertionHandleView) currentHandle).hideAfterDelay();
                            }
                            isNeedToDelay = true;
                        }
                    }
                    if (isNeedToDelay) {
                        Editor.this.mTextView.removeCallbacks(this.mUpdatePosition);
                        Editor.this.mTextView.postDelayed(this.mUpdatePosition, 300L);
                    } else if (!(positionListener instanceof SelectionHandleView)
                            || Editor.this.mTextActionMode != null) {
                        positionListener.updatePosition(
                                this.mPositionX,
                                this.mPositionY,
                                this.mPositionHasChanged,
                                this.mScrollHasChanged);
                    }
                }
            }
            this.mScrollHasChanged = false;
            return true;
        }

        private void updatePosition() {
            Editor.this.mTextView.getLocationInWindow(this.mTempCoords);
            this.mPositionHasChanged =
                    (this.mTempCoords[0] == this.mPositionX
                                    && this.mTempCoords[1] == this.mPositionY)
                            ? false
                            : true;
            this.mPositionX = this.mTempCoords[0];
            this.mPositionY = this.mTempCoords[1];
            Editor.this.mTextView.getLocationOnScreen(this.mTempCoords);
            this.mPositionXOnScreen = this.mTempCoords[0];
            this.mPositionYOnScreen = this.mTempCoords[1];
        }

        public void onScrollChanged() {
            this.mScrollHasChanged = true;
        }
    }

    private abstract class PinnedPopupWindow implements TextViewPositionListener {
        int mClippingLimitLeft;
        int mClippingLimitRight;
        protected ViewGroup mContentView;
        protected PopupWindow mPopupWindow;
        int mPositionX;
        int mPositionY;

        protected abstract int clipVertically(int i);

        protected abstract void createPopupWindow();

        protected abstract int getTextOffset();

        protected abstract int getVerticalLocalPosition(int i);

        protected abstract void initContentView();

        protected void setUp() {}

        public PinnedPopupWindow() {
            setUp();
            createPopupWindow();
            this.mPopupWindow.setWindowLayoutType(1005);
            this.mPopupWindow.setWidth(-2);
            this.mPopupWindow.setHeight(-2);
            initContentView();
            ViewGroup.LayoutParams wrapContent = new ViewGroup.LayoutParams(-2, -2);
            this.mContentView.setLayoutParams(wrapContent);
            this.mPopupWindow.setContentView(this.mContentView);
        }

        public void show() {
            Editor.this.getPositionListener().addSubscriber(this, false);
            computeLocalPosition();
            PositionListener positionListener = Editor.this.getPositionListener();
            updatePosition(positionListener.getPositionX(), positionListener.getPositionY());
        }

        protected void measureContent() {
            DisplayMetrics displayMetrics =
                    Editor.this.mTextView.getResources().getDisplayMetrics();
            this.mContentView.measure(
                    View.MeasureSpec.makeMeasureSpec(displayMetrics.widthPixels, Integer.MIN_VALUE),
                    View.MeasureSpec.makeMeasureSpec(
                            displayMetrics.heightPixels, Integer.MIN_VALUE));
        }

        private void computeLocalPosition() {
            measureContent();
            int width = this.mContentView.getMeasuredWidth();
            int offset = getTextOffset();
            int transformedOffset = Editor.this.mTextView.originalToTransformed(offset, 1);
            Layout layout = Editor.this.mTextView.getLayout();
            this.mPositionX =
                    (int) (layout.getPrimaryHorizontal(transformedOffset) - (width / 2.0f));
            this.mPositionX += Editor.this.mTextView.viewportToContentHorizontalOffset();
            int line = layout.getLineForOffset(transformedOffset);
            this.mPositionY = getVerticalLocalPosition(line);
            this.mPositionY += Editor.this.mTextView.viewportToContentVerticalOffset();
        }

        private void updatePosition(int parentPositionX, int parentPositionY) {
            int positionX = this.mPositionX + parentPositionX;
            int positionY = clipVertically(this.mPositionY + parentPositionY);
            DisplayMetrics displayMetrics =
                    Editor.this.mTextView.getResources().getDisplayMetrics();
            int width = this.mContentView.getMeasuredWidth();
            int positionX2 =
                    Math.max(
                            -this.mClippingLimitLeft,
                            Math.min(
                                    (displayMetrics.widthPixels - width) + this.mClippingLimitRight,
                                    positionX));
            if (isShowing()) {
                this.mPopupWindow.update(positionX2, positionY, -1, -1);
            } else {
                this.mPopupWindow.showAtLocation(Editor.this.mTextView, 0, positionX2, positionY);
            }
        }

        public void hide() {
            if (!isShowing()) {
                return;
            }
            this.mPopupWindow.dismiss();
            Editor.this.getPositionListener().removeSubscriber(this);
        }

        @Override // android.widget.Editor.TextViewPositionListener
        public void updatePosition(
                int parentPositionX,
                int parentPositionY,
                boolean parentPositionChanged,
                boolean parentScrolled) {
            if (isShowing() && Editor.this.isOffsetVisible(getTextOffset())) {
                if (parentScrolled) {
                    computeLocalPosition();
                }
                updatePosition(parentPositionX, parentPositionY);
                return;
            }
            hide();
        }

        public boolean isShowing() {
            return this.mPopupWindow.isShowing();
        }
    }

    private static final class SuggestionInfo {
        int mSuggestionEnd;
        int mSuggestionIndex;
        final SuggestionSpanInfo mSuggestionSpanInfo;
        int mSuggestionStart;
        final SpannableStringBuilder mText;

        private SuggestionInfo() {
            this.mSuggestionSpanInfo = new SuggestionSpanInfo();
            this.mText = new SpannableStringBuilder();
        }

        void clear() {
            this.mSuggestionSpanInfo.clear();
            this.mText.clear();
        }

        void setSpanInfo(SuggestionSpan span, int spanStart, int spanEnd) {
            this.mSuggestionSpanInfo.mSuggestionSpan = span;
            this.mSuggestionSpanInfo.mSpanStart = spanStart;
            this.mSuggestionSpanInfo.mSpanEnd = spanEnd;
        }
    }

    private static final class SuggestionSpanInfo {
        int mSpanEnd;
        int mSpanStart;
        SuggestionSpan mSuggestionSpan;

        private SuggestionSpanInfo() {}

        void clear() {
            this.mSuggestionSpan = null;
        }
    }

    private class SuggestionHelper {
        private final HashMap<SuggestionSpan, Integer> mSpansLengths;
        private final Comparator<SuggestionSpan> mSuggestionSpanComparator;

        private SuggestionHelper() {
            this.mSuggestionSpanComparator = new SuggestionSpanComparator();
            this.mSpansLengths = new HashMap<>();
        }

        private class SuggestionSpanComparator implements Comparator<SuggestionSpan> {
            private SuggestionSpanComparator() {}

            @Override // java.util.Comparator
            public int compare(SuggestionSpan span1, SuggestionSpan span2) {
                int flag1 = span1.getFlags();
                int flag2 = span2.getFlags();
                if (flag1 != flag2) {
                    int easy = compareFlag(1, flag1, flag2);
                    if (easy != 0) {
                        return easy;
                    }
                    int misspelled = compareFlag(2, flag1, flag2);
                    if (misspelled != 0) {
                        return misspelled;
                    }
                    int grammarError = compareFlag(8, flag1, flag2);
                    if (grammarError != 0) {
                        return grammarError;
                    }
                }
                return ((Integer) SuggestionHelper.this.mSpansLengths.get(span1)).intValue()
                        - ((Integer) SuggestionHelper.this.mSpansLengths.get(span2)).intValue();
            }

            private int compareFlag(int flagToCompare, int flags1, int flags2) {
                boolean hasFlag1 = (flags1 & flagToCompare) != 0;
                boolean hasFlag2 = (flags2 & flagToCompare) != 0;
                if (hasFlag1 == hasFlag2) {
                    return 0;
                }
                return hasFlag1 ? -1 : 1;
            }
        }

        private SuggestionSpan[] getSortedSuggestionSpans() {
            int pos = Editor.this.mTextView.getSelectionStart();
            Spannable spannable = (Spannable) Editor.this.mTextView.getText();
            SuggestionSpan[] suggestionSpans =
                    (SuggestionSpan[]) spannable.getSpans(pos, pos, SuggestionSpan.class);
            this.mSpansLengths.clear();
            for (SuggestionSpan suggestionSpan : suggestionSpans) {
                int start = spannable.getSpanStart(suggestionSpan);
                int end = spannable.getSpanEnd(suggestionSpan);
                this.mSpansLengths.put(suggestionSpan, Integer.valueOf(end - start));
            }
            Arrays.sort(suggestionSpans, this.mSuggestionSpanComparator);
            this.mSpansLengths.clear();
            return suggestionSpans;
        }

        public int getSuggestionInfo(
                SuggestionInfo[] suggestionInfos, SuggestionSpanInfo misspelledSpanInfo) {
            Spannable spannable;
            SuggestionSpan[] suggestionSpans;
            SuggestionInfo otherSuggestionInfo;
            SuggestionSpanInfo suggestionSpanInfo = misspelledSpanInfo;
            Spannable spannable2 = (Spannable) Editor.this.mTextView.getText();
            SuggestionSpan[] suggestionSpans2 = getSortedSuggestionSpans();
            int nbSpans = suggestionSpans2.length;
            SuggestionInfo suggestionInfo = null;
            if (nbSpans == 0) {
                return 0;
            }
            int numberOfSuggestions = 0;
            int length = suggestionSpans2.length;
            int i = 0;
            while (i < length) {
                SuggestionSpan suggestionSpan = suggestionSpans2[i];
                int spanStart = spannable2.getSpanStart(suggestionSpan);
                int spanEnd = spannable2.getSpanEnd(suggestionSpan);
                if (suggestionSpanInfo != null) {
                    if ((suggestionSpan.getFlags() & 10) != 0) {
                        suggestionSpanInfo.mSuggestionSpan = suggestionSpan;
                        suggestionSpanInfo.mSpanStart = spanStart;
                        suggestionSpanInfo.mSpanEnd = spanEnd;
                    } else {
                        misspelledSpanInfo.clear();
                    }
                }
                String[] suggestions = suggestionSpan.getSuggestions();
                int nbSuggestions = suggestions.length;
                int suggestionIndex = 0;
                while (suggestionIndex < nbSuggestions) {
                    String suggestion = suggestions[suggestionIndex];
                    int i2 = 0;
                    while (true) {
                        if (i2 < numberOfSuggestions) {
                            SuggestionInfo otherSuggestionInfo2 = suggestionInfos[i2];
                            spannable = spannable2;
                            Spannable spannable3 = otherSuggestionInfo2.mText;
                            if (!spannable3.toString().equals(suggestion)) {
                                suggestionSpans = suggestionSpans2;
                            } else {
                                int otherSpanStart =
                                        otherSuggestionInfo2.mSuggestionSpanInfo.mSpanStart;
                                suggestionSpans = suggestionSpans2;
                                int otherSpanEnd =
                                        otherSuggestionInfo2.mSuggestionSpanInfo.mSpanEnd;
                                if (spanStart == otherSpanStart && spanEnd == otherSpanEnd) {
                                    otherSuggestionInfo = null;
                                    break;
                                }
                            }
                            i2++;
                            spannable2 = spannable;
                            suggestionSpans2 = suggestionSpans;
                        } else {
                            spannable = spannable2;
                            suggestionSpans = suggestionSpans2;
                            SuggestionInfo suggestionInfo2 = suggestionInfos[numberOfSuggestions];
                            suggestionInfo2.setSpanInfo(suggestionSpan, spanStart, spanEnd);
                            suggestionInfo2.mSuggestionIndex = suggestionIndex;
                            otherSuggestionInfo = null;
                            suggestionInfo2.mSuggestionStart = 0;
                            suggestionInfo2.mSuggestionEnd = suggestion.length();
                            suggestionInfo2.mText.replace(
                                    0, suggestionInfo2.mText.length(), (CharSequence) suggestion);
                            numberOfSuggestions++;
                            if (numberOfSuggestions >= suggestionInfos.length) {
                                return numberOfSuggestions;
                            }
                        }
                    }
                    suggestionIndex++;
                    suggestionInfo = otherSuggestionInfo;
                    spannable2 = spannable;
                    suggestionSpans2 = suggestionSpans;
                }
                i++;
                suggestionSpanInfo = misspelledSpanInfo;
            }
            return numberOfSuggestions;
        }
    }

    private final class SuggestionsPopupWindow extends PinnedPopupWindow
            implements AdapterView.OnItemClickListener {
        private static final int MAX_NUMBER_SUGGESTIONS = 5;
        private static final String USER_DICTIONARY_EXTRA_LOCALE = "locale";
        private static final String USER_DICTIONARY_EXTRA_WORD = "word";
        private TextView mAddToDictionaryButton;
        private LinearLayout mButtonItemView;
        private int mContainerMarginTop;
        private int mContainerMarginWidth;
        private LinearLayout mContainerView;
        private Context mContext;
        private boolean mCursorWasVisibleBeforeSuggestions;
        private TextView mDeleteButton;
        private TextAppearanceSpan mHighlightSpan;
        private boolean mIsShowingUp;
        private final SuggestionSpanInfo mMisspelledSpanInfo;
        private int mNumberOfButtons;
        private int mNumberOfSuggestions;
        private SuggestionInfo[] mSuggestionInfos;
        private ListView mSuggestionListView;
        private SuggestionAdapter mSuggestionsAdapter;

        private class CustomPopupWindow extends PopupWindow {
            private CustomPopupWindow() {}

            @Override // android.widget.PopupWindow
            public void dismiss() {
                if (!isShowing()) {
                    return;
                }
                super.dismiss();
                Editor.this.getPositionListener().removeSubscriber(SuggestionsPopupWindow.this);
                ((Spannable) Editor.this.mTextView.getText())
                        .removeSpan(Editor.this.mSuggestionRangeSpan);
                Editor.this.mTextView.setCursorVisible(
                        SuggestionsPopupWindow.this.mCursorWasVisibleBeforeSuggestions);
                if (Editor.this.hasInsertionController()
                        && !Editor.this.extractedTextModeWillBeStarted()) {
                    Editor.this.getInsertionController().show();
                }
            }
        }

        public SuggestionsPopupWindow() {
            super();
            this.mIsShowingUp = false;
            this.mMisspelledSpanInfo = new SuggestionSpanInfo();
            this.mCursorWasVisibleBeforeSuggestions =
                    Editor.this.mTextView.isCursorVisibleFromAttr();
        }

        @Override // android.widget.Editor.PinnedPopupWindow
        protected void setUp() {
            this.mContext = applyDefaultTheme(Editor.this.mTextView.getContext());
            this.mHighlightSpan =
                    new TextAppearanceSpan(
                            this.mContext, Editor.this.mTextView.mTextEditSuggestionHighlightStyle);
        }

        private Context applyDefaultTheme(Context originalContext) {
            TypedArray a = originalContext.obtainStyledAttributes(new int[] {16844176});
            boolean isLightTheme = a.getBoolean(0, true);
            int themeId = isLightTheme ? 16974410 : 16974411;
            a.recycle();
            if (Editor.this.mIsThemeDeviceDefault) {
                boolean mIsNightMode =
                        (originalContext.getResources().getConfiguration().uiMode & 48) == 32;
                themeId = mIsNightMode ? 16974120 : 16974123;
            }
            return new ContextThemeWrapper(originalContext, themeId);
        }

        @Override // android.widget.Editor.PinnedPopupWindow
        protected void createPopupWindow() {
            this.mPopupWindow = new CustomPopupWindow();
            this.mPopupWindow.setInputMethodMode(2);
            this.mPopupWindow.setBackgroundDrawable(new ColorDrawable(0));
            this.mPopupWindow.setFocusable(true);
            this.mPopupWindow.setClippingEnabled(false);
        }

        @Override // android.widget.Editor.PinnedPopupWindow
        protected void initContentView() {
            LayoutInflater layoutInflater =
                    (LayoutInflater)
                            this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            byte b = 0;
            this.mContentView =
                    (ViewGroup)
                            layoutInflater.inflate(
                                    Editor.this.mTextView.mTextEditSuggestionContainerLayout,
                                    (ViewGroup) null);
            this.mContainerView =
                    (LinearLayout) this.mContentView.findViewById(R.id.suggestionWindowContainer);
            ViewGroup.MarginLayoutParams marginLayoutParams =
                    (ViewGroup.MarginLayoutParams) this.mContainerView.getLayoutParams();
            this.mContainerMarginWidth =
                    marginLayoutParams.leftMargin + marginLayoutParams.rightMargin;
            this.mContainerMarginTop = marginLayoutParams.topMargin;
            this.mClippingLimitLeft = marginLayoutParams.leftMargin;
            this.mClippingLimitRight = marginLayoutParams.rightMargin;
            this.mSuggestionListView =
                    (ListView) this.mContentView.findViewById(R.id.suggestionContainer);
            this.mSuggestionsAdapter = new SuggestionAdapter();
            this.mSuggestionListView.setAdapter((ListAdapter) this.mSuggestionsAdapter);
            this.mSuggestionListView.setOnItemClickListener(this);
            this.mSuggestionInfos = new SuggestionInfo[5];
            for (int i = 0; i < this.mSuggestionInfos.length; i++) {
                this.mSuggestionInfos[i] = new SuggestionInfo();
            }
            if (Editor.this.mIsThemeDeviceDefault) {
                this.mButtonItemView =
                        (LinearLayout)
                                layoutInflater.inflate(
                                        R.layout.tw_text_edit_suggestion_button_item,
                                        (ViewGroup) null);
                this.mAddToDictionaryButton =
                        (TextView) this.mButtonItemView.findViewById(R.id.addToDictionaryButton);
                this.mDeleteButton =
                        (TextView) this.mButtonItemView.findViewById(R.id.deleteButton);
            } else {
                this.mAddToDictionaryButton =
                        (TextView) this.mContentView.findViewById(R.id.addToDictionaryButton);
                this.mAddToDictionaryButton.setOnClickListener(
                        new View
                                .OnClickListener() { // from class:
                                                     // android.widget.Editor.SuggestionsPopupWindow.1
                            @Override // android.view.View.OnClickListener
                            public void onClick(View v) {
                                SuggestionSpan misspelledSpan =
                                        Editor.this.findEquivalentSuggestionSpan(
                                                SuggestionsPopupWindow.this.mMisspelledSpanInfo);
                                if (misspelledSpan == null) {
                                    return;
                                }
                                Editable editable = (Editable) Editor.this.mTextView.getText();
                                int spanStart = editable.getSpanStart(misspelledSpan);
                                int spanEnd = editable.getSpanEnd(misspelledSpan);
                                if (spanStart < 0 || spanEnd <= spanStart) {
                                    return;
                                }
                                String originalText =
                                        TextUtils.substring(editable, spanStart, spanEnd);
                                Intent intent = new Intent(Settings.ACTION_USER_DICTIONARY_INSERT);
                                intent.putExtra("word", originalText);
                                intent.putExtra(
                                        "locale",
                                        Editor.this.mTextView.getTextServicesLocale().toString());
                                intent.setFlags(intent.getFlags() | 268435456);
                                Editor.this.mTextView.startActivityAsTextOperationUserIfNecessary(
                                        intent);
                                editable.removeSpan(
                                        SuggestionsPopupWindow.this
                                                .mMisspelledSpanInfo
                                                .mSuggestionSpan);
                                Selection.setSelection(editable, spanEnd);
                                Editor.this.updateSpellCheckSpans(spanStart, spanEnd, false);
                                SuggestionsPopupWindow.this.hideWithCleanUp();
                            }
                        });
                this.mDeleteButton = (TextView) this.mContentView.findViewById(R.id.deleteButton);
                this.mDeleteButton.setOnClickListener(
                        new View
                                .OnClickListener() { // from class:
                                                     // android.widget.Editor.SuggestionsPopupWindow.2
                            @Override // android.view.View.OnClickListener
                            public void onClick(View v) {
                                Editable editable = (Editable) Editor.this.mTextView.getText();
                                int spanUnionStart =
                                        editable.getSpanStart(Editor.this.mSuggestionRangeSpan);
                                int spanUnionEnd =
                                        editable.getSpanEnd(Editor.this.mSuggestionRangeSpan);
                                if (spanUnionStart >= 0 && spanUnionEnd > spanUnionStart) {
                                    if (spanUnionEnd < editable.length()
                                            && Character.isSpaceChar(editable.charAt(spanUnionEnd))
                                            && (spanUnionStart == 0
                                                    || Character.isSpaceChar(
                                                            editable.charAt(spanUnionStart - 1)))) {
                                        spanUnionEnd++;
                                    }
                                    Editor.this.mTextView.deleteText_internal(
                                            spanUnionStart, spanUnionEnd);
                                }
                                SuggestionsPopupWindow.this.hideWithCleanUp();
                            }
                        });
            }
            this.mNumberOfButtons = 1;
        }

        public boolean isShowingUp() {
            return this.mIsShowingUp;
        }

        public void onParentLostFocus() {
            this.mIsShowingUp = false;
        }

        private class SuggestionAdapter extends BaseAdapter {
            private LayoutInflater mInflater;

            private SuggestionAdapter() {
                this.mInflater =
                        (LayoutInflater)
                                SuggestionsPopupWindow.this.mContext.getSystemService(
                                        Context.LAYOUT_INFLATER_SERVICE);
            }

            @Override // android.widget.Adapter
            public int getCount() {
                if (Editor.this.mIsThemeDeviceDefault
                        && SuggestionsPopupWindow.this.mNumberOfSuggestions != 0) {
                    return SuggestionsPopupWindow.this.mNumberOfSuggestions
                            + SuggestionsPopupWindow.this.mNumberOfButtons;
                }
                return SuggestionsPopupWindow.this.mNumberOfSuggestions;
            }

            @Override // android.widget.Adapter
            public Object getItem(int position) {
                return SuggestionsPopupWindow.this.mSuggestionInfos[position];
            }

            @Override // android.widget.Adapter
            public long getItemId(int position) {
                return position;
            }

            @Override // android.widget.Adapter
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView;
                if (Editor.this.mIsThemeDeviceDefault) {
                    if (SuggestionsPopupWindow.this.mNumberOfButtons == 1
                            && position == SuggestionsPopupWindow.this.mNumberOfSuggestions) {
                        return SuggestionsPopupWindow.this.mDeleteButton;
                    }
                    if (SuggestionsPopupWindow.this.mNumberOfButtons == 2
                            && position == SuggestionsPopupWindow.this.mNumberOfSuggestions) {
                        return SuggestionsPopupWindow.this.mAddToDictionaryButton;
                    }
                    if (SuggestionsPopupWindow.this.mNumberOfButtons == 2
                            && position > SuggestionsPopupWindow.this.mNumberOfSuggestions) {
                        return SuggestionsPopupWindow.this.mDeleteButton;
                    }
                    textView =
                            (TextView)
                                    this.mInflater.inflate(
                                            Editor.this.mTextView.mTextEditSuggestionItemLayout,
                                            parent,
                                            false);
                } else {
                    textView = (TextView) convertView;
                    if (textView == null) {
                        textView =
                                (TextView)
                                        this.mInflater.inflate(
                                                Editor.this.mTextView.mTextEditSuggestionItemLayout,
                                                parent,
                                                false);
                    }
                }
                SuggestionInfo suggestionInfo =
                        SuggestionsPopupWindow.this.mSuggestionInfos[position];
                textView.lambda$setTextAsync$0(suggestionInfo.mText);
                return textView;
            }
        }

        @Override // android.widget.Editor.PinnedPopupWindow
        public void show() {
            if (!(Editor.this.mTextView.getText() instanceof Editable)
                    || Editor.this.extractedTextModeWillBeStarted()) {
                return;
            }
            if (updateSuggestions()) {
                this.mCursorWasVisibleBeforeSuggestions =
                        Editor.this.mTextView.isCursorVisibleFromAttr();
                Editor.this.mTextView.setCursorVisible(false);
                this.mIsShowingUp = true;
                this.mSuggestionListView.requestLayout();
                super.show();
            }
            this.mSuggestionListView.setVisibility(this.mNumberOfSuggestions == 0 ? 8 : 0);
        }

        @Override // android.widget.Editor.PinnedPopupWindow
        protected void measureContent() {
            DisplayMetrics displayMetrics =
                    Editor.this.mTextView.getResources().getDisplayMetrics();
            int horizontalMeasure =
                    View.MeasureSpec.makeMeasureSpec(displayMetrics.widthPixels, Integer.MIN_VALUE);
            int verticalMeasure =
                    View.MeasureSpec.makeMeasureSpec(
                            displayMetrics.heightPixels, Integer.MIN_VALUE);
            int width = 0;
            View view = null;
            for (int i = 0; i < this.mNumberOfSuggestions; i++) {
                view = this.mSuggestionsAdapter.getView(i, view, this.mContentView);
                view.getLayoutParams().width = -2;
                view.measure(horizontalMeasure, verticalMeasure);
                width = Math.max(width, view.getMeasuredWidth());
            }
            if (this.mAddToDictionaryButton.getVisibility() != 8) {
                this.mAddToDictionaryButton.measure(horizontalMeasure, verticalMeasure);
                width = Math.max(width, this.mAddToDictionaryButton.getMeasuredWidth());
            }
            this.mDeleteButton.measure(horizontalMeasure, verticalMeasure);
            int width2 =
                    Math.max(width, this.mDeleteButton.getMeasuredWidth())
                            + this.mContainerView.getPaddingLeft()
                            + this.mContainerView.getPaddingRight()
                            + this.mContainerMarginWidth;
            this.mContentView.measure(
                    View.MeasureSpec.makeMeasureSpec(width2, 1073741824), verticalMeasure);
            Drawable popupBackground = this.mPopupWindow.getBackground();
            if (popupBackground != null) {
                if (Editor.this.mTempRect == null) {
                    Editor.this.mTempRect = new Rect();
                }
                popupBackground.getPadding(Editor.this.mTempRect);
                width2 += Editor.this.mTempRect.left + Editor.this.mTempRect.right;
            }
            this.mPopupWindow.setWidth(width2);
        }

        @Override // android.widget.Editor.PinnedPopupWindow
        protected int getTextOffset() {
            return (Editor.this.mTextView.getSelectionStart()
                            + Editor.this.mTextView.getSelectionStart())
                    / 2;
        }

        @Override // android.widget.Editor.PinnedPopupWindow
        protected int getVerticalLocalPosition(int line) {
            Layout layout = Editor.this.mTextView.getLayout();
            return layout.getLineBottom(line, false) - this.mContainerMarginTop;
        }

        @Override // android.widget.Editor.PinnedPopupWindow
        protected int clipVertically(int positionY) {
            int height = this.mContentView.getMeasuredHeight();
            DisplayMetrics displayMetrics =
                    Editor.this.mTextView.getResources().getDisplayMetrics();
            return Math.min(positionY, displayMetrics.heightPixels - height);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void hideWithCleanUp() {
            for (SuggestionInfo info : this.mSuggestionInfos) {
                info.clear();
            }
            this.mMisspelledSpanInfo.clear();
            hide();
        }

        private boolean updateSuggestions() {
            int underlineColor;
            Spannable spannable = (Spannable) Editor.this.mTextView.getText();
            this.mNumberOfSuggestions =
                    Editor.this.mSuggestionHelper.getSuggestionInfo(
                            this.mSuggestionInfos, this.mMisspelledSpanInfo);
            if (this.mNumberOfSuggestions == 0
                    && this.mMisspelledSpanInfo.mSuggestionSpan == null) {
                return false;
            }
            int spanUnionStart = Editor.this.mTextView.getText().length();
            int spanUnionEnd = 0;
            for (int i = 0; i < this.mNumberOfSuggestions; i++) {
                SuggestionSpanInfo spanInfo = this.mSuggestionInfos[i].mSuggestionSpanInfo;
                spanUnionStart = Math.min(spanUnionStart, spanInfo.mSpanStart);
                spanUnionEnd = Math.max(spanUnionEnd, spanInfo.mSpanEnd);
            }
            if (this.mMisspelledSpanInfo.mSuggestionSpan != null) {
                spanUnionStart = Math.min(spanUnionStart, this.mMisspelledSpanInfo.mSpanStart);
                spanUnionEnd = Math.max(spanUnionEnd, this.mMisspelledSpanInfo.mSpanEnd);
            }
            for (int i2 = 0; i2 < this.mNumberOfSuggestions; i2++) {
                try {
                    highlightTextDifferences(
                            this.mSuggestionInfos[i2], spanUnionStart, spanUnionEnd);
                } catch (IndexOutOfBoundsException e) {
                    SuggestionSpanInfo spanInfo2 = this.mSuggestionInfos[i2].mSuggestionSpanInfo;
                    Log.e(
                            "Editor",
                            "mNumberOfSuggestions = " + this.mNumberOfSuggestions + ", i = " + i2);
                    Log.e(
                            "Editor",
                            "spanInfo.mSpanStart : "
                                    + spanInfo2.mSpanStart
                                    + ", spanInfo.mSpanEnd : "
                                    + spanInfo2.mSpanEnd);
                    Log.e(
                            "Editor",
                            "spanUnionStart : "
                                    + spanUnionStart
                                    + ", spanUnionEnd : "
                                    + spanUnionEnd);
                    Log.e(
                            "Editor",
                            "mTextView.getText() = " + ((Object) Editor.this.mTextView.getText()));
                    if (this.mMisspelledSpanInfo.mSuggestionSpan != null) {
                        Log.e(
                                "Editor",
                                "mMisspelledSpanInfo.mSpanStart : "
                                        + this.mMisspelledSpanInfo.mSpanStart
                                        + ", mMisspelledSpanInfo.mSpanEnd : "
                                        + this.mMisspelledSpanInfo.mSpanEnd);
                    }
                    return false;
                }
            }
            int addToDictionaryButtonVisibility = 8;
            InputMethodManager imm = Editor.this.getInputMethodManager();
            if (this.mMisspelledSpanInfo.mSuggestionSpan != null
                    && imm != null
                    && !imm.isCurrentInputMethodAsSamsungKeyboard()
                    && this.mMisspelledSpanInfo.mSpanStart >= 0
                    && this.mMisspelledSpanInfo.mSpanEnd > this.mMisspelledSpanInfo.mSpanStart) {
                addToDictionaryButtonVisibility = 0;
            }
            this.mAddToDictionaryButton.setVisibility(addToDictionaryButtonVisibility);
            if (addToDictionaryButtonVisibility == 0) {
                this.mNumberOfButtons = 2;
            } else {
                this.mNumberOfButtons = 1;
            }
            if (Editor.this.mSuggestionRangeSpan == null) {
                Editor.this.mSuggestionRangeSpan = new SuggestionRangeSpan();
            }
            if (this.mNumberOfSuggestions != 0) {
                underlineColor =
                        this.mSuggestionInfos[0].mSuggestionSpanInfo.mSuggestionSpan
                                .getUnderlineColor();
            } else {
                underlineColor = this.mMisspelledSpanInfo.mSuggestionSpan.getUnderlineColor();
            }
            if (underlineColor == 0) {
                Editor.this.mSuggestionRangeSpan.setBackgroundColor(
                        Editor.this.mTextView.mHighlightColor);
            } else {
                int newAlpha = (int) (Color.alpha(underlineColor) * 0.4f);
                Editor.this.mSuggestionRangeSpan.setBackgroundColor(
                        (16777215 & underlineColor) + (newAlpha << 24));
            }
            boolean sendAccessibilityEvent = Editor.this.mTextView.isVisibleToAccessibility();
            CharSequence beforeText =
                    sendAccessibilityEvent ? new SpannedString(spannable, true) : null;
            spannable.setSpan(Editor.this.mSuggestionRangeSpan, spanUnionStart, spanUnionEnd, 33);
            if (sendAccessibilityEvent) {
                Editor.this.mTextView.sendAccessibilityEventTypeViewTextChanged(
                        beforeText, spanUnionStart, spanUnionEnd);
            }
            this.mSuggestionsAdapter.notifyDataSetChanged();
            return true;
        }

        private void highlightTextDifferences(
                SuggestionInfo suggestionInfo, int unionStart, int unionEnd) {
            Spannable text = (Spannable) Editor.this.mTextView.getText();
            int spanStart = suggestionInfo.mSuggestionSpanInfo.mSpanStart;
            int spanEnd = suggestionInfo.mSuggestionSpanInfo.mSpanEnd;
            suggestionInfo.mSuggestionStart = spanStart - unionStart;
            suggestionInfo.mSuggestionEnd =
                    suggestionInfo.mSuggestionStart + suggestionInfo.mText.length();
            suggestionInfo.mText.setSpan(this.mHighlightSpan, 0, suggestionInfo.mText.length(), 33);
            String textAsString = text.toString();
            suggestionInfo.mText.insert(
                    0, (CharSequence) textAsString.substring(unionStart, spanStart));
            suggestionInfo.mText.append((CharSequence) textAsString.substring(spanEnd, unionEnd));
        }

        @Override // android.widget.AdapterView.OnItemClickListener
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (Editor.this.mIsThemeDeviceDefault) {
                if (this.mNumberOfButtons == 1 && position == this.mNumberOfSuggestions) {
                    clickButtons(this.mDeleteButton);
                    return;
                }
                if (this.mNumberOfButtons == 2 && position == this.mNumberOfSuggestions) {
                    clickButtons(this.mAddToDictionaryButton);
                    return;
                }
                if (this.mNumberOfButtons == 2 && position > this.mNumberOfSuggestions) {
                    clickButtons(this.mDeleteButton);
                    return;
                }
                SuggestionInfo suggestionInfo = this.mSuggestionInfos[position];
                Editor.this.replaceWithSuggestion(suggestionInfo);
                hideWithCleanUp();
                return;
            }
            SuggestionInfo suggestionInfo2 = this.mSuggestionInfos[position];
            Editor.this.replaceWithSuggestion(suggestionInfo2);
            hideWithCleanUp();
        }

        private void clickButtons(View view) {
            if (view == this.mAddToDictionaryButton) {
                SuggestionSpan misspelledSpan =
                        Editor.this.findEquivalentSuggestionSpan(this.mMisspelledSpanInfo);
                if (misspelledSpan == null) {
                    return;
                }
                Editable editable = (Editable) Editor.this.mTextView.getText();
                int spanStart = editable.getSpanStart(misspelledSpan);
                int spanEnd = editable.getSpanEnd(misspelledSpan);
                if (spanStart < 0 || spanEnd <= spanStart) {
                    return;
                }
                String originalText = TextUtils.substring(editable, spanStart, spanEnd);
                Intent intent = new Intent(Settings.ACTION_USER_DICTIONARY_INSERT);
                intent.putExtra("word", originalText);
                intent.putExtra("locale", Editor.this.mTextView.getTextServicesLocale().toString());
                intent.setFlags(intent.getFlags() | 268435456);
                Editor.this.mTextView.getContext().startActivity(intent);
                editable.removeSpan(this.mMisspelledSpanInfo.mSuggestionSpan);
                Selection.setSelection(editable, spanEnd);
                Editor.this.updateSpellCheckSpans(spanStart, spanEnd, false);
                hideWithCleanUp();
                return;
            }
            if (view == this.mDeleteButton) {
                Editable editable2 = (Editable) Editor.this.mTextView.getText();
                int spanUnionStart = editable2.getSpanStart(Editor.this.mSuggestionRangeSpan);
                int spanUnionEnd = editable2.getSpanEnd(Editor.this.mSuggestionRangeSpan);
                if (spanUnionStart >= 0 && spanUnionEnd > spanUnionStart) {
                    if (spanUnionEnd < editable2.length()
                            && Character.isSpaceChar(editable2.charAt(spanUnionEnd))
                            && (spanUnionStart == 0
                                    || Character.isSpaceChar(
                                            editable2.charAt(spanUnionStart - 1)))) {
                        spanUnionEnd++;
                    }
                    Editor.this.mTextView.deleteText_internal(spanUnionStart, spanUnionEnd);
                }
                hideWithCleanUp();
            }
        }
    }

    public class AssistantCallbackHelper {
        private final Map<MenuItem, View.OnClickListener> mAssistClickHandlers = new HashMap();
        private final SelectionActionModeHelper mHelper;
        private TextClassification mPrevTextClassification;

        public AssistantCallbackHelper(SelectionActionModeHelper helper) {
            this.mHelper = helper;
        }

        public void clearCallbackHandlers() {
            this.mAssistClickHandlers.clear();
        }

        public View.OnClickListener getOnClickListener(MenuItem key) {
            return this.mAssistClickHandlers.get(key);
        }

        public void updateAssistMenuItems(Menu menu, MenuItem.OnMenuItemClickListener listener) {
            TextClassification textClassification = this.mHelper.getTextClassification();
            if (this.mPrevTextClassification == textClassification) {
                return;
            }
            clearAssistMenuItems(menu);
            if (textClassification == null || !shouldEnableAssistMenuItems()) {
                return;
            }
            if (!textClassification.getActions().isEmpty()) {
                addAssistMenuItem(
                                menu,
                                textClassification.getActions().get(0),
                                16908353,
                                1,
                                2,
                                listener)
                        .setIntent(textClassification.getIntent());
            } else if (hasLegacyAssistItem(textClassification)) {
                MenuItem item =
                        menu.add(16908353, 16908353, 1, textClassification.getLabel())
                                .setIcon(textClassification.getIcon())
                                .setIntent(textClassification.getIntent());
                item.setShowAsAction(2);
                this.mAssistClickHandlers.put(
                        item,
                        TextClassification.createIntentOnClickListener(
                                TextClassification.createPendingIntent(
                                        Editor.this.mTextView.getContext(),
                                        textClassification.getIntent(),
                                        createAssistMenuItemPendingIntentRequestCode())));
            }
            int count = textClassification.getActions().size();
            for (int i = 1; i < count; i++) {
                addAssistMenuItem(
                        menu, textClassification.getActions().get(i), 0, (i + 50) - 1, 0, listener);
            }
            this.mPrevTextClassification = textClassification;
        }

        private MenuItem addAssistMenuItem(
                Menu menu,
                RemoteAction action,
                int itemId,
                int order,
                int showAsAction,
                MenuItem.OnMenuItemClickListener listener) {
            MenuItem item =
                    menu.add(16908353, itemId, order, action.getTitle())
                            .setContentDescription(action.getContentDescription());
            if (action.shouldShowIcon()) {
                item.setIcon(action.getIcon().loadDrawable(Editor.this.mTextView.getContext()));
            }
            item.setShowAsAction(showAsAction);
            this.mAssistClickHandlers.put(
                    item, TextClassification.createIntentOnClickListener(action.getActionIntent()));
            Editor.this.mA11ySmartActions.addAction(action);
            if (listener != null) {
                item.setOnMenuItemClickListener(listener);
            }
            return item;
        }

        private void clearAssistMenuItems(Menu menu) {
            int i = 0;
            while (i < menu.size()) {
                MenuItem menuItem = menu.getItem(i);
                if (menuItem.getGroupId() == 16908353) {
                    menu.removeItem(menuItem.getItemId());
                } else {
                    i++;
                }
            }
            Editor.this.mA11ySmartActions.reset();
        }

        private boolean hasLegacyAssistItem(TextClassification classification) {
            return ((classification.getIcon() == null
                                    && TextUtils.isEmpty(classification.getLabel()))
                            || (classification.getIntent() == null
                                    && classification.getOnClickListener() == null))
                    ? false
                    : true;
        }

        private boolean shouldEnableAssistMenuItems() {
            return Editor.this.mTextView.isDeviceProvisioned()
                    && TextClassificationManager.getSettings(Editor.this.mTextView.getContext())
                            .isSmartTextShareEnabled();
        }

        private int createAssistMenuItemPendingIntentRequestCode() {
            if (Editor.this.mTextView.hasSelection()) {
                return Editor.this
                        .mTextView
                        .getText()
                        .subSequence(
                                Editor.this.mTextView.getSelectionStart(),
                                Editor.this.mTextView.getSelectionEnd())
                        .hashCode();
            }
            return 0;
        }

        public boolean onAssistMenuItemClicked(MenuItem assistMenuItem) {
            Intent intent;
            Preconditions.checkArgument(assistMenuItem.getGroupId() == 16908353);
            TextClassification textClassification =
                    Editor.this.getSelectionActionModeHelper().getTextClassification();
            if (!shouldEnableAssistMenuItems() || textClassification == null) {
                return true;
            }
            View.OnClickListener onClickListener = getOnClickListener(assistMenuItem);
            if (onClickListener == null && (intent = assistMenuItem.getIntent()) != null) {
                onClickListener =
                        TextClassification.createIntentOnClickListener(
                                TextClassification.createPendingIntent(
                                        Editor.this.mTextView.getContext(),
                                        intent,
                                        createAssistMenuItemPendingIntentRequestCode()));
            }
            if (onClickListener != null) {
                onClickListener.onClick(Editor.this.mTextView);
                Editor.this.lambda$startActionModeInternal$0();
            }
            return true;
        }
    }

    private class TextActionModeCallback extends ActionMode.Callback2 {
        private final int mHandleHeight;
        private final boolean mHasSelection;
        private final AssistantCallbackHelper mHelper;
        private final Path mSelectionPath = new Path();
        private final RectF mSelectionBounds = new RectF();

        TextActionModeCallback(int mode) {
            this.mHelper =
                    Editor.this
                    .new AssistantCallbackHelper(Editor.this.getSelectionActionModeHelper());
            this.mHasSelection = mode == 0 || (Editor.this.mTextIsSelectable && mode == 2);
            if (this.mHasSelection) {
                SelectionModifierCursorController selectionController =
                        Editor.this.getSelectionController();
                if (selectionController.mStartHandle == null) {
                    Editor.this.loadHandleDrawables(false);
                    selectionController.initHandles();
                    selectionController.hide();
                }
                this.mHandleHeight =
                        Math.max(
                                Editor.this.mSelectHandleLeft.getMinimumHeight(),
                                Editor.this.mSelectHandleRight.getMinimumHeight());
                return;
            }
            InsertionPointCursorController insertionController =
                    Editor.this.getInsertionController();
            if (insertionController != null) {
                insertionController.getHandle();
                this.mHandleHeight = Editor.this.mSelectHandleCenter.getMinimumHeight();
            } else {
                this.mHandleHeight = 0;
            }
        }

        @Override // android.view.ActionMode.Callback
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            this.mHelper.clearCallbackHandlers();
            mode.setTitle((CharSequence) null);
            mode.setSubtitle((CharSequence) null);
            mode.setTitleOptionalHint(true);
            populateMenuWithItems(menu);
            AccessibilityManager manager =
                    (AccessibilityManager)
                            Editor.this
                                    .mTextView
                                    .getContext()
                                    .getSystemService(Context.ACCESSIBILITY_SERVICE);
            if (manager != null && manager.isEnabled()) {
                AccessibilityEvent e = AccessibilityEvent.obtain(16384);
                e.getText().clear();
                e.getText()
                        .add(
                                Editor.this
                                        .mTextView
                                        .getContext()
                                        .getText(R.string.copy_and_paste_toolbar));
                e.setPackageName(Editor.this.mTextView.getContext().getPackageName());
                manager.sendAccessibilityEvent(e);
            }
            ActionMode.Callback customCallback = getCustomCallback();
            if (customCallback != null && !customCallback.onCreateActionMode(mode, menu)) {
                Selection.setSelection(
                        (Spannable) Editor.this.mTextView.getText(),
                        Editor.this.mTextView.getSelectionEnd());
                return false;
            }
            if (Editor.this.mTextView.canProcessText()) {
                menu.add(0, R.id.manage_apps, 101, R.string.manage_apps).setShowAsAction(8);
                Editor.this.mProcessTextIntentActionsHandler.onInitializeMenu(menu);
            }
            if (!menu.hasVisibleItems() && mode.getCustomView() == null) {
                return false;
            }
            if (this.mHasSelection && !Editor.this.mTextView.hasTransientState()) {
                Editor.this.mTextView.setHasTransientState(true);
            }
            return true;
        }

        private ActionMode.Callback getCustomCallback() {
            if (this.mHasSelection) {
                return Editor.this.mCustomSelectionActionModeCallback;
            }
            return Editor.this.mCustomInsertionActionModeCallback;
        }

        private void populateMenuWithItems(Menu menu) {
            String selected;
            if (Editor.this.mTextView.canUndo()) {
                menu.add(0, 16908338, 11, R.string.undo)
                        .setAlphabeticShortcut(DateFormat.TIME_ZONE)
                        .setShowAsAction(2);
            }
            if (Editor.this.mTextView.canRedo()) {
                menu.add(0, 16908339, 12, R.string.redo)
                        .setAlphabeticShortcut('y')
                        .setShowAsAction(2);
            }
            if (Editor.this.mTextView.canCut()
                    && !Editor.this.mTextView.isClipboardDisallowedByKnox()) {
                menu.add(0, 16908320, 4, 17039363)
                        .setAlphabeticShortcut(EpicenterTranslateClipReveal.StateProperty.TARGET_X)
                        .setShowAsAction(2);
            }
            if (Editor.this.mTextView.canCopy()
                    && !Editor.this.mTextView.isClipboardDisallowedByKnox()) {
                menu.add(0, 16908321, 5, 17039361).setAlphabeticShortcut('c').setShowAsAction(2);
            }
            if (Editor.this.mTextView.canPaste()
                    && !Editor.this.mTextView.isClipboardDisallowedByKnox()) {
                menu.add(0, 16908322, 6, 17039371).setAlphabeticShortcut('v').setShowAsAction(2);
            }
            if (Editor.this.mTextView.canShare()) {
                menu.add(0, 16908341, 10, R.string.share).setShowAsAction(1);
            }
            if (Editor.this.SEP_VERSION.floatValue() < 15.1d
                    && Editor.this.mTextView.canClipboard()) {
                menu.add(0, R.id.clipboard, 19, R.string.tw_clipboard_title_text)
                        .setIcon(
                                Editor.this
                                        .mTextView
                                        .getContext()
                                        .getResources()
                                        .getDrawable(
                                                R.drawable.tw_floating_popup_button_ic_clipboard))
                        .setShowAsAction(1);
            }
            if (Editor.this.mTextView.canRequestAutofill()
                    && ((selected = Editor.this.mTextView.getSelectedText()) == null
                            || selected.isEmpty())) {
                menu.add(0, 16908355, 15, 17039386).setShowAsAction(0);
            }
            if (Editor.this.mTextView.canPasteAsPlainText()
                    && !Editor.this.mTextView.isClipboardDisallowedByKnox()) {
                menu.add(0, 16908337, 7, 17039385).setShowAsAction(1);
            }
            updateSelectAllItem(menu);
            if (Editor.this.mIsThemeDeviceDefault) {
                if (Editor.this.mTextView.canWebSearch()) {
                    menu.add(0, R.id.websearch, 13, R.string.websearch).setShowAsAction(1);
                }
            } else {
                updateReplaceItem(menu);
            }
            if (Editor.this.mTextView.canAssist()) {
                this.mHelper.updateAssistMenuItems(menu, null);
            }
            if (Editor.this.SEP_VERSION.floatValue() < 15.1d
                    && ViewRune.SUPPORT_EAGLE_EYE
                    && Editor.this.mTextView.canScanText()) {
                menu.add(0, R.id.scanText, 18, R.string.scan_text).setShowAsAction(1);
            }
            if (Editor.this.SEP_VERSION.floatValue() < 15.1d
                    && Editor.this.mTextView.canHBDTranslate()) {
                menu.add(0, R.id.hbdTranslate, 17, R.string.hbd_translate).setShowAsAction(1);
            }
            if (Editor.this.SEP_VERSION.floatValue() >= 15.1d
                    && !Editor.this.mTextView.hasPasswordTransformationMethod()
                    && Editor.this.mTextView.hasSelection()
                    && ViewRune.WIDGET_SSS_TRANSLATE_SUPPORTED
                    && Editor.this.mTextView.getContext().canStartActivityForResult()) {
                menu.add(0, R.id.sssTranslate, 8, R.string.sss_translate).setShowAsAction(1);
            }
            if (ViewRune.SUPPORT_WRITING_TOOLKIT
                    && !Editor.this.mTextView.isDisableWritingToolkit()
                    && Editor.this.mShowSoftInputOnFocus
                    && Editor.this.mTextView.getContext().canStartActivityForResult()
                    && !Editor.this.mTextView.isWritingToolkitDisallowedByKnox()) {
                menu.add(0, R.id.writing_toolkit, 0, R.string.writing_toolkit).setShowAsAction(2);
            }
        }

        @Override // android.view.ActionMode.Callback
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            updateSelectAllItem(menu);
            if (!Editor.this.mIsThemeDeviceDefault) {
                updateReplaceItem(menu);
            }
            if (Editor.this.mTextView.canAssist()) {
                this.mHelper.updateAssistMenuItems(menu, null);
            }
            ActionMode.Callback customCallback = getCustomCallback();
            if (customCallback != null) {
                return customCallback.onPrepareActionMode(mode, menu);
            }
            return true;
        }

        private void updateSelectAllItem(Menu menu) {
            boolean canSelectAll = Editor.this.mTextView.canSelectAllText();
            boolean selectAllItemExists = menu.findItem(16908319) != null;
            if (canSelectAll && !selectAllItemExists) {
                menu.add(0, 16908319, 9, 17039373).setShowAsAction(1);
            } else if (!canSelectAll && selectAllItemExists) {
                menu.removeItem(16908319);
            }
        }

        private void updateReplaceItem(Menu menu) {
            boolean canReplace =
                    Editor.this.mTextView.isSuggestionsEnabled()
                            && Editor.this.shouldOfferToShowSuggestions();
            boolean replaceItemExists = menu.findItem(16908340) != null;
            if (canReplace && !replaceItemExists) {
                menu.add(0, 16908340, 14, R.string.replace).setShowAsAction(1);
            } else if (!canReplace && replaceItemExists) {
                menu.removeItem(16908340);
            }
        }

        @Override // android.view.ActionMode.Callback
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            Editor.this
                    .getSelectionActionModeHelper()
                    .onSelectionAction(item.getItemId(), item.getTitle().toString());
            if (Editor.this.mProcessTextIntentActionsHandler.performMenuItemAction(item)) {
                return true;
            }
            ActionMode.Callback customCallback = getCustomCallback();
            if (customCallback != null && customCallback.onActionItemClicked(mode, item)) {
                return true;
            }
            if (item.getGroupId() == 16908353 && this.mHelper.onAssistMenuItemClicked(item)) {
                return true;
            }
            if (item.getItemId() == 16908341 && (mode instanceof FloatingActionMode)) {
                Point touch = ((FloatingActionMode) mode).getContentRectOnScreen();
                Editor.this.mTextView.startChooserPopupActivity(touch, false);
                return true;
            }
            return Editor.this.mTextView.onTextContextMenuItem(item.getItemId());
        }

        @Override // android.view.ActionMode.Callback
        public void onDestroyActionMode(ActionMode mode) {
            Editor.this.getSelectionActionModeHelper().onDestroyActionMode();
            Editor.this.mTextActionMode = null;
            ActionMode.Callback customCallback = getCustomCallback();
            if (customCallback != null) {
                customCallback.onDestroyActionMode(mode);
            }
            if (!Editor.this.mPreserveSelection) {
                Selection.setSelection(
                        (Spannable) Editor.this.mTextView.getText(),
                        Editor.this.mTextView.getSelectionEnd());
            }
            if (Editor.this.mSelectionModifierCursorController != null) {
                Editor.this.mSelectionModifierCursorController.hide();
            }
            if (Editor.this.mInsertionPointCursorController != null
                    && Editor.this.mTextView.getText() != null
                    && Editor.this.mTextView.getText().length() == 0) {
                Editor.this.mInsertionPointCursorController.hide();
                Editor.this.mToggleActionMode = false;
            }
            this.mHelper.clearCallbackHandlers();
            Editor.this.mRequestingLinkActionMode = false;
        }

        @Override // android.view.ActionMode.Callback2
        public void onGetContentRect(ActionMode mode, View view, Rect outRect) {
            if (!view.equals(Editor.this.mTextView) || Editor.this.getActiveLayout() == null) {
                super.onGetContentRect(mode, view, outRect);
                return;
            }
            int selectionStart = Editor.this.mTextView.getSelectionStartTransformed();
            int selectionEnd = Editor.this.mTextView.getSelectionEndTransformed();
            Layout layout = Editor.this.getActiveLayout();
            if (selectionStart != selectionEnd) {
                this.mSelectionPath.reset();
                layout.getSelectionPath(selectionStart, selectionEnd, this.mSelectionPath);
                this.mSelectionPath.computeBounds(this.mSelectionBounds, true);
                this.mSelectionBounds.bottom += this.mHandleHeight;
            } else {
                int line = layout.getLineForOffset(selectionStart);
                float primaryHorizontal =
                        Editor.this.clampHorizontalPosition(
                                null, layout.getPrimaryHorizontal(selectionEnd));
                this.mSelectionBounds.set(
                        primaryHorizontal,
                        layout.getLineTop(line),
                        primaryHorizontal,
                        layout.getLineBottom(line) + this.mHandleHeight);
            }
            int textHorizontalOffset = Editor.this.mTextView.viewportToContentHorizontalOffset();
            int textVerticalOffset = Editor.this.mTextView.viewportToContentVerticalOffset();
            outRect.set(
                    (int) Math.floor(this.mSelectionBounds.left + textHorizontalOffset),
                    (int) Math.floor(this.mSelectionBounds.top + textVerticalOffset),
                    (int) Math.ceil(this.mSelectionBounds.right + textHorizontalOffset),
                    (int) Math.ceil(this.mSelectionBounds.bottom + textVerticalOffset));
        }
    }

    private final class CursorAnchorInfoNotifier implements TextViewPositionListener {
        final CursorAnchorInfo.Builder mCursorAnchorInfoBuilder;
        final Matrix mViewToScreenMatrix;

        private CursorAnchorInfoNotifier() {
            this.mCursorAnchorInfoBuilder = new CursorAnchorInfo.Builder();
            this.mViewToScreenMatrix = new Matrix();
        }

        @Override // android.widget.Editor.TextViewPositionListener
        public void updatePosition(
                int parentPositionX,
                int parentPositionY,
                boolean parentPositionChanged,
                boolean parentScrolled) {
            InputMethodManager imm;
            CursorAnchorInfo cursorAnchorInfo;
            InputMethodState ims = Editor.this.mInputMethodState;
            if (ims != null
                    && ims.mBatchEditNesting <= 0
                    && (imm = Editor.this.getInputMethodManager()) != null
                    && imm.hasActiveInputConnection(Editor.this.mTextView)
                    && (ims.mUpdateCursorAnchorInfoMode & 3) != 0
                    && (cursorAnchorInfo =
                                    Editor.this.mTextView.getCursorAnchorInfo(
                                            ims.mUpdateCursorAnchorInfoFilter,
                                            this.mCursorAnchorInfoBuilder,
                                            this.mViewToScreenMatrix))
                            != null) {
                imm.updateCursorAnchorInfo(Editor.this.mTextView, cursorAnchorInfo);
                Editor.this.mInputMethodState.mUpdateCursorAnchorInfoMode &= -2;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class MagnifierMotionAnimator {
        private static final long DURATION = 100;
        private float mAnimationCurrentX;
        private float mAnimationCurrentY;
        private float mAnimationStartX;
        private float mAnimationStartY;
        private final ValueAnimator mAnimator;
        private float mLastX;
        private float mLastY;
        private final Magnifier mMagnifier;
        private boolean mMagnifierIsShowing;

        private MagnifierMotionAnimator(Magnifier magnifier) {
            this.mMagnifier = magnifier;
            this.mAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
            this.mAnimator.setDuration(DURATION);
            this.mAnimator.setInterpolator(new LinearInterpolator());
            this.mAnimator.addUpdateListener(
                    new ValueAnimator
                            .AnimatorUpdateListener() { // from class:
                                                        // android.widget.Editor$MagnifierMotionAnimator$$ExternalSyntheticLambda0
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                            Editor.MagnifierMotionAnimator.this.lambda$new$0(valueAnimator);
                        }
                    });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$0(ValueAnimator animation) {
            this.mAnimationCurrentX =
                    this.mAnimationStartX
                            + ((this.mLastX - this.mAnimationStartX)
                                    * animation.getAnimatedFraction());
            this.mAnimationCurrentY =
                    this.mAnimationStartY
                            + ((this.mLastY - this.mAnimationStartY)
                                    * animation.getAnimatedFraction());
            this.mMagnifier.show(this.mAnimationCurrentX, this.mAnimationCurrentY);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void show(float x, float y) {
            boolean startNewAnimation = this.mMagnifierIsShowing && y != this.mLastY;
            if (startNewAnimation) {
                if (this.mAnimator.isRunning()) {
                    this.mAnimator.cancel();
                    this.mAnimationStartX = this.mAnimationCurrentX;
                    this.mAnimationStartY = this.mAnimationCurrentY;
                } else {
                    this.mAnimationStartX = this.mLastX;
                    this.mAnimationStartY = this.mLastY;
                }
                this.mAnimator.start();
            } else if (!this.mAnimator.isRunning()) {
                this.mMagnifier.show(x, y);
            }
            this.mLastX = x;
            this.mLastY = y;
            this.mMagnifierIsShowing = true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void update() {
            this.mMagnifier.update();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void dismiss() {
            this.mMagnifier.dismiss();
            this.mAnimator.cancel();
            this.mMagnifierIsShowing = false;
        }
    }

    public abstract class HandleView extends View implements TextViewPositionListener {
        private static final int HISTORY_SIZE = 5;
        private static final float MAGNIFYING_FACTOR = 1.5f;
        private static final int TOUCH_UP_FILTER_DELAY_AFTER = 150;
        private static final int TOUCH_UP_FILTER_DELAY_BEFORE = 350;
        private TypeEvaluator<Rect> CHANGE_SIZE_EVALUATOR;
        private final PopupWindow mContainer;
        private int mContentsViewOffset;
        private float mCurrentDragInitialTouchRawX;
        private long mDownTime;
        protected Drawable mDrawable;
        protected Drawable mDrawableLtr;
        protected Drawable mDrawableRtl;
        protected int mFirstParentY;
        private VelocityTracker mHandleVelocityTracker;
        private ObjectAnimator mHideAnimator;
        protected int mHorizontalGravity;
        protected float mHorizontalOffset;
        protected int mHotspotX;
        private final int mIdealFingerToCursorOffset;
        private final float mIdealVerticalOffset;
        private boolean mIsDragging;
        private boolean mIsHideAnimating;
        private boolean mIsRestoring;
        private boolean mIsShowAnimating;
        private boolean mIsSwitching;
        protected boolean mIsVerticalScrolled;
        protected int mLastParentX;
        protected int mLastParentXOnScreen;
        protected int mLastParentY;
        protected int mLastParentYOnScreen;
        private int mMinSize;
        private int mNumberPreviousOffsets;
        private final PathInterpolator mPathInterpolator;
        private boolean mPositionHasChanged;
        private int mPositionX;
        private int mPositionY;
        protected int mPrevLine;
        protected int mPreviousLineTouched;
        protected int mPreviousOffset;
        private int mPreviousOffsetIndex;
        private final int[] mPreviousOffsets;
        private final long[] mPreviousOffsetsTimes;
        private ObjectAnimator mShowAnimator;
        private float mTextViewScaleX;
        private float mTextViewScaleY;
        protected float mTouchOffsetY;
        protected float mTouchToWindowOffsetX;
        protected float mTouchToWindowOffsetY;
        protected float mVerticalOffset;
        protected int mVerticalScrolledYOffset;

        public abstract int getCurrentCursorOffset();

        protected abstract int getHorizontalGravity(boolean z);

        protected abstract int getHotspotX(Drawable drawable, boolean z);

        protected abstract int getMagnifierHandleTrigger();

        protected abstract void updatePosition(float f, float f2, boolean z);

        protected abstract void updateSelection(int i);

        private HandleView(Drawable drawableLtr, Drawable drawableRtl, int id) {
            super(Editor.this.mTextView.getContext());
            this.mPreviousOffset = -1;
            this.mPositionHasChanged = true;
            this.mPrevLine = -1;
            this.mPreviousLineTouched = -1;
            this.mCurrentDragInitialTouchRawX = -1.0f;
            this.mDownTime = 0L;
            this.mPreviousOffsetsTimes = new long[5];
            this.mPreviousOffsets = new int[5];
            this.mPreviousOffsetIndex = 0;
            this.mNumberPreviousOffsets = 0;
            this.mIsRestoring = false;
            this.CHANGE_SIZE_EVALUATOR =
                    new TypeEvaluator<Rect>() { // from class: android.widget.Editor.HandleView.1
                        @Override // android.animation.TypeEvaluator
                        public Rect evaluate(float fraction, Rect startRect, Rect targetRect) {
                            int startWidth = startRect.width();
                            int startHeight = startRect.height();
                            int targetWidth = targetRect.width();
                            int targetHeight = targetRect.height();
                            int w = Math.round((targetWidth - startWidth) * fraction) + startWidth;
                            int h =
                                    Math.round((targetHeight - startHeight) * fraction)
                                            + startHeight;
                            return HandleView.this.getDrawableBounds(w, h);
                        }
                    };
            this.mPathInterpolator = new PathInterpolator(0.25f, 0.46f, 0.45f, 1.0f);
            setId(id);
            LinearLayout contentHolder = new LinearLayout(Editor.this.mTextView.getContext());
            this.mContainer =
                    new PopupWindow(
                            Editor.this.mTextView.getContext(), (AttributeSet) null, 16843464);
            this.mContainer.setSplitTouchEnabled(true);
            this.mContainer.setClippingEnabled(false);
            this.mContainer.setWindowLayoutType(1002);
            contentHolder.addView(this);
            this.mContainer.setContentView(contentHolder);
            setDrawables(drawableLtr, drawableRtl);
            this.mMinSize =
                    Editor.this
                            .mTextView
                            .getContext()
                            .getResources()
                            .getDimensionPixelSize(R.dimen.text_handle_min_size);
            this.mContainer.setWidth((int) Math.ceil(this.mDrawable.getIntrinsicWidth() * 1.5f));
            this.mContainer.setHeight((int) Math.ceil(this.mDrawable.getIntrinsicHeight() * 1.5f));
            int handleHeight = getPreferredHeight();
            this.mTouchOffsetY = handleHeight * (-0.3f);
            int distance =
                    AppGlobals.getIntCoreSetting(WidgetFlags.KEY_FINGER_TO_CURSOR_DISTANCE, -1);
            if (distance < 0 || distance > 100) {
                this.mIdealVerticalOffset = handleHeight * 0.7f;
                this.mIdealFingerToCursorOffset =
                        (int) (this.mIdealVerticalOffset - this.mTouchOffsetY);
            } else {
                this.mIdealFingerToCursorOffset =
                        (int)
                                TypedValue.applyDimension(
                                        1,
                                        distance,
                                        Editor.this
                                                .mTextView
                                                .getContext()
                                                .getResources()
                                                .getDisplayMetrics());
                this.mIdealVerticalOffset = this.mIdealFingerToCursorOffset + this.mTouchOffsetY;
            }
        }

        public float getIdealVerticalOffset() {
            return this.mIdealVerticalOffset;
        }

        final int getIdealFingerToCursorOffset() {
            return this.mIdealFingerToCursorOffset;
        }

        void setDrawables(Drawable drawableLtr, Drawable drawableRtl) {
            this.mDrawableLtr = drawableLtr;
            this.mDrawableRtl = drawableRtl;
            updateDrawable(true);
        }

        protected void updateDrawable(boolean updateDrawableWhenDragging) {
            Layout layout;
            if ((!updateDrawableWhenDragging && this.mIsDragging)
                    || (layout = Editor.this.mTextView.getLayout()) == null) {
                return;
            }
            int offset = getCurrentCursorOffset();
            boolean isRtlCharAtOffset = isAtRtlRun(layout, offset);
            Drawable oldDrawable = this.mDrawable;
            this.mDrawable = isRtlCharAtOffset ? this.mDrawableRtl : this.mDrawableLtr;
            this.mHotspotX = getHotspotX(this.mDrawable, isRtlCharAtOffset);
            this.mHorizontalGravity = getHorizontalGravity(isRtlCharAtOffset);
            ((LinearLayout) this.mContainer.getContentView()).setGravity(this.mHorizontalGravity);
            int positionX =
                    getCursorHorizontalPosition(layout, offset)
                            + getCursorOffset()
                            + Editor.this.mTextView.viewportToContentHorizontalOffset()
                            + Editor.this.getPositionListener().getPositionX();
            if (isScreenOut(positionX, isRtlCharAtOffset)) {
                boolean isRtlCharAtOffset2 = !isRtlCharAtOffset;
                this.mDrawable = isRtlCharAtOffset2 ? this.mDrawableRtl : this.mDrawableLtr;
                this.mHotspotX = getHotspotX(this.mDrawable, isRtlCharAtOffset2);
                this.mHorizontalGravity = getHorizontalGravity(isRtlCharAtOffset2);
                ((LinearLayout) this.mContainer.getContentView())
                        .setGravity(this.mHorizontalGravity);
            }
            if (oldDrawable != this.mDrawable && isShowing()) {
                this.mPositionX =
                        ((getCursorHorizontalPosition(layout, offset) - this.mHotspotX)
                                        - getHorizontalOffset())
                                + getCursorOffset();
                this.mPositionX += Editor.this.mTextView.viewportToContentHorizontalOffset();
                this.mPositionHasChanged = true;
                this.mIsSwitching = true;
                this.mContainer.dismiss();
                updatePosition(this.mLastParentX, this.mLastParentY, false, false);
                postInvalidate();
                this.mIsSwitching = false;
            }
        }

        private void startTouchUpFilter(int offset) {
            this.mNumberPreviousOffsets = 0;
            addPositionToTouchUpFilter(offset);
        }

        private void addPositionToTouchUpFilter(int offset) {
            this.mPreviousOffsetIndex = (this.mPreviousOffsetIndex + 1) % 5;
            this.mPreviousOffsets[this.mPreviousOffsetIndex] = offset;
            this.mPreviousOffsetsTimes[this.mPreviousOffsetIndex] = SystemClock.uptimeMillis();
            this.mNumberPreviousOffsets++;
        }

        private void filterOnTouchUp(boolean fromTouchScreen) {
            long now = SystemClock.uptimeMillis();
            int i = 0;
            int index = this.mPreviousOffsetIndex;
            int iMax = Math.min(this.mNumberPreviousOffsets, 5);
            while (i < iMax && now - this.mPreviousOffsetsTimes[index] < 150) {
                i++;
                index = ((this.mPreviousOffsetIndex - i) + 5) % 5;
            }
            if (i > 0 && i < iMax && now - this.mPreviousOffsetsTimes[index] > 350) {
                positionAtCursorOffset(this.mPreviousOffsets[index], false, fromTouchScreen);
            }
        }

        public boolean offsetHasBeenChanged() {
            return this.mNumberPreviousOffsets > 1;
        }

        @Override // android.view.View
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int width = getPreferredWidth();
            int height = getPreferredHeight();
            if (this.mIsDragging || this.mIsRestoring) {
                width = (int) Math.ceil(this.mDrawable.getIntrinsicWidth() * 1.5f);
                height = (int) Math.ceil(this.mDrawable.getIntrinsicHeight() * 1.5f);
            }
            setMeasuredDimension(width, height);
        }

        @Override // android.view.View
        public void invalidate() {
            super.invalidate();
            if (!this.mIsShowAnimating && !this.mIsHideAnimating && isShowing()) {
                positionAtCursorOffset(getCurrentCursorOffset(), true, false);
            }
        }

        protected final int getPreferredWidth() {
            return Math.max(this.mDrawable.getIntrinsicWidth(), this.mMinSize);
        }

        protected final int getPreferredHeight() {
            return Math.max(this.mDrawable.getIntrinsicHeight(), this.mMinSize);
        }

        public void show() {
            if (!isShowing() || this.mIsHideAnimating) {
                Editor.this.getPositionListener().addSubscriber(this, true);
                this.mPreviousOffset = -1;
                positionAtCursorOffset(getCurrentCursorOffset(), false, false);
            }
        }

        protected void dismiss() {
            this.mIsDragging = false;
            this.mIsRestoring = false;
            if (this.mHideAnimator == null) {
                this.mHideAnimator = getHideAnimator();
            }
            if (isShowing()) {
                if (!this.mHideAnimator.isStarted()) {
                    this.mHideAnimator.start();
                }
            } else {
                this.mContainer.dismiss();
            }
            onDetached();
        }

        public void hide() {
            dismiss();
            Editor.this.getPositionListener().removeSubscriber(this);
        }

        public boolean isShowing() {
            return this.mContainer.isShowing();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean shouldShow() {
            if (this.mIsDragging) {
                return true;
            }
            if (Editor.this.mTextView.isInBatchEditMode()) {
                return false;
            }
            return Editor.this.mTextView.isPositionVisible(
                    this.mPositionX + this.mHotspotX + getHorizontalOffset(), this.mPositionY);
        }

        private void setVisible(boolean visible) {
            this.mContainer.getContentView().setVisibility(visible ? 0 : 4);
        }

        protected boolean isAtRtlRun(Layout layout, int offset) {
            int transformedOffset = Editor.this.mTextView.originalToTransformed(offset, 1);
            return layout.isRtlCharAt(transformedOffset);
        }

        public float getHorizontal(Layout layout, int offset) {
            int transformedOffset = Editor.this.mTextView.originalToTransformed(offset, 1);
            return layout.getPrimaryHorizontal(transformedOffset);
        }

        public int getLineForOffset(Layout layout, int offset) {
            int transformedOffset = Editor.this.mTextView.originalToTransformed(offset, 1);
            return layout.getLineForOffset(transformedOffset);
        }

        protected int getOffsetAtCoordinate(Layout layout, int line, float x) {
            return Editor.this.mTextView.getOffsetAtCoordinate(line, x);
        }

        protected void positionAtCursorOffset(
                int offset, boolean forceUpdatePosition, boolean fromTouchScreen) {
            if (Editor.this.mTextView.getLayout() == null) {
                Editor.this.prepareCursorControllers();
                return;
            }
            Layout layout = Editor.this.getActiveLayout();
            boolean offsetChanged = offset != this.mPreviousOffset;
            if (offsetChanged || forceUpdatePosition) {
                if (offsetChanged) {
                    updateSelection(offset);
                    if (fromTouchScreen && Editor.this.mHapticTextHandleEnabled) {
                        Editor.this.mTextView.performHapticFeedback(
                                HapticFeedbackConstants.semGetVibrationIndex(41));
                    }
                    addPositionToTouchUpFilter(offset);
                }
                int line = getLineForOffset(layout, offset);
                this.mPrevLine = line;
                if (!this.mIsDragging && !this.mIsRestoring) {
                    this.mPositionX =
                            ((getCursorHorizontalPosition(layout, offset) - this.mHotspotX)
                                            - getHorizontalOffset())
                                    + getCursorOffset();
                    this.mPositionY = layout.getLineBottom(line, false);
                    this.mPositionX += Editor.this.mTextView.viewportToContentHorizontalOffset();
                    this.mPositionY += Editor.this.mTextView.viewportToContentVerticalOffset();
                }
                this.mPreviousOffset = offset;
                this.mPositionHasChanged = true;
            }
        }

        int getCursorHorizontalPosition(Layout layout, int offset) {
            return (int) (getHorizontal(layout, offset) - 0.5f);
        }

        @Override // android.widget.Editor.TextViewPositionListener
        public void updatePosition(
                int parentPositionX,
                int parentPositionY,
                boolean parentPositionChanged,
                boolean parentScrolled) {
            positionAtCursorOffset(getCurrentCursorOffset(), parentScrolled, false);
            if (parentPositionChanged || this.mPositionHasChanged) {
                if (this.mIsDragging) {
                    if (parentPositionX != this.mLastParentX
                            || parentPositionY != this.mLastParentY) {
                        this.mTouchToWindowOffsetX += parentPositionX - this.mLastParentX;
                        this.mTouchToWindowOffsetY += parentPositionY - this.mLastParentY;
                        this.mLastParentX = parentPositionX;
                        this.mLastParentY = parentPositionY;
                    }
                    onHandleMoved();
                }
                if (!this.mIsDragging && !this.mIsRestoring) {
                    if (shouldShow()) {
                        int[] pts = {
                            this.mPositionX + this.mHotspotX + getHorizontalOffset(),
                            this.mPositionY
                        };
                        Editor.this.mTextView.transformFromViewToWindowSpace(pts);
                        pts[0] = pts[0] - (this.mHotspotX + getHorizontalOffset());
                        if (isShowing() && !this.mIsHideAnimating) {
                            this.mContainer.update(pts[0], pts[1], -1, -1);
                        } else if (isValid()) {
                            this.mContainer.showAtLocation(
                                    Editor.this.mTextView, 0, pts[0], pts[1]);
                            if (this.mShowAnimator == null) {
                                this.mShowAnimator = getShowAnimator();
                            }
                            if (!this.mShowAnimator.isStarted() && !this.mIsSwitching) {
                                this.mShowAnimator.start();
                            }
                        }
                    } else if (isShowing()) {
                        dismiss();
                    }
                }
                this.mPositionHasChanged = false;
            }
        }

        @Override // android.view.View
        protected void onDraw(Canvas c) {
            int drawWidth = this.mDrawable.getIntrinsicWidth();
            int left = getHorizontalOffset() - this.mContentsViewOffset;
            if (!this.mIsDragging
                    && !this.mIsRestoring
                    && !this.mIsShowAnimating
                    && !this.mIsHideAnimating) {
                this.mDrawable.setBounds(
                        left, 0, left + drawWidth, this.mDrawable.getIntrinsicHeight());
            }
            this.mDrawable.draw(c);
        }

        protected int getHorizontalOffset() {
            int left;
            int width = getPreferredWidth();
            int drawWidth = this.mDrawable.getIntrinsicWidth();
            int popupWidth = this.mContainer.getWidth();
            switch (this.mHorizontalGravity) {
                case 3:
                    left = 0;
                    this.mContentsViewOffset = 0;
                    break;
                case 4:
                default:
                    left = (width - drawWidth) / 2;
                    this.mContentsViewOffset = (popupWidth - width) / 2;
                    break;
                case 5:
                    left = width - drawWidth;
                    this.mContentsViewOffset = popupWidth - width;
                    break;
            }
            return this.mContentsViewOffset + left;
        }

        protected int getCursorOffset() {
            return 0;
        }

        private boolean tooLargeTextForMagnifier() {
            if (Editor.this.mNewMagnifierEnabled) {
                Layout layout = Editor.this.mTextView.getLayout();
                int line = getLineForOffset(layout, getCurrentCursorOffset());
                return layout.getLineBottom(line, false) - layout.getLineTop(line)
                        >= Editor.this.mMaxLineHeightForMagnifier;
            }
            float magnifierContentHeight =
                    Math.round(
                            Editor.this.mMagnifierAnimator.mMagnifier.getHeight()
                                    / Editor.this.mMagnifierAnimator.mMagnifier.getZoom());
            Paint.FontMetrics fontMetrics = Editor.this.mTextView.getPaint().getFontMetrics();
            float glyphHeight = fontMetrics.descent - fontMetrics.ascent;
            return this.mTextViewScaleY * glyphHeight > magnifierContentHeight;
        }

        private boolean checkForTransforms() {
            if (Editor.this.mMagnifierAnimator.mMagnifierIsShowing) {
                return true;
            }
            if (Editor.this.mTextView.getRotation() != 0.0f
                    || Editor.this.mTextView.getRotationX() != 0.0f
                    || Editor.this.mTextView.getRotationY() != 0.0f) {
                return false;
            }
            this.mTextViewScaleX = Editor.this.mTextView.getScaleX();
            this.mTextViewScaleY = Editor.this.mTextView.getScaleY();
            for (ViewParent viewParent = Editor.this.mTextView.getParent();
                    viewParent != null;
                    viewParent = viewParent.getParent()) {
                if (viewParent instanceof View) {
                    View view = (View) viewParent;
                    if (view.getRotation() != 0.0f
                            || view.getRotationX() != 0.0f
                            || view.getRotationY() != 0.0f) {
                        return false;
                    }
                    this.mTextViewScaleX *= view.getScaleX();
                    this.mTextViewScaleY *= view.getScaleY();
                }
            }
            return true;
        }

        /* JADX WARN: Removed duplicated region for block: B:26:0x00c5  */
        /* JADX WARN: Removed duplicated region for block: B:29:0x00c7  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private boolean obtainMagnifierShowCoordinates(
                android.view.MotionEvent r17, android.graphics.PointF r18) {
            /*
                Method dump skipped, instructions count: 292
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " android.widget.Editor.HandleView.obtainMagnifierShowCoordinates(android.view.MotionEvent,"
                        + " android.graphics.PointF):boolean");
        }

        private boolean handleOverlapsMagnifier(HandleView handle, Rect magnifierRect) {
            PopupWindow window = handle.mContainer;
            if (!window.hasDecorView()) {
                return false;
            }
            Rect handleRect =
                    new Rect(
                            window.getDecorViewLayoutParams().x,
                            window.getDecorViewLayoutParams().y,
                            window.getDecorViewLayoutParams().x
                                    + window.getContentView().getWidth(),
                            window.getDecorViewLayoutParams().y
                                    + window.getContentView().getHeight());
            return Rect.intersects(handleRect, magnifierRect);
        }

        private HandleView getOtherSelectionHandle() {
            SelectionModifierCursorController controller = Editor.this.getSelectionController();
            if (controller == null || !controller.isActive()) {
                return null;
            }
            if (controller.mStartHandle != this) {
                return controller.mStartHandle;
            }
            return controller.mEndHandle;
        }

        private void updateHandlesVisibility() {
            Point magnifierTopLeft = Editor.this.mMagnifierAnimator.mMagnifier.getPosition();
            if (magnifierTopLeft == null) {
                return;
            }
            Rect magnifierRect =
                    new Rect(
                            magnifierTopLeft.x,
                            magnifierTopLeft.y,
                            magnifierTopLeft.x
                                    + Editor.this.mMagnifierAnimator.mMagnifier.getWidth(),
                            magnifierTopLeft.y
                                    + Editor.this.mMagnifierAnimator.mMagnifier.getHeight());
            setVisible(
                    (handleOverlapsMagnifier(this, magnifierRect)
                                    || Editor.this.mDrawCursorOnMagnifier)
                            ? false
                            : true);
            HandleView otherHandle = getOtherSelectionHandle();
            if (otherHandle != null) {
                otherHandle.setVisible(true ^ handleOverlapsMagnifier(otherHandle, magnifierRect));
            }
        }

        protected final void updateMagnifier(MotionEvent event) {
            if (Editor.this.getMagnifierAnimator() == null) {
                return;
            }
            PointF showPosInView = new PointF();
            boolean shouldShow =
                    checkForTransforms()
                            && !tooLargeTextForMagnifier()
                            && obtainMagnifierShowCoordinates(event, showPosInView)
                            && Editor.this.mTextView.showUIForTouchScreen();
            if (shouldShow) {
                Editor.this.mRenderCursorRegardlessTiming = true;
                Editor.this.mTextView.invalidateCursorPath();
                Editor.this.suspendBlink();
                if (Editor.this.mNewMagnifierEnabled) {
                    Layout layout = Editor.this.mTextView.getLayout();
                    int line = getLineForOffset(layout, getCurrentCursorOffset());
                    int lineLeft = (int) layout.getLineLeft(line);
                    int lineLeft2 =
                            lineLeft
                                    + (Editor.this.mTextView.getTotalPaddingLeft()
                                            - Editor.this.mTextView.getScrollX());
                    int lineRight = (int) layout.getLineRight(line);
                    Editor.this.mDrawCursorOnMagnifier =
                            showPosInView.x < ((float) (lineLeft2 + (-20)))
                                    || showPosInView.x
                                            > ((float)
                                                    ((lineRight
                                                                    + (Editor.this.mTextView
                                                                                    .getTotalPaddingLeft()
                                                                            - Editor.this.mTextView
                                                                                    .getScrollX()))
                                                            + 20));
                    Editor.this.mMagnifierAnimator.mMagnifier.setDrawCursor(
                            Editor.this.mDrawCursorOnMagnifier, Editor.this.mDrawableForCursor);
                    boolean cursorVisible = Editor.this.mCursorVisible;
                    Editor.this.mCursorVisible = true ^ Editor.this.mDrawCursorOnMagnifier;
                    if (Editor.this.mCursorVisible && !cursorVisible) {
                        Editor.this.updateCursorPosition();
                    }
                    int lineHeight = layout.getLineBottom(line, false) - layout.getLineTop(line);
                    float zoom = Editor.this.mInitialZoom;
                    if (lineHeight < Editor.this.mMinLineHeightForMagnifier) {
                        zoom = (Editor.this.mMinLineHeightForMagnifier * zoom) / lineHeight;
                    }
                    Editor.this.mMagnifierAnimator.mMagnifier.updateSourceFactors(lineHeight, zoom);
                    Editor.this.mMagnifierAnimator.mMagnifier.show(
                            showPosInView.x, showPosInView.y);
                } else {
                    Editor.this.mMagnifierAnimator.show(showPosInView.x, showPosInView.y);
                }
                updateHandlesVisibility();
                return;
            }
            dismissMagnifier();
        }

        protected final void dismissMagnifier() {
            if (Editor.this.mMagnifierAnimator != null) {
                Editor.this.mMagnifierAnimator.dismiss();
                Editor.this.mRenderCursorRegardlessTiming = false;
                Editor.this.mDrawCursorOnMagnifier = false;
                if (!Editor.this.mCursorVisible) {
                    Editor.this.mCursorVisible = true;
                    Editor.this.mTextView.invalidate();
                }
                Editor.this.resumeBlink();
                setVisible(true);
                HandleView otherHandle = getOtherSelectionHandle();
                if (otherHandle != null) {
                    otherHandle.setVisible(true);
                }
            }
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Code restructure failed: missing block: B:32:0x01df, code lost:

           return true;
        */
        @Override // android.view.View
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public boolean onTouchEvent(android.view.MotionEvent r23) {
            /*
                Method dump skipped, instructions count: 492
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " android.widget.Editor.HandleView.onTouchEvent(android.view.MotionEvent):boolean");
        }

        public boolean isDragging() {
            return this.mIsDragging;
        }

        void onHandleMoved() {}

        public void onDetached() {
            dismissMagnifier();
        }

        @Override // android.view.View
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            setSystemGestureExclusionRects(Collections.singletonList(new Rect(0, 0, w, h)));
        }

        protected void removeHiderCallback() {}

        protected void hideAfterDelay() {}

        protected boolean isScreenOut(int x, boolean atRtl) {
            return false;
        }

        private boolean isScrollChanged(MotionEvent event) {
            Rect viewPortRect = new Rect();
            Editor.this.mTextView.getWindowVisibleDisplayFrame(viewPortRect);
            return event.getRawY() > ((float) viewPortRect.bottom)
                    || event.getRawY() < ((float) viewPortRect.top);
        }

        protected void updatePositionDuringDragging(int x, int y) {
            int[] textViewCoords = new int[2];
            getLocationInWindow(textViewCoords);
            textViewCoords[0] = textViewCoords[0] + Editor.this.mTextView.getMeasuredWidth();
            textViewCoords[1] = textViewCoords[1] + Editor.this.mTextView.getMeasuredHeight();
            this.mPositionX =
                    Math.max(
                            (-this.mHotspotX) - getHorizontalOffset(),
                            Math.min(x, textViewCoords[0]));
            this.mPositionY = Math.max(0, Math.min(y, textViewCoords[1]));
            int[] pts = {this.mPositionX + this.mHotspotX + getHorizontalOffset(), this.mPositionY};
            Editor.this.mTextView.transformFromViewToWindowSpace(pts);
            pts[0] = pts[0] - (this.mHotspotX + getHorizontalOffset());
            if (isShowing()) {
                this.mContainer.update(pts[0], pts[1], -1, -1);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Rect getDrawableBounds(int width, int height) {
            int left = getHorizontalOffset() - this.mContentsViewOffset;
            if (this.mIsDragging || this.mIsRestoring) {
                left += this.mContentsViewOffset;
            }
            int hotspot = getHotspotX(this.mDrawable, this.mDrawable == this.mDrawableRtl);
            int offset = 0;
            switch (this.mHorizontalGravity) {
                case 1:
                    offset = width / 2;
                    break;
                case 3:
                    offset = width / 4;
                    break;
                case 5:
                    offset = (width * 3) / 4;
                    break;
            }
            return new Rect(
                    left - (offset - hotspot), 0, (left - (offset - hotspot)) + width, height);
        }

        private ObjectAnimator getChangeSizeAnimator(Rect startRect, final Rect targetRect) {
            ObjectAnimator changeSizeAnimator =
                    ObjectAnimator.ofObject(
                            this.mDrawable,
                            "bounds",
                            this.CHANGE_SIZE_EVALUATOR,
                            startRect,
                            targetRect);
            changeSizeAnimator.addUpdateListener(
                    new ValueAnimator
                            .AnimatorUpdateListener() { // from class:
                                                        // android.widget.Editor.HandleView.2
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public void onAnimationUpdate(ValueAnimator animation) {
                            HandleView.this.invalidate();
                        }
                    });
            changeSizeAnimator.addListener(
                    new AnimatorListenerAdapter() { // from class:
                                                    // android.widget.Editor.HandleView.3
                        @Override // android.animation.Animator.AnimatorListener
                        public void onAnimationStart(Animator animation, boolean isReverse) {
                            HandleView.this.requestLayout();
                        }

                        @Override // android.animation.AnimatorListenerAdapter,
                                  // android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animation) {
                            HandleView.this.mDrawable.setBounds(
                                    HandleView.this.getDrawableBounds(
                                            targetRect.width(), targetRect.height()));
                            HandleView.this.requestLayout();
                            HandleView.this.invalidate();
                        }
                    });
            return changeSizeAnimator;
        }

        private void magnifySize() {
            int drawableStartWidth = this.mDrawable.getIntrinsicWidth();
            int drawableStartHeight = this.mDrawable.getIntrinsicHeight();
            int drawableTargetWidth = (int) (drawableStartWidth * 1.5f);
            int drawableTargetHeight = (int) (drawableStartHeight * 1.5f);
            ObjectAnimator magnifySizeAnimator =
                    getChangeSizeAnimator(
                            getDrawableBounds(drawableStartWidth, drawableStartHeight),
                            getDrawableBounds(drawableTargetWidth, drawableTargetHeight));
            magnifySizeAnimator.setDuration(250L);
            magnifySizeAnimator.setInterpolator(this.mPathInterpolator);
            magnifySizeAnimator.start();
        }

        private void restore() {
            AnimatorSet restoreAnimators = new AnimatorSet();
            ObjectAnimator restoreSizeAnimator = getRestoreSizeAnimator();
            ValueAnimator restorePositionAnimator = getRestorePositionAnimator();
            if (restorePositionAnimator == null) {
                Log.d("Editor", "restorePositionAnimator is null. hide() is called.");
                hide();
            } else {
                restoreAnimators.playTogether(restoreSizeAnimator, restorePositionAnimator);
                restoreAnimators.addListener(
                        new AnimatorListenerAdapter() { // from class:
                                                        // android.widget.Editor.HandleView.4
                            @Override // android.animation.AnimatorListenerAdapter,
                                      // android.animation.Animator.AnimatorListener
                            public void onAnimationEnd(Animator animation) {
                                HandleView.this.mIsRestoring = false;
                                HandleView.this.positionAtCursorOffset(
                                        HandleView.this.getCurrentCursorOffset(), true, false);
                                if (HandleView.this.shouldShow()) {
                                    if (HandleView.this.isShowing()) {
                                        HandleView.this.requestLayout();
                                        HandleView.this.invalidate();
                                    }
                                } else if (HandleView.this.isShowing()) {
                                    HandleView.this.dismiss();
                                }
                                if (Editor.this.mTextView.getSelectionStart()
                                        > Editor.this.mTextView.getSelectionEnd()) {
                                    Selection.setSelection(
                                            (Spannable) Editor.this.mTextView.getText(),
                                            Editor.this.mTextView.getSelectionEnd(),
                                            Editor.this.mTextView.getSelectionStart());
                                }
                                HandleView.this.updateDrawable(false);
                            }
                        });
                restoreAnimators.start();
            }
        }

        private ObjectAnimator getRestoreSizeAnimator() {
            Rect r = this.mDrawable.getBounds();
            int drawableStartWidth = r.width();
            int drawableStartHeight = r.height();
            int drawableTargetWidth = this.mDrawable.getIntrinsicWidth();
            int drawableTargetHeight = this.mDrawable.getIntrinsicHeight();
            ObjectAnimator restoreSizeAnimator =
                    getChangeSizeAnimator(
                            getDrawableBounds(drawableStartWidth, drawableStartHeight),
                            getDrawableBounds(drawableTargetWidth, drawableTargetHeight));
            restoreSizeAnimator.setDuration(250L);
            restoreSizeAnimator.setInterpolator(this.mPathInterpolator);
            return restoreSizeAnimator;
        }

        private ValueAnimator getRestorePositionAnimator() {
            int[] startCoords = {this.mPositionX, this.mPositionY};
            int[] targetCoords = new int[2];
            if (Editor.this.mTextView.getLayout() == null) {
                Editor.this.prepareCursorControllers();
                return null;
            }
            Layout layout = Editor.this.getActiveLayout();
            int offset = getCurrentCursorOffset();
            int line = layout.getLineForOffset(offset);
            targetCoords[0] =
                    ((getCursorHorizontalPosition(layout, offset) - this.mHotspotX)
                                    - getHorizontalOffset())
                            + getCursorOffset()
                            + Editor.this.mTextView.viewportToContentHorizontalOffset();
            targetCoords[1] =
                    layout.getLineBottom(line)
                            + Editor.this.mTextView.viewportToContentVerticalOffset();
            startCoords[0] = startCoords[0] + this.mHotspotX + getHorizontalOffset();
            targetCoords[0] = targetCoords[0] + this.mHotspotX + getHorizontalOffset();
            Editor.this.mTextView.transformFromViewToWindowSpace(startCoords);
            Editor.this.mTextView.transformFromViewToWindowSpace(targetCoords);
            startCoords[0] = startCoords[0] - (this.mHotspotX + getHorizontalOffset());
            targetCoords[0] = targetCoords[0] - (this.mHotspotX + getHorizontalOffset());
            PropertyValuesHolder[] valuesHolders = {
                PropertyValuesHolder.ofInt("x", startCoords[0], targetCoords[0]),
                PropertyValuesHolder.ofInt("y", startCoords[1], targetCoords[1])
            };
            ValueAnimator restorePositionAnimator =
                    ValueAnimator.ofPropertyValuesHolder(valuesHolders);
            restorePositionAnimator.setDuration(250L);
            restorePositionAnimator.setInterpolator(this.mPathInterpolator);
            restorePositionAnimator.addUpdateListener(
                    new ValueAnimator
                            .AnimatorUpdateListener() { // from class:
                                                        // android.widget.Editor.HandleView.5
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public void onAnimationUpdate(ValueAnimator animation) {
                            int x = ((Integer) animation.getAnimatedValue("x")).intValue();
                            int y = ((Integer) animation.getAnimatedValue("y")).intValue();
                            if (HandleView.this.isShowing()) {
                                HandleView.this.invalidate();
                                HandleView.this.mContainer.update(x, y, -1, -1);
                            }
                        }
                    });
            return restorePositionAnimator;
        }

        private ObjectAnimator getShowAnimator() {
            final int targetWidth = this.mDrawable.getIntrinsicWidth();
            final int targetHeight = this.mDrawable.getIntrinsicHeight();
            ObjectAnimator showAnimator =
                    ObjectAnimator.ofObject(
                            this.mDrawable,
                            "bounds",
                            this.CHANGE_SIZE_EVALUATOR,
                            getDrawableBounds(0, 0),
                            getDrawableBounds(targetWidth, targetHeight));
            showAnimator.addUpdateListener(
                    new ValueAnimator
                            .AnimatorUpdateListener() { // from class:
                                                        // android.widget.Editor.HandleView.6
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public void onAnimationUpdate(ValueAnimator animation) {
                            if (!HandleView.this.mIsShowAnimating) {
                                return;
                            }
                            HandleView.this.invalidate();
                        }
                    });
            showAnimator.addListener(
                    new AnimatorListenerAdapter() { // from class:
                                                    // android.widget.Editor.HandleView.7
                        @Override // android.animation.AnimatorListenerAdapter,
                                  // android.animation.Animator.AnimatorListener
                        public void onAnimationStart(Animator animation) {
                            if (HandleView.this.mIsHideAnimating) {
                                HandleView.this.mHideAnimator.cancel();
                                HandleView.this.mIsHideAnimating = false;
                            }
                            HandleView.this.positionAtCursorOffset(
                                    HandleView.this.getCurrentCursorOffset(), true, false);
                            int[] pts = {
                                HandleView.this.mPositionX
                                        + HandleView.this.mHotspotX
                                        + HandleView.this.getHorizontalOffset(),
                                HandleView.this.mPositionY
                            };
                            Editor.this.mTextView.transformFromViewToWindowSpace(pts);
                            pts[0] =
                                    pts[0]
                                            - (HandleView.this.mHotspotX
                                                    + HandleView.this.getHorizontalOffset());
                            HandleView.this.mContainer.update(pts[0], pts[1], -1, -1);
                            HandleView.this.mIsShowAnimating = true;
                            HandleView.this.setLayerType(1, null);
                        }

                        @Override // android.animation.AnimatorListenerAdapter,
                                  // android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animation) {
                            if (!HandleView.this.mIsShowAnimating) {
                                return;
                            }
                            HandleView.this.mDrawable.setBounds(
                                    HandleView.this.getDrawableBounds(targetWidth, targetHeight));
                            HandleView.this.invalidate();
                            if (Editor.this.mTextActionMode != null) {
                                HandleView.this.removeHiderCallback();
                            } else {
                                HandleView.this.hideAfterDelay();
                            }
                            HandleView.this.setLayerType(0, null);
                            HandleView.this.mIsShowAnimating = false;
                            HandleView.this.mShowAnimator = null;
                        }
                    });
            showAnimator.setDuration(200L);
            showAnimator.setInterpolator(this.mPathInterpolator);
            return showAnimator;
        }

        private ObjectAnimator getHideAnimator() {
            Rect r = this.mDrawable.getBounds();
            int startWidth = r.width();
            int startHeight = r.height();
            ObjectAnimator hideAnimator =
                    ObjectAnimator.ofObject(
                            this.mDrawable,
                            "bounds",
                            this.CHANGE_SIZE_EVALUATOR,
                            getDrawableBounds(startWidth, startHeight),
                            new Rect(0, 0, 0, 0));
            hideAnimator.addUpdateListener(
                    new ValueAnimator
                            .AnimatorUpdateListener() { // from class:
                                                        // android.widget.Editor.HandleView.8
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public void onAnimationUpdate(ValueAnimator animation) {
                            if (!HandleView.this.mIsHideAnimating) {
                                return;
                            }
                            HandleView.this.invalidate();
                        }
                    });
            hideAnimator.addListener(
                    new AnimatorListenerAdapter() { // from class:
                                                    // android.widget.Editor.HandleView.9
                        @Override // android.animation.AnimatorListenerAdapter,
                                  // android.animation.Animator.AnimatorListener
                        public void onAnimationStart(Animator animation) {
                            if (HandleView.this.mIsShowAnimating) {
                                HandleView.this.mShowAnimator.cancel();
                                HandleView.this.mIsShowAnimating = false;
                            }
                            HandleView.this.mIsHideAnimating = true;
                            HandleView.this.setLayerType(1, null);
                        }

                        @Override // android.animation.AnimatorListenerAdapter,
                                  // android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animation) {
                            if (!HandleView.this.mIsHideAnimating) {
                                return;
                            }
                            HandleView.this.setLayerType(0, null);
                            HandleView.this.mContainer.dismiss();
                            HandleView.this.mIsHideAnimating = false;
                            HandleView.this.mHideAnimator = null;
                        }

                        @Override // android.animation.AnimatorListenerAdapter,
                                  // android.animation.Animator.AnimatorListener
                        public void onAnimationCancel(Animator animation) {
                            HandleView.this.setLayerType(0, null);
                            HandleView.this.mIsHideAnimating = false;
                            HandleView.this.mHideAnimator = null;
                        }
                    });
            hideAnimator.setDuration(100L);
            hideAnimator.setInterpolator(this.mPathInterpolator);
            return hideAnimator;
        }

        private boolean isValid() {
            return Editor.this.mTextView.getApplicationWindowToken() != null
                    && Editor.this.mTextView.getWindowToken() != null
                    && Editor.this.mTextView.getApplicationWindowToken()
                            == Editor.this.mTextView.getWindowToken()
                    && Editor.this.mTextView.hasFocus();
        }
    }

    private class InsertionHandleView extends HandleView {
        private final int mDeltaHeight;
        private final int mDrawableOpacity;
        private Runnable mHider;
        private boolean mIsInActionMode;
        private boolean mIsTouchDown;
        private float mLastDownRawX;
        private float mLastDownRawY;
        private long mLastUpTime;
        private boolean mOffsetChanged;
        private int mOffsetDown;
        private boolean mPendingDismissOnUp;
        private boolean mShouldMagnifierCursorAdjust;
        private float mTouchDownX;
        private float mTouchDownY;

        InsertionHandleView(Drawable drawable) {
            super(drawable, drawable, R.id.insertion_handle);
            this.mIsTouchDown = false;
            this.mPendingDismissOnUp = false;
            this.mShouldMagnifierCursorAdjust = false;
            int opacity = 0;
            int opacity2 = 255;
            if (Editor.this.mFlagInsertionHandleGesturesEnabled) {
                int deltaHeight =
                        AppGlobals.getIntCoreSetting(
                                WidgetFlags.KEY_INSERTION_HANDLE_DELTA_HEIGHT, 25);
                int opacity3 =
                        AppGlobals.getIntCoreSetting(WidgetFlags.KEY_INSERTION_HANDLE_OPACITY, 50);
                deltaHeight = (deltaHeight < -25 || deltaHeight > 50) ? 25 : deltaHeight;
                opacity2 = (((opacity3 < 10 || opacity3 > 100) ? 50 : opacity3) * 255) / 100;
                opacity = deltaHeight;
            }
            this.mDeltaHeight = opacity;
            this.mDrawableOpacity = opacity2;
        }

        @Override // android.widget.Editor.HandleView
        protected void hideAfterDelay() {
            if (this.mHider == null) {
                this.mHider =
                        new Runnable() { // from class: android.widget.Editor.InsertionHandleView.1
                            @Override // java.lang.Runnable
                            public void run() {
                                InsertionHandleView.this.hide();
                            }
                        };
            } else {
                removeHiderCallback();
            }
            Editor.this.mTextView.postDelayed(this.mHider, 4000L);
        }

        @Override // android.widget.Editor.HandleView
        protected void removeHiderCallback() {
            if (this.mHider != null) {
                Editor.this.mTextView.removeCallbacks(this.mHider);
            }
        }

        @Override // android.widget.Editor.HandleView
        protected int getHotspotX(Drawable drawable, boolean isRtlRun) {
            return drawable.getIntrinsicWidth() / 2;
        }

        @Override // android.widget.Editor.HandleView
        protected int getHorizontalGravity(boolean isRtlRun) {
            return 1;
        }

        @Override // android.widget.Editor.HandleView
        protected int getCursorOffset() {
            int offset = super.getCursorOffset();
            if (Editor.this.mDrawableForCursor != null) {
                Editor.this.mDrawableForCursor.getPadding(Editor.this.mTempRect);
                return offset
                        + (((Editor.this.mDrawableForCursor.getIntrinsicWidth()
                                                - Editor.this.mTempRect.left)
                                        - Editor.this.mTempRect.right)
                                / 2);
            }
            return offset;
        }

        @Override // android.widget.Editor.HandleView
        int getCursorHorizontalPosition(Layout layout, int offset) {
            if (Editor.this.mDrawableForCursor != null) {
                float horizontal = getHorizontal(layout, offset);
                return Editor.this.clampHorizontalPosition(
                                Editor.this.mDrawableForCursor, horizontal)
                        + Editor.this.mTempRect.left;
            }
            return super.getCursorHorizontalPosition(layout, offset);
        }

        @Override // android.widget.Editor.HandleView, android.view.View
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            if (Editor.this.mFlagInsertionHandleGesturesEnabled) {
                int height =
                        Math.max(
                                getPreferredHeight() + this.mDeltaHeight,
                                this.mDrawable.getIntrinsicHeight());
                setMeasuredDimension(getPreferredWidth(), height);
            } else {
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean shouldMagnifierCursorAdjust() {
            return this.mShouldMagnifierCursorAdjust
                    && Editor.this.mMagnifierAnimator != null
                    && Editor.this.mMagnifierAnimator.mMagnifierIsShowing;
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Code restructure failed: missing block: B:36:0x00d3, code lost:

           return r0;
        */
        @Override // android.widget.Editor.HandleView, android.view.View
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public boolean onTouchEvent(android.view.MotionEvent r9) {
            /*
                r8 = this;
                android.widget.Editor r0 = android.widget.Editor.this
                android.widget.TextView r0 = android.widget.Editor.m6496$$Nest$fgetmTextView(r0)
                r1 = 1
                boolean r0 = r0.isFromPrimePointer(r9, r1)
                if (r0 != 0) goto Le
                return r1
            Le:
                android.widget.Editor r0 = android.widget.Editor.this
                boolean r0 = android.widget.Editor.m6481$$Nest$fgetmFlagInsertionHandleGesturesEnabled(r0)
                if (r0 == 0) goto L23
                android.widget.Editor r0 = android.widget.Editor.this
                boolean r0 = android.widget.Editor.m6480$$Nest$fgetmFlagCursorDragFromAnywhereEnabled(r0)
                if (r0 == 0) goto L23
                boolean r0 = r8.touchThrough(r9)
                return r0
            L23:
                boolean r0 = super.onTouchEvent(r9)
                int r2 = r9.getActionMasked()
                r3 = 0
                switch(r2) {
                    case 0: goto Lb1;
                    case 1: goto L56;
                    case 2: goto L40;
                    case 3: goto L31;
                    default: goto L2f;
                }
            L2f:
                goto Ld3
            L31:
                r8.hideAfterDelay()
                r8.dismissMagnifier()
                r8.mShouldMagnifierCursorAdjust = r3
                android.widget.Editor r1 = android.widget.Editor.this
                r1.updateCursorPosition()
                goto Ld3
            L40:
                android.widget.Editor r1 = android.widget.Editor.this
                boolean r1 = android.widget.Editor.m6485$$Nest$fgetmIsMagnifierHideByVelocityTracker(r1)
                if (r1 != 0) goto L4c
                r8.updateMagnifier(r9)
                goto L4f
            L4c:
                r8.dismissMagnifier()
            L4f:
                android.widget.Editor r1 = android.widget.Editor.this
                r1.updateCursorPosition()
                goto Ld3
            L56:
                boolean r1 = r8.offsetHasBeenChanged()
                if (r1 != 0) goto L86
                android.widget.Editor r1 = android.widget.Editor.this
                android.widget.TextView r1 = android.widget.Editor.m6496$$Nest$fgetmTextView(r1)
                android.content.Context r1 = r1.getContext()
                android.view.ViewConfiguration r1 = android.view.ViewConfiguration.get(r1)
                float r2 = r8.mLastDownRawX
                float r4 = r8.mLastDownRawY
                float r5 = r9.getRawX()
                float r6 = r9.getRawY()
                int r7 = r1.getScaledTouchSlop()
                boolean r2 = android.widget.EditorTouchState.isDistanceWithin(r2, r4, r5, r6, r7)
                if (r2 == 0) goto L85
                android.widget.Editor r4 = android.widget.Editor.this
                android.widget.Editor.m6540$$Nest$mtoggleInsertionActionMode(r4)
            L85:
                goto L97
            L86:
                android.widget.Editor r1 = android.widget.Editor.this
                android.view.ActionMode r1 = android.widget.Editor.m6495$$Nest$fgetmTextActionMode(r1)
                if (r1 == 0) goto L97
                android.widget.Editor r1 = android.widget.Editor.this
                android.view.ActionMode r1 = android.widget.Editor.m6495$$Nest$fgetmTextActionMode(r1)
                r1.invalidateContentRect()
            L97:
                android.widget.Editor r1 = android.widget.Editor.this
                android.view.ActionMode r1 = android.widget.Editor.m6495$$Nest$fgetmTextActionMode(r1)
                if (r1 == 0) goto La3
                r8.removeHiderCallback()
                goto La6
            La3:
                r8.hideAfterDelay()
            La6:
                r8.dismissMagnifier()
                r8.mShouldMagnifierCursorAdjust = r3
                android.widget.Editor r1 = android.widget.Editor.this
                r1.updateCursorPosition()
                goto Ld3
            Lb1:
                float r2 = r9.getRawX()
                r8.mLastDownRawX = r2
                float r2 = r9.getRawY()
                r8.mLastDownRawY = r2
                android.widget.Editor r2 = android.widget.Editor.this
                boolean r2 = android.widget.Editor.m6486$$Nest$fgetmIsThemeDeviceDefault(r2)
                if (r2 == 0) goto Lc8
                r8.removeHiderCallback()
            Lc8:
                r8.updateMagnifier(r9)
                r8.mShouldMagnifierCursorAdjust = r1
                android.widget.Editor r1 = android.widget.Editor.this
                r1.updateCursorPosition()
            Ld3:
                return r0
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " android.widget.Editor.InsertionHandleView.onTouchEvent(android.view.MotionEvent):boolean");
        }

        private boolean touchThrough(MotionEvent ev) {
            int actionType = ev.getActionMasked();
            switch (actionType) {
                case 0:
                    this.mIsTouchDown = true;
                    this.mOffsetChanged = false;
                    this.mOffsetDown = Editor.this.mTextView.getSelectionStart();
                    this.mTouchDownX = ev.getX();
                    this.mTouchDownY = ev.getY();
                    this.mIsInActionMode = Editor.this.mTextActionMode != null;
                    if (ev.getEventTime() - this.mLastUpTime
                            < ViewConfiguration.getDoubleTapTimeout()) {
                        Editor.this.lambda$startActionModeInternal$0();
                    }
                    Editor.this.mTouchState.setIsOnHandle(true);
                    break;
                case 1:
                    this.mLastUpTime = ev.getEventTime();
                    break;
            }
            boolean ret = Editor.this.mTextView.onTouchEvent(transformEventForTouchThrough(ev));
            if (actionType == 1 || actionType == 3) {
                this.mIsTouchDown = false;
                if (this.mPendingDismissOnUp) {
                    dismiss();
                }
                Editor.this.mTouchState.setIsOnHandle(false);
            }
            if (!this.mOffsetChanged) {
                int start = Editor.this.mTextView.getSelectionStart();
                int end = Editor.this.mTextView.getSelectionEnd();
                if (start != end || this.mOffsetDown != start) {
                    this.mOffsetChanged = true;
                }
            }
            if (!this.mOffsetChanged && actionType == 1) {
                if (this.mIsInActionMode) {
                    Editor.this.lambda$startActionModeInternal$0();
                } else {
                    Editor.this.startInsertionActionMode();
                }
            }
            return ret;
        }

        private MotionEvent transformEventForTouchThrough(MotionEvent ev) {
            Layout layout = Editor.this.mTextView.getLayout();
            int line = getLineForOffset(layout, getCurrentCursorOffset());
            int textHeight = layout.getLineBottom(line, false) - layout.getLineTop(line);
            Matrix m = new Matrix();
            m.setTranslate(
                    ((ev.getRawX() - ev.getX()) + (getMeasuredWidth() >> 1)) - this.mTouchDownX,
                    ((ev.getRawY() - ev.getY()) - (textHeight >> 1)) - this.mTouchDownY);
            ev.transform(m);
            Editor.this.mTextView.toLocalMotionEvent(ev);
            return ev;
        }

        @Override // android.widget.Editor.HandleView
        public boolean isShowing() {
            if (this.mPendingDismissOnUp) {
                return false;
            }
            return super.isShowing();
        }

        @Override // android.widget.Editor.HandleView
        public void show() {
            super.show();
            this.mPendingDismissOnUp = false;
            this.mDrawable.setAlpha(this.mDrawableOpacity);
        }

        @Override // android.widget.Editor.HandleView
        public void dismiss() {
            if (this.mIsTouchDown) {
                this.mPendingDismissOnUp = true;
                this.mDrawable.setAlpha(0);
            } else {
                super.dismiss();
                this.mPendingDismissOnUp = false;
            }
        }

        @Override // android.widget.Editor.HandleView
        protected void updateDrawable(boolean updateDrawableWhenDragging) {
            super.updateDrawable(updateDrawableWhenDragging);
            this.mDrawable.setAlpha(this.mDrawableOpacity);
        }

        @Override // android.widget.Editor.HandleView
        public int getCurrentCursorOffset() {
            return Editor.this.mTextView.getSelectionStart();
        }

        @Override // android.widget.Editor.HandleView
        public void updateSelection(int offset) {
            Selection.setSelection((Spannable) Editor.this.mTextView.getText(), offset);
        }

        @Override // android.widget.Editor.HandleView
        protected void updatePosition(float x, float y, boolean fromTouchScreen) {
            int offset;
            int y_;
            float inWindowY = (y - this.mLastParentYOnScreen) + this.mFirstParentY;
            Layout layout = Editor.this.mTextView.getLayout();
            if (layout != null) {
                if (this.mPreviousLineTouched == -1) {
                    this.mPreviousLineTouched =
                            Editor.this.mTextView.getLineAtCoordinate(inWindowY);
                }
                int currLine =
                        Editor.this.getCurrentLineAdjustedForSlop(
                                layout, this.mPreviousLineTouched, inWindowY);
                offset = getOffsetAtCoordinate(layout, currLine, x);
                int currentLineBottom = layout.getLineBottom(currLine);
                int previousLineBottom = layout.getLineBottom(this.mPreviousLineTouched);
                int verticalOffset =
                        Editor.this.mTextView.getVerticalOffset(true)
                                + Editor.this.mTextView.getCompoundPaddingTop();
                int diff = (currentLineBottom - previousLineBottom) - verticalOffset;
                this.mPreviousLineTouched = currLine;
                int x_ =
                        (int)
                                ((((((this.mTouchToWindowOffsetX + x) - this.mHotspotX)
                                                                - getHorizontalOffset())
                                                        - this.mHorizontalOffset)
                                                + this.mLastParentXOnScreen)
                                        - this.mLastParentX);
                if (this.mIsVerticalScrolled) {
                    y_ = currentLineBottom - diff;
                } else {
                    y_ =
                            (int)
                                    ((((this.mTouchToWindowOffsetY + y) - this.mTouchOffsetY)
                                                    - this.mVerticalScrolledYOffset)
                                            - this.mVerticalOffset);
                }
                updatePositionDuringDragging(x_, y_);
            } else {
                offset = -1;
            }
            positionAtCursorOffset(offset, false, fromTouchScreen);
            if (Editor.this.mTextActionMode != null) {
                Editor.this.invalidateActionMode();
            }
        }

        @Override // android.widget.Editor.HandleView
        void onHandleMoved() {
            super.onHandleMoved();
            removeHiderCallback();
        }

        @Override // android.widget.Editor.HandleView
        public void onDetached() {
            super.onDetached();
            removeHiderCallback();
        }

        @Override // android.widget.Editor.HandleView
        protected int getMagnifierHandleTrigger() {
            return 0;
        }
    }

    public final class SelectionHandleView extends HandleView {
        private final int mHandleType;
        private boolean mInWord;
        private boolean mLanguageDirectionChanged;
        private float mPrevX;
        private final float mTextViewEdgeSlop;
        private final int[] mTextViewLocation;
        private float mTouchWordDelta;

        public SelectionHandleView(
                Drawable drawableLtr, Drawable drawableRtl, int id, int handleType) {
            super(drawableLtr, drawableRtl, id);
            this.mInWord = false;
            this.mLanguageDirectionChanged = false;
            this.mTextViewLocation = new int[2];
            this.mHandleType = handleType;
            ViewConfiguration viewConfiguration =
                    ViewConfiguration.get(Editor.this.mTextView.getContext());
            this.mTextViewEdgeSlop = viewConfiguration.getScaledTouchSlop() * 4;
        }

        private boolean isStartHandle() {
            return this.mHandleType == 0;
        }

        @Override // android.widget.Editor.HandleView
        protected int getHotspotX(Drawable drawable, boolean isRtlRun) {
            if (isRtlRun == isStartHandle()) {
                return drawable.getIntrinsicWidth() / 4;
            }
            return (drawable.getIntrinsicWidth() * 3) / 4;
        }

        @Override // android.widget.Editor.HandleView
        protected int getHorizontalGravity(boolean isRtlRun) {
            return isRtlRun == isStartHandle() ? 3 : 5;
        }

        @Override // android.widget.Editor.HandleView
        public int getCurrentCursorOffset() {
            return isStartHandle()
                    ? Editor.this.mTextView.getSelectionStart()
                    : Editor.this.mTextView.getSelectionEnd();
        }

        @Override // android.widget.Editor.HandleView
        protected void updateSelection(int offset) {
            if (isStartHandle()) {
                Selection.setSelection(
                        (Spannable) Editor.this.mTextView.getText(),
                        offset,
                        Editor.this.mTextView.getSelectionEnd());
            } else {
                Selection.setSelection(
                        (Spannable) Editor.this.mTextView.getText(),
                        Editor.this.mTextView.getSelectionStart(),
                        offset);
            }
            updateDrawable(false);
            if (Editor.this.mTextActionMode != null) {
                Editor.this.invalidateActionMode();
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:72:0x019b, code lost:

           if (r5.canScrollHorizontally(r3) != false) goto L76;
        */
        @Override // android.widget.Editor.HandleView
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        protected void updatePosition(float r30, float r31, boolean r32) {
            /*
                Method dump skipped, instructions count: 486
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " android.widget.Editor.SelectionHandleView.updatePosition(float, float,"
                        + " boolean):void");
        }

        @Override // android.widget.Editor.HandleView
        protected void positionAtCursorOffset(
                int offset, boolean forceUpdatePosition, boolean fromTouchScreen) {
            super.positionAtCursorOffset(offset, forceUpdatePosition, fromTouchScreen);
            this.mInWord =
                    (offset == -1 || Editor.this.getWordIteratorWithText().isBoundary(offset))
                            ? false
                            : true;
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Code restructure failed: missing block: B:13:0x0039, code lost:

           return r0;
        */
        @Override // android.widget.Editor.HandleView, android.view.View
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public boolean onTouchEvent(android.view.MotionEvent r3) {
            /*
                r2 = this;
                android.widget.Editor r0 = android.widget.Editor.this
                android.widget.TextView r0 = android.widget.Editor.m6496$$Nest$fgetmTextView(r0)
                r1 = 1
                boolean r0 = r0.isFromPrimePointer(r3, r1)
                if (r0 != 0) goto Le
                return r1
            Le:
                boolean r0 = super.onTouchEvent(r3)
                int r1 = r3.getActionMasked()
                switch(r1) {
                    case 0: goto L2e;
                    case 1: goto L2a;
                    case 2: goto L1a;
                    case 3: goto L2a;
                    default: goto L19;
                }
            L19:
                goto L39
            L1a:
                android.widget.Editor r1 = android.widget.Editor.this
                boolean r1 = android.widget.Editor.m6485$$Nest$fgetmIsMagnifierHideByVelocityTracker(r1)
                if (r1 != 0) goto L26
                r2.updateMagnifier(r3)
                goto L39
            L26:
                r2.dismissMagnifier()
                goto L39
            L2a:
                r2.dismissMagnifier()
                goto L39
            L2e:
                r1 = 0
                r2.mTouchWordDelta = r1
                r1 = -1082130432(0xffffffffbf800000, float:-1.0)
                r2.mPrevX = r1
                r2.updateMagnifier(r3)
            L39:
                return r0
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " android.widget.Editor.SelectionHandleView.onTouchEvent(android.view.MotionEvent):boolean");
        }

        private void positionAndAdjustForCrossingHandles(int offset, boolean fromTouchScreen) {
            int anotherHandleOffset =
                    isStartHandle()
                            ? Editor.this.mTextView.getSelectionEnd()
                            : Editor.this.mTextView.getSelectionStart();
            if ((isStartHandle() && offset >= anotherHandleOffset)
                    || (!isStartHandle() && offset <= anotherHandleOffset)) {
                Layout layout = Editor.this.mTextView.getLayout();
                if (layout != null && offset == anotherHandleOffset) {
                    offset = Editor.this.getNextCursorOffset(anotherHandleOffset, !isStartHandle());
                }
            }
            positionAtCursorOffset(offset, false, fromTouchScreen);
        }

        private boolean positionNearEdgeOfScrollingView(float x, boolean atRtl) {
            Editor.this.mTextView.getLocationOnScreen(this.mTextViewLocation);
            if (atRtl == isStartHandle()) {
                int rightEdge =
                        (this.mTextViewLocation[0] + Editor.this.mTextView.getWidth())
                                - Editor.this.mTextView.getPaddingRight();
                boolean nearEdge = x > ((float) rightEdge) - this.mTextViewEdgeSlop;
                return nearEdge;
            }
            int leftEdge = this.mTextViewLocation[0] + Editor.this.mTextView.getPaddingLeft();
            boolean nearEdge2 = x < ((float) leftEdge) + this.mTextViewEdgeSlop;
            return nearEdge2;
        }

        @Override // android.widget.Editor.HandleView
        protected boolean isAtRtlRun(Layout layout, int offset) {
            int offsetToCheck;
            int transformedOffset = Editor.this.mTextView.transformedToOriginal(offset, 0);
            if (isStartHandle()
                    != (Editor.this.mTextView.getSelectionStart()
                            < Editor.this.mTextView.getSelectionEnd())) {
                offsetToCheck = Math.max(transformedOffset - 1, 0);
            } else {
                offsetToCheck = transformedOffset;
            }
            return layout.isRtlCharAt(offsetToCheck);
        }

        @Override // android.widget.Editor.HandleView
        public float getHorizontal(Layout layout, int offset) {
            return getHorizontal(layout, offset, isStartHandle());
        }

        private float getHorizontal(Layout layout, int offset, boolean startHandle) {
            int offsetTransformed = Editor.this.mTextView.originalToTransformed(offset, 1);
            int line = layout.getLineForOffset(offsetTransformed);
            int offsetToCheck =
                    startHandle ? offsetTransformed : Math.max(offsetTransformed - 1, 0);
            boolean isRtlChar = layout.isRtlCharAt(offsetToCheck);
            boolean isRtlParagraph = layout.getParagraphDirection(line) == -1;
            if (isRtlChar != isRtlParagraph) {
                return layout.getSecondaryHorizontal(offsetTransformed);
            }
            return layout.getPrimaryHorizontal(offsetTransformed);
        }

        @Override // android.widget.Editor.HandleView
        protected int getOffsetAtCoordinate(Layout layout, int line, float x) {
            int offsetToCheck;
            int offset;
            float localX = Editor.this.mTextView.convertToLocalHorizontalCoordinate(x);
            int primaryOffset = layout.getOffsetForHorizontal(line, localX, true);
            if (!layout.isLevelBoundary(primaryOffset)) {
                return Editor.this.mTextView.transformedToOriginal(primaryOffset, 1);
            }
            boolean isRtlParagraph = false;
            int secondaryOffset = layout.getOffsetForHorizontal(line, localX, false);
            int currentOffset =
                    Editor.this.mTextView.originalToTransformed(getCurrentCursorOffset(), 1);
            int primaryDiff = Math.abs(primaryOffset - currentOffset);
            int secondaryDiff = Math.abs(secondaryOffset - currentOffset);
            if (primaryDiff < secondaryDiff) {
                offset = primaryOffset;
            } else if (primaryDiff > secondaryDiff) {
                offset = secondaryOffset;
            } else {
                if (!isStartHandle()) {
                    offsetToCheck = Math.max(currentOffset - 1, 0);
                } else {
                    offsetToCheck = currentOffset;
                }
                boolean isRtlChar = layout.isRtlCharAt(offsetToCheck);
                if (layout.getParagraphDirection(line) == -1) {
                    isRtlParagraph = true;
                }
                offset = isRtlChar == isRtlParagraph ? primaryOffset : secondaryOffset;
            }
            return Editor.this.mTextView.transformedToOriginal(offset, 1);
        }

        @Override // android.widget.Editor.HandleView
        protected int getMagnifierHandleTrigger() {
            if (isStartHandle()) {
                return 1;
            }
            return 2;
        }

        @Override // android.widget.Editor.HandleView
        protected boolean isScreenOut(int x, boolean atRtl) {
            int startX;
            int endX;
            int displayWidth = Editor.this.mTextView.getRootView().getRight();
            int iconSize = this.mDrawableLtr.getIntrinsicWidth() / 2;
            if (isStartHandle() == atRtl) {
                startX = x;
                endX = x + iconSize;
            } else {
                startX = x - iconSize;
                endX = x;
            }
            if (startX < 0 || endX < 0 || startX > displayWidth || endX > displayWidth) {
                return true;
            }
            return false;
        }
    }

    public void setLineChangeSlopMinMaxForTesting(int min, int max) {
        this.mLineChangeSlopMin = min;
        this.mLineChangeSlopMax = max;
    }

    public int getCurrentLineAdjustedForSlop(Layout layout, int prevLine, float y) {
        int trueLine = this.mTextView.getLineAtCoordinate(y);
        if (layout == null
                || prevLine >= layout.getLineCount()
                || layout.getLineCount() <= 0
                || prevLine < 0) {
            return trueLine;
        }
        if (Math.abs(trueLine - prevLine) >= 2) {
            return trueLine;
        }
        int lineHeight = this.mTextView.getLineHeight();
        int slop =
                Math.max(
                        0,
                        Math.max(
                                        this.mLineChangeSlopMin,
                                        Math.min(
                                                this.mLineChangeSlopMax,
                                                lineHeight
                                                        + ((int)
                                                                (this.mLineSlopRatio
                                                                        * lineHeight))))
                                - lineHeight);
        float verticalOffset = this.mTextView.viewportToContentVerticalOffset();
        if (trueLine > prevLine && y >= layout.getLineBottom(prevLine) + slop + verticalOffset) {
            return trueLine;
        }
        if (trueLine < prevLine && y <= (layout.getLineTop(prevLine) - slop) + verticalOffset) {
            return trueLine;
        }
        return prevLine;
    }

    void loadCursorDrawable() {
        if (this.mDrawableForCursor == null) {
            this.mDrawableForCursor = this.mTextView.getTextCursorDrawable();
        }
    }

    public class InsertionPointCursorController implements CursorController {
        private InsertionHandleView mHandle;
        private boolean mIsDraggingCursor;
        private boolean mIsTouchSnappedToHandleDuringDrag;
        private int mPrevLineDuringDrag;

        public InsertionPointCursorController() {}

        public void onTouchEvent(MotionEvent event) {
            if (Editor.this.hasSelectionController()
                    && Editor.this.getSelectionController().isCursorBeingModified()) {}
            switch (event.getActionMasked()) {
                case 1:
                case 3:
                    if (this.mIsDraggingCursor) {
                        endCursorDrag(event);
                        break;
                    }
                    break;
                case 2:
                    if (!event.isFromSource(8194)) {
                        if (!Editor.this.mTextView.isAutoHandwritingEnabled()
                                || !isFromStylus(event)) {
                            if (this.mIsDraggingCursor) {
                                performCursorDrag(event);
                                break;
                            } else if (Editor.this.mFlagCursorDragFromAnywhereEnabled
                                    && Editor.this.mTextView.getLayout() != null
                                    && Editor.this.mTextView.isFocused()
                                    && Editor.this.mTouchState.isMovedEnoughForDrag()) {
                                if (Editor.this.mTouchState.getInitialDragDirectionXYRatio()
                                                > Editor.this.mCursorDragDirectionMinXYRatio
                                        || Editor.this.mTouchState.isOnHandle()) {
                                    startCursorDrag(event);
                                    break;
                                }
                            }
                        }
                    }
                    break;
            }
        }

        private boolean isFromStylus(MotionEvent motionEvent) {
            int pointerIndex = motionEvent.getActionIndex();
            return motionEvent.getToolType(pointerIndex) == 2;
        }

        private void positionCursorDuringDrag(MotionEvent event) {
            this.mPrevLineDuringDrag = getLineDuringDrag(event);
            int offset =
                    Editor.this.mTextView.getOffsetAtCoordinate(
                            this.mPrevLineDuringDrag, event.getX());
            int oldSelectionStart = Editor.this.mTextView.getSelectionStart();
            int oldSelectionEnd = Editor.this.mTextView.getSelectionEnd();
            if (offset == oldSelectionStart && offset == oldSelectionEnd) {
                return;
            }
            Selection.setSelection((Spannable) Editor.this.mTextView.getText(), offset);
            Editor.this.updateCursorPosition();
            if (Editor.this.mHapticTextHandleEnabled) {
                Editor.this.mTextView.performHapticFeedback(9);
            }
        }

        private int getLineDuringDrag(MotionEvent event) {
            float fingerY;
            Layout layout = Editor.this.mTextView.getLayout();
            if (this.mPrevLineDuringDrag == -1) {
                return Editor.this.getCurrentLineAdjustedForSlop(
                        layout, this.mPrevLineDuringDrag, event.getY());
            }
            if (Editor.this.mTouchState.isOnHandle()) {
                fingerY = event.getRawY() - Editor.this.mTextView.getLocationOnScreen()[1];
            } else {
                fingerY = event.getY();
            }
            float cursorY = fingerY - getHandle().getIdealFingerToCursorOffset();
            int line =
                    Editor.this.getCurrentLineAdjustedForSlop(
                            layout, this.mPrevLineDuringDrag, cursorY);
            if (this.mIsTouchSnappedToHandleDuringDrag) {
                return line;
            }
            if (line < this.mPrevLineDuringDrag) {
                return Math.min(
                        this.mPrevLineDuringDrag,
                        Editor.this.getCurrentLineAdjustedForSlop(
                                layout, this.mPrevLineDuringDrag, fingerY));
            }
            this.mIsTouchSnappedToHandleDuringDrag = true;
            return line;
        }

        private void startCursorDrag(MotionEvent event) {
            this.mIsDraggingCursor = true;
            this.mIsTouchSnappedToHandleDuringDrag = false;
            this.mPrevLineDuringDrag = -1;
            Editor.this.mTextView.getParent().requestDisallowInterceptTouchEvent(true);
            Editor.this.mTextView.cancelLongPress();
            positionCursorDuringDrag(event);
            show();
            getHandle().removeHiderCallback();
            getHandle().updateMagnifier(event);
        }

        private void performCursorDrag(MotionEvent event) {
            positionCursorDuringDrag(event);
            getHandle().updateMagnifier(event);
        }

        private void endCursorDrag(MotionEvent event) {
            this.mIsDraggingCursor = false;
            this.mIsTouchSnappedToHandleDuringDrag = false;
            this.mPrevLineDuringDrag = -1;
            getHandle().dismissMagnifier();
            getHandle().hideAfterDelay();
            Editor.this.mTextView.getParent().requestDisallowInterceptTouchEvent(false);
        }

        @Override // android.widget.Editor.CursorController
        public void show() {
            if ((Editor.this.mUseCtxMenuInDesktopMode && Editor.this.mTextView.isDesktopMode())
                    || Editor.this.isUniversalSwitchEnable()) {
                Log.e(
                        "Editor",
                        "Action mode didn't start because Universal Switch / Desktop mode was"
                            + " enabled");
                return;
            }
            getHandle().removeHiderCallback();
            getHandle().show();
            long durationSinceCutOrCopy =
                    SystemClock.uptimeMillis() - TextView.sLastCutCopyOrTextChangedTime;
            if (Editor.this.mInsertionActionModeRunnable != null
                    && (this.mIsDraggingCursor
                            || Editor.this.mTouchState.isMultiTap()
                            || Editor.this.isCursorInsideEasyCorrectionSpan())) {
                Editor.this.mTextView.removeCallbacks(Editor.this.mInsertionActionModeRunnable);
            }
            if (!this.mIsDraggingCursor
                    && !Editor.this.mTouchState.isMultiTap()
                    && !Editor.this.isCursorInsideEasyCorrectionSpan()
                    && durationSinceCutOrCopy < 15000
                    && Editor.this.mTextActionMode == null) {
                if (Editor.this.mInsertionActionModeRunnable == null) {
                    Editor.this.mInsertionActionModeRunnable =
                            new Runnable() { // from class:
                                             // android.widget.Editor.InsertionPointCursorController.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    Editor.this.startInsertionActionMode();
                                }
                            };
                }
                Editor.this.mTextView.postDelayed(
                        Editor.this.mInsertionActionModeRunnable,
                        ViewConfiguration.getDoubleTapTimeout() + 1);
            }
            if (!this.mIsDraggingCursor) {
                getHandle().hideAfterDelay();
            }
            if (Editor.this.mSelectionModifierCursorController != null) {
                Editor.this.mSelectionModifierCursorController.hide();
            }
        }

        @Override // android.widget.Editor.CursorController
        public void hide() {
            if (this.mHandle != null) {
                this.mHandle.hide();
            }
        }

        @Override // android.view.ViewTreeObserver.OnTouchModeChangeListener
        public void onTouchModeChanged(boolean isInTouchMode) {
            if (!isInTouchMode) {
                hide();
            }
        }

        public InsertionHandleView getHandle() {
            if (this.mHandle == null) {
                Editor.this.loadHandleDrawables(false);
                this.mHandle = Editor.this.new InsertionHandleView(Editor.this.mSelectHandleCenter);
            }
            return this.mHandle;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void reloadHandleDrawable() {
            if (this.mHandle == null) {
                return;
            }
            this.mHandle.setDrawables(
                    Editor.this.mSelectHandleCenter, Editor.this.mSelectHandleCenter);
        }

        @Override // android.widget.Editor.CursorController
        public void onDetached() {
            ViewTreeObserver observer = Editor.this.mTextView.getViewTreeObserver();
            observer.removeOnTouchModeChangeListener(this);
            if (this.mHandle != null) {
                this.mHandle.onDetached();
            }
        }

        @Override // android.widget.Editor.CursorController
        public boolean isCursorBeingModified() {
            return this.mIsDraggingCursor || (this.mHandle != null && this.mHandle.isDragging());
        }

        @Override // android.widget.Editor.CursorController
        public boolean isActive() {
            return this.mHandle != null && this.mHandle.isShowing();
        }

        public void invalidateHandle() {
            if (this.mHandle != null) {
                this.mHandle.invalidate();
            }
        }
    }

    public class SelectionModifierCursorController implements CursorController {
        private static final int DRAG_ACCELERATOR_MODE_CHARACTER = 1;
        private static final int DRAG_ACCELERATOR_MODE_INACTIVE = 0;
        private static final int DRAG_ACCELERATOR_MODE_PARAGRAPH = 3;
        private static final int DRAG_ACCELERATOR_MODE_WORD = 2;
        private SelectionHandleView mEndHandle;
        private boolean mGestureStayedInTapRegion;
        private boolean mHaventMovedEnoughToStartDrag;
        private int mMaxTouchOffset;
        private int mMinTouchOffset;
        private VelocityTracker mSelectionVelocityTracker;
        private SelectionHandleView mStartHandle;
        private int mStartOffset = -1;
        private int mLineSelectionIsOn = -1;
        private boolean mSwitchedLines = false;
        private int mDragAcceleratorMode = 0;
        private boolean mIsExpanded = false;
        private int mPrevDownTouchOffset = 0;
        private int mPrevTouchOffset = 0;
        private int mPrevTouchWordStart = 0;
        private int mPrevTouchWordEnd = 0;

        SelectionModifierCursorController() {
            resetTouchOffsets();
        }

        @Override // android.widget.Editor.CursorController
        public void show() {
            if (Editor.this.mTextView.isInBatchEditMode()) {
                return;
            }
            Editor.this.loadHandleDrawables(false);
            initHandles();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void initHandles() {
            if (this.mStartHandle == null) {
                this.mStartHandle =
                        Editor.this
                        .new SelectionHandleView(
                                Editor.this.mSelectHandleLeft,
                                Editor.this.mSelectHandleRight,
                                R.id.selection_start_handle,
                                0);
            }
            if (this.mEndHandle == null) {
                this.mEndHandle =
                        Editor.this
                        .new SelectionHandleView(
                                Editor.this.mSelectHandleRight,
                                Editor.this.mSelectHandleLeft,
                                R.id.selection_end_handle,
                                1);
            }
            this.mStartHandle.show();
            this.mEndHandle.show();
            Editor.this.hideInsertionPointCursorController();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void reloadHandleDrawables() {
            if (this.mStartHandle == null) {
                return;
            }
            this.mStartHandle.setDrawables(
                    Editor.this.mSelectHandleLeft, Editor.this.mSelectHandleRight);
            this.mEndHandle.setDrawables(
                    Editor.this.mSelectHandleRight, Editor.this.mSelectHandleLeft);
        }

        @Override // android.widget.Editor.CursorController
        public void hide() {
            if (this.mStartHandle != null) {
                this.mStartHandle.hide();
            }
            if (this.mEndHandle != null) {
                this.mEndHandle.hide();
            }
        }

        public void enterDrag(int dragAcceleratorMode) {
            show();
            this.mDragAcceleratorMode = dragAcceleratorMode;
            this.mStartOffset =
                    Editor.this.mTextView.getOffsetForPosition(
                            Editor.this.mTouchState.getLastDownX(),
                            Editor.this.mTouchState.getLastDownY());
            this.mLineSelectionIsOn =
                    Editor.this.mTextView.getLineAtCoordinate(
                            Editor.this.mTouchState.getLastDownY());
            hide();
            Editor.this.mTextView.getParent().requestDisallowInterceptTouchEvent(true);
            Editor.this.mTextView.cancelLongPress();
        }

        public void onTouchEvent(MotionEvent event) {
            float eventX = event.getX();
            float eventY = event.getY();
            boolean isMouse = event.isFromSource(8194);
            switch (event.getActionMasked()) {
                case 0:
                    if (Editor.this.extractedTextModeWillBeStarted()) {
                        hide();
                    } else {
                        int offsetForPosition =
                                Editor.this.mTextView.getOffsetForPosition(eventX, eventY);
                        this.mMaxTouchOffset = offsetForPosition;
                        this.mMinTouchOffset = offsetForPosition;
                        this.mPrevDownTouchOffset = this.mMinTouchOffset;
                        this.mPrevTouchOffset = this.mMinTouchOffset;
                        this.mPrevTouchWordEnd = Editor.this.getWordEnd(this.mPrevTouchOffset);
                        this.mPrevTouchWordStart = Editor.this.getWordStart(this.mPrevTouchOffset);
                        if (this.mGestureStayedInTapRegion
                                && Editor.this.mTouchState.isMultiTapInSameArea()
                                && !Editor.mDisableDoubleTapTextSelection
                                && (isMouse
                                        || Editor.this.isPositionOnText(eventX, eventY)
                                        || Editor.this.mTouchState.isOnHandle())) {
                            if (Editor.this.mTouchState.isDoubleTap()) {
                                Editor.this.selectCurrentWordAndStartDrag();
                            } else if (Editor.this.mTouchState.isTripleClick()) {
                                selectCurrentParagraphAndStartDrag();
                            }
                            Editor.this.mDiscardNextActionUp = true;
                        }
                        this.mGestureStayedInTapRegion = true;
                        this.mHaventMovedEnoughToStartDrag = true;
                        if (Editor.this.mTextView.isDesktopMode()) {
                            this.mStartOffset =
                                    Editor.this.mTextView.getOffsetForPosition(
                                            Editor.this.mTouchState.getLastDownX(),
                                            Editor.this.mTouchState.getLastDownY());
                        }
                    }
                    if (this.mSelectionVelocityTracker == null) {
                        this.mSelectionVelocityTracker = VelocityTracker.obtain();
                    } else {
                        this.mSelectionVelocityTracker.clear();
                    }
                    this.mSelectionVelocityTracker.addMovement(event);
                    this.mSelectionVelocityTracker.computeCurrentVelocity(1);
                    return;
                case 1:
                    if (this.mEndHandle != null) {
                        this.mEndHandle.dismissMagnifier();
                    }
                    if (isDragAcceleratorActive()) {
                        updateSelection(event);
                        this.mIsExpanded = false;
                        Editor.this.mTextView.getParent().requestDisallowInterceptTouchEvent(false);
                        resetDragAcceleratorState();
                        if (Editor.this.mTextView.hasSelection()) {
                            Editor.this.startSelectionActionModeAsync(
                                    this.mHaventMovedEnoughToStartDrag);
                            Editor.this.updateWritingToolkit();
                            break;
                        }
                    } else {
                        return;
                    }
                    break;
                case 2:
                    if (this.mGestureStayedInTapRegion) {
                        ViewConfiguration viewConfig =
                                ViewConfiguration.get(Editor.this.mTextView.getContext());
                        this.mGestureStayedInTapRegion =
                                EditorTouchState.isDistanceWithin(
                                        Editor.this.mTouchState.getLastDownX(),
                                        Editor.this.mTouchState.getLastDownY(),
                                        eventX,
                                        eventY,
                                        viewConfig.getScaledDoubleTapTouchSlop());
                    }
                    if (this.mHaventMovedEnoughToStartDrag) {
                        this.mHaventMovedEnoughToStartDrag =
                                !Editor.this.mTouchState.isMovedEnoughForDrag();
                    }
                    if (isMouse && !isDragAcceleratorActive()) {
                        int offset = Editor.this.mTextView.getOffsetForPosition(eventX, eventY);
                        if (Editor.this.mTextView.hasSelection()
                                && ((!this.mHaventMovedEnoughToStartDrag
                                                || this.mStartOffset != offset)
                                        && offset >= Editor.this.mTextView.getSelectionStart()
                                        && offset <= Editor.this.mTextView.getSelectionEnd())) {
                            Editor.this.startDragAndDrop();
                            return;
                        } else if (this.mStartOffset != offset) {
                            Editor.this.lambda$startActionModeInternal$0();
                            enterDrag(1);
                            Editor.this.mDiscardNextActionUp = true;
                            this.mHaventMovedEnoughToStartDrag = false;
                        }
                    }
                    if (this.mStartHandle == null || !this.mStartHandle.isShowing()) {
                        if (this.mSelectionVelocityTracker != null) {
                            this.mSelectionVelocityTracker.addMovement(event);
                            this.mSelectionVelocityTracker.computeCurrentVelocity(1);
                            Editor.this.mIsMagnifierHideByVelocityTracker =
                                    this.mSelectionVelocityTracker.getYVelocity() > 0.5f
                                            || this.mSelectionVelocityTracker.getYVelocity()
                                                    < -0.5f;
                        }
                        if (Editor.this.mShowMagnifier
                                && !Editor.this.mIsMagnifierHideByVelocityTracker) {
                            Editor.this.updateMagnifierForDrag(event);
                        } else if (Editor.this.mMagnifierAnimator != null) {
                            Editor.this.mMagnifierAnimator.dismiss();
                        }
                        int curTouchOffset =
                                Editor.this.mTextView.getOffsetForPosition(eventX, eventY);
                        if (this.mDragAcceleratorMode != 0) {
                            if (this.mPrevDownTouchOffset < curTouchOffset
                                    && this.mPrevTouchWordEnd < curTouchOffset) {
                                if (this.mPrevTouchOffset < curTouchOffset) {
                                    this.mDragAcceleratorMode = 2;
                                } else if (curTouchOffset < this.mPrevTouchOffset) {
                                    this.mDragAcceleratorMode = 1;
                                }
                            } else if (curTouchOffset < this.mPrevDownTouchOffset
                                    && curTouchOffset < this.mPrevTouchWordStart) {
                                if (this.mPrevTouchOffset < curTouchOffset) {
                                    this.mDragAcceleratorMode = 1;
                                } else if (curTouchOffset < this.mPrevTouchOffset) {
                                    this.mDragAcceleratorMode = 2;
                                }
                            }
                        }
                        this.mPrevTouchOffset = curTouchOffset;
                        updateSelection(event);
                        return;
                    }
                    return;
                case 3:
                    break;
                case 4:
                default:
                    return;
                case 5:
                case 6:
                    if (Editor.this
                            .mTextView
                            .getContext()
                            .getPackageManager()
                            .hasSystemFeature(
                                    PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH_DISTINCT)) {
                        updateMinAndMaxOffsets(event);
                        return;
                    }
                    return;
            }
            Editor.this.mShowMagnifier = false;
            Editor.this.dismissMagnifierForDrag();
            if (this.mSelectionVelocityTracker != null) {
                this.mSelectionVelocityTracker.recycle();
                this.mSelectionVelocityTracker = null;
            }
        }

        private void updateSelection(MotionEvent event) {
            if (Editor.this.mTextView.getLayout() != null) {
                switch (this.mDragAcceleratorMode) {
                    case 1:
                        boolean isMouse = event.isFromSource(8194);
                        if (isMouse
                                && !Editor.this.mTouchState.isDoubleTap()
                                && !Editor.this.mIsSelectedByLongClick) {
                            updateCharacterBasedSelection(event);
                            break;
                        } else {
                            updateCharacterBasedSelectionAfterSelectWord(event);
                            break;
                        }
                        break;
                    case 2:
                        updateWordBasedSelection(event);
                        break;
                    case 3:
                        updateParagraphBasedSelection(event);
                        break;
                }
            }
        }

        private boolean selectCurrentParagraphAndStartDrag() {
            if (Editor.this.mInsertionActionModeRunnable != null) {
                Editor.this.mTextView.removeCallbacks(Editor.this.mInsertionActionModeRunnable);
            }
            Editor.this.lambda$startActionModeInternal$0();
            if (!Editor.this.selectCurrentParagraph()) {
                return false;
            }
            enterDrag(3);
            return true;
        }

        private void updateCharacterBasedSelection(MotionEvent event) {
            int offset = Editor.this.mTextView.getOffsetForPosition(event.getX(), event.getY());
            updateSelectionInternal(this.mStartOffset, offset, event.isFromSource(4098));
        }

        private void updateWordBasedSelection(MotionEvent event) {
            int offset;
            int startOffset;
            if (this.mHaventMovedEnoughToStartDrag) {
                return;
            }
            event.isFromSource(8194);
            ViewConfiguration.get(Editor.this.mTextView.getContext());
            float eventX = event.getX();
            float eventY = event.getY();
            int currLine = Editor.this.mTextView.getLineAtCoordinate(eventY);
            int offset2 = Editor.this.mTextView.getOffsetAtCoordinate(currLine, eventX);
            if (this.mStartOffset < offset2) {
                offset = Editor.this.getWordEnd(offset2);
                startOffset = Editor.this.getWordStart(this.mStartOffset);
            } else {
                offset = Editor.this.getWordStart(offset2);
                startOffset = Editor.this.getWordEnd(this.mStartOffset);
                if (startOffset == offset) {
                    offset = Editor.this.getNextCursorOffset(offset, false);
                }
            }
            this.mLineSelectionIsOn = currLine;
            updateSelectionInternal(startOffset, offset, event.isFromSource(4098));
        }

        private void updateParagraphBasedSelection(MotionEvent event) {
            int offset = Editor.this.mTextView.getOffsetForPosition(event.getX(), event.getY());
            int start = Math.min(offset, this.mStartOffset);
            int end = Math.max(offset, this.mStartOffset);
            long paragraphsRange = Editor.this.getParagraphsRange(start, end);
            int selectionStart = TextUtils.unpackRangeStartFromLong(paragraphsRange);
            int selectionEnd = TextUtils.unpackRangeEndFromLong(paragraphsRange);
            updateSelectionInternal(selectionStart, selectionEnd, event.isFromSource(4098));
        }

        private void updateSelectionInternal(
                int selectionStart, int selectionEnd, boolean fromTouchScreen) {
            boolean performHapticFeedback =
                    fromTouchScreen
                            && Editor.this.mHapticTextHandleEnabled
                            && !(Editor.this.mTextView.getSelectionStart() == selectionStart
                                    && Editor.this.mTextView.getSelectionEnd() == selectionEnd);
            Selection.setSelection(
                    (Spannable) Editor.this.mTextView.getText(), selectionStart, selectionEnd);
            if (performHapticFeedback) {
                Editor.this.mTextView.performHapticFeedback(
                        HapticFeedbackConstants.semGetVibrationIndex(41));
            }
        }

        private void updateMinAndMaxOffsets(MotionEvent event) {
            int pointerCount = event.getPointerCount();
            for (int index = 0; index < pointerCount; index++) {
                int offset =
                        Editor.this.mTextView.getOffsetForPosition(
                                event.getX(index), event.getY(index));
                if (offset < this.mMinTouchOffset) {
                    this.mMinTouchOffset = offset;
                }
                if (offset > this.mMaxTouchOffset) {
                    this.mMaxTouchOffset = offset;
                }
            }
        }

        public int getMinTouchOffset() {
            return this.mMinTouchOffset;
        }

        public int getMaxTouchOffset() {
            return this.mMaxTouchOffset;
        }

        public void resetTouchOffsets() {
            this.mMaxTouchOffset = -1;
            this.mMinTouchOffset = -1;
            resetDragAcceleratorState();
        }

        private void resetDragAcceleratorState() {
            this.mStartOffset = -1;
            this.mDragAcceleratorMode = 0;
            this.mSwitchedLines = false;
            int selectionStart = Editor.this.mTextView.getSelectionStart();
            int selectionEnd = Editor.this.mTextView.getSelectionEnd();
            if (selectionStart < 0 || selectionEnd < 0) {
                Selection.removeSelection((Spannable) Editor.this.mTextView.getText());
            } else if (selectionStart > selectionEnd) {
                Selection.setSelection(
                        (Spannable) Editor.this.mTextView.getText(), selectionEnd, selectionStart);
            }
        }

        public boolean isSelectionStartDragged() {
            return this.mStartHandle != null && this.mStartHandle.isDragging();
        }

        @Override // android.widget.Editor.CursorController
        public boolean isCursorBeingModified() {
            return isDragAcceleratorActive()
                    || isSelectionStartDragged()
                    || (this.mEndHandle != null && this.mEndHandle.isDragging());
        }

        public boolean isDragAcceleratorActive() {
            return this.mDragAcceleratorMode != 0;
        }

        @Override // android.view.ViewTreeObserver.OnTouchModeChangeListener
        public void onTouchModeChanged(boolean isInTouchMode) {
            if (!isInTouchMode) {
                hide();
            }
        }

        @Override // android.widget.Editor.CursorController
        public void onDetached() {
            ViewTreeObserver observer = Editor.this.mTextView.getViewTreeObserver();
            observer.removeOnTouchModeChangeListener(this);
            if (this.mStartHandle != null) {
                this.mStartHandle.onDetached();
            }
            if (this.mEndHandle != null) {
                this.mEndHandle.onDetached();
            }
        }

        @Override // android.widget.Editor.CursorController
        public boolean isActive() {
            return (this.mStartHandle != null && this.mStartHandle.isShowing())
                    || (this.mEndHandle != null && this.mEndHandle.isShowing());
        }

        public void invalidateHandles() {
            if (this.mStartHandle != null) {
                this.mStartHandle.invalidate();
            }
            if (this.mEndHandle != null) {
                this.mEndHandle.invalidate();
            }
        }

        private void updateCharacterBasedSelectionAfterSelectWord(MotionEvent event) {
            int offset = Editor.this.mTextView.getOffsetForPosition(event.getX(), event.getY());
            int startOffset = Editor.this.mTextView.getSelectionStart();
            int endOffset = Editor.this.mTextView.getSelectionEnd();
            int wordStart = Editor.this.getWordStart(this.mStartOffset);
            int wordEnd = Editor.this.getWordEnd(this.mStartOffset);
            if (wordStart > offset || wordEnd < offset) {
                this.mIsExpanded = true;
            }
            if (this.mIsExpanded && startOffset != offset) {
                if (offset < startOffset && offset < endOffset && endOffset == wordEnd) {
                    startOffset = wordEnd;
                }
                Selection.setSelection(
                        (Spannable) Editor.this.mTextView.getText(), startOffset, offset);
            }
        }
    }

    void loadHandleDrawables(boolean overwrite) {
        if (this.mSelectHandleCenter == null || overwrite) {
            this.mSelectHandleCenter = this.mTextView.getTextSelectHandle();
            if (hasInsertionController()) {
                getInsertionController().reloadHandleDrawable();
            }
        }
        if (this.mSelectHandleLeft == null || this.mSelectHandleRight == null || overwrite) {
            this.mSelectHandleLeft = this.mTextView.getTextSelectHandleLeft();
            this.mSelectHandleRight = this.mTextView.getTextSelectHandleRight();
            if (hasSelectionController()) {
                getSelectionController().reloadHandleDrawables();
            }
        }
    }

    private class CorrectionHighlighter {
        private static final int FADE_OUT_DURATION = 400;
        private int mEnd;
        private long mFadingStartTime;
        private int mStart;
        private RectF mTempRectF;
        private final Path mPath = new Path();
        private final Paint mPaint = new Paint(1);

        public CorrectionHighlighter() {
            this.mPaint.setCompatibilityScaling(
                    Editor.this.mTextView.getResources().getCompatibilityInfo().applicationScale);
            this.mPaint.setStyle(Paint.Style.FILL);
        }

        public void highlight(CorrectionInfo info) {
            this.mStart = info.getOffset();
            this.mEnd = this.mStart + info.getNewText().length();
            this.mFadingStartTime = SystemClock.uptimeMillis();
            if (this.mStart < 0 || this.mEnd < 0) {
                stopAnimation();
            }
        }

        public void draw(Canvas canvas, int cursorOffsetVertical) {
            if (updatePath() && updatePaint()) {
                if (cursorOffsetVertical != 0) {
                    canvas.translate(0.0f, cursorOffsetVertical);
                }
                canvas.drawPath(this.mPath, this.mPaint);
                if (cursorOffsetVertical != 0) {
                    canvas.translate(0.0f, -cursorOffsetVertical);
                }
                invalidate(true);
                return;
            }
            stopAnimation();
            invalidate(false);
        }

        private boolean updatePaint() {
            long duration = SystemClock.uptimeMillis() - this.mFadingStartTime;
            if (duration > 400) {
                return false;
            }
            float coef = 1.0f - (duration / 400.0f);
            int highlightColorAlpha = Color.alpha(Editor.this.mTextView.mHighlightColor);
            int color =
                    (Editor.this.mTextView.mHighlightColor & 16777215)
                            + (((int) (highlightColorAlpha * coef)) << 24);
            this.mPaint.setColor(color);
            return true;
        }

        private boolean updatePath() {
            Layout layout = Editor.this.mTextView.getLayout();
            if (layout == null) {
                return false;
            }
            int length = Editor.this.mTextView.getText().length();
            int start = Math.min(length, this.mStart);
            int end = Math.min(length, this.mEnd);
            this.mPath.reset();
            layout.getSelectionPath(
                    Editor.this.mTextView.originalToTransformed(start, 0),
                    Editor.this.mTextView.originalToTransformed(end, 0),
                    this.mPath);
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void invalidate(boolean delayed) {
            if (Editor.this.mTextView.getLayout() == null) {
                return;
            }
            if (this.mTempRectF == null) {
                this.mTempRectF = new RectF();
            }
            this.mPath.computeBounds(this.mTempRectF, false);
            int left = Editor.this.mTextView.getCompoundPaddingLeft();
            int top =
                    Editor.this.mTextView.getExtendedPaddingTop()
                            + Editor.this.mTextView.getVerticalOffset(true);
            if (delayed) {
                Editor.this.mTextView.postInvalidateOnAnimation(
                        ((int) this.mTempRectF.left) + left,
                        ((int) this.mTempRectF.top) + top,
                        ((int) this.mTempRectF.right) + left,
                        ((int) this.mTempRectF.bottom) + top);
            } else {
                Editor.this.mTextView.postInvalidate(
                        (int) this.mTempRectF.left,
                        (int) this.mTempRectF.top,
                        (int) this.mTempRectF.right,
                        (int) this.mTempRectF.bottom);
            }
        }

        private void stopAnimation() {
            Editor.this.mCorrectionHighlighter = null;
        }
    }

    private static class ErrorPopup extends PopupWindow {
        private boolean mAbove;
        private int mPopupInlineErrorAboveBackgroundId;
        private int mPopupInlineErrorBackgroundId;
        private final TextView mView;

        ErrorPopup(TextView v, int width, int height) {
            super(v, width, height);
            this.mAbove = false;
            this.mPopupInlineErrorBackgroundId = 0;
            this.mPopupInlineErrorAboveBackgroundId = 0;
            this.mView = v;
            this.mPopupInlineErrorBackgroundId =
                    getResourceId(this.mPopupInlineErrorBackgroundId, 334);
            this.mView.setBackgroundResource(this.mPopupInlineErrorBackgroundId);
        }

        void fixDirection(boolean above) {
            this.mAbove = above;
            if (above) {
                this.mPopupInlineErrorAboveBackgroundId =
                        getResourceId(this.mPopupInlineErrorAboveBackgroundId, 333);
            } else {
                this.mPopupInlineErrorBackgroundId =
                        getResourceId(this.mPopupInlineErrorBackgroundId, 334);
            }
            this.mView.setBackgroundResource(
                    above
                            ? this.mPopupInlineErrorAboveBackgroundId
                            : this.mPopupInlineErrorBackgroundId);
        }

        private int getResourceId(int currentId, int index) {
            if (currentId == 0) {
                TypedArray styledAttributes =
                        this.mView.getContext().obtainStyledAttributes(android.R.styleable.Theme);
                int currentId2 = styledAttributes.getResourceId(index, 0);
                styledAttributes.recycle();
                return currentId2;
            }
            return currentId;
        }

        @Override // android.widget.PopupWindow
        public void update(int x, int y, int w, int h, boolean force) {
            super.update(x, y, w, h, force);
            boolean above = isAboveAnchor();
            if (above != this.mAbove) {
                fixDirection(above);
            }
        }
    }

    static class InputContentType {
        boolean enterDown;
        Bundle extras;
        int imeActionId;
        CharSequence imeActionLabel;
        LocaleList imeHintLocales;
        int imeOptions = 0;
        TextView.OnEditorActionListener onEditorActionListener;
        String privateImeOptions;

        InputContentType() {}
    }

    static class InputMethodState {
        int mBatchEditNesting;
        int mChangedDelta;
        int mChangedEnd;
        int mChangedStart;
        boolean mContentChanged;
        boolean mCursorChanged;
        final ExtractedText mExtractedText = new ExtractedText();
        ExtractedTextRequest mExtractedTextRequest;
        boolean mSelectionModeChanged;
        int mUpdateCursorAnchorInfoFilter;
        int mUpdateCursorAnchorInfoMode;

        InputMethodState() {}
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isValidRange(CharSequence text, int start, int end) {
        return start >= 0 && start <= end && end <= text.length();
    }

    public static class UndoInputFilter implements InputFilter {
        private static final int MERGE_EDIT_MODE_FORCE_MERGE = 0;
        private static final int MERGE_EDIT_MODE_NEVER_MERGE = 1;
        private static final int MERGE_EDIT_MODE_NORMAL = 2;
        private final Editor mEditor;
        private boolean mExpanding;
        private boolean mHasComposition;
        private boolean mIsUserEdit;
        private boolean mPreviousOperationWasInSameBatchEdit;

        @Retention(RetentionPolicy.SOURCE)
        private @interface MergeMode {}

        public UndoInputFilter(Editor editor) {
            this.mEditor = editor;
        }

        public void saveInstanceState(Parcel parcel) {
            parcel.writeInt(this.mIsUserEdit ? 1 : 0);
            parcel.writeInt(this.mHasComposition ? 1 : 0);
            parcel.writeInt(this.mExpanding ? 1 : 0);
            parcel.writeInt(this.mPreviousOperationWasInSameBatchEdit ? 1 : 0);
        }

        public void restoreInstanceState(Parcel parcel) {
            this.mIsUserEdit = parcel.readInt() != 0;
            this.mHasComposition = parcel.readInt() != 0;
            this.mExpanding = parcel.readInt() != 0;
            this.mPreviousOperationWasInSameBatchEdit = parcel.readInt() != 0;
        }

        public void beginBatchEdit() {
            this.mIsUserEdit = true;
        }

        public void endBatchEdit() {
            this.mIsUserEdit = false;
            this.mPreviousOperationWasInSameBatchEdit = false;
        }

        @Override // android.text.InputFilter
        public CharSequence filter(
                CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            boolean shouldCreateSeparateState;
            if (!canUndoEdit(source, start, end, dest, dstart, dend)) {
                return null;
            }
            boolean hadComposition = this.mHasComposition;
            this.mHasComposition = isComposition(source);
            boolean wasExpanding = this.mExpanding;
            if (end - start != dend - dstart) {
                this.mExpanding = end - start > dend - dstart;
                if (hadComposition && this.mExpanding != wasExpanding) {
                    if (!isHangul(dest)) {
                        shouldCreateSeparateState = true;
                        handleEdit(
                                source, start, end, dest, dstart, dend, shouldCreateSeparateState);
                        return null;
                    }
                }
            }
            shouldCreateSeparateState = false;
            handleEdit(source, start, end, dest, dstart, dend, shouldCreateSeparateState);
            return null;
        }

        void freezeLastEdit() {
            this.mEditor.mUndoManager.beginUpdate("Edit text");
            EditOperation lastEdit = getLastEdit();
            if (lastEdit != null) {
                lastEdit.mFrozen = true;
            }
            this.mEditor.mUndoManager.endUpdate();
        }

        private void handleEdit(
                CharSequence source,
                int start,
                int end,
                Spanned dest,
                int dstart,
                int dend,
                boolean shouldCreateSeparateState) {
            int mergeMode;
            if (isInTextWatcher() || this.mPreviousOperationWasInSameBatchEdit) {
                mergeMode = 0;
            } else if (shouldCreateSeparateState) {
                mergeMode = 1;
            } else {
                mergeMode = 2;
            }
            String newText = TextUtils.substring(source, start, end);
            String oldText = TextUtils.substring(dest, dstart, dend);
            EditOperation edit =
                    new EditOperation(this.mEditor, oldText, dstart, newText, this.mHasComposition);
            if (this.mHasComposition && TextUtils.equals(edit.mNewText, edit.mOldText)) {
                return;
            }
            recordEdit(edit, mergeMode);
        }

        private EditOperation getLastEdit() {
            UndoManager um = this.mEditor.mUndoManager;
            return (EditOperation)
                    um.getLastOperation(EditOperation.class, this.mEditor.mUndoOwner, 1);
        }

        private void recordEdit(EditOperation edit, int mergeMode) {
            UndoManager um = this.mEditor.mUndoManager;
            um.beginUpdate("Edit text");
            EditOperation lastEdit = getLastEdit();
            if (lastEdit == null) {
                um.addOperation(edit, 0);
            } else if (mergeMode == 0) {
                lastEdit.forceMergeWith(edit);
            } else if (!this.mIsUserEdit) {
                um.commitState(this.mEditor.mUndoOwner);
                um.addOperation(edit, 0);
            } else if (mergeMode != 2 || !lastEdit.mergeWith(edit)) {
                um.commitState(this.mEditor.mUndoOwner);
                um.addOperation(edit, 0);
            }
            this.mPreviousOperationWasInSameBatchEdit = this.mIsUserEdit;
            um.endUpdate();
        }

        private boolean canUndoEdit(
                CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            if (this.mEditor.mAllowUndo
                    && !this.mEditor.mUndoManager.isInUndo()
                    && Editor.isValidRange(source, start, end)
                    && Editor.isValidRange(dest, dstart, dend)) {
                return (start == end && dstart == dend) ? false : true;
            }
            return false;
        }

        private static boolean isComposition(CharSequence source) {
            if (!(source instanceof Spannable)) {
                return false;
            }
            Spannable text = (Spannable) source;
            int composeBegin = EditableInputConnection.getComposingSpanStart(text);
            int composeEnd = EditableInputConnection.getComposingSpanEnd(text);
            return composeBegin < composeEnd;
        }

        private boolean isInTextWatcher() {
            CharSequence text = this.mEditor.mTextView.getText();
            return (text instanceof SpannableStringBuilder)
                    && ((SpannableStringBuilder) text).getTextWatcherDepth() > 0;
        }

        private boolean isHangul(Spanned dest) {
            if (TextUtils.isEmpty(dest)) {
                return false;
            }
            char lastChar = dest.charAt(dest.length() - 1);
            return Character.UnicodeBlock.of(lastChar)
                            == Character.UnicodeBlock.HANGUL_COMPATIBILITY_JAMO
                    || Character.UnicodeBlock.of(lastChar) == Character.UnicodeBlock.HANGUL_JAMO
                    || Character.UnicodeBlock.of(lastChar)
                            == Character.UnicodeBlock.HANGUL_JAMO_EXTENDED_A
                    || Character.UnicodeBlock.of(lastChar)
                            == Character.UnicodeBlock.HANGUL_JAMO_EXTENDED_B
                    || Character.UnicodeBlock.of(lastChar)
                            == Character.UnicodeBlock.HANGUL_SYLLABLES;
        }
    }

    public static class EditOperation extends UndoOperation<Editor> {
        public static final Parcelable.ClassLoaderCreator<EditOperation> CREATOR =
                new Parcelable.ClassLoaderCreator<
                        EditOperation>() { // from class: android.widget.Editor.EditOperation.1
                    @Override // android.os.Parcelable.Creator
                    public EditOperation createFromParcel(Parcel in) {
                        return new EditOperation(in, null);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.ClassLoaderCreator
                    public EditOperation createFromParcel(Parcel in, ClassLoader loader) {
                        return new EditOperation(in, loader);
                    }

                    @Override // android.os.Parcelable.Creator
                    public EditOperation[] newArray(int size) {
                        return new EditOperation[size];
                    }
                };
        private static final int TYPE_DELETE = 1;
        private static final int TYPE_INSERT = 0;
        private static final int TYPE_REPLACE = 2;
        private boolean mFrozen;
        private boolean mIsComposition;
        private int mNewCursorPos;
        private String mNewText;
        private int mOldCursorPos;
        private String mOldText;
        private int mStart;
        private int mType;

        public EditOperation(
                Editor editor, String oldText, int dstart, String newText, boolean isComposition) {
            super(editor.mUndoOwner);
            this.mOldText = oldText;
            this.mNewText = newText;
            if (this.mNewText.length() > 0 && this.mOldText.length() == 0) {
                this.mType = 0;
            } else if (this.mNewText.length() == 0 && this.mOldText.length() > 0) {
                this.mType = 1;
            } else {
                this.mType = 2;
            }
            this.mStart = dstart;
            this.mOldCursorPos = editor.mTextView.getSelectionStart();
            this.mNewCursorPos = this.mNewText.length() + dstart;
            this.mIsComposition = isComposition;
        }

        public EditOperation(Parcel src, ClassLoader loader) {
            super(src, loader);
            this.mType = src.readInt();
            this.mOldText = src.readString();
            this.mNewText = src.readString();
            this.mStart = src.readInt();
            this.mOldCursorPos = src.readInt();
            this.mNewCursorPos = src.readInt();
            this.mFrozen = src.readInt() == 1;
            this.mIsComposition = src.readInt() == 1;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mType);
            parcel.writeString(this.mOldText);
            parcel.writeString(this.mNewText);
            parcel.writeInt(this.mStart);
            parcel.writeInt(this.mOldCursorPos);
            parcel.writeInt(this.mNewCursorPos);
            parcel.writeInt(this.mFrozen ? 1 : 0);
            parcel.writeInt(this.mIsComposition ? 1 : 0);
        }

        private int getNewTextEnd() {
            return this.mStart + this.mNewText.length();
        }

        private int getOldTextEnd() {
            return this.mStart + this.mOldText.length();
        }

        @Override // android.content.UndoOperation
        public void commit() {}

        @Override // android.content.UndoOperation
        public void undo() {
            Editor editor = getOwnerData();
            Editable text = (Editable) editor.mTextView.getText();
            modifyText(
                    text,
                    this.mStart,
                    getNewTextEnd(),
                    this.mOldText,
                    this.mStart,
                    this.mOldCursorPos);
        }

        @Override // android.content.UndoOperation
        public void redo() {
            Editor editor = getOwnerData();
            Editable text = (Editable) editor.mTextView.getText();
            modifyText(
                    text,
                    this.mStart,
                    getOldTextEnd(),
                    this.mNewText,
                    this.mStart,
                    this.mNewCursorPos);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean mergeWith(EditOperation edit) {
            if (this.mFrozen) {
                return false;
            }
            switch (this.mType) {
            }
            return false;
        }

        private boolean mergeInsertWith(EditOperation edit) {
            if (edit.mType == 0) {
                if (getNewTextEnd() != edit.mStart) {
                    return false;
                }
                this.mNewText += edit.mNewText;
                this.mNewCursorPos = edit.mNewCursorPos;
                this.mFrozen = edit.mFrozen;
                this.mIsComposition = edit.mIsComposition;
                return true;
            }
            if (!this.mIsComposition
                    || edit.mType != 2
                    || this.mStart > edit.mStart
                    || getNewTextEnd() < edit.getOldTextEnd()) {
                return false;
            }
            this.mNewText =
                    this.mNewText.substring(0, edit.mStart - this.mStart)
                            + edit.mNewText
                            + this.mNewText.substring(
                                    edit.getOldTextEnd() - this.mStart, this.mNewText.length());
            this.mNewCursorPos = edit.mNewCursorPos;
            this.mIsComposition = edit.mIsComposition;
            return true;
        }

        private boolean mergeDeleteWith(EditOperation edit) {
            if (edit.mType != 1 || this.mStart != edit.getOldTextEnd()) {
                return false;
            }
            this.mStart = edit.mStart;
            this.mOldText = edit.mOldText + this.mOldText;
            this.mNewCursorPos = edit.mNewCursorPos;
            this.mIsComposition = edit.mIsComposition;
            return true;
        }

        private boolean mergeReplaceWith(EditOperation edit) {
            if (edit.mType == 0 && getNewTextEnd() == edit.mStart) {
                this.mNewText += edit.mNewText;
                this.mNewCursorPos = edit.mNewCursorPos;
                return true;
            }
            if (!this.mIsComposition) {
                return false;
            }
            if (edit.mType == 1
                    && this.mStart <= edit.mStart
                    && getNewTextEnd() >= edit.getOldTextEnd()) {
                this.mNewText =
                        this.mNewText.substring(0, edit.mStart - this.mStart)
                                + this.mNewText.substring(
                                        edit.getOldTextEnd() - this.mStart, this.mNewText.length());
                if (this.mNewText.isEmpty()) {
                    this.mType = 1;
                }
                this.mNewCursorPos = edit.mNewCursorPos;
                this.mIsComposition = edit.mIsComposition;
                return true;
            }
            if (edit.mType != 2
                    || this.mStart != edit.mStart
                    || !TextUtils.equals(this.mNewText, edit.mOldText)) {
                return false;
            }
            this.mNewText = edit.mNewText;
            this.mNewCursorPos = edit.mNewCursorPos;
            this.mIsComposition = edit.mIsComposition;
            return true;
        }

        public void forceMergeWith(EditOperation edit) {
            if (mergeWith(edit)) {
                return;
            }
            Editor editor = getOwnerData();
            Editable editable = (Editable) editor.mTextView.getText();
            Editable originalText = new SpannableStringBuilder(editable.toString());
            modifyText(
                    originalText,
                    this.mStart,
                    getNewTextEnd(),
                    this.mOldText,
                    this.mStart,
                    this.mOldCursorPos);
            Editable finalText = new SpannableStringBuilder(editable.toString());
            modifyText(
                    finalText,
                    edit.mStart,
                    edit.getOldTextEnd(),
                    edit.mNewText,
                    edit.mStart,
                    edit.mNewCursorPos);
            this.mType = 2;
            this.mNewText = finalText.toString();
            this.mOldText = originalText.toString();
            this.mStart = 0;
            this.mNewCursorPos = edit.mNewCursorPos;
            this.mIsComposition = edit.mIsComposition;
        }

        private static void modifyText(
                Editable text,
                int deleteFrom,
                int deleteTo,
                CharSequence newText,
                int newTextInsertAt,
                int newCursorPos) {
            if (Editor.isValidRange(text, deleteFrom, deleteTo)
                    && newTextInsertAt <= text.length() - (deleteTo - deleteFrom)) {
                if (deleteFrom != deleteTo) {
                    text.delete(deleteFrom, deleteTo);
                }
                if (newText.length() != 0) {
                    text.insert(newTextInsertAt, newText);
                }
            }
            if (newCursorPos >= 0 && newCursorPos <= text.length()) {
                Selection.setSelection(text, newCursorPos);
            }
        }

        private String getTypeString() {
            switch (this.mType) {
                case 0:
                    return "insert";
                case 1:
                    return "delete";
                case 2:
                    return "replace";
                default:
                    return "";
            }
        }

        public String toString() {
            return "[mType="
                    + getTypeString()
                    + ", mOldText="
                    + this.mOldText
                    + ", mNewText="
                    + this.mNewText
                    + ", mStart="
                    + this.mStart
                    + ", mOldCursorPos="
                    + this.mOldCursorPos
                    + ", mNewCursorPos="
                    + this.mNewCursorPos
                    + ", mFrozen="
                    + this.mFrozen
                    + ", mIsComposition="
                    + this.mIsComposition
                    + NavigationBarInflaterView.SIZE_MOD_END;
        }
    }

    static final class ProcessTextIntentActionsHandler {
        private final SparseArray<AccessibilityNodeInfo.AccessibilityAction> mAccessibilityActions;
        private final SparseArray<Intent> mAccessibilityIntents;
        private final Context mContext;
        private final Editor mEditor;
        private final PackageManager mPackageManager;
        private final String mPackageName;
        private final List<ResolveInfo> mSupportedActivities;
        private final TextView mTextView;

        private ProcessTextIntentActionsHandler(Editor editor) {
            this.mAccessibilityIntents = new SparseArray<>();
            this.mAccessibilityActions = new SparseArray<>();
            this.mSupportedActivities = new ArrayList();
            this.mEditor = (Editor) Objects.requireNonNull(editor);
            this.mTextView = (TextView) Objects.requireNonNull(this.mEditor.mTextView);
            this.mContext = (Context) Objects.requireNonNull(this.mTextView.getContext());
            this.mPackageManager =
                    (PackageManager) Objects.requireNonNull(this.mContext.getPackageManager());
            this.mPackageName = (String) Objects.requireNonNull(this.mContext.getPackageName());
        }

        public void onInitializeMenu(Menu menu) {
            String processTextManageAppsStr =
                    Settings.Global.getString(
                            this.mTextView.getContext().getContentResolver(),
                            Settings.Global.SEM_PROCESS_TEXT_MANAGE_APPS);
            loadSupportedActivities();
            int size = this.mSupportedActivities.size();
            for (int i = 0; i < size; i++) {
                ResolveInfo resolveInfo = this.mSupportedActivities.get(i);
                int order = getOrder(resolveInfo);
                if (order < 0) {
                    order = i + 100;
                }
                Log.e("Editor", "label : " + ((Object) getLabel(resolveInfo)));
                if (processTextManageAppsStr != null
                        && resolveInfo.getComponentInfo() != null
                        && processTextManageAppsStr.contains(resolveInfo.getComponentInfo().name)) {
                    menu.add(0, 0, order, getLabel(resolveInfo))
                            .setIcon(loadIcon(resolveInfo))
                            .setIntent(createProcessTextIntentForResolveInfo(resolveInfo))
                            .setShowAsAction(1);
                }
            }
        }

        public boolean performMenuItemAction(MenuItem item) {
            return fireIntent(item.getIntent());
        }

        public void initializeAccessibilityActions() {
            this.mAccessibilityIntents.clear();
            this.mAccessibilityActions.clear();
            int actionId = 0;
            loadSupportedActivities();
            for (ResolveInfo resolveInfo : this.mSupportedActivities) {
                int i = actionId + 1;
                int actionId2 = actionId + 268435712;
                this.mAccessibilityActions.put(
                        actionId2,
                        new AccessibilityNodeInfo.AccessibilityAction(
                                actionId2, getLabel(resolveInfo)));
                this.mAccessibilityIntents.put(
                        actionId2, createProcessTextIntentForResolveInfo(resolveInfo));
                actionId = i;
            }
        }

        public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo nodeInfo) {
            for (int i = 0; i < this.mAccessibilityActions.size(); i++) {
                nodeInfo.addAction(this.mAccessibilityActions.valueAt(i));
            }
        }

        public boolean performAccessibilityAction(int actionId) {
            return fireIntent(this.mAccessibilityIntents.get(actionId));
        }

        private boolean fireIntent(Intent intent) {
            if (intent != null && Intent.ACTION_PROCESS_TEXT.equals(intent.getAction())) {
                String selectedText = this.mTextView.getSelectedText();
                intent.putExtra(
                        Intent.EXTRA_PROCESS_TEXT,
                        (String) TextUtils.trimToParcelableSize(selectedText));
                this.mEditor.mPreserveSelection = true;
                this.mTextView.startActivityForResult(intent, 100);
                return true;
            }
            return false;
        }

        private void loadSupportedActivities() {
            this.mSupportedActivities.clear();
            if (!this.mContext.canStartActivityForResult()) {
                return;
            }
            PackageManager packageManager = this.mTextView.getContext().getPackageManager();
            List<ResolveInfo> unfiltered =
                    packageManager.queryIntentActivities(createProcessTextIntent(), 0);
            for (ResolveInfo info : unfiltered) {
                if (isSupportedActivity(info)
                        && !info.getComponentInfo()
                                .packageName
                                .contains("com.samsung.android.app.interpreter")) {
                    this.mSupportedActivities.add(info);
                }
            }
        }

        private boolean isSupportedActivity(ResolveInfo info) {
            return this.mPackageName.equals(info.activityInfo.packageName)
                    || (info.activityInfo.exported
                            && (info.activityInfo.permission == null
                                    || this.mContext.checkSelfPermission(
                                                    info.activityInfo.permission)
                                            == 0));
        }

        private Intent createProcessTextIntentForResolveInfo(ResolveInfo info) {
            return createProcessTextIntent()
                    .putExtra(Intent.EXTRA_PROCESS_TEXT_READONLY, !this.mTextView.isTextEditable())
                    .setClassName(info.activityInfo.packageName, info.activityInfo.name);
        }

        private Intent createProcessTextIntent() {
            return new Intent().setAction(Intent.ACTION_PROCESS_TEXT).setType("text/plain");
        }

        private CharSequence getLabel(ResolveInfo resolveInfo) {
            return resolveInfo.loadLabel(this.mPackageManager);
        }

        private int getOrder(ResolveInfo resolveInfo) {
            if (!this.mTextView.isThemeDeviceDefault()) {
                return -1;
            }
            String resolveInfoString = resolveInfo.toString();
            return (resolveInfoString.contains("com.sec.android.app.translator")
                            || resolveInfoString.contains("com.google.android.apps.translate"))
                    ? 16
                    : -1;
        }

        private Drawable loadIcon(ResolveInfo resolveInfo) {
            String resolveInfoString = resolveInfo.toString();
            PackageManager packageManager = this.mTextView.getContext().getPackageManager();
            Drawable menuIcon = resolveInfo.loadIcon(packageManager);
            if (resolveInfoString.contains("com.sec.android.app.translator")
                    || resolveInfoString.contains("com.google.android.apps.translate")) {
                return this.mTextView
                        .getContext()
                        .getResources()
                        .getDrawable(R.drawable.tw_floating_popup_button_ic_translate);
            }
            if (menuIcon != null) {
                int iconSize =
                        this.mTextView
                                .getContext()
                                .getResources()
                                .getDrawable(R.drawable.tw_floating_popup_button_ic_selectall)
                                .getIntrinsicWidth();
                menuIcon.setBounds(0, 0, iconSize, iconSize);
                return menuIcon;
            }
            return menuIcon;
        }
    }

    private static final class AccessibilitySmartActions {
        private final SparseArray<Pair<AccessibilityNodeInfo.AccessibilityAction, RemoteAction>>
                mActions;
        private final TextView mTextView;

        private AccessibilitySmartActions(TextView textView) {
            this.mActions = new SparseArray<>();
            this.mTextView = (TextView) Objects.requireNonNull(textView);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addAction(RemoteAction action) {
            int actionId = this.mActions.size() + 268439552;
            this.mActions.put(
                    actionId,
                    new Pair<>(
                            new AccessibilityNodeInfo.AccessibilityAction(
                                    actionId, action.getTitle()),
                            action));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void reset() {
            this.mActions.clear();
        }

        void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo nodeInfo) {
            for (int i = 0; i < this.mActions.size(); i++) {
                nodeInfo.addAction(this.mActions.valueAt(i).first);
            }
        }

        boolean performAccessibilityAction(int actionId) {
            Pair<AccessibilityNodeInfo.AccessibilityAction, RemoteAction> pair =
                    this.mActions.get(actionId);
            if (pair != null) {
                TextClassification.createIntentOnClickListener(pair.second.getActionIntent())
                        .onClick(this.mTextView);
                return true;
            }
            return false;
        }
    }

    private static final class InsertModeController {
        private final TextView mTextView;
        private boolean mUpdatingTransformationMethod;
        private boolean mIsInsertModeActive = false;
        private InsertModeTransformationMethod mInsertModeTransformationMethod = null;
        private final Paint mHighlightPaint = new Paint();
        private final Path mHighlightPath = new Path();

        InsertModeController(TextView textView) {
            this.mTextView = (TextView) Objects.requireNonNull(textView);
            int color = this.mTextView.getTextColors().getDefaultColor();
            this.mHighlightPaint.setColor(
                    ColorUtils.setAlphaComponent(color, (int) (Color.alpha(color) * 0.2f)));
        }

        boolean enterInsertMode(int offset) {
            if (this.mIsInsertModeActive) {
                return false;
            }
            TransformationMethod oldTransformationMethod = this.mTextView.getTransformationMethod();
            if (oldTransformationMethod instanceof OffsetMapping) {
                return false;
            }
            boolean isSingleLine = this.mTextView.isSingleLine();
            this.mInsertModeTransformationMethod =
                    new InsertModeTransformationMethod(
                            offset, isSingleLine, oldTransformationMethod);
            setTransformationMethod(this.mInsertModeTransformationMethod, true);
            Selection.setSelection((Spannable) this.mTextView.getText(), offset);
            this.mIsInsertModeActive = true;
            return true;
        }

        void exitInsertMode() {
            exitInsertMode(true);
        }

        void exitInsertMode(boolean updateText) {
            if (this.mIsInsertModeActive) {
                if (this.mInsertModeTransformationMethod == null
                        || this.mInsertModeTransformationMethod
                                != this.mTextView.getTransformationMethod()) {
                    this.mIsInsertModeActive = false;
                    return;
                }
                int selectionStart = this.mTextView.getSelectionStart();
                int selectionEnd = this.mTextView.getSelectionEnd();
                TransformationMethod oldTransformationMethod =
                        this.mInsertModeTransformationMethod.getOldTransformationMethod();
                setTransformationMethod(oldTransformationMethod, updateText);
                Selection.setSelection(
                        (Spannable) this.mTextView.getText(), selectionStart, selectionEnd);
                this.mIsInsertModeActive = false;
            }
        }

        void onDraw(Canvas canvas) {
            Layout layout;
            if (this.mIsInsertModeActive) {
                CharSequence transformedText = this.mTextView.getTransformed();
                if (!(transformedText instanceof InsertModeTransformationMethod.TransformedText)
                        || (layout = this.mTextView.getLayout()) == null) {
                    return;
                }
                InsertModeTransformationMethod.TransformedText insertModeTransformedText =
                        (InsertModeTransformationMethod.TransformedText) transformedText;
                int highlightStart = insertModeTransformedText.getHighlightStart();
                int highlightEnd = insertModeTransformedText.getHighlightEnd();
                layout.getSelectionPath(highlightStart, highlightEnd, this.mHighlightPath);
                canvas.drawPath(this.mHighlightPath, this.mHighlightPaint);
            }
        }

        private void setTransformationMethod(TransformationMethod method, boolean updateText) {
            this.mUpdatingTransformationMethod = true;
            this.mTextView.setTransformationMethodInternal(method, updateText);
            this.mUpdatingTransformationMethod = false;
        }

        void beforeSetText() {
            if (this.mUpdatingTransformationMethod) {
                return;
            }
            exitInsertMode(false);
        }

        void updateTransformationMethod(TransformationMethod transformationMethod) {
            if (!this.mIsInsertModeActive) {
                setTransformationMethod(transformationMethod, true);
                return;
            }
            int selectionStart = this.mTextView.getSelectionStart();
            int selectionEnd = this.mTextView.getSelectionEnd();
            this.mInsertModeTransformationMethod =
                    this.mInsertModeTransformationMethod.update(
                            transformationMethod, this.mTextView.isSingleLine());
            setTransformationMethod(this.mInsertModeTransformationMethod, true);
            Selection.setSelection(
                    (Spannable) this.mTextView.getText(), selectionStart, selectionEnd);
        }
    }

    boolean enterInsertMode(int offset) {
        if (this.mInsertModeController == null) {
            if (this.mTextView == null) {
                return false;
            }
            this.mInsertModeController = new InsertModeController(this.mTextView);
        }
        return this.mInsertModeController.enterInsertMode(offset);
    }

    void exitInsertMode() {
        if (this.mInsertModeController == null) {
            return;
        }
        this.mInsertModeController.exitInsertMode();
    }

    void setTransformationMethod(TransformationMethod method) {
        if (this.mInsertModeController == null) {
            this.mTextView.setTransformationMethodInternal(method, true);
        } else {
            this.mInsertModeController.updateTransformationMethod(method);
        }
    }

    void beforeSetText() {
        if (this.mInsertModeController == null) {
            return;
        }
        this.mInsertModeController.beforeSetText();
    }

    void onInitializeSmartActionsAccessibilityNodeInfo(AccessibilityNodeInfo nodeInfo) {
        this.mA11ySmartActions.onInitializeAccessibilityNodeInfo(nodeInfo);
    }

    boolean performSmartActionsAccessibilityAction(int actionId) {
        return this.mA11ySmartActions.performAccessibilityAction(actionId);
    }

    static void logCursor(String location, String msgFormat, Object... msgArgs) {
        if (msgFormat != null) {
            Log.d("Editor", location + ": " + String.format(msgFormat, msgArgs));
        } else {
            Log.d("Editor", location);
        }
    }

    private boolean tooLargeTextForMagnifierForDrag() {
        if (this.mMagnifierAnimator == null) {
            return false;
        }
        float magnifierContentHeight =
                Math.round(
                        this.mMagnifierAnimator.mMagnifier.getHeight()
                                / this.mMagnifierAnimator.mMagnifier.getZoom());
        Paint.FontMetrics fontMetrics = this.mTextView.getPaint().getFontMetrics();
        float glyphHeight = fontMetrics.descent - fontMetrics.ascent;
        boolean result = glyphHeight > magnifierContentHeight;
        return result;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dismissMagnifierForDrag() {
        if (this.mMagnifierAnimator != null) {
            this.mMagnifierAnimator.dismiss();
            this.mRenderCursorRegardlessTiming = false;
            resumeBlink();
        }
    }

    private boolean obtainMagnifierShowCoordinatesForDrag(MotionEvent event, PointF showPosInView) {
        int offset = this.mTextView.getOffsetForPosition(event.getX(), event.getY());
        if (offset == -1) {
            return false;
        }
        Layout layout = this.mTextView.getLayout();
        int lineNumber = layout.getLineForOffset(offset);
        int[] textViewLocationOnScreen = new int[2];
        this.mTextView.getLocationOnScreen(textViewLocationOnScreen);
        float touchXInView = event.getRawX() - textViewLocationOnScreen[0];
        float leftBound = this.mTextView.getTotalPaddingLeft() - this.mTextView.getScrollX();
        float rightBound = this.mTextView.getTotalPaddingLeft() - this.mTextView.getScrollX();
        float leftBound2 = leftBound + this.mTextView.getLayout().getLineLeft(lineNumber);
        float rightBound2 = rightBound + this.mTextView.getLayout().getLineRight(lineNumber);
        float contentWidth =
                Math.round(
                        this.mMagnifierAnimator.mMagnifier.getWidth()
                                / this.mMagnifierAnimator.mMagnifier.getZoom());
        if (touchXInView < leftBound2 - (contentWidth / 2.0f)
                || touchXInView > (contentWidth / 2.0f) + rightBound2) {
            return false;
        }
        showPosInView.x = Math.max(leftBound2, Math.min(rightBound2, touchXInView));
        showPosInView.y =
                (((this.mTextView.getLayout().getLineTop(lineNumber)
                                                + this.mTextView
                                                        .getLayout()
                                                        .getLineBottom(lineNumber))
                                        / 2.0f)
                                + this.mTextView.getTotalPaddingTop())
                        - this.mTextView.getScrollY();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateMagnifierForDrag(MotionEvent event) {
        if (getMagnifierAnimator() == null) {
            return;
        }
        PointF showPosInView = new PointF();
        boolean shouldShow =
                !tooLargeTextForMagnifierForDrag()
                        && obtainMagnifierShowCoordinatesForDrag(event, showPosInView);
        if (shouldShow) {
            this.mRenderCursorRegardlessTiming = true;
            this.mTextView.invalidateCursorPath();
            suspendBlink();
            this.mMagnifierAnimator.show(showPosInView.x, showPosInView.y);
            return;
        }
        dismissMagnifierForDrag();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isUniversalSwitchEnable() {
        return Settings.Secure.getInt(
                        this.mTextView.getContext().getContentResolver(), SWITCH_CONTROL_ENABLED, 0)
                == 1;
    }

    public void setUseCtxMenuInDesktopMode(boolean isMouse) {
        if (!this.mTextView.isDesktopMode()) {
            this.mUseCtxMenuInDesktopMode = isMouse;
            return;
        }
        if (this.mDesktopModeManager == null) {
            this.mDesktopModeManager =
                    (SemDesktopModeManager)
                            this.mTextView
                                    .getContext()
                                    .getSystemService(Context.SEM_DESKTOP_MODE_SERVICE);
        }
        if (this.mDesktopModeManager != null) {
            SemDesktopModeState desktopModeState = this.mDesktopModeManager.getDesktopModeState();
            boolean z = true;
            boolean isStandAlone =
                    desktopModeState != null && desktopModeState.getDisplayType() == 101;
            if (!isMouse && isStandAlone) {
                z = false;
            }
            this.mUseCtxMenuInDesktopMode = z;
            return;
        }
        this.mUseCtxMenuInDesktopMode = isMouse;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Layout getActiveLayout() {
        Layout layout = this.mTextView.getLayout();
        Layout hintLayout = this.mTextView.getHintLayout();
        if (layout != null
                && TextUtils.isEmpty(layout.getText())
                && hintLayout != null
                && !TextUtils.isEmpty(hintLayout.getText())) {
            return hintLayout;
        }
        return layout;
    }

    protected void stopTextActionModeFromIME() {
        if (this.mTextActionMode != null) {
            this.mTextActionMode.finish();
        }
        if (this.mInsertionPointCursorController != null) {
            this.mInsertionPointCursorController.hide();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateWritingToolkit() {
        InputMethodManager imm = getInputMethodManager();
        if (imm != null && imm.usingWritingToolkit()) {
            Bundle bundle = new Bundle();
            bundle.putString("newSelection", this.mTextView.getSelectedText());
            imm.sendAppPrivateCommand(
                    this.mTextView, SemInputMethodManagerUtils.ACTION_UPDATE_TOOLKIT_HBD, bundle);
        }
    }

    private boolean canPrintLagLog() {
        String enable = SemSystemProperties.get("persist.keyboard.enable_write_lagLog", "");
        return Boolean.parseBoolean(enable);
    }
}
