package android.widget;

import android.Manifest;
import android.R;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.ActivityOptions;
import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.app.SearchManager;
import android.app.compat.CompatChanges;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.UndoManager;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.FontScaleConverterFactory;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BaseCanvas;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Insets;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.text.LineBreakConfig;
import android.hardware.scontext.SContextConstants;
import android.icu.text.DecimalFormatSymbols;
import android.inputmethodservice.ExtractEditText;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.LocaleList;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.ParcelableParcel;
import android.os.Process;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.UserHandle;
import android.provider.Browser;
import android.provider.Settings;
import android.sec.enterprise.ApplicationRestrictionsManager;
import android.sec.enterprise.EnterpriseDeviceManager;
import android.sec.enterprise.RestrictionPolicy;
import android.speech.RecognizerIntent;
import android.text.BoringLayout;
import android.text.DynamicLayout;
import android.text.Editable;
import android.text.GetChars;
import android.text.GraphemeClusterSegmentFinder;
import android.text.GraphicsOperations;
import android.text.Highlights;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Layout;
import android.text.MultiSelection;
import android.text.NoCopySpan;
import android.text.ParcelableSpan;
import android.text.PrecomputedText;
import android.text.SegmentFinder;
import android.text.Selection;
import android.text.SpanWatcher;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.StaticLayout;
import android.text.TextDirectionHeuristic;
import android.text.TextDirectionHeuristics;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.WordSegmentFinder;
import android.text.method.AllCapsTransformationMethod;
import android.text.method.ArrowKeyMovementMethod;
import android.text.method.DateKeyListener;
import android.text.method.DateTimeKeyListener;
import android.text.method.DialerKeyListener;
import android.text.method.DigitsKeyListener;
import android.text.method.KeyListener;
import android.text.method.LinkMovementMethod;
import android.text.method.MetaKeyKeyListener;
import android.text.method.MovementMethod;
import android.text.method.OffsetMapping;
import android.text.method.PasswordTransformationMethod;
import android.text.method.SingleLineTransformationMethod;
import android.text.method.TextKeyListener;
import android.text.method.TimeKeyListener;
import android.text.method.TransformationMethod;
import android.text.method.TransformationMethod2;
import android.text.method.WordIterator;
import android.text.style.CharacterStyle;
import android.text.style.ClickableSpan;
import android.text.style.ParagraphStyle;
import android.text.style.ReplacementSpan;
import android.text.style.SpellCheckSpan;
import android.text.style.SuggestionSpan;
import android.text.style.URLSpan;
import android.text.style.UpdateAppearance;
import android.text.util.Linkify;
import android.util.ArraySet;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.IntArray;
import android.util.Log;
import android.util.Patterns;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.TypedValue;
import android.view.AccessibilityIterators;
import android.view.ActionMode;
import android.view.Choreographer;
import android.view.ContentInfo;
import android.view.ContextMenu;
import android.view.ContextThemeWrapper;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.HapticFeedbackConstants;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.PointerIcon;
import android.view.RemotableViewMethod;
import android.view.View;
import android.view.View$InspectionCompanion$$ExternalSyntheticLambda0;
import android.view.View$InspectionCompanion$$ExternalSyntheticLambda1;
import android.view.ViewConfiguration;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.ViewHierarchyEncoder;
import android.view.ViewRootImpl;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.AnimationUtils;
import android.view.autofill.AutofillManager;
import android.view.autofill.AutofillValue;
import android.view.autofill.Helper;
import android.view.contentcapture.ContentCaptureManager;
import android.view.contentcapture.ContentCaptureSession;
import android.view.inputmethod.BaseInputConnection;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.CorrectionInfo;
import android.view.inputmethod.CursorAnchorInfo;
import android.view.inputmethod.DeleteGesture;
import android.view.inputmethod.DeleteRangeGesture;
import android.view.inputmethod.EditorBoundsInfo;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.HandwritingGesture;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.InsertGesture;
import android.view.inputmethod.InsertModeGesture;
import android.view.inputmethod.JoinOrSplitGesture;
import android.view.inputmethod.PreviewableHandwritingGesture;
import android.view.inputmethod.RemoveSpaceGesture;
import android.view.inputmethod.SelectGesture;
import android.view.inputmethod.SelectRangeGesture;
import android.view.inputmethod.SemInputMethodManagerUtils;
import android.view.inputmethod.TextAppearanceInfo;
import android.view.inputmethod.TextBoundsInfo;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.IntFlagMapping;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import android.view.textclassifier.TextClassification;
import android.view.textclassifier.TextClassificationContext;
import android.view.textclassifier.TextClassificationManager;
import android.view.textclassifier.TextClassifier;
import android.view.textclassifier.TextLinks;
import android.view.textservice.SpellCheckerSubtype;
import android.view.textservice.TextServicesManager;
import android.view.translation.TranslationRequestValue;
import android.view.translation.UiTranslationController;
import android.view.translation.ViewTranslationCallback;
import android.view.translation.ViewTranslationRequest;
import android.widget.AccessibilityIterators;
import android.widget.Editor;
import android.widget.RemoteViews;
import com.android.internal.graphics.ColorUtils;
import com.android.internal.inputmethod.EditableInputConnection;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.nano.MetricsProto;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.FastMath;
import com.android.internal.util.Preconditions;
import com.android.text.flags.Flags;
import com.samsung.android.common.AsPackageName;
import com.samsung.android.content.clipboard.SemClipboardManager;
import com.samsung.android.content.smartclip.SemSmartClipCroppedArea;
import com.samsung.android.content.smartclip.SemSmartClipDataElement;
import com.samsung.android.content.smartclip.SemSmartClipDataRepository;
import com.samsung.android.content.smartclip.SemSmartClipMetaTag;
import com.samsung.android.content.smartclip.SemSmartClipMetaTagType;
import com.samsung.android.content.smartclip.SmartClipDataElementImpl;
import com.samsung.android.cover.ICoverManager;
import com.samsung.android.infoextraction.SemInfoExtractionManager;
import com.samsung.android.penselect.PenSelectionController;
import com.samsung.android.rune.ViewRune;
import com.samsung.android.sdk.sfe.SFText;
import com.samsung.android.sepunion.UnionConstants;
import com.samsung.android.widget.SemHoverPopupWindow;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import libcore.util.EmptyArray;
import org.xmlpull.v1.XmlPullParserException;

@RemoteViews.RemoteView
/* loaded from: classes4.dex */
public class TextView extends View implements ViewTreeObserver.OnPreDrawListener {
    static final int ACCESSIBILITY_ACTION_PROCESS_TEXT_START_ID = 268435712;
    private static final int ACCESSIBILITY_ACTION_SHARE = 268435456;
    static final int ACCESSIBILITY_ACTION_SMART_START_ID = 268439552;
    private static final String ACTION_SHOW_BOARD = "com.samsung.android.honeyboard.action.SHOW_BOARD";
    private static final String ACTION_SSS_TRANSLATE = "sec.intent.action.TRANSLATE";
    private static final int ANIMATED_SCROLL_GAP = 250;
    public static final int AUTO_SIZE_TEXT_TYPE_NONE = 0;
    public static final int AUTO_SIZE_TEXT_TYPE_UNIFORM = 1;
    private static final int AUTO_SIZE_TEXT_TYPE_UNIFORM_TWEAK = 100;
    private static final int BIXBY_TOUCH_FOUND_TEXT_MAX_LENGTH = 500;
    public static final long BORINGLAYOUT_FALLBACK_LINESPACING = 210923482;
    private static final int CHANGE_WATCHER_PRIORITY = 100;
    static final boolean DEBUG_CURSOR = false;
    static final boolean DEBUG_EXTRACT = false;
    private static final int DECIMAL = 4;
    private static final int DEFAULT_AUTO_SIZE_GRANULARITY_IN_PX = 1;
    private static final int DEFAULT_AUTO_SIZE_MAX_TEXT_SIZE_IN_SP = 112;
    private static final int DEFAULT_AUTO_SIZE_MIN_TEXT_SIZE_IN_SP = 12;
    private static final int DEFAULT_LINE_BREAK_STYLE = 0;
    private static final int DEFAULT_LINE_BREAK_WORD_STYLE = 0;
    private static final int DEFAULT_TYPEFACE = -1;
    private static final int DEVICE_PROVISIONED_NO = 1;
    private static final int DEVICE_PROVISIONED_UNKNOWN = 0;
    private static final int DEVICE_PROVISIONED_YES = 2;
    private static final int ELLIPSIZE_END = 3;
    private static final int ELLIPSIZE_MARQUEE = 4;
    private static final int ELLIPSIZE_MIDDLE = 2;
    private static final int ELLIPSIZE_NONE = 0;
    private static final int ELLIPSIZE_NOT_SET = -1;
    private static final int ELLIPSIZE_START = 1;
    private static final int EMS = 1;
    private static final String EXTRA_KEY_BOARD = "board";
    private static final String EXTRA_VALUE_BOARD_CLIPBOARD = "clipboard";
    private static final String EXTRA_VALUE_BOARD_EAGLE_EYE = "eagle_eye";
    private static final String EXTRA_VALUE_BOARD_HBD_TRANSLATE = "translation";
    private static final int FALLBACK_LINE_SPACING_ALL = 2;
    private static final int FALLBACK_LINE_SPACING_NONE = 0;
    private static final int FALLBACK_LINE_SPACING_STATIC_LAYOUT_ONLY = 1;
    private static final int FLOATING_TOOLBAR_SELECT_ALL_REFRESH_DELAY = 500;
    public static final int FOCUSED_SEARCH_RESULT_INDEX_NONE = -1;
    static final int ID_ASSIST = 16908353;
    static final int ID_AUTOFILL = 16908355;
    static final int ID_CLIPBOARD = 16908910;
    static final int ID_CLOSE = 16909092;
    static final int ID_COPY = 16908321;
    static final int ID_CUT = 16908320;
    static final int ID_DELETE = 16908993;
    static final int ID_HBD_TRANSLATE = 16909128;
    static final int ID_MANAGE_APPS = 16909292;
    static final int ID_MULTI_SELECT_ALL = 16909349;
    static final int ID_MULTI_SELECT_COPY = 16909350;
    static final int ID_MULTI_SELECT_DICTIONARY = 16909351;
    static final int ID_MULTI_SELECT_SHARE = 16909352;
    static final int ID_MULTI_SELECT_TRANSLATE = 16909353;
    static final int ID_PASTE = 16908322;
    static final int ID_PASTE_AS_PLAIN_TEXT = 16908337;
    static final int ID_REDO = 16908339;
    static final int ID_REPLACE = 16908340;
    static final int ID_SCAN_TEXT = 16909592;
    static final int ID_SELECT_ALL = 16908319;
    static final int ID_SHARE = 16908341;
    static final int ID_SSS_TRANSLATE = 16909811;
    static final int ID_UNDO = 16908338;
    static final int ID_WEBSEARCH = 16910013;
    static final int ID_WRITING_TOOLKIT = 16910028;
    private static final int KEY_DOWN_HANDLED_BY_KEY_LISTENER = 1;
    private static final int KEY_DOWN_HANDLED_BY_MOVEMENT_METHOD = 2;
    private static final int KEY_EVENT_HANDLED = -1;
    private static final int KEY_EVENT_NOT_HANDLED = 0;
    private static final int LINES = 1;
    static final String LOG_TAG = "TextView";
    private static final int MARQUEE_FADE_NORMAL = 0;
    private static final int MARQUEE_FADE_SWITCH_SHOW_ELLIPSIS = 1;
    private static final int MARQUEE_FADE_SWITCH_SHOW_FADE = 2;
    private static final int MAX_LENGTH_FOR_SINGLE_LINE_EDIT_TEXT = 5000;
    private static final int MONOSPACE = 3;
    private static final int NO_POINTER_ID = -1;
    private static final int OFFSET_MAPPING_SPAN_PRIORITY = 200;
    private static final int PIXELS = 2;
    public static final int PROCESS_TEXT_REQUEST_CODE = 100;
    private static final int SANS = 1;
    public static final int SEM_ASSIST_ID = 65536;
    public static final int SEM_AUTOFILL_ID = 131072;
    public static final int SEM_CLIPBOARD_ID = 4096;
    public static final int SEM_DICTIONARY_ID = 32768;
    public static final int SEM_SHARE_ID = 8192;
    public static final int SEM_WEBSEARCH_ID = 16384;
    private static final int SERIF = 2;
    private static final int SETTING_SOURCE_CONNECT_DEXONPC = 3;
    private static final String SETTING_SOURCE_CONNECT_DEX_ON_PC = "dexonpc_connection_state";
    private static final int SHOW_IME_WITH_HARDKEY = 1;
    private static final int SIGNED = 2;
    public static final long STATICLAYOUT_FALLBACK_LINESPACING = 37756858;
    private static final int STRIKE_ANIMATION_DURATION = 400;
    public static final int TRANSLATE_TEXT_REQUEST_CODE = 101;
    static final String TRANSLATOR_PACKAGE_NAME_GED = "com.google.android.apps.translate";
    static final String TRANSLATOR_PACKAGE_NAME_SEC = "com.sec.android.app.translator";
    static final String TRANSLATOR_PACKAGE_NAME_SSS = "com.samsung.android.app.interpreter";
    public static final BoringLayout.Metrics UNKNOWN_BORING;
    private static final float UNSET_AUTO_SIZE_UNIFORM_CONFIGURATION_VALUE = -1.0f;
    public static final long USE_BOUNDS_FOR_WIDTH = 63938206;
    static final int VERY_WIDE = 1048576;
    private static final int WRITING_TOOLKIT_REQUEST_CODE = 102;
    private static long mCurTime;
    private static boolean mIsFindTargetView;
    private static long mLastHoveredTime;
    private static int mLastHoveredViewId;
    private static long mLastPenDownTime;
    private static Runnable mShowPenSelectionRunnable;
    private static int mTargetViewId;
    static long sLastCutCopyOrTextChangedTime;
    private final int HOVER_INTERVAL;
    private float TOUCH_DELTA;
    private int mActionModeFlags;
    private boolean mAllowTransformationLengthChange;
    private boolean mAttachedWindow;
    private int mAutoLinkMask;
    private float mAutoSizeMaxTextSizeInPx;
    private float mAutoSizeMinTextSizeInPx;
    private float mAutoSizeStepGranularityInPx;
    private int[] mAutoSizeTextSizesInPx;
    private int mAutoSizeTextType;
    private BoringLayout.Metrics mBoring;
    private int mBreakStrategy;
    private BufferType mBufferType;
    private int mButtonShapeAlpha;
    private int mButtonShapeColor;
    private double mButtonShapeLuminance;
    private int mButtonShapeOutlineRadius;
    private int mButtonShapeOutlineStrokeBottom;
    private int mButtonShapeOutlineStrokeDisabled;
    private int mButtonShapeOutlineStrokeEnabled;
    private int mButtonShapeOutlineStrokeHorizontal;
    private int mButtonShapeOutlineStrokeTop;
    private Paint mButtonShapePaint;
    private RectF mButtonShapeRect;
    private boolean mButtonShapeSettingEnabled;
    private Integer mButtonShapeTextColor;
    private int mButtonShapeTextColorDark;
    private int mButtonShapeTextColorLight;
    private boolean mCanTextMultiSelection;
    private ChangeWatcher mChangeWatcher;
    private boolean mChangedSelectionBySIP;
    private CharWrapper mCharWrapper;
    private int mCurHintTextColor;

    @ViewDebug.ExportedProperty(category = "text")
    private int mCurTextColor;
    private volatile Locale mCurrentSpellCheckerLocaleCache;
    private Drawable mCursorDrawable;
    int mCursorDrawableRes;
    private float mCursorThicknessScale;
    private boolean mCursorVisibleFromAttr;
    private int mDeferScroll;
    private int mDesiredHeightAtMeasure;
    private int mDeviceProvisionedState;
    private boolean mDisableWritingToolkitMenu;
    private CharSequence mDisplayText;
    private float mDrawStrikeAnimationValue;
    private ValueAnimator mDrawTextStrikeAnimator;
    Drawables mDrawables;
    private Editable.Factory mEditableFactory;
    private Editor mEditor;
    private TextUtils.TruncateAt mEllipsize;
    private boolean mEnableLinkPreview;
    private boolean mEnableMultiSelection;
    private InputFilter[] mFilters;
    private int mFocusedSearchResultHighlightColor;
    private Paint mFocusedSearchResultHighlightPaint;
    private int mFocusedSearchResultIndex;
    private String mFontFamily;
    private int mFontWeightAdjustment;
    private boolean mFreezesText;
    private int mGesturePreviewHighlightEnd;
    private Paint mGesturePreviewHighlightPaint;
    private int mGesturePreviewHighlightStart;

    @ViewDebug.ExportedProperty(category = "text")
    private int mGravity;
    private boolean mHasPresetAutoSizeValues;
    private boolean mHideHint;
    int mHighlightColor;
    private final Paint mHighlightPaint;
    private List<Paint> mHighlightPaints;
    private Path mHighlightPath;
    private boolean mHighlightPathBogus;
    private List<Path> mHighlightPaths;
    private boolean mHighlightPathsBogus;
    private Highlights mHighlights;
    private CharSequence mHint;
    private BoringLayout.Metrics mHintBoring;
    private int mHintId;
    private Layout mHintLayout;
    private ColorStateList mHintTextColor;
    private boolean mHorizontallyScrolling;
    private long mHoverEnterTime;
    private long mHoverExitTime;
    private Object mHoveredSpan;
    private int mHyphenationFrequency;
    private boolean mImeIsConsumingInput;
    private boolean mIncludePad;
    private boolean mIsButtonShapeTarget;
    private boolean mIsNightMode;
    private boolean mIsPrimePointerFromHandleView;
    private boolean mIsShowingLinkPreview;
    private boolean mIsThemeDeviceDefault;
    boolean mIsTouchDown;
    private int mJustificationMode;
    private boolean mKeycodeDpadCenterStatus;
    private int mLastInputSource;
    private int mLastLayoutDirection;
    private int mLastOrientation;
    private long mLastScroll;
    private Layout mLayout;
    private int mLineBreakStyle;
    private int mLineBreakWordStyle;
    private int mLineHeightComplexDimen;
    private boolean mLineIsDrawed;
    private ColorStateList mLinkTextColor;
    private boolean mLinksClickable;
    private boolean mListenerChanged;
    private ArrayList<TextWatcher> mListeners;
    private Paint.FontMetrics mLocalePreferredFontMetrics;
    private boolean mLocalesChanged;
    private Marquee mMarquee;
    private int mMarqueeFadeMode;
    private int mMarqueeRepeatLimit;
    private int mMaxMode;
    private int mMaxWidth;
    private int mMaxWidthMode;
    private int mMaximum;
    private int mMinMode;
    private int mMinWidth;
    private int mMinWidthMode;
    private int mMinimum;
    private Paint.FontMetrics mMinimumFontMetrics;
    private MovementMethod mMovement;
    private int mMultiHighlightColor;
    private Paint mMultiHighlightPaint;
    private MultiSelectPopupWindow mMultiSelectPopupWindow;
    private boolean mNeedsAutoSizeText;
    private int mOldMaxMode;
    private int mOldMaximum;
    private Typeface mOriginalTypeface;
    private final List<Path> mPathRecyclePool;
    private PenSelectionController mPenSelectionController;
    private boolean mPreDrawListenerDetached;
    private boolean mPreDrawRegistered;
    private PrecomputedText mPrecomputed;
    private int mPrevSelectionEndForSSS;
    private int mPrevSelectionStartForSSS;
    private boolean mPreventDefaultMovement;
    private int mPrimePointerId;
    private boolean mRestartMarquee;
    private RestrictionPolicy mRestrictionPolicy;
    private BoringLayout mSavedHintLayout;
    private BoringLayout mSavedLayout;
    private Layout mSavedMarqueeModeLayout;
    private Scroller mScroller;
    private int mSearchResultHighlightColor;
    private Paint mSearchResultHighlightPaint;
    private int[] mSearchResultHighlights;
    private SemClipboardManager mSemClipboardManager;
    private int mShadowColor;
    private float mShadowDx;
    private float mShadowDy;
    private float mShadowRadius;
    private boolean mShiftDrawingOffsetForStartOverhang;
    private boolean mSingleLine;
    private InputFilter.LengthFilter mSingleLineLengthFilter;
    private boolean mSkipUpdateDisplayText;
    private float mSpacingAdd;
    private float mSpacingMult;
    private Spannable mSpannable;
    private Spannable.Factory mSpannableFactory;
    private Paint mStrikeThroughPaint;
    private StylusEventListener mStylusEventListener;
    private Object mTempCursor;
    private Rect mTempRect;
    private TextPaint mTempTextPaint;

    @ViewDebug.ExportedProperty(category = "text")
    private CharSequence mText;
    private TextClassificationContext mTextClassificationContext;
    private TextClassifier mTextClassificationSession;
    private TextClassifier mTextClassifier;
    private ColorStateList mTextColor;
    private TextDirectionHeuristic mTextDir;
    int mTextEditSuggestionContainerLayout;
    int mTextEditSuggestionHighlightStyle;
    int mTextEditSuggestionItemLayout;
    private SFText mTextEffect;
    private int mTextEffectLines;
    private int mTextId;
    private UserHandle mTextOperationUser;
    private final TextPaint mTextPaint;
    private Drawable mTextSelectHandle;
    private Drawable mTextSelectHandleLeft;
    int mTextSelectHandleLeftRes;
    int mTextSelectHandleRes;
    private Drawable mTextSelectHandleRight;
    int mTextSelectHandleRightRes;
    private boolean mTextSetFromXmlOrResourceId;
    private int mTextSizeUnit;
    private boolean mTextStrikeThroughEnabled;
    private TransformationMethod mTransformation;
    private CharSequence mTransformed;
    private boolean mUseBoundsForWidth;
    private boolean mUseDisplayText;
    private int mUseFallbackLineSpacing;
    private final boolean mUseInternationalizedInput;
    private boolean mUseLocalePreferredLineHeightForMinimum;
    private final boolean mUseTextPaddingForUiTranslation;
    private boolean mUserSetTextScaleX;
    private Pattern mWhitespacePattern;
    private WordIterator mWordIteratorForMultiSelection;
    private boolean mhasMultiSelection;
    private static final float[] TEMP_POSITION = new float[2];
    private static final RectF TEMP_RECTF = new RectF();
    private static final InputFilter[] NO_FILTERS = new InputFilter[0];
    private static final Spanned EMPTY_SPANNED = new SpannedString("");
    private static final int[] MULTILINE_STATE_SET = {16843597};
    private static final Pattern urlPattern = Patterns.WEB_URL;
    private static final Pattern emailPattern = Patterns.EMAIL_ADDRESS;
    private static ViewRootImpl.MotionEventMonitor.OnTouchListener mMotionEventMonitorListener = null;
    private static final SparseIntArray sAppearanceValues = new SparseIntArray();

    @Retention(RetentionPolicy.SOURCE)
    public @interface AutoSizeTextType {
    }

    public enum BufferType {
        NORMAL,
        SPANNABLE,
        EDITABLE
    }

    public interface OnEditorActionListener {
        boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface XMLTypefaceAttr {
    }

    public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<TextView> {
        private int mAutoLinkId;
        private int mAutoSizeMaxTextSizeId;
        private int mAutoSizeMinTextSizeId;
        private int mAutoSizeStepGranularityId;
        private int mAutoSizeTextTypeId;
        private int mBreakStrategyId;
        private int mCursorVisibleId;
        private int mDrawableBlendModeId;
        private int mDrawablePaddingId;
        private int mDrawableTintId;
        private int mDrawableTintModeId;
        private int mElegantTextHeightId;
        private int mEllipsizeId;
        private int mFallbackLineSpacingId;
        private int mFirstBaselineToTopHeightId;
        private int mFontFeatureSettingsId;
        private int mFreezesTextId;
        private int mGravityId;
        private int mHintId;
        private int mHyphenationFrequencyId;
        private int mImeActionIdId;
        private int mImeActionLabelId;
        private int mImeOptionsId;
        private int mIncludeFontPaddingId;
        private int mInputTypeId;
        private int mJustificationModeId;
        private int mLastBaselineToBottomHeightId;
        private int mLetterSpacingId;
        private int mLineHeightId;
        private int mLineSpacingExtraId;
        private int mLineSpacingMultiplierId;
        private int mLinksClickableId;
        private int mMarqueeRepeatLimitId;
        private int mMaxEmsId;
        private int mMaxHeightId;
        private int mMaxLinesId;
        private int mMaxWidthId;
        private int mMinEmsId;
        private int mMinLinesId;
        private int mMinWidthId;
        private int mPrivateImeOptionsId;
        private boolean mPropertiesMapped = false;
        private int mScrollHorizontallyId;
        private int mShadowColorId;
        private int mShadowDxId;
        private int mShadowDyId;
        private int mShadowRadiusId;
        private int mSingleLineId;
        private int mTextAllCapsId;
        private int mTextColorHighlightId;
        private int mTextColorHintId;
        private int mTextColorId;
        private int mTextColorLinkId;
        private int mTextId;
        private int mTextIsSelectableId;
        private int mTextScaleXId;
        private int mTextSizeId;
        private int mTypefaceId;

        @Override // android.view.inspector.InspectionCompanion
        public void mapProperties(PropertyMapper propertyMapper) {
            IntFlagMapping autoLinkFlagMapping = new IntFlagMapping();
            autoLinkFlagMapping.add(2, 2, "email");
            autoLinkFlagMapping.add(8, 8, "map");
            autoLinkFlagMapping.add(4, 4, "phone");
            autoLinkFlagMapping.add(1, 1, SemSmartClipDataRepository.CONTENT_TYPE_WEB);
            Objects.requireNonNull(autoLinkFlagMapping);
            this.mAutoLinkId = propertyMapper.mapIntFlag("autoLink", 16842928, new View$InspectionCompanion$$ExternalSyntheticLambda1(autoLinkFlagMapping));
            this.mAutoSizeMaxTextSizeId = propertyMapper.mapInt("autoSizeMaxTextSize", 16844102);
            this.mAutoSizeMinTextSizeId = propertyMapper.mapInt("autoSizeMinTextSize", 16844088);
            this.mAutoSizeStepGranularityId = propertyMapper.mapInt("autoSizeStepGranularity", 16844086);
            SparseArray<String> autoSizeTextTypeEnumMapping = new SparseArray<>();
            autoSizeTextTypeEnumMapping.put(0, "none");
            autoSizeTextTypeEnumMapping.put(1, "uniform");
            Objects.requireNonNull(autoSizeTextTypeEnumMapping);
            this.mAutoSizeTextTypeId = propertyMapper.mapIntEnum("autoSizeTextType", 16844085, new View$InspectionCompanion$$ExternalSyntheticLambda0(autoSizeTextTypeEnumMapping));
            SparseArray<String> breakStrategyEnumMapping = new SparseArray<>();
            breakStrategyEnumMapping.put(0, "simple");
            breakStrategyEnumMapping.put(1, "high_quality");
            breakStrategyEnumMapping.put(2, RecognizerIntent.LANGUAGE_SWITCH_BALANCED);
            Objects.requireNonNull(breakStrategyEnumMapping);
            this.mBreakStrategyId = propertyMapper.mapIntEnum("breakStrategy", 16843997, new View$InspectionCompanion$$ExternalSyntheticLambda0(breakStrategyEnumMapping));
            this.mCursorVisibleId = propertyMapper.mapBoolean("cursorVisible", 16843090);
            this.mDrawableBlendModeId = propertyMapper.mapObject("drawableBlendMode", 80);
            this.mDrawablePaddingId = propertyMapper.mapInt("drawablePadding", 16843121);
            this.mDrawableTintId = propertyMapper.mapObject("drawableTint", 16843990);
            this.mDrawableTintModeId = propertyMapper.mapObject("drawableTintMode", 16843991);
            this.mElegantTextHeightId = propertyMapper.mapBoolean("elegantTextHeight", 16843869);
            this.mEllipsizeId = propertyMapper.mapObject("ellipsize", 16842923);
            this.mFallbackLineSpacingId = propertyMapper.mapBoolean("fallbackLineSpacing", 16844155);
            this.mFirstBaselineToTopHeightId = propertyMapper.mapInt("firstBaselineToTopHeight", 16844157);
            this.mFontFeatureSettingsId = propertyMapper.mapObject("fontFeatureSettings", 16843959);
            this.mFreezesTextId = propertyMapper.mapBoolean("freezesText", 16843116);
            this.mGravityId = propertyMapper.mapGravity("gravity", 16842927);
            this.mHintId = propertyMapper.mapObject("hint", 16843088);
            SparseArray<String> hyphenationFrequencyEnumMapping = new SparseArray<>();
            hyphenationFrequencyEnumMapping.put(0, "none");
            hyphenationFrequencyEnumMapping.put(1, "normal");
            hyphenationFrequencyEnumMapping.put(2, "full");
            Objects.requireNonNull(hyphenationFrequencyEnumMapping);
            this.mHyphenationFrequencyId = propertyMapper.mapIntEnum("hyphenationFrequency", 16843998, new View$InspectionCompanion$$ExternalSyntheticLambda0(hyphenationFrequencyEnumMapping));
            this.mImeActionIdId = propertyMapper.mapInt("imeActionId", 16843366);
            this.mImeActionLabelId = propertyMapper.mapObject("imeActionLabel", 16843365);
            IntFlagMapping imeOptionsFlagMapping = new IntFlagMapping();
            imeOptionsFlagMapping.add(255, 6, "actionDone");
            imeOptionsFlagMapping.add(255, 2, "actionGo");
            imeOptionsFlagMapping.add(255, 5, "actionNext");
            imeOptionsFlagMapping.add(255, 1, "actionNone");
            imeOptionsFlagMapping.add(255, 7, "actionPrevious");
            imeOptionsFlagMapping.add(255, 3, "actionSearch");
            imeOptionsFlagMapping.add(255, 4, "actionSend");
            imeOptionsFlagMapping.add(255, 0, "actionUnspecified");
            imeOptionsFlagMapping.add(Integer.MIN_VALUE, Integer.MIN_VALUE, "flagForceAscii");
            imeOptionsFlagMapping.add(134217728, 134217728, "flagNavigateNext");
            imeOptionsFlagMapping.add(67108864, 67108864, "flagNavigatePrevious");
            imeOptionsFlagMapping.add(536870912, 536870912, "flagNoAccessoryAction");
            imeOptionsFlagMapping.add(1073741824, 1073741824, "flagNoEnterAction");
            imeOptionsFlagMapping.add(268435456, 268435456, "flagNoExtractUi");
            imeOptionsFlagMapping.add(33554432, 33554432, "flagNoFullscreen");
            imeOptionsFlagMapping.add(16777216, 16777216, "flagNoPersonalizedLearning");
            imeOptionsFlagMapping.add(-1, 0, "normal");
            Objects.requireNonNull(imeOptionsFlagMapping);
            this.mImeOptionsId = propertyMapper.mapIntFlag("imeOptions", 16843364, new View$InspectionCompanion$$ExternalSyntheticLambda1(imeOptionsFlagMapping));
            this.mIncludeFontPaddingId = propertyMapper.mapBoolean("includeFontPadding", 16843103);
            IntFlagMapping inputTypeFlagMapping = new IntFlagMapping();
            inputTypeFlagMapping.add(4095, 20, "date");
            inputTypeFlagMapping.add(4095, 4, TextClassifier.TYPE_DATE_TIME);
            inputTypeFlagMapping.add(-1, 0, "none");
            inputTypeFlagMapping.add(4095, 2, "number");
            inputTypeFlagMapping.add(16773135, 8194, "numberDecimal");
            inputTypeFlagMapping.add(4095, 18, "numberPassword");
            inputTypeFlagMapping.add(16773135, 4098, "numberSigned");
            inputTypeFlagMapping.add(4095, 3, "phone");
            inputTypeFlagMapping.add(4095, 1, "text");
            inputTypeFlagMapping.add(16773135, 65537, "textAutoComplete");
            inputTypeFlagMapping.add(16773135, 32769, "textAutoCorrect");
            inputTypeFlagMapping.add(16773135, 4097, "textCapCharacters");
            inputTypeFlagMapping.add(16773135, 16385, "textCapSentences");
            inputTypeFlagMapping.add(16773135, 8193, "textCapWords");
            inputTypeFlagMapping.add(4095, 33, "textEmailAddress");
            inputTypeFlagMapping.add(4095, 49, "textEmailSubject");
            inputTypeFlagMapping.add(4095, 177, "textFilter");
            inputTypeFlagMapping.add(16773135, 262145, "textImeMultiLine");
            inputTypeFlagMapping.add(4095, 81, "textLongMessage");
            inputTypeFlagMapping.add(16773135, 131073, "textMultiLine");
            inputTypeFlagMapping.add(16773135, 524289, "textNoSuggestions");
            inputTypeFlagMapping.add(4095, 129, "textPassword");
            inputTypeFlagMapping.add(4095, 97, "textPersonName");
            inputTypeFlagMapping.add(4095, 193, "textPhonetic");
            inputTypeFlagMapping.add(4095, 113, "textPostalAddress");
            inputTypeFlagMapping.add(4095, 65, "textShortMessage");
            inputTypeFlagMapping.add(4095, 17, "textUri");
            inputTypeFlagMapping.add(4095, 145, "textVisiblePassword");
            inputTypeFlagMapping.add(4095, 161, "textWebEditText");
            inputTypeFlagMapping.add(4095, 209, "textWebEmailAddress");
            inputTypeFlagMapping.add(4095, 225, "textWebPassword");
            inputTypeFlagMapping.add(4095, 36, "time");
            Objects.requireNonNull(inputTypeFlagMapping);
            this.mInputTypeId = propertyMapper.mapIntFlag("inputType", 16843296, new View$InspectionCompanion$$ExternalSyntheticLambda1(inputTypeFlagMapping));
            SparseArray<String> justificationModeEnumMapping = new SparseArray<>();
            justificationModeEnumMapping.put(0, "none");
            justificationModeEnumMapping.put(1, "inter_word");
            Objects.requireNonNull(justificationModeEnumMapping);
            this.mJustificationModeId = propertyMapper.mapIntEnum("justificationMode", 16844135, new View$InspectionCompanion$$ExternalSyntheticLambda0(justificationModeEnumMapping));
            this.mLastBaselineToBottomHeightId = propertyMapper.mapInt("lastBaselineToBottomHeight", 16844158);
            this.mLetterSpacingId = propertyMapper.mapFloat("letterSpacing", 16843958);
            this.mLineHeightId = propertyMapper.mapInt("lineHeight", 16844159);
            this.mLineSpacingExtraId = propertyMapper.mapFloat("lineSpacingExtra", 16843287);
            this.mLineSpacingMultiplierId = propertyMapper.mapFloat("lineSpacingMultiplier", 16843288);
            this.mLinksClickableId = propertyMapper.mapBoolean("linksClickable", 16842929);
            this.mMarqueeRepeatLimitId = propertyMapper.mapInt("marqueeRepeatLimit", 16843293);
            this.mMaxEmsId = propertyMapper.mapInt("maxEms", 16843095);
            this.mMaxHeightId = propertyMapper.mapInt("maxHeight", 16843040);
            this.mMaxLinesId = propertyMapper.mapInt("maxLines", 16843091);
            this.mMaxWidthId = propertyMapper.mapInt("maxWidth", 16843039);
            this.mMinEmsId = propertyMapper.mapInt("minEms", 16843098);
            this.mMinLinesId = propertyMapper.mapInt("minLines", 16843094);
            this.mMinWidthId = propertyMapper.mapInt("minWidth", 16843071);
            this.mPrivateImeOptionsId = propertyMapper.mapObject("privateImeOptions", 16843299);
            this.mScrollHorizontallyId = propertyMapper.mapBoolean("scrollHorizontally", 16843099);
            this.mShadowColorId = propertyMapper.mapColor("shadowColor", 16843105);
            this.mShadowDxId = propertyMapper.mapFloat("shadowDx", 16843106);
            this.mShadowDyId = propertyMapper.mapFloat("shadowDy", 16843107);
            this.mShadowRadiusId = propertyMapper.mapFloat("shadowRadius", 16843108);
            this.mSingleLineId = propertyMapper.mapBoolean("singleLine", 16843101);
            this.mTextId = propertyMapper.mapObject("text", 16843087);
            this.mTextAllCapsId = propertyMapper.mapBoolean("textAllCaps", 16843660);
            this.mTextColorId = propertyMapper.mapObject("textColor", 16842904);
            this.mTextColorHighlightId = propertyMapper.mapColor("textColorHighlight", 16842905);
            this.mTextColorHintId = propertyMapper.mapObject("textColorHint", 16842906);
            this.mTextColorLinkId = propertyMapper.mapObject("textColorLink", 16842907);
            this.mTextIsSelectableId = propertyMapper.mapBoolean("textIsSelectable", 16843542);
            this.mTextScaleXId = propertyMapper.mapFloat("textScaleX", 16843089);
            this.mTextSizeId = propertyMapper.mapFloat("textSize", 16842901);
            this.mTypefaceId = propertyMapper.mapObject("typeface", 16842902);
            this.mPropertiesMapped = true;
        }

        @Override // android.view.inspector.InspectionCompanion
        public void readProperties(TextView node, PropertyReader propertyReader) {
            if (!this.mPropertiesMapped) {
                throw new InspectionCompanion.UninitializedPropertyMapException();
            }
            propertyReader.readIntFlag(this.mAutoLinkId, node.getAutoLinkMask());
            propertyReader.readInt(this.mAutoSizeMaxTextSizeId, node.getAutoSizeMaxTextSize());
            propertyReader.readInt(this.mAutoSizeMinTextSizeId, node.getAutoSizeMinTextSize());
            propertyReader.readInt(this.mAutoSizeStepGranularityId, node.getAutoSizeStepGranularity());
            propertyReader.readIntEnum(this.mAutoSizeTextTypeId, node.getAutoSizeTextType());
            propertyReader.readIntEnum(this.mBreakStrategyId, node.getBreakStrategy());
            propertyReader.readBoolean(this.mCursorVisibleId, node.isCursorVisible());
            propertyReader.readObject(this.mDrawableBlendModeId, node.getCompoundDrawableTintBlendMode());
            propertyReader.readInt(this.mDrawablePaddingId, node.getCompoundDrawablePadding());
            propertyReader.readObject(this.mDrawableTintId, node.getCompoundDrawableTintList());
            propertyReader.readObject(this.mDrawableTintModeId, node.getCompoundDrawableTintMode());
            propertyReader.readBoolean(this.mElegantTextHeightId, node.isElegantTextHeight());
            propertyReader.readObject(this.mEllipsizeId, node.getEllipsize());
            propertyReader.readBoolean(this.mFallbackLineSpacingId, node.isFallbackLineSpacing());
            propertyReader.readInt(this.mFirstBaselineToTopHeightId, node.getFirstBaselineToTopHeight());
            propertyReader.readObject(this.mFontFeatureSettingsId, node.getFontFeatureSettings());
            propertyReader.readBoolean(this.mFreezesTextId, node.getFreezesText());
            propertyReader.readGravity(this.mGravityId, node.getGravity());
            propertyReader.readObject(this.mHintId, node.getHint());
            propertyReader.readIntEnum(this.mHyphenationFrequencyId, node.getHyphenationFrequency());
            propertyReader.readInt(this.mImeActionIdId, node.getImeActionId());
            propertyReader.readObject(this.mImeActionLabelId, node.getImeActionLabel());
            propertyReader.readIntFlag(this.mImeOptionsId, node.getImeOptions());
            propertyReader.readBoolean(this.mIncludeFontPaddingId, node.getIncludeFontPadding());
            propertyReader.readIntFlag(this.mInputTypeId, node.getInputType());
            propertyReader.readIntEnum(this.mJustificationModeId, node.getJustificationMode());
            propertyReader.readInt(this.mLastBaselineToBottomHeightId, node.getLastBaselineToBottomHeight());
            propertyReader.readFloat(this.mLetterSpacingId, node.getLetterSpacing());
            propertyReader.readInt(this.mLineHeightId, node.getLineHeight());
            propertyReader.readFloat(this.mLineSpacingExtraId, node.getLineSpacingExtra());
            propertyReader.readFloat(this.mLineSpacingMultiplierId, node.getLineSpacingMultiplier());
            propertyReader.readBoolean(this.mLinksClickableId, node.getLinksClickable());
            propertyReader.readInt(this.mMarqueeRepeatLimitId, node.getMarqueeRepeatLimit());
            propertyReader.readInt(this.mMaxEmsId, node.getMaxEms());
            propertyReader.readInt(this.mMaxHeightId, node.getMaxHeight());
            propertyReader.readInt(this.mMaxLinesId, node.getMaxLines());
            propertyReader.readInt(this.mMaxWidthId, node.getMaxWidth());
            propertyReader.readInt(this.mMinEmsId, node.getMinEms());
            propertyReader.readInt(this.mMinLinesId, node.getMinLines());
            propertyReader.readInt(this.mMinWidthId, node.getMinWidth());
            propertyReader.readObject(this.mPrivateImeOptionsId, node.getPrivateImeOptions());
            propertyReader.readBoolean(this.mScrollHorizontallyId, node.isHorizontallyScrollable());
            propertyReader.readColor(this.mShadowColorId, node.getShadowColor());
            propertyReader.readFloat(this.mShadowDxId, node.getShadowDx());
            propertyReader.readFloat(this.mShadowDyId, node.getShadowDy());
            propertyReader.readFloat(this.mShadowRadiusId, node.getShadowRadius());
            propertyReader.readBoolean(this.mSingleLineId, node.isSingleLine());
            propertyReader.readObject(this.mTextId, node.getText());
            propertyReader.readBoolean(this.mTextAllCapsId, node.isAllCaps());
            propertyReader.readObject(this.mTextColorId, node.getTextColors());
            propertyReader.readColor(this.mTextColorHighlightId, node.getHighlightColor());
            propertyReader.readObject(this.mTextColorHintId, node.getHintTextColors());
            propertyReader.readObject(this.mTextColorLinkId, node.getLinkTextColors());
            propertyReader.readBoolean(this.mTextIsSelectableId, node.isTextSelectable());
            propertyReader.readFloat(this.mTextScaleXId, node.getTextScaleX());
            propertyReader.readFloat(this.mTextSizeId, node.getTextSize());
            propertyReader.readObject(this.mTypefaceId, node.getTypeface());
        }
    }

    static {
        sAppearanceValues.put(6, 4);
        sAppearanceValues.put(99, 22);
        sAppearanceValues.put(100, 23);
        sAppearanceValues.put(5, 3);
        sAppearanceValues.put(7, 5);
        sAppearanceValues.put(8, 6);
        sAppearanceValues.put(2, 0);
        sAppearanceValues.put(96, 19);
        sAppearanceValues.put(3, 1);
        sAppearanceValues.put(75, 12);
        sAppearanceValues.put(4, 2);
        sAppearanceValues.put(95, 18);
        sAppearanceValues.put(72, 11);
        sAppearanceValues.put(36, 7);
        sAppearanceValues.put(37, 8);
        sAppearanceValues.put(38, 9);
        sAppearanceValues.put(39, 10);
        sAppearanceValues.put(76, 13);
        sAppearanceValues.put(91, 17);
        sAppearanceValues.put(77, 14);
        sAppearanceValues.put(78, 15);
        sAppearanceValues.put(90, 16);
        sAppearanceValues.put(97, 20);
        sAppearanceValues.put(98, 21);
        UNKNOWN_BORING = new BoringLayout.Metrics();
        mLastHoveredViewId = -1;
        mTargetViewId = -1;
        mIsFindTargetView = false;
        mLastHoveredTime = 0L;
        mLastPenDownTime = 0L;
        mCurTime = 0L;
        mShowPenSelectionRunnable = null;
    }

    static class Drawables {
        static final int BOTTOM = 3;
        static final int DRAWABLE_LEFT = 1;
        static final int DRAWABLE_NONE = -1;
        static final int DRAWABLE_RIGHT = 0;
        static final int LEFT = 0;
        static final int RIGHT = 2;
        static final int TOP = 1;
        BlendMode mBlendMode;
        Drawable mDrawableEnd;
        Drawable mDrawableError;
        int mDrawableHeightEnd;
        int mDrawableHeightError;
        int mDrawableHeightLeft;
        int mDrawableHeightRight;
        int mDrawableHeightStart;
        int mDrawableHeightTemp;
        Drawable mDrawableLeftInitial;
        int mDrawablePadding;
        Drawable mDrawableRightInitial;
        int mDrawableSizeBottom;
        int mDrawableSizeEnd;
        int mDrawableSizeError;
        int mDrawableSizeLeft;
        int mDrawableSizeRight;
        int mDrawableSizeStart;
        int mDrawableSizeTemp;
        int mDrawableSizeTop;
        Drawable mDrawableStart;
        Drawable mDrawableTemp;
        int mDrawableWidthBottom;
        int mDrawableWidthTop;
        boolean mHasTint;
        boolean mHasTintMode;
        boolean mIsRtlCompatibilityMode;
        boolean mOverride;
        ColorStateList mTintList;
        final Rect mCompoundRect = new Rect();
        final Drawable[] mShowing = new Drawable[4];
        int mDrawableSaved = -1;

        public Drawables(Context context) {
            int targetSdkVersion = context.getApplicationInfo().targetSdkVersion;
            this.mIsRtlCompatibilityMode = targetSdkVersion < 17 || !context.getApplicationInfo().hasRtlSupport();
            this.mOverride = false;
        }

        public boolean hasMetadata() {
            return this.mDrawablePadding != 0 || this.mHasTintMode || this.mHasTint;
        }

        public boolean resolveWithLayoutDirection(int layoutDirection) {
            Drawable previousLeft = this.mShowing[0];
            Drawable previousRight = this.mShowing[2];
            this.mShowing[0] = this.mDrawableLeftInitial;
            this.mShowing[2] = this.mDrawableRightInitial;
            if (this.mIsRtlCompatibilityMode) {
                if (this.mDrawableStart != null && this.mShowing[0] == null) {
                    this.mShowing[0] = this.mDrawableStart;
                    this.mDrawableSizeLeft = this.mDrawableSizeStart;
                    this.mDrawableHeightLeft = this.mDrawableHeightStart;
                }
                if (this.mDrawableEnd != null && this.mShowing[2] == null) {
                    this.mShowing[2] = this.mDrawableEnd;
                    this.mDrawableSizeRight = this.mDrawableSizeEnd;
                    this.mDrawableHeightRight = this.mDrawableHeightEnd;
                }
            } else {
                switch (layoutDirection) {
                    case 1:
                        if (this.mOverride) {
                            this.mShowing[2] = this.mDrawableStart;
                            this.mDrawableSizeRight = this.mDrawableSizeStart;
                            this.mDrawableHeightRight = this.mDrawableHeightStart;
                            this.mShowing[0] = this.mDrawableEnd;
                            this.mDrawableSizeLeft = this.mDrawableSizeEnd;
                            this.mDrawableHeightLeft = this.mDrawableHeightEnd;
                            break;
                        }
                        break;
                    default:
                        if (this.mOverride) {
                            this.mShowing[0] = this.mDrawableStart;
                            this.mDrawableSizeLeft = this.mDrawableSizeStart;
                            this.mDrawableHeightLeft = this.mDrawableHeightStart;
                            this.mShowing[2] = this.mDrawableEnd;
                            this.mDrawableSizeRight = this.mDrawableSizeEnd;
                            this.mDrawableHeightRight = this.mDrawableHeightEnd;
                            break;
                        }
                        break;
                }
            }
            applyErrorDrawableIfNeeded(layoutDirection);
            return (this.mShowing[0] == previousLeft && this.mShowing[2] == previousRight) ? false : true;
        }

        public void setErrorDrawable(Drawable dr, TextView tv) {
            if (this.mDrawableError != dr && this.mDrawableError != null) {
                this.mDrawableError.setCallback(null);
            }
            this.mDrawableError = dr;
            if (this.mDrawableError != null) {
                Rect compoundRect = this.mCompoundRect;
                int[] state = tv.getDrawableState();
                this.mDrawableError.setState(state);
                this.mDrawableError.copyBounds(compoundRect);
                this.mDrawableError.setCallback(tv);
                this.mDrawableSizeError = compoundRect.width();
                this.mDrawableHeightError = compoundRect.height();
                return;
            }
            this.mDrawableHeightError = 0;
            this.mDrawableSizeError = 0;
        }

        private void applyErrorDrawableIfNeeded(int layoutDirection) {
            switch (this.mDrawableSaved) {
                case 0:
                    this.mShowing[2] = this.mDrawableTemp;
                    this.mDrawableSizeRight = this.mDrawableSizeTemp;
                    this.mDrawableHeightRight = this.mDrawableHeightTemp;
                    break;
                case 1:
                    this.mShowing[0] = this.mDrawableTemp;
                    this.mDrawableSizeLeft = this.mDrawableSizeTemp;
                    this.mDrawableHeightLeft = this.mDrawableHeightTemp;
                    break;
            }
            if (this.mDrawableError != null) {
                switch (layoutDirection) {
                    case 1:
                        this.mDrawableSaved = 1;
                        this.mDrawableTemp = this.mShowing[0];
                        this.mDrawableSizeTemp = this.mDrawableSizeLeft;
                        this.mDrawableHeightTemp = this.mDrawableHeightLeft;
                        this.mShowing[0] = this.mDrawableError;
                        this.mDrawableSizeLeft = this.mDrawableSizeError;
                        this.mDrawableHeightLeft = this.mDrawableHeightError;
                        break;
                    default:
                        this.mDrawableSaved = 0;
                        this.mDrawableTemp = this.mShowing[2];
                        this.mDrawableSizeTemp = this.mDrawableSizeRight;
                        this.mDrawableHeightTemp = this.mDrawableHeightRight;
                        this.mShowing[2] = this.mDrawableError;
                        this.mDrawableSizeRight = this.mDrawableSizeError;
                        this.mDrawableHeightRight = this.mDrawableHeightError;
                        break;
                }
            }
        }
    }

    public static void preloadFontCache() {
    }

    public TextView(Context context) {
        this(context, null);
    }

    public TextView(Context context, AttributeSet attrs) {
        this(context, attrs, 16842884);
    }

    public TextView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    /* JADX WARN: Removed duplicated region for block: B:205:0x13ab  */
    /* JADX WARN: Removed duplicated region for block: B:207:0x13c3  */
    /* JADX WARN: Removed duplicated region for block: B:222:0x1442  */
    /* JADX WARN: Removed duplicated region for block: B:227:0x1461  */
    /* JADX WARN: Removed duplicated region for block: B:228:0x1467  */
    /* JADX WARN: Removed duplicated region for block: B:229:0x146d  */
    /* JADX WARN: Removed duplicated region for block: B:237:0x14a8  */
    /* JADX WARN: Removed duplicated region for block: B:246:0x14d5  */
    /* JADX WARN: Removed duplicated region for block: B:249:0x1502  */
    /* JADX WARN: Removed duplicated region for block: B:252:0x1511  */
    /* JADX WARN: Removed duplicated region for block: B:254:0x1515  */
    /* JADX WARN: Removed duplicated region for block: B:257:0x151c  */
    /* JADX WARN: Removed duplicated region for block: B:273:0x1568  */
    /* JADX WARN: Removed duplicated region for block: B:276:0x1585  */
    /* JADX WARN: Removed duplicated region for block: B:303:0x15ee  */
    /* JADX WARN: Removed duplicated region for block: B:306:0x15fb  */
    /* JADX WARN: Removed duplicated region for block: B:309:0x1602  */
    /* JADX WARN: Removed duplicated region for block: B:312:0x160d  */
    /* JADX WARN: Removed duplicated region for block: B:315:0x1619  */
    /* JADX WARN: Removed duplicated region for block: B:335:0x166f  */
    /* JADX WARN: Removed duplicated region for block: B:337:0x1674  */
    /* JADX WARN: Removed duplicated region for block: B:339:0x167e  */
    /* JADX WARN: Removed duplicated region for block: B:342:0x168a  */
    /* JADX WARN: Removed duplicated region for block: B:349:0x169b  */
    /* JADX WARN: Removed duplicated region for block: B:350:0x1684  */
    /* JADX WARN: Removed duplicated region for block: B:351:0x167a  */
    /* JADX WARN: Removed duplicated region for block: B:352:0x1665  */
    /* JADX WARN: Removed duplicated region for block: B:353:0x1612  */
    /* JADX WARN: Removed duplicated region for block: B:357:0x1509  */
    /* JADX WARN: Removed duplicated region for block: B:358:0x14e2  */
    /* JADX WARN: Removed duplicated region for block: B:368:0x13e4  */
    /* JADX WARN: Removed duplicated region for block: B:370:0x13ed  */
    /* JADX WARN: Removed duplicated region for block: B:372:0x13fe  */
    /* JADX WARN: Removed duplicated region for block: B:373:0x13f9  */
    /* JADX WARN: Removed duplicated region for block: B:374:0x13d4  */
    /* JADX WARN: Removed duplicated region for block: B:375:0x13b9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public TextView(android.content.Context r83, android.util.AttributeSet r84, int r85, int r86) {
        /*
            Method dump skipped, instructions count: 6064
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.TextView.<init>(android.content.Context, android.util.AttributeSet, int, int):void");
    }

    private void setTextInternal(CharSequence text) {
        this.mText = text;
        this.mSpannable = text instanceof Spannable ? (Spannable) text : null;
        this.mPrecomputed = text instanceof PrecomputedText ? (PrecomputedText) text : null;
    }

    public void setAutoSizeTextTypeWithDefaults(int autoSizeTextType) {
        if (autoSizeTextType != 100 && supportsAutoSizeText()) {
            switch (autoSizeTextType) {
                case 0:
                    clearAutoSizeConfiguration();
                    return;
                case 1:
                    DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
                    float autoSizeMinTextSizeInPx = TypedValue.applyDimension(2, 12.0f, displayMetrics);
                    float autoSizeMaxTextSizeInPx = TypedValue.applyDimension(2, 112.0f, displayMetrics);
                    validateAndSetAutoSizeTextTypeUniformConfiguration(autoSizeMinTextSizeInPx, autoSizeMaxTextSizeInPx, 1.0f);
                    if (setupAutoSizeText()) {
                        autoSizeText();
                        invalidate();
                        return;
                    }
                    return;
                default:
                    throw new IllegalArgumentException("Unknown auto-size text type: " + autoSizeTextType);
            }
        }
    }

    public void setAutoSizeTextTypeUniformWithConfiguration(int autoSizeMinTextSize, int autoSizeMaxTextSize, int autoSizeStepGranularity, int unit) {
        if (supportsAutoSizeText()) {
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            float autoSizeMinTextSizeInPx = TypedValue.applyDimension(unit, autoSizeMinTextSize, displayMetrics);
            float autoSizeMaxTextSizeInPx = TypedValue.applyDimension(unit, autoSizeMaxTextSize, displayMetrics);
            float autoSizeStepGranularityInPx = TypedValue.applyDimension(unit, autoSizeStepGranularity, displayMetrics);
            validateAndSetAutoSizeTextTypeUniformConfiguration(autoSizeMinTextSizeInPx, autoSizeMaxTextSizeInPx, autoSizeStepGranularityInPx);
            if (setupAutoSizeText()) {
                autoSizeText();
                invalidate();
            }
        }
    }

    public void setAutoSizeTextTypeUniformWithPresetSizes(int[] presetSizes, int unit) {
        if (supportsAutoSizeText()) {
            int presetSizesLength = presetSizes.length;
            if (presetSizesLength > 0) {
                int[] presetSizesInPx = new int[presetSizesLength];
                if (unit == 0) {
                    presetSizesInPx = Arrays.copyOf(presetSizes, presetSizesLength);
                } else {
                    DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
                    for (int i = 0; i < presetSizesLength; i++) {
                        presetSizesInPx[i] = Math.round(TypedValue.applyDimension(unit, presetSizes[i], displayMetrics));
                    }
                }
                this.mAutoSizeTextSizesInPx = cleanupAutoSizePresetSizes(presetSizesInPx);
                if (!setupAutoSizeUniformPresetSizesConfiguration()) {
                    throw new IllegalArgumentException("None of the preset sizes is valid: " + Arrays.toString(presetSizes));
                }
            } else {
                this.mHasPresetAutoSizeValues = false;
            }
            if (setupAutoSizeText()) {
                autoSizeText();
                invalidate();
            }
        }
    }

    public int getAutoSizeTextType() {
        return this.mAutoSizeTextType;
    }

    public int getAutoSizeStepGranularity() {
        return Math.round(this.mAutoSizeStepGranularityInPx);
    }

    public int getAutoSizeMinTextSize() {
        return Math.round(this.mAutoSizeMinTextSizeInPx);
    }

    public int getAutoSizeMaxTextSize() {
        return Math.round(this.mAutoSizeMaxTextSizeInPx);
    }

    public int[] getAutoSizeTextAvailableSizes() {
        return this.mAutoSizeTextSizesInPx;
    }

    private void setupAutoSizeUniformPresetSizes(TypedArray textSizes) {
        int textSizesLength = textSizes.length();
        int[] parsedSizes = new int[textSizesLength];
        if (textSizesLength > 0) {
            for (int i = 0; i < textSizesLength; i++) {
                parsedSizes[i] = textSizes.getDimensionPixelSize(i, -1);
            }
            this.mAutoSizeTextSizesInPx = cleanupAutoSizePresetSizes(parsedSizes);
            setupAutoSizeUniformPresetSizesConfiguration();
        }
    }

    private boolean setupAutoSizeUniformPresetSizesConfiguration() {
        int sizesLength = this.mAutoSizeTextSizesInPx.length;
        this.mHasPresetAutoSizeValues = sizesLength > 0;
        if (this.mHasPresetAutoSizeValues) {
            this.mAutoSizeTextType = 1;
            this.mAutoSizeMinTextSizeInPx = this.mAutoSizeTextSizesInPx[0];
            this.mAutoSizeMaxTextSizeInPx = this.mAutoSizeTextSizesInPx[sizesLength - 1];
            this.mAutoSizeStepGranularityInPx = -1.0f;
        }
        return this.mHasPresetAutoSizeValues;
    }

    private void validateAndSetAutoSizeTextTypeUniformConfiguration(float autoSizeMinTextSizeInPx, float autoSizeMaxTextSizeInPx, float autoSizeStepGranularityInPx) {
        if (autoSizeMinTextSizeInPx <= 0.0f) {
            throw new IllegalArgumentException("Minimum auto-size text size (" + autoSizeMinTextSizeInPx + "px) is less or equal to (0px)");
        }
        if (autoSizeMaxTextSizeInPx <= autoSizeMinTextSizeInPx) {
            throw new IllegalArgumentException("Maximum auto-size text size (" + autoSizeMaxTextSizeInPx + "px) is less or equal to minimum auto-size text size (" + autoSizeMinTextSizeInPx + "px)");
        }
        if (autoSizeStepGranularityInPx <= 0.0f) {
            throw new IllegalArgumentException("The auto-size step granularity (" + autoSizeStepGranularityInPx + "px) is less or equal to (0px)");
        }
        this.mAutoSizeTextType = 1;
        this.mAutoSizeMinTextSizeInPx = autoSizeMinTextSizeInPx;
        this.mAutoSizeMaxTextSizeInPx = autoSizeMaxTextSizeInPx;
        this.mAutoSizeStepGranularityInPx = ((float) Math.floor(autoSizeStepGranularityInPx * 10000.0f)) / 10000.0f;
        this.mHasPresetAutoSizeValues = false;
    }

    private void clearAutoSizeConfiguration() {
        this.mAutoSizeTextType = 0;
        this.mAutoSizeMinTextSizeInPx = -1.0f;
        this.mAutoSizeMaxTextSizeInPx = -1.0f;
        this.mAutoSizeStepGranularityInPx = -1.0f;
        this.mAutoSizeTextSizesInPx = EmptyArray.INT;
        this.mNeedsAutoSizeText = false;
    }

    private int[] cleanupAutoSizePresetSizes(int[] presetValues) {
        int presetValuesLength = presetValues.length;
        if (presetValuesLength == 0) {
            return presetValues;
        }
        Arrays.sort(presetValues);
        IntArray uniqueValidSizes = new IntArray();
        for (int currentPresetValue : presetValues) {
            if (currentPresetValue > 0 && uniqueValidSizes.binarySearch(currentPresetValue) < 0) {
                uniqueValidSizes.add(currentPresetValue);
            }
        }
        int i = uniqueValidSizes.size();
        if (presetValuesLength == i) {
            return presetValues;
        }
        return uniqueValidSizes.toArray();
    }

    private boolean setupAutoSizeText() {
        if (supportsAutoSizeText() && this.mAutoSizeTextType == 1) {
            if (!this.mHasPresetAutoSizeValues || this.mAutoSizeTextSizesInPx.length == 0) {
                int autoSizeValuesLength = ((int) Math.floor((this.mAutoSizeMaxTextSizeInPx - this.mAutoSizeMinTextSizeInPx) / this.mAutoSizeStepGranularityInPx)) + 1;
                int[] autoSizeTextSizesInPx = new int[autoSizeValuesLength];
                for (int i = 0; i < autoSizeValuesLength; i++) {
                    autoSizeTextSizesInPx[i] = Math.round(this.mAutoSizeMinTextSizeInPx + (i * this.mAutoSizeStepGranularityInPx));
                }
                this.mAutoSizeTextSizesInPx = cleanupAutoSizePresetSizes(autoSizeTextSizesInPx);
            }
            this.mNeedsAutoSizeText = true;
        } else {
            this.mNeedsAutoSizeText = false;
        }
        return this.mNeedsAutoSizeText;
    }

    private int[] parseDimensionArray(TypedArray dimens) {
        if (dimens == null) {
            return null;
        }
        int[] result = new int[dimens.length()];
        for (int i = 0; i < result.length; i++) {
            result[i] = dimens.getDimensionPixelSize(i, 0);
        }
        return result;
    }

    @Override // android.view.View
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        CharSequence result;
        if (requestCode == 100) {
            if (resultCode == -1 && data != null) {
                CharSequence result2 = data.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT);
                if (result2 != null) {
                    if (isTextEditable()) {
                        ClipData clip = ClipData.newPlainText("", result2);
                        ContentInfo payload = new ContentInfo.Builder(clip, 5).build();
                        performReceiveContent(payload);
                        if (this.mEditor != null) {
                            this.mEditor.refreshTextActionMode();
                            return;
                        }
                        return;
                    }
                    if (result2.length() > 0) {
                        Toast.makeText(getContext(), String.valueOf(result2), 1).show();
                        return;
                    }
                    return;
                }
                return;
            }
            if (this.mSpannable != null) {
                Selection.setSelection(this.mSpannable, getSelectionEnd());
                return;
            }
            return;
        }
        if (requestCode == 101) {
            if (resultCode == -1 && data != null && (result = data.getCharSequenceExtra("translatedText")) != null && isTextEditable()) {
                int selectionStart = getSelectionStart();
                int selectionEnd = getSelectionEnd();
                if (selectionStart == selectionEnd) {
                    selectionStart = this.mPrevSelectionStartForSSS;
                    selectionEnd = this.mPrevSelectionEndForSSS;
                }
                replaceText_internal(selectionStart, selectionEnd, result);
                return;
            }
            return;
        }
        if (requestCode == 102 && resultCode == -1 && data != null) {
            CharSequence result3 = data.getCharSequenceExtra("toolkitText");
            CharSequence action = data.getCharSequenceExtra("toolkitAction");
            if (result3 != null && isTextEditable()) {
                int selectionStart2 = getSelectionStart();
                int selectionEnd2 = getSelectionEnd();
                if (selectionStart2 == selectionEnd2) {
                    selectionStart2 = this.mPrevSelectionStartForSSS;
                    selectionEnd2 = this.mPrevSelectionEndForSSS;
                }
                if ("replace".equals(action)) {
                    replaceText_internal(selectionStart2, selectionEnd2, result3);
                } else if ("addto".equals(action)) {
                    replaceText_internal(selectionEnd2, selectionEnd2, result3);
                } else {
                    Log.e(LOG_TAG, "writingtoolkit action error");
                }
            }
        }
    }

    private void setTypefaceFromAttrs(Typeface typeface, String familyName, int typefaceIndex, int style, int weight) {
        if (typeface == null && familyName != null) {
            Typeface normalTypeface = Typeface.create(familyName, 0);
            resolveStyleAndSetTypeface(normalTypeface, style, weight);
            return;
        }
        if (typeface != null) {
            resolveStyleAndSetTypeface(typeface, style, weight);
            return;
        }
        switch (typefaceIndex) {
            case 1:
                resolveStyleAndSetTypeface(Typeface.SANS_SERIF, style, weight);
                break;
            case 2:
                resolveStyleAndSetTypeface(Typeface.SERIF, style, weight);
                break;
            case 3:
                resolveStyleAndSetTypeface(Typeface.MONOSPACE, style, weight);
                break;
            default:
                resolveStyleAndSetTypeface(null, style, weight);
                break;
        }
    }

    private void resolveStyleAndSetTypeface(Typeface typeface, int style, int weight) {
        if (weight >= 0) {
            int weight2 = Math.min(1000, weight);
            boolean italic = (style & 2) != 0;
            setTypeface(Typeface.create(typeface, weight2, italic));
            return;
        }
        setTypeface(typeface, style);
    }

    private void setRelativeDrawablesIfNeeded(Drawable start, Drawable end) {
        boolean hasRelativeDrawables = (start == null && end == null) ? false : true;
        if (hasRelativeDrawables) {
            Drawables dr = this.mDrawables;
            if (dr == null) {
                Drawables drawables = new Drawables(getContext());
                dr = drawables;
                this.mDrawables = drawables;
            }
            this.mDrawables.mOverride = true;
            Rect compoundRect = dr.mCompoundRect;
            int[] state = getDrawableState();
            if (start != null) {
                start.setBounds(0, 0, start.getIntrinsicWidth(), start.getIntrinsicHeight());
                start.setState(state);
                start.copyBounds(compoundRect);
                start.setCallback(this);
                dr.mDrawableStart = start;
                dr.mDrawableSizeStart = compoundRect.width();
                dr.mDrawableHeightStart = compoundRect.height();
            } else {
                dr.mDrawableHeightStart = 0;
                dr.mDrawableSizeStart = 0;
            }
            if (end != null) {
                end.setBounds(0, 0, end.getIntrinsicWidth(), end.getIntrinsicHeight());
                end.setState(state);
                end.copyBounds(compoundRect);
                end.setCallback(this);
                dr.mDrawableEnd = end;
                dr.mDrawableSizeEnd = compoundRect.width();
                dr.mDrawableHeightEnd = compoundRect.height();
            } else {
                dr.mDrawableHeightEnd = 0;
                dr.mDrawableSizeEnd = 0;
            }
            resetResolvedDrawables();
            resolveDrawables();
            applyCompoundDrawableTint();
        }
    }

    @Override // android.view.View
    @RemotableViewMethod
    public void setEnabled(boolean enabled) {
        InputMethodManager imm;
        InputMethodManager imm2;
        if (enabled == isEnabled()) {
            return;
        }
        if (!enabled && (imm2 = getInputMethodManager()) != null) {
            imm2.hideSoftInputFromView(this, 0);
        }
        super.setEnabled(enabled);
        if (enabled && (imm = getInputMethodManager()) != null) {
            imm.restartInput(this);
        }
        if (this.mEditor != null) {
            this.mEditor.invalidateTextDisplayList();
            this.mEditor.prepareCursorControllers();
            this.mEditor.makeBlink();
        }
        if (this.mButtonShapePaint != null) {
            this.mButtonShapeAlpha = enabled ? this.mButtonShapeOutlineStrokeEnabled : this.mButtonShapeOutlineStrokeDisabled;
        }
    }

    public void setTypeface(Typeface tf, int style) {
        Typeface tf2;
        if (style > 0) {
            if (tf == null) {
                tf2 = Typeface.defaultFromStyle(style);
            } else {
                tf2 = Typeface.create(tf, style);
            }
            setTypeface(tf2);
            int typefaceStyle = tf2 != null ? tf2.getStyle() : 0;
            int need = (~typefaceStyle) & style;
            this.mTextPaint.setFakeBoldText((need & 1) != 0);
            this.mTextPaint.setTextSkewX((need & 2) != 0 ? -0.25f : 0.0f);
            return;
        }
        this.mTextPaint.setFakeBoldText(false);
        this.mTextPaint.setTextSkewX(0.0f);
        setTypeface(tf);
    }

    protected boolean getDefaultEditable() {
        return false;
    }

    protected MovementMethod getDefaultMovementMethod() {
        return null;
    }

    @ViewDebug.CapturedViewProperty
    public CharSequence getText() {
        ViewTranslationCallback callback;
        if (this.mUseTextPaddingForUiTranslation && (callback = getViewTranslationCallback()) != null && (callback instanceof TextViewTranslationCallback)) {
            TextViewTranslationCallback defaultCallback = (TextViewTranslationCallback) callback;
            if (defaultCallback.isTextPaddingEnabled() && defaultCallback.isShowingTranslation()) {
                return defaultCallback.getPaddedText(this.mText, this.mTransformed);
            }
        }
        return this.mText;
    }

    public int length() {
        return this.mText.length();
    }

    public Editable getEditableText() {
        if (this.mText instanceof Editable) {
            return (Editable) this.mText;
        }
        return null;
    }

    public CharSequence getTransformed() {
        return this.mTransformed;
    }

    public int getLineHeight() {
        return FastMath.round((this.mTextPaint.getFontMetricsInt(null) * this.mSpacingMult) + this.mSpacingAdd);
    }

    public final Layout getLayout() {
        return this.mLayout;
    }

    final Layout getHintLayout() {
        return this.mHintLayout;
    }

    public final UndoManager getUndoManager() {
        throw new UnsupportedOperationException("not implemented");
    }

    public final Editor getEditorForTesting() {
        return this.mEditor;
    }

    public final void setUndoManager(UndoManager undoManager, String tag) {
        throw new UnsupportedOperationException("not implemented");
    }

    public final KeyListener getKeyListener() {
        if (this.mEditor == null) {
            return null;
        }
        return this.mEditor.mKeyListener;
    }

    public void setKeyListener(KeyListener input) {
        this.mListenerChanged = true;
        setKeyListenerOnly(input);
        fixFocusableAndClickableSettings();
        if (input != null) {
            createEditorIfNeeded();
            setInputTypeFromEditor();
        } else if (this.mEditor != null) {
            this.mEditor.mInputType = 0;
        }
        InputMethodManager imm = getInputMethodManager();
        if (imm != null) {
            imm.restartInput(this);
        }
    }

    private void setInputTypeFromEditor() {
        try {
            this.mEditor.mInputType = this.mEditor.mKeyListener.getInputType();
        } catch (IncompatibleClassChangeError e) {
            this.mEditor.mInputType = 1;
        }
        setInputTypeSingleLine(this.mSingleLine);
    }

    private void setKeyListenerOnly(KeyListener input) {
        if (this.mEditor == null && input == null) {
            return;
        }
        createEditorIfNeeded();
        if (this.mEditor.mKeyListener != input) {
            this.mEditor.mKeyListener = input;
            if (input != null && !(this.mText instanceof Editable)) {
                lambda$setTextAsync$0(this.mText);
            }
            setFilters((Editable) this.mText, this.mFilters);
        }
    }

    public final MovementMethod getMovementMethod() {
        return this.mMovement;
    }

    public final void setMovementMethod(MovementMethod movement) {
        if (this.mMovement != movement) {
            this.mMovement = movement;
            if (movement != null && this.mSpannable == null) {
                lambda$setTextAsync$0(this.mText);
            }
            fixFocusableAndClickableSettings();
            if (this.mEditor != null) {
                this.mEditor.prepareCursorControllers();
            }
        }
    }

    private void fixFocusableAndClickableSettings() {
        if (this.mMovement != null || (this.mEditor != null && this.mEditor.mKeyListener != null)) {
            setFocusable(1);
            setClickable(true);
            setLongClickable(true);
        } else {
            setFocusable(16);
            setClickable(false);
            setLongClickable(false);
        }
    }

    public final TransformationMethod getTransformationMethod() {
        return this.mTransformation;
    }

    public final void setTransformationMethod(TransformationMethod method) {
        if (this.mEditor != null) {
            this.mEditor.setTransformationMethod(method);
        } else {
            setTransformationMethodInternal(method, true);
        }
    }

    void setTransformationMethodInternal(TransformationMethod method, boolean updateText) {
        if (method == this.mTransformation) {
            return;
        }
        if (this.mTransformation != null && this.mSpannable != null) {
            this.mSpannable.removeSpan(this.mTransformation);
        }
        this.mTransformation = method;
        if (method instanceof TransformationMethod2) {
            TransformationMethod2 method2 = (TransformationMethod2) method;
            this.mAllowTransformationLengthChange = (isTextSelectable() || (this.mText instanceof Editable)) ? false : true;
            method2.setLengthChangesAllowed(this.mAllowTransformationLengthChange);
        } else {
            this.mAllowTransformationLengthChange = false;
        }
        if (updateText) {
            if (Flags.insertModeNotUpdateSelection()) {
                if (this.mTransformation == null) {
                    this.mTransformed = this.mText;
                } else {
                    this.mTransformed = this.mTransformation.getTransformation(this.mText, this);
                }
                if (this.mTransformed == null) {
                    this.mTransformed = "";
                }
                boolean isOffsetMapping = this.mTransformed instanceof OffsetMapping;
                if (this.mTransformation != null && (this.mText instanceof Spannable) && (!this.mAllowTransformationLengthChange || isOffsetMapping)) {
                    Spannable sp = (Spannable) this.mText;
                    int priority = isOffsetMapping ? 200 : 0;
                    sp.setSpan(this.mTransformation, 0, this.mText.length(), (priority << 16) | 18);
                }
                if (this.mLayout != null) {
                    nullLayouts();
                    requestLayout();
                    invalidate();
                }
            } else {
                lambda$setTextAsync$0(this.mText);
            }
        }
        if (hasPasswordTransformationMethod()) {
            notifyViewAccessibilityStateChangedIfNeeded(0);
        }
    }

    public int getCompoundPaddingTop() {
        Drawables dr = this.mDrawables;
        if (dr == null || dr.mShowing[1] == null) {
            return this.mPaddingTop;
        }
        return this.mPaddingTop + dr.mDrawablePadding + dr.mDrawableSizeTop;
    }

    public int getCompoundPaddingBottom() {
        Drawables dr = this.mDrawables;
        if (dr == null || dr.mShowing[3] == null) {
            return this.mPaddingBottom;
        }
        return this.mPaddingBottom + dr.mDrawablePadding + dr.mDrawableSizeBottom;
    }

    public int getCompoundPaddingLeft() {
        Drawables dr = this.mDrawables;
        if (dr == null || dr.mShowing[0] == null) {
            return this.mPaddingLeft;
        }
        return this.mPaddingLeft + dr.mDrawablePadding + dr.mDrawableSizeLeft;
    }

    public int getCompoundPaddingRight() {
        Drawables dr = this.mDrawables;
        if (dr == null || dr.mShowing[2] == null) {
            return this.mPaddingRight;
        }
        return this.mPaddingRight + dr.mDrawablePadding + dr.mDrawableSizeRight;
    }

    public int getCompoundPaddingStart() {
        resolveDrawables();
        switch (getLayoutDirection()) {
            case 1:
                return getCompoundPaddingRight();
            default:
                return getCompoundPaddingLeft();
        }
    }

    public int getCompoundPaddingEnd() {
        resolveDrawables();
        switch (getLayoutDirection()) {
            case 1:
                return getCompoundPaddingLeft();
            default:
                return getCompoundPaddingRight();
        }
    }

    public int getExtendedPaddingTop() {
        if (this.mMaxMode != 1) {
            return getCompoundPaddingTop();
        }
        if (this.mLayout == null) {
            assumeLayout();
        }
        if (this.mLayout.getLineCount() <= this.mMaximum) {
            return getCompoundPaddingTop();
        }
        int top = getCompoundPaddingTop();
        int bottom = getCompoundPaddingBottom();
        int viewht = (getHeight() - top) - bottom;
        int layoutht = this.mLayout.getLineTop(this.mMaximum);
        if (layoutht >= viewht) {
            return top;
        }
        int gravity = this.mGravity & 112;
        if (gravity == 48) {
            return top;
        }
        if (gravity == 80) {
            return (top + viewht) - layoutht;
        }
        return ((viewht - layoutht) / 2) + top;
    }

    public int getExtendedPaddingBottom() {
        if (this.mMaxMode != 1) {
            return getCompoundPaddingBottom();
        }
        if (this.mLayout == null) {
            assumeLayout();
        }
        if (this.mLayout.getLineCount() <= this.mMaximum) {
            return getCompoundPaddingBottom();
        }
        int top = getCompoundPaddingTop();
        int bottom = getCompoundPaddingBottom();
        int viewht = (getHeight() - top) - bottom;
        int layoutht = this.mLayout.getLineTop(this.mMaximum);
        if (layoutht >= viewht) {
            return bottom;
        }
        int gravity = this.mGravity & 112;
        if (gravity == 48) {
            return (bottom + viewht) - layoutht;
        }
        if (gravity == 80) {
            return bottom;
        }
        return ((viewht - layoutht) / 2) + bottom;
    }

    public int getTotalPaddingLeft() {
        return getCompoundPaddingLeft();
    }

    public int getTotalPaddingRight() {
        return getCompoundPaddingRight();
    }

    public int getTotalPaddingStart() {
        return getCompoundPaddingStart();
    }

    public int getTotalPaddingEnd() {
        return getCompoundPaddingEnd();
    }

    public int getTotalPaddingTop() {
        return getExtendedPaddingTop() + getVerticalOffset(true);
    }

    public int getTotalPaddingBottom() {
        return getExtendedPaddingBottom() + getBottomVerticalOffset(true);
    }

    public void setCompoundDrawables(Drawable left, Drawable top, Drawable right, Drawable bottom) {
        Drawables dr = this.mDrawables;
        if (dr != null) {
            if (dr.mDrawableStart != null) {
                dr.mDrawableStart.setCallback(null);
            }
            dr.mDrawableStart = null;
            if (dr.mDrawableEnd != null) {
                dr.mDrawableEnd.setCallback(null);
            }
            dr.mDrawableEnd = null;
            dr.mDrawableHeightStart = 0;
            dr.mDrawableSizeStart = 0;
            dr.mDrawableHeightEnd = 0;
            dr.mDrawableSizeEnd = 0;
        }
        boolean drawables = (left == null && top == null && right == null && bottom == null) ? false : true;
        if (!drawables) {
            if (dr != null) {
                if (dr.hasMetadata()) {
                    for (int i = dr.mShowing.length - 1; i >= 0; i--) {
                        if (dr.mShowing[i] != null) {
                            dr.mShowing[i].setCallback(null);
                        }
                        dr.mShowing[i] = null;
                    }
                    dr.mDrawableHeightLeft = 0;
                    dr.mDrawableSizeLeft = 0;
                    dr.mDrawableHeightRight = 0;
                    dr.mDrawableSizeRight = 0;
                    dr.mDrawableWidthTop = 0;
                    dr.mDrawableSizeTop = 0;
                    dr.mDrawableWidthBottom = 0;
                    dr.mDrawableSizeBottom = 0;
                } else {
                    this.mDrawables = null;
                }
            }
        } else {
            if (dr == null) {
                Drawables drawables2 = new Drawables(getContext());
                dr = drawables2;
                this.mDrawables = drawables2;
            }
            this.mDrawables.mOverride = false;
            if (dr.mShowing[0] != left && dr.mShowing[0] != null) {
                dr.mShowing[0].setCallback(null);
            }
            dr.mShowing[0] = left;
            if (dr.mShowing[1] != top && dr.mShowing[1] != null) {
                dr.mShowing[1].setCallback(null);
            }
            dr.mShowing[1] = top;
            if (dr.mShowing[2] != right && dr.mShowing[2] != null) {
                dr.mShowing[2].setCallback(null);
            }
            dr.mShowing[2] = right;
            if (dr.mShowing[3] != bottom && dr.mShowing[3] != null) {
                dr.mShowing[3].setCallback(null);
            }
            dr.mShowing[3] = bottom;
            Rect compoundRect = dr.mCompoundRect;
            int[] state = getDrawableState();
            if (left != null) {
                left.setState(state);
                left.copyBounds(compoundRect);
                left.setCallback(this);
                dr.mDrawableSizeLeft = compoundRect.width();
                dr.mDrawableHeightLeft = compoundRect.height();
            } else {
                dr.mDrawableHeightLeft = 0;
                dr.mDrawableSizeLeft = 0;
            }
            if (right != null) {
                right.setState(state);
                right.copyBounds(compoundRect);
                right.setCallback(this);
                dr.mDrawableSizeRight = compoundRect.width();
                dr.mDrawableHeightRight = compoundRect.height();
            } else {
                dr.mDrawableHeightRight = 0;
                dr.mDrawableSizeRight = 0;
            }
            if (top != null) {
                top.setState(state);
                top.copyBounds(compoundRect);
                top.setCallback(this);
                dr.mDrawableSizeTop = compoundRect.height();
                dr.mDrawableWidthTop = compoundRect.width();
            } else {
                dr.mDrawableWidthTop = 0;
                dr.mDrawableSizeTop = 0;
            }
            if (bottom != null) {
                bottom.setState(state);
                bottom.copyBounds(compoundRect);
                bottom.setCallback(this);
                dr.mDrawableSizeBottom = compoundRect.height();
                dr.mDrawableWidthBottom = compoundRect.width();
            } else {
                dr.mDrawableWidthBottom = 0;
                dr.mDrawableSizeBottom = 0;
            }
        }
        if (dr != null) {
            dr.mDrawableLeftInitial = left;
            dr.mDrawableRightInitial = right;
        }
        resetResolvedDrawables();
        resolveDrawables();
        applyCompoundDrawableTint();
        invalidate();
        requestLayout();
    }

    @RemotableViewMethod
    public void setCompoundDrawablesWithIntrinsicBounds(int left, int top, int right, int bottom) {
        Context context = getContext();
        setCompoundDrawablesWithIntrinsicBounds(left != 0 ? context.getDrawable(left) : null, top != 0 ? context.getDrawable(top) : null, right != 0 ? context.getDrawable(right) : null, bottom != 0 ? context.getDrawable(bottom) : null);
    }

    @RemotableViewMethod
    public void setCompoundDrawablesWithIntrinsicBounds(Drawable left, Drawable top, Drawable right, Drawable bottom) {
        if (left != null) {
            left.setBounds(0, 0, left.getIntrinsicWidth(), left.getIntrinsicHeight());
        }
        if (right != null) {
            right.setBounds(0, 0, right.getIntrinsicWidth(), right.getIntrinsicHeight());
        }
        if (top != null) {
            top.setBounds(0, 0, top.getIntrinsicWidth(), top.getIntrinsicHeight());
        }
        if (bottom != null) {
            bottom.setBounds(0, 0, bottom.getIntrinsicWidth(), bottom.getIntrinsicHeight());
        }
        setCompoundDrawables(left, top, right, bottom);
    }

    @RemotableViewMethod
    public void setCompoundDrawablesRelative(Drawable start, Drawable top, Drawable end, Drawable bottom) {
        boolean drawables;
        Drawables dr = this.mDrawables;
        if (dr != null) {
            if (dr.mShowing[0] != null) {
                dr.mShowing[0].setCallback(null);
            }
            Drawable[] drawableArr = dr.mShowing;
            dr.mDrawableLeftInitial = null;
            drawableArr[0] = null;
            if (dr.mShowing[2] != null) {
                dr.mShowing[2].setCallback(null);
            }
            Drawable[] drawableArr2 = dr.mShowing;
            dr.mDrawableRightInitial = null;
            drawableArr2[2] = null;
            dr.mDrawableHeightLeft = 0;
            dr.mDrawableSizeLeft = 0;
            dr.mDrawableHeightRight = 0;
            dr.mDrawableSizeRight = 0;
        }
        if (start == null && top == null && end == null && bottom == null) {
            drawables = false;
        } else {
            drawables = true;
        }
        if (!drawables) {
            if (dr != null) {
                if (!dr.hasMetadata()) {
                    this.mDrawables = null;
                } else {
                    if (dr.mDrawableStart != null) {
                        dr.mDrawableStart.setCallback(null);
                    }
                    dr.mDrawableStart = null;
                    if (dr.mShowing[1] != null) {
                        dr.mShowing[1].setCallback(null);
                    }
                    dr.mShowing[1] = null;
                    if (dr.mDrawableEnd != null) {
                        dr.mDrawableEnd.setCallback(null);
                    }
                    dr.mDrawableEnd = null;
                    if (dr.mShowing[3] != null) {
                        dr.mShowing[3].setCallback(null);
                    }
                    dr.mShowing[3] = null;
                    dr.mDrawableHeightStart = 0;
                    dr.mDrawableSizeStart = 0;
                    dr.mDrawableHeightEnd = 0;
                    dr.mDrawableSizeEnd = 0;
                    dr.mDrawableWidthTop = 0;
                    dr.mDrawableSizeTop = 0;
                    dr.mDrawableWidthBottom = 0;
                    dr.mDrawableSizeBottom = 0;
                }
            }
        } else {
            if (dr == null) {
                Drawables drawables2 = new Drawables(getContext());
                dr = drawables2;
                this.mDrawables = drawables2;
            }
            this.mDrawables.mOverride = true;
            if (dr.mDrawableStart != start && dr.mDrawableStart != null) {
                dr.mDrawableStart.setCallback(null);
            }
            dr.mDrawableStart = start;
            if (dr.mShowing[1] != top && dr.mShowing[1] != null) {
                dr.mShowing[1].setCallback(null);
            }
            dr.mShowing[1] = top;
            if (dr.mDrawableEnd != end && dr.mDrawableEnd != null) {
                dr.mDrawableEnd.setCallback(null);
            }
            dr.mDrawableEnd = end;
            if (dr.mShowing[3] != bottom && dr.mShowing[3] != null) {
                dr.mShowing[3].setCallback(null);
            }
            dr.mShowing[3] = bottom;
            Rect compoundRect = dr.mCompoundRect;
            int[] state = getDrawableState();
            if (start != null) {
                start.setState(state);
                start.copyBounds(compoundRect);
                start.setCallback(this);
                dr.mDrawableSizeStart = compoundRect.width();
                dr.mDrawableHeightStart = compoundRect.height();
            } else {
                dr.mDrawableHeightStart = 0;
                dr.mDrawableSizeStart = 0;
            }
            if (end != null) {
                end.setState(state);
                end.copyBounds(compoundRect);
                end.setCallback(this);
                dr.mDrawableSizeEnd = compoundRect.width();
                dr.mDrawableHeightEnd = compoundRect.height();
            } else {
                dr.mDrawableHeightEnd = 0;
                dr.mDrawableSizeEnd = 0;
            }
            if (top != null) {
                top.setState(state);
                top.copyBounds(compoundRect);
                top.setCallback(this);
                dr.mDrawableSizeTop = compoundRect.height();
                dr.mDrawableWidthTop = compoundRect.width();
            } else {
                dr.mDrawableWidthTop = 0;
                dr.mDrawableSizeTop = 0;
            }
            if (bottom != null) {
                bottom.setState(state);
                bottom.copyBounds(compoundRect);
                bottom.setCallback(this);
                dr.mDrawableSizeBottom = compoundRect.height();
                dr.mDrawableWidthBottom = compoundRect.width();
            } else {
                dr.mDrawableWidthBottom = 0;
                dr.mDrawableSizeBottom = 0;
            }
        }
        resetResolvedDrawables();
        resolveDrawables();
        invalidate();
        requestLayout();
    }

    @RemotableViewMethod
    public void setCompoundDrawablesRelativeWithIntrinsicBounds(int start, int top, int end, int bottom) {
        Context context = getContext();
        setCompoundDrawablesRelativeWithIntrinsicBounds(start != 0 ? context.getDrawable(start) : null, top != 0 ? context.getDrawable(top) : null, end != 0 ? context.getDrawable(end) : null, bottom != 0 ? context.getDrawable(bottom) : null);
    }

    @RemotableViewMethod
    public void setCompoundDrawablesRelativeWithIntrinsicBounds(Drawable start, Drawable top, Drawable end, Drawable bottom) {
        if (start != null) {
            start.setBounds(0, 0, start.getIntrinsicWidth(), start.getIntrinsicHeight());
        }
        if (end != null) {
            end.setBounds(0, 0, end.getIntrinsicWidth(), end.getIntrinsicHeight());
        }
        if (top != null) {
            top.setBounds(0, 0, top.getIntrinsicWidth(), top.getIntrinsicHeight());
        }
        if (bottom != null) {
            bottom.setBounds(0, 0, bottom.getIntrinsicWidth(), bottom.getIntrinsicHeight());
        }
        setCompoundDrawablesRelative(start, top, end, bottom);
    }

    public Drawable[] getCompoundDrawables() {
        Drawables dr = this.mDrawables;
        if (dr != null) {
            return (Drawable[]) dr.mShowing.clone();
        }
        return new Drawable[]{null, null, null, null};
    }

    public Drawable[] getCompoundDrawablesRelative() {
        Drawables dr = this.mDrawables;
        if (dr != null) {
            return new Drawable[]{dr.mDrawableStart, dr.mShowing[1], dr.mDrawableEnd, dr.mShowing[3]};
        }
        return new Drawable[]{null, null, null, null};
    }

    @RemotableViewMethod
    public void setCompoundDrawablePadding(int pad) {
        Drawables dr = this.mDrawables;
        if (pad == 0) {
            if (dr != null) {
                dr.mDrawablePadding = pad;
            }
        } else {
            if (dr == null) {
                Drawables drawables = new Drawables(getContext());
                dr = drawables;
                this.mDrawables = drawables;
            }
            dr.mDrawablePadding = pad;
        }
        invalidate();
        requestLayout();
    }

    public int getCompoundDrawablePadding() {
        Drawables dr = this.mDrawables;
        if (dr != null) {
            return dr.mDrawablePadding;
        }
        return 0;
    }

    public void setCompoundDrawableTintList(ColorStateList tint) {
        if (this.mDrawables == null) {
            this.mDrawables = new Drawables(getContext());
        }
        this.mDrawables.mTintList = tint;
        this.mDrawables.mHasTint = true;
        applyCompoundDrawableTint();
    }

    public ColorStateList getCompoundDrawableTintList() {
        if (this.mDrawables != null) {
            return this.mDrawables.mTintList;
        }
        return null;
    }

    public void setCompoundDrawableTintMode(PorterDuff.Mode tintMode) {
        setCompoundDrawableTintBlendMode(tintMode != null ? BlendMode.fromValue(tintMode.nativeInt) : null);
    }

    public void setCompoundDrawableTintBlendMode(BlendMode blendMode) {
        if (this.mDrawables == null) {
            this.mDrawables = new Drawables(getContext());
        }
        this.mDrawables.mBlendMode = blendMode;
        this.mDrawables.mHasTintMode = true;
        applyCompoundDrawableTint();
    }

    public PorterDuff.Mode getCompoundDrawableTintMode() {
        BlendMode mode = getCompoundDrawableTintBlendMode();
        if (mode != null) {
            return BlendMode.blendModeToPorterDuffMode(mode);
        }
        return null;
    }

    public BlendMode getCompoundDrawableTintBlendMode() {
        if (this.mDrawables != null) {
            return this.mDrawables.mBlendMode;
        }
        return null;
    }

    private void applyCompoundDrawableTint() {
        if (this.mDrawables == null) {
            return;
        }
        if (this.mDrawables.mHasTint || this.mDrawables.mHasTintMode) {
            ColorStateList tintList = this.mDrawables.mTintList;
            BlendMode blendMode = this.mDrawables.mBlendMode;
            boolean hasTint = this.mDrawables.mHasTint;
            boolean hasTintMode = this.mDrawables.mHasTintMode;
            int[] state = getDrawableState();
            for (Drawable dr : this.mDrawables.mShowing) {
                if (dr != null && dr != this.mDrawables.mDrawableError) {
                    dr.mutate();
                    if (hasTint) {
                        dr.setTintList(tintList);
                    }
                    if (hasTintMode) {
                        dr.setTintBlendMode(blendMode);
                    }
                    if (dr.isStateful()) {
                        dr.setState(state);
                    }
                }
            }
        }
    }

    @Override // android.view.View
    public void setPadding(int left, int top, int right, int bottom) {
        if (left != this.mPaddingLeft || right != this.mPaddingRight || top != this.mPaddingTop || bottom != this.mPaddingBottom) {
            nullLayouts();
        }
        super.setPadding(left, top, right, bottom);
        invalidate();
    }

    @Override // android.view.View
    public void setPaddingRelative(int start, int top, int end, int bottom) {
        if (start != getPaddingStart() || end != getPaddingEnd() || top != this.mPaddingTop || bottom != this.mPaddingBottom) {
            nullLayouts();
        }
        super.setPaddingRelative(start, top, end, bottom);
        invalidate();
    }

    public void setFirstBaselineToTopHeight(int firstBaselineToTopHeight) {
        int fontMetricsTop;
        Preconditions.checkArgumentNonnegative(firstBaselineToTopHeight);
        Paint.FontMetricsInt fontMetrics = getPaint().getFontMetricsInt();
        if (getIncludeFontPadding()) {
            fontMetricsTop = fontMetrics.top;
        } else {
            fontMetricsTop = fontMetrics.ascent;
        }
        if (firstBaselineToTopHeight > Math.abs(fontMetricsTop)) {
            int paddingTop = firstBaselineToTopHeight - (-fontMetricsTop);
            setPadding(getPaddingLeft(), paddingTop, getPaddingRight(), getPaddingBottom());
        }
    }

    public void setLastBaselineToBottomHeight(int lastBaselineToBottomHeight) {
        int fontMetricsBottom;
        Preconditions.checkArgumentNonnegative(lastBaselineToBottomHeight);
        Paint.FontMetricsInt fontMetrics = getPaint().getFontMetricsInt();
        if (getIncludeFontPadding()) {
            fontMetricsBottom = fontMetrics.bottom;
        } else {
            fontMetricsBottom = fontMetrics.descent;
        }
        if (lastBaselineToBottomHeight > Math.abs(fontMetricsBottom)) {
            int paddingBottom = lastBaselineToBottomHeight - fontMetricsBottom;
            setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), paddingBottom);
        }
    }

    public int getFirstBaselineToTopHeight() {
        return getPaddingTop() - getPaint().getFontMetricsInt().top;
    }

    public int getLastBaselineToBottomHeight() {
        return getPaddingBottom() + getPaint().getFontMetricsInt().bottom;
    }

    public final int getAutoLinkMask() {
        return this.mAutoLinkMask;
    }

    @RemotableViewMethod
    public void setTextSelectHandle(Drawable textSelectHandle) {
        Preconditions.checkNotNull(textSelectHandle, "The text select handle should not be null.");
        this.mTextSelectHandle = textSelectHandle;
        this.mTextSelectHandleRes = 0;
        if (this.mEditor != null) {
            this.mEditor.loadHandleDrawables(true);
        }
    }

    @RemotableViewMethod
    public void setTextSelectHandle(int textSelectHandle) {
        Preconditions.checkArgument(textSelectHandle != 0, "The text select handle should be a valid drawable resource id.");
        setTextSelectHandle(this.mContext.getDrawable(textSelectHandle));
    }

    public Drawable getTextSelectHandle() {
        if (this.mTextSelectHandle == null && this.mTextSelectHandleRes != 0) {
            this.mTextSelectHandle = this.mContext.getDrawable(this.mTextSelectHandleRes);
        }
        return this.mTextSelectHandle;
    }

    @RemotableViewMethod
    public void setTextSelectHandleLeft(Drawable textSelectHandleLeft) {
        Preconditions.checkNotNull(textSelectHandleLeft, "The left text select handle should not be null.");
        this.mTextSelectHandleLeft = textSelectHandleLeft;
        this.mTextSelectHandleLeftRes = 0;
        if (this.mEditor != null) {
            this.mEditor.loadHandleDrawables(true);
        }
    }

    @RemotableViewMethod
    public void setTextSelectHandleLeft(int textSelectHandleLeft) {
        Preconditions.checkArgument(textSelectHandleLeft != 0, "The text select left handle should be a valid drawable resource id.");
        setTextSelectHandleLeft(this.mContext.getDrawable(textSelectHandleLeft));
    }

    public Drawable getTextSelectHandleLeft() {
        if (this.mTextSelectHandleLeft == null && this.mTextSelectHandleLeftRes != 0) {
            this.mTextSelectHandleLeft = this.mContext.getDrawable(this.mTextSelectHandleLeftRes);
        }
        return this.mTextSelectHandleLeft;
    }

    @RemotableViewMethod
    public void setTextSelectHandleRight(Drawable textSelectHandleRight) {
        Preconditions.checkNotNull(textSelectHandleRight, "The right text select handle should not be null.");
        this.mTextSelectHandleRight = textSelectHandleRight;
        this.mTextSelectHandleRightRes = 0;
        if (this.mEditor != null) {
            this.mEditor.loadHandleDrawables(true);
        }
    }

    @RemotableViewMethod
    public void setTextSelectHandleRight(int textSelectHandleRight) {
        Preconditions.checkArgument(textSelectHandleRight != 0, "The text select right handle should be a valid drawable resource id.");
        setTextSelectHandleRight(this.mContext.getDrawable(textSelectHandleRight));
    }

    public Drawable getTextSelectHandleRight() {
        if (this.mTextSelectHandleRight == null && this.mTextSelectHandleRightRes != 0) {
            this.mTextSelectHandleRight = this.mContext.getDrawable(this.mTextSelectHandleRightRes);
        }
        return this.mTextSelectHandleRight;
    }

    public void setTextCursorDrawable(Drawable textCursorDrawable) {
        this.mCursorDrawable = textCursorDrawable;
        this.mCursorDrawableRes = 0;
        if (this.mEditor != null) {
            this.mEditor.loadCursorDrawable();
        }
    }

    public void setTextCursorDrawable(int textCursorDrawable) {
        setTextCursorDrawable(textCursorDrawable != 0 ? this.mContext.getDrawable(textCursorDrawable) : null);
    }

    public Drawable getTextCursorDrawable() {
        if (this.mCursorDrawable == null && this.mCursorDrawableRes != 0) {
            this.mCursorDrawable = this.mContext.getDrawable(this.mCursorDrawableRes);
        }
        return this.mCursorDrawable;
    }

    public void setTextAppearance(int resId) {
        setTextAppearance(this.mContext, resId);
    }

    @Deprecated
    public void setTextAppearance(Context context, int resId) {
        TypedArray ta = context.obtainStyledAttributes(resId, R.styleable.TextAppearance);
        TextAppearanceAttributes attributes = new TextAppearanceAttributes();
        readTextAppearance(context, ta, attributes, false);
        ta.recycle();
        applyTextAppearance(attributes);
        this.mFontFamily = attributes.mFontFamily;
    }

    private static class TextAppearanceAttributes {
        boolean mAllCaps;
        boolean mElegant;
        boolean mFallbackLineSpacing;
        int mFocusedSearchResultHighlightColor;
        String mFontFamily;
        boolean mFontFamilyExplicit;
        String mFontFeatureSettings;
        Typeface mFontTypeface;
        String mFontVariationSettings;
        int mFontWeight;
        boolean mHasElegant;
        boolean mHasFallbackLineSpacing;
        boolean mHasLetterSpacing;
        boolean mHasLineBreakStyle;
        boolean mHasLineBreakWordStyle;
        float mLetterSpacing;
        int mLineBreakStyle;
        int mLineBreakWordStyle;
        int mSearchResultHighlightColor;
        int mShadowColor;
        float mShadowDx;
        float mShadowDy;
        float mShadowRadius;
        ColorStateList mTextColor;
        int mTextColorHighlight;
        ColorStateList mTextColorHint;
        ColorStateList mTextColorLink;
        LocaleList mTextLocales;
        int mTextSize;
        int mTextSizeUnit;
        int mTextStyle;
        int mTypefaceIndex;

        private TextAppearanceAttributes() {
            this.mTextColorHighlight = 0;
            this.mSearchResultHighlightColor = 0;
            this.mFocusedSearchResultHighlightColor = 0;
            this.mTextColor = null;
            this.mTextColorHint = null;
            this.mTextColorLink = null;
            this.mTextSize = -1;
            this.mTextSizeUnit = -1;
            this.mTextLocales = null;
            this.mFontFamily = null;
            this.mFontTypeface = null;
            this.mFontFamilyExplicit = false;
            this.mTypefaceIndex = -1;
            this.mTextStyle = 0;
            this.mFontWeight = -1;
            this.mAllCaps = false;
            this.mShadowColor = 0;
            this.mShadowDx = 0.0f;
            this.mShadowDy = 0.0f;
            this.mShadowRadius = 0.0f;
            this.mHasElegant = false;
            this.mElegant = false;
            this.mHasFallbackLineSpacing = false;
            this.mFallbackLineSpacing = false;
            this.mHasLetterSpacing = false;
            this.mLetterSpacing = 0.0f;
            this.mFontFeatureSettings = null;
            this.mFontVariationSettings = null;
            this.mHasLineBreakStyle = false;
            this.mHasLineBreakWordStyle = false;
            this.mLineBreakStyle = 0;
            this.mLineBreakWordStyle = 0;
        }

        public String toString() {
            return "TextAppearanceAttributes {\n    mTextColorHighlight:" + this.mTextColorHighlight + "\n    mSearchResultHighlightColor: " + this.mSearchResultHighlightColor + "\n    mFocusedSearchResultHighlightColor: " + this.mFocusedSearchResultHighlightColor + "\n    mTextColor:" + this.mTextColor + "\n    mTextColorHint:" + this.mTextColorHint + "\n    mTextColorLink:" + this.mTextColorLink + "\n    mTextSize:" + this.mTextSize + "\n    mTextSizeUnit:" + this.mTextSizeUnit + "\n    mTextLocales:" + this.mTextLocales + "\n    mFontFamily:" + this.mFontFamily + "\n    mFontTypeface:" + this.mFontTypeface + "\n    mFontFamilyExplicit:" + this.mFontFamilyExplicit + "\n    mTypefaceIndex:" + this.mTypefaceIndex + "\n    mTextStyle:" + this.mTextStyle + "\n    mFontWeight:" + this.mFontWeight + "\n    mAllCaps:" + this.mAllCaps + "\n    mShadowColor:" + this.mShadowColor + "\n    mShadowDx:" + this.mShadowDx + "\n    mShadowDy:" + this.mShadowDy + "\n    mShadowRadius:" + this.mShadowRadius + "\n    mHasElegant:" + this.mHasElegant + "\n    mElegant:" + this.mElegant + "\n    mHasFallbackLineSpacing:" + this.mHasFallbackLineSpacing + "\n    mFallbackLineSpacing:" + this.mFallbackLineSpacing + "\n    mHasLetterSpacing:" + this.mHasLetterSpacing + "\n    mLetterSpacing:" + this.mLetterSpacing + "\n    mFontFeatureSettings:" + this.mFontFeatureSettings + "\n    mFontVariationSettings:" + this.mFontVariationSettings + "\n    mHasLineBreakStyle:" + this.mHasLineBreakStyle + "\n    mHasLineBreakWordStyle:" + this.mHasLineBreakWordStyle + "\n    mLineBreakStyle:" + this.mLineBreakStyle + "\n    mLineBreakWordStyle:" + this.mLineBreakWordStyle + "\n}";
        }
    }

    private void readTextAppearance(Context context, TypedArray appearance, TextAppearanceAttributes attributes, boolean styleArray) {
        int n = appearance.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = appearance.getIndex(i);
            int index = attr;
            if (!styleArray || (index = sAppearanceValues.get(attr, -1)) != -1) {
                switch (index) {
                    case 0:
                        attributes.mTextSize = appearance.getDimensionPixelSize(attr, attributes.mTextSize);
                        attributes.mTextSizeUnit = appearance.peekValue(attr).getComplexUnit();
                        break;
                    case 1:
                        attributes.mTypefaceIndex = appearance.getInt(attr, attributes.mTypefaceIndex);
                        if (attributes.mTypefaceIndex != -1 && !attributes.mFontFamilyExplicit) {
                            attributes.mFontFamily = null;
                            break;
                        }
                        break;
                    case 2:
                        attributes.mTextStyle = appearance.getInt(attr, attributes.mTextStyle);
                        break;
                    case 3:
                        attributes.mTextColor = appearance.getColorStateList(attr);
                        break;
                    case 4:
                        attributes.mTextColorHighlight = appearance.getColor(attr, attributes.mTextColorHighlight);
                        break;
                    case 5:
                        attributes.mTextColorHint = appearance.getColorStateList(attr);
                        break;
                    case 6:
                        attributes.mTextColorLink = appearance.getColorStateList(attr);
                        break;
                    case 7:
                        attributes.mShadowColor = appearance.getInt(attr, attributes.mShadowColor);
                        break;
                    case 8:
                        attributes.mShadowDx = appearance.getFloat(attr, attributes.mShadowDx);
                        break;
                    case 9:
                        attributes.mShadowDy = appearance.getFloat(attr, attributes.mShadowDy);
                        break;
                    case 10:
                        attributes.mShadowRadius = appearance.getFloat(attr, attributes.mShadowRadius);
                        break;
                    case 11:
                        attributes.mAllCaps = appearance.getBoolean(attr, attributes.mAllCaps);
                        break;
                    case 12:
                        if (!context.isRestricted() && context.canLoadUnsafeResources()) {
                            try {
                                attributes.mFontTypeface = appearance.getFont(attr);
                            } catch (Resources.NotFoundException | UnsupportedOperationException e) {
                            }
                        }
                        if (attributes.mFontTypeface == null) {
                            attributes.mFontFamily = appearance.getString(attr);
                        }
                        attributes.mFontFamilyExplicit = true;
                        break;
                    case 13:
                        attributes.mHasElegant = true;
                        attributes.mElegant = appearance.getBoolean(attr, attributes.mElegant);
                        break;
                    case 14:
                        attributes.mHasLetterSpacing = true;
                        attributes.mLetterSpacing = appearance.getFloat(attr, attributes.mLetterSpacing);
                        break;
                    case 15:
                        attributes.mFontFeatureSettings = appearance.getString(attr);
                        break;
                    case 16:
                        attributes.mFontVariationSettings = appearance.getString(attr);
                        break;
                    case 17:
                        attributes.mHasFallbackLineSpacing = true;
                        attributes.mFallbackLineSpacing = appearance.getBoolean(attr, attributes.mFallbackLineSpacing);
                        break;
                    case 18:
                        attributes.mFontWeight = appearance.getInt(attr, attributes.mFontWeight);
                        break;
                    case 19:
                        String localeString = appearance.getString(attr);
                        if (localeString != null) {
                            LocaleList localeList = LocaleList.forLanguageTags(localeString);
                            if (localeList.isEmpty()) {
                                break;
                            } else {
                                attributes.mTextLocales = localeList;
                                break;
                            }
                        } else {
                            break;
                        }
                    case 20:
                        attributes.mHasLineBreakStyle = true;
                        attributes.mLineBreakStyle = appearance.getInt(attr, attributes.mLineBreakStyle);
                        break;
                    case 21:
                        attributes.mHasLineBreakWordStyle = true;
                        attributes.mLineBreakWordStyle = appearance.getInt(attr, attributes.mLineBreakWordStyle);
                        break;
                    case 22:
                        attributes.mSearchResultHighlightColor = appearance.getColor(attr, attributes.mSearchResultHighlightColor);
                        break;
                    case 23:
                        attributes.mFocusedSearchResultHighlightColor = appearance.getColor(attr, attributes.mFocusedSearchResultHighlightColor);
                        break;
                }
            }
        }
    }

    private void applyTextAppearance(TextAppearanceAttributes attributes) {
        if (attributes.mTextColor != null) {
            setTextColor(attributes.mTextColor);
        }
        if (attributes.mTextColorHint != null) {
            setHintTextColor(attributes.mTextColorHint);
        }
        if (attributes.mTextColorLink != null) {
            setLinkTextColor(attributes.mTextColorLink);
        }
        if (attributes.mTextColorHighlight != 0) {
            setHighlightColor(attributes.mTextColorHighlight);
        }
        if (attributes.mSearchResultHighlightColor != 0) {
            setSearchResultHighlightColor(attributes.mSearchResultHighlightColor);
        }
        if (attributes.mFocusedSearchResultHighlightColor != 0) {
            setFocusedSearchResultHighlightColor(attributes.mFocusedSearchResultHighlightColor);
        }
        if (attributes.mTextSize != -1) {
            this.mTextSizeUnit = attributes.mTextSizeUnit;
            setRawTextSize(attributes.mTextSize, true);
        }
        if (attributes.mTextLocales != null) {
            setTextLocales(attributes.mTextLocales);
        }
        if (attributes.mTypefaceIndex != -1 && !attributes.mFontFamilyExplicit) {
            attributes.mFontFamily = null;
        }
        setTypefaceFromAttrs(attributes.mFontTypeface, attributes.mFontFamily, attributes.mTypefaceIndex, attributes.mTextStyle, attributes.mFontWeight);
        if (attributes.mShadowColor != 0) {
            setShadowLayer(attributes.mShadowRadius, attributes.mShadowDx, attributes.mShadowDy, attributes.mShadowColor);
        }
        if (attributes.mAllCaps) {
            setTransformationMethod(new AllCapsTransformationMethod(getContext()));
        }
        if (attributes.mHasElegant) {
            setElegantTextHeight(attributes.mElegant);
        }
        if (attributes.mHasFallbackLineSpacing) {
            setFallbackLineSpacing(attributes.mFallbackLineSpacing);
        }
        if (attributes.mHasLetterSpacing) {
            setLetterSpacing(attributes.mLetterSpacing);
        }
        if (attributes.mFontFeatureSettings != null) {
            setFontFeatureSettings(attributes.mFontFeatureSettings);
        }
        if (attributes.mFontVariationSettings != null) {
            setFontVariationSettings(attributes.mFontVariationSettings);
        }
        if (attributes.mHasLineBreakStyle || attributes.mHasLineBreakWordStyle) {
            updateLineBreakConfigFromTextAppearance(attributes.mHasLineBreakStyle, attributes.mHasLineBreakWordStyle, attributes.mLineBreakStyle, attributes.mLineBreakWordStyle);
        }
    }

    private void updateLineBreakConfigFromTextAppearance(boolean isLineBreakStyleSpecified, boolean isLineBreakWordStyleSpecified, int lineBreakStyle, int lineBreakWordStyle) {
        boolean updated = false;
        if (isLineBreakStyleSpecified && this.mLineBreakStyle != lineBreakStyle) {
            this.mLineBreakStyle = lineBreakStyle;
            updated = true;
        }
        if (isLineBreakWordStyleSpecified && this.mLineBreakWordStyle != lineBreakWordStyle) {
            this.mLineBreakWordStyle = lineBreakWordStyle;
            updated = true;
        }
        if (updated && this.mLayout != null) {
            nullLayouts();
            requestLayout();
            invalidate();
        }
    }

    public Locale getTextLocale() {
        return this.mTextPaint.getTextLocale();
    }

    public LocaleList getTextLocales() {
        return this.mTextPaint.getTextLocales();
    }

    private void changeListenerLocaleTo(Locale locale) {
        KeyListener listener;
        if (!this.mListenerChanged && this.mEditor != null) {
            KeyListener listener2 = this.mEditor.mKeyListener;
            if (listener2 instanceof DigitsKeyListener) {
                listener = DigitsKeyListener.getInstance(locale, (DigitsKeyListener) listener2);
            } else if (listener2 instanceof DateKeyListener) {
                listener = DateKeyListener.getInstance(locale);
            } else if (listener2 instanceof TimeKeyListener) {
                listener = TimeKeyListener.getInstance(locale);
            } else if (listener2 instanceof DateTimeKeyListener) {
                listener = DateTimeKeyListener.getInstance(locale);
            } else {
                return;
            }
            boolean wasPasswordType = isPasswordInputType(this.mEditor.mInputType);
            setKeyListenerOnly(listener);
            setInputTypeFromEditor();
            if (wasPasswordType) {
                int newInputClass = this.mEditor.mInputType & 15;
                if (newInputClass == 1) {
                    this.mEditor.mInputType |= 128;
                } else if (newInputClass == 2) {
                    this.mEditor.mInputType |= 16;
                }
            }
        }
    }

    public void setTextLocale(Locale locale) {
        this.mLocalesChanged = true;
        this.mTextPaint.setTextLocale(locale);
        if (this.mLayout != null) {
            nullLayouts();
            requestLayout();
            invalidate();
        }
    }

    public void setTextLocales(LocaleList locales) {
        this.mLocalesChanged = true;
        this.mTextPaint.setTextLocales(locales);
        if (this.mLayout != null) {
            nullLayouts();
            requestLayout();
            invalidate();
        }
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (this.mCursorThicknessScale != newConfig.semCursorThicknessScale) {
            this.mCursorThicknessScale = newConfig.semCursorThicknessScale;
        }
        boolean buttonShapeEnabled = newConfig.semButtonShapeEnabled == 1;
        if (this.mButtonShapeSettingEnabled != buttonShapeEnabled) {
            this.mButtonShapeSettingEnabled = buttonShapeEnabled;
        }
        boolean isNightMode = (newConfig.uiMode & 48) == 32;
        if (this.mIsNightMode != isNightMode) {
            this.mIsNightMode = isNightMode;
        }
        if (!this.mLocalesChanged) {
            this.mTextPaint.setTextLocales(LocaleList.getDefault());
            if (this.mLayout != null) {
                nullLayouts();
                requestLayout();
                invalidate();
            }
        }
        if (this.mFontWeightAdjustment != newConfig.fontWeightAdjustment || (newConfig.fontWeightAdjustment > 0 && newConfig.FlipFont > 0)) {
            this.mFontWeightAdjustment = newConfig.fontWeightAdjustment;
            setTypeface(getTypeface());
        }
        InputMethodManager imm = getInputMethodManager();
        if (this.mLastOrientation != newConfig.orientation && imm != null && imm.hasActiveInputConnection(this)) {
            imm.restartInput(this);
        }
        this.mLastOrientation = newConfig.orientation;
        if (!(this instanceof ExtractEditText) && this.mEditor != null) {
            this.mEditor.onScrollChanged();
        }
        if (ViewRune.WIDGET_PEN_SUPPORTED && this.mhasMultiSelection) {
            clearAllMultiSelection();
        }
    }

    @ViewDebug.ExportedProperty(category = "text")
    public float getTextSize() {
        return this.mTextPaint.getTextSize();
    }

    @ViewDebug.ExportedProperty(category = "text")
    public float getScaledTextSize() {
        return this.mTextPaint.getTextSize() / this.mTextPaint.density;
    }

    @ViewDebug.ExportedProperty(category = "text", mapping = {@ViewDebug.IntToString(from = 0, to = SQLiteDatabase.SYNC_MODE_NORMAL), @ViewDebug.IntToString(from = 1, to = "BOLD"), @ViewDebug.IntToString(from = 2, to = "ITALIC"), @ViewDebug.IntToString(from = 3, to = "BOLD_ITALIC")})
    public int getTypefaceStyle() {
        Typeface typeface = this.mTextPaint.getTypeface();
        if (typeface != null) {
            return typeface.getStyle();
        }
        return 0;
    }

    @RemotableViewMethod
    public void setTextSize(float size) {
        setTextSize(2, size);
    }

    public void setTextSize(int unit, float size) {
        if (!isAutoSizeEnabled()) {
            setTextSizeInternal(unit, size, true);
        }
    }

    private DisplayMetrics getDisplayMetricsOrSystem() {
        Resources r;
        Context c = getContext();
        if (c == null) {
            r = Resources.getSystem();
        } else {
            r = c.getResources();
        }
        return r.getDisplayMetrics();
    }

    private void setTextSizeInternal(int unit, float size, boolean shouldRequestLayout) {
        this.mTextSizeUnit = unit;
        setRawTextSize(TypedValue.applyDimension(unit, size, getDisplayMetricsOrSystem()), shouldRequestLayout);
    }

    private void setRawTextSize(float size, boolean shouldRequestLayout) {
        if (size != this.mTextPaint.getTextSize()) {
            this.mTextPaint.setTextSize(size);
            maybeRecalculateLineHeight();
            if (shouldRequestLayout && this.mLayout != null) {
                this.mNeedsAutoSizeText = false;
                nullLayouts();
                requestLayout();
                invalidate();
            }
        }
    }

    public int getTextSizeUnit() {
        return this.mTextSizeUnit;
    }

    public float getTextScaleX() {
        return this.mTextPaint.getTextScaleX();
    }

    @RemotableViewMethod
    public void setTextScaleX(float size) {
        if (size != this.mTextPaint.getTextScaleX()) {
            this.mUserSetTextScaleX = true;
            this.mTextPaint.setTextScaleX(size);
            if (this.mLayout != null) {
                nullLayouts();
                requestLayout();
                invalidate();
            }
        }
    }

    public void setTypeface(Typeface tf) {
        this.mOriginalTypeface = tf;
        if (this.mFontWeightAdjustment != 0 && this.mFontWeightAdjustment != Integer.MAX_VALUE) {
            if (tf == null) {
                if (Flags.fixNullTypefaceBolding()) {
                    tf = Typeface.DEFAULT_BOLD;
                } else {
                    tf = Typeface.DEFAULT;
                }
            } else {
                int newWeight = Math.min(Math.max(tf.getWeight() + this.mFontWeightAdjustment, 1), 1000);
                int typefaceStyle = tf != null ? tf.getStyle() : 0;
                boolean italic = (typefaceStyle & 2) != 0;
                tf = Typeface.create(tf, newWeight, italic);
            }
        }
        if (this.mTextPaint.getTypeface() != tf) {
            this.mTextPaint.setTypeface(tf);
            if (this.mLayout != null) {
                nullLayouts();
                requestLayout();
                invalidate();
            }
        }
    }

    public Typeface getTypeface() {
        if (Typeface.semIsDefaultFontStyle()) {
            return this.mOriginalTypeface;
        }
        return Typeface.defaultFromStyle(0);
    }

    public void setElegantTextHeight(boolean elegant) {
        if (elegant != this.mTextPaint.isElegantTextHeight()) {
            this.mTextPaint.setElegantTextHeight(elegant);
            if (this.mLayout != null) {
                nullLayouts();
                requestLayout();
                invalidate();
            }
        }
    }

    public void setFallbackLineSpacing(boolean enabled) {
        int fallbackStrategy;
        if (enabled) {
            if (CompatChanges.isChangeEnabled(BORINGLAYOUT_FALLBACK_LINESPACING)) {
                fallbackStrategy = 2;
            } else {
                fallbackStrategy = 1;
            }
        } else {
            fallbackStrategy = 0;
        }
        if (this.mUseFallbackLineSpacing != fallbackStrategy) {
            this.mUseFallbackLineSpacing = fallbackStrategy;
            if (this.mLayout != null) {
                nullLayouts();
                requestLayout();
                invalidate();
            }
        }
    }

    public void setUseBoundsForWidth(boolean useBoundsForWidth) {
        if (this.mUseBoundsForWidth != useBoundsForWidth) {
            this.mUseBoundsForWidth = useBoundsForWidth;
            if (this.mLayout != null) {
                nullLayouts();
                requestLayout();
                invalidate();
            }
        }
    }

    public boolean getUseBoundsForWidth() {
        return this.mUseBoundsForWidth;
    }

    public void setShiftDrawingOffsetForStartOverhang(boolean shiftDrawingOffsetForStartOverhang) {
        if (this.mShiftDrawingOffsetForStartOverhang != shiftDrawingOffsetForStartOverhang) {
            this.mShiftDrawingOffsetForStartOverhang = shiftDrawingOffsetForStartOverhang;
            if (this.mLayout != null) {
                nullLayouts();
                requestLayout();
                invalidate();
            }
        }
    }

    public boolean getShiftDrawingOffsetForStartOverhang() {
        return this.mShiftDrawingOffsetForStartOverhang;
    }

    public void setMinimumFontMetrics(Paint.FontMetrics minimumFontMetrics) {
        this.mMinimumFontMetrics = minimumFontMetrics;
    }

    public Paint.FontMetrics getMinimumFontMetrics() {
        return this.mMinimumFontMetrics;
    }

    public boolean isLocalePreferredLineHeightForMinimumUsed() {
        return this.mUseLocalePreferredLineHeightForMinimum;
    }

    public void setLocalePreferredLineHeightForMinimumUsed(boolean flag) {
        this.mUseLocalePreferredLineHeightForMinimum = flag;
    }

    public boolean isFallbackLineSpacing() {
        return this.mUseFallbackLineSpacing != 0;
    }

    private boolean isFallbackLineSpacingForBoringLayout() {
        return this.mUseFallbackLineSpacing == 2;
    }

    boolean isFallbackLineSpacingForStaticLayout() {
        return this.mUseFallbackLineSpacing == 2 || this.mUseFallbackLineSpacing == 1;
    }

    public boolean isElegantTextHeight() {
        return this.mTextPaint.isElegantTextHeight();
    }

    public float getLetterSpacing() {
        return this.mTextPaint.getLetterSpacing();
    }

    @RemotableViewMethod
    public void setLetterSpacing(float letterSpacing) {
        if (letterSpacing != this.mTextPaint.getLetterSpacing()) {
            this.mTextPaint.setLetterSpacing(letterSpacing);
            if (this.mLayout != null) {
                nullLayouts();
                requestLayout();
                invalidate();
            }
        }
    }

    public String getFontFeatureSettings() {
        return this.mTextPaint.getFontFeatureSettings();
    }

    public String getFontVariationSettings() {
        return this.mTextPaint.getFontVariationSettings();
    }

    public void setBreakStrategy(int breakStrategy) {
        this.mBreakStrategy = breakStrategy;
        if (this.mLayout != null) {
            nullLayouts();
            requestLayout();
            invalidate();
        }
    }

    public int getBreakStrategy() {
        return this.mBreakStrategy;
    }

    public void setHyphenationFrequency(int hyphenationFrequency) {
        this.mHyphenationFrequency = hyphenationFrequency;
        if (this.mLayout != null) {
            nullLayouts();
            requestLayout();
            invalidate();
        }
    }

    public int getHyphenationFrequency() {
        return this.mHyphenationFrequency;
    }

    public void setLineBreakStyle(int lineBreakStyle) {
        if (this.mLineBreakStyle != lineBreakStyle) {
            this.mLineBreakStyle = lineBreakStyle;
            if (this.mLayout != null) {
                nullLayouts();
                requestLayout();
                invalidate();
            }
        }
    }

    public void setLineBreakWordStyle(int lineBreakWordStyle) {
        if (this.mLineBreakWordStyle != lineBreakWordStyle) {
            this.mLineBreakWordStyle = lineBreakWordStyle;
            if (this.mLayout != null) {
                nullLayouts();
                requestLayout();
                invalidate();
            }
        }
    }

    public int getLineBreakStyle() {
        return this.mLineBreakStyle;
    }

    public int getLineBreakWordStyle() {
        return this.mLineBreakWordStyle;
    }

    public PrecomputedText.Params getTextMetricsParams() {
        return new PrecomputedText.Params(new TextPaint(this.mTextPaint), LineBreakConfig.getLineBreakConfig(this.mLineBreakStyle, this.mLineBreakWordStyle), getTextDirectionHeuristic(), this.mBreakStrategy, this.mHyphenationFrequency);
    }

    public void setTextMetricsParams(PrecomputedText.Params params) {
        this.mTextPaint.set(params.getTextPaint());
        this.mUserSetTextScaleX = true;
        this.mTextDir = params.getTextDirection();
        this.mBreakStrategy = params.getBreakStrategy();
        this.mHyphenationFrequency = params.getHyphenationFrequency();
        LineBreakConfig lineBreakConfig = params.getLineBreakConfig();
        this.mLineBreakStyle = LineBreakConfig.getResolvedLineBreakStyle(lineBreakConfig);
        this.mLineBreakWordStyle = LineBreakConfig.getResolvedLineBreakWordStyle(lineBreakConfig);
        if (this.mLayout != null) {
            nullLayouts();
            requestLayout();
            invalidate();
        }
    }

    @RemotableViewMethod
    public void setJustificationMode(int justificationMode) {
        this.mJustificationMode = justificationMode;
        if (this.mLayout != null) {
            nullLayouts();
            requestLayout();
            invalidate();
        }
    }

    public int getJustificationMode() {
        return this.mJustificationMode;
    }

    @RemotableViewMethod
    public void setFontFeatureSettings(String fontFeatureSettings) {
        if (fontFeatureSettings != this.mTextPaint.getFontFeatureSettings()) {
            this.mTextPaint.setFontFeatureSettings(fontFeatureSettings);
            if (this.mLayout != null) {
                nullLayouts();
                requestLayout();
                invalidate();
            }
        }
    }

    @RemotableViewMethod
    public boolean setFontVariationSettings(String fontVariationSettings) {
        String existingSettings = this.mTextPaint.getFontVariationSettings();
        if (fontVariationSettings != existingSettings) {
            if (fontVariationSettings != null && fontVariationSettings.equals(existingSettings)) {
                return true;
            }
            boolean effective = this.mTextPaint.setFontVariationSettings(fontVariationSettings);
            if (effective && this.mLayout != null) {
                nullLayouts();
                requestLayout();
                invalidate();
            }
            return effective;
        }
        return true;
    }

    @RemotableViewMethod
    public void setTextColor(int color) {
        this.mTextColor = ColorStateList.valueOf(color);
        updateTextColors();
    }

    @RemotableViewMethod
    public void setTextColor(ColorStateList colors) {
        if (colors == null) {
            throw new NullPointerException();
        }
        this.mTextColor = colors;
        updateTextColors();
    }

    public final ColorStateList getTextColors() {
        return this.mTextColor;
    }

    public final int getCurrentTextColor() {
        return this.mCurTextColor;
    }

    @RemotableViewMethod
    public void setHighlightColor(int color) {
        if (this.mHighlightColor != color) {
            this.mHighlightColor = color;
            invalidate();
        }
    }

    public int getHighlightColor() {
        return this.mHighlightColor;
    }

    @RemotableViewMethod
    public final void setShowSoftInputOnFocus(boolean show) {
        createEditorIfNeeded();
        this.mEditor.mShowSoftInputOnFocus = show;
    }

    public final boolean getShowSoftInputOnFocus() {
        return this.mEditor == null || this.mEditor.mShowSoftInputOnFocus;
    }

    public void setShadowLayer(float radius, float dx, float dy, int color) {
        this.mTextPaint.setShadowLayer(radius, dx, dy, color);
        this.mShadowRadius = radius;
        this.mShadowDx = dx;
        this.mShadowDy = dy;
        this.mShadowColor = color;
        if (this.mEditor != null) {
            this.mEditor.invalidateTextDisplayList();
            this.mEditor.invalidateHandlesAndActionMode();
        }
        invalidate();
    }

    public float getShadowRadius() {
        return this.mShadowRadius;
    }

    public float getShadowDx() {
        return this.mShadowDx;
    }

    public float getShadowDy() {
        return this.mShadowDy;
    }

    public int getShadowColor() {
        return this.mShadowColor;
    }

    public TextPaint getPaint() {
        return this.mTextPaint;
    }

    @RemotableViewMethod
    public final void setAutoLinkMask(int mask) {
        this.mAutoLinkMask = mask;
    }

    @RemotableViewMethod
    public final void setLinksClickable(boolean whether) {
        this.mLinksClickable = whether;
    }

    public final boolean getLinksClickable() {
        return this.mLinksClickable;
    }

    public URLSpan[] getUrls() {
        if (this.mText instanceof Spanned) {
            return (URLSpan[]) ((Spanned) this.mText).getSpans(0, this.mText.length(), URLSpan.class);
        }
        return new URLSpan[0];
    }

    @RemotableViewMethod
    public final void setHintTextColor(int color) {
        this.mHintTextColor = ColorStateList.valueOf(color);
        updateTextColors();
    }

    public final void setHintTextColor(ColorStateList colors) {
        this.mHintTextColor = colors;
        updateTextColors();
    }

    public final ColorStateList getHintTextColors() {
        return this.mHintTextColor;
    }

    public final int getCurrentHintTextColor() {
        return this.mHintTextColor != null ? this.mCurHintTextColor : this.mCurTextColor;
    }

    @RemotableViewMethod
    public final void setLinkTextColor(int color) {
        this.mLinkTextColor = ColorStateList.valueOf(color);
        updateTextColors();
    }

    public final void setLinkTextColor(ColorStateList colors) {
        this.mLinkTextColor = colors;
        updateTextColors();
    }

    public final ColorStateList getLinkTextColors() {
        return this.mLinkTextColor;
    }

    @RemotableViewMethod
    public void setGravity(int gravity) {
        if ((gravity & Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK) == 0) {
            gravity |= Gravity.START;
        }
        if ((gravity & 112) == 0) {
            gravity |= 48;
        }
        boolean newLayout = false;
        if ((gravity & Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK) != (8388615 & this.mGravity)) {
            newLayout = true;
        }
        if (gravity != this.mGravity) {
            invalidate();
        }
        this.mGravity = gravity;
        if (this.mLayout != null && newLayout) {
            int want = this.mLayout.getWidth();
            int hintWant = this.mHintLayout == null ? 0 : this.mHintLayout.getWidth();
            makeNewLayout(want, hintWant, UNKNOWN_BORING, UNKNOWN_BORING, ((this.mRight - this.mLeft) - getCompoundPaddingLeft()) - getCompoundPaddingRight(), true);
        }
    }

    public int getGravity() {
        return this.mGravity;
    }

    public int getPaintFlags() {
        return this.mTextPaint.getFlags();
    }

    @RemotableViewMethod
    public void setPaintFlags(int flags) {
        if (this.mTextPaint.getFlags() != flags) {
            this.mTextPaint.setFlags(flags);
            if (this.mLayout != null) {
                nullLayouts();
                requestLayout();
                invalidate();
            }
        }
    }

    public void setHorizontallyScrolling(boolean whether) {
        if (this.mHorizontallyScrolling != whether) {
            this.mHorizontallyScrolling = whether;
            if (this.mLayout != null) {
                nullLayouts();
                requestLayout();
                invalidate();
            }
        }
    }

    public final boolean isHorizontallyScrollable() {
        return this.mHorizontallyScrolling;
    }

    public boolean getHorizontallyScrolling() {
        return this.mHorizontallyScrolling;
    }

    @RemotableViewMethod
    public void setMinLines(int minLines) {
        this.mMinimum = minLines;
        this.mMinMode = 1;
        requestLayout();
        invalidate();
    }

    public int getMinLines() {
        if (this.mMinMode == 1) {
            return this.mMinimum;
        }
        return -1;
    }

    @RemotableViewMethod
    public void setMinHeight(int minPixels) {
        this.mMinimum = minPixels;
        this.mMinMode = 2;
        requestLayout();
        invalidate();
    }

    public int getMinHeight() {
        if (this.mMinMode == 2) {
            return this.mMinimum;
        }
        return -1;
    }

    @RemotableViewMethod
    public void setMaxLines(int maxLines) {
        this.mMaximum = maxLines;
        this.mMaxMode = 1;
        requestLayout();
        invalidate();
    }

    public int getMaxLines() {
        if (this.mMaxMode == 1) {
            return this.mMaximum;
        }
        return -1;
    }

    @RemotableViewMethod
    public void setMaxHeight(int maxPixels) {
        this.mMaximum = maxPixels;
        this.mMaxMode = 2;
        requestLayout();
        invalidate();
    }

    public int getMaxHeight() {
        if (this.mMaxMode == 2) {
            return this.mMaximum;
        }
        return -1;
    }

    @RemotableViewMethod
    public void setLines(int lines) {
        this.mMinimum = lines;
        this.mMaximum = lines;
        this.mMinMode = 1;
        this.mMaxMode = 1;
        this.mTextEffectLines = lines;
        requestLayout();
        invalidate();
    }

    @RemotableViewMethod
    public void setHeight(int pixels) {
        this.mMinimum = pixels;
        this.mMaximum = pixels;
        this.mMinMode = 2;
        this.mMaxMode = 2;
        requestLayout();
        invalidate();
    }

    @RemotableViewMethod
    public void setMinEms(int minEms) {
        this.mMinWidth = minEms;
        this.mMinWidthMode = 1;
        requestLayout();
        invalidate();
    }

    public int getMinEms() {
        if (this.mMinWidthMode == 1) {
            return this.mMinWidth;
        }
        return -1;
    }

    @RemotableViewMethod
    public void setMinWidth(int minPixels) {
        this.mMinWidth = minPixels;
        this.mMinWidthMode = 2;
        requestLayout();
        invalidate();
    }

    public int getMinWidth() {
        if (this.mMinWidthMode == 2) {
            return this.mMinWidth;
        }
        return -1;
    }

    @RemotableViewMethod
    public void setMaxEms(int maxEms) {
        this.mMaxWidth = maxEms;
        this.mMaxWidthMode = 1;
        requestLayout();
        invalidate();
    }

    public int getMaxEms() {
        if (this.mMaxWidthMode == 1) {
            return this.mMaxWidth;
        }
        return -1;
    }

    @RemotableViewMethod
    public void setMaxWidth(int maxPixels) {
        this.mMaxWidth = maxPixels;
        this.mMaxWidthMode = 2;
        requestLayout();
        invalidate();
    }

    public int getMaxWidth() {
        if (this.mMaxWidthMode == 2) {
            return this.mMaxWidth;
        }
        return -1;
    }

    @RemotableViewMethod
    public void setEms(int ems) {
        this.mMinWidth = ems;
        this.mMaxWidth = ems;
        this.mMinWidthMode = 1;
        this.mMaxWidthMode = 1;
        requestLayout();
        invalidate();
    }

    @RemotableViewMethod
    public void setWidth(int pixels) {
        this.mMinWidth = pixels;
        this.mMaxWidth = pixels;
        this.mMinWidthMode = 2;
        this.mMaxWidthMode = 2;
        requestLayout();
        invalidate();
    }

    public void setLineSpacing(float add, float mult) {
        if (this.mSpacingAdd != add || this.mSpacingMult != mult) {
            this.mSpacingAdd = add;
            this.mSpacingMult = mult;
            if (this.mLayout != null) {
                nullLayouts();
                requestLayout();
                invalidate();
            }
        }
    }

    public float getLineSpacingMultiplier() {
        return this.mSpacingMult;
    }

    public float getLineSpacingExtra() {
        return this.mSpacingAdd;
    }

    @RemotableViewMethod
    public void setLineHeight(int lineHeight) {
        setLineHeightPx(lineHeight);
    }

    private void setLineHeightPx(float lineHeight) {
        Preconditions.checkArgumentNonNegative(lineHeight, "Expecting non-negative lineHeight while the input is " + lineHeight);
        int fontHeight = getPaint().getFontMetricsInt(null);
        if (lineHeight != fontHeight) {
            setLineSpacing(lineHeight - fontHeight, 1.0f);
            this.mLineHeightComplexDimen = TypedValue.createComplexDimension(lineHeight, 0);
        }
    }

    @RemotableViewMethod
    public void setLineHeight(int unit, float lineHeight) {
        DisplayMetrics metrics = getDisplayMetricsOrSystem();
        if (!FontScaleConverterFactory.isNonLinearFontScalingActive(getResources().getConfiguration().fontScale) || unit != 2 || this.mTextSizeUnit != 2) {
            float textSizeSp = TypedValue.applyDimension(unit, lineHeight, metrics);
            setLineHeightPx(textSizeSp);
            this.mLineHeightComplexDimen = TypedValue.createComplexDimension(lineHeight, unit);
        } else {
            float textSizePx = getTextSize();
            float textSizeSp2 = TypedValue.convertPixelsToDimension(2, textSizePx, metrics);
            float ratio = lineHeight / textSizeSp2;
            setLineHeightPx(textSizePx * ratio);
            this.mLineHeightComplexDimen = TypedValue.createComplexDimension(lineHeight, unit);
        }
    }

    private void maybeRecalculateLineHeight() {
        int unit;
        if (this.mLineHeightComplexDimen == 0 || (unit = TypedValue.getUnitFromComplexDimension(this.mLineHeightComplexDimen)) != 2) {
            return;
        }
        setLineHeight(unit, TypedValue.complexToFloat(this.mLineHeightComplexDimen));
    }

    public void setHighlights(Highlights highlights) {
        this.mHighlights = highlights;
        this.mHighlightPathsBogus = true;
        invalidate();
    }

    public Highlights getHighlights() {
        return this.mHighlights;
    }

    public void setSearchResultHighlights(int... ranges) {
        if (ranges != null) {
            if (ranges.length % 2 == 1) {
                throw new IllegalArgumentException("Flatten ranges must have even numbered elements");
            }
            for (int j = 0; j < ranges.length / 2; j++) {
                int start = ranges[j * 2];
                int end = ranges[(j * 2) + 1];
                if (start > end) {
                    throw new IllegalArgumentException("Reverse range found in the flatten range: " + start + ", " + end + " at " + j + "-th range");
                }
            }
            this.mHighlightPathsBogus = true;
            this.mSearchResultHighlights = ranges;
            this.mFocusedSearchResultIndex = -1;
            invalidate();
            return;
        }
        this.mSearchResultHighlights = null;
        this.mHighlightPathsBogus = true;
    }

    public int[] getSearchResultHighlights() {
        return this.mSearchResultHighlights;
    }

    public void setFocusedSearchResultIndex(int index) {
        if (this.mSearchResultHighlights == null) {
            throw new IllegalArgumentException("Search result range must be set beforehand.");
        }
        if (index < -1 || index >= this.mSearchResultHighlights.length / 2) {
            throw new IllegalArgumentException("Focused index(" + index + ") must be larger than -1 and less than range count(" + (this.mSearchResultHighlights.length / 2) + NavigationBarInflaterView.KEY_CODE_END);
        }
        this.mFocusedSearchResultIndex = index;
        this.mHighlightPathsBogus = true;
        invalidate();
    }

    public int getFocusedSearchResultIndex() {
        return this.mFocusedSearchResultIndex;
    }

    public void setSearchResultHighlightColor(int color) {
        this.mSearchResultHighlightColor = color;
    }

    public int getSearchResultHighlightColor() {
        return this.mSearchResultHighlightColor;
    }

    public void setFocusedSearchResultHighlightColor(int color) {
        this.mFocusedSearchResultHighlightColor = color;
    }

    public int getFocusedSearchResultHighlightColor() {
        return this.mFocusedSearchResultHighlightColor;
    }

    private void setSelectGesturePreviewHighlight(int start, int end) {
        setGesturePreviewHighlight(start, end, this.mHighlightColor);
    }

    private void setDeleteGesturePreviewHighlight(int start, int end) {
        int color = this.mTextColor.getDefaultColor();
        setGesturePreviewHighlight(start, end, ColorUtils.setAlphaComponent(color, (int) (Color.alpha(color) * 0.2f)));
    }

    private void setGesturePreviewHighlight(int start, int end, int color) {
        this.mGesturePreviewHighlightStart = start;
        this.mGesturePreviewHighlightEnd = end;
        if (this.mGesturePreviewHighlightPaint == null) {
            this.mGesturePreviewHighlightPaint = new Paint();
            this.mGesturePreviewHighlightPaint.setStyle(Paint.Style.FILL);
        }
        this.mGesturePreviewHighlightPaint.setColor(color);
        if (this.mEditor != null) {
            this.mEditor.hideCursorAndSpanControllers();
            this.mEditor.stopTextActionModeWithPreservingSelection();
        }
        this.mHighlightPathsBogus = true;
        invalidate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearGesturePreviewHighlight() {
        this.mGesturePreviewHighlightStart = -1;
        this.mGesturePreviewHighlightEnd = -1;
        this.mHighlightPathsBogus = true;
        invalidate();
    }

    boolean hasGesturePreviewHighlight() {
        return this.mGesturePreviewHighlightStart >= 0;
    }

    public final void append(CharSequence text) {
        append(text, 0, text.length());
    }

    public void append(CharSequence text, int start, int end) {
        if (!(this.mText instanceof Editable)) {
            setText(this.mText, BufferType.EDITABLE);
        }
        ((Editable) this.mText).append(text, start, end);
        if (this.mAutoLinkMask != 0) {
            boolean linksWereAdded = Linkify.addLinks(this.mSpannable, this.mAutoLinkMask);
            if (linksWereAdded && this.mLinksClickable && !textCanBeSelected()) {
                setMovementMethod(LinkMovementMethod.getInstance());
            }
        }
    }

    private void updateTextColors() {
        int color;
        int color2;
        boolean inval = false;
        int[] drawableState = getDrawableState();
        int color3 = this.mTextColor.getColorForState(drawableState, 0);
        this.mButtonShapeColor = color3;
        if (color3 != this.mCurTextColor) {
            this.mCurTextColor = color3;
            inval = true;
        }
        if (this.mLinkTextColor != null && (color2 = this.mLinkTextColor.getColorForState(drawableState, 0)) != this.mTextPaint.linkColor) {
            this.mTextPaint.linkColor = color2;
            inval = true;
        }
        if (this.mHintTextColor != null && (color = this.mHintTextColor.getColorForState(drawableState, 0)) != this.mCurHintTextColor) {
            this.mCurHintTextColor = color;
            if (this.mText.length() == 0) {
                inval = true;
            }
        }
        if (inval) {
            if (this.mEditor != null) {
                this.mEditor.invalidateTextDisplayList();
            }
            invalidate();
        }
    }

    @Override // android.view.View
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if ((this.mTextColor != null && this.mTextColor.isStateful()) || ((this.mHintTextColor != null && this.mHintTextColor.isStateful()) || (this.mLinkTextColor != null && this.mLinkTextColor.isStateful()))) {
            updateTextColors();
        }
        if (this.mDrawables != null) {
            int[] state = getDrawableState();
            for (Drawable dr : this.mDrawables.mShowing) {
                if (dr != null && dr.isStateful() && dr.setState(state)) {
                    invalidateDrawable(dr);
                }
            }
        }
    }

    @Override // android.view.View
    public void drawableHotspotChanged(float x, float y) {
        super.drawableHotspotChanged(x, y);
        if (this.mDrawables != null) {
            for (Drawable dr : this.mDrawables.mShowing) {
                if (dr != null) {
                    dr.setHotspot(x, y);
                }
            }
        }
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        boolean freezesText = getFreezesText();
        boolean hasSelection = false;
        int start = -1;
        int end = -1;
        if (this.mText != null) {
            start = getSelectionStart();
            end = getSelectionEnd();
            if (start >= 0 || end >= 0) {
                hasSelection = true;
            }
        }
        if (freezesText || hasSelection) {
            SavedState ss = new SavedState(superState);
            if (freezesText) {
                if (this.mText instanceof Spanned) {
                    Spannable sp = new SpannableStringBuilder(this.mText);
                    if (this.mEditor != null) {
                        removeMisspelledSpans(sp);
                        sp.removeSpan(this.mEditor.mSuggestionRangeSpan);
                    }
                    ss.text = sp;
                } else {
                    ss.text = this.mText.toString();
                }
            }
            if (hasSelection) {
                ss.selStart = start;
                ss.selEnd = end;
            }
            if (isFocused() && start >= 0 && end >= 0) {
                ss.frozenWithFocus = true;
            }
            ss.error = getError();
            if (this.mEditor != null) {
                ss.editorState = this.mEditor.saveInstanceState();
            }
            return ss;
        }
        return superState;
    }

    void removeMisspelledSpans(Spannable spannable) {
        SuggestionSpan[] suggestionSpans = (SuggestionSpan[]) spannable.getSpans(0, spannable.length(), SuggestionSpan.class);
        for (int i = 0; i < suggestionSpans.length; i++) {
            int flags = suggestionSpans[i].getFlags();
            if ((flags & 1) != 0 && (flags & 2) != 0) {
                spannable.removeSpan(suggestionSpans[i]);
            }
        }
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        if (ss.text != null) {
            lambda$setTextAsync$0(ss.text);
        }
        if (ss.selStart >= 0 && ss.selEnd >= 0 && this.mSpannable != null) {
            int len = this.mText.length();
            if (ss.selStart > len || ss.selEnd > len) {
                String restored = "";
                if (ss.text != null) {
                    restored = "(restored) ";
                }
                Log.e(LOG_TAG, "Saved cursor position " + ss.selStart + "/" + ss.selEnd + " out of range for " + restored + "text " + ((Object) this.mText));
            } else {
                Selection.setSelection(this.mSpannable, ss.selStart, ss.selEnd);
                if (ss.frozenWithFocus) {
                    createEditorIfNeeded();
                    this.mEditor.mFrozenWithFocus = true;
                }
            }
        }
        if (ss.error != null) {
            final CharSequence error = ss.error;
            post(new Runnable() { // from class: android.widget.TextView.1
                @Override // java.lang.Runnable
                public void run() {
                    if (TextView.this.mEditor == null || !TextView.this.mEditor.mErrorWasChanged) {
                        TextView.this.setError(error);
                    }
                }
            });
        }
        if (ss.editorState != null) {
            createEditorIfNeeded();
            this.mEditor.restoreInstanceState(ss.editorState);
        }
    }

    @RemotableViewMethod
    public void setFreezesText(boolean freezesText) {
        this.mFreezesText = freezesText;
    }

    public boolean getFreezesText() {
        return this.mFreezesText;
    }

    public final void setEditableFactory(Editable.Factory factory) {
        this.mEditableFactory = factory;
        lambda$setTextAsync$0(this.mText);
    }

    public final void setSpannableFactory(Spannable.Factory factory) {
        this.mSpannableFactory = factory;
        lambda$setTextAsync$0(this.mText);
    }

    @RemotableViewMethod(asyncImpl = "setTextAsync")
    /* renamed from: setText, reason: merged with bridge method [inline-methods] */
    public final void lambda$setTextAsync$0(CharSequence text) {
        setText(text, this.mBufferType);
    }

    public Runnable setTextAsync(final CharSequence text) {
        return new Runnable() { // from class: android.widget.TextView$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                TextView.this.lambda$setTextAsync$0(text);
            }
        };
    }

    @RemotableViewMethod
    public final void setTextKeepState(CharSequence text) {
        setTextKeepState(text, this.mBufferType);
    }

    public void setText(CharSequence text, BufferType type) {
        setText(text, type, true, 0);
        this.mCharWrapper = null;
    }

    /* JADX WARN: Removed duplicated region for block: B:101:0x01fc  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x0294  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x0299  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x02cb  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x02db  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x02e6  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x02f4  */
    /* JADX WARN: Removed duplicated region for block: B:131:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:132:0x02ed  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x02df  */
    /* JADX WARN: Removed duplicated region for block: B:137:0x0228 A[LOOP:1: B:136:0x0226->B:137:0x0228, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:141:0x0234  */
    /* JADX WARN: Removed duplicated region for block: B:144:0x0248  */
    /* JADX WARN: Removed duplicated region for block: B:147:0x0251  */
    /* JADX WARN: Removed duplicated region for block: B:152:0x026a  */
    /* JADX WARN: Removed duplicated region for block: B:156:0x0264  */
    /* JADX WARN: Removed duplicated region for block: B:158:0x01f0  */
    /* JADX WARN: Removed duplicated region for block: B:168:0x00ae  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00aa  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0156  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0168  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x0172  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x01ed  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void setText(java.lang.CharSequence r19, android.widget.TextView.BufferType r20, boolean r21, int r22) {
        /*
            Method dump skipped, instructions count: 776
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.TextView.setText(java.lang.CharSequence, android.widget.TextView$BufferType, boolean, int):void");
    }

    public final void setText(char[] text, int start, int len) {
        int oldlen = 0;
        if (start < 0 || len < 0 || start + len > text.length) {
            throw new IndexOutOfBoundsException(start + ", " + len);
        }
        if (this.mText != null) {
            oldlen = this.mText.length();
            sendBeforeTextChanged(this.mText, 0, oldlen, len);
        } else {
            sendBeforeTextChanged("", 0, 0, len);
        }
        if (this.mCharWrapper == null) {
            this.mCharWrapper = new CharWrapper(text, start, len);
        } else {
            this.mCharWrapper.set(text, start, len);
        }
        setText(this.mCharWrapper, this.mBufferType, false, oldlen);
    }

    public final void setTextKeepState(CharSequence text, BufferType type) {
        int start = getSelectionStart();
        int end = getSelectionEnd();
        int len = text.length();
        setText(text, type);
        if ((start >= 0 || end >= 0) && this.mSpannable != null) {
            Selection.setSelection(this.mSpannable, Math.max(0, Math.min(start, len)), Math.max(0, Math.min(end, len)));
        }
    }

    @RemotableViewMethod
    public final void setText(int resid) {
        lambda$setTextAsync$0(getContext().getResources().getText(resid));
        this.mTextSetFromXmlOrResourceId = true;
        this.mTextId = resid;
    }

    public final void setText(int resid, BufferType type) {
        setText(getContext().getResources().getText(resid), type);
        this.mTextSetFromXmlOrResourceId = true;
        this.mTextId = resid;
    }

    @RemotableViewMethod
    public final void setHint(CharSequence hint) {
        setHintInternal(hint);
        if (this.mEditor != null && isInputMethodTarget()) {
            this.mEditor.reportExtractedText();
        }
    }

    private void setHintInternal(CharSequence hint) {
        this.mHideHint = false;
        this.mHint = TextUtils.stringOrSpannedString(hint);
        if (this.mLayout != null) {
            checkForRelayout();
        }
        if (this.mText.length() == 0) {
            invalidate();
        }
        if (this.mEditor != null && this.mText.length() == 0 && this.mHint != null) {
            this.mEditor.invalidateTextDisplayList();
        }
    }

    @RemotableViewMethod
    public final void setHint(int resid) {
        this.mHintId = resid;
        setHint(getContext().getResources().getText(resid));
    }

    @ViewDebug.CapturedViewProperty
    public CharSequence getHint() {
        return this.mHint;
    }

    public void hideHint() {
        if (isShowingHint()) {
            this.mHideHint = true;
            invalidate();
        }
    }

    public boolean isSingleLine() {
        return this.mSingleLine;
    }

    private static boolean isMultilineInputType(int type) {
        return (131087 & type) == 131073;
    }

    CharSequence removeSuggestionSpans(CharSequence text) {
        Spannable spannable;
        if (text instanceof Spanned) {
            if (text instanceof Spannable) {
                spannable = (Spannable) text;
            } else {
                spannable = this.mSpannableFactory.newSpannable(text);
            }
            SuggestionSpan[] spans = (SuggestionSpan[]) spannable.getSpans(0, text.length(), SuggestionSpan.class);
            if (spans.length == 0) {
                return text;
            }
            text = spannable;
            for (SuggestionSpan suggestionSpan : spans) {
                spannable.removeSpan(suggestionSpan);
            }
        }
        return text;
    }

    public void setInputType(int type) {
        boolean wasPassword = isPasswordInputType(getInputType());
        boolean wasVisiblePassword = isVisiblePasswordInputType(getInputType());
        setInputType(type, false);
        boolean isPassword = isPasswordInputType(type);
        boolean isVisiblePassword = isVisiblePasswordInputType(type);
        boolean forceUpdate = false;
        if (isPassword) {
            setTransformationMethod(PasswordTransformationMethod.getInstance());
        } else if (isVisiblePassword) {
            if (this.mTransformation == PasswordTransformationMethod.getInstance()) {
                forceUpdate = true;
            }
        } else if (wasPassword || wasVisiblePassword) {
            setTypefaceFromAttrs(null, null, -1, 0, -1);
            if (this.mTransformation == PasswordTransformationMethod.getInstance()) {
                forceUpdate = true;
            }
        }
        boolean singleLine = !isMultilineInputType(type);
        if (this.mSingleLine != singleLine || forceUpdate) {
            applySingleLine(singleLine, !isPassword, true, true);
        }
        if (!isSuggestionsEnabled()) {
            setTextInternal(removeSuggestionSpans(this.mText));
        }
        InputMethodManager imm = getInputMethodManager();
        if (imm != null) {
            imm.restartInput(this);
        }
    }

    boolean hasPasswordTransformationMethod() {
        return this.mTransformation instanceof PasswordTransformationMethod;
    }

    public boolean isAnyPasswordInputType() {
        int inputType = getInputType();
        return isPasswordInputType(inputType) || isVisiblePasswordInputType(inputType);
    }

    static boolean isPasswordInputType(int inputType) {
        int variation = inputType & 4095;
        return variation == 129 || variation == 225 || variation == 18;
    }

    private static boolean isVisiblePasswordInputType(int inputType) {
        int variation = inputType & 4095;
        return variation == 145;
    }

    public void setRawInputType(int type) {
        if (type == 0 && this.mEditor == null) {
            return;
        }
        createEditorIfNeeded();
        this.mEditor.mInputType = type;
    }

    @Override // android.view.View
    public String[] getAutofillHints() {
        String[] hints = super.getAutofillHints();
        if (isAnyPasswordInputType() && !ArrayUtils.contains(hints, View.AUTOFILL_HINT_PASSWORD_AUTO)) {
            return (String[]) ArrayUtils.appendElement(String.class, hints, View.AUTOFILL_HINT_PASSWORD_AUTO);
        }
        return hints;
    }

    private Locale getCustomLocaleForKeyListenerOrNull() {
        LocaleList locales;
        if (this.mUseInternationalizedInput && (locales = getImeHintLocales()) != null) {
            return locales.get(0);
        }
        return null;
    }

    private void setInputType(int type, boolean direct) {
        KeyListener input;
        KeyListener input2;
        boolean autotext;
        TextKeyListener.Capitalize cap;
        int cls = type & 15;
        if (cls == 1) {
            autotext = (32768 & type) != 0;
            if ((type & 4096) != 0) {
                cap = TextKeyListener.Capitalize.CHARACTERS;
            } else if ((type & 8192) != 0) {
                cap = TextKeyListener.Capitalize.WORDS;
            } else if ((type & 16384) != 0) {
                cap = TextKeyListener.Capitalize.SENTENCES;
            } else {
                cap = TextKeyListener.Capitalize.NONE;
            }
            input = TextKeyListener.getInstance(autotext, cap);
        } else if (cls == 2) {
            Locale locale = getCustomLocaleForKeyListenerOrNull();
            boolean z = (type & 4096) != 0;
            autotext = (type & 8192) != 0;
            input = DigitsKeyListener.getInstance(locale, z, autotext);
            if (locale != null) {
                int newType = input.getInputType();
                int newClass = newType & 15;
                if (newClass != 2) {
                    if ((type & 16) != 0) {
                        newType |= 128;
                    }
                    type = newType;
                }
            }
        } else if (cls == 4) {
            Locale locale2 = getCustomLocaleForKeyListenerOrNull();
            switch (type & InputType.TYPE_MASK_VARIATION) {
                case 16:
                    input2 = DateKeyListener.getInstance(locale2);
                    break;
                case 32:
                    input2 = TimeKeyListener.getInstance(locale2);
                    break;
                default:
                    input2 = DateTimeKeyListener.getInstance(locale2);
                    break;
            }
            if (this.mUseInternationalizedInput) {
                type = input2.getInputType();
            }
            input = input2;
        } else if (cls == 3) {
            input = DialerKeyListener.getInstance();
        } else {
            input = TextKeyListener.getInstance();
        }
        setRawInputType(type);
        this.mListenerChanged = false;
        if (direct) {
            createEditorIfNeeded();
            this.mEditor.mKeyListener = input;
        } else {
            setKeyListenerOnly(input);
        }
    }

    public int getInputType() {
        if (this.mEditor == null) {
            return 0;
        }
        return this.mEditor.mInputType;
    }

    public void setImeOptions(int imeOptions) {
        createEditorIfNeeded();
        this.mEditor.createInputContentTypeIfNeeded();
        this.mEditor.mInputContentType.imeOptions = imeOptions;
    }

    public int getImeOptions() {
        if (this.mEditor == null || this.mEditor.mInputContentType == null) {
            return 0;
        }
        return this.mEditor.mInputContentType.imeOptions;
    }

    public void setImeActionLabel(CharSequence label, int actionId) {
        createEditorIfNeeded();
        this.mEditor.createInputContentTypeIfNeeded();
        this.mEditor.mInputContentType.imeActionLabel = label;
        this.mEditor.mInputContentType.imeActionId = actionId;
    }

    public CharSequence getImeActionLabel() {
        if (this.mEditor == null || this.mEditor.mInputContentType == null) {
            return null;
        }
        return this.mEditor.mInputContentType.imeActionLabel;
    }

    public int getImeActionId() {
        if (this.mEditor == null || this.mEditor.mInputContentType == null) {
            return 0;
        }
        return this.mEditor.mInputContentType.imeActionId;
    }

    public void setOnEditorActionListener(OnEditorActionListener l) {
        createEditorIfNeeded();
        this.mEditor.createInputContentTypeIfNeeded();
        this.mEditor.mInputContentType.onEditorActionListener = l;
    }

    public void onEditorAction(int actionCode) {
        Editor.InputContentType ict = this.mEditor == null ? null : this.mEditor.mInputContentType;
        if (ict != null) {
            if (ict.onEditorActionListener != null && ict.onEditorActionListener.onEditorAction(this, actionCode, null)) {
                return;
            }
            if (actionCode == 5) {
                View v = focusSearch(2);
                if (v != null && !v.requestFocus(2)) {
                    throw new IllegalStateException("focus search returned a view that wasn't able to take focus!");
                }
                return;
            }
            if (actionCode == 7) {
                View v2 = focusSearch(1);
                if (v2 != null && !v2.requestFocus(1)) {
                    throw new IllegalStateException("focus search returned a view that wasn't able to take focus!");
                }
                return;
            }
            if (actionCode == 6) {
                InputMethodManager imm = getInputMethodManager();
                if (imm != null) {
                    imm.hideSoftInputFromView(this, 0);
                    return;
                }
                return;
            }
        }
        ViewRootImpl viewRootImpl = getViewRootImpl();
        if (viewRootImpl != null) {
            long eventTime = SystemClock.uptimeMillis();
            viewRootImpl.dispatchKeyFromIme(new KeyEvent(eventTime, eventTime, 0, 66, 0, 0, -1, 0, 22));
            viewRootImpl.dispatchKeyFromIme(new KeyEvent(SystemClock.uptimeMillis(), eventTime, 1, 66, 0, 0, -1, 0, 22));
        }
    }

    public void setPrivateImeOptions(String type) {
        if (ViewRune.SUPPORT_WRITING_TOOLKIT) {
            this.mDisableWritingToolkitMenu = type != null && Arrays.asList(type.split(NavigationBarInflaterView.GRAVITY_SEPARATOR)).contains("disableWritingToolkit=true");
        }
        createEditorIfNeeded();
        this.mEditor.createInputContentTypeIfNeeded();
        this.mEditor.mInputContentType.privateImeOptions = type;
    }

    public String getPrivateImeOptions() {
        if (this.mEditor == null || this.mEditor.mInputContentType == null) {
            return null;
        }
        return this.mEditor.mInputContentType.privateImeOptions;
    }

    public void setInputExtras(int xmlResId) throws XmlPullParserException, IOException {
        createEditorIfNeeded();
        XmlResourceParser parser = getResources().getXml(xmlResId);
        this.mEditor.createInputContentTypeIfNeeded();
        this.mEditor.mInputContentType.extras = new Bundle();
        getResources().parseBundleExtras(parser, this.mEditor.mInputContentType.extras);
    }

    public Bundle getInputExtras(boolean create) {
        if (this.mEditor == null && !create) {
            return null;
        }
        createEditorIfNeeded();
        if (this.mEditor.mInputContentType == null) {
            if (!create) {
                return null;
            }
            this.mEditor.createInputContentTypeIfNeeded();
        }
        if (this.mEditor.mInputContentType.extras == null) {
            if (!create) {
                return null;
            }
            this.mEditor.mInputContentType.extras = new Bundle();
        }
        return this.mEditor.mInputContentType.extras;
    }

    public void setImeHintLocales(LocaleList hintLocales) {
        createEditorIfNeeded();
        this.mEditor.createInputContentTypeIfNeeded();
        this.mEditor.mInputContentType.imeHintLocales = hintLocales;
        if (this.mUseInternationalizedInput) {
            changeListenerLocaleTo(hintLocales == null ? null : hintLocales.get(0));
        }
    }

    public LocaleList getImeHintLocales() {
        if (this.mEditor == null || this.mEditor.mInputContentType == null) {
            return null;
        }
        return this.mEditor.mInputContentType.imeHintLocales;
    }

    public CharSequence getError() {
        if (this.mEditor == null) {
            return null;
        }
        return this.mEditor.mError;
    }

    @RemotableViewMethod
    public void setError(CharSequence error) {
        if (error == null) {
            setError(null, null);
            return;
        }
        Drawable dr = getContext().getDrawable(com.android.internal.R.drawable.indicator_input_error);
        dr.setBounds(0, 0, dr.getIntrinsicWidth(), dr.getIntrinsicHeight());
        setError(error, dr);
    }

    public void setError(CharSequence error, Drawable icon) {
        createEditorIfNeeded();
        this.mEditor.setError(error, icon);
        notifyViewAccessibilityStateChangedIfNeeded(3072);
    }

    @Override // android.view.View
    protected boolean setFrame(int l, int t, int r, int b) {
        boolean result = super.setFrame(l, t, r, b);
        if (this.mEditor != null) {
            this.mEditor.setFrame();
        }
        restartMarqueeIfNeeded();
        return result;
    }

    private void restartMarqueeIfNeeded() {
        if (this.mRestartMarquee && this.mEllipsize == TextUtils.TruncateAt.MARQUEE) {
            this.mRestartMarquee = false;
            startMarquee();
        }
    }

    public void setFilters(InputFilter[] filters) {
        if (filters == null) {
            throw new IllegalArgumentException();
        }
        this.mFilters = filters;
        if (this.mText instanceof Editable) {
            setFilters((Editable) this.mText, filters);
        }
    }

    private RestrictionPolicy getRestrictionPolicy() {
        if (this.mRestrictionPolicy == null) {
            this.mRestrictionPolicy = EnterpriseDeviceManager.getInstance().getRestrictionPolicy();
        }
        return this.mRestrictionPolicy;
    }

    private ApplicationRestrictionsManager getApplicationRestrictionsManager() {
        return EnterpriseDeviceManager.getInstance().getApplicationRestrictionsManager();
    }

    private void setFilters(Editable e, InputFilter[] filters) {
        if (this.mEditor != null) {
            boolean undoFilter = this.mEditor.mUndoInputFilter != null;
            boolean keyFilter = this.mEditor.mKeyListener instanceof InputFilter;
            int num = undoFilter ? 0 + 1 : 0;
            if (keyFilter) {
                num++;
            }
            if (num > 0) {
                InputFilter[] nf = new InputFilter[filters.length + num];
                System.arraycopy(filters, 0, nf, 0, filters.length);
                int num2 = 0;
                if (undoFilter) {
                    nf[filters.length] = this.mEditor.mUndoInputFilter;
                    num2 = 0 + 1;
                }
                if (keyFilter) {
                    nf[filters.length + num2] = (InputFilter) this.mEditor.mKeyListener;
                }
                e.setFilters(nf);
                return;
            }
        }
        e.setFilters(filters);
    }

    public InputFilter[] getFilters() {
        return this.mFilters;
    }

    private int getBoxHeight(Layout l) {
        int padding;
        Insets opticalInsets = isLayoutModeOptical(this.mParent) ? getOpticalInsets() : Insets.NONE;
        if (l == this.mHintLayout) {
            padding = getCompoundPaddingTop() + getCompoundPaddingBottom();
        } else {
            padding = getExtendedPaddingTop() + getExtendedPaddingBottom();
        }
        return (getMeasuredHeight() - padding) + opticalInsets.top + opticalInsets.bottom;
    }

    int getVerticalOffset(boolean forceNormal) {
        int boxht;
        int textht;
        int gravity = this.mGravity & 112;
        Layout l = this.mLayout;
        if (!forceNormal && this.mText.length() == 0 && this.mHintLayout != null) {
            l = this.mHintLayout;
        }
        if (gravity == 48 || (textht = l.getHeight()) >= (boxht = getBoxHeight(l))) {
            return 0;
        }
        if (gravity == 80) {
            int voffset = boxht - textht;
            return voffset;
        }
        int voffset2 = (boxht - textht) >> 1;
        return voffset2;
    }

    private int getBottomVerticalOffset(boolean forceNormal) {
        int boxht;
        int textht;
        int gravity = this.mGravity & 112;
        Layout l = this.mLayout;
        if (!forceNormal && this.mText.length() == 0 && this.mHintLayout != null) {
            l = this.mHintLayout;
        }
        if (gravity == 80 || (textht = l.getHeight()) >= (boxht = getBoxHeight(l))) {
            return 0;
        }
        if (gravity == 48) {
            int voffset = boxht - textht;
            return voffset;
        }
        int voffset2 = (boxht - textht) >> 1;
        return voffset2;
    }

    void invalidateCursorPath() {
        if (this.mHighlightPathBogus) {
            invalidateCursor();
            return;
        }
        int horizontalPadding = getCompoundPaddingLeft();
        int verticalPadding = getExtendedPaddingTop() + getVerticalOffset(true);
        if (this.mEditor.mDrawableForCursor == null) {
            synchronized (TEMP_RECTF) {
                float thick = (float) Math.ceil(this.mTextPaint.getStrokeWidth());
                if (thick < 1.0f) {
                    thick = 1.0f;
                }
                float thick2 = thick / 2.0f;
                this.mHighlightPath.computeBounds(TEMP_RECTF, false);
                invalidate((int) Math.floor((horizontalPadding + TEMP_RECTF.left) - thick2), (int) Math.floor((verticalPadding + TEMP_RECTF.top) - thick2), (int) Math.ceil(horizontalPadding + TEMP_RECTF.right + thick2), (int) Math.ceil(verticalPadding + TEMP_RECTF.bottom + thick2));
            }
            return;
        }
        Rect bounds = this.mEditor.mDrawableForCursor.getBounds();
        invalidate(bounds.left + horizontalPadding, bounds.top + verticalPadding, bounds.right + horizontalPadding, bounds.bottom + verticalPadding);
    }

    void invalidateCursor() {
        int where = getSelectionEnd();
        invalidateCursor(where, where, where);
    }

    private void invalidateCursor(int a, int b, int c) {
        if (a >= 0 || b >= 0 || c >= 0) {
            int start = Math.min(Math.min(a, b), c);
            int end = Math.max(Math.max(a, b), c);
            invalidateRegion(start, end, true);
        }
    }

    void invalidateRegion(int start, int end, boolean invalidateCursor) {
        int lineEnd;
        int left;
        int right;
        if (this.mLayout == null) {
            invalidate();
            return;
        }
        int start2 = originalToTransformed(start, 1);
        int end2 = originalToTransformed(end, 1);
        int lineStart = this.mLayout.getLineForOffset(start2);
        int top = this.mLayout.getLineTop(lineStart);
        if (lineStart > 0) {
            top -= this.mLayout.getLineDescent(lineStart - 1);
        }
        if (start2 == end2) {
            lineEnd = lineStart;
        } else {
            lineEnd = this.mLayout.getLineForOffset(end2);
        }
        int bottom = this.mLayout.getLineBottom(lineEnd);
        if (invalidateCursor && this.mEditor != null && this.mEditor.mDrawableForCursor != null) {
            Rect bounds = this.mEditor.mDrawableForCursor.getBounds();
            top = Math.min(top, bounds.top);
            bottom = Math.max(bottom, bounds.bottom);
        }
        int compoundPaddingLeft = getCompoundPaddingLeft();
        int verticalPadding = getExtendedPaddingTop() + getVerticalOffset(true);
        if (lineStart == lineEnd && !invalidateCursor) {
            int left2 = (int) this.mLayout.getPrimaryHorizontal(start2);
            int right2 = (int) (this.mLayout.getPrimaryHorizontal(end2) + 1.0d);
            left = left2 + compoundPaddingLeft;
            right = right2 + compoundPaddingLeft;
        } else {
            left = compoundPaddingLeft;
            right = getWidth() - getCompoundPaddingRight();
        }
        invalidate(this.mScrollX + left, verticalPadding + top, this.mScrollX + right, verticalPadding + bottom);
    }

    private void registerForPreDraw() {
        if (!this.mPreDrawRegistered) {
            getViewTreeObserver().addOnPreDrawListener(this);
            this.mPreDrawRegistered = true;
        }
    }

    private void unregisterForPreDraw() {
        getViewTreeObserver().removeOnPreDrawListener(this);
        this.mPreDrawRegistered = false;
        this.mPreDrawListenerDetached = false;
    }

    @Override // android.view.ViewTreeObserver.OnPreDrawListener
    public boolean onPreDraw() {
        if (this.mLayout == null) {
            assumeLayout();
        }
        if (this.mMovement != null) {
            int curs = getSelectionEnd();
            if (this.mEditor != null && this.mEditor.mSelectionModifierCursorController != null && this.mEditor.mSelectionModifierCursorController.isSelectionStartDragged()) {
                curs = getSelectionStart();
            }
            if (curs < 0 && (this.mGravity & 112) == 80) {
                curs = this.mText.length();
            }
            if (curs >= 0) {
                bringPointIntoView(curs);
            }
        } else {
            bringTextIntoView();
        }
        if (this.mEditor != null && this.mEditor.mCreatedWithASelection) {
            this.mEditor.refreshTextActionMode();
            this.mEditor.mCreatedWithASelection = false;
        }
        unregisterForPreDraw();
        return true;
    }

    @Override // android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (ViewRune.WIDGET_PEN_SUPPORTED) {
            this.mAttachedWindow = true;
            registerForStylusPenEvent();
        }
        if (this.mEditor != null) {
            this.mEditor.onAttachedToWindow();
        }
        if (this.mPreDrawListenerDetached) {
            getViewTreeObserver().addOnPreDrawListener(this);
            this.mPreDrawListenerDetached = false;
        }
    }

    @Override // android.view.View
    protected void onDetachedFromWindowInternal() {
        if (this.mPreDrawRegistered) {
            getViewTreeObserver().removeOnPreDrawListener(this);
            this.mPreDrawListenerDetached = true;
        }
        if (ViewRune.WIDGET_PEN_SUPPORTED) {
            removeForStylusPenEvent();
            clearMultiSelection();
        }
        resetResolvedDrawables();
        if (this.mEditor != null) {
            this.mEditor.onDetachedFromWindow();
        }
        startStopMarquee(false);
        super.onDetachedFromWindowInternal();
    }

    @Override // android.view.View
    public void onScreenStateChanged(int screenState) {
        super.onScreenStateChanged(screenState);
        if (this.mEditor != null) {
            this.mEditor.onScreenStateChanged(screenState);
        }
    }

    @Override // android.view.View
    protected boolean isPaddingOffsetRequired() {
        return (this.mShadowRadius == 0.0f && this.mDrawables == null) ? false : true;
    }

    @Override // android.view.View
    protected int getLeftPaddingOffset() {
        return (getCompoundPaddingLeft() - this.mPaddingLeft) + ((int) Math.min(0.0f, this.mShadowDx - this.mShadowRadius));
    }

    @Override // android.view.View
    protected int getTopPaddingOffset() {
        return (int) Math.min(0.0f, this.mShadowDy - this.mShadowRadius);
    }

    @Override // android.view.View
    protected int getBottomPaddingOffset() {
        return (int) Math.max(0.0f, this.mShadowDy + this.mShadowRadius);
    }

    @Override // android.view.View
    protected int getRightPaddingOffset() {
        return (-(getCompoundPaddingRight() - this.mPaddingRight)) + ((int) Math.max(0.0f, this.mShadowDx + this.mShadowRadius));
    }

    @Override // android.view.View
    protected boolean verifyDrawable(Drawable who) {
        boolean verified = super.verifyDrawable(who);
        if (!verified && this.mDrawables != null) {
            for (Drawable dr : this.mDrawables.mShowing) {
                if (who == dr) {
                    return true;
                }
            }
        }
        return verified;
    }

    @Override // android.view.View
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        if (this.mDrawables != null) {
            for (Drawable dr : this.mDrawables.mShowing) {
                if (dr != null) {
                    dr.jumpToCurrentState();
                }
            }
        }
    }

    @Override // android.view.View, android.graphics.drawable.Drawable.Callback
    public void invalidateDrawable(Drawable drawable) {
        boolean handled = false;
        if (verifyDrawable(drawable)) {
            Rect dirty = drawable.getBounds();
            int scrollX = this.mScrollX;
            int scrollY = this.mScrollY;
            Drawables drawables = this.mDrawables;
            if (drawables != null) {
                if (drawable == drawables.mShowing[0]) {
                    int compoundPaddingTop = getCompoundPaddingTop();
                    int compoundPaddingBottom = getCompoundPaddingBottom();
                    int vspace = ((this.mBottom - this.mTop) - compoundPaddingBottom) - compoundPaddingTop;
                    scrollX += this.mPaddingLeft;
                    scrollY += ((vspace - drawables.mDrawableHeightLeft) / 2) + compoundPaddingTop;
                    handled = true;
                } else if (drawable == drawables.mShowing[2]) {
                    int compoundPaddingTop2 = getCompoundPaddingTop();
                    int compoundPaddingBottom2 = getCompoundPaddingBottom();
                    int vspace2 = ((this.mBottom - this.mTop) - compoundPaddingBottom2) - compoundPaddingTop2;
                    scrollX += ((this.mRight - this.mLeft) - this.mPaddingRight) - drawables.mDrawableSizeRight;
                    scrollY += ((vspace2 - drawables.mDrawableHeightRight) / 2) + compoundPaddingTop2;
                    handled = true;
                } else if (drawable == drawables.mShowing[1]) {
                    int compoundPaddingLeft = getCompoundPaddingLeft();
                    int compoundPaddingRight = getCompoundPaddingRight();
                    int hspace = ((this.mRight - this.mLeft) - compoundPaddingRight) - compoundPaddingLeft;
                    scrollX += ((hspace - drawables.mDrawableWidthTop) / 2) + compoundPaddingLeft;
                    scrollY += this.mPaddingTop;
                    handled = true;
                } else if (drawable == drawables.mShowing[3]) {
                    int compoundPaddingLeft2 = getCompoundPaddingLeft();
                    int compoundPaddingRight2 = getCompoundPaddingRight();
                    int hspace2 = ((this.mRight - this.mLeft) - compoundPaddingRight2) - compoundPaddingLeft2;
                    scrollX += ((hspace2 - drawables.mDrawableWidthBottom) / 2) + compoundPaddingLeft2;
                    scrollY += ((this.mBottom - this.mTop) - this.mPaddingBottom) - drawables.mDrawableSizeBottom;
                    handled = true;
                }
            }
            if (handled) {
                invalidate(dirty.left + scrollX, dirty.top + scrollY, dirty.right + scrollX, dirty.bottom + scrollY);
            }
        }
        if (!handled) {
            super.invalidateDrawable(drawable);
        }
    }

    @Override // android.view.View
    public boolean hasOverlappingRendering() {
        return ((getBackground() == null || getBackground().getCurrent() == null) && this.mSpannable == null && !hasSelection() && !isHorizontalFadingEdgeEnabled() && this.mShadowColor == 0) ? false : true;
    }

    public boolean isTextSelectable() {
        if (this.mEditor == null) {
            return false;
        }
        return this.mEditor.mTextIsSelectable;
    }

    public void setTextIsSelectable(boolean selectable) {
        if (selectable || this.mEditor != null) {
            createEditorIfNeeded();
            if (this.mEditor.mTextIsSelectable == selectable) {
                return;
            }
            if (ViewRune.WIDGET_PEN_SUPPORTED) {
                registerForStylusPenEvent();
            }
            this.mEditor.mTextIsSelectable = selectable;
            setFocusableInTouchMode(selectable);
            setFocusable(16);
            setClickable(selectable);
            setLongClickable(selectable);
            setMovementMethod(selectable ? ArrowKeyMovementMethod.getInstance() : null);
            setText(this.mText, selectable ? BufferType.SPANNABLE : BufferType.NORMAL);
            this.mEditor.prepareCursorControllers();
        }
    }

    @Override // android.view.View
    protected int[] onCreateDrawableState(int extraSpace) {
        int[] drawableState;
        if (this.mSingleLine) {
            drawableState = super.onCreateDrawableState(extraSpace);
        } else {
            drawableState = super.onCreateDrawableState(extraSpace + 1);
            mergeDrawableStates(drawableState, MULTILINE_STATE_SET);
        }
        if (isTextSelectable()) {
            int length = drawableState.length;
            for (int i = 0; i < length; i++) {
                if (drawableState[i] == 16842919) {
                    int[] nonPressedState = new int[length - 1];
                    System.arraycopy(drawableState, 0, nonPressedState, 0, i);
                    System.arraycopy(drawableState, i + 1, nonPressedState, i, (length - i) - 1);
                    return nonPressedState;
                }
            }
        }
        return drawableState;
    }

    private void maybeUpdateHighlightPaths() {
        Path path;
        final Path path2;
        if (!this.mHighlightPathsBogus) {
            return;
        }
        if (this.mHighlightPaths != null) {
            this.mPathRecyclePool.addAll(this.mHighlightPaths);
            this.mHighlightPaths.clear();
            this.mHighlightPaints.clear();
        } else {
            this.mHighlightPaths = new ArrayList();
            this.mHighlightPaints = new ArrayList();
        }
        if (this.mHighlights != null) {
            for (int i = 0; i < this.mHighlights.getSize(); i++) {
                int[] ranges = this.mHighlights.getRanges(i);
                Paint paint = this.mHighlights.getPaint(i);
                if (this.mPathRecyclePool.isEmpty()) {
                    path2 = new Path();
                } else {
                    path2 = this.mPathRecyclePool.get(this.mPathRecyclePool.size() - 1);
                    this.mPathRecyclePool.remove(this.mPathRecyclePool.size() - 1);
                    path2.reset();
                }
                boolean atLeastOnePathAdded = false;
                for (int j = 0; j < ranges.length / 2; j++) {
                    int start = ranges[j * 2];
                    int end = ranges[(j * 2) + 1];
                    if (start < end) {
                        this.mLayout.getSelection(start, end, new Layout.SelectionRectangleConsumer() { // from class: android.widget.TextView$$ExternalSyntheticLambda0
                            @Override // android.text.Layout.SelectionRectangleConsumer
                            public final void accept(float f, float f2, float f3, float f4, int i2) {
                                Path.this.addRect(f, f2, f3, f4, Path.Direction.CW);
                            }
                        });
                        atLeastOnePathAdded = true;
                    }
                }
                if (atLeastOnePathAdded) {
                    this.mHighlightPaths.add(path2);
                    this.mHighlightPaints.add(paint);
                }
            }
        }
        addSearchHighlightPaths();
        if (hasGesturePreviewHighlight()) {
            if (this.mPathRecyclePool.isEmpty()) {
                path = new Path();
            } else {
                path = this.mPathRecyclePool.get(this.mPathRecyclePool.size() - 1);
                this.mPathRecyclePool.remove(this.mPathRecyclePool.size() - 1);
                path.reset();
            }
            this.mLayout.getSelectionPath(this.mGesturePreviewHighlightStart, this.mGesturePreviewHighlightEnd, path);
            this.mHighlightPaths.add(path);
            this.mHighlightPaints.add(this.mGesturePreviewHighlightPaint);
        }
        this.mHighlightPathsBogus = false;
    }

    private void addSearchHighlightPaths() {
        final Path searchResultPath;
        final Path focusedSearchResultPath;
        if (this.mSearchResultHighlights != null) {
            if (this.mPathRecyclePool.isEmpty()) {
                searchResultPath = new Path();
            } else {
                searchResultPath = this.mPathRecyclePool.get(this.mPathRecyclePool.size() - 1);
                this.mPathRecyclePool.remove(this.mPathRecyclePool.size() - 1);
                searchResultPath.reset();
            }
            if (this.mFocusedSearchResultIndex == -1) {
                focusedSearchResultPath = null;
            } else if (this.mPathRecyclePool.isEmpty()) {
                focusedSearchResultPath = new Path();
            } else {
                focusedSearchResultPath = this.mPathRecyclePool.get(this.mPathRecyclePool.size() - 1);
                this.mPathRecyclePool.remove(this.mPathRecyclePool.size() - 1);
                focusedSearchResultPath.reset();
            }
            boolean atLeastOnePathAdded = false;
            for (int j = 0; j < this.mSearchResultHighlights.length / 2; j++) {
                int start = this.mSearchResultHighlights[j * 2];
                int end = this.mSearchResultHighlights[(j * 2) + 1];
                if (start < end) {
                    if (j == this.mFocusedSearchResultIndex) {
                        this.mLayout.getSelection(start, end, new Layout.SelectionRectangleConsumer() { // from class: android.widget.TextView$$ExternalSyntheticLambda6
                            @Override // android.text.Layout.SelectionRectangleConsumer
                            public final void accept(float f, float f2, float f3, float f4, int i) {
                                Path.this.addRect(f, f2, f3, f4, Path.Direction.CW);
                            }
                        });
                    } else {
                        this.mLayout.getSelection(start, end, new Layout.SelectionRectangleConsumer() { // from class: android.widget.TextView$$ExternalSyntheticLambda7
                            @Override // android.text.Layout.SelectionRectangleConsumer
                            public final void accept(float f, float f2, float f3, float f4, int i) {
                                Path.this.addRect(f, f2, f3, f4, Path.Direction.CW);
                            }
                        });
                        atLeastOnePathAdded = true;
                    }
                }
            }
            if (atLeastOnePathAdded) {
                if (this.mSearchResultHighlightPaint == null) {
                    this.mSearchResultHighlightPaint = new Paint();
                }
                this.mSearchResultHighlightPaint.setColor(this.mSearchResultHighlightColor);
                this.mSearchResultHighlightPaint.setStyle(Paint.Style.FILL);
                this.mHighlightPaths.add(searchResultPath);
                this.mHighlightPaints.add(this.mSearchResultHighlightPaint);
            }
            if (focusedSearchResultPath != null) {
                if (this.mFocusedSearchResultHighlightPaint == null) {
                    this.mFocusedSearchResultHighlightPaint = new Paint();
                }
                this.mFocusedSearchResultHighlightPaint.setColor(this.mFocusedSearchResultHighlightColor);
                this.mFocusedSearchResultHighlightPaint.setStyle(Paint.Style.FILL);
                this.mHighlightPaths.add(focusedSearchResultPath);
                this.mHighlightPaints.add(this.mFocusedSearchResultHighlightPaint);
            }
        }
    }

    private Path getUpdatedHighlightPath() {
        Paint highlightPaint = this.mHighlightPaint;
        int selStart = getSelectionStartTransformed();
        int selEnd = getSelectionEndTransformed();
        if (ViewRune.WIDGET_PEN_SUPPORTED && this.mhasMultiSelection) {
            CharSequence text = getTextForMultiSelection();
            if (text == null) {
                this.mhasMultiSelection = false;
                return null;
            }
            if (this.mHighlightPathBogus) {
                if (this.mHighlightPath == null) {
                    this.mHighlightPath = new Path();
                }
                this.mHighlightPath.reset();
                int[] multiSelStart = MultiSelection.getMultiSelectionStart((Spannable) text);
                int[] multiSelEnd = MultiSelection.getMultiSelectionEnd((Spannable) text);
                int multiSelCount = MultiSelection.getMultiSelectionCount((Spannable) text);
                for (int i = 0; i < multiSelCount; i++) {
                    this.mLayout.addSelectionPath(multiSelStart[i], multiSelEnd[i], this.mHighlightPath);
                }
                this.mHighlightPathBogus = false;
            }
            this.mMultiHighlightPaint.setColor(this.mMultiHighlightColor);
            this.mMultiHighlightPaint.setStyle(Paint.Style.FILL);
            Path highlight = this.mHighlightPath;
            return highlight;
        }
        if (this.mMovement == null) {
            return null;
        }
        if ((!isFocused() && !isPressed()) || selStart < 0) {
            return null;
        }
        if (selStart == selEnd) {
            if (this.mEditor == null || !this.mEditor.shouldRenderCursor()) {
                return null;
            }
            if (this.mHighlightPathBogus) {
                if (this.mHighlightPath == null) {
                    this.mHighlightPath = new Path();
                }
                this.mHighlightPath.reset();
                this.mLayout.getCursorPath(selStart, this.mHighlightPath, this.mText);
                this.mEditor.updateCursorPosition();
                this.mHighlightPathBogus = false;
            }
            highlightPaint.setColor(this.mCurTextColor);
            highlightPaint.setStyle(Paint.Style.STROKE);
            Path highlight2 = this.mHighlightPath;
            return highlight2;
        }
        if (this.mHighlightPathBogus) {
            if (this.mHighlightPath == null) {
                this.mHighlightPath = new Path();
            }
            this.mHighlightPath.reset();
            this.mLayout.getSelectionPath(selStart, selEnd, this.mHighlightPath);
            this.mHighlightPathBogus = false;
        }
        highlightPaint.setColor(this.mHighlightColor);
        highlightPaint.setStyle(Paint.Style.FILL);
        Path highlight3 = this.mHighlightPath;
        return highlight3;
    }

    public int getHorizontalOffsetForDrawables() {
        return 0;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        Layout layout;
        int color;
        Layout layout2;
        int top;
        float clipLeft;
        float clipBottom;
        float clipTop;
        int voffsetCursor;
        int hctHor;
        float clipBottom2;
        int hctVert;
        int layoutDirection;
        Layout layout3;
        int i;
        boolean z;
        Paint highlightPaint;
        Layout layout4;
        int compoundPaddingLeft;
        int hctHor2;
        int i2;
        restartMarqueeIfNeeded();
        super.onDraw(canvas);
        if (this.mTextEffect != null && this.mTextEffect.hasEffect()) {
            int color2 = this.mCurTextColor;
            if (this.mLayout == null) {
                assumeLayout();
            }
            if (this.mHint != null && this.mText.length() == 0 && this.mHintTextColor != null) {
                color2 = this.mCurHintTextColor;
            }
            if (this.mButtonShapeSettingEnabled && this.mIsButtonShapeTarget && !TextUtils.isEmpty(this.mText)) {
                color2 = getButtonShapeTextColor();
            }
            this.mTextPaint.setColor(color2);
            Log.d(LOG_TAG, "TextView : Paint's Alpha value = " + getPaint().getAlpha());
            this.mTextEffect.setOwnerView(this);
            this.mTextEffect.setLines(this.mTextEffectLines);
            this.mTextEffect.setFontFamily(this.mFontFamily);
            if (this.mTextEffect.render(canvas, getScrollX(), getScrollY())) {
                return;
            }
        }
        int compoundPaddingLeft2 = getCompoundPaddingLeft();
        int compoundPaddingTop = getCompoundPaddingTop();
        int compoundPaddingRight = getCompoundPaddingRight();
        int compoundPaddingBottom = getCompoundPaddingBottom();
        int scrollX = this.mScrollX;
        int scrollY = this.mScrollY;
        int right = this.mRight;
        int left = this.mLeft;
        int bottom = this.mBottom;
        int top2 = this.mTop;
        boolean isLayoutRtl = isLayoutRtl();
        int offset = getHorizontalOffsetForDrawables();
        int leftOffset = isLayoutRtl ? 0 : offset;
        int rightOffset = isLayoutRtl ? offset : 0;
        Drawables dr = this.mDrawables;
        if (dr != null) {
            int vspace = ((bottom - top2) - compoundPaddingBottom) - compoundPaddingTop;
            int hspace = ((right - left) - compoundPaddingRight) - compoundPaddingLeft2;
            if (dr.mShowing[0] != null) {
                canvas.save();
                canvas.translate(this.mPaddingLeft + scrollX + leftOffset, scrollY + compoundPaddingTop + ((vspace - dr.mDrawableHeightLeft) / 2));
                dr.mShowing[0].draw(canvas);
                canvas.restore();
            }
            if (dr.mShowing[2] != null) {
                canvas.save();
                canvas.translate(((((scrollX + right) - left) - this.mPaddingRight) - dr.mDrawableSizeRight) - rightOffset, scrollY + compoundPaddingTop + ((vspace - dr.mDrawableHeightRight) / 2));
                dr.mShowing[2].draw(canvas);
                canvas.restore();
            }
            if (dr.mShowing[1] != null) {
                canvas.save();
                canvas.translate(scrollX + compoundPaddingLeft2 + ((hspace - dr.mDrawableWidthTop) / 2), this.mPaddingTop + scrollY);
                dr.mShowing[1].draw(canvas);
                canvas.restore();
            }
            if (dr.mShowing[3] != null) {
                canvas.save();
                canvas.translate(scrollX + compoundPaddingLeft2 + ((hspace - dr.mDrawableWidthBottom) / 2), (((scrollY + bottom) - top2) - this.mPaddingBottom) - dr.mDrawableSizeBottom);
                dr.mShowing[3].draw(canvas);
                canvas.restore();
            }
        }
        int color3 = this.mCurTextColor;
        if (this.mLayout == null) {
            assumeLayout();
        }
        Layout layout5 = this.mLayout;
        if (this.mHint != null && !this.mHideHint && this.mText.length() == 0) {
            if (this.mHintTextColor != null) {
                color3 = this.mCurHintTextColor;
            }
            Layout layout6 = this.mHintLayout;
            layout = layout6;
        } else {
            layout = layout5;
        }
        if (this.mButtonShapeSettingEnabled && this.mIsButtonShapeTarget && !TextUtils.isEmpty(this.mText)) {
            color = getButtonShapeTextColor();
        } else {
            color = color3;
        }
        this.mTextPaint.setColor(color);
        this.mTextPaint.drawableState = getDrawableState();
        canvas.save();
        if (!this.mButtonShapeSettingEnabled || !this.mIsButtonShapeTarget || this.mButtonShapePaint == null || this.mButtonShapeRect == null) {
            layout2 = layout;
            top = top2;
        } else if (TextUtils.isEmpty(this.mText)) {
            layout2 = layout;
            top = top2;
        } else {
            int paddingLeft = getCompoundPaddingLeft();
            int paddingTop = getExtendedPaddingTop();
            if ((this.mGravity & 112) == 48) {
                i2 = 0;
            } else {
                i2 = 0;
                paddingTop += getVerticalOffset(false);
            }
            int lineStart = this.mLayout.getLineForOffset(i2);
            int lineEnd = this.mLayout.getLineForOffset(this.mText.length());
            float lineLeft = this.mLayout.getLineLeft(lineStart);
            float lineRight = this.mLayout.getLineRight(lineStart);
            float lineRight2 = lineRight;
            float lineLeft2 = lineLeft;
            layout2 = layout;
            int i3 = lineStart;
            while (i3 <= lineEnd) {
                int top3 = top2;
                if (lineLeft2 > this.mLayout.getLineLeft(i3)) {
                    lineLeft2 = this.mLayout.getLineLeft(i3);
                }
                if (lineRight2 < this.mLayout.getLineRight(i3)) {
                    lineRight2 = this.mLayout.getLineRight(i3);
                }
                i3++;
                top2 = top3;
            }
            top = top2;
            this.mButtonShapePaint.setAlpha(this.mButtonShapeAlpha);
            this.mButtonShapePaint.setColor(this.mButtonShapeColor);
            this.mButtonShapeRect.top = (this.mLayout.getLineTop(lineStart) + paddingTop) - this.mButtonShapeOutlineStrokeTop;
            this.mButtonShapeRect.bottom = this.mLayout.getLineBottom(lineEnd) + paddingTop + this.mButtonShapeOutlineStrokeBottom;
            this.mButtonShapeRect.left = (((float) Math.floor(lineLeft2)) + paddingLeft) - this.mButtonShapeOutlineStrokeHorizontal;
            this.mButtonShapeRect.right = ((float) Math.ceil(lineRight2)) + paddingLeft + this.mButtonShapeOutlineStrokeHorizontal;
            canvas.drawRoundRect(this.mButtonShapeRect, this.mButtonShapeOutlineRadius, this.mButtonShapeOutlineRadius, this.mButtonShapePaint);
        }
        int extendedPaddingTop = getExtendedPaddingTop();
        int extendedPaddingBottom = getExtendedPaddingBottom();
        int maxScrollY = this.mLayout.getHeight() - (((this.mBottom - this.mTop) - compoundPaddingBottom) - compoundPaddingTop);
        float clipLeft2 = compoundPaddingLeft2 + scrollX;
        float clipTop2 = scrollY == 0 ? 0.0f : extendedPaddingTop + scrollY;
        float clipRight = ((right - left) - getCompoundPaddingRight()) + scrollX;
        float clipBottom3 = ((bottom - top) + scrollY) - (scrollY == maxScrollY ? 0 : extendedPaddingBottom);
        if (this.mShadowRadius != 0.0f) {
            float clipLeft3 = clipLeft2 + Math.min(0.0f, this.mShadowDx - this.mShadowRadius);
            clipRight += Math.max(0.0f, this.mShadowDx + this.mShadowRadius);
            float clipTop3 = clipTop2 + Math.min(0.0f, this.mShadowDy - this.mShadowRadius);
            float clipBottom4 = clipBottom3 + Math.max(0.0f, this.mShadowDy + this.mShadowRadius);
            clipLeft = clipLeft3;
            clipBottom = clipTop3;
            clipTop = clipBottom4;
        } else {
            clipLeft = clipLeft2;
            clipBottom = clipTop2;
            clipTop = clipBottom3;
        }
        canvas.clipRect(clipLeft, clipBottom, clipRight, clipTop);
        int voffsetText = 0;
        if ((this.mGravity & 112) != 48) {
            int voffsetText2 = getVerticalOffset(false);
            int voffsetCursor2 = getVerticalOffset(true);
            voffsetCursor = voffsetCursor2;
            voffsetText = voffsetText2;
        } else {
            voffsetCursor = 0;
        }
        if (!isHighContrastTextEnabled()) {
            hctHor = 0;
            clipBottom2 = clipTop;
            hctVert = 0;
        } else {
            if ((this.mGravity & 7) == 1 || !isEditorNotFull()) {
                clipBottom2 = clipTop;
                hctHor2 = 0;
            } else {
                clipBottom2 = clipTop;
                hctHor2 = (int) Math.floor((this.mTextPaint.getHCTStrokeWidth() / 2.0f) * (isRightAligned() ? -1 : 1));
            }
            hctHor = hctHor2;
            if ((this.mGravity & 112) == 16) {
                hctVert = 0;
            } else {
                int hctVert2 = (int) Math.floor(this.mTextPaint.getHCTStrokeWidth() / 2.0f);
                hctVert = hctVert2;
            }
        }
        canvas.translate(compoundPaddingLeft2 + hctHor, extendedPaddingTop + voffsetText + hctVert);
        int layoutDirection2 = getLayoutDirection();
        int absoluteGravity = Gravity.getAbsoluteGravity(this.mGravity, layoutDirection2);
        if (!isMarqueeFadeEnabled()) {
            layoutDirection = layoutDirection2;
            layout3 = layout2;
            i = 0;
            z = false;
        } else {
            if (!this.mSingleLine && getLineCount() == 1 && canMarquee() && (absoluteGravity & 7) != 3) {
                int width = this.mRight - this.mLeft;
                int padding = getCompoundPaddingLeft() + getCompoundPaddingRight();
                layoutDirection = layoutDirection2;
                float dx = this.mLayout.getLineRight(0) - (width - padding);
                layout3 = layout2;
                canvas.translate(layout3.getParagraphDirection(0) * dx, 0.0f);
            } else {
                layoutDirection = layoutDirection2;
                layout3 = layout2;
            }
            if (this.mMarquee == null || !this.mMarquee.isRunning()) {
                i = 0;
                z = false;
            } else {
                float dx2 = -this.mMarquee.getScroll();
                i = 0;
                z = false;
                canvas.translate(layout3.getParagraphDirection(0) * dx2, 0.0f);
            }
        }
        int cursorOffsetVertical = voffsetCursor - voffsetText;
        maybeUpdateHighlightPaths();
        Path highlight = hasGesturePreviewHighlight() ? null : getUpdatedHighlightPath();
        Paint highlightPaint2 = this.mHighlightPaint;
        if (ViewRune.WIDGET_PEN_SUPPORTED && this.mhasMultiSelection) {
            Paint highlightPaint3 = this.mMultiHighlightPaint;
            highlightPaint = highlightPaint3;
        } else {
            highlightPaint = highlightPaint2;
        }
        if (this.mEditor != null) {
            layout4 = layout3;
            compoundPaddingLeft = i;
            this.mEditor.onDraw(canvas, layout3, this.mHighlightPaths, this.mHighlightPaints, highlight, highlightPaint, cursorOffsetVertical);
        } else {
            layout4 = layout3;
            compoundPaddingLeft = i;
            layout4.draw(canvas, this.mHighlightPaths, this.mHighlightPaints, highlight, highlightPaint, cursorOffsetVertical);
        }
        if (this.mMarquee != null && this.mMarquee.shouldDrawGhost()) {
            float dx3 = this.mMarquee.getGhostOffset();
            canvas.translate(r13.getParagraphDirection(compoundPaddingLeft) * dx3, 0.0f);
            layout4.draw(canvas, this.mHighlightPaths, this.mHighlightPaints, highlight, highlightPaint, cursorOffsetVertical);
        }
        canvas.restore();
    }

    @Override // android.view.View
    public void getFocusedRect(Rect r) {
        if (this.mLayout == null) {
            super.getFocusedRect(r);
            return;
        }
        int selEnd = getSelectionEndTransformed();
        if (selEnd < 0) {
            super.getFocusedRect(r);
            return;
        }
        int selStart = getSelectionStartTransformed();
        if (selStart < 0 || selStart >= selEnd) {
            int line = this.mLayout.getLineForOffset(selEnd);
            r.top = this.mLayout.getLineTop(line);
            r.bottom = this.mLayout.getLineBottom(line);
            r.left = ((int) this.mLayout.getPrimaryHorizontal(selEnd)) - 2;
            r.right = r.left + 4;
        } else {
            int lineStart = this.mLayout.getLineForOffset(selStart);
            int lineEnd = this.mLayout.getLineForOffset(selEnd);
            r.top = this.mLayout.getLineTop(lineStart);
            r.bottom = this.mLayout.getLineBottom(lineEnd);
            if (lineStart == lineEnd) {
                r.left = (int) this.mLayout.getPrimaryHorizontal(selStart);
                r.right = (int) this.mLayout.getPrimaryHorizontal(selEnd);
            } else {
                if (this.mHighlightPathBogus) {
                    if (this.mHighlightPath == null) {
                        this.mHighlightPath = new Path();
                    }
                    this.mHighlightPath.reset();
                    this.mLayout.getSelectionPath(selStart, selEnd, this.mHighlightPath);
                    this.mHighlightPathBogus = false;
                }
                synchronized (TEMP_RECTF) {
                    this.mHighlightPath.computeBounds(TEMP_RECTF, true);
                    r.left = ((int) TEMP_RECTF.left) - 1;
                    r.right = ((int) TEMP_RECTF.right) + 1;
                }
            }
        }
        int paddingLeft = getCompoundPaddingLeft();
        int paddingTop = getExtendedPaddingTop();
        if ((this.mGravity & 112) != 48) {
            paddingTop += getVerticalOffset(false);
        }
        r.offset(paddingLeft, paddingTop);
        int paddingBottom = getExtendedPaddingBottom();
        r.bottom += paddingBottom;
    }

    public int getLineCount() {
        if (this.mLayout != null) {
            return this.mLayout.getLineCount();
        }
        return 0;
    }

    public int getLineBounds(int line, Rect bounds) {
        if (this.mLayout == null) {
            if (bounds != null) {
                bounds.set(0, 0, 0, 0);
            }
            return 0;
        }
        int baseline = this.mLayout.getLineBounds(line, bounds);
        int voffset = getExtendedPaddingTop();
        if ((this.mGravity & 112) != 48) {
            voffset += getVerticalOffset(true);
        }
        if (bounds != null) {
            bounds.offset(getCompoundPaddingLeft(), voffset);
        }
        return baseline + voffset;
    }

    @Override // android.view.View
    public int getBaseline() {
        if (this.mLayout == null) {
            return super.getBaseline();
        }
        return getBaselineOffset() + this.mLayout.getLineBaseline(0);
    }

    int getBaselineOffset() {
        int voffset = 0;
        if ((this.mGravity & 112) != 48) {
            voffset = getVerticalOffset(true);
        }
        if (isLayoutModeOptical(this.mParent)) {
            voffset -= getOpticalInsets().top;
        }
        return getExtendedPaddingTop() + voffset;
    }

    @Override // android.view.View
    protected int getFadeTop(boolean offsetRequired) {
        if (this.mLayout == null) {
            return 0;
        }
        int voffset = 0;
        if ((this.mGravity & 112) != 48) {
            voffset = getVerticalOffset(true);
        }
        if (offsetRequired) {
            voffset += getTopPaddingOffset();
        }
        return getExtendedPaddingTop() + voffset;
    }

    @Override // android.view.View
    protected int getFadeHeight(boolean offsetRequired) {
        if (this.mLayout != null) {
            return this.mLayout.getHeight();
        }
        return 0;
    }

    @Override // android.view.View
    public PointerIcon onResolvePointerIcon(MotionEvent event, int pointerIndex) {
        if (event.isFromSource(8194)) {
            if (this.mSpannable != null && this.mLinksClickable) {
                float x = event.getX(pointerIndex);
                float y = event.getY(pointerIndex);
                int offset = getOffsetForPosition(x, y);
                ClickableSpan[] clickables = (ClickableSpan[]) this.mSpannable.getSpans(offset, offset, ClickableSpan.class);
                if (clickables.length > 0) {
                    return PointerIcon.getSystemIcon(this.mContext, 1002);
                }
            }
            if (isTextSelectable() || isTextEditable()) {
                if (event.getToolType(pointerIndex) == 2) {
                    return PointerIcon.getSystemIcon(this.mContext, 20002);
                }
                return PointerIcon.getSystemIcon(this.mContext, 1008);
            }
        }
        return super.onResolvePointerIcon(event, pointerIndex);
    }

    @Override // android.view.View
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (keyCode == 4 && handleBackInTextActionModeIfNeeded(event)) {
            return true;
        }
        if (keyCode == 23 && event != null && event.getAction() == 1) {
            this.mKeycodeDpadCenterStatus = false;
        }
        return super.onKeyPreIme(keyCode, event);
    }

    public boolean handleBackInTextActionModeIfNeeded(KeyEvent event) {
        if (this.mEditor == null || this.mEditor.getTextActionMode() == null) {
            return false;
        }
        if (event.getAction() == 0 && event.getRepeatCount() == 0) {
            KeyEvent.DispatcherState state = getKeyDispatcherState();
            if (state != null) {
                state.startTracking(event, this);
            }
            return true;
        }
        if (event.getAction() == 1) {
            KeyEvent.DispatcherState state2 = getKeyDispatcherState();
            if (state2 != null) {
                state2.handleUpEvent(event);
            }
            if (event.isTracking() && !event.isCanceled()) {
                stopTextActionMode();
                return true;
            }
        }
        return false;
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 19 || keyCode == 20 || keyCode == 21 || keyCode == 22) {
            stopTextActionMode();
            if (event.isShiftPressed()) {
                this.mChangedSelectionBySIP = true;
            }
        }
        if (keyCode == 1102) {
            if (this.mEditor != null) {
                this.mEditor.stopTextActionModeFromIME();
            }
            return true;
        }
        int which = doKeyDown(keyCode, event, null);
        if (which == 0) {
            return super.onKeyDown(keyCode, event);
        }
        return true;
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event) {
        KeyEvent down = KeyEvent.changeAction(event, 0);
        int which = doKeyDown(keyCode, down, event);
        if (which == 0) {
            return super.onKeyMultiple(keyCode, repeatCount, event);
        }
        if (which == -1) {
            return true;
        }
        int repeatCount2 = repeatCount - 1;
        KeyEvent up = KeyEvent.changeAction(event, 1);
        if (which == 1) {
            this.mEditor.mKeyListener.onKeyUp(this, (Editable) this.mText, keyCode, up);
            while (true) {
                repeatCount2--;
                if (repeatCount2 <= 0) {
                    break;
                }
                this.mEditor.mKeyListener.onKeyDown(this, (Editable) this.mText, keyCode, down);
                this.mEditor.mKeyListener.onKeyUp(this, (Editable) this.mText, keyCode, up);
            }
            hideErrorIfUnchanged();
        } else if (which == 2) {
            this.mMovement.onKeyUp(this, this.mSpannable, keyCode, up);
            while (true) {
                repeatCount2--;
                if (repeatCount2 <= 0) {
                    break;
                }
                this.mMovement.onKeyDown(this, this.mSpannable, keyCode, down);
                this.mMovement.onKeyUp(this, this.mSpannable, keyCode, up);
            }
        }
        return true;
    }

    private boolean shouldAdvanceFocusOnEnter() {
        int variation;
        if (getKeyListener() == null) {
            return false;
        }
        if (this.mSingleLine) {
            return true;
        }
        return this.mEditor != null && (this.mEditor.mInputType & 15) == 1 && ((variation = this.mEditor.mInputType & InputType.TYPE_MASK_VARIATION) == 32 || variation == 48);
    }

    private boolean isDirectionalNavigationKey(int keyCode) {
        switch (keyCode) {
            case 19:
            case 20:
            case 21:
            case 22:
                return true;
            default:
                return false;
        }
    }

    private int doKeyDown(int keyCode, KeyEvent event, KeyEvent otherEvent) {
        if (!isEnabled()) {
            return 0;
        }
        if (event.getRepeatCount() == 0 && !KeyEvent.isModifierKey(keyCode)) {
            this.mPreventDefaultMovement = false;
        }
        switch (keyCode) {
            case 4:
                if (this.mEditor != null && this.mEditor.getTextActionMode() != null) {
                    stopTextActionMode();
                    return -1;
                }
                break;
            case 23:
                this.mKeycodeDpadCenterStatus = true;
                if (event.hasNoModifiers() && shouldAdvanceFocusOnEnter()) {
                    return 0;
                }
                break;
            case 61:
                if (event.hasNoModifiers() || event.hasModifiers(1)) {
                    return 0;
                }
                break;
            case 66:
            case 160:
                if (event.hasNoModifiers()) {
                    if (this.mEditor != null && this.mEditor.mInputContentType != null && this.mEditor.mInputContentType.onEditorActionListener != null && this.mEditor.mInputContentType.onEditorActionListener.onEditorAction(this, getActionIdForEnterEvent(), event)) {
                        this.mEditor.mInputContentType.enterDown = true;
                        return -1;
                    }
                    if ((event.getFlags() & 16) != 0 || shouldAdvanceFocusOnEnter()) {
                        return hasOnClickListeners() ? 0 : -1;
                    }
                }
                break;
            case 111:
                if (Flags.escapeClearsFocus() && event.hasNoModifiers()) {
                    if (this.mEditor != null && this.mEditor.getTextActionMode() != null) {
                        stopTextActionMode();
                        return -1;
                    }
                    if (hasFocus()) {
                        clearFocusInternal(null, true, false);
                        InputMethodManager imm = getInputMethodManager();
                        if (imm != null) {
                            imm.hideSoftInputFromView(this, 0);
                        }
                        return -1;
                    }
                }
                break;
            case 112:
                if (event.hasModifiers(1) && canCut() && onTextContextMenuItem(16908320)) {
                    return -1;
                }
                break;
            case 124:
                if (event.hasModifiers(4096) && canCopy()) {
                    if (onTextContextMenuItem(16908321)) {
                        return -1;
                    }
                } else if (event.hasModifiers(1) && canPaste() && onTextContextMenuItem(16908322)) {
                    return -1;
                }
                break;
            case 277:
                if (event.hasNoModifiers() && canCut() && onTextContextMenuItem(16908320)) {
                    return -1;
                }
                break;
            case 278:
                if (event.hasNoModifiers() && canCopy() && onTextContextMenuItem(16908321)) {
                    return -1;
                }
                break;
            case 279:
                if (event.hasNoModifiers() && canPaste() && onTextContextMenuItem(16908322)) {
                    return -1;
                }
                break;
        }
        if (this.mEditor != null && this.mEditor.mKeyListener != null) {
            boolean doDown = true;
            if (otherEvent != null) {
                try {
                    beginBatchEdit();
                    boolean handled = this.mEditor.mKeyListener.onKeyOther(this, (Editable) this.mText, otherEvent);
                    hideErrorIfUnchanged();
                    doDown = false;
                    if (handled) {
                        endBatchEdit();
                        return -1;
                    }
                } catch (AbstractMethodError e) {
                } catch (Throwable th) {
                    endBatchEdit();
                    throw th;
                }
                endBatchEdit();
            }
            if (doDown) {
                beginBatchEdit();
                boolean handled2 = this.mEditor.mKeyListener.onKeyDown(this, (Editable) this.mText, keyCode, event);
                endBatchEdit();
                hideErrorIfUnchanged();
                if (handled2) {
                    return 1;
                }
            }
        }
        if (this.mMovement != null && this.mLayout != null) {
            boolean doDown2 = true;
            if (otherEvent != null) {
                try {
                    boolean handled3 = this.mMovement.onKeyOther(this, this.mSpannable, otherEvent);
                    doDown2 = false;
                    if (handled3) {
                        return -1;
                    }
                } catch (AbstractMethodError e2) {
                }
            }
            if (doDown2 && this.mMovement.onKeyDown(this, this.mSpannable, keyCode, event)) {
                if (event.getRepeatCount() == 0 && !KeyEvent.isModifierKey(keyCode)) {
                    this.mPreventDefaultMovement = true;
                    return 2;
                }
                return 2;
            }
            if (event.getSource() == 257 && isDirectionalNavigationKey(keyCode)) {
                return -1;
            }
        }
        return (!this.mPreventDefaultMovement || KeyEvent.isModifierKey(keyCode)) ? 0 : -1;
    }

    public void resetErrorChangedFlag() {
        if (this.mEditor != null) {
            this.mEditor.mErrorWasChanged = false;
        }
    }

    public void hideErrorIfUnchanged() {
        if (this.mEditor != null && this.mEditor.mError != null && !this.mEditor.mErrorWasChanged) {
            setError(null, null);
        }
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        InputMethodManager imm;
        if (!isEnabled()) {
            return super.onKeyUp(keyCode, event);
        }
        if (!KeyEvent.isModifierKey(keyCode)) {
            this.mPreventDefaultMovement = false;
        }
        switch (keyCode) {
            case 23:
                this.mKeycodeDpadCenterStatus = false;
                if (event.hasNoModifiers() && !hasOnClickListeners() && this.mMovement != null && (this.mText instanceof Editable) && this.mLayout != null && onCheckIsTextEditor()) {
                    InputMethodManager imm2 = getInputMethodManager();
                    viewClicked(imm2);
                    if (imm2 != null && getShowSoftInputOnFocus()) {
                        imm2.showSoftInput(this, 0);
                    }
                }
                return super.onKeyUp(keyCode, event);
            case 66:
            case 160:
                if (event.hasNoModifiers()) {
                    if (this.mEditor != null && this.mEditor.mInputContentType != null && this.mEditor.mInputContentType.onEditorActionListener != null && this.mEditor.mInputContentType.enterDown) {
                        this.mEditor.mInputContentType.enterDown = false;
                        if (this.mEditor.mInputContentType.onEditorActionListener.onEditorAction(this, getActionIdForEnterEvent(), event)) {
                            return true;
                        }
                    }
                    if (((event.getFlags() & 16) != 0 || shouldAdvanceFocusOnEnter()) && !hasOnClickListeners()) {
                        View v = focusSearch(130);
                        if (v != null) {
                            if (!v.requestFocus(130)) {
                                throw new IllegalStateException("focus search returned a view that wasn't able to take focus!");
                            }
                            super.onKeyUp(keyCode, event);
                            return true;
                        }
                        if ((event.getFlags() & 16) != 0 && (imm = getInputMethodManager()) != null) {
                            imm.hideSoftInputFromView(this, 0);
                        }
                    }
                    return super.onKeyUp(keyCode, event);
                }
                break;
        }
        if ((keyCode == 59 || keyCode == 60) && this.mChangedSelectionBySIP) {
            InputMethodManager imm3 = getInputMethodManager();
            if (this.mEditor != null && hasSelection() && isFocused() && isShown() && imm3 != null) {
                int accessoryKeyboardState = imm3.isAccessoryKeyboardState();
                if ((accessoryKeyboardState & 7) == 0) {
                    int start = getSelectionStart();
                    int end = getSelectionEnd();
                    if (start > end) {
                        Selection.setSelection((Spannable) this.mText, end, start);
                    }
                    this.mEditor.startSelectionActionModeAsync(false);
                }
            }
        }
        this.mChangedSelectionBySIP = false;
        if (this.mEditor != null && this.mEditor.mKeyListener != null && this.mEditor.mKeyListener.onKeyUp(this, (Editable) this.mText, keyCode, event)) {
            return true;
        }
        if (this.mMovement != null && this.mLayout != null && this.mMovement.onKeyUp(this, this.mSpannable, keyCode, event)) {
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    private int getActionIdForEnterEvent() {
        if (!isSingleLine()) {
            return 0;
        }
        return getImeOptions() & 255;
    }

    @Override // android.view.View
    public boolean onCheckIsTextEditor() {
        return (this.mEditor == null || this.mEditor.mInputType == 0) ? false : true;
    }

    private boolean hasEditorInFocusSearchDirection(int direction) {
        View nextView = focusSearch(direction);
        return nextView != null && nextView.onCheckIsTextEditor();
    }

    @Override // android.view.View
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        if (onCheckIsTextEditor() && isEnabled()) {
            this.mEditor.createInputMethodStateIfNeeded();
            this.mEditor.mInputMethodState.mUpdateCursorAnchorInfoMode = 0;
            this.mEditor.mInputMethodState.mUpdateCursorAnchorInfoFilter = 0;
            outAttrs.inputType = getInputType();
            if (this.mEditor.mInputContentType != null) {
                outAttrs.imeOptions = this.mEditor.mInputContentType.imeOptions;
                outAttrs.privateImeOptions = this.mEditor.mInputContentType.privateImeOptions;
                outAttrs.actionLabel = this.mEditor.mInputContentType.imeActionLabel;
                outAttrs.actionId = this.mEditor.mInputContentType.imeActionId;
                outAttrs.extras = this.mEditor.mInputContentType.extras;
                outAttrs.hintLocales = this.mEditor.mInputContentType.imeHintLocales;
            } else {
                outAttrs.imeOptions = 0;
                outAttrs.hintLocales = null;
            }
            if (hasEditorInFocusSearchDirection(130)) {
                outAttrs.imeOptions |= 134217728;
            }
            if (hasEditorInFocusSearchDirection(33)) {
                outAttrs.imeOptions |= 67108864;
            }
            if ((outAttrs.imeOptions & 255) == 0) {
                if ((outAttrs.imeOptions & 134217728) != 0) {
                    outAttrs.imeOptions |= 5;
                } else {
                    outAttrs.imeOptions |= 6;
                }
                if (!shouldAdvanceFocusOnEnter()) {
                    outAttrs.imeOptions |= 1073741824;
                }
            }
            if (getResources().getConfiguration().orientation == 1) {
                outAttrs.internalImeOptions |= 1;
            }
            if (isMultilineInputType(outAttrs.inputType)) {
                outAttrs.imeOptions |= 1073741824;
            }
            outAttrs.hintText = this.mHint;
            outAttrs.targetInputMethodUser = this.mTextOperationUser;
            if (this.mText instanceof Editable) {
                InputConnection ic = new EditableInputConnection(this);
                outAttrs.initialSelStart = getSelectionStart();
                outAttrs.initialSelEnd = getSelectionEnd();
                outAttrs.initialCapsMode = ic.getCursorCapsMode(getInputType());
                outAttrs.setInitialSurroundingText(this.mText);
                outAttrs.contentMimeTypes = getReceiveContentMimeTypes();
                if (android.view.inputmethod.Flags.editorinfoHandwritingEnabled()) {
                    boolean handwritingEnabled = isAutoHandwritingEnabled();
                    outAttrs.setStylusHandwritingEnabled(handwritingEnabled);
                    if (outAttrs.extras == null) {
                        outAttrs.extras = new Bundle();
                    }
                    outAttrs.extras.putBoolean(EditorInfo.STYLUS_HANDWRITING_ENABLED_ANDROIDX_EXTRAS_KEY, handwritingEnabled);
                }
                ArrayList<Class<? extends HandwritingGesture>> gestures = new ArrayList<>();
                gestures.add(SelectGesture.class);
                gestures.add(SelectRangeGesture.class);
                gestures.add(DeleteGesture.class);
                gestures.add(DeleteRangeGesture.class);
                gestures.add(InsertGesture.class);
                gestures.add(RemoveSpaceGesture.class);
                gestures.add(JoinOrSplitGesture.class);
                gestures.add(InsertModeGesture.class);
                outAttrs.setSupportedHandwritingGestures(gestures);
                Set<Class<? extends PreviewableHandwritingGesture>> previews = new ArraySet<>();
                previews.add(SelectGesture.class);
                previews.add(SelectRangeGesture.class);
                previews.add(DeleteGesture.class);
                previews.add(DeleteRangeGesture.class);
                outAttrs.setSupportedHandwritingGesturePreviews(previews);
                return ic;
            }
        }
        return null;
    }

    public void onRequestCursorUpdatesInternal(int cursorUpdateMode, int cursorUpdateFilter) {
        this.mEditor.mInputMethodState.mUpdateCursorAnchorInfoMode = cursorUpdateMode;
        this.mEditor.mInputMethodState.mUpdateCursorAnchorInfoFilter = cursorUpdateFilter;
        if ((cursorUpdateMode & 1) != 0 && !isInLayout()) {
            requestLayout();
        }
    }

    public boolean extractText(ExtractedTextRequest request, ExtractedText outText) {
        createEditorIfNeeded();
        return this.mEditor.extractText(request, outText);
    }

    static void removeParcelableSpans(Spannable spannable, int start, int end) {
        Object[] spans = spannable.getSpans(start, end, ParcelableSpan.class);
        int i = spans.length;
        while (i > 0) {
            i--;
            spannable.removeSpan(spans[i]);
        }
    }

    public void setExtractedText(ExtractedText text) {
        int start;
        int end;
        Editable content = getEditableText();
        if (text.text != null) {
            if (content == null) {
                setText(text.text, BufferType.EDITABLE);
            } else {
                int end2 = content.length();
                if (text.partialStartOffset < 0) {
                    start = 0;
                    end = end2;
                } else {
                    int N = content.length();
                    int start2 = text.partialStartOffset;
                    if (start2 > N) {
                        start2 = N;
                    }
                    int end3 = text.partialEndOffset;
                    if (end3 > N) {
                        end3 = N;
                    }
                    start = start2;
                    end = end3;
                }
                removeParcelableSpans(content, start, end);
                if (TextUtils.equals(content.subSequence(start, end), text.text)) {
                    if (text.text instanceof Spanned) {
                        TextUtils.copySpansFrom((Spanned) text.text, 0, end - start, Object.class, content, start);
                    }
                } else {
                    content.replace(start, end, text.text);
                }
            }
        }
        Spannable sp = (Spannable) getText();
        int N2 = sp.length();
        int start3 = text.selectionStart;
        if (start3 < 0) {
            start3 = 0;
        } else if (start3 > N2) {
            start3 = N2;
        }
        int end4 = text.selectionEnd;
        if (end4 < 0) {
            end4 = 0;
        } else if (end4 > N2) {
            end4 = N2;
        }
        Selection.setSelection(sp, start3, end4);
        if ((text.flags & 2) != 0) {
            MetaKeyKeyListener.startSelecting(this, sp);
        } else {
            MetaKeyKeyListener.stopSelecting(this, sp);
        }
        setHintInternal(text.hint);
    }

    boolean isClipboardDisallowedByKnox() {
        return (getRestrictionPolicy() == null || getRestrictionPolicy().isClipboardAllowed(true)) ? false : true;
    }

    boolean isWritingToolkitDisallowedByKnox() {
        ApplicationRestrictionsManager mApplicationRestrictionsManager = getApplicationRestrictionsManager();
        Bundle restrictions = mApplicationRestrictionsManager.getApplicationRestrictions("com.samsung.android.knox.galaxyai", UserHandle.myUserId());
        return restrictions != null && !restrictions.isEmpty() && restrictions.containsKey("key_writing_toolkit") && restrictions.getBundle("key_writing_toolkit").getBoolean("grayout");
    }

    public void setExtracting(ExtractedTextRequest req) {
        if (this.mEditor.mInputMethodState != null) {
            this.mEditor.mInputMethodState.mExtractedTextRequest = req;
        }
        this.mEditor.hideCursorAndSpanControllers();
        stopTextActionMode();
        if (this.mEditor.mSelectionModifierCursorController != null) {
            this.mEditor.mSelectionModifierCursorController.resetTouchOffsets();
        }
    }

    public void onCommitCompletion(CompletionInfo text) {
    }

    public void onCommitCorrection(CorrectionInfo info) {
        if (this.mEditor != null) {
            this.mEditor.onCommitCorrection(info);
        }
    }

    public void beginBatchEdit() {
        if (this.mEditor != null) {
            this.mEditor.beginBatchEdit();
        }
    }

    public void endBatchEdit() {
        if (this.mEditor != null) {
            this.mEditor.endBatchEdit();
        }
    }

    public void onBeginBatchEdit() {
    }

    public void onEndBatchEdit() {
    }

    public void onPerformSpellCheck() {
        if (this.mEditor != null && this.mEditor.mSpellChecker != null) {
            this.mEditor.mSpellChecker.onPerformSpellCheck();
        }
    }

    public boolean onPrivateIMECommand(String action, Bundle data) {
        return false;
    }

    public boolean isOffsetMappingAvailable() {
        return this.mTransformation != null && (this.mTransformed instanceof OffsetMapping);
    }

    public boolean previewHandwritingGesture(PreviewableHandwritingGesture gesture, CancellationSignal cancellationSignal) {
        if (gesture instanceof SelectGesture) {
            performHandwritingSelectGesture((SelectGesture) gesture, true);
        } else if (gesture instanceof SelectRangeGesture) {
            performHandwritingSelectRangeGesture((SelectRangeGesture) gesture, true);
        } else if (gesture instanceof DeleteGesture) {
            performHandwritingDeleteGesture((DeleteGesture) gesture, true);
        } else if (gesture instanceof DeleteRangeGesture) {
            performHandwritingDeleteRangeGesture((DeleteRangeGesture) gesture, true);
        } else {
            return false;
        }
        if (cancellationSignal != null) {
            cancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener() { // from class: android.widget.TextView$$ExternalSyntheticLambda4
                @Override // android.os.CancellationSignal.OnCancelListener
                public final void onCancel() {
                    TextView.this.clearGesturePreviewHighlight();
                }
            });
        }
        return true;
    }

    public int performHandwritingSelectGesture(SelectGesture gesture) {
        return performHandwritingSelectGesture(gesture, false);
    }

    private int performHandwritingSelectGesture(SelectGesture gesture, boolean isPreview) {
        if (isOffsetMappingAvailable()) {
            return 3;
        }
        int[] range = getRangeForRect(convertFromScreenToContentCoordinates(gesture.getSelectionArea()), gesture.getGranularity());
        if (range == null) {
            return handleGestureFailure(gesture, isPreview);
        }
        return performHandwritingSelectGesture(range, isPreview);
    }

    private int performHandwritingSelectGesture(int[] range, boolean isPreview) {
        if (isPreview) {
            setSelectGesturePreviewHighlight(range[0], range[1]);
        } else {
            Selection.setSelection(getEditableText(), range[0], range[1]);
            this.mEditor.startSelectionActionModeAsync(false);
        }
        return 1;
    }

    public int performHandwritingSelectRangeGesture(SelectRangeGesture gesture) {
        return performHandwritingSelectRangeGesture(gesture, false);
    }

    private int performHandwritingSelectRangeGesture(SelectRangeGesture gesture, boolean isPreview) {
        if (isOffsetMappingAvailable()) {
            return 3;
        }
        int[] startRange = getRangeForRect(convertFromScreenToContentCoordinates(gesture.getSelectionStartArea()), gesture.getGranularity());
        if (startRange == null) {
            return handleGestureFailure(gesture, isPreview);
        }
        int[] endRange = getRangeForRect(convertFromScreenToContentCoordinates(gesture.getSelectionEndArea()), gesture.getGranularity());
        if (endRange == null) {
            return handleGestureFailure(gesture, isPreview);
        }
        int[] range = {Math.min(startRange[0], endRange[0]), Math.max(startRange[1], endRange[1])};
        return performHandwritingSelectGesture(range, isPreview);
    }

    public int performHandwritingDeleteGesture(DeleteGesture gesture) {
        return performHandwritingDeleteGesture(gesture, false);
    }

    private int performHandwritingDeleteGesture(DeleteGesture gesture, boolean isPreview) {
        if (isOffsetMappingAvailable()) {
            return 3;
        }
        int[] range = getRangeForRect(convertFromScreenToContentCoordinates(gesture.getDeletionArea()), gesture.getGranularity());
        if (range == null) {
            return handleGestureFailure(gesture, isPreview);
        }
        return performHandwritingDeleteGesture(range, gesture.getGranularity(), isPreview);
    }

    private int performHandwritingDeleteGesture(int[] range, int granularity, boolean isPreview) {
        if (isPreview) {
            setDeleteGesturePreviewHighlight(range[0], range[1]);
        } else {
            if (granularity == 1) {
                range = adjustHandwritingDeleteGestureRange(range);
            }
            Selection.setSelection(getEditableText(), range[0]);
            getEditableText().delete(range[0], range[1]);
        }
        return 1;
    }

    public int performHandwritingDeleteRangeGesture(DeleteRangeGesture gesture) {
        return performHandwritingDeleteRangeGesture(gesture, false);
    }

    private int performHandwritingDeleteRangeGesture(DeleteRangeGesture gesture, boolean isPreview) {
        if (isOffsetMappingAvailable()) {
            return 3;
        }
        int[] startRange = getRangeForRect(convertFromScreenToContentCoordinates(gesture.getDeletionStartArea()), gesture.getGranularity());
        if (startRange == null) {
            return handleGestureFailure(gesture, isPreview);
        }
        int[] endRange = getRangeForRect(convertFromScreenToContentCoordinates(gesture.getDeletionEndArea()), gesture.getGranularity());
        if (endRange == null) {
            return handleGestureFailure(gesture, isPreview);
        }
        int[] range = {Math.min(startRange[0], endRange[0]), Math.max(startRange[1], endRange[1])};
        return performHandwritingDeleteGesture(range, gesture.getGranularity(), isPreview);
    }

    private int[] adjustHandwritingDeleteGestureRange(int[] range) {
        int codePointBeforeStart;
        int start = range[0];
        int end = range[1];
        int codePointAtEnd = 10;
        if (start <= 0) {
            codePointBeforeStart = 10;
        } else {
            codePointBeforeStart = Character.codePointBefore(this.mText, start);
        }
        if (end < this.mText.length()) {
            codePointAtEnd = Character.codePointAt(this.mText, end);
        }
        if (TextUtils.isWhitespaceExceptNewline(codePointBeforeStart) && (TextUtils.isWhitespace(codePointAtEnd) || TextUtils.isPunctuation(codePointAtEnd))) {
            do {
                start -= Character.charCount(codePointBeforeStart);
                if (start == 0) {
                    break;
                }
                codePointBeforeStart = Character.codePointBefore(this.mText, start);
            } while (TextUtils.isWhitespaceExceptNewline(codePointBeforeStart));
            return new int[]{start, end};
        }
        if (TextUtils.isWhitespaceExceptNewline(codePointAtEnd) && (TextUtils.isWhitespace(codePointBeforeStart) || TextUtils.isPunctuation(codePointBeforeStart))) {
            do {
                end += Character.charCount(codePointAtEnd);
                if (end == this.mText.length()) {
                    break;
                }
                codePointAtEnd = Character.codePointAt(this.mText, end);
            } while (TextUtils.isWhitespaceExceptNewline(codePointAtEnd));
            return new int[]{start, end};
        }
        return range;
    }

    public int performHandwritingInsertGesture(InsertGesture gesture) {
        if (isOffsetMappingAvailable()) {
            return 3;
        }
        PointF point = convertFromScreenToContentCoordinates(gesture.getInsertionPoint());
        int line = getLineForHandwritingGesture(point);
        if (line == -1) {
            return handleGestureFailure(gesture);
        }
        int offset = this.mLayout.getOffsetForHorizontal(line, point.x);
        String textToInsert = gesture.getTextToInsert();
        return tryInsertTextForHandwritingGesture(offset, textToInsert, gesture);
    }

    public int performHandwritingRemoveSpaceGesture(RemoveSpaceGesture gesture) {
        int line;
        if (!isOffsetMappingAvailable()) {
            PointF startPoint = convertFromScreenToContentCoordinates(gesture.getStartPoint());
            PointF endPoint = convertFromScreenToContentCoordinates(gesture.getEndPoint());
            int startPointLine = getLineForHandwritingGesture(startPoint);
            int endPointLine = getLineForHandwritingGesture(endPoint);
            if (startPointLine == -1) {
                if (endPointLine == -1) {
                    return handleGestureFailure(gesture);
                }
                line = endPointLine;
            } else {
                line = endPointLine == -1 ? startPointLine : Math.min(startPointLine, endPointLine);
            }
            float lineVerticalCenter = (this.mLayout.getLineTop(line) + this.mLayout.getLineBottom(line, false)) / 2.0f;
            RectF area = new RectF(Math.min(startPoint.x, endPoint.x), lineVerticalCenter + 0.1f, Math.max(startPoint.x, endPoint.x), lineVerticalCenter - 0.1f);
            int[] range = this.mLayout.getRangeForRect(area, new GraphemeClusterSegmentFinder(this.mText, this.mTextPaint), Layout.INCLUSION_STRATEGY_ANY_OVERLAP);
            if (range == null) {
                return handleGestureFailure(gesture);
            }
            int startOffset = range[0];
            int endOffset = range[1];
            Pattern whitespacePattern = getWhitespacePattern();
            Matcher matcher = whitespacePattern.matcher(this.mText.subSequence(startOffset, endOffset));
            int lastRemoveOffset = -1;
            while (matcher.find()) {
                lastRemoveOffset = startOffset + matcher.start();
                getEditableText().delete(lastRemoveOffset, startOffset + matcher.end());
                startOffset = lastRemoveOffset;
                endOffset -= matcher.end() - matcher.start();
                if (startOffset == endOffset) {
                    break;
                }
                matcher = whitespacePattern.matcher(this.mText.subSequence(startOffset, endOffset));
            }
            if (lastRemoveOffset == -1) {
                return handleGestureFailure(gesture);
            }
            Selection.setSelection(getEditableText(), lastRemoveOffset);
            return 1;
        }
        return 3;
    }

    public int performHandwritingJoinOrSplitGesture(JoinOrSplitGesture gesture) {
        if (isOffsetMappingAvailable()) {
            return 3;
        }
        PointF point = convertFromScreenToContentCoordinates(gesture.getJoinOrSplitPoint());
        int line = getLineForHandwritingGesture(point);
        if (line == -1) {
            return handleGestureFailure(gesture);
        }
        int startOffset = this.mLayout.getOffsetForHorizontal(line, point.x);
        if (this.mLayout.isLevelBoundary(startOffset)) {
            return handleGestureFailure(gesture);
        }
        int endOffset = startOffset;
        while (startOffset > 0) {
            int codePointBeforeStart = Character.codePointBefore(this.mText, startOffset);
            if (!TextUtils.isWhitespace(codePointBeforeStart)) {
                break;
            }
            startOffset -= Character.charCount(codePointBeforeStart);
        }
        while (endOffset < this.mText.length()) {
            int codePointAtEnd = Character.codePointAt(this.mText, endOffset);
            if (!TextUtils.isWhitespace(codePointAtEnd)) {
                break;
            }
            endOffset += Character.charCount(codePointAtEnd);
        }
        if (startOffset < endOffset) {
            Selection.setSelection(getEditableText(), startOffset);
            getEditableText().delete(startOffset, endOffset);
            return 1;
        }
        return tryInsertTextForHandwritingGesture(startOffset, " ", gesture);
    }

    public int performHandwritingInsertModeGesture(InsertModeGesture gesture) {
        PointF insertPoint = convertFromScreenToContentCoordinates(gesture.getInsertionPoint());
        int line = getLineForHandwritingGesture(insertPoint);
        CancellationSignal cancellationSignal = gesture.getCancellationSignal();
        if (line == -1 || cancellationSignal == null) {
            int offset = handleGestureFailure(gesture);
            return offset;
        }
        int offset2 = this.mLayout.getOffsetForHorizontal(line, insertPoint.x);
        if (!this.mEditor.enterInsertMode(offset2)) {
            return 3;
        }
        cancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener() { // from class: android.widget.TextView$$ExternalSyntheticLambda5
            @Override // android.os.CancellationSignal.OnCancelListener
            public final void onCancel() {
                TextView.this.lambda$performHandwritingInsertModeGesture$4();
            }
        });
        return 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$performHandwritingInsertModeGesture$4() {
        this.mEditor.exitInsertMode();
    }

    private int handleGestureFailure(HandwritingGesture gesture) {
        return handleGestureFailure(gesture, false);
    }

    private int handleGestureFailure(HandwritingGesture gesture, boolean isPreview) {
        clearGesturePreviewHighlight();
        if (!isPreview && !TextUtils.isEmpty(gesture.getFallbackText())) {
            getEditableText().replace(getSelectionStart(), getSelectionEnd(), gesture.getFallbackText());
            return 5;
        }
        return 3;
    }

    private int getLineForHandwritingGesture(PointF point) {
        int line = this.mLayout.getLineForVertical((int) point.y);
        int lineMargin = ViewConfiguration.get(this.mContext).getScaledHandwritingGestureLineMargin();
        if (line < this.mLayout.getLineCount() - 1 && point.y > this.mLayout.getLineBottom(line) - lineMargin && point.y > (this.mLayout.getLineBottom(line, false) + this.mLayout.getLineBottom(line)) / 2.0f) {
            line++;
        } else if (point.y < this.mLayout.getLineTop(line) - lineMargin || point.y > this.mLayout.getLineBottom(line, false) + lineMargin) {
            return -1;
        }
        if (point.x < (-lineMargin) || point.x > this.mLayout.getWidth() + lineMargin) {
            return -1;
        }
        return line;
    }

    private int[] getRangeForRect(RectF area, int granularity) {
        SegmentFinder segmentFinder;
        if (granularity == 1) {
            WordIterator wordIterator = getWordIterator();
            wordIterator.setCharSequence(this.mText, 0, this.mText.length());
            segmentFinder = new WordSegmentFinder(this.mText, wordIterator);
        } else {
            segmentFinder = new GraphemeClusterSegmentFinder(this.mText, this.mTextPaint);
        }
        return this.mLayout.getRangeForRect(area, segmentFinder, Layout.INCLUSION_STRATEGY_CONTAINS_CENTER);
    }

    private int tryInsertTextForHandwritingGesture(int offset, String textToInsert, HandwritingGesture gesture) {
        Editable editableText = getEditableText();
        if (this.mTempCursor == null) {
            this.mTempCursor = new NoCopySpan.Concrete();
        }
        editableText.setSpan(this.mTempCursor, offset, offset, 34);
        editableText.insert(offset, textToInsert);
        int newOffset = editableText.getSpanStart(this.mTempCursor);
        editableText.removeSpan(this.mTempCursor);
        if (newOffset == offset) {
            return handleGestureFailure(gesture);
        }
        Selection.setSelection(editableText, newOffset);
        return 1;
    }

    private Pattern getWhitespacePattern() {
        if (this.mWhitespacePattern == null) {
            this.mWhitespacePattern = Pattern.compile("\\s+");
        }
        return this.mWhitespacePattern;
    }

    public void nullLayouts() {
        if ((this.mLayout instanceof BoringLayout) && this.mSavedLayout == null) {
            this.mSavedLayout = (BoringLayout) this.mLayout;
        }
        if ((this.mHintLayout instanceof BoringLayout) && this.mSavedHintLayout == null) {
            this.mSavedHintLayout = (BoringLayout) this.mHintLayout;
        }
        this.mHintLayout = null;
        this.mLayout = null;
        this.mSavedMarqueeModeLayout = null;
        this.mHintBoring = null;
        this.mBoring = null;
        if (this.mEditor != null) {
            this.mEditor.prepareCursorControllers();
        }
    }

    private void assumeLayout() {
        int width = ((this.mRight - this.mLeft) - getCompoundPaddingLeft()) - getCompoundPaddingRight();
        if (width < 1) {
            width = 0;
        }
        int physicalWidth = width;
        if (this.mHorizontallyScrolling) {
            width = 1048576;
        }
        makeNewLayout(width, physicalWidth, UNKNOWN_BORING, UNKNOWN_BORING, physicalWidth, false);
    }

    private Layout.Alignment getLayoutAlignment() {
        switch (getTextAlignment()) {
            case 1:
                switch (this.mGravity & Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK) {
                    case 1:
                        Layout.Alignment alignment = Layout.Alignment.ALIGN_CENTER;
                        return alignment;
                    case 3:
                        Layout.Alignment alignment2 = Layout.Alignment.ALIGN_LEFT;
                        return alignment2;
                    case 5:
                        Layout.Alignment alignment3 = Layout.Alignment.ALIGN_RIGHT;
                        return alignment3;
                    case Gravity.START /* 8388611 */:
                        Layout.Alignment alignment4 = Layout.Alignment.ALIGN_NORMAL;
                        return alignment4;
                    case Gravity.END /* 8388613 */:
                        Layout.Alignment alignment5 = Layout.Alignment.ALIGN_OPPOSITE;
                        return alignment5;
                    default:
                        Layout.Alignment alignment6 = Layout.Alignment.ALIGN_NORMAL;
                        return alignment6;
                }
            case 2:
                Layout.Alignment alignment7 = Layout.Alignment.ALIGN_NORMAL;
                return alignment7;
            case 3:
                Layout.Alignment alignment8 = Layout.Alignment.ALIGN_OPPOSITE;
                return alignment8;
            case 4:
                Layout.Alignment alignment9 = Layout.Alignment.ALIGN_CENTER;
                return alignment9;
            case 5:
                if (getLayoutDirection() == 1) {
                    Layout.Alignment alignment10 = Layout.Alignment.ALIGN_RIGHT;
                    return alignment10;
                }
                Layout.Alignment alignment11 = Layout.Alignment.ALIGN_LEFT;
                return alignment11;
            case 6:
                if (getLayoutDirection() == 1) {
                    Layout.Alignment alignment12 = Layout.Alignment.ALIGN_LEFT;
                    return alignment12;
                }
                Layout.Alignment alignment13 = Layout.Alignment.ALIGN_RIGHT;
                return alignment13;
            default:
                Layout.Alignment alignment14 = Layout.Alignment.ALIGN_NORMAL;
                return alignment14;
        }
    }

    private Paint.FontMetrics getResolvedMinimumFontMetrics() {
        if (this.mMinimumFontMetrics != null) {
            return this.mMinimumFontMetrics;
        }
        if (!this.mUseLocalePreferredLineHeightForMinimum) {
            return null;
        }
        if (this.mLocalePreferredFontMetrics == null) {
            this.mLocalePreferredFontMetrics = new Paint.FontMetrics();
        }
        this.mTextPaint.getFontMetricsForLocale(this.mLocalePreferredFontMetrics);
        return this.mLocalePreferredFontMetrics;
    }

    /* JADX WARN: Code restructure failed: missing block: B:87:0x0263, code lost:
    
        if (r27 == r29.mLayout.getParagraphDirection(r15)) goto L119;
     */
    /* JADX WARN: Removed duplicated region for block: B:100:0x028f  */
    /* JADX WARN: Removed duplicated region for block: B:103:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void makeNewLayout(int r30, int r31, android.text.BoringLayout.Metrics r32, android.text.BoringLayout.Metrics r33, int r34, boolean r35) {
        /*
            Method dump skipped, instructions count: 661
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.TextView.makeNewLayout(int, int, android.text.BoringLayout$Metrics, android.text.BoringLayout$Metrics, int, boolean):void");
    }

    public boolean useDynamicLayout() {
        return isTextSelectable() || (this.mSpannable != null && this.mPrecomputed == null);
    }

    protected Layout makeSingleLayout(int wantWidth, BoringLayout.Metrics boring, int ellipsisWidth, Layout.Alignment alignment, boolean shouldEllipsize, TextUtils.TruncateAt effectiveEllipsize, boolean useSaved) {
        CharSequence transformed;
        BoringLayout.Metrics boring2;
        CharSequence transformed2;
        BoringLayout.Metrics boring3;
        BoringLayout.Metrics boring4;
        Layout result = null;
        CharSequence transformed3 = this.mTransformed;
        if (ViewRune.WIDGET_PEN_SUPPORTED && this.mUseDisplayText) {
            CharSequence transformed4 = this.mDisplayText;
            transformed = transformed4;
        } else {
            transformed = transformed3;
        }
        if (useDynamicLayout()) {
            result = DynamicLayout.Builder.obtain(this.mText, this.mTextPaint, wantWidth).setDisplayText(transformed).setAlignment(alignment).setTextDirection(this.mTextDir).setLineSpacing(this.mSpacingAdd, this.mSpacingMult).setIncludePad(this.mIncludePad).setUseLineSpacingFromFallbacks(isFallbackLineSpacingForStaticLayout()).setBreakStrategy(this.mBreakStrategy).setHyphenationFrequency(this.mHyphenationFrequency).setJustificationMode(this.mJustificationMode).setLineBreakConfig(LineBreakConfig.getLineBreakConfig(this.mLineBreakStyle, this.mLineBreakWordStyle)).setUseBoundsForWidth(this.mUseBoundsForWidth).setEllipsize(getKeyListener() == null ? effectiveEllipsize : null).setEllipsizedWidth(ellipsisWidth).setMinimumFontMetrics(getResolvedMinimumFontMetrics()).build();
            transformed2 = transformed;
        } else {
            if (boring == UNKNOWN_BORING) {
                BoringLayout.Metrics boring5 = BoringLayout.isBoring(transformed, this.mTextPaint, this.mTextDir, isFallbackLineSpacingForBoringLayout(), getResolvedMinimumFontMetrics(), this.mBoring);
                if (boring5 != null) {
                    this.mBoring = boring5;
                }
                boring2 = boring5;
            } else {
                boring2 = boring;
            }
            if (boring2 != null) {
                if (boring2.width > wantWidth) {
                    boring3 = boring2;
                    transformed2 = transformed;
                } else if (effectiveEllipsize == null || boring2.width <= ellipsisWidth) {
                    if (!useSaved || this.mSavedLayout == null) {
                        boring4 = boring2;
                        transformed2 = transformed;
                        result = new BoringLayout(transformed2, this.mTextPaint, wantWidth, alignment, this.mSpacingMult, this.mSpacingAdd, this.mIncludePad, isFallbackLineSpacingForBoringLayout(), wantWidth, null, boring4, this.mUseBoundsForWidth, this.mShiftDrawingOffsetForStartOverhang, getResolvedMinimumFontMetrics());
                    } else {
                        boring4 = boring2;
                        transformed2 = transformed;
                        result = this.mSavedLayout.replaceOrMake(transformed, this.mTextPaint, wantWidth, alignment, this.mSpacingMult, this.mSpacingAdd, boring2, this.mIncludePad, null, wantWidth, isFallbackLineSpacingForBoringLayout(), this.mUseBoundsForWidth, getResolvedMinimumFontMetrics());
                    }
                    if (useSaved) {
                        this.mSavedLayout = (BoringLayout) result;
                    }
                } else {
                    boring3 = boring2;
                    transformed2 = transformed;
                }
                if (shouldEllipsize) {
                    BoringLayout.Metrics boring6 = boring3;
                    if (boring6.width <= wantWidth) {
                        if (useSaved && this.mSavedLayout != null) {
                            result = this.mSavedLayout.replaceOrMake(transformed2, this.mTextPaint, wantWidth, alignment, this.mSpacingMult, this.mSpacingAdd, boring6, this.mIncludePad, effectiveEllipsize, ellipsisWidth, isFallbackLineSpacingForBoringLayout(), this.mUseBoundsForWidth, getResolvedMinimumFontMetrics());
                        } else {
                            result = new BoringLayout(transformed2, this.mTextPaint, wantWidth, alignment, this.mSpacingMult, this.mSpacingAdd, this.mIncludePad, isFallbackLineSpacingForBoringLayout(), ellipsisWidth, effectiveEllipsize, boring6, this.mUseBoundsForWidth, this.mShiftDrawingOffsetForStartOverhang, getResolvedMinimumFontMetrics());
                        }
                    }
                }
            } else {
                transformed2 = transformed;
            }
        }
        if (result == null) {
            StaticLayout.Builder builder = StaticLayout.Builder.obtain(transformed2, 0, transformed2.length(), this.mTextPaint, wantWidth).setAlignment(alignment).setTextDirection(this.mTextDir).setLineSpacing(this.mSpacingAdd, this.mSpacingMult).setIncludePad(this.mIncludePad).setUseLineSpacingFromFallbacks(isFallbackLineSpacingForStaticLayout()).setBreakStrategy(this.mBreakStrategy).setHyphenationFrequency(this.mHyphenationFrequency).setJustificationMode(this.mJustificationMode).setMaxLines(this.mMaxMode == 1 ? this.mMaximum : Integer.MAX_VALUE).setLineBreakConfig(LineBreakConfig.getLineBreakConfig(this.mLineBreakStyle, this.mLineBreakWordStyle)).setUseBoundsForWidth(this.mUseBoundsForWidth).setMinimumFontMetrics(getResolvedMinimumFontMetrics());
            if (shouldEllipsize) {
                builder.setEllipsize(effectiveEllipsize).setEllipsizedWidth(ellipsisWidth);
            }
            return builder.build();
        }
        return result;
    }

    private boolean compressText(float width) {
        if (!isHardwareAccelerated() && width > 0.0f && this.mLayout != null && getLineCount() == 1 && !this.mUserSetTextScaleX && this.mTextPaint.getTextScaleX() == 1.0f) {
            float textWidth = this.mLayout.getLineWidth(0);
            float overflow = ((textWidth + 1.0f) - width) / width;
            if (overflow > 0.0f && overflow <= 0.07f) {
                this.mTextPaint.setTextScaleX((1.0f - overflow) - 0.005f);
                post(new Runnable() { // from class: android.widget.TextView.2
                    @Override // java.lang.Runnable
                    public void run() {
                        TextView.this.requestLayout();
                    }
                });
                return true;
            }
        }
        return false;
    }

    private static int desired(Layout layout, boolean useBoundsForWidth) {
        int n = layout.getLineCount();
        CharSequence text = layout.getText();
        float max = 0.0f;
        for (int i = 0; i < n - 1; i++) {
            if (text.charAt(layout.getLineEnd(i) - 1) != '\n') {
                return -1;
            }
        }
        for (int i2 = 0; i2 < n; i2++) {
            max = Math.max(max, layout.getLineMax(i2));
        }
        if (useBoundsForWidth) {
            max = Math.max(max, layout.computeDrawingBoundingBox().width());
        }
        return (int) Math.ceil(max);
    }

    public void setIncludeFontPadding(boolean includepad) {
        if (this.mIncludePad != includepad) {
            this.mIncludePad = includepad;
            if (this.mLayout != null) {
                nullLayouts();
                requestLayout();
                invalidate();
            }
        }
    }

    public boolean getIncludeFontPadding() {
        return this.mIncludePad;
    }

    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width;
        int width2;
        int width3;
        BoringLayout.Metrics boring;
        BoringLayout.Metrics hintBoring;
        int des;
        boolean fromexisting;
        int width4;
        int hintWidth;
        int width5;
        int unpaddedWidth;
        int widthSize;
        int width6;
        int hintWant;
        boolean z;
        boolean layoutChanged;
        boolean maximumChanged;
        int desired;
        int unpaddedWidth2;
        int heightMode;
        char c;
        int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
        int heightMode2 = View.MeasureSpec.getMode(heightMeasureSpec);
        int widthSize2 = View.MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = View.MeasureSpec.getSize(heightMeasureSpec);
        BoringLayout.Metrics boring2 = UNKNOWN_BORING;
        BoringLayout.Metrics hintBoring2 = UNKNOWN_BORING;
        if (this.mTextDir == null) {
            this.mTextDir = getTextDirectionHeuristic();
        }
        int des2 = -1;
        boolean fromexisting2 = false;
        float widthLimit = widthMode == Integer.MIN_VALUE ? widthSize2 : Float.MAX_VALUE;
        if (widthMode == 1073741824) {
            boring = boring2;
            hintBoring = hintBoring2;
            des = -1;
            fromexisting = false;
            width4 = widthSize2;
        } else {
            if (this.mLayout != null && this.mEllipsize == null) {
                des2 = desired(this.mLayout, this.mUseBoundsForWidth);
            }
            if (des2 < 0) {
                boring2 = BoringLayout.isBoring(this.mTransformed, this.mTextPaint, this.mTextDir, isFallbackLineSpacingForBoringLayout(), getResolvedMinimumFontMetrics(), this.mBoring);
                if (boring2 != null) {
                    this.mBoring = boring2;
                }
            } else {
                fromexisting2 = true;
            }
            if (boring2 == null || boring2 == UNKNOWN_BORING) {
                if (des2 < 0) {
                    des2 = (int) Math.ceil(Layout.getDesiredWidthWithLimit(this.mTransformed, 0, this.mTransformed.length(), this.mTextPaint, this.mTextDir, widthLimit, this.mUseBoundsForWidth));
                }
                width = des2;
            } else if (this.mUseBoundsForWidth) {
                RectF bbox = boring2.getDrawingBoundingBox();
                float rightMax = Math.max(bbox.right, boring2.width);
                float leftMin = Math.min(bbox.left, 0.0f);
                width = Math.max(boring2.width, (int) Math.ceil(rightMax - leftMin));
            } else {
                width = boring2.width;
            }
            if (isHighContrastTextEnabled()) {
                width += (int) Math.ceil(this.mTextPaint.getHCTStrokeWidth());
            }
            Drawables dr = this.mDrawables;
            if (dr != null) {
                width = Math.max(Math.max(width, dr.mDrawableWidthTop), dr.mDrawableWidthBottom);
            }
            if (this.mHint != null) {
                int hintDes = -1;
                if (this.mHintLayout != null && this.mEllipsize == null) {
                    hintDes = desired(this.mHintLayout, this.mUseBoundsForWidth);
                }
                if (hintDes < 0 && (hintBoring2 = BoringLayout.isBoring(this.mHint, this.mTextPaint, this.mTextDir, isFallbackLineSpacingForBoringLayout(), getResolvedMinimumFontMetrics(), this.mHintBoring)) != null) {
                    this.mHintBoring = hintBoring2;
                }
                if (hintBoring2 == null || hintBoring2 == UNKNOWN_BORING) {
                    if (hintDes < 0) {
                        hintDes = (int) Math.ceil(Layout.getDesiredWidthWithLimit(this.mHint, 0, this.mHint.length(), this.mTextPaint, this.mTextDir, widthLimit, this.mUseBoundsForWidth));
                    }
                    hintWidth = hintDes;
                } else {
                    hintWidth = hintBoring2.width;
                }
                if (hintWidth > width) {
                    width = hintWidth;
                }
            }
            int hintWidth2 = getCompoundPaddingLeft();
            int width7 = width + hintWidth2 + getCompoundPaddingRight();
            if (this.mMaxWidthMode == 1) {
                width2 = Math.min(width7, this.mMaxWidth * getLineHeight());
            } else {
                width2 = Math.min(width7, this.mMaxWidth);
            }
            if (this.mMinWidthMode == 1) {
                width3 = Math.max(width2, this.mMinWidth * getLineHeight());
            } else {
                width3 = Math.max(width2, this.mMinWidth);
            }
            int width8 = Math.max(width3, getSuggestedMinimumWidth());
            if (widthMode != Integer.MIN_VALUE) {
                boring = boring2;
                hintBoring = hintBoring2;
                des = des2;
                fromexisting = fromexisting2;
                width4 = width8;
            } else {
                boring = boring2;
                hintBoring = hintBoring2;
                des = des2;
                fromexisting = fromexisting2;
                width4 = Math.min(widthSize2, width8);
            }
        }
        int want = (width4 - getCompoundPaddingLeft()) - getCompoundPaddingRight();
        if (this.mHorizontallyScrolling) {
            want = 1048576;
        }
        int want2 = want;
        if (this.mHintLayout != null) {
            width5 = this.mHintLayout.getWidth();
        } else {
            width5 = want2;
        }
        int hintWidth3 = width5;
        if (this.mLayout == null) {
            unpaddedWidth = want;
            width6 = width4;
            widthSize = 1073741824;
            makeNewLayout(want2, want2, boring, hintBoring, (width4 - getCompoundPaddingLeft()) - getCompoundPaddingRight(), false);
        } else {
            unpaddedWidth = want;
            widthSize = 1073741824;
            width6 = width4;
            if (this.mLayout.getWidth() == want2) {
                hintWant = want2;
                if (hintWidth3 == hintWant && this.mLayout.getEllipsizedWidth() == (width6 - getCompoundPaddingLeft()) - getCompoundPaddingRight()) {
                    z = false;
                    layoutChanged = z;
                    boolean widthChanged = this.mHint != null && this.mEllipsize == null && want2 > this.mLayout.getWidth() && ((this.mLayout instanceof BoringLayout) || (fromexisting && des >= 0 && des <= want2));
                    maximumChanged = this.mMaxMode == this.mOldMaxMode || this.mMaximum != this.mOldMaximum;
                    if (!layoutChanged || maximumChanged) {
                        if (maximumChanged && widthChanged) {
                            this.mLayout.increaseWidthTo(want2);
                        } else {
                            makeNewLayout(want2, hintWant, boring, hintBoring, (width6 - getCompoundPaddingLeft()) - getCompoundPaddingRight(), false);
                        }
                    }
                }
            } else {
                hintWant = want2;
            }
            z = true;
            layoutChanged = z;
            boolean widthChanged2 = this.mHint != null && this.mEllipsize == null && want2 > this.mLayout.getWidth() && ((this.mLayout instanceof BoringLayout) || (fromexisting && des >= 0 && des <= want2));
            maximumChanged = this.mMaxMode == this.mOldMaxMode || this.mMaximum != this.mOldMaximum;
            if (!layoutChanged) {
            }
            if (maximumChanged) {
            }
            makeNewLayout(want2, hintWant, boring, hintBoring, (width6 - getCompoundPaddingLeft()) - getCompoundPaddingRight(), false);
        }
        if (heightMode2 == widthSize) {
            desired = heightSize;
            this.mDesiredHeightAtMeasure = -1;
        } else {
            int desired2 = getDesiredHeight();
            int height = desired2;
            this.mDesiredHeightAtMeasure = desired2;
            if (heightMode2 == Integer.MIN_VALUE) {
                height = Math.min(desired2, heightSize);
            }
            if (!isHighContrastTextEnabled()) {
                desired = height;
            } else {
                desired = height + ((int) Math.ceil(this.mTextPaint.getHCTStrokeWidth()));
            }
        }
        int height2 = getCompoundPaddingTop();
        int unpaddedHeight = (desired - height2) - getCompoundPaddingBottom();
        if (this.mMaxMode == 1 && this.mLayout.getLineCount() > this.mMaximum) {
            unpaddedHeight = Math.min(unpaddedHeight, this.mLayout.getLineTop(this.mMaximum));
        }
        if (this.mMovement != null) {
            unpaddedWidth2 = unpaddedWidth;
        } else {
            unpaddedWidth2 = unpaddedWidth;
            if (this.mLayout.getWidth() <= unpaddedWidth2 && this.mLayout.getHeight() <= unpaddedHeight) {
                scrollTo(0, 0);
                if (!ViewRune.WIDGET_PEN_SUPPORTED && this.mhasMultiSelection) {
                    CharSequence text = getTextForMultiSelection();
                    int[] selectRange = new int[2];
                    boolean flag = getVisibleTextRange(selectRange);
                    if (text != null && flag) {
                        int[] multiSelStart = MultiSelection.getMultiSelectionStart((Spannable) text);
                        int[] multiSelEnd = MultiSelection.getMultiSelectionEnd((Spannable) text);
                        int multiSelCount = MultiSelection.getMultiSelectionCount((Spannable) text);
                        int i = 0;
                        while (i < multiSelCount) {
                            int multiSelCount2 = multiSelCount;
                            int newStart = multiSelStart[i];
                            int unpaddedWidth3 = unpaddedWidth2;
                            int newEnd = multiSelEnd[i];
                            boolean flag2 = flag;
                            if (newStart < selectRange[0]) {
                                newStart = selectRange[0];
                                heightMode = heightMode2;
                                c = 1;
                            } else {
                                heightMode = heightMode2;
                                c = 1;
                                if (newStart > selectRange[1]) {
                                    newStart = selectRange[1];
                                }
                            }
                            if (newEnd < selectRange[0]) {
                                newEnd = selectRange[0];
                            } else if (newEnd > selectRange[c]) {
                                newEnd = selectRange[c];
                            }
                            if (multiSelStart[i] == newStart && multiSelEnd[i] == newEnd) {
                                i++;
                                multiSelCount = multiSelCount2;
                                heightMode2 = heightMode;
                                flag = flag2;
                                unpaddedWidth2 = unpaddedWidth3;
                            } else {
                                clearMultiSelection();
                                break;
                            }
                        }
                    }
                }
                setMeasuredDimension(width6, desired);
            }
        }
        registerForPreDraw();
        if (!ViewRune.WIDGET_PEN_SUPPORTED) {
        }
        setMeasuredDimension(width6, desired);
    }

    private void autoSizeText() {
        int availableWidth;
        if (!isAutoSizeEnabled()) {
            return;
        }
        if (this.mNeedsAutoSizeText) {
            if (getMeasuredWidth() <= 0 || getMeasuredHeight() <= 0) {
                return;
            }
            if (this.mHorizontallyScrolling) {
                availableWidth = 1048576;
            } else {
                availableWidth = (getMeasuredWidth() - getTotalPaddingLeft()) - getTotalPaddingRight();
            }
            int availableHeight = (getMeasuredHeight() - getExtendedPaddingBottom()) - getExtendedPaddingTop();
            if (availableWidth <= 0 || availableHeight <= 0) {
                return;
            }
            synchronized (TEMP_RECTF) {
                TEMP_RECTF.setEmpty();
                TEMP_RECTF.right = availableWidth;
                TEMP_RECTF.bottom = availableHeight;
                float optimalTextSize = findLargestTextSizeWhichFits(TEMP_RECTF);
                if (optimalTextSize != getTextSize()) {
                    setTextSizeInternal(0, optimalTextSize, false);
                    makeNewLayout(availableWidth, 0, UNKNOWN_BORING, UNKNOWN_BORING, ((this.mRight - this.mLeft) - getCompoundPaddingLeft()) - getCompoundPaddingRight(), false);
                }
            }
        }
        this.mNeedsAutoSizeText = true;
    }

    private int findLargestTextSizeWhichFits(RectF availableSpace) {
        int sizesCount = this.mAutoSizeTextSizesInPx.length;
        if (sizesCount == 0) {
            throw new IllegalStateException("No available text sizes to choose from.");
        }
        int bestSizeIndex = 0;
        int lowIndex = 0 + 1;
        int highIndex = sizesCount - 1;
        while (lowIndex <= highIndex) {
            int sizeToTryIndex = (lowIndex + highIndex) / 2;
            if (suggestedSizeFitsInSpace(this.mAutoSizeTextSizesInPx[sizeToTryIndex], availableSpace)) {
                bestSizeIndex = lowIndex;
                lowIndex = sizeToTryIndex + 1;
            } else {
                highIndex = sizeToTryIndex - 1;
                bestSizeIndex = highIndex;
            }
        }
        return this.mAutoSizeTextSizesInPx[bestSizeIndex];
    }

    private boolean suggestedSizeFitsInSpace(int suggestedSizeInPx, RectF availableSpace) {
        CharSequence text;
        if (this.mTransformed != null) {
            text = this.mTransformed;
        } else {
            text = getText();
        }
        int maxLines = getMaxLines();
        if (this.mTempTextPaint == null) {
            this.mTempTextPaint = new TextPaint();
        } else {
            this.mTempTextPaint.reset();
        }
        this.mTempTextPaint.set(getPaint());
        this.mTempTextPaint.setTextSize(suggestedSizeInPx);
        StaticLayout.Builder layoutBuilder = StaticLayout.Builder.obtain(text, 0, text.length(), this.mTempTextPaint, Math.round(availableSpace.right));
        layoutBuilder.setAlignment(getLayoutAlignment()).setLineSpacing(getLineSpacingExtra(), getLineSpacingMultiplier()).setIncludePad(getIncludeFontPadding()).setUseLineSpacingFromFallbacks(isFallbackLineSpacingForStaticLayout()).setBreakStrategy(getBreakStrategy()).setHyphenationFrequency(getHyphenationFrequency()).setJustificationMode(getJustificationMode()).setMaxLines(this.mMaxMode == 1 ? this.mMaximum : Integer.MAX_VALUE).setTextDirection(getTextDirectionHeuristic()).setLineBreakConfig(LineBreakConfig.getLineBreakConfig(this.mLineBreakStyle, this.mLineBreakWordStyle)).setUseBoundsForWidth(this.mUseBoundsForWidth).setMinimumFontMetrics(getResolvedMinimumFontMetrics());
        StaticLayout layout = layoutBuilder.build();
        return (maxLines == -1 || layout.getLineCount() <= maxLines) && ((float) layout.getHeight()) <= availableSpace.bottom;
    }

    private int getDesiredHeight() {
        return Math.max(getDesiredHeight(this.mLayout, true), getDesiredHeight(this.mHintLayout, this.mEllipsize != null));
    }

    private int getDesiredHeight(Layout layout, boolean cap) {
        if (layout == null) {
            return 0;
        }
        int desired = layout.getHeight(cap);
        Drawables dr = this.mDrawables;
        if (dr != null) {
            desired = Math.max(Math.max(desired, dr.mDrawableHeightLeft), dr.mDrawableHeightRight);
        }
        int linecount = layout.getLineCount();
        int padding = getCompoundPaddingTop() + getCompoundPaddingBottom();
        int desired2 = desired + padding;
        if (this.mMaxMode != 1) {
            desired2 = Math.min(desired2, this.mMaximum);
        } else if (cap && linecount > this.mMaximum && ((layout instanceof DynamicLayout) || (layout instanceof BoringLayout))) {
            int desired3 = layout.getLineTop(this.mMaximum);
            if (dr != null) {
                desired3 = Math.max(Math.max(desired3, dr.mDrawableHeightLeft), dr.mDrawableHeightRight);
            }
            desired2 = desired3 + padding;
            linecount = this.mMaximum;
        }
        if (this.mMinMode == 1) {
            if (linecount < this.mMinimum) {
                desired2 += getLineHeight() * (this.mMinimum - linecount);
            }
        } else {
            desired2 = Math.max(desired2, this.mMinimum);
        }
        return Math.max(desired2, getSuggestedMinimumHeight());
    }

    private void checkForResize() {
        boolean sizeChanged = false;
        if (this.mLayout != null) {
            if (this.mLayoutParams.width == -2) {
                sizeChanged = true;
                invalidate();
            }
            if (this.mLayoutParams.height == -2) {
                int desiredHeight = getDesiredHeight();
                if (isHighContrastTextEnabled()) {
                    desiredHeight += (int) Math.ceil(this.mTextPaint.getHCTStrokeWidth());
                }
                if (desiredHeight != getHeight()) {
                    sizeChanged = true;
                }
            } else if (this.mLayoutParams.height == -1 && this.mDesiredHeightAtMeasure >= 0 && getDesiredHeight() != this.mDesiredHeightAtMeasure) {
                sizeChanged = true;
            }
        }
        if (sizeChanged) {
            requestLayout();
        }
    }

    private void checkForRelayout() {
        if ((this.mLayoutParams.width != -2 || (this.mMaxWidthMode == this.mMinWidthMode && this.mMaxWidth == this.mMinWidth)) && ((this.mHint == null || this.mHintLayout != null) && ((this.mRight - this.mLeft) - getCompoundPaddingLeft()) - getCompoundPaddingRight() > 0)) {
            int oldht = this.mLayout.getHeight();
            int want = this.mLayout.getWidth();
            int hintWant = this.mHintLayout == null ? 0 : this.mHintLayout.getWidth();
            makeNewLayout(want, hintWant, UNKNOWN_BORING, UNKNOWN_BORING, ((this.mRight - this.mLeft) - getCompoundPaddingLeft()) - getCompoundPaddingRight(), false);
            if (this.mEllipsize != TextUtils.TruncateAt.MARQUEE) {
                if (this.mLayoutParams.height != -2 && this.mLayoutParams.height != -1) {
                    autoSizeText();
                    invalidate();
                    return;
                } else if (this.mLayout.getHeight() == oldht && (this.mHintLayout == null || this.mHintLayout.getHeight() == oldht)) {
                    autoSizeText();
                    invalidate();
                    return;
                }
            }
            requestLayout();
            invalidate();
            return;
        }
        nullLayouts();
        requestLayout();
        invalidate();
    }

    @Override // android.view.View
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (this.mDeferScroll >= 0) {
            int curs = this.mDeferScroll;
            this.mDeferScroll = -1;
            bringPointIntoView(Math.min(curs, this.mText.length()));
        }
        autoSizeText();
    }

    private boolean isShowingHint() {
        return (!TextUtils.isEmpty(this.mText) || TextUtils.isEmpty(this.mHint) || this.mHideHint) ? false : true;
    }

    private boolean bringTextIntoView() {
        int scrollx;
        int scrolly;
        Layout layout = isShowingHint() ? this.mHintLayout : this.mLayout;
        int line = 0;
        if ((this.mGravity & 112) == 80) {
            line = layout.getLineCount() - 1;
        }
        Layout.Alignment a = layout.getParagraphAlignment(line);
        int dir = layout.getParagraphDirection(line);
        int hspace = ((this.mRight - this.mLeft) - getCompoundPaddingLeft()) - getCompoundPaddingRight();
        int vspace = ((this.mBottom - this.mTop) - getExtendedPaddingTop()) - getExtendedPaddingBottom();
        int ht = layout.getHeight();
        if (a == Layout.Alignment.ALIGN_NORMAL) {
            a = dir == 1 ? Layout.Alignment.ALIGN_LEFT : Layout.Alignment.ALIGN_RIGHT;
        } else if (a == Layout.Alignment.ALIGN_OPPOSITE) {
            a = dir == 1 ? Layout.Alignment.ALIGN_RIGHT : Layout.Alignment.ALIGN_LEFT;
        }
        if (a == Layout.Alignment.ALIGN_CENTER) {
            int left = (int) Math.floor(layout.getLineLeft(line));
            int right = (int) Math.ceil(layout.getLineRight(line));
            if (right - left < hspace) {
                scrollx = ((right + left) / 2) - (hspace / 2);
            } else if (dir < 0) {
                scrollx = right - hspace;
            } else {
                scrollx = left;
            }
        } else if (a == Layout.Alignment.ALIGN_RIGHT) {
            scrollx = ((int) Math.ceil(layout.getLineRight(line))) - hspace;
        } else {
            scrollx = (int) Math.floor(layout.getLineLeft(line));
        }
        if (ht < vspace) {
            scrolly = 0;
        } else if ((this.mGravity & 112) == 80) {
            scrolly = ht - vspace;
        } else {
            scrolly = 0;
        }
        if (scrollx != this.mScrollX || scrolly != this.mScrollY) {
            scrollTo(scrollx, scrolly);
            return true;
        }
        return false;
    }

    public boolean bringPointIntoView(int offset) {
        return bringPointIntoView(offset, false);
    }

    public boolean bringPointIntoView(int offset, boolean requestRectWithoutFocus) {
        int grav;
        int vs;
        if (!isLayoutRequested()) {
            int offsetTransformed = originalToTransformed(offset, 1);
            Layout layout = isShowingHint() ? this.mHintLayout : this.mLayout;
            if (layout == null) {
                return false;
            }
            int line = layout.getLineForOffset(offsetTransformed);
            switch (layout.getParagraphAlignment(line)) {
                case ALIGN_LEFT:
                    grav = 1;
                    break;
                case ALIGN_RIGHT:
                    grav = -1;
                    break;
                case ALIGN_NORMAL:
                    grav = layout.getParagraphDirection(line);
                    break;
                case ALIGN_OPPOSITE:
                    int grav2 = layout.getParagraphDirection(line);
                    grav = -grav2;
                    break;
                default:
                    grav = 0;
                    break;
            }
            boolean clamped = grav > 0;
            int x = (int) layout.getPrimaryHorizontal(offsetTransformed, clamped);
            int top = layout.getLineTop(line);
            int bottom = layout.getLineTop(line + 1);
            int left = (int) Math.floor(layout.getLineLeft(line));
            int right = (int) Math.ceil(layout.getLineRight(line));
            int ht = layout.getHeight();
            int hspace = ((this.mRight - this.mLeft) - getCompoundPaddingLeft()) - getCompoundPaddingRight();
            int vspace = ((this.mBottom - this.mTop) - getExtendedPaddingTop()) - getExtendedPaddingBottom();
            if (!this.mHorizontallyScrolling && right - left > hspace && right > x) {
                right = Math.max(x, left + hspace);
            }
            int hslack = (bottom - top) / 2;
            int vslack = hslack;
            if (vslack > vspace / 4) {
                vslack = vspace / 4;
            }
            if (hslack > hspace / 4) {
                hslack = hspace / 4;
            }
            int hs = this.mScrollX;
            boolean changed = false;
            int vs2 = this.mScrollY;
            if (top - vs2 < vslack) {
                vs2 = top - vslack;
            }
            int vs3 = vs2;
            if (bottom - vs2 <= vspace - vslack) {
                vs = vs3;
            } else {
                vs = bottom - (vspace - vslack);
            }
            if (ht - vs < vspace) {
                vs = ht - vspace;
            }
            if (0 - vs > 0) {
                vs = 0;
            }
            if (grav != 0) {
                if (x - hs < hslack) {
                    hs = x - hslack;
                }
                int hs2 = hs;
                if (x - hs <= hspace - hslack) {
                    hs = hs2;
                } else {
                    hs = x - (hspace - hslack);
                }
            }
            if (grav < 0) {
                if (left - hs > 0) {
                    hs = left;
                }
                if (right - hs < hspace) {
                    hs = right - hspace;
                }
            } else if (grav > 0) {
                if (right - hs < hspace) {
                    hs = right - hspace;
                }
                if (left - hs > 0) {
                    hs = left;
                }
            } else if (right - left <= hspace) {
                hs = left - ((hspace - (right - left)) / 2);
            } else if (x > right - hslack) {
                hs = right - hspace;
            } else if (x < left + hslack) {
                hs = left;
            } else if (left > hs) {
                hs = left;
            } else if (right < hs + hspace) {
                hs = right - hspace;
            } else {
                if (x - hs < hslack) {
                    hs = x - hslack;
                }
                int hs3 = hs;
                if (x - hs <= hspace - hslack) {
                    hs = hs3;
                } else {
                    hs = x - (hspace - hslack);
                }
            }
            if (hs != this.mScrollX || vs != this.mScrollY) {
                if (this.mScroller != null) {
                    long duration = AnimationUtils.currentAnimationTimeMillis() - this.mLastScroll;
                    int dx = hs - this.mScrollX;
                    int dy = vs - this.mScrollY;
                    if (duration > 250) {
                        this.mScroller.startScroll(this.mScrollX, this.mScrollY, dx, dy);
                        awakenScrollBars(this.mScroller.getDuration());
                        invalidate();
                    } else {
                        if (!this.mScroller.isFinished()) {
                            this.mScroller.abortAnimation();
                        }
                        scrollBy(dx, dy);
                    }
                    this.mLastScroll = AnimationUtils.currentAnimationTimeMillis();
                } else {
                    scrollTo(hs, vs);
                }
                changed = true;
            }
            if (requestRectWithoutFocus || isFocused()) {
                if (this.mTempRect == null) {
                    this.mTempRect = new Rect();
                }
                this.mTempRect.set(x - 2, top, x + 2, bottom);
                getInterestingRect(this.mTempRect, line);
                this.mTempRect.offset(this.mScrollX, this.mScrollY);
                if (requestRectangleOnScreen(this.mTempRect)) {
                    return true;
                }
                return changed;
            }
            return changed;
        }
        this.mDeferScroll = offset;
        return false;
    }

    public boolean moveCursorToVisibleOffset() {
        if (!(this.mText instanceof Spannable)) {
            return false;
        }
        int start = getSelectionStartTransformed();
        int end = getSelectionEndTransformed();
        if (start != end) {
            return false;
        }
        int line = this.mLayout.getLineForOffset(start);
        int top = this.mLayout.getLineTop(line);
        int bottom = this.mLayout.getLineTop(line + 1);
        int vspace = ((this.mBottom - this.mTop) - getExtendedPaddingTop()) - getExtendedPaddingBottom();
        int vslack = (bottom - top) / 2;
        if (vslack > vspace / 4) {
            vslack = vspace / 4;
        }
        int vs = this.mScrollY;
        if (top < vs + vslack) {
            line = this.mLayout.getLineForVertical(vs + vslack + (bottom - top));
        } else if (bottom > (vspace + vs) - vslack) {
            line = this.mLayout.getLineForVertical(((vspace + vs) - vslack) - (bottom - top));
        }
        int hspace = ((this.mRight - this.mLeft) - getCompoundPaddingLeft()) - getCompoundPaddingRight();
        int hs = this.mScrollX;
        int leftChar = this.mLayout.getOffsetForHorizontal(line, hs);
        int rightChar = this.mLayout.getOffsetForHorizontal(line, hspace + hs);
        int lowChar = leftChar < rightChar ? leftChar : rightChar;
        int highChar = leftChar > rightChar ? leftChar : rightChar;
        int newStart = start;
        if (newStart < lowChar) {
            newStart = lowChar;
        } else if (newStart > highChar) {
            newStart = highChar;
        }
        if (newStart == start) {
            return false;
        }
        Selection.setSelection(this.mSpannable, transformedToOriginal(newStart, 1));
        return true;
    }

    @Override // android.view.View
    public void computeScroll() {
        if (this.mScroller != null && this.mScroller.computeScrollOffset()) {
            this.mScrollX = this.mScroller.getCurrX();
            this.mScrollY = this.mScroller.getCurrY();
            invalidateParentCaches();
            postInvalidate();
        }
    }

    private void getInterestingRect(Rect r, int line) {
        convertFromViewportToContentCoordinates(r);
        if (line == 0) {
            r.top -= getExtendedPaddingTop();
        }
        if (line == this.mLayout.getLineCount() - 1) {
            r.bottom += getExtendedPaddingBottom();
        }
    }

    private void convertFromViewportToContentCoordinates(Rect r) {
        int horizontalOffset = viewportToContentHorizontalOffset();
        r.left += horizontalOffset;
        r.right += horizontalOffset;
        int verticalOffset = viewportToContentVerticalOffset();
        r.top += verticalOffset;
        r.bottom += verticalOffset;
    }

    private PointF convertFromScreenToContentCoordinates(PointF point) {
        int[] screenToViewport = getLocationOnScreen();
        PointF copy = new PointF(point);
        copy.offset(-(screenToViewport[0] + viewportToContentHorizontalOffset()), -(screenToViewport[1] + viewportToContentVerticalOffset()));
        return copy;
    }

    private RectF convertFromScreenToContentCoordinates(RectF rect) {
        int[] screenToViewport = getLocationOnScreen();
        RectF copy = new RectF(rect);
        copy.offset(-(screenToViewport[0] + viewportToContentHorizontalOffset()), -(screenToViewport[1] + viewportToContentVerticalOffset()));
        return copy;
    }

    int viewportToContentHorizontalOffset() {
        return getCompoundPaddingLeft() - this.mScrollX;
    }

    int viewportToContentVerticalOffset() {
        int offset = getExtendedPaddingTop() - this.mScrollY;
        if ((this.mGravity & 112) != 48) {
            return offset + getVerticalOffset(false);
        }
        return offset;
    }

    @Override // android.view.View
    public void debug(int depth) {
        String output;
        super.debug(depth);
        String output2 = debugIndent(depth) + "frame={" + this.mLeft + ", " + this.mTop + ", " + this.mRight + ", " + this.mBottom + "} scroll={" + this.mScrollX + ", " + this.mScrollY + "} ";
        if (this.mText != null) {
            output = output2 + "mText=\"" + ((Object) this.mText) + "\" ";
            if (this.mLayout != null) {
                output = output + "mLayout width=" + this.mLayout.getWidth() + " height=" + this.mLayout.getHeight();
            }
        } else {
            output = output2 + "mText=NULL";
        }
        Log.d("View", output);
    }

    @ViewDebug.ExportedProperty(category = "text")
    public int getSelectionStart() {
        return Selection.getSelectionStart(getText());
    }

    @ViewDebug.ExportedProperty(category = "text")
    public int getSelectionEnd() {
        return Selection.getSelectionEnd(getText());
    }

    public void getSelection(int start, int end, Layout.SelectionRectangleConsumer consumer) {
        int transformedStart = originalToTransformed(start, 1);
        int transformedEnd = originalToTransformed(end, 1);
        this.mLayout.getSelection(transformedStart, transformedEnd, consumer);
    }

    int getSelectionStartTransformed() {
        int start = getSelectionStart();
        return start < 0 ? start : originalToTransformed(start, 1);
    }

    int getSelectionEndTransformed() {
        int end = getSelectionEnd();
        return end < 0 ? end : originalToTransformed(end, 1);
    }

    public boolean hasSelection() {
        int selectionMin;
        int selectionMax;
        int selectionStart = getSelectionStart();
        int selectionEnd = getSelectionEnd();
        if (selectionStart < selectionEnd) {
            selectionMin = selectionStart;
            selectionMax = selectionEnd;
        } else {
            selectionMin = selectionEnd;
            selectionMax = selectionStart;
        }
        return selectionMin >= 0 && selectionMax > 0 && selectionMin != selectionMax;
    }

    String getSelectedText() {
        if (!hasSelection()) {
            return null;
        }
        int start = getSelectionStart();
        int end = getSelectionEnd();
        CharSequence charSequence = this.mText;
        return String.valueOf(start > end ? charSequence.subSequence(end, start) : charSequence.subSequence(start, end));
    }

    public void setSingleLine() {
        setSingleLine(true);
    }

    @RemotableViewMethod
    public void setAllCaps(boolean allCaps) {
        if (allCaps) {
            setTransformationMethod(new AllCapsTransformationMethod(getContext()));
        } else {
            setTransformationMethod(null);
        }
    }

    public boolean isAllCaps() {
        TransformationMethod method = getTransformationMethod();
        return method != null && (method instanceof AllCapsTransformationMethod);
    }

    @RemotableViewMethod
    public void setSingleLine(boolean singleLine) {
        setInputTypeSingleLine(singleLine);
        applySingleLine(singleLine, true, true, true);
    }

    private void setInputTypeSingleLine(boolean singleLine) {
        if (this.mEditor != null && (this.mEditor.mInputType & 15) == 1) {
            if (singleLine) {
                this.mEditor.mInputType &= -131073;
            } else {
                this.mEditor.mInputType |= 131072;
            }
        }
    }

    private void applySingleLine(boolean singleLine, boolean applyTransformation, boolean changeMaxLines, boolean changeMaxLength) {
        this.mSingleLine = singleLine;
        if (singleLine) {
            setLines(1);
            setHorizontallyScrolling(true);
            if (applyTransformation) {
                setTransformationMethod(SingleLineTransformationMethod.getInstance());
            }
            if (changeMaxLength && this.mBufferType == BufferType.EDITABLE) {
                InputFilter[] prevFilters = getFilters();
                for (InputFilter filter : getFilters()) {
                    if (filter instanceof InputFilter.LengthFilter) {
                        return;
                    }
                }
                if (this.mSingleLineLengthFilter == null) {
                    this.mSingleLineLengthFilter = new InputFilter.LengthFilter(5000);
                }
                InputFilter[] newFilters = new InputFilter[prevFilters.length + 1];
                System.arraycopy(prevFilters, 0, newFilters, 0, prevFilters.length);
                newFilters[prevFilters.length] = this.mSingleLineLengthFilter;
                setFilters(newFilters);
                lambda$setTextAsync$0(getText());
                return;
            }
            return;
        }
        if (changeMaxLines) {
            setMaxLines(Integer.MAX_VALUE);
        }
        setHorizontallyScrolling(false);
        if (applyTransformation) {
            setTransformationMethod(null);
        }
        if (changeMaxLength && this.mBufferType == BufferType.EDITABLE) {
            InputFilter[] prevFilters2 = getFilters();
            if (prevFilters2.length == 0 || this.mSingleLineLengthFilter == null) {
                return;
            }
            int targetIndex = -1;
            int i = 0;
            while (true) {
                if (i >= prevFilters2.length) {
                    break;
                }
                if (prevFilters2[i] != this.mSingleLineLengthFilter) {
                    i++;
                } else {
                    targetIndex = i;
                    break;
                }
            }
            if (targetIndex == -1) {
                return;
            }
            if (prevFilters2.length == 1) {
                setFilters(NO_FILTERS);
                return;
            }
            InputFilter[] newFilters2 = new InputFilter[prevFilters2.length - 1];
            System.arraycopy(prevFilters2, 0, newFilters2, 0, targetIndex);
            System.arraycopy(prevFilters2, targetIndex + 1, newFilters2, targetIndex, (prevFilters2.length - targetIndex) - 1);
            setFilters(newFilters2);
            this.mSingleLineLengthFilter = null;
        }
    }

    public void setEllipsize(TextUtils.TruncateAt where) {
        if (this.mEllipsize != where) {
            this.mEllipsize = where;
            if (this.mLayout != null) {
                nullLayouts();
                requestLayout();
                invalidate();
            }
        }
    }

    public void setMarqueeRepeatLimit(int marqueeLimit) {
        this.mMarqueeRepeatLimit = marqueeLimit;
    }

    public int getMarqueeRepeatLimit() {
        return this.mMarqueeRepeatLimit;
    }

    @ViewDebug.ExportedProperty
    public TextUtils.TruncateAt getEllipsize() {
        return this.mEllipsize;
    }

    @RemotableViewMethod
    public void setSelectAllOnFocus(boolean selectAllOnFocus) {
        createEditorIfNeeded();
        this.mEditor.mSelectAllOnFocus = selectAllOnFocus;
        if (selectAllOnFocus && !(this.mText instanceof Spannable)) {
            setText(this.mText, BufferType.SPANNABLE);
        }
    }

    @RemotableViewMethod
    public void setCursorVisible(boolean visible) {
        this.mCursorVisibleFromAttr = visible;
        updateCursorVisibleInternal();
    }

    public void setImeConsumesInput(boolean imeConsumesInput) {
        this.mImeIsConsumingInput = imeConsumesInput;
        updateCursorVisibleInternal();
    }

    private void updateCursorVisibleInternal() {
        boolean visible = this.mCursorVisibleFromAttr && !this.mImeIsConsumingInput;
        if (visible && this.mEditor == null) {
            return;
        }
        createEditorIfNeeded();
        if (this.mEditor.mCursorVisible != visible) {
            this.mEditor.mCursorVisible = visible;
            invalidate();
            this.mEditor.makeBlink();
            this.mEditor.prepareCursorControllers();
        }
    }

    public boolean isCursorVisible() {
        if (this.mEditor == null) {
            return true;
        }
        return this.mEditor.mCursorVisible;
    }

    public boolean isCursorVisibleFromAttr() {
        return this.mCursorVisibleFromAttr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean canMarquee() {
        int width = ((this.mRight - this.mLeft) - getCompoundPaddingLeft()) - getCompoundPaddingRight();
        if (width > 0) {
            return this.mLayout.getLineWidth(0) > ((float) width) || !(this.mMarqueeFadeMode == 0 || this.mSavedMarqueeModeLayout == null || this.mSavedMarqueeModeLayout.getLineWidth(0) <= ((float) width));
        }
        return false;
    }

    protected void startMarquee() {
        if (getKeyListener() != null || compressText((getWidth() - getCompoundPaddingLeft()) - getCompoundPaddingRight())) {
            return;
        }
        if ((this.mMarquee == null || this.mMarquee.isStopped()) && isAggregatedVisible()) {
            if ((isFocused() || isSelected()) && getLineCount() == 1 && canMarquee()) {
                if (this.mMarqueeFadeMode == 1) {
                    this.mMarqueeFadeMode = 2;
                    Layout tmp = this.mLayout;
                    this.mLayout = this.mSavedMarqueeModeLayout;
                    this.mSavedMarqueeModeLayout = tmp;
                    setHorizontalFadingEdgeEnabled(true);
                    requestLayout();
                    invalidate();
                }
                if (this.mMarquee == null) {
                    this.mMarquee = new Marquee(this);
                }
                this.mMarquee.start(this.mMarqueeRepeatLimit);
            }
        }
    }

    protected void stopMarquee() {
        if (this.mMarquee != null && !this.mMarquee.isStopped()) {
            this.mMarquee.stop();
        }
        if (this.mMarqueeFadeMode == 2) {
            this.mMarqueeFadeMode = 1;
            Layout tmp = this.mSavedMarqueeModeLayout;
            this.mSavedMarqueeModeLayout = this.mLayout;
            this.mLayout = tmp;
            setHorizontalFadingEdgeEnabled(false);
            requestLayout();
            invalidate();
        }
    }

    private void startStopMarquee(boolean start) {
        if (this.mEllipsize == TextUtils.TruncateAt.MARQUEE) {
            if (start) {
                startMarquee();
            } else {
                stopMarquee();
            }
        }
    }

    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
    }

    protected void onSelectionChanged(int selStart, int selEnd) {
        sendAccessibilityEvent(8192);
    }

    public void addTextChangedListener(TextWatcher watcher) {
        if (this.mListeners == null) {
            this.mListeners = new ArrayList<>();
        }
        this.mListeners.add(watcher);
    }

    public void removeTextChangedListener(TextWatcher watcher) {
        int i;
        if (this.mListeners != null && (i = this.mListeners.indexOf(watcher)) >= 0) {
            this.mListeners.remove(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendBeforeTextChanged(CharSequence text, int start, int before, int after) {
        if (this.mListeners != null) {
            ArrayList<TextWatcher> list = this.mListeners;
            int count = list.size();
            for (int i = 0; i < count; i++) {
                list.get(i).beforeTextChanged(text, start, before, after);
            }
        }
        removeIntersectingNonAdjacentSpans(start, start + before, SpellCheckSpan.class);
        removeIntersectingNonAdjacentSpans(start, start + before, SuggestionSpan.class);
    }

    private <T> void removeIntersectingNonAdjacentSpans(int start, int end, Class<T> type) {
        if (this.mText instanceof Editable) {
            Editable text = (Editable) this.mText;
            Object[] spans = text.getSpans(start, end, type);
            ArrayList arrayList = new ArrayList();
            for (Object obj : spans) {
                int spanStart = text.getSpanStart(obj);
                int spanEnd = text.getSpanEnd(obj);
                if (spanEnd != start && spanStart != end) {
                    arrayList.add(obj);
                }
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                text.removeSpan(it.next());
            }
        }
    }

    void removeAdjacentSuggestionSpans(int pos) {
        if (this.mText instanceof Editable) {
            Editable text = (Editable) this.mText;
            SuggestionSpan[] spans = (SuggestionSpan[]) text.getSpans(pos, pos, SuggestionSpan.class);
            int length = spans.length;
            for (int i = 0; i < length; i++) {
                int spanStart = text.getSpanStart(spans[i]);
                int spanEnd = text.getSpanEnd(spans[i]);
                if ((spanEnd == pos || spanStart == pos) && SpellChecker.haveWordBoundariesChanged(text, pos, pos, spanStart, spanEnd)) {
                    text.removeSpan(spans[i]);
                }
            }
        }
    }

    void sendOnTextChanged(CharSequence text, int start, int before, int after) {
        if (this.mListeners != null) {
            ArrayList<TextWatcher> list = this.mListeners;
            int count = list.size();
            for (int i = 0; i < count; i++) {
                list.get(i).onTextChanged(text, start, before, after);
            }
        }
        if (ViewRune.WIDGET_PEN_SUPPORTED) {
            clearMultiSelection();
            if (this.mUseDisplayText && !this.mSkipUpdateDisplayText) {
                this.mDisplayText = new SpannableStringBuilder(this.mTransformed);
                Spannable sp = (Spannable) this.mDisplayText;
                if (this.mChangeWatcher == null) {
                    this.mChangeWatcher = new ChangeWatcher();
                }
                sp.setSpan(this.mChangeWatcher, 0, this.mDisplayText.length(), 6553618);
            }
            this.mSkipUpdateDisplayText = false;
        }
        if (this.mEditor != null) {
            this.mEditor.sendOnTextChanged(start, before, after);
        }
    }

    void sendAfterTextChanged(Editable text) {
        if (this.mListeners != null) {
            ArrayList<TextWatcher> list = this.mListeners;
            int count = list.size();
            for (int i = 0; i < count; i++) {
                list.get(i).afterTextChanged(text);
            }
        }
        notifyListeningManagersAfterTextChanged();
        hideErrorIfUnchanged();
    }

    private void notifyListeningManagersAfterTextChanged() {
        AutofillManager afm;
        if (isAutofillable() && (afm = (AutofillManager) this.mContext.getSystemService(AutofillManager.class)) != null) {
            if (Helper.sVerbose) {
                Log.v(LOG_TAG, "notifyAutoFillManagerAfterTextChanged");
            }
            afm.notifyValueChanged(this);
        }
        notifyContentCaptureTextChanged();
    }

    public void notifyContentCaptureTextChanged() {
        ContentCaptureManager cm;
        ContentCaptureSession session;
        if (isLaidOut() && isImportantForContentCapture() && getNotifiedContentCaptureAppeared() && (cm = (ContentCaptureManager) this.mContext.getSystemService(ContentCaptureManager.class)) != null && cm.isContentCaptureEnabled() && (session = getContentCaptureSession()) != null) {
            session.notifyViewTextChanged(getAutofillId(), getText());
        }
    }

    private boolean isAutofillable() {
        return getAutofillType() != 0;
    }

    void updateAfterEdit() {
        invalidate();
        int curs = getSelectionStart();
        if (curs >= 0 || (this.mGravity & 112) == 80) {
            registerForPreDraw();
        }
        checkForResize();
        if (curs >= 0) {
            this.mHighlightPathBogus = true;
            if (this.mEditor != null) {
                this.mEditor.makeBlink();
            }
            bringPointIntoView(curs);
        }
    }

    void handleTextChanged(CharSequence buffer, int start, int before, int after) {
        sLastCutCopyOrTextChangedTime = 0L;
        Editor.InputMethodState ims = this.mEditor == null ? null : this.mEditor.mInputMethodState;
        if (ims == null || ims.mBatchEditNesting == 0) {
            updateAfterEdit();
        }
        if (ims != null) {
            ims.mContentChanged = true;
            if (ims.mChangedStart < 0) {
                ims.mChangedStart = start;
                ims.mChangedEnd = start + before;
            } else {
                ims.mChangedStart = Math.min(ims.mChangedStart, start);
                ims.mChangedEnd = Math.max(ims.mChangedEnd, (start + before) - ims.mChangedDelta);
            }
            ims.mChangedDelta += after - before;
        }
        resetErrorChangedFlag();
        sendOnTextChanged(buffer, start, before, after);
        onTextChanged(buffer, start, before, after);
        this.mHideHint = false;
        clearGesturePreviewHighlight();
    }

    void spanChange(Spanned buf, Object what, int oldStart, int newStart, int oldEnd, int newEnd) {
        boolean selChanged = false;
        int newSelStart = -1;
        int newSelEnd = -1;
        Editor.InputMethodState ims = this.mEditor == null ? null : this.mEditor.mInputMethodState;
        if (ViewRune.WIDGET_PEN_SUPPORTED) {
            boolean multiSelChanged = false;
            if (what == MultiSelection.CURRENT_SELECTION_END) {
                multiSelChanged = true;
                newSelEnd = newStart;
                if (oldStart >= 0 || newStart >= 0) {
                    invalidateCursor(MultiSelection.getSelectionStart(buf), oldStart, newStart);
                    checkForResize();
                    registerForPreDraw();
                    if (this.mEditor != null) {
                        this.mEditor.makeBlink();
                    }
                }
            }
            if (what == MultiSelection.CURRENT_SELECTION_START) {
                multiSelChanged = true;
                newSelStart = newStart;
                if (oldStart >= 0 || newStart >= 0) {
                    int end = MultiSelection.getSelectionEnd(buf);
                    invalidateCursor(end, oldStart, newStart);
                }
            }
            if (multiSelChanged) {
                this.mHighlightPathBogus = true;
                if (this.mEditor != null && !isFocused()) {
                    this.mEditor.mSelectionMoved = true;
                }
            }
        }
        if (what == Selection.SELECTION_END) {
            selChanged = true;
            newSelEnd = newStart;
            if (oldStart >= 0 || newStart >= 0) {
                invalidateCursor(Selection.getSelectionStart(buf), oldStart, newStart);
                checkForResize();
                registerForPreDraw();
                if (this.mEditor != null) {
                    this.mEditor.makeBlink();
                }
            }
        }
        if (what == Selection.SELECTION_START) {
            selChanged = true;
            newSelStart = newStart;
            if (oldStart >= 0 || newStart >= 0) {
                int end2 = Selection.getSelectionEnd(buf);
                invalidateCursor(end2, oldStart, newStart);
            }
        }
        if (selChanged) {
            clearGesturePreviewHighlight();
            this.mHighlightPathBogus = true;
            if (this.mEditor != null && !isFocused()) {
                this.mEditor.mSelectionMoved = true;
            }
            if ((buf.getSpanFlags(what) & 512) == 0) {
                if (newSelStart < 0) {
                    newSelStart = Selection.getSelectionStart(buf);
                }
                if (newSelEnd < 0) {
                    newSelEnd = Selection.getSelectionEnd(buf);
                }
                if (this.mEditor != null) {
                    this.mEditor.refreshTextActionMode();
                    if (!hasSelection() && this.mEditor.getTextActionMode() == null && hasTransientState()) {
                        setHasTransientState(false);
                    }
                }
                onSelectionChanged(newSelStart, newSelEnd);
            }
        }
        if ((what instanceof UpdateAppearance) || (what instanceof ParagraphStyle) || (what instanceof CharacterStyle)) {
            if (ims == null || ims.mBatchEditNesting == 0) {
                invalidate();
                this.mHighlightPathBogus = true;
                checkForResize();
            } else {
                ims.mContentChanged = true;
            }
            if (this.mEditor != null) {
                if (oldStart >= 0) {
                    this.mEditor.invalidateTextDisplayList(this.mLayout, oldStart, oldEnd);
                }
                if (newStart >= 0) {
                    this.mEditor.invalidateTextDisplayList(this.mLayout, newStart, newEnd);
                }
                this.mEditor.invalidateHandlesAndActionMode();
            }
        }
        if (MetaKeyKeyListener.isMetaTracker(buf, what)) {
            this.mHighlightPathBogus = true;
            if (ims != null && MetaKeyKeyListener.isSelectingMetaTracker(buf, what)) {
                ims.mSelectionModeChanged = true;
            }
            if (Selection.getSelectionStart(buf) >= 0) {
                if (ims == null || ims.mBatchEditNesting == 0) {
                    invalidateCursor();
                } else {
                    ims.mCursorChanged = true;
                }
            }
        }
        if ((what instanceof ParcelableSpan) && ims != null && ims.mExtractedTextRequest != null) {
            if (ims.mBatchEditNesting != 0) {
                if (oldStart >= 0) {
                    if (ims.mChangedStart > oldStart) {
                        ims.mChangedStart = oldStart;
                    }
                    if (ims.mChangedStart > oldEnd) {
                        ims.mChangedStart = oldEnd;
                    }
                }
                if (newStart >= 0) {
                    if (ims.mChangedStart > newStart) {
                        ims.mChangedStart = newStart;
                    }
                    if (ims.mChangedStart > newEnd) {
                        ims.mChangedStart = newEnd;
                    }
                }
            } else {
                ims.mContentChanged = true;
            }
        }
        if (this.mEditor != null && this.mEditor.mSpellChecker != null && newStart < 0 && (what instanceof SpellCheckSpan)) {
            this.mEditor.mSpellChecker.onSpellCheckSpanRemoved((SpellCheckSpan) what);
        }
    }

    @Override // android.view.View
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        if (isTemporarilyDetached()) {
            super.onFocusChanged(focused, direction, previouslyFocusedRect);
            return;
        }
        this.mHideHint = false;
        if (this.mEditor != null) {
            this.mEditor.onFocusChanged(focused, direction);
        }
        if (focused && this.mSpannable != null) {
            MetaKeyKeyListener.resetMetaState(this.mSpannable);
        }
        startStopMarquee(focused);
        if (this.mTransformation != null) {
            this.mTransformation.onFocusChanged(this, this.mText, focused, direction, previouslyFocusedRect);
        }
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
    }

    @Override // android.view.View
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (this.mEditor != null) {
            this.mEditor.onWindowFocusChanged(hasWindowFocus);
        }
        startStopMarquee(hasWindowFocus);
    }

    @Override // android.view.View
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (this.mEditor != null && visibility != 0) {
            this.mEditor.hideCursorAndSpanControllers();
            stopTextActionMode();
        }
        if (ViewRune.WIDGET_PEN_SUPPORTED && visibility != 0) {
            clearMultiSelection();
        }
    }

    @Override // android.view.View
    public void onVisibilityAggregated(boolean isVisible) {
        super.onVisibilityAggregated(isVisible);
        startStopMarquee(isVisible);
    }

    public void clearComposingText() {
        if (this.mText instanceof Spannable) {
            BaseInputConnection.removeComposingSpans(this.mSpannable);
        }
    }

    @Override // android.view.View
    public void setSelected(boolean selected) {
        boolean wasSelected = isSelected();
        super.setSelected(selected);
        if (selected != wasSelected && this.mEllipsize == TextUtils.TruncateAt.MARQUEE) {
            if (selected) {
                startMarquee();
            } else {
                stopMarquee();
            }
        }
    }

    boolean isFromPrimePointer(MotionEvent event, boolean fromHandleView) {
        boolean res = true;
        boolean z = false;
        if (this.mPrimePointerId == -1) {
            this.mPrimePointerId = event.getPointerId(0);
            this.mIsPrimePointerFromHandleView = fromHandleView;
        } else if (this.mPrimePointerId != event.getPointerId(0)) {
            if (this.mIsPrimePointerFromHandleView && fromHandleView) {
                z = true;
            }
            res = z;
        }
        if (event.getActionMasked() == 1 || event.getActionMasked() == 3) {
            this.mPrimePointerId = -1;
        }
        return res;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm;
        this.mLastInputSource = event.getSource();
        int action = event.getActionMasked();
        if (this.mEditor != null) {
            if (!isFromPrimePointer(event, false)) {
                return true;
            }
            this.mEditor.onTouchEvent(event);
            if (this.mEditor.mInsertionPointCursorController != null && this.mEditor.mInsertionPointCursorController.isCursorBeingModified()) {
                return true;
            }
            if (this.mEditor.mSelectionModifierCursorController != null && this.mEditor.mSelectionModifierCursorController.isDragAcceleratorActive()) {
                return true;
            }
        }
        boolean superResult = super.onTouchEvent(event);
        if (this.mEditor != null && this.mEditor.mDiscardNextActionUp && action == 1) {
            this.mEditor.mDiscardNextActionUp = false;
            if (this.mEditor.mIsInsertionActionModeStartPending) {
                this.mEditor.startInsertionActionMode();
                this.mEditor.mIsInsertionActionModeStartPending = false;
            }
            return superResult;
        }
        if (Flags.handwritingEndOfLineTap() && action == 1 && shouldStartHandwritingForEndOfLineTap(event) && (imm = getInputMethodManager()) != null) {
            imm.startStylusHandwriting(this);
            return true;
        }
        boolean touchIsFinished = action == 1 && (this.mEditor == null || !this.mEditor.mIgnoreActionUpEvent) && isFocused() && (event.getToolType(0) != 2 || (event.getButtonState() & 32) == 0);
        if ((this.mMovement != null || onCheckIsTextEditor()) && isEnabled() && (this.mText instanceof Spannable) && this.mLayout != null) {
            boolean handled = false;
            if (this.mMovement != null) {
                handled = false | this.mMovement.onTouchEvent(this, this.mSpannable, event);
                boolean isShiftOn = MetaKeyKeyListener.getMetaState((Spannable) this.mText, 1) == 1;
                if (isShiftOn && action == 1 && handled && this.mEditor != null) {
                    this.mEditor.startSelectionActionModeAsync(false);
                }
            }
            boolean textIsSelectable = isTextSelectable();
            if (touchIsFinished && this.mLinksClickable && this.mAutoLinkMask != 0 && textIsSelectable) {
                ClickableSpan[] links = (ClickableSpan[]) this.mSpannable.getSpans(getSelectionStart(), getSelectionEnd(), ClickableSpan.class);
                if (links.length > 0) {
                    links[0].onClick(this);
                    handled = true;
                }
            }
            if (touchIsFinished && (isTextEditable() || textIsSelectable)) {
                InputMethodManager imm2 = getInputMethodManager();
                viewClicked(imm2);
                if (isTextEditable() && this.mEditor.mShowSoftInputOnFocus && imm2 != null && !showAutofillDialog()) {
                    imm2.showSoftInput(this, 0);
                }
                this.mEditor.onTouchUpEvent(event);
                handled = true;
            }
            if (handled) {
                return true;
            }
        }
        return superResult;
    }

    private boolean shouldStartHandwritingForEndOfLineTap(MotionEvent actionUpEvent) {
        int cursorOffset;
        if (!onCheckIsTextEditor() || !isEnabled() || !isAutoHandwritingEnabled() || TextUtils.isEmpty(this.mText) || didTouchFocusSelect() || this.mLayout == null || !actionUpEvent.isStylusPointer() || (cursorOffset = getSelectionStart()) < 0 || getSelectionEnd() != cursorOffset) {
            return false;
        }
        int cursorLine = this.mLayout.getLineForOffset(cursorOffset);
        int cursorLineEnd = this.mLayout.getLineEnd(cursorLine);
        if (cursorLine != this.mLayout.getLineCount() - 1) {
            cursorLineEnd--;
        }
        if (cursorLineEnd != cursorOffset || getLineAtCoordinate(actionUpEvent.getY()) != cursorLine) {
            return false;
        }
        float localX = convertToLocalHorizontalCoordinate(actionUpEvent.getX());
        if (this.mLayout.getParagraphDirection(cursorLine) != -1 ? localX > this.mLayout.getLineRight(cursorLine) : localX < this.mLayout.getLineLeft(cursorLine)) {
            return isStylusHandwritingAvailable();
        }
        return false;
    }

    public final boolean showUIForTouchScreen() {
        return (this.mLastInputSource & 4098) == 4098;
    }

    private boolean showAutofillDialog() {
        AutofillManager afm = (AutofillManager) this.mContext.getSystemService(AutofillManager.class);
        if (afm != null) {
            return afm.showAutofillDialog(this);
        }
        return false;
    }

    @Override // android.view.View
    public boolean onGenericMotionEvent(MotionEvent event) {
        if (this.mMovement != null && (this.mText instanceof Spannable) && this.mLayout != null) {
            try {
                if (this.mMovement.onGenericMotionEvent(this, this.mSpannable, event)) {
                    return true;
                }
            } catch (AbstractMethodError e) {
            }
        }
        return super.onGenericMotionEvent(event);
    }

    @Override // android.view.View
    protected void onCreateContextMenu(ContextMenu menu) {
        if (this.mEditor != null) {
            this.mEditor.onCreateContextMenu(menu);
        }
    }

    @Override // android.view.View
    public boolean showContextMenu() {
        if (this.mEditor != null) {
            this.mEditor.setContextMenuAnchor(Float.NaN, Float.NaN);
        }
        return super.showContextMenu();
    }

    @Override // android.view.View
    public boolean showContextMenu(float x, float y) {
        if (this.mEditor != null) {
            this.mEditor.setContextMenuAnchor(x, y);
        }
        return super.showContextMenu(x, y);
    }

    boolean isTextEditable() {
        return (this.mText instanceof Editable) && onCheckIsTextEditor() && isEnabled();
    }

    boolean isTextAutofillable() {
        return (this.mText instanceof Editable) && onCheckIsTextEditor();
    }

    public boolean didTouchFocusSelect() {
        return this.mEditor != null && this.mEditor.mTouchFocusSelected;
    }

    @Override // android.view.View
    public void cancelLongPress() {
        super.cancelLongPress();
        if (this.mEditor != null) {
            this.mEditor.mIgnoreActionUpEvent = true;
        }
    }

    @Override // android.view.View
    public boolean onTrackballEvent(MotionEvent event) {
        if (this.mMovement != null && this.mSpannable != null && this.mLayout != null && this.mMovement.onTrackballEvent(this, this.mSpannable, event)) {
            return true;
        }
        return super.onTrackballEvent(event);
    }

    public void setScroller(Scroller s) {
        this.mScroller = s;
    }

    @Override // android.view.View
    protected float getLeftFadingEdgeStrength() {
        if (isMarqueeFadeEnabled() && this.mMarquee != null && !this.mMarquee.isStopped()) {
            Marquee marquee = this.mMarquee;
            if (marquee.shouldDrawLeftFade()) {
                return getHorizontalFadingEdgeStrength(marquee.getScroll(), 0.0f);
            }
            return 0.0f;
        }
        if (getLineCount() == 1) {
            float lineLeft = getLayout().getLineLeft(0);
            if (lineLeft > this.mScrollX) {
                return 0.0f;
            }
            return getHorizontalFadingEdgeStrength(this.mScrollX, lineLeft);
        }
        return super.getLeftFadingEdgeStrength();
    }

    @Override // android.view.View
    protected float getRightFadingEdgeStrength() {
        if (isMarqueeFadeEnabled() && this.mMarquee != null && !this.mMarquee.isStopped()) {
            Marquee marquee = this.mMarquee;
            return getHorizontalFadingEdgeStrength(marquee.getMaxFadeScroll(), marquee.getScroll());
        }
        if (getLineCount() == 1) {
            float rightEdge = this.mScrollX + ((getWidth() - getCompoundPaddingLeft()) - getCompoundPaddingRight());
            float lineRight = getLayout().getLineRight(0);
            if (lineRight < rightEdge) {
                return 0.0f;
            }
            return getHorizontalFadingEdgeStrength(rightEdge, lineRight);
        }
        return super.getRightFadingEdgeStrength();
    }

    private float getHorizontalFadingEdgeStrength(float position1, float position2) {
        int horizontalFadingEdgeLength = getHorizontalFadingEdgeLength();
        if (horizontalFadingEdgeLength == 0) {
            return 0.0f;
        }
        float diff = Math.abs(position1 - position2);
        if (diff > horizontalFadingEdgeLength) {
            return 1.0f;
        }
        return diff / horizontalFadingEdgeLength;
    }

    private boolean isMarqueeFadeEnabled() {
        return this.mEllipsize == TextUtils.TruncateAt.MARQUEE && this.mMarqueeFadeMode != 1;
    }

    @Override // android.view.View
    protected int computeHorizontalScrollRange() {
        if (this.mLayout != null) {
            return (this.mSingleLine && (this.mGravity & 7) == 3) ? (int) this.mLayout.getLineWidth(0) : this.mLayout.getWidth();
        }
        return super.computeHorizontalScrollRange();
    }

    @Override // android.view.View
    protected int computeVerticalScrollRange() {
        if (this.mLayout != null) {
            return this.mLayout.getHeight();
        }
        return super.computeVerticalScrollRange();
    }

    @Override // android.view.View
    protected int computeVerticalScrollExtent() {
        return (getHeight() - getCompoundPaddingTop()) - getCompoundPaddingBottom();
    }

    @Override // android.view.View
    public void findViewsWithText(ArrayList<View> outViews, CharSequence searched, int flags) {
        super.findViewsWithText(outViews, searched, flags);
        if (!outViews.contains(this) && (flags & 1) != 0 && !TextUtils.isEmpty(searched) && !TextUtils.isEmpty(this.mText)) {
            String searchedLowerCase = searched.toString().toLowerCase();
            String textLowerCase = this.mText.toString().toLowerCase();
            if (textLowerCase.contains(searchedLowerCase)) {
                outViews.add(this);
            }
        }
    }

    public static ColorStateList getTextColors(Context context, TypedArray attrs) {
        int ap;
        if (attrs == null) {
            throw new NullPointerException();
        }
        TypedArray a = context.obtainStyledAttributes(R.styleable.TextView);
        ColorStateList colors = a.getColorStateList(5);
        if (colors == null && (ap = a.getResourceId(1, 0)) != 0) {
            TypedArray appearance = context.obtainStyledAttributes(ap, R.styleable.TextAppearance);
            colors = appearance.getColorStateList(3);
            appearance.recycle();
        }
        a.recycle();
        return colors;
    }

    public static int getTextColor(Context context, TypedArray attrs, int def) {
        ColorStateList colors = getTextColors(context, attrs);
        if (colors == null) {
            return def;
        }
        return colors.getDefaultColor();
    }

    @Override // android.view.View
    public boolean onKeyShortcut(int keyCode, KeyEvent event) {
        if (event.hasModifiers(4096)) {
            switch (keyCode) {
                case 29:
                    if (canSelectText()) {
                        return onTextContextMenuItem(16908319);
                    }
                    break;
                case 31:
                case 124:
                    if (canCopy()) {
                        return onTextContextMenuItem(16908321);
                    }
                    break;
                case 32:
                    if (canDelete()) {
                        return onTextContextMenuItem(16908993);
                    }
                    break;
                case 50:
                    if (canPaste()) {
                        return onTextContextMenuItem(16908322);
                    }
                    break;
                case 52:
                    if (canCut()) {
                        return onTextContextMenuItem(16908320);
                    }
                    break;
                case 53:
                    if (canRedo()) {
                        return onTextContextMenuItem(16908339);
                    }
                    break;
                case 54:
                    if (canUndo()) {
                        return onTextContextMenuItem(16908338);
                    }
                    break;
            }
        } else if (event.hasModifiers(4097)) {
            switch (keyCode) {
                case 50:
                    if (canPaste()) {
                        return onTextContextMenuItem(16908337);
                    }
                    break;
                case 54:
                    if (canRedo()) {
                        return onTextContextMenuItem(16908339);
                    }
                    break;
            }
        } else if (event.hasModifiers(1)) {
            switch (keyCode) {
                case 124:
                    if (canPaste()) {
                        return onTextContextMenuItem(16908322);
                    }
                    break;
            }
        }
        return super.onKeyShortcut(keyCode, event);
    }

    boolean canSelectText() {
        return (this.mText.length() == 0 || this.mEditor == null || !this.mEditor.hasSelectionController()) ? false : true;
    }

    boolean textCanBeSelected() {
        if (this.mMovement == null || !this.mMovement.canSelectArbitrarily()) {
            return false;
        }
        return isTextEditable() || (isTextSelectable() && (this.mText instanceof Spannable) && isEnabled());
    }

    private Locale getTextServicesLocale(boolean allowNullLocale) {
        updateTextServicesLocaleAsync();
        return (this.mCurrentSpellCheckerLocaleCache != null || allowNullLocale) ? this.mCurrentSpellCheckerLocaleCache : Locale.getDefault();
    }

    public final void setTextOperationUser(UserHandle user) {
        if (Objects.equals(this.mTextOperationUser, user)) {
            return;
        }
        if (user != null && !Process.myUserHandle().equals(user) && getContext().checkSelfPermission(Manifest.permission.INTERACT_ACROSS_USERS_FULL) != 0) {
            throw new SecurityException("INTERACT_ACROSS_USERS_FULL is required. userId=" + user.getIdentifier() + " callingUserId" + UserHandle.myUserId());
        }
        this.mTextOperationUser = user;
        this.mCurrentSpellCheckerLocaleCache = null;
        if (this.mEditor != null) {
            this.mEditor.onTextOperationUserChanged();
        }
    }

    @Override // android.view.View
    public boolean isAutoHandwritingEnabled() {
        return super.isAutoHandwritingEnabled() && !isAnyPasswordInputType();
    }

    @Override // android.view.View
    public boolean shouldTrackHandwritingArea() {
        return super.shouldTrackHandwritingArea() || (Flags.handwritingUnsupportedMessage() && onCheckIsTextEditor());
    }

    @Override // android.view.View
    public boolean isStylusHandwritingAvailable() {
        if (this.mTextOperationUser == null) {
            return super.isStylusHandwritingAvailable();
        }
        InputMethodManager imm = getInputMethodManager();
        return imm.isStylusHandwritingAvailableAsUser(this.mTextOperationUser);
    }

    final TextServicesManager getTextServicesManagerForUser() {
        return (TextServicesManager) getServiceManagerForUser("android", TextServicesManager.class);
    }

    final ClipboardManager getClipboardManagerForUser() {
        return (ClipboardManager) getServiceManagerForUser(getContext().getPackageName(), ClipboardManager.class);
    }

    final TextClassificationManager getTextClassificationManagerForUser() {
        return (TextClassificationManager) getServiceManagerForUser(getContext().getPackageName(), TextClassificationManager.class);
    }

    final <T> T getServiceManagerForUser(String str, Class<T> cls) {
        if (this.mTextOperationUser == null) {
            return (T) getContext().getSystemService(cls);
        }
        try {
            return (T) getContext().createPackageContextAsUser(str, 0, this.mTextOperationUser).getSystemService(cls);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    void startActivityAsTextOperationUserIfNecessary(Intent intent) {
        if (this.mTextOperationUser != null) {
            getContext().startActivityAsUser(intent, this.mTextOperationUser);
        } else {
            getContext().startActivity(intent);
        }
    }

    public Locale getTextServicesLocale() {
        return getTextServicesLocale(false);
    }

    public boolean isInExtractedMode() {
        return false;
    }

    private boolean isAutoSizeEnabled() {
        return supportsAutoSizeText() && this.mAutoSizeTextType != 0;
    }

    protected boolean supportsAutoSizeText() {
        return true;
    }

    public Locale getSpellCheckerLocale() {
        return getTextServicesLocale(true);
    }

    private void updateTextServicesLocaleAsync() {
        AsyncTask.execute(new Runnable() { // from class: android.widget.TextView.3
            @Override // java.lang.Runnable
            public void run() {
                TextView.this.updateTextServicesLocaleLocked();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateTextServicesLocaleLocked() {
        Locale locale;
        TextServicesManager textServicesManager = getTextServicesManagerForUser();
        if (textServicesManager == null) {
            return;
        }
        SpellCheckerSubtype subtype = textServicesManager.getCurrentSpellCheckerSubtype(true);
        if (subtype != null) {
            locale = subtype.getLocaleObject();
        } else {
            locale = null;
        }
        this.mCurrentSpellCheckerLocaleCache = locale;
    }

    void onLocaleChanged() {
        this.mEditor.onLocaleChanged();
    }

    public WordIterator getWordIterator() {
        if (this.mEditor != null) {
            return this.mEditor.getWordIterator();
        }
        return null;
    }

    @Override // android.view.View
    public void onPopulateAccessibilityEventInternal(AccessibilityEvent event) {
        super.onPopulateAccessibilityEventInternal(event);
        if (isAccessibilityDataSensitive() && !event.isAccessibilityDataSensitive()) {
            return;
        }
        CharSequence text = getTextForAccessibility();
        if (!TextUtils.isEmpty(text)) {
            if (hasPasswordTransformationMethod() && this.mText.length() > 0) {
                String textForVoiceAssistant = text.subSequence(0, text.length() - 1).toString();
                event.getText().add(textForVoiceAssistant + this.mText.charAt(this.mText.length() - 1));
                return;
            }
            event.getText().add(text);
        }
    }

    @Override // android.view.View
    public CharSequence getAccessibilityClassName() {
        return TextView.class.getName();
    }

    /* JADX WARN: Code restructure failed: missing block: B:93:0x0152, code lost:
    
        if (r6 >= r0.length()) goto L74;
     */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void onProvideStructure(android.view.ViewStructure r24, int r25, int r26) {
        /*
            Method dump skipped, instructions count: 564
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.TextView.onProvideStructure(android.view.ViewStructure, int, int):void");
    }

    boolean canRequestAutofill() {
        AutofillManager afm;
        if (isFlipCoverClosed() || this.mIsThemeDeviceDefault || (this.mActionModeFlags & 131072) != 131072 || !isAutofillable() || (afm = (AutofillManager) this.mContext.getSystemService(AutofillManager.class)) == null) {
            return false;
        }
        return afm.isEnabled();
    }

    private void requestAutofill() {
        AutofillManager afm = (AutofillManager) this.mContext.getSystemService(AutofillManager.class);
        if (afm != null) {
            afm.requestAutofill(this);
        }
    }

    @Override // android.view.View
    public void autofill(AutofillValue value) {
        if (Helper.sVerbose) {
            Log.v(LOG_TAG, "autofill() called on textview for id:" + getAutofillId());
        }
        if (!isTextAutofillable()) {
            Log.w(LOG_TAG, "cannot autofill non-editable TextView: " + this);
        } else {
            if (!value.isText()) {
                Log.w(LOG_TAG, "value of type " + value.describeContents() + " cannot be autofilled into " + this);
                return;
            }
            ClipData clip = ClipData.newPlainText("", value.getTextValue());
            ContentInfo payload = new ContentInfo.Builder(clip, 4).build();
            performReceiveContent(payload);
        }
    }

    @Override // android.view.View
    public int getAutofillType() {
        return isTextAutofillable() ? 1 : 0;
    }

    @Override // android.view.View
    public AutofillValue getAutofillValue() {
        if (isTextAutofillable()) {
            CharSequence text = TextUtils.trimToParcelableSize(getText());
            return AutofillValue.forText(text);
        }
        return null;
    }

    @Override // android.view.View
    public void onInitializeAccessibilityEventInternal(AccessibilityEvent event) {
        super.onInitializeAccessibilityEventInternal(event);
        boolean isPassword = hasPasswordTransformationMethod();
        event.setPassword(isPassword);
        if (event.getEventType() == 8192) {
            event.setFromIndex(Selection.getSelectionStart(this.mText));
            event.setToIndex(Selection.getSelectionEnd(this.mText));
            event.setItemCount(this.mText.length());
        }
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfoInternal(info);
        boolean isPassword = hasPasswordTransformationMethod();
        info.setPassword(isPassword);
        info.setText(getTextForAccessibility());
        info.setHintText(this.mHint);
        info.setShowingHintText(isShowingHint());
        if (this.mBufferType == BufferType.EDITABLE) {
            info.setEditable(true);
            if (isEnabled()) {
                info.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SET_TEXT);
            }
        }
        if (this.mEditor != null) {
            info.setInputType(this.mEditor.mInputType);
            if (this.mEditor.mError != null) {
                info.setContentInvalid(true);
                info.setError(this.mEditor.mError);
            }
            if (isTextEditable() && isFocused()) {
                CharSequence imeActionLabel = this.mContext.getResources().getString(com.android.internal.R.string.keyboardview_keycode_enter);
                if (getImeActionLabel() != null) {
                    imeActionLabel = getImeActionLabel();
                }
                AccessibilityNodeInfo.AccessibilityAction action = new AccessibilityNodeInfo.AccessibilityAction(16908372, imeActionLabel);
                info.addAction(action);
            }
        }
        CharSequence imeActionLabel2 = this.mText;
        if (!TextUtils.isEmpty(imeActionLabel2)) {
            info.addAction(256);
            info.addAction(512);
            info.setMovementGranularities(31);
            info.addAction(131072);
            info.setAvailableExtraData(Arrays.asList(AccessibilityNodeInfo.EXTRA_DATA_RENDERING_INFO_KEY, AccessibilityNodeInfo.EXTRA_DATA_TEXT_CHARACTER_LOCATION_KEY));
            info.setTextSelectable(isTextSelectable() || isTextEditable());
        } else {
            info.setAvailableExtraData(Arrays.asList(AccessibilityNodeInfo.EXTRA_DATA_RENDERING_INFO_KEY));
        }
        if (isFocused()) {
            if (canCopy()) {
                info.addAction(16384);
            }
            if (canPaste()) {
                info.addAction(32768);
            }
            if (canCut()) {
                info.addAction(65536);
            }
            if (canReplace()) {
                info.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SHOW_TEXT_SUGGESTIONS);
            }
            if (canShare()) {
                info.addAction(new AccessibilityNodeInfo.AccessibilityAction(268435456, getResources().getString(com.android.internal.R.string.share)));
            }
            if (canProcessText()) {
                this.mEditor.mProcessTextIntentActionsHandler.onInitializeAccessibilityNodeInfo(info);
                this.mEditor.onInitializeSmartActionsAccessibilityNodeInfo(info);
            }
        }
        int numFilters = this.mFilters.length;
        for (int i = 0; i < numFilters; i++) {
            InputFilter filter = this.mFilters[i];
            if (filter instanceof InputFilter.LengthFilter) {
                info.setMaxTextLength(((InputFilter.LengthFilter) filter).getMax());
            }
        }
        if (!isSingleLine()) {
            info.setMultiLine(true);
        }
        if (info.isClickable() || info.isLongClickable()) {
            if ((this.mMovement instanceof LinkMovementMethod) || (isTextSelectable() && !isTextEditable())) {
                if (!hasOnClickListeners()) {
                    info.setClickable(false);
                    info.removeAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK);
                }
                if (!hasOnLongClickListeners()) {
                    info.setLongClickable(false);
                    info.removeAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_LONG_CLICK);
                }
            }
        }
    }

    @Override // android.view.View
    public void addExtraDataToAccessibilityNodeInfo(AccessibilityNodeInfo info, String extraDataKey, Bundle arguments) {
        RectF bounds;
        if (arguments != null && extraDataKey.equals(AccessibilityNodeInfo.EXTRA_DATA_TEXT_CHARACTER_LOCATION_KEY)) {
            int positionInfoStartIndex = arguments.getInt(AccessibilityNodeInfo.EXTRA_DATA_TEXT_CHARACTER_LOCATION_ARG_START_INDEX, -1);
            int positionInfoLength = arguments.getInt(AccessibilityNodeInfo.EXTRA_DATA_TEXT_CHARACTER_LOCATION_ARG_LENGTH, -1);
            if (positionInfoLength <= 0 || positionInfoStartIndex < 0 || positionInfoStartIndex >= this.mText.length()) {
                Log.e(LOG_TAG, "Invalid arguments for accessibility character locations");
                return;
            }
            RectF[] boundingRects = new RectF[positionInfoLength];
            CursorAnchorInfo.Builder builder = new CursorAnchorInfo.Builder();
            populateCharacterBounds(builder, positionInfoStartIndex, Math.min(positionInfoStartIndex + positionInfoLength, length()), viewportToContentHorizontalOffset(), viewportToContentVerticalOffset());
            CursorAnchorInfo cursorAnchorInfo = builder.setMatrix(null).build();
            for (int i = 0; i < positionInfoLength; i++) {
                int flags = cursorAnchorInfo.getCharacterBoundsFlags(positionInfoStartIndex + i);
                if ((flags & 1) == 1 && (bounds = cursorAnchorInfo.getCharacterBounds(positionInfoStartIndex + i)) != null) {
                    mapRectFromViewToScreenCoords(bounds, true);
                    boundingRects[i] = bounds;
                }
            }
            info.getExtras().putParcelableArray(extraDataKey, boundingRects);
            return;
        }
        if (extraDataKey.equals(AccessibilityNodeInfo.EXTRA_DATA_RENDERING_INFO_KEY)) {
            AccessibilityNodeInfo.ExtraRenderingInfo extraRenderingInfo = AccessibilityNodeInfo.ExtraRenderingInfo.obtain();
            extraRenderingInfo.setLayoutSize(getLayoutParams().width, getLayoutParams().height);
            extraRenderingInfo.setTextSizeInPx(getTextSize());
            extraRenderingInfo.setTextSizeUnit(getTextSizeUnit());
            info.setExtraRenderingInfo(extraRenderingInfo);
        }
    }

    private boolean getViewVisibleRect(Rect rect) {
        if (!getLocalVisibleRect(rect)) {
            return false;
        }
        rect.offset(-getScrollX(), -getScrollY());
        return true;
    }

    private boolean getContentVisibleRect(Rect rect) {
        if (!getViewVisibleRect(rect)) {
            return false;
        }
        return rect.intersect(getCompoundPaddingLeft(), getCompoundPaddingTop(), getWidth() - getCompoundPaddingRight(), getHeight() - getCompoundPaddingBottom());
    }

    public void populateCharacterBounds(CursorAnchorInfo.Builder builder, int startIndex, int endIndex, float viewportToContentHorizontalOffset, float viewportToContentVerticalOffset) {
        if (isOffsetMappingAvailable()) {
            return;
        }
        Rect rect = new Rect();
        getContentVisibleRect(rect);
        RectF visibleRect = new RectF(rect);
        float[] characterBounds = getCharacterBounds(startIndex, endIndex, viewportToContentHorizontalOffset, viewportToContentVerticalOffset);
        int limit = endIndex - startIndex;
        for (int offset = 0; offset < limit; offset++) {
            float left = characterBounds[offset * 4];
            float top = characterBounds[(offset * 4) + 1];
            float right = characterBounds[(offset * 4) + 2];
            float bottom = characterBounds[(offset * 4) + 3];
            boolean hasVisibleRegion = visibleRect.intersects(left, top, right, bottom);
            boolean hasInVisibleRegion = !visibleRect.contains(left, top, right, bottom);
            int characterBoundsFlags = 0;
            if (hasVisibleRegion) {
                characterBoundsFlags = 0 | 1;
            }
            if (hasInVisibleRegion) {
                characterBoundsFlags |= 2;
            }
            if (this.mLayout.isRtlCharAt(offset)) {
                characterBoundsFlags |= 4;
            }
            builder.addCharacterBounds(offset + startIndex, left, top, right, bottom, characterBoundsFlags);
        }
    }

    private float[] getCharacterBounds(int start, int end, float layoutLeft, float layoutTop) {
        float[] characterBounds = new float[(end - start) * 4];
        this.mLayout.fillCharacterBounds(start, end, characterBounds, 0);
        for (int offset = 0; offset < end - start; offset++) {
            int i = offset * 4;
            characterBounds[i] = characterBounds[i] + layoutLeft;
            int i2 = (offset * 4) + 1;
            characterBounds[i2] = characterBounds[i2] + layoutTop;
            int i3 = (offset * 4) + 2;
            characterBounds[i3] = characterBounds[i3] + layoutLeft;
            int i4 = (offset * 4) + 3;
            characterBounds[i4] = characterBounds[i4] + layoutTop;
        }
        return characterBounds;
    }

    public CursorAnchorInfo getCursorAnchorInfo(int filter, CursorAnchorInfo.Builder cursorAnchorInfoBuilder, Matrix viewToScreenMatrix) {
        float viewportToContentVerticalOffset;
        int selectionStart;
        CursorAnchorInfo.Builder builder;
        CursorAnchorInfo.Builder builder2;
        int insertionMarkerFlags;
        int composingTextEnd;
        int composingTextStart;
        RectF editorBounds;
        RectF handwritingBounds;
        Layout layout = getLayout();
        if (layout == null) {
            return null;
        }
        boolean includeEditorBounds = (filter & 4) != 0;
        boolean includeCharacterBounds = (filter & 8) != 0;
        boolean includeInsertionMarker = (filter & 16) != 0;
        boolean includeVisibleLineBounds = (filter & 32) != 0;
        boolean includeTextAppearance = (filter & 64) != 0;
        boolean includeAll = (includeEditorBounds || includeCharacterBounds || includeInsertionMarker || includeVisibleLineBounds || includeTextAppearance) ? false : true;
        boolean includeEditorBounds2 = includeEditorBounds | includeAll;
        boolean includeCharacterBounds2 = includeCharacterBounds | includeAll;
        boolean includeInsertionMarker2 = includeInsertionMarker | includeAll;
        boolean includeVisibleLineBounds2 = includeVisibleLineBounds | includeAll;
        boolean includeTextAppearance2 = includeTextAppearance | includeAll;
        cursorAnchorInfoBuilder.reset();
        int selectionStart2 = getSelectionStart();
        cursorAnchorInfoBuilder.setSelectionRange(selectionStart2, getSelectionEnd());
        viewToScreenMatrix.reset();
        transformMatrixToGlobal(viewToScreenMatrix);
        cursorAnchorInfoBuilder.setMatrix(viewToScreenMatrix);
        if (includeEditorBounds2) {
            if (this.mTempRect == null) {
                this.mTempRect = new Rect();
            }
            Rect bounds = this.mTempRect;
            if (getViewVisibleRect(bounds)) {
                editorBounds = new RectF(bounds);
                handwritingBounds = new RectF(editorBounds);
                handwritingBounds.top -= getHandwritingBoundsOffsetTop();
                handwritingBounds.left -= getHandwritingBoundsOffsetLeft();
                handwritingBounds.bottom += getHandwritingBoundsOffsetBottom();
                handwritingBounds.right += getHandwritingBoundsOffsetRight();
            } else {
                editorBounds = new RectF();
                handwritingBounds = new RectF();
            }
            EditorBoundsInfo.Builder boundsBuilder = new EditorBoundsInfo.Builder();
            EditorBoundsInfo editorBoundsInfo = boundsBuilder.setEditorBounds(editorBounds).setHandwritingBounds(handwritingBounds).build();
            cursorAnchorInfoBuilder.setEditorBoundsInfo(editorBoundsInfo);
        }
        if (!includeCharacterBounds2 && !includeInsertionMarker2 && !includeVisibleLineBounds2) {
            builder2 = cursorAnchorInfoBuilder;
        } else {
            float viewportToContentHorizontalOffset = viewportToContentHorizontalOffset();
            float viewportToContentVerticalOffset2 = viewportToContentVerticalOffset();
            boolean isTextTransformed = getTransformationMethod() != null && (getTransformed() instanceof OffsetMapping);
            if (!includeCharacterBounds2 || isTextTransformed) {
                viewportToContentVerticalOffset = viewportToContentVerticalOffset2;
                selectionStart = selectionStart2;
                builder = cursorAnchorInfoBuilder;
            } else {
                CharSequence text = getText();
                if (!(text instanceof Spannable)) {
                    viewportToContentVerticalOffset = viewportToContentVerticalOffset2;
                    selectionStart = selectionStart2;
                    builder = cursorAnchorInfoBuilder;
                } else {
                    Spannable sp = (Spannable) text;
                    int composingTextStart2 = EditableInputConnection.getComposingSpanStart(sp);
                    int composingTextEnd2 = EditableInputConnection.getComposingSpanEnd(sp);
                    if (composingTextEnd2 >= composingTextStart2) {
                        composingTextEnd = composingTextStart2;
                        composingTextStart = composingTextEnd2;
                    } else {
                        composingTextEnd = composingTextEnd2;
                        composingTextStart = composingTextStart2;
                    }
                    boolean hasComposingText = composingTextEnd >= 0 && composingTextEnd < composingTextStart;
                    if (!hasComposingText) {
                        viewportToContentVerticalOffset = viewportToContentVerticalOffset2;
                        selectionStart = selectionStart2;
                        builder = cursorAnchorInfoBuilder;
                    } else {
                        CharSequence composingText = text.subSequence(composingTextEnd, composingTextStart);
                        cursorAnchorInfoBuilder.setComposingText(composingTextEnd, composingText);
                        viewportToContentVerticalOffset = viewportToContentVerticalOffset2;
                        selectionStart = selectionStart2;
                        builder = cursorAnchorInfoBuilder;
                        populateCharacterBounds(cursorAnchorInfoBuilder, composingTextEnd, composingTextStart, viewportToContentHorizontalOffset, viewportToContentVerticalOffset);
                    }
                }
            }
            if (includeInsertionMarker2 && selectionStart >= 0) {
                int offsetTransformed = originalToTransformed(selectionStart, 1);
                int line = layout.getLineForOffset(offsetTransformed);
                float insertionMarkerX = layout.getPrimaryHorizontal(offsetTransformed, layout.shouldClampCursor(line)) + viewportToContentHorizontalOffset;
                float insertionMarkerTop = layout.getLineTop(line) + viewportToContentVerticalOffset;
                float insertionMarkerBaseline = layout.getLineBaseline(line) + viewportToContentVerticalOffset;
                float insertionMarkerBottom = layout.getLineBottom(line, false) + viewportToContentVerticalOffset;
                boolean isTopVisible = isPositionVisible(insertionMarkerX, insertionMarkerTop);
                boolean isBottomVisible = isPositionVisible(insertionMarkerX, insertionMarkerBottom);
                int insertionMarkerFlags2 = 0;
                if (isTopVisible || isBottomVisible) {
                    insertionMarkerFlags2 = 0 | 1;
                }
                if (!isTopVisible || !isBottomVisible) {
                    insertionMarkerFlags2 |= 2;
                }
                if (!layout.isRtlCharAt(offsetTransformed)) {
                    insertionMarkerFlags = insertionMarkerFlags2;
                } else {
                    insertionMarkerFlags = insertionMarkerFlags2 | 4;
                }
                int line2 = insertionMarkerFlags;
                builder.setInsertionMarkerLocation(insertionMarkerX, insertionMarkerTop, insertionMarkerBaseline, insertionMarkerBottom, line2);
            }
            if (!includeVisibleLineBounds2) {
                builder2 = builder;
            } else {
                Rect visibleRect = new Rect();
                if (getContentVisibleRect(visibleRect)) {
                    float visibleTop = visibleRect.top - viewportToContentVerticalOffset;
                    float visibleBottom = visibleRect.bottom - viewportToContentVerticalOffset;
                    int firstLine = layout.getLineForVertical((int) Math.floor(visibleTop));
                    int lastLine = layout.getLineForVertical((int) Math.ceil(visibleBottom));
                    int line3 = firstLine;
                    while (line3 <= lastLine) {
                        float left = layout.getLineLeft(line3) + viewportToContentHorizontalOffset;
                        Rect visibleRect2 = visibleRect;
                        float top = layout.getLineTop(line3) + viewportToContentVerticalOffset;
                        float visibleTop2 = visibleTop;
                        int firstLine2 = firstLine;
                        float bottom = layout.getLineBottom(line3, false) + viewportToContentVerticalOffset;
                        builder.addVisibleLineBounds(left, top, layout.getLineRight(line3) + viewportToContentHorizontalOffset, bottom);
                        line3++;
                        firstLine = firstLine2;
                        visibleRect = visibleRect2;
                        visibleBottom = visibleBottom;
                        visibleTop = visibleTop2;
                    }
                    builder2 = builder;
                } else {
                    builder2 = builder;
                }
            }
        }
        if (includeTextAppearance2) {
            builder2.setTextAppearanceInfo(TextAppearanceInfo.createFromTextView(this));
        }
        return builder2.build();
    }

    public TextBoundsInfo getTextBoundsInfo(RectF bounds) {
        CharSequence text;
        RectF localBounds;
        Layout layout = getLayout();
        if (layout != null && (text = layout.getText()) != null && !isOffsetMappingAvailable()) {
            Matrix localToGlobalMatrix = new Matrix();
            transformMatrixToGlobal(localToGlobalMatrix);
            Matrix globalToLocalMatrix = new Matrix();
            if (!localToGlobalMatrix.invert(globalToLocalMatrix)) {
                return null;
            }
            float layoutLeft = viewportToContentHorizontalOffset();
            float layoutTop = viewportToContentVerticalOffset();
            RectF localBounds2 = new RectF(bounds);
            globalToLocalMatrix.mapRect(localBounds2);
            localBounds2.offset(-layoutLeft, -layoutTop);
            if (localBounds2.intersects(0.0f, 0.0f, layout.getWidth(), layout.getHeight()) && text.length() != 0) {
                int startLine = layout.getLineForVertical((int) Math.floor(localBounds2.top));
                int endLine = layout.getLineForVertical((int) Math.floor(localBounds2.bottom));
                int start = layout.getLineStart(startLine);
                int end = layout.getLineEnd(endLine);
                float[] characterBounds = getCharacterBounds(start, end, layoutLeft, layoutTop);
                int[] characterFlags = new int[end - start];
                int[] characterBidiLevels = new int[end - start];
                int line = startLine;
                while (true) {
                    if (line > endLine) {
                        break;
                    }
                    int lineStart = layout.getLineStart(line);
                    float layoutLeft2 = layoutLeft;
                    int lineEnd = layout.getLineEnd(line);
                    Matrix globalToLocalMatrix2 = globalToLocalMatrix;
                    Layout.Directions directions = layout.getLineDirections(line);
                    float layoutTop2 = layoutTop;
                    int i = 0;
                    while (true) {
                        localBounds = localBounds2;
                        if (i >= directions.getRunCount()) {
                            break;
                        }
                        int runStart = directions.getRunStart(i) + lineStart;
                        int runEnd = Math.min(runStart + directions.getRunLength(i), lineEnd);
                        float[] characterBounds2 = characterBounds;
                        int runLevel = directions.getRunLevel(i);
                        Arrays.fill(characterBidiLevels, runStart - start, runEnd - start, runLevel);
                        i++;
                        localBounds2 = localBounds;
                        characterBounds = characterBounds2;
                        directions = directions;
                    }
                    float[] characterBounds3 = characterBounds;
                    boolean lineIsRtl = layout.getParagraphDirection(line) == -1;
                    for (int index = lineStart; index < lineEnd; index++) {
                        int flags = 0;
                        if (TextUtils.isWhitespace(text.charAt(index))) {
                            flags = 0 | 1;
                        }
                        if (TextUtils.isPunctuation(Character.codePointAt(text, index))) {
                            flags |= 4;
                        }
                        if (TextUtils.isNewline(Character.codePointAt(text, index))) {
                            flags |= 2;
                        }
                        if (lineIsRtl) {
                            flags |= 8;
                        }
                        characterFlags[index - start] = flags;
                    }
                    line++;
                    layoutLeft = layoutLeft2;
                    globalToLocalMatrix = globalToLocalMatrix2;
                    localBounds2 = localBounds;
                    layoutTop = layoutTop2;
                    characterBounds = characterBounds3;
                }
                float[] characterBounds4 = characterBounds;
                SegmentFinder graphemeSegmentFinder = new GraphemeClusterSegmentFinder(text, layout.getPaint());
                WordIterator wordIterator = getWordIterator();
                wordIterator.setCharSequence(text, 0, text.length());
                SegmentFinder wordSegmentFinder = new WordSegmentFinder(text, wordIterator);
                int lineCount = (endLine - startLine) + 1;
                int[] lineRanges = new int[lineCount * 2];
                for (int line2 = startLine; line2 <= endLine; line2++) {
                    int offset = line2 - startLine;
                    lineRanges[offset * 2] = layout.getLineStart(line2);
                    lineRanges[(offset * 2) + 1] = layout.getLineEnd(line2);
                }
                SegmentFinder lineSegmentFinder = new SegmentFinder.PrescribedSegmentFinder(lineRanges);
                return new TextBoundsInfo.Builder(start, end).setMatrix(localToGlobalMatrix).setCharacterBounds(characterBounds4).setCharacterBidiLevel(characterBidiLevels).setCharacterFlags(characterFlags).setGraphemeSegmentFinder(graphemeSegmentFinder).setLineSegmentFinder(lineSegmentFinder).setWordSegmentFinder(wordSegmentFinder).build();
            }
            TextBoundsInfo.Builder builder = new TextBoundsInfo.Builder(0, 0);
            SegmentFinder emptySegmentFinder = new SegmentFinder.PrescribedSegmentFinder(new int[0]);
            builder.setMatrix(localToGlobalMatrix).setCharacterBounds(new float[0]).setCharacterBidiLevel(new int[0]).setCharacterFlags(new int[0]).setGraphemeSegmentFinder(emptySegmentFinder).setLineSegmentFinder(emptySegmentFinder).setWordSegmentFinder(emptySegmentFinder);
            return builder.build();
        }
        return null;
    }

    public boolean isPositionVisible(float positionX, float positionY) {
        synchronized (TEMP_POSITION) {
            float[] position = TEMP_POSITION;
            position[0] = positionX;
            position[1] = positionY;
            View view = this;
            while (view != null) {
                if (view != this) {
                    position[0] = position[0] - view.getScrollX();
                    position[1] = position[1] - view.getScrollY();
                }
                if (position[0] >= 0.0f && position[1] >= 0.0f && position[0] <= view.getWidth() && position[1] <= view.getHeight()) {
                    if (!view.getMatrix().isIdentity()) {
                        view.getMatrix().mapPoints(position);
                    }
                    position[0] = position[0] + view.getLeft();
                    position[1] = position[1] + view.getTop();
                    Object parent = view.getParent();
                    if (parent instanceof View) {
                        view = (View) parent;
                    } else {
                        view = null;
                    }
                }
                return false;
            }
            return true;
        }
    }

    @Override // android.view.View
    public boolean performAccessibilityActionInternal(int action, Bundle arguments) {
        int start;
        int end;
        int updatedTextLength;
        if (this.mEditor != null && (this.mEditor.mProcessTextIntentActionsHandler.performAccessibilityAction(action) || this.mEditor.performSmartActionsAccessibilityAction(action))) {
            return true;
        }
        switch (action) {
            case 16:
                return performAccessibilityActionClick(arguments);
            case 32:
                if (!isLongClickable()) {
                    return false;
                }
                if (isEnabled() && this.mBufferType == BufferType.EDITABLE) {
                    this.mEditor.mIsBeingLongClickedByAccessibility = true;
                    try {
                        boolean handled = performLongClick();
                        return handled;
                    } finally {
                        this.mEditor.mIsBeingLongClickedByAccessibility = false;
                    }
                }
                boolean handled2 = performLongClick();
                return handled2;
            case 256:
            case 512:
                ensureIterableTextForAccessibilitySelectable();
                return super.performAccessibilityActionInternal(action, arguments);
            case 16384:
                return isFocused() && canCopy() && onTextContextMenuItem(16908321);
            case 32768:
                return isFocused() && canPaste() && onTextContextMenuItem(16908322);
            case 65536:
                return isFocused() && canCut() && onTextContextMenuItem(16908320);
            case 131072:
                ensureIterableTextForAccessibilitySelectable();
                CharSequence text = getIterableTextForAccessibility();
                if (text == null) {
                    return false;
                }
                if (arguments != null) {
                    start = arguments.getInt(AccessibilityNodeInfo.ACTION_ARGUMENT_SELECTION_START_INT, -1);
                } else {
                    start = -1;
                }
                if (arguments != null) {
                    end = arguments.getInt(AccessibilityNodeInfo.ACTION_ARGUMENT_SELECTION_END_INT, -1);
                } else {
                    end = -1;
                }
                if (getSelectionStart() != start || getSelectionEnd() != end) {
                    if (start == end && end == -1) {
                        Selection.removeSelection((Spannable) text);
                        return true;
                    }
                    if (start >= 0 && start <= end && end <= text.length()) {
                        requestFocusOnNonEditableSelectableText();
                        Selection.setSelection((Spannable) text, start, end);
                        if (this.mEditor != null) {
                            this.mEditor.setUseCtxMenuInDesktopMode(false);
                            this.mEditor.startSelectionActionModeAsync(false);
                        }
                        return true;
                    }
                }
                return false;
            case 2097152:
                if (!isEnabled() || this.mBufferType != BufferType.EDITABLE) {
                    return false;
                }
                lambda$setTextAsync$0(arguments != null ? arguments.getCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE) : null);
                if (this.mText != null && (updatedTextLength = this.mText.length()) > 0) {
                    Selection.setSelection(this.mSpannable, updatedTextLength);
                }
                return true;
            case 16908372:
                if (isFocused() && isTextEditable()) {
                    onEditorAction(getImeActionId());
                }
                return true;
            case 268435456:
                return isFocused() && canShare() && onTextContextMenuItem(16908341);
            default:
                if (action == 16908376) {
                    return isFocused() && canReplace() && onTextContextMenuItem(16908340);
                }
                return super.performAccessibilityActionInternal(action, arguments);
        }
    }

    private boolean performAccessibilityActionClick(Bundle arguments) {
        boolean handled = false;
        if (!isEnabled()) {
            return false;
        }
        if (isClickable() || isLongClickable()) {
            if (isFocusable() && !isFocused()) {
                requestFocus();
            }
            performClick();
            handled = true;
        }
        if ((this.mMovement != null || onCheckIsTextEditor()) && hasSpannableText() && this.mLayout != null) {
            if ((isTextEditable() || isTextSelectable()) && isFocused()) {
                InputMethodManager imm = getInputMethodManager();
                viewClicked(imm);
                if (!isTextSelectable() && this.mEditor.mShowSoftInputOnFocus && imm != null) {
                    return handled | imm.showSoftInput(this, 0);
                }
                return handled;
            }
            return handled;
        }
        return handled;
    }

    private void requestFocusOnNonEditableSelectableText() {
        if (!isTextEditable() && isTextSelectable() && isEnabled() && isFocusable() && !isFocused()) {
            requestFocus();
        }
    }

    private boolean hasSpannableText() {
        return this.mText != null && (this.mText instanceof Spannable);
    }

    @Override // android.view.View
    public void sendAccessibilityEventInternal(int eventType) {
        if (eventType == 32768 && this.mEditor != null) {
            this.mEditor.mProcessTextIntentActionsHandler.initializeAccessibilityActions();
        }
        super.sendAccessibilityEventInternal(eventType);
    }

    @Override // android.view.View, android.view.accessibility.AccessibilityEventSource
    public void sendAccessibilityEventUnchecked(AccessibilityEvent event) {
        if (event.getEventType() == 4096) {
            return;
        }
        super.sendAccessibilityEventUnchecked(event);
    }

    private CharSequence getTextForAccessibility() {
        if (TextUtils.isEmpty(this.mText)) {
            return this.mHint;
        }
        return TextUtils.trimToParcelableSize(this.mTransformed);
    }

    boolean isVisibleToAccessibility() {
        return AccessibilityManager.getInstance(this.mContext).isEnabled() && (isFocused() || (isSelected() && isShown()));
    }

    void sendAccessibilityEventTypeViewTextChanged(CharSequence beforeText, int fromIndex, int removedCount, int addedCount) {
        AccessibilityEvent event = AccessibilityEvent.obtain(16);
        event.setFromIndex(fromIndex);
        event.setRemovedCount(removedCount);
        event.setAddedCount(addedCount);
        event.setBeforeText(beforeText);
        sendAccessibilityEventUnchecked(event);
    }

    void sendAccessibilityEventTypeViewTextChanged(CharSequence beforeText, int fromIndex, int toIndex) {
        AccessibilityEvent event = AccessibilityEvent.obtain(16);
        event.setFromIndex(fromIndex);
        event.setToIndex(toIndex);
        event.setBeforeText(beforeText);
        sendAccessibilityEventUnchecked(event);
    }

    private InputMethodManager getInputMethodManager() {
        return (InputMethodManager) getContext().getSystemService(InputMethodManager.class);
    }

    public boolean isInputMethodTarget() {
        InputMethodManager imm = getInputMethodManager();
        return imm != null && imm.isActive(this);
    }

    public boolean onTextContextMenuItem(int id) {
        String resolveInfo;
        int min = 0;
        int max = this.mText.length();
        if (isFocused()) {
            int selStart = getSelectionStart();
            int selEnd = getSelectionEnd();
            min = Math.max(0, Math.min(selStart, selEnd));
            max = Math.max(0, Math.max(selStart, selEnd));
        }
        switch (id) {
            case 16908319:
                boolean hadSelection = hasSelection();
                selectAllText();
                if (this.mEditor != null && hadSelection) {
                    this.mEditor.invalidateActionModeAsync();
                }
                return true;
            case 16908320:
                ClipData cutData = ClipData.newPlainText(null, getTransformedText(min, max));
                if (setPrimaryClip(cutData)) {
                    deleteText_internal(min, max);
                } else {
                    Toast.makeText(getContext(), com.android.internal.R.string.failed_to_copy_to_clipboard, 0).show();
                }
                return true;
            case 16908321:
                int selStart2 = getSelectionStart();
                int selEnd2 = getSelectionEnd();
                int min2 = Math.max(0, Math.min(selStart2, selEnd2));
                int max2 = Math.max(0, Math.max(selStart2, selEnd2));
                ClipData copyData = ClipData.newPlainText(null, getTransformedText(min2, max2));
                if (setPrimaryClip(copyData)) {
                    stopTextActionMode();
                } else {
                    Toast.makeText(getContext(), com.android.internal.R.string.failed_to_copy_to_clipboard, 0).show();
                }
                return true;
            case 16908322:
                paste(true);
                return true;
            case 16908337:
                paste(false);
                return true;
            case 16908338:
                if (this.mEditor != null) {
                    this.mEditor.undo();
                }
                return true;
            case 16908339:
                if (this.mEditor != null) {
                    this.mEditor.redo();
                }
                return true;
            case 16908340:
                if (this.mEditor != null) {
                    this.mEditor.replace();
                }
                return true;
            case 16908341:
                shareSelectedText();
                return true;
            case 16908355:
                requestAutofill();
                stopTextActionMode();
                return true;
            case 16908910:
                InputMethodManager imm = (InputMethodManager) this.mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    Bundle sipBundle = new Bundle();
                    sipBundle.putString(EXTRA_KEY_BOARD, "clipboard");
                    imm.sendAppPrivateCommand(this, ACTION_SHOW_BOARD, sipBundle);
                    if (imm.showSoftInput(this, 0)) {
                        Log.d(LOG_TAG, "Clipboard is shown");
                        return true;
                    }
                }
                Log.d(LOG_TAG, "Clipboard is not shown");
                return false;
            case 16908993:
                deleteText_internal(min, max);
                return true;
            case 16909092:
                if (this.mEditor != null) {
                    this.mEditor.lambda$startActionModeInternal$0();
                    this.mEditor.mSelectionModifierCursorController.hide();
                }
                return true;
            case 16909128:
                break;
            case 16909292:
                ComponentName cn = new ComponentName("com.android.settings", "com.samsung.android.settings.display.SecProcessTextManageAppsFragment");
                if (!this.mContext.canStartActivityForResult()) {
                    resolveInfo = "";
                } else {
                    PackageManager packageManager = getContext().getPackageManager();
                    List<ResolveInfo> unfiltered = packageManager.queryIntentActivities(new Intent().setAction(Intent.ACTION_PROCESS_TEXT).setType("text/plain"), 0);
                    resolveInfo = unfiltered.toString();
                }
                getContext().startActivity(new Intent().setComponent(cn).putExtra("resolveInfo", resolveInfo));
                return true;
            case 16909592:
                if (ViewRune.SUPPORT_EAGLE_EYE) {
                    InputMethodManager Imm = (InputMethodManager) this.mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (Imm != null) {
                        Bundle sipBundle2 = new Bundle();
                        sipBundle2.putString(EXTRA_KEY_BOARD, EXTRA_VALUE_BOARD_EAGLE_EYE);
                        Imm.sendAppPrivateCommand(this, ACTION_SHOW_BOARD, sipBundle2);
                        if (Imm.showSoftInput(this, 0)) {
                            return true;
                        }
                    }
                    return false;
                }
                break;
            case 16909811:
                String selectedText = getSelectedText();
                this.mPrevSelectionStartForSSS = getSelectionStart();
                this.mPrevSelectionEndForSSS = getSelectionEnd();
                Intent intent = new Intent().setAction(ACTION_SSS_TRANSLATE).putExtra(Intent.EXTRA_TEXT, selectedText).putExtra("needsTranslatedTextResult", canSSSPaste());
                intent.setFlags(0);
                try {
                    startActivityForResult(intent, 101);
                } catch (ActivityNotFoundException ex) {
                    Log.e(LOG_TAG, "sssTranslate failed");
                    Log.e(LOG_TAG, "ActivityNotFoundException", ex);
                }
                return true;
            case 16910013:
                Intent send = new Intent(Intent.ACTION_WEB_SEARCH);
                String source = this.mTransformed.subSequence(min, max).toString();
                send.putExtra(SearchManager.EXTRA_NEW_SEARCH, true);
                send.putExtra("query", source);
                send.putExtra(Browser.EXTRA_APPLICATION_ID, getContext().getPackageName());
                try {
                    send.setFlags(268435456);
                    getContext().startActivity(send);
                } catch (ActivityNotFoundException ex2) {
                    Log.e(LOG_TAG, "WebSearch failed");
                    Log.e(LOG_TAG, "ActivityNotFoundException", ex2);
                }
                return true;
            case 16910028:
                InputMethodManager inputMethodManager = (InputMethodManager) this.mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager != null) {
                    inputMethodManager.sendAppPrivateCommand(this, SemInputMethodManagerUtils.ACTION_SHOW_TOOLKIT_HBD, null);
                }
                boolean isTextEditable = isTextEditable();
                if (!isTextEditable) {
                    String selectedText2 = getSelectedText();
                    this.mPrevSelectionStartForSSS = getSelectionStart();
                    this.mPrevSelectionEndForSSS = getSelectionEnd();
                    Intent intent2 = new Intent().setAction("com.samsung.android.intent.action.WritingToolkit").setData(Uri.parse("honeyboard://writing-toolkit")).putExtra("toolkitSubject", selectedText2).putExtra("isTextEditable", isTextEditable);
                    intent2.setFlags(0);
                    try {
                        startActivityForResult(intent2, 102);
                    } catch (ActivityNotFoundException ex3) {
                        Log.e(LOG_TAG, "ActivityNotFoundException", ex3);
                    }
                }
                this.mEditor.stopTextActionModeWithPreservingSelection();
                return true;
            default:
                return false;
        }
        InputMethodManager Imm2 = (InputMethodManager) this.mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (Imm2 != null) {
            Bundle sipBundle3 = new Bundle();
            sipBundle3.putString(EXTRA_KEY_BOARD, "translation");
            Imm2.sendAppPrivateCommand(this, ACTION_SHOW_BOARD, sipBundle3);
            if (Imm2.showSoftInput(this, 0)) {
                return true;
            }
        }
        return false;
    }

    CharSequence getTransformedText(int start, int end) {
        return removeSuggestionSpans(this.mTransformed.subSequence(start, end));
    }

    @Override // android.view.View
    public boolean performLongClick() {
        boolean handled = false;
        boolean performedHapticFeedback = false;
        if (this.mEditor != null) {
            this.mEditor.mIsBeingLongClicked = true;
        }
        if (super.performLongClick()) {
            handled = true;
            performedHapticFeedback = true;
        }
        if (this.mEditor != null) {
            handled |= this.mEditor.performLongClick(handled);
            this.mEditor.mIsBeingLongClicked = false;
            this.mEditor.mIsSelectedByLongClick = true;
        }
        if (handled) {
            if (!performedHapticFeedback) {
                performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(1));
            }
            if (this.mEditor != null) {
                this.mEditor.mDiscardNextActionUp = true;
            }
        } else {
            MetricsLogger.action(this.mContext, MetricsProto.MetricsEvent.TEXT_LONGPRESS, 0);
        }
        return handled;
    }

    @Override // android.view.View
    protected void onScrollChanged(int horiz, int vert, int oldHoriz, int oldVert) {
        super.onScrollChanged(horiz, vert, oldHoriz, oldVert);
        if (this.mEditor != null) {
            this.mEditor.onScrollChanged();
        }
    }

    public boolean isSuggestionsEnabled() {
        if (this.mEditor == null || (this.mEditor.mInputType & 15) != 1 || (this.mEditor.mInputType & 524288) > 0) {
            return false;
        }
        int variation = this.mEditor.mInputType & InputType.TYPE_MASK_VARIATION;
        return variation == 0 || variation == 48 || variation == 80 || variation == 64 || variation == 160;
    }

    public void setCustomSelectionActionModeCallback(ActionMode.Callback actionModeCallback) {
        createEditorIfNeeded();
        this.mEditor.mCustomSelectionActionModeCallback = actionModeCallback;
    }

    public ActionMode.Callback getCustomSelectionActionModeCallback() {
        if (this.mEditor == null) {
            return null;
        }
        return this.mEditor.mCustomSelectionActionModeCallback;
    }

    public void setCustomInsertionActionModeCallback(ActionMode.Callback actionModeCallback) {
        createEditorIfNeeded();
        this.mEditor.mCustomInsertionActionModeCallback = actionModeCallback;
    }

    public ActionMode.Callback getCustomInsertionActionModeCallback() {
        if (this.mEditor == null) {
            return null;
        }
        return this.mEditor.mCustomInsertionActionModeCallback;
    }

    public void setTextClassifier(TextClassifier textClassifier) {
        this.mTextClassifier = textClassifier;
    }

    public TextClassifier getTextClassifier() {
        if (this.mTextClassifier == null) {
            TextClassificationManager tcm = getTextClassificationManagerForUser();
            if (tcm != null) {
                return tcm.getTextClassifier();
            }
            return TextClassifier.NO_OP;
        }
        return this.mTextClassifier;
    }

    TextClassifier getTextClassificationSession() {
        String widgetType;
        if (this.mTextClassificationSession == null || this.mTextClassificationSession.isDestroyed()) {
            TextClassificationManager tcm = getTextClassificationManagerForUser();
            if (tcm != null) {
                if (isTextEditable()) {
                    widgetType = TextClassifier.WIDGET_TYPE_EDITTEXT;
                } else if (isTextSelectable()) {
                    widgetType = TextClassifier.WIDGET_TYPE_TEXTVIEW;
                } else {
                    widgetType = TextClassifier.WIDGET_TYPE_UNSELECTABLE_TEXTVIEW;
                }
                this.mTextClassificationContext = new TextClassificationContext.Builder(this.mContext.getPackageName(), widgetType).build();
                if (this.mTextClassifier != null) {
                    this.mTextClassificationSession = tcm.createTextClassificationSession(this.mTextClassificationContext, this.mTextClassifier);
                } else {
                    this.mTextClassificationSession = tcm.createTextClassificationSession(this.mTextClassificationContext);
                }
            } else {
                this.mTextClassificationSession = TextClassifier.NO_OP;
            }
        }
        return this.mTextClassificationSession;
    }

    TextClassificationContext getTextClassificationContext() {
        return this.mTextClassificationContext;
    }

    boolean usesNoOpTextClassifier() {
        return getTextClassifier() == TextClassifier.NO_OP;
    }

    public boolean requestActionMode(TextLinks.TextLinkSpan clickedSpan) {
        Preconditions.checkNotNull(clickedSpan);
        if (!(this.mText instanceof Spanned)) {
            return false;
        }
        int start = ((Spanned) this.mText).getSpanStart(clickedSpan);
        int end = ((Spanned) this.mText).getSpanEnd(clickedSpan);
        if (start < 0 || end > this.mText.length() || start >= end) {
            return false;
        }
        createEditorIfNeeded();
        this.mEditor.startLinkActionModeAsync(start, end);
        return true;
    }

    public boolean handleClick(TextLinks.TextLinkSpan clickedSpan) {
        Preconditions.checkNotNull(clickedSpan);
        if (this.mText instanceof Spanned) {
            Spanned spanned = (Spanned) this.mText;
            int start = spanned.getSpanStart(clickedSpan);
            int end = spanned.getSpanEnd(clickedSpan);
            if (start >= 0 && end <= this.mText.length() && start < end) {
                final TextClassification.Request request = new TextClassification.Request.Builder(this.mText, start, end).setDefaultLocales(getTextLocales()).build();
                Supplier<TextClassification> supplier = new Supplier() { // from class: android.widget.TextView$$ExternalSyntheticLambda1
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        TextClassification lambda$handleClick$5;
                        lambda$handleClick$5 = TextView.this.lambda$handleClick$5(request);
                        return lambda$handleClick$5;
                    }
                };
                Consumer<TextClassification> consumer = new Consumer() { // from class: android.widget.TextView$$ExternalSyntheticLambda2
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        TextView.lambda$handleClick$6((TextClassification) obj);
                    }
                };
                CompletableFuture.supplyAsync(supplier).completeOnTimeout(null, 1L, TimeUnit.SECONDS).thenAccept((Consumer) consumer);
                return true;
            }
            return false;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ TextClassification lambda$handleClick$5(TextClassification.Request request) {
        return getTextClassificationSession().classifyText(request);
    }

    static /* synthetic */ void lambda$handleClick$6(TextClassification classification) {
        if (classification == null) {
            Log.d(LOG_TAG, "Timeout while classifying text");
            return;
        }
        if (classification.getActions().isEmpty()) {
            Log.d(LOG_TAG, "No link action to perform");
            return;
        }
        try {
            classification.getActions().get(0).getActionIntent().send();
        } catch (PendingIntent.CanceledException e) {
            Log.e(LOG_TAG, "Error sending PendingIntent", e);
        }
    }

    protected void stopTextActionMode() {
        if (this.mEditor != null) {
            this.mEditor.lambda$startActionModeInternal$0();
        }
    }

    public void hideFloatingToolbar(int durationMs) {
        if (this.mEditor != null) {
            this.mEditor.hideFloatingToolbar(durationMs);
        }
    }

    boolean canUndo() {
        return this.mEditor != null && this.mEditor.canUndo();
    }

    boolean canRedo() {
        return this.mEditor != null && this.mEditor.canRedo();
    }

    boolean canCut() {
        return (hasPasswordTransformationMethod() || isKeyguardLocked() || this.mText.length() <= 0 || !hasSelection() || !(this.mText instanceof Editable) || this.mEditor == null || this.mEditor.mKeyListener == null) ? false : true;
    }

    boolean canCopy() {
        return (hasPasswordTransformationMethod() || isKeyguardLocked() || this.mText.length() <= 0 || !hasSelection() || this.mEditor == null) ? false : true;
    }

    boolean canReplace() {
        return !hasPasswordTransformationMethod() && this.mText.length() > 0 && (this.mText instanceof Editable) && this.mEditor != null && isSuggestionsEnabled() && this.mEditor.shouldOfferToShowSuggestions();
    }

    boolean canShare() {
        return !isFlipCoverClosed() && getContext().canStartActivityForResult() && isDeviceProvisioned() && getContext().getResources().getBoolean(com.android.internal.R.bool.config_textShareSupported) && canCopy() && isFinishSetupWizard() && (this.mActionModeFlags & 8192) == 8192;
    }

    boolean isDeviceProvisioned() {
        int i;
        if (this.mDeviceProvisionedState == 0) {
            if (Settings.Global.getInt(this.mContext.getContentResolver(), "device_provisioned", 0) != 0) {
                i = 2;
            } else {
                i = 1;
            }
            this.mDeviceProvisionedState = i;
        }
        return this.mDeviceProvisionedState == 2;
    }

    boolean canPaste() {
        return (this.mText instanceof Editable) && this.mEditor != null && this.mEditor.mKeyListener != null && getSelectionStart() >= 0 && getSelectionEnd() >= 0 && getClipboardManagerForUser().hasPrimaryClip();
    }

    boolean canPasteAsPlainText() {
        ClipDescription description;
        if (!canPaste() || (description = getClipboardManagerForUser().getPrimaryClipDescription()) == null || description == null) {
            return false;
        }
        boolean isPlainType = description.hasMimeType("text/plain");
        return (isPlainType && description.isStyledText()) || description.hasMimeType("text/html");
    }

    boolean canProcessText() {
        if (getId() == -1) {
            return false;
        }
        return canShare();
    }

    boolean canSelectAllText() {
        return isFocused() && canSelectText() && !hasPasswordTransformationMethod() && !(getSelectionStart() == 0 && getSelectionEnd() == this.mText.length());
    }

    boolean selectAllText() {
        if (this.mEditor != null) {
            hideFloatingToolbar(500);
        }
        int length = this.mText.length();
        Selection.setSelection(this.mSpannable, 0, length);
        return length > 0;
    }

    private void paste(boolean z) {
        ClipData primaryClip = getClipboardManagerForUser().getPrimaryClip();
        if (primaryClip == null) {
            return;
        }
        performReceiveContent(new ContentInfo.Builder(primaryClip, 1).setFlags(!z ? 1 : 0).build());
        sLastCutCopyOrTextChangedTime = 0L;
    }

    private void shareSelectedText() {
        Intent intent = makeChooserIntent(false);
        if (intent != null) {
            intent.setFlags(268435456);
            getContext().startActivity(intent);
            Selection.setSelection(this.mSpannable, getSelectionEnd());
        }
    }

    private boolean setPrimaryClip(ClipData clip) {
        ClipboardManager clipboard = getClipboardManagerForUser();
        try {
            clipboard.setPrimaryClip(clip);
            sLastCutCopyOrTextChangedTime = SystemClock.uptimeMillis();
            return true;
        } catch (Throwable th) {
            return false;
        }
    }

    public int getOffsetForPosition(float x, float y) {
        if (getLayout() == null) {
            return -1;
        }
        int line = getLineAtCoordinate(y);
        int offset = getOffsetAtCoordinate(line, x);
        return offset;
    }

    float convertToLocalHorizontalCoordinate(float x) {
        return Math.min((getWidth() - getTotalPaddingRight()) - 1, Math.max(0.0f, x - getTotalPaddingLeft())) + getScrollX();
    }

    public int getLineAtCoordinate(float y) {
        return getLayout().getLineForVertical((int) (Math.min((getHeight() - getTotalPaddingBottom()) - 1, Math.max(0.0f, y - getTotalPaddingTop())) + getScrollY()));
    }

    int getLineAtCoordinateUnclamped(float y) {
        return getLayout().getLineForVertical((int) ((y - getTotalPaddingTop()) + getScrollY()));
    }

    int getOffsetAtCoordinate(int line, float x) {
        int offset = getLayout().getOffsetForHorizontal(line, convertToLocalHorizontalCoordinate(x));
        return transformedToOriginal(offset, 1);
    }

    public int transformedToOriginal(int offset, int strategy) {
        if (getTransformationMethod() == null) {
            return offset;
        }
        if (this.mTransformed instanceof OffsetMapping) {
            OffsetMapping transformedText = (OffsetMapping) this.mTransformed;
            return transformedText.transformedToOriginal(offset, strategy);
        }
        return offset;
    }

    public int originalToTransformed(int offset, int strategy) {
        if (getTransformationMethod() == null) {
            return offset;
        }
        if (this.mTransformed instanceof OffsetMapping) {
            OffsetMapping transformedText = (OffsetMapping) this.mTransformed;
            return transformedText.originalToTransformed(offset, strategy);
        }
        return offset;
    }

    @Override // android.view.View
    public boolean onDragEvent(DragEvent event) {
        if (this.mEditor == null || !this.mEditor.hasInsertionController()) {
            return super.onDragEvent(event);
        }
        switch (event.getAction()) {
            case 2:
                if (this.mText instanceof Spannable) {
                    int offset = getOffsetForPosition(event.getX(), event.getY());
                    Selection.setSelection(this.mSpannable, offset);
                    break;
                }
                break;
            case 3:
                if (this.mEditor != null) {
                    this.mEditor.onDrop(event);
                    break;
                }
                break;
            case 5:
                requestFocus();
                break;
        }
        return true;
    }

    boolean isInBatchEditMode() {
        if (this.mEditor == null) {
            return false;
        }
        Editor.InputMethodState ims = this.mEditor.mInputMethodState;
        if (ims != null) {
            return ims.mBatchEditNesting > 0;
        }
        return this.mEditor.mInBatchEditControllers;
    }

    @Override // android.view.View
    public void onRtlPropertiesChanged(int layoutDirection) {
        super.onRtlPropertiesChanged(layoutDirection);
        TextDirectionHeuristic newTextDir = getTextDirectionHeuristic();
        if (this.mTextDir != newTextDir) {
            this.mTextDir = newTextDir;
            if (this.mLayout != null) {
                checkForRelayout();
            }
        }
    }

    public TextDirectionHeuristic getTextDirectionHeuristic() {
        if (hasPasswordTransformationMethod()) {
            return TextDirectionHeuristics.LTR;
        }
        if (this.mEditor != null && (this.mEditor.mInputType & 15) == 3) {
            DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance(getTextLocale());
            String zero = symbols.getDigitStrings()[0];
            int firstCodepoint = zero.codePointAt(0);
            byte digitDirection = Character.getDirectionality(firstCodepoint);
            if (digitDirection == 1 || digitDirection == 2) {
                return TextDirectionHeuristics.RTL;
            }
            return TextDirectionHeuristics.LTR;
        }
        boolean defaultIsRtl = getLayoutDirection() == 1;
        switch (getTextDirection()) {
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            default:
                if (!defaultIsRtl) {
                    break;
                } else {
                    break;
                }
        }
        return TextDirectionHeuristics.LTR;
    }

    @Override // android.view.View
    public void onResolveDrawables(int layoutDirection) {
        if (this.mLastLayoutDirection == layoutDirection) {
            return;
        }
        this.mLastLayoutDirection = layoutDirection;
        if (this.mDrawables != null && this.mDrawables.resolveWithLayoutDirection(layoutDirection)) {
            prepareDrawableForDisplay(this.mDrawables.mShowing[0]);
            prepareDrawableForDisplay(this.mDrawables.mShowing[2]);
            applyCompoundDrawableTint();
        }
    }

    private void prepareDrawableForDisplay(Drawable dr) {
        if (dr == null) {
            return;
        }
        dr.setLayoutDirection(getLayoutDirection());
        if (dr.isStateful()) {
            dr.setState(getDrawableState());
            dr.jumpToCurrentState();
        }
    }

    @Override // android.view.View
    protected void resetResolvedDrawables() {
        super.resetResolvedDrawables();
        this.mLastLayoutDirection = -1;
    }

    protected void viewClicked(InputMethodManager imm) {
        if (imm != null) {
            imm.viewClicked(this);
        }
    }

    protected void deleteText_internal(int start, int end) {
        ((Editable) this.mText).delete(start, end);
    }

    protected void replaceText_internal(int start, int end, CharSequence text) {
        ((Editable) this.mText).replace(start, end, text);
    }

    protected void setSpan_internal(Object span, int start, int end, int flags) {
        ((Editable) this.mText).setSpan(span, start, end, flags);
    }

    protected void setCursorPosition_internal(int start, int end) {
        Selection.setSelection((Editable) this.mText, start, end);
    }

    private void createEditorIfNeeded() {
        if (this.mEditor == null) {
            this.mEditor = new Editor(this);
        }
    }

    @Override // android.view.View
    public CharSequence getIterableTextForAccessibility() {
        return this.mText;
    }

    private void ensureIterableTextForAccessibilitySelectable() {
        if (!(this.mText instanceof Spannable)) {
            setText(this.mText, BufferType.SPANNABLE);
            if (getLayout() == null) {
                assumeLayout();
            }
        }
    }

    @Override // android.view.View
    public AccessibilityIterators.TextSegmentIterator getIteratorForGranularity(int granularity) {
        switch (granularity) {
            case 4:
                Spannable text = (Spannable) getIterableTextForAccessibility();
                if (!TextUtils.isEmpty(text) && getLayout() != null) {
                    AccessibilityIterators.LineTextSegmentIterator iterator = AccessibilityIterators.LineTextSegmentIterator.getInstance();
                    iterator.initialize(text, getLayout());
                    return iterator;
                }
                break;
            case 16:
                if (!TextUtils.isEmpty((Spannable) getIterableTextForAccessibility()) && getLayout() != null) {
                    AccessibilityIterators.PageTextSegmentIterator iterator2 = AccessibilityIterators.PageTextSegmentIterator.getInstance();
                    iterator2.initialize(this);
                    return iterator2;
                }
                break;
        }
        return super.getIteratorForGranularity(granularity);
    }

    @Override // android.view.View
    public int getAccessibilitySelectionStart() {
        return getSelectionStart();
    }

    @Override // android.view.View
    public boolean isAccessibilitySelectionExtendable() {
        return true;
    }

    @Override // android.view.View
    public void prepareForExtendedAccessibilitySelection() {
        requestFocusOnNonEditableSelectableText();
    }

    @Override // android.view.View
    public int getAccessibilitySelectionEnd() {
        return getSelectionEnd();
    }

    @Override // android.view.View
    public void setAccessibilitySelection(int start, int end) {
        if (getAccessibilitySelectionStart() == start && getAccessibilitySelectionEnd() == end) {
            return;
        }
        CharSequence text = getIterableTextForAccessibility();
        if (Math.min(start, end) >= 0 && Math.max(start, end) <= text.length()) {
            Selection.setSelection((Spannable) text, start, end);
        } else {
            Selection.removeSelection((Spannable) text);
        }
        if (this.mEditor != null) {
            this.mEditor.hideCursorAndSpanControllers();
            this.mEditor.lambda$startActionModeInternal$0();
        }
    }

    @Override // android.view.View
    protected void encodeProperties(ViewHierarchyEncoder stream) {
        super.encodeProperties(stream);
        TextUtils.TruncateAt ellipsize = getEllipsize();
        stream.addProperty("text:ellipsize", ellipsize == null ? null : ellipsize.name());
        stream.addProperty("text:textSize", getTextSize());
        stream.addProperty("text:scaledTextSize", getScaledTextSize());
        stream.addProperty("text:typefaceStyle", getTypefaceStyle());
        stream.addProperty("text:selectionStart", getSelectionStart());
        stream.addProperty("text:selectionEnd", getSelectionEnd());
        stream.addProperty("text:curTextColor", this.mCurTextColor);
        stream.addUserProperty("text:text", this.mText != null ? this.mText.toString() : null);
        stream.addProperty("text:gravity", this.mGravity);
    }

    public static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() { // from class: android.widget.TextView.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        ParcelableParcel editorState;
        CharSequence error;
        boolean frozenWithFocus;
        int selEnd;
        int selStart;
        CharSequence text;

        SavedState(Parcelable superState) {
            super(superState);
            this.selStart = -1;
            this.selEnd = -1;
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.selStart);
            parcel.writeInt(this.selEnd);
            parcel.writeInt(this.frozenWithFocus ? 1 : 0);
            TextUtils.writeToParcel(this.text, parcel, i);
            if (this.error == null) {
                parcel.writeInt(0);
            } else {
                parcel.writeInt(1);
                TextUtils.writeToParcel(this.error, parcel, i);
            }
            if (this.editorState == null) {
                parcel.writeInt(0);
            } else {
                parcel.writeInt(1);
                this.editorState.writeToParcel(parcel, i);
            }
        }

        public String toString() {
            String str = "TextView.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " start=" + this.selStart + " end=" + this.selEnd;
            if (this.text != null) {
                str = str + " text=" + ((Object) this.text);
            }
            return str + "}";
        }

        private SavedState(Parcel in) {
            super(in);
            this.selStart = -1;
            this.selEnd = -1;
            this.selStart = in.readInt();
            this.selEnd = in.readInt();
            this.frozenWithFocus = in.readInt() != 0;
            this.text = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(in);
            if (in.readInt() != 0) {
                this.error = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(in);
            }
            if (in.readInt() != 0) {
                this.editorState = ParcelableParcel.CREATOR.createFromParcel(in);
            }
        }
    }

    private static class CharWrapper implements CharSequence, GetChars, GraphicsOperations {
        private char[] mChars;
        private int mLength;
        private int mStart;

        CharWrapper(char[] chars, int start, int len) {
            this.mChars = chars;
            this.mStart = start;
            this.mLength = len;
        }

        void set(char[] chars, int start, int len) {
            this.mChars = chars;
            this.mStart = start;
            this.mLength = len;
        }

        @Override // java.lang.CharSequence
        public int length() {
            return this.mLength;
        }

        @Override // java.lang.CharSequence
        public char charAt(int off) {
            return this.mChars[this.mStart + off];
        }

        @Override // java.lang.CharSequence
        public String toString() {
            return new String(this.mChars, this.mStart, this.mLength);
        }

        @Override // java.lang.CharSequence
        public CharSequence subSequence(int start, int end) {
            if (start < 0 || end < 0 || start > this.mLength || end > this.mLength) {
                throw new IndexOutOfBoundsException(start + ", " + end);
            }
            return new String(this.mChars, this.mStart + start, end - start);
        }

        @Override // android.text.GetChars
        public void getChars(int start, int end, char[] buf, int off) {
            if (start < 0 || end < 0 || start > this.mLength || end > this.mLength) {
                throw new IndexOutOfBoundsException(start + ", " + end);
            }
            System.arraycopy(this.mChars, this.mStart + start, buf, off, end - start);
        }

        @Override // android.text.GraphicsOperations
        public void drawText(BaseCanvas c, int start, int end, float x, float y, Paint p) {
            c.drawText(this.mChars, start + this.mStart, end - start, x, y, p);
        }

        @Override // android.text.GraphicsOperations
        public void drawTextRun(BaseCanvas c, int start, int end, int contextStart, int contextEnd, float x, float y, boolean isRtl, Paint p) {
            int count = end - start;
            int contextCount = contextEnd - contextStart;
            c.drawTextRun(this.mChars, start + this.mStart, count, contextStart + this.mStart, contextCount, x, y, isRtl, p);
        }

        @Override // android.text.GraphicsOperations
        public float measureText(int start, int end, Paint p) {
            return p.measureText(this.mChars, this.mStart + start, end - start);
        }

        @Override // android.text.GraphicsOperations
        public int getTextWidths(int start, int end, float[] widths, Paint p) {
            return p.getTextWidths(this.mChars, this.mStart + start, end - start, widths);
        }

        @Override // android.text.GraphicsOperations
        public float getTextRunAdvances(int start, int end, int contextStart, int contextEnd, boolean isRtl, float[] advances, int advancesIndex, Paint p) {
            int count = end - start;
            int contextCount = contextEnd - contextStart;
            return p.getTextRunAdvances(this.mChars, start + this.mStart, count, contextStart + this.mStart, contextCount, isRtl, advances, advancesIndex);
        }

        @Override // android.text.GraphicsOperations
        public int getTextRunCursor(int contextStart, int contextEnd, boolean isRtl, int offset, int cursorOpt, Paint p) {
            int contextCount = contextEnd - contextStart;
            return p.getTextRunCursor(this.mChars, contextStart + this.mStart, contextCount, isRtl, offset + this.mStart, cursorOpt);
        }
    }

    private static final class Marquee {
        private static final int MARQUEE_DELAY = 1200;
        private static final float MARQUEE_DELTA_MAX = 0.07f;
        private static final int MARQUEE_DP_PER_SECOND = 30;
        private static final byte MARQUEE_RUNNING = 2;
        private static final byte MARQUEE_STARTING = 1;
        private static final byte MARQUEE_STOPPED = 0;
        private final Choreographer mChoreographer;
        private float mFadeStop;
        private float mGhostOffset;
        private float mGhostStart;
        private long mLastAnimationMs;
        private float mMaxFadeScroll;
        private float mMaxScroll;
        private final float mPixelsPerMs;
        private int mRepeatLimit;
        private float mScroll;
        private final WeakReference<TextView> mView;
        private byte mStatus = 0;
        private Choreographer.FrameCallback mTickCallback = new Choreographer.FrameCallback() { // from class: android.widget.TextView.Marquee.1
            @Override // android.view.Choreographer.FrameCallback
            public void doFrame(long frameTimeNanos) {
                Marquee.this.tick();
            }
        };
        private Choreographer.FrameCallback mStartCallback = new Choreographer.FrameCallback() { // from class: android.widget.TextView.Marquee.2
            @Override // android.view.Choreographer.FrameCallback
            public void doFrame(long frameTimeNanos) {
                Marquee.this.mStatus = (byte) 2;
                Marquee.this.mLastAnimationMs = Marquee.this.mChoreographer.getFrameTime();
                Marquee.this.tick();
            }
        };
        private Choreographer.FrameCallback mRestartCallback = new Choreographer.FrameCallback() { // from class: android.widget.TextView.Marquee.3
            @Override // android.view.Choreographer.FrameCallback
            public void doFrame(long frameTimeNanos) {
                if (Marquee.this.mStatus == 2) {
                    if (Marquee.this.mRepeatLimit >= 0) {
                        Marquee marquee = Marquee.this;
                        marquee.mRepeatLimit--;
                    }
                    Marquee.this.start(Marquee.this.mRepeatLimit);
                }
            }
        };

        Marquee(TextView v) {
            float density = v.getContext().getResources().getDisplayMetrics().density;
            this.mPixelsPerMs = (30.0f * density) / 1000.0f;
            this.mView = new WeakReference<>(v);
            this.mChoreographer = Choreographer.getInstance();
        }

        void tick() {
            if (this.mStatus != 2) {
                return;
            }
            this.mChoreographer.removeFrameCallback(this.mTickCallback);
            TextView textView = this.mView.get();
            if (textView != null && textView.isAggregatedVisible()) {
                if (textView.isFocused() || textView.isSelected()) {
                    long currentMs = this.mChoreographer.getFrameTime();
                    long deltaMs = currentMs - this.mLastAnimationMs;
                    this.mLastAnimationMs = currentMs;
                    float deltaPx = deltaMs * this.mPixelsPerMs;
                    this.mScroll += deltaPx;
                    if (this.mScroll > this.mMaxScroll) {
                        this.mScroll = this.mMaxScroll;
                        this.mChoreographer.postFrameCallbackDelayed(this.mRestartCallback, 1200L);
                    } else {
                        this.mChoreographer.postFrameCallback(this.mTickCallback);
                    }
                    textView.invalidate();
                }
            }
        }

        void stop() {
            this.mStatus = (byte) 0;
            this.mChoreographer.removeFrameCallback(this.mStartCallback);
            this.mChoreographer.removeFrameCallback(this.mRestartCallback);
            this.mChoreographer.removeFrameCallback(this.mTickCallback);
            resetScroll();
        }

        private void resetScroll() {
            this.mScroll = 0.0f;
            TextView textView = this.mView.get();
            if (textView != null) {
                textView.invalidate();
            }
        }

        void start(int repeatLimit) {
            if (repeatLimit == 0) {
                stop();
                return;
            }
            this.mRepeatLimit = repeatLimit;
            TextView textView = this.mView.get();
            if (textView != null && textView.mLayout != null) {
                this.mStatus = (byte) 1;
                this.mScroll = 0.0f;
                int textWidth = (textView.getWidth() - textView.getCompoundPaddingLeft()) - textView.getCompoundPaddingRight();
                float lineWidth = textView.mLayout.getLineWidth(0);
                float gap = textWidth / 3.0f;
                this.mGhostStart = (lineWidth - textWidth) + gap;
                this.mMaxScroll = this.mGhostStart + textWidth;
                this.mGhostOffset = lineWidth + gap;
                this.mFadeStop = (textWidth / 6.0f) + lineWidth;
                this.mMaxFadeScroll = this.mGhostStart + lineWidth + lineWidth;
                textView.invalidate();
                this.mChoreographer.postFrameCallback(this.mStartCallback);
            }
        }

        float getGhostOffset() {
            return this.mGhostOffset;
        }

        float getScroll() {
            return this.mScroll;
        }

        float getMaxFadeScroll() {
            return this.mMaxFadeScroll;
        }

        boolean shouldDrawLeftFade() {
            return this.mScroll <= this.mFadeStop;
        }

        boolean shouldDrawGhost() {
            return this.mStatus == 2 && this.mScroll > this.mGhostStart;
        }

        boolean isRunning() {
            return this.mStatus == 2;
        }

        boolean isStopped() {
            return this.mStatus == 0;
        }
    }

    private class ChangeWatcher implements TextWatcher, SpanWatcher {
        private CharSequence mBeforeText;

        private ChangeWatcher() {
        }

        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence buffer, int start, int before, int after) {
            if (AccessibilityManager.getInstance(TextView.this.mContext).isEnabled() && TextView.this.mTransformed != null) {
                this.mBeforeText = TextView.this.mTransformed.toString();
            }
            TextView.this.sendBeforeTextChanged(buffer, start, before, after);
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence buffer, int start, int before, int after) {
            TextView.this.handleTextChanged(buffer, start, before, after);
            if (TextView.this.isVisibleToAccessibility()) {
                TextView.this.sendAccessibilityEventTypeViewTextChanged(this.mBeforeText, start, before, after);
                this.mBeforeText = null;
            }
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable buffer) {
            TextView.this.sendAfterTextChanged(buffer);
            if (MetaKeyKeyListener.getMetaState(buffer, 2048) != 0) {
                MetaKeyKeyListener.stopSelecting(TextView.this, buffer);
            }
        }

        @Override // android.text.SpanWatcher
        public void onSpanChanged(Spannable buf, Object what, int s, int e, int st, int en) {
            TextView.this.spanChange(buf, what, s, st, e, en);
        }

        @Override // android.text.SpanWatcher
        public void onSpanAdded(Spannable buf, Object what, int s, int e) {
            TextView.this.spanChange(buf, what, -1, s, -1, e);
        }

        @Override // android.text.SpanWatcher
        public void onSpanRemoved(Spannable buf, Object what, int s, int e) {
            TextView.this.spanChange(buf, what, s, -1, e, -1);
        }
    }

    @Override // android.view.View
    public void onInputConnectionOpenedInternal(InputConnection ic, EditorInfo editorInfo, Handler handler) {
        if (this.mEditor != null) {
            this.mEditor.getDefaultOnReceiveContentListener().setInputConnectionInfo(this, ic, editorInfo);
        }
    }

    @Override // android.view.View
    public void onInputConnectionClosedInternal() {
        if (this.mEditor != null) {
            this.mEditor.getDefaultOnReceiveContentListener().clearInputConnectionInfo();
        }
    }

    @Override // android.view.View
    public ContentInfo onReceiveContent(ContentInfo payload) {
        if (this.mEditor != null) {
            return this.mEditor.getDefaultOnReceiveContentListener().onReceiveContent(this, payload);
        }
        return payload;
    }

    private static void logCursor(String location, String msgFormat, Object... msgArgs) {
        if (msgFormat != null) {
            Log.d(LOG_TAG, location + ": " + String.format(msgFormat, msgArgs));
        } else {
            Log.d(LOG_TAG, location);
        }
    }

    @Override // android.view.View
    public void onCreateViewTranslationRequest(int[] supportedFormats, Consumer<ViewTranslationRequest> requestsCollector) {
        if (supportedFormats == null || supportedFormats.length == 0) {
            if (UiTranslationController.DEBUG) {
                Log.w(LOG_TAG, "Do not provide the support translation formats.");
                return;
            }
            return;
        }
        ViewTranslationRequest.Builder requestBuilder = new ViewTranslationRequest.Builder(getAutofillId());
        boolean isPassword = true;
        if (ArrayUtils.contains(supportedFormats, 1)) {
            if (this.mText == null || this.mText.length() == 0) {
                if (UiTranslationController.DEBUG) {
                    Log.w(LOG_TAG, "Cannot create translation request for the empty text.");
                    return;
                }
                return;
            }
            if (!isAnyPasswordInputType() && !hasPasswordTransformationMethod()) {
                isPassword = false;
            }
            if (isTextEditable() || isPassword) {
                Log.w(LOG_TAG, "Cannot create translation request. editable = " + isTextEditable() + ", isPassword = " + isPassword);
                return;
            } else {
                requestBuilder.setValue(ViewTranslationRequest.ID_TEXT, TranslationRequestValue.forText(this.mText));
                if (!TextUtils.isEmpty(getContentDescription())) {
                    requestBuilder.setValue(ViewTranslationRequest.ID_CONTENT_DESCRIPTION, TranslationRequestValue.forText(getContentDescription()));
                }
            }
        }
        requestsCollector.accept(requestBuilder.build());
    }

    @Override // android.view.View
    public SemHoverPopupWindow semGetHoverPopup(boolean createIfNotExist) {
        if (!isHoveringUIEnabled()) {
            return null;
        }
        if (this.mHoverPopup == null) {
            if (!createIfNotExist) {
                return null;
            }
            this.mHoverPopup = new MoreInfoHPW(this, this.mHoverPopupType);
        }
        if (isDesktopMode()) {
            setSemHoverPopupWindowSettings(3);
        } else {
            setSemHoverPopupWindowSettings(2);
        }
        return this.mHoverPopup;
    }

    @Override // android.view.View
    public SemHoverPopupWindow semGetHoverPopup(int tooltype) {
        if (!isHoveringUIEnabled()) {
            return null;
        }
        if (this.mHoverPopup == null) {
            switch (tooltype) {
                case 1:
                    switch (this.mHoverPopupType) {
                        case 2:
                        case 3:
                            this.mHoverPopup = new MoreInfoHPW(this, this.mHoverPopupType);
                            break;
                    }
                case 2:
                case 3:
                    this.mHoverPopup = new MoreInfoHPW(this, this.mHoverPopupType);
                    break;
            }
        }
        setSemHoverPopupWindowSettings(tooltype);
        this.mHoverPopupToolTypeByApp = tooltype;
        if (tooltype == 1 && this.mHoverPopupType == 1 && this.mHoverPopup != null) {
            this.mHoverPopup.dismiss();
            this.mHoverPopup = null;
        }
        return this.mHoverPopup;
    }

    private class MoreInfoHPW extends SemHoverPopupWindow {
        private static final boolean DEBUG = true;
        private static final int HOVER_DETECT_TIME_MS = 300;
        private static final int ID_INFO_VIEW = 117510676;
        private static final String TAG = "MoreInfoHPW";
        private int mInitialMaxLine;
        private int mLastOrientation;
        TextView mParentTextView;

        public MoreInfoHPW(View parentView, int type) {
            super(parentView, type);
            this.mLastOrientation = 0;
            this.mInitialMaxLine = 7;
            this.mParentTextView = null;
            if (this.mParentView instanceof TextView) {
                this.mParentTextView = (TextView) this.mParentView;
            } else {
                Log.e(TAG, "Parent view is not a TextView");
                this.mParentTextView = new TextView(TextView.this.mContext);
            }
        }

        @Override // com.samsung.android.widget.SemHoverPopupWindow
        protected void setInstanceByType(int type) {
            super.setInstanceByType(type);
            if (type == 2) {
                this.mPopupGravity = 12849;
                this.mAnimationStyle = com.android.internal.R.style.Animation_HoverPopup;
                this.mHoverDetectTimeMS = 300;
            }
        }

        @Override // com.samsung.android.widget.SemHoverPopupWindow
        public boolean isHoverPopupPossible() {
            if (this.mPopupType == 2) {
                if (TextView.this.isAnyPasswordInputType()) {
                    return false;
                }
                return !TextUtils.isEmpty(this.mContentText) || (this.mParentTextView.getLineCount() == 1 && this.mParentTextView.canMarquee()) || (this.mParentTextView.mLayout != null && this.mParentTextView.mLayout.getEllipsisCount(0) > 0);
            }
            return super.isHoverPopupPossible();
        }

        @Override // com.samsung.android.widget.SemHoverPopupWindow
        protected void makeDefaultContentView() {
            LayoutInflater inflater;
            TextView textView;
            int orientation = TextView.this.mContext.getResources().getConfiguration().orientation;
            if (this.mContentView == null || this.mContentView.getId() != ID_INFO_VIEW || orientation != this.mLastOrientation) {
                TypedValue outValue = new TypedValue();
                TextView.this.mContext.getTheme().resolveAttribute(16843945, outValue, false);
                if (!TextView.this.mIsThemeDeviceDefault || outValue.data == 0) {
                    inflater = LayoutInflater.from(TextView.this.mContext);
                } else {
                    Context context = new ContextThemeWrapper(TextView.this.mContext, outValue.data);
                    inflater = LayoutInflater.from(context);
                }
                textView = (TextView) inflater.inflate(com.android.internal.R.layout.hover_text_popup, (ViewGroup) null);
                textView.semSetHoverPopupType(0);
                textView.setId(ID_INFO_VIEW);
                this.mInitialMaxLine = textView.getMaxLines();
                this.mLastOrientation = orientation;
            } else {
                textView = (TextView) this.mContentView;
            }
            CharSequence text = !TextUtils.isEmpty(this.mContentText) ? this.mContentText : this.mParentTextView.getText();
            if (!TextUtils.isEmpty(text)) {
                textView.lambda$setTextAsync$0(text.toString());
                textView.setEllipsize(TextUtils.TruncateAt.END);
            } else {
                textView = null;
            }
            this.mContentView = textView;
        }
    }

    private void initTextStrikeThroughAnim() {
        if (this.mStrikeThroughPaint == null) {
            this.mStrikeThroughPaint = new Paint();
            this.mStrikeThroughPaint.setColor(this.mCurTextColor);
            this.mStrikeThroughPaint.setStrokeWidth(Math.max(getTextSize() / 18.0f, 1.0f));
        }
        if (this.mDrawTextStrikeAnimator == null) {
            this.mDrawTextStrikeAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
            this.mDrawTextStrikeAnimator.setDuration(400L);
            this.mDrawTextStrikeAnimator.addListener(new AnimatorListenerAdapter() { // from class: android.widget.TextView.4
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animation) {
                    TextView.this.mLineIsDrawed = true;
                }
            });
            this.mDrawTextStrikeAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: android.widget.TextView.5
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator anim) {
                    TextView.this.mDrawStrikeAnimationValue = anim.getAnimatedFraction();
                    TextView.this.invalidate();
                }
            });
        }
    }

    private void drawTextStrikethrough(Canvas canvas, int textColor) {
        float currentLineLength;
        int leftPadding = getTotalPaddingLeft();
        int topPadding = getTotalPaddingTop();
        int lineCount = getLineCount();
        Layout textLayout = getLayout();
        if (this.mStrikeThroughPaint != null) {
            this.mStrikeThroughPaint.setColor(textColor);
        }
        if (this.mTextStrikeThroughEnabled && this.mDrawStrikeAnimationValue > 0.0f && this.mStrikeThroughPaint != null && lineCount > 0) {
            float[] accumLineWidths = new float[lineCount];
            for (int i = 0; i < lineCount; i++) {
                accumLineWidths[i] = textLayout.getLineWidth(i);
                if (i > 0) {
                    accumLineWidths[i] = accumLineWidths[i] + accumLineWidths[i - 1];
                }
            }
            int i2 = accumLineWidths.length;
            float totalLineWidth = accumLineWidths[i2 - 1];
            float strikethroughCurrLength = totalLineWidth * this.mDrawStrikeAnimationValue;
            int i3 = 0;
            while (i3 < lineCount) {
                float lowerBound = i3 == 0 ? 0.0f : accumLineWidths[i3 - 1];
                float upperBound = accumLineWidths[i3];
                if (strikethroughCurrLength <= lowerBound) {
                    currentLineLength = 0.0f;
                } else if (strikethroughCurrLength > lowerBound && strikethroughCurrLength <= upperBound) {
                    float currentLineLength2 = strikethroughCurrLength - lowerBound;
                    currentLineLength = currentLineLength2;
                } else if (strikethroughCurrLength <= upperBound) {
                    currentLineLength = 0.0f;
                } else {
                    float currentLineLength3 = upperBound - lowerBound;
                    currentLineLength = currentLineLength3;
                }
                float lineTop = textLayout.getLineTop(i3) + topPadding;
                float baseLine = textLayout.getLineBaseline(i3) + topPadding;
                if (i3 == 0) {
                    float topPadding2 = lineTop - textLayout.getTopPadding();
                }
                float lineTop2 = getTextSize();
                float lineY = baseLine + (lineTop2 * (-0.2857143f));
                boolean isRTL = getLayoutDirection() == 1;
                if (isRTL) {
                    canvas.save();
                    canvas.translate(getWidth(), 0.0f);
                    canvas.scale(-1.0f, 1.0f);
                }
                int i4 = i3;
                canvas.drawLine(leftPadding, lineY, leftPadding + currentLineLength, lineY, this.mStrikeThroughPaint);
                if (isRTL) {
                    canvas.restore();
                }
                i3 = i4 + 1;
            }
        }
    }

    public void semSetAnimatedStrike(boolean enabled) {
        initTextStrikeThroughAnim();
        this.mTextStrikeThroughEnabled = enabled;
        if (this.mDrawTextStrikeAnimator != null) {
            if (this.mTextStrikeThroughEnabled) {
                this.mDrawTextStrikeAnimator.start();
            } else {
                invalidate();
            }
        }
    }

    public Paint.MyanmarEncoding getMyanmarEncoding() {
        return this.mTextPaint.getMyanmarEncoding();
    }

    public void setMyanmarEncoding(Paint.MyanmarEncoding myanmarEncoding) {
        if (myanmarEncoding != this.mTextPaint.getMyanmarEncoding()) {
            this.mTextPaint.setMyanmarEncoding(myanmarEncoding);
            if (this.mLayout != null) {
                nullLayouts();
                requestLayout();
                invalidate();
            }
        }
    }

    @RemotableViewMethod
    public void setMyanmarEncoding(int myanmarEncoding) {
        switch (myanmarEncoding) {
            case 0:
                this.mTextPaint.setMyanmarEncoding(Paint.MyanmarEncoding.ME_UNICODE);
                break;
            case 1:
                this.mTextPaint.setMyanmarEncoding(Paint.MyanmarEncoding.ME_ZAWGYI);
                break;
            default:
                this.mTextPaint.setMyanmarEncoding(Paint.MyanmarEncoding.ME_AUTO);
                break;
        }
    }

    public void hideCursorControllers() {
        if (this.mEditor != null) {
            this.mEditor.hideCursorAndSpanControllers();
            this.mEditor.lambda$startActionModeInternal$0();
        }
    }

    public boolean semIsEllipsis() {
        int width = ((this.mRight - this.mLeft) - getCompoundPaddingLeft()) - getCompoundPaddingRight();
        if (getVisibility() != 0 || width <= 0 || getLineCount() != 1 || getLayout() == null) {
            return false;
        }
        return ((int) getLayout().getLineWidth(0)) > width || getLayout().getEllipsisCount(0) > 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0050  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void semSetSelection(android.text.Spannable r19, int r20, int r21) {
        /*
            Method dump skipped, instructions count: 254
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.TextView.semSetSelection(android.text.Spannable, int, int):void");
    }

    public static void semSetSelection(Spannable text, int index) {
        semSetSelection(text, index, index);
    }

    private TextDirectionHeuristic getTextDirectionHeuristic(boolean isHint) {
        if (!isHint) {
            return getTextDirectionHeuristic();
        }
        boolean defaultIsRtl = getLayoutDirection() == 1;
        switch (getTextDirection()) {
            case 2:
                return TextDirectionHeuristics.ANYRTL_LTR;
            case 3:
                return TextDirectionHeuristics.LTR;
            case 4:
                return TextDirectionHeuristics.RTL;
            case 5:
                return TextDirectionHeuristics.LOCALE;
            case 6:
                return TextDirectionHeuristics.FIRSTSTRONG_LTR;
            case 7:
                return TextDirectionHeuristics.FIRSTSTRONG_RTL;
            default:
                return defaultIsRtl ? TextDirectionHeuristics.FIRSTSTRONG_RTL : TextDirectionHeuristics.FIRSTSTRONG_LTR;
        }
    }

    public boolean getKeycodeDpadCenterStatus() {
        return this.mKeycodeDpadCenterStatus;
    }

    public void semSetActionModeMenuItemEnabled(int menuId, boolean value) {
        if (value) {
            if ((this.mActionModeFlags & menuId) != menuId) {
                this.mActionModeFlags |= menuId;
            }
        } else if ((this.mActionModeFlags & menuId) == menuId) {
            this.mActionModeFlags ^= menuId;
        }
    }

    private void hidden_semSetActionModeMenuItemEnabled(int menuId, boolean value) {
        semSetActionModeMenuItemEnabled(menuId, value);
    }

    private static final int hidden_SEM_AUTOFILL_ID() {
        return 131072;
    }

    boolean canDelete() {
        return !hasPasswordTransformationMethod() && this.mText.length() > 0 && hasSelection();
    }

    boolean canWebSearch() {
        return canCopy() && isFinishSetupWizard() && (this.mActionModeFlags & 16384) == 16384;
    }

    boolean canClipboard() {
        if (isFlipCoverClosed() || !isFinishSetupWizard()) {
            return false;
        }
        if ((this.mActionModeFlags & 4096) != 4096 || isKeyguardLocked()) {
            Log.d(LOG_TAG, "cannot show clipboard, caused by mEnableClipboard = " + ((this.mActionModeFlags & 4096) == 4096) + ", isKeyguardLocked() : " + isKeyguardLocked());
            return false;
        }
        InputMethodManager imm = (InputMethodManager) this.mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return false;
        }
        boolean isAccessoryKeyboard = imm.semIsAccessoryKeyboard();
        int showImeWithHardKeyboard = Settings.Secure.getInt(this.mContext.getContentResolver(), Settings.Secure.SHOW_IME_WITH_HARD_KEYBOARD, 0);
        if (isAccessoryKeyboard && showImeWithHardKeyboard != 1) {
            Log.d(LOG_TAG, "isAccessoryKeyboard is true, showImeWithHardKeyboard : " + showImeWithHardKeyboard);
            return false;
        }
        InputMethodManager imm2 = (InputMethodManager) this.mContext.getSystemService(InputMethodManager.class);
        if (this.mEditor != null && !this.mEditor.editorShowSoftInput() && imm2 != null && !imm2.isInputMethodShown()) {
            Log.d(LOG_TAG, "Input method is not shown.");
            return false;
        }
        String defaultIME = Settings.Secure.getStringForUser(this.mContext.getContentResolver(), Settings.Secure.DEFAULT_INPUT_METHOD, -3);
        if (!SemInputMethodManagerUtils.METHOD_ID_HONEYBOARD.equals(defaultIME)) {
            Log.d(LOG_TAG, "defaultIME is not com.samsung.android.honeyboard/.service.HoneyBoardService");
            return false;
        }
        if (!getSemClipboardManager().isEnabled()) {
            Log.d(LOG_TAG, "getSemClipboardManager().isEnabled() is false");
            return false;
        }
        if (!(this.mText instanceof Editable)) {
            Log.d(LOG_TAG, "mText is not Editable");
            return false;
        }
        if (getKeyListener() != null) {
            return true;
        }
        Log.d(LOG_TAG, "getKeyListener() is null");
        return false;
    }

    private boolean isFlipCoverClosed() {
        return false;
    }

    boolean canScanText() {
        InputMethodManager imm;
        if (isFlipCoverClosed() || !ViewRune.SUPPORT_EAGLE_EYE) {
            return false;
        }
        if ((this.mEditor != null && !this.mEditor.mShowSoftInputOnFocus) || isKeyguardLocked() || !isFinishSetupWizard() || Settings.System.getInt(this.mContext.getContentResolver(), Settings.System.SEM_EMERGENCY_MODE, 0) == 1 || (imm = (InputMethodManager) this.mContext.getSystemService(Context.INPUT_METHOD_SERVICE)) == null) {
            return false;
        }
        boolean isAccessoryKeyboard = imm.semIsAccessoryKeyboard();
        int showImeWithHardKeyboard = Settings.Secure.getInt(this.mContext.getContentResolver(), Settings.Secure.SHOW_IME_WITH_HARD_KEYBOARD, 0);
        if (isAccessoryKeyboard && showImeWithHardKeyboard != 1) {
            return false;
        }
        String defaultIME = Settings.Secure.getStringForUser(this.mContext.getContentResolver(), Settings.Secure.DEFAULT_INPUT_METHOD, -3);
        return Build.VERSION.SEM_PLATFORM_INT >= 130100 && SemInputMethodManagerUtils.METHOD_ID_HONEYBOARD.equals(defaultIME) && !hasSelection();
    }

    boolean canHBDTranslate() {
        boolean isPwdTypeWithTypeNull = false;
        int variation = getInputType() & 4095;
        if (isFlipCoverClosed()) {
            return false;
        }
        if ((variation & 15) == 0) {
            isPwdTypeWithTypeNull = variation == 128 || variation == 224 || variation == 144;
        }
        if ((this.mEditor != null && !this.mEditor.mShowSoftInputOnFocus) || isAnyPasswordInputType() || isPwdTypeWithTypeNull || isKeyguardLocked() || !isFinishSetupWizard()) {
            return false;
        }
        String defaultIME = Settings.Secure.getStringForUser(this.mContext.getContentResolver(), Settings.Secure.DEFAULT_INPUT_METHOD, -3);
        return SemInputMethodManagerUtils.METHOD_ID_HONEYBOARD.equals(defaultIME) && hasSelection() && isTextEditable();
    }

    boolean canAssist() {
        return (this.mActionModeFlags & 65536) == 65536 && isFinishSetupWizard() && !isKeyguardLocked() && !isFlipCoverClosed();
    }

    private boolean isFinishSetupWizard() {
        if (Settings.Secure.getIntForUser(this.mContext.getContentResolver(), Settings.Secure.USER_SETUP_COMPLETE, 0, -3) == 1) {
            return true;
        }
        Log.w(LOG_TAG, "SetupWizard is not finished.");
        return false;
    }

    public boolean isThemeDeviceDefault() {
        return this.mIsThemeDeviceDefault;
    }

    protected boolean isDesktopMode() {
        ViewRootImpl viewRootImpl = getViewRootImpl();
        return viewRootImpl != null && viewRootImpl.isDesktopMode();
    }

    protected boolean isDisableWritingToolkit() {
        int inputType;
        int inputTypeMask;
        int inputTypeFlagMask;
        if (this.mDisableWritingToolkitMenu || isAnyPasswordInputType() || hasPasswordTransformationMethod()) {
            return true;
        }
        int imeOptions = getImeOptions();
        int imeOptionsActionMask = imeOptions & 255;
        if (imeOptionsActionMask == 3 || (inputTypeMask = (inputType = getInputType()) & 4095) == 17 || inputTypeMask == 33 || inputTypeMask == 49 || inputTypeMask == 209 || inputTypeMask == 4 || inputTypeMask == 20 || inputTypeMask == 36 || inputTypeMask == 2 || inputTypeMask == 3 || (inputTypeFlagMask = 16773135 & inputType) == 4098 || inputTypeFlagMask == 8194) {
            return true;
        }
        String privateImeOptions = getPrivateImeOptions();
        if (privateImeOptions != null) {
            List<String> list = Arrays.asList(privateImeOptions.split(NavigationBarInflaterView.GRAVITY_SEPARATOR));
            return list.contains("inputType=month_edittext") || list.contains("inputType=ipAddress");
        }
        return false;
    }

    protected SemClipboardManager getSemClipboardManager() {
        if (this.mSemClipboardManager == null) {
            this.mSemClipboardManager = (SemClipboardManager) getContext().getSystemService(Context.SEM_CLIPBOARD_SERVICE);
        }
        return this.mSemClipboardManager;
    }

    boolean canClipboardForContextMenu() {
        InputMethodManager imm = (InputMethodManager) this.mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return false;
        }
        if (!imm.isActive(this)) {
            Log.d(LOG_TAG, "this view is not active for input methoad");
            return false;
        }
        if (isDexEnabled() && !imm.isInputMethodShown() && !imm.getDexSettingsValue("keyboard_dex", "0")) {
            Log.d(LOG_TAG, "dexSetting is true, but show on-screen keyboard is false");
            return false;
        }
        if (this.mEditor != null && !this.mEditor.hasInsertionController()) {
            Log.d(LOG_TAG, "this view don't support insertion handles");
            return false;
        }
        return canClipboard();
    }

    private boolean isDexEnabled() {
        int desktopMode = getResources().getConfiguration().semDesktopModeEnabled;
        return desktopMode == 1;
    }

    private class TouchMonitorListener implements ViewRootImpl.MotionEventMonitor.OnTouchListener {
        private static final int globalTimeForTouch = 1000;
        private long mPressTime;
        private float mStartX;
        private float mStartY;

        private TouchMonitorListener() {
            this.mStartX = 0.0f;
            this.mStartY = 0.0f;
            this.mPressTime = 0L;
        }

        @Override // android.view.ViewRootImpl.MotionEventMonitor.OnTouchListener
        public void onTouch(MotionEvent event) {
            if (!ViewRune.WIDGET_PEN_SUPPORTED) {
            }
            int action = event.getAction();
            float rawX = event.getRawX();
            float rawY = event.getRawY();
            switch (action) {
                case 0:
                    long elipseTime = SystemClock.uptimeMillis();
                    this.mPressTime = elipseTime;
                    this.mStartX = rawX;
                    this.mStartY = rawY;
                    break;
                case 1:
                    long elipseTime2 = SystemClock.uptimeMillis() - this.mPressTime;
                    float moveX = Math.abs(rawX - this.mStartX);
                    float moveY = Math.abs(rawY - this.mStartY);
                    boolean flag = (moveX * moveX) + (moveY * moveY) <= TextView.this.TOUCH_DELTA * TextView.this.TOUCH_DELTA;
                    if (!MultiSelection.getIsMultiSelectingText() && elipseTime2 < 1000 && flag) {
                        if (!TextView.this.getPenSelectionController().isPenSelectionArea(TextView.this.getContext(), TextView.this.getRootView(), (int) rawX, (int) rawY)) {
                            TextView.this.clearAllMultiSelection();
                            break;
                        }
                    } else if (!TextView.this.isValidMultiSelection()) {
                        TextView.this.clearMultiSelection();
                        break;
                    }
                    break;
            }
        }
    }

    @Override // android.view.View
    public boolean performClick() {
        boolean result = super.performClick();
        if (ViewRune.WIDGET_PEN_SUPPORTED && result && this.mhasMultiSelection) {
            clearMultiSelection();
        }
        return result;
    }

    public static final boolean semIsTextSelectionProgressing() {
        return MultiSelection.getIsMultiSelectingText();
    }

    private static final boolean hidden_semIsTextSelectionProgressing() {
        return semIsTextSelectionProgressing();
    }

    public static final boolean semIsTextViewHovered() {
        return MultiSelection.isTextViewHovered();
    }

    private static final boolean hidden_semIsTextViewHovered() {
        return semIsTextViewHovered();
    }

    public void semSetMultiSelectionEnabled(boolean flag) {
        if (!ViewRune.WIDGET_PEN_SUPPORTED) {
            return;
        }
        this.mEnableMultiSelection = flag;
        if (!flag) {
            removeForStylusPenEvent();
        } else {
            registerForStylusPenEvent();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Rect getSpannedTextRect(Rect targetRect) {
        int line;
        int offset;
        int textXPos;
        int top;
        int bottom;
        CharSequence text = getText();
        if (!(text instanceof Spanned) || this.mLayout == null) {
            return null;
        }
        Spanned spanned = (Spanned) text;
        ReplacementSpan[] spans = (ReplacementSpan[]) spanned.getSpans(0, text.length(), ReplacementSpan.class);
        if (spans.length <= 0) {
            return null;
        }
        Point startPos = getScreenPointOfView(this);
        int tx = targetRect.left - startPos.x;
        int ty = targetRect.top - startPos.y;
        int line2 = getLineAtCoordinate(ty);
        int maxLine = getMaxLines();
        if (maxLine > 0 && maxLine <= line2) {
            line = maxLine - 1;
        } else {
            line = line2;
        }
        int offset2 = getOffsetAtCoordinate(line, tx);
        int textXPos2 = (int) this.mLayout.getPrimaryHorizontal(offset2);
        if (tx >= textXPos2) {
            offset = offset2;
            textXPos = textXPos2;
        } else {
            if (offset2 <= 0) {
                return null;
            }
            int offset3 = offset2 - 1;
            offset = offset3;
            textXPos = (int) this.mLayout.getPrimaryHorizontal(offset3);
        }
        ReplacementSpan[] replacementSpanArr = (ReplacementSpan[]) spanned.getSpans(offset, offset, ReplacementSpan.class);
        if (replacementSpanArr.length <= 0) {
            return null;
        }
        int start = spanned.getSpanStart(replacementSpanArr[0]);
        int end = spanned.getSpanEnd(replacementSpanArr[0]);
        if (line == getLineCount() - 1) {
            int top2 = this.mLayout.getLineTop(line) - (((int) getLineSpacingExtra()) / 2);
            top = top2;
            bottom = this.mLayout.getLineBottom(line) + (((int) getLineSpacingExtra()) / 2);
        } else {
            int top3 = this.mLayout.getLineTop(line);
            top = top3;
            bottom = this.mLayout.getLineBottom(line);
        }
        Rect rect = new Rect(0, 0, 0, 0);
        rect.right = replacementSpanArr[0].getSize(this.mTextPaint, spanned, start, end, null);
        rect.bottom = bottom - top;
        rect.offset(textXPos + startPos.x, top + startPos.y);
        return rect;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Point getScreenPointOfView(View view) {
        Point screenPointOfView = new Point();
        int[] screenOffsetOfView = new int[2];
        view.getLocationOnScreen(screenOffsetOfView);
        screenPointOfView.x = screenOffsetOfView[0];
        screenPointOfView.y = screenOffsetOfView[1];
        return screenPointOfView;
    }

    private boolean isLinkPreviewEnabled(int toolType) {
        switch (toolType) {
            case 1:
            case 3:
                return false;
            case 2:
                boolean ret = isLinkPreviewSettingsEnabled();
                return ret;
            default:
                return false;
        }
    }

    private boolean isLinkPreviewSettingsEnabled() {
        boolean isSPenHoveringOn = Settings.System.getIntForUser(this.mContext.getContentResolver(), Settings.System.SEM_PEN_HOVERING, 0, -3) == 1;
        return isSPenHoveringOn && Settings.System.getIntForUser(this.mContext.getContentResolver(), Settings.System.PEN_HOVERING_LINK_PREVIEW, 0, -3) == 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean canTextMultiSelection() {
        return ViewRune.WIDGET_PEN_SUPPORTED && this.mEnableMultiSelection && isCoverOpened() && isFinishSetupWizard() && !isCarMode() && !isKeyguardLocked() && !isDisabledStylusPenEvent() && !isSubWindow();
    }

    private boolean isCoverOpened() {
        if (ViewRune.WIDGET_PEN_SUPPORTED) {
            try {
                ICoverManager svc = ICoverManager.Stub.asInterface(ServiceManager.getService(UnionConstants.SERVICE_COVER));
                if (svc != null) {
                    return svc.getCoverState().getSwitchState();
                }
            } catch (Exception e) {
                Log.w(LOG_TAG, "isCoverOpened() : RemoteException!!!!");
                return true;
            }
        }
        return true;
    }

    private boolean isCarMode() {
        if (Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "car_mode_on", 0, -3) != 1) {
            return false;
        }
        Log.w(LOG_TAG, "TextView does not support text selection on Carmode.");
        return true;
    }

    public boolean isKeyguardLocked() {
        if (this.mContext == null) {
            Log.d(LOG_TAG, "isKeyguardLocked. context is null");
            return false;
        }
        KeyguardManager keyGuard = (KeyguardManager) this.mContext.getSystemService(Context.KEYGUARD_SERVICE);
        if (keyGuard == null) {
            Log.d(LOG_TAG, "keyGuard Service is null");
            return false;
        }
        boolean isLocked = keyGuard.isKeyguardLocked();
        if (isLocked) {
            Log.d(LOG_TAG, "Keyguard is Locked!");
        }
        return isLocked;
    }

    private boolean isSubWindow() {
        ViewGroup.LayoutParams params = getRootView().getLayoutParams();
        if (params instanceof WindowManager.LayoutParams) {
            WindowManager.LayoutParams windowParams = (WindowManager.LayoutParams) params;
            if (windowParams.type >= 1000 && windowParams.type <= 1999) {
                return true;
            }
            return false;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean checkPosInView(int x, int y, int overplus) {
        if (!isVisibleToUser()) {
            return false;
        }
        Rect rect = new Rect();
        getGlobalVisibleRect(rect, null);
        Point startPos = getScreenPointOfView(getRootView());
        rect.offset(startPos.x, startPos.y);
        rect.inset(-overplus, -overplus);
        return x >= rect.left && x <= rect.right && y >= rect.top && y <= rect.bottom;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean checkPosOnText(int x, int y, int overplus) {
        int textEndX;
        Layout layout = getLayout();
        if (layout == null) {
            return false;
        }
        int[] screenOffsetOfView = new int[2];
        getLocationOnScreen(screenOffsetOfView);
        int posX = x - screenOffsetOfView[0];
        int posY = y - screenOffsetOfView[1];
        int line = getLineAtCoordinate(posY);
        int lineStart = layout.getLineStart(line);
        int lineTop = layout.getLineTop(line) + getTotalPaddingTop();
        int lineBtm = layout.getLineBottom(line) + getTotalPaddingTop();
        int lineWidth = (int) layout.getLineWidth(line);
        int textStartX = (((int) layout.getPrimaryHorizontal(lineStart)) - getScrollX()) + getTotalPaddingLeft();
        if (layout.getParagraphDirection(line) == -1) {
            textEndX = textStartX;
            textStartX = textEndX - lineWidth;
        } else {
            textEndX = textStartX + lineWidth;
        }
        if (posX < textStartX - overplus || textEndX + overplus < posX || posY < lineTop || posY > lineBtm) {
            return false;
        }
        return true;
    }

    public boolean checkValidMultiSelectionForPreDraw() {
        if (!ViewRune.WIDGET_PEN_SUPPORTED || !this.mhasMultiSelection || !isVisibleToUser() || getLayout() == null) {
            return false;
        }
        View parent = this;
        View rootView = getRootView();
        do {
            parent = (View) parent.getParent();
            if (parent == null) {
                return false;
            }
            if (parent == rootView) {
                return true;
            }
        } while (parent instanceof View);
        return false;
    }

    public boolean isValidMultiSelection() {
        CharSequence text;
        Layout layout;
        if (!ViewRune.WIDGET_PEN_SUPPORTED || !this.mhasMultiSelection || !isVisibleToUser() || (text = getTextForMultiSelection()) == null || (layout = getLayout()) == null) {
            return false;
        }
        Rect tvRect = new Rect();
        getGlobalVisibleRect(tvRect, null);
        Point startPos = getScreenPointOfView(getRootView());
        tvRect.offset(startPos.x, startPos.y);
        int selStart = MultiSelection.getSelectionStart((Spannable) text);
        int selEnd = MultiSelection.getSelectionEnd((Spannable) text);
        if (selStart >= selEnd) {
            selStart = selEnd;
            selEnd = selStart;
        }
        int sLine = layout.getLineForOffset(selStart);
        int eLine = layout.getLineForOffset(selEnd);
        int topPadding = getTotalPaddingTop();
        int leftPadding = getTotalPaddingLeft();
        int sx = (((int) layout.getPrimaryHorizontal(selStart)) + leftPadding) - getScrollX();
        int ex = (((int) layout.getPrimaryHorizontal(selEnd)) + leftPadding) - getScrollX();
        if (sx > ex) {
            sx = ex;
            ex = sx;
        }
        int tmp = layout.getLineTop(sLine);
        int lineTop = (tmp + topPadding) - getScrollY();
        int baseLine = (layout.getLineBaseline(eLine) + topPadding) - getScrollY();
        Point startPos2 = getScreenPointOfView(this);
        Rect srcRect = new Rect(sx, lineTop, ex, baseLine);
        srcRect.offset(startPos2.x, startPos2.y);
        if (!srcRect.intersect(tvRect)) {
            return false;
        }
        View view = getPenSelectionController().findTargetTextView(getContext(), getRootView(), srcRect);
        if (view != this) {
            return false;
        }
        return true;
    }

    @Override // android.view.View
    public int semExtractSmartClipData(SemSmartClipCroppedArea croppedArea, SemSmartClipDataElement resultElement) {
        SmartClipDataElementImpl elementImpl = (SmartClipDataElementImpl) resultElement;
        if (hasPasswordTransformationMethod()) {
            Log.d(LOG_TAG, "Cannot get text of Password field");
            return 1;
        }
        if (elementImpl.getExtractionMode() == 0) {
            Rect cropRect = croppedArea.getRect();
            CharSequence charSequence = getTextForRectSelection(cropRect);
            if (charSequence == null) {
                charSequence = "";
            }
            resultElement.addTag(new SemSmartClipMetaTag(SemSmartClipMetaTagType.PLAIN_TEXT, charSequence.toString()));
        } else if (elementImpl.getExtractionMode() == 3) {
            Rect cropRect2 = croppedArea.getRect();
            Rect wordRect = new Rect(0, 0, 0, 0);
            CharSequence charSequence2 = getTextForSingleWord(cropRect2, wordRect);
            if (charSequence2 == null) {
                charSequence2 = "";
            }
            resultElement.addTag(new SemSmartClipMetaTag(SemSmartClipMetaTagType.PLAIN_TEXT, charSequence2.toString()));
            resultElement.setMetaAreaRect(wordRect);
        } else {
            super.semExtractSmartClipData(croppedArea, resultElement);
        }
        return 1;
    }

    private CharSequence getTextForRectSelection(Rect selectedRect) {
        CharSequence text;
        int endLine;
        int endOffset;
        CharSequence text2 = getTextForMultiSelection();
        if (text2 != null) {
            text = text2;
        } else {
            text = getText();
        }
        if (!TextUtils.isEmpty(text) && this.mLayout != null) {
            Rect tvRect = new Rect();
            getGlobalVisibleRect(tvRect, null);
            Point startPos = getScreenPointOfView(getRootView());
            tvRect.offset(startPos.x, startPos.y);
            Point startPos2 = getScreenPointOfView(this);
            int topPadding = getTotalPaddingTop();
            int lineCount = this.mLayout.getLineCount();
            int textTop = ((this.mLayout.getLineTop(0) + startPos2.y) + topPadding) - getScrollY();
            int textBtm = ((this.mLayout.getLineBottom(lineCount - 1) + startPos2.y) + topPadding) - getScrollY();
            tvRect.top = Math.max(textTop, tvRect.top);
            tvRect.bottom = Math.min(textBtm, tvRect.bottom);
            if (!Rect.intersects(tvRect, selectedRect)) {
                return null;
            }
            int startX = selectedRect.left - startPos2.x;
            int startY = selectedRect.top - startPos2.y;
            int endX = selectedRect.right - startPos2.x;
            int endY = selectedRect.bottom - startPos2.y;
            int startLine = getLineAtCoordinate(startY);
            int startOffset = getOffsetAtCoordinate(startLine, startX);
            int endLine2 = getLineAtCoordinate(endY);
            int endOffset2 = getOffsetAtCoordinate(endLine2, endX);
            if (startOffset < 0 || endOffset2 < 0) {
                return null;
            }
            if (startOffset == endOffset2) {
                return null;
            }
            if (startOffset > endOffset2) {
                endLine = startOffset;
                endOffset = endOffset2;
            } else {
                endLine = endOffset2;
                endOffset = startOffset;
            }
            try {
                CharSequence retText = text.subSequence(endOffset, endLine);
                return retText;
            } catch (IndexOutOfBoundsException ex) {
                Log.e(LOG_TAG, "IndexOutOfBoundsException" + ex);
                Log.e(LOG_TAG, "getTextForMultiSelection() = " + ((Object) getTextForMultiSelection()));
                Log.e(LOG_TAG, "getText() = " + ((Object) getText()));
                Log.e(LOG_TAG, "mLayout.getText() = " + ((Object) this.mLayout.getText()));
                return null;
            }
        }
        return null;
    }

    private CharSequence getTextForSingleWord(Rect selectedRect, Rect wordRect) {
        int selectionStart;
        CharSequence text = getTextForMultiSelection();
        if (text == null) {
            text = getText();
        }
        Layout layout = getLayout();
        if (TextUtils.isEmpty(text) || layout == null || !checkPosOnText(selectedRect.left, selectedRect.top, 0)) {
            return null;
        }
        Point startPos = getScreenPointOfView(this);
        int startX = selectedRect.left - startPos.x;
        int startY = selectedRect.top - startPos.y;
        int offset = getOffsetForPosition(startX, startY);
        int len = text.length();
        if (offset >= 0 && offset <= len) {
            if (this.mWordIteratorForMultiSelection == null) {
                this.mWordIteratorForMultiSelection = new WordIterator(getTextServicesLocale());
            }
            this.mWordIteratorForMultiSelection.setCharSequence(text, offset, offset);
            int selectionStart2 = this.mWordIteratorForMultiSelection.getBeginning(offset);
            int selectionEnd = this.mWordIteratorForMultiSelection.getEnd(offset);
            int line = getLineAtCoordinate(startY);
            int sLine = layout.getLineForOffset(selectionStart2);
            int eLine = layout.getLineForOffset(selectionEnd);
            if (line == sLine) {
                selectionStart = selectionStart2;
            } else {
                selectionStart = layout.getLineStart(line);
            }
            if (line != eLine) {
                selectionEnd = layout.getLineEnd(line);
            }
            if (selectionStart == -1 || selectionEnd == -1) {
                return null;
            }
            if (selectionStart != selectionEnd && selectionStart >= 0 && selectionEnd >= 0) {
                if (selectionStart > selectionEnd) {
                    Log.e(LOG_TAG, "AirDic : start > end !! start = " + selectionStart + ", end = " + selectionEnd);
                    Log.e(LOG_TAG, "AirDic : text = " + ((Object) text));
                    Log.e(LOG_TAG, "AirDic : line = " + line + ", sLine = " + sLine + ", eLine = " + eLine);
                    return null;
                }
                CharSequence singleWord = text.subSequence(selectionStart, selectionEnd);
                int top = layout.getLineTop(line);
                int bottom = layout.getLineBottom(line);
                wordRect.setEmpty();
                layout.getSelectionRect(line, selectionStart, selectionEnd, top, bottom, wordRect);
                int offsetX = (startPos.x + getTotalPaddingLeft()) - getScrollX();
                int offsetY = (startPos.y + getTotalPaddingTop()) - getScrollY();
                wordRect.offset(offsetX, offsetY);
                return singleWord;
            }
            return null;
        }
        return null;
    }

    public CharSequence getTextForMultiSelection() {
        if (!ViewRune.WIDGET_PEN_SUPPORTED) {
            return null;
        }
        if (this.mUseDisplayText) {
            return this.mDisplayText;
        }
        if (this.mTransformed instanceof Spannable) {
            return this.mTransformed;
        }
        if (this instanceof EditText) {
            return this.mText;
        }
        return null;
    }

    public boolean getVisibleTextRange(int[] range) {
        Layout layout = getLayout();
        if (layout == null || range == null || range.length < 2) {
            return false;
        }
        CharSequence text = layout.getText();
        if (TextUtils.isEmpty(text)) {
            return false;
        }
        if (this.mEllipsize == TextUtils.TruncateAt.START) {
            int start = layout.getEllipsisStart(0);
            int count = layout.getEllipsisCount(0);
            if (count > 0) {
                range[0] = (start + count) - 1;
                range[1] = text.length();
            } else {
                range[0] = 0;
                range[1] = text.length();
            }
        } else if (this.mEllipsize == TextUtils.TruncateAt.MIDDLE) {
            range[0] = 0;
            range[1] = text.length();
        } else if (this.mEllipsize == TextUtils.TruncateAt.SEM_KEYWORD) {
            int start2 = layout.getEllipsisStart(0);
            int count2 = layout.getEllipsisCount(0);
            range[0] = 0;
            range[1] = text.length();
            if (start2 == 0) {
                if (count2 > 0) {
                    range[0] = (start2 + count2) - 1;
                    range[1] = text.length();
                }
            } else if (count2 > 0) {
                range[0] = 0;
                range[1] = start2 + 1;
            }
        } else {
            int line = layout.getLineCount() - 1;
            if (line < 0) {
                return false;
            }
            int start3 = layout.getEllipsisStart(line);
            if (layout.getEllipsisCount(line) > 0) {
                range[0] = 0;
                range[1] = layout.getLineStart(line) + start3 + 1;
                if (range[1] >= text.length()) {
                    range[1] = text.length() - 1;
                }
                CharSequence disText = getTextForMultiSelection();
                if (disText != null) {
                    char startChar = disText.charAt(range[1]);
                    if (Character.isLowSurrogate(startChar)) {
                        range[1] = range[1] + 1;
                    }
                }
            } else {
                range[0] = 0;
                range[1] = text.length();
            }
        }
        return true;
    }

    public boolean hasMultiSelection() {
        if (!ViewRune.WIDGET_PEN_SUPPORTED) {
            return false;
        }
        return this.mhasMultiSelection;
    }

    public boolean clearMultiSelection() {
        if (!ViewRune.WIDGET_PEN_SUPPORTED) {
            return true;
        }
        this.mWordIteratorForMultiSelection = null;
        if (this.mhasMultiSelection) {
            CharSequence text = getTextForMultiSelection();
            if (text != null) {
                MultiSelection.clearMultiSelection((Spannable) text);
            }
            hideMultiSelectPopupWindow();
            removeForTouchMonitorListener();
            mTargetViewId = -1;
            this.mhasMultiSelection = false;
            invalidate();
        }
        return true;
    }

    public CharSequence getMultiSelectionText() {
        if (!ViewRune.WIDGET_PEN_SUPPORTED) {
            return null;
        }
        CharSequence selectedText = null;
        CharSequence text = getTextForMultiSelection();
        if (text == null || !this.mhasMultiSelection) {
            return null;
        }
        int[] range = new int[2];
        boolean flag = getVisibleTextRange(range);
        int[] multiSelStart = MultiSelection.getMultiSelectionStart((Spannable) text);
        int[] multiSelEnd = MultiSelection.getMultiSelectionEnd((Spannable) text);
        int multiSelCount = MultiSelection.getMultiSelectionCount((Spannable) text);
        if (ViewRune.WIDGET_MULTIPLE_PEN_TEXT_SUPPORTED) {
            Arrays.sort(multiSelStart);
            Arrays.sort(multiSelEnd);
        }
        for (int i = 0; i < multiSelCount; i++) {
            if (multiSelStart[i] <= multiSelEnd[i]) {
                if (flag) {
                    if (multiSelStart[i] == range[0]) {
                        multiSelStart[i] = 0;
                    }
                    if (multiSelEnd[i] == range[1]) {
                        multiSelEnd[i] = text.length();
                    }
                }
                if (selectedText == null) {
                    selectedText = new SpannableStringBuilder(text.subSequence(multiSelStart[i], multiSelEnd[i]));
                } else if (ViewRune.WIDGET_MULTIPLE_PEN_TEXT_SUPPORTED) {
                    ((Editable) selectedText).append((CharSequence) ("\n" + ((Object) text.subSequence(multiSelStart[i], multiSelEnd[i]))));
                } else {
                    ((Editable) selectedText).append(text.subSequence(multiSelStart[i], multiSelEnd[i]));
                }
            }
        }
        return selectedText;
    }

    public boolean isMultiSelectionLinkArea(int x, int y) {
        CharSequence text;
        if (!ViewRune.WIDGET_PEN_SUPPORTED || !this.mhasMultiSelection || this.mLayout == null || !checkPosInView(x, y, 0) || (text = getTextForMultiSelection()) == null) {
            return false;
        }
        Point startPos = getScreenPointOfView(this);
        int posX = x - startPos.x;
        int posY = y - startPos.y;
        int line = getLineAtCoordinate(posY);
        int offset = getOffsetAtCoordinate(line, posX);
        int[] multiSelStart = MultiSelection.getMultiSelectionStart((Spannable) text);
        int[] multiSelEnd = MultiSelection.getMultiSelectionEnd((Spannable) text);
        int multiSelCount = MultiSelection.getMultiSelectionCount((Spannable) text);
        for (int i = 0; i < multiSelCount; i++) {
            if (multiSelStart[i] <= offset && offset <= multiSelEnd[i]) {
                return true;
            }
        }
        return false;
    }

    public boolean clearAllMultiSelection() {
        if (!ViewRune.WIDGET_PEN_SUPPORTED) {
            return true;
        }
        removeForTouchMonitorListener();
        return getPenSelectionController().clearAllPenSelection(getContext(), getRootView());
    }

    public void registerForTouchMonitorListener() {
        ViewRootImpl viewRootImpl;
        if (!ViewRune.WIDGET_PEN_SUPPORTED || (viewRootImpl = getViewRootImpl()) == null) {
            return;
        }
        if (mMotionEventMonitorListener != null) {
            removeForTouchMonitorListener();
        }
        ViewRootImpl.MotionEventMonitor monitor = viewRootImpl.getMotionEventMonitor();
        mMotionEventMonitorListener = new TouchMonitorListener();
        monitor.registerMotionEventMonitor(mMotionEventMonitorListener);
    }

    public void removeForTouchMonitorListener() {
        ViewRootImpl viewRootImpl;
        if (!ViewRune.WIDGET_PEN_SUPPORTED || (viewRootImpl = getViewRootImpl()) == null || mMotionEventMonitorListener == null) {
            return;
        }
        ViewRootImpl.MotionEventMonitor monitor = viewRootImpl.getMotionEventMonitor();
        monitor.unregisterMotionEventMonitor(mMotionEventMonitorListener);
        mMotionEventMonitorListener = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean selectCurrentWordForMultiSelection(int minOffset, int maxOffset) {
        CharSequence text;
        int selectionStart;
        int selectionEnd;
        if (!ViewRune.WIDGET_PEN_SUPPORTED || (text = getTextForMultiSelection()) == null) {
            return false;
        }
        if (hasPasswordTransformationMethod()) {
            MultiSelection.selectAll((Spannable) text);
            return true;
        }
        int inputType = getInputType();
        int klass = inputType & 15;
        int variation = inputType & InputType.TYPE_MASK_VARIATION;
        if (klass == 2 || klass == 3 || klass == 4 || variation == 16 || variation == 32 || variation == 208 || variation == 176) {
            MultiSelection.selectAll((Spannable) text);
            return true;
        }
        URLSpan[] urlSpans = (URLSpan[]) ((Spanned) text).getSpans(minOffset, maxOffset, URLSpan.class);
        if (urlSpans.length >= 1) {
            URLSpan urlSpan = urlSpans[0];
            selectionStart = ((Spanned) text).getSpanStart(urlSpan);
            selectionEnd = ((Spanned) text).getSpanEnd(urlSpan);
        } else {
            if (this.mWordIteratorForMultiSelection == null) {
                this.mWordIteratorForMultiSelection = new WordIterator(getTextServicesLocale());
            }
            this.mWordIteratorForMultiSelection.setCharSequence(text, minOffset, maxOffset);
            selectionStart = this.mWordIteratorForMultiSelection.getBeginning(minOffset);
            selectionEnd = this.mWordIteratorForMultiSelection.getEnd(maxOffset);
            if (selectionStart == -1 || selectionEnd == -1 || selectionStart == selectionEnd) {
                return false;
            }
        }
        if (selectionStart < 0 || selectionEnd < 0 || selectionStart >= selectionEnd) {
            return false;
        }
        this.mIsTouchDown = false;
        MultiSelection.setSelection((Spannable) text, selectionStart, selectionEnd);
        return true;
    }

    private void registerForStylusPenEvent() {
        if (!ViewRune.WIDGET_PEN_SUPPORTED || this.mStylusEventListener != null) {
            return;
        }
        if (!this.mEnableMultiSelection) {
            removeForStylusPenEvent();
        } else {
            if (isDisabledStylusPenEvent()) {
                return;
            }
            ViewTreeObserver observer = getViewTreeObserver();
            this.mStylusEventListener = new StylusEventListener(this);
            observer.semAddOnStylusButtonEventListener(this.mStylusEventListener);
        }
    }

    private void removeForStylusPenEvent() {
        if (!ViewRune.WIDGET_PEN_SUPPORTED || this.mStylusEventListener == null) {
            return;
        }
        ViewTreeObserver observer = getViewTreeObserver();
        observer.semRemoveOnStylusButtonEventListener(this.mStylusEventListener);
        this.mStylusEventListener = null;
    }

    private boolean isDisabledStylusPenEvent() {
        if (!ViewRune.WIDGET_PEN_SUPPORTED) {
            return true;
        }
        String packName = this.mContext.getBasePackageName();
        if (packName != null) {
            return packName.equals("flipboard.boxer.app") || packName.equals(AsPackageName.SYSTEMUI) || packName.equals("com.android.keyguard") || packName.equals("android");
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showMultiSelectPopupWindow() {
        if (!ViewRune.WIDGET_PEN_SUPPORTED || this.mLayout == null || this.mMultiSelectPopupWindow == null) {
            return;
        }
        this.mMultiSelectPopupWindow.changeCurrentSelectedView(this);
        this.mMultiSelectPopupWindow.showMultiSelectPopupWindow();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideMultiSelectPopupWindow() {
        if (!ViewRune.WIDGET_PEN_SUPPORTED) {
            return;
        }
        this.mMultiSelectPopupWindow.hideMultiSelectPopupWindow();
    }

    private boolean isCharLTR() {
        int[] directionality = {-1, -1};
        if (this.mText.length() > 0) {
            directionality[0] = Character.getDirectionality(this.mText.charAt(0));
            directionality[1] = Character.getDirectionality(this.mText.charAt(this.mText.length() - 1));
        }
        for (int i = 0; i < 2; i++) {
            if (directionality[i] == 0 || directionality[i] == 14 || directionality[i] == 15) {
                return true;
            }
        }
        return false;
    }

    private boolean isCharRTL() {
        int[] directionality = {-1, -1};
        if (this.mText.length() > 0) {
            directionality[0] = Character.getDirectionality(this.mText.charAt(0));
            directionality[1] = Character.getDirectionality(this.mText.charAt(this.mText.length() - 1));
        }
        for (int i = 0; i < 2; i++) {
            if (directionality[i] == 1 || directionality[i] == 2 || directionality[i] == 16 || directionality[i] == 17) {
                return true;
            }
        }
        return false;
    }

    private boolean isRightAligned() {
        int relGravity = this.mGravity & Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK;
        int textAlignment = getTextAlignment();
        boolean rightAligned = false;
        if (relGravity == 5 || ((!isLayoutRtl() && textAlignment == 6) || (isLayoutRtl() && textAlignment == 5))) {
            rightAligned = true;
        }
        if (isCharLTR() || (!isCharRTL() && !isLayoutRtl())) {
            if (textAlignment == 3 || (textAlignment == 1 && relGravity == 8388613)) {
                return true;
            }
            return rightAligned;
        }
        if (isCharRTL() || isLayoutRtl()) {
            if (textAlignment == 2 || (textAlignment == 1 && relGravity == 8388611)) {
                return true;
            }
            return rightAligned;
        }
        return rightAligned;
    }

    private boolean isEditorNotFull() {
        return this.mEditor == null || getSelectionEnd() < length() || (((int) (Math.ceil((double) this.mTextPaint.getHCTStrokeWidth()) + Math.ceil((double) this.mTextPaint.measureText(this.mText.toString())))) + getCompoundPaddingLeft()) + getCompoundPaddingRight() < getWidth();
    }

    public boolean onMultiSelectMenuItem(MenuItem item) {
        if (!ViewRune.WIDGET_PEN_SUPPORTED) {
            return false;
        }
        String source = getPenSelectionController().getPenSelectionContents(getContext(), getRootView());
        CharSequence text = getTextForMultiSelection();
        if (text == null) {
            Log.e(LOG_TAG, "getTextFormultiSelection() text is null");
            return false;
        }
        int id = item.getItemId();
        if (id != 16909349) {
            clearAllMultiSelection();
        }
        if (source == null) {
            Log.e(LOG_TAG, "Multi Selected Text String is null");
            return false;
        }
        switch (id) {
            case 16909349:
                int[] range = new int[2];
                boolean flag = getVisibleTextRange(range);
                if (!flag) {
                    range[0] = 0;
                    range[1] = text.length();
                }
                MultiSelection.setSelection((Spannable) text, range[0], range[1]);
                showMultiSelectPopupWindow();
                break;
            case 16909350:
                if (!isClipboardDisallowedByKnox()) {
                    Spannable sp = new SpannableStringBuilder(source);
                    setPrimaryClip(ClipData.newPlainText(null, sp));
                    break;
                }
                break;
            case 16909351:
                sendToDictionary(source, 0, source.length());
                break;
            case 16909352:
                try {
                    Intent i = makeChooserIntent(true);
                    i.addFlags(268435456);
                    getContext().startActivity(i);
                    break;
                } catch (ActivityNotFoundException ex) {
                    Log.e(LOG_TAG, "Share failed");
                    Log.e(LOG_TAG, "ActivityNotFoundException", ex);
                    break;
                }
            case 16909353:
                Intent intent = item.getIntent();
                if (intent != null && Intent.ACTION_PROCESS_TEXT.equals(intent.getAction())) {
                    intent.putExtra(Intent.EXTRA_PROCESS_TEXT, source);
                    startActivityForResult(intent, 100);
                    break;
                }
                break;
        }
        return false;
        return true;
    }

    public void sendToDictionary(String word, int start, int end) {
        PackageManager pm = getContext().getPackageManager();
        Intent intent = new Intent("com.sec.android.app.dictionary.SEARCH");
        intent.addFlags(32);
        intent.putExtra("keyword", word);
        intent.putExtra("force", "true");
        List<ResolveInfo> info = pm.queryBroadcastReceivers(intent, 32);
        if (info.size() > 0) {
            getContext().sendBroadcast(intent);
        }
    }

    @Override // android.view.View
    public boolean onKeyTextMultiSelection(int keyCode, KeyEvent event) {
        if (!ViewRune.WIDGET_PEN_SUPPORTED) {
            return false;
        }
        if (keyCode == 4 && event.getAction() == 1 && this.mhasMultiSelection) {
            clearMultiSelection();
            return true;
        }
        return super.onKeyTextMultiSelection(keyCode, event);
    }

    @Override // android.view.View
    public boolean onHoverEvent(MotionEvent event) {
        int end;
        if (!ViewRune.WIDGET_PEN_SUPPORTED) {
            return super.onHoverEvent(event);
        }
        mLastHoveredViewId = getId() == -1 ? hashCode() : getId();
        mLastHoveredTime = SystemClock.uptimeMillis();
        CharSequence text = getTextForMultiSelection();
        if (text != null) {
            boolean isBtnPressed = (event.getButtonState() & 32) != 0;
            int action = event.getActionMasked();
            this.mEnableLinkPreview = isLinkPreviewEnabled(event.getToolType(0));
            final InputDevice inputDevice = event.getDevice();
            if (action == 9) {
                this.mCanTextMultiSelection = canTextMultiSelection();
            }
            if (action == 10) {
                if (this.mHoveredSpan != null) {
                    try {
                        SemInfoExtractionManager infoExtractionManager = new SemInfoExtractionManager(getContext());
                        infoExtractionManager.hideLinkPreview();
                    } catch (IllegalStateException e) {
                        Log.d(LOG_TAG, "** skip SemInfoExtractionManager Service by IllegalStateException, onHoverExit **");
                    }
                }
                this.mHoveredSpan = null;
                this.mHoverEnterTime = -1L;
                this.mHoverExitTime = -1L;
                if (MultiSelection.isTextViewHovered()) {
                    MultiSelection.setTextViewHovered(false);
                }
                return super.onHoverEvent(event);
            }
            if (!this.mEnableLinkPreview && !isBtnPressed) {
                return super.onHoverEvent(event);
            }
            if (action == 7) {
                Layout layout = getLayout();
                if (layout == null) {
                    return super.onHoverEvent(event);
                }
                int rawX = (int) event.getRawX();
                int rawY = (int) event.getRawY();
                if (!isBtnPressed || !this.mCanTextMultiSelection || !checkPosOnText(rawX, rawY, (int) this.TOUCH_DELTA)) {
                    if (MultiSelection.getHoveredIcon() == 20002) {
                        MultiSelection.setTextViewHovered(false);
                    }
                    URLSpan[] urlSpans = (URLSpan[]) ((Spannable) text).getSpans(0, text.length(), URLSpan.class);
                    if (urlSpans.length != 0) {
                        int x = (int) event.getX();
                        int y = (int) event.getY();
                        int x2 = x - getTotalPaddingLeft();
                        int y2 = y - getTotalPaddingTop();
                        int x3 = x2 + getScrollX();
                        int x4 = getScrollY();
                        int curLine = layout.getLineForVertical(y2 + x4);
                        int off = layout.getOffsetForHorizontal(curLine, x3);
                        URLSpan[] link = (URLSpan[]) ((Spannable) text).getSpans(off, off, URLSpan.class);
                        if (link.length != 0) {
                            this.mIsShowingLinkPreview = true;
                            int start = ((Spannable) text).getSpanStart(link[0]);
                            int end2 = ((Spannable) text).getSpanEnd(link[0]);
                            String url = text.subSequence(start, end2).toString();
                            Matcher m = emailPattern.matcher(url);
                            if (m.find()) {
                                return super.onHoverEvent(event);
                            }
                            Matcher m2 = urlPattern.matcher(url);
                            if (!m2.find()) {
                                return super.onHoverEvent(event);
                            }
                            int sLine = layout.getLineForOffset(start);
                            int eLine = layout.getLineForOffset(end2);
                            int start2 = curLine == sLine ? (int) layout.getPrimaryHorizontal(start) : (int) layout.getPrimaryHorizontal(0);
                            if (curLine == eLine) {
                                end = (int) layout.getPrimaryHorizontal(end2);
                            } else {
                                end = (int) layout.getLineWidth(curLine);
                            }
                            if (start2 > x3 || x3 > end) {
                                this.mHoverExitTime = -1L;
                            } else if (this.mHoveredSpan != link[0]) {
                                if (this.mHoverEnterTime > 0) {
                                    long elipse = System.currentTimeMillis() - this.mHoverEnterTime;
                                    if (elipse > 300) {
                                        SemInfoExtractionManager infoExtractionManager2 = null;
                                        try {
                                            infoExtractionManager2 = new SemInfoExtractionManager(getContext());
                                        } catch (IllegalStateException e2) {
                                            Log.d(LOG_TAG, "** skip SemInfoExtractionManager Service by IllegalStateException, onHover : URLSpan1 **");
                                        }
                                        if (this.mHoveredSpan != null && infoExtractionManager2 != null) {
                                            infoExtractionManager2.hideLinkPreview();
                                        }
                                        this.mHoveredSpan = link[0];
                                        this.mHoverEnterTime = -1L;
                                        if (infoExtractionManager2 != null) {
                                            int topPadding = getTotalPaddingTop();
                                            int top = layout.getLineTop(sLine) + topPadding;
                                            int bottom = layout.getLineBottom(eLine) + topPadding;
                                            float gabTop = event.getY() - top;
                                            float gabBtm = bottom - event.getY();
                                            int rawX2 = (int) event.getRawXForScaledWindow();
                                            int rawY2 = (int) event.getRawYForScaledWindow();
                                            int top2 = (int) (rawY2 - gabTop);
                                            int bottom2 = (int) (rawY2 + gabBtm);
                                            Rect rect = new Rect();
                                            rect.set(rawX2, top2, rawX2 + 1, bottom2);
                                            if (!TextUtils.isEmpty(url)) {
                                                infoExtractionManager2.showLinkPreview(url, rect);
                                                this.mIsShowingLinkPreview = false;
                                            }
                                        }
                                    }
                                } else {
                                    this.mHoverEnterTime = System.currentTimeMillis();
                                    post(new Runnable() { // from class: android.widget.TextView.7
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            MultiSelection.setTextViewHovered(true, 20010);
                                        }
                                    });
                                }
                            }
                            return super.onHoverEvent(event);
                        }
                        if (this.mIsShowingLinkPreview) {
                            MultiSelection.setTextViewHovered(false);
                            this.mIsShowingLinkPreview = false;
                        }
                    }
                    if (this.mHoveredSpan != null) {
                        if (this.mHoverExitTime > 0) {
                            long elipse2 = System.currentTimeMillis() - this.mHoverExitTime;
                            if (elipse2 > 300) {
                                try {
                                    SemInfoExtractionManager infoExtractionManager3 = new SemInfoExtractionManager(getContext());
                                    infoExtractionManager3.hideLinkPreview();
                                } catch (IllegalStateException e3) {
                                    Log.d(LOG_TAG, "** skip SemInfoExtractionManager Service by IllegalStateException, onHover : hover exit **");
                                }
                                this.mHoveredSpan = null;
                                this.mHoverEnterTime = -1L;
                                this.mHoverExitTime = -1L;
                            }
                        } else {
                            this.mHoverExitTime = System.currentTimeMillis();
                            MultiSelection.setTextViewHovered(false);
                        }
                        return super.onHoverEvent(event);
                    }
                } else {
                    if (MultiSelection.getHoveredIcon() != 20002) {
                        post(new Runnable() { // from class: android.widget.TextView.6
                            @Override // java.lang.Runnable
                            public void run() {
                                MultiSelection.setTextViewHovered(true, 20002);
                            }
                        });
                        try {
                            if (this.mHoveredSpan != null) {
                                SemInfoExtractionManager infoExtractionManager4 = new SemInfoExtractionManager(getContext());
                                infoExtractionManager4.hideLinkPreview();
                            }
                            this.mHoveredSpan = null;
                            this.mHoverEnterTime = -1L;
                            this.mHoverExitTime = -1L;
                        } catch (IllegalStateException e4) {
                            Log.d(LOG_TAG, "** hovering dismiss **");
                        }
                    }
                    return super.onHoverEvent(event);
                }
            }
        }
        return super.onHoverEvent(event);
    }

    @Override // android.view.View
    public void onStartTemporaryDetach() {
        super.onStartTemporaryDetach();
        if (ViewRune.WIDGET_PEN_SUPPORTED) {
            removeForStylusPenEvent();
            clearMultiSelection();
        }
    }

    @Override // android.view.View
    public void onFinishTemporaryDetach() {
        super.onFinishTemporaryDetach();
        if (ViewRune.WIDGET_PEN_SUPPORTED) {
            this.mAttachedWindow = true;
            registerForStylusPenEvent();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public PenSelectionController getPenSelectionController() {
        if (this.mPenSelectionController == null) {
            this.mPenSelectionController = PenSelectionController.getInstance();
        }
        return this.mPenSelectionController;
    }

    private class StylusEventListener implements ViewTreeObserver.SemOnStylusButtonEventListener {
        private TextView mThisView;
        private boolean mPressed = false;
        private boolean mIsSelectableTextView = false;
        private int mStartCurPosition = 0;
        private int mEndCurPosition = 0;
        private int oldEndPos = -1;
        private int[] selectRange = new int[2];
        private float mStartX = 0.0f;
        private float mStartY = 0.0f;
        private float mMaxX = 0.0f;

        public StylusEventListener(TextView textView) {
            this.mThisView = null;
            this.mThisView = textView;
        }

        @Override // android.view.ViewTreeObserver.SemOnStylusButtonEventListener
        public void onStylusButtonEvent(MotionEvent event) {
            if (!ViewRune.WIDGET_PEN_SUPPORTED) {
                return;
            }
            int action = event.getActionMasked();
            if (event.getToolType(0) != 2 || !TextView.this.mEnableMultiSelection) {
                return;
            }
            float rawX = event.getRawX();
            float rawY = event.getRawY();
            if (action == 0 && (event.getButtonState() & 32) != 0) {
                if (!TextView.mIsFindTargetView) {
                    if (TextView.mTargetViewId != -1) {
                        if (!ViewRune.WIDGET_MULTIPLE_PEN_TEXT_SUPPORTED) {
                            ((TextView) TextView.this.findViewById(TextView.mTargetViewId)).clearMultiSelection();
                        }
                        TextView.mTargetViewId = -1;
                    }
                    if (TextView.this.canTextMultiSelection()) {
                        TextView.mCurTime = SystemClock.uptimeMillis();
                        if (TextView.mLastHoveredViewId != -1 && TextView.mCurTime - TextView.mLastHoveredTime < 100) {
                            TextView.mTargetViewId = TextView.mLastHoveredViewId;
                        }
                    }
                    TextView.mLastHoveredViewId = -1;
                    TextView.mIsFindTargetView = true;
                }
                if (TextView.mTargetViewId == -1 || this.mThisView == null) {
                    return;
                }
                if (TextView.mTargetViewId != this.mThisView.getId() && TextView.mTargetViewId != this.mThisView.hashCode()) {
                    this.mThisView.hideCursorControllers();
                    return;
                }
                if (TextView.this.mMarquee != null && !TextView.this.mMarquee.isStopped()) {
                    return;
                }
                if (TextView.this.findViewById(TextView.mTargetViewId) instanceof EditText) {
                    ((EditText) TextView.this.findViewById(TextView.mTargetViewId)).hideCursorControllers();
                    if (!TextView.this.checkPosInView((int) rawX, (int) rawY, 0) || !TextView.this.canSelectText()) {
                        return;
                    }
                } else if (!TextView.this.checkPosOnText((int) rawX, (int) rawY, (int) TextView.this.TOUCH_DELTA)) {
                    return;
                }
            } else {
                TextView.mIsFindTargetView = false;
                if (!this.mPressed || TextView.mTargetViewId == -1) {
                    return;
                }
            }
            CharSequence text = TextView.this.getTextForMultiSelection();
            if (TextUtils.isEmpty(text)) {
                return;
            }
            switch (action) {
                case 0:
                    if (!this.mPressed) {
                        TextView.this.hideMultiSelectPopupWindow();
                        this.mIsSelectableTextView = TextView.this.isTextSelectable() || (this.mThisView instanceof EditText);
                        Point startPos = TextView.this.getScreenPointOfView(this.mThisView);
                        this.mStartX = rawX - startPos.x;
                        this.mStartY = rawY - startPos.y;
                        this.mMaxX = 0.0f;
                        this.mStartCurPosition = TextView.this.getOffsetForPosition(this.mStartX, this.mStartY);
                        if (this.mStartCurPosition >= 0) {
                            if (ViewRune.WIDGET_PEN_SUPPORTED && !this.mIsSelectableTextView) {
                                if (TextView.mCurTime - TextView.mLastPenDownTime < ViewConfiguration.getDoubleTapTimeout()) {
                                    if (TextView.mShowPenSelectionRunnable != null) {
                                        this.mThisView.removeCallbacks(TextView.mShowPenSelectionRunnable);
                                        TextView.mShowPenSelectionRunnable = null;
                                    }
                                    MultiSelection.setIsMultiSelectingText(false);
                                    MultiSelection.setNeedToScroll(false);
                                    TextView.mLastPenDownTime = TextView.mCurTime;
                                    return;
                                }
                                MultiSelection.setIsMultiSelectingText(true);
                                boolean flag = TextView.this.getVisibleTextRange(this.selectRange);
                                if (!flag) {
                                    this.selectRange[0] = 0;
                                    this.selectRange[1] = text.length();
                                }
                                if (this.mStartCurPosition < this.selectRange[0]) {
                                    this.mStartCurPosition = this.selectRange[0];
                                } else if (this.mStartCurPosition > this.selectRange[1]) {
                                    this.mStartCurPosition = this.selectRange[1];
                                }
                                TextView.mLastPenDownTime = TextView.mCurTime;
                                Log.d(TextView.LOG_TAG, "Pen down with side button! : start text selection");
                            }
                            this.oldEndPos = this.mStartCurPosition;
                            this.mPressed = true;
                            TextView.this.mIsTouchDown = true;
                        }
                        if (ViewRune.WIDGET_MULTIPLE_PEN_TEXT_SUPPORTED && MultiSelection.getMultiSelectionCount((Spannable) text) > 0) {
                            int oldStart = MultiSelection.getSelectionStart((Spannable) text);
                            int oldEnd = MultiSelection.getSelectionEnd((Spannable) text);
                            if (this.mStartCurPosition < oldStart || this.mStartCurPosition > oldEnd) {
                                MultiSelection.addMultiSelection((Spannable) text, oldStart, oldEnd);
                            }
                        }
                        MultiSelection.setNeedToScroll(false);
                        return;
                    }
                    return;
                case 1:
                    break;
                case 2:
                    if ((event.getButtonState() & 32) != 0) {
                        if (ViewRune.WIDGET_PEN_SUPPORTED) {
                            Point startPos2 = TextView.this.getScreenPointOfView(this.mThisView);
                            float endX = rawX - startPos2.x;
                            float endY = rawY - startPos2.y;
                            MultiSelection.setNeedToScroll(TextView.this.checkPosInView((int) rawX, (int) rawY, 0));
                            if (this.mMaxX < Math.abs(this.mStartX - endX)) {
                                this.mMaxX = Math.abs(this.mStartX - endX);
                            }
                            this.mEndCurPosition = TextView.this.getOffsetForPosition(endX, endY);
                            if (!this.mIsSelectableTextView) {
                                if (this.mEndCurPosition < this.selectRange[0]) {
                                    this.mEndCurPosition = this.selectRange[0];
                                } else if (this.mEndCurPosition > this.selectRange[1]) {
                                    this.mEndCurPosition = this.selectRange[1];
                                }
                            }
                            if (this.mStartCurPosition == this.mEndCurPosition) {
                                if (!this.mIsSelectableTextView) {
                                    MultiSelection.removeCurSelection((Spannable) text);
                                    return;
                                }
                                return;
                            }
                            if (this.oldEndPos != this.mEndCurPosition && this.mEndCurPosition >= 0) {
                                this.oldEndPos = this.mEndCurPosition;
                                TextView.this.mHighlightPathBogus = true;
                                if (!this.mIsSelectableTextView) {
                                    if (this.mStartCurPosition > text.length()) {
                                        this.mStartCurPosition = text.length();
                                    }
                                    if (this.mEndCurPosition > text.length()) {
                                        this.mEndCurPosition = text.length();
                                    }
                                    MultiSelection.setSelection((Spannable) text, this.mStartCurPosition, this.mEndCurPosition);
                                    TextView.this.mhasMultiSelection = true;
                                    return;
                                }
                                if (!this.mThisView.isFocused()) {
                                    this.mThisView.requestFocus();
                                }
                                Selection.setSelection((Spannable) text, this.mStartCurPosition, this.mEndCurPosition);
                                return;
                            }
                            return;
                        }
                        return;
                    }
                    break;
                case 3:
                case 214:
                    TextView.this.mIsTouchDown = false;
                    this.mPressed = false;
                    if (this.mIsSelectableTextView) {
                        TextView.this.mhasMultiSelection = false;
                        if (this.mStartCurPosition >= 0) {
                            Selection.setSelection((Spannable) text, this.mStartCurPosition);
                            return;
                        }
                        return;
                    }
                    MultiSelection.setIsMultiSelectingText(false);
                    MultiSelection.removeCurSelection((Spannable) text);
                    TextView.this.mhasMultiSelection = MultiSelection.getMultiSelectionCount((Spannable) text) > 0;
                    return;
                default:
                    return;
            }
            Point startPos3 = TextView.this.getScreenPointOfView(this.mThisView);
            float endX2 = rawX - startPos3.x;
            float endY2 = rawY - startPos3.y;
            if (this.mMaxX < Math.abs(this.mStartX - endX2)) {
                this.mMaxX = Math.abs(this.mStartX - endX2);
            }
            this.mEndCurPosition = TextView.this.getOffsetForPosition(endX2, endY2);
            if (!this.mIsSelectableTextView) {
                if (this.mEndCurPosition < this.selectRange[0]) {
                    this.mEndCurPosition = this.selectRange[0];
                } else if (this.mEndCurPosition > this.selectRange[1]) {
                    this.mEndCurPosition = this.selectRange[1];
                }
            }
            boolean isSameLine = false;
            if (TextView.this.mLayout != null) {
                isSameLine = TextView.this.getLineAtCoordinate(this.mStartY) == TextView.this.getLineAtCoordinate(endY2);
            }
            if (!this.mIsSelectableTextView && isSameLine && this.mMaxX < TextView.this.TOUCH_DELTA) {
                if (TextView.mShowPenSelectionRunnable != null) {
                    this.mThisView.removeCallbacks(TextView.mShowPenSelectionRunnable);
                    TextView.mShowPenSelectionRunnable = null;
                }
                TextView.mShowPenSelectionRunnable = new Runnable() { // from class: android.widget.TextView.StylusEventListener.1
                    @Override // java.lang.Runnable
                    public void run() {
                        CharSequence text2 = TextView.this.getTextForMultiSelection();
                        boolean isPenSelected = TextView.this.selectCurrentWordForMultiSelection(StylusEventListener.this.mStartCurPosition, StylusEventListener.this.mEndCurPosition);
                        if (isPenSelected) {
                            StylusEventListener.this.mStartCurPosition = MultiSelection.getSelectionStart((Spannable) text2);
                            StylusEventListener.this.mEndCurPosition = MultiSelection.getSelectionEnd((Spannable) text2);
                            TextView.this.showMultiSelectPopupWindow();
                            try {
                                SemInfoExtractionManager infoExtractionManager = new SemInfoExtractionManager(TextView.this.getContext());
                                infoExtractionManager.training(text2.subSequence(StylusEventListener.this.mStartCurPosition, StylusEventListener.this.mEndCurPosition).toString());
                            } catch (IllegalStateException e) {
                                Log.e(TextView.LOG_TAG, "** skip SemInfoExtractionManager Service by IllegalStateException **");
                            }
                            Log.d(TextView.LOG_TAG, "Pen up with side button! : end text selection");
                            TextView.this.registerForTouchMonitorListener();
                            TextView.this.mhasMultiSelection = true;
                            MultiSelection.setIsMultiSelectingText(false);
                            MultiSelection.setNeedToScroll(false);
                        }
                    }
                };
                this.mThisView.postDelayed(TextView.mShowPenSelectionRunnable, ViewConfiguration.getDoubleTapTimeout());
                TextView.this.mIsTouchDown = false;
                this.mPressed = false;
                return;
            }
            MultiSelection.setNeedToScroll(false);
            if (this.mStartCurPosition == this.mEndCurPosition) {
                this.mPressed = false;
                if (this.mIsSelectableTextView) {
                    TextView.this.mhasMultiSelection = false;
                    return;
                }
                MultiSelection.setIsMultiSelectingText(false);
                MultiSelection.removeCurSelection((Spannable) text);
                TextView.this.mhasMultiSelection = MultiSelection.getMultiSelectionCount((Spannable) text) > 0;
                return;
            }
            if (this.mStartCurPosition > this.mEndCurPosition) {
                int temp = this.mStartCurPosition;
                this.mStartCurPosition = this.mEndCurPosition;
                this.mEndCurPosition = temp;
            }
            if (!this.mIsSelectableTextView) {
                MultiSelection.setSelection((Spannable) text, this.mStartCurPosition, this.mEndCurPosition);
                TextView.this.showMultiSelectPopupWindow();
                try {
                    SemInfoExtractionManager infoExtractionManager = new SemInfoExtractionManager(TextView.this.getContext());
                    infoExtractionManager.training(text.subSequence(this.mStartCurPosition, this.mEndCurPosition).toString());
                } catch (IllegalStateException e) {
                    Log.e(TextView.LOG_TAG, "** skip SemInfoExtractionManager Service by IllegalStateException **");
                }
                Log.d(TextView.LOG_TAG, "Pen up with side button! : end text selection");
                TextView.this.registerForTouchMonitorListener();
                TextView.this.mhasMultiSelection = true;
                MultiSelection.setIsMultiSelectingText(false);
            } else if (this.mStartCurPosition >= 0 && this.mEndCurPosition >= 0 && ViewRune.WIDGET_PEN_SUPPORTED) {
                Selection.setSelection((Spannable) text, this.mStartCurPosition, this.mEndCurPosition);
                if (TextView.this.mEditor != null && TextView.this.mEditor.mCreatedWithASelection) {
                    TextView.this.mEditor.lambda$startActionModeInternal$0();
                }
                if (TextView.this.mEditor != null) {
                    TextView.this.mEditor.startSelectionActionModeAsync(false);
                    TextView.this.mEditor.mCreatedWithASelection = false;
                }
            }
            TextView.this.mIsTouchDown = false;
            this.mPressed = false;
        }
    }

    @RemotableViewMethod
    public void semSetButtonShapeEnabled(boolean enabled) {
        this.mIsButtonShapeTarget = enabled;
        initButtonShape();
    }

    private void hidden_semSetButtonShapeEnabled(boolean enabled) {
        semSetButtonShapeEnabled(enabled);
    }

    public void semSetButtonShapeEnabled(boolean enabled, int textColor) {
        this.mButtonShapeTextColor = Integer.valueOf(textColor);
        semSetButtonShapeEnabled(enabled);
    }

    private void hidden_semSetButtonShapeEnabled(boolean enabled, int textColor) {
        semSetButtonShapeEnabled(enabled, textColor);
    }

    private void initButtonShape() {
        if (this.mButtonShapePaint == null) {
            this.mButtonShapePaint = new Paint();
            this.mButtonShapePaint.setAntiAlias(true);
        }
        if (this.mButtonShapeRect == null) {
            this.mButtonShapeRect = new RectF();
        }
        Resources resources = getResources();
        this.mIsNightMode = (resources.getConfiguration().uiMode & 48) == 32;
        this.mButtonShapeSettingEnabled = resources.getConfiguration().semButtonShapeEnabled == 1;
        this.mButtonShapeTextColorLight = resources.getColor(com.android.internal.R.color.sem_button_shape_text_color_light, null);
        this.mButtonShapeTextColorDark = resources.getColor(com.android.internal.R.color.sem_button_shape_text_color_dark, null);
        this.mButtonShapeOutlineStrokeEnabled = resources.getInteger(com.android.internal.R.integer.sem_button_shape_outline_stroke_enabled);
        this.mButtonShapeOutlineStrokeDisabled = resources.getInteger(com.android.internal.R.integer.sem_button_shape_outline_stroke_disabled);
        this.mButtonShapeAlpha = this.mButtonShapeOutlineStrokeEnabled;
        this.mButtonShapeOutlineStrokeTop = resources.getDimensionPixelSize(com.android.internal.R.dimen.sem_button_shape_outline_stroke_top);
        this.mButtonShapeOutlineStrokeBottom = resources.getDimensionPixelSize(com.android.internal.R.dimen.sem_button_shape_outline_stroke_bottom);
        this.mButtonShapeOutlineStrokeHorizontal = resources.getDimensionPixelSize(com.android.internal.R.dimen.sem_button_shape_outline_stroke_horizontal);
        this.mButtonShapeOutlineRadius = resources.getDimensionPixelSize(com.android.internal.R.dimen.sem_button_shape_outline_radius);
    }

    private int getButtonShapeTextColor() {
        this.mButtonShapeLuminance = ColorUtils.calculateLuminance(this.mButtonShapeColor);
        if (this.mButtonShapeTextColor != null) {
            return this.mButtonShapeTextColor.intValue();
        }
        return this.mIsNightMode ? (this.mButtonShapeLuminance <= SContextConstants.ENVIRONMENT_VALUE_UNKNOWN || this.mButtonShapeLuminance >= 0.25d) ? this.mButtonShapeTextColorDark : this.mButtonShapeTextColorLight : this.mButtonShapeLuminance > 0.8d ? this.mButtonShapeTextColorDark : this.mButtonShapeTextColorLight;
    }

    public void setCursorThicknessScale(float scale) {
        this.mCursorThicknessScale = scale;
        if (this.mLayout != null) {
            nullLayouts();
            requestLayout();
            invalidate();
        }
    }

    float getCursorThicknessScale() {
        return Math.max(this.mCursorThicknessScale, 1.0f);
    }

    void startChooserPopupActivity(Point location, boolean isMultiSelect) {
        Intent intent = makeChooserIntent(isMultiSelect);
        if (intent == null) {
            return;
        }
        ActivityOptions opts = ActivityOptions.makeBasic();
        opts.semSetChooserPopOverPosition(getPosition(location));
        intent.setFlags(268435456);
        getContext().startActivity(intent, opts.toBundle());
        if (!isMultiSelect) {
            Selection.setSelection(this.mSpannable, getSelectionEnd());
        }
    }

    private int getPosition(Point location) {
        int pos;
        Resources resources = getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        int height = metrics.heightPixels;
        int width = metrics.widthPixels;
        int statusBarHeight = resources.getDimensionPixelSize(com.android.internal.R.dimen.status_bar_height);
        Point increment = new Point();
        increment.x = width / 3;
        increment.y = (height - statusBarHeight) / 3;
        if (location.x >= 0 && location.x < increment.x) {
            pos = 16;
        } else if (increment.x <= location.x && location.x < (increment.x << 1)) {
            pos = 64;
        } else {
            pos = 32;
        }
        if (statusBarHeight <= location.y && location.y < increment.y + statusBarHeight) {
            return pos | 1;
        }
        if (increment.y + statusBarHeight <= location.y && location.y < (increment.y << 1) + statusBarHeight) {
            return pos | 4;
        }
        return pos | 2;
    }

    private Intent makeChooserIntent(boolean isMultiSelect) {
        String selectedText;
        CharSequence title = null;
        if (isMultiSelect) {
            selectedText = getPenSelectionController().getPenSelectionContents(getContext(), getRootView());
            title = getContext().getString(com.android.internal.R.string.share);
        } else {
            selectedText = getSelectedText();
        }
        if (selectedText != null && !selectedText.isEmpty()) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.removeExtra(Intent.EXTRA_TEXT);
            intent.putExtra(Intent.EXTRA_TEXT, (String) TextUtils.trimToParcelableSize(selectedText));
            return Intent.createChooser(intent, title);
        }
        return null;
    }

    boolean canSSSPaste() {
        return (this.mText instanceof Editable) && this.mEditor != null && this.mEditor.mKeyListener != null && getSelectionStart() >= 0 && getSelectionEnd() >= 0;
    }

    protected String semGetFontFamily() {
        return this.mFontFamily;
    }

    @Deprecated
    public int semAddOuterShadowTextEffect(float angle, float offset, float softness, int color, float blendingOpacity) {
        if (this.mTextEffect == null) {
            this.mTextEffect = new SFText(getContext());
        }
        return this.mTextEffect.addOuterShadowTextEffect(angle, offset, softness, color, blendingOpacity);
    }

    @Deprecated
    public int semAddInnerShadowTextEffect(float angle, float offset, float softness, int color, float blendingOpacity) {
        if (this.mTextEffect == null) {
            this.mTextEffect = new SFText(getContext());
        }
        return this.mTextEffect.addInnerShadowTextEffect(angle, offset, softness, color, blendingOpacity);
    }

    @Deprecated
    public int semAddStrokeTextEffect(float size, int color, float blendingOpacity) {
        if (this.mTextEffect == null) {
            this.mTextEffect = new SFText(getContext());
        }
        return this.mTextEffect.addStrokeTextEffect(size, color, blendingOpacity);
    }

    @Deprecated
    public int semAddOuterGlowTextEffect(float size, int color, float blendingOpacity) {
        if (this.mTextEffect == null) {
            this.mTextEffect = new SFText(getContext());
        }
        return this.mTextEffect.addOuterGlowTextEffect(size, color, blendingOpacity);
    }

    @Deprecated
    public int semAddLinearGradientTextEffect(float angle, float scale, int[] colors, float[] alphas, float[] positions, float blendingOpacity) {
        if (this.mTextEffect == null) {
            this.mTextEffect = new SFText(getContext());
        }
        return this.mTextEffect.addLinearGradientTextEffect(angle, scale, colors, alphas, positions, blendingOpacity);
    }

    @Deprecated
    public void semClearAllTextEffect() {
        if (this.mTextEffect == null) {
            this.mTextEffect = new SFText(getContext());
        }
        this.mTextEffect.clearAllTextEffect();
    }

    @Deprecated
    public void semSetFontFromFile(String path) {
        Preconditions.checkNotNull(path);
        if (this.mTextEffect == null) {
            this.mTextEffect = new SFText(getContext());
        }
        this.mTextEffect.setFontFromFile(path);
    }

    @Deprecated
    public void semSetFontFromAsset(AssetManager mgr, String path) {
        Preconditions.checkNotNull(path);
        Preconditions.checkNotNull(mgr);
        Typeface tf = Typeface.createFromAsset(mgr, path);
        setTypeface(tf);
        if (this.mTextEffect == null) {
            this.mTextEffect = new SFText(getContext());
        }
        this.mTextEffect.setFontFromAsset(mgr, path);
    }

    @Override // android.view.View
    public View semDispatchFindView(float x, float y, boolean findImage) {
        String foundText = null;
        if (this.mText != null) {
            int offset = getOffsetForPosition(x, y);
            int length = this.mText.length();
            if (offset == length) {
                offset = length - 1;
            }
            if (length < 500) {
                foundText = this.mText.toString();
            } else if (offset < 250) {
                foundText = this.mText.subSequence(0, Math.min(length, 500)).toString();
            } else {
                foundText = this.mText.subSequence(offset - 250, Math.min(length, offset + 250)).toString();
            }
        }
        semSetBixbyTouchFoundText(foundText);
        return this;
    }
}
