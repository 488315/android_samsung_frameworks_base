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
import android.widget.AdapterView;
import android.widget.Editor;
import android.widget.Magnifier;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.window.OnBackInvokedCallback;
import com.android.internal.R;
import com.android.internal.graphics.ColorUtils;
import com.android.internal.inputmethod.EditableInputConnection;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.nano.MetricsProto;
import com.android.internal.transition.EpicenterTranslateClipReveal;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.Preconditions;
import com.android.internal.view.FloatingActionMode;
import com.android.text.flags.Flags;
import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.desktopmode.SemDesktopModeState;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.rune.ViewRune;
import java.lang.Character;
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

/* loaded from: classes5.dex */
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
    private static final boolean mDisableDoubleTapTextSelection = SemCscFeature.getInstance().getBoolean("CscFeature_Framework_DisableDoubleTapTextSelection", false);
    private Float SEP_VERSION;
    private final AccessibilitySmartActions mA11ySmartActions;
    boolean mAllowUndo;
    private final OnBackInvokedCallback mBackCallback;
    private boolean mBackCallbackRegistered;
    private Blink mBlink;
    private float mContextMenuAnchorX;
    private float mContextMenuAnchorY;
    private CorrectionHighlighter mCorrectionHighlighter;
    boolean mCreatedWithASelection;
    private final CursorAnchorInfoNotifier mCursorAnchorInfoNotifier;
    private float mCursorDragDirectionMinXYRatio;
    boolean mCursorVisible;
    ActionMode.Callback mCustomInsertionActionModeCallback;
    ActionMode.Callback mCustomSelectionActionModeCallback;
    private final TextViewOnReceiveContentListener mDefaultOnReceiveContentListener = new TextViewOnReceiveContentListener();
    private SemDesktopModeManager mDesktopModeManager;
    boolean mDiscardNextActionUp;
    private boolean mDrawCursorOnMagnifier;
    Drawable mDrawableForCursor;
    CharSequence mError;
    private ErrorPopup mErrorPopup;
    boolean mErrorWasChanged;
    private boolean mFlagCursorDragFromAnywhereEnabled;
    private boolean mFlagInsertionHandleGesturesEnabled;
    boolean mFrozenWithFocus;
    private final boolean mHapticTextHandleEnabled;
    private boolean mHasPendingRestartInputForSetText;
    boolean mIgnoreActionUpEvent;
    boolean mInBatchEditControllers;
    private float mInitialZoom;
    InputContentType mInputContentType;
    InputMethodState mInputMethodState;
    int mInputType;
    private InsertModeController mInsertModeController;
    private Runnable mInsertionActionModeRunnable;
    private boolean mInsertionControllerEnabled;
    InsertionPointCursorController mInsertionPointCursorController;
    boolean mIsBeingLongClicked;
    boolean mIsBeingLongClickedByAccessibility;
    boolean mIsInsertionActionModeStartPending;
    private boolean mIsMagnifierHideByVelocityTracker;
    boolean mIsSelectedByLongClick;
    private boolean mIsThemeDeviceDefault;
    KeyListener mKeyListener;
    private int mLastButtonState;
    private int mLineChangeSlopMax;
    private int mLineChangeSlopMin;
    private final float mLineSlopRatio;
    private MagnifierMotionAnimator mMagnifierAnimator;
    private final ViewTreeObserver.OnDrawListener mMagnifierOnDrawListener;
    private int mMaxLineHeightForMagnifier;
    private final MetricsLogger mMetricsLogger;
    private int mMinLineHeightForMagnifier;
    private final boolean mNewMagnifierEnabled;
    private final MenuItem.OnMenuItemClickListener mOnContextMenuItemClickListener;
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
    private final Runnable mShowFloatingToolbar;
    private boolean mShowMagnifier;
    boolean mShowSoftInputOnFocus;
    private boolean mShowSoftInputOnFocusInternal;
    private Runnable mShowSuggestionRunnable;
    private SpanController mSpanController;
    SpellChecker mSpellChecker;
    private final SuggestionHelper mSuggestionHelper;
    SuggestionRangeSpan mSuggestionRangeSpan;
    private SuggestionsPopupWindow mSuggestionsPopupWindow;
    private Rect mTempRect;
    private ActionMode mTextActionMode;
    boolean mTextIsSelectable;
    private TextRenderNode[] mTextRenderNodes;
    private final TextView mTextView;
    private boolean mToggleActionMode;
    boolean mTouchFocusSelected;
    private final EditorTouchState mTouchState;
    final UndoInputFilter mUndoInputFilter;
    private final UndoManager mUndoManager;
    private UndoOwner mUndoOwner;
    private final Runnable mUpdateMagnifierRunnable;
    private boolean mUpdateWordIteratorText;
    private boolean mUseCtxMenuInDesktopMode;
    private boolean mWasBlinking;
    private boolean mWasSIPShowing;
    private WordIterator mWordIterator;
    private WordIterator mWordIteratorWithText;
    private final float mYVelocityThreshold;
    private boolean mhadWindowFocus;

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
    public @interface HandleType {
    }

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

        public TextRenderNode(String str) {
            this.renderNode = RenderNode.create(str, null);
        }

        boolean needsRecord() {
            return this.isDirty || !this.renderNode.hasDisplayList();
        }
    }

    public boolean editorShowSoftInput() {
        return this.mShowSoftInputOnFocus || this.mShowSoftInputOnFocusInternal;
    }

    private boolean softInputShown() {
        InputMethodManager inputMethodManager = getInputMethodManager();
        return inputMethodManager != null && inputMethodManager.isInputMethodShown();
    }

    public Editor(TextView textView) {
        UndoManager undoManager = new UndoManager();
        this.mUndoManager = undoManager;
        this.mUndoOwner = undoManager.getOwner("Editor", this);
        this.mUndoInputFilter = new UndoInputFilter(this);
        this.mAllowUndo = true;
        this.mMetricsLogger = new MetricsLogger();
        this.mBackCallback = new OnBackInvokedCallback() { // from class: android.widget.Editor$$ExternalSyntheticLambda0
            @Override // android.window.OnBackInvokedCallback
            public final void onBackInvoked() {
                Editor.this.lambda$startActionModeInternal$0();
            }
        };
        this.mShowMagnifier = false;
        this.mUpdateMagnifierRunnable = new Runnable() { // from class: android.widget.Editor.1
            @Override // java.lang.Runnable
            public void run() {
                Editor.this.mMagnifierAnimator.update();
            }
        };
        this.mMagnifierOnDrawListener = new ViewTreeObserver.OnDrawListener() { // from class: android.widget.Editor.2
            @Override // android.view.ViewTreeObserver.OnDrawListener
            public void onDraw() {
                if (Editor.this.mMagnifierAnimator != null) {
                    Editor.this.mTextView.post(Editor.this.mUpdateMagnifierRunnable);
                }
            }
        };
        this.mHasPendingRestartInputForSetText = false;
        this.mInputType = 0;
        this.mCursorVisible = true;
        this.mShowSoftInputOnFocus = true;
        this.mDrawableForCursor = null;
        this.mDesktopModeManager = null;
        this.mUseCtxMenuInDesktopMode = true;
        this.mhadWindowFocus = false;
        this.mTouchState = new EditorTouchState();
        this.mCursorAnchorInfoNotifier = new CursorAnchorInfoNotifier();
        this.mShowFloatingToolbar = new Runnable() { // from class: android.widget.Editor.3
            @Override // java.lang.Runnable
            public void run() {
                if (Editor.this.mTextActionMode != null) {
                    Editor.this.mTextActionMode.hide(0L);
                }
            }
        };
        this.mIsInsertionActionModeStartPending = false;
        this.mSuggestionHelper = new SuggestionHelper();
        this.mInitialZoom = 1.0f;
        this.mIsThemeDeviceDefault = false;
        this.SEP_VERSION = Float.valueOf(Float.parseFloat("17.0"));
        this.mWasBlinking = false;
        this.mWasSIPShowing = false;
        this.mToggleActionMode = false;
        this.mShowSoftInputOnFocusInternal = false;
        this.mYVelocityThreshold = 0.5f;
        this.mIsMagnifierHideByVelocityTracker = false;
        this.mOnContextMenuItemClickListener = new MenuItem.OnMenuItemClickListener() { // from class: android.widget.Editor.5
            @Override // android.view.MenuItem.OnMenuItemClickListener
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (Editor.this.mProcessTextIntentActionsHandler.performMenuItemAction(menuItem)) {
                    return true;
                }
                return Editor.this.mTextView.onTextContextMenuItem(menuItem.getItemId());
            }
        };
        this.mTextView = textView;
        textView.setFilters(textView.getFilters());
        this.mProcessTextIntentActionsHandler = new ProcessTextIntentActionsHandler();
        this.mA11ySmartActions = new AccessibilitySmartActions(textView);
        this.mHapticTextHandleEnabled = textView.getContext().getResources().getBoolean(R.bool.config_enableHapticTextHandle);
        this.mFlagCursorDragFromAnywhereEnabled = AppGlobals.getIntCoreSetting(WidgetFlags.KEY_ENABLE_CURSOR_DRAG_FROM_ANYWHERE, 1) != 0;
        this.mCursorDragDirectionMinXYRatio = EditorTouchState.getXYRatio(AppGlobals.getIntCoreSetting(WidgetFlags.KEY_CURSOR_DRAG_MIN_ANGLE_FROM_VERTICAL, 45));
        this.mFlagInsertionHandleGesturesEnabled = AppGlobals.getIntCoreSetting(WidgetFlags.KEY_ENABLE_INSERTION_HANDLE_GESTURES, 0) != 0;
        this.mNewMagnifierEnabled = AppGlobals.getIntCoreSetting(WidgetFlags.KEY_ENABLE_NEW_MAGNIFIER, 0) != 0;
        this.mLineSlopRatio = AppGlobals.getFloatCoreSetting(WidgetFlags.KEY_LINE_SLOP_RATIO, 0.5f);
        this.mLineChangeSlopMax = (int) TypedValue.applyDimension(1, 45.0f, textView.getContext().getResources().getDisplayMetrics());
        this.mLineChangeSlopMin = (int) TypedValue.applyDimension(1, 8.0f, textView.getContext().getResources().getDisplayMetrics());
        this.mIsThemeDeviceDefault = textView.isThemeDeviceDefault();
    }

    public boolean getFlagCursorDragFromAnywhereEnabled() {
        return this.mFlagCursorDragFromAnywhereEnabled;
    }

    public void setFlagCursorDragFromAnywhereEnabled(boolean z) {
        this.mFlagCursorDragFromAnywhereEnabled = z;
    }

    public void setCursorDragMinAngleFromVertical(int i) {
        this.mCursorDragDirectionMinXYRatio = EditorTouchState.getXYRatio(i);
    }

    public boolean getFlagInsertionHandleGesturesEnabled() {
        return this.mFlagInsertionHandleGesturesEnabled;
    }

    public void setFlagInsertionHandleGesturesEnabled(boolean z) {
        this.mFlagInsertionHandleGesturesEnabled = z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public MagnifierMotionAnimator getMagnifierAnimator() {
        Magnifier.Builder createBuilderWithOldMagnifierDefaults;
        if (this.mMagnifierAnimator == null) {
            if (this.mNewMagnifierEnabled) {
                createBuilderWithOldMagnifierDefaults = createBuilderWithInlineMagnifierDefaults();
            } else {
                createBuilderWithOldMagnifierDefaults = Magnifier.createBuilderWithOldMagnifierDefaults(this.mTextView);
            }
            this.mMagnifierAnimator = new MagnifierMotionAnimator(createBuilderWithOldMagnifierDefaults.build());
        }
        return this.mMagnifierAnimator;
    }

    private Magnifier.Builder createBuilderWithInlineMagnifierDefaults() {
        Magnifier.Builder builder = new Magnifier.Builder(this.mTextView);
        float f = 1.5f;
        float floatCoreSetting = AppGlobals.getFloatCoreSetting(WidgetFlags.KEY_MAGNIFIER_ZOOM_FACTOR, 1.5f);
        float f2 = 5.5f;
        float floatCoreSetting2 = AppGlobals.getFloatCoreSetting(WidgetFlags.KEY_MAGNIFIER_ASPECT_RATIO, 5.5f);
        if (floatCoreSetting >= 1.2f && floatCoreSetting <= 1.8f) {
            f = floatCoreSetting;
        }
        if (floatCoreSetting2 >= 3.0f && floatCoreSetting2 <= 8.0f) {
            f2 = floatCoreSetting2;
        }
        this.mInitialZoom = f;
        this.mMinLineHeightForMagnifier = (int) TypedValue.applyDimension(1, 20.0f, this.mTextView.getContext().getResources().getDisplayMetrics());
        this.mMaxLineHeightForMagnifier = (int) TypedValue.applyDimension(1, 32.0f, this.mTextView.getContext().getResources().getDisplayMetrics());
        Layout layout = this.mTextView.getLayout();
        int lineForOffset = layout.getLineForOffset(this.mTextView.getSelectionStartTransformed());
        int lineBottom = layout.getLineBottom(lineForOffset, false) - layout.getLineTop(lineForOffset);
        int max = (int) (f2 * Math.max(lineBottom, this.mMinLineHeightForMagnifier));
        builder.setFishEyeStyle().setSize(max, (int) (lineBottom * f)).setSourceSize(max, lineBottom).setElevation(0.0f).setInitialZoom(f).setClippingEnabled(false);
        TypedArray obtainStyledAttributes = this.mTextView.getContext().obtainStyledAttributes(null, R.styleable.Magnifier, R.attr.magnifierStyle, 0);
        builder.setDefaultSourceToMagnifierOffset(obtainStyledAttributes.getDimensionPixelSize(3, 0), obtainStyledAttributes.getDimensionPixelSize(4, 0));
        obtainStyledAttributes.recycle();
        return builder.setSourceBounds(1, 0, 1, 0);
    }

    ParcelableParcel saveInstanceState() {
        ParcelableParcel parcelableParcel = new ParcelableParcel(getClass().getClassLoader());
        Parcel parcel = parcelableParcel.getParcel();
        this.mUndoManager.saveInstanceState(parcel);
        this.mUndoInputFilter.saveInstanceState(parcel);
        return parcelableParcel;
    }

    void restoreInstanceState(ParcelableParcel parcelableParcel) {
        Parcel parcel = parcelableParcel.getParcel();
        this.mUndoManager.restoreInstanceState(parcel, parcelableParcel.getClassLoader());
        this.mUndoInputFilter.restoreInstanceState(parcel);
        this.mUndoOwner = this.mUndoManager.getOwner("Editor", this);
    }

    public TextViewOnReceiveContentListener getDefaultOnReceiveContentListener() {
        return this.mDefaultOnReceiveContentListener;
    }

    void forgetUndoRedo() {
        UndoOwner[] undoOwnerArr = {this.mUndoOwner};
        this.mUndoManager.forgetUndos(undoOwnerArr, -1);
        this.mUndoManager.forgetRedos(undoOwnerArr, -1);
    }

    boolean canUndo() {
        return this.mAllowUndo && this.mUndoManager.countUndos(new UndoOwner[]{this.mUndoOwner}) > 0;
    }

    boolean canRedo() {
        return this.mAllowUndo && this.mUndoManager.countRedos(new UndoOwner[]{this.mUndoOwner}) > 0;
    }

    void undo() {
        if (this.mAllowUndo) {
            this.mUndoManager.undo(new UndoOwner[]{this.mUndoOwner}, 1);
        }
    }

    void redo() {
        if (this.mAllowUndo) {
            this.mUndoManager.redo(new UndoOwner[]{this.mUndoOwner}, 1);
        }
    }

    void replace() {
        if (this.mSuggestionsPopupWindow == null) {
            this.mSuggestionsPopupWindow = new SuggestionsPopupWindow();
        }
        hideCursorAndSpanControllers();
        this.mSuggestionsPopupWindow.show();
        Selection.setSelection((Spannable) this.mTextView.getText(), (this.mTextView.getSelectionStart() + this.mTextView.getSelectionEnd()) / 2);
    }

    void onAttachedToWindow() {
        if (this.mShowErrorAfterAttach) {
            showError();
            this.mShowErrorAfterAttach = false;
        }
        ViewTreeObserver viewTreeObserver = this.mTextView.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            InsertionPointCursorController insertionPointCursorController = this.mInsertionPointCursorController;
            if (insertionPointCursorController != null) {
                viewTreeObserver.addOnTouchModeChangeListener(insertionPointCursorController);
            }
            SelectionModifierCursorController selectionModifierCursorController = this.mSelectionModifierCursorController;
            if (selectionModifierCursorController != null) {
                selectionModifierCursorController.resetTouchOffsets();
                viewTreeObserver.addOnTouchModeChangeListener(this.mSelectionModifierCursorController);
            }
            viewTreeObserver.addOnDrawListener(this.mMagnifierOnDrawListener);
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
        InsertionPointCursorController insertionPointCursorController = this.mInsertionPointCursorController;
        if (insertionPointCursorController != null) {
            insertionPointCursorController.onDetached();
        }
        SelectionModifierCursorController selectionModifierCursorController = this.mSelectionModifierCursorController;
        if (selectionModifierCursorController != null) {
            selectionModifierCursorController.onDetached();
        }
        Runnable runnable = this.mShowSuggestionRunnable;
        if (runnable != null) {
            this.mTextView.removeCallbacks(runnable);
        }
        Runnable runnable2 = this.mInsertionActionModeRunnable;
        if (runnable2 != null) {
            this.mTextView.removeCallbacks(runnable2);
        }
        this.mTextView.removeCallbacks(this.mShowFloatingToolbar);
        discardTextDisplayLists();
        SpellChecker spellChecker = this.mSpellChecker;
        if (spellChecker != null) {
            spellChecker.closeSession();
            this.mSpellChecker = null;
        }
        ViewTreeObserver viewTreeObserver = this.mTextView.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.removeOnDrawListener(this.mMagnifierOnDrawListener);
        }
        hideCursorAndSpanControllers();
        stopTextActionModeWithPreservingSelection();
        this.mDefaultOnReceiveContentListener.clearInputConnectionInfo();
        unregisterOnBackInvokedCallback();
    }

    private void unregisterOnBackInvokedCallback() {
        ViewRootImpl viewRootImpl;
        if (this.mBackCallbackRegistered && (viewRootImpl = getTextView().getViewRootImpl()) != null && viewRootImpl.getOnBackInvokedDispatcher().isOnBackInvokedCallbackEnabled()) {
            viewRootImpl.getOnBackInvokedDispatcher().unregisterOnBackInvokedCallback(this.mBackCallback);
            this.mBackCallbackRegistered = false;
        }
    }

    private void registerOnBackInvokedCallback() {
        ViewRootImpl viewRootImpl;
        if (this.mBackCallbackRegistered || (viewRootImpl = this.mTextView.getViewRootImpl()) == null || !viewRootImpl.getOnBackInvokedDispatcher().isOnBackInvokedCallbackEnabled()) {
            return;
        }
        viewRootImpl.getOnBackInvokedDispatcher().registerOnBackInvokedCallback(0, this.mBackCallback);
        this.mBackCallbackRegistered = true;
    }

    private void discardTextDisplayLists() {
        if (this.mTextRenderNodes == null) {
            return;
        }
        int i = 0;
        while (true) {
            TextRenderNode[] textRenderNodeArr = this.mTextRenderNodes;
            if (i >= textRenderNodeArr.length) {
                return;
            }
            TextRenderNode textRenderNode = textRenderNodeArr[i];
            RenderNode renderNode = textRenderNode != null ? textRenderNode.renderNode : null;
            if (renderNode != null && renderNode.hasDisplayList()) {
                renderNode.discardDisplayList();
            }
            i++;
        }
    }

    private void showError() {
        if (this.mTextView.getWindowToken() == null) {
            this.mShowErrorAfterAttach = true;
            return;
        }
        if (this.mErrorPopup == null) {
            TextView textView = (TextView) LayoutInflater.from(this.mTextView.getContext()).inflate(this.mIsThemeDeviceDefault ? R.layout.tw_textview_hint : R.layout.textview_hint, (ViewGroup) null);
            float f = this.mTextView.getResources().getDisplayMetrics().density;
            ErrorPopup errorPopup = new ErrorPopup(textView, (int) ((200.0f * f) + 0.5f), (int) ((f * 50.0f) + 0.5f));
            this.mErrorPopup = errorPopup;
            errorPopup.setFocusable(false);
            this.mErrorPopup.setInputMethodMode(1);
        }
        TextView textView2 = (TextView) this.mErrorPopup.getContentView();
        chooseSize(this.mErrorPopup, this.mError, textView2);
        textView2.lambda$setTextAsync$0(this.mError);
        this.mErrorPopup.showAsDropDown(this.mTextView, getErrorX(), getErrorY(), 51);
        ErrorPopup errorPopup2 = this.mErrorPopup;
        errorPopup2.fixDirection(errorPopup2.isAboveAnchor());
    }

    public void setError(CharSequence charSequence, Drawable drawable) {
        CharSequence stringOrSpannedString = TextUtils.stringOrSpannedString(charSequence);
        this.mError = stringOrSpannedString;
        this.mErrorWasChanged = true;
        if (stringOrSpannedString == null) {
            setErrorIcon(null);
            ErrorPopup errorPopup = this.mErrorPopup;
            if (errorPopup != null) {
                if (errorPopup.isShowing()) {
                    this.mErrorPopup.dismiss();
                }
                this.mErrorPopup = null;
            }
            this.mShowErrorAfterAttach = false;
            return;
        }
        setErrorIcon(drawable);
        if (this.mTextView.isFocused()) {
            showError();
        }
    }

    private void setErrorIcon(Drawable drawable) {
        TextView.Drawables drawables = this.mTextView.mDrawables;
        if (drawables == null) {
            TextView textView = this.mTextView;
            TextView.Drawables drawables2 = new TextView.Drawables(this.mTextView.getContext());
            textView.mDrawables = drawables2;
            drawables = drawables2;
        }
        drawables.setErrorDrawable(drawable, this.mTextView);
        this.mTextView.resetResolvedDrawables();
        this.mTextView.invalidate();
        this.mTextView.requestLayout();
    }

    private void hideError() {
        ErrorPopup errorPopup = this.mErrorPopup;
        if (errorPopup != null && errorPopup.isShowing()) {
            this.mErrorPopup.dismiss();
        }
        this.mShowErrorAfterAttach = false;
    }

    private int getErrorX() {
        float f = this.mTextView.getResources().getDisplayMetrics().density;
        TextView.Drawables drawables = this.mTextView.mDrawables;
        if (this.mTextView.getLayoutDirection() != 1) {
            return ((this.mTextView.getWidth() - this.mErrorPopup.getWidth()) - this.mTextView.getPaddingRight()) + ((-(drawables != null ? drawables.mDrawableSizeRight : 0)) / 2) + ((int) ((f * 25.0f) + 0.5f));
        }
        return this.mTextView.getPaddingLeft() + (((drawables != null ? drawables.mDrawableSizeLeft : 0) / 2) - ((int) ((f * 25.0f) + 0.5f)));
    }

    private int getErrorY() {
        int compoundPaddingTop = this.mTextView.getCompoundPaddingTop();
        int bottom = ((this.mTextView.getBottom() - this.mTextView.getTop()) - this.mTextView.getCompoundPaddingBottom()) - compoundPaddingTop;
        TextView.Drawables drawables = this.mTextView.mDrawables;
        int i = 0;
        if (this.mTextView.getLayoutDirection() != 1) {
            if (drawables != null) {
                i = drawables.mDrawableHeightRight;
            }
        } else if (drawables != null) {
            i = drawables.mDrawableHeightLeft;
        }
        return (((compoundPaddingTop + ((bottom - i) / 2)) + i) - this.mTextView.getHeight()) - ((int) ((this.mTextView.getResources().getDisplayMetrics().density * 2.0f) + 0.5f));
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
        if (isCursorVisible()) {
            return this.mRenderCursorRegardlessTiming || (SystemClock.uptimeMillis() - this.mShowCursor) % 1000 < 500;
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x004d  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x005d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    void prepareCursorControllers() {
        /*
            r5 = this;
            android.widget.TextView r0 = r5.mTextView
            android.view.View r0 = r0.getRootView()
            android.view.ViewGroup$LayoutParams r0 = r0.getLayoutParams()
            boolean r1 = r0 instanceof android.view.WindowManager.LayoutParams
            r2 = 1
            r3 = 0
            if (r1 == 0) goto L28
            android.view.WindowManager$LayoutParams r0 = (android.view.WindowManager.LayoutParams) r0
            int r1 = r0.type
            r4 = 1000(0x3e8, float:1.401E-42)
            if (r1 < r4) goto L1e
            int r0 = r0.type
            r1 = 1999(0x7cf, float:2.801E-42)
            if (r0 <= r1) goto L28
        L1e:
            android.widget.TextView r0 = r5.mTextView
            android.text.Layout r0 = r0.getLayout()
            if (r0 == 0) goto L28
            r0 = r2
            goto L29
        L28:
            r0 = r3
        L29:
            if (r0 == 0) goto L37
            boolean r1 = r5.mDrawCursorOnMagnifier
            if (r1 != 0) goto L35
            boolean r1 = r5.isCursorVisible()
            if (r1 == 0) goto L37
        L35:
            r1 = r2
            goto L38
        L37:
            r1 = r3
        L38:
            r5.mInsertionControllerEnabled = r1
            if (r0 == 0) goto L45
            android.widget.TextView r0 = r5.mTextView
            boolean r0 = r0.textCanBeSelected()
            if (r0 == 0) goto L45
            goto L46
        L45:
            r2 = r3
        L46:
            r5.mSelectionControllerEnabled = r2
            boolean r0 = r5.mInsertionControllerEnabled
            r1 = 0
            if (r0 != 0) goto L59
            r5.hideInsertionPointCursorController()
            android.widget.Editor$InsertionPointCursorController r0 = r5.mInsertionPointCursorController
            if (r0 == 0) goto L59
            r0.onDetached()
            r5.mInsertionPointCursorController = r1
        L59:
            boolean r0 = r5.mSelectionControllerEnabled
            if (r0 != 0) goto L69
            r5.lambda$startActionModeInternal$0()
            android.widget.Editor$SelectionModifierCursorController r0 = r5.mSelectionModifierCursorController
            if (r0 == 0) goto L69
            r0.onDetached()
            r5.mSelectionModifierCursorController = r1
        L69:
            r5.mToggleActionMode = r3
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.Editor.prepareCursorControllers():void");
    }

    void hideInsertionPointCursorController() {
        InsertionPointCursorController insertionPointCursorController = this.mInsertionPointCursorController;
        if (insertionPointCursorController != null) {
            insertionPointCursorController.hide();
        }
    }

    void hideCursorAndSpanControllers() {
        hideCursorControllers();
        hideSpanControllers();
    }

    private void hideSpanControllers() {
        SpanController spanController = this.mSpanController;
        if (spanController != null) {
            spanController.hide();
        }
    }

    private void hideCursorControllers() {
        if (this.mSuggestionsPopupWindow != null && (this.mTextView.isInExtractedMode() || !this.mSuggestionsPopupWindow.isShowingUp())) {
            this.mSuggestionsPopupWindow.hide();
        }
        hideInsertionPointCursorController();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateSpellCheckSpans(int i, int i2, boolean z) {
        this.mTextView.removeAdjacentSuggestionSpans(i);
        this.mTextView.removeAdjacentSuggestionSpans(i2);
        if (this.mTextView.isTextEditable() && this.mTextView.isSuggestionsEnabled() && !this.mTextView.isInExtractedMode()) {
            InputMethodManager inputMethodManager = getInputMethodManager();
            if (inputMethodManager == null || !inputMethodManager.isInputMethodSuppressingSpellChecker()) {
                if (this.mSpellChecker == null && z) {
                    this.mSpellChecker = new SpellChecker(this.mTextView);
                }
                SpellChecker spellChecker = this.mSpellChecker;
                if (spellChecker != null) {
                    spellChecker.spellCheck(i, i2);
                }
            }
        }
    }

    void onScreenStateChanged(int i) {
        if (i != 0) {
            if (i == 1 && this.mhadWindowFocus) {
                this.mhadWindowFocus = false;
                resumeBlink();
                return;
            }
            return;
        }
        this.mhadWindowFocus = this.mTextView.hasWindowFocus();
        suspendBlink();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void suspendBlink() {
        Blink blink = this.mBlink;
        if (blink != null) {
            blink.cancel();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resumeBlink() {
        Blink blink = this.mBlink;
        if (blink != null) {
            blink.uncancel();
        }
        makeBlink();
    }

    void adjustInputType(boolean z, boolean z2, boolean z3, boolean z4) {
        int i = this.mInputType;
        if ((i & 15) != 1) {
            if ((i & 15) == 2 && z4) {
                this.mInputType = (i & (-4081)) | 16;
                return;
            }
            return;
        }
        if (z || z2) {
            this.mInputType = (i & (-4081)) | 128;
        }
        if (z3) {
            this.mInputType = (this.mInputType & (-4081)) | 224;
        }
    }

    private void chooseSize(PopupWindow popupWindow, CharSequence charSequence, TextView textView) {
        int paddingLeft = textView.getPaddingLeft() + textView.getPaddingRight();
        int paddingTop = textView.getPaddingTop() + textView.getPaddingBottom();
        StaticLayout build = StaticLayout.Builder.obtain(charSequence, 0, charSequence.length(), textView.getPaint(), this.mTextView.getResources().getDimensionPixelSize(R.dimen.textview_error_popup_default_width)).setUseLineSpacingFromFallbacks(textView.isFallbackLineSpacingForStaticLayout()).build();
        float f = 0.0f;
        for (int i = 0; i < build.getLineCount(); i++) {
            f = Math.max(f, build.getLineWidth(i));
        }
        popupWindow.setWidth(paddingLeft + ((int) Math.ceil(f)));
        popupWindow.setHeight(paddingTop + build.getHeight());
    }

    void setFrame() {
        ErrorPopup errorPopup = this.mErrorPopup;
        if (errorPopup != null) {
            chooseSize(this.mErrorPopup, this.mError, (TextView) errorPopup.getContentView());
            this.mErrorPopup.update(this.mTextView, getErrorX(), getErrorY(), this.mErrorPopup.getWidth(), this.mErrorPopup.getHeight());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getWordStart(int i) {
        int prevWordBeginningOnTwoWordsBoundary;
        if (getWordIteratorWithText().isOnPunctuation(getWordIteratorWithText().prevBoundary(i))) {
            prevWordBeginningOnTwoWordsBoundary = getWordIteratorWithText().getPunctuationBeginning(i);
        } else {
            prevWordBeginningOnTwoWordsBoundary = getWordIteratorWithText().getPrevWordBeginningOnTwoWordsBoundary(i);
        }
        return prevWordBeginningOnTwoWordsBoundary == -1 ? i : prevWordBeginningOnTwoWordsBoundary;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getWordEnd(int i) {
        int nextWordEndOnTwoWordBoundary;
        if (getWordIteratorWithText().isAfterPunctuation(getWordIteratorWithText().nextBoundary(i))) {
            nextWordEndOnTwoWordBoundary = getWordIteratorWithText().getPunctuationEnd(i);
        } else {
            nextWordEndOnTwoWordBoundary = getWordIteratorWithText().getNextWordEndOnTwoWordBoundary(i);
        }
        return nextWordEndOnTwoWordBoundary == -1 ? i : nextWordEndOnTwoWordBoundary;
    }

    private boolean needsToSelectAllToSelectWordOrParagraph() {
        if (this.mTextView.hasPasswordTransformationMethod()) {
            return true;
        }
        int inputType = this.mTextView.getInputType();
        int i = inputType & 15;
        int i2 = inputType & InputType.TYPE_MASK_VARIATION;
        return i == 2 || i == 3 || i == 4 || i2 == 16 || i2 == 32 || i2 == 208 || i2 == 176;
    }

    boolean selectCurrentWord() {
        int i;
        int i2;
        if (!this.mTextView.canSelectText()) {
            return false;
        }
        if (needsToSelectAllToSelectWordOrParagraph()) {
            return this.mTextView.selectAllText();
        }
        long lastTouchOffsets = getLastTouchOffsets();
        int unpackRangeStartFromLong = TextUtils.unpackRangeStartFromLong(lastTouchOffsets);
        int unpackRangeEndFromLong = TextUtils.unpackRangeEndFromLong(lastTouchOffsets);
        if (this.mTextView.getKeycodeDpadCenterStatus()) {
            unpackRangeStartFromLong = this.mTextView.getSelectionStart();
            unpackRangeEndFromLong = unpackRangeStartFromLong;
        }
        if (unpackRangeStartFromLong >= 0 && unpackRangeStartFromLong <= this.mTextView.getText().length() && unpackRangeEndFromLong >= 0 && unpackRangeEndFromLong <= this.mTextView.getText().length()) {
            URLSpan[] uRLSpanArr = (URLSpan[]) ((Spanned) this.mTextView.getText()).getSpans(unpackRangeStartFromLong, unpackRangeEndFromLong, URLSpan.class);
            if (uRLSpanArr.length >= 1) {
                URLSpan uRLSpan = uRLSpanArr[0];
                i = ((Spanned) this.mTextView.getText()).getSpanStart(uRLSpan);
                i2 = ((Spanned) this.mTextView.getText()).getSpanEnd(uRLSpan);
            } else {
                WordIterator wordIterator = getWordIterator();
                wordIterator.setCharSequence(this.mTextView.getText(), unpackRangeStartFromLong, unpackRangeEndFromLong);
                int beginning = wordIterator.getBeginning(unpackRangeStartFromLong);
                int end = wordIterator.getEnd(unpackRangeEndFromLong);
                if (beginning == -1 || end == -1 || beginning == end) {
                    long charClusterRange = getCharClusterRange(unpackRangeStartFromLong);
                    int unpackRangeStartFromLong2 = TextUtils.unpackRangeStartFromLong(charClusterRange);
                    int unpackRangeEndFromLong2 = TextUtils.unpackRangeEndFromLong(charClusterRange);
                    i = unpackRangeStartFromLong2;
                    i2 = unpackRangeEndFromLong2;
                } else {
                    i2 = end;
                    i = beginning;
                }
            }
            Selection.setSelection((Spannable) this.mTextView.getText(), i, i2);
            if (i2 > i) {
                return true;
            }
        }
        return false;
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
        long paragraphsRange = getParagraphsRange(TextUtils.unpackRangeStartFromLong(lastTouchOffsets), TextUtils.unpackRangeEndFromLong(lastTouchOffsets));
        int unpackRangeStartFromLong = TextUtils.unpackRangeStartFromLong(paragraphsRange);
        int unpackRangeEndFromLong = TextUtils.unpackRangeEndFromLong(paragraphsRange);
        if (unpackRangeStartFromLong >= unpackRangeEndFromLong) {
            return false;
        }
        Selection.setSelection((Spannable) this.mTextView.getText(), unpackRangeStartFromLong, unpackRangeEndFromLong);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public long getParagraphsRange(int i, int i2) {
        int originalToTransformed = this.mTextView.originalToTransformed(i, 1);
        int originalToTransformed2 = this.mTextView.originalToTransformed(i2, 1);
        Layout layout = this.mTextView.getLayout();
        if (layout == null) {
            return TextUtils.packRangeInLong(-1, -1);
        }
        CharSequence text = layout.getText();
        int lineForOffset = layout.getLineForOffset(originalToTransformed);
        while (lineForOffset > 0 && text.charAt(layout.getLineEnd(lineForOffset - 1) - 1) != '\n') {
            lineForOffset--;
        }
        int lineForOffset2 = layout.getLineForOffset(originalToTransformed2);
        while (lineForOffset2 < layout.getLineCount() - 1 && text.charAt(layout.getLineEnd(lineForOffset2) - 1) != '\n') {
            lineForOffset2++;
        }
        return TextUtils.packRangeInLong(this.mTextView.transformedToOriginal(layout.getLineStart(lineForOffset), 1), this.mTextView.transformedToOriginal(layout.getLineEnd(lineForOffset2), 1));
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
    public int getNextCursorOffset(int i, boolean z) {
        int offsetToRightOf;
        Layout layout = this.mTextView.getLayout();
        if (layout == null) {
            return i;
        }
        int originalToTransformed = this.mTextView.originalToTransformed(i, 1);
        if (z == layout.isRtlCharAt(originalToTransformed)) {
            offsetToRightOf = layout.getOffsetToLeftOf(originalToTransformed);
        } else {
            offsetToRightOf = layout.getOffsetToRightOf(originalToTransformed);
        }
        return this.mTextView.transformedToOriginal(offsetToRightOf, 1);
    }

    private long getCharClusterRange(int i) {
        if (i < this.mTextView.getText().length()) {
            int nextCursorOffset = getNextCursorOffset(i, true);
            return TextUtils.packRangeInLong(getNextCursorOffset(nextCursorOffset, false), nextCursorOffset);
        }
        if (i - 1 >= 0) {
            int nextCursorOffset2 = getNextCursorOffset(i, false);
            return TextUtils.packRangeInLong(nextCursorOffset2, getNextCursorOffset(nextCursorOffset2, true));
        }
        return TextUtils.packRangeInLong(i, i);
    }

    private boolean touchPositionIsInSelection() {
        int selectionStart = this.mTextView.getSelectionStart();
        int selectionEnd = this.mTextView.getSelectionEnd();
        if (selectionStart == selectionEnd) {
            return false;
        }
        if (selectionStart > selectionEnd) {
            Selection.setSelection((Spannable) this.mTextView.getText(), selectionEnd, selectionStart);
            selectionEnd = selectionStart;
            selectionStart = selectionEnd;
        }
        SelectionModifierCursorController selectionController = getSelectionController();
        return selectionController.getMinTouchOffset() >= selectionStart && selectionController.getMaxTouchOffset() < selectionEnd;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public PositionListener getPositionListener() {
        if (this.mPositionListener == null) {
            this.mPositionListener = new PositionListener();
        }
        return this.mPositionListener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isOffsetVisible(int i) {
        Layout layout = this.mTextView.getLayout();
        if (layout == null) {
            return false;
        }
        int originalToTransformed = this.mTextView.originalToTransformed(i, 1);
        int lineBottom = layout.getLineBottom(layout.getLineForOffset(originalToTransformed));
        int primaryHorizontal = (int) layout.getPrimaryHorizontal(originalToTransformed);
        return this.mTextView.isPositionVisible(primaryHorizontal + r0.viewportToContentHorizontalOffset(), lineBottom + this.mTextView.viewportToContentVerticalOffset());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isPositionOnText(float f, float f2) {
        Layout layout = this.mTextView.getLayout();
        if (layout == null) {
            return false;
        }
        if (this.mTextView.getKeycodeDpadCenterStatus()) {
            return this.mTextView.getSelectionStart() != this.mTextView.getText().length() || this.mTextView.hasSelection();
        }
        int lineAtCoordinate = this.mTextView.getLineAtCoordinate(f2);
        float convertToLocalHorizontalCoordinate = this.mTextView.convertToLocalHorizontalCoordinate(f);
        return convertToLocalHorizontalCoordinate >= layout.getLineLeft(lineAtCoordinate) && convertToLocalHorizontalCoordinate <= layout.getLineRight(lineAtCoordinate);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startDragAndDrop() {
        getSelectionActionModeHelper().onSelectionDrag();
        if (this.mTextView.isInExtractedMode()) {
            return;
        }
        int selectionStart = this.mTextView.getSelectionStart();
        int selectionEnd = this.mTextView.getSelectionEnd();
        ClipData newPlainText = ClipData.newPlainText(null, this.mTextView.getTransformedText(selectionStart, selectionEnd));
        DragLocalState dragLocalState = new DragLocalState(this.mTextView, selectionStart, selectionEnd);
        AudioManager audioManager = (AudioManager) this.mTextView.getContext().getSystemService("audio");
        if (audioManager != null) {
            audioManager.playSoundEffect(106);
        } else {
            Log.w("Editor", "performSoundEffect: Couldn't get audio manager");
        }
        this.mTextView.startDragAndDrop(newPlainText, getTextThumbnailBuilder(selectionStart, selectionEnd), dragLocalState, 768);
        lambda$startActionModeInternal$0();
        if (hasSelectionController()) {
            getSelectionController().resetTouchOffsets();
        }
        sendStartDragBroadcast();
    }

    public boolean performLongClick(boolean z) {
        if (this.mIsBeingLongClickedByAccessibility) {
            if (!z) {
                toggleInsertionActionMode();
            }
            return true;
        }
        if (!z && !isPositionOnText(this.mTouchState.getLastDownX(), this.mTouchState.getLastDownY()) && !this.mTouchState.isOnHandle() && this.mInsertionControllerEnabled) {
            int offsetForPosition = this.mTextView.getOffsetForPosition(this.mTouchState.getLastDownX(), this.mTouchState.getLastDownY());
            if (this.mTextView.getKeycodeDpadCenterStatus()) {
                offsetForPosition = this.mTextView.getSelectionStart();
                this.mToggleActionMode = true;
                startInsertionActionMode();
            }
            Selection.setSelection((Spannable) this.mTextView.getText(), offsetForPosition);
            getInsertionController().show();
            this.mIsInsertionActionModeStartPending = true;
            MetricsLogger.action(this.mTextView.getContext(), MetricsProto.MetricsEvent.TEXT_LONGPRESS, 0);
            z = true;
        }
        if (!z && this.mTextActionMode != null) {
            if (touchPositionIsInSelection()) {
                startDragAndDrop();
                MetricsLogger.action(this.mTextView.getContext(), MetricsProto.MetricsEvent.TEXT_LONGPRESS, 2);
            } else {
                lambda$startActionModeInternal$0();
                selectCurrentWordAndStartDrag();
                if (!this.mTextView.isDesktopMode() && this.mTextView.hasSelection()) {
                    this.mShowMagnifier = true;
                }
                MetricsLogger.action(this.mTextView.getContext(), MetricsProto.MetricsEvent.TEXT_LONGPRESS, 1);
            }
            z = true;
        }
        if (!z) {
            z = selectCurrentWordAndStartDrag();
            if (this.mTextView.isInTouchMode()) {
                if (!this.mTextView.isDesktopMode() && this.mTextView.hasSelection()) {
                    this.mShowMagnifier = true;
                }
            } else if (this.mTextView.getKeycodeDpadCenterStatus()) {
                startSelectionActionModeAsync(false);
            }
            if (z) {
                MetricsLogger.action(this.mTextView.getContext(), MetricsProto.MetricsEvent.TEXT_LONGPRESS, 1);
            }
        }
        return z;
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
        return TextUtils.packRangeInLong(selectionController.getMinTouchOffset(), selectionController.getMaxTouchOffset());
    }

    void onFocusChanged(boolean z, int i) {
        this.mShowCursor = SystemClock.uptimeMillis();
        ensureEndedBatchEdit();
        if (z) {
            int selectionStart = this.mTextView.getSelectionStart();
            int selectionEnd = this.mTextView.getSelectionEnd();
            this.mCreatedWithASelection = this.mFrozenWithFocus && this.mTextView.hasSelection() && !(this.mSelectAllOnFocus && selectionStart == 0 && selectionEnd == this.mTextView.getText().length());
            if (!this.mFrozenWithFocus || selectionStart < 0 || selectionEnd < 0) {
                int lastTapPosition = getLastTapPosition();
                if (lastTapPosition >= 0) {
                    Selection.setSelection((Spannable) this.mTextView.getText(), lastTapPosition);
                }
                MovementMethod movementMethod = this.mTextView.getMovementMethod();
                if (movementMethod != null) {
                    TextView textView = this.mTextView;
                    movementMethod.onTakeFocus(textView, (Spannable) textView.getText(), i);
                }
                if ((this.mTextView.isInExtractedMode() || this.mSelectionMoved) && selectionStart >= 0 && selectionEnd >= 0) {
                    Selection.setSelection((Spannable) this.mTextView.getText(), selectionStart, selectionEnd);
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
        SelectionModifierCursorController selectionModifierCursorController = this.mSelectionModifierCursorController;
        if (selectionModifierCursorController != null) {
            selectionModifierCursorController.resetTouchOffsets();
        }
        InsertModeController insertModeController = this.mInsertModeController;
        if (insertModeController != null) {
            insertModeController.exitInsertMode();
        }
        ensureNoSelectionIfNonSelectable();
    }

    private void ensureNoSelectionIfNonSelectable() {
        if (this.mTextView.textCanBeSelected() || !this.mTextView.hasSelection()) {
            return;
        }
        Selection.setSelection((Spannable) this.mTextView.getText(), this.mTextView.length(), this.mTextView.length());
    }

    private void downgradeEasyCorrectionSpans() {
        CharSequence text = this.mTextView.getText();
        if (text instanceof Spannable) {
            Spannable spannable = (Spannable) text;
            SuggestionSpan[] suggestionSpanArr = (SuggestionSpan[]) spannable.getSpans(0, spannable.length(), SuggestionSpan.class);
            for (int i = 0; i < suggestionSpanArr.length; i++) {
                int flags = suggestionSpanArr[i].getFlags();
                if ((flags & 1) != 0 && (flags & 10) == 0) {
                    suggestionSpanArr[i].setFlags(flags & (-2));
                }
            }
        }
    }

    void sendOnTextChanged(int i, int i2, int i3) {
        getSelectionActionModeHelper().onTextChanged(i, i2 + i);
        updateSpellCheckSpans(i, i3 + i, false);
        this.mUpdateWordIteratorText = true;
        hideCursorControllers();
        SelectionModifierCursorController selectionModifierCursorController = this.mSelectionModifierCursorController;
        if (selectionModifierCursorController != null) {
            selectionModifierCursorController.resetTouchOffsets();
        }
        lambda$startActionModeInternal$0();
    }

    private int getLastTapPosition() {
        int minTouchOffset;
        SelectionModifierCursorController selectionModifierCursorController = this.mSelectionModifierCursorController;
        if (selectionModifierCursorController == null || (minTouchOffset = selectionModifierCursorController.getMinTouchOffset()) < 0) {
            return -1;
        }
        return minTouchOffset > this.mTextView.getText().length() ? this.mTextView.getText().length() : minTouchOffset;
    }

    void onWindowFocusChanged(boolean z) {
        if (z) {
            resumeBlink();
            if (!this.mTextView.hasSelection() || extractedTextModeWillBeStarted()) {
                return;
            }
            refreshTextActionMode();
            return;
        }
        suspendBlink();
        InputContentType inputContentType = this.mInputContentType;
        if (inputContentType != null) {
            inputContentType.enterDown = false;
        }
        hideCursorAndSpanControllers();
        stopTextActionModeWithPreservingSelection();
        SuggestionsPopupWindow suggestionsPopupWindow = this.mSuggestionsPopupWindow;
        if (suggestionsPopupWindow != null) {
            suggestionsPopupWindow.onParentLostFocus();
        }
        ensureEndedBatchEdit();
        ensureNoSelectionIfNonSelectable();
    }

    private boolean shouldFilterOutTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.isFromSource(8194) && motionEvent.getToolType(0) != 1 && motionEvent.getToolType(0) != 2) {
            boolean z = ((this.mLastButtonState ^ motionEvent.getButtonState()) & 1) != 0;
            int actionMasked = motionEvent.getActionMasked();
            if ((actionMasked == 0 || actionMasked == 1) && !z) {
                return true;
            }
            if (actionMasked == 2 && !motionEvent.isButtonPressed(1)) {
                return true;
            }
        }
        return false;
    }

    public void onTouchEvent(MotionEvent motionEvent) {
        boolean shouldFilterOutTouchEvent = shouldFilterOutTouchEvent(motionEvent);
        this.mLastButtonState = motionEvent.getButtonState();
        if (shouldFilterOutTouchEvent) {
            if (motionEvent.getActionMasked() == 1) {
                this.mDiscardNextActionUp = true;
                return;
            }
            return;
        }
        this.mTouchState.update(motionEvent, ViewConfiguration.get(this.mTextView.getContext()));
        updateFloatingToolbarVisibility(motionEvent);
        if (hasInsertionController()) {
            getInsertionController().onTouchEvent(motionEvent);
        }
        if (hasSelectionController()) {
            getSelectionController().onTouchEvent(motionEvent);
        }
        Runnable runnable = this.mShowSuggestionRunnable;
        if (runnable != null) {
            this.mTextView.removeCallbacks(runnable);
            this.mShowSuggestionRunnable = null;
        }
        if (motionEvent.getActionMasked() == 1 || motionEvent.getActionMasked() == 3 || motionEvent.getActionMasked() == 6) {
            this.mIsSelectedByLongClick = false;
            this.mShowMagnifier = false;
            dismissMagnifierForDrag();
        }
        if (motionEvent.getActionMasked() == 0) {
            this.mTouchFocusSelected = false;
            this.mIgnoreActionUpEvent = false;
            this.mWasBlinking = shouldBlink() && this.mTextView.hasCallbacks(this.mBlink);
            this.mWasSIPShowing = softInputShown();
        }
        if (motionEvent.getActionMasked() == 0) {
            setUseCtxMenuInDesktopMode(motionEvent.isFromSource(8194));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateFloatingToolbarVisibility(MotionEvent motionEvent) {
        if (this.mTextActionMode != null) {
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked != 1) {
                if (actionMasked == 2) {
                    hideFloatingToolbar(-1);
                    return;
                } else if (actionMasked != 3) {
                    return;
                }
            }
            showFloatingToolbar();
        }
    }

    void hideFloatingToolbar(int i) {
        if (this.mTextActionMode != null) {
            this.mTextView.removeCallbacks(this.mShowFloatingToolbar);
            this.mTextActionMode.hide(i);
        }
    }

    private void showFloatingToolbar() {
        if (this.mTextActionMode == null || !this.mTextView.showUIForTouchScreen()) {
            return;
        }
        this.mTextView.postDelayed(this.mShowFloatingToolbar, ViewConfiguration.getDoubleTapTimeout());
        invalidateActionModeAsync();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public InputMethodManager getInputMethodManager() {
        return (InputMethodManager) this.mTextView.getContext().getSystemService(InputMethodManager.class);
    }

    public void beginBatchEdit() {
        this.mInBatchEditControllers = true;
        InputMethodState inputMethodState = this.mInputMethodState;
        if (inputMethodState != null) {
            int i = inputMethodState.mBatchEditNesting + 1;
            inputMethodState.mBatchEditNesting = i;
            if (i == 1) {
                inputMethodState.mCursorChanged = false;
                inputMethodState.mChangedDelta = 0;
                if (inputMethodState.mContentChanged) {
                    inputMethodState.mChangedStart = 0;
                    inputMethodState.mChangedEnd = this.mTextView.getText().length();
                } else {
                    inputMethodState.mChangedStart = -1;
                    inputMethodState.mChangedEnd = -1;
                    inputMethodState.mContentChanged = false;
                }
                this.mUndoInputFilter.beginBatchEdit();
                this.mTextView.onBeginBatchEdit();
            }
        }
    }

    public void endBatchEdit() {
        this.mInBatchEditControllers = false;
        InputMethodState inputMethodState = this.mInputMethodState;
        if (inputMethodState != null) {
            int i = inputMethodState.mBatchEditNesting - 1;
            inputMethodState.mBatchEditNesting = i;
            if (i == 0) {
                finishBatchEdit(inputMethodState);
            }
        }
    }

    void ensureEndedBatchEdit() {
        InputMethodState inputMethodState = this.mInputMethodState;
        if (inputMethodState == null || inputMethodState.mBatchEditNesting == 0) {
            return;
        }
        inputMethodState.mBatchEditNesting = 0;
        finishBatchEdit(inputMethodState);
    }

    void finishBatchEdit(InputMethodState inputMethodState) {
        this.mTextView.onEndBatchEdit();
        this.mUndoInputFilter.endBatchEdit();
        if (inputMethodState.mContentChanged || inputMethodState.mSelectionModeChanged) {
            this.mTextView.updateAfterEdit();
            reportExtractedText();
        } else if (inputMethodState.mCursorChanged) {
            this.mTextView.invalidateCursor();
        }
        sendUpdateSelection();
        if (this.mTextActionMode != null) {
            CursorController selectionController = this.mTextView.hasSelection() ? getSelectionController() : getInsertionController();
            if (selectionController == null || selectionController.isActive() || selectionController.isCursorBeingModified() || !this.mTextView.showUIForTouchScreen()) {
                return;
            }
            selectionController.show();
        }
    }

    void scheduleRestartInputForSetText() {
        this.mHasPendingRestartInputForSetText = true;
    }

    void maybeFireScheduledRestartInputForSetText() {
        if (this.mHasPendingRestartInputForSetText) {
            InputMethodManager inputMethodManager = getInputMethodManager();
            if (inputMethodManager != null) {
                inputMethodManager.invalidateInput(this.mTextView);
            }
            this.mHasPendingRestartInputForSetText = false;
        }
    }

    boolean extractText(ExtractedTextRequest extractedTextRequest, ExtractedText extractedText) {
        return extractTextInternal(extractedTextRequest, -1, -1, -1, extractedText);
    }

    private boolean extractTextInternal(ExtractedTextRequest extractedTextRequest, int i, int i2, int i3, ExtractedText extractedText) {
        CharSequence text;
        if (extractedTextRequest == null || extractedText == null || (text = this.mTextView.getText()) == null) {
            return false;
        }
        if (i != -2) {
            int length = text.length();
            if (i < 0) {
                extractedText.partialEndOffset = -1;
                extractedText.partialStartOffset = -1;
                i = 0;
            } else {
                int i4 = i2 + i3;
                if (text instanceof Spanned) {
                    Spanned spanned = (Spanned) text;
                    Object[] spans = spanned.getSpans(i, i4, ParcelableSpan.class);
                    int length2 = spans.length;
                    while (length2 > 0) {
                        length2--;
                        int spanStart = spanned.getSpanStart(spans[length2]);
                        if (spanStart < i) {
                            i = spanStart;
                        }
                        int spanEnd = spanned.getSpanEnd(spans[length2]);
                        if (spanEnd > i4) {
                            i4 = spanEnd;
                        }
                    }
                }
                extractedText.partialStartOffset = i;
                extractedText.partialEndOffset = i4 - i3;
                if (i > length) {
                    i = length;
                } else if (i < 0) {
                    i = 0;
                }
                if (i4 <= length) {
                    length = i4 < 0 ? 0 : i4;
                }
            }
            if ((extractedTextRequest.flags & 1) != 0) {
                extractedText.text = text.subSequence(i, length);
            } else {
                extractedText.text = TextUtils.substring(text, i, length);
            }
        } else {
            extractedText.partialStartOffset = 0;
            extractedText.partialEndOffset = 0;
            extractedText.text = "";
        }
        extractedText.flags = 0;
        if (MetaKeyKeyListener.getMetaState(text, 2048) != 0) {
            extractedText.flags |= 2;
        }
        if (this.mTextView.isSingleLine()) {
            extractedText.flags |= 1;
        }
        extractedText.startOffset = 0;
        extractedText.selectionStart = this.mTextView.getSelectionStart();
        extractedText.selectionEnd = this.mTextView.getSelectionEnd();
        extractedText.hint = this.mTextView.getHint();
        return true;
    }

    boolean reportExtractedText() {
        InputMethodManager inputMethodManager;
        InputMethodState inputMethodState = this.mInputMethodState;
        if (inputMethodState == null) {
            return false;
        }
        boolean z = inputMethodState.mContentChanged;
        if (!z && !inputMethodState.mSelectionModeChanged) {
            return false;
        }
        inputMethodState.mContentChanged = false;
        inputMethodState.mSelectionModeChanged = false;
        ExtractedTextRequest extractedTextRequest = inputMethodState.mExtractedTextRequest;
        if (extractedTextRequest == null || (inputMethodManager = getInputMethodManager()) == null) {
            return false;
        }
        if (inputMethodState.mChangedStart < 0 && !z) {
            inputMethodState.mChangedStart = -2;
        }
        if (!extractTextInternal(extractedTextRequest, inputMethodState.mChangedStart, inputMethodState.mChangedEnd, inputMethodState.mChangedDelta, inputMethodState.mExtractedText)) {
            return false;
        }
        inputMethodManager.updateExtractedText(this.mTextView, extractedTextRequest.token, inputMethodState.mExtractedText);
        inputMethodState.mChangedStart = -1;
        inputMethodState.mChangedEnd = -1;
        inputMethodState.mChangedDelta = 0;
        inputMethodState.mContentChanged = false;
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendUpdateSelection() {
        InputMethodManager inputMethodManager;
        int i;
        int i2;
        InputMethodState inputMethodState = this.mInputMethodState;
        if (inputMethodState == null || inputMethodState.mBatchEditNesting > 0 || this.mHasPendingRestartInputForSetText || (inputMethodManager = getInputMethodManager()) == null) {
            return;
        }
        int selectionStart = this.mTextView.getSelectionStart();
        int selectionEnd = this.mTextView.getSelectionEnd();
        if (this.mTextView.getText() instanceof Spannable) {
            Spannable spannable = (Spannable) this.mTextView.getText();
            int composingSpanStart = EditableInputConnection.getComposingSpanStart(spannable);
            i2 = EditableInputConnection.getComposingSpanEnd(spannable);
            i = composingSpanStart;
        } else {
            i = -1;
            i2 = -1;
        }
        inputMethodManager.updateSelection(this.mTextView, selectionStart, selectionEnd, i, i2);
    }

    /* JADX WARN: Removed duplicated region for block: B:47:0x00b2  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00b7  */
    /* JADX WARN: Removed duplicated region for block: B:52:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    void onDraw(android.graphics.Canvas r13, android.text.Layout r14, java.util.List<android.graphics.Path> r15, java.util.List<android.graphics.Paint> r16, android.graphics.Path r17, android.graphics.Paint r18, int r19) {
        /*
            r12 = this;
            android.widget.TextView r0 = r12.mTextView
            int r9 = r0.getSelectionStart()
            android.widget.TextView r0 = r12.mTextView
            int r10 = r0.getSelectionEnd()
            android.widget.Editor$InputMethodState r0 = r12.mInputMethodState
            if (r0 == 0) goto L2d
            int r1 = r0.mBatchEditNesting
            if (r1 != 0) goto L2d
            boolean r1 = r0.mContentChanged
            if (r1 != 0) goto L1c
            boolean r0 = r0.mSelectionModeChanged
            if (r0 == 0) goto L2d
        L1c:
            android.view.inputmethod.InputMethodManager r0 = r12.getInputMethodManager()
            if (r0 == 0) goto L2d
            android.widget.TextView r1 = r12.mTextView
            boolean r0 = r0.hasActiveInputConnection(r1)
            if (r0 == 0) goto L2d
            r12.reportExtractedText()
        L2d:
            boolean r0 = com.android.graphics.hwui.flags.Flags.highContrastTextSmallTextRect()
            r11 = 0
            if (r0 == 0) goto L3d
            boolean r0 = r13.isHighContrastTextEnabled()
            if (r0 == 0) goto L3d
            r0 = 1
            r8 = r0
            goto L3e
        L3d:
            r8 = r11
        L3e:
            if (r8 == 0) goto L50
            r0 = r12
            r1 = r13
            r2 = r14
            r3 = r15
            r4 = r16
            r5 = r17
            r6 = r18
            r7 = r19
            r0.drawLayout(r1, r2, r3, r4, r5, r6, r7, r8)
            goto L52
        L50:
            r7 = r19
        L52:
            android.widget.Editor$CorrectionHighlighter r2 = r12.mCorrectionHighlighter
            if (r2 == 0) goto L59
            r2.draw(r13, r7)
        L59:
            r2 = 0
            if (r17 == 0) goto L9a
            if (r9 != r10) goto L9a
            android.graphics.drawable.Drawable r3 = r12.mDrawableForCursor
            if (r3 == 0) goto L9a
            android.widget.TextView r3 = r12.mTextView
            boolean r3 = r3.hasGesturePreviewHighlight()
            if (r3 != 0) goto L9a
            boolean r3 = com.samsung.android.rune.CoreRune.GRAPHICS_RENDERER_HCF
            if (r3 == 0) goto L95
            if (r9 != 0) goto L95
            android.widget.TextView r3 = r12.mTextView
            boolean r3 = r3.isHighContrastTextEnabled()
            if (r3 == 0) goto L95
            android.widget.TextView r3 = r12.mTextView
            android.text.TextPaint r3 = r3.getPaint()
            float r3 = r3.getHCTStrokeWidth()
            r4 = 1073741824(0x40000000, float:2.0)
            float r3 = r3 / r4
            double r3 = (double) r3
            double r3 = java.lang.Math.floor(r3)
            int r11 = (int) r3
            android.widget.TextView r3 = r12.mTextView
            int r3 = r3.getLayoutDirection()
            if (r3 != 0) goto L95
            int r11 = r11 * (-1)
        L95:
            r12.drawCursor(r13, r11, r7)
            r3 = r2
            goto L9c
        L9a:
            r3 = r17
        L9c:
            android.widget.SelectionActionModeHelper r4 = r12.mSelectionActionModeHelper
            if (r4 == 0) goto Lad
            r4.onDraw(r13)
            android.widget.SelectionActionModeHelper r4 = r12.mSelectionActionModeHelper
            boolean r4 = r4.isDrawingHighlight()
            if (r4 == 0) goto Lad
            r5 = r2
            goto Lae
        Lad:
            r5 = r3
        Lae:
            android.widget.Editor$InsertModeController r2 = r12.mInsertModeController
            if (r2 == 0) goto Lb5
            r2.onDraw(r13)
        Lb5:
            if (r8 != 0) goto Lc2
            r0 = r12
            r1 = r13
            r2 = r14
            r3 = r15
            r4 = r16
            r6 = r18
            r0.drawLayout(r1, r2, r3, r4, r5, r6, r7, r8)
        Lc2:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.Editor.onDraw(android.graphics.Canvas, android.text.Layout, java.util.List, java.util.List, android.graphics.Path, android.graphics.Paint, int):void");
    }

    private void drawLayout(Canvas canvas, Layout layout, List<Path> list, List<Paint> list2, Path path, Paint paint, int i, boolean z) {
        if (this.mTextView.canHaveDisplayList() && canvas.isHardwareAccelerated()) {
            drawHardwareAccelerated(canvas, layout, list, list2, path, paint, i, z);
        } else {
            layout.draw(canvas, list, list2, path, paint, i);
        }
    }

    private void drawHardwareAccelerated(Canvas canvas, Layout layout, List<Path> list, List<Paint> list2, Path path, Paint paint, int i, boolean z) {
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        TextRenderNode textRenderNode;
        boolean z2;
        TextRenderNode textRenderNode2;
        TextRenderNode textRenderNode3;
        Editor editor = this;
        Canvas canvas2 = canvas;
        Layout layout2 = layout;
        long lineRangeForDraw = layout2.getLineRangeForDraw(canvas2);
        int unpackRangeStartFromLong = TextUtils.unpackRangeStartFromLong(lineRangeForDraw);
        int unpackRangeEndFromLong = TextUtils.unpackRangeEndFromLong(lineRangeForDraw);
        if (unpackRangeEndFromLong < 0) {
            return;
        }
        if (!z) {
            layout2.drawWithoutText(canvas2, list, list2, path, paint, i, unpackRangeStartFromLong, unpackRangeEndFromLong);
            layout2 = layout2;
            canvas2 = canvas2;
            i2 = unpackRangeStartFromLong;
            i3 = unpackRangeEndFromLong;
        } else {
            i2 = unpackRangeStartFromLong;
            i3 = unpackRangeEndFromLong;
            layout2.drawBackground(canvas2, i2, i3);
        }
        if (layout2 instanceof DynamicLayout) {
            if (editor.mTextRenderNodes == null) {
                editor.mTextRenderNodes = (TextRenderNode[]) ArrayUtils.emptyArray(TextRenderNode.class);
            }
            DynamicLayout dynamicLayout = (DynamicLayout) layout2;
            int[] blockEndLines = dynamicLayout.getBlockEndLines();
            int[] blockIndices = dynamicLayout.getBlockIndices();
            int numberOfBlocks = dynamicLayout.getNumberOfBlocks();
            int indexFirstChangedBlock = dynamicLayout.getIndexFirstChangedBlock();
            ArraySet<Integer> blocksAlwaysNeedToBeRedrawn = dynamicLayout.getBlocksAlwaysNeedToBeRedrawn();
            int i10 = -1;
            int i11 = 0;
            boolean z3 = true;
            if (blocksAlwaysNeedToBeRedrawn != null) {
                int i12 = 0;
                while (i12 < blocksAlwaysNeedToBeRedrawn.size()) {
                    int blockIndex = dynamicLayout.getBlockIndex(blocksAlwaysNeedToBeRedrawn.valueAt(i12).intValue());
                    if (blockIndex != i10 && (textRenderNode3 = editor.mTextRenderNodes[blockIndex]) != null) {
                        textRenderNode3.needsToBeShifted = true;
                    }
                    i12++;
                    i10 = -1;
                }
            }
            int binarySearch = Arrays.binarySearch(blockEndLines, 0, numberOfBlocks, i2);
            if (binarySearch < 0) {
                binarySearch = -(binarySearch + 1);
            }
            int min = Math.min(indexFirstChangedBlock, binarySearch);
            int i13 = 0;
            while (true) {
                if (min >= numberOfBlocks) {
                    i5 = i11;
                    i6 = i2;
                    i7 = -1;
                    i8 = numberOfBlocks;
                    break;
                }
                int i14 = blockIndices[min];
                if (min >= indexFirstChangedBlock && i14 != -1 && (textRenderNode2 = editor.mTextRenderNodes[i14]) != null) {
                    textRenderNode2.needsToBeShifted = z3;
                }
                if (blockEndLines[min] < i2) {
                    z2 = z3;
                    i6 = i2;
                    i5 = 0;
                } else {
                    z2 = z3;
                    i6 = i2;
                    i7 = -1;
                    i5 = 0;
                    i13 = editor.drawHardwareAcceleratedInner(canvas2, layout2, path, paint, i, blockEndLines, blockIndices, min, numberOfBlocks, i13);
                    if (blockEndLines[min] >= i3) {
                        i8 = Math.max(indexFirstChangedBlock, min + 1);
                        break;
                    }
                }
                min++;
                canvas2 = canvas;
                layout2 = layout;
                i2 = i6;
                i11 = i5;
                z3 = z2;
            }
            if (blocksAlwaysNeedToBeRedrawn != null) {
                int i15 = i5;
                while (i15 < blocksAlwaysNeedToBeRedrawn.size()) {
                    int intValue = blocksAlwaysNeedToBeRedrawn.valueAt(i15).intValue();
                    int blockIndex2 = dynamicLayout.getBlockIndex(intValue);
                    if (blockIndex2 == i7 || (textRenderNode = editor.mTextRenderNodes[blockIndex2]) == null || textRenderNode.needsToBeShifted) {
                        i9 = i15;
                        i13 = editor.drawHardwareAcceleratedInner(canvas, layout, path, paint, i, blockEndLines, blockIndices, intValue, numberOfBlocks, i13);
                    } else {
                        i9 = i15;
                    }
                    i15 = i9 + 1;
                    editor = this;
                }
            }
            canvas2 = canvas;
            layout2 = layout;
            dynamicLayout.setIndexFirstChangedBlock(i8);
            i4 = i6;
        } else {
            i4 = i2;
            layout2.drawText(canvas2, i4, i3);
        }
        if (z) {
            layout2.drawHighlights(canvas2, list, list2, path, paint, i, i4, i3);
        }
    }

    private int drawHardwareAcceleratedInner(Canvas canvas, Layout layout, Path path, Paint paint, int i, int[] iArr, int[] iArr2, int i2, int i3, int i4) {
        int i5;
        int i6;
        int i7 = iArr[i2];
        int i8 = iArr2[i2];
        if (i8 == -1) {
            i8 = getAvailableDisplayListIndex(iArr2, i3, i4);
            iArr2[i2] = i8;
            TextRenderNode textRenderNode = this.mTextRenderNodes[i8];
            if (textRenderNode != null) {
                textRenderNode.isDirty = true;
            }
            i5 = i8 + 1;
        } else {
            i5 = i4;
        }
        TextRenderNode[] textRenderNodeArr = this.mTextRenderNodes;
        if (textRenderNodeArr[i8] == null) {
            textRenderNodeArr[i8] = new TextRenderNode("Text " + i8);
        }
        boolean needsRecord = this.mTextRenderNodes[i8].needsRecord();
        RenderNode renderNode = this.mTextRenderNodes[i8].renderNode;
        if (this.mTextRenderNodes[i8].needsToBeShifted || needsRecord) {
            int i9 = i2 == 0 ? 0 : iArr[i2 - 1] + 1;
            int lineTop = layout.getLineTop(i9);
            int lineBottom = layout.getLineBottom(i7);
            int width = this.mTextView.getWidth();
            if (this.mTextView.getHorizontallyScrolling()) {
                float f = Float.MAX_VALUE;
                float f2 = Float.MIN_VALUE;
                for (int i10 = i9; i10 <= i7; i10++) {
                    f = Math.min(f, layout.getLineLeft(i10));
                    f2 = Math.max(f2, layout.getLineRight(i10));
                }
                int i11 = (int) (f2 + 0.5f);
                i6 = (int) f;
                width = i11;
            } else {
                i6 = 0;
            }
            if (needsRecord) {
                RecordingCanvas beginRecording = renderNode.beginRecording(width - i6, lineBottom - lineTop);
                try {
                    beginRecording.translate(-i6, -lineTop);
                    layout.drawText(beginRecording, i9, i7);
                    if (canPrintLagLog()) {
                        Log.d(TAG_LAG, "drawText");
                    }
                    this.mTextRenderNodes[i8].isDirty = false;
                } finally {
                    renderNode.endRecording();
                    renderNode.setClipToBounds(false);
                }
            }
            renderNode.setLeftTopRightBottom(i6, lineTop, width, lineBottom);
            this.mTextRenderNodes[i8].needsToBeShifted = false;
        }
        ((RecordingCanvas) canvas).drawRenderNode(renderNode);
        return i5;
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:0x000c, code lost:
    
        r6 = r6 + 1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int getAvailableDisplayListIndex(int[] r4, int r5, int r6) {
        /*
            r3 = this;
            android.widget.Editor$TextRenderNode[] r0 = r3.mTextRenderNodes
            int r0 = r0.length
        L3:
            if (r6 >= r0) goto L13
            r1 = 0
        L6:
            if (r1 >= r5) goto L12
            r2 = r4[r1]
            if (r2 != r6) goto Lf
            int r6 = r6 + 1
            goto L3
        Lf:
            int r1 = r1 + 1
            goto L6
        L12:
            return r6
        L13:
            android.widget.Editor$TextRenderNode[] r4 = r3.mTextRenderNodes
            r5 = 0
            java.lang.Object[] r4 = com.android.internal.util.GrowingArrayUtils.append(r4, r0, r5)
            android.widget.Editor$TextRenderNode[] r4 = (android.widget.Editor.TextRenderNode[]) r4
            r3.mTextRenderNodes = r4
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.Editor.getAvailableDisplayListIndex(int[], int, int):int");
    }

    private void drawCursor(Canvas canvas, int i, int i2) {
        boolean z = (i == 0 && i2 == 0) ? false : true;
        if (z) {
            canvas.translate(i, i2);
        }
        Drawable drawable = this.mDrawableForCursor;
        if (drawable != null) {
            drawable.draw(canvas);
        }
        if (z) {
            canvas.translate(-i, -i2);
        }
    }

    void invalidateHandlesAndActionMode() {
        SelectionModifierCursorController selectionModifierCursorController = this.mSelectionModifierCursorController;
        if (selectionModifierCursorController != null) {
            selectionModifierCursorController.invalidateHandles();
        }
        InsertionPointCursorController insertionPointCursorController = this.mInsertionPointCursorController;
        if (insertionPointCursorController != null) {
            insertionPointCursorController.invalidateHandle();
        }
        if (this.mTextActionMode != null) {
            invalidateActionMode();
        }
    }

    void invalidateTextDisplayList(Layout layout, int i, int i2) {
        if (this.mTextRenderNodes == null || !(layout instanceof DynamicLayout)) {
            return;
        }
        if (Flags.insertModeCrashWhenDelete() && this.mTextView.isOffsetMappingAvailable()) {
            invalidateTextDisplayList();
            return;
        }
        int i3 = 0;
        int originalToTransformed = this.mTextView.originalToTransformed(i, 0);
        int originalToTransformed2 = this.mTextView.originalToTransformed(i2, 0);
        int lineForOffset = layout.getLineForOffset(originalToTransformed);
        int lineForOffset2 = layout.getLineForOffset(originalToTransformed2);
        DynamicLayout dynamicLayout = (DynamicLayout) layout;
        int[] blockEndLines = dynamicLayout.getBlockEndLines();
        int[] blockIndices = dynamicLayout.getBlockIndices();
        int numberOfBlocks = dynamicLayout.getNumberOfBlocks();
        while (i3 < numberOfBlocks && blockEndLines[i3] < lineForOffset) {
            i3++;
        }
        while (i3 < numberOfBlocks) {
            int i4 = blockIndices[i3];
            if (i4 != -1) {
                this.mTextRenderNodes[i4].isDirty = true;
            }
            if (blockEndLines[i3] >= lineForOffset2) {
                return;
            } else {
                i3++;
            }
        }
    }

    void invalidateTextDisplayList() {
        if (this.mTextRenderNodes == null) {
            return;
        }
        int i = 0;
        while (true) {
            TextRenderNode[] textRenderNodeArr = this.mTextRenderNodes;
            if (i >= textRenderNodeArr.length) {
                return;
            }
            TextRenderNode textRenderNode = textRenderNodeArr[i];
            if (textRenderNode != null) {
                textRenderNode.isDirty = true;
            }
            i++;
        }
    }

    void updateCursorPosition() {
        loadCursorDrawable();
        if (this.mDrawableForCursor == null) {
            return;
        }
        Layout activeLayout = getActiveLayout();
        int originalToTransformed = this.mTextView.originalToTransformed(this.mTextView.getSelectionStart(), 1);
        int lineForOffset = activeLayout.getLineForOffset(originalToTransformed);
        updateCursorPosition(activeLayout.getLineTop(lineForOffset), activeLayout.getLineBottom(lineForOffset, false), activeLayout.getPrimaryHorizontal(originalToTransformed, activeLayout.shouldClampCursor(lineForOffset)));
    }

    void refreshTextActionMode() {
        if (extractedTextModeWillBeStarted()) {
            this.mRestartActionModeOnNextRefresh = false;
            return;
        }
        boolean hasSelection = this.mTextView.hasSelection();
        SelectionModifierCursorController selectionController = getSelectionController();
        InsertionPointCursorController insertionController = getInsertionController();
        if ((selectionController != null && selectionController.isCursorBeingModified()) || (insertionController != null && insertionController.isCursorBeingModified())) {
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
        } else {
            ActionMode actionMode = this.mTextActionMode;
            if (actionMode != null) {
                actionMode.invalidateContentRect();
            }
        }
        this.mRestartActionModeOnNextRefresh = false;
    }

    void startInsertionActionMode() {
        Runnable runnable = this.mInsertionActionModeRunnable;
        if (runnable != null) {
            this.mTextView.removeCallbacks(runnable);
        }
        if (extractedTextModeWillBeStarted()) {
            return;
        }
        lambda$startActionModeInternal$0();
        if ((this.mUseCtxMenuInDesktopMode && this.mTextView.isDesktopMode()) || isUniversalSwitchEnable()) {
            Log.e("Editor", "Action mode didn't start because Universal Switch / Desktop mode was enabled");
            return;
        }
        this.mTextActionMode = this.mTextView.startActionMode(new TextActionModeCallback(1), 1);
        registerOnBackInvokedCallback();
        if (this.mTextActionMode == null || getInsertionController() == null) {
            return;
        }
        getInsertionController().show();
    }

    TextView getTextView() {
        return this.mTextView;
    }

    ActionMode getTextActionMode() {
        return this.mTextActionMode;
    }

    void setRestartActionModeOnNextRefresh(boolean z) {
        this.mRestartActionModeOnNextRefresh = z;
    }

    void startSelectionActionModeAsync(boolean z) {
        getSelectionActionModeHelper().startSelectionActionModeAsync(z);
    }

    void startLinkActionModeAsync(int i, int i2) {
        if (this.mTextView.getText() instanceof Spannable) {
            lambda$startActionModeInternal$0();
            this.mRequestingLinkActionMode = true;
            getSelectionActionModeHelper().startLinkActionModeAsync(i, i2);
        }
    }

    void invalidateActionModeAsync() {
        getSelectionActionModeHelper().invalidateActionModeAsync();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void invalidateActionMode() {
        ActionMode actionMode = this.mTextActionMode;
        if (actionMode != null) {
            actionMode.invalidate();
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
        Runnable runnable = this.mInsertionActionModeRunnable;
        if (runnable != null) {
            this.mTextView.removeCallbacks(runnable);
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
        if (selectionController == null) {
            return true;
        }
        selectionController.enterDrag(2);
        return true;
    }

    boolean checkField() {
        if (this.mTextView.canSelectText() && this.mTextView.requestFocus()) {
            return true;
        }
        Log.w("TextView", "TextView does not support text selection. Selection cancelled.");
        return false;
    }

    boolean startActionModeInternal(int i) {
        InputMethodManager inputMethodManager;
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
        if ((i != 2 && (!checkField() || !this.mTextView.hasSelection())) || !this.mTextView.showUIForTouchScreen()) {
            return false;
        }
        if ((!this.mUseCtxMenuInDesktopMode || !this.mTextView.isDesktopMode()) && !isUniversalSwitchEnable()) {
            this.mTextActionMode = this.mTextView.startActionMode(new TextActionModeCallback(i), 1);
            registerOnBackInvokedCallback();
        } else {
            Log.e("Editor", "Action mode didn't start because Universal Switch / Desktop mode was enabled");
        }
        boolean z = this.mTextView.isTextEditable() || this.mTextView.isTextSelectable();
        if (i == 2 && !z) {
            ActionMode actionMode = this.mTextActionMode;
            if (actionMode instanceof FloatingActionMode) {
                ((FloatingActionMode) actionMode).setOutsideTouchable(true, new PopupWindow.OnDismissListener() { // from class: android.widget.Editor$$ExternalSyntheticLambda1
                    @Override // android.widget.PopupWindow.OnDismissListener
                    public final void onDismiss() {
                        Editor.this.lambda$startActionModeInternal$0();
                    }
                });
            }
        }
        boolean z2 = this.mTextActionMode != null;
        if (z2 && this.mTextView.isTextEditable() && !this.mTextView.isTextSelectable() && this.mShowSoftInputOnFocus && (inputMethodManager = getInputMethodManager()) != null) {
            inputMethodManager.showSoftInput(this.mTextView, 0, null);
        }
        return z2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean extractedTextModeWillBeStarted() {
        InputMethodManager inputMethodManager;
        return (this.mTextView.isInExtractedMode() || (inputMethodManager = getInputMethodManager()) == null || !inputMethodManager.isFullscreenMode()) ? false : true;
    }

    boolean shouldOfferToShowSuggestions() {
        CharSequence text = this.mTextView.getText();
        if (!(text instanceof Spannable)) {
            return false;
        }
        Spannable spannable = (Spannable) text;
        int selectionStart = this.mTextView.getSelectionStart();
        int selectionEnd = this.mTextView.getSelectionEnd();
        SuggestionSpan[] suggestionSpanArr = (SuggestionSpan[]) spannable.getSpans(selectionStart, selectionEnd, SuggestionSpan.class);
        if (suggestionSpanArr.length == 0) {
            return false;
        }
        if (selectionStart == selectionEnd) {
            for (int i = 0; i < suggestionSpanArr.length && (suggestionSpanArr[i].getFlags() & 12288) == 0; i++) {
                if (suggestionSpanArr[i].getSuggestions().length > 0) {
                    return true;
                }
            }
            return false;
        }
        int length = this.mTextView.getText().length();
        int length2 = this.mTextView.getText().length();
        boolean z = false;
        int i2 = 0;
        int i3 = 0;
        for (int i4 = 0; i4 < suggestionSpanArr.length; i4++) {
            if ((suggestionSpanArr[i4].getFlags() & 12288) != 0) {
                return false;
            }
            int spanStart = spannable.getSpanStart(suggestionSpanArr[i4]);
            int spanEnd = spannable.getSpanEnd(suggestionSpanArr[i4]);
            length = Math.min(length, spanStart);
            i3 = Math.max(i3, spanEnd);
            if (selectionStart >= spanStart && selectionStart <= spanEnd) {
                z = z || suggestionSpanArr[i4].getSuggestions().length > 0;
                length2 = Math.min(length2, spanStart);
                i2 = Math.max(i2, spanEnd);
            }
        }
        return z && length2 < i2 && length >= length2 && i3 <= i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isCursorInsideEasyCorrectionSpan() {
        for (SuggestionSpan suggestionSpan : (SuggestionSpan[]) ((Spannable) this.mTextView.getText()).getSpans(this.mTextView.getSelectionStart(), this.mTextView.getSelectionEnd(), SuggestionSpan.class)) {
            if ((suggestionSpan.getFlags() & 1) != 0) {
                return true;
            }
        }
        return false;
    }

    void onTouchUpEvent(MotionEvent motionEvent) {
        int i;
        boolean z;
        InsertionPointCursorController insertionController;
        if (getSelectionActionModeHelper().resetSelection(getTextView().getOffsetForPosition(motionEvent.getX(), motionEvent.getY()))) {
            return;
        }
        int i2 = 0;
        boolean z2 = this.mSelectAllOnFocus && this.mTextView.didTouchFocusSelect();
        if ((getInsertionController() == null || !getInsertionController().isActive()) && (this.mTextView.getText() == null || this.mTextView.getText().length() != 0)) {
            i = -1;
            z = false;
        } else {
            i = this.mTextView.getSelectionStart();
            z = true;
        }
        hideCursorAndSpanControllers();
        lambda$startActionModeInternal$0();
        CharSequence text = this.mTextView.getText();
        if (z2 || text.length() < 0) {
            return;
        }
        int offsetForPosition = this.mTextView.getOffsetForPosition(motionEvent.getX(), motionEvent.getY());
        boolean z3 = this.mRequestingLinkActionMode;
        if (!z3) {
            Selection.setSelection((Spannable) text, offsetForPosition);
            SpellChecker spellChecker = this.mSpellChecker;
            if (spellChecker != null) {
                spellChecker.onSelectionChanged();
            }
        }
        if (extractedTextModeWillBeStarted()) {
            return;
        }
        if (isCursorInsideEasyCorrectionSpan()) {
            Runnable runnable = this.mInsertionActionModeRunnable;
            if (runnable != null) {
                this.mTextView.removeCallbacks(runnable);
            }
            SuggestionSpan[] suggestionSpanArr = (SuggestionSpan[]) ((Spannable) this.mTextView.getText()).getSpans(this.mTextView.getSelectionStart(), this.mTextView.getSelectionEnd(), SuggestionSpan.class);
            while (true) {
                if (i2 < suggestionSpanArr.length) {
                    if ((suggestionSpanArr[i2].getFlags() & 12288) != 0) {
                        break;
                    } else {
                        i2++;
                    }
                } else {
                    this.mShowSuggestionRunnable = new Runnable() { // from class: android.widget.Editor$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            Editor.this.replace();
                        }
                    };
                    break;
                }
            }
            this.mTextView.postDelayed(this.mShowSuggestionRunnable, ViewConfiguration.getDoubleTapTimeout());
            return;
        }
        if (hasInsertionController()) {
            if (!z3 && this.mTextView.showUIForTouchScreen()) {
                if (!this.mShowSoftInputOnFocusInternal) {
                    this.mShowSoftInputOnFocusInternal = softInputShown();
                }
                if (this.mTextView.getText() != null && this.mTextView.getText().length() == 0) {
                    if (!this.mWasBlinking || this.mShowSoftInputOnFocus != this.mWasSIPShowing || (insertionController = getInsertionController()) == null || insertionController.isActive()) {
                        return;
                    }
                    insertionController.show();
                    startInsertionActionMode();
                    return;
                }
                if (z && i == this.mTextView.getSelectionStart() && !this.mToggleActionMode) {
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

    final void onTextOperationUserChanged() {
        SpellChecker spellChecker = this.mSpellChecker;
        if (spellChecker != null) {
            spellChecker.resetSession();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: stopTextActionMode, reason: merged with bridge method [inline-methods] */
    public void lambda$startActionModeInternal$0() {
        ActionMode actionMode = this.mTextActionMode;
        if (actionMode != null) {
            actionMode.finish();
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
            this.mTextView.getViewTreeObserver().addOnTouchModeChangeListener(this.mInsertionPointCursorController);
        }
        return this.mInsertionPointCursorController;
    }

    public SelectionModifierCursorController getSelectionController() {
        if (!this.mSelectionControllerEnabled) {
            return null;
        }
        if (this.mSelectionModifierCursorController == null) {
            this.mSelectionModifierCursorController = new SelectionModifierCursorController();
            this.mTextView.getViewTreeObserver().addOnTouchModeChangeListener(this.mSelectionModifierCursorController);
        }
        return this.mSelectionModifierCursorController;
    }

    public Drawable getCursorDrawable() {
        return this.mDrawableForCursor;
    }

    private void updateCursorPosition(int i, int i2, float f) {
        loadCursorDrawable();
        int clampHorizontalPosition = clampHorizontalPosition(this.mDrawableForCursor, f);
        int round = Math.round(this.mDrawableForCursor.getIntrinsicWidth() * this.mTextView.getCursorThicknessScale());
        int round2 = Math.round((i2 - i) * ((getInsertionController() == null || !getInsertionController().getHandle().shouldMagnifierCursorAdjust()) ? 0.0f : 0.2f));
        this.mDrawableForCursor.setBounds(clampHorizontalPosition, (i + round2) - this.mTempRect.top, round + clampHorizontalPosition, (i2 - round2) + this.mTempRect.bottom);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int clampHorizontalPosition(Drawable drawable, float f) {
        int i;
        float max = Math.max(0.5f, f - 0.5f);
        if (this.mTempRect == null) {
            this.mTempRect = new Rect();
        }
        if (drawable != null) {
            drawable.getPadding(this.mTempRect);
            i = drawable.getIntrinsicWidth();
        } else {
            this.mTempRect.setEmpty();
            i = 0;
        }
        int scrollX = this.mTextView.getScrollX();
        float f2 = max - scrollX;
        int width = (this.mTextView.getWidth() - this.mTextView.getCompoundPaddingLeft()) - this.mTextView.getCompoundPaddingRight();
        float f3 = width;
        if (f2 >= f3 - 1.0f) {
            return (width + scrollX) - (i - this.mTempRect.right);
        }
        if (Math.abs(f2) <= 1.0f || (TextUtils.isEmpty(this.mTextView.getText()) && 1048576 - scrollX <= f3 + 1.0f && max <= 1.0f)) {
            return scrollX - this.mTempRect.left;
        }
        return ((int) max) - this.mTempRect.left;
    }

    public void onCommitCorrection(CorrectionInfo correctionInfo) {
        CorrectionHighlighter correctionHighlighter = this.mCorrectionHighlighter;
        if (correctionHighlighter == null) {
            this.mCorrectionHighlighter = new CorrectionHighlighter();
        } else {
            correctionHighlighter.invalidate(false);
        }
        this.mCorrectionHighlighter.highlight(correctionInfo);
        this.mUndoInputFilter.freezeLastEdit();
    }

    void onScrollChanged() {
        PositionListener positionListener = this.mPositionListener;
        if (positionListener != null) {
            positionListener.onScrollChanged();
        }
        ActionMode actionMode = this.mTextActionMode;
        if (actionMode != null) {
            actionMode.invalidateContentRect();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean shouldBlink() {
        int selectionStart;
        int selectionEnd;
        if (isCursorVisible() && this.mTextView.isFocused()) {
            int windowVisibility = this.mTextView.getWindowVisibility();
            TextView textView = this.mTextView;
            if (windowVisibility == 0 && (selectionStart = textView.getSelectionStart()) >= 0 && (selectionEnd = this.mTextView.getSelectionEnd()) >= 0 && selectionStart == selectionEnd) {
                return true;
            }
        }
        return false;
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
        Blink blink = this.mBlink;
        if (blink != null) {
            this.mTextView.removeCallbacks(blink);
        }
    }

    public boolean isBlinking() {
        if (this.mBlink == null) {
            return false;
        }
        return !r0.mCancelled;
    }

    private class Blink implements Runnable {
        private boolean mCancelled;

        private Blink() {
        }

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
            if (this.mCancelled) {
                return;
            }
            Editor.this.mTextView.removeCallbacks(this);
            this.mCancelled = true;
        }

        void uncancel() {
            this.mCancelled = false;
        }
    }

    private View.DragShadowBuilder getTextThumbnailBuilder(int i, int i2) {
        int round;
        FrameLayout frameLayout = (FrameLayout) View.inflate(this.mTextView.getContext(), R.layout.sem_text_drag_thumbnail, null);
        TextView textView = (TextView) frameLayout.getChildAt(1);
        if (frameLayout == null) {
            throw new IllegalArgumentException("Unable to inflate text drag thumbnail");
        }
        textView.lambda$setTextAsync$0(this.mTextView.getTransformedText(i, i2));
        frameLayout.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        Resources resources = this.mTextView.getResources();
        if (r8.widthPixels / resources.getDisplayMetrics().density < 480.0f) {
            round = Math.round(r8.widthPixels * 0.75f);
        } else {
            round = Math.round(r8.widthPixels * SHADOW_VIEW_MAX_WIDTH_TABLET);
        }
        int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.sem_text_drag_thumbnail_min_width);
        int dimensionPixelSize2 = resources.getDimensionPixelSize(R.dimen.sem_text_drag_thumbnail_background_shadow_size);
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        frameLayout.measure(makeMeasureSpec, makeMeasureSpec);
        int measuredWidth = frameLayout.getMeasuredWidth();
        int measuredHeight = frameLayout.getMeasuredHeight();
        int i3 = dimensionPixelSize2 * 2;
        int i4 = measuredWidth - i3;
        if (i4 > round) {
            frameLayout.measure(View.MeasureSpec.makeMeasureSpec(round + i3, 1073741824), View.MeasureSpec.makeMeasureSpec(0, 0));
            measuredWidth = frameLayout.getMeasuredWidth();
            measuredHeight = frameLayout.getMeasuredHeight();
        } else if (i4 < dimensionPixelSize) {
            textView.setTextSize(0, (textView.getTextSize() * dimensionPixelSize) / i4);
            textView.setGravity(17);
            frameLayout.measure(View.MeasureSpec.makeMeasureSpec(dimensionPixelSize + i3, 1073741824), View.MeasureSpec.makeMeasureSpec(0, 0));
            measuredWidth = frameLayout.getMeasuredWidth();
            measuredHeight = frameLayout.getMeasuredHeight();
        }
        frameLayout.layout(0, 0, measuredWidth, measuredHeight);
        frameLayout.invalidate();
        return new View.DragShadowBuilder(frameLayout);
    }

    private static class DragLocalState {
        public int end;
        public TextView sourceTextView;
        public int start;

        public DragLocalState(TextView textView, int i, int i2) {
            this.sourceTextView = textView;
            this.start = i;
            this.end = i2;
        }
    }

    void onDrop(DragEvent dragEvent) {
        int offsetForPosition = this.mTextView.getOffsetForPosition(dragEvent.getX(), dragEvent.getY());
        Object localState = dragEvent.getLocalState();
        DragLocalState dragLocalState = localState instanceof DragLocalState ? (DragLocalState) localState : null;
        boolean z = dragLocalState != null && dragLocalState.sourceTextView == this.mTextView;
        if (!z || offsetForPosition < dragLocalState.start || offsetForPosition >= dragLocalState.end) {
            DragAndDropPermissions obtain = DragAndDropPermissions.obtain(dragEvent);
            if (obtain != null) {
                obtain.takeTransient();
            }
            this.mTextView.beginBatchEdit();
            this.mUndoInputFilter.freezeLastEdit();
            try {
                int length = this.mTextView.getText().length();
                Selection.setSelection((Spannable) this.mTextView.getText(), offsetForPosition);
                this.mTextView.performReceiveContent(new ContentInfo.Builder(dragEvent.getClipData(), 3).setDragAndDropPermissions(obtain).build());
                if (z) {
                    deleteSourceAfterLocalDrop(dragLocalState, offsetForPosition, length);
                }
                this.mTextView.endBatchEdit();
                this.mUndoInputFilter.freezeLastEdit();
                sendStopDragBroadcast();
            } catch (Throwable th) {
                this.mTextView.endBatchEdit();
                this.mUndoInputFilter.freezeLastEdit();
                throw th;
            }
        }
    }

    private void deleteSourceAfterLocalDrop(DragLocalState dragLocalState, int i, int i2) {
        int i3 = dragLocalState.start;
        int i4 = dragLocalState.end;
        if (i <= i3) {
            int length = this.mTextView.getText().length() - i2;
            i3 += length;
            i4 += length;
        }
        this.mTextView.deleteText_internal(i3, i4);
        int max = Math.max(0, i3 - 1);
        int min = Math.min(this.mTextView.getText().length(), i3 + 1);
        int i5 = max + 1;
        if (min > i5) {
            CharSequence transformedText = this.mTextView.getTransformedText(max, min);
            if (Character.isSpaceChar(transformedText.charAt(0)) && Character.isSpaceChar(transformedText.charAt(1))) {
                this.mTextView.deleteText_internal(max, i5);
            }
        }
    }

    public void addSpanWatchers(Spannable spannable) {
        int length = spannable.length();
        KeyListener keyListener = this.mKeyListener;
        if (keyListener != null) {
            spannable.setSpan(keyListener, 0, length, 18);
        }
        if (this.mSpanController == null) {
            this.mSpanController = new SpanController();
        }
        spannable.setSpan(this.mSpanController, 0, length, 18);
    }

    void setContextMenuAnchor(float f, float f2) {
        this.mContextMenuAnchorX = f;
        this.mContextMenuAnchorY = f2;
    }

    private void setAssistContextMenuItems(Menu menu) {
        if (getSelectionActionModeHelper().getTextClassification() == null) {
            return;
        }
        final AssistantCallbackHelper assistantCallbackHelper = new AssistantCallbackHelper(getSelectionActionModeHelper());
        assistantCallbackHelper.updateAssistMenuItems(menu, new MenuItem.OnMenuItemClickListener() { // from class: android.widget.Editor$$ExternalSyntheticLambda3
            @Override // android.view.MenuItem.OnMenuItemClickListener
            public final boolean onMenuItemClick(MenuItem menuItem) {
                boolean lambda$setAssistContextMenuItems$1;
                lambda$setAssistContextMenuItems$1 = Editor.this.lambda$setAssistContextMenuItems$1(assistantCallbackHelper, menuItem);
                return lambda$setAssistContextMenuItems$1;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$setAssistContextMenuItems$1(AssistantCallbackHelper assistantCallbackHelper, MenuItem menuItem) {
        getSelectionActionModeHelper().onSelectionAction(menuItem.getItemId(), menuItem.getTitle().toString());
        if (this.mProcessTextIntentActionsHandler.performMenuItemAction(menuItem)) {
            return true;
        }
        if (menuItem.getGroupId() == 16908353 && assistantCallbackHelper.onAssistMenuItemClicked(menuItem)) {
            return true;
        }
        return this.mTextView.onTextContextMenuItem(menuItem.getItemId());
    }

    public void onCreateContextMenu(ContextMenu contextMenu) {
        int offsetForPosition;
        if (this.mIsBeingLongClicked || Float.isNaN(this.mContextMenuAnchorX) || Float.isNaN(this.mContextMenuAnchorY) || (offsetForPosition = this.mTextView.getOffsetForPosition(this.mContextMenuAnchorX, this.mContextMenuAnchorY)) == -1) {
            return;
        }
        stopTextActionModeWithPreservingSelection();
        if (this.mTextView.canSelectText() && (!this.mTextView.hasSelection() || offsetForPosition < this.mTextView.getSelectionStart() || offsetForPosition > this.mTextView.getSelectionEnd())) {
            Selection.setSelection((Spannable) this.mTextView.getText(), offsetForPosition);
            lambda$startActionModeInternal$0();
        }
        if (shouldOfferToShowSuggestions()) {
            SuggestionInfo[] suggestionInfoArr = new SuggestionInfo[5];
            int i = 0;
            while (true) {
                if (i >= 5) {
                    break;
                }
                suggestionInfoArr[i] = new SuggestionInfo();
                i++;
            }
            SubMenu addSubMenu = contextMenu.addSubMenu(0, 0, 11, R.string.replace);
            int suggestionInfo = this.mSuggestionHelper.getSuggestionInfo(suggestionInfoArr, null);
            for (int i2 = 0; i2 < suggestionInfo; i2++) {
                final SuggestionInfo suggestionInfo2 = suggestionInfoArr[i2];
                addSubMenu.add(0, 0, i2, suggestionInfo2.mText).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() { // from class: android.widget.Editor.4
                    @Override // android.view.MenuItem.OnMenuItemClickListener
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        Editor.this.replaceWithSuggestion(suggestionInfo2);
                        return true;
                    }
                });
            }
        }
        if (!this.mIsThemeDeviceDefault) {
            contextMenu.setOptionalIconsVisible(true);
            contextMenu.setGroupDividerEnabled(true);
        }
        setAssistContextMenuItems(contextMenu);
        contextMenu.setQwertyMode(this.mTextView.getResources().getConfiguration().keyboard == 2);
        setTextContextMenuItems(contextMenu);
        this.mPreserveSelection = true;
        adjustIconSpacing(contextMenu);
    }

    public void setTextContextMenuItems(ContextMenu contextMenu) {
        TypedArray obtainStyledAttributes = this.mTextView.getContext().obtainStyledAttributes(new int[]{R.attr.actionModeUndoDrawable, R.attr.actionModeRedoDrawable, 16843537, 16843538, 16843539, 16843646, 16843897});
        boolean z = false;
        if (Flags.contextMenuHideUnavailableItems()) {
            if (this.mTextView.canUndo()) {
                contextMenu.add(1, 16908338, 10, R.string.undo).setAlphabeticShortcut(DateFormat.TIME_ZONE).setOnMenuItemClickListener(this.mOnContextMenuItemClickListener).setIcon(obtainStyledAttributes.getDrawable(0));
            }
            if (this.mTextView.canRedo()) {
                contextMenu.add(1, 16908339, 11, R.string.redo).setAlphabeticShortcut(DateFormat.TIME_ZONE, 4097).setOnMenuItemClickListener(this.mOnContextMenuItemClickListener).setIcon(obtainStyledAttributes.getDrawable(1));
            }
            if (this.mTextView.canCut()) {
                contextMenu.add(2, 16908320, 2, 17039363).setAlphabeticShortcut(EpicenterTranslateClipReveal.StateProperty.TARGET_X).setOnMenuItemClickListener(this.mOnContextMenuItemClickListener).setIcon(obtainStyledAttributes.getDrawable(2));
            }
            if (this.mTextView.canCopy()) {
                contextMenu.add(2, 16908321, 3, 17039361).setAlphabeticShortcut('c').setOnMenuItemClickListener(this.mOnContextMenuItemClickListener).setIcon(obtainStyledAttributes.getDrawable(3));
            }
            if (this.mTextView.canPaste()) {
                contextMenu.add(2, 16908322, 4, 17039371).setAlphabeticShortcut('v').setIcon(obtainStyledAttributes.getDrawable(4)).setOnMenuItemClickListener(this.mOnContextMenuItemClickListener);
            }
            if (this.mTextView.canPasteAsPlainText()) {
                contextMenu.add(2, 16908337, 6, 17039385).setAlphabeticShortcut('v', 4097).setIcon(obtainStyledAttributes.getDrawable(4)).setOnMenuItemClickListener(this.mOnContextMenuItemClickListener);
            }
            if (this.mTextView.canSelectAllText()) {
                contextMenu.add(2, 16908319, 7, 17039373).setAlphabeticShortcut(DateFormat.AM_PM).setIcon(obtainStyledAttributes.getDrawable(5)).setOnMenuItemClickListener(this.mOnContextMenuItemClickListener);
            }
            if (this.mTextView.canShare()) {
                contextMenu.add(3, 16908341, 9, R.string.share).setIcon(obtainStyledAttributes.getDrawable(6)).setOnMenuItemClickListener(this.mOnContextMenuItemClickListener);
            }
            String selectedText = this.mTextView.getSelectedText();
            if (this.mTextView.canRequestAutofill() && (selectedText == null || selectedText.isEmpty())) {
                contextMenu.add(3, 16908355, 8, 17039386).setOnMenuItemClickListener(this.mOnContextMenuItemClickListener);
            }
        } else {
            contextMenu.add(1, 16908338, 10, R.string.undo).setAlphabeticShortcut(DateFormat.TIME_ZONE).setOnMenuItemClickListener(this.mOnContextMenuItemClickListener).setIcon(obtainStyledAttributes.getDrawable(0)).setEnabled(this.mTextView.canUndo());
            contextMenu.add(1, 16908339, 11, R.string.redo).setAlphabeticShortcut(DateFormat.TIME_ZONE, 4097).setOnMenuItemClickListener(this.mOnContextMenuItemClickListener).setIcon(obtainStyledAttributes.getDrawable(1)).setEnabled(this.mTextView.canRedo());
            contextMenu.add(2, 16908320, 2, 17039363).setAlphabeticShortcut(EpicenterTranslateClipReveal.StateProperty.TARGET_X).setOnMenuItemClickListener(this.mOnContextMenuItemClickListener).setIcon(obtainStyledAttributes.getDrawable(2)).setEnabled(this.mTextView.canCut());
            contextMenu.add(2, 16908321, 3, 17039361).setAlphabeticShortcut('c').setOnMenuItemClickListener(this.mOnContextMenuItemClickListener).setIcon(obtainStyledAttributes.getDrawable(3)).setEnabled(this.mTextView.canCopy());
            contextMenu.add(2, 16908322, 4, 17039371).setAlphabeticShortcut('v').setEnabled(this.mTextView.canPaste()).setIcon(obtainStyledAttributes.getDrawable(4)).setOnMenuItemClickListener(this.mOnContextMenuItemClickListener);
            contextMenu.add(2, 16908337, 6, 17039385).setAlphabeticShortcut('v', 4097).setEnabled(this.mTextView.canPasteAsPlainText()).setIcon(obtainStyledAttributes.getDrawable(4)).setOnMenuItemClickListener(this.mOnContextMenuItemClickListener);
            contextMenu.add(2, 16908319, 7, 17039373).setAlphabeticShortcut(DateFormat.AM_PM).setEnabled(this.mTextView.canSelectAllText()).setIcon(obtainStyledAttributes.getDrawable(5)).setOnMenuItemClickListener(this.mOnContextMenuItemClickListener);
            contextMenu.add(3, 16908341, 9, R.string.share).setEnabled(this.mTextView.canShare()).setIcon(obtainStyledAttributes.getDrawable(6)).setOnMenuItemClickListener(this.mOnContextMenuItemClickListener);
            String selectedText2 = this.mTextView.getSelectedText();
            MenuItem add = contextMenu.add(3, 16908355, 8, 17039386);
            if (this.mTextView.canRequestAutofill() && (selectedText2 == null || selectedText2.isEmpty())) {
                z = true;
            }
            add.setEnabled(z).setOnMenuItemClickListener(this.mOnContextMenuItemClickListener);
        }
        if (this.SEP_VERSION.floatValue() >= 15.1d && !this.mTextView.hasPasswordTransformationMethod() && ViewRune.WIDGET_SSS_TRANSLATE_SUPPORTED && this.mTextView.getContext().canStartActivityForResult()) {
            contextMenu.add(2, R.id.sssTranslate, 5, R.string.sss_translate).setEnabled(this.mTextView.hasSelection()).setOnMenuItemClickListener(this.mOnContextMenuItemClickListener);
        }
        obtainStyledAttributes.recycle();
    }

    public void adjustIconSpacing(ContextMenu contextMenu) {
        int i = -1;
        int i2 = -1;
        for (int i3 = 0; i3 < contextMenu.size(); i3++) {
            Drawable icon = contextMenu.getItem(i3).getIcon();
            if (icon != null) {
                i = Math.max(i, icon.getIntrinsicWidth());
                i2 = Math.max(i2, icon.getIntrinsicHeight());
            }
        }
        if (i < 0 || i2 < 0) {
            return;
        }
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setSize(i, i2);
        for (int i4 = 0; i4 < contextMenu.size(); i4++) {
            MenuItem item = contextMenu.getItem(i4);
            if (item.getIcon() == null) {
                item.setIcon(gradientDrawable);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public SuggestionSpan findEquivalentSuggestionSpan(SuggestionSpanInfo suggestionSpanInfo) {
        Editable editable = (Editable) this.mTextView.getText();
        if (editable.getSpanStart(suggestionSpanInfo.mSuggestionSpan) >= 0) {
            return suggestionSpanInfo.mSuggestionSpan;
        }
        for (SuggestionSpan suggestionSpan : (SuggestionSpan[]) editable.getSpans(suggestionSpanInfo.mSpanStart, suggestionSpanInfo.mSpanEnd, SuggestionSpan.class)) {
            if (editable.getSpanStart(suggestionSpan) == suggestionSpanInfo.mSpanStart && editable.getSpanEnd(suggestionSpan) == suggestionSpanInfo.mSpanEnd && suggestionSpan.equals(suggestionSpanInfo.mSuggestionSpan)) {
                return suggestionSpan;
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void replaceWithSuggestion(SuggestionInfo suggestionInfo) {
        int i;
        SuggestionSpan findEquivalentSuggestionSpan = findEquivalentSuggestionSpan(suggestionInfo.mSuggestionSpanInfo);
        if (findEquivalentSuggestionSpan == null) {
            return;
        }
        Editable editable = (Editable) this.mTextView.getText();
        int spanStart = editable.getSpanStart(findEquivalentSuggestionSpan);
        int spanEnd = editable.getSpanEnd(findEquivalentSuggestionSpan);
        if (spanStart < 0 || spanEnd <= spanStart) {
            return;
        }
        String substring = TextUtils.substring(editable, spanStart, spanEnd);
        SuggestionSpan[] suggestionSpanArr = (SuggestionSpan[]) editable.getSpans(spanStart, spanEnd, SuggestionSpan.class);
        int length = suggestionSpanArr.length;
        int[] iArr = new int[length];
        int[] iArr2 = new int[length];
        int[] iArr3 = new int[length];
        for (int i2 = 0; i2 < length; i2++) {
            SuggestionSpan suggestionSpan = suggestionSpanArr[i2];
            iArr[i2] = editable.getSpanStart(suggestionSpan);
            iArr2[i2] = editable.getSpanEnd(suggestionSpan);
            iArr3[i2] = editable.getSpanFlags(suggestionSpan);
            int flags = suggestionSpan.getFlags();
            if ((flags & 10) != 0) {
                suggestionSpan.setFlags(flags & (-12));
            }
        }
        String charSequence = suggestionInfo.mText.subSequence(suggestionInfo.mSuggestionStart, suggestionInfo.mSuggestionEnd).toString();
        this.mTextView.replaceText_internal(spanStart, spanEnd, charSequence);
        findEquivalentSuggestionSpan.getSuggestions()[suggestionInfo.mSuggestionIndex] = substring;
        int length2 = charSequence.length() - (spanEnd - spanStart);
        for (int i3 = 0; i3 < length; i3++) {
            if (iArr[i3] <= spanStart && (i = iArr2[i3]) >= spanEnd && i + length2 <= this.mTextView.length()) {
                this.mTextView.setSpan_internal(suggestionSpanArr[i3], iArr[i3], iArr2[i3] + length2, iArr3[i3]);
            }
        }
        int i4 = spanEnd + length2;
        if (i4 > this.mTextView.length()) {
            i4 = this.mTextView.length();
        }
        this.mTextView.setCursorPosition_internal(i4, i4);
    }

    private class SpanController implements SpanWatcher {
        private static final int DISPLAY_TIMEOUT_MS = 3000;
        private Runnable mHidePopup;
        private EasyEditPopupWindow mPopupWindow;

        private SpanController() {
        }

        private boolean isNonIntermediateSelectionSpan(Spannable spannable, Object obj) {
            return (Selection.SELECTION_START == obj || Selection.SELECTION_END == obj) && (spannable.getSpanFlags(obj) & 512) == 0;
        }

        @Override // android.text.SpanWatcher
        public void onSpanAdded(Spannable spannable, Object obj, int i, int i2) {
            if (isNonIntermediateSelectionSpan(spannable, obj)) {
                Editor.this.sendUpdateSelection();
                return;
            }
            if (obj instanceof EasyEditSpan) {
                if (this.mPopupWindow == null) {
                    this.mPopupWindow = new EasyEditPopupWindow();
                    this.mHidePopup = new Runnable() { // from class: android.widget.Editor.SpanController.1
                        @Override // java.lang.Runnable
                        public void run() {
                            SpanController.this.hide();
                        }
                    };
                }
                if (this.mPopupWindow.mEasyEditSpan != null) {
                    this.mPopupWindow.mEasyEditSpan.setDeleteEnabled(false);
                }
                this.mPopupWindow.setEasyEditSpan((EasyEditSpan) obj);
                this.mPopupWindow.setOnDeleteListener(new EasyEditDeleteListener() { // from class: android.widget.Editor.SpanController.2
                    @Override // android.widget.Editor.EasyEditDeleteListener
                    public void onDeleteClick(EasyEditSpan easyEditSpan) {
                        Editable editable = (Editable) Editor.this.mTextView.getText();
                        int spanStart = editable.getSpanStart(easyEditSpan);
                        int spanEnd = editable.getSpanEnd(easyEditSpan);
                        if (spanStart >= 0 && spanEnd >= 0) {
                            SpanController.this.sendEasySpanNotification(1, easyEditSpan);
                            Editor.this.mTextView.deleteText_internal(spanStart, spanEnd);
                        }
                        editable.removeSpan(easyEditSpan);
                    }
                });
                if (Editor.this.mTextView.getWindowVisibility() != 0 || Editor.this.mTextView.getLayout() == null || Editor.this.extractedTextModeWillBeStarted()) {
                    return;
                }
                this.mPopupWindow.show();
                Editor.this.mTextView.removeCallbacks(this.mHidePopup);
                Editor.this.mTextView.postDelayed(this.mHidePopup, 3000L);
            }
        }

        @Override // android.text.SpanWatcher
        public void onSpanRemoved(Spannable spannable, Object obj, int i, int i2) {
            if (isNonIntermediateSelectionSpan(spannable, obj)) {
                Editor.this.sendUpdateSelection();
                return;
            }
            EasyEditPopupWindow easyEditPopupWindow = this.mPopupWindow;
            if (easyEditPopupWindow == null || obj != easyEditPopupWindow.mEasyEditSpan) {
                return;
            }
            hide();
        }

        @Override // android.text.SpanWatcher
        public void onSpanChanged(Spannable spannable, Object obj, int i, int i2, int i3, int i4) {
            if (isNonIntermediateSelectionSpan(spannable, obj)) {
                Editor.this.sendUpdateSelection();
            } else {
                if (this.mPopupWindow == null || !(obj instanceof EasyEditSpan)) {
                    return;
                }
                EasyEditSpan easyEditSpan = (EasyEditSpan) obj;
                sendEasySpanNotification(2, easyEditSpan);
                spannable.removeSpan(easyEditSpan);
            }
        }

        public void hide() {
            EasyEditPopupWindow easyEditPopupWindow = this.mPopupWindow;
            if (easyEditPopupWindow != null) {
                easyEditPopupWindow.hide();
                Editor.this.mTextView.removeCallbacks(this.mHidePopup);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void sendEasySpanNotification(int i, EasyEditSpan easyEditSpan) {
            try {
                PendingIntent pendingIntent = easyEditSpan.getPendingIntent();
                if (pendingIntent != null) {
                    Intent intent = new Intent();
                    intent.putExtra(EasyEditSpan.EXTRA_TEXT_CHANGED_TYPE, i);
                    pendingIntent.send(Editor.this.mTextView.getContext(), 0, intent);
                }
            } catch (PendingIntent.CanceledException e) {
                Log.w("Editor", "PendingIntent for notification cannot be sent", e);
            }
        }
    }

    private class EasyEditPopupWindow extends PinnedPopupWindow implements View.OnClickListener {
        private static final int POPUP_TEXT_LAYOUT = 17367499;
        private TextView mDeleteTextView;
        private EasyEditSpan mEasyEditSpan;
        private EasyEditDeleteListener mOnDeleteListener;

        @Override // android.widget.Editor.PinnedPopupWindow
        protected int clipVertically(int i) {
            return i;
        }

        private EasyEditPopupWindow() {
            super();
        }

        @Override // android.widget.Editor.PinnedPopupWindow
        protected void createPopupWindow() {
            this.mPopupWindow = new PopupWindow(Editor.this.mTextView.getContext(), (AttributeSet) null, 16843464);
            this.mPopupWindow.setInputMethodMode(2);
            this.mPopupWindow.setClippingEnabled(true);
        }

        @Override // android.widget.Editor.PinnedPopupWindow
        protected void initContentView() {
            LinearLayout linearLayout = new LinearLayout(Editor.this.mTextView.getContext());
            linearLayout.setOrientation(0);
            this.mContentView = linearLayout;
            this.mContentView.setBackgroundResource(R.drawable.text_edit_side_paste_window);
            LayoutInflater layoutInflater = (LayoutInflater) Editor.this.mTextView.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-2, -2);
            TextView textView = (TextView) layoutInflater.inflate(17367499, (ViewGroup) null);
            this.mDeleteTextView = textView;
            textView.setLayoutParams(layoutParams);
            this.mDeleteTextView.setText(R.string.delete);
            this.mDeleteTextView.setOnClickListener(this);
            this.mContentView.addView(this.mDeleteTextView);
        }

        public void setEasyEditSpan(EasyEditSpan easyEditSpan) {
            this.mEasyEditSpan = easyEditSpan;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setOnDeleteListener(EasyEditDeleteListener easyEditDeleteListener) {
            this.mOnDeleteListener = easyEditDeleteListener;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            EasyEditSpan easyEditSpan;
            EasyEditDeleteListener easyEditDeleteListener;
            if (view != this.mDeleteTextView || (easyEditSpan = this.mEasyEditSpan) == null || !easyEditSpan.isDeleteEnabled() || (easyEditDeleteListener = this.mOnDeleteListener) == null) {
                return;
            }
            easyEditDeleteListener.onDeleteClick(this.mEasyEditSpan);
        }

        @Override // android.widget.Editor.PinnedPopupWindow
        public void hide() {
            EasyEditSpan easyEditSpan = this.mEasyEditSpan;
            if (easyEditSpan != null) {
                easyEditSpan.setDeleteEnabled(false);
            }
            this.mOnDeleteListener = null;
            super.hide();
        }

        @Override // android.widget.Editor.PinnedPopupWindow
        protected int getTextOffset() {
            return ((Editable) Editor.this.mTextView.getText()).getSpanEnd(this.mEasyEditSpan);
        }

        @Override // android.widget.Editor.PinnedPopupWindow
        protected int getVerticalLocalPosition(int i) {
            return Editor.this.mTextView.getLayout().getLineBottom(i, false);
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
            this.mUpdatePosition = new Runnable() { // from class: android.widget.Editor.PositionListener.1
                @Override // java.lang.Runnable
                public void run() {
                    for (int i = 0; i < 7; i++) {
                        TextViewPositionListener textViewPositionListener = PositionListener.this.mPositionListeners[i];
                        if (textViewPositionListener != null && (textViewPositionListener instanceof HandleView)) {
                            if ((textViewPositionListener instanceof SelectionHandleView) && Editor.this.mTextActionMode == null) {
                                return;
                            } else {
                                textViewPositionListener.updatePosition(PositionListener.this.mPositionX, PositionListener.this.mPositionY, true, true);
                            }
                        }
                    }
                }
            };
        }

        public void addSubscriber(TextViewPositionListener textViewPositionListener, boolean z) {
            if (this.mNumberOfListeners == 0) {
                updatePosition();
                Editor.this.mTextView.getViewTreeObserver().addOnPreDrawListener(this);
            }
            int i = 0;
            int i2 = -1;
            for (int i3 = 0; i3 < 7; i3++) {
                TextViewPositionListener textViewPositionListener2 = this.mPositionListeners[i3];
                if (textViewPositionListener2 == textViewPositionListener) {
                    return;
                }
                if (i2 < 0 && textViewPositionListener2 == null) {
                    i2 = i3;
                }
            }
            if (i2 == -1) {
                for (int i4 = 0; i4 < 7; i4++) {
                    this.mPositionListeners[i4] = null;
                }
                this.mNumberOfListeners = 0;
            } else {
                i = i2;
            }
            this.mPositionListeners[i] = textViewPositionListener;
            this.mCanMove[i] = z;
            this.mNumberOfListeners++;
        }

        public void removeSubscriber(TextViewPositionListener textViewPositionListener) {
            if (textViewPositionListener == null) {
                return;
            }
            int i = 0;
            while (true) {
                if (i >= 7) {
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
                Editor.this.mTextView.getViewTreeObserver().removeOnPreDrawListener(this);
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
            TextViewPositionListener textViewPositionListener;
            updatePosition();
            for (int i = 0; i < 7; i++) {
                boolean z = this.mPositionHasChanged;
                if ((z || this.mScrollHasChanged || this.mCanMove[i]) && (textViewPositionListener = this.mPositionListeners[i]) != null) {
                    if (z && (textViewPositionListener instanceof HandleView)) {
                        HandleView handleView = (HandleView) textViewPositionListener;
                        if (!handleView.isDragging()) {
                            handleView.dismiss();
                            if ((handleView instanceof InsertionHandleView) && Editor.this.mTextActionMode == null) {
                                ((InsertionHandleView) handleView).hideAfterDelay();
                            }
                            Editor.this.mTextView.removeCallbacks(this.mUpdatePosition);
                            Editor.this.mTextView.postDelayed(this.mUpdatePosition, 300L);
                        }
                    }
                    if (!(textViewPositionListener instanceof SelectionHandleView) || Editor.this.mTextActionMode != null) {
                        textViewPositionListener.updatePosition(this.mPositionX, this.mPositionY, this.mPositionHasChanged, this.mScrollHasChanged);
                    }
                }
            }
            this.mScrollHasChanged = false;
            return true;
        }

        private void updatePosition() {
            Editor.this.mTextView.getLocationInWindow(this.mTempCoords);
            int[] iArr = this.mTempCoords;
            int i = iArr[0];
            this.mPositionHasChanged = (i == this.mPositionX && iArr[1] == this.mPositionY) ? false : true;
            this.mPositionX = i;
            this.mPositionY = iArr[1];
            Editor.this.mTextView.getLocationOnScreen(this.mTempCoords);
            int[] iArr2 = this.mTempCoords;
            this.mPositionXOnScreen = iArr2[0];
            this.mPositionYOnScreen = iArr2[1];
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

        protected void setUp() {
        }

        public PinnedPopupWindow() {
            setUp();
            createPopupWindow();
            this.mPopupWindow.setWindowLayoutType(1005);
            this.mPopupWindow.setWidth(-2);
            this.mPopupWindow.setHeight(-2);
            initContentView();
            this.mContentView.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
            this.mPopupWindow.setContentView(this.mContentView);
        }

        public void show() {
            Editor.this.getPositionListener().addSubscriber(this, false);
            computeLocalPosition();
            PositionListener positionListener = Editor.this.getPositionListener();
            updatePosition(positionListener.getPositionX(), positionListener.getPositionY());
        }

        protected void measureContent() {
            DisplayMetrics displayMetrics = Editor.this.mTextView.getResources().getDisplayMetrics();
            this.mContentView.measure(View.MeasureSpec.makeMeasureSpec(displayMetrics.widthPixels, Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(displayMetrics.heightPixels, Integer.MIN_VALUE));
        }

        private void computeLocalPosition() {
            measureContent();
            int measuredWidth = this.mContentView.getMeasuredWidth();
            int originalToTransformed = Editor.this.mTextView.originalToTransformed(getTextOffset(), 1);
            Layout layout = Editor.this.mTextView.getLayout();
            int primaryHorizontal = (int) (layout.getPrimaryHorizontal(originalToTransformed) - (measuredWidth / 2.0f));
            this.mPositionX = primaryHorizontal;
            this.mPositionX = primaryHorizontal + Editor.this.mTextView.viewportToContentHorizontalOffset();
            int verticalLocalPosition = getVerticalLocalPosition(layout.getLineForOffset(originalToTransformed));
            this.mPositionY = verticalLocalPosition;
            this.mPositionY = verticalLocalPosition + Editor.this.mTextView.viewportToContentVerticalOffset();
        }

        private void updatePosition(int i, int i2) {
            int i3 = i + this.mPositionX;
            int clipVertically = clipVertically(i2 + this.mPositionY);
            DisplayMetrics displayMetrics = Editor.this.mTextView.getResources().getDisplayMetrics();
            int max = Math.max(-this.mClippingLimitLeft, Math.min((displayMetrics.widthPixels - this.mContentView.getMeasuredWidth()) + this.mClippingLimitRight, i3));
            if (isShowing()) {
                this.mPopupWindow.update(max, clipVertically, -1, -1);
            } else {
                this.mPopupWindow.showAtLocation(Editor.this.mTextView, 0, max, clipVertically);
            }
        }

        public void hide() {
            if (isShowing()) {
                this.mPopupWindow.dismiss();
                Editor.this.getPositionListener().removeSubscriber(this);
            }
        }

        @Override // android.widget.Editor.TextViewPositionListener
        public void updatePosition(int i, int i2, boolean z, boolean z2) {
            if (isShowing() && Editor.this.isOffsetVisible(getTextOffset())) {
                if (z2) {
                    computeLocalPosition();
                }
                updatePosition(i, i2);
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

        void setSpanInfo(SuggestionSpan suggestionSpan, int i, int i2) {
            this.mSuggestionSpanInfo.mSuggestionSpan = suggestionSpan;
            this.mSuggestionSpanInfo.mSpanStart = i;
            this.mSuggestionSpanInfo.mSpanEnd = i2;
        }
    }

    private static final class SuggestionSpanInfo {
        int mSpanEnd;
        int mSpanStart;
        SuggestionSpan mSuggestionSpan;

        private SuggestionSpanInfo() {
        }

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
            private int compareFlag(int i, int i2, int i3) {
                boolean z = (i2 & i) != 0;
                if (z == ((i & i3) != 0)) {
                    return 0;
                }
                return z ? -1 : 1;
            }

            private SuggestionSpanComparator() {
            }

            @Override // java.util.Comparator
            public int compare(SuggestionSpan suggestionSpan, SuggestionSpan suggestionSpan2) {
                int flags = suggestionSpan.getFlags();
                int flags2 = suggestionSpan2.getFlags();
                if (flags != flags2) {
                    int compareFlag = compareFlag(1, flags, flags2);
                    if (compareFlag != 0) {
                        return compareFlag;
                    }
                    int compareFlag2 = compareFlag(2, flags, flags2);
                    if (compareFlag2 != 0) {
                        return compareFlag2;
                    }
                    int compareFlag3 = compareFlag(8, flags, flags2);
                    if (compareFlag3 != 0) {
                        return compareFlag3;
                    }
                }
                return ((Integer) SuggestionHelper.this.mSpansLengths.get(suggestionSpan)).intValue() - ((Integer) SuggestionHelper.this.mSpansLengths.get(suggestionSpan2)).intValue();
            }
        }

        private SuggestionSpan[] getSortedSuggestionSpans() {
            int selectionStart = Editor.this.mTextView.getSelectionStart();
            Spannable spannable = (Spannable) Editor.this.mTextView.getText();
            SuggestionSpan[] suggestionSpanArr = (SuggestionSpan[]) spannable.getSpans(selectionStart, selectionStart, SuggestionSpan.class);
            this.mSpansLengths.clear();
            for (SuggestionSpan suggestionSpan : suggestionSpanArr) {
                this.mSpansLengths.put(suggestionSpan, Integer.valueOf(spannable.getSpanEnd(suggestionSpan) - spannable.getSpanStart(suggestionSpan)));
            }
            Arrays.sort(suggestionSpanArr, this.mSuggestionSpanComparator);
            this.mSpansLengths.clear();
            return suggestionSpanArr;
        }

        public int getSuggestionInfo(SuggestionInfo[] suggestionInfoArr, SuggestionSpanInfo suggestionSpanInfo) {
            SuggestionSpan[] suggestionSpanArr;
            Spannable spannable = (Spannable) Editor.this.mTextView.getText();
            SuggestionSpan[] sortedSuggestionSpans = getSortedSuggestionSpans();
            int i = 0;
            if (sortedSuggestionSpans.length == 0) {
                return 0;
            }
            int length = sortedSuggestionSpans.length;
            int i2 = 0;
            for (int i3 = 0; i3 < length; i3++) {
                SuggestionSpan suggestionSpan = sortedSuggestionSpans[i3];
                int spanStart = spannable.getSpanStart(suggestionSpan);
                int spanEnd = spannable.getSpanEnd(suggestionSpan);
                if (suggestionSpanInfo != null) {
                    if ((suggestionSpan.getFlags() & 10) != 0) {
                        suggestionSpanInfo.mSuggestionSpan = suggestionSpan;
                        suggestionSpanInfo.mSpanStart = spanStart;
                        suggestionSpanInfo.mSpanEnd = spanEnd;
                    } else {
                        suggestionSpanInfo.clear();
                    }
                }
                String[] suggestions = suggestionSpan.getSuggestions();
                int length2 = suggestions.length;
                int i4 = i;
                while (i4 < length2) {
                    String str = suggestions[i4];
                    int i5 = i;
                    while (true) {
                        if (i5 < i2) {
                            SuggestionInfo suggestionInfo = suggestionInfoArr[i5];
                            suggestionSpanArr = sortedSuggestionSpans;
                            if (suggestionInfo.mText.toString().equals(str)) {
                                int i6 = suggestionInfo.mSuggestionSpanInfo.mSpanStart;
                                int i7 = suggestionInfo.mSuggestionSpanInfo.mSpanEnd;
                                if (spanStart == i6 && spanEnd == i7) {
                                    i = 0;
                                    break;
                                }
                            }
                            i5++;
                            sortedSuggestionSpans = suggestionSpanArr;
                        } else {
                            suggestionSpanArr = sortedSuggestionSpans;
                            SuggestionInfo suggestionInfo2 = suggestionInfoArr[i2];
                            suggestionInfo2.setSpanInfo(suggestionSpan, spanStart, spanEnd);
                            suggestionInfo2.mSuggestionIndex = i4;
                            i = 0;
                            suggestionInfo2.mSuggestionStart = 0;
                            suggestionInfo2.mSuggestionEnd = str.length();
                            suggestionInfo2.mText.replace(0, suggestionInfo2.mText.length(), (CharSequence) str);
                            i2++;
                            if (i2 >= suggestionInfoArr.length) {
                                return i2;
                            }
                        }
                    }
                    i4++;
                    sortedSuggestionSpans = suggestionSpanArr;
                }
            }
            return i2;
        }
    }

    private final class SuggestionsPopupWindow extends PinnedPopupWindow implements AdapterView.OnItemClickListener {
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
            private CustomPopupWindow() {
            }

            @Override // android.widget.PopupWindow
            public void dismiss() {
                if (isShowing()) {
                    super.dismiss();
                    Editor.this.getPositionListener().removeSubscriber(SuggestionsPopupWindow.this);
                    ((Spannable) Editor.this.mTextView.getText()).removeSpan(Editor.this.mSuggestionRangeSpan);
                    Editor.this.mTextView.setCursorVisible(SuggestionsPopupWindow.this.mCursorWasVisibleBeforeSuggestions);
                    if (!Editor.this.hasInsertionController() || Editor.this.extractedTextModeWillBeStarted()) {
                        return;
                    }
                    Editor.this.getInsertionController().show();
                }
            }
        }

        public SuggestionsPopupWindow() {
            super();
            this.mIsShowingUp = false;
            this.mMisspelledSpanInfo = new SuggestionSpanInfo();
            this.mCursorWasVisibleBeforeSuggestions = Editor.this.mTextView.isCursorVisibleFromAttr();
        }

        @Override // android.widget.Editor.PinnedPopupWindow
        protected void setUp() {
            this.mContext = applyDefaultTheme(Editor.this.mTextView.getContext());
            this.mHighlightSpan = new TextAppearanceSpan(this.mContext, Editor.this.mTextView.mTextEditSuggestionHighlightStyle);
        }

        private Context applyDefaultTheme(Context context) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(new int[]{16844176});
            int i = obtainStyledAttributes.getBoolean(0, true) ? 16974410 : 16974411;
            obtainStyledAttributes.recycle();
            if (Editor.this.mIsThemeDeviceDefault) {
                i = (context.getResources().getConfiguration().uiMode & 48) == 32 ? 16974120 : 16974123;
            }
            return new ContextThemeWrapper(context, i);
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
            LayoutInflater layoutInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.mContentView = (ViewGroup) layoutInflater.inflate(Editor.this.mTextView.mTextEditSuggestionContainerLayout, (ViewGroup) null);
            LinearLayout linearLayout = (LinearLayout) this.mContentView.findViewById(R.id.suggestionWindowContainer);
            this.mContainerView = linearLayout;
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) linearLayout.getLayoutParams();
            this.mContainerMarginWidth = marginLayoutParams.leftMargin + marginLayoutParams.rightMargin;
            this.mContainerMarginTop = marginLayoutParams.topMargin;
            this.mClippingLimitLeft = marginLayoutParams.leftMargin;
            this.mClippingLimitRight = marginLayoutParams.rightMargin;
            this.mSuggestionListView = (ListView) this.mContentView.findViewById(R.id.suggestionContainer);
            SuggestionAdapter suggestionAdapter = new SuggestionAdapter();
            this.mSuggestionsAdapter = suggestionAdapter;
            this.mSuggestionListView.setAdapter((ListAdapter) suggestionAdapter);
            this.mSuggestionListView.setOnItemClickListener(this);
            this.mSuggestionInfos = new SuggestionInfo[5];
            int i = 0;
            while (true) {
                SuggestionInfo[] suggestionInfoArr = this.mSuggestionInfos;
                if (i >= suggestionInfoArr.length) {
                    break;
                }
                suggestionInfoArr[i] = new SuggestionInfo();
                i++;
            }
            if (Editor.this.mIsThemeDeviceDefault) {
                LinearLayout linearLayout2 = (LinearLayout) layoutInflater.inflate(R.layout.tw_text_edit_suggestion_button_item, (ViewGroup) null);
                this.mButtonItemView = linearLayout2;
                this.mAddToDictionaryButton = (TextView) linearLayout2.findViewById(R.id.addToDictionaryButton);
                this.mDeleteButton = (TextView) this.mButtonItemView.findViewById(R.id.deleteButton);
            } else {
                TextView textView = (TextView) this.mContentView.findViewById(R.id.addToDictionaryButton);
                this.mAddToDictionaryButton = textView;
                textView.setOnClickListener(new View.OnClickListener() { // from class: android.widget.Editor.SuggestionsPopupWindow.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        SuggestionSpan findEquivalentSuggestionSpan = Editor.this.findEquivalentSuggestionSpan(SuggestionsPopupWindow.this.mMisspelledSpanInfo);
                        if (findEquivalentSuggestionSpan == null) {
                            return;
                        }
                        Editable editable = (Editable) Editor.this.mTextView.getText();
                        int spanStart = editable.getSpanStart(findEquivalentSuggestionSpan);
                        int spanEnd = editable.getSpanEnd(findEquivalentSuggestionSpan);
                        if (spanStart < 0 || spanEnd <= spanStart) {
                            return;
                        }
                        String substring = TextUtils.substring(editable, spanStart, spanEnd);
                        Intent intent = new Intent(Settings.ACTION_USER_DICTIONARY_INSERT);
                        intent.putExtra("word", substring);
                        intent.putExtra("locale", Editor.this.mTextView.getTextServicesLocale().toString());
                        intent.setFlags(intent.getFlags() | 268435456);
                        Editor.this.mTextView.startActivityAsTextOperationUserIfNecessary(intent);
                        editable.removeSpan(SuggestionsPopupWindow.this.mMisspelledSpanInfo.mSuggestionSpan);
                        Selection.setSelection(editable, spanEnd);
                        Editor.this.updateSpellCheckSpans(spanStart, spanEnd, false);
                        SuggestionsPopupWindow.this.hideWithCleanUp();
                    }
                });
                TextView textView2 = (TextView) this.mContentView.findViewById(R.id.deleteButton);
                this.mDeleteButton = textView2;
                textView2.setOnClickListener(new View.OnClickListener() { // from class: android.widget.Editor.SuggestionsPopupWindow.2
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        Editable editable = (Editable) Editor.this.mTextView.getText();
                        int spanStart = editable.getSpanStart(Editor.this.mSuggestionRangeSpan);
                        int spanEnd = editable.getSpanEnd(Editor.this.mSuggestionRangeSpan);
                        if (spanStart >= 0 && spanEnd > spanStart) {
                            if (spanEnd < editable.length() && Character.isSpaceChar(editable.charAt(spanEnd)) && (spanStart == 0 || Character.isSpaceChar(editable.charAt(spanStart - 1)))) {
                                spanEnd++;
                            }
                            Editor.this.mTextView.deleteText_internal(spanStart, spanEnd);
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

            @Override // android.widget.Adapter
            public long getItemId(int i) {
                return i;
            }

            private SuggestionAdapter() {
                this.mInflater = (LayoutInflater) SuggestionsPopupWindow.this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }

            @Override // android.widget.Adapter
            public int getCount() {
                if (Editor.this.mIsThemeDeviceDefault && SuggestionsPopupWindow.this.mNumberOfSuggestions != 0) {
                    return SuggestionsPopupWindow.this.mNumberOfSuggestions + SuggestionsPopupWindow.this.mNumberOfButtons;
                }
                return SuggestionsPopupWindow.this.mNumberOfSuggestions;
            }

            @Override // android.widget.Adapter
            public Object getItem(int i) {
                return SuggestionsPopupWindow.this.mSuggestionInfos[i];
            }

            @Override // android.widget.Adapter
            public View getView(int i, View view, ViewGroup viewGroup) {
                TextView textView;
                if (Editor.this.mIsThemeDeviceDefault) {
                    if (SuggestionsPopupWindow.this.mNumberOfButtons == 1 && i == SuggestionsPopupWindow.this.mNumberOfSuggestions) {
                        return SuggestionsPopupWindow.this.mDeleteButton;
                    }
                    if (SuggestionsPopupWindow.this.mNumberOfButtons == 2 && i == SuggestionsPopupWindow.this.mNumberOfSuggestions) {
                        return SuggestionsPopupWindow.this.mAddToDictionaryButton;
                    }
                    if (SuggestionsPopupWindow.this.mNumberOfButtons == 2 && i > SuggestionsPopupWindow.this.mNumberOfSuggestions) {
                        return SuggestionsPopupWindow.this.mDeleteButton;
                    }
                    textView = (TextView) this.mInflater.inflate(Editor.this.mTextView.mTextEditSuggestionItemLayout, viewGroup, false);
                } else {
                    textView = (TextView) view;
                    if (textView == null) {
                        textView = (TextView) this.mInflater.inflate(Editor.this.mTextView.mTextEditSuggestionItemLayout, viewGroup, false);
                    }
                }
                textView.lambda$setTextAsync$0(SuggestionsPopupWindow.this.mSuggestionInfos[i].mText);
                return textView;
            }
        }

        @Override // android.widget.Editor.PinnedPopupWindow
        public void show() {
            if ((Editor.this.mTextView.getText() instanceof Editable) && !Editor.this.extractedTextModeWillBeStarted()) {
                if (updateSuggestions()) {
                    this.mCursorWasVisibleBeforeSuggestions = Editor.this.mTextView.isCursorVisibleFromAttr();
                    Editor.this.mTextView.setCursorVisible(false);
                    this.mIsShowingUp = true;
                    this.mSuggestionListView.requestLayout();
                    super.show();
                }
                this.mSuggestionListView.setVisibility(this.mNumberOfSuggestions == 0 ? 8 : 0);
            }
        }

        @Override // android.widget.Editor.PinnedPopupWindow
        protected void measureContent() {
            DisplayMetrics displayMetrics = Editor.this.mTextView.getResources().getDisplayMetrics();
            int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(displayMetrics.widthPixels, Integer.MIN_VALUE);
            int makeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(displayMetrics.heightPixels, Integer.MIN_VALUE);
            View view = null;
            int i = 0;
            for (int i2 = 0; i2 < this.mNumberOfSuggestions; i2++) {
                view = this.mSuggestionsAdapter.getView(i2, view, this.mContentView);
                view.getLayoutParams().width = -2;
                view.measure(makeMeasureSpec, makeMeasureSpec2);
                i = Math.max(i, view.getMeasuredWidth());
            }
            if (this.mAddToDictionaryButton.getVisibility() != 8) {
                this.mAddToDictionaryButton.measure(makeMeasureSpec, makeMeasureSpec2);
                i = Math.max(i, this.mAddToDictionaryButton.getMeasuredWidth());
            }
            this.mDeleteButton.measure(makeMeasureSpec, makeMeasureSpec2);
            int max = Math.max(i, this.mDeleteButton.getMeasuredWidth()) + this.mContainerView.getPaddingLeft() + this.mContainerView.getPaddingRight() + this.mContainerMarginWidth;
            this.mContentView.measure(View.MeasureSpec.makeMeasureSpec(max, 1073741824), makeMeasureSpec2);
            Drawable background = this.mPopupWindow.getBackground();
            if (background != null) {
                if (Editor.this.mTempRect == null) {
                    Editor.this.mTempRect = new Rect();
                }
                background.getPadding(Editor.this.mTempRect);
                max += Editor.this.mTempRect.left + Editor.this.mTempRect.right;
            }
            this.mPopupWindow.setWidth(max);
        }

        @Override // android.widget.Editor.PinnedPopupWindow
        protected int getTextOffset() {
            return (Editor.this.mTextView.getSelectionStart() + Editor.this.mTextView.getSelectionStart()) / 2;
        }

        @Override // android.widget.Editor.PinnedPopupWindow
        protected int getVerticalLocalPosition(int i) {
            return Editor.this.mTextView.getLayout().getLineBottom(i, false) - this.mContainerMarginTop;
        }

        @Override // android.widget.Editor.PinnedPopupWindow
        protected int clipVertically(int i) {
            return Math.min(i, Editor.this.mTextView.getResources().getDisplayMetrics().heightPixels - this.mContentView.getMeasuredHeight());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void hideWithCleanUp() {
            for (SuggestionInfo suggestionInfo : this.mSuggestionInfos) {
                suggestionInfo.clear();
            }
            this.mMisspelledSpanInfo.clear();
            hide();
        }

        private boolean updateSuggestions() {
            int underlineColor;
            Spannable spannable = (Spannable) Editor.this.mTextView.getText();
            int suggestionInfo = Editor.this.mSuggestionHelper.getSuggestionInfo(this.mSuggestionInfos, this.mMisspelledSpanInfo);
            this.mNumberOfSuggestions = suggestionInfo;
            if (suggestionInfo == 0 && this.mMisspelledSpanInfo.mSuggestionSpan == null) {
                return false;
            }
            int length = Editor.this.mTextView.getText().length();
            int i = 0;
            for (int i2 = 0; i2 < this.mNumberOfSuggestions; i2++) {
                SuggestionSpanInfo suggestionSpanInfo = this.mSuggestionInfos[i2].mSuggestionSpanInfo;
                length = Math.min(length, suggestionSpanInfo.mSpanStart);
                i = Math.max(i, suggestionSpanInfo.mSpanEnd);
            }
            if (this.mMisspelledSpanInfo.mSuggestionSpan != null) {
                length = Math.min(length, this.mMisspelledSpanInfo.mSpanStart);
                i = Math.max(i, this.mMisspelledSpanInfo.mSpanEnd);
            }
            for (int i3 = 0; i3 < this.mNumberOfSuggestions; i3++) {
                try {
                    highlightTextDifferences(this.mSuggestionInfos[i3], length, i);
                } catch (IndexOutOfBoundsException unused) {
                    SuggestionSpanInfo suggestionSpanInfo2 = this.mSuggestionInfos[i3].mSuggestionSpanInfo;
                    Log.e("Editor", "mNumberOfSuggestions = " + this.mNumberOfSuggestions + ", i = " + i3);
                    Log.e("Editor", "spanInfo.mSpanStart : " + suggestionSpanInfo2.mSpanStart + ", spanInfo.mSpanEnd : " + suggestionSpanInfo2.mSpanEnd);
                    Log.e("Editor", "spanUnionStart : " + length + ", spanUnionEnd : " + i);
                    StringBuilder sb = new StringBuilder("mTextView.getText() = ");
                    sb.append((Object) Editor.this.mTextView.getText());
                    Log.e("Editor", sb.toString());
                    if (this.mMisspelledSpanInfo.mSuggestionSpan != null) {
                        Log.e("Editor", "mMisspelledSpanInfo.mSpanStart : " + this.mMisspelledSpanInfo.mSpanStart + ", mMisspelledSpanInfo.mSpanEnd : " + this.mMisspelledSpanInfo.mSpanEnd);
                    }
                    return false;
                }
            }
            InputMethodManager inputMethodManager = Editor.this.getInputMethodManager();
            int i4 = (this.mMisspelledSpanInfo.mSuggestionSpan == null || inputMethodManager == null || inputMethodManager.isCurrentInputMethodAsSamsungKeyboard() || this.mMisspelledSpanInfo.mSpanStart < 0 || this.mMisspelledSpanInfo.mSpanEnd <= this.mMisspelledSpanInfo.mSpanStart) ? 8 : 0;
            this.mAddToDictionaryButton.setVisibility(i4);
            if (i4 == 0) {
                this.mNumberOfButtons = 2;
            } else {
                this.mNumberOfButtons = 1;
            }
            if (Editor.this.mSuggestionRangeSpan == null) {
                Editor.this.mSuggestionRangeSpan = new SuggestionRangeSpan();
            }
            if (this.mNumberOfSuggestions != 0) {
                underlineColor = this.mSuggestionInfos[0].mSuggestionSpanInfo.mSuggestionSpan.getUnderlineColor();
            } else {
                underlineColor = this.mMisspelledSpanInfo.mSuggestionSpan.getUnderlineColor();
            }
            if (underlineColor == 0) {
                Editor.this.mSuggestionRangeSpan.setBackgroundColor(Editor.this.mTextView.mHighlightColor);
            } else {
                Editor.this.mSuggestionRangeSpan.setBackgroundColor((underlineColor & 16777215) + (((int) (Color.alpha(underlineColor) * 0.4f)) << 24));
            }
            boolean isVisibleToAccessibility = Editor.this.mTextView.isVisibleToAccessibility();
            SpannedString spannedString = isVisibleToAccessibility ? new SpannedString(spannable, true) : null;
            spannable.setSpan(Editor.this.mSuggestionRangeSpan, length, i, 33);
            if (isVisibleToAccessibility) {
                Editor.this.mTextView.sendAccessibilityEventTypeViewTextChanged(spannedString, length, i);
            }
            this.mSuggestionsAdapter.notifyDataSetChanged();
            return true;
        }

        private void highlightTextDifferences(SuggestionInfo suggestionInfo, int i, int i2) {
            Spannable spannable = (Spannable) Editor.this.mTextView.getText();
            int i3 = suggestionInfo.mSuggestionSpanInfo.mSpanStart;
            int i4 = suggestionInfo.mSuggestionSpanInfo.mSpanEnd;
            suggestionInfo.mSuggestionStart = i3 - i;
            suggestionInfo.mSuggestionEnd = suggestionInfo.mSuggestionStart + suggestionInfo.mText.length();
            suggestionInfo.mText.setSpan(this.mHighlightSpan, 0, suggestionInfo.mText.length(), 33);
            String spannable2 = spannable.toString();
            suggestionInfo.mText.insert(0, (CharSequence) spannable2.substring(i, i3));
            suggestionInfo.mText.append((CharSequence) spannable2.substring(i4, i2));
        }

        @Override // android.widget.AdapterView.OnItemClickListener
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
            if (Editor.this.mIsThemeDeviceDefault) {
                int i2 = this.mNumberOfButtons;
                if (i2 == 1 && i == this.mNumberOfSuggestions) {
                    clickButtons(this.mDeleteButton);
                    return;
                }
                if (i2 == 2 && i == this.mNumberOfSuggestions) {
                    clickButtons(this.mAddToDictionaryButton);
                    return;
                }
                if (i2 == 2 && i > this.mNumberOfSuggestions) {
                    clickButtons(this.mDeleteButton);
                    return;
                }
                Editor.this.replaceWithSuggestion(this.mSuggestionInfos[i]);
                hideWithCleanUp();
                return;
            }
            Editor.this.replaceWithSuggestion(this.mSuggestionInfos[i]);
            hideWithCleanUp();
        }

        private void clickButtons(View view) {
            if (view == this.mAddToDictionaryButton) {
                SuggestionSpan findEquivalentSuggestionSpan = Editor.this.findEquivalentSuggestionSpan(this.mMisspelledSpanInfo);
                if (findEquivalentSuggestionSpan == null) {
                    return;
                }
                Editable editable = (Editable) Editor.this.mTextView.getText();
                int spanStart = editable.getSpanStart(findEquivalentSuggestionSpan);
                int spanEnd = editable.getSpanEnd(findEquivalentSuggestionSpan);
                if (spanStart < 0 || spanEnd <= spanStart) {
                    return;
                }
                String substring = TextUtils.substring(editable, spanStart, spanEnd);
                Intent intent = new Intent(Settings.ACTION_USER_DICTIONARY_INSERT);
                intent.putExtra("word", substring);
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
                int spanStart2 = editable2.getSpanStart(Editor.this.mSuggestionRangeSpan);
                int spanEnd2 = editable2.getSpanEnd(Editor.this.mSuggestionRangeSpan);
                if (spanStart2 >= 0 && spanEnd2 > spanStart2) {
                    if (spanEnd2 < editable2.length() && Character.isSpaceChar(editable2.charAt(spanEnd2)) && (spanStart2 == 0 || Character.isSpaceChar(editable2.charAt(spanStart2 - 1)))) {
                        spanEnd2++;
                    }
                    Editor.this.mTextView.deleteText_internal(spanStart2, spanEnd2);
                }
                hideWithCleanUp();
            }
        }
    }

    public class AssistantCallbackHelper {
        private final Map<MenuItem, View.OnClickListener> mAssistClickHandlers = new HashMap();
        private final SelectionActionModeHelper mHelper;
        private TextClassification mPrevTextClassification;

        public AssistantCallbackHelper(SelectionActionModeHelper selectionActionModeHelper) {
            this.mHelper = selectionActionModeHelper;
        }

        public void clearCallbackHandlers() {
            this.mAssistClickHandlers.clear();
        }

        public View.OnClickListener getOnClickListener(MenuItem menuItem) {
            return this.mAssistClickHandlers.get(menuItem);
        }

        public void updateAssistMenuItems(Menu menu, MenuItem.OnMenuItemClickListener onMenuItemClickListener) {
            AssistantCallbackHelper assistantCallbackHelper;
            Menu menu2;
            MenuItem.OnMenuItemClickListener onMenuItemClickListener2;
            TextClassification textClassification = this.mHelper.getTextClassification();
            if (this.mPrevTextClassification == textClassification) {
                return;
            }
            clearAssistMenuItems(menu);
            if (textClassification != null && shouldEnableAssistMenuItems()) {
                if (!textClassification.getActions().isEmpty()) {
                    assistantCallbackHelper = this;
                    menu2 = menu;
                    onMenuItemClickListener2 = onMenuItemClickListener;
                    assistantCallbackHelper.addAssistMenuItem(menu2, textClassification.getActions().get(0), 16908353, 1, 2, onMenuItemClickListener2).setIntent(textClassification.getIntent());
                } else {
                    assistantCallbackHelper = this;
                    menu2 = menu;
                    onMenuItemClickListener2 = onMenuItemClickListener;
                    if (assistantCallbackHelper.hasLegacyAssistItem(textClassification)) {
                        MenuItem intent = menu2.add(16908353, 16908353, 1, textClassification.getLabel()).setIcon(textClassification.getIcon()).setIntent(textClassification.getIntent());
                        intent.setShowAsAction(2);
                        assistantCallbackHelper.mAssistClickHandlers.put(intent, TextClassification.createIntentOnClickListener(TextClassification.createPendingIntent(Editor.this.mTextView.getContext(), textClassification.getIntent(), assistantCallbackHelper.createAssistMenuItemPendingIntentRequestCode())));
                    }
                }
                int size = textClassification.getActions().size();
                for (int i = 1; i < size; i++) {
                    assistantCallbackHelper.addAssistMenuItem(menu2, textClassification.getActions().get(i), 0, i + 49, 0, onMenuItemClickListener2);
                }
                assistantCallbackHelper.mPrevTextClassification = textClassification;
            }
        }

        private MenuItem addAssistMenuItem(Menu menu, RemoteAction remoteAction, int i, int i2, int i3, MenuItem.OnMenuItemClickListener onMenuItemClickListener) {
            MenuItem contentDescription = menu.add(16908353, i, i2, remoteAction.getTitle()).setContentDescription(remoteAction.getContentDescription());
            if (remoteAction.shouldShowIcon()) {
                contentDescription.setIcon(remoteAction.getIcon().loadDrawable(Editor.this.mTextView.getContext()));
            }
            contentDescription.setShowAsAction(i3);
            this.mAssistClickHandlers.put(contentDescription, TextClassification.createIntentOnClickListener(remoteAction.getActionIntent()));
            Editor.this.mA11ySmartActions.addAction(remoteAction);
            if (onMenuItemClickListener != null) {
                contentDescription.setOnMenuItemClickListener(onMenuItemClickListener);
            }
            return contentDescription;
        }

        private void clearAssistMenuItems(Menu menu) {
            int i = 0;
            while (i < menu.size()) {
                MenuItem item = menu.getItem(i);
                if (item.getGroupId() == 16908353) {
                    menu.removeItem(item.getItemId());
                } else {
                    i++;
                }
            }
            Editor.this.mA11ySmartActions.reset();
        }

        private boolean hasLegacyAssistItem(TextClassification textClassification) {
            if (textClassification.getIcon() == null && TextUtils.isEmpty(textClassification.getLabel())) {
                return false;
            }
            return (textClassification.getIntent() == null && textClassification.getOnClickListener() == null) ? false : true;
        }

        private boolean shouldEnableAssistMenuItems() {
            return Editor.this.mTextView.isDeviceProvisioned() && TextClassificationManager.getSettings(Editor.this.mTextView.getContext()).isSmartTextShareEnabled();
        }

        private int createAssistMenuItemPendingIntentRequestCode() {
            if (Editor.this.mTextView.hasSelection()) {
                return Editor.this.mTextView.getText().subSequence(Editor.this.mTextView.getSelectionStart(), Editor.this.mTextView.getSelectionEnd()).hashCode();
            }
            return 0;
        }

        public boolean onAssistMenuItemClicked(MenuItem menuItem) {
            Intent intent;
            Preconditions.checkArgument(menuItem.getGroupId() == 16908353);
            TextClassification textClassification = Editor.this.getSelectionActionModeHelper().getTextClassification();
            if (shouldEnableAssistMenuItems() && textClassification != null) {
                View.OnClickListener onClickListener = getOnClickListener(menuItem);
                if (onClickListener == null && (intent = menuItem.getIntent()) != null) {
                    onClickListener = TextClassification.createIntentOnClickListener(TextClassification.createPendingIntent(Editor.this.mTextView.getContext(), intent, createAssistMenuItemPendingIntentRequestCode()));
                }
                if (onClickListener != null) {
                    onClickListener.onClick(Editor.this.mTextView);
                    Editor.this.lambda$startActionModeInternal$0();
                }
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

        TextActionModeCallback(int i) {
            this.mHelper = Editor.this.new AssistantCallbackHelper(Editor.this.getSelectionActionModeHelper());
            boolean z = i == 0 || (Editor.this.mTextIsSelectable && i == 2);
            this.mHasSelection = z;
            if (z) {
                SelectionModifierCursorController selectionController = Editor.this.getSelectionController();
                if (selectionController.mStartHandle == null) {
                    Editor.this.loadHandleDrawables(false);
                    selectionController.initHandles();
                    selectionController.hide();
                }
                this.mHandleHeight = Math.max(Editor.this.mSelectHandleLeft.getMinimumHeight(), Editor.this.mSelectHandleRight.getMinimumHeight());
                return;
            }
            InsertionPointCursorController insertionController = Editor.this.getInsertionController();
            if (insertionController != null) {
                insertionController.getHandle();
                this.mHandleHeight = Editor.this.mSelectHandleCenter.getMinimumHeight();
            } else {
                this.mHandleHeight = 0;
            }
        }

        @Override // android.view.ActionMode.Callback
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            this.mHelper.clearCallbackHandlers();
            actionMode.setTitle((CharSequence) null);
            actionMode.setSubtitle((CharSequence) null);
            actionMode.setTitleOptionalHint(true);
            populateMenuWithItems(menu);
            AccessibilityManager accessibilityManager = (AccessibilityManager) Editor.this.mTextView.getContext().getSystemService(Context.ACCESSIBILITY_SERVICE);
            if (accessibilityManager != null && accessibilityManager.isEnabled()) {
                AccessibilityEvent obtain = AccessibilityEvent.obtain(16384);
                obtain.getText().clear();
                obtain.getText().add(Editor.this.mTextView.getContext().getText(R.string.copy_and_paste_toolbar));
                obtain.setPackageName(Editor.this.mTextView.getContext().getPackageName());
                accessibilityManager.sendAccessibilityEvent(obtain);
            }
            ActionMode.Callback customCallback = getCustomCallback();
            if (customCallback != null && !customCallback.onCreateActionMode(actionMode, menu)) {
                Selection.setSelection((Spannable) Editor.this.mTextView.getText(), Editor.this.mTextView.getSelectionEnd());
                return false;
            }
            if (Editor.this.mTextView.canProcessText()) {
                menu.add(0, R.id.manage_apps, 101, R.string.manage_apps).setShowAsAction(8);
                Editor.this.mProcessTextIntentActionsHandler.onInitializeMenu(menu);
            }
            if (!menu.hasVisibleItems() && actionMode.getCustomView() == null) {
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
            String selectedText;
            if (Editor.this.mTextView.canUndo()) {
                menu.add(0, 16908338, 11, R.string.undo).setAlphabeticShortcut(DateFormat.TIME_ZONE).setShowAsAction(2);
            }
            if (Editor.this.mTextView.canRedo()) {
                menu.add(0, 16908339, 12, R.string.redo).setAlphabeticShortcut('y').setShowAsAction(2);
            }
            if (Editor.this.mTextView.canCut() && !Editor.this.mTextView.isClipboardDisallowedByKnox()) {
                menu.add(0, 16908320, 4, 17039363).setAlphabeticShortcut(EpicenterTranslateClipReveal.StateProperty.TARGET_X).setShowAsAction(2);
            }
            if (Editor.this.mTextView.canCopy() && !Editor.this.mTextView.isClipboardDisallowedByKnox()) {
                menu.add(0, 16908321, 5, 17039361).setAlphabeticShortcut('c').setShowAsAction(2);
            }
            if (Editor.this.mTextView.canPaste() && !Editor.this.mTextView.isClipboardDisallowedByKnox()) {
                menu.add(0, 16908322, 6, 17039371).setAlphabeticShortcut('v').setShowAsAction(2);
            }
            if (Editor.this.mTextView.canShare()) {
                menu.add(0, 16908341, 10, R.string.share).setShowAsAction(1);
            }
            if (Editor.this.mTextView.canClipboard()) {
                menu.add(0, R.id.clipboard, 19, R.string.tw_clipboard_title_text).setIcon(Editor.this.mTextView.getContext().getResources().getDrawable(R.drawable.tw_floating_popup_button_ic_clipboard)).setShowAsAction(1);
            }
            if (Editor.this.mTextView.canRequestAutofill() && ((selectedText = Editor.this.mTextView.getSelectedText()) == null || selectedText.isEmpty())) {
                menu.add(0, 16908355, 15, 17039386).setShowAsAction(0);
            }
            if (Editor.this.mTextView.canPasteAsPlainText() && !Editor.this.mTextView.isClipboardDisallowedByKnox()) {
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
            if (Editor.this.SEP_VERSION.floatValue() < 15.1d && ViewRune.SUPPORT_EAGLE_EYE && Editor.this.mTextView.canScanText()) {
                menu.add(0, R.id.scanText, 18, R.string.scan_text).setShowAsAction(1);
            }
            if (Editor.this.SEP_VERSION.floatValue() < 15.1d && Editor.this.mTextView.canHBDTranslate()) {
                menu.add(0, R.id.hbdTranslate, 17, R.string.hbd_translate).setShowAsAction(1);
            }
            if (Editor.this.SEP_VERSION.floatValue() >= 15.1d && !Editor.this.mTextView.hasPasswordTransformationMethod() && Editor.this.mTextView.hasSelection() && ViewRune.WIDGET_SSS_TRANSLATE_SUPPORTED && Editor.this.mTextView.getContext().canStartActivityForResult()) {
                menu.add(0, R.id.sssTranslate, 8, R.string.sss_translate).setShowAsAction(1);
            }
            if (ViewRune.SUPPORT_WRITING_TOOLKIT && !Editor.this.mTextView.isDisableWritingToolkit() && Editor.this.mShowSoftInputOnFocus && Editor.this.mTextView.getContext().canStartActivityForResult() && !Editor.this.mTextView.isWritingToolkitDisallowedByKnox()) {
                menu.add(0, R.id.writing_toolkit, 0, R.string.writing_toolkit).setShowAsAction(2);
            }
        }

        @Override // android.view.ActionMode.Callback
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            updateSelectAllItem(menu);
            if (!Editor.this.mIsThemeDeviceDefault) {
                updateReplaceItem(menu);
            }
            if (Editor.this.mTextView.canAssist()) {
                this.mHelper.updateAssistMenuItems(menu, null);
            }
            ActionMode.Callback customCallback = getCustomCallback();
            if (customCallback != null) {
                return customCallback.onPrepareActionMode(actionMode, menu);
            }
            return true;
        }

        private void updateSelectAllItem(Menu menu) {
            boolean canSelectAllText = Editor.this.mTextView.canSelectAllText();
            boolean z = menu.findItem(16908319) != null;
            if (canSelectAllText && !z) {
                menu.add(0, 16908319, 9, 17039373).setShowAsAction(1);
            } else {
                if (canSelectAllText || !z) {
                    return;
                }
                menu.removeItem(16908319);
            }
        }

        private void updateReplaceItem(Menu menu) {
            boolean z = Editor.this.mTextView.isSuggestionsEnabled() && Editor.this.shouldOfferToShowSuggestions();
            boolean z2 = menu.findItem(16908340) != null;
            if (z && !z2) {
                menu.add(0, 16908340, 14, R.string.replace).setShowAsAction(1);
            } else {
                if (z || !z2) {
                    return;
                }
                menu.removeItem(16908340);
            }
        }

        @Override // android.view.ActionMode.Callback
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            Editor.this.getSelectionActionModeHelper().onSelectionAction(menuItem.getItemId(), menuItem.getTitle().toString());
            if (Editor.this.mProcessTextIntentActionsHandler.performMenuItemAction(menuItem)) {
                return true;
            }
            ActionMode.Callback customCallback = getCustomCallback();
            if (customCallback != null && customCallback.onActionItemClicked(actionMode, menuItem)) {
                return true;
            }
            if (menuItem.getGroupId() == 16908353 && this.mHelper.onAssistMenuItemClicked(menuItem)) {
                return true;
            }
            if (menuItem.getItemId() == 16908341 && (actionMode instanceof FloatingActionMode)) {
                Editor.this.mTextView.startChooserPopupActivity(((FloatingActionMode) actionMode).getContentRectOnScreen(), false);
                return true;
            }
            return Editor.this.mTextView.onTextContextMenuItem(menuItem.getItemId());
        }

        @Override // android.view.ActionMode.Callback
        public void onDestroyActionMode(ActionMode actionMode) {
            Editor.this.getSelectionActionModeHelper().onDestroyActionMode();
            Editor.this.mTextActionMode = null;
            ActionMode.Callback customCallback = getCustomCallback();
            if (customCallback != null) {
                customCallback.onDestroyActionMode(actionMode);
            }
            if (!Editor.this.mPreserveSelection) {
                Selection.setSelection((Spannable) Editor.this.mTextView.getText(), Editor.this.mTextView.getSelectionEnd());
            }
            if (Editor.this.mSelectionModifierCursorController != null) {
                Editor.this.mSelectionModifierCursorController.hide();
            }
            if (Editor.this.mInsertionPointCursorController != null && Editor.this.mTextView.getText() != null && Editor.this.mTextView.getText().length() == 0) {
                Editor.this.mInsertionPointCursorController.hide();
                Editor.this.mToggleActionMode = false;
            }
            this.mHelper.clearCallbackHandlers();
            Editor.this.mRequestingLinkActionMode = false;
        }

        @Override // android.view.ActionMode.Callback2
        public void onGetContentRect(ActionMode actionMode, View view, Rect rect) {
            if (!view.equals(Editor.this.mTextView) || Editor.this.getActiveLayout() == null) {
                super.onGetContentRect(actionMode, view, rect);
                return;
            }
            int selectionStartTransformed = Editor.this.mTextView.getSelectionStartTransformed();
            int selectionEndTransformed = Editor.this.mTextView.getSelectionEndTransformed();
            Layout activeLayout = Editor.this.getActiveLayout();
            if (selectionStartTransformed != selectionEndTransformed) {
                this.mSelectionPath.reset();
                activeLayout.getSelectionPath(selectionStartTransformed, selectionEndTransformed, this.mSelectionPath);
                this.mSelectionPath.computeBounds(this.mSelectionBounds, true);
                this.mSelectionBounds.bottom += this.mHandleHeight;
            } else {
                int lineForOffset = activeLayout.getLineForOffset(selectionStartTransformed);
                float clampHorizontalPosition = Editor.this.clampHorizontalPosition(null, activeLayout.getPrimaryHorizontal(selectionEndTransformed));
                this.mSelectionBounds.set(clampHorizontalPosition, activeLayout.getLineTop(lineForOffset), clampHorizontalPosition, activeLayout.getLineBottom(lineForOffset) + this.mHandleHeight);
            }
            float viewportToContentHorizontalOffset = Editor.this.mTextView.viewportToContentHorizontalOffset();
            float viewportToContentVerticalOffset = Editor.this.mTextView.viewportToContentVerticalOffset();
            rect.set((int) Math.floor(this.mSelectionBounds.left + viewportToContentHorizontalOffset), (int) Math.floor(this.mSelectionBounds.top + viewportToContentVerticalOffset), (int) Math.ceil(this.mSelectionBounds.right + viewportToContentHorizontalOffset), (int) Math.ceil(this.mSelectionBounds.bottom + viewportToContentVerticalOffset));
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
        public void updatePosition(int i, int i2, boolean z, boolean z2) {
            InputMethodManager inputMethodManager;
            CursorAnchorInfo cursorAnchorInfo;
            InputMethodState inputMethodState = Editor.this.mInputMethodState;
            if (inputMethodState == null || inputMethodState.mBatchEditNesting > 0 || (inputMethodManager = Editor.this.getInputMethodManager()) == null || !inputMethodManager.hasActiveInputConnection(Editor.this.mTextView) || (inputMethodState.mUpdateCursorAnchorInfoMode & 3) == 0 || (cursorAnchorInfo = Editor.this.mTextView.getCursorAnchorInfo(inputMethodState.mUpdateCursorAnchorInfoFilter, this.mCursorAnchorInfoBuilder, this.mViewToScreenMatrix)) == null) {
                return;
            }
            inputMethodManager.updateCursorAnchorInfo(Editor.this.mTextView, cursorAnchorInfo);
            Editor.this.mInputMethodState.mUpdateCursorAnchorInfoMode &= -2;
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
            ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
            this.mAnimator = ofFloat;
            ofFloat.setDuration(DURATION);
            ofFloat.setInterpolator(new LinearInterpolator());
            ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: android.widget.Editor$MagnifierMotionAnimator$$ExternalSyntheticLambda0
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    Editor.MagnifierMotionAnimator.this.lambda$new$0(valueAnimator);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$0(ValueAnimator valueAnimator) {
            float f = this.mAnimationStartX;
            this.mAnimationCurrentX = f + ((this.mLastX - f) * valueAnimator.getAnimatedFraction());
            float f2 = this.mAnimationStartY;
            float animatedFraction = f2 + ((this.mLastY - f2) * valueAnimator.getAnimatedFraction());
            this.mAnimationCurrentY = animatedFraction;
            this.mMagnifier.show(this.mAnimationCurrentX, animatedFraction);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void show(float f, float f2) {
            if (this.mMagnifierIsShowing && f2 != this.mLastY) {
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
                this.mMagnifier.show(f, f2);
            }
            this.mLastX = f;
            this.mLastY = f2;
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

        protected int getCursorOffset() {
            return 0;
        }

        protected abstract int getHorizontalGravity(boolean z);

        protected abstract int getHotspotX(Drawable drawable, boolean z);

        protected abstract int getMagnifierHandleTrigger();

        protected void hideAfterDelay() {
        }

        protected boolean isScreenOut(int i, boolean z) {
            return false;
        }

        void onHandleMoved() {
        }

        protected void removeHiderCallback() {
        }

        protected abstract void updatePosition(float f, float f2, boolean z);

        protected abstract void updateSelection(int i);

        private HandleView(Drawable drawable, Drawable drawable2, int i) {
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
            this.CHANGE_SIZE_EVALUATOR = new TypeEvaluator<Rect>() { // from class: android.widget.Editor.HandleView.1
                @Override // android.animation.TypeEvaluator
                public Rect evaluate(float f, Rect rect, Rect rect2) {
                    int width = rect.width();
                    int height = rect.height();
                    int width2 = rect2.width();
                    int height2 = rect2.height();
                    return HandleView.this.getDrawableBounds(width + Math.round((width2 - width) * f), height + Math.round((height2 - height) * f));
                }
            };
            this.mPathInterpolator = new PathInterpolator(0.25f, 0.46f, 0.45f, 1.0f);
            setId(i);
            LinearLayout linearLayout = new LinearLayout(Editor.this.mTextView.getContext());
            PopupWindow popupWindow = new PopupWindow(Editor.this.mTextView.getContext(), (AttributeSet) null, 16843464);
            this.mContainer = popupWindow;
            popupWindow.setSplitTouchEnabled(true);
            popupWindow.setClippingEnabled(false);
            popupWindow.setWindowLayoutType(1002);
            linearLayout.addView(this);
            popupWindow.setContentView(linearLayout);
            setDrawables(drawable, drawable2);
            this.mMinSize = Editor.this.mTextView.getContext().getResources().getDimensionPixelSize(R.dimen.text_handle_min_size);
            popupWindow.setWidth((int) Math.ceil(this.mDrawable.getIntrinsicWidth() * 1.5f));
            popupWindow.setHeight((int) Math.ceil(this.mDrawable.getIntrinsicHeight() * 1.5f));
            float preferredHeight = getPreferredHeight();
            this.mTouchOffsetY = (-0.3f) * preferredHeight;
            int intCoreSetting = AppGlobals.getIntCoreSetting(WidgetFlags.KEY_FINGER_TO_CURSOR_DISTANCE, -1);
            if (intCoreSetting < 0 || intCoreSetting > 100) {
                float f = preferredHeight * 0.7f;
                this.mIdealVerticalOffset = f;
                this.mIdealFingerToCursorOffset = (int) (f - this.mTouchOffsetY);
            } else {
                int applyDimension = (int) TypedValue.applyDimension(1, intCoreSetting, Editor.this.mTextView.getContext().getResources().getDisplayMetrics());
                this.mIdealFingerToCursorOffset = applyDimension;
                this.mIdealVerticalOffset = applyDimension + this.mTouchOffsetY;
            }
        }

        public float getIdealVerticalOffset() {
            return this.mIdealVerticalOffset;
        }

        final int getIdealFingerToCursorOffset() {
            return this.mIdealFingerToCursorOffset;
        }

        void setDrawables(Drawable drawable, Drawable drawable2) {
            this.mDrawableLtr = drawable;
            this.mDrawableRtl = drawable2;
            updateDrawable(true);
        }

        protected void updateDrawable(boolean z) {
            Layout layout;
            if ((z || !this.mIsDragging) && (layout = Editor.this.mTextView.getLayout()) != null) {
                int currentCursorOffset = getCurrentCursorOffset();
                boolean isAtRtlRun = isAtRtlRun(layout, currentCursorOffset);
                Drawable drawable = this.mDrawable;
                Drawable drawable2 = isAtRtlRun ? this.mDrawableRtl : this.mDrawableLtr;
                this.mDrawable = drawable2;
                this.mHotspotX = getHotspotX(drawable2, isAtRtlRun);
                this.mHorizontalGravity = getHorizontalGravity(isAtRtlRun);
                ((LinearLayout) this.mContainer.getContentView()).setGravity(this.mHorizontalGravity);
                if (isScreenOut(getCursorHorizontalPosition(layout, currentCursorOffset) + getCursorOffset() + Editor.this.mTextView.viewportToContentHorizontalOffset() + Editor.this.getPositionListener().getPositionX(), isAtRtlRun)) {
                    boolean z2 = !isAtRtlRun;
                    Drawable drawable3 = !isAtRtlRun ? this.mDrawableRtl : this.mDrawableLtr;
                    this.mDrawable = drawable3;
                    this.mHotspotX = getHotspotX(drawable3, z2);
                    this.mHorizontalGravity = getHorizontalGravity(z2);
                    ((LinearLayout) this.mContainer.getContentView()).setGravity(this.mHorizontalGravity);
                }
                if (drawable == this.mDrawable || !isShowing()) {
                    return;
                }
                int cursorHorizontalPosition = ((getCursorHorizontalPosition(layout, currentCursorOffset) - this.mHotspotX) - getHorizontalOffset()) + getCursorOffset();
                this.mPositionX = cursorHorizontalPosition;
                this.mPositionX = cursorHorizontalPosition + Editor.this.mTextView.viewportToContentHorizontalOffset();
                this.mPositionHasChanged = true;
                this.mIsSwitching = true;
                this.mContainer.dismiss();
                updatePosition(this.mLastParentX, this.mLastParentY, false, false);
                postInvalidate();
                this.mIsSwitching = false;
            }
        }

        private void startTouchUpFilter(int i) {
            this.mNumberPreviousOffsets = 0;
            addPositionToTouchUpFilter(i);
        }

        private void addPositionToTouchUpFilter(int i) {
            int i2 = (this.mPreviousOffsetIndex + 1) % 5;
            this.mPreviousOffsetIndex = i2;
            this.mPreviousOffsets[i2] = i;
            this.mPreviousOffsetsTimes[i2] = SystemClock.uptimeMillis();
            this.mNumberPreviousOffsets++;
        }

        private void filterOnTouchUp(boolean z) {
            long uptimeMillis = SystemClock.uptimeMillis();
            int i = this.mPreviousOffsetIndex;
            int min = Math.min(this.mNumberPreviousOffsets, 5);
            int i2 = 0;
            while (i2 < min && uptimeMillis - this.mPreviousOffsetsTimes[i] < 150) {
                i2++;
                i = ((this.mPreviousOffsetIndex - i2) + 5) % 5;
            }
            if (i2 <= 0 || i2 >= min || uptimeMillis - this.mPreviousOffsetsTimes[i] <= 350) {
                return;
            }
            positionAtCursorOffset(this.mPreviousOffsets[i], false, z);
        }

        public boolean offsetHasBeenChanged() {
            return this.mNumberPreviousOffsets > 1;
        }

        @Override // android.view.View
        protected void onMeasure(int i, int i2) {
            int preferredWidth = getPreferredWidth();
            int preferredHeight = getPreferredHeight();
            if (this.mIsDragging || this.mIsRestoring) {
                preferredWidth = (int) Math.ceil(this.mDrawable.getIntrinsicWidth() * 1.5f);
                preferredHeight = (int) Math.ceil(this.mDrawable.getIntrinsicHeight() * 1.5f);
            }
            setMeasuredDimension(preferredWidth, preferredHeight);
        }

        @Override // android.view.View
        public void invalidate() {
            super.invalidate();
            if (this.mIsShowAnimating || this.mIsHideAnimating || !isShowing()) {
                return;
            }
            positionAtCursorOffset(getCurrentCursorOffset(), true, false);
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
            return Editor.this.mTextView.isPositionVisible(this.mPositionX + this.mHotspotX + getHorizontalOffset(), this.mPositionY);
        }

        private void setVisible(boolean z) {
            this.mContainer.getContentView().setVisibility(z ? 0 : 4);
        }

        protected boolean isAtRtlRun(Layout layout, int i) {
            return layout.isRtlCharAt(Editor.this.mTextView.originalToTransformed(i, 1));
        }

        public float getHorizontal(Layout layout, int i) {
            return layout.getPrimaryHorizontal(Editor.this.mTextView.originalToTransformed(i, 1));
        }

        public int getLineForOffset(Layout layout, int i) {
            return layout.getLineForOffset(Editor.this.mTextView.originalToTransformed(i, 1));
        }

        protected int getOffsetAtCoordinate(Layout layout, int i, float f) {
            return Editor.this.mTextView.getOffsetAtCoordinate(i, f);
        }

        protected void positionAtCursorOffset(int i, boolean z, boolean z2) {
            if (Editor.this.mTextView.getLayout() == null) {
                Editor.this.prepareCursorControllers();
                return;
            }
            Layout activeLayout = Editor.this.getActiveLayout();
            boolean z3 = i != this.mPreviousOffset;
            if (z3 || z) {
                if (z3) {
                    updateSelection(i);
                    if (z2 && Editor.this.mHapticTextHandleEnabled) {
                        Editor.this.mTextView.performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(41));
                    }
                    addPositionToTouchUpFilter(i);
                }
                int lineForOffset = getLineForOffset(activeLayout, i);
                this.mPrevLine = lineForOffset;
                if (!this.mIsDragging && !this.mIsRestoring) {
                    this.mPositionX = ((getCursorHorizontalPosition(activeLayout, i) - this.mHotspotX) - getHorizontalOffset()) + getCursorOffset();
                    this.mPositionY = activeLayout.getLineBottom(lineForOffset, false);
                    this.mPositionX += Editor.this.mTextView.viewportToContentHorizontalOffset();
                    this.mPositionY += Editor.this.mTextView.viewportToContentVerticalOffset();
                }
                this.mPreviousOffset = i;
                this.mPositionHasChanged = true;
            }
        }

        int getCursorHorizontalPosition(Layout layout, int i) {
            return (int) (getHorizontal(layout, i) - 0.5f);
        }

        @Override // android.widget.Editor.TextViewPositionListener
        public void updatePosition(int i, int i2, boolean z, boolean z2) {
            positionAtCursorOffset(getCurrentCursorOffset(), z2, false);
            if (z || this.mPositionHasChanged) {
                if (this.mIsDragging) {
                    if (i != this.mLastParentX || i2 != this.mLastParentY) {
                        this.mTouchToWindowOffsetX += i - r5;
                        this.mTouchToWindowOffsetY += i2 - this.mLastParentY;
                        this.mLastParentX = i;
                        this.mLastParentY = i2;
                    }
                    onHandleMoved();
                }
                if (!this.mIsDragging && !this.mIsRestoring) {
                    if (shouldShow()) {
                        int[] iArr = {this.mPositionX + this.mHotspotX + getHorizontalOffset(), this.mPositionY};
                        Editor.this.mTextView.transformFromViewToWindowSpace(iArr);
                        iArr[0] = iArr[0] - (this.mHotspotX + getHorizontalOffset());
                        if (isShowing() && !this.mIsHideAnimating) {
                            this.mContainer.update(iArr[0], iArr[1], -1, -1);
                        } else if (isValid()) {
                            this.mContainer.showAtLocation(Editor.this.mTextView, 0, iArr[0], iArr[1]);
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
        protected void onDraw(Canvas canvas) {
            int intrinsicWidth = this.mDrawable.getIntrinsicWidth();
            int horizontalOffset = getHorizontalOffset() - this.mContentsViewOffset;
            if (!this.mIsDragging && !this.mIsRestoring && !this.mIsShowAnimating && !this.mIsHideAnimating) {
                Drawable drawable = this.mDrawable;
                drawable.setBounds(horizontalOffset, 0, intrinsicWidth + horizontalOffset, drawable.getIntrinsicHeight());
            }
            this.mDrawable.draw(canvas);
        }

        protected int getHorizontalOffset() {
            int i;
            int preferredWidth = getPreferredWidth();
            int intrinsicWidth = this.mDrawable.getIntrinsicWidth();
            int width = this.mContainer.getWidth();
            int i2 = this.mHorizontalGravity;
            if (i2 == 3) {
                i = 0;
                this.mContentsViewOffset = 0;
            } else if (i2 != 5) {
                i = (preferredWidth - intrinsicWidth) / 2;
                this.mContentsViewOffset = (width - preferredWidth) / 2;
            } else {
                i = preferredWidth - intrinsicWidth;
                this.mContentsViewOffset = width - preferredWidth;
            }
            return i + this.mContentsViewOffset;
        }

        private boolean tooLargeTextForMagnifier() {
            if (Editor.this.mNewMagnifierEnabled) {
                Layout layout = Editor.this.mTextView.getLayout();
                int lineForOffset = getLineForOffset(layout, getCurrentCursorOffset());
                return layout.getLineBottom(lineForOffset, false) - layout.getLineTop(lineForOffset) >= Editor.this.mMaxLineHeightForMagnifier;
            }
            float round = Math.round(Editor.this.mMagnifierAnimator.mMagnifier.getHeight() / Editor.this.mMagnifierAnimator.mMagnifier.getZoom());
            Paint.FontMetrics fontMetrics = Editor.this.mTextView.getPaint().getFontMetrics();
            return (fontMetrics.descent - fontMetrics.ascent) * this.mTextViewScaleY > round;
        }

        private boolean checkForTransforms() {
            if (Editor.this.mMagnifierAnimator.mMagnifierIsShowing) {
                return true;
            }
            if (Editor.this.mTextView.getRotation() != 0.0f || Editor.this.mTextView.getRotationX() != 0.0f || Editor.this.mTextView.getRotationY() != 0.0f) {
                return false;
            }
            this.mTextViewScaleX = Editor.this.mTextView.getScaleX();
            this.mTextViewScaleY = Editor.this.mTextView.getScaleY();
            for (ViewParent parent = Editor.this.mTextView.getParent(); parent != null; parent = parent.getParent()) {
                if (parent instanceof View) {
                    View view = (View) parent;
                    if (view.getRotation() != 0.0f || view.getRotationX() != 0.0f || view.getRotationY() != 0.0f) {
                        return false;
                    }
                    this.mTextViewScaleX *= view.getScaleX();
                    this.mTextViewScaleY *= view.getScaleY();
                }
            }
            return true;
        }

        private boolean obtainMagnifierShowCoordinates(MotionEvent motionEvent, PointF pointF) {
            int selectionStart;
            int i;
            int magnifierHandleTrigger = getMagnifierHandleTrigger();
            if (magnifierHandleTrigger == 0) {
                selectionStart = Editor.this.mTextView.getSelectionStart();
                i = -1;
            } else if (magnifierHandleTrigger == 1) {
                selectionStart = Editor.this.mTextView.getSelectionStart();
                i = Editor.this.mTextView.getSelectionEnd();
            } else if (magnifierHandleTrigger != 2) {
                selectionStart = -1;
                i = -1;
            } else {
                selectionStart = Editor.this.mTextView.getSelectionEnd();
                i = Editor.this.mTextView.getSelectionStart();
            }
            if (selectionStart == -1) {
                return false;
            }
            if (motionEvent.getActionMasked() == 0) {
                this.mCurrentDragInitialTouchRawX = motionEvent.getRawX();
            } else if (motionEvent.getActionMasked() == 1) {
                this.mCurrentDragInitialTouchRawX = -1.0f;
            }
            Layout layout = Editor.this.mTextView.getLayout();
            int lineForOffset = getLineForOffset(layout, selectionStart);
            if (i != -1 && lineForOffset == getLineForOffset(layout, selectionStart)) {
                if (selectionStart < i) {
                }
                if (getHorizontal(Editor.this.mTextView.getLayout(), selectionStart) < getHorizontal(Editor.this.mTextView.getLayout(), i)) {
                }
            }
            Editor.this.mTextView.getLocationOnScreen(new int[2]);
            float rawX = motionEvent.getRawX() - r0[0];
            if (this.mTextViewScaleX != 1.0f) {
                float rawX2 = motionEvent.getRawX();
                float f = this.mCurrentDragInitialTouchRawX;
                rawX = (((rawX2 - f) * this.mTextViewScaleX) + f) - r0[0];
            }
            pointF.x = rawX;
            pointF.y = ((((Editor.this.mTextView.getLayout().getLineTop(lineForOffset) + Editor.this.mTextView.getLayout().getLineBottom(lineForOffset, false)) / 2.0f) + Editor.this.mTextView.getTotalPaddingTop()) - Editor.this.mTextView.getScrollY()) * this.mTextViewScaleY;
            return true;
        }

        private boolean handleOverlapsMagnifier(HandleView handleView, Rect rect) {
            PopupWindow popupWindow = handleView.mContainer;
            if (popupWindow.hasDecorView()) {
                return Rect.intersects(new Rect(popupWindow.getDecorViewLayoutParams().x, popupWindow.getDecorViewLayoutParams().y, popupWindow.getDecorViewLayoutParams().x + popupWindow.getContentView().getWidth(), popupWindow.getDecorViewLayoutParams().y + popupWindow.getContentView().getHeight()), rect);
            }
            return false;
        }

        private HandleView getOtherSelectionHandle() {
            SelectionModifierCursorController selectionController = Editor.this.getSelectionController();
            if (selectionController == null || !selectionController.isActive()) {
                return null;
            }
            if (selectionController.mStartHandle != this) {
                return selectionController.mStartHandle;
            }
            return selectionController.mEndHandle;
        }

        private void updateHandlesVisibility() {
            Point position = Editor.this.mMagnifierAnimator.mMagnifier.getPosition();
            if (position == null) {
                return;
            }
            setVisible((handleOverlapsMagnifier(this, new Rect(position.x, position.y, position.x + Editor.this.mMagnifierAnimator.mMagnifier.getWidth(), position.y + Editor.this.mMagnifierAnimator.mMagnifier.getHeight())) || Editor.this.mDrawCursorOnMagnifier) ? false : true);
            HandleView otherSelectionHandle = getOtherSelectionHandle();
            if (otherSelectionHandle != null) {
                otherSelectionHandle.setVisible(!handleOverlapsMagnifier(otherSelectionHandle, r1));
            }
        }

        protected final void updateMagnifier(MotionEvent motionEvent) {
            if (Editor.this.getMagnifierAnimator() == null) {
                return;
            }
            PointF pointF = new PointF();
            if (checkForTransforms() && !tooLargeTextForMagnifier() && obtainMagnifierShowCoordinates(motionEvent, pointF) && Editor.this.mTextView.showUIForTouchScreen()) {
                Editor.this.mRenderCursorRegardlessTiming = true;
                Editor.this.mTextView.invalidateCursorPath();
                Editor.this.suspendBlink();
                if (Editor.this.mNewMagnifierEnabled) {
                    Layout layout = Editor.this.mTextView.getLayout();
                    int lineForOffset = getLineForOffset(layout, getCurrentCursorOffset());
                    Editor.this.mDrawCursorOnMagnifier = pointF.x < ((float) ((((int) layout.getLineLeft(lineForOffset)) + (Editor.this.mTextView.getTotalPaddingLeft() - Editor.this.mTextView.getScrollX())) + (-20))) || pointF.x > ((float) ((((int) layout.getLineRight(lineForOffset)) + (Editor.this.mTextView.getTotalPaddingLeft() - Editor.this.mTextView.getScrollX())) + 20));
                    Editor.this.mMagnifierAnimator.mMagnifier.setDrawCursor(Editor.this.mDrawCursorOnMagnifier, Editor.this.mDrawableForCursor);
                    boolean z = Editor.this.mCursorVisible;
                    Editor editor = Editor.this;
                    editor.mCursorVisible = true ^ editor.mDrawCursorOnMagnifier;
                    if (Editor.this.mCursorVisible && !z) {
                        Editor.this.updateCursorPosition();
                    }
                    int lineBottom = layout.getLineBottom(lineForOffset, false) - layout.getLineTop(lineForOffset);
                    float f = Editor.this.mInitialZoom;
                    if (lineBottom < Editor.this.mMinLineHeightForMagnifier) {
                        f = (f * Editor.this.mMinLineHeightForMagnifier) / lineBottom;
                    }
                    Editor.this.mMagnifierAnimator.mMagnifier.updateSourceFactors(lineBottom, f);
                    Editor.this.mMagnifierAnimator.mMagnifier.show(pointF.x, pointF.y);
                } else {
                    Editor.this.mMagnifierAnimator.show(pointF.x, pointF.y);
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
                HandleView otherSelectionHandle = getOtherSelectionHandle();
                if (otherSelectionHandle != null) {
                    otherSelectionHandle.setVisible(true);
                }
            }
        }

        @Override // android.view.View
        public boolean onTouchEvent(MotionEvent motionEvent) {
            float min;
            Editor.this.updateFloatingToolbarVisibility(motionEvent);
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked != 0) {
                if (actionMasked == 1) {
                    filterOnTouchUp(motionEvent.isFromSource(4098));
                    this.mIsDragging = false;
                    this.mIsRestoring = true;
                    restore();
                    Editor.this.updateWritingToolkit();
                } else if (actionMasked == 2) {
                    float rawX = (motionEvent.getRawX() - this.mLastParentXOnScreen) + this.mLastParentX;
                    float rawY = motionEvent.getRawY() - this.mLastParentYOnScreen;
                    int i = this.mLastParentY;
                    float f = this.mTouchToWindowOffsetY - i;
                    float f2 = ((rawY + i) - this.mPositionY) - i;
                    float f3 = this.mIdealVerticalOffset;
                    if (f < f3) {
                        min = Math.max(Math.min(f2, f3), f);
                    } else {
                        min = Math.min(Math.max(f2, f3), f);
                    }
                    int i2 = this.mLastParentY;
                    this.mTouchToWindowOffsetY = min + i2;
                    this.mVerticalScrolledYOffset = i2 - this.mFirstParentY;
                    this.mIsVerticalScrolled = isScrollChanged(motionEvent);
                    try {
                        updatePosition((rawX - this.mTouchToWindowOffsetX) + this.mHotspotX + getHorizontalOffset(), (motionEvent.getRawY() - this.mTouchToWindowOffsetY) + this.mTouchOffsetY, motionEvent.isFromSource(4098));
                    } catch (IllegalArgumentException e) {
                        Log.e("Editor", "handle view action move IllegalArgumentException : " + e);
                    } catch (IndexOutOfBoundsException e2) {
                        Log.e("Editor", "handle view action move IndexOutOfBoundsException : " + e2);
                    }
                    if (this.mHandleVelocityTracker != null) {
                        MotionEvent obtain = MotionEvent.obtain(this.mDownTime, SystemClock.uptimeMillis(), motionEvent.getActionMasked(), motionEvent.getRawX(), motionEvent.getRawY(), 0);
                        this.mHandleVelocityTracker.addMovement(obtain);
                        this.mHandleVelocityTracker.computeCurrentVelocity(1);
                        Editor.this.mIsMagnifierHideByVelocityTracker = this.mHandleVelocityTracker.getYVelocity() > 0.5f || this.mHandleVelocityTracker.getYVelocity() < -0.5f;
                        obtain.recycle();
                    }
                } else if (actionMasked == 3) {
                    this.mIsDragging = false;
                    this.mIsRestoring = true;
                    restore();
                    updateDrawable(false);
                    VelocityTracker velocityTracker = this.mHandleVelocityTracker;
                    if (velocityTracker != null) {
                        velocityTracker.recycle();
                        this.mHandleVelocityTracker = null;
                    }
                }
            } else {
                startTouchUpFilter(getCurrentCursorOffset());
                PositionListener positionListener = Editor.this.getPositionListener();
                this.mLastParentX = positionListener.getPositionX();
                int positionY = positionListener.getPositionY();
                this.mLastParentY = positionY;
                this.mFirstParentY = positionY;
                this.mLastParentXOnScreen = positionListener.getPositionXOnScreen();
                this.mLastParentYOnScreen = positionListener.getPositionYOnScreen();
                float rawX2 = (motionEvent.getRawX() - this.mLastParentXOnScreen) + this.mLastParentX;
                float rawY2 = (motionEvent.getRawY() - this.mLastParentYOnScreen) + this.mLastParentY;
                this.mTouchToWindowOffsetX = rawX2 - this.mPositionX;
                this.mTouchToWindowOffsetY = rawY2 - this.mPositionY;
                this.mIsDragging = true;
                this.mPreviousLineTouched = -1;
                this.mVerticalOffset = motionEvent.getRawY() - this.mPositionY;
                this.mHorizontalOffset = motionEvent.getRawX() - this.mPositionX;
                magnifySize();
                VelocityTracker velocityTracker2 = this.mHandleVelocityTracker;
                if (velocityTracker2 == null) {
                    this.mHandleVelocityTracker = VelocityTracker.obtain();
                } else {
                    velocityTracker2.clear();
                }
                long uptimeMillis = SystemClock.uptimeMillis();
                this.mDownTime = uptimeMillis;
                MotionEvent obtain2 = MotionEvent.obtain(uptimeMillis, SystemClock.uptimeMillis(), motionEvent.getActionMasked(), motionEvent.getRawX(), motionEvent.getRawY(), 0);
                this.mHandleVelocityTracker.addMovement(obtain2);
                this.mHandleVelocityTracker.computeCurrentVelocity(1);
                obtain2.recycle();
            }
            return true;
        }

        public boolean isDragging() {
            return this.mIsDragging;
        }

        public void onDetached() {
            dismissMagnifier();
        }

        @Override // android.view.View
        protected void onSizeChanged(int i, int i2, int i3, int i4) {
            super.onSizeChanged(i, i2, i3, i4);
            setSystemGestureExclusionRects(Collections.singletonList(new Rect(0, 0, i, i2)));
        }

        private boolean isScrollChanged(MotionEvent motionEvent) {
            Rect rect = new Rect();
            Editor.this.mTextView.getWindowVisibleDisplayFrame(rect);
            return motionEvent.getRawY() > ((float) rect.bottom) || motionEvent.getRawY() < ((float) rect.top);
        }

        protected void updatePositionDuringDragging(int i, int i2) {
            int[] iArr = new int[2];
            getLocationInWindow(iArr);
            iArr[0] = iArr[0] + Editor.this.mTextView.getMeasuredWidth();
            iArr[1] = iArr[1] + Editor.this.mTextView.getMeasuredHeight();
            this.mPositionX = Math.max((-this.mHotspotX) - getHorizontalOffset(), Math.min(i, iArr[0]));
            this.mPositionY = Math.max(0, Math.min(i2, iArr[1]));
            int[] iArr2 = {this.mPositionX + this.mHotspotX + getHorizontalOffset(), this.mPositionY};
            Editor.this.mTextView.transformFromViewToWindowSpace(iArr2);
            iArr2[0] = iArr2[0] - (this.mHotspotX + getHorizontalOffset());
            if (isShowing()) {
                this.mContainer.update(iArr2[0], iArr2[1], -1, -1);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Rect getDrawableBounds(int i, int i2) {
            int i3;
            int horizontalOffset = getHorizontalOffset();
            int i4 = this.mContentsViewOffset;
            int i5 = horizontalOffset - i4;
            if (this.mIsDragging || this.mIsRestoring) {
                i5 += i4;
            }
            Drawable drawable = this.mDrawable;
            int hotspotX = getHotspotX(drawable, drawable == this.mDrawableRtl);
            int i6 = this.mHorizontalGravity;
            if (i6 == 1) {
                i3 = i / 2;
            } else if (i6 == 3) {
                i3 = i / 4;
            } else {
                i3 = i6 != 5 ? 0 : (i * 3) / 4;
            }
            int i7 = i5 - (i3 - hotspotX);
            return new Rect(i7, 0, i + i7, i2);
        }

        private ObjectAnimator getChangeSizeAnimator(Rect rect, final Rect rect2) {
            ObjectAnimator ofObject = ObjectAnimator.ofObject(this.mDrawable, "bounds", this.CHANGE_SIZE_EVALUATOR, rect, rect2);
            ofObject.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: android.widget.Editor.HandleView.2
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    HandleView.this.invalidate();
                }
            });
            ofObject.addListener(new AnimatorListenerAdapter() { // from class: android.widget.Editor.HandleView.3
                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator, boolean z) {
                    HandleView.this.requestLayout();
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    HandleView.this.mDrawable.setBounds(HandleView.this.getDrawableBounds(rect2.width(), rect2.height()));
                    HandleView.this.requestLayout();
                    HandleView.this.invalidate();
                }
            });
            return ofObject;
        }

        private void magnifySize() {
            int intrinsicWidth = this.mDrawable.getIntrinsicWidth();
            int intrinsicHeight = this.mDrawable.getIntrinsicHeight();
            ObjectAnimator changeSizeAnimator = getChangeSizeAnimator(getDrawableBounds(intrinsicWidth, intrinsicHeight), getDrawableBounds((int) (intrinsicWidth * 1.5f), (int) (intrinsicHeight * 1.5f)));
            changeSizeAnimator.setDuration(250L);
            changeSizeAnimator.setInterpolator(this.mPathInterpolator);
            changeSizeAnimator.start();
        }

        private void restore() {
            AnimatorSet animatorSet = new AnimatorSet();
            ObjectAnimator restoreSizeAnimator = getRestoreSizeAnimator();
            ValueAnimator restorePositionAnimator = getRestorePositionAnimator();
            if (restorePositionAnimator == null) {
                Log.d("Editor", "restorePositionAnimator is null. hide() is called.");
                hide();
            } else {
                animatorSet.playTogether(restoreSizeAnimator, restorePositionAnimator);
                animatorSet.addListener(new AnimatorListenerAdapter() { // from class: android.widget.Editor.HandleView.4
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        HandleView.this.mIsRestoring = false;
                        HandleView handleView = HandleView.this;
                        handleView.positionAtCursorOffset(handleView.getCurrentCursorOffset(), true, false);
                        if (HandleView.this.shouldShow()) {
                            if (HandleView.this.isShowing()) {
                                HandleView.this.requestLayout();
                                HandleView.this.invalidate();
                            }
                        } else if (HandleView.this.isShowing()) {
                            HandleView.this.dismiss();
                        }
                        if (Editor.this.mTextView.getSelectionStart() > Editor.this.mTextView.getSelectionEnd()) {
                            Selection.setSelection((Spannable) Editor.this.mTextView.getText(), Editor.this.mTextView.getSelectionEnd(), Editor.this.mTextView.getSelectionStart());
                        }
                        HandleView.this.updateDrawable(false);
                    }
                });
                animatorSet.start();
            }
        }

        private ObjectAnimator getRestoreSizeAnimator() {
            Rect bounds = this.mDrawable.getBounds();
            ObjectAnimator changeSizeAnimator = getChangeSizeAnimator(getDrawableBounds(bounds.width(), bounds.height()), getDrawableBounds(this.mDrawable.getIntrinsicWidth(), this.mDrawable.getIntrinsicHeight()));
            changeSizeAnimator.setDuration(250L);
            changeSizeAnimator.setInterpolator(this.mPathInterpolator);
            return changeSizeAnimator;
        }

        private ValueAnimator getRestorePositionAnimator() {
            int[] iArr = {this.mPositionX, this.mPositionY};
            int[] iArr2 = new int[2];
            if (Editor.this.mTextView.getLayout() == null) {
                Editor.this.prepareCursorControllers();
                return null;
            }
            Layout activeLayout = Editor.this.getActiveLayout();
            int currentCursorOffset = getCurrentCursorOffset();
            int lineForOffset = activeLayout.getLineForOffset(currentCursorOffset);
            iArr2[0] = ((getCursorHorizontalPosition(activeLayout, currentCursorOffset) - this.mHotspotX) - getHorizontalOffset()) + getCursorOffset() + Editor.this.mTextView.viewportToContentHorizontalOffset();
            iArr2[1] = activeLayout.getLineBottom(lineForOffset) + Editor.this.mTextView.viewportToContentVerticalOffset();
            iArr[0] = iArr[0] + this.mHotspotX + getHorizontalOffset();
            iArr2[0] = iArr2[0] + this.mHotspotX + getHorizontalOffset();
            Editor.this.mTextView.transformFromViewToWindowSpace(iArr);
            Editor.this.mTextView.transformFromViewToWindowSpace(iArr2);
            iArr[0] = iArr[0] - (this.mHotspotX + getHorizontalOffset());
            int horizontalOffset = iArr2[0] - (this.mHotspotX + getHorizontalOffset());
            iArr2[0] = horizontalOffset;
            ValueAnimator ofPropertyValuesHolder = ValueAnimator.ofPropertyValuesHolder(PropertyValuesHolder.ofInt("x", iArr[0], horizontalOffset), PropertyValuesHolder.ofInt("y", iArr[1], iArr2[1]));
            ofPropertyValuesHolder.setDuration(250L);
            ofPropertyValuesHolder.setInterpolator(this.mPathInterpolator);
            ofPropertyValuesHolder.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: android.widget.Editor.HandleView.5
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int intValue = ((Integer) valueAnimator.getAnimatedValue("x")).intValue();
                    int intValue2 = ((Integer) valueAnimator.getAnimatedValue("y")).intValue();
                    if (HandleView.this.isShowing()) {
                        HandleView.this.invalidate();
                        HandleView.this.mContainer.update(intValue, intValue2, -1, -1);
                    }
                }
            });
            return ofPropertyValuesHolder;
        }

        private ObjectAnimator getShowAnimator() {
            final int intrinsicWidth = this.mDrawable.getIntrinsicWidth();
            final int intrinsicHeight = this.mDrawable.getIntrinsicHeight();
            ObjectAnimator ofObject = ObjectAnimator.ofObject(this.mDrawable, "bounds", this.CHANGE_SIZE_EVALUATOR, getDrawableBounds(0, 0), getDrawableBounds(intrinsicWidth, intrinsicHeight));
            ofObject.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: android.widget.Editor.HandleView.6
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    if (HandleView.this.mIsShowAnimating) {
                        HandleView.this.invalidate();
                    }
                }
            });
            ofObject.addListener(new AnimatorListenerAdapter() { // from class: android.widget.Editor.HandleView.7
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator) {
                    if (HandleView.this.mIsHideAnimating) {
                        HandleView.this.mHideAnimator.cancel();
                        HandleView.this.mIsHideAnimating = false;
                    }
                    HandleView handleView = HandleView.this;
                    handleView.positionAtCursorOffset(handleView.getCurrentCursorOffset(), true, false);
                    int[] iArr = {HandleView.this.mPositionX + HandleView.this.mHotspotX + HandleView.this.getHorizontalOffset(), HandleView.this.mPositionY};
                    Editor.this.mTextView.transformFromViewToWindowSpace(iArr);
                    iArr[0] = iArr[0] - (HandleView.this.mHotspotX + HandleView.this.getHorizontalOffset());
                    HandleView.this.mContainer.update(iArr[0], iArr[1], -1, -1);
                    HandleView.this.mIsShowAnimating = true;
                    HandleView.this.setLayerType(1, null);
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    if (HandleView.this.mIsShowAnimating) {
                        HandleView.this.mDrawable.setBounds(HandleView.this.getDrawableBounds(intrinsicWidth, intrinsicHeight));
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
                }
            });
            ofObject.setDuration(200L);
            ofObject.setInterpolator(this.mPathInterpolator);
            return ofObject;
        }

        private ObjectAnimator getHideAnimator() {
            Rect bounds = this.mDrawable.getBounds();
            ObjectAnimator ofObject = ObjectAnimator.ofObject(this.mDrawable, "bounds", this.CHANGE_SIZE_EVALUATOR, getDrawableBounds(bounds.width(), bounds.height()), new Rect(0, 0, 0, 0));
            ofObject.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: android.widget.Editor.HandleView.8
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    if (HandleView.this.mIsHideAnimating) {
                        HandleView.this.invalidate();
                    }
                }
            });
            ofObject.addListener(new AnimatorListenerAdapter() { // from class: android.widget.Editor.HandleView.9
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator) {
                    if (HandleView.this.mIsShowAnimating) {
                        HandleView.this.mShowAnimator.cancel();
                        HandleView.this.mIsShowAnimating = false;
                    }
                    HandleView.this.mIsHideAnimating = true;
                    HandleView.this.setLayerType(1, null);
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    if (HandleView.this.mIsHideAnimating) {
                        HandleView.this.setLayerType(0, null);
                        HandleView.this.mContainer.dismiss();
                        HandleView.this.mIsHideAnimating = false;
                        HandleView.this.mHideAnimator = null;
                    }
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animator) {
                    HandleView.this.setLayerType(0, null);
                    HandleView.this.mIsHideAnimating = false;
                    HandleView.this.mHideAnimator = null;
                }
            });
            ofObject.setDuration(100L);
            ofObject.setInterpolator(this.mPathInterpolator);
            return ofObject;
        }

        private boolean isValid() {
            return Editor.this.mTextView.getApplicationWindowToken() != null && Editor.this.mTextView.getWindowToken() != null && Editor.this.mTextView.getApplicationWindowToken() == Editor.this.mTextView.getWindowToken() && Editor.this.mTextView.hasFocus();
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

        @Override // android.widget.Editor.HandleView
        protected int getHorizontalGravity(boolean z) {
            return 1;
        }

        @Override // android.widget.Editor.HandleView
        protected int getMagnifierHandleTrigger() {
            return 0;
        }

        InsertionHandleView(Drawable drawable) {
            super(drawable, drawable, R.id.insertion_handle);
            int i = 0;
            this.mIsTouchDown = false;
            this.mPendingDismissOnUp = false;
            this.mShouldMagnifierCursorAdjust = false;
            int i2 = 255;
            if (Editor.this.mFlagInsertionHandleGesturesEnabled) {
                i = AppGlobals.getIntCoreSetting(WidgetFlags.KEY_INSERTION_HANDLE_DELTA_HEIGHT, 25);
                int i3 = 50;
                int intCoreSetting = AppGlobals.getIntCoreSetting(WidgetFlags.KEY_INSERTION_HANDLE_OPACITY, 50);
                i = (i < -25 || i > 50) ? 25 : i;
                if (intCoreSetting >= 10 && intCoreSetting <= 100) {
                    i3 = intCoreSetting;
                }
                i2 = (i3 * 255) / 100;
            }
            this.mDeltaHeight = i;
            this.mDrawableOpacity = i2;
        }

        @Override // android.widget.Editor.HandleView
        protected void hideAfterDelay() {
            if (this.mHider == null) {
                this.mHider = new Runnable() { // from class: android.widget.Editor.InsertionHandleView.1
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
        protected int getHotspotX(Drawable drawable, boolean z) {
            return drawable.getIntrinsicWidth() / 2;
        }

        @Override // android.widget.Editor.HandleView
        protected int getCursorOffset() {
            int cursorOffset = super.getCursorOffset();
            if (Editor.this.mDrawableForCursor == null) {
                return cursorOffset;
            }
            Editor.this.mDrawableForCursor.getPadding(Editor.this.mTempRect);
            return cursorOffset + (((Editor.this.mDrawableForCursor.getIntrinsicWidth() - Editor.this.mTempRect.left) - Editor.this.mTempRect.right) / 2);
        }

        @Override // android.widget.Editor.HandleView
        int getCursorHorizontalPosition(Layout layout, int i) {
            if (Editor.this.mDrawableForCursor != null) {
                float horizontal = getHorizontal(layout, i);
                Editor editor = Editor.this;
                return editor.clampHorizontalPosition(editor.mDrawableForCursor, horizontal) + Editor.this.mTempRect.left;
            }
            return super.getCursorHorizontalPosition(layout, i);
        }

        @Override // android.widget.Editor.HandleView, android.view.View
        protected void onMeasure(int i, int i2) {
            if (Editor.this.mFlagInsertionHandleGesturesEnabled) {
                setMeasuredDimension(getPreferredWidth(), Math.max(getPreferredHeight() + this.mDeltaHeight, this.mDrawable.getIntrinsicHeight()));
            } else {
                super.onMeasure(i, i2);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean shouldMagnifierCursorAdjust() {
            return this.mShouldMagnifierCursorAdjust && Editor.this.mMagnifierAnimator != null && Editor.this.mMagnifierAnimator.mMagnifierIsShowing;
        }

        @Override // android.widget.Editor.HandleView, android.view.View
        public boolean onTouchEvent(MotionEvent motionEvent) {
            if (!Editor.this.mTextView.isFromPrimePointer(motionEvent, true)) {
                return true;
            }
            if (Editor.this.mFlagInsertionHandleGesturesEnabled && Editor.this.mFlagCursorDragFromAnywhereEnabled) {
                return touchThrough(motionEvent);
            }
            boolean onTouchEvent = super.onTouchEvent(motionEvent);
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked == 0) {
                this.mLastDownRawX = motionEvent.getRawX();
                this.mLastDownRawY = motionEvent.getRawY();
                if (Editor.this.mIsThemeDeviceDefault) {
                    removeHiderCallback();
                }
                updateMagnifier(motionEvent);
                this.mShouldMagnifierCursorAdjust = true;
                Editor.this.updateCursorPosition();
                return onTouchEvent;
            }
            if (actionMasked != 1) {
                if (actionMasked == 2) {
                    if (!Editor.this.mIsMagnifierHideByVelocityTracker) {
                        updateMagnifier(motionEvent);
                    } else {
                        dismissMagnifier();
                    }
                    Editor.this.updateCursorPosition();
                    return onTouchEvent;
                }
                if (actionMasked != 3) {
                    return onTouchEvent;
                }
                hideAfterDelay();
                dismissMagnifier();
                this.mShouldMagnifierCursorAdjust = false;
                Editor.this.updateCursorPosition();
                return onTouchEvent;
            }
            if (!offsetHasBeenChanged()) {
                if (EditorTouchState.isDistanceWithin(this.mLastDownRawX, this.mLastDownRawY, motionEvent.getRawX(), motionEvent.getRawY(), ViewConfiguration.get(Editor.this.mTextView.getContext()).getScaledTouchSlop())) {
                    Editor.this.toggleInsertionActionMode();
                }
            } else if (Editor.this.mTextActionMode != null) {
                Editor.this.mTextActionMode.invalidateContentRect();
            }
            if (Editor.this.mTextActionMode != null) {
                removeHiderCallback();
            } else {
                hideAfterDelay();
            }
            dismissMagnifier();
            this.mShouldMagnifierCursorAdjust = false;
            Editor.this.updateCursorPosition();
            return onTouchEvent;
        }

        private boolean touchThrough(MotionEvent motionEvent) {
            int selectionStart;
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked == 0) {
                this.mIsTouchDown = true;
                this.mOffsetChanged = false;
                this.mOffsetDown = Editor.this.mTextView.getSelectionStart();
                this.mTouchDownX = motionEvent.getX();
                this.mTouchDownY = motionEvent.getY();
                this.mIsInActionMode = Editor.this.mTextActionMode != null;
                if (motionEvent.getEventTime() - this.mLastUpTime < ViewConfiguration.getDoubleTapTimeout()) {
                    Editor.this.lambda$startActionModeInternal$0();
                }
                Editor.this.mTouchState.setIsOnHandle(true);
            } else if (actionMasked == 1) {
                this.mLastUpTime = motionEvent.getEventTime();
            }
            boolean onTouchEvent = Editor.this.mTextView.onTouchEvent(transformEventForTouchThrough(motionEvent));
            if (actionMasked == 1 || actionMasked == 3) {
                this.mIsTouchDown = false;
                if (this.mPendingDismissOnUp) {
                    dismiss();
                }
                Editor.this.mTouchState.setIsOnHandle(false);
            }
            if (!this.mOffsetChanged && ((selectionStart = Editor.this.mTextView.getSelectionStart()) != Editor.this.mTextView.getSelectionEnd() || this.mOffsetDown != selectionStart)) {
                this.mOffsetChanged = true;
            }
            if (!this.mOffsetChanged && actionMasked == 1) {
                if (this.mIsInActionMode) {
                    Editor.this.lambda$startActionModeInternal$0();
                    return onTouchEvent;
                }
                Editor.this.startInsertionActionMode();
            }
            return onTouchEvent;
        }

        private MotionEvent transformEventForTouchThrough(MotionEvent motionEvent) {
            Layout layout = Editor.this.mTextView.getLayout();
            int lineForOffset = getLineForOffset(layout, getCurrentCursorOffset());
            int lineBottom = layout.getLineBottom(lineForOffset, false) - layout.getLineTop(lineForOffset);
            Matrix matrix = new Matrix();
            matrix.setTranslate(((motionEvent.getRawX() - motionEvent.getX()) + (getMeasuredWidth() >> 1)) - this.mTouchDownX, ((motionEvent.getRawY() - motionEvent.getY()) - (lineBottom >> 1)) - this.mTouchDownY);
            motionEvent.transform(matrix);
            Editor.this.mTextView.toLocalMotionEvent(motionEvent);
            return motionEvent;
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
        protected void updateDrawable(boolean z) {
            super.updateDrawable(z);
            this.mDrawable.setAlpha(this.mDrawableOpacity);
        }

        @Override // android.widget.Editor.HandleView
        public int getCurrentCursorOffset() {
            return Editor.this.mTextView.getSelectionStart();
        }

        @Override // android.widget.Editor.HandleView
        public void updateSelection(int i) {
            Selection.setSelection((Spannable) Editor.this.mTextView.getText(), i);
        }

        @Override // android.widget.Editor.HandleView
        protected void updatePosition(float f, float f2, boolean z) {
            float f3 = (f2 - this.mLastParentYOnScreen) + this.mFirstParentY;
            Layout layout = Editor.this.mTextView.getLayout();
            int i = -1;
            if (layout != null) {
                if (this.mPreviousLineTouched == -1) {
                    this.mPreviousLineTouched = Editor.this.mTextView.getLineAtCoordinate(f3);
                }
                int currentLineAdjustedForSlop = Editor.this.getCurrentLineAdjustedForSlop(layout, this.mPreviousLineTouched, f3);
                i = getOffsetAtCoordinate(layout, currentLineAdjustedForSlop, f);
                int lineBottom = layout.getLineBottom(currentLineAdjustedForSlop);
                int lineBottom2 = (lineBottom - layout.getLineBottom(this.mPreviousLineTouched)) - (Editor.this.mTextView.getVerticalOffset(true) + Editor.this.mTextView.getCompoundPaddingTop());
                this.mPreviousLineTouched = currentLineAdjustedForSlop;
                updatePositionDuringDragging((int) ((((((f + this.mTouchToWindowOffsetX) - this.mHotspotX) - getHorizontalOffset()) - this.mHorizontalOffset) + this.mLastParentXOnScreen) - this.mLastParentX), this.mIsVerticalScrolled ? lineBottom - lineBottom2 : (int) ((((f2 + this.mTouchToWindowOffsetY) - this.mTouchOffsetY) - this.mVerticalScrolledYOffset) - this.mVerticalOffset));
            }
            positionAtCursorOffset(i, false, z);
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
    }

    public final class SelectionHandleView extends HandleView {
        private final int mHandleType;
        private boolean mInWord;
        private boolean mLanguageDirectionChanged;
        private float mPrevX;
        private final float mTextViewEdgeSlop;
        private final int[] mTextViewLocation;
        private float mTouchWordDelta;

        public SelectionHandleView(Drawable drawable, Drawable drawable2, int i, int i2) {
            super(drawable, drawable2, i);
            this.mInWord = false;
            this.mLanguageDirectionChanged = false;
            this.mTextViewLocation = new int[2];
            this.mHandleType = i2;
            this.mTextViewEdgeSlop = ViewConfiguration.get(Editor.this.mTextView.getContext()).getScaledTouchSlop() * 4;
        }

        private boolean isStartHandle() {
            return this.mHandleType == 0;
        }

        @Override // android.widget.Editor.HandleView
        protected int getHotspotX(Drawable drawable, boolean z) {
            if (z == isStartHandle()) {
                return drawable.getIntrinsicWidth() / 4;
            }
            return (drawable.getIntrinsicWidth() * 3) / 4;
        }

        @Override // android.widget.Editor.HandleView
        protected int getHorizontalGravity(boolean z) {
            return z == isStartHandle() ? 3 : 5;
        }

        @Override // android.widget.Editor.HandleView
        public int getCurrentCursorOffset() {
            boolean isStartHandle = isStartHandle();
            TextView textView = Editor.this.mTextView;
            return isStartHandle ? textView.getSelectionStart() : textView.getSelectionEnd();
        }

        @Override // android.widget.Editor.HandleView
        protected void updateSelection(int i) {
            if (isStartHandle()) {
                Selection.setSelection((Spannable) Editor.this.mTextView.getText(), i, Editor.this.mTextView.getSelectionEnd());
            } else {
                Selection.setSelection((Spannable) Editor.this.mTextView.getText(), Editor.this.mTextView.getSelectionStart(), i);
            }
            updateDrawable(false);
            if (Editor.this.mTextActionMode != null) {
                Editor.this.invalidateActionMode();
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:58:0x013e, code lost:
        
            if (r12.this$0.mTextView.canScrollHorizontally(r9 ? -1 : 1) != false) goto L52;
         */
        @Override // android.widget.Editor.HandleView
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        protected void updatePosition(float r13, float r14, boolean r15) {
            /*
                Method dump skipped, instructions count: 377
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: android.widget.Editor.SelectionHandleView.updatePosition(float, float, boolean):void");
        }

        @Override // android.widget.Editor.HandleView
        protected void positionAtCursorOffset(int i, boolean z, boolean z2) {
            super.positionAtCursorOffset(i, z, z2);
            this.mInWord = (i == -1 || Editor.this.getWordIteratorWithText().isBoundary(i)) ? false : true;
        }

        @Override // android.widget.Editor.HandleView, android.view.View
        public boolean onTouchEvent(MotionEvent motionEvent) {
            if (!Editor.this.mTextView.isFromPrimePointer(motionEvent, true)) {
                return true;
            }
            boolean onTouchEvent = super.onTouchEvent(motionEvent);
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked == 0) {
                this.mTouchWordDelta = 0.0f;
                this.mPrevX = -1.0f;
                updateMagnifier(motionEvent);
                return onTouchEvent;
            }
            if (actionMasked != 1) {
                if (actionMasked == 2) {
                    if (!Editor.this.mIsMagnifierHideByVelocityTracker) {
                        updateMagnifier(motionEvent);
                        return onTouchEvent;
                    }
                    dismissMagnifier();
                    return onTouchEvent;
                }
                if (actionMasked != 3) {
                    return onTouchEvent;
                }
            }
            dismissMagnifier();
            return onTouchEvent;
        }

        private void positionAndAdjustForCrossingHandles(int i, boolean z) {
            int selectionEnd = isStartHandle() ? Editor.this.mTextView.getSelectionEnd() : Editor.this.mTextView.getSelectionStart();
            if (((isStartHandle() && i >= selectionEnd) || (!isStartHandle() && i <= selectionEnd)) && Editor.this.mTextView.getLayout() != null && i == selectionEnd) {
                i = Editor.this.getNextCursorOffset(selectionEnd, !isStartHandle());
            }
            positionAtCursorOffset(i, false, z);
        }

        private boolean positionNearEdgeOfScrollingView(float f, boolean z) {
            Editor.this.mTextView.getLocationOnScreen(this.mTextViewLocation);
            return z == isStartHandle() ? f > ((float) ((this.mTextViewLocation[0] + Editor.this.mTextView.getWidth()) - Editor.this.mTextView.getPaddingRight())) - this.mTextViewEdgeSlop : f < ((float) (this.mTextViewLocation[0] + Editor.this.mTextView.getPaddingLeft())) + this.mTextViewEdgeSlop;
        }

        @Override // android.widget.Editor.HandleView
        protected boolean isAtRtlRun(Layout layout, int i) {
            int transformedToOriginal = Editor.this.mTextView.transformedToOriginal(i, 0);
            if (isStartHandle() != (Editor.this.mTextView.getSelectionStart() < Editor.this.mTextView.getSelectionEnd())) {
                transformedToOriginal = Math.max(transformedToOriginal - 1, 0);
            }
            return layout.isRtlCharAt(transformedToOriginal);
        }

        @Override // android.widget.Editor.HandleView
        public float getHorizontal(Layout layout, int i) {
            return getHorizontal(layout, i, isStartHandle());
        }

        private float getHorizontal(Layout layout, int i, boolean z) {
            int originalToTransformed = Editor.this.mTextView.originalToTransformed(i, 1);
            if (layout.isRtlCharAt(z ? originalToTransformed : Math.max(originalToTransformed - 1, 0)) != (layout.getParagraphDirection(layout.getLineForOffset(originalToTransformed)) == -1)) {
                return layout.getSecondaryHorizontal(originalToTransformed);
            }
            return layout.getPrimaryHorizontal(originalToTransformed);
        }

        /* JADX WARN: Code restructure failed: missing block: B:16:0x005f, code lost:
        
            if (r7.isRtlCharAt(r3) == (r7.getParagraphDirection(r8) == -1)) goto L19;
         */
        @Override // android.widget.Editor.HandleView
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        protected int getOffsetAtCoordinate(android.text.Layout r7, int r8, float r9) {
            /*
                r6 = this;
                android.widget.Editor r0 = android.widget.Editor.this
                android.widget.TextView r0 = android.widget.Editor.m6810$$Nest$fgetmTextView(r0)
                float r9 = r0.convertToLocalHorizontalCoordinate(r9)
                r0 = 1
                int r1 = r7.getOffsetForHorizontal(r8, r9, r0)
                boolean r2 = r7.isLevelBoundary(r1)
                if (r2 != 0) goto L20
                android.widget.Editor r6 = android.widget.Editor.this
                android.widget.TextView r6 = android.widget.Editor.m6810$$Nest$fgetmTextView(r6)
                int r6 = r6.transformedToOriginal(r1, r0)
                return r6
            L20:
                r2 = 0
                int r9 = r7.getOffsetForHorizontal(r8, r9, r2)
                android.widget.Editor r3 = android.widget.Editor.this
                android.widget.TextView r3 = android.widget.Editor.m6810$$Nest$fgetmTextView(r3)
                int r4 = r6.getCurrentCursorOffset()
                int r3 = r3.originalToTransformed(r4, r0)
                int r4 = r1 - r3
                int r4 = java.lang.Math.abs(r4)
                int r5 = r9 - r3
                int r5 = java.lang.Math.abs(r5)
                if (r4 >= r5) goto L42
                goto L61
            L42:
                if (r4 <= r5) goto L46
            L44:
                r1 = r9
                goto L61
            L46:
                boolean r4 = r6.isStartHandle()
                if (r4 == 0) goto L4d
                goto L53
            L4d:
                int r3 = r3 + (-1)
                int r3 = java.lang.Math.max(r3, r2)
            L53:
                boolean r3 = r7.isRtlCharAt(r3)
                int r7 = r7.getParagraphDirection(r8)
                r8 = -1
                if (r7 != r8) goto L5f
                r2 = r0
            L5f:
                if (r3 != r2) goto L44
            L61:
                android.widget.Editor r6 = android.widget.Editor.this
                android.widget.TextView r6 = android.widget.Editor.m6810$$Nest$fgetmTextView(r6)
                int r6 = r6.transformedToOriginal(r1, r0)
                return r6
            */
            throw new UnsupportedOperationException("Method not decompiled: android.widget.Editor.SelectionHandleView.getOffsetAtCoordinate(android.text.Layout, int, float):int");
        }

        @Override // android.widget.Editor.HandleView
        protected int getMagnifierHandleTrigger() {
            return isStartHandle() ? 1 : 2;
        }

        @Override // android.widget.Editor.HandleView
        protected boolean isScreenOut(int i, boolean z) {
            int i2;
            int right = Editor.this.mTextView.getRootView().getRight();
            int intrinsicWidth = this.mDrawableLtr.getIntrinsicWidth() / 2;
            if (isStartHandle() == z) {
                i2 = intrinsicWidth + i;
            } else {
                int i3 = i - intrinsicWidth;
                i2 = i;
                i = i3;
            }
            return i < 0 || i2 < 0 || i > right || i2 > right;
        }
    }

    public void setLineChangeSlopMinMaxForTesting(int i, int i2) {
        this.mLineChangeSlopMin = i;
        this.mLineChangeSlopMax = i2;
    }

    public int getCurrentLineAdjustedForSlop(Layout layout, int i, float f) {
        int lineAtCoordinate = this.mTextView.getLineAtCoordinate(f);
        if (layout != null && i < layout.getLineCount() && layout.getLineCount() > 0 && i >= 0 && Math.abs(lineAtCoordinate - i) < 2) {
            int lineHeight = this.mTextView.getLineHeight();
            int max = Math.max(0, Math.max(this.mLineChangeSlopMin, Math.min(this.mLineChangeSlopMax, ((int) (this.mLineSlopRatio * lineHeight)) + lineHeight)) - lineHeight);
            float viewportToContentVerticalOffset = this.mTextView.viewportToContentVerticalOffset();
            if ((lineAtCoordinate <= i || f < layout.getLineBottom(i) + max + viewportToContentVerticalOffset) && (lineAtCoordinate >= i || f > (layout.getLineTop(i) - max) + viewportToContentVerticalOffset)) {
                return i;
            }
        }
        return lineAtCoordinate;
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

        public InsertionPointCursorController() {
        }

        public void onTouchEvent(MotionEvent motionEvent) {
            if (Editor.this.hasSelectionController() && Editor.this.getSelectionController().isCursorBeingModified()) {
                return;
            }
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked != 1) {
                if (actionMasked == 2) {
                    if (motionEvent.isFromSource(8194)) {
                        return;
                    }
                    if (Editor.this.mTextView.isAutoHandwritingEnabled() && isFromStylus(motionEvent)) {
                        return;
                    }
                    if (this.mIsDraggingCursor) {
                        performCursorDrag(motionEvent);
                        return;
                    }
                    if (Editor.this.mFlagCursorDragFromAnywhereEnabled && Editor.this.mTextView.getLayout() != null && Editor.this.mTextView.isFocused() && Editor.this.mTouchState.isMovedEnoughForDrag()) {
                        if (Editor.this.mTouchState.getInitialDragDirectionXYRatio() > Editor.this.mCursorDragDirectionMinXYRatio || Editor.this.mTouchState.isOnHandle()) {
                            startCursorDrag(motionEvent);
                            return;
                        }
                        return;
                    }
                    return;
                }
                if (actionMasked != 3) {
                    return;
                }
            }
            if (this.mIsDraggingCursor) {
                endCursorDrag(motionEvent);
            }
        }

        private boolean isFromStylus(MotionEvent motionEvent) {
            return motionEvent.getToolType(motionEvent.getActionIndex()) == 2;
        }

        private void positionCursorDuringDrag(MotionEvent motionEvent) {
            this.mPrevLineDuringDrag = getLineDuringDrag(motionEvent);
            int offsetAtCoordinate = Editor.this.mTextView.getOffsetAtCoordinate(this.mPrevLineDuringDrag, motionEvent.getX());
            int selectionStart = Editor.this.mTextView.getSelectionStart();
            int selectionEnd = Editor.this.mTextView.getSelectionEnd();
            if (offsetAtCoordinate == selectionStart && offsetAtCoordinate == selectionEnd) {
                return;
            }
            Selection.setSelection((Spannable) Editor.this.mTextView.getText(), offsetAtCoordinate);
            Editor.this.updateCursorPosition();
            if (Editor.this.mHapticTextHandleEnabled) {
                Editor.this.mTextView.performHapticFeedback(9);
            }
        }

        private int getLineDuringDrag(MotionEvent motionEvent) {
            float y;
            Layout layout = Editor.this.mTextView.getLayout();
            int i = this.mPrevLineDuringDrag;
            if (i == -1) {
                return Editor.this.getCurrentLineAdjustedForSlop(layout, i, motionEvent.getY());
            }
            if (Editor.this.mTouchState.isOnHandle()) {
                y = motionEvent.getRawY() - Editor.this.mTextView.getLocationOnScreen()[1];
            } else {
                y = motionEvent.getY();
            }
            int currentLineAdjustedForSlop = Editor.this.getCurrentLineAdjustedForSlop(layout, this.mPrevLineDuringDrag, y - getHandle().getIdealFingerToCursorOffset());
            if (this.mIsTouchSnappedToHandleDuringDrag) {
                return currentLineAdjustedForSlop;
            }
            int i2 = this.mPrevLineDuringDrag;
            if (currentLineAdjustedForSlop < i2) {
                return Math.min(i2, Editor.this.getCurrentLineAdjustedForSlop(layout, i2, y));
            }
            this.mIsTouchSnappedToHandleDuringDrag = true;
            return currentLineAdjustedForSlop;
        }

        private void startCursorDrag(MotionEvent motionEvent) {
            this.mIsDraggingCursor = true;
            this.mIsTouchSnappedToHandleDuringDrag = false;
            this.mPrevLineDuringDrag = -1;
            Editor.this.mTextView.getParent().requestDisallowInterceptTouchEvent(true);
            Editor.this.mTextView.cancelLongPress();
            positionCursorDuringDrag(motionEvent);
            show();
            getHandle().removeHiderCallback();
            getHandle().updateMagnifier(motionEvent);
        }

        private void performCursorDrag(MotionEvent motionEvent) {
            positionCursorDuringDrag(motionEvent);
            getHandle().updateMagnifier(motionEvent);
        }

        private void endCursorDrag(MotionEvent motionEvent) {
            this.mIsDraggingCursor = false;
            this.mIsTouchSnappedToHandleDuringDrag = false;
            this.mPrevLineDuringDrag = -1;
            getHandle().dismissMagnifier();
            getHandle().hideAfterDelay();
            Editor.this.mTextView.getParent().requestDisallowInterceptTouchEvent(false);
        }

        @Override // android.widget.Editor.CursorController
        public void show() {
            if ((Editor.this.mUseCtxMenuInDesktopMode && Editor.this.mTextView.isDesktopMode()) || Editor.this.isUniversalSwitchEnable()) {
                Log.e("Editor", "Action mode didn't start because Universal Switch / Desktop mode was enabled");
                return;
            }
            getHandle().removeHiderCallback();
            getHandle().show();
            long uptimeMillis = SystemClock.uptimeMillis() - TextView.sLastCutCopyOrTextChangedTime;
            if (Editor.this.mInsertionActionModeRunnable != null && (this.mIsDraggingCursor || Editor.this.mTouchState.isMultiTap() || Editor.this.isCursorInsideEasyCorrectionSpan())) {
                Editor.this.mTextView.removeCallbacks(Editor.this.mInsertionActionModeRunnable);
            }
            if (!this.mIsDraggingCursor && !Editor.this.mTouchState.isMultiTap() && !Editor.this.isCursorInsideEasyCorrectionSpan() && uptimeMillis < 15000 && Editor.this.mTextActionMode == null) {
                if (Editor.this.mInsertionActionModeRunnable == null) {
                    Editor.this.mInsertionActionModeRunnable = new Runnable() { // from class: android.widget.Editor.InsertionPointCursorController.1
                        @Override // java.lang.Runnable
                        public void run() {
                            Editor.this.startInsertionActionMode();
                        }
                    };
                }
                Editor.this.mTextView.postDelayed(Editor.this.mInsertionActionModeRunnable, ViewConfiguration.getDoubleTapTimeout() + 1);
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
            InsertionHandleView insertionHandleView = this.mHandle;
            if (insertionHandleView != null) {
                insertionHandleView.hide();
            }
        }

        @Override // android.view.ViewTreeObserver.OnTouchModeChangeListener
        public void onTouchModeChanged(boolean z) {
            if (z) {
                return;
            }
            hide();
        }

        public InsertionHandleView getHandle() {
            if (this.mHandle == null) {
                Editor.this.loadHandleDrawables(false);
                Editor editor = Editor.this;
                this.mHandle = editor.new InsertionHandleView(editor.mSelectHandleCenter);
            }
            return this.mHandle;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void reloadHandleDrawable() {
            InsertionHandleView insertionHandleView = this.mHandle;
            if (insertionHandleView == null) {
                return;
            }
            insertionHandleView.setDrawables(Editor.this.mSelectHandleCenter, Editor.this.mSelectHandleCenter);
        }

        @Override // android.widget.Editor.CursorController
        public void onDetached() {
            Editor.this.mTextView.getViewTreeObserver().removeOnTouchModeChangeListener(this);
            InsertionHandleView insertionHandleView = this.mHandle;
            if (insertionHandleView != null) {
                insertionHandleView.onDetached();
            }
        }

        @Override // android.widget.Editor.CursorController
        public boolean isCursorBeingModified() {
            if (this.mIsDraggingCursor) {
                return true;
            }
            InsertionHandleView insertionHandleView = this.mHandle;
            return insertionHandleView != null && insertionHandleView.isDragging();
        }

        @Override // android.widget.Editor.CursorController
        public boolean isActive() {
            InsertionHandleView insertionHandleView = this.mHandle;
            return insertionHandleView != null && insertionHandleView.isShowing();
        }

        public void invalidateHandle() {
            InsertionHandleView insertionHandleView = this.mHandle;
            if (insertionHandleView != null) {
                insertionHandleView.invalidate();
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
                Editor editor = Editor.this;
                this.mStartHandle = editor.new SelectionHandleView(editor.mSelectHandleLeft, Editor.this.mSelectHandleRight, R.id.selection_start_handle, 0);
            }
            if (this.mEndHandle == null) {
                Editor editor2 = Editor.this;
                this.mEndHandle = editor2.new SelectionHandleView(editor2.mSelectHandleRight, Editor.this.mSelectHandleLeft, R.id.selection_end_handle, 1);
            }
            this.mStartHandle.show();
            this.mEndHandle.show();
            Editor.this.hideInsertionPointCursorController();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void reloadHandleDrawables() {
            SelectionHandleView selectionHandleView = this.mStartHandle;
            if (selectionHandleView == null) {
                return;
            }
            selectionHandleView.setDrawables(Editor.this.mSelectHandleLeft, Editor.this.mSelectHandleRight);
            this.mEndHandle.setDrawables(Editor.this.mSelectHandleRight, Editor.this.mSelectHandleLeft);
        }

        @Override // android.widget.Editor.CursorController
        public void hide() {
            SelectionHandleView selectionHandleView = this.mStartHandle;
            if (selectionHandleView != null) {
                selectionHandleView.hide();
            }
            SelectionHandleView selectionHandleView2 = this.mEndHandle;
            if (selectionHandleView2 != null) {
                selectionHandleView2.hide();
            }
        }

        public void enterDrag(int i) {
            show();
            this.mDragAcceleratorMode = i;
            this.mStartOffset = Editor.this.mTextView.getOffsetForPosition(Editor.this.mTouchState.getLastDownX(), Editor.this.mTouchState.getLastDownY());
            this.mLineSelectionIsOn = Editor.this.mTextView.getLineAtCoordinate(Editor.this.mTouchState.getLastDownY());
            hide();
            Editor.this.mTextView.getParent().requestDisallowInterceptTouchEvent(true);
            Editor.this.mTextView.cancelLongPress();
        }

        public void onTouchEvent(MotionEvent motionEvent) {
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            boolean isFromSource = motionEvent.isFromSource(8194);
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked == 0) {
                if (Editor.this.extractedTextModeWillBeStarted()) {
                    hide();
                } else {
                    int offsetForPosition = Editor.this.mTextView.getOffsetForPosition(x, y);
                    this.mMaxTouchOffset = offsetForPosition;
                    this.mMinTouchOffset = offsetForPosition;
                    this.mPrevDownTouchOffset = offsetForPosition;
                    this.mPrevTouchOffset = offsetForPosition;
                    this.mPrevTouchWordEnd = Editor.this.getWordEnd(offsetForPosition);
                    this.mPrevTouchWordStart = Editor.this.getWordStart(this.mPrevTouchOffset);
                    if (this.mGestureStayedInTapRegion && Editor.this.mTouchState.isMultiTapInSameArea() && !Editor.mDisableDoubleTapTextSelection && (isFromSource || Editor.this.isPositionOnText(x, y) || Editor.this.mTouchState.isOnHandle())) {
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
                        this.mStartOffset = Editor.this.mTextView.getOffsetForPosition(Editor.this.mTouchState.getLastDownX(), Editor.this.mTouchState.getLastDownY());
                    }
                }
                VelocityTracker velocityTracker = this.mSelectionVelocityTracker;
                if (velocityTracker == null) {
                    this.mSelectionVelocityTracker = VelocityTracker.obtain();
                } else {
                    velocityTracker.clear();
                }
                this.mSelectionVelocityTracker.addMovement(motionEvent);
                this.mSelectionVelocityTracker.computeCurrentVelocity(1);
                return;
            }
            if (actionMasked == 1) {
                SelectionHandleView selectionHandleView = this.mEndHandle;
                if (selectionHandleView != null) {
                    selectionHandleView.dismissMagnifier();
                }
                if (!isDragAcceleratorActive()) {
                    return;
                }
                updateSelection(motionEvent);
                this.mIsExpanded = false;
                Editor.this.mTextView.getParent().requestDisallowInterceptTouchEvent(false);
                resetDragAcceleratorState();
                if (Editor.this.mTextView.hasSelection()) {
                    Editor.this.startSelectionActionModeAsync(this.mHaventMovedEnoughToStartDrag);
                    Editor.this.updateWritingToolkit();
                }
            } else {
                if (actionMasked == 2) {
                    if (this.mGestureStayedInTapRegion) {
                        this.mGestureStayedInTapRegion = EditorTouchState.isDistanceWithin(Editor.this.mTouchState.getLastDownX(), Editor.this.mTouchState.getLastDownY(), x, y, ViewConfiguration.get(Editor.this.mTextView.getContext()).getScaledDoubleTapTouchSlop());
                    }
                    if (this.mHaventMovedEnoughToStartDrag) {
                        this.mHaventMovedEnoughToStartDrag = !Editor.this.mTouchState.isMovedEnoughForDrag();
                    }
                    if (isFromSource && !isDragAcceleratorActive()) {
                        int offsetForPosition2 = Editor.this.mTextView.getOffsetForPosition(x, y);
                        if (Editor.this.mTextView.hasSelection() && ((!this.mHaventMovedEnoughToStartDrag || this.mStartOffset != offsetForPosition2) && offsetForPosition2 >= Editor.this.mTextView.getSelectionStart() && offsetForPosition2 <= Editor.this.mTextView.getSelectionEnd())) {
                            Editor.this.startDragAndDrop();
                            return;
                        } else if (this.mStartOffset != offsetForPosition2) {
                            Editor.this.lambda$startActionModeInternal$0();
                            enterDrag(1);
                            Editor.this.mDiscardNextActionUp = true;
                            this.mHaventMovedEnoughToStartDrag = false;
                        }
                    }
                    SelectionHandleView selectionHandleView2 = this.mStartHandle;
                    if (selectionHandleView2 == null || !selectionHandleView2.isShowing()) {
                        VelocityTracker velocityTracker2 = this.mSelectionVelocityTracker;
                        if (velocityTracker2 != null) {
                            velocityTracker2.addMovement(motionEvent);
                            this.mSelectionVelocityTracker.computeCurrentVelocity(1);
                            Editor.this.mIsMagnifierHideByVelocityTracker = this.mSelectionVelocityTracker.getYVelocity() > 0.5f || this.mSelectionVelocityTracker.getYVelocity() < -0.5f;
                        }
                        if (Editor.this.mShowMagnifier && !Editor.this.mIsMagnifierHideByVelocityTracker) {
                            Editor.this.updateMagnifierForDrag(motionEvent);
                        } else if (Editor.this.mMagnifierAnimator != null) {
                            Editor.this.mMagnifierAnimator.dismiss();
                        }
                        int offsetForPosition3 = Editor.this.mTextView.getOffsetForPosition(x, y);
                        if (this.mDragAcceleratorMode != 0) {
                            int i = this.mPrevDownTouchOffset;
                            if (i < offsetForPosition3 && this.mPrevTouchWordEnd < offsetForPosition3) {
                                int i2 = this.mPrevTouchOffset;
                                if (i2 < offsetForPosition3) {
                                    this.mDragAcceleratorMode = 2;
                                } else if (offsetForPosition3 < i2) {
                                    this.mDragAcceleratorMode = 1;
                                }
                            } else if (offsetForPosition3 < i && offsetForPosition3 < this.mPrevTouchWordStart) {
                                int i3 = this.mPrevTouchOffset;
                                if (i3 < offsetForPosition3) {
                                    this.mDragAcceleratorMode = 1;
                                } else if (offsetForPosition3 < i3) {
                                    this.mDragAcceleratorMode = 2;
                                }
                            }
                        }
                        this.mPrevTouchOffset = offsetForPosition3;
                        updateSelection(motionEvent);
                        return;
                    }
                    return;
                }
                if (actionMasked != 3) {
                    if ((actionMasked == 5 || actionMasked == 6) && Editor.this.mTextView.getContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH_DISTINCT)) {
                        updateMinAndMaxOffsets(motionEvent);
                        return;
                    }
                    return;
                }
            }
            Editor.this.mShowMagnifier = false;
            Editor.this.dismissMagnifierForDrag();
            VelocityTracker velocityTracker3 = this.mSelectionVelocityTracker;
            if (velocityTracker3 != null) {
                velocityTracker3.recycle();
                this.mSelectionVelocityTracker = null;
            }
        }

        private void updateSelection(MotionEvent motionEvent) {
            if (Editor.this.mTextView.getLayout() != null) {
                int i = this.mDragAcceleratorMode;
                if (i != 1) {
                    if (i == 2) {
                        updateWordBasedSelection(motionEvent);
                        return;
                    } else {
                        if (i != 3) {
                            return;
                        }
                        updateParagraphBasedSelection(motionEvent);
                        return;
                    }
                }
                if (motionEvent.isFromSource(8194) && !Editor.this.mTouchState.isDoubleTap() && !Editor.this.mIsSelectedByLongClick) {
                    updateCharacterBasedSelection(motionEvent);
                } else {
                    updateCharacterBasedSelectionAfterSelectWord(motionEvent);
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

        private void updateCharacterBasedSelection(MotionEvent motionEvent) {
            updateSelectionInternal(this.mStartOffset, Editor.this.mTextView.getOffsetForPosition(motionEvent.getX(), motionEvent.getY()), motionEvent.isFromSource(4098));
        }

        private void updateWordBasedSelection(MotionEvent motionEvent) {
            int wordStart;
            int wordEnd;
            if (this.mHaventMovedEnoughToStartDrag) {
                return;
            }
            motionEvent.isFromSource(8194);
            ViewConfiguration.get(Editor.this.mTextView.getContext());
            float x = motionEvent.getX();
            int lineAtCoordinate = Editor.this.mTextView.getLineAtCoordinate(motionEvent.getY());
            int offsetAtCoordinate = Editor.this.mTextView.getOffsetAtCoordinate(lineAtCoordinate, x);
            if (this.mStartOffset < offsetAtCoordinate) {
                wordStart = Editor.this.getWordEnd(offsetAtCoordinate);
                wordEnd = Editor.this.getWordStart(this.mStartOffset);
            } else {
                wordStart = Editor.this.getWordStart(offsetAtCoordinate);
                wordEnd = Editor.this.getWordEnd(this.mStartOffset);
                if (wordEnd == wordStart) {
                    wordStart = Editor.this.getNextCursorOffset(wordStart, false);
                }
            }
            this.mLineSelectionIsOn = lineAtCoordinate;
            updateSelectionInternal(wordEnd, wordStart, motionEvent.isFromSource(4098));
        }

        private void updateParagraphBasedSelection(MotionEvent motionEvent) {
            int offsetForPosition = Editor.this.mTextView.getOffsetForPosition(motionEvent.getX(), motionEvent.getY());
            long paragraphsRange = Editor.this.getParagraphsRange(Math.min(offsetForPosition, this.mStartOffset), Math.max(offsetForPosition, this.mStartOffset));
            updateSelectionInternal(TextUtils.unpackRangeStartFromLong(paragraphsRange), TextUtils.unpackRangeEndFromLong(paragraphsRange), motionEvent.isFromSource(4098));
        }

        private void updateSelectionInternal(int i, int i2, boolean z) {
            boolean z2 = z && Editor.this.mHapticTextHandleEnabled && !(Editor.this.mTextView.getSelectionStart() == i && Editor.this.mTextView.getSelectionEnd() == i2);
            Selection.setSelection((Spannable) Editor.this.mTextView.getText(), i, i2);
            if (z2) {
                Editor.this.mTextView.performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(41));
            }
        }

        private void updateMinAndMaxOffsets(MotionEvent motionEvent) {
            int pointerCount = motionEvent.getPointerCount();
            for (int i = 0; i < pointerCount; i++) {
                int offsetForPosition = Editor.this.mTextView.getOffsetForPosition(motionEvent.getX(i), motionEvent.getY(i));
                if (offsetForPosition < this.mMinTouchOffset) {
                    this.mMinTouchOffset = offsetForPosition;
                }
                if (offsetForPosition > this.mMaxTouchOffset) {
                    this.mMaxTouchOffset = offsetForPosition;
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
                Selection.setSelection((Spannable) Editor.this.mTextView.getText(), selectionEnd, selectionStart);
            }
        }

        public boolean isSelectionStartDragged() {
            SelectionHandleView selectionHandleView = this.mStartHandle;
            return selectionHandleView != null && selectionHandleView.isDragging();
        }

        @Override // android.widget.Editor.CursorController
        public boolean isCursorBeingModified() {
            if (isDragAcceleratorActive() || isSelectionStartDragged()) {
                return true;
            }
            SelectionHandleView selectionHandleView = this.mEndHandle;
            return selectionHandleView != null && selectionHandleView.isDragging();
        }

        public boolean isDragAcceleratorActive() {
            return this.mDragAcceleratorMode != 0;
        }

        @Override // android.view.ViewTreeObserver.OnTouchModeChangeListener
        public void onTouchModeChanged(boolean z) {
            if (z) {
                return;
            }
            hide();
        }

        @Override // android.widget.Editor.CursorController
        public void onDetached() {
            Editor.this.mTextView.getViewTreeObserver().removeOnTouchModeChangeListener(this);
            SelectionHandleView selectionHandleView = this.mStartHandle;
            if (selectionHandleView != null) {
                selectionHandleView.onDetached();
            }
            SelectionHandleView selectionHandleView2 = this.mEndHandle;
            if (selectionHandleView2 != null) {
                selectionHandleView2.onDetached();
            }
        }

        @Override // android.widget.Editor.CursorController
        public boolean isActive() {
            SelectionHandleView selectionHandleView = this.mStartHandle;
            if (selectionHandleView != null && selectionHandleView.isShowing()) {
                return true;
            }
            SelectionHandleView selectionHandleView2 = this.mEndHandle;
            return selectionHandleView2 != null && selectionHandleView2.isShowing();
        }

        public void invalidateHandles() {
            SelectionHandleView selectionHandleView = this.mStartHandle;
            if (selectionHandleView != null) {
                selectionHandleView.invalidate();
            }
            SelectionHandleView selectionHandleView2 = this.mEndHandle;
            if (selectionHandleView2 != null) {
                selectionHandleView2.invalidate();
            }
        }

        private void updateCharacterBasedSelectionAfterSelectWord(MotionEvent motionEvent) {
            int offsetForPosition = Editor.this.mTextView.getOffsetForPosition(motionEvent.getX(), motionEvent.getY());
            int selectionStart = Editor.this.mTextView.getSelectionStart();
            int selectionEnd = Editor.this.mTextView.getSelectionEnd();
            int wordStart = Editor.this.getWordStart(this.mStartOffset);
            int wordEnd = Editor.this.getWordEnd(this.mStartOffset);
            if (wordStart > offsetForPosition || wordEnd < offsetForPosition) {
                this.mIsExpanded = true;
            }
            if (this.mIsExpanded && selectionStart != offsetForPosition) {
                if (offsetForPosition < selectionStart && offsetForPosition < selectionEnd && selectionEnd == wordEnd) {
                    selectionStart = wordEnd;
                }
                Selection.setSelection((Spannable) Editor.this.mTextView.getText(), selectionStart, offsetForPosition);
            }
        }
    }

    void loadHandleDrawables(boolean z) {
        if (this.mSelectHandleCenter == null || z) {
            this.mSelectHandleCenter = this.mTextView.getTextSelectHandle();
            if (hasInsertionController()) {
                getInsertionController().reloadHandleDrawable();
            }
        }
        if (this.mSelectHandleLeft == null || this.mSelectHandleRight == null || z) {
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
        private final Paint mPaint;
        private final Path mPath = new Path();
        private int mStart;
        private RectF mTempRectF;

        public CorrectionHighlighter() {
            Paint paint = new Paint(1);
            this.mPaint = paint;
            paint.setCompatibilityScaling(Editor.this.mTextView.getResources().getCompatibilityInfo().applicationScale);
            paint.setStyle(Paint.Style.FILL);
        }

        public void highlight(CorrectionInfo correctionInfo) {
            int offset = correctionInfo.getOffset();
            this.mStart = offset;
            this.mEnd = offset + correctionInfo.getNewText().length();
            this.mFadingStartTime = SystemClock.uptimeMillis();
            if (this.mStart < 0 || this.mEnd < 0) {
                stopAnimation();
            }
        }

        public void draw(Canvas canvas, int i) {
            if (updatePath() && updatePaint()) {
                if (i != 0) {
                    canvas.translate(0.0f, i);
                }
                canvas.drawPath(this.mPath, this.mPaint);
                if (i != 0) {
                    canvas.translate(0.0f, -i);
                }
                invalidate(true);
                return;
            }
            stopAnimation();
            invalidate(false);
        }

        private boolean updatePaint() {
            long uptimeMillis = SystemClock.uptimeMillis() - this.mFadingStartTime;
            if (uptimeMillis > 400) {
                return false;
            }
            this.mPaint.setColor((Editor.this.mTextView.mHighlightColor & 16777215) + (((int) (Color.alpha(Editor.this.mTextView.mHighlightColor) * (1.0f - (uptimeMillis / 400.0f)))) << 24));
            return true;
        }

        private boolean updatePath() {
            Layout layout = Editor.this.mTextView.getLayout();
            if (layout == null) {
                return false;
            }
            int length = Editor.this.mTextView.getText().length();
            int min = Math.min(length, this.mStart);
            int min2 = Math.min(length, this.mEnd);
            this.mPath.reset();
            layout.getSelectionPath(Editor.this.mTextView.originalToTransformed(min, 0), Editor.this.mTextView.originalToTransformed(min2, 0), this.mPath);
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void invalidate(boolean z) {
            if (Editor.this.mTextView.getLayout() == null) {
                return;
            }
            if (this.mTempRectF == null) {
                this.mTempRectF = new RectF();
            }
            this.mPath.computeBounds(this.mTempRectF, false);
            int compoundPaddingLeft = Editor.this.mTextView.getCompoundPaddingLeft();
            int extendedPaddingTop = Editor.this.mTextView.getExtendedPaddingTop() + Editor.this.mTextView.getVerticalOffset(true);
            if (z) {
                Editor.this.mTextView.postInvalidateOnAnimation(((int) this.mTempRectF.left) + compoundPaddingLeft, ((int) this.mTempRectF.top) + extendedPaddingTop, compoundPaddingLeft + ((int) this.mTempRectF.right), extendedPaddingTop + ((int) this.mTempRectF.bottom));
            } else {
                Editor.this.mTextView.postInvalidate((int) this.mTempRectF.left, (int) this.mTempRectF.top, (int) this.mTempRectF.right, (int) this.mTempRectF.bottom);
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

        ErrorPopup(TextView textView, int i, int i2) {
            super(textView, i, i2);
            this.mAbove = false;
            this.mPopupInlineErrorBackgroundId = 0;
            this.mPopupInlineErrorAboveBackgroundId = 0;
            this.mView = textView;
            int resourceId = getResourceId(0, 313);
            this.mPopupInlineErrorBackgroundId = resourceId;
            textView.setBackgroundResource(resourceId);
        }

        void fixDirection(boolean z) {
            this.mAbove = z;
            if (z) {
                this.mPopupInlineErrorAboveBackgroundId = getResourceId(this.mPopupInlineErrorAboveBackgroundId, 312);
            } else {
                this.mPopupInlineErrorBackgroundId = getResourceId(this.mPopupInlineErrorBackgroundId, 313);
            }
            this.mView.setBackgroundResource(z ? this.mPopupInlineErrorAboveBackgroundId : this.mPopupInlineErrorBackgroundId);
        }

        private int getResourceId(int i, int i2) {
            if (i != 0) {
                return i;
            }
            TypedArray obtainStyledAttributes = this.mView.getContext().obtainStyledAttributes(android.R.styleable.Theme);
            int resourceId = obtainStyledAttributes.getResourceId(i2, 0);
            obtainStyledAttributes.recycle();
            return resourceId;
        }

        @Override // android.widget.PopupWindow
        public void update(int i, int i2, int i3, int i4, boolean z) {
            super.update(i, i2, i3, i4, z);
            boolean isAboveAnchor = isAboveAnchor();
            if (isAboveAnchor != this.mAbove) {
                fixDirection(isAboveAnchor);
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

        InputContentType() {
        }
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

        InputMethodState() {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isValidRange(CharSequence charSequence, int i, int i2) {
        return i >= 0 && i <= i2 && i2 <= charSequence.length();
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
        private @interface MergeMode {
        }

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
        public CharSequence filter(CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
            boolean z;
            UndoInputFilter undoInputFilter;
            CharSequence charSequence2;
            int i5;
            int i6;
            int i7;
            int i8;
            Spanned spanned2;
            if (!canUndoEdit(charSequence, i, i2, spanned, i3, i4)) {
                return null;
            }
            boolean z2 = this.mHasComposition;
            this.mHasComposition = isComposition(charSequence);
            boolean z3 = this.mExpanding;
            int i9 = i2 - i;
            int i10 = i4 - i3;
            if (i9 != i10) {
                boolean z4 = i9 > i10;
                this.mExpanding = z4;
                if (z2 && z4 != z3 && !isHangul(spanned)) {
                    z = true;
                    undoInputFilter = this;
                    charSequence2 = charSequence;
                    i5 = i;
                    i6 = i2;
                    spanned2 = spanned;
                    i8 = i4;
                    i7 = i3;
                    undoInputFilter.handleEdit(charSequence2, i5, i6, spanned2, i7, i8, z);
                    return null;
                }
            }
            z = false;
            undoInputFilter = this;
            charSequence2 = charSequence;
            i5 = i;
            i6 = i2;
            i7 = i3;
            i8 = i4;
            spanned2 = spanned;
            undoInputFilter.handleEdit(charSequence2, i5, i6, spanned2, i7, i8, z);
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

        private void handleEdit(CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4, boolean z) {
            int i5 = (isInTextWatcher() || this.mPreviousOperationWasInSameBatchEdit) ? 0 : z ? 1 : 2;
            EditOperation editOperation = new EditOperation(this.mEditor, TextUtils.substring(spanned, i3, i4), i3, TextUtils.substring(charSequence, i, i2), this.mHasComposition);
            if (this.mHasComposition && TextUtils.equals(editOperation.mNewText, editOperation.mOldText)) {
                return;
            }
            recordEdit(editOperation, i5);
        }

        private EditOperation getLastEdit() {
            return (EditOperation) this.mEditor.mUndoManager.getLastOperation(EditOperation.class, this.mEditor.mUndoOwner, 1);
        }

        private void recordEdit(EditOperation editOperation, int i) {
            UndoManager undoManager = this.mEditor.mUndoManager;
            undoManager.beginUpdate("Edit text");
            EditOperation lastEdit = getLastEdit();
            if (lastEdit == null) {
                undoManager.addOperation(editOperation, 0);
            } else if (i == 0) {
                lastEdit.forceMergeWith(editOperation);
            } else if (!this.mIsUserEdit) {
                undoManager.commitState(this.mEditor.mUndoOwner);
                undoManager.addOperation(editOperation, 0);
            } else if (i != 2 || !lastEdit.mergeWith(editOperation)) {
                undoManager.commitState(this.mEditor.mUndoOwner);
                undoManager.addOperation(editOperation, 0);
            }
            this.mPreviousOperationWasInSameBatchEdit = this.mIsUserEdit;
            undoManager.endUpdate();
        }

        private boolean canUndoEdit(CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
            if (this.mEditor.mAllowUndo && !this.mEditor.mUndoManager.isInUndo() && Editor.isValidRange(charSequence, i, i2) && Editor.isValidRange(spanned, i3, i4)) {
                return (i == i2 && i3 == i4) ? false : true;
            }
            return false;
        }

        private static boolean isComposition(CharSequence charSequence) {
            if (!(charSequence instanceof Spannable)) {
                return false;
            }
            Spannable spannable = (Spannable) charSequence;
            return EditableInputConnection.getComposingSpanStart(spannable) < EditableInputConnection.getComposingSpanEnd(spannable);
        }

        private boolean isInTextWatcher() {
            CharSequence text = this.mEditor.mTextView.getText();
            return (text instanceof SpannableStringBuilder) && ((SpannableStringBuilder) text).getTextWatcherDepth() > 0;
        }

        private boolean isHangul(Spanned spanned) {
            if (TextUtils.isEmpty(spanned)) {
                return false;
            }
            char charAt = spanned.charAt(spanned.length() - 1);
            return Character.UnicodeBlock.of(charAt) == Character.UnicodeBlock.HANGUL_COMPATIBILITY_JAMO || Character.UnicodeBlock.of(charAt) == Character.UnicodeBlock.HANGUL_JAMO || Character.UnicodeBlock.of(charAt) == Character.UnicodeBlock.HANGUL_JAMO_EXTENDED_A || Character.UnicodeBlock.of(charAt) == Character.UnicodeBlock.HANGUL_JAMO_EXTENDED_B || Character.UnicodeBlock.of(charAt) == Character.UnicodeBlock.HANGUL_SYLLABLES;
        }
    }

    public static class EditOperation extends UndoOperation<Editor> {
        public static final Parcelable.ClassLoaderCreator<EditOperation> CREATOR = new Parcelable.ClassLoaderCreator<EditOperation>() { // from class: android.widget.Editor.EditOperation.1
            @Override // android.os.Parcelable.Creator
            public EditOperation createFromParcel(Parcel parcel) {
                return new EditOperation(parcel, null);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.ClassLoaderCreator
            public EditOperation createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new EditOperation(parcel, classLoader);
            }

            @Override // android.os.Parcelable.Creator
            public EditOperation[] newArray(int i) {
                return new EditOperation[i];
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

        @Override // android.content.UndoOperation
        public void commit() {
        }

        public EditOperation(Editor editor, String str, int i, String str2, boolean z) {
            super(editor.mUndoOwner);
            this.mOldText = str;
            this.mNewText = str2;
            if (str2.length() > 0 && this.mOldText.length() == 0) {
                this.mType = 0;
            } else if (this.mNewText.length() == 0 && this.mOldText.length() > 0) {
                this.mType = 1;
            } else {
                this.mType = 2;
            }
            this.mStart = i;
            this.mOldCursorPos = editor.mTextView.getSelectionStart();
            this.mNewCursorPos = i + this.mNewText.length();
            this.mIsComposition = z;
        }

        public EditOperation(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.mType = parcel.readInt();
            this.mOldText = parcel.readString();
            this.mNewText = parcel.readString();
            this.mStart = parcel.readInt();
            this.mOldCursorPos = parcel.readInt();
            this.mNewCursorPos = parcel.readInt();
            this.mFrozen = parcel.readInt() == 1;
            this.mIsComposition = parcel.readInt() == 1;
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
        public void undo() {
            modifyText((Editable) getOwnerData().mTextView.getText(), this.mStart, getNewTextEnd(), this.mOldText, this.mStart, this.mOldCursorPos);
        }

        @Override // android.content.UndoOperation
        public void redo() {
            modifyText((Editable) getOwnerData().mTextView.getText(), this.mStart, getOldTextEnd(), this.mNewText, this.mStart, this.mNewCursorPos);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean mergeWith(EditOperation editOperation) {
            if (this.mFrozen) {
                return false;
            }
            int i = this.mType;
            if (i == 0) {
                return mergeInsertWith(editOperation);
            }
            if (i == 1) {
                return mergeDeleteWith(editOperation);
            }
            if (i != 2) {
                return false;
            }
            return mergeReplaceWith(editOperation);
        }

        private boolean mergeInsertWith(EditOperation editOperation) {
            int i = editOperation.mType;
            if (i == 0) {
                if (getNewTextEnd() != editOperation.mStart) {
                    return false;
                }
                this.mNewText += editOperation.mNewText;
                this.mNewCursorPos = editOperation.mNewCursorPos;
                this.mFrozen = editOperation.mFrozen;
                this.mIsComposition = editOperation.mIsComposition;
                return true;
            }
            if (!this.mIsComposition || i != 2 || this.mStart > editOperation.mStart || getNewTextEnd() < editOperation.getOldTextEnd()) {
                return false;
            }
            this.mNewText = this.mNewText.substring(0, editOperation.mStart - this.mStart) + editOperation.mNewText + this.mNewText.substring(editOperation.getOldTextEnd() - this.mStart, this.mNewText.length());
            this.mNewCursorPos = editOperation.mNewCursorPos;
            this.mIsComposition = editOperation.mIsComposition;
            return true;
        }

        private boolean mergeDeleteWith(EditOperation editOperation) {
            if (editOperation.mType != 1 || this.mStart != editOperation.getOldTextEnd()) {
                return false;
            }
            this.mStart = editOperation.mStart;
            this.mOldText = editOperation.mOldText + this.mOldText;
            this.mNewCursorPos = editOperation.mNewCursorPos;
            this.mIsComposition = editOperation.mIsComposition;
            return true;
        }

        private boolean mergeReplaceWith(EditOperation editOperation) {
            if (editOperation.mType == 0 && getNewTextEnd() == editOperation.mStart) {
                this.mNewText += editOperation.mNewText;
                this.mNewCursorPos = editOperation.mNewCursorPos;
                return true;
            }
            if (!this.mIsComposition) {
                return false;
            }
            if (editOperation.mType == 1 && this.mStart <= editOperation.mStart && getNewTextEnd() >= editOperation.getOldTextEnd()) {
                String str = this.mNewText.substring(0, editOperation.mStart - this.mStart) + this.mNewText.substring(editOperation.getOldTextEnd() - this.mStart, this.mNewText.length());
                this.mNewText = str;
                if (str.isEmpty()) {
                    this.mType = 1;
                }
                this.mNewCursorPos = editOperation.mNewCursorPos;
                this.mIsComposition = editOperation.mIsComposition;
                return true;
            }
            if (editOperation.mType != 2 || this.mStart != editOperation.mStart || !TextUtils.equals(this.mNewText, editOperation.mOldText)) {
                return false;
            }
            this.mNewText = editOperation.mNewText;
            this.mNewCursorPos = editOperation.mNewCursorPos;
            this.mIsComposition = editOperation.mIsComposition;
            return true;
        }

        public void forceMergeWith(EditOperation editOperation) {
            if (mergeWith(editOperation)) {
                return;
            }
            Editable editable = (Editable) getOwnerData().mTextView.getText();
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(editable.toString());
            modifyText(spannableStringBuilder, this.mStart, getNewTextEnd(), this.mOldText, this.mStart, this.mOldCursorPos);
            SpannableStringBuilder spannableStringBuilder2 = new SpannableStringBuilder(editable.toString());
            modifyText(spannableStringBuilder2, editOperation.mStart, editOperation.getOldTextEnd(), editOperation.mNewText, editOperation.mStart, editOperation.mNewCursorPos);
            this.mType = 2;
            this.mNewText = spannableStringBuilder2.toString();
            this.mOldText = spannableStringBuilder.toString();
            this.mStart = 0;
            this.mNewCursorPos = editOperation.mNewCursorPos;
            this.mIsComposition = editOperation.mIsComposition;
        }

        private static void modifyText(Editable editable, int i, int i2, CharSequence charSequence, int i3, int i4) {
            if (Editor.isValidRange(editable, i, i2) && i3 <= editable.length() - (i2 - i)) {
                if (i != i2) {
                    editable.delete(i, i2);
                }
                if (charSequence.length() != 0) {
                    editable.insert(i3, charSequence);
                }
            }
            if (i4 < 0 || i4 > editable.length()) {
                return;
            }
            Selection.setSelection(editable, i4);
        }

        private String getTypeString() {
            int i = this.mType;
            if (i == 0) {
                return "insert";
            }
            if (i == 1) {
                return "delete";
            }
            if (i == 2) {
                return "replace";
            }
            return "";
        }

        public String toString() {
            return "[mType=" + getTypeString() + ", mOldText=" + this.mOldText + ", mNewText=" + this.mNewText + ", mStart=" + this.mStart + ", mOldCursorPos=" + this.mOldCursorPos + ", mNewCursorPos=" + this.mNewCursorPos + ", mFrozen=" + this.mFrozen + ", mIsComposition=" + this.mIsComposition + NavigationBarInflaterView.SIZE_MOD_END;
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
            Editor editor2 = (Editor) Objects.requireNonNull(editor);
            this.mEditor = editor2;
            TextView textView = (TextView) Objects.requireNonNull(editor2.mTextView);
            this.mTextView = textView;
            Context context = (Context) Objects.requireNonNull(textView.getContext());
            this.mContext = context;
            this.mPackageManager = (PackageManager) Objects.requireNonNull(context.getPackageManager());
            this.mPackageName = (String) Objects.requireNonNull(context.getPackageName());
        }

        public void onInitializeMenu(Menu menu) {
            String string = Settings.Global.getString(this.mTextView.getContext().getContentResolver(), Settings.Global.SEM_PROCESS_TEXT_MANAGE_APPS);
            loadSupportedActivities();
            int size = this.mSupportedActivities.size();
            for (int i = 0; i < size; i++) {
                ResolveInfo resolveInfo = this.mSupportedActivities.get(i);
                int order = getOrder(resolveInfo);
                if (order < 0) {
                    order = i + 100;
                }
                Log.e("Editor", "label : " + ((Object) getLabel(resolveInfo)));
                if (string != null && resolveInfo.getComponentInfo() != null && string.contains(resolveInfo.getComponentInfo().name)) {
                    menu.add(0, 0, order, getLabel(resolveInfo)).setIcon(loadIcon(resolveInfo)).setIntent(createProcessTextIntentForResolveInfo(resolveInfo)).setShowAsAction(1);
                }
            }
        }

        public boolean performMenuItemAction(MenuItem menuItem) {
            return fireIntent(menuItem.getIntent());
        }

        public void initializeAccessibilityActions() {
            this.mAccessibilityIntents.clear();
            this.mAccessibilityActions.clear();
            loadSupportedActivities();
            int i = 0;
            for (ResolveInfo resolveInfo : this.mSupportedActivities) {
                int i2 = i + 1;
                int i3 = i + 268435712;
                this.mAccessibilityActions.put(i3, new AccessibilityNodeInfo.AccessibilityAction(i3, getLabel(resolveInfo)));
                this.mAccessibilityIntents.put(i3, createProcessTextIntentForResolveInfo(resolveInfo));
                i = i2;
            }
        }

        public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
            for (int i = 0; i < this.mAccessibilityActions.size(); i++) {
                accessibilityNodeInfo.addAction(this.mAccessibilityActions.valueAt(i));
            }
        }

        public boolean performAccessibilityAction(int i) {
            return fireIntent(this.mAccessibilityIntents.get(i));
        }

        private boolean fireIntent(Intent intent) {
            if (intent == null || !Intent.ACTION_PROCESS_TEXT.equals(intent.getAction())) {
                return false;
            }
            intent.putExtra(Intent.EXTRA_PROCESS_TEXT, (String) TextUtils.trimToParcelableSize(this.mTextView.getSelectedText()));
            this.mEditor.mPreserveSelection = true;
            this.mTextView.startActivityForResult(intent, 100);
            return true;
        }

        private void loadSupportedActivities() {
            this.mSupportedActivities.clear();
            if (this.mContext.canStartActivityForResult()) {
                for (ResolveInfo resolveInfo : this.mTextView.getContext().getPackageManager().queryIntentActivities(createProcessTextIntent(), 0)) {
                    if (isSupportedActivity(resolveInfo) && !resolveInfo.getComponentInfo().packageName.contains("com.samsung.android.app.interpreter")) {
                        this.mSupportedActivities.add(resolveInfo);
                    }
                }
            }
        }

        private boolean isSupportedActivity(ResolveInfo resolveInfo) {
            if (this.mPackageName.equals(resolveInfo.activityInfo.packageName)) {
                return true;
            }
            if (resolveInfo.activityInfo.exported) {
                return resolveInfo.activityInfo.permission == null || this.mContext.checkSelfPermission(resolveInfo.activityInfo.permission) == 0;
            }
            return false;
        }

        private Intent createProcessTextIntentForResolveInfo(ResolveInfo resolveInfo) {
            return createProcessTextIntent().putExtra(Intent.EXTRA_PROCESS_TEXT_READONLY, !this.mTextView.isTextEditable()).setClassName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name);
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
            String resolveInfo2 = resolveInfo.toString();
            return (resolveInfo2.contains("com.sec.android.app.translator") || resolveInfo2.contains("com.google.android.apps.translate")) ? 16 : -1;
        }

        private Drawable loadIcon(ResolveInfo resolveInfo) {
            String resolveInfo2 = resolveInfo.toString();
            Drawable loadIcon = resolveInfo.loadIcon(this.mTextView.getContext().getPackageManager());
            if (resolveInfo2.contains("com.sec.android.app.translator") || resolveInfo2.contains("com.google.android.apps.translate")) {
                return this.mTextView.getContext().getResources().getDrawable(R.drawable.tw_floating_popup_button_ic_translate);
            }
            if (loadIcon != null) {
                int intrinsicWidth = this.mTextView.getContext().getResources().getDrawable(R.drawable.tw_floating_popup_button_ic_selectall).getIntrinsicWidth();
                loadIcon.setBounds(0, 0, intrinsicWidth, intrinsicWidth);
            }
            return loadIcon;
        }
    }

    private static final class AccessibilitySmartActions {
        private final SparseArray<Pair<AccessibilityNodeInfo.AccessibilityAction, RemoteAction>> mActions;
        private final TextView mTextView;

        private AccessibilitySmartActions(TextView textView) {
            this.mActions = new SparseArray<>();
            this.mTextView = (TextView) Objects.requireNonNull(textView);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addAction(RemoteAction remoteAction) {
            int size = this.mActions.size() + 268439552;
            this.mActions.put(size, new Pair<>(new AccessibilityNodeInfo.AccessibilityAction(size, remoteAction.getTitle()), remoteAction));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void reset() {
            this.mActions.clear();
        }

        void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
            for (int i = 0; i < this.mActions.size(); i++) {
                accessibilityNodeInfo.addAction(this.mActions.valueAt(i).first);
            }
        }

        boolean performAccessibilityAction(int i) {
            Pair<AccessibilityNodeInfo.AccessibilityAction, RemoteAction> pair = this.mActions.get(i);
            if (pair == null) {
                return false;
            }
            TextClassification.createIntentOnClickListener(pair.second.getActionIntent()).onClick(this.mTextView);
            return true;
        }
    }

    private static final class InsertModeController {
        private final Paint mHighlightPaint;
        private final Path mHighlightPath;
        private InsertModeTransformationMethod mInsertModeTransformationMethod;
        private boolean mIsInsertModeActive;
        private final TextView mTextView;
        private boolean mUpdatingTransformationMethod;

        InsertModeController(TextView textView) {
            TextView textView2 = (TextView) Objects.requireNonNull(textView);
            this.mTextView = textView2;
            this.mIsInsertModeActive = false;
            this.mInsertModeTransformationMethod = null;
            Paint paint = new Paint();
            this.mHighlightPaint = paint;
            this.mHighlightPath = new Path();
            paint.setColor(ColorUtils.setAlphaComponent(textView2.getTextColors().getDefaultColor(), (int) (Color.alpha(r2) * 0.2f)));
        }

        boolean enterInsertMode(int i) {
            if (this.mIsInsertModeActive) {
                return false;
            }
            TransformationMethod transformationMethod = this.mTextView.getTransformationMethod();
            if (transformationMethod instanceof OffsetMapping) {
                return false;
            }
            InsertModeTransformationMethod insertModeTransformationMethod = new InsertModeTransformationMethod(i, this.mTextView.isSingleLine(), transformationMethod);
            this.mInsertModeTransformationMethod = insertModeTransformationMethod;
            setTransformationMethod(insertModeTransformationMethod, true);
            Selection.setSelection((Spannable) this.mTextView.getText(), i);
            this.mIsInsertModeActive = true;
            return true;
        }

        void exitInsertMode() {
            exitInsertMode(true);
        }

        void exitInsertMode(boolean z) {
            if (this.mIsInsertModeActive) {
                InsertModeTransformationMethod insertModeTransformationMethod = this.mInsertModeTransformationMethod;
                if (insertModeTransformationMethod == null || insertModeTransformationMethod != this.mTextView.getTransformationMethod()) {
                    this.mIsInsertModeActive = false;
                    return;
                }
                int selectionStart = this.mTextView.getSelectionStart();
                int selectionEnd = this.mTextView.getSelectionEnd();
                setTransformationMethod(this.mInsertModeTransformationMethod.getOldTransformationMethod(), z);
                Selection.setSelection((Spannable) this.mTextView.getText(), selectionStart, selectionEnd);
                this.mIsInsertModeActive = false;
            }
        }

        void onDraw(Canvas canvas) {
            Layout layout;
            if (this.mIsInsertModeActive) {
                CharSequence transformed = this.mTextView.getTransformed();
                if (!(transformed instanceof InsertModeTransformationMethod.TransformedText) || (layout = this.mTextView.getLayout()) == null) {
                    return;
                }
                InsertModeTransformationMethod.TransformedText transformedText = (InsertModeTransformationMethod.TransformedText) transformed;
                layout.getSelectionPath(transformedText.getHighlightStart(), transformedText.getHighlightEnd(), this.mHighlightPath);
                canvas.drawPath(this.mHighlightPath, this.mHighlightPaint);
            }
        }

        private void setTransformationMethod(TransformationMethod transformationMethod, boolean z) {
            this.mUpdatingTransformationMethod = true;
            this.mTextView.setTransformationMethodInternal(transformationMethod, z);
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
            InsertModeTransformationMethod update = this.mInsertModeTransformationMethod.update(transformationMethod, this.mTextView.isSingleLine());
            this.mInsertModeTransformationMethod = update;
            setTransformationMethod(update, true);
            Selection.setSelection((Spannable) this.mTextView.getText(), selectionStart, selectionEnd);
        }
    }

    boolean enterInsertMode(int i) {
        if (this.mInsertModeController == null) {
            if (this.mTextView == null) {
                return false;
            }
            this.mInsertModeController = new InsertModeController(this.mTextView);
        }
        return this.mInsertModeController.enterInsertMode(i);
    }

    void exitInsertMode() {
        InsertModeController insertModeController = this.mInsertModeController;
        if (insertModeController == null) {
            return;
        }
        insertModeController.exitInsertMode();
    }

    void setTransformationMethod(TransformationMethod transformationMethod) {
        InsertModeController insertModeController = this.mInsertModeController;
        if (insertModeController == null) {
            this.mTextView.setTransformationMethodInternal(transformationMethod, true);
        } else {
            insertModeController.updateTransformationMethod(transformationMethod);
        }
    }

    void beforeSetText() {
        InsertModeController insertModeController = this.mInsertModeController;
        if (insertModeController == null) {
            return;
        }
        insertModeController.beforeSetText();
    }

    void onInitializeSmartActionsAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        this.mA11ySmartActions.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
    }

    boolean performSmartActionsAccessibilityAction(int i) {
        return this.mA11ySmartActions.performAccessibilityAction(i);
    }

    static void logCursor(String str, String str2, Object... objArr) {
        if (str2 == null) {
            Log.d("Editor", str);
            return;
        }
        Log.d("Editor", str + ": " + String.format(str2, objArr));
    }

    private boolean tooLargeTextForMagnifierForDrag() {
        if (this.mMagnifierAnimator != null) {
            float round = Math.round(r0.mMagnifier.getHeight() / this.mMagnifierAnimator.mMagnifier.getZoom());
            Paint.FontMetrics fontMetrics = this.mTextView.getPaint().getFontMetrics();
            if (fontMetrics.descent - fontMetrics.ascent > round) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dismissMagnifierForDrag() {
        MagnifierMotionAnimator magnifierMotionAnimator = this.mMagnifierAnimator;
        if (magnifierMotionAnimator != null) {
            magnifierMotionAnimator.dismiss();
            this.mRenderCursorRegardlessTiming = false;
            resumeBlink();
        }
    }

    private boolean obtainMagnifierShowCoordinatesForDrag(MotionEvent motionEvent, PointF pointF) {
        int offsetForPosition = this.mTextView.getOffsetForPosition(motionEvent.getX(), motionEvent.getY());
        if (offsetForPosition == -1) {
            return false;
        }
        int lineForOffset = this.mTextView.getLayout().getLineForOffset(offsetForPosition);
        this.mTextView.getLocationOnScreen(new int[2]);
        float rawX = motionEvent.getRawX() - r1[0];
        float totalPaddingLeft = this.mTextView.getTotalPaddingLeft() - this.mTextView.getScrollX();
        float totalPaddingLeft2 = this.mTextView.getTotalPaddingLeft() - this.mTextView.getScrollX();
        float lineLeft = totalPaddingLeft + this.mTextView.getLayout().getLineLeft(lineForOffset);
        float lineRight = totalPaddingLeft2 + this.mTextView.getLayout().getLineRight(lineForOffset);
        float round = Math.round(this.mMagnifierAnimator.mMagnifier.getWidth() / this.mMagnifierAnimator.mMagnifier.getZoom()) / 2.0f;
        if (rawX < lineLeft - round || rawX > round + lineRight) {
            return false;
        }
        pointF.x = Math.max(lineLeft, Math.min(lineRight, rawX));
        pointF.y = (((this.mTextView.getLayout().getLineTop(lineForOffset) + this.mTextView.getLayout().getLineBottom(lineForOffset)) / 2.0f) + this.mTextView.getTotalPaddingTop()) - this.mTextView.getScrollY();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateMagnifierForDrag(MotionEvent motionEvent) {
        if (getMagnifierAnimator() == null) {
            return;
        }
        PointF pointF = new PointF();
        if (!tooLargeTextForMagnifierForDrag() && obtainMagnifierShowCoordinatesForDrag(motionEvent, pointF)) {
            this.mRenderCursorRegardlessTiming = true;
            this.mTextView.invalidateCursorPath();
            suspendBlink();
            this.mMagnifierAnimator.show(pointF.x, pointF.y);
            return;
        }
        dismissMagnifierForDrag();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isUniversalSwitchEnable() {
        return Settings.Secure.getInt(this.mTextView.getContext().getContentResolver(), SWITCH_CONTROL_ENABLED, 0) == 1;
    }

    public void setUseCtxMenuInDesktopMode(boolean z) {
        if (!this.mTextView.isDesktopMode()) {
            this.mUseCtxMenuInDesktopMode = z;
            return;
        }
        if (this.mDesktopModeManager == null) {
            this.mDesktopModeManager = (SemDesktopModeManager) this.mTextView.getContext().getSystemService(Context.SEM_DESKTOP_MODE_SERVICE);
        }
        SemDesktopModeManager semDesktopModeManager = this.mDesktopModeManager;
        if (semDesktopModeManager != null) {
            SemDesktopModeState desktopModeState = semDesktopModeManager.getDesktopModeState();
            boolean z2 = true;
            boolean z3 = desktopModeState != null && desktopModeState.getDisplayType() == 101;
            if (!z && z3) {
                z2 = false;
            }
            this.mUseCtxMenuInDesktopMode = z2;
            return;
        }
        this.mUseCtxMenuInDesktopMode = z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Layout getActiveLayout() {
        Layout layout = this.mTextView.getLayout();
        Layout hintLayout = this.mTextView.getHintLayout();
        return (layout == null || !TextUtils.isEmpty(layout.getText()) || hintLayout == null || TextUtils.isEmpty(hintLayout.getText())) ? layout : hintLayout;
    }

    protected void stopTextActionModeFromIME() {
        ActionMode actionMode = this.mTextActionMode;
        if (actionMode != null) {
            actionMode.finish();
        }
        InsertionPointCursorController insertionPointCursorController = this.mInsertionPointCursorController;
        if (insertionPointCursorController != null) {
            insertionPointCursorController.hide();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateWritingToolkit() {
        InputMethodManager inputMethodManager = getInputMethodManager();
        if (inputMethodManager == null || !inputMethodManager.usingWritingToolkit()) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("newSelection", this.mTextView.getSelectedText());
        inputMethodManager.sendAppPrivateCommand(this.mTextView, SemInputMethodManagerUtils.ACTION_UPDATE_TOOLKIT_HBD, bundle);
    }

    void sendStartDragBroadcast() {
        this.mTextView.getContext().sendBroadcast(new Intent().setAction("com.samsung.android.intent.action.WritingToolkit.DragAndDrop").putExtra("dragAndDrop", "start"));
    }

    void sendStopDragBroadcast() {
        this.mTextView.getContext().sendBroadcast(new Intent().setAction("com.samsung.android.intent.action.WritingToolkit.DragAndDrop").putExtra("dragAndDrop", "stop"));
    }

    private boolean canPrintLagLog() {
        return Boolean.parseBoolean(SemSystemProperties.get("persist.keyboard.enable_write_lagLog", ""));
    }
}
