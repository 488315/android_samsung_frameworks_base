package android.widget;

import android.Manifest;
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
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.CompatibilityInfo;
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
import android.graphics.fonts.FontVariationAxis;
import android.graphics.text.LineBreakConfig;
import android.hardware.scontext.SContextConstants;
import android.icu.text.DecimalFormatSymbols;
import android.inputmethodservice.ExtractEditText;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.MediaCodecInfo;
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
import android.text.Emoji;
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
import android.view.ViewParent;
import android.view.ViewRootImpl;
import android.view.ViewStructure;
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
import com.android.internal.R;
import com.android.internal.accessibility.util.AccessibilityUtils;
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
import com.samsung.android.cover.ICoverManager;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import com.samsung.android.infoextraction.SemInfoExtractionManager;
import com.samsung.android.penselect.PenSelectionController;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.rune.ViewRune;
import com.samsung.android.sdk.sfe.SFText;
import com.samsung.android.sepunion.UnionConstants;
import com.samsung.android.widget.ISemTouchApi;
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
/* loaded from: classes5.dex */
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
    static final int ID_CLIPBOARD = 16908943;
    static final int ID_CLOSE = 16909130;
    static final int ID_COPY = 16908321;
    static final int ID_CUT = 16908320;
    static final int ID_DELETE = 16909029;
    static final int ID_HBD_TRANSLATE = 16909167;
    static final int ID_MANAGE_APPS = 16909333;
    static final int ID_MULTI_SELECT_ALL = 16909391;
    static final int ID_MULTI_SELECT_COPY = 16909392;
    static final int ID_MULTI_SELECT_DICTIONARY = 16909393;
    static final int ID_MULTI_SELECT_SHARE = 16909394;
    static final int ID_MULTI_SELECT_TRANSLATE = 16909395;
    static final int ID_PASTE = 16908322;
    static final int ID_PASTE_AS_PLAIN_TEXT = 16908337;
    static final int ID_REDO = 16908339;
    static final int ID_REPLACE = 16908340;
    static final int ID_SCAN_TEXT = 16909641;
    static final int ID_SELECT_ALL = 16908319;
    static final int ID_SHARE = 16908341;
    static final int ID_SSS_TRANSLATE = 16909874;
    static final int ID_UNDO = 16908338;
    static final int ID_WEBSEARCH = 16910076;
    static final int ID_WRITING_TOOLKIT = 16910091;
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
    private static final SparseIntArray sAppearanceValues;
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
    private Matrix mTempMatrix;
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
    private static final List<String> ACCESSIBILITY_EXTRA_DATA_KEYS = List.of(AccessibilityNodeInfo.EXTRA_DATA_RENDERING_INFO_KEY, AccessibilityNodeInfo.EXTRA_DATA_TEXT_CHARACTER_LOCATION_KEY);
    private static final List<String> ACCESSIBILITY_EXTRA_DATA_KEYS_FLAGGED = List.of(AccessibilityNodeInfo.EXTRA_DATA_RENDERING_INFO_KEY, AccessibilityNodeInfo.EXTRA_DATA_TEXT_CHARACTER_LOCATION_KEY, AccessibilityNodeInfo.EXTRA_DATA_TEXT_CHARACTER_LOCATION_IN_WINDOW_KEY);
    private static final Pattern urlPattern = Patterns.WEB_URL;
    private static final Pattern emailPattern = Patterns.EMAIL_ADDRESS;
    private static ViewRootImpl.MotionEventMonitor.OnTouchListener mMotionEventMonitorListener = null;

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

    private static final int hidden_SEM_AUTOFILL_ID() {
        return 131072;
    }

    private boolean isDirectionalNavigationKey(int i) {
        switch (i) {
            case 19:
            case 20:
            case 21:
            case 22:
                return true;
            default:
                return false;
        }
    }

    private boolean isFlipCoverClosed() {
        return false;
    }

    private static boolean isMultilineInputType(int i) {
        return (i & 131087) == 131073;
    }

    static boolean isPasswordInputType(int i) {
        int i2 = i & 4095;
        return i2 == 129 || i2 == 225 || i2 == 18;
    }

    private static boolean isVisiblePasswordInputType(int i) {
        return (i & 4095) == 145;
    }

    public static void preloadFontCache() {
    }

    protected boolean getDefaultEditable() {
        return false;
    }

    protected MovementMethod getDefaultMovementMethod() {
        return null;
    }

    public int getHorizontalOffsetForDrawables() {
        return 0;
    }

    @Override // android.view.View
    public boolean isAccessibilitySelectionExtendable() {
        return true;
    }

    public boolean isInExtractedMode() {
        return false;
    }

    public void onBeginBatchEdit() {
    }

    public void onCommitCompletion(CompletionInfo completionInfo) {
    }

    public void onEndBatchEdit() {
    }

    public boolean onPrivateIMECommand(String str, Bundle bundle) {
        return false;
    }

    protected void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    protected boolean supportsAutoSizeText() {
        return true;
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
            IntFlagMapping intFlagMapping = new IntFlagMapping();
            intFlagMapping.add(2, 2, "email");
            intFlagMapping.add(8, 8, "map");
            intFlagMapping.add(4, 4, "phone");
            intFlagMapping.add(1, 1, SemSmartClipDataRepository.CONTENT_TYPE_WEB);
            this.mAutoLinkId = propertyMapper.mapIntFlag("autoLink", 16842928, new View$InspectionCompanion$$ExternalSyntheticLambda1(intFlagMapping));
            this.mAutoSizeMaxTextSizeId = propertyMapper.mapInt("autoSizeMaxTextSize", 16844102);
            this.mAutoSizeMinTextSizeId = propertyMapper.mapInt("autoSizeMinTextSize", 16844088);
            this.mAutoSizeStepGranularityId = propertyMapper.mapInt("autoSizeStepGranularity", 16844086);
            SparseArray sparseArray = new SparseArray();
            sparseArray.put(0, "none");
            sparseArray.put(1, "uniform");
            this.mAutoSizeTextTypeId = propertyMapper.mapIntEnum("autoSizeTextType", 16844085, new View$InspectionCompanion$$ExternalSyntheticLambda0(sparseArray));
            SparseArray sparseArray2 = new SparseArray();
            sparseArray2.put(0, "simple");
            sparseArray2.put(1, "high_quality");
            sparseArray2.put(2, RecognizerIntent.LANGUAGE_SWITCH_BALANCED);
            this.mBreakStrategyId = propertyMapper.mapIntEnum("breakStrategy", 16843997, new View$InspectionCompanion$$ExternalSyntheticLambda0(sparseArray2));
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
            SparseArray sparseArray3 = new SparseArray();
            sparseArray3.put(0, "none");
            sparseArray3.put(1, "normal");
            sparseArray3.put(2, "full");
            this.mHyphenationFrequencyId = propertyMapper.mapIntEnum("hyphenationFrequency", 16843998, new View$InspectionCompanion$$ExternalSyntheticLambda0(sparseArray3));
            this.mImeActionIdId = propertyMapper.mapInt("imeActionId", 16843366);
            this.mImeActionLabelId = propertyMapper.mapObject("imeActionLabel", 16843365);
            IntFlagMapping intFlagMapping2 = new IntFlagMapping();
            intFlagMapping2.add(255, 6, "actionDone");
            intFlagMapping2.add(255, 2, "actionGo");
            intFlagMapping2.add(255, 5, "actionNext");
            intFlagMapping2.add(255, 1, "actionNone");
            intFlagMapping2.add(255, 7, "actionPrevious");
            intFlagMapping2.add(255, 3, "actionSearch");
            intFlagMapping2.add(255, 4, "actionSend");
            intFlagMapping2.add(255, 0, "actionUnspecified");
            intFlagMapping2.add(Integer.MIN_VALUE, Integer.MIN_VALUE, "flagForceAscii");
            intFlagMapping2.add(134217728, 134217728, "flagNavigateNext");
            intFlagMapping2.add(67108864, 67108864, "flagNavigatePrevious");
            intFlagMapping2.add(536870912, 536870912, "flagNoAccessoryAction");
            intFlagMapping2.add(1073741824, 1073741824, "flagNoEnterAction");
            intFlagMapping2.add(268435456, 268435456, "flagNoExtractUi");
            intFlagMapping2.add(33554432, 33554432, "flagNoFullscreen");
            intFlagMapping2.add(16777216, 16777216, "flagNoPersonalizedLearning");
            intFlagMapping2.add(-1, 0, "normal");
            this.mImeOptionsId = propertyMapper.mapIntFlag("imeOptions", 16843364, new View$InspectionCompanion$$ExternalSyntheticLambda1(intFlagMapping2));
            this.mIncludeFontPaddingId = propertyMapper.mapBoolean("includeFontPadding", 16843103);
            IntFlagMapping intFlagMapping3 = new IntFlagMapping();
            intFlagMapping3.add(4095, 20, "date");
            intFlagMapping3.add(4095, 4, TextClassifier.TYPE_DATE_TIME);
            intFlagMapping3.add(-1, 0, "none");
            intFlagMapping3.add(4095, 2, "number");
            intFlagMapping3.add(16773135, 8194, "numberDecimal");
            intFlagMapping3.add(4095, 18, "numberPassword");
            intFlagMapping3.add(16773135, 4098, "numberSigned");
            intFlagMapping3.add(4095, 3, "phone");
            intFlagMapping3.add(4095, 1, "text");
            intFlagMapping3.add(16773135, 65537, "textAutoComplete");
            intFlagMapping3.add(16773135, 32769, "textAutoCorrect");
            intFlagMapping3.add(16773135, 4097, "textCapCharacters");
            intFlagMapping3.add(16773135, 16385, "textCapSentences");
            intFlagMapping3.add(16773135, 8193, "textCapWords");
            intFlagMapping3.add(4095, 33, "textEmailAddress");
            intFlagMapping3.add(4095, 49, "textEmailSubject");
            intFlagMapping3.add(4095, 177, "textFilter");
            intFlagMapping3.add(16773135, 262145, "textImeMultiLine");
            intFlagMapping3.add(4095, 81, "textLongMessage");
            intFlagMapping3.add(16773135, MediaCodecInfo.CodecProfileLevel.APVLevel51Band0, "textMultiLine");
            intFlagMapping3.add(16773135, MediaCodecInfo.CodecProfileLevel.APVLevel61Band0, "textNoSuggestions");
            intFlagMapping3.add(4095, 129, "textPassword");
            intFlagMapping3.add(4095, 97, "textPersonName");
            intFlagMapping3.add(4095, 193, "textPhonetic");
            intFlagMapping3.add(4095, 113, "textPostalAddress");
            intFlagMapping3.add(4095, 65, "textShortMessage");
            intFlagMapping3.add(4095, 17, "textUri");
            intFlagMapping3.add(4095, 145, "textVisiblePassword");
            intFlagMapping3.add(4095, 161, "textWebEditText");
            intFlagMapping3.add(4095, 209, "textWebEmailAddress");
            intFlagMapping3.add(4095, 225, "textWebPassword");
            intFlagMapping3.add(4095, 36, "time");
            this.mInputTypeId = propertyMapper.mapIntFlag("inputType", 16843296, new View$InspectionCompanion$$ExternalSyntheticLambda1(intFlagMapping3));
            SparseArray sparseArray4 = new SparseArray();
            sparseArray4.put(0, "none");
            sparseArray4.put(1, "inter_word");
            this.mJustificationModeId = propertyMapper.mapIntEnum("justificationMode", 16844135, new View$InspectionCompanion$$ExternalSyntheticLambda0(sparseArray4));
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
        public void readProperties(TextView textView, PropertyReader propertyReader) {
            if (!this.mPropertiesMapped) {
                throw new InspectionCompanion.UninitializedPropertyMapException();
            }
            propertyReader.readIntFlag(this.mAutoLinkId, textView.getAutoLinkMask());
            propertyReader.readInt(this.mAutoSizeMaxTextSizeId, textView.getAutoSizeMaxTextSize());
            propertyReader.readInt(this.mAutoSizeMinTextSizeId, textView.getAutoSizeMinTextSize());
            propertyReader.readInt(this.mAutoSizeStepGranularityId, textView.getAutoSizeStepGranularity());
            propertyReader.readIntEnum(this.mAutoSizeTextTypeId, textView.getAutoSizeTextType());
            propertyReader.readIntEnum(this.mBreakStrategyId, textView.getBreakStrategy());
            propertyReader.readBoolean(this.mCursorVisibleId, textView.isCursorVisible());
            propertyReader.readObject(this.mDrawableBlendModeId, textView.getCompoundDrawableTintBlendMode());
            propertyReader.readInt(this.mDrawablePaddingId, textView.getCompoundDrawablePadding());
            propertyReader.readObject(this.mDrawableTintId, textView.getCompoundDrawableTintList());
            propertyReader.readObject(this.mDrawableTintModeId, textView.getCompoundDrawableTintMode());
            propertyReader.readBoolean(this.mElegantTextHeightId, textView.isElegantTextHeight());
            propertyReader.readObject(this.mEllipsizeId, textView.getEllipsize());
            propertyReader.readBoolean(this.mFallbackLineSpacingId, textView.isFallbackLineSpacing());
            propertyReader.readInt(this.mFirstBaselineToTopHeightId, textView.getFirstBaselineToTopHeight());
            propertyReader.readObject(this.mFontFeatureSettingsId, textView.getFontFeatureSettings());
            propertyReader.readBoolean(this.mFreezesTextId, textView.getFreezesText());
            propertyReader.readGravity(this.mGravityId, textView.getGravity());
            propertyReader.readObject(this.mHintId, textView.getHint());
            propertyReader.readIntEnum(this.mHyphenationFrequencyId, textView.getHyphenationFrequency());
            propertyReader.readInt(this.mImeActionIdId, textView.getImeActionId());
            propertyReader.readObject(this.mImeActionLabelId, textView.getImeActionLabel());
            propertyReader.readIntFlag(this.mImeOptionsId, textView.getImeOptions());
            propertyReader.readBoolean(this.mIncludeFontPaddingId, textView.getIncludeFontPadding());
            propertyReader.readIntFlag(this.mInputTypeId, textView.getInputType());
            propertyReader.readIntEnum(this.mJustificationModeId, textView.getJustificationMode());
            propertyReader.readInt(this.mLastBaselineToBottomHeightId, textView.getLastBaselineToBottomHeight());
            propertyReader.readFloat(this.mLetterSpacingId, textView.getLetterSpacing());
            propertyReader.readInt(this.mLineHeightId, textView.getLineHeight());
            propertyReader.readFloat(this.mLineSpacingExtraId, textView.getLineSpacingExtra());
            propertyReader.readFloat(this.mLineSpacingMultiplierId, textView.getLineSpacingMultiplier());
            propertyReader.readBoolean(this.mLinksClickableId, textView.getLinksClickable());
            propertyReader.readInt(this.mMarqueeRepeatLimitId, textView.getMarqueeRepeatLimit());
            propertyReader.readInt(this.mMaxEmsId, textView.getMaxEms());
            propertyReader.readInt(this.mMaxHeightId, textView.getMaxHeight());
            propertyReader.readInt(this.mMaxLinesId, textView.getMaxLines());
            propertyReader.readInt(this.mMaxWidthId, textView.getMaxWidth());
            propertyReader.readInt(this.mMinEmsId, textView.getMinEms());
            propertyReader.readInt(this.mMinLinesId, textView.getMinLines());
            propertyReader.readInt(this.mMinWidthId, textView.getMinWidth());
            propertyReader.readObject(this.mPrivateImeOptionsId, textView.getPrivateImeOptions());
            propertyReader.readBoolean(this.mScrollHorizontallyId, textView.isHorizontallyScrollable());
            propertyReader.readColor(this.mShadowColorId, textView.getShadowColor());
            propertyReader.readFloat(this.mShadowDxId, textView.getShadowDx());
            propertyReader.readFloat(this.mShadowDyId, textView.getShadowDy());
            propertyReader.readFloat(this.mShadowRadiusId, textView.getShadowRadius());
            propertyReader.readBoolean(this.mSingleLineId, textView.isSingleLine());
            propertyReader.readObject(this.mTextId, textView.getText());
            propertyReader.readBoolean(this.mTextAllCapsId, textView.isAllCaps());
            propertyReader.readObject(this.mTextColorId, textView.getTextColors());
            propertyReader.readColor(this.mTextColorHighlightId, textView.getHighlightColor());
            propertyReader.readObject(this.mTextColorHintId, textView.getHintTextColors());
            propertyReader.readObject(this.mTextColorLinkId, textView.getLinkTextColors());
            propertyReader.readBoolean(this.mTextIsSelectableId, textView.isTextSelectable());
            propertyReader.readFloat(this.mTextScaleXId, textView.getTextScaleX());
            propertyReader.readFloat(this.mTextSizeId, textView.getTextSize());
            propertyReader.readObject(this.mTypefaceId, textView.getTypeface());
        }
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sAppearanceValues = sparseIntArray;
        sparseIntArray.put(6, 4);
        sparseIntArray.put(99, 22);
        sparseIntArray.put(100, 23);
        sparseIntArray.put(5, 3);
        sparseIntArray.put(7, 5);
        sparseIntArray.put(8, 6);
        sparseIntArray.put(2, 0);
        sparseIntArray.put(96, 19);
        sparseIntArray.put(3, 1);
        sparseIntArray.put(75, 12);
        sparseIntArray.put(4, 2);
        sparseIntArray.put(95, 18);
        sparseIntArray.put(72, 11);
        sparseIntArray.put(36, 7);
        sparseIntArray.put(37, 8);
        sparseIntArray.put(38, 9);
        sparseIntArray.put(39, 10);
        sparseIntArray.put(76, 13);
        sparseIntArray.put(91, 17);
        sparseIntArray.put(77, 14);
        sparseIntArray.put(78, 15);
        sparseIntArray.put(90, 16);
        sparseIntArray.put(97, 20);
        sparseIntArray.put(98, 21);
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
            this.mIsRtlCompatibilityMode = context.getApplicationInfo().targetSdkVersion < 17 || !context.getApplicationInfo().hasRtlSupport();
            this.mOverride = false;
        }

        public boolean hasMetadata() {
            return this.mDrawablePadding != 0 || this.mHasTintMode || this.mHasTint;
        }

        public boolean resolveWithLayoutDirection(int i) {
            Drawable[] drawableArr = this.mShowing;
            Drawable drawable = drawableArr[0];
            Drawable drawable2 = drawableArr[2];
            Drawable drawable3 = this.mDrawableLeftInitial;
            drawableArr[0] = drawable3;
            Drawable drawable4 = this.mDrawableRightInitial;
            drawableArr[2] = drawable4;
            if (this.mIsRtlCompatibilityMode) {
                Drawable drawable5 = this.mDrawableStart;
                if (drawable5 != null && drawable3 == null) {
                    drawableArr[0] = drawable5;
                    this.mDrawableSizeLeft = this.mDrawableSizeStart;
                    this.mDrawableHeightLeft = this.mDrawableHeightStart;
                }
                Drawable drawable6 = this.mDrawableEnd;
                if (drawable6 != null && drawable4 == null) {
                    drawableArr[2] = drawable6;
                    this.mDrawableSizeRight = this.mDrawableSizeEnd;
                    this.mDrawableHeightRight = this.mDrawableHeightEnd;
                }
            } else if (i == 1) {
                if (this.mOverride) {
                    drawableArr[2] = this.mDrawableStart;
                    this.mDrawableSizeRight = this.mDrawableSizeStart;
                    this.mDrawableHeightRight = this.mDrawableHeightStart;
                    drawableArr[0] = this.mDrawableEnd;
                    this.mDrawableSizeLeft = this.mDrawableSizeEnd;
                    this.mDrawableHeightLeft = this.mDrawableHeightEnd;
                }
            } else if (this.mOverride) {
                drawableArr[0] = this.mDrawableStart;
                this.mDrawableSizeLeft = this.mDrawableSizeStart;
                this.mDrawableHeightLeft = this.mDrawableHeightStart;
                drawableArr[2] = this.mDrawableEnd;
                this.mDrawableSizeRight = this.mDrawableSizeEnd;
                this.mDrawableHeightRight = this.mDrawableHeightEnd;
            }
            applyErrorDrawableIfNeeded(i);
            Drawable[] drawableArr2 = this.mShowing;
            return (drawableArr2[0] == drawable && drawableArr2[2] == drawable2) ? false : true;
        }

        public void setErrorDrawable(Drawable drawable, TextView textView) {
            Drawable drawable2 = this.mDrawableError;
            if (drawable2 != drawable && drawable2 != null) {
                drawable2.setCallback(null);
            }
            this.mDrawableError = drawable;
            if (drawable != null) {
                Rect rect = this.mCompoundRect;
                this.mDrawableError.setState(textView.getDrawableState());
                this.mDrawableError.copyBounds(rect);
                this.mDrawableError.setCallback(textView);
                this.mDrawableSizeError = rect.width();
                this.mDrawableHeightError = rect.height();
                return;
            }
            this.mDrawableHeightError = 0;
            this.mDrawableSizeError = 0;
        }

        private void applyErrorDrawableIfNeeded(int i) {
            int i2 = this.mDrawableSaved;
            if (i2 == 0) {
                this.mShowing[2] = this.mDrawableTemp;
                this.mDrawableSizeRight = this.mDrawableSizeTemp;
                this.mDrawableHeightRight = this.mDrawableHeightTemp;
            } else if (i2 == 1) {
                this.mShowing[0] = this.mDrawableTemp;
                this.mDrawableSizeLeft = this.mDrawableSizeTemp;
                this.mDrawableHeightLeft = this.mDrawableHeightTemp;
            }
            Drawable drawable = this.mDrawableError;
            if (drawable != null) {
                if (i == 1) {
                    this.mDrawableSaved = 1;
                    Drawable[] drawableArr = this.mShowing;
                    this.mDrawableTemp = drawableArr[0];
                    this.mDrawableSizeTemp = this.mDrawableSizeLeft;
                    this.mDrawableHeightTemp = this.mDrawableHeightLeft;
                    drawableArr[0] = drawable;
                    this.mDrawableSizeLeft = this.mDrawableSizeError;
                    this.mDrawableHeightLeft = this.mDrawableHeightError;
                    return;
                }
                this.mDrawableSaved = 0;
                Drawable[] drawableArr2 = this.mShowing;
                this.mDrawableTemp = drawableArr2[2];
                this.mDrawableSizeTemp = this.mDrawableSizeRight;
                this.mDrawableHeightTemp = this.mDrawableHeightRight;
                drawableArr2[2] = drawable;
                this.mDrawableSizeRight = this.mDrawableSizeError;
                this.mDrawableHeightRight = this.mDrawableHeightError;
            }
        }
    }

    public TextView(Context context) {
        this(context, null);
    }

    public TextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842884);
    }

    public TextView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to find 'out' block for switch in B:43:0x0285. Please report as an issue. */
    /* JADX WARN: Failed to find 'out' block for switch in B:44:0x0288. Please report as an issue. */
    /* JADX WARN: Failed to find 'out' block for switch in B:45:0x028b. Please report as an issue. */
    /* JADX WARN: Removed duplicated region for block: B:267:0x0bf8  */
    /* JADX WARN: Removed duplicated region for block: B:269:0x0bfd  */
    /* JADX WARN: Removed duplicated region for block: B:274:0x0c0f A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:277:0x0c1b  */
    /* JADX WARN: Removed duplicated region for block: B:279:0x0c1f  */
    /* JADX WARN: Removed duplicated region for block: B:281:0x0c28  */
    /* JADX WARN: Removed duplicated region for block: B:282:0x0c32  */
    /* JADX WARN: Removed duplicated region for block: B:284:0x0c35  */
    /* JADX WARN: Removed duplicated region for block: B:287:0x0c54  */
    /* JADX WARN: Removed duplicated region for block: B:294:0x0c61  */
    /* JADX WARN: Removed duplicated region for block: B:307:0x0c93  */
    /* JADX WARN: Removed duplicated region for block: B:309:0x0c9a A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:314:0x0ca3  */
    /* JADX WARN: Removed duplicated region for block: B:317:0x0cc9  */
    /* JADX WARN: Removed duplicated region for block: B:320:0x0cd4 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:324:0x0ce5  */
    /* JADX WARN: Removed duplicated region for block: B:327:0x0ceb  */
    /* JADX WARN: Removed duplicated region for block: B:329:0x0cf8  */
    /* JADX WARN: Removed duplicated region for block: B:335:0x0d19  */
    /* JADX WARN: Removed duplicated region for block: B:338:0x0d1f  */
    /* JADX WARN: Removed duplicated region for block: B:340:0x0d23  */
    /* JADX WARN: Removed duplicated region for block: B:343:0x0d2a  */
    /* JADX WARN: Removed duplicated region for block: B:346:0x0d3d  */
    /* JADX WARN: Removed duplicated region for block: B:350:0x0d46  */
    /* JADX WARN: Removed duplicated region for block: B:352:0x0d49  */
    /* JADX WARN: Removed duplicated region for block: B:356:0x0d52  */
    /* JADX WARN: Removed duplicated region for block: B:358:0x0d55  */
    /* JADX WARN: Removed duplicated region for block: B:362:0x0d5e  */
    /* JADX WARN: Removed duplicated region for block: B:365:0x0d67  */
    /* JADX WARN: Removed duplicated region for block: B:368:0x0d73  */
    /* JADX WARN: Removed duplicated region for block: B:393:0x0dc6  */
    /* JADX WARN: Removed duplicated region for block: B:396:0x0dd3  */
    /* JADX WARN: Removed duplicated region for block: B:399:0x0dda  */
    /* JADX WARN: Removed duplicated region for block: B:402:0x0de4  */
    /* JADX WARN: Removed duplicated region for block: B:405:0x0ded  */
    /* JADX WARN: Removed duplicated region for block: B:422:0x0e2a  */
    /* JADX WARN: Removed duplicated region for block: B:425:0x0e34  */
    /* JADX WARN: Removed duplicated region for block: B:428:0x0e3b  */
    /* JADX WARN: Removed duplicated region for block: B:431:0x0e42  */
    /* JADX WARN: Removed duplicated region for block: B:434:0x0e4d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public TextView(Context context, AttributeSet attributeSet, int i, int i2) throws Resources.NotFoundException, ClassNotFoundException {
        TextView textView;
        Context context2;
        AttributeSet attributeSet2;
        int i3;
        int i4;
        TypedArray typedArrayObtainStyledAttributes;
        boolean z;
        BufferType bufferType;
        Editor editor;
        ColorStateList colorStateList;
        boolean z2;
        boolean z3;
        int i5;
        InputFilter.LengthFilter lengthFilter;
        CharSequence charSequence;
        int focusable;
        int indexCount;
        int i6;
        Editor editor2;
        int i7;
        int i8;
        float f;
        int i9;
        TextKeyListener.Capitalize capitalize;
        int i10;
        int i11;
        int i12;
        int i13;
        int i14;
        BlendMode blendMode;
        boolean z4;
        boolean z5;
        boolean z6;
        String str;
        boolean z7;
        String str2;
        boolean z8;
        boolean z9;
        super(context, attributeSet, i, i2);
        String str3 = "Failure reading input extras";
        String str4 = LOG_TAG;
        this.mRestrictionPolicy = null;
        this.mSingleLineLengthFilter = null;
        this.mDisableWritingToolkitMenu = false;
        this.mEditableFactory = Editable.Factory.getInstance();
        this.mSpannableFactory = Spannable.Factory.getInstance();
        this.mCursorVisibleFromAttr = true;
        this.mIsThemeDeviceDefault = false;
        this.mChangedSelectionBySIP = false;
        this.mSemClipboardManager = null;
        this.mKeycodeDpadCenterStatus = false;
        this.mButtonShapePaint = null;
        this.mButtonShapeRect = null;
        this.mButtonShapeTextColor = null;
        this.mIsNightMode = false;
        this.mIsButtonShapeTarget = false;
        this.mButtonShapeSettingEnabled = false;
        this.mCursorThicknessScale = 1.0f;
        this.mDrawTextStrikeAnimator = null;
        this.mDrawStrikeAnimationValue = 0.0f;
        this.mStrikeThroughPaint = null;
        this.mTextStrikeThroughEnabled = false;
        this.mLineIsDrawed = false;
        this.mMarqueeRepeatLimit = 3;
        this.mLastLayoutDirection = -1;
        this.mMarqueeFadeMode = 0;
        this.mBufferType = BufferType.NORMAL;
        this.mDisplayText = null;
        this.mUseDisplayText = false;
        this.mSkipUpdateDisplayText = false;
        this.mhasMultiSelection = false;
        this.mEnableMultiSelection = true;
        this.mEnableLinkPreview = false;
        this.mCanTextMultiSelection = false;
        this.mMultiHighlightColor = -1728022343;
        this.mIsShowingLinkPreview = false;
        this.HOVER_INTERVAL = 300;
        this.mHoveredSpan = null;
        this.mHoverEnterTime = -1L;
        this.mHoverExitTime = -1L;
        this.TOUCH_DELTA = 12.0f;
        this.mWordIteratorForMultiSelection = null;
        this.mPenSelectionController = null;
        this.mLocalesChanged = false;
        this.mTextSizeUnit = -1;
        this.mLineBreakStyle = 0;
        this.mLineBreakWordStyle = 0;
        this.mTextEffect = null;
        this.mFontFamily = Typeface.DEFAULT_FAMILY;
        this.mListenerChanged = false;
        this.mGravity = 8388659;
        this.mLinksClickable = true;
        this.mSpacingMult = 1.0f;
        this.mSpacingAdd = 0.0f;
        this.mMaximum = Integer.MAX_VALUE;
        this.mMaxMode = 1;
        this.mMinimum = 0;
        this.mMinMode = 1;
        this.mOldMaximum = Integer.MAX_VALUE;
        this.mOldMaxMode = 1;
        this.mMaxWidth = Integer.MAX_VALUE;
        this.mMaxWidthMode = 2;
        this.mMinWidth = 0;
        this.mMinWidthMode = 2;
        this.mDesiredHeightAtMeasure = -1;
        this.mIncludePad = true;
        this.mDeferScroll = -1;
        this.mFilters = NO_FILTERS;
        this.mHighlightColor = 1714664933;
        this.mHighlightPathBogus = true;
        this.mSearchResultHighlights = null;
        this.mSearchResultHighlightPaint = null;
        this.mFocusedSearchResultHighlightPaint = null;
        this.mFocusedSearchResultHighlightColor = -27086;
        this.mSearchResultHighlightColor = -256;
        this.mFocusedSearchResultIndex = -1;
        this.mGesturePreviewHighlightStart = -1;
        this.mGesturePreviewHighlightEnd = -1;
        this.mPathRecyclePool = new ArrayList();
        this.mHighlightPathsBogus = true;
        this.mPrimePointerId = -1;
        this.mDeviceProvisionedState = 0;
        this.mLastInputSource = 4098;
        this.mAutoSizeTextType = 0;
        this.mNeedsAutoSizeText = false;
        this.mAutoSizeStepGranularityInPx = -1.0f;
        this.mAutoSizeMinTextSizeInPx = -1.0f;
        this.mAutoSizeMaxTextSizeInPx = -1.0f;
        this.mAutoSizeTextSizesInPx = EmptyArray.INT;
        this.mHasPresetAutoSizeValues = false;
        this.mTextSetFromXmlOrResourceId = false;
        this.mTextId = 0;
        this.mHintId = 0;
        this.mIsTouchDown = false;
        this.mMultiSelectPopupWindow = MultiSelectPopupWindow.getInstance();
        if (getImportantForAutofill() == 0) {
            setImportantForAutofill(1);
        }
        if (getImportantForContentCapture() == 0) {
            setImportantForContentCapture(1);
        }
        this.mActionModeFlags = 241664;
        setTextInternal("");
        Resources resources = getResources();
        CompatibilityInfo compatibilityInfo = resources.getCompatibilityInfo();
        TextPaint textPaint = new TextPaint(1);
        this.mTextPaint = textPaint;
        textPaint.density = resources.getDisplayMetrics().density;
        textPaint.setCompatibilityScaling(compatibilityInfo.applicationScale);
        Paint paint = new Paint(1);
        this.mHighlightPaint = paint;
        paint.setCompatibilityScaling(compatibilityInfo.applicationScale);
        if (ViewRune.WIDGET_PEN_SUPPORTED) {
            Paint paint2 = new Paint(1);
            this.mMultiHighlightPaint = paint2;
            paint2.setCompatibilityScaling(compatibilityInfo.applicationScale);
            this.TOUCH_DELTA = textPaint.density * 12.0f;
        }
        this.mMovement = getDefaultMovementMethod();
        this.mTransformation = null;
        TextAppearanceAttributes textAppearanceAttributes = new TextAppearanceAttributes();
        textAppearanceAttributes.mTextColor = ColorStateList.valueOf(-16777216);
        textAppearanceAttributes.mTextSize = 15;
        this.mBreakStrategy = 0;
        this.mHyphenationFrequency = 0;
        this.mJustificationMode = 0;
        this.mLastOrientation = getResources().getConfiguration().orientation;
        Resources.Theme theme = context.getTheme();
        TypedArray typedArrayObtainStyledAttributes2 = theme.obtainStyledAttributes(new int[]{R.attr.parentIsDeviceDefault});
        if (typedArrayObtainStyledAttributes2.getBoolean(0, true)) {
            this.mIsThemeDeviceDefault = true;
        }
        typedArrayObtainStyledAttributes2.recycle();
        if (this.mIsThemeDeviceDefault) {
            textPaint.setFlags(textPaint.getFlags() | 192);
        }
        TypedArray typedArrayObtainStyledAttributes3 = theme.obtainStyledAttributes(attributeSet, R.styleable.TextViewAppearance, i, i2);
        saveAttributeDataForStyleable(context, R.styleable.TextViewAppearance, attributeSet, typedArrayObtainStyledAttributes3, i, i2);
        int resourceId = typedArrayObtainStyledAttributes3.getResourceId(0, -1);
        typedArrayObtainStyledAttributes3.recycle();
        if (resourceId != -1) {
            typedArrayObtainStyledAttributes = theme.obtainStyledAttributes(resourceId, R.styleable.TextAppearance);
            textView = this;
            context2 = context;
            attributeSet2 = attributeSet;
            i3 = i;
            i4 = i2;
            textView.saveAttributeDataForStyleable(context2, R.styleable.TextAppearance, null, typedArrayObtainStyledAttributes, 0, resourceId);
        } else {
            textView = this;
            context2 = context;
            attributeSet2 = attributeSet;
            i3 = i;
            i4 = i2;
            typedArrayObtainStyledAttributes = null;
        }
        if (typedArrayObtainStyledAttributes != null) {
            textView.readTextAppearance(context2, typedArrayObtainStyledAttributes, textAppearanceAttributes, false);
            textAppearanceAttributes.mFontFamilyExplicit = false;
            typedArrayObtainStyledAttributes.recycle();
        }
        boolean defaultEditable = textView.getDefaultEditable();
        TypedArray typedArrayObtainStyledAttributes4 = theme.obtainStyledAttributes(attributeSet2, R.styleable.TextView, i3, i4);
        textView.saveAttributeDataForStyleable(context2, R.styleable.TextView, attributeSet2, typedArrayObtainStyledAttributes4, i3, i4);
        textView.readTextAppearance(context2, typedArrayObtainStyledAttributes4, textAppearanceAttributes, true);
        int indexCount2 = typedArrayObtainStyledAttributes4.getIndexCount();
        int i15 = 0;
        int i16 = 0;
        int i17 = 0;
        boolean z10 = false;
        boolean z11 = false;
        boolean z12 = false;
        int i18 = 0;
        boolean z13 = false;
        boolean z14 = false;
        int dimensionPixelSize = 0;
        boolean z15 = false;
        boolean z16 = false;
        CharSequence text = "";
        boolean z17 = defaultEditable;
        int i19 = -1;
        int i20 = -1;
        int i21 = -1;
        float fApplyDimension = -1.0f;
        int dimensionPixelSize2 = -1;
        int dimensionPixelSize3 = -1;
        int complexUnit = -1;
        float dimension = -1.0f;
        float dimensionPixelSize4 = -1.0f;
        float dimension2 = -1.0f;
        CharSequence text2 = null;
        CharSequence text3 = null;
        BlendMode blendMode2 = null;
        Drawable drawable = null;
        Drawable drawable2 = null;
        Drawable drawable3 = null;
        Drawable drawable4 = null;
        Drawable drawable5 = null;
        Drawable drawable6 = null;
        ColorStateList colorStateList2 = null;
        CharSequence text4 = null;
        while (i15 < indexCount2) {
            int index = typedArrayObtainStyledAttributes4.getIndex(i15);
            if (index == 0) {
                i11 = indexCount2;
                i12 = i15;
                i13 = i17;
                i14 = dimensionPixelSize;
                blendMode = blendMode2;
                z4 = z12;
                z5 = z11;
                z6 = z17;
                str = str3;
                z7 = z14;
                str2 = str4;
                z8 = z10;
                textView.setEnabled(typedArrayObtainStyledAttributes4.getBoolean(index, textView.isEnabled()));
            } else if (index == 67) {
                i11 = indexCount2;
                i12 = i15;
                i13 = i17;
                i14 = dimensionPixelSize;
                blendMode = blendMode2;
                z4 = z12;
                z5 = z11;
                z6 = z17;
                str = str3;
                z7 = z14;
                str2 = str4;
                z8 = z10;
                textView.setTextIsSelectable(typedArrayObtainStyledAttributes4.getBoolean(index, false));
            } else if (index == 70) {
                i11 = indexCount2;
                i12 = i15;
                i13 = i17;
                i14 = dimensionPixelSize;
                blendMode = blendMode2;
                z4 = z12;
                z5 = z11;
                z6 = z17;
                str = str3;
                z7 = z14;
                str2 = str4;
                z8 = z10;
                textView.mCursorDrawableRes = typedArrayObtainStyledAttributes4.getResourceId(index, 0);
            } else if (index != 71) {
                if (index == 73) {
                    i11 = indexCount2;
                    i12 = i15;
                    i14 = dimensionPixelSize;
                    z4 = z12;
                    z5 = z11;
                    z6 = z17;
                    str = str3;
                    z7 = z14;
                    str2 = str4;
                    drawable5 = typedArrayObtainStyledAttributes4.getDrawable(index);
                } else if (index == 74) {
                    i11 = indexCount2;
                    i12 = i15;
                    i14 = dimensionPixelSize;
                    z4 = z12;
                    z5 = z11;
                    z6 = z17;
                    str = str3;
                    z7 = z14;
                    str2 = str4;
                    drawable6 = typedArrayObtainStyledAttributes4.getDrawable(index);
                } else if (index == 97) {
                    i11 = indexCount2;
                    i12 = i15;
                    i13 = i17;
                    i14 = dimensionPixelSize;
                    blendMode = blendMode2;
                    z4 = z12;
                    z5 = z11;
                    z6 = z17;
                    str = str3;
                    z7 = z14;
                    str2 = str4;
                    z8 = z10;
                    textView.mLineBreakStyle = typedArrayObtainStyledAttributes4.getInt(index, 0);
                } else if (index != 98) {
                    switch (index) {
                        case 9:
                            i11 = indexCount2;
                            i12 = i15;
                            i14 = dimensionPixelSize;
                            z4 = z12;
                            z5 = z11;
                            z6 = z17;
                            str = str3;
                            z7 = z14;
                            str2 = str4;
                            i19 = typedArrayObtainStyledAttributes4.getInt(index, i19);
                            break;
                        case 10:
                            i11 = indexCount2;
                            i12 = i15;
                            i13 = i17;
                            i14 = dimensionPixelSize;
                            blendMode = blendMode2;
                            z4 = z12;
                            z5 = z11;
                            z6 = z17;
                            str = str3;
                            z7 = z14;
                            str2 = str4;
                            z8 = z10;
                            textView.setGravity(typedArrayObtainStyledAttributes4.getInt(index, -1));
                            break;
                        case 11:
                            i11 = indexCount2;
                            i12 = i15;
                            i13 = i17;
                            i14 = dimensionPixelSize;
                            blendMode = blendMode2;
                            z4 = z12;
                            z5 = z11;
                            z6 = z17;
                            str = str3;
                            z7 = z14;
                            str2 = str4;
                            z8 = z10;
                            textView.mAutoLinkMask = typedArrayObtainStyledAttributes4.getInt(index, 0);
                            break;
                        case 12:
                            i11 = indexCount2;
                            i12 = i15;
                            i13 = i17;
                            i14 = dimensionPixelSize;
                            blendMode = blendMode2;
                            z4 = z12;
                            z5 = z11;
                            z6 = z17;
                            str = str3;
                            z7 = z14;
                            str2 = str4;
                            z8 = z10;
                            textView.mLinksClickable = typedArrayObtainStyledAttributes4.getBoolean(index, true);
                            break;
                        case 13:
                            i11 = indexCount2;
                            i12 = i15;
                            i13 = i17;
                            i14 = dimensionPixelSize;
                            blendMode = blendMode2;
                            z4 = z12;
                            z5 = z11;
                            z6 = z17;
                            str = str3;
                            z7 = z14;
                            str2 = str4;
                            z8 = z10;
                            textView.setMaxWidth(typedArrayObtainStyledAttributes4.getDimensionPixelSize(index, -1));
                            break;
                        case 14:
                            i11 = indexCount2;
                            i12 = i15;
                            i13 = i17;
                            i14 = dimensionPixelSize;
                            blendMode = blendMode2;
                            z4 = z12;
                            z5 = z11;
                            z6 = z17;
                            str = str3;
                            z7 = z14;
                            str2 = str4;
                            z8 = z10;
                            textView.setMaxHeight(typedArrayObtainStyledAttributes4.getDimensionPixelSize(index, -1));
                            break;
                        case 15:
                            i11 = indexCount2;
                            i12 = i15;
                            i13 = i17;
                            i14 = dimensionPixelSize;
                            blendMode = blendMode2;
                            z4 = z12;
                            z5 = z11;
                            z6 = z17;
                            str = str3;
                            z7 = z14;
                            str2 = str4;
                            z8 = z10;
                            textView.setMinWidth(typedArrayObtainStyledAttributes4.getDimensionPixelSize(index, -1));
                            break;
                        case 16:
                            i11 = indexCount2;
                            i12 = i15;
                            i13 = i17;
                            i14 = dimensionPixelSize;
                            blendMode = blendMode2;
                            z4 = z12;
                            z5 = z11;
                            z6 = z17;
                            str = str3;
                            z7 = z14;
                            str2 = str4;
                            z8 = z10;
                            textView.setMinHeight(typedArrayObtainStyledAttributes4.getDimensionPixelSize(index, -1));
                            break;
                        case 17:
                            i11 = indexCount2;
                            i12 = i15;
                            i14 = dimensionPixelSize;
                            z4 = z12;
                            z5 = z11;
                            z6 = z17;
                            str = str3;
                            z7 = z14;
                            str2 = str4;
                            i17 = typedArrayObtainStyledAttributes4.getInt(index, i17);
                            break;
                        case 18:
                            i11 = indexCount2;
                            i12 = i15;
                            i14 = dimensionPixelSize;
                            boolean z18 = z12;
                            boolean z19 = z11;
                            boolean z20 = z17;
                            str = str3;
                            z7 = z14;
                            str2 = str4;
                            textView.mTextId = typedArrayObtainStyledAttributes4.getResourceId(index, 0);
                            text = typedArrayObtainStyledAttributes4.getText(index);
                            z9 = z20;
                            z11 = z19;
                            z12 = z18;
                            z16 = true;
                            dimensionPixelSize = i14;
                            break;
                        case 19:
                            i11 = indexCount2;
                            i12 = i15;
                            i14 = dimensionPixelSize;
                            z4 = z12;
                            z5 = z11;
                            z6 = z17;
                            str = str3;
                            z7 = z14;
                            str2 = str4;
                            textView.mHintId = typedArrayObtainStyledAttributes4.getResourceId(index, 0);
                            text4 = typedArrayObtainStyledAttributes4.getText(index);
                            break;
                        case 20:
                            i11 = indexCount2;
                            i12 = i15;
                            i14 = dimensionPixelSize;
                            blendMode = blendMode2;
                            z4 = z12;
                            z5 = z11;
                            z6 = z17;
                            str = str3;
                            z7 = z14;
                            str2 = str4;
                            z8 = z10;
                            textView.setTextScaleX(typedArrayObtainStyledAttributes4.getFloat(index, 1.0f));
                            i13 = i17;
                            break;
                        case 21:
                            i11 = indexCount2;
                            i12 = i15;
                            i14 = dimensionPixelSize;
                            blendMode = blendMode2;
                            z4 = z12;
                            z5 = z11;
                            z6 = z17;
                            str = str3;
                            z7 = z14;
                            str2 = str4;
                            z8 = z10;
                            if (!typedArrayObtainStyledAttributes4.getBoolean(index, true)) {
                                textView.setCursorVisible(false);
                            }
                            i13 = i17;
                            break;
                        case 22:
                            i11 = indexCount2;
                            i12 = i15;
                            i14 = dimensionPixelSize;
                            blendMode = blendMode2;
                            z4 = z12;
                            z5 = z11;
                            z6 = z17;
                            str = str3;
                            z7 = z14;
                            str2 = str4;
                            z8 = z10;
                            textView.setMaxLines(typedArrayObtainStyledAttributes4.getInt(index, -1));
                            i13 = i17;
                            break;
                        case 23:
                            i11 = indexCount2;
                            i12 = i15;
                            i14 = dimensionPixelSize;
                            blendMode = blendMode2;
                            z4 = z12;
                            z5 = z11;
                            z6 = z17;
                            str = str3;
                            z7 = z14;
                            str2 = str4;
                            z8 = z10;
                            textView.setLines(typedArrayObtainStyledAttributes4.getInt(index, -1));
                            i13 = i17;
                            break;
                        case 24:
                            i11 = indexCount2;
                            i12 = i15;
                            i14 = dimensionPixelSize;
                            blendMode = blendMode2;
                            z4 = z12;
                            z5 = z11;
                            z6 = z17;
                            str = str3;
                            z7 = z14;
                            str2 = str4;
                            z8 = z10;
                            textView.setHeight(typedArrayObtainStyledAttributes4.getDimensionPixelSize(index, -1));
                            i13 = i17;
                            break;
                        case 25:
                            i11 = indexCount2;
                            i12 = i15;
                            i14 = dimensionPixelSize;
                            blendMode = blendMode2;
                            z4 = z12;
                            z5 = z11;
                            z6 = z17;
                            str = str3;
                            z7 = z14;
                            str2 = str4;
                            z8 = z10;
                            textView.setMinLines(typedArrayObtainStyledAttributes4.getInt(index, -1));
                            i13 = i17;
                            break;
                        case 26:
                            i11 = indexCount2;
                            i12 = i15;
                            i14 = dimensionPixelSize;
                            blendMode = blendMode2;
                            z4 = z12;
                            z5 = z11;
                            z6 = z17;
                            str = str3;
                            z7 = z14;
                            str2 = str4;
                            z8 = z10;
                            textView.setMaxEms(typedArrayObtainStyledAttributes4.getInt(index, -1));
                            i13 = i17;
                            break;
                        case 27:
                            i11 = indexCount2;
                            i12 = i15;
                            i14 = dimensionPixelSize;
                            blendMode = blendMode2;
                            z4 = z12;
                            z5 = z11;
                            z6 = z17;
                            str = str3;
                            z7 = z14;
                            str2 = str4;
                            z8 = z10;
                            textView.setEms(typedArrayObtainStyledAttributes4.getInt(index, -1));
                            i13 = i17;
                            break;
                        case 28:
                            i11 = indexCount2;
                            i12 = i15;
                            i14 = dimensionPixelSize;
                            blendMode = blendMode2;
                            z4 = z12;
                            z5 = z11;
                            z6 = z17;
                            str = str3;
                            z7 = z14;
                            str2 = str4;
                            z8 = z10;
                            textView.setWidth(typedArrayObtainStyledAttributes4.getDimensionPixelSize(index, -1));
                            i13 = i17;
                            break;
                        case 29:
                            i11 = indexCount2;
                            i12 = i15;
                            i14 = dimensionPixelSize;
                            blendMode = blendMode2;
                            z4 = z12;
                            z5 = z11;
                            z6 = z17;
                            str = str3;
                            z7 = z14;
                            str2 = str4;
                            z8 = z10;
                            textView.setMinEms(typedArrayObtainStyledAttributes4.getInt(index, -1));
                            i13 = i17;
                            break;
                        case 30:
                            i11 = indexCount2;
                            i12 = i15;
                            i14 = dimensionPixelSize;
                            blendMode = blendMode2;
                            z4 = z12;
                            z5 = z11;
                            z6 = z17;
                            str = str3;
                            z7 = z14;
                            str2 = str4;
                            z8 = z10;
                            if (typedArrayObtainStyledAttributes4.getBoolean(index, false)) {
                                textView.setHorizontallyScrolling(true);
                            }
                            i13 = i17;
                            break;
                        case 31:
                            i11 = indexCount2;
                            i12 = i15;
                            z9 = z17;
                            i14 = dimensionPixelSize;
                            str = str3;
                            z4 = z12;
                            z7 = z14;
                            str2 = str4;
                            z10 = typedArrayObtainStyledAttributes4.getBoolean(index, z10);
                            z12 = z4;
                            dimensionPixelSize = i14;
                            break;
                        case 32:
                            i11 = indexCount2;
                            i12 = i15;
                            z9 = z17;
                            i14 = dimensionPixelSize;
                            str = str3;
                            z7 = z14;
                            str2 = str4;
                            z11 = typedArrayObtainStyledAttributes4.getBoolean(index, z11);
                            dimensionPixelSize = i14;
                            break;
                        case 33:
                            i11 = indexCount2;
                            i12 = i15;
                            z9 = z17;
                            str = str3;
                            z7 = z14;
                            str2 = str4;
                            z12 = typedArrayObtainStyledAttributes4.getBoolean(index, z12);
                            break;
                        case 34:
                            i11 = indexCount2;
                            i12 = i15;
                            boolean z21 = z17;
                            i14 = dimensionPixelSize;
                            blendMode = blendMode2;
                            str = str3;
                            z7 = z14;
                            str2 = str4;
                            if (!typedArrayObtainStyledAttributes4.getBoolean(index, true)) {
                                textView.setIncludeFontPadding(false);
                            }
                            i13 = i17;
                            z8 = z10;
                            z4 = z12;
                            z5 = z11;
                            z6 = z21;
                            break;
                        case 35:
                            i11 = indexCount2;
                            i12 = i15;
                            z9 = z17;
                            str = str3;
                            z7 = z14;
                            str2 = str4;
                            i21 = typedArrayObtainStyledAttributes4.getInt(index, -1);
                            break;
                        default:
                            switch (index) {
                                case 40:
                                    i11 = indexCount2;
                                    i12 = i15;
                                    z9 = z17;
                                    str = str3;
                                    z7 = z14;
                                    str2 = str4;
                                    i18 = typedArrayObtainStyledAttributes4.getInt(index, i18);
                                    break;
                                case 41:
                                    i11 = indexCount2;
                                    i12 = i15;
                                    z9 = z17;
                                    str = str3;
                                    z7 = z14;
                                    str2 = str4;
                                    text3 = typedArrayObtainStyledAttributes4.getText(index);
                                    break;
                                case 42:
                                    i11 = indexCount2;
                                    i12 = i15;
                                    z9 = z17;
                                    str = str3;
                                    z7 = z14;
                                    str2 = str4;
                                    z13 = typedArrayObtainStyledAttributes4.getBoolean(index, z13);
                                    break;
                                case 43:
                                    i11 = indexCount2;
                                    i12 = i15;
                                    z9 = z17;
                                    str = str3;
                                    z7 = z14;
                                    text2 = typedArrayObtainStyledAttributes4.getText(index);
                                    str2 = str4;
                                    break;
                                case 44:
                                    i11 = indexCount2;
                                    i12 = i15;
                                    z9 = z17;
                                    str = str3;
                                    z7 = z14;
                                    i20 = typedArrayObtainStyledAttributes4.getInt(index, i20);
                                    str2 = str4;
                                    break;
                                case 45:
                                    i11 = indexCount2;
                                    i12 = i15;
                                    z9 = z17;
                                    str = str3;
                                    z14 = typedArrayObtainStyledAttributes4.getBoolean(index, z14);
                                    z7 = z14;
                                    str2 = str4;
                                    break;
                                case 46:
                                    i11 = indexCount2;
                                    i12 = i15;
                                    z9 = typedArrayObtainStyledAttributes4.getBoolean(index, z17);
                                    str = str3;
                                    z7 = z14;
                                    str2 = str4;
                                    break;
                                case 47:
                                    i11 = indexCount2;
                                    i12 = i15;
                                    i14 = dimensionPixelSize;
                                    blendMode = blendMode2;
                                    textView.mFreezesText = typedArrayObtainStyledAttributes4.getBoolean(index, false);
                                    i13 = i17;
                                    z4 = z12;
                                    z5 = z11;
                                    z6 = z17;
                                    str = str3;
                                    z7 = z14;
                                    str2 = str4;
                                    z8 = z10;
                                    break;
                                case 48:
                                    i11 = indexCount2;
                                    i12 = i15;
                                    drawable2 = typedArrayObtainStyledAttributes4.getDrawable(index);
                                    z9 = z17;
                                    str = str3;
                                    z7 = z14;
                                    str2 = str4;
                                    break;
                                case 49:
                                    i11 = indexCount2;
                                    i12 = i15;
                                    drawable4 = typedArrayObtainStyledAttributes4.getDrawable(index);
                                    z9 = z17;
                                    str = str3;
                                    z7 = z14;
                                    str2 = str4;
                                    break;
                                case 50:
                                    i11 = indexCount2;
                                    i12 = i15;
                                    drawable = typedArrayObtainStyledAttributes4.getDrawable(index);
                                    z9 = z17;
                                    str = str3;
                                    z7 = z14;
                                    str2 = str4;
                                    break;
                                case 51:
                                    i11 = indexCount2;
                                    i12 = i15;
                                    drawable3 = typedArrayObtainStyledAttributes4.getDrawable(index);
                                    z9 = z17;
                                    str = str3;
                                    z7 = z14;
                                    str2 = str4;
                                    break;
                                case 52:
                                    i11 = indexCount2;
                                    i12 = i15;
                                    dimensionPixelSize = typedArrayObtainStyledAttributes4.getDimensionPixelSize(index, dimensionPixelSize);
                                    z9 = z17;
                                    str = str3;
                                    z7 = z14;
                                    str2 = str4;
                                    break;
                                case 53:
                                    i11 = indexCount2;
                                    i12 = i15;
                                    blendMode = blendMode2;
                                    textView.mSpacingAdd = typedArrayObtainStyledAttributes4.getDimensionPixelSize(index, (int) textView.mSpacingAdd);
                                    i13 = i17;
                                    i14 = dimensionPixelSize;
                                    z4 = z12;
                                    z5 = z11;
                                    z6 = z17;
                                    str = str3;
                                    z7 = z14;
                                    str2 = str4;
                                    z8 = z10;
                                    break;
                                case 54:
                                    i11 = indexCount2;
                                    i12 = i15;
                                    blendMode = blendMode2;
                                    textView.mSpacingMult = typedArrayObtainStyledAttributes4.getFloat(index, textView.mSpacingMult);
                                    i13 = i17;
                                    i14 = dimensionPixelSize;
                                    z4 = z12;
                                    z5 = z11;
                                    z6 = z17;
                                    str = str3;
                                    z7 = z14;
                                    str2 = str4;
                                    z8 = z10;
                                    break;
                                case 55:
                                    i11 = indexCount2;
                                    i12 = i15;
                                    blendMode = blendMode2;
                                    textView.setMarqueeRepeatLimit(typedArrayObtainStyledAttributes4.getInt(index, textView.mMarqueeRepeatLimit));
                                    i13 = i17;
                                    i14 = dimensionPixelSize;
                                    z4 = z12;
                                    z5 = z11;
                                    z6 = z17;
                                    str = str3;
                                    z7 = z14;
                                    str2 = str4;
                                    z8 = z10;
                                    break;
                                case 56:
                                    i11 = indexCount2;
                                    i12 = i15;
                                    i16 = typedArrayObtainStyledAttributes4.getInt(index, 0);
                                    z9 = z17;
                                    str = str3;
                                    z7 = z14;
                                    str2 = str4;
                                    break;
                                case 57:
                                    i11 = indexCount2;
                                    i12 = i15;
                                    blendMode = blendMode2;
                                    textView.setPrivateImeOptions(typedArrayObtainStyledAttributes4.getString(index));
                                    i13 = i17;
                                    i14 = dimensionPixelSize;
                                    z4 = z12;
                                    z5 = z11;
                                    z6 = z17;
                                    str = str3;
                                    z7 = z14;
                                    str2 = str4;
                                    z8 = z10;
                                    break;
                                case 58:
                                    i11 = indexCount2;
                                    i12 = i15;
                                    blendMode = blendMode2;
                                    try {
                                        textView.setInputExtras(typedArrayObtainStyledAttributes4.getResourceId(index, 0));
                                    } catch (IOException e) {
                                        Log.w(str4, str3, e);
                                    } catch (XmlPullParserException e2) {
                                        Log.w(str4, str3, e2);
                                    }
                                    i13 = i17;
                                    i14 = dimensionPixelSize;
                                    z4 = z12;
                                    z5 = z11;
                                    z6 = z17;
                                    str = str3;
                                    z7 = z14;
                                    str2 = str4;
                                    z8 = z10;
                                    break;
                                case 59:
                                    i11 = indexCount2;
                                    i12 = i15;
                                    blendMode = blendMode2;
                                    textView.createEditorIfNeeded();
                                    textView.mEditor.createInputContentTypeIfNeeded();
                                    textView.mEditor.mInputContentType.imeOptions = typedArrayObtainStyledAttributes4.getInt(index, textView.mEditor.mInputContentType.imeOptions);
                                    i13 = i17;
                                    i14 = dimensionPixelSize;
                                    z4 = z12;
                                    z5 = z11;
                                    z6 = z17;
                                    str = str3;
                                    z7 = z14;
                                    str2 = str4;
                                    z8 = z10;
                                    break;
                                case 60:
                                    i11 = indexCount2;
                                    i12 = i15;
                                    blendMode = blendMode2;
                                    textView.createEditorIfNeeded();
                                    textView.mEditor.createInputContentTypeIfNeeded();
                                    textView.mEditor.mInputContentType.imeActionLabel = typedArrayObtainStyledAttributes4.getText(index);
                                    i13 = i17;
                                    i14 = dimensionPixelSize;
                                    z4 = z12;
                                    z5 = z11;
                                    z6 = z17;
                                    str = str3;
                                    z7 = z14;
                                    str2 = str4;
                                    z8 = z10;
                                    break;
                                case 61:
                                    i11 = indexCount2;
                                    i12 = i15;
                                    blendMode = blendMode2;
                                    textView.createEditorIfNeeded();
                                    textView.mEditor.createInputContentTypeIfNeeded();
                                    textView.mEditor.mInputContentType.imeActionId = typedArrayObtainStyledAttributes4.getInt(index, textView.mEditor.mInputContentType.imeActionId);
                                    i13 = i17;
                                    i14 = dimensionPixelSize;
                                    z4 = z12;
                                    z5 = z11;
                                    z6 = z17;
                                    str = str3;
                                    z7 = z14;
                                    str2 = str4;
                                    z8 = z10;
                                    break;
                                case 62:
                                    i11 = indexCount2;
                                    i12 = i15;
                                    blendMode = blendMode2;
                                    textView.mTextSelectHandleLeftRes = typedArrayObtainStyledAttributes4.getResourceId(index, 0);
                                    i13 = i17;
                                    i14 = dimensionPixelSize;
                                    z4 = z12;
                                    z5 = z11;
                                    z6 = z17;
                                    str = str3;
                                    z7 = z14;
                                    str2 = str4;
                                    z8 = z10;
                                    break;
                                case 63:
                                    i11 = indexCount2;
                                    i12 = i15;
                                    blendMode = blendMode2;
                                    textView.mTextSelectHandleRightRes = typedArrayObtainStyledAttributes4.getResourceId(index, 0);
                                    i13 = i17;
                                    i14 = dimensionPixelSize;
                                    z4 = z12;
                                    z5 = z11;
                                    z6 = z17;
                                    str = str3;
                                    z7 = z14;
                                    str2 = str4;
                                    z8 = z10;
                                    break;
                                case 64:
                                    i11 = indexCount2;
                                    i12 = i15;
                                    blendMode = blendMode2;
                                    textView.mTextSelectHandleRes = typedArrayObtainStyledAttributes4.getResourceId(index, 0);
                                    i13 = i17;
                                    i14 = dimensionPixelSize;
                                    z4 = z12;
                                    z5 = z11;
                                    z6 = z17;
                                    str = str3;
                                    z7 = z14;
                                    str2 = str4;
                                    z8 = z10;
                                    break;
                                default:
                                    switch (index) {
                                        case 79:
                                            i11 = indexCount2;
                                            i12 = i15;
                                            colorStateList2 = typedArrayObtainStyledAttributes4.getColorStateList(index);
                                            z9 = z17;
                                            str = str3;
                                            z7 = z14;
                                            str2 = str4;
                                            break;
                                        case 80:
                                            i11 = indexCount2;
                                            i12 = i15;
                                            blendMode2 = Drawable.parseBlendMode(typedArrayObtainStyledAttributes4.getInt(index, -1), blendMode2);
                                            z9 = z17;
                                            str = str3;
                                            z7 = z14;
                                            str2 = str4;
                                            break;
                                        case 81:
                                            i11 = indexCount2;
                                            i12 = i15;
                                            textView.mBreakStrategy = typedArrayObtainStyledAttributes4.getInt(index, 0);
                                            i13 = i17;
                                            i14 = dimensionPixelSize;
                                            blendMode = blendMode2;
                                            z4 = z12;
                                            z5 = z11;
                                            z6 = z17;
                                            str = str3;
                                            z7 = z14;
                                            str2 = str4;
                                            z8 = z10;
                                            break;
                                        case 82:
                                            i11 = indexCount2;
                                            i12 = i15;
                                            textView.mHyphenationFrequency = typedArrayObtainStyledAttributes4.getInt(index, 0);
                                            i13 = i17;
                                            i14 = dimensionPixelSize;
                                            blendMode = blendMode2;
                                            z4 = z12;
                                            z5 = z11;
                                            z6 = z17;
                                            str = str3;
                                            z7 = z14;
                                            str2 = str4;
                                            z8 = z10;
                                            break;
                                        case 83:
                                            i11 = indexCount2;
                                            i12 = i15;
                                            textView.createEditorIfNeeded();
                                            textView.mEditor.mAllowUndo = typedArrayObtainStyledAttributes4.getBoolean(index, true);
                                            i13 = i17;
                                            i14 = dimensionPixelSize;
                                            blendMode = blendMode2;
                                            z4 = z12;
                                            z5 = z11;
                                            z6 = z17;
                                            str = str3;
                                            z7 = z14;
                                            str2 = str4;
                                            z8 = z10;
                                            break;
                                        case 84:
                                            i11 = indexCount2;
                                            i12 = i15;
                                            textView.mAutoSizeTextType = typedArrayObtainStyledAttributes4.getInt(index, 0);
                                            i13 = i17;
                                            i14 = dimensionPixelSize;
                                            blendMode = blendMode2;
                                            z4 = z12;
                                            z5 = z11;
                                            z6 = z17;
                                            str = str3;
                                            z7 = z14;
                                            str2 = str4;
                                            z8 = z10;
                                            break;
                                        case 85:
                                            i11 = indexCount2;
                                            i12 = i15;
                                            dimension2 = typedArrayObtainStyledAttributes4.getDimension(index, -1.0f);
                                            z9 = z17;
                                            str = str3;
                                            z7 = z14;
                                            str2 = str4;
                                            break;
                                        case 86:
                                            i11 = indexCount2;
                                            i12 = i15;
                                            int resourceId2 = typedArrayObtainStyledAttributes4.getResourceId(index, 0);
                                            if (resourceId2 > 0) {
                                                TypedArray typedArrayObtainTypedArray = typedArrayObtainStyledAttributes4.getResources().obtainTypedArray(resourceId2);
                                                textView.setupAutoSizeUniformPresetSizes(typedArrayObtainTypedArray);
                                                typedArrayObtainTypedArray.recycle();
                                            }
                                            i13 = i17;
                                            i14 = dimensionPixelSize;
                                            blendMode = blendMode2;
                                            z4 = z12;
                                            z5 = z11;
                                            z6 = z17;
                                            str = str3;
                                            z7 = z14;
                                            str2 = str4;
                                            z8 = z10;
                                            break;
                                        case 87:
                                            i11 = indexCount2;
                                            i12 = i15;
                                            fApplyDimension = typedArrayObtainStyledAttributes4.getDimension(index, -1.0f);
                                            z9 = z17;
                                            str = str3;
                                            z7 = z14;
                                            str2 = str4;
                                            break;
                                        case 88:
                                            i11 = indexCount2;
                                            i12 = i15;
                                            dimension = typedArrayObtainStyledAttributes4.getDimension(index, -1.0f);
                                            z9 = z17;
                                            str = str3;
                                            z7 = z14;
                                            str2 = str4;
                                            break;
                                        case 89:
                                            i11 = indexCount2;
                                            i12 = i15;
                                            textView.mJustificationMode = typedArrayObtainStyledAttributes4.getInt(index, 0);
                                            i13 = i17;
                                            i14 = dimensionPixelSize;
                                            blendMode = blendMode2;
                                            z4 = z12;
                                            z5 = z11;
                                            z6 = z17;
                                            str = str3;
                                            z7 = z14;
                                            str2 = str4;
                                            z8 = z10;
                                            break;
                                        default:
                                            switch (index) {
                                                case 92:
                                                    i11 = indexCount2;
                                                    i12 = i15;
                                                    dimensionPixelSize2 = typedArrayObtainStyledAttributes4.getDimensionPixelSize(index, -1);
                                                    z9 = z17;
                                                    str = str3;
                                                    z7 = z14;
                                                    str2 = str4;
                                                    break;
                                                case 93:
                                                    i11 = indexCount2;
                                                    i12 = i15;
                                                    dimensionPixelSize3 = typedArrayObtainStyledAttributes4.getDimensionPixelSize(index, -1);
                                                    z9 = z17;
                                                    str = str3;
                                                    z7 = z14;
                                                    str2 = str4;
                                                    break;
                                                case 94:
                                                    TypedValue typedValuePeekValue = typedArrayObtainStyledAttributes4.peekValue(index);
                                                    i11 = indexCount2;
                                                    if (typedValuePeekValue != null) {
                                                        i12 = i15;
                                                        if (typedValuePeekValue.type == 5) {
                                                            complexUnit = typedValuePeekValue.getComplexUnit();
                                                            dimensionPixelSize4 = TypedValue.complexToFloat(typedValuePeekValue.data);
                                                        }
                                                        z9 = z17;
                                                        str = str3;
                                                        z7 = z14;
                                                        str2 = str4;
                                                        break;
                                                    } else {
                                                        i12 = i15;
                                                    }
                                                    dimensionPixelSize4 = typedArrayObtainStyledAttributes4.getDimensionPixelSize(index, -1);
                                                    z9 = z17;
                                                    str = str3;
                                                    z7 = z14;
                                                    str2 = str4;
                                                default:
                                                    switch (index) {
                                                        case 101:
                                                            textView.mUseBoundsForWidth = typedArrayObtainStyledAttributes4.getBoolean(index, false);
                                                            i11 = indexCount2;
                                                            i12 = i15;
                                                            z9 = z17;
                                                            z15 = true;
                                                            str = str3;
                                                            z7 = z14;
                                                            str2 = str4;
                                                            break;
                                                        case 102:
                                                            textView.mUseLocalePreferredLineHeightForMinimum = typedArrayObtainStyledAttributes4.getBoolean(index, false);
                                                            i11 = indexCount2;
                                                            i12 = i15;
                                                            i13 = i17;
                                                            i14 = dimensionPixelSize;
                                                            blendMode = blendMode2;
                                                            z4 = z12;
                                                            z5 = z11;
                                                            z6 = z17;
                                                            str = str3;
                                                            z7 = z14;
                                                            str2 = str4;
                                                            z8 = z10;
                                                            break;
                                                        case 103:
                                                            textView.mShiftDrawingOffsetForStartOverhang = typedArrayObtainStyledAttributes4.getBoolean(index, false);
                                                            i11 = indexCount2;
                                                            i12 = i15;
                                                            i13 = i17;
                                                            i14 = dimensionPixelSize;
                                                            blendMode = blendMode2;
                                                            z4 = z12;
                                                            z5 = z11;
                                                            z6 = z17;
                                                            str = str3;
                                                            z7 = z14;
                                                            str2 = str4;
                                                            z8 = z10;
                                                            break;
                                                        case 104:
                                                            textView.mTextEditSuggestionContainerLayout = typedArrayObtainStyledAttributes4.getResourceId(index, 0);
                                                            i11 = indexCount2;
                                                            i12 = i15;
                                                            i13 = i17;
                                                            i14 = dimensionPixelSize;
                                                            blendMode = blendMode2;
                                                            z4 = z12;
                                                            z5 = z11;
                                                            z6 = z17;
                                                            str = str3;
                                                            z7 = z14;
                                                            str2 = str4;
                                                            z8 = z10;
                                                            break;
                                                        case 105:
                                                            textView.mTextEditSuggestionHighlightStyle = typedArrayObtainStyledAttributes4.getResourceId(index, 0);
                                                            i11 = indexCount2;
                                                            i12 = i15;
                                                            i13 = i17;
                                                            i14 = dimensionPixelSize;
                                                            blendMode = blendMode2;
                                                            z4 = z12;
                                                            z5 = z11;
                                                            z6 = z17;
                                                            str = str3;
                                                            z7 = z14;
                                                            str2 = str4;
                                                            z8 = z10;
                                                            break;
                                                        default:
                                                            i11 = indexCount2;
                                                            i12 = i15;
                                                            i13 = i17;
                                                            i14 = dimensionPixelSize;
                                                            blendMode = blendMode2;
                                                            z4 = z12;
                                                            z5 = z11;
                                                            z6 = z17;
                                                            str = str3;
                                                            z7 = z14;
                                                            str2 = str4;
                                                            z8 = z10;
                                                            break;
                                                    }
                                            }
                                    }
                            }
                    }
                    str4 = str2;
                    indexCount2 = i11;
                    z14 = z7;
                    str3 = str;
                    z17 = z9;
                    i15 = i12 + 1;
                } else {
                    i11 = indexCount2;
                    i12 = i15;
                    i13 = i17;
                    i14 = dimensionPixelSize;
                    blendMode = blendMode2;
                    z4 = z12;
                    z5 = z11;
                    z6 = z17;
                    str = str3;
                    z7 = z14;
                    str2 = str4;
                    z8 = z10;
                    textView.mLineBreakWordStyle = typedArrayObtainStyledAttributes4.getInt(index, 0);
                }
                z9 = z6;
                z11 = z5;
                z12 = z4;
                dimensionPixelSize = i14;
                str4 = str2;
                indexCount2 = i11;
                z14 = z7;
                str3 = str;
                z17 = z9;
                i15 = i12 + 1;
            } else {
                i11 = indexCount2;
                i12 = i15;
                i13 = i17;
                i14 = dimensionPixelSize;
                blendMode = blendMode2;
                z4 = z12;
                z5 = z11;
                z6 = z17;
                str = str3;
                z7 = z14;
                str2 = str4;
                z8 = z10;
                textView.mTextEditSuggestionItemLayout = typedArrayObtainStyledAttributes4.getResourceId(index, 0);
            }
            blendMode2 = blendMode;
            i17 = i13;
            z10 = z8;
            z9 = z6;
            z11 = z5;
            z12 = z4;
            dimensionPixelSize = i14;
            str4 = str2;
            indexCount2 = i11;
            z14 = z7;
            str3 = str;
            z17 = z9;
            i15 = i12 + 1;
        }
        int i22 = i17;
        boolean z22 = z10;
        boolean z23 = z14;
        int i23 = dimensionPixelSize;
        BlendMode blendMode3 = blendMode2;
        boolean z24 = z12;
        boolean z25 = z11;
        boolean z26 = z17;
        typedArrayObtainStyledAttributes4.recycle();
        BufferType bufferType2 = BufferType.EDITABLE;
        int inputType = i16;
        int i24 = inputType & 4095;
        boolean z27 = i24 == 129;
        boolean z28 = i24 == 225;
        boolean z29 = i24 == 18;
        int i25 = context2.getApplicationInfo().targetSdkVersion;
        int i26 = i21;
        textView.mUseInternationalizedInput = i25 >= 26;
        if (CompatChanges.isChangeEnabled(BORINGLAYOUT_FALLBACK_LINESPACING)) {
            textView.mUseFallbackLineSpacing = 2;
        } else if (CompatChanges.isChangeEnabled(STATICLAYOUT_FALLBACK_LINESPACING)) {
            textView.mUseFallbackLineSpacing = 1;
        } else {
            textView.mUseFallbackLineSpacing = 0;
        }
        if (!z15) {
            textView.mUseBoundsForWidth = CompatChanges.isChangeEnabled(USE_BOUNDS_FOR_WIDTH);
        }
        textView.mUseTextPaddingForUiTranslation = i25 <= 30;
        if (text2 != null) {
            try {
                Class<?> cls = Class.forName(text2.toString());
                try {
                    textView.createEditorIfNeeded();
                    textView.mEditor.mKeyListener = (KeyListener) cls.newInstance();
                    try {
                        Editor editor3 = textView.mEditor;
                        if (inputType == 0) {
                            inputType = editor3.mKeyListener.getInputType();
                        }
                        editor3.mInputType = inputType;
                    } catch (IncompatibleClassChangeError unused) {
                        textView.mEditor.mInputType = 1;
                    }
                } catch (IllegalAccessException e3) {
                    throw new RuntimeException(e3);
                } catch (InstantiationException e4) {
                    throw new RuntimeException(e4);
                }
            } catch (ClassNotFoundException e5) {
                throw new RuntimeException(e5);
            }
        } else if (text3 != null) {
            textView.createEditorIfNeeded();
            textView.mEditor.mKeyListener = DigitsKeyListener.getInstance(text3.toString());
            textView.mEditor.mInputType = inputType == 0 ? 1 : inputType;
        } else {
            if (inputType != 0) {
                textView.setInputType(inputType, true);
                bufferType = bufferType2;
                z = !isMultilineInputType(inputType);
                editor = textView.mEditor;
                if (editor != null) {
                    editor.adjustInputType(z22, z27, z28, z29);
                }
                if (z24) {
                    textView.createEditorIfNeeded();
                    textView.mEditor.mSelectAllOnFocus = true;
                    if (bufferType == BufferType.NORMAL) {
                        bufferType = BufferType.SPANNABLE;
                    }
                }
                colorStateList = colorStateList2;
                if (colorStateList != null || blendMode3 != null) {
                    if (textView.mDrawables == null) {
                        textView.mDrawables = new Drawables(context2);
                    }
                    if (colorStateList == null) {
                        textView.mDrawables.mTintList = colorStateList;
                        z2 = true;
                        textView.mDrawables.mHasTint = true;
                    } else {
                        z2 = true;
                    }
                    if (blendMode3 != null) {
                        textView.mDrawables.mBlendMode = blendMode3;
                        textView.mDrawables.mHasTintMode = z2;
                    }
                }
                textView.setCompoundDrawablesWithIntrinsicBounds(drawable, drawable2, drawable3, drawable4);
                textView.setRelativeDrawablesIfNeeded(drawable5, drawable6);
                textView.setCompoundDrawablePadding(i23);
                textView.setInputTypeSingleLine(z);
                textView.applySingleLine(z, z, z, false);
                if (z && textView.getKeyListener() == null && i19 == -1) {
                    i19 = 3;
                }
                if (i19 == 1) {
                    textView.setEllipsize(TextUtils.TruncateAt.START);
                } else if (i19 == 2) {
                    textView.setEllipsize(TextUtils.TruncateAt.MIDDLE);
                } else if (i19 == 3) {
                    textView.setEllipsize(TextUtils.TruncateAt.END);
                } else if (i19 == 4) {
                    if (ViewConfiguration.get(context2).isFadingMarqueeEnabled()) {
                        textView.setHorizontalFadingEdgeEnabled(true);
                        textView.mMarqueeFadeMode = 0;
                    } else {
                        textView.setHorizontalFadingEdgeEnabled(false);
                        textView.mMarqueeFadeMode = 1;
                    }
                    textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                }
                z3 = z22 || z27 || z28 || z29;
                textView.mFontWeightAdjustment = textView.getContext().getResources().getConfiguration().fontWeightAdjustment;
                textView.applyTextAppearance(textAppearanceAttributes);
                textView.mCursorThicknessScale = textView.getContext().getResources().getConfiguration().semCursorThicknessScale;
                if (z3) {
                    textView.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                if (bufferType == BufferType.EDITABLE && z) {
                    i5 = i26;
                    if (i5 == -1) {
                        textView.mSingleLineLengthFilter = new InputFilter.LengthFilter(5000);
                    }
                } else {
                    i5 = i26;
                }
                lengthFilter = textView.mSingleLineLengthFilter;
                if (lengthFilter != null) {
                    textView.setFilters(new InputFilter[]{lengthFilter});
                } else if (i5 >= 0) {
                    textView.setFilters(new InputFilter[]{new InputFilter.LengthFilter(i5)});
                } else {
                    textView.setFilters(NO_FILTERS);
                }
                textView.setText(text, bufferType);
                if (textView.mText == null) {
                    textView.mText = "";
                }
                if (textView.mTransformed == null) {
                    textView.mTransformed = "";
                }
                if (z16) {
                    textView.mTextSetFromXmlOrResourceId = true;
                }
                charSequence = text4;
                if (charSequence != null) {
                    textView.setHint(charSequence);
                }
                TypedArray typedArrayObtainStyledAttributes5 = context2.obtainStyledAttributes(attributeSet, R.styleable.View, i, i2);
                boolean z30 = (textView.mMovement == null && textView.getKeyListener() == null) ? false : true;
                boolean z31 = z30 || textView.isClickable();
                boolean z32 = z30 || textView.isLongClickable();
                focusable = textView.getFocusable();
                if (CoreRune.DIRECT_WRITING) {
                    textView.setAutoHandwritingEnabled(true);
                }
                indexCount = typedArrayObtainStyledAttributes5.getIndexCount();
                boolean z33 = true;
                for (i6 = 0; i6 < indexCount; i6++) {
                    int index2 = typedArrayObtainStyledAttributes5.getIndex(i6);
                    if (index2 == 19) {
                        TypedValue typedValue = new TypedValue();
                        if (typedArrayObtainStyledAttributes5.getValue(index2, typedValue)) {
                            if (typedValue.type == 18) {
                                focusable = typedValue.data == 0 ? 0 : 1;
                            } else {
                                focusable = typedValue.data;
                            }
                        }
                    } else if (index2 == 107) {
                        z33 = typedArrayObtainStyledAttributes5.getBoolean(index2, true);
                    } else if (index2 == 30) {
                        z31 = typedArrayObtainStyledAttributes5.getBoolean(index2, z31);
                    } else if (index2 == 31) {
                        z32 = typedArrayObtainStyledAttributes5.getBoolean(index2, z32);
                    }
                }
                typedArrayObtainStyledAttributes5.recycle();
                if (focusable != textView.getFocusable()) {
                    textView.setFocusable(focusable);
                }
                textView.setClickable(z31);
                textView.setLongClickable(z32);
                if (!CoreRune.DIRECT_WRITING) {
                    textView.setAutoHandwritingEnabled(z33);
                }
                editor2 = textView.mEditor;
                if (editor2 != null) {
                    editor2.prepareCursorControllers();
                }
                if (textView.getImportantForAccessibility() == 0) {
                    textView.setImportantForAccessibility(1);
                }
                if (textView.supportsAutoSizeText()) {
                    if (textView.mAutoSizeTextType == 1) {
                        if (!textView.mHasPresetAutoSizeValues) {
                            DisplayMetrics displayMetrics = textView.getResources().getDisplayMetrics();
                            if (fApplyDimension == -1.0f) {
                                i9 = 2;
                                fApplyDimension = TypedValue.applyDimension(2, 12.0f, displayMetrics);
                            } else {
                                i9 = 2;
                            }
                            textView.validateAndSetAutoSizeTextTypeUniformConfiguration(fApplyDimension, dimension == -1.0f ? TypedValue.applyDimension(i9, 112.0f, displayMetrics) : dimension, dimension2 == -1.0f ? 1.0f : dimension2);
                        }
                        textView.setupAutoSizeText();
                    }
                } else {
                    textView.mAutoSizeTextType = 0;
                }
                if (textView.getHoverUIFeatureLevel() >= 2) {
                    textView.semSetHoverPopupType(2);
                }
                i7 = dimensionPixelSize2;
                if (i7 >= 0) {
                    textView.setFirstBaselineToTopHeight(i7);
                }
                i8 = dimensionPixelSize3;
                if (i8 >= 0) {
                    textView.setLastBaselineToBottomHeight(i8);
                }
                f = dimensionPixelSize4;
                if (f >= 0.0f) {
                    int i27 = complexUnit;
                    if (i27 == -1) {
                        textView.setLineHeightPx(f);
                    } else {
                        textView.setLineHeight(i27, f);
                    }
                }
                textView.mFontFamily = textAppearanceAttributes.mFontFamily;
            }
            if (z13) {
                textView.createEditorIfNeeded();
                textView.mEditor.mKeyListener = DialerKeyListener.getInstance();
                textView.mEditor.mInputType = 3;
            } else if (i18 != 0) {
                textView.createEditorIfNeeded();
                textView.mEditor.mKeyListener = DigitsKeyListener.getInstance(null, (i18 & 2) != 0, (i18 & 4) != 0);
                textView.mEditor.mInputType = textView.mEditor.mKeyListener.getInputType();
            } else if (z23 || i20 != -1) {
                if (i20 == 1) {
                    capitalize = TextKeyListener.Capitalize.SENTENCES;
                    i10 = 16385;
                } else if (i20 == 2) {
                    capitalize = TextKeyListener.Capitalize.WORDS;
                    i10 = 8193;
                } else if (i20 == 3) {
                    capitalize = TextKeyListener.Capitalize.CHARACTERS;
                    i10 = 4097;
                } else {
                    capitalize = TextKeyListener.Capitalize.NONE;
                    i10 = 1;
                }
                textView.createEditorIfNeeded();
                textView.mEditor.mKeyListener = TextKeyListener.getInstance(z23, capitalize);
                textView.mEditor.mInputType = i10;
            } else if (z26) {
                textView.createEditorIfNeeded();
                textView.mEditor.mKeyListener = TextKeyListener.getInstance();
                textView.mEditor.mInputType = 1;
            } else {
                if (textView.isTextSelectable()) {
                    Editor editor4 = textView.mEditor;
                    if (editor4 != null) {
                        editor4.mKeyListener = null;
                        textView.mEditor.mInputType = 0;
                    }
                    bufferType = BufferType.SPANNABLE;
                    textView.setMovementMethod(ArrowKeyMovementMethod.getInstance());
                } else {
                    Editor editor5 = textView.mEditor;
                    if (editor5 != null) {
                        editor5.mKeyListener = null;
                    }
                    if (i22 == 0) {
                        bufferType = BufferType.NORMAL;
                    } else if (i22 == 1) {
                        bufferType = BufferType.SPANNABLE;
                    } else if (i22 == 2) {
                        bufferType = BufferType.EDITABLE;
                    }
                }
                z = z25;
                editor = textView.mEditor;
                if (editor != null) {
                }
                if (z24) {
                }
                colorStateList = colorStateList2;
                if (colorStateList != null) {
                    if (textView.mDrawables == null) {
                    }
                    if (colorStateList == null) {
                    }
                    if (blendMode3 != null) {
                    }
                }
                textView.setCompoundDrawablesWithIntrinsicBounds(drawable, drawable2, drawable3, drawable4);
                textView.setRelativeDrawablesIfNeeded(drawable5, drawable6);
                textView.setCompoundDrawablePadding(i23);
                textView.setInputTypeSingleLine(z);
                textView.applySingleLine(z, z, z, false);
                if (z) {
                    i19 = 3;
                }
                if (i19 == 1) {
                }
                if (z22) {
                }
                textView.mFontWeightAdjustment = textView.getContext().getResources().getConfiguration().fontWeightAdjustment;
                textView.applyTextAppearance(textAppearanceAttributes);
                textView.mCursorThicknessScale = textView.getContext().getResources().getConfiguration().semCursorThicknessScale;
                if (z3) {
                }
                if (bufferType == BufferType.EDITABLE) {
                    i5 = i26;
                }
                lengthFilter = textView.mSingleLineLengthFilter;
                if (lengthFilter != null) {
                }
                textView.setText(text, bufferType);
                if (textView.mText == null) {
                }
                if (textView.mTransformed == null) {
                }
                if (z16) {
                }
                charSequence = text4;
                if (charSequence != null) {
                }
                TypedArray typedArrayObtainStyledAttributes52 = context2.obtainStyledAttributes(attributeSet, R.styleable.View, i, i2);
                if (textView.mMovement == null) {
                }
                if (z30) {
                }
                if (z30) {
                }
                focusable = textView.getFocusable();
                if (CoreRune.DIRECT_WRITING) {
                }
                indexCount = typedArrayObtainStyledAttributes52.getIndexCount();
                boolean z332 = true;
                while (i6 < indexCount) {
                }
                typedArrayObtainStyledAttributes52.recycle();
                if (focusable != textView.getFocusable()) {
                }
                textView.setClickable(z31);
                textView.setLongClickable(z32);
                if (!CoreRune.DIRECT_WRITING) {
                }
                editor2 = textView.mEditor;
                if (editor2 != null) {
                }
                if (textView.getImportantForAccessibility() == 0) {
                }
                if (textView.supportsAutoSizeText()) {
                }
                if (textView.getHoverUIFeatureLevel() >= 2) {
                }
                i7 = dimensionPixelSize2;
                if (i7 >= 0) {
                }
                i8 = dimensionPixelSize3;
                if (i8 >= 0) {
                }
                f = dimensionPixelSize4;
                if (f >= 0.0f) {
                }
                textView.mFontFamily = textAppearanceAttributes.mFontFamily;
            }
        }
        bufferType = bufferType2;
        z = z25;
        editor = textView.mEditor;
        if (editor != null) {
        }
        if (z24) {
        }
        colorStateList = colorStateList2;
        if (colorStateList != null) {
        }
        textView.setCompoundDrawablesWithIntrinsicBounds(drawable, drawable2, drawable3, drawable4);
        textView.setRelativeDrawablesIfNeeded(drawable5, drawable6);
        textView.setCompoundDrawablePadding(i23);
        textView.setInputTypeSingleLine(z);
        textView.applySingleLine(z, z, z, false);
        if (z) {
        }
        if (i19 == 1) {
        }
        if (z22) {
        }
        textView.mFontWeightAdjustment = textView.getContext().getResources().getConfiguration().fontWeightAdjustment;
        textView.applyTextAppearance(textAppearanceAttributes);
        textView.mCursorThicknessScale = textView.getContext().getResources().getConfiguration().semCursorThicknessScale;
        if (z3) {
        }
        if (bufferType == BufferType.EDITABLE) {
        }
        lengthFilter = textView.mSingleLineLengthFilter;
        if (lengthFilter != null) {
        }
        textView.setText(text, bufferType);
        if (textView.mText == null) {
        }
        if (textView.mTransformed == null) {
        }
        if (z16) {
        }
        charSequence = text4;
        if (charSequence != null) {
        }
        TypedArray typedArrayObtainStyledAttributes522 = context2.obtainStyledAttributes(attributeSet, R.styleable.View, i, i2);
        if (textView.mMovement == null) {
        }
        if (z30) {
        }
        if (z30) {
        }
        focusable = textView.getFocusable();
        if (CoreRune.DIRECT_WRITING) {
        }
        indexCount = typedArrayObtainStyledAttributes522.getIndexCount();
        boolean z3322 = true;
        while (i6 < indexCount) {
        }
        typedArrayObtainStyledAttributes522.recycle();
        if (focusable != textView.getFocusable()) {
        }
        textView.setClickable(z31);
        textView.setLongClickable(z32);
        if (!CoreRune.DIRECT_WRITING) {
        }
        editor2 = textView.mEditor;
        if (editor2 != null) {
        }
        if (textView.getImportantForAccessibility() == 0) {
        }
        if (textView.supportsAutoSizeText()) {
        }
        if (textView.getHoverUIFeatureLevel() >= 2) {
        }
        i7 = dimensionPixelSize2;
        if (i7 >= 0) {
        }
        i8 = dimensionPixelSize3;
        if (i8 >= 0) {
        }
        f = dimensionPixelSize4;
        if (f >= 0.0f) {
        }
        textView.mFontFamily = textAppearanceAttributes.mFontFamily;
    }

    private void setTextInternal(CharSequence charSequence) {
        this.mText = charSequence;
        this.mSpannable = charSequence instanceof Spannable ? (Spannable) charSequence : null;
        this.mPrecomputed = charSequence instanceof PrecomputedText ? (PrecomputedText) charSequence : null;
    }

    public void setAutoSizeTextTypeWithDefaults(int i) {
        if (i != 100 && supportsAutoSizeText()) {
            if (i == 0) {
                clearAutoSizeConfiguration();
                return;
            }
            if (i == 1) {
                DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
                validateAndSetAutoSizeTextTypeUniformConfiguration(TypedValue.applyDimension(2, 12.0f, displayMetrics), TypedValue.applyDimension(2, 112.0f, displayMetrics), 1.0f);
                if (setupAutoSizeText()) {
                    autoSizeText();
                    invalidate();
                    return;
                }
                return;
            }
            throw new IllegalArgumentException("Unknown auto-size text type: " + i);
        }
    }

    public void setAutoSizeTextTypeUniformWithConfiguration(int i, int i2, int i3, int i4) {
        if (supportsAutoSizeText()) {
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            validateAndSetAutoSizeTextTypeUniformConfiguration(TypedValue.applyDimension(i4, i, displayMetrics), TypedValue.applyDimension(i4, i2, displayMetrics), TypedValue.applyDimension(i4, i3, displayMetrics));
            if (setupAutoSizeText()) {
                autoSizeText();
                invalidate();
            }
        }
    }

    public void setAutoSizeTextTypeUniformWithPresetSizes(int[] iArr, int i) {
        if (supportsAutoSizeText()) {
            int length = iArr.length;
            if (length > 0) {
                int[] iArrCopyOf = new int[length];
                if (i == 0) {
                    iArrCopyOf = Arrays.copyOf(iArr, length);
                } else {
                    DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
                    for (int i2 = 0; i2 < length; i2++) {
                        iArrCopyOf[i2] = Math.round(TypedValue.applyDimension(i, iArr[i2], displayMetrics));
                    }
                }
                this.mAutoSizeTextSizesInPx = cleanupAutoSizePresetSizes(iArrCopyOf);
                if (!setupAutoSizeUniformPresetSizesConfiguration()) {
                    throw new IllegalArgumentException("None of the preset sizes is valid: " + Arrays.toString(iArr));
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

    private void setupAutoSizeUniformPresetSizes(TypedArray typedArray) {
        int length = typedArray.length();
        int[] iArr = new int[length];
        if (length > 0) {
            for (int i = 0; i < length; i++) {
                iArr[i] = typedArray.getDimensionPixelSize(i, -1);
            }
            this.mAutoSizeTextSizesInPx = cleanupAutoSizePresetSizes(iArr);
            setupAutoSizeUniformPresetSizesConfiguration();
        }
    }

    private boolean setupAutoSizeUniformPresetSizesConfiguration() {
        boolean z = this.mAutoSizeTextSizesInPx.length > 0;
        this.mHasPresetAutoSizeValues = z;
        if (z) {
            this.mAutoSizeTextType = 1;
            this.mAutoSizeMinTextSizeInPx = r0[0];
            this.mAutoSizeMaxTextSizeInPx = r0[r1 - 1];
            this.mAutoSizeStepGranularityInPx = -1.0f;
        }
        return z;
    }

    private void validateAndSetAutoSizeTextTypeUniformConfiguration(float f, float f2, float f3) {
        if (f <= 0.0f) {
            throw new IllegalArgumentException("Minimum auto-size text size (" + f + "px) is less or equal to (0px)");
        }
        if (f2 <= f) {
            throw new IllegalArgumentException("Maximum auto-size text size (" + f2 + "px) is less or equal to minimum auto-size text size (" + f + "px)");
        }
        if (f3 <= 0.0f) {
            throw new IllegalArgumentException("The auto-size step granularity (" + f3 + "px) is less or equal to (0px)");
        }
        this.mAutoSizeTextType = 1;
        this.mAutoSizeMinTextSizeInPx = f;
        this.mAutoSizeMaxTextSizeInPx = f2;
        this.mAutoSizeStepGranularityInPx = ((float) Math.floor(f3 * 10000.0f)) / 10000.0f;
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

    private int[] cleanupAutoSizePresetSizes(int[] iArr) {
        int length = iArr.length;
        if (length != 0) {
            Arrays.sort(iArr);
            IntArray intArray = new IntArray();
            for (int i : iArr) {
                if (i > 0 && intArray.binarySearch(i) < 0) {
                    intArray.add(i);
                }
            }
            if (length != intArray.size()) {
                return intArray.toArray();
            }
        }
        return iArr;
    }

    private boolean setupAutoSizeText() {
        if (supportsAutoSizeText() && this.mAutoSizeTextType == 1) {
            if (!this.mHasPresetAutoSizeValues || this.mAutoSizeTextSizesInPx.length == 0) {
                int iFloor = ((int) Math.floor((this.mAutoSizeMaxTextSizeInPx - this.mAutoSizeMinTextSizeInPx) / this.mAutoSizeStepGranularityInPx)) + 1;
                int[] iArr = new int[iFloor];
                for (int i = 0; i < iFloor; i++) {
                    iArr[i] = Math.round(this.mAutoSizeMinTextSizeInPx + (i * this.mAutoSizeStepGranularityInPx));
                }
                this.mAutoSizeTextSizesInPx = cleanupAutoSizePresetSizes(iArr);
            }
            this.mNeedsAutoSizeText = true;
        } else {
            this.mNeedsAutoSizeText = false;
        }
        return this.mNeedsAutoSizeText;
    }

    private int[] parseDimensionArray(TypedArray typedArray) {
        if (typedArray == null) {
            return null;
        }
        int length = typedArray.length();
        int[] iArr = new int[length];
        for (int i = 0; i < length; i++) {
            iArr[i] = typedArray.getDimensionPixelSize(i, 0);
        }
        return iArr;
    }

    @Override // android.view.View
    public void onActivityResult(int i, int i2, Intent intent) {
        CharSequence charSequenceExtra;
        if (i == 100) {
            if (i2 == -1 && intent != null) {
                CharSequence charSequenceExtra2 = intent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT);
                if (charSequenceExtra2 != null) {
                    if (isTextEditable()) {
                        performReceiveContent(new ContentInfo.Builder(ClipData.newPlainText("", charSequenceExtra2), 5).build());
                        Editor editor = this.mEditor;
                        if (editor != null) {
                            editor.refreshTextActionMode();
                            return;
                        }
                        return;
                    }
                    if (charSequenceExtra2.length() > 0) {
                        Toast.makeText(getContext(), String.valueOf(charSequenceExtra2), 1).show();
                        return;
                    }
                    return;
                }
                return;
            }
            Spannable spannable = this.mSpannable;
            if (spannable != null) {
                Selection.setSelection(spannable, getSelectionEnd());
                return;
            }
            return;
        }
        if (i == 101) {
            if (i2 != -1 || intent == null || (charSequenceExtra = intent.getCharSequenceExtra("translatedText")) == null || !isTextEditable()) {
                return;
            }
            int selectionStart = getSelectionStart();
            int selectionEnd = getSelectionEnd();
            if (selectionStart == selectionEnd) {
                selectionStart = this.mPrevSelectionStartForSSS;
                selectionEnd = this.mPrevSelectionEndForSSS;
            }
            replaceText_internal(selectionStart, selectionEnd, charSequenceExtra);
            return;
        }
        if (i == 102 && i2 == -1 && intent != null) {
            CharSequence charSequenceExtra3 = intent.getCharSequenceExtra("toolkitText");
            CharSequence charSequenceExtra4 = intent.getCharSequenceExtra("toolkitAction");
            if (charSequenceExtra3 == null || !isTextEditable()) {
                return;
            }
            int selectionStart2 = getSelectionStart();
            int selectionEnd2 = getSelectionEnd();
            if (selectionStart2 == selectionEnd2) {
                selectionStart2 = this.mPrevSelectionStartForSSS;
                selectionEnd2 = this.mPrevSelectionEndForSSS;
            }
            if ("replace".equals(charSequenceExtra4)) {
                replaceText_internal(selectionStart2, selectionEnd2, charSequenceExtra3);
            } else if ("addto".equals(charSequenceExtra4)) {
                replaceText_internal(selectionEnd2, selectionEnd2, charSequenceExtra3);
            } else {
                Log.e(LOG_TAG, "writingtoolkit action error");
            }
        }
    }

    private void setTypefaceFromAttrs(Typeface typeface, String str, int i, int i2, int i3) {
        if (typeface == null && str != null) {
            resolveStyleAndSetTypeface(Typeface.create(str, 0), i2, i3);
            return;
        }
        if (typeface != null) {
            resolveStyleAndSetTypeface(typeface, i2, i3);
            return;
        }
        if (i == 1) {
            resolveStyleAndSetTypeface(Typeface.SANS_SERIF, i2, i3);
            return;
        }
        if (i == 2) {
            resolveStyleAndSetTypeface(Typeface.SERIF, i2, i3);
        } else if (i == 3) {
            resolveStyleAndSetTypeface(Typeface.MONOSPACE, i2, i3);
        } else {
            resolveStyleAndSetTypeface(null, i2, i3);
        }
    }

    private void resolveStyleAndSetTypeface(Typeface typeface, int i, int i2) {
        if (i2 >= 0) {
            setTypeface(Typeface.create(typeface, Math.min(1000, i2), (i & 2) != 0));
        } else {
            setTypeface(typeface, i);
        }
    }

    private void setRelativeDrawablesIfNeeded(Drawable drawable, Drawable drawable2) {
        if (drawable == null && drawable2 == null) {
            return;
        }
        Drawables drawables = this.mDrawables;
        if (drawables == null) {
            drawables = new Drawables(getContext());
            this.mDrawables = drawables;
        }
        this.mDrawables.mOverride = true;
        Rect rect = drawables.mCompoundRect;
        int[] drawableState = getDrawableState();
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            drawable.setState(drawableState);
            drawable.copyBounds(rect);
            drawable.setCallback(this);
            drawables.mDrawableStart = drawable;
            drawables.mDrawableSizeStart = rect.width();
            drawables.mDrawableHeightStart = rect.height();
        } else {
            drawables.mDrawableHeightStart = 0;
            drawables.mDrawableSizeStart = 0;
        }
        if (drawable2 != null) {
            drawable2.setBounds(0, 0, drawable2.getIntrinsicWidth(), drawable2.getIntrinsicHeight());
            drawable2.setState(drawableState);
            drawable2.copyBounds(rect);
            drawable2.setCallback(this);
            drawables.mDrawableEnd = drawable2;
            drawables.mDrawableSizeEnd = rect.width();
            drawables.mDrawableHeightEnd = rect.height();
        } else {
            drawables.mDrawableHeightEnd = 0;
            drawables.mDrawableSizeEnd = 0;
        }
        resetResolvedDrawables();
        resolveDrawables();
        applyCompoundDrawableTint();
    }

    @Override // android.view.View
    @RemotableViewMethod
    public void setEnabled(boolean z) throws Resources.NotFoundException {
        InputMethodManager inputMethodManager;
        InputMethodManager inputMethodManager2;
        if (z == isEnabled()) {
            return;
        }
        if (!z && (inputMethodManager2 = getInputMethodManager()) != null) {
            inputMethodManager2.hideSoftInputFromView(this, 0);
        }
        super.setEnabled(z);
        if (z && (inputMethodManager = getInputMethodManager()) != null) {
            inputMethodManager.restartInput(this);
        }
        Editor editor = this.mEditor;
        if (editor != null) {
            editor.invalidateTextDisplayList();
            this.mEditor.prepareCursorControllers();
            this.mEditor.makeBlink();
        }
        if (this.mButtonShapePaint != null) {
            this.mButtonShapeAlpha = z ? this.mButtonShapeOutlineStrokeEnabled : this.mButtonShapeOutlineStrokeDisabled;
        }
    }

    public void setTypeface(Typeface typeface, int i) {
        Typeface typefaceCreate;
        if (i > 0) {
            if (typeface == null) {
                typefaceCreate = Typeface.defaultFromStyle(i);
            } else {
                typefaceCreate = Typeface.create(typeface, i);
            }
            setTypeface(typefaceCreate);
            int i2 = (~(typefaceCreate != null ? typefaceCreate.getStyle() : 0)) & i;
            this.mTextPaint.setFakeBoldText((i2 & 1) != 0);
            this.mTextPaint.setTextSkewX((i2 & 2) != 0 ? -0.25f : 0.0f);
            return;
        }
        this.mTextPaint.setFakeBoldText(false);
        this.mTextPaint.setTextSkewX(0.0f);
        setTypeface(typeface);
    }

    @ViewDebug.CapturedViewProperty
    public CharSequence getText() {
        ViewTranslationCallback viewTranslationCallback;
        if (this.mUseTextPaddingForUiTranslation && (viewTranslationCallback = getViewTranslationCallback()) != null && (viewTranslationCallback instanceof TextViewTranslationCallback)) {
            TextViewTranslationCallback textViewTranslationCallback = (TextViewTranslationCallback) viewTranslationCallback;
            if (textViewTranslationCallback.isTextPaddingEnabled() && textViewTranslationCallback.isShowingTranslation()) {
                return textViewTranslationCallback.getPaddedText(this.mText, this.mTransformed);
            }
        }
        return this.mText;
    }

    public int length() {
        return this.mText.length();
    }

    public Editable getEditableText() {
        CharSequence charSequence = this.mText;
        if (charSequence instanceof Editable) {
            return (Editable) charSequence;
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

    public final void setUndoManager(UndoManager undoManager, String str) {
        throw new UnsupportedOperationException("not implemented");
    }

    public final KeyListener getKeyListener() {
        Editor editor = this.mEditor;
        if (editor == null) {
            return null;
        }
        return editor.mKeyListener;
    }

    public void setKeyListener(KeyListener keyListener) {
        this.mListenerChanged = true;
        setKeyListenerOnly(keyListener);
        fixFocusableAndClickableSettings();
        if (keyListener != null) {
            createEditorIfNeeded();
            setInputTypeFromEditor();
        } else {
            Editor editor = this.mEditor;
            if (editor != null) {
                editor.mInputType = 0;
            }
        }
        InputMethodManager inputMethodManager = getInputMethodManager();
        if (inputMethodManager != null) {
            inputMethodManager.restartInput(this);
        }
        ensureEditorFocusedNotifiedToHandwritingInitiator();
    }

    private void setInputTypeFromEditor() {
        try {
            Editor editor = this.mEditor;
            editor.mInputType = editor.mKeyListener.getInputType();
        } catch (IncompatibleClassChangeError unused) {
            this.mEditor.mInputType = 1;
        }
        setInputTypeSingleLine(this.mSingleLine);
    }

    private void setKeyListenerOnly(KeyListener keyListener) {
        if (this.mEditor == null && keyListener == null) {
            return;
        }
        createEditorIfNeeded();
        if (this.mEditor.mKeyListener != keyListener) {
            this.mEditor.mKeyListener = keyListener;
            if (keyListener != null) {
                CharSequence charSequence = this.mText;
                if (!(charSequence instanceof Editable)) {
                    lambda$setTextAsync$0(charSequence);
                }
            }
            setFilters((Editable) this.mText, this.mFilters);
        }
    }

    public final MovementMethod getMovementMethod() {
        return this.mMovement;
    }

    public final void setMovementMethod(MovementMethod movementMethod) {
        if (this.mMovement != movementMethod) {
            this.mMovement = movementMethod;
            if (movementMethod != null && this.mSpannable == null) {
                lambda$setTextAsync$0(this.mText);
            }
            fixFocusableAndClickableSettings();
            Editor editor = this.mEditor;
            if (editor != null) {
                editor.prepareCursorControllers();
            }
        }
    }

    private void fixFocusableAndClickableSettings() {
        Editor editor;
        if (this.mMovement != null || ((editor = this.mEditor) != null && editor.mKeyListener != null)) {
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

    public final void setTransformationMethod(TransformationMethod transformationMethod) {
        Editor editor = this.mEditor;
        if (editor != null) {
            editor.setTransformationMethod(transformationMethod);
        } else {
            setTransformationMethodInternal(transformationMethod, true);
        }
    }

    void setTransformationMethodInternal(TransformationMethod transformationMethod, boolean z) {
        Spannable spannable;
        TransformationMethod transformationMethod2 = this.mTransformation;
        if (transformationMethod == transformationMethod2) {
            return;
        }
        if (transformationMethod2 != null && (spannable = this.mSpannable) != null) {
            spannable.removeSpan(transformationMethod2);
        }
        this.mTransformation = transformationMethod;
        if (transformationMethod instanceof TransformationMethod2) {
            TransformationMethod2 transformationMethod22 = (TransformationMethod2) transformationMethod;
            boolean z2 = (isTextSelectable() || (this.mText instanceof Editable)) ? false : true;
            this.mAllowTransformationLengthChange = z2;
            transformationMethod22.setLengthChangesAllowed(z2);
        } else {
            this.mAllowTransformationLengthChange = false;
        }
        if (z) {
            if (Flags.insertModeNotUpdateSelection()) {
                TransformationMethod transformationMethod3 = this.mTransformation;
                if (transformationMethod3 == null) {
                    this.mTransformed = this.mText;
                } else {
                    this.mTransformed = transformationMethod3.getTransformation(this.mText, this);
                }
                if (this.mTransformed == null) {
                    this.mTransformed = "";
                }
                boolean z3 = this.mTransformed instanceof OffsetMapping;
                TransformationMethod transformationMethod4 = this.mTransformation;
                if (transformationMethod4 != null) {
                    CharSequence charSequence = this.mText;
                    if ((charSequence instanceof Spannable) && (!this.mAllowTransformationLengthChange || z3)) {
                        ((Spannable) charSequence).setSpan(transformationMethod4, 0, charSequence.length(), ((z3 ? 200 : 0) << 16) | 18);
                    }
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
        Drawables drawables = this.mDrawables;
        if (drawables == null || drawables.mShowing[1] == null) {
            return this.mPaddingTop;
        }
        return this.mPaddingTop + drawables.mDrawablePadding + drawables.mDrawableSizeTop;
    }

    public int getCompoundPaddingBottom() {
        Drawables drawables = this.mDrawables;
        if (drawables == null || drawables.mShowing[3] == null) {
            return this.mPaddingBottom;
        }
        return this.mPaddingBottom + drawables.mDrawablePadding + drawables.mDrawableSizeBottom;
    }

    public int getCompoundPaddingLeft() {
        Drawables drawables = this.mDrawables;
        if (drawables == null || drawables.mShowing[0] == null) {
            return this.mPaddingLeft;
        }
        return this.mPaddingLeft + drawables.mDrawablePadding + drawables.mDrawableSizeLeft;
    }

    public int getCompoundPaddingRight() {
        Drawables drawables = this.mDrawables;
        if (drawables == null || drawables.mShowing[2] == null) {
            return this.mPaddingRight;
        }
        return this.mPaddingRight + drawables.mDrawablePadding + drawables.mDrawableSizeRight;
    }

    public int getCompoundPaddingStart() {
        resolveDrawables();
        if (getLayoutDirection() != 1) {
            return getCompoundPaddingLeft();
        }
        return getCompoundPaddingRight();
    }

    public int getCompoundPaddingEnd() {
        resolveDrawables();
        if (getLayoutDirection() != 1) {
            return getCompoundPaddingRight();
        }
        return getCompoundPaddingLeft();
    }

    public int getExtendedPaddingTop() {
        int i;
        if (this.mMaxMode != 1) {
            return getCompoundPaddingTop();
        }
        if (this.mLayout == null) {
            assumeLayout();
        }
        if (this.mLayout.getLineCount() <= this.mMaximum) {
            return getCompoundPaddingTop();
        }
        int compoundPaddingTop = getCompoundPaddingTop();
        int height = (getHeight() - compoundPaddingTop) - getCompoundPaddingBottom();
        int lineTop = this.mLayout.getLineTop(this.mMaximum);
        return (lineTop < height && (i = this.mGravity & 112) != 48) ? i == 80 ? (compoundPaddingTop + height) - lineTop : compoundPaddingTop + ((height - lineTop) / 2) : compoundPaddingTop;
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
        int compoundPaddingTop = getCompoundPaddingTop();
        int compoundPaddingBottom = getCompoundPaddingBottom();
        int height = (getHeight() - compoundPaddingTop) - compoundPaddingBottom;
        int lineTop = this.mLayout.getLineTop(this.mMaximum);
        if (lineTop < height) {
            int i = this.mGravity & 112;
            if (i == 48) {
                return (compoundPaddingBottom + height) - lineTop;
            }
            if (i != 80) {
                return compoundPaddingBottom + ((height - lineTop) / 2);
            }
        }
        return compoundPaddingBottom;
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

    public void setCompoundDrawables(Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4) {
        Drawables drawables = this.mDrawables;
        if (drawables != null) {
            if (drawables.mDrawableStart != null) {
                drawables.mDrawableStart.setCallback(null);
            }
            drawables.mDrawableStart = null;
            if (drawables.mDrawableEnd != null) {
                drawables.mDrawableEnd.setCallback(null);
            }
            drawables.mDrawableEnd = null;
            drawables.mDrawableHeightStart = 0;
            drawables.mDrawableSizeStart = 0;
            drawables.mDrawableHeightEnd = 0;
            drawables.mDrawableSizeEnd = 0;
        }
        if (drawable != null || drawable2 != null || drawable3 != null || drawable4 != null) {
            if (drawables == null) {
                drawables = new Drawables(getContext());
                this.mDrawables = drawables;
            }
            this.mDrawables.mOverride = false;
            if (drawables.mShowing[0] != drawable && drawables.mShowing[0] != null) {
                drawables.mShowing[0].setCallback(null);
            }
            drawables.mShowing[0] = drawable;
            if (drawables.mShowing[1] != drawable2 && drawables.mShowing[1] != null) {
                drawables.mShowing[1].setCallback(null);
            }
            drawables.mShowing[1] = drawable2;
            if (drawables.mShowing[2] != drawable3 && drawables.mShowing[2] != null) {
                drawables.mShowing[2].setCallback(null);
            }
            drawables.mShowing[2] = drawable3;
            if (drawables.mShowing[3] != drawable4 && drawables.mShowing[3] != null) {
                drawables.mShowing[3].setCallback(null);
            }
            drawables.mShowing[3] = drawable4;
            Rect rect = drawables.mCompoundRect;
            int[] drawableState = getDrawableState();
            if (drawable != null) {
                drawable.setState(drawableState);
                drawable.copyBounds(rect);
                drawable.setCallback(this);
                drawables.mDrawableSizeLeft = rect.width();
                drawables.mDrawableHeightLeft = rect.height();
            } else {
                drawables.mDrawableHeightLeft = 0;
                drawables.mDrawableSizeLeft = 0;
            }
            if (drawable3 != null) {
                drawable3.setState(drawableState);
                drawable3.copyBounds(rect);
                drawable3.setCallback(this);
                drawables.mDrawableSizeRight = rect.width();
                drawables.mDrawableHeightRight = rect.height();
            } else {
                drawables.mDrawableHeightRight = 0;
                drawables.mDrawableSizeRight = 0;
            }
            if (drawable2 != null) {
                drawable2.setState(drawableState);
                drawable2.copyBounds(rect);
                drawable2.setCallback(this);
                drawables.mDrawableSizeTop = rect.height();
                drawables.mDrawableWidthTop = rect.width();
            } else {
                drawables.mDrawableWidthTop = 0;
                drawables.mDrawableSizeTop = 0;
            }
            if (drawable4 != null) {
                drawable4.setState(drawableState);
                drawable4.copyBounds(rect);
                drawable4.setCallback(this);
                drawables.mDrawableSizeBottom = rect.height();
                drawables.mDrawableWidthBottom = rect.width();
            } else {
                drawables.mDrawableWidthBottom = 0;
                drawables.mDrawableSizeBottom = 0;
            }
        } else if (drawables != null) {
            if (!drawables.hasMetadata()) {
                this.mDrawables = null;
            } else {
                for (int length = drawables.mShowing.length - 1; length >= 0; length--) {
                    if (drawables.mShowing[length] != null) {
                        drawables.mShowing[length].setCallback(null);
                    }
                    drawables.mShowing[length] = null;
                }
                drawables.mDrawableHeightLeft = 0;
                drawables.mDrawableSizeLeft = 0;
                drawables.mDrawableHeightRight = 0;
                drawables.mDrawableSizeRight = 0;
                drawables.mDrawableWidthTop = 0;
                drawables.mDrawableSizeTop = 0;
                drawables.mDrawableWidthBottom = 0;
                drawables.mDrawableSizeBottom = 0;
            }
        }
        if (drawables != null) {
            drawables.mDrawableLeftInitial = drawable;
            drawables.mDrawableRightInitial = drawable3;
        }
        resetResolvedDrawables();
        resolveDrawables();
        applyCompoundDrawableTint();
        invalidate();
        requestLayout();
    }

    @RemotableViewMethod
    public void setCompoundDrawablesWithIntrinsicBounds(int i, int i2, int i3, int i4) {
        Context context = getContext();
        setCompoundDrawablesWithIntrinsicBounds(i != 0 ? context.getDrawable(i) : null, i2 != 0 ? context.getDrawable(i2) : null, i3 != 0 ? context.getDrawable(i3) : null, i4 != 0 ? context.getDrawable(i4) : null);
    }

    @RemotableViewMethod
    public void setCompoundDrawablesWithIntrinsicBounds(Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4) {
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        }
        if (drawable3 != null) {
            drawable3.setBounds(0, 0, drawable3.getIntrinsicWidth(), drawable3.getIntrinsicHeight());
        }
        if (drawable2 != null) {
            drawable2.setBounds(0, 0, drawable2.getIntrinsicWidth(), drawable2.getIntrinsicHeight());
        }
        if (drawable4 != null) {
            drawable4.setBounds(0, 0, drawable4.getIntrinsicWidth(), drawable4.getIntrinsicHeight());
        }
        setCompoundDrawables(drawable, drawable2, drawable3, drawable4);
    }

    @RemotableViewMethod
    public void setCompoundDrawablesRelative(Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4) {
        Drawables drawables = this.mDrawables;
        if (drawables != null) {
            if (drawables.mShowing[0] != null) {
                drawables.mShowing[0].setCallback(null);
            }
            Drawable[] drawableArr = drawables.mShowing;
            drawables.mDrawableLeftInitial = null;
            drawableArr[0] = null;
            if (drawables.mShowing[2] != null) {
                drawables.mShowing[2].setCallback(null);
            }
            Drawable[] drawableArr2 = drawables.mShowing;
            drawables.mDrawableRightInitial = null;
            drawableArr2[2] = null;
            drawables.mDrawableHeightLeft = 0;
            drawables.mDrawableSizeLeft = 0;
            drawables.mDrawableHeightRight = 0;
            drawables.mDrawableSizeRight = 0;
        }
        if (drawable != null || drawable2 != null || drawable3 != null || drawable4 != null) {
            if (drawables == null) {
                drawables = new Drawables(getContext());
                this.mDrawables = drawables;
            }
            this.mDrawables.mOverride = true;
            if (drawables.mDrawableStart != drawable && drawables.mDrawableStart != null) {
                drawables.mDrawableStart.setCallback(null);
            }
            drawables.mDrawableStart = drawable;
            if (drawables.mShowing[1] != drawable2 && drawables.mShowing[1] != null) {
                drawables.mShowing[1].setCallback(null);
            }
            drawables.mShowing[1] = drawable2;
            if (drawables.mDrawableEnd != drawable3 && drawables.mDrawableEnd != null) {
                drawables.mDrawableEnd.setCallback(null);
            }
            drawables.mDrawableEnd = drawable3;
            if (drawables.mShowing[3] != drawable4 && drawables.mShowing[3] != null) {
                drawables.mShowing[3].setCallback(null);
            }
            drawables.mShowing[3] = drawable4;
            Rect rect = drawables.mCompoundRect;
            int[] drawableState = getDrawableState();
            if (drawable != null) {
                drawable.setState(drawableState);
                drawable.copyBounds(rect);
                drawable.setCallback(this);
                drawables.mDrawableSizeStart = rect.width();
                drawables.mDrawableHeightStart = rect.height();
            } else {
                drawables.mDrawableHeightStart = 0;
                drawables.mDrawableSizeStart = 0;
            }
            if (drawable3 != null) {
                drawable3.setState(drawableState);
                drawable3.copyBounds(rect);
                drawable3.setCallback(this);
                drawables.mDrawableSizeEnd = rect.width();
                drawables.mDrawableHeightEnd = rect.height();
            } else {
                drawables.mDrawableHeightEnd = 0;
                drawables.mDrawableSizeEnd = 0;
            }
            if (drawable2 != null) {
                drawable2.setState(drawableState);
                drawable2.copyBounds(rect);
                drawable2.setCallback(this);
                drawables.mDrawableSizeTop = rect.height();
                drawables.mDrawableWidthTop = rect.width();
            } else {
                drawables.mDrawableWidthTop = 0;
                drawables.mDrawableSizeTop = 0;
            }
            if (drawable4 != null) {
                drawable4.setState(drawableState);
                drawable4.copyBounds(rect);
                drawable4.setCallback(this);
                drawables.mDrawableSizeBottom = rect.height();
                drawables.mDrawableWidthBottom = rect.width();
            } else {
                drawables.mDrawableWidthBottom = 0;
                drawables.mDrawableSizeBottom = 0;
            }
        } else if (drawables != null) {
            if (!drawables.hasMetadata()) {
                this.mDrawables = null;
            } else {
                if (drawables.mDrawableStart != null) {
                    drawables.mDrawableStart.setCallback(null);
                }
                drawables.mDrawableStart = null;
                if (drawables.mShowing[1] != null) {
                    drawables.mShowing[1].setCallback(null);
                }
                drawables.mShowing[1] = null;
                if (drawables.mDrawableEnd != null) {
                    drawables.mDrawableEnd.setCallback(null);
                }
                drawables.mDrawableEnd = null;
                if (drawables.mShowing[3] != null) {
                    drawables.mShowing[3].setCallback(null);
                }
                drawables.mShowing[3] = null;
                drawables.mDrawableHeightStart = 0;
                drawables.mDrawableSizeStart = 0;
                drawables.mDrawableHeightEnd = 0;
                drawables.mDrawableSizeEnd = 0;
                drawables.mDrawableWidthTop = 0;
                drawables.mDrawableSizeTop = 0;
                drawables.mDrawableWidthBottom = 0;
                drawables.mDrawableSizeBottom = 0;
            }
        }
        resetResolvedDrawables();
        resolveDrawables();
        invalidate();
        requestLayout();
    }

    @RemotableViewMethod
    public void setCompoundDrawablesRelativeWithIntrinsicBounds(int i, int i2, int i3, int i4) {
        Context context = getContext();
        setCompoundDrawablesRelativeWithIntrinsicBounds(i != 0 ? context.getDrawable(i) : null, i2 != 0 ? context.getDrawable(i2) : null, i3 != 0 ? context.getDrawable(i3) : null, i4 != 0 ? context.getDrawable(i4) : null);
    }

    @RemotableViewMethod
    public void setCompoundDrawablesRelativeWithIntrinsicBounds(Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4) {
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        }
        if (drawable3 != null) {
            drawable3.setBounds(0, 0, drawable3.getIntrinsicWidth(), drawable3.getIntrinsicHeight());
        }
        if (drawable2 != null) {
            drawable2.setBounds(0, 0, drawable2.getIntrinsicWidth(), drawable2.getIntrinsicHeight());
        }
        if (drawable4 != null) {
            drawable4.setBounds(0, 0, drawable4.getIntrinsicWidth(), drawable4.getIntrinsicHeight());
        }
        setCompoundDrawablesRelative(drawable, drawable2, drawable3, drawable4);
    }

    public Drawable[] getCompoundDrawables() {
        Drawables drawables = this.mDrawables;
        if (drawables != null) {
            return (Drawable[]) drawables.mShowing.clone();
        }
        return new Drawable[]{null, null, null, null};
    }

    public Drawable[] getCompoundDrawablesRelative() {
        Drawables drawables = this.mDrawables;
        if (drawables != null) {
            return new Drawable[]{drawables.mDrawableStart, drawables.mShowing[1], drawables.mDrawableEnd, drawables.mShowing[3]};
        }
        return new Drawable[]{null, null, null, null};
    }

    @RemotableViewMethod
    public void setCompoundDrawablePadding(int i) {
        Drawables drawables = this.mDrawables;
        if (i != 0) {
            if (drawables == null) {
                drawables = new Drawables(getContext());
                this.mDrawables = drawables;
            }
            drawables.mDrawablePadding = i;
        } else if (drawables != null) {
            drawables.mDrawablePadding = i;
        }
        invalidate();
        requestLayout();
    }

    public int getCompoundDrawablePadding() {
        Drawables drawables = this.mDrawables;
        if (drawables != null) {
            return drawables.mDrawablePadding;
        }
        return 0;
    }

    public void setCompoundDrawableTintList(ColorStateList colorStateList) {
        if (this.mDrawables == null) {
            this.mDrawables = new Drawables(getContext());
        }
        this.mDrawables.mTintList = colorStateList;
        this.mDrawables.mHasTint = true;
        applyCompoundDrawableTint();
    }

    public ColorStateList getCompoundDrawableTintList() {
        Drawables drawables = this.mDrawables;
        if (drawables != null) {
            return drawables.mTintList;
        }
        return null;
    }

    public void setCompoundDrawableTintMode(PorterDuff.Mode mode) {
        setCompoundDrawableTintBlendMode(mode != null ? BlendMode.fromValue(mode.nativeInt) : null);
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
        BlendMode compoundDrawableTintBlendMode = getCompoundDrawableTintBlendMode();
        if (compoundDrawableTintBlendMode != null) {
            return BlendMode.blendModeToPorterDuffMode(compoundDrawableTintBlendMode);
        }
        return null;
    }

    public BlendMode getCompoundDrawableTintBlendMode() {
        Drawables drawables = this.mDrawables;
        if (drawables != null) {
            return drawables.mBlendMode;
        }
        return null;
    }

    private void applyCompoundDrawableTint() {
        Drawables drawables = this.mDrawables;
        if (drawables == null) {
            return;
        }
        if (drawables.mHasTint || this.mDrawables.mHasTintMode) {
            ColorStateList colorStateList = this.mDrawables.mTintList;
            BlendMode blendMode = this.mDrawables.mBlendMode;
            boolean z = this.mDrawables.mHasTint;
            boolean z2 = this.mDrawables.mHasTintMode;
            int[] drawableState = getDrawableState();
            for (Drawable drawable : this.mDrawables.mShowing) {
                if (drawable != null && drawable != this.mDrawables.mDrawableError) {
                    drawable.mutate();
                    if (z) {
                        drawable.setTintList(colorStateList);
                    }
                    if (z2) {
                        drawable.setTintBlendMode(blendMode);
                    }
                    if (drawable.isStateful()) {
                        drawable.setState(drawableState);
                    }
                }
            }
        }
    }

    @Override // android.view.View
    public void setPadding(int i, int i2, int i3, int i4) {
        if (i != this.mPaddingLeft || i3 != this.mPaddingRight || i2 != this.mPaddingTop || i4 != this.mPaddingBottom) {
            nullLayouts();
        }
        super.setPadding(i, i2, i3, i4);
        invalidate();
    }

    @Override // android.view.View
    public void setPaddingRelative(int i, int i2, int i3, int i4) {
        if (i != getPaddingStart() || i3 != getPaddingEnd() || i2 != this.mPaddingTop || i4 != this.mPaddingBottom) {
            nullLayouts();
        }
        super.setPaddingRelative(i, i2, i3, i4);
        invalidate();
    }

    public void setFirstBaselineToTopHeight(int i) {
        int i2;
        Preconditions.checkArgumentNonnegative(i);
        Paint.FontMetricsInt fontMetricsInt = getPaint().getFontMetricsInt();
        if (getIncludeFontPadding()) {
            i2 = fontMetricsInt.top;
        } else {
            i2 = fontMetricsInt.ascent;
        }
        if (i > Math.abs(i2)) {
            setPadding(getPaddingLeft(), i - (-i2), getPaddingRight(), getPaddingBottom());
        }
    }

    public void setLastBaselineToBottomHeight(int i) {
        int i2;
        Preconditions.checkArgumentNonnegative(i);
        Paint.FontMetricsInt fontMetricsInt = getPaint().getFontMetricsInt();
        if (getIncludeFontPadding()) {
            i2 = fontMetricsInt.bottom;
        } else {
            i2 = fontMetricsInt.descent;
        }
        if (i > Math.abs(i2)) {
            setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), i - i2);
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
    public void setTextSelectHandle(Drawable drawable) {
        Preconditions.checkNotNull(drawable, "The text select handle should not be null.");
        this.mTextSelectHandle = drawable;
        this.mTextSelectHandleRes = 0;
        Editor editor = this.mEditor;
        if (editor != null) {
            editor.loadHandleDrawables(true);
        }
    }

    @RemotableViewMethod
    public void setTextSelectHandle(int i) {
        Preconditions.checkArgument(i != 0, "The text select handle should be a valid drawable resource id.");
        setTextSelectHandle(this.mContext.getDrawable(i));
    }

    public Drawable getTextSelectHandle() {
        if (this.mTextSelectHandle == null && this.mTextSelectHandleRes != 0) {
            this.mTextSelectHandle = this.mContext.getDrawable(this.mTextSelectHandleRes);
        }
        return this.mTextSelectHandle;
    }

    @RemotableViewMethod
    public void setTextSelectHandleLeft(Drawable drawable) {
        Preconditions.checkNotNull(drawable, "The left text select handle should not be null.");
        this.mTextSelectHandleLeft = drawable;
        this.mTextSelectHandleLeftRes = 0;
        Editor editor = this.mEditor;
        if (editor != null) {
            editor.loadHandleDrawables(true);
        }
    }

    @RemotableViewMethod
    public void setTextSelectHandleLeft(int i) {
        Preconditions.checkArgument(i != 0, "The text select left handle should be a valid drawable resource id.");
        setTextSelectHandleLeft(this.mContext.getDrawable(i));
    }

    public Drawable getTextSelectHandleLeft() {
        if (this.mTextSelectHandleLeft == null && this.mTextSelectHandleLeftRes != 0) {
            this.mTextSelectHandleLeft = this.mContext.getDrawable(this.mTextSelectHandleLeftRes);
        }
        return this.mTextSelectHandleLeft;
    }

    @RemotableViewMethod
    public void setTextSelectHandleRight(Drawable drawable) {
        Preconditions.checkNotNull(drawable, "The right text select handle should not be null.");
        this.mTextSelectHandleRight = drawable;
        this.mTextSelectHandleRightRes = 0;
        Editor editor = this.mEditor;
        if (editor != null) {
            editor.loadHandleDrawables(true);
        }
    }

    @RemotableViewMethod
    public void setTextSelectHandleRight(int i) {
        Preconditions.checkArgument(i != 0, "The text select right handle should be a valid drawable resource id.");
        setTextSelectHandleRight(this.mContext.getDrawable(i));
    }

    public Drawable getTextSelectHandleRight() {
        if (this.mTextSelectHandleRight == null && this.mTextSelectHandleRightRes != 0) {
            this.mTextSelectHandleRight = this.mContext.getDrawable(this.mTextSelectHandleRightRes);
        }
        return this.mTextSelectHandleRight;
    }

    public void setTextCursorDrawable(Drawable drawable) {
        this.mCursorDrawable = drawable;
        this.mCursorDrawableRes = 0;
        Editor editor = this.mEditor;
        if (editor != null) {
            editor.loadCursorDrawable();
        }
    }

    public void setTextCursorDrawable(int i) {
        setTextCursorDrawable(i != 0 ? this.mContext.getDrawable(i) : null);
    }

    public Drawable getTextCursorDrawable() {
        if (this.mCursorDrawable == null && this.mCursorDrawableRes != 0) {
            this.mCursorDrawable = this.mContext.getDrawable(this.mCursorDrawableRes);
        }
        return this.mCursorDrawable;
    }

    public void setTextAppearance(int i) throws Resources.NotFoundException {
        setTextAppearance(this.mContext, i);
    }

    @Deprecated
    public void setTextAppearance(Context context, int i) throws Resources.NotFoundException {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(i, android.R.styleable.TextAppearance);
        TextAppearanceAttributes textAppearanceAttributes = new TextAppearanceAttributes();
        readTextAppearance(context, typedArrayObtainStyledAttributes, textAppearanceAttributes, false);
        typedArrayObtainStyledAttributes.recycle();
        applyTextAppearance(textAppearanceAttributes);
        this.mFontFamily = textAppearanceAttributes.mFontFamily;
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

    private void readTextAppearance(Context context, TypedArray typedArray, TextAppearanceAttributes textAppearanceAttributes, boolean z) {
        int i;
        int indexCount = typedArray.getIndexCount();
        for (int i2 = 0; i2 < indexCount; i2++) {
            int index = typedArray.getIndex(i2);
            if (z) {
                i = sAppearanceValues.get(index, -1);
                if (i == -1) {
                }
            } else {
                i = index;
            }
            switch (i) {
                case 0:
                    textAppearanceAttributes.mTextSize = typedArray.getDimensionPixelSize(index, textAppearanceAttributes.mTextSize);
                    textAppearanceAttributes.mTextSizeUnit = typedArray.peekValue(index).getComplexUnit();
                    break;
                case 1:
                    textAppearanceAttributes.mTypefaceIndex = typedArray.getInt(index, textAppearanceAttributes.mTypefaceIndex);
                    if (textAppearanceAttributes.mTypefaceIndex != -1 && !textAppearanceAttributes.mFontFamilyExplicit) {
                        textAppearanceAttributes.mFontFamily = null;
                        break;
                    } else {
                        break;
                    }
                    break;
                case 2:
                    textAppearanceAttributes.mTextStyle = typedArray.getInt(index, textAppearanceAttributes.mTextStyle);
                    break;
                case 3:
                    textAppearanceAttributes.mTextColor = typedArray.getColorStateList(index);
                    break;
                case 4:
                    textAppearanceAttributes.mTextColorHighlight = typedArray.getColor(index, textAppearanceAttributes.mTextColorHighlight);
                    break;
                case 5:
                    textAppearanceAttributes.mTextColorHint = typedArray.getColorStateList(index);
                    break;
                case 6:
                    textAppearanceAttributes.mTextColorLink = typedArray.getColorStateList(index);
                    break;
                case 7:
                    textAppearanceAttributes.mShadowColor = typedArray.getInt(index, textAppearanceAttributes.mShadowColor);
                    break;
                case 8:
                    textAppearanceAttributes.mShadowDx = typedArray.getFloat(index, textAppearanceAttributes.mShadowDx);
                    break;
                case 9:
                    textAppearanceAttributes.mShadowDy = typedArray.getFloat(index, textAppearanceAttributes.mShadowDy);
                    break;
                case 10:
                    textAppearanceAttributes.mShadowRadius = typedArray.getFloat(index, textAppearanceAttributes.mShadowRadius);
                    break;
                case 11:
                    textAppearanceAttributes.mAllCaps = typedArray.getBoolean(index, textAppearanceAttributes.mAllCaps);
                    break;
                case 12:
                    if (!context.isRestricted() && context.canLoadUnsafeResources()) {
                        try {
                            textAppearanceAttributes.mFontTypeface = typedArray.getFont(index);
                        } catch (Resources.NotFoundException | UnsupportedOperationException unused) {
                        }
                    }
                    if (textAppearanceAttributes.mFontTypeface == null) {
                        textAppearanceAttributes.mFontFamily = typedArray.getString(index);
                    }
                    textAppearanceAttributes.mFontFamilyExplicit = true;
                    break;
                case 13:
                    textAppearanceAttributes.mHasElegant = true;
                    textAppearanceAttributes.mElegant = typedArray.getBoolean(index, textAppearanceAttributes.mElegant);
                    break;
                case 14:
                    textAppearanceAttributes.mHasLetterSpacing = true;
                    textAppearanceAttributes.mLetterSpacing = typedArray.getFloat(index, textAppearanceAttributes.mLetterSpacing);
                    break;
                case 15:
                    textAppearanceAttributes.mFontFeatureSettings = typedArray.getString(index);
                    break;
                case 16:
                    textAppearanceAttributes.mFontVariationSettings = typedArray.getString(index);
                    break;
                case 17:
                    textAppearanceAttributes.mHasFallbackLineSpacing = true;
                    textAppearanceAttributes.mFallbackLineSpacing = typedArray.getBoolean(index, textAppearanceAttributes.mFallbackLineSpacing);
                    break;
                case 18:
                    textAppearanceAttributes.mFontWeight = typedArray.getInt(index, textAppearanceAttributes.mFontWeight);
                    break;
                case 19:
                    String string = typedArray.getString(index);
                    if (string != null) {
                        LocaleList localeListForLanguageTags = LocaleList.forLanguageTags(string);
                        if (localeListForLanguageTags.isEmpty()) {
                            break;
                        } else {
                            textAppearanceAttributes.mTextLocales = localeListForLanguageTags;
                            break;
                        }
                    } else {
                        break;
                    }
                case 20:
                    textAppearanceAttributes.mHasLineBreakStyle = true;
                    textAppearanceAttributes.mLineBreakStyle = typedArray.getInt(index, textAppearanceAttributes.mLineBreakStyle);
                    break;
                case 21:
                    textAppearanceAttributes.mHasLineBreakWordStyle = true;
                    textAppearanceAttributes.mLineBreakWordStyle = typedArray.getInt(index, textAppearanceAttributes.mLineBreakWordStyle);
                    break;
                case 22:
                    textAppearanceAttributes.mSearchResultHighlightColor = typedArray.getColor(index, textAppearanceAttributes.mSearchResultHighlightColor);
                    break;
                case 23:
                    textAppearanceAttributes.mFocusedSearchResultHighlightColor = typedArray.getColor(index, textAppearanceAttributes.mFocusedSearchResultHighlightColor);
                    break;
            }
        }
    }

    private void applyTextAppearance(TextAppearanceAttributes textAppearanceAttributes) {
        if (textAppearanceAttributes.mTextColor != null) {
            setTextColor(textAppearanceAttributes.mTextColor);
        }
        if (textAppearanceAttributes.mTextColorHint != null) {
            setHintTextColor(textAppearanceAttributes.mTextColorHint);
        }
        if (textAppearanceAttributes.mTextColorLink != null) {
            setLinkTextColor(textAppearanceAttributes.mTextColorLink);
        }
        if (textAppearanceAttributes.mTextColorHighlight != 0) {
            setHighlightColor(textAppearanceAttributes.mTextColorHighlight);
        }
        if (textAppearanceAttributes.mSearchResultHighlightColor != 0) {
            setSearchResultHighlightColor(textAppearanceAttributes.mSearchResultHighlightColor);
        }
        if (textAppearanceAttributes.mFocusedSearchResultHighlightColor != 0) {
            setFocusedSearchResultHighlightColor(textAppearanceAttributes.mFocusedSearchResultHighlightColor);
        }
        if (textAppearanceAttributes.mTextSize != -1) {
            this.mTextSizeUnit = textAppearanceAttributes.mTextSizeUnit;
            setRawTextSize(textAppearanceAttributes.mTextSize, true);
        }
        if (textAppearanceAttributes.mTextLocales != null) {
            setTextLocales(textAppearanceAttributes.mTextLocales);
        }
        if (textAppearanceAttributes.mTypefaceIndex != -1 && !textAppearanceAttributes.mFontFamilyExplicit) {
            textAppearanceAttributes.mFontFamily = null;
        }
        setTypefaceFromAttrs(textAppearanceAttributes.mFontTypeface, textAppearanceAttributes.mFontFamily, textAppearanceAttributes.mTypefaceIndex, textAppearanceAttributes.mTextStyle, textAppearanceAttributes.mFontWeight);
        if (textAppearanceAttributes.mShadowColor != 0) {
            setShadowLayer(textAppearanceAttributes.mShadowRadius, textAppearanceAttributes.mShadowDx, textAppearanceAttributes.mShadowDy, textAppearanceAttributes.mShadowColor);
        }
        if (textAppearanceAttributes.mAllCaps) {
            setTransformationMethod(new AllCapsTransformationMethod(getContext()));
        }
        if (textAppearanceAttributes.mHasElegant) {
            setElegantTextHeight(textAppearanceAttributes.mElegant);
        }
        if (textAppearanceAttributes.mHasFallbackLineSpacing) {
            setFallbackLineSpacing(textAppearanceAttributes.mFallbackLineSpacing);
        }
        if (textAppearanceAttributes.mHasLetterSpacing) {
            setLetterSpacing(textAppearanceAttributes.mLetterSpacing);
        }
        if (textAppearanceAttributes.mFontFeatureSettings != null) {
            setFontFeatureSettings(textAppearanceAttributes.mFontFeatureSettings);
        }
        if (textAppearanceAttributes.mFontVariationSettings != null) {
            setFontVariationSettings(textAppearanceAttributes.mFontVariationSettings);
        }
        if (textAppearanceAttributes.mHasLineBreakStyle || textAppearanceAttributes.mHasLineBreakWordStyle) {
            updateLineBreakConfigFromTextAppearance(textAppearanceAttributes.mHasLineBreakStyle, textAppearanceAttributes.mHasLineBreakWordStyle, textAppearanceAttributes.mLineBreakStyle, textAppearanceAttributes.mLineBreakWordStyle);
        }
    }

    private void updateLineBreakConfigFromTextAppearance(boolean z, boolean z2, int i, int i2) {
        boolean z3;
        boolean z4 = true;
        if (!z || this.mLineBreakStyle == i) {
            z3 = false;
        } else {
            this.mLineBreakStyle = i;
            z3 = true;
        }
        if (!z2 || this.mLineBreakWordStyle == i2) {
            z4 = z3;
        } else {
            this.mLineBreakWordStyle = i2;
        }
        if (!z4 || this.mLayout == null) {
            return;
        }
        nullLayouts();
        requestLayout();
        invalidate();
    }

    public Locale getTextLocale() {
        return this.mTextPaint.getTextLocale();
    }

    public LocaleList getTextLocales() {
        return this.mTextPaint.getTextLocales();
    }

    private void changeListenerLocaleTo(Locale locale) {
        Editor editor;
        KeyListener dateTimeKeyListener;
        if (this.mListenerChanged || (editor = this.mEditor) == null) {
            return;
        }
        KeyListener keyListener = editor.mKeyListener;
        if (keyListener instanceof DigitsKeyListener) {
            dateTimeKeyListener = DigitsKeyListener.getInstance(locale, (DigitsKeyListener) keyListener);
        } else if (keyListener instanceof DateKeyListener) {
            dateTimeKeyListener = DateKeyListener.getInstance(locale);
        } else if (keyListener instanceof TimeKeyListener) {
            dateTimeKeyListener = TimeKeyListener.getInstance(locale);
        } else if (!(keyListener instanceof DateTimeKeyListener)) {
            return;
        } else {
            dateTimeKeyListener = DateTimeKeyListener.getInstance(locale);
        }
        boolean zIsPasswordInputType = isPasswordInputType(this.mEditor.mInputType);
        setKeyListenerOnly(dateTimeKeyListener);
        setInputTypeFromEditor();
        if (zIsPasswordInputType) {
            int i = this.mEditor.mInputType & 15;
            if (i == 1) {
                this.mEditor.mInputType |= 128;
            } else if (i == 2) {
                this.mEditor.mInputType |= 16;
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

    public void setTextLocales(LocaleList localeList) {
        this.mLocalesChanged = true;
        this.mTextPaint.setTextLocales(localeList);
        if (this.mLayout != null) {
            nullLayouts();
            requestLayout();
            invalidate();
        }
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        Editor editor;
        super.onConfigurationChanged(configuration);
        if (this.mCursorThicknessScale != configuration.semCursorThicknessScale) {
            this.mCursorThicknessScale = configuration.semCursorThicknessScale;
        }
        boolean z = configuration.semButtonShapeEnabled == 1;
        if (this.mButtonShapeSettingEnabled != z) {
            this.mButtonShapeSettingEnabled = z;
        }
        boolean z2 = (configuration.uiMode & 48) == 32;
        if (this.mIsNightMode != z2) {
            this.mIsNightMode = z2;
        }
        if (!this.mLocalesChanged) {
            this.mTextPaint.setTextLocales(LocaleList.getDefault());
            if (this.mLayout != null) {
                nullLayouts();
                requestLayout();
                invalidate();
            }
        }
        if (this.mFontWeightAdjustment != configuration.fontWeightAdjustment || (configuration.fontWeightAdjustment > 0 && configuration.FlipFont > 0)) {
            this.mFontWeightAdjustment = configuration.fontWeightAdjustment;
            setTypeface(getTypeface());
        }
        InputMethodManager inputMethodManager = getInputMethodManager();
        if (this.mLastOrientation != configuration.orientation && inputMethodManager != null && inputMethodManager.hasActiveInputConnection(this)) {
            inputMethodManager.restartInput(this);
        }
        this.mLastOrientation = configuration.orientation;
        if (!(this instanceof ExtractEditText) && (editor = this.mEditor) != null) {
            editor.onScrollChanged();
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
    public void setTextSize(float f) {
        setTextSize(2, f);
    }

    public void setTextSize(int i, float f) {
        if (isAutoSizeEnabled()) {
            return;
        }
        setTextSizeInternal(i, f, true);
    }

    private DisplayMetrics getDisplayMetricsOrSystem() {
        Resources resources;
        Context context = getContext();
        if (context == null) {
            resources = Resources.getSystem();
        } else {
            resources = context.getResources();
        }
        return resources.getDisplayMetrics();
    }

    private void setTextSizeInternal(int i, float f, boolean z) {
        this.mTextSizeUnit = i;
        setRawTextSize(TypedValue.applyDimension(i, f, getDisplayMetricsOrSystem()), z);
    }

    private void setRawTextSize(float f, boolean z) {
        if (f != this.mTextPaint.getTextSize()) {
            this.mTextPaint.setTextSize(f);
            maybeRecalculateLineHeight();
            if (!z || this.mLayout == null) {
                return;
            }
            this.mNeedsAutoSizeText = false;
            nullLayouts();
            requestLayout();
            invalidate();
        }
    }

    public int getTextSizeUnit() {
        return this.mTextSizeUnit;
    }

    public float getTextScaleX() {
        return this.mTextPaint.getTextScaleX();
    }

    @RemotableViewMethod
    public void setTextScaleX(float f) {
        if (f != this.mTextPaint.getTextScaleX()) {
            this.mUserSetTextScaleX = true;
            this.mTextPaint.setTextScaleX(f);
            if (this.mLayout != null) {
                nullLayouts();
                requestLayout();
                invalidate();
            }
        }
    }

    public void setTypeface(Typeface typeface) {
        this.mOriginalTypeface = typeface;
        int i = this.mFontWeightAdjustment;
        if (i != 0 && i != Integer.MAX_VALUE) {
            if (typeface == null) {
                if (Flags.fixNullTypefaceBolding()) {
                    typeface = Typeface.DEFAULT_BOLD;
                } else {
                    typeface = Typeface.DEFAULT;
                }
            } else {
                typeface = Typeface.create(typeface, Math.min(Math.max(typeface.getWeight() + this.mFontWeightAdjustment, 1), 1000), ((typeface != null ? typeface.getStyle() : 0) & 2) != 0);
            }
        }
        if (this.mTextPaint.getTypeface() != typeface) {
            this.mTextPaint.setTypeface(typeface);
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

    public void setElegantTextHeight(boolean z) {
        if (z != this.mTextPaint.isElegantTextHeight()) {
            this.mTextPaint.setElegantTextHeight(z);
            if (this.mLayout != null) {
                nullLayouts();
                requestLayout();
                invalidate();
            }
        }
    }

    public void setFallbackLineSpacing(boolean z) {
        int i;
        if (z) {
            i = CompatChanges.isChangeEnabled(BORINGLAYOUT_FALLBACK_LINESPACING) ? 2 : 1;
        } else {
            i = 0;
        }
        if (this.mUseFallbackLineSpacing != i) {
            this.mUseFallbackLineSpacing = i;
            if (this.mLayout != null) {
                nullLayouts();
                requestLayout();
                invalidate();
            }
        }
    }

    public void setUseBoundsForWidth(boolean z) {
        if (this.mUseBoundsForWidth != z) {
            this.mUseBoundsForWidth = z;
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

    public void setShiftDrawingOffsetForStartOverhang(boolean z) {
        if (this.mShiftDrawingOffsetForStartOverhang != z) {
            this.mShiftDrawingOffsetForStartOverhang = z;
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

    public void setMinimumFontMetrics(Paint.FontMetrics fontMetrics) {
        this.mMinimumFontMetrics = fontMetrics;
    }

    public Paint.FontMetrics getMinimumFontMetrics() {
        return this.mMinimumFontMetrics;
    }

    public boolean isLocalePreferredLineHeightForMinimumUsed() {
        return this.mUseLocalePreferredLineHeightForMinimum;
    }

    public void setLocalePreferredLineHeightForMinimumUsed(boolean z) {
        this.mUseLocalePreferredLineHeightForMinimum = z;
    }

    public boolean isFallbackLineSpacing() {
        return this.mUseFallbackLineSpacing != 0;
    }

    private boolean isFallbackLineSpacingForBoringLayout() {
        return this.mUseFallbackLineSpacing == 2;
    }

    boolean isFallbackLineSpacingForStaticLayout() {
        int i = this.mUseFallbackLineSpacing;
        return i == 2 || i == 1;
    }

    public boolean isElegantTextHeight() {
        return this.mTextPaint.isElegantTextHeight();
    }

    public float getLetterSpacing() {
        return this.mTextPaint.getLetterSpacing();
    }

    @RemotableViewMethod
    public void setLetterSpacing(float f) {
        if (f != this.mTextPaint.getLetterSpacing()) {
            this.mTextPaint.setLetterSpacing(f);
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

    public void setBreakStrategy(int i) {
        this.mBreakStrategy = i;
        if (this.mLayout != null) {
            nullLayouts();
            requestLayout();
            invalidate();
        }
    }

    public int getBreakStrategy() {
        return this.mBreakStrategy;
    }

    public void setHyphenationFrequency(int i) {
        this.mHyphenationFrequency = i;
        if (this.mLayout != null) {
            nullLayouts();
            requestLayout();
            invalidate();
        }
    }

    public int getHyphenationFrequency() {
        return this.mHyphenationFrequency;
    }

    public void setLineBreakStyle(int i) {
        if (this.mLineBreakStyle != i) {
            this.mLineBreakStyle = i;
            if (this.mLayout != null) {
                nullLayouts();
                requestLayout();
                invalidate();
            }
        }
    }

    public void setLineBreakWordStyle(int i) {
        if (this.mLineBreakWordStyle != i) {
            this.mLineBreakWordStyle = i;
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
    public void setJustificationMode(int i) {
        this.mJustificationMode = i;
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
    public void setFontFeatureSettings(String str) {
        if (str != this.mTextPaint.getFontFeatureSettings()) {
            this.mTextPaint.setFontFeatureSettings(str);
            if (this.mLayout != null) {
                nullLayouts();
                requestLayout();
                invalidate();
            }
        }
    }

    @RemotableViewMethod
    public boolean setFontVariationSettings(String str) {
        String fontVariationSettings = this.mTextPaint.getFontVariationSettings();
        boolean fontVariationSettings2 = true;
        if (str != fontVariationSettings && (str == null || !str.equals(fontVariationSettings))) {
            if (Flags.typefaceRedesignReadonly()) {
                int i = this.mFontWeightAdjustment;
                if (i != 0 && i != Integer.MAX_VALUE) {
                    List<FontVariationAxis> listFromFontVariationSettingsForList = FontVariationAxis.fromFontVariationSettingsForList(str);
                    if (listFromFontVariationSettingsForList == null) {
                        return false;
                    }
                    boolean z = false;
                    for (int i2 = 0; i2 < listFromFontVariationSettingsForList.size(); i2++) {
                        FontVariationAxis fontVariationAxis = listFromFontVariationSettingsForList.get(i2);
                        if (fontVariationAxis.getOpenTypeTagValue() == 2003265652) {
                            listFromFontVariationSettingsForList.set(i2, new FontVariationAxis("wght", Math.clamp(fontVariationAxis.getStyleValue() + this.mFontWeightAdjustment, 1.0f, 1000.0f)));
                            z = true;
                        }
                    }
                    if (!z) {
                        listFromFontVariationSettingsForList.add(new FontVariationAxis("wght", Math.clamp(this.mFontWeightAdjustment + 400, 1, 1000)));
                    }
                    this.mTextPaint.setFontVariationSettings(FontVariationAxis.toFontVariationSettings(listFromFontVariationSettingsForList));
                } else {
                    this.mTextPaint.setFontVariationSettings(str);
                }
            } else {
                fontVariationSettings2 = this.mTextPaint.setFontVariationSettings(str);
            }
            if (fontVariationSettings2 && this.mLayout != null) {
                nullLayouts();
                requestLayout();
                invalidate();
            }
        }
        return fontVariationSettings2;
    }

    @RemotableViewMethod
    public void setTextColor(int i) {
        this.mTextColor = ColorStateList.valueOf(i);
        updateTextColors();
    }

    @RemotableViewMethod
    public void setTextColor(ColorStateList colorStateList) {
        colorStateList.getClass();
        this.mTextColor = colorStateList;
        updateTextColors();
    }

    public final ColorStateList getTextColors() {
        return this.mTextColor;
    }

    public final int getCurrentTextColor() {
        return this.mCurTextColor;
    }

    @RemotableViewMethod
    public void setHighlightColor(int i) {
        if (this.mHighlightColor != i) {
            this.mHighlightColor = i;
            invalidate();
        }
    }

    public int getHighlightColor() {
        return this.mHighlightColor;
    }

    @RemotableViewMethod
    public final void setShowSoftInputOnFocus(boolean z) {
        createEditorIfNeeded();
        this.mEditor.mShowSoftInputOnFocus = z;
    }

    public final boolean getShowSoftInputOnFocus() {
        Editor editor = this.mEditor;
        return editor == null || editor.mShowSoftInputOnFocus;
    }

    public void setShadowLayer(float f, float f2, float f3, int i) {
        this.mTextPaint.setShadowLayer(f, f2, f3, i);
        this.mShadowRadius = f;
        this.mShadowDx = f2;
        this.mShadowDy = f3;
        this.mShadowColor = i;
        Editor editor = this.mEditor;
        if (editor != null) {
            editor.invalidateTextDisplayList();
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
    public final void setAutoLinkMask(int i) {
        this.mAutoLinkMask = i;
    }

    @RemotableViewMethod
    public final void setLinksClickable(boolean z) {
        this.mLinksClickable = z;
    }

    public final boolean getLinksClickable() {
        return this.mLinksClickable;
    }

    public URLSpan[] getUrls() {
        CharSequence charSequence = this.mText;
        if (charSequence instanceof Spanned) {
            return (URLSpan[]) ((Spanned) charSequence).getSpans(0, charSequence.length(), URLSpan.class);
        }
        return new URLSpan[0];
    }

    @RemotableViewMethod
    public final void setHintTextColor(int i) {
        this.mHintTextColor = ColorStateList.valueOf(i);
        updateTextColors();
    }

    public final void setHintTextColor(ColorStateList colorStateList) {
        this.mHintTextColor = colorStateList;
        updateTextColors();
    }

    public final ColorStateList getHintTextColors() {
        return this.mHintTextColor;
    }

    public final int getCurrentHintTextColor() {
        return this.mHintTextColor != null ? this.mCurHintTextColor : this.mCurTextColor;
    }

    @RemotableViewMethod
    public final void setLinkTextColor(int i) {
        this.mLinkTextColor = ColorStateList.valueOf(i);
        updateTextColors();
    }

    public final void setLinkTextColor(ColorStateList colorStateList) {
        this.mLinkTextColor = colorStateList;
        updateTextColors();
    }

    public final ColorStateList getLinkTextColors() {
        return this.mLinkTextColor;
    }

    @RemotableViewMethod
    public void setGravity(int i) {
        if ((i & Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK) == 0) {
            i |= Gravity.START;
        }
        if ((i & 112) == 0) {
            i |= 48;
        }
        int i2 = i & Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK;
        int i3 = this.mGravity;
        boolean z = i2 != (8388615 & i3);
        if (i != i3) {
            invalidate();
        }
        this.mGravity = i;
        Layout layout = this.mLayout;
        if (layout == null || !z) {
            return;
        }
        int width = layout.getWidth();
        Layout layout2 = this.mHintLayout;
        int width2 = layout2 != null ? layout2.getWidth() : 0;
        BoringLayout.Metrics metrics = UNKNOWN_BORING;
        makeNewLayout(width, width2, metrics, metrics, ((this.mRight - this.mLeft) - getCompoundPaddingLeft()) - getCompoundPaddingRight(), true);
    }

    public int getGravity() {
        return this.mGravity;
    }

    public int getPaintFlags() {
        return this.mTextPaint.getFlags();
    }

    @RemotableViewMethod
    public void setPaintFlags(int i) {
        if (this.mTextPaint.getFlags() != i) {
            this.mTextPaint.setFlags(i);
            if (this.mLayout != null) {
                nullLayouts();
                requestLayout();
                invalidate();
            }
        }
    }

    public void setHorizontallyScrolling(boolean z) {
        if (this.mHorizontallyScrolling != z) {
            this.mHorizontallyScrolling = z;
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
    public void setMinLines(int i) {
        this.mMinimum = i;
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
    public void setMinHeight(int i) {
        this.mMinimum = i;
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
    public void setMaxLines(int i) {
        this.mMaximum = i;
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
    public void setMaxHeight(int i) {
        this.mMaximum = i;
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
    public void setLines(int i) {
        this.mMinimum = i;
        this.mMaximum = i;
        this.mMinMode = 1;
        this.mMaxMode = 1;
        this.mTextEffectLines = i;
        requestLayout();
        invalidate();
    }

    @RemotableViewMethod
    public void setHeight(int i) {
        this.mMinimum = i;
        this.mMaximum = i;
        this.mMinMode = 2;
        this.mMaxMode = 2;
        requestLayout();
        invalidate();
    }

    @RemotableViewMethod
    public void setMinEms(int i) {
        this.mMinWidth = i;
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
    public void setMinWidth(int i) {
        this.mMinWidth = i;
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
    public void setMaxEms(int i) {
        this.mMaxWidth = i;
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
    public void setMaxWidth(int i) {
        this.mMaxWidth = i;
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
    public void setEms(int i) {
        this.mMinWidth = i;
        this.mMaxWidth = i;
        this.mMinWidthMode = 1;
        this.mMaxWidthMode = 1;
        requestLayout();
        invalidate();
    }

    @RemotableViewMethod
    public void setWidth(int i) {
        this.mMinWidth = i;
        this.mMaxWidth = i;
        this.mMinWidthMode = 2;
        this.mMaxWidthMode = 2;
        requestLayout();
        invalidate();
    }

    public void setLineSpacing(float f, float f2) {
        if (this.mSpacingAdd == f && this.mSpacingMult == f2) {
            return;
        }
        this.mSpacingAdd = f;
        this.mSpacingMult = f2;
        if (this.mLayout != null) {
            nullLayouts();
            requestLayout();
            invalidate();
        }
    }

    public float getLineSpacingMultiplier() {
        return this.mSpacingMult;
    }

    public float getLineSpacingExtra() {
        return this.mSpacingAdd;
    }

    @RemotableViewMethod
    public void setLineHeight(int i) {
        setLineHeightPx(i);
    }

    private void setLineHeightPx(float f) {
        Preconditions.checkArgumentNonNegative(f, "Expecting non-negative lineHeight while the input is " + f);
        float fontMetricsInt = (float) getPaint().getFontMetricsInt(null);
        if (f != fontMetricsInt) {
            setLineSpacing(f - fontMetricsInt, 1.0f);
            this.mLineHeightComplexDimen = TypedValue.createComplexDimension(f, 0);
        }
    }

    @RemotableViewMethod
    public void setLineHeight(int i, float f) {
        DisplayMetrics displayMetricsOrSystem = getDisplayMetricsOrSystem();
        if (!FontScaleConverterFactory.isNonLinearFontScalingActive(getResources().getConfiguration().fontScale) || i != 2 || this.mTextSizeUnit != 2) {
            setLineHeightPx(TypedValue.applyDimension(i, f, displayMetricsOrSystem));
            this.mLineHeightComplexDimen = TypedValue.createComplexDimension(f, i);
        } else {
            float textSize = getTextSize();
            setLineHeightPx(textSize * (f / TypedValue.convertPixelsToDimension(2, textSize, displayMetricsOrSystem)));
            this.mLineHeightComplexDimen = TypedValue.createComplexDimension(f, i);
        }
    }

    private void maybeRecalculateLineHeight() {
        int unitFromComplexDimension;
        int i = this.mLineHeightComplexDimen;
        if (i != 0 && (unitFromComplexDimension = TypedValue.getUnitFromComplexDimension(i)) == 2) {
            setLineHeight(unitFromComplexDimension, TypedValue.complexToFloat(this.mLineHeightComplexDimen));
        }
    }

    public void setHighlights(Highlights highlights) {
        this.mHighlights = highlights;
        this.mHighlightPathsBogus = true;
        invalidate();
    }

    public Highlights getHighlights() {
        return this.mHighlights;
    }

    public void setSearchResultHighlights(int... iArr) {
        if (iArr == null) {
            this.mSearchResultHighlights = null;
            this.mHighlightPathsBogus = true;
            return;
        }
        if (iArr.length % 2 == 1) {
            throw new IllegalArgumentException("Flatten ranges must have even numbered elements");
        }
        for (int i = 0; i < iArr.length / 2; i++) {
            int i2 = i * 2;
            int i3 = iArr[i2];
            int i4 = iArr[i2 + 1];
            if (i3 > i4) {
                throw new IllegalArgumentException("Reverse range found in the flatten range: " + i3 + ", " + i4 + " at " + i + "-th range");
            }
        }
        this.mHighlightPathsBogus = true;
        this.mSearchResultHighlights = iArr;
        this.mFocusedSearchResultIndex = -1;
        invalidate();
    }

    public int[] getSearchResultHighlights() {
        return this.mSearchResultHighlights;
    }

    public void setFocusedSearchResultIndex(int i) {
        int[] iArr = this.mSearchResultHighlights;
        if (iArr == null) {
            throw new IllegalArgumentException("Search result range must be set beforehand.");
        }
        if (i < -1 || i >= iArr.length / 2) {
            throw new IllegalArgumentException("Focused index(" + i + ") must be larger than -1 and less than range count(" + (this.mSearchResultHighlights.length / 2) + NavigationBarInflaterView.KEY_CODE_END);
        }
        this.mFocusedSearchResultIndex = i;
        this.mHighlightPathsBogus = true;
        invalidate();
    }

    public int getFocusedSearchResultIndex() {
        return this.mFocusedSearchResultIndex;
    }

    public void setSearchResultHighlightColor(int i) {
        this.mSearchResultHighlightColor = i;
    }

    public int getSearchResultHighlightColor() {
        return this.mSearchResultHighlightColor;
    }

    public void setFocusedSearchResultHighlightColor(int i) {
        this.mFocusedSearchResultHighlightColor = i;
    }

    public int getFocusedSearchResultHighlightColor() {
        return this.mFocusedSearchResultHighlightColor;
    }

    private void setSelectGesturePreviewHighlight(int i, int i2) {
        setGesturePreviewHighlight(i, i2, this.mHighlightColor);
    }

    private void setDeleteGesturePreviewHighlight(int i, int i2) {
        setGesturePreviewHighlight(i, i2, ColorUtils.setAlphaComponent(this.mTextColor.getDefaultColor(), (int) (Color.alpha(r0) * 0.2f)));
    }

    private void setGesturePreviewHighlight(int i, int i2, int i3) {
        this.mGesturePreviewHighlightStart = i;
        this.mGesturePreviewHighlightEnd = i2;
        if (this.mGesturePreviewHighlightPaint == null) {
            Paint paint = new Paint();
            this.mGesturePreviewHighlightPaint = paint;
            paint.setStyle(Paint.Style.FILL);
        }
        this.mGesturePreviewHighlightPaint.setColor(i3);
        Editor editor = this.mEditor;
        if (editor != null) {
            editor.hideCursorAndSpanControllers();
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

    public final void append(CharSequence charSequence) {
        append(charSequence, 0, charSequence.length());
    }

    public void append(CharSequence charSequence, int i, int i2) {
        CharSequence charSequence2 = this.mText;
        if (!(charSequence2 instanceof Editable)) {
            setText(charSequence2, BufferType.EDITABLE);
        }
        ((Editable) this.mText).append(charSequence, i, i2);
        int i3 = this.mAutoLinkMask;
        if (i3 == 0 || !Linkify.addLinks(this.mSpannable, i3) || !this.mLinksClickable || textCanBeSelected()) {
            return;
        }
        setMovementMethod(LinkMovementMethod.getInstance());
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0041  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void updateTextColors() {
        boolean z;
        int colorForState;
        int colorForState2;
        int[] drawableState = getDrawableState();
        int colorForState3 = this.mTextColor.getColorForState(drawableState, 0);
        this.mButtonShapeColor = colorForState3;
        boolean z2 = true;
        if (colorForState3 != this.mCurTextColor) {
            this.mCurTextColor = colorForState3;
            z = true;
        } else {
            z = false;
        }
        ColorStateList colorStateList = this.mLinkTextColor;
        if (colorStateList != null && (colorForState2 = colorStateList.getColorForState(drawableState, 0)) != this.mTextPaint.linkColor) {
            this.mTextPaint.linkColor = colorForState2;
            z = true;
        }
        ColorStateList colorStateList2 = this.mHintTextColor;
        if (colorStateList2 == null || (colorForState = colorStateList2.getColorForState(drawableState, 0)) == this.mCurHintTextColor) {
            z2 = z;
        } else {
            this.mCurHintTextColor = colorForState;
            if (this.mText.length() != 0) {
            }
        }
        if (z2) {
            Editor editor = this.mEditor;
            if (editor != null) {
                editor.invalidateTextDisplayList();
            }
            invalidate();
        }
    }

    @Override // android.view.View
    protected void drawableStateChanged() {
        ColorStateList colorStateList;
        ColorStateList colorStateList2;
        super.drawableStateChanged();
        ColorStateList colorStateList3 = this.mTextColor;
        if ((colorStateList3 != null && colorStateList3.isStateful()) || (((colorStateList = this.mHintTextColor) != null && colorStateList.isStateful()) || ((colorStateList2 = this.mLinkTextColor) != null && colorStateList2.isStateful()))) {
            updateTextColors();
        }
        if (this.mDrawables != null) {
            int[] drawableState = getDrawableState();
            for (Drawable drawable : this.mDrawables.mShowing) {
                if (drawable != null && drawable.isStateful() && drawable.setState(drawableState)) {
                    invalidateDrawable(drawable);
                }
            }
        }
    }

    @Override // android.view.View
    public void drawableHotspotChanged(float f, float f2) {
        super.drawableHotspotChanged(f, f2);
        Drawables drawables = this.mDrawables;
        if (drawables != null) {
            for (Drawable drawable : drawables.mShowing) {
                if (drawable != null) {
                    drawable.setHotspot(f, f2);
                }
            }
        }
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        int selectionStart;
        int selectionEnd;
        Parcelable parcelableOnSaveInstanceState = super.onSaveInstanceState();
        boolean freezesText = getFreezesText();
        boolean z = false;
        if (this.mText != null) {
            selectionStart = getSelectionStart();
            selectionEnd = getSelectionEnd();
            if (selectionStart >= 0 || selectionEnd >= 0) {
                z = true;
            }
        } else {
            selectionStart = -1;
            selectionEnd = -1;
        }
        if (!freezesText && !z) {
            return parcelableOnSaveInstanceState;
        }
        SavedState savedState = new SavedState(parcelableOnSaveInstanceState);
        if (freezesText) {
            CharSequence charSequence = this.mText;
            if (charSequence instanceof Spanned) {
                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(this.mText);
                if (this.mEditor != null) {
                    removeMisspelledSpans(spannableStringBuilder);
                    spannableStringBuilder.removeSpan(this.mEditor.mSuggestionRangeSpan);
                }
                savedState.text = spannableStringBuilder;
            } else {
                savedState.text = charSequence.toString();
            }
        }
        if (z) {
            savedState.selStart = selectionStart;
            savedState.selEnd = selectionEnd;
        }
        if (isFocused() && selectionStart >= 0 && selectionEnd >= 0) {
            savedState.frozenWithFocus = true;
        }
        savedState.error = getError();
        Editor editor = this.mEditor;
        if (editor != null) {
            savedState.editorState = editor.saveInstanceState();
        }
        return savedState;
    }

    void removeMisspelledSpans(Spannable spannable) {
        SuggestionSpan[] suggestionSpanArr = (SuggestionSpan[]) spannable.getSpans(0, spannable.length(), SuggestionSpan.class);
        for (int i = 0; i < suggestionSpanArr.length; i++) {
            int flags = suggestionSpanArr[i].getFlags();
            if ((flags & 1) != 0 && (flags & 2) != 0) {
                spannable.removeSpan(suggestionSpanArr[i]);
            }
        }
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        String str;
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        if (savedState.text != null) {
            lambda$setTextAsync$0(savedState.text);
        }
        if (savedState.selStart >= 0 && savedState.selEnd >= 0 && this.mSpannable != null) {
            int length = this.mText.length();
            if (savedState.selStart > length || savedState.selEnd > length) {
                if (savedState.text == null) {
                    str = "";
                } else {
                    str = "(restored) ";
                }
                Log.e(LOG_TAG, "Saved cursor position " + savedState.selStart + "/" + savedState.selEnd + " out of range for " + str + "text " + ((Object) this.mText));
            } else {
                Selection.setSelection(this.mSpannable, savedState.selStart, savedState.selEnd);
                if (savedState.frozenWithFocus) {
                    createEditorIfNeeded();
                    this.mEditor.mFrozenWithFocus = true;
                }
            }
        }
        if (savedState.error != null) {
            final CharSequence charSequence = savedState.error;
            post(new Runnable() { // from class: android.widget.TextView.1
                @Override // java.lang.Runnable
                public void run() {
                    if (TextView.this.mEditor == null || !TextView.this.mEditor.mErrorWasChanged) {
                        TextView.this.setError(charSequence);
                    }
                }
            });
        }
        if (savedState.editorState != null) {
            createEditorIfNeeded();
            this.mEditor.restoreInstanceState(savedState.editorState);
        }
    }

    @RemotableViewMethod
    public void setFreezesText(boolean z) {
        this.mFreezesText = z;
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
    public final void lambda$setTextAsync$0(CharSequence charSequence) {
        setText(charSequence, this.mBufferType);
    }

    public Runnable setTextAsync(final CharSequence charSequence) {
        return new Runnable() { // from class: android.widget.TextView$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$setTextAsync$0(charSequence);
            }
        };
    }

    @RemotableViewMethod
    public final void setTextKeepState(CharSequence charSequence) {
        setTextKeepState(charSequence, this.mBufferType);
    }

    public void setText(CharSequence charSequence, BufferType bufferType) {
        setText(charSequence, bufferType, true, 0);
        this.mCharWrapper = null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:160:0x0299  */
    /* JADX WARN: Removed duplicated region for block: B:163:0x02a6  */
    /* JADX WARN: Removed duplicated region for block: B:164:0x02aa  */
    /* JADX WARN: Removed duplicated region for block: B:167:0x02b1  */
    /* JADX WARN: Removed duplicated region for block: B:168:0x02b7  */
    /* JADX WARN: Removed duplicated region for block: B:171:0x02be  */
    /* JADX WARN: Removed duplicated region for block: B:177:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r14v0 */
    /* JADX WARN: Type inference failed for: r14v1 */
    /* JADX WARN: Type inference failed for: r14v2, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r14v3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void setText(CharSequence charSequence, BufferType bufferType, boolean z, int i) {
        int length;
        CharSequence charSequenceNewSpannable;
        ?? r14;
        Editor editor;
        boolean z2;
        Spannable spannableNewSpannable;
        BufferType bufferType2 = bufferType;
        Editor editor2 = this.mEditor;
        if (editor2 != null) {
            editor2.beforeSetText();
        }
        this.mTextSetFromXmlOrResourceId = false;
        CharSequence charSequenceRemoveSuggestionSpans = charSequence == null ? "" : charSequence;
        if (!isSuggestionsEnabled()) {
            charSequenceRemoveSuggestionSpans = removeSuggestionSpans(charSequenceRemoveSuggestionSpans);
        }
        if (!this.mUserSetTextScaleX) {
            this.mTextPaint.setTextScaleX(1.0f);
        }
        if ((charSequenceRemoveSuggestionSpans instanceof Spanned) && ((Spanned) charSequenceRemoveSuggestionSpans).getSpanStart(TextUtils.TruncateAt.MARQUEE) >= 0) {
            if (ViewConfiguration.get(this.mContext).isFadingMarqueeEnabled()) {
                setHorizontalFadingEdgeEnabled(true);
                this.mMarqueeFadeMode = 0;
            } else {
                setHorizontalFadingEdgeEnabled(false);
                this.mMarqueeFadeMode = 1;
            }
            setEllipsize(TextUtils.TruncateAt.MARQUEE);
        }
        int length2 = this.mFilters.length;
        CharSequence charSequence2 = charSequenceRemoveSuggestionSpans;
        int i2 = 0;
        while (i2 < length2) {
            CharSequence charSequenceFilter = this.mFilters[i2].filter(charSequence2, 0, charSequence2.length(), EMPTY_SPANNED, 0, 0);
            if (charSequenceFilter != null) {
                charSequence2 = charSequenceFilter;
            }
            i2++;
            charSequence2 = charSequence2;
        }
        if (z) {
            CharSequence charSequence3 = this.mText;
            if (charSequence3 == null) {
                sendBeforeTextChanged("", 0, 0, charSequence2.length());
                length = i;
            } else {
                length = charSequence3.length();
                sendBeforeTextChanged(this.mText, 0, length, charSequence2.length());
            }
        } else {
            length = i;
        }
        ArrayList<TextWatcher> arrayList = this.mListeners;
        boolean z3 = (arrayList == null || arrayList.size() == 0) ? false : true;
        PrecomputedText precomputedText = charSequence2 instanceof PrecomputedText ? (PrecomputedText) charSequence2 : null;
        if (bufferType2 == BufferType.EDITABLE || getKeyListener() != null || z3) {
            createEditorIfNeeded();
            this.mEditor.forgetUndoRedo();
            this.mEditor.scheduleRestartInputForSetText();
            Editable editableNewEditable = this.mEditableFactory.newEditable(charSequence2);
            setFilters(editableNewEditable, this.mFilters);
            charSequenceNewSpannable = editableNewEditable;
        } else if (precomputedText != null) {
            if (this.mTextDir == null) {
                this.mTextDir = getTextDirectionHeuristic();
            }
            int iCheckResultUsable = precomputedText.getParams().checkResultUsable(getPaint(), this.mTextDir, this.mBreakStrategy, this.mHyphenationFrequency, LineBreakConfig.getLineBreakConfig(this.mLineBreakStyle, this.mLineBreakWordStyle));
            charSequenceNewSpannable = charSequence2;
            if (iCheckResultUsable == 0) {
                throw new IllegalArgumentException("PrecomputedText's Parameters don't match the parameters of this TextView.Consider using setTextMetricsParams(precomputedText.getParams()) to override the settings of this TextView: PrecomputedText: " + precomputedText.getParams() + "TextView: " + getTextMetricsParams());
            }
            if (iCheckResultUsable == 1) {
                PrecomputedText.create(precomputedText, getTextMetricsParams());
                charSequenceNewSpannable = charSequence2;
            }
        } else if (bufferType2 == BufferType.SPANNABLE || this.mMovement != null) {
            charSequenceNewSpannable = this.mSpannableFactory.newSpannable(charSequence2);
        } else {
            boolean z4 = charSequence2 instanceof CharWrapper;
            charSequenceNewSpannable = charSequence2;
            if (!z4) {
                charSequenceNewSpannable = TextUtils.stringOrSpannedString(charSequence2);
            }
        }
        if (ViewRune.WIDGET_PEN_SUPPORTED) {
            this.mDisplayText = null;
            this.mUseDisplayText = false;
        }
        int iTextOrSpanChanged = AccessibilityManager.getInstance(this.mContext).isEnabled() ? AccessibilityUtils.textOrSpanChanged(charSequenceNewSpannable, this.mText) : 0;
        CharSequence charSequence4 = charSequenceNewSpannable;
        if (this.mAutoLinkMask != 0) {
            if (bufferType2 == BufferType.EDITABLE || (charSequenceNewSpannable instanceof Spannable)) {
                spannableNewSpannable = (Spannable) charSequenceNewSpannable;
            } else {
                spannableNewSpannable = this.mSpannableFactory.newSpannable(charSequenceNewSpannable);
            }
            charSequence4 = charSequenceNewSpannable;
            if (Linkify.addLinks(spannableNewSpannable, this.mAutoLinkMask)) {
                bufferType2 = bufferType2 == BufferType.EDITABLE ? BufferType.EDITABLE : BufferType.SPANNABLE;
                setTextInternal(spannableNewSpannable);
                if (iTextOrSpanChanged == 0) {
                    iTextOrSpanChanged = 2;
                }
                if (this.mLinksClickable && !textCanBeSelected()) {
                    setMovementMethod(LinkMovementMethod.getInstance());
                }
                charSequence4 = spannableNewSpannable;
            }
        }
        boolean zIsEmpty = TextUtils.isEmpty(charSequence4);
        CharSequence charSequence5 = charSequence4;
        if (!zIsEmpty) {
            boolean z5 = charSequence4 instanceof SpannedString;
            charSequence5 = charSequence4;
            if (!z5) {
                boolean z6 = charSequence4 instanceof Spannable;
                charSequence5 = charSequence4;
                if (!z6) {
                    boolean zSemNeedMoreWidth = TextUtils.semNeedMoreWidth(charSequence4.charAt(charSequence4.length() - 1));
                    charSequence5 = charSequence4;
                    if (zSemNeedMoreWidth) {
                        charSequence5 = charSequence4.toString() + (char) 160;
                    }
                }
            }
        }
        this.mBufferType = bufferType2;
        setTextInternal(charSequence5);
        TransformationMethod transformationMethod = this.mTransformation;
        if (transformationMethod == null) {
            this.mTransformed = charSequence5;
        } else {
            this.mTransformed = transformationMethod.getTransformation(charSequence5, this);
        }
        if (this.mTransformed == null) {
            this.mTransformed = "";
        }
        int length3 = charSequence5.length();
        boolean z7 = this.mTransformed instanceof OffsetMapping;
        if ((charSequence5 instanceof Spannable) && (!this.mAllowTransformationLengthChange || z7)) {
            Spannable spannable = (Spannable) charSequence5;
            for (ChangeWatcher changeWatcher : (ChangeWatcher[]) spannable.getSpans(0, spannable.length(), ChangeWatcher.class)) {
                spannable.removeSpan(changeWatcher);
            }
            if (this.mChangeWatcher == null) {
                this.mChangeWatcher = new ChangeWatcher();
            }
            spannable.setSpan(this.mChangeWatcher, 0, length3, 6553618);
            Editor editor3 = this.mEditor;
            if (editor3 != null) {
                editor3.addSpanWatchers(spannable);
            }
            TransformationMethod transformationMethod2 = this.mTransformation;
            if (transformationMethod2 != null) {
                z2 = false;
                spannable.setSpan(transformationMethod2, 0, length3, ((z7 ? 200 : 0) << 16) | 18);
            } else {
                z2 = false;
            }
            MovementMethod movementMethod = this.mMovement;
            if (movementMethod != null) {
                movementMethod.initialize(this, spannable);
                Editor editor4 = this.mEditor;
                if (editor4 != null) {
                    editor4.mSelectionMoved = z2;
                }
            }
        } else {
            if (ViewRune.WIDGET_PEN_SUPPORTED && !TextUtils.isEmpty(this.mTransformed)) {
                if (this.mTransformed.length() > 5000) {
                    r14 = 0;
                    semSetMultiSelectionEnabled(false);
                } else {
                    SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(this.mTransformed);
                    this.mDisplayText = spannableStringBuilder;
                    SpannableStringBuilder spannableStringBuilder2 = spannableStringBuilder;
                    if (this.mChangeWatcher == null) {
                        this.mChangeWatcher = new ChangeWatcher();
                    }
                    r14 = 0;
                    spannableStringBuilder2.setSpan(this.mChangeWatcher, 0, this.mDisplayText.length(), 6553618);
                    this.mUseDisplayText = true;
                    this.mSkipUpdateDisplayText = true;
                }
            }
            if (this.mLayout != null) {
                checkForRelayout();
            }
            sendOnTextChanged(charSequence5, r14, length, length3);
            onTextChanged(charSequence5, r14, length, length3);
            this.mHideHint = r14;
            if (iTextOrSpanChanged != 1) {
                notifyViewAccessibilityStateChangedIfNeeded(2);
            } else if (iTextOrSpanChanged == 2) {
                notifyViewAccessibilityStateChangedIfNeeded(r14);
            }
            if (!z3) {
                sendAfterTextChanged((Editable) charSequence5);
            } else {
                notifyListeningManagersAfterTextChanged();
            }
            editor = this.mEditor;
            if (editor == null) {
                editor.prepareCursorControllers();
                this.mEditor.maybeFireScheduledRestartInputForSetText();
                return;
            }
            return;
        }
        r14 = 0;
        if (this.mLayout != null) {
        }
        sendOnTextChanged(charSequence5, r14, length, length3);
        onTextChanged(charSequence5, r14, length, length3);
        this.mHideHint = r14;
        if (iTextOrSpanChanged != 1) {
        }
        if (!z3) {
        }
        editor = this.mEditor;
        if (editor == null) {
        }
    }

    public final void setText(char[] cArr, int i, int i2) {
        int length;
        if (i < 0 || i2 < 0 || i + i2 > cArr.length) {
            throw new IndexOutOfBoundsException(i + ", " + i2);
        }
        CharSequence charSequence = this.mText;
        if (charSequence != null) {
            length = charSequence.length();
            sendBeforeTextChanged(this.mText, 0, length, i2);
        } else {
            sendBeforeTextChanged("", 0, 0, i2);
            length = 0;
        }
        CharWrapper charWrapper = this.mCharWrapper;
        if (charWrapper == null) {
            this.mCharWrapper = new CharWrapper(cArr, i, i2);
        } else {
            charWrapper.set(cArr, i, i2);
        }
        setText(this.mCharWrapper, this.mBufferType, false, length);
    }

    public final void setTextKeepState(CharSequence charSequence, BufferType bufferType) {
        Spannable spannable;
        int selectionStart = getSelectionStart();
        int selectionEnd = getSelectionEnd();
        int length = charSequence.length();
        setText(charSequence, bufferType);
        if ((selectionStart >= 0 || selectionEnd >= 0) && (spannable = this.mSpannable) != null) {
            Selection.setSelection(spannable, Math.max(0, Math.min(selectionStart, length)), Math.max(0, Math.min(selectionEnd, length)));
        }
    }

    @RemotableViewMethod
    public final void setText(int i) {
        lambda$setTextAsync$0(getContext().getResources().getText(i));
        this.mTextSetFromXmlOrResourceId = true;
        this.mTextId = i;
    }

    public final void setText(int i, BufferType bufferType) {
        setText(getContext().getResources().getText(i), bufferType);
        this.mTextSetFromXmlOrResourceId = true;
        this.mTextId = i;
    }

    @RemotableViewMethod
    public final void setHint(CharSequence charSequence) {
        setHintInternal(charSequence);
        if (this.mEditor == null || !isInputMethodTarget()) {
            return;
        }
        this.mEditor.reportExtractedText();
    }

    private void setHintInternal(CharSequence charSequence) {
        this.mHideHint = false;
        this.mHint = TextUtils.stringOrSpannedString(charSequence);
        if (this.mLayout != null) {
            checkForRelayout();
        }
        if (this.mText.length() == 0) {
            invalidate();
        }
        if (this.mEditor == null || this.mText.length() != 0 || this.mHint == null) {
            return;
        }
        this.mEditor.invalidateTextDisplayList();
    }

    @RemotableViewMethod
    public final void setHint(int i) {
        this.mHintId = i;
        setHint(getContext().getResources().getText(i));
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

    CharSequence removeSuggestionSpans(CharSequence charSequence) {
        Spannable spannableNewSpannable;
        if (charSequence instanceof Spanned) {
            if (charSequence instanceof Spannable) {
                spannableNewSpannable = (Spannable) charSequence;
            } else {
                spannableNewSpannable = this.mSpannableFactory.newSpannable(charSequence);
            }
            SuggestionSpan[] suggestionSpanArr = (SuggestionSpan[]) spannableNewSpannable.getSpans(0, charSequence.length(), SuggestionSpan.class);
            if (suggestionSpanArr.length != 0) {
                for (SuggestionSpan suggestionSpan : suggestionSpanArr) {
                    spannableNewSpannable.removeSpan(suggestionSpan);
                }
                return spannableNewSpannable;
            }
        }
        return charSequence;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0056  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0061  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0070  */
    /* JADX WARN: Removed duplicated region for block: B:27:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void setInputType(int i) {
        TextView textView;
        boolean z;
        InputMethodManager inputMethodManager;
        boolean zIsPasswordInputType = isPasswordInputType(getInputType());
        boolean zIsVisiblePasswordInputType = isVisiblePasswordInputType(getInputType());
        boolean z2 = false;
        setInputType(i, false);
        boolean zIsPasswordInputType2 = isPasswordInputType(i);
        boolean zIsVisiblePasswordInputType2 = isVisiblePasswordInputType(i);
        if (zIsPasswordInputType2) {
            setTransformationMethod(PasswordTransformationMethod.getInstance());
        } else {
            if (zIsVisiblePasswordInputType2) {
                if (this.mTransformation == PasswordTransformationMethod.getInstance()) {
                    textView = this;
                    z2 = true;
                }
            } else if (zIsPasswordInputType || zIsVisiblePasswordInputType) {
                textView = this;
                textView.setTypefaceFromAttrs(null, null, -1, 0, -1);
                if (textView.mTransformation == PasswordTransformationMethod.getInstance()) {
                    z2 = true;
                }
            }
            z = !isMultilineInputType(i);
            if (textView.mSingleLine == z || z2) {
                textView.applySingleLine(z, !zIsPasswordInputType2, true, true);
            }
            if (!textView.isSuggestionsEnabled()) {
                textView.setTextInternal(textView.removeSuggestionSpans(textView.mText));
            }
            inputMethodManager = textView.getInputMethodManager();
            if (inputMethodManager == null) {
                inputMethodManager.restartInput(textView);
                return;
            }
            return;
        }
        textView = this;
        z = !isMultilineInputType(i);
        if (textView.mSingleLine == z) {
            textView.applySingleLine(z, !zIsPasswordInputType2, true, true);
        }
        if (!textView.isSuggestionsEnabled()) {
        }
        inputMethodManager = textView.getInputMethodManager();
        if (inputMethodManager == null) {
        }
    }

    boolean hasPasswordTransformationMethod() {
        return this.mTransformation instanceof PasswordTransformationMethod;
    }

    public boolean isAnyPasswordInputType() {
        int inputType = getInputType();
        return isPasswordInputType(inputType) || isVisiblePasswordInputType(inputType);
    }

    public void setRawInputType(int i) {
        if (i == 0 && this.mEditor == null) {
            return;
        }
        createEditorIfNeeded();
        this.mEditor.mInputType = i;
        ensureEditorFocusedNotifiedToHandwritingInitiator();
    }

    private void ensureEditorFocusedNotifiedToHandwritingInitiator() {
        ViewRootImpl viewRootImpl;
        if (android.view.inputmethod.Flags.initiationWithoutInputConnection() && !isHandwritingDelegate() && (viewRootImpl = getViewRootImpl()) != null && isFocused() && hasWindowFocus() && onCheckIsTextEditor()) {
            viewRootImpl.getHandwritingInitiator().onEditorFocused(this);
        }
    }

    @Override // android.view.View
    public String[] getAutofillHints() {
        String[] autofillHints = super.getAutofillHints();
        return (!isAnyPasswordInputType() || ArrayUtils.contains(autofillHints, View.AUTOFILL_HINT_PASSWORD_AUTO)) ? autofillHints : (String[]) ArrayUtils.appendElement(String.class, autofillHints, View.AUTOFILL_HINT_PASSWORD_AUTO);
    }

    private Locale getCustomLocaleForKeyListenerOrNull() {
        LocaleList imeHintLocales;
        if (this.mUseInternationalizedInput && (imeHintLocales = getImeHintLocales()) != null) {
            return imeHintLocales.get(0);
        }
        return null;
    }

    private void setInputType(int i, boolean z) {
        KeyListener textKeyListener;
        TextKeyListener.Capitalize capitalize;
        int i2 = i & 15;
        if (i2 == 1) {
            boolean z2 = (32768 & i) != 0;
            if ((i & 4096) != 0) {
                capitalize = TextKeyListener.Capitalize.CHARACTERS;
            } else if ((i & 8192) != 0) {
                capitalize = TextKeyListener.Capitalize.WORDS;
            } else if ((i & 16384) != 0) {
                capitalize = TextKeyListener.Capitalize.SENTENCES;
            } else {
                capitalize = TextKeyListener.Capitalize.NONE;
            }
            textKeyListener = TextKeyListener.getInstance(z2, capitalize);
        } else if (i2 == 2) {
            Locale customLocaleForKeyListenerOrNull = getCustomLocaleForKeyListenerOrNull();
            DigitsKeyListener digitsKeyListener = DigitsKeyListener.getInstance(customLocaleForKeyListenerOrNull, (i & 4096) != 0, (i & 8192) != 0);
            if (customLocaleForKeyListenerOrNull != null) {
                int inputType = digitsKeyListener.getInputType();
                if ((inputType & 15) != 2) {
                    if ((i & 16) != 0) {
                        inputType |= 128;
                    }
                    i = inputType;
                }
            }
            textKeyListener = digitsKeyListener;
        } else if (i2 == 4) {
            Locale customLocaleForKeyListenerOrNull2 = getCustomLocaleForKeyListenerOrNull();
            int i3 = i & InputType.TYPE_MASK_VARIATION;
            if (i3 == 16) {
                textKeyListener = DateKeyListener.getInstance(customLocaleForKeyListenerOrNull2);
            } else if (i3 == 32) {
                textKeyListener = TimeKeyListener.getInstance(customLocaleForKeyListenerOrNull2);
            } else {
                textKeyListener = DateTimeKeyListener.getInstance(customLocaleForKeyListenerOrNull2);
            }
            if (this.mUseInternationalizedInput) {
                i = textKeyListener.getInputType();
            }
        } else if (i2 == 3) {
            textKeyListener = DialerKeyListener.getInstance();
        } else {
            textKeyListener = TextKeyListener.getInstance();
        }
        setRawInputType(i);
        this.mListenerChanged = false;
        if (z) {
            createEditorIfNeeded();
            this.mEditor.mKeyListener = textKeyListener;
        } else {
            setKeyListenerOnly(textKeyListener);
        }
    }

    public int getInputType() {
        Editor editor = this.mEditor;
        if (editor == null) {
            return 0;
        }
        return editor.mInputType;
    }

    public void setImeOptions(int i) {
        createEditorIfNeeded();
        this.mEditor.createInputContentTypeIfNeeded();
        this.mEditor.mInputContentType.imeOptions = i;
    }

    public int getImeOptions() {
        Editor editor = this.mEditor;
        if (editor == null || editor.mInputContentType == null) {
            return 0;
        }
        return this.mEditor.mInputContentType.imeOptions;
    }

    public void setImeActionLabel(CharSequence charSequence, int i) {
        createEditorIfNeeded();
        this.mEditor.createInputContentTypeIfNeeded();
        this.mEditor.mInputContentType.imeActionLabel = charSequence;
        this.mEditor.mInputContentType.imeActionId = i;
    }

    public CharSequence getImeActionLabel() {
        Editor editor = this.mEditor;
        if (editor == null || editor.mInputContentType == null) {
            return null;
        }
        return this.mEditor.mInputContentType.imeActionLabel;
    }

    public int getImeActionId() {
        Editor editor = this.mEditor;
        if (editor == null || editor.mInputContentType == null) {
            return 0;
        }
        return this.mEditor.mInputContentType.imeActionId;
    }

    public void setOnEditorActionListener(OnEditorActionListener onEditorActionListener) {
        createEditorIfNeeded();
        this.mEditor.createInputContentTypeIfNeeded();
        this.mEditor.mInputContentType.onEditorActionListener = onEditorActionListener;
    }

    public void onEditorAction(int i) {
        Editor editor = this.mEditor;
        Editor.InputContentType inputContentType = editor == null ? null : editor.mInputContentType;
        if (inputContentType != null) {
            if (inputContentType.onEditorActionListener != null && inputContentType.onEditorActionListener.onEditorAction(this, i, null)) {
                return;
            }
            if (i == 5) {
                View viewFocusSearch = focusSearch(2);
                if (viewFocusSearch != null && !viewFocusSearch.requestFocus(2)) {
                    throw new IllegalStateException("focus search returned a view that wasn't able to take focus!");
                }
                return;
            }
            if (i == 7) {
                View viewFocusSearch2 = focusSearch(1);
                if (viewFocusSearch2 != null && !viewFocusSearch2.requestFocus(1)) {
                    throw new IllegalStateException("focus search returned a view that wasn't able to take focus!");
                }
                return;
            }
            if (i == 6) {
                InputMethodManager inputMethodManager = getInputMethodManager();
                if (inputMethodManager != null) {
                    inputMethodManager.hideSoftInputFromView(this, 0);
                    return;
                }
                return;
            }
        }
        ViewRootImpl viewRootImpl = getViewRootImpl();
        if (viewRootImpl != null) {
            long jUptimeMillis = SystemClock.uptimeMillis();
            viewRootImpl.dispatchKeyFromIme(new KeyEvent(jUptimeMillis, jUptimeMillis, 0, 66, 0, 0, -1, 0, 22));
            viewRootImpl.dispatchKeyFromIme(new KeyEvent(SystemClock.uptimeMillis(), jUptimeMillis, 1, 66, 0, 0, -1, 0, 22));
        }
    }

    public void setPrivateImeOptions(String str) {
        if (ViewRune.SUPPORT_WRITING_TOOLKIT) {
            this.mDisableWritingToolkitMenu = str != null && Arrays.asList(str.split(NavigationBarInflaterView.GRAVITY_SEPARATOR)).contains("disableWritingToolkit=true");
        }
        createEditorIfNeeded();
        this.mEditor.createInputContentTypeIfNeeded();
        this.mEditor.mInputContentType.privateImeOptions = str;
    }

    public String getPrivateImeOptions() {
        Editor editor = this.mEditor;
        if (editor == null || editor.mInputContentType == null) {
            return null;
        }
        return this.mEditor.mInputContentType.privateImeOptions;
    }

    public void setInputExtras(int i) throws XmlPullParserException, Resources.NotFoundException, IOException {
        createEditorIfNeeded();
        XmlResourceParser xml = getResources().getXml(i);
        this.mEditor.createInputContentTypeIfNeeded();
        this.mEditor.mInputContentType.extras = new Bundle();
        getResources().parseBundleExtras(xml, this.mEditor.mInputContentType.extras);
    }

    public Bundle getInputExtras(boolean z) {
        if (this.mEditor == null && !z) {
            return null;
        }
        createEditorIfNeeded();
        if (this.mEditor.mInputContentType == null) {
            if (!z) {
                return null;
            }
            this.mEditor.createInputContentTypeIfNeeded();
        }
        if (this.mEditor.mInputContentType.extras == null) {
            if (!z) {
                return null;
            }
            this.mEditor.mInputContentType.extras = new Bundle();
        }
        return this.mEditor.mInputContentType.extras;
    }

    public void setImeHintLocales(LocaleList localeList) {
        createEditorIfNeeded();
        this.mEditor.createInputContentTypeIfNeeded();
        this.mEditor.mInputContentType.imeHintLocales = localeList;
        if (this.mUseInternationalizedInput) {
            changeListenerLocaleTo(localeList == null ? null : localeList.get(0));
        }
    }

    public LocaleList getImeHintLocales() {
        Editor editor = this.mEditor;
        if (editor == null || editor.mInputContentType == null) {
            return null;
        }
        return this.mEditor.mInputContentType.imeHintLocales;
    }

    public CharSequence getError() {
        Editor editor = this.mEditor;
        if (editor == null) {
            return null;
        }
        return editor.mError;
    }

    @RemotableViewMethod
    public void setError(CharSequence charSequence) {
        if (charSequence == null) {
            setError(null, null);
            return;
        }
        Drawable drawable = getContext().getDrawable(R.drawable.indicator_input_error);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        setError(charSequence, drawable);
    }

    public void setError(CharSequence charSequence, Drawable drawable) {
        createEditorIfNeeded();
        this.mEditor.setError(charSequence, drawable);
        notifyViewAccessibilityStateChangedIfNeeded(3072);
    }

    @Override // android.view.View
    protected boolean setFrame(int i, int i2, int i3, int i4) throws Resources.NotFoundException {
        boolean frame = super.setFrame(i, i2, i3, i4);
        Editor editor = this.mEditor;
        if (editor != null) {
            editor.setFrame();
        }
        restartMarqueeIfNeeded();
        return frame;
    }

    private void restartMarqueeIfNeeded() {
        if (this.mRestartMarquee && this.mEllipsize == TextUtils.TruncateAt.MARQUEE) {
            this.mRestartMarquee = false;
            startMarquee();
        }
    }

    public void setFilters(InputFilter[] inputFilterArr) {
        if (inputFilterArr == null) {
            throw new IllegalArgumentException();
        }
        this.mFilters = inputFilterArr;
        CharSequence charSequence = this.mText;
        if (charSequence instanceof Editable) {
            setFilters((Editable) charSequence, inputFilterArr);
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

    private void setFilters(Editable editable, InputFilter[] inputFilterArr) {
        Editor editor = this.mEditor;
        if (editor != null) {
            int i = 1;
            int i2 = editor.mUndoInputFilter != null ? 1 : 0;
            boolean z = this.mEditor.mKeyListener instanceof InputFilter;
            int i3 = z ? i2 + 1 : i2;
            if (i3 > 0) {
                InputFilter[] inputFilterArr2 = new InputFilter[inputFilterArr.length + i3];
                System.arraycopy(inputFilterArr, 0, inputFilterArr2, 0, inputFilterArr.length);
                if (i2 != 0) {
                    inputFilterArr2[inputFilterArr.length] = this.mEditor.mUndoInputFilter;
                } else {
                    i = 0;
                }
                if (z) {
                    inputFilterArr2[inputFilterArr.length + i] = (InputFilter) this.mEditor.mKeyListener;
                }
                editable.setFilters(inputFilterArr2);
                return;
            }
        }
        editable.setFilters(inputFilterArr);
    }

    public InputFilter[] getFilters() {
        return this.mFilters;
    }

    private int getBoxHeight(Layout layout) {
        int extendedPaddingTop;
        int extendedPaddingBottom;
        Insets opticalInsets = isLayoutModeOptical(this.mParent) ? getOpticalInsets() : Insets.NONE;
        if (layout == this.mHintLayout) {
            extendedPaddingTop = getCompoundPaddingTop();
            extendedPaddingBottom = getCompoundPaddingBottom();
        } else {
            extendedPaddingTop = getExtendedPaddingTop();
            extendedPaddingBottom = getExtendedPaddingBottom();
        }
        return (getMeasuredHeight() - (extendedPaddingTop + extendedPaddingBottom)) + opticalInsets.top + opticalInsets.bottom;
    }

    int getVerticalOffset(boolean z) {
        int boxHeight;
        int height;
        Layout layout;
        int i = this.mGravity & 112;
        Layout layout2 = this.mLayout;
        if (!z && this.mText.length() == 0 && (layout = this.mHintLayout) != null) {
            layout2 = layout;
        }
        if (i == 48 || (height = layout2.getHeight()) >= (boxHeight = getBoxHeight(layout2))) {
            return 0;
        }
        return i == 80 ? boxHeight - height : (boxHeight - height) >> 1;
    }

    private int getBottomVerticalOffset(boolean z) {
        int boxHeight;
        int height;
        Layout layout;
        int i = this.mGravity & 112;
        Layout layout2 = this.mLayout;
        if (!z && this.mText.length() == 0 && (layout = this.mHintLayout) != null) {
            layout2 = layout;
        }
        if (i == 80 || (height = layout2.getHeight()) >= (boxHeight = getBoxHeight(layout2))) {
            return 0;
        }
        return i == 48 ? boxHeight - height : (boxHeight - height) >> 1;
    }

    void invalidateCursorPath() {
        if (this.mHighlightPathBogus) {
            invalidateCursor();
            return;
        }
        int compoundPaddingLeft = getCompoundPaddingLeft();
        int extendedPaddingTop = getExtendedPaddingTop() + getVerticalOffset(true);
        if (this.mEditor.mDrawableForCursor == null) {
            RectF rectF = TEMP_RECTF;
            synchronized (rectF) {
                float fCeil = (float) Math.ceil(this.mTextPaint.getStrokeWidth());
                if (fCeil < 1.0f) {
                    fCeil = 1.0f;
                }
                float f = fCeil / 2.0f;
                this.mHighlightPath.computeBounds(rectF, false);
                float f2 = compoundPaddingLeft;
                float f3 = extendedPaddingTop;
                invalidate((int) Math.floor((rectF.left + f2) - f), (int) Math.floor((rectF.top + f3) - f), (int) Math.ceil(f2 + rectF.right + f), (int) Math.ceil(f3 + rectF.bottom + f));
            }
            return;
        }
        Rect bounds = this.mEditor.mDrawableForCursor.getBounds();
        invalidate(bounds.left + compoundPaddingLeft, bounds.top + extendedPaddingTop, bounds.right + compoundPaddingLeft, bounds.bottom + extendedPaddingTop);
    }

    void invalidateCursor() {
        int selectionEnd = getSelectionEnd();
        invalidateCursor(selectionEnd, selectionEnd, selectionEnd);
    }

    private void invalidateCursor(int i, int i2, int i3) {
        if (i >= 0 || i2 >= 0 || i3 >= 0) {
            invalidateRegion(Math.min(Math.min(i, i2), i3), Math.max(Math.max(i, i2), i3), true);
        }
    }

    void invalidateRegion(int i, int i2, boolean z) {
        int width;
        Editor editor;
        if (this.mLayout == null) {
            invalidate();
            return;
        }
        int iOriginalToTransformed = originalToTransformed(i, 1);
        int iOriginalToTransformed2 = originalToTransformed(i2, 1);
        int lineForOffset = this.mLayout.getLineForOffset(iOriginalToTransformed);
        int lineTop = this.mLayout.getLineTop(lineForOffset);
        if (lineForOffset > 0) {
            lineTop -= this.mLayout.getLineDescent(lineForOffset - 1);
        }
        int lineForOffset2 = iOriginalToTransformed == iOriginalToTransformed2 ? lineForOffset : this.mLayout.getLineForOffset(iOriginalToTransformed2);
        int lineBottom = this.mLayout.getLineBottom(lineForOffset2);
        if (z && (editor = this.mEditor) != null && editor.mDrawableForCursor != null) {
            Rect bounds = this.mEditor.mDrawableForCursor.getBounds();
            lineTop = Math.min(lineTop, bounds.top);
            lineBottom = Math.max(lineBottom, bounds.bottom);
        }
        int compoundPaddingLeft = getCompoundPaddingLeft();
        int extendedPaddingTop = getExtendedPaddingTop() + getVerticalOffset(true);
        if (lineForOffset == lineForOffset2 && !z) {
            width = ((int) (this.mLayout.getPrimaryHorizontal(iOriginalToTransformed2) + 1.0d)) + compoundPaddingLeft;
            compoundPaddingLeft = ((int) this.mLayout.getPrimaryHorizontal(iOriginalToTransformed)) + compoundPaddingLeft;
        } else {
            width = getWidth() - getCompoundPaddingRight();
        }
        invalidate(this.mScrollX + compoundPaddingLeft, lineTop + extendedPaddingTop, this.mScrollX + width, extendedPaddingTop + lineBottom);
    }

    private void registerForPreDraw() {
        if (this.mPreDrawRegistered) {
            return;
        }
        getViewTreeObserver().addOnPreDrawListener(this);
        this.mPreDrawRegistered = true;
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
            int selectionEnd = getSelectionEnd();
            Editor editor = this.mEditor;
            if (editor != null && editor.mSelectionModifierCursorController != null && this.mEditor.mSelectionModifierCursorController.isSelectionStartDragged()) {
                selectionEnd = getSelectionStart();
            }
            if (selectionEnd < 0 && (this.mGravity & 112) == 80) {
                selectionEnd = this.mText.length();
            }
            if (selectionEnd >= 0) {
                bringPointIntoView(selectionEnd);
            }
        } else {
            bringTextIntoView();
        }
        Editor editor2 = this.mEditor;
        if (editor2 != null && editor2.mCreatedWithASelection) {
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
        Editor editor = this.mEditor;
        if (editor != null) {
            editor.onAttachedToWindow();
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
        Editor editor = this.mEditor;
        if (editor != null) {
            editor.onDetachedFromWindow();
        }
        startStopMarquee(false);
        super.onDetachedFromWindowInternal();
    }

    @Override // android.view.View
    public void onScreenStateChanged(int i) {
        super.onScreenStateChanged(i);
        Editor editor = this.mEditor;
        if (editor != null) {
            editor.onScreenStateChanged(i);
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
    protected boolean verifyDrawable(Drawable drawable) {
        Drawables drawables;
        boolean zVerifyDrawable = super.verifyDrawable(drawable);
        if (!zVerifyDrawable && (drawables = this.mDrawables) != null) {
            for (Drawable drawable2 : drawables.mShowing) {
                if (drawable == drawable2) {
                    return true;
                }
            }
        }
        return zVerifyDrawable;
    }

    @Override // android.view.View
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        Drawables drawables = this.mDrawables;
        if (drawables != null) {
            for (Drawable drawable : drawables.mShowing) {
                if (drawable != null) {
                    drawable.jumpToCurrentState();
                }
            }
        }
    }

    @Override // android.view.View, android.graphics.drawable.Drawable.Callback
    public void invalidateDrawable(Drawable drawable) {
        int i;
        int compoundPaddingTop;
        int i2;
        boolean z = false;
        if (verifyDrawable(drawable)) {
            Rect bounds = drawable.getBounds();
            int compoundPaddingRight = this.mScrollX;
            int i3 = this.mScrollY;
            Drawables drawables = this.mDrawables;
            if (drawables != null) {
                if (drawable == drawables.mShowing[0]) {
                    compoundPaddingTop = getCompoundPaddingTop();
                    int compoundPaddingBottom = ((this.mBottom - this.mTop) - getCompoundPaddingBottom()) - compoundPaddingTop;
                    compoundPaddingRight += this.mPaddingLeft;
                    i2 = (compoundPaddingBottom - drawables.mDrawableHeightLeft) / 2;
                } else if (drawable == drawables.mShowing[2]) {
                    compoundPaddingTop = getCompoundPaddingTop();
                    int compoundPaddingBottom2 = ((this.mBottom - this.mTop) - getCompoundPaddingBottom()) - compoundPaddingTop;
                    compoundPaddingRight += ((this.mRight - this.mLeft) - this.mPaddingRight) - drawables.mDrawableSizeRight;
                    i2 = (compoundPaddingBottom2 - drawables.mDrawableHeightRight) / 2;
                } else {
                    if (drawable == drawables.mShowing[1]) {
                        int compoundPaddingLeft = getCompoundPaddingLeft();
                        compoundPaddingRight += compoundPaddingLeft + (((((this.mRight - this.mLeft) - getCompoundPaddingRight()) - compoundPaddingLeft) - drawables.mDrawableWidthTop) / 2);
                        i = this.mPaddingTop;
                    } else if (drawable == drawables.mShowing[3]) {
                        int compoundPaddingLeft2 = getCompoundPaddingLeft();
                        compoundPaddingRight += compoundPaddingLeft2 + (((((this.mRight - this.mLeft) - getCompoundPaddingRight()) - compoundPaddingLeft2) - drawables.mDrawableWidthBottom) / 2);
                        i = ((this.mBottom - this.mTop) - this.mPaddingBottom) - drawables.mDrawableSizeBottom;
                    }
                    i3 += i;
                    z = true;
                }
                i = compoundPaddingTop + i2;
                i3 += i;
                z = true;
            }
            if (z) {
                invalidate(bounds.left + compoundPaddingRight, bounds.top + i3, bounds.right + compoundPaddingRight, bounds.bottom + i3);
            }
        }
        if (z) {
            return;
        }
        super.invalidateDrawable(drawable);
    }

    @Override // android.view.View
    public boolean hasOverlappingRendering() {
        return ((getBackground() == null || getBackground().getCurrent() == null) && this.mSpannable == null && !hasSelection() && !isHorizontalFadingEdgeEnabled() && this.mShadowColor == 0) ? false : true;
    }

    public boolean isTextSelectable() {
        Editor editor = this.mEditor;
        if (editor == null) {
            return false;
        }
        return editor.mTextIsSelectable;
    }

    public void setTextIsSelectable(boolean z) {
        if (z || this.mEditor != null) {
            createEditorIfNeeded();
            if (this.mEditor.mTextIsSelectable == z) {
                return;
            }
            if (ViewRune.WIDGET_PEN_SUPPORTED) {
                registerForStylusPenEvent();
            }
            this.mEditor.mTextIsSelectable = z;
            setFocusableInTouchMode(z);
            setFocusable(16);
            setClickable(z);
            setLongClickable(z);
            setMovementMethod(z ? ArrowKeyMovementMethod.getInstance() : null);
            setText(this.mText, z ? BufferType.SPANNABLE : BufferType.NORMAL);
            this.mEditor.prepareCursorControllers();
        }
    }

    @Override // android.view.View
    protected int[] onCreateDrawableState(int i) {
        int[] iArrOnCreateDrawableState;
        if (this.mSingleLine) {
            iArrOnCreateDrawableState = super.onCreateDrawableState(i);
        } else {
            iArrOnCreateDrawableState = super.onCreateDrawableState(i + 1);
            mergeDrawableStates(iArrOnCreateDrawableState, MULTILINE_STATE_SET);
        }
        if (isTextSelectable()) {
            int length = iArrOnCreateDrawableState.length;
            for (int i2 = 0; i2 < length; i2++) {
                if (iArrOnCreateDrawableState[i2] == 16842919) {
                    int[] iArr = new int[length - 1];
                    System.arraycopy(iArrOnCreateDrawableState, 0, iArr, 0, i2);
                    System.arraycopy(iArrOnCreateDrawableState, i2 + 1, iArr, i2, (length - i2) - 1);
                    return iArr;
                }
            }
        }
        return iArrOnCreateDrawableState;
    }

    private void maybeUpdateHighlightPaths() {
        Path path;
        final Path path2;
        if (this.mHighlightPathsBogus) {
            List<Path> list = this.mHighlightPaths;
            if (list != null) {
                this.mPathRecyclePool.addAll(list);
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
                        List<Path> list2 = this.mPathRecyclePool;
                        path2 = list2.get(list2.size() - 1);
                        List<Path> list3 = this.mPathRecyclePool;
                        list3.remove(list3.size() - 1);
                        path2.reset();
                    }
                    boolean z = false;
                    for (int i2 = 0; i2 < ranges.length / 2; i2++) {
                        int i3 = i2 * 2;
                        int i4 = ranges[i3];
                        int i5 = ranges[i3 + 1];
                        if (i4 < i5) {
                            this.mLayout.getSelection(i4, i5, new Layout.SelectionRectangleConsumer() { // from class: android.widget.TextView$$ExternalSyntheticLambda0
                                @Override // android.text.Layout.SelectionRectangleConsumer
                                public final void accept(float f, float f2, float f3, float f4, int i6) {
                                    path2.addRect(f, f2, f3, f4, Path.Direction.CW);
                                }
                            });
                            z = true;
                        }
                    }
                    if (z) {
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
                    List<Path> list4 = this.mPathRecyclePool;
                    path = list4.get(list4.size() - 1);
                    List<Path> list5 = this.mPathRecyclePool;
                    list5.remove(list5.size() - 1);
                    path.reset();
                }
                this.mLayout.getSelectionPath(this.mGesturePreviewHighlightStart, this.mGesturePreviewHighlightEnd, path);
                this.mHighlightPaths.add(path);
                this.mHighlightPaints.add(this.mGesturePreviewHighlightPaint);
            }
            this.mHighlightPathsBogus = false;
        }
    }

    private void addSearchHighlightPaths() {
        final Path path;
        final Path path2;
        if (this.mSearchResultHighlights != null) {
            if (this.mPathRecyclePool.isEmpty()) {
                path = new Path();
            } else {
                List<Path> list = this.mPathRecyclePool;
                path = list.get(list.size() - 1);
                List<Path> list2 = this.mPathRecyclePool;
                list2.remove(list2.size() - 1);
                path.reset();
            }
            if (this.mFocusedSearchResultIndex == -1) {
                path2 = null;
            } else if (this.mPathRecyclePool.isEmpty()) {
                path2 = new Path();
            } else {
                List<Path> list3 = this.mPathRecyclePool;
                path2 = list3.get(list3.size() - 1);
                List<Path> list4 = this.mPathRecyclePool;
                list4.remove(list4.size() - 1);
                path2.reset();
            }
            int i = 0;
            boolean z = false;
            while (true) {
                int[] iArr = this.mSearchResultHighlights;
                if (i >= iArr.length / 2) {
                    break;
                }
                int i2 = i * 2;
                int i3 = iArr[i2];
                int i4 = iArr[i2 + 1];
                if (i3 < i4) {
                    if (i == this.mFocusedSearchResultIndex) {
                        this.mLayout.getSelection(i3, i4, new Layout.SelectionRectangleConsumer() { // from class: android.widget.TextView$$ExternalSyntheticLambda6
                            @Override // android.text.Layout.SelectionRectangleConsumer
                            public final void accept(float f, float f2, float f3, float f4, int i5) {
                                path2.addRect(f, f2, f3, f4, Path.Direction.CW);
                            }
                        });
                    } else {
                        this.mLayout.getSelection(i3, i4, new Layout.SelectionRectangleConsumer() { // from class: android.widget.TextView$$ExternalSyntheticLambda7
                            @Override // android.text.Layout.SelectionRectangleConsumer
                            public final void accept(float f, float f2, float f3, float f4, int i5) {
                                path.addRect(f, f2, f3, f4, Path.Direction.CW);
                            }
                        });
                        z = true;
                    }
                }
                i++;
            }
            if (z) {
                if (this.mSearchResultHighlightPaint == null) {
                    this.mSearchResultHighlightPaint = new Paint();
                }
                this.mSearchResultHighlightPaint.setColor(this.mSearchResultHighlightColor);
                this.mSearchResultHighlightPaint.setStyle(Paint.Style.FILL);
                this.mHighlightPaths.add(path);
                this.mHighlightPaints.add(this.mSearchResultHighlightPaint);
            }
            if (path2 != null) {
                if (this.mFocusedSearchResultHighlightPaint == null) {
                    this.mFocusedSearchResultHighlightPaint = new Paint();
                }
                this.mFocusedSearchResultHighlightPaint.setColor(this.mFocusedSearchResultHighlightColor);
                this.mFocusedSearchResultHighlightPaint.setStyle(Paint.Style.FILL);
                this.mHighlightPaths.add(path2);
                this.mHighlightPaints.add(this.mFocusedSearchResultHighlightPaint);
            }
        }
    }

    private Path getUpdatedHighlightPath() {
        Paint paint = this.mHighlightPaint;
        int selectionStartTransformed = getSelectionStartTransformed();
        int selectionEndTransformed = getSelectionEndTransformed();
        if (ViewRune.WIDGET_PEN_SUPPORTED && this.mhasMultiSelection) {
            CharSequence textForMultiSelection = getTextForMultiSelection();
            if (textForMultiSelection == null) {
                this.mhasMultiSelection = false;
                return null;
            }
            if (this.mHighlightPathBogus) {
                if (this.mHighlightPath == null) {
                    this.mHighlightPath = new Path();
                }
                this.mHighlightPath.reset();
                Spannable spannable = (Spannable) textForMultiSelection;
                int[] multiSelectionStart = MultiSelection.getMultiSelectionStart(spannable);
                int[] multiSelectionEnd = MultiSelection.getMultiSelectionEnd(spannable);
                int multiSelectionCount = MultiSelection.getMultiSelectionCount(spannable);
                for (int i = 0; i < multiSelectionCount; i++) {
                    this.mLayout.addSelectionPath(multiSelectionStart[i], multiSelectionEnd[i], this.mHighlightPath);
                }
                this.mHighlightPathBogus = false;
            }
            this.mMultiHighlightPaint.setColor(this.mMultiHighlightColor);
            this.mMultiHighlightPaint.setStyle(Paint.Style.FILL);
            return this.mHighlightPath;
        }
        if (this.mMovement != null && ((isFocused() || isPressed()) && selectionStartTransformed >= 0)) {
            if (selectionStartTransformed == selectionEndTransformed) {
                Editor editor = this.mEditor;
                if (editor != null && editor.shouldRenderCursor()) {
                    if (this.mHighlightPathBogus) {
                        if (this.mHighlightPath == null) {
                            this.mHighlightPath = new Path();
                        }
                        this.mHighlightPath.reset();
                        this.mLayout.getCursorPath(selectionStartTransformed, this.mHighlightPath, this.mText);
                        this.mEditor.updateCursorPosition();
                        this.mHighlightPathBogus = false;
                    }
                    paint.setColor(this.mCurTextColor);
                    paint.setStyle(Paint.Style.STROKE);
                    return this.mHighlightPath;
                }
            } else {
                if (this.mHighlightPathBogus) {
                    if (this.mHighlightPath == null) {
                        this.mHighlightPath = new Path();
                    }
                    this.mHighlightPath.reset();
                    this.mLayout.getSelectionPath(selectionStartTransformed, selectionEndTransformed, this.mHighlightPath);
                    this.mHighlightPathBogus = false;
                }
                paint.setColor(this.mHighlightColor);
                paint.setStyle(Paint.Style.FILL);
                return this.mHighlightPath;
            }
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:129:0x037f  */
    /* JADX WARN: Removed duplicated region for block: B:145:0x03dc  */
    /* JADX WARN: Removed duplicated region for block: B:146:0x03de  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x03f4  */
    /* JADX WARN: Removed duplicated region for block: B:155:0x0402  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected void onDraw(Canvas canvas) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int verticalOffset;
        int i7;
        int iFloor;
        Paint paint;
        Editor editor;
        int i8;
        Canvas canvas2;
        Path path;
        Paint paint2;
        Layout layout;
        Marquee marquee;
        int i9;
        restartMarqueeIfNeeded();
        super.onDraw(canvas);
        SFText sFText = this.mTextEffect;
        if (sFText != null && sFText.hasEffect()) {
            int buttonShapeTextColor = this.mCurTextColor;
            if (this.mLayout == null) {
                assumeLayout();
            }
            if (this.mHint != null && this.mText.length() == 0 && this.mHintTextColor != null) {
                buttonShapeTextColor = this.mCurHintTextColor;
            }
            if (this.mButtonShapeSettingEnabled && this.mIsButtonShapeTarget && !TextUtils.isEmpty(this.mText)) {
                buttonShapeTextColor = getButtonShapeTextColor();
            }
            this.mTextPaint.setColor(buttonShapeTextColor);
            Log.d(LOG_TAG, "TextView : Paint's Alpha value = " + getPaint().getAlpha());
            this.mTextEffect.setOwnerView(this);
            this.mTextEffect.setLines(this.mTextEffectLines);
            this.mTextEffect.setFontFamily(this.mFontFamily);
            if (this.mTextEffect.render(canvas, getScrollX(), getScrollY())) {
                return;
            }
        }
        int compoundPaddingLeft = getCompoundPaddingLeft();
        int compoundPaddingTop = getCompoundPaddingTop();
        int compoundPaddingRight = getCompoundPaddingRight();
        int compoundPaddingBottom = getCompoundPaddingBottom();
        int i10 = this.mScrollX;
        int i11 = this.mScrollY;
        int i12 = this.mRight;
        int i13 = this.mLeft;
        int i14 = this.mBottom;
        int i15 = this.mTop;
        boolean zIsLayoutRtl = isLayoutRtl();
        int horizontalOffsetForDrawables = getHorizontalOffsetForDrawables();
        int i16 = zIsLayoutRtl ? 0 : horizontalOffsetForDrawables;
        if (!zIsLayoutRtl) {
            horizontalOffsetForDrawables = 0;
        }
        Drawables drawables = this.mDrawables;
        if (drawables != null) {
            int i17 = ((i14 - i15) - compoundPaddingBottom) - compoundPaddingTop;
            int i18 = ((i12 - i13) - compoundPaddingRight) - compoundPaddingLeft;
            if (drawables.mShowing[0] != null) {
                canvas.save();
                i = 3;
                canvas.translate(this.mPaddingLeft + i10 + i16, i11 + compoundPaddingTop + ((i17 - drawables.mDrawableHeightLeft) / 2));
                drawables.mShowing[0].draw(canvas);
                canvas.restore();
            } else {
                i = 3;
            }
            if (drawables.mShowing[2] != null) {
                canvas.save();
                canvas.translate(((((i10 + i12) - i13) - this.mPaddingRight) - drawables.mDrawableSizeRight) - horizontalOffsetForDrawables, i11 + compoundPaddingTop + ((i17 - drawables.mDrawableHeightRight) / 2));
                drawables.mShowing[2].draw(canvas);
                canvas.restore();
            }
            if (drawables.mShowing[1] != null) {
                canvas.save();
                canvas.translate(i10 + compoundPaddingLeft + ((i18 - drawables.mDrawableWidthTop) / 2), this.mPaddingTop + i11);
                drawables.mShowing[1].draw(canvas);
                canvas.restore();
            }
            if (drawables.mShowing[i] != null) {
                canvas.save();
                canvas.translate(i10 + compoundPaddingLeft + ((i18 - drawables.mDrawableWidthBottom) / 2), (((i11 + i14) - i15) - this.mPaddingBottom) - drawables.mDrawableSizeBottom);
                drawables.mShowing[i].draw(canvas);
                canvas.restore();
            }
        } else {
            i = 3;
        }
        int buttonShapeTextColor2 = this.mCurTextColor;
        if (this.mLayout == null) {
            assumeLayout();
        }
        Layout layout2 = this.mLayout;
        if (this.mHint != null && !this.mHideHint && this.mText.length() == 0) {
            if (this.mHintTextColor != null) {
                buttonShapeTextColor2 = this.mCurHintTextColor;
            }
            layout2 = this.mHintLayout;
        }
        if (this.mButtonShapeSettingEnabled && this.mIsButtonShapeTarget && !TextUtils.isEmpty(this.mText)) {
            buttonShapeTextColor2 = getButtonShapeTextColor();
        }
        this.mTextPaint.setColor(buttonShapeTextColor2);
        this.mTextPaint.drawableState = getDrawableState();
        canvas.save();
        if (!this.mButtonShapeSettingEnabled || !this.mIsButtonShapeTarget || this.mButtonShapePaint == null || this.mButtonShapeRect == null || TextUtils.isEmpty(this.mText)) {
            i2 = compoundPaddingLeft;
            i3 = compoundPaddingTop;
            i4 = compoundPaddingBottom;
            i5 = i10;
        } else {
            int compoundPaddingLeft2 = getCompoundPaddingLeft();
            int extendedPaddingTop = getExtendedPaddingTop();
            if ((this.mGravity & 112) != 48) {
                i9 = 0;
                extendedPaddingTop += getVerticalOffset(false);
            } else {
                i9 = 0;
            }
            int lineForOffset = this.mLayout.getLineForOffset(i9);
            i2 = compoundPaddingLeft;
            int lineForOffset2 = this.mLayout.getLineForOffset(this.mText.length());
            float lineLeft = this.mLayout.getLineLeft(lineForOffset);
            i3 = compoundPaddingTop;
            float lineRight = this.mLayout.getLineRight(lineForOffset);
            i4 = compoundPaddingBottom;
            int i19 = lineForOffset;
            while (i19 <= lineForOffset2) {
                int i20 = i10;
                if (lineLeft > this.mLayout.getLineLeft(i19)) {
                    lineLeft = this.mLayout.getLineLeft(i19);
                }
                if (lineRight < this.mLayout.getLineRight(i19)) {
                    lineRight = this.mLayout.getLineRight(i19);
                }
                i19++;
                i10 = i20;
            }
            i5 = i10;
            this.mButtonShapePaint.setAlpha(this.mButtonShapeAlpha);
            this.mButtonShapePaint.setColor(this.mButtonShapeColor);
            RectF rectF = this.mButtonShapeRect;
            float lineTop = this.mLayout.getLineTop(lineForOffset);
            float f = extendedPaddingTop;
            rectF.top = (lineTop + f) - this.mButtonShapeOutlineStrokeTop;
            this.mButtonShapeRect.bottom = this.mLayout.getLineBottom(lineForOffset2) + f + this.mButtonShapeOutlineStrokeBottom;
            float f2 = compoundPaddingLeft2;
            this.mButtonShapeRect.left = (((float) Math.floor(lineLeft)) + f2) - this.mButtonShapeOutlineStrokeHorizontal;
            this.mButtonShapeRect.right = ((float) Math.ceil(lineRight)) + f2 + this.mButtonShapeOutlineStrokeHorizontal;
            RectF rectF2 = this.mButtonShapeRect;
            int i21 = this.mButtonShapeOutlineRadius;
            canvas.drawRoundRect(rectF2, i21, i21, this.mButtonShapePaint);
        }
        int extendedPaddingTop2 = getExtendedPaddingTop();
        int extendedPaddingBottom = getExtendedPaddingBottom();
        int height = this.mLayout.getHeight() - (((this.mBottom - this.mTop) - i4) - i3);
        float fMin = i2 + i5;
        float fMin2 = i11 == 0 ? 0.0f : extendedPaddingTop2 + i11;
        float compoundPaddingRight2 = ((i12 - i13) - getCompoundPaddingRight()) + i5;
        int i22 = (i14 - i15) + i11;
        if (i11 == height) {
            extendedPaddingBottom = 0;
        }
        float fMax = i22 - extendedPaddingBottom;
        float f3 = this.mShadowRadius;
        if (f3 != 0.0f) {
            fMin += Math.min(0.0f, this.mShadowDx - f3);
            compoundPaddingRight2 += Math.max(0.0f, this.mShadowDx + this.mShadowRadius);
            fMin2 += Math.min(0.0f, this.mShadowDy - this.mShadowRadius);
            fMax += Math.max(0.0f, this.mShadowDy + this.mShadowRadius);
        }
        canvas.clipRect(fMin, fMin2, compoundPaddingRight2, fMax);
        if ((this.mGravity & 112) != 48) {
            int verticalOffset2 = getVerticalOffset(false);
            i6 = 1;
            verticalOffset = getVerticalOffset(true);
            i7 = verticalOffset2;
        } else {
            i6 = 1;
            verticalOffset = 0;
            i7 = 0;
        }
        if (CoreRune.GRAPHICS_RENDERER_HCF && isHighContrastTextEnabled()) {
            if ((this.mGravity & 7) == i6 || !isEditorNotFull()) {
                iFloor = 0;
            } else {
                iFloor = (int) Math.floor((this.mTextPaint.getHCTStrokeWidth() / 2.0f) * (isRightAligned() ? -1 : 1));
            }
            int iFloor2 = (this.mGravity & 112) != 16 ? (int) Math.floor(this.mTextPaint.getHCTStrokeWidth() / 2.0f) : 0;
            canvas.translate(i2 + iFloor, extendedPaddingTop2 + i7 + iFloor2);
            int absoluteGravity = Gravity.getAbsoluteGravity(this.mGravity, getLayoutDirection());
            if (isMarqueeFadeEnabled()) {
                if (!this.mSingleLine && getLineCount() == 1 && canMarquee() && (absoluteGravity & 7) != i) {
                    canvas.translate(layout2.getParagraphDirection(0) * (this.mLayout.getLineRight(0) - ((this.mRight - this.mLeft) - (getCompoundPaddingLeft() + getCompoundPaddingRight()))), 0.0f);
                }
                Marquee marquee2 = this.mMarquee;
                if (marquee2 != null && marquee2.isRunning()) {
                    canvas.translate(layout2.getParagraphDirection(0) * (-this.mMarquee.getScroll()), 0.0f);
                }
            }
            int i23 = verticalOffset - i7;
            maybeUpdateHighlightPaths();
            Path updatedHighlightPath = !hasGesturePreviewHighlight() ? null : getUpdatedHighlightPath();
            paint = this.mHighlightPaint;
            if (ViewRune.WIDGET_PEN_SUPPORTED && this.mhasMultiSelection) {
                paint = this.mMultiHighlightPaint;
            }
            Paint paint3 = paint;
            editor = this.mEditor;
            if (editor == null) {
                i8 = i23;
                path = updatedHighlightPath;
                layout = layout2;
                editor.onDraw(canvas, layout, this.mHighlightPaths, this.mHighlightPaints, path, paint3, i8);
                canvas2 = canvas;
                paint2 = paint3;
            } else {
                Layout layout3 = layout2;
                i8 = i23;
                canvas2 = canvas;
                layout3.draw(canvas2, this.mHighlightPaths, this.mHighlightPaints, updatedHighlightPath, paint3, i8);
                path = updatedHighlightPath;
                paint2 = paint3;
                layout = layout3;
            }
            marquee = this.mMarquee;
            if (marquee != null && marquee.shouldDrawGhost()) {
                canvas2.translate(layout.getParagraphDirection(0) * this.mMarquee.getGhostOffset(), 0.0f);
                layout.draw(canvas, this.mHighlightPaths, this.mHighlightPaints, path, paint2, i8);
            }
            canvas.restore();
        }
        iFloor = 0;
        canvas.translate(i2 + iFloor, extendedPaddingTop2 + i7 + iFloor2);
        int absoluteGravity2 = Gravity.getAbsoluteGravity(this.mGravity, getLayoutDirection());
        if (isMarqueeFadeEnabled()) {
        }
        int i232 = verticalOffset - i7;
        maybeUpdateHighlightPaths();
        Path updatedHighlightPath2 = !hasGesturePreviewHighlight() ? null : getUpdatedHighlightPath();
        paint = this.mHighlightPaint;
        if (ViewRune.WIDGET_PEN_SUPPORTED) {
            paint = this.mMultiHighlightPaint;
        }
        Paint paint32 = paint;
        editor = this.mEditor;
        if (editor == null) {
        }
        marquee = this.mMarquee;
        if (marquee != null) {
            canvas2.translate(layout.getParagraphDirection(0) * this.mMarquee.getGhostOffset(), 0.0f);
            layout.draw(canvas, this.mHighlightPaths, this.mHighlightPaints, path, paint2, i8);
        }
        canvas.restore();
    }

    @Override // android.view.View
    public void getFocusedRect(Rect rect) {
        if (this.mLayout == null) {
            super.getFocusedRect(rect);
            return;
        }
        int selectionEndTransformed = getSelectionEndTransformed();
        if (selectionEndTransformed < 0) {
            super.getFocusedRect(rect);
            return;
        }
        int selectionStartTransformed = getSelectionStartTransformed();
        if (selectionStartTransformed < 0 || selectionStartTransformed >= selectionEndTransformed) {
            int lineForOffset = this.mLayout.getLineForOffset(selectionEndTransformed);
            rect.top = this.mLayout.getLineTop(lineForOffset);
            rect.bottom = this.mLayout.getLineBottom(lineForOffset);
            rect.left = ((int) this.mLayout.getPrimaryHorizontal(selectionEndTransformed)) - 2;
            rect.right = rect.left + 4;
        } else {
            int lineForOffset2 = this.mLayout.getLineForOffset(selectionStartTransformed);
            int lineForOffset3 = this.mLayout.getLineForOffset(selectionEndTransformed);
            rect.top = this.mLayout.getLineTop(lineForOffset2);
            rect.bottom = this.mLayout.getLineBottom(lineForOffset3);
            if (lineForOffset2 == lineForOffset3) {
                rect.left = (int) this.mLayout.getPrimaryHorizontal(selectionStartTransformed);
                rect.right = (int) this.mLayout.getPrimaryHorizontal(selectionEndTransformed);
            } else {
                if (this.mHighlightPathBogus) {
                    if (this.mHighlightPath == null) {
                        this.mHighlightPath = new Path();
                    }
                    this.mHighlightPath.reset();
                    this.mLayout.getSelectionPath(selectionStartTransformed, selectionEndTransformed, this.mHighlightPath);
                    this.mHighlightPathBogus = false;
                }
                RectF rectF = TEMP_RECTF;
                synchronized (rectF) {
                    this.mHighlightPath.computeBounds(rectF, true);
                    rect.left = ((int) rectF.left) - 1;
                    rect.right = ((int) rectF.right) + 1;
                }
            }
        }
        int compoundPaddingLeft = getCompoundPaddingLeft();
        int extendedPaddingTop = getExtendedPaddingTop();
        if ((this.mGravity & 112) != 48) {
            extendedPaddingTop += getVerticalOffset(false);
        }
        rect.offset(compoundPaddingLeft, extendedPaddingTop);
        rect.bottom += getExtendedPaddingBottom();
    }

    public int getLineCount() {
        Layout layout = this.mLayout;
        if (layout != null) {
            return layout.getLineCount();
        }
        return 0;
    }

    public int getLineBounds(int i, Rect rect) {
        Layout layout = this.mLayout;
        if (layout == null) {
            if (rect != null) {
                rect.set(0, 0, 0, 0);
            }
            return 0;
        }
        int lineBounds = layout.getLineBounds(i, rect);
        int extendedPaddingTop = getExtendedPaddingTop();
        if ((this.mGravity & 112) != 48) {
            extendedPaddingTop += getVerticalOffset(true);
        }
        if (rect != null) {
            rect.offset(getCompoundPaddingLeft(), extendedPaddingTop);
        }
        return lineBounds + extendedPaddingTop;
    }

    @Override // android.view.View
    public int getBaseline() {
        if (this.mLayout == null) {
            return super.getBaseline();
        }
        return getBaselineOffset() + this.mLayout.getLineBaseline(0);
    }

    int getBaselineOffset() {
        int verticalOffset = (this.mGravity & 112) != 48 ? getVerticalOffset(true) : 0;
        if (isLayoutModeOptical(this.mParent)) {
            verticalOffset -= getOpticalInsets().top;
        }
        return getExtendedPaddingTop() + verticalOffset;
    }

    @Override // android.view.View
    protected int getFadeTop(boolean z) {
        if (this.mLayout == null) {
            return 0;
        }
        int verticalOffset = (this.mGravity & 112) != 48 ? getVerticalOffset(true) : 0;
        if (z) {
            verticalOffset += getTopPaddingOffset();
        }
        return getExtendedPaddingTop() + verticalOffset;
    }

    @Override // android.view.View
    protected int getFadeHeight(boolean z) {
        Layout layout = this.mLayout;
        if (layout != null) {
            return layout.getHeight();
        }
        return 0;
    }

    @Override // android.view.View
    public PointerIcon onResolvePointerIcon(MotionEvent motionEvent, int i) {
        if (motionEvent.isFromSource(8194)) {
            if (this.mSpannable != null && this.mLinksClickable) {
                int offsetForPosition = getOffsetForPosition(motionEvent.getX(i), motionEvent.getY(i));
                if (((ClickableSpan[]) this.mSpannable.getSpans(offsetForPosition, offsetForPosition, ClickableSpan.class)).length > 0) {
                    return PointerIcon.getSystemIcon(this.mContext, 1002);
                }
            }
            if (isTextSelectable() || isTextEditable()) {
                if (motionEvent.getToolType(i) == 2) {
                    return PointerIcon.getSystemIcon(this.mContext, 20002);
                }
                return PointerIcon.getSystemIcon(this.mContext, 1008);
            }
        }
        return super.onResolvePointerIcon(motionEvent, i);
    }

    @Override // android.view.View
    public boolean onKeyPreIme(int i, KeyEvent keyEvent) {
        if (i == 4 && handleBackInTextActionModeIfNeeded(keyEvent)) {
            return true;
        }
        if (i == 23 && keyEvent != null && keyEvent.getAction() == 1) {
            this.mKeycodeDpadCenterStatus = false;
        }
        return super.onKeyPreIme(i, keyEvent);
    }

    public boolean handleBackInTextActionModeIfNeeded(KeyEvent keyEvent) {
        Editor editor = this.mEditor;
        if (editor != null && editor.getTextActionMode() != null) {
            if (keyEvent.getAction() == 0 && keyEvent.getRepeatCount() == 0) {
                KeyEvent.DispatcherState keyDispatcherState = getKeyDispatcherState();
                if (keyDispatcherState != null) {
                    keyDispatcherState.startTracking(keyEvent, this);
                }
                return true;
            }
            if (keyEvent.getAction() == 1) {
                KeyEvent.DispatcherState keyDispatcherState2 = getKeyDispatcherState();
                if (keyDispatcherState2 != null) {
                    keyDispatcherState2.handleUpEvent(keyEvent);
                }
                if (keyEvent.isTracking() && !keyEvent.isCanceled()) {
                    stopTextActionMode();
                    return true;
                }
            }
        }
        return false;
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 19 || i == 20 || i == 21 || i == 22) {
            stopTextActionMode();
            if (keyEvent.isShiftPressed()) {
                this.mChangedSelectionBySIP = true;
            }
        }
        if (i == 1102) {
            Editor editor = this.mEditor;
            if (editor != null) {
                editor.stopTextActionModeFromIME();
            }
            return true;
        }
        if (doKeyDown(i, keyEvent, null) == 0) {
            return super.onKeyDown(i, keyEvent);
        }
        return true;
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyMultiple(int i, int i2, KeyEvent keyEvent) {
        KeyEvent keyEventChangeAction = KeyEvent.changeAction(keyEvent, 0);
        int iDoKeyDown = doKeyDown(i, keyEventChangeAction, keyEvent);
        if (iDoKeyDown == 0) {
            return super.onKeyMultiple(i, i2, keyEvent);
        }
        if (iDoKeyDown == -1) {
            return true;
        }
        int i3 = i2 - 1;
        KeyEvent keyEventChangeAction2 = KeyEvent.changeAction(keyEvent, 1);
        if (iDoKeyDown == 1) {
            this.mEditor.mKeyListener.onKeyUp(this, (Editable) this.mText, i, keyEventChangeAction2);
            while (true) {
                i3--;
                if (i3 <= 0) {
                    break;
                }
                this.mEditor.mKeyListener.onKeyDown(this, (Editable) this.mText, i, keyEventChangeAction);
                this.mEditor.mKeyListener.onKeyUp(this, (Editable) this.mText, i, keyEventChangeAction2);
            }
            hideErrorIfUnchanged();
        } else if (iDoKeyDown == 2) {
            this.mMovement.onKeyUp(this, this.mSpannable, i, keyEventChangeAction2);
            while (true) {
                i3--;
                if (i3 <= 0) {
                    break;
                }
                this.mMovement.onKeyDown(this, this.mSpannable, i, keyEventChangeAction);
                this.mMovement.onKeyUp(this, this.mSpannable, i, keyEventChangeAction2);
            }
        }
        return true;
    }

    private boolean shouldAdvanceFocusOnEnter() {
        int i;
        if (getKeyListener() == null) {
            return false;
        }
        if (this.mSingleLine) {
            return true;
        }
        Editor editor = this.mEditor;
        return editor != null && (editor.mInputType & 15) == 1 && ((i = this.mEditor.mInputType & InputType.TYPE_MASK_VARIATION) == 32 || i == 48);
    }

    /* JADX WARN: Removed duplicated region for block: B:86:0x00e7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int doKeyDown(int i, KeyEvent keyEvent, KeyEvent keyEvent2) {
        if (!isEnabled()) {
            return 0;
        }
        if (keyEvent.getRepeatCount() == 0 && !KeyEvent.isModifierKey(i)) {
            this.mPreventDefaultMovement = false;
        }
        if (i == 4) {
            Editor editor = this.mEditor;
            if (editor != null && editor.getTextActionMode() != null) {
                stopTextActionMode();
                return -1;
            }
        } else if (i == 23) {
            this.mKeycodeDpadCenterStatus = true;
            if (keyEvent.hasNoModifiers() && shouldAdvanceFocusOnEnter()) {
                return 0;
            }
        } else if (i != 61) {
            if (i == 66) {
                if (keyEvent.hasNoModifiers()) {
                    Editor editor2 = this.mEditor;
                    if (editor2 != null && editor2.mInputContentType != null && this.mEditor.mInputContentType.onEditorActionListener != null && this.mEditor.mInputContentType.onEditorActionListener.onEditorAction(this, getActionIdForEnterEvent(), keyEvent)) {
                        this.mEditor.mInputContentType.enterDown = true;
                        return -1;
                    }
                    if ((keyEvent.getFlags() & 16) != 0 || shouldAdvanceFocusOnEnter()) {
                        return hasOnClickListeners() ? 0 : -1;
                    }
                }
            } else if (i != 124) {
                if (i != 160) {
                    if (i != 111) {
                        if (i != 112) {
                            switch (i) {
                                case 277:
                                    if (keyEvent.hasNoModifiers() && canCut() && onTextContextMenuItem(16908320)) {
                                        return -1;
                                    }
                                    break;
                                case 278:
                                    if (keyEvent.hasNoModifiers() && canCopy() && onTextContextMenuItem(16908321)) {
                                        return -1;
                                    }
                                    break;
                                case 279:
                                    if (keyEvent.hasNoModifiers() && canPaste() && onTextContextMenuItem(16908322)) {
                                        return -1;
                                    }
                                    break;
                            }
                        } else if (keyEvent.hasModifiers(1) && canCut() && onTextContextMenuItem(16908320)) {
                            return -1;
                        }
                    } else if (Flags.escapeClearsFocus() && keyEvent.hasNoModifiers()) {
                        Editor editor3 = this.mEditor;
                        if (editor3 != null && editor3.getTextActionMode() != null) {
                            stopTextActionMode();
                            return -1;
                        }
                        if (hasFocus()) {
                            clearFocusInternal(null, true, false);
                            InputMethodManager inputMethodManager = getInputMethodManager();
                            if (inputMethodManager != null) {
                                inputMethodManager.hideSoftInputFromView(this, 0);
                            }
                            return -1;
                        }
                    }
                }
            } else if (keyEvent.hasModifiers(4096) && canCopy()) {
                if (onTextContextMenuItem(16908321)) {
                    return -1;
                }
            } else if (keyEvent.hasModifiers(1) && canPaste() && onTextContextMenuItem(16908322)) {
                return -1;
            }
        } else if (keyEvent.hasNoModifiers() || keyEvent.hasModifiers(1)) {
            return 0;
        }
        Editor editor4 = this.mEditor;
        if (editor4 != null && editor4.mKeyListener != null) {
            if (keyEvent2 != null) {
                try {
                    beginBatchEdit();
                    boolean zOnKeyOther = this.mEditor.mKeyListener.onKeyOther(this, (Editable) this.mText, keyEvent2);
                    hideErrorIfUnchanged();
                    if (zOnKeyOther) {
                        return -1;
                    }
                } catch (AbstractMethodError unused) {
                } finally {
                    endBatchEdit();
                }
            } else {
                beginBatchEdit();
                boolean zOnKeyDown = this.mEditor.mKeyListener.onKeyDown(this, (Editable) this.mText, i, keyEvent);
                endBatchEdit();
                hideErrorIfUnchanged();
                if (zOnKeyDown) {
                    return 1;
                }
            }
        }
        MovementMethod movementMethod = this.mMovement;
        if (movementMethod != null && this.mLayout != null) {
            if (keyEvent2 != null) {
                try {
                    if (movementMethod.onKeyOther(this, this.mSpannable, keyEvent2)) {
                        return -1;
                    }
                } catch (AbstractMethodError unused2) {
                }
                if (keyEvent.getSource() == 257 && isDirectionalNavigationKey(i)) {
                    return -1;
                }
            } else {
                if (this.mMovement.onKeyDown(this, this.mSpannable, i, keyEvent)) {
                    if (keyEvent.getRepeatCount() != 0 || KeyEvent.isModifierKey(i)) {
                        return 2;
                    }
                    this.mPreventDefaultMovement = true;
                    return 2;
                }
                if (keyEvent.getSource() == 257) {
                    return -1;
                }
            }
        }
        return (!this.mPreventDefaultMovement || KeyEvent.isModifierKey(i)) ? 0 : -1;
    }

    public void resetErrorChangedFlag() {
        Editor editor = this.mEditor;
        if (editor != null) {
            editor.mErrorWasChanged = false;
        }
    }

    public void hideErrorIfUnchanged() {
        Editor editor = this.mEditor;
        if (editor == null || editor.mError == null || this.mEditor.mErrorWasChanged) {
            return;
        }
        setError(null, null);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        InputMethodManager inputMethodManager;
        if (!isEnabled()) {
            return super.onKeyUp(i, keyEvent);
        }
        if (!KeyEvent.isModifierKey(i)) {
            this.mPreventDefaultMovement = false;
        }
        if (i == 23) {
            this.mKeycodeDpadCenterStatus = false;
            if (keyEvent.hasNoModifiers() && !hasOnClickListeners() && this.mMovement != null && (this.mText instanceof Editable) && this.mLayout != null && onCheckIsTextEditor()) {
                InputMethodManager inputMethodManager2 = getInputMethodManager();
                viewClicked(inputMethodManager2);
                if (inputMethodManager2 != null && getShowSoftInputOnFocus()) {
                    inputMethodManager2.showSoftInput(this, 0);
                }
            }
            return super.onKeyUp(i, keyEvent);
        }
        if ((i == 66 || i == 160) && keyEvent.hasNoModifiers()) {
            Editor editor = this.mEditor;
            if (editor != null && editor.mInputContentType != null && this.mEditor.mInputContentType.onEditorActionListener != null && this.mEditor.mInputContentType.enterDown) {
                this.mEditor.mInputContentType.enterDown = false;
                if (this.mEditor.mInputContentType.onEditorActionListener.onEditorAction(this, getActionIdForEnterEvent(), keyEvent)) {
                    return true;
                }
            }
            if (((keyEvent.getFlags() & 16) != 0 || shouldAdvanceFocusOnEnter()) && !hasOnClickListeners()) {
                View viewFocusSearch = focusSearch(130);
                if (viewFocusSearch != null) {
                    if (!viewFocusSearch.requestFocus(130)) {
                        throw new IllegalStateException("focus search returned a view that wasn't able to take focus!");
                    }
                    super.onKeyUp(i, keyEvent);
                    return true;
                }
                if ((keyEvent.getFlags() & 16) != 0 && (inputMethodManager = getInputMethodManager()) != null) {
                    inputMethodManager.hideSoftInputFromView(this, 0);
                }
            }
            return super.onKeyUp(i, keyEvent);
        }
        if ((i == 59 || i == 60) && this.mChangedSelectionBySIP) {
            InputMethodManager inputMethodManager3 = getInputMethodManager();
            if (this.mEditor != null && hasSelection() && isFocused() && isShown() && inputMethodManager3 != null && (inputMethodManager3.isAccessoryKeyboardState() & 7) == 0) {
                int selectionStart = getSelectionStart();
                int selectionEnd = getSelectionEnd();
                if (selectionStart > selectionEnd) {
                    Selection.setSelection((Spannable) this.mText, selectionEnd, selectionStart);
                }
                this.mEditor.startSelectionActionModeAsync(false);
            }
        }
        this.mChangedSelectionBySIP = false;
        Editor editor2 = this.mEditor;
        if (editor2 != null && editor2.mKeyListener != null && this.mEditor.mKeyListener.onKeyUp(this, (Editable) this.mText, i, keyEvent)) {
            return true;
        }
        MovementMethod movementMethod = this.mMovement;
        if (movementMethod == null || this.mLayout == null || !movementMethod.onKeyUp(this, this.mSpannable, i, keyEvent)) {
            return super.onKeyUp(i, keyEvent);
        }
        return true;
    }

    private int getActionIdForEnterEvent() {
        if (isSingleLine()) {
            return getImeOptions() & 255;
        }
        return 0;
    }

    @Override // android.view.View
    public boolean onCheckIsTextEditor() {
        Editor editor = this.mEditor;
        return (editor == null || editor.mInputType == 0) ? false : true;
    }

    private boolean hasEditorInFocusSearchDirection(int i) {
        View viewFocusSearch = focusSearch(i);
        return viewFocusSearch != null && viewFocusSearch.onCheckIsTextEditor();
    }

    @Override // android.view.View
    public InputConnection onCreateInputConnection(EditorInfo editorInfo) {
        if (onCheckIsTextEditor() && isEnabled()) {
            this.mEditor.createInputMethodStateIfNeeded();
            this.mEditor.mInputMethodState.mUpdateCursorAnchorInfoMode = 0;
            this.mEditor.mInputMethodState.mUpdateCursorAnchorInfoFilter = 0;
            editorInfo.inputType = getInputType();
            if (this.mEditor.mInputContentType != null) {
                editorInfo.imeOptions = this.mEditor.mInputContentType.imeOptions;
                editorInfo.privateImeOptions = this.mEditor.mInputContentType.privateImeOptions;
                editorInfo.actionLabel = this.mEditor.mInputContentType.imeActionLabel;
                editorInfo.actionId = this.mEditor.mInputContentType.imeActionId;
                editorInfo.extras = this.mEditor.mInputContentType.extras;
                editorInfo.hintLocales = this.mEditor.mInputContentType.imeHintLocales;
            } else {
                editorInfo.imeOptions = 0;
                editorInfo.hintLocales = null;
            }
            if (hasEditorInFocusSearchDirection(130)) {
                editorInfo.imeOptions |= 134217728;
            }
            if (hasEditorInFocusSearchDirection(33)) {
                editorInfo.imeOptions |= 67108864;
            }
            if ((editorInfo.imeOptions & 255) == 0) {
                if ((editorInfo.imeOptions & 134217728) != 0) {
                    editorInfo.imeOptions |= 5;
                } else {
                    editorInfo.imeOptions |= 6;
                }
                if (!shouldAdvanceFocusOnEnter()) {
                    editorInfo.imeOptions |= 1073741824;
                }
            }
            if (getResources().getConfiguration().orientation == 1) {
                editorInfo.internalImeOptions |= 1;
            }
            if (isMultilineInputType(editorInfo.inputType)) {
                editorInfo.imeOptions |= 1073741824;
            }
            editorInfo.hintText = this.mHint;
            editorInfo.targetInputMethodUser = this.mTextOperationUser;
            if (this.mText instanceof Editable) {
                EditableInputConnection editableInputConnection = new EditableInputConnection(this);
                editorInfo.initialSelStart = getSelectionStart();
                editorInfo.initialSelEnd = getSelectionEnd();
                editorInfo.initialCapsMode = editableInputConnection.getCursorCapsMode(getInputType());
                editorInfo.setInitialSurroundingText(this.mText);
                editorInfo.contentMimeTypes = getReceiveContentMimeTypes();
                if (android.view.inputmethod.Flags.editorinfoHandwritingEnabled()) {
                    boolean zIsAutoHandwritingEnabled = isAutoHandwritingEnabled();
                    editorInfo.setStylusHandwritingEnabled(zIsAutoHandwritingEnabled);
                    if (editorInfo.extras == null) {
                        editorInfo.extras = new Bundle();
                    }
                    editorInfo.extras.putBoolean(EditorInfo.STYLUS_HANDWRITING_ENABLED_ANDROIDX_EXTRAS_KEY, zIsAutoHandwritingEnabled);
                }
                if (android.view.inputmethod.Flags.writingTools()) {
                    editorInfo.setWritingToolsEnabled(isSuggestionsEnabled());
                }
                ArrayList arrayList = new ArrayList();
                arrayList.add(SelectGesture.class);
                arrayList.add(SelectRangeGesture.class);
                arrayList.add(DeleteGesture.class);
                arrayList.add(DeleteRangeGesture.class);
                arrayList.add(InsertGesture.class);
                arrayList.add(RemoveSpaceGesture.class);
                arrayList.add(JoinOrSplitGesture.class);
                arrayList.add(InsertModeGesture.class);
                editorInfo.setSupportedHandwritingGestures(arrayList);
                Set<Class<? extends PreviewableHandwritingGesture>> arraySet = new ArraySet<>();
                arraySet.add(SelectGesture.class);
                arraySet.add(SelectRangeGesture.class);
                arraySet.add(DeleteGesture.class);
                arraySet.add(DeleteRangeGesture.class);
                editorInfo.setSupportedHandwritingGesturePreviews(arraySet);
                return editableInputConnection;
            }
        }
        return null;
    }

    public void onRequestCursorUpdatesInternal(int i, int i2) {
        this.mEditor.mInputMethodState.mUpdateCursorAnchorInfoMode = i;
        this.mEditor.mInputMethodState.mUpdateCursorAnchorInfoFilter = i2;
        if ((i & 1) == 0 || isInLayout()) {
            return;
        }
        requestLayout();
    }

    public boolean extractText(ExtractedTextRequest extractedTextRequest, ExtractedText extractedText) {
        createEditorIfNeeded();
        return this.mEditor.extractText(extractedTextRequest, extractedText);
    }

    static void removeParcelableSpans(Spannable spannable, int i, int i2) {
        Object[] spans = spannable.getSpans(i, i2, ParcelableSpan.class);
        int length = spans.length;
        while (length > 0) {
            length--;
            spannable.removeSpan(spans[length]);
        }
    }

    public void setExtractedText(ExtractedText extractedText) {
        int i;
        Editable editableText = getEditableText();
        if (extractedText.text != null) {
            if (editableText == null) {
                setText(extractedText.text, BufferType.EDITABLE);
            } else {
                int length = editableText.length();
                if (extractedText.partialStartOffset >= 0) {
                    length = editableText.length();
                    int i2 = extractedText.partialStartOffset;
                    if (i2 > length) {
                        i2 = length;
                    }
                    int i3 = extractedText.partialEndOffset;
                    i = i2;
                    if (i3 <= length) {
                        length = i3;
                    }
                } else {
                    i = 0;
                }
                removeParcelableSpans(editableText, i, length);
                if (TextUtils.equals(editableText.subSequence(i, length), extractedText.text)) {
                    if (extractedText.text instanceof Spanned) {
                        TextUtils.copySpansFrom((Spanned) extractedText.text, 0, length - i, Object.class, editableText, i);
                    }
                } else {
                    editableText.replace(i, length, extractedText.text);
                }
            }
        }
        Spannable spannable = (Spannable) getText();
        int length2 = spannable.length();
        int i4 = extractedText.selectionStart;
        if (i4 < 0) {
            i4 = 0;
        } else if (i4 > length2) {
            i4 = length2;
        }
        int i5 = extractedText.selectionEnd;
        Selection.setSelection(spannable, i4, i5 >= 0 ? i5 > length2 ? length2 : i5 : 0);
        if ((extractedText.flags & 2) != 0) {
            MetaKeyKeyListener.startSelecting(this, spannable);
        } else {
            MetaKeyKeyListener.stopSelecting(this, spannable);
        }
        setHintInternal(extractedText.hint);
    }

    boolean isClipboardDisallowedByKnox() {
        return (getRestrictionPolicy() == null || getRestrictionPolicy().isClipboardAllowed(true)) ? false : true;
    }

    boolean isWritingToolkitDisallowedByKnox() {
        Bundle applicationRestrictions = getApplicationRestrictionsManager().getApplicationRestrictions("com.samsung.android.knox.galaxyai", UserHandle.myUserId());
        return applicationRestrictions != null && !applicationRestrictions.isEmpty() && applicationRestrictions.containsKey("key_writing_toolkit") && applicationRestrictions.getBundle("key_writing_toolkit").getBoolean("grayout");
    }

    public void setExtracting(ExtractedTextRequest extractedTextRequest) {
        if (this.mEditor.mInputMethodState != null) {
            this.mEditor.mInputMethodState.mExtractedTextRequest = extractedTextRequest;
        }
        this.mEditor.hideCursorAndSpanControllers();
        stopTextActionMode();
        if (this.mEditor.mSelectionModifierCursorController != null) {
            this.mEditor.mSelectionModifierCursorController.resetTouchOffsets();
        }
    }

    public void onCommitCorrection(CorrectionInfo correctionInfo) {
        Editor editor = this.mEditor;
        if (editor != null) {
            editor.onCommitCorrection(correctionInfo);
        }
    }

    public void beginBatchEdit() {
        Editor editor = this.mEditor;
        if (editor != null) {
            editor.beginBatchEdit();
        }
    }

    public void endBatchEdit() {
        Editor editor = this.mEditor;
        if (editor != null) {
            editor.endBatchEdit();
        }
    }

    public void onPerformSpellCheck() {
        Editor editor = this.mEditor;
        if (editor == null || editor.mSpellChecker == null) {
            return;
        }
        this.mEditor.mSpellChecker.onPerformSpellCheck();
    }

    public boolean isOffsetMappingAvailable() {
        return this.mTransformation != null && (this.mTransformed instanceof OffsetMapping);
    }

    public boolean previewHandwritingGesture(PreviewableHandwritingGesture previewableHandwritingGesture, CancellationSignal cancellationSignal) {
        if (previewableHandwritingGesture instanceof SelectGesture) {
            performHandwritingSelectGesture((SelectGesture) previewableHandwritingGesture, true);
        } else if (previewableHandwritingGesture instanceof SelectRangeGesture) {
            performHandwritingSelectRangeGesture((SelectRangeGesture) previewableHandwritingGesture, true);
        } else if (previewableHandwritingGesture instanceof DeleteGesture) {
            performHandwritingDeleteGesture((DeleteGesture) previewableHandwritingGesture, true);
        } else {
            if (!(previewableHandwritingGesture instanceof DeleteRangeGesture)) {
                return false;
            }
            performHandwritingDeleteRangeGesture((DeleteRangeGesture) previewableHandwritingGesture, true);
        }
        if (cancellationSignal != null) {
            cancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener() { // from class: android.widget.TextView$$ExternalSyntheticLambda4
                @Override // android.os.CancellationSignal.OnCancelListener
                public final void onCancel() {
                    this.f$0.clearGesturePreviewHighlight();
                }
            });
        }
        return true;
    }

    public int performHandwritingSelectGesture(SelectGesture selectGesture) {
        return performHandwritingSelectGesture(selectGesture, false);
    }

    private int performHandwritingSelectGesture(SelectGesture selectGesture, boolean z) {
        if (isOffsetMappingAvailable()) {
            return 3;
        }
        int[] rangeForRect = getRangeForRect(convertFromScreenToContentCoordinates(selectGesture.getSelectionArea()), selectGesture.getGranularity());
        if (rangeForRect == null) {
            return handleGestureFailure(selectGesture, z);
        }
        return performHandwritingSelectGesture(rangeForRect, z);
    }

    private int performHandwritingSelectGesture(int[] iArr, boolean z) {
        if (z) {
            setSelectGesturePreviewHighlight(iArr[0], iArr[1]);
        } else {
            Selection.setSelection(getEditableText(), iArr[0], iArr[1]);
            this.mEditor.startSelectionActionModeAsync(false);
        }
        return 1;
    }

    public int performHandwritingSelectRangeGesture(SelectRangeGesture selectRangeGesture) {
        return performHandwritingSelectRangeGesture(selectRangeGesture, false);
    }

    private int performHandwritingSelectRangeGesture(SelectRangeGesture selectRangeGesture, boolean z) {
        if (isOffsetMappingAvailable()) {
            return 3;
        }
        int[] rangeForRect = getRangeForRect(convertFromScreenToContentCoordinates(selectRangeGesture.getSelectionStartArea()), selectRangeGesture.getGranularity());
        if (rangeForRect == null) {
            return handleGestureFailure(selectRangeGesture, z);
        }
        int[] rangeForRect2 = getRangeForRect(convertFromScreenToContentCoordinates(selectRangeGesture.getSelectionEndArea()), selectRangeGesture.getGranularity());
        if (rangeForRect2 == null) {
            return handleGestureFailure(selectRangeGesture, z);
        }
        return performHandwritingSelectGesture(new int[]{Math.min(rangeForRect[0], rangeForRect2[0]), Math.max(rangeForRect[1], rangeForRect2[1])}, z);
    }

    public int performHandwritingDeleteGesture(DeleteGesture deleteGesture) {
        return performHandwritingDeleteGesture(deleteGesture, false);
    }

    private int performHandwritingDeleteGesture(DeleteGesture deleteGesture, boolean z) {
        if (isOffsetMappingAvailable()) {
            return 3;
        }
        int[] rangeForRect = getRangeForRect(convertFromScreenToContentCoordinates(deleteGesture.getDeletionArea()), deleteGesture.getGranularity());
        if (rangeForRect == null) {
            return handleGestureFailure(deleteGesture, z);
        }
        return performHandwritingDeleteGesture(rangeForRect, deleteGesture.getGranularity(), z);
    }

    private int performHandwritingDeleteGesture(int[] iArr, int i, boolean z) {
        if (z) {
            setDeleteGesturePreviewHighlight(iArr[0], iArr[1]);
        } else {
            if (i == 1) {
                iArr = adjustHandwritingDeleteGestureRange(iArr);
            }
            Selection.setSelection(getEditableText(), iArr[0]);
            getEditableText().delete(iArr[0], iArr[1]);
        }
        return 1;
    }

    public int performHandwritingDeleteRangeGesture(DeleteRangeGesture deleteRangeGesture) {
        return performHandwritingDeleteRangeGesture(deleteRangeGesture, false);
    }

    private int performHandwritingDeleteRangeGesture(DeleteRangeGesture deleteRangeGesture, boolean z) {
        if (isOffsetMappingAvailable()) {
            return 3;
        }
        int[] rangeForRect = getRangeForRect(convertFromScreenToContentCoordinates(deleteRangeGesture.getDeletionStartArea()), deleteRangeGesture.getGranularity());
        if (rangeForRect == null) {
            return handleGestureFailure(deleteRangeGesture, z);
        }
        int[] rangeForRect2 = getRangeForRect(convertFromScreenToContentCoordinates(deleteRangeGesture.getDeletionEndArea()), deleteRangeGesture.getGranularity());
        if (rangeForRect2 == null) {
            return handleGestureFailure(deleteRangeGesture, z);
        }
        return performHandwritingDeleteGesture(new int[]{Math.min(rangeForRect[0], rangeForRect2[0]), Math.max(rangeForRect[1], rangeForRect2[1])}, deleteRangeGesture.getGranularity(), z);
    }

    private int[] adjustHandwritingDeleteGestureRange(int[] iArr) {
        int iCharCount = iArr[0];
        int iCharCount2 = iArr[1];
        int iCodePointBefore = iCharCount > 0 ? Character.codePointBefore(this.mText, iCharCount) : 10;
        int iCodePointAt = iCharCount2 < this.mText.length() ? Character.codePointAt(this.mText, iCharCount2) : 10;
        if (TextUtils.isWhitespaceExceptNewline(iCodePointBefore) && (TextUtils.isWhitespace(iCodePointAt) || TextUtils.isPunctuation(iCodePointAt))) {
            do {
                iCharCount -= Character.charCount(iCodePointBefore);
                if (iCharCount == 0) {
                    break;
                }
                iCodePointBefore = Character.codePointBefore(this.mText, iCharCount);
            } while (TextUtils.isWhitespaceExceptNewline(iCodePointBefore));
            return new int[]{iCharCount, iCharCount2};
        }
        if (!TextUtils.isWhitespaceExceptNewline(iCodePointAt) || (!TextUtils.isWhitespace(iCodePointBefore) && !TextUtils.isPunctuation(iCodePointBefore))) {
            return iArr;
        }
        do {
            iCharCount2 += Character.charCount(iCodePointAt);
            if (iCharCount2 == this.mText.length()) {
                break;
            }
            iCodePointAt = Character.codePointAt(this.mText, iCharCount2);
        } while (TextUtils.isWhitespaceExceptNewline(iCodePointAt));
        return new int[]{iCharCount, iCharCount2};
    }

    public int performHandwritingInsertGesture(InsertGesture insertGesture) {
        if (isOffsetMappingAvailable()) {
            return 3;
        }
        PointF pointFConvertFromScreenToContentCoordinates = convertFromScreenToContentCoordinates(insertGesture.getInsertionPoint());
        int lineForHandwritingGesture = getLineForHandwritingGesture(pointFConvertFromScreenToContentCoordinates);
        if (lineForHandwritingGesture == -1) {
            return handleGestureFailure(insertGesture);
        }
        return tryInsertTextForHandwritingGesture(this.mLayout.getOffsetForHorizontal(lineForHandwritingGesture, pointFConvertFromScreenToContentCoordinates.x), insertGesture.getTextToInsert(), insertGesture);
    }

    public int performHandwritingRemoveSpaceGesture(RemoveSpaceGesture removeSpaceGesture) {
        if (isOffsetMappingAvailable()) {
            return 3;
        }
        PointF pointFConvertFromScreenToContentCoordinates = convertFromScreenToContentCoordinates(removeSpaceGesture.getStartPoint());
        PointF pointFConvertFromScreenToContentCoordinates2 = convertFromScreenToContentCoordinates(removeSpaceGesture.getEndPoint());
        int lineForHandwritingGesture = getLineForHandwritingGesture(pointFConvertFromScreenToContentCoordinates);
        int lineForHandwritingGesture2 = getLineForHandwritingGesture(pointFConvertFromScreenToContentCoordinates2);
        if (lineForHandwritingGesture != -1) {
            if (lineForHandwritingGesture2 != -1) {
                lineForHandwritingGesture = Math.min(lineForHandwritingGesture, lineForHandwritingGesture2);
            }
            lineForHandwritingGesture2 = lineForHandwritingGesture;
        } else if (lineForHandwritingGesture2 == -1) {
            return handleGestureFailure(removeSpaceGesture);
        }
        float lineTop = (this.mLayout.getLineTop(lineForHandwritingGesture2) + this.mLayout.getLineBottom(lineForHandwritingGesture2, false)) / 2.0f;
        int[] rangeForRect = this.mLayout.getRangeForRect(new RectF(Math.min(pointFConvertFromScreenToContentCoordinates.x, pointFConvertFromScreenToContentCoordinates2.x), lineTop + 0.1f, Math.max(pointFConvertFromScreenToContentCoordinates.x, pointFConvertFromScreenToContentCoordinates2.x), lineTop - 0.1f), new GraphemeClusterSegmentFinder(this.mText, this.mTextPaint), Layout.INCLUSION_STRATEGY_ANY_OVERLAP);
        if (rangeForRect == null) {
            return handleGestureFailure(removeSpaceGesture);
        }
        int i = rangeForRect[0];
        int iEnd = rangeForRect[1];
        Pattern whitespacePattern = getWhitespacePattern();
        Matcher matcher = whitespacePattern.matcher(this.mText.subSequence(i, iEnd));
        int iStart = -1;
        while (matcher.find()) {
            iStart = matcher.start() + i;
            getEditableText().delete(iStart, i + matcher.end());
            iEnd -= matcher.end() - matcher.start();
            if (iStart == iEnd) {
                break;
            }
            matcher = whitespacePattern.matcher(this.mText.subSequence(iStart, iEnd));
            i = iStart;
        }
        if (iStart == -1) {
            return handleGestureFailure(removeSpaceGesture);
        }
        Selection.setSelection(getEditableText(), iStart);
        return 1;
    }

    public int performHandwritingJoinOrSplitGesture(JoinOrSplitGesture joinOrSplitGesture) {
        if (isOffsetMappingAvailable()) {
            return 3;
        }
        PointF pointFConvertFromScreenToContentCoordinates = convertFromScreenToContentCoordinates(joinOrSplitGesture.getJoinOrSplitPoint());
        int lineForHandwritingGesture = getLineForHandwritingGesture(pointFConvertFromScreenToContentCoordinates);
        if (lineForHandwritingGesture == -1) {
            return handleGestureFailure(joinOrSplitGesture);
        }
        int offsetForHorizontal = this.mLayout.getOffsetForHorizontal(lineForHandwritingGesture, pointFConvertFromScreenToContentCoordinates.x);
        if (this.mLayout.isLevelBoundary(offsetForHorizontal)) {
            return handleGestureFailure(joinOrSplitGesture);
        }
        int iCharCount = offsetForHorizontal;
        while (iCharCount > 0) {
            int iCodePointBefore = Character.codePointBefore(this.mText, iCharCount);
            if (!TextUtils.isWhitespace(iCodePointBefore)) {
                break;
            }
            iCharCount -= Character.charCount(iCodePointBefore);
        }
        while (offsetForHorizontal < this.mText.length()) {
            int iCodePointAt = Character.codePointAt(this.mText, offsetForHorizontal);
            if (!TextUtils.isWhitespace(iCodePointAt)) {
                break;
            }
            offsetForHorizontal += Character.charCount(iCodePointAt);
        }
        if (iCharCount < offsetForHorizontal) {
            Selection.setSelection(getEditableText(), iCharCount);
            getEditableText().delete(iCharCount, offsetForHorizontal);
            return 1;
        }
        return tryInsertTextForHandwritingGesture(iCharCount, " ", joinOrSplitGesture);
    }

    public int performHandwritingInsertModeGesture(InsertModeGesture insertModeGesture) {
        PointF pointFConvertFromScreenToContentCoordinates = convertFromScreenToContentCoordinates(insertModeGesture.getInsertionPoint());
        int lineForHandwritingGesture = getLineForHandwritingGesture(pointFConvertFromScreenToContentCoordinates);
        CancellationSignal cancellationSignal = insertModeGesture.getCancellationSignal();
        if (lineForHandwritingGesture == -1 || cancellationSignal == null) {
            return handleGestureFailure(insertModeGesture);
        }
        if (!this.mEditor.enterInsertMode(this.mLayout.getOffsetForHorizontal(lineForHandwritingGesture, pointFConvertFromScreenToContentCoordinates.x))) {
            return 3;
        }
        cancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener() { // from class: android.widget.TextView$$ExternalSyntheticLambda5
            @Override // android.os.CancellationSignal.OnCancelListener
            public final void onCancel() {
                this.f$0.lambda$performHandwritingInsertModeGesture$4();
            }
        });
        return 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$performHandwritingInsertModeGesture$4() {
        this.mEditor.exitInsertMode();
    }

    private int handleGestureFailure(HandwritingGesture handwritingGesture) {
        return handleGestureFailure(handwritingGesture, false);
    }

    private int handleGestureFailure(HandwritingGesture handwritingGesture, boolean z) {
        clearGesturePreviewHighlight();
        if (z || TextUtils.isEmpty(handwritingGesture.getFallbackText())) {
            return 3;
        }
        getEditableText().replace(getSelectionStart(), getSelectionEnd(), handwritingGesture.getFallbackText());
        return 5;
    }

    private int getLineForHandwritingGesture(PointF pointF) {
        int lineForVertical = this.mLayout.getLineForVertical((int) pointF.y);
        int scaledHandwritingGestureLineMargin = ViewConfiguration.get(this.mContext).getScaledHandwritingGestureLineMargin();
        if (lineForVertical >= this.mLayout.getLineCount() - 1 || pointF.y <= this.mLayout.getLineBottom(lineForVertical) - scaledHandwritingGestureLineMargin || pointF.y <= (this.mLayout.getLineBottom(lineForVertical, false) + this.mLayout.getLineBottom(lineForVertical)) / 2.0f) {
            if (pointF.y >= this.mLayout.getLineTop(lineForVertical) - scaledHandwritingGestureLineMargin && pointF.y <= this.mLayout.getLineBottom(lineForVertical, false) + scaledHandwritingGestureLineMargin) {
            }
            return -1;
        }
        lineForVertical++;
        if (pointF.x < (-scaledHandwritingGestureLineMargin) || pointF.x > this.mLayout.getWidth() + scaledHandwritingGestureLineMargin) {
            return -1;
        }
        return lineForVertical;
    }

    private int[] getRangeForRect(RectF rectF, int i) {
        SegmentFinder graphemeClusterSegmentFinder;
        if (i == 1) {
            WordIterator wordIterator = getWordIterator();
            CharSequence charSequence = this.mText;
            wordIterator.setCharSequence(charSequence, 0, charSequence.length());
            graphemeClusterSegmentFinder = new WordSegmentFinder(this.mText, wordIterator);
        } else {
            graphemeClusterSegmentFinder = new GraphemeClusterSegmentFinder(this.mText, this.mTextPaint);
        }
        return this.mLayout.getRangeForRect(rectF, graphemeClusterSegmentFinder, Layout.INCLUSION_STRATEGY_CONTAINS_CENTER);
    }

    private int tryInsertTextForHandwritingGesture(int i, String str, HandwritingGesture handwritingGesture) {
        Editable editableText = getEditableText();
        if (this.mTempCursor == null) {
            this.mTempCursor = new NoCopySpan.Concrete();
        }
        editableText.setSpan(this.mTempCursor, i, i, 34);
        editableText.insert(i, str);
        int spanStart = editableText.getSpanStart(this.mTempCursor);
        editableText.removeSpan(this.mTempCursor);
        if (spanStart == i) {
            return handleGestureFailure(handwritingGesture);
        }
        Selection.setSelection(editableText, spanStart);
        return 1;
    }

    private Pattern getWhitespacePattern() {
        if (this.mWhitespacePattern == null) {
            this.mWhitespacePattern = Pattern.compile("\\s+");
        }
        return this.mWhitespacePattern;
    }

    public void nullLayouts() {
        Layout layout = this.mLayout;
        if ((layout instanceof BoringLayout) && this.mSavedLayout == null) {
            this.mSavedLayout = (BoringLayout) layout;
        }
        Layout layout2 = this.mHintLayout;
        if ((layout2 instanceof BoringLayout) && this.mSavedHintLayout == null) {
            this.mSavedHintLayout = (BoringLayout) layout2;
        }
        this.mHintLayout = null;
        this.mLayout = null;
        this.mSavedMarqueeModeLayout = null;
        this.mHintBoring = null;
        this.mBoring = null;
        Editor editor = this.mEditor;
        if (editor != null) {
            editor.prepareCursorControllers();
        }
    }

    private void assumeLayout() {
        int compoundPaddingLeft = ((this.mRight - this.mLeft) - getCompoundPaddingLeft()) - getCompoundPaddingRight();
        if (compoundPaddingLeft < 1) {
            compoundPaddingLeft = 0;
        }
        int i = compoundPaddingLeft;
        int i2 = this.mHorizontallyScrolling ? 1048576 : i;
        BoringLayout.Metrics metrics = UNKNOWN_BORING;
        makeNewLayout(i2, i, metrics, metrics, i, false);
    }

    private Layout.Alignment getLayoutAlignment() {
        switch (getTextAlignment()) {
            case 1:
                int i = this.mGravity & Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK;
                if (i == 1) {
                    return Layout.Alignment.ALIGN_CENTER;
                }
                if (i == 3) {
                    return Layout.Alignment.ALIGN_LEFT;
                }
                if (i == 5) {
                    return Layout.Alignment.ALIGN_RIGHT;
                }
                if (i == 8388611) {
                    return Layout.Alignment.ALIGN_NORMAL;
                }
                if (i == 8388613) {
                    return Layout.Alignment.ALIGN_OPPOSITE;
                }
                return Layout.Alignment.ALIGN_NORMAL;
            case 2:
                return Layout.Alignment.ALIGN_NORMAL;
            case 3:
                return Layout.Alignment.ALIGN_OPPOSITE;
            case 4:
                return Layout.Alignment.ALIGN_CENTER;
            case 5:
                return getLayoutDirection() == 1 ? Layout.Alignment.ALIGN_RIGHT : Layout.Alignment.ALIGN_LEFT;
            case 6:
                return getLayoutDirection() == 1 ? Layout.Alignment.ALIGN_LEFT : Layout.Alignment.ALIGN_RIGHT;
            default:
                return Layout.Alignment.ALIGN_NORMAL;
        }
    }

    private Paint.FontMetrics getResolvedMinimumFontMetrics() {
        Paint.FontMetrics fontMetrics = this.mMinimumFontMetrics;
        if (fontMetrics != null) {
            return fontMetrics;
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

    public void makeNewLayout(int i, int i2, BoringLayout.Metrics metrics, BoringLayout.Metrics metrics2, int i3, boolean z) {
        int i4;
        TextView textView;
        int i5;
        TextDirectionHeuristic textDirectionHeuristic;
        BoringLayout.Metrics metrics3;
        TextDirectionHeuristic textDirectionHeuristic2;
        int i6;
        stopMarquee();
        this.mOldMaximum = this.mMaximum;
        this.mOldMaxMode = this.mMaxMode;
        this.mHighlightPathBogus = true;
        this.mHighlightPathsBogus = true;
        int i7 = i < 0 ? 0 : i;
        int i8 = i2 < 0 ? 0 : i2;
        Layout.Alignment layoutAlignment = getLayoutAlignment();
        boolean z2 = this.mSingleLine && this.mLayout != null && (layoutAlignment == Layout.Alignment.ALIGN_NORMAL || layoutAlignment == Layout.Alignment.ALIGN_OPPOSITE);
        int paragraphDirection = z2 ? this.mLayout.getParagraphDirection(0) : 0;
        boolean z3 = this.mEllipsize != null && getKeyListener() == null;
        boolean z4 = this.mEllipsize == TextUtils.TruncateAt.MARQUEE && this.mMarqueeFadeMode != 0;
        TextUtils.TruncateAt truncateAt = this.mEllipsize;
        if (truncateAt == TextUtils.TruncateAt.MARQUEE && this.mMarqueeFadeMode == 1) {
            truncateAt = TextUtils.TruncateAt.END_SMALL;
        }
        TextUtils.TruncateAt truncateAt2 = truncateAt;
        if (this.mTextDir == null) {
            this.mTextDir = getTextDirectionHeuristic();
        }
        TextDirectionHeuristic textDirectionHeuristic3 = getTextDirectionHeuristic(true);
        this.mLayout = makeSingleLayout(i7, metrics, i3, layoutAlignment, z3, truncateAt2, truncateAt2 == this.mEllipsize);
        if (z4) {
            i4 = i3;
            this.mSavedMarqueeModeLayout = makeSingleLayout(i7, metrics, i4, layoutAlignment, z3, truncateAt2 == TextUtils.TruncateAt.MARQUEE ? TextUtils.TruncateAt.END : TextUtils.TruncateAt.MARQUEE, truncateAt2 != this.mEllipsize);
        } else {
            i4 = i3;
        }
        boolean z5 = this.mEllipsize != null;
        this.mHintLayout = null;
        CharSequence charSequence = this.mHint;
        if (charSequence != null) {
            int i9 = z5 ? i7 : i8;
            if (metrics2 == UNKNOWN_BORING) {
                BoringLayout.Metrics metricsIsBoring = BoringLayout.isBoring(charSequence, this.mTextPaint, textDirectionHeuristic3, isFallbackLineSpacingForBoringLayout(), getResolvedMinimumFontMetrics(), this.mHintBoring);
                textDirectionHeuristic = textDirectionHeuristic3;
                if (metricsIsBoring != null) {
                    this.mHintBoring = metricsIsBoring;
                }
                metrics3 = metricsIsBoring;
            } else {
                textDirectionHeuristic = textDirectionHeuristic3;
                metrics3 = metrics2;
            }
            if (metrics3 != null) {
                if (metrics3.width <= i9 && (!z5 || metrics3.width <= i4)) {
                    BoringLayout boringLayout = this.mSavedHintLayout;
                    if (boringLayout != null) {
                        int i10 = i9;
                        i6 = i10;
                        this.mHintLayout = boringLayout.replaceOrMake(this.mHint, this.mTextPaint, i10, layoutAlignment, this.mSpacingMult, this.mSpacingAdd, metrics3, this.mIncludePad);
                    } else {
                        i6 = i9;
                        this.mHintLayout = BoringLayout.make(this.mHint, this.mTextPaint, i6, layoutAlignment, this.mSpacingMult, this.mSpacingAdd, metrics3, this.mIncludePad);
                    }
                    this.mSavedHintLayout = (BoringLayout) this.mHintLayout;
                } else {
                    i6 = i9;
                    if (z5 && metrics3.width <= i6) {
                        BoringLayout boringLayout2 = this.mSavedHintLayout;
                        if (boringLayout2 != null) {
                            i5 = paragraphDirection;
                            textView = this;
                            textDirectionHeuristic2 = textDirectionHeuristic;
                            textView.mHintLayout = boringLayout2.replaceOrMake(this.mHint, this.mTextPaint, i6, layoutAlignment, this.mSpacingMult, this.mSpacingAdd, metrics3, this.mIncludePad, this.mEllipsize, i4);
                            i4 = i3;
                            i9 = i6;
                        } else {
                            textView = this;
                            i5 = paragraphDirection;
                            textDirectionHeuristic2 = textDirectionHeuristic;
                            i9 = i6;
                            BoringLayout boringLayoutMake = BoringLayout.make(textView.mHint, textView.mTextPaint, i9, layoutAlignment, textView.mSpacingMult, textView.mSpacingAdd, metrics3, textView.mIncludePad, textView.mEllipsize, i3);
                            layoutAlignment = layoutAlignment;
                            i4 = i3;
                            textView.mHintLayout = boringLayoutMake;
                        }
                    }
                }
                textView = this;
                i5 = paragraphDirection;
                i9 = i6;
                textDirectionHeuristic2 = textDirectionHeuristic;
            } else {
                textView = this;
                textDirectionHeuristic2 = textDirectionHeuristic;
                i5 = paragraphDirection;
            }
            if (textView.mHintLayout == null) {
                CharSequence charSequence2 = textView.mHint;
                StaticLayout.Builder minimumFontMetrics = StaticLayout.Builder.obtain(charSequence2, 0, charSequence2.length(), textView.mTextPaint, i9).setAlignment(layoutAlignment).setTextDirection(textDirectionHeuristic2).setLineSpacing(textView.mSpacingAdd, textView.mSpacingMult).setIncludePad(textView.mIncludePad).setUseLineSpacingFromFallbacks(textView.isFallbackLineSpacingForStaticLayout()).setBreakStrategy(textView.mBreakStrategy).setHyphenationFrequency(textView.mHyphenationFrequency).setJustificationMode(textView.mJustificationMode).setMaxLines(textView.mMaxMode == 1 ? textView.mMaximum : Integer.MAX_VALUE).setLineBreakConfig(LineBreakConfig.getLineBreakConfig(textView.mLineBreakStyle, textView.mLineBreakWordStyle)).setUseBoundsForWidth(textView.mUseBoundsForWidth).setMinimumFontMetrics(textView.getResolvedMinimumFontMetrics());
                if (z5) {
                    minimumFontMetrics.setEllipsize(textView.mEllipsize).setEllipsizedWidth(i4);
                }
                textView.mHintLayout = minimumFontMetrics.build();
            }
        } else {
            textView = this;
            i5 = paragraphDirection;
        }
        if (z || (z2 && i5 != textView.mLayout.getParagraphDirection(0))) {
            textView.registerForPreDraw();
        }
        if (textView.mEllipsize == TextUtils.TruncateAt.MARQUEE && !textView.compressText(i4)) {
            int i11 = textView.mLayoutParams.height;
            if (i11 != -2 && i11 != -1) {
                textView.startMarquee();
            } else {
                textView.mRestartMarquee = true;
            }
        }
        Editor editor = textView.mEditor;
        if (editor != null) {
            editor.prepareCursorControllers();
        }
    }

    public boolean useDynamicLayout() {
        if (isTextSelectable()) {
            return true;
        }
        return this.mSpannable != null && this.mPrecomputed == null;
    }

    /* JADX WARN: Removed duplicated region for block: B:46:0x015d  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x01d1 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected Layout makeSingleLayout(int i, BoringLayout.Metrics metrics, int i2, Layout.Alignment alignment, boolean z, TextUtils.TruncateAt truncateAt, boolean z2) {
        BoringLayout.Metrics metrics2;
        int i3;
        TextUtils.TruncateAt truncateAt2;
        BoringLayout boringLayout;
        BoringLayout boringLayout2;
        BoringLayout boringLayout3;
        int i4 = i;
        Layout.Alignment alignment2 = alignment;
        CharSequence charSequence = this.mTransformed;
        if (ViewRune.WIDGET_PEN_SUPPORTED && this.mUseDisplayText) {
            charSequence = this.mDisplayText;
        }
        CharSequence charSequence2 = charSequence;
        Layout boringLayout4 = null;
        boringLayout4 = null;
        boringLayout4 = null;
        if (useDynamicLayout()) {
            boringLayout4 = DynamicLayout.Builder.obtain(this.mText, this.mTextPaint, i4).setDisplayText(charSequence2).setAlignment(alignment2).setTextDirection(this.mTextDir).setLineSpacing(this.mSpacingAdd, this.mSpacingMult).setIncludePad(this.mIncludePad).setUseLineSpacingFromFallbacks(isFallbackLineSpacingForStaticLayout()).setBreakStrategy(this.mBreakStrategy).setHyphenationFrequency(this.mHyphenationFrequency).setJustificationMode(this.mJustificationMode).setLineBreakConfig(LineBreakConfig.getLineBreakConfig(this.mLineBreakStyle, this.mLineBreakWordStyle)).setUseBoundsForWidth(this.mUseBoundsForWidth).setEllipsize(getKeyListener() == null ? truncateAt : null).setEllipsizedWidth(i2).setMinimumFontMetrics(getResolvedMinimumFontMetrics()).build();
        } else {
            if (metrics == UNKNOWN_BORING) {
                BoringLayout.Metrics metricsIsBoring = BoringLayout.isBoring(charSequence2, this.mTextPaint, this.mTextDir, isFallbackLineSpacingForBoringLayout(), getResolvedMinimumFontMetrics(), this.mBoring);
                if (metricsIsBoring != null) {
                    this.mBoring = metricsIsBoring;
                }
                metrics2 = metricsIsBoring;
            } else {
                metrics2 = metrics;
            }
            if (metrics2 != null) {
                if (metrics2.width > i4 || (truncateAt != null && metrics2.width > i2)) {
                    if (z && metrics2.width <= i4) {
                        if (z2 && (boringLayout = this.mSavedLayout) != null) {
                            alignment2 = alignment;
                            boringLayout4 = boringLayout.replaceOrMake(charSequence2, this.mTextPaint, i4, alignment2, this.mSpacingMult, this.mSpacingAdd, metrics2, this.mIncludePad, truncateAt, i2, isFallbackLineSpacingForBoringLayout(), this.mUseBoundsForWidth, getResolvedMinimumFontMetrics());
                            i4 = i;
                            i3 = i2;
                        } else {
                            i4 = i;
                            i3 = i2;
                            alignment2 = alignment;
                            truncateAt2 = truncateAt;
                            boringLayout4 = new BoringLayout(charSequence2, this.mTextPaint, i4, alignment2, this.mSpacingMult, this.mSpacingAdd, this.mIncludePad, isFallbackLineSpacingForBoringLayout(), i3, truncateAt2, metrics2, this.mUseBoundsForWidth, this.mShiftDrawingOffsetForStartOverhang, getResolvedMinimumFontMetrics());
                        }
                    }
                    truncateAt2 = truncateAt;
                } else {
                    if (z2 && (boringLayout3 = this.mSavedLayout) != null) {
                        boringLayout2 = boringLayout3.replaceOrMake(charSequence2, this.mTextPaint, i4, alignment2, this.mSpacingMult, this.mSpacingAdd, metrics2, this.mIncludePad, null, i, isFallbackLineSpacingForBoringLayout(), this.mUseBoundsForWidth, getResolvedMinimumFontMetrics());
                        i4 = i;
                    } else {
                        i4 = i;
                        boringLayout2 = new BoringLayout(charSequence2, this.mTextPaint, i4, alignment, this.mSpacingMult, this.mSpacingAdd, this.mIncludePad, isFallbackLineSpacingForBoringLayout(), i, null, metrics2, this.mUseBoundsForWidth, this.mShiftDrawingOffsetForStartOverhang, getResolvedMinimumFontMetrics());
                    }
                    boringLayout4 = boringLayout2;
                    if (z2) {
                        this.mSavedLayout = (BoringLayout) boringLayout4;
                    }
                }
                i3 = i2;
                alignment2 = alignment;
                truncateAt2 = truncateAt;
            }
            if (boringLayout4 == null) {
                return boringLayout4;
            }
            StaticLayout.Builder minimumFontMetrics = StaticLayout.Builder.obtain(charSequence2, 0, charSequence2.length(), this.mTextPaint, i4).setAlignment(alignment2).setTextDirection(this.mTextDir).setLineSpacing(this.mSpacingAdd, this.mSpacingMult).setIncludePad(this.mIncludePad).setUseLineSpacingFromFallbacks(isFallbackLineSpacingForStaticLayout()).setBreakStrategy(this.mBreakStrategy).setHyphenationFrequency(this.mHyphenationFrequency).setJustificationMode(this.mJustificationMode).setMaxLines(this.mMaxMode == 1 ? this.mMaximum : Integer.MAX_VALUE).setLineBreakConfig(LineBreakConfig.getLineBreakConfig(this.mLineBreakStyle, this.mLineBreakWordStyle)).setUseBoundsForWidth(this.mUseBoundsForWidth).setMinimumFontMetrics(getResolvedMinimumFontMetrics());
            if (z) {
                minimumFontMetrics.setEllipsize(truncateAt2).setEllipsizedWidth(i3);
            }
            return minimumFontMetrics.build();
        }
        truncateAt2 = truncateAt;
        i3 = i2;
        if (boringLayout4 == null) {
        }
    }

    private boolean compressText(float f) {
        if (!isHardwareAccelerated() && f > 0.0f && this.mLayout != null && getLineCount() == 1 && !this.mUserSetTextScaleX && this.mTextPaint.getTextScaleX() == 1.0f) {
            float lineWidth = ((this.mLayout.getLineWidth(0) + 1.0f) - f) / f;
            if (lineWidth > 0.0f && lineWidth <= 0.07f) {
                this.mTextPaint.setTextScaleX((1.0f - lineWidth) - 0.005f);
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

    private static int desired(Layout layout, boolean z) {
        int lineCount = layout.getLineCount();
        CharSequence text = layout.getText();
        for (int i = 0; i < lineCount - 1; i++) {
            if (text.charAt(layout.getLineEnd(i) - 1) != '\n') {
                return -1;
            }
        }
        float fMax = 0.0f;
        for (int i2 = 0; i2 < lineCount; i2++) {
            fMax = Math.max(fMax, layout.getLineMax(i2));
        }
        if (z) {
            fMax = Math.max(fMax, layout.computeDrawingBoundingBox().width());
        }
        return (int) Math.ceil(fMax);
    }

    public void setIncludeFontPadding(boolean z) {
        if (this.mIncludePad != z) {
            this.mIncludePad = z;
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

    /* JADX WARN: Removed duplicated region for block: B:129:0x025c  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x025f  */
    /* JADX WARN: Removed duplicated region for block: B:140:0x0292  */
    /* JADX WARN: Removed duplicated region for block: B:151:0x02bf  */
    /* JADX WARN: Removed duplicated region for block: B:161:0x02ea  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected void onMeasure(int i, int i2) {
        BoringLayout.Metrics metricsIsBoring;
        boolean z;
        int i3;
        int iMax;
        int i4;
        int iMin;
        int iMax2;
        BoringLayout.Metrics metrics;
        int iMin2;
        int i5;
        BoringLayout.Metrics metricsIsBoring2;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        CharSequence textForMultiSelection;
        int multiSelectionCount;
        int i12;
        int i13;
        int mode = View.MeasureSpec.getMode(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        int size = View.MeasureSpec.getSize(i);
        int size2 = View.MeasureSpec.getSize(i2);
        BoringLayout.Metrics metrics2 = UNKNOWN_BORING;
        if (this.mTextDir == null) {
            this.mTextDir = getTextDirectionHeuristic();
        }
        float f = mode == Integer.MIN_VALUE ? size : Float.MAX_VALUE;
        if (mode == 1073741824) {
            metrics = metrics2;
            i3 = 1;
            i5 = -1;
            z = false;
            iMin2 = size;
        } else {
            Layout layout = this.mLayout;
            int iDesired = (layout == null || this.mEllipsize != null) ? -1 : desired(layout, this.mUseBoundsForWidth);
            if (iDesired < 0) {
                metricsIsBoring = BoringLayout.isBoring(this.mTransformed, this.mTextPaint, this.mTextDir, isFallbackLineSpacingForBoringLayout(), getResolvedMinimumFontMetrics(), this.mBoring);
                if (metricsIsBoring != null) {
                    this.mBoring = metricsIsBoring;
                }
                z = false;
            } else {
                metricsIsBoring = metrics2;
                z = true;
            }
            if (metricsIsBoring == null || metricsIsBoring == metrics2) {
                if (iDesired < 0) {
                    CharSequence charSequence = this.mTransformed;
                    i3 = 1;
                    iDesired = (int) Math.ceil(Layout.getDesiredWidthWithLimit(charSequence, 0, charSequence.length(), this.mTextPaint, this.mTextDir, f, this.mUseBoundsForWidth));
                } else {
                    i3 = 1;
                }
                iMax = iDesired;
                i4 = iMax;
            } else {
                if (this.mUseBoundsForWidth) {
                    RectF drawingBoundingBox = metricsIsBoring.getDrawingBoundingBox();
                    iMax = Math.max(metricsIsBoring.width, (int) Math.ceil(Math.max(drawingBoundingBox.right, metricsIsBoring.width) - Math.min(drawingBoundingBox.left, 0.0f)));
                } else {
                    iMax = metricsIsBoring.width;
                }
                i3 = 1;
                i4 = iDesired;
            }
            if (CoreRune.GRAPHICS_RENDERER_HCF && isHighContrastTextEnabled()) {
                iMax += (int) Math.ceil(this.mTextPaint.getHCTStrokeWidth());
            }
            Drawables drawables = this.mDrawables;
            if (drawables != null) {
                iMax = Math.max(Math.max(iMax, drawables.mDrawableWidthTop), drawables.mDrawableWidthBottom);
            }
            if (this.mHint != null) {
                Layout layout2 = this.mHintLayout;
                int iDesired2 = (layout2 == null || this.mEllipsize != null) ? -1 : desired(layout2, this.mUseBoundsForWidth);
                if (iDesired2 < 0) {
                    metricsIsBoring2 = BoringLayout.isBoring(this.mHint, this.mTextPaint, this.mTextDir, isFallbackLineSpacingForBoringLayout(), getResolvedMinimumFontMetrics(), this.mHintBoring);
                    if (metricsIsBoring2 != null) {
                        this.mHintBoring = metricsIsBoring2;
                    }
                } else {
                    metricsIsBoring2 = metrics2;
                }
                if (metricsIsBoring2 == null || metricsIsBoring2 == metrics2) {
                    if (iDesired2 < 0) {
                        CharSequence charSequence2 = this.mHint;
                        i6 = iMax;
                        iDesired2 = (int) Math.ceil(Layout.getDesiredWidthWithLimit(charSequence2, 0, charSequence2.length(), this.mTextPaint, this.mTextDir, f, this.mUseBoundsForWidth));
                    } else {
                        i6 = iMax;
                    }
                    iMax = iDesired2;
                } else {
                    int i14 = iMax;
                    iMax = metricsIsBoring2.width;
                    i6 = i14;
                }
                if (iMax <= i6) {
                    iMax = i6;
                }
                metrics2 = metricsIsBoring2;
            }
            int compoundPaddingLeft = iMax + getCompoundPaddingLeft() + getCompoundPaddingRight();
            if (this.mMaxWidthMode == i3) {
                iMin = Math.min(compoundPaddingLeft, this.mMaxWidth * getLineHeight());
            } else {
                iMin = Math.min(compoundPaddingLeft, this.mMaxWidth);
            }
            if (this.mMinWidthMode == i3) {
                iMax2 = Math.max(iMin, this.mMinWidth * getLineHeight());
            } else {
                iMax2 = Math.max(iMin, this.mMinWidth);
            }
            int iMax3 = Math.max(iMax2, getSuggestedMinimumWidth());
            if (mode == Integer.MIN_VALUE) {
                BoringLayout.Metrics metrics3 = metricsIsBoring;
                metrics = metrics2;
                metrics2 = metrics3;
                iMin2 = Math.min(size, iMax3);
            } else {
                BoringLayout.Metrics metrics4 = metricsIsBoring;
                metrics = metrics2;
                metrics2 = metrics4;
                iMin2 = iMax3;
            }
            i5 = i4;
        }
        int compoundPaddingLeft2 = (iMin2 - getCompoundPaddingLeft()) - getCompoundPaddingRight();
        int i15 = this.mHorizontallyScrolling ? 1048576 : compoundPaddingLeft2;
        Layout layout3 = this.mHintLayout;
        int width = layout3 == null ? i15 : layout3.getWidth();
        Layout layout4 = this.mLayout;
        if (layout4 == null) {
            i7 = i3;
            i8 = 0;
            i9 = 1073741824;
            i10 = -1;
            makeNewLayout(i15, i15, metrics2, metrics, (iMin2 - getCompoundPaddingLeft()) - getCompoundPaddingRight(), false);
        } else {
            i7 = i3;
            i8 = 0;
            i9 = 1073741824;
            i10 = -1;
            int i16 = (layout4.getWidth() == i15 && width == i15 && this.mLayout.getEllipsizedWidth() == (iMin2 - getCompoundPaddingLeft()) - getCompoundPaddingRight()) ? 0 : i7;
            int i17 = (this.mHint != null || this.mEllipsize != null || i15 <= this.mLayout.getWidth() || (!(this.mLayout instanceof BoringLayout) && (!z || i5 < 0 || i5 > i15))) ? 0 : i7;
            int i18 = (this.mMaxMode == this.mOldMaxMode && this.mMaximum == this.mOldMaximum) ? 0 : i7;
            if (i16 != 0 || i18 != 0) {
                if (i18 == 0 && i17 != 0) {
                    this.mLayout.increaseWidthTo(i15);
                } else {
                    i11 = i7;
                    makeNewLayout(i15, i15, metrics2, metrics, (iMin2 - getCompoundPaddingLeft()) - getCompoundPaddingRight(), false);
                }
            }
            if (mode2 != i9) {
                this.mDesiredHeightAtMeasure = i10;
            } else {
                int desiredHeight = getDesiredHeight();
                this.mDesiredHeightAtMeasure = desiredHeight;
                if (mode2 == Integer.MIN_VALUE) {
                    desiredHeight = Math.min(desiredHeight, size2);
                }
                size2 = desiredHeight;
                if (CoreRune.GRAPHICS_RENDERER_HCF && isHighContrastTextEnabled()) {
                    size2 += (int) Math.ceil(this.mTextPaint.getHCTStrokeWidth());
                }
            }
            int compoundPaddingTop = (size2 - getCompoundPaddingTop()) - getCompoundPaddingBottom();
            if (this.mMaxMode == i11) {
                int lineCount = this.mLayout.getLineCount();
                int i19 = this.mMaximum;
                if (lineCount > i19) {
                    compoundPaddingTop = Math.min(compoundPaddingTop, this.mLayout.getLineTop(i19));
                }
            }
            if (this.mMovement == null || this.mLayout.getWidth() > compoundPaddingLeft2 || this.mLayout.getHeight() > compoundPaddingTop) {
                registerForPreDraw();
            } else {
                scrollTo(i8, i8);
            }
            if (ViewRune.WIDGET_PEN_SUPPORTED && this.mhasMultiSelection) {
                textForMultiSelection = getTextForMultiSelection();
                int[] iArr = new int[2];
                boolean visibleTextRange = getVisibleTextRange(iArr);
                if (textForMultiSelection != null && visibleTextRange) {
                    Spannable spannable = (Spannable) textForMultiSelection;
                    int[] multiSelectionStart = MultiSelection.getMultiSelectionStart(spannable);
                    int[] multiSelectionEnd = MultiSelection.getMultiSelectionEnd(spannable);
                    multiSelectionCount = MultiSelection.getMultiSelectionCount(spannable);
                    for (i12 = i8; i12 < multiSelectionCount; i12++) {
                        int i20 = multiSelectionStart[i12];
                        int i21 = multiSelectionEnd[i12];
                        int i22 = iArr[i8];
                        if (i20 < i22) {
                            i13 = i22;
                        } else {
                            i13 = iArr[i11];
                            if (i20 <= i13) {
                                i13 = i20;
                            }
                        }
                        if (i21 >= i22 && i21 <= (i22 = iArr[i11])) {
                            i22 = i21;
                        }
                        if (i20 != i13 || i21 != i22) {
                            clearMultiSelection();
                            break;
                        }
                    }
                }
            }
            setMeasuredDimension(iMin2, size2);
        }
        i11 = i7;
        if (mode2 != i9) {
        }
        int compoundPaddingTop2 = (size2 - getCompoundPaddingTop()) - getCompoundPaddingBottom();
        if (this.mMaxMode == i11) {
        }
        if (this.mMovement == null) {
            registerForPreDraw();
        }
        if (ViewRune.WIDGET_PEN_SUPPORTED) {
            textForMultiSelection = getTextForMultiSelection();
            int[] iArr2 = new int[2];
            boolean visibleTextRange2 = getVisibleTextRange(iArr2);
            if (textForMultiSelection != null) {
                Spannable spannable2 = (Spannable) textForMultiSelection;
                int[] multiSelectionStart2 = MultiSelection.getMultiSelectionStart(spannable2);
                int[] multiSelectionEnd2 = MultiSelection.getMultiSelectionEnd(spannable2);
                multiSelectionCount = MultiSelection.getMultiSelectionCount(spannable2);
                while (i12 < multiSelectionCount) {
                }
            }
        }
        setMeasuredDimension(iMin2, size2);
    }

    private void autoSizeText() {
        TextView textView;
        if (isAutoSizeEnabled()) {
            if (!this.mNeedsAutoSizeText) {
                textView = this;
            } else {
                if (getMeasuredWidth() <= 0 || getMeasuredHeight() <= 0) {
                    return;
                }
                int measuredWidth = this.mHorizontallyScrolling ? 1048576 : (getMeasuredWidth() - getTotalPaddingLeft()) - getTotalPaddingRight();
                int measuredHeight = (getMeasuredHeight() - getExtendedPaddingBottom()) - getExtendedPaddingTop();
                if (measuredWidth <= 0 || measuredHeight <= 0) {
                    return;
                }
                RectF rectF = TEMP_RECTF;
                synchronized (rectF) {
                    rectF.setEmpty();
                    rectF.right = measuredWidth;
                    rectF.bottom = measuredHeight;
                    float fFindLargestTextSizeWhichFits = findLargestTextSizeWhichFits(rectF);
                    if (fFindLargestTextSizeWhichFits != getTextSize()) {
                        setTextSizeInternal(0, fFindLargestTextSizeWhichFits, false);
                        BoringLayout.Metrics metrics = UNKNOWN_BORING;
                        textView = this;
                        textView.makeNewLayout(measuredWidth, 0, metrics, metrics, ((this.mRight - this.mLeft) - getCompoundPaddingLeft()) - getCompoundPaddingRight(), false);
                    } else {
                        textView = this;
                    }
                }
            }
            textView.mNeedsAutoSizeText = true;
        }
    }

    private int findLargestTextSizeWhichFits(RectF rectF) {
        int length = this.mAutoSizeTextSizesInPx.length;
        if (length == 0) {
            throw new IllegalStateException("No available text sizes to choose from.");
        }
        int i = 1;
        int i2 = length - 1;
        int i3 = 0;
        while (i <= i2) {
            int i4 = (i + i2) / 2;
            if (suggestedSizeFitsInSpace(this.mAutoSizeTextSizesInPx[i4], rectF)) {
                int i5 = i4 + 1;
                i3 = i;
                i = i5;
            } else {
                i3 = i4 - 1;
                i2 = i3;
            }
        }
        return this.mAutoSizeTextSizesInPx[i3];
    }

    private boolean suggestedSizeFitsInSpace(int i, RectF rectF) {
        CharSequence text = this.mTransformed;
        if (text == null) {
            text = getText();
        }
        int maxLines = getMaxLines();
        TextPaint textPaint = this.mTempTextPaint;
        if (textPaint == null) {
            this.mTempTextPaint = new TextPaint();
        } else {
            textPaint.reset();
        }
        this.mTempTextPaint.set(getPaint());
        this.mTempTextPaint.setTextSize(i);
        StaticLayout.Builder builderObtain = StaticLayout.Builder.obtain(text, 0, text.length(), this.mTempTextPaint, Math.round(rectF.right));
        builderObtain.setAlignment(getLayoutAlignment()).setLineSpacing(getLineSpacingExtra(), getLineSpacingMultiplier()).setIncludePad(getIncludeFontPadding()).setUseLineSpacingFromFallbacks(isFallbackLineSpacingForStaticLayout()).setBreakStrategy(getBreakStrategy()).setHyphenationFrequency(getHyphenationFrequency()).setJustificationMode(getJustificationMode()).setMaxLines(this.mMaxMode == 1 ? this.mMaximum : Integer.MAX_VALUE).setTextDirection(getTextDirectionHeuristic()).setLineBreakConfig(LineBreakConfig.getLineBreakConfig(this.mLineBreakStyle, this.mLineBreakWordStyle)).setUseBoundsForWidth(this.mUseBoundsForWidth).setMinimumFontMetrics(getResolvedMinimumFontMetrics());
        StaticLayout staticLayoutBuild = builderObtain.build();
        return (maxLines == -1 || staticLayoutBuild.getLineCount() <= maxLines) && ((float) staticLayoutBuild.getHeight()) <= rectF.bottom;
    }

    private int getDesiredHeight() {
        return Math.max(getDesiredHeight(this.mLayout, true), getDesiredHeight(this.mHintLayout, this.mEllipsize != null));
    }

    private int getDesiredHeight(Layout layout, boolean z) {
        int i;
        if (layout == null) {
            return 0;
        }
        int height = layout.getHeight(z);
        Drawables drawables = this.mDrawables;
        if (drawables != null) {
            height = Math.max(Math.max(height, drawables.mDrawableHeightLeft), drawables.mDrawableHeightRight);
        }
        int lineCount = layout.getLineCount();
        int compoundPaddingTop = getCompoundPaddingTop() + getCompoundPaddingBottom();
        int iMax = height + compoundPaddingTop;
        if (this.mMaxMode != 1) {
            iMax = Math.min(iMax, this.mMaximum);
        } else if (z && lineCount > (i = this.mMaximum) && ((layout instanceof DynamicLayout) || (layout instanceof BoringLayout))) {
            int lineTop = layout.getLineTop(i);
            if (drawables != null) {
                lineTop = Math.max(Math.max(lineTop, drawables.mDrawableHeightLeft), drawables.mDrawableHeightRight);
            }
            iMax = lineTop + compoundPaddingTop;
            lineCount = this.mMaximum;
        }
        if (this.mMinMode == 1) {
            if (lineCount < this.mMinimum) {
                iMax += getLineHeight() * (this.mMinimum - lineCount);
            }
        } else {
            iMax = Math.max(iMax, this.mMinimum);
        }
        return Math.max(iMax, getSuggestedMinimumHeight());
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x004c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void checkForResize() {
        boolean z = false;
        if (this.mLayout != null) {
            if (this.mLayoutParams.width == -2) {
                invalidate();
                z = true;
            }
            if (this.mLayoutParams.height == -2) {
                int desiredHeight = getDesiredHeight();
                if (CoreRune.GRAPHICS_RENDERER_HCF && isHighContrastTextEnabled()) {
                    desiredHeight += (int) Math.ceil(this.mTextPaint.getHCTStrokeWidth());
                }
                if (desiredHeight != getHeight()) {
                    z = true;
                }
            } else if (this.mLayoutParams.height == -1 && this.mDesiredHeightAtMeasure >= 0 && getDesiredHeight() != this.mDesiredHeightAtMeasure) {
            }
        }
        if (z) {
            requestLayout();
        }
    }

    private void checkForRelayout() {
        Layout layout;
        if ((this.mLayoutParams.width != -2 || (this.mMaxWidthMode == this.mMinWidthMode && this.mMaxWidth == this.mMinWidth)) && ((this.mHint == null || this.mHintLayout != null) && ((this.mRight - this.mLeft) - getCompoundPaddingLeft()) - getCompoundPaddingRight() > 0)) {
            int height = this.mLayout.getHeight();
            int width = this.mLayout.getWidth();
            Layout layout2 = this.mHintLayout;
            int width2 = layout2 == null ? 0 : layout2.getWidth();
            BoringLayout.Metrics metrics = UNKNOWN_BORING;
            makeNewLayout(width, width2, metrics, metrics, ((this.mRight - this.mLeft) - getCompoundPaddingLeft()) - getCompoundPaddingRight(), false);
            if (this.mEllipsize != TextUtils.TruncateAt.MARQUEE) {
                if (this.mLayoutParams.height != -2 && this.mLayoutParams.height != -1) {
                    autoSizeText();
                    invalidate();
                    return;
                } else if (this.mLayout.getHeight() == height && ((layout = this.mHintLayout) == null || layout.getHeight() == height)) {
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
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        int i5 = this.mDeferScroll;
        if (i5 >= 0) {
            this.mDeferScroll = -1;
            bringPointIntoView(Math.min(i5, this.mText.length()));
        }
        autoSizeText();
    }

    private boolean isShowingHint() {
        return (!TextUtils.isEmpty(this.mText) || TextUtils.isEmpty(this.mHint) || this.mHideHint) ? false : true;
    }

    private boolean bringTextIntoView() {
        int iFloor;
        int iCeil;
        Layout layout = isShowingHint() ? this.mHintLayout : this.mLayout;
        int lineCount = (this.mGravity & 112) == 80 ? layout.getLineCount() - 1 : 0;
        Layout.Alignment paragraphAlignment = layout.getParagraphAlignment(lineCount);
        int paragraphDirection = layout.getParagraphDirection(lineCount);
        int compoundPaddingLeft = ((this.mRight - this.mLeft) - getCompoundPaddingLeft()) - getCompoundPaddingRight();
        int extendedPaddingTop = ((this.mBottom - this.mTop) - getExtendedPaddingTop()) - getExtendedPaddingBottom();
        int height = layout.getHeight();
        if (paragraphAlignment == Layout.Alignment.ALIGN_NORMAL) {
            paragraphAlignment = paragraphDirection == 1 ? Layout.Alignment.ALIGN_LEFT : Layout.Alignment.ALIGN_RIGHT;
        } else if (paragraphAlignment == Layout.Alignment.ALIGN_OPPOSITE) {
            paragraphAlignment = paragraphDirection == 1 ? Layout.Alignment.ALIGN_RIGHT : Layout.Alignment.ALIGN_LEFT;
        }
        if (paragraphAlignment == Layout.Alignment.ALIGN_CENTER) {
            iFloor = (int) Math.floor(layout.getLineLeft(lineCount));
            iCeil = (int) Math.ceil(layout.getLineRight(lineCount));
            if (iCeil - iFloor < compoundPaddingLeft) {
                iCeil = (iCeil + iFloor) / 2;
                compoundPaddingLeft /= 2;
            } else if (paragraphDirection < 0) {
            }
            iFloor = iCeil - compoundPaddingLeft;
        } else if (paragraphAlignment == Layout.Alignment.ALIGN_RIGHT) {
            iCeil = (int) Math.ceil(layout.getLineRight(lineCount));
            iFloor = iCeil - compoundPaddingLeft;
        } else {
            iFloor = (int) Math.floor(layout.getLineLeft(lineCount));
        }
        int i = (height >= extendedPaddingTop && (this.mGravity & 112) == 80) ? height - extendedPaddingTop : 0;
        if (iFloor == this.mScrollX && i == this.mScrollY) {
            return false;
        }
        scrollTo(iFloor, i);
        return true;
    }

    public boolean bringPointIntoView(int i) {
        return bringPointIntoView(i, false);
    }

    /* JADX WARN: Removed duplicated region for block: B:66:0x00f1  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean bringPointIntoView(int i, boolean z) {
        int paragraphDirection;
        boolean z2;
        if (isLayoutRequested()) {
            this.mDeferScroll = i;
            return false;
        }
        int iOriginalToTransformed = originalToTransformed(i, 1);
        Layout layout = isShowingHint() ? this.mHintLayout : this.mLayout;
        if (layout == null) {
            return false;
        }
        int lineForOffset = layout.getLineForOffset(iOriginalToTransformed);
        int i2 = AnonymousClass8.$SwitchMap$android$text$Layout$Alignment[layout.getParagraphAlignment(lineForOffset).ordinal()];
        if (i2 == 1) {
            paragraphDirection = 1;
        } else if (i2 == 2) {
            paragraphDirection = -1;
        } else if (i2 == 3) {
            paragraphDirection = layout.getParagraphDirection(lineForOffset);
        } else {
            paragraphDirection = i2 != 4 ? 0 : -layout.getParagraphDirection(lineForOffset);
        }
        int primaryHorizontal = (int) layout.getPrimaryHorizontal(iOriginalToTransformed, paragraphDirection > 0);
        int lineTop = layout.getLineTop(lineForOffset);
        int lineTop2 = layout.getLineTop(lineForOffset + 1);
        int iFloor = (int) Math.floor(layout.getLineLeft(lineForOffset));
        int iCeil = (int) Math.ceil(layout.getLineRight(lineForOffset));
        int height = layout.getHeight();
        int compoundPaddingLeft = ((this.mRight - this.mLeft) - getCompoundPaddingLeft()) - getCompoundPaddingRight();
        int extendedPaddingTop = ((this.mBottom - this.mTop) - getExtendedPaddingTop()) - getExtendedPaddingBottom();
        if (!this.mHorizontallyScrolling && iCeil - iFloor > compoundPaddingLeft && iCeil > primaryHorizontal) {
            iCeil = Math.max(primaryHorizontal, iFloor + compoundPaddingLeft);
        }
        int i3 = (lineTop2 - lineTop) / 2;
        int i4 = extendedPaddingTop / 4;
        if (i3 <= i4) {
            i4 = i3;
        }
        int i5 = compoundPaddingLeft / 4;
        if (i3 > i5) {
            i3 = i5;
        }
        int i6 = this.mScrollX;
        int i7 = this.mScrollY;
        if (lineTop - i7 < i4) {
            i7 = lineTop - i4;
        }
        int i8 = extendedPaddingTop - i4;
        if (lineTop2 - i7 > i8) {
            i7 = lineTop2 - i8;
        }
        if (height - i7 < extendedPaddingTop) {
            i7 = height - extendedPaddingTop;
        }
        if (0 - i7 > 0) {
            i7 = 0;
        }
        if (paragraphDirection != 0) {
            if (primaryHorizontal - i6 < i3) {
                i6 = primaryHorizontal - i3;
            }
            int i9 = compoundPaddingLeft - i3;
            if (primaryHorizontal - i6 > i9) {
                i6 = primaryHorizontal - i9;
            }
        }
        if (paragraphDirection < 0) {
            if (iFloor - i6 <= 0) {
                iFloor = i6;
            }
            if (iCeil - iFloor < compoundPaddingLeft) {
                iFloor = iCeil - compoundPaddingLeft;
            }
        } else if (paragraphDirection > 0) {
            if (iCeil - i6 < compoundPaddingLeft) {
                i6 = iCeil - compoundPaddingLeft;
            }
            if (iFloor - i6 <= 0) {
                iFloor = i6;
            }
        } else {
            int i10 = iCeil - iFloor;
            if (i10 <= compoundPaddingLeft) {
                iFloor -= (compoundPaddingLeft - i10) / 2;
            } else if (primaryHorizontal <= iCeil - i3) {
                if (primaryHorizontal >= iFloor + i3 && iFloor <= i6) {
                    if (iCeil >= i6 + compoundPaddingLeft) {
                        if (primaryHorizontal - i6 < i3) {
                            i6 = primaryHorizontal - i3;
                        }
                        iFloor = i6;
                        int i11 = compoundPaddingLeft - i3;
                        if (primaryHorizontal - iFloor > i11) {
                            iFloor = primaryHorizontal - i11;
                        }
                    }
                }
            }
        }
        if (iFloor == this.mScrollX && i7 == this.mScrollY) {
            z2 = false;
        } else {
            if (this.mScroller == null) {
                scrollTo(iFloor, i7);
            } else {
                long jCurrentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis() - this.mLastScroll;
                int i12 = iFloor - this.mScrollX;
                int i13 = i7 - this.mScrollY;
                if (jCurrentAnimationTimeMillis > 250) {
                    this.mScroller.startScroll(this.mScrollX, this.mScrollY, i12, i13);
                    awakenScrollBars(this.mScroller.getDuration());
                    invalidate();
                } else {
                    if (!this.mScroller.isFinished()) {
                        this.mScroller.abortAnimation();
                    }
                    scrollBy(i12, i13);
                }
                this.mLastScroll = AnimationUtils.currentAnimationTimeMillis();
            }
            z2 = true;
        }
        if (z || isFocused()) {
            if (this.mTempRect == null) {
                this.mTempRect = new Rect();
            }
            this.mTempRect.set(primaryHorizontal - 2, lineTop, primaryHorizontal + 2, lineTop2);
            getInterestingRect(this.mTempRect, lineForOffset);
            this.mTempRect.offset(this.mScrollX, this.mScrollY);
            if (requestRectangleOnScreen(this.mTempRect)) {
                return true;
            }
        }
        return z2;
    }

    /* renamed from: android.widget.TextView$8, reason: invalid class name */
    static /* synthetic */ class AnonymousClass8 {
        static final /* synthetic */ int[] $SwitchMap$android$text$Layout$Alignment;

        static {
            int[] iArr = new int[Layout.Alignment.values().length];
            $SwitchMap$android$text$Layout$Alignment = iArr;
            try {
                iArr[Layout.Alignment.ALIGN_LEFT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$android$text$Layout$Alignment[Layout.Alignment.ALIGN_RIGHT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$android$text$Layout$Alignment[Layout.Alignment.ALIGN_NORMAL.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$android$text$Layout$Alignment[Layout.Alignment.ALIGN_OPPOSITE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$android$text$Layout$Alignment[Layout.Alignment.ALIGN_CENTER.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    public boolean moveCursorToVisibleOffset() {
        int selectionStartTransformed;
        if (!(this.mText instanceof Spannable) || (selectionStartTransformed = getSelectionStartTransformed()) != getSelectionEndTransformed()) {
            return false;
        }
        int lineForOffset = this.mLayout.getLineForOffset(selectionStartTransformed);
        int lineTop = this.mLayout.getLineTop(lineForOffset);
        int lineTop2 = this.mLayout.getLineTop(lineForOffset + 1);
        int extendedPaddingTop = ((this.mBottom - this.mTop) - getExtendedPaddingTop()) - getExtendedPaddingBottom();
        int i = lineTop2 - lineTop;
        int i2 = i / 2;
        int i3 = extendedPaddingTop / 4;
        if (i2 > i3) {
            i2 = i3;
        }
        int i4 = this.mScrollY;
        int i5 = i4 + i2;
        if (lineTop < i5) {
            lineForOffset = this.mLayout.getLineForVertical(i5 + i);
        } else {
            int i6 = (extendedPaddingTop + i4) - i2;
            if (lineTop2 > i6) {
                lineForOffset = this.mLayout.getLineForVertical(i6 - i);
            }
        }
        int compoundPaddingLeft = ((this.mRight - this.mLeft) - getCompoundPaddingLeft()) - getCompoundPaddingRight();
        int offsetForHorizontal = this.mLayout.getOffsetForHorizontal(lineForOffset, this.mScrollX);
        int offsetForHorizontal2 = this.mLayout.getOffsetForHorizontal(lineForOffset, compoundPaddingLeft + r4);
        int i7 = offsetForHorizontal < offsetForHorizontal2 ? offsetForHorizontal : offsetForHorizontal2;
        if (offsetForHorizontal <= offsetForHorizontal2) {
            offsetForHorizontal = offsetForHorizontal2;
        }
        if (selectionStartTransformed >= i7) {
            i7 = selectionStartTransformed > offsetForHorizontal ? offsetForHorizontal : selectionStartTransformed;
        }
        if (i7 == selectionStartTransformed) {
            return false;
        }
        Selection.setSelection(this.mSpannable, transformedToOriginal(i7, 1));
        return true;
    }

    @Override // android.view.View
    public void computeScroll() {
        Scroller scroller = this.mScroller;
        if (scroller == null || !scroller.computeScrollOffset()) {
            return;
        }
        this.mScrollX = this.mScroller.getCurrX();
        this.mScrollY = this.mScroller.getCurrY();
        invalidateParentCaches();
        postInvalidate();
    }

    private void getInterestingRect(Rect rect, int i) {
        convertFromViewportToContentCoordinates(rect);
        if (i == 0) {
            rect.top -= getExtendedPaddingTop();
        }
        if (i == this.mLayout.getLineCount() - 1) {
            rect.bottom += getExtendedPaddingBottom();
        }
    }

    private void convertFromViewportToContentCoordinates(Rect rect) {
        int iViewportToContentHorizontalOffset = viewportToContentHorizontalOffset();
        rect.left += iViewportToContentHorizontalOffset;
        rect.right += iViewportToContentHorizontalOffset;
        int iViewportToContentVerticalOffset = viewportToContentVerticalOffset();
        rect.top += iViewportToContentVerticalOffset;
        rect.bottom += iViewportToContentVerticalOffset;
    }

    private PointF convertFromScreenToContentCoordinates(PointF pointF) {
        if (Flags.handwritingGestureWithTransformation()) {
            if (this.mTempMatrix == null) {
                this.mTempMatrix = new Matrix();
            }
            Matrix matrix = this.mTempMatrix;
            matrix.reset();
            transformMatrixToLocal(matrix);
            matrix.postTranslate(-viewportToContentHorizontalOffset(), -viewportToContentVerticalOffset());
            float[] fArr = {pointF.x, pointF.y};
            matrix.mapPoints(fArr);
            return new PointF(fArr[0], fArr[1]);
        }
        int[] locationOnScreen = getLocationOnScreen();
        PointF pointF2 = new PointF(pointF);
        pointF2.offset(-(locationOnScreen[0] + viewportToContentHorizontalOffset()), -(locationOnScreen[1] + viewportToContentVerticalOffset()));
        return pointF2;
    }

    private RectF convertFromScreenToContentCoordinates(RectF rectF) {
        if (Flags.handwritingGestureWithTransformation()) {
            if (this.mTempMatrix == null) {
                this.mTempMatrix = new Matrix();
            }
            Matrix matrix = this.mTempMatrix;
            matrix.reset();
            transformMatrixToLocal(matrix);
            matrix.postTranslate(-viewportToContentHorizontalOffset(), -viewportToContentVerticalOffset());
            RectF rectF2 = new RectF(rectF);
            matrix.mapRect(rectF2);
            return rectF2;
        }
        int[] locationOnScreen = getLocationOnScreen();
        RectF rectF3 = new RectF(rectF);
        rectF3.offset(-(locationOnScreen[0] + viewportToContentHorizontalOffset()), -(locationOnScreen[1] + viewportToContentVerticalOffset()));
        return rectF3;
    }

    int viewportToContentHorizontalOffset() {
        return getCompoundPaddingLeft() - this.mScrollX;
    }

    int viewportToContentVerticalOffset() {
        int extendedPaddingTop = getExtendedPaddingTop() - this.mScrollY;
        return (this.mGravity & 112) != 48 ? extendedPaddingTop + getVerticalOffset(false) : extendedPaddingTop;
    }

    @Override // android.view.View
    public void debug(int i) {
        String str;
        super.debug(i);
        String str2 = debugIndent(i) + "frame={" + this.mLeft + ", " + this.mTop + ", " + this.mRight + ", " + this.mBottom + "} scroll={" + this.mScrollX + ", " + this.mScrollY + "} ";
        if (this.mText != null) {
            str = str2 + "mText=\"" + ((Object) this.mText) + "\" ";
            if (this.mLayout != null) {
                str = str + "mLayout width=" + this.mLayout.getWidth() + " height=" + this.mLayout.getHeight();
            }
        } else {
            str = str2 + "mText=NULL";
        }
        Log.d("View", str);
    }

    @ViewDebug.ExportedProperty(category = "text")
    public int getSelectionStart() {
        return Selection.getSelectionStart(getText());
    }

    @ViewDebug.ExportedProperty(category = "text")
    public int getSelectionEnd() {
        return Selection.getSelectionEnd(getText());
    }

    public void getSelection(int i, int i2, Layout.SelectionRectangleConsumer selectionRectangleConsumer) {
        this.mLayout.getSelection(originalToTransformed(i, 1), originalToTransformed(i2, 1), selectionRectangleConsumer);
    }

    int getSelectionStartTransformed() {
        int selectionStart = getSelectionStart();
        return selectionStart < 0 ? selectionStart : originalToTransformed(selectionStart, 1);
    }

    int getSelectionEndTransformed() {
        int selectionEnd = getSelectionEnd();
        return selectionEnd < 0 ? selectionEnd : originalToTransformed(selectionEnd, 1);
    }

    public boolean hasSelection() {
        int selectionStart = getSelectionStart();
        int selectionEnd = getSelectionEnd();
        if (selectionStart >= selectionEnd) {
            selectionStart = selectionEnd;
            selectionEnd = selectionStart;
        }
        return selectionStart >= 0 && selectionEnd > 0 && selectionStart != selectionEnd;
    }

    public String getSelectedText() {
        if (!hasSelection()) {
            return null;
        }
        int selectionStart = getSelectionStart();
        int selectionEnd = getSelectionEnd();
        CharSequence charSequence = this.mText;
        return String.valueOf(selectionStart > selectionEnd ? charSequence.subSequence(selectionEnd, selectionStart) : charSequence.subSequence(selectionStart, selectionEnd));
    }

    public void setSingleLine() {
        setSingleLine(true);
    }

    @RemotableViewMethod
    public void setAllCaps(boolean z) {
        if (z) {
            setTransformationMethod(new AllCapsTransformationMethod(getContext()));
        } else {
            setTransformationMethod(null);
        }
    }

    public boolean isAllCaps() {
        TransformationMethod transformationMethod = getTransformationMethod();
        return transformationMethod != null && (transformationMethod instanceof AllCapsTransformationMethod);
    }

    @RemotableViewMethod
    public void setSingleLine(boolean z) {
        setInputTypeSingleLine(z);
        applySingleLine(z, true, true, true);
    }

    private void setInputTypeSingleLine(boolean z) {
        Editor editor = this.mEditor;
        if (editor == null || (editor.mInputType & 15) != 1) {
            return;
        }
        if (z) {
            this.mEditor.mInputType &= -131073;
        } else {
            this.mEditor.mInputType |= 131072;
        }
    }

    private void applySingleLine(boolean z, boolean z2, boolean z3, boolean z4) {
        this.mSingleLine = z;
        if (z) {
            setLines(1);
            setHorizontallyScrolling(true);
            if (z2) {
                setTransformationMethod(SingleLineTransformationMethod.getInstance());
            }
            if (z4 && this.mBufferType == BufferType.EDITABLE) {
                InputFilter[] filters = getFilters();
                for (InputFilter inputFilter : getFilters()) {
                    if (inputFilter instanceof InputFilter.LengthFilter) {
                        return;
                    }
                }
                if (this.mSingleLineLengthFilter == null) {
                    this.mSingleLineLengthFilter = new InputFilter.LengthFilter(5000);
                }
                InputFilter[] inputFilterArr = new InputFilter[filters.length + 1];
                System.arraycopy(filters, 0, inputFilterArr, 0, filters.length);
                inputFilterArr[filters.length] = this.mSingleLineLengthFilter;
                setFilters(inputFilterArr);
                lambda$setTextAsync$0(getText());
                return;
            }
            return;
        }
        if (z3) {
            setMaxLines(Integer.MAX_VALUE);
        }
        setHorizontallyScrolling(false);
        if (z2) {
            setTransformationMethod(null);
        }
        if (z4 && this.mBufferType == BufferType.EDITABLE) {
            InputFilter[] filters2 = getFilters();
            if (filters2.length == 0 || this.mSingleLineLengthFilter == null) {
                return;
            }
            int i = 0;
            while (true) {
                if (i >= filters2.length) {
                    i = -1;
                    break;
                } else if (filters2[i] == this.mSingleLineLengthFilter) {
                    break;
                } else {
                    i++;
                }
            }
            if (i == -1) {
                return;
            }
            if (filters2.length == 1) {
                setFilters(NO_FILTERS);
                return;
            }
            InputFilter[] inputFilterArr2 = new InputFilter[filters2.length - 1];
            System.arraycopy(filters2, 0, inputFilterArr2, 0, i);
            System.arraycopy(filters2, i + 1, inputFilterArr2, i, (filters2.length - i) - 1);
            setFilters(inputFilterArr2);
            this.mSingleLineLengthFilter = null;
        }
    }

    public void setEllipsize(TextUtils.TruncateAt truncateAt) {
        if (this.mEllipsize != truncateAt) {
            this.mEllipsize = truncateAt;
            if (this.mLayout != null) {
                nullLayouts();
                requestLayout();
                invalidate();
            }
        }
    }

    public void setMarqueeRepeatLimit(int i) {
        this.mMarqueeRepeatLimit = i;
    }

    public int getMarqueeRepeatLimit() {
        return this.mMarqueeRepeatLimit;
    }

    @ViewDebug.ExportedProperty
    public TextUtils.TruncateAt getEllipsize() {
        return this.mEllipsize;
    }

    @RemotableViewMethod
    public void setSelectAllOnFocus(boolean z) {
        createEditorIfNeeded();
        this.mEditor.mSelectAllOnFocus = z;
        if (z) {
            CharSequence charSequence = this.mText;
            if (charSequence instanceof Spannable) {
                return;
            }
            setText(charSequence, BufferType.SPANNABLE);
        }
    }

    @RemotableViewMethod
    public void setCursorVisible(boolean z) {
        this.mCursorVisibleFromAttr = z;
        updateCursorVisibleInternal();
    }

    public void setImeConsumesInput(boolean z) {
        this.mImeIsConsumingInput = z;
        updateCursorVisibleInternal();
    }

    private void updateCursorVisibleInternal() {
        boolean z = this.mCursorVisibleFromAttr && !this.mImeIsConsumingInput;
        if (z && this.mEditor == null) {
            return;
        }
        createEditorIfNeeded();
        if (this.mEditor.mCursorVisible != z) {
            this.mEditor.mCursorVisible = z;
            invalidate();
            this.mEditor.makeBlink();
            this.mEditor.prepareCursorControllers();
        }
    }

    public boolean isCursorVisible() {
        Editor editor = this.mEditor;
        if (editor == null) {
            return true;
        }
        return editor.mCursorVisible;
    }

    public boolean isCursorVisibleFromAttr() {
        return this.mCursorVisibleFromAttr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean canMarquee() {
        Layout layout;
        int compoundPaddingLeft = ((this.mRight - this.mLeft) - getCompoundPaddingLeft()) - getCompoundPaddingRight();
        if (compoundPaddingLeft > 0) {
            float f = compoundPaddingLeft;
            if (this.mLayout.getLineWidth(0) > f) {
                return true;
            }
            if (this.mMarqueeFadeMode != 0 && (layout = this.mSavedMarqueeModeLayout) != null && layout.getLineWidth(0) > f) {
                return true;
            }
        }
        return false;
    }

    protected void startMarquee() {
        if (getKeyListener() == null && !compressText((getWidth() - getCompoundPaddingLeft()) - getCompoundPaddingRight())) {
            Marquee marquee = this.mMarquee;
            if ((marquee == null || marquee.isStopped()) && isAggregatedVisible()) {
                if ((isFocused() || isSelected()) && getLineCount() == 1 && canMarquee()) {
                    if (this.mMarqueeFadeMode == 1) {
                        this.mMarqueeFadeMode = 2;
                        Layout layout = this.mLayout;
                        this.mLayout = this.mSavedMarqueeModeLayout;
                        this.mSavedMarqueeModeLayout = layout;
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
    }

    protected void stopMarquee() {
        Marquee marquee = this.mMarquee;
        if (marquee != null && !marquee.isStopped()) {
            this.mMarquee.stop();
        }
        if (this.mMarqueeFadeMode == 2) {
            this.mMarqueeFadeMode = 1;
            Layout layout = this.mSavedMarqueeModeLayout;
            this.mSavedMarqueeModeLayout = this.mLayout;
            this.mLayout = layout;
            setHorizontalFadingEdgeEnabled(false);
            requestLayout();
            invalidate();
        }
    }

    private void startStopMarquee(boolean z) {
        if (this.mEllipsize == TextUtils.TruncateAt.MARQUEE) {
            if (z) {
                startMarquee();
            } else {
                stopMarquee();
            }
        }
    }

    protected void onSelectionChanged(int i, int i2) {
        sendAccessibilityEvent(8192);
    }

    public void addTextChangedListener(TextWatcher textWatcher) {
        if (this.mListeners == null) {
            this.mListeners = new ArrayList<>();
        }
        this.mListeners.add(textWatcher);
    }

    public void removeTextChangedListener(TextWatcher textWatcher) {
        int iIndexOf;
        ArrayList<TextWatcher> arrayList = this.mListeners;
        if (arrayList == null || (iIndexOf = arrayList.indexOf(textWatcher)) < 0) {
            return;
        }
        this.mListeners.remove(iIndexOf);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendBeforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        ArrayList<TextWatcher> arrayList = this.mListeners;
        if (arrayList != null) {
            int size = arrayList.size();
            for (int i4 = 0; i4 < size; i4++) {
                arrayList.get(i4).beforeTextChanged(charSequence, i, i2, i3);
            }
        }
        int i5 = i2 + i;
        removeIntersectingNonAdjacentSpans(i, i5, SpellCheckSpan.class);
        removeIntersectingNonAdjacentSpans(i, i5, SuggestionSpan.class);
    }

    private <T> void removeIntersectingNonAdjacentSpans(int i, int i2, Class<T> cls) {
        CharSequence charSequence = this.mText;
        if (charSequence instanceof Editable) {
            Editable editable = (Editable) charSequence;
            Object[] spans = editable.getSpans(i, i2, cls);
            ArrayList arrayList = new ArrayList();
            for (Object obj : spans) {
                int spanStart = editable.getSpanStart(obj);
                if (editable.getSpanEnd(obj) != i && spanStart != i2) {
                    arrayList.add(obj);
                }
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                editable.removeSpan(it.next());
            }
        }
    }

    void removeAdjacentSuggestionSpans(int i) {
        CharSequence charSequence = this.mText;
        if (charSequence instanceof Editable) {
            Editable editable = (Editable) charSequence;
            SuggestionSpan[] suggestionSpanArr = (SuggestionSpan[]) editable.getSpans(i, i, SuggestionSpan.class);
            int length = suggestionSpanArr.length;
            for (int i2 = 0; i2 < length; i2++) {
                int spanStart = editable.getSpanStart(suggestionSpanArr[i2]);
                int spanEnd = editable.getSpanEnd(suggestionSpanArr[i2]);
                if ((spanEnd == i || spanStart == i) && SpellChecker.haveWordBoundariesChanged(editable, i, i, spanStart, spanEnd)) {
                    editable.removeSpan(suggestionSpanArr[i2]);
                }
            }
        }
    }

    void sendOnTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        ArrayList<TextWatcher> arrayList = this.mListeners;
        if (arrayList != null) {
            int size = arrayList.size();
            for (int i4 = 0; i4 < size; i4++) {
                arrayList.get(i4).onTextChanged(charSequence, i, i2, i3);
            }
        }
        if (ViewRune.WIDGET_PEN_SUPPORTED) {
            clearMultiSelection();
            if (this.mUseDisplayText && !this.mSkipUpdateDisplayText) {
                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(this.mTransformed);
                this.mDisplayText = spannableStringBuilder;
                SpannableStringBuilder spannableStringBuilder2 = spannableStringBuilder;
                if (this.mChangeWatcher == null) {
                    this.mChangeWatcher = new ChangeWatcher();
                }
                spannableStringBuilder2.setSpan(this.mChangeWatcher, 0, this.mDisplayText.length(), 6553618);
            }
            this.mSkipUpdateDisplayText = false;
        }
        Editor editor = this.mEditor;
        if (editor != null) {
            editor.sendOnTextChanged(i, i2, i3);
        }
    }

    void sendAfterTextChanged(Editable editable) {
        ArrayList<TextWatcher> arrayList = this.mListeners;
        if (arrayList != null) {
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                arrayList.get(i).afterTextChanged(editable);
            }
        }
        notifyListeningManagersAfterTextChanged();
        hideErrorIfUnchanged();
    }

    private void notifyListeningManagersAfterTextChanged() {
        AutofillManager autofillManager;
        if (isAutofillable() && (autofillManager = (AutofillManager) this.mContext.getSystemService(AutofillManager.class)) != null) {
            if (Helper.sVerbose) {
                Log.v(LOG_TAG, "notifyAutoFillManagerAfterTextChanged");
            }
            autofillManager.notifyValueChanged(this);
        }
        notifyContentCaptureTextChanged();
    }

    public void notifyContentCaptureTextChanged() {
        ContentCaptureManager contentCaptureManager;
        ContentCaptureSession contentCaptureSession;
        if (isLaidOut() && isImportantForContentCapture() && getNotifiedContentCaptureAppeared() && (contentCaptureManager = (ContentCaptureManager) this.mContext.getSystemService(ContentCaptureManager.class)) != null && contentCaptureManager.isContentCaptureEnabled() && (contentCaptureSession = getContentCaptureSession()) != null) {
            contentCaptureSession.notifyViewTextChanged(getAutofillId(), getText());
        }
    }

    private boolean isAutofillable() {
        return getAutofillType() != 0;
    }

    void updateAfterEdit() {
        invalidate();
        int selectionStart = getSelectionStart();
        if (selectionStart >= 0 || (this.mGravity & 112) == 80) {
            registerForPreDraw();
        }
        checkForResize();
        if (selectionStart >= 0) {
            this.mHighlightPathBogus = true;
            Editor editor = this.mEditor;
            if (editor != null) {
                editor.makeBlink();
            }
            bringPointIntoView(selectionStart);
        }
    }

    void handleTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        sLastCutCopyOrTextChangedTime = 0L;
        Editor editor = this.mEditor;
        Editor.InputMethodState inputMethodState = editor == null ? null : editor.mInputMethodState;
        if (inputMethodState == null || inputMethodState.mBatchEditNesting == 0) {
            updateAfterEdit();
        }
        if (inputMethodState != null) {
            inputMethodState.mContentChanged = true;
            if (inputMethodState.mChangedStart < 0) {
                inputMethodState.mChangedStart = i;
                inputMethodState.mChangedEnd = i + i2;
            } else {
                inputMethodState.mChangedStart = Math.min(inputMethodState.mChangedStart, i);
                inputMethodState.mChangedEnd = Math.max(inputMethodState.mChangedEnd, (i + i2) - inputMethodState.mChangedDelta);
            }
            inputMethodState.mChangedDelta += i3 - i2;
        }
        resetErrorChangedFlag();
        sendOnTextChanged(charSequence, i, i2, i3);
        onTextChanged(charSequence, i, i2, i3);
        this.mHideHint = false;
        clearGesturePreviewHighlight();
    }

    void spanChange(Spanned spanned, Object obj, int i, int i2, int i3, int i4) {
        int selectionEnd;
        boolean z;
        boolean z2;
        Editor editor = this.mEditor;
        Editor.InputMethodState inputMethodState = editor == null ? null : editor.mInputMethodState;
        int selectionStart = -1;
        if (ViewRune.WIDGET_PEN_SUPPORTED) {
            if (obj == MultiSelection.CURRENT_SELECTION_END) {
                if (i >= 0 || i2 >= 0) {
                    invalidateCursor(MultiSelection.getSelectionStart(spanned), i, i2);
                    checkForResize();
                    registerForPreDraw();
                    Editor editor2 = this.mEditor;
                    if (editor2 != null) {
                        editor2.makeBlink();
                    }
                }
                selectionEnd = i2;
                z2 = true;
            } else {
                z2 = false;
                selectionEnd = -1;
            }
            if (obj == MultiSelection.CURRENT_SELECTION_START) {
                if (i >= 0 || i2 >= 0) {
                    invalidateCursor(MultiSelection.getSelectionEnd(spanned), i, i2);
                }
                selectionStart = i2;
                z2 = true;
            }
            if (z2) {
                this.mHighlightPathBogus = true;
                if (this.mEditor != null && !isFocused()) {
                    this.mEditor.mSelectionMoved = true;
                }
            }
        } else {
            selectionEnd = -1;
        }
        if (obj == Selection.SELECTION_END) {
            if (i >= 0 || i2 >= 0) {
                invalidateCursor(Selection.getSelectionStart(spanned), i, i2);
                checkForResize();
                registerForPreDraw();
                Editor editor3 = this.mEditor;
                if (editor3 != null) {
                    editor3.makeBlink();
                }
            }
            selectionEnd = i2;
            z = true;
        } else {
            z = false;
        }
        if (obj == Selection.SELECTION_START) {
            if (i >= 0 || i2 >= 0) {
                invalidateCursor(Selection.getSelectionEnd(spanned), i, i2);
            }
            selectionStart = i2;
            z = true;
        }
        if (z) {
            clearGesturePreviewHighlight();
            this.mHighlightPathBogus = true;
            if (this.mEditor != null && !isFocused()) {
                this.mEditor.mSelectionMoved = true;
            }
            if ((spanned.getSpanFlags(obj) & 512) == 0) {
                if (selectionStart < 0) {
                    selectionStart = Selection.getSelectionStart(spanned);
                }
                if (selectionEnd < 0) {
                    selectionEnd = Selection.getSelectionEnd(spanned);
                }
                Editor editor4 = this.mEditor;
                if (editor4 != null) {
                    editor4.refreshTextActionMode();
                    if (!hasSelection() && this.mEditor.getTextActionMode() == null && hasTransientState()) {
                        setHasTransientState(false);
                    }
                }
                onSelectionChanged(selectionStart, selectionEnd);
            }
        }
        if ((obj instanceof UpdateAppearance) || (obj instanceof ParagraphStyle) || (obj instanceof CharacterStyle)) {
            if (inputMethodState == null || inputMethodState.mBatchEditNesting == 0) {
                invalidate();
                this.mHighlightPathBogus = true;
                checkForResize();
            } else {
                inputMethodState.mContentChanged = true;
            }
            Editor editor5 = this.mEditor;
            if (editor5 != null) {
                if (i >= 0) {
                    editor5.invalidateTextDisplayList(this.mLayout, i, i3);
                }
                if (i2 >= 0) {
                    this.mEditor.invalidateTextDisplayList(this.mLayout, i2, i4);
                }
                this.mEditor.invalidateHandlesAndActionMode();
            }
        }
        if (MetaKeyKeyListener.isMetaTracker(spanned, obj)) {
            this.mHighlightPathBogus = true;
            if (inputMethodState != null && MetaKeyKeyListener.isSelectingMetaTracker(spanned, obj)) {
                inputMethodState.mSelectionModeChanged = true;
            }
            if (Selection.getSelectionStart(spanned) >= 0) {
                if (inputMethodState == null || inputMethodState.mBatchEditNesting == 0) {
                    invalidateCursor();
                } else {
                    inputMethodState.mCursorChanged = true;
                }
            }
        }
        if ((obj instanceof ParcelableSpan) && inputMethodState != null && inputMethodState.mExtractedTextRequest != null) {
            if (inputMethodState.mBatchEditNesting != 0) {
                if (i >= 0) {
                    if (inputMethodState.mChangedStart > i) {
                        inputMethodState.mChangedStart = i;
                    }
                    if (inputMethodState.mChangedStart > i3) {
                        inputMethodState.mChangedStart = i3;
                    }
                }
                if (i2 >= 0) {
                    if (inputMethodState.mChangedStart > i2) {
                        inputMethodState.mChangedStart = i2;
                    }
                    if (inputMethodState.mChangedStart > i4) {
                        inputMethodState.mChangedStart = i4;
                    }
                }
            } else {
                inputMethodState.mContentChanged = true;
            }
        }
        Editor editor6 = this.mEditor;
        if (editor6 == null || editor6.mSpellChecker == null || i2 >= 0 || !(obj instanceof SpellCheckSpan)) {
            return;
        }
        this.mEditor.mSpellChecker.onSpellCheckSpanRemoved((SpellCheckSpan) obj);
    }

    @Override // android.view.View
    protected void onFocusChanged(boolean z, int i, Rect rect) throws Resources.NotFoundException {
        TextView textView;
        boolean z2;
        int i2;
        Rect rect2;
        Spannable spannable;
        if (isTemporarilyDetached()) {
            super.onFocusChanged(z, i, rect);
            return;
        }
        this.mHideHint = false;
        Editor editor = this.mEditor;
        if (editor != null) {
            editor.onFocusChanged(z, i);
        }
        if (z && (spannable = this.mSpannable) != null) {
            MetaKeyKeyListener.resetMetaState(spannable);
        }
        startStopMarquee(z);
        TransformationMethod transformationMethod = this.mTransformation;
        if (transformationMethod != null) {
            textView = this;
            z2 = z;
            i2 = i;
            rect2 = rect;
            transformationMethod.onFocusChanged(textView, this.mText, z2, i2, rect2);
        } else {
            textView = this;
            z2 = z;
            i2 = i;
            rect2 = rect;
        }
        super.onFocusChanged(z2, i2, rect2);
    }

    @Override // android.view.View
    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        Editor editor = this.mEditor;
        if (editor != null) {
            editor.onWindowFocusChanged(z);
        }
        startStopMarquee(z);
    }

    @Override // android.view.View
    protected void onVisibilityChanged(View view, int i) {
        super.onVisibilityChanged(view, i);
        Editor editor = this.mEditor;
        if (editor != null && i != 0) {
            editor.hideCursorAndSpanControllers();
            stopTextActionMode();
        }
        if (!ViewRune.WIDGET_PEN_SUPPORTED || i == 0) {
            return;
        }
        clearMultiSelection();
    }

    @Override // android.view.View
    public void onVisibilityAggregated(boolean z) {
        super.onVisibilityAggregated(z);
        startStopMarquee(z);
    }

    public void clearComposingText() {
        if (this.mText instanceof Spannable) {
            BaseInputConnection.removeComposingSpans(this.mSpannable);
        }
    }

    @Override // android.view.View
    public void setSelected(boolean z) {
        boolean zIsSelected = isSelected();
        super.setSelected(z);
        if (z == zIsSelected || this.mEllipsize != TextUtils.TruncateAt.MARQUEE) {
            return;
        }
        if (z) {
            startMarquee();
        } else {
            stopMarquee();
        }
    }

    boolean isFromPrimePointer(MotionEvent motionEvent, boolean z) {
        int i = this.mPrimePointerId;
        boolean z2 = false;
        if (i == -1) {
            this.mPrimePointerId = motionEvent.getPointerId(0);
            this.mIsPrimePointerFromHandleView = z;
        } else {
            if (i == motionEvent.getPointerId(0) || (this.mIsPrimePointerFromHandleView && z)) {
            }
            if (motionEvent.getActionMasked() == 1 && motionEvent.getActionMasked() != 3) {
                return z2;
            }
            this.mPrimePointerId = -1;
            return z2;
        }
        z2 = true;
        if (motionEvent.getActionMasked() == 1) {
        }
        this.mPrimePointerId = -1;
        return z2;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean zOnTouchEvent;
        Editor editor;
        Editor editor2;
        InputMethodManager inputMethodManager;
        this.mLastInputSource = motionEvent.getSource();
        int actionMasked = motionEvent.getActionMasked();
        if (this.mEditor != null) {
            if (!isFromPrimePointer(motionEvent, false)) {
                return true;
            }
            this.mEditor.onTouchEvent(motionEvent);
            if (this.mEditor.mInsertionPointCursorController != null && this.mEditor.mInsertionPointCursorController.isCursorBeingModified()) {
                return true;
            }
            if (this.mEditor.mSelectionModifierCursorController != null && this.mEditor.mSelectionModifierCursorController.isDragAcceleratorActive()) {
                return true;
            }
        }
        boolean zOnTouchEvent2 = super.onTouchEvent(motionEvent);
        Editor editor3 = this.mEditor;
        if (editor3 != null && editor3.mDiscardNextActionUp && actionMasked == 1) {
            this.mEditor.mDiscardNextActionUp = false;
            if (this.mEditor.mIsInsertionActionModeStartPending) {
                this.mEditor.startInsertionActionMode();
                this.mEditor.mIsInsertionActionModeStartPending = false;
                return zOnTouchEvent2;
            }
        } else {
            if (Flags.handwritingEndOfLineTap() && actionMasked == 1 && shouldStartHandwritingForEndOfLineTap(motionEvent) && (inputMethodManager = getInputMethodManager()) != null) {
                inputMethodManager.startStylusHandwriting(this);
                return true;
            }
            boolean z = actionMasked == 1 && ((editor2 = this.mEditor) == null || !editor2.mIgnoreActionUpEvent) && isFocused() && (motionEvent.getToolType(0) != 2 || (motionEvent.getButtonState() & 32) == 0);
            if ((this.mMovement != null || onCheckIsTextEditor()) && isEnabled() && (this.mText instanceof Spannable) && this.mLayout != null) {
                MovementMethod movementMethod = this.mMovement;
                if (movementMethod != null) {
                    zOnTouchEvent = movementMethod.onTouchEvent(this, this.mSpannable, motionEvent);
                    if ((MetaKeyKeyListener.getMetaState((Spannable) this.mText, 1) == 1) && actionMasked == 1 && zOnTouchEvent && (editor = this.mEditor) != null) {
                        editor.startSelectionActionModeAsync(false);
                    }
                } else {
                    zOnTouchEvent = false;
                }
                boolean zIsTextSelectable = isTextSelectable();
                if (z && this.mLinksClickable && this.mAutoLinkMask != 0 && zIsTextSelectable) {
                    ClickableSpan[] clickableSpanArr = (ClickableSpan[]) this.mSpannable.getSpans(getSelectionStart(), getSelectionEnd(), ClickableSpan.class);
                    if (clickableSpanArr.length > 0) {
                        clickableSpanArr[0].onClick(this);
                        zOnTouchEvent = true;
                    }
                }
                if (z && (isTextEditable() || zIsTextSelectable)) {
                    InputMethodManager inputMethodManager2 = getInputMethodManager();
                    viewClicked(inputMethodManager2);
                    if (isTextEditable() && this.mEditor.mShowSoftInputOnFocus && inputMethodManager2 != null && !showAutofillDialog()) {
                        inputMethodManager2.showSoftInput(this, 0);
                    }
                    this.mEditor.onTouchUpEvent(motionEvent);
                    zOnTouchEvent = true;
                }
                if (zOnTouchEvent) {
                    return true;
                }
            }
        }
        return zOnTouchEvent2;
    }

    private boolean shouldStartHandwritingForEndOfLineTap(MotionEvent motionEvent) {
        int selectionStart;
        if (!onCheckIsTextEditor() || !isEnabled() || !isAutoHandwritingEnabled() || TextUtils.isEmpty(this.mText) || didTouchFocusSelect() || this.mLayout == null || !motionEvent.isStylusPointer() || (selectionStart = getSelectionStart()) < 0 || getSelectionEnd() != selectionStart) {
            return false;
        }
        int lineForOffset = this.mLayout.getLineForOffset(selectionStart);
        int lineEnd = this.mLayout.getLineEnd(lineForOffset);
        if (lineForOffset != this.mLayout.getLineCount() - 1) {
            lineEnd--;
        }
        if (lineEnd != selectionStart || getLineAtCoordinate(motionEvent.getY()) != lineForOffset) {
            return false;
        }
        float fConvertToLocalHorizontalCoordinate = convertToLocalHorizontalCoordinate(motionEvent.getX());
        if (this.mLayout.getParagraphDirection(lineForOffset) != -1 ? fConvertToLocalHorizontalCoordinate > this.mLayout.getLineRight(lineForOffset) : fConvertToLocalHorizontalCoordinate < this.mLayout.getLineLeft(lineForOffset)) {
            return isStylusHandwritingAvailable();
        }
        return false;
    }

    public final boolean showUIForTouchScreen() {
        return (this.mLastInputSource & 4098) == 4098;
    }

    private boolean showAutofillDialog() {
        AutofillManager autofillManager = (AutofillManager) this.mContext.getSystemService(AutofillManager.class);
        if (autofillManager != null) {
            return autofillManager.showAutofillDialog(this);
        }
        return false;
    }

    @Override // android.view.View
    public boolean onGenericMotionEvent(MotionEvent motionEvent) {
        MovementMethod movementMethod = this.mMovement;
        if (movementMethod != null && (this.mText instanceof Spannable) && this.mLayout != null) {
            try {
                if (movementMethod.onGenericMotionEvent(this, this.mSpannable, motionEvent)) {
                    return true;
                }
            } catch (AbstractMethodError unused) {
            }
        }
        return super.onGenericMotionEvent(motionEvent);
    }

    @Override // android.view.View
    protected void onCreateContextMenu(ContextMenu contextMenu) {
        Editor editor = this.mEditor;
        if (editor != null) {
            editor.onCreateContextMenu(contextMenu);
        }
    }

    @Override // android.view.View
    public boolean showContextMenu() {
        Editor editor = this.mEditor;
        if (editor != null) {
            editor.setContextMenuAnchor(Float.NaN, Float.NaN);
        }
        return super.showContextMenu();
    }

    @Override // android.view.View
    public boolean showContextMenu(float f, float f2) {
        Editor editor = this.mEditor;
        if (editor != null) {
            editor.setContextMenuAnchor(f, f2);
        }
        return super.showContextMenu(f, f2);
    }

    boolean isTextEditable() {
        return (this.mText instanceof Editable) && onCheckIsTextEditor() && isEnabled();
    }

    boolean isTextAutofillable() {
        return (this.mText instanceof Editable) && onCheckIsTextEditor();
    }

    public boolean didTouchFocusSelect() {
        Editor editor = this.mEditor;
        return editor != null && editor.mTouchFocusSelected;
    }

    @Override // android.view.View
    public void cancelLongPress() {
        super.cancelLongPress();
        Editor editor = this.mEditor;
        if (editor != null) {
            editor.mIgnoreActionUpEvent = true;
        }
    }

    @Override // android.view.View
    public boolean onTrackballEvent(MotionEvent motionEvent) {
        Spannable spannable;
        MovementMethod movementMethod = this.mMovement;
        if (movementMethod == null || (spannable = this.mSpannable) == null || this.mLayout == null || !movementMethod.onTrackballEvent(this, spannable, motionEvent)) {
            return super.onTrackballEvent(motionEvent);
        }
        return true;
    }

    public void setScroller(Scroller scroller) {
        this.mScroller = scroller;
    }

    @Override // android.view.View
    protected float getLeftFadingEdgeStrength() {
        Marquee marquee;
        if (isMarqueeFadeEnabled() && (marquee = this.mMarquee) != null && !marquee.isStopped()) {
            Marquee marquee2 = this.mMarquee;
            if (marquee2.shouldDrawLeftFade()) {
                return getHorizontalFadingEdgeStrength(marquee2.getScroll(), 0.0f);
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
        Marquee marquee;
        if (isMarqueeFadeEnabled() && (marquee = this.mMarquee) != null && !marquee.isStopped()) {
            Marquee marquee2 = this.mMarquee;
            return getHorizontalFadingEdgeStrength(marquee2.getMaxFadeScroll(), marquee2.getScroll());
        }
        if (getLineCount() == 1) {
            float width = this.mScrollX + ((getWidth() - getCompoundPaddingLeft()) - getCompoundPaddingRight());
            float lineRight = getLayout().getLineRight(0);
            if (lineRight < width) {
                return 0.0f;
            }
            return getHorizontalFadingEdgeStrength(width, lineRight);
        }
        return super.getRightFadingEdgeStrength();
    }

    private float getHorizontalFadingEdgeStrength(float f, float f2) {
        int horizontalFadingEdgeLength = getHorizontalFadingEdgeLength();
        if (horizontalFadingEdgeLength == 0) {
            return 0.0f;
        }
        float fAbs = Math.abs(f - f2);
        float f3 = horizontalFadingEdgeLength;
        if (fAbs > f3) {
            return 1.0f;
        }
        return fAbs / f3;
    }

    private boolean isMarqueeFadeEnabled() {
        return this.mEllipsize == TextUtils.TruncateAt.MARQUEE && this.mMarqueeFadeMode != 1;
    }

    @Override // android.view.View
    protected int computeHorizontalScrollRange() {
        Layout layout = this.mLayout;
        if (layout != null) {
            return (this.mSingleLine && (this.mGravity & 7) == 3) ? (int) layout.getLineWidth(0) : layout.getWidth();
        }
        return super.computeHorizontalScrollRange();
    }

    @Override // android.view.View
    protected int computeVerticalScrollRange() {
        Layout layout = this.mLayout;
        if (layout != null) {
            return layout.getHeight();
        }
        return super.computeVerticalScrollRange();
    }

    @Override // android.view.View
    protected int computeVerticalScrollExtent() {
        return (getHeight() - getCompoundPaddingTop()) - getCompoundPaddingBottom();
    }

    @Override // android.view.View
    public void findViewsWithText(ArrayList<View> arrayList, CharSequence charSequence, int i) {
        super.findViewsWithText(arrayList, charSequence, i);
        if (arrayList.contains(this) || (i & 1) == 0 || TextUtils.isEmpty(charSequence) || TextUtils.isEmpty(this.mText)) {
            return;
        }
        if (this.mText.toString().toLowerCase().contains(charSequence.toString().toLowerCase())) {
            arrayList.add(this);
        }
    }

    public static ColorStateList getTextColors(Context context, TypedArray typedArray) throws Resources.NotFoundException {
        int resourceId;
        typedArray.getClass();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(android.R.styleable.TextView);
        ColorStateList colorStateList = typedArrayObtainStyledAttributes.getColorStateList(5);
        if (colorStateList == null && (resourceId = typedArrayObtainStyledAttributes.getResourceId(1, 0)) != 0) {
            TypedArray typedArrayObtainStyledAttributes2 = context.obtainStyledAttributes(resourceId, android.R.styleable.TextAppearance);
            colorStateList = typedArrayObtainStyledAttributes2.getColorStateList(3);
            typedArrayObtainStyledAttributes2.recycle();
        }
        typedArrayObtainStyledAttributes.recycle();
        return colorStateList;
    }

    public static int getTextColor(Context context, TypedArray typedArray, int i) throws Resources.NotFoundException {
        ColorStateList textColors = getTextColors(context, typedArray);
        return textColors == null ? i : textColors.getDefaultColor();
    }

    @Override // android.view.View
    public boolean onKeyShortcut(int i, KeyEvent keyEvent) {
        if (keyEvent.hasModifiers(4096)) {
            if (i != 29) {
                if (i != 50) {
                    if (i == 124 || i == 31) {
                        if (canCopy()) {
                            return onTextContextMenuItem(16908321);
                        }
                    } else if (i != 32) {
                        switch (i) {
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
                    } else if (canDelete()) {
                        return onTextContextMenuItem(16909029);
                    }
                } else if (canPaste()) {
                    return onTextContextMenuItem(16908322);
                }
            } else if (canSelectText()) {
                return onTextContextMenuItem(16908319);
            }
        } else if (keyEvent.hasModifiers(4097)) {
            if (i != 50) {
                if (i == 54 && canRedo()) {
                    return onTextContextMenuItem(16908339);
                }
            } else if (canPaste()) {
                return onTextContextMenuItem(16908337);
            }
        } else if (keyEvent.hasModifiers(1) && i == 124 && canPaste()) {
            return onTextContextMenuItem(16908322);
        }
        return super.onKeyShortcut(i, keyEvent);
    }

    boolean canSelectText() {
        Editor editor;
        return (this.mText.length() == 0 || (editor = this.mEditor) == null || !editor.hasSelectionController()) ? false : true;
    }

    boolean textCanBeSelected() {
        MovementMethod movementMethod = this.mMovement;
        if (movementMethod == null || !movementMethod.canSelectArbitrarily()) {
            return false;
        }
        if (isTextEditable()) {
            return true;
        }
        return isTextSelectable() && (this.mText instanceof Spannable) && isEnabled();
    }

    private Locale getTextServicesLocale(boolean z) {
        updateTextServicesLocaleAsync();
        return (this.mCurrentSpellCheckerLocaleCache != null || z) ? this.mCurrentSpellCheckerLocaleCache : Locale.getDefault();
    }

    public final void setTextOperationUser(UserHandle userHandle) {
        if (Objects.equals(this.mTextOperationUser, userHandle)) {
            return;
        }
        if (userHandle != null && !Process.myUserHandle().equals(userHandle) && getContext().checkSelfPermission(Manifest.permission.INTERACT_ACROSS_USERS_FULL) != 0) {
            throw new SecurityException("INTERACT_ACROSS_USERS_FULL is required. userId=" + userHandle.getIdentifier() + " callingUserId" + UserHandle.myUserId());
        }
        this.mTextOperationUser = userHandle;
        this.mCurrentSpellCheckerLocaleCache = null;
        Editor editor = this.mEditor;
        if (editor != null) {
            editor.onTextOperationUserChanged();
        }
    }

    @Override // android.view.View
    public boolean isAutoHandwritingEnabled() {
        return super.isAutoHandwritingEnabled() && !isAnyPasswordInputType();
    }

    @Override // android.view.View
    public boolean shouldTrackHandwritingArea() {
        if (super.shouldTrackHandwritingArea()) {
            return true;
        }
        return Flags.handwritingUnsupportedMessage() && onCheckIsTextEditor();
    }

    @Override // android.view.View
    public boolean isStylusHandwritingAvailable() {
        if (this.mTextOperationUser == null) {
            return super.isStylusHandwritingAvailable();
        }
        return getInputMethodManager().isStylusHandwritingAvailableAsUser(this.mTextOperationUser);
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
        } catch (PackageManager.NameNotFoundException unused) {
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

    private boolean isAutoSizeEnabled() {
        return supportsAutoSizeText() && this.mAutoSizeTextType != 0;
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
        TextServicesManager textServicesManagerForUser = getTextServicesManagerForUser();
        if (textServicesManagerForUser == null) {
            return;
        }
        SpellCheckerSubtype currentSpellCheckerSubtype = textServicesManagerForUser.getCurrentSpellCheckerSubtype(true);
        this.mCurrentSpellCheckerLocaleCache = currentSpellCheckerSubtype != null ? currentSpellCheckerSubtype.getLocaleObject() : null;
    }

    void onLocaleChanged() {
        this.mEditor.onLocaleChanged();
    }

    public WordIterator getWordIterator() {
        Editor editor = this.mEditor;
        if (editor != null) {
            return editor.getWordIterator();
        }
        return null;
    }

    @Override // android.view.View
    public void onPopulateAccessibilityEventInternal(AccessibilityEvent accessibilityEvent) {
        super.onPopulateAccessibilityEventInternal(accessibilityEvent);
        if (!isAccessibilityDataSensitive() || accessibilityEvent.isAccessibilityDataSensitive()) {
            CharSequence textForAccessibility = getTextForAccessibility();
            if (TextUtils.isEmpty(textForAccessibility)) {
                return;
            }
            if (hasPasswordTransformationMethod() && this.mText.length() > 0) {
                String string = textForAccessibility.subSequence(0, textForAccessibility.length() - 1).toString();
                StringBuilder sb = new StringBuilder();
                sb.append(string);
                sb.append(this.mText.charAt(r3.length() - 1));
                accessibilityEvent.getText().add(sb.toString());
                return;
            }
            accessibilityEvent.getText().add(textForAccessibility);
        }
    }

    @Override // android.view.View
    public CharSequence getAccessibilityClassName() {
        return TextView.class.getName();
    }

    @Override // android.view.View
    protected void onProvideStructure(ViewStructure viewStructure, int i, int i2) {
        Editor editor;
        int lineAtCoordinateUnclamped;
        int lineAtCoordinateUnclamped2;
        int max;
        super.onProvideStructure(viewStructure, i, i2);
        int i3 = 0;
        boolean z = hasPasswordTransformationMethod() || isPasswordInputType(getInputType());
        if (i == 1 || i == 2) {
            if (i == 1) {
                viewStructure.setDataIsSensitive(!this.mTextSetFromXmlOrResourceId);
            }
            if (this.mTextId != 0) {
                try {
                    viewStructure.setTextIdEntry(getResources().getResourceEntryName(this.mTextId));
                } catch (Resources.NotFoundException e) {
                    if (Helper.sVerbose) {
                        Log.v(LOG_TAG, "onProvideAutofillStructure(): cannot set name for text id " + this.mTextId + ": " + e.getMessage());
                    }
                }
            }
            String[] receiveContentMimeTypes = getReceiveContentMimeTypes();
            if (receiveContentMimeTypes == null && (editor = this.mEditor) != null) {
                receiveContentMimeTypes = editor.getDefaultOnReceiveContentListener().getFallbackMimeTypesForAutofill(this);
            }
            viewStructure.setReceiveContentMimeTypes(receiveContentMimeTypes);
        }
        if (!z || i == 1 || i == 2) {
            if (this.mLayout == null) {
                if (i == 2) {
                    Log.w(LOG_TAG, "onProvideContentCaptureStructure(): calling assumeLayout()");
                }
                assumeLayout();
            }
            Layout layout = this.mLayout;
            int lineCount = layout.getLineCount();
            if (lineCount <= 1) {
                CharSequence text = getText();
                if (i == 1) {
                    viewStructure.setText(text);
                } else {
                    viewStructure.setText(text, getSelectionStart(), getSelectionEnd());
                }
            } else {
                int[] iArr = new int[2];
                getLocationInWindow(iArr);
                int i4 = iArr[1];
                ViewParent parent = getParent();
                View view = this;
                while (parent instanceof View) {
                    view = (View) parent;
                    parent = view.getParent();
                }
                int height = view.getHeight();
                if (i4 >= 0) {
                    lineAtCoordinateUnclamped2 = getLineAtCoordinateUnclamped(0.0f);
                    lineAtCoordinateUnclamped = getLineAtCoordinateUnclamped(height - 1);
                } else {
                    int lineAtCoordinateUnclamped3 = getLineAtCoordinateUnclamped(-i4);
                    lineAtCoordinateUnclamped = getLineAtCoordinateUnclamped((height - 1) - i4);
                    lineAtCoordinateUnclamped2 = lineAtCoordinateUnclamped3;
                }
                int i5 = (lineAtCoordinateUnclamped - lineAtCoordinateUnclamped2) / 2;
                int i6 = lineAtCoordinateUnclamped2 - i5;
                if (i6 < 0) {
                    i6 = 0;
                }
                int i7 = lineAtCoordinateUnclamped + i5;
                if (i7 >= lineCount) {
                    i7 = lineCount - 1;
                }
                int iTransformedToOriginal = transformedToOriginal(layout.getLineStart(i6), 0);
                int iTransformedToOriginal2 = transformedToOriginal(layout.getLineEnd(i7), 0);
                int selectionStart = getSelectionStart();
                int selectionEnd = getSelectionEnd();
                if (selectionStart < selectionEnd) {
                    if (selectionStart < iTransformedToOriginal) {
                        iTransformedToOriginal = selectionStart;
                    }
                    if (selectionEnd > iTransformedToOriginal2) {
                        iTransformedToOriginal2 = selectionEnd;
                    }
                }
                CharSequence text2 = getText();
                if (text2 != null) {
                    if (iTransformedToOriginal > 0 || iTransformedToOriginal2 < text2.length()) {
                        text2 = text2.subSequence(Math.min(iTransformedToOriginal, text2.length()), Math.min(iTransformedToOriginal2, text2.length()));
                    }
                    if (i == 1) {
                        viewStructure.setText(text2);
                    } else {
                        viewStructure.setText(getText());
                    }
                }
            }
            if (i == 0 || i == 2) {
                int typefaceStyle = getTypefaceStyle();
                int i8 = (typefaceStyle & 1) != 0 ? 1 : 0;
                if ((typefaceStyle & 2) != 0) {
                    i8 |= 2;
                }
                int flags = this.mTextPaint.getFlags();
                if ((flags & 32) != 0) {
                    i8 |= 1;
                }
                if ((flags & 8) != 0) {
                    i8 |= 4;
                }
                if ((flags & 16) != 0) {
                    i8 |= 8;
                }
                viewStructure.setTextStyle(getTextSize(), getCurrentTextColor(), 1, i8);
            }
            if (i == 1 || i == 2) {
                viewStructure.setMinTextEms(getMinEms());
                viewStructure.setMaxTextEms(getMaxEms());
                InputFilter[] filters = getFilters();
                int length = filters.length;
                while (true) {
                    if (i3 >= length) {
                        max = -1;
                        break;
                    }
                    InputFilter inputFilter = filters[i3];
                    if (inputFilter instanceof InputFilter.LengthFilter) {
                        max = ((InputFilter.LengthFilter) inputFilter).getMax();
                        break;
                    }
                    i3++;
                }
                viewStructure.setMaxTextLength(max);
            }
        }
        if (this.mHintId != 0) {
            try {
                viewStructure.setHintIdEntry(getResources().getResourceEntryName(this.mHintId));
            } catch (Resources.NotFoundException e2) {
                if (Helper.sVerbose) {
                    Log.v(LOG_TAG, "onProvideAutofillStructure(): cannot set name for hint id " + this.mHintId + ": " + e2.getMessage());
                }
            }
        }
        viewStructure.setHint(getHint());
        viewStructure.setInputType(getInputType());
    }

    public boolean canRequestAutofill() {
        AutofillManager autofillManager;
        if (isFlipCoverClosed() || this.mIsThemeDeviceDefault || (this.mActionModeFlags & 131072) != 131072 || !isAutofillable() || (autofillManager = (AutofillManager) this.mContext.getSystemService(AutofillManager.class)) == null) {
            return false;
        }
        return autofillManager.isEnabled();
    }

    private void requestAutofill() {
        AutofillManager autofillManager = (AutofillManager) this.mContext.getSystemService(AutofillManager.class);
        if (autofillManager != null) {
            autofillManager.requestAutofill(this);
        }
    }

    @Override // android.view.View
    public void autofill(AutofillValue autofillValue) {
        if (Helper.sVerbose) {
            Log.v(LOG_TAG, "autofill() called on textview for id:" + getAutofillId());
        }
        if (!isTextAutofillable()) {
            Log.w(LOG_TAG, "cannot autofill non-editable TextView: " + this);
        } else {
            if (!autofillValue.isText()) {
                Log.w(LOG_TAG, "value of type " + autofillValue.describeContents() + " cannot be autofilled into " + this);
                return;
            }
            performReceiveContent(new ContentInfo.Builder(ClipData.newPlainText("", autofillValue.getTextValue()), 4).build());
        }
    }

    @Override // android.view.View
    public int getAutofillType() {
        return isTextAutofillable() ? 1 : 0;
    }

    @Override // android.view.View
    public AutofillValue getAutofillValue() {
        if (isTextAutofillable()) {
            return AutofillValue.forText(TextUtils.trimToParcelableSize(getText()));
        }
        return null;
    }

    @Override // android.view.View
    public void onInitializeAccessibilityEventInternal(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEventInternal(accessibilityEvent);
        accessibilityEvent.setPassword(hasPasswordTransformationMethod());
        if (accessibilityEvent.getEventType() == 8192) {
            accessibilityEvent.setFromIndex(Selection.getSelectionStart(this.mText));
            accessibilityEvent.setToIndex(Selection.getSelectionEnd(this.mText));
            accessibilityEvent.setItemCount(this.mText.length());
        }
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo accessibilityNodeInfo) throws Resources.NotFoundException {
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        accessibilityNodeInfo.setPassword(hasPasswordTransformationMethod());
        accessibilityNodeInfo.setText(getTextForAccessibility());
        accessibilityNodeInfo.setHintText(this.mHint);
        accessibilityNodeInfo.setShowingHintText(isShowingHint());
        if (this.mBufferType == BufferType.EDITABLE) {
            accessibilityNodeInfo.setEditable(true);
            if (isEnabled()) {
                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SET_TEXT);
            }
        }
        Editor editor = this.mEditor;
        if (editor != null) {
            accessibilityNodeInfo.setInputType(editor.mInputType);
            if (this.mEditor.mError != null) {
                accessibilityNodeInfo.setContentInvalid(true);
                accessibilityNodeInfo.setError(this.mEditor.mError);
            }
            if (isTextEditable() && isFocused()) {
                CharSequence string = this.mContext.getResources().getString(R.string.keyboardview_keycode_enter);
                if (getImeActionLabel() != null) {
                    string = getImeActionLabel();
                }
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(16908372, string));
            }
        }
        if (!TextUtils.isEmpty(this.mText)) {
            accessibilityNodeInfo.addAction(256);
            accessibilityNodeInfo.addAction(512);
            accessibilityNodeInfo.setMovementGranularities(31);
            accessibilityNodeInfo.addAction(131072);
            if (android.view.accessibility.Flags.a11yCharacterInWindowApi()) {
                accessibilityNodeInfo.setAvailableExtraData(ACCESSIBILITY_EXTRA_DATA_KEYS_FLAGGED);
            } else {
                accessibilityNodeInfo.setAvailableExtraData(ACCESSIBILITY_EXTRA_DATA_KEYS);
            }
            accessibilityNodeInfo.setTextSelectable(isTextSelectable() || isTextEditable());
        } else {
            accessibilityNodeInfo.setAvailableExtraData(Arrays.asList(AccessibilityNodeInfo.EXTRA_DATA_RENDERING_INFO_KEY));
        }
        if (isFocused()) {
            if (canCopy()) {
                accessibilityNodeInfo.addAction(16384);
            }
            if (canPaste()) {
                accessibilityNodeInfo.addAction(32768);
            }
            if (canCut()) {
                accessibilityNodeInfo.addAction(65536);
            }
            if (canReplace()) {
                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SHOW_TEXT_SUGGESTIONS);
            }
            if (canShare()) {
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(268435456, getResources().getString(R.string.share)));
            }
            if (canProcessText()) {
                this.mEditor.mProcessTextIntentActionsHandler.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
                this.mEditor.onInitializeSmartActionsAccessibilityNodeInfo(accessibilityNodeInfo);
            }
        }
        int length = this.mFilters.length;
        for (int i = 0; i < length; i++) {
            InputFilter inputFilter = this.mFilters[i];
            if (inputFilter instanceof InputFilter.LengthFilter) {
                accessibilityNodeInfo.setMaxTextLength(((InputFilter.LengthFilter) inputFilter).getMax());
            }
        }
        if (!isSingleLine()) {
            accessibilityNodeInfo.setMultiLine(true);
        }
        if (accessibilityNodeInfo.isClickable() || accessibilityNodeInfo.isLongClickable()) {
            if ((this.mMovement instanceof LinkMovementMethod) || (isTextSelectable() && !isTextEditable())) {
                if (!hasOnClickListeners()) {
                    accessibilityNodeInfo.setClickable(false);
                    accessibilityNodeInfo.removeAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK);
                }
                if (hasOnLongClickListeners()) {
                    return;
                }
                accessibilityNodeInfo.setLongClickable(false);
                accessibilityNodeInfo.removeAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_LONG_CLICK);
            }
        }
    }

    @Override // android.view.View
    public void addExtraDataToAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo, String str, Bundle bundle) {
        RectF characterBounds;
        boolean zEquals = str.equals(AccessibilityNodeInfo.EXTRA_DATA_TEXT_CHARACTER_LOCATION_KEY);
        boolean z = android.view.accessibility.Flags.a11yCharacterInWindowApi() && str.equals(AccessibilityNodeInfo.EXTRA_DATA_TEXT_CHARACTER_LOCATION_IN_WINDOW_KEY);
        if (bundle != null && (zEquals || z)) {
            int i = bundle.getInt(AccessibilityNodeInfo.EXTRA_DATA_TEXT_CHARACTER_LOCATION_ARG_START_INDEX, -1);
            int i2 = bundle.getInt(AccessibilityNodeInfo.EXTRA_DATA_TEXT_CHARACTER_LOCATION_ARG_LENGTH, -1);
            if (i2 <= 0 || i < 0 || i >= this.mText.length()) {
                Log.e(LOG_TAG, "Invalid arguments for accessibility character locations");
                return;
            }
            RectF[] rectFArr = new RectF[i2];
            CursorAnchorInfo.Builder builder = new CursorAnchorInfo.Builder();
            populateCharacterBounds(builder, i, Math.min(i + i2, length()), viewportToContentHorizontalOffset(), viewportToContentVerticalOffset());
            CursorAnchorInfo cursorAnchorInfoBuild = builder.setMatrix(null).build();
            for (int i3 = 0; i3 < i2; i3++) {
                int i4 = i + i3;
                if ((cursorAnchorInfoBuild.getCharacterBoundsFlags(i4) & 1) == 1 && (characterBounds = cursorAnchorInfoBuild.getCharacterBounds(i4)) != null) {
                    if (zEquals) {
                        mapRectFromViewToScreenCoords(characterBounds, true);
                    } else if (z) {
                        mapRectFromViewToWindowCoords(characterBounds, true);
                    }
                    rectFArr[i3] = characterBounds;
                }
            }
            accessibilityNodeInfo.getExtras().putParcelableArray(str, rectFArr);
            return;
        }
        if (str.equals(AccessibilityNodeInfo.EXTRA_DATA_RENDERING_INFO_KEY)) {
            AccessibilityNodeInfo.ExtraRenderingInfo extraRenderingInfoObtain = AccessibilityNodeInfo.ExtraRenderingInfo.obtain();
            extraRenderingInfoObtain.setLayoutSize(getLayoutParams().width, getLayoutParams().height);
            extraRenderingInfoObtain.setTextSizeInPx(getTextSize());
            extraRenderingInfoObtain.setTextSizeUnit(getTextSizeUnit());
            accessibilityNodeInfo.setExtraRenderingInfo(extraRenderingInfoObtain);
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
        if (getViewVisibleRect(rect)) {
            return rect.intersect(getCompoundPaddingLeft(), getCompoundPaddingTop(), getWidth() - getCompoundPaddingRight(), getHeight() - getCompoundPaddingBottom());
        }
        return false;
    }

    private boolean getEditorAndHandwritingBounds(RectF rectF, RectF rectF2) {
        if (this.mTempRect == null) {
            this.mTempRect = new Rect();
        }
        Rect rect = this.mTempRect;
        if (!getGlobalVisibleRect(rect)) {
            return false;
        }
        if (this.mTempMatrix == null) {
            this.mTempMatrix = new Matrix();
        }
        Matrix matrix = this.mTempMatrix;
        matrix.reset();
        transformMatrixRootToLocal(matrix);
        rectF.set(rect);
        matrix.mapRect(rectF);
        if (rectF2 == null) {
            return true;
        }
        rectF2.top = rect.top - getHandwritingBoundsOffsetTop();
        rectF2.left = rect.left - getHandwritingBoundsOffsetLeft();
        rectF2.bottom = rect.bottom + getHandwritingBoundsOffsetBottom();
        rectF2.right = rect.right + getHandwritingBoundsOffsetRight();
        matrix.mapRect(rectF2);
        return true;
    }

    private boolean getContentVisibleRect(RectF rectF) {
        if (getEditorAndHandwritingBounds(rectF, null)) {
            return rectF.intersect(getCompoundPaddingLeft(), getCompoundPaddingTop(), getWidth() - getCompoundPaddingRight(), getHeight() - getCompoundPaddingBottom());
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v11 */
    /* JADX WARN: Type inference failed for: r1v12 */
    /* JADX WARN: Type inference failed for: r1v5 */
    /* JADX WARN: Type inference failed for: r1v6 */
    /* JADX WARN: Type inference failed for: r1v7 */
    public void populateCharacterBounds(CursorAnchorInfo.Builder builder, int i, int i2, float f, float f2) {
        if (isOffsetMappingAvailable()) {
            return;
        }
        RectF rectF = new RectF();
        if (Flags.handwritingGestureWithTransformation()) {
            getContentVisibleRect(rectF);
        } else {
            Rect rect = new Rect();
            getContentVisibleRect(rect);
            rectF.set(rect);
        }
        float[] characterBounds = getCharacterBounds(i, i2, f, f2);
        int i3 = i2 - i;
        for (int i4 = 0; i4 < i3; i4++) {
            int i5 = i4 * 4;
            float f3 = characterBounds[i5];
            float f4 = characterBounds[i5 + 1];
            float f5 = characterBounds[i5 + 2];
            float f6 = characterBounds[i5 + 3];
            boolean zIntersects = rectF.intersects(f3, f4, f5, f6);
            int i6 = zIntersects;
            if (!rectF.contains(f3, f4, f5, f6)) {
                i6 = (zIntersects ? 1 : 0) | 2;
            }
            if (this.mLayout.isRtlCharAt(i4)) {
                i6 = (i6 == true ? 1 : 0) | 4;
            }
            builder.addCharacterBounds(i4 + i, f3, f4, f5, f6, i6);
        }
    }

    private float[] getCharacterBounds(int i, int i2, float f, float f2) {
        int i3 = i2 - i;
        float[] fArr = new float[i3 * 4];
        this.mLayout.fillCharacterBounds(i, i2, fArr, 0);
        for (int i4 = 0; i4 < i3; i4++) {
            int i5 = i4 * 4;
            fArr[i5] = fArr[i5] + f;
            int i6 = i5 + 1;
            fArr[i6] = fArr[i6] + f2;
            int i7 = i5 + 2;
            fArr[i7] = fArr[i7] + f;
            int i8 = i5 + 3;
            fArr[i8] = fArr[i8] + f2;
        }
        return fArr;
    }

    public CursorAnchorInfo getCursorAnchorInfo(int i, CursorAnchorInfo.Builder builder, Matrix matrix) {
        TextView textView;
        CursorAnchorInfo.Builder builder2 = builder;
        Layout layout = getLayout();
        if (layout == null) {
            return null;
        }
        int i2 = 1;
        boolean z = (i & 4) != 0;
        boolean z2 = (i & 8) != 0;
        boolean z3 = (i & 16) != 0;
        boolean z4 = (i & 32) != 0;
        boolean z5 = (i & 64) != 0;
        boolean z6 = (z || z2 || z3 || z4 || z5) ? false : true;
        boolean z7 = z | z6;
        boolean z8 = z2 | z6;
        boolean z9 = z3 | z6;
        boolean z10 = z4 | z6;
        boolean z11 = z5 | z6;
        builder2.reset();
        int selectionStart = getSelectionStart();
        builder2.setSelectionRange(selectionStart, getSelectionEnd());
        matrix.reset();
        transformMatrixToGlobal(matrix);
        builder.setMatrix(matrix);
        if (z7) {
            RectF rectF = new RectF();
            RectF rectF2 = new RectF();
            if (Flags.handwritingGestureWithTransformation()) {
                getEditorAndHandwritingBounds(rectF, rectF2);
            } else {
                if (this.mTempRect == null) {
                    this.mTempRect = new Rect();
                }
                Rect rect = this.mTempRect;
                if (getViewVisibleRect(rect)) {
                    rectF.set(rect);
                    rectF2.set(rectF);
                    rectF2.top -= getHandwritingBoundsOffsetTop();
                    rectF2.left -= getHandwritingBoundsOffsetLeft();
                    rectF2.bottom += getHandwritingBoundsOffsetBottom();
                    rectF2.right += getHandwritingBoundsOffsetRight();
                }
            }
            builder2.setEditorBoundsInfo(new EditorBoundsInfo.Builder().setEditorBounds(rectF).setHandwritingBounds(rectF2).build());
        }
        if (z8 || z9 || z10) {
            float fViewportToContentHorizontalOffset = viewportToContentHorizontalOffset();
            float fViewportToContentVerticalOffset = viewportToContentVerticalOffset();
            boolean z12 = getTransformationMethod() != null && (getTransformed() instanceof OffsetMapping);
            if (z8 && !z12) {
                CharSequence text = getText();
                if (text instanceof Spannable) {
                    Spannable spannable = (Spannable) text;
                    int composingSpanStart = EditableInputConnection.getComposingSpanStart(spannable);
                    int composingSpanEnd = EditableInputConnection.getComposingSpanEnd(spannable);
                    if (composingSpanEnd < composingSpanStart) {
                        composingSpanStart = composingSpanEnd;
                        composingSpanEnd = composingSpanStart;
                    }
                    if (composingSpanStart >= 0 && composingSpanStart < composingSpanEnd) {
                        builder2.setComposingText(composingSpanStart, text.subSequence(composingSpanStart, composingSpanEnd));
                        populateCharacterBounds(builder2, composingSpanStart, composingSpanEnd, fViewportToContentHorizontalOffset, fViewportToContentVerticalOffset);
                    }
                }
            }
            textView = this;
            if (!z9 || selectionStart < 0) {
                builder2 = builder;
            } else {
                int iOriginalToTransformed = textView.originalToTransformed(selectionStart, 1);
                float primaryHorizontal = layout.getPrimaryHorizontal(iOriginalToTransformed, layout.shouldClampCursor(layout.getLineForOffset(iOriginalToTransformed))) + fViewportToContentHorizontalOffset;
                float lineTop = layout.getLineTop(r1) + fViewportToContentVerticalOffset;
                float lineBaseline = layout.getLineBaseline(r1) + fViewportToContentVerticalOffset;
                float lineBottom = layout.getLineBottom(r1, false) + fViewportToContentVerticalOffset;
                boolean zIsPositionVisible = textView.isPositionVisible(primaryHorizontal, lineTop);
                boolean zIsPositionVisible2 = textView.isPositionVisible(primaryHorizontal, lineBottom);
                if (!zIsPositionVisible && !zIsPositionVisible2) {
                    i2 = 0;
                }
                if (!zIsPositionVisible || !zIsPositionVisible2) {
                    i2 |= 2;
                }
                if (layout.isRtlCharAt(iOriginalToTransformed)) {
                    i2 |= 4;
                }
                builder.setInsertionMarkerLocation(primaryHorizontal, lineTop, lineBaseline, lineBottom, i2);
                builder2 = builder;
            }
            if (z10) {
                if (Flags.handwritingGestureWithTransformation()) {
                    RectF rectF3 = new RectF();
                    if (textView.getContentVisibleRect(rectF3)) {
                        float f = rectF3.top - fViewportToContentVerticalOffset;
                        float f2 = rectF3.bottom - fViewportToContentVerticalOffset;
                        int lineForVertical = layout.getLineForVertical((int) Math.ceil(f2));
                        for (int lineForVertical2 = layout.getLineForVertical((int) Math.floor(f)); lineForVertical2 <= lineForVertical; lineForVertical2++) {
                            builder2.addVisibleLineBounds(layout.getLineLeft(lineForVertical2) + fViewportToContentHorizontalOffset, layout.getLineTop(lineForVertical2) + fViewportToContentVerticalOffset, layout.getLineRight(lineForVertical2) + fViewportToContentHorizontalOffset, layout.getLineBottom(lineForVertical2, false) + fViewportToContentVerticalOffset);
                        }
                    }
                } else {
                    if (textView.getContentVisibleRect(new Rect())) {
                        float f3 = r0.top - fViewportToContentVerticalOffset;
                        int lineForVertical3 = layout.getLineForVertical((int) Math.ceil(r0.bottom - fViewportToContentVerticalOffset));
                        for (int lineForVertical4 = layout.getLineForVertical((int) Math.floor(f3)); lineForVertical4 <= lineForVertical3; lineForVertical4++) {
                            builder2.addVisibleLineBounds(layout.getLineLeft(lineForVertical4) + fViewportToContentHorizontalOffset, layout.getLineTop(lineForVertical4) + fViewportToContentVerticalOffset, layout.getLineRight(lineForVertical4) + fViewportToContentHorizontalOffset, layout.getLineBottom(lineForVertical4, false) + fViewportToContentVerticalOffset);
                        }
                    }
                }
            }
        } else {
            textView = this;
        }
        if (z11) {
            builder2.setTextAppearanceInfo(TextAppearanceInfo.createFromTextView(textView));
        }
        return builder2.build();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public TextBoundsInfo getTextBoundsInfo(RectF rectF) {
        CharSequence text;
        Layout layout = getLayout();
        if (layout == null || (text = layout.getText()) == null || isOffsetMappingAvailable()) {
            return null;
        }
        Matrix matrix = new Matrix();
        transformMatrixToGlobal(matrix);
        Matrix matrix2 = new Matrix();
        if (!matrix.invert(matrix2)) {
            return null;
        }
        float fViewportToContentHorizontalOffset = viewportToContentHorizontalOffset();
        float fViewportToContentVerticalOffset = viewportToContentVerticalOffset();
        RectF rectF2 = new RectF(rectF);
        matrix2.mapRect(rectF2);
        rectF2.offset(-fViewportToContentHorizontalOffset, -fViewportToContentVerticalOffset);
        if (!rectF2.intersects(0.0f, 0.0f, layout.getWidth(), layout.getHeight()) || text.length() == 0) {
            TextBoundsInfo.Builder builder = new TextBoundsInfo.Builder(0, 0);
            SegmentFinder.PrescribedSegmentFinder prescribedSegmentFinder = new SegmentFinder.PrescribedSegmentFinder(new int[0]);
            builder.setMatrix(matrix).setCharacterBounds(new float[0]).setCharacterBidiLevel(new int[0]).setCharacterFlags(new int[0]).setGraphemeSegmentFinder(prescribedSegmentFinder).setLineSegmentFinder(prescribedSegmentFinder).setWordSegmentFinder(prescribedSegmentFinder);
            return builder.build();
        }
        int lineForVertical = layout.getLineForVertical((int) Math.floor(rectF2.top));
        int lineForVertical2 = layout.getLineForVertical((int) Math.floor(rectF2.bottom));
        int lineStart = layout.getLineStart(lineForVertical);
        int lineEnd = layout.getLineEnd(lineForVertical2);
        float[] characterBounds = getCharacterBounds(lineStart, lineEnd, fViewportToContentHorizontalOffset, fViewportToContentVerticalOffset);
        int i = lineEnd - lineStart;
        int[] iArr = new int[i];
        int[] iArr2 = new int[i];
        int i2 = lineForVertical;
        while (i2 <= lineForVertical2) {
            int lineStart2 = layout.getLineStart(i2);
            int lineEnd2 = layout.getLineEnd(i2);
            Layout.Directions lineDirections = layout.getLineDirections(i2);
            int i3 = 0;
            while (i3 < lineDirections.getRunCount()) {
                int runStart = lineDirections.getRunStart(i3) + lineStart2;
                Arrays.fill(iArr2, runStart - lineStart, Math.min(runStart + lineDirections.getRunLength(i3), lineEnd2) - lineStart, lineDirections.getRunLevel(i3));
                i3++;
                lineForVertical = lineForVertical;
            }
            int i4 = lineForVertical;
            boolean z = layout.getParagraphDirection(i2) == -1;
            while (lineStart2 < lineEnd2) {
                boolean zIsWhitespace = TextUtils.isWhitespace(text.charAt(lineStart2));
                int i5 = zIsWhitespace;
                if (TextUtils.isPunctuation(Character.codePointAt(text, lineStart2))) {
                    i5 = (zIsWhitespace ? 1 : 0) | 4;
                }
                int i6 = i5;
                if (TextUtils.isNewline(Character.codePointAt(text, lineStart2))) {
                    i6 = (i5 == true ? 1 : 0) | 2;
                }
                if (z) {
                    i6 = (i6 == true ? 1 : 0) | 8;
                }
                iArr[lineStart2 - lineStart] = i6;
                lineStart2++;
            }
            i2++;
            lineForVertical = i4;
        }
        int i7 = lineForVertical;
        GraphemeClusterSegmentFinder graphemeClusterSegmentFinder = new GraphemeClusterSegmentFinder(text, layout.getPaint());
        WordIterator wordIterator = getWordIterator();
        wordIterator.setCharSequence(text, 0, text.length());
        WordSegmentFinder wordSegmentFinder = new WordSegmentFinder(text, wordIterator);
        int[] iArr3 = new int[((lineForVertical2 - i7) + 1) * 2];
        for (int i8 = i7; i8 <= lineForVertical2; i8++) {
            int i9 = (i8 - i7) * 2;
            iArr3[i9] = layout.getLineStart(i8);
            iArr3[i9 + 1] = layout.getLineEnd(i8);
        }
        return new TextBoundsInfo.Builder(lineStart, lineEnd).setMatrix(matrix).setCharacterBounds(characterBounds).setCharacterBidiLevel(iArr2).setCharacterFlags(iArr).setGraphemeSegmentFinder(graphemeClusterSegmentFinder).setLineSegmentFinder(new SegmentFinder.PrescribedSegmentFinder(iArr3)).setWordSegmentFinder(wordSegmentFinder).build();
    }

    public boolean isPositionVisible(float f, float f2) {
        float[] fArr = TEMP_POSITION;
        synchronized (fArr) {
            fArr[0] = f;
            fArr[1] = f2;
            View view = this;
            while (view != null) {
                if (view != this) {
                    fArr[0] = fArr[0] - view.getScrollX();
                    fArr[1] = fArr[1] - view.getScrollY();
                }
                float f3 = fArr[0];
                if (f3 >= 0.0f && fArr[1] >= 0.0f && f3 <= view.getWidth() && fArr[1] <= view.getHeight()) {
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

    @Override // android.view.View
    public boolean performAccessibilityActionInternal(int i, Bundle bundle) {
        int length;
        Editor editor = this.mEditor;
        if (editor != null && (editor.mProcessTextIntentActionsHandler.performAccessibilityAction(i) || this.mEditor.performSmartActionsAccessibilityAction(i))) {
            return true;
        }
        switch (i) {
            case 16:
                return performAccessibilityActionClick(bundle);
            case 32:
                if (!isLongClickable()) {
                    return false;
                }
                if (isEnabled() && this.mBufferType == BufferType.EDITABLE) {
                    this.mEditor.mIsBeingLongClickedByAccessibility = true;
                    try {
                        return performLongClick();
                    } finally {
                        this.mEditor.mIsBeingLongClickedByAccessibility = false;
                    }
                }
                return performLongClick();
            case 256:
            case 512:
                ensureIterableTextForAccessibilitySelectable();
                return super.performAccessibilityActionInternal(i, bundle);
            case 16384:
                return isFocused() && canCopy() && onTextContextMenuItem(16908321);
            case 32768:
                return isFocused() && canPaste() && onTextContextMenuItem(16908322);
            case 65536:
                return isFocused() && canCut() && onTextContextMenuItem(16908320);
            case 131072:
                ensureIterableTextForAccessibilitySelectable();
                CharSequence iterableTextForAccessibility = getIterableTextForAccessibility();
                if (iterableTextForAccessibility == null) {
                    return false;
                }
                int i2 = bundle != null ? bundle.getInt(AccessibilityNodeInfo.ACTION_ARGUMENT_SELECTION_START_INT, -1) : -1;
                int i3 = bundle != null ? bundle.getInt(AccessibilityNodeInfo.ACTION_ARGUMENT_SELECTION_END_INT, -1) : -1;
                if (getSelectionStart() != i2 || getSelectionEnd() != i3) {
                    if (i2 == i3 && i3 == -1) {
                        Selection.removeSelection((Spannable) iterableTextForAccessibility);
                        return true;
                    }
                    if (i2 >= 0 && i2 <= i3 && i3 <= iterableTextForAccessibility.length()) {
                        requestFocusOnNonEditableSelectableText();
                        Selection.setSelection((Spannable) iterableTextForAccessibility, i2, i3);
                        Editor editor2 = this.mEditor;
                        if (editor2 != null) {
                            editor2.setUseCtxMenuInDesktopMode(false);
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
                lambda$setTextAsync$0(bundle != null ? bundle.getCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE) : null);
                CharSequence charSequence = this.mText;
                if (charSequence != null && (length = charSequence.length()) > 0) {
                    Selection.setSelection(this.mSpannable, length);
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
                if (i == 16908376) {
                    return isFocused() && canReplace() && onTextContextMenuItem(16908340);
                }
                return super.performAccessibilityActionInternal(i, bundle);
        }
    }

    private boolean performAccessibilityActionClick(Bundle bundle) {
        boolean z;
        if (!isEnabled()) {
            return false;
        }
        if (isClickable() || isLongClickable()) {
            if (isFocusable() && !isFocused()) {
                requestFocus();
            }
            performClick();
            z = true;
        } else {
            z = false;
        }
        if ((this.mMovement != null || onCheckIsTextEditor()) && hasSpannableText() && this.mLayout != null && ((isTextEditable() || isTextSelectable()) && isFocused())) {
            InputMethodManager inputMethodManager = getInputMethodManager();
            viewClicked(inputMethodManager);
            if (!isTextSelectable() && this.mEditor.mShowSoftInputOnFocus && inputMethodManager != null) {
                return inputMethodManager.showSoftInput(this, 0) | z;
            }
        }
        return z;
    }

    private void requestFocusOnNonEditableSelectableText() {
        if (!isTextEditable() && isTextSelectable() && isEnabled() && isFocusable() && !isFocused()) {
            requestFocus();
        }
    }

    private boolean hasSpannableText() {
        CharSequence charSequence = this.mText;
        return charSequence != null && (charSequence instanceof Spannable);
    }

    @Override // android.view.View
    public void sendAccessibilityEventInternal(int i) {
        Editor editor;
        if (i == 32768 && (editor = this.mEditor) != null) {
            editor.mProcessTextIntentActionsHandler.initializeAccessibilityActions();
        }
        super.sendAccessibilityEventInternal(i);
    }

    @Override // android.view.View, android.view.accessibility.AccessibilityEventSource
    public void sendAccessibilityEventUnchecked(AccessibilityEvent accessibilityEvent) {
        if (accessibilityEvent.getEventType() == 4096) {
            return;
        }
        super.sendAccessibilityEventUnchecked(accessibilityEvent);
    }

    private CharSequence getTextForAccessibility() {
        if (TextUtils.isEmpty(this.mText)) {
            return this.mHint;
        }
        return TextUtils.trimToParcelableSize(this.mTransformed);
    }

    boolean isVisibleToAccessibility() {
        if (!AccessibilityManager.getInstance(this.mContext).isEnabled()) {
            return false;
        }
        if (isFocused()) {
            return true;
        }
        return isSelected() && isShown();
    }

    void sendAccessibilityEventTypeViewTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        AccessibilityEvent accessibilityEventObtain = AccessibilityEvent.obtain(16);
        accessibilityEventObtain.setFromIndex(i);
        accessibilityEventObtain.setRemovedCount(i2);
        accessibilityEventObtain.setAddedCount(i3);
        accessibilityEventObtain.setBeforeText(charSequence);
        sendAccessibilityEventUnchecked(accessibilityEventObtain);
    }

    void sendAccessibilityEventTypeViewTextChanged(CharSequence charSequence, int i, int i2) {
        AccessibilityEvent accessibilityEventObtain = AccessibilityEvent.obtain(16);
        accessibilityEventObtain.setFromIndex(i);
        accessibilityEventObtain.setToIndex(i2);
        accessibilityEventObtain.setBeforeText(charSequence);
        sendAccessibilityEventUnchecked(accessibilityEventObtain);
    }

    private InputMethodManager getInputMethodManager() {
        return (InputMethodManager) getContext().getSystemService(InputMethodManager.class);
    }

    public boolean isInputMethodTarget() {
        InputMethodManager inputMethodManager = getInputMethodManager();
        return inputMethodManager != null && inputMethodManager.isActive(this);
    }

    public boolean onTextContextMenuItem(int i) {
        int iMax;
        String string;
        boolean zIsTextEditable;
        int length = this.mText.length();
        if (isFocused()) {
            int selectionStart = getSelectionStart();
            int selectionEnd = getSelectionEnd();
            iMax = Math.max(0, Math.min(selectionStart, selectionEnd));
            length = Math.max(0, Math.max(selectionStart, selectionEnd));
        } else {
            iMax = 0;
        }
        switch (i) {
            case 16908319:
                boolean zHasSelection = hasSelection();
                selectAllText();
                Editor editor = this.mEditor;
                if (editor != null && zHasSelection) {
                    editor.invalidateActionModeAsync();
                }
                return true;
            case 16908320:
                if (setPrimaryClip(ClipData.newPlainText(null, getTransformedText(iMax, length)))) {
                    deleteText_internal(iMax, length);
                } else {
                    Toast.makeText(getContext(), R.string.failed_to_copy_to_clipboard, 0).show();
                }
                return true;
            case 16908321:
                int selectionStart2 = getSelectionStart();
                int selectionEnd2 = getSelectionEnd();
                if (setPrimaryClip(ClipData.newPlainText(null, getTransformedText(Math.max(0, Math.min(selectionStart2, selectionEnd2)), Math.max(0, Math.max(selectionStart2, selectionEnd2)))))) {
                    stopTextActionMode();
                } else {
                    Toast.makeText(getContext(), R.string.failed_to_copy_to_clipboard, 0).show();
                }
                return true;
            case 16908322:
                paste(true);
                return true;
            default:
                switch (i) {
                    case 16908337:
                        paste(false);
                        return true;
                    case 16908338:
                        Editor editor2 = this.mEditor;
                        if (editor2 != null) {
                            editor2.undo();
                        }
                        return true;
                    case 16908339:
                        Editor editor3 = this.mEditor;
                        if (editor3 != null) {
                            editor3.redo();
                        }
                        return true;
                    case 16908340:
                        Editor editor4 = this.mEditor;
                        if (editor4 != null) {
                            editor4.replace();
                        }
                        return true;
                    case 16908341:
                        shareSelectedText();
                        return true;
                    default:
                        switch (i) {
                            case 16908355:
                                requestAutofill();
                                stopTextActionMode();
                                return true;
                            case 16908943:
                                InputMethodManager inputMethodManager = (InputMethodManager) this.mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                                if (inputMethodManager != null) {
                                    Bundle bundle = new Bundle();
                                    bundle.putString(EXTRA_KEY_BOARD, "clipboard");
                                    inputMethodManager.sendAppPrivateCommand(this, ACTION_SHOW_BOARD, bundle);
                                    if (inputMethodManager.showSoftInput(this, 0)) {
                                        Log.d(LOG_TAG, "Clipboard is shown");
                                        return true;
                                    }
                                }
                                Log.d(LOG_TAG, "Clipboard is not shown");
                                return false;
                            case 16909029:
                                deleteText_internal(iMax, length);
                                return true;
                            case 16909130:
                                Editor editor5 = this.mEditor;
                                if (editor5 != null) {
                                    editor5.lambda$startActionModeInternal$0();
                                    this.mEditor.mSelectionModifierCursorController.hide();
                                }
                                return true;
                            case 16909167:
                                break;
                            case 16909333:
                                ComponentName componentName = new ComponentName("com.android.settings", "com.samsung.android.settings.display.SecProcessTextManageAppsFragment");
                                if (!this.mContext.canStartActivityForResult()) {
                                    string = "";
                                } else {
                                    string = getContext().getPackageManager().queryIntentActivities(new Intent().setAction(Intent.ACTION_PROCESS_TEXT).setType("text/plain"), 0).toString();
                                }
                                getContext().startActivity(new Intent().setComponent(componentName).putExtra("resolveInfo", string));
                                return true;
                            case 16909641:
                                if (ViewRune.SUPPORT_EAGLE_EYE) {
                                    InputMethodManager inputMethodManager2 = (InputMethodManager) this.mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                                    if (inputMethodManager2 != null) {
                                        Bundle bundle2 = new Bundle();
                                        bundle2.putString(EXTRA_KEY_BOARD, EXTRA_VALUE_BOARD_EAGLE_EYE);
                                        inputMethodManager2.sendAppPrivateCommand(this, ACTION_SHOW_BOARD, bundle2);
                                        if (inputMethodManager2.showSoftInput(this, 0)) {
                                            return true;
                                        }
                                    }
                                    return false;
                                }
                                break;
                            case 16909874:
                                String selectedText = getSelectedText();
                                this.mPrevSelectionStartForSSS = getSelectionStart();
                                this.mPrevSelectionEndForSSS = getSelectionEnd();
                                Intent intentPutExtra = new Intent().setAction(ACTION_SSS_TRANSLATE).putExtra(Intent.EXTRA_TEXT, selectedText).putExtra("needsTranslatedTextResult", canSSSPaste());
                                intentPutExtra.setFlags(0);
                                try {
                                    startActivityForResult(intentPutExtra, 101);
                                } catch (ActivityNotFoundException e) {
                                    Log.e(LOG_TAG, "sssTranslate failed");
                                    Log.e(LOG_TAG, "ActivityNotFoundException", e);
                                }
                                return true;
                            case 16910076:
                                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                                String string2 = this.mTransformed.subSequence(iMax, length).toString();
                                intent.putExtra(SearchManager.EXTRA_NEW_SEARCH, true);
                                intent.putExtra("query", string2);
                                intent.putExtra(Browser.EXTRA_APPLICATION_ID, getContext().getPackageName());
                                try {
                                    intent.setFlags(268435456);
                                    getContext().startActivity(intent);
                                } catch (ActivityNotFoundException e2) {
                                    Log.e(LOG_TAG, "WebSearch failed");
                                    Log.e(LOG_TAG, "ActivityNotFoundException", e2);
                                }
                                return true;
                            case 16910091:
                                InputMethodManager inputMethodManager3 = (InputMethodManager) this.mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                                if (inputMethodManager3 != null) {
                                    Bundle bundle3 = new Bundle();
                                    bundle3.putString(SemInputMethodManagerUtils.KEY_SELECTED_TEXT, getSelectedText());
                                    inputMethodManager3.sendAppPrivateCommand(this, SemInputMethodManagerUtils.ACTION_SHOW_TOOLKIT_HBD, bundle3);
                                }
                                if (ViewRune.SUPPORT_WRITING_TOOLKIT_ACTIVITY && !(zIsTextEditable = isTextEditable())) {
                                    String selectedText2 = getSelectedText();
                                    this.mPrevSelectionStartForSSS = getSelectionStart();
                                    this.mPrevSelectionEndForSSS = getSelectionEnd();
                                    Intent intentPutExtra2 = new Intent().setAction("com.samsung.android.intent.action.WritingToolkit").setData(Uri.parse("honeyboard://writing-toolkit")).putExtra("toolkitSubject", selectedText2).putExtra("isTextEditable", zIsTextEditable);
                                    intentPutExtra2.setFlags(0);
                                    try {
                                        startActivityForResult(intentPutExtra2, 102);
                                    } catch (ActivityNotFoundException e3) {
                                        Log.e(LOG_TAG, "ActivityNotFoundException", e3);
                                    }
                                }
                                this.mEditor.stopTextActionModeWithPreservingSelection();
                                return true;
                            default:
                                return false;
                        }
                        InputMethodManager inputMethodManager4 = (InputMethodManager) this.mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                        if (inputMethodManager4 != null) {
                            Bundle bundle4 = new Bundle();
                            bundle4.putString(EXTRA_KEY_BOARD, "translation");
                            inputMethodManager4.sendAppPrivateCommand(this, ACTION_SHOW_BOARD, bundle4);
                            if (inputMethodManager4.showSoftInput(this, 0)) {
                                return true;
                            }
                        }
                        return false;
                }
        }
    }

    CharSequence getTransformedText(int i, int i2) {
        return removeSuggestionSpans(this.mTransformed.subSequence(i, i2));
    }

    @Override // android.view.View
    public boolean performLongClick() {
        boolean zPerformLongClick;
        Editor editor = this.mEditor;
        if (editor != null) {
            editor.mIsBeingLongClicked = true;
        }
        boolean zPerformLongClick2 = super.performLongClick();
        Editor editor2 = this.mEditor;
        if (editor2 != null) {
            zPerformLongClick = editor2.performLongClick(zPerformLongClick2) | zPerformLongClick2;
            this.mEditor.mIsBeingLongClicked = false;
            this.mEditor.mIsSelectedByLongClick = true;
        } else {
            zPerformLongClick = zPerformLongClick2;
        }
        if (zPerformLongClick) {
            if (!zPerformLongClick2) {
                performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(1));
            }
            Editor editor3 = this.mEditor;
            if (editor3 != null) {
                editor3.mDiscardNextActionUp = true;
            }
            return zPerformLongClick;
        }
        MetricsLogger.action(this.mContext, MetricsProto.MetricsEvent.TEXT_LONGPRESS, 0);
        return zPerformLongClick;
    }

    @Override // android.view.View
    protected void onScrollChanged(int i, int i2, int i3, int i4) {
        super.onScrollChanged(i, i2, i3, i4);
        Editor editor = this.mEditor;
        if (editor != null) {
            editor.onScrollChanged();
        }
    }

    public boolean isSuggestionsEnabled() {
        Editor editor = this.mEditor;
        if (editor == null || (editor.mInputType & 15) != 1 || (this.mEditor.mInputType & 524288) > 0) {
            return false;
        }
        int i = this.mEditor.mInputType & InputType.TYPE_MASK_VARIATION;
        return i == 0 || i == 48 || i == 80 || i == 64 || i == 160;
    }

    public void setCustomSelectionActionModeCallback(ActionMode.Callback callback) {
        createEditorIfNeeded();
        this.mEditor.mCustomSelectionActionModeCallback = callback;
    }

    public ActionMode.Callback getCustomSelectionActionModeCallback() {
        Editor editor = this.mEditor;
        if (editor == null) {
            return null;
        }
        return editor.mCustomSelectionActionModeCallback;
    }

    public void setCustomInsertionActionModeCallback(ActionMode.Callback callback) {
        createEditorIfNeeded();
        this.mEditor.mCustomInsertionActionModeCallback = callback;
    }

    public ActionMode.Callback getCustomInsertionActionModeCallback() {
        Editor editor = this.mEditor;
        if (editor == null) {
            return null;
        }
        return editor.mCustomInsertionActionModeCallback;
    }

    public void setTextClassifier(TextClassifier textClassifier) {
        this.mTextClassifier = textClassifier;
    }

    public TextClassifier getTextClassifier() {
        TextClassifier textClassifier = this.mTextClassifier;
        if (textClassifier != null) {
            return textClassifier;
        }
        TextClassificationManager textClassificationManagerForUser = getTextClassificationManagerForUser();
        if (textClassificationManagerForUser != null) {
            return textClassificationManagerForUser.getTextClassifier();
        }
        return TextClassifier.NO_OP;
    }

    TextClassifier getTextClassificationSession() {
        String str;
        TextClassifier textClassifier = this.mTextClassificationSession;
        if (textClassifier == null || textClassifier.isDestroyed()) {
            TextClassificationManager textClassificationManagerForUser = getTextClassificationManagerForUser();
            if (textClassificationManagerForUser != null) {
                if (isTextEditable()) {
                    str = TextClassifier.WIDGET_TYPE_EDITTEXT;
                } else if (isTextSelectable()) {
                    str = TextClassifier.WIDGET_TYPE_TEXTVIEW;
                } else {
                    str = TextClassifier.WIDGET_TYPE_UNSELECTABLE_TEXTVIEW;
                }
                TextClassificationContext textClassificationContextBuild = new TextClassificationContext.Builder(this.mContext.getPackageName(), str).build();
                this.mTextClassificationContext = textClassificationContextBuild;
                TextClassifier textClassifier2 = this.mTextClassifier;
                if (textClassifier2 != null) {
                    this.mTextClassificationSession = textClassificationManagerForUser.createTextClassificationSession(textClassificationContextBuild, textClassifier2);
                } else {
                    this.mTextClassificationSession = textClassificationManagerForUser.createTextClassificationSession(textClassificationContextBuild);
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

    public boolean requestActionMode(TextLinks.TextLinkSpan textLinkSpan) {
        Preconditions.checkNotNull(textLinkSpan);
        CharSequence charSequence = this.mText;
        if (!(charSequence instanceof Spanned)) {
            return false;
        }
        int spanStart = ((Spanned) charSequence).getSpanStart(textLinkSpan);
        int spanEnd = ((Spanned) this.mText).getSpanEnd(textLinkSpan);
        if (spanStart < 0 || spanEnd > this.mText.length() || spanStart >= spanEnd) {
            return false;
        }
        createEditorIfNeeded();
        this.mEditor.startLinkActionModeAsync(spanStart, spanEnd);
        return true;
    }

    public boolean handleClick(TextLinks.TextLinkSpan textLinkSpan) {
        Preconditions.checkNotNull(textLinkSpan);
        CharSequence charSequence = this.mText;
        if (!(charSequence instanceof Spanned)) {
            return false;
        }
        Spanned spanned = (Spanned) charSequence;
        int spanStart = spanned.getSpanStart(textLinkSpan);
        int spanEnd = spanned.getSpanEnd(textLinkSpan);
        if (spanStart < 0 || spanEnd > this.mText.length() || spanStart >= spanEnd) {
            return false;
        }
        final TextClassification.Request requestBuild = new TextClassification.Request.Builder(this.mText, spanStart, spanEnd).setDefaultLocales(getTextLocales()).build();
        Supplier supplier = new Supplier() { // from class: android.widget.TextView$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.lambda$handleClick$5(requestBuild);
            }
        };
        CompletableFuture.supplyAsync(supplier).completeOnTimeout(null, 1L, TimeUnit.SECONDS).thenAccept(new Consumer() { // from class: android.widget.TextView$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                TextView.lambda$handleClick$6((TextClassification) obj);
            }
        });
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ TextClassification lambda$handleClick$5(TextClassification.Request request) {
        return getTextClassificationSession().classifyText(request);
    }

    static /* synthetic */ void lambda$handleClick$6(TextClassification textClassification) {
        if (textClassification == null) {
            Log.d(LOG_TAG, "Timeout while classifying text");
            return;
        }
        if (textClassification.getActions().isEmpty()) {
            Log.d(LOG_TAG, "No link action to perform");
            return;
        }
        try {
            textClassification.getActions().get(0).getActionIntent().send();
        } catch (PendingIntent.CanceledException e) {
            Log.e(LOG_TAG, "Error sending PendingIntent", e);
        }
    }

    protected void stopTextActionMode() {
        Editor editor = this.mEditor;
        if (editor != null) {
            editor.lambda$startActionModeInternal$0();
        }
    }

    public void hideFloatingToolbar(int i) {
        Editor editor = this.mEditor;
        if (editor != null) {
            editor.hideFloatingToolbar(i);
        }
    }

    public boolean canUndo() {
        Editor editor = this.mEditor;
        return editor != null && editor.canUndo();
    }

    public boolean canRedo() {
        Editor editor = this.mEditor;
        return editor != null && editor.canRedo();
    }

    public boolean canCut() {
        Editor editor;
        return (hasPasswordTransformationMethod() || isKeyguardLocked() || this.mText.length() <= 0 || !hasSelection() || !(this.mText instanceof Editable) || (editor = this.mEditor) == null || editor.mKeyListener == null) ? false : true;
    }

    public boolean canCopy() {
        return (hasPasswordTransformationMethod() || isKeyguardLocked() || this.mText.length() <= 0 || !hasSelection() || this.mEditor == null) ? false : true;
    }

    boolean canReplace() {
        return !hasPasswordTransformationMethod() && this.mText.length() > 0 && (this.mText instanceof Editable) && this.mEditor != null && isSuggestionsEnabled() && this.mEditor.shouldOfferToShowSuggestions();
    }

    public boolean canShare() {
        return !isFlipCoverClosed() && getContext().canStartActivityForResult() && isDeviceProvisioned() && getContext().getResources().getBoolean(R.bool.config_textShareSupported) && canCopy() && isFinishSetupWizard() && (this.mActionModeFlags & 8192) == 8192;
    }

    boolean isDeviceProvisioned() {
        if (this.mDeviceProvisionedState == 0) {
            this.mDeviceProvisionedState = Settings.Global.getInt(this.mContext.getContentResolver(), "device_provisioned", 0) != 0 ? 2 : 1;
        }
        return this.mDeviceProvisionedState == 2;
    }

    public boolean canPaste() {
        Editor editor;
        return (this.mText instanceof Editable) && (editor = this.mEditor) != null && editor.mKeyListener != null && getSelectionStart() >= 0 && getSelectionEnd() >= 0 && getClipboardManagerForUser().hasPrimaryClip();
    }

    public boolean canPasteAsPlainText() {
        ClipDescription primaryClipDescription;
        if (!canPaste() || (primaryClipDescription = getClipboardManagerForUser().getPrimaryClipDescription()) == null || primaryClipDescription == null) {
            return false;
        }
        return (primaryClipDescription.hasMimeType("text/plain") && primaryClipDescription.isStyledText()) || primaryClipDescription.hasMimeType("text/html");
    }

    boolean canProcessText() {
        if (getId() == -1) {
            return false;
        }
        return canShare();
    }

    public boolean canSelectAllText() {
        if (isFocused() && canSelectText() && !hasPasswordTransformationMethod()) {
            return (getSelectionStart() == 0 && getSelectionEnd() == this.mText.length()) ? false : true;
        }
        return false;
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
        try {
            ClipData primaryClip = getClipboardManagerForUser().getPrimaryClip();
            if (primaryClip == null) {
                return;
            }
            performReceiveContent(new ContentInfo.Builder(primaryClip, 1).setFlags(!z ? 1 : 0).build());
            sLastCutCopyOrTextChangedTime = 0L;
        } catch (Exception e) {
            Log.e(LOG_TAG, "Failed to paste error.", e);
        }
    }

    private void shareSelectedText() {
        Intent intentMakeChooserIntent = makeChooserIntent(false);
        if (intentMakeChooserIntent != null) {
            intentMakeChooserIntent.setFlags(268435456);
            getContext().startActivity(intentMakeChooserIntent);
            Selection.setSelection(this.mSpannable, getSelectionEnd());
        }
    }

    private boolean setPrimaryClip(ClipData clipData) {
        try {
            getClipboardManagerForUser().setPrimaryClip(clipData);
            sLastCutCopyOrTextChangedTime = SystemClock.uptimeMillis();
            return true;
        } catch (Throwable unused) {
            return false;
        }
    }

    public int getOffsetForPosition(float f, float f2) {
        if (getLayout() == null) {
            return -1;
        }
        return getOffsetAtCoordinate(getLineAtCoordinate(f2), f);
    }

    float convertToLocalHorizontalCoordinate(float f) {
        return Math.min((getWidth() - getTotalPaddingRight()) - 1, Math.max(0.0f, f - getTotalPaddingLeft())) + getScrollX();
    }

    public int getLineAtCoordinate(float f) {
        return getLayout().getLineForVertical((int) (Math.min((getHeight() - getTotalPaddingBottom()) - 1, Math.max(0.0f, f - getTotalPaddingTop())) + getScrollY()));
    }

    int getLineAtCoordinateUnclamped(float f) {
        return getLayout().getLineForVertical((int) ((f - getTotalPaddingTop()) + getScrollY()));
    }

    int getOffsetAtCoordinate(int i, float f) {
        return transformedToOriginal(getLayout().getOffsetForHorizontal(i, convertToLocalHorizontalCoordinate(f)), 1);
    }

    public int transformedToOriginal(int i, int i2) {
        if (getTransformationMethod() != null) {
            CharSequence charSequence = this.mTransformed;
            if (charSequence instanceof OffsetMapping) {
                return ((OffsetMapping) charSequence).transformedToOriginal(i, i2);
            }
        }
        return i;
    }

    public int originalToTransformed(int i, int i2) {
        if (getTransformationMethod() != null) {
            CharSequence charSequence = this.mTransformed;
            if (charSequence instanceof OffsetMapping) {
                return ((OffsetMapping) charSequence).originalToTransformed(i, i2);
            }
        }
        return i;
    }

    @Override // android.view.View
    public boolean onDragEvent(DragEvent dragEvent) {
        Editor editor = this.mEditor;
        if (editor == null || !editor.hasInsertionController()) {
            return super.onDragEvent(dragEvent);
        }
        int action = dragEvent.getAction();
        if (action == 2) {
            if (this.mText instanceof Spannable) {
                Selection.setSelection(this.mSpannable, getOffsetForPosition(dragEvent.getX(), dragEvent.getY()));
            }
            return true;
        }
        if (action != 3) {
            if (action != 5) {
                return true;
            }
            requestFocus();
            return true;
        }
        Editor editor2 = this.mEditor;
        if (editor2 != null) {
            editor2.onDrop(dragEvent);
        }
        return true;
    }

    boolean isInBatchEditMode() {
        Editor editor = this.mEditor;
        if (editor == null) {
            return false;
        }
        Editor.InputMethodState inputMethodState = editor.mInputMethodState;
        if (inputMethodState != null) {
            return inputMethodState.mBatchEditNesting > 0;
        }
        return this.mEditor.mInBatchEditControllers;
    }

    @Override // android.view.View
    public void onRtlPropertiesChanged(int i) {
        super.onRtlPropertiesChanged(i);
        TextDirectionHeuristic textDirectionHeuristic = getTextDirectionHeuristic();
        if (this.mTextDir != textDirectionHeuristic) {
            this.mTextDir = textDirectionHeuristic;
            if (this.mLayout != null) {
                checkForRelayout();
            }
        }
    }

    public TextDirectionHeuristic getTextDirectionHeuristic() {
        if (hasPasswordTransformationMethod()) {
            return TextDirectionHeuristics.LTR;
        }
        Editor editor = this.mEditor;
        if (editor != null && (editor.mInputType & 15) == 3) {
            byte directionality = Character.getDirectionality(DecimalFormatSymbols.getInstance(getTextLocale()).getDigitStrings()[0].codePointAt(0));
            if (directionality == 1 || directionality == 2) {
                return TextDirectionHeuristics.RTL;
            }
            return TextDirectionHeuristics.LTR;
        }
        boolean z = getLayoutDirection() == 1;
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
                if (!z) {
                    break;
                } else {
                    break;
                }
        }
        return TextDirectionHeuristics.LTR;
    }

    @Override // android.view.View
    public void onResolveDrawables(int i) {
        if (this.mLastLayoutDirection == i) {
            return;
        }
        this.mLastLayoutDirection = i;
        Drawables drawables = this.mDrawables;
        if (drawables == null || !drawables.resolveWithLayoutDirection(i)) {
            return;
        }
        prepareDrawableForDisplay(this.mDrawables.mShowing[0]);
        prepareDrawableForDisplay(this.mDrawables.mShowing[2]);
        applyCompoundDrawableTint();
    }

    private void prepareDrawableForDisplay(Drawable drawable) {
        if (drawable == null) {
            return;
        }
        drawable.setLayoutDirection(getLayoutDirection());
        if (drawable.isStateful()) {
            drawable.setState(getDrawableState());
            drawable.jumpToCurrentState();
        }
    }

    @Override // android.view.View
    protected void resetResolvedDrawables() {
        super.resetResolvedDrawables();
        this.mLastLayoutDirection = -1;
    }

    protected void viewClicked(InputMethodManager inputMethodManager) {
        if (inputMethodManager != null) {
            inputMethodManager.viewClicked(this);
        }
    }

    protected void deleteText_internal(int i, int i2) {
        ((Editable) this.mText).delete(i, i2);
    }

    protected void replaceText_internal(int i, int i2, CharSequence charSequence) {
        ((Editable) this.mText).replace(i, i2, charSequence);
    }

    protected void setSpan_internal(Object obj, int i, int i2, int i3) {
        ((Editable) this.mText).setSpan(obj, i, i2, i3);
    }

    protected void setCursorPosition_internal(int i, int i2) {
        Selection.setSelection((Editable) this.mText, i, i2);
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
        CharSequence charSequence = this.mText;
        if (charSequence instanceof Spannable) {
            return;
        }
        setText(charSequence, BufferType.SPANNABLE);
        if (getLayout() == null) {
            assumeLayout();
        }
    }

    @Override // android.view.View
    public AccessibilityIterators.TextSegmentIterator getIteratorForGranularity(int i) {
        if (i == 4) {
            Spannable spannable = (Spannable) getIterableTextForAccessibility();
            if (!TextUtils.isEmpty(spannable) && getLayout() != null) {
                AccessibilityIterators.LineTextSegmentIterator lineTextSegmentIterator = AccessibilityIterators.LineTextSegmentIterator.getInstance();
                lineTextSegmentIterator.initialize(spannable, getLayout());
                return lineTextSegmentIterator;
            }
        } else if (i == 16 && !TextUtils.isEmpty((Spannable) getIterableTextForAccessibility()) && getLayout() != null) {
            AccessibilityIterators.PageTextSegmentIterator pageTextSegmentIterator = AccessibilityIterators.PageTextSegmentIterator.getInstance();
            pageTextSegmentIterator.initialize(this);
            return pageTextSegmentIterator;
        }
        return super.getIteratorForGranularity(i);
    }

    @Override // android.view.View
    public int getAccessibilitySelectionStart() {
        return getSelectionStart();
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
    public void setAccessibilitySelection(int i, int i2) {
        if (getAccessibilitySelectionStart() == i && getAccessibilitySelectionEnd() == i2) {
            return;
        }
        CharSequence iterableTextForAccessibility = getIterableTextForAccessibility();
        if (Math.min(i, i2) >= 0 && Math.max(i, i2) <= iterableTextForAccessibility.length()) {
            Selection.setSelection((Spannable) iterableTextForAccessibility, i, i2);
        } else {
            Selection.removeSelection((Spannable) iterableTextForAccessibility);
        }
        Editor editor = this.mEditor;
        if (editor != null) {
            editor.hideCursorAndSpanControllers();
            this.mEditor.lambda$startActionModeInternal$0();
        }
    }

    @Override // android.view.View
    protected void encodeProperties(ViewHierarchyEncoder viewHierarchyEncoder) throws Resources.NotFoundException, IOException {
        super.encodeProperties(viewHierarchyEncoder);
        TextUtils.TruncateAt ellipsize = getEllipsize();
        viewHierarchyEncoder.addProperty("text:ellipsize", ellipsize == null ? null : ellipsize.name());
        viewHierarchyEncoder.addProperty("text:textSize", getTextSize());
        viewHierarchyEncoder.addProperty("text:scaledTextSize", getScaledTextSize());
        viewHierarchyEncoder.addProperty("text:typefaceStyle", getTypefaceStyle());
        viewHierarchyEncoder.addProperty("text:selectionStart", getSelectionStart());
        viewHierarchyEncoder.addProperty("text:selectionEnd", getSelectionEnd());
        viewHierarchyEncoder.addProperty("text:curTextColor", this.mCurTextColor);
        CharSequence charSequence = this.mText;
        viewHierarchyEncoder.addUserProperty("text:text", charSequence != null ? charSequence.toString() : null);
        viewHierarchyEncoder.addProperty("text:gravity", this.mGravity);
    }

    public static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() { // from class: android.widget.TextView.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        ParcelableParcel editorState;
        CharSequence error;
        boolean frozenWithFocus;
        int selEnd;
        int selStart;
        CharSequence text;

        SavedState(Parcelable parcelable) {
            super(parcelable);
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

        private SavedState(Parcel parcel) {
            super(parcel);
            this.selStart = -1;
            this.selEnd = -1;
            this.selStart = parcel.readInt();
            this.selEnd = parcel.readInt();
            this.frozenWithFocus = parcel.readInt() != 0;
            this.text = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            if (parcel.readInt() != 0) {
                this.error = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            }
            if (parcel.readInt() != 0) {
                this.editorState = ParcelableParcel.CREATOR.createFromParcel(parcel);
            }
        }
    }

    private static class CharWrapper implements CharSequence, GetChars, GraphicsOperations {
        private char[] mChars;
        private int mLength;
        private int mStart;

        CharWrapper(char[] cArr, int i, int i2) {
            this.mChars = cArr;
            this.mStart = i;
            this.mLength = i2;
        }

        void set(char[] cArr, int i, int i2) {
            this.mChars = cArr;
            this.mStart = i;
            this.mLength = i2;
        }

        @Override // java.lang.CharSequence
        public int length() {
            return this.mLength;
        }

        @Override // java.lang.CharSequence
        public char charAt(int i) {
            return this.mChars[i + this.mStart];
        }

        @Override // java.lang.CharSequence
        public String toString() {
            return new String(this.mChars, this.mStart, this.mLength);
        }

        @Override // java.lang.CharSequence
        public CharSequence subSequence(int i, int i2) {
            int i3;
            if (i < 0 || i2 < 0 || i > (i3 = this.mLength) || i2 > i3) {
                throw new IndexOutOfBoundsException(i + ", " + i2);
            }
            return new String(this.mChars, this.mStart + i, i2 - i);
        }

        @Override // android.text.GetChars
        public void getChars(int i, int i2, char[] cArr, int i3) {
            int i4;
            if (i < 0 || i2 < 0 || i > (i4 = this.mLength) || i2 > i4) {
                throw new IndexOutOfBoundsException(i + ", " + i2);
            }
            System.arraycopy(this.mChars, this.mStart + i, cArr, i3, i2 - i);
        }

        @Override // android.text.GraphicsOperations
        public void drawText(BaseCanvas baseCanvas, int i, int i2, float f, float f2, Paint paint) {
            baseCanvas.drawText(this.mChars, this.mStart + i, i2 - i, f, f2, paint);
        }

        @Override // android.text.GraphicsOperations
        public void drawTextRun(BaseCanvas baseCanvas, int i, int i2, int i3, int i4, float f, float f2, boolean z, Paint paint) {
            char[] cArr = this.mChars;
            int i5 = this.mStart;
            baseCanvas.drawTextRun(cArr, i + i5, i2 - i, i3 + i5, i4 - i3, f, f2, z, paint);
        }

        @Override // android.text.GraphicsOperations
        public float measureText(int i, int i2, Paint paint) {
            return paint.measureText(this.mChars, this.mStart + i, i2 - i);
        }

        @Override // android.text.GraphicsOperations
        public int getTextWidths(int i, int i2, float[] fArr, Paint paint) {
            return paint.getTextWidths(this.mChars, this.mStart + i, i2 - i, fArr);
        }

        @Override // android.text.GraphicsOperations
        public float getTextRunAdvances(int i, int i2, int i3, int i4, boolean z, float[] fArr, int i5, Paint paint) {
            int i6 = i2 - i;
            int i7 = i4 - i3;
            char[] cArr = this.mChars;
            int i8 = this.mStart;
            return paint.getTextRunAdvances(cArr, i + i8, i6, i3 + i8, i7, z, fArr, i5);
        }

        @Override // android.text.GraphicsOperations
        public int getTextRunCursor(int i, int i2, boolean z, int i3, int i4, Paint paint) {
            int i5 = i2 - i;
            char[] cArr = this.mChars;
            int i6 = this.mStart;
            return paint.getTextRunCursor(cArr, i + i6, i5, z, i3 + i6, i4);
        }
    }

    private static final class Marquee {
        private static final int MARQUEE_DELAY = 1200;
        private static final float MARQUEE_DELTA_MAX = 0.07f;
        private static final int MARQUEE_DP_PER_SECOND = 30;
        private static final byte MARQUEE_RUNNING = 2;
        private static final byte MARQUEE_STARTING = 1;
        private static final byte MARQUEE_STOPPED = 0;
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
            public void doFrame(long j) {
                Marquee.this.tick();
            }
        };
        private Choreographer.FrameCallback mStartCallback = new Choreographer.FrameCallback() { // from class: android.widget.TextView.Marquee.2
            @Override // android.view.Choreographer.FrameCallback
            public void doFrame(long j) {
                Marquee.this.mStatus = (byte) 2;
                Marquee marquee = Marquee.this;
                marquee.mLastAnimationMs = marquee.mChoreographer.getFrameTime();
                Marquee.this.tick();
            }
        };
        private Choreographer.FrameCallback mRestartCallback = new Choreographer.FrameCallback() { // from class: android.widget.TextView.Marquee.3
            @Override // android.view.Choreographer.FrameCallback
            public void doFrame(long j) {
                if (Marquee.this.mStatus == 2) {
                    if (Marquee.this.mRepeatLimit >= 0) {
                        Marquee marquee = Marquee.this;
                        marquee.mRepeatLimit--;
                    }
                    Marquee marquee2 = Marquee.this;
                    marquee2.start(marquee2.mRepeatLimit);
                }
            }
        };
        private final Choreographer mChoreographer = Choreographer.getInstance();

        Marquee(TextView textView) {
            this.mPixelsPerMs = (textView.getContext().getResources().getDisplayMetrics().density * 30.0f) / 1000.0f;
            this.mView = new WeakReference<>(textView);
        }

        void tick() {
            if (this.mStatus != 2) {
                return;
            }
            this.mChoreographer.removeFrameCallback(this.mTickCallback);
            TextView textView = this.mView.get();
            if (textView == null || !textView.isAggregatedVisible()) {
                return;
            }
            if (textView.isFocused() || textView.isSelected()) {
                long frameTime = this.mChoreographer.getFrameTime();
                long j = frameTime - this.mLastAnimationMs;
                this.mLastAnimationMs = frameTime;
                float f = this.mScroll + (j * this.mPixelsPerMs);
                this.mScroll = f;
                float f2 = this.mMaxScroll;
                if (f > f2) {
                    this.mScroll = f2;
                    this.mChoreographer.postFrameCallbackDelayed(this.mRestartCallback, 1200L);
                } else {
                    this.mChoreographer.postFrameCallback(this.mTickCallback);
                }
                textView.invalidate();
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

        void start(int i) {
            if (i == 0) {
                stop();
                return;
            }
            this.mRepeatLimit = i;
            TextView textView = this.mView.get();
            if (textView == null || textView.mLayout == null) {
                return;
            }
            this.mStatus = (byte) 1;
            this.mScroll = 0.0f;
            int width = (textView.getWidth() - textView.getCompoundPaddingLeft()) - textView.getCompoundPaddingRight();
            float lineWidth = textView.mLayout.getLineWidth(0);
            float f = width;
            float f2 = f / 3.0f;
            float f3 = (lineWidth - f) + f2;
            this.mGhostStart = f3;
            this.mMaxScroll = f3 + f;
            this.mGhostOffset = f2 + lineWidth;
            this.mFadeStop = (f / 6.0f) + lineWidth;
            this.mMaxFadeScroll = f3 + lineWidth + lineWidth;
            textView.invalidate();
            this.mChoreographer.postFrameCallback(this.mStartCallback);
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
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if (AccessibilityManager.getInstance(TextView.this.mContext).isEnabled() && TextView.this.mTransformed != null) {
                this.mBeforeText = TextView.this.mTransformed.toString();
            }
            TextView.this.sendBeforeTextChanged(charSequence, i, i2, i3);
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            TextView.this.handleTextChanged(charSequence, i, i2, i3);
            if (TextView.this.isVisibleToAccessibility()) {
                TextView.this.sendAccessibilityEventTypeViewTextChanged(this.mBeforeText, i, i2, i3);
                this.mBeforeText = null;
            }
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
            TextView.this.sendAfterTextChanged(editable);
            if (MetaKeyKeyListener.getMetaState(editable, 2048) != 0) {
                MetaKeyKeyListener.stopSelecting(TextView.this, editable);
            }
        }

        @Override // android.text.SpanWatcher
        public void onSpanChanged(Spannable spannable, Object obj, int i, int i2, int i3, int i4) {
            TextView.this.spanChange(spannable, obj, i, i3, i2, i4);
        }

        @Override // android.text.SpanWatcher
        public void onSpanAdded(Spannable spannable, Object obj, int i, int i2) {
            TextView.this.spanChange(spannable, obj, -1, i, -1, i2);
        }

        @Override // android.text.SpanWatcher
        public void onSpanRemoved(Spannable spannable, Object obj, int i, int i2) {
            TextView.this.spanChange(spannable, obj, i, -1, i2, -1);
        }
    }

    @Override // android.view.View
    public void onInputConnectionOpenedInternal(InputConnection inputConnection, EditorInfo editorInfo, Handler handler) {
        Editor editor = this.mEditor;
        if (editor != null) {
            editor.getDefaultOnReceiveContentListener().setInputConnectionInfo(this, inputConnection, editorInfo);
        }
    }

    @Override // android.view.View
    public void onInputConnectionClosedInternal() {
        Editor editor = this.mEditor;
        if (editor != null) {
            editor.getDefaultOnReceiveContentListener().clearInputConnectionInfo();
        }
    }

    @Override // android.view.View
    public ContentInfo onReceiveContent(ContentInfo contentInfo) {
        Editor editor = this.mEditor;
        return editor != null ? editor.getDefaultOnReceiveContentListener().onReceiveContent(this, contentInfo) : contentInfo;
    }

    private static void logCursor(String str, String str2, Object... objArr) {
        if (str2 == null) {
            Log.d(LOG_TAG, str);
            return;
        }
        Log.d(LOG_TAG, str + ": " + String.format(str2, objArr));
    }

    @Override // android.view.View
    public void onCreateViewTranslationRequest(int[] iArr, Consumer<ViewTranslationRequest> consumer) {
        if (iArr == null || iArr.length == 0) {
            if (UiTranslationController.DEBUG) {
                Log.w(LOG_TAG, "Do not provide the support translation formats.");
                return;
            }
            return;
        }
        ViewTranslationRequest.Builder builder = new ViewTranslationRequest.Builder(getAutofillId());
        boolean z = true;
        if (ArrayUtils.contains(iArr, 1)) {
            CharSequence charSequence = this.mText;
            if (charSequence == null || charSequence.length() == 0) {
                if (UiTranslationController.DEBUG) {
                    Log.w(LOG_TAG, "Cannot create translation request for the empty text.");
                    return;
                }
                return;
            }
            if (!isAnyPasswordInputType() && !hasPasswordTransformationMethod()) {
                z = false;
            }
            if (isTextEditable() || z) {
                Log.w(LOG_TAG, "Cannot create translation request. editable = " + isTextEditable() + ", isPassword = " + z);
                return;
            }
            builder.setValue(ViewTranslationRequest.ID_TEXT, TranslationRequestValue.forText(this.mText));
            if (!TextUtils.isEmpty(getContentDescription())) {
                builder.setValue(ViewTranslationRequest.ID_CONTENT_DESCRIPTION, TranslationRequestValue.forText(getContentDescription()));
            }
        }
        consumer.accept(builder.build());
    }

    @Override // android.view.View
    public SemHoverPopupWindow semGetHoverPopup(boolean z) {
        if (!isHoveringUIEnabled()) {
            return null;
        }
        if (this.mHoverPopup == null) {
            if (!z) {
                return null;
            }
            this.mHoverPopup = new MoreInfoHPW(this, this.mHoverPopupType);
        }
        setSemHoverPopupWindowSettings(2);
        return this.mHoverPopup;
    }

    @Override // android.view.View
    public SemHoverPopupWindow semGetHoverPopup(int i) {
        if (!isHoveringUIEnabled()) {
            return null;
        }
        if (this.mHoverPopup == null) {
            if (i == 1) {
                int i2 = this.mHoverPopupType;
                if (i2 == 2 || i2 == 3) {
                    this.mHoverPopup = new MoreInfoHPW(this, this.mHoverPopupType);
                }
            } else if (i == 2 || i == 3) {
                this.mHoverPopup = new MoreInfoHPW(this, this.mHoverPopupType);
            }
        }
        setSemHoverPopupWindowSettings(i);
        this.mHoverPopupToolTypeByApp = i;
        if (i == 1 && this.mHoverPopupType == 1 && this.mHoverPopup != null) {
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

        public MoreInfoHPW(View view, int i) {
            super(view, i);
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
        protected void setInstanceByType(int i) {
            super.setInstanceByType(i);
            if (i == 2) {
                this.mPopupGravity = 12849;
                this.mAnimationStyle = R.style.Animation_HoverPopup;
                this.mHoverDetectTimeMS = 300;
            }
        }

        @Override // com.samsung.android.widget.SemHoverPopupWindow
        public boolean isHoverPopupPossible() {
            if (this.mPopupType == 2) {
                return !TextView.this.isAnyPasswordInputType() && (!TextUtils.isEmpty(this.mContentText) || ((this.mParentTextView.getLineCount() == 1 && this.mParentTextView.canMarquee()) || (this.mParentTextView.mLayout != null && this.mParentTextView.mLayout.getEllipsisCount(0) > 0)));
            }
            return super.isHoverPopupPossible();
        }

        @Override // com.samsung.android.widget.SemHoverPopupWindow
        protected void makeDefaultContentView() {
            LayoutInflater layoutInflaterFrom;
            TextView textView;
            int i = TextView.this.mContext.getResources().getConfiguration().orientation;
            TextView textView2 = null;
            if (this.mContentView == null || this.mContentView.getId() != ID_INFO_VIEW || i != this.mLastOrientation) {
                TypedValue typedValue = new TypedValue();
                TextView.this.mContext.getTheme().resolveAttribute(16843945, typedValue, false);
                if (!TextView.this.mIsThemeDeviceDefault || typedValue.data == 0) {
                    layoutInflaterFrom = LayoutInflater.from(TextView.this.mContext);
                } else {
                    layoutInflaterFrom = LayoutInflater.from(new ContextThemeWrapper(TextView.this.mContext, typedValue.data));
                }
                TextView textView3 = (TextView) layoutInflaterFrom.inflate(R.layout.hover_text_popup, (ViewGroup) null);
                textView3.semSetHoverPopupType(0);
                textView3.setId(ID_INFO_VIEW);
                this.mInitialMaxLine = textView3.getMaxLines();
                this.mLastOrientation = i;
                textView = textView3;
            } else {
                textView = (TextView) this.mContentView;
            }
            CharSequence text = !TextUtils.isEmpty(this.mContentText) ? this.mContentText : this.mParentTextView.getText();
            if (!TextUtils.isEmpty(text)) {
                textView.lambda$setTextAsync$0(text.toString());
                textView.setEllipsize(TextUtils.TruncateAt.END);
                textView2 = textView;
            }
            this.mContentView = textView2;
        }
    }

    private void initTextStrikeThroughAnim() {
        if (this.mStrikeThroughPaint == null) {
            Paint paint = new Paint();
            this.mStrikeThroughPaint = paint;
            paint.setColor(this.mCurTextColor);
            this.mStrikeThroughPaint.setStrokeWidth(Math.max(getTextSize() / 18.0f, 1.0f));
        }
        if (this.mDrawTextStrikeAnimator == null) {
            ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
            this.mDrawTextStrikeAnimator = valueAnimatorOfFloat;
            valueAnimatorOfFloat.setDuration(400L);
            this.mDrawTextStrikeAnimator.addListener(new AnimatorListenerAdapter() { // from class: android.widget.TextView.4
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    TextView.this.mLineIsDrawed = true;
                }
            });
            this.mDrawTextStrikeAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: android.widget.TextView.5
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    TextView.this.mDrawStrikeAnimationValue = valueAnimator.getAnimatedFraction();
                    TextView.this.invalidate();
                }
            });
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x005f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void drawTextStrikethrough(Canvas canvas, int i) {
        float f;
        Canvas canvas2 = canvas;
        int totalPaddingLeft = getTotalPaddingLeft();
        int totalPaddingTop = getTotalPaddingTop();
        int lineCount = getLineCount();
        Layout layout = getLayout();
        Paint paint = this.mStrikeThroughPaint;
        if (paint != null) {
            paint.setColor(i);
        }
        if (!this.mTextStrikeThroughEnabled || this.mDrawStrikeAnimationValue <= 0.0f || this.mStrikeThroughPaint == null || lineCount <= 0) {
            return;
        }
        float[] fArr = new float[lineCount];
        for (int i2 = 0; i2 < lineCount; i2++) {
            float lineWidth = layout.getLineWidth(i2);
            fArr[i2] = lineWidth;
            if (i2 > 0) {
                fArr[i2] = lineWidth + fArr[i2 - 1];
            }
        }
        float f2 = fArr[lineCount - 1] * this.mDrawStrikeAnimationValue;
        int i3 = 0;
        while (i3 < lineCount) {
            float f3 = i3 == 0 ? 0.0f : fArr[i3 - 1];
            float f4 = fArr[i3];
            if (f2 > f3) {
                f = (f2 <= f3 || f2 > f4) ? f2 > f4 ? f4 - f3 : 0.0f : f2 - f3;
            }
            layout.getLineTop(i3);
            float lineBaseline = layout.getLineBaseline(i3) + totalPaddingTop;
            if (i3 == 0) {
                layout.getTopPadding();
            }
            float textSize = lineBaseline + (getTextSize() * (-0.2857143f));
            boolean z = getLayoutDirection() == 1;
            if (z) {
                canvas2.save();
                canvas2.translate(getWidth(), 0.0f);
                canvas2.scale(-1.0f, 1.0f);
            }
            float f5 = f;
            float f6 = totalPaddingLeft;
            canvas2.drawLine(f6, textSize, f5 + f6, textSize, this.mStrikeThroughPaint);
            if (z) {
                canvas.restore();
            }
            i3++;
            canvas2 = canvas;
        }
    }

    public void semSetAnimatedStrike(boolean z) {
        initTextStrikeThroughAnim();
        this.mTextStrikeThroughEnabled = z;
        ValueAnimator valueAnimator = this.mDrawTextStrikeAnimator;
        if (valueAnimator != null) {
            if (z) {
                valueAnimator.start();
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
    public void setMyanmarEncoding(int i) {
        if (i == 0) {
            this.mTextPaint.setMyanmarEncoding(Paint.MyanmarEncoding.ME_UNICODE);
        } else if (i == 1) {
            this.mTextPaint.setMyanmarEncoding(Paint.MyanmarEncoding.ME_ZAWGYI);
        } else {
            this.mTextPaint.setMyanmarEncoding(Paint.MyanmarEncoding.ME_AUTO);
        }
    }

    public void hideCursorControllers() {
        Editor editor = this.mEditor;
        if (editor != null) {
            editor.hideCursorAndSpanControllers();
            this.mEditor.lambda$startActionModeInternal$0();
        }
    }

    public boolean semIsEllipsis() {
        int compoundPaddingLeft = ((this.mRight - this.mLeft) - getCompoundPaddingLeft()) - getCompoundPaddingRight();
        return getVisibility() == 0 && compoundPaddingLeft > 0 && getLineCount() == 1 && getLayout() != null && (((int) getLayout().getLineWidth(0)) > compoundPaddingLeft || getLayout().getEllipsisCount(0) > 0);
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x0081  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void semSetSelection(Spannable spannable, int i, int i2) {
        int i3;
        int i4;
        int length = spannable.length();
        if ((i <= 0 || i >= length) && (i2 <= 0 || i2 >= length)) {
            i3 = i;
            i4 = i2;
        } else {
            boolean z = i > 0 && i < length && (TextUtils.isCombinedCode(spannable.charAt(i)) || spannable.charAt(i + (-1)) == Emoji.ZERO_WIDTH_JOINER);
            if (i2 > 0 && i2 < length) {
                char cCharAt = spannable.charAt(i2);
                if (!z && (TextUtils.isCombinedCode(cCharAt) || spannable.charAt(i2 - 1) == Emoji.ZERO_WIDTH_JOINER)) {
                    z = true;
                }
            }
            if (z) {
                float[] fArr = new float[length];
                char[] cArr = new char[length];
                Paint paint = new Paint(1);
                TextUtils.getChars(spannable, 0, length, cArr, 0);
                paint.getTextRunAdvances(cArr, 0, length, 0, length, false, fArr, 0);
                int i5 = i;
                while (i5 < length && fArr[i5] == 0.0f && cArr[i5] != '\n') {
                    i5++;
                }
                i4 = i2;
                while (i4 < length && fArr[i4] == 0.0f && cArr[i4] != '\n') {
                    i4++;
                }
                i3 = i5;
            }
        }
        try {
            Selection.setSelection(spannable, i3, i4);
        } catch (IndexOutOfBoundsException e) {
            Log.e(LOG_TAG, "TextView.semSetSelection");
            Log.e(LOG_TAG, "text : " + ((Object) spannable));
            Log.e(LOG_TAG, "initStart : " + i + ", initStop" + i2);
            Log.e(LOG_TAG, "start : " + i3 + ", stop" + i4);
            throw e;
        }
    }

    public static void semSetSelection(Spannable spannable, int i) {
        semSetSelection(spannable, i, i);
    }

    private TextDirectionHeuristic getTextDirectionHeuristic(boolean z) {
        if (!z) {
            return getTextDirectionHeuristic();
        }
        boolean z2 = getLayoutDirection() == 1;
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
                if (z2) {
                    return TextDirectionHeuristics.FIRSTSTRONG_RTL;
                }
                return TextDirectionHeuristics.FIRSTSTRONG_LTR;
        }
    }

    public boolean getKeycodeDpadCenterStatus() {
        return this.mKeycodeDpadCenterStatus;
    }

    public void semSetActionModeMenuItemEnabled(int i, boolean z) {
        if (z) {
            int i2 = this.mActionModeFlags;
            if ((i2 & i) != i) {
                this.mActionModeFlags = i | i2;
                return;
            }
            return;
        }
        int i3 = this.mActionModeFlags;
        if ((i3 & i) == i) {
            this.mActionModeFlags = i ^ i3;
        }
    }

    private void hidden_semSetActionModeMenuItemEnabled(int i, boolean z) {
        semSetActionModeMenuItemEnabled(i, z);
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
            StringBuilder sb = new StringBuilder("cannot show clipboard, caused by mEnableClipboard = ");
            sb.append((this.mActionModeFlags & 4096) == 4096);
            sb.append(", isKeyguardLocked() : ");
            sb.append(isKeyguardLocked());
            Log.d(LOG_TAG, sb.toString());
            return false;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) this.mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager == null) {
            return false;
        }
        boolean zSemIsAccessoryKeyboard = inputMethodManager.semIsAccessoryKeyboard();
        int i = Settings.Secure.getInt(this.mContext.getContentResolver(), Settings.Secure.SHOW_IME_WITH_HARD_KEYBOARD, 0);
        if (zSemIsAccessoryKeyboard && i != 1) {
            Log.d(LOG_TAG, "isAccessoryKeyboard is true, showImeWithHardKeyboard : " + i);
            return false;
        }
        InputMethodManager inputMethodManager2 = (InputMethodManager) this.mContext.getSystemService(InputMethodManager.class);
        Editor editor = this.mEditor;
        if (editor != null && !editor.editorShowSoftInput() && inputMethodManager2 != null && !inputMethodManager2.isInputMethodShown()) {
            Log.d(LOG_TAG, "Input method is not shown.");
            return false;
        }
        if (!SemInputMethodManagerUtils.METHOD_ID_HONEYBOARD.equals(Settings.Secure.getStringForUser(this.mContext.getContentResolver(), Settings.Secure.DEFAULT_INPUT_METHOD, -3))) {
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

    boolean canScanText() {
        InputMethodManager inputMethodManager;
        if (isFlipCoverClosed() || !ViewRune.SUPPORT_EAGLE_EYE) {
            return false;
        }
        Editor editor = this.mEditor;
        if ((editor == null || editor.mShowSoftInputOnFocus) && !isKeyguardLocked() && isFinishSetupWizard() && Settings.System.getInt(this.mContext.getContentResolver(), Settings.System.SEM_EMERGENCY_MODE, 0) != 1 && (inputMethodManager = (InputMethodManager) this.mContext.getSystemService(Context.INPUT_METHOD_SERVICE)) != null) {
            boolean zSemIsAccessoryKeyboard = inputMethodManager.semIsAccessoryKeyboard();
            int i = Settings.Secure.getInt(this.mContext.getContentResolver(), Settings.Secure.SHOW_IME_WITH_HARD_KEYBOARD, 0);
            if (zSemIsAccessoryKeyboard && i != 1) {
                return false;
            }
            String stringForUser = Settings.Secure.getStringForUser(this.mContext.getContentResolver(), Settings.Secure.DEFAULT_INPUT_METHOD, -3);
            if (Build.VERSION.SEM_PLATFORM_INT >= 130100 && SemInputMethodManagerUtils.METHOD_ID_HONEYBOARD.equals(stringForUser) && !hasSelection()) {
                return true;
            }
        }
        return false;
    }

    boolean canHBDTranslate() {
        int inputType = getInputType();
        int i = inputType & 4095;
        if (isFlipCoverClosed()) {
            return false;
        }
        boolean z = (inputType & 15) == 0 && (i == 128 || i == 224 || i == 144);
        Editor editor = this.mEditor;
        return (editor == null || editor.mShowSoftInputOnFocus) && !isAnyPasswordInputType() && !z && !isKeyguardLocked() && isFinishSetupWizard() && SemInputMethodManagerUtils.METHOD_ID_HONEYBOARD.equals(Settings.Secure.getStringForUser(this.mContext.getContentResolver(), Settings.Secure.DEFAULT_INPUT_METHOD, -3)) && hasSelection() && isTextEditable();
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
        int inputType2;
        int i;
        if (isFlipCoverClosed() || this.mDisableWritingToolkitMenu || isAnyPasswordInputType() || hasPasswordTransformationMethod() || (getImeOptions() & 255) == 3 || (inputType2 = (inputType = getInputType()) & 4095) == 17 || inputType2 == 33 || inputType2 == 49 || inputType2 == 209 || inputType2 == 4 || inputType2 == 20 || inputType2 == 36 || inputType2 == 2 || inputType2 == 3 || (i = inputType & 16773135) == 4098 || i == 8194) {
            return true;
        }
        String privateImeOptions = getPrivateImeOptions();
        if (privateImeOptions == null) {
            return false;
        }
        List listAsList = Arrays.asList(privateImeOptions.split(NavigationBarInflaterView.GRAVITY_SEPARATOR));
        return listAsList.contains("inputType=month_edittext") || listAsList.contains("inputType=ipAddress");
    }

    protected SemClipboardManager getSemClipboardManager() {
        if (this.mSemClipboardManager == null) {
            this.mSemClipboardManager = (SemClipboardManager) getContext().getSystemService(Context.SEM_CLIPBOARD_SERVICE);
        }
        return this.mSemClipboardManager;
    }

    boolean canClipboardForContextMenu() {
        InputMethodManager inputMethodManager = (InputMethodManager) this.mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager == null) {
            return false;
        }
        if (!inputMethodManager.isActive(this)) {
            Log.d(LOG_TAG, "this view is not active for input methoad");
            return false;
        }
        if (isDexEnabled() && !inputMethodManager.isInputMethodShown() && !inputMethodManager.getDexSettingsValue(Settings.Global.SEM_DEX_SHOW_VIRTUAL_KEYBOARD, "0")) {
            Log.d(LOG_TAG, "dexSetting is true, but show on-screen keyboard is false");
            return false;
        }
        Editor editor = this.mEditor;
        if (editor != null && !editor.hasInsertionController()) {
            Log.d(LOG_TAG, "this view don't support insertion handles");
            return false;
        }
        return canClipboard();
    }

    private boolean isDexEnabled() {
        return getResources().getConfiguration().semDesktopModeEnabled == 1;
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
        public void onTouch(MotionEvent motionEvent) {
            if (ViewRune.WIDGET_PEN_SUPPORTED) {
                int action = motionEvent.getAction();
                float rawX = motionEvent.getRawX();
                float rawY = motionEvent.getRawY();
                if (action == 0) {
                    this.mPressTime = SystemClock.uptimeMillis();
                    this.mStartX = rawX;
                    this.mStartY = rawY;
                    return;
                }
                if (action != 1) {
                    return;
                }
                long jUptimeMillis = SystemClock.uptimeMillis() - this.mPressTime;
                float fAbs = Math.abs(rawX - this.mStartX);
                float fAbs2 = Math.abs(rawY - this.mStartY);
                boolean z = (fAbs * fAbs) + (fAbs2 * fAbs2) <= TextView.this.TOUCH_DELTA * TextView.this.TOUCH_DELTA;
                if (!MultiSelection.getIsMultiSelectingText() && jUptimeMillis < 1000 && z) {
                    if (TextView.this.getPenSelectionController().isPenSelectionArea(TextView.this.getContext(), TextView.this.getRootView(), (int) rawX, (int) rawY)) {
                        return;
                    }
                    TextView.this.clearAllMultiSelection();
                } else {
                    if (TextView.this.isValidMultiSelection()) {
                        return;
                    }
                    TextView.this.clearMultiSelection();
                }
            }
        }
    }

    @Override // android.view.View
    public boolean performClick() {
        boolean zPerformClick = super.performClick();
        if (ViewRune.WIDGET_PEN_SUPPORTED && zPerformClick && this.mhasMultiSelection) {
            clearMultiSelection();
        }
        return zPerformClick;
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

    public void semSetMultiSelectionEnabled(boolean z) {
        if (ViewRune.WIDGET_PEN_SUPPORTED) {
            this.mEnableMultiSelection = z;
            if (!z) {
                removeForStylusPenEvent();
            } else {
                registerForStylusPenEvent();
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Rect getSpannedTextRect(Rect rect) {
        int lineTop;
        int lineBottom;
        CharSequence text = getText();
        if ((text instanceof Spanned) && this.mLayout != null) {
            Spanned spanned = (Spanned) text;
            if (((ReplacementSpan[]) spanned.getSpans(0, text.length(), ReplacementSpan.class)).length <= 0) {
                return null;
            }
            Point screenPointOfView = getScreenPointOfView(this);
            int i = rect.left - screenPointOfView.x;
            int lineAtCoordinate = getLineAtCoordinate(rect.top - screenPointOfView.y);
            int maxLines = getMaxLines();
            if (maxLines > 0 && maxLines <= lineAtCoordinate) {
                lineAtCoordinate = maxLines - 1;
            }
            int offsetAtCoordinate = getOffsetAtCoordinate(lineAtCoordinate, i);
            int primaryHorizontal = (int) this.mLayout.getPrimaryHorizontal(offsetAtCoordinate);
            if (i < primaryHorizontal) {
                if (offsetAtCoordinate <= 0) {
                    return null;
                }
                offsetAtCoordinate--;
                primaryHorizontal = (int) this.mLayout.getPrimaryHorizontal(offsetAtCoordinate);
            }
            int i2 = primaryHorizontal;
            ReplacementSpan[] replacementSpanArr = (ReplacementSpan[]) spanned.getSpans(offsetAtCoordinate, offsetAtCoordinate, ReplacementSpan.class);
            if (replacementSpanArr.length > 0) {
                int spanStart = spanned.getSpanStart(replacementSpanArr[0]);
                int spanEnd = spanned.getSpanEnd(replacementSpanArr[0]);
                if (lineAtCoordinate == getLineCount() - 1) {
                    lineTop = this.mLayout.getLineTop(lineAtCoordinate) - (((int) getLineSpacingExtra()) / 2);
                    lineBottom = this.mLayout.getLineBottom(lineAtCoordinate) + (((int) getLineSpacingExtra()) / 2);
                } else {
                    lineTop = this.mLayout.getLineTop(lineAtCoordinate);
                    lineBottom = this.mLayout.getLineBottom(lineAtCoordinate);
                }
                Rect rect2 = new Rect(0, 0, 0, 0);
                rect2.right = replacementSpanArr[0].getSize(this.mTextPaint, spanned, spanStart, spanEnd, null);
                rect2.bottom = lineBottom - lineTop;
                rect2.offset(i2 + screenPointOfView.x, lineTop + screenPointOfView.y);
                return rect2;
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Point getScreenPointOfView(View view) {
        Point point = new Point();
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        point.x = iArr[0];
        point.y = iArr[1];
        return point;
    }

    private boolean isLinkPreviewEnabled(int i) {
        if (i == 1 || i != 2) {
            return false;
        }
        return isLinkPreviewSettingsEnabled();
    }

    private boolean isLinkPreviewSettingsEnabled() {
        return Settings.System.getIntForUser(this.mContext.getContentResolver(), Settings.System.SEM_PEN_HOVERING, 0, -3) == 1 && Settings.System.getIntForUser(this.mContext.getContentResolver(), Settings.System.PEN_HOVERING_LINK_PREVIEW, 0, -3) == 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean canTextMultiSelection() {
        return ViewRune.WIDGET_PEN_SUPPORTED && this.mEnableMultiSelection && isCoverOpened() && isFinishSetupWizard() && !isCarMode() && !isKeyguardLocked() && !isDisabledStylusPenEvent() && !isSubWindow();
    }

    private boolean isCoverOpened() {
        if (ViewRune.WIDGET_PEN_SUPPORTED) {
            try {
                ICoverManager iCoverManagerAsInterface = ICoverManager.Stub.asInterface(ServiceManager.getService(UnionConstants.SERVICE_COVER));
                if (iCoverManagerAsInterface != null) {
                    return iCoverManagerAsInterface.getCoverState().getSwitchState();
                }
            } catch (Exception unused) {
                Log.w(LOG_TAG, "isCoverOpened() : RemoteException!!!!");
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
        KeyguardManager keyguardManager = (KeyguardManager) this.mContext.getSystemService(Context.KEYGUARD_SERVICE);
        if (keyguardManager == null) {
            Log.d(LOG_TAG, "keyGuard Service is null");
            return false;
        }
        boolean zIsKeyguardLocked = keyguardManager.isKeyguardLocked();
        if (zIsKeyguardLocked) {
            Log.d(LOG_TAG, "Keyguard is Locked!");
        }
        return zIsKeyguardLocked;
    }

    private boolean isSubWindow() {
        ViewGroup.LayoutParams layoutParams = getRootView().getLayoutParams();
        if (!(layoutParams instanceof WindowManager.LayoutParams)) {
            return false;
        }
        WindowManager.LayoutParams layoutParams2 = (WindowManager.LayoutParams) layoutParams;
        return layoutParams2.type >= 1000 && layoutParams2.type <= 1999;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean checkPosInView(int i, int i2, int i3) {
        if (!isVisibleToUser()) {
            return false;
        }
        Rect rect = new Rect();
        getGlobalVisibleRect(rect, null);
        Point screenPointOfView = getScreenPointOfView(getRootView());
        rect.offset(screenPointOfView.x, screenPointOfView.y);
        int i4 = -i3;
        rect.inset(i4, i4);
        return i >= rect.left && i <= rect.right && i2 >= rect.top && i2 <= rect.bottom;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean checkPosOnText(int i, int i2, int i3) {
        int i4;
        Layout layout = getLayout();
        if (layout == null) {
            return false;
        }
        int[] iArr = new int[2];
        getLocationOnScreen(iArr);
        int i5 = i - iArr[0];
        int i6 = i2 - iArr[1];
        int lineAtCoordinate = getLineAtCoordinate(i6);
        int lineStart = layout.getLineStart(lineAtCoordinate);
        int lineTop = layout.getLineTop(lineAtCoordinate) + getTotalPaddingTop();
        int lineBottom = layout.getLineBottom(lineAtCoordinate) + getTotalPaddingTop();
        int lineWidth = (int) layout.getLineWidth(lineAtCoordinate);
        int primaryHorizontal = (((int) layout.getPrimaryHorizontal(lineStart)) - getScrollX()) + getTotalPaddingLeft();
        if (layout.getParagraphDirection(lineAtCoordinate) == -1) {
            int i7 = primaryHorizontal - lineWidth;
            i4 = primaryHorizontal;
            primaryHorizontal = i7;
        } else {
            i4 = lineWidth + primaryHorizontal;
        }
        return i5 >= primaryHorizontal - i3 && i4 + i3 >= i5 && i6 >= lineTop && i6 <= lineBottom;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0, types: [android.widget.TextView] */
    /* JADX WARN: Type inference failed for: r3v1, types: [android.view.View] */
    /* JADX WARN: Type inference failed for: r3v3, types: [android.view.View] */
    public boolean checkValidMultiSelectionForPreDraw() {
        if (!ViewRune.WIDGET_PEN_SUPPORTED || !this.mhasMultiSelection || !isVisibleToUser() || getLayout() == null) {
            return false;
        }
        View rootView = getRootView();
        do {
            this = (View) this.getParent();
            if (this == 0) {
                return false;
            }
            if (this == rootView) {
                return true;
            }
        } while (this instanceof View);
        return false;
    }

    public boolean isValidMultiSelection() {
        CharSequence textForMultiSelection;
        Layout layout;
        if (!ViewRune.WIDGET_PEN_SUPPORTED || !this.mhasMultiSelection || !isVisibleToUser() || (textForMultiSelection = getTextForMultiSelection()) == null || (layout = getLayout()) == null) {
            return false;
        }
        Rect rect = new Rect();
        getGlobalVisibleRect(rect, null);
        Point screenPointOfView = getScreenPointOfView(getRootView());
        rect.offset(screenPointOfView.x, screenPointOfView.y);
        Spannable spannable = (Spannable) textForMultiSelection;
        int selectionStart = MultiSelection.getSelectionStart(spannable);
        int selectionEnd = MultiSelection.getSelectionEnd(spannable);
        if (selectionStart >= selectionEnd) {
            selectionStart = selectionEnd;
            selectionEnd = selectionStart;
        }
        int lineForOffset = layout.getLineForOffset(selectionStart);
        int lineForOffset2 = layout.getLineForOffset(selectionEnd);
        int totalPaddingTop = getTotalPaddingTop();
        int totalPaddingLeft = getTotalPaddingLeft();
        int primaryHorizontal = (((int) layout.getPrimaryHorizontal(selectionStart)) + totalPaddingLeft) - getScrollX();
        int primaryHorizontal2 = (((int) layout.getPrimaryHorizontal(selectionEnd)) + totalPaddingLeft) - getScrollX();
        if (primaryHorizontal > primaryHorizontal2) {
            primaryHorizontal = primaryHorizontal2;
            primaryHorizontal2 = primaryHorizontal;
        }
        int lineTop = (layout.getLineTop(lineForOffset) + totalPaddingTop) - getScrollY();
        int lineBaseline = (layout.getLineBaseline(lineForOffset2) + totalPaddingTop) - getScrollY();
        Point screenPointOfView2 = getScreenPointOfView(this);
        Rect rect2 = new Rect(primaryHorizontal, lineTop, primaryHorizontal2, lineBaseline);
        rect2.offset(screenPointOfView2.x, screenPointOfView2.y);
        return rect2.intersect(rect) && getPenSelectionController().findTargetTextView(getContext(), getRootView(), rect2) == this;
    }

    @Override // android.view.View
    public int semExtractSmartClipData(SemSmartClipCroppedArea semSmartClipCroppedArea, SemSmartClipDataElement semSmartClipDataElement) {
        if (hasPasswordTransformationMethod()) {
            Log.d(LOG_TAG, "Cannot get text of Password field");
            return 1;
        }
        CharSequence textForRectSelection = getTextForRectSelection(semSmartClipCroppedArea.getRect());
        if (textForRectSelection == null) {
            textForRectSelection = "";
        }
        semSmartClipDataElement.addTag(new SemSmartClipMetaTag(SemSmartClipMetaTagType.PLAIN_TEXT, textForRectSelection.toString()));
        return 1;
    }

    private CharSequence getTextForRectSelection(Rect rect) {
        CharSequence textForMultiSelection = getTextForMultiSelection();
        if (textForMultiSelection == null) {
            textForMultiSelection = getText();
        }
        if (!TextUtils.isEmpty(textForMultiSelection) && this.mLayout != null) {
            Rect rect2 = new Rect();
            getGlobalVisibleRect(rect2, null);
            Point screenPointOfView = getScreenPointOfView(getRootView());
            rect2.offset(screenPointOfView.x, screenPointOfView.y);
            Point screenPointOfView2 = getScreenPointOfView(this);
            int totalPaddingTop = getTotalPaddingTop();
            int lineCount = this.mLayout.getLineCount();
            int lineTop = ((this.mLayout.getLineTop(0) + screenPointOfView2.y) + totalPaddingTop) - getScrollY();
            int lineBottom = ((this.mLayout.getLineBottom(lineCount - 1) + screenPointOfView2.y) + totalPaddingTop) - getScrollY();
            rect2.top = Math.max(lineTop, rect2.top);
            rect2.bottom = Math.min(lineBottom, rect2.bottom);
            if (!Rect.intersects(rect2, rect)) {
                return null;
            }
            int i = rect.left - screenPointOfView2.x;
            int i2 = rect.top - screenPointOfView2.y;
            int i3 = rect.right - screenPointOfView2.x;
            int i4 = rect.bottom - screenPointOfView2.y;
            int offsetAtCoordinate = getOffsetAtCoordinate(getLineAtCoordinate(i2), i);
            int offsetAtCoordinate2 = getOffsetAtCoordinate(getLineAtCoordinate(i4), i3);
            if (offsetAtCoordinate >= 0 && offsetAtCoordinate2 >= 0 && offsetAtCoordinate != offsetAtCoordinate2) {
                if (offsetAtCoordinate > offsetAtCoordinate2) {
                    offsetAtCoordinate = offsetAtCoordinate2;
                    offsetAtCoordinate2 = offsetAtCoordinate;
                }
                try {
                    return textForMultiSelection.subSequence(offsetAtCoordinate, offsetAtCoordinate2);
                } catch (IndexOutOfBoundsException e) {
                    Log.e(LOG_TAG, "IndexOutOfBoundsException" + e);
                    Log.e(LOG_TAG, "getTextForMultiSelection() = " + ((Object) this.getTextForMultiSelection()));
                    Log.e(LOG_TAG, "getText() = " + ((Object) this.getText()));
                    Log.e(LOG_TAG, "mLayout.getText() = " + ((Object) this.mLayout.getText()));
                }
            }
        }
        return null;
    }

    private CharSequence getTextForSingleWord(Rect rect, Rect rect2) {
        CharSequence textForMultiSelection = getTextForMultiSelection();
        if (textForMultiSelection == null) {
            textForMultiSelection = getText();
        }
        Layout layout = getLayout();
        if (TextUtils.isEmpty(textForMultiSelection) || layout == null || !checkPosOnText(rect.left, rect.top, 0)) {
            return null;
        }
        Point screenPointOfView = getScreenPointOfView(this);
        int i = rect.left - screenPointOfView.x;
        float f = rect.top - screenPointOfView.y;
        int offsetForPosition = getOffsetForPosition(i, f);
        int length = textForMultiSelection.length();
        if (offsetForPosition >= 0 && offsetForPosition <= length) {
            if (this.mWordIteratorForMultiSelection == null) {
                this.mWordIteratorForMultiSelection = new WordIterator(getTextServicesLocale());
            }
            this.mWordIteratorForMultiSelection.setCharSequence(textForMultiSelection, offsetForPosition, offsetForPosition);
            int beginning = this.mWordIteratorForMultiSelection.getBeginning(offsetForPosition);
            int end = this.mWordIteratorForMultiSelection.getEnd(offsetForPosition);
            int lineAtCoordinate = getLineAtCoordinate(f);
            int lineForOffset = layout.getLineForOffset(beginning);
            int lineForOffset2 = layout.getLineForOffset(end);
            if (lineAtCoordinate != lineForOffset) {
                beginning = layout.getLineStart(lineAtCoordinate);
            }
            if (lineAtCoordinate != lineForOffset2) {
                end = layout.getLineEnd(lineAtCoordinate);
            }
            if (beginning != -1 && end != -1 && beginning != end && beginning >= 0 && end >= 0) {
                if (beginning > end) {
                    Log.e(LOG_TAG, "AirDic : start > end !! start = " + beginning + ", end = " + end);
                    StringBuilder sb = new StringBuilder("AirDic : text = ");
                    sb.append((Object) textForMultiSelection);
                    Log.e(LOG_TAG, sb.toString());
                    Log.e(LOG_TAG, "AirDic : line = " + lineAtCoordinate + ", sLine = " + lineForOffset + ", eLine = " + lineForOffset2);
                    return null;
                }
                CharSequence charSequenceSubSequence = textForMultiSelection.subSequence(beginning, end);
                int lineTop = layout.getLineTop(lineAtCoordinate);
                int lineBottom = layout.getLineBottom(lineAtCoordinate);
                rect2.setEmpty();
                layout.getSelectionRect(lineAtCoordinate, beginning, end, lineTop, lineBottom, rect2);
                rect2.offset((screenPointOfView.x + getTotalPaddingLeft()) - getScrollX(), (screenPointOfView.y + getTotalPaddingTop()) - getScrollY());
                return charSequenceSubSequence;
            }
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
        CharSequence charSequence = this.mTransformed;
        if (charSequence instanceof Spannable) {
            return charSequence;
        }
        if (this instanceof EditText) {
            return this.mText;
        }
        return null;
    }

    public boolean getVisibleTextRange(int[] iArr) {
        Layout layout = getLayout();
        if (layout == null || iArr == null || iArr.length < 2) {
            return false;
        }
        CharSequence text = layout.getText();
        if (TextUtils.isEmpty(text)) {
            return false;
        }
        if (this.mEllipsize == TextUtils.TruncateAt.START) {
            int ellipsisStart = layout.getEllipsisStart(0);
            int ellipsisCount = layout.getEllipsisCount(0);
            if (ellipsisCount > 0) {
                iArr[0] = (ellipsisStart + ellipsisCount) - 1;
                iArr[1] = text.length();
            } else {
                iArr[0] = 0;
                iArr[1] = text.length();
            }
        } else if (this.mEllipsize == TextUtils.TruncateAt.MIDDLE) {
            iArr[0] = 0;
            iArr[1] = text.length();
        } else if (this.mEllipsize == TextUtils.TruncateAt.SEM_KEYWORD) {
            int ellipsisStart2 = layout.getEllipsisStart(0);
            int ellipsisCount2 = layout.getEllipsisCount(0);
            iArr[0] = 0;
            iArr[1] = text.length();
            if (ellipsisStart2 == 0) {
                if (ellipsisCount2 > 0) {
                    iArr[0] = (ellipsisStart2 + ellipsisCount2) - 1;
                    iArr[1] = text.length();
                }
            } else if (ellipsisCount2 > 0) {
                iArr[0] = 0;
                iArr[1] = ellipsisStart2 + 1;
            }
        } else {
            int lineCount = layout.getLineCount() - 1;
            if (lineCount < 0) {
                return false;
            }
            int ellipsisStart3 = layout.getEllipsisStart(lineCount);
            if (layout.getEllipsisCount(lineCount) > 0) {
                iArr[0] = 0;
                int lineStart = layout.getLineStart(lineCount) + ellipsisStart3 + 1;
                iArr[1] = lineStart;
                if (lineStart >= text.length()) {
                    iArr[1] = text.length() - 1;
                }
                CharSequence textForMultiSelection = getTextForMultiSelection();
                if (textForMultiSelection != null && Character.isLowSurrogate(textForMultiSelection.charAt(iArr[1]))) {
                    iArr[1] = iArr[1] + 1;
                }
            } else {
                iArr[0] = 0;
                iArr[1] = text.length();
            }
        }
        return true;
    }

    public boolean hasMultiSelection() {
        if (ViewRune.WIDGET_PEN_SUPPORTED) {
            return this.mhasMultiSelection;
        }
        return false;
    }

    public boolean clearMultiSelection() {
        if (!ViewRune.WIDGET_PEN_SUPPORTED) {
            return true;
        }
        this.mWordIteratorForMultiSelection = null;
        if (this.mhasMultiSelection) {
            CharSequence textForMultiSelection = getTextForMultiSelection();
            if (textForMultiSelection != null) {
                MultiSelection.clearMultiSelection((Spannable) textForMultiSelection);
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
        SpannableStringBuilder spannableStringBuilder = null;
        if (!ViewRune.WIDGET_PEN_SUPPORTED) {
            return null;
        }
        CharSequence textForMultiSelection = getTextForMultiSelection();
        if (textForMultiSelection != null && this.mhasMultiSelection) {
            int[] iArr = new int[2];
            boolean visibleTextRange = getVisibleTextRange(iArr);
            Spannable spannable = (Spannable) textForMultiSelection;
            int[] multiSelectionStart = MultiSelection.getMultiSelectionStart(spannable);
            int[] multiSelectionEnd = MultiSelection.getMultiSelectionEnd(spannable);
            int multiSelectionCount = MultiSelection.getMultiSelectionCount(spannable);
            if (ViewRune.WIDGET_MULTIPLE_PEN_TEXT_SUPPORTED) {
                Arrays.sort(multiSelectionStart);
                Arrays.sort(multiSelectionEnd);
            }
            for (int i = 0; i < multiSelectionCount; i++) {
                int i2 = multiSelectionStart[i];
                if (i2 <= multiSelectionEnd[i]) {
                    if (visibleTextRange) {
                        if (i2 == iArr[0]) {
                            multiSelectionStart[i] = 0;
                        }
                        if (multiSelectionEnd[i] == iArr[1]) {
                            multiSelectionEnd[i] = textForMultiSelection.length();
                        }
                    }
                    if (spannableStringBuilder == null) {
                        spannableStringBuilder = new SpannableStringBuilder(textForMultiSelection.subSequence(multiSelectionStart[i], multiSelectionEnd[i]));
                    } else if (ViewRune.WIDGET_MULTIPLE_PEN_TEXT_SUPPORTED) {
                        spannableStringBuilder.append((CharSequence) (ShaderAssembler.NEWLINE + ((Object) textForMultiSelection.subSequence(multiSelectionStart[i], multiSelectionEnd[i]))));
                    } else {
                        spannableStringBuilder.append(textForMultiSelection.subSequence(multiSelectionStart[i], multiSelectionEnd[i]));
                    }
                }
            }
        }
        return spannableStringBuilder;
    }

    public boolean isMultiSelectionLinkArea(int i, int i2) {
        CharSequence textForMultiSelection;
        if (!ViewRune.WIDGET_PEN_SUPPORTED || !this.mhasMultiSelection || this.mLayout == null || !checkPosInView(i, i2, 0) || (textForMultiSelection = getTextForMultiSelection()) == null) {
            return false;
        }
        Point screenPointOfView = getScreenPointOfView(this);
        int offsetAtCoordinate = getOffsetAtCoordinate(getLineAtCoordinate(i2 - screenPointOfView.y), i - screenPointOfView.x);
        Spannable spannable = (Spannable) textForMultiSelection;
        int[] multiSelectionStart = MultiSelection.getMultiSelectionStart(spannable);
        int[] multiSelectionEnd = MultiSelection.getMultiSelectionEnd(spannable);
        int multiSelectionCount = MultiSelection.getMultiSelectionCount(spannable);
        for (int i3 = 0; i3 < multiSelectionCount; i3++) {
            if (multiSelectionStart[i3] <= offsetAtCoordinate && offsetAtCoordinate <= multiSelectionEnd[i3]) {
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
        if (ViewRune.WIDGET_PEN_SUPPORTED && (viewRootImpl = getViewRootImpl()) != null) {
            if (mMotionEventMonitorListener != null) {
                removeForTouchMonitorListener();
            }
            ViewRootImpl.MotionEventMonitor motionEventMonitor = viewRootImpl.getMotionEventMonitor();
            TouchMonitorListener touchMonitorListener = new TouchMonitorListener();
            mMotionEventMonitorListener = touchMonitorListener;
            motionEventMonitor.registerMotionEventMonitor(touchMonitorListener);
        }
    }

    public void removeForTouchMonitorListener() {
        ViewRootImpl viewRootImpl;
        if (!ViewRune.WIDGET_PEN_SUPPORTED || (viewRootImpl = getViewRootImpl()) == null || mMotionEventMonitorListener == null) {
            return;
        }
        viewRootImpl.getMotionEventMonitor().unregisterMotionEventMonitor(mMotionEventMonitorListener);
        mMotionEventMonitorListener = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean selectCurrentWordForMultiSelection(int i, int i2) {
        CharSequence textForMultiSelection;
        int spanStart;
        int spanEnd;
        if (!ViewRune.WIDGET_PEN_SUPPORTED || (textForMultiSelection = getTextForMultiSelection()) == null) {
            return false;
        }
        if (hasPasswordTransformationMethod()) {
            MultiSelection.selectAll((Spannable) textForMultiSelection);
            return true;
        }
        int inputType = getInputType();
        int i3 = inputType & 15;
        int i4 = inputType & InputType.TYPE_MASK_VARIATION;
        if (i3 == 2 || i3 == 3 || i3 == 4 || i4 == 16 || i4 == 32 || i4 == 208 || i4 == 176) {
            MultiSelection.selectAll((Spannable) textForMultiSelection);
            return true;
        }
        Spanned spanned = (Spanned) textForMultiSelection;
        URLSpan[] uRLSpanArr = (URLSpan[]) spanned.getSpans(i, i2, URLSpan.class);
        if (uRLSpanArr.length >= 1) {
            URLSpan uRLSpan = uRLSpanArr[0];
            spanStart = spanned.getSpanStart(uRLSpan);
            spanEnd = spanned.getSpanEnd(uRLSpan);
        } else {
            if (this.mWordIteratorForMultiSelection == null) {
                this.mWordIteratorForMultiSelection = new WordIterator(getTextServicesLocale());
            }
            this.mWordIteratorForMultiSelection.setCharSequence(textForMultiSelection, i, i2);
            int beginning = this.mWordIteratorForMultiSelection.getBeginning(i);
            int end = this.mWordIteratorForMultiSelection.getEnd(i2);
            if (beginning != -1 && end != -1 && beginning != end) {
                spanStart = beginning;
                spanEnd = end;
            }
            return false;
        }
        if (spanStart >= 0 && spanEnd >= 0 && spanStart < spanEnd) {
            this.mIsTouchDown = false;
            MultiSelection.setSelection((Spannable) textForMultiSelection, spanStart, spanEnd);
            return true;
        }
        return false;
    }

    private void registerForStylusPenEvent() {
        if (ViewRune.WIDGET_PEN_SUPPORTED && this.mStylusEventListener == null) {
            if (!this.mEnableMultiSelection) {
                removeForStylusPenEvent();
            } else {
                if (isDisabledStylusPenEvent()) {
                    return;
                }
                ViewTreeObserver viewTreeObserver = getViewTreeObserver();
                StylusEventListener stylusEventListener = new StylusEventListener(this);
                this.mStylusEventListener = stylusEventListener;
                viewTreeObserver.semAddOnStylusButtonEventListener(stylusEventListener);
            }
        }
    }

    private void removeForStylusPenEvent() {
        if (ViewRune.WIDGET_PEN_SUPPORTED && this.mStylusEventListener != null) {
            getViewTreeObserver().semRemoveOnStylusButtonEventListener(this.mStylusEventListener);
            this.mStylusEventListener = null;
        }
    }

    private boolean isDisabledStylusPenEvent() {
        if (!ViewRune.WIDGET_PEN_SUPPORTED) {
            return true;
        }
        String basePackageName = this.mContext.getBasePackageName();
        if (basePackageName != null) {
            return basePackageName.equals("flipboard.boxer.app") || basePackageName.equals(AsPackageName.SYSTEMUI) || basePackageName.equals("com.android.keyguard") || basePackageName.equals("android");
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showMultiSelectPopupWindow() {
        MultiSelectPopupWindow multiSelectPopupWindow;
        if (!ViewRune.WIDGET_PEN_SUPPORTED || this.mLayout == null || (multiSelectPopupWindow = this.mMultiSelectPopupWindow) == null) {
            return;
        }
        multiSelectPopupWindow.changeCurrentSelectedView(this);
        this.mMultiSelectPopupWindow.showMultiSelectPopupWindow();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideMultiSelectPopupWindow() {
        if (ViewRune.WIDGET_PEN_SUPPORTED) {
            this.mMultiSelectPopupWindow.hideMultiSelectPopupWindow();
        }
    }

    private boolean isCharLTR() {
        int[] iArr = {-1, -1};
        if (this.mText.length() > 0) {
            iArr[0] = Character.getDirectionality(this.mText.charAt(0));
            CharSequence charSequence = this.mText;
            iArr[1] = Character.getDirectionality(charSequence.charAt(charSequence.length() - 1));
        }
        for (int i = 0; i < 2; i++) {
            int i2 = iArr[i];
            if (i2 == 0 || i2 == 14 || i2 == 15) {
                return true;
            }
        }
        return false;
    }

    private boolean isCharRTL() {
        int[] iArr = {-1, -1};
        if (this.mText.length() > 0) {
            iArr[0] = Character.getDirectionality(this.mText.charAt(0));
            CharSequence charSequence = this.mText;
            iArr[1] = Character.getDirectionality(charSequence.charAt(charSequence.length() - 1));
        }
        for (int i = 0; i < 2; i++) {
            int i2 = iArr[i];
            if (i2 == 1 || i2 == 2 || i2 == 16 || i2 == 17) {
                return true;
            }
        }
        return false;
    }

    private boolean isRightAligned() {
        int i = this.mGravity & Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK;
        int textAlignment = getTextAlignment();
        boolean z = i == 5 || (!isLayoutRtl() && textAlignment == 6) || (isLayoutRtl() && textAlignment == 5);
        if (isCharLTR() || !(isCharRTL() || isLayoutRtl())) {
            if (textAlignment == 3 || (textAlignment == 1 && i == 8388613)) {
                return true;
            }
        } else if ((isCharRTL() || isLayoutRtl()) && (textAlignment == 2 || (textAlignment == 1 && i == 8388611))) {
            return true;
        }
        return z;
    }

    private boolean isEditorNotFull() {
        return this.mEditor == null || getSelectionEnd() < length() || (((int) (Math.ceil((double) this.mTextPaint.getHCTStrokeWidth()) + Math.ceil((double) this.mTextPaint.measureText(this.mText.toString())))) + getCompoundPaddingLeft()) + getCompoundPaddingRight() < getWidth();
    }

    public boolean onMultiSelectMenuItem(MenuItem menuItem) {
        if (!ViewRune.WIDGET_PEN_SUPPORTED) {
            return false;
        }
        String penSelectionContents = getPenSelectionController().getPenSelectionContents(getContext(), getRootView());
        CharSequence textForMultiSelection = getTextForMultiSelection();
        if (textForMultiSelection == null) {
            Log.e(LOG_TAG, "getTextFormultiSelection() text is null");
            return false;
        }
        int itemId = menuItem.getItemId();
        if (itemId != 16909391) {
            clearAllMultiSelection();
        }
        if (penSelectionContents == null) {
            Log.e(LOG_TAG, "Multi Selected Text String is null");
            return false;
        }
        switch (itemId) {
            case 16909391:
                int[] iArr = new int[2];
                if (!getVisibleTextRange(iArr)) {
                    iArr[0] = 0;
                    iArr[1] = textForMultiSelection.length();
                }
                MultiSelection.setSelection((Spannable) textForMultiSelection, iArr[0], iArr[1]);
                showMultiSelectPopupWindow();
                break;
            case 16909392:
                setPrimaryClip(ClipData.newPlainText(null, new SpannableStringBuilder(penSelectionContents)));
                break;
            case 16909393:
                sendToDictionary(penSelectionContents, 0, penSelectionContents.length());
                break;
            case 16909394:
                try {
                    Intent intentMakeChooserIntent = makeChooserIntent(true);
                    intentMakeChooserIntent.addFlags(268435456);
                    getContext().startActivity(intentMakeChooserIntent);
                    break;
                } catch (ActivityNotFoundException e) {
                    Log.e(LOG_TAG, "Share failed");
                    Log.e(LOG_TAG, "ActivityNotFoundException", e);
                    break;
                }
            case 16909395:
                Intent intent = menuItem.getIntent();
                if (intent != null && Intent.ACTION_PROCESS_TEXT.equals(intent.getAction())) {
                    intent.putExtra(Intent.EXTRA_PROCESS_TEXT, penSelectionContents);
                    startActivityForResult(intent, 100);
                    break;
                }
                break;
        }
        return false;
        return true;
    }

    public void sendToDictionary(String str, int i, int i2) {
        PackageManager packageManager = getContext().getPackageManager();
        Intent intent = new Intent("com.sec.android.app.dictionary.SEARCH");
        intent.addFlags(32);
        intent.putExtra("keyword", str);
        intent.putExtra("force", "true");
        if (packageManager.queryBroadcastReceivers(intent, 32).size() > 0) {
            getContext().sendBroadcast(intent);
        }
    }

    @Override // android.view.View
    public boolean onKeyTextMultiSelection(int i, KeyEvent keyEvent) {
        if (!ViewRune.WIDGET_PEN_SUPPORTED) {
            return false;
        }
        if (i == 4 && keyEvent.getAction() == 1 && this.mhasMultiSelection) {
            clearMultiSelection();
            return true;
        }
        return super.onKeyTextMultiSelection(i, keyEvent);
    }

    @Override // android.view.View
    public boolean onHoverEvent(MotionEvent motionEvent) throws IllegalStateException, IllegalArgumentException {
        long j;
        long j2;
        float primaryHorizontal;
        float lineWidth;
        SemInfoExtractionManager semInfoExtractionManager;
        if (!ViewRune.WIDGET_PEN_SUPPORTED) {
            return super.onHoverEvent(motionEvent);
        }
        mLastHoveredViewId = getId() == -1 ? hashCode() : getId();
        mLastHoveredTime = SystemClock.uptimeMillis();
        CharSequence textForMultiSelection = getTextForMultiSelection();
        if (textForMultiSelection != null) {
            boolean z = (motionEvent.getButtonState() & 32) != 0;
            int actionMasked = motionEvent.getActionMasked();
            this.mEnableLinkPreview = isLinkPreviewEnabled(motionEvent.getToolType(0));
            final InputDevice device = motionEvent.getDevice();
            if (actionMasked == 9) {
                this.mCanTextMultiSelection = canTextMultiSelection();
            }
            if (actionMasked == 10) {
                if (this.mHoveredSpan != null) {
                    try {
                        new SemInfoExtractionManager(getContext()).hideLinkPreview();
                    } catch (IllegalStateException unused) {
                        Log.d(LOG_TAG, "** skip SemInfoExtractionManager Service by IllegalStateException, onHoverExit **");
                    }
                }
                this.mHoveredSpan = null;
                this.mHoverEnterTime = -1L;
                this.mHoverExitTime = -1L;
                if (MultiSelection.isTextViewHovered()) {
                    MultiSelection.setTextViewHovered(false);
                }
                return super.onHoverEvent(motionEvent);
            }
            if (!this.mEnableLinkPreview && !z) {
                return super.onHoverEvent(motionEvent);
            }
            if (actionMasked == 7) {
                Layout layout = getLayout();
                if (layout == null) {
                    return super.onHoverEvent(motionEvent);
                }
                int rawX = (int) motionEvent.getRawX();
                int rawY = (int) motionEvent.getRawY();
                if (z && this.mCanTextMultiSelection && checkPosOnText(rawX, rawY, (int) this.TOUCH_DELTA)) {
                    if (MultiSelection.getHoveredIcon() != 20002) {
                        post(new Runnable(this) { // from class: android.widget.TextView.6
                            @Override // java.lang.Runnable
                            public void run() {
                                MultiSelection.setTextViewHovered(true, 20002);
                            }
                        });
                        try {
                            if (this.mHoveredSpan != null) {
                                new SemInfoExtractionManager(getContext()).hideLinkPreview();
                            }
                            this.mHoveredSpan = null;
                            this.mHoverEnterTime = -1L;
                            this.mHoverExitTime = -1L;
                        } catch (IllegalStateException unused2) {
                            Log.d(LOG_TAG, "** hovering dismiss **");
                        }
                    }
                    return super.onHoverEvent(motionEvent);
                }
                if (MultiSelection.getHoveredIcon() == 20002) {
                    MultiSelection.setTextViewHovered(false);
                }
                Spannable spannable = (Spannable) textForMultiSelection;
                if (((URLSpan[]) spannable.getSpans(0, textForMultiSelection.length(), URLSpan.class)).length != 0) {
                    int x = (int) motionEvent.getX();
                    j = 300;
                    int y = (int) motionEvent.getY();
                    int totalPaddingLeft = x - getTotalPaddingLeft();
                    int totalPaddingTop = y - getTotalPaddingTop();
                    int scrollX = totalPaddingLeft + getScrollX();
                    int lineForVertical = layout.getLineForVertical(totalPaddingTop + getScrollY());
                    int offsetForHorizontal = layout.getOffsetForHorizontal(lineForVertical, scrollX);
                    j2 = 0;
                    URLSpan[] uRLSpanArr = (URLSpan[]) spannable.getSpans(offsetForHorizontal, offsetForHorizontal, URLSpan.class);
                    if (uRLSpanArr.length != 0) {
                        this.mIsShowingLinkPreview = true;
                        int spanStart = spannable.getSpanStart(uRLSpanArr[0]);
                        int spanEnd = spannable.getSpanEnd(uRLSpanArr[0]);
                        String string = textForMultiSelection.subSequence(spanStart, spanEnd).toString();
                        if (emailPattern.matcher(string).find()) {
                            return super.onHoverEvent(motionEvent);
                        }
                        if (!urlPattern.matcher(string).find()) {
                            return super.onHoverEvent(motionEvent);
                        }
                        int lineForOffset = layout.getLineForOffset(spanStart);
                        int lineForOffset2 = layout.getLineForOffset(spanEnd);
                        if (lineForVertical == lineForOffset) {
                            primaryHorizontal = layout.getPrimaryHorizontal(spanStart);
                        } else {
                            primaryHorizontal = layout.getPrimaryHorizontal(0);
                        }
                        int i = (int) primaryHorizontal;
                        if (lineForVertical == lineForOffset2) {
                            lineWidth = layout.getPrimaryHorizontal(spanEnd);
                        } else {
                            lineWidth = layout.getLineWidth(lineForVertical);
                        }
                        int i2 = (int) lineWidth;
                        if (i <= scrollX && scrollX <= i2) {
                            if (this.mHoveredSpan != uRLSpanArr[0]) {
                                if (this.mHoverEnterTime <= 0) {
                                    this.mHoverEnterTime = System.currentTimeMillis();
                                    post(new Runnable(this) { // from class: android.widget.TextView.7
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            MultiSelection.setTextViewHovered(true, 20010);
                                        }
                                    });
                                } else if (System.currentTimeMillis() - this.mHoverEnterTime > 300) {
                                    try {
                                        semInfoExtractionManager = new SemInfoExtractionManager(getContext());
                                    } catch (IllegalStateException unused3) {
                                        Log.d(LOG_TAG, "** skip SemInfoExtractionManager Service by IllegalStateException, onHover : URLSpan1 **");
                                        semInfoExtractionManager = null;
                                    }
                                    if (this.mHoveredSpan != null && semInfoExtractionManager != null) {
                                        semInfoExtractionManager.hideLinkPreview();
                                    }
                                    this.mHoveredSpan = uRLSpanArr[0];
                                    this.mHoverEnterTime = -1L;
                                    if (semInfoExtractionManager != null) {
                                        int totalPaddingTop2 = getTotalPaddingTop();
                                        int lineTop = layout.getLineTop(lineForOffset) + totalPaddingTop2;
                                        int lineBottom = layout.getLineBottom(lineForOffset2) + totalPaddingTop2;
                                        float y2 = motionEvent.getY() - lineTop;
                                        float y3 = lineBottom - motionEvent.getY();
                                        int rawXForScaledWindow = (int) motionEvent.getRawXForScaledWindow();
                                        float rawYForScaledWindow = (int) motionEvent.getRawYForScaledWindow();
                                        int i3 = (int) (rawYForScaledWindow - y2);
                                        int i4 = (int) (rawYForScaledWindow + y3);
                                        Rect rect = new Rect();
                                        rect.set(rawXForScaledWindow, i3, rawXForScaledWindow + 1, i4);
                                        if (!TextUtils.isEmpty(string)) {
                                            semInfoExtractionManager.showLinkPreview(string, rect);
                                            this.mIsShowingLinkPreview = false;
                                        }
                                    }
                                }
                            }
                        } else {
                            this.mHoverExitTime = -1L;
                        }
                        return super.onHoverEvent(motionEvent);
                    }
                    if (this.mIsShowingLinkPreview) {
                        MultiSelection.setTextViewHovered(false);
                        this.mIsShowingLinkPreview = false;
                    }
                } else {
                    j = 300;
                    j2 = 0;
                }
                if (this.mHoveredSpan != null) {
                    if (this.mHoverExitTime <= j2) {
                        this.mHoverExitTime = System.currentTimeMillis();
                        MultiSelection.setTextViewHovered(false);
                    } else if (System.currentTimeMillis() - this.mHoverExitTime > j) {
                        try {
                            new SemInfoExtractionManager(getContext()).hideLinkPreview();
                        } catch (IllegalStateException unused4) {
                            Log.d(LOG_TAG, "** skip SemInfoExtractionManager Service by IllegalStateException, onHover : hover exit **");
                        }
                        this.mHoveredSpan = null;
                        this.mHoverEnterTime = -1L;
                        this.mHoverExitTime = -1L;
                    }
                    return super.onHoverEvent(motionEvent);
                }
            }
        }
        return super.onHoverEvent(motionEvent);
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
            this.mThisView = textView;
        }

        @Override // android.view.ViewTreeObserver.SemOnStylusButtonEventListener
        public void onStylusButtonEvent(MotionEvent motionEvent) throws IllegalArgumentException {
            if (ViewRune.WIDGET_PEN_SUPPORTED) {
                int actionMasked = motionEvent.getActionMasked();
                if (motionEvent.getToolType(0) == 2 && TextView.this.mEnableMultiSelection) {
                    float rawX = motionEvent.getRawX();
                    float rawY = motionEvent.getRawY();
                    if (actionMasked == 0 && (motionEvent.getButtonState() & 32) != 0) {
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
                        } else {
                            TextView textView = TextView.this;
                            if (!textView.checkPosOnText((int) rawX, (int) rawY, (int) textView.TOUCH_DELTA)) {
                                return;
                            }
                        }
                    } else {
                        TextView.mIsFindTargetView = false;
                        if (!this.mPressed || TextView.mTargetViewId == -1) {
                            return;
                        }
                    }
                    CharSequence textForMultiSelection = TextView.this.getTextForMultiSelection();
                    if (TextUtils.isEmpty(textForMultiSelection)) {
                        return;
                    }
                    if (actionMasked == 0) {
                        if (this.mPressed) {
                            return;
                        }
                        TextView.this.hideMultiSelectPopupWindow();
                        this.mIsSelectableTextView = TextView.this.isTextSelectable() || (this.mThisView instanceof EditText);
                        Point screenPointOfView = TextView.this.getScreenPointOfView(this.mThisView);
                        this.mStartX = rawX - screenPointOfView.x;
                        float f = rawY - screenPointOfView.y;
                        this.mStartY = f;
                        this.mMaxX = 0.0f;
                        int offsetForPosition = TextView.this.getOffsetForPosition(this.mStartX, f);
                        this.mStartCurPosition = offsetForPosition;
                        if (offsetForPosition >= 0) {
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
                                if (!TextView.this.getVisibleTextRange(this.selectRange)) {
                                    int[] iArr = this.selectRange;
                                    iArr[0] = 0;
                                    iArr[1] = textForMultiSelection.length();
                                }
                                int i = this.mStartCurPosition;
                                int[] iArr2 = this.selectRange;
                                int i2 = iArr2[0];
                                if (i < i2) {
                                    this.mStartCurPosition = i2;
                                } else {
                                    int i3 = iArr2[1];
                                    if (i > i3) {
                                        this.mStartCurPosition = i3;
                                    }
                                }
                                TextView.mLastPenDownTime = TextView.mCurTime;
                                Log.d(TextView.LOG_TAG, "Pen down with side button! : start text selection");
                            }
                            this.oldEndPos = this.mStartCurPosition;
                            this.mPressed = true;
                            TextView.this.mIsTouchDown = true;
                        }
                        if (ViewRune.WIDGET_MULTIPLE_PEN_TEXT_SUPPORTED) {
                            Spannable spannable = (Spannable) textForMultiSelection;
                            if (MultiSelection.getMultiSelectionCount(spannable) > 0) {
                                int selectionStart = MultiSelection.getSelectionStart(spannable);
                                int selectionEnd = MultiSelection.getSelectionEnd(spannable);
                                int i4 = this.mStartCurPosition;
                                if (i4 < selectionStart || i4 > selectionEnd) {
                                    MultiSelection.addMultiSelection(spannable, selectionStart, selectionEnd);
                                }
                            }
                        }
                        MultiSelection.setNeedToScroll(false);
                        return;
                    }
                    if (actionMasked != 1) {
                        if (actionMasked != 2) {
                            if (actionMasked == 3 || actionMasked == 214) {
                                TextView.this.mIsTouchDown = false;
                                this.mPressed = false;
                                if (!this.mIsSelectableTextView) {
                                    MultiSelection.setIsMultiSelectingText(false);
                                    Spannable spannable2 = (Spannable) textForMultiSelection;
                                    MultiSelection.removeCurSelection(spannable2);
                                    TextView.this.mhasMultiSelection = MultiSelection.getMultiSelectionCount(spannable2) > 0;
                                    return;
                                }
                                TextView.this.mhasMultiSelection = false;
                                int i5 = this.mStartCurPosition;
                                if (i5 >= 0) {
                                    Selection.setSelection((Spannable) textForMultiSelection, i5);
                                    return;
                                }
                                return;
                            }
                            return;
                        }
                        if ((motionEvent.getButtonState() & 32) != 0) {
                            if (ViewRune.WIDGET_PEN_SUPPORTED) {
                                Point screenPointOfView2 = TextView.this.getScreenPointOfView(this.mThisView);
                                float f2 = rawX - screenPointOfView2.x;
                                float f3 = rawY - screenPointOfView2.y;
                                MultiSelection.setNeedToScroll(TextView.this.checkPosInView((int) rawX, (int) rawY, 0));
                                if (this.mMaxX < Math.abs(this.mStartX - f2)) {
                                    this.mMaxX = Math.abs(this.mStartX - f2);
                                }
                                int offsetForPosition2 = TextView.this.getOffsetForPosition(f2, f3);
                                this.mEndCurPosition = offsetForPosition2;
                                boolean z = this.mIsSelectableTextView;
                                if (!z) {
                                    int[] iArr3 = this.selectRange;
                                    int i6 = iArr3[0];
                                    if (offsetForPosition2 < i6) {
                                        this.mEndCurPosition = i6;
                                    } else {
                                        int i7 = iArr3[1];
                                        if (offsetForPosition2 > i7) {
                                            this.mEndCurPosition = i7;
                                        }
                                    }
                                }
                                int i8 = this.mStartCurPosition;
                                int i9 = this.mEndCurPosition;
                                if (i8 == i9) {
                                    if (z) {
                                        return;
                                    }
                                    MultiSelection.removeCurSelection((Spannable) textForMultiSelection);
                                    return;
                                }
                                if (this.oldEndPos == i9 || i9 < 0) {
                                    return;
                                }
                                this.oldEndPos = i9;
                                TextView.this.mHighlightPathBogus = true;
                                if (!this.mIsSelectableTextView) {
                                    if (this.mStartCurPosition > textForMultiSelection.length()) {
                                        this.mStartCurPosition = textForMultiSelection.length();
                                    }
                                    if (this.mEndCurPosition > textForMultiSelection.length()) {
                                        this.mEndCurPosition = textForMultiSelection.length();
                                    }
                                    MultiSelection.setSelection((Spannable) textForMultiSelection, this.mStartCurPosition, this.mEndCurPosition);
                                    TextView.this.mhasMultiSelection = true;
                                    return;
                                }
                                if (!this.mThisView.isFocused()) {
                                    this.mThisView.requestFocus();
                                }
                                Selection.setSelection((Spannable) textForMultiSelection, this.mStartCurPosition, this.mEndCurPosition);
                                return;
                            }
                            return;
                        }
                    }
                    Point screenPointOfView3 = TextView.this.getScreenPointOfView(this.mThisView);
                    float f4 = rawX - screenPointOfView3.x;
                    float f5 = rawY - screenPointOfView3.y;
                    if (this.mMaxX < Math.abs(this.mStartX - f4)) {
                        this.mMaxX = Math.abs(this.mStartX - f4);
                    }
                    int offsetForPosition3 = TextView.this.getOffsetForPosition(f4, f5);
                    this.mEndCurPosition = offsetForPosition3;
                    if (!this.mIsSelectableTextView) {
                        int[] iArr4 = this.selectRange;
                        int i10 = iArr4[0];
                        if (offsetForPosition3 < i10) {
                            this.mEndCurPosition = i10;
                        } else {
                            int i11 = iArr4[1];
                            if (offsetForPosition3 > i11) {
                                this.mEndCurPosition = i11;
                            }
                        }
                    }
                    boolean z2 = TextView.this.mLayout != null && TextView.this.getLineAtCoordinate(this.mStartY) == TextView.this.getLineAtCoordinate(f5);
                    if (!this.mIsSelectableTextView && z2 && this.mMaxX < TextView.this.TOUCH_DELTA) {
                        if (TextView.mShowPenSelectionRunnable != null) {
                            this.mThisView.removeCallbacks(TextView.mShowPenSelectionRunnable);
                            TextView.mShowPenSelectionRunnable = null;
                        }
                        TextView.mShowPenSelectionRunnable = new Runnable() { // from class: android.widget.TextView.StylusEventListener.1
                            @Override // java.lang.Runnable
                            public void run() throws IllegalArgumentException {
                                CharSequence textForMultiSelection2 = TextView.this.getTextForMultiSelection();
                                if (TextView.this.selectCurrentWordForMultiSelection(StylusEventListener.this.mStartCurPosition, StylusEventListener.this.mEndCurPosition)) {
                                    Spannable spannable3 = (Spannable) textForMultiSelection2;
                                    StylusEventListener.this.mStartCurPosition = MultiSelection.getSelectionStart(spannable3);
                                    StylusEventListener.this.mEndCurPosition = MultiSelection.getSelectionEnd(spannable3);
                                    TextView.this.showMultiSelectPopupWindow();
                                    try {
                                        new SemInfoExtractionManager(TextView.this.getContext()).training(textForMultiSelection2.subSequence(StylusEventListener.this.mStartCurPosition, StylusEventListener.this.mEndCurPosition).toString());
                                    } catch (IllegalStateException unused) {
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
                    int i12 = this.mStartCurPosition;
                    int i13 = this.mEndCurPosition;
                    if (i12 == i13) {
                        this.mPressed = false;
                        if (!this.mIsSelectableTextView) {
                            MultiSelection.setIsMultiSelectingText(false);
                            Spannable spannable3 = (Spannable) textForMultiSelection;
                            MultiSelection.removeCurSelection(spannable3);
                            TextView.this.mhasMultiSelection = MultiSelection.getMultiSelectionCount(spannable3) > 0;
                            return;
                        }
                        TextView.this.mhasMultiSelection = false;
                        return;
                    }
                    if (i12 > i13) {
                        this.mStartCurPosition = i13;
                        this.mEndCurPosition = i12;
                    }
                    if (!this.mIsSelectableTextView) {
                        MultiSelection.setSelection((Spannable) textForMultiSelection, this.mStartCurPosition, this.mEndCurPosition);
                        TextView.this.showMultiSelectPopupWindow();
                        try {
                            new SemInfoExtractionManager(TextView.this.getContext()).training(textForMultiSelection.subSequence(this.mStartCurPosition, this.mEndCurPosition).toString());
                        } catch (IllegalStateException unused) {
                            Log.e(TextView.LOG_TAG, "** skip SemInfoExtractionManager Service by IllegalStateException **");
                        }
                        Log.d(TextView.LOG_TAG, "Pen up with side button! : end text selection");
                        TextView.this.registerForTouchMonitorListener();
                        TextView.this.mhasMultiSelection = true;
                        MultiSelection.setIsMultiSelectingText(false);
                    } else if (this.mStartCurPosition >= 0 && this.mEndCurPosition >= 0 && ViewRune.WIDGET_PEN_SUPPORTED) {
                        Selection.setSelection((Spannable) textForMultiSelection, this.mStartCurPosition, this.mEndCurPosition);
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
        }
    }

    @RemotableViewMethod
    public void semSetButtonShapeEnabled(boolean z) {
        this.mIsButtonShapeTarget = z;
        initButtonShape();
    }

    private void hidden_semSetButtonShapeEnabled(boolean z) {
        semSetButtonShapeEnabled(z);
    }

    public void semSetButtonShapeEnabled(boolean z, int i) {
        this.mButtonShapeTextColor = Integer.valueOf(i);
        semSetButtonShapeEnabled(z);
    }

    private void hidden_semSetButtonShapeEnabled(boolean z, int i) {
        semSetButtonShapeEnabled(z, i);
    }

    private void initButtonShape() {
        if (this.mButtonShapePaint == null) {
            Paint paint = new Paint();
            this.mButtonShapePaint = paint;
            paint.setAntiAlias(true);
        }
        if (this.mButtonShapeRect == null) {
            this.mButtonShapeRect = new RectF();
        }
        Resources resources = getResources();
        this.mIsNightMode = (resources.getConfiguration().uiMode & 48) == 32;
        this.mButtonShapeSettingEnabled = resources.getConfiguration().semButtonShapeEnabled == 1;
        this.mButtonShapeTextColorLight = resources.getColor(R.color.sem_button_shape_text_color_light, null);
        this.mButtonShapeTextColorDark = resources.getColor(R.color.sem_button_shape_text_color_dark, null);
        this.mButtonShapeOutlineStrokeEnabled = resources.getInteger(R.integer.sem_button_shape_outline_stroke_enabled);
        this.mButtonShapeOutlineStrokeDisabled = resources.getInteger(R.integer.sem_button_shape_outline_stroke_disabled);
        this.mButtonShapeAlpha = this.mButtonShapeOutlineStrokeEnabled;
        this.mButtonShapeOutlineStrokeTop = resources.getDimensionPixelSize(R.dimen.sem_button_shape_outline_stroke_top);
        this.mButtonShapeOutlineStrokeBottom = resources.getDimensionPixelSize(R.dimen.sem_button_shape_outline_stroke_bottom);
        this.mButtonShapeOutlineStrokeHorizontal = resources.getDimensionPixelSize(R.dimen.sem_button_shape_outline_stroke_horizontal);
        this.mButtonShapeOutlineRadius = resources.getDimensionPixelSize(R.dimen.sem_button_shape_outline_radius);
    }

    private int getButtonShapeTextColor() {
        double dCalculateLuminance = ColorUtils.calculateLuminance(this.mButtonShapeColor);
        this.mButtonShapeLuminance = dCalculateLuminance;
        Integer num = this.mButtonShapeTextColor;
        if (num != null) {
            return num.intValue();
        }
        return this.mIsNightMode ? (dCalculateLuminance <= SContextConstants.ENVIRONMENT_VALUE_UNKNOWN || dCalculateLuminance >= 0.25d) ? this.mButtonShapeTextColorDark : this.mButtonShapeTextColorLight : dCalculateLuminance > 0.8d ? this.mButtonShapeTextColorDark : this.mButtonShapeTextColorLight;
    }

    public void setCursorThicknessScale(float f) {
        this.mCursorThicknessScale = f;
        if (this.mLayout != null) {
            nullLayouts();
            requestLayout();
            invalidate();
        }
    }

    float getCursorThicknessScale() {
        return Math.max(this.mCursorThicknessScale, 1.0f);
    }

    void startChooserPopupActivity(Point point, boolean z) {
        Intent intentMakeChooserIntent = makeChooserIntent(z);
        if (intentMakeChooserIntent == null) {
            return;
        }
        ActivityOptions activityOptionsMakeBasic = ActivityOptions.makeBasic();
        activityOptionsMakeBasic.semSetChooserPopOverPosition(getPosition(point));
        intentMakeChooserIntent.setFlags(268435456);
        getContext().startActivity(intentMakeChooserIntent, activityOptionsMakeBasic.toBundle());
        if (z) {
            return;
        }
        Selection.setSelection(this.mSpannable, getSelectionEnd());
    }

    private int getPosition(Point point) throws Resources.NotFoundException {
        int i;
        Resources resources = getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        int i2 = displayMetrics.heightPixels;
        int i3 = displayMetrics.widthPixels;
        int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.status_bar_height);
        Point point2 = new Point();
        point2.x = i3 / 3;
        point2.y = (i2 - dimensionPixelSize) / 3;
        if (point.x < 0 || point.x >= point2.x) {
            i = (point2.x > point.x || point.x >= (point2.x << 1)) ? 32 : 64;
        } else {
            i = 16;
        }
        return (dimensionPixelSize > point.y || point.y >= point2.y + dimensionPixelSize) ? (point2.y + dimensionPixelSize > point.y || point.y >= dimensionPixelSize + (point2.y << 1)) ? i | 2 : i | 4 : i | 1;
    }

    private Intent makeChooserIntent(boolean z) {
        String selectedText;
        String string;
        if (z) {
            selectedText = getPenSelectionController().getPenSelectionContents(getContext(), getRootView());
            string = getContext().getString(R.string.share);
        } else {
            selectedText = getSelectedText();
            string = null;
        }
        if (selectedText == null || selectedText.isEmpty()) {
            return null;
        }
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.removeExtra(Intent.EXTRA_TEXT);
        intent.putExtra(Intent.EXTRA_TEXT, (String) TextUtils.trimToParcelableSize(selectedText));
        return Intent.createChooser(intent, string);
    }

    boolean canSSSPaste() {
        Editor editor;
        return (this.mText instanceof Editable) && (editor = this.mEditor) != null && editor.mKeyListener != null && getSelectionStart() >= 0 && getSelectionEnd() >= 0;
    }

    protected String semGetFontFamily() {
        return this.mFontFamily;
    }

    @Deprecated
    public int semAddOuterShadowTextEffect(float f, float f2, float f3, int i, float f4) {
        if (this.mTextEffect == null) {
            this.mTextEffect = new SFText(getContext());
        }
        return this.mTextEffect.addOuterShadowTextEffect(f, f2, f3, i, f4);
    }

    @Deprecated
    public int semAddInnerShadowTextEffect(float f, float f2, float f3, int i, float f4) {
        if (this.mTextEffect == null) {
            this.mTextEffect = new SFText(getContext());
        }
        return this.mTextEffect.addInnerShadowTextEffect(f, f2, f3, i, f4);
    }

    @Deprecated
    public int semAddStrokeTextEffect(float f, int i, float f2) {
        if (this.mTextEffect == null) {
            this.mTextEffect = new SFText(getContext());
        }
        return this.mTextEffect.addStrokeTextEffect(f, i, f2);
    }

    @Deprecated
    public int semAddOuterGlowTextEffect(float f, int i, float f2) {
        if (this.mTextEffect == null) {
            this.mTextEffect = new SFText(getContext());
        }
        return this.mTextEffect.addOuterGlowTextEffect(f, i, f2);
    }

    @Deprecated
    public int semAddLinearGradientTextEffect(float f, float f2, int[] iArr, float[] fArr, float[] fArr2, float f3) {
        if (this.mTextEffect == null) {
            this.mTextEffect = new SFText(getContext());
        }
        return this.mTextEffect.addLinearGradientTextEffect(f, f2, iArr, fArr, fArr2, f3);
    }

    @Deprecated
    public void semClearAllTextEffect() {
        if (this.mTextEffect == null) {
            this.mTextEffect = new SFText(getContext());
        }
        this.mTextEffect.clearAllTextEffect();
    }

    @Deprecated
    public void semSetFontFromFile(String str) {
        Preconditions.checkNotNull(str);
        if (this.mTextEffect == null) {
            this.mTextEffect = new SFText(getContext());
        }
        this.mTextEffect.setFontFromFile(str);
    }

    @Deprecated
    public void semSetFontFromAsset(AssetManager assetManager, String str) {
        Preconditions.checkNotNull(str);
        Preconditions.checkNotNull(assetManager);
        setTypeface(Typeface.createFromAsset(assetManager, str));
        if (this.mTextEffect == null) {
            this.mTextEffect = new SFText(getContext());
        }
        this.mTextEffect.setFontFromAsset(assetManager, str);
    }

    @Override // android.view.View
    public View semDispatchFindView(PointF pointF, boolean z, ISemTouchApi iSemTouchApi) {
        iSemTouchApi.getViewContent(this.mContext, this.mContext.getPackageName(), this, pointF, this.mText);
        return this;
    }
}
